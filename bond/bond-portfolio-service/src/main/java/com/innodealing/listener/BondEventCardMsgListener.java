package com.innodealing.listener;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.innodealing.amqp.SenderService;
import com.innodealing.domain.dao.UserInfoDAO;
import com.innodealing.engine.mongo.bond.BondNotificationCardMsgDocRepository;
import com.innodealing.engine.redis.RedisUtil;
import com.innodealing.json.consts.Constants;
import com.innodealing.json.socketio.ToMembersSocketIoMsg;
import com.innodealing.model.mongo.dm.BondNotificationCardMsgDoc;
import com.innodealing.util.MD5Util;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;

/**
 * @author feng.ma
 * @date 2017年8月13日 下午2:29:55
 * @describe
 */
@Component
public class BondEventCardMsgListener {

	private final static Logger logger = LoggerFactory.getLogger(BondEventCardMsgListener.class);

	private @Autowired BondNotificationCardMsgDocRepository bondNotiCardMsgDocRep;

	private @Autowired SenderService senderService;
	
	private @Autowired RedisUtil redisUtil;
	
    private @Autowired UserInfoDAO userInfoDAO;

	private final Gson gson = new GsonBuilder().serializeNulls().create();
	
	@Async
	@EventListener
	public void handleBondEventCardMsg(BondNotificationCardMsgDoc bnCardMsgDoc) {
		if (null == bnCardMsgDoc) {
			return;
		}
		logger.info("handleBondEventCardMsg json:" + JSON.toJSONString(bnCardMsgDoc));

		try {
			if (!isRepeatMsg(bnCardMsgDoc)) {

				saveCardMsg(bnCardMsgDoc);
				/*
				if(saveCardMsg(bnCardMsgDoc) != null){

					sendCardMsgToSocketIo(bnCardMsgDoc);
				}else{
					logger.info("handleBondEventCardMsg after save,  the return entity is null.bnCardMsgJSON:"+JSON.toJSONString(bnCardMsgDoc));
				}
				*/
			}
		} catch (Exception ex) {
			logger.error("handleBondEventCardMsg error:" + ex.getMessage(), ex);
		}
	}

	private void sendCardMsgToSocketIo(BondNotificationCardMsgDoc bnCardMsgDoc) {
		String uid = userInfoDAO.getUidByUserId(SafeUtils.getString(bnCardMsgDoc.getUserId()));
		if (!StringUtils.isBlank(uid)) {
			ToMembersSocketIoMsg memberMsg = new ToMembersSocketIoMsg();
			memberMsg.setData(bnCardMsgDoc);
			memberMsg.setMembers(userInfoDAO.getUidByUserId(SafeUtils.getString(bnCardMsgDoc.getUserId())));
			memberMsg.setNamespaces(Constants.SOCKETIO_NAMESPACE_PORTFOLIO);
			memberMsg.setMessageType(Constants.MESSAGETYPE_CARDMSG);
			senderService.sendMsg2SocketIo(gson.toJson(memberMsg));
		}else{
			logger.info("sendCardMsgToSocketIo userId:"+bnCardMsgDoc.getUserId()+", uid is empty.");
		}
	}

	private boolean isRepeatMsg(BondNotificationCardMsgDoc bnCardMsgDoc) {
		boolean result = false;
		String msg = bnCardMsgDoc.getUserId()+"_"+bnCardMsgDoc.getBondId() + "_" + bnCardMsgDoc.getMsgContent();
		if (bnCardMsgDoc.getEventType().compareTo(Constants.EVENMSG_TYPE_ANNOUNCEMENT) == 0 ||
				bnCardMsgDoc.getEventType().compareTo(Constants.EVENMSG_TYPE_NEWSWARNING) == 0) {

			msg = bnCardMsgDoc.getUserId()+"_"+ bnCardMsgDoc.getMsgContent();
		}
		
		if (redisUtil.exists(MD5Util.getMD5(msg))) {
			logger.info("handleBondEventCardMsg EventType:"+bnCardMsgDoc.getEventType()+",isRepeatMsg:" + msg);

			result = true;
		} else {
			redisUtil.set(MD5Util.getMD5(msg), MD5Util.getMD5(msg), SafeUtils.getRestTodayTime());
		}

		return result;
	}

	@Transactional
	public BondNotificationCardMsgDoc saveCardMsg(BondNotificationCardMsgDoc bnCardMsgDoc) {
		
		return bondNotiCardMsgDocRep.save(bnCardMsgDoc);
	}

}
