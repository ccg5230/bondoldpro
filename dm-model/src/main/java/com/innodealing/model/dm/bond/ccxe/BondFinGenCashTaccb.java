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
@Table(name="d_bond_fin_gen_cash_taccb")
public class BondFinGenCashTaccb implements Serializable{
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
    @Column(name="TACCB_110101", length=20)
    private BigDecimal taccb110101;
    
	/**
	 * 
	 */
    @Column(name="TACCB_110301", length=20)
    private BigDecimal taccb110301;
    
	/**
	 * 
	 */
    @Column(name="TACCB_110401", length=20)
    private BigDecimal taccb110401;
    
	/**
	 * 
	 */
    @Column(name="TACCB_OPER_CASHIN_SPEC", length=20)
    private BigDecimal taccbOperCashinSpec;
    
	/**
	 * 
	 */
    @Column(name="TACCB_OPER_CASHIN_ADJU", length=20)
    private BigDecimal taccbOperCashinAdju;
    
	/**
	 * 
	 */
    @Column(name="TACCB_110000", length=20)
    private BigDecimal taccb110000;
    
	/**
	 * 
	 */
    @Column(name="TACCB_120101", length=20)
    private BigDecimal taccb120101;
    
	/**
	 * 
	 */
    @Column(name="TACCB_120301", length=20)
    private BigDecimal taccb120301;
    
	/**
	 * 
	 */
    @Column(name="TACCB_120401", length=20)
    private BigDecimal taccb120401;
    
	/**
	 * 
	 */
    @Column(name="TACCB_120501", length=20)
    private BigDecimal taccb120501;
    
	/**
	 * 
	 */
    @Column(name="TACCB_OPER_CASHOUT_SPEC", length=20)
    private BigDecimal taccbOperCashoutSpec;
    
	/**
	 * 
	 */
    @Column(name="TACCB_OPER_CASHOUT_ADJU", length=20)
    private BigDecimal taccbOperCashoutAdju;
    
	/**
	 * 
	 */
    @Column(name="TACCB_120000", length=20)
    private BigDecimal taccb120000;
    
	/**
	 * 
	 */
    @Column(name="TACCB_OPER_NET_CASH_ADJU", length=20)
    private BigDecimal taccbOperNetCashAdju;
    
	/**
	 * 
	 */
    @Column(name="TACCB_100000", length=20)
    private BigDecimal taccb100000;
    
	/**
	 * 
	 */
    @Column(name="TACCB_210101", length=20)
    private BigDecimal taccb210101;
    
	/**
	 * 
	 */
    @Column(name="TACCB_210201", length=20)
    private BigDecimal taccb210201;
    
	/**
	 * 
	 */
    @Column(name="TACCB_210301", length=20)
    private BigDecimal taccb210301;
    
	/**
	 * 
	 */
    @Column(name="TACCB_210501", length=20)
    private BigDecimal taccb210501;
    
	/**
	 * 
	 */
    @Column(name="TACCB_210401", length=20)
    private BigDecimal taccb210401;
    
	/**
	 * 
	 */
    @Column(name="TACCB_INVES_CASHIN_SPEC", length=20)
    private BigDecimal taccbInvesCashinSpec;
    
	/**
	 * 
	 */
    @Column(name="TACCB_INVES_CASHIN_ADJU", length=20)
    private BigDecimal taccbInvesCashinAdju;
    
	/**
	 * 
	 */
    @Column(name="TACCB_210000", length=20)
    private BigDecimal taccb210000;
    
	/**
	 * 
	 */
    @Column(name="TACCB_220101", length=20)
    private BigDecimal taccb220101;
    
	/**
	 * 
	 */
    @Column(name="TACCB_220201", length=20)
    private BigDecimal taccb220201;
    
	/**
	 * 
	 */
    @Column(name="TACCB_220401", length=20)
    private BigDecimal taccb220401;
    
	/**
	 * 
	 */
    @Column(name="TACCB_220402", length=20)
    private BigDecimal taccb220402;
    
	/**
	 * 
	 */
    @Column(name="TACCB_220301", length=20)
    private BigDecimal taccb220301;
    
	/**
	 * 
	 */
    @Column(name="TACCB_INVES_CASHOUT_SPEC", length=20)
    private BigDecimal taccbInvesCashoutSpec;
    
	/**
	 * 
	 */
    @Column(name="TACCB_INVES_CASHOUT_ADJU", length=20)
    private BigDecimal taccbInvesCashoutAdju;
    
	/**
	 * 
	 */
    @Column(name="TACCB_220000", length=20)
    private BigDecimal taccb220000;
    
	/**
	 * 
	 */
    @Column(name="TACCB_INVES_NET_CASH_ADJU", length=20)
    private BigDecimal taccbInvesNetCashAdju;
    
	/**
	 * 
	 */
    @Column(name="TACCB_200000", length=20)
    private BigDecimal taccb200000;
    
	/**
	 * 
	 */
    @Column(name="TACCB_310301", length=20)
    private BigDecimal taccb310301;
    
	/**
	 * 
	 */
    @Column(name="TACCB_310111", length=20)
    private BigDecimal taccb310111;
    
	/**
	 * 
	 */
    @Column(name="TACCB_310201", length=20)
    private BigDecimal taccb310201;
    
	/**
	 * 
	 */
    @Column(name="TACCB_310401", length=20)
    private BigDecimal taccb310401;
    
	/**
	 * 
	 */
    @Column(name="TACCB_310501", length=20)
    private BigDecimal taccb310501;
    
	/**
	 * 
	 */
    @Column(name="TACCB_FIN_CASHIN_SPEC", length=20)
    private BigDecimal taccbFinCashinSpec;
    
	/**
	 * 
	 */
    @Column(name="TACCB_FIN_CASHIN_ADJU", length=20)
    private BigDecimal taccbFinCashinAdju;
    
	/**
	 * 
	 */
    @Column(name="TACCB_310000", length=20)
    private BigDecimal taccb310000;
    
	/**
	 * 
	 */
    @Column(name="TACCB_320101", length=20)
    private BigDecimal taccb320101;
    
	/**
	 * 
	 */
    @Column(name="TACCB_320301", length=20)
    private BigDecimal taccb320301;
    
	/**
	 * 
	 */
    @Column(name="TACCB_320801", length=20)
    private BigDecimal taccb320801;
    
	/**
	 * 
	 */
    @Column(name="TACCB_320701", length=20)
    private BigDecimal taccb320701;
    
	/**
	 * 
	 */
    @Column(name="TACCB_FIN_CASHOUT_SPEC", length=20)
    private BigDecimal taccbFinCashoutSpec;
    
	/**
	 * 
	 */
    @Column(name="TACCB_FIN_CASHOUT_ADJU", length=20)
    private BigDecimal taccbFinCashoutAdju;
    
	/**
	 * 
	 */
    @Column(name="TACCB_320000", length=20)
    private BigDecimal taccb320000;
    
	/**
	 * 
	 */
    @Column(name="TACCB_FIN_NET_CASH_ADJU", length=20)
    private BigDecimal taccbFinNetCashAdju;
    
	/**
	 * 
	 */
    @Column(name="TACCB_300000", length=20)
    private BigDecimal taccb300000;
    
	/**
	 * 
	 */
    @Column(name="TACCB_410101", length=20)
    private BigDecimal taccb410101;
    
	/**
	 * 
	 */
    @Column(name="TACCB_413101", length=20)
    private BigDecimal taccb413101;
    
	/**
	 * 
	 */
    @Column(name="TACCB_CASH_ADJU", length=20)
    private BigDecimal taccbCashAdju;
    
	/**
	 * 
	 */
    @Column(name="TACCB_410201", length=20)
    private BigDecimal taccb410201;
    
	/**
	 * 
	 */
    @Column(name="TACCB_413201", length=20)
    private BigDecimal taccb413201;
    
	/**
	 * 
	 */
    @Column(name="TACCB_413301", length=20)
    private BigDecimal taccb413301;
    
	/**
	 * 
	 */
    @Column(name="TACCB_411001", length=20)
    private BigDecimal taccb411001;
    
	/**
	 * 
	 */
    @Column(name="TACCB_411401", length=20)
    private BigDecimal taccb411401;
    
	/**
	 * 
	 */
    @Column(name="TACCB_411501", length=20)
    private BigDecimal taccb411501;
    
	/**
	 * 
	 */
    @Column(name="TACCB_411601", length=20)
    private BigDecimal taccb411601;
    
	/**
	 * 
	 */
    @Column(name="TACCB_411701", length=20)
    private BigDecimal taccb411701;
    
	/**
	 * 
	 */
    @Column(name="TACCB_411801", length=20)
    private BigDecimal taccb411801;
    
	/**
	 * 
	 */
    @Column(name="TACCB_411901", length=20)
    private BigDecimal taccb411901;
    
	/**
	 * 
	 */
    @Column(name="TACCB_412001", length=20)
    private BigDecimal taccb412001;
    
	/**
	 * 
	 */
    @Column(name="TACCB_412101", length=20)
    private BigDecimal taccb412101;
    
	/**
	 * 
	 */
    @Column(name="TACCB_413501", length=20)
    private BigDecimal taccb413501;
    
