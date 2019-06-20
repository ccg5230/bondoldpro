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
@Table(name="d_bond_fin_fal_prof_tafpb")
public class BondFinFalProfTafpb implements Serializable{
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
    @Column(name="TAFPBY_100000", length=20)
    private BigDecimal tafpby100000;
    
	/**
	 * 
	 */
    @Column(name="TAFPBY_110101", length=20)
    private BigDecimal tafpby110101;
    
	/**
	 * 
	 */
    @Column(name="TAFPBY_110111", length=20)
    private BigDecimal tafpby110111;
    
	/**
	 * 
	 */
    @Column(name="TAFPBY_110123", length=20)
    private BigDecimal tafpby110123;
    
	/**
	 * 
	 */
    @Column(name="TAFPBY_110301", length=20)
    private BigDecimal tafpby110301;
    
	/**
	 * 
	 */
    @Column(name="TAFPBY_111511", length=20)
    private BigDecimal tafpby111511;
    
	/**
	 * 
	 */
    @Column(name="TAFPBY_111523", length=20)
    private BigDecimal tafpby111523;
    
	/**
	 * 
	 */
    @Column(name="TAFPBF_1110525", length=20)
    private BigDecimal tafpbf1110525;
    
	/**
	 * 
	 */
    @Column(name="TAFPBF_110601", length=20)
    private BigDecimal tafpbf110601;
    
	/**
	 * 
	 */
    @Column(name="TAFPBF_110701", length=20)
    private BigDecimal tafpbf110701;
    
	/**
	 * 
	 */
    @Column(name="TAFPBF_110201", length=20)
    private BigDecimal tafpbf110201;
    
	/**
	 * 
	 */
    @Column(name="TAFPBF_110213", length=20)
    private BigDecimal tafpbf110213;
    
	/**
	 * 
	 */
    @Column(name="TAFPBF_110215", length=20)
    private BigDecimal tafpbf110215;
    
	/**
	 * 
	 */
    @Column(name="TAFPBF_110224", length=20)
    private BigDecimal tafpbf110224;
    
	/**
	 * 
	 */
    @Column(name="TAFPBF_110301", length=20)
    private BigDecimal tafpbf110301;
    
	/**
	 * 
	 */
    @Column(name="TAFPBY_110701", length=20)
    private BigDecimal tafpby110701;
    
	/**
	 * 
	 */
    @Column(name="TAFPBY_110711", length=20)
    private BigDecimal tafpby110711;
    
	/**
	 * 
	 */
    @Column(name="TAFPBY_110801", length=20)
    private BigDecimal tafpby110801;
    
	/**
	 * 
	 */
    @Column(name="TAFPBY_111101", length=20)
    private BigDecimal tafpby111101;
    
	/**
	 * 
	 */
    @Column(name="TAFPBY_111201", length=20)
    private BigDecimal tafpby111201;
    
	/**
	 * 
	 */
    @Column(name="TAFPB_OPER_INC_SPEC", length=20)
    private BigDecimal tafpbOperIncSpec;
    
	/**
	 * 
	 */
    @Column(name="TAFPB_OPER_INC_ADJU", length=20)
    private BigDecimal tafpbOperIncAdju;
    
	/**
	 * 
	 */
    @Column(name="TAFPBY_200000", length=20)
    private BigDecimal tafpby200000;
    
	/**
	 * 
	 */
    @Column(name="TAFPBF_200603", length=20)
    private BigDecimal tafpbf200603;
    
	/**
	 * 
	 */
    @Column(name="TAFPBF_200203", length=20)
    private BigDecimal tafpbf200203;
    
	/**
	 * 
	 */
    @Column(name="TAFPBF_200213", length=20)
    private BigDecimal tafpbf200213;
    
	/**
	 * 
	 */
    @Column(name="TAFPBF_200403", length=20)
    private BigDecimal tafpbf200403;
    
	/**
	 * 
	 */
    @Column(name="TAFPBF_200413", length=20)
    private BigDecimal tafpbf200413;
    
	/**
	 * 
	 */
    @Column(name="TAFPBF_200703", length=20)
    private BigDecimal tafpbf200703;
    
	/**
	 * 
	 */
    @Column(name="TAFPBF_200503", length=20)
    private BigDecimal tafpbf200503;
    
	/**
	 * 
	 */
    @Column(name="TAFPBF_200504", length=20)
    private BigDecimal tafpbf200504;
    
	/**
	 * 
	 */
    @Column(name="TAFPBF_201903", length=20)
    private BigDecimal tafpbf201903;
    
	/**
	 * 
	 */
    @Column(name="TAFPBF_200903", length=20)
    private BigDecimal tafpbf200903;
    
	/**
	 * 
	 */
    @Column(name="TAFPBF_200913", length=20)
    private BigDecimal tafpbf200913;
    
	/**
	 * 
	 */
    @Column(name="TAFPBF_201703", length=20)
    private BigDecimal tafpbf201703;
    
	/**
	 * 
	 */
    @Column(name="TAFPBF_201803", length=20)
    private BigDecimal tafpbf201803;
    
	/**
	 * 
	 */
    @Column(name="TAFPB_OPER_PAY_SPEC", length=20)
    private BigDecimal tafpbOperPaySpec;
    
	/**
	 * 
	 */
    @Column(name="TAFPB_OPER_PAY_ADJU", length=20)
    private BigDecimal tafpbOperPayAdju;
    
	/**
	 * 
	 */
    @Column(name="TAFPBF_201804", length=20)
    private BigDecimal tafpbf201804;
    
	/**
	 * 
	 */
    @Column(name="TAFPB_OPER_PROF_ADJU", length=20)
    private BigDecimal tafpbOperProfAdju;
    
	/**
	 * 
	 */
    @Column(name="TAFPBY_300000", length=20)
    private BigDecimal tafpby300000;
    
	/**
	 * 
	 */
    @Column(name="TAFPBY_310101", length=20)
    private BigDecimal tafpby310101;
    
	/**
	 * 
	 */
    @Column(name="TAFPBY_310203", length=20)
    private BigDecimal tafpby310203;
    
	/**
	 * 
	 */
    @Column(name="TAFPBY_310213", length=20)
    private BigDecimal tafpby310213;
    
	/**
	 * 
	 */
    @Column(name="TAFPBY_310214", length=20)
    private BigDecimal tafpby310214;
    
	/**
	 * 
	 */
    @Column(name="TAFPB_PROF_ADJU", length=20)
    private BigDecimal tafpbProfAdju;
    
