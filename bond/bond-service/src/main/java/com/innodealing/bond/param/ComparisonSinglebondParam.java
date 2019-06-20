package com.innodealing.bond.param;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author stephen.ma
 * @date 2016年9月19日
 * @clasename ComparisonSinglebondParam.java
 * @decription TODO
 */
@ApiModel
public class ComparisonSinglebondParam {
	
	@ApiModelProperty(value = "债券代码")
	private List<Long> bondIds;

	public List<Long> getBondIds() {
		return bondIds;
	}

	public void setBondIds(List<Long> bondIds) {
		this.bondIds = bondIds;
	}

}
