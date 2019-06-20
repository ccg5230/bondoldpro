package com.innodealing.bond.vo.finance;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiParam;

/**
 * 规定时间未收集到财报信息Vo<p>
 * @author 赵正来
 *
 */


@JsonInclude(value = Include.NON_NULL)
public class IssNoFinanceInDateVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiParam(value = "主体唯一id号")
	private Long comUniCode;
	
	@ApiParam(value = "季度")
	private String quarter;

	public Long getComUniCode() {
		return comUniCode;
	}

	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	public IssNoFinanceInDateVo(Long comUniCode, String quarter) {
		super();
		this.comUniCode = comUniCode;
		this.quarter = quarter;
	}

	public IssNoFinanceInDateVo() {
		super();
	}

	@Override
	public String toString() {
		return "IssNoFinanceInDateVo [comUniCode=" + comUniCode + ", quarter=" + quarter + "]";
	}
	
	
}
