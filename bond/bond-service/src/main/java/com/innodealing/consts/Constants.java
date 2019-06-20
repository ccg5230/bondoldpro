package com.innodealing.consts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author stephen.ma
 * @clasename Constants.java
 * @decription TODO
 */
public class Constants {
    
    public static final String  QUOTEID_PREFIX =  "7";
	
	public static final Integer ASSETSCALE_OFFSET = 2;
	
	//timeout 10 minutes
	public static final long TIMEOUT_SECONDS = 60*10;
	
	public static final List<Integer> VALID_DEALED_STATUS = new ArrayList<>(Arrays.asList(1,2));
	
	public static final List<Integer> PUBLIC_SOURCE = new ArrayList<>(Arrays.asList(1,2,3));

	public static final List<Integer> PRIVATE_SOURCE = new ArrayList<>(Arrays.asList(0,4));
	
	
	//quote status,1 正常 2 已成交 99 已取消
	public static final Integer QUOTE_STATUS_NORMAL = 1;
	public static final Integer QUOTE_STATUS_TRADED = 2;
	public static final Integer QUOTE_STATUS_CANCELED = 99;
	
	//债券剩余期限的偏移量，±2M(60)以内
	public static final long BOND_TENOR_OFFSET = 60;
	
	/**
	 * 存续变动
	 */
	public static final Integer EVENMSG_TYPE_MATURITY = 1;
	/**
	 * 风险变动-主/债外部评级
	 */
	public static final Integer EVENMSG_TYPE_BONDRATE = 2;
	/**
	 * 风险变动-隐含评级
	 */
	public static final Integer EVENMSG_TYPE_IMRATING = 6;
	/**
	 * 风险变动-外部评级展望
	 */
	public static final Integer EVENMSG_TYPE_EXTRATOL = 8;
	/**
	 * 风险变动-财务指标
	 */
	public static final Integer EVENMSG_TYPE_FINALERT = 3;
	/**
	 * 风险变动-主体量化风险等级
	 */
	public static final Integer EVENMSG_TYPE_ISSQURAT = 4;
	/**
	 * 主体舆情
	 */
	public static final Integer EVENMSG_TYPE_BONDNEWS = 5;
	/**
	 * 报价监测
	 */
	public static final Integer EVENMSG_TYPE_QUOTEMNT = 7;
	
	/**
	 * 异常价格
	 */
	public static final Integer EVENMSG_TYPE_ABNPRICE = 4;

	public static final List<Integer> EVENMSG_TYPE_LISTS = new ArrayList<>(
		Arrays.asList(EVENMSG_TYPE_MATURITY,
             EVENMSG_TYPE_BONDRATE,
             EVENMSG_TYPE_FINALERT,
             EVENMSG_TYPE_ISSQURAT,
             EVENMSG_TYPE_BONDNEWS,
             EVENMSG_TYPE_IMRATING,
             EVENMSG_TYPE_QUOTEMNT,
             EVENMSG_TYPE_EXTRATOL)
            );
	
	public static final List<Integer> EVENMSG_TYPE_RISKCHANGE_LISTS = new ArrayList<>(
		Arrays.asList(EVENMSG_TYPE_BONDRATE,
            EVENMSG_TYPE_FINALERT,
            EVENMSG_TYPE_ISSQURAT,
            EVENMSG_TYPE_IMRATING,
            EVENMSG_TYPE_EXTRATOL)
			);
	
	//行业分类类型
	public static final Integer INDU_CLASS_GICS = 1;
	public static final Integer INDU_CLASS_SW = 2;
	public static final Integer INDU_CLASS_CUSTOM = 3;
	public static final Integer INDU_CLASS_UNDEFINED = 9;
	public static final String  INDU_CLASS_REDIS_PREFIX = "indu_user_";
	public static final String  INDU_CLASS_GICS_ID = "induId";
	public static final String  INDU_CLASS_GICS_NAME = "induName";
	public static final String  INDU_CLASS_SW_ID = "induIdSw";
	public static final String  INDU_CLASS_SW_NAME = "induNameSw";
	
	/**
	 * 财报暂未披露
	 */
	public static final String FINACE_REPORT_UNPUBLISHED = "财报暂未披露";
	
	/**
	 * 重点指标缺失
	 */
	public static final String FINACE_KEY_INDICATOR_MISSING = "重点指标缺失";
	
