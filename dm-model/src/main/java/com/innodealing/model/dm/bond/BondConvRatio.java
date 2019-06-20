package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "t_bond_conv_ratio")
public class BondConvRatio implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BondConvRatioKey convRatioKey;

	@Column(name = "bond_short_name")
	private String bondShortName;

	@Column(name = "bond_code")
	private String bondCode;
	   
	@Column(name = "conv_ratio")
	private BigDecimal convRatio;

    @Column(name = "start_date")
    private Date startDate;
    
    @Column(name = "end_date")
    private Date endDate;
	
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * @return the convRatioKey
     */
    public BondConvRatioKey getConvRatioKey() {
        return convRatioKey;
    }

    /**
     * @param convRatioKey the convRatioKey to set
     */
    public void setConvRatioKey(BondConvRatioKey convRatioKey) {
        this.convRatioKey = convRatioKey;
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
     * @return the convRatio
     */
    public BigDecimal getConvRatio() {
        return convRatio;
    }

    /**
     * @param convRatio the convRatio to set
     */
    public void setConvRatio(BigDecimal convRatio) {
        this.convRatio = convRatio;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return the bondCode
     */
    public String getBondCode() {
        return bondCode;
    }

    /**
     * @param bondCode the bondCode to set
     */
    public void setBondCode(String bondCode) {
        this.bondCode = bondCode;
    }

	@Override
	public String toString() {
		return "BondConvRatio [" + (convRatioKey != null ? "convRatioKey=" + convRatioKey + ", " : "")
				+ (bondShortName != null ? "bondShortName=" + bondShortName + ", " : "")
				+ (bondCode != null ? "bondCode=" + bondCode + ", " : "")
				+ (convRatio != null ? "convRatio=" + convRatio + ", " : "")
				+ (startDate != null ? "startDate=" + startDate + ", " : "")
				+ (endDate != null ? "endDate=" + endDate + ", " : "")
				+ (updateTime != null ? "updateTime=" + updateTime : "") + "]";
	}

  
}

