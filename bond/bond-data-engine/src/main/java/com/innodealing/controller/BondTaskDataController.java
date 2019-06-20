package com.innodealing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.domain.JsonResult;
import com.innodealing.service.BondFinancePrimaryDataService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "财务数据任务服务")
@RestController
@RequestMapping("api/bond/tasks/")
public class BondTaskDataController {
	
	private @Autowired BondFinancePrimaryDataService bondFinancePrimaryDataService;
	
	@ApiOperation(value = "私人报表检查任务状态变化")
	@RequestMapping(value = "amaresun/calculationResult", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> calculationResult() {
		
		bondFinancePrimaryDataService.calculationResult();
		
        return new JsonResult<String>().ok("ok");
	}

}
