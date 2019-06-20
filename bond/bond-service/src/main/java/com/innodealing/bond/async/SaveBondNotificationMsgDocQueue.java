package com.innodealing.bond.async;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.innodealing.engine.mongo.bond.BondNotificationMsgDocRepository;
import com.innodealing.model.mongo.dm.BondNotificationMsgDoc;

public class SaveBondNotificationMsgDocQueue extends Thread {

	private static final Logger logger = Logger.getLogger(SaveBondNotificationMsgDocQueue.class);

	private final static Integer INTERRUPUT_TIME = 100;

	private static Queue<BondNotificationMsgDoc> saveBNMsgDocQueue = new LinkedList<BondNotificationMsgDoc>();

	private @Autowired Gson gson;

	private @Autowired BondNotificationMsgDocRepository bondNotificationMsgDocRepository;

	@Override
	public void run() {
		while (true) {
			int size = saveBNMsgDocQueue.size();
			if (size > 0) {
				this.handleMsgParams();
			} else {
				sleep(INTERRUPUT_TIME);
			}
		}
	}

	public synchronized void putMsgParams(BondNotificationMsgDoc sourceMsgDoc) {
		try {
			saveBNMsgDocQueue.add(sourceMsgDoc);
		} catch (Exception e) {
			logger.error("SaveBondNotificationMsgDocQueue putMsgParams error:", e);
			e.printStackTrace();
		}
	}

	private synchronized void handleMsgParams() {
		BondNotificationMsgDoc sourceMsgDoc = null;
		try {
			if (null != saveBNMsgDocQueue && saveBNMsgDocQueue.size() > 0) {
				sourceMsgDoc = saveBNMsgDocQueue.remove();
				if (null != sourceMsgDoc) {
					logger.info("SaveBondNotificationMsgDocQueue handleMsgParams save bondNotificationMsgDoc json:"
							+ gson.toJson(sourceMsgDoc));
					bondNotificationMsgDocRepository.save(sourceMsgDoc);
				}
			}
		} catch (Exception e) {
			logger.error("SaveBondNotificationMsgDocQueue handleMsgParams error:", e);
			e.printStackTrace();
		}
	}

	private void sleep(int seconds) {
		try {
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			logger.error("SaveBondNotificationMsgDocQueue sleep error:", e);
			e.printStackTrace();
		}
	}
}
