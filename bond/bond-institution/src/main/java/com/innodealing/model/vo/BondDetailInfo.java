package com.innodealing.model.vo;

import com.innodealing.util.Column;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * 
 * @author 戴永杰
 *
 * @date 2017年12月28日 下午3:36:10 
 * @version V1.0   
 *
 */
public class BondDetailInfo {

	
	@ApiModelProperty(value = "主体Code")
	@Column(name="com_uni_code")
	private String comUniCode;
	
	@ApiModelProperty(value = "公司名称")
	@Column(name="com_chi_name")
	private String comChiName;
	
	@ApiModelProperty(value = "公司简称")
	@Column(name="com_chi_short_name")
	private String comChiShortName;
	
	@ApiModelProperty(value = "法人代表")
	@Column(name="leg_per")
	private String legPer;
	
	@ApiModelProperty(value = "工商注册号")
	@Column(name="ic_reg_code")
	private String icRegCode;
	
	@ApiModelProperty(value = "公司电话")
	@Column(name="com_tel")
	private String comTel;
	
	@ApiModelProperty(value = "公司人数")
	@Column(name="staff_sum")
	private String staffSum;
	
	@ApiModelProperty(value = "公司网址")
	@Column(name="com_web")
	private String comWeb;
	
	
	@ApiModelProperty(value = "注册地址")
	@Column(name="reg_addr")
	private String regAddr;
	
	
	@ApiModelProperty(value = "公司简介")
	@Column(name="com_pro")
	private String comPro;


	public String getComUniCode() {
		return comUniCode;
	}


	public void setComUniCode(String comUniCode) {
		this.comUniCode = comUniCode;
	}


	public String getComChiName() {
		return comChiName;
	}


	public void setComChiName(String comChiName) {
		this.comChiName = comChiName;
	}


	public String getComChiShortName() {
		return comChiShortName;
	}


	public void setComChiShortName(String comChiShortName) {
		this.comChiShortName = comChiShortName;
	}


	public String getLegPer() {
		return legPer;
	}


	public void setLegPer(String legPer) {
		this.legPer = legPer;
	}


	public String getIcRegCode() {
		return icRegCode;
	}


	public void setIcRegCode(String icRegCode) {
		this.icRegCode = icRegCode;
	}


	public String getComTel() {
		return comTel;
	}


	public void setComTel(String comTel) {
		this.comTel = comTel;
	}


	public String getStaffSum() {
		return staffSum;
	}


	public void setStaffSum(String staffSum) {
		this.staffSum = staffSum;
	}


	public String getComWeb() {
		return comWeb;
	}


	public void setComWeb(String comWeb) {
		this.comWeb = comWeb;
	}


	public String getRegAddr() {
		return regAddr;
	}


	public void setRegAddr(String regAddr) {
		this.regAddr = regAddr;
	}


	public String getComPro() {
		return comPro;
	}


	public void setComPro(String comPro) {
		this.comPro = comPro;
	}


	
}
