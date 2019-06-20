package com.innodealing.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.bond.service.BondComInfoService;
import com.innodealing.bond.service.finance.BondFinanceInfoService;
import com.innodealing.bond.vo.analyse.BondFinanceInfoVo;
import com.innodealing.bond.vo.analyse.IndicatorGroupVo;
import com.innodealing.bond.vo.analyse.InduBreachProbabilityUpDoc;
import com.innodealing.bond.vo.analyse.InduCredRatingVo;
import com.innodealing.bond.vo.analyse.IssFinanceChangeKVo;
import com.innodealing.bond.vo.analyse.IssFinanceChangeVo;
import com.innodealing.bond.vo.analyse.IssIndicatorPdVo;
import com.innodealing.bond.vo.analyse.IssIndicatorsVo;
import com.innodealing.bond.vo.analyse.IssInduRatingCompareVo;
import com.innodealing.bond.vo.analyse.PdInduCompareVo;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.mongo.dm.BondPdHistRec;
import com.innodealing.model.mongo.dm.bond.detail.analyse.BondFinanceInfoInduDoc;
import com.innodealing.model.mongo.dm.bond.detail.analyse.BondFinanceInfoQuarterDoc;
import com.innodealing.model.mongo.dm.bond.detail.analyse.BondInduAnalyseDoc;
import com.innodealing.model.mongo.dm.bond.detail.analyse.FinanceInfoQuarter;
import com.innodealing.util.SafeUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "信用债-债券详情-主体分析")
@RestController
@RequestMapping("api/bond/")
public class BondPdAnalysisController extends BaseController{

    @Autowired
    private BondComInfoService comInfoService;

    @Autowired
    private BondFinanceInfoService bondFinanceInfoService;
    
    
    private static final Logger logger = LoggerFactory.getLogger(BondPdAnalysisController.class);

