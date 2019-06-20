package com.innodealing.model.mongo.dm.bond.finance;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;

/**
 * 财务专项指标分类过滤器
 * @author zhaozhenglai
 * @date 2017年2月13日下午3:13:05
 *Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
@Document(collection = "iss_indicator_special_filter")
public class SpecialIndicatorFilterDoc implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5949316004169324771L;

	@ApiModelProperty("行业类型")
	@Id
	private String type;
	
	@ApiModelProperty("当前类型的所有专项指标")
	private List<IndicatorFieldGroup> indicatorFieldGroups;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<IndicatorFieldGroup> getIndicatorFieldGroups() {
		return indicatorFieldGroups;
	}

	public void setIndicatorFieldGroups(List<IndicatorFieldGroup> indicatorFieldGroups) {
		this.indicatorFieldGroups = indicatorFieldGroups;
	}
	
}



