/**
 * 
 */
package com.innodealing.bond.vo.msg;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author stephen.ma
 * @date 2017年1月4日
 * @clasename BondNotificationStatistic.java
 * @decription TODO
 */
public class BondNotificationStatistic {

    @ApiModelProperty(value = "债券总数")
	private Long bondCount = 0L;
	
    @ApiModelProperty(value = "提醒消息总数")
	private Long eventMsgCount = 0L;

	public Long getBondCount() {
		return bondCount;
	}

	public void setBondCount(Long bondCount) {
		this.bondCount = bondCount;
	}

	public Long getEventMsgCount() {
		return eventMsgCount;
	}

	public void setEventMsgCount(Long eventMsgCount) {
		this.eventMsgCount = eventMsgCount;
	}
    
}
