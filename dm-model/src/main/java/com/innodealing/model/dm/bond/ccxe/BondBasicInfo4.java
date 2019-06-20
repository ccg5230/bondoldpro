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
@Table(name="d_bond_basic_info_4")
public class BondBasicInfo4 implements Serializable{
    private static final long serialVersionUID = 1L;

    
    /**
     * 
     */
    @Column(name="ID", length=36)
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
    @Column(name="BOND_ID", length=19)
    private Long bondId;
    
    /**
     * 
     */
	 @Id
    @Column(name="BOND_UNI_CODE", length=19)
    private Long bondUniCode;
    
    /**
     * 
     */
    @Column(name="BOND_CODE", length=20)
    private String bondCode;
    
    /**
     * 
     */
    @Column(name="BOND_SHORT_NAME", length=200)
    private String bondShortName;
    
    /**
     * 
     */
    @Column(name="BOND_FULL_NAME", length=200)
    private String bondFullName;
    
    /**
     * 
     */
    @Column(name="ISIN_CODE", length=12)
    private String isinCode;
    
    /**
     * 
     */
    @Column(name="SPE_SHORT_NAME", length=100)
    private String speShortName;
    
    /**
     * 
     */
    @Column(name="ORG_UNI_CODE", length=19)
    private Long orgUniCode;
    
    /**
     * 
     */
    @Column(name="GURA_NAME", length=65535)
    private String guraName;
    
    /**
     * 
     */
    @Column(name="UNDE_NAME", length=65535)
    private String undeName;
    
    /**
     * 
     */
    @Column(name="UNDE_CLS_PAR", length=10)
    private Integer undeClsPar;
    
    /**
     * 
     */
    @Column(name="BOND_PAR_VAL", length=18)
    private BigDecimal bondParVal;
    
    /**
     * 
     */
    @Column(name="BOND_MATU", length=18)
    private BigDecimal bondMatu;
    
    /**
     * 
     */
    @Column(name="MATU_UNIT_PAR", length=10)
    private Integer matuUnitPar;
    
    /**
     * 
     */
    @Column(name="BOND_TYPE_PAR", length=10)
    private Integer bondTypePar;
    
    /**
     * 
     */
    @Column(name="BOND_FORM_PAR", length=10)
    private Integer bondFormPar;
    
    /**
     * 
     */
    @Column(name="OPT_DES", length=65535)
    private String optDes;
    
    /**
     * 
     */
    @Column(name="IS_REDEM_PAR", length=10)
    private Integer isRedemPar;
    
    /**
     * 
     */
    @Column(name="IS_RESA_PAR", length=10)
    private Integer isResaPar;
    
    /**
     * 
     */
    @Column(name="IS_GUAR_PAR", length=10)
    private Integer isGuarPar;
    
    /**
     * 
     */
    @Column(name="RATE_DES", length=65535)
    private String rateDes;
    
    /**
     * 
     */
    @Column(name="RATE_TYPE_PAR", length=10)
    private Integer rateTypePar;
    
    /**
     * 
     */
    @Column(name="IS_SEGM_PAR", length=10)
    private Integer isSegmPar;
    
    /**
     * 
     */
    @Column(name="INTE_PAY_CLS_PAR", length=10)
    private Integer intePayClsPar;
    
    /**
     * 
     */
    @Column(name="INTE_PAY_METH", length=65535)
    private String intePayMeth;
    
    /**
     * 
     */
    @Column(name="INTE_PAY_FREQ", length=10)
    private Integer intePayFreq;
    
    /**
     * 
     */
    @Column(name="INTE_CALCU_CLS_PAR", length=10)
    private Integer inteCalcuClsPar;
    
    /**
     * 
     */
    @Column(name="SIMP_COMP_INTE_PAR", length=10)
    private Integer simpCompIntePar;
    
