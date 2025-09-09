package com.nseit.generic.util;

import org.apache.logging.log4j.Logger;

public class LogicError extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public LogicError(String msg) {
    super(msg);
  }
}
