package com.innodealing.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.innodealing.bond.vo.favorite.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.innodealing.bond.param.BondFavoriteParam;
import com.innodealing.bond.param.BondFavoriteUpdateList;
import com.innodealing.bond.param.BondFavorityAddList;
import com.innodealing.bond.param.BondFavorityAddReq;
import com.innodealing.bond.param.BondFavorityGroupAddReq;
import com.innodealing.bond.param.BondFavorityGroupUpdateReq;
import com.innodealing.bond.service.BondDetailService;
import com.innodealing.bond.service.BondFavoriteService;
import com.innodealing.bond.validation.FavoriteGroupValidator;
import com.innodealing.bond.vo.msg.BasicBondVo;
import com.innodealing.bond.vo.msg.BondNotificationMsgVo;
import com.innodealing.bond.vo.msg.BondNotificationStatisticsVo;
import com.innodealing.bond.vo.msg.BondNotificationsVo;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.dm.bond.BondFavorite;
import com.innodealing.model.dm.bond.BondFavoriteGroup;
import com.innodealing.service.BondTextParseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author stephen.ma
 * @date 2016年9月1日
 * @clasename BondQuoteController.java
 * @decription TODO
 */
@Api(description = "我的关注管理-债券")
@RestController
@RequestMapping("api/bond/")
public class BondFavoriteController extends BaseController{
	
	private @Autowired BondFavoriteService bondFavService;
	
	private @Autowired FavoriteGroupValidator favGroupValidator;
	
	private @Autowired BondDetailService bondDetailService;
	
    private @Autowired BondTextParseService bondTextParseService;
    
    private @Autowired Gson gson;
	