    @ApiOperation(value = "公司违约概率列表")
    @RequestMapping(value = "/companys/{comUniCode}/pds", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<PageImpl<BondPdHistRec>> findPdHistBy(
    		@ApiParam(name = "page", value = "页数", required = false) @RequestParam(defaultValue="1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue="20") Integer limit,
            @ApiParam(name = "comUniCode", value = "公司id", required = true) @RequestParam Long comUniCode,
			@ApiParam(name = "year", value = "选定的年份[yyyy]", required = false) @RequestParam(required = false) Long year,
			@ApiParam(name = "quarter", value = "选定的季度[1/2/3/4]", required = false) @RequestParam(required = false) Long quarter) {
    	if (null != year && null != quarter) {
    		return new JsonResult<PageImpl<BondPdHistRec>>().ok(comInfoService.findPdHistByComUniCodeAndQuar(comUniCode, page-1, limit, year, quarter));
    	} else {
    		return new JsonResult<PageImpl<BondPdHistRec>>().ok(comInfoService.findPdHistByComUniCode(comUniCode, page-1, limit));
    	}
    }
    
    @ApiOperation(value = "违约提示信息")
    @RequestMapping(value = "/companys/{comUniCode}/pdsmsg", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<String> findPdMsg(@ApiParam(name = "comUniCode", value = "公司id", required = true) @RequestParam Long comUniCode) {
        return new JsonResult<String>().ok(comInfoService.pdExceptionRoute(comUniCode));
    }

    @ApiOperation(value = "违约概率分析-第一页(财务指标变动情况表)-[zzl]")
    @RequestMapping(value = "/companys/{companyId}/financeChange", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<IssFinanceChangeVo>> findIssFinanceChange(
            @ApiParam(name = "companyId", value = "公司id", required = true) @PathVariable Long companyId) {
        return new JsonResult<List<IssFinanceChangeVo>>().ok(bondFinanceInfoService.findIssFinanceChange(companyId));
    }
    
    @ApiOperation(value = "违约概率分析-第一页(财务指标变动情况K线图)-[zzl]")
    @RequestMapping(value = "/companys/{companyId}/financeChangeK", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<IssFinanceChangeKVo>> findIssFinanceChange(
            @ApiParam(name = "companyId", value = "公司id", required = true) @PathVariable Long companyId,
            @ApiParam(name = "category", value = "公司id", required = true) @RequestParam String category,
            @ApiParam(name = "field", value = "公司id", required = true)  @RequestParam String field,
            @RequestHeader(name="userid" ,required = false) Long userid) {
        return new JsonResult<List<IssFinanceChangeKVo>>().ok(bondFinanceInfoService.findIssFinanceChangeKX(companyId, category, field, null, userid));
    }
    
    @ApiOperation(value = "违约概率分析-第一页(财务指标变动情况K线图)-[zzl]")
    @RequestMapping(value = "/companys/{companyId}/financeChangeK4Province", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<List<IssFinanceChangeKVo>> financeChangeK4Province(
            @ApiParam(name = "companyId", value = "公司id", required = true) @PathVariable Long companyId,
            @ApiParam(name = "category", value = "公司id", required = true) @RequestParam String category,
            @ApiParam(name = "field", value = "公司id", required = true)  @RequestParam String field,
            @ApiParam(name = "provinceIds[]", value = "省份id集合") @RequestParam(value = "provinceIds[]") Long[] provinceIds,
            @RequestHeader(name="userid" ,required = false) Long userid) {
        return new JsonResult<List<IssFinanceChangeKVo>>().ok(bondFinanceInfoService.findIssFinanceChangeKX(companyId, category, field, Arrays.asList(provinceIds), userid));
    }
    
    @ApiOperation(value = "违约概率分析-第二页(本期重点财务指标于行业位置分析)-[zzl]")
    @RequestMapping(value = "/companys/{companyId}/finance", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<BondFinanceInfoVo>> findBondFinanceInfoDoc(
            @RequestHeader(name="userid" ,required = false) Long userid,
            @ApiParam(name = "companyId", value = "公司id", required = true) @PathVariable Long companyId,
			@ApiParam(name = "year", value = "选定的年份[yyyy]", required = false) @RequestParam(required = false) Integer year,
			@ApiParam(name = "quarter", value = "选定的季度[1/2/3/4]", required = false) @RequestParam(required = false) Integer quarter) {
        return new JsonResult<List<BondFinanceInfoVo>>().ok(bondFinanceInfoService.findBondFinanceInfoDoc(companyId, null, userid, year, quarter, null));
    }
    
    
    @ApiOperation(value = "违约概率分析-城头债(本期重点财务指标于行业位置分析)-[zzl]")
    @RequestMapping(value = "/companys/{companyId}/finance4Province", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<List<BondFinanceInfoVo>> findBondFinanceInfoDoc(
            @ApiParam(name = "companyId", value = "公司id", required = true) @PathVariable Long companyId,
            @ApiParam(name = "provinceIds[]", value = "省份id集合") @RequestParam(value = "provinceIds[]") Long[] provinceIds,
            @RequestHeader(name="userid" ,required = false) Long userid) {
        return new JsonResult<List<BondFinanceInfoVo>>().ok(bondFinanceInfoService.findBondFinanceInfoDoc(companyId, Arrays.asList(provinceIds), userid, null, null, null));
    }

    @ApiOperation(value = "违约概率分析-第三页(本期重点财务指标与行业比较  )-[zzl]")
    @RequestMapping(value = "/companys/{companyId}/financeIndu", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<BondFinanceInfoInduDoc> findBondFinanceInfoInduDoc(
            @ApiParam(name = "companyId", value = "公司id", required = true) @PathVariable Long companyId,
            @ApiParam(name = "provinceIds[]", value = "省份id集合") @RequestParam(value = "provinceIds[]", required = false ) Long[] provinceIds,
            @RequestHeader(name="userid" ,required = false) Long userid) {
        List<Long> proIds = provinceIds== null ||provinceIds.length == 0 ? null : Arrays.asList(provinceIds);
        return new JsonResult<BondFinanceInfoInduDoc>()
                .ok(bondFinanceInfoService.findBondFinanceInfoInduDoc(companyId ,proIds, userid, null, null));
    }
    
    @ApiOperation(value = "违约概率分析-第三页(本期重点财务指标与行业比较  )-[zzl]")
    @RequestMapping(value = "/companys/{companyId}/financeIndu", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<BondFinanceInfoInduDoc> findBondFinanceInfoInduPost(
            @ApiParam(name = "companyId", value = "公司id", required = true) @PathVariable Long companyId,
            @ApiParam(name = "provinceIds[]", value = "省份id集合") @RequestParam(value = "provinceIds[]", required = false ) Long[] provinceIds,
            @RequestHeader(name="userid" ,required = false) Long userid) {
        List<Long> proIds = provinceIds== null ||provinceIds.length == 0 ? null : Arrays.asList(provinceIds);
        return new JsonResult<BondFinanceInfoInduDoc>()
                .ok(bondFinanceInfoService.findBondFinanceInfoInduDoc(companyId ,proIds, userid, null, null));
    }
    
    @ApiOperation(value = "行业分析- 行业主体财务指标分类-[zzl]")
    @RequestMapping(value = "/companys/indicator/categorys", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<IndicatorGroupVo>> categorys() {
        return new JsonResult<List<IndicatorGroupVo>>().ok(bondFinanceInfoService.findIndicatorCategory());
    }
    
    @ApiOperation(value = "行业分析- 行业主体财务指标分类-[zzl]")
    @RequestMapping(value = "/companys/{issId}/indicator/categorys", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<IndicatorGroupVo>> categorys(
            @ApiParam(name = "issId", value = "公司id", required = true) @PathVariable Long issId) {
        return new JsonResult<List<IndicatorGroupVo>>().ok(bondFinanceInfoService.findIndicatorCategoryDistr(issId));
    }

    @ApiOperation(value = "违约概率分析-第三页(近2期重点财务指标对比  )-[zzl]")
    @RequestMapping(value = "/companys/{companyId}/financeQuarter", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<BondFinanceInfoQuarterDoc> findBondFinanceInfoQuarterDoc(
            @ApiParam(name = "companyId", value = "公司id", required = true) @PathVariable Long companyId,
            @RequestHeader(name="userid" ,required = false) Long userid) {
        return new JsonResult<BondFinanceInfoQuarterDoc>()
                .ok(bondFinanceInfoService.findBondFinanceInfoQuarterDoc(companyId, userid));
    }

   /* @ApiOperation(value = "行业分析-行业评级变动概览-[zzl]")
    @RequestMapping(value = "/indus/{induId}/analyse", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<BondInduAnalyseDoc> findBondInduAnalyse(
            @ApiParam(name = "induId", value = "行业id", required = true) @PathVariable Long induId,
            @RequestHeader(name="userid" ,required = false) Long userid) {
    	System.out.println(userid);
        return new JsonResult<BondInduAnalyseDoc>().ok(bondFinanceInfoService.findBondInduAnalyseV2(induId, userid,null));
    }*/

   /* @ApiOperation(value = "行业分析-同行业违约概率增加-[zzl]")
    @RequestMapping(value = "/indus/{induId}/pdUp", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<Page<InduBreachProbabilityUpDoc>> findInduBreachProbability(
            @ApiParam(name = "induId", value = "行业id", required = true) @PathVariable Long induId,
            @ApiParam(name = "page", value = "第几页", required = true) @RequestParam int page,
            @ApiParam(name = "size", value = "每页返回行数", required = true) @RequestParam int size,
            @RequestHeader(name="userid" ,required = false) Long userid) {
        return new JsonResult<Page<InduBreachProbabilityUpDoc>>()
                .ok(bondFinanceInfoService.findInduBreachProbability(induId, page, size, userid));
    }*/
  
    /*@ApiOperation(value = "行业分析- 同行业主体评级下降-[zzl]")
    @RequestMapping(value = "/indus/pdDetail", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<Page<InduCredRatingVo>> findInduRatingDown(
            //@RequestBody ListParam<Long> induIds,
            @ApiParam(name = "induIds[]", value = "行业ids,在实际代码中参数为induIds即可", required = true) @RequestParam("induIds[]") Long[] induIds,
            @ApiParam(name = "page", value = "第几页", required = true) @RequestParam int page,
            @ApiParam(name = "size", value = "每页返回行数", required = true) @RequestParam int size,
            @RequestHeader(name="userid" ,required = false) Long userid) {
        
        return new JsonResult<Page<InduCredRatingVo>>()
                .ok(bondFinanceInfoService.findInduRatingDown(Arrays.asList(induIds), page, size, userid));
    }*/
    
   /* @ApiOperation(value = "行业分析- 同行业主体展望负面-[zzl]")
    @RequestMapping(value = "/indus/{induId}/por", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<Page<InduCredRatingVo>> findInduPor(
            @ApiParam(name = "induId", value = "行业id", required = true) @PathVariable Long induId,
            @ApiParam(name = "page", value = "第几页", required = true) @RequestParam int page,
            @ApiParam(name = "size", value = "每页返回行数", required = true) @RequestParam int size,
            @RequestHeader(name="userid" ,required = false) Long userid) {
        return new JsonResult<Page<InduCredRatingVo>>()
                .ok(bondFinanceInfoService.findInduPor(induId, page, size, userid));
    }*/
    
    
    /*@ApiOperation(value = "行业分析-同行业主体展望负面-[zzl]")
    @RequestMapping(value = "/indus/porRating", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<Page<InduCredRatingVo>> findInduPor( //@RequestBody ListParam<Long> induIds,
            @ApiParam(name = "induIds[]", value = "行业ids,在实际代码中参数为induIds即可", required = true) @RequestParam("induIds[]") Long[] induIds,
            @ApiParam(name = "page", value = "第几页", required = true) @RequestParam int page,
            @ApiParam(name = "size", value = "每页返回行数", required = true) @RequestParam int size,
            @RequestHeader(name="userid" ,required = false) Long userid) {
        return new JsonResult<Page<InduCredRatingVo>>()
                .ok(bondFinanceInfoService.findInduPor(Arrays.asList(induIds), page, size, userid));
    }*/
    
    

    /*@ApiOperation(value = "行业分析- 行业主体评级分布对比-[zzl]")
    @RequestMapping(value = "/indus/issInduRating", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<IssInduRatingCompareVo>> findIssInduRatingCompare(
            @ApiParam(name = "issInduId", value = "基准行业id", required = true) @RequestParam() String issInduId,
            @ApiParam(name = "compareInduId", value = "对比行业id,全部为0或者不传该参数", required = false) @RequestParam String compareInduId,
            @RequestHeader(name="userid" ,required = false) Long userid) {
        List<Long> iss = SafeUtils.strToArrLong(issInduId);
        List<Long> com = SafeUtils.strToArrLong(compareInduId);
        return new JsonResult<List<IssInduRatingCompareVo>>()
                .ok(bondFinanceInfoService.findIssInduRatingCompare(iss, com, userid));
    }*/
    
    /*@ApiOperation(value = "行业分析- 行业主体量风险等级分布对比-[zzl]")
    @RequestMapping(value = "/indus/induPdStat", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<PdInduCompareVo>> findInduPdStat(
            @ApiParam(name = "issInduId", value = "基准行业id", required = true) @RequestParam String issInduId,
            @ApiParam(name = "compareInduId", value = "对比行业id,全部为0或者不传该参数", required = true) @RequestParam String compareInduId,
            @RequestHeader(name="userid" ,required = false) Long userid) {
        List<Long> iss = SafeUtils.strToArrLong(issInduId);
        List<Long> com = SafeUtils.strToArrLong(compareInduId);
        logger.info("参数---->{iss:"+iss + ",com" + com +"}");
        return new JsonResult<List<PdInduCompareVo>>().ok(bondFinanceInfoService.findInduPdStat(iss, com, userid));
    }*/
    
    @ApiOperation(value = "信用债-债券详情-重点信息-板块6-[zzl]")
    @RequestMapping(value = "/iss/{issId}/indicators", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<FinanceInfoQuarter>> issInduBreachProbability3(
            @ApiParam(name = "issId", value = "主体|发行人id", required = true) @PathVariable Long issId,
            @RequestHeader(name="userid" ,required = false) Long userid) {
        return new JsonResult<List<FinanceInfoQuarter>>()
                .ok(bondFinanceInfoService.findFinanceInfoQuarters(issId, userid));
    }
    
    
    @ApiOperation(value = "主体分析-违约概率分析-重点风险指标揭示-[zzl]")
    @RequestMapping(value = "/iss/{issId}/issIndicators", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<IssIndicatorsVo>> issIndicators(
            @ApiParam(name = "issId", value = "主体|发行人id", required = true) @PathVariable Long issId
            ) {
        return new JsonResult<List<IssIndicatorsVo>>()
                .ok(bondFinanceInfoService.findIssIndicators(issId));
    }
    
    
    @ApiOperation(value = "主体分析-违约概率分析-重点风险指标揭示曲线图-[zzl]")
    @RequestMapping(value = "/iss/{issId}/issIndicatorsPds", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<IssIndicatorPdVo>> issIndicators(
            @ApiParam(name = "issId", value = "主体|发行人id", required = true) @PathVariable Long issId,
            @ApiParam(name = "category", value = "指标类别", required = true) @RequestParam String category,
            @ApiParam(name = "field", value = "具体指标", required = true) @RequestParam String field
            ) {
        return new JsonResult<List<IssIndicatorPdVo>>()
                .ok(bondFinanceInfoService.findIssIndicators(issId, category, field));
    }
    
    @ApiOperation(value = "主体分析-违约概率分析-重点风险指标揭示曲线图分组-[zzl]")
    @RequestMapping(value = "/iss/{issId}/issIndicatorsGroup", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<IndicatorGroupVo>> issIndicatorsGroup(
            @ApiParam(name = "issId", value = "主体|发行人id", required = true) @PathVariable Long issId
            ) {
        return new JsonResult<List<IndicatorGroupVo>>()
                .ok(bondFinanceInfoService.findIssIndicatorGroup(issId));
    }
    
    @ApiOperation(value = "主体量化风险等级变化图")
    @RequestMapping(value = "/pd/{comUniCode}/pds", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<Map<String,Object>> findPdHist(
            @ApiParam(name = "comUniCode", value = "公司id", required = true) @RequestParam Long comUniCode,
            @RequestHeader(name="userid" ,required = false) Long userid) {
    		return new JsonResult<Map<String,Object>>().ok(comInfoService.findPdHist(comUniCode, userid));
    }
    
    @ApiOperation(value = "获取主体指标的分位值")
    @RequestMapping(value = "{issuerId}/indicators/quartile", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<Map<String,Integer > > quartileMap(
    		@RequestHeader(name="userid" ,required = false) Long userid,
    		@ApiParam(name = "issuerId",value = "发行人id") @PathVariable("issuerId") Long issuerId,
    		@ApiParam(name = "finDate",value = "财报日期") @RequestParam("finDate") @DateTimeFormat(pattern = "yyyy-MM-dd")Date finDate,
    		@ApiParam(name = "indicatorType",value = "指标类型") @RequestParam("indicatorType") Integer indicatorType,
    		@ApiParam(name="fields") @RequestParam(required = false,name="fields[]")String[] fields) throws Exception {
    	Map<String,Integer > result=  bondFinanceInfoService.quartileMap(issuerId, finDate, fields == null ? null : Arrays.asList(fields), indicatorType);
        return new JsonResult<Map<String,Integer > >().ok(result);
    }
}




