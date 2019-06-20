package com.innodealing.model.mongo.dm.bond.detail.analyse;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;
/**
 * 行业评级变动概览
 * @author zhaozhenglai
 * @since 2016年9月8日 下午7:32:23 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */

@Document(collection = "bond_indu_analyse")
public class BondInduAnalyseDoc implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value="公司id")
    private Long compId;
    
    @ApiModelProperty(value="行业发行人总数量")
    private long issTotal;
    
    @ApiModelProperty(value="行业评级列表")
    private List<InduAnalyse> induAnalyses;
    
    

    public Long getCompId() {
        return compId;
    }


    public long getIssTotal() {
        return issTotal;
    }

    public List<InduAnalyse> getInduAnalyses() {
        return induAnalyses;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }


    public void setIssTotal(long issTotal) {
        this.issTotal = issTotal;
    }

    public void setInduAnalyses(List<InduAnalyse> induAnalyses) {
        this.induAnalyses = induAnalyses;
    }

    public BondInduAnalyseDoc() {
        super();
    }

    public BondInduAnalyseDoc(Long compId, long issTotal, List<InduAnalyse> induAnalyses) {
        super();
        this.compId = compId;
        this.issTotal = issTotal;
        this.induAnalyses = induAnalyses;
    }
    
}
