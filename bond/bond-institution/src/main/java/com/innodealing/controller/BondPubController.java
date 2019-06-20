package com.innodealing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.model.PageAdapter;
import com.innodealing.model.VBondPubComDetail;
import com.innodealing.model.VUserInfo;
import com.innodealing.model.mysql.VBondInfo;
import com.innodealing.model.mysql.VBondPub;
import com.innodealing.model.mysql.VPubInfo;
import com.innodealing.model.vo.BondPubList;
import com.innodealing.service.BondPubService;
import com.innodealing.util.JsonResult;
import com.innodealing.util.KitCost;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 债券发行人关系
 * 
 * @author 戴永杰
 *
 * @date 2017年9月14日 上午9:36:47
 * @version V1.0
 *
 */
@Api(description = "债券发行人关系")
@RequestMapping("/bondpub")
@RestController
public class BondPubController extends BaseController {
	@Autowired
	private BondPubService bondPubService;

	@ApiOperation(value = "获取债券发行人")
	@RequestMapping(value = "/getBondPubList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<List<VPubInfo>> getBondPubList(
			@ApiParam(name = "key", value = "关键字", required = true) @RequestParam String key) {
		return ok(bondPubService.getBondPubList(key, 10));
	}

	@ApiOperation(value = "获取债券列表")
	@RequestMapping(value = "/getBondList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<List<VBondInfo>> getBondList(
			@ApiParam(name = "key", value = "关键字", required = true) @RequestParam String key) {
		return ok(bondPubService.getBondList(key, 10));
	}

	@ApiOperation(value = "设置债券发行人关系")
	@RequestMapping(value = "/setBondPub", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Integer> setBondPub(
			@ApiParam(name = "id", value = "ID [多个用逗号分割]", required = true) @RequestParam String id,
			@ApiParam(name = "type", value = "类型 1 债券 2 发行人 ", required = true) @RequestParam Integer type,
			@ApiParam(name = "comUniCode", value = "关联发行人ID", required = true) @RequestParam Integer comUniCode,
			@ApiParam(name = "remark", value = "备注", required = true) @RequestParam String remark,

			@ApiParam(name = "userid", value = "用户ID ", required = true) @RequestParam Integer userid) {
		return ok(bondPubService.setBondPub(id, type, comUniCode, remark, getCurrentUser().getUserId(),getCurrentUser().getOrgId()));
	}

	@ApiOperation(value = "检查发行关系有效性")
	@RequestMapping(value = "/checkBondPubValid", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Integer> checkBondPubValid(
			@ApiParam(name = "comUniCode", value = "发行人标识", required = true) @RequestParam String comUniCode) {
		return ok(bondPubService.checkBondPubValid(comUniCode));

	}

	@ApiOperation(value = "删除债券发行人关系")
	@RequestMapping(value = "/removeBondPub", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Integer> removeBondPub(
			@ApiParam(name = "id", value = "唯一标识 多个逗号分隔", required = true) @RequestParam String id) {
		return ok(bondPubService.removeBondPub(KitCost.split(id, ",")));
	}

	@ApiOperation(value = "获取发行人关系分页列表")
	@RequestMapping(value = "/getBondPubListPage", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<PageAdapter<VBondPub>> getBondPubListPage(
			@ApiParam(name = "key", value = "关键字", required = true) @RequestParam String key,
			@ApiParam(name = "type", value = "类型 1债券简称/发行人  2 关联发行人 ", required = true, defaultValue = "1") @RequestParam Integer type,
			@ApiParam(name = "page", value = "页数", required = false) @RequestParam(defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue = "20") Integer limit) {

		return ok(bondPubService.getBondPubListPage(getCurrentUser().getOrgId(),key, type, page, limit));
	}

	/**
	 * 发行人详情页
	 */
	@ApiOperation(value = "获取发行人关系分页列表")
	@RequestMapping(value = "/getBondPubOnBondComDetail", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<PageAdapter<VBondPubComDetail>> getBondPubOnBondComDetail(
			@ApiParam(name = "comUniCode", value = "主体标识", required = true) @RequestParam String comUniCode,
			@ApiParam(name = "page", value = "页数", required = false) @RequestParam(defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue = "20") Integer limit) {
		return ok(bondPubService.getBondPubOnBondComDetail(getCurrentUser().getOrgId(),comUniCode, page, limit));
	}
	
	
	
	/**
	 * 债券详情页
	 */
	@ApiOperation(value = "债券获取关联的发行人列表")
	@RequestMapping(value = "/getBondPubListByBondCode", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<List<BondPubList>> getBondPubListByBondCode(
			@ApiParam(name = "bondUniCode", value = "债券标识", required = true) @RequestParam String bondUniCode){
		VUserInfo currentUser = getCurrentUser();
		return ok(bondPubService.getBondPubListByBondCode(currentUser.getUserId(),currentUser.getInduType(),bondUniCode,currentUser.getOrgId()));
	}
}
