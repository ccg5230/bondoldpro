package com.innodealing.task.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.innodealing.domain.JsonResult;
import com.innodealing.service.BondAnnAttInfoService;
import com.innodealing.service.BondDealDataService;
import com.innodealing.service.BondQuoteIntegrationService;

/**
 * @author stephen.ma
 * @date 2016年9月1日
 * @clasename ScheduledTasks.java
 * @decription TODO
 */
@EnableScheduling
@Component
public class ScheduledTasks {

    private final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    // private @Autowired BondQuoteIntegrationService
    // bondQuoteIntegrationService;
    //
    // private @Autowired BondDealDataService dealDataService;
    @Autowired
    private BondAnnAttInfoService bondAnnAttInfoService;

    // //@Scheduled(fixedRate=600000)
    // @Scheduled(cron = "0 0 * * * ?")
    // public void bondUploadFtp() throws Exception{
    // JsonResult<String> rs = bondAnnAttInfoService.bondUploadOss();
    // String code = rs.getCode();
    // Date now=new Date();
    // SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // if(code.equals("0")){
    // log.info(myFmt.format(now)+ "-------->上传完成");
    // }
    // }

//    @Scheduled(cron = "0 0 0/8 * * ?")
//    public void deleteUploadFaliedAttDatas() throws Exception {
//        JsonResult<String> rs = bondAnnAttInfoService.deleteUploadFailedAttDatas();
//        String code = rs.getCode();
//        Date now = new Date();
//        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        if (code.equals("0")) {
////            log.info(myFmt.format(now) + "-------->删除成功");
//        } else {
//            log.error(myFmt.format(now) + "-------->删除失败");
//        }
//    }

    /**
     * 每x秒执行一次
     */
    // @Scheduled(cron = "0/5 * * * * ?")
    // @Scheduled(fixedRate=5000)
    // public void scheduler() {
    // bondQuoteIntegrationService.saveBondSingleComparisonDoc();
    // bondQuoteIntegrationService.saveWorkingdateSixdaysDoc();
    // bondQuoteIntegrationService.saveBondBestQuoteDoc();
    // bondQuoteIntegrationService.saveBondQuoteTodaycurveDoc();
    // System.out.println("5s 執行一次。。");
    // }

    // //5分钟执行一次
    // @Scheduled(fixedRate=300000)
    // public void bondBestQuoteScheduler() {
    // LOGGER.debug("bondBestQuoteScheduler is begining in every 5 mins.");
    //
    // bondQuoteIntegrationService.saveBondBestQuoteDoc();
    // }
    //
    // //4分钟执行一次
    // @Scheduled(fixedRate=240000)
    // public void bondQuoteSingleComparisonScheduler() {
    // LOGGER.debug("bondQuoteSingleComparisonScheduler is begining.");
    //
    // bondQuoteIntegrationService.saveBondSingleComparisonDoc();
    // }
    //
    // //3分钟执行一次
    // @Scheduled(fixedRate=180000)
    // public void bondQuoteTodaycurveScheduler() {
    // LOGGER.debug("bondQuoteTodaycurveScheduler is begining in every 3
    // mins.");
    //
    // bondQuoteIntegrationService.saveBondQuoteTodaycurveDoc();
    // }

    /*
     * //每天下午7点，11点 触发一次
     * 
     * @Scheduled(cron = "0 0 19,23 * * ?") public void
     * bondQuoteHistorycurveScheduler() {
     * LOGGER.debug("bondQuoteHistorycurveScheduler is begining.");
     * 
     * bondQuoteIntegrationService.saveBondQuoteHistorycurveDoc(); }
     * 
     * //每天晚上10点 触发一次
     * 
     * @Scheduled(cron = "0 0 22 * * ?") public void
     * workingdateSixdaysScheduler() {
     * LOGGER.debug("workingdateSixdaysScheduler is begining.");
     * 
     * bondQuoteIntegrationService.saveWorkingdateSixdaysDoc(); }
     */

}
