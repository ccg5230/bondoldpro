package com.innodealing.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.innodealing.consts.Constants;
import com.innodealing.domain.enums.FavRadarSchemaEnum;
import com.innodealing.engine.mongo.bond.BondBasicInfoRepository;
import com.innodealing.json.portfolio.SentimentLawsuitJson;
import com.innodealing.model.mongo.dm.BondBasicInfoDoc;
import com.innodealing.model.mongo.dm.BondFavSentimentIdxDoc;
import com.innodealing.util.SafeUtils;

/**
 * @author feng.ma
 * @date 2017年6月30日 上午11:27:39
 * @describe 舆情的公司诉讼
 */
@Service
public class BondPortfolioSentimentLawsuitHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(BondPortfolioSentimentLawsuitHandler.class);

	@Autowired
	private BondPortfolioNotificationMsgHandler notificationMsgHandler;

	@Autowired
	private BondBasicInfoRepository bondBasicInfoRep;

	@Autowired
	private MongoOperations mongoOperations;

	public void handleBondSentimentLawsuit(SentimentLawsuitJson sentiLawsuitJson) {
		if (null == sentiLawsuitJson) {
			return;
		}
		// 只需要发布时间2天之内的舆情
		if (SafeUtils.getHoursBetween(sentiLawsuitJson.getPubDate(), new Date()) > 48.00) {
			return;
		}
		Long comUniCode = SafeUtils.getLong(sentiLawsuitJson.getComUniCode());
		Query query = new Query();
		query.addCriteria(Criteria.where("comUniCode").is(comUniCode).and("radarId")
				.is(FavRadarSchemaEnum.COMLGN_SENS.getCode()).and("createTime").lte(sentiLawsuitJson.getPubDate()));

		List<BondFavSentimentIdxDoc> favSentimentIdxes = mongoOperations.find(query, BondFavSentimentIdxDoc.class);
		favSentimentIdxes.stream().forEach(favSentimentIdx -> {
			if (isValidBond(favSentimentIdx.getBondUniCode())) {

				notificationMsgHandler.pubNotificationMsg(
						BondPortfolioNotificationMsgHandler.buildMsgParam(favSentimentIdx.getUserId(),
								favSentimentIdx.getBondUniCode(), Constants.EVENMSG_TYPE_COMLITIGATION,
								sentiLawsuitJson.getTitle(), sentiLawsuitJson.getPubDate(), sentiLawsuitJson.getIndex(),
								0, favSentimentIdx.getGroupId(), 0, favSentimentIdx.getNotifiedEnable()));
			} else {
				LOGGER.info("handleBondSentimentLawsuit BondUniCode:" + favSentimentIdx.getBondUniCode()
						+ ",ComUniCode:" + comUniCode + " is expired.");
			}
		});

	}

	private boolean isValidBond(Long bondUniCode) {
		boolean result = false;

		BondBasicInfoDoc bondBasicInfo = bondBasicInfoRep.findOne(bondUniCode);
		if (null != bondBasicInfo && bondBasicInfo.getCurrStatus().intValue() == 1
				&& bondBasicInfo.getIssStaPar().intValue() == 1) {
			result = true;
		}

		return result;
	}

}
