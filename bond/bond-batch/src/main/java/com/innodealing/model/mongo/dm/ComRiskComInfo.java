package com.innodealing.model.mongo.dm;

import java.io.Serializable;

public class ComRiskComInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// @ApiModelProperty(value = "安硕内部等级")
	private Integer level;

	// @ApiModelProperty(value = "安硕内部评级 AAA AA+")
	private String rating;

	// @ApiModelProperty(value = "中诚信外部评级 AAA AAA- ")
	private String issCredLevel;

	// @ApiModelProperty(value = "发行人名称")
	private String comChiName;

	// @ApiModelProperty(value = "发行人编号")
	private Long comUniCode;

	// @ApiModelProperty(value = "评级年份")
	private Integer pdTime;

	private String type;

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getIssCredLevel() {
		return issCredLevel;
	}

	public void setIssCredLevel(String issCredLevel) {
		this.issCredLevel = issCredLevel;
	}

	public String getComChiName() {
		return comChiName;
	}

	public void setComChiName(String comChiName) {
		this.comChiName = comChiName;
	}

	public Long getComUniCode() {
		return comUniCode;
	}

	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

	public Integer getPdTime() {
		return pdTime;
	}

	public void setPdTime(Integer pdTime) {
		this.pdTime = pdTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}