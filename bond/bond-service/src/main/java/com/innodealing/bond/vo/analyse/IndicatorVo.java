package com.innodealing.bond.vo.analyse;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
/**
 * 指标简要信息VO
 * @author zhaozhenglai
 * @since 2016年9月20日 下午7:10:04 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
public class IndicatorVo implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "field")
    private String key;
    
    @ApiModelProperty(value = "field名称")
    private String value;
    
    @ApiModelProperty(value = "负向指标标识(1负向、0正向)")
    int isNegative = 0;
    

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

   

    public int getIsNegative() {
        return isNegative;
    }

    public void setIsNegative(int isNegative) {
        this.isNegative = isNegative;
    }

    public IndicatorVo() {
        super();
    }

    public IndicatorVo(String key, String value, int isNegative) {
        super();
        this.key = key;
        this.value = value;
        this.isNegative = isNegative;
    }
    
    
    
    
}
