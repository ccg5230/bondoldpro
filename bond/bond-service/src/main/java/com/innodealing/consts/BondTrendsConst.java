package com.innodealing.consts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author xiaochao
 * @time 2017年3月29日
 * @description: 
 */
public class BondTrendsConst {
//	/**
//	 * 发行、流通、付息与兑付
//	 */
//	public static final Integer TRENDS_BASIC_STATI_CODE = 1;
//	/**
//	 * 违约
//	 */
//	public static final Integer TRENDS_BOF_STATI_CODE = 2;
//	/**
//	 * 评级跟踪
//	 */
//	public static final Integer TRENDS_RATING_STATI_CODE = 3;
//	/**
//	 * 其他公告
//	 */
//	public static final Integer TRENDS_OTHER_STATI_CODE = 4;

	/**
	 * 债券存续
	 */
	public static final Integer TRENDS_STATISTICS_CODE_1 = 1;
	/**
	 * 评级公告
	 */
	public static final Integer TRENDS_STATISTICS_CODE_2 = 2;
	/**
	 * 违法违规
	 */
	public static final Integer TRENDS_STATISTICS_CODE_3 = 3;
	/**
	 * 股市公告
	 */
	public static final Integer TRENDS_STATISTICS_CODE_4 = 4;
	/**
	 * 中介公告
	 */
	public static final Integer TRENDS_STATISTICS_CODE_5 = 5;
	/**
	 * 财务情况
	 */
	public static final Integer TRENDS_STATISTICS_CODE_6 = 6;
	/**
	 * 公司经营
	 */
	public static final Integer TRENDS_STATISTICS_CODE_7 = 7;
	/**
	 * 高管情况
	 */
	public static final Integer TRENDS_STATISTICS_CODE_8 = 8;
	/**
	 * 其他公告
	 */
	public static final Integer TRENDS_STATISTICS_CODE_9 = 9;

	/**
	 * 默认今日动态类别
	 */
	public static final List<Integer> DEFAULT_TODAY_TRENDS_CLSI_LIST = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

	/**
	 * 今日动态展示
	 */
	public static final List<Integer> DEFAULT_TODAY_TRENDS_DISPLAY_LIST = Arrays.asList(2, 3);

	/**
	 * 默认今日动态的正面负面
	 */
	public static final List<Integer> DEFAULT_TODAY_TRENDS_NEGA_LIST = Arrays.asList(0, 1, 2, 3);
}
