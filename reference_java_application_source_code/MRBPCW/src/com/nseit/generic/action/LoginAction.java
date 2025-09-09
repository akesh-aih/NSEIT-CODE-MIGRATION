
package com.nseit.generic.action;

import static com.nseit.generic.util.GenericConstants.GLOBAL_PLAIN_TEXT_MESSAGE;
import static com.nseit.generic.util.GenericConstants.SESSION_USER;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Logger;
import org.apache.struts2.dispatcher.HttpParameters;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.CookiesAware;

import com.nseit.generic.models.LoginBean;
import com.nseit.generic.models.RegistrationBean;
import com.nseit.generic.models.SessionDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.AgencyService;
import com.nseit.generic.service.CommonService;
import com.nseit.generic.service.LoginService;
import com.nseit.generic.service.OTPService;
import com.nseit.generic.service.RegistrationService;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.DESEncrypter;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.LogicError;
import com.nseit.generic.util.ValidatorUtil;
import com.nseit.generic.util.aware.UserAware;
import com.nseit.generic.util.resource.ResourceUtil;
import com.nseit.generic.util.resource.ValidationMessageConstants;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import software.amazon.awssdk.utils.StringUtils;

public class LoginAction extends BaseAction implements ModelDriven<LoginBean>, UserAware, CookiesAware {

	private static final long serialVersionUID = 1L;
	private LoginService loginService;
	private LoginBean loginBean = new LoginBean();
	private CommonService commonService;
	private AgencyService agencyService;
	private Logger logger = LoggerHome.getLogger(getClass());
	private OTPService m_OTPService;

	public void setOTPService(OTPService OTPService) {
		m_OTPService = OTPService;
	}

	public void setRegistrationService(RegistrationService registrationService) {
	}

	public LoginBean getModel() {
		return loginBean;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public void setAgencyService(AgencyService agencyService) {
		this.agencyService = agencyService;
	}

	public String input() throws Exception {
		sessionAttributes.put(SESSION_USER, null);
		String loginFooterVal = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.LOGIN_FOOTER);
		loginBean.setLoginFooter(loginFooterVal);
		Map<Integer, List<Long>> dateWindowMap = ConfigurationConstants.getInstance().getDateWindowMap();
		List<Long> dateList = dateWindowMap.get(7);
		Long startDate = dateList.get(0);
		Long endDate = dateList.get(1);
		loginBean.setRegStrtDate(CommonUtil.formatDate(new Date(startDate), GenericConstants.DATE_FORMAT_DEFAULT));
		loginBean.setRegEndDate(CommonUtil.formatDate(new Date(endDate), GenericConstants.DATE_FORMAT_DEFAULT));

		long today = ZonedDateTime.now().toInstant().toEpochMilli();
		if ((today == startDate || today > startDate) && (today == endDate || today < endDate)) {
			loginBean.setRegistrationActive(true);
		} else {
			loginBean.setRegistrationActive(false);
		}
		return INPUT;
	}
	
	public String loadInstructions() {
		return "instructions";
	}


