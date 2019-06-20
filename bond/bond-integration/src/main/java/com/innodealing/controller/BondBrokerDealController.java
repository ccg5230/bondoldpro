package com.innodealing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.bond.param.BondBrokerDealParam;
import com.innodealing.domain.JsonResult;
import com.innodealing.service.BondBrokerDealService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "Broker债券数据服务")
@RestController
@RequestMapping("api/bond/tasks/")
public class BondBrokerDealController {

	private final static Logger logger = LoggerFactory.getLogger(BondBrokerDealController.class);

	private @Autowired BondBrokerDealService bondBrokerDealService;

	@ApiOperation(value = "构建broker成交数据(内部调用)")
	@RequestMapping(value = "/deals/broker", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Long> handleBrokerData(@RequestBody BondBrokerDealParam params) {
		logger.info("handleBrokerData BondCode:"+params.getBondCode());
		return new JsonResult<Long>().ok(bondBrokerDealService.handleBrokerData(params));
	}
}
