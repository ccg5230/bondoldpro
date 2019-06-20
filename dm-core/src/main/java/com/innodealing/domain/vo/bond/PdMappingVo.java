package com.innodealing.domain.vo.bond;

import java.io.Serializable;
/**
 * 违约映射关系dao
 * @author zhaozhenglai
 * @since 2016年11月25日 下午5:00:14 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
public class PdMappingVo implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty("违约概率值")
    private BigDecimal pd;
    
    @ApiModelProperty("违约评级")
    private String rating;

    public BigDecimal getPd() {
        return pd;
    }

    public String getRating() {
        return rating;
    }

    public void setPd(BigDecimal pd) {
        this.pd = pd;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
    
    
    

    
}
