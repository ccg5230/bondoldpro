package com.innodealing.controller;

import com.innodealing.bond.param.BondAbnormalPriceFilterReq;
import com.innodealing.bond.param.BondMailReq;
import com.innodealing.domain.JsonResult;
import com.innodealing.service.BondFavoriteDataService;
import com.innodealing.service.BondMailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/bond/tasks")
public class BondMailController {
	
	private @Autowired
    BondMailService bondMailService;

    @ApiOperation(value = "直接发送text邮件")
    @RequestMapping(value = "/mail/text", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> sendText(@RequestBody BondMailReq req) {
        return new JsonResult<String>().ok(bondMailService.sendText(req));
    }

    @ApiOperation(value = "根据模板发送html邮件")
    @RequestMapping(value = "/mail/template", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> sendHtmlByTemplate(@RequestBody BondMailReq req) {
        return new JsonResult<String>().ok(bondMailService.sendHtmlByTemplate(req));
    }
}
