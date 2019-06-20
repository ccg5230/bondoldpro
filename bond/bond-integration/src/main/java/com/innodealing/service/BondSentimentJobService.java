package com.innodealing.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.innodealing.engine.mongo.bond.BondComInfoRepository;
import com.innodealing.engine.mongo.bond.BondSentimentDistinctRepository;
import com.innodealing.engine.mongo.bond.BondSentimentTopRepository;
import com.innodealing.engine.mongo.bond.BondSentimentVersionRepository;
import com.innodealing.engine.redis.RedisUtil;
import com.innodealing.model.mongo.dm.BondSentimentDistinctDoc;
import com.innodealing.model.mongo.dm.BondSentimentVersion;
import com.innodealing.model.mongo.dm.BondSentimentDoc;
import com.innodealing.util.MD5Util;
import com.innodealing.util.StringUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * top:1 带情感倾向的舆情数据处理
 * 
 * @author liuqi
 *
 */
@Component
public class BondSentimentJobService {

	private static final Logger LOG = LoggerFactory.getLogger(BondSentimentJobService.class);

	// 版本号bond_sentiment_version.ID
	private final Long ID = 1L;

	private final Long SIZE = 10000L;

	protected @Autowired MongoOperations mongoOperations;

	protected @Autowired BondSentimentTopRepository bondSentimentTopRepository;

	protected @Autowired JdbcTemplate jdbcTemplate;

	protected @Autowired BondComInfoRepository bondComInfoRepository;

	protected @Autowired BondSentimentVersionRepository bondVersionRepository;

	protected @Autowired RedisUtil redisUtil;

	protected @Autowired BondSentimentDistinctRepository bondSentimentDistinctRepository;

	private @Autowired ApplicationEventPublisher eventPublisher;

	private Boolean flay = false;

	public String syncIntegration(Boolean flay) {
		this.flay = flay;
		synchronized (BondSentimentJobService.class) {
			return findListByDateOrder();
		}
	}

	public String findListByDateOrder() {

		LOG.info("============Sentiment start===========");

		// 取历史版本号
		Long version = getVersion(ID);

		if (version == null) {
			version = 0L;
		}

		// 得到当前版本号
		Long newVersion = getMaxId();

		if (newVersion.equals(version)) {
			LOG.info("舆情数据未变..");
			return null;
		}

		LOG.info("当前版本号:" + version + ",最大版本号:" + newVersion);

		for (Long i = version; i < newVersion; i += SIZE) {

			List<BondSentimentDistinctDoc> list = null;

			// 最新数据
			try {
				list = getData(i, i + SIZE >= newVersion ? newVersion : i + SIZE);
			} catch (Exception e) {
				LOG.error("取数据出现错误!当前版本号:" + version + ",最大版本号:" + newVersion, e);
				return null;
			}
			if (null != list && !list.isEmpty()) {

				for (BondSentimentDistinctDoc vo : list) {
					// 标记当前记录为带情感倾向的
					vo.setImportant(1);
					mongoOperations.save(vo);
					if(!redisUtil.exists("sentiment" + MD5Util.getMD5(vo.toString())) && flay){
						//舆情数据的通知
						eventPublisher.publishEvent(vo);
					}
					//去重
					redisUtil.set("sentiment" + MD5Util.getMD5(vo.toString()), vo);
					// 合并
					redisUtil.set("sentimentMerge" + MD5Util.getMD5(vo.merge()), vo);
				}

			}

		}

		// 修改版本号
		updateVersion(ID, newVersion);
		LOG.info("舆情去重数据更新成功..");

		LOG.info("============Sentiment end===========");
		return null;
	}

	/**
	 * 从mongo取版本号
	 * 
	 * @param id
	 * @return
	 */
	public Long getVersion(Long id) {
		Long version = null;
		BondSentimentVersion vo = bondVersionRepository.findOne(id);
		if (null != vo) {
			version = vo.getVersion();
		}
		return version;
	}

