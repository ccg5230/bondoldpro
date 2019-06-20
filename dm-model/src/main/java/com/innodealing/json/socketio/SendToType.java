package com.innodealing.json.socketio;

public enum SendToType {

	ALL(1, "all"), ROOMS(1 << 1, "rooms"), MEMBERS(1 << 2, "members");

	public final int value;
	public final String text;

	public int getValue() {
		return value;
	}

	public String getText() {
		return text;
	}

	SendToType(int value, String text) {
		this.value = value;
		this.text = text;
	}
}
