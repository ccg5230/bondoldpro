package com.innodealing.model;

import java.io.Serializable;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;

public class BondDetailNewSizeCount implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private Map<String, Object> _id;

	@ApiModelProperty(value = "总债券规模")
	private Double newSizeCount;

	public Map<String, Object> get_id() {
		return _id;
	}

	public void set_id(Map<String, Object> _id) {
		this._id = _id;
	}

	public Double getNewSizeCount() {
		return newSizeCount;
	}

	public void setNewSizeCount(Double newSizeCount) {
		this.newSizeCount = newSizeCount;
	}
	
	

}
