package com.innodealing.listener;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.innodealing.amqp.SenderService;
import com.innodealing.model.mongo.dm.BondSentimentDistinctDoc;
import com.innodealing.util.SafeUtils;

@Service
public class BondSentimentEventMsgListener {

	private @Autowired SenderService senderService;

	@Async
	@EventListener
	public void handleBondSentimentEventMsg(BondSentimentDistinctDoc bstDoc) {
		return;
		/*
		if (null == bstDoc) {
			return;
		}

		if (null == bstDoc.getPubDate()) {
			return;
		}

		double hoursoffset = SafeUtils.getHoursBetween(bstDoc.getPubDate(), new Date());
		if (hoursoffset > 48.0) {
			return;
		}

		senderService.sendBondSentimentnews2Rabbitmq(JSON.toJSONString(bstDoc));
		*/
	}
}
