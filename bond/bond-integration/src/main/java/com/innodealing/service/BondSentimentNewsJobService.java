package com.innodealing.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.innodealing.engine.mongo.bond.BondComInfoRepository;
import com.innodealing.engine.mongo.bond.BondSentimentDistinctRepository;
import com.innodealing.engine.mongo.bond.BondSentimentTopRepository;
import com.innodealing.engine.mongo.bond.BondSentimentVersionRepository;
import com.innodealing.engine.redis.RedisUtil;
import com.innodealing.model.mongo.dm.BondSentimentDistinctDoc;
import com.innodealing.model.mongo.dm.BondSentimentDoc;
import com.innodealing.model.mongo.dm.BondSentimentMergeDoc;
import com.innodealing.util.MD5Util;
import com.innodealing.util.StringUtils;

/**
 * top:2 舆情数据处理
 * 
 * @author liuqi
 */
@Component
public class BondSentimentNewsJobService {

	private static final Logger LOG = LoggerFactory.getLogger(BondSentimentNewsJobService.class);

	// 版本号bond_sentiment_version.ID
	private final Long ID3 = 3L;

	private final Long SIZE = 50000L;

	private final Long PAGESIZE = 500000L;

	protected @Autowired MongoOperations mongoOperations;

	protected @Autowired BondSentimentTopRepository bondSentimentTopRepository;

	protected @Autowired JdbcTemplate jdbcTemplate;

	protected @Autowired BondComInfoRepository bondComInfoRepository;

	protected @Autowired BondSentimentVersionRepository bondVersionRepository;

	protected @Autowired RedisUtil redisUtil;

	protected @Autowired BondSentimentDistinctRepository bondSentimentDistinctRepository;

	protected @Autowired ApplicationEventPublisher eventMsgPublisher;

	protected @Autowired BondSentimentJobService bondSentimentJobService;

	private Boolean flay = false;

	public String syncIntegration(Boolean flay) {
		this.flay = flay;
		synchronized (BondSentimentNewsJobService.class) {
			return findListByDateOrder();
		}
	}

	public String findListByDateOrder() {

		LOG.info("============SentimentNews start===========");

		// 取历史版本号
		Long version = bondSentimentJobService.getVersion(ID3);

		if (version == null) {
			version = 0L;
		}

		Long newVersion = 0L;

		// 得到当前版本号
		try {
			newVersion = getMergeMaxId();
			LOG.info("==============newVersion=" + newVersion);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			LOG.error("数据合并-->取当前版本发生错误,历史版本:" + version);
			return null;
		}

		if (newVersion.equals(version)) {
			LOG.info("舆情数据未变..");
			return null;
		}

		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
		List<Callable<String>> taskList = new ArrayList<Callable<String>>();

		Long pageCount = 1L;
		if ((newVersion - version) > PAGESIZE) {
			pageCount = (newVersion - version) % PAGESIZE == 0 ? (newVersion - version) / PAGESIZE
					: ((newVersion - version) / PAGESIZE) + 1;
		}

		final Long counts = pageCount;
		final Long newNum = newVersion;
		final Long oldNum = version;

		LOG.info("当前版本号:" + version + ",最大版本号:" + newVersion);

		for (long i = 0; i < pageCount; i++) {
			String num = String.valueOf(i);
			Callable<String> c = new Callable<String>() {
				@Override
				public String call() throws Exception {
					LOG.info(Thread.currentThread().getName() + "main start ...");
					if (counts == 1L) {
						find(newNum, oldNum);
					} else {
						find(((Long.parseLong(num) + 1) * PAGESIZE) >= newNum ? newNum
								: ((Long.parseLong(num) + 1) * PAGESIZE), Long.parseLong(num) * PAGESIZE);
					}
					return "success";
				}
			};
			taskList.add(c);
		}

		try {
			fixedThreadPool.invokeAll(taskList);
			bondSentimentJobService.updateVersion(ID3, newVersion);
			LOG.info("舆情数据更新成功..");
		} catch (Exception e) {
			e.printStackTrace();
		}

		LOG.info("============SentimentNews end===========");

		return null;
	}

	private void find(Long newVersion, Long version) throws Exception {

		for (Long i = version; i < newVersion; i += SIZE) {
			Long i1 = System.currentTimeMillis();

			LOG.info(i.toString());

			List<BondSentimentDistinctDoc> list = null;

			// 最新数据
			try {
				list = handleSentimentMerge(i, i + SIZE >= newVersion ? newVersion : i + SIZE);
			} catch (Exception e) {
				LOG.error("取数据出现错误!当前版本号:" + version + ",最大版本号:" + newVersion, e);
				throw new Exception("取数据出现错误!当前版本号:" + version + ",最大版本号:" + newVersion);
			}
			if (null != list && !list.isEmpty()) {

				try {
					// 数据分组
					Map<String, List<BondSentimentDistinctDoc>> resultMap = group(list);
					// 入库
					excute(resultMap);
					Long i2 = System.currentTimeMillis();
					LOG.info("sum:" + (i2 - i1) / 1000);
				} catch (Exception e) {
					LOG.error("数据分组出现错误!当前版本号:" + version + ",最大版本号:" + newVersion, e);
					throw new Exception("数据分组出现错误!当前版本号:" + version + ",最大版本号:" + newVersion);
				}

			}

		}
	}

