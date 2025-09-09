package com.nseit.generic.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class LoggerHome
{
	private  Logger logger;
	
	static
	{
		/*try
		{
			PropertyConfigurator.configure("log4j.properties");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}*/
	}
	
	public static Logger getLogger(Class clazz)
	{
		return LogManager.getLogger(clazz);
	}
	
	public static void logThrowable(Class clazz, Throwable throwable)
	{
		Logger logger = getLogger(clazz);
		
		logger.fatal(throwable.getMessage(), throwable);
	}
}
