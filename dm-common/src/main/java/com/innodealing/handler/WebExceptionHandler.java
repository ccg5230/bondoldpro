package com.innodealing.handler;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.innodealing.exception.BusinessException;
import com.innodealing.vo.JsonResult;
/**
 * exception handler
 * @author zhaozhenglai
 * @since 2016年12月22日 下午3:17:42 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
@Controller
public class WebExceptionHandler {

	Logger log = LoggerFactory.getLogger(WebExceptionHandler.class);
    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonResult<String>> handleBusinessException(Exception ex) {
        ex.printStackTrace();
        if (ex instanceof BusinessException) {
            BusinessException bex = (BusinessException) ex;
            JsonResult<String> result = new JsonResult<String>(bex.getCode(), bex.getMsg(), null);
            log.error(ex.getMessage(),ex);
            return new ResponseEntity<JsonResult<String>>(result, HttpStatus.OK);
        } else if (ex instanceof ConstraintViolationException ){
            ConstraintViolationException violationEx = (ConstraintViolationException) ex;
            Set<ConstraintViolation<?>> violations =  violationEx.getConstraintViolations();
            String msg = "亲,验证异常,请检查您的输入是否有误！";
            if(violations != null && violations.size() != 0){
                msg = violations.iterator().next().getMessage();
            }
            JsonResult<String> result = new JsonResult<String>("-1", msg, null);
            log.error(ex.getMessage(),ex);
            return new ResponseEntity<JsonResult<String>>(result, HttpStatus.OK);
        }
        else {
            JsonResult<String> result = new JsonResult<String>("-1", "啊哦!系统繁忙,请稍后再试~",null );
            log.error(ex.getMessage(),ex);
            return new ResponseEntity<JsonResult<String>>(result, HttpStatus.OK);
        }
    }
}
