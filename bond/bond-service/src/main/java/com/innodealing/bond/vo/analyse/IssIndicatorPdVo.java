package com.innodealing.bond.vo.analyse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "重点风险指标揭示曲线图")
public class IssIndicatorPdVo implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("各个季度对应指标")
    private List<BigDecimal> indicators = new ArrayList<>();
    
    @ApiModelProperty("各个季度对应违约概率")
    private List<Double> pds = new ArrayList<>();
    
    @ApiModelProperty("各个季度")
    private List<String> quarters = new ArrayList<>();

    public List<BigDecimal> getIndicators() {
        return indicators;
    }

    public List<Double> getPds() {
        return pds;
    }

    public List<String> getQuarters() {
        return quarters;
    }

    public void setIndicators(List<BigDecimal> indicators) {
        this.indicators = indicators;
    }

    public void setPds(List<Double> pds) {
        this.pds = pds;
    }

    public void setQuarters(List<String> quarters) {
        this.quarters = quarters;
    }

    
    
}