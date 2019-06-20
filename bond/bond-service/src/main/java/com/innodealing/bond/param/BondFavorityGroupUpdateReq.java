package com.innodealing.bond.param;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class BondFavorityGroupUpdateReq {
		
	@ApiModelProperty(value = "关注组名")
	String groupName;
	
	@ApiModelProperty(value = "债券变动是否提醒，1 提醒， 0 不提醒")
	Integer notifiedEnable;
	
	@ApiModelProperty(value = "事件提醒消息的类型List")
	List<Integer> notifiedEventtypes;

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getNotifiedEnable() {
		return notifiedEnable;
	}

	public void setNotifiedEnable(Integer notifiedEnable) {
		this.notifiedEnable = notifiedEnable;
	}

	public List<Integer> getNotifiedEventtypes() {
		return notifiedEventtypes;
	}

	public void setNotifiedEventtypes(List<Integer> notifiedEventtypes) {
		this.notifiedEventtypes = notifiedEventtypes;
	}
}
