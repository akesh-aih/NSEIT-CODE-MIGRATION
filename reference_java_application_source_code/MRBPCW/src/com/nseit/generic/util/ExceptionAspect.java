package com.nseit.generic.util;

import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class ExceptionAspect
{

	private  final Logger log = LoggerHome
			.getLogger(ExceptionAspect.class);

	public Object handle(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable
	{
		try
		{
			return proceedingJoinPoint.proceed();
		}
		catch (Throwable t)
		{

			log.fatal("invocation of "
					+ proceedingJoinPoint.getSignature().toLongString()
					+ " failed", t);
			throw t;
		}
	}
}
