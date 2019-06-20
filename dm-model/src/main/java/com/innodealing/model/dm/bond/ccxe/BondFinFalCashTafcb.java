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
@Table(name="d_bond_fin_fal_cash_tafcb")
public class BondFinFalCashTafcb implements Serializable{
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
    @Column(name="START_DATE", length=19)
	@Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
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
    @Column(name="TAFCBY_110001", length=20)
    private BigDecimal tafcby110001;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_110007", length=20)
    private BigDecimal tafcby110007;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_110008", length=20)
    private BigDecimal tafcby110008;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_110025", length=20)
    private BigDecimal tafcby110025;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_110009", length=20)
    private BigDecimal tafcby110009;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_110048", length=20)
    private BigDecimal tafcby110048;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_110049", length=20)
    private BigDecimal tafcby110049;
    
	/**
	 * 
	 */
    @Column(name="TAFCBF_110020", length=20)
    private BigDecimal tafcbf110020;
    
	/**
	 * 
	 */
    @Column(name="TAFCBF_110014", length=20)
    private BigDecimal tafcbf110014;
    
	/**
	 * 
	 */
    @Column(name="TAFCBF_110015", length=20)
    private BigDecimal tafcbf110015;
    
	/**
	 * 
	 */
    @Column(name="TAFCBF_110016", length=20)
    private BigDecimal tafcbf110016;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_110401", length=20)
    private BigDecimal tafcby110401;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_OPER_CASHIN_SPEC", length=20)
    private BigDecimal tafcbyOperCashinSpec;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_OPER_CASHIN_ADJU", length=20)
    private BigDecimal tafcbyOperCashinAdju;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_110000", length=20)
    private BigDecimal tafcby110000;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_120301", length=20)
    private BigDecimal tafcby120301;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_120401", length=20)
    private BigDecimal tafcby120401;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_120001", length=20)
    private BigDecimal tafcby120001;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_120045", length=20)
    private BigDecimal tafcby120045;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_120020", length=20)
    private BigDecimal tafcby120020;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_120002", length=20)
    private BigDecimal tafcby120002;
    
	/**
	 * 
	 */
    @Column(name="TAFCBF_120025", length=20)
    private BigDecimal tafcbf120025;
    
	/**
	 * 
	 */
    @Column(name="TAFCBF_120038", length=20)
    private BigDecimal tafcbf120038;
    
	/**
	 * 
	 */
    @Column(name="TAFCBF_120026", length=20)
    private BigDecimal tafcbf120026;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_120501", length=20)
    private BigDecimal tafcby120501;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_OPER_CASHOUT_SPEC", length=20)
    private BigDecimal tafcbyOperCashoutSpec;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_OPER_CASHOUT_ADJU", length=20)
    private BigDecimal tafcbyOperCashoutAdju;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_120000", length=20)
    private BigDecimal tafcby120000;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_OPER_NET_CASH_ADJU", length=20)
    private BigDecimal tafcbyOperNetCashAdju;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_100000", length=20)
    private BigDecimal tafcby100000;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_210101", length=20)
    private BigDecimal tafcby210101;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_210201", length=20)
    private BigDecimal tafcby210201;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_210301", length=20)
    private BigDecimal tafcby210301;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_210501", length=20)
    private BigDecimal tafcby210501;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_210401", length=20)
    private BigDecimal tafcby210401;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_INVES_CASHIN_SPEC", length=20)
    private BigDecimal tafcbyInvesCashinSpec;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_INVES_CASHIN_ADJU", length=20)
    private BigDecimal tafcbyInvesCashinAdju;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_210000", length=20)
    private BigDecimal tafcby210000;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_220101", length=20)
    private BigDecimal tafcby220101;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_220201", length=20)
    private BigDecimal tafcby220201;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_220401", length=20)
    private BigDecimal tafcby220401;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_220402", length=20)
    private BigDecimal tafcby220402;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_220301", length=20)
    private BigDecimal tafcby220301;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_INVES_CASHOUT_SPEC", length=20)
    private BigDecimal tafcbyInvesCashoutSpec;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_INVES_CASHOUT_ADJU", length=20)
    private BigDecimal tafcbyInvesCashoutAdju;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_220000", length=20)
    private BigDecimal tafcby220000;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_INVES_NET_CASH_ADJU", length=20)
    private BigDecimal tafcbyInvesNetCashAdju;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_200000", length=20)
    private BigDecimal tafcby200000;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_310301", length=20)
    private BigDecimal tafcby310301;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_310111", length=20)
    private BigDecimal tafcby310111;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_310201", length=20)
    private BigDecimal tafcby310201;
    
	/**
	 * 
	 */
    @Column(name="TAFCBF_320010", length=20)
    private BigDecimal tafcbf320010;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_310401", length=20)
    private BigDecimal tafcby310401;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_310501", length=20)
    private BigDecimal tafcby310501;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_FIN_CASHIN_SPEC", length=20)
    private BigDecimal tafcbyFinCashinSpec;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_FIN_CASHIN_ADJU", length=20)
    private BigDecimal tafcbyFinCashinAdju;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_310000", length=20)
    private BigDecimal tafcby310000;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_320101", length=20)
    private BigDecimal tafcby320101;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_320301", length=20)
    private BigDecimal tafcby320301;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_320801", length=20)
    private BigDecimal tafcby320801;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_320701", length=20)
    private BigDecimal tafcby320701;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_FIN_CASHOUT_SPEC", length=20)
    private BigDecimal tafcbyFinCashoutSpec;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_FIN_CASHOUT_ADJU", length=20)
    private BigDecimal tafcbyFinCashoutAdju;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_320000", length=20)
    private BigDecimal tafcby320000;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_FIN_NET_CASH_ADJU", length=20)
    private BigDecimal tafcbyFinNetCashAdju;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_300000", length=20)
    private BigDecimal tafcby300000;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_410101", length=20)
    private BigDecimal tafcby410101;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_413101", length=20)
    private BigDecimal tafcby413101;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_CASH_ADJU", length=20)
    private BigDecimal tafcbyCashAdju;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_410201", length=20)
    private BigDecimal tafcby410201;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_413201", length=20)
    private BigDecimal tafcby413201;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_413301", length=20)
    private BigDecimal tafcby413301;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_411001", length=20)
    private BigDecimal tafcby411001;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_411401", length=20)
    private BigDecimal tafcby411401;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_411501", length=20)
    private BigDecimal tafcby411501;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_411601", length=20)
    private BigDecimal tafcby411601;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_411701", length=20)
    private BigDecimal tafcby411701;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_411801", length=20)
    private BigDecimal tafcby411801;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_411901", length=20)
    private BigDecimal tafcby411901;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_412001", length=20)
    private BigDecimal tafcby412001;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_412101", length=20)
    private BigDecimal tafcby412101;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_413501", length=20)
    private BigDecimal tafcby413501;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_412201", length=20)
    private BigDecimal tafcby412201;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_412301", length=20)
    private BigDecimal tafcby412301;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_413601", length=20)
    private BigDecimal tafcby413601;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_413701", length=20)
    private BigDecimal tafcby413701;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_412601", length=20)
    private BigDecimal tafcby412601;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_412701", length=20)
    private BigDecimal tafcby412701;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_412801", length=20)
    private BigDecimal tafcby412801;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_413001", length=20)
    private BigDecimal tafcby413001;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_ADD_OPER_NETCASH_SPEC", length=20)
    private BigDecimal tafcbyAddOperNetcashSpec;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_ADD_OPER_NETCASH_ADJU", length=20)
    private BigDecimal tafcbyAddOperNetcashAdju;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_ADD_OPER_NETCASH", length=20)
    private BigDecimal tafcbyAddOperNetcash;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_ADD_OPER_NETCASH_CPRT", length=20)
    private BigDecimal tafcbyAddOperNetcashCprt;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_410501", length=20)
    private BigDecimal tafcby410501;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_410801", length=20)
    private BigDecimal tafcby410801;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_410802", length=20)
    private BigDecimal tafcby410802;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_510101", length=20)
    private BigDecimal tafcby510101;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_510201", length=20)
    private BigDecimal tafcby510201;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_510301", length=20)
    private BigDecimal tafcby510301;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_510401", length=20)
    private BigDecimal tafcby510401;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_ADD_CASH_SPEC", length=20)
    private BigDecimal tafcbyAddCashSpec;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_ADD_CASH_ADJU", length=20)
    private BigDecimal tafcbyAddCashAdju;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_ADD_NETCASH", length=20)
    private BigDecimal tafcbyAddNetcash;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_NETCASH_CPRT", length=20)
    private BigDecimal tafcbyNetcashCprt;
    
	/**
	 * 
	 */
    @Column(name="TAFCBY_SPEC_DES", length=16777215)
    private String tafcbySpecDes;
    
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
	
    public void setStartDate(Date startDate){
       this.startDate = startDate;
    }
    
