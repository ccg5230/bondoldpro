package com.innodealing.bond.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.innodealing.util.HttpClientUtil;

/**
 * @author stephen.ma
 * @date 2016年9月28日
 * @clasename BondGuidePriceService.java
 * @decription TODO
 */
@Service
public class BondGuidePriceService {
	
	private final static Logger logger = LoggerFactory.getLogger(BondGuidePriceService.class);
	
    @Value("${config.define.guidepriceUrl}")
    private String guidepriceUrl;

	/**
	 * @param date
	 * @return
	 */
	public String findGuidePrice(String date) {
		String jsonResult = "";
		
		try {
			
			Map<String, String> params = new HashMap<>();
			params.put("date", date);
			params.put("category", "6");
			jsonResult = HttpClientUtil.doPost(guidepriceUrl, params);
			
		} catch (Exception e) {
			logger.error("findGuidePrice error:"+e.getMessage(), e);
		}
		
		return jsonResult;
	}

}
