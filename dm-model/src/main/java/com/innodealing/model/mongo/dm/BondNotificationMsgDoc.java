package com.innodealing.model.mongo.dm;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
@Document(collection="bond_notification_msg")
public class BondNotificationMsgDoc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@ApiModelProperty(value = "ID")
	private Long id;

	@ApiModelProperty(value = "bondId")
	@Indexed
	private Long bondId;

	@ApiModelProperty(value = "groupId:0-公共消息;具体编号-私人消息")
	@Indexed
	private Long groupId;
	
	@ApiModelProperty(value = "情感标签:1-正面;2-负面;3-中性")
	@Indexed
	private Integer emotionTag;

	/**
	 * 1 违约概率 2 存续管理 3 评级 4 舆情 5 主体
	 * 
	 */
	@ApiModelProperty(value = "提醒消息分类")
	@Indexed
	private Integer eventType;

	/**
	 * 消息内容
	 */
	@ApiModelProperty(value = "消息内容")
	private String msgContent;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	/**
	 * 舆情的index
	 */
	@ApiModelProperty(value = "舆情编号")
	private Long newsIndex;
	
	/**
	 * 舆情的index
	 */
	@ApiModelProperty(value = "舆情是否是重要新闻,0.否 1 是")
	private Integer important;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBondId() {
		return bondId;
	}

	public void setBondId(Long bondId) {
		this.bondId = bondId;
	}

	public Integer getEventType() {
		return eventType;
	}

	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

    /**
     * @return the newsIndex
     */
    public Long getNewsIndex() {
        return newsIndex;
    }

    /**
     * @param newsIndex the newsIndex to set
     */
    public void setNewsIndex(Long newsIndex) {
        this.newsIndex = newsIndex;
    }

	/**
	 * @return the important
	 */
	public Integer getImportant() {
		return important;
	}

	/**
	 * @param important the important to set
	 */
	public void setImportant(Integer important) {
		this.important = important;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Integer getEmotionTag() {
		return emotionTag;
	}

	public void setEmotionTag(Integer emotionTag) {
		this.emotionTag = emotionTag;
	}

}
