package com.innodealing.amqp.config;

/**
 * @author stephen.ma
 * @date 2016年7月26日
 * @clasename MqConstants.java
 * @decription TODO
 */
public class MqConstants {
	
	//config
	public static final Integer RABBIT_PREFETCH_COUNT = 7;
	
	//mq.exchange
	public static final String MQ_DEFAULT_EXCHANGE = "message";
	
	//mq.queues
	public static final String MQ_QUEUE_BOND_CHANGE_EVENT = "queue.bond_change_event";

}
