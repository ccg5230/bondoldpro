package com.innodealing.consts;

import java.util.*;

/**
 * @author stephen.ma
 * @clasename Constants.java
 * @decription TODO
 */
public class Constants {

	public static final String QUOTEID_PREFIX = "7";

	public static final Integer ASSETSCALE_OFFSET = 2;

	// timeout 10 minutes
	public static final long TIMEOUT_SECONDS = 60 * 10;

	public static final List<Integer> VALID_DEALED_STATUS = new ArrayList<>(Arrays.asList(1, 2));

	public static final List<Integer> PUBLIC_SOURCE = new ArrayList<>(Arrays.asList(1, 2, 3));

	public static final List<Integer> PRIVATE_SOURCE = new ArrayList<>(Arrays.asList(0, 4));

	// quote status,1 正常 2 已成交 99 已取消
	public static final Integer QUOTE_STATUS_NORMAL = 1;
	public static final Integer QUOTE_STATUS_TRADED = 2;
	public static final Integer QUOTE_STATUS_CANCELED = 99;

	// 债券剩余期限的偏移量，±2M(60)以内
	public static final long BOND_TENOR_OFFSET = 60;

	/**
	 * 异常价格
	 */
	public static final Integer EVENMSG_TYPE_ABNPRICE = 4;

	/**
	 * 财务指标
	 */
	public static final Integer EVENMSG_TYPE_FININDICATOR = 5;

	/**
	 * 存续变动 ，MATURITY
	 */
	// 付息
	public static final Integer EVENMSG_TYPE_YEAR_PAY = 7;
	// 到期
	public static final Integer EVENMSG_TYPE_THEO_PAY = 8;
	// 行权
	public static final Integer EVENMSG_TYPE_EXER_PAY = 9;
	//公告日期
	public static final Integer EVENMSG_TYPE_EXER_DECL = 21;
	//行权申报起始日
	public static final Integer EVENMSG_TYPE_EXER_APPL_START = 22;
	//行权申报截止日
	public static final Integer EVENMSG_TYPE_EXER_APPL_END = 23;

	/**
	 * 风险变动-主体评级和展望
	 */
	public static final Integer EVENMSG_TYPE_ISSRATE = 10;
	/**
	 * 风险变动-债项评级和展望
	 */
	public static final Integer EVENMSG_TYPE_BONDRATE = 11;
	/**
	 * 风险变动-主体量化风险等级
	 */
	public static final Integer EVENMSG_TYPE_ISSQURAT = 12;
	/**
	 * 风险变动-隐含评级
	 */
	public static final Integer EVENMSG_TYPE_IMRATING = 13;

	/**
	 * 主体舆情
	 */
	// 公告
	public static final Integer EVENMSG_TYPE_ANNOUNCEMENT = 14;
	// 工商变更
	public static final Integer EVENMSG_TYPE_BUSINESSCHANGE = 15;
	// 新闻预警
	public static final Integer EVENMSG_TYPE_NEWSWARNING = 16;
	// 公司诉讼
	public static final Integer EVENMSG_TYPE_COMLITIGATION = 17;
	

	/**
	 * 其他
	 */
	//披露财报
	public static final Integer EVENMSG_TYPE_FINDISCLOSURE = 18;
	//规定时间未收集到财报
	public static final Integer EVENMSG_TYPE_UNCOLLECTEDRPT = 19;

	/**
	 * 报价监测
	 */
	public static final Integer EVENMSG_TYPE_QUOTEMNT = 7;

	public static final List<Integer> EVENMSG_TYPE_LISTS = new ArrayList<>(
			Arrays.asList(EVENMSG_TYPE_ABNPRICE, EVENMSG_TYPE_FININDICATOR, EVENMSG_TYPE_YEAR_PAY,
					EVENMSG_TYPE_THEO_PAY, EVENMSG_TYPE_EXER_PAY, EVENMSG_TYPE_ISSRATE, EVENMSG_TYPE_BONDRATE,
					EVENMSG_TYPE_ISSQURAT, EVENMSG_TYPE_IMRATING, EVENMSG_TYPE_ANNOUNCEMENT,
					EVENMSG_TYPE_BUSINESSCHANGE, EVENMSG_TYPE_NEWSWARNING, EVENMSG_TYPE_COMLITIGATION));

	public static final List<Integer> EVENMSG_TYPE_RISKCHANGE_LISTS = new ArrayList<>(
			Arrays.asList(EVENMSG_TYPE_ISSRATE, EVENMSG_TYPE_BONDRATE, EVENMSG_TYPE_ISSQURAT, EVENMSG_TYPE_IMRATING));

