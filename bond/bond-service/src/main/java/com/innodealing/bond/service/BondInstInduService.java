package com.innodealing.bond.service;

import java.util.List;

import com.innodealing.model.mongo.dm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.innodealing.consts.Constants;
import com.innodealing.model.dm.bond.BondInstComIndu;
import com.innodealing.model.dm.bond.BondInstIndu;
import com.innodealing.model.mongo.dm.bond.detail.analyse.IssPdDoc;

@Service
public class BondInstInduService {

	protected @Autowired MongoOperations mongoOperations;

	public String saveIndu(List<BondInstIndu> instInduList) {

		for (BondInstIndu instIndu : instInduList) {
			saveInstIndu(instIndu, BondComInfoDoc.class);
			saveInstIndu(instIndu, BondDetailDoc.class);
			saveInstIndu(instIndu, BondBasicInfoDoc.class);
			saveInstIndu(instIndu, BondPdRankDoc.class);
			saveInstIndu(instIndu, IssPdDoc.class);
			saveInstIndu(instIndu, BondDiscoveryTodayDealDetailDoc.class);
			saveInstIndu(instIndu, BondDiscoveryTodayQuoteDetailDoc.class);
			saveInstIndu(instIndu, BondDiscoveryAbnormalDealDoc.class);
			saveInstIndu(instIndu, BondDiscoveryAbnormalQuoteDoc.class);
			
		}

		return "OK";
	}

	public String saveComIndu(List<BondInstComIndu> instComInduList) {

		for (BondInstComIndu instComIndu : instComInduList) {
			saveInstComIndu(instComIndu, BondComInfoDoc.class, "_id");
			saveInstComIndu(instComIndu, BondDetailDoc.class, "comUniCode");
			saveInstComIndu(instComIndu, BondBasicInfoDoc.class, "issuerId");
			saveInstComIndu(instComIndu, IssPdDoc.class,"issId");
			saveInstComIndu(instComIndu, BondDiscoveryTodayDealDetailDoc.class,"issuerId");
			saveInstComIndu(instComIndu, BondDiscoveryTodayQuoteDetailDoc.class,"issuerId");
			saveInstComIndu(instComIndu, BondDiscoveryAbnormalDealDoc.class,"issuerId");
			saveInstComIndu(instComIndu, BondDiscoveryAbnormalQuoteDoc.class,"issuerId");
		}

		return "OK";

	}

	private Boolean saveInstIndu(BondInstIndu instIndu, Class<?> c) {
		String code = Constants.INSTIRUTION_INDU_CODE + instIndu.getInstId();
		mongoOperations.updateMulti(new Query(Criteria.where(code).is(instIndu.getOldInduUniCode())),
				updateInstIndu(instIndu), c);
		return true;
	}

	private Boolean saveInstComIndu(BondInstComIndu instIndu, Class<?> c, String key) {
		mongoOperations.updateMulti(new Query(Criteria.where(key).is(instIndu.getComUniCode())),
				updateInstComIndu(instIndu), c);
		return true;
	}

	private Update updateInstIndu(BondInstIndu instindu) {
		String name = Constants.INSTIRUTION_INDU_NAME + instindu.getInstId();
		String code = Constants.INSTIRUTION_INDU_CODE + instindu.getInstId();
		Update update = new Update();
		update.set(name, !StringUtils.isEmpty(instindu.getInduClassName()) ? instindu.getInduClassName() : null);
		update.set(code, !StringUtils.isEmpty(instindu.getInduUniCode()) ? instindu.getInduUniCode() : null);
		return update;
	}

	private Update updateInstComIndu(BondInstComIndu instComIndu) {
		String name = Constants.INSTIRUTION_INDU_NAME + instComIndu.getInstId();
		String code = Constants.INSTIRUTION_INDU_CODE + instComIndu.getInstId();
		Update update = new Update();
		update.set(name, !StringUtils.isEmpty(instComIndu.getInduUniName()) ? instComIndu.getInduUniName() : null);
		update.set(code, !StringUtils.isEmpty(instComIndu.getInduUniCode()) ? instComIndu.getInduUniCode() : null);
		return update;
	}

}
