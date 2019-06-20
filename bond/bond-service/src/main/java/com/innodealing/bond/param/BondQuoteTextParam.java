package com.innodealing.bond.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author stephen.ma
 * @date 2016年9月9日
 * @clasename BondQuoteTextParam.java
 * @decription TODO
 */
@ApiModel
public class BondQuoteTextParam {

	@ApiModelProperty(value = "发送源", required=true)
	private Integer postfrom;
	
	@ApiModelProperty(value = "发送的需求文本", required = true)
	private String content;

	public Integer getPostfrom() {
		return postfrom;
	}

	public void setPostfrom(Integer postfrom) {
		this.postfrom = postfrom;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
