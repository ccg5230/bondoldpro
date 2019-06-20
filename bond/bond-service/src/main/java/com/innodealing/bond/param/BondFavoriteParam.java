package com.innodealing.bond.param;

import io.swagger.annotations.ApiModelProperty;

public class BondFavoriteParam {
	
	@ApiModelProperty(value = "持仓量")
	private Integer openinterest;
	
	@ApiModelProperty(value = "备注")
	private String remark;

	/**
	 * @return the openinterest
	 */
	public Integer getOpeninterest() {
		return openinterest;
	}

	/**
	 * @param openinterest the openinterest to set
	 */
	public void setOpeninterest(Integer openinterest) {
		this.openinterest = openinterest;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
