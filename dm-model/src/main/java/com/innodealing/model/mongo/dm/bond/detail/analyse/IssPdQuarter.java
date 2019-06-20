package com.innodealing.model.mongo.dm.bond.detail.analyse;

import io.swagger.annotations.ApiModelProperty;

/**
 * 季度违约单项
 * @author zhaozhenglai
 * @since 2016年9月26日 下午2:31:36 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
public class IssPdQuarter{
    @ApiModelProperty(value = "最近本季度")
    private String onePdQ;
    
    @ApiModelProperty(value = "最近上季度")
    private String twoPdQ;
    
    @ApiModelProperty(value = "最近本季度违约概率")
    private double onePd;
    
    @ApiModelProperty(value = "最近上季度违约概率")
    private double twoPd;

    public String getOnePdQ() {
        return onePdQ;
    }

    public String getTwoPdQ() {
        return twoPdQ;
    }

    public double getOnePd() {
        return onePd;
    }

    public double getTwoPd() {
        return twoPd;
    }

    public void setOnePdQ(String onePdQ) {
        this.onePdQ = onePdQ;
    }

    public void setTwoPdQ(String twoPdQ) {
        this.twoPdQ = twoPdQ;
    }

    public void setOnePd(double onePd) {
        this.onePd = onePd;
    }

    public void setTwoPd(double twoPd) {
        this.twoPd = twoPd;
    }

    public IssPdQuarter(String onePdQ, String twoPdQ, double onePd, double twoPd) {
        super();
        this.onePdQ = onePdQ;
        this.twoPdQ = twoPdQ;
        this.onePd = onePd;
        this.twoPd = twoPd;
    }

    public IssPdQuarter() {
        super();
    }
    
}