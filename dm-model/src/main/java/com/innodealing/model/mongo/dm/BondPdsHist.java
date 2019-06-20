package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

/**
 * 违约概率历史
 *
 */
@JsonInclude(value=Include.NON_NULL)
public class BondPdsHist implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主体量化风险等级")
	private String pd;
	
	@ApiModelProperty(value = "主体量化风险值")
	private Long pdNum;
	
	@ApiModelProperty(value = "评级时间(季度)")
	private String quarter;
	
	@ApiModelProperty(value = "评级时间")
	@JsonFormat(pattern="yyyy/MM/dd")
	private Date date;
	
	@ApiModelProperty(value = "行业量化风险等级最小分位值")
	private Long indu1;
	
	@ApiModelProperty(value = "行业量化风险等级10分位值")
	private Long indu10;
	
	@ApiModelProperty(value = "行业量化风险等级50分位值")
	private Long indu50;
	
	@ApiModelProperty(value = "行业量化风险等级90分位值")
	private Long indu90;
	
	@ApiModelProperty(value = "行业量化风险等级最大分位值")
	private Long indu100;

	@ApiModelProperty(value = "行业名称")
	private String induName;
	
	public String getPd() {
		return pd;
	}

	public void setPd(String pd) {
		this.pd = pd;
	}

	public Long getPdNum() {
		return pdNum;
	}

	public void setPdNum(Long pdNum) {
		this.pdNum = pdNum;
	}

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getIndu1() {
		return indu1;
	}

	public void setIndu1(Long indu1) {
		this.indu1 = indu1;
	}

	public Long getIndu10() {
		return indu10;
	}

	public void setIndu10(Long indu10) {
		this.indu10 = indu10;
	}

	public Long getIndu50() {
		return indu50;
	}

	public void setIndu50(Long indu50) {
		this.indu50 = indu50;
	}

	public Long getIndu90() {
		return indu90;
	}

	public void setIndu90(Long indu90) {
		this.indu90 = indu90;
	}

	public Long getIndu100() {
		return indu100;
	}

	public void setIndu100(Long indu100) {
		this.indu100 = indu100;
	}

	public String getInduName() {
		return induName;
	}

	public void setInduName(String induName) {
		this.induName = induName;
	}
	
}
