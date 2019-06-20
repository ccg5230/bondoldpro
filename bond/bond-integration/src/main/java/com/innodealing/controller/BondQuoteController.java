package com.innodealing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.bond.param.BrokerBondQuoteParam;
import com.innodealing.bond.service.BondQuoteService;
import com.innodealing.domain.JsonResult;
import com.innodealing.service.BondQuoteDataMigrationService;
import com.innodealing.service.BondQuoteIntegrationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author stephen.ma
 * @date 2016年9月1日
 * @clasename BondQuoteController.java
 * @decription TODO
 */
@Api(description = "债券需求和报价数据处理")
@RestController
@RequestMapping("api/bond/tasks/")
public class BondQuoteController {
	
	private @Autowired BondQuoteService bondQuoteService;

	@Autowired
	BondQuoteIntegrationService bondQuoteIntegrationService;

	@Autowired
	BondQuoteDataMigrationService bondQuoteDataMigrationService;
	
	@ApiOperation(value="Broker报价数据保存")
    @RequestMapping(value = "/quotes/broker", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<Long> saveBrokerQuote(@RequestBody BrokerBondQuoteParam brokerBondQuoteParam){
		return new JsonResult<Long>().ok(bondQuoteService.saveBrokerBondQuote(brokerBondQuoteParam));
	}

	@ApiOperation(value = "重建债券报价历史曲线数据，每天晚上11点触发一次即可")
	@RequestMapping(value = "/bonds/quotes/historycurve", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Integer> buildQuotesHistorycurve() {
		bondQuoteIntegrationService.saveBondQuoteHistorycurveDocInMongo();
		return new JsonResult<Integer>().ok(1);
	}
	
	@ApiOperation(value = "重建债券今天开始往前推6天的日期，每天晚上10点触发一次即可")
	@RequestMapping(value = "/bonds/quotes/workingdates", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Integer> buildWorkingdatesSixdays() {
		return new JsonResult<Integer>().ok(bondQuoteIntegrationService.saveWorkingdateSixdaysDoc());
	}
	
	@ApiOperation(value = "数据迁移")
	@RequestMapping(value = "/quotes/dataMigration", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> buildDataMigration() {
		bondQuoteDataMigrationService.handleBondQuotes();
		return new JsonResult<String>().ok("");
	}

	@ApiOperation(value = "重建债券报价今日曲线数据，每隔5分钟触发一次")
	@RequestMapping(value = "/bonds/quotes/todaycurve", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Integer> buildQuotesTodaycurve() {
		bondQuoteIntegrationService.saveBondQuoteTodaycurveDocInMongo();
		return new JsonResult<Integer>().ok(1);
	}
	
	@ApiOperation(value = "重建今日最优报价数据，每天隔3分钟触发一次")
	@RequestMapping(value = "/bonds/quotes/bestQuote", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Integer> buildBestQuote() {
		bondQuoteIntegrationService.saveBondBestQuoteDoc();
		return new JsonResult<Integer>().ok(1);
	}
	
	@ApiOperation(value = "重建今日最优报价数据，每天隔10分钟触发一次")
	@RequestMapping(value = "/bonds/quotes/bondSingleComparison", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Integer> buildBondSingleComparison() {
		bondQuoteIntegrationService.saveBondSingleComparisonDoc();
		return new JsonResult<Integer>().ok(1);
	}
	
	@ApiOperation(value = "重新构建MongoDB中bond_quotes的数据")
	@RequestMapping(value = "/bonds/quotes/dataRebuilding", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Integer> rebuildBondQuotes() {
		bondQuoteDataMigrationService.rebuildBondQuotesInMongo();
		return new JsonResult<Integer>().ok(1);
	}
}
