package com.innodealing.param;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author xiaochao
 * @time 2017年9月12日
 * @description:
 */
public class FavoriteBondBatchReq {

	@ApiModelProperty(value = "投组ID编号")
	private Integer groupId;

	@ApiModelProperty(value = "债券ID编号List")
	private List<Long> bondIds;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public List<Long> getBondIds() {
		return bondIds;
	}

	public void setBondIds(List<Long> bondIds) {
		this.bondIds = bondIds;
	}

}
