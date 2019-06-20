package com.innodealing.broker.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author stephen.ma
 * @date 2016年9月12日
 * @clasename BrokerBondQuoteParam.java
 * @decription TODO
 */
public class BrokerBondQuoteParam extends BaseQuoteParam {

	private Date sendTime;

	/** 成交价 */
	private BigDecimal bondStrikePrice;

	/** 经纪商 */
	private Integer brokerType;

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public BigDecimal getBondStrikePrice() {
		return bondStrikePrice;
	}

	public void setBondStrikePrice(BigDecimal bondStrikePrice) {
		this.bondStrikePrice = bondStrikePrice;
	}

	public Integer getBrokerType() {
		return brokerType;
	}

	public void setBrokerType(Integer brokerType) {
		this.brokerType = brokerType;
	}

}
