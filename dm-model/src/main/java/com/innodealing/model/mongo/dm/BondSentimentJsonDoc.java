package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

public class BondSentimentJsonDoc implements Serializable,Comparable{

	@ApiModelProperty(value = "主键")
	private Map<String, Object> _id;

	@ApiModelProperty(value = "舆情正面总数")
	private Integer sentimentPositive=0;

	@ApiModelProperty(value = "舆情负面总数")
	private Integer sentimentNegative=0;

	@ApiModelProperty(value = "舆情中性总数")
	private Integer sentimentNeutral=0;

	@ApiModelProperty(value = "债券缩写")
	private String comChiName;

	@ApiModelProperty(value = "债券代码")
	private String comUniCode;
	
	@ApiModelProperty(value = "每周第一天")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date weekDate;
	
	@ApiModelProperty(value = "舆情总数")
	private Integer sentimentCount=0;

	public Map<String, Object> get_id() {
		return _id;
	}

	public void set_id(Map<String, Object> _id) {
		this._id = _id;
	}

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

	public String getComChiName() {
		return comChiName;
	}

	public void setComChiName(String comChiName) {
		this.comChiName = comChiName;
	}

	public String getComUniCode() {
		return comUniCode;
	}

	public void setComUniCode(String comUniCode) {
		this.comUniCode = comUniCode;
	}

	public Date getWeekDate() {
		return weekDate;
	}

	public void setWeekDate(Date weekDate) {
		this.weekDate = weekDate;
	}

	public Integer getSentimentCount() {
		return sentimentCount;
	}

	public void setSentimentCount(Integer sentimentCount) {
		this.sentimentCount = sentimentCount;
	}
	
	public int compareTo(Object o) {
		BondSentimentJsonDoc tmp=(BondSentimentJsonDoc) o;
		return tmp.sentimentCount>sentimentCount?1:(tmp.sentimentCount==sentimentCount?0:-1);
	}

}
