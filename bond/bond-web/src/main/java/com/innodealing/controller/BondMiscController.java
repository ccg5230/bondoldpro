package com.innodealing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.bond.param.BondEntitySearchReply;
import com.innodealing.bond.service.BondBasicInfoService;
import com.innodealing.bond.service.BondComInfoService;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.mongo.dm.BondBasicInfoDoc;
import com.innodealing.model.mongo.dm.BondComInfoDoc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "搜索等接口")
@RestController
@RequestMapping("api/bond/")
public class BondMiscController extends BaseController {

	@Autowired
	BondComInfoService comInfoService;
	
	@Autowired
	BondBasicInfoService bondBasicInfoService;
	
	@ApiOperation(value="查询-债券/发行人")
    @RequestMapping(value = "/entity/search", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<BondEntitySearchReply> findByFilterId(
    		@ApiParam(name = "query", value = "查询关键词", required = true) @RequestParam String query,
			@ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue="5") Integer limit,
			@ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam Long userId
			) {
		
		BondEntitySearchReply results = new BondEntitySearchReply();
		
		Page<BondBasicInfoDoc> basicInfoDocPage = bondBasicInfoService.searchBond(query, limit,userId);
		Page<BondComInfoDoc> comInfoDocPage = comInfoService.searchComInfo(query, limit ,userId);
		
		results.setIssuers(comInfoDocPage.getContent());
		results.setBonds(basicInfoDocPage.getContent());
		results.setIssuerCount(comInfoDocPage.getTotalElements());
		results.setBondCount(basicInfoDocPage.getTotalElements());
		
    	return new JsonResult<BondEntitySearchReply>().ok(results);
    }
}