	@ApiOperation(value="获取债券分组")
    @RequestMapping(value = "/users/{userId}/favoriteGroups", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<BondFavoriteGroupVo>> findFavoriteGroup(@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId){
		return new JsonResult<List<BondFavoriteGroupVo>>().ok(bondFavService.findFavoriteGroupsByUserId(userId));
	}
	
	@ApiOperation(value="添加分组")
    @RequestMapping(value = "/users/{userId}/favoriteGroups", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<BondFavoriteGroupVo> addFavoriteGroup(@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
    		@RequestBody BondFavorityGroupAddReq req){
		favGroupValidator.validateAddGroup(userId, req.getGroupName());
		
		BondFavoriteGroup group = new BondFavoriteGroup();
		group.setGroupName(req.getGroupName());
		group.setGroupType(1);
		group.setUserId(userId);
		group.setIsDelete(0);
		group.setCreateTime(new Date());
		group.setUpdateTime(new Date());
		group.setNotifiedEnable(req.getNotifiedEnable());
		if (null != req.getNotifiedEventtypes() && req.getNotifiedEventtypes().size() > 0) {
			group.setNotifiedEventtype(gson.toJson(req.getNotifiedEventtypes()));
		}
		//bondFavService.findFavoriteByGroupId(groupId)
		return new JsonResult<BondFavoriteGroupVo>().ok(bondFavService.addFavoriteGroup(group));
	}
	
	@ApiOperation(value="更新分组")
    @RequestMapping(value = "/users/{userId}/favoriteGroups/{groupId}", method = RequestMethod.PUT, produces = "application/json")
    public JsonResult<BondFavoriteGroup> updateFavoriteGroup(@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
    		@ApiParam(name = "groupId", value = "分组Id", required = true) @PathVariable Integer groupId,
    		@RequestBody BondFavorityGroupUpdateReq req){
		BondFavoriteGroup group = favGroupValidator.validateUpdateGroup(userId, groupId, req.getGroupName());
		bondFavService.updateFavoriteGroup(group, req);
		
		return new JsonResult<BondFavoriteGroup>().ok(group);
		
		
	}
	
	@ApiOperation(value="删除债券分组")
    @RequestMapping(value = "/users/{userId}/favoriteGroups/{groupId}", method = RequestMethod.DELETE, produces = "application/json")
    public JsonResult<Integer> deleteFavoriteGroup(@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
    		@ApiParam(name = "groupId", value = "分组Id", required = true) @PathVariable Integer groupId){
		favGroupValidator.validateDeleteGroup(groupId);
		bondFavService.deleteFavoriteGroup(groupId);
		return new JsonResult<Integer>().ok(groupId);
	}
	
	@ApiOperation(value="添加债券关注")
    @RequestMapping(value = "/users/{userId}/favoriteGroups/favorites", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<List<BondFavorite>> addFavorite(@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
    		@RequestBody BondFavorityAddReq req){
	    List<BondFavorite> list = new ArrayList<>();
	    for (Integer groupId : req.getGroupIds()) {
	    	List<BondFavorite> favorites = bondFavService.findFavoriteByGroupIdAndBondUniCode(groupId, req.getBondUniCode());
	    	if (null != favorites && favorites.size() > 0) {
	    		bondFavService.updateFavoriteByGroupIdAndBondUniCode(favorites);
	    		list = favorites;
			}else{
				BondFavorite favorite = new BondFavorite();
		        favorite.setBondUniCode(req.getBondUniCode());
		        favorite.setGroupId(groupId);
		        favorite.setUserId(userId);
		        favorite.setIsDelete(0);
		        favorite.setCreateTime(new Date());
		        favorite.setUpdateTime(new Date());
		        favorite.setOpeninterest(0);
		        favorite.setBookmark(0L);
		        favorite.setBookmarkUpdateTime(new Date());
		        bondFavService.addFavorite(favorite);
		        list.add(favorite);
			}
        }
		return new JsonResult<List<BondFavorite>>().ok(list);
	}

	@ApiOperation(value="删除债券关注")
    @RequestMapping(value = "/users/{userId}/favorites/{favoriteId}", method = RequestMethod.DELETE, produces = "application/json")
    public JsonResult<Integer> deleteFavorite(@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
    		@ApiParam(name = "favoriteId", value = "债券关注的Id", required = true) @PathVariable Integer favoriteId){
		bondFavService.deleteFavorite(userId, favoriteId);
		return new JsonResult<Integer>().ok(favoriteId);
	}
	
	@ApiOperation(value="批量删除债券关注")
    @RequestMapping(value = "/users/{userId}/favoriteGroups/favorites", method = RequestMethod.DELETE, produces = "application/json")
    public JsonResult<List<Integer>> deleteFavorites(@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
    		@RequestBody BondFavoriteUpdateList param){
		bondFavService.deleteFavorites(userId, param);
		return new JsonResult<List<Integer>>().ok(null);
	}
	
	@ApiOperation(value="获取分组下所有关注")
    @RequestMapping(value = "/users/{userId}/favoriteGroups/{groupId}/favorites", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<BondFavoriteVo>> getFavorite(@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId, 
    		@ApiParam(name = "groupId", value = "分组Id", required = true) @PathVariable Integer groupId){
		return new JsonResult<List<BondFavoriteVo>>().ok(bondFavService.findFavoritesByGroupId(userId, groupId));
	}
	
	@ApiOperation(value="获取关注债券明细")
    @RequestMapping(value = "/users/{userId}/favoriteGroups/{groupId}/favorites/bonds", 
    	method = RequestMethod.GET, produces = "application/json")
    public JsonResult<Page<BondDetailVo>> findBondsByFavoriteId(
    		@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
    		@ApiParam(name = "groupId", value = "分组Id", required = true) @PathVariable Integer groupId,
    		@ApiParam(name = "page", value = "页数", required = false) @RequestParam(defaultValue="1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue="20") Integer limit,
			@ApiParam(name = "sort", value = "排序规则", required = false) @RequestParam(defaultValue="updateTime:desc") String sort)
	{
		//找关注组下面的所有债券,待优化
		//List<BondFavorite> favList = bondFavService.findFavoriteByGroupId(groupId);
		int resultsize = bondFavService.getFavoriteCountByGroupId(groupId);
		List<BondFavorite> favList = bondFavService.findFavoriteByGroupIdAndLimit(groupId, (page-1)*limit, limit);

		Map<Long, BondDetailVo> bondVos = bondDetailService.findBondDetailVos(favList, userId, groupId, page, limit, sort);
		List<BondDetailVo> results = new ArrayList<BondDetailVo>();
		for(BondFavorite fav: favList) {
		    results.add(bondVos.get(fav.getBondUniCode()));
		}
		
		return new  JsonResult<Page<BondDetailVo>>().ok(
				new PageImpl<BondDetailVo>(results, new PageRequest(page-1, limit), resultsize));
		
    }
	
	@ApiOperation(value="导出关注债券明细")
    @RequestMapping(value = "/users/{userId}/favoriteGroups/{groupId}/export/bonds", 
    	method = RequestMethod.GET, produces = "application/json")
    public JsonResult<List<BondExportFavorite>> exportBondsByFavoriteId(
    		@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
    		@ApiParam(name = "groupId", value = "分组Id", required = true) @PathVariable Integer groupId)
	{
		return new  JsonResult<List<BondExportFavorite>>().ok(
				bondFavService.exportFavoritesByGroupId(userId, groupId));
    }
	
	    @ApiOperation(value="批量添加债券关注")
	    @RequestMapping(value = "/users/{userId}/favoriteGroups/favoriteBatch", method = RequestMethod.POST, produces = "application/json")
	    public JsonResult<List<BondFavorite>> addFavoriteBatch(@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
	            @RequestBody BondFavorityAddList list){
	    	
	        return new JsonResult<List<BondFavorite>>().ok(bondFavService.addFavoriteBatch(userId, list));
	    }
	
	@ApiOperation(value="获取债券提醒关注的事件消息")
    @RequestMapping(value = "/users/{userId}/favorites/{favoriteId}/notification", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<Page<BondNotificationMsgVo>> findBondNotificationMsg(@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
    		@ApiParam(name = "favoriteId", value = "债券的关注Id", required = true) @PathVariable Integer favoriteId,  
		    @ApiParam(name = "typeList", value = "消息类型") @RequestParam List<Integer> typeList,
		    @ApiParam(name = "pageIndex", value = "页数,数字:1", required = true) @RequestParam Integer pageIndex,
		    @ApiParam(name = "pageSize", value = "每页显示数量,数字:20", required = true) @RequestParam Integer pageSize){
		//bondFavService.findBondMsgsByBondUniCode(favoriteId,typeList, pageIndex-1, pageSize)
		return new JsonResult<Page<BondNotificationMsgVo>>().ok(bondFavService.findBondNotificationMsg(favoriteId,typeList, pageIndex-1, pageSize));
	}
	
    @ApiOperation(value="识别债券，返回解析结果")
    @RequestMapping(value = "/users/{userId}/bond/bondText", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<BondParseVO> parseBondText(@ApiParam(name = "userId", value = "用户Id", required = true) @PathVariable Long userId,
            @ApiParam(name = "content", value = "需求文本", required = true) @RequestBody String content){
        return new JsonResult<BondParseVO>().ok(bondTextParseService.parseBondText(userId, content));
    }
    
	@ApiOperation(value="修改关注分组下债券")
    @RequestMapping(value = "/users/favoriteGroups/bonds/{favoriteId}", method = RequestMethod.PUT, produces = "application/json")
    public JsonResult<Integer> updateFavorite(@ApiParam(name = "favoriteId", value = "债券的关注Id", required = true) @PathVariable Integer favoriteId, 
    		@RequestBody BondFavoriteParam param){
		
		return new JsonResult<Integer>().ok(bondFavService.updateFavorite(favoriteId, param));
	}
	
    
	@ApiOperation(value="修改关注分组下债券消息为已读")
    @RequestMapping(value = "/users/{userId}/favoriteGroups/favorite", method = RequestMethod.PUT, produces = "application/json")
    public JsonResult<List<Integer>> updateMsgReadStatus(@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId, 
    		@RequestBody BondFavoriteUpdateList param){
		
		return new JsonResult<List<Integer>>().ok(bondFavService.updateMsgReadStatus(userId, param));
	}

	@ApiOperation(value="[消息卡片]推送全局提醒的消息-轮询方式接口调用")
	@RequestMapping(value = "/users/{userId}/bonds/notification", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Page<BondNotificationsVo>> findBondNotifications(@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
																	   @ApiParam(name = "pageIndex", value = "页数,数字:1", required = true) @RequestParam Integer pageIndex,
																	   @ApiParam(name = "pageSize", value = "每页显示数量,数字:20", required = true) @RequestParam Integer pageSize){
		return new JsonResult<Page<BondNotificationsVo>>().ok(bondFavService.findBondNotifications(userId,pageIndex-1,pageSize));
	}
	
	@ApiOperation(value="[消息卡片V2.0]推送全局提醒的消息-轮询方式接口调用新接口")
    @RequestMapping(value = "/users/{userId}/bonds/cardmsg", method = RequestMethod.GET, produces = "application/json")
    public JsonResult<Page<BondNotificationsVo>> findCardmsgs(@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
		    @ApiParam(name = "pageIndex", value = "页数,数字:1", required = true) @RequestParam Integer pageIndex,
		    @ApiParam(name = "pageSize", value = "每页显示数量,数字:20", required = true) @RequestParam Integer pageSize){
		return new JsonResult<Page<BondNotificationsVo>>().ok(bondFavService.findCardMsgList(userId,pageIndex-1,pageSize));
	}

	@ApiOperation(value="[消息卡片]通知消息卡片统计")
    @RequestMapping(value = "/users/{userId}/bonds/notification/statistics", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<BondNotificationStatisticsVo> getNotificationStatistics(@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId){
		return new JsonResult<BondNotificationStatisticsVo>().ok(bondFavService.getNotificationStatistics(userId));
	}
	
	@ApiOperation(value="[消息卡片V2.0]通知消息卡片统计新接口")
    @RequestMapping(value = "/users/{userId}/bonds/cardmsg/statistics", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<BondNotificationStatisticsVo> getCardmsgStatistics(@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId){
		return new JsonResult<BondNotificationStatisticsVo>().ok(bondFavService.getCardMsgStatistics(userId));
	}
	
	@ApiOperation(value="[消息卡片]通知消息卡片更新操作状态")
    @RequestMapping(value = "/users/{userId}/bonds/{bondId}/notification/operation", method = RequestMethod.POST, produces = "application/json")
    public JsonResult<String> addNotificationStatus(@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "bondId", value = "债券唯一编号", required = true) @PathVariable Long bondId,
    		@ApiParam(name = "msgId", value = "消息Id", required = true) @RequestParam Long msgId,
    		@ApiParam(name = "operation", value = "消息操作方式，101：4s自动消失，102 ：查看消息， 103：关闭消息弹出窗", required = true) @RequestParam Integer operation){
		return new JsonResult<String>().ok(bondFavService.addNotificationStatus(userId, bondId, msgId, operation));
	}
	
}
