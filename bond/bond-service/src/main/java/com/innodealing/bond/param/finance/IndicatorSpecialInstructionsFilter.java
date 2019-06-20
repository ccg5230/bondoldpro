package com.innodealing.bond.param.finance;

import java.io.Serializable;
import java.util.List;

import com.innodealing.annotation.QueryField;
import com.innodealing.consts.Constants;

import io.swagger.annotations.ApiModelProperty;

/**
 * 财务专项指标过滤条件
 * 
 * @author zhaozhenglai
 * @date 2017年2月10日下午3:53:49 Copyright © 2016 DealingMatrix.cn. All Rights
 *       Reserved.
 */
public class IndicatorSpecialInstructionsFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 944070056409378579L;

	@ApiModelProperty("发行人id")
	@QueryField(columnName = "issId", option = "=")
	private Long issId;

	@ApiModelProperty("指标code")
	@QueryField(columnName = "field", option = "in")
	private List<String> fields;

	@ApiModelProperty("财报时间{1:一季度,2:二季度(中报),3:三季度,4:四季度(年报) }")
	private Integer[] financialReportType = { 1, 2, 3, 4 };

	@ApiModelProperty("时间范围({1:3Y,2:5Y,3:10Y})")
	private Integer timeHorizon;

	@ApiModelProperty("开始时间")
	private Integer startYear;

	@ApiModelProperty("结束时间")
	private Integer endYear;
	
	@ApiModelProperty("是否为专项指标,1是，0否")
	private int isSpecial = Constants.TRUE;

	@ApiModelProperty("tabindex。0专项指标、1资产负债表、2利润表、3现金流量表")
	private Integer tabIndex = 0; 
	
	@ApiModelProperty("指标单位{1:元,2:万,3:亿}")
	private Integer unit;
	
	public Long getIssId() {
		return issId;
	}

	public void setIssId(Long issId) {
		this.issId = issId;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public Integer[] getFinancialReportType() {
		return financialReportType;
	}

	public void setFinancialReportType(Integer[] financialReportType) {
		this.financialReportType = financialReportType;
	}

	public Integer getTimeHorizon() {
		return timeHorizon;
	}

	public void setTimeHorizon(Integer timeHorizon) {
		this.timeHorizon = timeHorizon;
	}

	public Integer getStartYear() {
		return startYear;
	}

	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}

	public Integer getEndYear() {
		return endYear;
	}

	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}

	public int getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(int isSpecial) {
		this.isSpecial = isSpecial;
	}

	public Integer getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(Integer tabIndex) {
		this.tabIndex = tabIndex;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}



}

