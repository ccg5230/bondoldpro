package com.innodealing.model.vo;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class CreditRatingParsedInfoVo {

	@ApiModelProperty(value = "可识别的数量")
	private Integer validCount;

	@ApiModelProperty(value = "可识别的数据")
	private List<CreditRatingParsed> validData;

	@ApiModelProperty(value = "无法识别的数量")
	private Integer invalidCount;

	@ApiModelProperty(value = "无法识别的数据")
	private List<CreditRatingParsed> invalidData;

	public Integer getValidCount() {
		return validCount;
	}

	public void setValidCount(Integer validCount) {
		this.validCount = validCount;
	}

	public List<CreditRatingParsed> getValidData() {
		return validData;
	}

	public void setValidData(List<CreditRatingParsed> validData) {
		this.validData = validData;
	}

	public Integer getInvalidCount() {
		return invalidCount;
	}

	public void setInvalidCount(Integer invalidCount) {
		this.invalidCount = invalidCount;
	}

	public List<CreditRatingParsed> getInvalidData() {
		return invalidData;
	}

	public void setInvalidData(List<CreditRatingParsed> invalidData) {
		this.invalidData = invalidData;
	}

}
