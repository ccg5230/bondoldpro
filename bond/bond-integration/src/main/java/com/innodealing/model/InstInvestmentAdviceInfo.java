package com.innodealing.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author feng.ma
 * @date 2017年11月15日 下午8:04:21
 * @describe
 */
public class InstInvestmentAdviceInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "债券ID")
	private Long bondId;

	@ApiModelProperty(value = "投资建议ID")
	private Integer adviceId;

	@ApiModelProperty(value = "投资建议名称")
	private String adviceName;

	@ApiModelProperty(value = "是否有投资建议分析标示")
	private Integer textFlag;

	public Long getBondId() {
		return bondId;
	}

	public void setBondId(Long bondId) {
		this.bondId = bondId;
	}

	public Integer getAdviceId() {
		return adviceId;
	}

	public void setAdviceId(Integer adviceId) {
		this.adviceId = adviceId;
	}

	public String getAdviceName() {
		return adviceName;
	}

	public void setAdviceName(String adviceName) {
		this.adviceName = adviceName;
	}

	public Integer getTextFlag() {
		return textFlag;
	}

	public void setTextFlag(Integer textFlag) {
		this.textFlag = textFlag;
	}

}
