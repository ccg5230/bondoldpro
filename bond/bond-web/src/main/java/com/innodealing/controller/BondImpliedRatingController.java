package com.innodealing.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.adapter.BondInstitutionInduAdapter;
import com.innodealing.bond.service.BondDetailService;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.mongo.dm.BondDetailDoc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "债劵隐含评级对比")
@RestController
@RequestMapping("api/impliedRating/")
public class BondImpliedRatingController extends BaseController {
	
	@Autowired
	MongoOperations mongoOperations;
	
	@Autowired
	BondDetailService bondDetailService;
	
	@Autowired
	BondInstitutionInduAdapter induAdapter;
	
	@ApiOperation(value = "债劵隐含评级对比-->债劵数据")
	@RequestMapping(value = "/bonds", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Page<BondDetailDoc>> findImpliedRatingBonds(
	        @RequestHeader("userid") long userid,
			@ApiParam(name = "condition", value = "列") @RequestParam String condition,
			@ApiParam(name = "page", value = "页数", required = false) @RequestParam(defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue = "20") Integer limit,
			@ApiParam(name = "type", value = "类型,1信用债(不含短融/超短融)，2信用债(短融/超短融)", required = false) @RequestParam(defaultValue="1") Integer type) {
		return new JsonResult<Page<BondDetailDoc>>().ok(
		        induAdapter.conv(bondDetailService.findImpliedRatingBonds(condition,page,limit,type), userid).createPage());
	}
	
	@ApiOperation(value = "债劵隐含评级对比")
	@RequestMapping(value = "/lists", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Object> findImpliedRatingList(
			@ApiParam(name = "type", value = "类型,1信用债(不含短融/超短融)，2信用债(短融/超短融)", required = false) @RequestParam(defaultValue="1") Integer type) {
		return new JsonResult<Object>().ok(bondDetailService.findImpliedRatingList(type));
	}

	@ApiOperation(value = "债劵隐含评级对比-->债劵数据【含有行业信息作为查询条件】")
	@RequestMapping(value = "/{userId}/bonds", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Page<BondDetailDoc>> findImpliedRatingBonds(
			@ApiParam(name = "userId", value = "用户ID", required = true) @PathVariable Long userId,
			@ApiParam(name = "condition", value = "列") @RequestParam String condition,
			@ApiParam(name = "induIds", value = "行业ID") @RequestParam(defaultValue="") Long[] induIds,
			@ApiParam(name = "page", value = "页数", required = false) @RequestParam(defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue = "20") Integer limit,
			@ApiParam(name = "type", value = "类型,1信用债(不含短融/超短融)，2信用债(短融/超短融)", required = false) @RequestParam(defaultValue="1") Integer type) {
		return new JsonResult<Page<BondDetailDoc>>().ok(
		        induAdapter.conv(bondDetailService.findImpliedRatingBondsWithInduId(userId,condition,induIds,page,limit,type), userId).createPage());
	}
	
	@ApiOperation(value = "债劵隐含评级对比【含有行业信息作为查询条件】")
	@RequestMapping(value = "/{userId}/lists", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Object> findImpliedRatingList(
			@ApiParam(name = "userId", value = "用户ID", required = true) @PathVariable Long userId,
			@ApiParam(name = "type", value = "类型,1信用债(不含短融/超短融)，2信用债(短融/超短融)", required = false) @RequestParam(defaultValue="1") Integer type,
			@ApiParam(name = "induIds", value = "行业ID") @RequestParam(defaultValue="") Long[] induIds) {
		return new JsonResult<Object>().ok(bondDetailService.findImpliedRatingList(userId, type, induIds));
	}
}
