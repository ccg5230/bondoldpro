package com.innodealing.bond.vo.reportdata;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaochao
 * @time 2017年3月9日
 * @description: 外部主体评级历史变动情况
 */
public class BondIssExtRatingHistSubVO {

	@ApiModelProperty(value = "评级日期")
	private String rateWritDate;

	@ApiModelProperty(value = "评级")
	private String issCredLevel;

	@ApiModelProperty(value = "评级机构")
	private String orgChiName;

	/**
	 * @return the bondCredLevel
	 */
	public String getIssCredLevel() {
		return issCredLevel;
	}

	/**
	 * @param bondCredLevel
	 *            the bondCredLevel to set
	 */
	public void setIssCredLevel(String bondCredLevel) {
		this.issCredLevel = bondCredLevel;
	}

	/**
	 * @return the rateWritDate
	 */
	public String getRateWritDate() {
		return rateWritDate;
	}

	/**
	 * @param rateWritDate
	 *            the rateWritDate to set
	 */
	public void setRateWritDate(String rateWritDate) {
		this.rateWritDate = rateWritDate;
	}

	/**
	 * @return the orgChiName
	 */
	public String getOrgChiName() {
		return orgChiName;
	}

	/**
	 * @param orgChiName
	 *            the orgChiName to set
	 */
	public void setOrgChiName(String orgChiName) {
		this.orgChiName = orgChiName;
	}
}
