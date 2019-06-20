package com.innodealing.bond.vo.finance;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
/**
 * 专项指标雷达图
 * @author zhaozhenglai
 * @date 2017年2月27日下午2:02:17
 *Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
public class FinanceIndicatorRadarChartVo{
	@ApiModelProperty("基准主体指标值")
	private BigDecimal valueIssStandard;
	
	@ApiModelProperty("对比主体指标值")
	private BigDecimal valueIssCompare;
	
	@ApiModelProperty("基准行业指标值")
	private BigDecimal valueInduStandard;
	
	@ApiModelProperty("对比行业指标值")
	private BigDecimal valueInduCompare;
	
	@ApiModelProperty("指标名称")
	private String fieldName;
	
	@ApiModelProperty("负向指标标识,1是、0不是")
	private Integer negative;

	public BigDecimal getValueIssStandard() {
		return valueIssStandard;
	}

	public void setValueIssStandard(BigDecimal valueIssStandard) {
		this.valueIssStandard = valueIssStandard;
	}

	public BigDecimal getValueIssCompare() {
		return valueIssCompare;
	}

	public void setValueIssCompare(BigDecimal valueIssCompare) {
		this.valueIssCompare = valueIssCompare;
	}

	public BigDecimal getValueInduStandard() {
		return valueInduStandard;
	}

	public void setValueInduStandard(BigDecimal valueInduStandard) {
		this.valueInduStandard = valueInduStandard;
	}

	public BigDecimal getValueInduCompare() {
		return valueInduCompare;
	}

	public void setValueInduCompare(BigDecimal valueInduCompare) {
		this.valueInduCompare = valueInduCompare;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	
	public Integer getNegative() {
		return negative;
	}

	public void setNegative(Integer negative) {
		this.negative = negative;
	}



	public class RadarIssInduItemVo{
		@ApiModelProperty("主体指标值")
		private BigDecimal valueIss;
		
		@ApiModelProperty("行业指标值")
		private BigDecimal valueIndu;
		
		@ApiModelProperty("行业指标名称")
		private String fieldName;
		
		@ApiModelProperty("负向指标标识,1是、0不是")
		private Integer negative;

		public BigDecimal getValueIss() {
			return valueIss;
		}

		public void setValueIss(BigDecimal valueIss) {
			this.valueIss = valueIss;
		}

		public BigDecimal getValueIndu() {
			return valueIndu;
		}

		public void setValueIndu(BigDecimal valueIndu) {
			this.valueIndu = valueIndu;
		}

		public String getFieldName() {
			return fieldName;
		}

		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}

		public Integer getNegative() {
			return negative;
		}

		public void setNegative(Integer negative) {
			this.negative = negative;
		}
		
		
	}
	
}
