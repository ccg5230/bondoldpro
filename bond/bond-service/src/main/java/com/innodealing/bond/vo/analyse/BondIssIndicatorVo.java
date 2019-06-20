package com.innodealing.bond.vo.analyse;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class BondIssIndicatorVo implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "债券名")
    String bondName;
    
    @ApiModelProperty(value = "发行人名")
    String issName;
    
    @ApiModelProperty(value = "发行人id")
    Long issId;
    
    @ApiModelProperty(value = "指标集合")
    List<IssIndicatorVo> indicator;

    public String getBondName() {
        return bondName;
    }

    public String getIssName() {
        return issName;
    }

    public Long getIssId() {
        return issId;
    }

    public List<IssIndicatorVo> getIndicator() {
        return indicator;
    }

    public void setBondName(String bondName) {
        this.bondName = bondName;
    }

    public void setIssName(String issName) {
        this.issName = issName;
    }

    public void setIssId(Long issId) {
        this.issId = issId;
    }

    public void setIndicator(List<IssIndicatorVo> indicator) {
        this.indicator = indicator;
    }

    public BondIssIndicatorVo(String bondName, String issName, Long issId, List<IssIndicatorVo> indicator) {
        super();
        this.bondName = bondName;
        this.issName = issName;
        this.issId = issId;
        this.indicator = indicator;
    }

    public BondIssIndicatorVo() {
        super();
    }

   
    
    
    
    
}

