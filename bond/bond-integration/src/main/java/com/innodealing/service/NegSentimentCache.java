package com.innodealing.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheLoader.InvalidCacheLoadException;
import com.google.common.cache.LoadingCache;
import com.innodealing.aop.NoLogging;
import com.innodealing.model.mongo.dm.BondSentiment;


@Component
public class NegSentimentCache {
//	
//	@Autowired
//	private RedisUtil redisUtil;
	
	
//	@Autowired
//	private MongoTemplate mongoTemplate;
//
	
	@Resource(name="sentimentMongo")
	protected MongoTemplate sentimentMongoTemplate;
	private static final Logger LOG = LoggerFactory.getLogger(NegSentimentCache.class);

	public NegSentimentCache(){}
	
	@SuppressWarnings("all")
	private LoadingCache<Long, BondSentiment> localCache = CacheBuilder.newBuilder()
			.concurrencyLevel(10)
			.weakKeys()
			.maximumSize(7000)
			.expireAfterWrite(5, TimeUnit.MINUTES)
			.build(new CacheLoader<Long, BondSentiment>() {
				public synchronized BondSentiment load(Long comUniCode) {
					//次出重构-舆情信息直接访问
					BondSentiment sentiment = new BondSentiment();
					try {
						Date now = new Date();
						
						String startTime = dateToStr(dateForLastMonth(now), "yyyy-MM-dd");
						String endTime = dateToStr(now, "yyyy-MM-dd");
						Query query = new Query();
						//最近1月舆情数-公告
						Criteria criatira = new Criteria();
						criatira.and("comUniCode").is(comUniCode.toString());
						criatira.and("sentiment").ne(0);// 重点公告（tags!='0'）
						criatira.and("status").is(1); //已发布
						criatira = buildTimeBetween(criatira, "pubDate", startTime, endTime);
						query.addCriteria(criatira);
						Long count = (long) sentimentMongoTemplate.count(query, "bond_bulletin");
						sentiment.setSentimentMonthCount(count.intValue());
						//最近1月舆情数-诉讼
						query = new Query();
						criatira = new Criteria();
						criatira.and("comUniCode").is(comUniCode.toString());
						criatira.and("reduplicated").is(0);
						criatira.and("pDesc").ne("");//排除内容为空的
						criatira = buildTimeBetween(criatira, "pubDate", startTime, endTime);
						query.addCriteria(criatira);
						count = (long) sentimentMongoTemplate.count(query, "bond_law_detail");
						sentiment.setSentimentMonthCount(sentiment.getSentimentMonthCount()+count.intValue());
						//最近1月舆情数-舆情
						query = new Query();
						criatira = new Criteria();
						criatira.and("reduplicated").is(0);	// 去重
						criatira.and("sentiment").ne(0);// 重点公告（tags!='0'）
						criatira = buildTimeBetween(criatira, "pubDate", startTime, endTime);
						criatira.and("status").is(1); //已发布
						criatira.and("comUniCode").is(comUniCode.toString());
						query.addCriteria(criatira);
						count = (long) sentimentMongoTemplate.count(query, "bond_sentiment_tagnews_simple");
						sentiment.setSentimentMonthCount(sentiment.getSentimentMonthCount()+count.intValue());					
						
						//负面情感-公告
						query = new Query();
						criatira = new Criteria();
						criatira.and("sentiment").is(1); //情感是风险
						criatira = buildTimeBetween(criatira, "pubDate", startTime, endTime);
						criatira.and("status").is(1); //已发布
						criatira.and("comUniCode").is(comUniCode.toString());
						query.addCriteria(criatira);
						count = (long) sentimentMongoTemplate.count(query, "bond_bulletin");
						sentiment.setSentimentNegative(count.intValue());
						//负面情感-新闻
						query = new Query();
						criatira = new Criteria();
						criatira.and("reduplicated").is(0);	// 去重
						criatira.and("sentiment").is(1); //情感是风险
						criatira = buildTimeBetween(criatira, "pubDate", startTime, endTime);
						criatira.and("status").is(1); //已发布
						criatira.and("comUniCode").is(comUniCode.toString());
						query.addCriteria(criatira);
						count = (long) sentimentMongoTemplate.count(query, "bond_sentiment_tagnews_simple");
						sentiment.setSentimentNegative(sentiment.getSentimentNegative()+count.intValue());
					
						//中性情感-公告
						query = new Query();
						criatira = new Criteria();
						criatira.and("sentiment").is(2); //情感是中性
						criatira = buildTimeBetween(criatira, "pubDate", startTime, endTime);
						criatira.and("status").is(1); //已发布
						criatira.and("comUniCode").is(comUniCode.toString());
						query.addCriteria(criatira);
						count = (long) sentimentMongoTemplate.count(query, "bond_bulletin");
						sentiment.setSentimentNeutral(count.intValue());
						//中性情感-新闻
						query = new Query();
						criatira = new Criteria();
						criatira.and("reduplicated").is(0);	// 去重
						criatira.and("sentiment").is(2); //情感是中性
						criatira = buildTimeBetween(criatira, "pubDate", startTime, endTime);
						criatira.and("status").is(1); //已发布
						criatira.and("comUniCode").is(comUniCode.toString());
						query.addCriteria(criatira);
						count = (long) sentimentMongoTemplate.count(query, "bond_sentiment_tagnews_simple");
						sentiment.setSentimentNeutral(sentiment.getSentimentNeutral()+count.intValue());
						
						
						//利好情感-新闻
						query = new Query();
						criatira = new Criteria();
						criatira.and("reduplicated").is(0);	// 去重
						criatira.and("sentiment").is(3); //情感是利好
						criatira = buildTimeBetween(criatira, "pubDate", startTime, endTime);
						criatira.and("status").is(1); //已发布
						criatira.and("comUniCode").is(comUniCode.toString());
						query.addCriteria(criatira);
						count = (long) sentimentMongoTemplate.count(query, "bond_bulletin");
						sentiment.setSentimentPositive(count.intValue());
						//利好情感-公告
						query = new Query();
						criatira = new Criteria();
						criatira.and("reduplicated").is(0);	// 去重
						criatira.and("sentiment").is(3); //情感是利好
						criatira = buildTimeBetween(criatira, "pubDate", startTime, endTime);
						criatira.and("status").is(1); //已发布
						criatira.and("comUniCode").is(comUniCode.toString());
						query.addCriteria(criatira);
						count = (long) sentimentMongoTemplate.count(query, "bond_sentiment_tagnews_simple");
						sentiment.setSentimentPositive(sentiment.getSentimentPositive()+count.intValue());
					} catch (Exception e) {
						e.printStackTrace();
						LOG.error("can't not found sentiment date is error, comUniCode:" + comUniCode , e);
					}
					return sentiment;
					
//					try {
//						Map<String,BondSentiment> map = (Map<String,BondSentiment>)redisUtil.get("sentimentDate2Map");
//						
//						BondSentiment sentiment = null;
//						if (map == null) {
//							map = new HashMap<String,BondSentiment>();
//						}
//						if(map.containsKey(comUniCode.toString())){
//							sentiment = map.get(comUniCode.toString());
//						}else{
//							sentiment = new BondSentiment();
//						}
//						
//						if(redisUtil.exists("sentimentMonthCount" + comUniCode)){
//							sentiment.setSentimentMonthCount((Integer)redisUtil.get("sentimentMonthCount" + comUniCode));
//						}
//						
//						return sentiment;
//					} catch (Exception e) {
//						//e.printStackTrace();
//						LOG.warn("can't not found sentiment date 2, comUniCode:" + comUniCode , e);
//						return null;
//					}
				}
			});
	
