package com.innodealing.controller;

import com.google.gson.Gson;
import com.innodealing.define.PostFrom;
import com.innodealing.domain.dto.GlobalConfig;
import com.innodealing.domain.dto.UpgradeConfig;
import com.innodealing.domain.dto.UserDetail;
import com.innodealing.domain.model.RestResponse;
import com.innodealing.intercept.XAuthToken;
import com.innodealing.service.BasicService;
import com.innodealing.utils.CheckValueUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "[APP]综合接口")
@RestController
@RequestMapping("api/bond/basic/")
public class BasicController {

    @Autowired
    private BasicService basicService;

    @ApiOperation(value = "获取app配置项")
    @ResponseBody
    @RequestMapping(value = "/config/urls", method = RequestMethod.GET, produces = "application/json")
    public RestResponse<List<GlobalConfig>> getGlobalConfig() {
        List<GlobalConfig> appConfigList = basicService.getGlobalConfig();
        if (appConfigList.size() == 0) {
            return RestResponse.Fail("暂无相关数据", null);
        }
        return RestResponse.Success(appConfigList);
    }

    @ApiOperation(value = "获取app升级信息")
    @ResponseBody
    @RequestMapping(value = "/upgrade/info", method = RequestMethod.GET, produces = "application/json")
    public RestResponse<UpgradeConfig> getUpgradeConfig(
            @RequestHeader String appKey,
            @ApiParam(name = "version", value = "当前版本号") @RequestParam String version,
            @ApiParam(name = "versionCode", value = "当前版本代码") @RequestParam String versionCode) {

        Integer app = CheckValueUtil.getPostFromByAppKey(appKey);
        Integer terminalType = 2; // android
        String versionParam = versionCode; // android
        if (app == PostFrom.IOS.value) {
            terminalType = 4; // ios
            versionParam = version; // ios
        }

        UpgradeConfig upgradeConfig = basicService.getUpgradeConfig(versionParam, terminalType);
        if (upgradeConfig == null) {
            return RestResponse.Fail("error", null);
        }

        if (app == PostFrom.IOS.value) {
            // ios 跳转AppStore的地址,写死.
            upgradeConfig.setUrlAPK("https://itunes.apple.com/cn/app/dm-dealing-matrix/id1066193374?mt=8");
        }

        return RestResponse.Success(upgradeConfig);
    }

    @ApiOperation(value = "修改密码")
    @ResponseBody
    @RequestMapping(value = "/user/{username}/password/{password}", method = RequestMethod.PUT, produces = "application/json")
    public RestResponse<Boolean> updatePassword(
            @PathVariable String username,
            @PathVariable String password) {

        Boolean success = basicService.updatePassword(username, password);
        if (success == null || success == false) {
            return RestResponse.Fail("修改密码失败,请输入6-16位英文或者数字密码", null);
        }
        return RestResponse.Success(success);
    }

    @ApiOperation(value = "获取用户信息")
    @ResponseBody
    @RequestMapping(value = "/user/vcard", method = RequestMethod.GET, produces = "application/json")
    public RestResponse<UserDetail> getVCard(
            @RequestHeader("X-Auth-Token") String xAuthTokenBase64) {
        String xAuthTokenJson = new String(Base64.decodeBase64(xAuthTokenBase64));

        Gson gson = new Gson();
        XAuthToken xAuthToken = gson.fromJson(xAuthTokenJson, XAuthToken.class);
        Object obj = basicService.getUserDetailByUserId(xAuthToken.getUserId());

        String userDetailJson = gson.toJson(obj);
        UserDetail userDetail = gson.fromJson(userDetailJson, UserDetail.class);
        if (userDetail == null) {
            return RestResponse.Fail("获取用户信息失败了", null);
        }
        return RestResponse.Success(userDetail);
    }

}
