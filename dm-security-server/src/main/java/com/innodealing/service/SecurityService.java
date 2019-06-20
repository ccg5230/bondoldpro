package com.innodealing.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.innodealing.dao.SecurityDao;
import com.innodealing.exception.BusinessException;
import com.innodealing.util.security.HmacSha1Util;
/**
 * SecurityService for token
 * @author zhaozhenglai
 * @since 2016年12月22日 下午3:32:37 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
@Service
public class SecurityService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    public final static String USER_TOKEN_KEY = "sysuser_token_";
   
    @Autowired
    private SecurityDao securityDao;
    
    /**
     * get token
     * @param userAccount
     * @param password
     * @return
     */
    public String getToken(String userAccount, String password){
        String token = HmacSha1Util.sign(userAccount, password);
        String key = USER_TOKEN_KEY + userAccount;
        String tokenFromDb = redisTemplate.opsForValue().get(key);
        if(tokenFromDb == null || !tokenFromDb.equals(token)){
            Map<String,Object> mapUser = securityDao.findByNameAndPwd(userAccount, password);
            if(mapUser != null){
                Object passwordFromDB  = mapUser.get("password");
                if(passwordFromDB != null){
                    if(!passwordFromDB.equals(password)){
                        throw new BusinessException("密码有误，请核实密码是否正确！");
                    }
                }
                redisTemplate.opsForValue().set(key, token);
                return token;
            }else{
                return "非法用户！";
            }
        }else{
            return token;
        }
    }
}
