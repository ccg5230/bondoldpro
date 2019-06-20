package com.innodealing.uilogic;

import static java.util.Arrays.asList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innodealing.exception.BusinessException;
import com.innodealing.util.StringUtils;

public class UIAdapter {
	
	private static final Logger LOG = LoggerFactory.getLogger(UIAdapter.class);
	
	public static final String DEFAULT_TEXT = "主体已违约";
	
	private static final Map<String, Integer> credMaps = new HashMap<String, Integer>() {{
		put("AAA",1);
		put("AA+",2);
		put("AA",3);
		put("AA-",4);
		put("A+",5);
		put("A-",6);
		put("A-1",7);
	}};

	private static final Map<String, Integer> ratingMaps = new HashMap<String, Integer>() {{
		put("AAA+",1);   
		put("AAA",2);
		put("AAA-",3);
		put("AA+",4);
		put("AA",5);
		put("AA-",6);
		put("A+",7);
		put("A",8);
		put("A-",9);
		put("A-1",10);
		put("A1",11);
		put("BBB+",12);
		put("BBB",13);
		put("BBB-",14);
		put("BB+",15);
		put("BB",16);
		put("BB-",17);
		put("B+",18);
		put("B",19);
		put("B-",20);
		put("CCC",21);
		put("CC",22);
		put("C",23);
	}};

	private static final Map<Integer, String> mktMaps = new HashMap<Integer, String>() {{
		put(1, ".SZ");
		put(2, ".SH");
		put(3, ".IB");
		put(4, ".IB");
		put(5, "");
		put(6, ".SH");
	}};


	/**
	 * 违约率映射表
	 */
	private static final Map<String,BigDecimal> pdMaping = new HashMap<String,BigDecimal>(){{
		put("AAA", new BigDecimal("0.01") );
		put("AA+", new BigDecimal("0.014"));
		put("AA", new BigDecimal("0.02"));
		put("AA-", new BigDecimal("0.04"));
		put("A+", new BigDecimal("0.07"));
		put("A", new BigDecimal("0.08"));
		put("A-", new BigDecimal("0.09"));
		put("BBB+", new BigDecimal("0.16"));
		put("BBB", new BigDecimal("0.24"));
		put("BBB-", new BigDecimal("0.38"));
		put("BB+", new BigDecimal("0.55"));
		put("BB", new BigDecimal("0.81"));
		put("BB-", new BigDecimal("1.34"));
		put("B+", new BigDecimal("2.62"));
		put("B", new BigDecimal("6"));
		put("B-", new BigDecimal("9.27"));
		put("CCC+", new BigDecimal("21.3"));
		put("CCC", new BigDecimal("21.98"));
		put("CCC-", new BigDecimal("34.36")); 
		put("C", new BigDecimal("58.22"));
		put("D", new BigDecimal("100"));
	}};
	
	//评级种类
	public final static List<String> Ratings = Arrays.asList("AAA", "AA+", "AA", "AA-", "A+", "A", "A-", "BBB+", "BBB", "BBB-", "BB+",
			"BB-", "BB", "B+", "B", "B-", "CCC+");
	
