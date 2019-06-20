package com.innodealing.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.bond.service.BondGuidePriceService;
import com.innodealing.bond.vo.indu.InduVo;
import com.innodealing.domain.JsonResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author stephen.ma
 * @date 2016年9月28日
 * @clasename BondGuidePriceController.java
 * @decription TODO
 */
@Api(description = "债券指导价")
@RestController
@RequestMapping("api/bond/")
public class BondGuidePriceController extends BaseController {

	private @Autowired BondGuidePriceService bondGuidePriceService;
	
    @ApiOperation(value = "债券指导价")
    @RequestMapping(value = "/bonds/guideprice", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String findGuidePrice( @ApiParam(name = "date", value = "时间，格式 yyyy-MM-dd", required = true) @RequestParam String date) {
        return bondGuidePriceService.findGuidePrice(date);
    }
	
}
