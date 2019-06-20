package com.innodealing.bond.param;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class BondRating {
	
	@ApiModelProperty(value = "评级")
	private String bondCredLevel;
	
	@ApiModelProperty(value = "评级数据")
	@JsonFormat(pattern="yyyy/MM/dd")
	private  Date rateWritDate;

	@ApiModelProperty(value = "评级机构简称")
	private  String orgShortName;
	
	
	/**
	 * @return the issCredLevel
	 */
	public String getBondCredLevel() {
		return bondCredLevel;
	}

	/**
	 * @param issCredLevel the issCredLevel to set
	 */
	public void setBondCredLevel(String bondCredLevel) {
		this.bondCredLevel = bondCredLevel;
	}

	/**
	 * @return the rateWritDate
	 */
	public Date getRateWritDate() {
		return rateWritDate;
	}

	/**
	 * @param rateWritDate the rateWritDate to set
	 */
	public void setRateWritDate(Date rateWritDate) {
		this.rateWritDate = rateWritDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BondRating [" + (bondCredLevel != null ? "bondCredLevel=" + bondCredLevel + ", " : "")
				+ (rateWritDate != null ? "rateWritDate=" + rateWritDate + ", " : "")
				+ (orgShortName != null ? "orgShortName=" + orgShortName : "") + "]";
	}

	/**
	 * @return the orgShortName
	 */
	public String getOrgShortName() {
		return orgShortName;
	}

	/**
	 * @param orgShortName the orgShortName to set
	 */
	public void setOrgShortName(String orgShortName) {
		this.orgShortName = orgShortName;
	}

	
}
