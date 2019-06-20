package com.innodealing.model.mongo.dm;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class BondIdNameDoc {
	
	public BondIdNameDoc(Long bondUniCode, String bondShortName) {
		super();
		this.bondUniCode = bondUniCode;
		this.bondShortName = bondShortName;
	}
	Long bondUniCode;
	String bondShortName;
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
	
}
