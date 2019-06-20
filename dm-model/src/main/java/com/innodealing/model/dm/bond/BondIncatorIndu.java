package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.math.BigDecimal;

public class BondIncatorIndu extends BondAnalysisBase implements Serializable{
    /******城投公司******/
    private BigDecimal INDU_RATIO5;//EBITDA平均覆盖利息倍数  短期偿债能力  1
    private BigDecimal Operating_profit_mrgn;//  营业利润率   盈利能力    2
    private BigDecimal AveInco_Urban;//   城镇人均可支配收入   区域经济数据  3
    private BigDecimal Gov_Level_NM;//    地方政府等级  区域经济数据  4
    private BigDecimal comp_type;//   公司类型    区域经济数据  5
    private BigDecimal Lvrg_Ratio;//  产权比率    资本结构    6
    private BigDecimal DEBT2LIAB;//   有息债务/债务总额   资本结构    7
    private BigDecimal LIAB2TNGBL_ASST;// 有形资产负债率（％）  资本结构    8
    private BigDecimal NETASST_DEBT_RATIO;//  净资产负债比（%）   短期偿债能力  9
    
    /******房地产公司*******/
    private BigDecimal Tot_Eqty;//    所有者权益   规模  1
    private BigDecimal NETDEBT2TOT_EQTY;//    净债务权益比  资本结构    2
    //private BigDecimal INDU_RATIO5;// EBITDA利息保障倍数    短期偿债能力  3
    private BigDecimal INDU_RATIO2;// 短期债务占总有息债务比率    资本结构    4
    //private BigDecimal Lvrg_Ratio;//  产权比率    资本结构    5
    private BigDecimal EBITDA_Mrgn;// EBITDA 利润率  盈利能力    6
    private BigDecimal CPTL_PRFT;//   资本利润率   盈利能力    7
    private BigDecimal INDU_RATIO1;// 有息债务结构占比    资本结构    8
    private BigDecimal PPT_MOD_RATIO4;//  资产回报率   盈利能力    9
    
    
    /*******工业企业*********/
    private BigDecimal Debt2Cptl;//   杠杆比率    资本结构    1
    //private BigDecimal Tot_Eqty;//    所有者权益   规模  2
    private BigDecimal Debt2Liab;//   债务结构    资本结构    3
    private BigDecimal INDU_RATIO3;// 利润资产比(X)    盈利能力    4
    private BigDecimal MANU_MOD_RATIO9;// 行业评分    行业风险    5
    private BigDecimal INDU_RATIO4;// 现金流利润率  营运能力    6
    private BigDecimal EBITDA_Intrst_Cov;//   EBITDA利息保障倍数    短期偿债能力  7
    private BigDecimal AP_DAY;//  应付帐款周转天数(天） 营运能力    8
    private BigDecimal TNGBLASST_RTRN;//  有形资产收益率（％）  盈利能力    9
    //private BigDecimal NETDEBT2TOT_EQTY;//    净债务权益比(%)   资本结构    10

    /********商贸服务企业*********/
    //private BigDecimal Tot_Eqty;//    所有者权益   规模  1
    private BigDecimal Equity_Multi;//    权益乘数    短期偿债能力  2
    //private BigDecimal MANU_MOD_RATIO9;// 行业评分    行业风险    3
    //private BigDecimal Debt2Cptl;//   杠杆比率    资本结构    4
    private BigDecimal NETASST_RTRN;//    净资产收益率  盈利能力    5
    //private BigDecimal Operating_profit_mrgn;//  营业利润率   盈利能力    6
    //private BigDecimal EBITDA_Intrst_Cov;//   EBITDA利息保障倍数    短期偿债能力  7
    private BigDecimal CSH2LIAB;//    现金负债总额比率    盈利能力    8
    private BigDecimal TRD_DAY;// 贸易经营周期(天)   营运能力    9



}