	@NoLogging
	public BondSentiment getNegSentimentCount(Long comUniCode)
	{
		BondSentiment sentiment = null;
		try {
			sentiment =  localCache.get(comUniCode);
		} catch (InvalidCacheLoadException e) {
			LOG.warn("not sentiment for comUniCode:" + comUniCode);
			return null;
		} catch (ExecutionException e) {
			LOG.warn("can't not found sentiment date 2, comUniCode:" + comUniCode , e);
			return null;
		}
		return sentiment;
	}
	

	/**
	 * 获取上一个月的时间
	 * @param date
	 * @return
	 */
	public Date dateForLastMonth(Date date){
		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例  
		ca.setTime(date);
		ca.add(Calendar.MONTH, -1);// 月份减1  
		return ca.getTime(); // 结果  
	}
	public  Date strToDate(String data, String pattern) {
		Date result = null;
		try {
			SimpleDateFormat sf = new SimpleDateFormat(pattern);
			result = sf.parse(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String dateToStr(Date date, String pattern) {
		String result = null;
		try {
			SimpleDateFormat sf = new SimpleDateFormat(pattern);
			result =  sf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Criteria buildTimeBetween(Criteria criteria,String key,String startTime,String endTime){
		Date stime = strToDateStart(startTime, "yyyy-MM-dd");
		Date etime = strToDateEnd(endTime, "yyyy-MM-dd");
		if(stime!=null  && etime!=null){
			return 	criteria.and(key).gte(stime).lte(etime);
		}
		if(stime!=null){
			return criteria.and(key).gte(stime);
		}
		if(etime!=null){
			return criteria.and(key).lte(etime);
		}
		return criteria;
	}
	
	/**
	 * 时间转换 yyyy-MM-dd 00:00:00:000
	 * 
	 * @param data
	 * @param pattern
	 * @return
	 */
	public Date strToDateStart(String data, String pattern) {
		if(data==null) return null;
		Date result = null;
		try {
			SimpleDateFormat sf = new SimpleDateFormat(pattern);
			result = sf.parse(data);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(result);
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			result = calendar.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 时间转换 yyyy-MM-dd 23:59:59:999
	 * 
	 * @param data
	 * @param pattern
	 * @return
	 */
	public Date strToDateEnd(String data, String pattern) {
		if(data==null)  return null;
		Date result = null;
		try {
			SimpleDateFormat sf = new SimpleDateFormat(pattern);
			result = sf.parse(data);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(result);
			calendar.set(Calendar.HOUR, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			result = calendar.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
