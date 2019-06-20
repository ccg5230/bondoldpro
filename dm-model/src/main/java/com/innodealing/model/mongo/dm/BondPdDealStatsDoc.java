package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
public class BondPdDealStatsDoc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7876953753135158565L;

	@ApiModelProperty(value = "债券id")
	private Long bondUniCode;

	@ApiModelProperty(value = "债券简称")
	private String shortName;

	@ApiModelProperty(value = "成交价")
	private BigDecimal lastPrice;

	@ApiModelProperty(value = "成交笔数")
	private Long dealCount;

	public BondPdDealStatsDoc() {
	}

	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * @return the lastPrice
	 */
	public BigDecimal getLastPrice() {
		return lastPrice;
	}

	/**
	 * @param lastPrice the lastPrice to set
	 */
	public void setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}

	/**
	 * @return the execCount
	 */
	public Long getDealCount() {
		return dealCount;
	}

	/**
	 * @param execCount the execCount to set
	 */
	public void setDealCount(Long dealCount) {
		this.dealCount = dealCount;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BondPdDealStatsDoc [" + (bondUniCode != null ? "bondUniCode=" + bondUniCode + ", " : "")
				+ (shortName != null ? "shortName=" + shortName + ", " : "")
				+ (lastPrice != null ? "lastPrice=" + lastPrice + ", " : "")
				+ (dealCount != null ? "dealCount=" + dealCount : "") + "]";
	}

	/**
	 * @return the bondUniCode
	 */
	public Long getBondUniCode() {
		return bondUniCode;
	}

	/**
	 * @param bondUniCode the bondUniCode to set
	 */
	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}


}
