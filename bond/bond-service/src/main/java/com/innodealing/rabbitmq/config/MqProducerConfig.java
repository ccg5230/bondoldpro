package com.innodealing.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

import com.innodealing.consts.RabbitmqConstants;

/**
 * @author stephen.ma
 * @date 2016年6月8日
 * @clasename ProducerConfig.java
 * @decription TODO
 */
@Configuration
public class MqProducerConfig {

	@Bean
	RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
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
		//rabbitMessagingTemplate.setMessageConverter(jackson2Converter());
		rabbitMessagingTemplate.setRabbitTemplate(rabbitTemplate);
		return rabbitMessagingTemplate;
	}
	
	@Bean
	public MappingJackson2MessageConverter jackson2Converter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		return converter;
	}
	
	@Bean
	Queue queueBondIdxPrice(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(RabbitmqConstants.MQ_QUEUE_PORTFOLIO_BOND_IDXPRICE, false);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}
	
	@Bean
	Binding bindingExchangeBondIdxPrice(Queue queueBondIdxPrice, DirectExchange exchange, RabbitAdmin rabbitAdmin) {
		Binding binding = BindingBuilder.bind(queueBondIdxPrice).to(exchange).with(RabbitmqConstants.MQ_QUEUE_PORTFOLIO_BOND_IDXPRICE);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}
	
}