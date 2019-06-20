/**
 * 
 */
package com.innodealing.bond.service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.innodealing.engine.jdbc.bond.IndicatorDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.innodealing.adapter.BondInstitutionInduAdapter;
import com.innodealing.bond.param.BondIssRating;
import com.innodealing.bond.param.BondIssRatingSummary;
import com.innodealing.bond.service.rrs.BondDocRuleFactory;
import com.innodealing.bond.service.rrs.StrategyRuleI;
import com.innodealing.bond.vo.summary.BondIssDMRatingSummaryDTO;
import com.innodealing.bond.vo.summary.BondIssDMRatingSummaryVO;
import com.innodealing.bond.vo.summary.BondIssExtRatingSummaryDTO;
import com.innodealing.bond.vo.summary.BondIssExtRatingSummaryVO;
import com.innodealing.bond.vo.summary.BondPersonalRatingDataVO;
import com.innodealing.bond.vo.summary.IssRatingScoreSummary;
import com.innodealing.consts.Constants;
import com.innodealing.domain.vo.bond.PdMappingVo;
import com.innodealing.engine.jdbc.bond.BondSearchExtDao;
import com.innodealing.engine.jdbc.bond.PdMappingDao;
import com.innodealing.engine.jpa.dm.BondComExtRepository;
import com.innodealing.engine.mongo.bond.BondComInfoRepository;
import com.innodealing.engine.mongo.bond.BondPdHistRepository;
import com.innodealing.engine.redis.RedisUtil;
import com.innodealing.exception.BusinessException;
import com.innodealing.exception.WarnException;
import com.innodealing.model.dm.bond.BondRatingDate;
import com.innodealing.model.dm.bond.ccxe.BondDAnnMain;
import com.innodealing.model.mongo.dm.BondComInfoDoc;
import com.innodealing.model.mongo.dm.BondPdHistDoc;
import com.innodealing.model.mongo.dm.BondPdHistRec;
import com.innodealing.model.mongo.dm.BondPdRankDoc;
import com.innodealing.model.mongo.dm.BondPdsHist;
import com.innodealing.model.mongo.dm.bond.detail.analyse.IssFinDatesDoc;
import com.innodealing.uilogic.UIAdapter;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Administrator
 *
 */
@Service
public class BondComInfoService {

	private static final Logger LOG = LoggerFactory.getLogger(BondComInfoService.class);

	@Autowired
	BondPdHistRepository pdHistRepository;

	@Autowired
	MongoOperations mongoOperations;

	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected JdbcTemplate asbrsPerResultJdbcTemplate;

	@Autowired
	protected BondComInfoRepository comInfoRepo;

	@Autowired
	protected BondInduService bondInduService;

	@Autowired
	protected BondAnalysisService bondAnalysisService;
	
	@Autowired
	protected BondComExtRepository bondComExtRep;
	
	@Autowired
	protected PdMappingDao pdMappingDao;
	
	@Autowired
	protected BondPdHistRepository bondPdHistRepository;

	@Autowired
	RedisUtil redisUtil;

	@Autowired
    BondSearchExtDao bondSearchExtDao;

	@Autowired
	BondRatingPoolService bondRatingPoolService;
	
    @Autowired
    private BondInstitutionInduAdapter induAdapter;
    
	@Autowired
	private IndicatorDao indicatorDAO;

	// 金融企业债
	private static final List<String> FINA_COMP_TYPE_LIST = Arrays.asList("bank", "secu", "insu");

	/**
	 * 信用债活跃成交一览
	 */
	@SuppressWarnings("unused")
	public List<BondComInfoDoc> findByNamePrefix(String prefix, Integer limit) {

		PageRequest request = new PageRequest(0, limit, new Sort(Sort.Direction.ASC, "comChiName"));

		Query query = new Query();
		// TODO performance optimization by better TECH
		Criteria c = Criteria.where("comChiName").regex("^" + prefix);
		query.addCriteria(c).with(request);
		query.fields().exclude("amaComId");
		List<BondComInfoDoc> list = mongoOperations.find(query, BondComInfoDoc.class);
		return mongoOperations.find(query, BondComInfoDoc.class);
	}

	/**
	 * 信用债活跃成交一览
	 */
	public List<BondComInfoDoc> search(String queryString, Integer limit) {

		PageRequest request = new PageRequest(0, limit, new Sort(Sort.Direction.ASC, "comChiName"));

		// TODO performance optimization by better TECH
		Query query = new Query();
		query.addCriteria(Criteria.where("comChiName").regex("^.*" + queryString + ".*$")).with(request);
		query.fields().exclude("amaComId");
		return mongoOperations.find(query, BondComInfoDoc.class);
	}

	public PageImpl<BondPdHistRec> findPdHistByComUniCode(Long comUniCode, Integer page, Integer limit) {

		if (!pdHistRepository.exists(comUniCode))
			return new PageImpl<BondPdHistRec>(new ArrayList<BondPdHistRec>(), new PageRequest(page, limit, null), 0);

		BondPdHistDoc doc = pdHistRepository.findOne(comUniCode);
		// pdExceptionRoute(doc);
		List<BondPdHistRec> pds = doc.getPd();
		// 转为季度
		for (BondPdHistRec bondPdHistRec : pds) {
			if (bondPdHistRec.getDate() != null) {
				bondPdHistRec.setQuarter(
						SafeUtils.convertFromYearQnToYearDesc(SafeUtils.getQuarter(bondPdHistRec.getDate())));
			}
		}
		return new PageImpl<BondPdHistRec>(
				pds.subList(Math.min(page * limit, pds.size()), Math.min((page + 1) * limit, pds.size())),
				new PageRequest(page, limit, null), pds.size());
	}

	/**
	 * 违约概率风险缺失提示路由
	 * 
	 * @param issId
	 */
	public String pdExceptionRoute(Long issId) {
		// return "数据完整.";
		BondPdHistDoc bondPd = pdHistRepository.findOne(issId);
		if (bondPd == null) {
			throw new WarnException("该发行主体未披露财报，故无法计算得出风险等级。");
		}
		List<BondPdHistRec> pds = bondPd.getPd();
		if (pds.size() == 0) {
			throw new WarnException("该发行主体未披露财报，故无法计算得出风险等级。");
		}
		// 当前最新季度
		String currentDate = SafeUtils.getFormatDate(new Date(), SafeUtils.DATE_FORMAT);
		String currentQuarter = SafeUtils.getQuarter(currentDate, 2)[1];
		// 上年同期季度
		String yearAgoQuarter = SafeUtils.getQuarter(currentDate, 7)[5];
		// 上年第四季度
		int currentYear = SafeUtils.getInt(currentQuarter.substring(0, 4));
		String yearAgoFourQuarter = SafeUtils.getQuarter((currentYear - 1) + "-12-01", 1)[0];

		String pdDate = SafeUtils.getFormatDate(pds.get(0).getDate(), SafeUtils.DATE_FORMAT);
		if (SafeUtils.getQuarter(pdDate, 1)[0].equals(currentQuarter)) {
			return "";
		}
		IssFinDatesDoc issFinDate = mongoOperations
				.findOne(new Query(Criteria.where("issId").is(bondPd.getComUniCode())), IssFinDatesDoc.class);
		// 财报季度
		Collection<String> quarter = null;
		// //最近6季度指标
		// Collection<String> dateList = null;
		if (issFinDate == null) {
			throw new WarnException("该发行主体未披露财报，故无法计算得出风险等级。");
		} else {
			quarter = issFinDate.getFinDates();
		}
		// 主体历史违约map集合映射
		Map<String, BondPdHistRec> mapPd = new HashMap<>();
		for (BondPdHistRec pd : pds) {
			String date = SafeUtils.getFormatDate(pd.getDate(), SafeUtils.DATE_FORMAT);
			mapPd.put(date, pd);
		}
		// 主体历史财报集合映射
		Map<String, String> mapFinDate = new HashMap<>();

		for (String date : quarter) {
			mapFinDate.put(SafeUtils.getQuarter(date, 1)[0], date);

		}

		// 1、 若有历史风险等级，但当季主体量化风险等级缺失
		if (mapPd.get(currentQuarter) == null) {
			// 1、1若有当季财报，但是却无等级结果
			if (mapFinDate.get(currentQuarter) != null) {
				// 上年同期季报
				String yearAgoQuarterIn = mapFinDate.get(yearAgoQuarter);
				// 上年季报年报
				String yearAgoFourQuarterIn = mapFinDate.get(yearAgoFourQuarter);
				// 1、1、1是否有上年年报（即第四季度财报）或上年同期季报
				if (yearAgoQuarterIn == null || yearAgoFourQuarterIn == null) {
					throw new WarnException("该发行主体上年年报或上年同期季报为空，因此财务指标无法年化，故无法计算得出风险等级。");
				} else {// 1、1、2若上年年报和上年同期季报都存在
					throw new WarnException("该发行主体财报缺失重点指标，故无法计算得出量化风险等级。");
				}
				// 1、2若无当季财报，则根据时间来提示不同内容
			} else {
				// Calendar.getInstance().get(Calendar.YEAR);
				// 当年第一季度财报
				String currentQ1 = mapFinDate.get(SafeUtils.getQuarter(currentYear + "-03-01", 1)[0]);
				// 当年第二季度财报
				String currentQ2 = mapFinDate.get(SafeUtils.getQuarter(currentYear + "-06-01", 1)[0]);
				// 当年第三季度财报
				String currentQ3 = mapFinDate.get(SafeUtils.getQuarter(currentYear + "-09-01", 1)[0]);
				// 年报
				String currentQ4 = mapFinDate.get(SafeUtils.getQuarter(currentYear + "-012-01", 1)[0]);
				// 无一季度财报：当年4月30日前
				if (currentQ1 == null) {
					throw new WarnException("该发行主体未披露一季度财报，故未能计算当季度风险量化等级。");
				}
				// 无二季度财报：当年8月31日前
				if (currentQ2 == null) {
					throw new WarnException("该发行主体未披露二季度财报，故未能计算当季度风险量化等级。");
				}
				// 无三季度财报：当年10月31日前
				if (currentQ3 == null) {
					throw new WarnException("该发行主体未披露三季度财报，故未能计算当季度风险量化等级。");
				}
				// 无年报（即第四季度财报）：第二年4月30日前
				if (currentQ4 == null) {
					throw new WarnException("该发行主体未披露年报，故未能计算年度风险量化等级。");
				}
				return "数据完整.";
			}
		} else {
			throw new WarnException("该发行主体未披露财报，故无法计算得出风险等级。");
		}

	}

