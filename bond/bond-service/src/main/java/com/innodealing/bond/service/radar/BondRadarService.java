package com.innodealing.bond.service.radar;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.innodealing.consts.Constants;
import com.innodealing.engine.mongo.bond.BondBasicInfoRepository;
import com.innodealing.engine.mongo.bond.BondDetailRepository;
import com.innodealing.engine.mongo.bond.BondTrendsDealdataRepository;
import com.innodealing.model.dm.bond.BondNotificationMsg;
import com.innodealing.model.mongo.dm.BondBasicInfoDoc;
import com.innodealing.model.mongo.dm.BondDetailDoc;
import com.innodealing.model.mongo.dm.BondTrendsDealdataDoc;

/**
 * @author xiaochao
 * @time 2017年4月20日
 * @description: 债券预警雷达服务类
 */
@Service
public class BondRadarService {
	
	private static final Logger LOG = LoggerFactory.getLogger(BondRadarService.class);

	private @Autowired JdbcTemplate jdbcTemplate;
	
	private @Autowired BondDetailRepository bondDetailRep;

	private @Autowired ApplicationEventPublisher publisher;
	
	private @Autowired BondBasicInfoRepository bondBasicInfoRep;
	
	private @Autowired BondTrendsDealdataRepository bondTrendsDealdataRep;

	public BondDetailDoc getBondDetailRepByBondUniCode(Long bondUniCode) {
		return bondDetailRep.findByBondUniCode(bondUniCode);
	}
	
	public BigDecimal getValuationInBondInfo(String code) {
		try {
			String sql = "SELECT b.rate from innodealing.bond_info b WHERE b.code='" + code	+ "' ORDER BY b.create_time DESC LIMIT 1";
			return jdbcTemplate.queryForObject(sql, BigDecimal.class);
		} catch (Exception ex) {
			LOG.error("getValuationInBondInfo error," + ex.getMessage(), ex);
		}
		return null;
	}

	public void publishEvent(Long bondId, String content) {
		BondNotificationMsg dealMsg = new BondNotificationMsg();
		dealMsg.setBondId(bondId);
		dealMsg.setEventType(Constants.EVENMSG_TYPE_QUOTEMNT);
		dealMsg.setCreateTime(new Date());
		dealMsg.setMsgContent(content);
		publisher.publishEvent(dealMsg);
	}
	
	public void handleBondTrendsDealdata(Date pubDate, BigDecimal bondRate, Long bondUniCode,
			BondDetailDoc bondDetailDoc, BigDecimal valuation, BigDecimal diffBP, Integer status) {
		BondBasicInfoDoc bondBasicInfo = bondBasicInfoRep.findOne(bondUniCode);
		BondTrendsDealdataDoc btdDoc = new BondTrendsDealdataDoc();
		btdDoc.setBondDealRate(bondRate);
		btdDoc.setBondName(bondDetailDoc.getName());
		btdDoc.setBondUniCode(bondUniCode);
		btdDoc.setCode(bondDetailDoc.getCode());
		btdDoc.setPubDate(pubDate);
		btdDoc.setValuation(status);
		btdDoc.setValuationRate(valuation);
		btdDoc.setValuationDeviation(diffBP);
		btdDoc.setTenor(bondDetailDoc.getTenor());
		btdDoc.setInduNameSw(bondDetailDoc.getInduNameSw());
		btdDoc.setInduName(bondDetailDoc.getInduName());
		btdDoc.setIssRating(bondDetailDoc.getIssRating());
		btdDoc.setBondRating(bondDetailDoc.getBondRating());
		if (null != bondBasicInfo) {
			btdDoc.setIssRatePros(bondBasicInfo.getIssRatePros());
			btdDoc.setIssCoupRate(bondBasicInfo.getIssCoupRate());
			btdDoc.setIssuer(bondBasicInfo.getIssuer());
			btdDoc.setIssuerId(bondBasicInfo.getIssuerId());
		}
		
		bondTrendsDealdataRep.save(btdDoc);
	}
}
