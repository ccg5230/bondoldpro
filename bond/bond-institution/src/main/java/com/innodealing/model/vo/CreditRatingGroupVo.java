package com.innodealing.model.vo;

import io.swagger.annotations.ApiModelProperty;

public class CreditRatingGroupVo {

	@ApiModelProperty(value = "信评组ID")
    private Long groupId;
	
	@ApiModelProperty(value = "信评组名称")
	private String groupName;

	@ApiModelProperty(value = "信评组类型, 默认0-全部，默认1-禁投组，9-信评分组")
	private Integer groupType;

	@ApiModelProperty(value = "发行人数量")
	private Integer issuerCount;

	@ApiModelProperty(value = "债券数量")
	private Integer bondCount;

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

	public Integer getIssuerCount() {
		return issuerCount;
	}

	public void setIssuerCount(Integer issuerCount) {
		this.issuerCount = issuerCount;
	}

	public Integer getBondCount() {
		return bondCount;
	}

	public void setBondCount(Integer bondCount) {
		this.bondCount = bondCount;
	}

}
