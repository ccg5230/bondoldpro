package com.innodealing.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.innodealing.json.portfolio.IssPdRatingJson;
import com.innodealing.model.mongo.dm.BondFavRatingIdxDoc;
import com.innodealing.model.mongo.dm.BondFavoriteRadarMappingDoc;
import com.innodealing.model.mongo.dm.IssPdRatingDoc;
import com.innodealing.util.SafeUtils;

@Service
public class BondPortfolioIssPdHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(BondPortfolioIssPdHandler.class);

	@Autowired
	private BondPortfolioNotificationMsgHandler notificationMsgHandler;

	@Autowired
	private MongoOperations mongoOperations;

	// 量化风险等级
	public void handleBondIsspdrat(IssPdRatingJson issPdRatJson) {
		// 查询Index条件
		Long comUniCode = issPdRatJson.getComUniCode();
		Query query = new Query();
		query.addCriteria(
				Criteria.where("comUniCode").is(comUniCode).and("radarId").is(FavRadarSchemaEnum.ISSPD_RAT.getCode()));
		List<BondFavRatingIdxDoc> favRatingIdxes = mongoOperations.find(query, BondFavRatingIdxDoc.class);

		if (null != favRatingIdxes && favRatingIdxes.size() > 0) {
			favRatingIdxes.parallelStream().forEach(favRatingIdx -> {

				handleIssPdMsg(issPdRatJson, favRatingIdx);
			});
		}
	}

	private void handleIssPdMsg(IssPdRatingJson issPdRatDoc, BondFavoriteRadarMappingDoc favRadarMapping) {
		Long compId = issPdRatDoc.getCompId();
		IssPdRatingDoc histIprDoc = mongoOperations.findOne(new Query(Criteria.where("compId").is(compId)), IssPdRatingDoc.class);
		
		Integer emotionTag = EmotionTagEnum.DEFAULT.getCode();
		if (null != histIprDoc) {
			Calendar curPdTime = null;
			Calendar histPdTime = null;
			
			try {
				curPdTime = convertStringToDay(issPdRatDoc.getWorstPdTime());
				histPdTime = convertStringToDay(histIprDoc.getWorstPdTime());
			} catch (ParseException e) {
				LOGGER.error("failed to convert time", e);
				e.printStackTrace();
				return;
			}

			if (curPdTime != null && histPdTime != null && curPdTime.after(histPdTime)) {
				LOGGER.info("handleIssPdMsg diff, newIprDoc:" + issPdRatDoc.toString() + ", histIprDoc:"
						+ histIprDoc.toString());
				int pdParDiff = histIprDoc.getPdParNum() - issPdRatDoc.getPdParNum();
				if (pdParDiff > 0) {
					// 上升
					String pdhContent = "主体量化风险等级从" + histIprDoc.getPdRating() + "上调至" + issPdRatDoc.getPdRating();
					notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(favRadarMapping.getUserId(), favRadarMapping.getBondUniCode(),
							Constants.EVENMSG_TYPE_ISSQURAT, pdhContent, new Date(), 0L, 0,
							favRadarMapping.getGroupId(), EmotionTagEnum.GOOD.getCode(), favRadarMapping.getNotifiedEnable()));
				} else if (pdParDiff < 0) {
					// 下降
					if (issPdRatDoc.getPdParNum() >= 18) {
						String pdlContent = "主体量化风险等级从" + histIprDoc.getPdRating() + "下降至" + issPdRatDoc.getPdRating()
								+ ",达到违约预警级别";
						notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(favRadarMapping.getUserId(), favRadarMapping.getBondUniCode(),
								Constants.EVENMSG_TYPE_ISSQURAT, pdlContent, new Date(), 0L, 0,
								favRadarMapping.getGroupId(), EmotionTagEnum.RISK.getCode(), favRadarMapping.getNotifiedEnable()));
					} else {
						String pdlContent = "主体量化风险等级从" + histIprDoc.getPdRating() + "下降至"
								+ issPdRatDoc.getPdRating();
						notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(favRadarMapping.getUserId(), favRadarMapping.getBondUniCode(),
								Constants.EVENMSG_TYPE_ISSQURAT, pdlContent, new Date(), 0L, 0,
								favRadarMapping.getGroupId(), EmotionTagEnum.RISK.getCode(), favRadarMapping.getNotifiedEnable()));
					}
				} else {
					String pdeContent = "主体量化风险等级维持" + histIprDoc.getPdRating();
					notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(favRadarMapping.getUserId(), favRadarMapping.getBondUniCode(),
							Constants.EVENMSG_TYPE_ISSQURAT, pdeContent, new Date(), 0L, 0,
							favRadarMapping.getGroupId(), emotionTag, favRadarMapping.getNotifiedEnable()));

				}
			}
		} else {
			if (issPdRatDoc.getPdParNum() < 18) {
				String fpdhContent = "主体量化风险等级" + issPdRatDoc.getPdRating();
				notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(favRadarMapping.getUserId(), favRadarMapping.getBondUniCode(),
						Constants.EVENMSG_TYPE_ISSQURAT, fpdhContent, new Date(), 0L, 0, favRadarMapping.getGroupId(),
						emotionTag, favRadarMapping.getNotifiedEnable()));
			} else {
				String pdhContent = "主体量化风险等级" + issPdRatDoc.getPdRating()+",达到违约预警级别";
				notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(favRadarMapping.getUserId(), favRadarMapping.getBondUniCode(),
						Constants.EVENMSG_TYPE_ISSQURAT, pdhContent, new Date(), 0L, 0, favRadarMapping.getGroupId(),
						emotionTag, favRadarMapping.getNotifiedEnable()));
			}
		}
	}

	public static Calendar convertStringToDay(String date) throws ParseException {
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(SafeUtils.DATE_FORMAT);
			cal.setTime(sdf.parse(date));
			cal.set(Calendar.HOUR_OF_DAY, 0);
			return cal;
		} catch (Exception e) {
			return null;
		}

	}
}
