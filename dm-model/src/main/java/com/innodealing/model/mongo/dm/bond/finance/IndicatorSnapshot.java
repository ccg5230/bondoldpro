package com.innodealing.model.mongo.dm.bond.finance;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

public class IndicatorSnapshot{
	
	@ApiModelProperty(name="指标code")
	private String field;
	
	@ApiModelProperty(name="指标自身")
	private BigDecimal value;
	
	@ApiModelProperty(name="指标在行业的分位数")
	private Integer quartile;
	

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Integer getQuartile() {
		return quartile;
	}

	public void setQuartile(Integer quartile) {
		this.quartile = quartile;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "IndicatorSnapshot [field=" + field + ", value=" + value + ", quartile=" + quartile + "]";
	}
	
	
}
