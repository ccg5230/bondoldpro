package com.innodealing.service;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.innodealing.domain.model.BondNotificationMsgParam;

/**
 * @author feng.ma
 * @date 2017年5月26日 上午9:59:19
 * @describe:负责处理NotificationMsg逻辑的类
 */
@Service
public class BondPortfolioNotificationMsgHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(BondPortfolioNotificationMsgHandler.class);

	@Autowired
	private ApplicationEventPublisher publisher;

	public void pubNotificationMsg(BondNotificationMsgParam entityParam) {
		if (null == entityParam || StringUtils.isBlank(entityParam.getMsgContent())) {
			return;
		}
		
		publisher.publishEvent(entityParam);
		
		LOGGER.info("BondPortfolioNotificationMsgService pub entityParam:" + entityParam.toString());
	}

	public static BondNotificationMsgParam buildMsgParam(Integer userId, Long bondId, Integer eventType, String msgContent,
			Date createTime, Long newsIndex, Integer important, Long groupId, Integer emotionTag, Integer notifiedEnable) {
		BondNotificationMsgParam entityParam = new BondNotificationMsgParam();
		entityParam.setUserId(userId);
		entityParam.setBondId(bondId);
		entityParam.setEventType(eventType);
		entityParam.setMsgContent(msgContent);
		entityParam.setCreateTime(createTime);
		entityParam.setNewsIndex(newsIndex);
		entityParam.setImportant(important);
		entityParam.setGroupId(groupId);
		entityParam.setEmotionTag(emotionTag);
		entityParam.setNotifiedEnable(notifiedEnable);
		return entityParam;
	}
}
