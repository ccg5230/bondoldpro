package com.innodealing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.domain.JsonResult;
import com.innodealing.service.BondFinanceRptService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author stephen.ma
 * @date 2017年8月25日
 * @clasename BondFinanceRptController.java
 * @decription TODO
 */

@Api(description = "债券主体财务财报的处理")
@RestController
@RequestMapping("api/bond/tasks/")
public class BondFinanceRptController {
	
	private @Autowired BondFinanceRptService bondFinanceRptService;
	
    @ApiOperation(value = "获取规定时间未收集到财报[每个月的第一天触发一次]")
    @RequestMapping(value = "/finance/uncollectedFinarpt", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> handleUncollectedFinarpt() throws Exception {
    	
        return new JsonResult<String>().ok(bondFinanceRptService.handleUncollectedFinarpt());
    }

}
