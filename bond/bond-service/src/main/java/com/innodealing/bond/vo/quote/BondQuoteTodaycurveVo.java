package com.innodealing.bond.vo.quote;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.index.TextIndexed;

import com.innodealing.model.mongo.dm.bond.quote.analyse.BondQuoteTodaycurveBasic;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author stephen.ma
 * @date 2016年9月20日
 * @clasename BondQuoteTodaycurveVo.java
 * @decription TODO
 */
public class BondQuoteTodaycurveVo {
	
	@ApiModelProperty(value = "bid的曲線的數據")
	private List<BondQuoteTodaycurveBasic> bidData = new ArrayList<>();
	
	@ApiModelProperty(value = "ofr的曲線的數據")
	private List<BondQuoteTodaycurveBasic> ofrData = new ArrayList<>();
	
	@ApiModelProperty(value = "成交的數據")
	private List<BondQuoteTodaycurveBasic> dealData = new ArrayList<>();
	
	@ApiModelProperty(value = "发布时间")
	private String sendTime;

	public List<BondQuoteTodaycurveBasic> getBidData() {
		return bidData;
	}

	public void setBidData(List<BondQuoteTodaycurveBasic> bidData) {
		this.bidData = bidData;
	}

	public List<BondQuoteTodaycurveBasic> getOfrData() {
		return ofrData;
	}

	public void setOfrData(List<BondQuoteTodaycurveBasic> ofrData) {
		this.ofrData = ofrData;
	}

	public List<BondQuoteTodaycurveBasic> getDealData() {
		return dealData;
	}

	public void setDealData(List<BondQuoteTodaycurveBasic> dealData) {
		this.dealData = dealData;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

}
