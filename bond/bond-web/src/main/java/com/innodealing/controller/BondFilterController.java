package com.innodealing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.adapter.BondInstitutionInduAdapter;
import com.innodealing.bond.service.BondDetailService;
import com.innodealing.bond.service.BondDmFilterService;
import com.innodealing.bond.service.BondInduService;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.mongo.dm.BondDmFilterDoc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "债券过滤方案接口")
@RestController
@RequestMapping("api/bond/")
public class BondFilterController extends BaseController {

	@Autowired
	BondDetailService bondDetailService;
	
	@Autowired
	BondDmFilterService filterService;
	
	@Autowired
	BondInduService induSerivce;
	
	@Autowired
	BondInstitutionInduAdapter induAdapter;
	   
	@ApiOperation(value="添加或者更新筛选方案")
    @RequestMapping(value = "/users/{userId}/filters", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<BondDmFilterDoc> addFilter(
    		@ApiParam(name = "userId", value = "用户id", required = true) @PathVariable Long userId,
    		@RequestBody BondDmFilterDoc newFilterParam) {
		newFilterParam.setUserId(userId);
		newFilterParam.setInduClass(induSerivce.findInduClassByUser(userId));
    	return new JsonResult<BondDmFilterDoc>().ok(filterService.saveFilter(userId, newFilterParam));
    }
	
	@ApiOperation(value="更新筛选方案")
    @RequestMapping(value = "/users/{userId}/filters/{filterId}", method = RequestMethod.PUT, produces = "application/json")
    public JsonResult<BondDmFilterDoc> updateFilter(
    		@ApiParam(name = "userId", value = "用户id", required = true) @PathVariable Long userId,
    		@ApiParam(name = "filterId", value = "过滤方案id", required = true) @PathVariable String filterId,
    		@RequestBody BondDmFilterDoc newFilterParam) {
		newFilterParam.setFilterId(filterId);
		newFilterParam.setUserId(userId);
    	return new JsonResult<BondDmFilterDoc>().ok(filterService.updateFilter(newFilterParam));
    }
	
	@ApiOperation(value="更新筛选方案名称")
    @RequestMapping(value = "/users/{userId}/filters/{filterId}/filterName/{filterName}", method = RequestMethod.PUT, produces = "application/json")
    public JsonResult<BondDmFilterDoc> updateFilter(
    		@ApiParam(name = "userId", value = "用户id", required = true) @PathVariable Long userId,
    		@ApiParam(name = "filterId", value = "过滤方案id", required = true) @PathVariable String filterId,
    		@ApiParam(name = "filterName", value = "过滤方案名", required = true) @PathVariable String filterName) {
    	return new JsonResult<BondDmFilterDoc>().ok(filterService.saveFilterName(userId, filterId, filterName));
    }
	
	@ApiOperation(value="取得筛选方案名称列表")
    @RequestMapping(value = "/users/{userId}/filters", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<BondDmFilterDoc>> getUserFilters(
    		@ApiParam(name = "userId", value = "用户id", required = true) @PathVariable Long userId) {
    	return new JsonResult<List<BondDmFilterDoc>>().ok(induAdapter.conv(filterService.findFilterNamesByUserId(userId),userId));
    }
	
	@ApiOperation(value="取得筛选方案")
    @RequestMapping(value = "/users/{userId}/filters/{filterId}", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<BondDmFilterDoc> findByFilter(
    		@ApiParam(name = "userId", value = "用户id", required = true) @PathVariable Long userId,
    		@ApiParam(name = "filterId", value = "过滤方案id", required = true) @PathVariable String filterId) {
    	return new JsonResult<BondDmFilterDoc>().ok(induAdapter.conv(filterService.findFilterById(filterId),userId));
    }

	//不再提供回收站功能，所以用户选择删除时，直接物理删除，不可恢复
	@ApiOperation(value="删除回收站中的筛选方案（彻底删除）")
    @RequestMapping(value = "/users/{userId}/trash/filters/{filterId}", method = RequestMethod.DELETE, produces = "application/json")
    public JsonResult<String> deleteFilterFromTrash(
    		@ApiParam(name = "userId", value = "用户id", required = true) @PathVariable Long userId,
    		@ApiParam(name = "filterId", value = "过滤方案id", required = true) @PathVariable String filterId) {
		filterService.deleteFileterFromTrash(userId, filterId);
    	return new JsonResult<String>().ok(filterId);
    }
	
}

