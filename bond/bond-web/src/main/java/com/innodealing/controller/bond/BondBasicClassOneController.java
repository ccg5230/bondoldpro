package com.innodealing.controller.bond;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.bond.param.bond.BondClassOneParam;
import com.innodealing.bond.param.bond.BondClassOneSearchPage;
import com.innodealing.bond.service.bond.BondBasicClassOneService;
import com.innodealing.controller.BaseController;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.dm.bond.BondClassOneAttInfoVo;
import com.innodealing.model.mongo.dm.BondBasicInfoClassOneDoc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "城投债财务信息接口")
@RestController
@RequestMapping("api/bondClassOne")
public class BondBasicClassOneController extends BaseController {

    private @Autowired BondBasicClassOneService bondBasicClassOneService;

    @ApiOperation(value = "查询")
    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<BondClassOneSearchPage> viewList(@RequestHeader("userid") long userid, @RequestBody BondClassOneParam bondClassOneParam,
            @ApiParam(name = "page", value = "页数", required = true) @RequestParam(defaultValue = "1") Integer page,
            @ApiParam(name = "limit", value = "每页显示数量", required = true) @RequestParam(defaultValue = "20") Integer limit) {
        BondClassOneSearchPage result = bondBasicClassOneService.find(bondClassOneParam, userid, page, limit);
        return new JsonResult<BondClassOneSearchPage>().ok(result);
    }

    @ApiOperation(value = "查询导出【全部数据】")
    @RequestMapping(value = "/searchReoport", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<List<BondBasicInfoClassOneDoc>> viewListReoport(@RequestHeader("userid") long userid,
            @ApiParam(name = "jsonParam", value = "查询参数json", required = true) String jsonParam) {
        List<BondBasicInfoClassOneDoc> result = bondBasicClassOneService.findReoport(jsonParam, userid);
        return new JsonResult<List<BondBasicInfoClassOneDoc>>().ok(result);
    }

    @ApiOperation(value = "相关文件")
    @RequestMapping(value = "/attInfoList/{bondUniCode}", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<BondClassOneAttInfoVo> attInfoList(@RequestHeader("userid") long userid,
            @ApiParam(name = "bondUniCode", value = "债券唯一编号", required = true) @PathVariable long bondUniCode) {
        BondClassOneAttInfoVo result = bondBasicClassOneService.getAttInfoList(bondUniCode);
        return new JsonResult<BondClassOneAttInfoVo>().ok(result);
    }
}
