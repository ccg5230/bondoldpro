package com.innodealing.domain.enums;

public enum EventMsgTypeEnum {
	
	MATURITY(1, "存续变动"),
	BONDRATE(2, "风险变动-评级"),
	FINALERT(3, "风险变动-财务预警"),
	ISSQURAT(4, "风险变动-主体量化风险等级"),
	BONDNEWS(5, "主体舆情");
    
    private final int code;
    private final String value;
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	private EventMsgTypeEnum(int code, String value) {
		this.code = code;
		this.value = value;
	}
	
}
