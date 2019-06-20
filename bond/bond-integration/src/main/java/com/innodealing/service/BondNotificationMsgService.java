package com.innodealing.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.innodealing.amqp.SenderService;
import com.innodealing.bond.param.DefaultBondParam;
import com.innodealing.engine.jdbc.NotificationMsgDao;
import com.innodealing.engine.jpa.dm.BondNotificationMsgRepository;
import com.innodealing.engine.mongo.bond.BondNotificationMsgDocRepository;
import com.innodealing.engine.mongo.bond.FinanceAlertInfoRepository;
import com.innodealing.json.portfolio.BondMaturityJson;
import com.innodealing.json.portfolio.ImpliedRatingJson;
import com.innodealing.model.dm.bond.BondNotificationMsg;
import com.innodealing.model.mongo.dm.BondCredRatingDoc;
import com.innodealing.model.mongo.dm.BondDetailDoc;
import com.innodealing.model.mongo.dm.BondNotificationMsgDoc;
import com.innodealing.model.mongo.dm.FinanceAlertInfoDoc;
import com.innodealing.model.mongo.dm.IssCredRatingDoc;
import com.innodealing.model.mongo.dm.IssPdRatingDoc;
import com.innodealing.util.SafeUtils;

@Service
public class BondNotificationMsgService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BondNotificationMsgService.class);

	private @Autowired BondNotificationMsgRepository bondNotificationMsgRepository;

	private @Autowired BondNotificationMsgDocRepository bondNotificationMsgDocRepository;

	private @Autowired NotificationMsgDao notificationMsgDao;

	private @Autowired FinanceAlertInfoRepository financeAlertInfoRepository;

	private @Autowired ApplicationEventPublisher publisher;

	private @Autowired MongoOperations mongoOperations;

	private @Autowired SenderService senderService;

	public Long saveBondMaturityDtoMsg() {
		int count = notificationMsgDao.getMaturityCount();
		int limit = 1000;
		int num = count / limit + 1;

		LOGGER.info("saveBondMaturityDtoMsg count:" + count + ", num:" + num + ", limit:" + limit);

		ExecutorService exec = Executors.newCachedThreadPool();
		ArrayList<Integer> pages = new ArrayList<Integer>();
		// for debug
		// num = 1;
		for (int page = 0; page < num; page++) {
			pages.add(page);
		}

		for (Integer workingPage : pages) {
			exec.execute(new Runnable() {
				@Override
				public void run() {
					pubBondMaturityDtoMsgByPage(limit, workingPage);
				}
			});
		}

		exec.shutdown();
		try {
			exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			LOGGER.error("等待任务完成中发生异常 ", e);
			e.printStackTrace();
		}
		LOGGER.info("saveBondMaturityDtoMsg end...");
		return 1L;
	}

	private void pubBondMaturityDtoMsgByPage(int limit, int index) {
		List<BondMaturityJson> list = notificationMsgDao.findMaturity(index * limit, limit);
		list.stream().forEach(maturityJson -> {
			if (null != maturityJson.getTheoDiffdays() && maturityJson.getTheoDiffdays() > 0) {
				if(!isExpiredBondDetail(maturityJson.getBondUniCode())){
					
					senderService.sendBondMaturity2Rabbitmq(JSON.toJSONString(maturityJson));
				}
			}
		});
	}

	private boolean isExpiredBondDetail(Long bondUniCode) {
		boolean result = true;
		BondDetailDoc obj = mongoOperations.findOne(new Query(Criteria.where("bondUniCode").is(bondUniCode)
				.and("currStatus").is(1).and("issStaPar").is(1)), BondDetailDoc.class);
		if (null != obj) {
			result = false;
		}
		return result;
	}

	public Long saveBondNotificationMsgDoc(BondNotificationMsgDoc bondNotificationMsgDoc) {
		Long resId = 0L;
		BondNotificationMsgDoc resObj = bondNotificationMsgDocRepository.save(bondNotificationMsgDoc);
		if (null != resObj) {
			resId = resObj.getId();
		}
		return resId;
	}

	@Transactional
	public Long saveBondNotificationMsg(BondNotificationMsg bnMsg) {
		Long resId = 0L;
		BondNotificationMsg resObj = bondNotificationMsgRepository.save(bnMsg);
		if (null != resObj) {
			BondNotificationMsgDoc bnMsgDoc = new BondNotificationMsgDoc();
			BeanUtils.copyProperties(resObj, bnMsgDoc);
			resId = saveBondNotificationMsgDoc(bnMsgDoc);
		}
		return resId;
	}

	// 构建财务预警数据
	public String saveFinancialAlertInfo() {
		LOGGER.info("saveFinancialAlertInfo begin");
		financeAlertInfoRepository.deleteAll();

		int limit = 1000;
		int count = notificationMsgDao.getFinancialAlertInfoCount();
		int num = count / limit + 1;
		for (int i = 0; i < num; i++) {
			List<FinanceAlertInfoDoc> entites = notificationMsgDao.findFinancialAlertInfo(i * limit, limit);
			mongoOperations.insert(entites, FinanceAlertInfoDoc.class);
		}
		LOGGER.info("saveFinancialAlertInfo end");
		return "done";
	}

	// 构建财务预警消息
	@Deprecated
	public String saveFinancialAlertMsg() {
		int count = SafeUtils.getInt(mongoOperations.count(new Query(), FinanceAlertInfoDoc.class));
		int limit = 1000;
		int sum = count / limit + 1;
		LOGGER.info("saveFinancialAlertMsg count:" + count + ",sum:" + sum + ",limit:" + limit);

		Map<Long, FinanceAlertInfoDoc> maps = new HashMap<>();
		List<FinanceAlertInfoDoc> lastFinInfos = notificationMsgDao.findLastFinancialAlertInfos();
		lastFinInfos.stream().forEach(lastFinInfo -> {
			maps.put(lastFinInfo.getComUniCode(), lastFinInfo);
		});

		ExecutorService exec = Executors.newCachedThreadPool();
		ArrayList<Integer> pages = new ArrayList<Integer>();
		for (int page = 0; page < sum; page++) {
			pages.add(page);
		}

		for (Integer workingPage : pages) {
			exec.execute(new Runnable() {
				@Override
				public void run() {
					saveFinancialAlertMsgByPage(limit, workingPage, maps);
				}
			});
		}

		exec.shutdown();

		try {
			exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			LOGGER.error("等待任务完成中发生异常 ", e);
			e.printStackTrace();
		}

		LOGGER.info("saveFinancialAlertMsg end...");
		return "done";
	}

	@Deprecated
	private void saveFinancialAlertMsgByPage(int limit, int page, Map<Long, FinanceAlertInfoDoc> maps) {
		LOGGER.info("saveFinancialAlertMsgByPage current page:" + page);

		Query query = new Query();
		PageRequest request = new PageRequest(page, limit);
		query.with(request);

		List<FinanceAlertInfoDoc> finaiDocs = mongoOperations.find(query, FinanceAlertInfoDoc.class);

		finaiDocs.stream().forEach(finaiDoc -> {
			FinanceAlertInfoDoc lastfinaInfoDoc = null;
			if (maps.containsKey(finaiDoc.getComUniCode())) {
				lastfinaInfoDoc = maps.get(finaiDoc.getComUniCode());
			}

			if (null != lastfinaInfoDoc && lastfinaInfoDoc.getFinDate().after(finaiDoc.getFinDate())) {
				//senderService.sendBondFinindic2Rabbitmq(JSON.toJSONString(lastfinaInfoDoc));
			}
		});
	}

	// 构建債券違約事件消息
	public String saveDefaultBondMsg(DefaultBondParam params) {
		LOGGER.info("saveDefaultBondMsg params : BondCode:" + params.getBondCode() + ",BondUniCode:"
				+ params.getBondUniCode() + ",DefaultDate:" + params.getDefaultDate() + ",EventContent:"
				+ params.getEventContent());

		String msgContent = params.getDefaultDate() + "," + params.getEventContent();
		BondNotificationMsg bondMsg = new BondNotificationMsg();
		bondMsg.setBondId(params.getBondUniCode());
		bondMsg.setEventType(7);
		bondMsg.setMsgContent(msgContent);
		bondMsg.setCreateTime(new Date());
		publisher.publishEvent(bondMsg);

		return "done";
	}

	// 构建债项评级的备份数据
	public String saveBondcredData() {
		LOGGER.info("saveBondcredData is begin..");
		if (mongoOperations.collectionExists("bond_cred_rating")) {
			mongoOperations.remove(new Query(), "bond_cred_rating");
		}
		int limit = 1000;
		int count = notificationMsgDao.getBondCredRatingCount();
		int num = count / limit + 1;

		ExecutorService pool = Executors.newFixedThreadPool(10);
		ArrayList<Integer> pages = new ArrayList<Integer>();
		for (int page = 0; page < num; page++) {
			pages.add(page);
		}

		for (Integer workingPage : pages) {
			pool.execute(new Runnable() {
				@Override
				public void run() {
					List<BondCredRatingDoc> entites = notificationMsgDao.findBondCredRating(workingPage * limit, limit);
					mongoOperations.insert(entites, BondCredRatingDoc.class);
				}
			});
		}

		pool.shutdown();
		try {
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			LOGGER.error("等待任务完成中发生异常 ", e);
			e.printStackTrace();
		}

		LOGGER.info("saveBondcredData end..");

		return "done";
	}

	public String saveBondcredMsg() {
		LOGGER.info("saveBondcredMsg is begin..");
		int count = notificationMsgDao.getBondCredRatingCount();
		int limit = 5000;
		int sum = count / limit + 1;
		LOGGER.info("saveBondcredMsg count:" + count + ",sum:" + sum + ",limit:" + limit);

		ExecutorService pool = Executors.newCachedThreadPool();
		ArrayList<Integer> pages = new ArrayList<Integer>();
		for (int page = 0; page < sum; page++) {
			pages.add(page);
		}

		for (Integer workingPage : pages) {
			pool.execute(new Runnable() {
				@Override
				public void run() {
					saveBondCredMsgByPage(limit, workingPage);
				}
			});
		}

		pool.shutdown();
		try {
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			LOGGER.error("等待任务完成中发生异常 ", e);
			e.printStackTrace();
		}

		LOGGER.info("saveBondcredMsg end..");
		return "done";
	}

	private void saveBondCredMsgByPage(int limit, int page) {
		LOGGER.info("saveBondcredMsg current page:" + page + ",limit:" + limit);
		List<BondCredRatingDoc> entites = notificationMsgDao.findBondCredRating(page * limit, limit);

		entites.stream().forEach(lastBcrDoc -> {
			if (!isExpiredBondDetail(lastBcrDoc.getBondUniCode())) {
			
				senderService.sendBondCredrat2Rabbitmq(JSON.toJSONString(lastBcrDoc));
			}
		});
	}

	// 构建债项主体评级的备份数据
	public String saveIsscredData() {
		LOGGER.info("saveIsscredData is begin..");
		if (mongoOperations.collectionExists("iss_cred_rating")) {
			mongoOperations.remove(new Query(), "iss_cred_rating");
		}
		int limit = 1000;
		int count = notificationMsgDao.getIssCredRatingCount();
		int num = count / limit + 1;
		for (int i = 0; i < num; i++) {
			List<IssCredRatingDoc> entites = notificationMsgDao.findIssCredRating(i * limit, limit);
			mongoOperations.insert(entites, IssCredRatingDoc.class);
		}

		LOGGER.info("saveIsscredData end..");
		return "done";
	}

	public String saveIsscredMsg() {
		LOGGER.info("saveIsscredMsg is begin..");
		int count = notificationMsgDao.getIssCredRatingCount();
		int limit = 1000;
		int sum = count / limit + 1;

		LOGGER.info("saveIsscredMsg count:" + count + ",sum:" + sum + ",limit:" + limit);
		ExecutorService pool = Executors.newCachedThreadPool();
		ArrayList<Integer> pages = new ArrayList<Integer>();
		for (int page = 0; page < sum; page++) {
			pages.add(page);
		}
		for (Integer workingPage : pages) {
			pool.execute(new Runnable() {
				@Override
				public void run() {
					saveIssCredMsgByPage(limit, workingPage);
				}
			});
		}
		pool.shutdown();
		try {
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			LOGGER.error("等待任务完成中发生异常 ", e);
			e.printStackTrace();
		}
		LOGGER.info("saveIsscredMsg end..");

		return "done";
	}

	private void saveIssCredMsgByPage(int limit, int page) {
		LOGGER.info("saveIssCredMsgByPage page:" + page);
		List<IssCredRatingDoc> entites = notificationMsgDao.findIssCredRating(page * limit, limit);

		entites.stream().forEach(lastIcrDoc -> {
			senderService.sendBondIsscredrat2Rabbitmq(JSON.toJSONString(lastIcrDoc));
		});
	}

	// 构建债项主体量化风险的备份数据
	public String saveIssPdData() {
		LOGGER.info("saveIssPdData is begin..");
		if (mongoOperations.collectionExists("iss_pd_rating")) {
			mongoOperations.remove(new Query(), "iss_pd_rating");
		}
		List<IssPdRatingDoc> issPdRatingDocs = notificationMsgDao.findIssPdRating();
		if (null != issPdRatingDocs) {
			LOGGER.info("saveIssPdData insert..");
			mongoOperations.insert(issPdRatingDocs, IssPdRatingDoc.class);
		}

		LOGGER.info("saveIssPdData end..");

		return "done";
	}

	public String saveIssPdMsg() {
		LOGGER.info("saveIssPdMsg is begin..");
		int count = notificationMsgDao.getIssPdRatingCount();
		int limit = 1000;
		int sum = count / limit + 1;

		LOGGER.info("saveIssPdMsg count:" + count + ",sum:" + sum + ",limit:" + limit);

		ExecutorService pool = Executors.newCachedThreadPool();
		ArrayList<Integer> pages = new ArrayList<Integer>();
		for (int page = 0; page < sum; page++) {
			pages.add(page);
		}
		for (Integer workingPage : pages) {
			pool.execute(new Runnable() {
				@Override
				public void run() {
					saveIssPdMsgByPage(limit, workingPage);
				}
			});
		}
		pool.shutdown();
		try {
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			LOGGER.error("等待任务完成中发生异常 ", e);
			e.printStackTrace();
		}
		LOGGER.info("saveIssPdMsg end..");

		return "done";
	}

	private void saveIssPdMsgByPage(int limit, int page) {
		LOGGER.info("saveIssPdMsgByPage current page:" + page + ",limit:" + limit);
		List<IssPdRatingDoc> iprDocs = notificationMsgDao.findIssPdRating(page * limit, limit);

		iprDocs.stream().forEach(issPdRatingDoc -> {
			senderService.sendBondIssPdrat2Rabbitmq(JSON.toJSONString(issPdRatingDoc));
		});
	}

	public static Calendar convertStringToDay(String date) throws ParseException {
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(SafeUtils.DATE_FORMAT);
			cal.setTime(sdf.parse(date));
			cal.set(Calendar.HOUR_OF_DAY, 0);
			return cal;
		} catch (Exception e) {
			return null;
		}
	}

	public String saveImpliedRatingMsg() {
		Map<String, String> datemap = new HashMap<>();
		List<String> dates = notificationMsgDao.getLastTwodaysInImpliedRatingHist();
		if (null != dates && dates.size() > 1) {
			datemap.put("beginDate", dates.get(0));
			datemap.put("endDate", dates.get(1));
			// 查询慢的情况下，需要优化分页查询
			List<ImpliedRatingJson> impliedRatings = notificationMsgDao
					.findImpliedRatingHistData(datemap.get("beginDate").toString(), datemap.get("endDate").toString());
			if (null != impliedRatings && impliedRatings.size() > 0) {
				for (ImpliedRatingJson impliedRatingJson : impliedRatings) {
					if (!isExpiredBondDetail(impliedRatingJson.getBondUniCode())) {
						
						senderService.sendBondImpliedrat2Rabbitmq(JSON.toJSONString(impliedRatingJson));
					}
				}
			}
		}

		return "done";
	}

	public Long saveBondExerData() {
		int count = notificationMsgDao.getExerDataCount();
		int limit = 1000;
		int num = count / limit + 1;

		LOGGER.info("saveBondExerData count:" + count + ", num:" + num + ", limit:" + limit);

		ExecutorService exec = Executors.newFixedThreadPool(5);
		ArrayList<Integer> pages = new ArrayList<Integer>();

		for (int page = 0; page < num; page++) {
			pages.add(page);
		}

		for (Integer workingPage : pages) {
			exec.execute(new Runnable() {
				@Override
				public void run() {
					pubBondExerMsgByPage(limit, workingPage);
				}
			});
		}

		exec.shutdown();
		try {
			exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			LOGGER.error("等待任务完成中发生异常 ", e);
			e.printStackTrace();
		}
		LOGGER.info("saveBondExerData end...");
		return 1L;
	
	}

	protected void pubBondExerMsgByPage(int limit, int index) {
		List<BondMaturityJson> list = notificationMsgDao.findExerData(index * limit, limit);
		list.stream().forEach(maturityJson -> {
			if (!isExpiredBondDetail(maturityJson.getBondUniCode())) {
				
				senderService.sendBondMaturity2Rabbitmq(JSON.toJSONString(maturityJson));
			}
		});
	}

}
