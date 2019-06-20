package com.innodealing.bond.param;

import java.util.List;
import io.swagger.annotations.ApiModelProperty;

/**
 * 区域经济指标过滤条件
 * @author Administrator
 *
 */
public class AreaEconomiesIndicatorFilter {
		
	@ApiModelProperty("区域代码")
	private Long areaUniCode ;
	
	@ApiModelProperty("时间范围:1(1Y),2(3Y),3(5Y)")
	private Integer timeHorizon ;
	
	@ApiModelProperty("开始时间")
	private Integer startYear;
	
	@ApiModelProperty("结束时间")
	private Integer endYear;
	
	@ApiModelProperty("来源:公报 年鉴")
	private String statisticsType ;
	
	@ApiModelProperty("数据类型:年度 季度 月度")
	private String dataType;

	public Long getAreaUniCode() {
		return areaUniCode;
	}

	public void setAreaUniCode(Long areaUniCode) {
		this.areaUniCode = areaUniCode;
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