	/**
	 * 
	 */
    @Column(name="TAFPBY_400000", length=20)
    private BigDecimal tafpby400000;
    
	/**
	 * 
	 */
    @Column(name="TAFPBY_410203", length=20)
    private BigDecimal tafpby410203;
    
	/**
	 * 
	 */
    @Column(name="TAFPBY_410204", length=20)
    private BigDecimal tafpby410204;
    
	/**
	 * 
	 */
    @Column(name="TAFPBY_410303", length=20)
    private BigDecimal tafpby410303;
    
	/**
	 * 
	 */
    @Column(name="TAFPB_NET_PROF_ADJU", length=20)
    private BigDecimal tafpbNetProfAdju;
    
	/**
	 * 
	 */
    @Column(name="TAFPBY_500000", length=20)
    private BigDecimal tafpby500000;
    
	/**
	 * 
	 */
    @Column(name="TAFPBY_510101", length=20)
    private BigDecimal tafpby510101;
    
	/**
	 * 
	 */
    @Column(name="TAFPBY_510201", length=20)
    private BigDecimal tafpby510201;
    
	/**
	 * 
	 */
    @Column(name="TAFPBY_510301", length=20)
    private BigDecimal tafpby510301;
    
	/**
	 * 
	 */
    @Column(name="TAFPB_INC_ADJU", length=20)
    private BigDecimal tafpbIncAdju;
    
	/**
	 * 
	 */
    @Column(name="TAFPB_I_170101", length=20)
    private BigDecimal tafpbI170101;
    
	/**
	 * 
	 */
    @Column(name="TAFPB_170102", length=20)
    private BigDecimal tafpb170102;
    
	/**
	 * 
	 */
    @Column(name="TAFPB_170103", length=20)
    private BigDecimal tafpb170103;
    
	/**
	 * 
	 */
    @Column(name="TAFPB_PARE_COM_INC_ADJU", length=20)
    private BigDecimal tafpbPareComIncAdju;
    
	/**
	 * 
	 */
    @Column(name="TAFPB_240801", length=20)
    private BigDecimal tafpb240801;
    
	/**
	 * 
	 */
    @Column(name="TAFPB_240901", length=20)
    private BigDecimal tafpb240901;
    
	/**
	 * 
	 */
    @Column(name="TAFPB_SPEC_DES", length=16777215)
    private String tafpbSpecDes;
    
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
	
    public void setTafpby100000(BigDecimal tafpby100000){
       this.tafpby100000 = tafpby100000;
    }
    
    public BigDecimal getTafpby100000(){
       return this.tafpby100000;
    }
	
    public void setTafpby110101(BigDecimal tafpby110101){
       this.tafpby110101 = tafpby110101;
    }
    
    public BigDecimal getTafpby110101(){
       return this.tafpby110101;
    }
	
    public void setTafpby110111(BigDecimal tafpby110111){
       this.tafpby110111 = tafpby110111;
    }
    
    public BigDecimal getTafpby110111(){
       return this.tafpby110111;
    }
	
    public void setTafpby110123(BigDecimal tafpby110123){
       this.tafpby110123 = tafpby110123;
    }
    
    public BigDecimal getTafpby110123(){
       return this.tafpby110123;
    }
	
    public void setTafpby110301(BigDecimal tafpby110301){
       this.tafpby110301 = tafpby110301;
    }
    
    public BigDecimal getTafpby110301(){
       return this.tafpby110301;
    }
	
    public void setTafpby111511(BigDecimal tafpby111511){
       this.tafpby111511 = tafpby111511;
    }
    
    public BigDecimal getTafpby111511(){
       return this.tafpby111511;
    }
	
    public void setTafpby111523(BigDecimal tafpby111523){
       this.tafpby111523 = tafpby111523;
    }
    
    public BigDecimal getTafpby111523(){
       return this.tafpby111523;
    }
	
    public void setTafpbf1110525(BigDecimal tafpbf1110525){
       this.tafpbf1110525 = tafpbf1110525;
    }
    
    public BigDecimal getTafpbf1110525(){
       return this.tafpbf1110525;
    }
	
    public void setTafpbf110601(BigDecimal tafpbf110601){
       this.tafpbf110601 = tafpbf110601;
    }
    
    public BigDecimal getTafpbf110601(){
       return this.tafpbf110601;
    }
	
    public void setTafpbf110701(BigDecimal tafpbf110701){
       this.tafpbf110701 = tafpbf110701;
    }
    
    public BigDecimal getTafpbf110701(){
       return this.tafpbf110701;
    }
	
    public void setTafpbf110201(BigDecimal tafpbf110201){
       this.tafpbf110201 = tafpbf110201;
    }
    
    public BigDecimal getTafpbf110201(){
       return this.tafpbf110201;
    }
	
    public void setTafpbf110213(BigDecimal tafpbf110213){
       this.tafpbf110213 = tafpbf110213;
    }
    
    public BigDecimal getTafpbf110213(){
       return this.tafpbf110213;
    }
	
    public void setTafpbf110215(BigDecimal tafpbf110215){
       this.tafpbf110215 = tafpbf110215;
    }
    
    public BigDecimal getTafpbf110215(){
       return this.tafpbf110215;
    }
	
    public void setTafpbf110224(BigDecimal tafpbf110224){
       this.tafpbf110224 = tafpbf110224;
    }
    
    public BigDecimal getTafpbf110224(){
       return this.tafpbf110224;
    }
	
    public void setTafpbf110301(BigDecimal tafpbf110301){
       this.tafpbf110301 = tafpbf110301;
    }
    
    public BigDecimal getTafpbf110301(){
       return this.tafpbf110301;
    }
	
    public void setTafpby110701(BigDecimal tafpby110701){
       this.tafpby110701 = tafpby110701;
    }
    
    public BigDecimal getTafpby110701(){
       return this.tafpby110701;
    }
	
    public void setTafpby110711(BigDecimal tafpby110711){
       this.tafpby110711 = tafpby110711;
    }
    
    public BigDecimal getTafpby110711(){
       return this.tafpby110711;
    }
	
    public void setTafpby110801(BigDecimal tafpby110801){
       this.tafpby110801 = tafpby110801;
    }
    
    public BigDecimal getTafpby110801(){
       return this.tafpby110801;
    }
	
    public void setTafpby111101(BigDecimal tafpby111101){
       this.tafpby111101 = tafpby111101;
    }
    
    public BigDecimal getTafpby111101(){
       return this.tafpby111101;
    }
	
