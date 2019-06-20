package com.innodealing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.model.mysql.BondInstSystem;
import com.innodealing.service.SystemService;
import com.innodealing.util.JsonResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * 系统参数设置
 * @author 戴永杰
 *
 * @date 2017年9月8日 上午9:22:44 
 * @version V1.0   
 *
 */
@Api(description = "系统参数设置")
@RestController
@RequestMapping("/system")
public class SystemController extends BaseController {

	@Autowired
	private SystemService systemService;

	@ApiOperation(value = "获取财务数据状态")
	@RequestMapping(value = "/getFinancialStatus", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<BondInstSystem> getFinancialStatus(
			@ApiParam(name = "userid", value = "用户ID ", required = true) @RequestParam Integer userid
			){
		return ok(systemService.getFinancialStatus(getCurrentUser().getUserId(),getCurrentUser().getRoleId()));
	}
	

	@ApiOperation(value = "设置财务数据状态")
	@RequestMapping(value = "/setfinancialStatus", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Integer>  setfinancialStatus(
			@ApiParam(name = "userid", value = "用户ID ", required = true) @RequestParam Integer userid,
			@ApiParam(name = "id", value = "唯一标识 ", required = true) @RequestParam Integer id,
			@ApiParam(name = "status", value = "状态 0 未开启 1 已开启 ", required = true) @RequestParam Integer status
			){
		return ok(systemService.setfinancialStatus(userid,id,status));
		
	}


}
