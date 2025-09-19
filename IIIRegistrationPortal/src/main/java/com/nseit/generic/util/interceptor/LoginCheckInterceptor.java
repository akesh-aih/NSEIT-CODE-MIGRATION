package com.nseit.generic.util.interceptor;

import org.apache.struts2.ActionInvocation;
import org.apache.struts2.interceptor.Interceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginCheckInterceptor implements Interceptor {

    private static final Logger logger = LogManager.getLogger(LoginCheckInterceptor.class);

    @Override
    public void destroy() {
        logger.debug("LoginCheckInterceptor destroyed.");
    }

    @Override
    public void init() {
        logger.debug("LoginCheckInterceptor initialized.");
    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        logger.debug("LoginCheckInterceptor: Performing login checks.");
        // In a real application, this would contain logic to check user roles, permissions, etc.
        // For now, just proceed.
        return invocation.invoke();
    }
}
