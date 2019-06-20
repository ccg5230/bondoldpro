package com.innodealing.model.vo;

import io.swagger.annotations.ApiModelProperty;

public class CreditRatingBasic {

	@ApiModelProperty(value = "发行人Id")
	private Long issuerId = 0L;
	
	@ApiModelProperty(value = "信评组ID")
    private Long groupId;

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
	
}
