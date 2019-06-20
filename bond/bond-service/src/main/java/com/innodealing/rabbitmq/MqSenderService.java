package com.innodealing.rabbitmq;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.innodealing.consts.RabbitmqConstants;
import com.innodealing.json.portfolio.FinSpclindicatorJson;

/**
 * @author stephen.ma
 * @date 2016年6月8日
 * @clasename SenderService.java
 * @decription TODO
 */
@Component
public class MqSenderService {
    
	private static Logger LOGGER = LoggerFactory.getLogger(MqSenderService.class);
	
    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;
    
    public void sendBondIdxPrice2Rabbitmq(final String json) {
    	LOGGER.info("sendBondIdxPrice2Rabbitmq json:"+json);
    	
        this.rabbitMessagingTemplate.convertAndSend(RabbitmqConstants.MQ_DEFAULT_EXCHANGE,
        		RabbitmqConstants.MQ_QUEUE_PORTFOLIO_BOND_IDXPRICE, json);
    }
    
    /**
     * 发送指标变动通知
     * @param json
     */
    public void sendIndicatorForNotice(FinSpclindicatorJson json){
		if(json == null){
			LOGGER.error("消息不能为空");
			return;
		}
		Map<String,Object> map = new HashMap<>();
		map.put("comUniCode", json.getComUniCode());
		map.put("finQuarter", json.getFinQuarter());
		map.put("RANK", json.getRANK());
		map.put("SELF", json.getSELF());
		map.put("YOY", json.getYOY());
		map.put("finRptFlag", json.getFinRptFlag());
		String j = JSON.toJSONString(map);
		rabbitMessagingTemplate.convertAndSend(RabbitmqConstants.MQ_QUEUE_PORTFOLIO_BOND_FINSPCLINDIC,j);
		LOGGER.info("mq消息:" + j);
	}
    
    /**
	 * 处理营业周期
	 * @param special	
	 */
	private Map<String, Object> dealTradeCycle(Map<String, Object> indicator) {
		if(indicator == null){
			return indicator;
		}
		Object Invntry_Day =  indicator.get("Invntry_Day");
		Object AR_Day = indicator.get("AR_Day");
		if(Invntry_Day == null){
			Invntry_Day = new BigDecimal(0);
		}else{
			Invntry_Day = new BigDecimal(Invntry_Day.toString());
		}
		if(AR_Day == null){
			AR_Day = new BigDecimal(0);
		}else{
			AR_Day = new BigDecimal(AR_Day.toString());
		}
		BigDecimal tradeCycle = new BigDecimal(Invntry_Day.toString()).add(new BigDecimal(AR_Day.toString()));
		indicator.put("Invntry_Day+AR_Day",  tradeCycle);
		return indicator;
	}
	
	public void sendBondQuoteDmmsRabbitMQ(final String quoteDocJson) {
		
		//设置content_type
		Map<String,Object> headMap = new HashMap<String,Object>();
		headMap.put("contentType", "application/json");
		
    	LOGGER.info("sendBondQuoteDmmsRabbitMQ json:"+quoteDocJson);
        this.rabbitMessagingTemplate.convertAndSend(RabbitmqConstants.MQ_VENDOR_EXCHANGE,
        		RabbitmqConstants.MQ_VENDOR_QUOTE_BOND, quoteDocJson,headMap);
    }
	
}