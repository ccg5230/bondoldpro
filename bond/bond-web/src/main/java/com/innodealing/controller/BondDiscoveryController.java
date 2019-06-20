package com.innodealing.controller;

import com.innodealing.adapter.BondInstitutionInduAdapter;
import com.innodealing.bond.param.BondAbnormalPriceFilterReq;
import com.innodealing.bond.param.BondAbnormalPriceHistoryReq;
import com.innodealing.bond.param.BondTodayDetailReq;
import com.innodealing.bond.param.BondTodayFilterReq;
import com.innodealing.bond.service.BondDiscoveryService;
import com.innodealing.domain.JsonResult;
import com.innodealing.domain.websocket.SocketDiscoveryUserMessageDTO;
import com.innodealing.model.dm.bond.BondAbnormalPriceHistoryVO;
import com.innodealing.model.dm.bond.BondTodayDMAggr;
import com.innodealing.model.dm.bond.BondTodayExtAggr;
import com.innodealing.model.mongo.dm.BondDiscoveryTodayDealDetailDoc;
import com.innodealing.model.mongo.dm.BondDiscoveryTodayQuoteDetailDoc;
import com.innodealing.model.mongo.dm.BondRiskDoc;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "债劵->发现")
@RestController
@RequestMapping("api/bond/discovery/")
public class BondDiscoveryController extends BaseController {

	@Autowired
	private BondDiscoveryService bondDiscoveryService;

	@Autowired
	private BondInstitutionInduAdapter induAdapter;

