package com.innodealing.param;

import java.util.ArrayList;
import java.util.List;

import com.innodealing.model.dm.bond.BondFavoriteFinaIndex;
import com.innodealing.model.dm.bond.BondFavoritePriceIndex;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaochao
 * @time 2017年5月11日
 * @description:
 */
public class FavoriteRadarReq {

	@ApiModelProperty(value = "投组债券编号")
	private Long favoriteId;

	@ApiModelProperty(value = "投组编号")
	private Long groupId;

	@ApiModelProperty(value = "价格指标列表")
	private List<BondFavoritePriceIndex> priceList = new ArrayList<>();

	@ApiModelProperty(value = "财务指标编号")
	private List<BondFavoriteFinaIndex> finaList = new ArrayList<>();

	public Long getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(Long favoriteId) {
		this.favoriteId = favoriteId;
	}

	public List<BondFavoritePriceIndex> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<BondFavoritePriceIndex> priceList) {
		this.priceList = priceList;
	}

	public List<BondFavoriteFinaIndex> getFinaList() {
		return finaList;
	}

	public void setFinaList(List<BondFavoriteFinaIndex> finaList) {
		this.finaList = finaList;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
}
