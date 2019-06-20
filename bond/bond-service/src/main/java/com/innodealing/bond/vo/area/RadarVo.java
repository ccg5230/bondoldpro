package com.innodealing.bond.vo.area;

import java.util.List;

import com.innodealing.model.mongo.dm.EconomieIndicator;

import io.swagger.annotations.ApiModelProperty;

public class RadarVo {
	
	@ApiModelProperty("返回数据类型:基准 对比")
	private String type ;
	
	@ApiModelProperty("返回指标的值")
	private List<EconomieIndicator> economieIndicators;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<EconomieIndicator> getEconomieIndicators() {
		return economieIndicators;
	}

	public void setEconomieIndicators(List<EconomieIndicator> economieIndicators) {
		this.economieIndicators = economieIndicators;
	} 
		
}
