/**
 * 
 */
package com.innodealing.controller;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import org.junit.Test;

/**
 * 
 * @author 戴永杰
 *
 * @date 2017年9月14日 上午10:38:40 
 * @version V1.0   
 *
 */

public class BondPubControllerTest extends BaseJunit {
	
	@Test
	public void testGetBondPubList() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("key", "神火集团");
		System.out.println(sendPost("/bondpub/getBondPubList", params));
	}

	@Test
	public void testGetBondList() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("key", "神火集团");
		System.out.println(sendPost("/bondpub/getBondList", params));
	}

	@Test
	public void testSetBondPub() throws Exception {
		HashMap<String, Object> params = new HashMap<>();
		params.put("id", "103005940");
		params.put("type", "1");
		params.put("comUniCode", "200025997");
		params.put("remark", "东软控股");
		params.put("userid", "518602");
		
//		params.put("id", "103005940");
//		params.put("type", "1");
//		params.put("comUniCode", "200246083");
//		params.put("remark", "9999");
//		params.put("userid", "518602");
		System.out.println(sendPost("/bondpub/setBondPub", params));
	}

	@Test
	public void testRemoveBondPub() throws Exception {
		Map<String, Object> params = new IdentityHashMap<>();
		params.put("id", "17,18,19");
		System.out.println(sendPost("/bondpub/removeBondPub", params));
	}

	@Test
	public void testGetBondPubListPage() throws Exception {
		HashMap<String, Object> params = new HashMap<>();
		params.put("key", "");
		params.put("type", "1");
		params.put("page", "2");
		params.put("limit", "3");
		params.put("userid", "518602");
		System.out.println(sendPost("/bondpub/getBondPubListPage", params));
	}

	@Test
	public void testCheckBondPubValid() throws Exception {
		HashMap<String, Object> params = new HashMap<>();
		params.put("comUniCode", "200311829");
		System.out.println(sendPost("/bondpub/checkBondPubValid", params));
	}

	@Test
	public void testGetBondPubOnBondComDetail() throws Exception {
		HashMap<String, Object> params = new HashMap<>();
		params.put("comUniCode", "200246083");
		params.put("userid", "518602");
		params.put("page", "1");
		params.put("limit", "5");
		System.out.println(sendPost("/bondpub/getBondPubOnBondComDetail", params));
	}

	@Test
	public void testGetBondPubListByBondCode() throws Exception {
		HashMap<String, Object> params = new HashMap<>();
		params.put("bondUniCode", "103013786");
		params.put("userid", "502160");
		params.put("type", "2");
		params.put("fatId", "11");
		System.out.println(sendPost("/bondpub/getBondPubListByBondCode", params));
	}
	
}
