package com.innodealing.model.socketio;

import com.innodealing.json.socketio.BaseSocketIoMsg;
import com.innodealing.json.socketio.SendToType;

public class ToRoomsSocketIoMsg extends BaseSocketIoMsg {

	private String[] rooms;

	public String[] getRooms() {
		return rooms;
	}

	public void setRooms(String... rooms) {
		this.rooms = rooms;
	}

	@Override
	public SendToType getSendToType() {
		return SendToType.ROOMS;
	}

	@Override
	public Object toJsonMessage() {
		JsonClass jsonClass = new JsonClass();
		jsonClass.setMessageId(this.getMessageId());
		jsonClass.setMessageType(this.getMessageType());
		jsonClass.setNamespaces(this.getNamespaces());
		jsonClass.setSendToType(this.getSendToType().text);
		jsonClass.setRooms(this.getRooms());
		jsonClass.setData(this.getData());
		return jsonClass;
	}

	private class JsonClass {
		private String messageId;
		private String messageType;
		private String[] namespaces;
		private String sendToType;
		private String[] rooms;
		private Object data;

		public String getMessageType() {
			return messageType;
		}

		public void setMessageType(String messageType) {
			this.messageType = messageType;
		}

		public String getMessageId() {
			return messageId;
		}

		public void setMessageId(String messageId) {
			this.messageId = messageId;
		}

		public String[] getNamespaces() {
			return namespaces;
		}

		public void setNamespaces(String[] namespaces) {
			this.namespaces = namespaces;
		}

		public String getSendToType() {
			return sendToType;
		}

		public void setSendToType(String sendToType) {
			this.sendToType = sendToType;
		}

		public String[] getRooms() {
			return rooms;
		}

		public void setRooms(String[] rooms) {
			this.rooms = rooms;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}
	}

}
