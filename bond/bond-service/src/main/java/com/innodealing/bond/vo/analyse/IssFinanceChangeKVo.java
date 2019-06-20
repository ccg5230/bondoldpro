package com.innodealing.bond.vo.analyse;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.innodealing.consts.Constants;
import com.innodealing.util.NumberUtils;

import io.swagger.annotations.ApiModelProperty;

/**
 * 发行人财务指标K线图
 * 
 * @author zhaozhenglai
 * @since 2016年9月20日 上午11:37:20 Copyright © 2016 DealingMatrix.cn. All Rights
 *        Reserved.
 */
@JsonInclude(Include.NON_NULL)
public class IssFinanceChangeKVo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "季度")
    private String quarter;

    @ApiModelProperty(value = "主体指标")
    private BigDecimal issIn;

    @ApiModelProperty(value = "行业平均指标")
    private BigDecimal induInAvg;

    @ApiModelProperty(value = "行业90分位")
    private BigDecimal in90;

    @ApiModelProperty(value = "行业75分位")
    private BigDecimal in75;

    @ApiModelProperty(value = "行业15分位")
    private BigDecimal in15;

    @ApiModelProperty(value = "行业10分位")
    private BigDecimal in10;
    
    @ApiModelProperty(value = "地区平均指标")
    private BigDecimal cityInAvg;
    
    @ApiModelProperty(value = "最小分位")
    private BigDecimal min;
    
    @ApiModelProperty(value = "最大分位")
    private BigDecimal max;
    
    @ApiModelProperty("指标是否为百分比的小数，1是、0万、2其他")
    private int percent;
    
    @ApiModelProperty("指标名称")
    private String fieldName;

    public BigDecimal getCityInAvg() {
        return cityInAvg;
    }

    public void setCityInAvg(BigDecimal cityInAvg) {
        this.cityInAvg = cityInAvg;
    }

    public String getQuarter() {
        return quarter;
    }

    public BigDecimal getIssIn() {
        return issIn;
    }

    public BigDecimal getInduInAvg() {
        return induInAvg;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public void setIssIn(BigDecimal issIn) {
        this.issIn = issIn;
    }

    public void setInduInAvg(BigDecimal induInAvg) {
        this.induInAvg = induInAvg;
    }

    public BigDecimal getIn90() {
        return in90;
    }

    public BigDecimal getIn75() {
        return in75;
    }

    public BigDecimal getIn15() {
        return in15;
    }

    public BigDecimal getIn10() {
        return in10;
    }

    public void setIn90(BigDecimal in90) {
        this.in90 = in90;
    }

    public void setIn75(BigDecimal in75) {
        this.in75 = in75;
    }

    public void setIn15(BigDecimal in15) {
        this.in15 = in15;
    }

    public void setIn10(BigDecimal in10) {
        this.in10 = in10;
    }
    
    

    public int getPercent() {
    	if(this.fieldName.contains("％")){
    		return Constants.INDICATOR_PERCENT_L;
    	}
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public IssFinanceChangeKVo() {
        super();
    }
    
    

    public BigDecimal getMin() {
        return min;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public IssFinanceChangeKVo(String quarter, BigDecimal issIn, BigDecimal induInAvg) {
        super();
        this.quarter = quarter;
        this.issIn = issIn;
        this.induInAvg = induInAvg;
    }

    public IssFinanceChangeKVo(String quarter, BigDecimal issIn, BigDecimal induInAvg, BigDecimal in90, BigDecimal in75,
            BigDecimal in15, BigDecimal in10,Integer type, BigDecimal min ,BigDecimal max, int percent, String fieldName) {
        super();
        this.quarter = quarter;
        this.issIn = issIn;
        this.induInAvg = induInAvg;
        this.in90 = in90;
        this.in75 = in75;
        this.in15 = in15;
        this.in10 =in10;
        this.min = min;
        this.max = max;
        this.percent = percent;
        this.fieldName = fieldName;
    }

}
