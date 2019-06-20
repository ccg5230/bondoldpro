package com.innodealing.bond.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParseException;
import com.innodealing.engine.mongo.bond.BondComInfoRepository;
import com.innodealing.engine.mongo.bond.BondSentimentTopRepository;
import com.innodealing.engine.redis.RedisUtil;
import com.innodealing.model.mongo.dm.BondSentimentDistinctDoc;
import com.innodealing.model.mongo.dm.BondSentimentDoc;
import com.innodealing.model.mongo.dm.BondSentimentJsonDoc;
import com.innodealing.model.mongo.dm.BondSentimentMergeDoc;
import com.innodealing.model.mongo.dm.BondSentimentTopDateDoc;
import com.innodealing.util.StringUtils;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Service
public class BondSentimentService {

	private static final Logger LOG = LoggerFactory.getLogger(BondSentimentService.class);

	@Autowired
	MongoOperations mongoOperations;

	@Autowired
	BondSentimentTopRepository bondSentimentTopRepository;

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@Autowired
	protected BondComInfoRepository bondComInfoRepository;

	@Autowired
	private BondInduService bondInduService;

	@Autowired
	RedisUtil redisUtil;

	public Page<BondSentimentDistinctDoc> findListByDate(String startTime, String endTime, String comUniCode,
			Integer page, Integer limit, Integer type) {

		Sort sort = new Sort(Sort.Direction.DESC, "pubDate");

		PageRequest request = new PageRequest(page - 1, limit, sort);

		Query query = new Query();

		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date stime = null;
		Date etime = null;
		if (!StringUtils.isEmpty(startTime)) {
			try {
				stime = sf.parse(startTime);
			} catch (ParseException e) {
				stime = null;
				LOG.error("字符串转日期失败！", e);
			}
		}

		if (!StringUtils.isEmpty(endTime)) {
			try {
				etime = sf.parse(endTime);
			} catch (ParseException e) {
				etime = null;
				LOG.error("字符串转日期失败！", e);
			}
		}

		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("title").ne(""),
				StringUtils.isEmpty(startTime) ? new Criteria() : Criteria.where("pubDate").gte(stime),
				StringUtils.isEmpty(endTime) ? new Criteria() : Criteria.where("pubDate").lte(etime),
				StringUtils.isEmpty(comUniCode) ? new Criteria() : Criteria.where("comUniCode").is(comUniCode),
				StringUtils.isEmpty(type) || type != 1 ? new Criteria() : Criteria.where("important").is(1));

		query.addCriteria(criteria).with(request);

		List<BondSentimentDistinctDoc> list = mongoOperations.find(query, BondSentimentDistinctDoc.class);

