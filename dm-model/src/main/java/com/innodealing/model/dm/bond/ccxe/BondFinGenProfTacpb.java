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
@Table(name="d_bond_fin_gen_prof_tacpb")
public class BondFinGenProfTacpb implements Serializable{
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
    @Column(name="TACPB_110100", length=20)
    private BigDecimal tacpb110100;
    
	/**
	 * 
	 */
    @Column(name="TACPB_110101", length=20)
    private BigDecimal tacpb110101;
    
	/**
	 * 
	 */
    @Column(name="TACPB_110131", length=20)
    private BigDecimal tacpb110131;
    
	/**
	 * 
	 */
    @Column(name="TACPB_11013101", length=20)
    private BigDecimal tacpb11013101;
    
	/**
	 * 
	 */
    @Column(name="TACPB_11013102", length=20)
    private BigDecimal tacpb11013102;
    
	/**
	 * 
	 */
    @Column(name="TACPB_11013103", length=20)
    private BigDecimal tacpb11013103;
    
	/**
	 * 
	 */
    @Column(name="TACPB_110200", length=20)
    private BigDecimal tacpb110200;
    
	/**
	 * 
	 */
    @Column(name="TACPB_110202", length=20)
    private BigDecimal tacpb110202;
    
	/**
	 * 
	 */
    @Column(name="TACPB_110402", length=20)
    private BigDecimal tacpb110402;
    
	/**
	 * 
	 */
    @Column(name="TACPB_11040201", length=20)
    private BigDecimal tacpb11040201;
    
	/**
	 * 
	 */
    @Column(name="TACPB_11040202", length=20)
    private BigDecimal tacpb11040202;
    
	/**
	 * 
	 */
    @Column(name="TACPB_110302", length=20)
    private BigDecimal tacpb110302;
    
	/**
	 * 
	 */
    @Column(name="TACPB_120442", length=20)
    private BigDecimal tacpb120442;
    
	/**
	 * 
	 */
    @Column(name="TACPB_120422", length=20)
    private BigDecimal tacpb120422;
    
	/**
	 * 
	 */
    @Column(name="TACPB_120432", length=20)
    private BigDecimal tacpb120432;
    
	/**
	 * 
	 */
    @Column(name="TACPB_131102", length=20)
    private BigDecimal tacpb131102;
    
	/**
	 * 
	 */
    @Column(name="TACPB_131103", length=20)
    private BigDecimal tacpb131103;
    
	/**
	 * 
	 */
    @Column(name="TACPB_131104", length=20)
    private BigDecimal tacpb131104;
    
	/**
	 * 
	 */
    @Column(name="TACPB_131105", length=20)
    private BigDecimal tacpb131105;
    
	/**
	 * 
	 */
    @Column(name="TACPB_131106", length=20)
    private BigDecimal tacpb131106;
    
	/**
	 * 
	 */
    @Column(name="TACPB_131107", length=20)
    private BigDecimal tacpb131107;
    
	/**
	 * 
	 */
    @Column(name="TACPB_OPER_COST_SPEC", length=20)
    private BigDecimal tacpbOperCostSpec;
    
	/**
	 * 
	 */
    @Column(name="TACPB_OPER_COST_ADJU", length=20)
    private BigDecimal tacpbOperCostAdju;
    
	/**
	 * 
	 */
    @Column(name="TACPB_110300", length=20)
    private BigDecimal tacpb110300;
    
	/**
	 * 
	 */
    @Column(name="TACPB_131201", length=20)
    private BigDecimal tacpb131201;
    
	/**
	 * 
	 */
    @Column(name="TACPB_130201", length=20)
    private BigDecimal tacpb130201;
    
	/**
	 * 
	 */
    @Column(name="TACPB_130211", length=20)
    private BigDecimal tacpb130211;
    
	/**
	 * 
	 */
    @Column(name="TACPB_120601", length=20)
    private BigDecimal tacpb120601;
    
	/**
	 * 
	 */
    @Column(name="TACPB_NOPER_INC_SPEC", length=20)
    private BigDecimal tacpbNoperIncSpec;
    
	/**
	 * 
	 */
    @Column(name="TACPB_NOPER_INC_ADJU", length=20)
    private BigDecimal tacpbNoperIncAdju;
    
	/**
	 * 
	 */
    @Column(name="TACPB_130101", length=20)
    private BigDecimal tacpb130101;
    
	/**
	 * 
	 */
    @Column(name="TACPB_130501", length=20)
    private BigDecimal tacpb130501;
    
	/**
	 * 
	 */
    @Column(name="TACPB_130702", length=20)
    private BigDecimal tacpb130702;
    
	/**
	 * 
	 */
    @Column(name="TACPB_130712", length=20)
    private BigDecimal tacpb130712;
    
	/**
	 * 
	 */
    @Column(name="TACPB_131401", length=20)
    private BigDecimal tacpb131401;
    
	/**
	 * 
	 */
    @Column(name="TACPB_PROF_ADJU", length=20)
    private BigDecimal tacpbProfAdju;
    
	/**
	 * 
	 */
    @Column(name="TACPB_140101", length=20)
    private BigDecimal tacpb140101;
    
	/**
	 * 
	 */
    @Column(name="TACPB_140202", length=20)
    private BigDecimal tacpb140202;
    
	/**
	 * 
	 */
    @Column(name="TACPB_140801", length=20)
    private BigDecimal tacpb140801;
    
