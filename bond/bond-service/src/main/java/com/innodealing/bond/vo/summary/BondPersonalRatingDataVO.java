package com.innodealing.bond.vo.summary;

import javax.persistence.Transient;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaochao
 * @time 2017年3月14日
 * @description:
 */
public class BondPersonalRatingDataVO {

	@ApiModelProperty(value = "私人财报task编号")
	private Long taskId;

	@ApiModelProperty(value = "模型编号")
	private Long modleId;

	@ApiModelProperty(value = "发行人名称")
	private String compName;

	@Transient
	@ApiModelProperty(value = "行业编号")
	private Long induId;
	
	@ApiModelProperty(value = "行业名称")
	private String induName;

	@ApiModelProperty(value = "行业编号前缀(忽略了完整编号的最后两位)")
	private String induPre;

	@ApiModelProperty(value = "评分年份")
	private Long year;

	@ApiModelProperty(value = "评分月份")
	private Long month;

	@ApiModelProperty(value = "评分年月(yyyyMM)")
	private int yearMonth;

	@ApiModelProperty(value = "评级")
	private String rating;

	@ApiModelProperty(value = "评级数值")
	private int ratingValue;

	public Long getModleId() {
		return modleId;
	}

	public void setModleId(Long modleId) {
		this.modleId = modleId;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getInduPre() {
		return induPre;
	}

	public void setInduPre(String induPre) {
		this.induPre = induPre;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public Long getMonth() {
		return month;
	}

	public void setMonth(Long month) {
		this.month = month;
	}

	public int getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(int yearMonth) {
		this.yearMonth = yearMonth;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public int getRatingValue() {
		return ratingValue;
	}

	public void setRatingValue(int ratingValue) {
		this.ratingValue = ratingValue;
	}

	public String getInduName() {
		return induName;
	}

	public void setInduName(String induName) {
		this.induName = induName;
	}

	public Long getInduId() {
		return Long.valueOf(induPre + "00");
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
}
