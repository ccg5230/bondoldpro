package com.innodealing.model.mysql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.innodealing.util.Column;

import io.swagger.annotations.ApiModelProperty;


/**
 * 债券
 * 
 * @author 戴永杰
 *
 * @date 2017年9月14日 下午2:38:32 
 * @version V1.0   
 *
 */
public class VBondInfo {
	
	@ApiModelProperty(value = "债券标识")
	@Column(name="bond_uni_code")
	private String bondUniCode;
	
	
	@ApiModelProperty(value = "债券代码")
	@Column(name="bond_uni_code")
	private String bondCode;
	
	@ApiModelProperty(value = "债券市场")
	@Column(name="sec_mar_par")
	@JsonIgnore
	private int secMarPar;
	
	public int getSecMarPar() {
		return secMarPar;
	}


	public void setSecMarPar(int secMarPar) {
		this.secMarPar = secMarPar;
	}


	public String getBondCode() {
		return bondCode;
	}


	public void setBondCode(String bondCode) {
		this.bondCode = bondCode;
	}


	@ApiModelProperty(value = "发行人标识")
	@Column(name="com_uni_code")
	private String comUniCode;
	
	
	@ApiModelProperty(value = "债券全称")
	@Column(name="bond_full_name")
	private String bondFullName;
	
	
	@ApiModelProperty(value = "债券简称")
	@Column(name="bond_short_name")
	private String bondShortName;


	public String getBondUniCode() {
		return bondUniCode;
	}


	public void setBondUniCode(String bondUniCode) {
		this.bondUniCode = bondUniCode;
	}


	public String getComUniCode() {
		return comUniCode;
	}


	public void setComUniCode(String comUniCode) {
		this.comUniCode = comUniCode;
	}


	public String getBondFullName() {
		return bondFullName;
	}


	public void setBondFullName(String bondFullName) {
		this.bondFullName = bondFullName;
	}


	public String getBondShortName() {
		return bondShortName;
	}


	public void setBondShortName(String bondShortName) {
		this.bondShortName = bondShortName;
	}
	
	
	
	
	

}
