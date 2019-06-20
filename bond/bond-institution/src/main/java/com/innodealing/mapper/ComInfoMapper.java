package com.innodealing.mapper;

import java.util.List;
import java.util.Map;

import com.innodealing.model.mysql.BondInstComIndu;

public interface ComInfoMapper{
	
	public List<BondInstComIndu> queryComListByInstIndu(Map<String,Object> map);
	
	public List<BondInstComIndu> queryComNotInduList(Map<String,Object> map);
	
	public int insertBondInstComIndu(BondInstComIndu indu);
	
	public int updateBondInstComIndu(BondInstComIndu indu);
	
	public int deleteBondInstComIndu(Map<String,Object> map);
	
	public int queryComInduCount(BondInstComIndu indu);
	
	public int deleteBondInstComInduByComUniCode(Map<String,Object> map);

}
