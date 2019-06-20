package com.innodealing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.domain.JsonResult;
import com.innodealing.service.BondAnalysislIngtService;
import com.innodealing.service.BondComDataService;
import com.innodealing.service.BondRiskIntegrationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "发行人数据服务")
@RestController
@RequestMapping("api/bond/tasks/")
public class BondComDataController {

	@Autowired
	BondAnalysislIngtService bondAnalysislIngtService;
	
	@Autowired
	BondComDataService comDataService;
	
	@Autowired
	BondRiskIntegrationService bondRiskService2;
	
	@ApiOperation(value = "重建违约概率历史")
	@RequestMapping(value = "/coms/integrations/pds", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> buildPdHistory() {
		return new JsonResult<String>().ok(comDataService.integratePds());
	}
	
	@ApiOperation(value = "加载财务数据到缓存")
	@RequestMapping(value = "/coms/integrations/findata", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> buildComFinData() {
		return new JsonResult<String>().ok(comDataService.loadComFinQuality());
	}
	
	@ApiOperation(value = "重建发行人数据")
	@RequestMapping(value = "/coms/integrations/com", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> buildComData() {
		return new JsonResult<String>().ok(comDataService.integrateComInfos());
	}

	@ApiOperation(value = "重建违约概率排行")
	@RequestMapping(value = "/coms/integrations/pdrank", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> buildPdRank() {
		return new JsonResult<String>().ok(comDataService.integratePdRank());
	}
	
	@ApiOperation(value = "重建违约概率评分")
	@RequestMapping(value = "/coms/integrations/pdrrs", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> buildPdRrs() {
		return new JsonResult<String>().ok(comDataService.integratePdRrs());
	}
	
	@ApiOperation(value = "重建发行人数据")
	@RequestMapping(value = "/coms/integrations/all", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> buildComDataAll() {
		return new JsonResult<String>().ok(comDataService.integrateComInfoAll());
	}
	
	@ApiOperation(value = "重建发行人评级数据")
	@RequestMapping(value = "/coms/risk/integrations", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> buildComRiskData() {
		return new JsonResult<String>().ok(bondRiskService2.integrateComRiskInfo());
	}
	
	@ApiOperation(value = "重建发行人附属数据")
	@RequestMapping(value = "/coms/appurtenant/integrations", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> buildComAppurtenantData() {
		return new JsonResult<String>().ok(comDataService.buildComAppurtenantData());
	}
	
	@ApiOperation(value = "重建PdRank的最近两期主体量化风险等级数据")
	@RequestMapping(value = "/coms/pdrank/recentpd", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> buildComRecentPdRankData() {
		return new JsonResult<String>().ok(comDataService.updateBondIssuerPdRank());
	}
}