	/**
	 * 
	 */
    @Column(name="TACPB_NET_PROF_SPEC", length=20)
    private BigDecimal tacpbNetProfSpec;
    
	/**
	 * 
	 */
    @Column(name="TACPB_NET_PROF_ADJU", length=20)
    private BigDecimal tacpbNetProfAdju;
    
	/**
	 * 
	 */
    @Column(name="TACPB_150101", length=20)
    private BigDecimal tacpb150101;
    
	/**
	 * 
	 */
    @Column(name="TACPB_160101", length=20)
    private BigDecimal tacpb160101;
    
	/**
	 * 
	 */
    @Column(name="TACPB_140302", length=20)
    private BigDecimal tacpb140302;
    
	/**
	 * 
	 */
    @Column(name="TACPB_140303", length=20)
    private BigDecimal tacpb140303;
    
	/**
	 * 
	 */
    @Column(name="TACPB_INC_ADJU", length=20)
    private BigDecimal tacpbIncAdju;
    
	/**
	 * 
	 */
    @Column(name="TACPB_170101", length=20)
    private BigDecimal tacpb170101;
    
	/**
	 * 
	 */
    @Column(name="TACPB_170102", length=20)
    private BigDecimal tacpb170102;
    
	/**
	 * 
	 */
    @Column(name="TACPB_170103", length=20)
    private BigDecimal tacpb170103;
    
	/**
	 * 
	 */
    @Column(name="TACPB_PARE_COM_INC_ADJU", length=20)
    private BigDecimal tacpbPareComIncAdju;
    
	/**
	 * 
	 */
    @Column(name="TACPB_240801", length=20)
    private BigDecimal tacpb240801;
    
	/**
	 * 
	 */
    @Column(name="TACPB_240901", length=20)
    private BigDecimal tacpb240901;
    
	/**
	 * 
	 */
    @Column(name="TACPB_SPEC_DES", length=16777215)
    private String tacpbSpecDes;
    
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
	
    public void setTacpb110100(BigDecimal tacpb110100){
       this.tacpb110100 = tacpb110100;
    }
    
    public BigDecimal getTacpb110100(){
       return this.tacpb110100;
    }
	
    public void setTacpb110101(BigDecimal tacpb110101){
       this.tacpb110101 = tacpb110101;
    }
    
    public BigDecimal getTacpb110101(){
       return this.tacpb110101;
    }
	
    public void setTacpb110131(BigDecimal tacpb110131){
       this.tacpb110131 = tacpb110131;
    }
    
    public BigDecimal getTacpb110131(){
       return this.tacpb110131;
    }
	
    public void setTacpb11013101(BigDecimal tacpb11013101){
       this.tacpb11013101 = tacpb11013101;
    }
    
    public BigDecimal getTacpb11013101(){
       return this.tacpb11013101;
    }
	
    public void setTacpb11013102(BigDecimal tacpb11013102){
       this.tacpb11013102 = tacpb11013102;
    }
    
    public BigDecimal getTacpb11013102(){
       return this.tacpb11013102;
    }
	
    public void setTacpb11013103(BigDecimal tacpb11013103){
       this.tacpb11013103 = tacpb11013103;
    }
    
    public BigDecimal getTacpb11013103(){
       return this.tacpb11013103;
    }
	
    public void setTacpb110200(BigDecimal tacpb110200){
       this.tacpb110200 = tacpb110200;
    }
    
    public BigDecimal getTacpb110200(){
       return this.tacpb110200;
    }
	
    public void setTacpb110202(BigDecimal tacpb110202){
       this.tacpb110202 = tacpb110202;
    }
    
    public BigDecimal getTacpb110202(){
       return this.tacpb110202;
    }
	
    public void setTacpb110402(BigDecimal tacpb110402){
       this.tacpb110402 = tacpb110402;
    }
    
    public BigDecimal getTacpb110402(){
       return this.tacpb110402;
    }
	
    public void setTacpb11040201(BigDecimal tacpb11040201){
       this.tacpb11040201 = tacpb11040201;
    }
    
    public BigDecimal getTacpb11040201(){
       return this.tacpb11040201;
    }
	
    public void setTacpb11040202(BigDecimal tacpb11040202){
       this.tacpb11040202 = tacpb11040202;
    }
    
    public BigDecimal getTacpb11040202(){
       return this.tacpb11040202;
    }
	
    public void setTacpb110302(BigDecimal tacpb110302){
       this.tacpb110302 = tacpb110302;
    }
    
    public BigDecimal getTacpb110302(){
       return this.tacpb110302;
    }
	
    public void setTacpb120442(BigDecimal tacpb120442){
       this.tacpb120442 = tacpb120442;
    }
    
    public BigDecimal getTacpb120442(){
       return this.tacpb120442;
    }
	
    public void setTacpb120422(BigDecimal tacpb120422){
       this.tacpb120422 = tacpb120422;
    }
    
    public BigDecimal getTacpb120422(){
       return this.tacpb120422;
    }
	
    public void setTacpb120432(BigDecimal tacpb120432){
       this.tacpb120432 = tacpb120432;
    }
    
    public BigDecimal getTacpb120432(){
       return this.tacpb120432;
    }
	
    public void setTacpb131102(BigDecimal tacpb131102){
       this.tacpb131102 = tacpb131102;
    }
    
    public BigDecimal getTacpb131102(){
       return this.tacpb131102;
    }
	
    public void setTacpb131103(BigDecimal tacpb131103){
       this.tacpb131103 = tacpb131103;
    }
    