    public void setTafpby111201(BigDecimal tafpby111201){
       this.tafpby111201 = tafpby111201;
    }
    
    public BigDecimal getTafpby111201(){
       return this.tafpby111201;
    }
	
    public void setTafpbOperIncSpec(BigDecimal tafpbOperIncSpec){
       this.tafpbOperIncSpec = tafpbOperIncSpec;
    }
    
    public BigDecimal getTafpbOperIncSpec(){
       return this.tafpbOperIncSpec;
    }
	
    public void setTafpbOperIncAdju(BigDecimal tafpbOperIncAdju){
       this.tafpbOperIncAdju = tafpbOperIncAdju;
    }
    
    public BigDecimal getTafpbOperIncAdju(){
       return this.tafpbOperIncAdju;
    }
	
    public void setTafpby200000(BigDecimal tafpby200000){
       this.tafpby200000 = tafpby200000;
    }
    
    public BigDecimal getTafpby200000(){
       return this.tafpby200000;
    }
	
    public void setTafpbf200603(BigDecimal tafpbf200603){
       this.tafpbf200603 = tafpbf200603;
    }
    
    public BigDecimal getTafpbf200603(){
       return this.tafpbf200603;
    }
	
    public void setTafpbf200203(BigDecimal tafpbf200203){
       this.tafpbf200203 = tafpbf200203;
    }
    
    public BigDecimal getTafpbf200203(){
       return this.tafpbf200203;
    }
	
    public void setTafpbf200213(BigDecimal tafpbf200213){
       this.tafpbf200213 = tafpbf200213;
    }
    
    public BigDecimal getTafpbf200213(){
       return this.tafpbf200213;
    }
	
    public void setTafpbf200403(BigDecimal tafpbf200403){
       this.tafpbf200403 = tafpbf200403;
    }
    
    public BigDecimal getTafpbf200403(){
       return this.tafpbf200403;
    }
	
    public void setTafpbf200413(BigDecimal tafpbf200413){
       this.tafpbf200413 = tafpbf200413;
    }
    
    public BigDecimal getTafpbf200413(){
       return this.tafpbf200413;
    }
	
    public void setTafpbf200703(BigDecimal tafpbf200703){
       this.tafpbf200703 = tafpbf200703;
    }
    
    public BigDecimal getTafpbf200703(){
       return this.tafpbf200703;
    }
	
    public void setTafpbf200503(BigDecimal tafpbf200503){
       this.tafpbf200503 = tafpbf200503;
    }
    
    public BigDecimal getTafpbf200503(){
       return this.tafpbf200503;
    }
	
    public void setTafpbf200504(BigDecimal tafpbf200504){
       this.tafpbf200504 = tafpbf200504;
    }
    
    public BigDecimal getTafpbf200504(){
       return this.tafpbf200504;
    }
	
    public void setTafpbf201903(BigDecimal tafpbf201903){
       this.tafpbf201903 = tafpbf201903;
    }
    
    public BigDecimal getTafpbf201903(){
       return this.tafpbf201903;
    }
	
    public void setTafpbf200903(BigDecimal tafpbf200903){
       this.tafpbf200903 = tafpbf200903;
    }
    
    public BigDecimal getTafpbf200903(){
       return this.tafpbf200903;
    }
	
    public void setTafpbf200913(BigDecimal tafpbf200913){
       this.tafpbf200913 = tafpbf200913;
    }
    
    public BigDecimal getTafpbf200913(){
       return this.tafpbf200913;
    }
	
    public void setTafpbf201703(BigDecimal tafpbf201703){
       this.tafpbf201703 = tafpbf201703;
    }
    
    public BigDecimal getTafpbf201703(){
       return this.tafpbf201703;
    }
	
    public void setTafpbf201803(BigDecimal tafpbf201803){
       this.tafpbf201803 = tafpbf201803;
    }
    
    public BigDecimal getTafpbf201803(){
       return this.tafpbf201803;
    }
	
    public void setTafpbOperPaySpec(BigDecimal tafpbOperPaySpec){
       this.tafpbOperPaySpec = tafpbOperPaySpec;
    }
    
    public BigDecimal getTafpbOperPaySpec(){
       return this.tafpbOperPaySpec;
    }
	
    public void setTafpbOperPayAdju(BigDecimal tafpbOperPayAdju){
       this.tafpbOperPayAdju = tafpbOperPayAdju;
    }
    
    public BigDecimal getTafpbOperPayAdju(){
       return this.tafpbOperPayAdju;
    }
	
    public void setTafpbf201804(BigDecimal tafpbf201804){
       this.tafpbf201804 = tafpbf201804;
    }
    
    public BigDecimal getTafpbf201804(){
       return this.tafpbf201804;
    }
	
    public void setTafpbOperProfAdju(BigDecimal tafpbOperProfAdju){
       this.tafpbOperProfAdju = tafpbOperProfAdju;
    }
    
    public BigDecimal getTafpbOperProfAdju(){
       return this.tafpbOperProfAdju;
    }
	
    public void setTafpby300000(BigDecimal tafpby300000){
       this.tafpby300000 = tafpby300000;
    }
    
    public BigDecimal getTafpby300000(){
       return this.tafpby300000;
    }
	
    public void setTafpby310101(BigDecimal tafpby310101){
       this.tafpby310101 = tafpby310101;
    }
    
    public BigDecimal getTafpby310101(){
       return this.tafpby310101;
    }
	
    public void setTafpby310203(BigDecimal tafpby310203){
       this.tafpby310203 = tafpby310203;
    }
    
    public BigDecimal getTafpby310203(){
       return this.tafpby310203;
    }
	
    public void setTafpby310213(BigDecimal tafpby310213){
       this.tafpby310213 = tafpby310213;
    }
    
    public BigDecimal getTafpby310213(){
       return this.tafpby310213;
    }
	
    public void setTafpby310214(BigDecimal tafpby310214){
       this.tafpby310214 = tafpby310214;
    }
    
    public BigDecimal getTafpby310214(){
       return this.tafpby310214;
    }
	
    public void setTafpbProfAdju(BigDecimal tafpbProfAdju){
       this.tafpbProfAdju = tafpbProfAdju;
    }
    
    public BigDecimal getTafpbProfAdju(){
       return this.tafpbProfAdju;
    }
	
    public void setTafpby400000(BigDecimal tafpby400000){
       this.tafpby400000 = tafpby400000;
    }
    
    public BigDecimal getTafpby400000(){
       return this.tafpby400000;
    }
	
