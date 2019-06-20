package com.innodealing.controller;

import java.util.HashMap;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.innodealing.dao.BondInstRatingHistDao;
import com.innodealing.model.mysql.BondInstRatingHist;

public class BondInstRatingHistControllerTest extends BaseJunit {

	private @Autowired BondInstRatingHistDao bondInstRatingHistDao;

	@Test
	public void testInsertBondInstRatingHist() throws Exception {
		BondInstRatingHist entity = new BondInstRatingHist();
		entity.setBondUniCode(103005940L);
		entity.setFatId(11L);
		entity.setComUniCode(200246083L);
		int checkRepeatData = bondInstRatingHistDao.checkRepeatData(entity);
		System.out.println(checkRepeatData);
	}

}