	@ApiOperation(value = "今日成交-->按DM量化评分分类")
	@RequestMapping(value = "/today/deal/dmRating", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<BondTodayDMAggr> getBondTodayDealDMRating(@RequestBody BondTodayFilterReq req) {
		return new JsonResult<BondTodayDMAggr>().ok(bondDiscoveryService.getBondTodayDealDMRating(req));
	}

	@ApiOperation(value = "今日成交-->按外部评级分类")
	@RequestMapping(value = "/today/deal/extRating", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<BondTodayExtAggr> getBondTodayDealExtRating(@RequestBody BondTodayFilterReq req) {
		return new JsonResult<BondTodayExtAggr>().ok(bondDiscoveryService.getBondTodayDealExtRating(req));
	}

	@ApiOperation(value = "今日成交-->单元格详情（记录点击时间，返回包含的详情列表）")
	@RequestMapping(value = "/today/deal/cell/detail", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Page<BondDiscoveryTodayDealDetailDoc>> getBondTodayDealDetailList(
			@RequestBody BondTodayDetailReq req,
			@ApiParam(name = "page", value = "页数") @RequestParam(defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量") @RequestParam(defaultValue = "20") Integer limit,
			@ApiParam(name = "sort", value = "排序规则") @RequestParam(defaultValue = "createTime:desc") String sort) {
		return new JsonResult<Page<BondDiscoveryTodayDealDetailDoc>>()
				.ok(induAdapter.conv(bondDiscoveryService.getBondTodayDealDetailDocList(req, page - 1, limit, sort), req.getUserId()));
	}

	@ApiOperation(value = "今日成交-->显示所有详情列表")
	@RequestMapping(value = "/today/deal/detail/list", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Page<BondDiscoveryTodayDealDetailDoc>> getAllBondTodayDealDetailList(
			@RequestBody BondTodayDetailReq req,
			@ApiParam(name = "page", value = "页数") @RequestParam(defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量") @RequestParam(defaultValue = "20") Integer limit,
			@ApiParam(name = "sort", value = "排序规则") @RequestParam(defaultValue = "createTime:desc") String sort) {
		return new JsonResult<Page<BondDiscoveryTodayDealDetailDoc>>()
				.ok(induAdapter.conv(bondDiscoveryService.getAllBondTodayDealDetailDocList(req, page - 1, limit, sort), req.getUserId()));
	}

	@ApiOperation(value = "今日报价-->按DM量化评分分类")
	@RequestMapping(value = "/today/quote/dmRating", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<BondTodayDMAggr> getBondTodayQuoteDMRating(@RequestBody BondTodayFilterReq req) {
		return new JsonResult<BondTodayDMAggr>().ok(bondDiscoveryService.getBondTodayQuoteDMRating(req));
	}

	@ApiOperation(value = "今日报价-->按外部评级分类")
	@RequestMapping(value = "/today/quote/extRating", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<BondTodayExtAggr> getBondTodayQuoteExtRating(@RequestBody BondTodayFilterReq req) {
		return new JsonResult<BondTodayExtAggr>().ok(bondDiscoveryService.getBondTodayQuoteExtRating(req));
	}

	@ApiOperation(value = "今日报价-->单元格详情（记录点击时间，返回包含的详情列表）")
	@RequestMapping(value = "/today/quote/cell/detail", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Page<BondDiscoveryTodayQuoteDetailDoc>> getBondTodayQuoteDetailList(
			@RequestBody BondTodayDetailReq req,
			@ApiParam(name = "page", value = "页数") @RequestParam(defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量") @RequestParam(defaultValue = "20") Integer limit,
			@ApiParam(name = "sort", value = "排序规则") @RequestParam(defaultValue = "createTime:desc") String sort) {
		return new JsonResult<Page<BondDiscoveryTodayQuoteDetailDoc>>()
				.ok(induAdapter.conv(bondDiscoveryService.getBondTodayQuoteDetailDocList(req, page - 1, limit, sort), req.getUserId()));
	}

	@ApiOperation(value = "今日报价-->显示所有详情列表")
	@RequestMapping(value = "/today/quote/detail/list", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Page<BondDiscoveryTodayQuoteDetailDoc>> getAllBondTodayQuoteDetailList(
			@RequestBody BondTodayDetailReq req,
			@ApiParam(name = "page", value = "页数") @RequestParam(defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量") @RequestParam(defaultValue = "20") Integer limit,
			@ApiParam(name = "sort", value = "排序规则") @RequestParam(defaultValue = "createTime:desc") String sort) {
		return new JsonResult<Page<BondDiscoveryTodayQuoteDetailDoc>>()
				.ok(bondDiscoveryService.getAllBondTodayQuoteDetailDocList(req, page - 1, limit, sort));
	}

	@ApiOperation(value = "今日报价/成交，刷新用户页面时间")
	@RequestMapping(value = "/today/user/heartbeat", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Boolean> getAbnormalPriceHistoryList(@RequestBody SocketDiscoveryUserMessageDTO socketDTO) {
		return new JsonResult<Boolean>().ok(bondDiscoveryService.discoveryTodayHeartBeat(socketDTO));
	}

	@ApiOperation(value = "异常价格")
	@RequestMapping(value = "/abnormalPrice/list", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Page<Object>> getAbnormalPriceList(@RequestBody BondAbnormalPriceFilterReq req,
		 @ApiParam(name = "page", value = "页数") @RequestParam(defaultValue = "1") Integer page,
		 @ApiParam(name = "limit", value = "每页显示数量") @RequestParam(defaultValue = "20") Integer limit,
		 @ApiParam(name = "sort", value = "排序规则") @RequestParam(defaultValue = "pubDate:desc") String sort) {
		return new JsonResult<Page<Object>>().ok(bondDiscoveryService.getAbnormalPriceList(req, page - 1, limit, sort));
	}

	@ApiOperation(value = "异常价格,单支债券历史数据")
	@RequestMapping(value = "/abnormalPrice/history/list", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<BondAbnormalPriceHistoryVO> getAbnormalPriceHistoryList(@RequestBody BondAbnormalPriceHistoryReq req) {
		return new JsonResult<BondAbnormalPriceHistoryVO>().ok(bondDiscoveryService.getAbnormalPriceHistoryList(req));
	}

	@ApiOperation(value = "信用对比-->主体对比")
	@RequestMapping(value = "/credit/riskList", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<BondRiskDoc>> riskList(
			@ApiParam(name = "year", value = "年份") @RequestParam(defaultValue = "2016") Integer year) {
		year = 2017;
		return new JsonResult<List<BondRiskDoc>>().ok(bondDiscoveryService.getRiskList(year));
	}

	@ApiOperation(value = "信用对比-->主体对比【含有行业信息作为查询条件】")
	@RequestMapping(value = "/credit/{userId}/riskList", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<BondRiskDoc>> riskList(
			@ApiParam(name = "userId", value = "用户ID", required = true) @PathVariable Long userId,
			@ApiParam(name = "year", value = "年份") @RequestParam(defaultValue = "2017") Integer year,
			@ApiParam(name = "induIds", value = "行业ID") @RequestParam(defaultValue="") Long[] induIds) {
		return new JsonResult<List<BondRiskDoc>>().ok(bondDiscoveryService.getRiskList(userId, year, induIds));
	}
}
