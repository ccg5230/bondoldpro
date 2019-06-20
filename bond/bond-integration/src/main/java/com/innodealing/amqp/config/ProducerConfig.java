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

import com.innodealing.consts.MqConstants;

/**
 * @author stephen.ma
 * @date 2016年6月8日
 * @clasename ProducerConfig.java
 * @decription TODO
 */
@Configuration
public class ProducerConfig {

	@Bean
	RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	DirectExchange exchange(RabbitAdmin rabbitAdmin) {
		DirectExchange directExchange = new DirectExchange(MqConstants.MQ_DEFAULT_EXCHANGE, false, false);
//	    DirectExchange directExchange = new DirectExchange(MqConstants.MQ_BOND_CALCULATE_EXCHANGE, false, false);
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
	Queue queuebBondMaturity(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_MATURITY, false);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}
	
	@Bean
	Binding bindingExchangeBondMaturity(Queue queuebBondMaturity, DirectExchange exchange, RabbitAdmin rabbitAdmin) {
		Binding binding = BindingBuilder.bind(queuebBondMaturity).to(exchange).with(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_MATURITY);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}
	
	@Bean
	Queue queueBondCredrat(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_CREDRAT, false);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}
	
	@Bean
	Binding bindingExchangeBondCredrat(Queue queueBondCredrat, DirectExchange exchange, RabbitAdmin rabbitAdmin) {
		Binding binding = BindingBuilder.bind(queueBondCredrat).to(exchange).with(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_CREDRAT);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}
	
	@Bean
	Queue queueBondIsscredrat(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_ISSCREDRAT, false);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}
	
	@Bean
	Binding bindingExchangeBondIsscredrat(Queue queueBondIsscredrat, DirectExchange exchange, RabbitAdmin rabbitAdmin) {
		Binding binding = BindingBuilder.bind(queueBondIsscredrat).to(exchange).with(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_ISSCREDRAT);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}
	
	@Bean
	Queue queueBondIsspdrat(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_ISSPDRAT, false);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}
	
	@Bean
	Binding bindingExchangeBondIsspdrat(Queue queueBondIsspdrat, DirectExchange exchange, RabbitAdmin rabbitAdmin) {
		Binding binding = BindingBuilder.bind(queueBondIsspdrat).to(exchange).with(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_ISSPDRAT);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}
	
	@Bean
	Queue queueBondImpliedrat(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_IMPLIEDRAT, false);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}
	
	@Bean
	Binding bindingExchangeBondImpliedrat(Queue queueBondImpliedrat, DirectExchange exchange, RabbitAdmin rabbitAdmin) {
		Binding binding = BindingBuilder.bind(queueBondImpliedrat).to(exchange).with(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_IMPLIEDRAT);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}
	
	@Bean
	Queue queueBondIdxprice(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_IDXPRICE, false);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}
	
	@Bean
	Binding bindingExchangeBondIdxprice(Queue queueBondIdxprice, DirectExchange exchange, RabbitAdmin rabbitAdmin) {
		Binding binding = BindingBuilder.bind(queueBondIdxprice).to(exchange).with(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_IDXPRICE);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}
	
	@Bean
	Queue queueBondFinindic(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_FININDIC, false);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}
	
	@Bean
	Binding bindingExchangeBondFinindic(Queue queueBondFinindic, DirectExchange exchange, RabbitAdmin rabbitAdmin) {
		Binding binding = BindingBuilder.bind(queueBondFinindic).to(exchange).with(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_FININDIC);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}
	
	@Bean
	Queue queueBondFinspclindic(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_FINSPCLINDIC, false);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}
	
	@Bean
	Binding bindingExchangeBondFinspclindic(Queue queueBondFinspclindic, DirectExchange exchange, RabbitAdmin rabbitAdmin) {
		Binding binding = BindingBuilder.bind(queueBondFinspclindic).to(exchange).with(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_FINSPCLINDIC);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}
	
	@Bean
	Queue queueBondSentimentnews(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_SENTIMENTNEWS, false);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}
	
	@Bean
	Binding bindingExchangeBondSentimentnews(Queue queueBondSentimentnews, DirectExchange exchange, RabbitAdmin rabbitAdmin) {
		Binding binding = BindingBuilder.bind(queueBondSentimentnews).to(exchange).with(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_SENTIMENTNEWS);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}

	@Bean
	Queue queuebBondDiscoveryDeal(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_WS_BOND_DISCOVERY_DEAL, false);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}

	@Bean
	Binding bindingExchangeBondDiscoveryDeal(Queue queuebBondDiscoveryDeal, DirectExchange exchange, RabbitAdmin rabbitAdmin) {
		Binding binding = BindingBuilder.bind(queuebBondDiscoveryDeal).to(exchange).with(MqConstants.MQ_QUEUE_WS_BOND_DISCOVERY_DEAL);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}

	@Bean
	Queue queuebBondDiscoveryQuote(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_WS_BOND_DISCOVERY_QUOTE, false);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}

	@Bean
	Binding bindingExchangeBondDiscoveryQuote(Queue queuebBondDiscoveryQuote, DirectExchange exchange, RabbitAdmin rabbitAdmin) {
		Binding binding = BindingBuilder.bind(queuebBondDiscoveryQuote).to(exchange).with(MqConstants.MQ_QUEUE_WS_BOND_DISCOVERY_QUOTE);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}
	
   @Bean
    Queue queueNewBondAnnAttQuote(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(MqConstants.MQ_QUEUE_NEWBOND_ANNATT_UPLOAD, false);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    Binding bindingExchangeNewBondAnnAttQuote(Queue queueNewBondAnnAttQuote, DirectExchange exchange, RabbitAdmin rabbitAdmin) {//队列名称要和@Bean一样创建的一样
        Binding binding = BindingBuilder.bind(queueNewBondAnnAttQuote).to(exchange).with(MqConstants.MQ_QUEUE_NEWBOND_ANNATT_UPLOAD);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }
	
}