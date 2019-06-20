package com.innodealing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.model.mongodb.BondPdRankDoc;
import com.innodealing.service.BondPdRankService;
import com.innodealing.util.JsonResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


/**
 * 
 * 
 * @author 戴永杰
 *
 * @date 2018年1月15日 上午11:13:36 
 * @version V1.0   
 *
 */
@Api(description = "风险量化等级")
@RequestMapping("/bondIndu")
@RestController
public class BondPdRankController extends BaseController{
	
	@Autowired
	private BondPdRankService bondPdRankService;
	
	@ApiOperation(value = "获取风险量化等级详细")
	@RequestMapping(value = "/getPdRankDetail", method = RequestMethod.POST, produces = "application/json")
	public 	JsonResult<BondPdRankDoc> getPdRankDetail(
			@ApiParam(name = "key", value = "关键字,债券简称、全称、代码，企业简称、全称、代码", required = true) @RequestParam String key) {
		return new JsonResult<BondPdRankDoc>()
				.ok(bondPdRankService.getPdRankDetail(key));
	}

}
