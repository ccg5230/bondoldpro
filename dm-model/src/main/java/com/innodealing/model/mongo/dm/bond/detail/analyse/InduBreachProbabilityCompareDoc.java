package com.innodealing.model.mongo.dm.bond.detail.analyse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 行业主体违约概率分布对比DOC
 * @author zhaozhenglai
 * @since 2016年9月12日 上午10:55:43 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
@Document(collection = "indu_pd_compare")
@ApiModel(description = "行业主体违约概率分布对比DOC")
public class InduBreachProbabilityCompareDoc implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "中位数")
    private BigDecimal median;

    @ApiModelProperty(value = "违约率集合")
    private List<Long> bps;


    public BigDecimal getMedian() {
        if(median != null){
            return median.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return median;
    }

    public List<Long> getBps() {
        return bps;
    }


    public void setMedian(BigDecimal median) {
        this.median = median;
    }

    public void setBps(List<Long> bps) {
        this.bps = bps;
    }

    public InduBreachProbabilityCompareDoc(BigDecimal median, List<Long> bps) {
        super();
        this.median = median;
        this.bps = bps;
    }

    public InduBreachProbabilityCompareDoc() {
        super();
    }

    @Override
    public String toString() {
        return "InduBreachProbabilityCompareDoc [ median=" + median + ", bps=" + bps + "]";
    }
    
    

}
