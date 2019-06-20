package com.innodealing.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.adapter.BondInstitutionInduAdapter;
import com.innodealing.bond.param.BondComparisonReq;
import com.innodealing.bond.param.ComparisonSinglebondParam;
import com.innodealing.bond.service.BondComparisonService;
import com.innodealing.bond.service.BondDetailService;
import com.innodealing.bond.service.finance.BondFinanceInfoService;
import com.innodealing.bond.vo.analyse.BondIssIndicatorGroupVo;
import com.innodealing.bond.vo.quote.BondSingleComparisonVo;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.mongo.dm.BondComparisonDoc;
import com.innodealing.model.mongo.dm.BondDetailDoc;
import com.innodealing.util.SafeUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "对比接口")
@RestController
@RequestMapping("api/bond/")
public class BondComparisonController extends BaseController {

	@Autowired
	BondComparisonService comparisonService;

	@Autowired
	BondDetailService bondDetailService;
	
	@Autowired
	BondFinanceInfoService bondFinanceInfoService;

	@Autowired
	BondInstitutionInduAdapter induAdapter;
	   
	@ApiOperation(value = "添加对比")
	@RequestMapping(value = "/users/{userId}/comparisons", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<List<Long>> addComparison(
			@ApiParam(name = "userId", value = "用户唯一编号", required = true) @PathVariable Long userId,
			@RequestBody BondComparisonReq req) {

		return new JsonResult<List<Long>>().ok(comparisonService.addComparisons(userId, req.getBondId()));
	}

	@ApiOperation(value = "移出对比")
	@RequestMapping(value = "/users/{userId}/comparisons/{bondId}", method = RequestMethod.DELETE, produces = "application/json")
	public JsonResult<Long> removeComparison(
			@ApiParam(name = "userId", value = "用户唯一编号", required = true) @PathVariable Long userId,
			@ApiParam(name = "bondId", value = "债券唯一编号", required = true) @PathVariable Long bondId) {

		return new JsonResult<Long>().ok(comparisonService.removeComparison(userId, bondId));
	}

	@ApiOperation(value = "获取对比债券明细")
	@RequestMapping(value = "/users/{userId}/comparisons/bonds", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Page<BondDetailDoc>> findComparisonBonds(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Long userId,
			@ApiParam(name = "page", value = "页数", required = false) @RequestParam(defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue = "20") Integer limit,
			@ApiParam(name = "sort", value = "排序规则", required = false) @RequestParam(defaultValue = "updateTime:desc") String sort) {
		// 找关注组下面的所有债券
		List<BondComparisonDoc> comparisons = comparisonService.findComparisonByUserId(userId);

		// 保存债券号和关注号的关系
		List<Long> bondUniCodes = new ArrayList<Long>();
		comparisons.forEach(comparison -> {
			bondUniCodes.add(comparison.getBondId());
		});

		List<BondDetailDoc> docs= (List<BondDetailDoc>) 
		        induAdapter.conv(bondDetailService.findBondDetailByBondUniCodes(bondUniCodes, page-1, limit, sort), userId);
		
		// 查找bonds
		return new  JsonResult<Page<BondDetailDoc>>().ok(
				new PageImpl<BondDetailDoc>(
				        docs, 
						new PageRequest(page , limit-1), 
						bondUniCodes.size())
				);
	}

	@ApiOperation(value = "获取单券对比数据")
	@RequestMapping(value = "/users/{userId}/comparisons/singlebond", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<List<BondSingleComparisonVo>> findComparisonSinglebond(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Long userId,
			@ApiParam(name = "params", value = "BondId数据", required = true) @RequestBody ComparisonSinglebondParam params) {

		return new JsonResult<List<BondSingleComparisonVo>>()
				.ok(comparisonService.findComparisonSinglebond(userId, params));
	}
	
	@ApiOperation(value = "获取单券对比数据横轴数据日期")
	@RequestMapping(value = "/users/bonds/comparisons/singlebond/dates", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<String>> findComparisonDates() {
		return new JsonResult<List<String>>().ok(comparisonService.findComparisonDates());
	}
	
	
	@ApiOperation(value = "单击财务指标比对-[zzl]")
    @RequestMapping(value = "/users/bonds/indicators", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<List<BondIssIndicatorGroupVo>> bondCompare(
            @ApiParam(name = "bondUniCods", value = "债券id集合‘,’隔开", required = true) @RequestParam String bondUniCods) {
	    Set<Long> bondCodsA = new HashSet<>();
	   
	    for (String string :  bondUniCods.split(",")) {
	        bondCodsA.add(SafeUtils.getLong(string));
        }
        return new JsonResult<List<BondIssIndicatorGroupVo>>()
                .ok(bondFinanceInfoService.bondCompare(bondCodsA));
    }

}
