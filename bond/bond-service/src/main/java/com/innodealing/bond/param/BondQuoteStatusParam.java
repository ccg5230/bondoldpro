package com.innodealing.bond.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author stephen.ma
 * @date 2016年9月26日
 * @clasename BondQuoteStatusParam.java
 * @decription TODO
 */
@ApiModel
public class BondQuoteStatusParam {
	
	@ApiModelProperty(value = "状态操作（2=成交,99=撤销）", required=true)
	
	private Integer action;

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

}
