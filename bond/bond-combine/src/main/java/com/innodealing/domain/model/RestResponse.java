package com.innodealing.domain.model;

import java.io.Serializable;

public class RestResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Integer CODE_SUCCESS = Integer.valueOf(0);
    private static final Integer CODE_FAILURE = Integer.valueOf(-1);
    private static final String MSG_SUCCESS = "success";
    private static final String MSG_FAILURE = "failure";
    private Integer code;
    private String message;
    private T data;

    public RestResponse() {
    }

    public RestResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> RestResponse<T> Success(T data) {
        return new RestResponse(CODE_SUCCESS, "success", data);
    }

    public static <T> RestResponse<T> Success(String message, T data) {
        return new RestResponse(CODE_SUCCESS, message, data);
    }

    public static <T> RestResponse<T> Fail(T data) {
        return new RestResponse(CODE_FAILURE, "failure", data);
    }

    public static <T> RestResponse<T> Fail(String message, T data) {
        return new RestResponse(CODE_FAILURE, message, data);
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }
}