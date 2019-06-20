package com.innodealing.bond.vo.msg;

import java.io.Serializable;

import com.innodealing.model.mongo.dm.BondNotificationMsgDoc;

import io.swagger.annotations.ApiModelProperty;

public class BondNotificationsVo extends BondNotificationMsgDoc implements Serializable {

	/**
	 * BondNotificationsVo
	 */
	private static final long serialVersionUID = 1L;
	
    @ApiModelProperty(value = "债券代码")
    private String bondCode;
    
    @ApiModelProperty(value = "债券名称")
    private String bondName;
    
    @ApiModelProperty(value = "剩余期限")
    private String tenor;

	@ApiModelProperty(value = "类别名称")
	private String radarTypeName;
    
	/**
	 * @return the bondCode
	 */
	public String getBondCode() {
		return bondCode;
	}

	/**
	 * @param bondCode the bondCode to set
	 */
	public void setBondCode(String bondCode) {
		this.bondCode = bondCode;
	}

	/**
	 * @return the bondName
	 */
	public String getBondName() {
		return bondName;
	}

	/**
	 * @param bondName the bondName to set
	 */
	public void setBondName(String bondName) {
		this.bondName = bondName;
	}

	/**
	 * @return the tenor
	 */
	public String getTenor() {
		return tenor;
	}

	/**
	 * @param tenor the tenor to set
	 */
	public void setTenor(String tenor) {
		this.tenor = tenor;
	}

	public String getRadarTypeName() {
		return radarTypeName;
	}

	public void setRadarTypeName(String radarTypeName) {
		this.radarTypeName = radarTypeName;
	}
}
