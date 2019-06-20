package com.innodealing.bond.vo.analyse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "重点风险指标揭示")
public class IssIndicatorsVo implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("指标分类")
    private String category;
    
    @ApiModelProperty("指标名称")
    private String fieldName;
    
    @ApiModelProperty("负向指标标识【1为负向指标，0非负向指标】")
    private Integer negative = 0;
    
    @ApiModelProperty("各个季度")
    private List<String> quarters;
    
    @ApiModelProperty("指标值")
    private List<BigDecimal> fieldValue;

    public String getCategory() {
        return category;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Integer getNegative() {
        return negative;
    }

    public List<String> getQuarters() {
        return quarters;
    }

    public List<BigDecimal> getFieldValue() {
        return fieldValue;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setNegative(Integer negative) {
        this.negative = negative;
    }

    public void setQuarters(List<String> quarters) {
        this.quarters = quarters;
    }

    public void setFieldValue(List<BigDecimal> fieldValue) {
        this.fieldValue = fieldValue;
    }

   
}
