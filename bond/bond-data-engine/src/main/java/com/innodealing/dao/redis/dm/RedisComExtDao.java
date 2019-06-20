package com.innodealing.dao.redis.dm;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.innodealing.domain.BondComExtVO;
import com.innodealing.util.RedisConstants;

/**
 * @author kunkun.zhou
 * @date 2016年12月08日
 * @clasename RedisRuleRowKeyDao.java
 * @decription TODO
 */
@Service
public class RedisComExtDao {
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Resource(name = "redisTemplate")
	private ValueOperations<String, BondComExtVO> opt;
	
	/**
	 * 过期时间   : 不设置
	 */
	public void saveNoExpire(String key, BondComExtVO value) {
		opt.set(key, value);
	}
	
	/**
	 * 过期时间   : 2小时
	 */
	public void save(String key, BondComExtVO value) {
		saveWithExpire(key, value, RedisConstants.EXPIRE_TIME);
	}

	/**
	 * 过期时间   : 参数expireTime[second]
	 */
	public void saveWithExpire(String key, BondComExtVO value, long expireTime) {
		opt.set(key, value, expireTime, TimeUnit.SECONDS);
	}

	public BondComExtVO get(String key) {
		return opt.get(key);
	}
	
	public void delete(String key) {
		redisTemplate.delete(key);
	}

}