	/**
	 * 修改版本号
	 * 
	 * @param id
	 * @param version
	 */
	public void updateVersion(Long id, Long version) {
		BondSentimentVersion vo = new BondSentimentVersion(id, version);
		mongoOperations.save(vo);
	}

	/**
	 * 得到数据
	 * 
	 * @param queryObject
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private List<BondSentimentDistinctDoc> getData(BasicDBObject queryObject)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		// 记录处理
		final String reduce = "function(doc, prev){ \n\t" + "if(prev.ruleName.indexOf(doc.ruleName)==-1){ \n\t"
				+ "     prev.ruleName = doc.ruleName +',' + prev.ruleName \n\t" + "} \n\t"
				+ "prev.comChiName = doc.comChiName \n\t" + "prev.pubDate = doc.pubDate \n\t"
				+ "prev.url = doc.url \n\t" + "prev.index = doc.index \n\t" + "prev.serialNo = doc.serialNo \n\t" + "}";
		Map<String, Object> groupmap = new HashMap<String, Object>();
		groupmap.put("comUniCode", 1);
		groupmap.put("sentiment", 2);
		groupmap.put("title", 3);

		// 分组统计初始变量
		DBObject initial = new BasicDBObject();
		initial.put("ruleName", "");
		initial.put("comChiName", "");
		initial.put("pubDate", "");
		initial.put("url", "");
		initial.put("index", 0);
		initial.put("serialNo", "");

		DBObject result = mongoOperations.getCollection("bond_sentiment").group(new BasicDBObject(groupmap),
				queryObject, initial, reduce);

		List<BondSentimentDistinctDoc> resultList = new ArrayList<BondSentimentDistinctDoc>();

		if (null != result) {
			@SuppressWarnings("unchecked")
			Map<String, BasicDBObject> map1 = result.toMap();

			for (String str : map1.keySet()) {
				BasicDBObject basicdb = map1.get(str);
				Double d = (Double) basicdb.get("index");
				basicdb.put("index", d.longValue());
				BondSentimentDistinctDoc sc = dbObjectToBean(basicdb, new BondSentimentDistinctDoc());
				sc.setId(sc.toString());
				resultList.add(sc);
			}
		}
		return resultList;
	}

	/**
	 * 根据节点查询mongo舆情数据
	 * 
	 * @param version
	 * @param newVersion
	 * @return
	 * @throws Exception
	 */
	private List<BondSentimentDistinctDoc> getData(Long version, Long newVersion) throws Exception {

		LOG.info("=====================查询version=" + version + ",newVersion=" + newVersion);

		List<BondSentimentDistinctDoc> resultList = new ArrayList<BondSentimentDistinctDoc>();

		BasicDBObject queryObject = new BasicDBObject();
		queryObject.put("title", new BasicDBObject("$ne", ""));

		queryObject.put("index", new BasicDBObject("$gt", version).append("$lte", newVersion));
		resultList.addAll(getData(queryObject));

		// 标签去重
		List<BondSentimentDistinctDoc> list = dataDistinct(resultList);

		return list;
	}