	//评级与等级的对应关系
	private static Map<String, Integer> RISKMAP = new HashMap<String, Integer>(){{
		put("AAA", 1);
		put("AA+", 1);
		put("AA", 2);
		put("AA-", 2);
		put("A+", 2);
		put("A", 3);
		put("A-", 3);
		put("BBB+", 3);
		put("BBB", 3);
		put("BBB-", 4);
		put("BB+", 4);
		put("BB-", 5);
		put("BB", 5);
		put("B+", 5);
		put("B", 6);
		put("B-", 6);
		put("CCC+", 6);
		put("CCC及以下", 7);
	}};
	
	
	/**
	 * 市场动态评级等级顺序
	 * @param rating
	 * @param level
	 * @return
	 */
	public static Map<String, Object> getPriorityAndName(String rating, Integer level) {

		Map<String, Object> map = new HashMap<String, Object>();

		Integer sort = 0;
		String name = "";

		if (StringUtils.isEmpty(rating)) {
			switch (level) {
			case 1:
				sort = 1;
				name = "AAA ~ AA+";
				break;
			case 2:
				sort = 4;
				name = "AA ~ A+";
				break;
			case 3:
				sort = 8;
				name = "A ~ BBB";
				break;
			case 4:
				sort = 13;
				name = "BBB- ~ BB+";
				break;
			case 5:
				sort = 16;
				name = "BB ~ B+";
				break;
			case 6:
				sort = 20;
				name = "B ~ CCC+";
				break;
			default:
				sort = 24;
				name = "CCC ~ C";
				break;
			}
			map.put("sort", sort);
			map.put("name", name);
			return map;
		}

		switch (rating) {
		case "AAA":
			sort = 2;
			break;
		case "AA+":
			sort = 3;
			break;
		case "AA":
			sort = 5;
			break;
		case "AA-":
			sort = 6;
			break;
		case "A+":
			sort = 7;
			break;
		case "A":
			sort = 9;
			break;
		case "A-":
			sort = 10;
			break;
		case "BBB+":
			sort = 11;
			break;
		case "BBB":
			sort = 12;
			break;
		case "BBB-":
			sort = 14;
			break;
		case "BB+":
			sort = 15;
			break;
		case "BB":
			sort = 17;
			break;
		case "BB-":
			sort = 18;
			break;
		case "B+":
			sort = 19;
			break;
		case "B":
			sort = 21;
			break;
		case "B-":
			sort = 22;
			break;
		case "CCC+":
			sort = 23;
			break;
		default:
			sort = 25;
			break;
		}
		map.put("name", rating);
		map.put("sort", sort);
		return map;
	}
	
	public static final Map<String, Integer> BondQuoteSorts = new HashMap<String, Integer>() {{
		put("国债",1);
		put("央票",2);
		put("国开债",3);
		put("非国开债",4);
		put("地方债",5);
	}};
	
	public static final Map<Integer, String> BondTypeNames = new HashMap<Integer, String>() {{
		put(3,"国债");
		put(4,"央票");
		put(6,"地方债");
	}};


	static public String cvtMatuUnit2UIOpt(Integer unit)
	{
		if (unit == null) return null;

		switch (unit) {
		case 1: return "Y";
		case 2: return "M"; 
		case 3: return "D"; 
		default: return ""; 
		}	
	}

	static public Integer cvtCred2UIOpt(String credLevel)
	{
		if (credLevel == null) return null;
		if (!credMaps.containsKey(credLevel))
			return 99;

		return credMaps.get(credLevel);
	}

	static public Integer cvtRating2UIOpt(String credLevel)
	{
		if (credLevel == null) return null;
		if (!ratingMaps.containsKey(credLevel))
			return 99;

		return ratingMaps.get(credLevel);
	}

	static public  Map<String, Integer> getRatingMaps()
	{
		return ratingMaps;
	}


	static public Integer cvtComAttr2UIOpt(Integer attr)
	{
		if (attr == null) return null;

		switch (attr) {
		//中央国有企业
		case 1: return 1; //央企
		//地方国有企业
		case 2: return 2; //国企
		//集体企业
		case 3:  return 99;
		//中外合资企业
		case 4: return 99;
		//外商独资企业
		case 5:  return 99;
		//民营企业
		case 6:  return 3; //民企
		//公众企业
		case 7: return 99;
		default: return 99; 
		}
	}

	static public String cvtComAttr2UIStr(Integer attr)
	{
		if (attr == null) return "其他";
		switch (attr) {
		case 1: return "央企";  
		case 2: return "国企"; 
		case 3: return "集体企业"; 
		case 4: return "合资";
		case 5: return "外商独资";
		case 6: return "民企"; 
		case 7: return "公众企业";
		default: return "其他"; 
		}
	}

	static public String cvtSecMar2Postfix(Integer secMarket)
	{
		if (secMarket == null) return "";
		if (!mktMaps.containsKey(secMarket))
			return "";

		return mktMaps.get(secMarket);
	}

	public enum MarketOpt
	{
		E_EXCHG(1), 
		E_IB(2), 
		E_CROSS_MAR(3),
		E_PLEDGE(4);

		private Integer value;    
		MarketOpt(Integer value) {
			this.value = value;
		}
		public Integer code() {
			return value;
		}
	}

