package com.innodealing.bond.vo.finance;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

/**
 * 专项指标主体排序VO
 * @author zhaozhenglai
 * @date 2017年2月15日下午6:09:46
 *Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
@JsonInclude(value = Include.NON_NULL)
public class IssuerSortVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiParam(value = "主体排名编号")
	private Integer idx;

	@ApiParam(value = "主体id", hidden = true)
	private Long issuerId;
	
	@ApiParam("主体名称")
	private String issuerName;
	
	@ApiParam("主体量化风险总值, 分值来自rating_ratio_score")
	private Double pdSortRRs;
	
	@ApiParam("是否是当前主体自己(1是、0不是)")
	private int oneself;
	
	@ApiModelProperty(value = "流通中债劵数")
	private Long effectiveBondCount;

	public String getIssuerName() {
		return issuerName;
	}
	
	public Long getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(Long issuerId) {
		this.issuerId = issuerId;
	}
	
	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public int getOneself() {
		return oneself;
	}

	public void setOneself(int oneself) {
		this.oneself = oneself;
	}

	public Long getEffectiveBondCount() {
		return effectiveBondCount;
	}

	public void setEffectiveBondCount(Long effectiveBondCount) {
		this.effectiveBondCount = effectiveBondCount;
	}

	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}

	public Double getPdSortRRs() {
		return pdSortRRs;
	}

	public void setPdSortRRs(Double pdSortRRs) {
		this.pdSortRRs = pdSortRRs;
	}	
}