	/**
	 * 标签去重 当批数据去重以及新旧历史去重
	 * 
	 * @param list
	 * @return
	 */
	private List<BondSentimentDistinctDoc> dataDistinct(List<BondSentimentDistinctDoc> list) {

		if (null == list || list.isEmpty()) {
			return null;
		}

		List<BondSentimentDistinctDoc> results = new ArrayList<BondSentimentDistinctDoc>();

		for (BondSentimentDistinctDoc newvo : list) {

			// 是否存在历史记录
			if (redisUtil.exists("sentiment" + MD5Util.getMD5(newvo.toString()))) {
				BondSentimentDistinctDoc oldvo = (BondSentimentDistinctDoc) redisUtil
						.get("sentiment" + MD5Util.getMD5(newvo.toString()));
				if (!StringUtils.isEmpty(oldvo.getRuleName())) {
					// 旧数据
					String[] strlist = oldvo.getRuleName().split(",");
					List<String> lists = Arrays.asList(strlist);
					// 新数据
					String[] strlist1 = newvo.getRuleName().split(",");
					List<String> lists2 = Arrays.asList(strlist1);
					// 新旧数据标签去重
					List<String> arraylist = union(lists, lists2);
					String str = "";
					for (int i = 0; i < arraylist.size(); i++) {
						str += arraylist.get(i);
						if (i < arraylist.size() - 1) {
							str += ",";
						}
					}
					oldvo.setRuleName(str);
				} else {
					oldvo.setRuleName(newvo.getRuleName());
				}
				// 保留新数据
				oldvo.setIndex(newvo.getIndex());
				oldvo.setId(MD5Util.getMD5(newvo.toString()));
				results.add(oldvo);
			} else {
				newvo.setRuleName(newvo.getRuleName());
				newvo.setId(MD5Util.getMD5(newvo.toString()));
				results.add(newvo);
			}

		}

		return results;
	}

	/**
	 * 集合合并去重
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static <T> List<T> union(List<T> list1, List<T> list2) {
		Set<T> set = new HashSet<T>();

		set.addAll(list1);
		set.addAll(list2);

		return new ArrayList<T>(set);
	}

	/**
	 * 得到最大的自增ID
	 * 
	 * @return
	 */
	private Long getMaxId() {
		PageRequest request = new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "index"));
		Query query = new Query();
		query.with(request);
		query.fields().include("index");
		BondSentimentDoc vo = mongoOperations.findOne(query, BondSentimentDoc.class);
		return vo != null ? vo.getIndex() : null;
	}

	/**
	 * 将DBObject转换成Bean对象
	 * 
	 * @param dbObject
	 * @param bean
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public BondSentimentDistinctDoc dbObjectToBean(DBObject dbObject, BondSentimentDistinctDoc bean)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (bean == null) {
			return null;
		}
		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields) {
			String varName = field.getName();
			Object object = dbObject.get(varName);
			if (object != null) {
				setProperty(bean, varName, object);
			}

		}
		return bean;
	}

	/**
	 * 取出Mongo中的属性值，为bean赋值
	 * 
	 * @param bean
	 * @param varName
	 * @param object
	 */
	public static <T> void setProperty(T bean, String varName, T object) {
		varName = varName.substring(0, 1).toUpperCase() + varName.substring(1);
		try {
			String type = object.getClass().getName();
			// 类型为String
			if (type.equals("java.lang.String")) {
				Method m = bean.getClass().getMethod("get" + varName);
				String value = (String) m.invoke(bean);
				if (value == null) {
					m = bean.getClass().getMethod("set" + varName, String.class);
					m.invoke(bean, object);
				}
			}
			// 类型为String
			if (type.equals("java.util.Date")) {
				Method m = bean.getClass().getMethod("get" + varName);
				String value = (String) m.invoke(bean);
				if (value == null) {
					m = bean.getClass().getMethod("set" + varName, Date.class);
					m.invoke(bean, object);
				}
			}

			// 类型为Long
			if (type.equals("java.lang.Long")) {
				Method m = bean.getClass().getMethod("get" + varName);
				String value = (String) m.invoke(bean);
				if (value == null) {
					m = bean.getClass().getMethod("set" + varName, Long.class);
					m.invoke(bean, object);
				}
			}
		} catch (NoSuchMethodException e) {
			LOG.error("NoSuchMethodException", e);
		} catch (SecurityException e) {
			LOG.error("SecurityException", e);
		} catch (IllegalAccessException e) {
			LOG.error("IllegalAccessException", e);
		} catch (IllegalArgumentException e) {
			LOG.error("IllegalArgumentException", e);
		} catch (InvocationTargetException e) {
			LOG.error("InvocationTargetException", e);
		}
	}

}
