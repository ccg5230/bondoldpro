package com.innodealing.service;

import org.springframework.stereotype.Service;

@Service
public class BondPortfolioIdxConsts {
	
	//指标类型:1-固有指标;2-组合指标
	public static final int INDEX_TYPE_FIXED = 1;
	public static final int INDEX_TYPE_CUSTOMIZED = 2;
	
	//数值类型:1-指标自身;2-同比;3-行业排名
	public static final int INDEX_VALUE_TYPE_SELF = 1;
	public static final int INDEX_VALUE_TYPE_YOY  = 2;
	public static final int INDEX_VALUE_TYPE_RANK = 3;
	
	//数值单位:1-%;2-万元
	public static final int INDEX_VALUE_UNIT_PERCENT = 1;
	public static final int INDEX_VALUE_UNIT_RMB  = 2;

	//当前状态:0-无效;1-有效
	public static final int INDEX_STATUS_INVALID = 0;
	public static final int INDEX_STATUS_VALID  = 1;
	
	//条件关系: 1-gteA; 2-lteA; 3-A和B的闭区间; 4-lteA或者gteB; 5-全部,[排名] 6-前A; 7-后A;
	public static final int INDEX_STATUS_NEXUS_GTE_A = 1;
	public static final int INDEX_STATUS_NEXUS_LTE_A = 2;
	public static final int INDEX_STATUS_NEXUS_BEWTEEN_AB = 3;
	public static final int INDEX_STATUS_NEXUS_LTE_A_OR_GTE_B = 4;
	public static final int INDEX_STATUS_NEXUS_ALL = 5;
	public static final int INDEX_STATUS_NEXUS_FRONT_A = 6;
	public static final int INDEX_STATUS_NEXUS_END_A = 7;

	//价格指标;成交价，卖出报价，买入报价
	public static final int INDEX_BONDPRICE = 1;
	public static final int INDEX_OFR_QOUTE = 2;
	public static final int INDEX_BID_QOUTE = 3;

	//收益率，净价，估值偏离
	public static final int INDEX_PRICETYPE_BONDYEILD = 1;
	public static final int INDEX_PRICETYPE_CLEANPRICE = 2;
	public static final int INDEX_PRICETYPE_VALUDEVI = 3;
	
	//财务指标：数值单位:0-(空);1-%;2-万元;3-天
	public static final int INDEXVALUEUNIT_1 = 1;
	public static final int INDEXVALUEUNIT_2 = 2;
	public static final int INDEXVALUEUNIT_3 = 3;
	
	//指标的field
	public static final String ESTCLEANPRICE_FIELD = "estCleanPrice";
	public static final String ESTYIELD_FIELD = "estYield";
}
