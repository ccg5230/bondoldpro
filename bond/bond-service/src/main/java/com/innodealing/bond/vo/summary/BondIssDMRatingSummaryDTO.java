package com.innodealing.bond.vo.summary;

import java.math.BigDecimal;

/**
 * @author xiaochao
 * @time 2017年2月28日
 * @description:
 */
public class BondIssDMRatingSummaryDTO {
	private String ratingDate;
	private Long ratingPar;
	private Long parDiff;
	private String rating;
	private BigDecimal pd;
	private BigDecimal pdDiff;
	private String induName;
	private String comChiName;

	public Long getRatingPar() {
		return ratingPar;
	}

	public void setRatingPar(Long ratingPar) {
		this.ratingPar = ratingPar;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public BigDecimal getPd() {
		return pd;
	}

	public void setPd(BigDecimal pd) {
		this.pd = pd;
	}

	public Long getParDiff() {
		return parDiff;
	}

	public void setParDiff(Long parDiff) {
		this.parDiff = parDiff;
	}

	public BigDecimal getPdDiff() {
		return pdDiff;
	}

	public void setPdDiff(BigDecimal pdDiff) {
		this.pdDiff = pdDiff;
	}

	public String getRatingDate() {
		return ratingDate;
	}

	public void setRatingDate(String ratingDate) {
		this.ratingDate = ratingDate;
	}

	public String getInduName() {
		return induName;
	}

	public void setInduName(String induName) {
		this.induName = induName;
	}

	public String getComChiName() {
		return comChiName;
	}

	public void setComChiName(String comChiName) {
		this.comChiName = comChiName;
	}
}
