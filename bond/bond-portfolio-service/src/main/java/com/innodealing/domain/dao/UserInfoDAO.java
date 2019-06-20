package com.innodealing.domain.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.innodealing.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserInfoDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoDAO.class);

    @Value("${config.define.queryCrmId}")
    private String queryCrmIdUrl;

    @Cacheable(value = "getUidByUserIdCache", key = "#userId")
    public String getUidByUserId(String userId) {
        String result = "";
        String params = Arrays.asList(userId).toString();
        try {
            String response = HttpClientUtil.doPostWithUserId(queryCrmIdUrl, params, userId);
            String code = JSONObject.parseObject(response).getString("code");
            if ("0".equals(code)) {
                JSONArray dataArray = JSONObject.parseObject(response).getJSONArray("data");
                if (dataArray != null && dataArray.size() == 1) {
                    JSONObject jsonObject = dataArray.getJSONObject(0);
                    if (jsonObject.containsKey("crmId")) {
                        result = jsonObject.get("crmId").toString();
                    }
                }
            } else {
                LOGGER.error("Failed to getUidByUserId [{}]", JSONObject.parseObject(response).getString("message"));
            }
        } catch (Exception ex) {
            LOGGER.error("getUidByUserId error[{}]", ex.getMessage());
            ex.printStackTrace();
        }
        return result;
    }
}
