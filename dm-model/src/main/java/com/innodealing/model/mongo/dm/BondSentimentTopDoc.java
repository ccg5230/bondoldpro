package com.innodealing.model.mongo.dm;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
@Document(collection = "bond_sentiment_top")
public class BondSentimentTopDoc implements Serializable {

	@ApiModelProperty(value = "简称")
	private String comChiName;

	@ApiModelProperty(value = "代码")
	private String comUniCode;

	@ApiModelProperty(value = "舆论正面总数")
	private Integer sentimentPositive;

	@ApiModelProperty(value = "舆论负面总数")
	private Integer sentimentNegative;

	@ApiModelProperty(value = "舆论中性总数")
	private Integer sentimentNeutral;

	@ApiModelProperty(value = "DM-行业唯一编号-GICS")
	private Long induId;

	@ApiModelProperty(value = "DM-行业-GICS")
	private String induName;

	@ApiModelProperty(value = "DM-行业唯一编号-SW")
	private Long induIdSw;

	@ApiModelProperty(value = "DM-行业-SW")
	private String induNameSw;
	
	@ApiModelProperty(value = "流通中债劵数")
	private Long effectiveBondCount;

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

	public Long getInduId() {
		return induId;
	}

	public void setInduId(Long induId) {
		this.induId = induId;
	}

	public String getInduName() {
		return induName;
	}

	public void setInduName(String induName) {
		this.induName = induName;
	}

	public Long getInduIdSw() {
		return induIdSw;
	}

	public void setInduIdSw(Long induIdSw) {
		this.induIdSw = induIdSw;
	}

	public String getInduNameSw() {
		return induNameSw;
	}

	public void setInduNameSw(String induNameSw) {
		this.induNameSw = induNameSw;
	}

	public Long getEffectiveBondCount() {
		return effectiveBondCount;
	}

	public void setEffectiveBondCount(Long effectiveBondCount) {
		this.effectiveBondCount = effectiveBondCount;
	}
	

}