    public void setTafpby410203(BigDecimal tafpby410203){
       this.tafpby410203 = tafpby410203;
    }
    
    public BigDecimal getTafpby410203(){
       return this.tafpby410203;
    }
	
    public void setTafpby410204(BigDecimal tafpby410204){
       this.tafpby410204 = tafpby410204;
    }
    
    public BigDecimal getTafpby410204(){
       return this.tafpby410204;
    }
	
    public void setTafpby410303(BigDecimal tafpby410303){
       this.tafpby410303 = tafpby410303;
    }
    
    public BigDecimal getTafpby410303(){
       return this.tafpby410303;
    }
	
    public void setTafpbNetProfAdju(BigDecimal tafpbNetProfAdju){
       this.tafpbNetProfAdju = tafpbNetProfAdju;
    }
    
    public BigDecimal getTafpbNetProfAdju(){
       return this.tafpbNetProfAdju;
    }
	
    public void setTafpby500000(BigDecimal tafpby500000){
       this.tafpby500000 = tafpby500000;
    }
    
    public BigDecimal getTafpby500000(){
       return this.tafpby500000;
    }
	
    public void setTafpby510101(BigDecimal tafpby510101){
       this.tafpby510101 = tafpby510101;
    }
    
    public BigDecimal getTafpby510101(){
       return this.tafpby510101;
    }
	
    public void setTafpby510201(BigDecimal tafpby510201){
       this.tafpby510201 = tafpby510201;
    }
    
    public BigDecimal getTafpby510201(){
       return this.tafpby510201;
    }
	
    public void setTafpby510301(BigDecimal tafpby510301){
       this.tafpby510301 = tafpby510301;
    }
    
    public BigDecimal getTafpby510301(){
       return this.tafpby510301;
    }
	
    public void setTafpbIncAdju(BigDecimal tafpbIncAdju){
       this.tafpbIncAdju = tafpbIncAdju;
    }
    
    public BigDecimal getTafpbIncAdju(){
       return this.tafpbIncAdju;
    }
	
    public void setTafpbI170101(BigDecimal tafpbI170101){
       this.tafpbI170101 = tafpbI170101;
    }
    
    public BigDecimal getTafpbI170101(){
       return this.tafpbI170101;
    }
	
    public void setTafpb170102(BigDecimal tafpb170102){
       this.tafpb170102 = tafpb170102;
    }
    
    public BigDecimal getTafpb170102(){
       return this.tafpb170102;
    }
	
    public void setTafpb170103(BigDecimal tafpb170103){
       this.tafpb170103 = tafpb170103;
    }
    
    public BigDecimal getTafpb170103(){
       return this.tafpb170103;
    }
	
    public void setTafpbPareComIncAdju(BigDecimal tafpbPareComIncAdju){
       this.tafpbPareComIncAdju = tafpbPareComIncAdju;
    }
    
    public BigDecimal getTafpbPareComIncAdju(){
       return this.tafpbPareComIncAdju;
    }
	
    public void setTafpb240801(BigDecimal tafpb240801){
       this.tafpb240801 = tafpb240801;
    }
    
    public BigDecimal getTafpb240801(){
       return this.tafpb240801;
    }
	
    public void setTafpb240901(BigDecimal tafpb240901){
       this.tafpb240901 = tafpb240901;
    }
    
    public BigDecimal getTafpb240901(){
       return this.tafpb240901;
    }
	
    public void setTafpbSpecDes(String tafpbSpecDes){
       this.tafpbSpecDes = tafpbSpecDes;
    }
    
