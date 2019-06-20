package com.innodealing.model.mongo.dm.bond.quote.analyse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author stephen.ma
 * @date 2016年9月13日
 * @clasename BondQuoteHistorycurveDoc.java
 * @decription TODO
 */
@JsonInclude(Include.NON_NULL)
@Document(collection = "bond_quote_historycurve")
public class BondQuoteHistorycurveDoc implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "时间，格式：yyyy-MM-dd")
	@Indexed
	private String sendTime;
	
	@ApiModelProperty(value = "债券的uni_code")
	@Indexed
    private Long bondUniCode;
	
	@ApiModelProperty(value = "bid价格最优")
	private BigDecimal bidPrice;

	@ApiModelProperty(value = "bid Vol")
	private BigDecimal bidVol;
	
	@ApiModelProperty(value = "ofr价格最优")
	private BigDecimal ofrPrice;

	@ApiModelProperty(value = "ofr Vol")
	private BigDecimal ofrVol;
	
	@ApiModelProperty(value = "加权成交价")
	private BigDecimal weightedPrice;
	
	@ApiModelProperty(value = "成交量")
	private BigDecimal dealVol;
	
	public Long getBondUniCode() {
		return bondUniCode;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public BigDecimal getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(BigDecimal bidPrice) {
		this.bidPrice = bidPrice;
	}

	public BigDecimal getBidVol() {
		return bidVol;
	}

	public void setBidVol(BigDecimal bidVol) {
		this.bidVol = bidVol;
	}

	public BigDecimal getOfrPrice() {
		return ofrPrice;
	}

	public void setOfrPrice(BigDecimal ofrPrice) {
		this.ofrPrice = ofrPrice;
	}

	public BigDecimal getOfrVol() {
		return ofrVol;
	}

	public void setOfrVol(BigDecimal ofrVol) {
		this.ofrVol = ofrVol;
	}

	public BigDecimal getWeightedPrice() {
		return weightedPrice;
	}

	public void setWeightedPrice(BigDecimal weightedPrice) {
		this.weightedPrice = weightedPrice;
	}

	public BigDecimal getDealVol() {
		return dealVol;
	}

	public void setDealVol(BigDecimal dealVol) {
		this.dealVol = dealVol;
	}

}
