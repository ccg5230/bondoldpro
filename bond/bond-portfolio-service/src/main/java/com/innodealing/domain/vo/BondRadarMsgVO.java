package com.innodealing.domain.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 根据雷达类型区分的用户消息实体
 */
public class BondRadarMsgVO implements Serializable {

    @ApiModelProperty(value = "radarTypeId")
    private Long radarTypeId;

    @ApiModelProperty(value = "雷达类型名称")
    private String radarTypeName;

    @ApiModelProperty(value = "最后一条消息内容")
    private String lastMsgContent;

    @ApiModelProperty(value = "消息数")
    private Integer radarMsgNumber;

    public Long getRadarTypeId() {
        return radarTypeId;
    }

    public void setRadarTypeId(Long radarTypeId) {
        this.radarTypeId = radarTypeId;
    }

    public String getRadarTypeName() {
        return radarTypeName;
    }

    public void setRadarTypeName(String radarTypeName) {
        this.radarTypeName = radarTypeName;
    }

    public String getLastMsgContent() {
        return lastMsgContent;
    }

    public void setLastMsgContent(String lastMsgContent) {
        this.lastMsgContent = lastMsgContent;
    }

    public Integer getRadarMsgNumber() {
        return radarMsgNumber;
    }

    public void setRadarMsgNumber(Integer radarMsgNumber) {
        this.radarMsgNumber = radarMsgNumber;
    }
}
