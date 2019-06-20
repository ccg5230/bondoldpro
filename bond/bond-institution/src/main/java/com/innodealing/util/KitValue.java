package com.innodealing.util;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.BasicDBList;

public class KitValue {

	/**
	 * yyyy-MM-dd
	 */
	public static final String YYYYMMDD = "yyyy-MM-dd";

	/**
	 * 情感倾向 1[风险] 2[中性] 3[利好]
	 */
	public static final Map<Integer, String> SENTIMENT_MAP = new HashMap<Integer, String>();

	/**
	 * 风险级别 1[高] 2[中] 3[低]
	 */
	public static final Map<Integer, String> RISK_LEVEL_MAP = new HashMap<Integer, String>();

	
	/**
	 * 排除 诉讼类型其他集合 [被告、原审被告、被上诉人、原告、无关第三方] 
	 */
	public static final BasicDBList BOND_LAW_STATUS_OTHER = new BasicDBList();
	
	
	{
		SENTIMENT_MAP.put(1, "风险");
		SENTIMENT_MAP.put(2, "中性");
		SENTIMENT_MAP.put(3, "利好");

		RISK_LEVEL_MAP.put(1, "高");
		RISK_LEVEL_MAP.put(2, "中");
		RISK_LEVEL_MAP.put(3, "低");
		
		BOND_LAW_STATUS_OTHER.add("被告");
		BOND_LAW_STATUS_OTHER.add("原审被告");
		BOND_LAW_STATUS_OTHER.add("被上诉人");
		BOND_LAW_STATUS_OTHER.add("原告");
		BOND_LAW_STATUS_OTHER.add("无关第三方");
	}

}
