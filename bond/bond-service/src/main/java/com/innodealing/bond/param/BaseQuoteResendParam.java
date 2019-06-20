package com.innodealing.bond.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author stephen.ma
 * @date 2016年9月19日
 * @clasename BaseQuoteResendParam.java
 * @decription TODO
 */
@ApiModel
public class BaseQuoteResendParam extends BaseQuotePriceParam {
	
	@ApiModelProperty(value = "0 QQ 1微信 2 PC客户端 3 PC网页端 4手机APP(老版本) 5管理后台 6 IM群 7 IOS 8 Android 9 Broker")
    private Integer postfrom;

	@ApiModelProperty(value = "备注")
	private String remark;
	
	public Integer getPostfrom() {
		return postfrom;
	}

	public void setPostfrom(Integer postfrom) {
		this.postfrom = postfrom;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
