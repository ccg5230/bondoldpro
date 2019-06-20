package com.innodealing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.bond.param.BaseQuoteParam;
import com.innodealing.bond.param.BaseQuoteResendParam;
import com.innodealing.bond.param.BondQuoteAddParam;
import com.innodealing.bond.param.BondQuoteStatusParam;
import com.innodealing.bond.param.BondQuoteTextParam;
import com.innodealing.bond.service.BondQuoteService;
import com.innodealing.bond.vo.quote.BondQuoteInfoVo;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.dm.bond.BondQuote;
import com.innodealing.model.mongo.dm.BondBestQuoteDoc;
import com.innodealing.model.mongo.dm.BondDealDataDoc;
import com.innodealing.model.mongo.dm.BondQuoteDoc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author stephen.ma
 * @date 2016年9月1日
 * @clasename BondQuoteController.java
 * @decription TODO
 */
@Api(description = "债券需求和报价数据处理")
@RestController
@RequestMapping("api/bond/")
public class BondQuoteController extends BaseController {
	
	private @Autowired BondQuoteService bondQuoteService;
	
	@ApiOperation(value="添加需求")
    @RequestMapping(value = "/users/{userId}/demand", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<List<Long>> addDemand(@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Long userId,
    		@RequestBody BondQuoteAddParam bondQuoteAddParam){
		
		return new JsonResult<List<Long>>().ok(bondQuoteService.addDemand(userId, bondQuoteAddParam));
		
	}
	
	@ApiOperation(value="添加需求文本，返回解析结果")
    @RequestMapping(value = "/users/{userId}/demandtext", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<List<BaseQuoteParam>> addDemandText(@ApiParam(name = "userId", value = "用户Id", required = true) @PathVariable Long userId,
    		@ApiParam(name = "bondQuoteTextParam", value = "需求文本", required = true) @RequestBody BondQuoteTextParam bondQuoteTextParam){
	
		return new JsonResult<List<BaseQuoteParam>>().ok(bondQuoteService.addDemandText(bondQuoteTextParam.getPostfrom(), userId, bondQuoteTextParam.getContent()));
		
	}
	
	@ApiOperation(value="今日最优报价")
    @RequestMapping(value = "/bonds/{bondId}/quotes/todaybest", method = RequestMethod.GET, produces = "application/json")
	 public JsonResult<BondBestQuoteDoc> findBestOffer(@ApiParam(name = "bondId", value = "债券的BondID/BondUniCode", required = true) @PathVariable Long bondId){
		
		return new JsonResult<BondBestQuoteDoc>().ok(bondQuoteService.findBestOffer(bondId));
	}
	
	@ApiOperation(value="获取今日报价/历史报价数据")
    @RequestMapping(value = "/users/{userId}/bonds/{bondId}/quotedata", method = RequestMethod.GET, produces = "application/json")
	 public JsonResult<Page<BondQuoteDoc>> findQuotes(@ApiParam(name = "userId", value = "用户Id", required = true) @PathVariable Long userId,
			 @ApiParam(name = "bondId", value = "债券的BondID/BondUniCode", required = true) @PathVariable Long bondId,
			 @ApiParam(name = "istoday", value = "1 今日报价，0 历史报价", required = true) @RequestParam Integer istoday,
			 @ApiParam(name = "pageIndex", value = "页数,数字:1", required = true) @RequestParam Integer pageIndex,
			 @ApiParam(name = "pageSize", value = "每页显示数量,数字:20", required = true) @RequestParam Integer pageSize){
		
		return new JsonResult<Page<BondQuoteDoc>>().ok(bondQuoteService.findQuotes(userId, bondId, istoday, pageIndex-1, pageSize));
	}
	
	@ApiOperation(value="获取债券下的报价数据")
    @RequestMapping(value = "/users/{userId}/bonds/{bondUniCode}/quotes", method = RequestMethod.GET, produces = "application/json")
	 public JsonResult<List<BondQuoteDoc>> findQuotesByBondUniCode(@ApiParam(name = "userId", value = "用户Id", required = true) @PathVariable Long userId,
			 @ApiParam(name = "bondUniCode", value = "债券的BondUniCode", required = true) @PathVariable Long bondUniCode,
			 @ApiParam(name = "side", value = "1收券-bid，2出券-ofr", required = true) @RequestParam Integer side,
			 @ApiParam(name = "pageIndex", value = "页数", required = true) @RequestParam(defaultValue="1") Integer pageIndex,
			 @ApiParam(name = "pageSize", value = "每页显示数量", required = true) @RequestParam(defaultValue="20") Integer pageSize){
		
		return new JsonResult<List<BondQuoteDoc>>().ok(bondQuoteService.findQuotesByBondUniCode(userId, bondUniCode, side, pageIndex-1, pageSize));
	}
	
	
	@ApiOperation(value="需求管理下操作（成交和撤销）债券报价")
    @RequestMapping(value = "/quotes/{quoteId}/handler", method = RequestMethod.POST, produces = "application/json")
	 public JsonResult<Integer> handleQuoteStatus(@ApiParam(name = "quoteId", value = "债券报价的ID", required = true) @PathVariable Long quoteId,
			 @ApiParam(name = "action", value = "状态操作（2=成交,99=撤销）", required = true) @RequestBody BondQuoteStatusParam params){
		
		return new JsonResult<Integer>().ok(bondQuoteService.handleQuoteStatus(quoteId, params));
	}
	
	@ApiOperation(value="需求管理下重发债券报价")
    @RequestMapping(value = "/quotes/{quoteId}/resend", method = RequestMethod.POST, produces = "application/json")
	 public JsonResult<Long> resendQuote(@ApiParam(name = "quoteId", value = "债券报价的ID", required = true) @PathVariable Long quoteId,
			 @RequestBody BaseQuoteResendParam baseQuoteResendParam){
		
		return new JsonResult<Long>().ok(bondQuoteService.resendQuote(quoteId, baseQuoteResendParam));
	}
	
	@ApiOperation(value="需求管理下查看自己的报价")
    @RequestMapping(value = "/users/{userId}/ownquotes", method = RequestMethod.GET, produces = "application/json")
	 public JsonResult<Page<BondQuoteInfoVo>> findOwnquotes(@ApiParam(name = "userId", value = "用户Id", required = true) @PathVariable Long userId,
			 @ApiParam(name = "status", value = "状态，全部 0,1有效, 2 已成交, 99 取消") @RequestParam Integer status,
			 @ApiParam(name = "page", value = "页数", required = true) @RequestParam(defaultValue="1") Integer page,
			 @ApiParam(name = "limit", value = "每页显示数量", required = true) @RequestParam(defaultValue="20") Integer limit){
		
		return new JsonResult<Page<BondQuoteInfoVo>>().ok(bondQuoteService.findOwnquotes(userId, status, page-1, limit));
	}
	
	@ApiOperation(value="根据老系统中的quoteId调用的新数据库中的债券报价的ID")
    @RequestMapping(value = "/quotes/quote/quoteMappingId", method = RequestMethod.GET, produces = "application/json")
	 public JsonResult<Long> findBondQuoteId(
			 @ApiParam(name = "oldQuoteId", value = "normalize_data中的id", required = true) @RequestParam String oldQuoteId){
		Long newQuoteId = 0L;
		BondQuote bondQuote = bondQuoteService.findBondQuoteByQuoteId(oldQuoteId);
		if (null != bondQuote) {
			newQuoteId = bondQuote.getId();
		}
		return new JsonResult<Long>().ok(newQuoteId);
	}
	
	@ApiOperation(value="获取债券下的成交价数据")
    @RequestMapping(value = "/users/{userId}/bonds/{bondUniCode}/deals", method = RequestMethod.GET, produces = "application/json")
	 public JsonResult<List<BondDealDataDoc>> getDealDataByBondUniCode(@ApiParam(name = "userId", value = "用户Id", required = true) @PathVariable Long userId,
			 @ApiParam(name = "bondUniCode", value = "债券的BondUniCode", required = true) @PathVariable Long bondUniCode,
			 @ApiParam(name = "pageIndex", value = "页数", required = true) @RequestParam(defaultValue="1") Integer pageIndex,
			 @ApiParam(name = "pageSize", value = "每页显示数量", required = true) @RequestParam(defaultValue="20") Integer pageSize){
		return new JsonResult<List<BondDealDataDoc>>().ok(bondQuoteService.getDealsByBondUniCode(userId, bondUniCode, pageIndex-1, pageSize));
	}
}
