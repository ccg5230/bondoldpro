package com.innodealing.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.domain.JsonResult;
import com.innodealing.service.BondBasicInfoClassOneService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "城投债财务信息数据构建接口")
@RestController
@RequestMapping("api/bondClassOne/bonds/")
public class BondBasicClassOneBuildController extends BaseController {
	
	private @Autowired BondBasicInfoClassOneService bondBasicInfoClassOneService;
	
    @ApiOperation(value = "重建一级发行mongodb数据：不包含非当日取消发行数据")
    @RequestMapping(value = "/integrations/rebuild", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<Boolean> rebuildDuration(){
        return new JsonResult<Boolean>().ok(bondBasicInfoClassOneService.bulid());
    }
    
    @ApiOperation(value = "一级发行mongodb删除已上市新债数据")
    @RequestMapping(value = "/integrations/deleteMarketedNewBond", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<Boolean> deleteMarketedNewBond(
    		@ApiParam(name = "bondUniCodes", value = "债券唯一编号list", required = true) @RequestBody Long[] bondUniCodes){
        return new JsonResult<Boolean>().ok(bondBasicInfoClassOneService.deleteMarketedNewBond(bondUniCodes));
    }
    
    @ApiOperation(value = "新增一级发行mongodb新债数据")
    @RequestMapping(value = "/integrations/{bondUniCode}/addAndUpdateNewBond", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<Boolean> addNewBond(
    		@ApiParam(name = "bondUniCode", value = "债券唯一编号", required = true) @PathVariable Long bondUniCode){
        return new JsonResult<Boolean>().ok(bondBasicInfoClassOneService.addAndUpdateNewBond(bondUniCode));
    }
    
    @ApiOperation(value = "一级发行mongodb删除已截标新债数据：按结束时间")
    @RequestMapping(value = "/integrations/deleteStopBidNewBond", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<Boolean> deleteStopBidNewBond(){
        return new JsonResult<Boolean>().ok(bondBasicInfoClassOneService.deleteStopBidNewBond());
    }
    
}
