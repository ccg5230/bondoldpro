package com.innodealing.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.innodealing.annotation.EnableTokenFilter;
import com.innodealing.exception.BusinessException;
import com.mysql.jdbc.StringUtils;
/**
 * token filter
 * @author zhaozhenglai
 * @since 2016年12月20日 下午6:45:55 
 * Copyright © 2016 DealingMatrix.cn. All Rights Reserved.
 */
@Component
public class TokenFilter implements Filter{
    
    @Autowired private RedisTemplate<String, String> redisTemplate;
    
    @Autowired private JdbcTemplate jdbcTemplate;
    
    @Autowired private ListableBeanFactory listableBeanFactory;
    
    @Autowired private Environment env;
    
    public final static String USER_TOKEN_KEY = "sysuser_token_";
    
    public static Boolean TOKEN_SWItCH = null;
    
    public static  Set<String> urls = new HashSet<>();
    
    public static Map<Long,Long> USER_ID_CACHE = null;
    
    private static Date lastCacheCheckTime = null;
    private static String lastUserSiginature = "";
    
    private static String envType; 
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	envType = env.getProperty("BOND_DEPLOYMENT_ENV", "prod");
    }
    
    /**
     * 初始化缓存
     */
    private void initUserIdCache(){
        if(USER_ID_CACHE == null){
            USER_ID_CACHE = new Hashtable<>();
            lastCacheCheckTime = new Date();
            doCache(null);
        }
        else {
	        updateCache();  
        }
    }
    
    private void updateCache()
    {
    	Date currentTime = new Date();
    	//刷新缓存是重操作，这里做时间间隔保护，避免每次http请求都去刷新缓存
    	if (currentTime.getTime() - lastCacheCheckTime.getTime() < 60000) 
    		return ;
    	else 
    		lastCacheCheckTime = new Date();
    	
    	//这个逻辑条件成立的前提是用户表不做物理删除
		jdbcTemplate.execute("SET SESSION group_concat_max_len=204800");
    	String newSignature = jdbcTemplate.queryForObject("SELECT md5(GROUP_CONCAT(id)) FROM innodealing.t_sysuser where status = 1", String.class);
        if(!newSignature.equals(lastUserSiginature)) {
        	doCache(newSignature);
        }
    }
    
    private void doCache(String userSiginature) {
        if (StringUtils.isNullOrEmpty(userSiginature)) {
        	jdbcTemplate.execute("SET SESSION group_concat_max_len=204800");
        	userSiginature = jdbcTemplate.queryForObject("SELECT md5(GROUP_CONCAT(id)) FROM innodealing.t_sysuser where status = 1", String.class);
        }
        lastUserSiginature = userSiginature;
        
        List<Long> userIds = jdbcTemplate.queryForList("SELECT id FROM innodealing.t_sysuser where status = 1", Long.class);
        for (Long userId : userIds) {
            USER_ID_CACHE.put(userId, userId);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        //初始化用户缓存
        initUserIdCache();
        
        HttpServletRequest httpRequset = (HttpServletRequest) request;
        StringBuffer url = httpRequset.getRequestURL(); 
        
        
        String method = httpRequset.getMethod();
//        String token = httpRequset/*.getParameter("token");*/.getHeader("token");
//        String userAccount = httpRequset/*.getParameter("userAccount");*/.getHeader("userAccount");
        String userId = httpRequset.getHeader("userid")/*.getParameter("userid")*/;
        userId = userId == null ? httpRequset.getParameter("userid") : userId;
        if(method.equalsIgnoreCase("OPTIONS")){
            chain.doFilter(request, response);
            return ;
        }
        //判断是否开启token验证
        if(TOKEN_SWItCH == null){
            String[] beanNames = listableBeanFactory.getBeanNamesForAnnotation(EnableTokenFilter.class);
            if(beanNames != null && beanNames.length != 0){
                TOKEN_SWItCH = true;
            }else{
                TOKEN_SWItCH = false;
            }
        }
        
        if(url != null && (url.toString().contains("swagger") || url.toString().contains("api-docs")
        		|| url.toString().endsWith("bond-web/api/bond/users/headAuthorization")
        		|| url.toString().endsWith("bond-web/api/bond/getComChiNameBykey")
        		|| url.toString().endsWith("bond-web/api/bond/getComDetail")
        		|| url.toString().contains("socket-"))){
//            chain.doFilter(request, response);
        }else{
        	//如开启进行token验证
            if(TOKEN_SWItCH && url != null) {
                if(envType.equalsIgnoreCase("prod") && (userId == null || USER_ID_CACHE.get(Long.valueOf(userId)) == null)) {
                	throw new BusinessException("当前用户不存在或用户session已失效！");
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        
    }

}