	/**
	 * 
	 */
    @Column(name="TACCB_412201", length=20)
    private BigDecimal taccb412201;
    
	/**
	 * 
	 */
    @Column(name="TACCB_412301", length=20)
    private BigDecimal taccb412301;
    
	/**
	 * 
	 */
    @Column(name="TACCB_413601", length=20)
    private BigDecimal taccb413601;
    
	/**
	 * 
	 */
    @Column(name="TACCB_413701", length=20)
    private BigDecimal taccb413701;
    
	/**
	 * 
	 */
    @Column(name="TACCB_412601", length=20)
    private BigDecimal taccb412601;
    
	/**
	 * 
	 */
    @Column(name="TACCB_412701", length=20)
    private BigDecimal taccb412701;
    
	/**
	 * 
	 */
    @Column(name="TACCB_412801", length=20)
    private BigDecimal taccb412801;
    
	/**
	 * 
	 */
    @Column(name="TACCB_411201", length=20)
    private BigDecimal taccb411201;
    
	/**
	 * 
	 */
    @Column(name="TACCB_413001", length=20)
    private BigDecimal taccb413001;
    
	/**
	 * 
	 */
    @Column(name="TACCB_ADD_OPER_NETCASH_SPEC", length=20)
    private BigDecimal taccbAddOperNetcashSpec;
    
	/**
	 * 
	 */
    @Column(name="TACCB_ADD_OPER_NETCASH_ADJU", length=20)
    private BigDecimal taccbAddOperNetcashAdju;
    
	/**
	 * 
	 */
    @Column(name="TACCB_ADD_OPER_NETCASH", length=20)
    private BigDecimal taccbAddOperNetcash;
    
	/**
	 * 
	 */
    @Column(name="TACCB_ADD_OPER_NETCASH_CPRT", length=20)
    private BigDecimal taccbAddOperNetcashCprt;
    
	/**
	 * 
	 */
    @Column(name="TACCB_410501", length=20)
    private BigDecimal taccb410501;
    
	/**
	 * 
	 */
    @Column(name="TACCB_410801", length=20)
    private BigDecimal taccb410801;
    
	/**
	 * 
	 */
    @Column(name="TACCB_410802", length=20)
    private BigDecimal taccb410802;
    
	/**
	 * 
	 */
    @Column(name="TACCB_510101", length=20)
    private BigDecimal taccb510101;
    
	/**
	 * 
	 */
    @Column(name="TACCB_510201", length=20)
    private BigDecimal taccb510201;
    
	/**
	 * 
	 */
    @Column(name="TACCB_510301", length=20)
    private BigDecimal taccb510301;
    
	/**
	 * 
	 */
    @Column(name="TACCB_510401", length=20)
    private BigDecimal taccb510401;
    
	/**
	 * 
	 */
    @Column(name="TACCB_ADD_CASH_SPEC", length=20)
    private BigDecimal taccbAddCashSpec;
    
	/**
	 * 
	 */
    @Column(name="TACCB_ADD_CASH_ADJU", length=20)
    private BigDecimal taccbAddCashAdju;
    
	/**
	 * 
	 */
    @Column(name="TACCB_ADD_NETCASH", length=20)
    private BigDecimal taccbAddNetcash;
    
	/**
	 * 
	 */
    @Column(name="TACCB_NETCASH_CPRT", length=20)
    private BigDecimal taccbNetcashCprt;
    
	/**
	 * 
	 */
    @Column(name="TACCB_SPEC_DES", length=16777215)
    private String taccbSpecDes;
    
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
	
    public void setTaccb110101(BigDecimal taccb110101){
       this.taccb110101 = taccb110101;
    }
    
    public BigDecimal getTaccb110101(){
       return this.taccb110101;
    }
	
    public void setTaccb110301(BigDecimal taccb110301){
       this.taccb110301 = taccb110301;
    }
    
    public BigDecimal getTaccb110301(){
       return this.taccb110301;
    }
	
    public void setTaccb110401(BigDecimal taccb110401){
       this.taccb110401 = taccb110401;
    }
    
    public BigDecimal getTaccb110401(){
       return this.taccb110401;
    }
	
    public void setTaccbOperCashinSpec(BigDecimal taccbOperCashinSpec){
       this.taccbOperCashinSpec = taccbOperCashinSpec;
    }
    
    public BigDecimal getTaccbOperCashinSpec(){
       return this.taccbOperCashinSpec;
    }
	
    public void setTaccbOperCashinAdju(BigDecimal taccbOperCashinAdju){
       this.taccbOperCashinAdju = taccbOperCashinAdju;
    }
    
    public BigDecimal getTaccbOperCashinAdju(){
       return this.taccbOperCashinAdju;
    }
	
    public void setTaccb110000(BigDecimal taccb110000){
       this.taccb110000 = taccb110000;
    }
    
    public BigDecimal getTaccb110000(){
       return this.taccb110000;
    }
	
    public void setTaccb120101(BigDecimal taccb120101){
       this.taccb120101 = taccb120101;
    }
    
    public BigDecimal getTaccb120101(){
       return this.taccb120101;
    }
	
    public void setTaccb120301(BigDecimal taccb120301){
       this.taccb120301 = taccb120301;
    }
    
    public BigDecimal getTaccb120301(){
       return this.taccb120301;
    }
	
    public void setTaccb120401(BigDecimal taccb120401){
       this.taccb120401 = taccb120401;
    }
    
    public BigDecimal getTaccb120401(){
       return this.taccb120401;
    }
	
    public void setTaccb120501(BigDecimal taccb120501){
       this.taccb120501 = taccb120501;
    }
    
    public BigDecimal getTaccb120501(){
       return this.taccb120501;
    }
	
    public void setTaccbOperCashoutSpec(BigDecimal taccbOperCashoutSpec){
       this.taccbOperCashoutSpec = taccbOperCashoutSpec;
    }
    
    public BigDecimal getTaccbOperCashoutSpec(){
       return this.taccbOperCashoutSpec;
    }
	
    public void setTaccbOperCashoutAdju(BigDecimal taccbOperCashoutAdju){
       this.taccbOperCashoutAdju = taccbOperCashoutAdju;
    }
    
    public BigDecimal getTaccbOperCashoutAdju(){
       return this.taccbOperCashoutAdju;
    }
	
    public void setTaccb120000(BigDecimal taccb120000){
       this.taccb120000 = taccb120000;
    }
    
    public BigDecimal getTaccb120000(){
       return this.taccb120000;
    }
	
    public void setTaccbOperNetCashAdju(BigDecimal taccbOperNetCashAdju){
       this.taccbOperNetCashAdju = taccbOperNetCashAdju;
    }
    
    public BigDecimal getTaccbOperNetCashAdju(){
       return this.taccbOperNetCashAdju;
    }
	
    public void setTaccb100000(BigDecimal taccb100000){
       this.taccb100000 = taccb100000;
    }
    
    public BigDecimal getTaccb100000(){
       return this.taccb100000;
    }
	
    public void setTaccb210101(BigDecimal taccb210101){
       this.taccb210101 = taccb210101;
    }
    
    public BigDecimal getTaccb210101(){
       return this.taccb210101;
    }
	
    public void setTaccb210201(BigDecimal taccb210201){
       this.taccb210201 = taccb210201;
    }
    
    public BigDecimal getTaccb210201(){
       return this.taccb210201;
    }
	
    public void setTaccb210301(BigDecimal taccb210301){
       this.taccb210301 = taccb210301;
    }
    
    public BigDecimal getTaccb210301(){
       return this.taccb210301;
    }
	
    public void setTaccb210501(BigDecimal taccb210501){
       this.taccb210501 = taccb210501;
    }
    
    public BigDecimal getTaccb210501(){
       return this.taccb210501;
    }
	
    public void setTaccb210401(BigDecimal taccb210401){
       this.taccb210401 = taccb210401;
    }
    
    public BigDecimal getTaccb210401(){
       return this.taccb210401;
    }
	
    public void setTaccbInvesCashinSpec(BigDecimal taccbInvesCashinSpec){
       this.taccbInvesCashinSpec = taccbInvesCashinSpec;
    }
    
    public BigDecimal getTaccbInvesCashinSpec(){
       return this.taccbInvesCashinSpec;
    }
	
    public void setTaccbInvesCashinAdju(BigDecimal taccbInvesCashinAdju){
       this.taccbInvesCashinAdju = taccbInvesCashinAdju;
    }
    
    public BigDecimal getTaccbInvesCashinAdju(){
       return this.taccbInvesCashinAdju;
    }
	
    public void setTaccb210000(BigDecimal taccb210000){
       this.taccb210000 = taccb210000;
    }
    
    public BigDecimal getTaccb210000(){
       return this.taccb210000;
    }
	
    public void setTaccb220101(BigDecimal taccb220101){
       this.taccb220101 = taccb220101;
    }
    
    public BigDecimal getTaccb220101(){
       return this.taccb220101;
    }
	
    public void setTaccb220201(BigDecimal taccb220201){
       this.taccb220201 = taccb220201;
    }
    
    public BigDecimal getTaccb220201(){
       return this.taccb220201;
    }
	
