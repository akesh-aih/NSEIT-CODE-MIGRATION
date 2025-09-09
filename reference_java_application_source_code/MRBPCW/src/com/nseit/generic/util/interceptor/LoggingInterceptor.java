package com.nseit.generic.util.interceptor;

import org.apache.logging.log4j.Logger;

import com.nseit.generic.util.LoggerHome;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class LoggingInterceptor implements Interceptor {
	Logger logger = null;

	public void destroy() {
	}

	public void init() {
		logger = LoggerHome.getLogger(getClass());
	}

	public String intercept(ActionInvocation actionInvocation) throws Exception {
		String result = null;

		logger.debug("Begin Logger Interceptor - Action : " + actionInvocation.getProxy().getActionName());
		result = actionInvocation.invoke();
		logger.debug("End Logger Interceptor - Action : " + actionInvocation.getProxy().getActionName() + " - Result : " + actionInvocation.getResultCode());

		return result;
	}

}
