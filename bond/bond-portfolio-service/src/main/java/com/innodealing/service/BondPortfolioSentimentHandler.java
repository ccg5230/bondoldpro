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
import com.innodealing.json.portfolio.SentimentBulletinJson;
import com.innodealing.json.portfolio.SentimentnewsJson;
import com.innodealing.model.mongo.dm.BondBasicInfoDoc;
import com.innodealing.model.mongo.dm.BondFavSentimentIdxDoc;
import com.innodealing.util.SafeUtils;

@Service
public class BondPortfolioSentimentHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(BondPortfolioSentimentHandler.class);

	@Autowired
	private BondPortfolioNotificationMsgHandler notificationMsgHandler;

	@Autowired
	private BondBasicInfoRepository bondBasicInfoRep;

	@Autowired
	private MongoOperations mongoOperations;

	// 原先的舆情的处理
	public void handleBondSentimentnews(SentimentnewsJson sentiNewsJson) {
		if (null == sentiNewsJson) {
			return;
		}

		Long comUniCode = SafeUtils.getLong(sentiNewsJson.getComUniCode());
		Query query = new Query();
		query.addCriteria(
				Criteria.where("comUniCode").is(comUniCode).and("radarId").is(FavRadarSchemaEnum.NEWS_SENS.getCode()));

		List<BondFavSentimentIdxDoc> favRatingIdxes = mongoOperations.find(query, BondFavSentimentIdxDoc.class);
		favRatingIdxes.stream().forEach(favSentimentIdx -> {

			if (isValidBond(favSentimentIdx.getBondUniCode())) {

				notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(
						favSentimentIdx.getUserId(), favSentimentIdx.getBondUniCode(),
						Constants.EVENMSG_TYPE_NEWSWARNING, sentiNewsJson.getTitle(), sentiNewsJson.getPubDate(),
						SafeUtils.getLong(sentiNewsJson.getIndex()), SafeUtils.getInteger(sentiNewsJson.getImportant()),
						favSentimentIdx.getGroupId(), 0, favSentimentIdx.getNotifiedEnable()));
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

	// 舆情公告处理
	public void handleBondSentimentBulletin(SentimentBulletinJson sentiBulletinJson) {
		if (null == sentiBulletinJson) {
			return;
		}
		// 只需要发布时间2天之内的舆情
		if (SafeUtils.getHoursBetween(sentiBulletinJson.getPubDate(), new Date()) > 48.00) {
			return;
		}
		int sentiType = SafeUtils.getInt(sentiBulletinJson.getSentiType());
		if (sentiType == 1) {
			sentiType = FavRadarSchemaEnum.ANOUNCE_SENS.getCode();
		} else {
			sentiType = FavRadarSchemaEnum.NEWS_SENS.getCode();
		}

		Long comUniCode = SafeUtils.getLong(sentiBulletinJson.getComUniCode());
		Query query = new Query();
		query.addCriteria(Criteria.where("comUniCode").is(comUniCode).and("radarId").is(sentiType).and("createTime").lte(sentiBulletinJson.getPubDate()));
		List<BondFavSentimentIdxDoc> favRatingIdxes = mongoOperations.find(query, BondFavSentimentIdxDoc.class);
		favRatingIdxes.parallelStream().forEach(favSentimentIdx -> {
			if (isValidBond(favSentimentIdx.getBondUniCode())) {

				notificationMsgHandler.pubNotificationMsg(
						BondPortfolioNotificationMsgHandler.buildMsgParam(favSentimentIdx.getUserId(),
								favSentimentIdx.getBondUniCode(), SafeUtils.getInteger(favSentimentIdx.getRadarId()),
								sentiBulletinJson.getMsgTitle(), sentiBulletinJson.getPubDate(),
								SafeUtils.getLong(sentiBulletinJson.getId()), 0, favSentimentIdx.getGroupId(),
								sentiBulletinJson.getEmotionTag(), favSentimentIdx.getNotifiedEnable()));
			} else {
				LOGGER.info("handleBondSentimentBulletin comUniCode:" + comUniCode + ",BondUniCode["
						+ favSentimentIdx.getBondUniCode() + "] is expired.");
			}
		});
	}
}