    public void setTaccb220401(BigDecimal taccb220401){
       this.taccb220401 = taccb220401;
    }
    
    public BigDecimal getTaccb220401(){
       return this.taccb220401;
    }
	
    public void setTaccb220402(BigDecimal taccb220402){
       this.taccb220402 = taccb220402;
    }
    
    public BigDecimal getTaccb220402(){
       return this.taccb220402;
    }
	
    public void setTaccb220301(BigDecimal taccb220301){
       this.taccb220301 = taccb220301;
    }
    
    public BigDecimal getTaccb220301(){
       return this.taccb220301;
    }
	
    public void setTaccbInvesCashoutSpec(BigDecimal taccbInvesCashoutSpec){
       this.taccbInvesCashoutSpec = taccbInvesCashoutSpec;
    }
    
    public BigDecimal getTaccbInvesCashoutSpec(){
       return this.taccbInvesCashoutSpec;
    }
	
    public void setTaccbInvesCashoutAdju(BigDecimal taccbInvesCashoutAdju){
       this.taccbInvesCashoutAdju = taccbInvesCashoutAdju;
    }
    
    public BigDecimal getTaccbInvesCashoutAdju(){
       return this.taccbInvesCashoutAdju;
    }
	
    public void setTaccb220000(BigDecimal taccb220000){
       this.taccb220000 = taccb220000;
    }
    
    public BigDecimal getTaccb220000(){
       return this.taccb220000;
    }
	
    public void setTaccbInvesNetCashAdju(BigDecimal taccbInvesNetCashAdju){
       this.taccbInvesNetCashAdju = taccbInvesNetCashAdju;
    }
    
    public BigDecimal getTaccbInvesNetCashAdju(){
       return this.taccbInvesNetCashAdju;
    }
	
    public void setTaccb200000(BigDecimal taccb200000){
       this.taccb200000 = taccb200000;
    }
    
    public BigDecimal getTaccb200000(){
       return this.taccb200000;
    }
	
    public void setTaccb310301(BigDecimal taccb310301){
       this.taccb310301 = taccb310301;
    }
    
    public BigDecimal getTaccb310301(){
       return this.taccb310301;
    }
	
    public void setTaccb310111(BigDecimal taccb310111){
       this.taccb310111 = taccb310111;
    }
    
    public BigDecimal getTaccb310111(){
       return this.taccb310111;
    }
	
    public void setTaccb310201(BigDecimal taccb310201){
       this.taccb310201 = taccb310201;
    }
    
    public BigDecimal getTaccb310201(){
       return this.taccb310201;
    }
	
    public void setTaccb310401(BigDecimal taccb310401){
       this.taccb310401 = taccb310401;
    }
    
    public BigDecimal getTaccb310401(){
       return this.taccb310401;
    }
	
    public void setTaccb310501(BigDecimal taccb310501){
       this.taccb310501 = taccb310501;
    }
    
    public BigDecimal getTaccb310501(){
       return this.taccb310501;
    }
	
    public void setTaccbFinCashinSpec(BigDecimal taccbFinCashinSpec){
       this.taccbFinCashinSpec = taccbFinCashinSpec;
    }
    
    public BigDecimal getTaccbFinCashinSpec(){
       return this.taccbFinCashinSpec;
    }
	
    public void setTaccbFinCashinAdju(BigDecimal taccbFinCashinAdju){
       this.taccbFinCashinAdju = taccbFinCashinAdju;
    }
    
    public BigDecimal getTaccbFinCashinAdju(){
       return this.taccbFinCashinAdju;
    }
	
    public void setTaccb310000(BigDecimal taccb310000){
       this.taccb310000 = taccb310000;
    }
    
    public BigDecimal getTaccb310000(){
       return this.taccb310000;
    }
	
    public void setTaccb320101(BigDecimal taccb320101){
       this.taccb320101 = taccb320101;
    }
    
    public BigDecimal getTaccb320101(){
       return this.taccb320101;
    }
	
    public void setTaccb320301(BigDecimal taccb320301){
       this.taccb320301 = taccb320301;
    }
    
    public BigDecimal getTaccb320301(){
       return this.taccb320301;
    }
	
    public void setTaccb320801(BigDecimal taccb320801){
       this.taccb320801 = taccb320801;
    }
    
    public BigDecimal getTaccb320801(){
       return this.taccb320801;
    }
	
    public void setTaccb320701(BigDecimal taccb320701){
       this.taccb320701 = taccb320701;
    }
    
    public BigDecimal getTaccb320701(){
       return this.taccb320701;
    }
	
    public void setTaccbFinCashoutSpec(BigDecimal taccbFinCashoutSpec){
       this.taccbFinCashoutSpec = taccbFinCashoutSpec;
    }
    
    public BigDecimal getTaccbFinCashoutSpec(){
       return this.taccbFinCashoutSpec;
    }
	
    public void setTaccbFinCashoutAdju(BigDecimal taccbFinCashoutAdju){
       this.taccbFinCashoutAdju = taccbFinCashoutAdju;
    }
    
    public BigDecimal getTaccbFinCashoutAdju(){
       return this.taccbFinCashoutAdju;
    }
	
    public void setTaccb320000(BigDecimal taccb320000){
       this.taccb320000 = taccb320000;
    }
    
    public BigDecimal getTaccb320000(){
       return this.taccb320000;
    }
	
    public void setTaccbFinNetCashAdju(BigDecimal taccbFinNetCashAdju){
       this.taccbFinNetCashAdju = taccbFinNetCashAdju;
    }
    
    public BigDecimal getTaccbFinNetCashAdju(){
       return this.taccbFinNetCashAdju;
    }
	
    public void setTaccb300000(BigDecimal taccb300000){
       this.taccb300000 = taccb300000;
    }
    
    public BigDecimal getTaccb300000(){
       return this.taccb300000;
    }
	
    public void setTaccb410101(BigDecimal taccb410101){
       this.taccb410101 = taccb410101;
    }
    
    public BigDecimal getTaccb410101(){
       return this.taccb410101;
    }
	
    public void setTaccb413101(BigDecimal taccb413101){
       this.taccb413101 = taccb413101;
    }
    
    public BigDecimal getTaccb413101(){
       return this.taccb413101;
    }
	
    public void setTaccbCashAdju(BigDecimal taccbCashAdju){
       this.taccbCashAdju = taccbCashAdju;
    }
    
    public BigDecimal getTaccbCashAdju(){
       return this.taccbCashAdju;
    }
	
    public void setTaccb410201(BigDecimal taccb410201){
       this.taccb410201 = taccb410201;
    }
    
    public BigDecimal getTaccb410201(){
       return this.taccb410201;
    }
	
    public void setTaccb413201(BigDecimal taccb413201){
       this.taccb413201 = taccb413201;
    }
    
    public BigDecimal getTaccb413201(){
       return this.taccb413201;
    }
	
    public void setTaccb413301(BigDecimal taccb413301){
       this.taccb413301 = taccb413301;
    }
    
    public BigDecimal getTaccb413301(){
       return this.taccb413301;
    }
	
    public void setTaccb411001(BigDecimal taccb411001){
       this.taccb411001 = taccb411001;
    }
    
    public BigDecimal getTaccb411001(){
       return this.taccb411001;
    }
	
    public void setTaccb411401(BigDecimal taccb411401){
       this.taccb411401 = taccb411401;
    }
    
    public BigDecimal getTaccb411401(){
       return this.taccb411401;
    }
	
    public void setTaccb411501(BigDecimal taccb411501){
       this.taccb411501 = taccb411501;
    }
    
    public BigDecimal getTaccb411501(){
       return this.taccb411501;
    }
	
    public void setTaccb411601(BigDecimal taccb411601){
       this.taccb411601 = taccb411601;
    }
    
    public BigDecimal getTaccb411601(){
       return this.taccb411601;
    }
	
    public void setTaccb411701(BigDecimal taccb411701){
       this.taccb411701 = taccb411701;
    }
    
    public BigDecimal getTaccb411701(){
       return this.taccb411701;
    }
	
    public void setTaccb411801(BigDecimal taccb411801){
       this.taccb411801 = taccb411801;
    }
    
    public BigDecimal getTaccb411801(){
       return this.taccb411801;
    }
	
    public void setTaccb411901(BigDecimal taccb411901){
       this.taccb411901 = taccb411901;
    }
    
    public BigDecimal getTaccb411901(){
       return this.taccb411901;
    }
	
    public void setTaccb412001(BigDecimal taccb412001){
       this.taccb412001 = taccb412001;
    }
    
    public BigDecimal getTaccb412001(){
       return this.taccb412001;
    }
	
    public void setTaccb412101(BigDecimal taccb412101){
       this.taccb412101 = taccb412101;
    }
    
    public BigDecimal getTaccb412101(){
       return this.taccb412101;
    }
	
    public void setTaccb413501(BigDecimal taccb413501){
       this.taccb413501 = taccb413501;
    }
    
    public BigDecimal getTaccb413501(){
       return this.taccb413501;
    }
	
    public void setTaccb412201(BigDecimal taccb412201){
       this.taccb412201 = taccb412201;
    }
    
