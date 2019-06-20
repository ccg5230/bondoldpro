package com.innodealing.consts;

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
	public static final String MQ_QUEUE_BOND = "queue.nd_bond";
	public static final String MQ_QUEUE_BOND_SRCMSG = "queue.bond_srcmsg";
	public static final String MQ_QUEUE_BOND_ITEM_STATUS = "queue.bond_item_status";
	public static final String MQ_QUEUE_DMMD_MINE_FIELD = "dmms_mine_field";
	public static final String MQ_QUEUE_NEWBOND_ANNATT_UPLOAD = "queue.newbond.annatt.upload";

	//portfolio mq.queues producer
	//债项评级/债项展望
	public static final String MQ_QUEUE_PORTFOLIO_BOND_CREDRAT = "queue.portfolio.bond_credrat";
	//价格指标
	public static final String MQ_QUEUE_PORTFOLIO_BOND_IDXPRICE = "queue.portfolio.bond_idxprice";
	//存续
	public static final String MQ_QUEUE_PORTFOLIO_BOND_MATURITY = "queue.portfolio.bond_maturity";
	//财务预警
	public static final String MQ_QUEUE_PORTFOLIO_BOND_FINALERT = "queue.portfolio.bond_finalert";
	//隐含评级
	public static final String MQ_QUEUE_PORTFOLIO_BOND_IMPLIEDRAT = "queue.portfolio.bond_impliedrat";
	//主体评级/主体展望
	public static final String MQ_QUEUE_PORTFOLIO_BOND_ISSCREDRAT = "queue.portfolio.bond_isscredrat";
	//量化风险等级
	public static final String MQ_QUEUE_PORTFOLIO_BOND_ISSPDRAT = "queue.portfolio.bond_isspdrat";
	//财务指标-市场指标
	public static final String MQ_QUEUE_PORTFOLIO_BOND_FININDIC = "queue.portfolio.bond_finindic";
	//财务指标--非金融企业专项指标
	public static final String MQ_QUEUE_PORTFOLIO_BOND_FINSPCLINDIC = "queue.portfolio.bond_finspclindic";
	//舆情的新闻(原先舆情的数据)
	public static final String MQ_QUEUE_PORTFOLIO_BOND_SENTIMENTNEWS = "queue.portfolio.bond_sentimentnews";

	// WebSocket，债券->发现->今日成交
	public static final String MQ_QUEUE_WS_BOND_DISCOVERY_DEAL ="queue.ws.bond_discovery_today_deal";
	// WebSocket，债券->发现->今日报价
	public static final String MQ_QUEUE_WS_BOND_DISCOVERY_QUOTE ="queue.ws.bond_discovery_today_quote";
	// WebSocket，债券->发现->异常价格->成交价
	public static final String MQ_QUEUE_WS_BOND_DISCOVERY_AP_DEAL ="queue.ws.bond_discovery_ap_deal";
	// WebSocket，债券->发现->异常价格->报价
	public static final String MQ_QUEUE_WS_BOND_DISCOVERY_AP_QUOTE ="queue.ws.bond_discovery_ap_quote";


}
