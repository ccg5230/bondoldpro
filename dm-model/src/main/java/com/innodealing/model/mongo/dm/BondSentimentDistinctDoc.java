package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
@Document(collection = "bond_sentiment_distinct")
@CompoundIndex(name = "important_pubDate_1", def = "{'comUniCode' : 1, 'title' : 1,'pubDate' : -1,'important' : -1}")
public class BondSentimentDistinctDoc implements Serializable {

	@Id
	@ApiModelProperty(value = "id")
	private String id;

	@ApiModelProperty(value = "债券缩写")
	private String comChiName;

	@ApiModelProperty(value = "债券代码")
	private String comUniCode;

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "情感倾向")
	private String sentiment;

	@ApiModelProperty(value = "规则名称")
	private String ruleName;

	@ApiModelProperty(value = "发行日期")
	@Indexed
	private Date pubDate;

	@ApiModelProperty(value = "网页链接")
	private String url;

	@ApiModelProperty(value = "自增ID")
	@Indexed
	private Long index;

	@ApiModelProperty(value = "序列")
	private String serialNo;

	@ApiModelProperty(value = "是否是重要新闻,0.否 1 是")
	private Integer important;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSentiment() {
		return sentiment;
	}

	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}

	public String getRuleName() {
		if (StringUtils.isEmpty(ruleName)) {
			return null;
		}
		String RuleName = ruleName.lastIndexOf(",") == ruleName.length() - 1
				? ruleName.substring(0, ruleName.length() - 1) : ruleName;
		return RuleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Integer getImportant() {
		return important;
	}

	public void setImportant(Integer important) {
		this.important = important;
	}

	@Override
	public String toString() {
		return "BondSentimentDistinctDoc [comUniCode=" + comUniCode + ", title=" + title
				+ ", sentiment=" + sentiment + "]";
	}

	public String merge() {
		return "BondSentimentDistinctDoc [title=" + title + ", comUniCode=" + comUniCode + "]";
	}

}
