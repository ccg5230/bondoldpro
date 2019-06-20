package com.innodealing.bond.service;

import static java.lang.Math.toIntExact;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.innodealing.adapter.BondInstitutionInduAdapter;
import com.innodealing.adapter.BondPageAdapter;
import com.innodealing.bond.param.BondDmFilterReq;
import com.innodealing.bond.param.BondSimilarFilterReq;
import com.innodealing.bond.vo.favorite.BondDetailVo;
import com.innodealing.bond.vo.summary.BondDetailSummary;
import com.innodealing.cache.AreaXRefCache;
import com.innodealing.cache.FieldGroupMappingCache;
import com.innodealing.consts.Constants;
import com.innodealing.engine.jdbc.bondccxe.BondCashFlowChartDao;
import com.innodealing.engine.jpa.dm.BondFavoriteGroupRepository;
import com.innodealing.engine.mongo.bond.BondBasicInfoRepository;
import com.innodealing.engine.mongo.bond.BondDetailRepository;
import com.innodealing.engine.redis.RedisMsgService;
import com.innodealing.exception.BusinessException;
import com.innodealing.json.portfolio.BondIdxPriceJson;
import com.innodealing.model.dm.bond.BondCredChan;
import com.innodealing.model.dm.bond.BondFavorite;
import com.innodealing.model.dm.bond.BondFavoriteGroup;
import com.innodealing.model.dm.bond.BondIndicatorFilterReq;
import com.innodealing.model.mongo.dm.BondBasicInfoDoc;
import com.innodealing.model.mongo.dm.BondBestQuoteNetpriceDoc;
import com.innodealing.model.mongo.dm.BondBestQuoteYieldDoc;
import com.innodealing.model.mongo.dm.BondComInfoDoc;
import com.innodealing.model.mongo.dm.BondComparisonDoc;
import com.innodealing.model.mongo.dm.BondDetailDoc;
import com.innodealing.model.mongo.dm.BondFieldGroupMappingDoc;
import com.innodealing.model.mongo.dm.BondImpliedRatingCpInfo;
import com.innodealing.model.mongo.dm.BondImpliedRatingInfo;
import com.innodealing.model.mongo.dm.BondIssuerDoc;
import com.innodealing.model.mongo.dm.BondQuoteDoc;
import com.innodealing.model.mongo.dm.BondRiskColumnDoc;
import com.innodealing.model.mongo.dm.BondRiskDoc;
import com.innodealing.rabbitmq.MqSenderService;
import com.innodealing.uilogic.UIAdapter;
import com.innodealing.util.BeanUtil;
import com.innodealing.util.BondDealUtil;
import com.innodealing.util.ExportExcel;
import com.innodealing.util.MD5Util;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * @author Administrator
 *
 */
@Service
public class BondDetailService {

    private static final Logger LOG = LoggerFactory.getLogger(BondDetailService.class);

    @Autowired
    private BondDetailRepository bondDetailRepository;

    @Autowired
    private BondBasicInfoRepository bondBasicInfoRepository;

    @Resource(name="bondMongo")
    protected MongoTemplate bondMongoTemplate;

    private @Autowired BondFavoriteService bondFavService;

    private @Autowired BondComparisonService comparisonService;

    private @Autowired BondFavoriteGroupRepository favoriteGroupRepository;

    protected @Autowired RedisMsgService redisUtil;

    private @Autowired BondUserOperationService bondUserOperationService;

	private @Autowired MqSenderService senderService;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Gson gson;

    @Autowired
    private AreaXRefCache areaXRefCache;

    @Autowired
    private BondInduService induService;

    @Autowired
    private BondInstitutionInduAdapter induAdapter;

    @Autowired
    private FieldGroupMappingCache fieldGroupMappingCache;
    
    @Autowired
    private BondInstRatingService bondInstRatingService;
    
	@Autowired
	private BondInstitutionInduAdapter bondInstitutionInduAdapter;
	
	@Autowired
	private BondCashFlowChartDao cashFlowChartDAO;
	
    final Criteria CRITERIA_MATCH_ALL = new Criteria();

    BondDetailService() {
    }

    public List<BondDetailDoc> findBondDetailByBondUniCodes(List<Long> bondUniCodes, Integer page, Integer limit, String sort) {
        String[] sortPars = sort.split(":");
        String sortField = sortPars[0];
        Direction sortDir = sortPars[1].toLowerCase().startsWith("des") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest request = new PageRequest(page, limit, new Sort(sortDir, sortField));

        Query query = new Query();
        query.addCriteria(Criteria.where("bondUniCode").in(bondUniCodes)).with(request);
        query.fields().exclude("pdUiOpt").exclude("issRatingUiOpt").exclude("bondRatingUiOpt").exclude("tenorUiOpt").exclude("listStaPar");
        List<BondDetailDoc> result = bondMongoTemplate.find(query, BondDetailDoc.class);
        return result;
    }

    public BondPageAdapter<BondDetailDoc> findBondDetailByFilter(BondDmFilterReq filter, Integer page, Integer limit, String sort) {

        String[] sortPars = sort.split(":");
        String sortField = sortPars[0];
        Direction sortDir = sortPars[1].toLowerCase().startsWith("des") ? Sort.Direction.DESC : Sort.Direction.ASC;

        Sort sorter = null;
        if (sortField.equalsIgnoreCase("tenor")) {
            sorter = new Sort(sortDir, "tenorDays");
        } else if (sortField.equalsIgnoreCase("updateTime")) {
            sorter = new Sort(new Order(sortDir, sortField), new Order(Sort.Direction.ASC, "name"));
        } else if (sortField.equalsIgnoreCase("pd")) {
            sorter = new Sort(new Order(sortDir, "pdSortRRs"));
        } else if (sortField.equalsIgnoreCase("worstPd")) {
            sorter = new Sort(new Order(sortDir, "worstPdNum"));
        } else {
            sorter = new Sort(sortDir, sortField);
        }

        PageRequest request = new PageRequest(page, limit, sorter);

        Query query = new Query();
        UICriteriaBuilder cbuild = new UICriteriaBuilder(filter);
        query.addCriteria(cbuild.generate()).with(request);
        query.fields().exclude("pdUiOpt").exclude("issRatingUiOpt").exclude("bondRatingUiOpt").exclude("tenorUiOpt").exclude("currStatus").exclude("issStaPar")
                .exclude("listStaPar").exclude("quarters");

        // 保存债券号和关注号的关系
        if (filter.getUserId() == null) {
            throw new BusinessException("用户 id不能为空");
        }

        return queryBondDetail(request, query, filter.getUserId());
    }

    private BondPageAdapter<BondDetailDoc> queryBondDetail(PageRequest request, Query query, Long userId) {
        // 保存债券号和关注号的关系
        if (userId == null) {
            throw new BusinessException("用户 id不能为空");
        }

        HashMap<Long, BondFavorite> bondUniCode2FavMap = new HashMap<Long, BondFavorite>();
        List<BondFavorite> favList = bondFavService.findFavoriteByUserId(toIntExact(userId));
        if (favList != null) {
            favList.forEach(f -> {
                bondUniCode2FavMap.put(f.getBondUniCode(), f);
            });
        }

        // 对比列表
        Set<Long> comparisonSet = new HashSet<Long>();
        List<BondComparisonDoc> comps = comparisonService.findComparisonByUserId(userId);
        if (comps != null) {
            comps.forEach(compDoc -> {
                comparisonSet.add(compDoc.getBondId());
            });
        }

        List<BondDetailDoc> bondDetailDocs = bondMongoTemplate.find(query, BondDetailDoc.class);
        bondDetailDocs.forEach(bond -> {
            BondFavorite fav = bondUniCode2FavMap.get(bond.getBondUniCode());
            if (fav != null) {
                bond.setIsFavorited(true);
                bond.setFavoriteId(fav.getFavoriteId());
            } else {
                bond.setIsFavorited(false);
            }
            bond.setIsCompared(comparisonSet.contains(bond.getBondUniCode()));
        });

        return new BondPageAdapter<BondDetailDoc>(bondDetailDocs, request, bondMongoTemplate.count(query, BondDetailDoc.class));
    }

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

    private class UICriteriaBuilder {
        private BondDmFilterReq filter;

        public Criteria generate() {
            Criteria criteria = new Criteria();
            criteria.andOperator(genExcludeInvalidBondCriteria(), genDmBondTypeCriteria(),theoEndDateCriteria(),
                    new CriteriaFactory<Integer>().generate(induService.getInduIdByClass(filter.getInduClass(),filter.getUserId()), filter.getInduIds()),
                    new CriteriaFactory<Integer>().generate("comUniCode", filter.getIssuer()),
                    new CriteriaFactory<String>().generate("areaCode2", filter.getAreaCodes()),
                    new CriteriaFactory<Integer>().generate("ownerType", filter.getOwnerTypes()),
                    new CriteriaFactory<Integer>().generate("bondRatingUiOpt", filter.getBondRatings()),
                    new CriteriaFactory<Integer>().generate("impliedRatingUiOpt", filter.getImpliedRatings()),
                    new CriteriaFactory<Integer>().generate("issRatingUiOpt", filter.getIssRatings()), genMarketCriteria(),
                    new CriteriaFactory<Boolean>().generate("munInvest", filter.getMunInvest()),
                    new CriteriaFactory<Integer>().generate("tenorUiOpt", UIAdapter.cvtTenorStrictOptFromOpt(filter.getTenors())),
                    new CriteriaFactory<Integer>().generate("pdNum", filter.getPds()), genQuoteExistCriteria(),
                    new CriteriaFactory<Boolean>().generate("listPar", filter.getListPar()),instRating());
            // LOG.info("criteria:" + criteria.);
            return criteria;
        }

        private Criteria genQuoteExistCriteria() {
            Boolean isValidQuote = filter.getIsValidQuoteExist();
            if (isValidQuote != null && isValidQuote) {
                return Criteria.where("updateTime").ne(null);
            }
            return new Criteria();
        }

        private Criteria genExcludeInvalidBondCriteria() {
            // Criteria cri = Criteria.where("quarter").is(filter.getQuarter());
            // Criteria.where("quarters").elemMatch(cri)
            return new Criteria().andOperator(Criteria.where("currStatus").is(1), Criteria.where("issStaPar").is(1));
        }

        private Criteria genDmBondTypeCriteria() {
            // 利率债信用债是大分类，实际给债券标记分类时候用的是二级分类，不能匹配界面上选择的上一级分类值
            // 界面有自动联动关联二级分类的功能，所以一级分类信息已经被二级分类覆盖
            List<Integer> list = filter.getDmBondTypes();
            // list.remove(1); //利率债
            // list.remove(2); //信用债
            return new CriteriaFactory<Integer>().generate("dmBondType", list);
        }

        private Criteria genMarketCriteria() {
            List<Integer> list = filter.getMarkets();
            if (list == null)
                return CRITERIA_MATCH_ALL;

            List<Criteria> subCriterias = new ArrayList<Criteria>();
            if (list.contains(1)) // 银行间
                subCriterias.add(Criteria.where("market").is(UIAdapter.MarketOpt.E_IB.code()));
            if (list.contains(2)) // 交易所
                subCriterias.add(Criteria.where("market").is(UIAdapter.MarketOpt.E_EXCHG.code()));
            if (list.contains(3))
                subCriterias.add(Criteria.where("isCrossMar").is(1));
            if (list.contains(4))
                subCriterias.add(Criteria.where("pledgeCode").exists(true));

            return new Criteria().orOperator(subCriterias.toArray(new Criteria[subCriterias.size()]));
        }

        public UICriteriaBuilder(BondDmFilterReq filter) {
            this.filter = filter;
        }
        
        
        private Criteria theoEndDateCriteria() {
        	
        	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        	Date stime = null;
        	Date etime = null;
        	if(!StringUtils.isEmpty(filter.getStartTime())){
        		if (!StringUtils.isEmpty(filter.getStartTime())) {
        			try {
        				stime = sf.parse(filter.getStartTime());
        			} catch (ParseException e) {
        				stime = null;
        				LOG.error("字符串转日期失败！", e);
        			}
        		}
        	}
        	
        	if(!StringUtils.isEmpty(filter.getEndTime())){
        		if (!StringUtils.isEmpty(filter.getEndTime())) {
        			try {
        				etime = sf.parse(filter.getEndTime());
        			} catch (ParseException e) {
        				etime = null;
        				LOG.error("字符串转日期失败！", e);
        			}
        		}
        	}
        	
            return new Criteria().andOperator(StringUtils.isEmpty(filter.getStartTime()) ? new Criteria() : Criteria.where("theoEndDate").gte(stime),
    				StringUtils.isEmpty(filter.getEndTime()) ? new Criteria() : Criteria.where("theoEndDate").lte(etime));
        }
        
        private Criteria instRating() {
    		if(filter.getInstRatings()!=null || filter.getInstInvestmentAdvices()!=null){
            	 return new Criteria().andOperator(new CriteriaFactory<Integer>().generate(bondInstRatingService.getRatingIdByUserId(filter.getUserId()), filter.getInstRatings()),
            			 new CriteriaFactory<Integer>().generate(bondInstRatingService.getInstInvestmentAdviceIdByUserId(filter.getUserId()), filter.getInstInvestmentAdvices()));
            }
            return new Criteria();
        }
    }

