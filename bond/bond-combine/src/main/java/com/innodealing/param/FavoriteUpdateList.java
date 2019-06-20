package com.innodealing.param;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/** 
* @author feng.ma
* @date 2017年8月16日 下午8:52:57 
* @describe 
*/
public class FavoriteUpdateList {

	@ApiModelProperty(value = "关注债券的ID的List")
	private List<Integer> favoriteIds = new ArrayList<>();

	/**
	 * @return the favoriteIds
	 */
	public List<Integer> getFavoriteIds() {
		return favoriteIds;
	}

	/**
	 * @param favoriteIds the favoriteIds to set
	 */
	public void setFavoriteIds(List<Integer> favoriteIds) {
		this.favoriteIds = favoriteIds;
	}
}
