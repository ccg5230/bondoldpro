package com.innodealing.model.mongo.dm.bond.detail.analyse;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * 周违约单项
 * @author zhaozhenglai
 * @since 2016年9月26日 下午2:32:17 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
public class IssPdWeek implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "最近本周违约概率")
    private double onePd;
    
    @ApiModelProperty(value = "最近上周违约概率")
    private double twoPd;
    
    @ApiModelProperty(value = "最近上上周违约概率")
    private double threePd;

    public double getOnePd() {
        return onePd;
    }

    public double getTwoPd() {
        return twoPd;
    }

    public double getThreePd() {
        return threePd;
    }

    public void setOnePd(double onePd) {
        this.onePd = onePd;
    }

    public void setTwoPd(double twoPd) {
        this.twoPd = twoPd;
    }

    public void setThreePd(double threePd) {
        this.threePd = threePd;
    }

    public IssPdWeek(double onePd, double twoPd, double threePd) {
        super();
        this.onePd =  onePd;
        this.twoPd =  twoPd;
        this.threePd = threePd;
    }

    public IssPdWeek() {
        super();
    }
   
}
