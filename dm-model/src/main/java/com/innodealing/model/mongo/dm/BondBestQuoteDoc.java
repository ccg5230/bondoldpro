package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author stephen.ma
 * @date 2016年9月6日
 * @clasename BondBestQuoteDoc.java
 * @decription TODO
 */
public class BondBestQuoteDoc implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "BondId")
	@Indexed
    private Long bondUniCode;
	
	@ApiModelProperty(value = "债券简称")
    private String bondShortName;

	@ApiModelProperty(value = "报价方")
    private String bidderName = "DM最优价";
	
	@ApiModelProperty(value = "bid.vol")
    private BigDecimal bidVol;
	
	@ApiModelProperty(value = "bid")
    private BigDecimal bidPrice;
	
	@ApiModelProperty(value = "ofr.vol")
    private BigDecimal ofrVol;
	
	@ApiModelProperty(value = "ofr")
    private BigDecimal ofrPrice;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "发布日期")
    @TextIndexed
	private String sendTime;
	
	public Long getBondUniCode() {
		return bondUniCode;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public String getBondShortName() {
		return bondShortName;
	}

	public void setBondShortName(String bondShortName) {
		this.bondShortName = bondShortName;
	}

	public String getBidderName() {
		return bidderName;
	}

	public void setBidderName(String bidderName) {
		this.bidderName = bidderName;
	}

	public BigDecimal getBidVol() {
		return bidVol;
	}

	public void setBidVol(BigDecimal bidVol) {
		this.bidVol = bidVol;
	}

	public BigDecimal getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(BigDecimal bidPrice) {
		this.bidPrice = bidPrice;
	}

	public BigDecimal getOfrVol() {
		return ofrVol;
	}

	public void setOfrVol(BigDecimal ofrVol) {
		this.ofrVol = ofrVol;
	}

	public BigDecimal getOfrPrice() {
		return ofrPrice;
	}

	public void setOfrPrice(BigDecimal ofrPrice) {
		this.ofrPrice = ofrPrice;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	
}
