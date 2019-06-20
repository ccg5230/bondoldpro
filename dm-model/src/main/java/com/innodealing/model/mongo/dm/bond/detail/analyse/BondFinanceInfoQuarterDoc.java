package com.innodealing.model.mongo.dm.bond.detail.analyse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
/**
 * 近2期重点财务指标对比DOC
 * @author zhaozhenglai
 * @since 2016年9月8日 下午3:38:52 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
@Document(collection="bond_finance_info_quarter")
@JsonInclude(Include.NON_NULL)
public class BondFinanceInfoQuarterDoc implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公司id")
    private Long compId;
    
    @ApiModelProperty(value = "所属行业")
    private String orgType;
    
    @ApiModelProperty(value = "本期财报时间")
    private String currentTime;
    
    @ApiModelProperty(value = "上期财报时间")
    private String previousTime;
    
    @ApiModelProperty(value = "本期上期的各项指标集合")
    List<FinanceInfoQuarter> indicatorQuarter = new ArrayList<>();

    public Long getCompId() {
        return compId;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public String getPreviousTime() {
        return previousTime;
    }

    public List<FinanceInfoQuarter> getIndicatorQuarter() {
        return indicatorQuarter;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public void setPreviousTime(String previousTime) {
        this.previousTime = previousTime;
    }

    public void setIndicatorQuarter(List<FinanceInfoQuarter> indicatorQuarter) {
        this.indicatorQuarter = indicatorQuarter;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }
    public BondFinanceInfoQuarterDoc() {
        super();
    }

    public BondFinanceInfoQuarterDoc(Long compId, String currentTime, String previousTime,
            List<FinanceInfoQuarter> indicatorQuarter) {
        super();
        this.compId = compId;
        this.currentTime = currentTime;
        this.previousTime = previousTime;
        this.indicatorQuarter = indicatorQuarter;
    }
   

    public BondFinanceInfoQuarterDoc(Long compId, String orgType, String currentTime, String previousTime,
            List<FinanceInfoQuarter> indicatorQuarter) {
        super();
        this.compId = compId;
        this.orgType = orgType;
        this.currentTime = currentTime;
        this.previousTime = previousTime;
        this.indicatorQuarter = indicatorQuarter;
    }

    @Override
    public String toString() {
        return "BondFinanceInfoQuarterDoc [compId=" + compId + ", currentTime=" + currentTime + ", previousTime="
                + previousTime + ", indicatorQuarter=" + indicatorQuarter + "]";
    }
    
    
}

