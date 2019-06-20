package com.innodealing.param;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * @author xiaochao
 * @time 2017年9月12日
 * @description:
 */
public class FavoriteBondBatchReq {

	@ApiModelProperty(value = "债券ID编号List")
	private List<Long> bondIds;

	@ApiModelProperty(value = "投组ID编号List")
	private List<Integer> groupIds;

	public List<Long> getBondIds() {
		return bondIds;
	}

	public void setBondIds(List<Long> bondIds) {
		this.bondIds = bondIds;
	}

	public List<Integer> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(List<Integer> groupId) {
		this.groupIds = groupId;
	}
}
