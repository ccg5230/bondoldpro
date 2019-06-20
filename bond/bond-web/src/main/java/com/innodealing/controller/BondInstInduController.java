package com.innodealing.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.bond.service.BondInstInduService;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.dm.bond.BondInstComIndu;
import com.innodealing.model.dm.bond.BondInstIndu;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "债券-机构行业信息")
@RestController
@RequestMapping("api/bond/inst")
public class BondInstInduController extends BaseController{
	
	private static final Logger LOG = LoggerFactory.getLogger(BondInstInduController.class);
	
	@Autowired
	BondInstInduService bondInstInduService;

    @ApiOperation(value = "根据机构修改行业信息")
    @RequestMapping(value = "/indu/save", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> saveIndu(
    		@ApiParam(name = "json", value = "行业信息", required = true) @RequestBody List<BondInstIndu> json) {
    	LOG.info("saveIndu,json:"+json);
        return new JsonResult<String>().ok(bondInstInduService.saveIndu(json));
    }
    
    
    @ApiOperation(value = "根据机构修改行业与发行人关系")
    @RequestMapping(value = "/comIndu/save", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> saveComIndu(
    		@ApiParam(name = "json", value = "行业与发行人关系", required = true) @RequestBody List<BondInstComIndu> json) {
    	LOG.info("saveComIndu,json:"+json);
        return new JsonResult<String>().ok(bondInstInduService.saveComIndu(json));
    }
    
    
   
    
}
