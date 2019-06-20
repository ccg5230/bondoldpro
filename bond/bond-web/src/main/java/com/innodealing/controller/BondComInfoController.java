package com.innodealing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.adapter.BondInstitutionInduAdapter;
import com.innodealing.bond.param.BondIssRating;
import com.innodealing.bond.service.BondComInfoService;
import com.innodealing.bond.service.BondDetailService;
import com.innodealing.bond.vo.summary.BondIssDMRatingSummaryVO;
import com.innodealing.bond.vo.summary.BondIssExtRatingSummaryVO;
import com.innodealing.bond.vo.summary.IssRatingScoreSummary;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.dm.bond.BondCredChan;
import com.innodealing.model.dm.bond.BondRatingDate;
import com.innodealing.model.dm.bond.ccxe.BondDAnnMain;
import com.innodealing.model.mongo.dm.BondComInfoDoc;
import com.innodealing.model.mongo.dm.BondDetailDoc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "发行人数据接口")
@RestController
@RequestMapping("api/bond/")
public class BondComInfoController extends BaseController {

	@Autowired
	BondDetailService bondDetailService;

	@Autowired 
	BondComInfoService comInfoService;

    @Autowired
    BondInstitutionInduAdapter induAdapter;
    
	@ApiOperation(value = "自动完成-查找发行人")
	@RequestMapping(value = "/issuers/suggestions", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<BondComInfoDoc>> findSuggestionsByPrefix(
	        @RequestHeader("userid") long userid,
			@ApiParam(name = "prefix", value = "公司名前缀", required = true) @RequestParam String prefix,
			@ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue = "5") Integer limit) {
		return new JsonResult<List<BondComInfoDoc>>().ok(
		        (List<BondComInfoDoc>) induAdapter.conv(comInfoService.findByNamePrefix(prefix, limit), userid)
		        );
	}

