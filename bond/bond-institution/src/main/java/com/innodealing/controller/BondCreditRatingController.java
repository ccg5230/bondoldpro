package com.innodealing.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.innodealing.model.mysql.BondCreditRatingGroup;
import com.innodealing.model.req.CreditRatingGroupReq;
import com.innodealing.model.req.CreditRatingGroupchangeReq;
import com.innodealing.model.req.CreditRatingImportReq;
import com.innodealing.model.vo.CreditRatingAuditInfo;
import com.innodealing.model.vo.CreditRatingBondVo;
import com.innodealing.model.vo.CreditRatingGroupVo;
import com.innodealing.model.vo.CreditRatingIssuerVo;
import com.innodealing.model.vo.CreditRatingParsed;
import com.innodealing.model.vo.CreditRatingParsedInfoVo;
import com.innodealing.service.BondCreditRatingService;
import com.innodealing.service.BondPubService;
import com.innodealing.service.FileHandleService;
import com.innodealing.util.BusinessException;
import com.innodealing.util.JsonResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "信评池相关接口")
@RestController
@RequestMapping("api/bond/")
public class BondCreditRatingController extends BaseController {

	private @Autowired BondCreditRatingService creditRatingService;

	@ApiOperation(value = "添加信评分组")
	@RequestMapping(value = "/users/{userId}/creditrating/group", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Integer> addGroup(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@RequestBody CreditRatingGroupReq req) {

		return new JsonResult<Integer>().ok(creditRatingService.addGroup(userId, req));
	}

	@ApiOperation(value = "删除信评分组")
	@RequestMapping(value = "/user/creditrating/group/{groupId}", method = RequestMethod.DELETE, produces = "application/json")
	public JsonResult<Integer> deleteGroup(
			@ApiParam(name = "groupId", value = "分组Id", required = true) @PathVariable Long groupId) {

		return new JsonResult<Integer>().ok(creditRatingService.deleteGroup(groupId));
	}

	@ApiOperation(value = "修改信评分组")
	@RequestMapping(value = "/user/creditrating/group/{groupId}", method = RequestMethod.PUT, produces = "application/json")
	public JsonResult<Integer> updateGroup(
			@ApiParam(name = "groupId", value = "分组Id", required = true) @PathVariable Long groupId,
			@RequestBody CreditRatingGroupReq req) {

		return new JsonResult<Integer>().ok(creditRatingService.updateGroup(groupId, req));
	}

	@ApiOperation(value = "获取所有信评分组")
	@RequestMapping(value = "/users/{userId}/creditrating/groups", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<List<CreditRatingGroupVo>> findGroups(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId) {

		return new JsonResult<List<CreditRatingGroupVo>>().ok(creditRatingService.findGroups(userId));
	}

	@ApiOperation(value = "获取信评分组的发行人")
	@RequestMapping(value = "/users/{userId}/creditrating/group/{groupId}/issuers", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Page<CreditRatingIssuerVo>> findIssuers(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "groupId", value = "分组Id", required = true) @PathVariable Long groupId,
			@ApiParam(name = "groupedFlag", value = "是否分组， 1=已分组，0=未分组", required = false) @RequestParam(defaultValue = "0") Integer groupedFlag,
			@ApiParam(name = "ratingId", value = "内部评级Id") @RequestParam(defaultValue = "0") Integer ratingId,
			@ApiParam(name = "induId", value = "行业Id") @RequestParam(defaultValue = "0") Integer induId,
			@ApiParam(name = "query", value = "查询关键词") @RequestParam(defaultValue = " ") String query,
			@ApiParam(name = "pageIndex", value = "页数,数字:1", required = true) @RequestParam(defaultValue = "1") Integer pageIndex,
			@ApiParam(name = "pageSize", value = "每页显示数量,数字:20", required = true) @RequestParam(defaultValue = "20") Integer pageSize) {

		return new JsonResult<Page<CreditRatingIssuerVo>>().ok(creditRatingService.findIssuers(userId, groupId,
				groupedFlag, ratingId, induId, query, pageIndex - 1, pageSize));
	}

	@ApiOperation(value = "导出信评分组的发行人Excel")
	@RequestMapping(value = "/users/{userId}/creditrating/group/{groupId}/issuersExport", method = RequestMethod.GET, produces = "application/json")
	public void exportIssuers(@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "groupId", value = "分组Id", required = true) @PathVariable Long groupId,
			@ApiParam(name = "groupedFlag", value = "是否分组， 1=已分组，0=未分组", required = false) @RequestParam(defaultValue = "0") Integer groupedFlag,
			@ApiParam(name = "ratingId", value = "内部评级Id") @RequestParam(defaultValue = "0") Integer ratingId,
			@ApiParam(name = "induId", value = "行业Id") @RequestParam(defaultValue = "0") Integer induId,
			@ApiParam(name = "query", value = "查询关键词") @RequestParam(defaultValue = " ") String query,
			@ApiParam(name = "response", value = "response") HttpServletResponse response) throws Exception {

		creditRatingService.exportIssuers(userId, groupId, groupedFlag, ratingId, induId, query, response);
	}

	@ApiOperation(value = "导出信评分组的债券Excel")
	@RequestMapping(value = "/users/{userId}/creditrating/group/{groupId}/bondsExport", method = RequestMethod.GET, produces = "application/json")
	public void exportBonds(@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "groupId", value = "分组Id", required = true) @PathVariable Long groupId,
			@ApiParam(name = "adviceId", value = "投资建议Id") @RequestParam(defaultValue = "0") Integer adviceId,
			@ApiParam(name = "ratingId", value = "内部评级Id") @RequestParam(defaultValue = "0") Integer ratingId,
			@ApiParam(name = "induId", value = "行业Id") @RequestParam(defaultValue = "0") Integer induId,
			@ApiParam(name = "query", value = "查询关键词,债券简称，债券代码，发行人简称") @RequestParam(defaultValue = " ") String query,
			@ApiParam(name = "response", value = "response") HttpServletResponse response) throws Exception {

		creditRatingService.exportBonds(userId, groupId, adviceId, ratingId, induId, query, response);
	}

