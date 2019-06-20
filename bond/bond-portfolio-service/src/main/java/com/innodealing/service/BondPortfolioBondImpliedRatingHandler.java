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
import com.innodealing.json.portfolio.ImpliedRatingJson;
import com.innodealing.model.mongo.dm.BondFavRatingIdxDoc;
import com.innodealing.model.mongo.dm.BondFavoriteRadarMappingDoc;

@Service
public class BondPortfolioBondImpliedRatingHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(BondPortfolioBondImpliedRatingHandler.class);

	@Autowired
	private BondPortfolioNotificationMsgHandler notificationMsgHandler;

	@Autowired
	private MongoOperations mongoOperations;

	// 隐含评级
	public void handleBondImpliedrat(ImpliedRatingJson impliedRatJson) {
		// 查询Index条件
		Long bondUniCode = impliedRatJson.getBondUniCode();
		Query query = new Query();
		query.addCriteria(
				Criteria.where("bondUniCode").is(bondUniCode).and("radarId").is(FavRadarSchemaEnum.IMPL_RAT.getCode()));

		List<BondFavRatingIdxDoc> favRatingIdxes = mongoOperations.find(query, BondFavRatingIdxDoc.class);
		if (null != favRatingIdxes && favRatingIdxes.size() > 0) {
			favRatingIdxes.parallelStream().forEach(favRatingIdx -> {
				buildImpliedRatingMsg(impliedRatJson, favRatingIdx);
			});
		}
	}

	private void buildImpliedRatingMsg(ImpliedRatingJson impliedRatJson, BondFavoriteRadarMappingDoc favRadarMapping) {
		String fstImpliedRat = impliedRatJson.getFstImpliedRat();
		String secImpliedRat = impliedRatJson.getSecImpliedRat();
		Long bondId = impliedRatJson.getBondUniCode();
		int ratediff = impliedRatJson.getRateDiff();

		StringBuffer ratediffcon = new StringBuffer();
		if (ratediff < 0) {
			// 上调
			ratediffcon.append("中债隐含评级从原有的").append(secImpliedRat).append("上调至").append(fstImpliedRat);
			notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(favRadarMapping.getUserId(), bondId, Constants.EVENMSG_TYPE_IMRATING, ratediffcon.toString(),
					new Date(), 0L, 0, favRadarMapping.getGroupId(), EmotionTagEnum.GOOD.getCode(), favRadarMapping.getNotifiedEnable()));

		} else if (ratediff > 0) {
			// 下调
			ratediffcon.append("中债隐含评级从原有的").append(secImpliedRat).append("下调至").append(fstImpliedRat);
			notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(favRadarMapping.getUserId(), bondId, Constants.EVENMSG_TYPE_IMRATING, ratediffcon.toString(),
					new Date(), 0L, 0, favRadarMapping.getGroupId(), EmotionTagEnum.RISK.getCode(), favRadarMapping.getNotifiedEnable()));
		}
	}
}
