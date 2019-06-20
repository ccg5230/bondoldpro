package com.innodealing.consts;

/**
 * @author stephen.ma
 * @date 2016年7月26日
 * @clasename MqConstants.java
 * @decription TODO
 */
public class RabbitmqConstants {
	
	//config
	public static final Integer RABBIT_PREFETCH_COUNT = 7;
	
	//mq.exchange
	public static final String MQ_DEFAULT_EXCHANGE = "message";
	public static final String MQ_VENDOR_EXCHANGE = "vendor";
	public static final String MQ_QUEUE_SOCKETIO = "socketio";

	//portfolio mq.queues producer
	//价格指标
	public static final String MQ_QUEUE_PORTFOLIO_BOND_IDXPRICE = "queue.portfolio.bond_idxprice";
	public static final String MQ_VENDOR_QUOTE_BOND = "vendor.quote.bond";
	
	/**
	 * 投组指标变动通知
	 */
	public static final String MQ_QUEUE_PORTFOLIO_BOND_FINSPCLINDIC= "queue.portfolio.bond_finspclindic";

	// WebSocket，债券->发现->今日成交
	public static final String MQ_QUEUE_WS_BOND_DISCOVERY_DEAL ="queue.ws.bond_discovery_today_deal";
	// WebSocket，债券->发现->今日报价
	public static final String MQ_QUEUE_WS_BOND_DISCOVERY_QUOTE ="queue.ws.bond_discovery_today_quote";
	// WebSocket，债券->发现->异常价格->成交价
	public static final String MQ_QUEUE_WS_BOND_DISCOVERY_AP_DEAL ="queue.ws.bond_discovery_ap_deal";
	// WebSocket，债券->发现->异常价格->报价
	public static final String MQ_QUEUE_WS_BOND_DISCOVERY_AP_QUOTE ="queue.ws.bond_discovery_ap_quote";
}

