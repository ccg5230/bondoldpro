package com.innodealing.bond.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.innodealing.bond.async.SaveBondNotificationMsgQueue;
import com.innodealing.model.dm.bond.BondNotificationMsg;

@Component
public class BondEventMsgListener {
	
	private final static Logger logger = LoggerFactory.getLogger(BondEventMsgListener.class);
	
	private @Autowired Gson gson;
	
	private @Autowired SaveBondNotificationMsgQueue saveBondNotificationMsgQueue;
	
	@Async
	@EventListener
	public void handleBondEventMsg(BondNotificationMsg bnMsg){
		//logger.info("handleBondEventMsg json params:"+gson.toJson(bnMsg));
		if (null != bnMsg) {
			saveBondNotificationMsgQueue.putMsgParams(bnMsg);
		}
	}
	
	/*
	@Async
	@EventListener
	public void handleBondEventMsgCount(BondNotificationMsgDoc bnMsgDoc){
		if (null == bnMsgDoc) {
			return;
		}
		Long bondId = bnMsgDoc.getBondId();
		Query query = new Query();
		query.addCriteria(Criteria.where("bondUniCode").is(bondId).and("isDelete").is(0).and("bookmark").lt(bnMsgDoc.getId()));
		List<BondFavoriteDoc> bondFavoriteDocList = mongoOperations.find(query, BondFavoriteDoc.class);
		
		bondFavoriteDocList.stream().forEach(bondFavoriteDoc -> {
			String key = RedisKeyConstants.R_BONDMSGCOUNT_KEY+bondFavoriteDoc.getUserId()+"_"+bondFavoriteDoc.getGroupId()+"_"+bondFavoriteDoc.getBondUniCode();
			Long countVal = SafeUtils.getLong(redisUtil.get(key));
			if (null != countVal && countVal > 0L) {
				redisUtil.set(key, countVal++);
			}else {
				redisUtil.set(key, 0L);
			}
		});
	}
	*/
}
