package com.innodealing.model.dm.bond;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "t_bond_notification_msg")
public class BondNotificationMsg implements Serializable {

	public BondNotificationMsg() {
		super();
	}

	public BondNotificationMsg(Long bondId, Integer eventType, String msgContent, Date createTime, Long newsIndex,
			Integer important, Long groupId, Integer emotionTag,Integer isGrouprepeatmsg) {
		super();
		this.bondId = bondId;
		this.eventType = eventType;
		this.msgContent = msgContent;
		this.createTime = createTime;
		this.newsIndex = newsIndex;
		this.important = important;
		this.groupId = groupId;
		this.emotionTag = emotionTag;
		this.isGrouprepeatmsg = isGrouprepeatmsg;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 
	 */
	@Column(nullable = false, name = "bond_id", length = 19)
	private Long bondId;

	/**
	 * 1 存续变动 2 风险变动评级 3 风险变动财务预警 4 风险变动主题量化风险等级 5 舆情 6隐含评级 7报价检测
	 * 
	 */
	@Column(nullable = false, name = "event_type", length = 3)
	private Integer eventType;

	/**
	 * 消息内容
	 */
	@Column(name = "msg_content", length = 1024)
	private String msgContent;

	/**
	 * 创建时间
	 */
	@Column(nullable = false, name = "create_time", length = 19)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	/**
	 * 舆情的index
	 */
	@Column(nullable = false, name = "news_index", length = 19)
	private Long newsIndex;

	/**
	 * 舆情是否是重要新闻,0.否 1 是
	 */
	@Column(nullable = false, name = "important", length = 4)
	private Integer important;

	/**
	 * DM用户id
	 */
	@Column(nullable = false, name = "group_id", length = 19)
	private Long groupId;

	/**
	 * 情感标签,1-正面;2-负面;3-中性
	 */
	@Column(nullable = true, name = "emotion_tag", length = 4)
	private Integer emotionTag;

	
	/**
	 * 
	 */
	@Column(nullable = false, name = "isgrouprepeatmsg", length = 3)
	private Integer isGrouprepeatmsg = 0;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param bondId
	 *            the bondId to set
	 */
	public void setBondId(Long bondId) {
		this.bondId = bondId;
	}

	/**
	 * @return the bondId
	 */
	public Long getBondId() {
		return this.bondId;
	}

	/**
	 * @param eventType
	 *            the eventType to set
	 */
	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}

	/**
	 * @return the eventType
	 */
	public Integer getEventType() {
		return this.eventType;
	}

	/**
	 * @param msgContent
	 *            the msgContent to set
	 */
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	/**
	 * @return the msgContent
	 */
	public String getMsgContent() {
		return this.msgContent;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return this.createTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BondNotificationMsg [id=" + id + ", bondId=" + bondId + ", eventType=" + eventType + ", msgContent="
				+ msgContent + ", createTime=" + createTime + ", newsIndex=" + newsIndex + ", important=" + important
				+ ", groupId=" + groupId + ", emotionTag=" + emotionTag + "]";
	}
	
	/**
	 * @return the newsIndex
	 */
	public Long getNewsIndex() {
		return newsIndex;
	}

	/**
	 * @param newsIndex
	 *            the newsIndex to set
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
	 * @param important
	 *            the important to set
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

	public Integer getIsGrouprepeatmsg() {
		return isGrouprepeatmsg;
	}

	public void setIsGrouprepeatmsg(Integer isGrouprepeatmsg) {
		this.isGrouprepeatmsg = isGrouprepeatmsg;
	}
}
