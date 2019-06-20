package com.innodealing.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.innodealing.Application;
import com.innodealing.util.KitCost;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-dev.properties")
public class BaseJunit {

	@Autowired
	protected MockMvc mvc;

	protected String sendPost(String uri) {
		try {
			MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON))
					.andReturn();
			return mvcResult.getResponse().getContentAsString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected String sendPost(String uri, Map<String, Object> params) {
		MultiValueMap<String, String> valMap = new LinkedMultiValueMap<>();
		
		try {
			StringBuffer urlBuf = new StringBuffer(uri);
			if (params != null)
				params.forEach((x, y) -> {
					valMap.add(x, y+"");
				});
			MvcResult mvcResult = mvc
					.perform(MockMvcRequestBuilders.post(urlBuf.toString()).params(valMap).accept(MediaType.APPLICATION_JSON))
					.andReturn();

			if (mvcResult.getResponse().getStatus() == HttpStatus.SC_OK)

				return mvcResult.getResponse().getContentAsString();
			else {
				return mvcResult.getResponse().getErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Deprecated
	protected String sendPost_(String uri, Map<String, Object> params) {
		try {
			StringBuffer urlBuf = new StringBuffer(uri);
			if (params != null) {
				urlBuf.append("?");
			}
			if (params != null)
				params.forEach((x, y) -> {
					if (params instanceof IdentityHashMap) {
						try {
							urlBuf.append(x + "=" + URLEncoder.encode(y.toString(), "utf-8") + "&");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					} else {
						urlBuf.append(x + "=" + y + "&");
					}
				});

			MvcResult mvcResult = mvc
					.perform(MockMvcRequestBuilders.post(urlBuf.toString()).accept(MediaType.APPLICATION_JSON))
					.andReturn();

			if (mvcResult.getResponse().getStatus() == HttpStatus.SC_OK)

				return mvcResult.getResponse().getContentAsString();
			else {
				return mvcResult.getResponse().getErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected String sendGet(String uri) {
		try {
			MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON))
					.andReturn();
			return mvcResult.getResponse().getContentAsString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected String str(String key) {
		return new String(key);
	}

	protected Map<String, Object> kv(String k, Object v) {
		Map<String, Object> map = new HashMap<>();
		map.put(k, v);
		return map;
	}

	protected Object[] arrs(Object... object) {
		return object;
	}

	protected Map<String, Object> bean(Map<?, ?>... map) {
		Map<String, Object> result = new HashMap<>();
		for (Map<?, ?> objects : map) {
			result.put(objects.keySet().iterator().next() + "", objects.values().iterator().next());
		}
		return result;
	}

	protected String toJson(Map<?, ?>... map) {
		Map<String, Object> result = new HashMap<>();
		for (Map<?, ?> objects : map) {
			result.put(objects.keySet().iterator().next() + "", objects.values().iterator().next());
		}
		return KitCost.objToJson(result);
	}

	protected String toJson(Object... obj) {
		return KitCost.objToJson(obj);
	}

}
