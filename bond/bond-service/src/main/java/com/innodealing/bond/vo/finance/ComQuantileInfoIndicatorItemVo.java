package com.innodealing.bond.vo.finance;

import io.swagger.annotations.ApiModelProperty;

/**
 * 指标分位值item
 * @author 赵正来
 *
 */
public class ComQuantileInfoIndicatorItemVo{
	
	@ApiModelProperty("指标名称")
	private String fieldName;
	
	@ApiModelProperty("指标code")
	private String field;
	
	@ApiModelProperty("指标分位值")
	private Integer quantile;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Integer getQuantile() {
		return quantile;
	}

	public void setQuantile(Integer quantile) {
		this.quantile = quantile;
	}

	public ComQuantileInfoIndicatorItemVo() {
		super();
	}

	public ComQuantileInfoIndicatorItemVo(String fieldName, String field, Integer quantile) {
		super();
		this.fieldName = fieldName;
		this.field = field;
		this.quantile = quantile;
	}

	@Override
	public String toString() {
		return "ComQuantileInfoIndicatorItemVo [fieldName=" + fieldName + ", field=" + field + ", quantile="
				+ quantile + "]";
	}
	
	
	
}
