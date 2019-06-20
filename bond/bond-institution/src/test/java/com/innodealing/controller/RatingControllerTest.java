package com.innodealing.controller;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import org.junit.Test;

public class RatingControllerTest extends BaseJunit {

	@Test
	public void testGetInnerRateList() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("userid", "500115");
		System.out.println(sendPost("/rating/getInnerRateList", params));
	}

	/**
	 * 该测试需要URL解码
	 * @throws Exception
	 */
	@Test
	public void testSetInnerRate() throws Exception {

		Map<String, Object> params = new IdentityHashMap<>();
		params.put("userid", "502160");
		
//		params.put(str("innerRate"), toJson(kv("sort",1),kv("name", "bbb")));
//		params.put(str("innerRate"), toJson(kv("sort",3),kv("name", "qqq"),kv("id",0)));
//		params.put(str("innerRate"), toJson(kv("sort",2),kv("name", "www"),kv("id",98)));
//		params.put(str("innerRate"), toJson(kv("sort",6),kv("name", "ddd"),kv("id",99)));
//		params.put(str("innerRate"), toJson(kv("sort",4),kv("name", "ccc")));
//		params.put(str("innerRate"), toJson(kv("sort",5),kv("name", "fff")));
		
		params.put(str("innerRate"), "[{\"sort\":0,\"name\":\"AAA++\",\"id\":712},{\"sort\":3,\"name\":\"9999\",\"id\":718}]]");
		System.out.println(sendPost("/rating/setInnerRate", params));

	}

	/**
	 * 该测试需要URL解码
	 * @throws Exception
	 */
	@Test
	public void testSetInvestSuggest() throws Exception {

		Map<String, Object> params = new IdentityHashMap<>();
		params.put("userid", "500115");
		params.put(str("investSuggest"), "{\"sort\":1,\"name\":\"可投\"}");
		params.put(str("investSuggest"), "{\"sort\":4,\"name\":\"不可投\"}");
		params.put(str("investSuggest"), "{\"sort\":2,\"name\":\"其他业务\"}");
		System.out.println(sendPost("/rating/setInvestSuggest", params));
	}

	@Test
	public void testGetInvestSuggestList() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("userid", "500115");
		System.out.println(sendPost("/rating/getInvestSuggestList", params));
	}

	@Test
	public void testDelInnerRate() throws Exception {
		HashMap<String, Object> params = new HashMap<>();
		params.put("userid", "500115");
		params.put("id", "12");
		System.out.println(sendPost("/rating/delInnerRate", params));
	}

	@Test
	public void testDelInvestSuggest() throws Exception {
		HashMap<String, Object> params = new HashMap<>();
		params.put("userid", "500115");
		params.put("id", "17");
		System.out.println(sendPost("/rating/delInvestSuggest", params));
	}

}
