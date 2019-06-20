package com.innodealing.model.mongo.dm.bond.detail.analyse;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

/**
 * 本期重点财务指标
 * @author zhaozhenglai
 * @since 2016年9月8日 下午4:14:47 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
public class FinanceInfoIndu implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "指标名称")
    private String indicator;
    
    @ApiModelProperty(value = "公司数值")
    private BigDecimal issVal;

    @ApiModelProperty(value = "行业|地区水平")
    private BigDecimal induVal;

    @ApiModelProperty(value = "负向指标标识，1表示为负向指标，0非负向")
    private int negative;
    public String getIndicator() {
        return indicator;
    }

    public BigDecimal getIssVal() {
        if(issVal.doubleValue() < 0){
            return new BigDecimal(0.00);
        }
        if(issVal.doubleValue() > 1){
            return new BigDecimal(1);
        }
        if(issVal != null){
            return issVal.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return issVal;
    }

    public BigDecimal getInduVal() {
        if(induVal.doubleValue() < 0){
            return new BigDecimal(0.00);
        }
        if(induVal.doubleValue() > 1){
            return new BigDecimal(1);
        }
        if(induVal != null){
            return induVal.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return induVal;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public void setIssVal(BigDecimal issVal) {
        this.issVal = issVal;
    }

    public void setInduVal(BigDecimal induVal) {
        this.induVal = induVal;
    }

    public int getNegative() {
        return negative;
    }

    public void setNegative(int negative) {
        this.negative = negative;
    }

    public FinanceInfoIndu(String indicator, BigDecimal issVal, BigDecimal induVal, int negative) {
        super();
        this.indicator = indicator;
        this.issVal = issVal == null ? BigDecimal.ZERO : issVal;
        this.induVal = induVal== null ? BigDecimal.ZERO : induVal;
        this.negative = negative;
    }

    public FinanceInfoIndu() {
        super();
    }

    @Override
    public String toString() {
        return "FinanceInfoIndu [indicator=" + indicator + ", issVal=" + issVal + ", induVal=" + induVal + "]";
    }
    
    
    
}
