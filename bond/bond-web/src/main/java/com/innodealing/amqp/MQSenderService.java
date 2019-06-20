package com.innodealing.amqp;

import com.innodealing.consts.RabbitmqConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MQSenderService {
    private final static Logger logger = LoggerFactory.getLogger(MQSenderService.class);

    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;

    public void sendMsg2SocketIO(final String json) {
        logger.info("sendMsg2SocketIo JSON:" + json);
        this.rabbitMessagingTemplate.convertAndSend(RabbitmqConstants.MQ_DEFAULT_EXCHANGE,
                RabbitmqConstants.MQ_QUEUE_SOCKETIO, json);
    }
}
