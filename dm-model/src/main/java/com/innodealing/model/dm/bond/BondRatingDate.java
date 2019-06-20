package com.innodealing.model.dm.bond;

import io.swagger.annotations.ApiModelProperty;

public class BondRatingDate {

	@ApiModelProperty(value = "年")
	private Integer year;

	@ApiModelProperty(value = "月")
	private String month;
	
	@ApiModelProperty(value = "月")
	private String[] months;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String[] getMonths() {
		return months;
	}

	public void setMonths(String[] months) {
		this.months = months;
	}
	
}
