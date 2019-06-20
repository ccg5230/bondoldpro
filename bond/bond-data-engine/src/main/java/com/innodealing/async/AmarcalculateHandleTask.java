package com.innodealing.async;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innodealing.manager.BondAmarcalculateManager;
import com.innodealing.util.SafeUtils;

@Component
public class AmarcalculateHandleTask extends Thread {
	
	private static final Logger LOG = LoggerFactory.getLogger(AmarcalculateHandleTask.class);

	private final static Integer INTERRUPUT_TIME = 100;

	private static BlockingQueue<String> taskIdQueue = new LinkedBlockingQueue<String>();
	
	private @Autowired BondAmarcalculateManager amarcalculateManager;
	
	@Override
	public void run() {
		while (true) {
			int size = taskIdQueue.size();
			if (size > 0) {
				this.handleParams();
			} else {
				sleep(INTERRUPUT_TIME);
			}
		}
	}
	
	private synchronized void handleParams() {
		try {
			String taskId = taskIdQueue.remove();
			amarcalculateManager.calculationResult(SafeUtils.getLong(taskId));
		} catch (Exception e) {
			LOG.error("AmarcalculateHandleTask handleParams error:", e);
			e.printStackTrace();
		}
	}
	
	public synchronized void putParams(String taskId) {
		try {
			taskIdQueue.add(taskId);
		} catch (Exception e) {
			LOG.error("SaveBondNotificationMsgDocQueue putMsgParams error:", e);
			e.printStackTrace();
		}
	}
	
	private void sleep(Integer seconds) {
		try {
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			LOG.error("AmarcalculateHandleTask sleep error:", e);
			e.printStackTrace();
		}		
	}
}
