package com.innodealing.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.innodealing.async.AmarcalculateHandleTask;
import com.innodealing.dao.jdbc.dm.base.BondPerFinanceDao;
import com.innodealing.dao.redis.dm.bond.asbrs.RedisAmaresunAuthDao;
import com.innodealing.domain.AmaresunResponse;
import com.innodealing.util.HttpHelper;
import com.innodealing.util.JsonUtil;
import com.innodealing.util.MD5Utils;
import com.innodealing.util.RedisConstants;
import com.innodealing.util.SafeUtils;
import com.innodealing.util.StringUtils;
import com.innodealing.util.config.AppConfig;

@Service
public class BondFinancePrimaryDataService {

	private static final Logger LOG = LoggerFactory.getLogger(BondFinancePrimaryDataService.class);

	@Autowired
	private RedisAmaresunAuthDao redisAmaresunAuthDao;
	
	@Autowired
	private AmarcalculateHandleTask amarcalculateHandleTask;
	
	@Autowired
	private BondPerFinanceDao bondPerFinanceDao;
	
	public String getAuth() throws Exception {
		/*String token = redisAmaresunAuthDao.get(RedisConstants.BOND_AMARESUN_AUTH);
		if (!StringUtils.isBlank(token)) {
			return token;
		}*/
		String token = null;
		
		String url = AppConfig.getAmaresunUrlAuth();
		String userId = AppConfig.getAmaresunUserId();
		String userName = AppConfig.getAmaresunUserName();
		String password = AppConfig.getAmaresunPassword();
		
		Map<String, String> sortMap = new HashMap<>();
		sortMap.put("userId", userId);
		sortMap.put("userName", userName);
		sortMap.put("password", password);
		
		String paramResult = SafeUtils.formatUrlMap(sortMap, false, false);
		String sign = MD5Utils.Bit32(paramResult);
		LOG.info("----getAuth sign:" + sign);
		String result = HttpHelper.post(url, "password=" + password + "&userId=" + userId + "&userName=" + userName + "&sign=" + sign).getResponse();
		LOG.info("-------getAuth result:" + result);
		
		try {
			JSONObject resultJson = JSONObject.parseObject(result).getJSONObject("result");
			String status = resultJson.getString("status");
			if (!StringUtils.isBlank(status)&& "200".equals(status)) {
				token = resultJson.getString("token");
				redisAmaresunAuthDao.save(RedisConstants.BOND_AMARESUN_AUTH, token);
			}
		} catch (Exception e) {
		}
		return token;
	}
	
	public String calculateGate(Long taskId) throws Exception {
		String res = "0";
		String token = getAuth();
		
		String url = AppConfig.getAmaresunUrlGate();
		String userId = AppConfig.getAmaresunUserId();
		
		Map<String, String> sortMap = new HashMap<>();
		sortMap.put("taskId", String.valueOf(taskId));
		sortMap.put("userId", userId);
		sortMap.put("token", token);
		String paramResult = SafeUtils.formatUrlMap(sortMap, false, false);
		String sign = MD5Utils.Bit32(paramResult);
		LOG.info("----calculateGate sign:" + sign);
		String result = HttpHelper.post(url, "taskId=" + taskId + "&token=" + token + "&userId=" + userId + "&sign=" + sign).getResponse();
		AmaresunResponse resObj = JsonUtil.stringToTObj(result, AmaresunResponse.class);
		LOG.info("-------calculateGate result:" + result);
		
		if (null != resObj && null != resObj.getResult()) {
			if (200 == resObj.getResult().getStatus()) {
				res = "1";
			}
		}
		
		return res;
	}
	
	public void calculationResult(){
		List<String> taskIds = bondPerFinanceDao.getTaskIdInPerFin();
		for(String taskId : taskIds){
			if (!StringUtils.isBlank(taskId)) {
				amarcalculateHandleTask.putParams(taskId);
			}
		}
	}
	
}
