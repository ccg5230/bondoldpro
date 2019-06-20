package com.innodealing.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.bond.param.BondUserIndu;
import com.innodealing.bond.service.BondInduService;
import com.innodealing.bond.vo.indu.BondComInfoDetailVo;
import com.innodealing.bond.vo.indu.InduVo;
import com.innodealing.bond.vo.indu.Indus;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.mongo.dm.BondComInfoRatingInduDoc;
import com.innodealing.model.mongo.dm.BondPdRankDoc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "债券-行业信息")
@RestController
@RequestMapping("api/bond")
public class BondInduController extends BaseController{

    @Autowired
    private BondInduService bondInduService;


    @ApiOperation(value = "所有子行业信息-[zzl]")
    @RequestMapping(value = "/indus/subIndus", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<InduVo>> findAllSubIndu() {
        return new JsonResult<List<InduVo>>().ok(bondInduService.findAllSubIndu());
    }
    
    @ApiOperation(value = "更新用户使用的行业分类类型")
    @RequestMapping(value = "/users/{userId}/induClass", method = RequestMethod.PUT, produces = "application/json")
    public JsonResult<String> updateUserInduType(
            @ApiParam(name = "userId", value = "用户id", required = true) @PathVariable Long userId,
            @RequestBody BondUserIndu induClass 
            ) {
        bondInduService.updateInduClassByUser(userId, induClass.getInduClass());
        return new JsonResult<String>().ok("success");
    }
    
    @ApiOperation(value = "获取用户使用的行业分类类型")
    @RequestMapping(value = "/users/{userId}/induClass", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<Integer> getUserInduType(
            @ApiParam(name = "userId", value = "用户id", required = true) @PathVariable Long userId
            ) {
        return new JsonResult<Integer>().ok(bondInduService.findInduClassByUser(userId));
    }
    
    @ApiOperation(value = "获取用户使用的行业分类")
    @RequestMapping(value = "/users/{userId}/bondIndus", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<Indus>> getUserIndus(
            @ApiParam(name = "userId", value = "用户id", required = true) @PathVariable Long userId
            ) {
        return new JsonResult<List<Indus>>().ok(bondInduService.findInduMetaInfo(userId));
    }
    
    
    @ApiOperation(value = "行业分析")
    @RequestMapping(value = "/indus/pdDetails", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<Page<BondPdRankDoc>> findInduRatingDown(
            @ApiParam(name = "induIds[]", value = "行业ids,在实际代码中参数为induIds即可", required = true) @RequestParam("induIds[]") Long[] induIds,
            @ApiParam(name = "page", value = "第几页", required = true) @RequestParam int page,
            @ApiParam(name = "size", value = "每页返回行数", required = true) @RequestParam int size,
            @ApiParam(name = "sort", value = "排序规则", required = false) @RequestParam(defaultValue="rateWritDate:desc") String sort,
            @ApiParam(name = "type", value = "类型[1-主体量化风险等级排行榜;2-同行业主体评级下降;3-同行业主体展望负面;4-同行业主体展望正面;5-同行业主体量化风险等级下降;6-同行业主体量化风险等级上升;7-同行业主体关注点;]", required = true) @RequestParam(defaultValue="1") Integer type,
            @ApiParam(name = "munInvest", value = "非城投1是", required = true) @RequestParam(defaultValue = "0") Integer munInvest,
            @RequestHeader(name="userid" ,required = false) Long userid) {
        
        return new JsonResult<Page<BondPdRankDoc>>()
                .ok(bondInduService.findInduRatingDown(Arrays.asList(induIds), page, size, sort, type, munInvest,userid));
    }
    
	@ApiOperation(value = "获取风险量化等级详细")
	@RequestMapping(value = "/getPdRankDetail", method = RequestMethod.POST, produces = "application/json")
	public 	JsonResult<BondPdRankDoc> getPdRankDetail(
			@ApiParam(name = "key", value = "关键字,债券简称、全称、代码，企业简称、全称、代码", required = true) @RequestParam String key) {
		return new JsonResult<BondPdRankDoc>()
				.ok(bondInduService.getPdRankDetail(key));
	}
    
    @ApiOperation(value = "主体量化分析等级分析-->分布图")
    @RequestMapping(value = "/indus/pdView", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<List<BondComInfoRatingInduDoc>> findInduRatingView(
            @ApiParam(name = "induIds[]", value = "行业ids,在实际代码中参数为induIds即可,不传则代表全行业", required = false) @RequestParam(required=false,value="induIds[]") Long[] induIds,
            @RequestHeader(name="userid" ,required = false) Long userid) {
        
        return new JsonResult<List<BondComInfoRatingInduDoc>>()
                .ok(bondInduService.findInduRatingView(induIds!=null?Arrays.asList(induIds):null, userid));
    }
    
    @ApiOperation(value = "主体量化分析等级分析-->分布图-->根据日期条件筛选")
    @RequestMapping(value = "/indus/pdViewByDate", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<List<BondComInfoRatingInduDoc>> findInduRatingByDateView(
    		@ApiParam(name = "induIds[]", value = "行业ids,在实际代码中参数为induIds即可,不传则代表全行业", required = false) @RequestParam(required=false,value="induIds[]") Long[] induIds,
    		@RequestHeader(name="userid" ,required = false) Long userid,
    		@ApiParam(name = "year", value = "选定的年份[yyyy]", required = true) @RequestParam(required = true) Long year,
			@ApiParam(name = "quarter", value = "选定的季度[1/2/3/4]", required = true) @RequestParam(required = true) Long quarter) {
        
        return new JsonResult<List<BondComInfoRatingInduDoc>>()
                .ok(bondInduService.findInduRatingViewByDate(induIds!=null?Arrays.asList(induIds):null, userid,year,quarter, false));
    }
    
    @ApiOperation(value = "是否是自定义行业")
    @RequestMapping(value = "/indus/isInduInstitution", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<Boolean> isInduInstitution(
            @RequestHeader(name="userid" ,required = false) Long userid) {
        return new JsonResult<Boolean>()
                .ok(bondInduService.isInduInstitution(userid));
    }
    
	@ApiOperation(value = "雷猴-关键字匹配")
	@RequestMapping(value = "/getComChiNameBykey", method = RequestMethod.POST, produces = "application/json")
	public 	JsonResult<Map<String,Object>> getComChiNameBykey(
			@ApiParam(name = "key", value = "关键字,债券简称、全称、代码，企业简称、全称、代码", required = true) @RequestParam String key) {
		return new JsonResult<Map<String,Object>>()
				.ok(bondInduService.getComChiNameBykey(key));
	}
	
    
	@ApiOperation(value = "雷猴-发行人详细信息")
	@RequestMapping(value = "/getComDetail", method = RequestMethod.POST, produces = "application/json")
	public 	JsonResult<BondComInfoDetailVo> getComDetail(
			@ApiParam(name = "comUniCode", value = "发行人唯一标示", required = true) @RequestParam Long comUniCode) {
		return new JsonResult<BondComInfoDetailVo>()
				.ok(bondInduService.getComDetail(comUniCode));
	}
    
}
