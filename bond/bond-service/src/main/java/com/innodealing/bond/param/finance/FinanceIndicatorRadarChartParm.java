package com.innodealing.bond.param.finance;

import java.io.Serializable;
import java.util.List;
/**
 * 财务指标分析-专项指标雷达图
 * @author zhaozhenglai
 * @date 2017年2月27日下午1:53:44
 *Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
public class FinanceIndicatorRadarChartParm implements Serializable{
	private Long issuerId; 
	private Long userId; 
	private Integer standardYear;
	private Integer standardQuarter; 
	private Integer compareYear; 
	private Integer compareQuarter; 
	private List<Long> provinceIds; 
	private List<String> fields;
	public Long getIssuerId() {
		return issuerId;
	}
	public void setIssuerId(Long issuerId) {
		this.issuerId = issuerId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getStandardYear() {
		return standardYear;
	}
	public void setStandardYear(Integer standardYear) {
		this.standardYear = standardYear;
	}
	public Integer getStandardQuarter() {
		return standardQuarter;
	}
	public void setStandardQuarter(Integer standardQuarter) {
		this.standardQuarter = standardQuarter;
	}
	public Integer getCompareYear() {
		return compareYear;
	}
	public void setCompareYear(Integer compareYear) {
		this.compareYear = compareYear;
	}
	public Integer getCompareQuarter() {
		return compareQuarter;
	}
	public void setCompareQuarter(Integer compareQuarter) {
		this.compareQuarter = compareQuarter;
	}
	public List<Long> getProvinceIds() {
		return provinceIds;
	}
	public void setProvinceIds(List<Long> provinceIds) {
		this.provinceIds = provinceIds;
	}
	public List<String> getFields() {
		return fields;
	}
	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	
}
