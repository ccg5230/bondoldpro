package com.innodealing.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SystemControllerTest extends BaseJunit {

	@Test
	public void testGetFinancialStatus() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("userid", "518602");
		System.out.println(sendPost("/system/getFinancialStatus",params));
	}

	@Test
	public void testSetfinancialStatus() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("userid", "518602");
		params.put("id", "7");
		params.put("status", "0");
		System.out.println(sendPost("/system/setfinancialStatus",params));
	}

}
