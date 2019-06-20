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
import com.innodealing.json.portfolio.IssCredRatingJson;
import com.innodealing.model.mongo.dm.BondFavRatingIdxDoc;
import com.innodealing.model.mongo.dm.BondFavoriteRadarMappingDoc;
import com.innodealing.model.mongo.dm.IssCredRatingDoc;
import com.innodealing.util.StringUtils;

@Service
public class BondPortfolioIssCredEventHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(BondPortfolioIssCredEventHandler.class);

	@Autowired
	private BondPortfolioNotificationMsgHandler notificationMsgHandler;

	@Autowired
	private MongoOperations mongoOperations;

	// 主体评级/主体展望
	public void handleBondIsscredrat(IssCredRatingJson issCredRatJson) {
		// 查询Index条件
		Long comUniCode = issCredRatJson.getComUniCode();
		Query query = new Query();
		query.addCriteria(
				Criteria.where("comUniCode").is(comUniCode).and("radarId").is(FavRadarSchemaEnum.ISSU_RAT.getCode()));

		List<BondFavRatingIdxDoc> favRatingIdxes = mongoOperations.find(query, BondFavRatingIdxDoc.class);
		if (null != favRatingIdxes && favRatingIdxes.size() > 0) {
			favRatingIdxes.parallelStream().forEach(favRatingIdx -> {
				IssCredRatingDoc hisIcrDoc = findHistIssCredRat(issCredRatJson);

				handleIsscredMsg(issCredRatJson, hisIcrDoc, favRatingIdx);
			});
		}
	}

	private IssCredRatingDoc findHistIssCredRat(IssCredRatingJson issCredRatJson) {
		Query subquery = new Query();
		subquery.addCriteria(Criteria.where("comUniCode").is(issCredRatJson.getComUniCode()).and("orgUniCode")
				.is(issCredRatJson.getOrgUniCode()));
		IssCredRatingDoc hisIcrDoc = mongoOperations.findOne(subquery, IssCredRatingDoc.class);
		return hisIcrDoc;
	}

	private void handleIsscredMsg(IssCredRatingJson issCredRatJson, IssCredRatingDoc hisIcrDoc,
			BondFavoriteRadarMappingDoc favRadarMapping) {
		if (null != hisIcrDoc) {
			if (hisIcrDoc.getRateWritDate().before(issCredRatJson.getRateWritDate())) {

				int issCredLevelParFlag = 0;
				int issCredLevelPar = hisIcrDoc.getCredLevelPar() - issCredRatJson.getCredLevelPar();
				if (issCredLevelPar > 0) {
					issCredLevelParFlag = 1;
				} else if (issCredLevelPar < 0) {
					issCredLevelParFlag = -1;
				}
				//主体评级
				handleIssRating(issCredRatJson, hisIcrDoc, favRadarMapping, issCredLevelParFlag);
				//主体展望
				handleIssPros(issCredRatJson, hisIcrDoc, favRadarMapping);
			}
		} else {
			//主体初评
			handleIssInitialEvalu(issCredRatJson, favRadarMapping);
		}
	}

	private void handleIssInitialEvalu(IssCredRatingJson issCredRatJson, BondFavoriteRadarMappingDoc favRadarMapping) {
		if (issCredRatJson.getCredLevel() != null) {
			String clcontent = "主体评级初评" + issCredRatJson.getCredLevel() + "(" + issCredRatJson.getOrgShortName()
					+ ")";
			notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(favRadarMapping.getUserId(), favRadarMapping.getBondUniCode(),
					Constants.EVENMSG_TYPE_ISSRATE, clcontent, new Date(), 0L, 0, favRadarMapping.getGroupId(),
					EmotionTagEnum.DEFAULT.getCode(), favRadarMapping.getNotifiedEnable()));
		}
		if (StringUtils.isNotBlank(issCredRatJson.getParName())) {
			String pncontent = "主体展望初评" + issCredRatJson.getParName() + "(" + issCredRatJson.getOrgShortName()
					+ ")";
			notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(favRadarMapping.getUserId(), favRadarMapping.getBondUniCode(),
					Constants.EVENMSG_TYPE_ISSRATE, pncontent, new Date(), 0L, 0, favRadarMapping.getGroupId(),
					EmotionTagEnum.DEFAULT.getCode(), favRadarMapping.getNotifiedEnable()));
		}
	}

	private void handleIssPros(IssCredRatingJson issCredRatJson, IssCredRatingDoc hisIcrDoc,
			BondFavoriteRadarMappingDoc favRadarMapping) {
		if (null != issCredRatJson.getRateProsPar()) {
			if (null != hisIcrDoc.getRateProsPar()) {
				int rateProsParFlag = hisIcrDoc.getRateProsPar() - issCredRatJson.getRateProsPar();
				
				String content = "";
				int emotionTag = EmotionTagEnum.DEFAULT.getCode();
				if (rateProsParFlag == 0) {
					content = "主体展望维持原有的" + hisIcrDoc.getParName() + "展望("
							+ issCredRatJson.getOrgShortName() + ")";
				}else{
					content = "主体展望从原有的" + hisIcrDoc.getParName() + "调整至"
							+ issCredRatJson.getParName() + "(" + issCredRatJson.getOrgShortName() + ")";
					if (rateProsParFlag > 0) {
						emotionTag = EmotionTagEnum.GOOD.getCode();
					}else{
						emotionTag = EmotionTagEnum.RISK.getCode();
					}
				}
				
				notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(favRadarMapping.getUserId(), favRadarMapping.getBondUniCode(),
						Constants.EVENMSG_TYPE_ISSRATE, content, new Date(), 0L, 0,
						favRadarMapping.getGroupId(), emotionTag, favRadarMapping.getNotifiedEnable()));
			} else {
				String appfhContent = "主体展望调整至" + issCredRatJson.getParName() + "("
						+ issCredRatJson.getOrgShortName() + ")";
				
				notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(favRadarMapping.getUserId(), favRadarMapping.getBondUniCode(),
						Constants.EVENMSG_TYPE_ISSRATE, appfhContent, new Date(), 0L, 0,
						favRadarMapping.getGroupId(), EmotionTagEnum.DEFAULT.getCode(), favRadarMapping.getNotifiedEnable()));
			}
		}
	}

	private void handleIssRating(IssCredRatingJson issCredRatJson, IssCredRatingDoc hisIcrDoc,
			BondFavoriteRadarMappingDoc favRadarMapping, int issCredLevelParFlag) {
		switch (issCredLevelParFlag) {
		case 0:
			String rppContent = "主体评级维持原有的" + hisIcrDoc.getCredLevel() + "评级(" + hisIcrDoc.getOrgShortName()
					+ ")";
			notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(favRadarMapping.getUserId(), favRadarMapping.getBondUniCode(),
					Constants.EVENMSG_TYPE_ISSRATE, rppContent, new Date(), 0L, 0,
					favRadarMapping.getGroupId(), EmotionTagEnum.DEFAULT.getCode(), favRadarMapping.getNotifiedEnable()));
			break;
		case 1:
			String isshContent = "主体评级从原有的" + hisIcrDoc.getCredLevel() + "上调至" + issCredRatJson.getCredLevel()
					+ "(" + issCredRatJson.getOrgShortName() + ")";
			notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(favRadarMapping.getUserId(), favRadarMapping.getBondUniCode(),
					Constants.EVENMSG_TYPE_ISSRATE, isshContent, new Date(), 0L, 0,
					favRadarMapping.getGroupId(), EmotionTagEnum.GOOD.getCode(), favRadarMapping.getNotifiedEnable()));
			break;
		case -1:
			String isslContent = "主体评级从原有的" + hisIcrDoc.getCredLevel() + "下降至" + issCredRatJson.getCredLevel()
					+ "(" + issCredRatJson.getOrgShortName() + ")";
			notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(favRadarMapping.getUserId(), favRadarMapping.getBondUniCode(),
					Constants.EVENMSG_TYPE_ISSRATE, isslContent, new Date(), 0L, 0,
					favRadarMapping.getGroupId(), EmotionTagEnum.RISK.getCode(), favRadarMapping.getNotifiedEnable()));
			break;
		}
	}
}
