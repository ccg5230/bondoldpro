package com.innodealing.model.mongo.dm.bond.finance;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * 指标分组
 * @author zhaozhenglai
 * @date 2017年2月13日下午3:23:31
 *Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
public class IndicatorFieldGroup implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5108226665377019266L;

	@ApiModelProperty("指标所属组名")
	private String group;
	
	@ApiModelProperty("指标所属组名父级")
	private String groupParent;
	
	@ApiModelProperty("当前分组的所有专项指标")
	private List<IndicatorField> indicatorFields;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getGroupParent() {
		return groupParent;
	}

	public void setGroupParent(String groupParent) {
		this.groupParent = groupParent;
	}

	public List<IndicatorField> getIndicatorFields() {
		return indicatorFields;
	}

	public void setIndicatorFields(List<IndicatorField> indicatorFields) {
		this.indicatorFields = indicatorFields;
	}
	
	
}