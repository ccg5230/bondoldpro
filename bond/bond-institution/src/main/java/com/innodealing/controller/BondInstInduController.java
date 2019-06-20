package com.innodealing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.model.mysql.BondInstIndu;
import com.innodealing.service.BondInstInduService;
import com.innodealing.util.JsonResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author liuqi
 *
 */
@Api(description = "机构行业信息接口")
@RestController
@RequestMapping("api/institution/indu")
public class BondInstInduController extends BaseController{
	
	@Autowired
	private BondInstInduService bondInstInduService;
	
	@ApiOperation(value = "机构行业列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<BondInstIndu>> list(
			@RequestHeader("userId") Integer userId,
    		@ApiParam(name = "induClassName", value = "行业名关键字", required = false) @RequestParam(required=false)  String induClassName
			) {
    	return new JsonResult<List<BondInstIndu>>().ok(bondInstInduService.queryBondInstInduList(userId,induClassName));
    }
	
	@ApiOperation(value = "新增行业")
	@RequestMapping(value = "/insert", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<BondInstIndu> insert(
    		@RequestHeader("userId") Integer userId,
    		@ApiParam(name = "indu", value = "行业", required = true) @RequestBody BondInstIndu indu
			) {
    	return new JsonResult<BondInstIndu>().ok(bondInstInduService.insertBondInstIndu(userId,indu));
    }
	
	@ApiOperation(value = "修改行业")
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<Boolean> update(
    		@RequestHeader("userId") Integer userId,
    		@ApiParam(name = "indu", value = "行业", required = true) @RequestBody BondInstIndu indu
			) {
    	return new JsonResult<Boolean>().ok(bondInstInduService.updateBondInstIndu(userId,indu));
    }
	
	@ApiOperation(value = "删除行业")
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<Boolean> delete(
    		@RequestHeader("userId") Integer userId,
    		@ApiParam(name = "induUniCodeList", value = "行业id", required = true) @RequestBody  List<BondInstIndu> induUniCodeList
			) {
    	return new JsonResult<Boolean>().ok(bondInstInduService.deleteBondInstIndu(userId,induUniCodeList));
    }
	
	@ApiOperation(value = "修改行业规则")
	@RequestMapping(value = "/updateInduRule", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<Boolean> updateInduRule(
    		@RequestHeader("userId") Integer userId,
    		@ApiParam(name = "indu", value = "行业", required = true) @RequestBody BondInstIndu indu
			) {
    	return new JsonResult<Boolean>().ok(bondInstInduService.updateInduRule(userId,indu));
    }
}
