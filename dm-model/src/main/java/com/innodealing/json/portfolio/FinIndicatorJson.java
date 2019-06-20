package com.innodealing.json.portfolio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

//财务指标默认条件
public class FinIndicatorJson implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(name="发行人ID")
    private Long comUniCode;
	
	@ApiModelProperty(name="安硕中公司的ID")
	private Long compId;
	
	@ApiModelProperty(name="流动比率")
	private BigDecimal currRatio;
	
	@ApiModelProperty(name="速动比率")
	private BigDecimal quckRatio;
	
	@ApiModelProperty(name="存货周转天数")
	private BigDecimal invntryDay;
	
	@ApiModelProperty(name="应收账款周转天数")
	private BigDecimal arDay;
	
	@ApiModelProperty(name="营业周期")
	private BigDecimal bizCycleDay;
	
	@ApiModelProperty(name="总资产周转率")
	private BigDecimal totAsstRtrn;
	
	@ApiModelProperty(name="资产负债率")
	private BigDecimal liab2Asst;
	
	@ApiModelProperty(name="销售毛利率")
	private BigDecimal grssMrgn;
	
    @ApiModelProperty(value="EBIT利息保障倍数(X)")
    private BigDecimal intrstCovEBIT;
	
	@ApiModelProperty(name="报表日期")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date finDate;

	public Long getComUniCode() {
		return comUniCode;
	}

	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

	public Long getCompId() {
		return compId;
	}

	public void setCompId(Long compId) {
		this.compId = compId;
	}

	public BigDecimal getCurrRatio() {
		return currRatio;
	}

	public void setCurrRatio(BigDecimal currRatio) {
		this.currRatio = currRatio;
	}

	public BigDecimal getQuckRatio() {
		return quckRatio;
	}

	public void setQuckRatio(BigDecimal quckRatio) {
		this.quckRatio = quckRatio;
	}

	public BigDecimal getInvntryDay() {
		return invntryDay;
	}

	public void setInvntryDay(BigDecimal invntryDay) {
		this.invntryDay = invntryDay;
	}

	public BigDecimal getArDay() {
		return arDay;
	}

	public void setArDay(BigDecimal arDay) {
		this.arDay = arDay;
	}

	public BigDecimal getBizCycleDay() {
		return bizCycleDay;
	}

	public void setBizCycleDay(BigDecimal bizCycleDay) {
		this.bizCycleDay = bizCycleDay;
	}

	public BigDecimal getTotAsstRtrn() {
		return totAsstRtrn;
	}

	public void setTotAsstRtrn(BigDecimal totAsstRtrn) {
		this.totAsstRtrn = totAsstRtrn;
	}

	public BigDecimal getLiab2Asst() {
		return liab2Asst;
	}

	public void setLiab2Asst(BigDecimal liab2Asst) {
		this.liab2Asst = liab2Asst;
	}

	public BigDecimal getGrssMrgn() {
		return grssMrgn;
	}

	public void setGrssMrgn(BigDecimal grssMrgn) {
		this.grssMrgn = grssMrgn;
	}

	public BigDecimal getIntrstCovEBIT() {
		return intrstCovEBIT;
	}

	public void setIntrstCovEBIT(BigDecimal intrstCovEBIT) {
		this.intrstCovEBIT = intrstCovEBIT;
	}

	public Date getFinDate() {
		return finDate;
	}

	public void setFinDate(Date finDate) {
		this.finDate = finDate;
	}
	
	public FinIndicatorJson() {
	}
	
	@Override
	public String toString() {
		return "FinIndicatorJson [comUniCode=" + comUniCode + ", compId=" + compId + ", currRatio=" + currRatio
				+ ", quckRatio=" + quckRatio + ", invntryDay=" + invntryDay + ", arDay=" + arDay + ", bizCycleDay="
				+ bizCycleDay + ", totAsstRtrn=" + totAsstRtrn + ", liab2Asst=" + liab2Asst + ", grssMrgn=" + grssMrgn
				+ ", intrstCovEBIT=" + intrstCovEBIT + ", finDate=" + finDate + "]";
	}
	
}
