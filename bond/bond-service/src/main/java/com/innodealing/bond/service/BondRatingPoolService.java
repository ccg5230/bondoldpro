package com.innodealing.bond.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.innodealing.domain.JsonResult;
import com.innodealing.model.dm.bond.BondCreditRatingGroup;
import com.innodealing.util.HttpClientUtil;

@Service
public class BondRatingPoolService {

	@Value("${config.define.BondCreditRatingGroup.queryUrl}")
	private String queryUrl;

	public BondCreditRatingGroup getRatingGroupByComUniCode(Long userId, Long comUniCode) {

		String url = queryUrl+"/api/bond/users/%1$d/creditrating/creditRatingGroup?issuerId=%2$d";
		String str = HttpClientUtil.doGet(String.format(url, userId, comUniCode), null);
		@SuppressWarnings("unchecked")
		JsonResult<Object> jsonResult = JSONObject.parseObject(str, JsonResult.class);
		if(jsonResult == null || jsonResult.getData() == null)
			return null;
		BondCreditRatingGroup ratingGroup = JSONObject.parseObject(jsonResult.getData().toString(), BondCreditRatingGroup.class);
		return ratingGroup;

	}

}
