package com.nseit.generic.action;

import java.util.Map;

import org.apache.struts2.action.SessionAware;
import org.apache.struts2.ActionSupport;

public class BaseAction extends ActionSupport implements SessionAware {

    private static final long serialVersionUID = 1L;

    private Map<String, Object> userSession;

    @Override
    public void withSession(Map<String, Object> session) {
        this.userSession = session;
    }

    public Map<String, Object> getUserSession() {
        return userSession;
    }

}