    public BondPageAdapter<BondDetailDoc> findBondByCompany(String comUniCode, Integer type, Integer page, Integer limit) {
        PageRequest request = new PageRequest(page, limit, new Sort(Direction.DESC, "updateTime"));

        Query query = new Query();
        Criteria criteria = new Criteria();

        /** 当天有效报价 */
        if (type == 1) {
            Calendar c1 = Calendar.getInstance();
            c1.set(Calendar.HOUR_OF_DAY, 0);
            c1.set(Calendar.SECOND, 0);
            c1.set(Calendar.MINUTE, 0);
            Date stime = new Date(c1.getTimeInMillis());

            Calendar c2 = Calendar.getInstance();
            c2.set(Calendar.HOUR_OF_DAY, 24);
            c2.set(Calendar.SECOND, 0);
            c2.set(Calendar.MINUTE, 0);
            Date etime = new Date(c2.getTimeInMillis());

            criteria.andOperator(Criteria.where("updateTime").gte(stime).lte(etime), Criteria.where("comUniCode").is(Long.parseLong(comUniCode)),
                    Criteria.where("ofrPrice").exists(true), Criteria.where("bidPrice").exists(true));
        }
        /** 所有债券 */
        else if (type == 2) {
            criteria.andOperator(Criteria.where("comUniCode").is(Long.parseLong(comUniCode)));
        }

        query.addCriteria(criteria).with(request);

        return new BondPageAdapter(bondMongoTemplate.find(query, BondDetailDoc.class), request, bondMongoTemplate.count(query, BondDetailDoc.class));
    }

    public BondPageAdapter<BondDetailDoc> findBondDetailBySimilar(BondSimilarFilterReq req, int page, Integer limit, String sort) {
        BondDetailDoc detailDoc = null;
        {
            Query query = new Query();
            query.addCriteria(Criteria.where("bondUniCode").is(req.getBondId()));
            detailDoc = bondMongoTemplate.findOne(query, BondDetailDoc.class);
            if (detailDoc == null) {
                throw new BusinessException("该债券不存在:" + req.getBondId());
            }
        }

        String[] sortPars = sort.split(":");
        String sortField = sortPars[0];
        Direction sortDir = sortPars[1].toLowerCase().startsWith("des") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest request = new PageRequest(page, limit, new Sort(sortDir, sortField));

        Query query = new Query();
        Criteria criteria = new Criteria();
        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("bondUniCode").ne(req.getBondId()));

        for (String similarField : req.getSimilarField()) {
            switch (similarField) {
                case "dmBondType":
                    criterias.add(Criteria.where("dmBondType").is(detailDoc.getDmBondType()));
                    break;
                case "bondRating":
                    criterias.add(Criteria.where("bondRating").is(detailDoc.getBondRating()));
                    break;
                case "issRating":
                    criterias.add(Criteria.where("issRating").is(detailDoc.getIssRating()));
                    break;
                case Constants.INDU_CLASS_GICS_ID:
                    criterias.add(Criteria.where(similarField).is(detailDoc.getInduId()));
                    break;
                case Constants.INDU_CLASS_SW_ID:
                    criterias.add(Criteria.where(similarField).is(detailDoc.getInduIdSw()));
                    break;
                case "tenor":
                    Long tenorDays = detailDoc.getTenorDays();
                    long tenorHigh = (tenorDays + Constants.BOND_TENOR_OFFSET) <= 0L ? 0L : (tenorDays + Constants.BOND_TENOR_OFFSET);
                    long tenorlow = (tenorDays - Constants.BOND_TENOR_OFFSET) <= 0L ? 0L : (tenorDays - Constants.BOND_TENOR_OFFSET);
                    criterias.add(Criteria.where("tenorDays").gte(tenorlow).lte(tenorHigh));
                    break;
                default:
                    break;
            }
        }

