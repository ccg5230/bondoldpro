package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Embeddable
public class BondStaticSpreadKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "bond_uni_code", nullable = false)
	private Long bondUniCode;

	@Column(name = "update_date", nullable = false)
	private Date updateDate;

	public Long getBondUniCode() {
		return bondUniCode;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


  
}
