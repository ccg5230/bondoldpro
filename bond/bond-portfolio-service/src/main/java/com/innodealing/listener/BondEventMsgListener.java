package com.innodealing.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.innodealing.async.SaveBondNotificationMsgQueue;
import com.innodealing.domain.model.BondNotificationMsgParam;
import com.innodealing.model.mongo.dm.BondDetailDoc;

@Component
public class BondEventMsgListener {

	private final static Logger logger = LoggerFactory.getLogger(BondEventMsgListener.class);

	private @Autowired SaveBondNotificationMsgQueue saveBondNotificationMsgQueue;

	private @Autowired MongoOperations mongoOperations;

	@Async
	@EventListener
	public void handleBondEventMsg(BondNotificationMsgParam bnMsg) {
		if (isExpiredBondDetail(bnMsg.getBondId())) {
			logger.info("handleBondEventMsg,the bond [" + bnMsg.getBondId() + "] in system is expired.");
			return;
		}
		// 正式环境上去掉日志
		logger.info("handleBondEventMsg json:" + JSON.toJSONString(bnMsg));
		
		if (null != bnMsg) {
			saveBondNotificationMsgQueue.putMsgParams(bnMsg);
		}
	}

	private boolean isExpiredBondDetail(Long bondUniCode) {
		boolean result = true;
		Query query = new Query();
		query.addCriteria(Criteria.where("bondUniCode").is(bondUniCode).and("currStatus").is(1).and("issStaPar").is(1));
		query.fields().include("bondUniCode").include("code").include("currStatus").include("issStaPar");
		
		BondDetailDoc obj = mongoOperations.findOne(query, BondDetailDoc.class);
		if (null != obj) {
			result = false;
		}
		return result;
	}
}
