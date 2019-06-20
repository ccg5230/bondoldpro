package com.innodealing.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author xiaochao
 * @time 2017年8月14日
 * @description:
 */
@JsonInclude(Include.NON_NULL)
public class SimpleFavoriteGroupVO implements Serializable {
	@ApiModelProperty(value = "关注债券id")
	private Integer favoriteId;

	@ApiModelProperty(value = "投组id")
	private Integer groupId;

	@ApiModelProperty(value = "投组名称")
	private String groupName;

	@ApiModelProperty(value = "债券变动是否提醒:0-不提醒;1-提醒")
	private Integer notifiedEnable;

	@ApiModelProperty(value = "提醒消息总数")
	private Long eventMsgCount = 0L;

	@ApiModelProperty(value = "该关注组下关注的债券总数")
	private Long bondCount = 0L;

	@ApiModelProperty(value = "邮件推送提醒，1 提醒， 0 不提醒")
	private Integer emailEnable;

	@ApiModelProperty(value = "email地址")
	private String email;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getNotifiedEnable() {
		return notifiedEnable;
	}

	public void setNotifiedEnable(Integer notifiedEnable) {
		this.notifiedEnable = notifiedEnable;
	}

	public Long getEventMsgCount() {
		return eventMsgCount;
	}

	public void setEventMsgCount(Long eventMsgCount) {
		this.eventMsgCount = eventMsgCount;
	}

	public Long getBondCount() {
		return bondCount;
	}

	public void setBondCount(Long bondCount) {
		this.bondCount = bondCount;
	}

	public Integer getEmailEnable() {
		return emailEnable;
	}

	public void setEmailEnable(Integer emailEnable) {
		this.emailEnable = emailEnable;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(Integer favoriteId) {
		this.favoriteId = favoriteId;
	}
}
