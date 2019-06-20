package com.innodealing.controller;

import com.innodealing.domain.JsonResult;
import com.innodealing.domain.model.RestResponse;
import com.innodealing.domain.vo.*;
import com.innodealing.model.dm.bond.BondFavoriteFinaIndex;
import com.innodealing.model.dm.bond.BondFavoritePriceIndex;
import com.innodealing.model.dm.bond.BondFavoriteRadarMapping;
import com.innodealing.model.dm.bond.BondFavoriteRadarSchema;
import com.innodealing.model.mongo.dm.BondComInfoDoc;
import com.innodealing.model.mongo.dm.BondDetailDoc;
import com.innodealing.param.FavoriteBondBatchReq;
import com.innodealing.param.FavoriteGroupReq;
import com.innodealing.param.FavoriteRadarReq;
import com.innodealing.service.BondCombineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "[APP]债券-投组接口")
@RestController
@RequestMapping("api/bond/combine/")
public class BondCombineController {

    @Autowired
    private BondCombineService bondCombineService;

    @ApiOperation(value = "获取用户投组列表")
    @RequestMapping(value = "/users/{userId}/groups", method = RequestMethod.GET, produces = "application/json")
    public RestResponse<List<BondFavoriteGroupVO>> getFavoriteGroupList(
            @ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId) {

        List<BondFavoriteGroupVO> resultList = bondCombineService.getFavoriteGroupListByUserId(userId);
        if (resultList == null || resultList.size() == 0) {
            return RestResponse.Fail("获取投组列表为空", null);
        }
        return RestResponse.Success(resultList);
    }

    // 结果需要分页
    // 自己的投组和全部的债券
    // 搜做的字段: 包括 投组名称,债券名称和债券发行人
    @ApiOperation(value = "根据名称搜索用户投组，债券列表")
    @RequestMapping(value = "/users/{userId}/all/search", method = RequestMethod.GET, produces = "application/json")
    public RestResponse<Page<BondSearchItemVO>> searchBondAllList(
            @ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
            @ApiParam(name = "searchContent", value = "搜索内容") @RequestParam String searchContent,
            @ApiParam(name = "pageIndex", value = "页数") @RequestParam(defaultValue = "1") Integer pageIndex,
            @ApiParam(name = "pageSize", value = "每页显示数量") @RequestParam(defaultValue = "20") Integer pageSize) {
        Page<BondSearchItemVO> resultList = bondCombineService.searchBondAllList(userId, searchContent, pageIndex,pageSize);
        if (resultList == null) {
            return RestResponse.Fail("获取数据列表为空", null);
        }
        return RestResponse.Success(resultList);
    }

    @ApiOperation(value = "根据名称搜索债券列表")
    @RequestMapping(value = "/users/{userId}/bond/{groupId}/search", method = RequestMethod.GET, produces = "application/json")
    public RestResponse<Page<BondSearchItemVO>> searchBondList(
            @ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
            @ApiParam(name = "groupId", value = "投组Id", required = true) @PathVariable Long groupId,
            @ApiParam(name = "searchContent", value = "搜索内容") @RequestParam String searchContent,
            @ApiParam(name = "pageIndex", value = "页数") @RequestParam(defaultValue = "1") Integer pageIndex,
            @ApiParam(name = "pageSize", value = "每页显示数量") @RequestParam(defaultValue = "20") Integer pageSize) {
        Page<BondSearchItemVO> resultList = bondCombineService.searchBondList(userId,groupId, searchContent, pageIndex,pageSize);
        if (resultList == null) {
            return RestResponse.Fail("获取数据列表为空", null);
        }
        return RestResponse.Success(resultList);
    }

