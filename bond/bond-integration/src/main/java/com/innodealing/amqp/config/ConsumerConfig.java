package com.innodealing.amqp.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import com.innodealing.amqp.ReceiverService;
import com.innodealing.consts.MqConstants;

/**
 * @author stephen.ma
 * @date 2016年6月8日
 * @clasename ConsumerConfig.java
 * @decription TODO
 */
@Configuration
@EnableRabbit
public class ConsumerConfig implements RabbitListenerConfigurer {

    @Autowired
    private ReceiverService receiverService;

    @Bean
    public DefaultMessageHandlerMethodFactory msgHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(new MappingJackson2MessageConverter());
        return factory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPrefetchCount(MqConstants.RABBIT_PREFETCH_COUNT);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }

    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    Queue queueBondItemStatus(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(MqConstants.MQ_QUEUE_BOND_ITEM_STATUS, false);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    Queue queueBondSrcMsg(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(MqConstants.MQ_QUEUE_BOND_SRCMSG, false);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    Queue queueBondNdBond(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(MqConstants.MQ_QUEUE_BOND, false);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    DirectExchange exchange(RabbitAdmin rabbitAdmin) {
        DirectExchange directExchange = new DirectExchange(MqConstants.MQ_DEFAULT_EXCHANGE, false, false);
        rabbitAdmin.declareExchange(directExchange);
        return directExchange;
    }
    
    @Bean
    Queue queueNewBondAnnAtt(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(MqConstants.MQ_QUEUE_NEWBOND_ANNATT_UPLOAD, false);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(msgHandlerMethodFactory());
    }

    @Bean
    Queue receiveBondItemStatus(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(MqConstants.MQ_QUEUE_BOND_ITEM_STATUS, false);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

}