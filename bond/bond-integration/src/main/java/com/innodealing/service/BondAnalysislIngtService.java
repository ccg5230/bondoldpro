package com.innodealing.service;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.innodealing.aop.NoLogging;
import com.innodealing.bond.param.BondSentimentParam;
import com.innodealing.bond.service.BondBasicInfoService;
import com.innodealing.engine.mongo.bond.BondDetailRepository;
import com.innodealing.engine.mongo.bond.BondPdHistRepository;
import com.innodealing.engine.redis.RedisUtil;
import com.innodealing.model.BondPriceAmp;
import com.innodealing.model.dm.bond.BondDeal;
import com.innodealing.model.mongo.dm.BondBasicInfoDoc;
import com.innodealing.model.mongo.dm.BondBestQuoteYieldDoc;
import com.innodealing.model.mongo.dm.BondComInfoDoc;
import com.innodealing.model.mongo.dm.BondDealDataDoc;
import com.innodealing.model.mongo.dm.BondDealPdDistDoc;
import com.innodealing.model.mongo.dm.BondDetailDoc;
import com.innodealing.model.mongo.dm.BondPdDealStatsDoc;
import com.innodealing.model.mongo.dm.BondSentimentDistinctDoc;
import com.innodealing.model.mongo.dm.BondSentimentTopDateDoc;
import com.innodealing.model.mongo.dm.BondSentimentVersion;
import com.innodealing.util.MD5Util;
import com.innodealing.util.StringUtils;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;


@Service
public class BondAnalysislIngtService {

	static final Logger LOG = LoggerFactory.getLogger(BondAnalysislIngtService.class);

	protected @Autowired JdbcTemplate jdbcTemplate;


	@Autowired
	public BondComDataService bondComDataService;

	@Autowired 
	BondPdHistRepository pdHistRepository;

	@Autowired
	MongoOperations mongoOperations;

	@Autowired
	BondBasicInfoService bondInfoService;

	@Autowired
	RedisUtil redisUtil;

	@Autowired
	BondDetailRepository bondDetailRepository;

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	final String sqlQuoteFormat = 
			"	SELECT MAX(bond_price)- MIN(bond_price) AS amplitude, QUOT.bond_code, QUOT.bond_uni_code, QUOT.bond_short_name, BOND_ISSER.ORG_UNI_CODE, COM_INDU.indu_uni_code\r\n" + 
					"	FROM dmdb.t_bond_quote AS QUOT\r\n" + 
					"	LEFT JOIN bond_ccxe.bond_isser_info  BOND_ISSER ON QUOT.bond_uni_code = BOND_ISSER.BOND_UNI_CODE\r\n" + 
					"	LEFT JOIN dmdb.t_bond_com_ext AS COM_INDU ON BOND_ISSER.ORG_UNI_CODE = COM_INDU.com_uni_code \r\n" + 
					"	WHERE update_time >= DATE(NOW()) - INTERVAL 7 DAY AND side = %d  AND COM_INDU.indu_uni_code = %d \r\n" + 
					"	GROUP BY QUOT.bond_uni_code\r\n" + 
					"	ORDER BY amplitude %s \r\n" + 
					"   limit 1\r\n" ;

	final String sqlDealFormat = 
			"	SELECT MAX(bond_rate)- MIN(bond_rate) AS amplitude, DEAL.bond_uni_code, DEAL.bond_name as bond_short_name, BOND_ISSER.ORG_UNI_CODE, COM_INDU.indu_uni_code\r\n" + 
					"	FROM dmdb.t_bond_deal_data AS DEAL\r\n" + 
					"	LEFT JOIN bond_ccxe.bond_isser_info  BOND_ISSER ON DEAL.bond_uni_code = BOND_ISSER.BOND_UNI_CODE\r\n" + 
					"	LEFT JOIN dmdb.t_bond_com_ext AS COM_INDU ON BOND_ISSER.ORG_UNI_CODE = COM_INDU.com_uni_code \r\n" + 
					"	WHERE DEAL.create_time >= DATE(NOW()) - INTERVAL 7 DAY  AND COM_INDU.indu_uni_code = %d\r\n" + 
					"	GROUP BY DEAL.bond_uni_code\r\n" + 
					"	ORDER BY amplitude %s \r\n" + 
					"   limit 1\r\n" ;

