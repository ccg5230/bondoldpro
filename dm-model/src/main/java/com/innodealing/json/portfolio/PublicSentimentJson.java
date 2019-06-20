package com.innodealing.json.portfolio;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/** 
* @author feng.ma
* @describe 舆情消息数据
*/
public class PublicSentimentJson implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(name="发行人ID")
    private Long comUniCode;
	
	@ApiModelProperty(value = "情感标签: 0-默认，1-正面;2-负面;3-中性")
	private Integer emotionTag;
	
	@ApiModelProperty(value = "舆情类型，1 公告，2 公司诉讼， 3 工商变更，4 新闻预警")
	private Integer sentiType;

	@ApiModelProperty(value = "舆情内容标题")
	private String msgTitle;
	
	@ApiModelProperty(value = "舆情编号，index")
	private Long sentisIndex;
	
	@ApiModelProperty(value = "舆情是否是重要新闻,0 否 1 是")
	private Integer important;

	public Long getComUniCode() {
		return comUniCode;
	}

	public void setComUniCode(Long comUniCode) {
		this.comUniCode = comUniCode;
	}

	public Integer getEmotionTag() {
		return emotionTag;
	}

	public void setEmotionTag(Integer emotionTag) {
		this.emotionTag = emotionTag;
	}

	public Integer getSentiType() {
		return sentiType;
	}

	public void setSentiType(Integer sentiType) {
		this.sentiType = sentiType;
	}

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public Long getSentisIndex() {
		return sentisIndex;
	}

	public void setSentisIndex(Long sentisIndex) {
		this.sentisIndex = sentisIndex;
	}

	public Integer getImportant() {
		return important;
	}

	public void setImportant(Integer important) {
		this.important = important;
	}

	public PublicSentimentJson() {
	}
	
}
