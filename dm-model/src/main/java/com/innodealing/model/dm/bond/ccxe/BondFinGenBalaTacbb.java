package com.innodealing.model.dm.bond.ccxe;

import java.util.Date;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.io.Serializable;

@Entity
@Table(name="d_bond_fin_gen_bala_tacbb")
public class BondFinGenBalaTacbb implements Serializable{
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
    @Column(name="TACBB_110101", length=20)
    private BigDecimal tacbb110101;
    
	/**
	 * 
	 */
    @Column(name="TACBB_112201", length=20)
    private BigDecimal tacbb112201;
    
	/**
	 * 
	 */
    @Column(name="TACBB_110401", length=20)
    private BigDecimal tacbb110401;
    
	/**
	 * 
	 */
    @Column(name="TACBB_110501", length=20)
    private BigDecimal tacbb110501;
    
	/**
	 * 
	 */
    @Column(name="TACBB_110601", length=20)
    private BigDecimal tacbb110601;
    
	/**
	 * 
	 */
    @Column(name="TACBB_110711", length=20)
    private BigDecimal tacbb110711;
    
	/**
	 * 
	 */
    @Column(name="TACBB_110721", length=20)
    private BigDecimal tacbb110721;
    
	/**
	 * 
	 */
    @Column(name="TACBB_110901", length=20)
    private BigDecimal tacbb110901;
    
	/**
	 * 
	 */
    @Column(name="TACBB_111511", length=20)
    private BigDecimal tacbb111511;
    
	/**
	 * 
	 */
    @Column(name="TACBB_111531", length=20)
    private BigDecimal tacbb111531;
    
	/**
	 * 
	 */
    @Column(name="TACBB_111601", length=20)
    private BigDecimal tacbb111601;
    
	/**
	 * 
	 */
    @Column(name="TACBB_112301", length=20)
    private BigDecimal tacbb112301;
    
	/**
	 * 
	 */
    @Column(name="TACBB_1021", length=20)
    private BigDecimal tacbb1021;
    
	/**
	 * 
	 */
    @Column(name="TACBB_1302", length=20)
    private BigDecimal tacbb1302;
    
	/**
	 * 
	 */
    @Column(name="TACBB_1210", length=20)
    private BigDecimal tacbb1210;
    
	/**
	 * 
	 */
    @Column(name="TACBB_1211", length=20)
    private BigDecimal tacbb1211;
    
	/**
	 * 
	 */
    @Column(name="TACBB_1212", length=20)
    private BigDecimal tacbb1212;
    
	/**
	 * 
	 */
    @Column(name="TACBB_1111", length=20)
    private BigDecimal tacbb1111;
    
	/**
	 * 
	 */
    @Column(name="TACBB_112101", length=20)
    private BigDecimal tacbb112101;
    
	/**
	 * 
	 */
    @Column(name="TACBB_LIQU_ASE_SPEC", length=20)
    private BigDecimal tacbbLiquAseSpec;
    
	/**
	 * 
	 */
    @Column(name="TACBB_LIQU_ASE_ADJU", length=20)
    private BigDecimal tacbbLiquAseAdju;
    
	/**
	 * 
	 */
    @Column(name="TACBB_110001", length=20)
    private BigDecimal tacbb110001;
    
	/**
	 * 
	 */
    @Column(name="TACBB_120801", length=20)
    private BigDecimal tacbb120801;
    
	/**
	 * 
	 */
    @Column(name="TACBB_120901", length=20)
    private BigDecimal tacbb120901;
    
	/**
	 * 
	 */
    @Column(name="TACBB_121001", length=20)
    private BigDecimal tacbb121001;
    
	/**
	 * 
	 */
    @Column(name="TACBB_120111", length=20)
    private BigDecimal tacbb120111;
    
	/**
	 * 
	 */
    @Column(name="TACBB_121101", length=20)
    private BigDecimal tacbb121101;
    
	/**
	 * 
	 */
    @Column(name="TACBB_130101", length=20)
    private BigDecimal tacbb130101;
    
	/**
	 * 
	 */
    @Column(name="TACBB_130201", length=20)
    private BigDecimal tacbb130201;
    
	/**
	 * 
	 */
    @Column(name="TACBB_130301", length=20)
    private BigDecimal tacbb130301;
    
	/**
	 * 
	 */
    @Column(name="TACBB_130401", length=20)
    private BigDecimal tacbb130401;
    
	/**
	 * 
	 */
    @Column(name="TACBB_130601", length=20)
    private BigDecimal tacbb130601;
    
	/**
	 * 
	 */
    @Column(name="TACBB_130701", length=20)
    private BigDecimal tacbb130701;
    
	/**
	 * 
	 */
    @Column(name="TACBB_140101", length=20)
    private BigDecimal tacbb140101;
    
	/**
	 * 
	 */
    @Column(name="TACBB_140601", length=20)
    private BigDecimal tacbb140601;
    
	/**
	 * 
	 */
    @Column(name="TACBB_140701", length=20)
    private BigDecimal tacbb140701;
    
	/**
	 * 
	 */
    @Column(name="TACBB_140401", length=20)
    private BigDecimal tacbb140401;
    
	/**
	 * 
	 */
    @Column(name="TACBB_150001", length=20)
    private BigDecimal tacbb150001;
    
	/**
	 * 
	 */
    @Column(name="TACBB_1303", length=20)
    private BigDecimal tacbb1303;
    
	/**
	 * 
	 */
    @Column(name="TACBB_160101", length=20)
    private BigDecimal tacbb160101;
    
	/**
	 * 
	 */
    @Column(name="TACBB_NLIQU_ASE_SPEC", length=20)
    private BigDecimal tacbbNliquAseSpec;
    
	/**
	 * 
	 */
    @Column(name="TACBB_NLIQU_ASE_ADJU", length=20)
    private BigDecimal tacbbNliquAseAdju;
    
	/**
	 * 
	 */
    @Column(name="TACBB_160000", length=20)
    private BigDecimal tacbb160000;
    
	/**
	 * 
	 */
    @Column(name="TACBB_ASE_SPEC", length=20)
    private BigDecimal tacbbAseSpec;
    
	/**
	 * 
	 */
    @Column(name="TACBB_ASE_ADJU", length=20)
    private BigDecimal tacbbAseAdju;
    
	/**
	 * 
	 */
    @Column(name="TACBB_100000", length=20)
    private BigDecimal tacbb100000;
    
	/**
	 * 
	 */
    @Column(name="TACBB_210101", length=20)
    private BigDecimal tacbb210101;
    
	/**
	 * 
	 */
    @Column(name="TACBB_210103", length=20)
    private BigDecimal tacbb210103;
    
	/**
	 * 
	 */
    @Column(name="TACBB_212301", length=20)
    private BigDecimal tacbb212301;
    
	/**
	 * 
	 */
    @Column(name="TACBB_210201", length=20)
    private BigDecimal tacbb210201;
    
	/**
	 * 
	 */
    @Column(name="TACBB_210301", length=20)
    private BigDecimal tacbb210301;
    
	/**
	 * 
	 */
    @Column(name="TACBB_211401", length=20)
    private BigDecimal tacbb211401;
    
	/**
	 * 
	 */
    @Column(name="TACBB_210401", length=20)
    private BigDecimal tacbb210401;
    
	/**
	 * 
	 */
    @Column(name="TACBB_210601", length=20)
    private BigDecimal tacbb210601;
    
	/**
	 * 
	 */
    @Column(name="TACBB_210801", length=20)
    private BigDecimal tacbb210801;
    
	/**
	 * 
	 */
    @Column(name="TACBB_210901", length=20)
    private BigDecimal tacbb210901;
    
	/**
	 * 
	 */
    @Column(name="TACBB_212401", length=20)
    private BigDecimal tacbb212401;
    
	/**
	 * 
	 */
    @Column(name="TACBB_211301", length=20)
    private BigDecimal tacbb211301;
    
	/**
	 * 
	 */
    @Column(name="TACBB_211501", length=20)
    private BigDecimal tacbb211501;
    
	/**
	 * 
	 */
    @Column(name="TACBB_212701", length=20)
    private BigDecimal tacbb212701;
    
	/**
	 * 
	 */
    @Column(name="TACBB_2004", length=20)
    private BigDecimal tacbb2004;
    
	/**
	 * 
	 */
    @Column(name="TACBB_2011", length=20)
    private BigDecimal tacbb2011;
    
	/**
	 * 
	 */
    @Column(name="TACBB_2003", length=20)
    private BigDecimal tacbb2003;
    
	/**
	 * 
	 */
    @Column(name="TACBB_2111", length=20)
    private BigDecimal tacbb2111;
    
	/**
	 * 
	 */
    @Column(name="TACBB_2251", length=20)
    private BigDecimal tacbb2251;
    
	/**
	 * 
	 */
    @Column(name="TACBB_2261", length=20)
    private BigDecimal tacbb2261;
    
	/**
	 * 
	 */
    @Column(name="TACBB_2602", length=20)
    private BigDecimal tacbb2602;
    
	/**
	 * 
	 */
    @Column(name="TACBB_2311", length=20)
    private BigDecimal tacbb2311;
    
	/**
	 * 
	 */
    @Column(name="TACBB_2312", length=20)
    private BigDecimal tacbb2312;
    
	/**
	 * 
	 */
    @Column(name="TACBB_212501", length=20)
    private BigDecimal tacbb212501;
    
	/**
	 * 
	 */
    @Column(name="TACBB_212101", length=20)
    private BigDecimal tacbb212101;
    
	/**
	 * 
	 */
    @Column(name="TACBB_LIQU_LIAB_SPEC", length=20)
    private BigDecimal tacbbLiquLiabSpec;
    
	/**
	 * 
	 */
    @Column(name="TACBB_LIQU_LIAB_ADJU", length=20)
    private BigDecimal tacbbLiquLiabAdju;
    
	/**
	 * 
	 */
    @Column(name="TACBB_210001", length=20)
    private BigDecimal tacbb210001;
    
	/**
	 * 
	 */
    @Column(name="TACBB_220101", length=20)
    private BigDecimal tacbb220101;
    
	/**
	 * 
	 */
    @Column(name="TACBB_220201", length=20)
    private BigDecimal tacbb220201;
    
	/**
	 * 
	 */
    @Column(name="TACBB_220301", length=20)
    private BigDecimal tacbb220301;
    
	/**
	 * 
	 */
    @Column(name="TACBB_220401", length=20)
    private BigDecimal tacbb220401;
    
	/**
	 * 
	 */
    @Column(name="TACBB_211901", length=20)
    private BigDecimal tacbb211901;
    
	/**
	 * 
	 */
    @Column(name="TACBB_240001", length=20)
    private BigDecimal tacbb240001;
    