    public BigDecimal getTaccb412201(){
       return this.taccb412201;
    }
	
    public void setTaccb412301(BigDecimal taccb412301){
       this.taccb412301 = taccb412301;
    }
    
    public BigDecimal getTaccb412301(){
       return this.taccb412301;
    }
	
    public void setTaccb413601(BigDecimal taccb413601){
       this.taccb413601 = taccb413601;
    }
    
    public BigDecimal getTaccb413601(){
       return this.taccb413601;
    }
	
    public void setTaccb413701(BigDecimal taccb413701){
       this.taccb413701 = taccb413701;
    }
    
    public BigDecimal getTaccb413701(){
       return this.taccb413701;
    }
	
    public void setTaccb412601(BigDecimal taccb412601){
       this.taccb412601 = taccb412601;
    }
    
    public BigDecimal getTaccb412601(){
       return this.taccb412601;
    }
	
    public void setTaccb412701(BigDecimal taccb412701){
       this.taccb412701 = taccb412701;
    }
    
    public BigDecimal getTaccb412701(){
       return this.taccb412701;
    }
	
    public void setTaccb412801(BigDecimal taccb412801){
       this.taccb412801 = taccb412801;
    }
    
    public BigDecimal getTaccb412801(){
       return this.taccb412801;
    }
	
    public void setTaccb411201(BigDecimal taccb411201){
       this.taccb411201 = taccb411201;
    }
    
    public BigDecimal getTaccb411201(){
       return this.taccb411201;
    }
	
    public void setTaccb413001(BigDecimal taccb413001){
       this.taccb413001 = taccb413001;
    }
    
    public BigDecimal getTaccb413001(){
       return this.taccb413001;
    }
	
    public void setTaccbAddOperNetcashSpec(BigDecimal taccbAddOperNetcashSpec){
       this.taccbAddOperNetcashSpec = taccbAddOperNetcashSpec;
    }
    
    public BigDecimal getTaccbAddOperNetcashSpec(){
       return this.taccbAddOperNetcashSpec;
    }
	
    public void setTaccbAddOperNetcashAdju(BigDecimal taccbAddOperNetcashAdju){
       this.taccbAddOperNetcashAdju = taccbAddOperNetcashAdju;
    }
    
    public BigDecimal getTaccbAddOperNetcashAdju(){
       return this.taccbAddOperNetcashAdju;
    }
	
    public void setTaccbAddOperNetcash(BigDecimal taccbAddOperNetcash){
       this.taccbAddOperNetcash = taccbAddOperNetcash;
    }
    
    public BigDecimal getTaccbAddOperNetcash(){
       return this.taccbAddOperNetcash;
    }
	
    public void setTaccbAddOperNetcashCprt(BigDecimal taccbAddOperNetcashCprt){
       this.taccbAddOperNetcashCprt = taccbAddOperNetcashCprt;
    }
    
    public BigDecimal getTaccbAddOperNetcashCprt(){
       return this.taccbAddOperNetcashCprt;
    }
	
    public void setTaccb410501(BigDecimal taccb410501){
       this.taccb410501 = taccb410501;
    }
    
    public BigDecimal getTaccb410501(){
       return this.taccb410501;
    }
	
    public void setTaccb410801(BigDecimal taccb410801){
       this.taccb410801 = taccb410801;
    }
    
    public BigDecimal getTaccb410801(){
       return this.taccb410801;
    }
	
    public void setTaccb410802(BigDecimal taccb410802){
       this.taccb410802 = taccb410802;
    }
    
    public BigDecimal getTaccb410802(){
       return this.taccb410802;
    }
	
    public void setTaccb510101(BigDecimal taccb510101){
       this.taccb510101 = taccb510101;
    }
    
    public BigDecimal getTaccb510101(){
       return this.taccb510101;
    }
	
    public void setTaccb510201(BigDecimal taccb510201){
       this.taccb510201 = taccb510201;
    }
    
    public BigDecimal getTaccb510201(){
       return this.taccb510201;
    }
	
    public void setTaccb510301(BigDecimal taccb510301){
       this.taccb510301 = taccb510301;
    }
    
    public BigDecimal getTaccb510301(){
       return this.taccb510301;
    }
	
    public void setTaccb510401(BigDecimal taccb510401){
       this.taccb510401 = taccb510401;
    }
    
    public BigDecimal getTaccb510401(){
       return this.taccb510401;
    }
	
    public void setTaccbAddCashSpec(BigDecimal taccbAddCashSpec){
       this.taccbAddCashSpec = taccbAddCashSpec;
    }
    
    public BigDecimal getTaccbAddCashSpec(){
       return this.taccbAddCashSpec;
    }
	
    public void setTaccbAddCashAdju(BigDecimal taccbAddCashAdju){
       this.taccbAddCashAdju = taccbAddCashAdju;
    }
    
    public BigDecimal getTaccbAddCashAdju(){
       return this.taccbAddCashAdju;
    }
	
    public void setTaccbAddNetcash(BigDecimal taccbAddNetcash){
       this.taccbAddNetcash = taccbAddNetcash;
    }
    
    public BigDecimal getTaccbAddNetcash(){
       return this.taccbAddNetcash;
    }
	
    public void setTaccbNetcashCprt(BigDecimal taccbNetcashCprt){
       this.taccbNetcashCprt = taccbNetcashCprt;
    }
    
    public BigDecimal getTaccbNetcashCprt(){
       return this.taccbNetcashCprt;
    }
	
    public void setTaccbSpecDes(String taccbSpecDes){
       this.taccbSpecDes = taccbSpecDes;
    }
    
