package com.innodealing.model.mongo.dm.bond.detail.analyse;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
/**
 * 指标项
 * @author zhaozhenglai
 * @since 2016年9月19日 上午11:00:11 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
public class IssIndicatorItem implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "年份")
    private int year;

    @ApiModelProperty(value = "季度")
    private int quarter;
    
    @ApiModelProperty(value = "指标")
    private BigDecimal indicator;

    public int getYear() {
        return year;
    }

    public int getQuarter() {
        return quarter;
    }

    public BigDecimal getIndicator() {
        return indicator;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public void setIndicator(BigDecimal indicator) {
        this.indicator = indicator;
    }

    public IssIndicatorItem() {
        super();
    }

    public IssIndicatorItem(int year, int quarter, BigDecimal indicator) {
        super();
        this.year = year;
        this.quarter = quarter;
        this.indicator = indicator;
    }
    
    
}
