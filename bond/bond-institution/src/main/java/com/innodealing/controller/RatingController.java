package com.innodealing.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.model.mysql.BondInstCode;
import com.innodealing.service.RatingService;
import com.innodealing.util.JsonResult;
import com.innodealing.util.KitCost;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 信评模块
 * 
 * @author 戴永杰
 *
 * @date 2017年9月11日 下午2:11:10 
 * @version V1.0   
 *
 */
@Api(description = "信评模块")
@RestController
@RequestMapping("/rating")
public class RatingController extends BaseController{
	
	@Autowired
	private RatingService ratingService;
	
	
	@ApiOperation(value = "获取内部信评")
	@RequestMapping(value = "/getInnerRateList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<List<BondInstCode>> getInnerRateList(
			@ApiParam(name = "userid", value = "用户ID ", required = true) @RequestParam Integer userid
			){
		return ok(ratingService.getInnerRateList(getCurrentUser().getOrgId()));
	}
	
	@ApiOperation(value = "设置内部评级")
	@RequestMapping(value = "/setInnerRate", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Integer> setInnerRate(
			@ApiParam(name = "userid", value = "用户ID ", required = true) @RequestParam Integer userid,
			@ApiParam(name = "innerRate", value = "内部评级 ", required = true) @RequestParam String innerRate
			){
		List<BondInstCode> rateList  = new ArrayList<BondInstCode>();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> innerRateArr =  KitCost.jsonToObject(innerRate,List.class);
		for (Map<String, Object> m : innerRateArr) {
			rateList.add(KitCost.mapToBean(m, BondInstCode.class));
		}
		return ok(ratingService.setInnerRate(userid,getCurrentUser().getOrgId(),rateList));
	}
	
	@ApiOperation(value = "获取投资建议")
	@RequestMapping(value = "/getInvestSuggestList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<List<BondInstCode>> getInvestSuggestList(
			@ApiParam(name = "userid", value = "用户ID ", required = true) @RequestParam Integer userid
			){
		return ok(ratingService.getInvestSuggestList(getCurrentUser().getOrgId()));
	}
	
	@ApiOperation(value = "设置投资建议")
	@RequestMapping(value = "/setInvestSuggest", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Integer> setInvestSuggest(
			@ApiParam(name = "userid", value = "用户ID ", required = true) @RequestParam Integer userid,
			@ApiParam(name = "investSuggest", value = "投资建议", required = true) @RequestParam String investSuggest
			){
		List<BondInstCode> rateList  = new ArrayList<BondInstCode>();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> innerRateArr =  KitCost.jsonToObject(investSuggest,List.class);
		for (Map<String, Object> m : innerRateArr) {
			rateList.add(KitCost.mapToBean(m, BondInstCode.class));
		}
		return ok(ratingService.setInvestSuggest(userid,getCurrentUser().getOrgId(),rateList));
	}
	
	
}
