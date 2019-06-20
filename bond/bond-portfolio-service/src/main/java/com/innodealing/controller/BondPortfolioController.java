package com.innodealing.controller;

import java.util.List;

import com.innodealing.param.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.domain.JsonResult;
import com.innodealing.domain.vo.BondCommonRadarVO;
import com.innodealing.domain.vo.BondFavoriteDetailVO;
import com.innodealing.domain.vo.BondFavoriteGroupVO;
import com.innodealing.domain.vo.BondSimpleNotiMsgVO;
import com.innodealing.model.dm.bond.BondFavorite;
import com.innodealing.model.dm.bond.BondFavoriteFinaIndex;
import com.innodealing.model.dm.bond.BondFavoritePriceIndex;
import com.innodealing.model.dm.bond.BondFavoriteRadarSchema;
import com.innodealing.service.BondPortfolioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "债券投资组合接口")
@RestController
@RequestMapping("api/bond/portfolio/")
public class BondPortfolioController {

	@Autowired
	private BondPortfolioService bondPortfolioService;

	@ApiOperation(value = "[投组] 获取投组名称列表")
	@RequestMapping(value = "/users/{userId}/group/brief/list", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<BondFavoriteGroupVO>> getFavoriteGroupNameList(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId) {
		return new JsonResult<List<BondFavoriteGroupVO>>()
				.ok(bondPortfolioService.getFavoriteGroupNameListByUserId(userId));
	}

	@ApiOperation(value = "[投组] 获取投组列表")
	@RequestMapping(value = "/users/{userId}/groups", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<BondFavoriteGroupVO>> findFavoriteGroupList(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId) {
		return new JsonResult<List<BondFavoriteGroupVO>>()
				.ok(bondPortfolioService.getFavoriteGroupListByUserId(userId));
	}

	@ApiOperation(value = "[消息] 按照雷达类型过滤的投组消息列表")
	@RequestMapping(value = "/user/{userId}/group/{groupId}/msg/{radarTypes}", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Page<BondSimpleNotiMsgVO>> getGroupMsgListByType(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "groupId", value = "投组Id", required = true) @PathVariable Integer groupId,
			@ApiParam(name = "radarTypes", value = "消息类型[1-存续;2-评级;3-舆情;4-价格;5-指标;6-其他]", required = true) @PathVariable List<Long> radarTypes,
			@ApiParam(name = "pageIndex", value = "页数") @RequestParam(defaultValue = "1") Integer pageIndex,
			@ApiParam(name = "pageSize", value = "每页显示数量") @RequestParam(defaultValue = "20") Integer pageSize) {
		Page<BondSimpleNotiMsgVO> result = bondPortfolioService.getGroupSimpleMsgWithRadarTypes(userId, groupId,
				radarTypes, pageIndex - 1, pageSize);
		return new JsonResult<Page<BondSimpleNotiMsgVO>>().ok(result);
	}

	@ApiOperation(value = "[消息] 按照雷达类型过滤的关注债券消息列表")
	@RequestMapping(value = "/user/{userId}/favorite/{favoriteId}/msg/{radarTypes}", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Page<BondSimpleNotiMsgVO>> getFavoriteMsgListByType(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "favoriteId", value = "关注债券Id", required = true) @PathVariable Integer favoriteId,
			@ApiParam(name = "radarTypes", value = "消息类型[1-存续;2-评级;3-舆情;4-价格;5-指标;6-其他]", required = true) @PathVariable List<Long> radarTypes,
			@ApiParam(name = "pageIndex", value = "页数") @RequestParam(defaultValue = "1") Integer pageIndex,
			@ApiParam(name = "pageSize", value = "每页显示数量") @RequestParam(defaultValue = "20") Integer pageSize) {
		Page<BondSimpleNotiMsgVO> result = bondPortfolioService.getFavoriteSimpleMsgWithRadarTypes(userId, favoriteId,
				radarTypes, pageIndex - 1, pageSize);
		return new JsonResult<Page<BondSimpleNotiMsgVO>>().ok(result);
	}

	@ApiOperation(value = "[投组] 添加投组")
	@RequestMapping(value = "/user/{userId}/group", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Long> addFavoriteGroup(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@RequestBody FavoriteGroupReq req) {
		Long result = bondPortfolioService.createGroup(userId, req);
		return new JsonResult<Long>().ok(result);
	}

	@ApiOperation(value = "[投组] 更新投组")
	@RequestMapping(value = "/user/{userId}/group/{groupId}", method = RequestMethod.PUT, produces = "application/json")
	public JsonResult<String> updateFavoriteGroup(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "groupId", value = "投组Id", required = true) @PathVariable Long groupId,
			@RequestBody FavoriteGroupReq req) {
		String result = bondPortfolioService.updateGroup(userId, groupId, req);
		return new JsonResult<String>().ok(result);
	}

	@ApiOperation(value="更新关注债券的持仓量/持仓价格/持仓日期/备注")
	@RequestMapping(value = "/user/{userId}/favorite/{favoriteId}", method = RequestMethod.PUT, produces = "application/json")
	public JsonResult<Integer> updateFavoritePosition(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "favoriteId", value = "债券的关注Id", required = true) @PathVariable Integer favoriteId,
			@RequestBody BondFavoritePositionParam param){
		return new JsonResult<Integer>().ok(bondPortfolioService.updateFavoritePosition(favoriteId, param));
	}

	@ApiOperation(value = "[投组] 删除投组")
	@RequestMapping(value = "/user/{userId}/group/{groupId}", method = RequestMethod.DELETE, produces = "application/json")
	public JsonResult<String> deleteFavoriteGroup(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "groupId", value = "分组Id", required = true) @PathVariable Long groupId) {
		String result = bondPortfolioService.deleteGroup(userId, groupId);
		return new JsonResult<String>().ok(result);
	}
	