	static public Integer cvtSecMar2UIOpt(Integer secMarket)
	{
		//note: 
		// secMarket: pub_par's par_sys_code is 1011
		// isCrossMar: pub_par's par_sys_code is 1013
		if (secMarket != null) {
			switch (secMarket) {
			case 1:  //深圳证券交易所
			case 2: //上海证券交易所
				return MarketOpt.E_EXCHG.code(); //交易所
			case 3: //银行间市场
				return MarketOpt.E_IB.code(); //银行间
			case 4: //柜台交易市场
				return MarketOpt.E_IB.code(); //银行间
			case 5: //一级市场
				break; // 一级市场（暂时不需要，以后要放在一级发行tab中）
			case 6: //上交所固定收益平台
				return MarketOpt.E_EXCHG.code(); //交易所
			default: break;
			}
		}
		return null;
	}

	public enum PdOpt
	{
		//! The enum value has to be as same as the definition in t_pub_par ! 
		E_INVEST_LEVEL(1), 
		E_RISK_LEVEL(2),
		E_ALARM_LEVEL(3),
		E_DEFAULT(4);

		private Integer value;

		PdOpt(Integer value) {
			this.value = value;
		}
		public Integer code() {
			return value;
		}
		static public PdOpt fromInteger(Integer param) {
			PdOpt[] opts = PdOpt.values();
			for (PdOpt opt : opts)
				if (opt.code().equals(param))
					return opt;
			return null;
		}	
		
		static public PdOpt fromPdNum(Long pdNum) {
			if (pdNum >= 1 && pdNum <= 9) {
				return E_INVEST_LEVEL;
			}
			else if (pdNum >=10 && pdNum <= 17) {
				return E_RISK_LEVEL;
			}
			else if (pdNum <= 20) {
				return E_ALARM_LEVEL;
			}
			else if (pdNum == 21) {
				return E_DEFAULT;
			}
			throw new BusinessException("错误的主体量化风险等级");
		}
	}

	
	static public Set<Integer> cvtPdStrictOptFromOpt(List<Integer> opts)
	{
		if (opts == null) return null;
		
		Set<Integer> hs = new HashSet<Integer>();
		Iterator<Integer> it = opts.iterator();
		while (it.hasNext()) {
			Integer param = it.next();
			PdOpt opt = PdOpt.fromInteger(param);
			if (opt == null) {
				LOG.error("invalide tenor option, param:", param);
				continue;
			}
			switch (opt) {
				case E_RISK_LEVEL:
					hs.add(PdOpt.E_ALARM_LEVEL.code());
					hs.add(PdOpt.E_RISK_LEVEL.code());
					hs.add(PdOpt.E_DEFAULT.code());
					break;
				case E_ALARM_LEVEL:
				case E_DEFAULT:
				case E_INVEST_LEVEL:
					hs.add(opt.code());
					break;
				default:
					assert false : "cvtPdStrictOptFromOpt branch should not reach!";
					break;
			}
		}
		return hs;
	}
	
	public enum TenorOpt
	{
		//! The enum value has to be as same as the definition in t_pub_par ! 
		E_lS_3M(1), 
		E_3_6M(2),
		E_6_9M(3),
		E_9_12M(4),
		E_1_3Y(5), 
		E_3_5Y(6),
		E_5_7Y(7), 
		E_7_10Y(8), 
		E_GE_10Y(9);

		private Integer value;

		TenorOpt(Integer value) {
			this.value = value;
		}
		public Integer code() {
			return value;
		}
		static public TenorOpt fromInteger(Integer param) {
			TenorOpt[] opts = TenorOpt.values();
			for (TenorOpt opt : opts)
				if (opt.code().equals(param))
					return opt;
			return null;
		}	
	}

	public enum TenorStrictOpt
	{
		E_lS_3M(1), E_3M(3), E_3_6M(4), 
		E_6M(6), E_6_9M(7), E_9M(9), E_9_12M(10),
		E_1Y(12), E_1_3Y(24), E_3Y(36), E_3_5Y(48),
		E_5Y(60), E_5_7Y(72), E_7Y(84), E_7_10Y(96), 
		E_10Y(120), E_GT_10Y(132), E_ERR(999);

		private Integer value;    
		TenorStrictOpt(Integer value) {
			this.value = value;
		}
		public Integer code() {
			return value;
		}
	}

