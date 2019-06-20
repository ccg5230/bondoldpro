package com.innodealing.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.innodealing.adapter.BondInstitutionInduAdapter;
import com.innodealing.bond.param.BondDmFilterReq;
import com.innodealing.bond.param.BondSimilarFilterReq;
import com.innodealing.bond.service.BondDetailService;
import com.innodealing.bond.service.BondInduService;
import com.innodealing.bond.vo.summary.BondDetailSummary;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.dm.bond.BondIndicatorFilterReq;
import com.innodealing.model.mongo.dm.BondDetailDoc;
import com.innodealing.model.mongo.dm.BondFieldGroupMappingDoc;
import com.innodealing.util.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "债券动态详情数据接口")
@RestController
@RequestMapping("api/bond/")
public class BondDetailController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(BondDetailController.class);

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    BondDetailService bondDetailService;

    @Autowired
    BondInstitutionInduAdapter induAdapter;

    @Autowired
    BondInduService induSerivce;

    @ApiOperation(value = "通过动态筛选方案筛选债券")
    @RequestMapping(value = "/bonds/filter", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<Page<BondDetailDoc>> findByFilter(@RequestBody BondDmFilterReq newFilterParam, @RequestHeader("userid") long userid,
            @ApiParam(name = "page", value = "页数", required = false) @RequestParam(defaultValue = "1") Integer page,
            @ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue = "20") Integer limit,
            @ApiParam(name = "sort", value = "排序规则", required = false) @RequestParam(defaultValue = "updateTime:desc") String sort) {
        newFilterParam.setInduClass(induSerivce.findInduClassByUser(userid));
        return new JsonResult<Page<BondDetailDoc>>()
                .ok(induAdapter.conv(bondDetailService.findBondDetailByFilter(newFilterParam, page - 1, limit, sort), userid).createPage());
    }
    
    @ApiOperation(value = "获取发行人持有债券汇总")
    @RequestMapping(value = "/bonds/getBondStatistic", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<Map<String, Object>> getBondStatistic(
    		@ApiParam(name = "comUniCode", value = "发行人标识", required = true) @RequestParam Long comUniCode) {
        return new JsonResult<Map<String, Object>>()
                .ok(bondDetailService.getBondStatistic(comUniCode));
    }

    @ApiOperation(value = "获取主体在给定时间前的发行债券列表数据[report-service]")
    @RequestMapping(value = "/issuer/{issuerId}/bond/list", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<BondDetailSummary> getBondListSummary(@RequestHeader("userid") Long userid,
			@ApiParam(name = "issuerId", value = "发行人", required = true) @PathVariable Long issuerId,
			@ApiParam(name = "year", value = "选定的年份[yyyy]", required = false) @RequestParam(required = false) Integer year,
			@ApiParam(name = "quarter", value = "选定的季度[1/2/3/4]", required = false) @RequestParam(required = false) Integer quarter) {
        return new JsonResult<BondDetailSummary>().ok(bondDetailService.getBondDetailSummary(userid, issuerId, year, quarter));
    }

    @ApiOperation(value = "同类债券筛选")
    @RequestMapping(value = "/bonds/{bondId}/similar", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<Page<BondDetailDoc>> findBySimpleFilter(@RequestHeader("userid") long userid, @RequestBody BondSimilarFilterReq filter,
            @ApiParam(name = "page", value = "页数", required = false) @RequestParam(defaultValue = "1") Integer page,
            @ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue = "20") Integer limit,
            @ApiParam(name = "sort", value = "排序规则", required = false) @RequestParam(defaultValue = "updateTime:desc") String sort) {
        return new JsonResult<Page<BondDetailDoc>>()
                .ok(induAdapter.conv(bondDetailService.findBondDetailBySimilar(induAdapter.conv(filter, userid), page - 1, limit, sort), userid).createPage());
    }

    @ApiOperation(value = "债券高级筛选")
    @RequestMapping(value = "/bonds/issNdicatorFilter/list", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<Object> issNdicatorFilter(@RequestBody BondDmFilterReq newFilterParam, @RequestHeader("userid") long userid) {
        JsonResult<Object> o = new JsonResult<Object>().ok(bondDetailService.issNdicatorFilter(newFilterParam));
        return o;
    }

    @ApiOperation(value = "债券高级筛选-->财务指标")
    @RequestMapping(value = "/bonds/issNdicator/list", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<List<BondFieldGroupMappingDoc>> issNdicator(
            @ApiParam(name = "type", value = "类型", required = false) @RequestParam(defaultValue = "1") Integer type) {
        List<BondFieldGroupMappingDoc> list = bondDetailService.queryBondFieldGroupMappingList(type);
        return new JsonResult<List<BondFieldGroupMappingDoc>>().ok(list);
    }

    @ApiOperation(value = "债券高级筛选-->查看结果")
    @RequestMapping(value = "/bonds/issNdicatorFilter/filter", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<Page<BondDetailDoc>> issNdicatorFilterList(@RequestBody BondDmFilterReq newFilterParam, @RequestHeader("userid") long userid,
            @ApiParam(name = "page", value = "页数", required = false) @RequestParam(defaultValue = "1") Integer page,
            @ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue = "20") Integer limit,
            @ApiParam(name = "sort", value = "排序规则", required = false) @RequestParam(defaultValue = "updateTime:desc") String sort) throws Exception {
//        newFilterParam.setInduClass(induSerivce.findInduClassByUser(userid));
        return new JsonResult<Page<BondDetailDoc>>()
                .ok(induAdapter.convByInduClass(bondDetailService.issNdicatorFilterList(newFilterParam, page - 1, limit, sort, userid), newFilterParam.getInduClass(),userid).createPage());
    }

    @ApiOperation(value = "债券高级筛选-->导出结果")
    @RequestMapping(value = "/bonds/issNdicatorFilter/exportExcel", method = RequestMethod.GET, produces = "application/json")
    public void exportExcel(@ApiParam(name = "newFilterParamJson", required = true) String newFilterParamJson, HttpServletResponse response) throws Exception {
        
        if (StringUtils.isEmpty(newFilterParamJson)) {
            throw new NullPointerException("newFilterParamJson is null");
        }
        //处理特殊符号%
//        newFilterParamJson = ConverPercent.convertPercent(newFilterParamJson); 
        
        newFilterParamJson = java.net.URLDecoder.decode(newFilterParamJson, "UTF-8");

        BondDmFilterReq newFilterParam = JSONObject.parseObject(newFilterParamJson, BondDmFilterReq.class);

        if (newFilterParam == null) {
            throw new NullPointerException("newFilterParam is null");
        }
//        newFilterParam.setInduClass(induSerivce.findInduClassByUser(newFilterParam.getUserId()));
        bondDetailService.exportExcel(newFilterParam, newFilterParam.getUserId(), response);
    }

    @ApiOperation(value = "导出结果")
    @RequestMapping(value = "/bonds/issNdicatorFilter/exportExcel1", method = RequestMethod.GET, produces = "application/json")
    public void exportExcel1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BondDmFilterReq newFilterParam = new BondDmFilterReq();
        newFilterParam.setInduClass(induSerivce.findInduClassByUser(500001L));
        List<BondIndicatorFilterReq> list = new ArrayList<BondIndicatorFilterReq>();
        BondIndicatorFilterReq a1 = new BondIndicatorFilterReq();
        a1.setField("quarters.Turnover");
        a1.setQuarter("2016/Q3");
        a1.setMinIndicator(7418D);
        a1.setMaxIndicator(24909687D);
        BondIndicatorFilterReq a2 = new BondIndicatorFilterReq();
        a2.setField("quarters.Oprt_prft");
        a2.setQuarter("2016/Q2");
        a2.setMinIndicator(57D);
        a2.setMaxIndicator(1250936D);
        list.add(a1);
        list.add(a2);
        newFilterParam.setBondIndicatorFilterReqs(list);
        newFilterParam.setUserId(500001L);
        List<Integer> dmBondTypes = new ArrayList<Integer>() {
            {
                add(7);
                add(8);
                add(9);
                add(11);
                add(12);
                add(13);
            }
        };
        newFilterParam.setDmBondTypes(dmBondTypes);
        newFilterParam.setFilterName("新建筛选方案1");

        bondDetailService.exportExcel(newFilterParam, 500001L, response);
    }
    
    @ApiOperation(value = "债券高级筛选-->查看结果-->所有债劵代码")
    @RequestMapping(value = "/bonds/filterBondUniCodeList", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<List<Long>> filterBondUniCodeList(@RequestBody BondDmFilterReq newFilterParam, @RequestHeader("userid") long userid,
            @ApiParam(name = "page", value = "页数", required = false) @RequestParam(defaultValue = "1") Integer page,
            @ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue = "20") Integer limit,
            @ApiParam(name = "sort", value = "排序规则", required = false) @RequestParam(defaultValue = "updateTime:desc") String sort) throws Exception {
        return new JsonResult<List<Long>>()
                .ok(bondDetailService.filterBondUniCodeList(newFilterParam, page - 1, limit, sort, userid));
    }

}
