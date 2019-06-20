package com.innodealing.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.innodealing.controller.BaseJunit;
import com.innodealing.model.mysql.BondInstRatingBondPub;


public class BondInstRatingBondPubMapperTest extends BaseJunit {

	
	@Autowired
	BondInstRatingBondPubMapper bondInstRatingBondPubMapper;
	
	@Test
	public void test(){
		BondInstRatingBondPub record = new BondInstRatingBondPub();
		record.setId(1);
		record.setComUniCode(9999);
//		bondInstRatingBondPubMapper.insert(record );
		
		bondInstRatingBondPubMapper.updateByPrimaryKey(record);
		
		bondInstRatingBondPubMapper.deleteByPrimaryKey(1);
		
		
	}
}
