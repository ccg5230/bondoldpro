package com.innodealing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.model.vo.BondDetail;
import com.innodealing.model.vo.BondDetailBase;
import com.innodealing.model.vo.BondDetailInfo;
import com.innodealing.service.BondDetailService;
import com.innodealing.util.JsonResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * 
 * @author 戴永杰
 *
 * @date 2017年12月26日 下午6:17:01 
 * @version V1.0   
 *
 */

@Api(description = "主体详情页")
@RequestMapping("/bondDetail")
@RestController
public class BondDetailController extends BaseController{

	@Autowired
	private BondDetailService bondDetailService;
	
	@ApiOperation(value = "获取债券发行人概述")
	@RequestMapping(value = "/getBondDetailInfo", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<BondDetailInfo> getBondDetailInfo(
			@ApiParam(name = "comUniCode", value = "发行人标识", required = true) @RequestParam String comUniCode) {
		return ok(bondDetailService.getBondDetailInfo(comUniCode));
	}
	
	@ApiOperation(value = "获取债券发行人基本信息")
	@RequestMapping(value = "/getBondDetailBase", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<BondDetailBase> getBondDetailBase(
			@ApiParam(name = "comUniCode", value = "发行人标识", required = true) @RequestParam String comUniCode) {
		return ok(bondDetailService.getBondDetailBase(comUniCode));
	}
	
	@ApiOperation(value = "获取债券发行人详情")
	@RequestMapping(value = "/getBondDetail", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<BondDetail> getBondDetail(
	         @ApiParam(name = "bondId", value = "债券唯一编号", required = true) @RequestParam String bondId){
		return ok(bondDetailService.getBondDetail(bondId));
	}

}
