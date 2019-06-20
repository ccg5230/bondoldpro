package com.innodealing.bond.vo.area;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
public class AreaIndicatorKChartVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "指标维度")
	private String quarter;
	
	@ApiModelProperty(value = "自身")
	private  Integer self;
	
	@ApiModelProperty(value = "行业10分位")
	private BigDecimal quantile10;
	
	@ApiModelProperty(value = "行业25分位")
	private BigDecimal quantile25;
	
	@ApiModelProperty(value = "行业50分位")
	private BigDecimal quantile50;
	
	@ApiModelProperty(value = "行业75分位")
	private BigDecimal quantile75;
	
	@ApiModelProperty(value = "行业90分位")
	private BigDecimal quantile90;
	
	@ApiModelProperty(value = "最小分位")
	private BigDecimal quantileMIn;
	
	@ApiModelProperty(value = "最大分位")
	private BigDecimal quantileMax;
	
	@ApiModelProperty(value = "区域值")
	private BigDecimal quantileIss;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Integer getSelf() {
		return self;
	}


	public void setSelf(Integer self) {
		this.self = self;
	}

	public String getQuarter() {
		return quarter;
	}


	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}


	public BigDecimal getQuantile10() {
		return quantile10;
	}

	public BigDecimal getQuantile25() {
		return quantile25;
	}

	public BigDecimal getQuantile50() {
		return quantile50;
	}

	public BigDecimal getQuantile75() {
		return quantile75;
	}

	public BigDecimal getQuantile90() {
		return quantile90;
	}

	public BigDecimal getQuantileMIn() {
		return quantileMIn;
	}

	public BigDecimal getQuantileMax() {
		return quantileMax;
	}

	public BigDecimal getQuantileIss() {
		return quantileIss;
	}

	

	public void setQuantile10(BigDecimal quantile10) {
		this.quantile10 = quantile10;
	}

	public void setQuantile25(BigDecimal quantile25) {
		this.quantile25 = quantile25;
	}

	public void setQuantile50(BigDecimal quantile50) {
		this.quantile50 = quantile50;
	}

	public void setQuantile75(BigDecimal quantile75) {
		this.quantile75 = quantile75;
	}

	public void setQuantile90(BigDecimal quantile90) {
		this.quantile90 = quantile90;
	}

	public void setQuantileMIn(BigDecimal quantileMIn) {
		this.quantileMIn = quantileMIn;
	}

	public void setQuantileMax(BigDecimal quantileMax) {
		this.quantileMax = quantileMax;
	}

	public void setQuantileIss(BigDecimal quantileIss) {
		this.quantileIss = quantileIss;
	}

	@Override
	public String toString() {
		return "AreaIndicatorKChartVo [quarter=" + quarter + ", quantile10=" + quantile10 + ", quantile25=" + quantile25
				+ ", quantile50=" + quantile50 + ", quantile75=" + quantile75 + ", quantile90=" + quantile90
				+ ", quantileMIn=" + quantileMIn + ", quantileMax=" + quantileMax + ", quantileIss=" + quantileIss
				+ "]";
	}
	
	
	
}
