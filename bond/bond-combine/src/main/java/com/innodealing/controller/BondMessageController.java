package com.innodealing.controller;

import com.innodealing.domain.model.RestResponse;
import com.innodealing.domain.vo.BondRadarMsgVO;
import com.innodealing.domain.vo.BondSimpleNotiMsgVO;
import com.innodealing.service.BondCombineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "[APP]债券-消息接口")
@RestController
@RequestMapping("api/bond/message/")
public class BondMessageController {
    @Autowired
    private BondCombineService bondCombineService;

    @ApiOperation(value = "获取关注投组消息列表(按雷达类型过滤)")
    @RequestMapping(value = "/user/{userId}/group/{groupId}/msg/{radarType}", method = RequestMethod.GET, produces = "application/json")
    public RestResponse<Page<BondSimpleNotiMsgVO>> getGroupMsgListByType(
            @ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
            @ApiParam(name = "groupId", value = "投组Id", required = true) @PathVariable Integer groupId,
            @ApiParam(name = "radarType", value = "消息类型[0-所有;1-存续;2-评级;3-舆情;4-价格;5-指标;6-其他]", required = true) @PathVariable Long radarType,
            @ApiParam(name = "pageIndex", value = "页数") @RequestParam(defaultValue = "1") Integer pageIndex,
            @ApiParam(name = "pageSize", value = "每页显示数量") @RequestParam(defaultValue = "20") Integer pageSize) {
        Page<BondSimpleNotiMsgVO> resultList = bondCombineService.getGroupSimpleMsgWithRadarTypes(userId, groupId,
                radarType, pageIndex - 1, pageSize);
        if (resultList == null) {
            return RestResponse.Fail("获取消息列表为空", null);
        }
        return RestResponse.Success(resultList);
    }

    @ApiOperation(value = "获取关注债券消息列表(按雷达类型过滤)")
    @RequestMapping(value = "/user/{userId}/favorite/{favoriteId}/msg/{radarType}", method = RequestMethod.GET, produces = "application/json")
    public RestResponse<Page<BondSimpleNotiMsgVO>> getFavoriteMsgListByType(
            @ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
            @ApiParam(name = "favoriteId", value = "关注债券Id", required = true) @PathVariable Integer favoriteId,
            @ApiParam(name = "radarType", value = "消息类型[0-所有;1-存续;2-评级;3-舆情;4-价格;5-指标;6-其他]", required = true) @PathVariable Long radarType,
            @ApiParam(name = "pageIndex", value = "页数") @RequestParam(defaultValue = "1") Integer pageIndex,
            @ApiParam(name = "pageSize", value = "每页显示数量") @RequestParam(defaultValue = "20") Integer pageSize) {
        Page<BondSimpleNotiMsgVO> resultList = bondCombineService.getFavoriteSimpleMsgWithRadarTypes(userId, favoriteId,
                radarType, pageIndex - 1, pageSize);
        if (resultList == null) {
            return RestResponse.Fail("获取消息列表为空", null);
        }
        return RestResponse.Success(resultList);
    }

    @ApiOperation(value = "获取用户所有消息列表(按雷达类型分组)")
    @RequestMapping(value = "/user/{userId}/favorite/msg", method = RequestMethod.GET, produces = "application/json")
    public RestResponse<List<BondRadarMsgVO>> getAllFavoriteMsgList(
            @ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId) {
        List<BondRadarMsgVO> resultList = bondCombineService.getAllFavoriteMsg(userId);
        if (resultList == null) {
            return RestResponse.Fail("获取消息列表为空", null);
        }
        return RestResponse.Success(resultList);
    }

