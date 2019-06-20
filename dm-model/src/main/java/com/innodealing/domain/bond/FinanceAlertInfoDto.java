package com.innodealing.domain.bond;

import java.math.BigDecimal;
import java.util.Date;

public class FinanceAlertInfoDto {

	private Long comUniCode;
	private Long compId;
	private BigDecimal currRatio;
	private BigDecimal quckRatio;
	private BigDecimal invntryDay;
	private BigDecimal arDay;
	private BigDecimal bizCycleDay;
	private BigDecimal totAsstRtrn;
	private BigDecimal liab2Asst;
	private BigDecimal grssMrgn;
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

	public Date getFinDate() {
		return finDate;
	}

	public void setFinDate(Date finDate) {
		this.finDate = finDate;
	}

}
