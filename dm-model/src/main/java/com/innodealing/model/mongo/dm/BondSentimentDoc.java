package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
@Document(collection="bond_sentiment")
public class BondSentimentDoc implements Serializable {
    
    @ApiModelProperty(value = "债券缩写")
    private String comChiName;

    @ApiModelProperty(value = "债券代码")
    private String comUniCode;
    
    @ApiModelProperty(value = "序列号")
    private String serialNo;

    @ApiModelProperty(value = "文章标题")
    private String title;
    
    @ApiModelProperty(value = "情感倾向")
    private String sentiment;
    
    @ApiModelProperty(value = "文章摘要")
    private String summary;
    
    @ApiModelProperty(value = "文章内容")
    private String text;
    
    @ApiModelProperty(value = "网站名称")
    private String siteName;
    
    @ApiModelProperty(value = "发行时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date pubDate;
    
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date collectionDate;
    
    @ApiModelProperty(value = "网页链接")
    private String url;
    
    @ApiModelProperty(value = "一级主题")
    private String topic1;
    
    @ApiModelProperty(value = "二级主题")
    private String topic2;
    
    @ApiModelProperty(value = "规则编号")
    private String ruleNo;
    
    @ApiModelProperty(value = "规则名称")
    private String ruleName;
    
    @ApiModelProperty(value = "预警级别")
    private String warningLevel;
    
    @ApiModelProperty(value = "规则描述")
    private String ruleDesc;
    
    @Indexed
    @ApiModelProperty(value = "自增ID")
    private Long index;

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

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public Date getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopic1() {
		return topic1;
	}

	public void setTopic1(String topic1) {
		this.topic1 = topic1;
	}

	public String getTopic2() {
		return topic2;
	}

	public void setTopic2(String topic2) {
		this.topic2 = topic2;
	}

	public String getRuleNo() {
		return ruleNo;
	}

	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getWarningLevel() {
		return warningLevel;
	}

	public void setWarningLevel(String warningLevel) {
		this.warningLevel = warningLevel;
	}

	public String getRuleDesc() {
		return ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "BondSentimentDistinctDoc [comChiName=" + comChiName + ", comUniCode=" + comUniCode + ", title=" + title
				+ ", sentiment=" + sentiment + "]";
	}
    
    
    
}
