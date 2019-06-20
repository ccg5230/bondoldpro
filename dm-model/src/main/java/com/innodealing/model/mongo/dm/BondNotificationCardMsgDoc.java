package com.innodealing.model.mongo.dm;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author feng.ma
 * @date 2017年8月13日 下午2:24:11
 * @describe
 */
@JsonInclude(Include.NON_NULL)
@Document(collection = "bond_notification_cardmsg")
public class BondNotificationCardMsgDoc extends BondNotificationMsgDoc {
	
	private static final long serialVersionUID = 842146911491768476L;
	
	@ApiModelProperty(value = "用户id")
	@Indexed
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
