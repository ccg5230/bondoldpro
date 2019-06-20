package com.innodealing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.innodealing.handler.WebExceptionHandler;
import com.innodealing.service.SecurityService;
import com.innodealing.vo.JsonResult;

@RestController
@RequestMapping("/security")
public class SecurityController extends WebExceptionHandler{
    
    @Autowired private SecurityService securityService;

    /**
     * 用户获取token
     * @param userAccount 账号
     * @param password 密码【MD5】
     * @return
     */
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public JsonResult<String> getToken(String userAccount,String password){
        String result = securityService.getToken(userAccount, password);
        return new JsonResult<String>().ok(result);
    }

    
}
