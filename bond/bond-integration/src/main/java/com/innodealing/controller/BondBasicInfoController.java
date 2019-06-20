package com.innodealing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.domain.JsonResult;
import com.innodealing.service.BondBasicDataService;
import com.innodealing.service.BondImpliedRatingCpIntegrationService;
import com.innodealing.service.BondImpliedRatingIntegrationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "债券基础数据服务")
@RestController
@RequestMapping("api/bond/tasks/")
public class BondBasicInfoController {

	@Autowired
	BondBasicDataService bondDetailIntegrationSvc;
	
	@Autowired
	BondImpliedRatingIntegrationService bondImpliedRatingIntegrationService;
	
	@Autowired
	BondImpliedRatingCpIntegrationService bondImpliedRatingCpIntegrationService;

	@ApiOperation(value = "重建债券数据")
	@RequestMapping(value = "/bonds/integrations/rebuild", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> updateBondData() {
		return new JsonResult<String>().ok(bondDetailIntegrationSvc.syncIntegration(false));
	}

	@ApiOperation(value = "重建债券数据")
	@RequestMapping(value = "/bonds/integrations", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> rebuildBondData() {
		return new JsonResult<String>().ok(bondDetailIntegrationSvc.syncIntegration(true));
	}
	
	@ApiOperation(value = "同步存续债到dmdb库:支持单条")
    @RequestMapping(value = "/bonds/integrations/syncccxebond", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> syncccxebond(@ApiParam(name = "bondUniCode") @RequestParam(required = false) Long bondUniCode) {
        return new JsonResult<String>().ok(bondDetailIntegrationSvc.syncccxebond(bondUniCode));
    }
	
	@ApiOperation(value = "重建债劵隐含评级对比数据")
	@RequestMapping(value = "/bonds/bondImpliedRatingIntegration", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> buildComRiskData() {
		
		bondImpliedRatingIntegrationService.integrate();
		bondImpliedRatingCpIntegrationService.integrate();
		
		return new JsonResult<String>().ok("success");
	}
	
}
