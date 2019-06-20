package com.innodealing.bond.vo.msg;

import com.innodealing.model.mongo.dm.BondNotificationMsgDoc;

import io.swagger.annotations.ApiModelProperty;

public class BondNotificationMsgVo extends BondNotificationMsgDoc{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	
	@ApiModelProperty(value = "提醒消息是否已读， 1 已读,0 未读")
	private Integer readStatus;


	/**
	 * @return the readStatus
	 */
	public Integer getReadStatus() {
		return readStatus;
	}


	/**
	 * @param readStatus the readStatus to set
	 */
	public void setReadStatus(Integer readStatus) {
		this.readStatus = readStatus;
	}
	
}
