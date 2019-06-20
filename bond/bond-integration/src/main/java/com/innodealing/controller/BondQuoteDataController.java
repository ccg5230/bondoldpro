package com.innodealing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.bond.monitor.BondQuoteDataMonitor;
import com.innodealing.bond.service.BondQuoteService;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.dm.bond.BondQuote;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author stephen.ma
 * @date 2016年9月1日
 * @clasename BondQuoteController.java
 * @decription TODO
 */
@Api(description = "债券价数据处理-内部系统调用")
@RestController
@RequestMapping("api/bond")
public class BondQuoteDataController {
	
	private final static Logger logger = LoggerFactory.getLogger(BondQuoteDataController.class);


	private @Autowired BondQuoteService bondQuoteService;
	
	private @Autowired BondQuoteDataMonitor bondQuoteDataMonitor;
	
	@ApiOperation(value = "内部系统獲取債券報價")
	@RequestMapping(value = "/quotes/quote/{quoteId}", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<BondQuote> findBondQuoteById(
			@ApiParam(name = "quoteId", value = "债券报价ID，t_bond_quote的主键ID", required = true) @PathVariable Long quoteId) {
//		System.setProperty("user.timezone","Asia/Shanghai");
//		TimeZone timeZone = TimeZone.getDefault();
//		logger.info("===TimeZone in JVM==="+timeZone);
		return new JsonResult<BondQuote>().ok(bondQuoteService.findBondQuoteById(quoteId));
	}
	
	@ApiOperation(value = "根据oldQuoteId获取内部系统獲取債券報價")
	@RequestMapping(value = "/quotes/norquote/{oldQuoteId}", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<BondQuote> findBondQuoteByQuoteId(
			@ApiParam(name = "oldQuoteId", value = "债券报价同步老系统的ID，t_bond_quote的quote_id", required = true) @PathVariable String oldQuoteId){
//		System.setProperty("user.timezone","Asia/Shanghai");
//		TimeZone timeZone = TimeZone.getDefault();
//		logger.info("===TimeZone in JVM==="+timeZone);
		return new JsonResult<BondQuote>().ok(bondQuoteService.findBondQuoteByQuoteId(oldQuoteId));
	}
	
	
	@ApiOperation(value = "计算统计每隔一段时间（自己定义）的报价数据")
	@RequestMapping(value = "/quotes/data/monitor", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> findBondQuoteById() {
		return new JsonResult<String>().ok(bondQuoteDataMonitor.monitorBondQuoteData());
	}
}
