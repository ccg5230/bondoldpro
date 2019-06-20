package com.innodealing.bond.vo.reportdata;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaochao
 * @time 2017年3月9日
 * @description: 主体量化风险等级变动情况
 */
public class BondIssDMRatingHistSubVO {

	@ApiModelProperty(value = "财报日期(季度)")
	private String quarter;

	@ApiModelProperty(value = "主体量化风险等级")
	private String pd;

	@ApiModelProperty(value = "较上期对比")
	private String pdDiff;

	@ApiModelProperty(value = "财务质量评级")
	private String finQuality;

	/**
	 * @return the pd
	 */
	public String getPd() {
		return pd;
	}

	/**
	 * @param pd
	 *            the pd to set
	 */
	public void setPd(String pd) {
		this.pd = pd;
	}

	/**
	 * @return the pdDiff
	 */
	public String getPdDiff() {
		return pdDiff;
	}

	/**
	 * @param pdDiff
	 *            the pdDiff to set
	 */
	public void setPdDiff(String pdDiff) {
		this.pdDiff = pdDiff;
	}

	/**
	 * @return the finQuality
	 */
	public String getFinQuality() {
		return finQuality;
	}

	/**
	 * @param finQuality
	 *            the finQuality to set
	 */
	public void setFinQuality(String finQuality) {
		this.finQuality = finQuality;
	}

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}
}
