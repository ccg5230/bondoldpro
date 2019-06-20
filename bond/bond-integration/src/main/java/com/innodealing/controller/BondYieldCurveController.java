package com.innodealing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.BuildResult;
import com.innodealing.service.BondYieldCurveJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping("api/tasks")
public class BondYieldCurveController {

	@Autowired
	private BondYieldCurveJobService bondYieldCurveJobService;
	
	@ApiOperation(value="曲线收益率")
	@RequestMapping(value="/bondYieldCurve",method=RequestMethod.GET,produces="application/json")
	public JsonResult<BuildResult> findBondYieldCurvesList(){
		bondYieldCurveJobService.refreshBondYieldCure();
		return new JsonResult<BuildResult>();
	}
	
	@ApiOperation(value="债券名称")
	@RequestMapping(value="/bondPubPar",method=RequestMethod.GET,produces="application/json")
	public JsonResult<BuildResult> findBondPubParList(){
		bondYieldCurveJobService.refreshPubPar();
		return new JsonResult<BuildResult>();
	}
}
