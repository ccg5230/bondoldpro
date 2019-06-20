package com.innodealing.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.innodealing.BondApp;
import com.innodealing.engine.mongo.bond.BondPdHistRepository;
import com.innodealing.model.mongo.dm.BondPdHistDoc;
import com.innodealing.service.BondComDataService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BondApp.class)
@WebIntegrationTest
public class BondComDateServiceTest {
	private @Autowired BondComDataService bondComDataService;
	
	private @Autowired BondPdHistRepository pdHistRepository;
	
	@Test
	public void formatterPdMsgTest(){
		Long conUinCode = 200035800L;
		BondPdHistDoc doc = pdHistRepository.findOne(conUinCode);
		System.out.println("原始数据：" + doc);
		bondComDataService.formatterPdMsg(doc);
		System.out.println("格式数据：" + doc);
	}
	
	@Test
	public void integratePdsTest(){
		System.out.println(bondComDataService.integratePds());
	}
	
	@Test
	public void integratePdRankTest(){
		System.out.println(bondComDataService.integratePdRank());
	}
}
