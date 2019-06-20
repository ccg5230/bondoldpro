package com.innodealing.controller;



import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.domain.JsonResult;
import com.innodealing.service.BondAnnAttInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "公告文件上传下载")
@RestController
@RequestMapping("api/bond/info")
public class BondAnnAttInfoController extends BaseController{
	private final Logger log = LoggerFactory.getLogger(BondAnnAttInfoController.class);
	@Autowired   
    private BondAnnAttInfoService bondAnnAttInfoService;
	
	@ApiOperation(value = "上传公告附件")
	@RequestMapping(value = "/upload/srcurl", method = RequestMethod.GET)
	public JsonResult<String> queryByUrlAndTime() throws Exception{
		JsonResult<String> rs = bondAnnAttInfoService.bondUploadOss();
		String code = rs.getCode();
		Date now=new Date();
		SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		if(code.equals("0")){
		    log.info(myFmt.format(now)+ "-------->上传完成");
		}
        return new JsonResult<String>().ok("success");
    }
	
	@ApiOperation(value = "发送公告附件Mq")
    @RequestMapping(value = "/upload/sendMq", method = RequestMethod.GET)
    public JsonResult<String> sendMq() throws Exception{
        JsonResult<String> rs = bondAnnAttInfoService.sendAttInfo2Mq();
        String code = rs.getCode();
        Date now=new Date();
        SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        if(code.equals("0")){
            log.info(myFmt.format(now)+ "待上传公告附件发送mq消息队列成功");
        }
        return new JsonResult<String>().ok("success");
    }
	
	@ApiOperation(value = "新债公告附件上传最大次数失败后删除附件数据")
    @RequestMapping(value = "/upload/deleteUploadFailedAttDatas", method = RequestMethod.GET)
    public JsonResult<String> deleteUploadFailedAttDatas(){
        JsonResult<String> rs = bondAnnAttInfoService.deleteUploadFailedAttDatas();
        String code = rs.getCode();
        Date now=new Date();
        SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        if(code.equals("0")){
            log.info(myFmt.format(now)+ "-------->新债公告附件上传最大次数失败后删除附件数据成功");
        }
        return new JsonResult<String>().ok("success");
    }
	
	@ApiOperation(value = "下载公告附件文件")
	@RequestMapping(value = "/download/annAttInfo", method = RequestMethod.GET)
    public JsonResult<String> downloadAttInfo(
            @ApiParam(name = "id") @RequestParam(required = true) Long id,
            HttpServletRequest request,HttpServletResponse response){
        JsonResult<String> num = bondAnnAttInfoService.downloadAttInfo(id,request,response);
        String code = num.getCode();
        Date now=new Date();
        SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        if(code.equals("0")){
            log.info(myFmt.format(now)+ "-------->下载完成");
        }
        return new JsonResult<String>().ok("success");
    }
	
	@ApiOperation(value = "债券主体评级公告附件ftp下载")
    @RequestMapping(value = "/download/downLoadComRatingAtt", method = RequestMethod.GET)
    public JsonResult<String> downLoadComRatingAtt(
            @ApiParam(name = "annId") @RequestParam(required = true) Long annId,
            HttpServletRequest request,HttpServletResponse response) {
        JsonResult<String> result = bondAnnAttInfoService.downloadComRatingAttInfo(annId,request,response);
        String code = result.getCode();
        Date now=new Date();
        SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        if(code.equals("0")){
            log.info(myFmt.format(now)+ "-------->下载完成");
        }
        return new JsonResult<String>().ok("success");
    }

}
