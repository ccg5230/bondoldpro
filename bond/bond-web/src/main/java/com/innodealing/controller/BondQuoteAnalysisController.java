package com.innodealing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.bond.service.BondQuoteAnalysisService;
import com.innodealing.bond.vo.quote.BondQuoteTodaycurveVo;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.mongo.dm.bond.quote.analyse.BondQuoteHistorycurveDoc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author stephen.ma
 * @date 2016年9月13日
 * @clasename BondQuoteAnalysisController.java
 * @decription TODO
 */
@Api(description = "行情及分析-报价分析")
@RestController
@RequestMapping("api/bond/")
public class BondQuoteAnalysisController extends BaseController {

	private @Autowired BondQuoteAnalysisService bondQuoteAnalysisService;

	@ApiOperation(value="今日曲线")
    @RequestMapping(value = "/bonds/{bondId}/quotes/analysis/todaycurve", method = RequestMethod.GET, produces = "application/json")
	 public JsonResult<BondQuoteTodaycurveVo> findTodaycurve(@ApiParam(name = "bondId", value = "债券的BondID/BondUniCode", required = true) @PathVariable Long bondId,
			 @ApiParam(name = "date", value = "时间，格式 yyyy-MM-dd", required = true) @RequestParam String date){
		
		return new JsonResult<BondQuoteTodaycurveVo>().ok(bondQuoteAnalysisService.findTodaycurve(bondId, date));
	}

	@ApiOperation(value = "歷史曲线")
	@RequestMapping(value = "/bonds/{bondId}/quotes/analysis/historycurve", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<BondQuoteHistorycurveDoc>> findHistorycurve(
			@ApiParam(name = "bondId", value = "债券的BondID/BondUniCode", required = true) @PathVariable Long bondId) {

		return new JsonResult<List<BondQuoteHistorycurveDoc>>().ok(bondQuoteAnalysisService.findHistorycurve(bondId));
	}

}
