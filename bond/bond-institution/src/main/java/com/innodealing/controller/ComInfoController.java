package com.innodealing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.innodealing.model.mysql.BondInstComIndu;
import com.innodealing.service.ComInfoService;
import com.innodealing.util.JsonResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author liuqi
 *
 */
@Api(description = "发行人信息接口")
@RestController
@RequestMapping("api/institution/com")
public class ComInfoController extends BaseController {

	@Autowired
	private ComInfoService comInfoService;

	@ApiOperation(value = "全部发行人")
	@RequestMapping(value = "/comList", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<PageInfo<List<BondInstComIndu>>> comList(@RequestHeader("userId") Integer userId,
			@ApiParam(name = "induUniCode", value = "行业id", required = false) @RequestParam(required = false) Integer induUniCode,
			@ApiParam(name = "comChiName", value = "发行人关键字", required = false) @RequestParam(required = false) String comChiName,
			@ApiParam(name = "type", value = "1父类2子类", required = false) @RequestParam(required = false) Integer type,
			@ApiParam(name = "page", value = "页数", required = false) @RequestParam(defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue = "10") Integer limit) {
		return new JsonResult<PageInfo<List<BondInstComIndu>>>()
				.ok(comInfoService.queryComListByInstIndu(induUniCode, userId, type,comChiName, page, limit));
	}

	@ApiOperation(value = "未添加行业的发行人")
	@RequestMapping(value = "/notComInduList", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<PageInfo<List<BondInstComIndu>>> notComInduList(@RequestHeader("userId") Integer userId,
			@ApiParam(name = "comChiName", value = "发行人关键字", required = false) @RequestParam(required = false) String comChiName,
			@ApiParam(name = "page", value = "页数", required = false) @RequestParam(defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue = "10") Integer limit) {
		return new JsonResult<PageInfo<List<BondInstComIndu>>>()
				.ok(comInfoService.queryComNotInduList(userId, comChiName, page, limit));
	}

	@ApiOperation(value = "新增发行人与行业之间关系")
	@RequestMapping(value = "/insert", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Boolean> insert(@RequestHeader("userId") Integer userId,
			@ApiParam(name = "cominduList", value = "发行人行业关系", required = true) @RequestBody List<BondInstComIndu> cominduList) {
		return new JsonResult<Boolean>().ok(comInfoService.insertBondInstComIndu(userId, cominduList));
	}

	@ApiOperation(value = "修改发行人与行业之间关系")
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Boolean> update(@RequestHeader("userId") Integer userId,
			@ApiParam(name = "cominduList", value = "发行人行业关系", required = true) @RequestBody List<BondInstComIndu> cominduList) {
		return new JsonResult<Boolean>().ok(comInfoService.updateBondInstComIndu(userId, cominduList));
	}
	
	

}