	static public List<Integer> cvtTenorStrictOptFromOpt(List<Integer> opts)
	{
		if (opts == null) return opts;

		Set<Integer> hs = new HashSet<Integer>();
		Iterator<Integer> it = opts.iterator();
		while (it.hasNext()) {
			Integer param = it.next();
			TenorOpt opt = TenorOpt.fromInteger(param);
			if (opt == null) {
				LOG.error("invalide tenor option, param:", param);
				continue;
			}
			switch (opt) {
			case E_lS_3M:
				hs.addAll(asList(TenorStrictOpt.E_lS_3M.code()));
				break;
			case E_3_6M:
				hs.addAll(asList(TenorStrictOpt.E_3M.code(), TenorStrictOpt.E_3_6M.code(), TenorStrictOpt.E_6M.code()));
				break;
			case E_6_9M:
				hs.addAll(asList(TenorStrictOpt.E_6M.code(), TenorStrictOpt.E_6_9M.code(), TenorStrictOpt.E_9M.code()));
				break;
			case E_9_12M:
				hs.addAll(asList(TenorStrictOpt.E_9M.code(), TenorStrictOpt.E_9_12M.code(), TenorStrictOpt.E_1Y.code()));
				break;
			case E_1_3Y:
				hs.addAll(asList(TenorStrictOpt.E_1Y.code(), TenorStrictOpt.E_1_3Y.code(), TenorStrictOpt.E_3Y.code()));
				break;
			case E_3_5Y:
				hs.addAll(asList(TenorStrictOpt.E_3Y.code(), TenorStrictOpt.E_3_5Y.code(), TenorStrictOpt.E_5Y.code()));
				break;
			case E_5_7Y:
				hs.addAll(asList(TenorStrictOpt.E_5Y.code(), TenorStrictOpt.E_5_7Y.code(), TenorStrictOpt.E_7Y.code()));
				break;
			case E_7_10Y:
				hs.addAll(asList(TenorStrictOpt.E_7Y.code(), TenorStrictOpt.E_7_10Y.code(), TenorStrictOpt.E_10Y.code()));
				break;
			case E_GE_10Y:
				hs.addAll(asList(TenorStrictOpt.E_10Y.code(), TenorStrictOpt.E_GT_10Y.code()));
				break;
			default:
				assert false : "branch should not reach!";
			break;
			}
		}
		return hs == null? null : new ArrayList<Integer>(hs);
	}

	static public Integer cvtTenor2StrictOpt2(Long tenorDays)
	{
		if (tenorDays == null) return null;
		if (tenorDays < 0) return null;

		if (tenorDays < 90) 
			return TenorStrictOpt.E_lS_3M.code();
		else if (tenorDays == 90 )
			return TenorStrictOpt.E_3M.code();
		else if (tenorDays > 90 && tenorDays < 180 )
			return TenorStrictOpt.E_3_6M.code();
		else if ( tenorDays == 180) 
			return TenorStrictOpt.E_6M.code();
		else if (tenorDays > 180 && tenorDays < 270)
			return TenorStrictOpt.E_6_9M.code();
		else if (tenorDays == 270 )	
			return TenorStrictOpt.E_9M.code();
		else if (tenorDays > 270 && tenorDays < 366)
			return TenorStrictOpt.E_9_12M.code();
		else  {
			Long years = tenorDays/365;
			if (years >= 1 && years < 3)
				return TenorStrictOpt.E_1_3Y.code();
			else if (years == 3)	
				return TenorStrictOpt.E_3Y.code();
			else if (years > 3 && years < 5)
				return TenorStrictOpt.E_3_5Y.code();
			else if (years == 5)	
				return TenorStrictOpt.E_5Y.code();
			else if (years > 5 && years < 7)
				return TenorStrictOpt.E_5_7Y.code();
			else if (years == 7)	
				return TenorStrictOpt.E_7Y.code();
			else if (years > 7 && years < 10)
				return TenorStrictOpt.E_7_10Y.code();
			else if (years == 10)
				return TenorStrictOpt.E_10Y.code(); 
			else if (years > 10)
				return TenorStrictOpt.E_GT_10Y.code(); 
		}
		return null;
	}