	@ApiOperation(value = "获取信评分组的债券")
	@RequestMapping(value = "/users/{userId}/creditrating/group/{groupId}/bonds", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Page<CreditRatingBondVo>> findBonds(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "groupId", value = "分组Id", required = true) @PathVariable Long groupId,
			@ApiParam(name = "adviceId", value = "投资建议Id") @RequestParam(defaultValue = "0") Integer adviceId,
			@ApiParam(name = "ratingId", value = "内部评级Id") @RequestParam(defaultValue = "0") Integer ratingId,
			@ApiParam(name = "induId", value = "行业Id") @RequestParam(defaultValue = "0") Integer induId,
			@ApiParam(name = "query", value = "查询关键词,债券简称，债券代码，发行人简称") @RequestParam(defaultValue = " ") String query,
			@ApiParam(name = "pageIndex", value = "页数,数字:1", required = true) @RequestParam(defaultValue = "1") Integer pageIndex,
			@ApiParam(name = "pageSize", value = "每页显示数量,数字:20", required = true) @RequestParam(defaultValue = "20") Integer pageSize) {

		return new JsonResult<Page<CreditRatingBondVo>>().ok(creditRatingService.findBonds(userId, groupId, adviceId,
				ratingId, induId, query, pageIndex - 1, pageSize));
	}

	@ApiOperation(value = "导入信评Excel")
	@RequestMapping(value = "/users/{userId}/creditrating/group/{groupId}/excelparse", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<CreditRatingParsedInfoVo> parseExcel(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "groupId", value = "分组Id", required = true) @PathVariable Long groupId,
			@ApiParam(name = "sourceFile", value = "上传文件", required = true) @RequestParam("sourceFile") MultipartFile sourceFile) {
		String fileName = sourceFile.getOriginalFilename();
		int lastIndex = fileName.lastIndexOf(".");
		if (!fileName.substring(lastIndex, fileName.length()).equalsIgnoreCase(FileHandleService.SUFFIX_2007)) {
			throw new BusinessException("上传文件的类型不合适");
		}
		return new JsonResult<CreditRatingParsedInfoVo>()
				.ok(creditRatingService.parseExcel(userId, groupId, sourceFile));
	}

	@ApiOperation(value = "从Excel中导入信评数据")
	@RequestMapping(value = "/users/{userId}/creditrating/group/{groupId}/excelimport", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<Object> importExcel(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "groupId", value = "分组Id", required = true) @PathVariable Long groupId,
			@ApiParam(name = "usedFlag", value = "是否忽略属于用于其他信评组, 1=是，0=否", required = true) @RequestParam Integer usedFlag,
			@ApiParam(name = "repeatFlag", value = "当天重复导入数据, 1=是，0=否", required = true) @RequestParam Integer repeatFlag,
			@ApiParam(name = "importFlag", value = "导入数据的入口, 1=从债券导入，0=从发行人导入", required = true) @RequestParam Integer importFlag,
			@ApiParam(name = "data", value = "导入数据", required = true) @RequestBody CreditRatingImportReq data)
			throws Exception {

		return new JsonResult<>().ok(creditRatingService.importExcel(userId, groupId, usedFlag,repeatFlag, importFlag, data));
	}

