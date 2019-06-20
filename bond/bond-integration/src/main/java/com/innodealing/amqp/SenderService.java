package com.innodealing.amqp;

import com.innodealing.model.mongo.dm.BondDiscoveryAbnormalDealDoc;
import com.innodealing.model.mongo.dm.BondDiscoveryAbnormalQuoteDoc;
import com.innodealing.model.mongo.dm.BondDiscoveryTodayDealDetailDoc;
import com.innodealing.model.mongo.dm.BondDiscoveryTodayQuoteDetailDoc;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innodealing.consts.MqConstants;

/**
 * @author stephen.ma
 * @date 2016年6月8日
 * @clasename SenderService.java
 * @decription TODO
 */
@Component
public class SenderService {
    
	private static Logger LOGGER = Logger.getLogger(SenderService.class);
	
    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;
    
    public void sendBondMaturity2Rabbitmq(final String json) {
    	LOGGER.info("sendBondMaturity2Rabbitmq json:"+json);
    	
        this.rabbitMessagingTemplate.convertAndSend(MqConstants.MQ_DEFAULT_EXCHANGE,
                MqConstants.MQ_QUEUE_PORTFOLIO_BOND_MATURITY, json);
    }
    
    public void sendBondCredrat2Rabbitmq(final String json) {
    	LOGGER.info("sendBondCredrat2Rabbitmq json:"+json);
    	
        this.rabbitMessagingTemplate.convertAndSend(MqConstants.MQ_DEFAULT_EXCHANGE,
                MqConstants.MQ_QUEUE_PORTFOLIO_BOND_CREDRAT, json);
    }
    
    
    public void sendBondIsscredrat2Rabbitmq(final String json) {
    	LOGGER.info("sendBondIsscredrat2Rabbitmq json:"+json);
    	
        this.rabbitMessagingTemplate.convertAndSend(MqConstants.MQ_DEFAULT_EXCHANGE,
                MqConstants.MQ_QUEUE_PORTFOLIO_BOND_ISSCREDRAT, json);
    }
    
    public void sendBondIssPdrat2Rabbitmq(final String json) {
    	LOGGER.info("sendBondIssPdrat2Rabbitmq json:"+json);
    	
        this.rabbitMessagingTemplate.convertAndSend(MqConstants.MQ_DEFAULT_EXCHANGE,
                MqConstants.MQ_QUEUE_PORTFOLIO_BOND_ISSPDRAT, json);
    }
    
    public void sendBondImpliedrat2Rabbitmq(final String json) {
    	LOGGER.info("sendBondImpliedrat2Rabbitmq json:"+json);
    	
        this.rabbitMessagingTemplate.convertAndSend(MqConstants.MQ_DEFAULT_EXCHANGE,
                MqConstants.MQ_QUEUE_PORTFOLIO_BOND_IMPLIEDRAT, json);
    }
    
    public void sendBondIdxprice2Rabbitmq(final String json) {
    	LOGGER.info("sendBondIdxprice2Rabbitmq json:"+json);
    	
        this.rabbitMessagingTemplate.convertAndSend(MqConstants.MQ_DEFAULT_EXCHANGE,
                MqConstants.MQ_QUEUE_PORTFOLIO_BOND_IDXPRICE, json);
    }
    
    public void sendBondFinindic2Rabbitmq(final String json) {
    	LOGGER.info("sendBondFinindic2Rabbitmq json:"+json);
    	
        this.rabbitMessagingTemplate.convertAndSend(MqConstants.MQ_DEFAULT_EXCHANGE,
                MqConstants.MQ_QUEUE_PORTFOLIO_BOND_FININDIC, json);
    }
    
    public void sendBondFinspclindic2Rabbitmq(final String json) {
    	LOGGER.info("sendBondFinspclindic2Rabbitmq json:"+json);
    	
        this.rabbitMessagingTemplate.convertAndSend(MqConstants.MQ_DEFAULT_EXCHANGE,
                MqConstants.MQ_QUEUE_PORTFOLIO_BOND_FINSPCLINDIC, json);
    }
    
    public void sendBondSentimentnews2Rabbitmq(final String json) {
    	LOGGER.info("sendBondSentimentnews2Rabbitmq json:"+json);
    	
        this.rabbitMessagingTemplate.convertAndSend(MqConstants.MQ_DEFAULT_EXCHANGE,
                MqConstants.MQ_QUEUE_PORTFOLIO_BOND_SENTIMENTNEWS, json);
    }

    public void sendBondDiscoveryTodayDeal2RabbitMQ(BondDiscoveryTodayDealDetailDoc todayDealDoc) {
        this.rabbitMessagingTemplate.convertAndSend(MqConstants.MQ_DEFAULT_EXCHANGE,
                MqConstants.MQ_QUEUE_WS_BOND_DISCOVERY_DEAL, todayDealDoc);
    }

    public void sendBondDiscoveryTodayQuote2RabbitMQ(BondDiscoveryTodayQuoteDetailDoc todayQuoteDoc) {
        this.rabbitMessagingTemplate.convertAndSend(MqConstants.MQ_DEFAULT_EXCHANGE,
                MqConstants.MQ_QUEUE_WS_BOND_DISCOVERY_QUOTE, todayQuoteDoc);
    }

    public void sendBondDiscoveryAbnormalDeal2RabbitMQ(BondDiscoveryAbnormalDealDoc abnormalDealDoc) {
        this.rabbitMessagingTemplate.convertAndSend(MqConstants.MQ_DEFAULT_EXCHANGE,
                MqConstants.MQ_QUEUE_WS_BOND_DISCOVERY_AP_DEAL, abnormalDealDoc);
    }

    public void sendBondDiscoveryAbnormalQuote2RabbitMQ(BondDiscoveryAbnormalQuoteDoc abnormalQuoteDoc) {
        this.rabbitMessagingTemplate.convertAndSend(MqConstants.MQ_DEFAULT_EXCHANGE,
                MqConstants.MQ_QUEUE_WS_BOND_DISCOVERY_AP_QUOTE, abnormalQuoteDoc);
    }
    
    public void sendNewBondAtt2RabbitMQ(final String quoteDocJson) {
        this.rabbitMessagingTemplate.convertAndSend(MqConstants.MQ_DEFAULT_EXCHANGE,
                MqConstants.MQ_QUEUE_NEWBOND_ANNATT_UPLOAD, quoteDocJson);
    }
}