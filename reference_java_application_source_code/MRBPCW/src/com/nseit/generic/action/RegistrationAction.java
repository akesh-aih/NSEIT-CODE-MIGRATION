package com.nseit.generic.action;

// RegistrationAction for Registration Process 
import static com.nseit.generic.util.GenericConstants.GLOBAL_PLAIN_TEXT_MESSAGE;
import static com.nseit.generic.util.GenericConstants.SESSION_USER;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.apache.struts2.dispatcher.HttpParameters;
import org.apache.struts2.util.TokenHelper;

import com.nseit.generic.models.RegistrationBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.CommonService;
import com.nseit.generic.service.LoginService;
import com.nseit.generic.service.RegistrationService;
import com.nseit.generic.service.RegistrationService.registrationServiceConstants;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.EmailValidator;
import com.nseit.generic.util.ExceptionStacktrace;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.GenericServiceException;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.LogicError;
import com.nseit.generic.util.MobileValidator;
import com.nseit.generic.util.ValidatorUtil;
import com.nseit.generic.util.aware.UserAware;
import com.nseit.generic.util.emailsms.EmailSMSUtil;
import com.nseit.generic.util.resource.ResourceUtil;
import com.nseit.generic.util.resource.ValidationMessageConstants;
import com.nseit.generic.util.validation.RegistrationBeanValidator;
import com.opensymphony.xwork2.ModelDriven;

import lombok.Getter;
import lombok.Setter;

public class RegistrationAction extends BaseAction implements ModelDriven<RegistrationBean>, UserAware {

	private static final long serialVersionUID = 5594900346849746082L;
	private Logger logger = LoggerHome.getLogger(getClass());
	private RegistrationService registrationService;
	private LoginService loginService;
	@Getter
	@Setter
	private RegistrationBean registrationBean ;
	private CommonService commonService;
	protected HttpServletRequest request;
	@Getter
	@Setter
	private RegistrationBeanValidator registrationBeanValidator;
	
	@Override
	public RegistrationBean getModel() {
		// TODO Auto-generated method stub
		return this.registrationBean;
	}

	public void setRegistrationService(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	
	public String updatePasswordDetailsValidation() {
		List<String> errorList = new ArrayList<String>();
		String errors = "";

		if (ValidatorUtil.isEmpty(loggedInUser.getUsername()))
			errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.LOGIN_USERNAME_REQUIRED));

