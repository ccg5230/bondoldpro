package com.innodealing.bond.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.innodealing.engine.jdbc.bond.BondFavoriteDao;
import com.innodealing.model.mongo.dm.BondDetailDoc;

/** 
* @author feng.ma
* @date 2017年5月3日 上午11:32:35 
* @describe 
*/
@Component
public class BondExpiredFavListener {

	@Autowired
	private BondFavoriteDao bondFavoriteDao;
	
	@Async
	@EventListener
	public void handleExpiredBondFav(BondDetailDoc bondDetailDoc){
		if (null != bondDetailDoc) {
			bondFavoriteDao.updateExpiredBond(bondDetailDoc.getBondUniCode());
		}
	}
}
