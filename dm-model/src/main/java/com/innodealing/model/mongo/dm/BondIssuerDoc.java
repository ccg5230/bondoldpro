package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 债项评级历史
 *
 */
@ApiModel
@JsonInclude(Include.NON_NULL)
@Document(collection="bond_issuer")
public class BondIssuerDoc implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1565631874559543007L;

	@Id
	@ApiModelProperty(value = "发行人id")
	private Long comUniCode;
  
	@ApiModelProperty(value = "发行人")
	private String name;


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the comUniCode
	 */
	public Long getComUniCode() {
		return comUniCode;
	}

	/**
	 * @param comUniCode the comUniCode to set
	 */
	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BondIssuerDoc [" + (comUniCode != null ? "comUniCode=" + comUniCode + ", " : "")
				+ (name != null ? "name=" + name : "") + "]";
	}

}
