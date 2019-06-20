package com.innodealing.bond.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class IssuerFavorityAddReq {

	@ApiModelProperty(value = "发行人编号")
	private Long issuerUniCode;

	public Long getIssuerUniCode() {
		return issuerUniCode;
	}

	public void setIssuerUniCode(Long issuerUniCode) {
		this.issuerUniCode = issuerUniCode;
	}
	
}
