package com.innodealing.domain.enums;

/**
 * @author feng.ma
 * @date 2017年5月10日 下午2:33:07
 * @describe
 */
public enum BondFavPriceIdxEnum {

	CURR_RATE_IDX(1, "成交价"), 
	OFR_QUOTE_IDX(2, "卖出报价"), 
	BID_QUOTE_IDX(3, "买入报价"), 
	ASMT_VALU_IDX(4, "估值");
	
	private final int code;
	private final String value;
	
	public int getCode() {
		return code;
	}
	public String getValue() {
		return value;
	}
	private BondFavPriceIdxEnum(int code, String value) {
		this.code = code;
		this.value = value;
	}
	
}
