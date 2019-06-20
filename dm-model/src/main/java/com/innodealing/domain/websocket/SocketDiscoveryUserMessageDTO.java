package com.innodealing.domain.websocket;

import io.swagger.annotations.ApiModelProperty;

public class SocketDiscoveryUserMessageDTO {
    private String message;

    private String date;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "债券类型:1-今日成交;2-今日报价")
    private Integer discoveryType;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getDiscoveryType() {
        return discoveryType;
    }

    public void setDiscoveryType(Integer discoveryType) {
        this.discoveryType = discoveryType;
    }
}