	static public Integer cvtTenor2StrictOpt(BigDecimal val, Integer unit)
	{
		if (val == null) return null;

		switch (unit) {
		case 3: {  //D
			if (val.compareTo(BigDecimal.valueOf(90)) <0) 
				return TenorStrictOpt.E_lS_3M.code();
			else if (val.compareTo(BigDecimal.valueOf(90)) == 0 )
				return TenorStrictOpt.E_3M.code();
			else if (val.compareTo(BigDecimal.valueOf(90)) > 0 &&
					val.compareTo(BigDecimal.valueOf(180)) <0)
				return TenorStrictOpt.E_3_6M.code();
			else if (val.compareTo(BigDecimal.valueOf(180)) == 0) 
				return TenorStrictOpt.E_6M.code();
			else if (val.compareTo(BigDecimal.valueOf(180)) > 0 &&
					val.compareTo(BigDecimal.valueOf(270)) <0)
				return TenorStrictOpt.E_6_9M.code();
			else if (val.compareTo(BigDecimal.valueOf(270)) == 0 )	
				return TenorStrictOpt.E_9M.code();
			else if (val.compareTo(BigDecimal.valueOf(270)) > 0 &&
					val.compareTo(BigDecimal.valueOf(366)) <0)
				return TenorStrictOpt.E_9_12M.code();
			else return TenorStrictOpt.E_1Y.code();
		}	
		case 2: {  //M
			if (val.compareTo(BigDecimal.valueOf(3)) < 0) 
				return TenorStrictOpt.E_lS_3M.code();
			else if (val.compareTo(BigDecimal.valueOf(3)) >= 0 &&
					val.compareTo(BigDecimal.valueOf(6)) <0)
				return TenorStrictOpt.E_3_6M.code();
			else if (val.compareTo(BigDecimal.valueOf(6)) == 0 )	
				return TenorStrictOpt.E_6M.code();
			else if (val.compareTo(BigDecimal.valueOf(6)) > 0 &&
					val.compareTo(BigDecimal.valueOf(9)) <0)
				return TenorStrictOpt.E_6_9M.code();
			else if (val.compareTo(BigDecimal.valueOf(9)) == 0 )	
				return TenorStrictOpt.E_9M.code();
			else if (val.compareTo(BigDecimal.valueOf(9)) > 0 &&
					val.compareTo(BigDecimal.valueOf(12)) < 0)
				return TenorStrictOpt.E_9_12M.code();
			else if (val.compareTo(BigDecimal.valueOf(12)) == 0 )
				return TenorStrictOpt.E_1Y.code();
		}
		case 1: {  //Y
			if (val.compareTo(BigDecimal.valueOf(1)) ==0) 
				return TenorStrictOpt.E_1Y.code();
			else if (val.compareTo(BigDecimal.valueOf(1)) > 0 &&
					val.compareTo(BigDecimal.valueOf(3)) <0)
				return TenorStrictOpt.E_1_3Y.code();
			else if (val.compareTo(BigDecimal.valueOf(3)) == 0 )	
				return TenorStrictOpt.E_3Y.code();
			else if (val.compareTo(BigDecimal.valueOf(3)) > 0 &&
					val.compareTo(BigDecimal.valueOf(5)) <0)
				return TenorStrictOpt.E_3_5Y.code();
			else if (val.compareTo(BigDecimal.valueOf(5)) == 0 )	
				return TenorStrictOpt.E_5Y.code();
			else if (val.compareTo(BigDecimal.valueOf(5)) > 0 &&
					val.compareTo(BigDecimal.valueOf(7)) < 0)
				return TenorStrictOpt.E_5_7Y.code();
			else if (val.compareTo(BigDecimal.valueOf(7)) == 0 )	
				return TenorStrictOpt.E_7Y.code();
			else if (val.compareTo(BigDecimal.valueOf(7)) > 0 &&
					val.compareTo(BigDecimal.valueOf(10)) < 0)
				return TenorStrictOpt.E_7_10Y.code();
			else if (val.compareTo(BigDecimal.valueOf(10)) == 0)
				return TenorStrictOpt.E_10Y.code(); 
			else if (val.compareTo(BigDecimal.valueOf(10)) > 0)
				return TenorStrictOpt.E_GT_10Y.code(); 
		}
		default: return TenorStrictOpt.E_ERR.code();  
		}
	}

	/**
	 * 获取违约概率
	 * @param rating 违约评级
	 * @return
	 */
	public static BigDecimal convAmaRating2Pd(String rating){
		BigDecimal result = pdMaping.get(rating);
		return result == null ? new BigDecimal(-1) : result;
	}
	
	public static String convAmaRating2UIText(String rating){
	    BigDecimal pd = pdMaping.get(rating);
	    return (pd != null)? rating + "(" + pd + "%)" : null;
	}
	
	public static Map<String,Integer> getRiskMap(){
		return RISKMAP;
	}
	
}