	//暂时注销下面的函数，改为从redis获取最近一个移动月的负面舆情数
	//@EventListener
	//@Async
	public void handleSentimentSaved(BondSentimentTopDateDoc sentiment) {
		try {
			if (sentiment == null) {
				return;
			}
			//是否当日数据
			Calendar pubDate = Calendar.getInstance();
			pubDate.setTime(sentiment.getPubDate());
			Calendar today = Calendar.getInstance();
			boolean isSameDay = pubDate.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
					pubDate.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR);
			if (!isSameDay) return;
			//更新当日债券详情
			Long comUniCode = Long.valueOf(sentiment.getComUniCode());
			Query query = new Query();
			query.addCriteria(Criteria.where("comUniCode").is(comUniCode));
			List<BondDetailDoc> detailDocs = mongoOperations.find(query, BondDetailDoc.class);
			for (BondDetailDoc detailDoc : detailDocs) {
				detailDoc.setPoNegtive(sentiment.getSentimentNegative());
				detailDoc.setPoNeutral(sentiment.getSentimentNeutral());
				detailDoc.setPoPositive(sentiment.getSentimentPositive());
				mongoOperations.save(detailDoc);
			}
			mongoOperations.updateMulti(new Query(Criteria.where("_id").is(comUniCode)),
					new Update()
					.set("sentimentPositive", sentiment.getSentimentPositive())
					.set("sentimentNegative", sentiment.getSentimentNegative())
					.set("sentimentNeutral",sentiment.getSentimentNeutral()),BondComInfoDoc.class);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("failed to handleSentimentSaved", e);
		}
	}

	@EventListener
	@Async
	@NoLogging
	public void handleBestQuoteSaved(BondBestQuoteYieldDoc quote) {
		if (quote == null || quote.getBondUniCode() == null) {
			LOG.error("invalid BondBestQuoteYieldDoc, data:" + ((quote==null)?"null":quote.toString()));
			return;
		}
		Update update = new Update().set("bestBidPrice", quote.getBidPrice())
				.set("bestOfrPrice", quote.getOfrPrice());
		mongoOperations.updateFirst(new Query(Criteria.where("_id").is(quote.getBondUniCode())), 
				update, BondDetailDoc.class);
	}

	@EventListener
	@Async
	public void handleDealSaved(BondDeal dealEvent) {
		try {
			if (dealEvent == null) return ;
			if (dealEvent.getBondUniCode() == null) return;
			if (dealEvent.getBondRate() == null) return;

			BondBasicInfoDoc basicInfoDoc = bondInfoService.findByBondUniCode(dealEvent.getBondUniCode(),0);
			if (basicInfoDoc == null) return;
			
			LOG.debug("BondDeal event callback:" + dealEvent.toString());
			updateBondDetailByQuote(dealEvent);
			//updatePdDist(dealEvent, basicInfoDoc);
			
		} catch (Exception e) {
			LOG.error(e.toString(), e);
			e.printStackTrace();
		}
	}

	private void updateBondDetailByQuote(BondDeal dealEvent) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("_id").is(dealEvent.getBondUniCode()));
		query.addCriteria(criteria);
		BondDetailDoc doc = mongoOperations.findOne(query, BondDetailDoc.class);
		try {
			Update update = new Update().set("price", dealEvent.getBondRate().doubleValue());
			if(doc.getPriceCount()==null){
				Long count = mongoOperations.count(query, BondDealDataDoc.class);
				update.set("priceCount", count+1);
			}else{
				update.set("priceCount", doc.getPriceCount()+1);
			}
			update.set("priceUpdateTime", dealEvent.getCreateTime());
			mongoOperations.updateFirst(new Query(Criteria.where("_id").is(dealEvent.getBondUniCode())), update,
					BondDetailDoc.class);
		} catch (Exception e) {
			LOG.error("failed to updateBondDetailByQuote", e);
		}
	}

	public String getTenor(Long tenorDays){
		double years = new BigDecimal(tenorDays).divide(new BigDecimal(365L),2,BigDecimal.ROUND_HALF_UP).doubleValue();
		double months = new BigDecimal(tenorDays).divide(new BigDecimal(30L),2,BigDecimal.ROUND_HALF_UP).doubleValue();
		String tenor = ""; 
		if(years >=8.5){
			tenor = "10Y";
		}
		else if(years >=7){
			tenor = "7Y";
		}
		else if (years >= 4) {
			tenor = "5Y";
		}
		else if (years >= 2) {
			tenor = "3Y";
		}
		else if (months >= 10.5) {
			tenor = "1Y";
		}
		else if (months >= 7.5) {
			tenor = "9M";
		}
		else if (months >= 4.5) {
			tenor = "6M";
		}
		else if (months >= 2) {
			tenor = "3M";
		}
		else {
			tenor = "1M";
		}
		return tenor;
	}

	private BondPdDealStatsDoc resortActiveDeals(String cacheTenorKey, BondDeal newDealEvent)
	{
		LOG.info("update zset, key:" + cacheTenorKey + ", value:" + newDealEvent.toString());
		try {
			redisTemplate.boundZSetOps(cacheTenorKey).incrementScore(newDealEvent.getBondName(), 1);
			Set<TypedTuple<String>> highestBondDeals = redisTemplate.boundZSetOps(cacheTenorKey)
					.reverseRangeWithScores(0, 0);
			if (highestBondDeals != null && !highestBondDeals.isEmpty()) {
				TypedTuple<String> highestBondDeal = highestBondDeals.iterator().next();
				Double score = highestBondDeal.getScore();
				String bondName = highestBondDeal.getValue();
				BondDeal activeBondDealStats = (BondDeal) redisUtil.get("Last_Deal_" + bondName);
				BondPdDealStatsDoc dealStats = new BondPdDealStatsDoc();
				dealStats.setDealCount(score.longValue());
				dealStats.setLastPrice(activeBondDealStats.getBondRate());
				dealStats.setShortName(activeBondDealStats.getBondName());
				dealStats.setBondUniCode(activeBondDealStats.getBondUniCode());
				return dealStats;
			} 
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("failed to resortActiveDeals", e);
		}
		return null;
	}

	public String clean(String key){

		if(StringUtils.isEmpty(key) || !"DM".equals(key)){

			return "秘钥错误";

		}

		mongoOperations.dropCollection(BondSentimentTopDateDoc.class);
		mongoOperations.dropCollection(BondSentimentVersion.class);
		mongoOperations.dropCollection(BondSentimentDistinctDoc.class);

		/**清空redis去重数据*/
		redisUtil.removePattern("sentiment*");
		redisUtil.removePattern("sentimentMerge*");

		return "成功";
	}
	
	
	public String delete(BondSentimentParam bondSentimentParam){
		
		BondSentimentDistinctDoc doc = bondSentimentParam.getDoc();
		
		Query query = new Query();
		
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("comUniCode").is(doc.getComUniCode()),Criteria.where("serialNo").is(doc.getSerialNo()),Criteria.where("important").is(doc.getImportant()));
		query.addCriteria(criteria);
		
		/**移除mongodb数据*/
		mongoOperations.remove(query,BondSentimentDistinctDoc.class);
		
		/** 移除redis数据*/
		redisUtil.remove("sentiment"+ MD5Util.getMD5(doc.toString()));
		redisUtil.remove("sentimentMerge"+ MD5Util.getMD5(doc.merge()));
		
		return "成功";
	}
}

