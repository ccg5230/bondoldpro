package com.innodealing.model.mongo.dm.bond.quote.analyse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author stephen.ma
 * @date 2016年9月13日
 * @clasename BondQuoteTodaycurveDoc.java
 * @decription TODO
 */
@JsonInclude(Include.NON_NULL)
@Document(collection = "bond_quote_todaycurve")
public class BondQuoteTodaycurveDoc implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "1收券-bid，2出券-ofr")
    private Integer side;
	
	@ApiModelProperty(value = "债券名称")
    private String bondShortName;
	
	@ApiModelProperty(value = "债券价格")
    private BigDecimal bondPrice;
	
	@ApiModelProperty(value = "债券发行量")
    private BigDecimal bondVol;
    
	@ApiModelProperty(value = " 收益率/净价单位，1 % ，2元")
    private Integer priceUnit;
	
	@ApiModelProperty(value = "成交价")
    private BigDecimal bondRate;
	
	@ApiModelProperty(value = "加权成交价", hidden = true)
    private BigDecimal bondWeightedRate;
	
	@ApiModelProperty(value = "发布时间, yyyy-MM-dd HH:mm:ss")
	@Indexed
	private String sendTime;
	
	@ApiModelProperty(value = "发布时间，yyyy-MM-dd ")
	@Indexed
	private String sendDateFormat;
	
	@ApiModelProperty(value = "债券的uni_code")
	@Indexed
    private Long bondUniCode;

	public Integer getSide() {
		return side;
	}

	public void setSide(Integer side) {
		this.side = side;
	}

	public String getBondShortName() {
		return bondShortName;
	}

	public void setBondShortName(String bondShortName) {
		this.bondShortName = bondShortName;
	}

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

	public Integer getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(Integer priceUnit) {
		this.priceUnit = priceUnit;
	}

	public BigDecimal getBondRate() {
		return bondRate;
	}

	public void setBondRate(BigDecimal bondRate) {
		this.bondRate = bondRate;
	}

	public BigDecimal getBondWeightedRate() {
		return bondWeightedRate;
	}

	public void setBondWeightedRate(BigDecimal bondWeightedRate) {
		this.bondWeightedRate = bondWeightedRate;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getSendDateFormat() {
		return sendDateFormat;
	}

	public void setSendDateFormat(String sendDateFormat) {
		this.sendDateFormat = sendDateFormat;
	}

	public Long getBondUniCode() {
		return bondUniCode;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

}