	/**
	 * 
	 */
    @Column(name="TACBB_240002", length=20)
    private BigDecimal tacbb240002;
    
	/**
	 * 
	 */
    @Column(name="TACBB_250001", length=20)
    private BigDecimal tacbb250001;
    
	/**
	 * 
	 */
    @Column(name="TACBB_NLIQU_LIAB_SPEC", length=20)
    private BigDecimal tacbbNliquLiabSpec;
    
	/**
	 * 
	 */
    @Column(name="TACBB_NLIQU_LIAB_ADJU", length=20)
    private BigDecimal tacbbNliquLiabAdju;
    
	/**
	 * 
	 */
    @Column(name="TACBB_270001", length=20)
    private BigDecimal tacbb270001;
    
	/**
	 * 
	 */
    @Column(name="TACBB_LIAB_SPEC", length=20)
    private BigDecimal tacbbLiabSpec;
    
	/**
	 * 
	 */
    @Column(name="TACBB_LIAB_ADJU", length=20)
    private BigDecimal tacbbLiabAdju;
    
	/**
	 * 
	 */
    @Column(name="TACBB_200000", length=20)
    private BigDecimal tacbb200000;
    
	/**
	 * 
	 */
    @Column(name="TACBB_310101", length=20)
    private BigDecimal tacbb310101;
    
	/**
	 * 
	 */
    @Column(name="TACBB_310201", length=20)
    private BigDecimal tacbb310201;
    
	/**
	 * 
	 */
    @Column(name="TACBB_310902", length=20)
    private BigDecimal tacbb310902;
    
	/**
	 * 
	 */
    @Column(name="TACBB_310903", length=20)
    private BigDecimal tacbb310903;
    
	/**
	 * 
	 */
    @Column(name="TACBB_310301", length=20)
    private BigDecimal tacbb310301;
    
	/**
	 * 
	 */
    @Column(name="TACBB_310701", length=20)
    private BigDecimal tacbb310701;
    
	/**
	 * 
	 */
    @Column(name="TACBB_4102", length=20)
    private BigDecimal tacbb4102;
    
	/**
	 * 
	 */
    @Column(name="TACBB_310801", length=20)
    private BigDecimal tacbb310801;
    
	/**
	 * 
	 */
    @Column(name="TACBB_310601", length=20)
    private BigDecimal tacbb310601;
    
	/**
	 * 
	 */
    @Column(name="TACBB_6101", length=20)
    private BigDecimal tacbb6101;
    
	/**
	 * 
	 */
    @Column(name="TACBB_PARE_COM_EQU_SPEC", length=20)
    private BigDecimal tacbbPareComEquSpec;
    
	/**
	 * 
	 */
    @Column(name="TACBB_PARE_COM_EQU_ADJU", length=20)
    private BigDecimal tacbbPareComEquAdju;
    
	/**
	 * 
	 */
    @Column(name="TACBB_311101", length=20)
    private BigDecimal tacbb311101;
    
	/**
	 * 
	 */
    @Column(name="TACBB_400000", length=20)
    private BigDecimal tacbb400000;
    
	/**
	 * 
	 */
    @Column(name="TACBB_EQU_ADJU", length=20)
    private BigDecimal tacbbEquAdju;
    
	/**
	 * 
	 */
    @Column(name="TACBB_300000", length=20)
    private BigDecimal tacbb300000;
    
	/**
	 * 
	 */
    @Column(name="TACBB_LIAB_EQU_SPEC", length=20)
    private BigDecimal tacbbLiabEquSpec;
    
	/**
	 * 
	 */
    @Column(name="TACBB_LIAB_EQU_ADJU", length=20)
    private BigDecimal tacbbLiabEquAdju;
    
	/**
	 * 
	 */
    @Column(name="TACBB_500000", length=20)
    private BigDecimal tacbb500000;
    
	/**
	 * 
	 */
    @Column(name="TACBB_SPEC_DES", length=16777215)
    private String tacbbSpecDes;
    
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
	
    public void setTacbb110101(BigDecimal tacbb110101){
       this.tacbb110101 = tacbb110101;
    }
    
    public BigDecimal getTacbb110101(){
       return this.tacbb110101;
    }
	
    public void setTacbb112201(BigDecimal tacbb112201){
       this.tacbb112201 = tacbb112201;
    }
    
    public BigDecimal getTacbb112201(){
       return this.tacbb112201;
    }
	
    public void setTacbb110401(BigDecimal tacbb110401){
       this.tacbb110401 = tacbb110401;
    }
    
    public BigDecimal getTacbb110401(){
       return this.tacbb110401;
    }
	
    public void setTacbb110501(BigDecimal tacbb110501){
       this.tacbb110501 = tacbb110501;
    }
    
    public BigDecimal getTacbb110501(){
       return this.tacbb110501;
    }
	
    public void setTacbb110601(BigDecimal tacbb110601){
       this.tacbb110601 = tacbb110601;
    }
    
    public BigDecimal getTacbb110601(){
       return this.tacbb110601;
    }
	
    public void setTacbb110711(BigDecimal tacbb110711){
       this.tacbb110711 = tacbb110711;
    }
    
    public BigDecimal getTacbb110711(){
       return this.tacbb110711;
    }
	
    public void setTacbb110721(BigDecimal tacbb110721){
       this.tacbb110721 = tacbb110721;
    }
    
    public BigDecimal getTacbb110721(){
       return this.tacbb110721;
    }
	
    public void setTacbb110901(BigDecimal tacbb110901){
       this.tacbb110901 = tacbb110901;
    }
    
    public BigDecimal getTacbb110901(){
       return this.tacbb110901;
    }
	
    public void setTacbb111511(BigDecimal tacbb111511){
       this.tacbb111511 = tacbb111511;
    }
    
    public BigDecimal getTacbb111511(){
       return this.tacbb111511;
    }
	
    public void setTacbb111531(BigDecimal tacbb111531){
       this.tacbb111531 = tacbb111531;
    }
    
    public BigDecimal getTacbb111531(){
       return this.tacbb111531;
    }
	
    public void setTacbb111601(BigDecimal tacbb111601){
       this.tacbb111601 = tacbb111601;
    }
    
    public BigDecimal getTacbb111601(){
       return this.tacbb111601;
    }
	
    public void setTacbb112301(BigDecimal tacbb112301){
       this.tacbb112301 = tacbb112301;
    }
    
    public BigDecimal getTacbb112301(){
       return this.tacbb112301;
    }
	
    public void setTacbb1021(BigDecimal tacbb1021){
       this.tacbb1021 = tacbb1021;
    }
    
    public BigDecimal getTacbb1021(){
       return this.tacbb1021;
    }
	
    public void setTacbb1302(BigDecimal tacbb1302){
       this.tacbb1302 = tacbb1302;
    }
    
    public BigDecimal getTacbb1302(){
       return this.tacbb1302;
    }
	
    public void setTacbb1210(BigDecimal tacbb1210){
       this.tacbb1210 = tacbb1210;
    }
    
    public BigDecimal getTacbb1210(){
       return this.tacbb1210;
    }
	
    public void setTacbb1211(BigDecimal tacbb1211){
       this.tacbb1211 = tacbb1211;
    }
    
    public BigDecimal getTacbb1211(){
       return this.tacbb1211;
    }
	
    public void setTacbb1212(BigDecimal tacbb1212){
       this.tacbb1212 = tacbb1212;
    }
    
    public BigDecimal getTacbb1212(){
       return this.tacbb1212;
    }
	
    public void setTacbb1111(BigDecimal tacbb1111){
       this.tacbb1111 = tacbb1111;
    }
    
    public BigDecimal getTacbb1111(){
       return this.tacbb1111;
    }
	
    public void setTacbb112101(BigDecimal tacbb112101){
       this.tacbb112101 = tacbb112101;
    }
    
    public BigDecimal getTacbb112101(){
       return this.tacbb112101;
    }
	
    public void setTacbbLiquAseSpec(BigDecimal tacbbLiquAseSpec){
       this.tacbbLiquAseSpec = tacbbLiquAseSpec;
    }
    
    public BigDecimal getTacbbLiquAseSpec(){
       return this.tacbbLiquAseSpec;
    }
	
    public void setTacbbLiquAseAdju(BigDecimal tacbbLiquAseAdju){
       this.tacbbLiquAseAdju = tacbbLiquAseAdju;
    }
    
    public BigDecimal getTacbbLiquAseAdju(){
       return this.tacbbLiquAseAdju;
    }
	
    public void setTacbb110001(BigDecimal tacbb110001){
       this.tacbb110001 = tacbb110001;
    }
    
    public BigDecimal getTacbb110001(){
       return this.tacbb110001;
    }
	
    public void setTacbb120801(BigDecimal tacbb120801){
       this.tacbb120801 = tacbb120801;
    }
    
    public BigDecimal getTacbb120801(){
       return this.tacbb120801;
    }
	
    public void setTacbb120901(BigDecimal tacbb120901){
       this.tacbb120901 = tacbb120901;
    }
    
    public BigDecimal getTacbb120901(){
       return this.tacbb120901;
    }
	
    public void setTacbb121001(BigDecimal tacbb121001){
       this.tacbb121001 = tacbb121001;
    }
    
    public BigDecimal getTacbb121001(){
       return this.tacbb121001;
    }
	
    public void setTacbb120111(BigDecimal tacbb120111){
       this.tacbb120111 = tacbb120111;
    }
    
    public BigDecimal getTacbb120111(){
       return this.tacbb120111;
    }
	
    public void setTacbb121101(BigDecimal tacbb121101){
       this.tacbb121101 = tacbb121101;
    }
    
    public BigDecimal getTacbb121101(){
       return this.tacbb121101;
    }
	
    public void setTacbb130101(BigDecimal tacbb130101){
       this.tacbb130101 = tacbb130101;
    }
    
    public BigDecimal getTacbb130101(){
       return this.tacbb130101;
    }
	
    public void setTacbb130201(BigDecimal tacbb130201){
       this.tacbb130201 = tacbb130201;
    }
    
    public BigDecimal getTacbb130201(){
       return this.tacbb130201;
    }
	
    public void setTacbb130301(BigDecimal tacbb130301){
       this.tacbb130301 = tacbb130301;
    }
    
    public BigDecimal getTacbb130301(){
       return this.tacbb130301;
    }
	
    public void setTacbb130401(BigDecimal tacbb130401){
       this.tacbb130401 = tacbb130401;
    }
    
    public BigDecimal getTacbb130401(){
       return this.tacbb130401;
    }
	
    public void setTacbb130601(BigDecimal tacbb130601){
       this.tacbb130601 = tacbb130601;
    }
    
    public BigDecimal getTacbb130601(){
       return this.tacbb130601;
    }
	
    public void setTacbb130701(BigDecimal tacbb130701){
       this.tacbb130701 = tacbb130701;
    }
    
