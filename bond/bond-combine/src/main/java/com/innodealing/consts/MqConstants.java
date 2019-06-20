package com.innodealing.consts;

/**
 * @author stephen.ma
 * @clasename MqConstants.java
 * @decription TODO
 */
public class MqConstants {
	
	//config
	public static final Integer RABBIT_PREFETCH_COUNT = 7;
	
	//mq.exchange
	public static final String MQ_DEFAULT_EXCHANGE = "message";
	public static final String MQ_SENTIMENT_EXCHANGE = "sentiment";
	public static final String MQ_BOND_SENTIMENT_EXCHANGE = "bond_sentiment";
	
	//mq.queues
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
	//舆情的公司诉讼
	public static final String MQ_QUEUE_PORTFOLIO_BOND_SENTIMENTLAWSUIT = "bond_sentiment_law";
	//舆情的公告
	public static final String MQ_QUEUE_PORTFOLIO_BOND_SENTIMENTBULLETIN = "dmms.bulletin";
}
