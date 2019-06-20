package com.innodealing.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.innodealing.mapper.BondUserInduClassMapper;
import com.innodealing.model.dm.bond.BondUserInduClass;

@Repository
public class BondUserInduClassDao {

	private @Autowired BondUserInduClassMapper userInduClassMapper;

	public BondUserInduClass queryById(Long userId) {
		return userInduClassMapper.queryById(userId);
	}

}
