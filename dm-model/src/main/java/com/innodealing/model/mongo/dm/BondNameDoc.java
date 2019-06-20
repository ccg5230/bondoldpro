package com.innodealing.model.mongo.dm;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Document(collection="bond_name")
public class BondNameDoc {
	Integer bondUniCode;
	String bondShortName;
	/**
	 * @return the bondUniCode
	 */
	public Integer getBondUniCode() {
		return bondUniCode;
	}
	/**
	 * @param bondUniCode the bondUniCode to set
	 */
	public void setBondUniCode(Integer bondUniCode) {
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
