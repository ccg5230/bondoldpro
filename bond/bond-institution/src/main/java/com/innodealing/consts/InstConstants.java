package com.innodealing.consts;

public class InstConstants {

	// 1 内部评级 2 投资建议
	public static final Integer INST_CODE_TYPE_RATING = 1;
	public static final Integer INST_CODE_TYPE_INVADC = 2;

	//信评分组，分组类型
	public static final Integer CREDITRATING_GROUPTYPE0 = 0;
	public static final Integer CREDITRATING_GROUPTYPE1 = 1;
	public static final Integer CREDITRATING_GROUPTYPE9 = 9;
	
	// 信评状态 1待信评，2待审核，3已完成
	public static final Integer INST_RATING_HIST_STATUS_1 = 1;
	public static final Integer INST_RATING_HIST_STATUS_2 = 2;
	public static final Integer INST_RATING_HIST_STATUS_3 = 3;

	public static final String NOTJOIN_GROUP = "暂不加入分组";

	//导入信评池未识别的提示分类
	public static final String REMINDERHINT_BOND_EXPIRED = "债券已过期";
	public static final String REMINDERHINT_BONDCODE_EMPTY = "债券代码为空";
	public static final String REMINDERHINT_BONDCODE_NOEXSIT = "债券代码不匹配";
	public static final String REMINDERHINT_ISSUER_EMPTY = "发行人名称为空";
	public static final String REMINDERHINT_ISSUER_NOEXSIT = "发行人名称不匹配";
	public static final String REMINDERHINT_RATING_EMPTY = "内部评级为空";
	public static final String REMINDERHINT_RATING_NOEXSIT = "内部评级不存在";
	public static final String REMINDERHINT_INVADV_NOEXSIT = "投资建议不存在";
	public static final String REMINDERHINT_BONDCODEISSUER_EMPTY = "债券代码/发行人名称为空";
	
	// 信评池中导出Excel的headers
	public static final String CREDITRATING_HEADER_0 = "发行人名称";
	public static final String CREDITRATING_HEADER_1 = "所属行业";
	public static final String CREDITRATING_HEADER_2 = "当前内部评级";
	public static final String CREDITRATING_HEADER_3 = "发行人分析";
	public static final String CREDITRATING_HEADER_4 = "DM量化评分";
	public static final String CREDITRATING_HEADER_5 = "外部评级";
	public static final String CREDITRATING_HEADER_6 = "主体展望";
	public static final String CREDITRATING_HEADER_7 = "所属信评分组";

	public static final String[] CREDITRATING_ROWSNAME = { CREDITRATING_HEADER_0, CREDITRATING_HEADER_1,
			CREDITRATING_HEADER_2, CREDITRATING_HEADER_3, CREDITRATING_HEADER_4, CREDITRATING_HEADER_5,
			CREDITRATING_HEADER_6, CREDITRATING_HEADER_7 };

	// 信评池中导出Excel的债券的headers
	public static final String CREDITRATING_BONDHEADER_0 = "债券名称";
	public static final String CREDITRATING_BONDHEADER_1 = "债券代码";
	public static final String CREDITRATING_BONDHEADER_2 = "当前投资建议";
	public static final String CREDITRATING_BONDHEADER_3 = "个券分析";
	public static final String CREDITRATING_BONDHEADER_4 = "隐含评级";
	public static final String CREDITRATING_BONDHEADER_5 = "发行人名称";
	public static final String CREDITRATING_BONDHEADER_6 = "所属行业";
	public static final String CREDITRATING_BONDHEADER_7 = "当前内部评级";
	public static final String CREDITRATING_BONDHEADER_8 = "发行人分析";
	public static final String CREDITRATING_BONDHEADER_9 = "DM量化评分";
	public static final String CREDITRATING_BONDHEADER_10 = "主/债";
	public static final String CREDITRATING_BONDHEADER_11 = "主体展望";

	public static final String[] CREDITRATING_BOND_ROWSNAME = { CREDITRATING_BONDHEADER_0, CREDITRATING_BONDHEADER_1,
			CREDITRATING_BONDHEADER_2, CREDITRATING_BONDHEADER_3, CREDITRATING_BONDHEADER_4, CREDITRATING_BONDHEADER_5,
			CREDITRATING_BONDHEADER_6, CREDITRATING_BONDHEADER_7, CREDITRATING_BONDHEADER_8, CREDITRATING_BONDHEADER_9,
			CREDITRATING_BONDHEADER_10, CREDITRATING_BONDHEADER_11 };

	// 内部评级
	public static final String INSTRATING_INSTID = "instRatingMap.instId";
	public static final String INSTRATING_RATEID = "instRatingMap.code";
	public static final String INSTRATING_RATENAME = "instRatingMap.name";
	public static final String INSTRATING_TEXTFLAG = "instRatingMap.textFlag";

	// 投资建议
	public static final String INSTINVESTMENTADVICE_INSTID = "instInvestmentAdviceMap.instId";
	public static final String INSTINVESTMENTADVICE_INVADVID = "instInvestmentAdviceMap.code";
	public static final String INSTINVESTMENTADVICE_INVADVNAME = "instInvestmentAdviceMap.name";
	public static final String INSTINVESTMENTADVICE_TEXTFLAG = "instInvestmentAdviceMap.textFlag";
	
	//临时文件路径
	public static final String MULTIPART_TMPPATH = "/data/tmp";
}
