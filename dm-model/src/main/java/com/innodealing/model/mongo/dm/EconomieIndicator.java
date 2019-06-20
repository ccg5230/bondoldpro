package com.innodealing.model.mongo.dm;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

public class EconomieIndicator {
	
	@ApiModelProperty(value="指标")
	private String field ;	
	
	@ApiModelProperty(value="指标名称")
	private String fieldName;
	
	@ApiModelProperty(value="指标的值")
	private BigDecimal value;
	
	@ApiModelProperty("单位")
	private String unit;
	
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
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}	
	
	
	
}
