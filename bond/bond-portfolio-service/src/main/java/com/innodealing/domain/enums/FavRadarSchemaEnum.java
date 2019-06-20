package com.innodealing.domain.enums;

public enum FavRadarSchemaEnum {
	
	YEAR_PAY(7, "付息日"), 
	THEO_PAY(8, "到期日"),
	EXER_PAY(9, "行权日"), 
	EXER_DECL(21, "行权公告日期"), 
	EXER_APPL_START(22, "行权申报起始日"), 
	EXER_APPL_END(23, "行权申报截止日"), 

	ISSU_RAT(10, "主体评级/主体展望"),
	CRED_RAT(11, "债项评级/债项展望"),
	ISSPD_RAT(12, "主体量化风险等级"),
	IMPL_RAT(13, "隐含评级"),
	
	ANOUNCE_SENS(14, "公告"),
	BIZCHG_SENS(15, "工商变更"),
	NEWS_SENS(16, "新闻预警"),
	COMLGN_SENS(17, "公司诉讼"),
	
	FIN_DISCLOSURE(18, "披露财报"),
	UNCOLLECTED_EARNINGRPT(19, "规定时间未收集到财报");
	
	private final int code;
	private final String value;

	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	private FavRadarSchemaEnum(int code, String value) {
		this.code = code;
		this.value = value;
	}

}
