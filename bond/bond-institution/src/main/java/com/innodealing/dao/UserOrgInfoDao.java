package com.innodealing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import com.innodealing.util.HttpClientUtil2;
import com.innodealing.util.StringUtils;

import net.sf.json.JSONArray;

@Service
@SuppressWarnings("all")
public class UserOrgInfoDao {

	@Value("${config.define.queryCrmId}")
	private String accountUrl;

	@Value("${config.define.queryUserOrgId}")
	private String url;
	
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(UserOrgInfoDao.class);
    
	/**
	 * 根据用户ID得到用户机构信息
	 * 
	 * @param userId
	 * @return
	 */
	@Cacheable(value = "queryOrgIdByUserIdCache", key = "#userId")
	public Integer queryOrgIdByUserId(String userId) {
		try {
			List<Integer> list = new ArrayList<Integer>() {
				{
					add(Integer.parseInt(userId));
				}
			};
			// 获取crmid
			String result = HttpClientUtil2.doPost(accountUrl, list.toString(), "utf-8", userId.toString());
			Map<String, Object> map = JSON.parseObject(result);
			if (!StringUtils.isEmpty(map.get("data"))) {
				List<Object> data = JSON.parseArray(map.get("data").toString());
				Map<String, Object> accountMap = (Map<String, Object>) data.get(0);
				list.set(0, (Integer) accountMap.get("crmId"));
				// 获取机构信息
				result = HttpClientUtil2.doPost(url, list.toString(), "utf-8", String.valueOf(accountMap.get("crmId")));
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
			LOG.error("queryOrgIdByUserId exception with userId[" + userId + "]: " + ex.getMessage());
		}
		return null;
	}

	@Cacheable(value = "getUserNameByIdCache", key = "#userId")
	public String getUserNameById(Integer userId) {
		if (userId == null) {
			return null;
		}
    	StringBuffer sql = new StringBuffer();
    	sql.append("SELECT a.name AS userName FROM innodealing.t_sysuser a WHERE a.id=");
    	sql.append(userId);
		return jdbcTemplate.queryForObject(sql.toString(), String.class);
	}
	
	public static void main(String[] args) {

		List<Integer> dmIdList = new ArrayList<Integer>() {
			{
				add(515706);
				add(519081);
				add(515706);
				add(515706);
			}
		};

		List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();

		List<Integer> crmIdList = new ArrayList<Integer>();

		List<Integer> oldDmIdList = new ArrayList<>(dmIdList);

		// 去重
		dmIdList = removeDuplicate(dmIdList);

		// 获取crmid
		String result = HttpClientUtil2.doPost("http://r.qa.innodealing.com/meta-service/account/dm/id",
				dmIdList.toString(), "utf-8", "519081");
		Map<String, Object> map = JSON.parseObject(result);
		if (!StringUtils.isEmpty(map.get("data"))) {
			List<Object> data = JSON.parseArray(map.get("data").toString());
			if (data == null) {
				return;
			}
			Map<String, Object> accountMap = null;
			for (Integer dmid : dmIdList) {
				for (Object obj : data) {
					accountMap = (Map<String, Object>) obj;
					if (dmid.equals(accountMap.get("dmId"))) {
						crmIdList.add((Integer) accountMap.get("crmId"));
					}
				}
			}
			// 获取机构信息
			result = HttpClientUtil2.doPost("http://r.qa.innodealing.com/meta-service/user/detail/id",
					crmIdList.toString(), "utf-8", String.valueOf(accountMap.get("crmId")));
			map = JSON.parseObject(result);
			if (!StringUtils.isEmpty(map.get("data"))) {
				List<Object> data2 = JSON.parseArray(map.get("data").toString());
				if (data2 == null) {
					return;
				}
				List<Integer> newDmIdList = new ArrayList<Integer>();
				// 排序处理
				for (Integer dmid : oldDmIdList) {
					for (Object obj : data) {
						accountMap = (Map<String, Object>) obj;
						if (dmid.equals(accountMap.get("dmId"))) {
							newDmIdList.add((Integer) accountMap.get("crmId"));
						}
					}
				}

				for (Integer dmId : newDmIdList) {
					for (Object obj : data2) {
						Map<String, Object> vo = (Map<String, Object>) obj;
						if (dmId.equals(vo.get("id"))) {
							resultMap.add((Map<String, Object>) obj);
						}
					}
				}
				System.out.println(resultMap.toString());
			}
		}

	}

	/**
	 * 根据用户ID得到用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> queryUserByUserIdList(List<Integer> dmIdList, Integer userId) {

		List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();

		List<Integer> crmIdList = new ArrayList<Integer>();

		List<Integer> oldDmIdList = new ArrayList<>(dmIdList);

		// 去重
		dmIdList = removeDuplicate(dmIdList);
		try {
			// 获取crmid
			String result = HttpClientUtil2.doPost(accountUrl, dmIdList.toString(), "utf-8", userId.toString());
			Map<String, Object> map = JSON.parseObject(result);
			if (!StringUtils.isEmpty(map.get("data"))) {
				List<Object> data = JSON.parseArray(map.get("data").toString());
				if (data == null) {
					return null;
				}
				Map<String, Object> accountMap = null;
				for (Integer dmid : dmIdList) {
					for (Object obj : data) {
						accountMap = (Map<String, Object>) obj;
						if (dmid.equals(accountMap.get("dmId"))) {
							crmIdList.add((Integer) accountMap.get("crmId"));
						}
					}
				}
				// 获取机构信息
				result = HttpClientUtil2.doPost(url, crmIdList.toString(), "utf-8",
						String.valueOf(accountMap.get("crmId")));
				map = JSON.parseObject(result);
				if (!StringUtils.isEmpty(map.get("data"))) {
					List<Object> data2 = JSON.parseArray(map.get("data").toString());
					if (data2 == null) {
						return null;
					}

					List<Integer> newDmIdList = new ArrayList<Integer>();
					// 排序处理
					for (Integer dmid : oldDmIdList) {
						for (Object obj : data) {
							accountMap = (Map<String, Object>) obj;
							if (dmid.equals(accountMap.get("dmId"))) {
								newDmIdList.add((Integer) accountMap.get("crmId"));
							}
						}
					}

					for (Integer dmId : newDmIdList) {
						for (Object obj : data2) {
							Map<String, Object> vo = (Map<String, Object>) obj;
							if (dmId.equals(vo.get("id"))) {
								resultMap.add((Map<String, Object>) obj);
							}
						}
					}

					return resultMap;
				}
			}
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.error("queryUserByUserIdListCache exception with userId[" + userId + "]: " + ex.getMessage());
		}
		return null;
	}

	/**
	 * 集合去重
	 * @param list
	 * @return
	 */
	public static List removeDuplicate(List list) {
		HashSet h = new HashSet(list);
		list.clear();
		list.addAll(h);
		return list;
	}
}
