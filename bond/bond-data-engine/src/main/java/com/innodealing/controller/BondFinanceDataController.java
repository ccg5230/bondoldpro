package com.innodealing.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.domain.JsonResult;
import com.innodealing.service.BondCcxeIncrDataChangePollService;
import com.innodealing.service.BondFinanceCcxeDataSnapShotService;
import com.innodealing.service.BondFinanceDataService;
import com.innodealing.service.BondFinancePrimaryDataService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "财务数据服务")
@RestController
@RequestMapping("api/bond/tasks/")
public class BondFinanceDataController {

	@Autowired
	BondFinanceDataService bondFinanceDataService;

	@Autowired
	BondCcxeIncrDataChangePollService ccxeIncrDataChangePoller;
	
	@Autowired
	BondFinanceCcxeDataSnapShotService bondFinanceCcxeDataSnapShotService;
	
	@Autowired
	BondFinancePrimaryDataService bondFinancePrimaryDataService;

	@ApiOperation(value = "全量输入财务数据")
	@RequestMapping(value = "/finance/input", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> inputFinanceData() {
		bondFinanceDataService.syncIntegration();
        return new JsonResult<String>().ok("ok");
	}
	
    @ApiOperation(value = "找出增量更新数据")
    @RequestMapping(value = "/ccxe/incrConstruction/{modelBeanName}", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> processIncrementalCcxeData(
            @ApiParam(name = "modelBeanName", value = "modelBeanName", required = true) @PathVariable String modelBeanName, 
            @RequestParam(name = "fromTime", value = "fromTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fromTime
            ) throws Exception {
        ccxeIncrDataChangePoller.pollChange(modelBeanName, fromTime);
        return new JsonResult<String>().ok("ok");
    }
    
    @ApiOperation(value = "根据参数找出增量更新数据")
    @RequestMapping(value = "/ccxe/incrementalCcxeData/{modelBeanName}", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> incrementalCcxeData(
            @ApiParam(name = "modelBeanName", value = "modelBeanName", required = true) @PathVariable String modelBeanName, 
            @RequestParam(name = "fromTime", value = "fromTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fromTime
            ) throws Exception {
    	bondFinanceCcxeDataSnapShotService.pollChange(modelBeanName, fromTime);
        return new JsonResult<String>().ok("ok");
    }
    
    @ApiOperation(value = "获取安硕用户授权")
    @RequestMapping(value = "/amaresun/auth", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> getAuth() throws Exception {
        return new JsonResult<String>().ok(bondFinancePrimaryDataService.getAuth());
    }

    @ApiOperation(value = "安硕计算入口")
    @RequestMapping(value = "/amaresun/gate", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<String> calculateGate(
    		@RequestParam(name = "taskId", value = "taskId", required = true) @PathVariable Long taskId
            ) throws Exception {
        return new JsonResult<String>().ok(bondFinancePrimaryDataService.calculateGate(taskId));
    }
    
    /*
    @ApiOperation(value = "私人财报-安硕计算完成后调用的接口")
    @RequestMapping(value = "/amaresun/calculationresult", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> calculationResult(
    		@RequestParam(name = "taskId", value = "taskId", required = true) @PathVariable Long taskId
            ) throws Exception {
        return new JsonResult<String>().ok(bondFinancePrimaryDataService.calculationResult(taskId));
    }
    */
    
}
