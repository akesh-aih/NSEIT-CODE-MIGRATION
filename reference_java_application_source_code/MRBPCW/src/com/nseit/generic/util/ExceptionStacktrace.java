package com.nseit.generic.util;

public class ExceptionStacktrace {

  public static String get(Exception msg){
    String stackTrace=msg.getMessage() + GenericConstants.LINE_SEPERATOR;
    for(StackTraceElement stackTraceElement : msg.getStackTrace())
    {
      stackTrace=stackTrace + stackTraceElement.toString() + GenericConstants.LINE_SEPERATOR;
    }
    return stackTrace;
  }
}
