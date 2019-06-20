package com.innodealing.bond.param;

import aj.org.objectweb.asm.Type;
import io.swagger.annotations.ApiModelProperty;

/**
 * 雷达图查询条件封装
 * @author Administrator
 *
 */
public class RaddrFilter {
		
	@ApiModelProperty("地区代码")
	private Long areaUniCode;
	
	@ApiModelProperty("基准月度")
	private Integer bondMonth;
	
	@ApiModelProperty("对比月度")
	private Integer contrastBondMonth;
	
	@ApiModelProperty("基准季度")
	private Integer bondQuarter;
	
	@ApiModelProperty("对比季度")
	private Integer contrastBondQuarter;
		
	@ApiModelProperty("基准年度")
	private Integer bondYear;
	
	@ApiModelProperty("对比年度")
	private Integer contrastBondYear;
	
	@ApiModelProperty("数据类型:年度  季度 月度")
	private String dataType ;
	
	@ApiModelProperty("数据来源:公报")
	private String statisticsType;

	public Long getAreaUniCode() {
		return areaUniCode;
	}

	public void setAreaUniCode(Long areaUniCode) {
		this.areaUniCode = areaUniCode;
	}

	public Integer getBondMonth() {
		return bondMonth;
	}

	public void setBondMonth(Integer bondMonth) {
		this.bondMonth = bondMonth;
	}

	public Integer getContrastBondMonth() {
		return contrastBondMonth;
	}

	public void setContrastBondMonth(Integer contrastBondMonth) {
		this.contrastBondMonth = contrastBondMonth;
	}

	public Integer getBondQuarter() {
		return bondQuarter;
	}

	public void setBondQuarter(Integer bondQuarter) {
		this.bondQuarter = bondQuarter;
	}

	public Integer getContrastBondQuarter() {
		return contrastBondQuarter;
	}

	public void setContrastBondQuarter(Integer contrastBondQuarter) {
		this.contrastBondQuarter = contrastBondQuarter;
	}

	public Integer getBondYear() {
		return bondYear;
	}

	public void setBondYear(Integer bondYear) {
		this.bondYear = bondYear;
	}

	public Integer getContrastBondYear() {
		return contrastBondYear;
	}

	public void setContrastBondYear(Integer contrastBondYear) {
		this.contrastBondYear = contrastBondYear;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getStatisticsType() {
		return statisticsType;
	}

	public void setStatisticsType(String statisticsType) {
		this.statisticsType = statisticsType;
	}

	
	
}
