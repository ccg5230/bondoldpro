package com.innodealing.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.bond.service.BondAnnAttInfoFtpFilePathService;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.dm.bond.BondAnnAttInfoFtpFilePath;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "债券公告pdf文件接口")
@RestController
@RequestMapping("api/bond/")
public class BondFileController extends BaseController{
	
	private @Autowired BondAnnAttInfoFtpFilePathService BondAnnAttInfoService;
	
	
	
	@ApiOperation(value="获取债券公告文件oss路径")
    @RequestMapping(value = "/annaAtt/info", 
    	method = RequestMethod.GET, produces = "application/json")
	public JsonResult<Page<BondAnnAttInfoFtpFilePath>> findFtpFilePathByPublishDate(
    		@ApiParam(name = "id", value = "Id", required = true) @RequestParam Integer id,
    		@ApiParam(name = "page", value = "页数", required = false) @RequestParam(defaultValue="1") Integer page,
			@ApiParam(name = "limit", value = "每页显示数量", required = false) @RequestParam(defaultValue="20") Integer limit){
		
		List<BondAnnAttInfoFtpFilePath> list = BondAnnAttInfoService.findFtpFilePathByPublishDate(id,(page-1)*limit,limit);
		
		
		
		//查询数据库中所有的ftpfilepath的总数量
		int num = BondAnnAttInfoService.getFtpFilePathCount();
		return new  JsonResult<Page<BondAnnAttInfoFtpFilePath>>().ok(
				new PageImpl<BondAnnAttInfoFtpFilePath>(list, new PageRequest(page-1, limit), num));
	}

}
