package com.innodealing.model.dm.bond;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaochao
 * @time 2017年2月27日
 * @description:
 */
public class BondAnalysisComRRS {

	@ApiModelProperty(value = "主体unicode-对应DM")
	private Long comUniCode;

	@ApiModelProperty(value = "主体编号-对应安硕")
	private Long compId;

	@ApiModelProperty(value = "评级")
	private String rating;

	@ApiModelProperty(value = "评级数值")
	private Long pdOrder;

	@ApiModelProperty(value = "模型编号")
	private Long modelId;

	@ApiModelProperty(value = "模型名称")
	private String modelName;

	@ApiModelProperty(value = "评分")
	private Double score;

	@ApiModelProperty(value = "年份")
	private Integer year;

	@ApiModelProperty(value = "主体名称")
	private String compName;

	@ApiModelProperty(value = "当前主体有效流通债券数量")
	private Long effeBondCount;

	/**
	 * @return the compId
	 */
	public Long getCompId() {
		return compId;
	}

	/**
	 * @param compId
	 *            the compId to set
	 */
	public void setCompId(Long compId) {
		this.compId = compId;
	}

	/**
	 * @return the modelId
	 */
	public Long getModelId() {
		return modelId;
	}

	/**
	 * @param modelId
	 *            the modelId to set
	 */
	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	/**
	 * @return the modelName
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * @param modelName
	 *            the modelName to set
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * @return the score
	 */
	public Double getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(Double score) {
		this.score = score;
	}

	/**
	 * @return the comUniCode
	 */
	public Long getComUniCode() {
		return comUniCode;
	}

	/**
	 * @param comUniCode
	 *            the comUniCode to set
	 */
	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

	/**
	 * @return the rating
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}

	/**
	 * @return the pdOrder
	 */
	public Long getPdOrder() {
		return pdOrder;
	}

	/**
	 * @param pdOrder
	 *            the pdOrder to set
	 */
	public void setPdOrder(Long pdOrder) {
		this.pdOrder = pdOrder;
	}

	/**
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}

	/**
	 * @param year
	 *            the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public Long getEffeBondCount() {
		return effeBondCount;
	}

	public void setEffeBondCount(Long effeBondCount) {
		this.effeBondCount = effeBondCount;
	}
}
