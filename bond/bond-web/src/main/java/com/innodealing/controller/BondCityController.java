package com.innodealing.controller;


import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.bond.service.BondCityService;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.dm.bond.BondArea;
import com.innodealing.model.dm.bond.BondAreaData;
import com.innodealing.model.dm.bond.BondCity;
import com.innodealing.model.dm.bond.BondCitySort;
import com.innodealing.model.dm.bond.BondCustomArea;
import com.innodealing.model.mongo.dm.BondDetailDoc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "城投债财务信息接口")
@RestController
@RequestMapping("api/bondCity/")
public class BondCityController extends BaseController {
	
	@Autowired
	BondCityService bondCityService;
	
	@ApiOperation(value="查询-城投债财务信息")
    @RequestMapping(value = "/viewList", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<BondAreaData>> viewList(
    		@ApiParam(name = "areaUniCode", value = "区域编号", required = true) @RequestParam String areaUniCode,
    		@ApiParam(name = "type", value = "类型", required = false) @RequestParam(defaultValue="1") Integer type
			) {
    	return new JsonResult<List<BondAreaData>>().ok(bondCityService.viewAreaList(areaUniCode,type));
    }
	
    public JsonResult<List<BondCity>> list(
    		@ApiParam(name = "areaUniCode", value = "区域编号", required = true) @RequestParam String areaUniCode,
    		@ApiParam(name = "notNull", value = "非空条件", required = false) @RequestParam(required=false) String notNull,
    		@ApiParam(name = "columnList", value = "列名集合", required = true) @RequestParam String[] columnList,
    		@ApiParam(name = "page", value = "页数", required = false) @RequestParam(defaultValue="1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue="6") Integer limit,
			@ApiParam(name = "sort", value = "排序规则", required = false) @RequestParam(defaultValue="bondYear:desc") String sort,
			@ApiParam(name = "group", value = "分组规则", required = false) @RequestParam(required=false) String group,
			@ApiParam(name = "type", value = "类型1,地级市2,省", required = true) @RequestParam Integer type
			) {
    	return new JsonResult<List<BondCity>>().ok(bondCityService.list(areaUniCode,notNull,columnList,page,limit,sort,group,type));
    }
	
	@ApiOperation(value="查询-城投债区域经济数据")
    @RequestMapping(value = "/details", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<BondCity>> details(
    		@ApiParam(name = "areaUniCode", value = "区域编号", required = true) @RequestParam String areaUniCode
			) {
    	return new JsonResult<List<BondCity>>().ok(bondCityService.details(areaUniCode));
    }
	
	@ApiOperation(value="查询-城投债项目信息")
    @RequestMapping(value = "/projectDetail", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<Map<String,Object>> projectDetail(
    		@ApiParam(name = "bondUniCode", value = "债劵编号", required = true) @RequestParam String bondUniCode
			) {
    	return new JsonResult<Map<String,Object>>().ok(bondCityService.projectDetail(bondUniCode));
    }
	
	@ApiOperation(value="查询-城投债区域经济对比")
    @RequestMapping(value = "/compare", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<BondCitySort>> compare(
    		@ApiParam(name = "areaUniCode", value = "区域编号,逗号分隔", required = true) @RequestParam String areaUniCode,
    		@ApiParam(name = "bondYear", value = "年度", required = true) @RequestParam Integer bondYear,
    		@ApiParam(name = "bondQuarter", value = "季度", required = false) @RequestParam(required=false) Integer bondQuarter,
    		@ApiParam(name = "bondMonth",value="月度",required = false) @RequestParam(required = false) Integer bondMonth,
    		@ApiParam(name = "dataType",value="数据类型,年度，季度，月度",required=true) @RequestParam(required=true) String dataType,
    		@ApiParam(name = "statisticsType",value="数据来源:公报，年鉴",required=true) @RequestParam(required=true) String statisticsType,
    		@ApiParam(name = "type", value = "类型:1.省份数据排名  2.城市数据排名 3.区/县数据排名  4.自选区域对比", required = false)  @RequestParam(defaultValue="1") Integer type,
			@ApiParam(name = "sortType" ,value = "类型:DESC 降序,ASC 升序",required = false ) @RequestParam(defaultValue ="ASC") String sortType,
			@ApiParam(name = "sortColumn",value="排序字段" ,required = false) @RequestParam(required=false) String sortColumn
    		) {
    	return new JsonResult<List<BondCitySort>>().ok(bondCityService.compares(areaUniCode,bondYear,bondQuarter,bondMonth,statisticsType,dataType,type,sortType,sortColumn));
    }
	
	@ApiOperation(value="添加-自定义区域数据")
    @RequestMapping(value = "/{userId}/customArea/add", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> addCustomArea(@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable BigInteger userId,
    		@ApiParam(name = "bondYear", value = "年份", required = false) @RequestParam(required=false) BigInteger bondYear,
    		@ApiParam(name = "bondQuarter", value = "季度", required = false) @RequestParam(required=false) BigInteger bondQuarter,
    		@ApiParam(name = "areaUniCode", value = "区域编号,逗号分隔", required = true) @RequestParam String areaUniCode
    		){
		return new JsonResult<String>().ok(bondCityService.addCustomArea(userId, bondYear, bondQuarter, areaUniCode));
	}
	
	@ApiOperation(value="查询-当前用户自定义区域数据")
    @RequestMapping(value = "/{userId}/customArea/find", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<BondCustomArea> findCustomArea(@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable BigInteger userId
    		){
		return new JsonResult<BondCustomArea>().ok(bondCityService.findCustomArea(userId));
	}
	
	@ApiOperation(value="查询-地区信息")
    @RequestMapping(value = "/bondArea", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<BondArea>> bondArea(
    		@ApiParam(name = "type", value = "1,省跟市2,所有省3,省下面的市", required = true) @RequestParam Integer type,
    		@ApiParam(name = "subUniCode", value = "省编号", required = false) @RequestParam(required=false) String subUniCode
			) {
    	return new JsonResult<List<BondArea>>().ok(bondCityService.bondArea(type,subUniCode));
    }
	
	@ApiOperation(value="查询-是否是城投债")
    @RequestMapping(value = "/isBondCity", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<BondDetailDoc> isBondCity(
    		@ApiParam(name = "uniCode", value = "债劵编号", required = true) @RequestParam Long uniCode,
    		@ApiParam(name = "type", value = "1,债劵2,发行人", required = true) @RequestParam Integer type
			) {
	    return new JsonResult<BondDetailDoc>().ok(bondCityService.isBondCity(uniCode,type));
    }
	
	@ApiOperation(value="查询-区域经济区域信息")
    @RequestMapping(value = "/isAreaBondCity", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<BondDetailDoc> isAreaBondCity(
            @ApiParam(name = "uniCode", value = "债劵编号", required = true) @RequestParam Long uniCode,
            @ApiParam(name = "type", value = "1,债劵2,发行人", required = true) @RequestParam Integer type
            ) {
        return new JsonResult<BondDetailDoc>().ok(bondCityService.isAreaBondCity(uniCode,type));
    }
	
}
