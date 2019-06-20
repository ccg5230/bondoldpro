package com.innodealing.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.bond.service.BondTrendsService;
import com.innodealing.bond.vo.trends.TrendsBondSentiInfoVO;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.mongo.dm.BondTrendsImpRatingChangeDoc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author xiaochao
 * @time 2017年3月29日
 * @description:
 */
@Api(description = "债券动态接口(今日关注)")
@RestController
@RequestMapping("api/bond/")
public class BondTrendsController extends BaseController {

	@Autowired
	private BondTrendsService bondTrendsService;

	@ApiOperation(value = "今日动态列表[9527]")
	@RequestMapping(value = "/trends/focusontoday/list", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Page<TrendsBondSentiInfoVO>> findFocusOnTodayList(
			@ApiParam(name = "negaTypes[]", value = "只看负面动态[情感类别:0-空(默认);1-负面;2-中性;3-利好;]，默认全部")
			@RequestParam(value = "negaTypes[]", required = false) List<Integer> negaTypes,
			@ApiParam(name = "clsiTypes[]", value = "今日动态分类, 默认全部")
			@RequestParam(value = "clsiTypes[]", required = false) List<Integer> clsiTypes,
			@ApiParam(name = "startDate", value = "开始日期[yyyy-MM-dd]，默认7天前")
			@RequestParam(required = false) String startDate,
			@ApiParam(name = "endDate", value = "结束日期[yyyy-MM-dd]，默认今天")
			@RequestParam(required = false) String endDate,
			@ApiParam(name = "page", value = "页数")
			@RequestParam(defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量")
			@RequestParam(defaultValue = "20") Integer limit) {
		return new JsonResult<Page<TrendsBondSentiInfoVO>>()
				.ok(bondTrendsService.getFocusOnTodayList(negaTypes, clsiTypes, startDate, endDate, page - 1, limit));
	}

	@ApiOperation(value = "隐含评级变动列表[9527]")
	@RequestMapping(value = "/trends/impratingchange/list", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Page<BondTrendsImpRatingChangeDoc>> findImpRatingChangeList(
			@ApiParam(name = "pubDate", value = "发布日期[yyyy-MM-dd]") @RequestParam(required = false) String pubDate,
			@ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam Long userId,
			@ApiParam(name = "type", value = "隐含评级变动类型[0=全部;1=上调;2=下调]") @RequestParam(defaultValue = "0") int type,
			@ApiParam(name = "page", value = "页数") @RequestParam(defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量") @RequestParam(defaultValue = "20") Integer limit,
			@ApiParam(name = "sort", value = "排序规则") @RequestParam(defaultValue = "currRatingPar:asc") String sort) {
		return new JsonResult<Page<BondTrendsImpRatingChangeDoc>>()
				.ok(bondTrendsService.findImpRatingList(userId, pubDate, type, sort, page - 1, limit));
	}

	@ApiOperation(value = "获取上一个交易日的日期[9527]")
	@RequestMapping(value = "/trends/lasttradingday", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Date> getLastTradingDay() {
		return new JsonResult<Date>().ok(bondTrendsService.getLastTradingDay());
	}
}
