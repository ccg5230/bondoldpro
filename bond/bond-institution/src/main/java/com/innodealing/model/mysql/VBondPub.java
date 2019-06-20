package com.innodealing.model.mysql;

import com.innodealing.util.Column;

import io.swagger.annotations.ApiModelProperty;

public class VBondPub  {

	private int id;
	
	@Column(name="bond_uni_code")
	@ApiModelProperty(value = "债券编码")
	private String bondUniCode;
	

	@Column(name="bond_short_name")
	@ApiModelProperty(value = "债券简称")
	private String bondShortName;
	
	@Column(name="bond_full_name")
	@ApiModelProperty(value = "债券全称")
	private String bondFullName;
	
	@Column(name="iss_name")
	@ApiModelProperty(value = "发行人名称")
	private String issName;
	
	@ApiModelProperty(value = "备注")
	private String remark;
	
	
	@Column(name="com_chi_name")
	@ApiModelProperty(value = "关联发行人")
	private String comChiName;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getBondUniCode() {
		return bondUniCode;
	}


	public void setBondUniCode(String bondUniCode) {
		this.bondUniCode = bondUniCode;
	}


	public String getBondShortName() {
		return bondShortName;
	}


	public void setBondShortName(String bondShortName) {
		this.bondShortName = bondShortName;
	}


	public String getBondFullName() {
		return bondFullName;
	}


	public void setBondFullName(String bondFullName) {
		this.bondFullName = bondFullName;
	}


	public String getIssName() {
		return issName;
	}


	public void setIssName(String issName) {
		this.issName = issName;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getComChiName() {
		return comChiName;
	}


	public void setComChiName(String comChiName) {
		this.comChiName = comChiName;
	}
	
	
	
}