	@ApiOperation(value = "[投组] 获取投组的的普通雷达集合")
	@RequestMapping(value = "/user/{userId}/group/{groupId}/radar/{radarType}/items", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<BondCommonRadarVO>> getGroupCommonRadarList(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "groupId", value = "投组Id[0表示取初始的默认值]", required = true) @PathVariable Long groupId,
			@ApiParam(name = "radarType", value = "普通雷达类型[1-存续;2-评级;3-舆情;6-其他]", required = true) @PathVariable Long radarType) {
		List<BondCommonRadarVO> commonRadarList = bondPortfolioService.getGroupCommonRadarListByType(groupId,
				radarType);
		return new JsonResult<List<BondCommonRadarVO>>().ok(commonRadarList);
	}

	@ApiOperation(value = "[投组] 获取投组的的价格雷达集合")
	@RequestMapping(value = "/user/{userId}/group/{groupId}/radar/price/items", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<BondFavoritePriceIndex>> getGroupPriceRadarList(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "groupId", value = "投组Id[0表示取初始的默认值]", required = true) @PathVariable Long groupId) {
		List<BondFavoritePriceIndex> priceRadarList = bondPortfolioService.getGroupPriceRadarList(groupId);
		return new JsonResult<List<BondFavoritePriceIndex>>().ok(priceRadarList);
	}

	@ApiOperation(value = "[投组] 获取投组的的财务指标雷达集合")
	@RequestMapping(value = "/user/{userId}/group/{groupId}/radar/fina/items", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<BondFavoriteFinaIndex>> getGroupFinaRadarList(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "groupId", value = "投组Id[0表示取初始的默认值]", required = true) @PathVariable Long groupId) {
		List<BondFavoriteFinaIndex> finaRadarList = bondPortfolioService.getGroupFinaRadarList(groupId);
		return new JsonResult<List<BondFavoriteFinaIndex>>().ok(finaRadarList);
	}
	
	@ApiOperation(value="[投组] 更新投组主界面上的消息是否推送按钮")
    @RequestMapping(value = "/users/{userId}/groupNotified/{groupId}", method = RequestMethod.PUT, produces = "application/json")
    public JsonResult<Integer> updateGroupNotified(@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
    		@ApiParam(name = "groupId", value = "分组Id", required = true) @PathVariable Integer groupId,
    		@ApiParam(name = "notifiedEnable", value = "债券变动是否提醒，1 提醒， 0 不提醒", required = true) @RequestBody FavoriteNotifiedEnableReq req){
		bondPortfolioService.updateGroupNotified(userId, groupId, req.getNotifiedEnable());
		return new JsonResult<Integer>().ok(groupId);
	}

