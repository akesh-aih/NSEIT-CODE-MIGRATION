package com.nseit.generic.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nseit.generic.models.LoginBean;
import com.nseit.generic.models.RegistrationBean;
import com.nseit.generic.models.SessionDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.util.GenericException;

public interface LoginService
{
  public enum loginServiceConstants {
    SESSIONERROR,
    ACCOUNT_LOCK,
    INVALID_LOGIN_CREDENTIALS,
    INSTRUCTION_PAGE,
    CLOSE_WINDOW,
    SUCCESS_AGENCY,
    SUCCESS_NSEIT,
    SUCCESS_QST_BANK,
    SUCCESS_NODAL,
    SUCCESS_USER,
    ERROR,
    INPUT,
    REDIRECT_ACTION_URL,
    NONE, 
    BLOCKED_USER,
  }
 
	public Users getUserForUsername(String username)throws Exception;
	public boolean checkForgotPassword(String Userid)throws Exception;
	public int  changePassword(RegistrationBean registrationBean) throws Exception;
	public String checkForgotPasswordKey(String Userid)throws Exception;
	public boolean oldPassword(RegistrationBean registrationBean) throws Exception;
	public loginServiceConstants authenticate(LoginBean bean,HttpServletRequest request, Map<String, Object> sessionAttributes,HttpServletResponse response,Map<String,Object> requeAttribute) throws GenericException, Exception;
	public String getFirstTimeLoggedUser(Long userFk);
	public Users getUser(String username) throws Exception;
	public String getStatusForUsername(String username);

	//public Map<Integer, String> getEducationMajorSubjectList(Users loggedInUser, LoginBean loginBean) throws Exception;
}
