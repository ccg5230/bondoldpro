package com.innodealing.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.innodealing.mapper.BondInstCodeMapper;
import com.innodealing.model.mysql.BondInstCode;

@Repository
public class BondInstCodeDao {

	private @Autowired BondInstCodeMapper bondInstCodeMapper;

	public BondInstCode queryByIdAndTypeAndOrgId(Map<String, Object> map) {
		return bondInstCodeMapper.queryByIdAndTypeAndOrgId(map);
	}

	public BondInstCode queryByNameAndTypeAndOrgId(Map<String, Object> map) {
		return bondInstCodeMapper.queryByNameAndTypeAndOrgId(map);
	}
	
	public BondInstCode queryById(Integer id) {
		return bondInstCodeMapper.queryById(id);
	}

	public List<BondInstCode> queryByTypeAndOrgId(Map<String, Object> map) {
		return bondInstCodeMapper.queryByTypeAndOrgId(map);
	}
	
	public Integer queryVersionByOrgId(Integer orgId){
		return bondInstCodeMapper.queryVersionByOrgId(orgId);
	}

}
