package com.innodealing.bond.vo.analyse;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * 发行人财务指标
 * 
 * @author zhaozhenglai
 * @since 2016年9月6日 下午5:10:47 Copyright © 2016 DealingMatrix.cn. All Rights
 *        Reserved.
 */

public class BondFinanceInfoVo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "发行人|公司id")
    private Long compId;

    @ApiModelProperty(value = "财报时间")
    private String earningsTime;
    
    @ApiModelProperty(value = "指标类别")
    private String category;
    

    @ApiModelProperty(value = "各项指标")
    private List<FinanceInfoIndicatorVo> indicators;

    public Long getCompId() {
        return compId;
    }

    public String getCategory() {
        return category;
    }

    public List<FinanceInfoIndicatorVo> getIndicators() {
        return indicators;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setIndicators(List<FinanceInfoIndicatorVo> indicators) {
        this.indicators = indicators;
    }

    

    public String getEarningsTime() {
        return earningsTime;
    }

    public void setEarningsTime(String earningsTime) {
        this.earningsTime = earningsTime;
    }

    public BondFinanceInfoVo(Long compId, String category, String earningsTime,
            List<FinanceInfoIndicatorVo> indicators) {
        super();
        this.compId = compId;
        this.category = category;
        this.earningsTime = earningsTime;
        this.indicators = indicators;
    }
    
   

    public BondFinanceInfoVo() {
        super();
    }

}