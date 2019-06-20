package com.innodealing.bond.vo.analyse;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

public class IssIndicatorVo implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "指标名")
    private String key;
    
    @ApiModelProperty(value = "指标值")
    private BigDecimal value;
    
    @ApiModelProperty(value = "是否为负向指标【1是，0不是】")
    private int negative ;

    public String getKey() {
        return key;
    }

    public BigDecimal getValue() {
        return value;
    }

    public int getNegative() {
        return negative;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void setNegative(int negative) {
        this.negative = negative;
    }

    public IssIndicatorVo(String key, BigDecimal value, int negative) {
        super();
        this.key = key;
        this.value = value;
        this.negative = negative;
    }

    public IssIndicatorVo() {
        super();
    }
   
}
