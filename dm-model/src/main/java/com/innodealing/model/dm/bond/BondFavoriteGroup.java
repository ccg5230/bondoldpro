package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

/**
 * 用户收藏model
 * 
 * @author zhaozhenglai
 * @since 2016年7月1日上午10:02:45 Copyright © 2015 DealingMatrix.cn. All Rights
 *        Reserved.
 */
@Entity
@Table(name = "t_bond_favorite_group")
public class BondFavoriteGroup implements Serializable {

	private static final long serialVersionUID = -2730483893976669456L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(value = "关注组id")
	@Column(name = "group_id")
	private Integer groupId;

	@ApiModelProperty(value = "关注组")
	@Column(name = "group_name")
	private String groupName;

	@ApiModelProperty(value = "用户id")
	@Column(name = "user_id")
	private Integer userId;

	@ApiModelProperty(value = "分组类型，0：我的持仓 1: 普通组")
	@Column(name = "group_type")
	private Integer groupType;

	@ApiModelProperty(hidden = true)
	@Column(name = "create_time")
	private Date createTime;

	@ApiModelProperty(hidden = true)
	@Column(name = "update_time")
	private Date updateTime;

	@ApiModelProperty(hidden = true)
	@Column(name = "is_delete")
	private Integer isDelete;

	@ApiModelProperty(value = "债券变动是否提醒，1 提醒， 0 不提醒")
	@Column(name = "notified_enable")
	private Integer notifiedEnable;

	@ApiModelProperty(value = "事件提醒消息的类型", hidden = true)
	@Column(name = "notified_eventtype")
	private String notifiedEventtype;

	@ApiModelProperty(value = "邮件推送提醒，1 提醒， 0 不提醒", hidden = true)
	@Column(name = "email_enable")
	private Integer emailEnable;

	@ApiModelProperty(value = "email地址", hidden = true)
	@Column(name = "email")
	private String email;

	/**
	 * @return the groupId
	 */
	public Integer getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the groupType
	 */
	public Integer getGroupType() {
		return groupType;
	}

	/**
	 * @param groupType
	 *            the groupType to set
	 */
	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the isDelete
	 */
	public Integer getIsDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete
	 *            the isDelete to set
	 */
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * @return the notifiedEnable
	 */
	public Integer getNotifiedEnable() {
		return notifiedEnable;
	}

	/**
	 * @param notifiedEnable
	 *            the notifiedEnable to set
	 */
	public void setNotifiedEnable(Integer notifiedEnable) {
		this.notifiedEnable = notifiedEnable;
	}

	/**
	 * @return the notifiedEventtype
	 */
	public String getNotifiedEventtype() {
		return notifiedEventtype;
	}

	/**
	 * @param notifiedEventtype
	 *            the notifiedEventtype to set
	 */
	public void setNotifiedEventtype(String notifiedEventtype) {
		this.notifiedEventtype = notifiedEventtype;
	}

	/**
	 * @return the emailEnable
	 */
	public Integer getEmailEnable() {
		return emailEnable;
	}

	/**
	 * @param emailEnable
	 *            the emailEnable to set
	 */
	public void setEmailEnable(Integer emailEnable) {
		this.emailEnable = emailEnable;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