        criteria.andOperator(criterias.toArray(new Criteria[criterias.size()]));
        query.addCriteria(criteria).with(request);
        return queryBondDetail(request, query, req.getUserId());
    }

    public BondCredChan ratePoint(String issuerId, String rateWriteDate, Integer type) {

        StringBuffer sb = new StringBuffer("");
        sb.append("SELECT t.*,q.ORG_CHI_NAME as orgChiName FROM ( ");

        /** 观点 */
        if (type == 1) {
            sb.append("SELECT dbich.RATE_POINT as ratePoint, ");
        }
        /** 负面点 */
        else if (type == 2) {
            sb.append("SELECT dbich.CCE_DISADVT as cceDisadvt, ");
        }
        /** 正面点 */
        else if (type == 3) {
            sb.append("SELECT dbich.CCE_ADVT as cceAdvt, ");
        }
        /** 关注点 */
        else if (type==4){
        	  sb.append("SELECT dbich.ATT_POINT as attPoint, ");
        }

        sb.append(
                "dbich.RATE_WRIT_DATE as rateWritDate,dbci.COM_CHI_NAME as comChiName,dbich.ORG_UNI_CODE as orgUniCode,dbich.CREATETIME FROM bond_ccxe.d_bond_iss_cred_chan dbich ");
        sb.append("INNER JOIN bond_ccxe.d_pub_com_info_2 dbci ON dbich.COM_UNI_CODE = dbci.COM_UNI_CODE ");
        sb.append("WHERE dbich.ISVALID = 1 and dbich.com_type_par=1  AND dbich.COM_UNI_CODE = ? AND  dbich.RATE_WRIT_DATE = ?) t  ");
        sb.append("INNER JOIN (");
        sb.append("SELECT g.ORG_UNI_CODE,g.ORG_CHI_NAME FROM  bond_ccxe.d_pub_org_info_g g ");
        sb.append("UNION ");
        sb.append("SELECT r.ORG_UNI_CODE,r.ORG_CHI_NAME FROM  bond_ccxe.d_pub_org_info_r r ) q ");
        sb.append("ON q.ORG_UNI_CODE = t.orgUniCode ORDER BY CREATETIME DESC LIMIT 1");

        Map<String, Object> map;
        try {
            map = jdbcTemplate.queryForMap(sb.toString(), issuerId, rateWriteDate + " 00:00:00");
            BondCredChan vo = BeanUtil.map2Bean(map, BondCredChan.class);
            return vo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    public static void main(String[] args) {
		int  type = 4;
		   StringBuffer sb = new StringBuffer("");
	        sb.append("SELECT t.*,q.ORG_CHI_NAME as orgChiName FROM ( ");

	        /** 观点 */
	        if (type == 1) {
	            sb.append("SELECT dbich.RATE_POINT as ratePoint, ");
	        }
	        /** 负面点 */
	        else if (type == 2) {
	            sb.append("SELECT dbich.CCE_DISADVT as cceDisadvt, ");
	        }
	        /** 正面点 */
	        else if (type == 3) {
	            sb.append("SELECT dbich.CCE_ADVT as cceAdvt, ");
	        }
	        /** 关注点 */
	        else if (type==4){
	        	  sb.append("SELECT dbich.ATT_POINT as attPoint, ");
	        }

	        sb.append(
	                "dbich.RATE_WRIT_DATE as rateWritDate,dbci.COM_CHI_NAME as comChiName,dbich.ORG_UNI_CODE as orgUniCode,dbich.CREATETIME FROM bond_ccxe.d_bond_iss_cred_chan dbich ");
	        sb.append("INNER JOIN bond_ccxe.d_pub_com_info_2 dbci ON dbich.COM_UNI_CODE = dbci.COM_UNI_CODE ");
	        sb.append("WHERE dbich.ISVALID = 1 AND dbich.com_type_par=1  AND dbich.COM_UNI_CODE = ? AND  dbich.RATE_WRIT_DATE = ?) t  ");
	        sb.append("INNER JOIN (");
	        sb.append("SELECT g.ORG_UNI_CODE,g.ORG_CHI_NAME FROM  bond_ccxe.d_pub_org_info_g g ");
	        sb.append("UNION ");
	        sb.append("SELECT r.ORG_UNI_CODE,r.ORG_CHI_NAME FROM  bond_ccxe.d_pub_org_info_r r ) q ");
	        sb.append("ON q.ORG_UNI_CODE = t.orgUniCode ORDER BY CREATETIME DESC LIMIT 1");

        System.out.println(sb);

    	
	}

    @EventListener
    @Async
    public void handleQuoteSaved(BondQuoteDoc quote) {
        LOG.info("handleQuoteSaved begin,BondQuoteDoc json:" + gson.toJson(quote));
        if (quote.getBondUniCode() == null) {
            LOG.error("failed to handle quote data, bond_uni_code is null");
            return;
        }

        BondDetailDoc detailDoc = bondDetailRepository.findByBondUniCode(quote.getBondUniCode());
        if (detailDoc == null)
            return;

        switch (quote.getSide()) {
            case 1:
                detailDoc.setBidPrice(quote.getBidPrice()!=null?quote.getBidPrice().doubleValue():null);
                detailDoc.setBidVol(quote.getBidVol());
                Long bidOrderCnt = detailDoc.getBidOrderCnt();
                detailDoc.setBidOrderCnt((bidOrderCnt == null) ? 1 : ++bidOrderCnt);
                detailDoc.setUpdateTime(new Date());
                detailDoc.setBidUpdateTime(new Date());
                break;
            case 2:
                detailDoc.setOfrPrice(quote.getOfrPrice()!=null?quote.getOfrPrice().doubleValue():null);
                detailDoc.setOfrVol(quote.getOfrVol());
                Long ofrOrderCnt = detailDoc.getOfrOrderCnt();
                detailDoc.setOfrOrderCnt((ofrOrderCnt == null) ? 1 : ++ofrOrderCnt);
                detailDoc.setUpdateTime(new Date());
                detailDoc.setOfrUpdateTime(new Date());
                break;
            default:
                return;
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("bondUniCode").is(detailDoc.getBondUniCode()));
        switch (quote.getPriceUnit()) {
            case 1:
                BondBestQuoteYieldDoc yldbestQuote = bondMongoTemplate.findOne(query, BondBestQuoteYieldDoc.class);
                if (null != yldbestQuote) {
                    detailDoc.setBestBidPrice(yldbestQuote.getBidPrice());
                    detailDoc.setBestOfrPrice(yldbestQuote.getOfrPrice());
                }
                break;
            case 2:
                BondBestQuoteNetpriceDoc netbestQuote = bondMongoTemplate.findOne(query, BondBestQuoteNetpriceDoc.class);
                if (null != netbestQuote) {
                    detailDoc.setBestBidPrice(netbestQuote.getBidPrice());
                    detailDoc.setBestOfrPrice(netbestQuote.getOfrPrice());
                }
                break;
            default:
                return;
        }

        bondDetailRepository.save(detailDoc);

        // 报价监测,针对信用债
        if (BondDealUtil.isCreditDebt(detailDoc.getDmBondType())) {
            publishBondQuoteNotiMsg(quote);
        }

        LOG.info("handleQuoteSaved end,BondQuoteDoc json:" + gson.toJson(quote));
    }

    public BondPageAdapter<BondComInfoDoc> findBondComInfoList(String condition, Integer page, Integer limit) {
        // 得到发行人集合
        Set<Long> list = getComInfoRiskDocByCondition(condition);
       
        PageRequest request = new PageRequest(page - 1, limit, new Sort(Direction.DESC, "pdTime"));
        Query query = queryConditionInUniCodes(list, request, null, null);

        return new BondPageAdapter<BondComInfoDoc>(bondMongoTemplate.find(query, BondComInfoDoc.class), request,
                bondMongoTemplate.count(queryConditionInUniCodes(list, null, null, null), BondComInfoDoc.class));
    }

	private Query queryConditionInUniCodes(Set<Long> list, PageRequest request, String induField, Long[] induIds) {
		Query query = new Query();
        Criteria criteria = new Criteria();
        if (!StringUtils.isBlank(induField)) {
        	query.addCriteria(Criteria.where(induField).in(induIds));
        }
        criteria.andOperator(Criteria.where("_id").in(list));
        query.addCriteria(criteria);
        if (null != request) {
        	query.with(request);
		}
        return query;
	}

	private Set<Long> getComInfoRiskDocByCondition(String condition) {
        Set<Long> list = new HashSet<Long>();
		BondRiskDoc doc = bondMongoTemplate.findById(condition.split("\\*")[0], BondRiskDoc.class);
        if (null != doc) {
            BondRiskColumnDoc columnDoc = null;
            Method method;
            try {
                method = doc.getClass().getDeclaredMethod(condition.split("\\*")[1]);
                if (method != null) {
                    columnDoc = (BondRiskColumnDoc) method.invoke(doc);
                }
                if (columnDoc != null) {
                    list = columnDoc.getComUniCodeList();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
		return list;
	}

    public Map<Long, BondDetailVo> findBondDetailVos(List<BondFavorite> favList, Integer userId, Integer groupId, Integer page, Integer limit, String sort) {
        BondFavoriteGroup bfGroup = favoriteGroupRepository.findOne(groupId);

        HashMap<String, List<Integer>> eventTypesMap = new HashMap<String, List<Integer>>();
        List<Integer> eventTypes = null;
        if (StringUtils.isNotBlank(bfGroup.getNotifiedEventtype())) {
            eventTypes = gson.fromJson(bfGroup.getNotifiedEventtype(), new TypeToken<List<Integer>>() {
            }.getType());
        }
        eventTypesMap.put("eventTypes", eventTypes);

        // 保存债券号和关注号的关系
        HashMap<Long, BondFavorite> bondUniCode2FavMap = new HashMap<Long, BondFavorite>();
        List<Long> bondUniCodes = new ArrayList<Long>();
        for (BondFavorite f : favList) {
            bondUniCodes.add(f.getBondUniCode());
            bondUniCode2FavMap.put(f.getBondUniCode(), f);
        }

        // 创建返回对象链表
        Set<Long> comparisonSet = new HashSet<Long>();
        List<BondComparisonDoc> comps = comparisonService.findComparisonByUserId((long) userId);
        comps.forEach(compDoc -> {
            comparisonSet.add(compDoc.getBondId());
        });

        // 查找bonds
        List<BondDetailDoc> bonds = this.findBondDetailByBondUniCodes(bondUniCodes, 0, limit, sort);

        // 回置关注id
        Map<Long, BondDetailVo> bondVos = new HashMap<Long, BondDetailVo>();
        bonds.stream().forEach(bondDetail -> {
            BondBasicInfoDoc bondBasicInfo = bondBasicInfoRepository.findOne(bondDetail.getBondUniCode());
            BondDetailVo bondDetailVo = new BondDetailVo();
            BeanUtils.copyProperties(bondDetail, bondDetailVo);
            BondFavorite fav = bondUniCode2FavMap.get(bondDetail.getBondUniCode());
            List<Integer> eventTypeList = eventTypesMap.get("eventTypes");

            bondDetailVo.setFavoriteId(fav.getFavoriteId());
            bondDetailVo.setIsFavorited(true);
            bondDetailVo.setIsCompared(comparisonSet.contains(bondDetail.getBondUniCode()));
            bondDetailVo.setOpeninterest(fav.getOpeninterest());
            Long eventMsgCount = 0L;
            if (null != bfGroup && bfGroup.getNotifiedEnable() == 1) {
                eventMsgCount = bondFavService.findBondEventMsgCount(userId, bondDetail.getBondUniCode(), fav.getBookmark(), fav.getCreateTime(),
                        eventTypeList);
            }
            bondDetailVo.setEventMsgCount(eventMsgCount);

            if (StringUtils.isNotBlank(fav.getRemark())) {
                bondDetailVo.setRemark(fav.getRemark());
            } else {
                bondDetailVo.setRemark("");
            }
            if ((null != bondDetail.getCurrStatus() && bondDetail.getCurrStatus().intValue() == 1)
                    && (null != bondDetail.getIssStaPar() && bondDetail.getIssStaPar().intValue() == 1)) {
                bondDetailVo.setExpiredState(0);
            } else {
                bondDetailVo.setExpiredState(1);
            }
            bondDetailVo.setRatePros(bondBasicInfo.getIssRatePros());
            bondVos.put(bondDetailVo.getBondUniCode(), bondDetailVo);
        });

        return bondVos;
    }

	/**
	 * 保存报价检测消息事件
	 * @param bondQuote
	 */
	private void publishBondQuoteNotiMsg(BondQuoteDoc bondQuote) {
		LOG.info(String.format("publishBondQuoteNotiMsg: unicode[%1$d] price[%2$f] unit[%3$d]",
				bondQuote.getBondUniCode(), (bondQuote.getSide() == 1?bondQuote.getBidPrice():bondQuote.getOfrPrice()), bondQuote.getPriceUnit()));
		Long bondUniCode = bondQuote.getBondUniCode();
		int bondSide = bondQuote.getSide();
		BigDecimal cleanPrice = bondQuote.getCleanPrice();
		
		long timeout = 24 * 60 * 60L;	//24小时缓存过期
		if (bondUniCode != null && cleanPrice != null) {
			// 首先redis缓存去重
			Long userId = bondQuote.getUserId();
			String currDateStr = SafeUtils.convertDateToString(SafeUtils.getCurrentTime(), SafeUtils.DATE_FORMAT1);
			String redisKey = String.format("%1$s_%2$d_%3$d_%4$d_%5$s",
					Constants.QUOTE_NOTI_REDIS_PREFIX, userId, bondUniCode, bondSide, currDateStr);
			String duplicatedQuoteValue = redisUtil.getMsgContent(redisKey);
			if (StringUtils.isNotBlank(duplicatedQuoteValue) && duplicatedQuoteValue.equals(cleanPrice.toString())) {
				// 过滤[同一只债，同一个人发的，相同报价]
				LOG.warn(String.format("publishBondQuoteNotiMsg: duplicated quote radar with redisKey[%1$s], value[%2$s]",
						redisKey, duplicatedQuoteValue));
			} else {
		        redisUtil.saveMsgWithTimeout(redisKey, cleanPrice.toString(), timeout);
		        BigDecimal valuation = getValuationInBondInfo(bondQuote.getBondCode());
		        
		        sendPriceData(bondQuote, bondUniCode, bondSide, cleanPrice, valuation);
			}
		}else{
			LOG.info("cleanPrice is null, bondUniCode:"+bondUniCode+",cleanPrice:"+cleanPrice);
		}
	}

	private void sendPriceData(BondQuoteDoc bondQuote, Long bondUniCode, int bondSide, BigDecimal cleanPrice,
			BigDecimal valuation) {
		
		//报价的price_unit为2的时候就是净价，单位为元，这时需要去根据净价换算成收益率去推送[零散的需求标记]
		int priceUnit = SafeUtils.getInt(bondQuote.getPriceUnit());
		if (priceUnit == 2) {
			bondQuote.setOfrPrice(bondQuote.getYtm());
			bondQuote.setBidPrice(bondQuote.getYtm());
		}
		
		switch(bondSide){
		    case 1:
		        senderService.sendBondIdxPrice2Rabbitmq(JSON.toJSONString(new BondIdxPriceJson(bondUniCode, 3, bondQuote.getBidPrice(), cleanPrice, valuation)));
		    	break;
		    case 2:
		        senderService.sendBondIdxPrice2Rabbitmq(JSON.toJSONString(new BondIdxPriceJson(bondUniCode, 2, bondQuote.getOfrPrice(), cleanPrice, valuation)));
		    	break;
			default:
				break;
		}
	}
	
	private BigDecimal getValuationInBondInfo(String code) {
		try {
			String sql = "SELECT CASE WHEN b.exercise_yield IS NOT NULL THEN b.exercise_yield  WHEN b.rate IS NOT NULL THEN b.rate ELSE 0 END AS rate from innodealing.bond_info b WHERE b.code='" + code	+ "' ORDER BY b.create_time DESC LIMIT 1";

			return jdbcTemplate.queryForObject(sql, BigDecimal.class);
		} catch (Exception ex) {
			//LOG.error("getValuationInBondInfo error," + ex.getMessage(), ex);
		}
		return null;
	}

    public BondPageAdapter<BondDetailDoc> findImpliedRatingBonds(String condition, Integer page, Integer limit, Integer type) {
        Object doc = getImpliedRatingByCondition(condition, type);

        if (doc == null)
            return new BondPageAdapter<BondDetailDoc>(new ArrayList<BondDetailDoc>(), null, 0);

        Set<Long> list = getBondRiskColumnByCondition(condition, doc);
        PageRequest request = new PageRequest(page - 1, limit, new Sort(Direction.DESC, "createTime"));
        Query query = queryConditionInUniCodes(list, request, null, null);

        return new BondPageAdapter<BondDetailDoc>(bondMongoTemplate.find(query, BondDetailDoc.class), request, bondMongoTemplate.count(query, BondDetailDoc.class));
    }

	private Set<Long> getBondRiskColumnByCondition(String condition, Object doc) {
		Set<Long> list = new HashSet<Long>();
        BondRiskColumnDoc columnDoc = null;
        Method method;
        try {
            method = doc.getClass().getDeclaredMethod(condition.split("\\*")[1]);
            if (method != null) {
                columnDoc = (BondRiskColumnDoc) method.invoke(doc);
            }
            if (columnDoc != null) {
                list = columnDoc.getComUniCodeList();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return list;
	}

	private Object getImpliedRatingByCondition(String condition, Integer type) {
		Object doc = null;
		if (type == 1) {
            doc = bondMongoTemplate.findById(condition.split("\\*")[0], BondImpliedRatingInfo.class);
        } else if (type == 2) {
            doc = bondMongoTemplate.findById(condition.split("\\*")[0], BondImpliedRatingCpInfo.class);
        }
		return doc;
	}

    public Object findImpliedRatingList(Integer type) {
        Sort sort = new Sort(Sort.Direction.ASC, "priority");
        Query query = new Query();
        query.with(sort);

        Object list = null;

        if (type == 1) {
            list = bondMongoTemplate.find(query, BondImpliedRatingInfo.class);
        } else if (type == 2) {
            list = bondMongoTemplate.find(query, BondImpliedRatingCpInfo.class);
        }

        return list;

    }

    /**
     * 债劵财务指标高级筛选
     * 
     * @param newFilterParam
     * @return
     */
    public Object issNdicatorFilter(BondDmFilterReq newFilterParam) {

        Long i1 = System.currentTimeMillis();
        
        if (StringUtils.isEmpty(newFilterParam.getQuarter())) {
            throw new NullPointerException("BondDmFilterReq Quarter is null,newFilterParam=" + newFilterParam.toString());
        }

        LOG.info(newFilterParam.toString());

        Map<String, Object> resultMap = new HashMap<String, Object>();

        Query query = new Query();
        UICriteriaBuilder cbuild = new UICriteriaBuilder(newFilterParam);
        query.addCriteria(cbuild.generate());
        query.fields().exclude("pdUiOpt").exclude("issRatingUiOpt").exclude("bondRatingUiOpt").exclude("tenorUiOpt").exclude("currStatus").exclude("issStaPar")
                .exclude("listStaPar");

        if (newFilterParam.getBondIndicatorFilterReqs() == null || newFilterParam.getBondIndicatorFilterReqs().isEmpty()) {// 不带财务指标查询总数
            // 取bond_detail_info
            long count = bondMongoTemplate.count(query, BondDetailDoc.class);
            resultMap.put("count", count);
        } else {// 带财务指标查询分位图及总数
            List<Map<String, Object>> bondDetailDocs = exec(newFilterParam, cbuild.generate());
            resultMap.put("data", bondDetailDocs);
            Object count = bondDetailDocs.get(newFilterParam.getBondIndicatorFilterReqs().size() - 1).get("count");
            resultMap.put("count", count != null ? count : 0);
        }

        Long i2 = System.currentTimeMillis();
        LOG.info("filter-->issNdicatorFilter:" + (i2 - i1)  );
        return resultMap;

    }

    /**
     * 分位图查询
     * 
     * @param newFilterParam
     * @param criteria
     * @return
     */
    private List<Map<String, Object>> exec(BondDmFilterReq newFilterParam, Criteria criteria) {
        
        Long i1 = System.currentTimeMillis();

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        List<BondIndicatorFilterReq> bondIndicatorFilterReqs = newFilterParam.getBondIndicatorFilterReqs();
        for (int i = 0; i < bondIndicatorFilterReqs.size(); i++) {
            resultList.add(new HashMap<String, Object>());

            if (BASICFIELD.contains(bondIndicatorFilterReqs.get(i).getField())) {
                bondIndicatorFilterReqs.get(i).setQuarter(null);
            }

        }
        // 创建线程池
        ExecutorService CachedThreadPool = Executors.newCachedThreadPool();
        List<Callable<String>> tasks = new ArrayList<Callable<String>>();
        for (int i = 0; i < bondIndicatorFilterReqs.size(); i++) {
            // 区间个数
            int sectionCount = 5000;
            final int number = i;
            Callable<String> c = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    ex(resultList, number, sectionCount, bondIndicatorFilterReqs, newFilterParam, criteria);
                    return "success";
                }
            };
            tasks.add(c);
        }
        try {
            // 执行任务
            CachedThreadPool.invokeAll(tasks);
        } catch (Exception e) {
            e.printStackTrace();
        }
        CachedThreadPool.shutdown();
        try {
            CachedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            LOG.error("等待任务完成中发生异常 ", e);
            e.printStackTrace();
        }

        Long i2 = System.currentTimeMillis();
        LOG.info("filter-->exec:" + (i2 - i1) );
        
        return resultList;
    }

    private Map<String, Object> ex(List<Map<String, Object>> resultList, int number, int sectionCount, List<BondIndicatorFilterReq> bondIndicatorFilterReqs,
            BondDmFilterReq newFilterParam, Criteria criteria) {

        Long i1 = System.currentTimeMillis();
        
        List<Object> fieldValueList = null;
        List<Object> list = null;
        BondIndicatorFilterReq req = bondIndicatorFilterReqs.get(number);
        Integer type = req.getType();
        String quarter = null;
        if (!BASICFIELD.contains(req.getField()) && StringUtils.isEmpty(req.getQuarter())) {
            quarter = newFilterParam.getQuarter();
        }
        Map<String, Object> map = execCall(bondIndicatorFilterReqs, criteria, number, quarter, sectionCount);
        @SuppressWarnings("unchecked")
        List<BasicDBObject> filterList = (ArrayList<BasicDBObject>) map.get("filterList");

        if (filterList.size() == 0) {
            fieldValueList = new ArrayList<Object>();
            list = new ArrayList<Object>();
        } else if (filterList.size() == 1) {
            fieldValueList = new ArrayList<Object>();
            list = new ArrayList<Object>();
            list.add(1);
            fieldValueList.add((Double) map.get("min"));
        } else if (filterList.size() > 1 && filterList.size() < 100 && sectionCount < 10000) {// 扩大一倍区间重新查询处理
            map = ex(resultList, number, sectionCount * 2, bondIndicatorFilterReqs, newFilterParam, criteria);
        } else if (filterList.size() >= 100 && filterList.size() <= 200) { // 不需要处理
            getFilterList(filterList, map);
            fieldValueList = getFieldValueList(filterList, type);
        } else if (filterList.size() > 200) { // 合并
            List<BasicDBObject> dblist = hb(filterList);
            getFilterList(dblist, map);
            fieldValueList = getFieldValueList(dblist, type);
        } else {
            map = execCall(bondIndicatorFilterReqs, criteria, number, quarter, 200);
            filterList = (ArrayList<BasicDBObject>) map.get("filterList");
            fieldValueList = getFieldValueList((Double) map.get("result"), 200, (Double) map.get("min"), type);
            list = getFilterList(filterList, 200);
        }
        if (fieldValueList != null) {
            map.put("fieldValueList", fieldValueList);
        }
        if (list != null) {
            map.put("filterList", list);
        }
        resultList.set(number, map);
        
        Long i2 = System.currentTimeMillis();
        LOG.info("filter-->ex:" + (i2 - i1)  );
        return map;
    }

    private List<Object> getFieldValueList(Double result, int sectionCount, Double min, Integer type) {

        List<Object> fieldValueList = new ArrayList<Object>();

        BigDecimal o = null;

        if (result != null) {
            o = new BigDecimal(result);
        }

        for (int i = 0; i <= sectionCount; i++) {
            if (result != null) {
                BigDecimal o2 = o.multiply(new BigDecimal(i)).add(new BigDecimal(min));
                if (isFinancialIndex(type)) {
                    o2 = o2.divide(new BigDecimal("10000"));
                }
                // fieldValueList.add(i, o2.setScale(10,
                // BigDecimal.ROUND_HALF_UP));
                fieldValueList.add(o2.doubleValue());
            } else {
                fieldValueList.add(i, 0D);
            }
        }
        fieldValueList.set(0, (Double)fieldValueList.get(0)-0.01D);
        fieldValueList.set(200, (Double)fieldValueList.get(200)+0.01D);

        return fieldValueList;

    }

    private List<Object> getFilterList(List<BasicDBObject> list, int sectionCount) {

        List<Object> resultList = new ArrayList<Object>();

        // 数据初始化
        for (int i = 0; i <= sectionCount; i++) {
            resultList.add(i, 0D);
        }

        // 赋值
        for (BasicDBObject db : list) {
            try {
                resultList.set((int) db.getLong("index"), (double) db.getLong("sum"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return resultList;

    }

    /**
     * 
     * log:(常用对数 10为底) double value 值 double base 底
     * 
     * @since CodingExample Ver 1.1
     */
    private double log(double value, double base) {
        double d = Math.log(value) / Math.log(base);
        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);
        return bg.doubleValue();
    }

    private List<BasicDBObject> hb(List<BasicDBObject> filterList) {
        
        Long i1 = System.currentTimeMillis();
        
        List<BasicDBObject> list = new ArrayList<BasicDBObject>();
        try {
            int count = filterList.size() / 200 + 1;
            int sum = 0;
            for (int i = 0; i < filterList.size(); i++) {
                BasicDBObject obj = filterList.get(i);
                if (i == 0 || i == filterList.size() - 1) {
                    list.add(obj);
                } else if (i % count == 0) {
                    BasicDBObject o = new BasicDBObject();
                    o.put("sum", sum);
                    o.put("_id", obj.getDouble("_id"));
                    list.add(o);
                    sum = 0;
                } else {
                    sum += obj.getLong("sum");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (list.size() > 200) {
            hb(list);
        }
        
        Long i2 = System.currentTimeMillis();
        LOG.info("filter-->ex:" + (i2 - i1)  );
        return list;
    }

    /**
     * 
     * getResultMap:(返回参数封装)
     *
     * @param @param
     *            bondIndicatorFilterReq
     * @param @return
     *            设定文件
     * @return Map<String,Object> DOM对象
     * @throws @since
     *             CodingExample Ver 1.1
     */
    private Map<String, Object> getResultMap(BondIndicatorFilterReq bondIndicatorFilterReq) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("field", bondIndicatorFilterReq.getField());
        resultMap.put("count", 0);
        resultMap.put("filterList", new ArrayList<>());
        resultMap.put("fieldValueList", new ArrayList<>());
        // 得到当前指标名及正负向
        BondFieldGroupMappingDoc doc = fieldGroupMappingCache.BOND_FINELD_GROUP_MAPPING().get(bondIndicatorFilterReq.getField());
        if (doc != null) {
            resultMap.put("fieldName", doc.getFieldName());
            resultMap.put("fieldType", doc.getFieldType());
        }
        return resultMap;
    }

    private BondIndicatorFilterReq getBondIndicatorFilterReq(BondIndicatorFilterReq bondIndicatorFilterReq) {
        return new BondIndicatorFilterReq(bondIndicatorFilterReq.getField(), bondIndicatorFilterReq.getQuarter(), null, null, bondIndicatorFilterReq.getType());
    }

    private String getKey(Object... objects) {

        StringBuffer key = new StringBuffer("");

        if (objects == null || objects.length < 1) {
            return "";
        }

        for (Object obj : objects) {
            key.append(String.valueOf(obj));
        }

        return key.toString();
    }

    private String getEncodeBase64(String key) {
        byte[] str = Base64.encodeBase64(key.getBytes(), true);
        return MD5Util.getMD5(new String(str));
    }

    private Boolean isEmpty(String key) {
        return redisUtil.get(key) != null && !"null".equals(redisUtil.get(key)) ? true : false;
    }
    
    private Boolean isCache(String key){
    	return key.indexOf("field=ofrPrice")==-1&&key.indexOf("field=price")==-1?true:false;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> execCall(List<BondIndicatorFilterReq> bondIndicatorFilterReqs, Criteria criteria, final int number, String quarter,
            int sectionCount) {

        Long i1 = System.currentTimeMillis();
        
        BondIndicatorFilterReq bondIndicatorFilterReq = bondIndicatorFilterReqs.get(number);

        if (StringUtils.isEmpty(bondIndicatorFilterReq.getQuarter()) && !StringUtils.isEmpty(quarter)
                && !BASICFIELD.contains(bondIndicatorFilterReq.getField())) {
            bondIndicatorFilterReq.setQuarter(quarter);
        }

        Map<String, Object> resultMap = getResultMap(bondIndicatorFilterReq);
        BondIndicatorFilterReq bondIndicatorFilterReq3 = null;
        List<DBObject> list2 = new ArrayList<DBObject>();
        StringBuffer str3 = new StringBuffer();
        for (int j = 0; j <= number; j++) {
            if (j == number) {
                bondIndicatorFilterReq3 = getBondIndicatorFilterReq(bondIndicatorFilterReqs.get(j));
            } else {
                bondIndicatorFilterReq3 = bondIndicatorFilterReqs.get(j);
            }
            list2.add(getElemMatch(bondIndicatorFilterReq3));
            str3.append(bondIndicatorFilterReq3.getField() + bondIndicatorFilterReq3.toString());
        }
        DBObject db1 = getMatch(bondIndicatorFilterReq3);
        String key = getKey("filterBasicdb", getEncodeBase64(getKey(str3.toString(), bondIndicatorFilterReq3.getField(),
                criteria.getCriteriaObject().hashCode(), list2.hashCode(), db1.hashCode(), sectionCount)));
        BasicDBObject basicdb2 = null;
        String o = redisUtil.getMsgContent(key);
        if (isEmpty(key) && isCache(str3.toString())) {
            basicdb2 = new BasicDBObject((Map<String, Object>) JSONObject.parse(o));
        } else {
            basicdb2 = getBasicdb(criteria, bondIndicatorFilterReq3, list2, db1, sectionCount);
            redisUtil.saveMsgWithTimeout(key, JSONObject.toJSONString(basicdb2), 10800);
        }

        if (basicdb2 == null) {
            return resultMap;
        }

        resultMap.put("min", basicdb2.getDouble("min"));
        resultMap.put("max", basicdb2.getDouble("max"));
        resultMap.put("result", basicdb2.getDouble("result"));

        if (StringUtils.isEmpty(bondIndicatorFilterReq.getMinIndicator())) {
            Integer type = bondIndicatorFilterReq.getType();
            if (isFinancialIndex(type)) {
                bondIndicatorFilterReq.setMinIndicator(accuracy(basicdb2.getDouble("min"), 10000, 2, 1));
            } else {
                bondIndicatorFilterReq.setMinIndicator(basicdb2.getDouble("min"));
            }
        }
        if (StringUtils.isEmpty(bondIndicatorFilterReq.getMaxIndicator())) {
            Integer type = bondIndicatorFilterReq.getType();
            if (isFinancialIndex(type)) {
                bondIndicatorFilterReq.setMaxIndicator(accuracy(basicdb2.getDouble("max"), 10000, 2, 1));
            } else {
                bondIndicatorFilterReq.setMaxIndicator(basicdb2.getDouble("max"));
            }
        }

        final BasicDBObject basicdb = basicdb2;

        // 创建线程池
        ExecutorService CachedThreadPool = Executors.newCachedThreadPool();
        List<Callable<String>> tasks = new ArrayList<Callable<String>>();
        Callable<String> c = new Callable<String>() {
            @Override
            public String call() throws Exception {
                ArrayList<BasicDBObject> filterList = new ArrayList<BasicDBObject>();
                StringBuffer str = new StringBuffer();
                BondIndicatorFilterReq bondIndicatorFilterReq = null;
                // 封装指标查询条件
                List<DBObject> list = new ArrayList<DBObject>();
                for (int j = 0; j <= number; j++) {
                    if (j == number) {
                        bondIndicatorFilterReq = getBondIndicatorFilterReq(bondIndicatorFilterReqs.get(j));
                    } else {
                        bondIndicatorFilterReq = bondIndicatorFilterReqs.get(j);
                    }
                    list.add(getElemMatch(bondIndicatorFilterReq));
                    str.append(bondIndicatorFilterReq.getField() + bondIndicatorFilterReq.toString());
                }
                DBObject db = getMatch(bondIndicatorFilterReq);
                String key = getKey("filterList", getEncodeBase64(getKey(str.toString(), criteria.getCriteriaObject().hashCode(),
                        bondIndicatorFilterReq.toString(), list.hashCode(), db.hashCode(), sectionCount)));
                String o = redisUtil.getMsgContent(key);
                if (isEmpty(key) && isCache(str.toString())) {
                    filterList = (ArrayList<BasicDBObject>) JSONObject.parseArray(o, BasicDBObject.class);
                } else {
                    filterList = getList(criteria, bondIndicatorFilterReq, basicdb, list, db);
                    redisUtil.saveMsgWithTimeout(key, JSONObject.toJSONString(filterList), 10800);
                }

                // 根据上一个条件过滤后的结果
                try {
                    if (filterList.size() > 0) {
                        resultMap.put("filterList", filterList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return "success";
            }
        };
        tasks.add(c);
        Callable<String> c1 = new Callable<String>() {
            @Override
            public String call() throws Exception {

                if (bondIndicatorFilterReqs.size() - 1 != number) {
                    return "success";
                }

                BasicDBObject basicdb2 = null;
                StringBuffer str = new StringBuffer();
                BondIndicatorFilterReq bondIndicatorFilterReq2 = null;
                // 封装指标查询条件
                List<DBObject> list = new ArrayList<DBObject>();
                for (int j = 0; j <= number; j++) {
                    if (j == number) {
                        bondIndicatorFilterReq2 = bondIndicatorFilterReq;
                    } else {
                        bondIndicatorFilterReq2 = bondIndicatorFilterReqs.get(j);
                    }
                    list.add(getElemMatch(bondIndicatorFilterReq2));
                    str.append(bondIndicatorFilterReq.getField() + bondIndicatorFilterReq.toString());
                }
                DBObject db = getMatch(bondIndicatorFilterReq);
                String key = getKey("filterCount", getEncodeBase64(getKey(str.toString(), criteria.getCriteriaObject().hashCode(),
                        bondIndicatorFilterReq.toString(), list.hashCode(), db.hashCode(), sectionCount)));
                String o = redisUtil.getMsgContent(key);
                if (isEmpty(key) && isCache(str.toString())) {
                    basicdb2 = new BasicDBObject((Map<String, Object>) JSONObject.parse(o));
                } else {
                    basicdb2 = getBasicdb(criteria, bondIndicatorFilterReq, list, db, sectionCount);
                    redisUtil.saveMsgWithTimeout(key, JSONObject.toJSONString(basicdb2), 10800);
                }
                if (basicdb2 == null) {
                    resultMap.put("count", 0);
                    return "success";
                }
                // 根据上一个条件筛选后的总数
                resultMap.put("count", basicdb2 != null ? basicdb2.getLong("count") : 0);
                return "success";
            }
        };
        tasks.add(c1);
        try {
            // 执行任务
            CachedThreadPool.invokeAll(tasks);
        } catch (Exception e) {
            e.printStackTrace();
        }

        CachedThreadPool.shutdown();

        try {
            CachedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            LOG.error("等待任务完成中发生异常 ", e);
            e.printStackTrace();
        }
        
        Long i2 = System.currentTimeMillis();
        LOG.info("filter-->exec:" + (i2 - i1)  );

        return resultMap;
    }

    /**
     * 得到分位图基础数据(区间,最大值,最小值,总数)
     * 
     * @param criteria
     * @param bondIndicatorFilterReq
     * @param list
     * @param db
     * @return
     */
    private BasicDBObject getBasicdb(Criteria criteria, BondIndicatorFilterReq bondIndicatorFilterReq, List<DBObject> list, DBObject db, int sectionCount) {
        
        Long i1 = System.currentTimeMillis();
        
        // 过滤条件(发行人,财务指标,季度)
        List<DBObject> listdb = new ArrayList<DBObject>();

        DBObject match = new BasicDBObject("$match", criteria.getCriteriaObject() != null ? criteria.getCriteriaObject() : new BasicDBObject());

        listdb.add(match);

        DBObject match1 = new BasicDBObject("$match", new BasicDBObject("$and", list));

        listdb.add(match1);

        listdb.add(getFinancesProject(bondIndicatorFilterReq));

        if (list.get(list.size() - 1).get("quarters") != null) { // 如果最后一个指标不是基础指标
            // $unwind将文档中的某一个数组类型字段拆分成多条，每条包含数组中的一个值(一个季度一条数据)
            listdb.add(new BasicDBObject("$unwind", "$quarters"));
            listdb.add(new BasicDBObject("$match", db));
        }

        DBObject group = new BasicDBObject();
        DBObject grop = new BasicDBObject();
        grop.put("_id", null);
        grop.put("min", new BasicDBObject("$min", "$" + bondIndicatorFilterReq.getField()));
        grop.put("max", new BasicDBObject("$max", "$" + bondIndicatorFilterReq.getField()));
        grop.put("count", new BasicDBObject("$sum", 1));
        group.put("$group", grop);
        listdb.add(group);

        DBObject projects = new BasicDBObject();
        DBObject project = new BasicDBObject();
        Object[] divide = new Object[2];
        divide[0] = new BasicDBObject("$subtract", new Object[] { "$max", "$min" });
        divide[1] = sectionCount; // 平均分配到200个点
        project.put("min", 1);
        project.put("max", 1);
        project.put("count", 1);
        project.put("result", new BasicDBObject("$divide", divide));
        projects.put("$project", project);
        listdb.add(projects);

        AggregationOutput output = bondMongoTemplate.getCollection("bond_detail_info").aggregate(listdb);
        Iterator<?> iterator = output.results().iterator();

        BasicDBObject basicdb = null;

        while (iterator.hasNext()) {
            basicdb = (BasicDBObject) iterator.next();
        }
        
        Long i2 = System.currentTimeMillis();
        LOG.info("filter-->getBasicdb:" + (i2 - i1)  );

        return basicdb;
    }

    private ArrayList<BasicDBObject> getList(Criteria criteria, BondIndicatorFilterReq bondIndicatorFilterReq, BasicDBObject basicdb, List<DBObject> list,
            DBObject db) {
        
        Long i1 = System.currentTimeMillis();

        ArrayList<BasicDBObject> resultList = new ArrayList<BasicDBObject>();

        List<DBObject> listdb = new ArrayList<DBObject>();

        DBObject match = new BasicDBObject("$match", criteria.getCriteriaObject() != null ? criteria.getCriteriaObject() : new BasicDBObject());
        listdb.add(match);

        DBObject match1 = new BasicDBObject("$match", new BasicDBObject("$and", list));
        listdb.add(match1);

        listdb.add(getFinancesProject(bondIndicatorFilterReq));

        if (list.get(list.size() - 1).get("quarters") != null) { // 如果最后一个指标不是基础指标
            listdb.add(new BasicDBObject("$unwind", "$quarters"));
            listdb.add(new BasicDBObject("$match", db));
        }

        DBObject project = new BasicDBObject();

        // 不能除以0
        Double result = basicdb.getDouble("result") != 0L ? basicdb.getDouble("result") : 1L;

        project.put("_id", 1);
        project.put("w", new BasicDBObject("$floor", new BasicDBObject("$divide",
                new Object[] { new BasicDBObject("$subtract", new Object[] { "$" + bondIndicatorFilterReq.getField(), basicdb.getDouble("min") }), result })));

        DBObject let = new BasicDBObject();
        let.put("vars", new BasicDBObject("varin", "$" + bondIndicatorFilterReq.getField()));
        let.put("in",
                new BasicDBObject("$add",
                        new Object[] {
                                basicdb.getDouble(
                                        "min"),
                                new BasicDBObject("$multiply", new Object[] {
                                        new BasicDBObject("$floor",
                                                new BasicDBObject("$divide", new Object[] {
                                                        new BasicDBObject("$subtract", new Object[] { "$$varin", basicdb.getDouble("min") }), result })),
                                        result }) }));
        project.put("pos", new BasicDBObject("$let", let));

        DBObject projects = new BasicDBObject();
        projects.put("$project", project);
        listdb.add(projects);

        DBObject group = new BasicDBObject();
        DBObject grop = new BasicDBObject();
        grop.put("_id", "$pos");
        grop.put("sum", new BasicDBObject("$sum", 1));
        grop.put("index", new BasicDBObject("$avg", "$w"));
        group.put("$group", grop);
        listdb.add(group);

        DBObject sorts = new BasicDBObject();
        DBObject sort = new BasicDBObject();
        sort.put("_id", 1);
        sorts.put("$sort", sort);
        listdb.add(sorts);

        AggregationOutput output = bondMongoTemplate.getCollection("bond_detail_info").aggregate(listdb);
        Iterator<?> iterator = output.results().iterator();

        while (iterator.hasNext()) {
            BasicDBObject basicdb1 = (BasicDBObject) iterator.next();
            resultList.add(basicdb1);
        }
        
        Long i2 = System.currentTimeMillis();
        LOG.info("filter-->getList:" + (i2 - i1)  );

        return resultList;
    }

    private List<Object> getFieldValueList(List<BasicDBObject> list, Integer type) {

        List<Object> fieldValueList = new ArrayList<Object>();

        // 赋值
        if (list.size() > 0) {
            for (BasicDBObject db : list) {
                try {
                    BigDecimal o2 = new BigDecimal(db.getDouble("_id"));
                    if (isFinancialIndex(type)) {
                        o2 = o2.divide(new BigDecimal("10000"));
                    }
                    // fieldValueList.add(o2.setScale(10,
                    // BigDecimal.ROUND_HALF_UP));
                    fieldValueList.add(o2.doubleValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return fieldValueList;

    }

    private void getFilterList(List<BasicDBObject> list, Map<String, Object> map) {

        int count = 0;

        List<Object> resultList = new ArrayList<Object>();
        List<Object> resultList2 = new ArrayList<Object>();

        // 赋值
        if (list.size() > 0) {
            for (BasicDBObject db : list) {
                try {
                    double d = db.getDouble("sum");
                    resultList.add(d);
                    resultList2.add(log(d, 10));// 对数处理
                    if (d > 500) {
                        count = 1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (count == 0) {
            map.put("filterList", resultList);
            map.put("logarithmList", new ArrayList<Object>());
        } else {
            map.put("filterList", resultList);
            map.put("logarithmList", resultList2);
        }

    }

    /**
     * 封装财务指标条件
     * 
     * @param bondIndicatorFilterReq
     * @return
     */
    private DBObject getMatch(BondIndicatorFilterReq bondIndicatorFilterReq) {

        Integer type = bondIndicatorFilterReq.getType();

        if (bondIndicatorFilterReq == null || StringUtils.isEmpty(bondIndicatorFilterReq.getField())) {
            return null;
        }

        DBObject dbObject = new BasicDBObject();

        if (!StringUtils.isEmpty(bondIndicatorFilterReq.getQuarter())) {
            dbObject.put("quarters.quarter", bondIndicatorFilterReq.getQuarter());
        }
        if (bondIndicatorFilterReq.getMinIndicator() != null && bondIndicatorFilterReq.getMaxIndicator() != null) {
            DBObject o2 = new BasicDBObject();
            if (isFinancialIndex(type)) {
                o2.put("$gte", bondIndicatorFilterReq.getMinIndicator() * 10000);
                o2.put("$lte", bondIndicatorFilterReq.getMaxIndicator() * 10000);
            } else {
                o2.put("$gte", bondIndicatorFilterReq.getMinIndicator());
                o2.put("$lte", bondIndicatorFilterReq.getMaxIndicator());
            }
            dbObject.put(bondIndicatorFilterReq.getField(), o2);
        } else if (bondIndicatorFilterReq.getMinIndicator() == null && bondIndicatorFilterReq.getMaxIndicator() == null) {
            dbObject.put(bondIndicatorFilterReq.getField(), new BasicDBObject("$exists", true));
        } else {
            if (bondIndicatorFilterReq.getMinIndicator() != null) {
                DBObject o2 = new BasicDBObject();
                if (isFinancialIndex(type)) {
                    o2.put("$gte", bondIndicatorFilterReq.getMinIndicator() * 10000);
                } else {
                    o2.put("$gte", bondIndicatorFilterReq.getMinIndicator());
                }
                dbObject.put(bondIndicatorFilterReq.getField(), o2);
            }
            if (bondIndicatorFilterReq.getMaxIndicator() != null) {
                DBObject o2 = new BasicDBObject();
                if (isFinancialIndex(type)) {
                    o2.put("$lte", bondIndicatorFilterReq.getMaxIndicator() * 10000);
                } else {
                    o2.put("$lte", bondIndicatorFilterReq.getMaxIndicator());
                }
                dbObject.put(bondIndicatorFilterReq.getField(), o2);

            }
        }

        return dbObject;
    }

    /**
     * 封装财务指标条件(子文档查询)
     * 
     * @param bondIndicatorFilterReq
     * @return
     */
    private DBObject getElemMatch(BondIndicatorFilterReq bondIndicatorFilterReq) {

        if (bondIndicatorFilterReq == null || StringUtils.isEmpty(bondIndicatorFilterReq.getField())) {
            return null;
        }

        Integer type = bondIndicatorFilterReq.getType();

        DBObject dbObject = new BasicDBObject();
        DBObject dbObject1 = new BasicDBObject();
        DBObject dbObject2 = new BasicDBObject();

        String field = bondIndicatorFilterReq.getField().substring(bondIndicatorFilterReq.getField().indexOf(".") + 1);

        if (!StringUtils.isEmpty(bondIndicatorFilterReq.getQuarter())) {
            dbObject2.put("quarter", bondIndicatorFilterReq.getQuarter());
        } else {
            return getMatch(bondIndicatorFilterReq);
        }

        if (bondIndicatorFilterReq.getMinIndicator() != null && bondIndicatorFilterReq.getMaxIndicator() != null) {
            DBObject o2 = new BasicDBObject();
            if (isFinancialIndex(type)) {
                o2.put("$gte", bondIndicatorFilterReq.getMinIndicator() * 10000);
                o2.put("$lte", bondIndicatorFilterReq.getMaxIndicator() * 10000);
            } else {
                o2.put("$gte", bondIndicatorFilterReq.getMinIndicator());
                o2.put("$lte", bondIndicatorFilterReq.getMaxIndicator());
            }
            dbObject2.put(field, o2);
        } else if (bondIndicatorFilterReq.getMinIndicator() == null && bondIndicatorFilterReq.getMaxIndicator() == null) {
            dbObject2.put(field, new BasicDBObject("$exists", true));
        } else {
            if (bondIndicatorFilterReq.getMinIndicator() != null) {
                DBObject o2 = new BasicDBObject();
                if (isFinancialIndex(type)) {
                    o2.put("$gte", bondIndicatorFilterReq.getMinIndicator() * 10000);
                } else {
                    o2.put("$gte", bondIndicatorFilterReq.getMinIndicator());
                }
                dbObject2.put(field, o2);
            }
            if (bondIndicatorFilterReq.getMaxIndicator() != null) {
                DBObject o2 = new BasicDBObject();
                if (isFinancialIndex(type)) {
                    o2.put("$lte", bondIndicatorFilterReq.getMaxIndicator() * 10000);
                } else {
                    o2.put("$lte", bondIndicatorFilterReq.getMaxIndicator());
                }
                dbObject2.put(field, o2);

            }
        }

        dbObject1.put("$elemMatch", dbObject2);
        dbObject.put("quarters", dbObject1);

        return dbObject;
    }

    private DBObject getFinancesProject(BondIndicatorFilterReq bondDmFilterReq) {
        DBObject projects = new BasicDBObject();
        DBObject project = new BasicDBObject();
        project.put(bondDmFilterReq.getField(), 1);
        project.put("quarters.quarter", 1);
        projects.put("$project", project);
        return projects;
    }

    private static Double accuracy(double num, double total, int scale, int type) {
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
        // 可以设置精确几位小数
        df.setMaximumFractionDigits(scale);
        // 模式 例如四舍五入
        df.setRoundingMode(RoundingMode.HALF_UP);
        double accuracy_num = 0.0;
        if (type == 1) {
            accuracy_num = num / total;
        } else if (type == 2) {
            accuracy_num = num * total;
        }
        String number = df.format(accuracy_num).replace(",", "");
        if (!StringUtils.isEmpty(number)) {
            return Double.parseDouble(number);
        }
        return accuracy_num;
    }

    @SuppressWarnings("all")
    private static String accuracy(float num, float total) {
        NumberFormat nt = NumberFormat.getPercentInstance();
        // 设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(0);
        float accuracy_num = num / total;
        return nt.format(accuracy_num);
    }

    /**
     * 财务指标数据
     * 
     * @param type
     * @return
     */
    public List<BondFieldGroupMappingDoc> queryBondFieldGroupMappingList(Integer type) {

        List<BondFieldGroupMappingDoc> list = new ArrayList<BondFieldGroupMappingDoc>();

        StringBuffer sql = new StringBuffer();

        switch (type) {
            case 1:// 市场指标
                list.add(new BondFieldGroupMappingDoc("estYield", "中债估值(%)", 1, 1, 0));
                // list.add(new BondFieldGroupMappingDoc("estDirtyPrice",
                // "估价全价", 0));
                list.add(new BondFieldGroupMappingDoc("estCleanPrice", "估值净价", 0, 1, 0));
                list.add(new BondFieldGroupMappingDoc("optionYield", "行权收益率(%)", 1, 1, 0));
                list.add(new BondFieldGroupMappingDoc("staticSpread", "单券利差", 0, 1, 0));
                // list.add(new
                // BondFieldGroupMappingDoc("staticSpreadInduQuantile",
                // "单券利差行业分位", 0));
                list.add(new BondFieldGroupMappingDoc("macd", "久期", 0, 1, 0));
                list.add(new BondFieldGroupMappingDoc("modd", "修正久期", 0, 1, 0));
                list.add(new BondFieldGroupMappingDoc("convexity", "凸性", 0, 1, 0));
                list.add(new BondFieldGroupMappingDoc("convRatio", "标准券折算率", 0, 1, 0));
                list.add(new BondFieldGroupMappingDoc("ofrPrice", "报价", 0, 1, 0));
                list.add(new BondFieldGroupMappingDoc("price", "成交价", 0, 1, 0));
                return list;
            case 2:// 专项指标
                sql.append("SELECT ");
//                sql.append("\n\t CONCAT('quarters.',column_name)  AS columnName,");
//                sql.append("\n\tfield_name AS fieldName,");
//                sql.append("\n\tgroup_name AS groupName,");
//                sql.append("\n\tfield_type AS fieldType,");
//                sql.append("\n\tpercent AS percent");
//                sql.append("\n\tFROM");
//                sql.append("\n\tdmdb.dm_field_group_mapping ");
//                sql.append("\n\tWHERE table_name = 'dm_analysis_indu' ");
                sql.append("\n\t CONCAT('quarters.',f.column_name)  AS columnName,");
                sql.append("\n\t f.field_name AS fieldName,");
                sql.append("\n\t g.group_name AS groupName,");
                sql.append("\n\t f.field_type AS fieldType,");
                sql.append("\n\t f.percent AS percent");
                sql.append("\n\t from t_bond_indicator_info f ");
                sql.append("\n\t LEFT JOIN t_bond_indicator_relevance_group r ON f.id = r.indicator_id ");
                sql.append("\n\t LEFT JOIN t_bond_indicator_group g ON r.group_id = g.id ");
                sql.append("\n\t WHERE f.indicator_type=0 AND f.effective=1 AND f.table_name = 'dm_analysis_indu' ");
                break;
            case 3:// 资产负债
            case 4:// 利润
            case 5:// 现金流量
                sql.append("SELECT ");
                sql.append("\n\t CONCAT('quarters.',column_name)  AS columnName,");
                sql.append("\n\tfield_name AS fieldName,");
                sql.append("\n\tgroup_name AS groupName,");
                sql.append("\n\tgroup_name_parent AS groupNameParent ");
                sql.append("\n\tFROM");
                sql.append("\n\tdmdb.field_group_mapping ");
                sql.append("\n\tWHERE table_name = 'manu_fina_sheet' AND column_name NOT REGEXP '_'");
                int i = 0;
                if (type == 3) {
                    i = 1;
                } else if (type == 4) {
                    i = 2;
                } else if (type == 5) {
                    i = 3;
                }
                sql.append("\n\tAND TYPE = " + i);
                break;
            default:
                break;
        }
        list = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(BondFieldGroupMappingDoc.class));

        // 单位处理
        if (list == null || list.size() < 1) {
            return list;
        }

        list.stream().forEach(doc -> {
            String fieldName = doc.getFieldName();
            if (!StringUtils.isEmpty(fieldName)) {
                for (String key : COMPANYS.keySet()) {
                    if (fieldName.indexOf(key) != -1) {
                        doc.setCompany(key);
                        doc.setCompanyType(COMPANYS.get(key));
                        break;
                    } else {
                        doc.setCompanyType(0);
                    }
                }
            }
        });

        return list;

    }

    /**
     * 债劵财务指标高级筛选-->查看结果
     * @param filter
     * @param page
     * @param limit
     * @param sort
     * @param userid
     * @return
     * @throws Exception
     */
    public BondPageAdapter<BondDetailDoc> issNdicatorFilterList(BondDmFilterReq filter, Integer page, Integer limit, String sort, Long userid)
            throws Exception {

        String[] sortPars = sort.split(":");
        String sortField = sortPars[0];
        Direction sortDir = sortPars[1].toLowerCase().startsWith("des") ? Sort.Direction.DESC : Sort.Direction.ASC;

        Sort sorter = null;
        String sortName = null;
        if (sortField.equalsIgnoreCase("tenor")) {
            sortName = "tenorDays";
            sorter = new Sort(sortDir, "tenorDays");
        } else if (sortField.equalsIgnoreCase("updateTime")) {
            sortName = sortField;
            sorter = new Sort(new Order(sortDir, sortField), new Order(Sort.Direction.ASC, "name"));
        } else if (sortField.equalsIgnoreCase("pd")) {
            sortName = "pdSortRRs";
            sorter = new Sort(new Order(sortDir, "pdSortRRs"));
        } else if (sortField.equalsIgnoreCase("worstPd")) {
            sortName = "worstPdNum";
            sorter = new Sort(new Order(sortDir, "worstPdNum"));
        } else {
            sortName = sortField;
            sorter = new Sort(sortDir, sortField);
        }

        if (filter.getBondIndicatorFilterReqs() != null && filter.getBondIndicatorFilterReqs().size() > 0) {
            for (int i = 0; i < filter.getBondIndicatorFilterReqs().size(); i++) {

                if (BASICFIELD.contains(filter.getBondIndicatorFilterReqs().get(i).getField())) {
                    filter.getBondIndicatorFilterReqs().get(i).setQuarter(null);
                }

            }
        }

        // 结果
        List<DBObject> listdb = new ArrayList<DBObject>();
        // 总数
        List<DBObject> listdb2 = new ArrayList<DBObject>();

        UICriteriaBuilder cbuild = new UICriteriaBuilder(filter);

        DBObject match = new BasicDBObject("$match",
                cbuild.generate().getCriteriaObject() != null ? cbuild.generate().getCriteriaObject() : new BasicDBObject());

        listdb.add(match);
        listdb2.add(match);

        if (filter.getBondIndicatorFilterReqs() != null && filter.getBondIndicatorFilterReqs().size() > 0) {

            BondIndicatorFilterReq req = filter.getBondIndicatorFilterReqs().get(filter.getBondIndicatorFilterReqs().size() - 1);

            if (!BASICFIELD.contains(req.getField()) && StringUtils.isEmpty(req.getQuarter())) {
                req.setQuarter(filter.getQuarter());
            }

            BondIndicatorFilterReq bondIndicatorFilterReq2 = null;

            List<DBObject> list = new ArrayList<DBObject>();
            for (int j = 0; j < filter.getBondIndicatorFilterReqs().size(); j++) {
                bondIndicatorFilterReq2 = filter.getBondIndicatorFilterReqs().get(j);
                list.add(getElemMatch(bondIndicatorFilterReq2));
            }

            for (int i = 0; i < list.size(); i++) {
                listdb.add(new BasicDBObject("$match", list.get(i)));
                listdb2.add(new BasicDBObject("$match", list.get(i)));
            }

        }

        // 需要显示的列
        List<String> rows = getResultColumn(filter, 1 ,userid);
        DBObject projects = new BasicDBObject();
        DBObject project = new BasicDBObject();
        if (rows != null && rows.size() > 0) {
            rows.stream().forEach(row -> {
                project.put(row, 1);
            });
        }
        // 财务指标
        Map<String, String> finances = getResultFinance(filter.getBondIndicatorFilterReqs());
        if (finances != null && finances.size() > 0) {
            Iterator<String> keys = finances.keySet().iterator();
            while (keys.hasNext()) {
                project.put(keys.next(), 1);
            }
            project.put("quarters.quarter", 1);
        }
        projects.put("$project", project);
        listdb.add(projects);
        DBObject sorts = new BasicDBObject();
        DBObject sortObject = new BasicDBObject();
        sortObject.put(sortName, sortPars[1].toLowerCase().startsWith("des") ? -1 : 1);
        sorts.put("$sort", sortObject);
        listdb.add(sorts);

        PageRequest request = null;

        if (page != null && limit != null) {
            request = new PageRequest(page, limit, sorter);

            DBObject skipObject = new BasicDBObject();
            skipObject.put("$skip", request.getOffset());
            listdb.add(skipObject);

            DBObject limitObject = new BasicDBObject();
            limitObject.put("$limit", request.getPageSize());
            listdb.add(limitObject);
        }

        return queryBondDetail(listdb, listdb2, request, userid);

    }

    private BondPageAdapter<BondDetailDoc> queryBondDetail(List<DBObject> listdb, List<DBObject> listdb2, PageRequest request, Long userId) throws Exception {
        // 保存债券号和关注号的关系
        if (userId == null) {
            throw new BusinessException("用户 id不能为空");
        }

        DBObject group = new BasicDBObject();
        DBObject grop = new BasicDBObject();
        grop.put("_id", null);
        grop.put("count", new BasicDBObject("$sum", 1));
        group.put("$group", grop);
        listdb2.add(group);

        AggregationOutput output2 = bondMongoTemplate.getCollection("bond_detail_info").aggregate(listdb2);

        Iterator<?> iterator2 = output2.results().iterator();
        long count = 0;
        while (iterator2.hasNext()) {
            BasicDBObject db = (BasicDBObject) iterator2.next();
            count = db.getLong("count");
        }

        if (count == 0)
            return new BondPageAdapter<BondDetailDoc>(new ArrayList<BondDetailDoc>(), request, count);

        HashMap<Long, BondFavorite> bondUniCode2FavMap = new HashMap<Long, BondFavorite>();
        List<BondFavorite> favList = bondFavService.findFavoriteByUserId(toIntExact(userId));
        if (favList != null) {
            favList.forEach(f -> {
                bondUniCode2FavMap.put(f.getBondUniCode(), f);
            });
        }

        // 对比列表
        Set<Long> comparisonSet = new HashSet<Long>();
        List<BondComparisonDoc> comps = comparisonService.findComparisonByUserId(userId);
        if (comps != null) {
            comps.forEach(compDoc -> {
                comparisonSet.add(compDoc.getBondId());
            });
        }

        List<BondDetailDoc> bondDetailDocs = new ArrayList<BondDetailDoc>();

        AggregationOutput output = bondMongoTemplate.getCollection("bond_detail_info").aggregate(listdb);
        Iterator<?> iterator = output.results().iterator();

        BondDetailDoc doc = null;
        while (iterator.hasNext()) {
            BasicDBObject db = (BasicDBObject) iterator.next();
            doc = BeanUtil.dbObject2Bean(db, new BondDetailDoc());
            doc.setBondUniCode(db.getLong("_id"));
            bondDetailDocs.add(doc);
        }

        bondDetailDocs.forEach(bond -> {
            BondFavorite fav = bondUniCode2FavMap.get(bond.getBondUniCode());
            if (fav != null) {
                bond.setIsFavorited(true);
                bond.setFavoriteId(fav.getFavoriteId());
            } else {
                bond.setIsFavorited(false);
            }
            bond.setIsCompared(comparisonSet.contains(bond.getBondUniCode()));
        });

        return new BondPageAdapter<BondDetailDoc>(bondDetailDocs, request, count);

    }

    /**
     * 筛选结果导出
     * @param newFilterParam
     * @param userid
     * @param response
     * @throws Exception
     */
    public void exportExcel(BondDmFilterReq newFilterParam, long userid, HttpServletResponse response) throws Exception {

        BondPageAdapter<BondDetailDoc> pages = induAdapter.convByInduClass(issNdicatorFilterList(newFilterParam, null, null, "updateTime:desc", userid), newFilterParam.getInduClass(),userid);
        if (pages == null || pages.getContent() == null || pages.getContent().size() < 1)
            return;

        List<BondDetailDoc> bonds = pages.getContent();

        // 筛选范围
        List<String> rows = getResultColumn(newFilterParam, 2 , userid);
        // 财务指标
        Map<String, String> finances = getResultFinance(newFilterParam.getBondIndicatorFilterReqs());

        if (finances.containsKey("convRatio")) {
            finances.remove("convRatio");
        }
        if (finances.containsKey("estYield")) {
            finances.remove("estYield");
        }
        if (finances.containsKey("ofrPrice")) {
            finances.remove("ofrPrice");
        }
        if (finances.containsKey("price")) {
            finances.remove("price");
        }
        //0没有中债权限
        if("0".equals(bondUserOperationService.findDebtBasic(userid))){
            rows.remove("fairValue");
        }
        
        rows.remove("induNameSw");
        rows.remove("institutionInduMap");
        rows.remove("instRatingMap");
        rows.remove("instInvestmentAdviceMap");

        // 总列数=筛选范围数+财务指标筛选数
        String[] rowsName = new String[rows.size() + finances.size()];
        // 排除掉第一列bondUniCode导出
        for (int i = 1; i < rows.size(); i++) {
            rowsName[i] = COLUMNMAP.get(rows.get(i));
        }
        // 记录筛选指标总数
        int count = 0;

        Iterator<String> keys = finances.keySet().iterator();
        while (keys.hasNext() && count < finances.size()) {
            BondFieldGroupMappingDoc doc = fieldGroupMappingCache.BOND_FINELD_GROUP_MAPPING().get(keys.next());
            String t = null;
            try {
                t = doc.getFieldName();
            } catch (Exception e) {
                t = "指标";
            }
            // 根据已保存的债劵筛选范围总数+指标下标赋值
            rowsName[rows.size() + count] = t;
            count++;
        }

        // 具体的数据集合
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        for (int i = 0; i < bonds.size(); i++) {
            BondDetailDoc doc = bonds.get(i);
            objs = new Object[rows.size() + finances.size()];
            Method method = null;
            count = 0;
            // 筛选范围数据
            for (int j = 1; j < rows.size(); j++) {
                // 通过反射得到属性具体值并赋值
                String column = rows.get(j);
                method = getDeclaredMethod(doc,"get" + column.substring(0, 1).toUpperCase() + column.substring(1));
                if (method != null) {
                    Object obj = method.invoke(doc);
                    if ("dmBondType".equals(column)) {
                        objs[j] = BONDTYPES.get(obj);
                    } else if ("ownerType".equals(column)) {
                        objs[j] = OWNERTYPES.get(obj);
                    } else if ("market".equals(column)) {
                        objs[j] = MARKETS.get(obj);
                    } else if ("munInvest".equals(column)) {
                        objs[j] = (Boolean) obj ? "城投" : "非城投";
                    } else if ("priceUpdateTime".equals(column) && obj!=null) {
                        objs[j] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date)obj);
                    } else {
                        objs[j] = obj;
                    }
                }
            }
            // 指标数据
            keys = finances.keySet().iterator();
            while (keys.hasNext() && count < finances.size()) {
                String key = keys.next();
                String value = finances.get(key);
                if (!BASICFIELD.contains(key)) { // 财务指,专项指标
                    for (Map<String, Object> map : doc.getQuarters()) {
                        // 找到当前债劵下面对应的季度财务信息,并赋值到对应列
                        if (value.equals(map.get("quarter"))) {
                            objs[rows.size() + count] = map.get(key.substring(key.indexOf(".") + 1));
                            break;
                        }
                    }
                } else if (BASICFIELD.contains(key)) { // 基础指标
                    method = doc.getClass().getDeclaredMethod("get" + key.substring(0, 1).toUpperCase() + key.substring(1));
                    if (method != null) {
                        Object obj = method.invoke(doc);
                        objs[rows.size() + count] = obj;
                    }
                }
                count++;
            }
            dataList.add(objs);
        }

        // 文件名(方案名_日期)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String fileName = newFilterParam.getFilterName() + "_" + sdf.format(new Date());

        // 导出
        ExportExcel ex = new ExportExcel(fileName, rowsName, dataList, fileName);
        ex.export(response);
    }

    /**
     * 筛选范围
     * 
     * @param newFilterParam
     * @return
     */
    private List<String> getResultColumn(BondDmFilterReq newFilterParam, Integer type ,Long userid) {
    	
        List<String> list = new ArrayList<String>();

        list.add("_id");
        list.add("code");
        list.add("name");
        list.add("tenor");
        list.add("fairValue");
        list.add("issBondRating");
        list.add("pd");
        list.add("pdTime");
        list.add("worstPd");
        list.add("worstPdTime");
        list.add("impliedRating");
        list.add("poNegtive");// 最近一个月负面舆情
        list.add("coupRate"); // 票面
        list.add("convRatio"); // 折算率
        list.add("induName");
        list.add("induNameSw");
        list.add("ofrPrice"); //ofr价格
        list.add("ofrVol"); //ofr报价数额
        list.add("ofrOrderCnt"); //ofr报价笔数
        list.add("ofrUpdateTime"); //ofr报价日期
        list.add("price"); //成交价 
        list.add("priceCount"); //成交价数目 
        list.add("priceUpdateTime"); //最后成交日期
        list.add("institutionInduMap"); //机构所属行业
        if (newFilterParam.getDmBondTypes() != null && newFilterParam.getDmBondTypes().size() != 6) {
            list.add("dmBondType"); // 劵种
        }
        if (newFilterParam.getAreaCodes() != null && newFilterParam.getAreaCodes().size() > 0) {
            list.add("areaName1");
        }
        if (newFilterParam.getOwnerTypes() != null && newFilterParam.getOwnerTypes().size() > 0) {
            list.add("ownerType");// 企业
        }
        if (newFilterParam.getMarkets() != null && newFilterParam.getMarkets().size() > 0) {
            list.add("market");// 市场
        }
        if (newFilterParam.getMunInvest() != null) {
            list.add("munInvest");
        }
        if (type == 1) {
            list.add("pdDiff");
            list.add("riskWarning");
            list.add("worstRiskWarning");
            list.add("defaultDate");
            list.add("tenorDays");
            list.add("updateTime");
            list.add("pdSortRRs");
            list.add("worstPdNum");
        }
      //内部评级权限
      Integer org_id = bondInstitutionInduAdapter.getInstitution(userid);
	  String sql2 = "SELECT COUNT(1) FROM institution.t_bond_inst_code WHERE org_id = %1$d";
	  if(jdbcTemplate.queryForObject(String.format(sql2, org_id), Long.class)>0){
            list.add("instRating");
            list.add("instInvestmentAdvice");
            list.add("instRatingMap");
            list.add("instInvestmentAdviceMap");
       }
       return list;

    }

    private static Map<String, String> COLUMNMAP = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;
        {
            put("name", "债劵简称");
            put("tenor", "剩余期限");
            put("fairValue", "估值收益率");
            put("issBondRating", "主/债");
            put("pd", "主体量化风险等级");
            put("worstPd", "历史最高风险等级");
            put("impliedRating", "隐含评级");
            put("induName", "行业");
            put("induNameSw", "行业");
            put("poNegtive", "近1个月负面(舆情总数)");
            put("coupRate", "票面");
            put("convRatio", "折算率");
            put("dmBondType", "劵种");
            put("areaName1", "省");
            put("ownerType", "企业");
            put("bondRating", "债项评级");
            put("issRating", "主体评级");
            put("market", "交易市场");
            put("munInvest", "城投");
            put("pdTime", "主体量化风险等级财报时间");
            put("worstPdTime", "历史最高风险财报时间");
            put("ofrPrice", "报价");
            put("ofrVol", "报价数额");
            put("ofrOrderCnt", "报价笔数");
            put("ofrUpdateTime", "最后报价日期");
            put("price", "成交价");
            put("priceCount", "成交价数目");
            put("priceUpdateTime", "最后成交日期");
            put("code", "债劵代码");
            put("instRating", "内部评级");
            put("instInvestmentAdvice", "投资建议");
        }
    };

    private static Map<Integer, String> OWNERTYPES = new HashMap<Integer, String>() {
        private static final long serialVersionUID = 1L;
        {
            put(1, "央企");
            put(2, "国企");
            put(3, "民企");
            put(99, "其他");
        }
    };

    private static Map<Integer, String> MARKETS = new HashMap<Integer, String>() {
        private static final long serialVersionUID = 1L;
        {
            put(1, "银行间");
            put(2, "交易所");
            put(3, "跨市场");
            put(4, "可质押");
        }
    };

    private static Map<Integer, String> BONDTYPES = new HashMap<Integer, String>() {
        private static final long serialVersionUID = 1L;
        {
            put(1, "利率债");
            put(2, "信用债");
            put(3, "国债");
            put(4, "央票");
            put(5, "金融债");
            put(6, "地方债");
            put(7, "短融");
            put(8, "中票");
            put(9, "企业债");
            put(10, "存单");
            put(11, "ABS");
            put(12, "PPN");
            put(13, "公司债");
            put(99, "其他");
        }
    };

    public static List<String> BASICFIELD = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add("estYield");
            add("estDirtyPrice");
            add("estCleanPrice");
            add("optionYield");
            add("staticSpread");
            add("staticSpreadInduQuantile");
            add("macd");
            add("modd");
            add("convexity");
            add("convRatio");
            add("ofrPrice");
            add("price");
        }
    };

    /**
     * 请求参数的财务指标
     * 
     * @param BondIndicatorFilterReqs
     * @return
     */
    private Map<String, String> getResultFinance(List<BondIndicatorFilterReq> BondIndicatorFilterReqs) {

        if (BondIndicatorFilterReqs == null || BondIndicatorFilterReqs.size() < 1)
            return new HashMap<String, String>();

        Map<String, String> map = new HashMap<String, String>();

        BondIndicatorFilterReqs.stream().forEach(doc -> {
            map.put(doc.getField(), doc.getQuarter());
        });

        return map;

    }

    private static Map<String, Integer> COMPANYS = new HashMap<String, Integer>() {
        private static final long serialVersionUID = 1L;
        {
            put("％", 1);
            put("%", 1);
            put("万元", 2);
            put("天", 3);
            put("次", 4);
            put("个", 5);
            put("人", 6);
            put("万元/部", 7);
            put("万元/人", 8);
            put("万元/网点", 9);
            put("亿美元", 10);
        }
    };

    /**
     * 
     * isFinancialIndex:(是否是财务指标)
     * 
     * @param @param
     *            type
     * @param @return
     *            设定文件
     * @return Boolean DOM对象
     * @throws @since
     *             CodingExample Ver 1.1
     */
    private Boolean isFinancialIndex(Integer type) {
        if (type == 3 || type == 4 || type == 5) {
            return true;
        }
        return false;
    }

    /**
     * 债券高级筛选-->查看结果-->所有债劵代码
     * @param filter
     * @param page
     * @param limit
     * @param sort
     * @param userid
     * @return
     */
    public List<Long> filterBondUniCodeList(BondDmFilterReq filter, Integer page, Integer limit, String sort, Long userid){
    	
    	List<Long> bondUniCodeList = new ArrayList<Long>();
    	
    	 String[] sortPars = sort.split(":");
         String sortField = sortPars[0];

         String sortName = null;
         if (sortField.equalsIgnoreCase("tenor")) {
             sortName = "tenorDays";
         } else if (sortField.equalsIgnoreCase("pd")) {
             sortName = "pdSortRRs";
         } else if (sortField.equalsIgnoreCase("worstPd")) {
             sortName = "worstPdNum";
         } else {
             sortName = sortField;
         }

         if (filter.getBondIndicatorFilterReqs() != null && filter.getBondIndicatorFilterReqs().size() > 0) {
             for (int i = 0; i < filter.getBondIndicatorFilterReqs().size(); i++) {

                 if (BASICFIELD.contains(filter.getBondIndicatorFilterReqs().get(i).getField())) {
                     filter.getBondIndicatorFilterReqs().get(i).setQuarter(null);
                 }

             }
         }

         List<DBObject> listdb = new ArrayList<DBObject>();

         UICriteriaBuilder cbuild = new UICriteriaBuilder(filter);

         DBObject match = new BasicDBObject("$match",
                 cbuild.generate().getCriteriaObject() != null ? cbuild.generate().getCriteriaObject() : new BasicDBObject());

         listdb.add(match);

         if (filter.getBondIndicatorFilterReqs() != null && filter.getBondIndicatorFilterReqs().size() > 0) {

             BondIndicatorFilterReq req = filter.getBondIndicatorFilterReqs().get(filter.getBondIndicatorFilterReqs().size() - 1);

             if (!BASICFIELD.contains(req.getField()) && StringUtils.isEmpty(req.getQuarter())) {
                 req.setQuarter(filter.getQuarter());
             }

             BondIndicatorFilterReq bondIndicatorFilterReq2 = null;

             List<DBObject> list = new ArrayList<DBObject>();
             for (int j = 0; j < filter.getBondIndicatorFilterReqs().size(); j++) {
                 bondIndicatorFilterReq2 = filter.getBondIndicatorFilterReqs().get(j);
                 list.add(getElemMatch(bondIndicatorFilterReq2));
             }

             for (int i = 0; i < list.size(); i++) {
                 listdb.add(new BasicDBObject("$match", list.get(i)));
             }

         }

         // 需要显示的列
         DBObject projects = new BasicDBObject();
         DBObject project = new BasicDBObject();
         List<String> rows = getResultColumn(filter, 1 ,userid);
         if (rows != null && rows.size() > 0) {
             rows.stream().forEach(row -> {
                 project.put(row, 1);
             });
         }
         // 财务指标
         Map<String, String> finances = getResultFinance(filter.getBondIndicatorFilterReqs());
         if (finances != null && finances.size() > 0) {
             Iterator<String> keys = finances.keySet().iterator();
             while (keys.hasNext()) {
                 project.put(keys.next(), 1);
             }
             project.put("quarters.quarter", 1);
         }
         projects.put("$project", project);
         listdb.add(projects);
         DBObject sorts = new BasicDBObject();
         DBObject sortObject = new BasicDBObject();
//         if (sortField.equalsIgnoreCase("updateTime")) {
//        	 DBObject o1 = new BasicDBObject();
//        	 o1.put(sortName, sortPars[1].toLowerCase().startsWith("des") ?  -1 : 1);
//        	 o1.put("name", 1);
//        	 sorts.put("$sort", o1);
//         }else{
        	 sortObject.put(sortName, sortPars[1].toLowerCase().startsWith("des") ?  -1 : 1);
        	 sorts.put("$sort", sortObject);
//         }
         listdb.add(sorts);

         AggregationOutput output = bondMongoTemplate.getCollection("bond_detail_info").aggregate(listdb);
         Iterator<?> iterator = output.results().iterator();

         while (iterator.hasNext()) {
             BasicDBObject db = (BasicDBObject) iterator.next();
             bondUniCodeList.add(db.getLong("_id"));
         }
         
         LOG.info("bondUniCodeList:"+bondUniCodeList.size());
    	
    	return bondUniCodeList;
    	
    }

    /** 
     * 循环向上转型, 获取对象的 DeclaredMethod 
     * @param object : 子类对象 
     * @param methodName : 父类中的方法名 
     * @param parameterTypes : 父类中的方法参数类型 
     * @return 父类中的方法对象 
     */  
    public static Method getDeclaredMethod(Object object, String methodName, Class<?> ... parameterTypes){
        Method method = null ;
        for(Class<?> clazz = object.getClass() ; clazz != Object.class ; clazz = clazz.getSuperclass()) {
            try {   
                method = clazz.getDeclaredMethod(methodName, parameterTypes) ;   
                return method ;  
            } catch (Exception e) { 
            }
        }
        return null;
   }

    /**
     * 获取主体在给定时间前的发行债券列表数据[report-service]
     * @param userId
     * @param issuerId
     * @param year
     * @param quarter
     * @return
     */
	public BondDetailSummary getBondDetailSummary(Long userId, Long issuerId, Integer year, Integer quarter) {
		BondDetailSummary result = new BondDetailSummary();
		String summary = this.getBondStatisticByIssuerId(issuerId);
		result.setSummary(summary);
		//Date lastDate = SafeUtils.getLastDayOfQuarter(year, quarter);
		Query query = new Query(Criteria.where("comUniCode").is(issuerId).and("currStatus").is(1).and("issStaPar").is(1));
		query.fields().include("comUniCode").include("name").include("tenor").include("coupRate")
			.include("bondRating").include("latelyPayDate").include("newSize").include("createTime");
		List<BondDetailDoc> detailList = bondMongoTemplate.find(query, BondDetailDoc.class);
		result.setOnlineBondList(detailList);
		return result;
	}
	
	/**
	 * 获取发行人持有债券的summary
	 * @param issuerId
	 * @return
	 */
	public String getBondStatisticByIssuerId(Long issuerId) {
		Query query = new Query(Criteria.where("comUniCode").is(issuerId));
		query.fields().include("comUniCode").include("currStatus").include("issStaPar").include("newSize");
		List<BondDetailDoc> allBondDetailList = bondMongoTemplate.find(query, BondDetailDoc.class);
		// 先过滤掉跨市场的重复债券,NotCrossMarket->NCM
        List<Long> allBondUniCodeList = allBondDetailList.stream().map(BondDetailDoc::getBondUniCode)
                .collect(Collectors.toList());
        List<Long> allNcmBondUniCodeList = cashFlowChartDAO.getDistCrossMarketBondUniCodeList(allBondUniCodeList);
        allBondDetailList = allBondDetailList.stream()
                .filter(bond -> allNcmBondUniCodeList.contains(bond.getBondUniCode())).collect(Collectors.toList());
        // 计算总发行规模
        Double allBondNewSize = allBondDetailList.stream().mapToDouble(BondDetailDoc::getNewSize).sum();
        // 计算已到期债券规模
        Double allOfflineBondNewSize = allBondDetailList.stream()
                .filter(dtl -> dtl.getCurrStatus() != 1 || dtl.getIssStaPar() != 1)
                .mapToDouble(BondDetailDoc::getNewSize).sum();
        // 计算待付本息
		List<Long> onlineBondUinCodeList = allBondDetailList.stream()
				.filter(dtl -> dtl.getCurrStatus() == 1 && dtl.getIssStaPar() == 1)
				.map(BondDetailDoc::getBondUniCode).collect(Collectors.toList());
		String startDateStr = SafeUtils.getDateFromNowInYear(0);
		String endDateStr = SafeUtils.getDateFromNowInYear(1);
		Double allPendingNewSize = cashFlowChartDAO.getValidTotalPayAmount(onlineBondUinCodeList, startDateStr, endDateStr);
		String result = String.format("截止到%1$s为止，发行人发行规模达到%2$.2f亿元， 已到期债券规模共%3$.2f亿元，近一年内待付的本金利息总共%4$.2f亿元。",
				startDateStr, allBondNewSize, allOfflineBondNewSize, allPendingNewSize);
		return result;
	}

	/**
	 * findImpliedRatingList
	 * @param userId
	 * @param type
	 * @param induIds
	 * @return
	 */
	public Object findImpliedRatingList(Long userId, Integer type, Long[] induIds) {
		LOG.info("findImpliedRatingList with induinfo, userId:"+userId+",type:"+type+",induIds:"+induIds.toString());
		Object list = findImpliedRatingList(type);
		List<Long> bondUniCodes = findBondUniCodesWithInduIdAndUserId(userId, induIds);
		if (null != induIds && induIds.length > 0) {
			switch(SafeUtils.getInt(type)){
			case 1:
				List<BondImpliedRatingInfo> impliedRatingList = (List<BondImpliedRatingInfo>) list;
				impliedRatingList.stream().forEach(impliedRatingInfo -> {
					resetImpliedRatingColumns(bondUniCodes, impliedRatingInfo);				
				});
				
				list = impliedRatingList;
				break;
			case 2:
	            List<BondImpliedRatingCpInfo> impliedRatingCpList = (List<BondImpliedRatingCpInfo>) list;
	            impliedRatingCpList.stream().forEach(impliedRatingCpInfo -> {
					resetImpliedRatingCpColumns(bondUniCodes, impliedRatingCpInfo);			
	            });
	            
	            list = impliedRatingCpList;
				break;
			default:
				break;
			}
		}

		return list;
	}

	private List<Long> findBondUniCodesWithInduIdAndUserId(Long userId, Long[] induIds) {
		List<Long> bondUniCodes = null;
		if (null != induIds && induIds.length > 0) {
			Query query = new Query();
			query.fields().include("comUniCode");
			if (induAdapter.isInstitutionIndu(userId)) {
	        	Integer orgId = bondInstitutionInduAdapter.getInstitution(userId);
				String instFeild = Constants.INSTIRUTION_INDU_CODE+orgId;
				query.addCriteria(Criteria.where(instFeild).in(induIds));
			}else{
				if (induAdapter.isGicsInduClass(userId)) {
					query.addCriteria(Criteria.where("induId").in(induIds));
				}else{
					query.addCriteria(Criteria.where("induIdSw").in(induIds));
				}
			}
			List<BondDetailDoc> bondInfoList = bondMongoTemplate.find(query, BondDetailDoc.class);
			
			bondUniCodes = bondInfoList.stream().map(BondDetailDoc::getBondUniCode).collect(Collectors.toList());
		}
		return bondUniCodes;
	}

	private void resetImpliedRatingColumns(List<Long> comUniCodes, BondImpliedRatingInfo impliedRatingInfo) {
		resetRiskColumn(comUniCodes, impliedRatingInfo.getColumn1());
		resetRiskColumn(comUniCodes, impliedRatingInfo.getColumn2());
		resetRiskColumn(comUniCodes, impliedRatingInfo.getColumn3());
		resetRiskColumn(comUniCodes, impliedRatingInfo.getColumn4());
		resetRiskColumn(comUniCodes, impliedRatingInfo.getColumn5());
		resetRiskColumn(comUniCodes, impliedRatingInfo.getColumn6());
		resetRiskColumn(comUniCodes, impliedRatingInfo.getColumn7());
		resetRiskColumn(comUniCodes, impliedRatingInfo.getColumn8());
		resetRiskColumn(comUniCodes, impliedRatingInfo.getColumn9());
	}

	private void resetImpliedRatingCpColumns(List<Long> comUniCodes, BondImpliedRatingCpInfo impliedRatingCpInfo) {
		resetRiskColumn(comUniCodes, impliedRatingCpInfo.getColumn1());
		resetRiskColumn(comUniCodes, impliedRatingCpInfo.getColumn2());
		resetRiskColumn(comUniCodes, impliedRatingCpInfo.getColumn3());
		resetRiskColumn(comUniCodes, impliedRatingCpInfo.getColumn4());
		resetRiskColumn(comUniCodes, impliedRatingCpInfo.getColumn5());
		resetRiskColumn(comUniCodes, impliedRatingCpInfo.getColumn6());
		resetRiskColumn(comUniCodes, impliedRatingCpInfo.getColumn7());
		resetRiskColumn(comUniCodes, impliedRatingCpInfo.getColumn8());
		resetRiskColumn(comUniCodes, impliedRatingCpInfo.getColumn9());
	}
	
	/**
	 * resetRiskColumnss
	 * @param comUniCodes
	 * @param column
	 */
	private void resetRiskColumn(List<Long> comUniCodes, BondRiskColumnDoc column) {
		if (null != comUniCodes && !comUniCodes.isEmpty() && 
				null != column && !column.getComUniCodeList().isEmpty()) {
			Set<Long> comUniCodeSet = column.getComUniCodeList().parallelStream().filter(comUniCode -> comUniCodes.contains(comUniCode)).collect(Collectors.toSet());
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

	/**
	 * findBondComInfoListWithInduId
	 * @param userId
	 * @param condition
	 * @param induIds
	 * @param page
	 * @param limit
	 * @return
	 */
	public BondPageAdapter<BondComInfoDoc> findBondComInfoListWithInduId(Long userId, String condition, Long[] induIds, Integer page,
			Integer limit) {
        // 得到发行人集合
        Set<Long> list = getComInfoRiskDocByCondition(condition);
        PageRequest request = new PageRequest(page - 1, limit, new Sort(Direction.DESC, "pdTime"));
        Query query = queryConditionInUniCodes(list, request, null, null);
        Query totalCountQuery = queryConditionInUniCodes(list, null, null, null);
        if (null != induIds && induIds.length > 0) {
        	if (bondInstitutionInduAdapter.isInstitutionIndu(userId)) {
        		Integer orgId = bondInstitutionInduAdapter.getInstitution(userId);
				String instFeild = Constants.INSTIRUTION_INDU_CODE+orgId;
				query = queryConditionInUniCodes(list, request, instFeild, induIds);
				totalCountQuery = queryConditionInUniCodes(list, null, instFeild, induIds);
			}else{
				if (bondInstitutionInduAdapter.isGicsInduClass(userId)) {
					query = queryConditionInUniCodes(list, request, "induId", induIds);
					totalCountQuery = queryConditionInUniCodes(list, null, "induId", induIds);
				}else{
					query = queryConditionInUniCodes(list, request, "induIdSw", induIds);
					totalCountQuery = queryConditionInUniCodes(list, null, "induIdSw", induIds);
				}
			}
		}else{
    		query = queryConditionInUniCodes(list, request, null, null);
    		totalCountQuery = queryConditionInUniCodes(list, null, null, null);
		}
        List<BondComInfoDoc> results = bondMongoTemplate.find(query, BondComInfoDoc.class);
        
        return new BondPageAdapter<BondComInfoDoc>(results, request, bondMongoTemplate.count(totalCountQuery, BondComInfoDoc.class));
	}

	/**
	 * 
	 * @param userId
	 * @param condition
	 * @param induIds
	 * @param page
	 * @param limit
	 * @param type
	 * @return
	 */
	public BondPageAdapter<BondDetailDoc> findImpliedRatingBondsWithInduId(Long userId, String condition, Long[] induIds, Integer page,
			Integer limit, Integer type) {
        Object doc = getImpliedRatingByCondition(condition, type);

        if (doc == null)
            return new BondPageAdapter<BondDetailDoc>(new ArrayList<BondDetailDoc>(), null, 0);

        Set<Long> list = getBondRiskColumnByCondition(condition, doc);
        PageRequest request = new PageRequest(page - 1, limit, new Sort(Direction.DESC, "createTime"));
        Query query = queryConditionInUniCodes(list, request, null, null);
        Query totalCountQuery = queryConditionInUniCodes(list, null, null, null);
        if (null != induIds && induIds.length > 0) {
        	if (bondInstitutionInduAdapter.isInstitutionIndu(userId)) {
        		Integer orgId = bondInstitutionInduAdapter.getInstitution(userId);
				String instFeild = Constants.INSTIRUTION_INDU_CODE+orgId;
				query = queryConditionInUniCodes(list, request, instFeild, induIds);
				totalCountQuery = queryConditionInUniCodes(list, null, instFeild, induIds);
			}else{
				if (bondInstitutionInduAdapter.isGicsInduClass(userId)) {
					query = queryConditionInUniCodes(list, request, "induId", induIds);
					totalCountQuery = queryConditionInUniCodes(list, null, "induId", induIds);
				}else{
					query = queryConditionInUniCodes(list, request, "induIdSw", induIds);
					totalCountQuery = queryConditionInUniCodes(list, null, "induIdSw", induIds);
				}
			}
		}else{
    		query = queryConditionInUniCodes(list, request, null, null);
    		totalCountQuery = queryConditionInUniCodes(list, null, null, null);
		}
        
        List<BondDetailDoc> results = bondMongoTemplate.find(query, BondDetailDoc.class);
        
		return new BondPageAdapter<BondDetailDoc>(results, request, bondMongoTemplate.count(totalCountQuery, BondDetailDoc.class));
	}

	public Map<String, Object> getBondStatistic(Long comUniCode) {
		Query query = new Query(Criteria.where("comUniCode").is(comUniCode));
		query.fields().include("comUniCode").include("currStatus").include("issStaPar").include("newSize");
		List<BondDetailDoc> allBondDetailList = bondMongoTemplate.find(query, BondDetailDoc.class);
		// 先过滤掉跨市场的重复债券,NotCrossMarket->NCM
        List<Long> allBondUniCodeList = allBondDetailList.stream().map(BondDetailDoc::getBondUniCode)
                .collect(Collectors.toList());
        List<Long> allNcmBondUniCodeList = cashFlowChartDAO.getDistCrossMarketBondUniCodeList(allBondUniCodeList);
        allBondDetailList = allBondDetailList.stream()
                .filter(bond -> allNcmBondUniCodeList.contains(bond.getBondUniCode())).collect(Collectors.toList());
        // 计算总发行规模
        Double allBondNewSize = allBondDetailList.stream().mapToDouble(BondDetailDoc::getNewSize).sum();
        // 计算已到期债券规模
        Double allOfflineBondNewSize = allBondDetailList.stream()
                .filter(dtl -> dtl.getCurrStatus() != 1 || dtl.getIssStaPar() != 1)
                .mapToDouble(BondDetailDoc::getNewSize).sum();
        // 计算待付本息
		List<Long> onlineBondUinCodeList = allBondDetailList.stream()
				.filter(dtl -> dtl.getCurrStatus() == 1 && dtl.getIssStaPar() == 1)
				.map(BondDetailDoc::getBondUniCode).collect(Collectors.toList());
		String startDateStr = SafeUtils.getDateFromNowInYear(0);
		String endDateStr = SafeUtils.getDateFromNowInYear(1);
		Double allPendingNewSize = cashFlowChartDAO.getValidTotalPayAmount(onlineBondUinCodeList, startDateStr, endDateStr);
		Map<String, Object> result = new HashMap<>();
		result.put("allBondNewSize", allBondNewSize);
		result.put("allOfflineBondNewSize", allOfflineBondNewSize);
		result.put("allPendingNewSize", allPendingNewSize);
		return result;
	}
	
}
