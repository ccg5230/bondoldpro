package com.innodealing.rabbitmq;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqSenderService {
    
	private static Logger LOGGER = LoggerFactory.getLogger(MqSenderService.class);
	
    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;
    
	/**
	 * 发送消息通知前端刷新信评数据
	 * @param json
	 */
	public void sendBondOrgMessageRabbitMQ(String json){
		//设置content_type
		Map<String,Object> headMap = new HashMap<String,Object>();
		headMap.put("contentType", "application/json");
		LOGGER.info("sendBondOrgMessageRabbitMQ json:"+json);
        this.rabbitMessagingTemplate.convertAndSend(RabbitmqConstants.MQ_DEFAULT_EXCHANGE,
        		RabbitmqConstants.MQ_SOCKET_IO, json,headMap);
	}
	
}