    @ApiOperation(value = "[投组] 批量添加关注的债券列表到投组列表")
    @RequestMapping(value = "/user/{userId}/bonds/add", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> addBondListToGroupList(
            @ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
            @RequestBody FavoriteBondBatchReq req) {
        String result = bondCombineService.addBondListToGroup(userId, req);
        return new JsonResult<String>().ok(result);
    }

    @ApiOperation(value = "添加默认投组")
    @RequestMapping(value = "/user/{userId}/group/default/{groupName}", method = RequestMethod.POST, produces = "application/json")
    public RestResponse<Long> createDefaultGroup(
            @ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
            @ApiParam(name = "groupName", value = "投组名", required = true) @PathVariable String groupName) {
        Long result = bondCombineService.createDefaultGroup(userId, groupName);
        if (result == null) {
            return RestResponse.Fail("添加默认投组失败", null);
        }
        return RestResponse.Success(result);
    }

    @ApiOperation(value = "更新投组")
    @RequestMapping(value = "/user/{userId}/group/{groupId}", method = RequestMethod.PUT, produces = "application/json")
    public RestResponse<String> updateFavoriteGroup(
            @ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
            @ApiParam(name = "groupId", value = "投组Id", required = true) @PathVariable Long groupId,
            @RequestBody FavoriteGroupReq req) {
        String result = bondCombineService.updateGroup(userId, groupId, req);
        if (result == null) {
            return RestResponse.Fail("更新投组失败", null);
        }
        return RestResponse.Success(result);
    }

    @ApiOperation(value = "删除投组")
    @RequestMapping(value = "/user/{userId}/group/{groupId}", method = RequestMethod.DELETE, produces = "application/json")
    public RestResponse<String> deleteFavoriteGroup(
            @ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
            @ApiParam(name = "groupId", value = "分组Id", required = true) @PathVariable Long groupId) {
        String result = bondCombineService.deleteGroup(userId, groupId);
        if (result == null) {
            return RestResponse.Fail("删除投组失败", null);
        }
        return RestResponse.Success(result);
    }

    @ApiOperation(value = "获取投组中的全部债券")
    @RequestMapping(value = "/users/{userId}/group/{groupId}/favorites/all", method = RequestMethod.GET, produces = "application/json")
    public RestResponse<Page<BondFavoriteProfileVO>> getGroupAllFavoriteList(
            @ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
            @ApiParam(name = "groupId", value = "分组Id", required = true) @PathVariable Integer groupId,
            @ApiParam(name = "pageIndex", value = "页数") @RequestParam(defaultValue = "1") Integer pageIndex,
            @ApiParam(name = "pageSize", value = "每页显示数量") @RequestParam(defaultValue = "20") Integer pageSize) {
        Page<BondFavoriteProfileVO> result = bondCombineService.getGroupProfileFavBondList(userId, groupId, pageIndex - 1, pageSize);
        if (result == null || result.getTotalElements() == 0) {
            return RestResponse.Fail("获取债券列表失败", null);
        }
        return RestResponse.Success(result);
    }

    @ApiOperation(value = "获取关注债券简况")
    @RequestMapping(value = "/favorite/{bondCodeId}/profile", method = RequestMethod.GET, produces = "application/json")
    public RestResponse<BondDetailDoc> getFavoriteBondProfile(
            @ApiParam(name = "bondCodeId", value = "投组债券Id", required = true) @PathVariable Long bondCodeId) {
        BondDetailDoc result = bondCombineService.getBondFavoriteProfileById(bondCodeId);
        if (result == null) {
            return RestResponse.Fail("获取债券简况失败", null);
        }
        return RestResponse.Success(result);
    }

    @ApiOperation(value = "获取关注债券主体")
    @RequestMapping(value = "/favorite/{comUniCode}/company", method = RequestMethod.GET, produces = "application/json")
    public RestResponse<BondComInfoDoc> getFavoriteBondCompany(
            @ApiParam(name = "comUniCode", value = "债券主体Id", required = true) @PathVariable Long comUniCode) {
        BondComInfoDoc result = bondCombineService.getBondFavoriteCompanyById(comUniCode);
        if (result == null) {
            return RestResponse.Fail("获取债券主体失败", null);
        }
        return RestResponse.Success(result);
    }

    @ApiOperation(value = "获取单支债券的价格指标列表")
    @RequestMapping(value = "/favorite/{favoriteId}/radars/price", method = RequestMethod.GET, produces = "application/json")
    public RestResponse<List<BondFavoritePriceIndex>> getFavoriteRadarPrice(
            @ApiParam(name = "favoriteId", value = "投组债券Id", required = true) @PathVariable Long favoriteId) {
        List<BondFavoritePriceIndex> resultList = bondCombineService.getSingleFavoritePriceIndexList(favoriteId);
        if (resultList == null || resultList.size() == 0) {
            return RestResponse.Fail("获取债券价格指标列表失败", null);
        }
        return RestResponse.Success(resultList);
    }

    @ApiOperation(value = "获取单支债券的财务指标列表")
    @RequestMapping(value = "/favorite/{favoriteId}/radars/fina", method = RequestMethod.GET, produces = "application/json")
    public RestResponse<List<BondFavoriteFinaIndex>> getFavoriteRadarFina(
            @ApiParam(name = "userId", value = "账号Id", required = true) @RequestParam Integer userId,
            @ApiParam(name = "favoriteId", value = "投组债券Id", required = true) @PathVariable Long favoriteId) {
        List<BondFavoriteFinaIndex> result = bondCombineService.getSingleFavoriteFinaIndexList(favoriteId);
        if (result == null || result.size() == 0) {
            return RestResponse.Fail("获取债券财务指标列表失败", null);
        }
        return RestResponse.Success(result);
    }

    @ApiOperation(value = "设置单支债券，保存价格+财务指标雷达信息")
    @RequestMapping(value = "/favorite/radars", method = RequestMethod.POST, produces = "application/json")
    public RestResponse<Boolean> saveFavoriteRadars(
            @ApiParam(name = "userId", value = "账号Id", required = true) @RequestParam Integer userId,
            @RequestBody FavoriteRadarReq req) {
        boolean result = bondCombineService.saveFavoriteRadars(userId, req);
        if (!result) {
            return RestResponse.Fail("更新债券信息失败", null);
        }
        return RestResponse.Success(result);
    }

    @ApiOperation(value = "从单个投组中删除该条关注的债券")
    @RequestMapping(value = "/user/{userId}/favorite/{favoriteId}", method = RequestMethod.DELETE, produces = "application/json")
    public RestResponse<Long> deleteFavorite(
            @ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
            @ApiParam(name = "favoriteId", value = "关注债券Id", required = true) @PathVariable Long favoriteId) {
        bondCombineService.deleteFavorite(favoriteId);
        return RestResponse.Success(favoriteId);
    }


    @ApiOperation(value = "[投组] 根据父雷达ID获取子雷达")
    @RequestMapping(value = "/bond/radar/{radarType}/child", method = RequestMethod.GET, produces = "application/json")
    public RestResponse<List<BondFavoriteRadarSchema>> getChildRadarByRadarId(
            @ApiParam(name = "radarType", value = "雷达Id", required = true) @PathVariable Long radarType) {
        List<BondFavoriteRadarSchema> result = bondCombineService.getChildRadarByRadarId(radarType);
        if (result == null) {
            return RestResponse.Fail("获取雷达信息失败", null);
        }
        return RestResponse.Success(result);
    }
}
