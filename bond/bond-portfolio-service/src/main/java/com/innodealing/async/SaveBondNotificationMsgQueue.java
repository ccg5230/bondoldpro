package com.innodealing.async;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.innodealing.amqp.SenderService;
import com.innodealing.consts.Constants;
import com.innodealing.domain.dao.UserInfoDAO;
import com.innodealing.domain.model.BondNotificationMsgParam;
import com.innodealing.engine.jpa.dm.BondNotificationMsgRepository;
import com.innodealing.engine.mongo.bond.BondNotificationMsgDocRepository;
import com.innodealing.engine.redis.RedisUtil;
import com.innodealing.json.socketio.ToMembersSocketIoMsg;
import com.innodealing.model.dm.bond.BondNotificationMsg;
import com.innodealing.model.mongo.dm.BondNotificationCardMsgDoc;
import com.innodealing.model.mongo.dm.BondNotificationMsgDoc;
import com.innodealing.util.MD5Util;
import com.innodealing.util.SafeUtils;

@Component
public class SaveBondNotificationMsgQueue extends Thread {

	private static final Logger logger = Logger.getLogger(SaveBondNotificationMsgQueue.class);

	private final Gson gson = new GsonBuilder().serializeNulls().create();
	
	private final static Integer INTERRUPUT_TIME = 100;

	private static Queue<BondNotificationMsgParam> saveBNMsgQueue = new LinkedList<BondNotificationMsgParam>();

	private @Autowired BondNotificationMsgRepository bondNotificationMsgRepository;

	private @Autowired BondNotificationMsgDocRepository bondNotificationMsgDocRepository;
	
	private @Autowired ApplicationEventPublisher msgPublisher;

	private @Autowired SenderService senderService;
	
    private @Autowired UserInfoDAO userInfoDAO;
    
	private @Autowired RedisUtil redisUtil;
	
	@Override
	public void run() {
		while (true) {
			int size = saveBNMsgQueue.size();
			if (size > 0) {
				this.handleMsgParams();
			} else {
				sleep(INTERRUPUT_TIME);
			}
		}
	}

	public synchronized void putMsgParams(BondNotificationMsgParam sourceMsg) {
		try {
			saveBNMsgQueue.add(sourceMsg);
		} catch (Exception e) {
			logger.error("SaveBondNotificationMsgQueue putMsgParams error:", e);
			e.printStackTrace();
		}
	}

	private synchronized void handleMsgParams() {
		BondNotificationMsgParam sourceMsg = null;
		try {
			if (null != saveBNMsgQueue && saveBNMsgQueue.size() > 0) {
				sourceMsg = saveBNMsgQueue.remove();
				saveMsg(sourceMsg);
			}
		} catch (Exception e) {
			logger.error("SaveBondNotificationMsgQueue handleMsgParams error:", e);
			e.printStackTrace();
		}
	}
	
	private void saveMsg(BondNotificationMsgParam sourceMsg) {
		if (null != sourceMsg) {
			sourceMsg.setNewsIndex(SafeUtils.getLong(sourceMsg.getNewsIndex()));
			sourceMsg.setImportant(SafeUtils.getInteger(sourceMsg.getImportant()));
			
			BondNotificationMsg target = new BondNotificationMsg();
			BeanUtils.copyProperties(sourceMsg, target);
			handleIsGrouprepeatmsg(sourceMsg, target);
			
			BondNotificationMsg entity = bondNotificationMsgRepository.save(target);
			saveNotificationMsgDoc(entity, sourceMsg.getUserId(), sourceMsg.getNotifiedEnable());
		}
	}

	private void handleIsGrouprepeatmsg(BondNotificationMsgParam sourceMsg, BondNotificationMsg target) {
		if (sourceMsg.getEventType().compareTo(Constants.EVENMSG_TYPE_ANNOUNCEMENT) == 0 ||
				sourceMsg.getEventType().compareTo(Constants.EVENMSG_TYPE_NEWSWARNING) == 0) {
			String msg = target.getGroupId()+"_"+target.getMsgContent()+"_"+target.getEventType();
			if (redisUtil.exists(MD5Util.getMD5(msg))) {
				logger.info("save bondGrouprepeatMsgRepository EventType:"+target.getEventType()+",isRepeatMsg:" + msg);
				target.setIsGrouprepeatmsg(1);
			} else {
				redisUtil.set(MD5Util.getMD5(msg), MD5Util.getMD5(msg), SafeUtils.getRestTodayTime());
				target.setIsGrouprepeatmsg(0);
			}
		}else{
			target.setIsGrouprepeatmsg(0);
		}
	}

	private void saveNotificationMsgDoc(BondNotificationMsg entity, Integer userId, Integer notifiedEnable) {
		if (null != entity) {
			logger.info("saveMsg userId:"+userId+",notifiedEnable:"+notifiedEnable+",Json:" +JSON.toJSONString(entity));

			BondNotificationMsgDoc bnMsgDoc = new BondNotificationMsgDoc();
			BeanUtils.copyProperties(entity, bnMsgDoc);
			BondNotificationMsgDoc resEntity = bondNotificationMsgDocRepository.save(bnMsgDoc);
			
			pubNotificationMsgToSocketIo(resEntity, userId);
			
			if (Constants.NOTIFIEDENABLE_OPENED == notifiedEnable) {
				pubCardMsgDoc(userId, bnMsgDoc);
			}
		}
	}

	private void pubNotificationMsgToSocketIo(BondNotificationMsgDoc resEntity, Integer userId) {
		String uid = userInfoDAO.getUidByUserId(SafeUtils.getString(userId));
		if (!StringUtils.isBlank(uid)) {
			ToMembersSocketIoMsg memberMsg = new ToMembersSocketIoMsg();
			memberMsg.setData(resEntity);
			memberMsg.setMembers(uid);
			memberMsg.setNamespaces(Constants.SOCKETIO_NAMESPACE_PORTFOLIO);
			memberMsg.setMessageType(Constants.MESSAGETYPE_NOTIFICATIONMSG);
			senderService.sendMsg2SocketIo(gson.toJson(memberMsg));
		}else{
			logger.info("pubNotificationMsgToSocketIo userId:"+userId+", uid is empty.");
		}
	}

	private void pubCardMsgDoc(Integer userId, BondNotificationMsgDoc bnMsgDoc) {
		BondNotificationCardMsgDoc bnCardMsgDoc = new BondNotificationCardMsgDoc();
		BeanUtils.copyProperties(bnMsgDoc, bnCardMsgDoc);
		bnCardMsgDoc.setUserId(userId);

		msgPublisher.publishEvent(bnCardMsgDoc);
	}

	private void sleep(int seconds) {
		try {
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			logger.error("SaveBondNotificationMsgQueue sleep error:", e);
			e.printStackTrace();
		}
	}

}