	/**
	 * 模型不匹配无法计算
	 */
	public static final String FINACE_MODEL_UNMATCHED = "模型不匹配无法计算";
	
	/**
	 * 权重非0的指标值为NULL
	 */
	public static final String FINANCE_WEIGHT_INDICATOR_IS_NULL= "权重非0的指标值为NULL";
	
	/**
	 * 年报或指标缺失
	 */
	public static final String FINANCE_REPORT_INDICATOR_MISSING= "年报或指标缺失";
	
	public static final int TRUE = 1;
	public static final int FALSE = 0;
	
	//财务指标分析-资产负债、利润表、现金流量分类
	/**
	 * 资产负债表
	 */
	public static final Integer  FINANCE_INDICATOR_ZCFZ = 1;
	
	/**
	 * 利润表
	 */
	public static final Integer  FINANCE_INDICATOR_LR = 2;
	
	/**
	 * 现金流量表
	 */
	public static final Integer  FINANCE_INDICATOR_XJLL = 3;
	
	
	//指标万、率、一般类型
	
	/**
	 * 万
	 */
	public static final int INDICATOR_PERCENT_W = 0; 
	
	/**
	 * 率
	 */
	public static final int INDICATOR_PERCENT_L = 1; 
	
	/**
	 * 一般
	 */
	public static final int INDICATOR_PERCENT_Y = 2; 
	
	/**
	 * 债券报价监测，缓存去重key
	 */
	public static final String QUOTE_NOTI_REDIS_PREFIX = "quote_noti";
	
    /**
     * 利率类型，需要与中诚信的pub_par一致
     */
	public static final String RATE_TYPE_DISCOUNT_BOND = "贴现";
	
	public static final String QUOTEDATA_POSTFROM_KEY = "QUOTEDATA_POSTFROM_KEY_";
	
	public static final List<Integer> QUOTEDATA_POSTFROM_LIST = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8,9));

	/**
	 * 存续1
	 */
	public static final Long BOND_FAV_GROUP_MATURITY_TYPE = 1L;
	/**
	 * 评级2
	 */
	public static final Long BOND_FAV_GROUP_RATING_TYPE = 2L;
	/**
	 * 舆情3
	 */
	public static final Long BOND_FAV_GROUP_SENTIMENT_TYPE = 3L;
	/**
	 * 价格4
	 */
	public static final Long BOND_FAV_GROUP_PRICE_TYPE = 4L;
	/**
	 * 财务5
	 */
	public static final Long BOND_FAV_GROUP_FINA_TYPE = 5L;
	/**
	 * 其他6
	 */
	public static final Long BOND_FAV_GROUP_OTHER_TYPE = 6L;
	
	/*
	 * 财务指标单位1->元、2->万、3->亿
	 */
	/**
	 * 
	 * 元
	 */
	public static final Integer YUAN = 1;
	
	/**
	 * 万
	 */
	public static final Integer WAN = 2;
	
	/**
	 * 亿
	 */
	public static final Integer YI = 3;
	
	/**
	 * 中诚信金额单位：bond_ccxe.pub_par where PAR_SYS_CODE=6007
	 * 1  元 ，2   千元 ，3   万元 ，4   百万元 ，5   亿元
	 */
	public static final String BOND_CCXE_YUAN_UNIT = "1";
	public static final String BOND_CCXE_THOUSAND_UNIT = "2";
	public static final String BOND_CCXE_WAN_UNIT = "3";
	public static final String BOND_CCXE_MILLION_UNIT = "4";
	public static final String BOND_CCXE_YI_UNIT = "5";
	
	public static final String INSTIRUTION_INDU_CODE="institutionInduMap.code";
	public static final String INSTIRUTION_INDU_NAME="institutionInduMap.name";
	
	public static final String INST_RATING_CODE="instRatingMap.code";
	public static final String INST_RATING_NAME="instRatingMap.name";
	
	public static final String INST_INVESTMENT_ADVICE_CODE="instInvestmentAdviceMap.code";
	public static final String INST_INVESTMENT_ADVICE_NAME="instInvestmentAdviceMap.name";
	
	
	/**
	 * 日
	 */
	public static final int DAY = 3;
	
	/**
	 * 月
	 */
	public static final int MONTH = 2;
	
	/**
	 * 年
	 */
	public static final int YEAR = 1;
}
