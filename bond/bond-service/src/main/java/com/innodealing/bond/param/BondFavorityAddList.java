package com.innodealing.bond.param;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class BondFavorityAddList {
	
	@ApiModelProperty(value = "债券ID编号List")
	private List<Long> bondIds;
	
	@ApiModelProperty(value = "分组id集合")
	private List<Integer> groupId ;

	public List<Long> getBondIds() {
		return bondIds;
	}

	public void setBondIds(List<Long> bondIds) {
		this.bondIds = bondIds;
	}

	public List<Integer> getGroupId() {
		return groupId;
	}

	public void setGroupId(List<Integer> groupId) {
		this.groupId = groupId;
	}
	
}
