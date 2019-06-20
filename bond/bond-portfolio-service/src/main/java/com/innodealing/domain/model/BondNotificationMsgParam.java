package com.innodealing.domain.model;

import com.innodealing.model.dm.bond.BondNotificationMsg;

import io.swagger.annotations.ApiModelProperty;

/** 
* @author feng.ma
* @date 2017年8月16日 下午6:17:53 
* @describe 
*/
public class BondNotificationMsgParam extends BondNotificationMsg {

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "用户id")
    private Integer userId;
	
	@ApiModelProperty(value = "投组是否开启通知 1 开启，0 不开启")
	private Integer notifiedEnable;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getNotifiedEnable() {
		return notifiedEnable;
	}

	public void setNotifiedEnable(Integer notifiedEnable) {
		this.notifiedEnable = notifiedEnable;
	}
	
}
