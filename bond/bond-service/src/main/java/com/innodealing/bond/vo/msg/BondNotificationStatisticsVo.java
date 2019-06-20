package com.innodealing.bond.vo.msg;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author stephen.ma
 * @date 2017年1月4日
 * @clasename BondNotificationStatisticsVo.java
 * @decription TODO
 */
public class BondNotificationStatisticsVo {

	@ApiModelProperty(value = "存续统计")
	private BondNotificationStatistic maturityStatistic;

	@ApiModelProperty(value = "评级统计")
	private BondNotificationStatistic ratingStatistic;

	@ApiModelProperty(value = "舆情统计")
	private BondNotificationStatistic sentimentStatistic;

	@ApiModelProperty(value = "其他统计")
	private BondNotificationStatistic otherStatistic;

	@ApiModelProperty(value = "价格指标统计")
	private BondNotificationStatistic priceIdxStatistic;

	@ApiModelProperty(value = "财务指标统计")
	private BondNotificationStatistic finaIdxStatistic;

	public BondNotificationStatistic getMaturityStatistic() {
		return maturityStatistic;
	}

	public void setMaturityStatistic(BondNotificationStatistic maturityStatistic) {
		this.maturityStatistic = maturityStatistic;
	}

	public BondNotificationStatistic getRatingStatistic() {
		return ratingStatistic;
	}

	public void setRatingStatistic(BondNotificationStatistic ratingStatistic) {
		this.ratingStatistic = ratingStatistic;
	}

	public BondNotificationStatistic getSentimentStatistic() {
		return sentimentStatistic;
	}

	public void setSentimentStatistic(BondNotificationStatistic sentimentStatistic) {
		this.sentimentStatistic = sentimentStatistic;
	}

	public BondNotificationStatistic getOtherStatistic() {
		return otherStatistic;
	}

	public void setOtherStatistic(BondNotificationStatistic otherStatistic) {
		this.otherStatistic = otherStatistic;
	}

	public BondNotificationStatistic getPriceIdxStatistic() {
		return priceIdxStatistic;
	}

	public void setPriceIdxStatistic(BondNotificationStatistic priceIdxStatistic) {
		this.priceIdxStatistic = priceIdxStatistic;
	}

	public BondNotificationStatistic getFinaIdxStatistic() {
		return finaIdxStatistic;
	}

	public void setFinaIdxStatistic(BondNotificationStatistic finaIdxStatistic) {
		this.finaIdxStatistic = finaIdxStatistic;
	}
}
