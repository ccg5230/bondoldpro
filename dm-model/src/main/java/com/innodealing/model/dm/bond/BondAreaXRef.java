package com.innodealing.model.dm.bond;

import java.math.BigDecimal;
import java.util.Date;

public class BondAreaXRef {
	
	Long areaUniCode;
	String areaChiShortName;
	String parName;
	Integer parCode;
	/**
	 * @return the areaUniCode
	 */
	public Long getAreaUniCode() {
		return areaUniCode;
	}
	/**
	 * @param areaUniCode the areaUniCode to set
	 */
	public void setAreaUniCode(Long areaUniCode) {
		this.areaUniCode = areaUniCode;
	}
	/**
	 * @return the areaChiShortName
	 */
	public String getAreaChiShortName() {
		return areaChiShortName;
	}
	/**
	 * @param areaChiShortName the areaChiShortName to set
	 */
	public void setAreaChiShortName(String areaChiShortName) {
		this.areaChiShortName = areaChiShortName;
	}
	/**
	 * @return the parName
	 */
	public String getParName() {
		return parName;
	}
	/**
	 * @param parName the parName to set
	 */
	public void setParName(String parName) {
		this.parName = parName;
	}
	/**
	 * @return the parCode
	 */
	public Integer getParCode() {
		return parCode;
	}
	/**
	 * @param parCode the parCode to set
	 */
	public void setParCode(Integer parCode) {
		this.parCode = parCode;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BondCcxePar [" + (areaUniCode != null ? "areaUniCode=" + areaUniCode + ", " : "")
				+ (areaChiShortName != null ? "areaChiShortName=" + areaChiShortName + ", " : "")
				+ (parName != null ? "parName=" + parName + ", " : "") + (parCode != null ? "parCode=" + parCode : "")
				+ "]";
	}

}
