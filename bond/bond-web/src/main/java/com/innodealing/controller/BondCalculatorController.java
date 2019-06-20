package com.innodealing.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.bond.param.BondIssRating;
import com.innodealing.bond.service.BondComInfoService;
import com.innodealing.bond.service.BondDetailService;
import com.innodealing.bond.service.BondQuantAnalysisService;
import com.innodealing.bond.vo.summary.BondIssDMRatingSummaryVO;
import com.innodealing.bond.vo.summary.BondIssExtRatingSummaryVO;
import com.innodealing.bond.vo.summary.IssRatingScoreSummary;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.dm.bond.BondCredChan;
import com.innodealing.model.dm.bond.BondRatingDate;
import com.innodealing.model.mongo.dm.BondComInfoDoc;
import com.innodealing.model.mongo.dm.BondDetailDoc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "发行人数据接口")
@RestController
@RequestMapping("api/bond/")
public class BondCalculatorController extends BaseController {

	@Autowired
	BondQuantAnalysisService calculateService;
    
	@ApiOperation(value = "根据到期收益率计算净价")
	@RequestMapping(value = "/bonds/{bondId}/ytm/{ytm}/cleanPrice", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Double> calCleanPriceByYTM(
	        @RequestHeader("userid") long userid,
	        @ApiParam(name = "bondId", value = "债券唯一编号", required = true) @PathVariable String bondId, 
	        @ApiParam(name = "ytm", value = "到期收益率", required = true) @PathVariable String ytm) {
		return new JsonResult<Double>().ok(
		        calculateService.calCleanPriceByYTM(Long.valueOf(bondId), Double.valueOf(ytm)) );
	}
	
	@ApiOperation(value = "根据净价计算到期收益率")
	@RequestMapping(value = "/bonds/{bondId}/cleanPrice/{cleanPrice}/ytm", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Double> calYTMByCleanPrice(
	        @RequestHeader("userid") long userid,
	        @ApiParam(name = "bondId", value = "债券唯一编号", required = true) @PathVariable String bondId, 
	        @ApiParam(name = "cleanPrice", value = "净价", required = true) @PathVariable String cleanPrice) {
		return new JsonResult<Double>().ok(
		        calculateService.calYTMByCleanPrice(Long.valueOf(bondId), Double.valueOf(cleanPrice)) );
	}

    @ApiOperation(value = "根据中债估值计算修正久期")
    @RequestMapping(value = "/bonds/{bondId}/ytm/{ytm}/duration", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<Double> calModdByYield(
            @RequestHeader("userid") long userid,
            @ApiParam(name = "bondId", value = "债券唯一编号", required = true) @PathVariable String bondId, 
            @ApiParam(name = "ytm", value = "收益率", required = true) @PathVariable String ytm) {
        return new JsonResult<Double >().ok(
                calculateService.calDurationByYield(Long.valueOf(bondId), Double.valueOf(ytm)).get("modd") );
    }
	
    @ApiOperation(value = "根据中债估值计算麦考利久期")
    @RequestMapping(value = "/bonds/{bondId}/ytm/{ytm}/macd", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<Double> calMacdByYield(
            @RequestHeader("userid") long userid,
            @ApiParam(name = "bondId", value = "债券唯一编号", required = true) @PathVariable String bondId, 
            @ApiParam(name = "ytm", value = "收益率", required = true) @PathVariable String ytm) {
        return new JsonResult<Double >().ok(
                calculateService.calDurationByYield(Long.valueOf(bondId), Double.valueOf(ytm)).get("macd") );    }
    
    @ApiOperation(value = "根据中债估值计算凸性")
    @RequestMapping(value = "/bonds/{bondId}/ytm/{ytm}/convexity", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<Double> calConvexityByYield(
            @RequestHeader("userid") long userid,
            @ApiParam(name = "bondId", value = "债券唯一编号", required = true) @PathVariable String bondId, 
            @ApiParam(name = "ytm", value = "收益率", required = true) @PathVariable String ytm) {
        return new JsonResult<Double >().ok(
                calculateService.calDurationByYield(Long.valueOf(bondId), Double.valueOf(ytm)).get("convexity") );    }

    
    @ApiOperation(value = "利差计算")
    @RequestMapping(value = "/bonds/{bondId}/cleanPrice/{cleanPrice}/spread", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<Double> calSpreadByCleanPrice(
            @RequestHeader("userid") long userid,
            @ApiParam(name = "bondId", value = "债券唯一编号", required = true) @PathVariable String bondId, 
            @ApiParam(name = "cleanPrice", value = "净价", required = true) @PathVariable String cleanPrice) {
        return new JsonResult<Double >().ok(
                calculateService.calStaticSpreadByCleanPrice(Long.valueOf(bondId), Double.valueOf(cleanPrice)) );    }
    
}
