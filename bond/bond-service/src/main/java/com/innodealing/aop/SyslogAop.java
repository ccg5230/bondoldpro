package com.innodealing.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 系统日志
 * @author 赵正来
 * @since 2016年7月21日上午11:46:38 
 * Copyright © 2015 DealingMatrix.cn. All Rights Reserved.
 */
@Aspect
@Component
public class SyslogAop {
	
	@Pointcut("execution(* com.innodealing.bond.service..*.*(..)) && ! @annotation(com.innodealing.aop.NoLogging)") // the pointcut expression
	public void logMethod() {
	}
	
	@Around(value="logMethod()")
	public Object doAfter(ProceedingJoinPoint jp) throws Throwable {
		long start = System.currentTimeMillis();
		Object obj = jp.proceed();//执行该方�?
		long end = System.currentTimeMillis();
		long totalTime = (end-start);
		doLog(jp,null,totalTime);
		return obj;
	}

	@AfterThrowing(pointcut="logMethod()",throwing="e")
	public void doAfterThrow(JoinPoint jp,Exception e) {
		doLog(jp,e,null);
	}
	
	public void doLog(JoinPoint point,Exception e,Long totalTime){
		Logger logger = LoggerFactory.getLogger(point.getTarget().getClass());
		//封装参数
		MethodSignature ms = (MethodSignature) point.getSignature();
		String[] parameterNames = ms.getParameterNames();
		Object[] args = point.getArgs();
		Map<String,Object> parameters =  new HashMap<String, Object> ();
		if (parameterNames != null) {
			for (int i=0;i< parameterNames.length ; i++) {
				parameters.put(parameterNames[i], args[i]);
			}
		}
		//log
		if(e==null){
			logger.info("info:{parameter:" + parameters+",totalTime:{"+totalTime+"ms}}" );
		}else{
			logger.error(e.getMessage());
		}
	}
	
	
}
