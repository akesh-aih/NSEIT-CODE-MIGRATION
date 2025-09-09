package com.nseit.generic.service.impl;

import static com.nseit.generic.util.GenericConstants.DESTINATION_URL;
import static com.nseit.generic.util.GenericConstants.SESSION_USER;

import java.awt.Window;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;

import com.nseit.generic.dao.CommonDao;
import com.nseit.generic.dao.LoginDao;
import com.nseit.generic.models.LoginBean;
import com.nseit.generic.models.RegistrationBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.LoginService;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.DESEncrypter;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.GenericServiceException;
import com.nseit.generic.util.LoggerHome;
import com.opensymphony.xwork2.ActionContext;

public class LoginServiceImpl implements LoginService {
	private LoginDao loginDao;
	private CommonDao commonDao;
	private Logger logger = LoggerHome.getLogger(getClass());
	protected Users loggedInUser;

	public void setLoggedInUser(Users loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}

	public Users getUserForUsername(String username) throws Exception {

		return loginDao.getUserForUsername(username);
	}

	@Override
	public boolean checkForgotPassword(String Userid) throws Exception {
		return loginDao.checkForgotPassword(Userid);
	}

	public int changePassword(RegistrationBean registrationBean) throws Exception {

		int count = 0;
		try {
			registrationBean.setNewPwd(DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).encrypt(registrationBean.getNewPwd()));
			count = loginDao.changePassword(registrationBean);
			LoginBean bean = new LoginBean();
			bean.setActiveStatus("A");
			bean.setInvalidCount(0);
			bean.setUsername(registrationBean.getUserId());
			loginDao.updateUserStatus(bean);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		return count;
	}

	@Override
	public String checkForgotPasswordKey(String Userid) throws Exception {
		return loginDao.checkForgotPasswordKey(Userid);
	}

	public boolean oldPassword(RegistrationBean registrationBean) throws Exception {

		boolean count = false;

		try {
			registrationBean.setNewPassword(DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).encrypt(registrationBean.getNewPwd()));
			count = loginDao.oldPassword(registrationBean);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return count;

	}

	@Override
	public loginServiceConstants authenticate(LoginBean loginBean, HttpServletRequest request, Map<String, Object> sessionAttributes, HttpServletResponse response,
			Map<String, Object> requestAttributes) throws GenericException, Exception {
		Integer currFailureCount = 0;
		Users userPreData = loginDao.getUserForUsername(loginBean.getUsername().trim());
		
		if (userPreData != null) {
			if (userPreData.getActiveStatus() != null && userPreData.getActiveStatus().equalsIgnoreCase("B")) {
				logger.info("User try to login with blocked user Id :" + loginBean.getUsername());
				return loginServiceConstants.BLOCKED_USER;
			}
		}
		String browserVersion = (String) request.getSession().getAttribute("BROWSER_VERSION");
		SessionMap session = (SessionMap) ActionContext.getContext().getSession();
		session.invalidate();
		
		HttpServletResponse httpServletResponse = ServletActionContext.getResponse();
		String jSessionId = request.getSession().getId();
		
		String lProtocolType = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.PROTOCOL_TYPE);
		if (null != lProtocolType && lProtocolType.equalsIgnoreCase("https")) {
			httpServletResponse.setHeader("SET-COOKIE", "JSESSIONID=" + jSessionId + "; Path=/MRBPCW; Secure;  HttpOnly; SameSite=none;");
		} else {
			httpServletResponse.setHeader("SET-COOKIE", "JSESSIONID=" + jSessionId + "; Path=/MRBPCW; HttpOnly");
		}
		
				
		/*if (sessionAttributes.get(GenericConstants.LOGIN_FAILURE_COUNT) == null) {
			sessionAttributes.put(GenericConstants.LOGIN_FAILURE_COUNT, currFailureCount);
			sessionAttributes.put("loginuserid", "XYZ");
		}
		if (null != String.valueOf(sessionAttributes.get("loginuserid")) && !loginBean.getUsername().equalsIgnoreCase(String.valueOf(sessionAttributes.get("loginuserid")))) {*/
			if (userPreData != null && userPreData.getUserId() > 0) {
				sessionAttributes.put(GenericConstants.LOGIN_FAILURE_COUNT, userPreData.getInvalidCount());
				sessionAttributes.put("loginuserid", loginBean.getUsername());
				sessionAttributes.put("checkDeclaration", userPreData.getFirstLogin());
			} else {
				sessionAttributes.put(GenericConstants.LOGIN_FAILURE_COUNT, 0);
			}
		/*}*/
		currFailureCount = (Integer) sessionAttributes.get(GenericConstants.LOGIN_FAILURE_COUNT);
		setLoggedInUser(userPreData);

