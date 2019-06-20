package com.innodealing.json.portfolio;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

/** 
* @author feng.ma
* @date 2017年7月27日 上午11:26:48 
* @describe 
*/
public class SentimentBulletinJson implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(name="发行人ID")
    private String comUniCode;
	
	@ApiModelProperty(value = "情感标签: 0-默认，1-负面;2-中性;3-正面")
	private Integer emotionTag;
	
	@ApiModelProperty(value = "舆情类型，1 公告，2 新闻")
	private Integer sentiType;

	@ApiModelProperty(value = "舆情内容标题")
	private String msgTitle;
	
	@ApiModelProperty(value = "舆情编号，id")
	private Integer id;
	
	@ApiModelProperty(value = "舆情发布日期")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date pubDate;

	public String getComUniCode() {
		return comUniCode;
	}

	public void setComUniCode(String comUniCode) {
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	
}
