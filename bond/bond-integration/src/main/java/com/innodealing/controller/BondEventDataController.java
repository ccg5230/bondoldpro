package com.innodealing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.bond.param.DefaultBondParam;
import com.innodealing.domain.JsonResult;
import com.innodealing.service.BondNotificationMsgService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "债券事件消息处理-内部系统调用")
@RestController
@RequestMapping("api/bond/event")
public class BondEventDataController {
	
	private @Autowired BondNotificationMsgService  bondNotificationMsgService;
	
	@ApiOperation(value="管理后台添加某债券的违约事件")
    @RequestMapping(value = "/defaultBond", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> saveDefaultBond(@RequestBody DefaultBondParam params){
		
		return new JsonResult<String>().ok(bondNotificationMsgService.saveDefaultBondMsg(params));
	}

}
