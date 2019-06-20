package com.innodealing.mapper;

import java.util.List;
import java.util.Map;

import com.innodealing.model.mysql.BondInstIndu;

public interface BondInstInduMapper{
	
	public List<BondInstIndu> queryBondInstInduList(Map<String,Object> map);
	
	public int insertBondInstIndu(BondInstIndu indu);
	
	public int updateBondInstIndu(BondInstIndu indu);
	
	public int deleteBondInstIndu(Map<String,Object> map);
	
	public Integer queryInduCount(Map<String,Object> map);
	
	public int updateOldBondInstIndu(BondInstIndu indu);
	
	public int updateNewBondInstIndu(BondInstIndu indu);
	
	public int updateBondInstInduLevel(BondInstIndu indu);
	
}
