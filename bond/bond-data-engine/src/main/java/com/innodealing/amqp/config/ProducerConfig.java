package com.innodealing.amqp.config;

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


@Configuration
public class ProducerConfig {

	@Bean
	RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	Queue queueBondChangeEvent(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_BOND_CHANGE_EVENT, false);
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
	Binding bindingExchangeBondChangeEvent(Queue queueBondChangeEvent, DirectExchange exchange, RabbitAdmin rabbitAdmin) {
		Binding binding = BindingBuilder.bind(queueBondChangeEvent).to(exchange).with(MqConstants.MQ_QUEUE_BOND_CHANGE_EVENT);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}
	
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