package com.innodealing.amqp.config;

import com.innodealing.consts.RabbitmqConstants;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
@EnableRabbit
public class MQConsumerConfig implements RabbitListenerConfigurer {
    @Bean
    public MappingJackson2MessageConverter jackson2Converter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        return converter;
    }

    @Bean
    public DefaultMessageHandlerMethodFactory msgHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(jackson2Converter());
        return factory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPrefetchCount(RabbitmqConstants.RABBIT_PREFETCH_COUNT);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }

    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    /**
     *债券->发现->今日成交
     * @param rabbitAdmin
     * @return
     */
    @Bean
    Queue queueBondDiscoveryTodayDeal(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(RabbitmqConstants.MQ_QUEUE_WS_BOND_DISCOVERY_DEAL, false);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    /**
     *债券->发现->今日报价
     * @param rabbitAdmin
     * @return
     */
    @Bean
    Queue queueBondDiscoveryTodayQuote(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(RabbitmqConstants.MQ_QUEUE_WS_BOND_DISCOVERY_QUOTE, false);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    /**
     *债券->发现->异常价格->成交价
     * @param rabbitAdmin
     * @return
     */
    @Bean
    Queue queueBondDiscoveryAbnormalDeal(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(RabbitmqConstants.MQ_QUEUE_WS_BOND_DISCOVERY_AP_DEAL, false);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    /**
     * 债券->发现->异常价格->报价
     * @param rabbitAdmin
     * @return
     */
    @Bean
    Queue queueBondDiscoveryAbnormalQuote(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(RabbitmqConstants.MQ_QUEUE_WS_BOND_DISCOVERY_AP_QUOTE, false);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(msgHandlerMethodFactory());
    }

    @Bean
    DirectExchange exchange(RabbitAdmin rabbitAdmin) {
        DirectExchange directExchange = new DirectExchange(RabbitmqConstants.MQ_DEFAULT_EXCHANGE, false, false);
        rabbitAdmin.declareExchange(directExchange);
        return directExchange;
    }
}
