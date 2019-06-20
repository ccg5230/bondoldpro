package com.innodealing.model.dm.bond;

import com.innodealing.annotation.Indicator;
import com.innodealing.annotation.IndicatorType;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author zhaozhenglai
 * @since 2016年9月18日 下午5:46:30 Copyright © 2016 DealingMatrix.cn. All Rights
 *        Reserved.
 */
public class BondAnalysisBank extends BondAnalysisBase{

    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // bank_ratio1 总资产
    @ApiModelProperty(value = "总资产(万元)")
    @Indicator(type = IndicatorType.VALUE)
    private String bank_ratio1;
    
    // bank_ratio2 净资产
    @ApiModelProperty(value = "净资产(万元)")
    @Indicator(type = IndicatorType.VALUE)
    private String bank_ratio2;
    
    // bank_ratio3 存款余额
    @ApiModelProperty(value = "存款余额(万元)")
    @Indicator(type = IndicatorType.VALUE)
    private String bank_ratio3;
    
    // bank_ratio4 贷款总额
    @ApiModelProperty(value = "贷款总额(万元)")
    @Indicator(type = IndicatorType.VALUE)
    private String bank_ratio4;
    
    // bank_ratio5 提取准备金前利润
    @ApiModelProperty(value = "提取准备金前利润(万元)")
    @Indicator(type = IndicatorType.VALUE)
    private String bank_ratio5;
    
    // bank_ratio6 净利润
    @ApiModelProperty(value = "净利润(万元)")
    @Indicator(type = IndicatorType.VALUE)
    private String bank_ratio6;
    
    // bank_ratio7 总资产增长率
    @ApiModelProperty(value = "总资产增长率(%)")
    private String bank_ratio7;
    
    // bank_ratio8 净资产增长率
    @ApiModelProperty(value = "净资产增长率(%)")
    private String bank_ratio8;
    
    // bank_ratio9 存款增长率
    @ApiModelProperty(value = "存款增长率(%)")
    private String bank_ratio9;
    
    // bank_ratio10 贷款增长率
    @ApiModelProperty(value = "贷款增长率(%)")
    private String bank_ratio10;
    
    // bank_ratio16 拨备前利润增长率
    @ApiModelProperty(value = "拨备前利润增长率(%)")
    private String bank_ratio16;
    
    // bank_ratio17 净利润增长率
    @ApiModelProperty(value = "净利润增长率(%)")
    private String bank_ratio17;
    
    // bank_ratio24 资产收益率
    @ApiModelProperty(value = "资产收益率(%)")
    private String bank_ratio24;
    
    // bank_ratio25 资本收益率
    @ApiModelProperty(value = "资本收益率(%)")
    private String bank_ratio25;
    
    // bank_ratio27 净息差(NIM)
    @ApiModelProperty(value = "净息差(NIM)(%)")
    @Indicator(type = IndicatorType.VALUE)
    private String bank_ratio27;
    
    // bank_ratio28 净利差(spread)
    @ApiModelProperty(value = "净利差(spread)(%)")
    @Indicator(type = IndicatorType.VALUE)
    private String bank_ratio28;
    
    // bank_ratio31 手续费净收入/经营收入
    @ApiModelProperty(value = "手续费净收入/经营收入(%)")
    @Indicator(type = IndicatorType.VALUE)
    private String bank_ratio31;
    
    // bank_ratio32 成本收入比
    @ApiModelProperty(value = "成本收入比(%)")
    private String bank_ratio32;
    
    // bank_ratio43 不良贷款率
    @ApiModelProperty(value = "不良贷款率(%)")
    private String bank_ratio43;
    
    // bank_ratio45 拨备覆盖率
    @ApiModelProperty(value = "拨备覆盖率(%)")
    private String bank_ratio45;
    
    // bank_ratio47 拨贷比
    @ApiModelProperty(value = "拨贷比(%)")
    private String bank_ratio47;
    
    // bank_ratio53 流动性比率
    @ApiModelProperty(value = "流动性比率(%)")
    private String bank_ratio53;
    
    // bank_ratio54 超额准备金比率
    @ApiModelProperty(value = "超额准备金比率(%)")
    private String bank_ratio54;
    
    // bank_ratio55 存贷款比例(含贴现)
    @ApiModelProperty(value = "存贷款比例(含贴现)(%)")
    private String bank_ratio55;
    
    // bank_ratio63 资本充足率
    @ApiModelProperty(value = "资本充足率(%)")
    private String bank_ratio63;
    
    // bank_ratio64 核心资本充足率
    @ApiModelProperty(value = "核心资本充足率(%)")
    private String bank_ratio64;

    public String getBank_ratio1() {
        return bank_ratio1;
    }

    public String getBank_ratio2() {
        return bank_ratio2;
    }

