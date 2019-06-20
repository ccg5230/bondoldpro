package com.innodealing.dao.redis.dm.bond.asbrs;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.innodealing.util.RedisConstants;

/**
 * @author kunkun.zhou
 * @date 2017年01月18日
 * @clasename RedisAmaresunAuthDao.java
 * @decription TODO
 */
@Service
public class RedisAmaresunAuthDao {
	private static final Logger LOG = LoggerFactory.getLogger(RedisAmaresunAuthDao.class);
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Resource(name = "redisTemplate")
	private ValueOperations<String, String> optAuth;
	
	/**
	 * 过期时间   : 不设置
	 */
	public void saveNoExpire(String key, String value) {
		optAuth.set(key, value);
	}
	
	/**
	 * 过期时间   : 29天
	 * amaresun的auth过期时间是30天，为保持绝对有效，则为29天
	 */
	public void save(String key, String value) {
		saveWithExpire(key, value, RedisConstants.EXPIRE_TIME_AUTH);
	}

	/**
	 * 过期时间   : 参数expireTime[second]
	 */
	public void saveWithExpire(String key, String value, long expireTime) {
		optAuth.set(key, value, expireTime, TimeUnit.DAYS);
	}

	public String get(String key) {
		return optAuth.get(key);
	}
	
	public void delete(String key) {
		redisTemplate.delete(key);
	}

}
