package com.innodealing.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.innodealing.model.mysql.BondInstRatingFile;
import com.innodealing.model.mysql.BondInstRatingHist;
import com.innodealing.service.BondInstRatingHistService;
import com.innodealing.util.JsonResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "债劵内部评级")
@RestController
@RequestMapping("api/bond/instRating")
public class BondInstRatingHistController extends BaseController {

	@Autowired
	private BondInstRatingHistService bondInstRatingHistService;

	@ApiOperation(value = "内部信评变化")
	@RequestMapping(value = "/findInstRatingHistList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<List<BondInstRatingHist>> findInstRatingHistList(@RequestHeader("userid") Integer userid,
			@ApiParam(name = "uniCode", value = "编号", required = false)  @RequestParam Long uniCode,
			@ApiParam(name = "type", value = "1,发行人2,债劵", required = false) @RequestParam(defaultValue = "1") Integer type,
			@ApiParam(name = "requestType", value = "1,内部评级变化", required = false) @RequestParam(defaultValue = "0") Integer requestType) {
		return new JsonResult<List<BondInstRatingHist>>()
				.ok(bondInstRatingHistService.findInstRatingHistList(userid, uniCode, type , requestType));
	}
	
	@ApiOperation(value = "发起信评")
	@RequestMapping(value = "/insertBondInstRatingHist", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Boolean> insertBondInstRatingHist(@RequestHeader("userid") Integer userid,
			@ApiParam(name = "bondInstRatingHist", value = "信评记录", required = true)  @RequestBody BondInstRatingHist bondInstRatingHist) {
		return new JsonResult<Boolean>()
				.ok(bondInstRatingHistService.insertBondInstRatingHist(userid,bondInstRatingHist));
	}
	
	@ApiOperation(value = "更新信评")
	@RequestMapping(value = "/updateBondInstRatingHist", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Boolean> updateBondInstRatingHist(@RequestHeader("userid") Integer userid,
			@ApiParam(name = "bondInstRatingHist", value = "信评记录", required = true)  @RequestBody BondInstRatingHist bondInstRatingHist) {
		return new JsonResult<Boolean>()
				.ok(bondInstRatingHistService.updateBondInstRatingHist(userid,bondInstRatingHist));
	}
	
	@ApiOperation(value = "上传相关资料")
	@RequestMapping(value = "bondInstRatingFile", consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE }, method = RequestMethod.POST, produces = "application/json")
	public JsonResult<BondInstRatingFile> bondInstRatingFile(
			@RequestHeader("userid") Integer userid,
			@ApiParam(name = "type", value = "1发起相关资料 2评级说明相关资料 3投资说明相关资料", required = false) @RequestParam(defaultValue = "1") Integer type,
			@ApiParam(name = "sourceFile", value = "上传文件", required = true) @RequestParam("sourceFile") MultipartFile sourceFile)
			throws Exception {
		return new JsonResult<BondInstRatingFile>().ok(bondInstRatingHistService.uploadFileOss(userid,type,sourceFile));
	}
	
	@ApiOperation(value = "信评需求列表")
	@RequestMapping(value = "/findInstRatingHistAll", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<PageInfo<List<BondInstRatingHist>>> findInstRatingHistAll(@RequestHeader("userid") Integer userid,
		@ApiParam(name = "status", value = "信评状态 1待信评，2待审核，3已完成 4信评处理中", required = false) @RequestParam(defaultValue = "1") Integer status,
		@ApiParam(name = "type", value = "查看状态 1发起,2,信评3,审核", required = false) @RequestParam(defaultValue = "1") Integer type,
		@ApiParam(name = "flay", value = "是否只看自己发起的", required = false) @RequestParam(defaultValue = "false") Boolean flay,
		@ApiParam(name = "page", value = "页数", required = false) @RequestParam(defaultValue = "1") Integer page,
		@ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue = "10") Integer limit) {
		return new JsonResult<PageInfo<List<BondInstRatingHist>>>()
				.ok(bondInstRatingHistService.findInstRatingHistAll(userid,status,type,flay,page,limit));
	}
	
	@ApiOperation(value = "信评需求详细信息")
	@RequestMapping(value = "/findInstRatingHist", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<BondInstRatingHist> findInstRatingHist(@RequestHeader("userid") Integer userid,
			@ApiParam(name = "uniCode", value = "编号", required = false)  @RequestParam Long uniCode,
			@ApiParam(name = "type", value = "1,发行人2,债劵", required = false) @RequestParam(defaultValue = "1") Integer type,
			@ApiParam(name = "id", value = "id", required = false) @RequestParam(required=false) Long id) {
		return new JsonResult<BondInstRatingHist>()
				.ok(bondInstRatingHistService.findInstRatingHist(userid,uniCode,type,id));
	}
	
	@ApiOperation(value = "撤销信评记录")
	@RequestMapping(value = "/deleteInstRatingHistById", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Integer> deleteInstRatingHistById(
			@RequestHeader("userid") Integer userid,
			@ApiParam(name = "id", value = "信评记录id", required = true)  @RequestParam Long id) {
		return new JsonResult<Integer>()
				.ok(bondInstRatingHistService.deleteInstRatingHistById(userid,id));
	}
	
	@ApiOperation(value = "信评状态  1待信评，2待审核，3已完成 4,正在信评中 ")
	@RequestMapping(value = "/findInstRatingHistStatus", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Map<String, Object>> findInstRatingHistStatus(@RequestHeader("userid") Integer userid,
			@ApiParam(name = "bondUniCode", value = "债劵code", required = false)  @RequestParam(required=false) Long bondUniCode,
			@ApiParam(name = "comUniCode", value = "发行人code", required = false) @RequestParam(required=false) Long comUniCode,
			@ApiParam(name = "type", value = "1,发行人2,债劵", required = false) @RequestParam(defaultValue = "1") Integer type) {
		return new JsonResult<Map<String, Object>>()
				.ok(bondInstRatingHistService.findInstRatingHistStatus(userid,bondUniCode,comUniCode,type));
	}
	
	@ApiOperation(value = "信评各状态总数")
	@RequestMapping(value = "/findInstRatingHistStatusCount", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<List<Map<String,Integer>>> findInstRatingHistStatusCount(@RequestHeader("userid") Integer userid) {
		return new JsonResult<List<Map<String,Integer>>>()
				.ok(bondInstRatingHistService.findInstRatingHistStatusCount(userid));
	}
	
	@ApiOperation(value = "不能发起信评")
	@RequestMapping(value = "/isCommentary", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Map<String,Integer>> isCommentary(@RequestHeader("userid") Integer userid,
			@ApiParam(name = "bondUniCode", value = "债劵code", required = false)  @RequestParam Long bondUniCode,
			@ApiParam(name = "comUniCode", value = "发行人code", required = false) @RequestParam Long comUniCode){
		return new JsonResult<Map<String,Integer>>()
				.ok(bondInstRatingHistService.isCommentary(userid,bondUniCode,comUniCode));
	}
	
	@ApiOperation(value = "信评状态更新")
	@RequestMapping(value = "/commentaryStatus", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> commentaryStatus(@RequestHeader("userid") Integer userid,
			@ApiParam(name = "status", value = "1,进行中0,结束", required = false)  @RequestParam Integer status,
			@ApiParam(name = "bondUniCode", value = "债劵code", required = false)  @RequestParam Long bondUniCode,
			@ApiParam(name = "comUniCode", value = "发行人code", required = false) @RequestParam Long comUniCode){
		return new JsonResult<String>()
				.ok(bondInstRatingHistService.commentaryStatus(userid,status,bondUniCode,comUniCode));
	}
	
	@ApiOperation(value = "发起信评详细信息")
	@RequestMapping(value = "/findBondInstRatingHist", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<BondInstRatingHist> findBondInstRatingHist(@RequestHeader("userid") Integer userid,
			@ApiParam(name = "uniCode", value = "编号", required = false)  @RequestParam Long uniCode,
			@ApiParam(name = "type", value = "1,发行人2,债劵", required = false) @RequestParam(defaultValue = "1") Integer type) {
		return new JsonResult<BondInstRatingHist>()
				.ok(bondInstRatingHistService.findBondInstRatingHist(userid,uniCode,type));
	}
	
	
	@ApiOperation(value = "信评历史记录详细信息")
	@RequestMapping(value = "/findInstRatingHistById", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<BondInstRatingHist> findInstRatingHistById(@RequestHeader("userid") Integer userid,
			@ApiParam(name = "id", value = "id", required = false)  @RequestParam Long id) {
		return new JsonResult<BondInstRatingHist>()
				.ok(bondInstRatingHistService.findInstRatingHistById(id,userid));
	}
	
	//信评审核
	@ApiOperation(value = "信评审核")
	@RequestMapping(value = "/checkInstRatingHist", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Boolean> checkInstRatingHist(@RequestHeader("userid") Integer userid,
			@ApiParam(name = "bondInstRatingHist", value = "信评记录", required = true)  @RequestBody BondInstRatingHist bondInstRatingHist) {
		return new JsonResult<Boolean>()
				.ok(bondInstRatingHistService.checkInstRatingHist(bondInstRatingHist,userid));
	}
	
	
	//信评记录详细信息
	
	//新增字段 1.是否延用上次内部评级  是  不处理  绑定 信评记录ID给 当前信评记录    否  新增一条信评记录 并绑定      2.退回理由   3组长建议时间
	
	//信评审核不成功  状态变成 1  待信评
	
	//信评记录为待信评存在信评时间的最新一条记录不能撤销 （撤销条件）
	//发起信评 增加一条信评记录 信评时间是空的  当点击信评 修改信评时间
	//撤销删除当前信评记录
	
	//发起信评 待信评 待审核状态 存在记录 则不能发起 反之可以发起， 发起信评保存的时候二次判断
	
	
	
	//发起信评  根据发起时间最新一条
	
	//信评时 查上一次最新已完成数据  以及 债劵关联发行人最新关系  保存信评历史记录 并且保存当前关系
	
	//待审核 已完成 信评追踪全部查询信评历史记录
	
	//信评审核不成功  状态变成 1  待信评 删除 当前保存关系 
	
	
	
	
}
