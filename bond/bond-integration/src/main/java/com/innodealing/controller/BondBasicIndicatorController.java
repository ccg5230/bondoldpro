package com.innodealing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.domain.JsonResult;
import com.innodealing.service.BondFundamentalIndicatorCalculateService;

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
public class BondBasicIndicatorController {

    private final static Logger logger = LoggerFactory.getLogger(BondBasicIndicatorController.class);

    private @Autowired BondFundamentalIndicatorCalculateService indicatorCalculateService;
    
    @ApiOperation(value = "重建久期数据")
    @RequestMapping(value = "/duration/rebuild", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> rebuildDuration(){
        return new JsonResult<String>().ok(indicatorCalculateService.rebuild());
    }
    
    @ApiOperation(value = "重建久期数据")
    @RequestMapping(value = "/duration/forceRebuild", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> forceRebuildDuration(){
        return new JsonResult<String>().ok(indicatorCalculateService.forceRebuildDuration());
    }

    @ApiOperation(value = "根据债券code重建久期数据")
    @RequestMapping(value = "/duration/rebuildByBondCode", method = RequestMethod.POST)
    public JsonResult<String> rebuildDurationByCode(
    		@ApiParam(name = "bondCode", value = "code, 例如011697008.IB") @RequestParam(required = true) String bondCode
    		){
        return new JsonResult<String>().ok(indicatorCalculateService.rebuildDurationByCode(bondCode));
    }
    
}
