package com.innodealing.bond.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class DefaultBondParam {
	
	@ApiModelProperty(value = "债券UniCode，BondID", required=true)
    private Long bondUniCode;
	
	@ApiModelProperty(value = "债券代码")
	private String bondCode;
	
	@ApiModelProperty(value = "发行人UniCode， IsserID")
    private Long comUniCode;
	
	@ApiModelProperty(value = "债券违约日期， yyyy-MM-dd")
    private String defaultDate;
	
	@ApiModelProperty(value = "债券违约事件")
    private String eventContent;

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

	public Long getComUniCode() {
		return comUniCode;
	}

	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

	public String getDefaultDate() {
		return defaultDate;
	}

	public void setDefaultDate(String defaultDate) {
		this.defaultDate = defaultDate;
	}

	public String getEventContent() {
		return eventContent;
	}

	public void setEventContent(String eventContent) {
		this.eventContent = eventContent;
	}
	
}
