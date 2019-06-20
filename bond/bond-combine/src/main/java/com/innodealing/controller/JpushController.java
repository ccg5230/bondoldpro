package com.innodealing.controller;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import com.innodealing.service.JpushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 极光推送
 */
@RestController
public class JpushController {

    protected static final Logger logger = LoggerFactory.getLogger(JpushController.class);

    @RequestMapping(value = "push", method = RequestMethod.GET)
    public void push(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置好账号的ACCESS_KEY和SECRET_KEY
        String appKey = "8f5c18bd27facf6c479ceb5d";
        String masterSecret = "1e4bfefb4bc3e2f1c2f732a3";

        JpushService jpush = new JpushService();
        jpush.testSendPush(appKey, masterSecret);
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
        try {
            PushResult result = jpushClient.sendMessageAll("jy7tjy");
            logger.info("Got result - " + result);
        } catch (APIConnectionException e) {
            logger.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            logger.error("Error response from JPush server. Should review and fix it. ", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Code: " + e.getErrorCode());
            logger.info("Error Message: " + e.getErrorMessage());
        }

        PrintWriter out = response.getWriter();
        out.print("");
        out.flush();
        out.close();
    }
}
