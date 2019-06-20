package com.innodealing.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.innodealing.util.SafeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innodealing.bond.service.BondSentimentService;
import com.innodealing.engine.mongo.bond.BondComInfoRepository;
import com.innodealing.engine.mongo.bond.BondSentimentTopDateRepository;
import com.innodealing.engine.mongo.bond.BondSentimentVersionRepository;
import com.innodealing.engine.redis.RedisUtil;
import com.innodealing.model.mongo.dm.BondComInfoDoc;
import com.innodealing.model.mongo.dm.BondSentiment;
import com.innodealing.model.mongo.dm.BondSentimentDistinctDoc;
import com.innodealing.util.StringUtils;
import com.innodealing.model.mongo.dm.BondSentimentJsonDoc;
import com.innodealing.model.mongo.dm.BondSentimentTopDateDoc;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class BondSentimentDateJobService {

	private static final Logger LOG = LoggerFactory.getLogger(BondSentimentDateJobService.class);

	private static final String BONDSENTIMENT = "bond_sentiment_distinct";

	private final Long ID = 2L;

	protected @Autowired MongoOperations mongoOperations;

	protected @Autowired BondSentimentTopDateRepository bondSentimentTopDateRepository;

	protected @Autowired JdbcTemplate jdbcTemplate;

	protected @Autowired BondComInfoRepository bondComInfoRepository;

	protected @Autowired BondSentimentVersionRepository bondVersionRepository;

	protected @Autowired ApplicationEventPublisher publisher;

	protected @Autowired RedisUtil redisUtil;

	protected @Autowired BondSentimentJobService bondSentimentJobService;

	public String syncIntegration() {
		synchronized (BondSentimentDateJobService.class) {
			return findListByDateOrder();
		}
	}

	public String findListByDateOrder() {

		LOG.info("============Sentiment date start===========");

		/** 取历史版本号 */
		Long version = bondSentimentJobService.getVersion(ID);
		LOG.info("version:" + version);

		/** 得到当前版本号 */
		Long newVersion = getNewVersion();
		LOG.info("newVersion:" + newVersion);

		/** 从mongo里面得到数据 */
		List<BondSentimentTopDateDoc> list = null;
		try {
			list = getData(version, newVersion);
		} catch (Exception e) {
			LOG.error("取数据出现错误!当前版本号:" + version + ",最大版本号:" + newVersion, e);
			return null;
		}

		if (null != list && list.size() > 0) {
			/** 处理舆情总数 */
			// sum(list);

			for (BondSentimentTopDateDoc vo : list) {
				LOG.info("save BondSentimentTopDateDoc:" + vo.getComChiName());
				mongoOperations.save(vo);
				this.publisher.publishEvent(vo);
			}
			/** 修改版本号 */
			bondSentimentJobService.updateVersion(ID, newVersion);
			LOG.info("舆情数据更新成功..");

			/** 统计最近一月负面舆情数 */
			LOG.info("==============最近一月负面舆情数统计====start================");
			sum();
			LOG.info("==============最近一月负面舆情数统计====end================");
			/** 统计最近一月舆情总数 */
			LOG.info("==============统计最近一月舆情总数====start================");
			getSentimentMonthCount();
			LOG.info("==============统计最近一月舆情总数====end================");

		}

		LOG.info("============Sentiment date end===========");

		return null;
	}

	/** 根据日期节点查询mongo舆情数据 */
	private List<BondSentimentTopDateDoc> getData(Long version, Long newVersion) throws Exception {

		LOG.info("根据日期节点查询mongo舆情数据...");

		DBObject filterCond = new BasicDBObject();

		if (newVersion.equals(version)) {
			LOG.info("舆情数据未变..");
			return null;
		}

		// filterCond.put("index", new BasicDBObject("$gte", 1).append("$lt",
		// 10));
		filterCond.put("important", 1);

		if (!StringUtils.isEmpty(version) && !StringUtils.isEmpty(newVersion)) {
			filterCond.put("index", new BasicDBObject("$gt", version).append("$lt", newVersion + 1));
		}

		/** 排除pubDate为空的 */
		filterCond.put("pubDate", new BasicDBObject("$ne", null));

		DBObject match = new BasicDBObject("$match", filterCond);
		BasicDBList dateList = new BasicDBList();
		dateList.add("$pubDate");
		dateList.add(28800000);// 解决timezone 8小时时差
		// dateList.add(0);
		DBObject time = new BasicDBObject("$add", dateList);
		DBObject group = new BasicDBObject();
		DBObject groupDate = new BasicDBObject();
		groupDate.put("year", new BasicDBObject("$year", time));
		groupDate.put("month", new BasicDBObject("$month", time));
		groupDate.put("day", new BasicDBObject("$dayOfMonth", time));
		groupDate.put("comUniCode", "$comUniCode");

		BasicDBList dateListnum = new BasicDBList();
		dateListnum.add("$sentiment");
		dateListnum.add("正面");
		DBObject nums1 = new BasicDBObject("$eq", dateListnum);

		BasicDBList dateListnum1 = new BasicDBList();
		dateListnum1.add("$sentiment");
		dateListnum1.add("负面");
		DBObject nums2 = new BasicDBObject("$eq", dateListnum1);

		BasicDBList dateListnum2 = new BasicDBList();
		dateListnum2.add("$sentiment");
		dateListnum2.add("中性");
		DBObject nums3 = new BasicDBObject("$eq", dateListnum2);

		DBObject grop = new BasicDBObject();
		grop.put("_id", groupDate);
		grop.put("sentimentPositive",
				new BasicDBObject("$sum", new BasicDBObject("$cond", new Object[] { nums1, 1, 0 })));
		grop.put("sentimentNegative",
				new BasicDBObject("$sum", new BasicDBObject("$cond", new Object[] { nums2, 1, 0 })));
		grop.put("sentimentNeutral",
				new BasicDBObject("$sum", new BasicDBObject("$cond", new Object[] { nums3, 1, 0 })));

		group.put("$group", grop);

		@SuppressWarnings("all")
		AggregationOutput output = mongoOperations.getCollection(BONDSENTIMENT).aggregate(match, group);
		Iterator<?> iterator = output.results().iterator();

		List<BondSentimentTopDateDoc> resultMap = new ArrayList<BondSentimentTopDateDoc>();

		while (iterator.hasNext()) {
			Object basicdb = iterator.next();
			BondSentimentJsonDoc vo = json2bean(basicdb);
			resultMap.add(getJsonData(vo));
		}

		LOG.info("resultMap.length:" + resultMap.size());

		return datProcessing(resultMap);
	}

	/**
	 * 得到当前版本号
	 */
	private Long getNewVersion() {
		PageRequest request = new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "index"));
		Query query = new Query();
		query.addCriteria(Criteria.where("important").is(1)).with(request);
		query.fields().include("index");
		BondSentimentDistinctDoc vo = mongoOperations.findOne(query, BondSentimentDistinctDoc.class);
		return vo.getIndex();
	}

	/** 当天的数据 */
	private List<BondSentimentTopDateDoc> getOldDate() {
		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.MILLISECOND, 0);
		Date stime = new Date(c1.getTimeInMillis());

		Calendar c2 = Calendar.getInstance();
		c2.set(Calendar.HOUR_OF_DAY, 24);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.MINUTE, 0);
		c1.set(Calendar.MILLISECOND, 999);
		Date etime = new Date(c2.getTimeInMillis());

		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("pubDate").gte(stime), Criteria.where("pubDate").lte(etime));
		query.addCriteria(criteria);
		List<BondSentimentTopDateDoc> list = mongoOperations.find(query, BondSentimentTopDateDoc.class);
		return list;
	}

	private BondSentimentJsonDoc json2bean(Object object) {
		BondSentimentJsonDoc vo = null;
		if (null == object) {
			return vo;
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			vo = mapper.readValue(object.toString(), BondSentimentJsonDoc.class);
		} catch (JsonParseException e) {
			LOG.error("JsonParseException,json=" + object.toString());
		} catch (JsonMappingException e) {
			LOG.error("JsonMappingException,json=" + object.toString());
		} catch (IOException e) {
			LOG.error("IOException,json=" + object.toString());
		}
		return vo;
	}

	/** 处理日期跟uincode */
	private BondSentimentTopDateDoc getJsonData(BondSentimentJsonDoc vo) throws Exception {
		BondSentimentTopDateDoc resultvo = new BondSentimentTopDateDoc();
		if (null != vo) {
			Map<String, Object> map = vo.get_id();
			if (null != map) {
				resultvo.setSentimentPositive(vo.getSentimentPositive());
				resultvo.setSentimentNegative(vo.getSentimentNegative());
				resultvo.setSentimentNeutral(vo.getSentimentNeutral());
				resultvo.setComUniCode(map.get("comUniCode").toString());
				int y = (int) map.get("year");
				int m = (int) map.get("month");
				int d = (int) map.get("day");
				Calendar c1 = Calendar.getInstance();
				c1.set(y, m - 1, d, 00, 00, 00);
				resultvo.setPubDate(new Date(c1.getTimeInMillis()));
				BondComInfoDoc cominfo = bondComInfoRepository.findOne(SafeUtils.getLong(map.get("comUniCode")));
				if (null != cominfo) {
					resultvo.setComChiName(cominfo.getComChiName());
					resultvo.setInduId(cominfo.getInduId());
					resultvo.setInduName(cominfo.getInduName());
					resultvo.setInduIdSw(cominfo.getInduIdSw());
					resultvo.setInduNameSw(cominfo.getInduNameSw());
					resultvo.setEffectiveBondCount(cominfo.getEffectiveBondCount());
				}
			}
		}
		return resultvo;
	}

	private List<BondSentimentTopDateDoc> datProcessing(List<BondSentimentTopDateDoc> list) {

		/** 历史数据 */
		Map<String, BondSentimentTopDateDoc> map = list2map(getOldDate());

		if (map == null) {
			map = new HashMap<String, BondSentimentTopDateDoc>();
		}

		BondSentimentTopDateDoc vo = null;

		List<BondSentimentTopDateDoc> results = new ArrayList<BondSentimentTopDateDoc>();

		if (null != list && list.size() > 0) {
			for (BondSentimentTopDateDoc parameterMap : list) {
				if (null != map.get(parameterMap.toString())) {
					vo = map.get(parameterMap.toString());
				}
				if (null != vo) {
					parameterMap.setSentimentNegative(parameterMap.getSentimentNegative() + vo.getSentimentNegative());
					parameterMap.setSentimentNeutral(parameterMap.getSentimentNeutral() + vo.getSentimentNeutral());
					parameterMap.setSentimentPositive(parameterMap.getSentimentPositive() + vo.getSentimentPositive());
				}
				parameterMap.setId(parameterMap.toString());
				results.add(parameterMap);
			}
		}
		return results;
	}

	/**
	 * list转map
	 */
	private Map<String, BondSentimentTopDateDoc> list2map(List<BondSentimentTopDateDoc> list) {

		Map<String, BondSentimentTopDateDoc> map = new HashMap<String, BondSentimentTopDateDoc>();

		if (null != list && list.size() > 0) {
			for (BondSentimentTopDateDoc vo : list) {
				map.put(vo.toString(), vo);
			}
		}
		return map;
	}

	public static void main(String[] args) {
		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.MILLISECOND, 0);
		Date stime = new Date(c1.getTimeInMillis());
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(stime));
		c1.add(Calendar.MONTH, -1);// 获取上个月月份

		Date etime = new Date(c1.getTimeInMillis());
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(etime));
	}

	/**
	 * 统计近一个月内发行人的负面舆情总数
	 * 
	 * @throws Exception
	 */
	private void sum() {

		List<BondSentimentTopDateDoc> list = null;
		try {
			list = getSentimentNegative();
		} catch (Exception e) {
			LOG.error("===============取最近一月负面舆情失败============");
		}

		if (list == null || list.isEmpty()) {
			return;
		}

		// 清除原数据
		redisUtil.remove("sentimentDate2Map");

		Map<String, BondSentiment> map = new HashMap<String, BondSentiment>();

		for (BondSentimentTopDateDoc vo : list) {

			if (StringUtils.isEmpty(vo.getSentimentPositive())) {
				vo.setSentimentPositive(0);
			}
			if (StringUtils.isEmpty(vo.getSentimentNegative())) {
				vo.setSentimentNegative(0);
			}
			if (StringUtils.isEmpty(vo.getSentimentNeutral())) {
				vo.setSentimentNeutral(0);
			}

			BondSentiment sentiment = new BondSentiment();
			sentiment.setSentimentPositive(vo.getSentimentPositive());
			sentiment.setSentimentNegative(vo.getSentimentNegative());
			sentiment.setSentimentNeutral(vo.getSentimentNeutral());
			// 将当前统计的数据保存
			map.put(vo.getComUniCode(), sentiment);
		}

		// 将当前统计的数据保存
		redisUtil.set("sentimentDate2Map", map);

	}

	/**
	 * 最近一个月舆情总数
	 */
	@SuppressWarnings("all")
	public String getSentimentMonthCount() {

		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.MILLISECOND, 0);

		Date etime = new Date(c1.getTimeInMillis());

		c1.add(Calendar.MONTH, -1);// 获取上个月月份

		Date stime = new Date(c1.getTimeInMillis());

		DBObject filterCond = new BasicDBObject();

		/** 排除pubDate为空的 */
		filterCond.put("pubDate", new BasicDBObject("$gte", stime).append("$lte", etime));

		DBObject match = new BasicDBObject("$match", filterCond);

		DBObject groupDate = new BasicDBObject();
		groupDate.put("comUniCode", "$comUniCode");

		DBObject group = new BasicDBObject();
		DBObject grop = new BasicDBObject();
		grop.put("_id", groupDate);
		grop.put("count", new BasicDBObject("$sum", 1));
		group.put("$group", grop);

		List<DBObject> listdb = new ArrayList<DBObject>();
		listdb.add(match);
		listdb.add(group);

		AggregationOutput output = mongoOperations.getCollection("bond_sentiment_distinct").aggregate(listdb);
		Iterator iterator = output.results().iterator();
		BondSentimentService service = new BondSentimentService();
		try {
			while (iterator.hasNext()) {
				Object basicdb = iterator.next();
				Map<String, Object> map = (Map<String, Object>) basicdb;
				Map<String, String> codemap = (Map<String, String>) map.get("_id");
				redisUtil.set("sentimentMonthCount" + codemap.get("comUniCode"), map.get("count"));
			}
		} catch (Exception e) {
			LOG.error("最近一个月舆情总数更新失败..");
		}

		return "SUCCESS";
	}

	/** 查询最近一个月的负面舆情 */
	private List<BondSentimentTopDateDoc> getSentimentNegative() throws Exception {

		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.MILLISECOND, 0);

		Date etime = new Date(c1.getTimeInMillis());

		c1.add(Calendar.MONTH, -1);// 获取上个月月份

		Date stime = new Date(c1.getTimeInMillis());

		DBObject filterCond = new BasicDBObject();

		/** 排除pubDate为空的 */
		filterCond.put("pubDate", new BasicDBObject("$gte", stime).append("$lte", etime));

		DBObject match = new BasicDBObject("$match", filterCond);

		DBObject groupDate = new BasicDBObject();
		groupDate.put("comUniCode", "$comUniCode");

		DBObject group = new BasicDBObject();
		DBObject grop = new BasicDBObject();
		grop.put("_id", groupDate);
		grop.put("sentimentPositive", new BasicDBObject("$sum", "$sentimentPositive"));
		grop.put("sentimentNegative", new BasicDBObject("$sum", "$sentimentNegative"));
		grop.put("sentimentNeutral", new BasicDBObject("$sum", "$sentimentNeutral"));
		group.put("$group", grop);

		List<DBObject> listdb = new ArrayList<DBObject>();
		listdb.add(match);
		listdb.add(group);

		AggregationOutput output = mongoOperations.getCollection("bond_sentiment_date_top").aggregate(listdb);
		Iterator<?> iterator = output.results().iterator();

		List<BondSentimentTopDateDoc> resultMap = new ArrayList<BondSentimentTopDateDoc>();
		BondSentimentService service = new BondSentimentService();

		BondSentimentJsonDoc vo = null;
		BondSentimentTopDateDoc doc = null;
		try {
			while (iterator.hasNext()) {
				Object basicdb = iterator.next();
				vo = (BondSentimentJsonDoc) service.json2bean(basicdb, new BondSentimentJsonDoc());
				doc = new BondSentimentTopDateDoc();
				Map<String, Object> map = vo.get_id();
				if (null != map) {
					doc.setComUniCode(map.get("comUniCode").toString());
					doc.setSentimentPositive(vo.getSentimentPositive());
					doc.setSentimentNegative(vo.getSentimentNegative());
					doc.setSentimentNeutral(vo.getSentimentNeutral());
				}
				resultMap.add(doc);
			}
		} catch (Exception e) {
			LOG.error("stime:" + stime + ",etime=" + etime, e);
		}

		return resultMap;
	}

}
