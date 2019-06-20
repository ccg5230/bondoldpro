package com.innodealing.bond.vo.favorite;

import com.innodealing.model.mongo.dm.BondFavoriteDoc;

import io.swagger.annotations.ApiModelProperty;

public class BondFavoriteVo extends BondFavoriteDoc{

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "消息总数")
	private Long eventMsgCount = 0L;

	public Long getEventMsgCount() {
		return eventMsgCount;
	}

	public void setEventMsgCount(Long eventMsgCount) {
		this.eventMsgCount = eventMsgCount;
	}
    
}
