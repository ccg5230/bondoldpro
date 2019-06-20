package com.innodealing.model.mongo.dm;

import java.io.Serializable;


import io.swagger.annotations.ApiModelProperty;

public class BondSentiment implements Serializable {


	@ApiModelProperty(value = "舆论正面总数")
	private Integer sentimentPositive = 0;

	@ApiModelProperty(value = "舆论负面总数")
	private Integer sentimentNegative = 0;

	@ApiModelProperty(value = "舆论中性总数")
	private Integer sentimentNeutral = 0;
	
    @ApiModelProperty(value = "最近一个月舆情总数")
    private Integer sentimentMonthCount=0;

	public Integer getSentimentPositive() {
		return sentimentPositive;
	}

	public void setSentimentPositive(Integer sentimentPositive) {
		this.sentimentPositive = sentimentPositive;
	}

	public Integer getSentimentNegative() {
		return sentimentNegative;
	}

	public void setSentimentNegative(Integer sentimentNegative) {
		this.sentimentNegative = sentimentNegative;
	}

	public Integer getSentimentNeutral() {
		return sentimentNeutral;
	}

	public void setSentimentNeutral(Integer sentimentNeutral) {
		this.sentimentNeutral = sentimentNeutral;
	}
	
	public Integer getSentimentMonthCount() {
		return sentimentMonthCount;
	}

	public void setSentimentMonthCount(Integer sentimentMonthCount) {
		this.sentimentMonthCount = sentimentMonthCount;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BondSentiment [" + (sentimentPositive != null ? "sentimentPositive=" + sentimentPositive + ", " : "")
				+ (sentimentNegative != null ? "sentimentNegative=" + sentimentNegative + ", " : "")
				+ (sentimentNeutral != null ? "sentimentNeutral=" + sentimentNeutral : "") + "]";
	}

	

}