    public String getBank_ratio3() {
        return bank_ratio3;
    }

    public String getBank_ratio4() {
        return bank_ratio4;
    }

    public String getBank_ratio5() {
        return bank_ratio5;
    }

    public String getBank_ratio6() {
        return bank_ratio6;
    }

    public String getBank_ratio7() {
        return bank_ratio7;
    }

    public String getBank_ratio8() {
        return bank_ratio8;
    }

    public String getBank_ratio9() {
        return bank_ratio9;
    }

    public String getBank_ratio10() {
        return bank_ratio10;
    }

    public String getBank_ratio16() {
        return bank_ratio16;
    }

    public String getBank_ratio17() {
        return bank_ratio17;
    }

    public String getBank_ratio24() {
        return bank_ratio24;
    }

    public String getBank_ratio25() {
        return bank_ratio25;
    }

    public String getBank_ratio27() {
        return bank_ratio27;
    }

    public String getBank_ratio28() {
        return bank_ratio28;
    }

    public String getBank_ratio31() {
        return bank_ratio31;
    }

    public String getBank_ratio32() {
        return bank_ratio32;
    }

    public String getBank_ratio43() {
        return bank_ratio43;
    }

    public String getBank_ratio45() {
        return bank_ratio45;
    }

    public String getBank_ratio47() {
        return bank_ratio47;
    }

    public String getBank_ratio53() {
        return bank_ratio53;
    }

    public String getBank_ratio54() {
        return bank_ratio54;
    }

    public String getBank_ratio55() {
        return bank_ratio55;
    }

    public String getBank_ratio63() {
        return bank_ratio63;
    }

    public String getBank_ratio64() {
        return bank_ratio64;
    }

    public void setBank_ratio1(String bank_ratio1) {
        this.bank_ratio1 = bank_ratio1;
    }

    public void setBank_ratio2(String bank_ratio2) {
        this.bank_ratio2 = bank_ratio2;
    }

    public void setBank_ratio3(String bank_ratio3) {
        this.bank_ratio3 = bank_ratio3;
    }

    public void setBank_ratio4(String bank_ratio4) {
        this.bank_ratio4 = bank_ratio4;
    }

    public void setBank_ratio5(String bank_ratio5) {
        this.bank_ratio5 = bank_ratio5;
    }

    public void setBank_ratio6(String bank_ratio6) {
        this.bank_ratio6 = bank_ratio6;
    }

    public void setBank_ratio7(String bank_ratio7) {
        this.bank_ratio7 = bank_ratio7;
    }

    public void setBank_ratio8(String bank_ratio8) {
        this.bank_ratio8 = bank_ratio8;
    }

    public void setBank_ratio9(String bank_ratio9) {
        this.bank_ratio9 = bank_ratio9;
    }

    public void setBank_ratio10(String bank_ratio10) {
        this.bank_ratio10 = bank_ratio10;
    }

    public void setBank_ratio16(String bank_ratio16) {
        this.bank_ratio16 = bank_ratio16;
    }

    public void setBank_ratio17(String bank_ratio17) {
        this.bank_ratio17 = bank_ratio17;
    }

    public void setBank_ratio24(String bank_ratio24) {
        this.bank_ratio24 = bank_ratio24;
    }

    public void setBank_ratio25(String bank_ratio25) {
        this.bank_ratio25 = bank_ratio25;
    }

    public void setBank_ratio27(String bank_ratio27) {
        this.bank_ratio27 = bank_ratio27;
    }

    public void setBank_ratio28(String bank_ratio28) {
        this.bank_ratio28 = bank_ratio28;
    }

    public void setBank_ratio31(String bank_ratio31) {
        this.bank_ratio31 = bank_ratio31;
    }

    public void setBank_ratio32(String bank_ratio32) {
        this.bank_ratio32 = bank_ratio32;
    }

    public void setBank_ratio43(String bank_ratio43) {
        this.bank_ratio43 = bank_ratio43;
    }

    public void setBank_ratio45(String bank_ratio45) {
        this.bank_ratio45 = bank_ratio45;
    }

    public void setBank_ratio47(String bank_ratio47) {
        this.bank_ratio47 = bank_ratio47;
    }

    public void setBank_ratio53(String bank_ratio53) {
        this.bank_ratio53 = bank_ratio53;
    }

    public void setBank_ratio54(String bank_ratio54) {
        this.bank_ratio54 = bank_ratio54;
    }

    public void setBank_ratio55(String bank_ratio55) {
        this.bank_ratio55 = bank_ratio55;
    }

    public void setBank_ratio63(String bank_ratio63) {
        this.bank_ratio63 = bank_ratio63;
    }

    public void setBank_ratio64(String bank_ratio64) {
        this.bank_ratio64 = bank_ratio64;
    }
    
}
