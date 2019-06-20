package com.innodealing.model.mongo.dm.bond.detail.analyse;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;

/**
 * 债券主体违约概率原数据
 * @author zhaozhenglai
 * @since 2016年9月26日 下午2:29:36 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
@Document(collection = "iss_pd")
public class IssPdDoc implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "发行人id")
    @Indexed
    private Long issId;
    
    @ApiModelProperty(value = "发行人名称")
    @Indexed
    private String issName;
    
    @ApiModelProperty(value = "GICS行业id")
    private Long induId;
    
    @ApiModelProperty(value = "申万行业id")
    private Long induIdSw;
    
    @ApiModelProperty(value = "机构所属行业")
    private Map<String,Object> institutionInduMap; 
    
    @ApiModelProperty(value = "主体债券流通状态1正在流通，2不在流通")
    @Indexed
    private Integer currStatus = 1;
    
    @ApiModelProperty(value = "季度违约数据")
    private IssPdQuarter pdQ;
    
    @ApiModelProperty(value = "周违约数据")
    private IssPdWeek pdW;
    
    @ApiModelProperty(value = "最近6期违约概率")
    private List<Double> pds;
    
    @ApiModelProperty(value = "最近6期")
    private List<String> quarters;
    
    
    public Long getIssId() {
        return issId;
    }

    public Long getInduId() {
        return induId;
    }

    public IssPdQuarter getPdQ() {
        return pdQ;
    }

    public IssPdWeek getPdW() {
        return pdW;
    }

    public void setIssId(Long issId) {
        this.issId = issId;
    }

    public void setInduId(Long induId) {
        this.induId = induId;
    }

    public void setPdQ(IssPdQuarter pdQ) {
        this.pdQ = pdQ;
    }

    public void setPdW(IssPdWeek pdW) {
        this.pdW = pdW;
    }

    public IssPdDoc() {
        super();
    }

    public String getIssName() {
        return issName;
    }

    public void setIssName(String issName) {
        this.issName = issName;
    }
    

    public Integer getCurrStatus() {
        return currStatus;
    }

    public void setCurrStatus(Integer currStatus) {
        this.currStatus = currStatus;
    }

//    public IssPdDoc(Long issId, Long induId, IssPdQuarter pdQ, IssPdWeek pdW, String issName, Integer currStatus) {
//        super();
//        this.issId = issId;
//        this.induId = induId;
//        this.pdQ = pdQ;
//        this.pdW = pdW;
//        this.issName = issName;
//        this.currStatus = currStatus;
//    }

    public List<Double> getPds() {
        return pds;
    }

    public List<String> getQuarters() {
        return quarters;
    }

    public void setPds(List<Double> pds) {
        this.pds = pds;
    }

    public void setQuarters(List<String> quarters) {
        this.quarters = quarters;
    }

	public Long getInduIdSw() {
		return induIdSw;
	}

	public void setInduIdSw(Long induIdSw) {
		this.induIdSw = induIdSw;
	}

	public Map<String, Object> getInstitutionInduMap() {
		return institutionInduMap;
	}

	public void setInstitutionInduMap(Map<String, Object> institutionInduMap) {
		this.institutionInduMap = institutionInduMap;
	}

   
    
    
    
}