    @ApiOperation(value = "获取用户所有消息列表(按雷达类型过滤)")
    @RequestMapping(value = "/user/{userId}/favorite/msg/{radarType}", method = RequestMethod.GET, produces = "application/json")
    public RestResponse<Page<BondSimpleNotiMsgVO>> getAllFavoriteMsgListByType(
            @ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
            @ApiParam(name = "radarType", value = "消息类型[1-存续;2-评级;3-舆情;4-价格;5-指标;6-其他]", required = true) @PathVariable Long radarType,
            @ApiParam(name = "pageIndex", value = "页数") @RequestParam(defaultValue = "1") Integer pageIndex,
            @ApiParam(name = "pageSize", value = "每页显示数量") @RequestParam(defaultValue = "20") Integer pageSize) {
        Page<BondSimpleNotiMsgVO> resultList = bondCombineService.getAllFavoriteMsgByRadar(userId,
                radarType, pageIndex - 1, pageSize);
        if (resultList == null) {
            return RestResponse.Fail("获取消息列表为空", null);
        }
        return RestResponse.Success(resultList);
    }

    @ApiOperation(value = "获取用户的未读消息数")
    @RequestMapping(value = "/user/{userId}/newMessageCount", method = RequestMethod.GET, produces = "application/json")
    public RestResponse<Long> getUserNewMessageCount(
            @ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId) {
        Long result = bondCombineService.getNewMessageCount(userId);
        if (result == null) {
            return RestResponse.Fail("获取未读消息数失败", null);
        }
        return RestResponse.Success(result);
    }

    @ApiOperation(value = "获取用户所有未读消息列表")
    @RequestMapping(value = "/user/{userId}/favorite/unread/msg", method = RequestMethod.GET, produces = "application/json")
    public RestResponse<Page<BondSimpleNotiMsgVO>> getAllFavoriteUnreadMsg(
            @ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
            @ApiParam(name = "pageIndex", value = "页数") @RequestParam(defaultValue = "1") Integer pageIndex,
            @ApiParam(name = "pageSize", value = "每页显示数量") @RequestParam(defaultValue = "20") Integer pageSize) {
        Page<BondSimpleNotiMsgVO> result = bondCombineService.getAllFavoriteUnreadMsg(userId, pageIndex - 1, pageSize);
        if (result == null) {
            return RestResponse.Fail("获取用户未读消息列表失败", null);
        }
        return RestResponse.Success(result);
    }

    @ApiOperation(value = "修改关注投组下所有的债券消息为已读")
    @RequestMapping(value = "/group/{groupId}/readstatus", method = RequestMethod.PUT, produces = "application/json")
    public RestResponse<Long> updateGroupMsgReadStatus(
            @ApiParam(name = "groupId", value = "投组Id", required = true) @PathVariable Long groupId) {
        Long result = bondCombineService.updateGroupMsgReadStatus(groupId);
        if (result == null) {
            return RestResponse.Fail("修改债券消息已读失败", null);
        }
        return RestResponse.Success(result);
    }

    @ApiOperation(value = "修改用户所有未读消息为已读")
    @RequestMapping(value = "/user/{userId}/favorite/msg/{radarType}/readstatus", method = RequestMethod.PUT, produces = "application/json")
    public RestResponse<Long> updateAllFavoriteMsgReadStatus(
            @ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
            @ApiParam(name = "radarType", value = "消息类型[0-所有;1-存续;2-评级;3-舆情;4-价格;5-指标;6-其他;]", required = true) @PathVariable Long radarType) {
        Long result = bondCombineService.updateAllFavoriteMsgReadStatus(userId, radarType);
        if (result == null) {
            return RestResponse.Fail("修改未读消息已读失败", null);
        }
        return RestResponse.Success(result);
    }

    @ApiOperation(value = "根据消息舆情索引获取URL")
    @RequestMapping(value = "/favorite/msg/{newsIndex}", method = RequestMethod.GET, produces = "application/json")
    public RestResponse<String> getFavoriteMsgNewsUrl(
            @ApiParam(name = "newsIndex", value = "消息新闻索引", required = true) @PathVariable Long newsIndex) {
        String result = bondCombineService.getFavoriteMsgNewsUrl(newsIndex);
        if (result == null) {
            return RestResponse.Fail("获取URL失败", null);
        }
        return RestResponse.Success(result);
    }

}
