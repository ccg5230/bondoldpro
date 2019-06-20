package com.innodealing.bond.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.innodealing.bond.vo.quote.BondQuoteTodaycurveVo;
import com.innodealing.model.mongo.dm.bond.quote.analyse.BondQuoteHistorycurveDoc;
import com.innodealing.model.mongo.dm.bond.quote.analyse.BondQuoteTodaycurveBasic;
import com.innodealing.model.mongo.dm.bond.quote.analyse.BondQuoteTodaycurveDoc;

/**
 * @author stephen.ma
 * @date 2016年9月13日
 * @clasename BondQuoteAnalysisService.java
 * @decription TODO
 */
@Service
public class BondQuoteAnalysisService {

	private final static Logger logger = LoggerFactory.getLogger(BondQuoteAnalysisService.class);

	private @Autowired MongoOperations mongoOperations;

	/**
	 * @param date
	 * @return
	 */
	public BondQuoteTodaycurveVo findTodaycurve(Long bondId, String date) {
		logger.info("findTodaycurve bondId:" + bondId + ",date:" + date);

		BondQuoteTodaycurveVo bqtcVo = new BondQuoteTodaycurveVo();
		/*
		Query query = new Query();
		query.addCriteria(Criteria.where("bondUniCode").is(bondId).and("sendDateFormat").is(date));
		query.with(new Sort(Sort.Direction.ASC, "sendTime"));

		List<BondQuoteTodaycurveDoc> bqtcDocList = mongoOperations.find(query, BondQuoteTodaycurveDoc.class);
		*/
		//如果出现 Sort operation used more than the maximum的问题，可用该方案，查询有会比较慢
		AggregationOperation match = Aggregation.match(Criteria.where("bondUniCode").is(bondId).and("sendDateFormat").is(date));
		AggregationOperation sort = Aggregation.sort(new Sort(Sort.Direction.ASC, "sendTime"));
		Aggregation aggregation = Aggregation.newAggregation(match, sort).withOptions(Aggregation.newAggregationOptions().allowDiskUse(true).build());
		AggregationResults<BondQuoteTodaycurveDoc> result = mongoOperations.aggregate(aggregation, "bond_quote_todaycurve", BondQuoteTodaycurveDoc.class);

		List<BondQuoteTodaycurveDoc> bqtcDocList = result.getMappedResults();
		
		bqtcDocList.forEach(bqtcDoc -> {
			BondQuoteTodaycurveBasic bidBasic = new BondQuoteTodaycurveBasic();
			BondQuoteTodaycurveBasic ofrBasic = new BondQuoteTodaycurveBasic();
			BondQuoteTodaycurveBasic dealBasic = new BondQuoteTodaycurveBasic();

			Integer side = bqtcDoc.getSide();
			BigDecimal bondPrice = bqtcDoc.getBondPrice();
			BigDecimal bondVol = bqtcDoc.getBondVol();
			BigDecimal bondRate = bqtcDoc.getBondRate();
			String sendTime = bqtcDoc.getSendTime();

			if (null != side && side == 1) {
				bidBasic.setBondPrice(bondPrice);
				bidBasic.setBondVol(bondVol);
				bidBasic.setSendTime(sendTime);
				bqtcVo.getBidData().add(bidBasic);

			} else if (null != side && side == 2) {
				ofrBasic.setBondPrice(bondPrice);
				ofrBasic.setBondVol(bondVol);
				ofrBasic.setSendTime(sendTime);
				bqtcVo.getOfrData().add(ofrBasic);
			}
			if (null != bondRate) {
				dealBasic.setBondPrice(bondRate);
				dealBasic.setSendTime(sendTime);
				bqtcVo.getDealData().add(dealBasic);
			}
			
		});
		bqtcVo.setSendTime(date);

		return bqtcVo;
	}

	/**
	 * @return
	 */
	public List<BondQuoteHistorycurveDoc> findHistorycurve(Long bondId) {
		logger.info("findHistorycurve bondId:" + bondId);
		//原先的实现方式
		/*
		Query query = new Query();
		query.addCriteria(Criteria.where("bondUniCode").is(bondId));
		query.with(new Sort(Sort.Direction.ASC, "sendTime"));
		List<BondQuoteHistorycurveDoc> list = mongoOperations.find(query, BondQuoteHistorycurveDoc.class);
		*/
		//如果出现 Sort operation used more than the maximum的问题，可用该方案，查询有会比较慢
		AggregationOperation match = Aggregation.match(Criteria.where("bondUniCode").is(bondId));
		AggregationOperation sort = Aggregation.sort(new Sort(Sort.Direction.ASC, "sendTime"));
		Aggregation aggregation = Aggregation.newAggregation(match, sort).withOptions(Aggregation.newAggregationOptions().allowDiskUse(true).build());
		
		AggregationResults<BondQuoteHistorycurveDoc> result = mongoOperations.aggregate(aggregation, "bond_quote_historycurve", BondQuoteHistorycurveDoc.class);

		List<BondQuoteHistorycurveDoc> list = result.getMappedResults();
		
		return list;
	}

}
