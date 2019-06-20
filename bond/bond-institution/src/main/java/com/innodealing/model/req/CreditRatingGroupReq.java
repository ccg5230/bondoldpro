package com.innodealing.model.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class CreditRatingGroupReq implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "信评组名称")
	private String groupName;
	
	@ApiModelProperty(value = "信评组类型, 默认1-全部，默认2-禁投组，9-信评分组")
	private Integer groupType;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

}
