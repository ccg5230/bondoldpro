package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
/**
 * 保险公司重点财务指标类
 * @author zhaozhenglai
 * @since 2016年10月24日 下午3:23:49 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
public class BondIndicatorInsu extends BondAnalysisBase implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "净资产")
    private BigDecimal insu_ratio2;//       净资产 规模和成长性——规模  1
    
    @ApiModelProperty(value = "市场地位")
    private BigDecimal insu_ratio29;//    市场地位    规模和成长性——规模  2
    
    @ApiModelProperty(value = "偿付能力充足率")
    private BigDecimal insu_ratio25;//    偿付能力充足率 流动性及资产负债匹配  3
    
    @ApiModelProperty(value = "赔付率")
    private BigDecimal insu_ratio26;//    赔付率 流动性及资产负债匹配  4
    
    @ApiModelProperty(value = "净保费增长率")
    private BigDecimal insu_ratio28;//    净保费增长率  盈利能力    5
    
    @ApiModelProperty(value = "营业利润率")
    private BigDecimal insu_ratio16;//    营业利润率   盈利能力    6
    
    @ApiModelProperty(value = "净资产收益率")
    private BigDecimal insu_ratio24;//   净资产收益率  盈利能力    7
    
    @ApiModelProperty(value = "净利润增长率(%)")
    private BigDecimal insu_ratio12;//   净利润增长率(%)   规模和成长性——年增长率    8
    
    @ApiModelProperty(value = "总资产增长率(%)")
    private BigDecimal insu_ratio7; // 总资产增长率(%)   规模和成长性——年增长率    9

    public BigDecimal getInsu_ratio2() {
        return insu_ratio2;
    }

    public BigDecimal getInsu_ratio29() {
        return insu_ratio29;
    }

    public BigDecimal getInsu_ratio25() {
        return insu_ratio25;
    }

    public BigDecimal getInsu_ratio26() {
        return insu_ratio26;
    }

    public BigDecimal getInsu_ratio28() {
        return insu_ratio28;
    }

    public BigDecimal getInsu_ratio16() {
        return insu_ratio16;
    }

    public BigDecimal getInsu_ratio24() {
        return insu_ratio24;
    }

    public BigDecimal getInsu_ratio12() {
        return insu_ratio12;
    }

    public BigDecimal getInsu_ratio7() {
        return insu_ratio7;
    }

    public void setInsu_ratio2(BigDecimal insu_ratio2) {
        this.insu_ratio2 = insu_ratio2;
    }

    public void setInsu_ratio29(BigDecimal insu_ratio29) {
        this.insu_ratio29 = insu_ratio29;
    }

    public void setInsu_ratio25(BigDecimal insu_ratio25) {
        this.insu_ratio25 = insu_ratio25;
    }

    public void setInsu_ratio26(BigDecimal insu_ratio26) {
        this.insu_ratio26 = insu_ratio26;
    }

    public void setInsu_ratio28(BigDecimal insu_ratio28) {
        this.insu_ratio28 = insu_ratio28;
    }

    public void setInsu_ratio16(BigDecimal insu_ratio16) {
        this.insu_ratio16 = insu_ratio16;
    }

    public void setInsu_ratio24(BigDecimal insu_ratio24) {
        this.insu_ratio24 = insu_ratio24;
    }

    public void setInsu_ratio12(BigDecimal insu_ratio12) {
        this.insu_ratio12 = insu_ratio12;
    }

    public void setInsu_ratio7(BigDecimal insu_ratio7) {
        this.insu_ratio7 = insu_ratio7;
    }
    

}
