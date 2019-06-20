package com.innodealing.model.dm.bond;

import java.io.Serializable;

import javax.persistence.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class BondCity3 implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "值")
	private String value;
	
	@ApiModelProperty(value = "排名")
	private Integer top;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

	
	
	
	
}