    public BigDecimal getTacpb131103(){
       return this.tacpb131103;
    }
	
    public void setTacpb131104(BigDecimal tacpb131104){
       this.tacpb131104 = tacpb131104;
    }
    
    public BigDecimal getTacpb131104(){
       return this.tacpb131104;
    }
	
    public void setTacpb131105(BigDecimal tacpb131105){
       this.tacpb131105 = tacpb131105;
    }
    
    public BigDecimal getTacpb131105(){
       return this.tacpb131105;
    }
	
    public void setTacpb131106(BigDecimal tacpb131106){
       this.tacpb131106 = tacpb131106;
    }
    
    public BigDecimal getTacpb131106(){
       return this.tacpb131106;
    }
	
    public void setTacpb131107(BigDecimal tacpb131107){
       this.tacpb131107 = tacpb131107;
    }
    
    public BigDecimal getTacpb131107(){
       return this.tacpb131107;
    }
	
    public void setTacpbOperCostSpec(BigDecimal tacpbOperCostSpec){
       this.tacpbOperCostSpec = tacpbOperCostSpec;
    }
    
    public BigDecimal getTacpbOperCostSpec(){
       return this.tacpbOperCostSpec;
    }
	
    public void setTacpbOperCostAdju(BigDecimal tacpbOperCostAdju){
       this.tacpbOperCostAdju = tacpbOperCostAdju;
    }
    
    public BigDecimal getTacpbOperCostAdju(){
       return this.tacpbOperCostAdju;
    }
	
    public void setTacpb110300(BigDecimal tacpb110300){
       this.tacpb110300 = tacpb110300;
    }
    
    public BigDecimal getTacpb110300(){
       return this.tacpb110300;
    }
	
    public void setTacpb131201(BigDecimal tacpb131201){
       this.tacpb131201 = tacpb131201;
    }
    
    public BigDecimal getTacpb131201(){
       return this.tacpb131201;
    }
	
    public void setTacpb130201(BigDecimal tacpb130201){
       this.tacpb130201 = tacpb130201;
    }
    
    public BigDecimal getTacpb130201(){
       return this.tacpb130201;
    }
	
    public void setTacpb130211(BigDecimal tacpb130211){
       this.tacpb130211 = tacpb130211;
    }
    
    public BigDecimal getTacpb130211(){
       return this.tacpb130211;
    }
	
    public void setTacpb120601(BigDecimal tacpb120601){
       this.tacpb120601 = tacpb120601;
    }
    
    public BigDecimal getTacpb120601(){
       return this.tacpb120601;
    }
	
    public void setTacpbNoperIncSpec(BigDecimal tacpbNoperIncSpec){
       this.tacpbNoperIncSpec = tacpbNoperIncSpec;
    }
    
    public BigDecimal getTacpbNoperIncSpec(){
       return this.tacpbNoperIncSpec;
    }
	
    public void setTacpbNoperIncAdju(BigDecimal tacpbNoperIncAdju){
       this.tacpbNoperIncAdju = tacpbNoperIncAdju;
    }
    
    public BigDecimal getTacpbNoperIncAdju(){
       return this.tacpbNoperIncAdju;
    }
	
    public void setTacpb130101(BigDecimal tacpb130101){
       this.tacpb130101 = tacpb130101;
    }
    
    public BigDecimal getTacpb130101(){
       return this.tacpb130101;
    }
	
    public void setTacpb130501(BigDecimal tacpb130501){
       this.tacpb130501 = tacpb130501;
    }
    
    public BigDecimal getTacpb130501(){
       return this.tacpb130501;
    }
	
    public void setTacpb130702(BigDecimal tacpb130702){
       this.tacpb130702 = tacpb130702;
    }
    
    public BigDecimal getTacpb130702(){
       return this.tacpb130702;
    }
	
    public void setTacpb130712(BigDecimal tacpb130712){
       this.tacpb130712 = tacpb130712;
    }
    
    public BigDecimal getTacpb130712(){
       return this.tacpb130712;
    }
	
    public void setTacpb131401(BigDecimal tacpb131401){
       this.tacpb131401 = tacpb131401;
    }
    
    public BigDecimal getTacpb131401(){
       return this.tacpb131401;
    }
	
    public void setTacpbProfAdju(BigDecimal tacpbProfAdju){
       this.tacpbProfAdju = tacpbProfAdju;
    }
    
    public BigDecimal getTacpbProfAdju(){
       return this.tacpbProfAdju;
    }
	
    public void setTacpb140101(BigDecimal tacpb140101){
       this.tacpb140101 = tacpb140101;
    }
    
    public BigDecimal getTacpb140101(){
       return this.tacpb140101;
    }
	
    public void setTacpb140202(BigDecimal tacpb140202){
       this.tacpb140202 = tacpb140202;
    }
    
    public BigDecimal getTacpb140202(){
       return this.tacpb140202;
    }
	
    public void setTacpb140801(BigDecimal tacpb140801){
       this.tacpb140801 = tacpb140801;
    }
    
    public BigDecimal getTacpb140801(){
       return this.tacpb140801;
    }
	
    public void setTacpbNetProfSpec(BigDecimal tacpbNetProfSpec){
       this.tacpbNetProfSpec = tacpbNetProfSpec;
    }
    
    public BigDecimal getTacpbNetProfSpec(){
       return this.tacpbNetProfSpec;
    }
	
