package com.innodealing.bond.vo.area;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class AreaIndicatorVo {
	
	@ApiModelProperty("区域经济代码")
	private Long areaUniCode ;
	
	@ApiModelProperty("地区名称")
	private String areaChiName;

	@ApiModelProperty("类型:基本 ,经济 ,财政 ,负债")
	private String type;

	@ApiModelProperty("指标")
	private String field ;
	
	@ApiModelProperty("指标中文名称")
	private String fieldName;
	
	@ApiModelProperty("该指标各个时间区间")
	private List<String> quarters = new ArrayList<>();
	
	@ApiModelProperty("对应该指标各个时间区间的值")
	private List<BigDecimal> values = new ArrayList<>();
	
	@ApiModelProperty("如果有子指标则封装到集合中，如果没有则subAreaIndicator为空")
	private List<AreaIndicatorVo> subAreaIndicator = new ArrayList<>(); 
	
	@ApiModelProperty("单位")
	private String unit;

	public Long getAreaUniCode() {
		return areaUniCode;
	}

	public void setAreaUniCode(Long areaUniCode) {
		this.areaUniCode = areaUniCode;
	}

	public String getAreaChiName() {
		return areaChiName;
	}

	public void setAreaChiName(String areaChiName) {
		this.areaChiName = areaChiName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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

	public List<String> getQuarters() {
		return quarters;
	}

	public void setQuarters(List<String> quarters) {
		this.quarters = quarters;
	}

	public List<BigDecimal> getValues() {
		return values;
	}

	public void setValues(List<BigDecimal> values) {
		this.values = values;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public List<AreaIndicatorVo> getSubAreaIndicator() {
		return subAreaIndicator;
	}

	public void setSubAreaIndicator(List<AreaIndicatorVo> subAreaIndicator) {
		this.subAreaIndicator = subAreaIndicator;
	}
	
	
}
