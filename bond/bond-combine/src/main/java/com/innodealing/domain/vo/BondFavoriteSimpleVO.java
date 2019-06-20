package com.innodealing.domain.vo;

import io.swagger.annotations.ApiModelProperty;

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
}
