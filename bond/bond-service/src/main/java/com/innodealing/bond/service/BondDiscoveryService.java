package com.innodealing.bond.service;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.innodealing.bond.param.BondAbnormalPriceHistoryReq;
import com.innodealing.domain.websocket.*;
import com.innodealing.model.dm.bond.*;
import com.innodealing.model.mongo.dm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.innodealing.adapter.BondInstitutionInduAdapter;
import com.innodealing.bond.param.BondAbnormalPriceFilterReq;
import com.innodealing.bond.param.BondTodayDetailReq;
import com.innodealing.bond.param.BondTodayFilterReq;
import com.innodealing.engine.redis.RedisMsgService;
import com.innodealing.engine.redis.RedisUtil;
import com.innodealing.exception.BusinessException;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;

@Service
public class BondDiscoveryService {
    private static final Logger LOG = LoggerFactory.getLogger(BondDiscoveryService.class);

    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private RedisMsgService redisMsgService;
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BondInduService induService;
    
    @Autowired
    private BondInstitutionInduAdapter induAdapter;

    @Autowired
    protected BondComInfoService comInfoService;

    final Criteria CRITERIA_MATCH_ALL = new Criteria();

    // 有效时间
    private final static Long EFFECTIVE_TIME = 130L;
    private final static String USER_DEAL_PREFIX = "todayUserDealPageTime";
    private final static String USER_QUOTE_PREFIX = "todayUserQuotePageTime";
    private static final String DEFAULT_DM_RATING_NAME = "CCC ~ C";
    private static final String DEFAULT_EXT_RATING_NAME = "≤A+";

    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);
    private static Map<String, List<String>> dmRatingRoot2SubMap = new LinkedHashMap<>();
    private static Map<String, String> dmRatingSub2RootMap = new HashMap<>();

    private static List<String> dmSubRowNameList = new ArrayList<>();
    private static List<String> dmRootRowNameList = new ArrayList<>();

    private static List<String> extRowNameList = new ArrayList<>();
    private static List<String> columnNameList = new ArrayList<>();

    private static List<String> extFormalRowNameList = new ArrayList<>();

    static {
        dmRatingRoot2SubMap.put("AAA ~ AA+", Arrays.asList("AAA", "AA+"));
        dmRatingRoot2SubMap.put("AA ~ A+", Arrays.asList("AA", "AA-", "A+"));
        dmRatingRoot2SubMap.put("A ~ BBB", Arrays.asList("A", "A-", "BBB+", "BBB"));
        dmRatingRoot2SubMap.put("BBB- ~ BB+", Arrays.asList("BBB-", "BB+"));
        dmRatingRoot2SubMap.put("BB ~ B+", Arrays.asList("BB-", "BB", "B+"));
        dmRatingRoot2SubMap.put("B ~ CCC+", Arrays.asList("B", "B-", "CCC+"));
        dmRatingRoot2SubMap.put("CCC ~ C", Arrays.asList("CCC", "CCC-", "C"));

        dmRatingSub2RootMap.put("AAA", "AAA ~ AA+");
        dmRatingSub2RootMap.put("AA+", "AAA ~ AA+");
        dmRatingSub2RootMap.put("AA", "AA ~ A+");
        dmRatingSub2RootMap.put("AA-", "AA ~ A+");
        dmRatingSub2RootMap.put("A+", "AA ~ A+");
        dmRatingSub2RootMap.put("A", "A ~ BBB");
        dmRatingSub2RootMap.put("A-", "A ~ BBB");
        dmRatingSub2RootMap.put("BBB+", "A ~ BBB");
        dmRatingSub2RootMap.put("BBB", "A ~ BBB");
        dmRatingSub2RootMap.put("BBB-", "BBB- ~ BB+");
        dmRatingSub2RootMap.put("BB+", "BBB- ~ BB+");
        dmRatingSub2RootMap.put("BB-", "BB ~ B+");
        dmRatingSub2RootMap.put("BB", "BB ~ B+");
        dmRatingSub2RootMap.put("B+", "BB ~ B+");
        dmRatingSub2RootMap.put("B", "B ~ CCC+");
        dmRatingSub2RootMap.put("B-", "B ~ CCC+");
        dmRatingSub2RootMap.put("CCC+", "B ~ CCC+");
        dmRatingSub2RootMap.put("CCC", "CCC ~ C");
        dmRatingSub2RootMap.put("CCC-", "CCC ~ C");
        dmRatingSub2RootMap.put("C", "CCC ~ C");

        dmRootRowNameList.addAll(dmRatingRoot2SubMap.keySet());
        dmSubRowNameList.addAll(dmRatingSub2RootMap.keySet());
        extFormalRowNameList = Arrays.asList("AAA", "AA+", "AA", "AA-");
        extRowNameList = Arrays.asList("AAA", "AA+", "AA", "AA-", "≤A+");
        columnNameList = Arrays.asList("1M", "3M", "6M", "9M", "1Y", "3Y", "5Y", "7Y", "10Y");
    }

    // ===========================================================
    // ========================= 今日成交 =========================
    // ===========================================================
    /**
     * 今日成交 -> 用户请求数据，按照DM量化评分
     * 用户首次访问，全部不产生未读红点标识
     * 1.用户访问时间redis仅保存130L，用户在130s后的请求，由于redis中已经没有记录了，跟首次访问没有区别
     * 2.用户在130s内有请求，会重置超时时间130L，已经记录的首次访问时间不变；再根据用户点击情况，生成未读红点标识
     *
     * @param req
     * @return
     */
    public BondTodayDMAggr getBondTodayDealDMRating(BondTodayFilterReq req) {
        // 更新用户访问请求时间
        Date currDate = SafeUtils.getCurrentTime();
        String currDateStr = SafeUtils.convertDateToString(currDate, SafeUtils.TIMESTAMP_FORMAT);
        String redisKey = String.format("%1$s_%2$d", USER_DEAL_PREFIX, req.getUserId());
        String redisUserPageTimeStr = redisMsgService.getMsgContent(redisKey);
        boolean isUserAccessTimePositive = false;
        if (StringUtils.isEmpty(redisUserPageTimeStr)) {
            // 用户当前首次访问, 130s失效
            redisMsgService.saveMsgWithTimeout(redisKey, currDateStr, EFFECTIVE_TIME);
        } else {
            // 有访问记录，重置失效时间
            redisMsgService.saveMsgWithTimeout(redisKey, redisUserPageTimeStr, EFFECTIVE_TIME);
            isUserAccessTimePositive = true;
        }
        // 生成空模板
        BondTodayDMAggr todayDMAggr = this.getTodayDMAggr();
        // 分解bondTodayDMAggr
        Map<String, BondTodayDMRow> subRatingRowMap = new HashMap<>();
        Map<String, BondTodayDMRow> rootRatingRowMap = new HashMap<>();
        todayDMAggr.getRootRowList().stream().forEach(rootRow -> {
            rootRatingRowMap.put(rootRow.getName(), rootRow);
            rootRow.getSubRowList().stream().forEach(subRow -> subRatingRowMap.put(subRow.getName(), subRow));
        });
        // 取数据，并装配子集+父集
        List<BondDiscoveryTodayDealDetailDoc> docList = this.getDealFilterDetailDocList(req);
        todayDMAggr.setCount(docList.size());
        docList.stream().forEach(doc -> {
            // 生成BondTodayDMItem
            String rating = doc.getDmRating();
            // 更新子集
            BondTodayDMRow subRow = subRatingRowMap.get(rating);
            this.updateTodayDealRowItem(doc, subRow);
            // 更新父集
            String rootKey = dmRatingSub2RootMap.get(rating);
            BondTodayDMRow rootRow = rootRatingRowMap.get(rootKey);
            this.updateTodayDealRowItem(doc, rootRow);
        });
        // 装配用户未读红点标识
        if (isUserAccessTimePositive) {
            this.assemblyDealDMItemReadStatus(req.getUserId(), subRatingRowMap, rootRatingRowMap, redisUserPageTimeStr);
        }
        return todayDMAggr;
    }

    /**
     * 今日成交 -> 用户请求按照外部评级数据
     * 用户首次访问，全部不产生未读红点标识
     * 1.用户访问时间redis仅保存130L，用户在130s后的请求，由于redis中已经没有记录了，跟首次访问没有区别
     * 2.用户在130s内有请求，会重置超时时间130L，已经记录的首次访问时间不变；再根据用户点击情况，生成未读红点标识
     *
     * @param req
     * @return
     */
    public BondTodayExtAggr getBondTodayDealExtRating(BondTodayFilterReq req) {
        // 更新用户访问请求时间
        Date currDate = SafeUtils.getCurrentTime();
        String currDateStr = SafeUtils.convertDateToString(currDate, SafeUtils.TIMESTAMP_FORMAT);
        String redisKey = String.format("%1$s_%2$d", USER_DEAL_PREFIX, req.getUserId());
        String redisUserPageTimeStr = redisMsgService.getMsgContent(redisKey);
        boolean isUserAccessTimePositive = false;
        if (StringUtils.isEmpty(redisUserPageTimeStr)) {
            // 用户当前首次访问, 130s失效
            redisMsgService.saveMsgWithTimeout(redisKey, currDateStr, EFFECTIVE_TIME);
        } else {
            // 有访问记录，重置失效时间
            redisMsgService.saveMsgWithTimeout(redisKey, redisUserPageTimeStr, EFFECTIVE_TIME);
            isUserAccessTimePositive = true;
        }
        // 生成空模板
        BondTodayExtAggr todayExtAggr = this.getTodayExtAggr();
        // 分解bondTodayExtAggr
        Map<String, BondTodayExtRow> ratingRowMap = new HashMap<>();
        todayExtAggr.getRowList().stream().forEach(rootRow -> {
            ratingRowMap.put(rootRow.getName(), rootRow);
        });
        // 取数据装配行集合
        List<BondDiscoveryTodayDealDetailDoc> docList = this.getDealFilterDetailDocList(req);
        todayExtAggr.setCount(docList.size());
        docList.stream().forEach(doc -> {
            // 生成BondTodayExtItem
            String rating = doc.getExtRating();
            // 更新行集合
            BondTodayExtRow row = ratingRowMap.get(rating);
            this.updateTodayDealRowItem(doc, row);
        });
        // 装配用户未读红点标识
        if (isUserAccessTimePositive) {
            this.assemblyDealExtItemReadStatus(req.getUserId(), ratingRowMap, redisUserPageTimeStr);
        }
        return todayExtAggr;
    }

    /**
     * 今日成交 -> 获取单元格中的债券详情列表
     * @param req
     * @return
     */
    public Page<BondDiscoveryTodayDealDetailDoc> getBondTodayDealDetailDocList(BondTodayDetailReq req,
                                                                               Integer page, Integer limit, String sort) {
        Sort customSort = this.generateConditionSort(sort);
        Date currDate = SafeUtils.getCurrentTime();
        // 1.参数合法性判断
        this.detailReqValidation(req);
        // 2.记录用户的点击单元格时间，用来标识未读红点
        this.saveDealUserClickRecord(req, currDate);
        // 3.返回该单元格中的债券详情列表
        Query query = this.getBondTodayDetailQuery(req);
        Long total = mongoOperations.count(query, BondDiscoveryTodayDealDetailDoc.class);

        PageRequest request = new PageRequest(page, limit, customSort);
        query.with(request);
        List<BondDiscoveryTodayDealDetailDoc> docList = mongoOperations.find(query, BondDiscoveryTodayDealDetailDoc.class);
        docList = induAdapter.convList(docList, req.getUserId());
        return new PageImpl<>(docList, new PageRequest(page, limit, null), total);
    }

    private Sort generateConditionSort(String sort) {
        String[] sortPars = sort.split(":");
        String sortField = sortPars[0];
        Direction sortDir = sortPars[1].toLowerCase().startsWith("des") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return new Sort(sortDir, sortField);
    }

    /**
     * 今日成交 -> 获取单元格中的债券详情列表
     * @param req
     * @return
     */
    public Page<BondDiscoveryTodayDealDetailDoc> getAllBondTodayDealDetailDocList(BondTodayDetailReq req,
                                                                                  Integer page, Integer limit, String sort) {
        Sort customSort = this.generateConditionSort(sort);
        // 根据side返回债券成交详情列表
        Query query = new Query();
        Criteria criteria = new Criteria();
        List<Criteria> subCriteriaList = new ArrayList<>();
        // 未勾选包含存单，过滤出部分数据；勾选了包含存单，为全部数据
        if (!req.getIsContainsNCD()) {
            subCriteriaList.add(Criteria.where("isNCD").is(false));
        }
        if (req.getInduIds() != null && !req.getInduIds().isEmpty()) {
            // 选择了行业列表，过滤出部分数据
            Long userId = req.getUserId();
            Integer userInduClass = induService.findInduClassByUser(userId);
            String induPropertyName = induService.getInduIdByClass(userInduClass, userId);
            subCriteriaList.add(new CriteriaFactory<Integer>().generate(induPropertyName, req.getInduIds()));
        }
        if (!subCriteriaList.isEmpty()) {
            criteria.andOperator(subCriteriaList.toArray(new Criteria[subCriteriaList.size()]));
            query.addCriteria(criteria);
        }
        Long total = mongoOperations.count(query, BondDiscoveryTodayDealDetailDoc.class);

        PageRequest request = new PageRequest(page, limit, customSort);
        query.with(request);
        List<BondDiscoveryTodayDealDetailDoc> docList = mongoOperations.find(query, BondDiscoveryTodayDealDetailDoc.class);
        docList = induAdapter.convList(docList, req.getUserId());
        return new PageImpl<>(docList, new PageRequest(page, limit, null), total);
    }

    /**
     * 今日成交 -> 记录用户点击Item时间
     * @param detailReq
     * @param currDate
     * @return
     */
    private void saveDealUserClickRecord(BondTodayDetailReq detailReq, Date currDate) {
        Long userId = detailReq.getUserId();
        Integer ratingType = detailReq.getRatingType();
        String rating = detailReq.getRating();
        String tenor = detailReq.getTenor();

        List<BondDiscoveryTodayDealUserDoc> docList = new ArrayList<>();
        List<String> tobeRemovedRatingList = new ArrayList<>();
        if (dmRootRowNameList.contains(rating)) {
            // 根节点
            List<String> subRatingList = dmRatingRoot2SubMap.get(rating);
            subRatingList.stream().forEach(subRating -> {
                docList.add(this.getDealUserClickRecordDocRef(userId, ratingType, subRating, tenor, currDate));
                tobeRemovedRatingList.add(subRating);
            });
        } else {
            docList.add(this.getDealUserClickRecordDocRef(userId, ratingType, rating, tenor, currDate));
            tobeRemovedRatingList.add(rating);
        }
        // 先删除
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId).and("rating").in(tobeRemovedRatingList).and("tenor").is(tenor));
        mongoOperations.remove(query, BondDiscoveryTodayDealUserDoc.class);
        // 再添加
        if (!docList.isEmpty()) {
            mongoOperations.insertAll(docList);
        }
    }

    /**
     * 今日成交 -> 根据过滤条件进行过滤成交数据
     * @param req
     * @return
     */
    private List<BondDiscoveryTodayDealDetailDoc> getDealFilterDetailDocList(BondTodayFilterReq req) {
        List<Integer> induIdList = req.getInduIds();
        Query query = new Query();
        Criteria criteria = new Criteria();
        // 子约束条件
        List<Criteria> subCriteriaList = new ArrayList<>();
        // 未勾选包含存单，过滤出部分数据；勾选了包含存单，为全部数据
        if (req.getIsNCD() != null && !req.getIsNCD()) {
            subCriteriaList.add(Criteria.where("isNCD").is(false));
        }
        Long userId = req.getUserId();
        // 选择了行业列表，过滤出部分数据
        if (!induIdList.isEmpty()) {
            // String induPropertyName = "institutionInduMap.code4979";
            Integer userInduClass = induService.findInduClassByUser(userId);
            String induPropertyName = induService.getInduIdByClass(userInduClass, userId);
            subCriteriaList.add(new CriteriaFactory<Integer>().generate(induPropertyName, induIdList));
        }
        if (!subCriteriaList.isEmpty()) {
        	criteria.andOperator(subCriteriaList.toArray(new Criteria[subCriteriaList.size()]));
        }
        // 按照createTime倒序
        query.addCriteria(criteria).with(new Sort(Sort.Direction.DESC, "createTime"));
        List<BondDiscoveryTodayDealDetailDoc> docList = mongoOperations.find(query, BondDiscoveryTodayDealDetailDoc.class);
        //docList = induAdapter.convList(docList, userId);
        return docList;
    }

    /**
     * 今日成交 -> 更新时间和笔数
     * @param doc
     * @param row
     */
    private void updateTodayDealRowItem(BondDiscoveryTodayDealDetailDoc doc, Object row) {
        if (row == null) return;
        String displayTenor = doc.getDisplayTenor();
        BigDecimal newPrice = doc.getPrice();
        try {
            Method method = row.getClass().getDeclaredMethod(String.format("getC%1$s", displayTenor));
            if (method != null) {
                BondTodayItem oldItem = (BondTodayItem) method.invoke(row);
                if (oldItem == null) {
                    oldItem = new BondTodayItem();
                    oldItem.setBondType(1); // 成交
                    oldItem.setUpdateTime(doc.getCreateTime());
                    oldItem.setMinPrice(newPrice);
                    oldItem.setMaxPrice(newPrice);
                    Method accessMethod = row.getClass().getDeclaredMethod(String.format("setC%1$s", displayTenor), BondTodayItem.class);
                    accessMethod.invoke(row, oldItem);
                }
                if (newPrice.compareTo(oldItem.getMinPrice()) == -1) {
                    // 小于原来最小值
                    oldItem.setMinPrice(newPrice);
                } else if (newPrice.compareTo(oldItem.getMaxPrice()) == 1) {
                    // 大于原来最大值
                    oldItem.setMaxPrice(newPrice);
                }
                oldItem.setCount(oldItem.getCount() + 1L);
                if (oldItem.getUpdateTime().getTime() < doc.getCreateTime().getTime()) {
                    oldItem.setUpdateTime(doc.getCreateTime());
                }
                //oldItem.getBondUniCodeList().add(doc.getBondUniCode());
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
    }

    /**
     * 今日成交 -> 生成用户点击记录
     * @param userId
     * @param ratingType
     * @param rating
     * @param tenor
     * @param currDate
     * @return
     */
    private BondDiscoveryTodayDealUserDoc getDealUserClickRecordDocRef(Long userId, Integer ratingType, String rating, String tenor, Date currDate) {
        BondDiscoveryTodayDealUserDoc doc = new BondDiscoveryTodayDealUserDoc();
        doc.setUserId(userId);
        doc.setRatingType(ratingType);
        doc.setRating(rating);
        doc.setTenor(tenor);
        doc.setClickTime(currDate);
        return doc;
    }

    /**
     * 今日成交 -> 按照DM量化评级，遍历集合，设置未读标识
     * @param userId
     * @param subRatingRowMap
     * @param rootRatingRowMap
     * @param redisUserPageTimeStr
     */
    private void assemblyDealDMItemReadStatus(Long userId, Map<String, BondTodayDMRow> subRatingRowMap,
                                          Map<String, BondTodayDMRow> rootRatingRowMap, String redisUserPageTimeStr) {
        if (StringUtils.isBlank(redisUserPageTimeStr)) {
            return;
        }
        Date userPageTime;
        try {
            userPageTime = SafeUtils.convertStringToDate(redisUserPageTimeStr, SafeUtils.TIMESTAMP_FORMAT);
        } catch (Exception ex) {
            return;
        }
        // 获取用户点击时间数据
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId).and("ratingType").is(1));
        List<BondDiscoveryTodayDealUserDoc> dealUserTimeDocList = mongoOperations.find(query, BondDiscoveryTodayDealUserDoc.class);
        // 遍历子集，比较时间
        dmSubRowNameList.stream().forEach(row -> {
            columnNameList.stream().forEach(column -> {
                BondTodayDMRow subRow = subRatingRowMap.get(row);
                BondTodayItem subItem = this.reflectBondTodayDMItem(subRow, column);
                BondDiscoveryTodayDealUserDoc userDealTimeDoc = dealUserTimeDocList.stream()
                        .filter(doc -> doc.getRating().equals(row) && doc.getTenor().equals(column)).findFirst().orElse(null);
                Date userCellTime = userDealTimeDoc != null ? userDealTimeDoc.getClickTime() : null;
                this.updateSubRootRowItem(row, column, subItem, rootRatingRowMap, userPageTime, userCellTime);
            });
        });
    }

    /**
     * 今日成交 -> 按照外部评级，遍历集合，设置未读标识
     * @param userId
     * @param ratingRowMap
     * @param redisUserPageTimeStr
     */
    private void assemblyDealExtItemReadStatus(Long userId, Map<String, BondTodayExtRow> ratingRowMap, String redisUserPageTimeStr) {
        if (StringUtils.isBlank(redisUserPageTimeStr)) {
            return;
        }
        Date userPageTime;
        try {
            userPageTime = SafeUtils.convertStringToDate(redisUserPageTimeStr, SafeUtils.TIMESTAMP_FORMAT);
        } catch (Exception ex) {
            return;
        }
        // 获取用户点击时间数据
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId).and("ratingType").is(2));
        List<BondDiscoveryTodayDealUserDoc> dealUserTimeDocList = mongoOperations.find(query, BondDiscoveryTodayDealUserDoc.class);
        // 遍历子集，比较时间
        extRowNameList.stream().forEach(row -> {
            columnNameList.stream().forEach(column -> {
                BondTodayExtRow rowItem = ratingRowMap.get(row);
                BondTodayItem cellItem = this.reflectBondTodayDMItem(rowItem, column);
                BondDiscoveryTodayDealUserDoc userDealTimeDoc = dealUserTimeDocList.stream()
                        .filter(doc -> doc.getRating().equals(row) && doc.getTenor().equals(column)).findFirst().orElse(null);
                Date userCellTime = userDealTimeDoc != null ? userDealTimeDoc.getClickTime() : null;
                this.updateSubRowItem(cellItem, userPageTime, userCellTime);
            });
        });
    }



    // ===========================================================
    // ========================= 今日报价 =========================
    // ===========================================================
    /**
     * 今日报价 -> DM量化评级数据
     * 用户首次访问，全部不产生未读红点标识
     * 1.用户访问时间redis仅保存130L，用户在130s后的请求，由于redis中已经没有记录了，跟首次访问没有区别
     * 2.用户在130s内有请求，会重置超时时间130L，已经记录的首次访问时间不变；再根据用户点击情况，生成未读红点标识
     *
     * @param req
     * @return
     */
    public BondTodayDMAggr getBondTodayQuoteDMRating(BondTodayFilterReq req) {
        Integer side = req.getSide();
        // 更新用户访问请求时间
        Date currDate = SafeUtils.getCurrentTime();
        String currDateStr = SafeUtils.convertDateToString(currDate, SafeUtils.TIMESTAMP_FORMAT);
        String redisKey = String.format("%1$s_%2$d", USER_QUOTE_PREFIX, req.getUserId());
        String redisUserPageTimeStr = redisMsgService.getMsgContent(redisKey);
        boolean isUserAccessTimePositive = false;
        if (StringUtils.isEmpty(redisUserPageTimeStr)) {
            // 用户当前首次访问, 130s失效
            redisMsgService.saveMsgWithTimeout(redisKey, currDateStr, EFFECTIVE_TIME);
        } else {
            // 有访问记录，重置失效时间
            redisMsgService.saveMsgWithTimeout(redisKey, redisUserPageTimeStr, EFFECTIVE_TIME);
            isUserAccessTimePositive = true;
        }
        // 生成空模板
        BondTodayDMAggr todayDMAggr = this.getTodayDMAggr();
        // 分解bondTodayDMAggr
        Map<String, BondTodayDMRow> subRatingRowMap = new HashMap<>();
        Map<String, BondTodayDMRow> rootRatingRowMap = new HashMap<>();
        todayDMAggr.getRootRowList().stream().forEach(rootRow -> {
            rootRatingRowMap.put(rootRow.getName(), rootRow);
                rootRow.getSubRowList().stream().forEach(subRow -> {
                    subRatingRowMap.put(subRow.getName(), subRow);
            });
        });
        // 取数据，并装配子集+父集
        List<BondDiscoveryTodayQuoteDetailDoc> docList = this.getQuoteFilterDetailDocList(req);
        docList.stream().forEach(doc -> {
            // 生成BondTodayDMItem
            String rating = doc.getDmRating();
            // 更新子集
            BondTodayDMRow subRow = subRatingRowMap.get(rating);
            this.updateTodayQuoteRowItem(doc, side, subRow);
            // 更新父集
            String rootKey = dmRatingSub2RootMap.get(rating);
            BondTodayDMRow rootRow = rootRatingRowMap.get(rootKey);
            this.updateTodayQuoteRowItem(doc, side, rootRow);
        });
        // 装配用户未读红点标识
        if (isUserAccessTimePositive) {
            this.assemblyQuoteDMItemReadStatus(req.getUserId(), side, subRatingRowMap, rootRatingRowMap, redisUserPageTimeStr);
        }
        return todayDMAggr;
    }

    /**
     * 今日报价 -> 外部评级数据
     * 用户首次访问，全部不产生未读红点标识
     * 1.用户访问时间redis仅保存130L，用户在130s后的请求，由于redis中已经没有记录了，跟首次访问没有区别
     * 2.用户在130s内有请求，会重置超时时间130L，已经记录的首次访问时间不变；再根据用户点击情况，生成未读红点标识
     *
     * @param req
     * @return
     */
    public BondTodayExtAggr getBondTodayQuoteExtRating(BondTodayFilterReq req) {
        Integer side = req.getSide();
        // 更新用户访问请求时间
        Date currDate = SafeUtils.getCurrentTime();
        String currDateStr = SafeUtils.convertDateToString(currDate, SafeUtils.TIMESTAMP_FORMAT);
        String redisKey = String.format("%1$s_%2$d", USER_QUOTE_PREFIX, req.getUserId());
        String redisUserPageTimeStr = redisMsgService.getMsgContent(redisKey);
        boolean isUserAccessTimePositive = false;
        if (StringUtils.isEmpty(redisUserPageTimeStr)) {
            // 用户当前首次访问, 130s失效
            redisMsgService.saveMsgWithTimeout(redisKey, currDateStr, EFFECTIVE_TIME);
        } else {
            // 有访问记录，重置失效时间
            redisMsgService.saveMsgWithTimeout(redisKey, redisUserPageTimeStr, EFFECTIVE_TIME);
            isUserAccessTimePositive = true;
        }
        // 生成空模板
        BondTodayExtAggr todayExtAggr = this.getTodayExtAggr();
        // 分解bondTodayExtAggr
        Map<String, BondTodayExtRow> ratingRowMap = new HashMap<>();
        todayExtAggr.getRowList().stream().forEach(rootRow -> {
            ratingRowMap.put(rootRow.getName(), rootRow);
        });
        // 取数据装配行集合
        List<BondDiscoveryTodayQuoteDetailDoc> docList = this.getQuoteFilterDetailDocList(req);
        docList.stream().forEach(doc -> {
            // 生成BondTodayExtItem
            String rating = doc.getExtRating();
            // 更新行集合
            BondTodayExtRow row = ratingRowMap.get(rating);
            this.updateTodayQuoteRowItem(doc, side, row);
        });
        // 装配用户未读红点标识
        if (isUserAccessTimePositive) {
            this.assemblyQuoteExtItemReadStatus(req.getUserId(), side, ratingRowMap, redisUserPageTimeStr);
        }
        return todayExtAggr;
    }

    /**
     * 今日报价 -> 获取单元格中的债券详情列表
     * @param req
     * @return
     */
    public Page<BondDiscoveryTodayQuoteDetailDoc> getBondTodayQuoteDetailDocList(BondTodayDetailReq req,
                                                                                 Integer page, Integer limit, String sort) {
        Sort customSort = this.generateConditionSort(sort);
        Date currDate = SafeUtils.getCurrentTime();
        // 1.参数合法性判断
        this.detailReqValidation(req);
        // 报价方向
        Integer quoteSide = req.getSide();
        if (quoteSide != 1 && quoteSide != 2) {
            throw new BusinessException(String.format("报价方向quoteSide[%1$d]不正确", quoteSide));
        }
        // 2.记录用户的点击单元格时间，用来标识未读红点
        this.saveQuoteUserClickRecord(req, currDate);
        // 3.返回该单元格中的债券详情列表
        Query query = this.getBondTodayDetailQuery(req);
        Long total = mongoOperations.count(query, BondDiscoveryTodayDealDetailDoc.class);

        PageRequest request = new PageRequest(page, limit, customSort);
        query.with(request);
        List<BondDiscoveryTodayQuoteDetailDoc> docList = mongoOperations.find(query, BondDiscoveryTodayQuoteDetailDoc.class);
        docList = induAdapter.convList(docList, req.getUserId());
        return new PageImpl<>(docList, new PageRequest(page, limit, null), total);
    }

    /**
     * 今日报价 -> 获取单元格中的债券详情列表
     * @param req
     * @return
     */
    public Page<BondDiscoveryTodayQuoteDetailDoc> getAllBondTodayQuoteDetailDocList(BondTodayDetailReq req,
                                                                                    Integer page, Integer limit, String sort) {
        Sort customSort = this.generateConditionSort(sort);
        // 根据side返回债券成交详情列表
        Query query = new Query();
        Criteria criteria = new Criteria();
        List<Criteria> subCriteriaList = new ArrayList<>();
        // 未勾选包含存单，过滤出部分数据；勾选了包含存单，为全部数据
        if (!req.getIsContainsNCD()) {
            subCriteriaList.add(Criteria.where("isNCD").is(false));
        }
        // 今日报价 -> 单边报价
        if (req.getIsUnilateralOffer() != null && req.getIsUnilateralOffer()) {
            subCriteriaList.add(Criteria.where("isUnilateralOffer").is(true));
        }
        // 今日报价 -> 方向:bid/offer
        Integer side = req.getSide();
        if (side != null) {
            subCriteriaList.add(Criteria.where("side").is(side));
        }
        if (req.getInduIds() != null && !req.getInduIds().isEmpty()) {
            // 选择了行业列表，过滤出部分数据
            Long userId = req.getUserId();
            Integer userInduClass = induService.findInduClassByUser(userId);
            String induPropertyName = induService.getInduIdByClass(userInduClass, userId);
            subCriteriaList.add(new CriteriaFactory<Integer>().generate(induPropertyName, req.getInduIds()));
        }
        if (!subCriteriaList.isEmpty()) {
            criteria.andOperator(subCriteriaList.toArray(new Criteria[subCriteriaList.size()]));
            query.addCriteria(criteria);
        }
        Long total = mongoOperations.count(query, BondDiscoveryTodayQuoteDetailDoc.class);

        PageRequest request = new PageRequest(page, limit, customSort);
        query.with(request);
        List<BondDiscoveryTodayQuoteDetailDoc> docList = mongoOperations.find(query, BondDiscoveryTodayQuoteDetailDoc.class);
        induAdapter.convList(docList, req.getUserId());
        return new PageImpl<>(docList, new PageRequest(page, limit, null), total);
    }

    /**
     * 今日报价 -> 记录用户点击Item时间
     * @param detailReq
     * @param currDate
     * @return
     */
    private void saveQuoteUserClickRecord(BondTodayDetailReq detailReq, Date currDate) {
        Long userId = detailReq.getUserId();
        Integer ratingType = detailReq.getRatingType();
        String rating = detailReq.getRating();
        String tenor = detailReq.getTenor();
        Integer side = detailReq.getSide();

        List<BondDiscoveryTodayQuoteUserDoc> docList = new ArrayList<>();
        List<String> tobeRemovedRatingList = new ArrayList<>();
        if (dmRootRowNameList.contains(rating)) {
            List<String> subRatingList = dmRatingRoot2SubMap.get(rating);
            subRatingList.stream().forEach(subRating -> {
                docList.add(this.getQuoteUserClickRecordDocRef(userId, ratingType, side, subRating, tenor, currDate));
                tobeRemovedRatingList.add(subRating);
            });
        } else {
            docList.add(this.getQuoteUserClickRecordDocRef(userId, ratingType, side, rating, tenor, currDate));
            tobeRemovedRatingList.add(rating);
        }
        // 先删除
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId).and("rating").in(tobeRemovedRatingList)
                .and("tenor").is(tenor).and("side").is(side));
        mongoOperations.remove(query, BondDiscoveryTodayQuoteUserDoc.class);
        // 再添加
        if (!docList.isEmpty()) {
            mongoOperations.insertAll(docList);
        }
    }

    /**
     * 今日报价 -> 根据过滤条件进行过滤成交数据
     * @param req
     * @return
     */
    private List<BondDiscoveryTodayQuoteDetailDoc> getQuoteFilterDetailDocList(BondTodayFilterReq req) {
        Long userId = req.getUserId();
        Integer userInduClass = induService.findInduClassByUser(userId);
        String induPropertyName = induService.getInduIdByClass(userInduClass, userId);
        List<Integer> induIdList = req.getInduIds();
        Query query = new Query();
        Criteria criteria = new Criteria();
        // 子约束条件
        List<Criteria> subCriteriaList = new ArrayList<>();
        subCriteriaList.add(Criteria.where("side").is(req.getSide()));
        // 未勾选包含存单，过滤出部分数据；勾选了包含存单，为全部数据
        if (req.getIsNCD() != null && !req.getIsNCD()) {
            subCriteriaList.add(Criteria.where("isNCD").is(false));
        }
        // 勾选了单边报价，过滤出部分数据
        if (req.getIsUnilateralOffer() != null && req.getIsUnilateralOffer()) {
            subCriteriaList.add(Criteria.where("isUnilateralOffer").is(true));
        }
        // 选择了行业列表，过滤出部分数据
        if (!induIdList.isEmpty()) {
            subCriteriaList.add(new CriteriaFactory<Integer>().generate(induPropertyName, induIdList));
        }
        criteria.andOperator(subCriteriaList.toArray(new Criteria[subCriteriaList.size()]));
        query.addCriteria(criteria);
        List<BondDiscoveryTodayQuoteDetailDoc> docList = mongoOperations.find(query, BondDiscoveryTodayQuoteDetailDoc.class);
        //docList = induAdapter.convList(docList, userId);
        return docList;
    }

    /**
     * 今日报价 -> 按照DM量化评级，遍历集合，设置未读标识
     * @param userId
     * @param side
     * @param subRatingRowMap
     * @param rootRatingRowMap
     * @param redisUserPageTimeStr
     */
    private void assemblyQuoteDMItemReadStatus(Long userId, Integer side, Map<String, BondTodayDMRow> subRatingRowMap,
                                              Map<String, BondTodayDMRow> rootRatingRowMap, String redisUserPageTimeStr) {
        if (StringUtils.isBlank(redisUserPageTimeStr)) {
            return;
        }
        Date userPageTime;
        try {
            userPageTime = SafeUtils.convertStringToDate(redisUserPageTimeStr, SafeUtils.TIMESTAMP_FORMAT);
        } catch (Exception ex) {
            return;
        }
        // 获取用户点击时间数据
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId).and("side").is(side).and("ratingType").is(1));
        List<BondDiscoveryTodayQuoteUserDoc> quoteUserTimeDocList = mongoOperations.find(query, BondDiscoveryTodayQuoteUserDoc.class);
        // 遍历子集，比较时间
        dmSubRowNameList.stream().forEach(row -> {
            columnNameList.stream().forEach(column -> {
                BondTodayDMRow subRow = subRatingRowMap.get(row);
                BondTodayItem subItem = this.reflectBondTodayDMItem(subRow, column);
                BondDiscoveryTodayQuoteUserDoc userQuoteTimeDoc = quoteUserTimeDocList.stream()
                        .filter(doc -> doc.getRating().equals(row) && doc.getTenor().equals(column)).findFirst().orElse(null);
                Date userCellTime = userQuoteTimeDoc != null ? userQuoteTimeDoc.getClickTime() : null;
                this.updateSubRootRowItem(row, column, subItem, rootRatingRowMap, userPageTime, userCellTime);
            });
        });
    }

    /**
     * 今日报价 -> 按照外部评级，遍历集合，设置未读标识
     * @param userId
     * @param side
     * @param ratingRowMap
     * @param redisUserPageTimeStr
     */
    private void assemblyQuoteExtItemReadStatus(Long userId, Integer side, Map<String, BondTodayExtRow> ratingRowMap, String redisUserPageTimeStr) {
        if (StringUtils.isBlank(redisUserPageTimeStr)) {
            return;
        }
        Date userPageTime;
        try {
            userPageTime = SafeUtils.convertStringToDate(redisUserPageTimeStr, SafeUtils.TIMESTAMP_FORMAT);
        } catch (Exception ex) {
            return;
        }
        // 获取用户点击时间数据
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId).and("side").is(side).and("ratingType").is(2));
        List<BondDiscoveryTodayQuoteUserDoc> quoteUserTimeDocList = mongoOperations.find(query, BondDiscoveryTodayQuoteUserDoc.class);
        // 遍历子集，比较时间
        extRowNameList.stream().forEach(row -> {
            columnNameList.stream().forEach(column -> {
                BondTodayExtRow rowItem = ratingRowMap.get(row);
                BondTodayItem cellItem = this.reflectBondTodayDMItem(rowItem, column);
                BondDiscoveryTodayQuoteUserDoc userQuoteTimeDoc = quoteUserTimeDocList.stream()
                        .filter(doc -> doc.getRating().equals(row) && doc.getTenor().equals(column)).findFirst().orElse(null);
                Date userCellTime = userQuoteTimeDoc != null ? userQuoteTimeDoc.getClickTime() : null;
                this.updateSubRowItem(cellItem, userPageTime, userCellTime);
            });
        });
    }

    /**
     * 今日报价 -> 更新row和item，展示时间最新的一个价格
     * @param doc
     * @param row
     */
    private void updateTodayQuoteRowItem(BondDiscoveryTodayQuoteDetailDoc doc, Integer side, Object row) {
        if (row == null) return;
        String displayTenor = doc.getDisplayTenor();
        try {
            Method method = row.getClass().getDeclaredMethod(String.format("getC%1$s", displayTenor));
            if (method != null) {
                BondTodayItem oldItem = (BondTodayItem) method.invoke(row);
                if (oldItem == null) {
                    oldItem = new BondTodayItem();
                    oldItem.setBondType(2); // 报价
                    oldItem.setQuotePrice(doc.getBondPrice());
                    oldItem.setShortName(doc.getShortName());
                    oldItem.setUpdateTime(doc.getCreateTime());
                    Method accessMethod = row.getClass().getDeclaredMethod(String.format("setC%1$s", displayTenor), BondTodayItem.class);
                    accessMethod.invoke(row, oldItem);
                }
                if (doc.getCreateTime().getTime() > oldItem.getUpdateTime().getTime()) {
                    // 最新的时间
                    oldItem.setQuotePrice(doc.getBondPrice());
                    oldItem.setShortName(doc.getShortName());
                    oldItem.setUpdateTime(doc.getCreateTime());
                }
                //oldItem.getBondUniCodeList().add(doc.getBondUniCode());
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
    }

    private BondDiscoveryTodayQuoteUserDoc getQuoteUserClickRecordDocRef(Long userId, Integer ratingType, Integer side, String rating, String tenor, Date currDate) {
        BondDiscoveryTodayQuoteUserDoc doc = new BondDiscoveryTodayQuoteUserDoc();
        doc.setUserId(userId);
        doc.setRatingType(ratingType);
        doc.setSide(side);
        doc.setRating(rating);
        doc.setTenor(tenor);
        doc.setClickTime(currDate);
        return doc;
    }



    // ===========================================================
    // ========================= 共用方法 =========================
    // ===========================================================

    private BondTodayItem reflectBondTodayDMItem(Object sourceRow, String tenor) {
        BondTodayItem result = null;
        try {
            Method method = sourceRow.getClass().getDeclaredMethod(String.format("getC%1$s", tenor));
            if (method != null) {
                result = (BondTodayItem) method.invoke(sourceRow);
                if (result == null) {
                    result = new BondTodayItem();
                    Method accessMethod = sourceRow.getClass().getDeclaredMethod(String.format("setC%1$s", tenor), BondTodayItem.class);
                    accessMethod.invoke(sourceRow, result);
                }
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
        return result;
    }

    private BondTodayDMAggr getTodayDMAggr() {
        // 初始化bondTodayDMAggr
        BondTodayDMAggr result = new BondTodayDMAggr();
        dmRatingRoot2SubMap.keySet().stream().forEach(rootName -> {
            // 根节点
            BondTodayDMRow rootRow = new BondTodayDMRow();
            rootRow.setIsRoot(true);
            rootRow.setName(rootName);
            // 子节点
            List<BondTodayDMRow> subRowList = new ArrayList<>();
            dmRatingRoot2SubMap.get(rootName).stream().forEach(subName -> {
                BondTodayDMRow subRow = new BondTodayDMRow();
                subRow.setIsRoot(false);
                subRow.setName(subName);
                subRowList.add(subRow);
            });
            rootRow.setSubRowList(subRowList);
            result.getRootRowList().add(rootRow);
        });
        return result;
    }

    private BondTodayExtAggr getTodayExtAggr() {
        // 初始化BondTodayExtAggr
        BondTodayExtAggr result = new BondTodayExtAggr();
        extRowNameList.stream().forEach(rowName -> {
            BondTodayExtRow row = new BondTodayExtRow();
            row.setName(rowName);
            result.getRowList().add(row);
        });
        return result;
    }

    /**
     * 更新子集和父集单元格的未读红点标识
     * @param row
     * @param column
     * @param subItem
     * @param rootRatingRowMap
     * @param userPageTime
     * @param userCellTime
     */
    private void updateSubRootRowItem(String row, String column, BondTodayItem subItem,
                                      Map<String, BondTodayDMRow> rootRatingRowMap, Date userPageTime, Date userCellTime) {
        if (subItem != null && subItem.getUpdateTime() != null) {
            long itemTime = subItem.getUpdateTime().getTime();
            if (userCellTime != null) {
                if (itemTime > userPageTime.getTime() && itemTime > userCellTime.getTime()) {
                    // 未读标识
                    subItem.setIsRead(false);
                    // 标红父集
                    BondTodayDMRow rootRow = rootRatingRowMap.get(dmRatingSub2RootMap.get(row));
                    BondTodayItem rootItem = this.reflectBondTodayDMItem(rootRow, column);
                    rootItem.setIsRead(false);
                }
            } else {
                if (itemTime > userPageTime.getTime()) {
                    subItem.setIsRead(false);
                    // 标红父集
                    BondTodayDMRow rootRow = rootRatingRowMap.get(dmRatingSub2RootMap.get(row));
                    BondTodayItem rootItem = this.reflectBondTodayDMItem(rootRow, column);
                    rootItem.setIsRead(false);
                }
            }
        }
    }

    /**
     * 更新子集单元格的未读红点标识
     * @param cellItem
     * @param userPageTime
     * @param userCellTime
     */
    private void updateSubRowItem(BondTodayItem cellItem, Date userPageTime, Date userCellTime) {
        if (cellItem != null && cellItem.getUpdateTime() != null) {
            long itemTime = cellItem.getUpdateTime().getTime();
            if (userCellTime != null && (itemTime > userPageTime.getTime() && itemTime > userCellTime.getTime())) {
                cellItem.setIsRead(false);
            } else if (userCellTime == null && itemTime > userPageTime.getTime()) {
                cellItem.setIsRead(false);
            }
        }
    }

    /**
     * 单元格参数合法性验证
     * @param req
     */
    private void detailReqValidation(BondTodayDetailReq req) {
        Integer ratingType = req.getRatingType();
        String rating = req.getRating();
        String tenor = req.getTenor();
        if (ratingType != 1 && ratingType != 2) {
            throw new BusinessException(String.format("类型ratingType[%1$d]不正确", ratingType));
        } else if (ratingType == 1 && (!dmRootRowNameList.contains(rating) && !dmSubRowNameList.contains(rating))) {
            // DM量化评级，rating既不是root也不是sub
            throw new BusinessException(String.format("DM量化评级rating[%1$s]不正确", rating));
        } else if (ratingType == 2 && !extRowNameList.contains(rating)) {
            throw new BusinessException(String.format("外部评级rating[%1$s]不正确", rating));
        } else if (!columnNameList.contains(tenor)) {
            throw new BusinessException(String.format("期限tenor[%1$s]不正确", tenor));
        }
    }

    private Query getBondTodayDetailQuery(BondTodayDetailReq req) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        // 子约束条件
        List<Criteria> subCriteriaList = new ArrayList<>();
        // 未勾选包含存单，过滤出部分数据；勾选了包含存单，为全部数据
        if (!req.getIsContainsNCD()) {
            subCriteriaList.add(Criteria.where("isNCD").is(false));
        }
        // 今日报价 -> 单边报价
        if (req.getIsUnilateralOffer() != null && req.getIsUnilateralOffer()) {
            subCriteriaList.add(Criteria.where("isUnilateralOffer").is(true));
        }
        // 评级
        if (req.getRatingType() == 1) {
            // DM量化评级
            String ratingName = req.getRating();
            if (StringUtils.isNotBlank(ratingName)) {
                List<String> ratingNameList = dmSubRowNameList.contains(ratingName) ? Arrays.asList(ratingName)
                        : dmRatingRoot2SubMap.get(ratingName);
                subCriteriaList.add(Criteria.where("dmRating").in(ratingNameList).and("displayTenor").is(req.getTenor()));
            }
        } else {
            // 外部评级
            String ratingName = req.getRating();
            if (StringUtils.isNotBlank(ratingName)) {
                if (extFormalRowNameList.contains(ratingName)) {
                    subCriteriaList.add(Criteria.where("extRating").is(ratingName).and("displayTenor").is(req.getTenor()));
                } else {
                    subCriteriaList.add(Criteria.where("extRating").nin(extFormalRowNameList).and("displayTenor").is(req.getTenor()));
                }
            }
        }
        if (req.getInduIds() != null && !req.getInduIds().isEmpty()) {
            // 选择了行业列表，过滤出部分数据
            Long userId = req.getUserId();
            Integer userInduClass = induService.findInduClassByUser(userId);
            String induPropertyName = induService.getInduIdByClass(userInduClass, userId);
            subCriteriaList.add(new CriteriaFactory<Integer>().generate(induPropertyName, req.getInduIds()));
        }
        // 今日报价 -> 方向:bid/offer
        Integer side = req.getSide();
        if (side != null) {
            subCriteriaList.add(Criteria.where("side").is(side));
        }
        criteria.andOperator(subCriteriaList.toArray(new Criteria[subCriteriaList.size()]));
        // createTime倒序
        query.addCriteria(criteria).with(new Sort(Sort.Direction.DESC, "createTime"));
        return query;
    }



    // =================================================================
    // ========================= WebSocket交互 =========================
    // =================================================================

    /**
     * WebSocket心跳，用于更新该用户的pageTime
     * @param socketDTO
     * @return
     */
    public boolean discoveryTodayHeartBeat(SocketDiscoveryUserMessageDTO socketDTO) {
        // 当前时间，用于记录用户首次访问
        if (socketDTO == null || socketDTO.getDiscoveryType() == null
                || (socketDTO.getDiscoveryType() != 1 && socketDTO.getDiscoveryType() != 2)) {
            return false;
        }
        Date currDate = SafeUtils.getCurrentTime();
        String currDateStr = SafeUtils.convertDateToString(currDate, SafeUtils.TIMESTAMP_FORMAT);
        // 根据心跳类型，记录或者更新失效时间
        String prefix = socketDTO.getDiscoveryType() == 1 ? USER_DEAL_PREFIX : USER_QUOTE_PREFIX;
        String redisKey = String.format("%1$s_%2$d", prefix, socketDTO.getUserId());
        String redisUserPageTimeStr = redisMsgService.getMsgContent(redisKey);
        if (StringUtils.isEmpty(redisUserPageTimeStr)) {
            // 用户当前首次访问, 130s失效
            redisMsgService.saveMsgWithTimeout(redisKey, currDateStr, EFFECTIVE_TIME);
        } else {
            // 有访问记录，重置失效时间
            redisMsgService.saveMsgWithTimeout(redisKey, redisUserPageTimeStr, EFFECTIVE_TIME);
        }
        return true;
    }


    // ============================================================
    // ========================= 异常价格 =========================
    // ============================================================

    public Page<Object> getAbnormalPriceList(BondAbnormalPriceFilterReq req, Integer page, Integer limit, String sort) {
        this.checkAbnormalPriceParameters(req);
        Sort customSort = this.generateConditionSort(sort);
        Date startDate;
        Date endDate;
        String startDateStr = req.getStartDateStr();
        String endDateStr = req.getEndDateStr();
        try {
            startDate = SafeUtils.convertStringToDate(startDateStr + " 00:00:00", SafeUtils.DATE_TIME_FORMAT1);
            endDate = SafeUtils.convertStringToDate(endDateStr + " 23:59:59", SafeUtils.DATE_TIME_FORMAT1);
        } catch (Exception ex) {
            LOG.error(String.format("getAbnormalPriceList error: invalid startDate[%1$s] and endDate[%2$s]",
                    startDateStr, endDateStr));
            return null;
        }
        boolean isCrossYear = startDate.getYear() != endDate.getYear();
        String targetDateFmt = isCrossYear ? SafeUtils.DATE_TIME_FORMAT5 : SafeUtils.DATE_TIME_FORMAT4;

        Integer type = req.getType();
        Integer deviationType = req.getDeviationType();
        Integer sourceType = req.getSourceType();
        Integer deviationDirection = req.getDeviationDirection();
        Double deviationFilterValue = req.getDeviationFilterValue();
        Integer netPriceDirection = req.getNetPriceDirection();
        Double netPriceFilterValue = req.getNetPriceFilterValue();
        String bondFilterValue = req.getBondFilterValue();
        Integer tenorDay = req.getTenorDays();
        Boolean isContainsNCD = req.getIsContainsNCD();

        // 过滤条件
        Query query = new Query();
        Criteria criteria = new Criteria();
        List<Criteria> subCriteriaList = new ArrayList<>();
        if (!isContainsNCD) { // 存单限制
            subCriteriaList.add(Criteria.where("isNCD").is(isContainsNCD));
        }
        // 净价值/偏离值过滤
        Criteria priceCriteria = this.getPriceCriteria(type, deviationType, sourceType,
                netPriceDirection, netPriceFilterValue, deviationDirection, deviationFilterValue);
        if (priceCriteria != null) {
            subCriteriaList.add(priceCriteria);
        }
        // 来源限制,BondDiscoveryNetPriceEnum
        Map<Integer, String> sourceNameMap = Arrays.stream(BondDiscoveryNetPriceEnum.values())
                .collect(Collectors.toMap(BondDiscoveryNetPriceEnum::getCode, BondDiscoveryNetPriceEnum::getValue));
        if (sourceNameMap != null && sourceNameMap.containsKey(sourceType)) {
            subCriteriaList.add(Criteria.where(sourceNameMap.get(sourceType)).exists(true));
        }
        // 日期限制
        subCriteriaList.add(Criteria.where("pubDate").gte(startDate).lte(endDate));
        // 剩余限制
        if (tenorDay != null) {
        	subCriteriaList.add(Criteria.where("tenorDays").gte(req.getTenorDays()));
		}
        if (type == 2) { // 估值偏离
            if (StringUtils.isNotBlank(bondFilterValue)) { // 债项简称/代码限制
                subCriteriaList.add(new Criteria().orOperator(Criteria.where("shortName").regex("^.*" + bondFilterValue + ".*$"),
                        Criteria.where("code").regex("^.*" + bondFilterValue + ".*$")));
            }
        }
        if (!subCriteriaList.isEmpty()) { // 合并限制条件
            criteria.andOperator(subCriteriaList.toArray(new Criteria[subCriteriaList.size()]));
            query = new Query(criteria);
        }
        long total = 0L;
        long underEstimateCount = 0L;
        long upValuationCount = 0L;
        List<Object> resultList = new ArrayList<>();
        if (sourceType == 1) { // 来源：成交价
            // 复用query
            total = mongoOperations.count(query, BondDiscoveryAbnormalDealDoc.class);
            if (type == 2 && total > 0L) { // 估值偏离，能查询到数据集时，统计高估和低估的条数
                List<BondDiscoveryAbnormalDealDoc> totalDealList = mongoOperations.find(query, BondDiscoveryAbnormalDealDoc.class);
                underEstimateCount = totalDealList.stream()
                        .filter(deal -> deal.getDeviationDirection() != null && deal.getDeviationDirection() == 1).count(); // 低估
                upValuationCount = totalDealList.stream()
                        .filter(deal -> deal.getDeviationDirection() != null && deal.getDeviationDirection() == 2).count(); // 高估
            }
            PageRequest request = new PageRequest(page, limit, customSort);
            List<BondDiscoveryAbnormalDealDoc> dealList = mongoOperations.find(query.with(request), BondDiscoveryAbnormalDealDoc.class);
            if (dealList != null && !dealList.isEmpty()) {
                if (total > 0L) {
                    dealList.get(0).setUnderStati(underEstimateCount);
                    dealList.get(0).setOverStati(upValuationCount);
                }
                // 转换
                dealList.stream().forEach(deal -> {
                	// 净价偏离，保留四位小数
                    deal.setNetPriceDeviation(this.getScaledDouble(deal.getNetPriceDeviation()));
                    deal.setDealNetPrice(this.getScaledDouble(deal.getDealNetPrice()));
                    deal.setDisplayPubDate(SafeUtils.convertDateToString(deal.getPubDate(), targetDateFmt));
                    resultList.add(deal);
                });
            }
        } else if (Arrays.asList(2, 3).contains(sourceType)) { // 来源：报价
            total = mongoOperations.count(query, BondDiscoveryAbnormalQuoteDoc.class);
            // 复用query
            if (type == 2 && total > 0L) { // 估值偏离，能查询到数据集时，统计高估和低估的条数
                List<BondDiscoveryAbnormalQuoteDoc> totalQuoteList = mongoOperations.find(query, BondDiscoveryAbnormalQuoteDoc.class);
                underEstimateCount = totalQuoteList.stream()
                        .filter(quote -> quote.getDeviationDirection() != null && quote.getDeviationDirection() == 1).count();
                upValuationCount = totalQuoteList.stream()
                        .filter(quote -> quote.getDeviationDirection() != null && quote.getDeviationDirection() == 2).count();
            }
            PageRequest request = new PageRequest(page, limit, customSort);
            List<BondDiscoveryAbnormalQuoteDoc> quoteList = mongoOperations.find(query.with(request), BondDiscoveryAbnormalQuoteDoc.class);
            if (quoteList != null && !quoteList.isEmpty()) {
                if (total > 0L) {
                    quoteList.get(0).setUnderStati(underEstimateCount);
                    quoteList.get(0).setOverStati(upValuationCount);
                }
                // 转换
                quoteList.stream().forEach(quote -> {
                    quote.setDisplayPubDate(SafeUtils.convertDateToString(quote.getPubDate(), targetDateFmt));
                    quote.setBidNetPrice(this.getScaledDouble(quote.getBidNetPrice()));
                    quote.setBidPriceYield(this.getScaledDouble(quote.getBidPriceYield()));
                    quote.setOfrNetPrice(this.getScaledDouble(quote.getOfrNetPrice()));
                    quote.setOfrPriceYield(this.getScaledDouble(quote.getOfrPriceYield()));
                    resultList.add(quote);
                });
            }
        }
        
        return new PageImpl<>(induAdapter.convList(resultList, req.getUserId()), new PageRequest(page, limit, null), total);
    }

    private void checkAbnormalPriceParameters(BondAbnormalPriceFilterReq req) {
        Integer type = req.getType();
        Integer sourceType = req.getSourceType();
        Integer netPriceDirection = req.getNetPriceDirection();
        Integer deviationType = req.getDeviationType();
        Integer deviationDirection = req.getDeviationDirection();
        if (!Arrays.asList(1, 2).contains(type)) {
            throw new BusinessException(String.format("价格异常类型type[%1$d]不正确", type));
        } else if (!Arrays.asList(1, 2, 3).contains(sourceType)) {
            throw new BusinessException(String.format("来源sourceType[%1$d]不正确", sourceType));
        } else if (type == 1) { // 高收益债
            if (!Arrays.asList(1,2).contains(netPriceDirection)) {
                throw new BusinessException(String.format("净价方向netPriceDirection[%1$d]不正确", netPriceDirection));
            }
        } else if (type == 2) { // 估值偏离
            if (!Arrays.asList(1, 2).contains(deviationType)) {
                throw new BusinessException(String.format("偏离类型deviationType[%1$d]不正确", deviationType));
            } else if (!Arrays.asList(0, 1, 2).contains(deviationDirection)) {
                throw new BusinessException(String.format("偏离方向deviationDirection[%1$d]不正确", deviationDirection));
            }
        }
    }

    /**
     * 生成偏离过滤条件, 不涉及来源过滤
     * @param type 价格异常类型:1-高收益债;2-估值偏离
     * @param deviationType 偏离类型:1-收益率;2-净价
     * @param sourceType 来源:1-成交价;2-买入报价Bid;3-卖出报价Ofr
     * @param netPriceDirection 净价方向:1-小于等于;2-大于等于
     * @param netPriceFilterValue 净价值
     * @param deviationDirection 偏离方向:0-全部;1-低估;2-高估
     * @param deviationFilterValue 偏离值
     * @return
     */
    private Criteria getPriceCriteria(Integer type, Integer deviationType, Integer sourceType,
                                    Integer netPriceDirection, Double netPriceFilterValue,
                                    Integer deviationDirection, Double deviationFilterValue) {
        Criteria priceCriteria = new Criteria();
        String targetName;
        if (type == 1) { // 高收益债
            if (Arrays.asList(1, 2, 3).contains(sourceType)) {
                targetName = "convertNetPrice";
                if (netPriceFilterValue != null) {
                    if (netPriceDirection == 1) {
                        priceCriteria = Criteria.where(targetName).lte(netPriceFilterValue);
                    } else if (netPriceDirection == 2) {
                        priceCriteria = Criteria.where(targetName).gte(netPriceFilterValue);
                    }
                }
            }
        } else if (type == 2) { // 估值偏离
            if (deviationFilterValue != null) { // 有偏离值
                if (deviationType == 1) { // 收益率
                    targetName = "valuationDeviation";
                    if (deviationDirection == 0) { // 全部, <= -40BP && >= 40BP
                        priceCriteria = new Criteria().orOperator(Criteria.where(targetName).lte(-deviationFilterValue),
                                Criteria.where(targetName).gte(deviationFilterValue));
                    } else if (deviationDirection == 1) { // 低估, >= 40BP
                        priceCriteria = Criteria.where(targetName).gte(deviationFilterValue)
                                .and("deviationDirection").is(deviationDirection);
                    } else if (deviationDirection == 2) { // 高估,<= -40BP
                        priceCriteria = Criteria.where(targetName).lte(-deviationFilterValue)
                                .and("deviationDirection").is(deviationDirection);
                    }
                } else if (deviationType == 2) { // 净价
                    targetName = "netPriceDeviation";
                    if (deviationDirection == 0) { // 全部, <= -0.4元 && >= 0.4元
                        priceCriteria = new Criteria().orOperator(Criteria.where(targetName).lte(-deviationFilterValue),
                                Criteria.where(targetName).gte(deviationFilterValue));
                    } else if (deviationDirection == 1) { // 低估, <= -0.4元
                        priceCriteria = Criteria.where(targetName).lte(-deviationFilterValue)
                                .and("deviationDirection").is(deviationDirection);
                    } else if (deviationDirection == 2) { // 高估,>= 0.4元
                        priceCriteria = Criteria.where(targetName).gte(deviationFilterValue)
                                .and("deviationDirection").is(deviationDirection);
                    }
                }
            } else { // 没有偏离值
                if (deviationDirection == 0) { // 全部, 不设限制条件
                    priceCriteria = null;
                } else { // 低估/高估
                    priceCriteria = Criteria.where("deviationDirection").is(deviationDirection);
                }
            }
        }
        return priceCriteria;
    }

    /**
     * 获取单支债券成交价+报价的历史价格/收益率信息
     * @param req
     * @return
     */
    public BondAbnormalPriceHistoryVO getAbnormalPriceHistoryList(BondAbnormalPriceHistoryReq req) {
//        Date startDate;
//        Date endDate;
//        String startDateStr = req.getStartDateStr()+" 00:00:00";
//        String endDateStr = req.getEndDateStr()+" 23:59:59";
//        try {
//            startDate = SafeUtils.convertStringToDate(startDateStr, SafeUtils.DATE_TIME_FORMAT1);
//            endDate = SafeUtils.convertStringToDate(endDateStr, SafeUtils.DATE_TIME_FORMAT1);
//        } catch (Exception ex) {
//            LOG.error("getAbnormalPriceHistoryList error: invalid startDate[{}] and endDate[{}], error[{}]",
//                    startDateStr, endDateStr, ex.getMessage());
//            throw new BusinessException(String.format("非法日期格式 start[%1$s] end[%2$s]", startDateStr, endDateStr));
//        }
        Long bondUniCode = req.getBondUniCode();
        BondAbnormalPriceHistoryVO result = new BondAbnormalPriceHistoryVO();
        // 债券信息
        Query bondQuery = new Query(Criteria.where("bondUniCode").is(bondUniCode));
        BondDetailDoc detailDoc = mongoOperations.findOne(bondQuery, BondDetailDoc.class);
        if (detailDoc == null) {
            LOG.error("getAbnormalPriceHistoryList error: cannot find detailDoc with bondUniCode[{}]", bondUniCode);
            throw new BusinessException(String.format("债券[%1$d]找不到对应的BondDetailDoc", bondUniCode));
        }
        BeanUtils.copyProperties(detailDoc, result);
        result.setShortName(detailDoc.getName());
//        Query resultQuery = new Query(Criteria.where("bondUniCode").is(bondUniCode)
//                .and("pubDate").gte(startDate).lte(endDate));
        Query resultQuery = new Query(Criteria.where("bondUniCode").is(bondUniCode));
        List<BondDiscoveryAbnormalDealDoc> dealDocList = mongoOperations.find(resultQuery, BondDiscoveryAbnormalDealDoc.class);
        // 按照成交价日期的day分类,yyyy-MM-dd
        Map<String, List<BondDiscoveryAbnormalDealDoc>> day2DPDealListMap = dealDocList.stream()
                .collect(Collectors.groupingBy(deal -> SafeUtils.convertDateToString(deal.getPubDate(), SafeUtils.DATE_FORMAT)));
        List<String> dealDateList = new ArrayList<>(day2DPDealListMap.keySet());
        if (req.getType() == 1 && !dealDocList.isEmpty()) { // 高收益债->历史价格
        	dealDateList = dealDateList.stream().sorted().collect(Collectors.toList());
            result.setDateArr(dealDateList.toArray(new String[dealDateList.size()]));
            List<Double> dealPriceYieldList = new ArrayList<>(); // 成交价收益率列表
            dealDateList.stream().forEach(date -> {
                BondDiscoveryAbnormalDealDoc tailDealDoc = day2DPDealListMap.get(date).stream().reduce((a, b) -> b).get();
                Double dealPriceYield = tailDealDoc.getDealPriceYield();
                dealPriceYieldList.add(dealPriceYield != null ? dealPriceYield : null);
            });
            result.setDealPriceYieldArr(dealPriceYieldList.toArray(new Double[dealPriceYieldList.size()]));
        } else if (req.getType() == 2) { // 估值偏离
            List<Double> dealNetPriceList = new ArrayList<>();
            List<Double> dealPriceYieldList = new ArrayList<>();
            List<Integer> dealCountList = new ArrayList<>();

            List<Double> valuationNetPriceList = new ArrayList<>();
            List<Double> valuationYieldList = new ArrayList<>();

            List<Double> bidNetPriceList = new ArrayList<>();
            List<Double> bidPriceYieldList = new ArrayList<>();
            List<Integer> bidCountList = new ArrayList<>();
            List<Double> ofrPriceYieldList = new ArrayList<>();
            List<Double> ofrNetPriceList = new ArrayList<>();
            List<Integer> ofrCountList = new ArrayList<>();

            List<BondDiscoveryAbnormalQuoteDoc> quoteDocList = mongoOperations.find(resultQuery, BondDiscoveryAbnormalQuoteDoc.class);
            Map<String, List<BondDiscoveryAbnormalQuoteDoc>> day2DPQuoteListMap = quoteDocList.stream()
                    .collect(Collectors.groupingBy(quote -> SafeUtils.convertDateToString(quote.getPubDate(), SafeUtils.DATE_FORMAT)));
            // 生成一个按照时间排序的list
            List<String> allDayStringList = Stream.concat(dealDocList.stream().map(BondDiscoveryAbnormalDealDoc::getPubDate),
                    quoteDocList.stream().map(BondDiscoveryAbnormalQuoteDoc::getPubDate))
                    .sorted().map(date -> SafeUtils.convertDateToString(date, SafeUtils.DATE_FORMAT))
                    .distinct().collect(Collectors.toList());
            result.setDateArr(allDayStringList.toArray(new String[allDayStringList.size()]));
            allDayStringList.stream().forEach(day -> {
                // 两个成交价Array
                List<BondDiscoveryAbnormalDealDoc> currDealDocList = day2DPDealListMap.getOrDefault(day, new ArrayList<>());
                BondDiscoveryAbnormalDealDoc tailDealDoc = currDealDocList.isEmpty() ? null
                        : currDealDocList.get(currDealDocList.size() - 1);
                dealNetPriceList.add(tailDealDoc == null ? null : this.getScaledDouble(tailDealDoc.getDealNetPrice()));
                dealPriceYieldList.add(tailDealDoc == null ? null : this.getScaledDouble(tailDealDoc.getDealPriceYield()));
                dealCountList.add(currDealDocList.size());
                // 四个报价Array
                List<BondDiscoveryAbnormalQuoteDoc> currQuoteDocList = day2DPQuoteListMap.getOrDefault(day, new ArrayList<>());
                BondDiscoveryAbnormalQuoteDoc tailQuoteDoc = currQuoteDocList.isEmpty() ? null
                        : currQuoteDocList.get(currQuoteDocList.size() - 1);
                bidNetPriceList.add(tailQuoteDoc == null ? null : this.getScaledDouble(tailQuoteDoc.getBidNetPrice()));
                bidPriceYieldList.add(tailQuoteDoc == null ? null : this.getScaledDouble(tailQuoteDoc.getBidPriceYield()));
                long bidCount = currQuoteDocList.stream().filter(quote -> quote.getSide() == 1)
                        .mapToInt(BondDiscoveryAbnormalQuoteDoc::getSide).count();
                bidCountList.add(SafeUtils.getInteger(bidCount));
                // Ofr
                ofrNetPriceList.add(tailQuoteDoc == null ? null : this.getScaledDouble(tailQuoteDoc.getOfrNetPrice()));
                ofrPriceYieldList.add(tailQuoteDoc == null ? null : this.getScaledDouble(tailQuoteDoc.getOfrPriceYield()));
                long ofrCount = currQuoteDocList.stream().filter(quote -> quote.getSide() == 2)
                        .mapToInt(BondDiscoveryAbnormalQuoteDoc::getSide).count();
                ofrCountList.add(SafeUtils.getInteger(ofrCount));
                // 两个重叠Array
                if ((tailDealDoc == null && tailQuoteDoc != null)
                        || (tailDealDoc != null && tailQuoteDoc != null && tailDealDoc.getPubDate().getTime() <= tailQuoteDoc.getPubDate().getTime())) {
                    valuationNetPriceList.add(this.getScaledDouble(tailQuoteDoc.getValuationNetPrice()));
                    valuationYieldList.add(this.getScaledDouble(tailQuoteDoc.getValuationYield()));
                } else if ((tailDealDoc != null && tailQuoteDoc == null)
                        || (tailDealDoc != null && tailQuoteDoc != null && tailDealDoc.getPubDate().getTime() >= tailQuoteDoc.getPubDate().getTime())) {
                    valuationNetPriceList.add(this.getScaledDouble(tailDealDoc.getValuationNetPrice()));
                    valuationYieldList.add(this.getScaledDouble(tailDealDoc.getValuationYield()));
                } else {
                    valuationNetPriceList.add(null);
                    valuationYieldList.add(null);
                }
            });
            result.setDealNetPriceArr(dealNetPriceList.toArray(new Double[dealNetPriceList.size()]));
            result.setDealPriceYieldArr(dealPriceYieldList.toArray(new Double[dealPriceYieldList.size()]));
            result.setDealCountArr(dealCountList.toArray(new Integer[dealCountList.size()]));
            result.setValuationNetPriceArr(valuationNetPriceList.toArray(new Double[valuationNetPriceList.size()]));
            result.setValuationYieldArr(valuationYieldList.toArray(new Double[valuationYieldList.size()]));
            result.setBidNetPriceArr(bidNetPriceList.toArray(new Double[bidNetPriceList.size()]));
            result.setOfrNetPriceArr(ofrNetPriceList.toArray(new Double[ofrNetPriceList.size()]));
            result.setBidPriceYieldArr(bidPriceYieldList.toArray(new Double[bidPriceYieldList.size()]));
            result.setOfrPriceYieldArr(ofrPriceYieldList.toArray(new Double[ofrPriceYieldList.size()]));
            result.setBidCountArr(bidCountList.toArray(new Integer[bidCountList.size()]));
            result.setOfrCountArr(ofrCountList.toArray(new Integer[ofrCountList.size()]));
        }
        return result;
    }

    private Double getScaledDouble(Double sourceDouble) {
        if (sourceDouble == null) return null;
        return new BigDecimal(sourceDouble).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public List<BondRiskDoc> getRiskList(Integer year) {
        Sort sort = new Sort(Sort.Direction.ASC, "priority");
        Query query = new Query();
        Criteria c = new Criteria();
        c.andOperator(Criteria.where("pdTime").is(year));
        query.addCriteria(c).with(sort);
        List<BondRiskDoc> list = mongoOperations.find(query, BondRiskDoc.class);
        return list;
    }

    public List<BondRiskDoc> getRiskList(Long userId, Integer year, Long[] induIds) {
        LOG.info("riskList with induinfo,userId:"+userId+",year:"+year+",induIds:"+induIds.toString());
        List<BondRiskDoc> results = this.getRiskList(year);
        if (null != induIds && induIds.length > 0) {
            List<Long> comUniCodes = comInfoService.findComUniCodesWithInduIdAndUserId(userId, induIds);
            results.stream().forEach(bondRiskDoc -> {
                this.resetRiskColumns(comUniCodes, bondRiskDoc);
            });
        }
        return results;
    }

    private void resetRiskColumns(List<Long> comUniCodes, BondRiskDoc bondRiskDoc) {
        resetRiskColumn(comUniCodes, bondRiskDoc.getColumn1());
        resetRiskColumn(comUniCodes, bondRiskDoc.getColumn2());
        resetRiskColumn(comUniCodes, bondRiskDoc.getColumn3());
        resetRiskColumn(comUniCodes, bondRiskDoc.getColumn4());
        resetRiskColumn(comUniCodes, bondRiskDoc.getColumn5());
        resetRiskColumn(comUniCodes, bondRiskDoc.getColumn6());
        resetRiskColumn(comUniCodes, bondRiskDoc.getColumn7());
        resetRiskColumn(comUniCodes, bondRiskDoc.getColumn8());
        resetRiskColumn(comUniCodes, bondRiskDoc.getColumn9());
    }

    private void resetRiskColumn(List<Long> comUniCodes, BondRiskColumnDoc column) {
        if (null != comUniCodes && !comUniCodes.isEmpty() &&
                null != column && !column.getComUniCodeList().isEmpty()) {
            Set<Long> comUniCodeSet = column.getComUniCodeList().parallelStream()
                    .filter(comUniCode -> comUniCodes.contains(comUniCode)).collect(Collectors.toSet());
            if (null != comUniCodeSet && !comUniCodeSet.isEmpty()) {
                column.setCount(comUniCodeSet.size());
            }else{
                column.setCount(null);
            }
            column.setComUniCodeList(comUniCodeSet);
        }else{
            column.setCount(null);
            column.setComUniCodeList(new HashSet<Long>());
        }
    }

    // ============================================================
    // ========================= 行业方法 =========================
    // ============================================================

    private class CriteriaFactory<T> {
        private String collection2string(Collection<T> list) {
            String listString = "";
            for (T s : list)
                listString += s.toString() + " ";
            return listString;
        }

        public Criteria generate(String name, BondIssuerDoc issuer) {
            LOG.info("Criteria, name:" + name + ", issuer:" + ((issuer == null) ? "null" : issuer.toString()));
            if (issuer != null && issuer.getComUniCode() != null)
                return Criteria.where(name).is(issuer.getComUniCode());
            else
                return CRITERIA_MATCH_ALL;
        }

        public Criteria generate(String name, Collection<T> list) {
            LOG.info("Criteria, name:" + name + ", list:" + ((list == null) ? "null" : collection2string(list)));
            if (list != null)
                return Criteria.where(name).in(list);
            else
                return CRITERIA_MATCH_ALL;
        }

        public Criteria generate(String name, T o) {
            LOG.info("Criteria, name:" + name + ", objct:" + ((o == null) ? "null" : o.toString()));
            if (o != null)
                return Criteria.where(name).is(o);
            else
                return CRITERIA_MATCH_ALL;
        }
    }
}
