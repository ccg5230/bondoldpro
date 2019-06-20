package com.innodealing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.innodealing.domain.JsonResult;
import com.innodealing.service.AreaEconomiesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(description="构建区域经济指标")
@RestController
@RequestMapping("api/bond/areaEconomies")
public class BondAreaEconomiesController {
		
	private @Autowired AreaEconomiesService areaEconomiesService ;
	
	@ApiOperation(value ="构建区域经济指标")
	@RequestMapping(value="/area/indicators",method=RequestMethod.GET,produces="application/json")
	public JsonResult<Boolean> buildAreaEconomiesIndicators(){
		Boolean result = areaEconomiesService.buildIndicators();
		return new JsonResult<Boolean>().ok(result);
	}

	
	
}
