package com.nseit.generic.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class UserSessionBean implements  Serializable, HttpSessionBindingListener {

	private long userId;
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}



	//private static Map<UserSessionBean, HttpSession> logins = new HashMap<UserSessionBean, HttpSession>();
	
	@Override
    public boolean equals(Object other) {
		Long usId=Long.valueOf(userId);
        return (other instanceof UserSessionBean) && (usId != null) ? usId.equals((Long.valueOf(((UserSessionBean) other).userId))) : (other == this);
    }

    @Override
    public int hashCode() {
    	Long usId=Long.valueOf(userId);
        return (usId != null) ? (this.getClass().hashCode() + usId.hashCode()) : super.hashCode();
    }



	@Override
	public void valueBound(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		  /* HttpSession session = logins.remove(this);
	        if (session != null) {
	            session.invalidate();
	        }
	        logins.put(this, arg0.getSession());*/
		
	}



	@Override
	public void valueUnbound(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		 //logins.remove(this);
		
	}
	

}
