package com.innodealing.model;

import java.math.BigDecimal;
import java.util.Date;

public class BondPd {
	Long com_uni_code;
	String pd; 
	Long pdNum;
	Date date;
	Integer year;
	Integer month;
	
	/**
	 * @return the com_uni_code
	 */
	public Long getCom_uni_code() {
		return com_uni_code;
	}
	/**
	 * @param com_uni_code the com_uni_code to set
	 */
	public void setCom_uni_code(Long com_uni_code) {
		this.com_uni_code = com_uni_code;
	}
	/**
	 * @return the pd
	 */
	public String getPd() {
		return pd;
	}
	/**
	 * @param pd the pd to set
	 */
	public void setPd(String pd) {
		this.pd = pd;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}
	/**
	 * @return the month
	 */
	public Integer getMonth() {
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(Integer month) {
		this.month = month;
	}
	/**
	 * @return the pdNum
	 */
	public Long getPdNum() {
		return pdNum;
	}
	/**
	 * @param pdNum the pdNum to set
	 */
	public void setPdNum(Long pdNum) {
		this.pdNum = pdNum;
	} 

}
