package com.nseit.generic.util.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.nseit.generic.action.BaseAction;
import com.nseit.generic.models.Users;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.aware.UserAware;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class LoginInterceptor implements Interceptor {

	private static final long serialVersionUID = 6695194710735405783L;
	private org.apache.logging.log4j.Logger logger = LoggerHome.getLogger(getClass());
	private String domainName;

	public void destroy() {
	}

	public void init() {
	}

	public String intercept(ActionInvocation actionInvocation) throws Exception {
		HttpServletRequest request;
		HttpServletResponse httpServletResponse;
		// logger.info("Entering Loggin interceptor");
		// logger.info("Action name:
		// "+actionInvocation.getProxy().getActionName());

		// logger.info("Session:
		// "+actionInvocation.getInvocationContext().getSession().get(GenericConstants.SESSION_USER));

		request = ServletActionContext.getRequest();
		request.getSession();

		// logger.info("Session:
		// "+request.getSession().getAttribute(GenericConstants.SESSION_USER));

		if (actionInvocation.getProxy().getActionName().startsWith("LoginAction") || actionInvocation.getProxy().getActionName().compareTo("CandidateAction_registerCandidate") == 0
				|| actionInvocation.getProxy().getActionName().compareTo("RegistrationAction_generateOTP") == 0
				|| actionInvocation.getProxy().getActionName().compareTo("RegistrationAction_verifyOTP") == 0
				|| actionInvocation.getProxy().getActionName().compareTo("RegistrationAction_verifyEmailExists") == 0
				|| actionInvocation.getProxy().getActionName().compareTo("RegistrationAction_getAgeMatrixDetails") == 0
				|| actionInvocation.getProxy().getActionName().compareTo("RegistrationAction_forgotRegistrationNumber") == 0
				|| actionInvocation.getProxy().getActionName().compareTo("RegistrationAction_forgotPasswordOTP") == 0
				|| actionInvocation.getProxy().getActionName().compareTo("RegistrationAction_generateFpOTP") == 0
				|| actionInvocation.getProxy().getActionName().compareTo("RegistrationAction_forgotPassword") == 0
				|| actionInvocation.getProxy().getActionName().compareTo("RegistrationAction_forgotUserId") == 0
				|| actionInvocation.getProxy().getActionName().compareTo("CandidateAction_checkPhotoExist") == 0
				|| actionInvocation.getProxy().getActionName().compareTo("CandidateAction_navigateToAction") == 0
				|| actionInvocation.getProxy().getActionName().compareTo("CandidateAction_checkSignExist") == 0
				|| actionInvocation.getProxy().getActionName().compareTo("CandidateAction_updateScribeDetails") == 0
				|| actionInvocation.getProxy().getActionName().compareTo("RegistrationAction_changePasswordForFirstTime") == 0
				|| actionInvocation.getProxy().getActionName().compareTo("RegistrationAction_registerCandidate") == 0
				|| actionInvocation.getProxy().getActionName().compareTo("RegistrationAction_refreshCaptchaImage") == 0
				|| actionInvocation.getProxy().getActionName().compareTo("Index") == 0 || actionInvocation.getProxy().getActionName().compareTo("ContactUsAction") == 0
				|| actionInvocation.getProxy().getActionName().compareTo("CandidateAction_downloadDetails") == 0
				|| actionInvocation.getProxy().getActionName().compareTo("RedirectFinal") == 0
				|| actionInvocation.getProxy().getActionName().compareTo("PaymentOnlineAction_showPaymentScreen") == 0
				|| actionInvocation.getProxy().getActionName().compareTo("CandidateAction_createServerSidePDF") == 0
				|| actionInvocation.getProxy().getActionName().compareTo("LoginAction_loadInstructions") == 0
				|| actionInvocation.getProxy().getActionName().compareTo("CandidateAction_auditTrailForGenderConfirmation") == 0
				|| actionInvocation.getProxy().getActionName().compareTo("CovidDutyCertificateAction_displaySignedBy") == 0
				|| request.getSession().getAttribute(GenericConstants.SESSION_USER) != null) {

			Object action = actionInvocation.getAction();

			if (action instanceof UserAware) {
				((UserAware) action).setLoggedInUser((Users) request.getSession().getAttribute(GenericConstants.SESSION_USER));
			}

			if (action instanceof BaseAction) {
				((BaseAction) action).setActionInvocation(actionInvocation);
				((BaseAction) action).resetModel();
				((BaseAction) action).clearActionErrors();
				((BaseAction) action).clearMessages();
				((BaseAction) action).clearFieldErrors();
				((BaseAction) action).clearErrorsAndMessages();
				((BaseAction) action).clearErrors();
			}
			// fake hostname restriction
			domainName = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOMAIN_NAME);
			request = ServletActionContext.getRequest();
			httpServletResponse = ServletActionContext.getResponse();
			String host = request.getServerName();
			if (host != null && !(domainName.contains(host) || host.contains("examsstqc.nseitonline.com") || host.contains("www.techprocess.co.in"))) {
				logger.info("#####Invalid Host name..!! #Actual::" + host + " #Expected::" + domainName);
				httpServletResponse.sendError(403);
				return null;
			}
			/*
			 * (just for developer understanding) Referer: This header indicates
			 * the URL of the referring Web page. For example, if you are at Web
			 * page 1 and click on a link to Web page 2, the URL of Web page 1
			 * is included in the Referer header when the browser requests Web
			 * page 2. All major browsers set this header, so it is a useful way
			 * of tracking where requests come from. This capability is helpful
			 * for tracking advertisers who refer people to your site, for
			 * slightly changing content depending on the referring site, or
			 * simply for keeping track of where your traffic comes from. In the
			 * last case, most people simply rely on Web server log files, since
			 * the Referer is typically recorded there. Although the Referer
			 * header is useful, don't rely too heavily on it since it can
			 * easily be spoofed by a custom client. Finally, note that, due to
			 * a spelling mistake by one of the original HTTP authors, this
			 * header is Referer, not the expected Referrer.
			 */
			String ref = request.getHeader("Referer");
			String domainSplit[] = domainName.split(",");

			boolean flagRef = false;
			for (int i = 0; i < domainSplit.length; i++) {
				if (ref != null && ref.contains(domainSplit[i])) {
					flagRef = true;
					break;
				}
			}
			if (ref != null && !flagRef) {
				logger.info("#####Invalid Referer name..!! #Actual::" + ref + " #Expected::" + domainName);
				// httpServletResponse.sendError(403);
				// return null;
			}

			/*
			 * Host: (just for developer understanding) In HTTP 1.1, browsers
			 * and other clients are required to specify this header, which
			 * indicates the host and port as given in the original URL. Due to
			 * request forwarding and machines that have multiple hostnames, it
			 * is quite possible that the server could not otherwise determine
			 * this information. This header is not new in HTTP 1.1, but in HTTP
			 * 1.0 it was optional, not required.
			 */
			String hostHeader = request.getHeader("Host");

			boolean flag = false;
			for (int i = 0; i < domainSplit.length; i++) {
				if (hostHeader != null && hostHeader.contains(domainSplit[i])) {
					flag = true;
					break;
				}
			}
			if (hostHeader != null && !flag) {
				logger.info("#####Invalid Host Header name..!! #Actual::" + hostHeader + " #Expected::" + domainName);
				httpServletResponse.sendError(403);
				return null;
			}
			// [START NPCIL(VAPT)-XSS]
			boolean isValidParameterValues = CommonUtil.validateRequestParameters(request);

			if (!isValidParameterValues && request.getRequestURL() != null && !request.getRequestURL().toString().contains("TechProcessResponseAction")) {
				// request.getSession().setAttribute(OESConstants.DISPLAY_MESSAGE_HEADER,
				// "Error");
				// request.getSession().setAttribute(OESConstants.DISPLAY_MESSAGE_ERROR,
				// ResourceUtil.getJavaErrorMessageProperty(JavaErrorMessageKeyConstants.APPLICATION_WHITELIST_FAILURE));
				logger.info("#####VAPT Invalid ParameterValues Found..!!");
				httpServletResponse.sendRedirect("LoginAction_input.action");
				return null;
			}
			// [END NPCIL(VAPT)-XSS]
			Users user = (Users) request.getSession().getAttribute(GenericConstants.SESSION_USER);
			String actionName = actionInvocation.getProxy().getActionName();
			if (actionName != null
					&& (actionName.contains("TemplateDownloadAction") || actionName.contains("SettingsAction") || actionName.contains("AgencyAction")
							|| actionName.contains("CandidateMgmtAction") || actionName.contains("BillDeskJobsAction") || actionName.contains("ModuleMasterAction"))
					&& user != null && !"S".equals(user.getUserType()) && !"CA".equals(user.getUserType()) && !"NA".equals(user.getUserType())
					&& !"HC".equals(user.getUserType())) {
				logger.info("#####VAPT Invalid Admin User Found..!!");
				return "errorPage";
			}
			return actionInvocation.invoke();
		} else {
			logger.info("not loggedIn Class name LoginI..!!");
			return "notloggedin";
		}
	}

}
