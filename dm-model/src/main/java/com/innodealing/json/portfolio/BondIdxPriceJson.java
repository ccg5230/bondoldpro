package com.innodealing.json.portfolio;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
//价格异常
public class BondIdxPriceJson implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "债券id")
    private Long bondUniCode;
	
	@ApiModelProperty(value = "价格指标1 - 成交价 2 - 卖出报价 3 - 买入报价4 - 估值")
    private Integer priceIndex;
	
	@ApiModelProperty(value = "收益率")
    private BigDecimal bondYield;
	
	@ApiModelProperty(value = "净价")
    private BigDecimal cleanPrice;
	
	@ApiModelProperty(value = "估值")
    private BigDecimal valuDevi;

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

	/**
	 * @return the priceIndex
	 */
	public Integer getPriceIndex() {
		return priceIndex;
	}

	/**
	 * @param priceIndex the priceIndex to set
	 */
	public void setPriceIndex(Integer priceIndex) {
		this.priceIndex = priceIndex;
	}

	/**
	 * @return the bondYield
	 */
	public BigDecimal getBondYield() {
		return bondYield;
	}

	/**
	 * @param bondYield the bondYield to set
	 */
	public void setBondYield(BigDecimal bondYield) {
		this.bondYield = bondYield;
	}

	/**
	 * @return the cleanPrice
	 */
	public BigDecimal getCleanPrice() {
		return cleanPrice;
	}

	/**
	 * @param cleanPrice the cleanPrice to set
	 */
	public void setCleanPrice(BigDecimal cleanPrice) {
		this.cleanPrice = cleanPrice;
	}

	/**
	 * @return the valuDevi
	 */
	public BigDecimal getValuDevi() {
		return valuDevi;
	}

	/**
	 * @param valuDevi the valuDevi to set
	 */
	public void setValuDevi(BigDecimal valuDevi) {
		this.valuDevi = valuDevi;
	}

	public BondIdxPriceJson() {
	}
	
	public BondIdxPriceJson(Long bondUniCode, Integer priceIndex, BigDecimal bondYield, BigDecimal cleanPrice,
			BigDecimal valuDevi) {
		this.bondUniCode = bondUniCode;
		this.priceIndex = priceIndex;
		this.bondYield = bondYield;
		this.cleanPrice = cleanPrice;
		this.valuDevi = valuDevi;
	}
	
}
