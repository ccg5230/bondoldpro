package com.innodealing.mapper;


import java.util.List;

import com.innodealing.model.mysql.BondInstRatingFile;

public interface BondInstRatingFileMapper{
	
	public int insertBondInstRatingFile(BondInstRatingFile bondInstRatingFile);
	
	public List<BondInstRatingFile> queryBondInstRatingFileList(BondInstRatingFile bondInstRatingFile);
	
	public int deleteBondInstRatingFileByHistId(Long instRatingId);
}
