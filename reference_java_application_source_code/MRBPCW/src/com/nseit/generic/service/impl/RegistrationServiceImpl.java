/***
 * RegistrationServiceImpl for Implements RegistrationService
 */
package com.nseit.generic.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.apache.struts2.dispatcher.HttpParameters;

import com.nseit.generic.action.BaseAction;
import com.nseit.generic.dao.CommonDao;
import com.nseit.generic.dao.LoginDao;
import com.nseit.generic.dao.RegistraionDAO;
import com.nseit.generic.models.LoginBean;
import com.nseit.generic.models.RegistrationBean;
import com.nseit.generic.models.SessionDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.RegistrationService;
import com.nseit.generic.util.AuditTrailConstants;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.DESEncrypter;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.GenericServiceException;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.LogicError;
import com.nseit.generic.util.emailsms.EmailSMSUtil;

public class RegistrationServiceImpl extends BaseAction implements RegistrationService {
	private static final long serialVersionUID = 1L;
	public RegistraionDAO registraionDAO;
	public CommonDao commonDao;
	private LoginDao loginDao;
	private Logger logger = LoggerHome.getLogger(getClass());
	protected HttpServletRequest request;

	@Override
	public void resetModel() {
		// TODO Auto-generated method stub
	}

	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}

	public void setRegistraionDAO(RegistraionDAO registraionDAO) {
		this.registraionDAO = registraionDAO;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public int forgotPassword(RegistrationBean registrationBean) throws Exception {
		int count = registraionDAO.forgotPassword(registrationBean);
		return count;
	}

	public int updatePasswordDetails(Users user, RegistrationBean registrationBean) throws Exception {
		int updatePassword = 0;
		try {
			registrationBean.setNewPassword(DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).encrypt(registrationBean.getNewPassword()));
			registrationBean.setOldPassword(DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).encrypt(registrationBean.getOldPassword()));
			updatePassword = registraionDAO.updatePasswordDetails(user, registrationBean);
		} catch (Exception ex) {
			throw new GenericServiceException(ex);
		}
		return updatePassword;
	}

	public int updatePasswordForFirstTime(RegistrationBean registrationBean) throws Exception {
		int updatePwd = 0;
		DESEncrypter desEncrypter = DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey());
		try {
			registrationBean.setNewPassword(desEncrypter.encrypt(registrationBean.getNewPassword()));
			registrationBean.setOldPassword(desEncrypter.encrypt(registrationBean.getOldPassword()));
			updatePwd = registraionDAO.updatePasswordForFirstTime(registrationBean);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return updatePwd;
	}

	public int insertUserDetails(RegistrationBean registrationBean) throws Exception {
		return registraionDAO.insertUserDetails(registrationBean);
	}

	public boolean checkIfUserIdExists(RegistrationBean registrationBean) {
		return registraionDAO.checkIfUserIdExists(registrationBean);
	}

	@Override
	public boolean checkIfUserIdEmailIDExists(RegistrationBean registrationBean) {
		return registraionDAO.checkIfUserIdEmailIDExists(registrationBean);
	}

	public RegistrationBean generateUserID(RegistrationBean registrationBean) {
		return registraionDAO.generateUserID(registrationBean);
	}

	@Override
	public String checkOTP(RegistrationBean registrationBean) throws Exception {
		return registraionDAO.checkOTP(registrationBean);
	}

	@Override
	public String getOTP(RegistrationBean registrationBean) {
		return registraionDAO.getOTPInEmailOtpMaster(registrationBean, "");
	}

	private void createAudittrail(String key, String value, StringBuffer result, boolean concat) {
		result.append(key);
		result.append(":");
		result.append(value);
		if (concat) {
			result.append("~");
		}
	}
	@Override
	public registrationServiceConstants register(RegistrationBean registrationBean, HttpServletRequest request) throws LogicError, GenericException, Exception {
		registrationBean.setNewPassword(registrationBean.getNewPassword());
		try {
			String age = registrationBean.getAgeInYears() + " years "+registrationBean.getAgeInMonths() +" months "+registrationBean.getAgeInDays()+ " days";
			
			int insertCandidateDetails = registraionDAO.insertUserDetails(registrationBean);
			
			if (insertCandidateDetails > 0) {
				registraionDAO.updateOtpStatus(registrationBean); // update otp as verified
				
				String userPkForUser = registraionDAO.getUserPkForUser(registrationBean.getUserName());
				
				//RegistrationBean newRegistrationBean = getSystemGeneratedUserID(registrationBean);
				String status = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.NEW_REGISTERATION) + "";
				Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.NEW_REGISTERATION);
				Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.NEW_REGISTERATION);
				EmailSMSUtil.insertEmailNSMSForNewRegisteration(emailReq, smsReq, status, registrationBean);

				registrationBean.setUserId(userPkForUser);
				registrationBean.setUserName(registrationBean.getUserName());
				Users users = new Users();
				users.setRemoteIp(request.getRemoteAddr());
				users.setUsername(registrationBean.getUserName());
				if (null != registrationBean.getUserId()) {
					users.setUserId(Long.parseLong(registrationBean.getUserId()));
				}
				StringBuffer newaudit = new StringBuffer();
				//createAudittrail(AuditTrailConstants.REGISTRATION_PAGE,registrationBean.getUserName(),newaudit,true);
				createAudittrail(AuditTrailConstants.CANDIDATE_ID,registrationBean.getUserName(),newaudit,true);
				createAudittrail(AuditTrailConstants.POST_APPLIED,registrationBean.getCourses(),newaudit,true);
				createAudittrail(AuditTrailConstants.COMPLETE_NAME,registrationBean.getCandidateName(),newaudit,true);
				createAudittrail(AuditTrailConstants.NATIONALITY,ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(registrationBean.getNationVal())),newaudit,true);
				createAudittrail(AuditTrailConstants.GENDER,ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(registrationBean.getGender())),newaudit,true);
				createAudittrail(AuditTrailConstants.EXSERVICEMAN_YESNO,ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(registrationBean.getExServiceMen())),newaudit,true);
				createAudittrail(AuditTrailConstants.DISCHARGE_DATE,registrationBean.getDischargeDate(),newaudit,true);
				createAudittrail(AuditTrailConstants.PPONUMBER,registrationBean.getPpoNumber(),newaudit,true);
				createAudittrail(AuditTrailConstants.COMMUNITY_YESNO,ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(registrationBean.getCommCertYesNo())),newaudit,true);
				if(Strings.isNotBlank(registrationBean.getCommCertYesNo()) && (registrationBean.getCommCertYesNo().equals("7") || registrationBean.getCommCertYesNo().equals("No")))
					createAudittrail(AuditTrailConstants.COMMUNITY,registrationBean.getCategoryVal1(),newaudit,true);
				else
					createAudittrail(AuditTrailConstants.COMMUNITY,registrationBean.getCommunity(),newaudit,true);
				createAudittrail(AuditTrailConstants.SUBCASTE,registrationBean.getSubCaste(),newaudit,true);
				createAudittrail(AuditTrailConstants.COMM_CERT,registrationBean.getIssueAuthCommCert(),newaudit,true);
				createAudittrail(AuditTrailConstants.COMM_CERT_NO,registrationBean.getCommCertNo(),newaudit,true);
				createAudittrail(AuditTrailConstants.COMM_CERT_PLACE,registrationBean.getCommCertPlace(),newaudit,true);
				createAudittrail(AuditTrailConstants.COMM_CERT_DATE,registrationBean.getCommIssueDate(),newaudit,true);
				createAudittrail(AuditTrailConstants.DISABLITY_YESNO,ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(registrationBean.getDisableYesNo())),newaudit,true);
				if(registrationBean.isDiffAbledChkBox()) {
					createAudittrail(AuditTrailConstants.DISABLED_CHKBOX,6+"",newaudit,true);
				}else {
					createAudittrail(AuditTrailConstants.DISABLED_CHKBOX,7+"",newaudit,true);
				}
				createAudittrail(AuditTrailConstants.DISABILITY_CATEGORY,registrationBean.getWidowYesNo(),newaudit,true);
				if(registrationBean.getWidowYesNo().equals("6"))
				{
					createAudittrail(AuditTrailConstants.WIDOW_CERT_NO,registrationBean.getDesWidowCertNo(),newaudit,true);
					createAudittrail(AuditTrailConstants.WIDOW_ISSUE_DATE,registrationBean.getWidowIssueDate(),newaudit,true);
					createAudittrail(AuditTrailConstants.WIDOW_ISSUE_AUTHORITY,registrationBean.getWidowIssueAuth(),newaudit,true);
					createAudittrail(AuditTrailConstants.WIDOW_DISTRICT,registrationBean.getWidowDistrict(),newaudit,true);
					createAudittrail(AuditTrailConstants.WIDOW_OTHER_DISTRICT,registrationBean.getWidowOtherDistrict(),newaudit,true);
					createAudittrail(AuditTrailConstants.WIDOW_SUBDIVISION,registrationBean.getWidowSubDivision(),newaudit,true);
					if(registrationBean.isWidowCheckbox()) {
						createAudittrail(AuditTrailConstants.DISABLED_CHKBOX,6+"",newaudit,true);
	
					}else {
						createAudittrail(AuditTrailConstants.DISABLED_CHKBOX,7+"",newaudit,true);
					}
				}
				createAudittrail(AuditTrailConstants.DISABILITY_CATEGORY,registrationBean.getDisableType(),newaudit,true);
				createAudittrail(AuditTrailConstants.DISABILITY_PERCENT,registrationBean.getDisablityPercent(),newaudit,true);
				createAudittrail(AuditTrailConstants.DOB,registrationBean.getDateOfBirth(),newaudit,true);
				createAudittrail(AuditTrailConstants.AGE_AS_ON,ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AGE_AS_ON),newaudit,true);
				createAudittrail(AuditTrailConstants.AGE_AS_ON_DURATION,age,newaudit,true);
				createAudittrail(AuditTrailConstants.EMAIL,registrationBean.getEmailAddress(),newaudit,true);
				createAudittrail(AuditTrailConstants.MOBILENO,registrationBean.getMobileNo(),newaudit,false);
				
				String browserVersion = (String) request.getSession().getAttribute("BROWSER_VERSION");
				logger.info(registrationBean.getUserName() + ", " + request.getRemoteAddr()
				+ ", Successfully Registered , " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss")+"browserVersion :" + browserVersion + " User ID:" + registrationBean.getUserName());
				request.setAttribute("SysGenUserID", registrationBean.getUserName());
				request.setAttribute("IsRegistered", "Y");
				commonDao.insertCandidateAuditrail(users, "User Browser", "Candidate with User Id " + registrationBean.getUserName()
						+ " login Successfully. browserVersion :" + browserVersion);
				commonDao.insertCandidateAuditrail(users, "Nationality Confirmation", "Candidate clicked OK on confirmation window for Nationality");
				commonDao.insertCandidateAuditrail(users, AuditTrailConstants.REG_PAGE_SUBMIT,newaudit.toString());
				return registrationServiceConstants.SUCCESS;
			} else {
				return registrationServiceConstants.ERROR;
			}
		} catch (Exception ex) {
			logger.error(ex,ex);
			return registrationServiceConstants.ERROR;
		}
	}

	@Override
	public registrationServiceConstants sendUserIdLinkToRegMail(RegistrationBean registrationBean, HttpServletRequest request) throws GenericException, LogicError, Exception {
		String choice = request.getParameter("choice");
		registrationServiceConstants result = null;
		int count = 0;
		String userIdExist = registraionDAO.getUserIdexistDtls(registrationBean, choice);
		if (userIdExist != null)
			count = registraionDAO.updateSentCount(userIdExist);

		if (count < 1) {
			return registrationServiceConstants.ATTEMPT_EXCEEDED;
		}
		
		if (StringUtils.isBlank(userIdExist)) {
			if ("email".equals(choice)) {
				result = registrationServiceConstants.EMAIL_NOT_EXISTS;
			}
			if ("mobile".equals(choice)) {
				result = registrationServiceConstants.MOBILE_NOT_EXISTS;
			}
		}
		else {

			registrationBean.setUserId(userIdExist);

			Users users = new Users();
			users.setUsername(userIdExist);

			String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.FORGOT_USER_ID_STATUS) + "";
			ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger()
					.info(users.getUsername() + ", " + request.getRemoteAddr() + ", Forgot UserId , " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
			users.setRemoteIp(request.getRemoteAddr());
			String userFK = registraionDAO.getUserFK(registrationBean);
			users.setUserId(Long.parseLong(userFK));

			Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.FORGOT_USER_ID_STATUS);

			Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.FORGOT_USER_ID_STATUS);

			EmailSMSUtil.insertEmailNSMSForForgotUserId(ConfigurationConstants.getInstance().getActivtyKeyForName(GenericConstants.FORGOT_USER_ID_STATUS), users.getUsername(),
					users.getUsername(), staus, users, emailReq, smsReq);

			commonDao.insertCandidateAuditrail(users, "Forgot User ID", "Candidate with User Id " + users.getUsername() + " has forgot his user id.");

			SessionDetailsBean sessionDetailsBean = new SessionDetailsBean();
			sessionDetailsBean.setOUM_STATUS("A");
			sessionDetailsBean.setOUM_INVAL_ATTEMPT_COUNT(0);
			sessionDetailsBean.setOUM_USER_ID(users.getUsername());

			loginDao.updateUserStatus(sessionDetailsBean);

			result = registrationServiceConstants.SEND_USERID_LINK_TO_REGMAIL;

		}
		return result;
	}

	@Override
	public registrationServiceConstants sendForgotPasswordLinkToRegMail(RegistrationBean registrationBean, HttpServletRequest request)
			throws GenericException, LogicError, Exception {
		Users loggedInUser = new Users();
		loggedInUser = loginDao.getUserForUsername(registrationBean.getUserId().trim().toUpperCase());
		loggedInUser = loginDao.getUserFromEmail(registrationBean.getEmailAddress());

		if (StringUtils.isBlank(loggedInUser.getUsername().trim())) {
			return registrationServiceConstants.INVALID_USERID;
		} else if (StringUtils.isBlank(registrationBean.getEmailAddress().trim())) {
			return registrationServiceConstants.INVALID_EMAIL_FOR_USER;
		}
		registrationBean.setUserId(loggedInUser.getUsername());

		String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.FORGOT_PASSWORD_STATUS) + "";

		ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger()
				.info(loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ", Forgot Password , " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
		loggedInUser.setRemoteIp(request.getRemoteAddr());
		// String userFK = Long.toString(loggedInUser.getUserId());
		// EmailSMSUtil.insertEmailNSMSForChangePassword(registrationBean.getUserId(), registrationBean.getEmailAddress();
		Integer emailReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.FORGOT_PASSWORD_STATUS);
		Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.FORGOT_PASSWORD_STATUS);
		EmailSMSUtil.insertEmailNSMSForForgotPwd(ConfigurationConstants.getInstance().getActivtyKeyForName(GenericConstants.FORGOT_PASSWORD_STATUS), loggedInUser.getUsername(),
				loggedInUser.getUsername(), staus, loggedInUser, emailReq, smsReq, registrationBean);

		commonDao.insertCandidateAuditrail(loggedInUser, "Forgot Password", "Candidate with User Id " + loggedInUser.getUsername() + " has forgot his password.");

		LoginBean bean = new LoginBean();
		bean.setActiveStatus("A");
		bean.setInvalidCount(0);
		bean.setUsername(loggedInUser.getUsername());
		loginDao.updateUserStatus(bean);
		return registrationServiceConstants.SEND_PWD_LINK_TO_REGMAIL;

		// loggedInUser.setUserFk(Long.parseLong(userFK));
		// registrationBean.setUserId(userFK);
		// registrationBean.setUserName(loggedInUser.getUsername());
		// registrationBean.setMobileNo(loggedInUser.getMobile());
		// if (loggedInUser.getDisciplineID() != null && !loggedInUser.getDisciplineID().equals("")) {
		// EmailSMSUtil.insertEmailChangePassword(registrationBean.getUserId(), registrationBean.getEmailAddress());
		// // for sms below code
		// }
		// return registrationServiceConstants.SEND_PWD_LINK_TO_REGMAIL;
	}

	@Override
	public boolean checkUserExist(RegistrationBean registrationBean, String flag) throws Exception {
		return registraionDAO.checkUserExist(registrationBean, flag);
	}

	public boolean checkOTP(RegistrationBean registrationBean, String otpflag) {
		return registraionDAO.checkOTP(registrationBean, otpflag);
	}

	@Override
	public Map<String, String> getAgeMatrixDetails(String post_selected) throws Exception {
		return registraionDAO.getAgeMatrixDetails(post_selected);
	}

	private RegistrationBean getSystemGeneratedUserID(RegistrationBean registrationBean) {
		try {
			return registraionDAO.generateUserID(registrationBean);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return null;
	}

	@Override
	public RegistrationBean checkMobNoOTP(RegistrationBean registrationBean) throws GenericException {
		return registraionDAO.checkMobNoOTP(registrationBean);
	}

	@Override
	public int updateMOBOTPDetails(String mobileNo1, String otp, boolean flag) throws GenericServiceException { // , String courseVal
		try {
			return registraionDAO.updateMOBOTPDetails(mobileNo1, otp, flag); // , courseVal
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
	}

	@Override
	public int insertMOBDetails(String mobileNo1, String otp) throws GenericServiceException { // , String courseVal
		try {
			return registraionDAO.insertMOBDetails(mobileNo1, otp); // , courseVal
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
	}

	@Override
	public String checkIfUserDetailsExist(RegistrationBean registrationBean) {
		return registraionDAO.checkIfUserDetailsExist(registrationBean);
	}
	
	@Override
	public int checkIfMobOrEmailIsAlreadyVerified(String mobileNo , String emailAddress) {
		return registraionDAO.checkIfMobOrEmailIsAlreadyVerified(mobileNo,emailAddress);
	}

	@Override
	public int verifyEmailExists(String emailAddress) {
		return registraionDAO.verifyEmailExists(emailAddress);
	}

	@Override
	public boolean verifyOtp(String enteredOtp, String mobileNo) {
		boolean result = registraionDAO.verifyOtp(enteredOtp, mobileNo);
		return result;
	}

	@Override
	public int checkAttemptCount(String mobileNo) {
		return registraionDAO.checkAttemptCount(mobileNo);
	}

	@Override
	public void withSession(Map<String, Object> sessionAttributes) {
		this.sessionAttributes = sessionAttributes;

	}

	@Override
	public void withParameters(HttpParameters httpParameters) {
		this.httpParameters = httpParameters;

	}

	@Override
	public void withServletRequest(HttpServletRequest request) {
		this.request = request;

	}

	@Override
	public void withServletResponse(HttpServletResponse response) {
		this.response = response;

	}

	@Override
	public int checkEmailId(RegistrationBean registrationBean) throws Exception {
		return registraionDAO.checkEmailID(registrationBean);
	}

	@Override
	public String checkEmailOTP(RegistrationBean registrationBean) throws Exception {
		return registraionDAO.checkEmailOTP(registrationBean);
	}

	@Override
	public int insertOTPDetails(String email, String otp, int pk) throws Exception {
		try {
			return registraionDAO.insertOTPDetails(email, otp, pk);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
	}

	@Override
	public int updateOTPDetails(String email, String otp, boolean flag) throws Exception {
		try {
			return registraionDAO.updateOTPDetails(email, otp, flag);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
	}

	@Override
	public String getExistingOTP(String email) throws Exception {
		String existingOTP = "";
		try {
			existingOTP = registraionDAO.getExistingOTP(email);
		} catch (Exception e) {
			existingOTP = "";
		}
		return existingOTP;
	}

	@Override
	public boolean checkFpOTP(RegistrationBean registrationBean, String otpflag) {
		return registraionDAO.checkFpOTP(registrationBean, otpflag);
	}

	@Override
	public boolean checkNewOldPasswordMatch(RegistrationBean registrationBean) throws Exception {
		boolean count = false;
		try {
			count = registraionDAO.checkNewOldPasswordMatch(registrationBean);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		return count;
	}

	public String getUserFK(RegistrationBean registrationBean) throws Exception {
		String userfk = registraionDAO.getUserFK(registrationBean);
		return userfk;
	}

	@Override
	public String generateFpOTP(String mobile, String username, String choice) {
		int length = 6;
		try {
			String otp = null;
			// for mobile number
			boolean mobExists = registraionDAO.checkFpMobNoOTP(mobile, username);
			if (mobExists) {
				otp = generateRandomOTP(length).toString();

				int otpUpdatecount = 0, transInsertCount = 0;
				otpUpdatecount = registraionDAO.updateFpMobOTPDetails(mobile, otp);

				if (otpUpdatecount > 0) {
					if("email".equals(choice)) {
						transInsertCount = EmailSMSUtil.insertEmailSmsForForgotPassword(1,0, 
								username, mobile, otp);
						}
						if("mobile".equals(choice)) {
							transInsertCount = EmailSMSUtil.insertEmailSmsForForgotPassword(0,1, 
									username, mobile, otp);
						}
				}
				if (transInsertCount > 0) {
					return otp;
				} else {
					// error updating records
					logger.info("Error inserting FP OTP sms content in transmaster for Update in regservice generateFpOTP");
					return null;
				}
			} else {
				// generate new otp
				otp = generateRandomOTP(length).toString();
				int otpInsertcount = 0, transInsertCount = 0;
				otpInsertcount = registraionDAO.insertFpMobDetails(mobile, otp, username); //

				if (otpInsertcount > 0) {
					if("email".equals(choice)) {
						transInsertCount = EmailSMSUtil.insertEmailSmsForForgotPassword(1,0, 
								username, mobile, otp);
						}
						if("mobile".equals(choice)) {
							transInsertCount = EmailSMSUtil.insertEmailSmsForForgotPassword(0,1, 
									username, mobile, otp);
						}
				}
				if (transInsertCount > 0) {
					return otp;
				} else {
					// error updating records
					logger.info("Error inserting FP OTP sms content in transmaster for Insert in regservice generateFpOTP");
					return null;
				}
			}
		} catch (Exception e) {
			logger.fatal(e, e);
			return null;
		}
	}

	@Override
	public boolean checkIfUserIdMobileNoExists(RegistrationBean registrationBean) {
		return registraionDAO.checkIfUserIdMobileNoExists(registrationBean);
	}
	
	@Override
	public boolean checkIfEmailExistsForUserId(RegistrationBean registrationBean) {
		return registraionDAO.checkIfEmailExistsForUserId(registrationBean);
	}

	static String generateRandomOTP(int len) {
		// Using numeric values
		String numbers = "0123456789";

		// Using random method
		Random rndm_method = new Random();

		char[] otp = new char[len];
		StringBuilder otp1 = new StringBuilder("");
		for (int i = 0; i < len; i++) {
			// Use of charAt() method : to get character value
			// Use of nextInt() as it is scanning the value as int
			otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
			otp1.append(numbers.charAt(rndm_method.nextInt(numbers.length())));
		}
		return otp1.toString();
	}
	
	@Override
	public Map<String, String> getAgeMatrixDetails(int post_selected,String exserviceman,String disability,int category,String destitute) throws Exception {
		return registraionDAO.getAgeMatrixDetails(post_selected,exserviceman,disability,category,destitute);
	}
	
	@Override
	public String generateFpOTPEmail(String email, String username) {
          return "";
        		  /*int length = 6;
		try {
			String otp = null;
			// for mobile number
			boolean mobExists = registraionDAO.checkFpEmailNoOTP(email, username);
			if (mobExists) {
				otp = generateRandomOTP(length).toString();

				int otpUpdatecount = 0, transInsertCount = 0;
				otpUpdatecount = registraionDAO.updateFpEmailOTPDetails(email, otp);

				if (otpUpdatecount > 0) {
					transInsertCount = EmailSMSUtil.insertEmailForFpOTP(otp, email, username);
				}
				if (transInsertCount > 0) {
					return otp;
				} else {
					// error updating records
					logger.info("Error inserting FP OTP email content in transmaster for Update in regservice generateFpOTP");
					return null;
				}
			} else {
				// generate new otp
				otp = generateRandomOTP(length).toString();
				int otpInsertcount = 0, transInsertCount = 0;
				otpInsertcount = registraionDAO.insertFpEmailDetails(email, otp, username); //

				if (otpInsertcount > 0) {
					transInsertCount = EmailSMSUtil.insertEmailForFpOTP(otp, email, username); //
				}
				if (transInsertCount > 0) {
					return otp;
				} else {
					// error updating records
					logger.info("Error inserting FP OTP email content in transmaster for Insert in regservice generateFpOTP");
					return null;
				}
			}
		} catch (Exception e) {
			logger.fatal(e, e);
			return null;
		}
	
	*/}

}