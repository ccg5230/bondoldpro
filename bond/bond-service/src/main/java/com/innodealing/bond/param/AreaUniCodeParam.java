package com.innodealing.bond.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class AreaUniCodeParam {
	
	@ApiModelProperty(value="地区代码")
	private Long areaUniCode;
	
	@ApiModelProperty(value="地区名称")
	private String areaChiName;
	
	@ApiModelProperty(value="是否为所在的省或者市或者地区")
	private Integer selected; //0-不在   1-在

	

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

	public Integer getSelected() {
		return selected;
	}

	public void setSelected(Integer selected) {
		this.selected = selected;
	}

	
	
	
}
