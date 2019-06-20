package com.innodealing.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.innodealing.amqp.SenderService;
import com.innodealing.bond.service.BondQuantAnalysisService;
import com.innodealing.engine.mongo.bond.BondBasicInfoRepository;
import com.innodealing.engine.mongo.bond.BondDetailRepository;
import com.innodealing.json.portfolio.BondIdxPriceJson;
import com.innodealing.model.dm.bond.BondDeal;
import com.innodealing.model.mongo.dm.BondBasicInfoDoc;
import com.innodealing.model.mongo.dm.BondDetailDoc;
import com.innodealing.util.BondDealUtil;

/**
 * @author feng.ma
 * @date 2017年3月28日 上午10:37:50
 * @describe
 */
@Service
public class BondEventMsgService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BondEventMsgService.class);

	private @Autowired BondDetailRepository bondDetailRep;

	private @Autowired JdbcTemplate jdbcTemplate;
	
	private @Autowired BondBasicInfoRepository bondBasicInfoRep;
	
	private @Autowired SenderService senderService;

	private @Autowired BondQuantAnalysisService bondQuantService;
	
	@Async
	@EventListener
	public void handleBondDealMsg(BondDeal vo) {
		if (null == vo) {
			return;
		}
		
		BigDecimal bondRate = vo.getBondRate();
		Long bondUniCode = vo.getBondUniCode();
		
		if (null == bondUniCode) {
			LOGGER.info("handleBondDealMsg BondDeal:"+JSON.toJSONString(vo)+", bondUniCode is null,BondName:"+vo.getBondName());
			return;
		}
		BondBasicInfoDoc bondBasicInfo = bondBasicInfoRep.findOne(bondUniCode);
		//1344 价格异常只针对信用债
		if(!BondDealUtil.isCreditDebt(bondBasicInfo.getDmBondType())){
			return;
		}
		
		//1339 异常成交价只显示8:30-17:00
		if (!BondDealUtil.isInTime0830And1700(vo.getCreateTime())) {
			return;
		}
		
		sendIdxPriceToMq(vo, bondRate, bondUniCode, bondBasicInfo);
		//mainHandler(vo, bondRate, bondUniCode, bondBasicInfo);
	}

	private void sendIdxPriceToMq(BondDeal vo, BigDecimal bondRate, Long bondUniCode, BondBasicInfoDoc bondBasicInfo) {
		BondDetailDoc bondDetailDoc = bondDetailRep.findByBondUniCode(bondUniCode);
		if (null != bondDetailDoc) {
			String code = bondDetailDoc.getCode();
			BigDecimal valuation = getValuationInBondInfo(code);
			
			Double cleanPrice = bondQuantService.calCleanPriceByYTM(bondUniCode, bondRate.doubleValue());
			if (null == cleanPrice) {
				cleanPrice =  new Double(0);
			}
			
			if (isNotExpiredBondDetail(bondDetailDoc)) {
				senderService.sendBondIdxprice2Rabbitmq(JSON.toJSONString(new BondIdxPriceJson(bondUniCode, 1, bondRate, new BigDecimal(cleanPrice), valuation)));
			}

		}
	}

	private BigDecimal getValuationInBondInfo(String code) {
		try {
			String sql = "SELECT CASE WHEN b.exercise_yield IS NOT NULL AND b.exercise_yield !=0 THEN b.exercise_yield  WHEN b.rate IS NOT NULL AND b.rate !=0 THEN b.rate ELSE 0 END AS rate from innodealing.bond_info b WHERE b.code='" + code	+ "' ORDER BY b.create_time DESC LIMIT 1";

			return jdbcTemplate.queryForObject(sql, BigDecimal.class);
		} catch (Exception ex) {
			//LOGGER.error("getValuationInBondInfo error," + ex.getMessage(), ex);
		}
		return null;
	}
	
    private boolean isNotExpiredBondDetail(BondDetailDoc detailDoc){
    	boolean result = false;
    	if (null != detailDoc.getCurrStatus() && detailDoc.getCurrStatus() == 1 
    			&& null != detailDoc.getIssStaPar() && detailDoc.getIssStaPar() == 1) {
    		result = true;
		}
    	return result;
    }
}
