package com.nseit.generic.util;

import org.apache.logging.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

@SuppressWarnings("serial")
public class ExceptionLoggingInterceptor implements Interceptor {
  private Logger logger = LoggerHome.getLogger(getClass());
  public void init() {

  }

  public void destroy() {

  }

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
   String result;
   try{
     result=invocation.invoke();
   }catch (Exception e) {
     logger.error(e, e);
     throw e;
  }
    return result;
  }

}
