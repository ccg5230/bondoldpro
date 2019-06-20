package com.innodealing.controller;

import com.innodealing.domain.JsonResult;
import com.innodealing.service.BondFavoriteDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(description = "我的关注的数据构建")
@RestController
@RequestMapping("api/bond/tasks")
public class BondFavoriteDataController {
	
	private @Autowired BondFavoriteDataService bondFavoriteDataService;
	
	@ApiOperation(value = "出来BondFavorite原先预留的脏数据的问题，执行完该步骤之后，需要执行buildBondFavoriteDocData进行同步数据")
	@RequestMapping(value = "/bondFavorite/dirtyDataClean", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> cleanBondFavoriteDirtydata() {
		
		return new JsonResult<String>().ok(bondFavoriteDataService.cleanBondFavoriteDirtydata());
	}

	@ApiOperation(value = "构建BondFavorite数据到MongoDB")
	@RequestMapping(value = "/bondFavorite/integration", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> buildBondFavoriteDocData() {
		
		return new JsonResult<String>().ok(bondFavoriteDataService.buildBondFavoriteDoc());
	}
	

	@ApiOperation(value = "构建BondFavoriteGroup数据到MongoDB")
	@RequestMapping(value = "/bondFavoriteGroup/integration", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> buildBondFavoriteGroupDocData() {
		
		return new JsonResult<String>().ok(bondFavoriteDataService.buildBondFavoriteGroupDoc());
	}
	

    @ApiOperation(value = "构建notification数据到MongoDB")
    @RequestMapping(value = "/bondNotificationMsg/integration", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> buildBondNotificationData(
            @ApiParam(name = "gte", value = "起始id", required = true) @RequestParam Long gte,
            @ApiParam(name = "lte", value = "终止id", required = false) @RequestParam Long lte
            ) {
        
        return new JsonResult<String>().ok(bondFavoriteDataService.buildBondNotificationMsgDoc(gte, lte));
    }
    
    
    @ApiOperation(value = "更新notification中的舆情数据")
    @RequestMapping(value = "/bondNotificationMsg/bondSentiment/update", method = RequestMethod.PUT, produces = "application/json")
    public JsonResult<String> updateBondSentimentMsgData(
            @ApiParam(name = "gte", value = "起始id", required = true) @RequestParam Long gte,
            @ApiParam(name = "lte", value = "终止id", required = true) @RequestParam Long lte) {
        
        return new JsonResult<String>().ok(bondFavoriteDataService.updateBondSentimentMsgData(gte, lte));
    }

    @ApiOperation(value = "重构投组消息类型:1.逻辑删除没有关注债券的“我的持仓”")
    @RequestMapping(value = "/bondFavoriteGroup/reconsitution/default/useless/{encryptKey}", method = RequestMethod.DELETE, produces = "application/json")
    public JsonResult<String> reconsitutionUselessDefaultGroup(
            @ApiParam(name = "encryptKey", value = "密钥", required = true) @PathVariable String encryptKey) {
        return new JsonResult<String>().ok(bondFavoriteDataService.removeUselessDefaultGroup(encryptKey));
    }

    @ApiOperation(value = "重构投组消息类型:2.根据所有有效投组选定的通知类型生成新的提醒条件入库+缓存")
    @RequestMapping(value = "/bondFavoriteGroup/reconsitution/allValid/{encryptKey}", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> reconsitutionGroup(
            @ApiParam(name = "encryptKey", value = "密钥", required = true) @PathVariable String encryptKey) {
        return new JsonResult<String>().ok(bondFavoriteDataService.reconsitutionAllValidGroup(encryptKey));
    }

    @ApiOperation(value = "重构投组消息类型:3.重新映射Mysql数据到mongo缓存")
    @RequestMapping(value = "/bondFavoriteGroup/reconsitution/msg/{encryptKey}", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> reconsitutionMsgType(
            @ApiParam(name = "encryptKey", value = "密钥", required = true) @PathVariable String encryptKey) {
        return new JsonResult<String>().ok(bondFavoriteDataService.reconsitutionTodayMsg(encryptKey));
    }

    @ApiOperation(value = "更新投组提醒条件9+18")
    @RequestMapping(value = "/bondFavoriteGroup/radar27/{encryptKey}", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> updateRadar27FromMapping(
            @ApiParam(name = "encryptKey", value = "密钥", required = true) @PathVariable String encryptKey) {
        return new JsonResult<String>().ok(bondFavoriteDataService.updateRadar27FromMapping(encryptKey));
    }

    @ApiOperation(value = "更新投组提醒条件19")
    @RequestMapping(value = "/bondFavoriteGroup/radar19/{encryptKey}", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> updateRadar19FromMapping(
            @ApiParam(name = "encryptKey", value = "密钥", required = true) @PathVariable String encryptKey) {
        return new JsonResult<String>().ok(bondFavoriteDataService.updateRadar19FromMapping(encryptKey));
    }

    @ApiOperation(value = "更新投组提醒条件14+17")
    @RequestMapping(value = "/bondFavoriteGroup/radar31/{encryptKey}", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> updateRadar31FromMapping(
            @ApiParam(name = "encryptKey", value = "密钥", required = true) @PathVariable String encryptKey) {
        return new JsonResult<String>().ok(bondFavoriteDataService.updateRadar31FromMapping(encryptKey));
    }

    @ApiOperation(value = "[投组发邮件] 投组发邮件")
    @RequestMapping(value = "/bondNotificationMsg/email/{encryptKey}", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<String> sendCustomGroupEmails(
            @ApiParam(name = "userId", value = "用户Id[如果有值，只筛选该用户的投组]，为空则查询所有符合条件的投组") @RequestParam(required = false, defaultValue="0") Integer userId,
            @ApiParam(name = "encryptKey", value = "密钥", required = true) @PathVariable String encryptKey,
            @ApiParam(name = "inputStartDateStr", value = "消息开始时间[yyyy-MM-dd HH:mm:ss]，为空则前一天的08:30:00") @RequestParam(required = false) String inputStartDateStr,
            @ApiParam(name = "inputEndDateStr", value = "消息结束时间[yyyy-MM-dd HH:mm:ss]，为空则当天的08:30:00") @RequestParam(required = false) String inputEndDateStr,
            @ApiParam(name = "mailTo", value = "邮件发送对象[如果有值，所有邮件发送到该地址]，为空则发送给投组对应的用户") @RequestParam(required = false) String mailTo) {
        return new JsonResult<String>().ok(bondFavoriteDataService.sendCustomGroupEmails(encryptKey, userId, inputStartDateStr, inputEndDateStr, mailTo));
    }

    @ApiOperation(value = "[投组发邮件] 测试邮件通道")
    @RequestMapping(value = "/bondNotificationMsg/subscribe/email/test/{encryptKey}", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> testEmailChannel(
            @ApiParam(name = "encryptKey", value = "密钥", required = true) @PathVariable String encryptKey) {
        return new JsonResult<String>().ok(bondFavoriteDataService.testEmailChannel(encryptKey));
    }

    @ApiOperation(value = "[消息] 重新过滤核查mongodb中缓存的消息的已读状态")
    @RequestMapping(value = "/portfolio/msg/readStatus/mongodb/recheck", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<String> recheckMsgReadStatusCache() {
        return new JsonResult<String>().ok(bondFavoriteDataService.recheckMsgReadStatusCache());
    }

    @ApiOperation(value = "[数据查错] 去除历史投组中重复的关注债券，只保留最后一条")
    @RequestMapping(value = "/bondFavoriteGroup/favorite/duplicate/{encryptKey}", method = RequestMethod.DELETE, produces = "application/json")
    public JsonResult<String> deleteDuplicateFavorite(
            @ApiParam(name = "encryptKey", value = "密钥", required = true) @PathVariable String encryptKey) {
        return new JsonResult<String>().ok(bondFavoriteDataService.deleteDuplicateFavorite(encryptKey));
    }

    @ApiOperation(value = "[数据查错] 移除投组消息中groupId不正确的数据")
    @RequestMapping(value = "/portfolio/msg/invalid/remove", method = RequestMethod.DELETE, produces = "application/json")
    public JsonResult<String> removeMsgWithInvalidGroupId() {
        return new JsonResult<String>().ok(bondFavoriteDataService.removeMsgWithInvalidGroupId());
    }
}
