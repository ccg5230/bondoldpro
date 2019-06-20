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
@Table(name="d_bond_basic_info_2")
public class BondBasicInfo2 implements Serializable{
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
    @Column(name="SPE_SHORT_NAME", length=100)
    private String speShortName;
    
    /**
     * 
     */
    @Column(name="ENG_FULL_NAME", length=200)
    private String engFullName;
    
    /**
     * 
     */
    @Column(name="ENG_SHORT_NAME", length=100)
    private String engShortName;
    
    /**
     * 
     */
    @Column(name="ISIN_CODE", length=12)
    private String isinCode;
    
    /**
     * 
     */
    @Column(name="ORG_UNI_CODE", length=19)
    private Long orgUniCode;
    
    /**
     * 
     */
    @Column(name="STOCK_CODE", length=30)
    private String stockCode;
    
    /**
     * 
     */
    @Column(name="IS_TRADE_PAR", length=10)
    private Integer isTradePar;
    
    /**
     * 
     */
    @Column(name="WARR_CODE", length=30)
    private String warrCode;
    
    /**
     * 
     */
    @Column(name="CURR_STATUS", length=10)
    private Integer currStatus;
    
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
    @Column(name="BOND_FORM_PAR", length=10)
    private Integer bondFormPar;
    
    /**
     * 
     */
    @Column(name="IS_TYPE_PAR", length=10)
    private Integer isTypePar;
    
