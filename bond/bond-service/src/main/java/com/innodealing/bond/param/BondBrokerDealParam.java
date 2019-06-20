package com.innodealing.bond.param;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class BondBrokerDealParam {

	@ApiModelProperty(value = "债券代码", required = true)
	private String bondCode;

	@ApiModelProperty(value = "broker报价bid的价格")
    private BigDecimal bidPrice = new BigDecimal(0);

	@ApiModelProperty(value = "broker报价bid交易量")
	private BigDecimal bidVol = new BigDecimal(0);

	@ApiModelProperty(value = "broker报价ofr的价格")
	private BigDecimal ofrPrice = new BigDecimal(0);

	@ApiModelProperty(value = "ofr_vol")
	private BigDecimal ofrVol = new BigDecimal(0);

	@ApiModelProperty(value = "Broker成交价", required = true)
	private BigDecimal bondStrikePrice;

	@ApiModelProperty(value = "Broker数数据的经纪商")
	private String brokerType;

	@ApiModelProperty(value = "Broker报价的最后更新时间", required = true)
	private Date sendTime;

	/**
	 * @return the bondCode
	 */
	public String getBondCode() {
		return bondCode;
	}

	/**
	 * @param bondCode
	 *            the bondCode to set
	 */
	public void setBondCode(String bondCode) {
		this.bondCode = bondCode;
	}

	/**
	 * @return the bidPrice
	 */
	public BigDecimal getBidPrice() {
		return bidPrice;
	}

	/**
	 * @param bidPrice
	 *            the bidPrice to set
	 */
	public void setBidPrice(BigDecimal bidPrice) {
		this.bidPrice = bidPrice;
	}

	/**
	 * @return the bidVol
	 */
	public BigDecimal getBidVol() {
		return bidVol;
	}

	/**
	 * @param bidVol
	 *            the bidVol to set
	 */
	public void setBidVol(BigDecimal bidVol) {
		this.bidVol = bidVol;
	}

	/**
	 * @return the ofrPrice
	 */
	public BigDecimal getOfrPrice() {
		return ofrPrice;
	}

	/**
	 * @param ofrPrice
	 *            the ofrPrice to set
	 */
	public void setOfrPrice(BigDecimal ofrPrice) {
		this.ofrPrice = ofrPrice;
	}

	/**
	 * @return the ofrVol
	 */
	public BigDecimal getOfrVol() {
		return ofrVol;
	}

	/**
	 * @param ofrVol
	 *            the ofrVol to set
	 */
	public void setOfrVol(BigDecimal ofrVol) {
		this.ofrVol = ofrVol;
	}

	/**
	 * @return the bondStrikePrice
	 */
	public BigDecimal getBondStrikePrice() {
		return bondStrikePrice;
	}

	/**
	 * @param bondStrikePrice the bondStrikePrice to set
	 */
	public void setBondStrikePrice(BigDecimal bondStrikePrice) {
		this.bondStrikePrice = bondStrikePrice;
	}

	/**
	 * @return the brokerType
	 */
	public String getBrokerType() {
		return brokerType;
	}

	/**
	 * @param brokerType
	 *            the brokerType to set
	 */
	public void setBrokerType(String brokerType) {
		this.brokerType = brokerType;
	}

	/**
	 * @return the sendTime
	 */
	public Date getSendTime() {
		return sendTime;
	}

	/**
	 * @param sendTime
	 *            the sendTime to set
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

}