	/**
	 * 数据查询
	 * 
	 * @param str
	 */
	public List<BondSentimentDistinctDoc> handleSentimentMerge(Long version, Long newVersion) {

		LOG.info("=====================查询version=" + version + ",newVersion=" + newVersion);

		List<Criteria> subCriterias = new ArrayList<Criteria>();
		subCriterias.add(Criteria.where("reduplicated").is(0));
		Criteria criteria = new Criteria();
		if (!StringUtils.isEmpty(version) && !StringUtils.isEmpty(newVersion)) {
			subCriterias.add(Criteria.where("index").gt(version).lte(newVersion));
		} else {
			return null;
		}
		criteria.andOperator(subCriterias.toArray(new Criteria[subCriterias.size()]));

		Sort sort = new Sort(Sort.Direction.ASC, "index");
		Query query = new Query();
		query.addCriteria(criteria).with(sort);
		query.fields().include("comUniCode").include("sentiment").include("title").include("ruleName")
				.include("comChiName").include("pubDate").include("url").include("index").include("serialNo");
		Long i1 = System.currentTimeMillis();
		List<BondSentimentMergeDoc> list = mongoOperations.find(query, BondSentimentMergeDoc.class);
		Long i2 = System.currentTimeMillis();
		LOG.info("find:" + (i2 - i1) / 1000);

		List<BondSentimentDistinctDoc> resultlist = new ArrayList<BondSentimentDistinctDoc>();

		BondSentimentDistinctDoc doc = null;

		if (null != list && !list.isEmpty()) {
			for (BondSentimentDoc vo : list) {
				doc = new BondSentimentDistinctDoc();
				BeanUtils.copyProperties(vo, doc);
				resultlist.add(doc);
			}
		}
		return resultlist;

	}

	private void excute(Map<String, List<BondSentimentDistinctDoc>> map) {
		ExecutorService EXEC = Executors.newFixedThreadPool(10);
		List<Callable<String>> tasks = new ArrayList<Callable<String>>();

		Iterator<String> keys = map.keySet().iterator();

		while (keys.hasNext()) {
			List<BondSentimentDistinctDoc> list = map.get(keys.next());
			Callable<String> c = new Callable<String>() {
				@Override
				public String call() throws Exception {
					LOG.info(Thread.currentThread().getName() + " start ...");
					dataMerge(list);
					return "success";
				}
			};
			tasks.add(c);
		}
		try {
			EXEC.invokeAll(tasks);
			LOG.info(" end ...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 数据分组
	 * 
	 * @param list
	 * @throws Exception
	 */
	private Map<String, List<BondSentimentDistinctDoc>> group(List<BondSentimentDistinctDoc> list) throws Exception {

		Map<String, List<BondSentimentDistinctDoc>> resultMap = new HashMap<String, List<BondSentimentDistinctDoc>>();

		List<BondSentimentDistinctDoc> reusltlits = null;

		for (int i = 0; i < list.size(); i++) {
			BondSentimentDistinctDoc vo = list.get(i);
			reusltlits = resultMap.get(vo.getComUniCode()) != null ? resultMap.get(vo.getComUniCode())
					: new ArrayList<BondSentimentDistinctDoc>();
			reusltlits.add(vo);
			resultMap.put(vo.getComUniCode(), reusltlits);
		}

		return resultMap;
	}

	/**
	 * 数据合并
	 * 
	 * @param list
	 */
	private void dataMerge(List<BondSentimentDistinctDoc> list) {

		LOG.info(Thread.currentThread().getName() + "==============开始合并===============");

		if (null == list || list.isEmpty()) {
			return;
		}

		// 需要通知的舆情
		Map<String, BondSentimentDistinctDoc> mapCache = new HashMap<String, BondSentimentDistinctDoc>();

		Map<String, BondSentimentDistinctDoc> map = new HashMap<String, BondSentimentDistinctDoc>();

		for (BondSentimentDistinctDoc newvo : list) {

			// 当前所有数据临时去重
			if ( // title为空的舍弃
			StringUtils.isEmpty(newvo.getTitle())
					// 带情绪的是否有相同的,有的话舍弃当前的保留带情绪的
					|| redisUtil.exists("sentimentMerge" + MD5Util.getMD5(newvo.merge()))) {
				map.remove(newvo.merge());
				mapCache.remove(newvo.merge());
				continue;
			}

			newvo.setId(MD5Util.getMD5(newvo.merge() + newvo.getSerialNo()));

			if (map.get(newvo.merge()) != null) {
				newvo.setImportant(0);
				map.remove(newvo.merge());
				map.put(newvo.merge(), newvo);
				continue;
			}

			newvo.setImportant(0);
			map.put(newvo.merge(), newvo);
			mapCache.put(newvo.merge(), newvo);

		}

		List<BondSentimentDistinctDoc> results = new ArrayList<BondSentimentDistinctDoc>();

		for (String str : map.keySet()) {
			BondSentimentDistinctDoc vo = map.get(str);
			results.add(map.get(str));
			// 舆情数据的通知
			if (mapCache.get(str) != null && flay) {
				eventMsgPublisher.publishEvent(vo);
			}
		}

		mongoOperations.insertAll(results);

		LOG.info(Thread.currentThread().getName() + "==============结束合并===============");
	}

	/**
	 * 得到最大的自增ID
	 * 
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private Long getMergeMaxId() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		PageRequest request = new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "index"));
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("reduplicated").is(0));
		Query query = new Query();
		query.addCriteria(criteria).with(request);
		query.fields().include("index");
		BondSentimentMergeDoc vo = mongoOperations.findOne(query, BondSentimentMergeDoc.class);
		return vo != null ? vo.getIndex() : null;
	}

}
