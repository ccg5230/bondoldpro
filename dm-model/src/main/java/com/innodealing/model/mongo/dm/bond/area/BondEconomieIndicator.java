package com.innodealing.model.mongo.dm.bond.area;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

public class BondEconomieIndicator {
	
	@ApiModelProperty(value="指标")
	private String field ;	
	
	@ApiModelProperty(value="指标名称")
	private String fieldName;
	
	@ApiModelProperty(value="指标的值")
	private BigDecimal value;

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

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "BondEconomieIndicator [field=" + field + ", fieldName=" + fieldName + ", value=" + value + "]";
	}

}