    public BigDecimal getTacbb130701(){
       return this.tacbb130701;
    }
	
    public void setTacbb140101(BigDecimal tacbb140101){
       this.tacbb140101 = tacbb140101;
    }
    
    public BigDecimal getTacbb140101(){
       return this.tacbb140101;
    }
	
    public void setTacbb140601(BigDecimal tacbb140601){
       this.tacbb140601 = tacbb140601;
    }
    
    public BigDecimal getTacbb140601(){
       return this.tacbb140601;
    }
	
    public void setTacbb140701(BigDecimal tacbb140701){
       this.tacbb140701 = tacbb140701;
    }
    
    public BigDecimal getTacbb140701(){
       return this.tacbb140701;
    }
	
    public void setTacbb140401(BigDecimal tacbb140401){
       this.tacbb140401 = tacbb140401;
    }
    
    public BigDecimal getTacbb140401(){
       return this.tacbb140401;
    }
	
    public void setTacbb150001(BigDecimal tacbb150001){
       this.tacbb150001 = tacbb150001;
    }
    
    public BigDecimal getTacbb150001(){
       return this.tacbb150001;
    }
	
    public void setTacbb1303(BigDecimal tacbb1303){
       this.tacbb1303 = tacbb1303;
    }
    
    public BigDecimal getTacbb1303(){
       return this.tacbb1303;
    }
	
    public void setTacbb160101(BigDecimal tacbb160101){
       this.tacbb160101 = tacbb160101;
    }
    
    public BigDecimal getTacbb160101(){
       return this.tacbb160101;
    }
	
    public void setTacbbNliquAseSpec(BigDecimal tacbbNliquAseSpec){
       this.tacbbNliquAseSpec = tacbbNliquAseSpec;
    }
    
    public BigDecimal getTacbbNliquAseSpec(){
       return this.tacbbNliquAseSpec;
    }
	
    public void setTacbbNliquAseAdju(BigDecimal tacbbNliquAseAdju){
       this.tacbbNliquAseAdju = tacbbNliquAseAdju;
    }
    
    public BigDecimal getTacbbNliquAseAdju(){
       return this.tacbbNliquAseAdju;
    }
	
    public void setTacbb160000(BigDecimal tacbb160000){
       this.tacbb160000 = tacbb160000;
    }
    
    public BigDecimal getTacbb160000(){
       return this.tacbb160000;
    }
	
    public void setTacbbAseSpec(BigDecimal tacbbAseSpec){
       this.tacbbAseSpec = tacbbAseSpec;
    }
    
    public BigDecimal getTacbbAseSpec(){
       return this.tacbbAseSpec;
    }
	
    public void setTacbbAseAdju(BigDecimal tacbbAseAdju){
       this.tacbbAseAdju = tacbbAseAdju;
    }
    
    public BigDecimal getTacbbAseAdju(){
       return this.tacbbAseAdju;
    }
	
    public void setTacbb100000(BigDecimal tacbb100000){
       this.tacbb100000 = tacbb100000;
    }
    
    public BigDecimal getTacbb100000(){
       return this.tacbb100000;
    }
	
    public void setTacbb210101(BigDecimal tacbb210101){
       this.tacbb210101 = tacbb210101;
    }
    
    public BigDecimal getTacbb210101(){
       return this.tacbb210101;
    }
	
    public void setTacbb210103(BigDecimal tacbb210103){
       this.tacbb210103 = tacbb210103;
    }
    
    public BigDecimal getTacbb210103(){
       return this.tacbb210103;
    }
	
    public void setTacbb212301(BigDecimal tacbb212301){
       this.tacbb212301 = tacbb212301;
    }
    
    public BigDecimal getTacbb212301(){
       return this.tacbb212301;
    }
	
    public void setTacbb210201(BigDecimal tacbb210201){
       this.tacbb210201 = tacbb210201;
    }
    
    public BigDecimal getTacbb210201(){
       return this.tacbb210201;
    }
	
    public void setTacbb210301(BigDecimal tacbb210301){
       this.tacbb210301 = tacbb210301;
    }
    
    public BigDecimal getTacbb210301(){
       return this.tacbb210301;
    }
	
    public void setTacbb211401(BigDecimal tacbb211401){
       this.tacbb211401 = tacbb211401;
    }
    
    public BigDecimal getTacbb211401(){
       return this.tacbb211401;
    }
	
    public void setTacbb210401(BigDecimal tacbb210401){
       this.tacbb210401 = tacbb210401;
    }
    
    public BigDecimal getTacbb210401(){
       return this.tacbb210401;
    }
	
    public void setTacbb210601(BigDecimal tacbb210601){
       this.tacbb210601 = tacbb210601;
    }
    
    public BigDecimal getTacbb210601(){
       return this.tacbb210601;
    }
	
    public void setTacbb210801(BigDecimal tacbb210801){
       this.tacbb210801 = tacbb210801;
    }
    
    public BigDecimal getTacbb210801(){
       return this.tacbb210801;
    }
	
    public void setTacbb210901(BigDecimal tacbb210901){
       this.tacbb210901 = tacbb210901;
    }
    
    public BigDecimal getTacbb210901(){
       return this.tacbb210901;
    }
	
    public void setTacbb212401(BigDecimal tacbb212401){
       this.tacbb212401 = tacbb212401;
    }
    
    public BigDecimal getTacbb212401(){
       return this.tacbb212401;
    }
	
    public void setTacbb211301(BigDecimal tacbb211301){
       this.tacbb211301 = tacbb211301;
    }
    
    public BigDecimal getTacbb211301(){
       return this.tacbb211301;
    }
	
    public void setTacbb211501(BigDecimal tacbb211501){
       this.tacbb211501 = tacbb211501;
    }
    
    public BigDecimal getTacbb211501(){
       return this.tacbb211501;
    }
	
    public void setTacbb212701(BigDecimal tacbb212701){
       this.tacbb212701 = tacbb212701;
    }
    
    public BigDecimal getTacbb212701(){
       return this.tacbb212701;
    }
	
    public void setTacbb2004(BigDecimal tacbb2004){
       this.tacbb2004 = tacbb2004;
    }
    
    public BigDecimal getTacbb2004(){
       return this.tacbb2004;
    }
	
    public void setTacbb2011(BigDecimal tacbb2011){
       this.tacbb2011 = tacbb2011;
    }
    
    public BigDecimal getTacbb2011(){
       return this.tacbb2011;
    }
	
    public void setTacbb2003(BigDecimal tacbb2003){
       this.tacbb2003 = tacbb2003;
    }
    
    public BigDecimal getTacbb2003(){
       return this.tacbb2003;
    }
	
    public void setTacbb2111(BigDecimal tacbb2111){
       this.tacbb2111 = tacbb2111;
    }
    
    public BigDecimal getTacbb2111(){
       return this.tacbb2111;
    }
	
    public void setTacbb2251(BigDecimal tacbb2251){
       this.tacbb2251 = tacbb2251;
    }
    
    public BigDecimal getTacbb2251(){
       return this.tacbb2251;
    }
	
    public void setTacbb2261(BigDecimal tacbb2261){
       this.tacbb2261 = tacbb2261;
    }
    
    public BigDecimal getTacbb2261(){
       return this.tacbb2261;
    }
	
    public void setTacbb2602(BigDecimal tacbb2602){
       this.tacbb2602 = tacbb2602;
    }
    
    public BigDecimal getTacbb2602(){
       return this.tacbb2602;
    }
	
    public void setTacbb2311(BigDecimal tacbb2311){
       this.tacbb2311 = tacbb2311;
    }
    
    public BigDecimal getTacbb2311(){
       return this.tacbb2311;
    }
	
    public void setTacbb2312(BigDecimal tacbb2312){
       this.tacbb2312 = tacbb2312;
    }
    
    public BigDecimal getTacbb2312(){
       return this.tacbb2312;
    }
	
    public void setTacbb212501(BigDecimal tacbb212501){
       this.tacbb212501 = tacbb212501;
    }
    
    public BigDecimal getTacbb212501(){
       return this.tacbb212501;
    }
	
    public void setTacbb212101(BigDecimal tacbb212101){
       this.tacbb212101 = tacbb212101;
    }
    
    public BigDecimal getTacbb212101(){
       return this.tacbb212101;
    }
	
    public void setTacbbLiquLiabSpec(BigDecimal tacbbLiquLiabSpec){
       this.tacbbLiquLiabSpec = tacbbLiquLiabSpec;
    }
    
    public BigDecimal getTacbbLiquLiabSpec(){
       return this.tacbbLiquLiabSpec;
    }
	
    public void setTacbbLiquLiabAdju(BigDecimal tacbbLiquLiabAdju){
       this.tacbbLiquLiabAdju = tacbbLiquLiabAdju;
    }
    
    public BigDecimal getTacbbLiquLiabAdju(){
       return this.tacbbLiquLiabAdju;
    }
	
    public void setTacbb210001(BigDecimal tacbb210001){
       this.tacbb210001 = tacbb210001;
    }
    
    public BigDecimal getTacbb210001(){
       return this.tacbb210001;
    }
	
    public void setTacbb220101(BigDecimal tacbb220101){
       this.tacbb220101 = tacbb220101;
    }
    
    public BigDecimal getTacbb220101(){
       return this.tacbb220101;
    }
	
    public void setTacbb220201(BigDecimal tacbb220201){
       this.tacbb220201 = tacbb220201;
    }
    
    public BigDecimal getTacbb220201(){
       return this.tacbb220201;
    }
	
    public void setTacbb220301(BigDecimal tacbb220301){
       this.tacbb220301 = tacbb220301;
    }
    
    public BigDecimal getTacbb220301(){
       return this.tacbb220301;
    }
	
    public void setTacbb220401(BigDecimal tacbb220401){
       this.tacbb220401 = tacbb220401;
    }
    
    public BigDecimal getTacbb220401(){
       return this.tacbb220401;
    }
	
    public void setTacbb211901(BigDecimal tacbb211901){
       this.tacbb211901 = tacbb211901;
    }
    
    public BigDecimal getTacbb211901(){
       return this.tacbb211901;
    }
	
    public void setTacbb240001(BigDecimal tacbb240001){
       this.tacbb240001 = tacbb240001;
    }
    
    public BigDecimal getTacbb240001(){
       return this.tacbb240001;
    }
	
    public void setTacbb240002(BigDecimal tacbb240002){
       this.tacbb240002 = tacbb240002;
    }
    
    public BigDecimal getTacbb240002(){
       return this.tacbb240002;
    }
	
    public void setTacbb250001(BigDecimal tacbb250001){
       this.tacbb250001 = tacbb250001;
    }
    
    public BigDecimal getTacbb250001(){
       return this.tacbb250001;
    }
	
