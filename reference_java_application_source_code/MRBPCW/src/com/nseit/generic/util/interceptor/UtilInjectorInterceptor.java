package com.nseit.generic.util.interceptor;

import static com.nseit.generic.util.GenericConstants.GLOBAL_PLAIN_TEXT_MESSAGE;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.nseit.generic.action.BaseAction;
import com.nseit.generic.models.Users;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.ValidatorUtil;
import com.nseit.generic.util.aware.UserAware;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.logging.Logger;

public class UtilInjectorInterceptor implements Interceptor ,ServletRequestAware,ServletResponseAware 
{
	private HttpServletRequest request;    
	private HttpServletResponse response;
	private static final String REQUEST_HEADER_ACCEPT = "Accept";
	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void init()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception
	{
		Object action = actionInvocation.getAction();
		
		request=ServletActionContext.getRequest();
		response=ServletActionContext.getResponse();
		
		boolean isValidParameterValues = ValidatorUtil.validateRequestParameters(request);
		
		if(!isValidParameterValues)
		{
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Invalid Request.");
			request.setAttribute(GenericConstants.MESSAGE_TYPE, GenericConstants.MESSAGE_TYPE_ERROR);
			request.setAttribute(GenericConstants.DESTINATION_PATH, "LoginAction_input.action");
			return "displayMessage";
		}
		
		
		//final String acceptHeader = ServletActionContext.getRequest().getHeader(REQUEST_HEADER_ACCEPT);     
		// Map<String, String> acceptHeader = ServletActionContext.getRequest().getParameterMap();   
		
	
		
		if(action instanceof UserAware)
		{
			((UserAware)action).setLoggedInUser((Users)actionInvocation.getInvocationContext().getSession().get(GenericConstants.SESSION_USER));
		}
		
		if(action instanceof BaseAction)
		{
			((BaseAction)action).setActionInvocation(actionInvocation);
			((BaseAction)action).resetModel();
			((BaseAction)action).clearActionErrors();
			((BaseAction)action).clearMessages();
			((BaseAction)action).clearFieldErrors();
			((BaseAction)action).clearErrorsAndMessages();
			((BaseAction)action).clearErrors();
		}
		
		return actionInvocation.invoke();
	}
	
		public void setServletRequest(HttpServletRequest request) {       
		this.request = request;    
		}
		   

		public void setServletResponse(HttpServletResponse response) {       
		this.response = response;    
		}
		   

		public HttpServletRequest getServletRequest(){       
		return request;    
		}
		   

		public HttpServletResponse getServletResponse(){       
		return response;    
		}
		

}
