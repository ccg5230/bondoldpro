package com.innodealing.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.bond.service.BondYieldCurvesService;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.mongo.dm.BondPubPar;
import com.innodealing.model.mongo.dm.BondYieldCurve;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author lihao
 * @date 2016年9月11日
 * @ClassName BondYieldCurvesController
 * @Description: TODO
 */
@Api(description="行情分析-收益率曲线")
@RestController
@RequestMapping("api/bond")
public class BondYieldCurvesController {
	
	private @Autowired BondYieldCurvesService bondYieldCurvesService;

	@ApiOperation(value="查询收益率曲线债券名称")
	@RequestMapping(value="/yieldCurves",method=RequestMethod.GET,produces="application/json")
	public JsonResult<List<BondPubPar>> findYieldCurvesBondName(){
		return new JsonResult<List<BondPubPar>>().ok(bondYieldCurvesService.findYieldCurvesBondName());
	}
	
	@ApiOperation(value="根据收益率债券名称获取对应的债券曲线,曲线1,2(参数值:1~16,没有7)")
	@RequestMapping(value="/yieldCurves/bondName/{bondNameOneId}/{bondNameTwoId}",method=RequestMethod.GET,produces="application/json")
	public JsonResult<Map<String,Object>> findYieldCurves(
			@ApiParam(name="bondNameOneId",value="曲线一债券id",required=false) @PathVariable Integer bondNameOneId,
			@ApiParam(name="bondNameTwoId",value="曲线二债券id",required=false) @PathVariable Integer bondNameTwoId
			){
		return new JsonResult<Map<String,Object>>().ok(bondYieldCurvesService.findYieldCurves(bondNameOneId, bondNameTwoId));
		
	}
	
	@ApiOperation(value="根据收益率债券名称和时间获取对应的债券曲线,曲线1,2(参数值:1~16,没有7),曲线日期(参数规则:如 3M=threem,1Y=oney,对应数字英文字母加m或y)")
	@RequestMapping(value="/yieldCurves/bondNameAndDate/{bondNameOneId}/{bondNameTwoId}/{bondDate}",method=RequestMethod.GET,produces="application/json")
	public JsonResult<Map<String,Object>> findYieldCurvesCompare(
			@ApiParam(name="bondNameOneId",value="曲线一债券id",required=false) @PathVariable Integer bondNameOneId,
			@ApiParam(name="bondNameTwoId",value="曲线二债券id",required=false) @PathVariable Integer bondNameTwoId,
			@ApiParam(name="bondDate",value="曲线日期",required=false) @PathVariable String bondDate
			){
		return new JsonResult<Map<String,Object>>().ok(bondYieldCurvesService.findYieldCurvesCompare(bondNameOneId, bondNameTwoId, bondDate));
	}
}
