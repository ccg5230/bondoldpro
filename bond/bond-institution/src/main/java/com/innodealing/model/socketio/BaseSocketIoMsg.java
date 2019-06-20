package com.innodealing.model.socketio;

import java.util.UUID;

import com.innodealing.json.socketio.SendToType;

public abstract class BaseSocketIoMsg {

	// set default messageid as uuid
	private String messageId = UUID.randomUUID().toString();
	private String messageType;
	private String[] namespaces;

	// msg data
	private Object data;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String[] getNamespaces() {
		return namespaces;
	}

	public void setNamespaces(String... namespaces) {
		this.namespaces = namespaces;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public abstract SendToType getSendToType();

	public abstract Object toJsonMessage();
}
