package com.innodealing.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.adapter.BondIndicatorAdapter;
import com.innodealing.bond.param.finance.FinanceIndicatorRadarChartParm;
import com.innodealing.bond.param.finance.IndicatorSpecialInstructionsFilter;
import com.innodealing.bond.service.BondUserOperationService;
import com.innodealing.bond.service.finance.BondFinanceInfoService;
import com.innodealing.bond.service.finance.IndicatorAnalysisService;
import com.innodealing.bond.service.user.UserIndicatorFilterService;
import com.innodealing.bond.vo.analyse.BondFinanceInfoVo;
import com.innodealing.bond.vo.analyse.IssFinanceChangeKVo;
import com.innodealing.bond.vo.finance.FinanceIndicatorRadarChartVo;
import com.innodealing.bond.vo.finance.IndicatorSpecialIndicatorVo;
import com.innodealing.bond.vo.finance.IssNoFinanceInDateVo;
import com.innodealing.bond.vo.finance.IssuerSortVo;
import com.innodealing.consts.Constants;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.mongo.dm.bond.finance.IndicatorFieldGroup;
import com.innodealing.util.SafeUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 财务指标分析控制器
 * @author zhaozhenglai
 * @date 2017年2月14日下午6:57:56
 *Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
@Api(value = "财务指标分析接口", description = "财务指标分析接口")
@RestController
@RequestMapping("api/bond/finance")
public class BondIndicatorAnalysisController extends BaseController {
	@Autowired private IndicatorAnalysisService indicatorAnalysisService;
	
	@Autowired private BondUserOperationService userOperationService;
	
	@Autowired private BondFinanceInfoService bondFinanceInfoService;
	
	
	@Autowired private UserIndicatorFilterService userIndicatorFilterService;
	
