package com.innodealing.bond.vo.analyse;

import com.innodealing.model.mongo.dm.bond.detail.analyse.InduBreachProbabilityCompareDoc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "行业分析-行业主体违约概率分布对比View Object")
public class PdCompareVo{
    @ApiModelProperty(value = "当前|基准行业")
    private InduBreachProbabilityCompareDoc current;
    
    @ApiModelProperty(value = "对比行业")
    private InduBreachProbabilityCompareDoc compare;

    public InduBreachProbabilityCompareDoc getCurrent() {
        return current;
    }

    public InduBreachProbabilityCompareDoc getCompare() {
        return compare;
    }

    public void setCurrent(InduBreachProbabilityCompareDoc current) {
        this.current = current;
    }

    public void setCompare(InduBreachProbabilityCompareDoc compare) {
        this.compare = compare;
    }

    public PdCompareVo(InduBreachProbabilityCompareDoc current, InduBreachProbabilityCompareDoc compare) {
        super();
        this.current = current;
        this.compare = compare;
    }

    public PdCompareVo() {
        super();
    }
    
    
}