package com.innodealing.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.google.common.base.Objects;
import com.innodealing.aop.NoLogging;
import com.innodealing.bond.service.BondCityService;
import com.innodealing.bond.service.BondDetailService;
import com.innodealing.consts.Constants;
import com.innodealing.engine.jdbc.bond.AmaBondDao;
import com.innodealing.engine.jdbc.bond.BondCalculateErrorLogDao;
import com.innodealing.engine.jdbc.bond.BondNoRatingReasonDao;
import com.innodealing.engine.jdbc.bond.IndicatorDao;
import com.innodealing.engine.mongo.bond.BondComInfoRepository;
import com.innodealing.engine.mongo.bond.BondPdHistRepository;
import com.innodealing.engine.mongo.bond.BondPdRankRepository;
import com.innodealing.engine.redis.RedisUtil;
import com.innodealing.model.BondPd;
import com.innodealing.model.BondPdRankSrc;
import com.innodealing.model.InstInnerRatingInfo;
import com.innodealing.model.dm.bond.InstComIndu;
import com.innodealing.model.mongo.dm.BondComInfoDoc;
import com.innodealing.model.mongo.dm.BondDetailDoc;
import com.innodealing.model.mongo.dm.BondPdHistDoc;
import com.innodealing.model.mongo.dm.BondPdHistRec;
import com.innodealing.model.mongo.dm.BondPdRankDoc;
import com.innodealing.model.mongo.dm.BondSentiment;
import com.innodealing.model.mongo.dm.bond.detail.analyse.IssFinDatesDoc;
import com.innodealing.model.mongo.dm.bond.rating.RatingIssBondDoc;
import com.innodealing.uilogic.UIAdapter;
import com.innodealing.util.BeanUtil;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;
//import com.innodealing.util.SimpleStatistics;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class BondComDataService {

	private static final Logger LOG = LoggerFactory.getLogger(BondComDataService.class);

	@Autowired
	BondPdHistRepository pdHistRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	BondComInfoRepository comInfoReposity;

	@Autowired
	NegSentimentCache negSentimentCache;

	@Autowired
	BondPdRankRepository pdRankRepository;

	@Autowired
	ComFinQualityCache comFinQualityCache;

	@Autowired
	BondCityService bondCityService;
	
	@Autowired
	BondDetailService bondDetailService;
	
	@Autowired
	private BondCalculateErrorLogDao calculateErrorLogDao ;

	@Autowired
	private IndicatorDao indicatorDao;
	
	@Autowired
	private AmaBondDao amaBondDao;
	Map<Long, BondComInfoDoc> comInfoCache = new HashMap<Long, BondComInfoDoc>();

	protected @Autowired MongoOperations mongoOperations;

	@Autowired
	BondComSortRrsService rrsService;
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private BondInstInnerRateAndInvAdviceService instInnerRateAndInvAdviceService;

	public BondComDataService() {
	}

	@NoLogging
	public void intialize() {
		loadComFinQuality();
		integratePds();
		integratePdRank();
		integrateComInfos();
	}

	public String loadComFinQuality() {
		comFinQualityCache.loadData();
		return "task done";
	}

	public String integrateComInfos() {

		String sql = "SELECT D_PUB_COM_INFO_2.COM_UNI_CODE, D_PUB_COM_INFO_2.COM_CHI_NAME, AREA_UNI_CODE1,AREA_UNI_CODE, COM_ATTR_PAR, COM_RATING.iss_cred_level, COM_RATING.rate_writ_date ,COM_EXT.indu_uni_code as indu_id, INDU.indu_class_name as indu_name, COM_EXT.indu_uni_code_sw as indu_id_sw, INDUSW.indu_class_name as indu_name_sw, DEFAULTS.pd_date AS default_date, DEFAULTS.pd_event as default_event, DEFAULTS.bond_name as default_bond_name , \r\n"
				+ "					COM_WST_PD.worst_pd_num, COM_WST_PD.worst_pd, COM_WST_PD.worst_pd_time,COM_WST_RATING.RATE_WRIT_DATE as worstRatingTime,COM_WST_RATING.ISS_CRED_LEVEL_PAR as worstRatingNum,COM_WST_RATING.ISS_CRED_LEVEL as worstRating \r\n"
				+ "					FROM bond_ccxe.D_PUB_COM_INFO_2	    					 			      \r\n"
				+ "					LEFT JOIN       \r\n" + "						(    select *     \r\n"
				+ "								 from (     \r\n"
				+ "										 SELECT rate_writ_date, iss_cred_level, COM_UNI_CODE        \r\n"
				+ "										FROM bond_ccxe.D_BOND_ISS_CRED_CHAN        \r\n"
				+ "										WHERE is_new_rate = 1  AND ISVALID=1 and com_type_par=1   \r\n"
				+ "										order by rate_writ_date desc      \r\n"
				+ "										) AS ISS_CRED    \r\n"
				+ "								GROUP BY com_uni_code         \r\n"
				+ "						) AS COM_RATING ON D_PUB_COM_INFO_2.COM_UNI_CODE = COM_RATING.COM_UNI_CODE       \r\n"
				+ "					LEFT JOIN dmdb.t_bond_com_ext as COM_EXT on D_PUB_COM_INFO_2.COM_UNI_CODE = COM_EXT.com_uni_code    \r\n"
				+ "					LEFT JOIN dmdb.t_pub_indu_code AS INDU on INDU.indu_uni_code = COM_EXT.indu_uni_code  \r\n"
				+ "                   LEFT JOIN dmdb.t_pub_indu_code_sw AS INDUSW on INDUSW.indu_uni_code = COM_EXT.indu_uni_code_sw  \r\n"
				+ "					LEFT JOIN ( \r\n" + "							select * from (\r\n"
				+ "								select * from innodealing.t_bond_iss_pd_manage order by iss_id, t_bond_iss_pd_manage.pd_date asc\r\n"
				+ "							) D \r\n" + "							group by iss_id\r\n"
				+ "					)  DEFAULTS\r\n"
				+ "					on DEFAULTS.iss_id = COM_EXT.com_uni_code  \r\n"
				+ "					left join   \r\n" + "					(  \r\n"
				+ "							SELECT *   \r\n" + "							FROM   \r\n"
				+ "							(  \r\n"
				+ "								SELECT t_bond_com_ext.com_uni_code, t_bond_pd_par.id AS worst_pd_num, dm_bond.Rating as worst_pd, CONCAT(dm_bond.year, '/',CASE QUARTER(CONCAT(dm_bond.year, '/', dm_bond.quan_month, '/1')) WHEN 1 THEN '一季报' WHEN 2 THEN '中报' WHEN 3 THEN '三季报' WHEN 4 THEN '年报' END) AS worst_pd_time  \r\n"
				+ "								FROM  /*amaresun*/ dmdb.dm_bond AS dm_bond  \r\n"
				+ "								INNER JOIN dmdb.t_bond_com_ext AS t_bond_com_ext ON dm_bond.Comp_ID = t_bond_com_ext.ama_com_id  \r\n"
				+ "								LEFT JOIN dmdb.t_bond_pd_par AS t_bond_pd_par ON t_bond_pd_par.rating = dm_bond.Rating  \r\n"
				+ "								ORDER BY t_bond_com_ext.com_uni_code, worst_pd_num DESC,dm_bond.year desc,dm_bond.quan_month desc  \r\n"
				+ "							) AS PD_HIST_LIST  \r\n"
				+ "							GROUP BY PD_HIST_LIST.com_uni_code  \r\n"
				+ "					) AS COM_WST_PD  \r\n"
				+ "					on COM_EXT.com_uni_code = COM_WST_PD.com_uni_code  \r\n"
				+ "					left join   \r\n" + "					(  \r\n"
				+ "							SELECT *   \r\n" + "							FROM   \r\n"
				+ "							(  \r\n"
				+ "							SELECT icc.RATE_WRIT_DATE, icc.ISS_CRED_LEVEL,(CASE WHEN icc.ISS_CRED_LEVEL_PAR = 35 THEN 0 ELSE icc.ISS_CRED_LEVEL_PAR END ) AS ISS_CRED_LEVEL_PAR1, \r\n"
				+ "							icc.ISS_CRED_LEVEL_PAR,icc.COM_UNI_CODE \r\n"
				+ "							FROM bond_ccxe.d_bond_iss_cred_chan AS icc  WHERE icc.ISVALID=1 and icc.com_type_par=1  \r\n"
				+ "							ORDER BY ISS_CRED_LEVEL_PAR1 DESC,icc.RATE_WRIT_DATE DESC \r\n"
				+ "							) AS RATING_HIST_LIST  \r\n"
				+ "							GROUP BY RATING_HIST_LIST.com_uni_code  \r\n"
				+ "					) AS COM_WST_RATING  \r\n"
				+ "					on COM_EXT.com_uni_code = COM_WST_RATING.com_uni_code  \r\n"
				+ "					WHERE D_PUB_COM_INFO_2.ISVALID = 1   \r\n" + "					\r\n" + "	";

		List<BondComInfoDoc> results = (List<BondComInfoDoc>) jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<BondComInfoDoc>(BondComInfoDoc.class));

		if (results == null || results.isEmpty()) {
			LOG.error("failed to intialize ComInfoCache");
			return "failed";
		}
		
		//初始化机构行业数据
		List<Integer> insts = this.insts();
		instInduInit(insts);
		Map<Integer, List<InstInnerRatingInfo>> instIssuersDataMap = instInnerRateAndInvAdviceService.getInstHistIssuersDataMap(insts);
		
		for (BondComInfoDoc comInfoDoc : results) {

			BondPdHistDoc pdHist = pdHistRepository.findOne(comInfoDoc.getComUniCode());
			if (pdHist != null) {
				// 找到最近有财报披露的一期数据, 作为该债券的量化风险等级数据
				List<BondPdHistRec> hists = pdHist.getPd();
				if (hists != null) {
					for (int i = 0; i < hists.size(); ++i) {
						if (hists.get(i).getPdNum() == null
								|| hists.get(i).getPd().equals(Constants.FINACE_REPORT_UNPUBLISHED))
							continue;
						comInfoDoc.setPd(hists.get(i).getPd());
						comInfoDoc.setPdNum(hists.get(i).getPdNum());
						comInfoDoc.setPdDiff(hists.get(i).getPdDiff());
						comInfoDoc.setPdTime(SafeUtils.convertFromYearQnToYearDesc(SafeUtils.getQuarter(hists.get(i).getDate())));
						break;
					}
				}
			}

			comInfoDoc.setWorstPd(UIAdapter.convAmaRating2UIText(comInfoDoc.getWorstPd()));
			BondSentiment sentiment = negSentimentCache.getNegSentimentCount(comInfoDoc.getComUniCode());
			if (sentiment != null) {
				comInfoDoc.setSentimentNegative(sentiment.getSentimentNegative());
				comInfoDoc.setSentimentNeutral(sentiment.getSentimentNeutral());
				comInfoDoc.setSentimentPositive(sentiment.getSentimentPositive());
				comInfoDoc.setSentimentMonthCount(sentiment.getSentimentMonthCount());
			}

			// 地区信息
			BondDetailDoc doc = bondCityService.isBondCity(comInfoDoc.getComUniCode(), 2);
			if (doc != null) {
				comInfoDoc.setAreaCode1(doc.getAreaCode1());
				comInfoDoc.setAreaCode2(doc.getAreaCode2());
				comInfoDoc.setAreaName1(doc.getAreaName1());
				comInfoDoc.setAreaName2(doc.getAreaName2());
			}
			// 流通中债劵数
			// comInfoDoc.setEffectiveBondCount(getEffectiveBondCount(comInfoDoc.getComUniCode()));
			// 评级展望
			// comInfoDoc.setRateProsPar(getRateProsPar(comInfoDoc.getComUniCode()));
			// 总债券规模
			// comInfoDoc.setNewSizeCount(getComNewSizeCount(comInfoDoc.getComUniCode()));
			
			Map<String,Object> institutionInduMap = new HashMap<String,Object>();
			for(Integer inst : insts){
				InstComIndu indu = (InstComIndu) redisUtil.get("integration"+inst.toString()+"com"+comInfoDoc.getComUniCode());
				if(indu==null)
					continue;
				institutionInduMap.put("code"+inst, indu.getInduUniCode());
				institutionInduMap.put("name"+inst, indu.getInduUniName());
			}
			comInfoDoc.setInstitutionInduMap(institutionInduMap);
			instInnerRateAndInvAdviceService.makeComInfoWithInstDataMap(comInfoDoc, instIssuersDataMap);
			//是否是城投公司
			comInfoDoc.setMunInvest(bondCityService.isBondCity(comInfoDoc.getComUniCode(), 2).getMunInvest());
			LOG.info("构建公司主体信息：" + comInfoDoc.toString());
		}
	
		invalidComInfoCache();
		comInfoReposity.deleteAll();
		try {
			comInfoReposity.insert(results);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "task done";
	}
	

	public void refreshAll() {
		for (Map.Entry<Long, BondComInfoDoc> entry : comInfoCache.entrySet()) {
			BondComInfoDoc comInfo = entry.getValue();
			if (comInfo != null)
				comInfoReposity.save(entry.getValue());
		}
	}

	@NoLogging
	public BondComInfoDoc get(Long comUniCode) {
		if (comUniCode == null)
			return null;

		if (!comInfoCache.containsKey(comUniCode)) {
			comInfoCache.put(comUniCode, comInfoReposity.findOne(comUniCode));
		}
		return comInfoCache.get(comUniCode);
	}

	@NoLogging
	public void invalidComInfoCache() {
		comInfoCache.clear();
	}

	public String integrateComInfoAll() {
		synchronized (BondAnalysislIngtService.class) {
			// integratePds();
			intialize();
			return "task done";
		}
	}

	public String integratePds() {

		synchronized (BondComDataService.class) {
			pdHistRepository.deleteAll();
			List<BondPd> records = (List<BondPd>) jdbcTemplate.query(
					"select t_bond_com_ext.com_uni_code, t_bond_pd_par.id as pd_num,  dm_bond.Rating as pd, STR_TO_DATE(CONCAT(dm_bond.year, '/', dm_bond.quan_month, '/1'), '%Y/%m/%d') as date, dm_bond.year as year, dm_bond.quan_month as month   \r\n"
							+ "							from  /*amaresun*/ dmdb.dm_bond  as dm_bond  \r\n"
							+ "							inner join dmdb.t_bond_com_ext as t_bond_com_ext on dm_bond.Comp_ID = t_bond_com_ext.ama_com_id    \r\n"
							+ "							LEFT JOIN dmdb.t_bond_pd_par AS t_bond_pd_par ON t_bond_pd_par.rating = dm_bond.Rating  \r\n"
							+ "                         WHERE dm_bond.Rating is  NOT NULL "
							+ "							order by t_bond_com_ext.com_uni_code, date asc",
					new BeanPropertyRowMapper<BondPd>(BondPd.class));
			if (records == null) {
				LOG.error("internal error");
				return "internal error";
			}
			;
			savePdHist2Mongo(records);
			return "task done";
		}
	}

	/**
	 * use application logic instead of SQL because of difference calculation
	 * 
	 * @param records
	 */
	private void savePdHist2Mongo(List<BondPd> records) {
		List<Long> comUinCodes = getComUinCodes();
		try {
			BondPdHistDoc doc = null;
			Long comUniCode = null;
			Long lastPdNum = null;

			for (BondPd rec : records) {
				comUinCodes.remove(rec.getCom_uni_code());
				
				if (comUniCode == null || !comUniCode.equals(rec.getCom_uni_code())) {
					if (doc != null) {
						formatterPdMsg(doc);
						LOG.debug(doc.toString());
						pdHistRepository.insert(doc);
					}
					comUniCode = rec.getCom_uni_code();
					doc = new BondPdHistDoc();
					doc.setComUniCode(comUniCode);
					doc.setPd(new LinkedList<BondPdHistRec>());
					lastPdNum = null;
				}
				BondPdHistRec val = new BondPdHistRec();

				/// val.setDate(rec.getDate());

				Calendar c = Calendar.getInstance();
				// passing month-1 because 0-->jan, 1-->feb... 11-->dec
				c.set(rec.getYear(), rec.getMonth() - 1, 1);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
				val.setDate(c.getTime());
				val.setPd(UIAdapter.convAmaRating2UIText(rec.getPd()));
				val.setPdNum(rec.getPdNum());
				val.setPdDiff((lastPdNum == null || rec.getPdNum() == null) ? new Long(0) : (rec.getPdNum() - lastPdNum));
				lastPdNum = rec.getPdNum();
				Integer month = rec.getMonth();
				if (month >= 10 && month <= 12) {
					BigDecimal finQuality = comFinQualityCache.getComFinQuality(
							comFinQualityCache.new ComFinQualityKey(rec.getCom_uni_code(), rec.getYear()));
					if (finQuality != null)
						val.setFinQuality(finQuality.doubleValue());
				}
				((LinkedList<BondPdHistRec>) doc.getPd()).addFirst(val);
			}
			// 没有违约纪录的也初始化
			comUinCodes.stream().forEach(item -> {
				List<BondPdHistRec> list = initNoPdsMsg(item);
				BondPdHistDoc doc2 = new BondPdHistDoc();
				doc2.setComUniCode(item);
				doc2.setPd(list);
				pdHistRepository.insert(doc2);
			});
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.error("failed to save pd history", ex.toString());
		}
	}

	/**
	 * 获取所有主体id
	 * 
	 * @return
	 */
	private List<Long> getComUinCodes() {
		String sql = "SELECT com_uni_code  FROM t_bond_com_ext WHERE com_uni_code >0 ";
		return jdbcTemplate.queryForList(sql, Long.class);
	}

	/**
	 * 格式化违约信息
	 * 
	 * @param doc
	 */
	public void formatterPdMsg(BondPdHistDoc doc) {
		
		List<BondPdHistRec> pdRecords = doc.getPd();
		if (pdRecords == null || pdRecords.size() == 0){
			pdRecords = initNoPdsMsg(doc.getComUniCode());// 如果没有历史风险等级则初始化5条财报未披露信息提示
		}
		else{
			pdRecords = initPdsMsg(doc.getComUniCode(), pdRecords);// 如果有历史信息，格式化原信息
		}
		// 将格式化好的是新违约信息放到BondPdHistDoc中
		Collections.sort(pdRecords, new BondPdHistRecComparator());
		doc.setPd(pdRecords);
	}

	/**
	 * 初始化没有违约纪录的信息
	 * 
	 * @return
	 */
	private List<BondPdHistRec> initNoPdsMsg(Long issuerId) {
		List<BondPdHistRec> list = new ArrayList<BondPdHistRec>();
		// 最近的要显示的极度
		List<String> queterToView = getFiveQuaterRecent(QUARTER_TO_VIEW);
		//财报纪录
		Map<String,String> finDateMapping = getQuarterDateMapping(issuerId);
		
		for (int i = 0; i < queterToView.size(); i++) {
			BondPdHistRec record = new BondPdHistRec();
			String quarter = queterToView.get(i);
			record.setDate(BondPdHistRec.quarterToDate(quarter));
			record.setPdDiff(0L);
			record.setQuarter(quarter);
			record.setPd(getReason(issuerId, quarter, finDateMapping));
			list.add(record);
		}
		return list;
	}

	public static void main(String[] args) {
		String sql = "SELECT D_PUB_COM_INFO_2.COM_UNI_CODE, D_PUB_COM_INFO_2.COM_CHI_NAME, AREA_UNI_CODE1,AREA_UNI_CODE, COM_ATTR_PAR, COM_RATING.iss_cred_level, COM_RATING.rate_writ_date ,COM_EXT.indu_uni_code as indu_id, INDU.indu_class_name as indu_name, COM_EXT.indu_uni_code_sw as indu_id_sw, INDUSW.indu_class_name as indu_name_sw, DEFAULTS.pd_date AS default_date, DEFAULTS.pd_event as default_event, DEFAULTS.bond_name as default_bond_name , \r\n"
				+ "					COM_WST_PD.worst_pd_num, COM_WST_PD.worst_pd, COM_WST_PD.worst_pd_time,COM_WST_RATING.RATE_WRIT_DATE as worstRatingTime,COM_WST_RATING.ISS_CRED_LEVEL_PAR as worstRatingNum,COM_WST_RATING.ISS_CRED_LEVEL as worstRating \r\n"
				+ "					FROM bond_ccxe.D_PUB_COM_INFO_2	    					 			      \r\n"
				+ "					LEFT JOIN       \r\n" + "						(    select *     \r\n"
				+ "								 from (     \r\n"
				+ "										 SELECT rate_writ_date, iss_cred_level, COM_UNI_CODE        \r\n"
				+ "										FROM bond_ccxe.D_BOND_ISS_CRED_CHAN        \r\n"
				+ "										WHERE is_new_rate = 1 AND ISVALID=1 AND ATT_POINT IS NOT NULL     \r\n"
				+ "										order by rate_writ_date desc      \r\n"
				+ "										) AS ISS_CRED    \r\n"
				+ "								GROUP BY com_uni_code         \r\n"
				+ "						) AS COM_RATING ON D_PUB_COM_INFO_2.COM_UNI_CODE = COM_RATING.COM_UNI_CODE       \r\n"
				+ "					LEFT JOIN dmdb.t_bond_com_ext as COM_EXT on D_PUB_COM_INFO_2.COM_UNI_CODE = COM_EXT.com_uni_code    \r\n"
				+ "					LEFT JOIN dmdb.t_pub_indu_code AS INDU on INDU.indu_uni_code = COM_EXT.indu_uni_code  \r\n"
				+ "                   LEFT JOIN dmdb.t_pub_indu_code_sw AS INDUSW on INDUSW.indu_uni_code = COM_EXT.indu_uni_code_sw  \r\n"
				+ "					LEFT JOIN ( \r\n" + "							select * from (\r\n"
				+ "								select * from innodealing.t_bond_iss_pd_manage order by iss_id, t_bond_iss_pd_manage.pd_date asc\r\n"
				+ "							) D \r\n" + "							group by iss_id\r\n"
				+ "					)  DEFAULTS\r\n"
				+ "					on DEFAULTS.iss_id = COM_EXT.com_uni_code  \r\n"
				+ "					left join   \r\n" + "					(  \r\n"
				+ "							SELECT *   \r\n" + "							FROM   \r\n"
				+ "							(  \r\n"
				+ "								SELECT t_bond_com_ext.com_uni_code, t_bond_pd_par.id AS worst_pd_num, dm_bond.Rating as worst_pd, CONCAT(dm_bond.year, '/',CASE QUARTER(CONCAT(dm_bond.year, '/', dm_bond.quan_month, '/1')) WHEN 1 THEN '一季报' WHEN 2 THEN '中报' WHEN 3 THEN '三季报' WHEN 4 THEN '年报' END) AS worst_pd_time  \r\n"
				+ "								FROM  /*amaresun*/ dmdb.dm_bond AS dm_bond  \r\n"
				+ "								INNER JOIN dmdb.t_bond_com_ext AS t_bond_com_ext ON dm_bond.Comp_ID = t_bond_com_ext.ama_com_id  \r\n"
				+ "								LEFT JOIN dmdb.t_bond_pd_par AS t_bond_pd_par ON t_bond_pd_par.rating = dm_bond.Rating  \r\n"
				+ "								ORDER BY t_bond_com_ext.com_uni_code, worst_pd_num DESC,dm_bond.year desc,dm_bond.quan_month desc  \r\n"
				+ "							) AS PD_HIST_LIST  \r\n"
				+ "							GROUP BY PD_HIST_LIST.com_uni_code  \r\n"
				+ "					) AS COM_WST_PD  \r\n"
				+ "					on COM_EXT.com_uni_code = COM_WST_PD.com_uni_code  \r\n"
				+ "					left join   \r\n" + "					(  \r\n"
				+ "							SELECT *   \r\n" + "							FROM   \r\n"
				+ "							(  \r\n"
				+ "							SELECT icc.RATE_WRIT_DATE, icc.ISS_CRED_LEVEL,(CASE WHEN icc.ISS_CRED_LEVEL_PAR = 35 THEN 0 ELSE icc.ISS_CRED_LEVEL_PAR END ) AS ISS_CRED_LEVEL_PAR1, \r\n"
				+ "							icc.ISS_CRED_LEVEL_PAR,icc.COM_UNI_CODE \r\n"
				+ "							FROM bond_ccxe.d_bond_iss_cred_chan AS icc \r\n"
				+ "							ORDER BY ISS_CRED_LEVEL_PAR1 DESC,icc.RATE_WRIT_DATE DESC \r\n"
				+ "							) AS RATING_HIST_LIST  \r\n"
				+ "							GROUP BY RATING_HIST_LIST.com_uni_code  \r\n"
				+ "					) AS COM_WST_RATING  \r\n"
				+ "					on COM_EXT.com_uni_code = COM_WST_RATING.com_uni_code  \r\n"
				+ "					WHERE D_PUB_COM_INFO_2.ISVALID = 1   \r\n" + "					\r\n" + "	";

		
		System.out.println(sql);
	}

	/**
	 * 初始化违约信息提示
	 * 
	 * @param comUniCode
	 *            主体唯一标识符
	 * @param originalRecords
	 *            原始历史违约信息纪录
	 * @return
	 */
	@NoLogging
	private final static int QUARTER_TO_VIEW = 5;

	private List<BondPdHistRec> initPdsMsg(Long comUniCode, List<BondPdHistRec> originalRecords) {
		// 最近的要显示的极度
		List<String> queterToView = getFiveQuaterRecent(QUARTER_TO_VIEW);

		Iterator<BondPdHistRec> iterator = originalRecords.iterator();
		Map<String, BondPdHistRec> quarterMap = new HashMap<>();
		while (iterator.hasNext()) {
			BondPdHistRec next = iterator.next();
			String thisQuarter = SafeUtils.convertFromYearQnToYearDesc(SafeUtils.getQuarter(next.getDate()));
			quarterMap.put(thisQuarter, next);
		}

		for (String quarterKey : queterToView) {
			if (!quarterMap.containsKey(quarterKey))
				addMsg(comUniCode, quarterKey, originalRecords);
		}
		//初始化往年没有的
		return originalRecords;
	}

	/**
	 * 最近的要显示的季度
	 * 
	 * @param quaters
	 *            季度数
	 * @return
	 */
	private List<String> getFiveQuaterRecent(int quaters) {
		// Q4 Q1 Q2 Q3
		// 01-01:04-30 05-01:08-31 09-01:10-31 11-01:12-30
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String[] scopeStringArray = { "1-2-3-4", "5-6-7-8", "9-10", "11-12" };
		String[] scopeStringArrayQuater = { "yyyy-12-31", "yyyy-03-31", "yyyy-06-30", "yyyy-09-30" };
		String mounth = (Calendar.getInstance().get(Calendar.MONTH) + 1) + "";
		int q = 1;
		for (int i = 0; i < scopeStringArray.length; i++) {
			if (scopeStringArray[i].contains(mounth)) {
				q = ++i;
				break;
			}
		}
		if (q == 1)
			year--;
		
		 String[] quarterArr = SafeUtils.getQuarter(scopeStringArrayQuater[q - 1].replace("yyyy", year + ""), quaters);
		// 需要显示的最新5季度
		List<String> queterToView = new ArrayList<>();
		for(int i=0; i<quarterArr.length; i++){
			queterToView.add(SafeUtils.convertFromYearQnToYearDesc(quarterArr[i]));
		}
		
		return queterToView;
	}

	/**
	 * 
	 * @param comUniCode
	 * @param quarter
	 * @param pdRecords
	 *            历史违约纪录
	 */
	private void addMsg(Long comUniCode, String quarter, List<BondPdHistRec> pdRecords) {
		BondPdHistRec newOne = new BondPdHistRec();
		String msg = null;
		try {
			msg = getPdMsg(comUniCode, quarter);
		} catch (Exception e) {
			LOG.info(e.getMessage(), e);
			e.printStackTrace();
		}
		newOne.setPd(msg);
		newOne.setDate(BondPdHistRec.quarterToDate(quarter));
		newOne.setQuarter(quarter);
		newOne.setPdDiff(0L);
		pdRecords.add(newOne);
	}

	
	@Autowired BondNoRatingReasonDao bondNoRatingReasonDao;
	
	/**
	 * 
	 * @param comUniCode
	 *            主体唯一标识符
	 * @param quarter
	 *            当前季度
	 * @return
	 * @throws Exception 
	 */
	private String getPdMsg(Long comUniCode, String quarter) throws Exception {
		
		String quarterDate =  SafeUtils.convertFromYearReportToDate(quarter);
	
		boolean hasFinance = indicatorDao.hasFinance(amaBondDao.findIssMap(comUniCode).getAmsIssId(), quarterDate);
		// 财报季度
		// Collection<String> quarters = null;
		if (!hasFinance) {
			return Constants.FINACE_REPORT_UNPUBLISHED;
		} else {
			return hasCurrentFinancialReportMsg(comUniCode, SafeUtils.convertStringToDate(quarterDate, SafeUtils.DATE_FORMAT));
		}
	}

	private String getReason(Long comUniCode, String quarter, Map<String,String> dateMapping) {
		
		String quarterDate =  SafeUtils.convertFromYearReportToDate(quarter);
		List<String> msgs = null;
		try {
			msgs = calculateErrorLogDao.findErrorType(comUniCode, SafeUtils.convertStringToDate(quarterDate, SafeUtils.DATE_FORMAT));
		} catch (ParseException e) {
			LOG.info(e.getMessage(), e);
		}
		String msg = getMsg(msgs);
		if("".equals(SafeUtils.getString(msg))){
			return Constants.FINACE_REPORT_UNPUBLISHED;
		}else{
			return msg;
		}
		/*	
		quarter = SafeUtils.getQuarter(quarterDate, 1)[0];
		Map<String,Object> reason = bondNoRatingReasonDao.findByCompidAndFindate(comUniCode, quarterDate);
		if(reason == null){
			reason = bondNoRatingReasonDao.findByCompid(comUniCode);
		}
		if(reason == null){
			if(dateMapping.get(quarter) == null){
				return Constants.FINACE_REPORT_UNPUBLISHED;
			}else{
				return Constants.FINACE_MODEL_UNMATCHED;
			}
		}else if(Constants.FINANCE_WEIGHT_INDICATOR_IS_NULL.equals(reason.get("ques_desc"))){
			return Constants.FINACE_KEY_INDICATOR_MISSING;
		}else{
			if(dateMapping.get(quarter) == null){
				return Constants.FINACE_REPORT_UNPUBLISHED;
			}else{
				return Constants.FINACE_MODEL_UNMATCHED;
			}
		}*/
	}

	/**
	 * 获取历史财报的季度和日期mapping
	 * 
	 * @param issFinDate
	 * @return
	 */
	private Map<String, String> getQuarterDateMapping(Long issuerId) {
		//该主体的财报纪录
		IssFinDatesDoc issFinDate = mongoOperations.findOne(new Query(Criteria.where("issId").is(issuerId)), IssFinDatesDoc.class);
		Map<String, String> mapping = new HashMap<String, String>();
		if (issFinDate == null || issFinDate.getFinDates() == null) {
			return mapping;
		} else {
			Iterator<String> iterator = issFinDate.getFinDates().iterator();
			while (iterator.hasNext()) {
				String finDate = iterator.next();
				mapping.put(SafeUtils.getQuarter(finDate, 1)[0], finDate);
			}
			return mapping;
		}

	}

	/**
	 * 有当季财报，但无等级结果时的信息提示
	 * 
	 * @param quarter
	 *            当季度
	 * @param finDateMapping
	 *            历史财报信息
	 * @return msg
	 */
	private String hasCurrentFinancialReportMsg(Long comUniCode, Date finDate) {
		List<String> msgs = calculateErrorLogDao.findErrorType(comUniCode, finDate);
		return getMsg(msgs);
	}

	public static Map<String,String> msgMap = new HashMap<>();
	static {
		msgMap.put("年化错误", "因数据缺失重点指标无法年化，评级无法计算 ");
		msgMap.put("质量指标计算错误", "因重点数据缺失导致财报质量无法计算");
		msgMap.put("评级指标计算错误", " 因重点数据缺失导致评级指标无法计算");
	}
	
	private String getMsg(List<String> msgs) {
		if(msgs ==null){
			return "";
		}
		String m = "";
		for (String msg : msgs) {
			m += msgMap.get(msg) + "|";
		}
		if(msgs.size() > 0){
			return m.substring(0, m.length() - 1);
		}else{
			return m;
		}
	}

	
	public String integratePdRank() {
		// rrsService.init();

		synchronized (BondComDataService.class) {
			List<BondPdRankDoc> docs = new ArrayList<BondPdRankDoc>();
			pdRankRepository.deleteAll();
			// 从 /*amaresun*/ dmdb.dm_bond最近两年的数据，
			// 提取每个主体公司最新一期的量化等级，两年是考虑新年没有财报问题
			String sql = " SELECT * FROM   \r\n" + "                          (  \r\n"
					+ "                             SELECT * FROM (  \r\n"
					+ "                                   SELECT d_pub_com_info_2.COM_UNI_CODE as issuerId, d_pub_com_info_2.COM_CHI_NAME as issuer,dm_bond.Comp_ID as compId, t_pub_indu_code.indu_uni_code as induId,     \r\n"
					+ "                                                   t_pub_indu_code.indu_class_name as induName, t_pub_indu_code_sw.indu_uni_code as induIdSw, t_pub_indu_code_sw.indu_class_name as induNameSw, dm_bond.Rating as pd, t_bond_pd_par.id as pdNum, CONCAT(dm_bond.year, '/Q',QUARTER(CONCAT(dm_bond.year, '/', dm_bond.quan_month, '/1'))) AS reportTime ,    \r\n"
					+ "                                                   d_pub_com_info_2.COM_ATTR_PAR as comAttrPar, pub_area_code.AREA_NAME2 as province, pub_area_code.AREA_NAME3 as city    \r\n"
					+ "                                                 FROM bond_ccxe.d_pub_com_info_2 AS d_pub_com_info_2 \r\n"
					+ "                                                  LEFT JOIN dmdb.t_bond_com_ext AS t_bond_com_ext ON t_bond_com_ext.com_uni_code = d_pub_com_info_2.COM_UNI_CODE \r\n"
					+ "                                                  LEFT JOIN  /*amaresun*/ dmdb.dm_bond AS dm_bond ON dm_bond.Comp_ID = t_bond_com_ext.ama_com_id      \r\n"
					+ "                                                   LEFT JOIN dmdb.t_pub_indu_code AS t_pub_indu_code ON t_bond_com_ext.indu_uni_code = t_pub_indu_code.indu_uni_code    \r\n"
					+ "                                                   LEFT JOIN dmdb.t_pub_indu_code_sw AS t_pub_indu_code_sw ON t_bond_com_ext.indu_uni_code_sw = t_pub_indu_code_sw.indu_uni_code    \r\n"
					+ "                                                   LEFT JOIN dmdb.t_bond_pd_par AS t_bond_pd_par ON t_bond_pd_par.rating = dm_bond.Rating    \r\n"
					+ "                                                   LEFT JOIN bond_ccxe.pub_area_code AS pub_area_code ON d_pub_com_info_2.AREA_UNI_CODE = pub_area_code.AREA_UNI_CODE    \r\n"
					+ "                               WHERE  d_pub_com_info_2.ISVALID=1 and d_pub_com_info_2.COM_UNI_CODE IS NOT NULL AND t_pub_indu_code.indu_uni_code IS NOT NULL "
					+ "                                                   ORDER BY issuer ASC , reportTime DESC  \r\n"
					+ "                                           ) AS R  \r\n" +
					// 这里的ORDER BY issuer ASC , reportTime DESC 再GROUP BY
					// R.issuerId 是典型的mysql按照特定条件获取分组首条记录的手法
					"                               GROUP BY R.issuerId    \r\n"
					+ "                               ORDER BY R.pdNum DESC     \r\n"
					+ "                           ) AS PD  \r\n" + "                             \r\n"
					+ "					 		LEFT JOIN  (  \r\n"
					+ "						 		select COM_UNI_CODE, ISS_CRED_LEVEL AS rating,ORG_UNI_CODE,RATE_WRIT_DATE as rateWritDate, ATT_POINT attPoint, CASE WHEN CCE_ADVT IS NULL THEN FALSE ELSE TRUE END AS cceAdvt, \r\n"
					+ "						 	CASE WHEN CCE_DISADVT IS NULL THEN FALSE ELSE TRUE END AS cceDisadvt from (   \r\n"
					+ "							 		select * from bond_ccxe.d_bond_iss_cred_chan   WHERE ISVALID = 1  and com_type_par=1  \r\n"
					+ "									 order by bond_ccxe.d_bond_iss_cred_chan.RATE_WRIT_DATE desc   \r\n"
					+ "								 ) AS ISS  \r\n"
					+ "								 group by ISS.COM_UNI_CODE  \r\n"
					+ "							 ) AS ISS_CRED  \r\n" + "							   \r\n"
					+ "							 ON PD.issuerId = ISS_CRED.COM_UNI_CODE \r\n"
					+ "          LEFT JOIN  (SELECT CHI_SHORT_NAME as chiShortName,ORG_UNI_CODE FROM bond_ccxe.d_pub_org_info_r) AS orginfo ON  orginfo.ORG_UNI_CODE = ISS_CRED.ORG_UNI_CODE";

			List<BondPdRankSrc> records = (List<BondPdRankSrc>) jdbcTemplate.query(sql,
					new BeanPropertyRowMapper<BondPdRankSrc>(BondPdRankSrc.class));
			if (records == null) {
				LOG.error("internal error");
				return "internal error";
			}
			
			records.forEach(record -> {
				BondPdRankDoc doc = new BondPdRankDoc();
				BeanUtil.copyProperties(record, doc);
				doc.setRatingNum(UIAdapter.ratingMapAsc.get(doc.getRating()));
				doc.setOwnerType(UIAdapter.cvtComAttr2UIStr(record.getComAttrPar()));
				doc.setPd(UIAdapter.convAmaRating2UIText(doc.getPd()));
				doc.setPdSortRRs(rrsService.findSortRrsByComUniCode(record.getIssuerId()));
				if (!StringUtils.isEmpty(record.getReportTime())) {
					doc.setReportTime(SafeUtils.convertFromYearQnToYearDesc(record.getReportTime()));
				}
				
				/**
				 * BondComInfoDoc comInfoDoc =
				 * comInfoReposity.findOne(record.getIssuerId()); if (comInfoDoc
				 * == null) { LOG.warn("公司信息不存在: " + record.toString()); } else
				 * {
				 * doc.setEffectiveBondCount(comInfoDoc.getEffectiveBondCount())
				 * ;//流通中债劵数
				 * doc.setNewSizeCount(comInfoDoc.getNewSizeCount());//债劵总规模
				 * doc.setRateProsPar(comInfoDoc.getRateProsPar());//主体评级展望 }
				 */
				docs.add(doc);
			});
			pdRankRepository.insert(docs);
			return "task done";
		}
	}
	

	/**
	 * 查询发行人流通中的债劵数
	 * 
	 * @param comUniCode
	 * @return
	 */
	private Long getEffectiveBondCount(Long comUniCode) {
		Query query = new Query();
		Criteria c = new Criteria();
		c.andOperator(Criteria.where("comUniCode").is(comUniCode), Criteria.where("currStatus").is(1)// 当前存续状态
				, Criteria.where("issStaPar").is(1));// 发行状态
		query.addCriteria(c);
		Long effectiveBondCount = mongoOperations.count(query, BondDetailDoc.class);
		return effectiveBondCount == null ? 0L : effectiveBondCount;
	}

	/**
	 * 发行人最新评级展望
	 * 
	 * @param comUniCode
	 * @return
	 */
	private String getRateProsPar(Long comUniCode) {
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT \n\t");
		sql.append("(CASE rate_pros_par WHEN  1 THEN '正面'  \n\t");
		sql.append("WHEN  2 THEN '稳定'  \n\t");
		sql.append("WHEN 3 THEN '观望'  \n\t");
		sql.append("WHEN 4 THEN '负面'  \n\t");
		sql.append("ELSE '' END ) rateProsPar \n\t");
		sql.append(
				"FROM bond_ccxe.d_bond_iss_cred_chan WHERE com_uni_code = " + comUniCode + " AND is_new_rate = 1 AND ISVALID=1  and com_type_par=1 \n\t");
		sql.append("ORDER BY RATE_WRIT_DATE DESC  LIMIT 1");
		try {
			Map<String, Object> map = jdbcTemplate.queryForMap(sql.toString());
			return (String) map.get("rateProsPar");
		} catch (EmptyResultDataAccessException e) {
			return "";
		}
	}
	
	/**
	 * 得到当前发行人总资产规模
	 * 
	 * @param comUniCode
	 * @return
	 */
	class ComNewSize 
	{
		public ComNewSize(Double newSize, Double maturedSize, Double payAmount) {
			super();
			this.newSize = newSize;
			this.maturedSize = maturedSize;
			this.payAmount = payAmount;
		}
		public Double newSize;
		public Double maturedSize;
		public Double payAmount;
	}
	
	private ComNewSize getComNewSizeCount(Long comUniCode) {
		
		
		
		
		DBObject match = (DBObject)new BasicDBObject(
				"$match", new BasicDBObject( "issuerId", comUniCode	));
		DBObject group = (DBObject)new BasicDBObject(
		        "$group", new BasicDBObject(
		            "_id","$issuerId"
		        ).append(
		            "totalIssueSize", new BasicDBObject(
		                "$sum", new BasicDBObject(
		                    "$cond", new Object[]{
		                        new BasicDBObject(
		                            "$or", new Object[]{ 
		                            		 new BasicDBObject(
		         		                            "$eq", new Object[]{ "$currStatus", 1}
		         		                        ), 
		                            		 new BasicDBObject(
		         		                            "$eq", new Object[]{ "$currStatus", 2}
		         		                        ),
		                            		 }
		                        ),
		                        "$newSize",
		                        0.00
		                    }
		                )
		            )
		        ).append(
		            "maturedIssueSize", new BasicDBObject(
		                "$sum", new BasicDBObject(
		                    "$cond", new Object[]{
		                        new BasicDBObject(
		                            "$eq", new Object[]{ "$currStatus", 2}
		                        ),
		                        "$newSize",
		                        0.00
		                    }
		                )
		             )
		        ).append(
			            "payAmount", new BasicDBObject(
				                "$sum", new BasicDBObject(
				                    "$cond", new Object[]{
				                        new BasicDBObject(
				                            "$eq", new Object[]{ "$currStatus", 1}
				                        ),
				                        "$payAmount",
				                        0.00
				                    }
				                )
				             )
				        )
		     );
		
		List<DBObject> query = new ArrayList<DBObject>();
		query.add(match);
		query.add(group);
		AggregationOutput output = 
				mongoOperations.getCollection("bond_basic_info").aggregate(query);
		
		Iterator<DBObject> iterator = output.results().iterator();
		if (iterator.hasNext()) {
			DBObject ret = iterator.next();
			return new ComNewSize((Double)ret.get("totalIssueSize"), 
					(Double)ret.get("maturedIssueSize"), 
					(Double)ret.get("payAmount"));
		}
		return null;
	}
	
	/**
	 * 更新pd_rank中的主体量化风险等级两次变化情况
	 * @return
	 */
	public String updateBondIssuerPdRank() {
		LOG.info("Start to exec updateBondIssuerPdRank");
		long begin = System.currentTimeMillis();
		List<BondPdRankDoc> resultList = new ArrayList<>();
		try {
			// 获取所有的BondPdRankDoc，得到pdrank
			List<BondPdRankDoc> sourceList = mongoOperations.findAll(BondPdRankDoc.class);
			if (sourceList == null || sourceList.isEmpty()) return "failed";
			LOG.info(String.format("updateBondIssuerPdRank: find %1$d BondPdRankDoc items", sourceList.size()));
			sourceList.stream().forEach(item -> {
				// 根据reportTime得到BondPdHistDoc中的记录，并且找到其上一期
				BondPdHistDoc pdHistDoc = mongoOperations.findById(item.getIssuerId(), BondPdHistDoc.class);
				if (pdHistDoc != null) {
					List<BondPdHistRec> recList = pdHistDoc.getPd();
					// 找到当前记录
					Optional<BondPdHistRec> currPdOpt = recList.parallelStream()
							.filter(rec -> SafeUtils.getQuarter(rec.getDate()).equals(item.getReportTime()) || 
									SafeUtils.convertFromYearQnToYearDesc(SafeUtils.getQuarter(rec.getDate())).equals(item.getReportTime())).findFirst();
					if (currPdOpt.isPresent()) {
						// 当前一期存在
						BondPdHistRec currPd = currPdOpt.get();
						Optional<BondPdHistRec> lastPdOpt = recList.stream()
								.filter(rec -> 
									currPdOpt.get().getDate().getTime() > rec.getDate().getTime() && rec.getPd() != null && rec.getPdNum() != null)
								.findFirst();
						if (lastPdOpt.isPresent()) {
							BondPdHistRec lastPd = lastPdOpt.get();
							// 上期有值
							if(currPd == null){
								System.out.println(12);
							}
							if (!Objects.equal(currPd.getPdNum(), item.getCurrPdNum())
									|| !Objects.equal(lastPd.getPdNum(), item.getLastPdNum())) {
								// 当前或者上一期有变动，更新
								
								item.setCurrPd(currPd.getPd());
								item.setCurrPdNum(currPd.getPdNum());
								item.setLastPd(lastPd.getPd());
								item.setLastPdNum(lastPd.getPdNum());
								resultList.add(item);
							}
						} else {
							if (!Objects.equal(currPd.getPdNum(), item.getCurrPdNum())) {
								// 当前有变动，更新
								item.setCurrPd(currPd.getPd());
								item.setCurrPdNum(currPd.getPdNum());
								resultList.add(item);
							}
						}
					}
				} else {
					//LOG.warn(String.format("updateBondIssuerPdRank: cannot find BondPdHistDoc with id[%1$d]",
					//		item.getIssuerId()));
				}
				
			});
			LOG.info(String.format("updateBondIssuerPdRank: get %1$d result items after process", resultList.size()));
			// 更新
			resultList.parallelStream().forEach(item -> mongoOperations.save(item));
			LOG.info(String.format("updateBondIssuerPdRank: updated %1$d result items", resultList.size()));
		} catch (Exception ex) {
			LOG.error(String.format("updateBondIssuerPdRank exception[%1%s]", ex.getMessage()));
			ex.printStackTrace();
			long end = System.currentTimeMillis();
			LOG.info(String.format("End to exec updateBondIssuerPdRank in %1$d ms", end-begin));
			return "failed";
		}
		long end = System.currentTimeMillis();
		LOG.info(String.format("End to exec updateBondIssuerPdRank in %1$d ms", end-begin));
		return "success";
	}

	/**
	 * 更新主体最新评级跟上期评级
	 * 
	 * @param comUniCode
	 * @param issRating90d
	 */
	@SuppressWarnings("all")
	private void updateBondPdRankDoc(BondComInfoDoc comInfoDoc, List<Integer> issRating90d) {

		if (issRating90d == null)
			return;

		BondPdRankDoc doc = execPdRank(issRating90d);

		if (comInfoDoc == null) {
			LOG.warn("公司信息不存在..");
			return;
		}

		if (issRating90d == null || doc == null)
			return;

		mongoOperations.updateMulti(new Query(Criteria.where("_id").is(comInfoDoc.getComUniCode())),
				new Update().set("currRating", doc.getCurrRating()).set("lastRating", doc.getLastRating())
						.set("lastR", doc.getLastR()).set("currR", doc.getCurrR())
						.set("effectiveBondCount", comInfoDoc.getEffectiveBondCount())
						.set("newSizeCount", comInfoDoc.getNewSizeCount())
						.set("munInvest", bondCityService.isBondCity(comInfoDoc.getComUniCode(), 2).getMunInvest())
						.set("institutionInduMap", comInfoDoc.getInstitutionInduMap()),
				BondPdRankDoc.class);
	}

	private BondPdRankDoc execPdRank(List<Integer> issRating90d) {

		BondPdRankDoc doc = new BondPdRankDoc();

		Integer currR = null;
		Integer lastR = null;
		if (issRating90d.size() == 1) {
			currR = issRating90d.get(0);
		}
		if (issRating90d.size() > 1) {
			currR = issRating90d.get(0);
			lastR = issRating90d.get(1);
		}
		Map<Integer, String> ratingMapIn = new HashMap<>();
		for (Entry<String, Integer> map : UIAdapter.getRatingMaps().entrySet()) {
			ratingMapIn.put(map.getValue(), map.getKey());
		}

		doc.setCurrR(UIAdapter.ratingMapAsc.get(UIAdapter.num2ratingMaps.get(currR)));
		doc.setLastR(UIAdapter.ratingMapAsc.get(UIAdapter.num2ratingMaps.get(lastR)));
		doc.setCurrRating(ratingMapIn.get(currR));
		doc.setLastRating(ratingMapIn.get(lastR));

		return doc;
	}

	public String buildComAppurtenantData() {

		synchronized (BondAnalysislIngtService.class) {

			List<BondComInfoDoc> list = comInfoReposity.findAll();

			if (list == null) {
				return "comInfoReposity.findAll() is null";
			}

			Map<Long, List<Integer>> ratingIssMap = getRatingIssBondList();

			for(BondComInfoDoc comInfoDoc : list)
			{
				// 发行总规模
				String sizeDesc = bondDetailService.getBondStatisticByIssuerId(comInfoDoc.getComUniCode());
				ComNewSize newSize = getComNewSizeCount(comInfoDoc.getComUniCode());
				if (newSize != null) {
					comInfoDoc.setNewSizeCount(newSize.newSize);
					comInfoDoc.setMaturedSize(newSize.maturedSize);
					comInfoDoc.setPayAmount(newSize.payAmount);
					
//					String sizeDesc = new String();
//					if (newSize.newSize != null) {
//						sizeDesc += String.format("主体发行规模达到%1$.2f亿元。", newSize.newSize);
//						if (newSize.maturedSize != null) {
//							sizeDesc = String.format("%1$s 已到期债券规模共%2$.2f亿元。", sizeDesc, newSize.maturedSize);
//						}
//						if (newSize.payAmount != null && newSize.payAmount.doubleValue() != 0) {
//							sizeDesc = String.format("%1$s 待付的本金利息总共%2$.2f亿元。", sizeDesc, newSize.payAmount);
//						}
//					}
//					
					
					comInfoDoc.setSizeDesc(sizeDesc);
					LOG.info("issuer:" + comInfoDoc.getComChiName() + ", sizeDesc:" + sizeDesc);
				}
				
				// 流通中债劵数
				comInfoDoc.setEffectiveBondCount(getEffectiveBondCount(comInfoDoc.getComUniCode()));
				// 评级展望
				comInfoDoc.setRateProsPar(getRateProsPar(comInfoDoc.getComUniCode()));
				comInfoReposity.save(comInfoDoc);

				if (ratingIssMap != null && ratingIssMap.size() > 0)
					updateBondPdRankDoc(comInfoDoc, ratingIssMap.get(comInfoDoc.getComUniCode()));
			};

			return "task done";
		}
	}

	/**
	 * 得到主体和债项评级和评级展望
	 * 
	 * @return
	 */
	private Map<Long, List<Integer>> getRatingIssBondList() {
		Map<Long, List<Integer>> ratingIssMap = new HashMap<Long, List<Integer>>();
		Query query = new Query();
		query.fields().include("issId").include("issRating90d");
		List<RatingIssBondDoc> ratingIssBondList = mongoOperations.find(query, RatingIssBondDoc.class);
		if (ratingIssBondList == null)
			return null;
		ratingIssBondList.stream().forEach(ratingIssBondDoc -> {
			ratingIssMap.put(ratingIssBondDoc.getIssId(), ratingIssBondDoc.getIssRating90d());
		});
		return ratingIssMap;
	}

	public String integratePdRrs() {
		rrsService.loadData();
		return "success";
	}
	
	/**
	 * 查询所有机构ID
	 * @return
	 */
	public List<Integer> insts(){
		final String sql = "SELECT inst_id FROM institution.t_bond_inst_indu GROUP BY inst_id";
		List<Integer> insts = jdbcTemplate.queryForList(sql, Integer.class);
		return insts;
	}
	
	public void instInduInit(List<Integer> instIds){
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT com_uni_code AS comUniCode,com_chi_name AS comChiName,indu_uni_code AS induUniCode, \n\t");
		sql.append("indu_uni_name AS induUniName,inst_id AS instId FROM institution.t_bond_inst_com_indu");
		List<InstComIndu> comIndus = jdbcTemplate.query(sql.toString(), 
                new BeanPropertyRowMapper<InstComIndu>(InstComIndu.class));
		
		if(comIndus==null || comIndus.isEmpty())
			return;
		
		Map<Integer, List<InstComIndu>> groupBy = comIndus.stream().collect(Collectors.groupingBy(InstComIndu::getInstId));
		
		for(Integer key :groupBy.keySet()){
			List<InstComIndu> list = groupBy.get(key);
//			Map<Long, InstComIndu> instcominduMap = list.stream().collect(Collectors.toMap(InstComIndu::getComUniCode, a -> a,(k1,k2)->k1));
//			redisUtil.set("integration"+key.toString(), instcominduMap,3600L);
			for(InstComIndu indu : list){
				redisUtil.set("integration"+key.toString()+"com"+indu.getComUniCode(),indu,10800L);
			}
		}
		
	}
	
}

/**
 * 比较器
 * 
 * @author zzl
 *
 */
class BondPdHistRecComparator implements Comparator<BondPdHistRec> {

	@Override
	public int compare(BondPdHistRec o1, BondPdHistRec o2) {
		String o1Date = SafeUtils.getFormatDate(o1.getDate(), SafeUtils.DATE_FORMAT);
		String o2Date = SafeUtils.getFormatDate(o2.getDate(), SafeUtils.DATE_FORMAT);
		return o2Date.compareTo(o1Date);
	}

}