	// 行业分类类型
	public static final Integer INDU_CLASS_GICS = 1;
	public static final Integer INDU_CLASS_SW = 2;
	public static final Integer INDU_CLASS_UNDEFINED = 9;
	public static final String INDU_CLASS_REDIS_PREFIX = "indu_user_";
	public static final String INDU_CLASS_GICS_ID = "induId";
	public static final String INDU_CLASS_SW_ID = "induIdSw";

	/**
	 * 财报暂未披露
	 */
	public static final String FINACE_REPORT_UNPUBLISHED = "财报暂未披露";

	/**
	 * 重点指标缺失
	 */
	public static final String FINACE_KEY_INDICATOR_MISSING = "重点指标缺失";

	/**
	 * 模型不匹配无法计算
	 */
	public static final String FINACE_MODEL_UNMATCHED = "模型不匹配无法计算";

	/**
	 * 权重非0的指标值为NULL
	 */
	public static final String FINANCE_WEIGHT_INDICATOR_IS_NULL = "权重非0的指标值为NULL";

	public static final int TRUE = 1;
	public static final int FALSE = 0;

	// 财务指标分析-资产负债、利润表、现金流量分类
	/**
	 * 资产负债表
	 */
	public static final Integer FINANCE_INDICATOR_ZCFZ = 1;

	/**
	 * 利润表
	 */
	public static final Integer FINANCE_INDICATOR_LR = 2;

	/**
	 * 现金流量表
	 */
	public static final Integer FINANCE_INDICATOR_XJLL = 3;

	// 指标万、率、一般类型

	/**
	 * 万
	 */
	public static final int INDICATOR_PERCENT_W = 0;

	/**
	 * 率
	 */
	public static final int INDICATOR_PERCENT_L = 1;

	/**
	 * 一般
	 */
	public static final int INDICATOR_PERCENT_Y = 2;

	/**
	 * 债券报价监测，缓存去重key
	 */
	public static final String QUOTE_NOTI_REDIS_PREFIX = "quote_noti";

	/**
	 * 利率类型，需要与中诚信的pub_par一致
	 */
	public static final String RATE_TYPE_DISCOUNT_BOND = "贴现";

	public static final String QUOTEDATA_POSTFROM_KEY = "QUOTEDATA_POSTFROM_KEY_";

	public static final List<Integer> QUOTEDATA_POSTFROM_LIST = new ArrayList<>(
			Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

	/**
	 * 雷达类型：价格提醒
	 */
	public static final Integer RADAR_TYPE_PRICE = 4;

	/**
	 * 雷达类型：财务指标
	 */
	public static final Integer RADAR_TYPE_FINA = 5;
	
	//中债权限，dmms中也定义
    public static final String DM_USER_DEBTROLE = "dm_user_debtrole_";

    // 金融企业债
	public static final List<String> FINA_COMP_BOND_LIST = Arrays.asList("bank", "secu", "insu");

	// 投组雷达缓存映射
	public static final Map<Long, String> RADAR_MONGO_MAP = new HashMap<Long, String>(){{
		put(7L, "BondFavMaturityIdxDoc");
		put(8L, "BondFavMaturityIdxDoc");
		put(9L, "BondFavMaturityIdxDoc");
		put(10L, "BondFavRatingIdxDoc");
		put(11L, "BondFavRatingIdxDoc");
		put(12L, "BondFavRatingIdxDoc");
		put(13L, "BondFavRatingIdxDoc");
		put(14L, "BondFavSentimentIdxDoc");
		put(15L, "BondFavSentimentIdxDoc");
		put(16L, "BondFavSentimentIdxDoc");
		put(17L, "BondFavSentimentIdxDoc");
		put(18L, "BondFavOtherIdxDoc");
		put(19L, "BondFavOtherIdxDoc");
		put(21L, "BondFavMaturityIdxDoc");
		put(22L, "BondFavMaturityIdxDoc");
		put(23L, "BondFavMaturityIdxDoc");
	}};
	
	// mongodb中条件doc
	public static final List<String> INDEX_DOC_LISTS = new ArrayList<>(
			Arrays.asList("bond_favorite_priceidx", 
					"bond_favorite_finaidx", 
					"bond_favorite_ratingidx", 
					"bond_favorite_sentimentidx",
					"bond_favorite_maturityidx",
					"bond_favorite_otheridx"));
	
	//债券变动是否提醒，1 提醒， 0 不提醒, 针对卡片消息
	public static final int NOTIFIEDENABLE_OPENED = 1;
	public static final int NOTIFIEDENABLE_CLOSED = 0;

	// 投组中最多关注债券数量
	public static final int GROUP_FAV_MAX_COUNT = 5000;
	public static final String GROUP_FAV_OVER_MAX_ERROR_MSG = "同时关注的债券数量不能大于5000条！";

	/**
	 * 单次关注债券的条数上限（应用事务）
	 */
	public static final int BATCH_FAVORITE_LIMIT = 500;
}

