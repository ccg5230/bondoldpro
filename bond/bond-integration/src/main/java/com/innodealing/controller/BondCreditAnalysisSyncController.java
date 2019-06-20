package com.innodealing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.innodealing.domain.JsonResult;
import com.innodealing.service.BondCreditAnalysisIntergationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "构建信用分析数据", description = "构建信用分析数据")
@RestController
@RequestMapping(value = "api/bond/creditAnalysis")
public class BondCreditAnalysisSyncController {

	@Autowired
	private BondCreditAnalysisIntergationService intergationService;

	@ApiOperation(value = "构建信用分析-YY评级")
	@RequestMapping(value = "/bulid/ratingdog", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Boolean> bulidRatiingDog() {
		Boolean result = intergationService.buildRatingDog();
		return new JsonResult<Boolean>().ok(result);
	}
	
	@ApiOperation(value = "删除ratingdog数据")
	@RequestMapping(value="/integration/{id}/deleteRatingdog",method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Boolean> deleteRatingdog(
			@ApiParam(name="id",value="数据id",required=true)@PathVariable Long id){
		Boolean result = intergationService.deleteRatingdog(id);
		return new JsonResult<Boolean>().ok(result);
	}
	
	@ApiOperation(value = "新增或者修改ratingdog数据")
	@RequestMapping(value="/integration/{id}/addAndUpdateRatingdog",method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Boolean> addAndUpdateRatingdog(
			@ApiParam(name="id",value="数据id",required=true)@PathVariable Long id){
		Boolean result = intergationService.addAndUpdateRatingdog(id);
		return new JsonResult<Boolean>().ok(result);
	}
	
	@ApiOperation(value = "构建信用分析-兴业评级")
	@RequestMapping(value = "/bulid/industrial", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Boolean> bulidIndustrial() {
		Boolean result = intergationService.bulidIndustrial();
		return new JsonResult<Boolean>().ok(result);
	}
	
	@ApiOperation(value = "删除兴业数据")
	@RequestMapping(value="/integration/{id}/deleteIndustrial",method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Boolean> deleteIndustrial(
			@ApiParam(name="id",value="数据id",required=true)@PathVariable Long id){
		Boolean result = intergationService.deleteIndustrial(id);
		return new JsonResult<Boolean>().ok(result);
	}
	
	@ApiOperation(value = "新增或者修改兴业数据")
	@RequestMapping(value="/integration/{id}/addAndUpdateIndustrial",method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Boolean> addAndUpdateIndustrial(
			@ApiParam(name="id",value="数据id",required=true)@PathVariable Long id){
		Boolean result = intergationService.addAndUpdateIndustrial(id);
		return new JsonResult<Boolean>().ok(result);
	}
	

	@ApiOperation(value = "构建信用分析-中金评级")
	@RequestMapping(value = "/bulid/cicc", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Boolean> bulidCicc() {
		Boolean result = intergationService.bulidCicc();
		return new JsonResult<Boolean>().ok(result);
	}
			
	@ApiOperation(value = "删除中金数据")
	@RequestMapping(value="/integration/{id}/deleteCicc",method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Boolean> deleteCicc(
			@ApiParam(name="id",value="数据id",required=true)@PathVariable Long id){
		Boolean result = intergationService.deleteCicc(id);
		return new JsonResult<Boolean>().ok(result);
	}
	
	@ApiOperation(value = "新增或者修改中金数据")
	@RequestMapping(value="/integration/{id}/addAndUpdateCicc",method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Boolean> addAndUpdateCicc(
			@ApiParam(name="id",value="数据id",required=true)@PathVariable Long id){
		Boolean result = intergationService.addAndUpdateCicc(id);
		return new JsonResult<Boolean>().ok(result);
	}
	
	@ApiOperation(value = "构建信用分析-国君评级")
	@RequestMapping(value = "/bulid/guoJun", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Boolean> bulidGuoJun() {
		Boolean result = intergationService.bulidGuoJun();
		return new JsonResult<Boolean>().ok(result);
	}
	
	@ApiOperation(value = "删除国君数据")
	@RequestMapping(value="/integration/{id}/deleteGuoJun",method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Boolean> deleteGuoJun(
			@ApiParam(name="id",value="数据id",required=true)@PathVariable Long id){
		Boolean result = intergationService.deleteGuoJun(id);
		return new JsonResult<Boolean>().ok(result);
	}

	@ApiOperation(value = "新增或者修改国君数据")
	@RequestMapping(value="/integration/{id}/addAndUpdateGuoJun",method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Boolean> addAndUpdateGuoJun(
			@ApiParam(name="id",value="数据id",required=true)@PathVariable Long id){
		Boolean result = intergationService.addAndUpdateGuoJun(id);
		return new JsonResult<Boolean>().ok(result);
	}
	
		
}
