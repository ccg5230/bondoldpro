package com.innodealing.domain.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaochao
 * @time 2017年5月18日
 * @description:
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BondFavoriteGroupVO {
	@ApiModelProperty(value = "关注组id")
	private Integer groupId;

	@ApiModelProperty(value = "关注组")
	private String groupName;

	@ApiModelProperty(value = "用户id")
	private Integer userId;

	@ApiModelProperty(value = "分组类型:0-我的持仓;1-普通组")
	private Integer groupType;

	@ApiModelProperty(value = "创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@ApiModelProperty(value = "更新时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

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

	@ApiModelProperty(value = "是否有过期债券的变化(仅针对推送消息)")
	private Boolean hasExpired;

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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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

	/**
	 * @return the bondCount
	 */
	public Long getBondCount() {
		return bondCount;
	}

	/**
	 * @param bondCount
	 *            the bondCount to set
	 */
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

	public Boolean getHasExpired() {
		return hasExpired;
	}

	public void setHasExpired(Boolean hasExpired) {
		this.hasExpired = hasExpired;
	}
}
