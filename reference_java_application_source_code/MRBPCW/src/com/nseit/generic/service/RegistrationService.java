/**
 * RegistrationService for Registration Process 
 */

package com.nseit.generic.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nseit.generic.models.RegistrationBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.GenericServiceException;
import com.nseit.generic.util.LogicError;

public interface RegistrationService {

	public enum registrationServiceConstants {
		INSERT, UPDATE, IMAGE_NOT_MATCH, USER_EXIST, EMAIL_EXISTS, MOBILE_EXISTS, INVALIDOTP, NONE, EMAIL_NOT_EXISTS,MOBILE_NOT_EXISTS, INVALIDQST, SEND_USERID_LINK_TO_REGMAIL, INVALID_EMAIL, INVALID_USERID, INVALID_EMAIL_FOR_USER, SEND_PWD_LINK_TO_REGMAIL, RESULT, ERROR, SUCCESS, ATTEMPT_EXCEEDED
	}

	public int forgotPassword(RegistrationBean registrationBean) throws Exception;

	public int updatePasswordDetails(Users users, RegistrationBean registrationBean) throws Exception;

	public int updatePasswordForFirstTime(RegistrationBean registrationBean) throws Exception;

	public int insertUserDetails(RegistrationBean registrationBean) throws Exception;

	public boolean checkIfUserIdExists(RegistrationBean registrationBean);

	public boolean checkIfUserIdEmailIDExists(RegistrationBean registrationBean);

	public RegistrationBean generateUserID(RegistrationBean registrationBean);

	public String getOTP(RegistrationBean registrationBean);

	public String checkOTP(RegistrationBean registrationBean) throws Exception;

	public registrationServiceConstants register(RegistrationBean registrationBean, HttpServletRequest request) throws GenericException, LogicError, Exception;

	public registrationServiceConstants sendUserIdLinkToRegMail(RegistrationBean registrationBean, HttpServletRequest request) throws GenericException, LogicError, Exception;

	public registrationServiceConstants sendForgotPasswordLinkToRegMail(RegistrationBean registrationBean, HttpServletRequest request)
			throws GenericException, LogicError, Exception;

	public boolean checkUserExist(RegistrationBean registrationBean, String flag) throws Exception;

	public boolean checkOTP(RegistrationBean registrationBean, String otpflag);

	public Map<String, String> getAgeMatrixDetails(String post_selected) throws Exception;

	public RegistrationBean checkMobNoOTP(RegistrationBean registrationBean) throws GenericException;

	public int updateMOBOTPDetails(String mobileNo1, String otp, boolean flag) throws GenericServiceException; //, String courseVal

	public int insertMOBDetails(String mobileNo1, String otp) throws GenericServiceException; //, String courseVal

	public String checkIfUserDetailsExist(RegistrationBean registrationBean);
	
	public int checkIfMobOrEmailIsAlreadyVerified(String mobileNo , String emailAddress);

	public int verifyEmailExists(String emailAddress);

	public boolean verifyOtp(String enteredOtp, String mobileNo);

	public int checkEmailId(RegistrationBean registrationBean) throws Exception;

	public String checkEmailOTP(RegistrationBean registrationBean) throws Exception;

	public int insertOTPDetails(String emailId, String otp, int pk_of_transaction)throws Exception;

	public int updateOTPDetails(String emailId, String otp, boolean flag) throws Exception;
	
	public String getExistingOTP(String emailId) throws Exception;

	boolean checkFpOTP(RegistrationBean registrationBean, String otpflag);

	public boolean checkNewOldPasswordMatch(RegistrationBean registrationBean) throws Exception;

	public String getUserFK(RegistrationBean registrationBean) throws Exception;

	String generateFpOTP(String mobile, String username, String choice);

	boolean checkIfUserIdMobileNoExists(RegistrationBean registrationBean);
	
	boolean checkIfEmailExistsForUserId(RegistrationBean registrationBean);

	public int checkAttemptCount(String mobileNo);

	public Map<String, String> getAgeMatrixDetails(int post_selected,String exserviceman,String disability,int category,String destitute)throws Exception;
	
	public String generateFpOTPEmail(String emailId, String userName);
}
