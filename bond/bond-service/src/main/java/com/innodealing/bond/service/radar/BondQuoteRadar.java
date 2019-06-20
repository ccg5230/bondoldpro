package com.innodealing.bond.service.radar;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innodealing.model.mongo.dm.BondDetailDoc;

/**
 * @author xiaochao
 * @time 2017年4月20日
 * @description: 债券成交价/报价预警雷达
 */
@Service
public class BondQuoteRadar implements BondRadarI {

	private static final Logger LOGGER = LoggerFactory.getLogger(BondQuoteRadar.class);
	
	private static final int radarLine = 20;
	// TODO 封装到对象
	private static String up = "";
	private static String down = "";
	
	private @Autowired BondRadarService bondNotiRadarService;
	
	static {
		up = "成交价%1$s,估值%2$s,高估%3$dBP";
		down = "成交价%1$s,估值%2$s,低估%3$dBP";
	}

	@Override
	public void trackingNotiEvent(Long bondUniCode, BigDecimal bondRate, Date pubDate) {
		BondDetailDoc bondDetailDoc = bondNotiRadarService.getBondDetailRepByBondUniCode(bondUniCode);
		if (null != bondDetailDoc) {
			String code = bondDetailDoc.getCode();
			BigDecimal valuation = bondNotiRadarService.getValuationInBondInfo(code);
			if (null != valuation && null != bondRate) {
				BigDecimal diffBP = bondRate.subtract(valuation).multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
				if (!isValid(diffBP.intValue())) return;
				String content = getTpl(diffBP.intValue(), bondRate, valuation);
				bondNotiRadarService.publishEvent(bondUniCode, content);
				bondNotiRadarService.handleBondTrendsDealdata(pubDate, bondRate, bondUniCode, bondDetailDoc, valuation, diffBP, 0);
			}
		} else {
			LOGGER.info(String.format("trackingNotiEvent: cannot get valuation of bondInfo with code[%1$d]", bondUniCode));
		}
	}
	
	private boolean isValid(int currValue) {
		if (currValue > radarLine || currValue < -radarLine) return true;
		return false;
	}
	
	private String getTpl(int currValue, BigDecimal bondRate, BigDecimal valuation) {
		return String.format(currValue > radarLine ? up : down, bondRate.toString(), valuation.toString(), currValue);
	}
}
