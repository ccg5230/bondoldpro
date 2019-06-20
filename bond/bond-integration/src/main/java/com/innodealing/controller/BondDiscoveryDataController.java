package com.innodealing.controller;

import com.innodealing.domain.JsonResult;
import com.innodealing.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(description = "债劵->发现")
@RestController
@RequestMapping("api/bond/tasks/")
public class BondDiscoveryDataController {

	@Autowired
	BondDealDataService dealService;

	@Autowired
	BondDiscoveryDataService bondDiscoveryDataService;

	@ApiOperation(value = "增量构建成交数据")
	@RequestMapping(value = "/deals/integration", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> buildBondData() {
		return new JsonResult<String>().ok(dealService.syncIntegration());
	}

	@ApiOperation(value = "重置债劵今日成交数据")
	@RequestMapping(value = "/discovery/today/deal/init", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> initBondDiscoveryTodayDeal() {
		bondDiscoveryDataService.initBondDiscoveryTodayDealData();
		return new JsonResult<String>().ok("done");
	}

	@ApiOperation(value = "重置债劵今日报价数据")
	@RequestMapping(value = "/discovery/today/quote/init", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> initBondDiscoveryTodayQuote() {
		bondDiscoveryDataService.initBondDiscoveryTodayQuoteData();
		return new JsonResult<String>().ok("done");
	}

	@ApiOperation(value = "删除给定日期之前的今日报价+成交数据，处理数据冗余")
	@RequestMapping(value = "/discovery/today/remove/{day}", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> removeUselessBondDiscoveryTodayDealAndQuote(
			@ApiParam(name = "day", value = "格式yyyy-MM-dd", required = true) @PathVariable String day) {
		bondDiscoveryDataService.removeUselessBondDiscoveryTodayDealAndQuoteData(day);
		return new JsonResult<String>().ok("done");
	}

	@ApiOperation(value = "删除给定日期之前的异常价格数据，处理数据冗余")
	@RequestMapping(value = "/discovery/abnormal/remove/{day}", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> removeUselessBondDiscoveryAbnormalDealAndQuote(
			@ApiParam(name = "day", value = "格式yyyy-MM-dd", required = true) @PathVariable String day) {
		bondDiscoveryDataService.removeUselessBondDiscoveryAbnormalDealAndQuoteData(day);
		return new JsonResult<String>().ok("done");
	}

	@ApiOperation(value = "异常价格洗数据，处理isNCD")
	@RequestMapping(value = "/discovery/abnormal/isNCD/init", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> initBondDiscoveryAbnormalDealAndQuote() {
		bondDiscoveryDataService.initBondDiscoveryAbnormalDealIsNCD();
		bondDiscoveryDataService.initBondDiscoveryAbnormalQuoteIsNCD();
		return new JsonResult<String>().ok("done");
	}

	@ApiOperation(value = "异常价格洗数据，处理convertNetPrice")
	@RequestMapping(value = "/discovery/abnormal/convertNetPrice/init", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> initBondDiscoveryAbnormalConvertNetPrice() {
		String dealResult = bondDiscoveryDataService.initBondDiscoveryAbnormalDealConvertNetPrice();
		String quoteResult = bondDiscoveryDataService.initBondDiscoveryAbnormalQuoteConvertNetPrice();
		return new JsonResult<String>().ok(dealResult + quoteResult);
	}

//	@ApiOperation(value = "测试异常价格-成交价")
//	@RequestMapping(value = "/discovery/test/abnormal/deal", method = RequestMethod.POST, produces = "application/json")
//	public JsonResult<String> testSubscribeSaveBondDealAbnormalPrice(@RequestBody BondDeal bondDeal) {
//		bondDiscoveryDataService.subscribeSaveBondDealAbnormalPrice(bondDeal);
//		return new JsonResult<String>().ok("success");
//	}
//
//	@ApiOperation(value = "测试异常价格-报价")
//	@RequestMapping(value = "/discovery/test/abnormal/quote", method = RequestMethod.POST, produces = "application/json")
//	public JsonResult<String> testSubscribeSaveBondQuoteAbnormalPrice(@RequestBody BrokerBondQuoteParam quoteParam) {
//		bondDiscoveryDataService.subscribeSaveBondQuoteAbnormalPrice(quoteParam);
//		return new JsonResult<String>().ok("success");
//	}
}
