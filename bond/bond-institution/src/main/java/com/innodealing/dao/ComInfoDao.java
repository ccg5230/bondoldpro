package com.innodealing.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.innodealing.mapper.ComInfoMapper;
import com.innodealing.model.mysql.BondInstComIndu;

@Repository
public class ComInfoDao {

	@Autowired
	private ComInfoMapper comInfoMapper;

	public List<BondInstComIndu> queryComListByInstIndu(Map<String, Object> map) {
		return comInfoMapper.queryComListByInstIndu(map);
	}

	public List<BondInstComIndu> queryComNotInduList(Map<String, Object> map) {
		return comInfoMapper.queryComNotInduList(map);
	}

	public int insertBondInstComIndu(BondInstComIndu indu) {
		return comInfoMapper.insertBondInstComIndu(indu);
	}
	
	public int updateBondInstComIndu(BondInstComIndu indu) {
		return comInfoMapper.updateBondInstComIndu(indu);
	}

	public int deleteBondInstComIndu(Map<String, Object> map) {
		return comInfoMapper.deleteBondInstComIndu(map);
	}
	
	public int queryComInduCount(BondInstComIndu indu){
		return comInfoMapper.queryComInduCount(indu);
	}

	public int deleteBondInstComInduByComUniCode(Map<String,Object> map){
		return comInfoMapper.deleteBondInstComInduByComUniCode(map);
	}
}