    public void setTacpbNetProfAdju(BigDecimal tacpbNetProfAdju){
       this.tacpbNetProfAdju = tacpbNetProfAdju;
    }
    
    public BigDecimal getTacpbNetProfAdju(){
       return this.tacpbNetProfAdju;
    }
	
    public void setTacpb150101(BigDecimal tacpb150101){
       this.tacpb150101 = tacpb150101;
    }
    
    public BigDecimal getTacpb150101(){
       return this.tacpb150101;
    }
	
    public void setTacpb160101(BigDecimal tacpb160101){
       this.tacpb160101 = tacpb160101;
    }
    
    public BigDecimal getTacpb160101(){
       return this.tacpb160101;
    }
	
    public void setTacpb140302(BigDecimal tacpb140302){
       this.tacpb140302 = tacpb140302;
    }
    
    public BigDecimal getTacpb140302(){
       return this.tacpb140302;
    }
	
    public void setTacpb140303(BigDecimal tacpb140303){
       this.tacpb140303 = tacpb140303;
    }
    
    public BigDecimal getTacpb140303(){
       return this.tacpb140303;
    }
	
    public void setTacpbIncAdju(BigDecimal tacpbIncAdju){
       this.tacpbIncAdju = tacpbIncAdju;
    }
    
    public BigDecimal getTacpbIncAdju(){
       return this.tacpbIncAdju;
    }
	
    public void setTacpb170101(BigDecimal tacpb170101){
       this.tacpb170101 = tacpb170101;
    }
    
    public BigDecimal getTacpb170101(){
       return this.tacpb170101;
    }
	
    public void setTacpb170102(BigDecimal tacpb170102){
       this.tacpb170102 = tacpb170102;
    }
    
    public BigDecimal getTacpb170102(){
       return this.tacpb170102;
    }
	
    public void setTacpb170103(BigDecimal tacpb170103){
       this.tacpb170103 = tacpb170103;
    }
    
    public BigDecimal getTacpb170103(){
       return this.tacpb170103;
    }
	
    public void setTacpbPareComIncAdju(BigDecimal tacpbPareComIncAdju){
       this.tacpbPareComIncAdju = tacpbPareComIncAdju;
    }
    
    public BigDecimal getTacpbPareComIncAdju(){
       return this.tacpbPareComIncAdju;
    }
	
    public void setTacpb240801(BigDecimal tacpb240801){
       this.tacpb240801 = tacpb240801;
    }
    
    public BigDecimal getTacpb240801(){
       return this.tacpb240801;
    }
	
    public void setTacpb240901(BigDecimal tacpb240901){
       this.tacpb240901 = tacpb240901;
    }
    
    public BigDecimal getTacpb240901(){
       return this.tacpb240901;
    }
	
    public void setTacpbSpecDes(String tacpbSpecDes){
       this.tacpbSpecDes = tacpbSpecDes;
    }
    
