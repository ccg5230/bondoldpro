package com.innodealing.broker.model;

import java.io.Serializable;

public class BaseQuoteBaordParam implements Serializable {

	private static final long serialVersionUID = 1L;

	private String bondCode;

	private String bondShortName;

	private String bondPrice;

	private String bondVol;

	private String ofrBondPrice;

	private String ofrBondVol;

	private String sendTime;

	private String residualMaturity;

	private Integer brokerType;

	private String bondStrikePrice;

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

	public String getBondPrice() {
		return bondPrice;
	}

	public void setBondPrice(String bondPrice) {
		this.bondPrice = bondPrice;
	}

	public String getBondVol() {
		return bondVol;
	}

	public void setBondVol(String bondVol) {
		this.bondVol = bondVol;
	}

	public String getOfrBondPrice() {
		return ofrBondPrice;
	}

	public void setOfrBondPrice(String ofrBondPrice) {
		this.ofrBondPrice = ofrBondPrice;
	}

	public String getOfrBondVol() {
		return ofrBondVol;
	}

	public void setOfrBondVol(String ofrBondVol) {
		this.ofrBondVol = ofrBondVol;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getResidualMaturity() {
		return residualMaturity;
	}

	public void setResidualMaturity(String residualMaturity) {
		this.residualMaturity = residualMaturity;
	}

	public Integer getBrokerType() {
		return brokerType;
	}

	public void setBrokerType(Integer brokerType) {
		this.brokerType = brokerType;
	}

	public String getBondStrikePrice() {
		return bondStrikePrice;
	}

	public void setBondStrikePrice(String bondStrikePrice) {
		this.bondStrikePrice = bondStrikePrice;
	}

}
