package com.innodealing.model;

import java.math.BigDecimal;

public class BondPriceAmp {

    private BigDecimal amplitude;
    
    private Long bondUniCode;
    
    private String bondShortName;
    
    private Long orgUniCode;
    
    private Long induUniCode;

	/**
	 * @return the amplitude
	 */
	public BigDecimal getAmplitude() {
		return amplitude;
	}

	/**
	 * @param amplitude the amplitude to set
	 */
	public void setAmplitude(BigDecimal amplitude) {
		this.amplitude = amplitude;
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
	 * @return the orgUniCode
	 */
	public Long getOrgUniCode() {
		return orgUniCode;
	}

	/**
	 * @param orgUniCode the orgUniCode to set
	 */
	public void setOrgUniCode(Long orgUniCode) {
		this.orgUniCode = orgUniCode;
	}

	/**
	 * @return the induUniCode
	 */
	public Long getInduUniCode() {
		return induUniCode;
	}

	/**
	 * @param induUniCode the induUniCode to set
	 */
	public void setInduUniCode(Long induUniCode) {
		this.induUniCode = induUniCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BondPriceAmp [" + (amplitude != null ? "amplitude=" + amplitude + ", " : "")
				+ (bondUniCode != null ? "bondUniCode=" + bondUniCode + ", " : "")
				+ (bondShortName != null ? "bondShortName=" + bondShortName + ", " : "")
				+ (orgUniCode != null ? "orgUniCode=" + orgUniCode + ", " : "")
				+ (induUniCode != null ? "induUniCode=" + induUniCode : "") + "]";
	}
    
}