package com.innodealing.bond.param;


import com.innodealing.model.mongo.dm.BondSentimentDistinctDoc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class BondSentimentParam {
	
	@ApiModelProperty(value = "参数对象", required=true)
	private BondSentimentDistinctDoc doc;

	public BondSentimentDistinctDoc getDoc() {
		return doc;
	}

	public void setDoc(BondSentimentDistinctDoc doc) {
		this.doc = doc;
	}

	

}
