package com.innodealing.model.mongo.dm;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(value=Include.NON_NULL)
@Document(collection="issuer_notification_msg")
public class IssuerNotificationMsgDoc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@ApiModelProperty(value = "ID")
	private Long id;

	/**
	 * 发行人的Uni Code
	 */
	@ApiModelProperty(value = "发行人的Uni Cod")
	private Long issuerId;

	/**
	 * 1 违约概率 2 存续管理 3 评级 4 舆情 5 主体
	 */
	@ApiModelProperty(value = "提醒消息分类")
	private Integer eventType;

	/**
	 * 消息内容
	 */
	@ApiModelProperty(value = "消息内容")
	private String msgContent;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间，string格式")
	private String createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(Long issuerId) {
		this.issuerId = issuerId;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
