package com.innodealing.broker.model;

import java.math.BigDecimal;

/**
 * @author stephen.ma
 * @date 2016年9月5日
 * @clasename BaseQuoteParam.java
 * @decription TODO
 */
public class BaseQuoteParam {
	
    private Long bondUniCode;
	
	private String bondCode;
	
	private String bondShortName;
	
	private Integer side;
	
    private BigDecimal bondPrice;
	
    private BigDecimal bondVol;
	
    private Integer anonymous;
	
	private String remark;
	
    private Integer postfrom;
	
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

	public String getBondShortName() {
		return bondShortName;
	}

	public void setBondShortName(String bondShortName) {
		this.bondShortName = bondShortName;
	}

	public Integer getSide() {
		return side;
	}

	public void setSide(Integer side) {
		this.side = side;
	}

	public BigDecimal getBondPrice() {
		return bondPrice;
	}

	public void setBondPrice(BigDecimal bondPrice) {
		this.bondPrice = bondPrice;
	}

	public BigDecimal getBondVol() {
		return bondVol;
	}

	public void setBondVol(BigDecimal bondVol) {
		this.bondVol = bondVol;
	}

	public Integer getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(Integer anonymous) {
		this.anonymous = anonymous;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getPostfrom() {
		return postfrom;
	}

	public void setPostfrom(Integer postfrom) {
		this.postfrom = postfrom;
	}
	
}
