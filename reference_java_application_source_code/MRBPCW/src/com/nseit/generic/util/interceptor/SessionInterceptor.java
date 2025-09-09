package com.nseit.generic.util.interceptor;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;

import com.atom.ots.enc.AtomEncryption;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.LoginService;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.SpringUtil;
import com.nseit.payment.models.PayInstrument;
import com.nseit.payment.models.ResponseParser;
import com.nseit.payment.service.PaymentService;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * @author shailendras
 *
 */

public class SessionInterceptor implements Interceptor {

	private static final long serialVersionUID = -896592544814428765L;
	private Logger logger = LoggerHome.getLogger(getClass());

	private LoginService loginService;
	private PaymentService paymentService;

	@Override
	public void init() {
		logger.info("SessionInterceptor init is calling");

	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		HttpServletRequest request;
		HttpServletResponse httpServletResponse;
		// logger.info("Entering session interceptor");
		request = ServletActionContext.getRequest();
		httpServletResponse = ServletActionContext.getResponse();
		request.getRequestURL();
		// <IE11 browsers disabled
		String userAgent = request.getHeader("User-Agent");

		// System.out.println("Durgesh"+userAgent);
		String userAgentLow = userAgent.toLowerCase();

		String browser = "";
		if (userAgentLow == null) {
			httpServletResponse.sendRedirect("/MRBPCW/browserPage.jsp");
		} else {
			/*
			 * if(userAgent != null && (userAgentLow.contains("mobile") ||
			 * userAgentLow.contains("android") || userAgentLow.contains("webos") ||
			 * userAgentLow.contains("iphone") || userAgentLow.contains("ipad") ||
			 * userAgentLow.contains("ipod") || userAgentLow.contains("blackberry") ||
			 * userAgentLow.contains("iemobile") || userAgentLow.contains("opera mini"))) {
			 * httpServletResponse.sendRedirect("/MRBPCW/nomobile.jsp"); }
			 */
			 
			if (userAgentLow.contains("msie")) {
				String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
				browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
			} else if (userAgentLow.contains("opr") || userAgentLow.contains("opera")) {
				if (userAgentLow.contains("opera"))
					browser = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0] + "-"
							+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
				else if (userAgentLow.contains("opr"))
					browser = ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
			} else if (userAgentLow.contains("safari") && userAgentLow.contains("version")) {
				browser = (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0] + "-"
						+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
			}
			// maintain this order of browser names or else by default chrome will be selected
			else if (userAgentLow.contains("firefox")) {
				browser = (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
			} else if (userAgentLow.contains("rv")) {
				browser = "IE-" + userAgentLow.substring(userAgentLow.indexOf("rv") + 3, userAgentLow.indexOf(")"));
			} else if (userAgentLow.contains("edge")) {
				browser = (userAgent.substring(userAgent.indexOf("Edge")).split(" ")[0]).replace("/", "-");
			} else if (userAgentLow.contains("chrome")) {
				browser = (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
			} else if (userAgentLow.contains("fxios")) {
				browser = (userAgent.substring(userAgent.indexOf("FxiOS")).split(" ")[0]).replace("/", "-");
			} else if ((userAgentLow.indexOf("mozilla/7.0") > -1) || (userAgentLow.indexOf("netscape6") != -1) || (userAgentLow.indexOf("mozilla/4.7") != -1)
					|| (userAgentLow.indexOf("mozilla/4.78") != -1) || (userAgentLow.indexOf("mozilla/4.08") != -1) || (userAgentLow.indexOf("mozilla/3") != -1)) {
				// browser=(userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[0]).replace("/", "-");
				browser = "Netscape-?";
			} else if (userAgentLow.contains("crios")) {
				browser = (userAgentLow.substring(userAgentLow.indexOf("crios")).split(" ")[0]).replace("/", "-");
			} else {
				browser = "UnKnown, More-Info: " + userAgent;
			}
		}
		Double browserVersion = 0.0d;
		request.getSession().setAttribute("BROWSER_VERSION", browser);
		String[] browserArr;

		if (browser.contains("Chrome")) {
			browserArr = browser.split("-");
			String browserArr1 = browserArr[1];
			String[] browserVersionCheck = browserArr1.split("\\.");
			if (null != browserVersionCheck[0])
				if (Integer.parseInt(browserVersionCheck[0]) < 86) {
					logger.info("#####VAPT Invalid browserVersion Found..!! " + browser + " browserVersion : " + browserArr);
					httpServletResponse.sendRedirect("/MRBPCW/browserPage.jsp");
				}
		} else if (browser.contains("crios")) {
			browserArr = browser.split("-");
			String browserArr1 = browserArr[1];
			String[] browserVersionCheck = browserArr1.split("\\.");
			if (null != browserVersionCheck[0])
				if (Integer.parseInt(browserVersionCheck[0]) < 86) {
					logger.info("#####VAPT Invalid browserVersion Found..!! " + browser + " browserVersion : " + browserArr);
					httpServletResponse.sendRedirect("/MRBPCW/browserPage.jsp");
				}
		} else if (browser.contains("Firefox")) {
			browserArr = browser.split("-");
			if (null != browserArr[1])
				try {
					browserVersion = Double.parseDouble(browserArr[1]);
				} catch (NumberFormatException nfe) {
				}
			if (browserVersion < 86.0d) {
				logger.info("#####VAPT Invalid browserVersion Found..!! " + browser + " browserVersion :" + browserVersion);
				httpServletResponse.sendRedirect("/MRBPCW/browserPage.jsp");
			}
		} else if (browser.contains("Edge")) {
			browserArr = browser.split("-");
			if (null != browserArr[1])
				try {
					browserVersion = Double.parseDouble(browserArr[1]);
				} catch (NumberFormatException nfe) {
				}
			if (browserVersion < 14.0d) {
				logger.info("#####VAPT Invalid browserVersion Found..!! " + browser + " browserVersion :" + browserVersion);
				httpServletResponse.sendRedirect("/MRBPCW/browserPage.jsp");
			}
		} else if (browser.contains("Safari")) {
			browserArr = browser.split("-");
			String browserArr1 = browserArr[1];
			String[] browserVersionCheck = browserArr1.split("\\.");
			if (null != browserVersionCheck[0] && !"Info: Mozilla/5".equals(browserVersionCheck[0]))
			{
				if (Integer.parseInt(browserVersionCheck[0]) < 3) {
					logger.info("#####VAPT Invalid browserVersion Found..!! " + browser + " browserVersion : " + browserArr);
					httpServletResponse.sendRedirect("/MRBPCW/browserPage.jsp");
				}
			}
		} else {
			logger.info("#####VAPT Invalid browserVersion Found..!! " + browser + " browserVersion :" + browserVersion);
			httpServletResponse.sendRedirect("/MRBPCW/browserPage.jsp");
		}

		// Allow only GET and POST
		if (!(request.getMethod().equalsIgnoreCase("POST") || request.getMethod().equalsIgnoreCase("GET"))) {
			((HttpServletResponse) httpServletResponse).setStatus(HttpServletResponse.SC_FORBIDDEN);
			((HttpServletResponse) httpServletResponse).setHeader("Location", "errorPage.jsp");
			logger.info("#####VAPT Invalid GET and POST Found..!! request.getMethod():" + request.getMethod());
			return null;

		}
		
		// [START NPCIL(VAPT)-XSS]
		boolean isValidParameterValues = CommonUtil.validateRequestParameters(request);

		if (!isValidParameterValues && request.getRequestURL() != null && !request.getRequestURL().toString().contains("TechProcessResponseAction")
				&& !request.getRequestURL().toString().contains("saveAddDocVerify")) {
			// request.getSession().setAttribute(OESConstants.DISPLAY_MESSAGE_HEADER,
			// "Error");
			// request.getSession().setAttribute(OESConstants.DISPLAY_MESSAGE_ERROR,
			// ResourceUtil.getJavaErrorMessageProperty(JavaErrorMessageKeyConstants.APPLICATION_WHITELIST_FAILURE));
			httpServletResponse.sendRedirect("LoginAction_input.action");
			logger.info("#####VAPT Invalid ParameterValues Found.. !!!");
			return null;
		}
		// [END NPCIL(VAPT)-XSS]

		ApplicationContext applicationContext = SpringUtil.getInstance().getApplicationContext();
		loginService = (LoginService) applicationContext.getBean("loginService");
		ApplicationContext applicationContext1 = SpringUtil.getInstance().getApplicationContext();
		paymentService = (PaymentService) applicationContext1.getBean("paymentService");

		Users user = null;
		// HttpSession session = request.getSession();

		// 0. Every session has its unique id
		// 1. It will give back your old session
		// 2. OR if old session is not found, then it will create new session
		// HttpSession session = request.getSession();

		// It will be null if there is no old session
		HttpSession session = request.getSession();
		// logger.info(invocation.getInvocationContext().getSession().get(GenericConstants.SESSION_USER));
		if (invocation.getProxy().getActionName().equals("AtomResponseAction")) {

			if (invocation.getInvocationContext().getSession().get(GenericConstants.SESSION_USER) == null) {

				String key = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AIPAY_DECRYPTION_KEY);
				String responseMsg = request.getParameter("encData");

				logger.info("Enterd in response from ATOM block processing:");
				logger.info("response from ATOM from SessionInterceptor print in case of jsessionId issue encrpted:" + responseMsg);
				if (responseMsg != null && !responseMsg.equals("")) {

					String decryptedResponse = AtomEncryption.decrypt(responseMsg, key);
					logger.info("ATOM decrypted responseMsg ===:" + decryptedResponse);

					ObjectMapper objectMapper = new ObjectMapper();
					ResponseParser resp = objectMapper.readValue(decryptedResponse, ResponseParser.class);
					PayInstrument payInstrument = resp.getPayInstrument();

					String userID = payInstrument.getExtras().getUdf1();

					logger.info("Response from ATOM from SessionInterceptor print in case of jsessionId issue after decrpt:" 
							+ decryptedResponse + "for transaction number " + userID);

					logger.info("User id " + userID);

					user = loginService.getUserForUsername(userID.trim());
					session.setAttribute("SESSION_USER", user);
				}
			}
		}

		if (session.getAttribute("SESSION_USER") != null) {
			user = (Users) session.getAttribute("SESSION_USER");
		}
		
		// [START NPCIL(VAPT)-Prevent Caching at browser]
		httpServletResponse.addHeader("Content-Security-Policy", "script-src 'unsafe-inline' 'unsafe-eval' 'self'");
		httpServletResponse.addHeader("Strict-Transport-Security", "max-age=31622400; includeSubDomains");

		httpServletResponse.addHeader("X-XSS-Protection", "1; mode=block");
		// httpServletResponse.addHeader("Content-Type", "text/html");
		httpServletResponse.addHeader("X-Content-Type-Options", "nosniff");

		httpServletResponse.addHeader("Cache-Control", "max-age = 0, s-maxage = 0,must-revalidate, proxy-revalidate, no-cache, no-store ");
		httpServletResponse.addHeader("Pragma", "no-cache");
		httpServletResponse.setDateHeader("Expires", 0);
		// [END NPCIL(VAPT)-Prevent Caching at browser]

		// [START NPCIL(VAPT)-Prevent Clickjacking]
		httpServletResponse.addHeader("X-FRAME-OPTIONS", "SAMEORIGIN");
		// [END NPCIL(VAPT)-Prevent Clickjacking]
		
		// HttpSession session=httpServletRequest.getSession();
		String jSessionId = session.getId();
		// logger.info("Jsession Id"+jSessionId);
		// System.out.println("jSessionId:"+jSessionId);
		/*
		 * if(httpServletResponse.containsHeader("SET-COOKIE")) { //[START
		 * NPCIL(VAPT)-Set cookie as HttpOnly - Cookies can not be accessed by
		 * any client side script] httpServletResponse.setHeader("SET-COOKIE",
		 * "JSESSIONID="+jSessionId+"; Path=/TNU; HttpOnly"); //[START
		 * NPCIL(VAPT)-Set cookie as HttpOnly] }
		 */

		// These code replacing Https /Http protocal while givving VAPT testing
		// change PROTOCOL_TYPE value to Https.in other
		// case make it http
		String lProtocolType = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.PROTOCOL_TYPE);
		if (null != lProtocolType && lProtocolType.equalsIgnoreCase("https")) {
			httpServletResponse.setHeader("SET-COOKIE", "JSESSIONID=" + jSessionId + "; Path=/MRBPCW; Secure;  HttpOnly");
		} else {
			httpServletResponse.setHeader("SET-COOKIE", "JSESSIONID=" + jSessionId + "; Path=/MRBPCW; Secure; HttpOnly");
		}

		return invocation.invoke();
	}

	static String Decrypt(String encData) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		String password = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AIPAY_DECRYPTION_KEY);
		String salt = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AIPAY_DECRYPTION_IV);
		
		int pswdIterations = 65536;
		int keySize = 256;
		final byte[] ivBytes = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
		byte[] saltBytes = salt.getBytes("UTF-8");
		byte[] encryptedTextBytes = hex2ByteArray(encData);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, pswdIterations, keySize);
		SecretKey secretKey = factory.generateSecret(spec);
		SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
		IvParameterSpec localIvParameterSpec = new IvParameterSpec(ivBytes);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(2, secret, localIvParameterSpec);
		byte[] decryptedTextBytes = (byte[]) null;
		decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
		return new String(decryptedTextBytes);
	}

	static byte[] hex2ByteArray(String sHexData) {
		byte[] rawData = new byte[sHexData.length() / 2];
		for (int i = 0; i < rawData.length; ++i) {
			int index = i * 2;
			int v = Integer.parseInt(sHexData.substring(index, index + 2), 16);
			rawData[i] = (byte) v;
		}
		return rawData;
	}

	@Override
	public void destroy() {

	}
}
