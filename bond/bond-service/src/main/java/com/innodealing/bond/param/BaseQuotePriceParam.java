package com.innodealing.bond.param;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author stephen.ma
 * @date 2016年9月19日
 * @clasename BaseQuotePriceParam.java
 * @decription TODO
 */
@ApiModel
public class BaseQuotePriceParam {
	
	@ApiModelProperty(value = "方向,1收券-bid，2出券-ofr", required=true)
	private Integer side;
	
	@ApiModelProperty(value = "债券价格")
    private BigDecimal bondPrice = new BigDecimal(0);
	
	@ApiModelProperty(value = "债券发行量，单位：万")
    private BigDecimal bondVol = new BigDecimal(0);

	public Integer getSide() {
		return side;
	}

	public void setSide(Integer side) {
		this.side = side;
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

}
