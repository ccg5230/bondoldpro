package com.innodealing.model.mongo.dm.bond.user;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.innodealing.model.mongo.dm.bond.area.AreaIndicatorFilterDoc;

import io.swagger.annotations.ApiModelProperty;

@Document(collection = "user_area_indicator_filter")
public class UserAreaIndicatorFilter implements Serializable{

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
	private AreaIndicatorFilterDoc filter;

	public Long getUserId() {
		return userId;
	}

	public String getModel() {
		return model;
	}

	public AreaIndicatorFilterDoc getFilter() {
		return filter;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setFilter(AreaIndicatorFilterDoc filter) {
		this.filter = filter;
	}

	@Override
	public String toString() {
		return "UserAreaIndicatorFilter [userId=" + userId + ", model=" + model + ", filter=" + filter + "]";
	}
	

}
