package com.innodealing.bond.vo.favorite;

import com.innodealing.model.mongo.dm.BondDetailDoc;

import io.swagger.annotations.ApiModelProperty;

public class BondDetailVo extends BondDetailDoc {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	
	@ApiModelProperty(value = "持仓量")
	private Integer openinterest;
	
	@ApiModelProperty(value = "变动消息数量")
	private Long eventMsgCount;
	
	@ApiModelProperty(value = "备注")
	private String remark;
    
    @ApiModelProperty(value = "是否过期， 过期 1， 未过期 0")
    private Integer expiredState;
    
    @ApiModelProperty(value = "评级展望")
    private String ratePros;

	/**
	 * @return the openinterest
	 */
	public Integer getOpeninterest() {
		return openinterest;
	}

	/**
	 * @param openinterest the openinterest to set
	 */
	public void setOpeninterest(Integer openinterest) {
		this.openinterest = openinterest;
	}

	/**
	 * @return the eventMsgCount
	 */
	public Long getEventMsgCount() {
		return eventMsgCount;
	}

	/**
	 * @param eventMsgCount the eventMsgCount to set
	 */
	public void setEventMsgCount(Long eventMsgCount) {
		this.eventMsgCount = eventMsgCount;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the expiredState
	 */
	public Integer getExpiredState() {
		return expiredState;
	}

	/**
	 * @param expiredState the expiredState to set
	 */
	public void setExpiredState(Integer expiredState) {
		this.expiredState = expiredState;
	}

	public String getRatePros() {
		return ratePros;
	}

	public void setRatePros(String ratePros) {
		this.ratePros = ratePros;
	}
	
}
