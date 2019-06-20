package com.innodealing.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.innodealing.consts.MqConstants;
import com.innodealing.domain.enums.EmotionTagEnum;
import com.innodealing.json.portfolio.BondCredRatingJson;
import com.innodealing.json.portfolio.BondIdxPriceJson;
import com.innodealing.json.portfolio.BondMaturityJson;
import com.innodealing.json.portfolio.ImpliedRatingJson;
import com.innodealing.json.portfolio.IssCredRatingJson;
import com.innodealing.json.portfolio.IssPdRatingJson;
import com.innodealing.json.portfolio.SentimentBulletinJson;
import com.innodealing.json.portfolio.SentimentLawsuitJson;
import com.innodealing.json.portfolio.SentimentnewsJson;
import com.innodealing.service.BondPortfolioBondCredEventHandler;
import com.innodealing.service.BondPortfolioBondImpliedRatingHandler;
import com.innodealing.service.BondPortfolioFinIndicatorHandler;
import com.innodealing.service.BondPortfolioIssCredEventHandler;
import com.innodealing.service.BondPortfolioIssPdHandler;
import com.innodealing.service.BondPortfolioMaturityEventHandler;
import com.innodealing.service.BondPortfolioPriceHandler;
import com.innodealing.service.BondPortfolioSentimentHandler;
import com.innodealing.service.BondPortfolioSentimentLawsuitHandler;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;

/**
 * @author stephen.ma
 * @clasename ReceiverService.java
 * @decription TODO
 */
@Component
public class ReceiverService {

	private final static Logger logger = LoggerFactory.getLogger(ReceiverService.class);
	
	@Autowired
	private BondPortfolioFinIndicatorHandler finIndicatorHandler;
	
	@Autowired
	private BondPortfolioBondCredEventHandler bondCredEventHandler;
	
	@Autowired
	private BondPortfolioMaturityEventHandler maturityEventHandler;
	
	@Autowired
	private BondPortfolioIssCredEventHandler issCredEventHandler;
	
	@Autowired
	private BondPortfolioIssPdHandler issPdHandler;
	
	@Autowired
	private BondPortfolioBondImpliedRatingHandler impliedRatingHandler;
	
	@Autowired
	private BondPortfolioPriceHandler priceHandler;
	
	@Autowired
	private BondPortfolioSentimentHandler sentimentHandler;
	
	@Autowired
	private BondPortfolioSentimentLawsuitHandler sentimentLawsuitHandler;
	