		if (ValidatorUtil.isEmpty(registrationBean.getOldPassword()))
			errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.OLD_PASSWORD));

		if (ValidatorUtil.isEmpty(registrationBean.getNewPassword()))
			errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.NEW_PASSWORD));

		if (!ValidatorUtil.isEmpty(registrationBean.getOldPassword()) && !ValidatorUtil.isEmpty(registrationBean.getNewPassword()))
			if (registrationBean.getOldPassword().compareTo(registrationBean.getNewPassword()) == 0)
				errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.USE_DIFFERENT_PASSWORD));

		if (errorList.size() > 0)
			errors = ValidatorUtil.validationMessageFormatter(errorList);

		return errors;
	}

	// Update Password Details
	public String updatePasswordDetails() throws Exception {
		String result = null;
		String errors = "";
		int updatePassword = 0;
		String sa = request.getParameter("sa").trim();
		String iv = request.getParameter("iv").trim();
		SecretKey key;
		// Added for Encryption/Decryption Password NPCIL(VAPT)[START]
		byte[] ivBytes = CommonUtil.hexStringToByteArray(iv);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
		System.out.println("token" + TokenHelper.getToken());
		errors = updatePasswordDetailsValidation();
		if (!ValidatorUtil.isEmpty(errors)) {
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "9," + errors);
			return "writePlainText";
		}
		String lLoginId1 = request.getParameter("loginId");
		String oldPassword = "";
		String newPassword = "";

		key = CommonUtil.generateKeyFromPassword(iv, CommonUtil.hexStringToByteArray(sa));
		oldPassword = CommonUtil.getDecryptedPassword(registrationBean.getOldPassword(), (SecretKeySpec) key, ivParameterSpec);
		newPassword = CommonUtil.getDecryptedPassword(registrationBean.getNewPassword(), (SecretKeySpec) key, ivParameterSpec);
		registrationBean.setOldPassword(oldPassword);
		registrationBean.setNewPassword(newPassword);
		registrationBean.setUserName(loggedInUser.getUsername());
		//registrationBean.setUserId(Long.toString(loggedInUser.getUserId()));

		if (lLoginId1 != null && lLoginId1.equals(loggedInUser.getUsername())) {
			updatePassword = registrationService.updatePasswordDetails(loggedInUser, registrationBean);
			if (updatePassword == 0) {
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "1,Your old password is not correct.");
			} else {
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "0,Your password has been changed successfully.");
				loggedInUser.setFirstLogin("N");
				if (loggedInUser.getUserType() != null && !loggedInUser.getUserType().equals("") && loggedInUser.getUserType().equals("C")) {
					String status = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.CHANGE_PASSWORD_STATUS) + "";
					Integer disciplineId = 0;
					if (loggedInUser.getDisciplineID() != null) {
						disciplineId = Integer.parseInt(loggedInUser.getDisciplineID());
					}
					Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.CHANGE_PASSWORD_STATUS);
					Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.CHANGE_PASSWORD_STATUS);
					/*EmailSMSUtil.insertEmailNSMSForActivitynTest(ConfigurationConstants.getInstance().getActivtyKeyForName(GenericConstants.CHANGE_PASSWORD), disciplineId,
							loggedInUser.getUsername(), loggedInUser.getUsername(), status, loggedInUser, emailReq, smsReq);
*/
					ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger()
							.info(loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ", Home Page - Change Password , " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
					loggedInUser.setRemoteIp(request.getRemoteAddr());
					commonService.insertCandidateAuditrail(loggedInUser, "Change Password",
							"Candidate with User Id " + loggedInUser.getUsername() + " : Password changed Successfully.");
				}
			}
			result = "writePlainText";
		} else {
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "3,Session is Invalid.");
			result = "writePlainText";
		}
		return result;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	// Validation for forgot registration number
	public String forgotRegistrationNumberValidation() {
		String errors = "";
		List<String> errorList = new ArrayList<String>();

		if (ValidatorUtil.isEmpty(registrationBean.getMobileNo()))
			errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.ERROR_LABEL_MOBILENO));

		if (!ValidatorUtil.isNumeric(registrationBean.getMobileNo()))
			errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.ERROR_LABEL_MOBILENO_NUMERIC));

		if (ValidatorUtil.isEmpty(registrationBean.getEmailAddress()))
			errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.ERROR_LABEL_EMAILADDRESS));

		if (errorList.size() > 0)
			errors = ValidatorUtil.validationMessageFormatter(errorList);

		return errors;
	}

	private String forgotPasswordValidation() {
		String errors = "";
		List<String> errorList = new ArrayList<String>();
		try {
			if (StringUtils.isBlank(registrationBean.getFpOtp())) {
				errorList.add("<li>" + "Please enter Verification OTP" + ".</li>");
			} else if (!Pattern.matches("[0-9]{6}", registrationBean.getFpOtp().trim())) {
				errorList.add("<li>" + "Please enter a valid 6 digit Verification OTP" + ".</li>");
			} else if (!registrationService.checkFpOTP(registrationBean, "mobile")) {
				errorList.add("<li>" + "Invalid Verification OTP" + ".</li>");
			}

			if (StringUtils.isBlank(registrationBean.getNewPwd())) {
				errorList.add("<li>" + "Please enter New Password" + ".</li>");
			} else if (registrationBean.getNewPwd().length() < 8 || registrationBean.getNewPwd().length() > 12) {
				errorList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.NEW_PASSWORD_LENGTH) + "</li>");
			} else if (!Pattern.matches("^(?=.{8,12}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$", registrationBean.getNewPwd().trim())) {
				errorList.add("<li>" + "Please enter valid New Password" + ".</li>");
			} else if (registrationService.checkNewOldPasswordMatch(registrationBean)) {
				errorList.add("<li>" + "New Password cannot be same as Old Password" + ".</li>");
			}

			if (StringUtils.isBlank(registrationBean.getRePwd())) {
				errorList.add("<li>" + "Please enter Confirm New Password" + ".</li>");
			} else if (!registrationBean.getNewPwd().equals(registrationBean.getRePwd())) {
				errorList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.NEW_PASSWORD_MATCH) + "</li>");
			}

			if (errorList.size() > 0) {
				errors = ValidatorUtil.validationMessageFormatter(errorList);
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return errors;
	}
	
	private String forgotPasswordOtpValidation() {
		String errors = "";
		List<String> errorList = new ArrayList<String>();
		String choice = request.getParameter("choicepass");

		if (choice == null || "".equals(choice) || choice.contains("undefined")) {
			errorList.add("<li>" + "Please choose any one option." + "</li>");
		} else {
			if ("email".equals(choice)) {
				if (ValidatorUtil.isEmpty(registrationBean.getEmailId().toLowerCase())) {
					errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.EMAIL_REQD));
				}else {
					if (!EmailValidator.validate(registrationBean.getEmailId().toLowerCase())) {
						errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.INVALID_EMAIL));
					}
				}
				if (ValidatorUtil.isEmpty(registrationBean.getUserId().trim())) {
					errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.REGISTER_USERNAME_REQUIRED));
				}
				if (!ValidatorUtil.isalphaNumeric(registrationBean.getUserId().trim())) {
					errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.FORGOT_USERID_ALPHANUMERIC));
				}
				registrationBean.setUserName(registrationBean.getUserId().trim());
				if (StringUtils.isNotBlank(registrationBean.getEmailId().toLowerCase()) && StringUtils.isNotBlank(registrationBean.getUserName())) {
					if (!registrationService.checkIfEmailExistsForUserId(registrationBean)) {
						errorList.add("Invalid User ID or Email ID.");
					}
				}
			}
			if ("mobile".equals(choice)) {
				if (ValidatorUtil.isEmpty(registrationBean.getMobileNo())) {
					errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.REGISTRATION_MOBILE_REQUIRE));
				} else {
					String str = registrationBean.getMobileNo();
					Boolean isNumber = false;
					for (int i = 0; i < 10; i++) {
						if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
							isNumber = true;
						} else {
							isNumber = false;
						}
					}
					if (!isNumber) {
						errorList.add("<li>" + "Please enter 10 digit Mobile Number." + "</li>");
					}
				}
				if (ValidatorUtil.isEmpty(registrationBean.getUserId().trim())) {
					errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.REGISTER_USERNAME_REQUIRED));
				}
				if (!ValidatorUtil.isalphaNumeric(registrationBean.getUserId().trim())) {
					errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.FORGOT_USERID_ALPHANUMERIC));
				}

				registrationBean.setUserName(registrationBean.getUserId().trim());
				if (StringUtils.isNotBlank(registrationBean.getMobileNo()) && StringUtils.isNotBlank(registrationBean.getUserName())) {
					if (!registrationService.checkIfUserIdMobileNoExists(registrationBean)) {
						errorList.add("Invalid User ID or Mobile Number.");
					}
				}
			}
		}
		if (errorList.size() > 0) {
			errors = ValidatorUtil.validationMessageFormatter(errorList);
		}
		return errors;
	}

	private String forgotUserIdValidation() {
		String errors = "";
		List<String> errorList = new ArrayList<String>();
		String choice = request.getParameter("choice");

		if (choice == null || "".equals(choice)) {
			errorList.add("<li>" + "Please choose any one option." + "</li>");
		} else {
			if ("email".equals(choice)) {
				if (ValidatorUtil.isEmpty(registrationBean.getEmailAddress().toLowerCase())) {
					errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.EMAIL_REQD));
				}
			}
			if ("mobile".equals(choice)) {
				if (ValidatorUtil.isEmpty(registrationBean.getMobileNo())) {
					errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.MOBILE_REQD));
				} else {
					String str = registrationBean.getMobileNo();
					Boolean isNumber = false;
					for (int i = 0; i < 10; i++) {
						if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
							isNumber = true;
						} else {
							isNumber = false;
						}
					}
					if (!isNumber) {
						errorList.add("<li>" + "Please enter 10 digit Mobile Number." + "</li>");
					}
				}

			}
		}

		if (errorList.size() > 0) {
			errors = ValidatorUtil.validationMessageFormatter(errorList);
		}
		return errors;
	}
	
	public String forgotPasswordOTP() {
		String serverSideErrors = null;
		String otpFlagVal = "";
		otpFlagVal = request.getParameter("choicepass");
		serverSideErrors = forgotPasswordOtpValidation();
		try {
			Users users = new Users();
			users.setUsername(registrationBean.getUserId());
			String userExist = commonService.getCandidateDetailsByLoginId(registrationBean.getUserName());
			logger.info("forgotPasswordOTP method called with email address : {} ",userExist);
			Users userForUsername = loginService.getUserForUsername(registrationBean.getUserName());
			if(!ObjectUtils.isEmpty(userForUsername) && userForUsername.getUserId() > 0 && StringUtils.isNotBlank(userExist)) {
				users.setUserId(userForUsername.getUserId());
				commonService.insertCandidateAuditrail(users, "Forgot Password", "Candidate with User ID " + users.getUsername() + " has forgotten his password.");
			}else {
				commonService.insertCandidateAuditrail(users, "Forgot Password", "Candidate with User ID " + users.getUsername() + " not found for forgot password.");
			}
			//commonService.insertCandidateAuditrail(users, "Forgot Password", "Candidate with User ID " + users.getUsername() + " has forgotten password.");
			if (!ValidatorUtil.isEmpty(serverSideErrors)) {
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "1," + serverSideErrors);
			} else {
				String statusForUsername = loginService.getStatusForUsername(registrationBean.getUserName());
				if("B".equals(statusForUsername)) {
					request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "1,"+ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.MSG_FOR_DUPLICATE_USER));
					logger.info("User try to recover forgot password with blocked user Id :" + registrationBean.getUserName());
					return "writePlainText";
				}else {
				if (otpFlagVal.equals("mobile")) {

					String otpSent = registrationService.generateFpOTP(registrationBean.getMobileNo(), registrationBean.getUserId(), "mobile");
					if (otpSent != null && !otpSent.equals("")) {
						registrationBean.setFpOtp(otpSent);
						logger.info("Candidate with User ID " + registrationBean.getUserId() + " has received Forgot Password verification OTP Successfully.");
						commonService.insertCandidateAuditrail(users, "Forgot Password",
								"Candidate with User ID " + users.getUserId() + " has received Forgot Password verification OTP Successfully.");
						request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "1,OTP for verification sent to Registered Mobile Number." + "," + registrationBean.getUserId() + ","
								+ registrationBean.getMobileNo() + "," + registrationBean.getFpOtp());
					}
				} else if (otpFlagVal.equals("email")) {
					String otpSent1 = registrationService.generateFpOTP(registrationBean.getEmailId(), registrationBean.getUserId(), "email");
					if (otpSent1 != null && !otpSent1.equals("")) {
						registrationBean.setFpOtp(otpSent1);
						logger.info("Candidate with User ID " + registrationBean.getUserId() + " has received Forgot Password verification OTP Successfully.");
						commonService.insertCandidateAuditrail(users, "Forgot Password",
								"Candidate with User ID " + users.getUsername() + " has received Forgot Password verification OTP Successfully.");
						request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "1,OTP for verification sent to Registered Email ID." + "," + registrationBean.getUserId() + ","
								+ registrationBean.getEmailId() + "," + registrationBean.getFpOtp());
					}

				} else {
					request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "1, Please try again.");
					return "writePlainText";
				}
				return "writePlainText";
				}
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return "writePlainText";
	}

	public String forgotPassword() {
		int count = 0;
		String serverSideErrors = null;
		registrationBean.setUserName(request.getParameter("userId"));
		registrationBean.setMobileNo(request.getParameter("mobile"));
		registrationBean.setFpOtp(request.getParameter("verfOtp"));
		registrationBean.setNewPwd(request.getParameter("newPswd") != null ? (String) request.getParameter("newPswd") : null);
		registrationBean.setRePwd(request.getParameter("confirmPswd") != null ? (String) request.getParameter("confirmPswd") : null);

		serverSideErrors = forgotPasswordValidation();
		try {
			if (!ValidatorUtil.isEmpty(serverSideErrors)) {
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "1," + serverSideErrors);
			} else {
				count = registrationService.forgotPassword(registrationBean);
				if (count > 0) {
					if(sessionAttributes != null)
					sessionAttributes.put(GenericConstants.LOGIN_FAILURE_COUNT, 0);
					Users users = new Users();
					users.setUsername(registrationBean.getUserId().trim());
					String staus = "1";
					String userFK = registrationService.getUserFK(registrationBean);
					users.setUserFk(Long.parseLong(userFK));

					registrationBean.setUserId(userFK); // Set For EmaiSMS
					registrationBean.setUserName(users.getUsername());

					ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger()
							.info(users.getUsername() + ", " + request.getRemoteAddr() + ", Forgot Password , " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));

					commonService.insertCandidateAuditrail(users, "Forgot Password", "Candidate with User ID " + users.getUsername() + " has set new password sucessfully.");
					
					request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "1,New Password set successfully.");
					
					String userId = registrationBean.getUserName().trim();
					sessionAttributes.put(GenericConstants.LOGIN_FAILURE_COUNT + userId, null);
					return "writePlainText";
				} else {
					request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "1,Invalid Verification OTP.");
					return "writePlainText";
				}
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return "writePlainText";
	}

	public String checkForgotPassword() throws Exception {
		int count = 0;
		String serverSideErrors = null;
		registrationBean.setUserName(request.getParameter("userId"));
		registrationBean.setMobileNo(request.getParameter("mobile"));
		registrationBean.setFpOtp(request.getParameter("verfOtp"));
		registrationBean.setNewPwd(request.getParameter("newPswd") != null ? (String) request.getParameter("newPswd") : null);
		registrationBean.setRePwd(request.getParameter("confirmPswd") != null ? (String) request.getParameter("confirmPswd") : null);

		serverSideErrors = forgotPasswordValidation();
		try {
			if (!ValidatorUtil.isEmpty(serverSideErrors)) {
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "1," + serverSideErrors);
			} else {
				count = registrationService.forgotPassword(registrationBean);
				if (count > 0) {
					Users users = new Users();
					users.setUsername(registrationBean.getUserId().trim());
					String staus = "1";
					String userFK = registrationService.getUserFK(registrationBean);
					users.setUserFk(Long.parseLong(userFK));

					registrationBean.setUserId(userFK); // Set For EmaiSMS
					registrationBean.setUserName(users.getUsername());

					ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger()
							.info(users.getUsername() + ", " + request.getRemoteAddr() + ", Forgot Password , " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));

					commonService.insertCandidateAuditrail(users, "Forgot Password", "Candidate with User ID " + users.getUsername() + " has set new password sucessfully.");
					// Integer disciplineId = commonService.getDisciplineForCandidate(users);
					// if (disciplineId!=null && !disciplineId.equals("")){
					/* Integer emailReq = 1; Integer smsReq = 1; */
					// EmailSMSUtil.insertEmailNSMSForActivitynTest(ConfigurationConstants.getInstance().getActivtyKeyForName(GenericConstants.FORGOT_PASSWORD),disciplineId,
					// users.getUsername(), users.getUsername(),staus,users,emailReq,smsReq);
					// For Forgot Password Email/SMS
					/* EmailSMSUtil.insertEmailNSMSForForgotPwd(emailReq, smsReq, staus, registrationBean); */

					// }
					request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "1,New Password set successfully.");
					// registrationService.getResetDoneStatus(registrationBean);
					String userId = registrationBean.getUserName().trim();
					sessionAttributes.put(GenericConstants.LOGIN_FAILURE_COUNT + userId, null);
					return "writePlainText";
				} else {
					request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "1,Invalid Verification OTP.");
					return "writePlainText";
				}
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return "writePlainText";
	}
	public String forgotUserId() throws Exception {
		try {
//			String post = (String) request.getParameter("post");
//			registrationBean.setPostApplied(post);

			checkForgotUserId();
		} catch (Exception e) {
			String msg = ExceptionStacktrace.get(e);
			System.out.println(msg);
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Error occured while sending Link");
		}
		return "writePlainText";
	}

	public void checkForgotUserId() throws Exception {
		String serverSideErrors = null;
		serverSideErrors = forgotUserIdValidation();

		if (!ValidatorUtil.isEmpty(serverSideErrors)) {
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "1," + serverSideErrors);
		} else {
			RegistrationService.registrationServiceConstants status = registrationService.sendUserIdLinkToRegMail(registrationBean, request);
			switch (status) {
			case ATTEMPT_EXCEEDED:
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "1,Invalid Credentials or Attempt Exceeded.");
				break;
			case EMAIL_NOT_EXISTS:
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "1,Please enter valid Email ID.");
				break;
			case MOBILE_NOT_EXISTS:
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "1,Please enter valid Mobile Number.");
				break;
			case SEND_USERID_LINK_TO_REGMAIL:
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "1,User ID has been sent to your registered Email ID and Mobile Number.");
				break;
			default:
				throw new LogicError("Error occured while sending Link");
			}
		}
	}

	// Server Side Validation for First time change password

	private String changePasswordForFirstTimeValidation() {
		String errors = "";
		List<String> errorsList = new ArrayList<String>();
		if (registrationBean.getNewPassword() != null) {
			if (ValidatorUtil.isEmpty(registrationBean.getNewPassword())) {
				errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.NEW_PASSWORD));
			}
		} else {
			errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.NEW_PASSWORD));
		}

		if (registrationBean.getOldPassword() != null) {
			if (ValidatorUtil.isEmpty(registrationBean.getOldPassword())) {
				errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.OLD_PASSWORD));
			}
		} else {
			errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.OLD_PASSWORD));
		}

		if (registrationBean.getOldPassword() != null && registrationBean.getNewPassword() != null) {
			if (!ValidatorUtil.isEmpty(registrationBean.getOldPassword()) && !ValidatorUtil.isEmpty(registrationBean.getNewPassword())) {
				if (registrationBean.getOldPassword().compareTo(registrationBean.getNewPassword()) == 0) {
					errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.USE_DIFFERENT_PASSWORD));
				}
			}
		}

		if (errorsList != null && !errorsList.isEmpty()) {
			errors = ValidatorUtil.validationMessageFormatter(errorsList);
		}

		return errors;
	}

	public String changePasswordForFirstTime() throws Exception {
		int changePassword = 0;
		String result = null;
		String serverSideErrorMessages = null;

		serverSideErrorMessages = changePasswordForFirstTimeValidation();
		if (serverSideErrorMessages != null && !serverSideErrorMessages.equals("")) {
			addActionMessage(serverSideErrorMessages);
			result = "firstLogin";
		} else {
			Users users = (Users) sessionAttributes.get(SESSION_USER + "1");
			changePassword = registrationService.updatePasswordForFirstTime(registrationBean);

			if (changePassword > 0) {
				ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger().info(registrationBean.getUserName() + ", " + request.getRemoteAddr()
						+ ", First Time Change Password , " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
				request.getSession().setMaxInactiveInterval(Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SESSION_TIMEOUT)));
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Password Changed Successfully");
				request.setAttribute(GenericConstants.MESSAGE_TYPE, GenericConstants.MESSAGE_TYPE_INFORMATION);
				request.setAttribute(GenericConstants.DESTINATION_PATH, "CandidateAction_getCandidateDetails.action");

				if (users != null) {
					users.setPassword(registrationBean.getNewPassword());
				}
				sessionAttributes.put(GenericConstants.SESSION_USER, users);

				result = "displayAppForm";
			} else {
				addActionMessage("Error in Change Password");
				result = "firstLogin";
			}
		}
		return result;
	}

	
	public void loadDefaults() {
		
		registrationBean.setAgeAsOn(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AGE_AS_ON));

		Long endDate = ConfigurationConstants.getInstance().getDateWindowMap().get(7).get(1);
		LocalDateTime endDateNew = Instant.ofEpochMilli(endDate).atZone(ZoneId.systemDefault()).toLocalDateTime().plusYears(1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd,yyyy");
		registrationBean.setDischargeEndYear(endDateNew.getYear());
		registrationBean.setDischargeEndDate(endDateNew.format(formatter));
		 
		Map<Integer, String> nationalityList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Nationality");
		Map<Integer, String> nationalityMap = new TreeMap<Integer, String>(nationalityList);
		registrationBean.setNationalityList(nationalityMap);
		
		Map<Integer, String> disabilityTypeList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Disability_type");
		Map<Integer, String> disableTypeMap = new TreeMap<Integer, String>(disabilityTypeList);
		registrationBean.setPersonDisabilityList(disableTypeMap);
		
		Map<Integer, String> yesNo = ConfigurationConstants.getInstance().getParameterMapForDomainName("Yes_No_Map");
		Map<Integer, String> yesNoMap = new TreeMap<Integer, String>(yesNo);
		registrationBean.setYesNo(yesNoMap);
		
		Map<Integer, String> categoryList = ConfigurationConstants.getInstance().getCategoryListMap();
		registrationBean.setCategoryList(categoryList);
		
		Map<Integer, String> genderList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Gender");
		Map<Integer, String> genderMap = new TreeMap<Integer, String>(genderList);
		registrationBean.setGenderList(genderMap);
		
		registrationBean.setStateList(ConfigurationConstants.getInstance().getStateMap(1));
		
		Map<Integer, String> districtList = ConfigurationConstants.getInstance().getDistrictMap(1);
		Map<Integer, String> districtListMap = new TreeMap<Integer, String>(districtList);
		registrationBean.setDistrictList(districtListMap);
	}

	public String registerCandidate() {

		String result = null;
		try {

			registrationBean.setAgeAsOn(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AGE_AS_ON));
			String serverSideErrors = registrationBeanValidator.validateRegisterCandidateBean(registrationBean);
			List<String> errorsList = new ArrayList<String>();
			ArrayList<String> errorField1 = new ArrayList<String>();
			errorsList.add(serverSideErrors);
			String generatedCaptcha = request.getParameter("txtCaptchaHid").replaceAll("\\s", "");
			String enteredCaptcha = request.getParameter("registeredCaptchaHid");

			if (StringUtils.isNoneBlank(enteredCaptcha) && StringUtils.isNoneBlank(generatedCaptcha)) {
				if (!generatedCaptcha.equals(enteredCaptcha)) {
					errorsList.add("<li>" + "Captcha validation failed" + ".</li>");
				}
			}

			if (Strings.isNotBlank(registrationBean.getMobileNo())) {

				if (registrationBean.getMobileNo().matches("[56789][0-9]{9}")) {
					if (Strings.isNotBlank(registrationBean.getMobotp())) {
						boolean checkotp = registrationService.verifyOtp(registrationBean.getMobotp(), registrationBean.getMobileNo());
						if (checkotp) {
							registrationBean.setVerifyMobileOTPFlag("false");
							errorsList.add("<li>" + "Invalid OTP. Please try again." + "</li>");
							errorField1.add("mobileNo");
						}
					} else {
						errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.MOB_OTP_REQD));
						errorField1.add("mobileNo");
					}
				} else {
					errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.INVALID_MOBILE_NO));
					errorField1.add("mobileNo");
				}
			} else {
				errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.MOBILE_REQD));
				errorField1.add("mobileNo");
			}

			if (Strings.isNotBlank(registrationBean.getEmailAddress())) {

				if (EmailValidator.validate(registrationBean.getEmailAddress())) {
					if (Strings.isNotBlank(registrationBean.getEmailotp())) {
						boolean checkEmailotp = registrationService.verifyOtp(registrationBean.getEmailotp(), registrationBean.getEmailAddress());
						if (checkEmailotp) {
							registrationBean.setVerifyemailOTPFlag("false");
							errorsList.add("<li>" + "Invalid OTP. Please try again." + "</li>");
						}
					} else {
						errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.OTP_REQD));
					}
				} else {
					errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.INVALID_EMAIL));
					errorField1.add("emailAddress");
				}
			} else {
				errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.EMAIL_REQD));
				errorField1.add("emailAddress");
			}

			if (errorsList != null && !errorsList.isEmpty()) {
				serverSideErrors = ValidatorUtil.validationMessageFormatter(errorsList);
				registrationBean.setErrorField1(errorField1);
			}
			if (serverSideErrors != null && !serverSideErrors.equals("")) {
				loadDefaults();
				addActionMessage(serverSideErrors);
				result = "registerPage";
			} else {
				try {
					registrationServiceConstants finalResult = registrationService.register(registrationBean, request);

					switch (finalResult) {
					case SUCCESS:
						result = "goToWelcomePage";
						break;
					case ERROR:
						result = ERROR;
						break;
					case USER_EXIST:
						addActionMessage("<li> User id Already Exist. Please Try Again !!</li>");
						registrationBean.setCaptcha("");
						registrationBean.setUserName("");
						result = "registerPage";
					default:
						throw new LogicError("Error occured while sending Link");
					}
				} catch (GenericServiceException ex) {
					logger.info(ex.getMessage());
					result = ERROR;
				}
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return result;
	}
	
	 public String getAgeMatrixDetails(){
		  // to get Max age and Max date from DB
			 
			String minDate = "";
			try {
				LinkedHashMap<String, String> ageMatrixMap = null;
				int post_selected = 39;//change if multiple post introduced
				int category = 7;
				String disability = "N";
				String exserviceman = "N";
				String destiWidow = "N";
				
				if(request.getParameter("coursesVal") != null && !request.getParameter("coursesVal").isEmpty()) {
					post_selected = Integer.parseInt(request.getParameter("coursesVal"));
				}
				if(request.getParameter("exServicemenVal") != null && !request.getParameter("exServicemenVal").isEmpty() && request.getParameter("exServicemenVal").equals("6")) {
					exserviceman = "Y";
				}
				if(request.getParameter("disabilityVal") != null && !request.getParameter("disabilityVal").isEmpty() && request.getParameter("disabilityVal").equals("6")) {
					disability = "Y";
				}
				if(request.getParameter("categoryvalue") != null && !request.getParameter("categoryvalue").isEmpty()) {
					category = Integer.parseInt(request.getParameter("categoryvalue"));
				}
				if(request.getParameter("widowYesNo") != null && !request.getParameter("widowYesNo").isEmpty() && request.getParameter("widowYesNo").equals("6")) {
					destiWidow = "Y";
				}
				if(category != 7)
				{
					category = 8;
					exserviceman = "Y";disability = "Y";destiWidow = "Y";
				}
				ageMatrixMap = new LinkedHashMap<String, String>(registrationService.getAgeMatrixDetails(post_selected,exserviceman,disability,category,destiWidow));
						registrationBean.setMinDate(ageMatrixMap.get("START_DATE"));
				
				if (ageMatrixMap != null && !ageMatrixMap.isEmpty()) {
					minDate = registrationBean.getMinDate();
				} else {
					minDate =ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_DATE_FOR_DATEPICKER);
				}
			} catch (Exception e) {
				logger.fatal(e, e);
			}
			//requestAttributes.put(GLOBAL_PLAIN_TEXT_MESSAGE, minDate);
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, minDate);
			return "writePlainText";
		}

	public String goToHomePage() {
		return "notloggedin";
	}

	@Override
	public void setLoggedInUser(Users users) {
		this.loggedInUser = users;
	}

	@Override
	public void resetModel() {
		registrationBean = new RegistrationBean();

	}

	public String refreshCaptchaImage() {
		try {
			try {

				Map<Integer, String> nationalityList;
				nationalityList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Nationality");
				Map<Integer, String> lMap = new TreeMap<Integer, String>(nationalityList);
				registrationBean.setNationalityList(lMap);
				
				Map<Integer, String> nativityCertDistList;
				nativityCertDistList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Nativity_Cert_District");
				Map<Integer, String> NMap = new TreeMap<Integer, String>(nativityCertDistList);
				registrationBean.setNativityCertDistList(NMap);
				
				registrationBean.setEmailAddress(registrationBean.getEmailAddressHid());
				registrationBean.setMobileNo(registrationBean.getMobileNoHid());
				registrationBean.setNewPassword(registrationBean.getNewPasswordHid());
				registrationBean.setConfirmPassword(registrationBean.getConfirmPasswordHid());
				registrationBean.setCaptcha(registrationBean.getCaptchaHid());

				return "registerPage";
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "registerPage";

		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return "registerPage";

	}

	static String OTP(int len) {

		String numbers = "0123456789";
		Random rndm_method = new Random();
		char[] otp = new char[len];
		StringBuilder otp1 = new StringBuilder("");
		for (int i = 0; i < len; i++) {
			otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
			otp1.append(numbers.charAt(rndm_method.nextInt(numbers.length())));
		}
		return otp1.toString();
	}

	public String generateOTP() {
		String otpFlagVal = "";
		int length = 6, otp_attempt = 0, flag = 0;
		try {
			otpFlagVal = request.getParameter("otpFlag");
			String mobileNo1 = request.getParameter("mobileNo1");
			String emailId = request.getParameter("emailId");

			registrationBean.setMobileNo(mobileNo1);
			registrationBean.setEmailAddress(emailId.toLowerCase().trim());
			
			String otp = null;

			if (otpFlagVal.equals("email")) {
				// validate email
				if (!EmailValidator.validate(registrationBean.getEmailAddress())) {
					request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Please enter valid Email ID.");
					return "writePlainText";
				}

				String emailOTPExist = registrationService.checkEmailOTP(registrationBean); // send or verified
				if (emailOTPExist == null) { // emailExist <= 0 &&
					otp = generateRandomOTP(length).toString();
					int pk_of_transaction = EmailSMSUtil.insertEmailForOTP(otp, emailId, emailId);

					registrationService.insertOTPDetails(emailId, otp, pk_of_transaction);
					 //registrationBean.setEmailAddress(registrationBean.getEmailAddress());
					 String showOtpOnScreen = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SHOW_OTP_ON_SCREEN);
					 if ("Y".equalsIgnoreCase(showOtpOnScreen)) {
						 request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, " OTP sent to Email ID " + emailId + " ## " + otp); 
						} else {
							request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, " OTP sent to Email ID " + emailId + "##");
						}
				} else {
					if (emailOTPExist.equals("send")) {

						boolean isExistingOtp = false;
						
							otp = generateRandomOTP(length).toString();
						
						// Update email_sms_tran_master
						EmailSMSUtil.insertEmailForOTP(otp, emailId, emailId);

						registrationService.updateOTPDetails(emailId, otp, isExistingOtp);
						// registrationBean.setEmailAddress(registrationBean.getEmailAddress());
						String showOtpOnScreen = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SHOW_OTP_ON_SCREEN);
						if ("Y".equalsIgnoreCase(showOtpOnScreen)) {
						request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "OTP resent to Email ID " +  emailId + " ## " + otp); 
						}else {
							request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "OTP resent to Email ID " +  emailId + "##"); 
						}
					} else if (emailOTPExist.equals("verified")) {
						request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Please try again with a different Email ID." + "##");
					}
				}

			} else if (otpFlagVal.equals("mobile")) {
				// for mobile number
				if (!registrationBean.getMobileNo().matches("[56789][0-9]{9}")) {
					request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Please enter valid Mobile Number.");
					return "writePlainText";
				} else {
					if (MobileValidator.isValidMsisdn(registrationBean.getMobileNo())) {
					} else {
						request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Please enter valid Mobile Number.");
						return "writePlainText";
					}
				}

				RegistrationBean regBean = registrationService.checkMobNoOTP(registrationBean); // send or verified
				if (regBean != null) {
					// either otp is send or verified
					if (regBean.getOtpSate().equals("send")
							&& regBean.getOtpLockCount() < Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OTP_ATTEMPT))) {
						// resend otp
						boolean isExistingMobOtp = false;
						
							otp = generateRandomOTP(length).toString();
						
						int otpUpdatecount = 0, transInsertCount = 0;
						otpUpdatecount = registrationService.updateMOBOTPDetails(mobileNo1, otp, isExistingMobOtp); 

						if (otpUpdatecount > 0) {
							transInsertCount = EmailSMSUtil.insertMobForOTP(otp, mobileNo1, mobileNo1);
						}
						if (transInsertCount > 0) {
							String showOtpOnScreen = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SHOW_OTP_ON_SCREEN);
							if ("Y".equalsIgnoreCase(showOtpOnScreen)) {
								request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "OTP resent to Mobile Number " + mobileNo1 + "##" + otp);
							} else {
								request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "OTP resent to Mobile Number " + mobileNo1 + "##");
							}
						} else {
							request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Error occurred while sending OTP to " + mobileNo1 + "##");
						}
						
					} else if (regBean.getOtpSate().equals("verified")) {
						// TODO 1: Check OTP flag
						request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Please try again with a different mobile number." + "##");
					} else {
						// resend otp attempts exceeded
						request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "You have reached maximum SMS limit." + "##");
					}

				} else {
					// generate new otp
					otp = generateRandomOTP(length).toString();
					int otpInsertcount = 0, transInsertCount = 0;
					otpInsertcount = registrationService.insertMOBDetails(mobileNo1, otp); // , courseVal

					if (otpInsertcount > 0) {
						transInsertCount = EmailSMSUtil.insertMobForOTP(otp, mobileNo1, mobileNo1);
					}
					if (transInsertCount > 0) {
						String showOtpOnScreen = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SHOW_OTP_ON_SCREEN);
						if ("Y".equalsIgnoreCase(showOtpOnScreen)) {
							request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, " OTP sent to Mobile Number " + mobileNo1 + " ## " + otp);
						}else {
							request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, " OTP sent to Mobile Number " + mobileNo1 + "##");
	
						}
					} else {
						// error updating records
						request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Error occurred while sending OTP to \" + mobileNo1 + \"##");
					}

				}
				registrationBean.setMobileNo(registrationBean.getMobileNo());
				response.setContentType("text/plain");
				PrintWriter out = response.getWriter();
				out.print(otp);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Please try again for " + otpFlagVal + " OTP" + "##");
		}
		return "writePlainText";
	}
	
	public String verifyOTP() {
		String enteredOtp = request.getParameter("enteredOtp");
		String mobileNo = request.getParameter("mobileNo");
		boolean result = registrationService.verifyOtp(enteredOtp, mobileNo);
		int count = registrationService.checkAttemptCount(mobileNo);
		if (count > 10) {
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "attempt exceeded");
			return "writePlainText";
		}
		if (!result) {
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "ok");
		} else {
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Invalid Otp");
		}
		return "writePlainText";
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

	public String verifyEmailExists() {
		String emailAddress = request.getParameter("emailAdress");
		int emailCount = registrationService.verifyEmailExists(emailAddress);
		if(emailCount > 0) {
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE,"Please try again with a different Email ID.");
		}else {
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE,"Success");
		}
		return "writePlainText";
	}

	public String generateFpOTP() {
		try {
			String userName = request.getParameter("username");
			String mob = request.getParameter("mobile");
			String choice = request.getParameter("choicepass");
			if (choice.equals("mobile")) {
				String otp = registrationService.generateFpOTP(mob, userName, "mobile");

				if (otp != "" && otp != null) {
					logger.info("Candidate with User ID " + userName + " has received Resend Forgot Password Verification OTP Successfully.");
						request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "1,OTP resend successfully"+","+otp); 
				} else {
					request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "1,Please try again for Resend OTP for verification ##");
				}
			} else if (choice.equals("email")) {
				String otp = registrationService.generateFpOTP(mob, userName, "email");

				if (otp != "" && otp != null) {
					logger.info("Candidate with User ID " + userName + " has received Resend Forgot Password Verification OTP Successfully.");
					request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "1,OTP resend successfully"+","+otp); 
				} else {
					request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "1,Please try again for Resend OTP for verification ##");
				}
			}
		} catch (Exception e) {
			String msg = ExceptionStacktrace.get(e);
			System.out.println(msg);
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "1,Error occured while sending OTP");
		}
		return "writePlainText";
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
}