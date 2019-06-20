package com.innodealing.model.mongo.dm;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
@Document(collection="bond_msg_user_mapping")
public class BondMsgUserStatusDoc implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@ApiModelProperty(value = "消息id")
	private Long notifyMsgId;
	
	@ApiModelProperty(value = "用户id")
	@Indexed
    private Integer userId;
	
	@ApiModelProperty(value = "债券的id")
	@Indexed
	private Long bondId;
	
	@ApiModelProperty(value = "消息操作方式，101：4s自动消失，102 ：查看消息， 103：关闭消息弹出窗")
    private Integer operation;
	

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the bondId
	 */
	public Long getBondId() {
		return bondId;
	}

	/**
	 * @param bondId the bondId to set
	 */
	public void setBondId(Long bondId) {
		this.bondId = bondId;
	}

	/**
	 * @return the notifyMsgId
	 */
	public Long getNotifyMsgId() {
		return notifyMsgId;
	}

	/**
	 * @param notifyMsgId the notifyMsgId to set
	 */
	public void setNotifyMsgId(Long notifyMsgId) {
		this.notifyMsgId = notifyMsgId;
	}

	/**
	 * @return the operation
	 */
	public Integer getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(Integer operation) {
		this.operation = operation;
	}
	
}
