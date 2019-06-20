package com.innodealing.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
/**
 * 债券主体违约概率
 * @author zhaozhenglai
 * @since 2016年9月26日 下午3:23:30 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
public class IssPd implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "季度违约概率")
    private String ratingQ;
    
    @ApiModelProperty(value = "周违约概率")
    private String ratingW;
    
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    
    @ApiModelProperty(value = "安硕主体id")
    private Long amaIssId;
    
    @ApiModelProperty(value = "发布日期")
    private String publDate;

    public String getRatingQ() {
        return ratingQ;
    }

    public String getRatingW() {
        return ratingW;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Long getAmaIssId() {
        return amaIssId;
    }

    public String getPublDate() {
        return publDate;
    }

    public void setRatingQ(String ratingQ) {
        this.ratingQ = ratingQ;
    }

    public void setRatingW(String ratingW) {
        this.ratingW = ratingW;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setAmaIssId(Long amaIssId) {
        this.amaIssId = amaIssId;
    }

    public void setPublDate(String publDate) {
        this.publDate = publDate;
    }

}
