package com.innodealing.param;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author xiaochao
 * @time 2017年6月15日
 * @description:
 */
public class FavorityAddReq {
	@ApiModelProperty(value = "投组id集合")
	private List<Long> groupIds;

	public List<Long> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(List<Long> groupIds) {
		this.groupIds = groupIds;
	}
}
