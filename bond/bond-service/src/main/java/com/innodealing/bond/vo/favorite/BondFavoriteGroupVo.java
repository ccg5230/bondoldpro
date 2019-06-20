package com.innodealing.bond.vo.favorite;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class BondFavoriteGroupVo{

	@ApiModelProperty(value = "关注组id")
    private Integer groupId;
    
    @ApiModelProperty(value = "关注组")
    private String groupName;
    
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    
    @ApiModelProperty(value = "分组类型，0：我的持仓 1: 普通组")
    private Integer groupType;
    
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    
    @ApiModelProperty(value = "是否删除 ")
    private Integer isDelete;
    
    @ApiModelProperty(value = "债券变动是否提醒，1 提醒， 0 不提醒")
    private Integer notifiedEnable;

    @ApiModelProperty(value = "事件提醒消息的类型")
    private List<Integer> notifiedEventtype;
	
    @ApiModelProperty(value = "提醒消息总数")
	private Long eventMsgCount = 0L;

    @ApiModelProperty(value = "该关注组下关注的债券总数")
	private Long bondCount = 0L;
    
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

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getNotifiedEnable() {
		return notifiedEnable;
	}

	public void setNotifiedEnable(Integer notifiedEnable) {
		this.notifiedEnable = notifiedEnable;
	}

	public List<Integer> getNotifiedEventtype() {
		return notifiedEventtype;
	}

	public void setNotifiedEventtype(List<Integer> notifiedEventtype) {
		this.notifiedEventtype = notifiedEventtype;
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
	 * @param bondCount the bondCount to set
	 */
	public void setBondCount(Long bondCount) {
		this.bondCount = bondCount;
	}

}
