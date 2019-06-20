package com.innodealing.bond.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class BondSuggestion {
	
	@ApiModelProperty(value = "债券唯一编号")
	private Long bondUniCode;
	
	@ApiModelProperty(value = "债券编码")
	private String code;

	@ApiModelProperty(value = "债券简称")
	private String shortName;
	
	@ApiModelProperty(value = "期限")
	private String tenor;

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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * @return the tenor
	 */
	public String getTenor() {
		return tenor;
	}

	/**
	 * @param tenor the tenor to set
	 */
	public void setTenor(String tenor) {
		this.tenor = tenor;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BondSuggestion [" + (bondUniCode != null ? "bondUniCode=" + bondUniCode + ", " : "")
				+ (code != null ? "code=" + code + ", " : "")
				+ (shortName != null ? "shortName=" + shortName + ", " : "") + (tenor != null ? "tenor=" + tenor : "")
				+ "]";
	}
	
	

	
}
