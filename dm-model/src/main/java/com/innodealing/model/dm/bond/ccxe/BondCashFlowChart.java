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

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

@Entity
@Table(name="d_bond_cash_flow_chart")
public class BondCashFlowChart implements Serializable{
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @Column(name="ID", length=36)
    private String id;
    
    @JsonIgnore
    @Column(name="ISVALID", length=10)
    private Integer isvalid;
    
    @JsonIgnore
    @Column(name="CREATETIME", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createtime;
    
    @JsonIgnore
    @Column(name="UPDATETIME", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatetime;
    
    @JsonIgnore
    @Column(nullable=false, name="CCXEID", length=19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ccxeid;
    
    @JsonIgnore
	@Id
    @Column(name="BOND_UNI_CODE", length=20)
    private Long bondUniCode;
    
	@JsonIgnore
    @Column(name="BOND_CODE", length=20)
    private String bondCode;
    
	@JsonIgnore
    @Column(name="BOND_SHORT_NAME", length=200)
    private String bondShortName;
    
    @JsonIgnore
    @Column(name="ORI_TYPE_PAR", length=11)
    private Integer oriTypePar;
    
    @JsonIgnore
    @Column(name="CASH_TYPE_PAR", length=11)
    private Integer cashTypePar;
    
    @Column(name="INTE_PERI", length=11)
    private Integer intePeri;	
    
    @Column(name="INTE_START_DATE", length=11)
    private Date inteStartDate;
    
    @Column(name="INTE_END_DATE", length=11)
    private Date inteEndDate;
    
    @JsonIgnore
    @Column(name="IS_NO_OPT_PERI", length=11)
    private Integer isNoOptPeri;
    
    @Column(name="INTE_PAY")
    private Double intePay;

    @JsonIgnore
    @Column(name="INTE_PAY_END")
    private Double intePayEnd;
    
    @Column(name="PAY_AMUT")
    private Double payAmut;
    
    @JsonIgnore
    @Column(name="EXER_INTE_PAY")
    private Double exerIntePay;
    
    @JsonIgnore
    @Column(name="EXER_INTE_PAY_END")
    private Double exerIntePayEnd;
    
    @JsonIgnore
    @Column(name="EXER_PAY_AMUT")
    private Double exerPayAmut;
    
    @JsonIgnore
    @Column(name="DECL_DATE")
    private Date declDate;
    
    @JsonIgnore
    @Column(name="REG_DATE")
    private Date regDate;
    
    @JsonIgnore
    @Column(name="EXDIV_DATE")
    private Date exdivDate;
    
    @JsonIgnore
    @Column(name="PAY_OBJ")
    private String payObj;
    
    @JsonIgnore
    @Column(name="TAX_RATE")
    private Double taxRate;
    
    @JsonIgnore
    @Column(name="PAY_DATE")
    private Date payDate;
    
    @JsonIgnore
    @Column(name="PAY_END_DATE")
    private Date payEndDate;
    
    @JsonIgnore
    @Column(name="OTHER_PAY_DATE")
    private Date otherPayDate;
    
    @JsonIgnore
    @Column(name="PAY_FEE_RATE")
    private Double payFeeRate;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the isvalid
     */
    public Integer getIsvalid() {
        return isvalid;
    }

    /**
     * @param isvalid the isvalid to set
     */
    public void setIsvalid(Integer isvalid) {
        this.isvalid = isvalid;
    }

    /**
     * @return the createtime
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime the createtime to set
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * @return the updatetime
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * @param updatetime the updatetime to set
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * @return the ccxeid
     */
    public Date getCcxeid() {
        return ccxeid;
    }

    /**
     * @param ccxeid the ccxeid to set
     */
    public void setCcxeid(Date ccxeid) {
        this.ccxeid = ccxeid;
    }

    /**
     * @return the bondUniCode
     */
    public Long getBondUniCode() {
        return bondUniCode;
    }

    /**
     * @param bondUniCode the bondUniCode to set
     */
    public void setBondUniCode(Long bondUniCode) {
        this.bondUniCode = bondUniCode;
    }

    /**
     * @return the bondCode
     */
    public String getBondCode() {
        return bondCode;
    }

    /**
     * @param bondCode the bondCode to set
     */
    public void setBondCode(String bondCode) {
        this.bondCode = bondCode;
    }

    /**
     * @return the bondShortName
     */
    public String getBondShortName() {
        return bondShortName;
    }

    /**
     * @param bondShortName the bondShortName to set
     */
    public void setBondShortName(String bondShortName) {
        this.bondShortName = bondShortName;
    }

    /**
     * @return the oriTypePar
     */
    public Integer getOriTypePar() {
        return oriTypePar;
    }

    /**
     * @param oriTypePar the oriTypePar to set
     */
    public void setOriTypePar(Integer oriTypePar) {
        this.oriTypePar = oriTypePar;
    }

    /**
     * @return the cashTypePar
     */
    public Integer getCashTypePar() {
        return cashTypePar;
    }

    /**
     * @param cashTypePar the cashTypePar to set
     */
    public void setCashTypePar(Integer cashTypePar) {
        this.cashTypePar = cashTypePar;
    }

    /**
     * @return the intePeri
     */
    public Integer getIntePeri() {
        return intePeri;
    }

    /**
     * @param intePeri the intePeri to set
     */
    public void setIntePeri(Integer intePeri) {
        this.intePeri = intePeri;
    }

    /**
     * @return the inteStartDate
     */
    public Date getInteStartDate() {
        return inteStartDate;
    }

    /**
     * @param inteStartDate the inteStartDate to set
     */
    public void setInteStartDate(Date inteStartDate) {
        this.inteStartDate = inteStartDate;
    }

    /**
     * @return the inteEndDate
     */
    public Date getInteEndDate() {
        return inteEndDate;
    }

    /**
     * @param inteEndDate the inteEndDate to set
     */
    public void setInteEndDate(Date inteEndDate) {
        this.inteEndDate = inteEndDate;
    }

    /**
     * @return the isNoOptPeri
     */
    public Integer getIsNoOptPeri() {
        return isNoOptPeri;
    }

    /**
     * @param isNoOptPeri the isNoOptPeri to set
     */
    public void setIsNoOptPeri(Integer isNoOptPeri) {
        this.isNoOptPeri = isNoOptPeri;
    }

    /**
     * @return the intePar
     */
    public Double getIntePay() {
        return intePay;
    }

    /**
     * @param intePay the intePar to set
     */
    public void setIntePay(Double intePay) {
        this.intePay = intePay;
    }

    /**
     * @return the intePayEnd
     */
    public Double getIntePayEnd() {
        return intePayEnd;
    }

    /**
     * @param intePayEnd the intePayEnd to set
     */
    public void setIntePayEnd(Double intePayEnd) {
        this.intePayEnd = intePayEnd;
    }

    /**
     * @return the payAmut
     */
    public Double getPayAmut() {
        return payAmut;
    }

    /**
     * @param payAmut the payAmut to set
     */
    public void setPayAmut(Double payAmut) {
        this.payAmut = payAmut;
    }

    /**
     * @return the exerIntePay
     */
    public Double getExerIntePay() {
        return exerIntePay;
    }

    /**
     * @param exerIntePay the exerIntePay to set
     */
    public void setExerIntePay(Double exerIntePay) {
        this.exerIntePay = exerIntePay;
    }

    /**
     * @return the exerIntePayEnd
     */
    public Double getExerIntePayEnd() {
        return exerIntePayEnd;
    }

    /**
     * @param exerIntePayEnd the exerIntePayEnd to set
     */
    public void setExerIntePayEnd(Double exerIntePayEnd) {
        this.exerIntePayEnd = exerIntePayEnd;
    }

    /**
     * @return the exerPayAmut
     */
    public Double getExerPayAmut() {
        return exerPayAmut;
    }

    /**
     * @param exerPayAmut the exerPayAmut to set
     */
    public void setExerPayAmut(Double exerPayAmut) {
        this.exerPayAmut = exerPayAmut;
    }

    /**
     * @return the declDate
     */
    public Date getDeclDate() {
        return declDate;
    }

    /**
     * @param declDate the declDate to set
     */
    public void setDeclDate(Date declDate) {
        this.declDate = declDate;
    }

    /**
     * @return the regDate
     */
    public Date getRegDate() {
        return regDate;
    }

    /**
     * @param regDate the regDate to set
     */
    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    /**
     * @return the exdivDate
     */
    public Date getExdivDate() {
        return exdivDate;
    }

    /**
     * @param exdivDate the exdivDate to set
     */
    public void setExdivDate(Date exdivDate) {
        this.exdivDate = exdivDate;
    }

    /**
     * @return the payObj
     */
    public String getPayObj() {
        return payObj;
    }

    /**
     * @param payObj the payObj to set
     */
    public void setPayObj(String payObj) {
        this.payObj = payObj;
    }

    /**
     * @return the taxRate
     */
    public Double getTaxRate() {
        return taxRate;
    }

    /**
     * @param taxRate the taxRate to set
     */
    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * @return the payDate
     */
    public Date getPayDate() {
        return payDate;
    }

    /**
     * @param payDate the payDate to set
     */
    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    /**
     * @return the payEndDate
     */
    public Date getPayEndDate() {
        return payEndDate;
    }

    /**
     * @param payEndDate the payEndDate to set
     */
    public void setPayEndDate(Date payEndDate) {
        this.payEndDate = payEndDate;
    }

    /**
     * @return the otherPayDate
     */
    public Date getOtherPayDate() {
        return otherPayDate;
    }

    /**
     * @param otherPayDate the otherPayDate to set
     */
    public void setOtherPayDate(Date otherPayDate) {
        this.otherPayDate = otherPayDate;
    }

    /**
     * @return the payFeeRate
     */
    public Double getPayFeeRate() {
        return payFeeRate;
    }

    /**
     * @param payFeeRate the payFeeRate to set
     */
    public void setPayFeeRate(Double payFeeRate) {
        this.payFeeRate = payFeeRate;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BondCashFlowChart [" + (id != null ? "id=" + id + ", " : "")
                + (isvalid != null ? "isvalid=" + isvalid + ", " : "")
                + (createtime != null ? "createtime=" + createtime + ", " : "")
                + (updatetime != null ? "updatetime=" + updatetime + ", " : "")
                + (ccxeid != null ? "ccxeid=" + ccxeid + ", " : "")
                + (bondUniCode != null ? "bondUniCode=" + bondUniCode + ", " : "")
                + (bondCode != null ? "bondCode=" + bondCode + ", " : "")
                + (bondShortName != null ? "bondShortName=" + bondShortName + ", " : "")
                + (oriTypePar != null ? "oriTypePar=" + oriTypePar + ", " : "")
                + (cashTypePar != null ? "cashTypePar=" + cashTypePar + ", " : "")
                + (intePeri != null ? "intePeri=" + intePeri + ", " : "")
                + (inteStartDate != null ? "inteStartDate=" + inteStartDate + ", " : "")
                + (inteEndDate != null ? "inteEndDate=" + inteEndDate + ", " : "")
                + (isNoOptPeri != null ? "isNoOptPeri=" + isNoOptPeri + ", " : "")
                + (intePay != null ? "intePar=" + intePay + ", " : "")
                + (intePayEnd != null ? "intePayEnd=" + intePayEnd + ", " : "")
                + (payAmut != null ? "payAmut=" + payAmut + ", " : "")
                + (exerIntePay != null ? "exerIntePay=" + exerIntePay + ", " : "")
                + (exerIntePayEnd != null ? "exerIntePayEnd=" + exerIntePayEnd + ", " : "")
                + (exerPayAmut != null ? "exerPayAmut=" + exerPayAmut + ", " : "")
                + (declDate != null ? "declDate=" + declDate + ", " : "")
                + (regDate != null ? "regDate=" + regDate + ", " : "")
                + (exdivDate != null ? "exdivDate=" + exdivDate + ", " : "")
                + (payObj != null ? "payObj=" + payObj + ", " : "")
                + (taxRate != null ? "taxRate=" + taxRate + ", " : "")
                + (payDate != null ? "payDate=" + payDate + ", " : "")
                + (payEndDate != null ? "payEndDate=" + payEndDate + ", " : "")
                + (otherPayDate != null ? "otherPayDate=" + otherPayDate + ", " : "")
                + (payFeeRate != null ? "payFeeRate=" + payFeeRate : "") + "]";
    }
    
}
