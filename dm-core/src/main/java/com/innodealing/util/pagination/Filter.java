package com.innodealing.util.pagination;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 查询过滤器
 * @author zhaozhenglai
 * @since 2016年7月1日上午10:02:45 
 * Copyright © 2015 DealingMatrix.cn. All Rights Reserved.
 */
@ApiModel(description="查询过滤器")
public class Filter implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="过滤的列名")
	private String name;
	
	@ApiModelProperty(value="过滤的列名value")
	private String value;
	
	@ApiModelProperty(value="连接条件(AND | OR),默认AND")
	private String joinType = "AND";
	
	@ApiModelProperty(value="过滤选项(等于(=)、大于(>)、小于(<)、LIKE、BETWEEN等)")
	private String option;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getJoinType() {
		return joinType;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

	/**
	 * @param name
	 * @param value
	 * @param joinType
	 * @param option
	 */
	public Filter(String name, String value, String joinType, String option) {
		this.name = name;
		this.value = value;
		this.joinType = joinType;
		this.option = option;
	}

	public Filter() {
	}
	
}
