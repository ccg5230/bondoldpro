package com.innodealing.domain.enums;

/**
 * @author feng.ma
 * @date 2017年5月17日 上午11:22:42
 * @describe
 */
public enum EmotionTagEnum {

	DEFAULT(0, "默认"), GOOD(1, "利好"), RISK(2, "风险"), NEUTRAL(3, "中性");

	private final int code;
	private final String value;

	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	private EmotionTagEnum(int code, String value) {
		this.code = code;
		this.value = value;
	}

}
