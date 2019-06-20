package com.innodealing.bond.async;

import java.util.LinkedList;
import java.util.Queue;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.innodealing.engine.jpa.dm.BondNotificationMsgRepository;
import com.innodealing.engine.mongo.bond.BondNotificationMsgDocRepository;
import com.innodealing.model.dm.bond.BondNotificationMsg;
import com.innodealing.model.mongo.dm.BondNotificationMsgDoc;
import com.innodealing.util.SafeUtils;

public class SaveBondNotificationMsgQueue extends Thread {

	private static final Logger logger = Logger.getLogger(SaveBondNotificationMsgQueue.class);

	private final static Integer INTERRUPUT_TIME = 100;

	private static Queue<BondNotificationMsg> saveBNMsgQueue = new LinkedList<BondNotificationMsg>();

	private @Autowired Gson gson;

	private @Autowired BondNotificationMsgRepository bondNotificationMsgRepository;

	private @Autowired BondNotificationMsgDocRepository bondNotificationMsgDocRepository;

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

	public synchronized void putMsgParams(BondNotificationMsg sourceMsg) {
		try {
			saveBNMsgQueue.add(sourceMsg);
		} catch (Exception e) {
			logger.error("SaveBondNotificationMsgQueue putMsgParams error:", e);
			e.printStackTrace();
		}
	}

	private synchronized void handleMsgParams() {
		BondNotificationMsg sourceMsg = null;
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
	
	@Transactional
	private void saveMsg(BondNotificationMsg sourceMsg) {
		if (null != sourceMsg) {
			sourceMsg.setNewsIndex(SafeUtils.getLong(sourceMsg.getNewsIndex()));
			sourceMsg.setImportant(SafeUtils.getInteger(sourceMsg.getImportant()));
			
			BondNotificationMsg entity = bondNotificationMsgRepository.save(sourceMsg);
			
			logger.info("saveMsg entity gson:" +gson.toJson(entity));
			if (null != entity) {
				logger.info("saveMsg gson:" +gson.toJson(entity));

				BondNotificationMsgDoc bnMsgDoc = new BondNotificationMsgDoc();
				BeanUtils.copyProperties(entity, bnMsgDoc);
				bondNotificationMsgDocRepository.save(bnMsgDoc);
			}
		}
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
