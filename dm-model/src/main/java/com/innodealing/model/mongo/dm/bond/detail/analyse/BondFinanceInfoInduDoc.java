package com.innodealing.model.mongo.dm.bond.detail.analyse;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
/**
 * 本期重点财务指标与行业比较DOC
 * @author zhaozhenglai
 * @since 2016年9月8日 下午3:38:52 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
@Document(collection="bond_finance_info_indu")
@JsonInclude(Include.NON_NULL)
public class BondFinanceInfoInduDoc implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公司id")
    private Long compId;
    
    @ApiModelProperty(value = "本期财报时间")
    private String currentTime;
    
    @ApiModelProperty(value = "本期重点财务指标集")
    private List<FinanceInfoIndu> indicatorIndus;


    public Long getCompId() {
        return compId;
    }


    public String getCurrentTime() {
        return currentTime;
    }


    public List<FinanceInfoIndu> getIndicatorIndus() {
        return indicatorIndus;
    }


    public void setCompId(Long compId) {
        this.compId = compId;
    }


    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }


    public void setIndicatorIndus(List<FinanceInfoIndu> indicatorIndus) {
        this.indicatorIndus = indicatorIndus;
    }


    public BondFinanceInfoInduDoc(Long compId, String currentTime, List<FinanceInfoIndu> indicatorIndus) {
        super();
        this.compId = compId;
        this.currentTime = currentTime;
        this.indicatorIndus = indicatorIndus;
    }


    public BondFinanceInfoInduDoc() {
        super();
    }
    
   
}

