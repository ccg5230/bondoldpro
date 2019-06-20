package com.innodealing.model.dm.bond;

import com.innodealing.annotation.Indicator;
import com.innodealing.annotation.IndicatorType;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author zhaozhenglai
 * @since 2016年9月18日 下午7:09:17 Copyright © 2016 DealingMatrix.cn. All Rights
 *        Reserved.
 */
public class BondAnalysisSecu extends BondAnalysisBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    
    // secu_ratio1 资产总额
    @ApiModelProperty(value= "资产总额(万元)")
    @Indicator(type = IndicatorType.VALUE)
    private String secu_ratio1;
    
    // secu_ratio2 资产总额(扣除代买卖证券款)
    @ApiModelProperty(value= "资产总额(扣除代买卖证券款)(万元)")
    @Indicator(type = IndicatorType.VALUE)
    private String secu_ratio2;
    // secu_ratio3 净资产
    @ApiModelProperty(value= "净资产(万元)")
    @Indicator(type = IndicatorType.VALUE)
    private String secu_ratio3;
    
    // secu_ratio5 净资本
    @ApiModelProperty(value= "净资本(万元)")
    @Indicator(type = IndicatorType.VALUE)
    private String secu_ratio5;
    
    // secu_ratio7 利润总额
    @ApiModelProperty(value= "利润总额(万元)")
    @Indicator(type = IndicatorType.VALUE)
    private String secu_ratio7;
    
    // secu_ratio8 净利润
    @ApiModelProperty(value= "净利润(万元)")
    @Indicator(type = IndicatorType.VALUE)
    private String secu_ratio8;
    
    // secu_ratio38 资产收益率
    @ApiModelProperty(value= "资产收益率(%)")
    private String secu_ratio38;
    
    // secu_ratio39 净资产收益率
    @ApiModelProperty(value= "净资产收益率(%)")
    private String secu_ratio39;
    
    // secu_ratio49 资产负债率
    @ApiModelProperty(value= "资产负债率(%)")
    private String secu_ratio49;
    
    // secu_ratio50 净资产负债率(负债/净资产)
    @ApiModelProperty(value= "净资产负债率(负债/净资产)(%)")
    private String secu_ratio50;
    
    // secu_ratio53 权益性资产/净资产
    @ApiModelProperty(value= "权益性资产/净资产(%)")
    private String secu_ratio53;
    
    // secu_ratio54 净资本/净资产(≥40%)
    @ApiModelProperty(value= "净资本/净资产(≥40%)(%)")
    private String secu_ratio54;
    
    // secu_ratio55 净资本比率(净资本/负债(≥8%))
    @ApiModelProperty(value= "净资本比率(净资本/负债(≥8%))(%)")
    private String secu_ratio55;
    
    // secu_ratio56 净资产/负债(≥20%)
    @ApiModelProperty(value= "净资产/负债(≥20%)(%)")
    private String secu_ratio56;
    
    // secu_ratio57 自营固定收益类证券/净资本(≤500%)
    @ApiModelProperty(value= "自营固定收益类证券/净资本(≤500%)(%)")
    private String secu_ratio57;
    
    // secu_ratio58 自营股票/净资本(≤100%)
    @ApiModelProperty(value= "自营股票/净资本(≤100%)(%)")
    private String secu_ratio58;
    
    // secu_ratio59 (净资本/各项风险准备)≥100%
    @ApiModelProperty(value= "净资本/各项风险准备(≥100%)(%)")
    private String secu_ratio59;

   

    public String getSecu_ratio1() {
        return secu_ratio1;
    }

    public String getSecu_ratio2() {
        return secu_ratio2;
    }

    public String getSecu_ratio3() {
        return secu_ratio3;
    }

    public String getSecu_ratio5() {
        return secu_ratio5;
    }

    public String getSecu_ratio7() {
        return secu_ratio7;
    }

    public String getSecu_ratio8() {
        return secu_ratio8;
    }

    public String getSecu_ratio38() {
        return secu_ratio38;
    }

    public String getSecu_ratio39() {
        return secu_ratio39;
    }

    public String getSecu_ratio49() {
        return secu_ratio49;
    }

    public String getSecu_ratio50() {
        return secu_ratio50;
    }

    public String getSecu_ratio53() {
        return secu_ratio53;
    }

    public String getSecu_ratio54() {
        return secu_ratio54;
    }

    public String getSecu_ratio55() {
        return secu_ratio55;
    }

    public String getSecu_ratio56() {
        return secu_ratio56;
    }

    public String getSecu_ratio57() {
        return secu_ratio57;
    }

    

    public String getSecu_ratio58() {
        return secu_ratio58;
    }

    public String getSecu_ratio59() {
        return secu_ratio59;
    }

    public void setSecu_ratio58(String secu_ratio58) {
        this.secu_ratio58 = secu_ratio58;
    }

    public void setSecu_ratio59(String secu_ratio59) {
        this.secu_ratio59 = secu_ratio59;
    }

    public void setSecu_ratio1(String secu_ratio1) {
        this.secu_ratio1 = secu_ratio1;
    }

    public void setSecu_ratio2(String secu_ratio2) {
        this.secu_ratio2 = secu_ratio2;
    }

    public void setSecu_ratio3(String secu_ratio3) {
        this.secu_ratio3 = secu_ratio3;
    }

    public void setSecu_ratio5(String secu_ratio5) {
        this.secu_ratio5 = secu_ratio5;
    }

    public void setSecu_ratio7(String secu_ratio7) {
        this.secu_ratio7 = secu_ratio7;
    }

    public void setSecu_ratio8(String secu_ratio8) {
        this.secu_ratio8 = secu_ratio8;
    }

    public void setSecu_ratio38(String secu_ratio38) {
        this.secu_ratio38 = secu_ratio38;
    }

    public void setSecu_ratio39(String secu_ratio39) {
        this.secu_ratio39 = secu_ratio39;
    }

    public void setSecu_ratio49(String secu_ratio49) {
        this.secu_ratio49 = secu_ratio49;
    }

    public void setSecu_ratio50(String secu_ratio50) {
        this.secu_ratio50 = secu_ratio50;
    }

    public void setSecu_ratio53(String secu_ratio53) {
        this.secu_ratio53 = secu_ratio53;
    }

    public void setSecu_ratio54(String secu_ratio54) {
        this.secu_ratio54 = secu_ratio54;
    }

    public void setSecu_ratio55(String secu_ratio55) {
        this.secu_ratio55 = secu_ratio55;
    }

    public void setSecu_ratio56(String secu_ratio56) {
        this.secu_ratio56 = secu_ratio56;
    }

    public void setSecu_ratio57(String secu_ratio57) {
        this.secu_ratio57 = secu_ratio57;
    }

  

   
}