	@ApiOperation(value = "财务指标分析-专项指标列表[zzl]")
	@RequestMapping(value = "/indicator/specials", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<List<IndicatorSpecialIndicatorVo>> list(
			@ApiParam(name = "userid",value = "当前用户id") @RequestHeader Long userid,
			@RequestBody IndicatorSpecialInstructionsFilter filter){
		List<IndicatorSpecialIndicatorVo> result = indicatorAnalysisService.findIssuerIndicators(userid, filter);
		result = BondIndicatorAdapter.doFormatterIndicator(result);
		result = BondIndicatorAdapter.doFormatterIndicatorUnit(result, filter.getUnit());
		return new JsonResult<List<IndicatorSpecialIndicatorVo>>().ok(result);
	}
	
	@ApiOperation(value = "财务指标分析-专项指标filter[zzl]")
	@RequestMapping(value = "{issuerId}/indicator/specials/filter", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<IndicatorFieldGroup>> filter(
			@ApiParam(name = "userid",value = "当前用户id") @RequestHeader Long userid, 
			@ApiParam(name = "issuerId",value = "发行人id") @PathVariable("issuerId") Long issuerId,
			@ApiParam(name = "isDefault",value = "是否默认筛选条件") @RequestParam(defaultValue = "false") boolean isDefault){
		return new JsonResult<List<IndicatorFieldGroup>>().ok(userIndicatorFilterService.findFilter(issuerId, userid, isDefault));
	}
	
	@ApiOperation(value = "财务指标分析-某指标K线图")
	@RequestMapping(value = "{issuerId}/indicator/specials/{field}/kchart", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<IssFinanceChangeKVo>> kchart(@ApiParam(name = "issuerId", value = "发行人id") @PathVariable("issuerId") Long issuerId, 
			@ApiParam(name = "userid",value = "当前用户id") @RequestHeader Long userid, 
			@ApiParam(name = "field", value = "指标field") @PathVariable("field") String field,
			@ApiParam(name = "isSpecial", value = "是否为专项指标1是，0不是") @RequestParam(required = false, defaultValue="1")  Integer isSpecial){
		List<IssFinanceChangeKVo>  list  = null;
		if(isSpecial == Constants.TRUE){
			list = indicatorAnalysisService.findKchart(issuerId, field, userid);
		}else{
			list = indicatorAnalysisService.findKchartForFinanceIndicator(issuerId, field, userid);
		}
		return new JsonResult<List<IssFinanceChangeKVo>>().ok(BondIndicatorAdapter.doFormatterIndicatorKchart(list));
	}
	
	@ApiOperation(value = "财务指标分析-某指标行业前10[zzl]")
	@RequestMapping(value = "{issuerId}/indicator/specials/{field}/top10", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<IssuerSortVo>> top10(@ApiParam(name = "issuerId", value = "发行人id") @PathVariable("issuerId") Long issuerId, 
			@ApiParam(name = "userid",value = "当前用户id") @RequestHeader Long userid, 
			@ApiParam(name = "field", value = "指标field") @PathVariable("field") String field,
			@ApiParam(name = "isInvestment", value = "是否去城投1是,0不是") @RequestParam(required = false, defaultValue="1") Integer isInvestment,
			@ApiParam(name = "isSpecial", value = "是否为专项指标1是，0不是") @RequestParam(required = false, defaultValue="1")  Integer isSpecial){
		return new JsonResult<List<IssuerSortVo>>().ok(indicatorAnalysisService.top10InIndu(issuerId, userid, field, isSpecial,isInvestment));
	}
	
	@ApiOperation(value = "财务指标分析-发行人某一指标排名和附近4个主体[zzl]")
	@RequestMapping(value = "{issuerId}/indicator/specials/{field}/near5", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<IssuerSortVo>> nearCurrentIssuer5(@ApiParam(name = "issuerId", value = "发行人id") @PathVariable("issuerId") Long issuerId, 
			@ApiParam(name = "userid",value = "当前用户id") @RequestHeader Long userid, 
			@ApiParam(name = "field", value = "指标field") @PathVariable("field") String field,
			@ApiParam(name = "isInvestment", value = "是否去城投1是,0不是") @RequestParam(required = false, defaultValue="1") Integer isInvestment,
			@ApiParam(name = "isSpecial", value = "是否为专项指标1是，0不是") @RequestParam(required = false, defaultValue="1")  Integer isSpecial){
		return new JsonResult<List<IssuerSortVo>>().ok(indicatorAnalysisService.nearCurrentIssuer5(issuerId, userid, field, isSpecial,isInvestment));
	}
	
	@ApiOperation(value = "财务指标分析-导出报表[zzl]")
	@RequestMapping(value = "/{issuerId}/indicator/reportExcel", method = RequestMethod.GET)
	public JsonResult<Boolean> report(HttpServletRequest request, HttpServletResponse response,
			/*@ApiParam(name = "userid",value = "当前用户id") @RequestHeader Long userid, */
			@ApiParam(name = "issuerId",value = "发行人id") @PathVariable("issuerId") Long issuerId,
			@ApiParam(name = "tabIndex",value = "tabindex。0专项指标、1资产负债表、2利润表、3现金流量表") @RequestParam Integer tabIndex, 
			@ApiParam(name = "startYear",value = "开始时间") @RequestParam(required = false) Integer startYear,
			@ApiParam(name = "endYear",value = "结束时间") @RequestParam(required = false) Integer endYear,
			@ApiParam(name = "timeHorizon",value = "时间范围({1:3Y,2:5Y,3:10Y})") @RequestParam(required = false) Integer timeHorizon,
			@ApiParam(name = "quarters",value = "季度选项1,2,3,4分别代表四个季度，多个用逗号隔开") @RequestParam String quarters,
			@ApiParam(name = "isSpecial", value = "是否为专项指标1是，0不是") @RequestParam(required = false, defaultValue="1")  Integer isSpecial){
			IndicatorSpecialInstructionsFilter filter = new IndicatorSpecialInstructionsFilter();
			filter.setIssId(issuerId);
			filter.setTimeHorizon(timeHorizon);
			filter.setStartYear(startYear);
			filter.setEndYear(endYear);
			filter.setTabIndex(tabIndex);
			filter.setIsSpecial(isSpecial);
			if(quarters != null){
				filter.setFinancialReportType(SafeUtils.getIntArray(quarters.split(",")));
			}
			boolean success = indicatorAnalysisService.report(request, response, filter);
			String msg = success ? "导出成功" :"抱歉该发行人在此期间无财务信息！";
			return new JsonResult<Boolean>(success ? "0" : "-1", msg, success);
			
	}
	
	@ApiOperation(value = "财务指标分析-导出报表(消息提示)[zzl]")
	@RequestMapping(value = "/{issuerId}/indicator/reportExcelMsg", method = RequestMethod.GET)
	public JsonResult<Boolean> reportMsg(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(name = "userid",value = "当前用户id") @RequestHeader Long userid,
			@ApiParam(name = "issuerId",value = "发行人id") @PathVariable("issuerId") Long issuerId,
			@ApiParam(name = "tabIndex",value = "tabindex。0专项指标、1资产负债表、2利润表、3现金流量表") @RequestParam Integer tabIndex, 
			@ApiParam(name = "startYear",value = "开始时间") @RequestParam(required = false) Integer startYear,
			@ApiParam(name = "endYear",value = "结束时间") @RequestParam(required = false) Integer endYear,
			@ApiParam(name = "timeHorizon",value = "时间范围({1:3Y,2:5Y,3:10Y})") @RequestParam(required = false) Integer timeHorizon,
			@ApiParam(name = "quarters",value = "季度选项1,2,3,4分别代表四个季度，多个用逗号隔开") @RequestParam String quarters,
			@ApiParam(name = "isSpecial", value = "是否为专项指标1是，0不是") @RequestParam(required = false, defaultValue="1")  Integer isSpecial){
			boolean result = false;
			String msg = "您没有权限导出该财务数据";
			
			//if (userOperationService.getFinaReportPerm(userid)) {
				IndicatorSpecialInstructionsFilter filter = new IndicatorSpecialInstructionsFilter();
				filter.setIssId(issuerId);
				filter.setTimeHorizon(timeHorizon);
				filter.setStartYear(startYear);
				filter.setEndYear(endYear);
				filter.setTabIndex(tabIndex);
				filter.setIsSpecial(isSpecial);
				
				if(quarters != null){
					filter.setFinancialReportType(SafeUtils.getIntArray(quarters.split(",")));
				}
				result = indicatorAnalysisService.reportMsg(request, response, filter);
				msg = result ? "导出成功" :"抱歉该发行人在此期间无财务信息！";
			//}
			
			return new JsonResult<Boolean>(result ? "0" : "-1", msg, result);
	}
	
	@ApiOperation(value = "财务指标分析-行业分布对比图)-[zzl]")
    @RequestMapping(value = "{issuerId}/indicator/specials/compare", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<List<BondFinanceInfoVo>> findBondFinanceInfoDoc(
    		@RequestBody IndicatorSpecialInstructionsFilter filter,
            @ApiParam(name = "issuerId", value = "公司id", required = true) @PathVariable Long issuerId,
            @ApiParam(name = "year",value = "年份") @RequestParam Integer year,
            @ApiParam(name = "quarter",value = "季度") @RequestParam Integer quarter,
            @ApiParam(name = "provinceIds[]", value = "省份id集合") @RequestParam(value = "provinceIds[]", required = false) Long[] provinceIds,
            @RequestHeader(name="userid" ,required = false) Long userid) {
        return new JsonResult<List<BondFinanceInfoVo>>().ok(indicatorAnalysisService.compareToIndu(issuerId, userid, year, quarter ,provinceIds == null? null :Arrays.asList(provinceIds), filter));
    }
	
	
	@ApiOperation(value = "财务指标分析-专项指标雷达图)-[zzl]")
    @RequestMapping(value = "/indicator/specials/radarChart", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<List<FinanceIndicatorRadarChartVo>> findBondFinanceInfoDoc(@RequestBody FinanceIndicatorRadarChartParm radarChartParm) {
        return new JsonResult<List<FinanceIndicatorRadarChartVo>>().ok(indicatorAnalysisService.indicatorRadarChart(radarChartParm));
    }
	
	@ApiOperation(value = "财务指标分析-专项指标雷达图(获取主体的财报时间))-[zzl]")
    @RequestMapping(value = "{issuerId}/indicator/specials/financeDate", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<String>> financeDate(
    		@ApiParam(name = "issuerId",value = "发行人id") @PathVariable("issuerId") Long issuerId) {
        return new JsonResult<List<String>>().ok(indicatorAnalysisService.getIndicatorFinDate(issuerId));
    }

	@ApiOperation(value = "财务指标分析-主体行业总体总数量-[zzl]")
    @RequestMapping(value = "{issuerId}/indu/issNum", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<Long> issNum(
    		@ApiParam(name = "issuerId",value = "发行人id") @PathVariable("issuerId") Long issuerId,
    		@RequestHeader(name="userid" ,required = false) Long userid,
    		@ApiParam(name = "isInvestment", value = "是否去城投1是,0不是") @RequestParam(required = false, defaultValue="1") Integer isInvestment,
    		@ApiParam(name="field" ,required = false) @RequestParam(required = false)String field) {
        return new JsonResult<Long>().ok(indicatorAnalysisService.findInduIssCount(issuerId, userid, field,isInvestment));
    }
	
	@ApiOperation(value = "规定时间未收集到财报-[zzl]")
    @RequestMapping(value = "/nofinance", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<IssNoFinanceInDateVo>> findNotFinanceInDate(
    		@ApiParam(name="date",value = "yyyy-05-01、yyyy-09-01、yyyy-11-01") @RequestParam(required = false) @DateTimeFormat(pattern = SafeUtils.DATE_FORMAT) Date date) {
		List<IssNoFinanceInDateVo>  list = bondFinanceInfoService.findNotFinanceInDate(date);
        return new JsonResult<List<IssNoFinanceInDateVo>>().ok(list);
	
    }
	
	
}