    /**
     * 
     */
    @Column(name="INTE_START_DATE", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date inteStartDate;
    
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
    @Column(name="NEW_RATE", length=65535)
    private String newRate;
    
    /**
     * 
     */
    @Column(name="INTE_DES", length=65535)
    private String inteDes;
    
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
    @Column(name="ISS_COUP_RATE", length=22)
    private Double issCoupRate;
    
    /**
     * 
     */
    @Column(name="NEW_COUP_RATE", length=22)
    private Double newCoupRate;
    
    /**
     * 
     */
    @Column(name="INTE_PAY_CLS_PAR", length=10)
    private Integer intePayClsPar;
    
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
    @Column(name="IS_SEGM_PAR", length=10)
    private Integer isSegmPar;
    
    /**
     * 
     */
    @Column(name="SIMP_COMP_INTE_PAR", length=10)
    private Integer simpCompIntePar;
    
    /**
     * 
     */
    @Column(name="INTE_PAY_METH", length=65535)
    private String intePayMeth;
    
    /**
     * 
     */
    @Column(name="IS_INTE_COMP_PAR", length=10)
    private Integer isInteCompPar;
    
    /**
     * 
     */
    @Column(name="COMP_RATE", length=22)
    private Double compRate;
    
    /**
     * 
     */
    @Column(name="YTM_INTE_COMP", length=22)
    private Double ytmInteComp;
    
    /**
     * 
     */
    @Column(name="REPAY_CLS_PAY_PAR", length=10)
    private Integer repayClsPayPar;
    
    /**
     * 
     */
    @Column(name="MATU_PAY_DATE", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date matuPayDate;
    
    /**
     * 
     */
    @Column(name="EXER_PAY_DATE", length=65535)
    private String exerPayDate;
    
    /**
     * 
     */
    @Column(name="PAY_MATU", length=65535)
    private String payMatu;
    
    /**
     * 
     */
    @Column(name="PAY_CLS_DES", length=65535)
    private String payClsDes;
    
    /**
     * 
     */
    @Column(name="REDEM_PROT_DATE", length=22)
    private Double redemProtDate;
    
    /**
     * 
     */
    @Column(name="CONV_CODE", length=12)
    private String convCode;
    
    /**
     * 
     */
    @Column(name="CONV_SHORT_NAME", length=20)
    private String convShortName;
    
    /**
     * 
     */
    @Column(name="INIT_CONV_PRI", length=18)
    private BigDecimal initConvPri;
    
    /**
     * 
     */
    @Column(name="CONV_START_DATE", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date convStartDate;
    
    /**
     * 
     */
    @Column(name="CONV_END_DATE", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date convEndDate;
    
    /**
     * 
     */
    @Column(name="STOP_CONV_DATE", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date stopConvDate;
    
    /**
     * 
     */
    @Column(name="FORCE_CONV_DATE", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date forceConvDate;
    
    /**
     * 
     */
    @Column(name="FORCE_CONV_PRI", length=18)
    private BigDecimal forceConvPri;
    
    /**
     * 
     */
    @Column(name="OPT_DES", length=65535)
    private String optDes;
    
    /**
     * 
     */
    @Column(name="IS_GUAR_PAR", length=10)
    private Integer isGuarPar;
    
    /**
     * 
     */
    @Column(name="GURA_NAME", length=65535)
    private String guraName;
    
    /**
     * 
     */
    @Column(name="IS_REPU_PAR", length=10)
    private Integer isRepuPar;
    
    /**
     * 
     */
    @Column(name="PLEDGE_CODE", length=12)
    private String pledgeCode;
    
    /**
     * 
     */
    @Column(name="PLEDGE_NAME", length=30)
    private String pledgeName;
    
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
    
    public void setSpeShortName(String speShortName){
       this.speShortName = speShortName;
    }
    
    public String getSpeShortName(){
       return this.speShortName;
    }
    
    public void setEngFullName(String engFullName){
       this.engFullName = engFullName;
    }
    
    public String getEngFullName(){
       return this.engFullName;
    }
    
    public void setEngShortName(String engShortName){
       this.engShortName = engShortName;
    }
    
    public String getEngShortName(){
       return this.engShortName;
    }
    
    public void setIsinCode(String isinCode){
       this.isinCode = isinCode;
    }
    
    public String getIsinCode(){
       return this.isinCode;
    }
    
    public void setOrgUniCode(Long orgUniCode){
       this.orgUniCode = orgUniCode;
    }
    
    public Long getOrgUniCode(){
       return this.orgUniCode;
    }
    
    public void setStockCode(String stockCode){
       this.stockCode = stockCode;
    }
    
    public String getStockCode(){
       return this.stockCode;
    }
    
    public void setIsTradePar(Integer isTradePar){
       this.isTradePar = isTradePar;
    }
    
    public Integer getIsTradePar(){
       return this.isTradePar;
    }
    
    public void setWarrCode(String warrCode){
       this.warrCode = warrCode;
    }
    
    public String getWarrCode(){
       return this.warrCode;
    }
    
    public void setCurrStatus(Integer currStatus){
       this.currStatus = currStatus;
    }
    
    public Integer getCurrStatus(){
       return this.currStatus;
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
    
    public void setBondFormPar(Integer bondFormPar){
       this.bondFormPar = bondFormPar;
    }
    
    public Integer getBondFormPar(){
       return this.bondFormPar;
    }
    
    public void setIsTypePar(Integer isTypePar){
       this.isTypePar = isTypePar;
    }
    
    public Integer getIsTypePar(){
       return this.isTypePar;
    }
    
    public void setInteStartDate(Date inteStartDate){
       this.inteStartDate = inteStartDate;
    }
    
    public Date getInteStartDate(){
       return this.inteStartDate;
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
    
    public void setNewRate(String newRate){
       this.newRate = newRate;
    }
    
    public String getNewRate(){
       return this.newRate;
    }
    
    public void setInteDes(String inteDes){
       this.inteDes = inteDes;
    }
    
    public String getInteDes(){
       return this.inteDes;
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
    
    public void setIssCoupRate(Double issCoupRate){
       this.issCoupRate = issCoupRate;
    }
    
    public Double getIssCoupRate(){
       return this.issCoupRate;
    }
    
    public void setNewCoupRate(Double newCoupRate){
       this.newCoupRate = newCoupRate;
    }
    
    public Double getNewCoupRate(){
       return this.newCoupRate;
    }
    
    public void setIntePayClsPar(Integer intePayClsPar){
       this.intePayClsPar = intePayClsPar;
    }
    
    public Integer getIntePayClsPar(){
       return this.intePayClsPar;
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
    
    public void setIsSegmPar(Integer isSegmPar){
       this.isSegmPar = isSegmPar;
    }
    
    public Integer getIsSegmPar(){
       return this.isSegmPar;
    }
    
    public void setSimpCompIntePar(Integer simpCompIntePar){
       this.simpCompIntePar = simpCompIntePar;
    }
    
    public Integer getSimpCompIntePar(){
       return this.simpCompIntePar;
    }
    
    public void setIntePayMeth(String intePayMeth){
       this.intePayMeth = intePayMeth;
    }
    
    public String getIntePayMeth(){
       return this.intePayMeth;
    }
    
    public void setIsInteCompPar(Integer isInteCompPar){
       this.isInteCompPar = isInteCompPar;
    }
    
    public Integer getIsInteCompPar(){
       return this.isInteCompPar;
    }
    
    public void setCompRate(Double compRate){
       this.compRate = compRate;
    }
    
    public Double getCompRate(){
       return this.compRate;
    }
    
    public void setYtmInteComp(Double ytmInteComp){
       this.ytmInteComp = ytmInteComp;
    }
    
    public Double getYtmInteComp(){
       return this.ytmInteComp;
    }
    
    public void setRepayClsPayPar(Integer repayClsPayPar){
       this.repayClsPayPar = repayClsPayPar;
    }
    
    public Integer getRepayClsPayPar(){
       return this.repayClsPayPar;
    }
    
    public void setMatuPayDate(Date matuPayDate){
       this.matuPayDate = matuPayDate;
    }
    
    public Date getMatuPayDate(){
       return this.matuPayDate;
    }
    
    public void setExerPayDate(String exerPayDate){
       this.exerPayDate = exerPayDate;
    }
    
    public String getExerPayDate(){
       return this.exerPayDate;
    }
    
    public void setPayMatu(String payMatu){
       this.payMatu = payMatu;
    }
    
    public String getPayMatu(){
       return this.payMatu;
    }
    
    public void setPayClsDes(String payClsDes){
       this.payClsDes = payClsDes;
    }
    
    public String getPayClsDes(){
       return this.payClsDes;
    }
    
    public void setRedemProtDate(Double redemProtDate){
       this.redemProtDate = redemProtDate;
    }
    
    public Double getRedemProtDate(){
       return this.redemProtDate;
    }
    
    public void setConvCode(String convCode){
       this.convCode = convCode;
    }
    
    public String getConvCode(){
       return this.convCode;
    }
    
    public void setConvShortName(String convShortName){
       this.convShortName = convShortName;
    }
    
    public String getConvShortName(){
       return this.convShortName;
    }
    
    public void setInitConvPri(BigDecimal initConvPri){
       this.initConvPri = initConvPri;
    }
    
    public BigDecimal getInitConvPri(){
       return this.initConvPri;
    }
    
    public void setConvStartDate(Date convStartDate){
       this.convStartDate = convStartDate;
    }
    
    public Date getConvStartDate(){
       return this.convStartDate;
    }
    
    public void setConvEndDate(Date convEndDate){
       this.convEndDate = convEndDate;
    }
    
    public Date getConvEndDate(){
       return this.convEndDate;
    }
    
    public void setStopConvDate(Date stopConvDate){
       this.stopConvDate = stopConvDate;
    }
    
    public Date getStopConvDate(){
       return this.stopConvDate;
    }
    
    public void setForceConvDate(Date forceConvDate){
       this.forceConvDate = forceConvDate;
    }
    
    public Date getForceConvDate(){
       return this.forceConvDate;
    }
    
    public void setForceConvPri(BigDecimal forceConvPri){
       this.forceConvPri = forceConvPri;
    }
    
    public BigDecimal getForceConvPri(){
       return this.forceConvPri;
    }
    
    public void setOptDes(String optDes){
       this.optDes = optDes;
    }
    
    public String getOptDes(){
       return this.optDes;
    }
    
    public void setIsGuarPar(Integer isGuarPar){
       this.isGuarPar = isGuarPar;
    }
    
    public Integer getIsGuarPar(){
       return this.isGuarPar;
    }
    
    public void setGuraName(String guraName){
       this.guraName = guraName;
    }
    
    public String getGuraName(){
       return this.guraName;
    }
    
    public void setIsRepuPar(Integer isRepuPar){
       this.isRepuPar = isRepuPar;
    }
    
    public Integer getIsRepuPar(){
       return this.isRepuPar;
    }
    
    public void setPledgeCode(String pledgeCode){
       this.pledgeCode = pledgeCode;
    }
    
    public String getPledgeCode(){
       return this.pledgeCode;
    }
    
    public void setPledgeName(String pledgeName){
       this.pledgeName = pledgeName;
    }
    
    public String getPledgeName(){
       return this.pledgeName;
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
        result = prime * result + ((actuEndDate == null) ? 0 : actuEndDate.hashCode());
        result = prime * result + ((bondCode == null) ? 0 : bondCode.hashCode());
        result = prime * result + ((bondFormPar == null) ? 0 : bondFormPar.hashCode());
        result = prime * result + ((bondFullName == null) ? 0 : bondFullName.hashCode());
        result = prime * result + ((bondId == null) ? 0 : bondId.hashCode());
        result = prime * result + ((bondMatu == null) ? 0 : bondMatu.hashCode());
        result = prime * result + ((bondParVal == null) ? 0 : bondParVal.hashCode());
        result = prime * result + ((bondShortName == null) ? 0 : bondShortName.hashCode());
        result = prime * result + ((bondUniCode == null) ? 0 : bondUniCode.hashCode());
        result = prime * result + ((ccxeid == null) ? 0 : ccxeid.hashCode());
        result = prime * result + ((compRate == null) ? 0 : compRate.hashCode());
        result = prime * result + ((convCode == null) ? 0 : convCode.hashCode());
        result = prime * result + ((convEndDate == null) ? 0 : convEndDate.hashCode());
        result = prime * result + ((convShortName == null) ? 0 : convShortName.hashCode());
        result = prime * result + ((convStartDate == null) ? 0 : convStartDate.hashCode());
        result = prime * result + ((createtime == null) ? 0 : createtime.hashCode());
        result = prime * result + ((currStatus == null) ? 0 : currStatus.hashCode());
        result = prime * result + ((curyTypePar == null) ? 0 : curyTypePar.hashCode());
        result = prime * result + ((engFullName == null) ? 0 : engFullName.hashCode());
        result = prime * result + ((engShortName == null) ? 0 : engShortName.hashCode());
        result = prime * result + ((exerPayDate == null) ? 0 : exerPayDate.hashCode());
        result = prime * result + ((forceConvDate == null) ? 0 : forceConvDate.hashCode());
        result = prime * result + ((forceConvPri == null) ? 0 : forceConvPri.hashCode());
        result = prime * result + ((guraName == null) ? 0 : guraName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((initConvPri == null) ? 0 : initConvPri.hashCode());
        result = prime * result + ((inteCalcuClsPar == null) ? 0 : inteCalcuClsPar.hashCode());
        result = prime * result + ((inteDes == null) ? 0 : inteDes.hashCode());
        result = prime * result + ((intePayClsPar == null) ? 0 : intePayClsPar.hashCode());
        result = prime * result + ((intePayFreq == null) ? 0 : intePayFreq.hashCode());
        result = prime * result + ((intePayMeth == null) ? 0 : intePayMeth.hashCode());
        result = prime * result + ((inteStartDate == null) ? 0 : inteStartDate.hashCode());
        result = prime * result + ((isGuarPar == null) ? 0 : isGuarPar.hashCode());
        result = prime * result + ((isInteCompPar == null) ? 0 : isInteCompPar.hashCode());
        result = prime * result + ((isRedemPar == null) ? 0 : isRedemPar.hashCode());
        result = prime * result + ((isRepuPar == null) ? 0 : isRepuPar.hashCode());
        result = prime * result + ((isResaPar == null) ? 0 : isResaPar.hashCode());
        result = prime * result + ((isSegmPar == null) ? 0 : isSegmPar.hashCode());
        result = prime * result + ((isTradePar == null) ? 0 : isTradePar.hashCode());
        result = prime * result + ((isTypePar == null) ? 0 : isTypePar.hashCode());
        result = prime * result + ((isinCode == null) ? 0 : isinCode.hashCode());
        result = prime * result + ((issCoupRate == null) ? 0 : issCoupRate.hashCode());
        result = prime * result + ((isvalid == null) ? 0 : isvalid.hashCode());
        result = prime * result + ((matuPayDate == null) ? 0 : matuPayDate.hashCode());
        result = prime * result + ((matuUnitPar == null) ? 0 : matuUnitPar.hashCode());
        result = prime * result + ((newCoupRate == null) ? 0 : newCoupRate.hashCode());
        result = prime * result + ((newRate == null) ? 0 : newRate.hashCode());
        result = prime * result + ((optDes == null) ? 0 : optDes.hashCode());
        result = prime * result + ((orgUniCode == null) ? 0 : orgUniCode.hashCode());
        result = prime * result + ((payClsDes == null) ? 0 : payClsDes.hashCode());
        result = prime * result + ((payMatu == null) ? 0 : payMatu.hashCode());
        result = prime * result + ((pledgeCode == null) ? 0 : pledgeCode.hashCode());
        result = prime * result + ((pledgeName == null) ? 0 : pledgeName.hashCode());
        result = prime * result + ((rateDes == null) ? 0 : rateDes.hashCode());
        result = prime * result + ((rateTypePar == null) ? 0 : rateTypePar.hashCode());
        result = prime * result + ((redemProtDate == null) ? 0 : redemProtDate.hashCode());
        result = prime * result + ((repayClsPayPar == null) ? 0 : repayClsPayPar.hashCode());
        result = prime * result + ((simpCompIntePar == null) ? 0 : simpCompIntePar.hashCode());
        result = prime * result + ((speShortName == null) ? 0 : speShortName.hashCode());
        result = prime * result + ((stockCode == null) ? 0 : stockCode.hashCode());
        result = prime * result + ((stopConvDate == null) ? 0 : stopConvDate.hashCode());
        result = prime * result + ((theoEndDate == null) ? 0 : theoEndDate.hashCode());
        result = prime * result + ((updatetime == null) ? 0 : updatetime.hashCode());
        result = prime * result + ((warrCode == null) ? 0 : warrCode.hashCode());
        result = prime * result + ((yearPayDate == null) ? 0 : yearPayDate.hashCode());
        result = prime * result + ((yearPayMatu == null) ? 0 : yearPayMatu.hashCode());
        result = prime * result + ((ytmInteComp == null) ? 0 : ytmInteComp.hashCode());
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
        BondBasicInfo2 other = (BondBasicInfo2) obj;
        if (actuEndDate == null) {
            if (other.actuEndDate != null)
                return false;
        } else if (!actuEndDate.equals(other.actuEndDate))
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
        if (compRate == null) {
            if (other.compRate != null)
                return false;
        } else if (!compRate.equals(other.compRate))
            return false;
        if (convCode == null) {
            if (other.convCode != null)
                return false;
        } else if (!convCode.equals(other.convCode))
            return false;
        if (convEndDate == null) {
            if (other.convEndDate != null)
                return false;
        } else if (!convEndDate.equals(other.convEndDate))
            return false;
        if (convShortName == null) {
            if (other.convShortName != null)
                return false;
        } else if (!convShortName.equals(other.convShortName))
            return false;
        if (convStartDate == null) {
            if (other.convStartDate != null)
                return false;
        } else if (!convStartDate.equals(other.convStartDate))
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
        if (engFullName == null) {
            if (other.engFullName != null)
                return false;
        } else if (!engFullName.equals(other.engFullName))
            return false;
        if (engShortName == null) {
            if (other.engShortName != null)
                return false;
        } else if (!engShortName.equals(other.engShortName))
            return false;
        if (exerPayDate == null) {
            if (other.exerPayDate != null)
                return false;
        } else if (!exerPayDate.equals(other.exerPayDate))
            return false;
        if (forceConvDate == null) {
            if (other.forceConvDate != null)
                return false;
        } else if (!forceConvDate.equals(other.forceConvDate))
            return false;
        if (forceConvPri == null) {
            if (other.forceConvPri != null)
                return false;
        } else if (!forceConvPri.equals(other.forceConvPri))
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
        if (initConvPri == null) {
            if (other.initConvPri != null)
                return false;
        } else if (!initConvPri.equals(other.initConvPri))
            return false;
        if (inteCalcuClsPar == null) {
            if (other.inteCalcuClsPar != null)
                return false;
        } else if (!inteCalcuClsPar.equals(other.inteCalcuClsPar))
            return false;
        if (inteDes == null) {
            if (other.inteDes != null)
                return false;
        } else if (!inteDes.equals(other.inteDes))
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
        if (isGuarPar == null) {
            if (other.isGuarPar != null)
                return false;
        } else if (!isGuarPar.equals(other.isGuarPar))
            return false;
        if (isInteCompPar == null) {
            if (other.isInteCompPar != null)
                return false;
        } else if (!isInteCompPar.equals(other.isInteCompPar))
            return false;
        if (isRedemPar == null) {
            if (other.isRedemPar != null)
                return false;
        } else if (!isRedemPar.equals(other.isRedemPar))
            return false;
        if (isRepuPar == null) {
            if (other.isRepuPar != null)
                return false;
        } else if (!isRepuPar.equals(other.isRepuPar))
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
        if (isTradePar == null) {
            if (other.isTradePar != null)
                return false;
        } else if (!isTradePar.equals(other.isTradePar))
            return false;
        if (isTypePar == null) {
            if (other.isTypePar != null)
                return false;
        } else if (!isTypePar.equals(other.isTypePar))
            return false;
        if (isinCode == null) {
            if (other.isinCode != null)
                return false;
        } else if (!isinCode.equals(other.isinCode))
            return false;
        if (issCoupRate == null) {
            if (other.issCoupRate != null)
                return false;
        } else if (!issCoupRate.equals(other.issCoupRate))
            return false;
        if (isvalid == null) {
            if (other.isvalid != null)
                return false;
        } else if (!isvalid.equals(other.isvalid))
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
        if (optDes == null) {
            if (other.optDes != null)
                return false;
        } else if (!optDes.equals(other.optDes))
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
        if (payMatu == null) {
            if (other.payMatu != null)
                return false;
        } else if (!payMatu.equals(other.payMatu))
            return false;
        if (pledgeCode == null) {
            if (other.pledgeCode != null)
                return false;
        } else if (!pledgeCode.equals(other.pledgeCode))
            return false;
        if (pledgeName == null) {
            if (other.pledgeName != null)
                return false;
        } else if (!pledgeName.equals(other.pledgeName))
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
        if (redemProtDate == null) {
            if (other.redemProtDate != null)
                return false;
        } else if (!redemProtDate.equals(other.redemProtDate))
            return false;
        if (repayClsPayPar == null) {
            if (other.repayClsPayPar != null)
                return false;
        } else if (!repayClsPayPar.equals(other.repayClsPayPar))
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
        if (stockCode == null) {
            if (other.stockCode != null)
                return false;
        } else if (!stockCode.equals(other.stockCode))
            return false;
        if (stopConvDate == null) {
            if (other.stopConvDate != null)
                return false;
        } else if (!stopConvDate.equals(other.stopConvDate))
            return false;
        if (theoEndDate == null) {
            if (other.theoEndDate != null)
                return false;
        } else if (!theoEndDate.equals(other.theoEndDate))
            return false;
        if (updatetime == null) {
            if (other.updatetime != null)
                return false;
        } else if (!updatetime.equals(other.updatetime))
            return false;
        if (warrCode == null) {
            if (other.warrCode != null)
                return false;
        } else if (!warrCode.equals(other.warrCode))
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
        if (ytmInteComp == null) {
            if (other.ytmInteComp != null)
                return false;
        } else if (!ytmInteComp.equals(other.ytmInteComp))
            return false;
        return true;
    }
}
