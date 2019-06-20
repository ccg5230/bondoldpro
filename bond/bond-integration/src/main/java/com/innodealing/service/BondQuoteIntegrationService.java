package com.innodealing.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.innodealing.engine.jdbc.bond.BondQuoteDao;
import com.innodealing.engine.jdbc.im.IsHolidayDao;
import com.innodealing.engine.mongo.bond.BondDealDataDocRepository;
import com.innodealing.engine.redis.RedisUtil;
import com.innodealing.model.dm.bond.BondDeal;
import com.innodealing.model.mongo.dm.BondBestQuoteNetpriceDoc;
import com.innodealing.model.mongo.dm.BondBestQuoteYieldDoc;
import com.innodealing.model.mongo.dm.BondDealDataDoc;
import com.innodealing.model.mongo.dm.BondQuoteDoc;
import com.innodealing.model.mongo.dm.BondSingleComparisonDoc;
import com.innodealing.model.mongo.dm.WorkingdateSixdaysDoc;
import com.innodealing.model.mongo.dm.bond.quote.analyse.BondQuoteHistorycurveDoc;
import com.innodealing.model.mongo.dm.bond.quote.analyse.BondQuoteTodaycurveDoc;
import com.innodealing.util.SafeUtils;

/**
 * @author stephen.ma
 * @date 2016年9月6日
 * @clasename BondQuoteIntegrationService.java
 * @decription TODO
 */

