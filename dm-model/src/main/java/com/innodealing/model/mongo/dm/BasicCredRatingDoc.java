package com.innodealing.model.mongo.dm;

import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

public class BasicCredRatingDoc {
	
	@ApiModelProperty(value = "债券评级机构ID")
	@Indexed
    private Long orgUniCode;

	@ApiModelProperty(value = "债券评级机构名称")
	private String orgShortName;
	
	@ApiModelProperty(value = "债券评级评级日期")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date rateWritDate;
	
	@ApiModelProperty(value = "债券评级分数")
	private Integer credLevelPar;
	
	@ApiModelProperty(value = "评级")
	private String credLevel;
	
	@ApiModelProperty(value = "债券评级展望分数")
	private Integer rateProsPar;
	
	@ApiModelProperty(value = "评级展望")
	private String parName;

	@ApiModelProperty(value = "ccxeid")
	private String ccxeDate;
	
	@ApiModelProperty(value = "IS_NEW_RATE")
	private Integer isNewRate;
	
	/**
	 * @return the orgUniCode
	 */
	public Long getOrgUniCode() {
		return orgUniCode;
	}

	/**
	 * @param orgUniCode the orgUniCode to set
	 */
	public void setOrgUniCode(Long orgUniCode) {
		this.orgUniCode = orgUniCode;
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

	/**
	 * @return the credLevelPar
	 */
	public Integer getCredLevelPar() {
		return credLevelPar;
	}

	/**
	 * @param credLevelPar the credLevelPar to set
	 */
	public void setCredLevelPar(Integer credLevelPar) {
		this.credLevelPar = credLevelPar;
	}

	/**
	 * @return the credLevel
	 */
	public String getCredLevel() {
		return credLevel;
	}

	/**
	 * @param credLevel the credLevel to set
	 */
	public void setCredLevel(String credLevel) {
		this.credLevel = credLevel;
	}

	/**
	 * @return the rateProsPar
	 */
	public Integer getRateProsPar() {
		return rateProsPar;
	}

	/**
	 * @param rateProsPar the rateProsPar to set
	 */
	public void setRateProsPar(Integer rateProsPar) {
		this.rateProsPar = rateProsPar;
	}

	/**
	 * @return the parName
	 */
	public String getParName() {
		return parName;
	}

	/**
	 * @param parName the parName to set
	 */
	public void setParName(String parName) {
		this.parName = parName;
	}

	/**
	 * @return the ccxeDate
	 */
	public String getCcxeDate() {
		return ccxeDate;
	}

	/**
	 * @param ccxeDate the ccxeDate to set
	 */
	public void setCcxeDate(String ccxeDate) {
		this.ccxeDate = ccxeDate;
	}

	/**
	 * @return the isNewRate
	 */
	public Integer getIsNewRate() {
		return isNewRate;
	}

	/**
	 * @param isNewRate the isNewRate to set
	 */
	public void setIsNewRate(Integer isNewRate) {
		this.isNewRate = isNewRate;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BasicCredRatingDoc [" + (orgUniCode != null ? "orgUniCode=" + orgUniCode + ", " : "")
                + (orgShortName != null ? "orgShortName=" + orgShortName + ", " : "")
                + (rateWritDate != null ? "rateWritDate=" + rateWritDate + ", " : "")
                + (credLevelPar != null ? "credLevelPar=" + credLevelPar + ", " : "")
                + (credLevel != null ? "credLevel=" + credLevel + ", " : "")
                + (rateProsPar != null ? "rateProsPar=" + rateProsPar + ", " : "")
                + (parName != null ? "parName=" + parName + ", " : "")
                + (ccxeDate != null ? "ccxeDate=" + ccxeDate + ", " : "")
                + (isNewRate != null ? "isNewRate=" + isNewRate : "") + "]";
    }
}
