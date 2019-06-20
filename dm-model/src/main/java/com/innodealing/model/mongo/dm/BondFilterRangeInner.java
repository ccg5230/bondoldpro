package com.innodealing.model.mongo.dm;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
public class BondFilterRangeInner {
	
	BondFilterRangeInner(){
			
	}
	@ApiModelProperty(value = "最小值")
	public BigDecimal min;
	
	@ApiModelProperty(value = "最大值")
	public BigDecimal max;
	
}
