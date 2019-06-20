package com.innodealing.model.dm.bond;

import com.innodealing.annotation.Indicator;
import com.innodealing.annotation.IndicatorType;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author zhaozhenglai
 * @since 2016年9月18日 下午7:21:02 Copyright © 2016 DealingMatrix.cn. All Rights
 *        Reserved.
 */
public class BondAnalysisInsu extends BondAnalysisBase{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    // insu_ratio1 总资产
    @ApiModelProperty(value= "总资产(万元)")
    @Indicator(type = IndicatorType.VALUE)
    private String insu_ratio1;
    
    // insu_ratio2 净资产
    @ApiModelProperty(value= "净资产(万元)")
    @Indicator(type = IndicatorType.VALUE)
    private String insu_ratio2;
    
    // insu_ratio3 已赚保费收入
    @ApiModelProperty(value= "已赚保费收入(万元)")
    @Indicator(type = IndicatorType.VALUE)
    private String insu_ratio3;
    
    // insu_ratio4 总投资收益
    @ApiModelProperty(value= "总投资收益(万元)")
    @Indicator(type = IndicatorType.VALUE)
    private String insu_ratio4;
    
    // insu_ratio6 净利润
    @ApiModelProperty(value= "净利润(万元)")
    @Indicator(type = IndicatorType.VALUE)
    private String insu_ratio6;
    
    // insu_ratio13 退保率
    @ApiModelProperty(value= "退保率(%)")
    private String insu_ratio13;
    
    // insu_ratio14 资产收益率
    @ApiModelProperty(value= "资产收益率(%)")
    private String insu_ratio14;
    
    // insu_ratio15 资本收益率
    @ApiModelProperty(value= "资本收益率(%)")
    private String insu_ratio15;
    
    // insu_ratio26 赔付率
    @ApiModelProperty(value= "赔付率(%)")
    private String insu_ratio26;

    public String getInsu_ratio1() {
        return insu_ratio1;
    }

    public String getInsu_ratio2() {
        return insu_ratio2;
    }

    public String getInsu_ratio3() {
        return insu_ratio3;
    }

    public String getInsu_ratio4() {
        return insu_ratio4;
    }

    public String getInsu_ratio6() {
        return insu_ratio6;
    }

    public String getInsu_ratio13() {
        return insu_ratio13;
    }

    public String getInsu_ratio14() {
        return insu_ratio14;
    }

    public String getInsu_ratio15() {
        return insu_ratio15;
    }

    public String getInsu_ratio26() {
        return insu_ratio26;
    }

    public void setInsu_ratio1(String insu_ratio1) {
        this.insu_ratio1 = insu_ratio1;
    }

    public void setInsu_ratio2(String insu_ratio2) {
        this.insu_ratio2 = insu_ratio2;
    }

    public void setInsu_ratio3(String insu_ratio3) {
        this.insu_ratio3 = insu_ratio3;
    }

    public void setInsu_ratio4(String insu_ratio4) {
        this.insu_ratio4 = insu_ratio4;
    }

    public void setInsu_ratio6(String insu_ratio6) {
        this.insu_ratio6 = insu_ratio6;
    }

    public void setInsu_ratio13(String insu_ratio13) {
        this.insu_ratio13 = insu_ratio13;
    }

    public void setInsu_ratio14(String insu_ratio14) {
        this.insu_ratio14 = insu_ratio14;
    }

    public void setInsu_ratio15(String insu_ratio15) {
        this.insu_ratio15 = insu_ratio15;
    }

    public void setInsu_ratio26(String insu_ratio26) {
        this.insu_ratio26 = insu_ratio26;
    }

   
}
