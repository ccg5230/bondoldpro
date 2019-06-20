package com.innodealing.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.bond.service.BondUserInstitution;
import com.innodealing.bond.service.BondUserOperationService;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.mongo.dm.bond.user.UserContactDoc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "用户接口")
@RestController
@RequestMapping("api/bond/users")
public class BondUserController extends BaseController{

	@Autowired
	BondUserOperationService bondUserOperationService;
	
	@Autowired
	BondUserInstitution bondUserInstitution;
	
	
	@ApiOperation(value = "联系我们-[zzl]")
	@RequestMapping(value = "/contact", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Boolean> contact(
			@RequestBody UserContactDoc userContact) {
		return new JsonResult<Boolean>().ok(bondUserOperationService.contactUs(userContact));
	}
	
	
	@ApiOperation(value = "用户是否有权限查看债券DashBoard-[mf]")
	@RequestMapping(value = "/{userId}/permission/dashboard", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Boolean> dashboardPerm(
			@ApiParam(name = "userId", value = "用户ID", required = true) @PathVariable Long userId
			) {
		return new JsonResult<Boolean>().ok(bondUserOperationService.getPaymentPerm(userId));
	}

	@ApiOperation(value = "用户是否有权限查看付费模块-[zzl]")
	@RequestMapping(value = "/{userId}/permission/payment", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Boolean> paymentPerm(
			@ApiParam(name = "userId", value = "用户ID", required = true) @PathVariable Long userId
			) {
		return new JsonResult<Boolean>().ok(bondUserOperationService.getPaymentPerm(userId));
	}
	
	@ApiOperation(value = "用户是否有权限导出报告模块-[xiaochao]")
	@RequestMapping(value = "/{userId}/permission/report", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Boolean> reportPerm(
			@ApiParam(name = "userId", value = "DM用户编号", required = true) @PathVariable Long userId) {
		return new JsonResult<Boolean>().ok(bondUserOperationService.getReportPerm(userId));
	}
	
	@ApiOperation(value = "用户债劵板块权限体系")
	@RequestMapping(value = "/{userId}/authorization", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Map<String,Object>> bondAuthorization(
			@ApiParam(name = "userId", value = "DM用户编号", required = true) @PathVariable Long userId) {
		return new JsonResult<Map<String,Object>>().ok(bondUserOperationService.bondAuthorization(userId));
	}
	
	@ApiOperation(value = "用户债劵板块头部导航权限体系")
	@RequestMapping(value = "/headAuthorization", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Map<String,Object>> headAuthorization(
			@RequestHeader("userid") Long userid) {
		return new JsonResult<Map<String,Object>>().ok(bondUserOperationService.headAuthorization(userid));
	}
	
	@ApiOperation(value = "用户债劵板块自定义行业权限")
	@RequestMapping(value = "/instInduAuthorization", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Boolean> instInduAuthorization(
			@RequestHeader("userid") Long userid,
			@ApiParam(name = "type", value = "类型 1[机构] 2[用户]", required = false) @RequestParam(defaultValue = "1") Integer type) {
		return new JsonResult<Boolean>().ok(bondUserOperationService.instInduAuthorization(userid,type));
	}
	
	@ApiOperation(value = "用户债劵板块数据权限")
	@RequestMapping(value = "/instDataAuthorization", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Map<String,Object>> instDataAuthorization(
			@RequestHeader("userid") Long userid) {
		return new JsonResult<Map<String,Object>>().ok(bondUserOperationService.instDataAuthorization(userid));
	}
	
	@ApiOperation(value = "查看用户所属机构")
	@RequestMapping(value = "/userOrg", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Integer> userOrg(
			@RequestHeader("userid") Long userid) {
		return new JsonResult<Integer>().ok(bondUserInstitution.getUserInstitutionByUserId(userid.toString()));
	}
	
}
