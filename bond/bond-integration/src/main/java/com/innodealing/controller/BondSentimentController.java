package com.innodealing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.bond.param.BondSentimentParam;
import com.innodealing.domain.JsonResult;
import com.innodealing.service.BondAnalysislIngtService;
import com.innodealing.service.BondSentimentDateJobService;
import com.innodealing.service.BondSentimentJobService;
import com.innodealing.service.BondSentimentNewsJobService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "舆情数据服务")
@RestController
@RequestMapping("api/bond/tasks/")
public class BondSentimentController {

	@Autowired
	BondSentimentJobService bondSentimentService1;
	
	@Autowired
	BondSentimentNewsJobService bondSentimentService2;
	
	@Autowired
	BondSentimentDateJobService bondSentimentJobDateService;
	
	@Autowired
	BondAnalysislIngtService analysislIngtService;
	
	@ApiOperation(value = "带情感的舆情数据NO.1")
	@RequestMapping(value = "/bonds/sentiment", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<String> bondSentiment(
			@ApiParam(name = "flay", value = "是否通知相关程序(默认false)",required = false) @RequestParam(defaultValue="false") Boolean flay) {
		return new JsonResult<String>().ok(bondSentimentService1.syncIntegration(flay));
	}
	
	@ApiOperation(value = "舆情数据NO.2")
	@RequestMapping(value = "/bonds/sentimentNews", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<String> bondSentimentNews(
			@ApiParam(name = "flay", value = "是否通知相关程序(默认false)",required = false)@RequestParam(defaultValue="false") Boolean flay) {
		return new JsonResult<String>().ok(bondSentimentService2.syncIntegration(flay));
	}
	
	@ApiOperation(value = "舆情日期数据NO.3")
	@RequestMapping(value = "/bonds/sentimentDate", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> bondDateSentiment() {
		return new JsonResult<String>().ok(bondSentimentJobDateService.syncIntegration());
	}

	@ApiOperation(value="清空-舆情信息(慎用)")
    @RequestMapping(value = "/sentiments/clean", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<String> clean(
    		@ApiParam(name = "key", value = "秘钥", required = true) @RequestParam String key
			) {
    	return new JsonResult<String>().ok(analysislIngtService.clean(key));
    }
	
	@ApiOperation(value="舆情信息-删除")
    @RequestMapping(value = "/sentiments/delete", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> delete(
    		@RequestBody BondSentimentParam bondSentimentParam
			) {
    	return new JsonResult<String>().ok(analysislIngtService.delete(bondSentimentParam));
    }
	
	@ApiOperation(value = "更新最近一个月舆情总数")
	@RequestMapping(value = "/bonds/sentimentsCount", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> sentimentsCount() {
		return new JsonResult<String>().ok(bondSentimentJobDateService.getSentimentMonthCount());
	}
	
}
