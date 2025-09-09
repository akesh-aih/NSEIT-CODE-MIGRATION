package com.nseit.generic.util;

import java.lang.reflect.Method;

import org.apache.logging.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

public class MethodLifeCycleLogger implements MethodBeforeAdvice
//, AfterReturningAdvice, ThrowsAdvice
{
	//Logger logger = LoggerHome.getLogger(MethodLifeCycleLogger.class);
	
	public void before(Method method, Object[] args, Object target)
		throws Throwable
	{
		//logger.info("Before Method Start :: " + method.getName());
	}
	
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable
	{
		//logger.info("After Method Start :: " + method.getName());
	}
	
	public void afterThrowing(IllegalArgumentException e) throws Throwable
	{
		//logger.info("Throw exception Caught :: " + e);
	}
}
