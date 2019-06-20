package com.innodealing.controller;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.innodealing.model.VUserInfo;
import com.innodealing.util.BusinessException;
import com.innodealing.util.JsonResult;
import com.innodealing.util.KitCost;


class BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<JsonResult<String>> handleBusinessException(Exception ex) {
		if (ex instanceof BusinessException) {
			BusinessException bex = (BusinessException) ex;
			JsonResult<String> result = new JsonResult<String>(bex.getCode(), bex.getMsg(), null);
			return new ResponseEntity<JsonResult<String>>(result, HttpStatus.OK);
		} else if (ex instanceof ConstraintViolationException) {
			ConstraintViolationException violationEx = (ConstraintViolationException) ex;
			Set<ConstraintViolation<?>> violations = violationEx.getConstraintViolations();
			String msg = "亲,验证异常,请检查您的输入是否有误！";
			if (violations != null && violations.size() != 0) {
				msg = violations.iterator().next().getMessage();
			}
			ex.printStackTrace();
			JsonResult<String> result = new JsonResult<String>("-1", msg, null);
			return new ResponseEntity<JsonResult<String>>(result, HttpStatus.OK);
		} else {
			ex.printStackTrace();
			JsonResult<String> result = new JsonResult<String>("-1", "啊哦!系统繁忙,请稍后再试~", null);
			return new ResponseEntity<JsonResult<String>>(result, HttpStatus.OK);
		}
	}

	protected <T> JsonResult<T> ok(T t) {
		return new JsonResult<T>("0", "success", t);
	}

	protected VUserInfo getCurrentUser() {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		String userid = servletRequestAttributes.getRequest().getParameter("userid");
		if(userid == null){
			userid = servletRequestAttributes.getRequest().getHeader("userid");
		}
		if (userid != null) {
			try {
				return vUserInfo.get(Integer.parseInt(userid));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private LoadingCache<Integer, VUserInfo> vUserInfo = CacheBuilder.newBuilder()
			.concurrencyLevel(8).expireAfterWrite(3600, TimeUnit.SECONDS).initialCapacity(10).maximumSize(100)
			.recordStats().removalListener(new RemovalListener<Object, Object>() {
				@Override
				public void onRemoval(RemovalNotification<Object, Object> notification) {
					LOG.info(notification.getKey() + " was removed, cause is " + notification.getCause());
				}
			})
			.build(new CacheLoader<Integer, VUserInfo>() {
				@Override
				public VUserInfo load(Integer key) throws Exception {
					Map<String, Object> result =  jdbcTemplate.queryForMap(
							"SELECT tuser.id user_id,userRole.role_id, tuser.`orgainfo_id` orgId FROM innodealing.t_sysuser tuser LEFT JOIN innodealing.dm_bond_user_role userRole  ON tuser.`id` = userRole.user_id where tuser.id = ?",
							key);
					return KitCost.mapToBean(result, VUserInfo.class);
				}
			});
}
