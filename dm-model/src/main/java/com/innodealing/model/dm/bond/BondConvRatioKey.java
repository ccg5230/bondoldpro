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
public class BondConvRatioKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "bond_uni_code", nullable = false)
	private Long bondUniCode;

	@Column(name = "create_date", nullable = false)
	private Date createDate;

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
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	@Override
	public String toString() {
		return "BondConvRatioKey [" + (bondUniCode != null ? "bondUniCode=" + bondUniCode + ", " : "")
				+ (createDate != null ? "createDate=" + createDate : "") + "]";
	}


}
