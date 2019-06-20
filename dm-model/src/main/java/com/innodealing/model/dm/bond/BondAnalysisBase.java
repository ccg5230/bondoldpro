package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author zhaozhenglai
 * @since 2016年9月18日 下午5:46:30 Copyright © 2016 DealingMatrix.cn. All Rights
 *        Reserved.
 */
public class BondAnalysisBase implements Serializable {

    /**
     * 
     */
    protected static final long serialVersionUID = 1L;

    // ID 自增长ID
    private Long id;
    
    // create_time 创建时间
    private Date create_time;
    
    // one_yr_pd 1年违约概率
    private String one_yr_pd;
    
    // Comp_ID 公司代码
    private Long Comp_ID;
    
    // Comp_Name 公司名称
    private String Comp_Name;
    
    // INDUSTRY_ID 行业代码
    private Long INDUSTRY_ID;
    
    // INDUSTRY_ID_NM 行业名称
    private String INDUSTRY_ID_NM;
    
    // fin_date 报表日期
    private String fin_date;

    public Long getId() {
        return id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public String getOne_yr_pd() {
        return one_yr_pd;
    }

    public Long getComp_ID() {
        return Comp_ID;
    }

    public String getComp_Name() {
        return Comp_Name;
    }

    public Long getINDUSTRY_ID() {
        return INDUSTRY_ID;
    }

    public String getINDUSTRY_ID_NM() {
        return INDUSTRY_ID_NM;
    }

    public String getFin_date() {
        return fin_date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public void setOne_yr_pd(String one_yr_pd) {
        this.one_yr_pd = one_yr_pd;
    }

    public void setComp_ID(Long comp_ID) {
        Comp_ID = comp_ID;
    }

    public void setComp_Name(String comp_Name) {
        Comp_Name = comp_Name;
    }

    public void setINDUSTRY_ID(Long iNDUSTRY_ID) {
        INDUSTRY_ID = iNDUSTRY_ID;
    }

    public void setINDUSTRY_ID_NM(String iNDUSTRY_ID_NM) {
        INDUSTRY_ID_NM = iNDUSTRY_ID_NM;
    }

    public void setFin_date(String fin_date) {
        this.fin_date = fin_date;
    }
    
    
    

}
