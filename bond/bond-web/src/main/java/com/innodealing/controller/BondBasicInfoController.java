package com.innodealing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.adapter.BondInstitutionInduAdapter;
import com.innodealing.bond.param.BondRating;
import com.innodealing.bond.service.BondBasicInfoService;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.mongo.dm.BondBasicInfoDoc;
import com.innodealing.model.mongo.dm.BondDetailDoc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "债券基本信息接口")
@RestController
@RequestMapping("api/bond/")
public class BondBasicInfoController extends BaseController {

    @Autowired
    BondBasicInfoService bondBasicInfoService;
    
    @Autowired
    BondInstitutionInduAdapter induAdapter;
    
    @ApiOperation(value="查询-债券基本信息")
    @RequestMapping(value = "/bonds/{bondId}", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<BondBasicInfoDoc> findByBondId(
            @RequestHeader("userid") long userid,
            @ApiParam(name = "bondId", value = "债券唯一编号", required = true) @PathVariable String bondId
            ) {
        return new JsonResult<BondBasicInfoDoc>().ok(
                induAdapter.conv(bondBasicInfoService.findByBondUniCode(Long.valueOf(bondId),userid), userid)
          );
    }

    @ApiOperation(value="查询-债券基本信息 -包含用户信息")
    @RequestMapping(value = "/users/{userId}/bonds/{bondId}", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<BondBasicInfoDoc> findBondByUserAndBondId(
            @ApiParam(name = "userId", value = "用户唯一编号", required = true) @PathVariable Long userId,
            @ApiParam(name = "bondId", value = "债券唯一编号", required = true) @PathVariable String bondId
            ) {
        return new JsonResult<BondBasicInfoDoc>().ok(
                induAdapter.conv(bondBasicInfoService.findByUserAndBondUniCode(userId, Long.valueOf(bondId)), userId)
              );
    }

    @ApiOperation(value="查询-公司介绍")
    @RequestMapping(value = "/bonds/bondIssuerId/{issuerId}", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<BondBasicInfoDoc> findByIssuerId(
            @RequestHeader("userid") long userid,
            @ApiParam(name = "issuerId", value = "发行code", required = true) @PathVariable String issuerId
            ) {
        return new JsonResult<BondBasicInfoDoc>().ok(
                induAdapter.conv(bondBasicInfoService.findByIssuerId(Long.valueOf(issuerId)), userid)
               );
    }

    @ApiOperation(value="债券评级历史")
    @RequestMapping(value = "/bonds/{bondId}/ratings/hist", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<BondRating>> findBondRatingHist(
            @ApiParam(name = "bondId", value = "债券唯一编号", required = true) @PathVariable Long bondId
            ) {
        return new JsonResult<List<BondRating>>().ok(bondBasicInfoService.findRatingHist(bondId));
    }

    @ApiOperation(value="自动提示-前缀查找债券")
    @RequestMapping(value = "/bonds/suggestions", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<BondBasicInfoDoc>> findSuggestionsByPrefix(
            @RequestHeader("userid") long userid,
            @ApiParam(name = "codePrefix", value = "债券代码前缀", required = false) @RequestParam(defaultValue="") String codePrefix,
            @ApiParam(name = "shortNamePrefix", value = "债券名前缀", required = false) @RequestParam(defaultValue="") String shortNamePrefix,
            @ApiParam(name = "operator", value = "关系操作符", required = true) @RequestParam(defaultValue="and") String operator,
            @ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue="20") Integer limit
            ) {
        return new JsonResult<List<BondBasicInfoDoc>>().ok(
                (List<BondBasicInfoDoc>) induAdapter.conv(
                        bondBasicInfoService.searchByNameAndCode(codePrefix, shortNamePrefix, operator, 0, limit), userid)
           );
    }


    @ApiOperation(value = "查询-该用户是否关注了该债券,是否加入了对比")
    @RequestMapping(value = "/bonds/{userId}/isFavoritedAndCompared/{bondId}", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<BondDetailDoc> isFavoritedAndCompared(
            @ApiParam(name = "userId", value = "用户唯一编号", required = true) @PathVariable Long userId,
            @ApiParam(name = "bondId", value = "债券唯一编号", required = true) @PathVariable Long bondId) {

        return new JsonResult<BondDetailDoc>().ok(bondBasicInfoService.isFavoritedAndCompared(userId, bondId));
    }
}
