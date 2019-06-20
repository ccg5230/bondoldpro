package com.innodealing.async;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartTask {
	
	private static final Logger LOG = LoggerFactory.getLogger(StartTask.class);

	private @Autowired AmarcalculateHandleTask amarcalculateHandleTask;
	
	@PostConstruct
	public void init(){
		
		amarcalculateHandleTask.start();
		LOG.info("StartTask init amarcalculateHandleTask");
	}

}
