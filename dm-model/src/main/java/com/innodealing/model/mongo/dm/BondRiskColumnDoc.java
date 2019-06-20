package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


import io.swagger.annotations.ApiModelProperty;

public class BondRiskColumnDoc implements Serializable {

	@ApiModelProperty(value = "总数")
	Integer count;

	@ApiModelProperty(value = "发行人ID集合")
	Set<Long> comUniCodeList = new HashSet<Long>();
	
	@ApiModelProperty(value = "查询条件")
	String condition;
	
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Set<Long> getComUniCodeList() {
		return comUniCodeList;
	}

	public void setComUniCodeList(Set<Long> comUniCodeList) {
		this.comUniCodeList = comUniCodeList;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	

	

}
