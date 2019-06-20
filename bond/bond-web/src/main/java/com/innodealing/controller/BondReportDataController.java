package com.innodealing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.bond.service.BondReportDataService;
import com.innodealing.bond.vo.reportdata.BondReportDataVO;
import com.innodealing.domain.JsonResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author xiaochao
 * @time 2017年3月9日
 * @description:
 */
@Api(description = "债券报告数据接口")
@RestController
@RequestMapping("api/bond/")
public class BondReportDataController extends BaseController {
	@Autowired
	BondReportDataService bondReportDataService;

	@ApiOperation(value = "获取私人财报数据")
	@RequestMapping(value = "report/data/personal/{taskId}", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<BondReportDataVO> findPerRptData(
			@RequestHeader("userid") Long userid,
			@ApiParam(name = "taskId", value = "私人财报的编号", required = true) @PathVariable Long taskId) {
		// TODO:userId validation
		return new JsonResult<BondReportDataVO>().ok(bondReportDataService.findPerBondReportData(taskId));
	}
}
