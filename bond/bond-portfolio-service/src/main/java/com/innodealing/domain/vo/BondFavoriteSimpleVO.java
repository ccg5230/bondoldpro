package com.innodealing.domain.vo;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaochao
 * @time 2017年5月18日
 * @description:
 */
public class BondFavoriteSimpleVO {
	@ApiModelProperty(value = "债券id")
	private Long bondUniCode;

	@ApiModelProperty(value = "持仓量")
	private Integer openinterest;

	@ApiModelProperty(value = "持仓价格")
	private BigDecimal positionPrice;

	@ApiModelProperty(value = "持仓日期")
	private Date positionDate;

	public Long getBondUniCode() {
		return bondUniCode;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public Integer getOpeninterest() {
		return openinterest;
	}

	public void setOpeninterest(Integer openinterest) {
		this.openinterest = openinterest;
	}

	public BigDecimal getPositionPrice() {
		return positionPrice;
	}

	public void setPositionPrice(BigDecimal positionPrice) {
		this.positionPrice = positionPrice;
	}

	public Date getPositionDate() {
		return positionDate;
	}

	public void setPositionDate(Date positionDate) {
		this.positionDate = positionDate;
	}
}
