package com.innodealing.bond.param;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author stephen.ma
 * @date 2016年9月5日
 * @clasename BondQuoteAddParam.java
 * @decription TODO
 */
@ApiModel
public class BondQuoteAddParam {
	
	@ApiModelProperty(value = "报价/需求入参List", required=true)
	private List<BaseQuoteParam> quotes;

	public List<BaseQuoteParam> getQuotes() {
		return quotes;
	}

	public void setQuotes(List<BaseQuoteParam> quotes) {
		this.quotes = quotes;
	}

}
