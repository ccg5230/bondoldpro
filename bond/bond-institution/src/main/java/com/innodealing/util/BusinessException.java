package com.innodealing.util;

/**
 * 
 * @author zhaozhenglai
 * @since 2016年12月22日 下午2:49:23 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String msg;

    private boolean success;

    private String code;

    @Override
    public String getMessage() {
        return this.msg;
    }

    public BusinessException(String msg) {
        this.msg = msg;
        this.code = "-1";
    }

    public BusinessException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(String msg, String[] params) {
        if (msg != null && params != null && params.length > 0) {
            if (!msg.contains("{0}")) {
                this.msg = msg;
                return;
            }
            for (int i = 0; i < params.length; i++) {
                String oldChar = "{" + i + "}";
                msg = msg.replace(oldChar, params[i]);
            }
            this.msg = msg;
        }
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BusinessException() {
        super();
    }

}
