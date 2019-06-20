package com.innodealing.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.innodealing.mapper.BondInstInduMapper;
import com.innodealing.model.mysql.BondInstIndu;

@Repository
public class BondInstInduDao {

	@Autowired
	private BondInstInduMapper bondInstInduMapper;

	public List<BondInstIndu> queryBondInstInduList(Map<String, Object> map) {
		return bondInstInduMapper.queryBondInstInduList(map);
	}

	public BondInstIndu insertBondInstIndu(BondInstIndu indu) {
		 bondInstInduMapper.insertBondInstIndu(indu);
		 return indu;
	}

	public int updateBondInstIndu(BondInstIndu indu) {
		return bondInstInduMapper.updateBondInstIndu(indu);
	}

	public int deleteBondInstIndu(Map<String, Object> map) {
		return bondInstInduMapper.deleteBondInstIndu(map);
	}

	public Integer queryInduCount(Map<String, Object> map) {
		return bondInstInduMapper.queryInduCount(map);
	}
	
	public int updateOldBondInstIndu(BondInstIndu indu) {
		return bondInstInduMapper.updateOldBondInstIndu(indu);
	}
	
	public int updateNewBondInstIndu(BondInstIndu indu) {
		return bondInstInduMapper.updateNewBondInstIndu(indu);
	}
	
	public int updateBondInstInduLevel(BondInstIndu indu){
		return bondInstInduMapper.updateBondInstInduLevel(indu);
	}
}
