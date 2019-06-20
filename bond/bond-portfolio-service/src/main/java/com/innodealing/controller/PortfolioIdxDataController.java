package com.innodealing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.domain.JsonResult;
import com.innodealing.service.PortfolioIdxDataService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "债券投资组合筛选条件同步数据接口[内部调用,运行一次即可]")
@RestController
@RequestMapping("api/bond/portfolio/idxData")
public class PortfolioIdxDataController {
	
	@Autowired
	private PortfolioIdxDataService portfolioIdxDataService;

	@ApiOperation(value="构建异常价格条件到缓存")
	@RequestMapping(value="/favPriceIdx",method=RequestMethod.POST,produces="application/json")
	public JsonResult<String> buildFavPriceIdx(){
		
		return new JsonResult<String>().ok(portfolioIdxDataService.buildFavPriceIdx());
	}
	
	@ApiOperation(value="构建财务指标条件到缓存")
	@RequestMapping(value="/favFinIdx",method=RequestMethod.POST,produces="application/json")
	public JsonResult<String> buildFavFinIdx(){
		
		return new JsonResult<String>().ok(portfolioIdxDataService.buildFavFinIdx());
	}
	
	@ApiOperation(value="构建存续条件到缓存")
	@RequestMapping(value="/favMaturityIdx",method=RequestMethod.POST,produces="application/json")
	public JsonResult<String> buildFavMaturityIdx(){
		
		return new JsonResult<String>().ok(portfolioIdxDataService.buildFavMaturityIdx());
	}
	
	
	@ApiOperation(value="构建评级条件到缓存")
	@RequestMapping(value="/favRatingIdx",method=RequestMethod.POST,produces="application/json")
	public JsonResult<String> buildFavRatingIdx(){
		
		return new JsonResult<String>().ok(portfolioIdxDataService.buildFavRatingIdx());
	}
	
	@ApiOperation(value="构建舆情新闻预警条件到缓存")
	@RequestMapping(value="/favSentimentIdx",method=RequestMethod.POST,produces="application/json")
	public JsonResult<String> buildFavSentimentIdx(){
		
		return new JsonResult<String>().ok(portfolioIdxDataService.buildFavSentimentIdx());
	}
	
	@ApiOperation(value="构建其他条件到缓存")
	@RequestMapping(value="/favOtherIdx",method=RequestMethod.POST,produces="application/json")
	public JsonResult<String> buildFavOtherIdx(){
		
		return new JsonResult<String>().ok(portfolioIdxDataService.buildFavOtherIdx());
	}
	
	@ApiOperation(value="初始化构建含有中债权限的账号到缓存中")
	@RequestMapping(value="/initUserDebtRole",method=RequestMethod.POST,produces="application/json")
	public JsonResult<String> initUserDebtRole(){
		
		return new JsonResult<String>().ok(portfolioIdxDataService.initUserDebtRole());
	}
	
}
