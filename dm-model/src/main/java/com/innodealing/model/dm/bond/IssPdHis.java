package com.innodealing.model.dm.bond;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * 主体违约概率历史
 * 
 * @author zhaozhenglai
 * @since 2016年11月2日 下午6:24:56 Copyright © 2016 DealingMatrix.cn. All Rights
 *        Reserved.
 */
public class IssPdHis implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("违约概率")
    private String rating;

    @ApiModelProperty("年")
    private Integer year;

    @ApiModelProperty("月")
    private Integer month;

    public String getRating() {
        return rating;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

}