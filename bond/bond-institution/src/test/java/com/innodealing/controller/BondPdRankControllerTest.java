package com.innodealing.controller;

import java.util.HashMap;

import org.junit.Test;

public class BondPdRankControllerTest extends BaseJunit {

	@Test
	public void testGetPdRankDetail() throws Exception {
		HashMap<String, Object> params = new HashMap<>();
		params.put("key", "200039085"); 
		System.out.println(sendPost("/bondIndu/getPdRankDetail", params));
	}

}
