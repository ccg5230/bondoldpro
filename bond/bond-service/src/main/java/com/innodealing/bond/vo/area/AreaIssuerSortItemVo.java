package com.innodealing.bond.vo.area;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

@JsonInclude(value = Include.NON_NULL)
public class AreaIssuerSortItemVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "地区的总数")
	private Long totalNum;
	
	@ApiParam(value = "区域排名编号")
	private Integer idx;
	
	@ApiModelProperty(value="区域code")
	private Long areaUniCode ;
	
	@ApiModelProperty(value="区域名称")
	private String areaChiName;
	
	@ApiParam("是否是当前区域自己(1是、0不是)")
	private  Integer self;
	
	private String time;
	
	private BigDecimal value;

	public Long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}

	public Long getAreaUniCode() {
		return areaUniCode;
	}

	public void setAreaUniCode(Long areaUniCode) {
		this.areaUniCode = areaUniCode;
	}

	public String getAreaChiName() {
		return areaChiName;
	}

	public void setAreaChiName(String areaChiName) {
		this.areaChiName = areaChiName;
	}

	public Integer getSelf() {
		return self;
	}

	public void setSelf(Integer self) {
		this.self = self;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "AreaIssuerSortItemVo [totalNum=" + totalNum + ", idx=" + idx + ", areaUniCode=" + areaUniCode
				+ ", areaChiName=" + areaChiName + ", self=" + self + ", time=" + time + ", value=" + value + "]";
	}

	


	

	
	
}
