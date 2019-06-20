package com.innodealing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.bond.service.BondSentimentService;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.mongo.dm.BondSentimentDistinctDoc;
import com.innodealing.model.mongo.dm.BondSentimentDoc;
import com.innodealing.model.mongo.dm.BondSentimentJsonDoc;
import com.innodealing.model.mongo.dm.BondSentimentTopDateDoc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "舆情信息接口")
@RestController
@RequestMapping("api/bond/")
public class BondSentimentController extends BaseController {

	@Autowired
	BondSentimentService bondSentimentService;

	@ApiOperation(value = "查询-所选期限内舆情列表")
	@RequestMapping(value = "/sentiments", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Page<BondSentimentDistinctDoc>> findListByDate(
			@ApiParam(name = "startTime", value = "开始日期yyyy-MM-dd", required = false) @RequestParam(required = false) String startTime,
			@ApiParam(name = "endTime", value = "结束日期yyyy-MM-dd", required = false) @RequestParam(required = false) String endTime,
			@ApiParam(name = "comUniCode", value = "发行人", required = true) @RequestParam String comUniCode,
			@ApiParam(name = "page", value = "页数", required = false) @RequestParam(defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue = "20") Integer limit,
			@ApiParam(name = "type", value = "1代表:重点新闻", required = false) @RequestParam(required = false) Integer type) {
		return new JsonResult<Page<BondSentimentDistinctDoc>>()
				.ok(bondSentimentService.findListByDate(startTime, endTime, comUniCode, page, limit, type));
	}

	@ApiOperation(value = "查询-同行业正面舆情数TOP5")
	@RequestMapping(value = "/sentiments/{userId}/positiveTop5", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<BondSentimentJsonDoc>> findListByCodeOrder(
			@ApiParam(name = "induId", value = "行业编号", required = true) @RequestParam Long[] induId,
			@ApiParam(name = "type", value = "类型 sentimentPositive正面sentimentNegative负面count舆情总数", required = true) @RequestParam String type,
			@ApiParam(name = "startTime", value = "开始日期yyyy-MM-dd", required = true) @RequestParam String startTime,
			@ApiParam(name = "endTime", value = "结束日期yyyy-MM-dd", required = true) @RequestParam String endTime,
			@ApiParam(name = "userId", value = "用户唯一编号", required = true) @PathVariable Long userId) {
		return new JsonResult<List<BondSentimentJsonDoc>>()
				.ok(bondSentimentService.findListByDateOrder(induId, type, startTime, endTime,userId));
	}

	@ApiOperation(value = "查询-所选期限内舆情分布")
	@RequestMapping(value = "/sentiments/distribution", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<BondSentimentJsonDoc>> findListByCodeOrder(
			@ApiParam(name = "startTime", value = "开始日期yyyy-MM-dd", required = true) @RequestParam String startTime,
			@ApiParam(name = "endTime", value = "结束日期yyyy-MM-dd", required = true) @RequestParam String endTime,
			@ApiParam(name = "comUniCode", value = "发行人", required = true) @RequestParam String comUniCode) {
		return new JsonResult<List<BondSentimentJsonDoc>>()
				.ok(bondSentimentService.findListByDate2(startTime, endTime, comUniCode));
	}

	@ApiOperation(value = "查询-最近五周舆情数据")
	@RequestMapping(value = "/sentiments/newestSentiments", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<BondSentimentJsonDoc>> findListByCodeOrder(
			@ApiParam(name = "comUniCode", value = "发行人", required = true) @RequestParam String comUniCode) {
		return new JsonResult<List<BondSentimentJsonDoc>>().ok(bondSentimentService.findListByDate(comUniCode));
	}

	@ApiOperation(value = "查询-舆情详细信息")
	@RequestMapping(value = "/sentiments/oneSentiments", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<BondSentimentDoc> oneSentiments(
			@ApiParam(name = "index", value = "舆情自增下标", required = true) @RequestParam Long index,
			@ApiParam(name = "important", value = "1:重点新闻,0:普通(默认)", required = false) @RequestParam(defaultValue = "0") Integer important) {
		return new JsonResult<BondSentimentDoc>().ok(bondSentimentService.oneSentiments(index, important));
	}

	@ApiOperation(value = "查询-发行人当天的舆情总数")
	@RequestMapping(value = "/sentiments/todaySentimentsOne", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<BondSentimentTopDateDoc> todaySentimentsOne(
			@ApiParam(name = "comUniCode", value = "发行人", required = true) @RequestParam String comUniCode) {
		return new JsonResult<BondSentimentTopDateDoc>().ok(bondSentimentService.todaySentimentsOne(comUniCode));
	}

}