    public Date getStartDate(){
       return this.startDate;
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
	
    public void setTafcby110001(BigDecimal tafcby110001){
       this.tafcby110001 = tafcby110001;
    }
    
    public BigDecimal getTafcby110001(){
       return this.tafcby110001;
    }
	
    public void setTafcby110007(BigDecimal tafcby110007){
       this.tafcby110007 = tafcby110007;
    }
    
    public BigDecimal getTafcby110007(){
       return this.tafcby110007;
    }
	
    public void setTafcby110008(BigDecimal tafcby110008){
       this.tafcby110008 = tafcby110008;
    }
    
    public BigDecimal getTafcby110008(){
       return this.tafcby110008;
    }
	
    public void setTafcby110025(BigDecimal tafcby110025){
       this.tafcby110025 = tafcby110025;
    }
    
    public BigDecimal getTafcby110025(){
       return this.tafcby110025;
    }
	
    public void setTafcby110009(BigDecimal tafcby110009){
       this.tafcby110009 = tafcby110009;
    }
    
    public BigDecimal getTafcby110009(){
       return this.tafcby110009;
    }
	
    public void setTafcby110048(BigDecimal tafcby110048){
       this.tafcby110048 = tafcby110048;
    }
    
    public BigDecimal getTafcby110048(){
       return this.tafcby110048;
    }
	
    public void setTafcby110049(BigDecimal tafcby110049){
       this.tafcby110049 = tafcby110049;
    }
    
    public BigDecimal getTafcby110049(){
       return this.tafcby110049;
    }
	
    public void setTafcbf110020(BigDecimal tafcbf110020){
       this.tafcbf110020 = tafcbf110020;
    }
    
    public BigDecimal getTafcbf110020(){
       return this.tafcbf110020;
    }
	
    public void setTafcbf110014(BigDecimal tafcbf110014){
       this.tafcbf110014 = tafcbf110014;
    }
    
    public BigDecimal getTafcbf110014(){
       return this.tafcbf110014;
    }
	
    public void setTafcbf110015(BigDecimal tafcbf110015){
       this.tafcbf110015 = tafcbf110015;
    }
    
    public BigDecimal getTafcbf110015(){
       return this.tafcbf110015;
    }
	
    public void setTafcbf110016(BigDecimal tafcbf110016){
       this.tafcbf110016 = tafcbf110016;
    }
    
    public BigDecimal getTafcbf110016(){
       return this.tafcbf110016;
    }
	
    public void setTafcby110401(BigDecimal tafcby110401){
       this.tafcby110401 = tafcby110401;
    }
    
    public BigDecimal getTafcby110401(){
       return this.tafcby110401;
    }
	
    public void setTafcbyOperCashinSpec(BigDecimal tafcbyOperCashinSpec){
       this.tafcbyOperCashinSpec = tafcbyOperCashinSpec;
    }
    
    public BigDecimal getTafcbyOperCashinSpec(){
       return this.tafcbyOperCashinSpec;
    }
	
    public void setTafcbyOperCashinAdju(BigDecimal tafcbyOperCashinAdju){
       this.tafcbyOperCashinAdju = tafcbyOperCashinAdju;
    }
    
    public BigDecimal getTafcbyOperCashinAdju(){
       return this.tafcbyOperCashinAdju;
    }
	
    public void setTafcby110000(BigDecimal tafcby110000){
       this.tafcby110000 = tafcby110000;
    }
    
    public BigDecimal getTafcby110000(){
       return this.tafcby110000;
    }
	
    public void setTafcby120301(BigDecimal tafcby120301){
       this.tafcby120301 = tafcby120301;
    }
    
    public BigDecimal getTafcby120301(){
       return this.tafcby120301;
    }
	
    public void setTafcby120401(BigDecimal tafcby120401){
       this.tafcby120401 = tafcby120401;
    }
    
    public BigDecimal getTafcby120401(){
       return this.tafcby120401;
    }
	
    public void setTafcby120001(BigDecimal tafcby120001){
       this.tafcby120001 = tafcby120001;
    }
    
    public BigDecimal getTafcby120001(){
       return this.tafcby120001;
    }
	
    public void setTafcby120045(BigDecimal tafcby120045){
       this.tafcby120045 = tafcby120045;
    }
    
    public BigDecimal getTafcby120045(){
       return this.tafcby120045;
    }
	
    public void setTafcby120020(BigDecimal tafcby120020){
       this.tafcby120020 = tafcby120020;
    }
    
    public BigDecimal getTafcby120020(){
       return this.tafcby120020;
    }
	
    public void setTafcby120002(BigDecimal tafcby120002){
       this.tafcby120002 = tafcby120002;
    }
    
    public BigDecimal getTafcby120002(){
       return this.tafcby120002;
    }
	
    public void setTafcbf120025(BigDecimal tafcbf120025){
       this.tafcbf120025 = tafcbf120025;
    }
    
    public BigDecimal getTafcbf120025(){
       return this.tafcbf120025;
    }
	
    public void setTafcbf120038(BigDecimal tafcbf120038){
       this.tafcbf120038 = tafcbf120038;
    }
    
    public BigDecimal getTafcbf120038(){
       return this.tafcbf120038;
    }
	
    public void setTafcbf120026(BigDecimal tafcbf120026){
       this.tafcbf120026 = tafcbf120026;
    }
    
    public BigDecimal getTafcbf120026(){
       return this.tafcbf120026;
    }
	
    public void setTafcby120501(BigDecimal tafcby120501){
       this.tafcby120501 = tafcby120501;
    }
    
    public BigDecimal getTafcby120501(){
       return this.tafcby120501;
    }
	
    public void setTafcbyOperCashoutSpec(BigDecimal tafcbyOperCashoutSpec){
       this.tafcbyOperCashoutSpec = tafcbyOperCashoutSpec;
    }
    
    public BigDecimal getTafcbyOperCashoutSpec(){
       return this.tafcbyOperCashoutSpec;
    }
	
    public void setTafcbyOperCashoutAdju(BigDecimal tafcbyOperCashoutAdju){
       this.tafcbyOperCashoutAdju = tafcbyOperCashoutAdju;
    }
    
    public BigDecimal getTafcbyOperCashoutAdju(){
       return this.tafcbyOperCashoutAdju;
    }
	
    public void setTafcby120000(BigDecimal tafcby120000){
       this.tafcby120000 = tafcby120000;
    }
    
    public BigDecimal getTafcby120000(){
       return this.tafcby120000;
    }
	
    public void setTafcbyOperNetCashAdju(BigDecimal tafcbyOperNetCashAdju){
       this.tafcbyOperNetCashAdju = tafcbyOperNetCashAdju;
    }
    
    public BigDecimal getTafcbyOperNetCashAdju(){
       return this.tafcbyOperNetCashAdju;
    }
	
    public void setTafcby100000(BigDecimal tafcby100000){
       this.tafcby100000 = tafcby100000;
    }
    
    public BigDecimal getTafcby100000(){
       return this.tafcby100000;
    }
	
    public void setTafcby210101(BigDecimal tafcby210101){
       this.tafcby210101 = tafcby210101;
    }
    
    public BigDecimal getTafcby210101(){
       return this.tafcby210101;
    }
	
    public void setTafcby210201(BigDecimal tafcby210201){
       this.tafcby210201 = tafcby210201;
    }
    
    public BigDecimal getTafcby210201(){
       return this.tafcby210201;
    }
	
    public void setTafcby210301(BigDecimal tafcby210301){
       this.tafcby210301 = tafcby210301;
    }
    
    public BigDecimal getTafcby210301(){
       return this.tafcby210301;
    }
	
    public void setTafcby210501(BigDecimal tafcby210501){
       this.tafcby210501 = tafcby210501;
    }
    
    public BigDecimal getTafcby210501(){
       return this.tafcby210501;
    }
	
    public void setTafcby210401(BigDecimal tafcby210401){
       this.tafcby210401 = tafcby210401;
    }
    
    public BigDecimal getTafcby210401(){
       return this.tafcby210401;
    }
	
    public void setTafcbyInvesCashinSpec(BigDecimal tafcbyInvesCashinSpec){
       this.tafcbyInvesCashinSpec = tafcbyInvesCashinSpec;
    }
    
    public BigDecimal getTafcbyInvesCashinSpec(){
       return this.tafcbyInvesCashinSpec;
    }
	
    public void setTafcbyInvesCashinAdju(BigDecimal tafcbyInvesCashinAdju){
       this.tafcbyInvesCashinAdju = tafcbyInvesCashinAdju;
    }
    
    public BigDecimal getTafcbyInvesCashinAdju(){
       return this.tafcbyInvesCashinAdju;
    }
	
    public void setTafcby210000(BigDecimal tafcby210000){
       this.tafcby210000 = tafcby210000;
    }
    
    public BigDecimal getTafcby210000(){
       return this.tafcby210000;
    }
	
    public void setTafcby220101(BigDecimal tafcby220101){
       this.tafcby220101 = tafcby220101;
    }
    
    public BigDecimal getTafcby220101(){
       return this.tafcby220101;
    }
	
    public void setTafcby220201(BigDecimal tafcby220201){
       this.tafcby220201 = tafcby220201;
    }
    
    public BigDecimal getTafcby220201(){
       return this.tafcby220201;
    }
	
    public void setTafcby220401(BigDecimal tafcby220401){
       this.tafcby220401 = tafcby220401;
    }
    
    public BigDecimal getTafcby220401(){
       return this.tafcby220401;
    }
	
    public void setTafcby220402(BigDecimal tafcby220402){
       this.tafcby220402 = tafcby220402;
    }
    
    public BigDecimal getTafcby220402(){
       return this.tafcby220402;
    }
	
    public void setTafcby220301(BigDecimal tafcby220301){
       this.tafcby220301 = tafcby220301;
    }
    
    public BigDecimal getTafcby220301(){
       return this.tafcby220301;
    }
	
    public void setTafcbyInvesCashoutSpec(BigDecimal tafcbyInvesCashoutSpec){
       this.tafcbyInvesCashoutSpec = tafcbyInvesCashoutSpec;
    }
    
    public BigDecimal getTafcbyInvesCashoutSpec(){
       return this.tafcbyInvesCashoutSpec;
    }
	
    public void setTafcbyInvesCashoutAdju(BigDecimal tafcbyInvesCashoutAdju){
       this.tafcbyInvesCashoutAdju = tafcbyInvesCashoutAdju;
    }
    
    public BigDecimal getTafcbyInvesCashoutAdju(){
       return this.tafcbyInvesCashoutAdju;
    }
	
    public void setTafcby220000(BigDecimal tafcby220000){
       this.tafcby220000 = tafcby220000;
    }
    
    public BigDecimal getTafcby220000(){
       return this.tafcby220000;
    }
	
    public void setTafcbyInvesNetCashAdju(BigDecimal tafcbyInvesNetCashAdju){
       this.tafcbyInvesNetCashAdju = tafcbyInvesNetCashAdju;
    }
    
    public BigDecimal getTafcbyInvesNetCashAdju(){
       return this.tafcbyInvesNetCashAdju;
    }
	
    public void setTafcby200000(BigDecimal tafcby200000){
       this.tafcby200000 = tafcby200000;
    }
    
    public BigDecimal getTafcby200000(){
       return this.tafcby200000;
    }
	
    public void setTafcby310301(BigDecimal tafcby310301){
       this.tafcby310301 = tafcby310301;
    }
    
    public BigDecimal getTafcby310301(){
       return this.tafcby310301;
    }
	
    public void setTafcby310111(BigDecimal tafcby310111){
       this.tafcby310111 = tafcby310111;
    }
    
    public BigDecimal getTafcby310111(){
       return this.tafcby310111;
    }
	
    public void setTafcby310201(BigDecimal tafcby310201){
       this.tafcby310201 = tafcby310201;
    }
    
    public BigDecimal getTafcby310201(){
       return this.tafcby310201;
    }
	
    public void setTafcbf320010(BigDecimal tafcbf320010){
       this.tafcbf320010 = tafcbf320010;
    }
    
    public BigDecimal getTafcbf320010(){
       return this.tafcbf320010;
    }
	
    public void setTafcby310401(BigDecimal tafcby310401){
       this.tafcby310401 = tafcby310401;
    }
    
    public BigDecimal getTafcby310401(){
       return this.tafcby310401;
    }
	
    public void setTafcby310501(BigDecimal tafcby310501){
       this.tafcby310501 = tafcby310501;
    }
    
    public BigDecimal getTafcby310501(){
       return this.tafcby310501;
    }
	
    public void setTafcbyFinCashinSpec(BigDecimal tafcbyFinCashinSpec){
       this.tafcbyFinCashinSpec = tafcbyFinCashinSpec;
    }
    
    public BigDecimal getTafcbyFinCashinSpec(){
       return this.tafcbyFinCashinSpec;
    }
	
    public void setTafcbyFinCashinAdju(BigDecimal tafcbyFinCashinAdju){
       this.tafcbyFinCashinAdju = tafcbyFinCashinAdju;
    }
    
    public BigDecimal getTafcbyFinCashinAdju(){
       return this.tafcbyFinCashinAdju;
    }
	
    public void setTafcby310000(BigDecimal tafcby310000){
       this.tafcby310000 = tafcby310000;
    }
    
    public BigDecimal getTafcby310000(){
       return this.tafcby310000;
    }
	
    public void setTafcby320101(BigDecimal tafcby320101){
       this.tafcby320101 = tafcby320101;
    }
    
    public BigDecimal getTafcby320101(){
       return this.tafcby320101;
    }
	
    public void setTafcby320301(BigDecimal tafcby320301){
       this.tafcby320301 = tafcby320301;
    }
    
    public BigDecimal getTafcby320301(){
       return this.tafcby320301;
    }
	
    public void setTafcby320801(BigDecimal tafcby320801){
       this.tafcby320801 = tafcby320801;
    }
    
    public BigDecimal getTafcby320801(){
       return this.tafcby320801;
    }
	
    public void setTafcby320701(BigDecimal tafcby320701){
       this.tafcby320701 = tafcby320701;
    }
    
    public BigDecimal getTafcby320701(){
       return this.tafcby320701;
    }
	
    public void setTafcbyFinCashoutSpec(BigDecimal tafcbyFinCashoutSpec){
       this.tafcbyFinCashoutSpec = tafcbyFinCashoutSpec;
    }
    
    public BigDecimal getTafcbyFinCashoutSpec(){
       return this.tafcbyFinCashoutSpec;
    }
	
    public void setTafcbyFinCashoutAdju(BigDecimal tafcbyFinCashoutAdju){
       this.tafcbyFinCashoutAdju = tafcbyFinCashoutAdju;
    }
    
    public BigDecimal getTafcbyFinCashoutAdju(){
       return this.tafcbyFinCashoutAdju;
    }
	
    public void setTafcby320000(BigDecimal tafcby320000){
       this.tafcby320000 = tafcby320000;
    }
    
    public BigDecimal getTafcby320000(){
       return this.tafcby320000;
    }
	
    public void setTafcbyFinNetCashAdju(BigDecimal tafcbyFinNetCashAdju){
       this.tafcbyFinNetCashAdju = tafcbyFinNetCashAdju;
    }
    
    public BigDecimal getTafcbyFinNetCashAdju(){
       return this.tafcbyFinNetCashAdju;
    }
	
    public void setTafcby300000(BigDecimal tafcby300000){
       this.tafcby300000 = tafcby300000;
    }
    
    public BigDecimal getTafcby300000(){
       return this.tafcby300000;
    }
	
    public void setTafcby410101(BigDecimal tafcby410101){
       this.tafcby410101 = tafcby410101;
    }
    
    public BigDecimal getTafcby410101(){
       return this.tafcby410101;
    }
	
    public void setTafcby413101(BigDecimal tafcby413101){
       this.tafcby413101 = tafcby413101;
    }
    
    public BigDecimal getTafcby413101(){
       return this.tafcby413101;
    }
	
    public void setTafcbyCashAdju(BigDecimal tafcbyCashAdju){
       this.tafcbyCashAdju = tafcbyCashAdju;
    }
    
    public BigDecimal getTafcbyCashAdju(){
       return this.tafcbyCashAdju;
    }
	
    public void setTafcby410201(BigDecimal tafcby410201){
       this.tafcby410201 = tafcby410201;
    }
    
    public BigDecimal getTafcby410201(){
       return this.tafcby410201;
    }
	
    public void setTafcby413201(BigDecimal tafcby413201){
       this.tafcby413201 = tafcby413201;
    }
    
    public BigDecimal getTafcby413201(){
       return this.tafcby413201;
    }
	
    public void setTafcby413301(BigDecimal tafcby413301){
       this.tafcby413301 = tafcby413301;
    }
    
    public BigDecimal getTafcby413301(){
       return this.tafcby413301;
    }
	
    public void setTafcby411001(BigDecimal tafcby411001){
       this.tafcby411001 = tafcby411001;
    }
    
    public BigDecimal getTafcby411001(){
       return this.tafcby411001;
    }
	
    public void setTafcby411401(BigDecimal tafcby411401){
       this.tafcby411401 = tafcby411401;
    }
    
    public BigDecimal getTafcby411401(){
       return this.tafcby411401;
    }
	
    public void setTafcby411501(BigDecimal tafcby411501){
       this.tafcby411501 = tafcby411501;
    }
    
    public BigDecimal getTafcby411501(){
       return this.tafcby411501;
    }
	
    public void setTafcby411601(BigDecimal tafcby411601){
       this.tafcby411601 = tafcby411601;
    }
    
    public BigDecimal getTafcby411601(){
       return this.tafcby411601;
    }
	
    public void setTafcby411701(BigDecimal tafcby411701){
       this.tafcby411701 = tafcby411701;
    }
    
    public BigDecimal getTafcby411701(){
       return this.tafcby411701;
    }
	
    public void setTafcby411801(BigDecimal tafcby411801){
       this.tafcby411801 = tafcby411801;
    }
    
    public BigDecimal getTafcby411801(){
       return this.tafcby411801;
    }
	
    public void setTafcby411901(BigDecimal tafcby411901){
       this.tafcby411901 = tafcby411901;
    }
    
    public BigDecimal getTafcby411901(){
       return this.tafcby411901;
    }
	
    public void setTafcby412001(BigDecimal tafcby412001){
       this.tafcby412001 = tafcby412001;
    }
    
    public BigDecimal getTafcby412001(){
       return this.tafcby412001;
    }
	
    public void setTafcby412101(BigDecimal tafcby412101){
       this.tafcby412101 = tafcby412101;
    }
    
    public BigDecimal getTafcby412101(){
       return this.tafcby412101;
    }
	
    public void setTafcby413501(BigDecimal tafcby413501){
       this.tafcby413501 = tafcby413501;
    }
    
    public BigDecimal getTafcby413501(){
       return this.tafcby413501;
    }
	
    public void setTafcby412201(BigDecimal tafcby412201){
       this.tafcby412201 = tafcby412201;
    }
    
    public BigDecimal getTafcby412201(){
       return this.tafcby412201;
    }
	
    public void setTafcby412301(BigDecimal tafcby412301){
       this.tafcby412301 = tafcby412301;
    }
    
    public BigDecimal getTafcby412301(){
       return this.tafcby412301;
    }
	
    public void setTafcby413601(BigDecimal tafcby413601){
       this.tafcby413601 = tafcby413601;
    }
    
    public BigDecimal getTafcby413601(){
       return this.tafcby413601;
    }
	
    public void setTafcby413701(BigDecimal tafcby413701){
       this.tafcby413701 = tafcby413701;
    }
    
    public BigDecimal getTafcby413701(){
       return this.tafcby413701;
    }
	
    public void setTafcby412601(BigDecimal tafcby412601){
       this.tafcby412601 = tafcby412601;
    }
    
    public BigDecimal getTafcby412601(){
       return this.tafcby412601;
    }
	
    public void setTafcby412701(BigDecimal tafcby412701){
       this.tafcby412701 = tafcby412701;
    }
    
    public BigDecimal getTafcby412701(){
       return this.tafcby412701;
    }
	
    public void setTafcby412801(BigDecimal tafcby412801){
       this.tafcby412801 = tafcby412801;
    }
    
    public BigDecimal getTafcby412801(){
       return this.tafcby412801;
    }
	
    public void setTafcby413001(BigDecimal tafcby413001){
       this.tafcby413001 = tafcby413001;
    }
    
    public BigDecimal getTafcby413001(){
       return this.tafcby413001;
    }
	
    public void setTafcbyAddOperNetcashSpec(BigDecimal tafcbyAddOperNetcashSpec){
       this.tafcbyAddOperNetcashSpec = tafcbyAddOperNetcashSpec;
    }
    
    public BigDecimal getTafcbyAddOperNetcashSpec(){
       return this.tafcbyAddOperNetcashSpec;
    }
	
    public void setTafcbyAddOperNetcashAdju(BigDecimal tafcbyAddOperNetcashAdju){
       this.tafcbyAddOperNetcashAdju = tafcbyAddOperNetcashAdju;
    }
    
    public BigDecimal getTafcbyAddOperNetcashAdju(){
       return this.tafcbyAddOperNetcashAdju;
    }
	
    public void setTafcbyAddOperNetcash(BigDecimal tafcbyAddOperNetcash){
       this.tafcbyAddOperNetcash = tafcbyAddOperNetcash;
    }
    
    public BigDecimal getTafcbyAddOperNetcash(){
       return this.tafcbyAddOperNetcash;
    }
	
    public void setTafcbyAddOperNetcashCprt(BigDecimal tafcbyAddOperNetcashCprt){
       this.tafcbyAddOperNetcashCprt = tafcbyAddOperNetcashCprt;
    }
    
    public BigDecimal getTafcbyAddOperNetcashCprt(){
       return this.tafcbyAddOperNetcashCprt;
    }
	
    public void setTafcby410501(BigDecimal tafcby410501){
       this.tafcby410501 = tafcby410501;
    }
    
    public BigDecimal getTafcby410501(){
       return this.tafcby410501;
    }
	
    public void setTafcby410801(BigDecimal tafcby410801){
       this.tafcby410801 = tafcby410801;
    }
    
    public BigDecimal getTafcby410801(){
       return this.tafcby410801;
    }
	
    public void setTafcby410802(BigDecimal tafcby410802){
       this.tafcby410802 = tafcby410802;
    }
    
    public BigDecimal getTafcby410802(){
       return this.tafcby410802;
    }
	
    public void setTafcby510101(BigDecimal tafcby510101){
       this.tafcby510101 = tafcby510101;
    }
    
    public BigDecimal getTafcby510101(){
       return this.tafcby510101;
    }
	
    public void setTafcby510201(BigDecimal tafcby510201){
       this.tafcby510201 = tafcby510201;
    }
    
    public BigDecimal getTafcby510201(){
       return this.tafcby510201;
    }
	
    public void setTafcby510301(BigDecimal tafcby510301){
       this.tafcby510301 = tafcby510301;
    }
    
    public BigDecimal getTafcby510301(){
       return this.tafcby510301;
    }
	
    public void setTafcby510401(BigDecimal tafcby510401){
       this.tafcby510401 = tafcby510401;
    }
    
    public BigDecimal getTafcby510401(){
       return this.tafcby510401;
    }
	
    public void setTafcbyAddCashSpec(BigDecimal tafcbyAddCashSpec){
       this.tafcbyAddCashSpec = tafcbyAddCashSpec;
    }
    
    public BigDecimal getTafcbyAddCashSpec(){
       return this.tafcbyAddCashSpec;
    }
	
    public void setTafcbyAddCashAdju(BigDecimal tafcbyAddCashAdju){
       this.tafcbyAddCashAdju = tafcbyAddCashAdju;
    }
    
    public BigDecimal getTafcbyAddCashAdju(){
       return this.tafcbyAddCashAdju;
    }
	
    public void setTafcbyAddNetcash(BigDecimal tafcbyAddNetcash){
       this.tafcbyAddNetcash = tafcbyAddNetcash;
    }
    
    public BigDecimal getTafcbyAddNetcash(){
       return this.tafcbyAddNetcash;
    }
	
    public void setTafcbyNetcashCprt(BigDecimal tafcbyNetcashCprt){
       this.tafcbyNetcashCprt = tafcbyNetcashCprt;
    }
    
    public BigDecimal getTafcbyNetcashCprt(){
       return this.tafcbyNetcashCprt;
    }
	
    public void setTafcbySpecDes(String tafcbySpecDes){
       this.tafcbySpecDes = tafcbySpecDes;
    }
    
    public String getTafcbySpecDes(){
       return this.tafcbySpecDes;
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
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        result = prime * result + ((tafcbf110014 == null) ? 0 : tafcbf110014.hashCode());
        result = prime * result + ((tafcbf110015 == null) ? 0 : tafcbf110015.hashCode());
        result = prime * result + ((tafcbf110016 == null) ? 0 : tafcbf110016.hashCode());
        result = prime * result + ((tafcbf110020 == null) ? 0 : tafcbf110020.hashCode());
        result = prime * result + ((tafcbf120025 == null) ? 0 : tafcbf120025.hashCode());
        result = prime * result + ((tafcbf120026 == null) ? 0 : tafcbf120026.hashCode());
        result = prime * result + ((tafcbf120038 == null) ? 0 : tafcbf120038.hashCode());
        result = prime * result + ((tafcbf320010 == null) ? 0 : tafcbf320010.hashCode());
        result = prime * result + ((tafcby100000 == null) ? 0 : tafcby100000.hashCode());
        result = prime * result + ((tafcby110000 == null) ? 0 : tafcby110000.hashCode());
        result = prime * result + ((tafcby110001 == null) ? 0 : tafcby110001.hashCode());
        result = prime * result + ((tafcby110007 == null) ? 0 : tafcby110007.hashCode());
        result = prime * result + ((tafcby110008 == null) ? 0 : tafcby110008.hashCode());
        result = prime * result + ((tafcby110009 == null) ? 0 : tafcby110009.hashCode());
        result = prime * result + ((tafcby110025 == null) ? 0 : tafcby110025.hashCode());
        result = prime * result + ((tafcby110048 == null) ? 0 : tafcby110048.hashCode());
        result = prime * result + ((tafcby110049 == null) ? 0 : tafcby110049.hashCode());
        result = prime * result + ((tafcby110401 == null) ? 0 : tafcby110401.hashCode());
        result = prime * result + ((tafcby120000 == null) ? 0 : tafcby120000.hashCode());
        result = prime * result + ((tafcby120001 == null) ? 0 : tafcby120001.hashCode());
        result = prime * result + ((tafcby120002 == null) ? 0 : tafcby120002.hashCode());
        result = prime * result + ((tafcby120020 == null) ? 0 : tafcby120020.hashCode());
        result = prime * result + ((tafcby120045 == null) ? 0 : tafcby120045.hashCode());
        result = prime * result + ((tafcby120301 == null) ? 0 : tafcby120301.hashCode());
        result = prime * result + ((tafcby120401 == null) ? 0 : tafcby120401.hashCode());
        result = prime * result + ((tafcby120501 == null) ? 0 : tafcby120501.hashCode());
        result = prime * result + ((tafcby200000 == null) ? 0 : tafcby200000.hashCode());
        result = prime * result + ((tafcby210000 == null) ? 0 : tafcby210000.hashCode());
        result = prime * result + ((tafcby210101 == null) ? 0 : tafcby210101.hashCode());
        result = prime * result + ((tafcby210201 == null) ? 0 : tafcby210201.hashCode());
        result = prime * result + ((tafcby210301 == null) ? 0 : tafcby210301.hashCode());
        result = prime * result + ((tafcby210401 == null) ? 0 : tafcby210401.hashCode());
        result = prime * result + ((tafcby210501 == null) ? 0 : tafcby210501.hashCode());
        result = prime * result + ((tafcby220000 == null) ? 0 : tafcby220000.hashCode());
        result = prime * result + ((tafcby220101 == null) ? 0 : tafcby220101.hashCode());
        result = prime * result + ((tafcby220201 == null) ? 0 : tafcby220201.hashCode());
        result = prime * result + ((tafcby220301 == null) ? 0 : tafcby220301.hashCode());
        result = prime * result + ((tafcby220401 == null) ? 0 : tafcby220401.hashCode());
        result = prime * result + ((tafcby220402 == null) ? 0 : tafcby220402.hashCode());
        result = prime * result + ((tafcby300000 == null) ? 0 : tafcby300000.hashCode());
        result = prime * result + ((tafcby310000 == null) ? 0 : tafcby310000.hashCode());
        result = prime * result + ((tafcby310111 == null) ? 0 : tafcby310111.hashCode());
        result = prime * result + ((tafcby310201 == null) ? 0 : tafcby310201.hashCode());
        result = prime * result + ((tafcby310301 == null) ? 0 : tafcby310301.hashCode());
        result = prime * result + ((tafcby310401 == null) ? 0 : tafcby310401.hashCode());
        result = prime * result + ((tafcby310501 == null) ? 0 : tafcby310501.hashCode());
        result = prime * result + ((tafcby320000 == null) ? 0 : tafcby320000.hashCode());
        result = prime * result + ((tafcby320101 == null) ? 0 : tafcby320101.hashCode());
        result = prime * result + ((tafcby320301 == null) ? 0 : tafcby320301.hashCode());
        result = prime * result + ((tafcby320701 == null) ? 0 : tafcby320701.hashCode());
        result = prime * result + ((tafcby320801 == null) ? 0 : tafcby320801.hashCode());
        result = prime * result + ((tafcby410101 == null) ? 0 : tafcby410101.hashCode());
        result = prime * result + ((tafcby410201 == null) ? 0 : tafcby410201.hashCode());
        result = prime * result + ((tafcby410501 == null) ? 0 : tafcby410501.hashCode());
        result = prime * result + ((tafcby410801 == null) ? 0 : tafcby410801.hashCode());
        result = prime * result + ((tafcby410802 == null) ? 0 : tafcby410802.hashCode());
        result = prime * result + ((tafcby411001 == null) ? 0 : tafcby411001.hashCode());
        result = prime * result + ((tafcby411401 == null) ? 0 : tafcby411401.hashCode());
        result = prime * result + ((tafcby411501 == null) ? 0 : tafcby411501.hashCode());
        result = prime * result + ((tafcby411601 == null) ? 0 : tafcby411601.hashCode());
        result = prime * result + ((tafcby411701 == null) ? 0 : tafcby411701.hashCode());
        result = prime * result + ((tafcby411801 == null) ? 0 : tafcby411801.hashCode());
        result = prime * result + ((tafcby411901 == null) ? 0 : tafcby411901.hashCode());
        result = prime * result + ((tafcby412001 == null) ? 0 : tafcby412001.hashCode());
        result = prime * result + ((tafcby412101 == null) ? 0 : tafcby412101.hashCode());
        result = prime * result + ((tafcby412201 == null) ? 0 : tafcby412201.hashCode());
        result = prime * result + ((tafcby412301 == null) ? 0 : tafcby412301.hashCode());
        result = prime * result + ((tafcby412601 == null) ? 0 : tafcby412601.hashCode());
        result = prime * result + ((tafcby412701 == null) ? 0 : tafcby412701.hashCode());
        result = prime * result + ((tafcby412801 == null) ? 0 : tafcby412801.hashCode());
        result = prime * result + ((tafcby413001 == null) ? 0 : tafcby413001.hashCode());
        result = prime * result + ((tafcby413101 == null) ? 0 : tafcby413101.hashCode());
        result = prime * result + ((tafcby413201 == null) ? 0 : tafcby413201.hashCode());
        result = prime * result + ((tafcby413301 == null) ? 0 : tafcby413301.hashCode());
        result = prime * result + ((tafcby413501 == null) ? 0 : tafcby413501.hashCode());
        result = prime * result + ((tafcby413601 == null) ? 0 : tafcby413601.hashCode());
        result = prime * result + ((tafcby413701 == null) ? 0 : tafcby413701.hashCode());
        result = prime * result + ((tafcby510101 == null) ? 0 : tafcby510101.hashCode());
        result = prime * result + ((tafcby510201 == null) ? 0 : tafcby510201.hashCode());
        result = prime * result + ((tafcby510301 == null) ? 0 : tafcby510301.hashCode());
        result = prime * result + ((tafcby510401 == null) ? 0 : tafcby510401.hashCode());
        result = prime * result + ((tafcbyAddCashAdju == null) ? 0 : tafcbyAddCashAdju.hashCode());
        result = prime * result + ((tafcbyAddCashSpec == null) ? 0 : tafcbyAddCashSpec.hashCode());
        result = prime * result + ((tafcbyAddNetcash == null) ? 0 : tafcbyAddNetcash.hashCode());
        result = prime * result + ((tafcbyAddOperNetcash == null) ? 0 : tafcbyAddOperNetcash.hashCode());
        result = prime * result + ((tafcbyAddOperNetcashAdju == null) ? 0 : tafcbyAddOperNetcashAdju.hashCode());
        result = prime * result + ((tafcbyAddOperNetcashCprt == null) ? 0 : tafcbyAddOperNetcashCprt.hashCode());
        result = prime * result + ((tafcbyAddOperNetcashSpec == null) ? 0 : tafcbyAddOperNetcashSpec.hashCode());
        result = prime * result + ((tafcbyCashAdju == null) ? 0 : tafcbyCashAdju.hashCode());
        result = prime * result + ((tafcbyFinCashinAdju == null) ? 0 : tafcbyFinCashinAdju.hashCode());
        result = prime * result + ((tafcbyFinCashinSpec == null) ? 0 : tafcbyFinCashinSpec.hashCode());
        result = prime * result + ((tafcbyFinCashoutAdju == null) ? 0 : tafcbyFinCashoutAdju.hashCode());
        result = prime * result + ((tafcbyFinCashoutSpec == null) ? 0 : tafcbyFinCashoutSpec.hashCode());
        result = prime * result + ((tafcbyFinNetCashAdju == null) ? 0 : tafcbyFinNetCashAdju.hashCode());
        result = prime * result + ((tafcbyInvesCashinAdju == null) ? 0 : tafcbyInvesCashinAdju.hashCode());
        result = prime * result + ((tafcbyInvesCashinSpec == null) ? 0 : tafcbyInvesCashinSpec.hashCode());
        result = prime * result + ((tafcbyInvesCashoutAdju == null) ? 0 : tafcbyInvesCashoutAdju.hashCode());
        result = prime * result + ((tafcbyInvesCashoutSpec == null) ? 0 : tafcbyInvesCashoutSpec.hashCode());
        result = prime * result + ((tafcbyInvesNetCashAdju == null) ? 0 : tafcbyInvesNetCashAdju.hashCode());
        result = prime * result + ((tafcbyNetcashCprt == null) ? 0 : tafcbyNetcashCprt.hashCode());
        result = prime * result + ((tafcbyOperCashinAdju == null) ? 0 : tafcbyOperCashinAdju.hashCode());
        result = prime * result + ((tafcbyOperCashinSpec == null) ? 0 : tafcbyOperCashinSpec.hashCode());
        result = prime * result + ((tafcbyOperCashoutAdju == null) ? 0 : tafcbyOperCashoutAdju.hashCode());
        result = prime * result + ((tafcbyOperCashoutSpec == null) ? 0 : tafcbyOperCashoutSpec.hashCode());
        result = prime * result + ((tafcbyOperNetCashAdju == null) ? 0 : tafcbyOperNetCashAdju.hashCode());
        result = prime * result + ((tafcbySpecDes == null) ? 0 : tafcbySpecDes.hashCode());
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
        BondFinFalCashTafcb other = (BondFinFalCashTafcb) obj;
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
        if (startDate == null) {
            if (other.startDate != null)
                return false;
        } else if (!startDate.equals(other.startDate))
            return false;
        if (tafcbf110014 == null) {
            if (other.tafcbf110014 != null)
                return false;
        } else if (!tafcbf110014.equals(other.tafcbf110014))
            return false;
        if (tafcbf110015 == null) {
            if (other.tafcbf110015 != null)
                return false;
        } else if (!tafcbf110015.equals(other.tafcbf110015))
            return false;
        if (tafcbf110016 == null) {
            if (other.tafcbf110016 != null)
                return false;
        } else if (!tafcbf110016.equals(other.tafcbf110016))
            return false;
        if (tafcbf110020 == null) {
            if (other.tafcbf110020 != null)
                return false;
        } else if (!tafcbf110020.equals(other.tafcbf110020))
            return false;
        if (tafcbf120025 == null) {
            if (other.tafcbf120025 != null)
                return false;
        } else if (!tafcbf120025.equals(other.tafcbf120025))
            return false;
        if (tafcbf120026 == null) {
            if (other.tafcbf120026 != null)
                return false;
        } else if (!tafcbf120026.equals(other.tafcbf120026))
            return false;
        if (tafcbf120038 == null) {
            if (other.tafcbf120038 != null)
                return false;
        } else if (!tafcbf120038.equals(other.tafcbf120038))
            return false;
        if (tafcbf320010 == null) {
            if (other.tafcbf320010 != null)
                return false;
        } else if (!tafcbf320010.equals(other.tafcbf320010))
            return false;
        if (tafcby100000 == null) {
            if (other.tafcby100000 != null)
                return false;
        } else if (!tafcby100000.equals(other.tafcby100000))
            return false;
        if (tafcby110000 == null) {
            if (other.tafcby110000 != null)
                return false;
        } else if (!tafcby110000.equals(other.tafcby110000))
            return false;
        if (tafcby110001 == null) {
            if (other.tafcby110001 != null)
                return false;
        } else if (!tafcby110001.equals(other.tafcby110001))
            return false;
        if (tafcby110007 == null) {
            if (other.tafcby110007 != null)
                return false;
        } else if (!tafcby110007.equals(other.tafcby110007))
            return false;
        if (tafcby110008 == null) {
            if (other.tafcby110008 != null)
                return false;
        } else if (!tafcby110008.equals(other.tafcby110008))
            return false;
        if (tafcby110009 == null) {
            if (other.tafcby110009 != null)
                return false;
        } else if (!tafcby110009.equals(other.tafcby110009))
            return false;
        if (tafcby110025 == null) {
            if (other.tafcby110025 != null)
                return false;
        } else if (!tafcby110025.equals(other.tafcby110025))
            return false;
        if (tafcby110048 == null) {
            if (other.tafcby110048 != null)
                return false;
        } else if (!tafcby110048.equals(other.tafcby110048))
            return false;
        if (tafcby110049 == null) {
            if (other.tafcby110049 != null)
                return false;
        } else if (!tafcby110049.equals(other.tafcby110049))
            return false;
        if (tafcby110401 == null) {
            if (other.tafcby110401 != null)
                return false;
        } else if (!tafcby110401.equals(other.tafcby110401))
            return false;
        if (tafcby120000 == null) {
            if (other.tafcby120000 != null)
                return false;
        } else if (!tafcby120000.equals(other.tafcby120000))
            return false;
        if (tafcby120001 == null) {
            if (other.tafcby120001 != null)
                return false;
        } else if (!tafcby120001.equals(other.tafcby120001))
            return false;
        if (tafcby120002 == null) {
            if (other.tafcby120002 != null)
                return false;
        } else if (!tafcby120002.equals(other.tafcby120002))
            return false;
        if (tafcby120020 == null) {
            if (other.tafcby120020 != null)
                return false;
        } else if (!tafcby120020.equals(other.tafcby120020))
            return false;
        if (tafcby120045 == null) {
            if (other.tafcby120045 != null)
                return false;
        } else if (!tafcby120045.equals(other.tafcby120045))
            return false;
        if (tafcby120301 == null) {
            if (other.tafcby120301 != null)
                return false;
        } else if (!tafcby120301.equals(other.tafcby120301))
            return false;
        if (tafcby120401 == null) {
            if (other.tafcby120401 != null)
                return false;
        } else if (!tafcby120401.equals(other.tafcby120401))
            return false;
        if (tafcby120501 == null) {
            if (other.tafcby120501 != null)
                return false;
        } else if (!tafcby120501.equals(other.tafcby120501))
            return false;
        if (tafcby200000 == null) {
            if (other.tafcby200000 != null)
                return false;
        } else if (!tafcby200000.equals(other.tafcby200000))
            return false;
        if (tafcby210000 == null) {
            if (other.tafcby210000 != null)
                return false;
        } else if (!tafcby210000.equals(other.tafcby210000))
            return false;
        if (tafcby210101 == null) {
            if (other.tafcby210101 != null)
                return false;
        } else if (!tafcby210101.equals(other.tafcby210101))
            return false;
        if (tafcby210201 == null) {
            if (other.tafcby210201 != null)
                return false;
        } else if (!tafcby210201.equals(other.tafcby210201))
            return false;
        if (tafcby210301 == null) {
            if (other.tafcby210301 != null)
                return false;
        } else if (!tafcby210301.equals(other.tafcby210301))
            return false;
        if (tafcby210401 == null) {
            if (other.tafcby210401 != null)
                return false;
        } else if (!tafcby210401.equals(other.tafcby210401))
            return false;
        if (tafcby210501 == null) {
            if (other.tafcby210501 != null)
                return false;
        } else if (!tafcby210501.equals(other.tafcby210501))
            return false;
        if (tafcby220000 == null) {
            if (other.tafcby220000 != null)
                return false;
        } else if (!tafcby220000.equals(other.tafcby220000))
            return false;
        if (tafcby220101 == null) {
            if (other.tafcby220101 != null)
                return false;
        } else if (!tafcby220101.equals(other.tafcby220101))
            return false;
        if (tafcby220201 == null) {
            if (other.tafcby220201 != null)
                return false;
        } else if (!tafcby220201.equals(other.tafcby220201))
            return false;
        if (tafcby220301 == null) {
            if (other.tafcby220301 != null)
                return false;
        } else if (!tafcby220301.equals(other.tafcby220301))
            return false;
        if (tafcby220401 == null) {
            if (other.tafcby220401 != null)
                return false;
        } else if (!tafcby220401.equals(other.tafcby220401))
            return false;
        if (tafcby220402 == null) {
            if (other.tafcby220402 != null)
                return false;
        } else if (!tafcby220402.equals(other.tafcby220402))
            return false;
        if (tafcby300000 == null) {
            if (other.tafcby300000 != null)
                return false;
        } else if (!tafcby300000.equals(other.tafcby300000))
            return false;
        if (tafcby310000 == null) {
            if (other.tafcby310000 != null)
                return false;
        } else if (!tafcby310000.equals(other.tafcby310000))
            return false;
        if (tafcby310111 == null) {
            if (other.tafcby310111 != null)
                return false;
        } else if (!tafcby310111.equals(other.tafcby310111))
            return false;
        if (tafcby310201 == null) {
            if (other.tafcby310201 != null)
                return false;
        } else if (!tafcby310201.equals(other.tafcby310201))
            return false;
        if (tafcby310301 == null) {
            if (other.tafcby310301 != null)
                return false;
        } else if (!tafcby310301.equals(other.tafcby310301))
            return false;
        if (tafcby310401 == null) {
            if (other.tafcby310401 != null)
                return false;
        } else if (!tafcby310401.equals(other.tafcby310401))
            return false;
        if (tafcby310501 == null) {
            if (other.tafcby310501 != null)
                return false;
        } else if (!tafcby310501.equals(other.tafcby310501))
            return false;
        if (tafcby320000 == null) {
            if (other.tafcby320000 != null)
                return false;
        } else if (!tafcby320000.equals(other.tafcby320000))
            return false;
        if (tafcby320101 == null) {
            if (other.tafcby320101 != null)
                return false;
        } else if (!tafcby320101.equals(other.tafcby320101))
            return false;
        if (tafcby320301 == null) {
            if (other.tafcby320301 != null)
                return false;
        } else if (!tafcby320301.equals(other.tafcby320301))
            return false;
        if (tafcby320701 == null) {
            if (other.tafcby320701 != null)
                return false;
        } else if (!tafcby320701.equals(other.tafcby320701))
            return false;
        if (tafcby320801 == null) {
            if (other.tafcby320801 != null)
                return false;
        } else if (!tafcby320801.equals(other.tafcby320801))
            return false;
        if (tafcby410101 == null) {
            if (other.tafcby410101 != null)
                return false;
        } else if (!tafcby410101.equals(other.tafcby410101))
            return false;
        if (tafcby410201 == null) {
            if (other.tafcby410201 != null)
                return false;
        } else if (!tafcby410201.equals(other.tafcby410201))
            return false;
        if (tafcby410501 == null) {
            if (other.tafcby410501 != null)
                return false;
        } else if (!tafcby410501.equals(other.tafcby410501))
            return false;
        if (tafcby410801 == null) {
            if (other.tafcby410801 != null)
                return false;
        } else if (!tafcby410801.equals(other.tafcby410801))
            return false;
        if (tafcby410802 == null) {
            if (other.tafcby410802 != null)
                return false;
        } else if (!tafcby410802.equals(other.tafcby410802))
            return false;
        if (tafcby411001 == null) {
            if (other.tafcby411001 != null)
                return false;
        } else if (!tafcby411001.equals(other.tafcby411001))
            return false;
        if (tafcby411401 == null) {
            if (other.tafcby411401 != null)
                return false;
        } else if (!tafcby411401.equals(other.tafcby411401))
            return false;
        if (tafcby411501 == null) {
            if (other.tafcby411501 != null)
                return false;
        } else if (!tafcby411501.equals(other.tafcby411501))
            return false;
        if (tafcby411601 == null) {
            if (other.tafcby411601 != null)
                return false;
        } else if (!tafcby411601.equals(other.tafcby411601))
            return false;
        if (tafcby411701 == null) {
            if (other.tafcby411701 != null)
                return false;
        } else if (!tafcby411701.equals(other.tafcby411701))
            return false;
        if (tafcby411801 == null) {
            if (other.tafcby411801 != null)
                return false;
        } else if (!tafcby411801.equals(other.tafcby411801))
            return false;
        if (tafcby411901 == null) {
            if (other.tafcby411901 != null)
                return false;
        } else if (!tafcby411901.equals(other.tafcby411901))
            return false;
        if (tafcby412001 == null) {
            if (other.tafcby412001 != null)
                return false;
        } else if (!tafcby412001.equals(other.tafcby412001))
            return false;
        if (tafcby412101 == null) {
            if (other.tafcby412101 != null)
                return false;
        } else if (!tafcby412101.equals(other.tafcby412101))
            return false;
        if (tafcby412201 == null) {
            if (other.tafcby412201 != null)
                return false;
        } else if (!tafcby412201.equals(other.tafcby412201))
            return false;
        if (tafcby412301 == null) {
            if (other.tafcby412301 != null)
                return false;
        } else if (!tafcby412301.equals(other.tafcby412301))
            return false;
        if (tafcby412601 == null) {
            if (other.tafcby412601 != null)
                return false;
        } else if (!tafcby412601.equals(other.tafcby412601))
            return false;
        if (tafcby412701 == null) {
            if (other.tafcby412701 != null)
                return false;
        } else if (!tafcby412701.equals(other.tafcby412701))
            return false;
        if (tafcby412801 == null) {
            if (other.tafcby412801 != null)
                return false;
        } else if (!tafcby412801.equals(other.tafcby412801))
            return false;
        if (tafcby413001 == null) {
            if (other.tafcby413001 != null)
                return false;
        } else if (!tafcby413001.equals(other.tafcby413001))
            return false;
        if (tafcby413101 == null) {
            if (other.tafcby413101 != null)
                return false;
        } else if (!tafcby413101.equals(other.tafcby413101))
            return false;
        if (tafcby413201 == null) {
            if (other.tafcby413201 != null)
                return false;
        } else if (!tafcby413201.equals(other.tafcby413201))
            return false;
        if (tafcby413301 == null) {
            if (other.tafcby413301 != null)
                return false;
        } else if (!tafcby413301.equals(other.tafcby413301))
            return false;
        if (tafcby413501 == null) {
            if (other.tafcby413501 != null)
                return false;
        } else if (!tafcby413501.equals(other.tafcby413501))
            return false;
        if (tafcby413601 == null) {
            if (other.tafcby413601 != null)
                return false;
        } else if (!tafcby413601.equals(other.tafcby413601))
            return false;
        if (tafcby413701 == null) {
            if (other.tafcby413701 != null)
                return false;
        } else if (!tafcby413701.equals(other.tafcby413701))
            return false;
        if (tafcby510101 == null) {
            if (other.tafcby510101 != null)
                return false;
        } else if (!tafcby510101.equals(other.tafcby510101))
            return false;
        if (tafcby510201 == null) {
            if (other.tafcby510201 != null)
                return false;
        } else if (!tafcby510201.equals(other.tafcby510201))
            return false;
        if (tafcby510301 == null) {
            if (other.tafcby510301 != null)
                return false;
        } else if (!tafcby510301.equals(other.tafcby510301))
            return false;
        if (tafcby510401 == null) {
            if (other.tafcby510401 != null)
                return false;
        } else if (!tafcby510401.equals(other.tafcby510401))
            return false;
        if (tafcbyAddCashAdju == null) {
            if (other.tafcbyAddCashAdju != null)
                return false;
        } else if (!tafcbyAddCashAdju.equals(other.tafcbyAddCashAdju))
            return false;
        if (tafcbyAddCashSpec == null) {
            if (other.tafcbyAddCashSpec != null)
                return false;
        } else if (!tafcbyAddCashSpec.equals(other.tafcbyAddCashSpec))
            return false;
        if (tafcbyAddNetcash == null) {
            if (other.tafcbyAddNetcash != null)
                return false;
        } else if (!tafcbyAddNetcash.equals(other.tafcbyAddNetcash))
            return false;
        if (tafcbyAddOperNetcash == null) {
            if (other.tafcbyAddOperNetcash != null)
                return false;
        } else if (!tafcbyAddOperNetcash.equals(other.tafcbyAddOperNetcash))
            return false;
        if (tafcbyAddOperNetcashAdju == null) {
            if (other.tafcbyAddOperNetcashAdju != null)
                return false;
        } else if (!tafcbyAddOperNetcashAdju.equals(other.tafcbyAddOperNetcashAdju))
            return false;
        if (tafcbyAddOperNetcashCprt == null) {
            if (other.tafcbyAddOperNetcashCprt != null)
                return false;
        } else if (!tafcbyAddOperNetcashCprt.equals(other.tafcbyAddOperNetcashCprt))
            return false;
        if (tafcbyAddOperNetcashSpec == null) {
            if (other.tafcbyAddOperNetcashSpec != null)
                return false;
        } else if (!tafcbyAddOperNetcashSpec.equals(other.tafcbyAddOperNetcashSpec))
            return false;
        if (tafcbyCashAdju == null) {
            if (other.tafcbyCashAdju != null)
                return false;
        } else if (!tafcbyCashAdju.equals(other.tafcbyCashAdju))
            return false;
        if (tafcbyFinCashinAdju == null) {
            if (other.tafcbyFinCashinAdju != null)
                return false;
        } else if (!tafcbyFinCashinAdju.equals(other.tafcbyFinCashinAdju))
            return false;
        if (tafcbyFinCashinSpec == null) {
            if (other.tafcbyFinCashinSpec != null)
                return false;
        } else if (!tafcbyFinCashinSpec.equals(other.tafcbyFinCashinSpec))
            return false;
        if (tafcbyFinCashoutAdju == null) {
            if (other.tafcbyFinCashoutAdju != null)
                return false;
        } else if (!tafcbyFinCashoutAdju.equals(other.tafcbyFinCashoutAdju))
            return false;
        if (tafcbyFinCashoutSpec == null) {
            if (other.tafcbyFinCashoutSpec != null)
                return false;
        } else if (!tafcbyFinCashoutSpec.equals(other.tafcbyFinCashoutSpec))
            return false;
        if (tafcbyFinNetCashAdju == null) {
            if (other.tafcbyFinNetCashAdju != null)
                return false;
        } else if (!tafcbyFinNetCashAdju.equals(other.tafcbyFinNetCashAdju))
            return false;
        if (tafcbyInvesCashinAdju == null) {
            if (other.tafcbyInvesCashinAdju != null)
                return false;
        } else if (!tafcbyInvesCashinAdju.equals(other.tafcbyInvesCashinAdju))
            return false;
        if (tafcbyInvesCashinSpec == null) {
            if (other.tafcbyInvesCashinSpec != null)
                return false;
        } else if (!tafcbyInvesCashinSpec.equals(other.tafcbyInvesCashinSpec))
            return false;
        if (tafcbyInvesCashoutAdju == null) {
            if (other.tafcbyInvesCashoutAdju != null)
                return false;
        } else if (!tafcbyInvesCashoutAdju.equals(other.tafcbyInvesCashoutAdju))
            return false;
        if (tafcbyInvesCashoutSpec == null) {
            if (other.tafcbyInvesCashoutSpec != null)
                return false;
        } else if (!tafcbyInvesCashoutSpec.equals(other.tafcbyInvesCashoutSpec))
            return false;
        if (tafcbyInvesNetCashAdju == null) {
            if (other.tafcbyInvesNetCashAdju != null)
                return false;
        } else if (!tafcbyInvesNetCashAdju.equals(other.tafcbyInvesNetCashAdju))
            return false;
        if (tafcbyNetcashCprt == null) {
            if (other.tafcbyNetcashCprt != null)
                return false;
        } else if (!tafcbyNetcashCprt.equals(other.tafcbyNetcashCprt))
            return false;
        if (tafcbyOperCashinAdju == null) {
            if (other.tafcbyOperCashinAdju != null)
                return false;
        } else if (!tafcbyOperCashinAdju.equals(other.tafcbyOperCashinAdju))
            return false;
        if (tafcbyOperCashinSpec == null) {
            if (other.tafcbyOperCashinSpec != null)
                return false;
        } else if (!tafcbyOperCashinSpec.equals(other.tafcbyOperCashinSpec))
            return false;
        if (tafcbyOperCashoutAdju == null) {
            if (other.tafcbyOperCashoutAdju != null)
                return false;
        } else if (!tafcbyOperCashoutAdju.equals(other.tafcbyOperCashoutAdju))
            return false;
        if (tafcbyOperCashoutSpec == null) {
            if (other.tafcbyOperCashoutSpec != null)
                return false;
        } else if (!tafcbyOperCashoutSpec.equals(other.tafcbyOperCashoutSpec))
            return false;
        if (tafcbyOperNetCashAdju == null) {
            if (other.tafcbyOperNetCashAdju != null)
                return false;
        } else if (!tafcbyOperNetCashAdju.equals(other.tafcbyOperNetCashAdju))
            return false;
        if (tafcbySpecDes == null) {
            if (other.tafcbySpecDes != null)
                return false;
        } else if (!tafcbySpecDes.equals(other.tafcbySpecDes))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BondFinFalCashTafcb [" + (id != null ? "id=" + id + ", " : "")
                + (isvalid != null ? "isvalid=" + isvalid + ", " : "")
                + (createtime != null ? "createtime=" + createtime + ", " : "")
                + (updatetime != null ? "updatetime=" + updatetime + ", " : "")
                + (ccxeid != null ? "ccxeid=" + ccxeid + ", " : "")
                + (bondUniCode != null ? "bondUniCode=" + bondUniCode + ", " : "")
                + (bondCode != null ? "bondCode=" + bondCode + ", " : "")
                + (bondShortName != null ? "bondShortName=" + bondShortName + ", " : "")
                + (startDate != null ? "startDate=" + startDate + ", " : "")
                + (endDate != null ? "endDate=" + endDate + ", " : "")
                + (sheetMarkPar != null ? "sheetMarkPar=" + sheetMarkPar + ", " : "")
                + (sheetAttrPar != null ? "sheetAttrPar=" + sheetAttrPar + ", " : "")
                + (comUniCode != null ? "comUniCode=" + comUniCode + ", " : "")
                + (tafcby110001 != null ? "tafcby110001=" + tafcby110001 + ", " : "")
                + (tafcby110007 != null ? "tafcby110007=" + tafcby110007 + ", " : "")
                + (tafcby110008 != null ? "tafcby110008=" + tafcby110008 + ", " : "")
                + (tafcby110025 != null ? "tafcby110025=" + tafcby110025 + ", " : "")
                + (tafcby110009 != null ? "tafcby110009=" + tafcby110009 + ", " : "")
                + (tafcby110048 != null ? "tafcby110048=" + tafcby110048 + ", " : "")
                + (tafcby110049 != null ? "tafcby110049=" + tafcby110049 + ", " : "")
                + (tafcbf110020 != null ? "tafcbf110020=" + tafcbf110020 + ", " : "")
                + (tafcbf110014 != null ? "tafcbf110014=" + tafcbf110014 + ", " : "")
                + (tafcbf110015 != null ? "tafcbf110015=" + tafcbf110015 + ", " : "")
                + (tafcbf110016 != null ? "tafcbf110016=" + tafcbf110016 + ", " : "")
                + (tafcby110401 != null ? "tafcby110401=" + tafcby110401 + ", " : "")
                + (tafcbyOperCashinSpec != null ? "tafcbyOperCashinSpec=" + tafcbyOperCashinSpec + ", " : "")
                + (tafcbyOperCashinAdju != null ? "tafcbyOperCashinAdju=" + tafcbyOperCashinAdju + ", " : "")
                + (tafcby110000 != null ? "tafcby110000=" + tafcby110000 + ", " : "")
                + (tafcby120301 != null ? "tafcby120301=" + tafcby120301 + ", " : "")
                + (tafcby120401 != null ? "tafcby120401=" + tafcby120401 + ", " : "")
                + (tafcby120001 != null ? "tafcby120001=" + tafcby120001 + ", " : "")
                + (tafcby120045 != null ? "tafcby120045=" + tafcby120045 + ", " : "")
                + (tafcby120020 != null ? "tafcby120020=" + tafcby120020 + ", " : "")
                + (tafcby120002 != null ? "tafcby120002=" + tafcby120002 + ", " : "")
                + (tafcbf120025 != null ? "tafcbf120025=" + tafcbf120025 + ", " : "")
                + (tafcbf120038 != null ? "tafcbf120038=" + tafcbf120038 + ", " : "")
                + (tafcbf120026 != null ? "tafcbf120026=" + tafcbf120026 + ", " : "")
                + (tafcby120501 != null ? "tafcby120501=" + tafcby120501 + ", " : "")
                + (tafcbyOperCashoutSpec != null ? "tafcbyOperCashoutSpec=" + tafcbyOperCashoutSpec + ", " : "")
                + (tafcbyOperCashoutAdju != null ? "tafcbyOperCashoutAdju=" + tafcbyOperCashoutAdju + ", " : "")
                + (tafcby120000 != null ? "tafcby120000=" + tafcby120000 + ", " : "")
                + (tafcbyOperNetCashAdju != null ? "tafcbyOperNetCashAdju=" + tafcbyOperNetCashAdju + ", " : "")
                + (tafcby100000 != null ? "tafcby100000=" + tafcby100000 + ", " : "")
                + (tafcby210101 != null ? "tafcby210101=" + tafcby210101 + ", " : "")
                + (tafcby210201 != null ? "tafcby210201=" + tafcby210201 + ", " : "")
                + (tafcby210301 != null ? "tafcby210301=" + tafcby210301 + ", " : "")
                + (tafcby210501 != null ? "tafcby210501=" + tafcby210501 + ", " : "")
                + (tafcby210401 != null ? "tafcby210401=" + tafcby210401 + ", " : "")
                + (tafcbyInvesCashinSpec != null ? "tafcbyInvesCashinSpec=" + tafcbyInvesCashinSpec + ", " : "")
                + (tafcbyInvesCashinAdju != null ? "tafcbyInvesCashinAdju=" + tafcbyInvesCashinAdju + ", " : "")
                + (tafcby210000 != null ? "tafcby210000=" + tafcby210000 + ", " : "")
                + (tafcby220101 != null ? "tafcby220101=" + tafcby220101 + ", " : "")
                + (tafcby220201 != null ? "tafcby220201=" + tafcby220201 + ", " : "")
                + (tafcby220401 != null ? "tafcby220401=" + tafcby220401 + ", " : "")
                + (tafcby220402 != null ? "tafcby220402=" + tafcby220402 + ", " : "")
                + (tafcby220301 != null ? "tafcby220301=" + tafcby220301 + ", " : "")
                + (tafcbyInvesCashoutSpec != null ? "tafcbyInvesCashoutSpec=" + tafcbyInvesCashoutSpec + ", " : "")
                + (tafcbyInvesCashoutAdju != null ? "tafcbyInvesCashoutAdju=" + tafcbyInvesCashoutAdju + ", " : "")
                + (tafcby220000 != null ? "tafcby220000=" + tafcby220000 + ", " : "")
                + (tafcbyInvesNetCashAdju != null ? "tafcbyInvesNetCashAdju=" + tafcbyInvesNetCashAdju + ", " : "")
                + (tafcby200000 != null ? "tafcby200000=" + tafcby200000 + ", " : "")
                + (tafcby310301 != null ? "tafcby310301=" + tafcby310301 + ", " : "")
                + (tafcby310111 != null ? "tafcby310111=" + tafcby310111 + ", " : "")
                + (tafcby310201 != null ? "tafcby310201=" + tafcby310201 + ", " : "")
                + (tafcbf320010 != null ? "tafcbf320010=" + tafcbf320010 + ", " : "")
                + (tafcby310401 != null ? "tafcby310401=" + tafcby310401 + ", " : "")
                + (tafcby310501 != null ? "tafcby310501=" + tafcby310501 + ", " : "")
                + (tafcbyFinCashinSpec != null ? "tafcbyFinCashinSpec=" + tafcbyFinCashinSpec + ", " : "")
                + (tafcbyFinCashinAdju != null ? "tafcbyFinCashinAdju=" + tafcbyFinCashinAdju + ", " : "")
                + (tafcby310000 != null ? "tafcby310000=" + tafcby310000 + ", " : "")
                + (tafcby320101 != null ? "tafcby320101=" + tafcby320101 + ", " : "")
                + (tafcby320301 != null ? "tafcby320301=" + tafcby320301 + ", " : "")
                + (tafcby320801 != null ? "tafcby320801=" + tafcby320801 + ", " : "")
                + (tafcby320701 != null ? "tafcby320701=" + tafcby320701 + ", " : "")
                + (tafcbyFinCashoutSpec != null ? "tafcbyFinCashoutSpec=" + tafcbyFinCashoutSpec + ", " : "")
                + (tafcbyFinCashoutAdju != null ? "tafcbyFinCashoutAdju=" + tafcbyFinCashoutAdju + ", " : "")
                + (tafcby320000 != null ? "tafcby320000=" + tafcby320000 + ", " : "")
                + (tafcbyFinNetCashAdju != null ? "tafcbyFinNetCashAdju=" + tafcbyFinNetCashAdju + ", " : "")
                + (tafcby300000 != null ? "tafcby300000=" + tafcby300000 + ", " : "")
                + (tafcby410101 != null ? "tafcby410101=" + tafcby410101 + ", " : "")
                + (tafcby413101 != null ? "tafcby413101=" + tafcby413101 + ", " : "")
                + (tafcbyCashAdju != null ? "tafcbyCashAdju=" + tafcbyCashAdju + ", " : "")
                + (tafcby410201 != null ? "tafcby410201=" + tafcby410201 + ", " : "")
                + (tafcby413201 != null ? "tafcby413201=" + tafcby413201 + ", " : "")
                + (tafcby413301 != null ? "tafcby413301=" + tafcby413301 + ", " : "")
                + (tafcby411001 != null ? "tafcby411001=" + tafcby411001 + ", " : "")
                + (tafcby411401 != null ? "tafcby411401=" + tafcby411401 + ", " : "")
                + (tafcby411501 != null ? "tafcby411501=" + tafcby411501 + ", " : "")
                + (tafcby411601 != null ? "tafcby411601=" + tafcby411601 + ", " : "")
                + (tafcby411701 != null ? "tafcby411701=" + tafcby411701 + ", " : "")
                + (tafcby411801 != null ? "tafcby411801=" + tafcby411801 + ", " : "")
                + (tafcby411901 != null ? "tafcby411901=" + tafcby411901 + ", " : "")
                + (tafcby412001 != null ? "tafcby412001=" + tafcby412001 + ", " : "")
                + (tafcby412101 != null ? "tafcby412101=" + tafcby412101 + ", " : "")
                + (tafcby413501 != null ? "tafcby413501=" + tafcby413501 + ", " : "")
                + (tafcby412201 != null ? "tafcby412201=" + tafcby412201 + ", " : "")
                + (tafcby412301 != null ? "tafcby412301=" + tafcby412301 + ", " : "")
                + (tafcby413601 != null ? "tafcby413601=" + tafcby413601 + ", " : "")
                + (tafcby413701 != null ? "tafcby413701=" + tafcby413701 + ", " : "")
                + (tafcby412601 != null ? "tafcby412601=" + tafcby412601 + ", " : "")
                + (tafcby412701 != null ? "tafcby412701=" + tafcby412701 + ", " : "")
                + (tafcby412801 != null ? "tafcby412801=" + tafcby412801 + ", " : "")
                + (tafcby413001 != null ? "tafcby413001=" + tafcby413001 + ", " : "")
                + (tafcbyAddOperNetcashSpec != null ? "tafcbyAddOperNetcashSpec=" + tafcbyAddOperNetcashSpec + ", "
                        : "")
                + (tafcbyAddOperNetcashAdju != null ? "tafcbyAddOperNetcashAdju=" + tafcbyAddOperNetcashAdju + ", "
                        : "")
                + (tafcbyAddOperNetcash != null ? "tafcbyAddOperNetcash=" + tafcbyAddOperNetcash + ", " : "")
                + (tafcbyAddOperNetcashCprt != null ? "tafcbyAddOperNetcashCprt=" + tafcbyAddOperNetcashCprt + ", "
                        : "")
                + (tafcby410501 != null ? "tafcby410501=" + tafcby410501 + ", " : "")
                + (tafcby410801 != null ? "tafcby410801=" + tafcby410801 + ", " : "")
                + (tafcby410802 != null ? "tafcby410802=" + tafcby410802 + ", " : "")
                + (tafcby510101 != null ? "tafcby510101=" + tafcby510101 + ", " : "")
                + (tafcby510201 != null ? "tafcby510201=" + tafcby510201 + ", " : "")
                + (tafcby510301 != null ? "tafcby510301=" + tafcby510301 + ", " : "")
                + (tafcby510401 != null ? "tafcby510401=" + tafcby510401 + ", " : "")
                + (tafcbyAddCashSpec != null ? "tafcbyAddCashSpec=" + tafcbyAddCashSpec + ", " : "")
                + (tafcbyAddCashAdju != null ? "tafcbyAddCashAdju=" + tafcbyAddCashAdju + ", " : "")
                + (tafcbyAddNetcash != null ? "tafcbyAddNetcash=" + tafcbyAddNetcash + ", " : "")
                + (tafcbyNetcashCprt != null ? "tafcbyNetcashCprt=" + tafcbyNetcashCprt + ", " : "")
                + (tafcbySpecDes != null ? "tafcbySpecDes=" + tafcbySpecDes + ", " : "")
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
        selectSql += ",START_DATE";
        selectSql += ",END_DATE";
        selectSql += ",SHEET_MARK_PAR";
        selectSql += ",SHEET_ATTR_PAR";
        selectSql += ",COM_UNI_CODE";
        selectSql += ",TAFCBY_110001";
        selectSql += ",TAFCBY_110007";
        selectSql += ",TAFCBY_110008";
        selectSql += ",TAFCBY_110025";
        selectSql += ",TAFCBY_110009";
        selectSql += ",TAFCBY_110048";
        selectSql += ",TAFCBY_110049";
        selectSql += ",TAFCBF_110020";
        selectSql += ",TAFCBF_110014";
        selectSql += ",TAFCBF_110015";
        selectSql += ",TAFCBF_110016";
        selectSql += ",TAFCBY_110401";
        selectSql += ",TAFCBY_OPER_CASHIN_SPEC";
        selectSql += ",TAFCBY_OPER_CASHIN_ADJU";
        selectSql += ",TAFCBY_110000";
        selectSql += ",TAFCBY_120301";
        selectSql += ",TAFCBY_120401";
        selectSql += ",TAFCBY_120001";
        selectSql += ",TAFCBY_120045";
        selectSql += ",TAFCBY_120020";
        selectSql += ",TAFCBY_120002";
        selectSql += ",TAFCBF_120025";
        selectSql += ",TAFCBF_120038";
        selectSql += ",TAFCBF_120026";
        selectSql += ",TAFCBY_120501";
        selectSql += ",TAFCBY_OPER_CASHOUT_SPEC";
        selectSql += ",TAFCBY_OPER_CASHOUT_ADJU";
        selectSql += ",TAFCBY_120000";
        selectSql += ",TAFCBY_OPER_NET_CASH_ADJU";
        selectSql += ",TAFCBY_100000";
        selectSql += ",TAFCBY_210101";
        selectSql += ",TAFCBY_210201";
        selectSql += ",TAFCBY_210301";
        selectSql += ",TAFCBY_210501";
        selectSql += ",TAFCBY_210401";
        selectSql += ",TAFCBY_INVES_CASHIN_SPEC";
        selectSql += ",TAFCBY_INVES_CASHIN_ADJU";
        selectSql += ",TAFCBY_210000";
        selectSql += ",TAFCBY_220101";
        selectSql += ",TAFCBY_220201";
        selectSql += ",TAFCBY_220401";
        selectSql += ",TAFCBY_220402";
        selectSql += ",TAFCBY_220301";
        selectSql += ",TAFCBY_INVES_CASHOUT_SPEC";
        selectSql += ",TAFCBY_INVES_CASHOUT_ADJU";
        selectSql += ",TAFCBY_220000";
        selectSql += ",TAFCBY_INVES_NET_CASH_ADJU";
        selectSql += ",TAFCBY_200000";
        selectSql += ",TAFCBY_310301";
        selectSql += ",TAFCBY_310111";
        selectSql += ",TAFCBY_310201";
        selectSql += ",TAFCBF_320010";
        selectSql += ",TAFCBY_310401";
        selectSql += ",TAFCBY_310501";
        selectSql += ",TAFCBY_FIN_CASHIN_SPEC";
        selectSql += ",TAFCBY_FIN_CASHIN_ADJU";
        selectSql += ",TAFCBY_310000";
        selectSql += ",TAFCBY_320101";
        selectSql += ",TAFCBY_320301";
        selectSql += ",TAFCBY_320801";
        selectSql += ",TAFCBY_320701";
        selectSql += ",TAFCBY_FIN_CASHOUT_SPEC";
        selectSql += ",TAFCBY_FIN_CASHOUT_ADJU";
        selectSql += ",TAFCBY_320000";
        selectSql += ",TAFCBY_FIN_NET_CASH_ADJU";
        selectSql += ",TAFCBY_300000";
        selectSql += ",TAFCBY_410101";
        selectSql += ",TAFCBY_413101";
        selectSql += ",TAFCBY_CASH_ADJU";
        selectSql += ",TAFCBY_410201";
        selectSql += ",TAFCBY_413201";
        selectSql += ",TAFCBY_413301";
        selectSql += ",TAFCBY_411001";
        selectSql += ",TAFCBY_411401";
        selectSql += ",TAFCBY_411501";
        selectSql += ",TAFCBY_411601";
        selectSql += ",TAFCBY_411701";
        selectSql += ",TAFCBY_411801";
        selectSql += ",TAFCBY_411901";
        selectSql += ",TAFCBY_412001";
        selectSql += ",TAFCBY_412101";
        selectSql += ",TAFCBY_413501";
        selectSql += ",TAFCBY_412201";
        selectSql += ",TAFCBY_412301";
        selectSql += ",TAFCBY_413601";
        selectSql += ",TAFCBY_413701";
        selectSql += ",TAFCBY_412601";
        selectSql += ",TAFCBY_412701";
        selectSql += ",TAFCBY_412801";
        selectSql += ",TAFCBY_413001";
        selectSql += ",TAFCBY_ADD_OPER_NETCASH_SPEC";
        selectSql += ",TAFCBY_ADD_OPER_NETCASH_ADJU";
        selectSql += ",TAFCBY_ADD_OPER_NETCASH";
        selectSql += ",TAFCBY_ADD_OPER_NETCASH_CPRT";
        selectSql += ",TAFCBY_410501";
        selectSql += ",TAFCBY_410801";
        selectSql += ",TAFCBY_410802";
        selectSql += ",TAFCBY_510101";
        selectSql += ",TAFCBY_510201";
        selectSql += ",TAFCBY_510301";
        selectSql += ",TAFCBY_510401";
        selectSql += ",TAFCBY_ADD_CASH_SPEC";
        selectSql += ",TAFCBY_ADD_CASH_ADJU";
        selectSql += ",TAFCBY_ADD_NETCASH";
        selectSql += ",TAFCBY_NETCASH_CPRT";
        selectSql += ",TAFCBY_SPEC_DES";
        selectSql += ",INFO_PUB_DATE";
 	   return selectSql.substring(1);
     }
 	
 	public String createEqualsSql(String whereSql){
 	   String selectSql = createSelectColumnSql();       
        String sql = "select count(1) from "
        +" (select * from (select "+selectSql+" from bond_ccxe.d_bond_fin_fal_cash_tafcb where "+whereSql
        +" order by ccxeid desc limit 1) a union select * from (select "+selectSql+" from bond_ccxe_snapshot.d_bond_fin_fal_cash_tafcb where "+whereSql+" order by ccxeid desc limit 1) a) a";
        return sql;
     }
     
     public String createEqualsSql(String id, Long bondUniCode, Long comUniCode, String endDate){
 	   String selectSql = createSelectColumnSql();
 	  String whereSql = " id='" + id + "' and bond_uni_code=" + bondUniCode 
 	         + " and com_uni_code=" + comUniCode 
 	         + " and end_date='" + endDate + "'";
                
        String sql = "select count(1) from "
        +" (select * from (select "+selectSql+" from bond_ccxe.d_bond_fin_fal_cash_tafcb where "+whereSql
        +" order by ccxeid desc limit 1) a union select * from (select "+selectSql+" from bond_ccxe_snapshot.d_bond_fin_fal_cash_tafcb where "+whereSql+" order by ccxeid desc limit 1) a) a";
        return sql;
     }
}
