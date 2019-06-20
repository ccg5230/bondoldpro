package com.innodealing.model.mongo.dm.bond.finance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class FinanceIndicator implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "字段")
	private String field;

	@ApiModelProperty(value = "字段名")
	private String fieldName;
	
	@ApiModelProperty(value = "类型1资产负债、2利润、3现金流")
	private Integer type;

	@ApiModelProperty(value = "指标分类")
	private String category;
	
	@ApiModelProperty(value = "指标分类父类")
	private String categoryParent;
	
	@ApiModelProperty(value = "各季度指标")
	private List<BigDecimal> indicators;
	
	@ApiModelProperty(value = "是否为负向指标【1是，0不是】")
    private int negative;
	
	@ApiModelProperty("指标是否为百分比的小数，1是、0否")
    private int percent;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}


	public List<BigDecimal> getIndicators() {
		return indicators;
	}

	public void setIndicators(List<BigDecimal> indicators) {
		this.indicators = indicators;
	}

	public String getCategoryParent() {
		return categoryParent;
	}

	public void setCategoryParent(String categoryParent) {
		this.categoryParent = categoryParent;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public int getNegative() {
		return negative;
	}

	public void setNegative(int negative) {
		this.negative = negative;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}
	
	
}