	@RabbitListener(queues = MqConstants.MQ_QUEUE_PORTFOLIO_BOND_MATURITY)
	public void receiveBondMaturity(String queue) {
		try {
			if (!StringUtils.isBlank(queue)) {
				// 主要处理逻辑
				logger.info("recive from queue.portfolio.bond_maturity,content is: " + queue);
				BondMaturityJson maturityJson = JSON.parseObject(queue, BondMaturityJson.class);
				
				maturityEventHandler.handleBondMaturity(maturityJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("receiveBondMaturity error:" + e.getMessage(), e);
		}
	}

	@RabbitListener(queues = MqConstants.MQ_QUEUE_PORTFOLIO_BOND_CREDRAT)
	public void receiveBondCredrat(String queue) {
		try {
			if (!StringUtils.isBlank(queue)) {
				// 主要处理逻辑
				logger.info("recive from queue.portfolio.bond_credrat,content is: " + queue);
				BondCredRatingJson credRatJson = JSON.parseObject(queue, BondCredRatingJson.class);
				
				bondCredEventHandler.handleBondCredRat(credRatJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("receiveBondCredrat error:" + e.getMessage(), e);
		}
	}

	@RabbitListener(queues = MqConstants.MQ_QUEUE_PORTFOLIO_BOND_ISSCREDRAT)
	public void receiveBondIsscredrat(String queue) {
		try {
			if (!StringUtils.isBlank(queue)) {
				// 主要处理逻辑
				logger.info("recive from queue.portfolio.bond_isscredrat, content is: " + queue);
				IssCredRatingJson issCredRatJson = JSON.parseObject(queue, IssCredRatingJson.class);
				
				issCredEventHandler.handleBondIsscredrat(issCredRatJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("receiveBondIsscredrat error:" + e.getMessage(), e);
		}
	}

	@RabbitListener(queues = MqConstants.MQ_QUEUE_PORTFOLIO_BOND_ISSPDRAT)
	public void receiveBondIsspdrat(String queue) {
		try {
			if (!StringUtils.isBlank(queue)) {
				// 主要处理逻辑
				logger.info("recive from queue.portfolio.bond_isspdrat, content is: " + queue);
				IssPdRatingJson issPdRatJson = JSON.parseObject(queue, IssPdRatingJson.class);
				
				issPdHandler.handleBondIsspdrat(issPdRatJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("receiveBondIsspdrat error:" + e.getMessage(), e);
		}
	}

	@RabbitListener(queues = MqConstants.MQ_QUEUE_PORTFOLIO_BOND_IMPLIEDRAT)
	public void receiveBondImpliedrat(String queue) {
		try {
			if (!StringUtils.isBlank(queue)) {
				// 主要处理逻辑
				logger.info("recive from queue.portfolio.bond_impliedrat, content is: " + queue);
				ImpliedRatingJson impliedRatJson = JSON.parseObject(queue, ImpliedRatingJson.class);
				
				impliedRatingHandler.handleBondImpliedrat(impliedRatJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("receiveBondImpliedrat error:" + e.getMessage(), e);
		}
	}

	@RabbitListener(queues = MqConstants.MQ_QUEUE_PORTFOLIO_BOND_IDXPRICE)
	public void receiveBondIdxprice(String queue) {
		try {
			if (!StringUtils.isBlank(queue)) {
				// 主要处理逻辑
				logger.info("recive from queue.portfolio.bond_idxprice, content is: " + queue);
				BondIdxPriceJson idxPriceJson = JSON.parseObject(queue, BondIdxPriceJson.class);
				
				priceHandler.handleBondIdxPrice(idxPriceJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("receiveBondIdxprice error:" + e.getMessage(), e);
		}
	}
	
	@RabbitListener(queues = MqConstants.MQ_QUEUE_PORTFOLIO_BOND_FININDIC)
	public void receiveBondFinindic(String queue) {
		try {
			if (!StringUtils.isBlank(queue)) {
				// 主要处理逻辑
				logger.info("recive from queue.portfolio.bond_finindic, content is: " + queue);
				//FinIndicatorJson finIndicatorJson = JSON.parseObject(queue, FinIndicatorJson.class);
				JSONObject jsonObj = JSON.parseObject(queue);

				finIndicatorHandler.handleBondFinIndic(jsonObj, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("receiveBondFinindic error:" + e.getMessage(), e);
		}
	}
	 
	
	@RabbitListener(queues = MqConstants.MQ_QUEUE_PORTFOLIO_BOND_FINSPCLINDIC)
	public void receiveBondFinSpclindic(String queue) {
		try {
			if (!StringUtils.isBlank(queue)) {
				// 主要处理逻辑
				logger.info("recive from queue.portfolio.bond_finspclindic, content is: " + queue);
				//FinSpclindicatorJson finSpclIndicJson = JSON.parseObject(queue, FinSpclindicatorJson.class);
				JSONObject jsonObj = JSON.parseObject(queue);

				finIndicatorHandler.handleBondFinIndic(jsonObj, false);
				//处理其他 披露财报
				finIndicatorHandler.handleBondFinRpt(jsonObj, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("receiveBondFinSpclindic error:" + e.getMessage(), e);
		}
	}

	@RabbitListener(queues = MqConstants.MQ_QUEUE_PORTFOLIO_BOND_SENTIMENTNEWS)
	public void receiveBondSentimentnews(String queue) {
		try {
			if (!StringUtils.isBlank(queue)) {
				// 主要处理逻辑
				logger.info("recive from queue.portfolio.bond_sentimentnews, content is: " + queue);
				SentimentnewsJson sentiNewsJson = JSON.parseObject(queue, SentimentnewsJson.class);
				
				sentimentHandler.handleBondSentimentnews(sentiNewsJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("receiveBondSentimentnews error:" + e.getMessage(), e);
		}
	}

	@RabbitListener(queues = MqConstants.MQ_QUEUE_PORTFOLIO_BOND_SENTIMENTLAWSUIT)
	public void receiveBondSentimentlawsuit(JSONObject queueObj) {
		try {
			if (null != queueObj) {
				// 主要处理逻辑
				logger.info("recive from bond_sentiment_law, content is: " + queueObj.toJSONString());
				SentimentLawsuitJson sentiLawsuitJson = JSON.parseObject(queueObj.toJSONString(), SentimentLawsuitJson.class);

				sentimentLawsuitHandler.handleBondSentimentLawsuit(sentiLawsuitJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("receiveBondSentimentlawsuit error:" + e.getMessage(), e);
		}
	}

	@RabbitListener(queues = MqConstants.MQ_QUEUE_PORTFOLIO_BOND_SENTIMENTBULLETIN)
	public void receiveBondSentimentBulletin(JSONObject queueObj) {
		try {
			if (null != queueObj) {
				// 主要处理逻辑
				logger.info("recive from dmms.bulletin, content is: " + queueObj.toJSONString());
				SentimentBulletinJson sentiBulletinJson = JSON.parseObject(queueObj.toJSONString(), SentimentBulletinJson.class);
				
				int tag = SafeUtils.getInt(sentiBulletinJson.getEmotionTag());
				switch(tag){
				case 1:
					sentiBulletinJson.setEmotionTag(EmotionTagEnum.RISK.getCode());
					break;
				case 2:
					sentiBulletinJson.setEmotionTag(EmotionTagEnum.NEUTRAL.getCode());
					break;
				case 3:
					sentiBulletinJson.setEmotionTag(EmotionTagEnum.GOOD.getCode());
					break;
				}
				sentimentHandler.handleBondSentimentBulletin(sentiBulletinJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("receiveBondSentimentBulletin error:" + e.getMessage(), e);
		}
	}
	
}