package com.innodealing.util;

import java.util.HashMap;
import java.util.Map;

public class UIAdapter {

	private static final Map<Integer, String> mktMaps = new HashMap<Integer, String>() {{
		put(1, ".SZ");
		put(2, ".SH");
		put(3, ".IB");
		put(4, ".IB");
		put(5, "");
		put(6, ".SH");
	}};


	static public String cvtSecMar2Postfix(Integer secMarket)
	{
		if (secMarket == null) return "";
		if (!mktMaps.containsKey(secMarket))
			return "";

		return mktMaps.get(secMarket);
	}

}
