package com.innodealing.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class BondDetailControllerTest extends BaseJunit {

	@Test
	public void testGetBondDetailInfo() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("comUniCode", "200025997");
		System.out.println(sendPost("/bondDetail/getBondDetailInfo", params));
	}

	@Test
	public void testGetBondDetail() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("bondId", "103069931");
		System.out.println(sendPost("/bondDetail/getBondDetail", params));
	}

	@Test
	public void testGetBondDetailBase() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("comUniCode", "200022793");
		System.out.println(sendPost("/bondDetail/getBondDetailBase", params));
	}

}
