package com.innodealing.bond.param.area;

import java.io.Serializable;
import java.util.List;

import com.innodealing.annotation.QueryField;
import com.innodealing.consts.Constants;

import io.swagger.annotations.ApiModelProperty;

public class IndicatorAreaInstrucationsFilter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("指标code")
	@QueryField(columnName = "field", option = "in")
	private List<String> fields;

	@ApiModelProperty("来源:公报 年鉴")
	private String statisticsType ;
	
	@ApiModelProperty("数据类型:年度 季度 月度")
	private String dataType;

	@ApiModelProperty("时间范围:1(1Y),2(2Y),3(3Y)")
	private Integer timeHorizon;

	@ApiModelProperty("开始时间")
	private Integer startYear;

	@ApiModelProperty("结束时间")
	private Integer endYear;
	

	public List<String> getFields() {
		return fields;
	}

	public Integer getTimeHorizon() {
		return timeHorizon;
	}

	public Integer getStartYear() {
		return startYear;
	}

	public Integer getEndYear() {
		return endYear;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	
	public void setTimeHorizon(Integer timeHorizon) {
		this.timeHorizon = timeHorizon;
	}

	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}

	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}

	public String getStatisticsType() {
		return statisticsType;
	}

	public void setStatisticsType(String statisticsType) {
		this.statisticsType = statisticsType;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

}
