package com.innodealing.domain.vo;

import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaochao
 * @time 2017年5月24日
 * @description:
 */
public class BondFavoriteGroupEmailVO {
	@ApiModelProperty(value = "投组id")
	private Integer groupId;

	@ApiModelProperty(value = "投组名称")
	private String groupName;

	@ApiModelProperty(value = "email地址")
	private String email;

	@ApiModelProperty(value = "用户id")
	private Integer userId;

	@ApiModelProperty(value = "投组中包含的关注债券列表")
	private Map<String, Object> favoriteMap = new HashMap<>();

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Map<String, Object> getFavoriteMap() {
		return favoriteMap;
	}

	public void setFavoriteMap(Map<String, Object> favoriteMap) {
		this.favoriteMap = favoriteMap;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
