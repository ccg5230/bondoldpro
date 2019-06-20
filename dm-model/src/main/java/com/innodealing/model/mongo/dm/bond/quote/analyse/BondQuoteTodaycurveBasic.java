package com.innodealing.model.mongo.dm.bond.quote.analyse;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author stephen.ma
 * @date 2016年9月13日
 * @clasename BondQuoteTodaycurveBasic.java
 * @decription TODO
 */
public class BondQuoteTodaycurveBasic {
	
	@ApiModelProperty(value = "bid/ofr价格,或是成交数据")
	private BigDecimal bondPrice;

	@ApiModelProperty(value = "bid/ofr Vol")
	private BigDecimal bondVol;
	
	@ApiModelProperty(value = "发布时间,格式：yyyy-MM-dd HH:mm:ss")
	private String sendTime;

	public BigDecimal getBondPrice() {
		return bondPrice;
	}

	public void setBondPrice(BigDecimal bondPrice) {
		this.bondPrice = bondPrice;
	}

	public BigDecimal getBondVol() {
		return bondVol;
	}

	public void setBondVol(BigDecimal bondVol) {
		this.bondVol = bondVol;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
}
