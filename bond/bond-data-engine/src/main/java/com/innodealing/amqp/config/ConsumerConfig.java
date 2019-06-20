package com.innodealing.amqp.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import com.innodealing.amqp.RabbitMqReceiverService;


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
    private RabbitMqReceiverService rabbitMqReceiverService;

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

	@Override
	public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(msgHandlerMethodFactory());
	}

}