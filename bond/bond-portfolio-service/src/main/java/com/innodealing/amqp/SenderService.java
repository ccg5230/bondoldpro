package com.innodealing.amqp;

import com.innodealing.consts.MqConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author stephen.ma
 * @date 2016年6月8日
 * @clasename SenderService.java
 * @decription TODO
 */
@Component
public class SenderService {

    private final static Logger logger = LoggerFactory.getLogger(SenderService.class);

    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;

//    public void sendFinanceSrcmsg2Rabbitmq(final String json) {
//        this.rabbitMessagingTemplate.convertAndSend(MqConstants.MQ_DEFAULT_EXCHANGE,
//                MqConstants.MQ_QUEUE_BOND_SRCMSG, json);
//    }

    public void sendMsg2SocketIo(final String json) {
        logger.info("sendMsg2SocketIo JSON:" + json);
        this.rabbitMessagingTemplate.convertAndSend(MqConstants.MQ_DEFAULT_EXCHANGE,
                MqConstants.MQ_QUEUE_SOCKETIO, json);
    }
}