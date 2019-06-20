package com.innodealing.domain.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaochao
 * @time 2017年5月10日
 * @description:
 */
@JsonInclude(Include.NON_NULL)
public class BondSimpleNotiMsgVO implements Serializable {
	private static final long serialVersionUID = -3311402107675389837L;

	@ApiModelProperty(value = "msgId")
	private Long msgId;

	@ApiModelProperty(value = "bondId")
	private Long bondId;

	@ApiModelProperty(value = "bondCode")
	private String bondCode;

	@ApiModelProperty(value = "bondName")
	private String bondName;

	@ApiModelProperty(value = "消息内容")
	private String msgContent;

	@ApiModelProperty(value = "雷达类型id")
	private Long radarTypeId;

	@ApiModelProperty(value = "雷达类型名称")
	private String radarTypeName;

	@ApiModelProperty(value = "舆情编号")
	private Long newsIndex;

	@ApiModelProperty(value = "舆情是否是重要新闻,0.否 1 是")
	private Integer important;

	@ApiModelProperty(value = "情感标签:1-正面;2-负面;3-中性")
	private Integer emotionTag;

	@ApiModelProperty(value = "创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
	private Date createTime;

	@ApiModelProperty(value = "提醒消息是否已读: 0-未读;1-已读")
	private Integer readStatus;

	@ApiModelProperty(value = "舆情查看标记")
	private Boolean sentimentRead;

//	public BondSimpleNotiMsgVO(Long msgId, Long bondId, String bondName, String msgContent, String radarTypeName,
//			Long newsIndex, Integer important, Integer emotionTag, Date createTime, Integer readStatus) {
//		this.setMsgId(msgId);
//		this.setBondId(bondId);
//		this.setBondName(bondName);
//		this.setMsgContent(msgContent);
//		this.setRadarTypeName(radarTypeName);
//		this.setNewsIndex(newsIndex);
//		this.setImportant(important);
//		this.setEmotionTag(emotionTag);
//		this.setCreateTime(createTime);
//		this.setReadStatus(readStatus);
//	}

	public Long getBondId() {
		return bondId;
	}

	public void setBondId(Long bondId) {
		this.bondId = bondId;
	}

	public String getBondName() {
		return bondName;
	}

	public void setBondName(String bondName) {
		this.bondName = bondName;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getRadarTypeName() {
		return radarTypeName;
	}

	public void setRadarTypeName(String radarTypeName) {
		this.radarTypeName = radarTypeName;
	}

	public Integer getEmotionTag() {
		return emotionTag;
	}

	public void setEmotionTag(Integer emotionTag) {
		this.emotionTag = emotionTag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "BondSimpleNotiMsgVO [msgId=" + msgId + ", bondId=" + bondId + ", bondName=" + bondName + ", msgContent="
				+ msgContent + ", radarTypeName=" + radarTypeName + ", emotionTag=" + emotionTag + ", readStatus="
				+ readStatus + ",  createTime=" + createTime + "]";
	}

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public Long getNewsIndex() {
		return newsIndex;
	}

	public void setNewsIndex(Long newsIndex) {
		this.newsIndex = newsIndex;
	}

	public Integer getImportant() {
		return important;
	}

	public void setImportant(Integer important) {
		this.important = important;
	}

	public Integer getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(Integer readStatus) {
		this.readStatus = readStatus;
	}

	public Long getRadarTypeId() {
		return radarTypeId;
	}

	public void setRadarTypeId(Long radarTypeId) {
		this.radarTypeId = radarTypeId;
	}

	public String getBondCode() {
		return bondCode;
	}

	public void setBondCode(String bondCode) {
		this.bondCode = bondCode;
	}

	public Boolean getSentimentRead() {
		return sentimentRead;
	}

	public void setSentimentRead(Boolean sentimentRead) {
		this.sentimentRead = sentimentRead;
	}
}
