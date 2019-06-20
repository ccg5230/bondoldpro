package com.innodealing.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.innodealing.mapper.BondInstRatingFileMapper;
import com.innodealing.model.mysql.BondInstRatingFile;

@Repository
public class BondInstRatingFileDao {

	@Autowired
	private BondInstRatingFileMapper bondInstRatingFileMapper;

	public int insertBondInstRatingFile(BondInstRatingFile bondInstRatingFile) {
		 return bondInstRatingFileMapper.insertBondInstRatingFile(bondInstRatingFile);
	}
	
	public int deleteBondInstRatingFileByHistId(Long instRatingId){
		return bondInstRatingFileMapper.deleteBondInstRatingFileByHistId(instRatingId);
	}
	
}
