package com.innodealing.model.mongo.dm.bond.area;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class AreaIndicatorField implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@ApiModelProperty("指标code")
	private String field;
	
	@ApiModelProperty("指标名称")
	private String fieldName;
	
	@ApiModelProperty("是否选中(1选中，0未选中)")
	private int selected = 0;

	public String getField() {
		return field;
	}

	public String getFieldName() {
		return fieldName;
	}

	public int getSelected() {
		return selected;
	}

	public void setField(String field) {
		this.field = field;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

	@Override
	public String toString() {
		return "AreaIndicatorField [field=" + field + ", fieldName=" + fieldName + ", selected=" + selected + "]";
	}
	
}