    public void setTacbbNliquLiabSpec(BigDecimal tacbbNliquLiabSpec){
       this.tacbbNliquLiabSpec = tacbbNliquLiabSpec;
    }
    
    public BigDecimal getTacbbNliquLiabSpec(){
       return this.tacbbNliquLiabSpec;
    }
	
    public void setTacbbNliquLiabAdju(BigDecimal tacbbNliquLiabAdju){
       this.tacbbNliquLiabAdju = tacbbNliquLiabAdju;
    }
    
    public BigDecimal getTacbbNliquLiabAdju(){
       return this.tacbbNliquLiabAdju;
    }
	
    public void setTacbb270001(BigDecimal tacbb270001){
       this.tacbb270001 = tacbb270001;
    }
    
    public BigDecimal getTacbb270001(){
       return this.tacbb270001;
    }
	
    public void setTacbbLiabSpec(BigDecimal tacbbLiabSpec){
       this.tacbbLiabSpec = tacbbLiabSpec;
    }
    
    public BigDecimal getTacbbLiabSpec(){
       return this.tacbbLiabSpec;
    }
	
    public void setTacbbLiabAdju(BigDecimal tacbbLiabAdju){
       this.tacbbLiabAdju = tacbbLiabAdju;
    }
    
    public BigDecimal getTacbbLiabAdju(){
       return this.tacbbLiabAdju;
    }
	
    public void setTacbb200000(BigDecimal tacbb200000){
       this.tacbb200000 = tacbb200000;
    }
    
    public BigDecimal getTacbb200000(){
       return this.tacbb200000;
    }
	
    public void setTacbb310101(BigDecimal tacbb310101){
       this.tacbb310101 = tacbb310101;
    }
    
    public BigDecimal getTacbb310101(){
       return this.tacbb310101;
    }
	
    public void setTacbb310201(BigDecimal tacbb310201){
       this.tacbb310201 = tacbb310201;
    }
    
    public BigDecimal getTacbb310201(){
       return this.tacbb310201;
    }
	
    public void setTacbb310902(BigDecimal tacbb310902){
       this.tacbb310902 = tacbb310902;
    }
    
    public BigDecimal getTacbb310902(){
       return this.tacbb310902;
    }
	
    public void setTacbb310903(BigDecimal tacbb310903){
       this.tacbb310903 = tacbb310903;
    }
    
    public BigDecimal getTacbb310903(){
       return this.tacbb310903;
    }
	
    public void setTacbb310301(BigDecimal tacbb310301){
       this.tacbb310301 = tacbb310301;
    }
    
    public BigDecimal getTacbb310301(){
       return this.tacbb310301;
    }
	
    public void setTacbb310701(BigDecimal tacbb310701){
       this.tacbb310701 = tacbb310701;
    }
    
    public BigDecimal getTacbb310701(){
       return this.tacbb310701;
    }
	
    public void setTacbb4102(BigDecimal tacbb4102){
       this.tacbb4102 = tacbb4102;
    }
    
    public BigDecimal getTacbb4102(){
       return this.tacbb4102;
    }
	
    public void setTacbb310801(BigDecimal tacbb310801){
       this.tacbb310801 = tacbb310801;
    }
    
    public BigDecimal getTacbb310801(){
       return this.tacbb310801;
    }
	
    public void setTacbb310601(BigDecimal tacbb310601){
       this.tacbb310601 = tacbb310601;
    }
    
    public BigDecimal getTacbb310601(){
       return this.tacbb310601;
    }
	
    public void setTacbb6101(BigDecimal tacbb6101){
       this.tacbb6101 = tacbb6101;
    }
    
    public BigDecimal getTacbb6101(){
       return this.tacbb6101;
    }
	
    public void setTacbbPareComEquSpec(BigDecimal tacbbPareComEquSpec){
       this.tacbbPareComEquSpec = tacbbPareComEquSpec;
    }
    
    public BigDecimal getTacbbPareComEquSpec(){
       return this.tacbbPareComEquSpec;
    }
	
    public void setTacbbPareComEquAdju(BigDecimal tacbbPareComEquAdju){
       this.tacbbPareComEquAdju = tacbbPareComEquAdju;
    }
    
    public BigDecimal getTacbbPareComEquAdju(){
       return this.tacbbPareComEquAdju;
    }
	
    public void setTacbb311101(BigDecimal tacbb311101){
       this.tacbb311101 = tacbb311101;
    }
    
    public BigDecimal getTacbb311101(){
       return this.tacbb311101;
    }
	
    public void setTacbb400000(BigDecimal tacbb400000){
       this.tacbb400000 = tacbb400000;
    }
    
    public BigDecimal getTacbb400000(){
       return this.tacbb400000;
    }
	
    public void setTacbbEquAdju(BigDecimal tacbbEquAdju){
       this.tacbbEquAdju = tacbbEquAdju;
    }
    
    public BigDecimal getTacbbEquAdju(){
       return this.tacbbEquAdju;
    }
	
    public void setTacbb300000(BigDecimal tacbb300000){
       this.tacbb300000 = tacbb300000;
    }
    
    public BigDecimal getTacbb300000(){
       return this.tacbb300000;
    }
	
    public void setTacbbLiabEquSpec(BigDecimal tacbbLiabEquSpec){
       this.tacbbLiabEquSpec = tacbbLiabEquSpec;
    }
    
    public BigDecimal getTacbbLiabEquSpec(){
       return this.tacbbLiabEquSpec;
    }
	
    public void setTacbbLiabEquAdju(BigDecimal tacbbLiabEquAdju){
       this.tacbbLiabEquAdju = tacbbLiabEquAdju;
    }
    
    public BigDecimal getTacbbLiabEquAdju(){
       return this.tacbbLiabEquAdju;
    }
	
    public void setTacbb500000(BigDecimal tacbb500000){
       this.tacbb500000 = tacbb500000;
    }
    
    public BigDecimal getTacbb500000(){
       return this.tacbb500000;
    }
	
    public void setTacbbSpecDes(String tacbbSpecDes){
       this.tacbbSpecDes = tacbbSpecDes;
    }
    
