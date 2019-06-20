package com.innodealing.mapper;

import java.util.List;
import java.util.Map;

import com.innodealing.model.mysql.BondInstCode;

public interface BondInstCodeMapper {
	
	public BondInstCode queryByIdAndTypeAndOrgId(Map<String,Object> map);
	
	public BondInstCode queryByNameAndTypeAndOrgId(Map<String,Object> map);
	
	public List<BondInstCode> queryByTypeAndOrgId(Map<String,Object> map);

	public BondInstCode queryById(Integer id);
	
	public Integer queryVersionByOrgId(Integer orgId);
}
