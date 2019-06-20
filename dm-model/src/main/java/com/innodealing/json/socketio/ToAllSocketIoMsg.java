package com.innodealing.json.socketio;

public class ToAllSocketIoMsg extends BaseSocketIoMsg {

	private String[] members;

	private String sendToType = this.getSendToType().getText();

	public String[] getMembers() {
		return members;
	}

	public void setMembers(String... members) {
		this.members = members;
	}

	@Override
	public SendToType getSendToType() {
		return SendToType.ALL;
	}

	@Override
	public Object toJsonMessage() {
		JsonClass jsonClass = new JsonClass();
		jsonClass.setMessageId(this.getMessageId());
		jsonClass.setMessageType(this.getMessageType());
		jsonClass.setNamespaces(this.getNamespaces());
		jsonClass.setSendToType(this.getSendToType().getText());
		jsonClass.setMembers(this.getMembers());
		jsonClass.setData(this.getData());
		return jsonClass;
	}

	private class JsonClass {
		private String messageId;
		private String messageType;
		private String[] namespaces;
		private String sendToType;
		private String[] members;
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

		public String[] getMembers() {
			return members;
		}

		public void setMembers(String[] members) {
			this.members = members;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}
	}
	
}
