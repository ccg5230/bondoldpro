package com.innodealing.model;

import java.math.BigDecimal;
import java.util.Date;

public class BondCcxePar {
	
	Integer parCode;
	String parName;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BondCcxePar [" + (parCode != null ? "parCode=" + parCode + ", " : "")
				+ (parName != null ? "parName=" + parName : "") + "]";
	}

}
