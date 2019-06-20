package com.innodealing.mapper;

import java.util.Map;

import com.innodealing.model.dm.bond.BondFavorite;

public interface BondFavoriteMapper {

	public BondFavorite queryOneByUserIdAndBondId(Map<String,Object> map);
}
