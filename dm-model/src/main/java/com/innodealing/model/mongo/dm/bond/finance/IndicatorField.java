package com.innodealing.model.mongo.dm.bond.finance;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
/**
 * 指标
 * @author zhaozhenglai
 * @date 2017年2月13日下午3:23:07
 *Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
public class IndicatorField implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8568073107852812598L;

	@ApiModelProperty("指标code")
	private String field;
	
	@ApiModelProperty("指标名称")
	private String fieldName;
	
	@ApiModelProperty("是否选中(1选中，0未选中)")
	private int selected = 0;
	
	@ApiModelProperty("指标表达式说明")
	private String expressDescription;

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

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

    public String getExpressDescription() {
        return expressDescription;
    }

    public void setExpressDescription(String expressDescription) {
        this.expressDescription = expressDescription;
    }


	
}
