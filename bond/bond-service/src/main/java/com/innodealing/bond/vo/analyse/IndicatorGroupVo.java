package com.innodealing.bond.vo.analyse;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
/**
 * 财务指标分类
 * @author zhaozhenglai
 * @since 2016年9月20日 下午7:10:58 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
public class IndicatorGroupVo implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "")
    String category;
    
    @ApiModelProperty(value = "")
    List<IndicatorVo> indicators;

    public String getCategory() {
        return category;
    }

    public List<IndicatorVo> getIndicators() {
        return indicators;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setIndicators(List<IndicatorVo> indicators) {
        this.indicators = indicators;
    }

    public IndicatorGroupVo(String category, List<IndicatorVo> indicators) {
        super();
        this.category = category;
        this.indicators = indicators;
    }

    public IndicatorGroupVo() {
        super();
    }
    
    
}
