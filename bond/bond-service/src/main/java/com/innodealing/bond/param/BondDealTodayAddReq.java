package com.innodealing.bond.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel
public class BondDealTodayAddReq {
	
	@ApiModelProperty(value = "条件")
	String condition;
	
	@ApiModelProperty(value = "是否是父类(默认否)")
	Boolean isParent=false;
	
	@ApiModelProperty(value = "是否包含存单数据,默认(默认否)-->只有今日报价信用债")
	Boolean notNcd=false;

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public Boolean getNotNcd() {
		return notNcd;
	}

	public void setNotNcd(Boolean notNcd) {
		this.notNcd = notNcd;
	}
	
	
	
	
}
