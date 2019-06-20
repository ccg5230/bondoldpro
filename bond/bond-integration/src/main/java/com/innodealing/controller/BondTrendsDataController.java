package com.innodealing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.innodealing.domain.JsonResult;
import com.innodealing.service.BondTrendsDataService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author xiaochao
 * @time 2017年4月13日
 * @description: 
 */
@Api(description = "债券动态-数据构建")
@RestController
@RequestMapping("api/bond/tasks")
public class BondTrendsDataController {

	@Autowired
	private BondTrendsDataService bondTrendsDataService;

	@ApiOperation(value = "构建最近一天的隐含评级变动数据到MongoDB")
	@RequestMapping(value = "/trends/imprating/today", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> buildTodayBondImpRatingChangeHistData() {
		return new JsonResult<String>().ok(bondTrendsDataService.buildTodayBondImpRatingChange());
	}

	@ApiOperation(value = "重新初始化隐含评级变动数据到MongoDB")
	@RequestMapping(value = "/trends/imprating/init/{encryptKey}", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> initBondImpRatingChangeHistData(
			@ApiParam(name = "encryptKey", value = "密钥", required = true) @PathVariable String encryptKey,
			@ApiParam(name = "startDateStr", value = "开始时间[yyyy-MM-dd]，为空则最前一天") @RequestParam(required = false) String startDateStr,
			@ApiParam(name = "endDateStr", value = "结束时间[yyyy-MM-dd]，为空则最后一天") @RequestParam(required = false) String endDateStr) {
		return new JsonResult<String>().ok(bondTrendsDataService.initBondImpRatingChangeHistData(encryptKey, startDateStr, endDateStr));
	}
}
