package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
@Document(collection="bond_favorite_group")
public class BondFavoriteGroupDoc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Id
    @ApiModelProperty(value = "关注组id")
    private Integer groupId;
    
    @ApiModelProperty(value = "关注组")
    private String groupName;
    
	@Indexed
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    
    @ApiModelProperty(value = "分组类型，0：我的持仓 1: 普通组")
    private Integer groupType;
    
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    
	@Indexed
    @ApiModelProperty(value = "是否删除 ")
    private Integer isDelete;
    
	@Indexed
    @ApiModelProperty(value = "债券变动是否提醒，1 提醒， 0 不提醒")
    private Integer notifiedEnable;

    @ApiModelProperty(value = "事件提醒消息的类型")
    private List<Integer> notifiedEventtype;

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

	/**
	 * @return the notifiedEventtype
	 */
	public List<Integer> getNotifiedEventtype() {
		return notifiedEventtype;
	}

	/**
	 * @param notifiedEventtype the notifiedEventtype to set
	 */
	public void setNotifiedEventtype(List<Integer> notifiedEventtype) {
		this.notifiedEventtype = notifiedEventtype;
	}
}