	@ApiOperation(value = "[投组] 获取投组中的全部的关注债券的详情列表(展示所有的关注债券)")
	@RequestMapping(value = "/users/{userId}/org/{orgId}/group/{groupId}/fullFavorites/all", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Page<BondFavoriteDetailVO>> getGroupAllFullFavoriteList(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "orgId", value = "用户所属机构Id", required = true) @PathVariable Integer orgId,
			@ApiParam(name = "groupId", value = "分组Id", required = true) @PathVariable Integer groupId,
			@ApiParam(name = "dateDiff", value = "时间差[n天内，-1表示从加入至今]", required = true) @RequestParam(defaultValue = "0") Integer dateDiff,
			@ApiParam(name = "keyword", value = "关键字") @RequestParam(defaultValue = "") String keyword,
			@ApiParam(name = "isShowExpired", value = "是否显示已过期债券") @RequestParam(defaultValue = "true") Boolean isShowExpired,
			@ApiParam(name = "page", value = "页数") @RequestParam(defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量") @RequestParam(defaultValue = "20") Integer limit,
			@ApiParam(name = "sort", value = "排序规则") @RequestParam(defaultValue = "createTime:desc") String sort) {
		return new JsonResult<Page<BondFavoriteDetailVO>>().ok(bondPortfolioService.getGroupFullFavBondList(userId, orgId,
				groupId, dateDiff, keyword, isShowExpired, page - 1, limit, sort));
	}

	@ApiOperation(value = "[投组] 获取投组中的有变动的债券的详情列表")
	@RequestMapping(value = "/users/{userId}/org/{orgId}/group/{groupId}/fullFavorites/{radarTypes}", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Page<BondFavoriteDetailVO>> getGroupFullFavoriteList(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "orgId", value = "用户所属机构Id", required = true) @PathVariable Integer orgId,
			@ApiParam(name = "groupId", value = "分组Id", required = true) @PathVariable Integer groupId,
			@ApiParam(name = "dateDiff", value = "时间差[n天内，-1表示从加入至今]", required = true) @RequestParam(defaultValue = "0") Integer dateDiff,
			@ApiParam(name = "keyword", value = "关键字") @RequestParam(defaultValue = "") String keyword,
			@ApiParam(name = "isShowExpired", value = "是否显示已过期债券") @RequestParam(defaultValue = "true") Boolean isShowExpired,
			@ApiParam(name = "radarTypes", value = "消息类型[1-存续;2-评级;3-舆情;4-价格;5-指标;6-其他]", required = true) @PathVariable List<Long> radarTypes,
			@ApiParam(name = "page", value = "页数") @RequestParam(defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量") @RequestParam(defaultValue = "20") Integer limit,
			@ApiParam(name = "sort", value = "排序规则") @RequestParam(defaultValue = "createTime:desc") String sort) {
		return new JsonResult<Page<BondFavoriteDetailVO>>().ok(bondPortfolioService.getGroupFullFavBondListByConstraint(
				userId, orgId, groupId, dateDiff, keyword, isShowExpired, radarTypes, page - 1, limit, sort));
	}

	@ApiOperation(value = "[投组] 获取投组雷达根节点/子节点列表")
	@RequestMapping(value = "/radar/nodes/{radarType}", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<BondFavoriteRadarSchema>> getRadarNodes(
			@ApiParam(name = "radarType", value = "节点类型[0-根节点列表;1-存续;2-评级;3-舆情;4-价格;5-指标;6-其他]", required = true) @PathVariable Long radarType) {
		List<BondFavoriteRadarSchema> result = bondPortfolioService.getRadarNodes(radarType);
		return new JsonResult<List<BondFavoriteRadarSchema>>().ok(result);
	}

	@ApiOperation(value = "[指标] 获取单支债券的价格指标列表")
	@RequestMapping(value = "/favorite/{favoriteId}/radars/price", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<BondFavoritePriceIndex>> getFavoriteRadarPrice(
			@ApiParam(name = "userId", value = "账号Id", required = true) @RequestParam Integer userId,
			@ApiParam(name = "favoriteId", value = "投组债券Id", required = true) @PathVariable Long favoriteId) {
		List<BondFavoritePriceIndex> result = bondPortfolioService.getSingleFavoritePriceIndexList(favoriteId);
		return new JsonResult<List<BondFavoritePriceIndex>>().ok(result);
	}

	@ApiOperation(value = "[指标] 获取单支债券的财务指标列表")
	@RequestMapping(value = "/favorite/{favoriteId}/radars/fina", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<BondFavoriteFinaIndex>> getFavoriteRadarFina(
			@ApiParam(name = "userId", value = "账号Id", required = true) @RequestParam Integer userId,
			@ApiParam(name = "favoriteId", value = "投组债券Id", required = true) @PathVariable Long favoriteId) {
		List<BondFavoriteFinaIndex> result = bondPortfolioService.getSingleFavoriteFinaIndexList(favoriteId);
		return new JsonResult<List<BondFavoriteFinaIndex>>().ok(result);
	}

	@ApiOperation(value = "[指标] 设置单支债券，保存价格+财务指标雷达信息")
	@RequestMapping(value = "/favorite/radars", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Boolean> saveFavoriteRadars(
			@ApiParam(name = "userId", value = "账号Id", required = true) @RequestParam Integer userId,
			@RequestBody FavoriteRadarReq req) {
		boolean result = bondPortfolioService.saveFavoriteRadars(userId, req);
		return new JsonResult<Boolean>().ok(result);
	}

	@ApiOperation(value = "[投组] 批量添加关注的债券列表到投组列表")
	@RequestMapping(value = "/user/{userId}/bonds/add", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> addBondListToGroupList(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@RequestBody FavoriteBondBatchReq req) {
		String result = bondPortfolioService.addBondListToGroupList(userId, req);
		return new JsonResult<String>().ok(result);
	}

	@ApiOperation(value = "[投组] 从当前用户的所有投组中删除该条关注的债券")
	@RequestMapping(value = "/user/{userId}/favorite/bond/{bondUniCode}", method = RequestMethod.DELETE, produces = "application/json")
	public JsonResult<Long> deleteBondFromUserGroups(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "bondUniCode", value = "债券Id", required = true) @PathVariable Long bondUniCode) {
		bondPortfolioService.deleteBondFromUserGroups(userId, bondUniCode);
		return new JsonResult<Long>().ok(bondUniCode);
	}

	@ApiOperation(value = "[投组] 从单个投组中删除该条关注的债券")
	@RequestMapping(value = "/user/{userId}/favorite/{favoriteId}", method = RequestMethod.DELETE, produces = "application/json")
	public JsonResult<Long> deleteFavorite(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "favoriteId", value = "关注债券Id", required = true) @PathVariable Long favoriteId) {
		bondPortfolioService.deleteFavorite(userId, favoriteId);
		return new JsonResult<Long>().ok(favoriteId);
	}
	
	@ApiOperation(value="[投组] 批量从单个投组中删除债券关注")
    @RequestMapping(value = "/users/{userId}/favorite/favorites", method = RequestMethod.DELETE, produces = "application/json")
    public JsonResult<String> deleteFavorites(@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
    		@RequestBody FavoriteUpdateList param){
		String result = bondPortfolioService.deleteFavorites(userId, param);
		return new JsonResult<String>().ok(result);
	}

	@ApiOperation(value = "[指标] 获取财务指标的单位")
	@RequestMapping(value = "/user/{userId}/fina/unit/{finaCode}", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Integer> getUnitByFinaCode(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Long userId,
			@ApiParam(name = "finaCode", value = "财务指标code", required = true) @PathVariable String finaCode) {
		Integer result = bondPortfolioService.getUnitByFinaCode(userId, finaCode);
		return new JsonResult<Integer>().ok(result);
	}

	@ApiOperation(value = "[消息] 修改关注投组下所有的债券消息为已读")
	@RequestMapping(value = "/user/{userId}/group/{groupId}/readstatus", method = RequestMethod.PUT, produces = "application/json")
	public JsonResult<Long> updateGroupMsgReadStatus(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "groupId", value = "投组Id", required = true) @PathVariable Long groupId) {
		return new JsonResult<Long>().ok(bondPortfolioService.updateGroupMsgReadStatus(userId, groupId));
	}

	@ApiOperation(value = "[投组] 添加默认投组")
	@RequestMapping(value = "/user/{userId}/group/default/{groupName}", method = RequestMethod.PUT, produces = "application/json")
	public JsonResult<Long> createDefaultGroup(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "groupName", value = "投组名", required = true) @PathVariable String groupName) {
		Long groupId = bondPortfolioService.createDefaultGroup(userId, groupName);
		return new JsonResult<Long>().ok(groupId);
	}

	@ApiOperation(value = "[投组] 获取投组的未读消息数")
	@RequestMapping(value = "/user/{userId}/group/{groupId}/newMessageCount", method = RequestMethod.PUT, produces = "application/json")
	public JsonResult<Long> createDefaultGroup(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "groupId", value = "投组Id", required = true) @PathVariable Long groupId) {
		Long newMessageCount = bondPortfolioService.getNewMessageCount(userId, groupId);
		return new JsonResult<Long>().ok(newMessageCount);
	}

	@ApiOperation(value = "[投组] 测试SocketIO消息推送")
	@RequestMapping(value = "/user/{userId}/group/{groupId}/testSocketIO", method = RequestMethod.PUT, produces = "application/json")
	public JsonResult<Boolean> testSocketIO(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "groupId", value = "投组Id", required = true) @PathVariable Long groupId) {
		Boolean isSent = bondPortfolioService.testSocketIO(userId, groupId);
		return new JsonResult<Boolean>().ok(isSent);
	}
}