package com.innodealing.bond.param;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonInclude(Include.NON_NULL)
public class BondIssRating {
	
	@ApiModelProperty(value = "评级")
	private String issCredLevel;
	
	@ApiModelProperty(value = "评级数据")
	@JsonFormat(pattern="yyyy/MM/dd", timezone="Asia/Shanghai")
	private  Date rateWritDate;

	@ApiModelProperty(value = "评级机构")
	private String orgChiName;
	
	@ApiModelProperty(value = "是否存在观点")
	private Double hasRatePoint;
	
	@ApiModelProperty(value = "是否存在负面点")
	private Double hasDisadvt;
	
	@ApiModelProperty(value = "评级展望")
	private String rateProsPar;
	
	/**
	 * @return the bondCredLevel
	 */
	public String getIssCredLevel() {
		return issCredLevel;
	}

	/**
	 * @param bondCredLevel the bondCredLevel to set
	 */
	public void setIssCredLevel(String bondCredLevel) {
		this.issCredLevel = bondCredLevel;
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
		return "BondIssRating [" + (issCredLevel != null ? "issCredLevel=" + issCredLevel + ", " : "")
				+ (rateWritDate != null ? "rateWritDate=" + rateWritDate + ", " : "")
				+ (orgChiName != null ? "orgChiName=" + orgChiName + ", " : "")
				+ (hasRatePoint != null ? "hasRatePoint=" + hasRatePoint + ", " : "")
				+ (rateProsPar != null ? "rateProsPar=" + rateProsPar : "") + "]";
	}

	/**
	 * @return the orgChiName
	 */
	public String getOrgChiName() {
		return orgChiName;
	}

	/**
	 * @param orgChiName the orgChiName to set
	 */
	public void setOrgChiName(String orgChiName) {
		this.orgChiName = orgChiName;
	}

	/**
	 * @return the hasRatePoint
	 */
	public Double getHasRatePoint() {
		return hasRatePoint;
	}

	/**
	 * @param hasRatePoint the hasRatePoint to set
	 */
	public void setHasRatePoint(Double hasRatePoint) {
		this.hasRatePoint = hasRatePoint;
	}

	/**
	 * @return the hasDisadvt
	 */
	public Double getHasDisadvt() {
		return hasDisadvt;
	}

	/**
	 * @param hasDisadvt the hasDisadvt to set
	 */
	public void setHasDisadvt(Double hasDisadvt) {
		this.hasDisadvt = hasDisadvt;
	}

	public String getRateProsPar() {
		return rateProsPar;
	}

	public void setRateProsPar(String rateProsPar) {
		this.rateProsPar = rateProsPar;
	}

	
	
}