		return new PageImpl<>(list, request, mongoOperations.count(query, BondSentimentDistinctDoc.class));
	}

	public List<BondSentimentJsonDoc> findListByDateOrder(Long[] induId, String type, String startTime, String endTime,
			Long userId) {

		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date stime = null;
		Date etime = null;
		try {
			stime = sf.parse(startTime);
			etime = sf.parse(endTime);
		} catch (ParseException e) {
			LOG.error("字符串转日期失败！", e);
			return null;
		}

		DBObject filterCond = new BasicDBObject();

		// 得到当前用户所选行业类型 true是GICS false是申万
		filterCond.put(bondInduService.getInduIdByUser(userId), new BasicDBObject("$in", induId));
		filterCond.put("pubDate", new BasicDBObject("$gte", stime).append("$lte", etime));
		//排除流通中债劵为0的发行人
		filterCond.put("effectiveBondCount",new BasicDBObject("$gt", 0));
		
		DBObject match = new BasicDBObject("$match", filterCond);
		DBObject groupDate = new BasicDBObject();

		groupDate.put("comUniCode", "$comUniCode");
		groupDate.put("comChiName", "$comChiName");

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
		if (!"count".equals(type)) {
			DBObject sort = new BasicDBObject("$sort", new BasicDBObject(type, -1));
			DBObject limit = new BasicDBObject("$limit", 5);
			listdb.add(sort);
			listdb.add(limit);
		}

		AggregationOutput output = mongoOperations.getCollection("bond_sentiment_date_top").aggregate(listdb);
		Iterator iterator = output.results().iterator();

		List<BondSentimentJsonDoc> resultMap = new ArrayList<BondSentimentJsonDoc>();
		try {
			while (iterator.hasNext()) {
				Object basicdb = iterator.next();
				BondSentimentJsonDoc vo = (BondSentimentJsonDoc) json2bean(basicdb, new BondSentimentJsonDoc());

				if (null != vo) {

					/** 处理舆情总数 */
					if ("count".equals(type)) {
						vo.setSentimentCount(
								vo.getSentimentNegative() + vo.getSentimentNeutral() + vo.getSentimentPositive());
					}

					Map<String, Object> map = vo.get_id();
					if (!StringUtils.isEmpty(map.get("comChiName"))) {
						vo.setComChiName(map.get("comChiName").toString());
					}
					if (!StringUtils.isEmpty(map.get("comUniCode"))) {
						vo.setComUniCode(map.get("comUniCode").toString());
					}
				}
				resultMap.add(vo);
			}

		} catch (Exception e) {
			LOG.error("findListByDateOrder is error ,induUniCode=" + induId + ",type=" + type + ",startTime="
					+ startTime + "endTime=" + endTime, e);
		}

		if ("count".equals(type)) {
			Collections.sort(resultMap);
			return resultMap.subList(0, resultMap.size()>=5?5:resultMap.size());
		}
		return resultMap;
	}

	public List<BondSentimentJsonDoc> findListByDate2(String startTime, String endTime, String comUniCode) {

		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date stime = null;
		Date etime = null;
		try {
			stime = sf.parse(startTime);
			etime = sf.parse(endTime);
		} catch (ParseException e) {
			LOG.error("字符串转日期失败！", e);
			return null;
		}

		DBObject filterCond = new BasicDBObject();
		filterCond.put("pubDate", new BasicDBObject("$gte", stime).append("$lte", etime));
		filterCond.put("comUniCode", comUniCode);

		DBObject match = new BasicDBObject("$match", filterCond);

		BasicDBList dateList = new BasicDBList();
		dateList.add("$pubDate");
		dateList.add(28800000);// 解决timezone 8小时时差
		// dateList.add(0);
		DBObject time = new BasicDBObject("$add", dateList);
		DBObject group = new BasicDBObject();
		DBObject groupDate = new BasicDBObject();

		Integer type = getType(stime, etime);

		if (type == 1) {
			groupDate.put("year", new BasicDBObject("$year", time));
		} else if (type == 2) {
			groupDate.put("year", new BasicDBObject("$year", time));
			groupDate.put("month", new BasicDBObject("$month", time));
		} else if (type == 3) {
			groupDate.put("year", new BasicDBObject("$year", time));
			groupDate.put("week", new BasicDBObject("$week", time));
		} else if (type == 4) {
			groupDate.put("year", new BasicDBObject("$year", time));
			groupDate.put("month", new BasicDBObject("$month", time));
			groupDate.put("day", new BasicDBObject("$dayOfMonth", time));
		}
		groupDate.put("comUniCode", "$comUniCode");

		DBObject grop = new BasicDBObject();
		grop.put("_id", groupDate);
		grop.put("sentimentPositive", new BasicDBObject("$sum", "$sentimentPositive"));
		grop.put("sentimentNegative", new BasicDBObject("$sum", "$sentimentNegative"));
		grop.put("sentimentNeutral", new BasicDBObject("$sum", "$sentimentNeutral"));

		group.put("$group", grop);

		DBObject sort = new BasicDBObject("$sort", new BasicDBObject("_id", -1));

		AggregationOutput output = mongoOperations.getCollection("bond_sentiment_date_top").aggregate(match, group,
				sort);
		Iterator iterator = output.results().iterator();

		List<BondSentimentJsonDoc> resultMap = new ArrayList<BondSentimentJsonDoc>();

		try {
			while (iterator.hasNext()) {
				Object basicdb = iterator.next();
				BondSentimentJsonDoc vo = (BondSentimentJsonDoc) json2bean(basicdb, new BondSentimentJsonDoc());
				if (type == 3) {
					resultMap.add(getBondSentimentJsonDocByWeek(vo));
				} else {
					resultMap.add(vo);
				}
			}
		} catch (Exception e) {
			LOG.error("findListByDateOrder is error ,comUniCode=" + comUniCode + ",startTime=" + startTime + "endTime="
					+ endTime, e);
		}

		return resultMap;
	}

	private Integer getType(Date strarTime, Date entTime) {

		/** 得到两个日期相差天数 */
		long day = (entTime.getTime() - strarTime.getTime()) / (24 * 60 * 60 * 1000);

		if (day >= 365) {
			return 1;
		} else if (day < 365 && day > 30) {
			return 2;
		} else if (day <= 30 && day > 7) {
			return 3;
		} else {
			return 4;
		}

	}

	public Object json2bean(Object object, Object c) {
		Object vo = null;
		if (null == object) {
			return vo;
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			vo = mapper.readValue(object.toString(), c.getClass());
		} catch (JsonParseException e) {
			LOG.error("JsonParseException,json=" + object.toString());
		} catch (JsonMappingException e) {
			LOG.error("JsonMappingException,json=" + object.toString());
		} catch (IOException e) {
			LOG.error("IOException,json=" + object.toString());
		}
		return vo;
	}

	public List<BondSentimentJsonDoc> findListByDate(String comUniCode) {

		Calendar calendar = Calendar.getInstance();
		Date etime = calendar.getTime();

		calendar.add(Calendar.WEEK_OF_YEAR, -5);
		Date stime = calendar.getTime();

		DBObject filterCond = new BasicDBObject();
		filterCond.put("pubDate", new BasicDBObject("$gte", stime).append("$lte", etime));
		filterCond.put("comUniCode", comUniCode);

		DBObject match = new BasicDBObject("$match", filterCond);

		BasicDBList dateList = new BasicDBList();
		dateList.add("$pubDate");
		dateList.add(28800000);// 解决timezone 8小时时差
		// dateList.add(0);
		DBObject time = new BasicDBObject("$add", dateList);
		DBObject group = new BasicDBObject();
		DBObject groupDate = new BasicDBObject();

		groupDate.put("year", new BasicDBObject("$year", time));
		groupDate.put("week", new BasicDBObject("$week", time));
		groupDate.put("comUniCode", "$comUniCode");

		DBObject grop = new BasicDBObject();
		grop.put("_id", groupDate);
		grop.put("sentimentPositive", new BasicDBObject("$sum", "$sentimentPositive"));
		grop.put("sentimentNegative", new BasicDBObject("$sum", "$sentimentNegative"));
		grop.put("sentimentNeutral", new BasicDBObject("$sum", "$sentimentNeutral"));

		group.put("$group", grop);

		DBObject sort = new BasicDBObject("$sort", new BasicDBObject("_id", -1));

		AggregationOutput output = mongoOperations.getCollection("bond_sentiment_date_top").aggregate(match, group,
				sort);
		Iterator iterator = output.results().iterator();

		List<BondSentimentJsonDoc> resultMap = new ArrayList<BondSentimentJsonDoc>();

		try {
			while (iterator.hasNext()) {
				Object basicdb = iterator.next();
				BondSentimentJsonDoc vo = (BondSentimentJsonDoc) json2bean(basicdb, new BondSentimentJsonDoc());
				resultMap.add(getBondSentimentJsonDocByWeek(vo));
			}
		} catch (Exception e) {
			LOG.error("最新五周舆情数据查询失败 ,comUniCode=" + comUniCode, e);
		}

		return resultMap;
	}

	public BondSentimentDoc oneSentiments(Long index, Integer important) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("index").is(index));
		query.addCriteria(criteria);

		BondSentimentDoc doc = null;
		if (1 == important) {
			doc = mongoOperations.findOne(query, BondSentimentDoc.class);
		} else if (0 == important) {
			doc = mongoOperations.findOne(query, BondSentimentMergeDoc.class);
		}
		return doc;
	}

	/**
	 * 根据week得到当前周的第一天
	 * 
	 * @param week
	 * @return
	 */
	private Date getWeekDate(Integer week) {

		Calendar cal = Calendar.getInstance();
		/** $week Converts a date into a number between 0 and 53 */
		cal.set(Calendar.WEEK_OF_YEAR, week + 1);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, cal.getActualMinimum(Calendar.DAY_OF_WEEK) - dayOfWeek);
		cal.add(Calendar.DATE, 1);
		Date weekDate = cal.getTime();
		return weekDate;

	}

	private BondSentimentJsonDoc getBondSentimentJsonDocByWeek(BondSentimentJsonDoc vo) {
		if (null != vo) {
			Map<String, Object> map = vo.get_id();
			if (!StringUtils.isEmpty(map.get("week"))) {
				Integer week = (Integer) map.get("week");
				vo.setWeekDate(getWeekDate(week));
			}
		}
		return vo;
	}

	public BondSentimentTopDateDoc todaySentimentsOne(String comUniCode) {
		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.MILLISECOND, 0);
		// Date stime = new Date(c1.getTimeInMillis() + 28800000);
		Date stime = new Date(c1.getTimeInMillis());

		Calendar c2 = Calendar.getInstance();
		c2.set(Calendar.HOUR_OF_DAY, 24);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.MILLISECOND, 999);
		// Date etime = new Date(c2.getTimeInMillis() + 28800000);
		Date etime = new Date(c2.getTimeInMillis());

		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("pubDate").gte(stime).lte(etime),
				Criteria.where("comUniCode").is(comUniCode));
		query.addCriteria(criteria);
		BondSentimentTopDateDoc doc = mongoOperations.findOne(query, BondSentimentTopDateDoc.class);
		return doc;
	}

	public static void main(String[] args) {
		// Calendar calendar = Calendar.getInstance();
		// calendar.add(Calendar.WEEK_OF_YEAR, -5);
		// Date date = calendar.getTime();
		// System.out.println(new SimpleDateFormat("yyyy-MM-dd
		// HH:mm:ss").format(date));

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.WEEK_OF_YEAR, 35);
		Calendar cal1 = (Calendar) cal.clone();
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, cal.getActualMinimum(Calendar.DAY_OF_WEEK) - dayOfWeek);
		cal.add(Calendar.DATE, 1);
		Date d = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(sdf.format(d));
		cal1.add(Calendar.DATE, cal1.getActualMaximum(Calendar.DAY_OF_WEEK) - dayOfWeek);
		cal1.add(Calendar.DATE, 1);
		System.out.println(sdf.format(cal1.getTime()));

		Calendar cal11 = Calendar.getInstance();
		cal11.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour
											// of day !
		cal11.clear(Calendar.MINUTE);
		cal11.clear(Calendar.SECOND);
		cal11.clear(Calendar.MILLISECOND);

		// get start of this week in milliseconds
		cal11.set(Calendar.DAY_OF_WEEK, cal11.getFirstDayOfWeek());
		System.out.println("Start of this week:       " + cal11.getTime());
		System.out.println("... in milliseconds:      " + cal11.getTimeInMillis());
		System.out.println(sdf.format(cal11.getTime()));
		System.out.println(sdf.format(cal11.getTimeInMillis()));

		Calendar firDay = Calendar.getInstance();
		// 先滚动到该年
		firDay.set(Calendar.YEAR, 2016);
		// 滚动到周
		firDay.set(Calendar.WEEK_OF_YEAR, 35 + 1);
		// 得到该周第一天
		firDay.set(Calendar.DAY_OF_WEEK, 2);
		String firstDay = sdf.format(firDay.getTime());
		System.out.println(firstDay);

	}

}
