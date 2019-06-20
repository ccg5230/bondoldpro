package com.innodealing.service;

import com.google.common.collect.Lists;
import com.innodealing.consts.Constants;
import com.innodealing.dao.BondCombineDao;
import com.innodealing.domain.vo.*;
import com.innodealing.engine.jdbc.NotificationMsgDao;
import com.innodealing.engine.jdbc.bond.*;
import com.innodealing.engine.jpa.dm.*;
import com.innodealing.engine.mongo.bond.BondBasicInfoRepository;
import com.innodealing.engine.mongo.bond.BondFieldGroupMappingDocRepository;
import com.innodealing.exception.BusinessException;
import com.innodealing.model.dm.bond.*;
import com.innodealing.model.mongo.dm.*;
import com.innodealing.param.FavoriteBondBatchReq;
import com.innodealing.param.FavoriteGroupReq;
import com.innodealing.param.FavoriteRadarReq;
import com.innodealing.param.FavoriteUpdateList;
import com.innodealing.util.HttpClientUtil;
import com.innodealing.util.SafeUtils;
import com.innodealing.utils.ExpressionUtil;
import com.mongodb.WriteResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BondCombineService {
    private final static Logger LOG = LoggerFactory.getLogger(BondCombineService.class);

    private @Autowired
    JdbcTemplate jdbcTemplate;
    private @Autowired
    ApplicationContext context;
    private @Autowired
    MongoOperations mongoOperations;

    private @Autowired
    IndicatorDao indicatorDAO;
    private @Autowired
    NotificationMsgDao msgDAO;
    private @Autowired
    BondFavoriteDao favoriteDAO;
    private @Autowired
    BondFavoritePriceIndexDAO priceIndexDAO;
    private @Autowired
    BondFavoriteFinaIndexDAO finaIndexDAO;
    private @Autowired
    BondFavoriteRadarMappingDAO radarMappingDAO;

    private @Autowired
    BondCombineDao bondCombineDao;

    private @Autowired
    BondFavoriteRepository favoriteRepo;
    private @Autowired
    BondBasicInfoRepository bondBasicInfoRepo;
    private @Autowired
    UserOprRecordRepository userOprRecordRepo;
    private @Autowired
    BondFavoriteGroupRepository favoriteGroupRepo;
    private @Autowired
    BondFavoritePriceIndexRepository favPriceIndexRepo;
    private @Autowired
    BondFavoriteFinaIndexRepository favFinaIndexRepo;
    private @Autowired
    BondFavoriteRadarMappingRepository favRadarMappingRepo;
    private @Autowired
    BondFavoriteRadarSchemaRepository bondFavRadarSchemaRepo;
    private @Autowired
    BondFieldGroupMappingDocRepository bondFieldGroupMappingDocRepo;

    private ExecutorService cachedThreadPool = Executors.newFixedThreadPool(50);

    @Value("${url.sentiment_url}")
    private String SENTIMENT_URL;//获取舆情URL的接口地址

    /**
     * 获取用户的投组列表(不再需要"我的持仓"默认投组)
     *
     * @return
     */
    public List<BondFavoriteGroupVO> getFavoriteGroupListByUserId(Integer userId) {
        List<BondFavoriteGroupVO> result = new ArrayList<>();

        List<SimpleFavoriteGroupVO> favGroupVOList =
                favoriteDAO.getSimpleFavoriteGroupVOListByUserId(userId, SimpleFavoriteGroupVO.class);
        Map<Integer, List<SimpleFavoriteGroupVO>> tempVOMap = favGroupVOList.stream()
                .collect(Collectors.groupingBy(SimpleFavoriteGroupVO::getGroupId));


        Map<Integer, List<SimpleFavoriteGroupVO>> groupVOMap = new LinkedHashMap<>();

        // 按照投组id排序
        tempVOMap.keySet().stream().sorted().forEachOrdered(key -> groupVOMap.put(key, tempVOMap.get(key)));

        if (!groupVOMap.keySet().isEmpty()) {
            List<CompletableFuture<BondFavoriteGroupVO>> groupFutureList = groupVOMap.keySet().stream()
                    .map(groupId -> CompletableFuture.supplyAsync(() -> this.getBondFavoriteGroupVO(groupId, groupVOMap.get(groupId)), cachedThreadPool))
                    .collect(Collectors.toList());

            result.addAll(groupFutureList.stream().map(CompletableFuture::join).collect(Collectors.toList()));
        }

        return result;
    }

    /**
     * 根据父雷达ID获取子雷达
     *
     * @return
     */
    public List<BondFavoriteRadarSchema> getChildRadarByRadarId(Long radarType) {
        List<BondFavoriteRadarSchema> result = bondFavRadarSchemaRepo.findAllByParentIdAndStatus(radarType, 1);
        return result;
    }

    /**
     * 根据名称搜索用户投组，债券列表
     *
     * @return
     */
    public Page<BondSearchItemVO> searchBondAllList(Integer userId, String searchContent,
                                                    Integer pageIndex, Integer pageSize) {
        List<BondSearchItemVO> result = bondCombineDao.searchBondAllList(userId, searchContent, pageIndex, pageSize);
        Long resultSize = bondCombineDao.getPageCountOfSearchBondAll(userId, searchContent);
        return new PageImpl<>(result, new PageRequest(pageIndex, pageSize), resultSize);
    }

    /**
     * 根据名称搜索债券列表
     *
     * @return
     */
    public Page<BondSearchItemVO> searchBondList(Integer userId, Long groupId, String searchContent,
                                                 Integer pageIndex, Integer pageSize) {
        List<BondSearchItemVO> result = bondCombineDao.searchBondList(userId, groupId, searchContent, pageIndex, pageSize);
        Long resultSize = bondCombineDao.getPageCountOfSearchBondList(userId, groupId, searchContent);
        return new PageImpl<>(result, new PageRequest(pageIndex, pageSize), resultSize);
    }

    /**
     * 获取用户关注投组
     *
     * @return
     */
    private BondFavoriteGroupVO getBondFavoriteGroupVO(Integer groupId, List<SimpleFavoriteGroupVO> voList) {
        BondFavoriteGroupVO favGroupVO = new BondFavoriteGroupVO();
//        if (!voList.isEmpty()) {
        SimpleFavoriteGroupVO headerSimpleVO = voList.get(0);
        BeanUtils.copyProperties(headerSimpleVO, favGroupVO);
//        }

        if (voList.size() == 1 && voList.get(0).getFavoriteId() == null) {
            favGroupVO.setBondCount(0L); // 处理新投组或者没有关注过债券的投组
        } else {
            favGroupVO.setBondCount(SafeUtils.getLong(voList.size()));
        }

        // 未读消息数,未读消息提醒开关已打开.
        if (favGroupVO.getNotifiedEnable() == 1) {
            // 新的未读消息数，减去其中标记为已读的消息数
            List<Long> newMsgIdList = favoriteDAO.getSketchyNewMsgIdListByGroupId(groupId);
            favGroupVO.setEventMsgCount(this.getEventMsgCountByNewMsgIdList(newMsgIdList));
        }

        return favGroupVO;
    }

    /**
     * 新的未读消息数，减去mongo中标记为已读的消息数
     *
     * @return
     */
    private Long getEventMsgCountByNewMsgIdList(List<Long> newMsgIdList) {
        Query markedQuery = new Query();
        markedQuery.addCriteria(Criteria.where("notifyMsgId").in(newMsgIdList));
        Long markedReadMsgCacheCount = mongoOperations.count(markedQuery, BondMsgUserStatusDoc.class);
        return newMsgIdList.size() - markedReadMsgCacheCount;
    }

    /**
     * 将rootRadar列表转化成subRadar列表，保留原列表的subRadar
     *
     * @return
     */
    private List<Long> convertToSubRadarList(List<Long> radarList) {
        List<Long> result = new ArrayList<>();
        radarList.stream().forEach(type -> {
            List<BondFavoriteRadarSchema> subRadarSchemaList = bondFavRadarSchemaRepo.findAllByParentIdAndStatus(type,
                    1);
            if (subRadarSchemaList.isEmpty() && !result.contains(type)) {
                result.add(type);
            } else {
                result.addAll(
                        subRadarSchemaList.stream().map(BondFavoriteRadarSchema::getId).collect(Collectors.toList()));
            }
        });
        return result;
    }

    /**
     * 设置单支债券变动提醒-价格指标列表
     * 获取投组债券价格指标列表，半全局为groupId和favoriteId都不等于0（status：0-不选；1-选中）
     * 私人为groupId等于0，且favoriteId不等于0
     *
     * @param favoriteId
     * @return
     */
    public List<BondFavoritePriceIndex> getSingleFavoritePriceIndexList(Long favoriteId) {
        return favPriceIndexRepo.findAllByFavoriteId(favoriteId);
    }

    /**
     * 设置单支债券变动提醒-财务指标列表
     * 获取投组债券财务指标列表，半全局为groupId和favoriteId都不等于0（status：0-不选；1-选中）
     * 私人为groupId等于0，且favoriteId不等于0
     *
     * @param favoriteId
     * @return
     */
    public List<BondFavoriteFinaIndex> getSingleFavoriteFinaIndexList(Long favoriteId) {
        // 如果不是"非金融企业债券",只能显示市场指标
        BondFavorite favorite = favoriteRepo.findOneByFavoriteIdAndIsDelete(SafeUtils.getInteger(favoriteId), 0);
        Query query = new Query(Criteria.where("bondUniCode").is(favorite.getBondUniCode()));
        query.fields().include("issuerId");
        BondBasicInfoDoc basicInfoDoc = mongoOperations.findOne(query, BondBasicInfoDoc.class);
        boolean isNotFinaCompBond = basicInfoDoc != null ? this.checkIsNotFinaCompBond(basicInfoDoc.getIssuerId()) : false;
        List<BondFavoriteFinaIndex> result = favFinaIndexRepo.findAllByFavoriteId(favoriteId);
        if (!isNotFinaCompBond) {
            result = result.stream().filter(finaIdx -> finaIdx.getFinaSubType() == 1).collect(Collectors.toList());
        }
        return result;
    }

    /**
     * 设置单支债券变动提醒 保存价格指标和财务指标雷达
     *
     * @param req
     * @return
     */
    @Transactional
    public boolean saveFavoriteRadars(Integer userId, FavoriteRadarReq req) {
        Long favIdL = req.getFavoriteId();
        Long groupIdL = req.getGroupId();
        Integer favId = SafeUtils.getInteger(favIdL);
        Integer groupId = SafeUtils.getInteger(groupIdL);
        BondFavoriteGroup group = favoriteGroupRepo.findOneByGroupIdAndIsDelete(groupId, 0);
        BondFavorite favorite = favoriteRepo.findOneByFavoriteIdAndIsDelete(favId, 0);
        Long bondUniCode = favorite.getBondUniCode();
        // 关注债券->bondUniCode缓存
        Map<Long, Long> fav2BondCodeMap = new HashMap<Long, Long>() {
            {
                put(favIdL, bondUniCode);
            }
        };
        // bondUniCode->issuerId缓存
        Query query = new Query(Criteria.where("bondUniCode").is(bondUniCode));
        query.fields().include("issuerId");
        BondBasicInfoDoc basicInfoDoc = mongoOperations.findOne(query, BondBasicInfoDoc.class);
        Map<Long, Long> bondCode2CompCodeMap = new HashMap<Long, Long>() {
            {
                put(bondUniCode, basicInfoDoc.getIssuerId());
            }
        };

        Integer notifiedEnable = group.getNotifiedEnable();
        boolean isNotFinaCompBond = this.checkIsNotFinaCompBond(basicInfoDoc.getIssuerId());
        Date currDate = SafeUtils.getCurrentTime();
        // 数据处理
        req.getPriceList().stream().forEach(item -> {
            if (item.getId() != null && item.getId().equals(0L)) {
                item.setId(null);
            }
            if (item.getId() == null) {
                item.setCreateTime(currDate);
            }
        });
        req.getFinaList().stream().forEach(item -> {
            if (item.getId() != null && item.getId().equals(0L)) {
                item.setId(null);
            }
            if (item.getId() == null) {
                item.setCreateTime(currDate);
            }
        });

        // 半全局的雷达只更新状态即可;私人雷达先删除，再重建
        favPriceIndexRepo.deleteByGroupIdAndFavoriteId(0L, favIdL);
        List<BondFavoritePriceIndex> validPriceIndexList = req.getPriceList().stream()
                .filter(item -> item.getIndexValue() != null).collect(Collectors.toList());
        // 批量插入
        this.batchInsertPriceRadarListByJdbc(validPriceIndexList);

        favFinaIndexRepo.deleteByGroupIdAndFavoriteId(0L, favIdL);
        List<BondFavoriteFinaIndex> validFinaIndexList = req.getFinaList();
        if (!isNotFinaCompBond) {
            // 如果不是非金融企业债，则只能匹配市场指标
            validFinaIndexList = req.getFinaList().stream()
                    .filter(item -> item.getFinaSubType() != null && item.getFinaSubType() == 1)
                    .collect(Collectors.toList());
        }
        // 批量插入
        this.batchInsertFinaRadarListByJdbc(validFinaIndexList);

        // 更新MongoDB缓存条件，先删除，再插入
        Query idxQuery = new Query();
        idxQuery.addCriteria(Criteria.where("favoriteId").is(favIdL));
        mongoOperations.remove(idxQuery, BondFavPriceIdxDoc.class);
        mongoOperations.remove(idxQuery, BondFavFinaIdxDoc.class);
        List<Object> priceDocList = this.convertToPriceMongoRadarList(userId, groupIdL, validPriceIndexList, fav2BondCodeMap, notifiedEnable);
        if (priceDocList != null && !priceDocList.isEmpty()) {
            mongoOperations.insertAll(priceDocList);
        }
        List<Object> finaDocList = this.convertToFinaMongoRadarList(userId, groupIdL, validFinaIndexList, fav2BondCodeMap, bondCode2CompCodeMap, notifiedEnable);
        if (finaDocList != null && !finaDocList.isEmpty()) {
            mongoOperations.insertAll(finaDocList);
        }
        return true;
    }

    /**
     * 会排除priceIndexList中status为0的记录
     *
     * @param userId
     * @param groupId
     * @param priceIndexList
     * @param fav2BondCodeMap
     * @param notifiedEnable
     * @return
     */
    private List<Object> convertToPriceMongoRadarList(Integer userId, Long groupId, List<BondFavoritePriceIndex> priceIndexList,
                                                      Map<Long, Long> fav2BondCodeMap, Integer notifiedEnable) {
        List<Object> result = new ArrayList<>();
        priceIndexList.stream().filter(price -> price.getStatus() == 1).forEach(item -> {
            BondFavPriceIdxDoc favPriceIdxDoc = new BondFavPriceIdxDoc();
            BeanUtils.copyProperties(item, favPriceIdxDoc);
            favPriceIdxDoc.setNotifiedEnable(notifiedEnable);
            favPriceIdxDoc.setUserId(userId);
            favPriceIdxDoc.setBondUniCode(fav2BondCodeMap.get(item.getFavoriteId()));
            // 给私人条件设置投组id
            if (favPriceIdxDoc.getGroupId().equals(0L)) {
                favPriceIdxDoc.setGroupId(groupId);
            }
            result.add(favPriceIdxDoc);
        });
        return result;
    }

    /**
     * 会排除finaIndexList中status为0的记录
     *
     * @param userId
     * @param groupId
     * @param finaIndexList
     * @param fav2BondCodeMap
     * @param bondCode2IssuerIdMap
     * @param notifiedEnable
     * @return
     */
    private List<Object> convertToFinaMongoRadarList(Integer userId, Long groupId, List<BondFavoriteFinaIndex> finaIndexList,
                                                     Map<Long, Long> fav2BondCodeMap, Map<Long, Long> bondCode2IssuerIdMap, Integer notifiedEnable) {
        List<Object> result = new ArrayList<>();
        finaIndexList.stream().filter(fina -> fina.getStatus() == 1).forEach(item -> {
            Long bondUniCode = fav2BondCodeMap.get(item.getFavoriteId());
            BondFavFinaIdxDoc favFinaIdxDoc = new BondFavFinaIdxDoc();
            BeanUtils.copyProperties(item, favFinaIdxDoc);
            List<String> variables = ExpressionUtil.extractFieldsInExpression(item.getIndexCodeExpr());
            favFinaIdxDoc.setVariables(variables);
            favFinaIdxDoc.setNotifiedEnable(notifiedEnable);
            favFinaIdxDoc.setUserId(userId);
            favFinaIdxDoc.setBondUniCode(bondUniCode);
            // 给私人条件设置投组id
            if (favFinaIdxDoc.getGroupId().equals(0L)) {
                favFinaIdxDoc.setGroupId(groupId);
            }
            // 财务指标需要发行人id
            if (bondCode2IssuerIdMap.containsKey(bondUniCode)) {
                favFinaIdxDoc.setComUniCode(bondCode2IssuerIdMap.get(bondUniCode));
            }
            result.add(favFinaIdxDoc);
        });
        return result;
    }

    /**
     * 价格指标，判断是否是从投组全局继承的提醒条件
     *
     * @param global
     * @param fav
     * @return
     */
    private boolean isInheritFromGlobalPriceRadar(BondFavoritePriceIndex global, BondFavoritePriceIndex fav) {
        boolean result = false;
        // status=1，并且priceIndex,priceType,priceCondi,indexUnit全部相同，则是继承全局的
        if (global.getStatus() == 1 && global.getPriceIndex().equals(fav.getPriceIndex())
                && global.getPriceType().equals(fav.getPriceType())
                && global.getPriceCondi().equals(fav.getPriceCondi())
                && global.getIndexUnit().equals(fav.getIndexUnit())) {
            result = true;
        }
        return result;
    }

    /**
     * 财务指标，判断是否是从投组全局继承的提醒条件
     *
     * @param global
     * @param fav
     * @return
     */
    private Boolean isInheritFromGlobalFinaRadar(BondFavoriteFinaIndex global, BondFavoriteFinaIndex fav) {
        boolean result = false;
        // status=1，并且IndexCodeExpr,IndexName,IndexValueType,IndexValueUnit全部相同，则是继承全局的
        if (global.getStatus() == 1 && global.getIndexCodeExpr().equals(fav.getIndexCodeExpr())
                && global.getIndexName().equals(fav.getIndexName())
                && global.getIndexValueType().equals(fav.getIndexValueType())
                && global.getIndexValueNexus().equals(fav.getIndexValueNexus())
                && global.getIndexValueUnit().equals(fav.getIndexValueUnit())) {
            result = true;
        }
        return result;
    }

    public List<BondCommonRadarVO> getGroupCommonRadarListByType(Long groupId, Long radarType) {
        List<BondCommonRadarVO> result = new ArrayList<>();
        // 取出字典数据
        List<BondFavoriteRadarSchema> radarSchemaList = bondFavRadarSchemaRepo.findAllByParentIdAndStatus(radarType, 1);
        if (Constants.RADAR_TYPE_PRICE.equals(radarType) || Constants.RADAR_TYPE_FINA.equals(radarType)
                || radarSchemaList.isEmpty()) {
            LOG.warn(String.format("getGroupCommonRadarListByType: invalid radarType[%1$d] with groupId[%2$d]",
                    radarType, groupId));
            return result;
        }

        Map<Long, String> radarNameMap = new HashMap<>();
        radarSchemaList.stream().forEach(item -> {
            radarNameMap.put(item.getId(), item.getName());
        });
        List<Long> subRadarTypeList = radarSchemaList.stream().map(BondFavoriteRadarSchema::getId)
                .collect(Collectors.toList());

        // 处理
        List<BondFavoriteRadarMapping> defaultCommonRadarList = favRadarMappingRepo.findAllByGroupIdAndRadarIdIn(0L,
                subRadarTypeList);
        if (!groupId.equals(0L)) {
            // 已经存在记录
            List<BondFavoriteRadarMapping> groupCommonRadarList = favRadarMappingRepo
                    .findAllByGroupIdAndRadarIdIn(groupId, subRadarTypeList);
            defaultCommonRadarList.stream().forEach(item -> {
                BondCommonRadarVO commonRadarVO = new BondCommonRadarVO();
                BeanUtils.copyProperties(item, commonRadarVO);
                // 某一些情况下置为不选中，其余为选中
                if (item.getThreshold() != null) {
                    // 存续
                    if (item.getThreshold() == 1 && groupCommonRadarList.stream().anyMatch(
                            temp -> temp.getRadarId().equals(item.getRadarId()) && temp.getThreshold() == 1)) {
                        // 存续默认值是1的情况，如果存在对应的1，则忽略默认值，将已存在的记录设为选中
                        BondFavoriteRadarMapping existCommonRadar = groupCommonRadarList.stream().filter(
                                exist -> exist.getRadarId().equals(item.getRadarId()) && exist.getThreshold() == 1)
                                .findFirst().get();
                        BeanUtils.copyProperties(existCommonRadar, commonRadarVO);
                        commonRadarVO.setStatus(1);
                    } else if (item.getThreshold() > 1 && groupCommonRadarList.stream()
                            .anyMatch(temp -> temp.getRadarId().equals(item.getRadarId()) && temp.getThreshold() > 1)) {
                        // 阈值大于1的情况下，群组设置覆盖掉默认设置
                        BondFavoriteRadarMapping groupCommonRadar = groupCommonRadarList.stream().filter(
                                exist -> exist.getRadarId().equals(item.getRadarId()) && exist.getThreshold() > 1)
                                .findFirst().get();
                        BeanUtils.copyProperties(groupCommonRadar, commonRadarVO);
                        commonRadarVO.setStatus(1);
                    } else {
                        commonRadarVO.setId(null);
                        commonRadarVO.setStatus(0);
                    }
                } else if (!groupCommonRadarList.stream()
                        .anyMatch(exist -> exist.getRadarId().equals(item.getRadarId()))) {
                    // 不存在，用默认的
                    commonRadarVO.setId(null);
                    commonRadarVO.setGroupId(groupId);
                    commonRadarVO.setStatus(0);
                } else {
                    // 不是存续，也存在，使用已经存在的（id）
                    BondFavoriteRadarMapping groupCommonRadar = groupCommonRadarList.stream()
                            .filter(exist -> exist.getRadarId().equals(item.getRadarId())).findFirst().get();
                    BeanUtils.copyProperties(groupCommonRadar, commonRadarVO);
                    commonRadarVO.setStatus(1);
                }
                commonRadarVO.setRadarName(radarNameMap.get(item.getRadarId()));
                result.add(commonRadarVO);
            });
        } else {
            defaultCommonRadarList.stream().forEach(item -> {
                item.setRadarName(radarNameMap.get(item.getRadarId()));
                BondCommonRadarVO commonRadarVO = new BondCommonRadarVO();
                BeanUtils.copyProperties(item, commonRadarVO);
                commonRadarVO.setId(null);
                commonRadarVO.setGroupId(null);
                commonRadarVO.setCreateTime(null);
                commonRadarVO.setStatus(1);
                result.add(commonRadarVO);
            });
        }
        return result;
    }

    public List<BondFavoritePriceIndex> getGroupPriceRadarList(Long groupId) {
        List<BondFavoritePriceIndex> result;
        result = favPriceIndexRepo.findAllByGroupIdAndFavoriteId(groupId, 0L);
        if (groupId.equals(0L)) {
            result.stream().forEach(item -> item.setId(null));
        }
        return result;
    }

    public List<BondFavoriteFinaIndex> getGroupFinaRadarList(Long groupId) {
        List<BondFavoriteFinaIndex> result;
        result = favFinaIndexRepo.findAllByGroupIdAndFavoriteId(groupId, 0L);
        if (groupId.equals(0L)) {
            result.stream().forEach(item -> item.setId(null));
        }
        return result;
    }

    /**
     * 获取投组中的债券详情列表
     *
     * @return
     */
    public Page<BondFavoriteProfileVO> getGroupProfileFavBondList(Integer userId, Integer groupId, Integer page, Integer limit) {
        Integer dateDiff = -1;
        List<Long> rootRadarList = bondFavRadarSchemaRepo.findAllByParentIdAndStatus(0L, 1).stream()
                .map(BondFavoriteRadarSchema::getId).collect(Collectors.toList());
        // 忽略限定条件，获取投组中所有的债券列表，不能分页，因为可能要再根据关键字过滤
        List<BondFavorite> allFavList = favoriteRepo.findByGroupIdOrderByUpdateTimeDesc(groupId);
        List<Long> radarTypeList = this.convertToSubRadarList(rootRadarList);
        // 全部转化成BondFavoriteDetailVO
        List<BondFavoriteProfileVO> unPagedResultList = this.getFavoriteProfileVOListByConstraint(allFavList);
        Map<Boolean, List<BondFavoriteProfileVO>> resultMap = unPagedResultList.stream().filter(item -> item != null)
                .collect(Collectors.partitioningBy(vo -> vo.getExpiredState() == 0));

        // createTime实际上是投组更新时间，因为被占用，所以用给createTime赋值来排序
        List<BondFavoriteProfileVO> briefVOList = Stream.concat(
                resultMap.get(true).stream().sorted(Comparator.comparing(BondFavoriteProfileVO::getCreateTime).reversed()),
                resultMap.get(false).stream().sorted(Comparator.comparing(BondFavoriteProfileVO::getCreateTime).reversed())
        ).collect(Collectors.toList());
        // 再对总数进行分页
        int resultSize = briefVOList.size();
        int from = page * limit > briefVOList.size() ? briefVOList.size() : page * limit;
        int to = (page + 1) * limit > briefVOList.size() ? briefVOList.size() : (page + 1) * limit;
        // 生成新的分页列表，清空未分页集合
        List<BondFavoriteProfileVO> pagedVOList = briefVOList.subList(from, to).stream().map(vo -> {
            BondFavoriteProfileVO newVO = new BondFavoriteProfileVO();
            BeanUtils.copyProperties(vo, newVO);
            return newVO;
        }).collect(Collectors.toList());
        briefVOList.clear();
        // 处理分页列表的数据
        this.handleFavoriteProfile(userId, groupId, dateDiff, radarTypeList, pagedVOList, allFavList);
        // 根据briefVOList，在给定的限制条件下(时间限定)，获取briefVOList的详情列表
        return new PageImpl<>(pagedVOList, new PageRequest(page, limit), resultSize);
    }

    /**
     * 根据id获取关注债券简况
     *
     * @return
     */
    public BondDetailDoc getBondFavoriteProfileById(Long bondCodeId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("bondUniCode").is(bondCodeId));
        query.fields().include("_id").include("name").include("code").include("priceUpdateTime").include("priceCount")
                .include("impliedRating").include("bidOrderCnt").include("bidPrice").include("latelyPayDate")
                .include("bidVol").include("coupRate").include("fairValue").include("isFavorited").include("comName")
                .include("issBondRating").include("bondRating").include("ofrOrderCnt").include("ofrPrice").include("ofrVol").include("pd")
                .include("pdDiff").include("poNegtive").include("poNeutral").include("poPositive").include("price")
                .include("tenor").include("updateTime").include("createTime").include("isCompared").include("pdTime")
                .include("riskWarning").include("defaultDate").include("defaultEvent").include("worstPd")
                .include("worstPdTime").include("worstRiskWarning").include("bestBidPrice").include("bestOfrPrice")
                .include("induName").include("newSize").include("favoriteId").include("yearPayDate")
                .include("exerPayDate").include("theoEndDate").include("currStatus").include("issStaPar");
        return mongoOperations.findOne(query, BondDetailDoc.class);
    }

    /**
     * 根据id获取关注债券主体
     *
     * @return
     */
    public BondComInfoDoc getBondFavoriteCompanyById(Long comUniCode) {
        Query query = new Query();
        query.addCriteria(Criteria.where("comUniCode").is(comUniCode));
        return mongoOperations.findOne(query, BondComInfoDoc.class);
    }

    /**
     * 处理未读消息数，成交价列表，是否有私人雷达
     */
    private void handleFavoriteProfile(Integer userId, Integer groupId, Integer dateDiff, List<Long> radarTypeList,
                                       List<BondFavoriteProfileVO> pagedVOList, List<BondFavorite> favList) {
        List<Long> bondUniCodeList = pagedVOList.stream().map(BondFavoriteProfileVO::getBondUniCode).collect(Collectors.toList());
        // 关注债券缓存
        Map<Long, BondFavorite> bond2FavMap = new HashMap<>();
        favList.stream().forEach(fav -> bond2FavMap.put(fav.getBondUniCode(), fav));
        // 生成basicInfo缓存
        List<BondBasicInfoDoc> basicInfoDocList = this.getBondBasicInfoByBondUniCodeList(bondUniCodeList);
        Map<Long, BondBasicInfoDoc> code2BasicMap = basicInfoDocList.stream()
                .collect(Collectors.toMap(BondBasicInfoDoc::getBondUniCode, item -> item));
        // 生成detailDoc缓存
        List<BondDetailDoc> bondDetailDocList = this.getBondDetailByBondUniCodeList(bondUniCodeList);
        Map<Long, BondDetailDoc> code2DetailMap = bondDetailDocList.stream()
                .collect(Collectors.toMap(BondDetailDoc::getBondUniCode, item -> item));
        // 处理数据
        CompletableFuture[] groupFavoriteMapFutures = pagedVOList.stream()
                .map(vo -> CompletableFuture.runAsync(() -> {
                    Integer favId = vo.getFavoriteId();
                    Long innerUniCode = vo.getBondUniCode();
                    // 保存通用属性
                    if (code2DetailMap.containsKey(innerUniCode)) {
                        BondDetailDoc bondDetailDoc = code2DetailMap.get(innerUniCode);
                        BeanUtils.copyProperties(bondDetailDoc, vo);
                    }
                    // 恢复债券被覆盖的相关属性
                    BondFavorite favorite = bond2FavMap.get(innerUniCode);
                    vo.setFavoriteId(favorite.getFavoriteId());
                    // 未读消息数
                    Long eventMsgCount = this.getFavoriteNewMsgCountByConstraint(userId, groupId, favId, dateDiff,
                            radarTypeList);
                    vo.setEventMsgCount(eventMsgCount);
                    // 成交价列表
                    this.calcPriceCountRelated(vo);
                })).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(groupFavoriteMapFutures).join();
    }

    /**
     * 获取过滤的关注债券列表，根据搜索关键字[债券的fullname或者code包含关键字]
     *
     * @return
     */
    private List<BondFavorite> getFilteredFavoriteByKeyword(List<BondFavorite> favList, String keyword) {
        if (favList == null || favList.isEmpty() || StringUtils.isBlank(keyword)) {
            return favList;
        }
        List<BondFavorite> result = new ArrayList<>();
        favList.stream().forEach(item -> {
            BondBasicInfoDoc bondBasicInfoDoc = bondBasicInfoRepo.findOne(item.getBondUniCode());
            if (bondBasicInfoDoc != null) {
                if ((bondBasicInfoDoc.getCode() != null && bondBasicInfoDoc.getCode().contains(keyword))
                        || (bondBasicInfoDoc.getShortName() != null
                        && bondBasicInfoDoc.getShortName().contains(keyword))) {
                    result.add(item);
                }
            }
        });
        return result;
    }

    /**
     * 获取favList对应的详情信息列表
     *
     * @return
     */
    private List<BondFavoriteProfileVO> getFavoriteProfileVOListByConstraint(List<BondFavorite> favList) {
        // 准备参数
        List<BondFavoriteProfileVO> result = new ArrayList<>();
        List<Long> bondUniCodeList = favList.stream().map(BondFavorite::getBondUniCode).collect(Collectors.toList());
        Map<Long, BondFavorite> bond2FavMap = new HashMap<>();
        favList.stream().forEach(fav -> bond2FavMap.put(fav.getBondUniCode(), fav));
        List<BondDetailDoc> bondDetailDocList = this.getBondDetailExpiredStateByBondUniCodeList(bondUniCodeList);
        if (bondDetailDocList.isEmpty()) {
            return result;
        }
        // 多线程异步获取债券详情列表，仅仅需要是否过期+favorite的属性，用于排序分页，其他属性分页之后再加载
        List<CompletableFuture<BondFavoriteProfileVO>> favDetailVOFutureList = bondDetailDocList.stream()
                .map(detail -> CompletableFuture.supplyAsync(() -> {
                    Long innerUniCode = detail.getBondUniCode();
                    BondFavoriteProfileVO vo = new BondFavoriteProfileVO();
                    vo.setBondUniCode(innerUniCode);
                    BondFavorite favorite = bond2FavMap.get(innerUniCode);
                    // 投组债券相关属性
                    vo.setFavoriteId(favorite.getFavoriteId());
                    vo.setExpiredState(detail.getCurrStatus() == 1 && detail.getIssStaPar() == 1 ? 0 : 1);
                    // 设置时间，排序依据
                    vo.setCreateTime(favorite.getUpdateTime());
                    return vo;
                }, cachedThreadPool)).collect(Collectors.toList());
        result = favDetailVOFutureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
        return result;
    }

    /**
     * 取得数据判断债券是否过期，用以排序
     *
     * @param bondUniCodeList
     * @return
     */
    private List<BondDetailDoc> getBondDetailExpiredStateByBondUniCodeList(List<Long> bondUniCodeList) {
        Query query = new Query();
        query.addCriteria(Criteria.where("bondUniCode").in(bondUniCodeList));
        query.fields().include("_id").include("currStatus").include("issStaPar");
        return mongoOperations.find(query, BondDetailDoc.class);
    }

    private List<BondDetailDoc> getBondDetailByBondUniCodeList(List<Long> bondUniCodeList) {
        List<BondDetailDoc> result = new ArrayList<>();
        Query query = new Query();
        query.addCriteria(Criteria.where("bondUniCode").in(bondUniCodeList));
        query.fields().include("_id").include("name").include("code").include("priceUpdateTime").include("priceCount")
                .include("impliedRating").include("bidOrderCnt").include("bidPrice").include("latelyPayDate")
                .include("bidVol").include("coupRate").include("fairValue").include("isFavorited").include("comName")
                .include("issBondRating").include("ofrOrderCnt").include("ofrPrice").include("ofrVol").include("pd")
                .include("pdDiff").include("poNegtive").include("poNeutral").include("poPositive").include("price")
                .include("tenor").include("updateTime").include("createTime").include("isCompared").include("pdTime")
                .include("riskWarning").include("defaultDate").include("defaultEvent").include("worstPd")
                .include("worstPdTime").include("worstRiskWarning").include("bestBidPrice").include("bestOfrPrice")
                .include("induName").include("newSize").include("favoriteId").include("yearPayDate")
                .include("exerPayDate").include("theoEndDate").include("currStatus").include("issStaPar");
        // 对结果集重新排序，以bondUniCodeList为基准
        List<BondDetailDoc> bondDetailDocList = mongoOperations.find(query, BondDetailDoc.class);
        bondUniCodeList.stream().forEach(item -> {
            bondDetailDocList.stream().filter(doc -> doc.getBondUniCode().equals(item)).findFirst()
                    .ifPresent(doc -> result.add(doc));
        });
        return result;
    }

    private List<BondBasicInfoDoc> getBondBasicInfoByBondUniCodeList(List<Long> bondUniCodeList) {
        Query query = new Query();
        query.addCriteria(Criteria.where("bondUniCode").in(bondUniCodeList));
        query.fields().include("_id").include("issRatePros");
        List<BondBasicInfoDoc> result = mongoOperations.find(query, BondBasicInfoDoc.class);
        return result;
    }

    private void calcPriceCountRelated(BondFavoriteProfileVO bondFavoriteProfileVO) {
        // price count
        Query priceQuery = new Query();
        priceQuery.addCriteria(Criteria.where("bondUniCode").is(bondFavoriteProfileVO.getBondUniCode()));
        priceQuery.with(new Sort(Sort.Direction.DESC, "createTime"));
        List<BondDealDataDoc> priceList = mongoOperations.find(priceQuery, BondDealDataDoc.class);
        if (priceList != null && !priceList.isEmpty()) {
            Date priceDate = null;
            try {
                priceDate = SafeUtils.convertStringToDate(priceList.get(0).getCreateTime(), SafeUtils.DATE_TIME_FORMAT1);
            } catch (Exception ex) {
                LOG.warn(String.format("calcPriceCountRelated with invalid date format[%1$s], error[%2$s]",
                        priceList.get(0).getCreateTime()), ex.getMessage());
            }
            bondFavoriteProfileVO.setUpdateTime(priceDate);
        }
    }

    /**
     * 通过限定条件获取关注债券的未读消息数
     *
     * @param userId
     * @param groupId
     * @param favoriteId
     * @param dateDiff
     * @param eventTypes
     * @return
     */
    private Long getFavoriteNewMsgCountByConstraint(Integer userId, Integer groupId, Integer favoriteId,
                                                    Integer dateDiff, List<Long> eventTypes) {
        // 按照bookmark和createTime取得的粗略未读消息数量
        List<Long> newMsgIdList = favoriteDAO.getNewMsgIdListByConstraint(userId, groupId, favoriteId, dateDiff, eventTypes);
        // 取得单独标记的已读消息
        Long newMsgCount = this.getEventMsgCountByNewMsgIdList(newMsgIdList);
        return newMsgCount;
    }

    public List<BondFavoriteRadarSchema> getRadarNodes(Long radarType) {
        List<BondFavoriteRadarSchema> result;
        if (radarType.equals(0L)) {
            result = bondFavRadarSchemaRepo.findAllByParentIdAndStatus(0L, 1);
        } else {
            result = bondFavRadarSchemaRepo.findAllByParentIdAndStatus(radarType, 1);
        }
        return result;
    }

    private Object reflectCommonRadarDoc(Integer userId, Long favoriteId, BondFavoriteRadarMapping radar,
                                         Map<Long, BondFavorite> favId2ItemMap, Map<Long, Long> bondCode2IssuerIdMap,
                                         Integer notifiedEnable, Date currDate) {
        Object result = null;
        if (!Constants.RADAR_MONGO_MAP.containsKey(radar.getRadarId())) {
            return result;
        }
        String beanName = Constants.RADAR_MONGO_MAP.get(radar.getRadarId());
        try {
            result = context.getBean(beanName);
            BeanUtils.copyProperties(radar, result);
            result.getClass().getMethod("setFavoriteId", Long.class).invoke(result, favoriteId);
            result.getClass().getMethod("setCreateTime", Date.class).invoke(result, currDate);
            result.getClass().getMethod("setNotifiedEnable", Integer.class).invoke(result, notifiedEnable);
            result.getClass().getMethod("setUserId", Integer.class).invoke(result, userId);
            if (favId2ItemMap.containsKey(favoriteId)) {
                BondFavorite favorite = favId2ItemMap.get(favoriteId);
                Long bondUniCode = favorite.getBondUniCode();
                Integer openInterest = favorite.getOpeninterest();
                result.getClass().getMethod("setBondUniCode", Long.class).invoke(result, bondUniCode);
                result.getClass().getMethod("setOpeninterest", Integer.class).invoke(result, openInterest);
                if (bondCode2IssuerIdMap.containsKey(bondUniCode)) {
                    Long issuerId = bondCode2IssuerIdMap.get(bondUniCode);
                    result.getClass().getMethod("setComUniCode", Long.class).invoke(result, issuerId);
                }
            }
        } catch (Exception ex) {
            LOG.error("reflectCommonRadarDoc: error is " + ex.getMessage());
        }
        return result;
    }

    @Transactional
    public synchronized void deleteBondFromGroup(Long userId, Long bondUniCode) {
        Date currDate = SafeUtils.getCurrentTime();
        // 根据favorite列表删除各个radar缓存，然后删除各个雷达，然后删除favorite，一气呵成
        List<BondFavorite> favList = favoriteRepo.findAllByBondUniCodeAndIsDelete(bondUniCode, 0);
        if (favList.isEmpty()) {
            return;
        }
        List<Long> favIdList = favList.stream().map(item -> SafeUtils.getLong(item.getFavoriteId()))
                .collect(Collectors.toList());
        if (!favIdList.isEmpty()) {
            // 清除mongo雷达缓存
            Query query = new Query();
            query.addCriteria(Criteria.where("favoriteId").in(favIdList));
            mongoOperations.remove(query, BondFavMaturityIdxDoc.class);
            mongoOperations.remove(query, BondFavRatingIdxDoc.class);
            mongoOperations.remove(query, BondFavSentimentIdxDoc.class);
            mongoOperations.remove(query, BondFavOtherIdxDoc.class);
            mongoOperations.remove(query, BondFavPriceIdxDoc.class);
            mongoOperations.remove(query, BondFavFinaIdxDoc.class);

            // 清除数据库雷达记录(无关普通雷达)
            favPriceIndexRepo.deleteByFavoriteIdIn(favIdList);
            favFinaIndexRepo.deleteByFavoriteIdIn(favIdList);

            // 逻辑删除所有关注债券
            favList.stream().forEach(fav -> {
                fav.setIsDelete(1);
                fav.setUpdateTime(currDate);
            });
            favoriteRepo.save(favList);
        }
    }

    @Transactional
    public synchronized void deleteFavorite(Long favoriteId) {
        // 合法性验证
        BondFavorite fav = favoriteRepo.findOneByFavoriteIdAndIsDelete(SafeUtils.getInteger(favoriteId), 0);
        if (fav == null) {
            LOG.warn(String.format("deleteFavorite failed with favoriteId[%2$d]", favoriteId));
            return;
        }
        // 清除mongo雷达缓存
        Query query = new Query();
        query.addCriteria(Criteria.where("favoriteId").is(favoriteId));
        mongoOperations.remove(query, BondFavMaturityIdxDoc.class);
        mongoOperations.remove(query, BondFavRatingIdxDoc.class);
        mongoOperations.remove(query, BondFavSentimentIdxDoc.class);
        mongoOperations.remove(query, BondFavOtherIdxDoc.class);
        mongoOperations.remove(query, BondFavPriceIdxDoc.class);
        mongoOperations.remove(query, BondFavFinaIdxDoc.class);
        // 清除数据库雷达记录(无关普通雷达)
        favPriceIndexRepo.deleteByFavoriteId(favoriteId);
        favFinaIndexRepo.deleteByFavoriteId(favoriteId);
        // 逻辑删除该关注债券
        fav.setIsDelete(1);
        fav.setUpdateTime(SafeUtils.getCurrentTime());
        favoriteRepo.save(fav);
    }

    /**
     * 新增普通雷达的缓存（不需要入库）
     *
     * @param groupId
     * @param newFavList
     */
    private void addNewCommonRadarDoc(Integer userId, Long groupId, List<BondFavorite> newFavList, Integer notifiedEnable, Date currDate) {
        List<Object> resultList = new ArrayList<>();
        List<BondFavoriteRadarMapping> defaultCommonRadarList = favRadarMappingRepo.findAllByGroupId(groupId);
        // favId->BondFavorite缓存
        Map<Long, BondFavorite> favId2ItemMap = newFavList.stream()
                .collect(Collectors.toMap(fav -> SafeUtils.getLong(fav.getFavoriteId()), item -> item));
        // bondCode->issuerId缓存
        List<Long> bondUniCodeList = newFavList.stream().map(BondFavorite::getBondUniCode).collect(Collectors.toList());
        Query query = new Query(Criteria.where("bondUniCode").in(bondUniCodeList));
        query.fields().include("bondUniCode").include("issuerId");
        List<BondBasicInfoDoc> basicInfoDocList = mongoOperations.find(query, BondBasicInfoDoc.class);
        Map<Long, Long> bondCode2IssuerIdMap = basicInfoDocList.stream()
                .collect(Collectors.toMap(BondBasicInfoDoc::getBondUniCode, BondBasicInfoDoc::getIssuerId));
        // 处理
        newFavList.stream().forEach(fav -> {
            defaultCommonRadarList.stream().forEach(rdr -> {
                Object commonRadarDoc = this.reflectCommonRadarDoc(userId, SafeUtils.getLong(fav.getFavoriteId()), rdr,
                        favId2ItemMap, bondCode2IssuerIdMap, notifiedEnable, currDate);
                if (commonRadarDoc != null) {
                    resultList.add(commonRadarDoc);
                }
            });
        });
        if (!resultList.isEmpty()) {
            mongoOperations.insertAll(resultList);
        }
    }

    private void addNewFavFinaRadars(Integer userId, Long groupId, List<BondFavorite> favList,
                                     Map<Long, Long> favId2BondCodeMap, Map<Long, Long> bondCode2IssuerIdMap,
                                     Map<Long, Boolean> bondCode2IsNotFinaCompBondMap, Integer notifiedEnable, Date currDate) {
        List<BondFavoriteFinaIndex> favFinaIndexList = new ArrayList<>();
        List<BondFavoriteFinaIndex> defaultGroupFinaIndexList = favFinaIndexRepo.findAllByGroupIdAndFavoriteId(groupId, 0L);
        favList.stream().forEach(fav -> {
            Long issuerId = bondCode2IssuerIdMap.getOrDefault(fav.getBondUniCode(), 0L);
            boolean isNotFinaCompBond = bondCode2IsNotFinaCompBondMap.getOrDefault(issuerId, false);
            defaultGroupFinaIndexList.stream().forEach(item -> {
                // 如果不是非金融企业债，则只能匹配市场指标
                if (item.getFinaSubType() != null && (isNotFinaCompBond || item.getFinaSubType() == 1)) {
                    BondFavoriteFinaIndex favFinaIndex = new BondFavoriteFinaIndex();
                    BeanUtils.copyProperties(item, favFinaIndex);
                    favFinaIndex.setId(null);
                    favFinaIndex.setFavoriteId(SafeUtils.getLong(fav.getFavoriteId()));
                    favFinaIndex.setCreateTime(currDate);
                    favFinaIndexList.add(favFinaIndex);
                }
            });
        });
        // 价格指标批量入库
        this.batchInsertFinaRadarListByJdbc(favFinaIndexList);
        // 生成缓存，更新mongoDB
        List<Object> finaRadarCacheList = this.convertToFinaMongoRadarList(userId, groupId, favFinaIndexList, favId2BondCodeMap, bondCode2IssuerIdMap, notifiedEnable);
        if (!finaRadarCacheList.isEmpty()) {
            mongoOperations.insertAll(finaRadarCacheList);
        }

    }

    private void addNewFavPriceRadars(Integer userId, Long groupId, List<BondFavorite> favList,
                                      Map<Long, Long> favId2BondCodeMap, Integer notifiedEnable, Date currDate) {
        List<BondFavoritePriceIndex> favPriceIndexList = new ArrayList<>();
        List<BondFavoritePriceIndex> defaultGroupPriceIndexList = favPriceIndexRepo.findAllByGroupIdAndFavoriteId(groupId, 0L);
        favList.stream().forEach(fav -> {
            defaultGroupPriceIndexList.stream().forEach(item -> {
                BondFavoritePriceIndex favPriceIndex = new BondFavoritePriceIndex();
                BeanUtils.copyProperties(item, favPriceIndex);
                favPriceIndex.setId(null);
                favPriceIndex.setFavoriteId(SafeUtils.getLong(fav.getFavoriteId()));
                favPriceIndex.setCreateTime(currDate);
                favPriceIndexList.add(favPriceIndex);
            });
        });
        // 价格指标批量入库
        this.batchInsertPriceRadarListByJdbc(favPriceIndexList);
        // 生成缓存，更新mongoDB
        List<Object> priceRadarCacheList = this.convertToPriceMongoRadarList(userId, groupId, favPriceIndexList, favId2BondCodeMap, notifiedEnable);
        if (!priceRadarCacheList.isEmpty()) {
            mongoOperations.insertAll(priceRadarCacheList);
        }
    }

    private List<BondFavorite> extractFavoriteList(Long groupId, Integer userId, List<Long> bondIdList, Date currDate) {
        List<BondFavorite> result = new ArrayList<>();
        if (bondIdList != null) {
            bondIdList.stream().forEach(id -> {
                BondFavorite favorite = new BondFavorite();
                favorite.setBondUniCode(id);
                favorite.setGroupId(SafeUtils.getInteger(groupId));
                favorite.setUserId(SafeUtils.getInt(userId));
                favorite.setCreateTime(currDate);
                favorite.setUpdateTime(currDate);
                favorite.setIsDelete(0);
                favorite.setOpeninterest(0);
                favorite.setBookmark(0L);
                favorite.setBookmarkUpdateTime(currDate);
                favorite.setRemark("");
                result.add(favorite);
            });
        }
        return result;
    }

    public Integer getUnitByFinaCode(Long userId, String finaCode) {
        Integer result = 0;
        BondFieldGroupMappingDoc fieldGroupMapping = bondFieldGroupMappingDocRepo.findByColumnName(finaCode);
        if (fieldGroupMapping != null)
            result = fieldGroupMapping.getCompanyType();
        return result;
    }

    /**
     * 修改关注投组下所有的债券消息为已读
     *
     * @return
     */
    public Long updateGroupMsgReadStatus(Long groupId) {
        Date currDate = SafeUtils.getCurrentTime();
        // 获取投组中的债券列表
        List<BondFavorite> favList = favoriteRepo.findByGroupId(SafeUtils.getInteger(groupId));
        if (favList.isEmpty()) {
            LOG.error(String.format("updateGroupMsgReadStatus: cannot find favList with groupId[%1$d]", groupId));
            return null;
        }
        Query msgQuery = new Query();
        msgQuery.addCriteria(Criteria.where("groupId").is(groupId));
        // fix issue： 改成按照id倒序，取最大id
        msgQuery.with(new Sort(Sort.Direction.DESC, "_id"));
        BondNotificationMsgDoc latestGroupMsg = mongoOperations.findOne(msgQuery.limit(1), BondNotificationMsgDoc.class);
        if (latestGroupMsg == null) {
            LOG.error(String.format("updateGroupMsgReadStatus: cannot find groupMsg with groupId[%1$d]", groupId));
            return null;
        }
        Long latestBookmark = latestGroupMsg.getId();
        favList.stream().forEach(item -> {
            item.setBookmark(latestBookmark);
            item.setBookmarkUpdateTime(currDate);
        });
        favoriteRepo.save(favList);

        return latestBookmark;
    }

    /**
     * 是否是“非金融企业债”,true-非金融企业债
     *
     * @param issuerId
     * @return
     */
    private boolean checkIsNotFinaCompBond(Long issuerId) {
        boolean result = false;
        try {
            result = !Constants.FINA_COMP_BOND_LIST.contains(indicatorDAO.getModelNameByCompId(issuerId));
        } catch (Exception ex) {
            return result;
        }
        return result;
    }

    @Transactional
    public void updateGroupNotified(Integer userId, Integer groupId, Integer notifiedEnable) {
        Date currDate = SafeUtils.getCurrentTime();
        BondFavoriteGroup favoriteGroup = favoriteGroupRepo.findOne(groupId);
        if (favoriteGroup == null) {
            //throw new BusinessException("该关注组不存在:" + groupId);
            return;
        }
        favoriteGroup.setUpdateTime(currDate);
        favoriteGroup.setNotifiedEnable(notifiedEnable);

        favoriteGroupRepo.save(favoriteGroup);
        updateGroupIdxDoc(userId, groupId, notifiedEnable);
        updateUserOprRecord(userId);
    }

    private void updateUserOprRecord(Integer userId) {
        UserOprRecord userOprRecord = userOprRecordRepo.findOne(userId);
        if (null != userOprRecord) {
            Long bookmark = findMaxCardMsgBookmarkCursor(userId);
            if (bookmark > 0) {
                userOprRecord.setBookmarkCursor(bookmark);
                userOprRecordRepo.save(userOprRecord);
            }
        }
    }

    private Long findMaxCardMsgBookmarkCursor(Integer userId) {
        Long cardMsgBookmark = 0L;
        Query query = new Query();
        PageRequest request = new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "id"));
        query.addCriteria(Criteria.where("userId").is(userId).and("_id").gt(0L));
        query.with(request);

        BondNotificationCardMsgDoc bnCardMsgDoc = mongoOperations.findOne(query.limit(1), BondNotificationCardMsgDoc.class);
        if (null != bnCardMsgDoc) {
            cardMsgBookmark = bnCardMsgDoc.getId();
        }
        return cardMsgBookmark;
    }

    private void updateGroupIdxDoc(Integer userId, Integer groupId, Integer notifiedEnable) {
        Constants.INDEX_DOC_LISTS.stream().forEach(collectionName -> {
            WriteResult wr = mongoOperations.updateMulti(new Query(Criteria.where("userId").is(userId).and("groupId").is(groupId)),
                    Update.update("notifiedEnable", notifiedEnable), collectionName);
            LOG.info("updateGroupIdxDoc collectionName:" + collectionName + ", WriteResult num:" + wr.getN());
        });
    }

    public synchronized String deleteFavorites(Integer userId, FavoriteUpdateList param) {
        LOG.info("deleteFavorites userId:" + userId);
        if (!param.getFavoriteIds().isEmpty()) {
            param.getFavoriteIds().stream().forEach(favId -> this.deleteFavorite(SafeUtils.getLong(favId)));
        }
        return "success";
    }

    /**
     * 创建投组
     *
     * @param userId
     * @param req
     * @return
     */
    @Transactional
    public synchronized Long createGroup(Integer userId, FavoriteGroupReq req) {
        if (req != null && req.getBonds() != null && req.getBonds().size() > Constants.GROUP_FAV_MAX_COUNT) {
            //throw new BusinessException(Constants.GROUP_FAV_OVER_MAX_ERROR_MSG);
            return null;
        }
        BondFavoriteGroup group = favoriteGroupRepo.findOneByUserIdAndGroupNameAndIsDelete(userId, req.getGroupName(), 0);
        if (group != null) {
            LOG.error("createDefaultGroup error, groupName[%2$s] already exist with user[%1$d]", userId, req.getGroupName());
            //throw new BusinessException("该投组名已存在");
            return null;
        }
        Date currDate = SafeUtils.getCurrentTime();
        List<Object> allRadarCacheList = new ArrayList<>();
        List<BondFavoriteRadarMapping> allCommonRadarList = new ArrayList<>();
        List<BondFavoritePriceIndex> allPriceRadarList = new ArrayList<>();
        List<BondFavoriteFinaIndex> allFinaRadarList = new ArrayList<>();
        // 保存投组和其中的关注债券列表
        BondFavoriteGroup favGroup = this.getSavedGroupFromReq(userId, req, currDate);
        Long groupIdL = SafeUtils.getLong(favGroup.getGroupId());
        Integer notifiedEnable = req.getNotifiedEnable();
        // 清理表以及mongo缓存数据
        if (req.getGroupId() != null) {
            this.clearGroupRadarDBWithCache(groupIdL);
        }
        // 设置groupId
        req.setGroupId(groupIdL);
        List<BondFavorite> savedFavoriteList = this.getSavedFavoriteListFromReq(userId, favGroup.getGroupId(), req, currDate);
        try {
            this.assemblyAndSaveGroupRadarList(userId, groupIdL, currDate, notifiedEnable, req, allRadarCacheList,
                    savedFavoriteList, allCommonRadarList, allPriceRadarList, allFinaRadarList);
        } catch (Exception ex) {
            LOG.error(String.format("createGroup error, userId[%1$d] with [%2$s]", userId, ex.getMessage()));
            //throw new BusinessException("创建投组出错");
            return null;
        }
        allCommonRadarList.clear();
        allPriceRadarList.clear();
        allFinaRadarList.clear();
        allRadarCacheList.clear();
        return groupIdL;
    }

    /**
     * 更新投组，提醒条件先删除再添加
     *
     * @param userId
     * @param groupId
     * @param req
     * @return
     */
    @Transactional
    public synchronized String updateGroup(Integer userId, Long groupId, FavoriteGroupReq req) {
        Date currDate = SafeUtils.getCurrentTime();
        req.setGroupId(groupId);
        List<Object> allRadarCacheList = new ArrayList<>();
        List<BondFavoriteRadarMapping> allCommonRadarList = new ArrayList<>();
        List<BondFavoritePriceIndex> allPriceRadarList = new ArrayList<>();
        List<BondFavoriteFinaIndex> allFinaRadarList = new ArrayList<>();
        // 保存投组和其中的关注债券列表
        BondFavoriteGroup group = this.getSavedGroupFromReq(userId, req, currDate);
        if (group == null) {
            LOG.error(String.format("updateGroup: cannot find group with id[%1$d] and userId[%2$d]", req.getGroupId(), userId));
            //throw new BusinessException("invalid group");
            return null;
        }
        Long groupIdL = groupId;
        Integer notifiedEnable = req.getNotifiedEnable();
        req.setGroupId(groupIdL);
        // 清理表以及mongo缓存数据
        this.clearGroupRadarDBWithCache(groupIdL);
        List<BondFavorite> savedFavoriteList = favoriteRepo.findByGroupId(group.getGroupId());
        try {
            this.assemblyAndSaveGroupRadarList(userId, groupIdL, currDate, notifiedEnable, req, allRadarCacheList,
                    savedFavoriteList, allCommonRadarList, allPriceRadarList, allFinaRadarList);
        } catch (Exception ex) {
            LOG.error(String.format("updateGroup: userId[%1$d] with [%2$s]", userId, ex.getMessage()));
            //throw new BusinessException("设置投组出错");
            return null;
        }
        allCommonRadarList.clear();
        allPriceRadarList.clear();
        allFinaRadarList.clear();
        allRadarCacheList.clear();
        return "success";
    }

    /**
     * 删除投组，以及所有相关记录
     *
     * @param userId
     * @param groupIdL
     * @return
     */
    @Transactional
    public synchronized String deleteGroup(Integer userId, Long groupIdL) {
        // 合法性验证
        Integer groupId = SafeUtils.getInteger(groupIdL);
        if (favoriteGroupRepo.findOneByGroupIdAndUserIdAndIsDelete(groupId, userId, 0) == null) {
            LOG.warn(String.format("deleteGroup failed with userId[%1$d] and groupId[%2$d]", userId, groupId));
            return "failed";
        }
        // 获取投组中的BondFavorite列表
        String currDateStr = SafeUtils.getCurrDateFmtStr(SafeUtils.DATE_TIME_FORMAT1);
        List<BondFavorite> favoriteList = favoriteRepo.findByGroupId(groupId);
        // 逻辑删除投组
        favoriteGroupRepo.logicDeleteByGroupId(groupId, currDateStr);
        if (!favoriteList.isEmpty()) {
            // 逻辑删除投组中的关注债券列表
            favoriteRepo.logicDeleteByGroupId(userId, groupId, currDateStr);
//			List<Long> favIdList = favoriteList.stream().map(item -> SafeUtils.getLong(item.getFavoriteId()))
//					.collect(Collectors.toList());
//			// 删除私人价格和财务指标条件(包含半全组)
//			favPriceIndexRepo.deleteByFavoriteIdIn(favIdList);
//			favFinaIndexRepo.deleteByFavoriteIdIn(favIdList);
            List<Integer> favIdList = favoriteList.stream().map(BondFavorite::getFavoriteId).collect(Collectors.toList());
            priceIndexDAO.batchDeleteByFavIdList(favIdList);
            finaIndexDAO.batchDeleteByFavIdList(favIdList);
        }
        // 删除投组全组提醒条件
//		favPriceIndexRepo.deleteByGroupId(groupIdL);
//		favFinaIndexRepo.deleteByGroupId(groupIdL);
//		favRadarMappingRepo.deleteByGroupId(groupIdL);
        priceIndexDAO.batchDeleteByGroupIdList(Arrays.asList(groupId));
        finaIndexDAO.batchDeleteByGroupIdList(Arrays.asList(groupId));
        radarMappingDAO.batchDeleteByGroupIdList(Arrays.asList(groupId));

        // 删除MongoDB缓存条件
        Query query = new Query();
        query.addCriteria(Criteria.where("groupId").is(groupIdL));
        mongoOperations.remove(query, BondFavPriceIdxDoc.class);
        mongoOperations.remove(query, BondFavFinaIdxDoc.class);
        mongoOperations.remove(query, BondFavMaturityIdxDoc.class);
        mongoOperations.remove(query, BondFavRatingIdxDoc.class);
        mongoOperations.remove(query, BondFavSentimentIdxDoc.class);
        mongoOperations.remove(query, BondFavOtherIdxDoc.class);

        return "success";
    }

    /**
     * 生成并保存投组，其中的关注债券(新建)，普通提醒条件，价格以及财务指标，并缓存
     *
     * @param userId
     * @param groupIdL
     * @param currDate
     * @param notifiedEnable
     * @param req
     * @param allRadarCacheList
     * @param savedFavoriteList
     * @param allCommonRadarList
     * @param allPriceRadarList
     * @param allFinaRadarList
     */
    private void assemblyAndSaveGroupRadarList(Integer userId, Long groupIdL, Date currDate, Integer notifiedEnable,
                                               FavoriteGroupReq req, List<Object> allRadarCacheList,
                                               List<BondFavorite> savedFavoriteList,
                                               List<BondFavoriteRadarMapping> allCommonRadarList,
                                               List<BondFavoritePriceIndex> allPriceRadarList,
                                               List<BondFavoriteFinaIndex> allFinaRadarList) {
        List<Long> savedFavIdList = savedFavoriteList.stream().map(item -> SafeUtils.getLong(item.getFavoriteId()))
                .collect(Collectors.toList());
        // 保存普通雷达入库
        allCommonRadarList.addAll(req.getCommonRadars());
        allCommonRadarList.stream().forEach(item -> {
            item.setGroupId(groupIdL);
            item.setCreateTime(currDate);
        });
        // 生成价格和财务指标全组雷达
        allPriceRadarList.addAll(this.generatePublicPriceRadarList(groupIdL, req.getPriceRadars(), currDate));
        allFinaRadarList.addAll(this.generatePublicFinaRadarList(groupIdL, req.getFinaRadars(), currDate));
        if (!savedFavoriteList.isEmpty()) {
            // 生成半全组价格和财务指标,并保存
            List<BondFavoritePriceIndex> protectedPriceRadarList = this.generateProtectedPriceRadarList(allPriceRadarList, savedFavIdList);
            List<BondFavoriteFinaIndex> protectedFinaRadarList = this.generateProtectedFinaRadarList(allFinaRadarList, savedFavIdList);
            // 根据私人雷达过滤出半全局雷达，将其状态置为无效
            List<BondFavoritePriceIndex> privatePriceIndexList = favPriceIndexRepo.findAllByGroupIdAndFavoriteIdIn(0L, savedFavIdList);
            if (!privatePriceIndexList.isEmpty()) {
                protectedPriceRadarList.stream().forEach(item -> {
                    if (privatePriceIndexList.stream().filter(priceIdx -> priceIdx.getFavoriteId().equals(item.getFavoriteId()))
                            .anyMatch(fpi -> this.isInheritFromGlobalPriceRadar(item, fpi))) {
                        item.setStatus(0);
                    }
                });
            }
            List<BondFavoriteFinaIndex> privateFinaIndexList = favFinaIndexRepo.findAllByGroupIdAndFavoriteIdIn(0L, savedFavIdList);
            if (!privateFinaIndexList.isEmpty()) {
                protectedFinaRadarList.stream().forEach(item -> {
                    if (privateFinaIndexList.stream().filter(finaIdx -> finaIdx.getFavoriteId().equals(item.getFavoriteId()))
                            .anyMatch(fi -> this.isInheritFromGlobalFinaRadar(item, fi))) {
                        item.setStatus(0);
                    }
                });
            }
            // 放入集合表示需要入库
            allPriceRadarList.addAll(protectedPriceRadarList);
            allFinaRadarList.addAll(protectedFinaRadarList);

            // 缓存数据，避免每次个类型的条件都要查询一次
            List<BondBasicInfoDoc> basicDocList = bondBasicInfoRepo.findAllByBondUniCodeIn(savedFavoriteList.stream()
                    .map(BondFavorite::getBondUniCode).collect(Collectors.toList()));
            Map<Integer, Long> favIdIssuerIdMap = new HashMap<>();
            savedFavoriteList.stream().forEach(fav -> {
                if (basicDocList.stream().anyMatch(doc -> doc.getBondUniCode().equals(fav.getBondUniCode()))) {
                    Long issuerId = basicDocList.stream().filter(doc -> doc.getBondUniCode().equals(fav.getBondUniCode())).findFirst().get().getIssuerId();
                    favIdIssuerIdMap.put(fav.getFavoriteId(), issuerId);
                }
            });
            Map<Long, Long> favIdCodeMap = new HashMap<>();
            savedFavoriteList.stream().forEach(fav -> favIdCodeMap.put(SafeUtils.getLong(fav.getFavoriteId()), fav.getBondUniCode()));

            // 生成所有缓存,并保存
            allRadarCacheList.addAll(this.generateRadarCacheList(userId, currDate, notifiedEnable, favIdIssuerIdMap, favIdCodeMap,
                    savedFavoriteList, allCommonRadarList, protectedPriceRadarList, protectedFinaRadarList));
            // 获取私人雷达，放入缓存
            allRadarCacheList.addAll(this.generatePrivateRadarCacheList(userId, groupIdL, currDate, notifiedEnable,
                    favIdIssuerIdMap, favIdCodeMap, savedFavIdList));
        }
        // 缓存
        if (!allRadarCacheList.isEmpty()) {
            mongoOperations.insertAll(allRadarCacheList);
        }
        // 入库
        this.batchInsertCommonRadarListByJdbc(allCommonRadarList);
        this.batchInsertPriceRadarListByJdbc(allPriceRadarList);
        this.batchInsertFinaRadarListByJdbc(allFinaRadarList);
    }

    /**
     * 清理表以及mongo缓存数据
     *
     * @param groupId
     */
    private void clearGroupRadarDBWithCache(Long groupId) {
        // 清理表数据
        favRadarMappingRepo.deleteByGroupId(groupId);
        favPriceIndexRepo.deleteByGroupId(groupId);
        favFinaIndexRepo.deleteByGroupId(groupId);
        // 清理缓存数据
        Query query = new Query();
        query.addCriteria(Criteria.where("groupId").is(groupId));
        mongoOperations.remove(query, BondFavMaturityIdxDoc.class);
        mongoOperations.remove(query, BondFavRatingIdxDoc.class);
        mongoOperations.remove(query, BondFavSentimentIdxDoc.class);
        mongoOperations.remove(query, BondFavOtherIdxDoc.class);
        mongoOperations.remove(query, BondFavPriceIdxDoc.class);
        mongoOperations.remove(query, BondFavFinaIdxDoc.class);

    }

    private BondFavoriteGroup getSavedGroupFromReq(Integer userId, FavoriteGroupReq req, Date currDate) {
        BondFavoriteGroup savedGroup;
        if (req.getGroupId() == null) {
            BondFavoriteGroup group = new BondFavoriteGroup();
            BeanUtils.copyProperties(req, group);
            group.setUserId(userId);
            group.setGroupType(1);
            group.setIsDelete(0);
            group.setCreateTime(currDate);
            group.setUpdateTime(currDate);
            savedGroup = favoriteGroupRepo.save(group);
        } else {
            BondFavoriteGroup group = favoriteGroupRepo.findOneByGroupIdAndIsDelete(SafeUtils.getInteger(req.getGroupId()), 0);
            if (group == null) return null;
            Date createTime = group.getCreateTime();
            BeanUtils.copyProperties(req, group);
            group.setCreateTime(createTime);
            group.setUpdateTime(currDate);
            savedGroup = group;
        }
        return savedGroup;
    }

    private List<BondFavorite> getSavedFavoriteListFromReq(Integer userId, Integer groupId, FavoriteGroupReq req, Date currDate) {
        // 再重新生成
        List<BondFavorite> result = new ArrayList<>();
        if (req.getBonds() != null) {
            req.getBonds().stream().forEach(item -> {
                BondFavorite favorite = new BondFavorite();
                favorite.setBondUniCode(item.getBondUniCode());
                favorite.setGroupId(groupId);
                favorite.setUserId(userId);
                favorite.setCreateTime(currDate);
                favorite.setUpdateTime(currDate);
                favorite.setIsDelete(0);
                favorite.setOpeninterest(item.getOpeninterest());
                favorite.setBookmark(0L);
                favorite.setBookmarkUpdateTime(currDate);
                favorite.setRemark("");
                result.add(favorite);
            });

        }
        return favoriteRepo.save(result);
    }

    private List<BondFavoritePriceIndex> generatePublicPriceRadarList(Long groupId, List<BondFavoritePriceIndex> priceIndexList, Date currDate) {
        // 保存全局价格, favoriteId置为0
        List<BondFavoritePriceIndex> result = new ArrayList<>();
        if (priceIndexList != null && !priceIndexList.isEmpty()) {
            result = priceIndexList.stream()
                    .filter(item -> item.getIndexValue() != null).collect(Collectors.toList());
            result.stream().forEach(item -> {
                item.setGroupId(groupId);
                item.setFavoriteId(0L);
                item.setStatus(1);
                item.setCreateTime(currDate);
            });
        }
        return result;
    }

    private List<BondFavoriteFinaIndex> generatePublicFinaRadarList(Long groupId, List<BondFavoriteFinaIndex> finaIndexList, Date currDate) {
        // 保存全局财务指标, favoriteId置为0
        if (finaIndexList != null && !finaIndexList.isEmpty()) {
            finaIndexList.stream().forEach(item -> {
                item.setGroupId(groupId);
                item.setFavoriteId(0L);
                item.setStatus(1);
                item.setCreateTime(currDate);
            });
        }
        return finaIndexList;
    }

    private List<BondFavoritePriceIndex> generateProtectedPriceRadarList(List<BondFavoritePriceIndex> groupPriceRadarList, List<Long> favIdList) {
        List<BondFavoritePriceIndex> result = new ArrayList<>();
        // 生成半组雷达
        favIdList.stream().forEach(favId -> {
            groupPriceRadarList.stream().forEach(gpi -> {
                BondFavoritePriceIndex idx = new BondFavoritePriceIndex();
                BeanUtils.copyProperties(gpi, idx);
                idx.setFavoriteId(favId);
                result.add(idx);
            });
        });
        return result;
    }

    private List<BondFavoriteFinaIndex> generateProtectedFinaRadarList(List<BondFavoriteFinaIndex> groupFinaRadarList, List<Long> favIdList) {
        List<BondFavoriteFinaIndex> result = new ArrayList<>();
        // 生成半组雷达
        favIdList.stream().forEach(favId -> {
            groupFinaRadarList.stream().forEach(gpi -> {
                BondFavoriteFinaIndex idx = new BondFavoriteFinaIndex();
                BeanUtils.copyProperties(gpi, idx);
                idx.setFavoriteId(favId);
                result.add(idx);
            });
        });
        return result;
    }

    private List<Object> generateRadarCacheList(Integer userId, Date currDate, Integer notifiedEnable,
                                                Map<Integer, Long> favIdIssuerIdMap, Map<Long, Long> favIdCodeMap,
                                                List<BondFavorite> newFavoriteList,
                                                List<BondFavoriteRadarMapping> groupCommonRadarList,
                                                List<BondFavoritePriceIndex> favGroupPriceIndexList,
                                                List<BondFavoriteFinaIndex> favGroupFinaIndexList) {
        List<Object> allRadarCacheList = new ArrayList<>();
        // 普通缓存
        groupCommonRadarList.stream().forEach(rdr -> {
            newFavoriteList.stream().forEach(fav -> {
                Object commonRadarDoc = this.reflectCommonRadarDoc1(userId, fav, rdr, currDate, notifiedEnable, favIdIssuerIdMap);
                if (commonRadarDoc != null) {
                    allRadarCacheList.add(commonRadarDoc);
                }
            });
        });
        // 价格缓存, status为1
        favGroupPriceIndexList.stream().filter(pi -> pi.getStatus() == 1).forEach(item -> {
            BondFavPriceIdxDoc priceIdxDoc = new BondFavPriceIdxDoc();
            BeanUtils.copyProperties(item, priceIdxDoc);
            priceIdxDoc.setNotifiedEnable(notifiedEnable);
            priceIdxDoc.setUserId(userId);
            priceIdxDoc.setIsDelete(0);
            priceIdxDoc.setBondUniCode(favIdCodeMap.get(item.getFavoriteId()));
            allRadarCacheList.add(priceIdxDoc);
        });
        // 财务指标缓存, status为1
        favGroupFinaIndexList.stream().filter(pi -> pi.getStatus() == 1).forEach(item -> {
            BondFavFinaIdxDoc finaIdxDoc = new BondFavFinaIdxDoc();
            BeanUtils.copyProperties(item, finaIdxDoc);
            List<String> variables = ExpressionUtil.extractFieldsInExpression(item.getIndexCodeExpr());
            finaIdxDoc.setVariables(variables);
            finaIdxDoc.setNotifiedEnable(notifiedEnable);
            finaIdxDoc.setUserId(userId);
            finaIdxDoc.setIsDelete(0);
            finaIdxDoc.setBondUniCode(favIdCodeMap.get(item.getFavoriteId()));
            if (favIdIssuerIdMap != null && favIdIssuerIdMap.containsKey(SafeUtils.getInteger(item.getFavoriteId()))) {
                finaIdxDoc.setComUniCode(favIdIssuerIdMap.get(SafeUtils.getInteger(item.getFavoriteId())));
            }
            allRadarCacheList.add(finaIdxDoc);
        });

        return allRadarCacheList;
    }

    private List<Object> generatePrivateRadarCacheList(Integer userId, Long groupId, Date currDate, Integer notifiedEnable,
                                                       Map<Integer, Long> favIdIssuerIdMap, Map<Long, Long> favIdCodeMap, List<Long> savedFavIdList) {
        List<Object> result = new ArrayList<>();
        List<BondFavoritePriceIndex> favPriceIndexList = favPriceIndexRepo.findAllByGroupIdAndFavoriteIdIn(0L, savedFavIdList);
        List<BondFavoriteFinaIndex> favFinaIndexList = favFinaIndexRepo.findAllByGroupIdAndFavoriteIdIn(0L, savedFavIdList);
        // 价格缓存, status为1
        favPriceIndexList.stream().filter(pi -> pi.getStatus() == 1).forEach(item -> {
            BondFavPriceIdxDoc priceIdxDoc = new BondFavPriceIdxDoc();
            BeanUtils.copyProperties(item, priceIdxDoc);
            priceIdxDoc.setGroupId(groupId);
            priceIdxDoc.setNotifiedEnable(notifiedEnable);
            priceIdxDoc.setUserId(userId);
            priceIdxDoc.setIsDelete(0);
            priceIdxDoc.setCreateTime(currDate);
            priceIdxDoc.setBondUniCode(favIdCodeMap.get(item.getFavoriteId()));
            result.add(priceIdxDoc);
        });
        // 财务指标缓存, status为1
        favFinaIndexList.stream().filter(pi -> pi.getStatus() == 1).forEach(item -> {
            BondFavFinaIdxDoc finaIdxDoc = new BondFavFinaIdxDoc();
            BeanUtils.copyProperties(item, finaIdxDoc);
            finaIdxDoc.setGroupId(groupId);
            List<String> variables = ExpressionUtil.extractFieldsInExpression(item.getIndexCodeExpr());
            finaIdxDoc.setVariables(variables);
            finaIdxDoc.setNotifiedEnable(notifiedEnable);
            finaIdxDoc.setUserId(userId);
            finaIdxDoc.setIsDelete(0);
            finaIdxDoc.setCreateTime(currDate);
            finaIdxDoc.setBondUniCode(favIdCodeMap.get(item.getFavoriteId()));
            if (favIdIssuerIdMap != null && favIdIssuerIdMap.containsKey(item.getFavoriteId())) {
                finaIdxDoc.setComUniCode(favIdIssuerIdMap.get(item.getFavoriteId()));
            }
            result.add(finaIdxDoc);
        });
        return result;
    }

    private Object reflectCommonRadarDoc1(Integer userId, BondFavorite fav, BondFavoriteRadarMapping radar, Date currDate,
                                          Integer notifiedEnable, Map<Integer, Long> favIssuerIdMap) {
        Object result = null;
        if (!Constants.RADAR_MONGO_MAP.containsKey(radar.getRadarId())) {
            return result;
        }
        String beanName = Constants.RADAR_MONGO_MAP.get(radar.getRadarId());
        Integer favId = fav.getFavoriteId();
        Long favIdL = SafeUtils.getLong(fav.getFavoriteId());
        try {
            result = context.getBean(beanName);
            BeanUtils.copyProperties(radar, result);
            result.getClass().getMethod("setFavoriteId", Long.class).invoke(result, favIdL);
            result.getClass().getMethod("setCreateTime", Date.class).invoke(result, currDate);
            result.getClass().getMethod("setNotifiedEnable", Integer.class).invoke(result, notifiedEnable);
            result.getClass().getMethod("setUserId", Integer.class).invoke(result, userId);
            result.getClass().getMethod("setBondUniCode", Long.class).invoke(result, fav.getBondUniCode());
            result.getClass().getMethod("setOpeninterest", Integer.class).invoke(result, fav.getOpeninterest());
            if (favIssuerIdMap != null && favIssuerIdMap.containsKey(favId)) {
                result.getClass().getMethod("setComUniCode", Long.class).invoke(result, favIssuerIdMap.get(favId));
            }

        } catch (Exception ex) {
            LOG.error("reflectCommonRadarDoc: error is " + ex.getMessage());
        }
        return result;
    }

    @Transactional
    protected void batchInsertCommonRadarListByJdbc(List<BondFavoriteRadarMapping> originalRadarList) {
        if (originalRadarList == null || originalRadarList.isEmpty()) return;
        final List<BondFavoriteRadarMapping> tempList = originalRadarList;
        String sql = "INSERT INTO t_bond_favorite_radar_mapping(group_id, radar_id, threshold, create_time) VALUES(?,?,?,?);";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, tempList.get(i).getGroupId());
                ps.setLong(2, tempList.get(i).getRadarId());
                ps.setInt(3, tempList.get(i).getThreshold() == null ? 0 : tempList.get(i).getThreshold());
                ps.setObject(4, new java.sql.Timestamp(tempList.get(i).getCreateTime().getTime()));
            }

            @Override
            public int getBatchSize() {
                return tempList.size();
            }
        });
        // 按照groupId取出刚刚保存的数据
