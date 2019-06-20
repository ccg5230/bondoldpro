package com.innodealing.bond.vo.area;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class AreaIssuerSortVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "地区的总数")
	private Long totalNum;
	
	@ApiModelProperty(value = "行业前10")
	private List<AreaIssuerSortItemVo> top10;
	
	private List<AreaIssuerSortItemVo> near5;

	public Long getTotalNum() {
		return totalNum;
	}

	public List<AreaIssuerSortItemVo> getTop10() {
		return top10;
	}

	public List<AreaIssuerSortItemVo> getNear5() {
		return near5;
	}

	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}

	public void setTop10(List<AreaIssuerSortItemVo> top10) {
		this.top10 = top10;
	}

	public void setNear5(List<AreaIssuerSortItemVo> near5) {
		this.near5 = near5;
	}

	
}