	@ApiOperation(value = "添加查询的数据到导入的tab")
	@RequestMapping(value = "/users/{userId}/creditrating/item", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<CreditRatingParsed> parseItem(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "bondId", value = "债券ID") @RequestParam(defaultValue = "0") Long bondId,
			@ApiParam(name = "issuerId", value = "主体ID", required = true) @RequestParam Long issuerId)
			throws Exception {

		return new JsonResult<CreditRatingParsed>().ok(creditRatingService.parseItem(userId, bondId, issuerId));
	}

	@ApiOperation(value = "下载模板Excel")
	@RequestMapping(value = "/users/{userId}/creditrating/exceldownload", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<String> downloadExcel(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId) {
		return new JsonResult<String>().ok(creditRatingService.downloadExcel(userId));
	}

	@ApiOperation(value = "主体所在信评池中的分组")
	@RequestMapping(value = "/users/{userId}/creditrating/creditRatingGroup", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<BondCreditRatingGroup> findCreditRatingGroup(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "issuerId", value = "主体ID", required = true) @RequestParam Long issuerId) {
		return new JsonResult<BondCreditRatingGroup>().ok(creditRatingService.findCreditRatingGroup(userId, issuerId));
	}

	@ApiOperation(value = "默认上传OSS模板Excel文件接口--仅用于内部人员使用")
	@RequestMapping(value = "/user/creditrating/uploadExcel", consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE }, method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> uploadExcelOss(
			@ApiParam(name = "tokenCode", value = "上传文件Code", required = true) @RequestParam String tokenCode,
			@ApiParam(name = "sourceFile", value = "上传文件", required = true) @RequestParam("sourceFile") MultipartFile sourceFile)
			throws Exception {
		return new JsonResult<String>().ok(creditRatingService.uploadExcelOss(tokenCode, sourceFile));
	}

	@ApiOperation(value = "主体所在信评池中的分组")
	@RequestMapping(value = "/users/{userId}/creditrating/groupChange", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> changeGroup(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "req", value = "主体和变更组的数据", required = true) @RequestBody CreditRatingGroupchangeReq req) {
		return new JsonResult<String>().ok(creditRatingService.changeGroup(userId, req));
	}

	@ApiOperation(value = "查看发行人分析、个券分析")
	@RequestMapping(value = "/user/{userId}/creditrating/analysisText", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<String> findAnalysisText(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "bondId", value = "债券ID", required = true) @RequestParam Long bondId,
			@ApiParam(name = "requestId", value = "内部评级ID 或者 投资建议ID", required = true) @RequestParam Integer requestId,
			@ApiParam(name = "textFlag", value = "0=选择内部评级ID， 1=选择投资建议ID", required = true) @RequestParam Integer textFlag) {
		return new JsonResult<String>().ok(creditRatingService.findAnalysisText(userId, bondId, requestId, textFlag));
	}

	@ApiOperation(value = "针对主体查看发行人分析")
	@RequestMapping(value = "/user/{userId}/creditrating/issuer/analysisText", method = RequestMethod.GET, produces = "application/json")
	public JsonResult<String> findIssuerAnalysisText(
			@ApiParam(name = "userId", value = "账号Id", required = true) @PathVariable Integer userId,
			@ApiParam(name = "issuerId", value = "债券主体ID", required = true) @RequestParam Long issuerId,
			@ApiParam(name = "requestId", value = "内部评级ID", required = true) @RequestParam Integer requestId) {
		return new JsonResult<String>().ok(creditRatingService.findIssuerAnalysisText(userId, issuerId, requestId));
	}

	@Autowired
	private BondPubService bondPubService;

	@ApiOperation(value = "查看信评审核信息")
	@RequestMapping(value = "/user/{userId}/creditrating/issuer/findRatingAuditInfo", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<List<CreditRatingAuditInfo>> findRatingAuditInfo(
			@ApiParam(name = "bondUniCode", value = "发行人标识", required = true) @RequestParam String bondUniCode,
			@ApiParam(name = "fatId", value = "信评id", required = true) @RequestParam Long fatId) {
		return new JsonResult<List<CreditRatingAuditInfo>>().ok(bondPubService.getRatingAuditInfo(bondUniCode, fatId));
	}

	
	@ApiOperation(value = "针对重复主体的数据做更新[更新原先的数据]")
	@RequestMapping(value = "/user/creditrating/updateIssuersGroup", method = RequestMethod.POST, produces = "application/json")
	public JsonResult<String> updateIssuersGroup(){
		
		return new JsonResult<String>().ok(creditRatingService.updateIssuersGroup());
	}
	
}
