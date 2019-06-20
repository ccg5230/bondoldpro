package com.innodealing.amqp.config;

import com.innodealing.consts.RabbitmqConstants;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

@Configuration
public class MQProducerConfig {
    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    Queue queueSocketIo(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(RabbitmqConstants.MQ_QUEUE_SOCKETIO, false);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    DirectExchange exchange(RabbitAdmin rabbitAdmin) {
        DirectExchange directExchange = new DirectExchange(RabbitmqConstants.MQ_DEFAULT_EXCHANGE, false, false);
        rabbitAdmin.declareExchange(directExchange);
        return directExchange;
    }

    /**
     * 生产者用
     * @return
     */
    @Bean
    public RabbitMessagingTemplate rabbitMessagingTemplate(RabbitTemplate rabbitTemplate) {
        RabbitMessagingTemplate rabbitMessagingTemplate = new RabbitMessagingTemplate();
        rabbitMessagingTemplate.setRabbitTemplate(rabbitTemplate);
        return rabbitMessagingTemplate;
    }

    @Bean
    public MappingJackson2MessageConverter jackson2Converter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        return converter;
    }
}