    public String getTaccbSpecDes(){
       return this.taccbSpecDes;
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
        result = prime * result + ((taccb100000 == null) ? 0 : taccb100000.hashCode());
        result = prime * result + ((taccb110000 == null) ? 0 : taccb110000.hashCode());
        result = prime * result + ((taccb110101 == null) ? 0 : taccb110101.hashCode());
        result = prime * result + ((taccb110301 == null) ? 0 : taccb110301.hashCode());
        result = prime * result + ((taccb110401 == null) ? 0 : taccb110401.hashCode());
        result = prime * result + ((taccb120000 == null) ? 0 : taccb120000.hashCode());
        result = prime * result + ((taccb120101 == null) ? 0 : taccb120101.hashCode());
        result = prime * result + ((taccb120301 == null) ? 0 : taccb120301.hashCode());
        result = prime * result + ((taccb120401 == null) ? 0 : taccb120401.hashCode());
        result = prime * result + ((taccb120501 == null) ? 0 : taccb120501.hashCode());
        result = prime * result + ((taccb200000 == null) ? 0 : taccb200000.hashCode());
        result = prime * result + ((taccb210000 == null) ? 0 : taccb210000.hashCode());
        result = prime * result + ((taccb210101 == null) ? 0 : taccb210101.hashCode());
        result = prime * result + ((taccb210201 == null) ? 0 : taccb210201.hashCode());
        result = prime * result + ((taccb210301 == null) ? 0 : taccb210301.hashCode());
        result = prime * result + ((taccb210401 == null) ? 0 : taccb210401.hashCode());
        result = prime * result + ((taccb210501 == null) ? 0 : taccb210501.hashCode());
        result = prime * result + ((taccb220000 == null) ? 0 : taccb220000.hashCode());
        result = prime * result + ((taccb220101 == null) ? 0 : taccb220101.hashCode());
        result = prime * result + ((taccb220201 == null) ? 0 : taccb220201.hashCode());
        result = prime * result + ((taccb220301 == null) ? 0 : taccb220301.hashCode());
        result = prime * result + ((taccb220401 == null) ? 0 : taccb220401.hashCode());
        result = prime * result + ((taccb220402 == null) ? 0 : taccb220402.hashCode());
        result = prime * result + ((taccb300000 == null) ? 0 : taccb300000.hashCode());
        result = prime * result + ((taccb310000 == null) ? 0 : taccb310000.hashCode());
        result = prime * result + ((taccb310111 == null) ? 0 : taccb310111.hashCode());
        result = prime * result + ((taccb310201 == null) ? 0 : taccb310201.hashCode());
        result = prime * result + ((taccb310301 == null) ? 0 : taccb310301.hashCode());
        result = prime * result + ((taccb310401 == null) ? 0 : taccb310401.hashCode());
        result = prime * result + ((taccb310501 == null) ? 0 : taccb310501.hashCode());
        result = prime * result + ((taccb320000 == null) ? 0 : taccb320000.hashCode());
        result = prime * result + ((taccb320101 == null) ? 0 : taccb320101.hashCode());
        result = prime * result + ((taccb320301 == null) ? 0 : taccb320301.hashCode());
        result = prime * result + ((taccb320701 == null) ? 0 : taccb320701.hashCode());
        result = prime * result + ((taccb320801 == null) ? 0 : taccb320801.hashCode());
        result = prime * result + ((taccb410101 == null) ? 0 : taccb410101.hashCode());
        result = prime * result + ((taccb410201 == null) ? 0 : taccb410201.hashCode());
        result = prime * result + ((taccb410501 == null) ? 0 : taccb410501.hashCode());
        result = prime * result + ((taccb410801 == null) ? 0 : taccb410801.hashCode());
        result = prime * result + ((taccb410802 == null) ? 0 : taccb410802.hashCode());
        result = prime * result + ((taccb411001 == null) ? 0 : taccb411001.hashCode());
        result = prime * result + ((taccb411201 == null) ? 0 : taccb411201.hashCode());
        result = prime * result + ((taccb411401 == null) ? 0 : taccb411401.hashCode());
        result = prime * result + ((taccb411501 == null) ? 0 : taccb411501.hashCode());
        result = prime * result + ((taccb411601 == null) ? 0 : taccb411601.hashCode());
        result = prime * result + ((taccb411701 == null) ? 0 : taccb411701.hashCode());
        result = prime * result + ((taccb411801 == null) ? 0 : taccb411801.hashCode());
        result = prime * result + ((taccb411901 == null) ? 0 : taccb411901.hashCode());
        result = prime * result + ((taccb412001 == null) ? 0 : taccb412001.hashCode());
        result = prime * result + ((taccb412101 == null) ? 0 : taccb412101.hashCode());
        result = prime * result + ((taccb412201 == null) ? 0 : taccb412201.hashCode());
        result = prime * result + ((taccb412301 == null) ? 0 : taccb412301.hashCode());
        result = prime * result + ((taccb412601 == null) ? 0 : taccb412601.hashCode());
        result = prime * result + ((taccb412701 == null) ? 0 : taccb412701.hashCode());
        result = prime * result + ((taccb412801 == null) ? 0 : taccb412801.hashCode());
        result = prime * result + ((taccb413001 == null) ? 0 : taccb413001.hashCode());
        result = prime * result + ((taccb413101 == null) ? 0 : taccb413101.hashCode());
        result = prime * result + ((taccb413201 == null) ? 0 : taccb413201.hashCode());
        result = prime * result + ((taccb413301 == null) ? 0 : taccb413301.hashCode());
        result = prime * result + ((taccb413501 == null) ? 0 : taccb413501.hashCode());
        result = prime * result + ((taccb413601 == null) ? 0 : taccb413601.hashCode());
        result = prime * result + ((taccb413701 == null) ? 0 : taccb413701.hashCode());
        result = prime * result + ((taccb510101 == null) ? 0 : taccb510101.hashCode());
        result = prime * result + ((taccb510201 == null) ? 0 : taccb510201.hashCode());
        result = prime * result + ((taccb510301 == null) ? 0 : taccb510301.hashCode());
        result = prime * result + ((taccb510401 == null) ? 0 : taccb510401.hashCode());
        result = prime * result + ((taccbAddCashAdju == null) ? 0 : taccbAddCashAdju.hashCode());
        result = prime * result + ((taccbAddCashSpec == null) ? 0 : taccbAddCashSpec.hashCode());
        result = prime * result + ((taccbAddNetcash == null) ? 0 : taccbAddNetcash.hashCode());
        result = prime * result + ((taccbAddOperNetcash == null) ? 0 : taccbAddOperNetcash.hashCode());
        result = prime * result + ((taccbAddOperNetcashAdju == null) ? 0 : taccbAddOperNetcashAdju.hashCode());
        result = prime * result + ((taccbAddOperNetcashCprt == null) ? 0 : taccbAddOperNetcashCprt.hashCode());
        result = prime * result + ((taccbAddOperNetcashSpec == null) ? 0 : taccbAddOperNetcashSpec.hashCode());
        result = prime * result + ((taccbCashAdju == null) ? 0 : taccbCashAdju.hashCode());
        result = prime * result + ((taccbFinCashinAdju == null) ? 0 : taccbFinCashinAdju.hashCode());
        result = prime * result + ((taccbFinCashinSpec == null) ? 0 : taccbFinCashinSpec.hashCode());
        result = prime * result + ((taccbFinCashoutAdju == null) ? 0 : taccbFinCashoutAdju.hashCode());
        result = prime * result + ((taccbFinCashoutSpec == null) ? 0 : taccbFinCashoutSpec.hashCode());
        result = prime * result + ((taccbFinNetCashAdju == null) ? 0 : taccbFinNetCashAdju.hashCode());
        result = prime * result + ((taccbInvesCashinAdju == null) ? 0 : taccbInvesCashinAdju.hashCode());
        result = prime * result + ((taccbInvesCashinSpec == null) ? 0 : taccbInvesCashinSpec.hashCode());
        result = prime * result + ((taccbInvesCashoutAdju == null) ? 0 : taccbInvesCashoutAdju.hashCode());
        result = prime * result + ((taccbInvesCashoutSpec == null) ? 0 : taccbInvesCashoutSpec.hashCode());
        result = prime * result + ((taccbInvesNetCashAdju == null) ? 0 : taccbInvesNetCashAdju.hashCode());
        result = prime * result + ((taccbNetcashCprt == null) ? 0 : taccbNetcashCprt.hashCode());
        result = prime * result + ((taccbOperCashinAdju == null) ? 0 : taccbOperCashinAdju.hashCode());
        result = prime * result + ((taccbOperCashinSpec == null) ? 0 : taccbOperCashinSpec.hashCode());
        result = prime * result + ((taccbOperCashoutAdju == null) ? 0 : taccbOperCashoutAdju.hashCode());
        result = prime * result + ((taccbOperCashoutSpec == null) ? 0 : taccbOperCashoutSpec.hashCode());
        result = prime * result + ((taccbOperNetCashAdju == null) ? 0 : taccbOperNetCashAdju.hashCode());
        result = prime * result + ((taccbSpecDes == null) ? 0 : taccbSpecDes.hashCode());
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
        BondFinGenCashTaccb other = (BondFinGenCashTaccb) obj;
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
        if (taccb100000 == null) {
            if (other.taccb100000 != null)
                return false;
        } else if (!taccb100000.equals(other.taccb100000))
            return false;
        if (taccb110000 == null) {
            if (other.taccb110000 != null)
                return false;
        } else if (!taccb110000.equals(other.taccb110000))
            return false;
        if (taccb110101 == null) {
            if (other.taccb110101 != null)
                return false;
        } else if (!taccb110101.equals(other.taccb110101))
            return false;
        if (taccb110301 == null) {
            if (other.taccb110301 != null)
                return false;
        } else if (!taccb110301.equals(other.taccb110301))
            return false;
        if (taccb110401 == null) {
            if (other.taccb110401 != null)
                return false;
        } else if (!taccb110401.equals(other.taccb110401))
            return false;
        if (taccb120000 == null) {
            if (other.taccb120000 != null)
                return false;
        } else if (!taccb120000.equals(other.taccb120000))
            return false;
        if (taccb120101 == null) {
            if (other.taccb120101 != null)
                return false;
        } else if (!taccb120101.equals(other.taccb120101))
            return false;
        if (taccb120301 == null) {
            if (other.taccb120301 != null)
                return false;
        } else if (!taccb120301.equals(other.taccb120301))
            return false;
        if (taccb120401 == null) {
            if (other.taccb120401 != null)
                return false;
        } else if (!taccb120401.equals(other.taccb120401))
            return false;
        if (taccb120501 == null) {
            if (other.taccb120501 != null)
                return false;
        } else if (!taccb120501.equals(other.taccb120501))
            return false;
        if (taccb200000 == null) {
            if (other.taccb200000 != null)
                return false;
        } else if (!taccb200000.equals(other.taccb200000))
            return false;
        if (taccb210000 == null) {
            if (other.taccb210000 != null)
                return false;
        } else if (!taccb210000.equals(other.taccb210000))
            return false;
        if (taccb210101 == null) {
            if (other.taccb210101 != null)
                return false;
        } else if (!taccb210101.equals(other.taccb210101))
            return false;
        if (taccb210201 == null) {
            if (other.taccb210201 != null)
                return false;
        } else if (!taccb210201.equals(other.taccb210201))
            return false;
        if (taccb210301 == null) {
            if (other.taccb210301 != null)
                return false;
        } else if (!taccb210301.equals(other.taccb210301))
            return false;
        if (taccb210401 == null) {
            if (other.taccb210401 != null)
                return false;
        } else if (!taccb210401.equals(other.taccb210401))
            return false;
        if (taccb210501 == null) {
            if (other.taccb210501 != null)
                return false;
        } else if (!taccb210501.equals(other.taccb210501))
            return false;
        if (taccb220000 == null) {
            if (other.taccb220000 != null)
                return false;
        } else if (!taccb220000.equals(other.taccb220000))
            return false;
        if (taccb220101 == null) {
            if (other.taccb220101 != null)
                return false;
        } else if (!taccb220101.equals(other.taccb220101))
            return false;
        if (taccb220201 == null) {
            if (other.taccb220201 != null)
                return false;
        } else if (!taccb220201.equals(other.taccb220201))
            return false;
        if (taccb220301 == null) {
            if (other.taccb220301 != null)
                return false;
        } else if (!taccb220301.equals(other.taccb220301))
            return false;
        if (taccb220401 == null) {
            if (other.taccb220401 != null)
                return false;
        } else if (!taccb220401.equals(other.taccb220401))
            return false;
        if (taccb220402 == null) {
            if (other.taccb220402 != null)
                return false;
        } else if (!taccb220402.equals(other.taccb220402))
            return false;
        if (taccb300000 == null) {
            if (other.taccb300000 != null)
                return false;
        } else if (!taccb300000.equals(other.taccb300000))
            return false;
        if (taccb310000 == null) {
            if (other.taccb310000 != null)
                return false;
        } else if (!taccb310000.equals(other.taccb310000))
            return false;
        if (taccb310111 == null) {
            if (other.taccb310111 != null)
                return false;
        } else if (!taccb310111.equals(other.taccb310111))
            return false;
        if (taccb310201 == null) {
            if (other.taccb310201 != null)
                return false;
        } else if (!taccb310201.equals(other.taccb310201))
            return false;
        if (taccb310301 == null) {
            if (other.taccb310301 != null)
                return false;
        } else if (!taccb310301.equals(other.taccb310301))
            return false;
        if (taccb310401 == null) {
            if (other.taccb310401 != null)
                return false;
        } else if (!taccb310401.equals(other.taccb310401))
            return false;
        if (taccb310501 == null) {
            if (other.taccb310501 != null)
                return false;
        } else if (!taccb310501.equals(other.taccb310501))
            return false;
        if (taccb320000 == null) {
            if (other.taccb320000 != null)
                return false;
        } else if (!taccb320000.equals(other.taccb320000))
            return false;
        if (taccb320101 == null) {
            if (other.taccb320101 != null)
                return false;
        } else if (!taccb320101.equals(other.taccb320101))
            return false;
        if (taccb320301 == null) {
            if (other.taccb320301 != null)
                return false;
        } else if (!taccb320301.equals(other.taccb320301))
            return false;
        if (taccb320701 == null) {
            if (other.taccb320701 != null)
                return false;
        } else if (!taccb320701.equals(other.taccb320701))
            return false;
        if (taccb320801 == null) {
            if (other.taccb320801 != null)
                return false;
        } else if (!taccb320801.equals(other.taccb320801))
            return false;
        if (taccb410101 == null) {
            if (other.taccb410101 != null)
                return false;
        } else if (!taccb410101.equals(other.taccb410101))
            return false;
        if (taccb410201 == null) {
            if (other.taccb410201 != null)
                return false;
        } else if (!taccb410201.equals(other.taccb410201))
            return false;
        if (taccb410501 == null) {
            if (other.taccb410501 != null)
                return false;
        } else if (!taccb410501.equals(other.taccb410501))
            return false;
        if (taccb410801 == null) {
            if (other.taccb410801 != null)
                return false;
        } else if (!taccb410801.equals(other.taccb410801))
            return false;
        if (taccb410802 == null) {
            if (other.taccb410802 != null)
                return false;
        } else if (!taccb410802.equals(other.taccb410802))
            return false;
        if (taccb411001 == null) {
            if (other.taccb411001 != null)
                return false;
        } else if (!taccb411001.equals(other.taccb411001))
            return false;
        if (taccb411201 == null) {
            if (other.taccb411201 != null)
                return false;
        } else if (!taccb411201.equals(other.taccb411201))
            return false;
        if (taccb411401 == null) {
            if (other.taccb411401 != null)
                return false;
        } else if (!taccb411401.equals(other.taccb411401))
            return false;
        if (taccb411501 == null) {
            if (other.taccb411501 != null)
                return false;
        } else if (!taccb411501.equals(other.taccb411501))
            return false;
        if (taccb411601 == null) {
            if (other.taccb411601 != null)
                return false;
        } else if (!taccb411601.equals(other.taccb411601))
            return false;
        if (taccb411701 == null) {
            if (other.taccb411701 != null)
                return false;
        } else if (!taccb411701.equals(other.taccb411701))
            return false;
        if (taccb411801 == null) {
            if (other.taccb411801 != null)
                return false;
        } else if (!taccb411801.equals(other.taccb411801))
            return false;
        if (taccb411901 == null) {
            if (other.taccb411901 != null)
                return false;
        } else if (!taccb411901.equals(other.taccb411901))
            return false;
        if (taccb412001 == null) {
            if (other.taccb412001 != null)
                return false;
        } else if (!taccb412001.equals(other.taccb412001))
            return false;
        if (taccb412101 == null) {
            if (other.taccb412101 != null)
                return false;
        } else if (!taccb412101.equals(other.taccb412101))
            return false;
        if (taccb412201 == null) {
            if (other.taccb412201 != null)
                return false;
        } else if (!taccb412201.equals(other.taccb412201))
            return false;
        if (taccb412301 == null) {
            if (other.taccb412301 != null)
                return false;
        } else if (!taccb412301.equals(other.taccb412301))
            return false;
        if (taccb412601 == null) {
            if (other.taccb412601 != null)
                return false;
        } else if (!taccb412601.equals(other.taccb412601))
            return false;
        if (taccb412701 == null) {
            if (other.taccb412701 != null)
                return false;
        } else if (!taccb412701.equals(other.taccb412701))
            return false;
        if (taccb412801 == null) {
            if (other.taccb412801 != null)
                return false;
        } else if (!taccb412801.equals(other.taccb412801))
            return false;
        if (taccb413001 == null) {
            if (other.taccb413001 != null)
                return false;
        } else if (!taccb413001.equals(other.taccb413001))
            return false;
        if (taccb413101 == null) {
            if (other.taccb413101 != null)
                return false;
        } else if (!taccb413101.equals(other.taccb413101))
            return false;
        if (taccb413201 == null) {
            if (other.taccb413201 != null)
                return false;
        } else if (!taccb413201.equals(other.taccb413201))
            return false;
        if (taccb413301 == null) {
            if (other.taccb413301 != null)
                return false;
        } else if (!taccb413301.equals(other.taccb413301))
            return false;
        if (taccb413501 == null) {
            if (other.taccb413501 != null)
                return false;
        } else if (!taccb413501.equals(other.taccb413501))
            return false;
        if (taccb413601 == null) {
            if (other.taccb413601 != null)
                return false;
        } else if (!taccb413601.equals(other.taccb413601))
            return false;
        if (taccb413701 == null) {
            if (other.taccb413701 != null)
                return false;
        } else if (!taccb413701.equals(other.taccb413701))
            return false;
        if (taccb510101 == null) {
            if (other.taccb510101 != null)
                return false;
        } else if (!taccb510101.equals(other.taccb510101))
            return false;
        if (taccb510201 == null) {
            if (other.taccb510201 != null)
                return false;
        } else if (!taccb510201.equals(other.taccb510201))
            return false;
        if (taccb510301 == null) {
            if (other.taccb510301 != null)
                return false;
        } else if (!taccb510301.equals(other.taccb510301))
            return false;
        if (taccb510401 == null) {
            if (other.taccb510401 != null)
                return false;
        } else if (!taccb510401.equals(other.taccb510401))
            return false;
        if (taccbAddCashAdju == null) {
            if (other.taccbAddCashAdju != null)
                return false;
        } else if (!taccbAddCashAdju.equals(other.taccbAddCashAdju))
            return false;
        if (taccbAddCashSpec == null) {
            if (other.taccbAddCashSpec != null)
                return false;
        } else if (!taccbAddCashSpec.equals(other.taccbAddCashSpec))
            return false;
        if (taccbAddNetcash == null) {
            if (other.taccbAddNetcash != null)
                return false;
        } else if (!taccbAddNetcash.equals(other.taccbAddNetcash))
            return false;
        if (taccbAddOperNetcash == null) {
            if (other.taccbAddOperNetcash != null)
                return false;
        } else if (!taccbAddOperNetcash.equals(other.taccbAddOperNetcash))
            return false;
        if (taccbAddOperNetcashAdju == null) {
            if (other.taccbAddOperNetcashAdju != null)
                return false;
        } else if (!taccbAddOperNetcashAdju.equals(other.taccbAddOperNetcashAdju))
            return false;
        if (taccbAddOperNetcashCprt == null) {
            if (other.taccbAddOperNetcashCprt != null)
                return false;
        } else if (!taccbAddOperNetcashCprt.equals(other.taccbAddOperNetcashCprt))
            return false;
        if (taccbAddOperNetcashSpec == null) {
            if (other.taccbAddOperNetcashSpec != null)
                return false;
        } else if (!taccbAddOperNetcashSpec.equals(other.taccbAddOperNetcashSpec))
            return false;
        if (taccbCashAdju == null) {
            if (other.taccbCashAdju != null)
                return false;
        } else if (!taccbCashAdju.equals(other.taccbCashAdju))
            return false;
        if (taccbFinCashinAdju == null) {
            if (other.taccbFinCashinAdju != null)
                return false;
        } else if (!taccbFinCashinAdju.equals(other.taccbFinCashinAdju))
            return false;
        if (taccbFinCashinSpec == null) {
            if (other.taccbFinCashinSpec != null)
                return false;
        } else if (!taccbFinCashinSpec.equals(other.taccbFinCashinSpec))
            return false;
        if (taccbFinCashoutAdju == null) {
            if (other.taccbFinCashoutAdju != null)
                return false;
        } else if (!taccbFinCashoutAdju.equals(other.taccbFinCashoutAdju))
            return false;
        if (taccbFinCashoutSpec == null) {
            if (other.taccbFinCashoutSpec != null)
                return false;
        } else if (!taccbFinCashoutSpec.equals(other.taccbFinCashoutSpec))
            return false;
        if (taccbFinNetCashAdju == null) {
            if (other.taccbFinNetCashAdju != null)
                return false;
        } else if (!taccbFinNetCashAdju.equals(other.taccbFinNetCashAdju))
            return false;
        if (taccbInvesCashinAdju == null) {
            if (other.taccbInvesCashinAdju != null)
                return false;
        } else if (!taccbInvesCashinAdju.equals(other.taccbInvesCashinAdju))
            return false;
        if (taccbInvesCashinSpec == null) {
            if (other.taccbInvesCashinSpec != null)
                return false;
        } else if (!taccbInvesCashinSpec.equals(other.taccbInvesCashinSpec))
            return false;
        if (taccbInvesCashoutAdju == null) {
            if (other.taccbInvesCashoutAdju != null)
                return false;
        } else if (!taccbInvesCashoutAdju.equals(other.taccbInvesCashoutAdju))
            return false;
        if (taccbInvesCashoutSpec == null) {
            if (other.taccbInvesCashoutSpec != null)
                return false;
        } else if (!taccbInvesCashoutSpec.equals(other.taccbInvesCashoutSpec))
            return false;
        if (taccbInvesNetCashAdju == null) {
            if (other.taccbInvesNetCashAdju != null)
                return false;
        } else if (!taccbInvesNetCashAdju.equals(other.taccbInvesNetCashAdju))
            return false;
        if (taccbNetcashCprt == null) {
            if (other.taccbNetcashCprt != null)
                return false;
        } else if (!taccbNetcashCprt.equals(other.taccbNetcashCprt))
            return false;
        if (taccbOperCashinAdju == null) {
            if (other.taccbOperCashinAdju != null)
                return false;
        } else if (!taccbOperCashinAdju.equals(other.taccbOperCashinAdju))
            return false;
        if (taccbOperCashinSpec == null) {
            if (other.taccbOperCashinSpec != null)
                return false;
        } else if (!taccbOperCashinSpec.equals(other.taccbOperCashinSpec))
            return false;
        if (taccbOperCashoutAdju == null) {
            if (other.taccbOperCashoutAdju != null)
                return false;
        } else if (!taccbOperCashoutAdju.equals(other.taccbOperCashoutAdju))
            return false;
        if (taccbOperCashoutSpec == null) {
            if (other.taccbOperCashoutSpec != null)
                return false;
        } else if (!taccbOperCashoutSpec.equals(other.taccbOperCashoutSpec))
            return false;
        if (taccbOperNetCashAdju == null) {
            if (other.taccbOperNetCashAdju != null)
                return false;
        } else if (!taccbOperNetCashAdju.equals(other.taccbOperNetCashAdju))
            return false;
        if (taccbSpecDes == null) {
            if (other.taccbSpecDes != null)
                return false;
        } else if (!taccbSpecDes.equals(other.taccbSpecDes))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BondFinGenCashTaccb [" + (id != null ? "id=" + id + ", " : "")
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
                + (taccb110101 != null ? "taccb110101=" + taccb110101 + ", " : "")
                + (taccb110301 != null ? "taccb110301=" + taccb110301 + ", " : "")
                + (taccb110401 != null ? "taccb110401=" + taccb110401 + ", " : "")
                + (taccbOperCashinSpec != null ? "taccbOperCashinSpec=" + taccbOperCashinSpec + ", " : "")
                + (taccbOperCashinAdju != null ? "taccbOperCashinAdju=" + taccbOperCashinAdju + ", " : "")
                + (taccb110000 != null ? "taccb110000=" + taccb110000 + ", " : "")
                + (taccb120101 != null ? "taccb120101=" + taccb120101 + ", " : "")
                + (taccb120301 != null ? "taccb120301=" + taccb120301 + ", " : "")
                + (taccb120401 != null ? "taccb120401=" + taccb120401 + ", " : "")
                + (taccb120501 != null ? "taccb120501=" + taccb120501 + ", " : "")
                + (taccbOperCashoutSpec != null ? "taccbOperCashoutSpec=" + taccbOperCashoutSpec + ", " : "")
                + (taccbOperCashoutAdju != null ? "taccbOperCashoutAdju=" + taccbOperCashoutAdju + ", " : "")
                + (taccb120000 != null ? "taccb120000=" + taccb120000 + ", " : "")
                + (taccbOperNetCashAdju != null ? "taccbOperNetCashAdju=" + taccbOperNetCashAdju + ", " : "")
                + (taccb100000 != null ? "taccb100000=" + taccb100000 + ", " : "")
                + (taccb210101 != null ? "taccb210101=" + taccb210101 + ", " : "")
                + (taccb210201 != null ? "taccb210201=" + taccb210201 + ", " : "")
                + (taccb210301 != null ? "taccb210301=" + taccb210301 + ", " : "")
                + (taccb210501 != null ? "taccb210501=" + taccb210501 + ", " : "")
                + (taccb210401 != null ? "taccb210401=" + taccb210401 + ", " : "")
                + (taccbInvesCashinSpec != null ? "taccbInvesCashinSpec=" + taccbInvesCashinSpec + ", " : "")
                + (taccbInvesCashinAdju != null ? "taccbInvesCashinAdju=" + taccbInvesCashinAdju + ", " : "")
                + (taccb210000 != null ? "taccb210000=" + taccb210000 + ", " : "")
                + (taccb220101 != null ? "taccb220101=" + taccb220101 + ", " : "")
                + (taccb220201 != null ? "taccb220201=" + taccb220201 + ", " : "")
                + (taccb220401 != null ? "taccb220401=" + taccb220401 + ", " : "")
                + (taccb220402 != null ? "taccb220402=" + taccb220402 + ", " : "")
                + (taccb220301 != null ? "taccb220301=" + taccb220301 + ", " : "")
                + (taccbInvesCashoutSpec != null ? "taccbInvesCashoutSpec=" + taccbInvesCashoutSpec + ", " : "")
                + (taccbInvesCashoutAdju != null ? "taccbInvesCashoutAdju=" + taccbInvesCashoutAdju + ", " : "")
                + (taccb220000 != null ? "taccb220000=" + taccb220000 + ", " : "")
                + (taccbInvesNetCashAdju != null ? "taccbInvesNetCashAdju=" + taccbInvesNetCashAdju + ", " : "")
                + (taccb200000 != null ? "taccb200000=" + taccb200000 + ", " : "")
                + (taccb310301 != null ? "taccb310301=" + taccb310301 + ", " : "")
                + (taccb310111 != null ? "taccb310111=" + taccb310111 + ", " : "")
                + (taccb310201 != null ? "taccb310201=" + taccb310201 + ", " : "")
                + (taccb310401 != null ? "taccb310401=" + taccb310401 + ", " : "")
                + (taccb310501 != null ? "taccb310501=" + taccb310501 + ", " : "")
                + (taccbFinCashinSpec != null ? "taccbFinCashinSpec=" + taccbFinCashinSpec + ", " : "")
                + (taccbFinCashinAdju != null ? "taccbFinCashinAdju=" + taccbFinCashinAdju + ", " : "")
                + (taccb310000 != null ? "taccb310000=" + taccb310000 + ", " : "")
                + (taccb320101 != null ? "taccb320101=" + taccb320101 + ", " : "")
                + (taccb320301 != null ? "taccb320301=" + taccb320301 + ", " : "")
                + (taccb320801 != null ? "taccb320801=" + taccb320801 + ", " : "")
                + (taccb320701 != null ? "taccb320701=" + taccb320701 + ", " : "")
                + (taccbFinCashoutSpec != null ? "taccbFinCashoutSpec=" + taccbFinCashoutSpec + ", " : "")
                + (taccbFinCashoutAdju != null ? "taccbFinCashoutAdju=" + taccbFinCashoutAdju + ", " : "")
                + (taccb320000 != null ? "taccb320000=" + taccb320000 + ", " : "")
                + (taccbFinNetCashAdju != null ? "taccbFinNetCashAdju=" + taccbFinNetCashAdju + ", " : "")
                + (taccb300000 != null ? "taccb300000=" + taccb300000 + ", " : "")
                + (taccb410101 != null ? "taccb410101=" + taccb410101 + ", " : "")
                + (taccb413101 != null ? "taccb413101=" + taccb413101 + ", " : "")
                + (taccbCashAdju != null ? "taccbCashAdju=" + taccbCashAdju + ", " : "")
                + (taccb410201 != null ? "taccb410201=" + taccb410201 + ", " : "")
                + (taccb413201 != null ? "taccb413201=" + taccb413201 + ", " : "")
                + (taccb413301 != null ? "taccb413301=" + taccb413301 + ", " : "")
                + (taccb411001 != null ? "taccb411001=" + taccb411001 + ", " : "")
                + (taccb411401 != null ? "taccb411401=" + taccb411401 + ", " : "")
                + (taccb411501 != null ? "taccb411501=" + taccb411501 + ", " : "")
                + (taccb411601 != null ? "taccb411601=" + taccb411601 + ", " : "")
                + (taccb411701 != null ? "taccb411701=" + taccb411701 + ", " : "")
                + (taccb411801 != null ? "taccb411801=" + taccb411801 + ", " : "")
                + (taccb411901 != null ? "taccb411901=" + taccb411901 + ", " : "")
                + (taccb412001 != null ? "taccb412001=" + taccb412001 + ", " : "")
                + (taccb412101 != null ? "taccb412101=" + taccb412101 + ", " : "")
                + (taccb413501 != null ? "taccb413501=" + taccb413501 + ", " : "")
                + (taccb412201 != null ? "taccb412201=" + taccb412201 + ", " : "")
                + (taccb412301 != null ? "taccb412301=" + taccb412301 + ", " : "")
                + (taccb413601 != null ? "taccb413601=" + taccb413601 + ", " : "")
                + (taccb413701 != null ? "taccb413701=" + taccb413701 + ", " : "")
                + (taccb412601 != null ? "taccb412601=" + taccb412601 + ", " : "")
                + (taccb412701 != null ? "taccb412701=" + taccb412701 + ", " : "")
                + (taccb412801 != null ? "taccb412801=" + taccb412801 + ", " : "")
                + (taccb411201 != null ? "taccb411201=" + taccb411201 + ", " : "")
                + (taccb413001 != null ? "taccb413001=" + taccb413001 + ", " : "")
                + (taccbAddOperNetcashSpec != null ? "taccbAddOperNetcashSpec=" + taccbAddOperNetcashSpec + ", " : "")
                + (taccbAddOperNetcashAdju != null ? "taccbAddOperNetcashAdju=" + taccbAddOperNetcashAdju + ", " : "")
                + (taccbAddOperNetcash != null ? "taccbAddOperNetcash=" + taccbAddOperNetcash + ", " : "")
                + (taccbAddOperNetcashCprt != null ? "taccbAddOperNetcashCprt=" + taccbAddOperNetcashCprt + ", " : "")
                + (taccb410501 != null ? "taccb410501=" + taccb410501 + ", " : "")
                + (taccb410801 != null ? "taccb410801=" + taccb410801 + ", " : "")
                + (taccb410802 != null ? "taccb410802=" + taccb410802 + ", " : "")
                + (taccb510101 != null ? "taccb510101=" + taccb510101 + ", " : "")
                + (taccb510201 != null ? "taccb510201=" + taccb510201 + ", " : "")
                + (taccb510301 != null ? "taccb510301=" + taccb510301 + ", " : "")
                + (taccb510401 != null ? "taccb510401=" + taccb510401 + ", " : "")
                + (taccbAddCashSpec != null ? "taccbAddCashSpec=" + taccbAddCashSpec + ", " : "")
                + (taccbAddCashAdju != null ? "taccbAddCashAdju=" + taccbAddCashAdju + ", " : "")
                + (taccbAddNetcash != null ? "taccbAddNetcash=" + taccbAddNetcash + ", " : "")
                + (taccbNetcashCprt != null ? "taccbNetcashCprt=" + taccbNetcashCprt + ", " : "")
                + (taccbSpecDes != null ? "taccbSpecDes=" + taccbSpecDes + ", " : "")
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
        selectSql += ",TACCB_110101";
        selectSql += ",TACCB_110301";
        selectSql += ",TACCB_110401";
        selectSql += ",TACCB_OPER_CASHIN_SPEC";
        selectSql += ",TACCB_OPER_CASHIN_ADJU";
        selectSql += ",TACCB_110000";
        selectSql += ",TACCB_120101";
        selectSql += ",TACCB_120301";
        selectSql += ",TACCB_120401";
        selectSql += ",TACCB_120501";
        selectSql += ",TACCB_OPER_CASHOUT_SPEC";
        selectSql += ",TACCB_OPER_CASHOUT_ADJU";
        selectSql += ",TACCB_120000";
        selectSql += ",TACCB_OPER_NET_CASH_ADJU";
        selectSql += ",TACCB_100000";
        selectSql += ",TACCB_210101";
        selectSql += ",TACCB_210201";
        selectSql += ",TACCB_210301";
        selectSql += ",TACCB_210501";
        selectSql += ",TACCB_210401";
        selectSql += ",TACCB_INVES_CASHIN_SPEC";
        selectSql += ",TACCB_INVES_CASHIN_ADJU";
        selectSql += ",TACCB_210000";
        selectSql += ",TACCB_220101";
        selectSql += ",TACCB_220201";
        selectSql += ",TACCB_220401";
        selectSql += ",TACCB_220402";
        selectSql += ",TACCB_220301";
        selectSql += ",TACCB_INVES_CASHOUT_SPEC";
        selectSql += ",TACCB_INVES_CASHOUT_ADJU";
        selectSql += ",TACCB_220000";
        selectSql += ",TACCB_INVES_NET_CASH_ADJU";
        selectSql += ",TACCB_200000";
        selectSql += ",TACCB_310301";
        selectSql += ",TACCB_310111";
        selectSql += ",TACCB_310201";
        selectSql += ",TACCB_310401";
        selectSql += ",TACCB_310501";
        selectSql += ",TACCB_FIN_CASHIN_SPEC";
        selectSql += ",TACCB_FIN_CASHIN_ADJU";
        selectSql += ",TACCB_310000";
        selectSql += ",TACCB_320101";
        selectSql += ",TACCB_320301";
        selectSql += ",TACCB_320801";
        selectSql += ",TACCB_320701";
        selectSql += ",TACCB_FIN_CASHOUT_SPEC";
        selectSql += ",TACCB_FIN_CASHOUT_ADJU";
        selectSql += ",TACCB_320000";
        selectSql += ",TACCB_FIN_NET_CASH_ADJU";
        selectSql += ",TACCB_300000";
        selectSql += ",TACCB_410101";
        selectSql += ",TACCB_413101";
        selectSql += ",TACCB_CASH_ADJU";
        selectSql += ",TACCB_410201";
        selectSql += ",TACCB_413201";
        selectSql += ",TACCB_413301";
        selectSql += ",TACCB_411001";
        selectSql += ",TACCB_411401";
        selectSql += ",TACCB_411501";
        selectSql += ",TACCB_411601";
        selectSql += ",TACCB_411701";
        selectSql += ",TACCB_411801";
        selectSql += ",TACCB_411901";
        selectSql += ",TACCB_412001";
        selectSql += ",TACCB_412101";
        selectSql += ",TACCB_413501";
        selectSql += ",TACCB_412201";
        selectSql += ",TACCB_412301";
        selectSql += ",TACCB_413601";
        selectSql += ",TACCB_413701";
        selectSql += ",TACCB_412601";
        selectSql += ",TACCB_412701";
        selectSql += ",TACCB_412801";
        selectSql += ",TACCB_411201";
        selectSql += ",TACCB_413001";
        selectSql += ",TACCB_ADD_OPER_NETCASH_SPEC";
        selectSql += ",TACCB_ADD_OPER_NETCASH_ADJU";
        selectSql += ",TACCB_ADD_OPER_NETCASH";
        selectSql += ",TACCB_ADD_OPER_NETCASH_CPRT";
        selectSql += ",TACCB_410501";
        selectSql += ",TACCB_410801";
        selectSql += ",TACCB_410802";
        selectSql += ",TACCB_510101";
        selectSql += ",TACCB_510201";
        selectSql += ",TACCB_510301";
        selectSql += ",TACCB_510401";
        selectSql += ",TACCB_ADD_CASH_SPEC";
        selectSql += ",TACCB_ADD_CASH_ADJU";
        selectSql += ",TACCB_ADD_NETCASH";
        selectSql += ",TACCB_NETCASH_CPRT";
        selectSql += ",TACCB_SPEC_DES";
        selectSql += ",INFO_PUB_DATE";
 	   return selectSql.substring(1);
     }
 	
 	public String createEqualsSql(String whereSql){
 	   String selectSql = createSelectColumnSql();       
        String sql = "select count(1) from "
        +" (select * from (select "+selectSql+" from bond_ccxe.d_bond_fin_gen_cash_taccb where "+whereSql
        +" order by ccxeid desc limit 1) a union select * from (select "+selectSql+" from bond_ccxe_snapshot.d_bond_fin_gen_cash_taccb where "+whereSql+" order by ccxeid desc limit 1) a) a";
        return sql;
     }
     
     public String createEqualsSql(String id, Long bondUniCode, Long comUniCode, String endDate){
 	   String selectSql = createSelectColumnSql();
 	  String whereSql = " id='" + id + "' and bond_uni_code=" + bondUniCode 
 	         + " and com_uni_code=" + comUniCode 
 	         + " and end_date='" + endDate + "'";
                
        String sql = "select count(1) from "
        +" (select * from (select "+selectSql+" from bond_ccxe.d_bond_fin_gen_cash_taccb where "+whereSql
        +" order by ccxeid desc limit 1) a union select * from (select "+selectSql+" from bond_ccxe_snapshot.d_bond_fin_gen_cash_taccb where "+whereSql+" order by ccxeid desc limit 1) a) a";
        return sql;
     }
}
