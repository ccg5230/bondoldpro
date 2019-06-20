package com.innodealing.model.dm.bond.ccxe;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "d_bond_fin_fal_bala_tafbb")
public class BondFinFalBalaTafbb implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", unique=false, length=36)
    private String id;
    
	/**
	 * 
	 */
    @Column(name="ISVALID", length=10)
    private Integer isvalid;
    
	/**
	 * 
	 */
    @Column(name="CREATETIME", length=19)
	@Temporal(TemporalType.TIMESTAMP)
    private Date createtime;
    
	/**
	 * 
	 */
    @Column(name="UPDATETIME", length=19)
	@Temporal(TemporalType.TIMESTAMP)
    private Date updatetime;
    
	/**
	 * 
	 */
    @Column(nullable=false, name="CCXEID", length=19)
	@Temporal(TemporalType.TIMESTAMP)
    private Date ccxeid;
    
	/**
	 * 
	 */
    @Column(name="BOND_UNI_CODE", length=19)
    private Long bondUniCode;
    
	/**
	 * 
	 */
    @Column(name="BOND_CODE", length=30)
    private String bondCode;
    
	/**
	 * 
	 */
    @Column(name="BOND_SHORT_NAME", length=200)
    private String bondShortName;
    
	/**
	 * 
	 */
    @Column(name="END_DATE", length=19)
	@Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    
	/**
	 * 
	 */
    @Column(name="SHEET_MARK_PAR", length=10)
    private Integer sheetMarkPar;
    
	/**
	 * 
	 */
    @Column(name="SHEET_ATTR_PAR", length=10)
    private Integer sheetAttrPar;
    
	/**
	 * 
	 */
    @Column(name="COM_UNI_CODE", length=19)
    private Long comUniCode;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_110101", length=20)
    private BigDecimal tafbby110101;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_110115", length=20)
    private BigDecimal tafbbf110115;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_110601", length=20)
    private BigDecimal tafbbf110601;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_110701", length=20)
    private BigDecimal tafbbf110701;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_110901", length=20)
    private BigDecimal tafbby110901;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_110201", length=20)
    private BigDecimal tafbby110201;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_110401", length=20)
    private BigDecimal tafbby110401;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_110501", length=20)
    private BigDecimal tafbby110501;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_110701", length=20)
    private BigDecimal tafbby110701;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_124101", length=20)
    private BigDecimal tafbby124101;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_110801", length=20)
    private BigDecimal tafbby110801;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_112001", length=20)
    private BigDecimal tafbby112001;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_111701", length=20)
    private BigDecimal tafbby111701;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_111801", length=20)
    private BigDecimal tafbby111801;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_111601", length=20)
    private BigDecimal tafbby111601;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_111601", length=20)
    private BigDecimal tafbbf111601;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_111701", length=20)
    private BigDecimal tafbbf111701;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_111801", length=20)
    private BigDecimal tafbbf111801;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_112001", length=20)
    private BigDecimal tafbbf112001;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_112101", length=20)
    private BigDecimal tafbbf112101;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_112201", length=20)
    private BigDecimal tafbbf112201;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_112301", length=20)
    private BigDecimal tafbbf112301;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_113701", length=20)
    private BigDecimal tafbbf113701;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_115101", length=20)
    private BigDecimal tafbbf115101;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_115401", length=20)
    private BigDecimal tafbbf115401;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_110501", length=20)
    private BigDecimal tafbbf110501;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_121501", length=20)
    private BigDecimal tafbby121501;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_110601", length=20)
    private BigDecimal tafbby110601;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_110602", length=20)
    private BigDecimal tafbby110602;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_113901", length=20)
    private BigDecimal tafbbf113901;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_123401", length=20)
    private BigDecimal tafbby123401;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_121601", length=20)
    private BigDecimal tafbby121601;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_121701", length=20)
    private BigDecimal tafbby121701;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_121117", length=20)
    private BigDecimal tafbby121117;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_121901", length=20)
    private BigDecimal tafbby121901;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_122327", length=20)
    private BigDecimal tafbby122327;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_122427", length=20)
    private BigDecimal tafbby122427;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_122601", length=20)
    private BigDecimal tafbby122601;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_122801", length=20)
    private BigDecimal tafbby122801;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_122901", length=20)
    private BigDecimal tafbby122901;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_123201", length=20)
    private BigDecimal tafbby123201;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_123301", length=20)
    private BigDecimal tafbby123301;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_123302", length=20)
    private BigDecimal tafbby123302;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_123701", length=20)
    private BigDecimal tafbby123701;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_130000", length=20)
    private BigDecimal tafbby130000;
    
	/**
	 * 
	 */
    @Column(name="TAFBB_ASE_SPEC", length=20)
    private BigDecimal tafbbAseSpec;
    
	/**
	 * 
	 */
    @Column(name="TAFBB_ASE_ADJU", length=20)
    private BigDecimal tafbbAseAdju;
    
	/**
	 * 
	 */
    @Column(name="TAFBB_100000", length=20)
    private BigDecimal tafbb100000;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_210201", length=20)
    private BigDecimal tafbby210201;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_210401", length=20)
    private BigDecimal tafbby210401;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_211201", length=20)
    private BigDecimal tafbby211201;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_210101", length=20)
    private BigDecimal tafbby210101;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_220401", length=20)
    private BigDecimal tafbby220401;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_211401", length=20)
    private BigDecimal tafbby211401;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_210301", length=20)
    private BigDecimal tafbby210301;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_210501", length=20)
    private BigDecimal tafbby210501;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_210601", length=20)
    private BigDecimal tafbby210601;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_210701", length=20)
    private BigDecimal tafbby210701;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_210702", length=20)
    private BigDecimal tafbby210702;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_212301", length=20)
    private BigDecimal tafbbf212301;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_212401", length=20)
    private BigDecimal tafbbf212401;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_213201", length=20)
    private BigDecimal tafbbf213201;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_213301", length=20)
    private BigDecimal tafbbf213301;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_213401", length=20)
    private BigDecimal tafbbf213401;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_214301", length=20)
    private BigDecimal tafbbf214301;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_214401", length=20)
    private BigDecimal tafbbf214401;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_220501", length=20)
    private BigDecimal tafbbf220501;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_214501", length=20)
    private BigDecimal tafbbf214501;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_214601", length=20)
    private BigDecimal tafbbf214601;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_220101", length=20)
    private BigDecimal tafbbf220101;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_220201", length=20)
    private BigDecimal tafbbf220201;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_213201", length=20)
    private BigDecimal tafbby213201;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_212001", length=20)
    private BigDecimal tafbby212001;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_213401", length=20)
    private BigDecimal tafbby213401;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_211901", length=20)
    private BigDecimal tafbby211901;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_214501", length=20)
    private BigDecimal tafbby214501;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_213601", length=20)
    private BigDecimal tafbby213601;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_230401", length=20)
    private BigDecimal tafbby230401;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_213801", length=20)
    private BigDecimal tafbby213801;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_220601", length=20)
    private BigDecimal tafbby220601;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_220801", length=20)
    private BigDecimal tafbby220801;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_220701", length=20)
    private BigDecimal tafbby220701;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_220702", length=20)
    private BigDecimal tafbby220702;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_230101", length=20)
    private BigDecimal tafbby230101;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_240101", length=20)
    private BigDecimal tafbby240101;
    
	/**
	 * 
	 */
    @Column(name="TAFBB_LIAB_SPEC", length=20)
    private BigDecimal tafbbLiabSpec;
    
	/**
	 * 
	 */
    @Column(name="TAFBB_LIAB_ADJU", length=20)
    private BigDecimal tafbbLiabAdju;
    
	/**
	 * 
	 */
    @Column(name="TAFBB_210000", length=20)
    private BigDecimal tafbb210000;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_310201", length=20)
    private BigDecimal tafbby310201;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_310301", length=20)
    private BigDecimal tafbby310301;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_310703", length=20)
    private BigDecimal tafbby310703;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_310401", length=20)
    private BigDecimal tafbby310401;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_310601", length=20)
    private BigDecimal tafbby310601;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_310701", length=20)
    private BigDecimal tafbby310701;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_310801", length=20)
    private BigDecimal tafbby310801;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_310802", length=20)
    private BigDecimal tafbby310802;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_311101", length=20)
    private BigDecimal tafbby311101;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_310101", length=20)
    private BigDecimal tafbby310101;
    
	/**
	 * 
	 */
    @Column(name="TAFBBF_301001", length=20)
    private BigDecimal tafbbf301001;
    
	/**
	 * 
	 */
    @Column(name="TAFBB_EQU_SPEC", length=20)
    private BigDecimal tafbbEquSpec;
    
	/**
	 * 
	 */
    @Column(name="TAFBB_EQU_ADJU", length=20)
    private BigDecimal tafbbEquAdju;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_300000", length=20)
    private BigDecimal tafbby300000;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_311201", length=20)
    private BigDecimal tafbby311201;
    
	/**
	 * 
	 */
    @Column(name="TAFBB_LIAB_EQU_SPEC", length=20)
    private BigDecimal tafbbLiabEquSpec;
    
	/**
	 * 
	 */
    @Column(name="TAFBB_LIAB_EQU_ADJU", length=20)
    private BigDecimal tafbbLiabEquAdju;
    
	/**
	 * 
	 */
    @Column(name="TAFBBY_400000", length=20)
    private BigDecimal tafbby400000;
    
	/**
	 * 
	 */
    @Column(name="TAFBB_SPEC_DES", length=16777215)
    private String tafbbSpecDes;
    
	/**
	 * 
	 */
    @Column(name="INFO_PUB_DATE", length=19)
	@Temporal(TemporalType.TIMESTAMP)
    private Date infoPubDate;

    public void setId(String id){
       this.id = id;
    }
    
    public String getId(){
       return this.id;
    }
	
    public void setIsvalid(Integer isvalid){
       this.isvalid = isvalid;
    }
    
    public Integer getIsvalid(){
       return this.isvalid;
    }
	
    public void setCreatetime(Date createtime){
       this.createtime = createtime;
    }
    
    public Date getCreatetime(){
       return this.createtime;
    }
	
    public void setUpdatetime(Date updatetime){
       this.updatetime = updatetime;
    }
    
    public Date getUpdatetime(){
       return this.updatetime;
    }
	
    public void setCcxeid(Date ccxeid){
       this.ccxeid = ccxeid;
    }
    
    public Date getCcxeid(){
       return this.ccxeid;
    }
	
    public void setBondUniCode(Long bondUniCode){
       this.bondUniCode = bondUniCode;
    }
    
    public Long getBondUniCode(){
       return this.bondUniCode;
    }
	
    public void setBondCode(String bondCode){
       this.bondCode = bondCode;
    }
    
    public String getBondCode(){
       return this.bondCode;
    }
	
    public void setBondShortName(String bondShortName){
       this.bondShortName = bondShortName;
    }
    
    public String getBondShortName(){
       return this.bondShortName;
    }
	
    public void setEndDate(Date endDate){
       this.endDate = endDate;
    }
    
    public Date getEndDate(){
       return this.endDate;
    }
	
    public void setSheetMarkPar(Integer sheetMarkPar){
       this.sheetMarkPar = sheetMarkPar;
    }
    
    public Integer getSheetMarkPar(){
       return this.sheetMarkPar;
    }
	
    public void setSheetAttrPar(Integer sheetAttrPar){
       this.sheetAttrPar = sheetAttrPar;
    }
    
    public Integer getSheetAttrPar(){
       return this.sheetAttrPar;
    }
	
    public void setComUniCode(Long comUniCode){
       this.comUniCode = comUniCode;
    }
    
    public Long getComUniCode(){
       return this.comUniCode;
    }
	
    public void setTafbby110101(BigDecimal tafbby110101){
       this.tafbby110101 = tafbby110101;
    }
    
    public BigDecimal getTafbby110101(){
       return this.tafbby110101;
    }
	
    public void setTafbbf110115(BigDecimal tafbbf110115){
       this.tafbbf110115 = tafbbf110115;
    }
    
    public BigDecimal getTafbbf110115(){
       return this.tafbbf110115;
    }
	
    public void setTafbbf110601(BigDecimal tafbbf110601){
       this.tafbbf110601 = tafbbf110601;
    }
    
    public BigDecimal getTafbbf110601(){
       return this.tafbbf110601;
    }
	
    public void setTafbbf110701(BigDecimal tafbbf110701){
       this.tafbbf110701 = tafbbf110701;
    }
    
    public BigDecimal getTafbbf110701(){
       return this.tafbbf110701;
    }
	
    public void setTafbby110901(BigDecimal tafbby110901){
       this.tafbby110901 = tafbby110901;
    }
    
    public BigDecimal getTafbby110901(){
       return this.tafbby110901;
    }
	
    public void setTafbby110201(BigDecimal tafbby110201){
       this.tafbby110201 = tafbby110201;
    }
    
    public BigDecimal getTafbby110201(){
       return this.tafbby110201;
    }
	
    public void setTafbby110401(BigDecimal tafbby110401){
       this.tafbby110401 = tafbby110401;
    }
    
    public BigDecimal getTafbby110401(){
       return this.tafbby110401;
    }
	
    public void setTafbby110501(BigDecimal tafbby110501){
       this.tafbby110501 = tafbby110501;
    }
    
    public BigDecimal getTafbby110501(){
       return this.tafbby110501;
    }
	
    public void setTafbby110701(BigDecimal tafbby110701){
       this.tafbby110701 = tafbby110701;
    }
    
    public BigDecimal getTafbby110701(){
       return this.tafbby110701;
    }
	
    public void setTafbby124101(BigDecimal tafbby124101){
       this.tafbby124101 = tafbby124101;
    }
    
    public BigDecimal getTafbby124101(){
       return this.tafbby124101;
    }
	
    public void setTafbby110801(BigDecimal tafbby110801){
       this.tafbby110801 = tafbby110801;
    }
    
    public BigDecimal getTafbby110801(){
       return this.tafbby110801;
    }
	
    public void setTafbby112001(BigDecimal tafbby112001){
       this.tafbby112001 = tafbby112001;
    }
    
    public BigDecimal getTafbby112001(){
       return this.tafbby112001;
    }
	
    public void setTafbby111701(BigDecimal tafbby111701){
       this.tafbby111701 = tafbby111701;
    }
    
    public BigDecimal getTafbby111701(){
       return this.tafbby111701;
    }
	
    public void setTafbby111801(BigDecimal tafbby111801){
       this.tafbby111801 = tafbby111801;
    }
    
    public BigDecimal getTafbby111801(){
       return this.tafbby111801;
    }
	
    public void setTafbby111601(BigDecimal tafbby111601){
       this.tafbby111601 = tafbby111601;
    }
    
    public BigDecimal getTafbby111601(){
       return this.tafbby111601;
    }
	
    public void setTafbbf111601(BigDecimal tafbbf111601){
       this.tafbbf111601 = tafbbf111601;
    }
    
    public BigDecimal getTafbbf111601(){
       return this.tafbbf111601;
    }
	
    public void setTafbbf111701(BigDecimal tafbbf111701){
       this.tafbbf111701 = tafbbf111701;
    }
    
    public BigDecimal getTafbbf111701(){
       return this.tafbbf111701;
    }
	
    public void setTafbbf111801(BigDecimal tafbbf111801){
       this.tafbbf111801 = tafbbf111801;
    }
    
    public BigDecimal getTafbbf111801(){
       return this.tafbbf111801;
    }
	
    public void setTafbbf112001(BigDecimal tafbbf112001){
       this.tafbbf112001 = tafbbf112001;
    }
    
    public BigDecimal getTafbbf112001(){
       return this.tafbbf112001;
    }
	
    public void setTafbbf112101(BigDecimal tafbbf112101){
       this.tafbbf112101 = tafbbf112101;
    }
    
    public BigDecimal getTafbbf112101(){
       return this.tafbbf112101;
    }
	
    public void setTafbbf112201(BigDecimal tafbbf112201){
       this.tafbbf112201 = tafbbf112201;
    }
    
    public BigDecimal getTafbbf112201(){
       return this.tafbbf112201;
    }
	
    public void setTafbbf112301(BigDecimal tafbbf112301){
       this.tafbbf112301 = tafbbf112301;
    }
    
    public BigDecimal getTafbbf112301(){
       return this.tafbbf112301;
    }
	
    public void setTafbbf113701(BigDecimal tafbbf113701){
       this.tafbbf113701 = tafbbf113701;
    }
    
    public BigDecimal getTafbbf113701(){
       return this.tafbbf113701;
    }
	
    public void setTafbbf115101(BigDecimal tafbbf115101){
       this.tafbbf115101 = tafbbf115101;
    }
    
    public BigDecimal getTafbbf115101(){
       return this.tafbbf115101;
    }
	
    public void setTafbbf115401(BigDecimal tafbbf115401){
       this.tafbbf115401 = tafbbf115401;
    }
    
    public BigDecimal getTafbbf115401(){
       return this.tafbbf115401;
    }
	
    public void setTafbbf110501(BigDecimal tafbbf110501){
       this.tafbbf110501 = tafbbf110501;
    }
    
    public BigDecimal getTafbbf110501(){
       return this.tafbbf110501;
    }
	
    public void setTafbby121501(BigDecimal tafbby121501){
       this.tafbby121501 = tafbby121501;
    }
    
    public BigDecimal getTafbby121501(){
       return this.tafbby121501;
    }
	
    public void setTafbby110601(BigDecimal tafbby110601){
       this.tafbby110601 = tafbby110601;
    }
    
    public BigDecimal getTafbby110601(){
       return this.tafbby110601;
    }
	
    public void setTafbby110602(BigDecimal tafbby110602){
       this.tafbby110602 = tafbby110602;
    }
    
    public BigDecimal getTafbby110602(){
       return this.tafbby110602;
    }
	
    public void setTafbbf113901(BigDecimal tafbbf113901){
       this.tafbbf113901 = tafbbf113901;
    }
    
    public BigDecimal getTafbbf113901(){
       return this.tafbbf113901;
    }
	
    public void setTafbby123401(BigDecimal tafbby123401){
       this.tafbby123401 = tafbby123401;
    }
    
    public BigDecimal getTafbby123401(){
       return this.tafbby123401;
    }
	
    public void setTafbby121601(BigDecimal tafbby121601){
       this.tafbby121601 = tafbby121601;
    }
    
    public BigDecimal getTafbby121601(){
       return this.tafbby121601;
    }
	
    public void setTafbby121701(BigDecimal tafbby121701){
       this.tafbby121701 = tafbby121701;
    }
    
    public BigDecimal getTafbby121701(){
       return this.tafbby121701;
    }
	
    public void setTafbby121117(BigDecimal tafbby121117){
       this.tafbby121117 = tafbby121117;
    }
    
    public BigDecimal getTafbby121117(){
       return this.tafbby121117;
    }
	
    public void setTafbby121901(BigDecimal tafbby121901){
       this.tafbby121901 = tafbby121901;
    }
    
    public BigDecimal getTafbby121901(){
       return this.tafbby121901;
    }
	
    public void setTafbby122327(BigDecimal tafbby122327){
       this.tafbby122327 = tafbby122327;
    }
    
    public BigDecimal getTafbby122327(){
       return this.tafbby122327;
    }
	
    public void setTafbby122427(BigDecimal tafbby122427){
       this.tafbby122427 = tafbby122427;
    }
    
    public BigDecimal getTafbby122427(){
       return this.tafbby122427;
    }
	
    public void setTafbby122601(BigDecimal tafbby122601){
       this.tafbby122601 = tafbby122601;
    }
    
    public BigDecimal getTafbby122601(){
       return this.tafbby122601;
    }
	
    public void setTafbby122801(BigDecimal tafbby122801){
       this.tafbby122801 = tafbby122801;
    }
    
    public BigDecimal getTafbby122801(){
       return this.tafbby122801;
    }
	
    public void setTafbby122901(BigDecimal tafbby122901){
       this.tafbby122901 = tafbby122901;
    }
    
    public BigDecimal getTafbby122901(){
       return this.tafbby122901;
    }
	
    public void setTafbby123201(BigDecimal tafbby123201){
       this.tafbby123201 = tafbby123201;
    }
    
    public BigDecimal getTafbby123201(){
       return this.tafbby123201;
    }
	
    public void setTafbby123301(BigDecimal tafbby123301){
       this.tafbby123301 = tafbby123301;
    }
    
    public BigDecimal getTafbby123301(){
       return this.tafbby123301;
    }
	
    public void setTafbby123302(BigDecimal tafbby123302){
       this.tafbby123302 = tafbby123302;
    }
    
    public BigDecimal getTafbby123302(){
       return this.tafbby123302;
    }
	
    public void setTafbby123701(BigDecimal tafbby123701){
       this.tafbby123701 = tafbby123701;
    }
    
    public BigDecimal getTafbby123701(){
       return this.tafbby123701;
    }
	
    public void setTafbby130000(BigDecimal tafbby130000){
       this.tafbby130000 = tafbby130000;
    }
    
    public BigDecimal getTafbby130000(){
       return this.tafbby130000;
    }
	
    public void setTafbbAseSpec(BigDecimal tafbbAseSpec){
       this.tafbbAseSpec = tafbbAseSpec;
    }
    
    public BigDecimal getTafbbAseSpec(){
       return this.tafbbAseSpec;
    }
	
    public void setTafbbAseAdju(BigDecimal tafbbAseAdju){
       this.tafbbAseAdju = tafbbAseAdju;
    }
    
    public BigDecimal getTafbbAseAdju(){
       return this.tafbbAseAdju;
    }
	
    public void setTafbb100000(BigDecimal tafbb100000){
       this.tafbb100000 = tafbb100000;
    }
    
    public BigDecimal getTafbb100000(){
       return this.tafbb100000;
    }
	
    public void setTafbby210201(BigDecimal tafbby210201){
       this.tafbby210201 = tafbby210201;
    }
    
    public BigDecimal getTafbby210201(){
       return this.tafbby210201;
    }
	
    public void setTafbby210401(BigDecimal tafbby210401){
       this.tafbby210401 = tafbby210401;
    }
    
    public BigDecimal getTafbby210401(){
       return this.tafbby210401;
    }
	
    public void setTafbby211201(BigDecimal tafbby211201){
       this.tafbby211201 = tafbby211201;
    }
    
    public BigDecimal getTafbby211201(){
       return this.tafbby211201;
    }
	
    public void setTafbby210101(BigDecimal tafbby210101){
       this.tafbby210101 = tafbby210101;
    }
    
    public BigDecimal getTafbby210101(){
       return this.tafbby210101;
    }
	
    public void setTafbby220401(BigDecimal tafbby220401){
       this.tafbby220401 = tafbby220401;
    }
    
    public BigDecimal getTafbby220401(){
       return this.tafbby220401;
    }
	
    public void setTafbby211401(BigDecimal tafbby211401){
       this.tafbby211401 = tafbby211401;
    }
    
    public BigDecimal getTafbby211401(){
       return this.tafbby211401;
    }
	
    public void setTafbby210301(BigDecimal tafbby210301){
       this.tafbby210301 = tafbby210301;
    }
    
    public BigDecimal getTafbby210301(){
       return this.tafbby210301;
    }
	
    public void setTafbby210501(BigDecimal tafbby210501){
       this.tafbby210501 = tafbby210501;
    }
    
    public BigDecimal getTafbby210501(){
       return this.tafbby210501;
    }
	
    public void setTafbby210601(BigDecimal tafbby210601){
       this.tafbby210601 = tafbby210601;
    }
    
    public BigDecimal getTafbby210601(){
       return this.tafbby210601;
    }
	
    public void setTafbby210701(BigDecimal tafbby210701){
       this.tafbby210701 = tafbby210701;
    }
    
    public BigDecimal getTafbby210701(){
       return this.tafbby210701;
    }
	
    public void setTafbby210702(BigDecimal tafbby210702){
       this.tafbby210702 = tafbby210702;
    }
    
    public BigDecimal getTafbby210702(){
       return this.tafbby210702;
    }
	
    public void setTafbbf212301(BigDecimal tafbbf212301){
       this.tafbbf212301 = tafbbf212301;
    }
    
    public BigDecimal getTafbbf212301(){
       return this.tafbbf212301;
    }
	
    public void setTafbbf212401(BigDecimal tafbbf212401){
       this.tafbbf212401 = tafbbf212401;
    }
    
    public BigDecimal getTafbbf212401(){
       return this.tafbbf212401;
    }
	
    public void setTafbbf213201(BigDecimal tafbbf213201){
       this.tafbbf213201 = tafbbf213201;
    }
    
    public BigDecimal getTafbbf213201(){
       return this.tafbbf213201;
    }
	
    public void setTafbbf213301(BigDecimal tafbbf213301){
       this.tafbbf213301 = tafbbf213301;
    }
    
    public BigDecimal getTafbbf213301(){
       return this.tafbbf213301;
    }
	
    public void setTafbbf213401(BigDecimal tafbbf213401){
       this.tafbbf213401 = tafbbf213401;
    }
    
    public BigDecimal getTafbbf213401(){
       return this.tafbbf213401;
    }
	
    public void setTafbbf214301(BigDecimal tafbbf214301){
       this.tafbbf214301 = tafbbf214301;
    }
    
    public BigDecimal getTafbbf214301(){
       return this.tafbbf214301;
    }
	
    public void setTafbbf214401(BigDecimal tafbbf214401){
       this.tafbbf214401 = tafbbf214401;
    }
    
    public BigDecimal getTafbbf214401(){
       return this.tafbbf214401;
    }
	
    public void setTafbbf220501(BigDecimal tafbbf220501){
       this.tafbbf220501 = tafbbf220501;
    }
    
    public BigDecimal getTafbbf220501(){
       return this.tafbbf220501;
    }
	
    public void setTafbbf214501(BigDecimal tafbbf214501){
       this.tafbbf214501 = tafbbf214501;
    }
    
    public BigDecimal getTafbbf214501(){
       return this.tafbbf214501;
    }
	
    public void setTafbbf214601(BigDecimal tafbbf214601){
       this.tafbbf214601 = tafbbf214601;
    }
    
    public BigDecimal getTafbbf214601(){
       return this.tafbbf214601;
    }
	
    public void setTafbbf220101(BigDecimal tafbbf220101){
       this.tafbbf220101 = tafbbf220101;
    }
    
    public BigDecimal getTafbbf220101(){
       return this.tafbbf220101;
    }
	
    public void setTafbbf220201(BigDecimal tafbbf220201){
       this.tafbbf220201 = tafbbf220201;
    }
    
    public BigDecimal getTafbbf220201(){
       return this.tafbbf220201;
    }
	
    public void setTafbby213201(BigDecimal tafbby213201){
       this.tafbby213201 = tafbby213201;
    }
    
    public BigDecimal getTafbby213201(){
       return this.tafbby213201;
    }
	
    public void setTafbby212001(BigDecimal tafbby212001){
       this.tafbby212001 = tafbby212001;
    }
    
    public BigDecimal getTafbby212001(){
       return this.tafbby212001;
    }
	
    public void setTafbby213401(BigDecimal tafbby213401){
       this.tafbby213401 = tafbby213401;
    }
    
    public BigDecimal getTafbby213401(){
       return this.tafbby213401;
    }
	
    public void setTafbby211901(BigDecimal tafbby211901){
       this.tafbby211901 = tafbby211901;
    }
    
    public BigDecimal getTafbby211901(){
       return this.tafbby211901;
    }
	
    public void setTafbby214501(BigDecimal tafbby214501){
       this.tafbby214501 = tafbby214501;
    }
    
    public BigDecimal getTafbby214501(){
       return this.tafbby214501;
    }
	
    public void setTafbby213601(BigDecimal tafbby213601){
       this.tafbby213601 = tafbby213601;
    }
    
    public BigDecimal getTafbby213601(){
       return this.tafbby213601;
    }
	
    public void setTafbby230401(BigDecimal tafbby230401){
       this.tafbby230401 = tafbby230401;
    }
    
    public BigDecimal getTafbby230401(){
       return this.tafbby230401;
    }
	
    public void setTafbby213801(BigDecimal tafbby213801){
       this.tafbby213801 = tafbby213801;
    }
    
    public BigDecimal getTafbby213801(){
       return this.tafbby213801;
    }
	
    public void setTafbby220601(BigDecimal tafbby220601){
       this.tafbby220601 = tafbby220601;
    }
    
    public BigDecimal getTafbby220601(){
       return this.tafbby220601;
    }
	
    public void setTafbby220801(BigDecimal tafbby220801){
       this.tafbby220801 = tafbby220801;
    }
    
    public BigDecimal getTafbby220801(){
       return this.tafbby220801;
    }
	
    public void setTafbby220701(BigDecimal tafbby220701){
       this.tafbby220701 = tafbby220701;
    }
    
    public BigDecimal getTafbby220701(){
       return this.tafbby220701;
    }
	
    public void setTafbby220702(BigDecimal tafbby220702){
       this.tafbby220702 = tafbby220702;
    }
    
    public BigDecimal getTafbby220702(){
       return this.tafbby220702;
    }
	
    public void setTafbby230101(BigDecimal tafbby230101){
       this.tafbby230101 = tafbby230101;
    }
    
    public BigDecimal getTafbby230101(){
       return this.tafbby230101;
    }
	
    public void setTafbby240101(BigDecimal tafbby240101){
       this.tafbby240101 = tafbby240101;
    }
    
    public BigDecimal getTafbby240101(){
       return this.tafbby240101;
    }
	
    public void setTafbbLiabSpec(BigDecimal tafbbLiabSpec){
       this.tafbbLiabSpec = tafbbLiabSpec;
    }
    
    public BigDecimal getTafbbLiabSpec(){
       return this.tafbbLiabSpec;
    }
	
    public void setTafbbLiabAdju(BigDecimal tafbbLiabAdju){
       this.tafbbLiabAdju = tafbbLiabAdju;
    }
    
    public BigDecimal getTafbbLiabAdju(){
       return this.tafbbLiabAdju;
    }
	
    public void setTafbb210000(BigDecimal tafbb210000){
       this.tafbb210000 = tafbb210000;
    }
    
    public BigDecimal getTafbb210000(){
       return this.tafbb210000;
    }
	
    public void setTafbby310201(BigDecimal tafbby310201){
       this.tafbby310201 = tafbby310201;
    }
    
    public BigDecimal getTafbby310201(){
       return this.tafbby310201;
    }
	
    public void setTafbby310301(BigDecimal tafbby310301){
       this.tafbby310301 = tafbby310301;
    }
    
    public BigDecimal getTafbby310301(){
       return this.tafbby310301;
    }
	
    public void setTafbby310703(BigDecimal tafbby310703){
       this.tafbby310703 = tafbby310703;
    }
    
    public BigDecimal getTafbby310703(){
       return this.tafbby310703;
    }
	
    public void setTafbby310401(BigDecimal tafbby310401){
       this.tafbby310401 = tafbby310401;
    }
    
    public BigDecimal getTafbby310401(){
       return this.tafbby310401;
    }
	
    public void setTafbby310601(BigDecimal tafbby310601){
       this.tafbby310601 = tafbby310601;
    }
    
    public BigDecimal getTafbby310601(){
       return this.tafbby310601;
    }
	
    public void setTafbby310701(BigDecimal tafbby310701){
       this.tafbby310701 = tafbby310701;
    }
    
    public BigDecimal getTafbby310701(){
       return this.tafbby310701;
    }
	
    public void setTafbby310801(BigDecimal tafbby310801){
       this.tafbby310801 = tafbby310801;
    }
    
    public BigDecimal getTafbby310801(){
       return this.tafbby310801;
    }
	
    public void setTafbby310802(BigDecimal tafbby310802){
       this.tafbby310802 = tafbby310802;
    }
    
    public BigDecimal getTafbby310802(){
       return this.tafbby310802;
    }
	
    public void setTafbby311101(BigDecimal tafbby311101){
       this.tafbby311101 = tafbby311101;
    }
    
    public BigDecimal getTafbby311101(){
       return this.tafbby311101;
    }
	
    public void setTafbby310101(BigDecimal tafbby310101){
       this.tafbby310101 = tafbby310101;
    }
    
    public BigDecimal getTafbby310101(){
       return this.tafbby310101;
    }
	
    public void setTafbbf301001(BigDecimal tafbbf301001){
       this.tafbbf301001 = tafbbf301001;
    }
    
    public BigDecimal getTafbbf301001(){
       return this.tafbbf301001;
    }
	
    public void setTafbbEquSpec(BigDecimal tafbbEquSpec){
       this.tafbbEquSpec = tafbbEquSpec;
    }
    
    public BigDecimal getTafbbEquSpec(){
       return this.tafbbEquSpec;
    }
	
    public void setTafbbEquAdju(BigDecimal tafbbEquAdju){
       this.tafbbEquAdju = tafbbEquAdju;
    }
    
    public BigDecimal getTafbbEquAdju(){
       return this.tafbbEquAdju;
    }
	
    public void setTafbby300000(BigDecimal tafbby300000){
       this.tafbby300000 = tafbby300000;
    }
    
    public BigDecimal getTafbby300000(){
       return this.tafbby300000;
    }
	
    public void setTafbby311201(BigDecimal tafbby311201){
       this.tafbby311201 = tafbby311201;
    }
    
    public BigDecimal getTafbby311201(){
       return this.tafbby311201;
    }
	
    public void setTafbbLiabEquSpec(BigDecimal tafbbLiabEquSpec){
       this.tafbbLiabEquSpec = tafbbLiabEquSpec;
    }
    
    public BigDecimal getTafbbLiabEquSpec(){
       return this.tafbbLiabEquSpec;
    }
	
    public void setTafbbLiabEquAdju(BigDecimal tafbbLiabEquAdju){
       this.tafbbLiabEquAdju = tafbbLiabEquAdju;
    }
    
    public BigDecimal getTafbbLiabEquAdju(){
       return this.tafbbLiabEquAdju;
    }
	
    public void setTafbby400000(BigDecimal tafbby400000){
       this.tafbby400000 = tafbby400000;
    }
    
    public BigDecimal getTafbby400000(){
       return this.tafbby400000;
    }
	
    public void setTafbbSpecDes(String tafbbSpecDes){
       this.tafbbSpecDes = tafbbSpecDes;
    }
    
    public String getTafbbSpecDes(){
       return this.tafbbSpecDes;
    }
	
    public void setInfoPubDate(Date infoPubDate){
       this.infoPubDate = infoPubDate;
    }
    
    public Date getInfoPubDate(){
       return this.infoPubDate;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bondCode == null) ? 0 : bondCode.hashCode());
        result = prime * result + ((bondShortName == null) ? 0 : bondShortName.hashCode());
        result = prime * result + ((bondUniCode == null) ? 0 : bondUniCode.hashCode());
        result = prime * result + ((comUniCode == null) ? 0 : comUniCode.hashCode());
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((infoPubDate == null) ? 0 : infoPubDate.hashCode());
        result = prime * result + ((isvalid == null) ? 0 : isvalid.hashCode());
        result = prime * result + ((sheetAttrPar == null) ? 0 : sheetAttrPar.hashCode());
        result = prime * result + ((sheetMarkPar == null) ? 0 : sheetMarkPar.hashCode());
        result = prime * result + ((tafbb100000 == null) ? 0 : tafbb100000.hashCode());
        result = prime * result + ((tafbb210000 == null) ? 0 : tafbb210000.hashCode());
        result = prime * result + ((tafbbAseAdju == null) ? 0 : tafbbAseAdju.hashCode());
        result = prime * result + ((tafbbAseSpec == null) ? 0 : tafbbAseSpec.hashCode());
        result = prime * result + ((tafbbEquAdju == null) ? 0 : tafbbEquAdju.hashCode());
        result = prime * result + ((tafbbEquSpec == null) ? 0 : tafbbEquSpec.hashCode());
        result = prime * result + ((tafbbLiabAdju == null) ? 0 : tafbbLiabAdju.hashCode());
        result = prime * result + ((tafbbLiabEquAdju == null) ? 0 : tafbbLiabEquAdju.hashCode());
        result = prime * result + ((tafbbLiabEquSpec == null) ? 0 : tafbbLiabEquSpec.hashCode());
        result = prime * result + ((tafbbLiabSpec == null) ? 0 : tafbbLiabSpec.hashCode());
        result = prime * result + ((tafbbSpecDes == null) ? 0 : tafbbSpecDes.hashCode());
        result = prime * result + ((tafbbf110115 == null) ? 0 : tafbbf110115.hashCode());
        result = prime * result + ((tafbbf110501 == null) ? 0 : tafbbf110501.hashCode());
        result = prime * result + ((tafbbf110601 == null) ? 0 : tafbbf110601.hashCode());
        result = prime * result + ((tafbbf110701 == null) ? 0 : tafbbf110701.hashCode());
        result = prime * result + ((tafbbf111601 == null) ? 0 : tafbbf111601.hashCode());
        result = prime * result + ((tafbbf111701 == null) ? 0 : tafbbf111701.hashCode());
        result = prime * result + ((tafbbf111801 == null) ? 0 : tafbbf111801.hashCode());
        result = prime * result + ((tafbbf112001 == null) ? 0 : tafbbf112001.hashCode());
        result = prime * result + ((tafbbf112101 == null) ? 0 : tafbbf112101.hashCode());
        result = prime * result + ((tafbbf112201 == null) ? 0 : tafbbf112201.hashCode());
        result = prime * result + ((tafbbf112301 == null) ? 0 : tafbbf112301.hashCode());
        result = prime * result + ((tafbbf113701 == null) ? 0 : tafbbf113701.hashCode());
        result = prime * result + ((tafbbf113901 == null) ? 0 : tafbbf113901.hashCode());
        result = prime * result + ((tafbbf115101 == null) ? 0 : tafbbf115101.hashCode());
        result = prime * result + ((tafbbf115401 == null) ? 0 : tafbbf115401.hashCode());
        result = prime * result + ((tafbbf212301 == null) ? 0 : tafbbf212301.hashCode());
        result = prime * result + ((tafbbf212401 == null) ? 0 : tafbbf212401.hashCode());
        result = prime * result + ((tafbbf213201 == null) ? 0 : tafbbf213201.hashCode());
        result = prime * result + ((tafbbf213301 == null) ? 0 : tafbbf213301.hashCode());
        result = prime * result + ((tafbbf213401 == null) ? 0 : tafbbf213401.hashCode());
        result = prime * result + ((tafbbf214301 == null) ? 0 : tafbbf214301.hashCode());
        result = prime * result + ((tafbbf214401 == null) ? 0 : tafbbf214401.hashCode());
        result = prime * result + ((tafbbf214501 == null) ? 0 : tafbbf214501.hashCode());
        result = prime * result + ((tafbbf214601 == null) ? 0 : tafbbf214601.hashCode());
        result = prime * result + ((tafbbf220101 == null) ? 0 : tafbbf220101.hashCode());
        result = prime * result + ((tafbbf220201 == null) ? 0 : tafbbf220201.hashCode());
        result = prime * result + ((tafbbf220501 == null) ? 0 : tafbbf220501.hashCode());
        result = prime * result + ((tafbbf301001 == null) ? 0 : tafbbf301001.hashCode());
        result = prime * result + ((tafbby110101 == null) ? 0 : tafbby110101.hashCode());
        result = prime * result + ((tafbby110201 == null) ? 0 : tafbby110201.hashCode());
        result = prime * result + ((tafbby110401 == null) ? 0 : tafbby110401.hashCode());
        result = prime * result + ((tafbby110501 == null) ? 0 : tafbby110501.hashCode());
        result = prime * result + ((tafbby110601 == null) ? 0 : tafbby110601.hashCode());
        result = prime * result + ((tafbby110602 == null) ? 0 : tafbby110602.hashCode());
        result = prime * result + ((tafbby110701 == null) ? 0 : tafbby110701.hashCode());
        result = prime * result + ((tafbby110801 == null) ? 0 : tafbby110801.hashCode());
        result = prime * result + ((tafbby110901 == null) ? 0 : tafbby110901.hashCode());
        result = prime * result + ((tafbby111601 == null) ? 0 : tafbby111601.hashCode());
        result = prime * result + ((tafbby111701 == null) ? 0 : tafbby111701.hashCode());
        result = prime * result + ((tafbby111801 == null) ? 0 : tafbby111801.hashCode());
        result = prime * result + ((tafbby112001 == null) ? 0 : tafbby112001.hashCode());
        result = prime * result + ((tafbby121117 == null) ? 0 : tafbby121117.hashCode());
        result = prime * result + ((tafbby121501 == null) ? 0 : tafbby121501.hashCode());
        result = prime * result + ((tafbby121601 == null) ? 0 : tafbby121601.hashCode());
        result = prime * result + ((tafbby121701 == null) ? 0 : tafbby121701.hashCode());
        result = prime * result + ((tafbby121901 == null) ? 0 : tafbby121901.hashCode());
        result = prime * result + ((tafbby122327 == null) ? 0 : tafbby122327.hashCode());
        result = prime * result + ((tafbby122427 == null) ? 0 : tafbby122427.hashCode());
        result = prime * result + ((tafbby122601 == null) ? 0 : tafbby122601.hashCode());
        result = prime * result + ((tafbby122801 == null) ? 0 : tafbby122801.hashCode());
        result = prime * result + ((tafbby122901 == null) ? 0 : tafbby122901.hashCode());
        result = prime * result + ((tafbby123201 == null) ? 0 : tafbby123201.hashCode());
        result = prime * result + ((tafbby123301 == null) ? 0 : tafbby123301.hashCode());
        result = prime * result + ((tafbby123302 == null) ? 0 : tafbby123302.hashCode());
        result = prime * result + ((tafbby123401 == null) ? 0 : tafbby123401.hashCode());
        result = prime * result + ((tafbby123701 == null) ? 0 : tafbby123701.hashCode());
        result = prime * result + ((tafbby124101 == null) ? 0 : tafbby124101.hashCode());
        result = prime * result + ((tafbby130000 == null) ? 0 : tafbby130000.hashCode());
        result = prime * result + ((tafbby210101 == null) ? 0 : tafbby210101.hashCode());
        result = prime * result + ((tafbby210201 == null) ? 0 : tafbby210201.hashCode());
        result = prime * result + ((tafbby210301 == null) ? 0 : tafbby210301.hashCode());
        result = prime * result + ((tafbby210401 == null) ? 0 : tafbby210401.hashCode());
        result = prime * result + ((tafbby210501 == null) ? 0 : tafbby210501.hashCode());
        result = prime * result + ((tafbby210601 == null) ? 0 : tafbby210601.hashCode());
        result = prime * result + ((tafbby210701 == null) ? 0 : tafbby210701.hashCode());
        result = prime * result + ((tafbby210702 == null) ? 0 : tafbby210702.hashCode());
        result = prime * result + ((tafbby211201 == null) ? 0 : tafbby211201.hashCode());
        result = prime * result + ((tafbby211401 == null) ? 0 : tafbby211401.hashCode());
        result = prime * result + ((tafbby211901 == null) ? 0 : tafbby211901.hashCode());
        result = prime * result + ((tafbby212001 == null) ? 0 : tafbby212001.hashCode());
        result = prime * result + ((tafbby213201 == null) ? 0 : tafbby213201.hashCode());
        result = prime * result + ((tafbby213401 == null) ? 0 : tafbby213401.hashCode());
        result = prime * result + ((tafbby213601 == null) ? 0 : tafbby213601.hashCode());
        result = prime * result + ((tafbby213801 == null) ? 0 : tafbby213801.hashCode());
        result = prime * result + ((tafbby214501 == null) ? 0 : tafbby214501.hashCode());
        result = prime * result + ((tafbby220401 == null) ? 0 : tafbby220401.hashCode());
        result = prime * result + ((tafbby220601 == null) ? 0 : tafbby220601.hashCode());
        result = prime * result + ((tafbby220701 == null) ? 0 : tafbby220701.hashCode());
        result = prime * result + ((tafbby220702 == null) ? 0 : tafbby220702.hashCode());
        result = prime * result + ((tafbby220801 == null) ? 0 : tafbby220801.hashCode());
        result = prime * result + ((tafbby230101 == null) ? 0 : tafbby230101.hashCode());
        result = prime * result + ((tafbby230401 == null) ? 0 : tafbby230401.hashCode());
        result = prime * result + ((tafbby240101 == null) ? 0 : tafbby240101.hashCode());
        result = prime * result + ((tafbby300000 == null) ? 0 : tafbby300000.hashCode());
        result = prime * result + ((tafbby310101 == null) ? 0 : tafbby310101.hashCode());
        result = prime * result + ((tafbby310201 == null) ? 0 : tafbby310201.hashCode());
        result = prime * result + ((tafbby310301 == null) ? 0 : tafbby310301.hashCode());
        result = prime * result + ((tafbby310401 == null) ? 0 : tafbby310401.hashCode());
        result = prime * result + ((tafbby310601 == null) ? 0 : tafbby310601.hashCode());
        result = prime * result + ((tafbby310701 == null) ? 0 : tafbby310701.hashCode());
        result = prime * result + ((tafbby310703 == null) ? 0 : tafbby310703.hashCode());
        result = prime * result + ((tafbby310801 == null) ? 0 : tafbby310801.hashCode());
        result = prime * result + ((tafbby310802 == null) ? 0 : tafbby310802.hashCode());
        result = prime * result + ((tafbby311101 == null) ? 0 : tafbby311101.hashCode());
        result = prime * result + ((tafbby311201 == null) ? 0 : tafbby311201.hashCode());
        result = prime * result + ((tafbby400000 == null) ? 0 : tafbby400000.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BondFinFalBalaTafbb other = (BondFinFalBalaTafbb) obj;
        if (bondCode == null) {
            if (other.bondCode != null)
                return false;
        } else if (!bondCode.equals(other.bondCode))
            return false;
        if (bondShortName == null) {
            if (other.bondShortName != null)
                return false;
        } else if (!bondShortName.equals(other.bondShortName))
            return false;
        if (bondUniCode == null) {
            if (other.bondUniCode != null)
                return false;
        } else if (!bondUniCode.equals(other.bondUniCode))
            return false;
        if (comUniCode == null) {
            if (other.comUniCode != null)
                return false;
        } else if (!comUniCode.equals(other.comUniCode))
            return false;
        if (endDate == null) {
            if (other.endDate != null)
                return false;
        } else if (!endDate.equals(other.endDate))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (infoPubDate == null) {
            if (other.infoPubDate != null)
                return false;
        } else if (!infoPubDate.equals(other.infoPubDate))
            return false;
        if (isvalid == null) {
            if (other.isvalid != null)
                return false;
        } else if (!isvalid.equals(other.isvalid))
            return false;
        if (sheetAttrPar == null) {
            if (other.sheetAttrPar != null)
                return false;
        } else if (!sheetAttrPar.equals(other.sheetAttrPar))
            return false;
        if (sheetMarkPar == null) {
            if (other.sheetMarkPar != null)
                return false;
        } else if (!sheetMarkPar.equals(other.sheetMarkPar))
            return false;
        if (tafbb100000 == null) {
            if (other.tafbb100000 != null)
                return false;
        } else if (!tafbb100000.equals(other.tafbb100000))
            return false;
        if (tafbb210000 == null) {
            if (other.tafbb210000 != null)
                return false;
        } else if (!tafbb210000.equals(other.tafbb210000))
            return false;
        if (tafbbAseAdju == null) {
            if (other.tafbbAseAdju != null)
                return false;
        } else if (!tafbbAseAdju.equals(other.tafbbAseAdju))
            return false;
        if (tafbbAseSpec == null) {
            if (other.tafbbAseSpec != null)
                return false;
        } else if (!tafbbAseSpec.equals(other.tafbbAseSpec))
            return false;
        if (tafbbEquAdju == null) {
            if (other.tafbbEquAdju != null)
                return false;
        } else if (!tafbbEquAdju.equals(other.tafbbEquAdju))
            return false;
        if (tafbbEquSpec == null) {
            if (other.tafbbEquSpec != null)
                return false;
        } else if (!tafbbEquSpec.equals(other.tafbbEquSpec))
            return false;
        if (tafbbLiabAdju == null) {
            if (other.tafbbLiabAdju != null)
                return false;
        } else if (!tafbbLiabAdju.equals(other.tafbbLiabAdju))
            return false;
        if (tafbbLiabEquAdju == null) {
            if (other.tafbbLiabEquAdju != null)
                return false;
        } else if (!tafbbLiabEquAdju.equals(other.tafbbLiabEquAdju))
            return false;
        if (tafbbLiabEquSpec == null) {
            if (other.tafbbLiabEquSpec != null)
                return false;
        } else if (!tafbbLiabEquSpec.equals(other.tafbbLiabEquSpec))
            return false;
        if (tafbbLiabSpec == null) {
            if (other.tafbbLiabSpec != null)
                return false;
        } else if (!tafbbLiabSpec.equals(other.tafbbLiabSpec))
            return false;
        if (tafbbSpecDes == null) {
            if (other.tafbbSpecDes != null)
                return false;
        } else if (!tafbbSpecDes.equals(other.tafbbSpecDes))
            return false;
        if (tafbbf110115 == null) {
            if (other.tafbbf110115 != null)
                return false;
        } else if (!tafbbf110115.equals(other.tafbbf110115))
            return false;
        if (tafbbf110501 == null) {
            if (other.tafbbf110501 != null)
                return false;
        } else if (!tafbbf110501.equals(other.tafbbf110501))
            return false;
        if (tafbbf110601 == null) {
            if (other.tafbbf110601 != null)
                return false;
        } else if (!tafbbf110601.equals(other.tafbbf110601))
            return false;
        if (tafbbf110701 == null) {
            if (other.tafbbf110701 != null)
                return false;
        } else if (!tafbbf110701.equals(other.tafbbf110701))
            return false;
        if (tafbbf111601 == null) {
            if (other.tafbbf111601 != null)
                return false;
        } else if (!tafbbf111601.equals(other.tafbbf111601))
            return false;
        if (tafbbf111701 == null) {
            if (other.tafbbf111701 != null)
                return false;
        } else if (!tafbbf111701.equals(other.tafbbf111701))
            return false;
        if (tafbbf111801 == null) {
            if (other.tafbbf111801 != null)
                return false;
        } else if (!tafbbf111801.equals(other.tafbbf111801))
            return false;
        if (tafbbf112001 == null) {
            if (other.tafbbf112001 != null)
                return false;
        } else if (!tafbbf112001.equals(other.tafbbf112001))
            return false;
        if (tafbbf112101 == null) {
            if (other.tafbbf112101 != null)
                return false;
        } else if (!tafbbf112101.equals(other.tafbbf112101))
            return false;
        if (tafbbf112201 == null) {
            if (other.tafbbf112201 != null)
                return false;
        } else if (!tafbbf112201.equals(other.tafbbf112201))
            return false;
        if (tafbbf112301 == null) {
            if (other.tafbbf112301 != null)
                return false;
        } else if (!tafbbf112301.equals(other.tafbbf112301))
            return false;
        if (tafbbf113701 == null) {
            if (other.tafbbf113701 != null)
                return false;
        } else if (!tafbbf113701.equals(other.tafbbf113701))
            return false;
        if (tafbbf113901 == null) {
            if (other.tafbbf113901 != null)
                return false;
        } else if (!tafbbf113901.equals(other.tafbbf113901))
            return false;
        if (tafbbf115101 == null) {
            if (other.tafbbf115101 != null)
                return false;
        } else if (!tafbbf115101.equals(other.tafbbf115101))
            return false;
        if (tafbbf115401 == null) {
            if (other.tafbbf115401 != null)
                return false;
        } else if (!tafbbf115401.equals(other.tafbbf115401))
            return false;
        if (tafbbf212301 == null) {
            if (other.tafbbf212301 != null)
                return false;
        } else if (!tafbbf212301.equals(other.tafbbf212301))
            return false;
        if (tafbbf212401 == null) {
            if (other.tafbbf212401 != null)
                return false;
        } else if (!tafbbf212401.equals(other.tafbbf212401))
            return false;
        if (tafbbf213201 == null) {
            if (other.tafbbf213201 != null)
                return false;
        } else if (!tafbbf213201.equals(other.tafbbf213201))
            return false;
        if (tafbbf213301 == null) {
            if (other.tafbbf213301 != null)
                return false;
        } else if (!tafbbf213301.equals(other.tafbbf213301))
            return false;
        if (tafbbf213401 == null) {
            if (other.tafbbf213401 != null)
                return false;
        } else if (!tafbbf213401.equals(other.tafbbf213401))
            return false;
        if (tafbbf214301 == null) {
            if (other.tafbbf214301 != null)
                return false;
        } else if (!tafbbf214301.equals(other.tafbbf214301))
            return false;
        if (tafbbf214401 == null) {
            if (other.tafbbf214401 != null)
                return false;
        } else if (!tafbbf214401.equals(other.tafbbf214401))
            return false;
        if (tafbbf214501 == null) {
            if (other.tafbbf214501 != null)
                return false;
        } else if (!tafbbf214501.equals(other.tafbbf214501))
            return false;
        if (tafbbf214601 == null) {
            if (other.tafbbf214601 != null)
                return false;
        } else if (!tafbbf214601.equals(other.tafbbf214601))
            return false;
        if (tafbbf220101 == null) {
            if (other.tafbbf220101 != null)
                return false;
        } else if (!tafbbf220101.equals(other.tafbbf220101))
            return false;
        if (tafbbf220201 == null) {
            if (other.tafbbf220201 != null)
                return false;
        } else if (!tafbbf220201.equals(other.tafbbf220201))
            return false;
        if (tafbbf220501 == null) {
            if (other.tafbbf220501 != null)
                return false;
        } else if (!tafbbf220501.equals(other.tafbbf220501))
            return false;
        if (tafbbf301001 == null) {
            if (other.tafbbf301001 != null)
                return false;
        } else if (!tafbbf301001.equals(other.tafbbf301001))
            return false;
        if (tafbby110101 == null) {
            if (other.tafbby110101 != null)
                return false;
        } else if (!tafbby110101.equals(other.tafbby110101))
            return false;
        if (tafbby110201 == null) {
            if (other.tafbby110201 != null)
                return false;
        } else if (!tafbby110201.equals(other.tafbby110201))
            return false;
        if (tafbby110401 == null) {
            if (other.tafbby110401 != null)
                return false;
        } else if (!tafbby110401.equals(other.tafbby110401))
            return false;
        if (tafbby110501 == null) {
            if (other.tafbby110501 != null)
                return false;
        } else if (!tafbby110501.equals(other.tafbby110501))
            return false;
        if (tafbby110601 == null) {
            if (other.tafbby110601 != null)
                return false;
        } else if (!tafbby110601.equals(other.tafbby110601))
            return false;
        if (tafbby110602 == null) {
            if (other.tafbby110602 != null)
                return false;
        } else if (!tafbby110602.equals(other.tafbby110602))
            return false;
        if (tafbby110701 == null) {
            if (other.tafbby110701 != null)
                return false;
        } else if (!tafbby110701.equals(other.tafbby110701))
            return false;
        if (tafbby110801 == null) {
            if (other.tafbby110801 != null)
                return false;
        } else if (!tafbby110801.equals(other.tafbby110801))
            return false;
        if (tafbby110901 == null) {
            if (other.tafbby110901 != null)
                return false;
        } else if (!tafbby110901.equals(other.tafbby110901))
            return false;
        if (tafbby111601 == null) {
            if (other.tafbby111601 != null)
                return false;
        } else if (!tafbby111601.equals(other.tafbby111601))
            return false;
        if (tafbby111701 == null) {
            if (other.tafbby111701 != null)
                return false;
        } else if (!tafbby111701.equals(other.tafbby111701))
            return false;
        if (tafbby111801 == null) {
            if (other.tafbby111801 != null)
                return false;
        } else if (!tafbby111801.equals(other.tafbby111801))
            return false;
        if (tafbby112001 == null) {
            if (other.tafbby112001 != null)
                return false;
        } else if (!tafbby112001.equals(other.tafbby112001))
            return false;
        if (tafbby121117 == null) {
            if (other.tafbby121117 != null)
                return false;
        } else if (!tafbby121117.equals(other.tafbby121117))
            return false;
        if (tafbby121501 == null) {
            if (other.tafbby121501 != null)
                return false;
        } else if (!tafbby121501.equals(other.tafbby121501))
            return false;
        if (tafbby121601 == null) {
            if (other.tafbby121601 != null)
                return false;
        } else if (!tafbby121601.equals(other.tafbby121601))
            return false;
        if (tafbby121701 == null) {
            if (other.tafbby121701 != null)
                return false;
        } else if (!tafbby121701.equals(other.tafbby121701))
            return false;
        if (tafbby121901 == null) {
            if (other.tafbby121901 != null)
                return false;
        } else if (!tafbby121901.equals(other.tafbby121901))
            return false;
        if (tafbby122327 == null) {
            if (other.tafbby122327 != null)
                return false;
        } else if (!tafbby122327.equals(other.tafbby122327))
            return false;
        if (tafbby122427 == null) {
            if (other.tafbby122427 != null)
                return false;
        } else if (!tafbby122427.equals(other.tafbby122427))
            return false;
        if (tafbby122601 == null) {
            if (other.tafbby122601 != null)
                return false;
        } else if (!tafbby122601.equals(other.tafbby122601))
            return false;
        if (tafbby122801 == null) {
            if (other.tafbby122801 != null)
                return false;
        } else if (!tafbby122801.equals(other.tafbby122801))
            return false;
        if (tafbby122901 == null) {
            if (other.tafbby122901 != null)
                return false;
        } else if (!tafbby122901.equals(other.tafbby122901))
            return false;
        if (tafbby123201 == null) {
            if (other.tafbby123201 != null)
                return false;
        } else if (!tafbby123201.equals(other.tafbby123201))
            return false;
        if (tafbby123301 == null) {
            if (other.tafbby123301 != null)
                return false;
        } else if (!tafbby123301.equals(other.tafbby123301))
            return false;
        if (tafbby123302 == null) {
            if (other.tafbby123302 != null)
                return false;
        } else if (!tafbby123302.equals(other.tafbby123302))
            return false;
        if (tafbby123401 == null) {
            if (other.tafbby123401 != null)
                return false;
        } else if (!tafbby123401.equals(other.tafbby123401))
            return false;
        if (tafbby123701 == null) {
            if (other.tafbby123701 != null)
                return false;
        } else if (!tafbby123701.equals(other.tafbby123701))
            return false;
        if (tafbby124101 == null) {
            if (other.tafbby124101 != null)
                return false;
        } else if (!tafbby124101.equals(other.tafbby124101))
            return false;
        if (tafbby130000 == null) {
            if (other.tafbby130000 != null)
                return false;
        } else if (!tafbby130000.equals(other.tafbby130000))
            return false;
        if (tafbby210101 == null) {
            if (other.tafbby210101 != null)
                return false;
        } else if (!tafbby210101.equals(other.tafbby210101))
            return false;
        if (tafbby210201 == null) {
            if (other.tafbby210201 != null)
                return false;
        } else if (!tafbby210201.equals(other.tafbby210201))
            return false;
        if (tafbby210301 == null) {
            if (other.tafbby210301 != null)
                return false;
        } else if (!tafbby210301.equals(other.tafbby210301))
            return false;
        if (tafbby210401 == null) {
            if (other.tafbby210401 != null)
                return false;
        } else if (!tafbby210401.equals(other.tafbby210401))
            return false;
        if (tafbby210501 == null) {
            if (other.tafbby210501 != null)
                return false;
        } else if (!tafbby210501.equals(other.tafbby210501))
            return false;
        if (tafbby210601 == null) {
            if (other.tafbby210601 != null)
                return false;
        } else if (!tafbby210601.equals(other.tafbby210601))
            return false;
        if (tafbby210701 == null) {
            if (other.tafbby210701 != null)
                return false;
        } else if (!tafbby210701.equals(other.tafbby210701))
            return false;
        if (tafbby210702 == null) {
            if (other.tafbby210702 != null)
                return false;
        } else if (!tafbby210702.equals(other.tafbby210702))
            return false;
        if (tafbby211201 == null) {
            if (other.tafbby211201 != null)
                return false;
        } else if (!tafbby211201.equals(other.tafbby211201))
            return false;
        if (tafbby211401 == null) {
            if (other.tafbby211401 != null)
                return false;
        } else if (!tafbby211401.equals(other.tafbby211401))
            return false;
        if (tafbby211901 == null) {
            if (other.tafbby211901 != null)
                return false;
        } else if (!tafbby211901.equals(other.tafbby211901))
            return false;
        if (tafbby212001 == null) {
            if (other.tafbby212001 != null)
                return false;
        } else if (!tafbby212001.equals(other.tafbby212001))
            return false;
        if (tafbby213201 == null) {
            if (other.tafbby213201 != null)
                return false;
        } else if (!tafbby213201.equals(other.tafbby213201))
            return false;
        if (tafbby213401 == null) {
            if (other.tafbby213401 != null)
                return false;
        } else if (!tafbby213401.equals(other.tafbby213401))
            return false;
        if (tafbby213601 == null) {
            if (other.tafbby213601 != null)
                return false;
        } else if (!tafbby213601.equals(other.tafbby213601))
            return false;
        if (tafbby213801 == null) {
            if (other.tafbby213801 != null)
                return false;
        } else if (!tafbby213801.equals(other.tafbby213801))
            return false;
        if (tafbby214501 == null) {
            if (other.tafbby214501 != null)
                return false;
        } else if (!tafbby214501.equals(other.tafbby214501))
            return false;
        if (tafbby220401 == null) {
            if (other.tafbby220401 != null)
                return false;
        } else if (!tafbby220401.equals(other.tafbby220401))
            return false;
        if (tafbby220601 == null) {
            if (other.tafbby220601 != null)
                return false;
        } else if (!tafbby220601.equals(other.tafbby220601))
            return false;
        if (tafbby220701 == null) {
            if (other.tafbby220701 != null)
                return false;
        } else if (!tafbby220701.equals(other.tafbby220701))
            return false;
        if (tafbby220702 == null) {
            if (other.tafbby220702 != null)
                return false;
        } else if (!tafbby220702.equals(other.tafbby220702))
            return false;
        if (tafbby220801 == null) {
            if (other.tafbby220801 != null)
                return false;
        } else if (!tafbby220801.equals(other.tafbby220801))
            return false;
        if (tafbby230101 == null) {
            if (other.tafbby230101 != null)
                return false;
        } else if (!tafbby230101.equals(other.tafbby230101))
            return false;
        if (tafbby230401 == null) {
            if (other.tafbby230401 != null)
                return false;
        } else if (!tafbby230401.equals(other.tafbby230401))
            return false;
        if (tafbby240101 == null) {
            if (other.tafbby240101 != null)
                return false;
        } else if (!tafbby240101.equals(other.tafbby240101))
            return false;
        if (tafbby300000 == null) {
            if (other.tafbby300000 != null)
                return false;
        } else if (!tafbby300000.equals(other.tafbby300000))
            return false;
        if (tafbby310101 == null) {
            if (other.tafbby310101 != null)
                return false;
        } else if (!tafbby310101.equals(other.tafbby310101))
            return false;
        if (tafbby310201 == null) {
            if (other.tafbby310201 != null)
                return false;
        } else if (!tafbby310201.equals(other.tafbby310201))
            return false;
        if (tafbby310301 == null) {
            if (other.tafbby310301 != null)
                return false;
        } else if (!tafbby310301.equals(other.tafbby310301))
            return false;
        if (tafbby310401 == null) {
            if (other.tafbby310401 != null)
                return false;
        } else if (!tafbby310401.equals(other.tafbby310401))
            return false;
        if (tafbby310601 == null) {
            if (other.tafbby310601 != null)
                return false;
        } else if (!tafbby310601.equals(other.tafbby310601))
            return false;
        if (tafbby310701 == null) {
            if (other.tafbby310701 != null)
                return false;
        } else if (!tafbby310701.equals(other.tafbby310701))
            return false;
        if (tafbby310703 == null) {
            if (other.tafbby310703 != null)
                return false;
        } else if (!tafbby310703.equals(other.tafbby310703))
            return false;
        if (tafbby310801 == null) {
            if (other.tafbby310801 != null)
                return false;
        } else if (!tafbby310801.equals(other.tafbby310801))
            return false;
        if (tafbby310802 == null) {
            if (other.tafbby310802 != null)
                return false;
        } else if (!tafbby310802.equals(other.tafbby310802))
            return false;
        if (tafbby311101 == null) {
            if (other.tafbby311101 != null)
                return false;
        } else if (!tafbby311101.equals(other.tafbby311101))
            return false;
        if (tafbby311201 == null) {
            if (other.tafbby311201 != null)
                return false;
        } else if (!tafbby311201.equals(other.tafbby311201))
            return false;
        if (tafbby400000 == null) {
            if (other.tafbby400000 != null)
                return false;
        } else if (!tafbby400000.equals(other.tafbby400000))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BondFinFalBalaTafbb [" + (id != null ? "id=" + id + ", " : "")
                + (isvalid != null ? "isvalid=" + isvalid + ", " : "")
                + (createtime != null ? "createtime=" + createtime + ", " : "")
                + (updatetime != null ? "updatetime=" + updatetime + ", " : "")
                + (ccxeid != null ? "ccxeid=" + ccxeid + ", " : "")
                + (bondUniCode != null ? "bondUniCode=" + bondUniCode + ", " : "")
                + (bondCode != null ? "bondCode=" + bondCode + ", " : "")
                + (bondShortName != null ? "bondShortName=" + bondShortName + ", " : "")
                + (endDate != null ? "endDate=" + endDate + ", " : "")
                + (sheetMarkPar != null ? "sheetMarkPar=" + sheetMarkPar + ", " : "")
                + (sheetAttrPar != null ? "sheetAttrPar=" + sheetAttrPar + ", " : "")
                + (comUniCode != null ? "comUniCode=" + comUniCode + ", " : "")
                + (tafbby110101 != null ? "tafbby110101=" + tafbby110101 + ", " : "")
                + (tafbbf110115 != null ? "tafbbf110115=" + tafbbf110115 + ", " : "")
                + (tafbbf110601 != null ? "tafbbf110601=" + tafbbf110601 + ", " : "")
                + (tafbbf110701 != null ? "tafbbf110701=" + tafbbf110701 + ", " : "")
                + (tafbby110901 != null ? "tafbby110901=" + tafbby110901 + ", " : "")
                + (tafbby110201 != null ? "tafbby110201=" + tafbby110201 + ", " : "")
                + (tafbby110401 != null ? "tafbby110401=" + tafbby110401 + ", " : "")
                + (tafbby110501 != null ? "tafbby110501=" + tafbby110501 + ", " : "")
                + (tafbby110701 != null ? "tafbby110701=" + tafbby110701 + ", " : "")
                + (tafbby124101 != null ? "tafbby124101=" + tafbby124101 + ", " : "")
                + (tafbby110801 != null ? "tafbby110801=" + tafbby110801 + ", " : "")
                + (tafbby112001 != null ? "tafbby112001=" + tafbby112001 + ", " : "")
                + (tafbby111701 != null ? "tafbby111701=" + tafbby111701 + ", " : "")
                + (tafbby111801 != null ? "tafbby111801=" + tafbby111801 + ", " : "")
                + (tafbby111601 != null ? "tafbby111601=" + tafbby111601 + ", " : "")
                + (tafbbf111601 != null ? "tafbbf111601=" + tafbbf111601 + ", " : "")
                + (tafbbf111701 != null ? "tafbbf111701=" + tafbbf111701 + ", " : "")
                + (tafbbf111801 != null ? "tafbbf111801=" + tafbbf111801 + ", " : "")
                + (tafbbf112001 != null ? "tafbbf112001=" + tafbbf112001 + ", " : "")
                + (tafbbf112101 != null ? "tafbbf112101=" + tafbbf112101 + ", " : "")
                + (tafbbf112201 != null ? "tafbbf112201=" + tafbbf112201 + ", " : "")
                + (tafbbf112301 != null ? "tafbbf112301=" + tafbbf112301 + ", " : "")
                + (tafbbf113701 != null ? "tafbbf113701=" + tafbbf113701 + ", " : "")
                + (tafbbf115101 != null ? "tafbbf115101=" + tafbbf115101 + ", " : "")
                + (tafbbf115401 != null ? "tafbbf115401=" + tafbbf115401 + ", " : "")
                + (tafbbf110501 != null ? "tafbbf110501=" + tafbbf110501 + ", " : "")
                + (tafbby121501 != null ? "tafbby121501=" + tafbby121501 + ", " : "")
                + (tafbby110601 != null ? "tafbby110601=" + tafbby110601 + ", " : "")
                + (tafbby110602 != null ? "tafbby110602=" + tafbby110602 + ", " : "")
                + (tafbbf113901 != null ? "tafbbf113901=" + tafbbf113901 + ", " : "")
                + (tafbby123401 != null ? "tafbby123401=" + tafbby123401 + ", " : "")
                + (tafbby121601 != null ? "tafbby121601=" + tafbby121601 + ", " : "")
                + (tafbby121701 != null ? "tafbby121701=" + tafbby121701 + ", " : "")
                + (tafbby121117 != null ? "tafbby121117=" + tafbby121117 + ", " : "")
                + (tafbby121901 != null ? "tafbby121901=" + tafbby121901 + ", " : "")
                + (tafbby122327 != null ? "tafbby122327=" + tafbby122327 + ", " : "")
                + (tafbby122427 != null ? "tafbby122427=" + tafbby122427 + ", " : "")
                + (tafbby122601 != null ? "tafbby122601=" + tafbby122601 + ", " : "")
                + (tafbby122801 != null ? "tafbby122801=" + tafbby122801 + ", " : "")
                + (tafbby122901 != null ? "tafbby122901=" + tafbby122901 + ", " : "")
                + (tafbby123201 != null ? "tafbby123201=" + tafbby123201 + ", " : "")
                + (tafbby123301 != null ? "tafbby123301=" + tafbby123301 + ", " : "")
                + (tafbby123302 != null ? "tafbby123302=" + tafbby123302 + ", " : "")
                + (tafbby123701 != null ? "tafbby123701=" + tafbby123701 + ", " : "")
                + (tafbby130000 != null ? "tafbby130000=" + tafbby130000 + ", " : "")
                + (tafbbAseSpec != null ? "tafbbAseSpec=" + tafbbAseSpec + ", " : "")
                + (tafbbAseAdju != null ? "tafbbAseAdju=" + tafbbAseAdju + ", " : "")
                + (tafbb100000 != null ? "tafbb100000=" + tafbb100000 + ", " : "")
                + (tafbby210201 != null ? "tafbby210201=" + tafbby210201 + ", " : "")
                + (tafbby210401 != null ? "tafbby210401=" + tafbby210401 + ", " : "")
                + (tafbby211201 != null ? "tafbby211201=" + tafbby211201 + ", " : "")
                + (tafbby210101 != null ? "tafbby210101=" + tafbby210101 + ", " : "")
                + (tafbby220401 != null ? "tafbby220401=" + tafbby220401 + ", " : "")
                + (tafbby211401 != null ? "tafbby211401=" + tafbby211401 + ", " : "")
                + (tafbby210301 != null ? "tafbby210301=" + tafbby210301 + ", " : "")
                + (tafbby210501 != null ? "tafbby210501=" + tafbby210501 + ", " : "")
                + (tafbby210601 != null ? "tafbby210601=" + tafbby210601 + ", " : "")
                + (tafbby210701 != null ? "tafbby210701=" + tafbby210701 + ", " : "")
                + (tafbby210702 != null ? "tafbby210702=" + tafbby210702 + ", " : "")
                + (tafbbf212301 != null ? "tafbbf212301=" + tafbbf212301 + ", " : "")
                + (tafbbf212401 != null ? "tafbbf212401=" + tafbbf212401 + ", " : "")
                + (tafbbf213201 != null ? "tafbbf213201=" + tafbbf213201 + ", " : "")
                + (tafbbf213301 != null ? "tafbbf213301=" + tafbbf213301 + ", " : "")
                + (tafbbf213401 != null ? "tafbbf213401=" + tafbbf213401 + ", " : "")
                + (tafbbf214301 != null ? "tafbbf214301=" + tafbbf214301 + ", " : "")
                + (tafbbf214401 != null ? "tafbbf214401=" + tafbbf214401 + ", " : "")
                + (tafbbf220501 != null ? "tafbbf220501=" + tafbbf220501 + ", " : "")
                + (tafbbf214501 != null ? "tafbbf214501=" + tafbbf214501 + ", " : "")
                + (tafbbf214601 != null ? "tafbbf214601=" + tafbbf214601 + ", " : "")
                + (tafbbf220101 != null ? "tafbbf220101=" + tafbbf220101 + ", " : "")
                + (tafbbf220201 != null ? "tafbbf220201=" + tafbbf220201 + ", " : "")
                + (tafbby213201 != null ? "tafbby213201=" + tafbby213201 + ", " : "")
                + (tafbby212001 != null ? "tafbby212001=" + tafbby212001 + ", " : "")
                + (tafbby213401 != null ? "tafbby213401=" + tafbby213401 + ", " : "")
                + (tafbby211901 != null ? "tafbby211901=" + tafbby211901 + ", " : "")
                + (tafbby214501 != null ? "tafbby214501=" + tafbby214501 + ", " : "")
                + (tafbby213601 != null ? "tafbby213601=" + tafbby213601 + ", " : "")
                + (tafbby230401 != null ? "tafbby230401=" + tafbby230401 + ", " : "")
                + (tafbby213801 != null ? "tafbby213801=" + tafbby213801 + ", " : "")
                + (tafbby220601 != null ? "tafbby220601=" + tafbby220601 + ", " : "")
                + (tafbby220801 != null ? "tafbby220801=" + tafbby220801 + ", " : "")
                + (tafbby220701 != null ? "tafbby220701=" + tafbby220701 + ", " : "")
                + (tafbby220702 != null ? "tafbby220702=" + tafbby220702 + ", " : "")
                + (tafbby230101 != null ? "tafbby230101=" + tafbby230101 + ", " : "")
                + (tafbby240101 != null ? "tafbby240101=" + tafbby240101 + ", " : "")
                + (tafbbLiabSpec != null ? "tafbbLiabSpec=" + tafbbLiabSpec + ", " : "")
                + (tafbbLiabAdju != null ? "tafbbLiabAdju=" + tafbbLiabAdju + ", " : "")
                + (tafbb210000 != null ? "tafbb210000=" + tafbb210000 + ", " : "")
                + (tafbby310201 != null ? "tafbby310201=" + tafbby310201 + ", " : "")
                + (tafbby310301 != null ? "tafbby310301=" + tafbby310301 + ", " : "")
                + (tafbby310703 != null ? "tafbby310703=" + tafbby310703 + ", " : "")
                + (tafbby310401 != null ? "tafbby310401=" + tafbby310401 + ", " : "")
                + (tafbby310601 != null ? "tafbby310601=" + tafbby310601 + ", " : "")
                + (tafbby310701 != null ? "tafbby310701=" + tafbby310701 + ", " : "")
                + (tafbby310801 != null ? "tafbby310801=" + tafbby310801 + ", " : "")
                + (tafbby310802 != null ? "tafbby310802=" + tafbby310802 + ", " : "")
                + (tafbby311101 != null ? "tafbby311101=" + tafbby311101 + ", " : "")
                + (tafbby310101 != null ? "tafbby310101=" + tafbby310101 + ", " : "")
                + (tafbbf301001 != null ? "tafbbf301001=" + tafbbf301001 + ", " : "")
                + (tafbbEquSpec != null ? "tafbbEquSpec=" + tafbbEquSpec + ", " : "")
                + (tafbbEquAdju != null ? "tafbbEquAdju=" + tafbbEquAdju + ", " : "")
                + (tafbby300000 != null ? "tafbby300000=" + tafbby300000 + ", " : "")
                + (tafbby311201 != null ? "tafbby311201=" + tafbby311201 + ", " : "")
                + (tafbbLiabEquSpec != null ? "tafbbLiabEquSpec=" + tafbbLiabEquSpec + ", " : "")
                + (tafbbLiabEquAdju != null ? "tafbbLiabEquAdju=" + tafbbLiabEquAdju + ", " : "")
                + (tafbby400000 != null ? "tafbby400000=" + tafbby400000 + ", " : "")
                + (tafbbSpecDes != null ? "tafbbSpecDes=" + tafbbSpecDes + ", " : "")
                + (infoPubDate != null ? "infoPubDate=" + infoPubDate : "") + "]";
    }
    
    public String createSelectColumnSql(){
        String selectSql = "";
        selectSql += ",ID";
        selectSql += ",ISVALID";
        selectSql += ",CREATETIME";
        selectSql += ",UPDATETIME";
        selectSql += ",BOND_UNI_CODE";
        selectSql += ",BOND_CODE";
        selectSql += ",BOND_SHORT_NAME";
        selectSql += ",END_DATE";
        selectSql += ",SHEET_MARK_PAR";
        selectSql += ",SHEET_ATTR_PAR";
        selectSql += ",COM_UNI_CODE";
        selectSql += ",TAFBBY_110101";
        selectSql += ",TAFBBF_110115";
        selectSql += ",TAFBBF_110601";
        selectSql += ",TAFBBF_110701";
        selectSql += ",TAFBBY_110901";
        selectSql += ",TAFBBY_110201";
        selectSql += ",TAFBBY_110401";
        selectSql += ",TAFBBY_110501";
        selectSql += ",TAFBBY_110701";
        selectSql += ",TAFBBY_124101";
        selectSql += ",TAFBBY_110801";
        selectSql += ",TAFBBY_112001";
        selectSql += ",TAFBBY_111701";
        selectSql += ",TAFBBY_111801";
        selectSql += ",TAFBBY_111601";
        selectSql += ",TAFBBF_111601";
        selectSql += ",TAFBBF_111701";
        selectSql += ",TAFBBF_111801";
        selectSql += ",TAFBBF_112001";
        selectSql += ",TAFBBF_112101";
        selectSql += ",TAFBBF_112201";
        selectSql += ",TAFBBF_112301";
        selectSql += ",TAFBBF_113701";
        selectSql += ",TAFBBF_115101";
        selectSql += ",TAFBBF_115401";
        selectSql += ",TAFBBF_110501";
        selectSql += ",TAFBBY_121501";
        selectSql += ",TAFBBY_110601";
        selectSql += ",TAFBBY_110602";
        selectSql += ",TAFBBF_113901";
        selectSql += ",TAFBBY_123401";
        selectSql += ",TAFBBY_121601";
        selectSql += ",TAFBBY_121701";
        selectSql += ",TAFBBY_121117";
        selectSql += ",TAFBBY_121901";
        selectSql += ",TAFBBY_122327";
        selectSql += ",TAFBBY_122427";
        selectSql += ",TAFBBY_122601";
        selectSql += ",TAFBBY_122801";
        selectSql += ",TAFBBY_122901";
        selectSql += ",TAFBBY_123201";
        selectSql += ",TAFBBY_123301";
        selectSql += ",TAFBBY_123302";
        selectSql += ",TAFBBY_123701";
        selectSql += ",TAFBBY_130000";
        selectSql += ",TAFBB_ASE_SPEC";
        selectSql += ",TAFBB_ASE_ADJU";
        selectSql += ",TAFBB_100000";
        selectSql += ",TAFBBY_210201";
        selectSql += ",TAFBBY_210401";
        selectSql += ",TAFBBY_211201";
        selectSql += ",TAFBBY_210101";
        selectSql += ",TAFBBY_220401";
        selectSql += ",TAFBBY_211401";
        selectSql += ",TAFBBY_210301";
        selectSql += ",TAFBBY_210501";
        selectSql += ",TAFBBY_210601";
        selectSql += ",TAFBBY_210701";
        selectSql += ",TAFBBY_210702";
        selectSql += ",TAFBBF_212301";
        selectSql += ",TAFBBF_212401";
        selectSql += ",TAFBBF_213201";
        selectSql += ",TAFBBF_213301";
        selectSql += ",TAFBBF_213401";
        selectSql += ",TAFBBF_214301";
        selectSql += ",TAFBBF_214401";
        selectSql += ",TAFBBF_220501";
        selectSql += ",TAFBBF_214501";
        selectSql += ",TAFBBF_214601";
        selectSql += ",TAFBBF_220101";
        selectSql += ",TAFBBF_220201";
        selectSql += ",TAFBBY_213201";
        selectSql += ",TAFBBY_212001";
        selectSql += ",TAFBBY_213401";
        selectSql += ",TAFBBY_211901";
        selectSql += ",TAFBBY_214501";
        selectSql += ",TAFBBY_213601";
        selectSql += ",TAFBBY_230401";
        selectSql += ",TAFBBY_213801";
        selectSql += ",TAFBBY_220601";
        selectSql += ",TAFBBY_220801";
        selectSql += ",TAFBBY_220701";
        selectSql += ",TAFBBY_220702";
        selectSql += ",TAFBBY_230101";
        selectSql += ",TAFBBY_240101";
        selectSql += ",TAFBB_LIAB_SPEC";
        selectSql += ",TAFBB_LIAB_ADJU";
        selectSql += ",TAFBB_210000";
        selectSql += ",TAFBBY_310201";
        selectSql += ",TAFBBY_310301";
        selectSql += ",TAFBBY_310703";
        selectSql += ",TAFBBY_310401";
        selectSql += ",TAFBBY_310601";
        selectSql += ",TAFBBY_310701";
        selectSql += ",TAFBBY_310801";
        selectSql += ",TAFBBY_310802";
        selectSql += ",TAFBBY_311101";
        selectSql += ",TAFBBY_310101";
        selectSql += ",TAFBBF_301001";
        selectSql += ",TAFBB_EQU_SPEC";
        selectSql += ",TAFBB_EQU_ADJU";
        selectSql += ",TAFBBY_300000";
        selectSql += ",TAFBBY_311201";
        selectSql += ",TAFBB_LIAB_EQU_SPEC";
        selectSql += ",TAFBB_LIAB_EQU_ADJU";
        selectSql += ",TAFBBY_400000";
        selectSql += ",TAFBB_SPEC_DES";
        selectSql += ",INFO_PUB_DATE";
 	   return selectSql.substring(1);
     }
 	
 	public String createEqualsSql(String whereSql){
 	   String selectSql = createSelectColumnSql();       
        String sql = "select count(1) from "
        +" (select * from (select "+selectSql+" from bond_ccxe.d_bond_fin_fal_bala_tafbb where "+whereSql
        +" order by ccxeid desc limit 1) a union select * from (select "+selectSql+" from bond_ccxe_snapshot.d_bond_fin_fal_bala_tafbb where "+whereSql+" order by ccxeid desc limit 1) a) a";
        return sql;
     }
     
     public String createEqualsSql(String id, Long bondUniCode, Long comUniCode, String endDate){
 	   String selectSql = createSelectColumnSql();
 	   String whereSql = " id='" + id + "' and bond_uni_code=" + bondUniCode 
         + " and com_uni_code=" + comUniCode 
         + " and end_date='" + endDate + "'";
                
        String sql = "select count(1) from "
        +" (select * from (select "+selectSql+" from bond_ccxe.d_bond_fin_fal_bala_tafbb where "+whereSql
        +" order by ccxeid desc limit 1) a union select * from (select "+selectSql+" from bond_ccxe_snapshot.d_bond_fin_fal_bala_tafbb where "+whereSql+" order by ccxeid desc limit 1) a) a";
        return sql;
     }
}
