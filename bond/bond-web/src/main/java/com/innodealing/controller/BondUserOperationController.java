package com.innodealing.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.bond.service.BondUserOperationService;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.mongo.dm.BondUserOperation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "记录用户操作数据接口")
@RestController
@RequestMapping("api/bond/")
public class BondUserOperationController extends BaseController{

	@Autowired
	BondUserOperationService bondUserOperationService;
	
	
	@ApiOperation(value = "新增-用户操作")
	@RequestMapping(value = "/User/{userId}/insert", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Long> insert(
			@RequestBody BondUserOperation bondUserOperation) {
		return new JsonResult<Long>().ok(bondUserOperationService.insert(bondUserOperation));
	}
	
	@ApiOperation(value = "删除-用户操作")
	@RequestMapping(value = "/User/{userId}/delete", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Long> delete(
			@ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam Long userId,
			@ApiParam(name = "bondUniCode", value = "代码", required = true) @RequestParam Long bondUniCode,
			@ApiParam(name = "type", value = "类型:1债劵(默认)2发行人", required = false)@RequestParam(defaultValue="1") Integer type) {
		return new JsonResult<Long>().ok(bondUserOperationService.delete(userId,bondUniCode,type));
	}
	
	@ApiOperation(value = "查询-用户操作")
	@RequestMapping(value = "/User/{userId}/find", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<BondUserOperation>> find(
			@ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam Long userId,
			@ApiParam(name = "type", value = "类型:1债劵(默认)2发行人", required = false) @RequestParam(defaultValue="1") Integer type) {
		return new JsonResult<List<BondUserOperation>>().ok(bondUserOperationService.find(userId,type));
	}
	
	@ApiOperation(value = "查询用户中债权限")
	@RequestMapping(value = "/User/{userId}/findDebtBasic", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<String> findDebtBasic(
			@ApiParam(name = "userId", value = "用户ID", required = true) @PathVariable Long userId){
		
		return  new JsonResult<String>().ok(bondUserOperationService.findDebtBasic(userId));
	}

}
