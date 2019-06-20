package com.innodealing.model.mongo.dm.bond.user;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.innodealing.model.mongo.dm.bond.finance.SpecialIndicatorFilterDoc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "用户发送联系我们")
@Document(collection = "user_indicator_filter")
public class UserIndicatorFilter implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	@Id
	private Long userId;
	
	@ApiModelProperty(value = "行业模型")
	@Indexed
	private String model;

	@ApiModelProperty(value = "用户专项指标筛选条件")
	private SpecialIndicatorFilterDoc filter;

	public Long getUserId() {
		return userId;
	}

	public SpecialIndicatorFilterDoc getFilter() {
		return filter;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setFilter(SpecialIndicatorFilterDoc filter) {
		this.filter = filter;
	}
	
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

}
