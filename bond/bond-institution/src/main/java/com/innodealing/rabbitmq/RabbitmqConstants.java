package com.innodealing.rabbitmq;

public class RabbitmqConstants {
	
	//config
	public static final Integer RABBIT_PREFETCH_COUNT = 7;
	
	//mq.exchange
	public static final String MQ_DEFAULT_EXCHANGE = "message";
	public static final String MQ_VENDOR_EXCHANGE = "vendor";

	//socketio通知前端刷新信评数据
	public static final String MQ_SOCKET_IO = "socketio";
}

