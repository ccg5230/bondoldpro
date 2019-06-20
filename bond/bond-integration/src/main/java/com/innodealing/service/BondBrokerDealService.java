package com.innodealing.service;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.innodealing.bond.param.BondBrokerDealParam;
import com.innodealing.bond.validation.MessageValidation;
import com.innodealing.engine.jpa.dm.BondBrokerDealRepository;
import com.innodealing.engine.mongo.bond.BondDetailRepository;
import com.innodealing.model.dm.bond.BondBrokerDeal;
import com.innodealing.model.mongo.dm.BondDetailDoc;
import com.innodealing.util.SafeUtils;

@Component
public class BondBrokerDealService {

	private final static Logger logger = LoggerFactory.getLogger(BondBrokerDealService.class);

	@Autowired
	private BondBrokerDealRepository bondBrokerDealRepository;

	@Autowired
	private BondDetailRepository bondDetailRepository;

	@Autowired
	private MessageValidation msgValidation;

	public Long handleBrokerData(BondBrokerDealParam params) {
		Long seqId = 0L;
		if (null == params.getBondStrikePrice() || params.getBondStrikePrice().doubleValue() == 0) {
			return seqId;
		}

		StringBuffer content = new StringBuffer();
		content.append(params.getBondCode()).append("_").append(params.getBidPrice()).append("_")
				.append(params.getBidVol()).append("_");
		content.append(params.getOfrPrice()).append("_").append(params.getOfrVol()).append("_")
				.append(params.getBondStrikePrice()).append("_").append(params.getBrokerType());

		logger.info("handleBrokerData params:" + JSON.toJSONString(params));
		if (!msgValidation.isSameMessage(content.toString())) {
			seqId = saveBrokerData(params, seqId);
			msgValidation.saveMsgWithTimeout(content.toString(), SafeUtils.getRestTodayTime());
		} else {
			logger.info("handleBrokerData content isSameMessage, content:" + content.toString());
		}

		return seqId;
	}

	@Transactional
	private Long saveBrokerData(BondBrokerDealParam params, Long seqId) {
		BondDetailDoc bondDetailDoc = bondDetailRepository.findByCode(params.getBondCode());
		if (null != bondDetailDoc) {
			BondBrokerDeal bondBrokerDeal = new BondBrokerDeal();
			BeanUtils.copyProperties(params, bondBrokerDeal);
			bondBrokerDeal.setBondShortName(bondDetailDoc.getName());
			bondBrokerDeal.setBondUniCode(bondDetailDoc.getBondUniCode());
			bondBrokerDeal.setStrikePrice(params.getBondStrikePrice());
			bondBrokerDeal.setPostfrom(9);
			bondBrokerDeal.setSourceName("Broker数据源");
			bondBrokerDeal.setCreateTime(new Date());
			if (null == bondBrokerDeal.getBidVol()) {
				bondBrokerDeal.setBidVol(new BigDecimal(0));
			}
			if (null == bondBrokerDeal.getBidPrice()) {
				bondBrokerDeal.setBidPrice(new BigDecimal(0));
			}
			if (null == bondBrokerDeal.getOfrVol()) {
				bondBrokerDeal.setOfrVol(new BigDecimal(0));
			}
			if (null == bondBrokerDeal.getOfrPrice()) {
				bondBrokerDeal.setOfrPrice(new BigDecimal(0));
			}

			bondBrokerDeal = bondBrokerDealRepository.save(bondBrokerDeal);
			seqId = bondBrokerDeal.getId();
			logger.info("after save bondBrokerDeal:" + JSON.toJSONString(bondBrokerDeal));
		} else {
			logger.info("saveBrokerData method bondDetailDoc is null, params:" + JSON.toJSONString(params));
		}
		return seqId;
	}

}
