package com.innodealing.async;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartThreadTask {
	
	private static final Logger LOG = LoggerFactory.getLogger(StartThreadTask.class);

	private @Autowired SaveBondNotificationMsgQueue saveBondNotificationMsgQueue;
	
	@PostConstruct
	public void init(){
		
		saveBondNotificationMsgQueue.start();
		LOG.info("StartThreadTask init saveBondNotificationMsgQueue");
	}

}