//		String formatSql = String.format("SELECT * FROM t_bond_favorite_radar_mapping WHERE group_id=%1$d", groupId);
//		return jdbcTemplate.query(formatSql, new BeanPropertyRowMapper<>(BondFavoriteRadarMapping.class));
    }

    @Transactional
    protected void batchInsertPriceRadarListByJdbc(List<BondFavoritePriceIndex> radarList) {
        final List<BondFavoritePriceIndex> tempList = radarList;
        String sql = "INSERT INTO t_bond_favorite_price_index(group_id, favorite_id, price_index, price_type," +
                " price_condi, index_value, index_unit, status, create_time) VALUES(?,?,?,?,?,?,?,?,?);";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, tempList.get(i).getGroupId());
                ps.setLong(2, tempList.get(i).getFavoriteId());
                ps.setInt(3, tempList.get(i).getPriceIndex());
                ps.setInt(4, tempList.get(i).getPriceType());
                ps.setInt(5, tempList.get(i).getPriceCondi());
                ps.setBigDecimal(6, tempList.get(i).getIndexValue());
                ps.setInt(7, tempList.get(i).getIndexUnit());
                ps.setInt(8, tempList.get(i).getStatus());
                ps.setObject(9, new java.sql.Timestamp(tempList.get(i).getCreateTime().getTime()));
            }

            @Override
            public int getBatchSize() {
                return tempList.size();
            }
        });
    }

    @Transactional
    protected void batchInsertFinaRadarListByJdbc(List<BondFavoriteFinaIndex> radarList) {
        final List<BondFavoriteFinaIndex> tempList = radarList;
        String sql = "INSERT INTO t_bond_favorite_fina_index(group_id, favorite_id, index_type, index_code_expr," +
                " index_name, fina_sub_type, index_value_type, index_value_nexus, index_value_unit, index_value_low," +
                " index_value_high, status, create_time) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, tempList.get(i).getGroupId());
                ps.setLong(2, tempList.get(i).getFavoriteId());
                ps.setInt(3, tempList.get(i).getIndexType());
                ps.setString(4, tempList.get(i).getIndexCodeExpr());
                ps.setString(5, tempList.get(i).getIndexName());
                ps.setInt(6, tempList.get(i).getFinaSubType() == null ? 0 : tempList.get(i).getFinaSubType());
                ps.setInt(7, tempList.get(i).getIndexValueType());
                ps.setInt(8, tempList.get(i).getIndexValueNexus());
                ps.setInt(9, tempList.get(i).getIndexValueUnit());
                ps.setBigDecimal(10, tempList.get(i).getIndexValueLow());
                ps.setBigDecimal(11, tempList.get(i).getIndexValueHigh());
                ps.setInt(12, tempList.get(i).getStatus());
                ps.setObject(13, new java.sql.Timestamp(tempList.get(i).getCreateTime().getTime()));
            }

            @Override
            public int getBatchSize() {
                return tempList.size();
            }
        });
    }

    /**
     * 获取投组的消息列表
     *
     * @return
     */
    public Page<BondSimpleNotiMsgVO> getGroupSimpleMsgWithRadarTypes(Integer userId, Integer groupId,
                                                                     Long radarType, Integer page, Integer limit) {
        // 获取有效的投组信息
        BondFavoriteGroup favGroup = favoriteGroupRepo.findOneByGroupIdAndIsDelete(groupId, 0);
        if (favGroup == null) {
            LOG.error(String.format("getGroupSimpleMsgWithRadarTypes: cannot find groupId[%1$d]", groupId));
            //throw new BusinessException("找不到有效投组");
            return null;
        }
        Long totalCount = 0L;
        List<BondSimpleNotiMsgVO> result = new ArrayList<>();
        List<BondFavorite> favList = favoriteRepo.findByGroupId(groupId);
        if (!favList.isEmpty()) {
            List<Long> radarTypes = radarType == 0 ? bondCombineDao.getAllRadarByParentId(0) : Arrays.asList(radarType);
            List<Long> subRadarTypeList = this.convertToSubRadarList(radarTypes);
            List<Long> bondIdList = favList.stream().map(BondFavorite::getBondUniCode).collect(Collectors.toList());
            totalCount = msgDAO.getCountOfPagedGroupMsgVOList(userId, groupId, bondIdList, subRadarTypeList);
            result = this.getPagedMsgVOListByGroupIdAndBondIdList(userId, groupId, favList,
                    subRadarTypeList, bondIdList, page, limit);
        }
        return new PageImpl<>(result, new PageRequest(page, limit), totalCount);
    }

    /**
     * 获取用户所有消息列表(按雷达类型分组)
     *
     * @return
     */
    public List<BondRadarMsgVO> getAllFavoriteMsg(Integer userId) {
        //获取所有父雷达列表
        List<BondFavoriteRadarSchema> radarTypeList = bondFavRadarSchemaRepo.findAllByParentIdAndStatus(0L, 1);
        List<BondRadarMsgVO> radarTypeVOList = radarTypeList.stream().map(vo -> {
            BondRadarMsgVO radarMsgVO = new BondRadarMsgVO();
            radarMsgVO.setRadarTypeId(vo.getId());
            radarMsgVO.setRadarTypeName(vo.getName());
            return radarMsgVO;
        }).collect(Collectors.toList());
        Query query = new Query(Criteria.where("userId").is(userId));
        List<BondMsgUserStatusDoc> readMsgList = mongoOperations.find(query, BondMsgUserStatusDoc.class);
        List<Long> readMsgIdList = readMsgList.stream().map(BondMsgUserStatusDoc::getNotifyMsgId).collect(Collectors.toList());
        //获取未读消息和雷达关联信息
        List<BondRadarMsgVO> tempList = bondCombineDao.getAllFavoriteMsg(userId, readMsgIdList);
        if (!tempList.isEmpty()) {
            tempList.stream().collect(Collectors.groupingBy(BondRadarMsgVO::getRadarTypeId))
                    .forEach((type, itemList) -> {
                        String msgContent = itemList.get(0).getLastMsgContent();
                        Integer msgCount = itemList.stream().mapToInt(s -> s.getRadarMsgNumber()).sum();
                        BondRadarMsgVO data = radarTypeVOList.stream().
                                filter(s -> s.getRadarTypeId() == type).findFirst().get();
                        data.setLastMsgContent(msgContent);
                        data.setRadarMsgNumber(msgCount);
                    });
        }
        return radarTypeVOList;
    }


    /**
     * 获取用户所有消息列表(按雷达类型过滤)
     *
     * @return
     */
    public Page<BondSimpleNotiMsgVO> getAllFavoriteMsgByRadar(Integer userId, Long radarType, Integer page, Integer limit) {
        List<Long> radarTypeList = this.convertToSubRadarList(Arrays.asList(radarType));
        Long totalCount = bondCombineDao.getCountOfPagedMsgByRadar(userId, radarTypeList);
        List<BondSimpleNotiMsgVO> result = bondCombineDao.getAllFavoriteMsgByRadar(userId, radarTypeList, page, limit);
        return new PageImpl<>(result, new PageRequest(page, limit), totalCount);
    }

    /**
     * 获取用户所有未读消息
     *
     * @return
     */
    public Page<BondSimpleNotiMsgVO> getAllFavoriteUnreadMsg(Integer userId, Integer page, Integer limit) {
        Query query = new Query(Criteria.where("userId").is(userId));
        List<BondMsgUserStatusDoc> readMsgList = mongoOperations.find(query, BondMsgUserStatusDoc.class);
        List<Long> readMsgIdList = readMsgList.stream().map(BondMsgUserStatusDoc::getNotifyMsgId).collect(Collectors.toList());
        // 获取用户的债券列表
        List<BondSimpleNotiMsgVO> favList = bondCombineDao.getAllFavoriteUnreadMsg(userId, readMsgIdList, page, limit);
        Long totalCount = getNewMessageCount(userId);
        return new PageImpl<>(favList, new PageRequest(page, limit), totalCount);
    }

    public String getFavoriteMsgNewsUrl(Long newsIndex) {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(newsIndex));
        String resultJson = HttpClientUtil.doPost(SENTIMENT_URL, params);
        return resultJson;
    }

    /**
     * 修改用户所有债券消息为已读
     *
     * @return
     */
    public Long updateAllFavoriteMsgReadStatus(Integer userId, Long radarType) {
        Date currDate = SafeUtils.getCurrentTime();
        // 获取用户所有债券列表
        List<BondFavorite> favList = radarType == 0 ? favoriteRepo.findByUserId(userId) :
                bondCombineDao.findByUserIdAndRadar(userId, this.convertToSubRadarList(Arrays.asList(radarType)));
        if (favList.isEmpty()) {
            LOG.error(String.format("updateAllFavoriteMsgReadStatus: cannot find favList with userId[%1$d]", userId));
            return null;
        }
        List<Long> bondIdList = favList.stream().map(BondFavorite::getBondUniCode).collect(Collectors.toList());
        Query msgQuery = new Query();
        if (radarType > 0) { // 根据雷达区分
            List<Long> radarList = this.convertToSubRadarList(Arrays.asList(radarType));//获取雷达子类型
            msgQuery.addCriteria(Criteria.where("bondId").in(bondIdList).and("eventType").in(radarList));
        } else {
            msgQuery.addCriteria(Criteria.where("bondId").in(bondIdList));
        }

        // 按照id倒序，取最大id
        msgQuery.with(new Sort(Sort.Direction.DESC, "_id"));
        BondNotificationMsgDoc latestMsg = mongoOperations.findOne(msgQuery.limit(1), BondNotificationMsgDoc.class);
        if (latestMsg == null) {
            LOG.error(String.format("updateAllFavoriteMsgReadStatus: cannot find latestMsg"));
            return null;
        }
        Long latestBookmark = latestMsg.getId();
        favList.stream().forEach(item -> {
            item.setBookmark(latestBookmark);
            item.setBookmarkUpdateTime(currDate);
        });
        favoriteRepo.save(favList);
        return latestBookmark;
    }


    /**
     * 获取关注债券的消息列表
     *
     * @return
     */
    public Page<BondSimpleNotiMsgVO> getFavoriteSimpleMsgWithRadarTypes(Integer userId, Integer favoriteId,
                                                                        Long radarType, int page, Integer limit) {
        // 获取有效的投组信息
        BondFavorite favorite = favoriteRepo.findOneByFavoriteIdAndIsDelete(favoriteId, 0);
        if (favorite == null) {
            LOG.error(String.format("getFavoriteSimpleMsgWithRadarTypes: cannot find favorite[%1$d]", favoriteId));
            //throw new BusinessException("找不到有效关注债券");
            return null;
        }
        List<Long> radarTypes = radarType == 0 ? bondCombineDao.getAllRadarByParentId(0) : Arrays.asList(radarType);
        List<Long> subRadarTypeList = this.convertToSubRadarList(radarTypes);
        List<Long> bondIdList = Arrays.asList(favorite.getBondUniCode());
        Long totalCount = msgDAO.getCountOfPagedGroupMsgVOList(userId, favorite.getGroupId(), bondIdList, subRadarTypeList);
        List<BondSimpleNotiMsgVO> result = this.getPagedMsgVOListByGroupIdAndBondIdList(userId, favorite.getGroupId(),
                Arrays.asList(favorite), subRadarTypeList, bondIdList, page, limit);
        return new PageImpl<>(result, new PageRequest(page, limit), totalCount);
    }

    private List<BondSimpleNotiMsgVO> getPagedMsgVOListByGroupIdAndBondIdList(Integer userId, Integer groupId,
                                                                              List<BondFavorite> favList,
                                                                              List<Long> subRadarTypeList,
                                                                              List<Long> bondIdList,
                                                                              Integer page, Integer limit) {
        List<BondSimpleNotiMsgVO> result = new ArrayList<>();
        if (!favList.isEmpty()) {
            // 1.先取出所有的bondId
            List<BondDetailDoc> bondDetailDocList = this.getBondDetailByBondUniCodeList(bondIdList);
            // 缓存map
            Map<Long, String> bondIdNameMap = bondDetailDocList.stream().collect(Collectors.toMap(BondDetailDoc::getBondUniCode, BondDetailDoc::getName));
            // 2.根据userId和bondIdList，获取所有缓存的已读消息的id
            Query query = new Query(Criteria.where("userId").is(userId).and("bondId").in(bondIdList));
            List<BondMsgUserStatusDoc> readMsgList = mongoOperations.find(query, BondMsgUserStatusDoc.class);
            List<Long> readMsgListIdList = readMsgList.stream().map(BondMsgUserStatusDoc::getNotifyMsgId).collect(Collectors.toList());
            // 3.根据userId, groupId, bondIdList以及readMsgListIdList，直接分页获取消息记录，按照未读->已读，再按照时间来排序
            result = bondCombineDao.getPagedGroupMsgVOList(userId, groupId, bondIdList, readMsgListIdList, subRadarTypeList,
                    page, limit);
            result.stream().forEach(vo -> {
                if (bondIdNameMap.containsKey(vo.getBondId())) {
                    vo.setBondName(bondIdNameMap.get(vo.getBondId()));
                }
            });
        }
        return result;
    }

    public Long createDefaultGroup(Integer userId, String groupName) {

//            BondFavoriteRadarMapping radarMapping = new BondFavoriteRadarMapping();
//            BeanUtils.copyProperties(item, radarMapping);
//            radarMapping.setId(null);
        List<BondFavoriteRadarMapping> commonRadarList = new ArrayList<>();
        favRadarMappingRepo.findAllByGroupId(0L).stream().forEach(item -> {
            item.setId(null);
            commonRadarList.add(item);
        });

        // 等同
//        List<BondFavoriteRadarMapping> test = favRadarMappingRepo.findAllByGroupId(0L)
//                .stream()
//                .map(item->{item.setId(null);return item;})
//                .collect(Collectors.toList());

//            BondFavoritePriceIndex priceIndex = new BondFavoritePriceIndex();
//            BeanUtils.copyProperties(item, priceIndex);
//            priceIndex.setId(null);
        List<BondFavoritePriceIndex> priceIndexList = new ArrayList<>();
        favPriceIndexRepo.findAllByGroupIdAndFavoriteId(0L, 0L).stream().forEach(item -> {
            item.setId(null);
            priceIndexList.add(item);
        });

//            BondFavoriteFinaIndex finaIndex = new BondFavoriteFinaIndex();
//            BeanUtils.copyProperties(item, finaIndex);
//            finaIndex.setId(null);
        List<BondFavoriteFinaIndex> finaIndexList = new ArrayList<>();
        favFinaIndexRepo.findAllByGroupIdAndFavoriteId(0L, 0L).stream().forEach(item -> {
            item.setId(null);
            finaIndexList.add(item);
        });

        FavoriteGroupReq req = new FavoriteGroupReq();
        req.setEmailEnable(0);
        req.setNotifiedEnable(1);
        req.setGroupName(groupName);
        req.setCommonRadars(commonRadarList);
        req.setPriceRadars(priceIndexList);
        req.setFinaRadars(finaIndexList);

        return this.createGroup(userId, req);
    }

    /**
     * 获取用户的未读消息数
     *
     * @return
     */
    public Long getNewMessageCount(Integer userId) {
        List<Long> newMsgIdList = bondCombineDao.getSketchyNewMsgIdListByUserId(userId);
        return this.getEventMsgCountByNewMsgIdList(newMsgIdList);
    }

    /**
     * 添加关注的债券列表到投组列表
     *
     * @param userId
     * @param req
     * @return
     */
    public String addBondListToGroup(Integer userId, FavoriteBondBatchReq req) {
        // 参数合法性验证
        LOG.info("addBondListToGroupList start");
        if (req == null || req.getBondIds() == null || req.getBondIds().isEmpty())
            return null;
        List<Long> bondIdList = req.getBondIds();
        Integer groupId = req.getGroupId();
        BondFavoriteGroup favGroup = favoriteGroupRepo.findOneByGroupIdAndIsDelete(groupId, 0);
        Date currDate = SafeUtils.getCurrentTime();
        if (favGroup != null) {
            Long groupIdL = SafeUtils.getLong(groupId);
            Integer notifiedEnable = favGroup.getNotifiedEnable();
            List<BondFavorite> favList = favoriteRepo.findByGroupId(SafeUtils.getInteger(groupId));
            if (favList == null || favList.isEmpty()) {
                LOG.warn(String.format("addBondListToGroup: cannot find any BondFavorite by groupId[%1$d]", groupId));
            }
            // 去掉已经存在的债券
            List<Long> newDiffBondCodeList = new ArrayList<>();
            bondIdList.stream().forEach(id -> {
                if (favList.stream().noneMatch(fav -> fav.getBondUniCode().equals(id))) {
                    newDiffBondCodeList.add(id);
                }
            });
            // bondCode->issuerId缓存
            Query query = new Query(Criteria.where("bondUniCode").in(newDiffBondCodeList));
            query.fields().include("bondUniCode").include("issuerId");
            List<BondBasicInfoDoc> basicInfoDocList = mongoOperations.find(query, BondBasicInfoDoc.class);
            Map<Long, Long> bondCode2IssuerIdMap = basicInfoDocList.stream()
                    .collect(Collectors.toMap(BondBasicInfoDoc::getBondUniCode, BondBasicInfoDoc::getIssuerId));
            // issuerId->modelName缓存
            List<Long> issuerIdList = basicInfoDocList.stream().map(BondBasicInfoDoc::getIssuerId).collect(Collectors.toList());
            List<Map<String, Object>> issuerIdModelNameMapList = indicatorDAO.getModelNameListByIssuerIdList(issuerIdList);
            // issuerId->isNotFinaComp缓存
            Map<Long, Boolean> bondCode2IsNotFinaCompBondMap = issuerIdModelNameMapList.stream()
                    .collect(Collectors.toMap(item -> SafeUtils.getLong(item.get("issuerId")),
                            item -> !Constants.FINA_COMP_BOND_LIST.contains(item.get("modelName"))));
            if (!newDiffBondCodeList.isEmpty()) {
                // 事务批量执行
                int totalSize = newDiffBondCodeList.size(); //总记录数
                int batchSize = Constants.BATCH_FAVORITE_LIMIT; //每页N条
                int totalBatch = totalSize / batchSize; //共N页
                if (totalSize % batchSize != 0) {
                    totalBatch += 1;
                    if (totalSize < batchSize) {
                        batchSize = newDiffBondCodeList.size();
                    }
                }
                for (int batchIdx = 1; batchIdx < totalBatch + 1; batchIdx++) {
                    int startIdx = (batchIdx - 1) * batchSize;
                    int endIdx = batchIdx * batchSize > totalSize ? totalSize : batchIdx * batchSize;
                    List<Long> pagedNewDiffBondCodeList = newDiffBondCodeList.subList(startIdx, endIdx);
                    this.saveRadarsToDBWithCache(userId, groupIdL, notifiedEnable, pagedNewDiffBondCodeList, bondCode2IssuerIdMap, bondCode2IsNotFinaCompBondMap, currDate);
                }
            }
        }
        return "success";
    }

    @Transactional
    protected void saveRadarsToDBWithCache(Integer userId, Long groupIdL, Integer notifiedEnable, List<Long> newDiffBondCodeList,
                                           Map<Long, Long> bondCode2IssuerIdMap, Map<Long, Boolean> bondCode2IsNotFinaCompBondMap, Date currDate) {
        // 生成favoriteList
        List<BondFavorite> newDiffFavList = this.extractFavoriteList(groupIdL, userId, newDiffBondCodeList, currDate);
        List<BondFavorite> savedFavList = favoriteRepo.save(newDiffFavList);
        Map<Long, Long> favId2BondCodeMap = savedFavList.stream()
                .collect(Collectors.toMap(item -> SafeUtils.getLong(item.getFavoriteId()), BondFavorite::getBondUniCode));
        // 新增普通雷达的缓存（不需要入库）
        this.addNewCommonRadarDoc(userId, groupIdL, savedFavList, notifiedEnable, currDate);
        // 新增关注债券的价格条件, 入库+缓存
        this.addNewFavPriceRadars(userId, groupIdL, savedFavList, favId2BondCodeMap, notifiedEnable, currDate);
        // 新增关注债券的财务指标, 添加缓存
        this.addNewFavFinaRadars(userId, groupIdL, savedFavList, favId2BondCodeMap, bondCode2IssuerIdMap, bondCode2IsNotFinaCompBondMap, notifiedEnable, currDate);
    }

    /**
     * 获取投组名称列表
     *
     * @param userId
     * @return
     */
    public List<BondFavoriteGroupVO> getFavoriteGroupNameListByUserId(Integer userId) {
        return favoriteDAO.getShortFavoriteGroupVOListByUserId(userId, BondFavoriteGroupVO.class);
    }
}
