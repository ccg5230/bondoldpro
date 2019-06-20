package com.innodealing.controller;

import com.innodealing.domain.vo.BondRadarMsgVO;
import com.innodealing.domain.vo.BondSimpleNotiMsgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.innodealing.domain.JsonResult;
import com.innodealing.service.BondPortfolioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

@Api(description = "债券APP中投资组合接口")
@RestController
@RequestMapping("api/bond/app/")
public class BondAppPortfolioController {
	
	@Autowired
	private BondPortfolioService bondPortfolioService;
	
	//消息相关
    @ApiOperation(value = "[消息] 修改关注投组下所有的债券消息为已读")
    @RequestMapping(value = "/message/user/{userId}/group/{groupId}/readstatus", method = RequestMethod.PUT, produces = "application/json")
    public JsonResult<Long> updateGroupMsgReadStatus(
            @ApiParam(name = "groupId", value = "投组Id", required = true) @PathVariable Long groupId) {
        Long result = bondPortfolioService.updateGroupMsgReadStatus(null, groupId);
        if (result == null) {
            return new JsonResult<>("-1", "修改债券消息已读失败", null);
        }
		return new JsonResult<Long>().ok(result);
    }

    @ApiOperation(value = "获取用户雷达类型列表(雷达消息概况)")
    @RequestMapping(value = "/user/{userId}/favorite/msg", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<BondRadarMsgVO>> getAllFavoriteMsgList(
            @ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId) {
        List<BondRadarMsgVO> resultList = bondPortfolioService.getAllFavoriteMsg(userId);
        if (resultList == null) {
            return new JsonResult<>("-1", "获取消息列表为空", null);
        }
        return new JsonResult<List<BondRadarMsgVO>>().ok(resultList);
    }

    @ApiOperation(value = "获取用户雷达类型消息列表")
    @RequestMapping(value = "/user/{userId}/favorite/msg/{radarType}", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<Page<BondSimpleNotiMsgVO>> getAllFavoriteMsgListByType(
            @ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
            @ApiParam(name = "radarType", value = "消息类型[1-存续;2-评级;3-舆情;4-价格;5-指标;6-其他]", required = true) @PathVariable Long radarType,
            @ApiParam(name = "pageIndex", value = "页数") @RequestParam(defaultValue = "1") Integer pageIndex,
            @ApiParam(name = "pageSize", value = "每页显示数量") @RequestParam(defaultValue = "20") Integer pageSize) {
        Page<BondSimpleNotiMsgVO> resultList = bondPortfolioService.getAllFavoriteMsgByRadar(userId,
                radarType, pageIndex - 1, pageSize);
        if (resultList == null) {
            return new JsonResult<>("-1", "获取消息列表为空", null);
        }
        return new JsonResult<Page<BondSimpleNotiMsgVO>>().ok(resultList);
    }

    @ApiOperation(value = "获取用户所有未读消息列表")
    @RequestMapping(value = "/user/{userId}/favorite/unread/msg", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<Page<BondSimpleNotiMsgVO>> getAllFavoriteUnreadMsg(
            @ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
            @ApiParam(name = "pageIndex", value = "页数") @RequestParam(defaultValue = "1") Integer pageIndex,
            @ApiParam(name = "pageSize", value = "每页显示数量") @RequestParam(defaultValue = "20") Integer pageSize) {
        Page<BondSimpleNotiMsgVO> result = bondPortfolioService.getAllFavoriteUnreadMsg(userId, pageIndex - 1, pageSize);
        if (result == null) {
            return new JsonResult<>("-1", "获取用户未读消息列表失败", null);
        }
        return new JsonResult<Page<BondSimpleNotiMsgVO>>().ok(result);
    }

    @ApiOperation(value = "修改用户所有未读消息为已读(根据雷达类型)")
    @RequestMapping(value = "/user/{userId}/favorite/msg/{radarType}/readstatus", method = RequestMethod.PUT, produces = "application/json")
    public JsonResult<Boolean> updateAllFavoriteMsgReadStatus(
            @ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
            @ApiParam(name = "radarType", value = "消息类型[0-所有;1-存续;2-评级;3-舆情;4-价格;5-指标;6-其他;]", required = true) @PathVariable Long radarType) {
        boolean result = bondPortfolioService.updateAllFavoriteMsgReadStatus(userId, radarType);
        if (!result) {
            return new JsonResult<>("-1", "修改未读消息已读失败", null);
        }
        return new JsonResult<Boolean>().ok(result);
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	//投组或操作相关
	
	
	
}
