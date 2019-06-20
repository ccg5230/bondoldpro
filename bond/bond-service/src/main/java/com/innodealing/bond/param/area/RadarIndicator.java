package com.innodealing.bond.param.area;

import java.math.BigDecimal;

public class RadarIndicator {
	
	private BigDecimal valueAreaStandard; //基准数据值
	private BigDecimal valueAreaCompare;  //对比数据值
	private BigDecimal valueAreaContrast;  //均值
	private String fieldName;     //指标名称
	private String unit;		//指标单位
	
	public BigDecimal getValueAreaStandard() {
		return valueAreaStandard;
	}
	public void setValueAreaStandard(BigDecimal valueAreaStandard) {
		this.valueAreaStandard = valueAreaStandard;
	}
	public BigDecimal getValueAreaCompare() {
		return valueAreaCompare;
	}
	public void setValueAreaCompare(BigDecimal valueAreaCompare) {
		this.valueAreaCompare = valueAreaCompare;
	}
	public BigDecimal getValueAreaContrast() {
		return valueAreaContrast;
	}
	public void setValueAreaContrast(BigDecimal valueAreaContrast) {
		this.valueAreaContrast = valueAreaContrast;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	

}
