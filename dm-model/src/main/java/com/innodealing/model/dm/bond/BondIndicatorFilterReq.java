package com.innodealing.model.dm.bond;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonInclude(Include.NON_NULL)
public class BondIndicatorFilterReq implements Serializable {

    private static final long serialVersionUID = 4623706192249175407L;

    @ApiModelProperty(value = "财务指标")
    private String field;

    @ApiModelProperty(value = "财务指标季度")
    private String quarter;

    @ApiModelProperty(value = "最小财务指标值")
    private Double minIndicator;

    @ApiModelProperty(value = "最大财务指标值")
    private Double maxIndicator;

    @ApiModelProperty(value = "类型 1.市场指标2.专项指标3.资产负债表4.利润表5.现金流量表")
    private Integer type;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public Double getMinIndicator() {

        return minIndicator;
    }

    public void setMinIndicator(Double minIndicator) {

        this.minIndicator = minIndicator;

    }

    public Double getMaxIndicator() {

        return maxIndicator;
    }

    public void setMaxIndicator(Double maxIndicator) {

        this.maxIndicator = maxIndicator;

    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BondIndicatorFilterReq() {
        super();
        // TODO Auto-generated constructor stub
    }

    public BondIndicatorFilterReq(String field, String quarter, Double minIndicator, Double maxIndicator, Integer type) {
        super();
        this.field = field;
        this.quarter = quarter;
        this.minIndicator = minIndicator;
        this.maxIndicator = maxIndicator;
        this.type = type;
    }

    @Override
    public String toString() {
        return "BondIndicatorFilterReq [field=" + field + ", quarter=" + quarter + ", minIndicator=" + minIndicator + ", maxIndicator=" + maxIndicator + "]";
    }

}
