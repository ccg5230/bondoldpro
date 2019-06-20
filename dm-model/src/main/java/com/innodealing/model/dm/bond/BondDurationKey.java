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
public class BondDurationKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "bond_uni_code", nullable = false)
	private Long bondUniCode;

	@Column(name = "duration_date", nullable = false)
	private Date durationDate;

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
     * @return the durationDate
     */
    public Date getDurationDate() {
        return durationDate;
    }

    /**
     * @param durationDate the durationDate to set
     */
    public void setDurationDate(Date durationDate) {
        this.durationDate = durationDate;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BondDurationKey [" + (bondUniCode != null ? "bondUniCode=" + bondUniCode + ", " : "")
                + (durationDate != null ? "durationDate=" + durationDate : "") + "]";
    }

}
