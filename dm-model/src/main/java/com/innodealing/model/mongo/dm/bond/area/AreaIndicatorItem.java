package com.innodealing.model.mongo.dm.bond.area;

import java.io.Serializable;
import java.util.List;


import io.swagger.annotations.ApiModelProperty;

public class AreaIndicatorItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5108226665377019266L;

	@ApiModelProperty("指标所属组名")
	private String group;
	
	@ApiModelProperty("当前分组的所有专项指标")
	private List<AreaIndicatorField> indicatorFields;

	
	public String getGroup() {
		return group;
	}

	public List<AreaIndicatorField> getIndicatorFields() {
		return indicatorFields;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void setIndicatorFields(List<AreaIndicatorField> indicatorFields) {
		this.indicatorFields = indicatorFields;
	}

	@Override
	public String toString() {
		return "AreaIndicatorItem [group=" + group + ", indicatorFields=" + indicatorFields + "]";
	}
	
	
}
