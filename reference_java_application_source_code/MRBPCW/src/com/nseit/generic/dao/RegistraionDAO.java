package com.nseit.generic.dao;

import java.util.List;
import java.util.Map;

import com.nseit.generic.models.RegistrationBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.util.GenericException;

public interface RegistraionDAO {
	public int updatePasswordDetails(Users users, RegistrationBean registrationBean) throws Exception;

	public int updatePasswordForFirstTime(RegistrationBean registrationBean) throws Exception;

	public int forgotPassword(RegistrationBean registrationBean) throws Exception;

	public int insertUserDetails(RegistrationBean registrationBean) throws Exception;
	
	public String getUserPkForUser(String username);

	public boolean checkIfUserIdExists(RegistrationBean registrationBean);

	public boolean checkIfUserIdEmailIDExists(RegistrationBean registrationBean);

	public RegistrationBean generateUserID(RegistrationBean registrationBean);

	public boolean checkPostExist(RegistrationBean registrationBean);

	public String getMobileNoInUserMater(String userid);

	public String checkOTP(RegistrationBean registrationBean) throws Exception;

	public String getOTPInEmailOtpMaster(RegistrationBean registrationBean, String otpflag);

	public boolean checkUserExist(RegistrationBean registrationBean, String flag) throws Exception;

	public boolean checkOTP(RegistrationBean registrationBean, String otpflag);

	public Map<String, String> getAgeMatrixDetails(String post_selected) throws Exception;

	public Long getRandomSequence() throws Exception;

	public RegistrationBean checkMobNoOTP(RegistrationBean registrationBean) throws GenericException;

	public int updateMOBOTPDetails(String mobileNo1, String otp, boolean flag) throws Exception; //, String courseVal

	public int insertMOBDetails(String mobileNo1, String otp) throws Exception;  //, String courseVal

	public String checkIfUserDetailsExist(RegistrationBean registrationBean);
	
	public int checkIfMobOrEmailIsAlreadyVerified(String mobileNo , String emailAddress);

	public void updateOtpStatus(RegistrationBean registrationBean);
	
	public int verifyEmailExists(String emailAddress);

	public boolean verifyOtp(String enteredOtp, String mobileNo);
	
	public Long getSequence1() throws Exception;

	public int checkEmailID(RegistrationBean registrationBean) throws Exception;

	public String checkEmailOTP(RegistrationBean registrationBean) throws Exception;

	public int insertOTPDetails(String email, String otp, int pk) throws Exception;

	public int updateOTPDetails(String email, String otp, boolean flag) throws Exception;
	
	public String getExistingOTP(String email) throws Exception;

	boolean checkEmialID(RegistrationBean registrationBean);

	boolean checkMobileNo(RegistrationBean registrationBean);

	public String getUserIdexistDtls(RegistrationBean registrationBean, String choice) throws Exception;

	public String getUserFK(RegistrationBean registrationBean) throws Exception;

	boolean checkFpOTP(RegistrationBean regBean, String otpflag);

	public boolean checkNewOldPasswordMatch(RegistrationBean registrationBean) throws Exception;

	public boolean checkFpMobNoOTP(String mobile, String username) throws Exception;

	public int updateFpMobOTPDetails(String mobile, String otp) throws Exception;

	public int insertFpMobDetails(String mobileNo1, String otp, String userID) throws Exception; 

	boolean checkIfUserIdMobileNoExists(RegistrationBean registrationBean);
	
	boolean checkIfEmailExistsForUserId(RegistrationBean registrationBean);

	public int checkAttemptCount(String mobileNo);

	public int updateSentCount(String username);
	
	//public Long getSequence2() throws Exception;
	
	public Map<String, String> getAgeMatrixDetails(int post_selected,String exserviceman,String disability,int category,String desti) throws Exception;
	
	public boolean checkFpEmailNoOTP(String email, String username);

	public int updateFpEmailOTPDetails(String email, String otp) throws Exception;

	public int insertFpEmailDetails(String email, String otp, String username) throws Exception;
}