    public String getTacpbSpecDes(){
       return this.tacpbSpecDes;
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
        result = prime * result + ((tacpb110100 == null) ? 0 : tacpb110100.hashCode());
        result = prime * result + ((tacpb110101 == null) ? 0 : tacpb110101.hashCode());
        result = prime * result + ((tacpb110131 == null) ? 0 : tacpb110131.hashCode());
        result = prime * result + ((tacpb11013101 == null) ? 0 : tacpb11013101.hashCode());
        result = prime * result + ((tacpb11013102 == null) ? 0 : tacpb11013102.hashCode());
        result = prime * result + ((tacpb11013103 == null) ? 0 : tacpb11013103.hashCode());
        result = prime * result + ((tacpb110200 == null) ? 0 : tacpb110200.hashCode());
        result = prime * result + ((tacpb110202 == null) ? 0 : tacpb110202.hashCode());
        result = prime * result + ((tacpb110300 == null) ? 0 : tacpb110300.hashCode());
        result = prime * result + ((tacpb110302 == null) ? 0 : tacpb110302.hashCode());
        result = prime * result + ((tacpb110402 == null) ? 0 : tacpb110402.hashCode());
        result = prime * result + ((tacpb11040201 == null) ? 0 : tacpb11040201.hashCode());
        result = prime * result + ((tacpb11040202 == null) ? 0 : tacpb11040202.hashCode());
        result = prime * result + ((tacpb120422 == null) ? 0 : tacpb120422.hashCode());
        result = prime * result + ((tacpb120432 == null) ? 0 : tacpb120432.hashCode());
        result = prime * result + ((tacpb120442 == null) ? 0 : tacpb120442.hashCode());
        result = prime * result + ((tacpb120601 == null) ? 0 : tacpb120601.hashCode());
        result = prime * result + ((tacpb130101 == null) ? 0 : tacpb130101.hashCode());
        result = prime * result + ((tacpb130201 == null) ? 0 : tacpb130201.hashCode());
        result = prime * result + ((tacpb130211 == null) ? 0 : tacpb130211.hashCode());
        result = prime * result + ((tacpb130501 == null) ? 0 : tacpb130501.hashCode());
        result = prime * result + ((tacpb130702 == null) ? 0 : tacpb130702.hashCode());
        result = prime * result + ((tacpb130712 == null) ? 0 : tacpb130712.hashCode());
        result = prime * result + ((tacpb131102 == null) ? 0 : tacpb131102.hashCode());
        result = prime * result + ((tacpb131103 == null) ? 0 : tacpb131103.hashCode());
        result = prime * result + ((tacpb131104 == null) ? 0 : tacpb131104.hashCode());
        result = prime * result + ((tacpb131105 == null) ? 0 : tacpb131105.hashCode());
        result = prime * result + ((tacpb131106 == null) ? 0 : tacpb131106.hashCode());
        result = prime * result + ((tacpb131107 == null) ? 0 : tacpb131107.hashCode());
        result = prime * result + ((tacpb131201 == null) ? 0 : tacpb131201.hashCode());
        result = prime * result + ((tacpb131401 == null) ? 0 : tacpb131401.hashCode());
        result = prime * result + ((tacpb140101 == null) ? 0 : tacpb140101.hashCode());
        result = prime * result + ((tacpb140202 == null) ? 0 : tacpb140202.hashCode());
        result = prime * result + ((tacpb140302 == null) ? 0 : tacpb140302.hashCode());
        result = prime * result + ((tacpb140303 == null) ? 0 : tacpb140303.hashCode());
        result = prime * result + ((tacpb140801 == null) ? 0 : tacpb140801.hashCode());
        result = prime * result + ((tacpb150101 == null) ? 0 : tacpb150101.hashCode());
        result = prime * result + ((tacpb160101 == null) ? 0 : tacpb160101.hashCode());
        result = prime * result + ((tacpb170101 == null) ? 0 : tacpb170101.hashCode());
        result = prime * result + ((tacpb170102 == null) ? 0 : tacpb170102.hashCode());
        result = prime * result + ((tacpb170103 == null) ? 0 : tacpb170103.hashCode());
        result = prime * result + ((tacpb240801 == null) ? 0 : tacpb240801.hashCode());
        result = prime * result + ((tacpb240901 == null) ? 0 : tacpb240901.hashCode());
        result = prime * result + ((tacpbIncAdju == null) ? 0 : tacpbIncAdju.hashCode());
        result = prime * result + ((tacpbNetProfAdju == null) ? 0 : tacpbNetProfAdju.hashCode());
        result = prime * result + ((tacpbNetProfSpec == null) ? 0 : tacpbNetProfSpec.hashCode());
        result = prime * result + ((tacpbNoperIncAdju == null) ? 0 : tacpbNoperIncAdju.hashCode());
        result = prime * result + ((tacpbNoperIncSpec == null) ? 0 : tacpbNoperIncSpec.hashCode());
        result = prime * result + ((tacpbOperCostAdju == null) ? 0 : tacpbOperCostAdju.hashCode());
        result = prime * result + ((tacpbOperCostSpec == null) ? 0 : tacpbOperCostSpec.hashCode());
        result = prime * result + ((tacpbPareComIncAdju == null) ? 0 : tacpbPareComIncAdju.hashCode());
        result = prime * result + ((tacpbProfAdju == null) ? 0 : tacpbProfAdju.hashCode());
        result = prime * result + ((tacpbSpecDes == null) ? 0 : tacpbSpecDes.hashCode());
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
        BondFinGenProfTacpb other = (BondFinGenProfTacpb) obj;
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
        if (tacpb110100 == null) {
            if (other.tacpb110100 != null)
                return false;
        } else if (!tacpb110100.equals(other.tacpb110100))
            return false;
        if (tacpb110101 == null) {
            if (other.tacpb110101 != null)
                return false;
        } else if (!tacpb110101.equals(other.tacpb110101))
            return false;
        if (tacpb110131 == null) {
            if (other.tacpb110131 != null)
                return false;
        } else if (!tacpb110131.equals(other.tacpb110131))
            return false;
        if (tacpb11013101 == null) {
            if (other.tacpb11013101 != null)
                return false;
        } else if (!tacpb11013101.equals(other.tacpb11013101))
            return false;
        if (tacpb11013102 == null) {
            if (other.tacpb11013102 != null)
                return false;
        } else if (!tacpb11013102.equals(other.tacpb11013102))
            return false;
        if (tacpb11013103 == null) {
            if (other.tacpb11013103 != null)
                return false;
        } else if (!tacpb11013103.equals(other.tacpb11013103))
            return false;
        if (tacpb110200 == null) {
            if (other.tacpb110200 != null)
                return false;
        } else if (!tacpb110200.equals(other.tacpb110200))
            return false;
        if (tacpb110202 == null) {
            if (other.tacpb110202 != null)
                return false;
        } else if (!tacpb110202.equals(other.tacpb110202))
            return false;
        if (tacpb110300 == null) {
            if (other.tacpb110300 != null)
                return false;
        } else if (!tacpb110300.equals(other.tacpb110300))
            return false;
        if (tacpb110302 == null) {
            if (other.tacpb110302 != null)
                return false;
        } else if (!tacpb110302.equals(other.tacpb110302))
            return false;
        if (tacpb110402 == null) {
            if (other.tacpb110402 != null)
                return false;
        } else if (!tacpb110402.equals(other.tacpb110402))
            return false;
        if (tacpb11040201 == null) {
            if (other.tacpb11040201 != null)
                return false;
        } else if (!tacpb11040201.equals(other.tacpb11040201))
            return false;
        if (tacpb11040202 == null) {
            if (other.tacpb11040202 != null)
                return false;
        } else if (!tacpb11040202.equals(other.tacpb11040202))
            return false;
        if (tacpb120422 == null) {
            if (other.tacpb120422 != null)
                return false;
        } else if (!tacpb120422.equals(other.tacpb120422))
            return false;
        if (tacpb120432 == null) {
            if (other.tacpb120432 != null)
                return false;
        } else if (!tacpb120432.equals(other.tacpb120432))
            return false;
        if (tacpb120442 == null) {
            if (other.tacpb120442 != null)
                return false;
        } else if (!tacpb120442.equals(other.tacpb120442))
            return false;
        if (tacpb120601 == null) {
            if (other.tacpb120601 != null)
                return false;
        } else if (!tacpb120601.equals(other.tacpb120601))
            return false;
        if (tacpb130101 == null) {
            if (other.tacpb130101 != null)
                return false;
        } else if (!tacpb130101.equals(other.tacpb130101))
            return false;
        if (tacpb130201 == null) {
            if (other.tacpb130201 != null)
                return false;
        } else if (!tacpb130201.equals(other.tacpb130201))
            return false;
        if (tacpb130211 == null) {
            if (other.tacpb130211 != null)
                return false;
        } else if (!tacpb130211.equals(other.tacpb130211))
            return false;
        if (tacpb130501 == null) {
            if (other.tacpb130501 != null)
                return false;
        } else if (!tacpb130501.equals(other.tacpb130501))
            return false;
        if (tacpb130702 == null) {
            if (other.tacpb130702 != null)
                return false;
        } else if (!tacpb130702.equals(other.tacpb130702))
            return false;
        if (tacpb130712 == null) {
            if (other.tacpb130712 != null)
                return false;
        } else if (!tacpb130712.equals(other.tacpb130712))
            return false;
        if (tacpb131102 == null) {
            if (other.tacpb131102 != null)
                return false;
        } else if (!tacpb131102.equals(other.tacpb131102))
            return false;
        if (tacpb131103 == null) {
            if (other.tacpb131103 != null)
                return false;
        } else if (!tacpb131103.equals(other.tacpb131103))
            return false;
        if (tacpb131104 == null) {
            if (other.tacpb131104 != null)
                return false;
        } else if (!tacpb131104.equals(other.tacpb131104))
            return false;
        if (tacpb131105 == null) {
            if (other.tacpb131105 != null)
                return false;
        } else if (!tacpb131105.equals(other.tacpb131105))
            return false;
        if (tacpb131106 == null) {
            if (other.tacpb131106 != null)
                return false;
        } else if (!tacpb131106.equals(other.tacpb131106))
            return false;
        if (tacpb131107 == null) {
            if (other.tacpb131107 != null)
                return false;
        } else if (!tacpb131107.equals(other.tacpb131107))
            return false;
        if (tacpb131201 == null) {
            if (other.tacpb131201 != null)
                return false;
        } else if (!tacpb131201.equals(other.tacpb131201))
            return false;
        if (tacpb131401 == null) {
            if (other.tacpb131401 != null)
                return false;
        } else if (!tacpb131401.equals(other.tacpb131401))
            return false;
        if (tacpb140101 == null) {
            if (other.tacpb140101 != null)
                return false;
        } else if (!tacpb140101.equals(other.tacpb140101))
            return false;
        if (tacpb140202 == null) {
            if (other.tacpb140202 != null)
                return false;
        } else if (!tacpb140202.equals(other.tacpb140202))
            return false;
        if (tacpb140302 == null) {
            if (other.tacpb140302 != null)
                return false;
        } else if (!tacpb140302.equals(other.tacpb140302))
            return false;
        if (tacpb140303 == null) {
            if (other.tacpb140303 != null)
                return false;
        } else if (!tacpb140303.equals(other.tacpb140303))
            return false;
        if (tacpb140801 == null) {
            if (other.tacpb140801 != null)
                return false;
        } else if (!tacpb140801.equals(other.tacpb140801))
            return false;
        if (tacpb150101 == null) {
            if (other.tacpb150101 != null)
                return false;
        } else if (!tacpb150101.equals(other.tacpb150101))
            return false;
        if (tacpb160101 == null) {
            if (other.tacpb160101 != null)
                return false;
        } else if (!tacpb160101.equals(other.tacpb160101))
            return false;
        if (tacpb170101 == null) {
            if (other.tacpb170101 != null)
                return false;
        } else if (!tacpb170101.equals(other.tacpb170101))
            return false;
        if (tacpb170102 == null) {
            if (other.tacpb170102 != null)
                return false;
        } else if (!tacpb170102.equals(other.tacpb170102))
            return false;
        if (tacpb170103 == null) {
            if (other.tacpb170103 != null)
                return false;
        } else if (!tacpb170103.equals(other.tacpb170103))
            return false;
        if (tacpb240801 == null) {
            if (other.tacpb240801 != null)
                return false;
        } else if (!tacpb240801.equals(other.tacpb240801))
            return false;
        if (tacpb240901 == null) {
            if (other.tacpb240901 != null)
                return false;
        } else if (!tacpb240901.equals(other.tacpb240901))
            return false;
        if (tacpbIncAdju == null) {
            if (other.tacpbIncAdju != null)
                return false;
        } else if (!tacpbIncAdju.equals(other.tacpbIncAdju))
            return false;
        if (tacpbNetProfAdju == null) {
            if (other.tacpbNetProfAdju != null)
                return false;
        } else if (!tacpbNetProfAdju.equals(other.tacpbNetProfAdju))
            return false;
        if (tacpbNetProfSpec == null) {
            if (other.tacpbNetProfSpec != null)
                return false;
        } else if (!tacpbNetProfSpec.equals(other.tacpbNetProfSpec))
            return false;
        if (tacpbNoperIncAdju == null) {
            if (other.tacpbNoperIncAdju != null)
                return false;
        } else if (!tacpbNoperIncAdju.equals(other.tacpbNoperIncAdju))
            return false;
        if (tacpbNoperIncSpec == null) {
            if (other.tacpbNoperIncSpec != null)
                return false;
        } else if (!tacpbNoperIncSpec.equals(other.tacpbNoperIncSpec))
            return false;
        if (tacpbOperCostAdju == null) {
            if (other.tacpbOperCostAdju != null)
                return false;
        } else if (!tacpbOperCostAdju.equals(other.tacpbOperCostAdju))
            return false;
        if (tacpbOperCostSpec == null) {
            if (other.tacpbOperCostSpec != null)
                return false;
        } else if (!tacpbOperCostSpec.equals(other.tacpbOperCostSpec))
            return false;
        if (tacpbPareComIncAdju == null) {
            if (other.tacpbPareComIncAdju != null)
                return false;
        } else if (!tacpbPareComIncAdju.equals(other.tacpbPareComIncAdju))
            return false;
        if (tacpbProfAdju == null) {
            if (other.tacpbProfAdju != null)
                return false;
        } else if (!tacpbProfAdju.equals(other.tacpbProfAdju))
            return false;
        if (tacpbSpecDes == null) {
            if (other.tacpbSpecDes != null)
                return false;
        } else if (!tacpbSpecDes.equals(other.tacpbSpecDes))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BondFinGenProfTacpb [" + (id != null ? "id=" + id + ", " : "")
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
                + (tacpb110100 != null ? "tacpb110100=" + tacpb110100 + ", " : "")
                + (tacpb110101 != null ? "tacpb110101=" + tacpb110101 + ", " : "")
                + (tacpb110131 != null ? "tacpb110131=" + tacpb110131 + ", " : "")
                + (tacpb11013101 != null ? "tacpb11013101=" + tacpb11013101 + ", " : "")
                + (tacpb11013102 != null ? "tacpb11013102=" + tacpb11013102 + ", " : "")
                + (tacpb11013103 != null ? "tacpb11013103=" + tacpb11013103 + ", " : "")
                + (tacpb110200 != null ? "tacpb110200=" + tacpb110200 + ", " : "")
                + (tacpb110202 != null ? "tacpb110202=" + tacpb110202 + ", " : "")
                + (tacpb110402 != null ? "tacpb110402=" + tacpb110402 + ", " : "")
                + (tacpb11040201 != null ? "tacpb11040201=" + tacpb11040201 + ", " : "")
                + (tacpb11040202 != null ? "tacpb11040202=" + tacpb11040202 + ", " : "")
                + (tacpb110302 != null ? "tacpb110302=" + tacpb110302 + ", " : "")
                + (tacpb120442 != null ? "tacpb120442=" + tacpb120442 + ", " : "")
                + (tacpb120422 != null ? "tacpb120422=" + tacpb120422 + ", " : "")
                + (tacpb120432 != null ? "tacpb120432=" + tacpb120432 + ", " : "")
                + (tacpb131102 != null ? "tacpb131102=" + tacpb131102 + ", " : "")
                + (tacpb131103 != null ? "tacpb131103=" + tacpb131103 + ", " : "")
                + (tacpb131104 != null ? "tacpb131104=" + tacpb131104 + ", " : "")
                + (tacpb131105 != null ? "tacpb131105=" + tacpb131105 + ", " : "")
                + (tacpb131106 != null ? "tacpb131106=" + tacpb131106 + ", " : "")
                + (tacpb131107 != null ? "tacpb131107=" + tacpb131107 + ", " : "")
                + (tacpbOperCostSpec != null ? "tacpbOperCostSpec=" + tacpbOperCostSpec + ", " : "")
                + (tacpbOperCostAdju != null ? "tacpbOperCostAdju=" + tacpbOperCostAdju + ", " : "")
                + (tacpb110300 != null ? "tacpb110300=" + tacpb110300 + ", " : "")
                + (tacpb131201 != null ? "tacpb131201=" + tacpb131201 + ", " : "")
                + (tacpb130201 != null ? "tacpb130201=" + tacpb130201 + ", " : "")
                + (tacpb130211 != null ? "tacpb130211=" + tacpb130211 + ", " : "")
                + (tacpb120601 != null ? "tacpb120601=" + tacpb120601 + ", " : "")
                + (tacpbNoperIncSpec != null ? "tacpbNoperIncSpec=" + tacpbNoperIncSpec + ", " : "")
                + (tacpbNoperIncAdju != null ? "tacpbNoperIncAdju=" + tacpbNoperIncAdju + ", " : "")
                + (tacpb130101 != null ? "tacpb130101=" + tacpb130101 + ", " : "")
                + (tacpb130501 != null ? "tacpb130501=" + tacpb130501 + ", " : "")
                + (tacpb130702 != null ? "tacpb130702=" + tacpb130702 + ", " : "")
                + (tacpb130712 != null ? "tacpb130712=" + tacpb130712 + ", " : "")
                + (tacpb131401 != null ? "tacpb131401=" + tacpb131401 + ", " : "")
                + (tacpbProfAdju != null ? "tacpbProfAdju=" + tacpbProfAdju + ", " : "")
                + (tacpb140101 != null ? "tacpb140101=" + tacpb140101 + ", " : "")
                + (tacpb140202 != null ? "tacpb140202=" + tacpb140202 + ", " : "")
                + (tacpb140801 != null ? "tacpb140801=" + tacpb140801 + ", " : "")
                + (tacpbNetProfSpec != null ? "tacpbNetProfSpec=" + tacpbNetProfSpec + ", " : "")
                + (tacpbNetProfAdju != null ? "tacpbNetProfAdju=" + tacpbNetProfAdju + ", " : "")
                + (tacpb150101 != null ? "tacpb150101=" + tacpb150101 + ", " : "")
                + (tacpb160101 != null ? "tacpb160101=" + tacpb160101 + ", " : "")
                + (tacpb140302 != null ? "tacpb140302=" + tacpb140302 + ", " : "")
                + (tacpb140303 != null ? "tacpb140303=" + tacpb140303 + ", " : "")
                + (tacpbIncAdju != null ? "tacpbIncAdju=" + tacpbIncAdju + ", " : "")
                + (tacpb170101 != null ? "tacpb170101=" + tacpb170101 + ", " : "")
                + (tacpb170102 != null ? "tacpb170102=" + tacpb170102 + ", " : "")
                + (tacpb170103 != null ? "tacpb170103=" + tacpb170103 + ", " : "")
                + (tacpbPareComIncAdju != null ? "tacpbPareComIncAdju=" + tacpbPareComIncAdju + ", " : "")
                + (tacpb240801 != null ? "tacpb240801=" + tacpb240801 + ", " : "")
                + (tacpb240901 != null ? "tacpb240901=" + tacpb240901 + ", " : "")
                + (tacpbSpecDes != null ? "tacpbSpecDes=" + tacpbSpecDes + ", " : "")
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
        selectSql += ",TACPB_110100";
        selectSql += ",TACPB_110101";
        selectSql += ",TACPB_110131";
        selectSql += ",TACPB_11013101";
        selectSql += ",TACPB_11013102";
        selectSql += ",TACPB_11013103";
        selectSql += ",TACPB_110200";
        selectSql += ",TACPB_110202";
        selectSql += ",TACPB_110402";
        selectSql += ",TACPB_11040201";
        selectSql += ",TACPB_11040202";
        selectSql += ",TACPB_110302";
        selectSql += ",TACPB_120442";
        selectSql += ",TACPB_120422";
        selectSql += ",TACPB_120432";
        selectSql += ",TACPB_131102";
        selectSql += ",TACPB_131103";
        selectSql += ",TACPB_131104";
        selectSql += ",TACPB_131105";
        selectSql += ",TACPB_131106";
        selectSql += ",TACPB_131107";
        selectSql += ",TACPB_OPER_COST_SPEC";
        selectSql += ",TACPB_OPER_COST_ADJU";
        selectSql += ",TACPB_110300";
        selectSql += ",TACPB_131201";
        selectSql += ",TACPB_130201";
        selectSql += ",TACPB_130211";
        selectSql += ",TACPB_120601";
        selectSql += ",TACPB_NOPER_INC_SPEC";
        selectSql += ",TACPB_NOPER_INC_ADJU";
        selectSql += ",TACPB_130101";
        selectSql += ",TACPB_130501";
        selectSql += ",TACPB_130702";
        selectSql += ",TACPB_130712";
        selectSql += ",TACPB_131401";
        selectSql += ",TACPB_PROF_ADJU";
        selectSql += ",TACPB_140101";
        selectSql += ",TACPB_140202";
        selectSql += ",TACPB_140801";
        selectSql += ",TACPB_NET_PROF_SPEC";
        selectSql += ",TACPB_NET_PROF_ADJU";
        selectSql += ",TACPB_150101";
        selectSql += ",TACPB_160101";
        selectSql += ",TACPB_140302";
        selectSql += ",TACPB_140303";
        selectSql += ",TACPB_INC_ADJU";
        selectSql += ",TACPB_170101";
        selectSql += ",TACPB_170102";
        selectSql += ",TACPB_170103";
        selectSql += ",TACPB_PARE_COM_INC_ADJU";
        selectSql += ",TACPB_240801";
        selectSql += ",TACPB_240901";
        selectSql += ",TACPB_SPEC_DES";
        selectSql += ",INFO_PUB_DATE";
 	   return selectSql.substring(1);
     }
 	
 	public String createEqualsSql(String whereSql){
 	   String selectSql = createSelectColumnSql();       
        String sql = "select count(1) from "
        +" (select * from (select "+selectSql+" from bond_ccxe.d_bond_fin_gen_prof_tacpb where "+whereSql
        +" order by ccxeid desc limit 1) a union select * from (select "+selectSql+" from bond_ccxe_snapshot.d_bond_fin_gen_prof_tacpb where "+whereSql+" order by ccxeid desc limit 1) a) a";
        return sql;
     }
     
     public String createEqualsSql(String id, Long bondUniCode, Long comUniCode, String endDate){
 	   String selectSql = createSelectColumnSql();
 	  String whereSql = " id='" + id + "' and bond_uni_code=" + bondUniCode 
 	         + " and com_uni_code=" + comUniCode 
 	         + " and end_date='" + endDate + "'";
                
        String sql = "select count(1) from "
        +" (select * from (select "+selectSql+" from bond_ccxe.d_bond_fin_gen_prof_tacpb where "+whereSql
        +" order by ccxeid desc limit 1) a union select * from (select "+selectSql+" from bond_ccxe_snapshot.d_bond_fin_gen_prof_tacpb where "+whereSql+" order by ccxeid desc limit 1) a) a";
        return sql;
     }
}
