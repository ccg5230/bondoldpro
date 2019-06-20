package com.innodealing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.adapter.BondInstitutionInduAdapter;
import com.innodealing.bond.service.BondAnalysisService;
import com.innodealing.bond.vo.finance.IssuerSortVo;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.mongo.dm.BondPdRankDoc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "债券分析数据接口")
@RestController
@RequestMapping("api/bond/")
public class BondAnalysisController extends BaseController{

	@Autowired
	BondAnalysisService analysisService;
	
	@Autowired
	BondInstitutionInduAdapter induAdapter;
	
	@ApiOperation(value="违约概率排行")
    @RequestMapping(value = "/analysis/pdRank/indus", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<Page<BondPdRankDoc>> findPdRankByInduId(
            @RequestHeader("userid") long userid,
    		@ApiParam(name = "page", value = "页数", required = false) @RequestParam(defaultValue="1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue="20") Integer limit,
			@ApiParam(name = "sort", value = "排序规则", required = false) @RequestParam(defaultValue="pdSortRRs:desc") String sort) {
    	return new JsonResult<Page<BondPdRankDoc>>().ok(
    	        induAdapter.conv(analysisService.findPdRank(null, page-1, limit, sort, userid), userid).createPage());
    }
	
	@ApiOperation(value="违约概率排行")
    @RequestMapping(value = "/analysis/pdRank/indus/{induIds}", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<Page<BondPdRankDoc>> findPdRankByInduId(
            @RequestHeader("userid") long userid,
    		@ApiParam(name = "induIds", value = "行业id, 支持复数, 通过\",\"号分隔的", required = false) @PathVariable String induIds,
    		@ApiParam(name = "page", value = "页数", required = false) @RequestParam(defaultValue="1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue="20") Integer limit,
			@ApiParam(name = "sort", value = "排序规则", required = false) @RequestParam(defaultValue="pdNum:desc") String sort) {
    	return new JsonResult<Page<BondPdRankDoc>>().ok(
    	        induAdapter.conv(analysisService.findPdRank(induIds, page-1, limit, sort, userid), userid).createPage());
    }
	

	
	@ApiOperation(value = "发行人主体分析-主体所在行业前10")
	@RequestMapping(value = "analysis/indu/issuers/{issuerId}/top10", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<IssuerSortVo>> findTop10InCurrIndu(
			@RequestHeader("userid") Long userid,
			@ApiParam(name = "issuerId", value = "发行人id") @PathVariable("issuerId") Long issuerId,
			@ApiParam(name = "year", value = "选定的年份", required = false) @RequestParam(required = false) Long year,
			@ApiParam(name = "quarter", value = "选定的季度", required = false) @RequestParam(required = false) Long quarter) {
		return new JsonResult<List<IssuerSortVo>>().ok(analysisService.getTop10InPubIndu(userid, issuerId, year, quarter));
	}
	
	@ApiOperation(value = "发行人主体分析-主体当前位置和附近4个其他主体")
	@RequestMapping(value = "analysis/indu/issuers/{issuerId}/near5", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<IssuerSortVo>> findNear5InCurrIndu(
			@RequestHeader("userid") Long userid,
			@ApiParam(name = "issuerId", value = "发行人id") @PathVariable("issuerId") Long issuerId,
			@ApiParam(name = "year", value = "选定的年份", required = false) @RequestParam(required = false) Long year,
			@ApiParam(name = "quarter", value = "选定的季度", required = false) @RequestParam(required = false) Long quarter){
		return new JsonResult<List<IssuerSortVo>>().ok(analysisService.getNear5InPubIndu(userid, issuerId, year, quarter));
	}
}


