package com.innodealing.model.dm.bond;

import com.innodealing.annotation.Indicator;
import com.innodealing.annotation.IndicatorType;

import io.swagger.annotations.ApiModelProperty;

/**
 * 非金融行业
 * @author zhaozhenglai
 * @since 2016年9月18日 下午7:00:53 Copyright © 2016 DealingMatrix.cn. All Rights
 *        Reserved.
 */
public class BondAnalysisIndu extends BondAnalysisBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // Tot_Asst 总资产(万元)
    @ApiModelProperty(value="总资产(万元)")
    @Indicator(type = IndicatorType.VALUE)
    private String Tot_Asst;
    
    // Turnover 营业收入净额(万元)
    @ApiModelProperty(value="营业收入净额(万元)")
    @Indicator(type = IndicatorType.VALUE)
    private String Turnover;
    
    // Curr_Ratio 流动比率(X)
    @ApiModelProperty(value="流动比率(X)")
    private String Curr_Ratio;
    
    // inventry2cost 存货占比(%)
    @ApiModelProperty(value="存货占比(%)")
    private String inventry2cost;
    
    // Currency_funds2Current_assets 货币资金占比(%)
    @ApiModelProperty(value="货币资金占比(%)")
    private String Currency_funds2Current_assets;
    
    // Liab2Asst 资产负债率(％)
    @ApiModelProperty(value="资产负债率(％)")
    private String Liab2Asst;
    
    // Debt2Liab 有息债务/债务总额(％)
    @ApiModelProperty(value="有息债务/债务总额(％)")
    private String Debt2Liab;
    
    // EBIT_Intrst_Cov EBIT利息保障倍数(X)
    @ApiModelProperty(value="EBIT利息保障倍数(X)")
    @Indicator(type = IndicatorType.VALUE)
    private String EBIT_Intrst_Cov;
    
    // Oprtg_CF_Intrst_Cov 经营活动现金流固定支出保障倍数(X)
    @ApiModelProperty(value="经营活动现金流固定支出保障倍数(X)")
    @Indicator(type = IndicatorType.VALUE)
    private String Oprtg_CF_Intrst_Cov;
    
    // Operating_profit_mrgn 营业利润率(%)
    @ApiModelProperty(value="营业利润率(%)")
    private String Operating_profit_mrgn;
    
    // Grss_Mrgn 毛利润率(％)
    @ApiModelProperty(value="毛利润率(％)")
    private String Grss_Mrgn;
    
    // Tot_Asst_Rtrn 总资产收益率(％)
    @ApiModelProperty(value="总资产收益率(％)")
    private String Tot_Asst_Rtrn;
    
    // AR_Day 应收帐款周转天数(天)
    @ApiModelProperty(value="应收帐款周转天数(天)")
    @Indicator(type = IndicatorType.VALUE)
    private String AR_Day;
    
    // Invntry_Day 存货周转天数(天)
    @ApiModelProperty(value="存货周转天数(天)")
    @Indicator(type = IndicatorType.VALUE)
    private String Invntry_Day;
    
    // AP_day 应付帐款周转天数(天)
    @ApiModelProperty(value="应付帐款周转天数(天)")
    @Indicator(type = IndicatorType.VALUE)
    private String AP_day;
    
    // Tot_Asst_Turnvr 总资产周转率(次)
    @ApiModelProperty(value="总资产周转率(次)")
    private String Tot_Asst_Turnvr;
    
    // Sale_Grwth 营业收入增长率(%)
    @ApiModelProperty(value="营业收入增长率(%)")
    private String Sale_Grwth;

    public String getTot_Asst() {
        return Tot_Asst;
    }

    public String getTurnover() {
        return Turnover;
    }

    public String getCurr_Ratio() {
        return Curr_Ratio;
    }

    public String getInventry2cost() {
        return inventry2cost;
    }

    public String getCurrency_funds2Current_assets() {
        return Currency_funds2Current_assets;
    }

    public String getLiab2Asst() {
        return Liab2Asst;
    }

    public String getDebt2Liab() {
        return Debt2Liab;
    }

    public String getEBIT_Intrst_Cov() {
        return EBIT_Intrst_Cov;
    }

    public String getOprtg_CF_Intrst_Cov() {
        return Oprtg_CF_Intrst_Cov;
    }

    public String getOperating_profit_mrgn() {
        return Operating_profit_mrgn;
    }

    public String getGrss_Mrgn() {
        return Grss_Mrgn;
    }

    public String getTot_Asst_Rtrn() {
        return Tot_Asst_Rtrn;
    }

    public String getAR_Day() {
        return AR_Day;
    }

    public String getInvntry_Day() {
        return Invntry_Day;
    }

    public String getAP_day() {
        return AP_day;
    }

    public String getTot_Asst_Turnvr() {
        return Tot_Asst_Turnvr;
    }

    public String getSale_Grwth() {
        return Sale_Grwth;
    }

    public void setTot_Asst(String tot_Asst) {
        Tot_Asst = tot_Asst;
    }

    public void setTurnover(String turnover) {
        Turnover = turnover;
    }

    public void setCurr_Ratio(String curr_Ratio) {
        Curr_Ratio = curr_Ratio;
    }

    public void setInventry2cost(String inventry2cost) {
        this.inventry2cost = inventry2cost;
    }

    public void setCurrency_funds2Current_assets(String currency_funds2Current_assets) {
        Currency_funds2Current_assets = currency_funds2Current_assets;
    }

    public void setLiab2Asst(String liab2Asst) {
        Liab2Asst = liab2Asst;
    }

    public void setDebt2Liab(String debt2Liab) {
        Debt2Liab = debt2Liab;
    }

    public void setEBIT_Intrst_Cov(String eBIT_Intrst_Cov) {
        EBIT_Intrst_Cov = eBIT_Intrst_Cov;
    }

    public void setOprtg_CF_Intrst_Cov(String oprtg_CF_Intrst_Cov) {
        Oprtg_CF_Intrst_Cov = oprtg_CF_Intrst_Cov;
    }

    public void setOperating_profit_mrgn(String operating_profit_mrgn) {
        Operating_profit_mrgn = operating_profit_mrgn;
    }

    public void setGrss_Mrgn(String grss_Mrgn) {
        Grss_Mrgn = grss_Mrgn;
    }

    public void setTot_Asst_Rtrn(String tot_Asst_Rtrn) {
        Tot_Asst_Rtrn = tot_Asst_Rtrn;
    }

    public void setAR_Day(String aR_Day) {
        AR_Day = aR_Day;
    }

    public void setInvntry_Day(String invntry_Day) {
        Invntry_Day = invntry_Day;
    }

    public void setAP_day(String aP_day) {
        AP_day = aP_day;
    }

    public void setTot_Asst_Turnvr(String tot_Asst_Turnvr) {
        Tot_Asst_Turnvr = tot_Asst_Turnvr;
    }

    public void setSale_Grwth(String sale_Grwth) {
        Sale_Grwth = sale_Grwth;
    }

    

}