	public PageImpl<BondIssRating> findIssRatingHist(Long issuerId, Integer page, Integer limit) {

		String sql = String
				.format("SELECT com_uni_code, iss_cred_level, rate_writ_date, bond_ccxe.d_pub_org_info_r.CHI_SHORT_NAME AS ORG_CHI_NAME,\r\n"
						+ " IF(RATE_POINT IS NULL or RATE_POINT = '', false, true) as has_rate_point , \r\n"
						+ " IF(CCE_DISADVT IS NULL or CCE_DISADVT = '', false, true) as has_disadvt \r\n"
						+ ",(CASE rate_pros_par WHEN  1 THEN '正面'  \n\t" + "WHEN  2 THEN '稳定'  \n\t"
						+ "WHEN 3 THEN '观望'  \n\t" + "WHEN 4 THEN '负面'  \n\t" + "ELSE '' END ) rateProsPar \n\t"
						+ "				FROM bond_ccxe.D_BOND_ISS_CRED_CHAN  \r\n"
						+ "				LEFT JOIN bond_ccxe.d_pub_org_info_r ON bond_ccxe.D_BOND_ISS_CRED_CHAN.org_uni_code = bond_ccxe.d_pub_org_info_r.ORG_UNI_CODE  \r\n"
						+ "	WHERE com_uni_code = " + issuerId + "\r\n"
						+ "				AND bond_ccxe.D_BOND_ISS_CRED_CHAN.isvalid = 1 and com_type_par=1 \r\n"
						+ "				ORDER BY rate_writ_date DESC ");

		// Integer fromIndex = page*limit;
		// sql += String.format("limit %1$d %2$d", fromIndex, limit);

		List<BondIssRating> issRatings = (List<BondIssRating>) jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<BondIssRating>(BondIssRating.class));
		if (issRatings == null) {
			LOG.error("internal error");
			return null;
		}
		;

		if (issRatings.isEmpty()) {
			LOG.info("task has beend done successfully");
			return null;
		}