		Users users = null;
		String sa = request.getParameter("sa").trim();
		String iv = request.getParameter("iv").trim();
		SecretKey key = CommonUtil.generateKeyFromPassword(iv, CommonUtil.hexStringToByteArray(sa));
		byte[] ivBytes = CommonUtil.hexStringToByteArray(iv);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
		if (userPreData != null) {
			String password = CommonUtil.getDecryptedPassword(loginBean.getPassword(), (SecretKeySpec) key, ivParameterSpec);
			String decryptedDBPassword = DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).decrypt(userPreData.getPassword());
			if (decryptedDBPassword.compareTo(password) == 0) {
				users = userPreData;
			}
		}
		
		if (users == null) {
			loginBean.setCaptcha("");
			currFailureCount++;
			sessionAttributes.put(GenericConstants.LOGIN_FAILURE_COUNT, currFailureCount);
			int AccountLockCnt = Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.ACCOUNT_LOCK_COUNT));
			if (Integer.parseInt(String.valueOf(sessionAttributes.get(GenericConstants.LOGIN_FAILURE_COUNT))) > AccountLockCnt) {
				LoginBean bean = new LoginBean();
				bean.setUsername(loginBean.getUsername());
				bean.setActiveStatus("D");
				bean.setInvalidCount(Integer.parseInt(String.valueOf(sessionAttributes.get(GenericConstants.LOGIN_FAILURE_COUNT))));
				loginDao.updateUserStatus(bean);
				return loginServiceConstants.ACCOUNT_LOCK;
			} else {
				LoginBean bean = new LoginBean();
				bean.setUsername(loginBean.getUsername());
				bean.setActiveStatus("A");
				bean.setInvalidCount(Integer.parseInt(String.valueOf(sessionAttributes.get(GenericConstants.LOGIN_FAILURE_COUNT))));
				loginDao.updateUserStatus(bean);
				return loginServiceConstants.INVALID_LOGIN_CREDENTIALS;
			}
		} else {
			if (users.getActiveStatus() != null && users.getActiveStatus().equalsIgnoreCase("D")) {
				return loginServiceConstants.ACCOUNT_LOCK;
			}
			sessionAttributes.put(GenericConstants.LOGIN_FAILURE_COUNT, 0);
			if (request.getParameter("loginPageReq") != null) {
				users.setLoginPageReq(request.getParameter("loginPageReq"));
			}

			request.getSession().setMaxInactiveInterval(Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SESSION_TIMEOUT)));
			sessionAttributes.put(SESSION_USER, users);
			if (users.getUserType().compareTo(GenericConstants.USER_TYPE_CANDIDATE) == 0) {
				users.setRemoteIp(request.getRemoteAddr());
				logger.info("User ID:" + users.getUsername() + " has logged in with browserVersion :" + browserVersion);
				commonDao.insertCandidateAuditrail(users, "Login Page-Login", "Candidate User Id : " + users.getUsername() + " ~ Login:Successfully ~ Browser version : " + browserVersion);

				if (users.getStage().compareTo("0") == 0) {
					Map<Integer, List<Long>> dateWindowMap = ConfigurationConstants.getInstance().getDateWindowMap();
					List<Long> dateList = dateWindowMap.get(1);
					Long startDate = dateList.get(0);
					Long endDate = dateList.get(1);
					long today = CommonUtil.getCurrentDateInMillis();
					if ((today == startDate || today > startDate) && (today == endDate || today < endDate)) {
						request.setAttribute("isInstPage", "true");
						return loginServiceConstants.INSTRUCTION_PAGE;
					} else {
						loginBean.setDisplayRegFlag("true");
						return loginServiceConstants.CLOSE_WINDOW;
					}
				} else {
					request.setAttribute(DESTINATION_URL, "CandidateAction_home.action");
					LoginBean bean = new LoginBean();
					bean.setUsername(loginBean.getUsername());
					bean.setActiveStatus("A");
					bean.setInvalidCount(0);
					loginDao.updateUserStatus(bean);
					return loginServiceConstants.REDIRECT_ACTION_URL;
				}
			} else if (users.getUserType().compareTo(GenericConstants.USER_TYPE_NSEIT) == 0) {
				return loginServiceConstants.SUCCESS_NSEIT;

			} else if (users.getUserType().compareTo(GenericConstants.USER_TYPE_GCET_ADMIN) == 0) {
				request.setAttribute(DESTINATION_URL, "AgencyAction_getGcetLoginHome.action");
				return loginServiceConstants.REDIRECT_ACTION_URL;

			} else if (users.getUserType().compareTo(GenericConstants.USER_TYPE_QB_ADMIN) == 0) {
				return loginServiceConstants.SUCCESS_QST_BANK;

			} else if (users.getUserType().compareTo(GenericConstants.USER_TYPE_GCET_NODAL) == 0) {
				return loginServiceConstants.SUCCESS_NODAL;

			} else if (users.getUserType().compareTo(GenericConstants.USER_TYPE_SUPER_ADMIN) == 0) {
				request.setAttribute(DESTINATION_URL, "AgencyAction_getGcetLoginHome.action");
				return loginServiceConstants.REDIRECT_ACTION_URL;

			} else {
				return loginServiceConstants.SUCCESS_USER;
			}

		}
	}

	@Override
	public String getFirstTimeLoggedUser(Long userFk) {
		return loginDao.getFirstTimeLoggedUser(userFk);
	}
	
	@Override
	public Users getUser(String username)throws Exception{
		Users userPreData = loginDao.getUserForUsername(username.trim());
		return userPreData;
	}
	@Override
	public String getStatusForUsername(String username){
		String status = loginDao.getStatusForUsername(username.trim());
		return status;
	}
}
