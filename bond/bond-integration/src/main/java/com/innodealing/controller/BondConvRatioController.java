package com.innodealing.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.domain.JsonResult;
import com.innodealing.service.BondAnalysislIngtService;
import com.innodealing.service.BondBasicDataService;
import com.innodealing.service.BondConvRatioService;
import com.innodealing.service.BondQuoteIntegrationService;
import com.innodealing.service.BondSentimentDateJobService;
import com.innodealing.service.BondSentimentJobService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "债券折算率数据")
@RestController
@RequestMapping("api/bond/tasks/")
public class BondConvRatioController {

	@Autowired
	BondConvRatioService convRatioSvc;

	@ApiOperation(value = "上传债券折算率数据")
	@RequestMapping(value = "/bonds/integrations/convRatio", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> updateBondData() throws IOException {
		return new JsonResult<String>().ok(convRatioSvc.convRatioRebuild());
	}
	
	@ApiOperation(value = "强制刷新债券折算率数据，无视数据是否已经存在")
	@RequestMapping(value = "/bonds/integrations/forceConvRatioRebuild", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> forceConvRatioRebuild() throws IOException {
		return new JsonResult<String>().ok(convRatioSvc.forceConvRatioRebuild());
	}

}
