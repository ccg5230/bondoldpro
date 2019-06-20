package com.innodealing.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author feng.ma
 * @date 2017年11月15日 下午8:09:29
 * @describe
 */
public class InstInnerRatingInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主体ID")
	private Long issuerId;

	@ApiModelProperty(value = "内部评级ID")
	private Integer ratingId;

	@ApiModelProperty(value = "内部评级名称")
	private String ratingName;

	@ApiModelProperty(value = "是否有投资建议分析标示")
	private Integer textFlag;

	public Long getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(Long issuerId) {
		this.issuerId = issuerId;
	}

	public Integer getRatingId() {
		return ratingId;
	}

	public void setRatingId(Integer ratingId) {
		this.ratingId = ratingId;
	}

	public String getRatingName() {
		return ratingName;
	}

	public void setRatingName(String ratingName) {
		this.ratingName = ratingName;
	}

	public Integer getTextFlag() {
		return textFlag;
	}

	public void setTextFlag(Integer textFlag) {
		this.textFlag = textFlag;
	}

}