    public String getTacbbSpecDes(){
       return this.tacbbSpecDes;
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
        result = prime * result + ((createtime == null) ? 0 : createtime.hashCode());
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((infoPubDate == null) ? 0 : infoPubDate.hashCode());
        result = prime * result + ((isvalid == null) ? 0 : isvalid.hashCode());
        result = prime * result + ((sheetAttrPar == null) ? 0 : sheetAttrPar.hashCode());
        result = prime * result + ((sheetMarkPar == null) ? 0 : sheetMarkPar.hashCode());
        result = prime * result + ((tacbb100000 == null) ? 0 : tacbb100000.hashCode());
        result = prime * result + ((tacbb1021 == null) ? 0 : tacbb1021.hashCode());
        result = prime * result + ((tacbb110001 == null) ? 0 : tacbb110001.hashCode());
        result = prime * result + ((tacbb110101 == null) ? 0 : tacbb110101.hashCode());
        result = prime * result + ((tacbb110401 == null) ? 0 : tacbb110401.hashCode());
        result = prime * result + ((tacbb110501 == null) ? 0 : tacbb110501.hashCode());
        result = prime * result + ((tacbb110601 == null) ? 0 : tacbb110601.hashCode());
        result = prime * result + ((tacbb110711 == null) ? 0 : tacbb110711.hashCode());
        result = prime * result + ((tacbb110721 == null) ? 0 : tacbb110721.hashCode());
        result = prime * result + ((tacbb110901 == null) ? 0 : tacbb110901.hashCode());
        result = prime * result + ((tacbb1111 == null) ? 0 : tacbb1111.hashCode());
        result = prime * result + ((tacbb111511 == null) ? 0 : tacbb111511.hashCode());
        result = prime * result + ((tacbb111531 == null) ? 0 : tacbb111531.hashCode());
        result = prime * result + ((tacbb111601 == null) ? 0 : tacbb111601.hashCode());
        result = prime * result + ((tacbb112101 == null) ? 0 : tacbb112101.hashCode());
        result = prime * result + ((tacbb112201 == null) ? 0 : tacbb112201.hashCode());
        result = prime * result + ((tacbb112301 == null) ? 0 : tacbb112301.hashCode());
        result = prime * result + ((tacbb120111 == null) ? 0 : tacbb120111.hashCode());
        result = prime * result + ((tacbb120801 == null) ? 0 : tacbb120801.hashCode());
        result = prime * result + ((tacbb120901 == null) ? 0 : tacbb120901.hashCode());
        result = prime * result + ((tacbb1210 == null) ? 0 : tacbb1210.hashCode());
        result = prime * result + ((tacbb121001 == null) ? 0 : tacbb121001.hashCode());
        result = prime * result + ((tacbb1211 == null) ? 0 : tacbb1211.hashCode());
        result = prime * result + ((tacbb121101 == null) ? 0 : tacbb121101.hashCode());
        result = prime * result + ((tacbb1212 == null) ? 0 : tacbb1212.hashCode());
        result = prime * result + ((tacbb130101 == null) ? 0 : tacbb130101.hashCode());
        result = prime * result + ((tacbb1302 == null) ? 0 : tacbb1302.hashCode());
        result = prime * result + ((tacbb130201 == null) ? 0 : tacbb130201.hashCode());
        result = prime * result + ((tacbb1303 == null) ? 0 : tacbb1303.hashCode());
        result = prime * result + ((tacbb130301 == null) ? 0 : tacbb130301.hashCode());
        result = prime * result + ((tacbb130401 == null) ? 0 : tacbb130401.hashCode());
        result = prime * result + ((tacbb130601 == null) ? 0 : tacbb130601.hashCode());
        result = prime * result + ((tacbb130701 == null) ? 0 : tacbb130701.hashCode());
        result = prime * result + ((tacbb140101 == null) ? 0 : tacbb140101.hashCode());
        result = prime * result + ((tacbb140401 == null) ? 0 : tacbb140401.hashCode());
        result = prime * result + ((tacbb140601 == null) ? 0 : tacbb140601.hashCode());
        result = prime * result + ((tacbb140701 == null) ? 0 : tacbb140701.hashCode());
        result = prime * result + ((tacbb150001 == null) ? 0 : tacbb150001.hashCode());
        result = prime * result + ((tacbb160000 == null) ? 0 : tacbb160000.hashCode());
        result = prime * result + ((tacbb160101 == null) ? 0 : tacbb160101.hashCode());
        result = prime * result + ((tacbb200000 == null) ? 0 : tacbb200000.hashCode());
        result = prime * result + ((tacbb2003 == null) ? 0 : tacbb2003.hashCode());
        result = prime * result + ((tacbb2004 == null) ? 0 : tacbb2004.hashCode());
        result = prime * result + ((tacbb2011 == null) ? 0 : tacbb2011.hashCode());
        result = prime * result + ((tacbb210001 == null) ? 0 : tacbb210001.hashCode());
        result = prime * result + ((tacbb210101 == null) ? 0 : tacbb210101.hashCode());
        result = prime * result + ((tacbb210103 == null) ? 0 : tacbb210103.hashCode());
        result = prime * result + ((tacbb210201 == null) ? 0 : tacbb210201.hashCode());
        result = prime * result + ((tacbb210301 == null) ? 0 : tacbb210301.hashCode());
        result = prime * result + ((tacbb210401 == null) ? 0 : tacbb210401.hashCode());
        result = prime * result + ((tacbb210601 == null) ? 0 : tacbb210601.hashCode());
        result = prime * result + ((tacbb210801 == null) ? 0 : tacbb210801.hashCode());
        result = prime * result + ((tacbb210901 == null) ? 0 : tacbb210901.hashCode());
        result = prime * result + ((tacbb2111 == null) ? 0 : tacbb2111.hashCode());
        result = prime * result + ((tacbb211301 == null) ? 0 : tacbb211301.hashCode());
        result = prime * result + ((tacbb211401 == null) ? 0 : tacbb211401.hashCode());
        result = prime * result + ((tacbb211501 == null) ? 0 : tacbb211501.hashCode());
        result = prime * result + ((tacbb211901 == null) ? 0 : tacbb211901.hashCode());
        result = prime * result + ((tacbb212101 == null) ? 0 : tacbb212101.hashCode());
        result = prime * result + ((tacbb212301 == null) ? 0 : tacbb212301.hashCode());
        result = prime * result + ((tacbb212401 == null) ? 0 : tacbb212401.hashCode());
        result = prime * result + ((tacbb212501 == null) ? 0 : tacbb212501.hashCode());
        result = prime * result + ((tacbb212701 == null) ? 0 : tacbb212701.hashCode());
        result = prime * result + ((tacbb220101 == null) ? 0 : tacbb220101.hashCode());
        result = prime * result + ((tacbb220201 == null) ? 0 : tacbb220201.hashCode());
        result = prime * result + ((tacbb220301 == null) ? 0 : tacbb220301.hashCode());
        result = prime * result + ((tacbb220401 == null) ? 0 : tacbb220401.hashCode());
        result = prime * result + ((tacbb2251 == null) ? 0 : tacbb2251.hashCode());
        result = prime * result + ((tacbb2261 == null) ? 0 : tacbb2261.hashCode());
        result = prime * result + ((tacbb2311 == null) ? 0 : tacbb2311.hashCode());
        result = prime * result + ((tacbb2312 == null) ? 0 : tacbb2312.hashCode());
        result = prime * result + ((tacbb240001 == null) ? 0 : tacbb240001.hashCode());
        result = prime * result + ((tacbb240002 == null) ? 0 : tacbb240002.hashCode());
        result = prime * result + ((tacbb250001 == null) ? 0 : tacbb250001.hashCode());
        result = prime * result + ((tacbb2602 == null) ? 0 : tacbb2602.hashCode());
        result = prime * result + ((tacbb270001 == null) ? 0 : tacbb270001.hashCode());
        result = prime * result + ((tacbb300000 == null) ? 0 : tacbb300000.hashCode());
        result = prime * result + ((tacbb310101 == null) ? 0 : tacbb310101.hashCode());
        result = prime * result + ((tacbb310201 == null) ? 0 : tacbb310201.hashCode());
        result = prime * result + ((tacbb310301 == null) ? 0 : tacbb310301.hashCode());
        result = prime * result + ((tacbb310601 == null) ? 0 : tacbb310601.hashCode());
        result = prime * result + ((tacbb310701 == null) ? 0 : tacbb310701.hashCode());
        result = prime * result + ((tacbb310801 == null) ? 0 : tacbb310801.hashCode());
        result = prime * result + ((tacbb310902 == null) ? 0 : tacbb310902.hashCode());
        result = prime * result + ((tacbb310903 == null) ? 0 : tacbb310903.hashCode());
        result = prime * result + ((tacbb311101 == null) ? 0 : tacbb311101.hashCode());
        result = prime * result + ((tacbb400000 == null) ? 0 : tacbb400000.hashCode());
        result = prime * result + ((tacbb4102 == null) ? 0 : tacbb4102.hashCode());
        result = prime * result + ((tacbb500000 == null) ? 0 : tacbb500000.hashCode());
        result = prime * result + ((tacbb6101 == null) ? 0 : tacbb6101.hashCode());
        result = prime * result + ((tacbbAseAdju == null) ? 0 : tacbbAseAdju.hashCode());
        result = prime * result + ((tacbbAseSpec == null) ? 0 : tacbbAseSpec.hashCode());
        result = prime * result + ((tacbbEquAdju == null) ? 0 : tacbbEquAdju.hashCode());
        result = prime * result + ((tacbbLiabAdju == null) ? 0 : tacbbLiabAdju.hashCode());
        result = prime * result + ((tacbbLiabEquAdju == null) ? 0 : tacbbLiabEquAdju.hashCode());
        result = prime * result + ((tacbbLiabEquSpec == null) ? 0 : tacbbLiabEquSpec.hashCode());
        result = prime * result + ((tacbbLiabSpec == null) ? 0 : tacbbLiabSpec.hashCode());
        result = prime * result + ((tacbbLiquAseAdju == null) ? 0 : tacbbLiquAseAdju.hashCode());
        result = prime * result + ((tacbbLiquAseSpec == null) ? 0 : tacbbLiquAseSpec.hashCode());
        result = prime * result + ((tacbbLiquLiabAdju == null) ? 0 : tacbbLiquLiabAdju.hashCode());
        result = prime * result + ((tacbbLiquLiabSpec == null) ? 0 : tacbbLiquLiabSpec.hashCode());
        result = prime * result + ((tacbbNliquAseAdju == null) ? 0 : tacbbNliquAseAdju.hashCode());
        result = prime * result + ((tacbbNliquAseSpec == null) ? 0 : tacbbNliquAseSpec.hashCode());
        result = prime * result + ((tacbbNliquLiabAdju == null) ? 0 : tacbbNliquLiabAdju.hashCode());
        result = prime * result + ((tacbbNliquLiabSpec == null) ? 0 : tacbbNliquLiabSpec.hashCode());
        result = prime * result + ((tacbbPareComEquAdju == null) ? 0 : tacbbPareComEquAdju.hashCode());
        result = prime * result + ((tacbbPareComEquSpec == null) ? 0 : tacbbPareComEquSpec.hashCode());
        result = prime * result + ((tacbbSpecDes == null) ? 0 : tacbbSpecDes.hashCode());
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
        BondFinGenBalaTacbb other = (BondFinGenBalaTacbb) obj;
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
        if (createtime == null) {
            if (other.createtime != null)
                return false;
        } else if (!createtime.equals(other.createtime))
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
        if (tacbb100000 == null) {
            if (other.tacbb100000 != null)
                return false;
        } else if (!tacbb100000.equals(other.tacbb100000))
            return false;
        if (tacbb1021 == null) {
            if (other.tacbb1021 != null)
                return false;
        } else if (!tacbb1021.equals(other.tacbb1021))
            return false;
        if (tacbb110001 == null) {
            if (other.tacbb110001 != null)
                return false;
        } else if (!tacbb110001.equals(other.tacbb110001))
            return false;
        if (tacbb110101 == null) {
            if (other.tacbb110101 != null)
                return false;
        } else if (!tacbb110101.equals(other.tacbb110101))
            return false;
        if (tacbb110401 == null) {
            if (other.tacbb110401 != null)
                return false;
        } else if (!tacbb110401.equals(other.tacbb110401))
            return false;
        if (tacbb110501 == null) {
            if (other.tacbb110501 != null)
                return false;
        } else if (!tacbb110501.equals(other.tacbb110501))
            return false;
        if (tacbb110601 == null) {
            if (other.tacbb110601 != null)
                return false;
        } else if (!tacbb110601.equals(other.tacbb110601))
            return false;
        if (tacbb110711 == null) {
            if (other.tacbb110711 != null)
                return false;
        } else if (!tacbb110711.equals(other.tacbb110711))
            return false;
        if (tacbb110721 == null) {
            if (other.tacbb110721 != null)
                return false;
        } else if (!tacbb110721.equals(other.tacbb110721))
            return false;
        if (tacbb110901 == null) {
            if (other.tacbb110901 != null)
                return false;
        } else if (!tacbb110901.equals(other.tacbb110901))
            return false;
        if (tacbb1111 == null) {
            if (other.tacbb1111 != null)
                return false;
        } else if (!tacbb1111.equals(other.tacbb1111))
            return false;
        if (tacbb111511 == null) {
            if (other.tacbb111511 != null)
                return false;
        } else if (!tacbb111511.equals(other.tacbb111511))
            return false;
        if (tacbb111531 == null) {
            if (other.tacbb111531 != null)
                return false;
        } else if (!tacbb111531.equals(other.tacbb111531))
            return false;
        if (tacbb111601 == null) {
            if (other.tacbb111601 != null)
                return false;
        } else if (!tacbb111601.equals(other.tacbb111601))
            return false;
        if (tacbb112101 == null) {
            if (other.tacbb112101 != null)
                return false;
        } else if (!tacbb112101.equals(other.tacbb112101))
            return false;
        if (tacbb112201 == null) {
            if (other.tacbb112201 != null)
                return false;
        } else if (!tacbb112201.equals(other.tacbb112201))
            return false;
        if (tacbb112301 == null) {
            if (other.tacbb112301 != null)
                return false;
        } else if (!tacbb112301.equals(other.tacbb112301))
            return false;
        if (tacbb120111 == null) {
            if (other.tacbb120111 != null)
                return false;
        } else if (!tacbb120111.equals(other.tacbb120111))
            return false;
        if (tacbb120801 == null) {
            if (other.tacbb120801 != null)
                return false;
        } else if (!tacbb120801.equals(other.tacbb120801))
            return false;
        if (tacbb120901 == null) {
            if (other.tacbb120901 != null)
                return false;
        } else if (!tacbb120901.equals(other.tacbb120901))
            return false;
        if (tacbb1210 == null) {
            if (other.tacbb1210 != null)
                return false;
        } else if (!tacbb1210.equals(other.tacbb1210))
            return false;
        if (tacbb121001 == null) {
            if (other.tacbb121001 != null)
                return false;
        } else if (!tacbb121001.equals(other.tacbb121001))
            return false;
        if (tacbb1211 == null) {
            if (other.tacbb1211 != null)
                return false;
        } else if (!tacbb1211.equals(other.tacbb1211))
            return false;
        if (tacbb121101 == null) {
            if (other.tacbb121101 != null)
                return false;
        } else if (!tacbb121101.equals(other.tacbb121101))
            return false;
        if (tacbb1212 == null) {
            if (other.tacbb1212 != null)
                return false;
        } else if (!tacbb1212.equals(other.tacbb1212))
            return false;
        if (tacbb130101 == null) {
            if (other.tacbb130101 != null)
                return false;
        } else if (!tacbb130101.equals(other.tacbb130101))
            return false;
        if (tacbb1302 == null) {
            if (other.tacbb1302 != null)
                return false;
        } else if (!tacbb1302.equals(other.tacbb1302))
            return false;
        if (tacbb130201 == null) {
            if (other.tacbb130201 != null)
                return false;
        } else if (!tacbb130201.equals(other.tacbb130201))
            return false;
        if (tacbb1303 == null) {
            if (other.tacbb1303 != null)
                return false;
        } else if (!tacbb1303.equals(other.tacbb1303))
            return false;
        if (tacbb130301 == null) {
            if (other.tacbb130301 != null)
                return false;
        } else if (!tacbb130301.equals(other.tacbb130301))
            return false;
        if (tacbb130401 == null) {
            if (other.tacbb130401 != null)
                return false;
        } else if (!tacbb130401.equals(other.tacbb130401))
            return false;
        if (tacbb130601 == null) {
            if (other.tacbb130601 != null)
                return false;
        } else if (!tacbb130601.equals(other.tacbb130601))
            return false;
        if (tacbb130701 == null) {
            if (other.tacbb130701 != null)
                return false;
        } else if (!tacbb130701.equals(other.tacbb130701))
            return false;
        if (tacbb140101 == null) {
            if (other.tacbb140101 != null)
                return false;
        } else if (!tacbb140101.equals(other.tacbb140101))
            return false;
        if (tacbb140401 == null) {
            if (other.tacbb140401 != null)
                return false;
        } else if (!tacbb140401.equals(other.tacbb140401))
            return false;
        if (tacbb140601 == null) {
            if (other.tacbb140601 != null)
                return false;
        } else if (!tacbb140601.equals(other.tacbb140601))
            return false;
        if (tacbb140701 == null) {
            if (other.tacbb140701 != null)
                return false;
        } else if (!tacbb140701.equals(other.tacbb140701))
            return false;
        if (tacbb150001 == null) {
            if (other.tacbb150001 != null)
                return false;
        } else if (!tacbb150001.equals(other.tacbb150001))
            return false;
        if (tacbb160000 == null) {
            if (other.tacbb160000 != null)
                return false;
        } else if (!tacbb160000.equals(other.tacbb160000))
            return false;
        if (tacbb160101 == null) {
            if (other.tacbb160101 != null)
                return false;
        } else if (!tacbb160101.equals(other.tacbb160101))
            return false;
        if (tacbb200000 == null) {
            if (other.tacbb200000 != null)
                return false;
        } else if (!tacbb200000.equals(other.tacbb200000))
            return false;
        if (tacbb2003 == null) {
            if (other.tacbb2003 != null)
                return false;
        } else if (!tacbb2003.equals(other.tacbb2003))
            return false;
        if (tacbb2004 == null) {
            if (other.tacbb2004 != null)
                return false;
        } else if (!tacbb2004.equals(other.tacbb2004))
            return false;
        if (tacbb2011 == null) {
            if (other.tacbb2011 != null)
                return false;
        } else if (!tacbb2011.equals(other.tacbb2011))
            return false;
        if (tacbb210001 == null) {
            if (other.tacbb210001 != null)
                return false;
        } else if (!tacbb210001.equals(other.tacbb210001))
            return false;
        if (tacbb210101 == null) {
            if (other.tacbb210101 != null)
                return false;
        } else if (!tacbb210101.equals(other.tacbb210101))
            return false;
        if (tacbb210103 == null) {
            if (other.tacbb210103 != null)
                return false;
        } else if (!tacbb210103.equals(other.tacbb210103))
            return false;
        if (tacbb210201 == null) {
            if (other.tacbb210201 != null)
                return false;
        } else if (!tacbb210201.equals(other.tacbb210201))
            return false;
        if (tacbb210301 == null) {
            if (other.tacbb210301 != null)
                return false;
        } else if (!tacbb210301.equals(other.tacbb210301))
            return false;
        if (tacbb210401 == null) {
            if (other.tacbb210401 != null)
                return false;
        } else if (!tacbb210401.equals(other.tacbb210401))
            return false;
        if (tacbb210601 == null) {
            if (other.tacbb210601 != null)
                return false;
        } else if (!tacbb210601.equals(other.tacbb210601))
            return false;
        if (tacbb210801 == null) {
            if (other.tacbb210801 != null)
                return false;
        } else if (!tacbb210801.equals(other.tacbb210801))
            return false;
        if (tacbb210901 == null) {
            if (other.tacbb210901 != null)
                return false;
        } else if (!tacbb210901.equals(other.tacbb210901))
            return false;
        if (tacbb2111 == null) {
            if (other.tacbb2111 != null)
                return false;
        } else if (!tacbb2111.equals(other.tacbb2111))
            return false;
        if (tacbb211301 == null) {
            if (other.tacbb211301 != null)
                return false;
        } else if (!tacbb211301.equals(other.tacbb211301))
            return false;
        if (tacbb211401 == null) {
            if (other.tacbb211401 != null)
                return false;
        } else if (!tacbb211401.equals(other.tacbb211401))
            return false;
        if (tacbb211501 == null) {
            if (other.tacbb211501 != null)
                return false;
        } else if (!tacbb211501.equals(other.tacbb211501))
            return false;
        if (tacbb211901 == null) {
            if (other.tacbb211901 != null)
                return false;
        } else if (!tacbb211901.equals(other.tacbb211901))
            return false;
        if (tacbb212101 == null) {
            if (other.tacbb212101 != null)
                return false;
        } else if (!tacbb212101.equals(other.tacbb212101))
            return false;
        if (tacbb212301 == null) {
            if (other.tacbb212301 != null)
                return false;
        } else if (!tacbb212301.equals(other.tacbb212301))
            return false;
        if (tacbb212401 == null) {
            if (other.tacbb212401 != null)
                return false;
        } else if (!tacbb212401.equals(other.tacbb212401))
            return false;
        if (tacbb212501 == null) {
            if (other.tacbb212501 != null)
                return false;
        } else if (!tacbb212501.equals(other.tacbb212501))
            return false;
        if (tacbb212701 == null) {
            if (other.tacbb212701 != null)
                return false;
        } else if (!tacbb212701.equals(other.tacbb212701))
            return false;
        if (tacbb220101 == null) {
            if (other.tacbb220101 != null)
                return false;
        } else if (!tacbb220101.equals(other.tacbb220101))
            return false;
        if (tacbb220201 == null) {
            if (other.tacbb220201 != null)
                return false;
        } else if (!tacbb220201.equals(other.tacbb220201))
            return false;
        if (tacbb220301 == null) {
            if (other.tacbb220301 != null)
                return false;
        } else if (!tacbb220301.equals(other.tacbb220301))
            return false;
        if (tacbb220401 == null) {
            if (other.tacbb220401 != null)
                return false;
        } else if (!tacbb220401.equals(other.tacbb220401))
            return false;
        if (tacbb2251 == null) {
            if (other.tacbb2251 != null)
                return false;
        } else if (!tacbb2251.equals(other.tacbb2251))
            return false;
        if (tacbb2261 == null) {
            if (other.tacbb2261 != null)
                return false;
        } else if (!tacbb2261.equals(other.tacbb2261))
            return false;
        if (tacbb2311 == null) {
            if (other.tacbb2311 != null)
                return false;
        } else if (!tacbb2311.equals(other.tacbb2311))
            return false;
        if (tacbb2312 == null) {
            if (other.tacbb2312 != null)
                return false;
        } else if (!tacbb2312.equals(other.tacbb2312))
            return false;
        if (tacbb240001 == null) {
            if (other.tacbb240001 != null)
                return false;
        } else if (!tacbb240001.equals(other.tacbb240001))
            return false;
        if (tacbb240002 == null) {
            if (other.tacbb240002 != null)
                return false;
        } else if (!tacbb240002.equals(other.tacbb240002))
            return false;
        if (tacbb250001 == null) {
            if (other.tacbb250001 != null)
                return false;
        } else if (!tacbb250001.equals(other.tacbb250001))
            return false;
        if (tacbb2602 == null) {
            if (other.tacbb2602 != null)
                return false;
        } else if (!tacbb2602.equals(other.tacbb2602))
            return false;
        if (tacbb270001 == null) {
            if (other.tacbb270001 != null)
                return false;
        } else if (!tacbb270001.equals(other.tacbb270001))
            return false;
        if (tacbb300000 == null) {
            if (other.tacbb300000 != null)
                return false;
        } else if (!tacbb300000.equals(other.tacbb300000))
            return false;
        if (tacbb310101 == null) {
            if (other.tacbb310101 != null)
                return false;
        } else if (!tacbb310101.equals(other.tacbb310101))
            return false;
        if (tacbb310201 == null) {
            if (other.tacbb310201 != null)
                return false;
        } else if (!tacbb310201.equals(other.tacbb310201))
            return false;
        if (tacbb310301 == null) {
            if (other.tacbb310301 != null)
                return false;
        } else if (!tacbb310301.equals(other.tacbb310301))
            return false;
        if (tacbb310601 == null) {
            if (other.tacbb310601 != null)
                return false;
        } else if (!tacbb310601.equals(other.tacbb310601))
            return false;
        if (tacbb310701 == null) {
            if (other.tacbb310701 != null)
                return false;
        } else if (!tacbb310701.equals(other.tacbb310701))
            return false;
        if (tacbb310801 == null) {
            if (other.tacbb310801 != null)
                return false;
        } else if (!tacbb310801.equals(other.tacbb310801))
            return false;
        if (tacbb310902 == null) {
            if (other.tacbb310902 != null)
                return false;
        } else if (!tacbb310902.equals(other.tacbb310902))
            return false;
        if (tacbb310903 == null) {
            if (other.tacbb310903 != null)
                return false;
        } else if (!tacbb310903.equals(other.tacbb310903))
            return false;
        if (tacbb311101 == null) {
            if (other.tacbb311101 != null)
                return false;
        } else if (!tacbb311101.equals(other.tacbb311101))
            return false;
        if (tacbb400000 == null) {
            if (other.tacbb400000 != null)
                return false;
        } else if (!tacbb400000.equals(other.tacbb400000))
            return false;
        if (tacbb4102 == null) {
            if (other.tacbb4102 != null)
                return false;
        } else if (!tacbb4102.equals(other.tacbb4102))
            return false;
        if (tacbb500000 == null) {
            if (other.tacbb500000 != null)
                return false;
        } else if (!tacbb500000.equals(other.tacbb500000))
            return false;
        if (tacbb6101 == null) {
            if (other.tacbb6101 != null)
                return false;
        } else if (!tacbb6101.equals(other.tacbb6101))
            return false;
        if (tacbbAseAdju == null) {
            if (other.tacbbAseAdju != null)
                return false;
        } else if (!tacbbAseAdju.equals(other.tacbbAseAdju))
            return false;
        if (tacbbAseSpec == null) {
            if (other.tacbbAseSpec != null)
                return false;
        } else if (!tacbbAseSpec.equals(other.tacbbAseSpec))
            return false;
        if (tacbbEquAdju == null) {
            if (other.tacbbEquAdju != null)
                return false;
        } else if (!tacbbEquAdju.equals(other.tacbbEquAdju))
            return false;
        if (tacbbLiabAdju == null) {
            if (other.tacbbLiabAdju != null)
                return false;
        } else if (!tacbbLiabAdju.equals(other.tacbbLiabAdju))
            return false;
        if (tacbbLiabEquAdju == null) {
            if (other.tacbbLiabEquAdju != null)
                return false;
        } else if (!tacbbLiabEquAdju.equals(other.tacbbLiabEquAdju))
            return false;
        if (tacbbLiabEquSpec == null) {
            if (other.tacbbLiabEquSpec != null)
                return false;
        } else if (!tacbbLiabEquSpec.equals(other.tacbbLiabEquSpec))
            return false;
        if (tacbbLiabSpec == null) {
            if (other.tacbbLiabSpec != null)
                return false;
        } else if (!tacbbLiabSpec.equals(other.tacbbLiabSpec))
            return false;
        if (tacbbLiquAseAdju == null) {
            if (other.tacbbLiquAseAdju != null)
                return false;
        } else if (!tacbbLiquAseAdju.equals(other.tacbbLiquAseAdju))
            return false;
        if (tacbbLiquAseSpec == null) {
            if (other.tacbbLiquAseSpec != null)
                return false;
        } else if (!tacbbLiquAseSpec.equals(other.tacbbLiquAseSpec))
            return false;
        if (tacbbLiquLiabAdju == null) {
            if (other.tacbbLiquLiabAdju != null)
                return false;
        } else if (!tacbbLiquLiabAdju.equals(other.tacbbLiquLiabAdju))
            return false;
        if (tacbbLiquLiabSpec == null) {
            if (other.tacbbLiquLiabSpec != null)
                return false;
        } else if (!tacbbLiquLiabSpec.equals(other.tacbbLiquLiabSpec))
            return false;
        if (tacbbNliquAseAdju == null) {
            if (other.tacbbNliquAseAdju != null)
                return false;
        } else if (!tacbbNliquAseAdju.equals(other.tacbbNliquAseAdju))
            return false;
        if (tacbbNliquAseSpec == null) {
            if (other.tacbbNliquAseSpec != null)
                return false;
        } else if (!tacbbNliquAseSpec.equals(other.tacbbNliquAseSpec))
            return false;
        if (tacbbNliquLiabAdju == null) {
            if (other.tacbbNliquLiabAdju != null)
                return false;
        } else if (!tacbbNliquLiabAdju.equals(other.tacbbNliquLiabAdju))
            return false;
        if (tacbbNliquLiabSpec == null) {
            if (other.tacbbNliquLiabSpec != null)
                return false;
        } else if (!tacbbNliquLiabSpec.equals(other.tacbbNliquLiabSpec))
            return false;
        if (tacbbPareComEquAdju == null) {
            if (other.tacbbPareComEquAdju != null)
                return false;
        } else if (!tacbbPareComEquAdju.equals(other.tacbbPareComEquAdju))
            return false;
        if (tacbbPareComEquSpec == null) {
            if (other.tacbbPareComEquSpec != null)
                return false;
        } else if (!tacbbPareComEquSpec.equals(other.tacbbPareComEquSpec))
            return false;
        if (tacbbSpecDes == null) {
            if (other.tacbbSpecDes != null)
                return false;
        } else if (!tacbbSpecDes.equals(other.tacbbSpecDes))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BondFinGenBalaTacbb [" + (id != null ? "id=" + id + ", " : "")
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
                + (tacbb110101 != null ? "tacbb110101=" + tacbb110101 + ", " : "")
                + (tacbb112201 != null ? "tacbb112201=" + tacbb112201 + ", " : "")
                + (tacbb110401 != null ? "tacbb110401=" + tacbb110401 + ", " : "")
                + (tacbb110501 != null ? "tacbb110501=" + tacbb110501 + ", " : "")
                + (tacbb110601 != null ? "tacbb110601=" + tacbb110601 + ", " : "")
                + (tacbb110711 != null ? "tacbb110711=" + tacbb110711 + ", " : "")
                + (tacbb110721 != null ? "tacbb110721=" + tacbb110721 + ", " : "")
                + (tacbb110901 != null ? "tacbb110901=" + tacbb110901 + ", " : "")
                + (tacbb111511 != null ? "tacbb111511=" + tacbb111511 + ", " : "")
                + (tacbb111531 != null ? "tacbb111531=" + tacbb111531 + ", " : "")
                + (tacbb111601 != null ? "tacbb111601=" + tacbb111601 + ", " : "")
                + (tacbb112301 != null ? "tacbb112301=" + tacbb112301 + ", " : "")
                + (tacbb1021 != null ? "tacbb1021=" + tacbb1021 + ", " : "")
                + (tacbb1302 != null ? "tacbb1302=" + tacbb1302 + ", " : "")
                + (tacbb1210 != null ? "tacbb1210=" + tacbb1210 + ", " : "")
                + (tacbb1211 != null ? "tacbb1211=" + tacbb1211 + ", " : "")
                + (tacbb1212 != null ? "tacbb1212=" + tacbb1212 + ", " : "")
                + (tacbb1111 != null ? "tacbb1111=" + tacbb1111 + ", " : "")
                + (tacbb112101 != null ? "tacbb112101=" + tacbb112101 + ", " : "")
                + (tacbbLiquAseSpec != null ? "tacbbLiquAseSpec=" + tacbbLiquAseSpec + ", " : "")
                + (tacbbLiquAseAdju != null ? "tacbbLiquAseAdju=" + tacbbLiquAseAdju + ", " : "")
                + (tacbb110001 != null ? "tacbb110001=" + tacbb110001 + ", " : "")
                + (tacbb120801 != null ? "tacbb120801=" + tacbb120801 + ", " : "")
                + (tacbb120901 != null ? "tacbb120901=" + tacbb120901 + ", " : "")
                + (tacbb121001 != null ? "tacbb121001=" + tacbb121001 + ", " : "")
                + (tacbb120111 != null ? "tacbb120111=" + tacbb120111 + ", " : "")
                + (tacbb121101 != null ? "tacbb121101=" + tacbb121101 + ", " : "")
                + (tacbb130101 != null ? "tacbb130101=" + tacbb130101 + ", " : "")
                + (tacbb130201 != null ? "tacbb130201=" + tacbb130201 + ", " : "")
                + (tacbb130301 != null ? "tacbb130301=" + tacbb130301 + ", " : "")
                + (tacbb130401 != null ? "tacbb130401=" + tacbb130401 + ", " : "")
                + (tacbb130601 != null ? "tacbb130601=" + tacbb130601 + ", " : "")
                + (tacbb130701 != null ? "tacbb130701=" + tacbb130701 + ", " : "")
                + (tacbb140101 != null ? "tacbb140101=" + tacbb140101 + ", " : "")
                + (tacbb140601 != null ? "tacbb140601=" + tacbb140601 + ", " : "")
                + (tacbb140701 != null ? "tacbb140701=" + tacbb140701 + ", " : "")
                + (tacbb140401 != null ? "tacbb140401=" + tacbb140401 + ", " : "")
                + (tacbb150001 != null ? "tacbb150001=" + tacbb150001 + ", " : "")
                + (tacbb1303 != null ? "tacbb1303=" + tacbb1303 + ", " : "")
                + (tacbb160101 != null ? "tacbb160101=" + tacbb160101 + ", " : "")
                + (tacbbNliquAseSpec != null ? "tacbbNliquAseSpec=" + tacbbNliquAseSpec + ", " : "")
                + (tacbbNliquAseAdju != null ? "tacbbNliquAseAdju=" + tacbbNliquAseAdju + ", " : "")
                + (tacbb160000 != null ? "tacbb160000=" + tacbb160000 + ", " : "")
                + (tacbbAseSpec != null ? "tacbbAseSpec=" + tacbbAseSpec + ", " : "")
                + (tacbbAseAdju != null ? "tacbbAseAdju=" + tacbbAseAdju + ", " : "")
                + (tacbb100000 != null ? "tacbb100000=" + tacbb100000 + ", " : "")
                + (tacbb210101 != null ? "tacbb210101=" + tacbb210101 + ", " : "")
                + (tacbb210103 != null ? "tacbb210103=" + tacbb210103 + ", " : "")
                + (tacbb212301 != null ? "tacbb212301=" + tacbb212301 + ", " : "")
                + (tacbb210201 != null ? "tacbb210201=" + tacbb210201 + ", " : "")
                + (tacbb210301 != null ? "tacbb210301=" + tacbb210301 + ", " : "")
                + (tacbb211401 != null ? "tacbb211401=" + tacbb211401 + ", " : "")
                + (tacbb210401 != null ? "tacbb210401=" + tacbb210401 + ", " : "")
                + (tacbb210601 != null ? "tacbb210601=" + tacbb210601 + ", " : "")
                + (tacbb210801 != null ? "tacbb210801=" + tacbb210801 + ", " : "")
                + (tacbb210901 != null ? "tacbb210901=" + tacbb210901 + ", " : "")
                + (tacbb212401 != null ? "tacbb212401=" + tacbb212401 + ", " : "")
                + (tacbb211301 != null ? "tacbb211301=" + tacbb211301 + ", " : "")
                + (tacbb211501 != null ? "tacbb211501=" + tacbb211501 + ", " : "")
                + (tacbb212701 != null ? "tacbb212701=" + tacbb212701 + ", " : "")
                + (tacbb2004 != null ? "tacbb2004=" + tacbb2004 + ", " : "")
                + (tacbb2011 != null ? "tacbb2011=" + tacbb2011 + ", " : "")
                + (tacbb2003 != null ? "tacbb2003=" + tacbb2003 + ", " : "")
                + (tacbb2111 != null ? "tacbb2111=" + tacbb2111 + ", " : "")
                + (tacbb2251 != null ? "tacbb2251=" + tacbb2251 + ", " : "")
                + (tacbb2261 != null ? "tacbb2261=" + tacbb2261 + ", " : "")
                + (tacbb2602 != null ? "tacbb2602=" + tacbb2602 + ", " : "")
                + (tacbb2311 != null ? "tacbb2311=" + tacbb2311 + ", " : "")
                + (tacbb2312 != null ? "tacbb2312=" + tacbb2312 + ", " : "")
                + (tacbb212501 != null ? "tacbb212501=" + tacbb212501 + ", " : "")
                + (tacbb212101 != null ? "tacbb212101=" + tacbb212101 + ", " : "")
                + (tacbbLiquLiabSpec != null ? "tacbbLiquLiabSpec=" + tacbbLiquLiabSpec + ", " : "")
                + (tacbbLiquLiabAdju != null ? "tacbbLiquLiabAdju=" + tacbbLiquLiabAdju + ", " : "")
                + (tacbb210001 != null ? "tacbb210001=" + tacbb210001 + ", " : "")
                + (tacbb220101 != null ? "tacbb220101=" + tacbb220101 + ", " : "")
                + (tacbb220201 != null ? "tacbb220201=" + tacbb220201 + ", " : "")
                + (tacbb220301 != null ? "tacbb220301=" + tacbb220301 + ", " : "")
                + (tacbb220401 != null ? "tacbb220401=" + tacbb220401 + ", " : "")
                + (tacbb211901 != null ? "tacbb211901=" + tacbb211901 + ", " : "")
                + (tacbb240001 != null ? "tacbb240001=" + tacbb240001 + ", " : "")
                + (tacbb240002 != null ? "tacbb240002=" + tacbb240002 + ", " : "")
                + (tacbb250001 != null ? "tacbb250001=" + tacbb250001 + ", " : "")
                + (tacbbNliquLiabSpec != null ? "tacbbNliquLiabSpec=" + tacbbNliquLiabSpec + ", " : "")
                + (tacbbNliquLiabAdju != null ? "tacbbNliquLiabAdju=" + tacbbNliquLiabAdju + ", " : "")
                + (tacbb270001 != null ? "tacbb270001=" + tacbb270001 + ", " : "")
                + (tacbbLiabSpec != null ? "tacbbLiabSpec=" + tacbbLiabSpec + ", " : "")
                + (tacbbLiabAdju != null ? "tacbbLiabAdju=" + tacbbLiabAdju + ", " : "")
                + (tacbb200000 != null ? "tacbb200000=" + tacbb200000 + ", " : "")
                + (tacbb310101 != null ? "tacbb310101=" + tacbb310101 + ", " : "")
                + (tacbb310201 != null ? "tacbb310201=" + tacbb310201 + ", " : "")
                + (tacbb310902 != null ? "tacbb310902=" + tacbb310902 + ", " : "")
                + (tacbb310903 != null ? "tacbb310903=" + tacbb310903 + ", " : "")
                + (tacbb310301 != null ? "tacbb310301=" + tacbb310301 + ", " : "")
                + (tacbb310701 != null ? "tacbb310701=" + tacbb310701 + ", " : "")
                + (tacbb4102 != null ? "tacbb4102=" + tacbb4102 + ", " : "")
                + (tacbb310801 != null ? "tacbb310801=" + tacbb310801 + ", " : "")
                + (tacbb310601 != null ? "tacbb310601=" + tacbb310601 + ", " : "")
                + (tacbb6101 != null ? "tacbb6101=" + tacbb6101 + ", " : "")
                + (tacbbPareComEquSpec != null ? "tacbbPareComEquSpec=" + tacbbPareComEquSpec + ", " : "")
                + (tacbbPareComEquAdju != null ? "tacbbPareComEquAdju=" + tacbbPareComEquAdju + ", " : "")
                + (tacbb311101 != null ? "tacbb311101=" + tacbb311101 + ", " : "")
                + (tacbb400000 != null ? "tacbb400000=" + tacbb400000 + ", " : "")
                + (tacbbEquAdju != null ? "tacbbEquAdju=" + tacbbEquAdju + ", " : "")
                + (tacbb300000 != null ? "tacbb300000=" + tacbb300000 + ", " : "")
                + (tacbbLiabEquSpec != null ? "tacbbLiabEquSpec=" + tacbbLiabEquSpec + ", " : "")
                + (tacbbLiabEquAdju != null ? "tacbbLiabEquAdju=" + tacbbLiabEquAdju + ", " : "")
                + (tacbb500000 != null ? "tacbb500000=" + tacbb500000 + ", " : "")
                + (tacbbSpecDes != null ? "tacbbSpecDes=" + tacbbSpecDes + ", " : "")
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
        selectSql += ",TACBB_110101";
        selectSql += ",TACBB_112201";
        selectSql += ",TACBB_110401";
        selectSql += ",TACBB_110501";
        selectSql += ",TACBB_110601";
        selectSql += ",TACBB_110711";
        selectSql += ",TACBB_110721";
        selectSql += ",TACBB_110901";
        selectSql += ",TACBB_111511";
        selectSql += ",TACBB_111531";
        selectSql += ",TACBB_111601";
        selectSql += ",TACBB_112301";
        selectSql += ",TACBB_1021";
        selectSql += ",TACBB_1302";
        selectSql += ",TACBB_1210";
        selectSql += ",TACBB_1211";
        selectSql += ",TACBB_1212";
        selectSql += ",TACBB_1111";
        selectSql += ",TACBB_112101";
        selectSql += ",TACBB_LIQU_ASE_SPEC";
        selectSql += ",TACBB_LIQU_ASE_ADJU";
        selectSql += ",TACBB_110001";
        selectSql += ",TACBB_120801";
        selectSql += ",TACBB_120901";
        selectSql += ",TACBB_121001";
        selectSql += ",TACBB_120111";
        selectSql += ",TACBB_121101";
        selectSql += ",TACBB_130101";
        selectSql += ",TACBB_130201";
        selectSql += ",TACBB_130301";
        selectSql += ",TACBB_130401";
        selectSql += ",TACBB_130601";
        selectSql += ",TACBB_130701";
        selectSql += ",TACBB_140101";
        selectSql += ",TACBB_140601";
        selectSql += ",TACBB_140701";
        selectSql += ",TACBB_140401";
        selectSql += ",TACBB_150001";
        selectSql += ",TACBB_1303";
        selectSql += ",TACBB_160101";
        selectSql += ",TACBB_NLIQU_ASE_SPEC";
        selectSql += ",TACBB_NLIQU_ASE_ADJU";
        selectSql += ",TACBB_160000";
        selectSql += ",TACBB_ASE_SPEC";
        selectSql += ",TACBB_ASE_ADJU";
        selectSql += ",TACBB_100000";
        selectSql += ",TACBB_210101";
        selectSql += ",TACBB_210103";
        selectSql += ",TACBB_212301";
        selectSql += ",TACBB_210201";
        selectSql += ",TACBB_210301";
        selectSql += ",TACBB_211401";
        selectSql += ",TACBB_210401";
        selectSql += ",TACBB_210601";
        selectSql += ",TACBB_210801";
        selectSql += ",TACBB_210901";
        selectSql += ",TACBB_212401";
        selectSql += ",TACBB_211301";
        selectSql += ",TACBB_211501";
        selectSql += ",TACBB_212701";
        selectSql += ",TACBB_2004";
        selectSql += ",TACBB_2011";
        selectSql += ",TACBB_2003";
        selectSql += ",TACBB_2111";
        selectSql += ",TACBB_2251";
        selectSql += ",TACBB_2261";
        selectSql += ",TACBB_2602";
        selectSql += ",TACBB_2311";
        selectSql += ",TACBB_2312";
        selectSql += ",TACBB_212501";
        selectSql += ",TACBB_212101";
        selectSql += ",TACBB_LIQU_LIAB_SPEC";
        selectSql += ",TACBB_LIQU_LIAB_ADJU";
        selectSql += ",TACBB_210001";
        selectSql += ",TACBB_220101";
        selectSql += ",TACBB_220201";
        selectSql += ",TACBB_220301";
        selectSql += ",TACBB_220401";
        selectSql += ",TACBB_211901";
        selectSql += ",TACBB_240001";
        selectSql += ",TACBB_240002";
        selectSql += ",TACBB_250001";
        selectSql += ",TACBB_NLIQU_LIAB_SPEC";
        selectSql += ",TACBB_NLIQU_LIAB_ADJU";
        selectSql += ",TACBB_270001";
        selectSql += ",TACBB_LIAB_SPEC";
        selectSql += ",TACBB_LIAB_ADJU";
        selectSql += ",TACBB_200000";
        selectSql += ",TACBB_310101";
        selectSql += ",TACBB_310201";
        selectSql += ",TACBB_310902";
        selectSql += ",TACBB_310903";
        selectSql += ",TACBB_310301";
        selectSql += ",TACBB_310701";
        selectSql += ",TACBB_4102";
        selectSql += ",TACBB_310801";
        selectSql += ",TACBB_310601";
        selectSql += ",TACBB_6101";
        selectSql += ",TACBB_PARE_COM_EQU_SPEC";
        selectSql += ",TACBB_PARE_COM_EQU_ADJU";
        selectSql += ",TACBB_311101";
        selectSql += ",TACBB_400000";
        selectSql += ",TACBB_EQU_ADJU";
        selectSql += ",TACBB_300000";
        selectSql += ",TACBB_LIAB_EQU_SPEC";
        selectSql += ",TACBB_LIAB_EQU_ADJU";
        selectSql += ",TACBB_500000";
        selectSql += ",TACBB_SPEC_DES";
        selectSql += ",INFO_PUB_DATE";
 	   return selectSql.substring(1);
     }
 	
 	public String createEqualsSql(String whereSql){
 	   String selectSql = createSelectColumnSql();       
        String sql = "select count(1) from "
        +" (select * from (select "+selectSql+" from bond_ccxe.d_bond_fin_gen_bala_tacbb where "+whereSql
        +" order by ccxeid desc limit 1) a union select * from (select "+selectSql+" from bond_ccxe_snapshot.d_bond_fin_gen_bala_tacbb where "+whereSql+" order by ccxeid desc limit 1) a) a";
        return sql;
     }
     
     public String createEqualsSql(String id, Long bondUniCode, Long comUniCode, String endDate){
 	   String selectSql = createSelectColumnSql();
 	  String whereSql = " id='" + id + "' and bond_uni_code=" + bondUniCode 
 	         + " and com_uni_code=" + comUniCode 
 	         + " and end_date='" + endDate + "'";
                
        String sql = "select count(1) from "
        +" (select * from (select "+selectSql+" from bond_ccxe.d_bond_fin_gen_bala_tacbb where "+whereSql
        +" order by ccxeid desc limit 1) a union select * from (select "+selectSql+" from bond_ccxe_snapshot.d_bond_fin_gen_bala_tacbb where "+whereSql+" order by ccxeid desc limit 1) a) a";
        return sql;
     }
}
