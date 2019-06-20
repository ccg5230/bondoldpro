package com.innodealing.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.innodealing.json.portfolio.BondMaturityJson;
import com.innodealing.model.mongo.dm.BondFavMaturityIdxDoc;
import com.innodealing.model.mongo.dm.BondFavoriteRadarMappingDoc;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;

@Service
public class BondPortfolioMaturityEventHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(BondPortfolioMaturityEventHandler.class);

	@Autowired
	private BondPortfolioNotificationMsgHandler notificationMsgHandler;

	@Autowired
	private MongoOperations mongoOperations;

	public void handleBondMaturity(BondMaturityJson maturityJson) {
		// 查询Index条件
		Long bondUniCode = maturityJson.getBondUniCode();
		Query query = new Query();
		query.addCriteria(Criteria.where("bondUniCode").is(bondUniCode).and("notifiedEnable").is(1).and("radarId")
				.in(Arrays.asList(FavRadarSchemaEnum.YEAR_PAY.getCode(), FavRadarSchemaEnum.THEO_PAY.getCode(),
						FavRadarSchemaEnum.EXER_DECL.getCode(), FavRadarSchemaEnum.EXER_APPL_START.getCode(),
						FavRadarSchemaEnum.EXER_APPL_END.getCode())));

		List<BondFavMaturityIdxDoc> favMaturityIdxes = mongoOperations.find(query, BondFavMaturityIdxDoc.class);
		if (null != favMaturityIdxes && favMaturityIdxes.size() > 0) {
			favMaturityIdxes.parallelStream().forEach(favMaturityIdx -> {
				// 处理Index条件，生成消息
				switch (SafeUtils.getInt(favMaturityIdx.getRadarId())) {
				// 付息日
				case 7:
					handleYearPayDateMsg(maturityJson, favMaturityIdx);
					break;
				// 到期日
				case 8:
					handlTheoEndDateMsg(maturityJson, favMaturityIdx);
					break;
				// 公告日期
				case 21:
					handleExerDeclDateMsg(maturityJson, favMaturityIdx);
					break;
				//行权申报起始日
				case 22:
					handleExerApplStartMsg(maturityJson, favMaturityIdx);
					break;
				//行权申报截止日
				case 23:
					handleExerApplEndMsg(maturityJson, favMaturityIdx);
					break;
				}
			});
		}
	}

	private void handleYearPayDateMsg(BondMaturityJson maturityJson, BondFavoriteRadarMappingDoc favRadarMapping) {
		if (StringUtils.isBlank(maturityJson.getYearPayDate()))
			return;
		if (maturityJson.getTheoEndDate() == null)
			return;

		String yearPayDateStr = maturityJson.getYearPayDate();
		String[] payDateStr = yearPayDateStr.split(",");

		List<Date> matchYears = new ArrayList<Date>();
		matchYears.add(new Date());
		matchYears.add(nextYear(new Date()));

		for (int i = 0; i < payDateStr.length; i++) {
			for (Date year : matchYears) {
				String ypdateStr = SafeUtils.getFormatDate(year, "yyyy") + "-" + payDateStr[i];
				Date yearPayDate = SafeUtils.parseDate(ypdateStr, SafeUtils.DATE_FORMAT);
				Date today = SafeUtils.parseDate(SafeUtils.getCalendarN(1), SafeUtils.DATE_FORMAT);
				if (null != yearPayDate) {
					int daysoffset = SafeUtils.getDaysBetween(today, yearPayDate);
					if (daysoffset == favRadarMapping.getThreshold().intValue()) {
						String ypdcontent = "将于" + ypdateStr + "付息";
						notificationMsgHandler.pubNotificationMsg(
								BondPortfolioNotificationMsgHandler.buildMsgParam(favRadarMapping.getUserId(),
										maturityJson.getBondUniCode(), Constants.EVENMSG_TYPE_YEAR_PAY, ypdcontent,
										new Date(), 0L, 0, favRadarMapping.getGroupId(),
										EmotionTagEnum.DEFAULT.getCode(), favRadarMapping.getNotifiedEnable()));
						return;
					}
				}
			}
		}
	}

	private Date nextYear(Date dt) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.YEAR, 1);
		dt = c.getTime();
		return dt;
	}

	private void handlTheoEndDateMsg(BondMaturityJson maturityJson, BondFavoriteRadarMappingDoc favRadarMapping) {
		if (null == maturityJson.getTheoDiffdays()) {
			return;
		}
		
		if (maturityJson.getTheoDiffdays() == favRadarMapping.getThreshold().intValue()) {
			String tedcontent = "将于" + SafeUtils.getFormatDate(maturityJson.getTheoEndDate(), SafeUtils.DATE_FORMAT)
					+ "到期";
			notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(
					favRadarMapping.getUserId(), maturityJson.getBondUniCode(), Constants.EVENMSG_TYPE_THEO_PAY,
					tedcontent, new Date(), 0L, 0, favRadarMapping.getGroupId(), EmotionTagEnum.DEFAULT.getCode(),
					favRadarMapping.getNotifiedEnable()));
		}
	}

	@Deprecated
	private void handleExerPayDateMsg(BondMaturityJson maturityJson, BondFavoriteRadarMappingDoc favRadarMapping) {
		if (!StringUtils.isNotBlank(maturityJson.getExerPayDate()))
			return;
		Date begin = new Date();
		begin = SafeUtils.parseDate(SafeUtils.getFormatDate(begin, SafeUtils.DATE_FORMAT), SafeUtils.DATE_FORMAT);

		String exerPayDateText = maturityJson.getExerPayDate();
		String[] exerPayDateStrList = exerPayDateText.split(",");

		for (String exerPayDateStr : exerPayDateStrList) {
			final List<DateFormat> formatters = new ArrayList<>(Arrays.asList(new SimpleDateFormat("yyyy-MM-dd"),
					new SimpleDateFormat("yyyy年MM月dd日"), new SimpleDateFormat("yyyy年MM月dd日。")));
			for (DateFormat formatter : formatters) {
				try {
					Date exerPayDate = (Date) formatter.parse(exerPayDateStr);
					if (null != exerPayDate) {
						int daysoffset = SafeUtils.getDaysBetween(begin, exerPayDate);
						if (daysoffset == favRadarMapping.getThreshold().intValue()) {
							String epdcontent = "将于" + SafeUtils.getFormatDate(exerPayDate, SafeUtils.DATE_FORMAT)
									+ "行权";
							notificationMsgHandler.pubNotificationMsg(
									BondPortfolioNotificationMsgHandler.buildMsgParam(favRadarMapping.getUserId(),
											maturityJson.getBondUniCode(), Constants.EVENMSG_TYPE_EXER_PAY, epdcontent,
											new Date(), 0L, 0, favRadarMapping.getGroupId(),
											EmotionTagEnum.DEFAULT.getCode(), favRadarMapping.getNotifiedEnable()));
						}
					}
				} catch (java.text.ParseException e) {
					// LOGGER.error(e.getMessage(), e);
				}
			}
		}
	}

	private void handleExerApplEndMsg(BondMaturityJson maturityJson, BondFavMaturityIdxDoc favMaturityIdx) {
		Date today = new Date();
		String backOneDt = SafeUtils.getCalendar(0);
		LOGGER.info("handleExerApplEndMsg backOneDt:" + backOneDt + ",today:" + today + ",maturityJson:"
				+ maturityJson.toString());
		
		if (null != maturityJson.getApplEndDate() && SafeUtils.isCurrYear(maturityJson.getApplEndDate())) {
			if (SafeUtils.parseDate(backOneDt, SafeUtils.DATE_TIME_FORMAT1)
					.compareTo(maturityJson.getApplEndDate()) == 0) {
				String endcontent = "于" + SafeUtils.getFormatDate(maturityJson.getApplEndDate(), SafeUtils.DATE_FORMAT)
						+ "结束行权";

				notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(
						favMaturityIdx.getUserId(), maturityJson.getBondUniCode(), Constants.EVENMSG_TYPE_EXER_APPL_END,
						endcontent, new Date(), 0L, 0, favMaturityIdx.getGroupId(), EmotionTagEnum.DEFAULT.getCode(),
						favMaturityIdx.getNotifiedEnable()));
			}
		}
	}
	
	private void handleExerApplStartMsg(BondMaturityJson maturityJson, BondFavMaturityIdxDoc favMaturityIdx) {
		Date today = new Date();
//		int threshold = 1 - favMaturityIdx.getThreshold().intValue();
		String backOneDt = SafeUtils.getCalendar(0);
		LOGGER.info("handleExerApplStartMsg backOneDt:" + backOneDt + ",today:" + today + ",maturityJson:"
				+ maturityJson.toString());
		
		if (null != maturityJson.getApplStartDate() && SafeUtils.isCurrYear(maturityJson.getApplStartDate())) {
			if (SafeUtils.parseDate(backOneDt, SafeUtils.DATE_TIME_FORMAT1)
					.compareTo(maturityJson.getApplStartDate()) == 0) {
				String bgncontent = "于" + SafeUtils.getFormatDate(maturityJson.getApplStartDate(), SafeUtils.DATE_FORMAT)
						+ "开始行权";

				notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(
						favMaturityIdx.getUserId(), maturityJson.getBondUniCode(), Constants.EVENMSG_TYPE_EXER_APPL_START,
						bgncontent, new Date(), 0L, 0, favMaturityIdx.getGroupId(), EmotionTagEnum.DEFAULT.getCode(),
						favMaturityIdx.getNotifiedEnable()));
			}
		}
	}

	private void handleExerDeclDateMsg(BondMaturityJson maturityJson, BondFavMaturityIdxDoc favMaturityIdx) {
		Date today = new Date();
		LOGGER.info("handleExerDeclDateMsg  today:" + today + ",maturityJson:"
				+ maturityJson.toString());

		if (null != maturityJson.getDeclDate() && SafeUtils.isCurrYear(maturityJson.getDeclDate())) {
			if (maturityJson.getDeclDate().after(today) || 
					maturityJson.getDeclDate().compareTo(SafeUtils.parseDate(SafeUtils.getCalendar(1), SafeUtils.DATE_TIME_FORMAT1)) == 0) {
				String declcontent = "于" + SafeUtils.getFormatDate(maturityJson.getDeclDate(), SafeUtils.DATE_FORMAT)
						+ "发布行权公告";

				notificationMsgHandler.pubNotificationMsg(BondPortfolioNotificationMsgHandler.buildMsgParam(
						favMaturityIdx.getUserId(), maturityJson.getBondUniCode(), Constants.EVENMSG_TYPE_EXER_DECL,
						declcontent, new Date(), 0L, 0, favMaturityIdx.getGroupId(), EmotionTagEnum.DEFAULT.getCode(),
						favMaturityIdx.getNotifiedEnable()));
			}
		}
	}

}
