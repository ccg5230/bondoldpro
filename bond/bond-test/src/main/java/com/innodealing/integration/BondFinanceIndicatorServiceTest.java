package com.innodealing.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.innodealing.BondApp;
import com.innodealing.service.BondFinanceIndicatorService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BondApp.class)
@WebIntegrationTest
public class BondFinanceIndicatorServiceTest {
	private @Autowired BondFinanceIndicatorService bondFinanceIndicatorService;
	
	
	@Test
	public void buildFinanceIndicatorsTest(){
		bondFinanceIndicatorService.buildFinanceIndicators(false);
	}
	
}
