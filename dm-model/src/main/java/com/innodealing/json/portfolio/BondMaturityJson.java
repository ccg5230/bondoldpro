package com.innodealing.json.portfolio;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.innodealing.domain.bond.MaturityDto;

import io.swagger.annotations.ApiModelProperty;

public class BondMaturityJson extends MaturityDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(name="行权公告日期")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date declDate;
	
	@ApiModelProperty(name="行权申报起始日")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date applStartDate;
	
	@ApiModelProperty(name="行权申报截止日")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date applEndDate;

	public Date getDeclDate() {
		return declDate;
	}

	public void setDeclDate(Date declDate) {
		this.declDate = declDate;
	}

	public Date getApplStartDate() {
		return applStartDate;
	}

	public void setApplStartDate(Date applStartDate) {
		this.applStartDate = applStartDate;
	}

	public Date getApplEndDate() {
		return applEndDate;
	}

	public void setApplEndDate(Date applEndDate) {
		this.applEndDate = applEndDate;
	}

	@Override
	public String toString() {
		return "BondMaturityJson [declDate=" + declDate + ", applStartDate=" + applStartDate + ", applEndDate="
				+ applEndDate + ", getBondUniCode()=" + getBondUniCode() + ", getYearPayDate()=" + getYearPayDate()
				+ ", getExerPayDate()=" + getExerPayDate() + ", getTheoEndDate()=" + getTheoEndDate()
				+ ", getTheoDiffdays()=" + getTheoDiffdays() + "]";
	}
	
}