	public String authenticateUser() throws Exception {
		String result = "";
		String requestMethod = request.getMethod();
		if (!"POST".equalsIgnoreCase(requestMethod)) {
			return "errorPage";
		}
		String serverSideValidation = authenticateUserValidation();
		if (serverSideValidation != null && !serverSideValidation.equals("")) {
			addActionMessage(serverSideValidation);
			result = INPUT;
		} else {
			LoginService.loginServiceConstants status = loginService.authenticate(loginBean, request, sessionAttributes, response, requestAttributes);
			
			switch (status) {
			case SESSIONERROR:
				result = "sessionError";
				break;
			case ACCOUNT_LOCK:
				addActionMessage("Your account has been blocked due to multiple incorrect attempts, Kindly click on Forgot password link in order to set new Password.");
				loginBean.setUsername(null);
				result = input();
				break;
			case INVALID_LOGIN_CREDENTIALS:
				addActionMessage("Invalid login credentials");
				result = input();
				break;
			case INSTRUCTION_PAGE:
				result = "instructionPage";
				break;
			case CLOSE_WINDOW:
				result = "CloseWindow";
				break;
			case INPUT:
				result = INPUT;
				input();
				break;
			case ERROR:
				result = ERROR;
				addActionMessage("User ID is not mapped to correct IP Address");
				input();
				break;
			case SUCCESS_AGENCY:
				result = "successAgency";
				break;

			case REDIRECT_ACTION_URL:
				result = GenericConstants.REDIRECT_ACTION_URL;// "successGcetLogin";
				break;
				
			case BLOCKED_USER:
				addActionMessage(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.MSG_FOR_DUPLICATE_USER));
				result = input();
				break;

			default:
				throw new LogicError("Error occured while authenticating the user:");
			}
		}
		return result;
	}

	public String registerCandidate() {
		String result = null;
		try {
			Map<Integer, List<Long>> dateWindowMap = ConfigurationConstants.getInstance().getDateWindowMap();
			List<Long> dateList = dateWindowMap.get(7);
			Long startDate = dateList.get(0);
			Long endDate = dateList.get(1);
			loginBean.setAgeAsOn(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AGE_AS_ON));
			loginBean.setRegStrtDate(CommonUtil.formatDate(new Date(startDate), GenericConstants.DATE_FORMAT_DEFAULT));
			loginBean.setRegEndDate(CommonUtil.formatDate(new Date(endDate), GenericConstants.DATE_FORMAT_DEFAULT));
			LocalDateTime endDateNew = Instant.ofEpochMilli(endDate).atZone(ZoneId.systemDefault()).toLocalDateTime().plusYears(1);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd,yyyy");
			loginBean.setDischargeEndYear(endDateNew.getYear());
	        loginBean.setDischargeEndDate(endDateNew.format(formatter));
			long today = ZonedDateTime.now().toInstant().toEpochMilli();
			if ((today == startDate || today > startDate) && (today == endDate || today < endDate)) {

				result = "registerPage";

				Map<Integer, String> nationalityList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Nationality");
				Map<Integer, String> nationalityMap = new TreeMap<Integer, String>(nationalityList);
				loginBean.setNationalityList(nationalityMap);

				Map<Integer, String> yesNoList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Yes_No_Map");
				Map<Integer, String> yesNoMap = new TreeMap<Integer, String>(yesNoList);
				loginBean.setYesNo(yesNoMap);
				
				Map<Integer, String> disabilityTypeList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Disability_type");
				Map<Integer, String> disableTypeMap = new TreeMap<Integer, String>(disabilityTypeList);
				loginBean.setPersonDisabilityList(disableTypeMap);
				
				Map<Integer, String> categoryList = ConfigurationConstants.getInstance().getCategoryListMap();
				loginBean.setCategoryList(categoryList);
				
				Map<Integer, String> genderList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Gender");
				Map<Integer, String> genderMap = new TreeMap<Integer, String>(genderList);
				loginBean.setGenderList(genderMap);
				
				loginBean.setStateList(ConfigurationConstants.getInstance().getStateMap(1));
				
				Map<Integer, String> districtList = ConfigurationConstants.getInstance().getDistrictMap(1);
				Map<Integer, String> districtListMap = new TreeMap<Integer, String>(districtList);
				loginBean.setDistrictList(districtListMap);
										
			} else {
				// Used for displaying closure date in closewindow.jsp
				loginBean.setRegStrtDate(CommonUtil.formatDate(new Date(startDate), GenericConstants.DATE_FORMAT_DEFAULT1));
				String newStartDate = CommonUtil.formatDate(new Date(startDate), GenericConstants.DATE_FORMAT_DEFAULT1);
				String newEndDate = CommonUtil.formatDate(new Date(endDate), GenericConstants.DATE_FORMAT_DEFAULT1);
				String[] endDateFinal = newEndDate.split(" ");
				String[] startDateFinal = newStartDate.split(" ");
				loginBean.setRegEndDate("- "+ startDateFinal[0]+" " +startDateFinal[1]+" hrs to "+endDateFinal[0]+" " +endDateFinal[1]+" hrs .");
				loginBean.setDisplayRegFlag("true");
				return "CloseWindow";
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return result;
	}

	private String authenticateUserValidation() throws Exception {
		String errors = "";
		String temp = "";
		List<String> errorsList = new ArrayList<String>();
		Map<Integer, List<Long>> dateWindowMap = ConfigurationConstants.getInstance().getDateWindowMap();		
		List<Long> dateList = dateWindowMap.get(1);
		Long startDate = dateList.get(0);
		Long endDate = dateList.get(1);
		long today = ZonedDateTime.now().toInstant().toEpochMilli();
		List<Long> dateList1 = dateWindowMap.get(6);
		Long startDate1 = dateList1.get(0);
		Long endDate1 = dateList1.get(1);		
	//	Map<Integer, String> paymentModeTypeMap = ConfigurationConstants.getInstance().getPaymentMasterMap();
		Users user = loginService.getUser(loginBean.getUsername());
		
		try {
			if (loginBean.getUsername() != null) {
				if (ValidatorUtil.isEmpty(loginBean.getUsername())) {
					errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CANDIDATE_LOGIN_REQD));
				}
				if (!((today == startDate || today > startDate) && (today == endDate || today < endDate))) {
					if(user.getStatusID() < 5) {
					temp="";
						// Used for displaying closure date in closewindow.jsp
					loginBean.setAppFormStrtDate(CommonUtil.formatDate(new Date(startDate), GenericConstants.DATE_FORMAT_DEFAULT1));
					String newStartDate = CommonUtil.formatDate(new Date(startDate), GenericConstants.DATE_FORMAT_DEFAULT1);
					String newEndDate = CommonUtil.formatDate(new Date(endDate), GenericConstants.DATE_FORMAT_DEFAULT1);
					String[] endDateFinal = newEndDate.split(" ");
					String[] startDateFinal = newStartDate.split(" ");
					loginBean.setAppFormEndDate(" - "+ startDateFinal[0]+" " +startDateFinal[1]+" to "+endDateFinal[0]+" " +endDateFinal[1]+" hrs .");
					loginBean.setDisplayAppFlag("true");		
					temp = "Cannot login as Application window is not open, Application window" + loginBean.getAppFormEndDate();
					errorsList.add(temp);
					}
				}
				if (!((today == startDate1 || today > startDate1) && (today == endDate1 || today < endDate1))) {
					if(user.getStatusID() == 5) {
						temp = "";
						loginBean.setPaymentStartDate(CommonUtil.formatDate(new Date(startDate1), GenericConstants.DATE_FORMAT_DEFAULT));
						String newEndDate = CommonUtil.formatDate(new Date(endDate1), GenericConstants.DATE_FORMAT_DEFAULT1);
						String[] endDateFinal = newEndDate.split(" ");
						String newStartDate = CommonUtil.formatDate(new Date(startDate1), GenericConstants.DATE_FORMAT_DEFAULT1);
						String[] startDateFinal = newStartDate.split(" ");
						loginBean.setPaymentEndDate(" - "+ startDateFinal[0]+" " +startDateFinal[1]+" to "+endDateFinal[0]+" " +endDateFinal[1]+" hrs .");
						loginBean.setDisplayPaymentFlag("true");
						loginBean.setDisplayRegFlag("false");
						loginBean.setDisplayAppFlag("false");
						temp = "Cannot login as Application & Payment window is not open, Application & Payment window" + loginBean.getPaymentEndDate();	
						errorsList.add(temp);
					}
				}
			} else {
				errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CANDIDATE_LOGIN_REQD));
			}

			if (loginBean.getPassword() != null) {
				if (ValidatorUtil.isEmpty(loginBean.getPassword())) {
					errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CANDIDATE_pASSWORD_REQD));
				}
			} else {
				errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CANDIDATE_pASSWORD_REQD));
			}

			if (errorsList != null && !errorsList.isEmpty()) {
				errors = ValidatorUtil.validationMessageFormatter(errorsList);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return errors;
	}

	public String signout() throws Exception {

		String requestMethod = request.getMethod();
		if (!"POST".equalsIgnoreCase(requestMethod)) {
			return "errorPage";
		}
		if (loggedInUser != null && loggedInUser.getUserType() != null && !loggedInUser.getUserType().equals("") && loggedInUser.getUserType().equals("C")) {
			loggedInUser.setRemoteIp(request.getRemoteAddr());
			commonService.insertCandidateAuditrail(loggedInUser, "Sign Out", "Candidate with User Id " + loggedInUser.getUsername() + " Sign Out Successfully.");
		}
		if (request.getSession().getAttribute("CANDIDATE_TYPE") != null && !request.getSession().getAttribute("CANDIDATE_TYPE").equals("")) {
			request.getSession().removeAttribute("CANDIDATE_TYPE");
		}

		Object object = sessionAttributes.get("session");
		sessionAttributes.put(SESSION_USER, null);
		sessionAttributes.remove(SESSION_USER);
		request.getSession().removeAttribute("UserSessionBean");

		if (loggedInUser != null) {
			SessionMap strutsSession = (SessionMap) ActionContext.getContext().getSession();
			strutsSession.invalidate(); // invalidate
			strutsSession.put("renewServletSession", null); // renew servlet session
			strutsSession.remove("renewServletSession");
			strutsSession.entrySet(); // populate the struts session
		}

		/*sessionAttributes.put(SESSION_USER, null);
		request.getSession().invalidate();*/
		return "notloggedin";
	}

	public String goToHomePage() {
		String result = null;
		try {
			Users users = (Users) sessionAttributes.get(SESSION_USER);
			if (users == null) {
				result = "goToHomePage";
			} else if (users.getUserType().equals(GenericConstants.USER_TYPE_CANDIDATE)) {
				result = "goToCandidateHomePage";
			} else if (users.getUserType().equals(GenericConstants.USER_TYPE_SUPER_ADMIN)) {
				result = "goToSuperAdminHomePage";
			} else {
				result = "goToOtherHomePage";
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return result;
	}

	@Override
	public void setLoggedInUser(Users users) {
		this.loggedInUser = users;
	}

	@Override
	public void resetModel() {
		loginBean = new LoginBean();
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	Map<String, String> cookiesMap;

	@Override
	public void setCookiesMap(Map<String, String> cookiesMap) {
		this.cookiesMap = cookiesMap;
	}

	public String checkForgotPasswordStatus() {
		boolean bValidated = false;
		String userId = null;

		try {
			if (request.getParameter("userId") != null && !request.getParameter("userId").equals("")) {
				userId = (String) request.getParameter("userId");
				bValidated = loginService.checkForgotPassword(userId.toUpperCase());
				if (bValidated)
					request.getSession().setAttribute("USER_ID", userId);
			}
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, String.valueOf(bValidated));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return "writePlainText";

	}

	public String changePassword() throws Exception {

		String result = null;
		String serverSideErrors = "";
		int count = 0;

		RegistrationBean registrationBean = new RegistrationBean();
		try {

			registrationBean.setNewPwd(request.getParameter("newPassword") != null ? (String) request.getParameter("newPassword") : null);
			registrationBean.setRePwd(request.getParameter("confirmPassword") != null ? (String) request.getParameter("confirmPassword") : null);
			String userid = (String) request.getSession().getAttribute("USER_ID");

			registrationBean.setUserId(userid);
			serverSideErrors = changePasswordValidation(registrationBean);

			if (!ValidatorUtil.isEmpty(serverSideErrors)) {
				addActionMessage(serverSideErrors);
				result = "resetPassword";
				return result;
			} else if (loginService.oldPassword(registrationBean)) {
				addActionMessage("New password cannot be same as old password.</li>");
				result = "resetPassword";
				return result;
			} else {

				count = loginService.changePassword(registrationBean);
				if (count > 0) {
					Users users = new Users();
					users.setUsername(registrationBean.getUserId().trim());
					String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.CHANGE_PASSWORD_STATUS) + "";

					if (loggedInUser == null)
						loggedInUser = new Users();
					loggedInUser.setRemoteIp(request.getRemoteAddr());
					commonService.insertCandidateAuditrail(loggedInUser, "Reset Password", "Candidate with User Id " + loggedInUser.getUsername() + " has change his password.");

					request.setAttribute("SysGenUserID", registrationBean.getUserId());
					sessionAttributes.put(GenericConstants.LOGIN_FAILURE_COUNT, 0);
					SessionDetailsBean bean = new SessionDetailsBean();
					bean.setOUM_USER_ID(loginBean.getUsername());
					bean.setOUM_STATUS("A");
					bean.setOUM_INVAL_ATTEMPT_COUNT(Integer.parseInt(String.valueOf(sessionAttributes.get(GenericConstants.LOGIN_FAILURE_COUNT))));
					result = "resetPasswordSuccessful";
				} else {

					result = "resetPassword";
				}
				request.getSession().removeAttribute("SESSION_USER");

			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}

		return result;
	}

	private String changePasswordValidation(RegistrationBean registrationBean) {
		String errors = "";
		List<String> errorList = new ArrayList<String>();
		try {

			if (StringUtils.isNotBlank(registrationBean.getNewPwd()) && StringUtils.isNotBlank(registrationBean.getRePwd())) {

				if (registrationBean.getNewPwd().length() < 8) {
					errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.NEW_PASSWORD_LENGTH));
				} else if (!registrationBean.getNewPwd().equals(registrationBean.getRePwd())) {
					errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.NEW_PASSWORD_MATCH));
				}

			} else {
				if (StringUtils.isBlank(registrationBean.getNewPwd())) {
					errorList.add("Please enter new password.");
				}
				if (StringUtils.isBlank(registrationBean.getRePwd())) {
					errorList.add("Please enter confirm password.");
				}
			}

			if (errorList.size() > 0) {
				errors = ValidatorUtil.validationMessageFormatter(errorList);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return errors;
	}

	public String resetPassword() throws Exception {
		String result = null;

		try {
			String userid = request.getParameter("S6q0wB_54aa02") != null ? (String) request.getParameter("S6q0wB_54aa02") : null;
			if (userid != null && userid.indexOf("WxO") != -1) {
				String trimUserid = userid.substring(0, userid.indexOf("WxO"));
				String urlkey = userid.substring(userid.indexOf("WxO") + 3);
				if ((trimUserid.length()) % 8 == 0)
					userid = DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).decrypt(trimUserid);
				String key = loginService.checkForgotPasswordKey(userid);

				boolean bValidated = loginService.checkForgotPassword(userid);
				if (bValidated) {

					HttpSession session = request.getSession();
					session.setAttribute("USER_ID", userid);
					if (userid != null && key != null && key.equals(urlkey))
						result = "resetPassword";
					else
						result = "linkExpire";
				} else
					result = "linkExpire";
			} else
				result = "errorPage";

		} catch (Exception e) {
			e.printStackTrace();
			result = "errorPage";
		}

		return result;
	}

	public String checkFirstLoggedUser() {
		String strMsg = "";

		strMsg = loginService.getFirstTimeLoggedUser(loggedInUser.getUserId());
		String lProtocolType = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.PROTOCOL_TYPE);
		Cookie div = new Cookie("division", loggedInUser.getUsername());
		HttpSession session = request.getSession();
		String jSessionId = session.getId();

		if (null != lProtocolType && lProtocolType.equalsIgnoreCase("https")) {
			response.setHeader("SET-COOKIE", "JSESSIONID=" + jSessionId + "; Path=/MRBPCW; Secure;  HttpOnly");
			div.setPath(";Path=/MRBPCW;Secure; HttpOnly;");
			div.setSecure(true);
		} else {
			response.setHeader("SET-COOKIE", "JSESSIONID=" + jSessionId + "; Path=/MRBPCW; HttpOnly");
			div.setPath(";Path=/MRBPCW;HttpOnly;");
		}
		/* response.addCookie(div); */
		sessionAttributes.put(SESSION_USER, loggedInUser);
		request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, strMsg);
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
