package com.innodealing.model.vo;

import io.swagger.annotations.ApiModelProperty;

public class IssuerGroupInfoVo {
	
	@ApiModelProperty(value = "发行人Id")
	private Long issuerId;

	@ApiModelProperty(value = "信评组Id")
	private Long groupId;
	
	@ApiModelProperty(value = "信评组名称")
	private String groupName;

	@ApiModelProperty(value = "信评组类型")
	private Integer groupType;

	public Long getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(Long issuerId) {
		this.issuerId = issuerId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

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
