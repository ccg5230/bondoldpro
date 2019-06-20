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
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SyslogAop {

	@Pointcut("execution(* com.innodealing.service..*.*(..)) && ! @annotation(com.innodealing.aop.NoLogging)") // the
																												// pointcut
																												// expression
	public void logMethod() {
	}

	@Around(value = "logMethod()")
	public Object doAfter(ProceedingJoinPoint jp) throws Throwable {

		long start = System.currentTimeMillis();
		Object ret = jp.proceed();// 执行该方�?
		long end = System.currentTimeMillis();
		long totalTime = (end - start);

		String methodInfo = getJoinPointInfo(jp) + ", ret:" + ((ret != null) ? ret : "") + ", totalTime:" + totalTime
				+ "(ms)";

		LoggerFactory.getLogger(jp.getTarget().getClass()).info(methodInfo);
		return ret;
	}

	@Pointcut("execution(* com.innodealing.service..*.*(..)) ")
	public void logAllServiceException() {
	}

	@AfterThrowing(pointcut = "logAllServiceException()", throwing = "e")
	public void doAfterThrow(JoinPoint jp, Exception e) {
		LoggerFactory.getLogger(jp.getTarget().getClass()).error("Exception, " + getJoinPointInfo(jp), e);
	}

	static private String getJoinPointInfo(JoinPoint point) {
		MethodSignature ms = (MethodSignature) point.getSignature();
		String[] parameterNames = ms.getParameterNames();
		Object[] args = point.getArgs();
		Map<String, Object> parameters = new HashMap<String, Object>();
		for (int i = 0; i < parameterNames.length; i++) {
			parameters.put(parameterNames[i], args[i]);
		}
		return "method:" + point.getSignature().getName() + ", parameter:" + parameters;
	}
}