    /**
     * 
     */
    @Column(name="INTE_START_DATE", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date inteStartDate;
    
    /**
     * 
     */
    @Column(name="THEO_END_DATE", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date theoEndDate;
    
    /**
     * 
     */
    @Column(name="ACTU_END_DATE", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date actuEndDate;
    
    /**
     * 
     */
    @Column(name="YEAR_PAY_DATE", length=40)
    private String yearPayDate;
    
    /**
     * 
     */
    @Column(name="YEAR_PAY_MATU", length=65535)
    private String yearPayMatu;
    
    /**
     * 
     */
    @Column(name="MATU_PAY_DATE", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date matuPayDate;
    
    /**
     * 
     */
    @Column(name="PAY_MATU", length=65535)
    private String payMatu;
    
    /**
     * 
     */
    @Column(name="EXER_PAY_DATE", length=65535)
    private String exerPayDate;
    
    /**
     * 
     */
    @Column(name="REPAY_CLS_PAY_PAR", length=10)
    private Integer repayClsPayPar;
    
    /**
     * 
     */
    @Column(name="PAY_CLS_DES", length=65535)
    private String payClsDes;
    
    /**
     * 
     */
    @Column(name="ISS_COUP_RATE", length=22)
    private Double issCoupRate;
    
    /**
     * 
     */
    @Column(name="BASE_RATE_PAR", length=10)
    private Integer baseRatePar;
    
    /**
     * 
     */
    @Column(name="BASE_RATE", length=22)
    private Double baseRate;
    
    /**
     * 
     */
    @Column(name="BAS_SPR", length=22)
    private Double basSpr;
    
    /**
     * 
     */
    @Column(name="OPT_EXTRA_SPR", length=22)
    private Double optExtraSpr;
    
    /**
     * 
     */
    @Column(name="EXTRA_SPR_SEQ_NUM", length=10)
    private Integer extraSprSeqNum;
    
    /**
     * 
     */
    @Column(name="BOND_ISS_YEAR", length=10)
    private Integer bondIssYear;
    
    /**
     * 
     */
    @Column(name="ISS_DECL_DATE", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date issDeclDate;
    
    /**
     * 
     */
    @Column(name="ISS_START_DATE", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date issStartDate;
    
    /**
     * 
     */
    @Column(name="ISS_END_DATE", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date issEndDate;
    
    /**
     * 
     */
    @Column(name="ISS_OBJ", length=65535)
    private String issObj;
    
    /**
     * 
     */
    @Column(name="ISS_CLS", length=65535)
    private String issCls;
    
    /**
     * 
     */
    @Column(name="ISS_PRI", length=18)
    private BigDecimal issPri;
    
    /**
     * 
     */
    @Column(name="PLAN_ISS_AMUT", length=20)
    private BigDecimal planIssAmut;
    
    /**
     * 
     */
    @Column(name="COLL_CAP_PURP", length=65535)
    private String collCapPurp;
    
    /**
     * 
     */
    @Column(name="ACTU_ISS_AMUT", length=20)
    private BigDecimal actuIssAmut;
    
    /**
     * 
     */
    @Column(name="ISS_STA_PAR", length=10)
    private Integer issStaPar;
    
    /**
     * 
     */
    @Column(name="IS_LIST_PAR", length=10)
    private Integer isListPar;
    
    /**
     * 
     */
    @Column(name="IS_CROS_MAR_PAR", length=10)
    private Integer isCrosMarPar;
    
    /**
     * 
     */
    @Column(name="LIST_DECL_DATE", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date listDeclDate;
    
    /**
     * 
     */
    @Column(name="LIST_DATE", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date listDate;
    
    /**
     * 
     */
    @Column(name="SEC_MAR_PAR", length=10)
    private Integer secMarPar;
    
    /**
     * 
     */
    @Column(name="LIST_SECT_PAR", length=10)
    private Integer listSectPar;
    
    /**
     * 
     */
    @Column(name="LIST_STA_PAR", length=10)
    private Integer listStaPar;
    
    /**
     * 
     */
    @Column(name="THEO_DELIST_DATE", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date theoDelistDate;
    
    /**
     * 
     */
    @Column(name="ACTU_DELIST_DATE", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date actuDelistDate;
    
    /**
     * 
     */
    @Column(name="CIRCU_AMUT", length=18)
    private BigDecimal circuAmut;
    
    /**
     * 
     */
    @Column(name="NEW_SIZE", length=20)
    private Double newSize;
    
    /**
     * 
     */
    @Column(name="ISS_NAME", length=65535)
    private String issName;
    
    /**
     * 
     */
    @Column(name="CURR_STATUS", length=10)
    private Integer currStatus;
    
    /**
     * 
     */
    @Column(name="NEW_COUP_RATE", length=22)
    private Double newCoupRate;
    
    /**
     * 
     */
    @Column(name="NEW_RATE", length=65535)
    private String newRate;
    
    /**
     * 
     */
    @Column(name="DEBT_REG_DATE", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date debtRegDate;
    
    /**
     * 
     */
    @Column(name="PAY_START_DATE", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date payStartDate;
    
    /**
     * 
     */
    @Column(name="PAY_END_DATE", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date payEndDate;
    
    /**
     * 
     */
    @Column(name="CURY_TYPE_PAR", length=10)
    private Integer curyTypePar;
    
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
    
    public void setBondId(Long bondId){
       this.bondId = bondId;
    }
    
    public Long getBondId(){
       return this.bondId;
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
    
    public void setBondFullName(String bondFullName){
       this.bondFullName = bondFullName;
    }
    
    public String getBondFullName(){
       return this.bondFullName;
    }
    
    public void setIsinCode(String isinCode){
       this.isinCode = isinCode;
    }
    
    public String getIsinCode(){
       return this.isinCode;
    }
    
    public void setSpeShortName(String speShortName){
       this.speShortName = speShortName;
    }
    
    public String getSpeShortName(){
       return this.speShortName;
    }
    
    public void setOrgUniCode(Long orgUniCode){
       this.orgUniCode = orgUniCode;
    }
    
    public Long getOrgUniCode(){
       return this.orgUniCode;
    }
    
    public void setGuraName(String guraName){
       this.guraName = guraName;
    }
    
    public String getGuraName(){
       return this.guraName;
    }
    
    public void setUndeName(String undeName){
       this.undeName = undeName;
    }
    
    public String getUndeName(){
       return this.undeName;
    }
    
    public void setUndeClsPar(Integer undeClsPar){
       this.undeClsPar = undeClsPar;
    }
    
    public Integer getUndeClsPar(){
       return this.undeClsPar;
    }
    
    public void setBondParVal(BigDecimal bondParVal){
       this.bondParVal = bondParVal;
    }
    
    public BigDecimal getBondParVal(){
       return this.bondParVal;
    }
    
    public void setBondMatu(BigDecimal bondMatu){
       this.bondMatu = bondMatu;
    }
    
    public BigDecimal getBondMatu(){
       return this.bondMatu;
    }
    
    public void setMatuUnitPar(Integer matuUnitPar){
       this.matuUnitPar = matuUnitPar;
    }
    
    public Integer getMatuUnitPar(){
       return this.matuUnitPar;
    }
    
    public void setBondTypePar(Integer bondTypePar){
       this.bondTypePar = bondTypePar;
    }
    
    public Integer getBondTypePar(){
       return this.bondTypePar;
    }
    
    public void setBondFormPar(Integer bondFormPar){
       this.bondFormPar = bondFormPar;
    }
    
    public Integer getBondFormPar(){
       return this.bondFormPar;
    }
    
    public void setOptDes(String optDes){
       this.optDes = optDes;
    }
    
    public String getOptDes(){
       return this.optDes;
    }
    
    public void setIsRedemPar(Integer isRedemPar){
       this.isRedemPar = isRedemPar;
    }
    
    public Integer getIsRedemPar(){
       return this.isRedemPar;
    }
    
    public void setIsResaPar(Integer isResaPar){
       this.isResaPar = isResaPar;
    }
    
    public Integer getIsResaPar(){
       return this.isResaPar;
    }
    
    public void setIsGuarPar(Integer isGuarPar){
       this.isGuarPar = isGuarPar;
    }
    
    public Integer getIsGuarPar(){
       return this.isGuarPar;
    }
    
    public void setRateDes(String rateDes){
       this.rateDes = rateDes;
    }
    
    public String getRateDes(){
       return this.rateDes;
    }
    
    public void setRateTypePar(Integer rateTypePar){
       this.rateTypePar = rateTypePar;
    }
    
    public Integer getRateTypePar(){
       return this.rateTypePar;
    }
    
    public void setIsSegmPar(Integer isSegmPar){
       this.isSegmPar = isSegmPar;
    }
    
    public Integer getIsSegmPar(){
       return this.isSegmPar;
    }
    
    public void setIntePayClsPar(Integer intePayClsPar){
       this.intePayClsPar = intePayClsPar;
    }
    
    public Integer getIntePayClsPar(){
       return this.intePayClsPar;
    }
    
    public void setIntePayMeth(String intePayMeth){
       this.intePayMeth = intePayMeth;
    }
    
    public String getIntePayMeth(){
       return this.intePayMeth;
    }
    
    public void setIntePayFreq(Integer intePayFreq){
       this.intePayFreq = intePayFreq;
    }
    
    public Integer getIntePayFreq(){
       return this.intePayFreq;
    }
    
    public void setInteCalcuClsPar(Integer inteCalcuClsPar){
       this.inteCalcuClsPar = inteCalcuClsPar;
    }
    
    public Integer getInteCalcuClsPar(){
       return this.inteCalcuClsPar;
    }
    
    public void setSimpCompIntePar(Integer simpCompIntePar){
       this.simpCompIntePar = simpCompIntePar;
    }
    
    public Integer getSimpCompIntePar(){
       return this.simpCompIntePar;
    }
    
    public void setInteStartDate(Date inteStartDate){
       this.inteStartDate = inteStartDate;
    }
    
    public Date getInteStartDate(){
       return this.inteStartDate;
    }
    
    public void setTheoEndDate(Date theoEndDate){
       this.theoEndDate = theoEndDate;
    }
    
    public Date getTheoEndDate(){
       return this.theoEndDate;
    }
    
    public void setActuEndDate(Date actuEndDate){
       this.actuEndDate = actuEndDate;
    }
    
    public Date getActuEndDate(){
       return this.actuEndDate;
    }
    
    public void setYearPayDate(String yearPayDate){
       this.yearPayDate = yearPayDate;
    }
    
    public String getYearPayDate(){
       return this.yearPayDate;
    }
    
    public void setYearPayMatu(String yearPayMatu){
       this.yearPayMatu = yearPayMatu;
    }
    
    public String getYearPayMatu(){
       return this.yearPayMatu;
    }
    
    public void setMatuPayDate(Date matuPayDate){
       this.matuPayDate = matuPayDate;
    }
    
    public Date getMatuPayDate(){
       return this.matuPayDate;
    }
    
    public void setPayMatu(String payMatu){
       this.payMatu = payMatu;
    }
    
    public String getPayMatu(){
       return this.payMatu;
    }
    
    public void setExerPayDate(String exerPayDate){
       this.exerPayDate = exerPayDate;
    }
    
    public String getExerPayDate(){
       return this.exerPayDate;
    }
    
    public void setRepayClsPayPar(Integer repayClsPayPar){
       this.repayClsPayPar = repayClsPayPar;
    }
    
    public Integer getRepayClsPayPar(){
       return this.repayClsPayPar;
    }
    
    public void setPayClsDes(String payClsDes){
       this.payClsDes = payClsDes;
    }
    
    public String getPayClsDes(){
       return this.payClsDes;
    }
    
    public void setIssCoupRate(Double issCoupRate){
       this.issCoupRate = issCoupRate;
    }
    
    public Double getIssCoupRate(){
       return this.issCoupRate;
    }
    
    public void setBaseRatePar(Integer baseRatePar){
       this.baseRatePar = baseRatePar;
    }
    
    public Integer getBaseRatePar(){
       return this.baseRatePar;
    }
    
    public void setBaseRate(Double baseRate){
       this.baseRate = baseRate;
    }
    
    public Double getBaseRate(){
       return this.baseRate;
    }
    
    public void setBasSpr(Double basSpr){
       this.basSpr = basSpr;
    }
    
    public Double getBasSpr(){
       return this.basSpr;
    }
    
    public void setOptExtraSpr(Double optExtraSpr){
       this.optExtraSpr = optExtraSpr;
    }
    
    public Double getOptExtraSpr(){
       return this.optExtraSpr;
    }
    
    public void setExtraSprSeqNum(Integer extraSprSeqNum){
       this.extraSprSeqNum = extraSprSeqNum;
    }
    
    public Integer getExtraSprSeqNum(){
       return this.extraSprSeqNum;
    }
    
    public void setBondIssYear(Integer bondIssYear){
       this.bondIssYear = bondIssYear;
    }
    
    public Integer getBondIssYear(){
       return this.bondIssYear;
    }
    
    public void setIssDeclDate(Date issDeclDate){
       this.issDeclDate = issDeclDate;
    }
    
    public Date getIssDeclDate(){
       return this.issDeclDate;
    }
    
    public void setIssStartDate(Date issStartDate){
       this.issStartDate = issStartDate;
    }
    
    public Date getIssStartDate(){
       return this.issStartDate;
    }
    
    public void setIssEndDate(Date issEndDate){
       this.issEndDate = issEndDate;
    }
    
    public Date getIssEndDate(){
       return this.issEndDate;
    }
    
    public void setIssObj(String issObj){
       this.issObj = issObj;
    }
    
    public String getIssObj(){
       return this.issObj;
    }
    
    public void setIssCls(String issCls){
       this.issCls = issCls;
    }
    
    public String getIssCls(){
       return this.issCls;
    }
    
    public void setIssPri(BigDecimal issPri){
       this.issPri = issPri;
    }
    
    public BigDecimal getIssPri(){
       return this.issPri;
    }
    
    public void setPlanIssAmut(BigDecimal planIssAmut){
       this.planIssAmut = planIssAmut;
    }
    
    public BigDecimal getPlanIssAmut(){
       return this.planIssAmut;
    }
    
    public void setCollCapPurp(String collCapPurp){
       this.collCapPurp = collCapPurp;
    }
    
    public String getCollCapPurp(){
       return this.collCapPurp;
    }
    
    public void setActuIssAmut(BigDecimal actuIssAmut){
       this.actuIssAmut = actuIssAmut;
    }
    
    public BigDecimal getActuIssAmut(){
       return this.actuIssAmut;
    }
    
    public void setIssStaPar(Integer issStaPar){
       this.issStaPar = issStaPar;
    }
    
    public Integer getIssStaPar(){
       return this.issStaPar;
    }
    
    public void setIsListPar(Integer isListPar){
       this.isListPar = isListPar;
    }
    
    public Integer getIsListPar(){
       return this.isListPar;
    }
    
    public void setIsCrosMarPar(Integer isCrosMarPar){
       this.isCrosMarPar = isCrosMarPar;
    }
    
    public Integer getIsCrosMarPar(){
       return this.isCrosMarPar;
    }
    
    public void setListDeclDate(Date listDeclDate){
       this.listDeclDate = listDeclDate;
    }
    
    public Date getListDeclDate(){
       return this.listDeclDate;
    }
    
    public void setListDate(Date listDate){
       this.listDate = listDate;
    }
    
    public Date getListDate(){
       return this.listDate;
    }
    
    public void setSecMarPar(Integer secMarPar){
       this.secMarPar = secMarPar;
    }
    
    public Integer getSecMarPar(){
       return this.secMarPar;
    }
    
    public void setListSectPar(Integer listSectPar){
       this.listSectPar = listSectPar;
    }
    
    public Integer getListSectPar(){
       return this.listSectPar;
    }
    
    public void setListStaPar(Integer listStaPar){
       this.listStaPar = listStaPar;
    }
    
    public Integer getListStaPar(){
       return this.listStaPar;
    }
    
    public void setTheoDelistDate(Date theoDelistDate){
       this.theoDelistDate = theoDelistDate;
    }
    
    public Date getTheoDelistDate(){
       return this.theoDelistDate;
    }
    
    public void setActuDelistDate(Date actuDelistDate){
       this.actuDelistDate = actuDelistDate;
    }
    
    public Date getActuDelistDate(){
       return this.actuDelistDate;
    }
    
    public void setCircuAmut(BigDecimal circuAmut){
       this.circuAmut = circuAmut;
    }
    
    public BigDecimal getCircuAmut(){
       return this.circuAmut;
    }
    
    public void setNewSize(Double newSize){
       this.newSize = newSize;
    }
    
    public Double getNewSize(){
       return this.newSize;
    }
    
    public void setIssName(String issName){
       this.issName = issName;
    }
    
    public String getIssName(){
       return this.issName;
    }
    
    public void setCurrStatus(Integer currStatus){
       this.currStatus = currStatus;
    }
    
    public Integer getCurrStatus(){
       return this.currStatus;
    }
    
    public void setNewCoupRate(Double newCoupRate){
       this.newCoupRate = newCoupRate;
    }
    
    public Double getNewCoupRate(){
       return this.newCoupRate;
    }
    
    public void setNewRate(String newRate){
       this.newRate = newRate;
    }
    
    public String getNewRate(){
       return this.newRate;
    }
    
    public void setDebtRegDate(Date debtRegDate){
       this.debtRegDate = debtRegDate;
    }
    
    public Date getDebtRegDate(){
       return this.debtRegDate;
    }
    
    public void setPayStartDate(Date payStartDate){
       this.payStartDate = payStartDate;
    }
    
    public Date getPayStartDate(){
       return this.payStartDate;
    }
    
    public void setPayEndDate(Date payEndDate){
       this.payEndDate = payEndDate;
    }
    
    public Date getPayEndDate(){
       return this.payEndDate;
    }
    
    public void setCuryTypePar(Integer curyTypePar){
       this.curyTypePar = curyTypePar;
    }
    
    public Integer getCuryTypePar(){
       return this.curyTypePar;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((actuDelistDate == null) ? 0 : actuDelistDate.hashCode());
        result = prime * result + ((actuEndDate == null) ? 0 : actuEndDate.hashCode());
        result = prime * result + ((actuIssAmut == null) ? 0 : actuIssAmut.hashCode());
        result = prime * result + ((basSpr == null) ? 0 : basSpr.hashCode());
        result = prime * result + ((baseRate == null) ? 0 : baseRate.hashCode());
        result = prime * result + ((baseRatePar == null) ? 0 : baseRatePar.hashCode());
        result = prime * result + ((bondCode == null) ? 0 : bondCode.hashCode());
        result = prime * result + ((bondFormPar == null) ? 0 : bondFormPar.hashCode());
        result = prime * result + ((bondFullName == null) ? 0 : bondFullName.hashCode());
        result = prime * result + ((bondId == null) ? 0 : bondId.hashCode());
        result = prime * result + ((bondIssYear == null) ? 0 : bondIssYear.hashCode());
        result = prime * result + ((bondMatu == null) ? 0 : bondMatu.hashCode());
        result = prime * result + ((bondParVal == null) ? 0 : bondParVal.hashCode());
        result = prime * result + ((bondShortName == null) ? 0 : bondShortName.hashCode());
        result = prime * result + ((bondTypePar == null) ? 0 : bondTypePar.hashCode());
        result = prime * result + ((bondUniCode == null) ? 0 : bondUniCode.hashCode());
        result = prime * result + ((ccxeid == null) ? 0 : ccxeid.hashCode());
        result = prime * result + ((circuAmut == null) ? 0 : circuAmut.hashCode());
        result = prime * result + ((collCapPurp == null) ? 0 : collCapPurp.hashCode());
        result = prime * result + ((createtime == null) ? 0 : createtime.hashCode());
        result = prime * result + ((currStatus == null) ? 0 : currStatus.hashCode());
        result = prime * result + ((curyTypePar == null) ? 0 : curyTypePar.hashCode());
        result = prime * result + ((debtRegDate == null) ? 0 : debtRegDate.hashCode());
        result = prime * result + ((exerPayDate == null) ? 0 : exerPayDate.hashCode());
        result = prime * result + ((extraSprSeqNum == null) ? 0 : extraSprSeqNum.hashCode());
        result = prime * result + ((guraName == null) ? 0 : guraName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((inteCalcuClsPar == null) ? 0 : inteCalcuClsPar.hashCode());
        result = prime * result + ((intePayClsPar == null) ? 0 : intePayClsPar.hashCode());
        result = prime * result + ((intePayFreq == null) ? 0 : intePayFreq.hashCode());
        result = prime * result + ((intePayMeth == null) ? 0 : intePayMeth.hashCode());
        result = prime * result + ((inteStartDate == null) ? 0 : inteStartDate.hashCode());
        result = prime * result + ((isCrosMarPar == null) ? 0 : isCrosMarPar.hashCode());
        result = prime * result + ((isGuarPar == null) ? 0 : isGuarPar.hashCode());
        result = prime * result + ((isListPar == null) ? 0 : isListPar.hashCode());
        result = prime * result + ((isRedemPar == null) ? 0 : isRedemPar.hashCode());
        result = prime * result + ((isResaPar == null) ? 0 : isResaPar.hashCode());
        result = prime * result + ((isSegmPar == null) ? 0 : isSegmPar.hashCode());
        result = prime * result + ((isinCode == null) ? 0 : isinCode.hashCode());
        result = prime * result + ((issCls == null) ? 0 : issCls.hashCode());
        result = prime * result + ((issCoupRate == null) ? 0 : issCoupRate.hashCode());
        result = prime * result + ((issDeclDate == null) ? 0 : issDeclDate.hashCode());
        result = prime * result + ((issEndDate == null) ? 0 : issEndDate.hashCode());
        result = prime * result + ((issName == null) ? 0 : issName.hashCode());
        result = prime * result + ((issObj == null) ? 0 : issObj.hashCode());
        result = prime * result + ((issPri == null) ? 0 : issPri.hashCode());
        result = prime * result + ((issStaPar == null) ? 0 : issStaPar.hashCode());
        result = prime * result + ((issStartDate == null) ? 0 : issStartDate.hashCode());
        result = prime * result + ((isvalid == null) ? 0 : isvalid.hashCode());
        result = prime * result + ((listDate == null) ? 0 : listDate.hashCode());
        result = prime * result + ((listDeclDate == null) ? 0 : listDeclDate.hashCode());
        result = prime * result + ((listSectPar == null) ? 0 : listSectPar.hashCode());
        result = prime * result + ((listStaPar == null) ? 0 : listStaPar.hashCode());
        result = prime * result + ((matuPayDate == null) ? 0 : matuPayDate.hashCode());
        result = prime * result + ((matuUnitPar == null) ? 0 : matuUnitPar.hashCode());
        result = prime * result + ((newCoupRate == null) ? 0 : newCoupRate.hashCode());
        result = prime * result + ((newRate == null) ? 0 : newRate.hashCode());
        result = prime * result + ((newSize == null) ? 0 : newSize.hashCode());
        result = prime * result + ((optDes == null) ? 0 : optDes.hashCode());
        result = prime * result + ((optExtraSpr == null) ? 0 : optExtraSpr.hashCode());
        result = prime * result + ((orgUniCode == null) ? 0 : orgUniCode.hashCode());
        result = prime * result + ((payClsDes == null) ? 0 : payClsDes.hashCode());
        result = prime * result + ((payEndDate == null) ? 0 : payEndDate.hashCode());
        result = prime * result + ((payMatu == null) ? 0 : payMatu.hashCode());
        result = prime * result + ((payStartDate == null) ? 0 : payStartDate.hashCode());
        result = prime * result + ((planIssAmut == null) ? 0 : planIssAmut.hashCode());
        result = prime * result + ((rateDes == null) ? 0 : rateDes.hashCode());
        result = prime * result + ((rateTypePar == null) ? 0 : rateTypePar.hashCode());
        result = prime * result + ((repayClsPayPar == null) ? 0 : repayClsPayPar.hashCode());
        result = prime * result + ((secMarPar == null) ? 0 : secMarPar.hashCode());
        result = prime * result + ((simpCompIntePar == null) ? 0 : simpCompIntePar.hashCode());
        result = prime * result + ((speShortName == null) ? 0 : speShortName.hashCode());
        result = prime * result + ((theoDelistDate == null) ? 0 : theoDelistDate.hashCode());
        result = prime * result + ((theoEndDate == null) ? 0 : theoEndDate.hashCode());
        result = prime * result + ((undeClsPar == null) ? 0 : undeClsPar.hashCode());
        result = prime * result + ((undeName == null) ? 0 : undeName.hashCode());
        result = prime * result + ((updatetime == null) ? 0 : updatetime.hashCode());
        result = prime * result + ((yearPayDate == null) ? 0 : yearPayDate.hashCode());
        result = prime * result + ((yearPayMatu == null) ? 0 : yearPayMatu.hashCode());
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
        BondBasicInfo4 other = (BondBasicInfo4) obj;
        if (actuDelistDate == null) {
            if (other.actuDelistDate != null)
                return false;
        } else if (!actuDelistDate.equals(other.actuDelistDate))
            return false;
        if (actuEndDate == null) {
            if (other.actuEndDate != null)
                return false;
        } else if (!actuEndDate.equals(other.actuEndDate))
            return false;
        if (actuIssAmut == null) {
            if (other.actuIssAmut != null)
                return false;
        } else if (!actuIssAmut.equals(other.actuIssAmut))
            return false;
        if (basSpr == null) {
            if (other.basSpr != null)
                return false;
        } else if (!basSpr.equals(other.basSpr))
            return false;
        if (baseRate == null) {
            if (other.baseRate != null)
                return false;
        } else if (!baseRate.equals(other.baseRate))
            return false;
        if (baseRatePar == null) {
            if (other.baseRatePar != null)
                return false;
        } else if (!baseRatePar.equals(other.baseRatePar))
            return false;
        if (bondCode == null) {
            if (other.bondCode != null)
                return false;
        } else if (!bondCode.equals(other.bondCode))
            return false;
        if (bondFormPar == null) {
            if (other.bondFormPar != null)
                return false;
        } else if (!bondFormPar.equals(other.bondFormPar))
            return false;
        if (bondFullName == null) {
            if (other.bondFullName != null)
                return false;
        } else if (!bondFullName.equals(other.bondFullName))
            return false;
        if (bondId == null) {
            if (other.bondId != null)
                return false;
        } else if (!bondId.equals(other.bondId))
            return false;
        if (bondIssYear == null) {
            if (other.bondIssYear != null)
                return false;
        } else if (!bondIssYear.equals(other.bondIssYear))
            return false;
        if (bondMatu == null) {
            if (other.bondMatu != null)
                return false;
        } else if (!bondMatu.equals(other.bondMatu))
            return false;
        if (bondParVal == null) {
            if (other.bondParVal != null)
                return false;
        } else if (!bondParVal.equals(other.bondParVal))
            return false;
        if (bondShortName == null) {
            if (other.bondShortName != null)
                return false;
        } else if (!bondShortName.equals(other.bondShortName))
            return false;
        if (bondTypePar == null) {
            if (other.bondTypePar != null)
                return false;
        } else if (!bondTypePar.equals(other.bondTypePar))
            return false;
        if (bondUniCode == null) {
            if (other.bondUniCode != null)
                return false;
        } else if (!bondUniCode.equals(other.bondUniCode))
            return false;
        if (ccxeid == null) {
            if (other.ccxeid != null)
                return false;
        } else if (!ccxeid.equals(other.ccxeid))
            return false;
        if (circuAmut == null) {
            if (other.circuAmut != null)
                return false;
        } else if (!circuAmut.equals(other.circuAmut))
            return false;
        if (collCapPurp == null) {
            if (other.collCapPurp != null)
                return false;
        } else if (!collCapPurp.equals(other.collCapPurp))
            return false;
        if (createtime == null) {
            if (other.createtime != null)
                return false;
        } else if (!createtime.equals(other.createtime))
            return false;
        if (currStatus == null) {
            if (other.currStatus != null)
                return false;
        } else if (!currStatus.equals(other.currStatus))
            return false;
        if (curyTypePar == null) {
            if (other.curyTypePar != null)
                return false;
        } else if (!curyTypePar.equals(other.curyTypePar))
            return false;
        if (debtRegDate == null) {
            if (other.debtRegDate != null)
                return false;
        } else if (!debtRegDate.equals(other.debtRegDate))
            return false;
        if (exerPayDate == null) {
            if (other.exerPayDate != null)
                return false;
        } else if (!exerPayDate.equals(other.exerPayDate))
            return false;
        if (extraSprSeqNum == null) {
            if (other.extraSprSeqNum != null)
                return false;
        } else if (!extraSprSeqNum.equals(other.extraSprSeqNum))
            return false;
        if (guraName == null) {
            if (other.guraName != null)
                return false;
        } else if (!guraName.equals(other.guraName))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (inteCalcuClsPar == null) {
            if (other.inteCalcuClsPar != null)
                return false;
        } else if (!inteCalcuClsPar.equals(other.inteCalcuClsPar))
            return false;
        if (intePayClsPar == null) {
            if (other.intePayClsPar != null)
                return false;
        } else if (!intePayClsPar.equals(other.intePayClsPar))
            return false;
        if (intePayFreq == null) {
            if (other.intePayFreq != null)
                return false;
        } else if (!intePayFreq.equals(other.intePayFreq))
            return false;
        if (intePayMeth == null) {
            if (other.intePayMeth != null)
                return false;
        } else if (!intePayMeth.equals(other.intePayMeth))
            return false;
        if (inteStartDate == null) {
            if (other.inteStartDate != null)
                return false;
        } else if (!inteStartDate.equals(other.inteStartDate))
            return false;
        if (isCrosMarPar == null) {
            if (other.isCrosMarPar != null)
                return false;
        } else if (!isCrosMarPar.equals(other.isCrosMarPar))
            return false;
        if (isGuarPar == null) {
            if (other.isGuarPar != null)
                return false;
        } else if (!isGuarPar.equals(other.isGuarPar))
            return false;
        if (isListPar == null) {
            if (other.isListPar != null)
                return false;
        } else if (!isListPar.equals(other.isListPar))
            return false;
        if (isRedemPar == null) {
            if (other.isRedemPar != null)
                return false;
        } else if (!isRedemPar.equals(other.isRedemPar))
            return false;
        if (isResaPar == null) {
            if (other.isResaPar != null)
                return false;
        } else if (!isResaPar.equals(other.isResaPar))
            return false;
        if (isSegmPar == null) {
            if (other.isSegmPar != null)
                return false;
        } else if (!isSegmPar.equals(other.isSegmPar))
            return false;
        if (isinCode == null) {
            if (other.isinCode != null)
                return false;
        } else if (!isinCode.equals(other.isinCode))
            return false;
        if (issCls == null) {
            if (other.issCls != null)
                return false;
        } else if (!issCls.equals(other.issCls))
            return false;
        if (issCoupRate == null) {
            if (other.issCoupRate != null)
                return false;
        } else if (!issCoupRate.equals(other.issCoupRate))
            return false;
        if (issDeclDate == null) {
            if (other.issDeclDate != null)
                return false;
        } else if (!issDeclDate.equals(other.issDeclDate))
            return false;
        if (issEndDate == null) {
            if (other.issEndDate != null)
                return false;
        } else if (!issEndDate.equals(other.issEndDate))
            return false;
        if (issName == null) {
            if (other.issName != null)
                return false;
        } else if (!issName.equals(other.issName))
            return false;
        if (issObj == null) {
            if (other.issObj != null)
                return false;
        } else if (!issObj.equals(other.issObj))
            return false;
        if (issPri == null) {
            if (other.issPri != null)
                return false;
        } else if (!issPri.equals(other.issPri))
            return false;
        if (issStaPar == null) {
            if (other.issStaPar != null)
                return false;
        } else if (!issStaPar.equals(other.issStaPar))
            return false;
        if (issStartDate == null) {
            if (other.issStartDate != null)
                return false;
        } else if (!issStartDate.equals(other.issStartDate))
            return false;
        if (isvalid == null) {
            if (other.isvalid != null)
                return false;
        } else if (!isvalid.equals(other.isvalid))
            return false;
        if (listDate == null) {
            if (other.listDate != null)
                return false;
        } else if (!listDate.equals(other.listDate))
            return false;
        if (listDeclDate == null) {
            if (other.listDeclDate != null)
                return false;
        } else if (!listDeclDate.equals(other.listDeclDate))
            return false;
        if (listSectPar == null) {
            if (other.listSectPar != null)
                return false;
        } else if (!listSectPar.equals(other.listSectPar))
            return false;
        if (listStaPar == null) {
            if (other.listStaPar != null)
                return false;
        } else if (!listStaPar.equals(other.listStaPar))
            return false;
        if (matuPayDate == null) {
            if (other.matuPayDate != null)
                return false;
        } else if (!matuPayDate.equals(other.matuPayDate))
            return false;
        if (matuUnitPar == null) {
            if (other.matuUnitPar != null)
                return false;
        } else if (!matuUnitPar.equals(other.matuUnitPar))
            return false;
        if (newCoupRate == null) {
            if (other.newCoupRate != null)
                return false;
        } else if (!newCoupRate.equals(other.newCoupRate))
            return false;
        if (newRate == null) {
            if (other.newRate != null)
                return false;
        } else if (!newRate.equals(other.newRate))
            return false;
        if (newSize == null) {
            if (other.newSize != null)
                return false;
        } else if (!newSize.equals(other.newSize))
            return false;
        if (optDes == null) {
            if (other.optDes != null)
                return false;
        } else if (!optDes.equals(other.optDes))
            return false;
        if (optExtraSpr == null) {
            if (other.optExtraSpr != null)
                return false;
        } else if (!optExtraSpr.equals(other.optExtraSpr))
            return false;
        if (orgUniCode == null) {
            if (other.orgUniCode != null)
                return false;
        } else if (!orgUniCode.equals(other.orgUniCode))
            return false;
        if (payClsDes == null) {
            if (other.payClsDes != null)
                return false;
        } else if (!payClsDes.equals(other.payClsDes))
            return false;
        if (payEndDate == null) {
            if (other.payEndDate != null)
                return false;
        } else if (!payEndDate.equals(other.payEndDate))
            return false;
        if (payMatu == null) {
            if (other.payMatu != null)
                return false;
        } else if (!payMatu.equals(other.payMatu))
            return false;
        if (payStartDate == null) {
            if (other.payStartDate != null)
                return false;
        } else if (!payStartDate.equals(other.payStartDate))
            return false;
        if (planIssAmut == null) {
            if (other.planIssAmut != null)
                return false;
        } else if (!planIssAmut.equals(other.planIssAmut))
            return false;
        if (rateDes == null) {
            if (other.rateDes != null)
                return false;
        } else if (!rateDes.equals(other.rateDes))
            return false;
        if (rateTypePar == null) {
            if (other.rateTypePar != null)
                return false;
        } else if (!rateTypePar.equals(other.rateTypePar))
            return false;
        if (repayClsPayPar == null) {
            if (other.repayClsPayPar != null)
                return false;
        } else if (!repayClsPayPar.equals(other.repayClsPayPar))
            return false;
        if (secMarPar == null) {
            if (other.secMarPar != null)
                return false;
        } else if (!secMarPar.equals(other.secMarPar))
            return false;
        if (simpCompIntePar == null) {
            if (other.simpCompIntePar != null)
                return false;
        } else if (!simpCompIntePar.equals(other.simpCompIntePar))
            return false;
        if (speShortName == null) {
            if (other.speShortName != null)
                return false;
        } else if (!speShortName.equals(other.speShortName))
            return false;
        if (theoDelistDate == null) {
            if (other.theoDelistDate != null)
                return false;
        } else if (!theoDelistDate.equals(other.theoDelistDate))
            return false;
        if (theoEndDate == null) {
            if (other.theoEndDate != null)
                return false;
        } else if (!theoEndDate.equals(other.theoEndDate))
            return false;
        if (undeClsPar == null) {
            if (other.undeClsPar != null)
                return false;
        } else if (!undeClsPar.equals(other.undeClsPar))
            return false;
        if (undeName == null) {
            if (other.undeName != null)
                return false;
        } else if (!undeName.equals(other.undeName))
            return false;
        if (updatetime == null) {
            if (other.updatetime != null)
                return false;
        } else if (!updatetime.equals(other.updatetime))
            return false;
        if (yearPayDate == null) {
            if (other.yearPayDate != null)
                return false;
        } else if (!yearPayDate.equals(other.yearPayDate))
            return false;
        if (yearPayMatu == null) {
            if (other.yearPayMatu != null)
                return false;
        } else if (!yearPayMatu.equals(other.yearPayMatu))
            return false;
        return true;
    }
}
