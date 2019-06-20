package com.innodealing.bond.param;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author stephen.ma
 * @date 2016年9月12日
 * @clasename BrokerBondQuoteParam.java
 * @decription TODO
 */
public class BrokerBondQuoteParam extends BaseQuoteParam{


	@ApiModelProperty(value = "最后更新时间")
	private Date sendTime;
	
	@ApiModelProperty(value = "QB中Broker数据的type类型")
    private Integer brokerType;
	
	public Date getSendTime() {
		return sendTime;
	}
	
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getBrokerType() {
		return brokerType;
	}

	public void setBrokerType(Integer brokerType) {
		this.brokerType = brokerType;
	}

	@Override
	public String toString() {
		return "BrokerBondQuoteParam [brokerType=" + brokerType + ", getBondCode()=" + getBondCode() + ", getSide()="
				+ getSide() + ", getBondPrice()=" + getBondPrice() + ", getBondVol()=" + getBondVol() + "]";
	}
}