		return new PageImpl<BondIssRating>(
				issRatings.subList(Math.min(page * limit, issRatings.size()),
						Math.min((page + 1) * limit, issRatings.size())),
				new PageRequest(page, limit, null), issRatings.size());
	}

	public BondComInfoDoc findComInfoById(Long issuerId,long userid) {
		
		BondComInfoDoc doc = mongoOperations.findById(issuerId, BondComInfoDoc.class);
		
		if(doc!=null)
			doc.setBondCreditRatingGroup(bondRatingPoolService.getRatingGroupByComUniCode(userid, issuerId));
		
		return doc;
	}

	/**
	 * 搜索发行人
	 */
	public Page<BondComInfoDoc> searchComInfo(String queryString, Integer limit , Long userId) {

		PageRequest request = new PageRequest(0, limit, new Sort(Sort.Direction.ASC, "comChiName"));

		List<Long> list = new ArrayList<Long>();
		
        if(!StringUtils.isEmpty(queryString)) {
            queryString = queryString==null ? "" : queryString.trim().toLowerCase();
            queryString = StringUtils.filter(queryString);//替换特殊符号
            list = bondSearchExtDao.findComUniCodesByQuerykey(queryString);
        }
        
        long count = 0;
        List<BondComInfoDoc> bondComInfoList = new ArrayList<BondComInfoDoc>();
        if(null != list && !list.isEmpty()){
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("_id").in(list));
            
            query.addCriteria(criteria).with(request);
            query.fields().exclude("amaComId");
            bondComInfoList = (List<BondComInfoDoc>) induAdapter.conv(mongoOperations.find(query, BondComInfoDoc.class),userId);
            count = mongoOperations.count(query, BondComInfoDoc.class);
        }
        if(bondComInfoList==null || bondComInfoList.isEmpty()){
            return new PageImpl<>(bondComInfoList);
        }
        
        
		Page<BondComInfoDoc> cominfoPage = new PageImpl<BondComInfoDoc>(
		        bondComInfoList, request,
		        count);

		return cominfoPage;
	}

	/**
	 * 获取外部评级概要信息
	 * 
	 * @param issuerId
	 * @return
	 */
	public BondIssExtRatingSummaryVO findBondIssExtRatingSummary(Long bondId, Long issuerId) {
		BondIssExtRatingSummaryDTO extRatingSummary = new BondIssExtRatingSummaryDTO();

		// 1.取mongo中缓存的信息
		BondComInfoDoc doc = mongoOperations.findById(issuerId, BondComInfoDoc.class);
		if (null != doc) {
			extRatingSummary.setIssuer(doc.getComChiName());
			extRatingSummary.setInduName(doc.getInduName());
			extRatingSummary.setExtWorstRatingTime(doc.getWorstRatingTime());
			extRatingSummary.setExtWorstRatingResult(doc.getWorstRating());
		}

		// 2.外部评级，当前评级信息
		try {
			List<BondIssRatingSummary> latestIssRatingSummaryList = getLatestIssRatingSummaryList(issuerId);
			if (null != latestIssRatingSummaryList) {
				BondIssRatingSummary latestSummary = latestIssRatingSummaryList.get(0);
				extRatingSummary.setExtLatestRatingDate(latestSummary.getRateWritDate());
				if (latestIssRatingSummaryList.size() == 2) {
					extRatingSummary.setExtLatestRatingDiff(latestIssRatingSummaryList.get(1).getIssCredLevelPar()
							- latestSummary.getIssCredLevelPar());
				}
				extRatingSummary.setExtLatestRatingForecast(latestSummary.getParName());
				extRatingSummary.setExtLatestRatingOrga(latestSummary.getChiShortName());
				extRatingSummary.setExtLatestRatingResult(latestSummary.getIssCredLevel());
				extRatingSummary.setExtLatestCceDisadvt(latestSummary.getCceDisadvt());
				extRatingSummary.setComRateProsParDate(latestSummary.getComRateProsParDate());
				extRatingSummary.setComRateProsParOrga(latestSummary.getComRateProsParOrga());
			}

			if (bondId == null)
				return getSummaryModel(bondId, issuerId, extRatingSummary);
			// 债劵最新评级跟展望
			BondIssExtRatingSummaryDTO bond = bondRating(bondId);
			if (bond == null)
				return getSummaryModel(bondId, issuerId, extRatingSummary);
			extRatingSummary.setBondShortName(bond.getBondShortName());
			extRatingSummary.setBondRating(bond.getBondRating());
			extRatingSummary.setBondRatingDate(bond.getBondRatingDate());
			extRatingSummary.setBondRatingOrga(bond.getBondRatingOrga());
			extRatingSummary.setBondRateProsPar(UIAdapter.rateProsParMaps().get(bond.getBondRateProsPar()));
			extRatingSummary.setBondRateProsParDate(bond.getBondRateProsParDate());
			extRatingSummary.setBondRateProsParOrga(bond.getBondRateProsParOrga());
		} catch (Exception ex) {
			LOG.error("findBondIssRatingSummary exception: " + ex.getMessage());
		}

		return getSummaryModel(bondId, issuerId, extRatingSummary);
	}

	private BondIssExtRatingSummaryVO getSummaryModel(Long bondId, Long issuerId,
			BondIssExtRatingSummaryDTO extRatingSummary) {
		BondIssExtRatingSummaryVO result = new BondIssExtRatingSummaryVO();
		List<String> strs = new ArrayList<String>();
		String str1 = "", str2 = "", str3 = "", str4 = "";
		if (bondId == null) { // 发行人
			if (isNotNull(extRatingSummary.getExtLatestRatingOrga())
					&& extRatingSummary.getExtLatestRatingDate() != null && isNotNull(extRatingSummary.getIssuer())
					&& isNotNull(extRatingSummary.getExtLatestRatingResult())) {
				str1 = extRatingSummary.getExtLatestRatingOrga() + "于"
						+ dateFormat(extRatingSummary.getExtLatestRatingDate()) + "评定" + extRatingSummary.getIssuer()
						+ "，最新外部主体评级为" + extRatingSummary.getExtLatestRatingResult() + "("
						+ dateFormat(extRatingSummary.getExtLatestRatingDate()) + ")。";
			}
			if (extRatingSummary.getExtLatestRatingDiff() != null)
				str1 += "评级" + rateProsPar(extRatingSummary.getExtLatestRatingDiff(), true);
			if (isNotNull(extRatingSummary.getExtWorstRatingResult())
					&& extRatingSummary.getExtWorstRatingTime() != null) {
				str1 += "历史最差一次评级为" + extRatingSummary.getExtWorstRatingResult() + "("
						+ dateStringFormat(extRatingSummary.getExtWorstRatingTime().substring(0,
								extRatingSummary.getExtWorstRatingTime().length() - 2))
						+ ")。";
			}
			if (isNotNull(extRatingSummary.getComRateProsParOrga()) && extRatingSummary.getComRateProsParDate() != null
					&& isNotNull(extRatingSummary.getExtLatestRatingForecast())) {
				str2 = extRatingSummary.getComRateProsParOrga() + "于"
						+ dateFormat(extRatingSummary.getComRateProsParDate()) + "调整该发行人主体展望为"
						+ extRatingSummary.getExtLatestRatingForecast() + "。";
			}
		} else if (bondId != null && issuerId != null) { // 债劵
			if (isNotNull(extRatingSummary.getBondRatingOrga()) && extRatingSummary.getBondRatingDate() != null
					&& isNotNull(extRatingSummary.getBondShortName()) && isNotNull(extRatingSummary.getBondRating())) {
				str1 = extRatingSummary.getBondRatingOrga() + "于" + dateFormat(extRatingSummary.getBondRatingDate())
						+ "评定" + extRatingSummary.getBondShortName() + "最新债项评级为" + extRatingSummary.getBondRating()
						+ "。";
			}
			if (isNotNull(extRatingSummary.getIssuer()) && isNotNull(extRatingSummary.getExtLatestRatingResult())
					&& extRatingSummary.getExtLatestRatingDate() != null) {
				str2 = "该债劵发行人为" + extRatingSummary.getIssuer() + "，最新外部主体评级为"
						+ extRatingSummary.getExtLatestRatingResult() + "("
						+ dateFormat(extRatingSummary.getExtLatestRatingDate()) + ")。";
			}
			if (extRatingSummary.getExtLatestRatingDiff() != null)
				str2 += "评级" + rateProsPar(extRatingSummary.getExtLatestRatingDiff(), true);
			if (isNotNull(extRatingSummary.getExtWorstRatingResult())
					&& extRatingSummary.getExtWorstRatingTime() != null) {
				str2 += "历史最差一次评级为" + extRatingSummary.getExtWorstRatingResult() + "("
						+ dateStringFormat(extRatingSummary.getExtWorstRatingTime().substring(0,
								extRatingSummary.getExtWorstRatingTime().length() - 2))
						+ ")。";
			}
			if (isNotNull(extRatingSummary.getBondRateProsParOrga())
					&& extRatingSummary.getBondRateProsParDate() != null
					&& isNotNull(extRatingSummary.getBondRateProsPar())) {
				str3 = extRatingSummary.getBondRateProsParOrga() + "于"
						+ dateFormat(extRatingSummary.getBondRateProsParDate()) + "调整该债劵展望为"
						+ extRatingSummary.getBondRateProsPar() + "。";
			}
			if (isNotNull(extRatingSummary.getComRateProsParOrga()) && extRatingSummary.getComRateProsParDate() != null
					&& isNotNull(extRatingSummary.getExtLatestRatingForecast())) {
				str4 = extRatingSummary.getComRateProsParOrga() + "于"
						+ dateFormat(extRatingSummary.getComRateProsParDate()) + "调整该发行人主体展望为"
						+ extRatingSummary.getExtLatestRatingForecast() + "。";
			}
		}
		add(str1, strs);
		add(str2, strs);
		add(str3, strs);
		add(str4, strs);
		result.setSummaryList(strs);
		if ("负面".equals(extRatingSummary.getExtLatestRatingForecast()))
			result.setExtLatestCceDisadvt(extRatingSummary.getExtLatestCceDisadvt());

		return result;
	}

	private String dateStringFormat(String str) {
		if (StringUtils.isEmpty(str))
			return null;
		return SafeUtils.convertDateToString(SafeUtils.parseDate(str, "yyyy-MM-dd"), "yyyy/MM/dd");
	}

	private Boolean isNotNull(Object obj) {
		if (!StringUtils.isEmpty(obj))
			return true;
		return false;
	}

	private List<String> add(String str, List<String> strs) {
		if (!StringUtils.isEmpty(str))
			strs.add(str);
		return strs;
	}

	private String rateProsPar(Integer extLatestRatingDiff, Boolean bool) {
		if (extLatestRatingDiff == null || !bool)
			return "";
		if (extLatestRatingDiff > 0)
			return "上调" + extLatestRatingDiff + "级。";
		if (extLatestRatingDiff < 0)
			return "下调" + Math.abs(extLatestRatingDiff) + "级。";
		if (extLatestRatingDiff == 0)
			return "维持不变。";
		return "";
	}

	private String dateFormat(Date d) {
		if (d == null)
			return null;
		return SafeUtils.convertDateToString(d, "yyyy/MM/dd");
	}

	@Autowired
	BondDocRuleFactory pdReasonRuleFactory;

	/**
	 * 债劵最新评级跟展望
	 * 
	 * @param bondId
	 * @return
	 */
	private BondIssExtRatingSummaryDTO bondRating(Long bondId) {
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT IFNULL(p.bondShortName,w.bondShortName) AS bondShortName,p.bondRatingOrga,\n\t");
		sql.append(
				"p.bondRatingDate,p.bondRating,w.bondRateProsParOrga,w.bondRateProsParDate,w.bondRateProsPar FROM (\n\t");
		sql.append("SELECT t.*,b.CHI_SHORT_NAME AS bondRatingOrga FROM (\n\t");
		sql.append(
				"SELECT BOND_SHORT_NAME AS bondShortName,ORG_UNI_CODE,RATE_WRIT_DATE AS bondRatingDate,BOND_CRED_LEVEL AS bondRating,bond_uni_code\n\t");
		sql.append("FROM bond_ccxe.d_bond_cred_chan WHERE IS_NEW_RATE = 1 AND bond_uni_code = ? \n\t");
		sql.append(
				"ORDER BY RATE_WRIT_DATE DESC ) t LEFT JOIN bond_ccxe.d_pub_org_info_r b ON t.ORG_UNI_CODE = b.ORG_UNI_CODE ) p\n\t");
		sql.append("LEFT JOIN \n\t");
		sql.append("(\n\t");
		sql.append("SELECT t.*,b.CHI_SHORT_NAME AS bondRateProsParOrga FROM (\n\t");
		sql.append(
				"SELECT BOND_SHORT_NAME AS bondShortName,ORG_UNI_CODE,RATE_WRIT_DATE AS bondRateProsParDate,RATE_PROS_PAR AS bondRateProsPar,bond_uni_code\n\t");
		sql.append(
				"FROM bond_ccxe.d_bond_cred_chan WHERE IS_NEW_RATE = 1 AND rate_pros_par IS NOT NULL AND bond_uni_code = ? \n\t");
		sql.append(
				"ORDER BY RATE_WRIT_DATE DESC ) t LEFT JOIN bond_ccxe.d_pub_org_info_r b ON t.ORG_UNI_CODE = b.ORG_UNI_CODE \n\t");
		sql.append(") w ON p.bond_uni_code = w.bond_uni_code\n\t");
		Long[] bondIds = { bondId, bondId };
		int[] bondIndexs = { 1, 2 };
		List<BondIssExtRatingSummaryDTO> list = jdbcTemplate.query(sql.toString(), bondIds, bondIndexs,
				new BeanPropertyRowMapper<BondIssExtRatingSummaryDTO>(BondIssExtRatingSummaryDTO.class));
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 发行人外部评级数据
	 */
	private List<BondIssRatingSummary> getLatestIssRatingSummaryList(Long issuerId) {
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT t.CHI_SHORT_NAME,t.RATE_WRIT_DATE,  \n\t");
		sql.append("t.ISS_CRED_LEVEL,t.ISS_CRED_LEVEL_PAR,t.CCE_DISADVT,p.RATE_WRIT_DATE AS comRateProsParDate \n\t");
		sql.append(",p.PAR_NAME,p.CHI_SHORT_NAME AS comRateProsParOrga \n\t");
		sql.append("FROM (	SELECT poir.CHI_SHORT_NAME, icc.RATE_WRIT_DATE, icc.ISS_CRED_LEVEL, \n\t");
		sql.append("icc.ISS_CRED_LEVEL_PAR, pp.PAR_NAME, icc.CCE_DISADVT,icc.COM_UNI_CODE \n\t");
		sql.append("FROM bond_ccxe.d_bond_iss_cred_chan AS icc \n\t");
		sql.append("LEFT JOIN bond_ccxe.d_pub_org_info_r AS poir ON poir.ORG_UNI_CODE=icc.ORG_UNI_CODE \n\t");
		sql.append(
				"LEFT JOIN bond_ccxe.pub_par AS pp ON pp.PAR_SYS_CODE='3110' AND pp.PAR_CODE=icc.RATE_PROS_PAR \n\t");
		sql.append("WHERE icc.COM_UNI_CODE=? AND icc.ISVALID=1 AND  icc.com_type_par=1 \n\t");
		sql.append("ORDER BY icc.RATE_WRIT_DATE DESC LIMIT 2 ) t LEFT JOIN \n\t");
		sql.append("( \n\t");
		sql.append("SELECT poir.CHI_SHORT_NAME, icc.RATE_WRIT_DATE, pp.PAR_NAME,icc.COM_UNI_CODE \n\t");
		sql.append("FROM bond_ccxe.d_bond_iss_cred_chan AS icc \n\t");
		sql.append("LEFT JOIN bond_ccxe.d_pub_org_info_r AS poir ON poir.ORG_UNI_CODE=icc.ORG_UNI_CODE \n\t");
		sql.append(
				"INNER JOIN bond_ccxe.pub_par AS pp ON pp.PAR_SYS_CODE='3110' AND pp.PAR_CODE=icc.RATE_PROS_PAR \n\t");
		sql.append("WHERE icc.COM_UNI_CODE=? AND icc.ISVALID=1 AND icc.com_type_par=1 \n\t");
		sql.append("ORDER BY icc.RATE_WRIT_DATE DESC LIMIT 1 \n\t");
		sql.append(") p ON t.COM_UNI_CODE = p.COM_UNI_CODE \n\t");
		Long[] bondIds = { issuerId, issuerId };
		int[] bondIndexs = { 1, 2 };
		List<BondIssRatingSummary> list = jdbcTemplate.query(sql.toString(), bondIds, bondIndexs,
				new BeanPropertyRowMapper<BondIssRatingSummary>(BondIssRatingSummary.class));
		return list;
	}

	public IssRatingScoreSummary findIssRatingScoreSummary(Long userid, Long issuerId, Long year, Long quarter) {
		IssRatingScoreSummary summary = new IssRatingScoreSummary();
		StrategyRuleI rule = pdReasonRuleFactory.createStategy(issuerId, userid, year, quarter);
		if (!rule.loadIssRatingScoreSummary(summary)) {
			return new IssRatingScoreSummary();
		}
		return summary;
	}

	public IssRatingScoreSummary getDMScoreComparisonResult(Long userId, Long issuerId, Long firstDate, Long secondDate) {
		IssRatingScoreSummary summary = new IssRatingScoreSummary();
		StrategyRuleI rule = pdReasonRuleFactory.createComparisonStrategy(issuerId, userId, firstDate, secondDate);
		if (!rule.loadIssRatingScoreSummary(summary)) {
			return new IssRatingScoreSummary();
		}
		return summary;
	}

	/**
	 * 获取主体量化评级概要信息
	 * 
	 * @param issuerId
	 * @param quarter
	 * @param year
	 * @return
	 * @throws Exception
	 */
	public BondIssDMRatingSummaryVO findBondIssDMRatingSummary(Long userid, Long issuerId, Long year, Long quarter) {
		BondIssDMRatingSummaryVO dmRatingSummary = new BondIssDMRatingSummaryVO();
		if (null == year ||  null == quarter) {
			BondComInfoDoc doc = mongoOperations.findById(issuerId, BondComInfoDoc.class);
			if(doc==null || StringUtils.isEmpty(doc.getPdTime()))
				return null;
			String pdTime = doc.getPdTime();
			String[] strs = pdTime.split("/");
			if(strs.length==2){
				year = Long.parseLong(strs[0]);
				quarter = getQuarter(strs[1]);
			}
		}
		if(year!=null && quarter!=null)
			dmRatingSummary.setRatingSummary(getBondComPdByQuarter(userid, issuerId, year, quarter, null));
		
		StrategyRuleI rule = pdReasonRuleFactory.createStategy(issuerId, userid, year, quarter);
		if (!rule.loadDMRatingSummary(dmRatingSummary)) {
			// 取消前端展示
			dmRatingSummary.setRatioContentList(null);
			dmRatingSummary.setRatioTitle(null);
			// throw new BusinessException("数据不完整导致无法生成主体量化评级的概要信息");
		}

		return dmRatingSummary;
	}
	
    public BondIssDMRatingSummaryVO findPrivateIssDMRatingSummary(Long taskId) {
        BondIssDMRatingSummaryVO dmRatingSummary = new BondIssDMRatingSummaryVO();
        StrategyRuleI rule = pdReasonRuleFactory.createPriStategy(taskId);
        if (rule==null || !rule.loadDMRatingSummary(dmRatingSummary)) {
            dmRatingSummary.setRatioContentList(null);
            dmRatingSummary.setRatioTitle(null);
        }
        return dmRatingSummary;
    }
    
	private Long getQuarter(String quarter){
		if(StringUtils.isEmpty(quarter))
			return null;
		if("一季报".equals(quarter)){
			return 1L;
		}else if("中报".equals(quarter)){
			return 2L;
		}else if("三季报".equals(quarter)){
			return 3L;
		}else if("年报".equals(quarter)){
			return 4L;
		}
		return null;
	}
	
	public List<BondRatingDate> getBondRateDate(Long issuerId) {
		List<BondRatingDate> ratingDateList = new ArrayList<BondRatingDate>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT q.year,GROUP_CONCAT(q.quan_month) AS MONTH FROM ( \n\t");
		sql.append("SELECT bond.year,bond.quan_month  FROM  /*amaresun*/ dmdb.dm_bond bond,dmdb.t_bond_com_ext com\n\t");
		sql.append("WHERE bond.comp_id = com.ama_com_id AND bond.year<=? AND bond.year>=? AND com_uni_code = ?\n\t");
		sql.append("AND CONCAT(bond.year,bond.quan_month) != (\n\t");
		sql.append("SELECT CONCAT(bond.year,bond.quan_month) AS DATE FROM \n\t");
		sql.append(" /*amaresun*/ dmdb.dm_bond bond,dmdb.t_bond_com_ext com\n\t");
		sql.append("WHERE bond.comp_id = com.ama_com_id AND bond.year<=? AND bond.year>=? AND com_uni_code = ?\n\t");
		sql.append("ORDER BY bond.year ,bond.quan_month  LIMIT 1 )\n\t");
		sql.append("ORDER BY bond.year,bond.quan_month \n\t");
		sql.append(") q \n\t");
		sql.append("GROUP BY q.year\n\t");

		Calendar nowDate = Calendar.getInstance();
		// 得到年份
		Integer nowYear = nowDate.get(Calendar.YEAR);

		int[] indexs = { 1, 2, 3, 4, 5, 6 };
		Integer[] values = { nowYear, nowYear - 10, issuerId.intValue(), nowYear, nowYear - 10, issuerId.intValue() };

		ratingDateList = jdbcTemplate.query(sql.toString(), values, indexs,
				new BeanPropertyRowMapper<BondRatingDate>(BondRatingDate.class));
		// 数据处理
		if (ratingDateList == null || ratingDateList.size() < 1)
			return null;

		for (BondRatingDate vo : ratingDateList) {
			vo.setMonths(vo.getMonth().split(","));
		}

		return ratingDateList;
	}

	/**
	 * 根据年份季度获取发行人主体评级概要信息
	 * 
	 * @param userId
	 * @param issuerId
	 * @param year yyyy
	 * @param quarter 1/2/3/4
	 * @return
	 */
	private String getBondComPdByQuarter(Long userId, Long issuerId, Long year, Long quarter, BondPersonalRatingDataVO perComInfo) {
		Date tempDate = SafeUtils.getLastDayOfQuarter(Integer.valueOf(year.toString()),
				Integer.valueOf(quarter.toString()));
		String lastDayOfQuarter = SafeUtils.convertDateToString(tempDate, SafeUtils.DATE_FORMAT4);
		// 获取发行人所属机构中，当前主体对应的行业
		Map<String, String> userInduMap = this.getIssuerInduIdAndNameMap(userId, issuerId);
		String userIssuerInduName = userInduMap.get("name");
		// 获取发行人主体量化评级信息
		StringBuilder result = new StringBuilder();
		Long finalQuar = quarter * 3;
		String sql = "SELECT par.id AS ratingPar, db.Rating, par.pd, com_chi_name, '%1$s' AS induName"
				+ " FROM dmdb.t_bond_com_ext AS ext"
				+ " LEFT JOIN dmdb.dm_bond AS db ON db.Comp_ID=ext.ama_com_id"
				+ " INNER JOIN dmdb.t_bond_pd_par AS par ON par.rating=db.Rating"
				+ " WHERE ext.com_uni_code=%2$d && (db.`year`<%3$d || (db.`year`=%3$d && db.quan_month<=%4$d))"
				+ " ORDER BY `year` DESC, quan_month DESC LIMIT 2;";
		String finalSql = String.format(sql, userIssuerInduName, issuerId, year, finalQuar);
		List<BondIssDMRatingSummaryDTO> dmRatingDTOList = (List<BondIssDMRatingSummaryDTO>) jdbcTemplate.query(finalSql,
				new BeanPropertyRowMapper<BondIssDMRatingSummaryDTO>(BondIssDMRatingSummaryDTO.class));
		if (null == dmRatingDTOList || dmRatingDTOList.size() == 0) {
			LOG.error("findComInfoByIdAndQuar: NO comInfoList can be found");
			return null;
		}
		BondIssDMRatingSummaryDTO firstDTO = dmRatingDTOList.get(0);
		firstDTO.setRatingDate(lastDayOfQuarter);
		if (dmRatingDTOList.size() > 1) {
			BondIssDMRatingSummaryDTO secondDTO = dmRatingDTOList.get(1);
			// 大于0表示评级降低
			firstDTO.setParDiff(firstDTO.getRatingPar() - secondDTO.getRatingPar());
			// 大于0表示风险提高
			firstDTO.setPdDiff(firstDTO.getPd().subtract(secondDTO.getPd()));
		}
		Long parDiff = firstDTO.getParDiff();
		BigDecimal pdDiff = firstDTO.getPdDiff();
		
		String parDiffStr = "";
		if (null != parDiff) {
			parDiffStr = parDiff > 0 ? String.format("，较上期下调%1$d级", parDiff) :
				(parDiff < 0 ? String.format("，较上期上调%1$d级", (-1)*parDiff) : "，较上期维持不变");
		}
		
		String pdDiffStr = "";
		if (null != pdDiff) {
			pdDiffStr = pdDiff.compareTo(BigDecimal.ZERO) > 0 ? String.format("，较上期提高%1$s%%", rvZeroAndDot(pdDiff.toString())) :
				(pdDiff.compareTo(BigDecimal.ZERO) < 0 ? String.format("，较上期降低%1$s%%", rvZeroAndDot(pdDiff.toString())) : "，较上期维持不变");
		}
		String firstPart = String.format("DM量化评分于%1$s评定为%2$s%3$s，现量化评分对应的违约概率为%4$s%%%5$s。", 
				firstDTO.getRatingDate(), firstDTO.getRating(), parDiffStr, rvZeroAndDot(firstDTO.getPd().toString()), pdDiffStr);
		result.append(firstPart);

		// 获取发行人行业位置信息
		List<BondPdRankDoc> allPdRankList = bondAnalysisService.getPdRankListByQuar(userId, issuerId, year, quarter,
				null, false);
		List<BondPdRankDoc> induPdRankList = bondAnalysisService.getPdRankListByQuar(userId, issuerId, year, quarter,
				null, true);
		if (null == allPdRankList || allPdRankList.size() == 0 || null == induPdRankList
				|| induPdRankList.size() == 0) {
			LOG.error("findComInfoByIdAndQuar: cannot get pdRankList from bondAnalysisService to sort by rrs");
			return null;
		}
		String allRankDesc = "";
		String induRankDesc = "";
		Long count = 1L;
		for (BondPdRankDoc pdRank : allPdRankList) {
			if (issuerId.equals(pdRank.getIssuerId())) {
				allRankDesc = getPdTopDesc(count, (long) allPdRankList.size());
				break;
			}
			count++;
		}
		count = 0L;
		for (BondPdRankDoc pdRank : induPdRankList) {
			if (issuerId.equals(pdRank.getIssuerId())) {
				induRankDesc = getPdTopDesc(count, (long) induPdRankList.size());
				break;
			}
			count++;
		}
		String secondPart = String.format("全市场共%1$s家发行主体，%2$s行业共%3$d家发行主体，%4$s于全市场位置为%5$s，于%2$s行业位置为%6$s。",
				allPdRankList.size(), firstDTO.getInduName(), induPdRankList.size(), firstDTO.getComChiName(),
				allRankDesc, induRankDesc);
		result.append(secondPart);
		
		
		return result.toString();
	}

	private String rvZeroAndDot(String s) {
		if (s.isEmpty()) {
			return null;
		}
		if (s.indexOf(".") > 0) {
			s = s.replaceAll("0+?$", "");// 去掉多余的0
			s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
		}
		return s;
	}

	/**
	 * 获取发行人主体评级的排名概要信息
	 * 
	 * @param userid
	 * @param issuerId
	 * @return
	 */
	private String getBondComPd(Long userid, Long issuerId) {
		BondComInfoDoc doc = mongoOperations.findById(issuerId, BondComInfoDoc.class);
		if (null == doc)
			return null;
		StringBuffer str = new StringBuffer();
		Map<String, String> map = new HashMap<String, String>();
		String pd = !StringUtils.isEmpty(doc.getPd()) ? doc.getPd().substring(0, doc.getPd().indexOf("(")) : null;
		Long pdDiff = doc.getPdDiff();
		map.put("pd", pd);
		map.put("pdTime", doc.getPdTime());
		map.put("pdDiff", this.rateProsPar(pdDiff.intValue(), isPdDiff(doc)));
		String pdNum = !StringUtils.isEmpty(doc.getPd())
				? doc.getPd().substring(doc.getPd().indexOf("(") + 1, doc.getPd().indexOf("%")) : null;
		map.put("pdNum", pdNum);
		// 上期PD
		BigDecimal PreviousPdNum = UIAdapter.convAmaRating2Pd(getPreviousPd(pd, pdDiff));
		BigDecimal pdNumBig = new BigDecimal(pdNum);
		BigDecimal resultPdNum = pdNumBig.subtract(PreviousPdNum).abs();
		str.append(getComPdString(map) + getPreviousPdString(pdDiff, resultPdNum));

		Query query = new Query();
		query.fields().include("_id");
		query.with(new Sort(Sort.Direction.ASC, "pdSortRRs"));

		// 全市场总发行人数量
		List<BondPdRankDoc> pdRankAll = mongoOperations.find(query, BondPdRankDoc.class);
		// 当前发行人所在行业总发行人数量
		String induField = bondInduService.isGicsInduClass(userid) ? "induId" : "induIdSw";
		query.addCriteria(Criteria.where(induField)
				.in(bondInduService.isGicsInduClass(userid) ? doc.getInduId() : doc.getInduIdSw()));
		List<BondPdRankDoc> pdRankInduAll = mongoOperations.find(query, BondPdRankDoc.class);
		String induName = bondInduService.isGicsInduClass(userid) ? doc.getInduName() : doc.getInduNameSw();

		str.append("全市场共" + pdRankAll.size() + "家发行主体，");
		str.append(induName + "行业共" + pdRankInduAll.size() + "家发行主体，");
		str.append(doc.getComChiName() + "于全市场位置为"
				+ getPdTopDesc(getPdTop(pdRankAll, doc.getComUniCode()), (long) pdRankAll.size()) + "，");
		str.append("于" + induName + "行业位置为"
				+ getPdTopDesc(getPdTop(pdRankInduAll, doc.getComUniCode()), (long) pdRankInduAll.size()) + "。");
		return str.toString();
	}

	/**
	 * 违约概率是否有较上期数据
	 * 
	 * @param comInfoDoc
	 * @return
	 */
	private Boolean isPdDiff(BondComInfoDoc comInfoDoc) {
		int count = 0;
		BondPdHistDoc pdHist = pdHistRepository.findOne(comInfoDoc.getComUniCode());
		if (pdHist != null) {
			List<BondPdHistRec> hists = pdHist.getPd();
			if (hists != null) {
				for (int i = 0; i < hists.size(); ++i) {
					if (hists.get(i).getPdNum() == null
							|| hists.get(i).getPd().equals(Constants.FINACE_REPORT_UNPUBLISHED))
						continue;
					count++;
				}
			}
		}
		if (count == 2)
			return true;
		return false;
	}

	/**
	 * 排名
	 * 
	 * @param list
	 * @param comUniCode
	 * @return
	 */
	private Long getPdTop(List<BondPdRankDoc> list, Long comUniCode) {
		if (list == null || list.isEmpty())
			return 0L;
		Long sum = 0L;
		Boolean bool = false;
		for (BondPdRankDoc doc : list) {
			sum += 1;
			if (comUniCode.equals(doc.getIssuerId())) {
				bool = true;
				break;
			}
		}
		return bool ? sum : 0L;
	}

	private String getPdTopDesc(Long curr, Long total) {
		double myTop = (double) curr / (double) total;
		myTop = (new BigDecimal(myTop).setScale(5, BigDecimal.ROUND_DOWN)).floatValue();
		if (myTop <= 0.1) {
			return "前10%";
		} else if (myTop > 0.1 && myTop <= 0.3) {
			return "前30%";
		} else if (myTop > 0.3 && myTop <= 0.5) {
			return "前50%";
		} else if (myTop > 0.5 && myTop <= 0.7) {
			return "前70%";
		} else if (myTop > 0.7 && myTop <= 0.9) {
			return "后30%";
		} else {
			return "后10%";
		}
	}

	private String getComPdString(Map<String, String> map) {
		StringBuffer str = new StringBuffer();
		if (map.get("pdTime") != null && map.get("pd") != null)
			str.append("DM主体量化风险等级于" + dateFormat(BondPdHistRec.quarterToDate(map.get("pdTime"))) + "评定为"
					+ map.get("pd") + "，");
		if (!StringUtils.isEmpty(map.get("pdDiff")))
			str.append("较上期" + map.get("pdDiff"));
		if (map.get("pdNum") != null)
			str.append("现主体量化风险等级对应的概率为" + map.get("pdNum") + "%");
		return str.toString();
	}

	/**
	 * 上期PD
	 * 
	 * @param pd
	 * @param pdDiff
	 * @return
	 */
	private String getPreviousPd(String pd, Long pdDiff) {
		if (pd == null || pdDiff == null)
			return null;
		Integer pdNum = UIAdapter.pdNumMaping.get(pd);
		if (pdDiff == 0)
			return pd;
		return UIAdapter.numPdMaping.get(pdNum + pdDiff.intValue());
	}

	private String getPreviousPdString(Long pdDiff, BigDecimal resultPdNum) {
		if (pdDiff == null || resultPdNum == null)
			return null;

		if (pdDiff == 0) {
			return "，较上期维持不变。";
		} else if (pdDiff > 0) {
			return "，较上期降低" + resultPdNum + "%。";
		} else {
			return "，较上期提升" + resultPdNum + "%。";
		}
	}

	public static void main(String[] args) {
		// String date = SafeUtils.getFormatDate(new Date(),
		// SafeUtils.DATE_FORMAT);
		// System.out.println(Arrays.toString(SafeUtils.getQuarter(date, 6)));

		String str = "2010-04-14 00:00:00";
		System.out.println(SafeUtils.convertDateToString(SafeUtils.parseDate(str, "yyyy-MM-dd"), "yyyy/MM/dd"));

	}

	/**
	 * 根据issuerId以及选定的季度，返回发行人主体的基本信息
	 * 
	 * @param issuerId 发行人主体编号
	 * @param year 选定的年份
	 * @param quarter 选定的季度
	 * @return
	 */
	public BondComInfoDoc findComInfoByIdAndQuar(Long issuerId, Long year, Long quarter) {
		Long finalQuar = quarter * 3;
		String sql = "SELECT ext.com_chi_name, CONCAT(asab.year,'/Q', round(asab.quan_month/3)) as pdTime,"
				+ " asab.Rating AS pd FROM dmdb.t_bond_com_ext AS ext"
				+ " LEFT JOIN dmdb.dm_bond as asab ON asab.Comp_ID=ext.ama_com_id"
				+ " WHERE ext.com_uni_code=%1$d"
				+ " AND ((asab.`year`=%2$d AND asab.quan_month<=%3$d) OR asab.`year`<%2$d)"
				+ " ORDER BY `year` DESC, quan_month DESC LIMIT 1;";
		String finalSql = String.format(sql, issuerId, year, finalQuar);
		List<BondComInfoDoc> comInfoList = (List<BondComInfoDoc>) jdbcTemplate.query(finalSql,
				new BeanPropertyRowMapper<BondComInfoDoc>(BondComInfoDoc.class));
		if (null == comInfoList || comInfoList.size() == 0) {
			LOG.error("findComInfoByIdAndQuar: comInfoList cannot be found");
			throw new BusinessException("找不到对应的发行人基本信息");
		}
		Query induQuery = new Query(Criteria.where("_id").is(issuerId));
		induQuery.fields().include("_id").include("induId").include("induName")
			.include("induIdSw").include("induNameSw").include("institutionInduMap");
		BondComInfoDoc induComInfoDoc = mongoOperations.findOne(induQuery, BondComInfoDoc.class);
		if (induComInfoDoc == null) {
			LOG.error("findComInfoByIdAndQuar: induComInfoDoc cannot be found");
			throw new BusinessException("找不到对应的发行人doc基本信息");
		}
		BondComInfoDoc result = comInfoList.get(0);
		result.setInduId(induComInfoDoc.getInduId());
		result.setInduName(induComInfoDoc.getInduName());
		result.setInduIdSw(induComInfoDoc.getInduIdSw());
		result.setInduNameSw(induComInfoDoc.getInduNameSw());
		result.setInstitutionInduMap(induComInfoDoc.getInstitutionInduMap());
		String modelName = indicatorDAO.getModelNameByCompId(issuerId);
		// 代替 1金融企业/2非金融企业
		result.setComAttrPar(FINA_COMP_TYPE_LIST.contains(modelName) ? 1 : 2);
		return result;
	}
	
	/**
	 * 获取用户所属机构中，发行人对应的行业id和name映射
	 * @param issuerId
	 * @param userId
	 * @return
	 */
	private Map<String, String> getIssuerInduIdAndNameMap(Long userId, Long issuerId) {
		Query induQuery = new Query(Criteria.where("_id").is(issuerId));
		induQuery.fields().include("_id").include("induId").include("induName")
			.include("induIdSw").include("induNameSw").include("institutionInduMap");
		BondComInfoDoc induComInfoDoc = mongoOperations.findOne(induQuery, BondComInfoDoc.class);
		if (induComInfoDoc == null) {
			LOG.error("findComInfoByIdAndQuar: induComInfoDoc cannot be found");
			throw new BusinessException("找不到对应的发行人doc基本信息");
		}
		induComInfoDoc = induAdapter.conv(induComInfoDoc, userId);
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("id", induComInfoDoc.getInduId().toString());
		resultMap.put("name", induComInfoDoc.getInduName());
		return resultMap;
	}

	public PageImpl<BondIssRating> findIssRatingHistByIdAndQuar(Long issuerId, Long year, Long quarter) {
		Date tempDate = SafeUtils.getLastDayOfQuarter(Integer.valueOf(year.toString()),
				Integer.valueOf(quarter.toString()));
		String lastDayOfQuarter = SafeUtils.convertDateToString(tempDate, SafeUtils.DATE_FORMAT);
		String sql = "SELECT poir.CHI_SHORT_NAME AS ORG_CHI_NAME, icc.RATE_WRIT_DATE, icc.ISS_CRED_LEVEL"
				+ " FROM bond_ccxe.d_bond_iss_cred_chan AS icc"
				+ " LEFT JOIN dmdb.t_bond_com_ext AS ext ON ext.com_uni_code=icc.COM_UNI_CODE"
				+ " LEFT JOIN bond_ccxe.d_pub_org_info_r AS poir ON poir.ORG_UNI_CODE=icc.ORG_UNI_CODE"
				+ " WHERE icc.COM_UNI_CODE=%1$d AND DATEDIFF('%2$s',icc.RATE_WRIT_DATE)>0"
				+ " AND icc.ISVALID=1 AND icc.com_type_par=1 "
				+ " ORDER BY icc.RATE_WRIT_DATE DESC LIMIT 5;";
		String finalSql = String.format(sql, issuerId, lastDayOfQuarter);
		List<BondIssRating> issRatingList = (List<BondIssRating>) jdbcTemplate.query(finalSql,
				new BeanPropertyRowMapper<BondIssRating>(BondIssRating.class));
		if (issRatingList == null || issRatingList.isEmpty()) {
			LOG.error(
					"findIssRatingHistByIdAndQuar: cannot find issRatingList with issuerId[{}], year[{year}], quarter[{quarter}]",
					issuerId, year, quarter);
			throw new BusinessException("没有对应选定时间的外部排名列表数据");
		}
		;
		return new PageImpl<BondIssRating>(issRatingList, new PageRequest(1, 5, null), issRatingList.size());
	}

	public BondIssExtRatingSummaryVO findBondIssExtRatingSummaryByQuarter(Long issuerId, Long year, Long quarter) {
		BondIssExtRatingSummaryVO result = new BondIssExtRatingSummaryVO();
		Date tempDate = SafeUtils.getLastDayOfQuarter(Integer.valueOf(year.toString()),
				Integer.valueOf(quarter.toString()));
		String lastDayOfQuarter = SafeUtils.convertDateToString(tempDate, SafeUtils.DATE_FORMAT);
		BondIssExtRatingSummaryDTO latestSummaryDTO = getLatestSummaryDTO(issuerId, lastDayOfQuarter);
		if (null == latestSummaryDTO) {
			LOG.info(
					"findBondIssExtRatingSummaryByQuarter: cannot get latestSummaryDTO with issuerId[%s], year[%d], quarter[%s]",
					issuerId, year, quarter);
			throw new BusinessException("没有对应时间的外部评级概要信息数据");
		}
		BondIssExtRatingSummaryDTO worstSummaryDTO = getWorstSummaryDTO(issuerId, lastDayOfQuarter);
		if (null == worstSummaryDTO) {
			LOG.info(
					"findBondIssExtRatingSummaryByQuarter: cannot get worstSummaryDTO with issuerId[%s], year[%d], quarter[%s]",
					issuerId, year, quarter);
			throw new BusinessException("没有对应时间的外部评级概要信息数据");
		}

		String summaryTpl = "%1$s于%2$tY/%2$tm/%2$te评定%3$s外部主体评级为%4$s。%5$s%3$s历史最差一次评级为%6$s(%7$tY/%7$tm/%7$te)。";
		String summaryContent = String.format(summaryTpl, latestSummaryDTO.getOrgChiName(),
				latestSummaryDTO.getRateWritDate(), latestSummaryDTO.getComChiName(),
				latestSummaryDTO.getIssCredLevel(), StringUtils.isEmpty(latestSummaryDTO.getPdDiff())?"":latestSummaryDTO.getPdDiff(), worstSummaryDTO.getIssCredLevel(),
				worstSummaryDTO.getRateWritDate(), latestSummaryDTO.getParName());
		result.getSummaryList().add(summaryContent);
		if (StringUtils.isNotBlank(latestSummaryDTO.getParName())) {
			summaryContent = String.format("%1$s于%2$tY/%2$tm/%2$te调整该发行人主体展望为%3$s。", latestSummaryDTO.getOrgChiName(),
					latestSummaryDTO.getRateWritDate(), latestSummaryDTO.getParName());
			result.getSummaryList().add(summaryContent);
		}
		result.setShortDesc(org.apache.commons.lang.StringUtils.join(result.getSummaryList().toArray(), ""));
		result.setExtLatestCceDisadvt(latestSummaryDTO.getCceDisadvt());
		return result;
	}

	/**
	 * 获取外部评级-历史最差评级信息
	 * 
	 * @param issuerId
	 * @param lastDayOfQuarter
	 * @return
	 */
	private BondIssExtRatingSummaryDTO getWorstSummaryDTO(Long issuerId, String lastDayOfQuarter) {
		String worstSql = "SELECT icc.RATE_WRIT_DATE, icc.ISS_CRED_LEVEL"
				+ " FROM bond_ccxe.d_bond_iss_cred_chan AS icc"
				+ " LEFT JOIN bond_ccxe.pub_par AS pp ON pp.PAR_SYS_CODE=3110 AND pp.PAR_CODE=icc.RATE_PROS_PAR AND pp.ISVALID=1"
				+ " WHERE icc.COM_UNI_CODE=%1$d AND icc.ISVALID=1 AND DATEDIFF('%2$s',RATE_WRIT_DATE)>0"
				+ " AND icc.ISS_CRED_LEVEL_PAR="
				+ " (SELECT MAX(ISS_CRED_LEVEL_PAR) FROM bond_ccxe.d_bond_iss_cred_chan"
				+ " WHERE COM_UNI_CODE=%1$d AND DATEDIFF('%2$s',RATE_WRIT_DATE)>0 AND ISVALID=1 AND com_type_par=1 AND ISS_CRED_LEVEL_PAR != 35 LIMIT 1)"
				+ " ORDER BY RATE_WRIT_DATE DESC LIMIT 1;";
		String assWorstSql = String.format(worstSql, issuerId, lastDayOfQuarter);
		List<BondIssExtRatingSummaryDTO> worstSummaryDTOList = (List<BondIssExtRatingSummaryDTO>) jdbcTemplate.query(
				assWorstSql, new BeanPropertyRowMapper<BondIssExtRatingSummaryDTO>(BondIssExtRatingSummaryDTO.class));
		if (worstSummaryDTOList == null) {
			LOG.error("getWorstSummaryDTO: internal error");
			return null;
		}
		;
		if (worstSummaryDTOList.isEmpty()) {
			LOG.info("getWorstSummaryDTO: task has beend done successfully");
			return null;
		}
		return worstSummaryDTOList.get(0);
	}

	/**
	 * 获取外部评级-最新评级信息
	 * 
	 * @param issuerId
	 * @param lastDayOfQuarter
	 * @return
	 */
	private BondIssExtRatingSummaryDTO getLatestSummaryDTO(Long issuerId, String lastDayOfQuarter) {
		// 首先获得两条外部评级来计算评级变化
		String latestSql = "SELECT ext.com_chi_name, poir.CHI_SHORT_NAME AS ORG_CHI_NAME, icc.RATE_WRIT_DATE,"
				+ " icc.ISS_CRED_LEVEL, icc.ISS_CRED_LEVEL_PAR, pp.PAR_NAME,"
				+ " CASE icc.RATE_PROS_PAR WHEN 4 THEN icc.CCE_DISADVT ELSE '' END AS cceDisadvt,"
				+ " CASE icc.RATE_PROS_PAR WHEN 4 THEN 1 ELSE 0 END AS hasDisadvt"
				+ " FROM bond_ccxe.d_bond_iss_cred_chan AS icc"
				+ " LEFT JOIN dmdb.t_bond_com_ext AS ext ON ext.com_uni_code=icc.COM_UNI_CODE"
				+ " LEFT JOIN bond_ccxe.d_pub_org_info_r AS poir ON poir.ORG_UNI_CODE=icc.ORG_UNI_CODE"
				+ " LEFT JOIN bond_ccxe.pub_par AS pp ON pp.PAR_SYS_CODE=3110 AND pp.PAR_CODE=icc.RATE_PROS_PAR AND pp.ISVALID=1"
				+ " WHERE icc.com_type_par=1 AND icc.ISVALID=1 AND icc.COM_UNI_CODE=%1$d AND DATEDIFF('%2$s',icc.RATE_WRIT_DATE) > 0"
				+ " ORDER BY icc.RATE_WRIT_DATE DESC LIMIT 2;";
		String assLatestSql = String.format(latestSql, issuerId, lastDayOfQuarter);
		List<BondIssExtRatingSummaryDTO> latestSummaryDTOList = (List<BondIssExtRatingSummaryDTO>) jdbcTemplate.query(
				assLatestSql, new BeanPropertyRowMapper<BondIssExtRatingSummaryDTO>(BondIssExtRatingSummaryDTO.class));
		if (latestSummaryDTOList == null || latestSummaryDTOList.isEmpty()) {
			LOG.error("getLatestSummaryDTO: NO latestSummaryDTOList can be found");
			return null;
		}
		;
		BondIssExtRatingSummaryDTO extSummaryDTO = latestSummaryDTOList.get(0);
		// 如果为空，则拼接的时候不显示
		if (2 == latestSummaryDTOList.size()) {
			int diff = extSummaryDTO.getIssCredLevelPar() - latestSummaryDTOList.get(1).getIssCredLevelPar();
			if (diff > 0) {
				extSummaryDTO.setPdDiff(String.format("评级下调%d级。", diff));
			} else if (diff < 0) {
				extSummaryDTO.setPdDiff(String.format("评级上调%d级。", Math.abs(diff)));
			} else {
				extSummaryDTO.setPdDiff("评级维持不变。");
			}
		}
		return extSummaryDTO;
	}

	public PageImpl<BondPdHistRec> findPdHistByComUniCodeAndQuar(Long comUniCode, Integer page, Integer limit,
			Long year, Long quarter) {
		Date lastDateOfQuarter = SafeUtils.getLastDayOfQuarter(Integer.valueOf(year.toString()),
				Integer.valueOf(quarter.toString()));
		if (!pdHistRepository.exists(comUniCode))
			return new PageImpl<BondPdHistRec>(new ArrayList<BondPdHistRec>(), new PageRequest(page, limit, null), 0);

		BondPdHistDoc doc = pdHistRepository.findOne(comUniCode);
		// pdExceptionRoute(doc);
		List<BondPdHistRec> pdList = new ArrayList<BondPdHistRec>();
		// 按季度过滤
		doc.getPd().forEach(item -> {
			if (lastDateOfQuarter.getTime() > item.getDate().getTime())
				pdList.add(item);
		});
		// 转为季度
		for (BondPdHistRec bondPdHistRec : pdList) {
			if (bondPdHistRec.getDate() != null) {
				bondPdHistRec.setQuarter(
						SafeUtils.convertFromYearQnToYearDesc(SafeUtils.getQuarter(bondPdHistRec.getDate())));
			}
		}
		if (pdList.isEmpty()) {
			throw new BusinessException("没有对应时间的主体量化评级列表数据");
		}
		return new PageImpl<BondPdHistRec>(
				pdList.subList(Math.min(page * limit, pdList.size()), Math.min((page + 1) * limit, pdList.size())),
				new PageRequest(page, limit, null), pdList.size());
	}

	public Map<String, Object> findPdHist(Long comUniCode, Long userId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (!pdHistRepository.exists(comUniCode))
			return resultMap;

		BondPdHistDoc doc = pdHistRepository.findOne(comUniCode);
		List<BondPdsHist> pds = new ArrayList<BondPdsHist>();

		//行业对用的风险等级分位值
		// 当前发行人所在行业总发行人数量
		Map<String, InduPdInfoVo> induPdInfoMap = getInduPdInfoMap(doc, userId);
		
		BondPdsHist hist = null;
		// 转为季度
		for (BondPdHistRec bondPdHistRec : doc.getPd()) {
			hist = new BondPdsHist();
			if(bondPdHistRec.getPdNum() != null){
				hist.setPdNum(21 - bondPdHistRec.getPdNum());
			}
			hist.setDate(bondPdHistRec.getDate());
			if (bondPdHistRec.getDate() != null) {
				hist.setQuarter(SafeUtils.convertFromYearQnToYearDesc(SafeUtils.getQuarter(bondPdHistRec.getDate())));
			}
			if (bondPdHistRec.getPd().indexOf("(") == -1) {
				hist.setPd(null);
			} else {
				hist.setPd(bondPdHistRec.getPd().substring(0, bondPdHistRec.getPd().indexOf("(")));
			}
			//行业分位值信息
			InduPdInfoVo induPdInfoVo = induPdInfoMap.get(hist.getQuarter());
			if(induPdInfoVo != null){
				hist.setIndu1(21 - induPdInfoVo.getIndu1());
				hist.setIndu10(21 - induPdInfoVo.getIndu10());
				hist.setIndu50(21 - induPdInfoVo.getIndu50());
				hist.setIndu90(21 - induPdInfoVo.getIndu90());
				hist.setIndu100(21 - induPdInfoVo.getIndu100());
				hist.setInduName(induPdInfoVo.getInduName());
			}
			pds.add(hist);
		}

		List<BondPdsHist> sortList = pds;
		BondPdHistRecComparator desc = new BondPdHistRecComparator(true);
		Collections.sort(sortList, desc);
		String minRating = sortList.get(0).getPd();

		BondPdHistRecComparator asc = new BondPdHistRecComparator(false);
		Collections.sort(sortList, asc);
		String maxRating = sortList.get(0).getPd();

		Collections.sort(pds, new BondPdHistRecDateComparator());
		resultMap.put("minRating", minRating);
		resultMap.put("maxRating", maxRating);
		resultMap.put("induName", bondInduService.getInduIdByUser(userId));
		resultMap.put("list", pds);

		return resultMap;
	}
	
	/**
	 * 根据行业id查询该行业所有主体的行业信息(只查询comUniCode，其他字段部查询查询)
	 * @return
	 */
	public List<BondComInfoDoc> findAllInduInfo(Long induId){
		
		Query query = new Query();
		query.fields()
			.include("comUniCode");
		return mongoOperations.find(query, BondComInfoDoc.class);
	}

	/**
	 * 获取私人财报的主体量化等级概要信息
	 * 
	 * @param taskId 私人财报的编号
	 * @param year 选定的年份[yyyy]
	 * @param quarter 选定的季度[1/2/3/4]
	 * @param perComInfo 选定的季度[1/2/3/4]
	 * @return
	 */
	public BondIssDMRatingSummaryVO findBondIssDMPerRatingSummary(Long taskId, Long year, Long quarter,
			BondPersonalRatingDataVO perComInfo) {
		BondIssDMRatingSummaryVO result = new BondIssDMRatingSummaryVO();
		try {
			String compName = perComInfo.getCompName();
			StringBuilder ratingSummary = new StringBuilder();
			
			StringBuilder querySql = new StringBuilder();
			querySql.append("SELECT v.rating, v.year, v.quan_month, v.induid4, v.induname4 FROM v_dm_personal_rating_data_sheet v ");
			querySql.append("WHERE v.visible=1 AND v.custid=").append(taskId).append(" AND v.taskid=").append(taskId).append(" ");
			querySql.append("ORDER BY v.`year` DESC,v.quan_month desc LIMIT 2");
			
			List<Map<String, Object>> perRatingRes = asbrsPerResultJdbcTemplate.queryForList(querySql.toString());
			if (null != perRatingRes && perRatingRes.size() > 0) {
				// 评级信息
				Map<String, Object> firstRat = perRatingRes.get(0);
				String rating = SafeUtils.getString(firstRat.get("rating"));
				Integer quanMonth = SafeUtils.getInt(firstRat.get("quan_month"));
				String ratingDate = SafeUtils.getString(firstRat.get("year"))+"/"+SafeUtils.getQuarterDescByMonth(quanMonth);
				String firstPart = String.format("DM主体量化风险等级于%1$s评定为%2$s", ratingDate, rating);
				ratingSummary.append(firstPart);
				// 等级变化+对应的违约概率
				PdMappingVo pdMapping = pdMappingDao.getPdMappingByRating(rating);
				String pdDiffStr = "";
				if (perRatingRes.size() > 1) {
					Map<String, Object> secondRat = perRatingRes.get(1);
					String raingSec = SafeUtils.getString(secondRat.get("rating"));
					PdMappingVo pdMappingSec = pdMappingDao.getPdMappingByRating(raingSec);
					
					BigDecimal pdDiff = pdMappingSec.getPd().subtract(pdMapping.getPd());
					
					if (null != pdDiff) {
						pdDiffStr = pdDiff.doubleValue() > 0 ? String.format("，较上期下调%1$.2f%%", pdDiff) :
							(pdDiff.doubleValue() < 0 ? String.format("，较上期上调%1$.2f%%", pdDiff.abs()) : "，较上期维持不变");
					}
					ratingSummary.append(pdDiffStr);
				}
				ratingSummary.append("，现风险等级对应的违约概率为").append(pdMapping.getPd().doubleValue()).append("%。");
				
				// 获取发行人行业位置信息
				String secondPart = "";
				boolean isNeedMongoDocList = false;
				List<BondPdRankDoc> allPdRankList = bondAnalysisService.getPerPdRankList(perComInfo, false, isNeedMongoDocList);
				List<BondPdRankDoc> induPdRankList = bondAnalysisService.getPerPdRankList(perComInfo, true, isNeedMongoDocList);
				
				// 全市场
				int allRankCount = 1;
				Long count = 1L;
				String allRankDesc = "前10%";
				if (null != allPdRankList && !allPdRankList.isEmpty()) {
					allRankCount = allPdRankList.size();
					for (BondPdRankDoc pdRank : allPdRankList) {
						if (compName.equals(pdRank.getIssuer())) {
							allRankDesc = getPdTopDesc(count, (long) (allPdRankList.size()));
							break;
						}
						count++;
					}
				}
				// 当前行业
				int induRankCount = 1;
				String induRankDesc = "前10%";
				count = 1L;
				if (null != induPdRankList && !induPdRankList.isEmpty()) {
					induRankCount = induPdRankList.size();
					for (BondPdRankDoc pdRank : induPdRankList) {
						if (compName.equals(pdRank.getIssuer())) {
							induRankDesc = getPdTopDesc(count, (long) induPdRankList.size());
							break;
						}
						count++;
					}
				}
				secondPart = String.format("全市场共%1$s家发行主体，%2$s行业共%3$d家发行主体，%4$s于全市场位置为%5$s，于%2$s行业位置为%6$s。",
						allRankCount, perComInfo.getInduName(), induRankCount, compName, allRankDesc, induRankDesc);
				ratingSummary.append(secondPart);
			}

			result = new BondIssDMRatingSummaryVO() {
				{ setRatingSummary(ratingSummary.toString()); }
			};
		} catch (Exception ex) {
			LOG.error(String.format("findBondIssDMPerRatingSummary: ex[%s]", ex.getMessage()));
		}
		
		return SafeUtils.throwNullBusinessEx(result, "无法获取私人财报的主体量化等级概要信息");
	}

	/**
	 * 行业的违约信息
	 * @param bondPdHistDoc
	 * @param userId
	 * @return
	 */
	private Map<String, InduPdInfoVo> getInduPdInfoMap(BondPdHistDoc bondPdHistDoc, Long userId) {
		//当前的主体行业id
		 List<Long> comUniCodes = bondInduService.findComUniCodeByIndu(bondPdHistDoc.getComUniCode(), userId);
		//所有行业的信息
		Query queryList = new Query(Criteria.where("comUniCode").in(comUniCodes));
		queryList.fields().include("comUniCode");
		List<BondComInfoDoc> list =  mongoOperations.find(queryList, BondComInfoDoc.class);
		List<Long> issIdInIndu = new ArrayList<>();
		for (BondComInfoDoc bondComInfo : list) {
			issIdInIndu.add(bondComInfo.getComUniCode());
		}
		//行业的违约信息，封装个季度的所有的风险等级
		List<BondPdHistDoc> bondPdHistDocList = pdHistRepository.findByComUniCodeIn(issIdInIndu);
		Map<String,List<Long>> listPdsAll = new HashMap<>();
		for (BondPdHistDoc bondPdHist : bondPdHistDocList) {
			for (BondPdHistRec bondPdHistRec : bondPdHist.getPd()) {
				String q = SafeUtils.convertFromYearQnToYearDesc(SafeUtils.getQuarter(bondPdHistRec.getDate()));
				Long pdNum = bondPdHistRec.getPdNum();
				if(listPdsAll.get(q) == null){
					List<Long> qList = new ArrayList<>();
					if(pdNum != null){
						qList.add(bondPdHistRec.getPdNum());
						listPdsAll.put(q, qList);
					}
				}else{
					if(pdNum != null){
						listPdsAll.get(q).add(bondPdHistRec.getPdNum());
					}
				}
			}
		}
		
		//和当前主体匹配的季度
		Map<String,List<Long>> listPdsMatch = new HashMap<>();
		for (BondPdHistRec bondPdHistRec : bondPdHistDoc.getPd()) {
			String q = SafeUtils.convertFromYearQnToYearDesc(SafeUtils.getQuarter(bondPdHistRec.getDate()));
			if(listPdsAll.get(q) != null){
				listPdsMatch.put(q, listPdsAll.get(q));
			}
		}
		
		Map<String,InduPdInfoVo> result = new HashMap<>();
		listPdsMatch.forEach((key, value) ->{
			Long indu1 = SafeUtils.getQuantileLong(value, 1, SafeUtils.QUANTILE_ASC);
			Long indu10 = SafeUtils.getQuantileLong(value, 10, SafeUtils.QUANTILE_ASC);
			Long indu50 = SafeUtils.getQuantileLong(value, 50, SafeUtils.QUANTILE_ASC);
			Long indu90 = SafeUtils.getQuantileLong(value, 90, SafeUtils.QUANTILE_ASC);
			Long indu100 = SafeUtils.getQuantileLong(value, 100, SafeUtils.QUANTILE_ASC);
			InduPdInfoVo induPdInfoVo = new InduPdInfoVo();
			induPdInfoVo.setIndu1(indu1);
			induPdInfoVo.setIndu10(indu10);
			induPdInfoVo.setIndu50(indu50);
			induPdInfoVo.setIndu90(indu90);
			induPdInfoVo.setIndu100(indu100);
			result.put(key, induPdInfoVo);
		});
		
		return result;
	}

	/**
	 * 获取行业名称
	 * @param issuerId
	 * @param isGics
	 * @return
	 */
	private String getInduName(Long issuerId, boolean isGics) {
		//行业名称处理
		Query queryIndu = new Query(Criteria.where("comUniCode").is(issuerId));
		queryIndu.fields().include("induName").include("induNameSw");
		BondComInfoDoc bondComInfoDoc = mongoOperations.findOne(queryIndu, BondComInfoDoc.class);
		if(bondComInfoDoc == null){
			return null;
		}else{
			return isGics ? bondComInfoDoc.getInduName() : bondComInfoDoc.getInduNameSw();
		}
	}
	
	/**
	 * findComUniCodesWithInduIdAndUserId
	 * @param userId
	 * @param induIds
	 * @return
	 */
	public List<Long> findComUniCodesWithInduIdAndUserId(Long userId, Long[] induIds) {
		List<Long> comUniCodes = null;
		if (induIds.length > 0) {
			Query query = new Query();
			query.fields().include("comUniCode");
			if (induAdapter.isInstitutionIndu(userId)) {
				Integer orgId = induAdapter.getInstitution(userId);
				String instFeild = Constants.INSTIRUTION_INDU_CODE+orgId;
				query.addCriteria(Criteria.where(instFeild).is(induIds));
			}else{
				if (induAdapter.isGicsInduClass(userId)) {
					query.addCriteria(Criteria.where("induId").in(induIds));
				}else{
					query.addCriteria(Criteria.where("induIdSw").in(induIds));
				}
			}
			List<BondComInfoDoc> comInfoList = mongoOperations.find(query, BondComInfoDoc.class);
			
			comUniCodes = comInfoList.stream().map(BondComInfoDoc::getComUniCode).collect(Collectors.toList());
		}
		return comUniCodes;
	}
	
    public List<BondDAnnMain> findIssRatingAnnAtt(Long issuerId) {
        String sql = "SELECT m.ANN_ID annId,m.ANN_TITLE annTitle FROM bond_ccxe.D_ANN_MAIN  m"
                + " LEFT JOIN bond_ccxe.ann_publ_org o ON m.ANN_ID=o.ANN_ID"
                + " WHERE m.IS_ATT=1 AND m.FILE_PATH IS NOT NULL"
                + " AND o.ORG_UNI_CODE =%1$d"
                + " AND m.ANN_ID in(SELECT ANN_ID from bond_ccxe.ann_class_rela "
//                + " WHERE ANN_CLASS_CODE in(SELECT ANN_CLASS_CODE FROM bond_ccxe.ann_class  WHERE class_name LIKE '%主体评级%'))"
                + " WHERE ANN_CLASS_CODE in(120502))"
                + " ORDER BY DECL_DATE DESC";
        String finalSql = String.format(sql, issuerId);
        List<BondDAnnMain> list = jdbcTemplate.query(finalSql,
                new BeanPropertyRowMapper<BondDAnnMain>(BondDAnnMain.class));
        return list==null ? new ArrayList<BondDAnnMain>() : list;
    }

}

    

class InduPdInfoVo{
	@ApiModelProperty(value = "行业量化风险等级最小分位值")
	private Long indu1;
	
	@ApiModelProperty(value = "行业量化风险等级10分位值")
	private Long indu10;
	
	@ApiModelProperty(value = "行业量化风险等级50分位值")
	private Long indu50;
	
	@ApiModelProperty(value = "行业量化风险等级90分位值")
	private Long indu90;
	
	@ApiModelProperty(value = "行业量化风险等级最大分位值")
	private Long indu100;
	
	@ApiModelProperty(value = "行业名称")
	private String induName;

	public Long getIndu1() {
		return indu1;
	}

	public void setIndu1(Long indu1) {
		this.indu1 = indu1;
	}

	public Long getIndu10() {
		return indu10;
	}

	public void setIndu10(Long indu10) {
		this.indu10 = indu10;
	}

	public Long getIndu50() {
		return indu50;
	}

	public void setIndu50(Long indu50) {
		this.indu50 = indu50;
	}

	public Long getIndu90() {
		return indu90;
	}

	public void setIndu90(Long indu90) {
		this.indu90 = indu90;
	}

	public Long getIndu100() {
		return indu100;
	}

	public void setIndu100(Long indu100) {
		this.indu100 = indu100;
	}

	public String getInduName() {
		return induName;
	}

	public void setInduName(String induName) {
		this.induName = induName;
	}
	
	
}


class BondPdHistRecComparator implements Comparator<BondPdsHist> {

	private Boolean bool = false;

	public BondPdHistRecComparator() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BondPdHistRecComparator(Boolean bool) {
		super();
		this.bool = bool;
	}

	@Override
	public int compare(BondPdsHist o1, BondPdsHist o2) {
		if (o1.getPdNum() == null && o2.getPdNum() == null)
			return 0;
		if (o1.getPdNum() == null)
			return 1;
		if (o2.getPdNum() == null)
			return -1;
		Long v1 = o1.getPdNum();
		Long v2 = o2.getPdNum();
		return bool ? v1.compareTo(v2) : v2.compareTo(v1);
	}

}

class BondPdHistRecDateComparator implements Comparator<BondPdsHist> {

	@Override
	public int compare(BondPdsHist o1, BondPdsHist o2) {
		String o1Date = SafeUtils.getFormatDate(o1.getDate(), SafeUtils.DATE_FORMAT);
		String o2Date = SafeUtils.getFormatDate(o2.getDate(), SafeUtils.DATE_FORMAT);
		return o2Date.compareTo(o1Date);
	}

}