package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
@Document(collection = "bond_bulletin")
public class BondBulletinDoc implements Serializable {
	private static final long serialVersionUID = -1716574173300899390L;

	@Id
	@ApiModelProperty(value = "编号")
	private Integer id;

	@ApiModelProperty(value = "标题")
	private String topic;

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "跳转类型")
	private Integer skipType;

	@ApiModelProperty(value = "跳转url")
	private String skipUrl;

	@ApiModelProperty(value = "链接url")
	private String siteUrl;

	@ApiModelProperty(value = "发行人id")
	private Integer comUniCode;

	@ApiModelProperty(value = "发行人名称")
	private String comChiName;

	@ApiModelProperty(value = "跳转json")
	private String skipJson;

	@ApiModelProperty(value = "来源是爬虫")
	private Integer fromSpider;

	@ApiModelProperty(value = "状态:0-未发布;1-已发布;2-已删除;")
	private Integer status;

	@ApiModelProperty(value = "雷区颜值:0-不标红(默认);1-标红;")
	private Integer colorState;

	@ApiModelProperty(value = "图片url")
	private String imgUrl;

	@ApiModelProperty(value = "概述")
	private String outlines;

	@ApiModelProperty(value = "情感类别:0-空(默认);1-负面;2-中性;3-利好;")
	private Integer sentiment;

	@ApiModelProperty(value = "风险等级:0-空(默认);1-高;2-中;3-低;")
	private Integer riskLevel;

	@ApiModelProperty(value = "标签名称s")
	private String tags;

	@ApiModelProperty(value = "标签编号s")
	private String tagIds;

	@ApiModelProperty(value = "债券编号s")
	private String bondIds;

	@ApiModelProperty(value = "债券名称s")
	private String bondNames;

	@ApiModelProperty(value = "今日动态类别:1-发行、流通、付息与兑付;2-违约;3-评级跟踪;4-其他公告;")
	private Integer classification;

	@ApiModelProperty(value = "显示类型:1-雷区;2-今日动态;3-Both;")
	private Integer display;

	@ApiModelProperty(value = "编辑人")
	private String editUser;

	@ApiModelProperty(value = "发布日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date pubDate;

	@ApiModelProperty(value = "插入时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date insertTime;

	@ApiModelProperty(value = "编辑时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date editTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOutlines() {
		return outlines;
	}

	public void setOutlines(String outlines) {
		this.outlines = outlines;
	}

	public Integer getSentiment() {
		return sentiment;
	}

	public void setSentiment(Integer sentiment) {
		this.sentiment = sentiment;
	}

	public Integer getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(Integer riskLevel) {
		this.riskLevel = riskLevel;
	}

	public Integer getClassification() {
		return classification;
	}

	public void setClassification(Integer classification) {
		this.classification = classification;
	}

	public Integer getDisplay() {
		return display;
	}

	public void setDisplay(Integer display) {
		this.display = display;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getColorState() {

		return colorState;
	}

	public void setColorState(Integer colorState) {
		this.colorState = colorState;
	}

	public Integer getSkipType() {
		return skipType;
	}

	public void setSkipType(Integer skipType) {
		this.skipType = skipType;
	}

	public String getSkipUrl() {
		return skipUrl;
	}

	public void setSkipUrl(String skipUrl) {
		this.skipUrl = skipUrl;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	public Integer getComUniCode() {
		return comUniCode;
	}

	public void setComUniCode(Integer comUniCode) {
		this.comUniCode = comUniCode;
	}

	public String getComChiName() {
		return comChiName;
	}

	public void setComChiName(String comChiName) {
		this.comChiName = comChiName;
	}

	public String getSkipJson() {
		return skipJson;
	}

	public void setSkipJson(String skipJson) {
		this.skipJson = skipJson;
	}

	public Integer getFromSpider() {
		return fromSpider;
	}

	public void setFromSpider(Integer fromSpider) {
		this.fromSpider = fromSpider;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getTagIds() {
		return tagIds;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}

	public String getBondIds() {
		return bondIds;
	}

	public void setBondIds(String bondIds) {
		this.bondIds = bondIds;
	}

	public String getBondNames() {
		return bondNames;
	}

	public void setBondNames(String bondNames) {
		this.bondNames = bondNames;
	}

	public String getEditUser() {
		return editUser;
	}

	public void setEditUser(String editUser) {
		this.editUser = editUser;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) throws Exception {
		this.pubDate = pubDate;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	@Override
	public String toString() {
		return "BondBulletinDoc [id=" + id + ", topic=" + topic + ", title=" + title + ", skipType=" + skipType
				+ ", skipUrl=" + skipUrl + ", siteUrl=" + siteUrl + ", comUniCode=" + comUniCode + ", comChiName="
				+ comChiName + ", skipJson=" + skipJson + ", fromSpider=" + fromSpider + ", status=" + status
				+ ", colorState=" + colorState + ", imgUrl=" + imgUrl + ", outlines=" + outlines + ", sentiment="
				+ sentiment + ", riskLevel=" + riskLevel + ", tags=" + tags + ", tagIds=" + tagIds + ", bondIds="
				+ bondIds + ", bondNames=" + bondNames + ", classification=" + classification + ", display=" + display
				+ ", editUser=" + editUser + ", pubDate=" + pubDate + ", insertTime=" + insertTime + ", editTime="
				+ editTime + "]";
	}
}
