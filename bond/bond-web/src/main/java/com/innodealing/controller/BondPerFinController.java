package com.innodealing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.bond.service.BondPerFinService;
import com.innodealing.bond.vo.perfin.BondPerFinanceVo;
import com.innodealing.domain.JsonResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author stephen.ma
 * @date 2017年3月13日
 * @clasename BondPerFinController.java
 * @decription TODO
 */
@Api(description = "私人财报接口")
@RestController
@RequestMapping("api/bond/")
public class BondPerFinController extends BaseController {
	
	@Autowired
	private BondPerFinService bondPerFinService;
	
	@ApiOperation(value="获取私人财报信息")
    @RequestMapping(value = "/users/{userId}/perfinreport", method = RequestMethod.GET, produces = "application/json")
	 public JsonResult<Page<BondPerFinanceVo>> findPerfinreport(
			 @ApiParam(name = "userId", value = "用户账号ID", required = true) @PathVariable Integer userId,
			 @ApiParam(name = "keyword", value = "查询关键字") String keyword,
			 @ApiParam(name = "pageIndex", value = "页数,数字:1", required = true) @RequestParam Integer pageIndex,
			 @ApiParam(name = "pageSize", value = "每页显示数量,数字:20", required = true) @RequestParam Integer pageSize)
	{
		
		return new JsonResult<Page<BondPerFinanceVo>>().ok(bondPerFinService.findPerfinreport(userId, keyword, pageIndex-1, pageSize));
	}
	
}
