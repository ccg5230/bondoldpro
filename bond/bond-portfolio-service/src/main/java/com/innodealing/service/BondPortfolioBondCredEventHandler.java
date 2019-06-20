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
import com.innodealing.domain.enums.EmotionTagEnum;
import com.innodealing.domain.enums.FavRadarSchemaEnum;
import com.innodealing.json.portfolio.BondCredRatingJson;
import com.innodealing.model.mongo.dm.BondCredRatingDoc;
import com.innodealing.model.mongo.dm.BondFavRatingIdxDoc;
import com.innodealing.model.mongo.dm.BondFavoriteRadarMappingDoc;
import com.innodealing.util.StringUtils;

@Service
public class BondPortfolioBondCredEventHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(BondPortfolioBondCredEventHandler.class);

	@Autowired
	private BondPortfolioNotificationMsgHandler notificationMsgHandler;

	@Autowired
	private MongoOperations mongoOperations;

	// 债项评级/债项展望
	public void handleBondCredRat(BondCredRatingJson credRatJson) {
		// 查询Index条件
		Long bondUniCode = credRatJson.getBondUniCode();
		Query query = new Query();
		query.addCriteria(
				Criteria.where("bondUniCode").is(bondUniCode).and("radarId").is(FavRadarSchemaEnum.CRED_RAT.getCode()));

		List<BondFavRatingIdxDoc> favRatingIdxes = mongoOperations.find(query, BondFavRatingIdxDoc.class);
		if (null != favRatingIdxes && favRatingIdxes.size() > 0) {
			favRatingIdxes.parallelStream().forEach(favRatingIdx -> {
				Query subquery = new Query();
				subquery.addCriteria(Criteria.where("bondUniCode").is(credRatJson.getBondUniCode()).and("orgUniCode").is(credRatJson.getOrgUniCode()));
				BondCredRatingDoc hisBcrDoc = mongoOperations.findOne(subquery, BondCredRatingDoc.class);

				handleBondcredMsg(credRatJson, hisBcrDoc, favRatingIdx);
			});
		}
	}

	private void handleBondcredMsg(BondCredRatingJson lastBcrDoc, BondCredRatingDoc hisBcrDoc,
			BondFavoriteRadarMappingDoc favRadarMapping) {
		Date lastBondRateWriteDt = lastBcrDoc.getRateWritDate();
		if (null != hisBcrDoc) {
			Date hisRateWriteDt = hisBcrDoc.getRateWritDate();

			if (hisRateWriteDt.before(lastBondRateWriteDt)) {
				LOGGER.info("handleBondcredMsg diff lastBcrDoc:" + lastBcrDoc.toString() + ", hisBcrDoc:"
						+ hisBcrDoc.toString());
				Integer emotionTag = EmotionTagEnum.DEFAULT.getCode();

				int bondCredLevelParFlag = 0;
				int bondCredLevelPar = hisBcrDoc.getCredLevelPar() - lastBcrDoc.getCredLevelPar();
				if (bondCredLevelPar > 0) {
					bondCredLevelParFlag = 1;
				} else if (bondCredLevelPar < 0) {
					bondCredLevelParFlag = -1;
				}
				
				//债项评级
				handleBondRating(lastBcrDoc, hisBcrDoc, favRadarMapping, emotionTag, bondCredLevelParFlag);
				//债项展望
				handleBondPros(lastBcrDoc, hisBcrDoc, favRadarMapping, emotionTag);
			}
		} else {
			//债项初评
			handleBondInitialEvalu(lastBcrDoc, favRadarMapping);
		}
	}

	private void handleBondInitialEvalu(BondCredRatingJson lastBcrDoc, BondFavoriteRadarMappingDoc favRadarMapping) {
		if (null != lastBcrDoc.getCredLevel()) {
			String clcontent = "债项评级初评" + lastBcrDoc.getCredLevel() + "(" + lastBcrDoc.getOrgShortName() + ")";
			notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(favRadarMapping.getUserId(), lastBcrDoc.getBondUniCode(), Constants.EVENMSG_TYPE_BONDRATE,
					clcontent, new Date(), 0L, 0, favRadarMapping.getGroupId(), EmotionTagEnum.DEFAULT.getCode(), favRadarMapping.getNotifiedEnable()));

		}

		if (StringUtils.isNotBlank(lastBcrDoc.getParName())) {
			String pdcontent = "债项展望初评" + lastBcrDoc.getParName() + "(" + lastBcrDoc.getOrgShortName() + ")";
			notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(favRadarMapping.getUserId(), lastBcrDoc.getBondUniCode(), Constants.EVENMSG_TYPE_BONDRATE,
					pdcontent, new Date(), 0L, 0, favRadarMapping.getGroupId(), EmotionTagEnum.DEFAULT.getCode(), favRadarMapping.getNotifiedEnable()));
		}
	}

	private void handleBondPros(BondCredRatingJson lastBcrDoc, BondCredRatingDoc hisBcrDoc,
			BondFavoriteRadarMappingDoc favRadarMapping, Integer emotionTag) {
		if (null != lastBcrDoc.getRateProsPar()) {
			String rppContent = "";
			if (null != hisBcrDoc.getRateProsPar()) {
				int rateProsParFlag = hisBcrDoc.getRateProsPar() - lastBcrDoc.getRateProsPar();
				
				if (rateProsParFlag != 0) {
					rppContent = "债项展望从" + hisBcrDoc.getParName() + "调整至" + lastBcrDoc.getParName() + "("
							+ lastBcrDoc.getOrgShortName() + ")";
					if (rateProsParFlag > 0) {
						emotionTag = EmotionTagEnum.GOOD.getCode();
					}else{
						emotionTag = EmotionTagEnum.RISK.getCode();
					}
				}else{
					emotionTag = EmotionTagEnum.DEFAULT.getCode();
					rppContent = "债项展望维持原有的" + hisBcrDoc.getParName() + "展望(" + lastBcrDoc.getOrgShortName()
							+ ")";
				}
			} else {
				emotionTag = EmotionTagEnum.DEFAULT.getCode();
				rppContent = "债项展望调整至" + lastBcrDoc.getParName() + "(" + lastBcrDoc.getOrgShortName() + ")";
			}
			
			notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(favRadarMapping.getUserId(), lastBcrDoc.getBondUniCode(),
					Constants.EVENMSG_TYPE_BONDRATE, rppContent, new Date(), 0L, 0,
					favRadarMapping.getGroupId(), emotionTag, favRadarMapping.getNotifiedEnable()));
			;
		}
	}

	private Integer handleBondRating(BondCredRatingJson lastBcrDoc, BondCredRatingDoc hisBcrDoc,
			BondFavoriteRadarMappingDoc favRadarMapping, Integer emotionTag, int bondCredLevelParFlag) {
		String clpContent = "";
		switch (bondCredLevelParFlag) {
		case 0:
			clpContent = "债项评级维持原有的" + hisBcrDoc.getCredLevel() + "评级(" + hisBcrDoc.getOrgShortName() + ")";
			break;
		case 1:
			clpContent = "债项评级从原有的" + hisBcrDoc.getCredLevel() + "上调至" + lastBcrDoc.getCredLevel() + "("
					+ lastBcrDoc.getOrgShortName() + ")";
			emotionTag = EmotionTagEnum.GOOD.getCode();
			break;
		case -1:
			clpContent = "债项评级从原有的" + hisBcrDoc.getCredLevel() + "下降至" + lastBcrDoc.getCredLevel() + "("
					+ lastBcrDoc.getOrgShortName() + ")";
			emotionTag = EmotionTagEnum.RISK.getCode();
			break;
		}

		notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(favRadarMapping.getUserId(), lastBcrDoc.getBondUniCode(),
				Constants.EVENMSG_TYPE_BONDRATE, clpContent, new Date(), 0L, 0,
				favRadarMapping.getGroupId(), emotionTag, favRadarMapping.getNotifiedEnable()));
		return emotionTag;
	}

}
