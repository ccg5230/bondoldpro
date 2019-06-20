package com.innodealing.bond.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.innodealing.util.HttpClientUtil;
import com.innodealing.util.StringUtils;

import net.sf.json.JSONArray;

/**
 * 
 * @author liuqi
 *
 */
@Service
@SuppressWarnings("all")
public class BondUserInstitution {

	private static final Logger LOG = LoggerFactory.getLogger(BondUserInstitution.class);

	@Value("${config.define.metaservice.accountUrl}")
	private String accountUrl;

	@Value("${config.define.queryUserOrgId}")
	private String url;

	/**
	 * 根据用户ID得到用户机构信息
	 * 
	 * @param userId
	 * @return
	 */
	@Cacheable(value = "userInstitutionCache", key = "#userId")
	public Integer getUserInstitutionByUserId(String userId) {
		try {
			List<Integer> list = new ArrayList<Integer>() {
				{
					add(Integer.parseInt(userId));
				}
			};
			// 获取crmid
			String result = HttpClientUtil.doPostJson(accountUrl, list.toString());
			Map<String, Object> map = JSON.parseObject(result);
			if (!StringUtils.isEmpty(map.get("data"))) {
				List<Object> data = JSON.parseArray(map.get("data").toString());
				Map<String, Object> accountMap = (Map<String, Object>) data.get(0);
				list.set(0, (Integer) accountMap.get("crmId"));
				// 获取机构信息
				result = HttpClientUtil.doPostJson(url, list.toString());
				map = JSON.parseObject(result);
				if (!StringUtils.isEmpty(map.get("data"))) {
					data = JSON.parseArray(map.get("data").toString());
					Map<String, Object> companyMap = (Map<String, Object>) data.get(0);
					Map<String, Object> maps = (Map<String, Object>) companyMap.get("company");
					Integer orgId = (Integer) maps.get("orgId");
					return orgId;
				}
			}
			return null;
		} catch (Exception ex) {
			LOG.error("getUserInstitutionByUserId exception with userId[" + userId + "]: " + ex.getMessage());
		}
		return null;
	}

}
