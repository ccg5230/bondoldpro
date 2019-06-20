package com.innodealing.domain.vo;

import io.swagger.annotations.ApiModelProperty;

public class SocketPortfolioNotificationVO {
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "债券bondUniCode")
    private Long bondUniCode;

    @ApiModelProperty(value = "投组")
    private Long groupId;

    @ApiModelProperty(value = "消息分类")
    private Integer eventType;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getBondUniCode() {
        return bondUniCode;
    }

    public void setBondUniCode(Long bondUniCode) {
        this.bondUniCode = bondUniCode;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }
}
