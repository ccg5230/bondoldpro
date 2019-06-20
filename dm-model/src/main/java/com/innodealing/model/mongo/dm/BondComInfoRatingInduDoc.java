package com.innodealing.model.mongo.dm;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
public class BondComInfoRatingInduDoc implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "评级")
	private String issCredLevel;

	@ApiModelProperty(value = "发行人数")
	private Integer count;

	public String getIssCredLevel() {
		return issCredLevel;
	}

	public void setIssCredLevel(String issCredLevel) {
		this.issCredLevel = issCredLevel;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public BondComInfoRatingInduDoc() {
		super();
	}

	public BondComInfoRatingInduDoc(String issCredLevel, Integer count) {
		super();
		this.issCredLevel = issCredLevel;
		this.count = count;
	}

	
}
