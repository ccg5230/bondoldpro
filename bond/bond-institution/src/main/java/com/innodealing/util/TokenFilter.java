package com.innodealing.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


@WebFilter(urlPatterns = "/*", filterName = "indexFilter")
public class TokenFilter implements Filter {

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ListableBeanFactory listableBeanFactory;

	public final static String USER_TOKEN_KEY = "sysuser_token_";

	public static Boolean TOKEN_SWItCH = null;

	public static Set<String> urls = new HashSet<>();

	public static Map<Long, Long> USER_ID_CACHE = null;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	/**
	 * 初始化缓存
	 */
	private void initUserIdCache() {
		Integer count = 0;
		if (USER_ID_CACHE == null) {
			USER_ID_CACHE = new Hashtable<>();
			// 缓存
			doCache();
			count = USER_ID_CACHE.size();
		}
		Integer total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM innodealing.t_sysuser WHERE `status` = 1",
				Integer.class);
		if (total > count) {
			// 更新缓存
			doCache();
		}
	}

	/**
	 * 缓存方法
	 */
	private void doCache() {
		List<Long> userIds = jdbcTemplate.queryForList("SELECT id FROM  innodealing.t_sysuser where status = 1", Long.class);
		for (Long userId : userIds) {
			USER_ID_CACHE.put(userId, userId);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// 初始化用户缓存
		initUserIdCache();

		HttpServletRequest httpRequset = (HttpServletRequest) request;
		StringBuffer url = httpRequset.getRequestURL();

		String method = httpRequset.getMethod();
		
		String userId = httpRequset
				.getHeader("userid");
		userId = userId == null ? httpRequset.getParameter("userid") : userId;
		if (method.equalsIgnoreCase("OPTIONS")) {
			chain.doFilter(request, response);
			return;
		}
		// 判断是否开启token验证
		if (TOKEN_SWItCH == null) {
			String[] beanNames = listableBeanFactory.getBeanNamesForAnnotation(EnableTokenFilter.class);
			if (beanNames != null && beanNames.length != 0) {
				TOKEN_SWItCH = true;
			} else {
				TOKEN_SWItCH = false;
			}
		}
		// 如开启进行token验证
		if (TOKEN_SWItCH) {
			/*
			 * if(token == null || userAccount == null){ return ; } String
			 * tokenFromDb = redisTemplate.opsForValue().get(USER_TOKEN_KEY +
			 * userAccount); if(!token.equals(tokenFromDb)){ return ; }
			 */
			// swagger 优先
			if (url != null && (url.toString().contains("swagger") || url.toString().contains("api-docs"))) {
				// chain.doFilter(request, response);
			} else {
				if (userId == null || USER_ID_CACHE.get(Long.valueOf(userId)) == null) {
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
