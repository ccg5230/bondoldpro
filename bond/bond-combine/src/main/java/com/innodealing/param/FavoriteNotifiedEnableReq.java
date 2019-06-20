package com.innodealing.param;

import io.swagger.annotations.ApiModelProperty;

/** 
* @author feng.ma
* @date 2017年8月17日 下午7:48:19 
* @describe 
*/
public class FavoriteNotifiedEnableReq {

	@ApiModelProperty(value = "债券变动是否提醒，1 提醒， 0 不提醒, 针对卡片消息")
	private Integer notifiedEnable;

	public Integer getNotifiedEnable() {
		return notifiedEnable;
	}

	public void setNotifiedEnable(Integer notifiedEnable) {
		this.notifiedEnable = notifiedEnable;
	}
	
}
