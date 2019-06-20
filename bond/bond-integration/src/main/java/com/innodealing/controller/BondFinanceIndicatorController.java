package com.innodealing.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.bond.service.finance.IndicatorStateService;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.BuildResult;
import com.innodealing.service.InduService;
import com.innodealing.service.IssAnalysisService;
import com.innodealing.service.IssIndicatorFieldGroupService;
import com.innodealing.util.SafeUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "构建债券主体财务指标")
@RestController
@RequestMapping("api/bond/tasks/")
public class BondFinanceIndicatorController extends BaseController{
    @Autowired  private IssAnalysisService issAnalysisService;

	@Autowired private IndicatorStateService indicatorStateService;
    
    @Autowired private IssIndicatorFieldGroupService issIndicatorFieldGroupService;
    
    @Autowired private InduService induService;
    
    @ApiOperation(value = "构建债券主体财务指标-[zzl]")
    @RequestMapping(value = "/finance/indicators", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<List<BuildResult>> build(@ApiParam(name = "initAll", value = "是否初始化全部的财务数据", required = false) @RequestParam boolean initAll) throws Exception {
        return new JsonResult<List<BuildResult>>().ok(issAnalysisService.build(initAll));
    }
    
    
    @ApiOperation(value = "构单个债券主体财务指标和专项指标-[zzl]")
    @RequestMapping(value = "/finance/indicator", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<List<BuildResult>> buildOne(
    		@ApiParam(name = "compId", value = "安硕主体id", required = false) @RequestParam Long compId) throws Exception {
        return new JsonResult<List<BuildResult>>().ok(issAnalysisService.buildOne(compId));
    }
    
    @ApiOperation(value = "构建主体财务指标分类")
    @RequestMapping(value = "/finance/FieldGroupMapping", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> fieldGroupMapping() throws Exception {
        return new JsonResult<String>().ok(issAnalysisService.fieldGroupMapping());
    }
    
    @ApiOperation(value = "构建专项指标filter-[zzl]")
    @RequestMapping(value = "/finance/indicator/filter", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<Boolean> buildIndicatorSpecailFilter() throws Exception {
    	boolean result = issIndicatorFieldGroupService.buildIndicatorSpecailFilter();
        return new JsonResult<Boolean>().ok(result);
    }
    
    @ApiOperation(value = "构建专项指标[zzl]")
    @RequestMapping(value = "/finance/indicator/special", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<Boolean> buildAllIssIndicators() throws Exception {
    	boolean result = issAnalysisService.buildAllIssIndicators();
        return new JsonResult<Boolean>().ok(result);
    }
    
    @ApiOperation(value = "将最新的主体指标动态添加到备忘录-[zzl]")
    @RequestMapping(value = "{comUniCode}/memento/add", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> state(
    		@ApiParam(name = "comUniCode",value = "发行人id") @PathVariable("comUniCode") Long comUniCode,
    		@ApiParam(name="finDate") @RequestParam(required = false) @DateTimeFormat(pattern = SafeUtils.DATE_FORMAT) Date finDate,
    		@ApiParam(name="rankIndu",value = "是否立即计算行业排名") @RequestParam(required = false,defaultValue = "false") boolean rankIndu) throws Exception{
		indicatorStateService.saveAndSend(comUniCode, finDate, rankIndu);
        return new JsonResult<String>().ok("添加成功");
    }
    
    @ApiOperation(value = "将指定财报日期的主体指标动态添加到备忘录-[zzl]")
    @RequestMapping(value = "/memento/addMissing", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> stateMiss(
    		@ApiParam(name="finDate") @RequestParam(required = true) @DateTimeFormat(pattern = SafeUtils.DATE_FORMAT) Date finDate) throws Exception{
		
    	indicatorStateService.sendMissTakerMq(SafeUtils.convertDateToString(finDate, SafeUtils.DATE_FORMAT));
        return new JsonResult<String>().ok("添加成功");
    }
    
    @ApiOperation(value = "构建发行人违约概率-[zzl]")
    @RequestMapping(value = "/finance/issPd", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<List<BuildResult>> stateMiss() throws Exception{
		
        return new JsonResult<List<BuildResult>>().ok(induService.build());
    }
    
    
    
    
}


