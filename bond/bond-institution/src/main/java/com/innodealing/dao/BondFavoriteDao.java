package com.innodealing.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.innodealing.mapper.BondFavoriteMapper;
import com.innodealing.model.dm.bond.BondFavorite;

@Repository
public class BondFavoriteDao {

	private @Autowired BondFavoriteMapper bondFavoriteMapper;

	public BondFavorite queryOneByUserIdAndBondId(Map<String, Object> map) {
		return bondFavoriteMapper.queryOneByUserIdAndBondId(map);
	}

}
