package com.nseit.generic.util;

import org.apache.logging.log4j.Logger;


public class GenericException extends Exception
{
	private Logger logger = LoggerHome.getLogger(getClass());
	
	public GenericException(Throwable ex) 
	{
		super(ex);
		
		String stackTrace=getMessage() + GenericConstants.LINE_SEPERATOR;
		for(StackTraceElement stackTraceElement : getStackTrace())
		{
			stackTrace=stackTrace + stackTraceElement.toString() + GenericConstants.LINE_SEPERATOR;
		}
		
		logger.error(stackTrace);		
	}
	
	public GenericException(String errDescription) 
	{
		super(errDescription);

		logger.error(errDescription);		
	}
}