    public String getTafpbSpecDes(){
       return this.tafpbSpecDes;
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
        result = prime * result + ((tafpb170102 == null) ? 0 : tafpb170102.hashCode());
        result = prime * result + ((tafpb170103 == null) ? 0 : tafpb170103.hashCode());
        result = prime * result + ((tafpb240801 == null) ? 0 : tafpb240801.hashCode());
        result = prime * result + ((tafpb240901 == null) ? 0 : tafpb240901.hashCode());
        result = prime * result + ((tafpbI170101 == null) ? 0 : tafpbI170101.hashCode());
        result = prime * result + ((tafpbIncAdju == null) ? 0 : tafpbIncAdju.hashCode());
        result = prime * result + ((tafpbNetProfAdju == null) ? 0 : tafpbNetProfAdju.hashCode());
        result = prime * result + ((tafpbOperIncAdju == null) ? 0 : tafpbOperIncAdju.hashCode());
        result = prime * result + ((tafpbOperIncSpec == null) ? 0 : tafpbOperIncSpec.hashCode());
        result = prime * result + ((tafpbOperPayAdju == null) ? 0 : tafpbOperPayAdju.hashCode());
        result = prime * result + ((tafpbOperPaySpec == null) ? 0 : tafpbOperPaySpec.hashCode());
        result = prime * result + ((tafpbOperProfAdju == null) ? 0 : tafpbOperProfAdju.hashCode());
        result = prime * result + ((tafpbPareComIncAdju == null) ? 0 : tafpbPareComIncAdju.hashCode());
        result = prime * result + ((tafpbProfAdju == null) ? 0 : tafpbProfAdju.hashCode());
        result = prime * result + ((tafpbSpecDes == null) ? 0 : tafpbSpecDes.hashCode());
        result = prime * result + ((tafpbf110201 == null) ? 0 : tafpbf110201.hashCode());
        result = prime * result + ((tafpbf110213 == null) ? 0 : tafpbf110213.hashCode());
        result = prime * result + ((tafpbf110215 == null) ? 0 : tafpbf110215.hashCode());
        result = prime * result + ((tafpbf110224 == null) ? 0 : tafpbf110224.hashCode());
        result = prime * result + ((tafpbf110301 == null) ? 0 : tafpbf110301.hashCode());
        result = prime * result + ((tafpbf110601 == null) ? 0 : tafpbf110601.hashCode());
        result = prime * result + ((tafpbf110701 == null) ? 0 : tafpbf110701.hashCode());
        result = prime * result + ((tafpbf1110525 == null) ? 0 : tafpbf1110525.hashCode());
        result = prime * result + ((tafpbf200203 == null) ? 0 : tafpbf200203.hashCode());
        result = prime * result + ((tafpbf200213 == null) ? 0 : tafpbf200213.hashCode());
        result = prime * result + ((tafpbf200403 == null) ? 0 : tafpbf200403.hashCode());
        result = prime * result + ((tafpbf200413 == null) ? 0 : tafpbf200413.hashCode());
        result = prime * result + ((tafpbf200503 == null) ? 0 : tafpbf200503.hashCode());
        result = prime * result + ((tafpbf200504 == null) ? 0 : tafpbf200504.hashCode());
        result = prime * result + ((tafpbf200603 == null) ? 0 : tafpbf200603.hashCode());
        result = prime * result + ((tafpbf200703 == null) ? 0 : tafpbf200703.hashCode());
        result = prime * result + ((tafpbf200903 == null) ? 0 : tafpbf200903.hashCode());
        result = prime * result + ((tafpbf200913 == null) ? 0 : tafpbf200913.hashCode());
        result = prime * result + ((tafpbf201703 == null) ? 0 : tafpbf201703.hashCode());
        result = prime * result + ((tafpbf201803 == null) ? 0 : tafpbf201803.hashCode());
        result = prime * result + ((tafpbf201804 == null) ? 0 : tafpbf201804.hashCode());
        result = prime * result + ((tafpbf201903 == null) ? 0 : tafpbf201903.hashCode());
        result = prime * result + ((tafpby100000 == null) ? 0 : tafpby100000.hashCode());
        result = prime * result + ((tafpby110101 == null) ? 0 : tafpby110101.hashCode());
        result = prime * result + ((tafpby110111 == null) ? 0 : tafpby110111.hashCode());
        result = prime * result + ((tafpby110123 == null) ? 0 : tafpby110123.hashCode());
        result = prime * result + ((tafpby110301 == null) ? 0 : tafpby110301.hashCode());
        result = prime * result + ((tafpby110701 == null) ? 0 : tafpby110701.hashCode());
        result = prime * result + ((tafpby110711 == null) ? 0 : tafpby110711.hashCode());
        result = prime * result + ((tafpby110801 == null) ? 0 : tafpby110801.hashCode());
        result = prime * result + ((tafpby111101 == null) ? 0 : tafpby111101.hashCode());
        result = prime * result + ((tafpby111201 == null) ? 0 : tafpby111201.hashCode());
        result = prime * result + ((tafpby111511 == null) ? 0 : tafpby111511.hashCode());
        result = prime * result + ((tafpby111523 == null) ? 0 : tafpby111523.hashCode());
        result = prime * result + ((tafpby200000 == null) ? 0 : tafpby200000.hashCode());
        result = prime * result + ((tafpby300000 == null) ? 0 : tafpby300000.hashCode());
        result = prime * result + ((tafpby310101 == null) ? 0 : tafpby310101.hashCode());
        result = prime * result + ((tafpby310203 == null) ? 0 : tafpby310203.hashCode());
        result = prime * result + ((tafpby310213 == null) ? 0 : tafpby310213.hashCode());
        result = prime * result + ((tafpby310214 == null) ? 0 : tafpby310214.hashCode());
        result = prime * result + ((tafpby400000 == null) ? 0 : tafpby400000.hashCode());
        result = prime * result + ((tafpby410203 == null) ? 0 : tafpby410203.hashCode());
        result = prime * result + ((tafpby410204 == null) ? 0 : tafpby410204.hashCode());
        result = prime * result + ((tafpby410303 == null) ? 0 : tafpby410303.hashCode());
        result = prime * result + ((tafpby500000 == null) ? 0 : tafpby500000.hashCode());
        result = prime * result + ((tafpby510101 == null) ? 0 : tafpby510101.hashCode());
        result = prime * result + ((tafpby510201 == null) ? 0 : tafpby510201.hashCode());
        result = prime * result + ((tafpby510301 == null) ? 0 : tafpby510301.hashCode());
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
        BondFinFalProfTafpb other = (BondFinFalProfTafpb) obj;
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
        if (tafpb170102 == null) {
            if (other.tafpb170102 != null)
                return false;
        } else if (!tafpb170102.equals(other.tafpb170102))
            return false;
        if (tafpb170103 == null) {
            if (other.tafpb170103 != null)
                return false;
        } else if (!tafpb170103.equals(other.tafpb170103))
            return false;
        if (tafpb240801 == null) {
            if (other.tafpb240801 != null)
                return false;
        } else if (!tafpb240801.equals(other.tafpb240801))
            return false;
        if (tafpb240901 == null) {
            if (other.tafpb240901 != null)
                return false;
        } else if (!tafpb240901.equals(other.tafpb240901))
            return false;
        if (tafpbI170101 == null) {
            if (other.tafpbI170101 != null)
                return false;
        } else if (!tafpbI170101.equals(other.tafpbI170101))
            return false;
        if (tafpbIncAdju == null) {
            if (other.tafpbIncAdju != null)
                return false;
        } else if (!tafpbIncAdju.equals(other.tafpbIncAdju))
            return false;
        if (tafpbNetProfAdju == null) {
            if (other.tafpbNetProfAdju != null)
                return false;
        } else if (!tafpbNetProfAdju.equals(other.tafpbNetProfAdju))
            return false;
        if (tafpbOperIncAdju == null) {
            if (other.tafpbOperIncAdju != null)
                return false;
        } else if (!tafpbOperIncAdju.equals(other.tafpbOperIncAdju))
            return false;
        if (tafpbOperIncSpec == null) {
            if (other.tafpbOperIncSpec != null)
                return false;
        } else if (!tafpbOperIncSpec.equals(other.tafpbOperIncSpec))
            return false;
        if (tafpbOperPayAdju == null) {
            if (other.tafpbOperPayAdju != null)
                return false;
        } else if (!tafpbOperPayAdju.equals(other.tafpbOperPayAdju))
            return false;
        if (tafpbOperPaySpec == null) {
            if (other.tafpbOperPaySpec != null)
                return false;
        } else if (!tafpbOperPaySpec.equals(other.tafpbOperPaySpec))
            return false;
        if (tafpbOperProfAdju == null) {
            if (other.tafpbOperProfAdju != null)
                return false;
        } else if (!tafpbOperProfAdju.equals(other.tafpbOperProfAdju))
            return false;
        if (tafpbPareComIncAdju == null) {
            if (other.tafpbPareComIncAdju != null)
                return false;
        } else if (!tafpbPareComIncAdju.equals(other.tafpbPareComIncAdju))
            return false;
        if (tafpbProfAdju == null) {
            if (other.tafpbProfAdju != null)
                return false;
        } else if (!tafpbProfAdju.equals(other.tafpbProfAdju))
            return false;
        if (tafpbSpecDes == null) {
            if (other.tafpbSpecDes != null)
                return false;
        } else if (!tafpbSpecDes.equals(other.tafpbSpecDes))
            return false;
        if (tafpbf110201 == null) {
            if (other.tafpbf110201 != null)
                return false;
        } else if (!tafpbf110201.equals(other.tafpbf110201))
            return false;
        if (tafpbf110213 == null) {
            if (other.tafpbf110213 != null)
                return false;
        } else if (!tafpbf110213.equals(other.tafpbf110213))
            return false;
        if (tafpbf110215 == null) {
            if (other.tafpbf110215 != null)
                return false;
        } else if (!tafpbf110215.equals(other.tafpbf110215))
            return false;
        if (tafpbf110224 == null) {
            if (other.tafpbf110224 != null)
                return false;
        } else if (!tafpbf110224.equals(other.tafpbf110224))
            return false;
        if (tafpbf110301 == null) {
            if (other.tafpbf110301 != null)
                return false;
        } else if (!tafpbf110301.equals(other.tafpbf110301))
            return false;
        if (tafpbf110601 == null) {
            if (other.tafpbf110601 != null)
                return false;
        } else if (!tafpbf110601.equals(other.tafpbf110601))
            return false;
        if (tafpbf110701 == null) {
            if (other.tafpbf110701 != null)
                return false;
        } else if (!tafpbf110701.equals(other.tafpbf110701))
            return false;
        if (tafpbf1110525 == null) {
            if (other.tafpbf1110525 != null)
                return false;
        } else if (!tafpbf1110525.equals(other.tafpbf1110525))
            return false;
        if (tafpbf200203 == null) {
            if (other.tafpbf200203 != null)
                return false;
        } else if (!tafpbf200203.equals(other.tafpbf200203))
            return false;
        if (tafpbf200213 == null) {
            if (other.tafpbf200213 != null)
                return false;
        } else if (!tafpbf200213.equals(other.tafpbf200213))
            return false;
        if (tafpbf200403 == null) {
            if (other.tafpbf200403 != null)
                return false;
        } else if (!tafpbf200403.equals(other.tafpbf200403))
            return false;
        if (tafpbf200413 == null) {
            if (other.tafpbf200413 != null)
                return false;
        } else if (!tafpbf200413.equals(other.tafpbf200413))
            return false;
        if (tafpbf200503 == null) {
            if (other.tafpbf200503 != null)
                return false;
        } else if (!tafpbf200503.equals(other.tafpbf200503))
            return false;
        if (tafpbf200504 == null) {
            if (other.tafpbf200504 != null)
                return false;
        } else if (!tafpbf200504.equals(other.tafpbf200504))
            return false;
        if (tafpbf200603 == null) {
            if (other.tafpbf200603 != null)
                return false;
        } else if (!tafpbf200603.equals(other.tafpbf200603))
            return false;
        if (tafpbf200703 == null) {
            if (other.tafpbf200703 != null)
                return false;
        } else if (!tafpbf200703.equals(other.tafpbf200703))
            return false;
        if (tafpbf200903 == null) {
            if (other.tafpbf200903 != null)
                return false;
        } else if (!tafpbf200903.equals(other.tafpbf200903))
            return false;
        if (tafpbf200913 == null) {
            if (other.tafpbf200913 != null)
                return false;
        } else if (!tafpbf200913.equals(other.tafpbf200913))
            return false;
        if (tafpbf201703 == null) {
            if (other.tafpbf201703 != null)
                return false;
        } else if (!tafpbf201703.equals(other.tafpbf201703))
            return false;
        if (tafpbf201803 == null) {
            if (other.tafpbf201803 != null)
                return false;
        } else if (!tafpbf201803.equals(other.tafpbf201803))
            return false;
        if (tafpbf201804 == null) {
            if (other.tafpbf201804 != null)
                return false;
        } else if (!tafpbf201804.equals(other.tafpbf201804))
            return false;
        if (tafpbf201903 == null) {
            if (other.tafpbf201903 != null)
                return false;
        } else if (!tafpbf201903.equals(other.tafpbf201903))
            return false;
        if (tafpby100000 == null) {
            if (other.tafpby100000 != null)
                return false;
        } else if (!tafpby100000.equals(other.tafpby100000))
            return false;
        if (tafpby110101 == null) {
            if (other.tafpby110101 != null)
                return false;
        } else if (!tafpby110101.equals(other.tafpby110101))
            return false;
        if (tafpby110111 == null) {
            if (other.tafpby110111 != null)
                return false;
        } else if (!tafpby110111.equals(other.tafpby110111))
            return false;
        if (tafpby110123 == null) {
            if (other.tafpby110123 != null)
                return false;
        } else if (!tafpby110123.equals(other.tafpby110123))
            return false;
        if (tafpby110301 == null) {
            if (other.tafpby110301 != null)
                return false;
        } else if (!tafpby110301.equals(other.tafpby110301))
            return false;
        if (tafpby110701 == null) {
            if (other.tafpby110701 != null)
                return false;
        } else if (!tafpby110701.equals(other.tafpby110701))
            return false;
        if (tafpby110711 == null) {
            if (other.tafpby110711 != null)
                return false;
        } else if (!tafpby110711.equals(other.tafpby110711))
            return false;
        if (tafpby110801 == null) {
            if (other.tafpby110801 != null)
                return false;
        } else if (!tafpby110801.equals(other.tafpby110801))
            return false;
        if (tafpby111101 == null) {
            if (other.tafpby111101 != null)
                return false;
        } else if (!tafpby111101.equals(other.tafpby111101))
            return false;
        if (tafpby111201 == null) {
            if (other.tafpby111201 != null)
                return false;
        } else if (!tafpby111201.equals(other.tafpby111201))
            return false;
        if (tafpby111511 == null) {
            if (other.tafpby111511 != null)
                return false;
        } else if (!tafpby111511.equals(other.tafpby111511))
            return false;
        if (tafpby111523 == null) {
            if (other.tafpby111523 != null)
                return false;
        } else if (!tafpby111523.equals(other.tafpby111523))
            return false;
        if (tafpby200000 == null) {
            if (other.tafpby200000 != null)
                return false;
        } else if (!tafpby200000.equals(other.tafpby200000))
            return false;
        if (tafpby300000 == null) {
            if (other.tafpby300000 != null)
                return false;
        } else if (!tafpby300000.equals(other.tafpby300000))
            return false;
        if (tafpby310101 == null) {
            if (other.tafpby310101 != null)
                return false;
        } else if (!tafpby310101.equals(other.tafpby310101))
            return false;
        if (tafpby310203 == null) {
            if (other.tafpby310203 != null)
                return false;
        } else if (!tafpby310203.equals(other.tafpby310203))
            return false;
        if (tafpby310213 == null) {
            if (other.tafpby310213 != null)
                return false;
        } else if (!tafpby310213.equals(other.tafpby310213))
            return false;
        if (tafpby310214 == null) {
            if (other.tafpby310214 != null)
                return false;
        } else if (!tafpby310214.equals(other.tafpby310214))
            return false;
        if (tafpby400000 == null) {
            if (other.tafpby400000 != null)
                return false;
        } else if (!tafpby400000.equals(other.tafpby400000))
            return false;
        if (tafpby410203 == null) {
            if (other.tafpby410203 != null)
                return false;
        } else if (!tafpby410203.equals(other.tafpby410203))
            return false;
        if (tafpby410204 == null) {
            if (other.tafpby410204 != null)
                return false;
        } else if (!tafpby410204.equals(other.tafpby410204))
            return false;
        if (tafpby410303 == null) {
            if (other.tafpby410303 != null)
                return false;
        } else if (!tafpby410303.equals(other.tafpby410303))
            return false;
        if (tafpby500000 == null) {
            if (other.tafpby500000 != null)
                return false;
        } else if (!tafpby500000.equals(other.tafpby500000))
            return false;
        if (tafpby510101 == null) {
            if (other.tafpby510101 != null)
                return false;
        } else if (!tafpby510101.equals(other.tafpby510101))
            return false;
        if (tafpby510201 == null) {
            if (other.tafpby510201 != null)
                return false;
        } else if (!tafpby510201.equals(other.tafpby510201))
            return false;
        if (tafpby510301 == null) {
            if (other.tafpby510301 != null)
                return false;
        } else if (!tafpby510301.equals(other.tafpby510301))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BondFinFalProfTafpb [" + (id != null ? "id=" + id + ", " : "")
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
                + (tafpby100000 != null ? "tafpby100000=" + tafpby100000 + ", " : "")
                + (tafpby110101 != null ? "tafpby110101=" + tafpby110101 + ", " : "")
                + (tafpby110111 != null ? "tafpby110111=" + tafpby110111 + ", " : "")
                + (tafpby110123 != null ? "tafpby110123=" + tafpby110123 + ", " : "")
                + (tafpby110301 != null ? "tafpby110301=" + tafpby110301 + ", " : "")
                + (tafpby111511 != null ? "tafpby111511=" + tafpby111511 + ", " : "")
                + (tafpby111523 != null ? "tafpby111523=" + tafpby111523 + ", " : "")
                + (tafpbf1110525 != null ? "tafpbf1110525=" + tafpbf1110525 + ", " : "")
                + (tafpbf110601 != null ? "tafpbf110601=" + tafpbf110601 + ", " : "")
                + (tafpbf110701 != null ? "tafpbf110701=" + tafpbf110701 + ", " : "")
                + (tafpbf110201 != null ? "tafpbf110201=" + tafpbf110201 + ", " : "")
                + (tafpbf110213 != null ? "tafpbf110213=" + tafpbf110213 + ", " : "")
                + (tafpbf110215 != null ? "tafpbf110215=" + tafpbf110215 + ", " : "")
                + (tafpbf110224 != null ? "tafpbf110224=" + tafpbf110224 + ", " : "")
                + (tafpbf110301 != null ? "tafpbf110301=" + tafpbf110301 + ", " : "")
                + (tafpby110701 != null ? "tafpby110701=" + tafpby110701 + ", " : "")
                + (tafpby110711 != null ? "tafpby110711=" + tafpby110711 + ", " : "")
                + (tafpby110801 != null ? "tafpby110801=" + tafpby110801 + ", " : "")
                + (tafpby111101 != null ? "tafpby111101=" + tafpby111101 + ", " : "")
                + (tafpby111201 != null ? "tafpby111201=" + tafpby111201 + ", " : "")
                + (tafpbOperIncSpec != null ? "tafpbOperIncSpec=" + tafpbOperIncSpec + ", " : "")
                + (tafpbOperIncAdju != null ? "tafpbOperIncAdju=" + tafpbOperIncAdju + ", " : "")
                + (tafpby200000 != null ? "tafpby200000=" + tafpby200000 + ", " : "")
                + (tafpbf200603 != null ? "tafpbf200603=" + tafpbf200603 + ", " : "")
                + (tafpbf200203 != null ? "tafpbf200203=" + tafpbf200203 + ", " : "")
                + (tafpbf200213 != null ? "tafpbf200213=" + tafpbf200213 + ", " : "")
                + (tafpbf200403 != null ? "tafpbf200403=" + tafpbf200403 + ", " : "")
                + (tafpbf200413 != null ? "tafpbf200413=" + tafpbf200413 + ", " : "")
                + (tafpbf200703 != null ? "tafpbf200703=" + tafpbf200703 + ", " : "")
                + (tafpbf200503 != null ? "tafpbf200503=" + tafpbf200503 + ", " : "")
                + (tafpbf200504 != null ? "tafpbf200504=" + tafpbf200504 + ", " : "")
                + (tafpbf201903 != null ? "tafpbf201903=" + tafpbf201903 + ", " : "")
                + (tafpbf200903 != null ? "tafpbf200903=" + tafpbf200903 + ", " : "")
                + (tafpbf200913 != null ? "tafpbf200913=" + tafpbf200913 + ", " : "")
                + (tafpbf201703 != null ? "tafpbf201703=" + tafpbf201703 + ", " : "")
                + (tafpbf201803 != null ? "tafpbf201803=" + tafpbf201803 + ", " : "")
                + (tafpbOperPaySpec != null ? "tafpbOperPaySpec=" + tafpbOperPaySpec + ", " : "")
                + (tafpbOperPayAdju != null ? "tafpbOperPayAdju=" + tafpbOperPayAdju + ", " : "")
                + (tafpbf201804 != null ? "tafpbf201804=" + tafpbf201804 + ", " : "")
                + (tafpbOperProfAdju != null ? "tafpbOperProfAdju=" + tafpbOperProfAdju + ", " : "")
                + (tafpby300000 != null ? "tafpby300000=" + tafpby300000 + ", " : "")
                + (tafpby310101 != null ? "tafpby310101=" + tafpby310101 + ", " : "")
                + (tafpby310203 != null ? "tafpby310203=" + tafpby310203 + ", " : "")
                + (tafpby310213 != null ? "tafpby310213=" + tafpby310213 + ", " : "")
                + (tafpby310214 != null ? "tafpby310214=" + tafpby310214 + ", " : "")
                + (tafpbProfAdju != null ? "tafpbProfAdju=" + tafpbProfAdju + ", " : "")
                + (tafpby400000 != null ? "tafpby400000=" + tafpby400000 + ", " : "")
                + (tafpby410203 != null ? "tafpby410203=" + tafpby410203 + ", " : "")
                + (tafpby410204 != null ? "tafpby410204=" + tafpby410204 + ", " : "")
                + (tafpby410303 != null ? "tafpby410303=" + tafpby410303 + ", " : "")
                + (tafpbNetProfAdju != null ? "tafpbNetProfAdju=" + tafpbNetProfAdju + ", " : "")
                + (tafpby500000 != null ? "tafpby500000=" + tafpby500000 + ", " : "")
                + (tafpby510101 != null ? "tafpby510101=" + tafpby510101 + ", " : "")
                + (tafpby510201 != null ? "tafpby510201=" + tafpby510201 + ", " : "")
                + (tafpby510301 != null ? "tafpby510301=" + tafpby510301 + ", " : "")
                + (tafpbIncAdju != null ? "tafpbIncAdju=" + tafpbIncAdju + ", " : "")
                + (tafpbI170101 != null ? "tafpbI170101=" + tafpbI170101 + ", " : "")
                + (tafpb170102 != null ? "tafpb170102=" + tafpb170102 + ", " : "")
                + (tafpb170103 != null ? "tafpb170103=" + tafpb170103 + ", " : "")
                + (tafpbPareComIncAdju != null ? "tafpbPareComIncAdju=" + tafpbPareComIncAdju + ", " : "")
                + (tafpb240801 != null ? "tafpb240801=" + tafpb240801 + ", " : "")
                + (tafpb240901 != null ? "tafpb240901=" + tafpb240901 + ", " : "")
                + (tafpbSpecDes != null ? "tafpbSpecDes=" + tafpbSpecDes + ", " : "")
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
        selectSql += ",TAFPBY_100000";
        selectSql += ",TAFPBY_110101";
        selectSql += ",TAFPBY_110111";
        selectSql += ",TAFPBY_110123";
        selectSql += ",TAFPBY_110301";
        selectSql += ",TAFPBY_111511";
        selectSql += ",TAFPBY_111523";
        selectSql += ",TAFPBF_1110525";
        selectSql += ",TAFPBF_110601";
        selectSql += ",TAFPBF_110701";
        selectSql += ",TAFPBF_110201";
        selectSql += ",TAFPBF_110213";
        selectSql += ",TAFPBF_110215";
        selectSql += ",TAFPBF_110224";
        selectSql += ",TAFPBF_110301";
        selectSql += ",TAFPBY_110701";
        selectSql += ",TAFPBY_110711";
        selectSql += ",TAFPBY_110801";
        selectSql += ",TAFPBY_111101";
        selectSql += ",TAFPBY_111201";
        selectSql += ",TAFPB_OPER_INC_SPEC";
        selectSql += ",TAFPB_OPER_INC_ADJU";
        selectSql += ",TAFPBY_200000";
        selectSql += ",TAFPBF_200603";
        selectSql += ",TAFPBF_200203";
        selectSql += ",TAFPBF_200213";
        selectSql += ",TAFPBF_200403";
        selectSql += ",TAFPBF_200413";
        selectSql += ",TAFPBF_200703";
        selectSql += ",TAFPBF_200503";
        selectSql += ",TAFPBF_200504";
        selectSql += ",TAFPBF_201903";
        selectSql += ",TAFPBF_200903";
        selectSql += ",TAFPBF_200913";
        selectSql += ",TAFPBF_201703";
        selectSql += ",TAFPBF_201803";
        selectSql += ",TAFPB_OPER_PAY_SPEC";
        selectSql += ",TAFPB_OPER_PAY_ADJU";
        selectSql += ",TAFPBF_201804";
        selectSql += ",TAFPB_OPER_PROF_ADJU";
        selectSql += ",TAFPBY_300000";
        selectSql += ",TAFPBY_310101";
        selectSql += ",TAFPBY_310203";
        selectSql += ",TAFPBY_310213";
        selectSql += ",TAFPBY_310214";
        selectSql += ",TAFPB_PROF_ADJU";
        selectSql += ",TAFPBY_400000";
        selectSql += ",TAFPBY_410203";
        selectSql += ",TAFPBY_410204";
        selectSql += ",TAFPBY_410303";
        selectSql += ",TAFPB_NET_PROF_ADJU";
        selectSql += ",TAFPBY_500000";
        selectSql += ",TAFPBY_510101";
        selectSql += ",TAFPBY_510201";
        selectSql += ",TAFPBY_510301";
        selectSql += ",TAFPB_INC_ADJU";
        selectSql += ",TAFPB_I_170101";
        selectSql += ",TAFPB_170102";
        selectSql += ",TAFPB_170103";
        selectSql += ",TAFPB_PARE_COM_INC_ADJU";
        selectSql += ",TAFPB_240801";
        selectSql += ",TAFPB_240901";
        selectSql += ",TAFPB_SPEC_DES";
        selectSql += ",INFO_PUB_DATE";
 	   return selectSql.substring(1);
     }
 	
 	public String createEqualsSql(String whereSql){
 	   String selectSql = createSelectColumnSql();       
        String sql = "select count(1) from "
        +" (select * from (select "+selectSql+" from bond_ccxe.d_bond_fin_fal_prof_tafpb where "+whereSql
        +" order by ccxeid desc limit 1) a union select * from (select "+selectSql+" from bond_ccxe_snapshot.d_bond_fin_fal_prof_tafpb where "+whereSql+" order by ccxeid desc limit 1) a) a";
        return sql;
     }
     
     public String createEqualsSql(String id, Long bondUniCode, Long comUniCode, String endDate){
 	   String selectSql = createSelectColumnSql();
 	  String whereSql = " id='" + id + "' and bond_uni_code=" + bondUniCode 
 	         + " and com_uni_code=" + comUniCode 
 	         + " and end_date='" + endDate + "'";
                
        String sql = "select count(1) from "
        +" (select * from (select "+selectSql+" from bond_ccxe.d_bond_fin_fal_prof_tafpb where "+whereSql
        +" order by ccxeid desc limit 1) a union select * from (select "+selectSql+" from bond_ccxe_snapshot.d_bond_fin_fal_prof_tafpb where "+whereSql+" order by ccxeid desc limit 1) a) a";
        return sql;
     }
}
