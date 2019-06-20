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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import com.innodealing.consts.MqConstants;


/**
 * @author stephen.ma
 * @date 2016骞�6鏈�8鏃�
 * @clasename ConsumerConfig.java
 * @decription TODO
 */
@Configuration
@EnableRabbit
public class ConsumerConfig implements RabbitListenerConfigurer {
	
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
		factory.setPrefetchCount(MqConstants.RABBIT_PREFETCH_COUNT);
		factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
		return factory;
	}
	
	@Override
	public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(msgHandlerMethodFactory());
	}
	
	@Bean
	RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}
	
	//下个版本的修改，添加舆情
	@Bean
	@Qualifier(MqConstants.MQ_DEFAULT_EXCHANGE) 
	public DirectExchange messageEx() {
		return new DirectExchange(MqConstants.MQ_DEFAULT_EXCHANGE, false, false);
	}
	
	@Bean
	@Qualifier(MqConstants.MQ_SENTIMENT_EXCHANGE) 
	public DirectExchange sentimentEx() {
		return new DirectExchange(MqConstants.MQ_SENTIMENT_EXCHANGE, false, false);
	}
	
	@Bean
	@Qualifier(MqConstants.MQ_BOND_SENTIMENT_EXCHANGE) 
	public DirectExchange bondSentimentEx() {
		return new DirectExchange(MqConstants.MQ_BOND_SENTIMENT_EXCHANGE, false, false);
	}

	@Bean
	public Binding bindingMaturity(@Qualifier(MqConstants.MQ_DEFAULT_EXCHANGE) DirectExchange messageEx, RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_MATURITY, false);
		Binding binding = BindingBuilder.bind(queue).to(messageEx).with(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_MATURITY);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}
	
	@Bean
	public Binding bindingBondCredrat(@Qualifier(MqConstants.MQ_DEFAULT_EXCHANGE) DirectExchange messageEx, RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_CREDRAT, false);
		Binding binding = BindingBuilder.bind(queue).to(messageEx).with(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_CREDRAT);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}
	
	@Bean
	public Binding bindingBondIsscredrat(@Qualifier(MqConstants.MQ_DEFAULT_EXCHANGE) DirectExchange messageEx, RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_ISSCREDRAT, false);
		Binding binding = BindingBuilder.bind(queue).to(messageEx).with(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_ISSCREDRAT);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}
	
	@Bean
	public Binding bindingBondIsspdrat(@Qualifier(MqConstants.MQ_DEFAULT_EXCHANGE) DirectExchange messageEx, RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_ISSPDRAT, false);
		Binding binding = BindingBuilder.bind(queue).to(messageEx).with(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_ISSPDRAT);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}
	
	@Bean
	public Binding bindingBondImpliedrat(@Qualifier(MqConstants.MQ_DEFAULT_EXCHANGE) DirectExchange messageEx, RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_IMPLIEDRAT, false);
		Binding binding = BindingBuilder.bind(queue).to(messageEx).with(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_IMPLIEDRAT);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}
	
	@Bean
	public Binding bindingBondIdxprice(@Qualifier(MqConstants.MQ_DEFAULT_EXCHANGE) DirectExchange messageEx, RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_IDXPRICE, false);
		Binding binding = BindingBuilder.bind(queue).to(messageEx).with(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_IDXPRICE);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}
	
	@Bean
	public Binding bindingBondFinSpclindic(@Qualifier(MqConstants.MQ_DEFAULT_EXCHANGE) DirectExchange messageEx, RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_FINSPCLINDIC, false);
		Binding binding = BindingBuilder.bind(queue).to(messageEx).with(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_FINSPCLINDIC);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}
	
	@Bean
	public Binding bindingBondSentimentnews(@Qualifier(MqConstants.MQ_DEFAULT_EXCHANGE) DirectExchange messageEx, RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_SENTIMENTNEWS, false);
		Binding binding = BindingBuilder.bind(queue).to(messageEx).with(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_SENTIMENTNEWS);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}
	
	/*
	@Bean
	Queue queueBondMaturity(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_MATURITY, false);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}
	
	@Bean
	Queue queueBondCredrat(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_CREDRAT, false);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}
	
	@Bean
	Queue queueBondIsscredrat(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_ISSCREDRAT, false);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}
	
	@Bean
	Queue queueBondIsspdrat(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_ISSPDRAT, false);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}
	
	@Bean
	Queue queueBondImpliedrat(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_IMPLIEDRAT, false);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}
	
	@Bean
	Queue queueBondIdxprice(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_IDXPRICE, false);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}
	*/
	/*
	@Bean
	Queue queueBondFinindic(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_FININDIC, false);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}

	@Bean
	Queue queueBondFinSpclindic(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_FINSPCLINDIC, false);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}
	
	@Bean
	Queue queueBondSentimentnews(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_SENTIMENTNEWS, false);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}
	*/
	//要上的新舆情
	
	@Bean
	public Binding bindingBulletin(@Qualifier(MqConstants.MQ_SENTIMENT_EXCHANGE) DirectExchange sentimentEx, RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_SENTIMENTBULLETIN, true);
		Binding binding = BindingBuilder.bind(queue).to(sentimentEx).with(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_SENTIMENTBULLETIN);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}
	
	@Bean
	public Binding bindingSentimentLaw(@Qualifier(MqConstants.MQ_BOND_SENTIMENT_EXCHANGE) DirectExchange bondSentimentEx, RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_SENTIMENTLAWSUIT, false);
		Binding binding = BindingBuilder.bind(queue).to(bondSentimentEx).with(MqConstants.MQ_QUEUE_PORTFOLIO_BOND_SENTIMENTLAWSUIT);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}
}