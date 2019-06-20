package com.innodealing.bond.vo.area;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class IndicatorAreaIndicatorVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("主体名称")
	private String issuserName;
	
	@ApiModelProperty("财报时间")
	private List<String> quarters;

	@ApiModelProperty("指标值")
	private List<BigDecimal> values;
	
	@ApiModelProperty("指标code")
	private String field;
	
	@ApiModelProperty("指标名称")
	private String fieldName;
	
	@ApiModelProperty("是否为负向指标")
	private Integer negative;

	@ApiModelProperty("所属分类")
	private String categoryName;
	
	@ApiModelProperty("是否为率值，1是；0不是")
	private Integer percent;

	public String getIssuserName() {
		return issuserName;
	}

	public List<String> getQuarters() {
		return quarters;
	}

	public List<BigDecimal> getValues() {
		return values;
	}

	public String getField() {
		return field;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Integer getNegative() {
		return negative;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public Integer getPercent() {
		return percent;
	}

	public void setIssuserName(String issuserName) {
		this.issuserName = issuserName;
	}

	public void setQuarters(List<String> quarters) {
		this.quarters = quarters;
	}

	public void setValues(List<BigDecimal> values) {
		this.values = values;
	}

	public void setField(String field) {
		this.field = field;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setNegative(Integer negative) {
		this.negative = negative;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setPercent(Integer percent) {
		this.percent = percent;
	}
	
	

}
