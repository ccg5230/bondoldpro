package com.innodealing.bond.param;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class BondFavoriteUpdateList {
	
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
