package com.innodealing.controller;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.innodealing.domain.JsonResult;
import com.innodealing.exception.BusinessException;

@Controller
public class BaseController {

    Logger log = LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonResult<String>> handleBusinessException(Exception ex) {
        if (ex instanceof BusinessException) {
            BusinessException bex = (BusinessException) ex;
            JsonResult<String> result = new JsonResult<String>(bex.getCode(), bex.getMsg(), null);
            return new ResponseEntity<JsonResult<String>>(result, HttpStatus.OK);
        } else if (ex instanceof ConstraintViolationException ){
            ConstraintViolationException violationEx = (ConstraintViolationException) ex;
            Set<ConstraintViolation<?>> violations =  violationEx.getConstraintViolations();
            String msg = "亲,验证异常,请检查您的输入是否有误！";
            if(violations != null && violations.size() != 0){
                msg = violations.iterator().next().getMessage();
            }
            ex.printStackTrace();
            log.warn(ex.getMessage(), ex);
            JsonResult<String> result = new JsonResult<String>("-1", msg, null);
            return new ResponseEntity<JsonResult<String>>(result, HttpStatus.OK);
        }
        else {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            JsonResult<String> result = new JsonResult<String>("-1", "啊哦!系统繁忙,请稍后再试~",null );
            return new ResponseEntity<JsonResult<String>>(result, HttpStatus.OK);
        }
    }

    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<JsonResult<String>>
    // handleException(BusinessException ex) {
    // JsonResult<String> result = new JsonResult<String>("-1", "fail",
    // "啊哦!系统繁忙,请稍后再试~");
    // return new ResponseEntity<JsonResult<String>>(result,
    // HttpStatus.BAD_REQUEST);
    // }
}
