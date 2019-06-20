package com.innodealing.model.mysql;

import com.innodealing.util.Column;

import io.swagger.annotations.ApiModelProperty;

/**
 * 试图 主题/发行人
 * 
 * @author 戴永杰
 *
 * @date 2017年9月14日 上午10:06:44 
 * @version V1.0   
 *
 */
public class VPubInfo {

	@ApiModelProperty(value = "发行人标识")
	@Column(name="com_uni_code")
	private String comUniCode;
	
	@ApiModelProperty(value = "发行人全称")
	@Column(name="com_chi_name")
	private String comChiFullName;
	
	@ApiModelProperty(value = "发行人简称")
	@Column(name="com_chi_short_name")
	private String comChiShortName;
	
	public String getComUniCode() {
		return comUniCode;
	}
	public void setComUniCode(String comUniCode) {
		this.comUniCode = comUniCode;
	}
	public String getComChiFullName() {
		return comChiFullName;
	}
	public void setComChiFullName(String comChiFullName) {
		this.comChiFullName = comChiFullName;
	}
	public String getComChiShortName() {
		return comChiShortName;
	}
	public void setComChiShortName(String comChiShortName) {
		this.comChiShortName = comChiShortName;
	}
	
	

}
