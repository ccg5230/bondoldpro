package com.innodealing.bond.vo.analyse;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;

/**
 * 同行业违约概率增加
 * 
 * @author zhaozhenglai
 * @since 2016年9月9日 上午10:30:15 Copyright © 2016 DealingMatrix.cn. All Rights
 *        Reserved.
 */

@Document(collection = "iss_pd_down")
public class InduBreachProbabilityUpDoc implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "行业id")
    private Long induId;

    @ApiModelProperty(value = "发行人")
    private String issName;

    @ApiModelProperty(value = "最近违约概率")
    private BigDecimal currBP;

    @ApiModelProperty(value = "较上期上调幅度(priorPeriod)")
    private BigDecimal rangPP;

    public Long getInduId() {
        return induId;
    }

    public BigDecimal getCurrBP() {
        if(currBP != null){
            currBP.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return currBP;
    }

    public BigDecimal getRangPP() {
        if(rangPP != null){
            rangPP.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return rangPP;
    }

    public void setInduId(Long induId) {
        this.induId = induId;
    }

    public void setCurrBP(BigDecimal currBP) {
        this.currBP = currBP;
    }

    public void setRangPP(BigDecimal rangPP) {
        this.rangPP = rangPP;
    }

    public String getIssName() {
        return issName;
    }

    public void setIssName(String issName) {
        this.issName = issName;
    }

    public InduBreachProbabilityUpDoc(Long induId, String issName, BigDecimal currBP, BigDecimal rangPP) {
        super();
        this.induId = induId;
        this.issName = issName;
        this.currBP = currBP;
        this.rangPP = rangPP;
    }

    public InduBreachProbabilityUpDoc() {
        super();
    }

}
