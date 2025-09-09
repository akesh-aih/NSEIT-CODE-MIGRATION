package com.nseit.generic.dao;

import com.nseit.generic.models.LoginBean;
import com.nseit.generic.models.RegistrationBean;
import com.nseit.generic.models.SessionDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.util.GenericException;

public interface LoginDao
{
	public Users getUserForUsername(String username) throws Exception;
	public String getStatusForUsername(String username);
	public Users getUserFromEmail(String emailAddress) throws Exception;
	public boolean checkForgotPassword(String Userid)throws Exception;
	public int  changePassword(RegistrationBean registrationBean) throws Exception;
	public String checkForgotPasswordKey(String Userid)throws Exception;
	public boolean oldPassword(RegistrationBean registrationBean) throws Exception;
	public int updateUserStatus(LoginBean loginBean) throws GenericException;
	public int updateUserStatus(SessionDetailsBean sessionDetailsBean) throws GenericException;
	public String getFirstTimeLoggedUser(Long userFk);
	//public Map<Integer, String> getEducationMajorSubjectList(Users loggedInUser, LoginBean loginBean) throws Exception;
	
}
