package com.innodealing.dao.redis.dm.bond.asbrs;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.innodealing.model.dm.bond.gate.BondRuleRowKey;
import com.innodealing.util.RedisConstants;

/**
 * @author kunkun.zhou
 * @date 2016年12月08日
 * @clasename RedisRuleRowKeyDao.java
 * @decription TODO
 */
@Service
public class RedisRuleRowKeyDao {
	private static final Logger LOG = LoggerFactory.getLogger(RedisRuleRowKeyDao.class);
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Resource(name = "redisTemplate")
	private ValueOperations<String, BondRuleRowKey> optRuleRowKey;
	
	/**
	 * 过期时间   : 不设置
	 */
	public void saveNoExpire(String key, BondRuleRowKey value) {
		optRuleRowKey.set(key, value);
	}
	
	/**
	 * 过期时间   : 2小时
	 */
	public void save(String key, BondRuleRowKey value) {
		saveWithExpire(key, value, RedisConstants.EXPIRE_TIME);
	}

	/**
	 * 过期时间   : 参数expireTime[second]
	 */
	public void saveWithExpire(String key, BondRuleRowKey value, long expireTime) {
		optRuleRowKey.set(key, value, expireTime, TimeUnit.SECONDS);
	}

	public BondRuleRowKey get(String key) {
		return optRuleRowKey.get(key);
	}
	
	public void delete(String key) {
		redisTemplate.delete(key);
	}

}
