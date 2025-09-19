package com.nseit.generic.util.interceptor;

import org.apache.struts2.ActionInvocation;
import org.apache.struts2.interceptor.Interceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Map;

public class SessionCheckInterceptor implements Interceptor {

    private static final Logger logger = LogManager.getLogger(SessionCheckInterceptor.class);

    @Override
    public void destroy() {
        logger.debug("SessionCheckInterceptor destroyed.");
    }

    @Override
    public void init() {
        logger.debug("SessionCheckInterceptor initialized.");
    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        Map<String, Object> session = invocation.getInvocationContext().getSession();
        Object user = session.get("user"); // Assuming "user" is stored in session upon login

        if (user == null) {
            logger.warn("User not found in session. Redirecting to login.");
            return "sessionExpired"; // Result name defined in struts.xml
        } else {
            logger.debug("User found in session. Proceeding with action.");
            return invocation.invoke();
        }
    }
}
