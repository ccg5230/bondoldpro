package com.innodealing.model.mongo.dm;


import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
@Document(collection="bond_single_‎comparison") 
public class BondSingleComparisonDoc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "债券uni_code")
	@Indexed
	private Long bondUniCode;
	
	@ApiModelProperty(value = "债券代码")
	private String bondCode;

	@ApiModelProperty(value = "债券名称")
	private String bondShortName;
	
	@ApiModelProperty(value = "bid最优")
    private BigDecimal bidPrice;
	
	@ApiModelProperty(value = "ofr最优")
    private BigDecimal ofrPrice;
	
	@ApiModelProperty(value = "时间，格式 yyyy-MM-dd")
	@Indexed
	private String sendTime;

	public Long getBondUniCode() {
		return bondUniCode;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public String getBondCode() {
		return bondCode;
	}

	public void setBondCode(String bondCode) {
		this.bondCode = bondCode;
	}

	public String getBondShortName() {
		return bondShortName;
	}

	public void setBondShortName(String bondShortName) {
		this.bondShortName = bondShortName;
	}

	public BigDecimal getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(BigDecimal bidPrice) {
		this.bidPrice = bidPrice;
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
