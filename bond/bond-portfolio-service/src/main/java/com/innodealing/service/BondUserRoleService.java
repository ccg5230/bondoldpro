package com.innodealing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.innodealing.consts.Constants;
import com.innodealing.util.SafeUtils;

/** 
* @author feng.ma
* @date 2017年8月11日 上午11:04:42 
* @describe 
*/
@Service
public class BondUserRoleService {

	@Autowired
	private RedisTemplate redisTemplate;
	
	public boolean isDebtRole(Integer userId) {
		boolean result = false;
		 if(SafeUtils.getInt(redisTemplate.opsForValue().get(Constants.DM_USER_DEBTROLE+userId)) == 1){
			 result = true;
		 }
		return result;
	}
	
}
