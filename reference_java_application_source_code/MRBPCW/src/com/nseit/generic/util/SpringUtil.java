package com.nseit.generic.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringUtil
{
	private static SpringUtil springUtil;
	private ApplicationContext applicationContext;
	
	static
	{
		springUtil = new SpringUtil();
	}
	
	private SpringUtil()
	{
		applicationContext = new ClassPathXmlApplicationContext("SpringBeans.xml");
		//applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
		
	}
	
	public static SpringUtil getInstance()
	{
		return springUtil;
	}
	
	public ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}
}
