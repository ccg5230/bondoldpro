package com.innodealing.controller;

import java.util.HashMap;

import org.junit.Test;

public class BondCreditRatingControllerTest extends BaseJunit {

	@Test
	public void testFindRatingAuditInfo() throws Exception {
		HashMap<String, Object> params = new HashMap<>();
		params.put("bondUniCode", "103005940");
		params.put("fatId", "11");
		System.out.println(sendPost("/api/bond/user/502160/creditrating/issuer/findRatingAuditInfo", params));
	}

}
