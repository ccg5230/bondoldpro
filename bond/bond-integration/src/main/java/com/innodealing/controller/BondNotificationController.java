package com.innodealing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.domain.JsonResult;
import com.innodealing.service.BondNotificationMsgService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "债券关注提醒消息数据构建")
@RestController
@RequestMapping("api/bond/tasks/notification")
public class BondNotificationController {
    
    private final static Logger logger = LoggerFactory.getLogger(BondNotificationController.class);
    
    private @Autowired BondNotificationMsgService bondNotificationMsgService;
    
    @ApiOperation(value = "构建存续变动消息")
    @RequestMapping(value = "/maturity", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<Long> buildMaturityData(){
    	
        return new JsonResult<Long>().ok(bondNotificationMsgService.saveBondMaturityDtoMsg());
    }
    
    @ApiOperation(value = "构建存续行权消息")
    @RequestMapping(value = "/exerData", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<Long> builExerData(){
    	
        return new JsonResult<Long>().ok(bondNotificationMsgService.saveBondExerData());
    }

    @ApiOperation(value = "构建财务预警数据")
    @RequestMapping(value = "/financealertData", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> buildFinanceAlertData(){
    
    	 return new JsonResult<String>().ok(bondNotificationMsgService.saveFinancialAlertInfo());
    }
    /*
    @ApiOperation(value = "构建财务预警消息")
    @RequestMapping(value = "/financealertMsg", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> buildFinanceAlertMsg(){
    
    	 return new JsonResult<String>().ok(bondNotificationMsgService.saveFinancialAlertMsg());
    }
    */
    @ApiOperation(value = "构建债项评级/展望的备份数据")
    @RequestMapping(value = "/bondcredData", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> buildBondcredData(){
    
    	 return new JsonResult<String>().ok(bondNotificationMsgService.saveBondcredData());
    }

    @ApiOperation(value = "构建债项评级/展望消息")
    @RequestMapping(value = "/bondcredMsg", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> buildBondcredMsg(){
    
    	 return new JsonResult<String>().ok(bondNotificationMsgService.saveBondcredMsg());
    }
    
    
    @ApiOperation(value = "构建主体评级/展望的备份数据")
    @RequestMapping(value = "/isscredData", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> buildIsscredData(){
    
    	 return new JsonResult<String>().ok(bondNotificationMsgService.saveIsscredData());
    }

    @ApiOperation(value = "构建主体评级/展望消息")
    @RequestMapping(value = "/isscredMsg", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> buildIsscredMsg(){
    
    	 return new JsonResult<String>().ok(bondNotificationMsgService.saveIsscredMsg());
    }
    
    @ApiOperation(value = "构建主体量化风险等级的备份数据")
    @RequestMapping(value = "/issPdData", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> buildIssPdData(){
    
    	 return new JsonResult<String>().ok(bondNotificationMsgService.saveIssPdData());
    }
    
    @ApiOperation(value = "构建主体量化风险等级消息")
    @RequestMapping(value = "/issPdMsg", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> buildIssPdMsg(){
    
    	 return new JsonResult<String>().ok(bondNotificationMsgService.saveIssPdMsg());
    }
    
    
    @ApiOperation(value = "构建中债隐含评级消息")
    @RequestMapping(value = "/impliedRatingMsg", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> buildBondImpliedRatingMsg(){
    
    	 return new JsonResult<String>().ok(bondNotificationMsgService.saveImpliedRatingMsg());
    }
    
}
