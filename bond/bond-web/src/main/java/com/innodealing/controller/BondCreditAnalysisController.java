package com.innodealing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.bond.param.PageModel;
import com.innodealing.bond.service.BondCreditAnalysisService;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.mongo.dm.bond.creditanalysis.CreditAnalysisCiccDoc;
import com.innodealing.model.mongo.dm.bond.creditanalysis.CreditAnalysisGuoJunDoc;
import com.innodealing.model.mongo.dm.bond.creditanalysis.CreditAnalysisIndustrialDoc;
import com.innodealing.model.mongo.dm.bond.creditanalysis.CreditAnalysisRatingDogDoc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 债券信用分析接口
 * 
 * @author Administrator
 *
 */
@Api(value = "债券信用分析接口", description = "债券信用分析接口")
@RestController
@RequestMapping("api/bond/creditAnalysis")
public class BondCreditAnalysisController {

	@Autowired
	private BondCreditAnalysisService bondCreditAnalysisService;

	@ApiOperation(value = "YY信用分析数据")
	@RequestMapping(value = "/findRatingDog/{comUniCode}", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<PageModel<CreditAnalysisRatingDogDoc>> findRatingDog(
			@ApiParam(name = "pagesize", value = "当前页数", required = true) @RequestParam(defaultValue = "1") Integer pagesize,
			@ApiParam(name = "limit", value = "每页显示数", required = true) @RequestParam(defaultValue = "8") Integer limit,
			@ApiParam(name = "comUniCode", value = "发行人Code", required = true) @PathVariable Long comUniCode) {
		return new JsonResult<PageModel<CreditAnalysisRatingDogDoc>>()
				.ok(bondCreditAnalysisService.findRatingDog(comUniCode, pagesize, limit));
	}

	@ApiOperation(value = "查询YY评级详情")
	@RequestMapping(value = "/findYYByComUniCode/{comUniCode}", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<CreditAnalysisRatingDogDoc>> findByComUniCode(
			@ApiParam(name = "comUniCode", value = "发行人code", required = true) @PathVariable Long comUniCode) {

		return new JsonResult<List<CreditAnalysisRatingDogDoc>>().ok(bondCreditAnalysisService.findByComUniCode(comUniCode));
	}

	@ApiOperation(value = "兴业信用分析数据")
	@RequestMapping(value = "/findIndustrial/{comUniCode}", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<PageModel<CreditAnalysisIndustrialDoc>> findIndustrial(
			@ApiParam(name = "pagesize", value = "当前页数", required = true) @RequestParam(defaultValue = "1") Integer pagesize,
			@ApiParam(name = "limit", value = "每页显示数", required = true) @RequestParam(defaultValue = "8") Integer limit,
			@ApiParam(name = "comUniCode", value = "发行人Code", required = true) @PathVariable Long comUniCode) {
		return new JsonResult<PageModel<CreditAnalysisIndustrialDoc>>()
				.ok(bondCreditAnalysisService.findIndustrial(comUniCode, pagesize, limit));
	}

	@ApiOperation(value = "查询兴业评级详情")
	@RequestMapping(value = "/findIndustrialBycomUniCode/{comUniCode}", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<CreditAnalysisIndustrialDoc>> findIndustrialBycomUniCode(
			@ApiParam(name = "comUniCode", value = "发行人code", required = true) @PathVariable Long comUniCode) {

		return new JsonResult<List<CreditAnalysisIndustrialDoc>>().ok(bondCreditAnalysisService.findIndustrialBycomUniCode(comUniCode));
	}

	@ApiOperation(value = "中金信用分析数据")
	@RequestMapping(value = "/findCicc/{comUniCode}", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<PageModel<CreditAnalysisCiccDoc>> findCicc(
			@ApiParam(name = "pagesize", value = "当前页数", required = true) @RequestParam(defaultValue = "1") Integer pagesize,
			@ApiParam(name = "limit", value = "每页显示数", required = true) @RequestParam(defaultValue = "8") Integer limit,
			@ApiParam(name = "comUniCode", value = "发行人Code", required = true) @PathVariable Long comUniCode) {
		return new JsonResult<PageModel<CreditAnalysisCiccDoc>>()
				.ok(bondCreditAnalysisService.findCicc(comUniCode, pagesize, limit));
	}

	@ApiOperation(value = "查询中金评级详情")
	@RequestMapping(value = "/findCiccByComUniCode/{comUniCode}", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<CreditAnalysisCiccDoc>> findCiccByComUniCode(
			@ApiParam(name = "comUniCode", value = "发行人code", required = true) @PathVariable Long comUniCode) {

		return new JsonResult<List<CreditAnalysisCiccDoc>>().ok(bondCreditAnalysisService.findCiccByComUniCode(comUniCode));
	}

	@ApiOperation(value = "国君信用分析数据")
	@RequestMapping(value = "/findGuoJun/{comUniCode}", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<PageModel<CreditAnalysisGuoJunDoc>> findGuoJun(
			@ApiParam(name = "pagesize", value = "当前页数", required = true) @RequestParam(defaultValue = "1") Integer pagesize,
			@ApiParam(name = "limit", value = "每页显示数", required = true) @RequestParam(defaultValue = "8") Integer limit,
			@ApiParam(name = "comUniCode", value = "发行人Code", required = true) @PathVariable Long comUniCode) {
		return new JsonResult<PageModel<CreditAnalysisGuoJunDoc>>()
				.ok(bondCreditAnalysisService.findGuoJun(comUniCode, pagesize, limit));
	}

	@ApiOperation(value = "查询国君评级详情")
	@RequestMapping(value = "/findGuoJunByComUniCode/{comUniCode}", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<CreditAnalysisGuoJunDoc>> findGuoJunByComUniCode(
			@ApiParam(name = "comUniCode", value = "发行人code", required = true) @PathVariable Long comUniCode) {

		return new JsonResult<List<CreditAnalysisGuoJunDoc>>().ok(bondCreditAnalysisService.findGuoJunByComUniCode(comUniCode));
	}

}