	@ApiOperation(value = "发行人评级历史")
	@RequestMapping(value = "/issuers/{issuerId}/ratings/hist", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<PageImpl<BondIssRating>> findIssRatingHist(
			@ApiParam(name = "page", value = "页数", required = false) @RequestParam(defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue = "20") Integer limit,
			@ApiParam(name = "issuerId", value = "发行人唯一编号", required = true) @PathVariable Long issuerId,
			@ApiParam(name = "year", value = "选定的年份[yyyy]", required = false) @RequestParam(required = false) Long year,
			@ApiParam(name = "quarter", value = "选定的季度[1/2/3/4]", required = false) @RequestParam(required = false) Long quarter) {
		if (null != year && null != quarter) {
			return new JsonResult<PageImpl<BondIssRating>>()
					.ok(comInfoService.findIssRatingHistByIdAndQuar(issuerId, year, quarter));
		} else {
			return new JsonResult<PageImpl<BondIssRating>>()
				.ok(comInfoService.findIssRatingHist(issuerId, page - 1, limit));
		}
	}

	@ApiOperation(value = "发行人信息")
	@RequestMapping(value = "/issuers/{issuerId}", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<BondComInfoDoc> findIssuer(
	        @RequestHeader("userid") long userid,
			@ApiParam(name = "issuerId", value = "发行人唯一编号", required = true) @PathVariable Long issuerId,
			@ApiParam(name = "year", value = "选定的年份[yyyy]", required = false) @RequestParam(required = false) Long year,
			@ApiParam(name = "quarter", value = "选定的季度[1/2/3/4]", required = false) @RequestParam(required = false) Long quarter) {
		if (null != year && null != quarter) {
			return new JsonResult<BondComInfoDoc>().ok(
			        induAdapter.conv(comInfoService.findComInfoByIdAndQuar(issuerId, year, quarter), userid));
		} else {
			return new JsonResult<BondComInfoDoc>().ok(
		        induAdapter.conv(comInfoService.findComInfoById(issuerId,userid), userid));
		}
	}

	@ApiOperation(value = "公司持有债券")
	@RequestMapping(value = "/issuers/{issuerId}/bonds", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Page<BondDetailDoc>> bondList(
	        @RequestHeader("userid") long userid,
			@ApiParam(name = "issuerId", value = "发行人", required = true) @PathVariable String issuerId,
			@ApiParam(name = "type", value = "类型:1.当日有效报价(默认)2.所有债券", required = false) @RequestParam(defaultValue = "1") Integer type,
			@ApiParam(name = "page", value = "页数", required = false) @RequestParam(defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue = "20") Integer limit) {
		return new JsonResult<Page<BondDetailDoc>>()
				.ok(induAdapter.conv(bondDetailService.findBondByCompany(issuerId, type, page - 1, limit), userid).createPage());
	}

	@ApiOperation(value = "查询-外部评级观点")
	@RequestMapping(value = "/issuers/{issuerId}/ratePoint/{rateWritDate}", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<BondCredChan> ratePoint(
			@ApiParam(name = "issuerId", value = "发行人", required = true) @PathVariable String issuerId,
			@ApiParam(name = "rateWritDate", value = "评级时间", required = true) @PathVariable String rateWritDate,
			@ApiParam(name = "type", value = "类型:1观点(默认)2负面点3正面点 4关注点", required = false) @RequestParam(defaultValue = "1") Integer type) {
		return new JsonResult<BondCredChan>().ok(bondDetailService.ratePoint(issuerId, rateWritDate, type));
	}

	@ApiOperation(value = "发行人集合查询")
	@RequestMapping(value = "/issuers/comInfoList", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Page<BondComInfoDoc>> findBondComInfoList(
	        @RequestHeader("userid") long userid,
			@ApiParam(name = "condition", value = "列") @RequestParam String condition,
			@ApiParam(name = "page", value = "页数", required = false) @RequestParam(defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue = "3") Integer limit) {
		return new JsonResult<Page<BondComInfoDoc>>().ok(
		        induAdapter.conv(bondDetailService.findBondComInfoList(condition,page,limit), userid).createPage());
	}
	
	@ApiOperation(value = "发行人集合查询【含有行业信息作为查询条件】")
	@RequestMapping(value = "/issuers/{userId}/comInfoList", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Page<BondComInfoDoc>> findBondComInfoList(
	        @ApiParam(name = "userId", value = "用户ID", required = true) @PathVariable Long userId,
	        @ApiParam(name = "condition", value = "列") @RequestParam String condition,
	        @ApiParam(name = "induIds", value = "行业ID") @RequestParam(defaultValue="") Long[] induIds,
	        @ApiParam(name = "page", value = "页数", required = false) @RequestParam(defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue = "3") Integer limit) {
		return new JsonResult<Page<BondComInfoDoc>>().ok(
		        induAdapter.conv(bondDetailService.findBondComInfoListWithInduId(userId,condition,induIds,page,limit), userId).createPage());
	}

	@ApiOperation(value = "发行人外部评级概要信息")
	@RequestMapping(value = "/issuers/extRatingSummary", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<BondIssExtRatingSummaryVO> findBondIssExtRatingSummary(
			@ApiParam(name = "bondId", value = "债券唯一编号", required = false) @RequestParam(required = false) Long bondId,
			@ApiParam(name = "issuerId", value = "发行人", required = false) @RequestParam Long issuerId,
			@ApiParam(name = "year", value = "选定的年份[yyyy]", required = false) @RequestParam(required = false) Long year,
			@ApiParam(name = "quarter", value = "选定的季度[1/2/3/4]", required = false) @RequestParam(required = false) Long quarter) {
		if (null != year && null != quarter) {
			return new JsonResult<BondIssExtRatingSummaryVO>().ok(comInfoService.findBondIssExtRatingSummaryByQuarter(issuerId, year, quarter));
		} else {
			return new JsonResult<BondIssExtRatingSummaryVO>().ok(comInfoService.findBondIssExtRatingSummary(bondId,issuerId));
		}
	}

	@ApiOperation(value = "发行人主体量化评级评分分析")
	@RequestMapping(value = "/issuers/{issuerId}/dmRatingScoreSummary", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<IssRatingScoreSummary> finDmRatingScoreAnalysis(
			@RequestHeader("userid") Long userid,
			@ApiParam(name = "issuerId", value = "发行人", required = true) @PathVariable Long issuerId,
			@ApiParam(name = "year", value = "选定的年份[yyyy]", required = false) @RequestParam(required = false) Long year,
			@ApiParam(name = "quarter", value = "选定的季度[1/2/3/4]", required = false) @RequestParam(required = false) Long quarter) {
		return new JsonResult<IssRatingScoreSummary>().ok(comInfoService.findIssRatingScoreSummary(userid, issuerId, year, quarter));
	}

	@ApiOperation(value = "发行人主体量化评分比较")
	@RequestMapping(value = "/issuers/{issuerId}/dmScore/comparison", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<IssRatingScoreSummary> getDMScoreComparisonResult(
			@RequestHeader("userid") Long userid,
			@ApiParam(name = "issuerId", value = "发行人", required = true) @PathVariable Long issuerId,
			@ApiParam(name = "firstDate", value = "第一个日期[yyyyMM]", required = true) @RequestParam Long firstDate,
			@ApiParam(name = "secondDate", value = "第二个日期[yyyyMM]", required = true) @RequestParam Long secondDate) {
		return new JsonResult<IssRatingScoreSummary>().ok(comInfoService.getDMScoreComparisonResult(userid, issuerId, firstDate, secondDate));
	}
	
	@ApiOperation(value = "发行人主体量化评级概要信息")
	@RequestMapping(value = "/issuers/{issuerId}/dmRatingSummary", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<BondIssDMRatingSummaryVO> findBondIssDMRatingSummary(
            @RequestHeader("userid") Long userid,
			@ApiParam(name = "issuerId", value = "发行人", required = true) @PathVariable Long issuerId,
			@ApiParam(name = "year", value = "选定的年份[yyyy]", required = false) @RequestParam(required = false) Long year,
			@ApiParam(name = "quarter", value = "选定的季度[1/2/3/4]", required = false) @RequestParam(required = false) Long quarter) {
		return new JsonResult<BondIssDMRatingSummaryVO>().ok(comInfoService.findBondIssDMRatingSummary(userid, issuerId, year, quarter));
	}

	@ApiOperation(value = "发行人主体量化评级概要信息")
	@RequestMapping(value = "/issuers/{taskId}/privateRatingSummary", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<BondIssDMRatingSummaryVO> findPrivateIssDMRatingSummary(
	        @RequestHeader("userid") Long userid,
	        @ApiParam(name = "taskId", value = "任务编号", required = true) @PathVariable Long taskId) {
	    return new JsonResult<BondIssDMRatingSummaryVO>().ok(comInfoService.findPrivateIssDMRatingSummary(taskId));
	}

	@ApiOperation(value = "导出主体信用分析报告-->存在评级的日期")
	@RequestMapping(value = "/issuers/{issuerId}/getBondRateDate", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<BondRatingDate>> getBondRateDate(
			@ApiParam(name = "issuerId", value = "发行人", required = true) @PathVariable Long issuerId) {
		return new JsonResult<List<BondRatingDate>>().ok(comInfoService.getBondRateDate(issuerId));
	}
	
	@ApiOperation(value = "发行人评级公告附件列表")
    @RequestMapping(value = "/issuers/{issuerId}/rating/annattlist", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<BondDAnnMain>> findIssRatingAnnAtt(
            @ApiParam(name = "issuerId", value = "发行人唯一编号", required = true) @PathVariable Long issuerId
            ) {
	    return new JsonResult<List<BondDAnnMain>>()
                .ok(comInfoService.findIssRatingAnnAtt(issuerId));
    }
}
