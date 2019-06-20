package com.innodealing.domain.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 根据雷达类型区分的用户消息实体
 */
public class BondRadarMatchVO implements Serializable {

    @ApiModelProperty(value = "radarId")
    private Integer radarId;

    @ApiModelProperty(value = "雷达类型名称")
    private String radarName;

    @ApiModelProperty(value = "子雷达id")
    private Integer parentId;

    public Integer getRadarId() {
        return radarId;
    }

    public void setRadarId(Integer radarId) {
        this.radarId = radarId;
    }

    public String getRadarName() {
        return radarName;
    }

    public void setRadarName(String radarName) {
        this.radarName = radarName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}