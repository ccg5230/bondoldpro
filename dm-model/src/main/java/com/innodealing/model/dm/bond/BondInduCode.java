package com.innodealing.model.dm.bond;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="t_pub_indu_code")
public class BondInduCode implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="indu_uni_code")
    private Long induUniCode;
    
    @Column(name="indu_class_name")
    private String induName;

    @Column(name="is_valid")
    private Integer isValid;

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

	/**
	 * @return the induName
	 */
	public String getInduName() {
		return induName;
	}

	/**
	 * @param induName the induName to set
	 */
	public void setInduName(String induName) {
		this.induName = induName;
	}

	/**
	 * @return the isValid
	 */
	public Integer getIsValid() {
		return isValid;
	}

	/**
	 * @param isValid the isValid to set
	 */
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BondInduCode [" + (induUniCode != null ? "induUniCode=" + induUniCode + ", " : "")
				+ (induName != null ? "induName=" + induName + ", " : "")
				+ (isValid != null ? "isValid=" + isValid : "") + "]";
	}


    
    
}
