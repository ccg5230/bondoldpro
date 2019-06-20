package com.innodealing.bond.param;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author stephen.ma
 * @date 2016年9月5日
 * @clasename BaseQuoteParam.java
 * @decription TODO
 */
@ApiModel
public class BaseQuoteParam extends BaseQuotePriceParam{
	
	@ApiModelProperty(value = "债券UniCode")
    private Long bondUniCode;
	
	@ApiModelProperty(value = "债券代码", required=true)
	private String bondCode;
	
	@ApiModelProperty(value = "债券简称")
	private String bondShortName;
	
	@ApiModelProperty(value = "是否匿名报价,0 否，1 是")
    private Integer anonymous;
	
	@ApiModelProperty(value = "备注")
	private String remark;
	
	@ApiModelProperty(value = "0 QQ 1微信 2 PC客户端 3 PC网页端 4手机APP(老版本) 5管理后台 6 IM群 7 IOS 8 Android 9 Broker")
    private Integer postfrom;
	
	public Long getBondUniCode() {
		return bondUniCode;
	}

	public void setBondUniCode(Long bondUniCode) {
		this.bondUniCode = bondUniCode;
	}

	public String getBondCode() {
		return bondCode;
	}

	public void setBondCode(String bondCode) {
		this.bondCode = bondCode;
	}

	public String getBondShortName() {
		return bondShortName;
	}

	public void setBondShortName(String bondShortName) {
		this.bondShortName = bondShortName;
	}

	public Integer getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(Integer anonymous) {
		this.anonymous = anonymous;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getPostfrom() {
		return postfrom;
	}

	public void setPostfrom(Integer postfrom) {
		this.postfrom = postfrom;
	}
	
}
