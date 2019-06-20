package com.innodealing.json.consts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {
	
	/**
	 * 异常价格
	 */
	public static final Integer EVENMSG_TYPE_ABNPRICE = 4;

	/**
	 * 财务指标
	 */
	public static final Integer EVENMSG_TYPE_FININDICATOR = 5;

	/**
	 * 存续变动 ，MATURITY
	 */
	// 付息
	public static final Integer EVENMSG_TYPE_YEAR_PAY = 7;
	// 到期
	public static final Integer EVENMSG_TYPE_THEO_PAY = 8;
	// 行权
	public static final Integer EVENMSG_TYPE_EXER_PAY = 9;

	/**
	 * 风险变动-主体评级和展望
	 */
	public static final Integer EVENMSG_TYPE_ISSRATE = 10;
	/**
	 * 风险变动-债项评级和展望
	 */
	public static final Integer EVENMSG_TYPE_BONDRATE = 11;
	/**
	 * 风险变动-主体量化风险等级
	 */
	public static final Integer EVENMSG_TYPE_ISSQURAT = 12;
	/**
	 * 风险变动-隐含评级
	 */
	public static final Integer EVENMSG_TYPE_IMRATING = 13;

	/**
	 * 主体舆情
	 */
	// 公告
	public static final Integer EVENMSG_TYPE_ANNOUNCEMENT = 14;
	// 工商变更
	public static final Integer EVENMSG_TYPE_BUSINESSCHANGE = 15;
	// 新闻预警
	public static final Integer EVENMSG_TYPE_NEWSWARNING = 16;
	// 公司诉讼
	public static final Integer EVENMSG_TYPE_COMLITIGATION = 17;

	/**
	 * 报价监测
	 */
	public static final Integer EVENMSG_TYPE_QUOTEMNT = 7;

	public static final List<Integer> EVENMSG_TYPE_LISTS = new ArrayList<>(
			Arrays.asList(EVENMSG_TYPE_ABNPRICE, EVENMSG_TYPE_FININDICATOR, EVENMSG_TYPE_YEAR_PAY,
					EVENMSG_TYPE_THEO_PAY, EVENMSG_TYPE_EXER_PAY, EVENMSG_TYPE_ISSRATE, EVENMSG_TYPE_BONDRATE,
					EVENMSG_TYPE_ISSQURAT, EVENMSG_TYPE_IMRATING, EVENMSG_TYPE_ANNOUNCEMENT,
					EVENMSG_TYPE_BUSINESSCHANGE, EVENMSG_TYPE_NEWSWARNING, EVENMSG_TYPE_COMLITIGATION));

	public static final List<Integer> EVENMSG_TYPE_RISKCHANGE_LISTS = new ArrayList<>(
			Arrays.asList(EVENMSG_TYPE_ISSRATE, EVENMSG_TYPE_BONDRATE, EVENMSG_TYPE_ISSQURAT, EVENMSG_TYPE_IMRATING));
	
	
	//存续变动
	private static final Integer YEAR_PAY_TYPE = 11;
	private static final Integer EXPE_PAY_TYPE = 12;
	private static final Integer THEO_PAY_TYPE = 13;
	
	/**
	 * true
	 */
	public static final Integer TRUE = 1;
	
	/**
	 * false
	 */
	public static final Integer FALSE = 0;
	
	//socketio to bond in web
	public static final String SOCKETIO_NAMESPACE_PORTFOLIO = "dm_client_web_bond";
	
	public static final String MESSAGETYPE_CARDMSG = "bond_card_msg";
	
	public static final String MESSAGETYPE_NOTIFICATIONMSG = "bond_notification_msg";

}
