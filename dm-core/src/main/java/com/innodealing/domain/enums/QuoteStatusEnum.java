package com.innodealing.domain.enums;

/**
 * @author stephen.ma
 * @date 2016年9月9日
 * @clasename QuoteStatusEnum.java
 * @decription TODO
 */
public enum QuoteStatusEnum {
	
	 NORMAL(1, "正常"),
	 TRADED(2, "已成交"),
	 CANCEL(99, "已取消");
	
    private final int code;
    private final String value;
    
	public int getCode() {
		return code;
	}
	public String getValue() {
		return value;
	}
	
	/**
	 * @param code
	 * @param value
	 */
	private QuoteStatusEnum(int code, String value) {
		this.code = code;
		this.value = value;
	}

}
