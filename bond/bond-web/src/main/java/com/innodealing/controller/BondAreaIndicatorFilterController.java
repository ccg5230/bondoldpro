package com.innodealing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Objects;
import com.innodealing.bond.param.AreaEconomiesIndicatorFilter;
import com.innodealing.bond.param.RaddrFilter;
import com.innodealing.bond.param.area.IndicatorAreaInstrucationsFilter;
import com.innodealing.bond.param.area.RadarIndicator;
import com.innodealing.bond.service.AreaEconomiesIndicatorService;
import com.innodealing.bond.service.area.AreaIndicatorService;
import com.innodealing.bond.service.area.IndicatorAreaService;
import com.innodealing.bond.service.user.UserAreaIndicatorFilterService;
import com.innodealing.bond.vo.area.AreaIndicatorKChartVo;
import com.innodealing.bond.vo.area.AreaIndicatorVo;
import com.innodealing.bond.vo.area.AreaIssuerSortItemVo;
import com.innodealing.bond.vo.area.IndicatorAreaIndicatorVo;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.mongo.dm.bond.area.AreaIndicatorFilterDoc;
import com.innodealing.model.mongo.dm.bond.area.AreaIndicatorItem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@Api(value = "区域经济分析接口", description = "区域经济分析接口")
@RestController
@RequestMapping("api/bond/area")
public class BondAreaIndicatorFilterController {
	
	private @Autowired IndicatorAreaService indicatorAreaService;
	
	private @Autowired UserAreaIndicatorFilterService userAreaIndicatorFilterService;
	
	private @Autowired AreaEconomiesIndicatorService areaEconomiesIndicatorService ;
	private @Autowired AreaIndicatorService areaIndicatorService;
	
	@ApiOperation(value = "区域经济分析-区域经济列表[zzl]")
	@RequestMapping(value = "/indicator/area", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<List<AreaIndicatorVo>> list(
			@ApiParam(name = "userid",value = "当前用户id") @RequestHeader Long userid,
			@RequestBody AreaEconomiesIndicatorFilter filter){				
		List<AreaIndicatorVo> result = areaEconomiesIndicatorService.findAreaEconomiesIndicators(userid,filter);
		
		return new JsonResult<List<AreaIndicatorVo>>().ok(result);
	}
	
	@ApiOperation(value="区域经济分析-查询雷达图")
	@RequestMapping(value="/indicator/radar",method = RequestMethod.POST, produces = "application/json")
	public JsonResult<List<RadarIndicator>> list(
			@ApiParam(name = "userid",value = "当前用户id") @RequestHeader Long userid,
			@RequestBody RaddrFilter filter,
			@ApiParam(name = "codes[]", value = "所有需要查询的指标") @RequestParam(value = "codes[]", required = true) String[] codes){
		List<RadarIndicator> result = areaEconomiesIndicatorService.findRadar(filter,codes);
		
		return new JsonResult<List<RadarIndicator>>().ok(result);
	}

			
	@ApiOperation(value = "区域经济分析-filter[]")
	@RequestMapping(value = "/indicator/area/filter", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<AreaIndicatorItem>> filter(
			@ApiParam(name = "userid",value = "当前用户id") @RequestHeader Long userid,
			@ApiParam(name = "isDefault",value = "是否默认筛选条件") @RequestParam(defaultValue = "false") boolean isDefault){
		return new JsonResult<List<AreaIndicatorItem>>().ok(userAreaIndicatorFilterService.findFilter(userid, isDefault));
	}
	
	@ApiOperation(value = "区域经济分析-用户保存filter[]")
	@RequestMapping(value = "/indicator/filter", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> list(
			@ApiParam(name = "userid",value = "当前用户id") @RequestHeader Long userid,
			@RequestBody IndicatorAreaInstrucationsFilter filter){
		boolean result = areaIndicatorService.findIssuerIndicators(userid, filter);
		return new JsonResult<String>().ok("success");
	}
	
	@ApiOperation(value = "区域经济分析-某指标K线图")
	@RequestMapping(value = "{areaUniCode}/indicator/{field}/area{dataType}/filter/{statisticsType}", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<AreaIndicatorKChartVo>> kchart(
			@ApiParam(name = "areaUniCode", value = "主体所在区域code") @PathVariable("areaUniCode") Long areaUniCode, 
			@ApiParam(name = "userid",value = "当前用户id") @RequestHeader Long userid, 
			@ApiParam(name = "field", value = "指标field") @PathVariable("field") String field,
			@ApiParam(name = "dataType", value = "年度 ,月度 ,季度") @RequestParam(required = false, defaultValue="季度")  String dataType,
			@ApiParam(name = "statisticsType", value = "公报,年鉴") @RequestParam(required = false, defaultValue="公报")  String statisticsType){
		List<AreaIndicatorKChartVo>  list  = null;
		list = indicatorAreaService.findkchart(areaUniCode, field, statisticsType, dataType, userid);
		return new JsonResult<List<AreaIndicatorKChartVo>>().ok(list);
	}
	@ApiOperation(value = "区域经济分析-区域排名Top10")
	@RequestMapping(value = "{areaUniCode}/indicator/{field}/area{dataType}/filter/{statisticsType}/sortTop10", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<AreaIssuerSortItemVo>> sortTop10(
			@ApiParam(name = "areaUniCode", value = "主体所在区域code") @PathVariable("areaUniCode") Long areaUniCode, 
			@ApiParam(name = "userid",value = "当前用户id") @RequestHeader Long userid, 
			@ApiParam(name = "field", value = "指标field") @PathVariable("field") String field,
			@ApiParam(name = "dataType", value = "年度 ,月度 ,季度") @RequestParam(required = false, defaultValue="季度")  String dataType,
			@ApiParam(name = "statisticsType", value = "公报,年鉴") @RequestParam(required = false, defaultValue="公报")  String statisticsType){
		return new JsonResult<List<AreaIssuerSortItemVo>>().ok(indicatorAreaService.sortTop10(areaUniCode, field, statisticsType, dataType, userid));
	}
	
	@ApiOperation(value = "区域经济分析-区域排名near5")
	@RequestMapping(value = "{areaUniCode}/indicator/{field}/area{dataType}/filter/{statisticsType}/sort/near", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<AreaIssuerSortItemVo>> sortnear5(
			@ApiParam(name = "areaUniCode", value = "主体所在区域code") @PathVariable("areaUniCode") Long areaUniCode, 
			@ApiParam(name = "userid",value = "当前用户id") @RequestHeader Long userid, 
			@ApiParam(name = "field", value = "指标field") @PathVariable("field") String field,
			@ApiParam(name = "dataType", value = "年度 ,月度 ,季度") @RequestParam(required = false, defaultValue="季度")  String dataType,
			@ApiParam(name = "statisticsType", value = "公报,年鉴") @RequestParam(required = false, defaultValue="公报")  String statisticsType){
		return new JsonResult<List<AreaIssuerSortItemVo>>().ok(indicatorAreaService.sortNear5(areaUniCode, field, statisticsType, dataType, userid));
	}
	


}
