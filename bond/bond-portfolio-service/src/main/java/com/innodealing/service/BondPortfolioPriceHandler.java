package com.innodealing.service;

import java.math.BigDecimal;
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
import com.innodealing.json.portfolio.BondIdxPriceJson;
import com.innodealing.model.mongo.dm.BondFavPriceIdxDoc;

@Service
public class BondPortfolioPriceHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(BondPortfolioPriceHandler.class);

	@Autowired
	private BondPortfolioNotificationMsgHandler notificationMsgHandler;

	@Autowired
	private MongoOperations mongoOperations;
	
	@Autowired
	private BondUserRoleService userRoleService;
	
	// 价格指标
	public void handleBondIdxPrice(BondIdxPriceJson idxPriceJson) {
		int priceIdx = idxPriceJson.getPriceIndex();
		Long bondUniCode = idxPriceJson.getBondUniCode();

		Query query = new Query();
		query.addCriteria(Criteria.where("bondUniCode").is(bondUniCode).and("priceIndex").is(priceIdx).and("status")
				.is(1).and("isDelete").is(0));
		List<BondFavPriceIdxDoc> favPriceIdxDocs = mongoOperations.find(query, BondFavPriceIdxDoc.class);

		if (null == favPriceIdxDocs || favPriceIdxDocs.size() == 0) {
			return;
		}

		favPriceIdxDocs.parallelStream().forEach(favPriceIdx -> {
			LOGGER.info("传入数据idxPriceJson："+idxPriceJson.toString()+",条件数据favPriceIdx:"+favPriceIdx.toString());
			handlePricesChange(idxPriceJson, favPriceIdx);
		});
		
	}

	private void handlePricesChange(BondIdxPriceJson idxPriceJson, BondFavPriceIdxDoc favPriceIdx) {
		String priceIdxStr = "";
		StringBuffer content = new StringBuffer("%1$s%2$s为");
		int priceIdx = idxPriceJson.getPriceIndex();
		switch (priceIdx) {
		case BondPortfolioIdxConsts.INDEX_BONDPRICE:
			priceIdxStr = "成交价";
			handleIdxByPriceType(idxPriceJson, favPriceIdx, content, priceIdxStr);
			
			break;
		case BondPortfolioIdxConsts.INDEX_OFR_QOUTE:
			priceIdxStr = "卖出报价";
			handleIdxByPriceType(idxPriceJson, favPriceIdx, content, priceIdxStr);
			
			break;
		case BondPortfolioIdxConsts.INDEX_BID_QOUTE:
			priceIdxStr = "买入报价";
			handleIdxByPriceType(idxPriceJson, favPriceIdx, content, priceIdxStr);
			
			break;
		default:
			break;
		}
		
	}

	private void handleIdxByPriceType(BondIdxPriceJson idxPriceJson, BondFavPriceIdxDoc favPriceIdx,
			StringBuffer content, String priceIdxStr) {
		String priceTypeStr = "";
		int priceType = favPriceIdx.getPriceType();
		switch (priceType) {
		case BondPortfolioIdxConsts.INDEX_PRICETYPE_BONDYEILD:
			priceTypeStr = "收益率";
			extractedContent(content, priceIdxStr, priceTypeStr);
			pubBondPriceMsg(idxPriceJson, idxPriceJson.getBondYield(), favPriceIdx, content);
			break;
		case BondPortfolioIdxConsts.INDEX_PRICETYPE_CLEANPRICE:
			priceTypeStr = "净价";
			extractedContent(content, priceIdxStr, priceTypeStr);
			pubBondPriceMsg(idxPriceJson, idxPriceJson.getCleanPrice(), favPriceIdx, content);
			break;
		case BondPortfolioIdxConsts.INDEX_PRICETYPE_VALUDEVI:
			//1614 投资组合没有中债权限的用户看不到异常价格提醒
			if(userRoleService.isDebtRole(favPriceIdx.getUserId())){
				priceTypeStr = "收益率";
				extractedContent(content, priceIdxStr, priceTypeStr);
				pubValuDeviMsg(idxPriceJson, idxPriceJson.getValuDevi(), idxPriceJson.getBondYield(), favPriceIdx, content);
			}
			break;
		}
	}

	private static void extractedContent(StringBuffer content, String priceIdxStr, String priceTypeStr) {
		priceIdxStr = String.format(content.toString(), priceIdxStr, priceTypeStr);
		content.delete(0, content.length());
		content.append(priceIdxStr);
	}

	//收益率 or 净价
	private void pubBondPriceMsg(BondIdxPriceJson idxPriceJson, BigDecimal priceValue, BondFavPriceIdxDoc favPriceIdx,
			StringBuffer content) {
		if (null != priceValue && priceValue.compareTo(BigDecimal.ZERO) != 0 ) {
			int priceCondi = favPriceIdx.getPriceCondi();
			int subPrice = priceValue.setScale(4, BigDecimal.ROUND_HALF_UP).compareTo(favPriceIdx.getIndexValue().setScale(4, BigDecimal.ROUND_HALF_UP));
			switch (priceCondi) {
			case 1:
				if (subPrice > 0) {
					content.append(priceValue.setScale(4, BigDecimal.ROUND_HALF_UP));
					notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(favPriceIdx.getUserId(), idxPriceJson.getBondUniCode(),
							Constants.EVENMSG_TYPE_ABNPRICE, content.toString(), new Date(), 0L, 0,
							favPriceIdx.getGroupId(), EmotionTagEnum.DEFAULT.getCode(), favPriceIdx.getNotifiedEnable()));
				}
				break;
			case 2:
				if (subPrice < 0) {
					content.append(priceValue.setScale(4, BigDecimal.ROUND_HALF_UP));
					notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(favPriceIdx.getUserId(), idxPriceJson.getBondUniCode(),
							Constants.EVENMSG_TYPE_ABNPRICE, content.toString(), new Date(), 0L, 0,
							favPriceIdx.getGroupId(), EmotionTagEnum.DEFAULT.getCode(), favPriceIdx.getNotifiedEnable()));
				}
				break;
			}
		}
	}

	//估值偏离
	private void pubValuDeviMsg(BondIdxPriceJson idxPriceJson, BigDecimal valuDevi, BigDecimal bondYield,
			BondFavPriceIdxDoc favPriceIdx, StringBuffer content) {
		if ((null != valuDevi && valuDevi.compareTo(BigDecimal.ZERO) != 0) && (null != bondYield && bondYield.compareTo(BigDecimal.ZERO) != 0)) {
			BigDecimal diffBP = bondYield.subtract(valuDevi).multiply(new BigDecimal(100)).setScale(0,
					BigDecimal.ROUND_HALF_UP);
			
			if (diffBP.intValue() > 0 && diffBP.intValue() > favPriceIdx.getIndexValue().intValue()) {
				content.append(bondYield.toString()).append(",估值").append(valuDevi.toString()).append(",高估")
						.append(diffBP.intValue()).append("BP");
				
				notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(favPriceIdx.getUserId(), idxPriceJson.getBondUniCode(),
						Constants.EVENMSG_TYPE_ABNPRICE, content.toString(), new Date(), 0L, 0,
						favPriceIdx.getGroupId(), EmotionTagEnum.DEFAULT.getCode(),favPriceIdx.getNotifiedEnable()));
			}
			if (diffBP.intValue() < 0 && Math.abs(diffBP.intValue()) > favPriceIdx.getIndexValue().intValue()) {
				content.append(bondYield.toString()).append(",估值").append(valuDevi.toString()).append(",低估")
						.append(Math.abs(diffBP.intValue())).append("BP");
				
				notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(favPriceIdx.getUserId(), idxPriceJson.getBondUniCode(),
						Constants.EVENMSG_TYPE_ABNPRICE, content.toString(), new Date(), 0L, 0,
						favPriceIdx.getGroupId(), EmotionTagEnum.DEFAULT.getCode(),favPriceIdx.getNotifiedEnable()));
			}
		}
	}
	
}
