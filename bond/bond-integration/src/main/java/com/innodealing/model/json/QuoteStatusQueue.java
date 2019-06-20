package com.innodealing.model.json;

import java.util.List;

public class QuoteStatusQueue {

	private String type;
	private List<QuoteStatus> items;

	public List<QuoteStatus> getItems() {
		return items;
	}

	public void setItems(List<QuoteStatus> items) {
		this.items = items;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