@Service
public class BondQuoteIntegrationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BondQuoteIntegrationService.class);

	private @Autowired MongoOperations mongoOperations;

	private @Autowired BondQuoteDao bondQuoteDao;

	private @Autowired IsHolidayDao isHolidayDao;

	private @Autowired BondDealDataDocRepository bondDealDataDocRepository;

	private @Autowired ApplicationEventPublisher publisher;
	
	private @Autowired RedisUtil redisUtil;
	
	// @Transactional
	public synchronized void saveBondBestQuoteDoc() {
		LOGGER.info("saveBondBestQuoteDoc is begining..");
		long bengin = System.currentTimeMillis();
		
        Date today = new Date();
        
        new Thread(new Runnable() {
            @Override
            public void run() {
            	handleBondBestQuoteInYield(today);
            }
        }).start();
        
        new Thread(new Runnable() {
            @Override
            public void run() {
        		handleBondBestQuoteInNetprice(today);
            }
        }).start();
        
		long end = System.currentTimeMillis();
		LOGGER.info("saveBondBestQuoteDoc, the time spent is " + (end - bengin));
	}

	private void handleBondBestQuoteInYield(Date today) {
		List<BondBestQuoteYieldDoc> bbqyDocList = new ArrayList<>();

		List<Map<String, Object>> listYield = bondQuoteDao.findBondBestQuoteInYield(today);
		listYield.parallelStream().forEach(result -> {
			BondBestQuoteYieldDoc bbqyDoc = new BondBestQuoteYieldDoc();

			Long bondUniCode = (Long) result.get("bondUniCode");
			String bondShortName = (String) result.get("bondShortName");
			BigDecimal bidPrice = new BigDecimal(0);
			BigDecimal ofrPrice = new BigDecimal(0);
			if (null != result.get("bidPrice")) {
				bidPrice = new BigDecimal(result.get("bidPrice").toString());
			}
			if (null != result.get("ofrPrice")) {
				ofrPrice = new BigDecimal(result.get("ofrPrice").toString());
			}
			BigDecimal bidVol = new BigDecimal(0);
			BigDecimal ofrVol = new BigDecimal(0);
			if (null != result.get("bidVol")) {
				bidVol = new BigDecimal(result.get("bidVol").toString());
			}
			if (null != result.get("ofrVol")) {
				ofrVol = new BigDecimal(result.get("ofrVol").toString());
			}

			bbqyDoc.setBidderName(bondShortName);
			bbqyDoc.setBidPrice(bidPrice);
			bbqyDoc.setBidVol(bidVol);
			bbqyDoc.setBondShortName(bondShortName);
			bbqyDoc.setBondUniCode(bondUniCode);
			bbqyDoc.setOfrPrice(ofrPrice);
			bbqyDoc.setOfrVol(ofrVol);
			bbqyDoc.setSendTime(SafeUtils.convertDateToString(today, SafeUtils.DATE_FORMAT));

			bbqyDocList.add(bbqyDoc);
			publisher.publishEvent(bbqyDoc); 
		});

		if (null != bbqyDocList && bbqyDocList.size() > 0) {
			mongoOperations.remove(new Query(), BondBestQuoteYieldDoc.class);
			mongoOperations.insert(bbqyDocList, BondBestQuoteYieldDoc.class);
		}
	}

	private void handleBondBestQuoteInNetprice(Date today) {
		List<BondBestQuoteNetpriceDoc> bbqnDocList = new ArrayList<>();
		List<Map<String, Object>> listNetprice = bondQuoteDao.findBondBestQuoteInNetprice(today);
		listNetprice.parallelStream().forEach(result -> {
			BondBestQuoteNetpriceDoc bbqnDoc = new BondBestQuoteNetpriceDoc();

			Long bondUniCode = (Long) result.get("bondUniCode");
			String bondShortName = (String) result.get("bondShortName");
			BigDecimal bidPrice = new BigDecimal(0.00);
			BigDecimal ofrPrice = new BigDecimal(0.00);
			if (null != result.get("bidPrice")) {
				bidPrice = new BigDecimal(result.get("bidPrice").toString());
			}
			if (null != result.get("ofrPrice")) {
				ofrPrice = new BigDecimal(result.get("ofrPrice").toString());
			}
			BigDecimal bidVol = new BigDecimal(0);
			BigDecimal ofrVol = new BigDecimal(0);
			if (null != result.get("bidVol")) {
				bidVol = new BigDecimal(result.get("bidVol").toString());
			}
			if (null != result.get("ofrVol")) {
				ofrVol = new BigDecimal(result.get("ofrVol").toString());
			}

			bbqnDoc.setBidderName(bondShortName);
			bbqnDoc.setBidPrice(bidPrice);
			bbqnDoc.setBidVol(bidVol);
			bbqnDoc.setBondShortName(bondShortName);
			bbqnDoc.setBondUniCode(bondUniCode);
			bbqnDoc.setOfrPrice(ofrPrice);
			bbqnDoc.setOfrVol(ofrVol);
			bbqnDoc.setSendTime(SafeUtils.convertDateToString(today, SafeUtils.DATE_FORMAT));

			bbqnDocList.add(bbqnDoc);
		});
		
		if (null != bbqnDocList && bbqnDocList.size() > 0) {
			mongoOperations.remove(new Query(), BondBestQuoteNetpriceDoc.class);
			mongoOperations.insert(bbqnDocList, BondBestQuoteNetpriceDoc.class);
		}
	}

	// @Transactional
	public synchronized void saveBondQuoteTodaycurveDoc() {
		long bengin = System.currentTimeMillis();

		List<BondQuoteTodaycurveDoc> bqtDocList = new ArrayList<>();

		List<Map<String, Object>> mapList = bondQuoteDao.findBondQuoteTodaycurve(new Date());
		mapList.forEach(mapObj -> {
			BondQuoteTodaycurveDoc bqtDoc = new BondQuoteTodaycurveDoc();

			Long bondUniCode = (Long) mapObj.get("bondUniCode");
			String bondShortName = (String) mapObj.get("bondShortName");
			Integer priceUnit = (Integer) mapObj.get("priceUnit");
			Integer side = (Integer) mapObj.get("side");
			BigDecimal bondPrice = (BigDecimal) mapObj.get("bondPrice");
			BigDecimal bondVol = (BigDecimal) mapObj.get("bondVol");
			BigDecimal bondRate = (BigDecimal) mapObj.get("bondRate");
			Date sendTime = (Date) mapObj.get("sendTime");

			bqtDoc.setBondPrice(bondPrice);
			bqtDoc.setBondRate(bondRate);
			bqtDoc.setBondShortName(bondShortName);
			bqtDoc.setBondUniCode(bondUniCode);
			bqtDoc.setBondVol(bondVol);
			bqtDoc.setPriceUnit(priceUnit);
			bqtDoc.setSendDateFormat(SafeUtils.convertDateToString(sendTime, SafeUtils.DATE_FORMAT));
			bqtDoc.setSendTime(SafeUtils.convertDateToString(sendTime, SafeUtils.DATE_TIME_FORMAT1));
			bqtDoc.setSide(side);

			bqtDocList.add(bqtDoc);
		});

		if (null != bqtDocList && bqtDocList.size() > 0) {
			mongoOperations.remove(new Query(), BondQuoteTodaycurveDoc.class);
			mongoOperations.insert(bqtDocList, BondQuoteTodaycurveDoc.class);
		}

		long end = System.currentTimeMillis();

		LOGGER.info("saveBondQuoteTodaycurveDoc, the time spent is " + (end - bengin));
	}

	// @Transactional
	public synchronized void saveBondQuoteTodaycurveDocInMongo() {
		LOGGER.info("saveBondQuoteTodaycurveDocInMongo is begining..");
		long bengin = System.currentTimeMillis();
		List<BondQuoteTodaycurveDoc> bqtDocList = new ArrayList<>();

		Query query = new Query();
		String todayStr = SafeUtils.convertDateToString(new Date(), SafeUtils.DATE_FORMAT);
		query.addCriteria(Criteria.where("status").is(1).and("priceUnit").is(1).and("sendTimeFormat").is(todayStr).and("bondUniCode").gt(0));
		query.with(new Sort(Sort.Direction.DESC, "sendTime"));
		List<BondQuoteDoc> bqDocList = mongoOperations.find(query, BondQuoteDoc.class);
		
		//如果出现 Sort operation used more than the maximum的问题，可用该方案，查询有会比较慢
		/*
		AggregationOperation match = Aggregation.match(Criteria.where("status").is(1).and("priceUnit").is(1).and("sendTimeFormat")
				.is(SafeUtils.convertDateToString(today, SafeUtils.DATE_FORMAT)).and("bondUniCode").gt(0));
		AggregationOperation sort = Aggregation.sort(new Sort(Sort.Direction.DESC, "sendTime"));
		Aggregation aggregation = Aggregation.newAggregation(match, sort).withOptions(Aggregation.newAggregationOptions().allowDiskUse(true).build());
		
		AggregationResults<BondQuoteDoc> result = mongoOperations.aggregate(aggregation, "bond_quotes", BondQuoteDoc.class);

		List<BondQuoteDoc> bqDocList = result.getMappedResults();
		*/
		
		LOGGER.info("saveBondQuoteTodaycurveDocInMongo bqDocList.size:"+(bqDocList == null ? 0:bqDocList.size())+",time cost:"+(System.currentTimeMillis()-bengin));

		bqDocList.forEach(bqDoc -> {
			LOGGER.debug("saveBondQuoteTodaycurveDocInMongo bqDoc.BondCode"+bqDoc.getBondCode()+",BondUniCode:"+bqDoc.getBondUniCode());
			BondQuoteTodaycurveDoc bqtcDoc = new BondQuoteTodaycurveDoc();
	        String todayend = todayStr+" "+"23:59:59";
			
			PageRequest request = new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "createTime"));
			Query bdQuery = new Query();
			bdQuery.addCriteria(Criteria.where("bondUniCode").is(bqDoc.getBondUniCode()).and("createTime").lt(todayend)).with(request);
			
			List<BondDealDataDoc> bondDealDataList = mongoOperations.find(bdQuery, BondDealDataDoc.class);
			if (null != bondDealDataList && bondDealDataList.size() > 0) {
				BondDealDataDoc bondDealDataDoc = bondDealDataList.get(0);
				String createDate = bondDealDataDoc.getCreateTime().split(" ")[0];
				if (todayStr.equals(createDate)) {
					bqtcDoc.setBondRate(bondDealDataDoc.getBondRate() == null ? new BigDecimal(0) : bondDealDataDoc.getBondRate());
					bqtcDoc.setBondWeightedRate(bondDealDataDoc.getBondAddRate() == null ? new BigDecimal(0) : bondDealDataDoc.getBondAddRate());
				} else {
					bqtcDoc.setBondRate(new BigDecimal(0));
					bqtcDoc.setBondWeightedRate(new BigDecimal(0));
				}
			} else {
				bqtcDoc.setBondRate(new BigDecimal(0));
				bqtcDoc.setBondWeightedRate(new BigDecimal(0));
			}
			
			if (bqDoc.getSide() == 1) {
				bqtcDoc.setBondPrice(bqDoc.getBidPrice());
				bqtcDoc.setBondVol(bqDoc.getBidVol());
			} else if (bqDoc.getSide() == 2) {
				bqtcDoc.setBondPrice(bqDoc.getOfrPrice());
				bqtcDoc.setBondVol(bqDoc.getOfrVol());
			}
			bqtcDoc.setSide(bqDoc.getSide());
			bqtcDoc.setBondShortName(bqDoc.getBondShortName());
			bqtcDoc.setBondUniCode(bqDoc.getBondUniCode());
			bqtcDoc.setPriceUnit(bqDoc.getPriceUnit());
			bqtcDoc.setSendTime(bqDoc.getSendTime());
			bqtcDoc.setSendDateFormat(todayStr);

			bqtDocList.add(bqtcDoc);
		});

		if (null != bqtDocList && bqtDocList.size() > 0) {
			mongoOperations.remove(new Query(), BondQuoteTodaycurveDoc.class);
			mongoOperations.insert(bqtDocList, BondQuoteTodaycurveDoc.class);
		}

		long end = System.currentTimeMillis();

		LOGGER.info("saveBondQuoteTodaycurveDocInMongo, the time spent is " + (end - bengin));
	}

	//@Transactional
	public synchronized void saveBondQuoteHistorycurveDoc() {
		long bengin = System.currentTimeMillis();

		List<BondQuoteHistorycurveDoc> bqhDocList = new ArrayList<>();

		List<Map<String, Object>> mapList = bondQuoteDao.findBondQuoteHistorycurve();

		mapList.forEach(mapObj -> {
			BondQuoteHistorycurveDoc bqhDoc = new BondQuoteHistorycurveDoc();

			Long bondUniCode = (Long) mapObj.get("bondUniCode");
			String bondShortName = (String) mapObj.get("bondShortName");
			BigDecimal bidPrice = (BigDecimal) mapObj.get("bidPrice");
			BigDecimal bidVol = (BigDecimal) mapObj.get("bidVol");
			BigDecimal ofrPrice = (BigDecimal) mapObj.get("ofrPrice");
			BigDecimal ofrVol = (BigDecimal) mapObj.get("ofrVol");
			BigDecimal weightedPrice = (BigDecimal) mapObj.get("bondWeightedRate");
			BigDecimal dealVol = (BigDecimal) mapObj.get("bondDealingVolume");
			String sendTime = (String) mapObj.get("sendTime");

			bqhDoc.setBidPrice(bidPrice);
			bqhDoc.setBidVol(bidVol);
			bqhDoc.setBondUniCode(bondUniCode);
			bqhDoc.setDealVol(dealVol);
			bqhDoc.setOfrPrice(ofrPrice);
			bqhDoc.setOfrVol(ofrVol);
			bqhDoc.setSendTime(sendTime);
			bqhDoc.setWeightedPrice(weightedPrice);

			bqhDocList.add(bqhDoc);
		});

		if (null != bqhDocList && bqhDocList.size() > 0) {
			mongoOperations.remove(new Query(), BondQuoteHistorycurveDoc.class);
			mongoOperations.insert(bqhDocList, BondQuoteHistorycurveDoc.class);
		}
		long end = System.currentTimeMillis();

		LOGGER.info("saveBondQuoteHistorycurveDoc, the time spent is " + (end - bengin));
	}
	
	//@Transactional
	public synchronized void saveBondQuoteHistorycurveDocInMongo() {
		LOGGER.info("saveBondQuoteHistorycurveDocInMongo is begining..");
		long bengin = System.currentTimeMillis();

		List<BondQuoteHistorycurveDoc> bqhDocList = new ArrayList<>();

		List<Map<String, Object>> mapList = bondQuoteDao.findBondQuoteHistorycurveWithoutRate();
		
		LOGGER.info("saveBondQuoteHistorycurveDocInMongo mapList.size:"+(mapList == null ? 0:mapList.size())+",time cost:"+(System.currentTimeMillis()-bengin));

		mapList.forEach(mapObj -> {
			BondQuoteHistorycurveDoc bqhDoc = new BondQuoteHistorycurveDoc();

			Long bondUniCode = (Long) mapObj.get("bondUniCode");
			String bondShortName = (String) mapObj.get("bondShortName");
			BigDecimal bidPrice = (BigDecimal) mapObj.get("bidPrice");
			BigDecimal bidVol = (BigDecimal) mapObj.get("bidVol");
			BigDecimal ofrPrice = (BigDecimal) mapObj.get("ofrPrice");
			BigDecimal ofrVol = (BigDecimal) mapObj.get("ofrVol");
			String sendTime = (String) mapObj.get("sendTime");
	        String dateend = sendTime + " "+"23:59:59";
	        
			PageRequest request = new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "createTime"));
			Query bdQuery = new Query();
			bdQuery.addCriteria(Criteria.where("bondUniCode").is(bondUniCode).and("createTime").lt(dateend)).with(request);
			
			List<BondDealDataDoc> bondDealDataList = mongoOperations.find(bdQuery, BondDealDataDoc.class);
			if (null != bondDealDataList && bondDealDataList.size() > 0) {
				BondDealDataDoc bondDealDataDoc = bondDealDataList.get(0);
				String createDate = bondDealDataDoc.getCreateTime().split(" ")[0];
				if (sendTime.equals(createDate)) {
					bqhDoc.setWeightedPrice(bondDealDataDoc.getBondAddRate() == null ? new BigDecimal(0) : bondDealDataDoc.getBondAddRate());
					bqhDoc.setDealVol(bondDealDataDoc.getBondTradingVolume() == null ? new BigDecimal(0) : bondDealDataDoc.getBondTradingVolume());
				} else {
					bqhDoc.setWeightedPrice(new BigDecimal(0));
					bqhDoc.setDealVol(new BigDecimal(0));
				}
			} else {
				bqhDoc.setWeightedPrice(new BigDecimal(0));
				bqhDoc.setDealVol(new BigDecimal(0));
			}

			bqhDoc.setBidPrice(bidPrice);
			bqhDoc.setBidVol(bidVol);
			bqhDoc.setBondUniCode(bondUniCode);
			bqhDoc.setOfrPrice(ofrPrice);
			bqhDoc.setOfrVol(ofrVol);
			bqhDoc.setSendTime(sendTime);

			bqhDocList.add(bqhDoc);
		});

		if (null != bqhDocList && bqhDocList.size() > 0) {
			mongoOperations.remove(new Query(), BondQuoteHistorycurveDoc.class);
			mongoOperations.insert(bqhDocList, BondQuoteHistorycurveDoc.class);
		}
		long end = System.currentTimeMillis();

		LOGGER.info("saveBondQuoteHistorycurveDocInMongo, the time spent is " + (end - bengin));
	}

	//@Transactional
	public synchronized void saveBondSingleComparisonDoc() {
		LOGGER.info("saveBondSingleComparisonDoc is beginning..");

		long bengin = System.currentTimeMillis();

		List<Map<String, Object>> daysList = null;
		if (null == redisUtil.get("WorkingDays")) {
			daysList = isHolidayDao.findWorkingDays();
			redisUtil.set("WorkingDays", daysList, SafeUtils.getRestTodayTime());
		}else{
			daysList = (List<Map<String, Object>>) redisUtil.get("WorkingDays");
		}
		
		String dateBgn = "",dateEnd = "";
		if (null != daysList && daysList.size() > 0) {
			dateEnd = SafeUtils.getString(daysList.get(0).get("date"));
			dateBgn = SafeUtils.getString(daysList.get(daysList.size()-1).get("date"));
		}
		
		List<BondSingleComparisonDoc> bscDocList = new ArrayList<>();

		List<Map<String, Object>> mapList = bondQuoteDao.findSinglebondComparison(dateBgn, dateEnd);

		mapList.forEach(mapObj -> {
			BondSingleComparisonDoc bscDoc = new BondSingleComparisonDoc();

			Long bondUniCode = (Long) mapObj.get("bondUniCode");
			BigDecimal bidPrice = new BigDecimal(0.00);
			if (null != mapObj.get("bidPrice")) {
				bidPrice = new BigDecimal(mapObj.get("bidPrice").toString());
			}
			BigDecimal ofrPrice = new BigDecimal(0.00);
			if (null != mapObj.get("ofrPrice")) {
				ofrPrice = new BigDecimal(mapObj.get("ofrPrice").toString());
			}
			
			String bondCode = (String) mapObj.get("bondCode");
			String bondShortName = (String) mapObj.get("bondShortName");
			String sendTime = (String) mapObj.get("sendTime");

			bscDoc.setBidPrice(bidPrice);
			bscDoc.setBondCode(bondCode);
			bscDoc.setBondShortName(bondShortName);
			bscDoc.setBondUniCode(bondUniCode);
			bscDoc.setOfrPrice(ofrPrice);
			bscDoc.setSendTime(sendTime);

			bscDocList.add(bscDoc);
		});

		if (null != bscDocList && bscDocList.size() > 0) {
			mongoOperations.remove(new Query(), BondSingleComparisonDoc.class);
			mongoOperations.insert(bscDocList, BondSingleComparisonDoc.class);
		}

		long end = System.currentTimeMillis();

		LOGGER.info("saveBondSingleComparisonDoc, the time spent is " + (end - bengin));
	}
	
	//@Transactional
	public synchronized int saveWorkingdateSixdaysDoc() {
		LOGGER.info("saveWorkingdateSixdaysDoc is beginning..");

		mongoOperations.remove(new Query(), WorkingdateSixdaysDoc.class);

		List<Map<String, Object>> mapList = isHolidayDao.findWorkingDays();

		mapList.forEach(datemap -> {
			WorkingdateSixdaysDoc wsDoc = new WorkingdateSixdaysDoc();
			Integer tableId = (Integer) datemap.get("id");
			Date date = (Date) datemap.get("date");
			wsDoc.setDate(SafeUtils.convertDateToString(date, SafeUtils.DATE_FORMAT));
			wsDoc.setTableId(tableId);

			mongoOperations.insert(wsDoc, "workingdate_sixdays");
		});

		return 1;
	}

	@EventListener
	@Async
	public void handleBondDealSaved(BondDeal dealEvent) {
		try {
			if (dealEvent == null) {
				return;
			}
			if (dealEvent.getBondUniCode() == null) {
				return;
			}
			BondDealDataDoc bondDealDataDoc = new BondDealDataDoc();
			BeanUtils.copyProperties(dealEvent, bondDealDataDoc);
			bondDealDataDoc.setDataId(dealEvent.getId());
			bondDealDataDoc.setCreateTime(
					SafeUtils.convertDateToString(dealEvent.getCreateTime(), SafeUtils.DATE_TIME_FORMAT1));

			bondDealDataDocRepository.save(bondDealDataDoc);
		} catch (Exception e) {
			// e.printStackTrace();
			LOGGER.error(e.toString(), e);
		}
	}
	
}
