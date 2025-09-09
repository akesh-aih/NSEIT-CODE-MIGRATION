package com.nseit.generic.util.emailsms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.nseit.generic.models.EmailSMSMasterBean;
import com.nseit.generic.models.EmailSMSTransactionBean;
import com.nseit.generic.models.RegistrationBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.CommonService;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.DESEncrypter;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.ParseXml;
import com.nseit.generic.util.SMSResponseBean;
import com.nseit.generic.util.SpringUtil;

public abstract class EmailSMSUtil {
	private static Map<String, String> systemParameterNDBParameterMap = ConfigurationConstants.getInstance().getSystemParameterNDBParameterMap();

	private static int[] insertEmailSMSDetails(CommonService commonService, List<EmailSMSTransactionBean> emailSMS, String username) throws Exception {
		return commonService.insertEmailSMSDetails(emailSMS, username);
	}

	public static List<Integer> insertEmailNSMSForPaymentFailure(Users users, String staus, Integer emailReq, Integer smsReqd) throws GenericException {
		List<Integer> lstEmailSMSStatus = new ArrayList<Integer>();
		try {
			if ((emailReq != null && !emailReq.equals("") && emailReq.equals(new Integer(1))) || (smsReqd != null && !smsReqd.equals("") && smsReqd.equals(new Integer(1)))) {
				lstEmailSMSStatus.add(insertEmailSMSForPaymentFailure(users, staus, emailReq, smsReqd));
			}
		} catch (Exception ex) {
			LoggerHome.getLogger(EmailSMSUtil.class).fatal("Error sending Email/SMS" + ex);
		}
		return lstEmailSMSStatus;
	}

	private static Integer insertEmailSMSForPaymentFailure(Users users, String status, Integer emailReq, Integer smsReqd) throws Exception {
		int[] result = null; // -1-> Error Sending Email, 0-> Not Applicable, 1->
		CommonService commonService = null;
		String mailContent = null;
		String subject = null;
		ApplicationContext applicationContext = SpringUtil.getInstance().getApplicationContext();
		commonService = (CommonService) applicationContext.getBean("commonService");
		Map<String, String> candidateValues = commonService.getCandidatesDataForEmailSMSForPayment(users.getUsername());
		if (candidateValues != null && !candidateValues.isEmpty()) {
			List<EmailSMSTransactionBean> emailSMS = new ArrayList<>();
			if (emailReq.equals(1)) {
				EmailSMSTransactionBean emailSMSTransactionBean = new EmailSMSTransactionBean();
				if (StringUtils.isNotBlank(candidateValues.get("mail_address"))) {
					subject = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.PAYMENT_REJECTED_EMAIL_SUBJECT);
					mailContent = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.PAYMENT_REJECTED_EMAIL);
					mailContent = replaceSystemParametersInEmailSMSObject(mailContent, candidateValues);
					emailSMSTransactionBean.setEmailSubject(subject);
					emailSMSTransactionBean.setSmsEmailContent(mailContent);
					emailSMSTransactionBean.setFromAddress("");
					emailSMSTransactionBean.setCandidateMailAddress(candidateValues.get("mail_address"));
					emailSMSTransactionBean.setSmsMailFlag(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.EMAIL_SMS_FLAG_EMAIL_VALUE));
					emailSMSTransactionBean.setStatusPk(status);
					emailSMSTransactionBean.setUserPk(users.getUserFk());
					emailSMS.add(emailSMSTransactionBean);
				}
			}
			if (smsReqd.equals(1)) {
				EmailSMSTransactionBean emailSMSTransactionBean = new EmailSMSTransactionBean();
				if (StringUtils.isNotBlank(candidateValues.get("mobile_no"))) {
					mailContent = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.PAYMENT_REJECTED_SMS);
					mailContent = replaceSystemParametersInEmailSMSObject(mailContent, candidateValues);
					emailSMSTransactionBean.setSmsEmailContent(mailContent);
					emailSMSTransactionBean.setFromAddress("");
					emailSMSTransactionBean.setCandidateMailAddress(candidateValues.get("mobile_no"));
					emailSMSTransactionBean.setSmsMailFlag(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.EMAIL_SMS_FLAG_SMS_VALUE));
					emailSMSTransactionBean.setStatusPk(status);
					emailSMSTransactionBean.setUserPk(users.getUserFk());
					emailSMS.add(emailSMSTransactionBean);
					result = insertEmailSMSDetails(commonService, emailSMS, users.getUsername());
				}
			}
		}
		return smsReqd;
	}

	public static void sendEmail(EmailSMSTransactionBean emailSMSTransactionBean, JavaMailSender mailSender) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setText(emailSMSTransactionBean.getSmsEmailContent(), true);
		helper.setSubject(emailSMSTransactionBean.getEmailSubject());
		if (emailSMSTransactionBean.getToAddress() != null && emailSMSTransactionBean.getToAddress().size() > 0)
			helper.setTo(emailSMSTransactionBean.getToAddress().toArray(new String[0]));
		if (emailSMSTransactionBean.getCcAddress() != null && emailSMSTransactionBean.getCcAddress().size() > 0)
			helper.setCc(emailSMSTransactionBean.getCcAddress().toArray(new String[0]));
		if (emailSMSTransactionBean.getBccAddress() != null && emailSMSTransactionBean.getBccAddress().size() > 0)
			helper.setBcc(emailSMSTransactionBean.getBccAddress().toArray(new String[0]));
		mailSender.send(message);
	}

	public static void sendEmailMailBySite(EmailSMSTransactionBean emailSMSTransactionBean, JavaMailSender mailSender) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setText(emailSMSTransactionBean.getSmsEmailContent(), true);
			helper.setSubject(emailSMSTransactionBean.getEmailSubject());
			helper.setFrom(new InternetAddress(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.EMAIL_SENDER)));
			if (emailSMSTransactionBean.getToAddress() != null && emailSMSTransactionBean.getToAddress().size() > 0)
				helper.setTo(emailSMSTransactionBean.getToAddress().toArray(new String[0]));
			if (emailSMSTransactionBean.getCcAddress() != null && emailSMSTransactionBean.getCcAddress().size() > 0)
				helper.setCc(emailSMSTransactionBean.getCcAddress().toArray(new String[0]));
			if (emailSMSTransactionBean.getBccAddress() != null && emailSMSTransactionBean.getBccAddress().size() > 0)
				helper.setBcc(emailSMSTransactionBean.getBccAddress().toArray(new String[0]));
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	// FOR NET CORE
	public static List sendSms(EmailSMSTransactionBean emailSMSTransactionBean) throws SendFailedException {
		List<String> lstMobileNumbers = emailSMSTransactionBean.getToAddress();
		String message = emailSMSTransactionBean.getSmsEmailContent();
		List<SMSResponseBean> returnList = new ArrayList<SMSResponseBean>();
		try {
			boolean sendFlag = true;
			SMSResponseBean smsResponseBean = null;
			String strMobileNo = "";
			String responseString = "";
			StringBuffer strXmlString = new StringBuffer("");
			StringBuffer strSmsUrl = new StringBuffer("");
			ParseXml parseXml = new ParseXml();
			URL url = null;
			URLConnection urlConnection = null;
			BufferedReader bufferedReader = null;
			String smsURL = CommonUtil.getParameter(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SMS_URL));
			String smsFeedId = CommonUtil.getParameter(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SMS_FEED_ID));
			String username = CommonUtil.getParameter(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SMS_USERNAME));
			String password = CommonUtil.getParameter(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SMS_PASSWORD));
			String time = CommonUtil.getParameter(
					CommonUtil.formatTimeStamp(CommonUtil.getCurrentTimestamp(), ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SMS_TIME_FORMAT)));
			String senderId = CommonUtil.getParameter(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SMS_SENDER_ID));
			String keyword = CommonUtil.getParameter(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SMS_KEYWORD));
			String finalURL = null;
			smsResponseBean = new SMSResponseBean();
			if (smsURL.compareTo("") == 0) {
				smsResponseBean.setCode("system");
				smsResponseBean.setDesc(smsResponseBean.getDesc() + " || " + " SMS Service URL not Proper ! ");
				sendFlag = false;
			}
			if (smsFeedId.compareTo("") == 0) {
				smsResponseBean.setCode("system");
				smsResponseBean.setDesc(smsResponseBean.getDesc() + " || " + " SMS Feed Id not Proper ! ");
				sendFlag = false;
			}
			if (username.compareTo("") == 0) {
				smsResponseBean.setCode("system");
				smsResponseBean.setDesc(smsResponseBean.getDesc() + " || " + " SMS UserName not Proper ! ");
				sendFlag = false;
			}
			if (password.compareTo("") == 0) {
				smsResponseBean.setCode("system");
				smsResponseBean.setDesc(smsResponseBean.getDesc() + " || " + " SMS Password not Proper ! ");
				sendFlag = false;
			}
			if (message.compareTo("") == 0) {
				smsResponseBean.setCode("system");
				smsResponseBean.setDesc(smsResponseBean.getDesc() + " || " + " No Message available to be sent ! ");
				sendFlag = false;
			}
			if (senderId.compareTo("") == 0) {
				smsResponseBean.setCode("system");
				smsResponseBean.setDesc(smsResponseBean.getDesc() + " || " + " SenderId unavailable ! ");
				sendFlag = false;
			}
			if (keyword.compareTo("") == 0) {
				smsResponseBean.setCode("system");
				smsResponseBean.setDesc(smsResponseBean.getDesc() + " || " + " SMS Keyword not Proper ! ");
				sendFlag = false;
			}
			if (lstMobileNumbers == null || lstMobileNumbers.size() == 0) {
				smsResponseBean.setCode("system");
				smsResponseBean.setDesc(smsResponseBean.getDesc() + " || " + " No Receipt for SMS ! ");
				sendFlag = false;
			}
			if (sendFlag) {
				strSmsUrl.append(smsURL);
				strSmsUrl.append("feedid=" + smsFeedId);
				strSmsUrl.append("&username=" + username);
				strSmsUrl.append("&password=" + password);
				strSmsUrl.append("&time=" + time);
				strSmsUrl.append("&senderid=" + senderId);
				strSmsUrl.append("&keyword=" + keyword);
				for (int currMobileNoIndex = 0; currMobileNoIndex < lstMobileNumbers.size(); currMobileNoIndex++) {
					smsResponseBean = new SMSResponseBean();
					strMobileNo = lstMobileNumbers.get(currMobileNoIndex);
					if (CommonUtil.getParameter(strMobileNo).compareTo("") == 0) {
						smsResponseBean.setDesc("No Mobile Number specified at index " + currMobileNoIndex + 1);
						continue;
					}
					smsResponseBean.setMobileNo(strMobileNo);
					if (sendFlag) {
						finalURL = strSmsUrl.toString() + "&To=" + strMobileNo + "&Text=" + URLEncoder.encode(message);
						System.out.println(finalURL);
						try {
							url = new URL(finalURL);
							// TODO Vijaya Remove this code from Live
							// System.setProperty("http.proxyHost",
							// "172.25.18.6");
							// System.setProperty("http.proxyPort", "8080");
							// TODO Vijaya Remove this code from Live
							urlConnection = url.openConnection();
							urlConnection.setConnectTimeout(Integer.parseInt(GenericConstants.smsConnectionTimeOut));
							urlConnection.setReadTimeout(Integer.parseInt(GenericConstants.smsReadOutputTimeOut));
							urlConnection.setDoOutput(true);
							bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
							if (!strXmlString.toString().equals("")) {
								strXmlString.delete(0, strXmlString.toString().length());
							}
							while ((responseString = bufferedReader.readLine()) != null) {
								strXmlString.append(responseString);
							}
						} catch (Exception e) {
							e.printStackTrace();
							strXmlString = new StringBuffer("");
						}
						// Parse Response XML String
						if (strXmlString.toString() != null && !strXmlString.toString().equals("") && !strXmlString.toString().equalsIgnoreCase("null")) {
							parseXml.processReturnResult(strXmlString.toString(), smsResponseBean);
						} else {
							smsResponseBean.setCode("system");
							smsResponseBean.setDesc(smsResponseBean.getDesc() + " || " + " SMS Response Not Received ! ");
						}
					}
					returnList.add(smsResponseBean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}

	// FOR VALUE FIRST
	public static List sendSmsNewChanged(EmailSMSTransactionBean emailSMSTransactionBean) throws SendFailedException {
		List<String> lstMobileNumbers = emailSMSTransactionBean.getToAddress();
		String message = emailSMSTransactionBean.getSmsEmailContent();
		List<SMSResponseBean> returnList = new ArrayList<SMSResponseBean>();
		try {
			boolean sendFlag = true;
			SMSResponseBean smsResponseBean = null;
			String strMobileNo = "";
			String responseString = "";
			StringBuffer strXmlString = new StringBuffer("");
			StringBuffer strSmsUrl = new StringBuffer("");
			ParseXml parseXml = new ParseXml();
			URL url = null;
			URLConnection urlConnection = null;
			BufferedReader bufferedReader = null;
			String smsURL = CommonUtil.getParameter(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SMS_URL));
			String username = CommonUtil.getParameter(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SMS_USERNAME));
			String password = CommonUtil.getParameter(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SMS_PASSWORD));
			String senderId = CommonUtil.getParameter(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SMS_SENDER_ID));
			String finalURL = null;
			smsResponseBean = new SMSResponseBean();
			if (smsURL.compareTo("") == 0) {
				smsResponseBean.setCode("system");
				smsResponseBean.setDesc(smsResponseBean.getDesc() + " || " + " SMS Service URL not Proper ! ");
				sendFlag = false;
			}
			if (username.compareTo("") == 0) {
				smsResponseBean.setCode("system");
				smsResponseBean.setDesc(smsResponseBean.getDesc() + " || " + " SMS UserName not Proper ! ");
				sendFlag = false;
			}
			if (password.compareTo("") == 0) {
				smsResponseBean.setCode("system");
				smsResponseBean.setDesc(smsResponseBean.getDesc() + " || " + " SMS Password not Proper ! ");
				sendFlag = false;
			}
			if (message.compareTo("") == 0) {
				smsResponseBean.setCode("system");
				smsResponseBean.setDesc(smsResponseBean.getDesc() + " || " + " No Message available to be sent ! ");
				sendFlag = false;
			}
			if (lstMobileNumbers == null || lstMobileNumbers.size() == 0) {
				smsResponseBean.setCode("system");
				smsResponseBean.setDesc(smsResponseBean.getDesc() + " || " + " No Receipt for SMS ! ");
				sendFlag = false;
			}
			if (sendFlag) {
				strSmsUrl.append(smsURL);
				strSmsUrl.append("&username=" + username);
				strSmsUrl.append("&password=" + password);
				strSmsUrl.append("&from=" + senderId);
				for (int currMobileNoIndex = 0; currMobileNoIndex < lstMobileNumbers.size(); currMobileNoIndex++) {
					smsResponseBean = new SMSResponseBean();
					strMobileNo = lstMobileNumbers.get(currMobileNoIndex);
					if (CommonUtil.getParameter(strMobileNo).compareTo("") == 0) {
						smsResponseBean.setDesc("No Mobile Number specified at index " + currMobileNoIndex + 1);
						continue;
					}
					smsResponseBean.setMobileNo(strMobileNo);
					if (sendFlag) {
						finalURL = strSmsUrl.toString() + "&to=" + strMobileNo + "&text=" + URLEncoder.encode(message);
						try {
							url = new URL(finalURL);
							// TODO Vijaya Remove this code from Live
							// System.setProperty("http.proxyHost",
							// "172.25.18.6");
							// System.setProperty("http.proxyPort", "8080");
							// TODO Vijaya Remove this code from Live
							urlConnection = url.openConnection();
							urlConnection.setConnectTimeout(Integer.parseInt(GenericConstants.smsConnectionTimeOut));
							urlConnection.setReadTimeout(Integer.parseInt(GenericConstants.smsReadOutputTimeOut));
							urlConnection.setDoOutput(true);
							bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
							if (!strXmlString.toString().equals("")) {
								strXmlString.delete(0, strXmlString.toString().length());
							}
							responseString = bufferedReader.readLine();
							while (responseString != null) {
								responseString = bufferedReader.readLine();
								strXmlString.append(responseString);
							}
						} catch (Exception e) {
							e.printStackTrace();
							strXmlString = new StringBuffer("");
						}
						// Parse Response XML String
						if (strXmlString.toString() != null && !strXmlString.toString().equals("") && !strXmlString.toString().equalsIgnoreCase("null")) {
							parseXml.processReturnResult(strXmlString.toString(), smsResponseBean);
						} else {
							smsResponseBean.setCode("system");
							smsResponseBean.setDesc(smsResponseBean.getDesc() + " || " + " SMS Response Not Received ! ");
						}
					}
					returnList.add(smsResponseBean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("message Sent.");
		return returnList;
	}

	private static String replaceSystemParametersInEmailSMSObject(String emailSMSContent, Map<String, String> candidateValues) throws GenericException {
		try {
			for (Entry<String, String> systemParameterNDBParameterEntry : systemParameterNDBParameterMap.entrySet()) {
				if (emailSMSContent.contains(systemParameterNDBParameterEntry.getKey())) {
					if (candidateValues.get(systemParameterNDBParameterEntry.getValue()) != null) {
						emailSMSContent = emailSMSContent.replaceAll(systemParameterNDBParameterEntry.getKey().replaceAll("\\{", "\\\\{").replaceAll("\\}", "\\\\}"),
								candidateValues.get(systemParameterNDBParameterEntry.getValue()));
					}
				}
			}
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
		return emailSMSContent;
	}

	public static List<Integer> insertEmailNSMSForNewRegisteration(Integer emailReq, Integer smsReqd, String status, RegistrationBean registrationBean) throws GenericException {
		List<Integer> lstEmailSMSStatus = new ArrayList<Integer>();
		try {
			if ((emailReq.equals(new Integer(1)) && smsReqd.equals(new Integer(1))) || (smsReqd != null && !smsReqd.equals("") && smsReqd.equals(new Integer(1)))) {
				lstEmailSMSStatus.add(insertEmailSmsForNewRegistration(emailReq, smsReqd, status, registrationBean, registrationBean.getUserName()));
			}
		} catch (Exception ex) {
			LoggerHome.getLogger(EmailSMSUtil.class).fatal("Error sending Email/SMS" + ex);
		}
		return lstEmailSMSStatus;
	}

	private static Integer insertEmailSmsForNewRegistration(Integer emailReq, Integer smsReqd, String status, RegistrationBean registrationBean, String userId) throws Exception {
		CommonService commonService = null;
		String mailContent = null;
		String subject = null;
		// String smsContent = null;
		// String emailContent = null;
		// String emailSubject = null;
		ApplicationContext applicationContext = SpringUtil.getInstance().getApplicationContext();
		commonService = (CommonService) applicationContext.getBean("commonService");
		int[] result = null;
		Map<String, String> candidateValues = commonService.getCandidatesDataForNewReg(userId);
		try {
			List<EmailSMSTransactionBean> emailSMS = new ArrayList<>();
			if (candidateValues != null && !candidateValues.isEmpty()) {
				if (smsReqd.equals(1)) {
					if (StringUtils.isNotBlank(candidateValues.get("mobile_no"))) {
						subject = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.NEW_REG_EMAIL_SUBJECT);
						EmailSMSTransactionBean emailSMSTransactionBean = new EmailSMSTransactionBean();
						emailSMSTransactionBean.setCandidateMailAddress(candidateValues.get("mobile_no").toString());
						mailContent = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.NEW_REG_SMS);
						mailContent = replaceSystemParametersInEmailSMSObject(mailContent, candidateValues);
						emailSMSTransactionBean.setSmsEmailContent(mailContent);
						emailSMSTransactionBean.setStatusPk(status);
						emailSMSTransactionBean.setSmsMailFlag(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.EMAIL_SMS_FLAG_SMS_VALUE));
						emailSMS.add(emailSMSTransactionBean);
						// result = commonService.insertEmailSMSDetailsForNewReg(emailSMSTransactionBean, registrationBean);
					}
				}
				if (emailReq.equals(1)) {
					if (StringUtils.isNotBlank(candidateValues.get("mail_address"))) {
						subject = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.NEW_REG_EMAIL_SUBJECT);
						EmailSMSTransactionBean emailSMSTransactionBean = new EmailSMSTransactionBean();
						emailSMSTransactionBean.setCandidateMailAddress(candidateValues.get("mail_address").toString());
						mailContent = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.NEW_REG_EMAIL);
						mailContent = replaceSystemParametersInEmailSMSObject(mailContent, candidateValues);
						emailSMSTransactionBean.setSmsEmailContent(mailContent);
						emailSMSTransactionBean.setStatusPk(status);
						emailSMSTransactionBean.setSmsMailFlag(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.EMAIL_SMS_FLAG_EMAIL_VALUE));
						emailSMSTransactionBean.setEmailSubject(subject);
						emailSMS.add(emailSMSTransactionBean);
					}
				}
			}
			result = commonService.insertEmailSMSDetailsForNewReg(emailSMS, registrationBean);
		} catch (Exception e) {
			LoggerHome.getLogger(EmailSMSUtil.class).fatal(e, e);
		}
		return smsReqd;
	}

	public static List<Integer> insertEmailNSMSForForgotUserId(int activityId, String loggedInUsername, String userId, String staus, Users users, Integer emailReq, Integer smsReqd)
			throws GenericException {
		List<Integer> lstEmailSMSStatus = new ArrayList<Integer>();
		try {
			if ((emailReq != null && !emailReq.equals("") && emailReq.equals(new Integer(1))) || (smsReqd != null && !smsReqd.equals("") && smsReqd.equals(new Integer(1)))) {
				lstEmailSMSStatus.add(insertSMSEmailForForgotUserId(emailReq, smsReqd, loggedInUsername, userId, staus, users));
			}

			/*
			 * if (emailReq != null && !emailReq.equals("") && emailReq.equals(new Integer(1))) { lstEmailSMSStatus.add(insertEmailForForgotUserId(loggedInUsername, userId, staus,
			 * users)); } if (smsReqd != null && !smsReqd.equals("") && smsReqd.equals(new Integer(1))) { lstEmailSMSStatus.add(insertSMSForForgotUserId(loggedInUsername, userId,
			 * staus, users)); }
			 */
		} catch (Exception ex) {
			LoggerHome.getLogger(EmailSMSUtil.class).fatal("Error sending Email/SMS" + ex);
		}
		return lstEmailSMSStatus;
	}

	private static Integer insertSMSEmailForForgotUserId(Integer emailReq, Integer smsReqd, String loggedInUsername, String userId, String staus, Users users) throws Exception {
		CommonService commonService = null;
		String mailContent = null;
		String subject = null;
		ApplicationContext applicationContext = SpringUtil.getInstance().getApplicationContext();
		commonService = (CommonService) applicationContext.getBean("commonService");
		int[] result = null;
		Map candidateValues = null;
		candidateValues = commonService.getCandidatesDataForForgotUserId(userId);
		List<EmailSMSTransactionBean> emailSMS = new ArrayList<>();
		if (candidateValues != null && !candidateValues.isEmpty()) {
			if (emailReq.equals(new Integer(1))) {
				if (candidateValues.get("mail_address") != null) {
					subject = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.FORGOT_USER_ID_SUBJECT);
					EmailSMSTransactionBean emailSMSTransactionBean = new EmailSMSTransactionBean();
					// emailSMSTransactionBean.setUserPk(Long.parseLong(registrationBean.getUserId()));
					emailSMSTransactionBean.setUserPk(users.getUserId());
					emailSMSTransactionBean.setCandidateMailAddress(candidateValues.get("mail_address").toString());
					mailContent = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.FORGOT_USER_ID_EMAIL);
					mailContent = replaceSystemParametersInEmailSMSObject(mailContent, candidateValues);
					emailSMSTransactionBean.setSmsEmailContent(mailContent);
					// emailSMSTransactionBean.setUserPk(Long.parseLong(registrationBean.getUserId()));
					emailSMSTransactionBean.setStatusPk(staus);
					emailSMSTransactionBean.setSmsMailFlag(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.EMAIL_SMS_FLAG_EMAIL_VALUE));
					emailSMSTransactionBean.setEmailSubject(subject);
					emailSMS.add(emailSMSTransactionBean);
				}
			}
			if (smsReqd.equals(new Integer(1))) {
				if (candidateValues.get("mobile_no") != null) {
					EmailSMSTransactionBean emailSMSTransactionBean = new EmailSMSTransactionBean();
					emailSMSTransactionBean.setUserPk(users.getUserId());
					emailSMSTransactionBean.setCandidateMailAddress(candidateValues.get("mobile_no").toString());
					mailContent = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.FORGOT_USER_ID_SMS);
					mailContent = replaceSystemParametersInEmailSMSObject(mailContent, candidateValues);
					emailSMSTransactionBean.setSmsEmailContent(mailContent);
					// emailSMSTransactionBean.setUserPk(Long.parseLong(registrationBean.getUserId()));
					emailSMSTransactionBean.setStatusPk(staus);
					emailSMSTransactionBean.setSmsMailFlag(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.EMAIL_SMS_FLAG_SMS_VALUE));
					emailSMS.add(emailSMSTransactionBean);
				}
			}
		}
		result = commonService.insertEmailSMSDetailsForForgotUserId(emailSMS, loggedInUsername);
		return smsReqd;
	}

	public static int insertMobileForOTP(int activityId, String OTP, String mobileNo) throws GenericException {
		int result = 0; // -1-> Error Sending Email, 0-> Not Applicable, 1-> Successful
		EmailSMSMasterBean emailSMSMasterBean = new EmailSMSMasterBean();
		try {
			if (emailSMSMasterBean != null) {
				CommonService commonService = null;
				String mailContent = null;
				List<String> toEmailAddress = new ArrayList<String>();
				String subject = null;
				EmailSMSTransactionBean emailSMSTransactionBean = new EmailSMSTransactionBean();
				ApplicationContext applicationContext = SpringUtil.getInstance().getApplicationContext();
				commonService = (CommonService) applicationContext.getBean("commonService");
				// Map<String, String> candidateValues =
				// commonService.getCandidatesDataForEmailSMS(userId);
				// if (candidateValues.get("mail_address")!=null &&
				// !candidateValues.get("mail_address").equals("")){
				toEmailAddress.add(mobileNo);
				// subject = "TNUSRB Email Verification OTP";
				mailContent = "Dear Candidate," + "Your OTP for mobile verification is: " + OTP + ". " + "Please enter this OTP and complete mobile verification process.";
				// mailContent =
				// replaceSystemParametersInEmailSMSObject(mailContent,
				// candidateValues);

				emailSMSTransactionBean.setEmailSubject(subject);
				emailSMSTransactionBean.setSmsEmailContent(mailContent);
				emailSMSTransactionBean.setFromAddress(null);
				emailSMSTransactionBean.setToAddress(toEmailAddress);
				emailSMSTransactionBean.setCcAddress(emailSMSMasterBean.getCcAddress());
				emailSMSTransactionBean.setBccAddress(emailSMSMasterBean.getBccAddress());
				emailSMSTransactionBean.setSmsMailFlag(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.EMAIL_SMS_FLAG_SMS_VALUE));
				emailSMSTransactionBean.setStatusPk(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.SEND_OTP) + "");
				// emailSMSTransactionBean.setUserPk(users.getUserFk());
				result = insertOTPDetails(commonService, emailSMSTransactionBean, mobileNo);
			}
			// }
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
		return result;
	}

	private static int insertOTPDetails(CommonService commonService, EmailSMSTransactionBean emailSMSBean, String emailId) throws Exception {
		return commonService.insertOTPDetails(emailSMSBean, emailId);
	}

	@SuppressWarnings("unused")
	private static int updateOTPDetails(CommonService commonService, EmailSMSTransactionBean emailSMSBean, String emailId) throws Exception {
		return commonService.updateEmailSmsOTPDetails(emailSMSBean, emailId);
	}

	public static List<Integer> insertEmailNSMSForChangePassword(String OTP, String emailId) throws GenericException {
		List<Integer> lstEmailSMSStatus = new ArrayList<Integer>();
		try {
			if (emailId != null && !emailId.equals("") && emailId.equals(new Integer(1)))
				insertEmailChangePassword(OTP, emailId);
		} catch (Exception ex) {
			LoggerHome.getLogger(EmailSMSUtil.class).fatal("Error sending Email/SMS" + ex);
		}
		return lstEmailSMSStatus;
	}

	public static int insertEmailChangePassword(String OTP, String emailId) throws GenericException {
		int result = 0; // -1-> Error Sending Email, 0-> Not Applicable, 1-> Successful
		EmailSMSMasterBean emailSMSMasterBean = new EmailSMSMasterBean();
		try {
			if (emailSMSMasterBean != null) {
				CommonService commonService = null;
				String mailContent = null;
				List<String> toEmailAddress = new ArrayList<String>();
				String subject = null;
				EmailSMSTransactionBean emailSMSTransactionBean = new EmailSMSTransactionBean();
				ApplicationContext applicationContext = SpringUtil.getInstance().getApplicationContext();
				commonService = (CommonService) applicationContext.getBean("commonService");
				String userid = "S6q0wB_54aa02";
				String actualuserid = DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).encrypt(OTP);
				char[] chars = "abcdefghi$jklmnopqrstuvwxyz!".toCharArray();
				StringBuilder sb = new StringBuilder();
				Random random = new Random();
				for (int i = 0; i < 20; i++) {
					char c = chars[random.nextInt(chars.length)];
					sb.append(c);
				}
				String append = sb.toString();
				int count = commonService.updateForgotPassword(OTP, append);
				if (count > 0) {
					// Map<String, String> candidateValues =
					// commonService.getCandidatesDataForEmailSMS(userId);
					// if (candidateValues.get("mail_address")!=null &&
					// !candidateValues.get("mail_address").equals("")){

					toEmailAddress.add(emailId);
					subject = "MRB - Forgot Password";
					mailContent = "Dear Applicant,<br/> <br/>\r\n" + "To change password please click on following link:<br/><br/><a target=\"_blank\" href=\""
							+ ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SERVER_ADDRESS) + "/MRBPCW/LoginAction_resetPassword.action?" + userid + "="
							+ URLEncoder.encode(actualuserid, "UTF-8") + "WxO" + append + "\">"
							+ ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SERVER_ADDRESS) + "/MRBPCW/LoginAction_resetPassword.action?" + userid + "="
							+ URLEncoder.encode(actualuserid, "UTF-8") + "WxO" + append + "</a>" +
							"<br/><br/><br/><br/>PS: Please do not reply to this email as this is a computer generated message.\r\n" + "<br/><br/>- MRB \r\n" + "";
					// mailContent =
					// replaceSystemParametersInEmailSMSObject(mailContent,
					// candidateValues);
					emailSMSTransactionBean.setEmailSubject(subject);
					emailSMSTransactionBean.setSmsEmailContent(mailContent);
					emailSMSTransactionBean.setFromAddress(null);
					emailSMSTransactionBean.setToAddress(toEmailAddress);
					emailSMSTransactionBean.setCcAddress(emailSMSMasterBean.getCcAddress());
					emailSMSTransactionBean.setBccAddress(emailSMSMasterBean.getBccAddress());
					emailSMSTransactionBean.setSmsMailFlag(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.EMAIL_SMS_FLAG_EMAIL_VALUE));
					emailSMSTransactionBean.setStatusPk(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.SEND_OTP) + "");
					// emailSMSTransactionBean.setUserPk(users.getUserFk());
					result = insertChangePassword(commonService, emailSMSTransactionBean, emailId);
				}
			}
			// }
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
		return result;
	}

	public static List<Integer> insertEmailNSMSForForgotPwd(int activityId, String loggedInUsername, String userId, String staus, Users users, Integer emailReq, Integer smsReqd,
			RegistrationBean registrationBean) throws GenericException {
		List<Integer> lstEmailSMSStatus = new ArrayList<Integer>();
		try {
			if ((emailReq != null && !emailReq.equals("") && emailReq.equals(new Integer(1))) || (smsReqd != null && !smsReqd.equals("") && smsReqd.equals(new Integer(1)))) {
				lstEmailSMSStatus.add(insertSMSForForgotPwd(emailReq, smsReqd, userId, staus, users, loggedInUsername));
			}
		} catch (Exception ex) {
			LoggerHome.getLogger(EmailSMSUtil.class).fatal("Error sending SMS" + ex.getMessage());
			ex.printStackTrace();
		}
		return lstEmailSMSStatus;
	}

	private static Integer insertSMSForForgotPwd(Integer emailReq, Integer smsReqd, String userId, String staus, Users users, String loggedInUsername) throws Exception {
		CommonService commonService = null;
		String mailContent = null;
		int[] result = null;
		String subject = null;
		ApplicationContext applicationContext = SpringUtil.getInstance().getApplicationContext();
		commonService = (CommonService) applicationContext.getBean("commonService");
		Map<String, String> candidateValues = commonService.getCandidatesDataForForgotPassword(userId);
		try {
			List<EmailSMSTransactionBean> emailSMS = new ArrayList<>();
			if (candidateValues != null && !candidateValues.isEmpty()) {
				if (emailReq.equals(new Integer(1))) {
					if (candidateValues.get("mail_address") != null) {
						subject = "MRB - Forgot Password";
						EmailSMSTransactionBean emailSMSTransactionBean = new EmailSMSTransactionBean();
						// emailSMSTransactionBean.setUserPk(Long.parseLong(registrationBean.getUserId()));
						emailSMSTransactionBean.setUserPk(users.getUserId());
						emailSMSTransactionBean.setCandidateMailAddress((candidateValues.get("mail_address").toString()));
						mailContent = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.FORGOT_PASSWORD_EMAIL);
						mailContent = replaceSystemParametersInEmailSMSObject(mailContent, candidateValues);
						emailSMSTransactionBean.setSmsEmailContent(mailContent);
						// emailSMSTransactionBean.setUserPk(Long.parseLong(registrationBean.getUserId()));
						emailSMSTransactionBean.setStatusPk(staus);
						emailSMSTransactionBean.setSmsMailFlag(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.EMAIL_SMS_FLAG_EMAIL_VALUE));
						emailSMSTransactionBean.setEmailSubject(subject);
						emailSMS.add(emailSMSTransactionBean);
					}
				}
				if (smsReqd.equals(new Integer(1))) {
					if (candidateValues.get("mobile_no") != null) {
						EmailSMSTransactionBean emailSMSTransactionBean = new EmailSMSTransactionBean();
						emailSMSTransactionBean.setUserPk(users.getUserId());
						emailSMSTransactionBean.setCandidateMailAddress(candidateValues.get("mobile_no").toString());
						mailContent = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.FORGOT_PASSWORD_SMS);
						mailContent = replaceSystemParametersInEmailSMSObject(mailContent, candidateValues);
						emailSMSTransactionBean.setSmsEmailContent(mailContent);
						emailSMSTransactionBean.setStatusPk(staus);
						emailSMSTransactionBean.setSmsMailFlag(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.EMAIL_SMS_FLAG_SMS_VALUE));
						emailSMS.add(emailSMSTransactionBean);
						// emailSMSTransactionBean.setCandidateMailAddress(registrationBean.getMobileNo());
						// mailContent = replaceSystemParametersInEmailSMSObject(mailContent, candidateValues);
						// emailSMSTransactionBean.setUserPk(Long.parseLong(registrationBean.getUserId()));
					}
				}
			}
			result = commonService.insertEmailSMSDetailsForForgotPassword(emailSMS, loggedInUsername);
		} catch (Exception e) {
			LoggerHome.getLogger(EmailSMSUtil.class).fatal(e, e);// e.printStackTrace();
		}
		return smsReqd;
	}

	private static int insertChangePassword(CommonService commonService, EmailSMSTransactionBean emailSMSBean, String emailId) throws Exception {
		return commonService.insertOTPDetails(emailSMSBean, emailId);
	}

	public static int insertMobForOTP(String OTP, String mobileNo1, String userName) throws GenericException {
		int[] result = null; // -1-> Error Sending Email, 0-> Not Applicable, 1-> Successful
		try {
			CommonService commonService = null;
			String mailContent = null;
			String toEmailAddress = mobileNo1;
			EmailSMSTransactionBean emailSMSTransactionBean = new EmailSMSTransactionBean();
			ApplicationContext applicationContext = SpringUtil.getInstance().getApplicationContext();
			List<EmailSMSTransactionBean> emailSMS = new ArrayList<>();
			commonService = (CommonService) applicationContext.getBean("commonService");
			mailContent = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OTP_MOB_CONTENT);
			Map<String, String> candidateValues = new HashMap<String, String>();
			candidateValues.put("otp_mobile", OTP);
			mailContent = replaceSystemParametersInEmailSMSObject(mailContent, candidateValues);
			emailSMSTransactionBean.setSmsEmailContent(mailContent);
			emailSMSTransactionBean.setFromAddress("");
			emailSMSTransactionBean.setCandidateMailAddress(toEmailAddress);
			emailSMSTransactionBean.setSmsMailFlag(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.EMAIL_SMS_FLAG_SMS_VALUE));
			emailSMSTransactionBean.setStatusPk(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.SEND_OTP) + "");
			emailSMSTransactionBean.setUserPk(Long.valueOf(0));
			emailSMS.add(emailSMSTransactionBean);
			result = insertEmailSMSDetails(commonService, emailSMS, userName);
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
		return 1;
	}

	public static List<Integer> insertEmailNSMSForPaymentApproved(Users users, String staus, Integer emailReq, Integer smsReqd) throws GenericException {
		List<Integer> lstEmailSMSStatus = new ArrayList<Integer>();
		try {
			if ((emailReq != null && !emailReq.equals("") && emailReq.equals(new Integer(1))) || (smsReqd != null && !smsReqd.equals("") && smsReqd.equals(new Integer(1)))) {
				lstEmailSMSStatus.add(insertsmsEmailForPaymentApproved(smsReqd, emailReq, users, staus, emailReq, smsReqd));
			}
		} catch (Exception ex) {
			LoggerHome.getLogger(EmailSMSUtil.class).fatal("Error sending Email/SMS" + ex);
			ex.printStackTrace();
		}
		return lstEmailSMSStatus;
	}

	private static Integer insertsmsEmailForPaymentApproved(Integer smsReqd, Integer emailReq, Users users, String status, Integer emailReq2, Integer smsReqd2) throws Exception {
		int[] result = null; // -1-> Error Sending Email, 0-> Not Applicable, 1->
		CommonService commonService = null;
		String mailContent = null;
		String subject = null;
		ApplicationContext applicationContext = SpringUtil.getInstance().getApplicationContext();
		commonService = (CommonService) applicationContext.getBean("commonService");
		Map<String, String> candidateValues = commonService.getCandidatesDataForEmailSMSForPayment(users.getUsername());
		List<EmailSMSTransactionBean> smsEmail = new ArrayList<>();
		if (candidateValues != null) {
			if (emailReq.equals(1)) {
				if (StringUtils.isNotBlank(candidateValues.get("mail_address"))) {
					EmailSMSTransactionBean emailSMSTransactionBean = new EmailSMSTransactionBean();
					subject = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.PAYMENT_APPROVED_EMAIL_SUBJECT);
					mailContent = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.PAYMENT_APPROVED_EMAIL);
					mailContent = replaceSystemParametersInEmailSMSObject(mailContent, candidateValues);
					emailSMSTransactionBean.setEmailSubject(subject);
					emailSMSTransactionBean.setSmsEmailContent(mailContent);
					emailSMSTransactionBean.setFromAddress("");
					emailSMSTransactionBean.setCandidateMailAddress(candidateValues.get("mail_address"));
					emailSMSTransactionBean.setSmsMailFlag(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.EMAIL_SMS_FLAG_EMAIL_VALUE));
					emailSMSTransactionBean.setStatusPk(status);
					emailSMSTransactionBean.setUserPk(users.getUserFk());
					smsEmail.add(emailSMSTransactionBean);
				}
			}
			if (smsReqd.equals(1)) {
				EmailSMSTransactionBean emailSMSTransactionBean = new EmailSMSTransactionBean();
				if (StringUtils.isNotBlank(candidateValues.get("mobile_no"))) {
					mailContent = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.PAYMENT_APPROVED_SMS);
					mailContent = replaceSystemParametersInEmailSMSObject(mailContent, candidateValues);
					emailSMSTransactionBean.setSmsEmailContent(mailContent);
					emailSMSTransactionBean.setFromAddress("");
					emailSMSTransactionBean.setCandidateMailAddress(candidateValues.get("mobile_no").toString());
					emailSMSTransactionBean.setSmsMailFlag(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.EMAIL_SMS_FLAG_SMS_VALUE));
					emailSMSTransactionBean.setStatusPk(status);
					emailSMSTransactionBean.setUserPk(users.getUserFk());
					smsEmail.add(emailSMSTransactionBean);
				}
			}
			result = insertEmailSMSDetails(commonService, smsEmail, users.getUsername());
		}
		return smsReqd;
	}

	public static int insertEmailForOTP(String OTP, String emailId, String userName) throws GenericException {
		int[] result;
		try {
			CommonService commonService = null;
			String mailContent = null;
			List<String> toEmailAddress = new ArrayList<String>();
			String subject = null;
			EmailSMSTransactionBean emailSMSTransactionBean = new EmailSMSTransactionBean();
			List<EmailSMSTransactionBean> emailSMS = new ArrayList<>();
			ApplicationContext applicationContext = SpringUtil.getInstance().getApplicationContext();
			commonService = (CommonService) applicationContext.getBean("commonService");
			toEmailAddress.add(emailId);
			subject = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.EMAIL_OTP_SUBJECT);
			mailContent = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OTP_EMAIL_CONTENT);
			Map<String, String> candidateValues = new HashMap<String, String>();
			candidateValues.put("otp_email", OTP);
			mailContent = replaceSystemParametersInEmailSMSObject(mailContent, candidateValues);
			emailSMSTransactionBean.setEmailSubject(subject);
			emailSMSTransactionBean.setSmsEmailContent(mailContent);
			emailSMSTransactionBean.setFromAddress("");
			emailSMSTransactionBean.setToAddress(toEmailAddress);
			emailSMSTransactionBean.setCandidateMailAddress(emailId);
			emailSMSTransactionBean.setSmsMailFlag(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.EMAIL_SMS_FLAG_EMAIL_VALUE));
			emailSMSTransactionBean.setStatusPk(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.SEND_OTP) + "");
			emailSMS.add(emailSMSTransactionBean);
			result = insertEmailSMSDetails(commonService, emailSMS, userName);
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
		return 1;
	}

	public static int insertMobForFpOTP(String OTP, String mobileNo1, String username) throws GenericException {
		int[] result = null; // -1-> Error Sending Email, 0-> Not Applicable, 1-> Successful
		try {
			CommonService commonService = null;
			String mailContent = null;
			EmailSMSTransactionBean emailSMSTransactionBean = new EmailSMSTransactionBean();
			ApplicationContext applicationContext = SpringUtil.getInstance().getApplicationContext();
			List<EmailSMSTransactionBean> emailSMSFp = new ArrayList<>();
			commonService = (CommonService) applicationContext.getBean("commonService");
			String toEmailAddress = mobileNo1;
			mailContent = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.FORGOT_PASSWORD_SMS);
			Map<String, String> candidateValues = new HashMap<String, String>();
			candidateValues.put("otp_mobile", OTP);
			mailContent = replaceSystemParametersInEmailSMSObject(mailContent, candidateValues);
			emailSMSTransactionBean.setSmsEmailContent(mailContent);
			emailSMSTransactionBean.setFromAddress("");
			emailSMSTransactionBean.setCandidateMailAddress(toEmailAddress);
			emailSMSTransactionBean.setSmsMailFlag(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.EMAIL_SMS_FLAG_SMS_VALUE));
			emailSMSTransactionBean.setStatusPk(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.FORGOT_PASSWORD_STATUS) + "");
			emailSMSFp.add(emailSMSTransactionBean);
			result = insertEmailSMSDetails(commonService, emailSMSFp, username);
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
		return 1;
	}
	
	public static List<Integer> insertEmailORSMSForForgotPassword(Integer emailReq, Integer smsReqd, String status, RegistrationBean registrationBean,String mobileNo1,String otp) throws GenericException {
		List<Integer> lstEmailSMSStatus = new ArrayList<Integer>();
		try {
			if ((emailReq.equals(new Integer(1)) || smsReqd.equals(new Integer(1)))) {
				lstEmailSMSStatus.add(insertEmailSmsForForgotPassword(emailReq, smsReqd, registrationBean.getUserName(), mobileNo1, otp));
			}
		} catch (Exception ex) {
			LoggerHome.getLogger(EmailSMSUtil.class).fatal("Error sending Email/SMS" + ex);
		}
		return lstEmailSMSStatus;
	}

	public static Integer insertEmailSmsForForgotPassword(Integer emailReq, Integer smsReqd,
			 String userName,String mobileNo1,String otp) throws Exception {
		CommonService commonService = null;
		String mailContent = null;
		String subject = null;
		// String smsContent = null;
		// String emailContent = null;
		// String emailSubject = null;
		ApplicationContext applicationContext = SpringUtil.getInstance().getApplicationContext();
		commonService = (CommonService) applicationContext.getBean("commonService");
		int[] result = null;
		try {
			List<EmailSMSTransactionBean> emailSMS = new ArrayList<>();
			Map<String, String> candidateValues = new HashMap<String, String>();
			List<EmailSMSTransactionBean> emailSMSFp = new ArrayList<>();
			subject = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.FORGOT_PASSWORD_SUBJECT);
				if (smsReqd.equals(1)) {
					EmailSMSTransactionBean emailSMSTransactionBean = new EmailSMSTransactionBean();
					String toEmailAddress = mobileNo1;
					mailContent = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.FORGOT_PASSWORD_SMS);
					candidateValues.put("otp_mobile", otp);
					mailContent = replaceSystemParametersInEmailSMSObject(mailContent, candidateValues);
					emailSMSTransactionBean.setSmsEmailContent(mailContent);
					emailSMSTransactionBean.setFromAddress("");
					emailSMSTransactionBean.setCandidateMailAddress(toEmailAddress);
					emailSMSTransactionBean.setSmsMailFlag(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.EMAIL_SMS_FLAG_SMS_VALUE));
					emailSMSTransactionBean.setStatusPk(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.FORGOT_PASSWORD_STATUS) + "");
					emailSMSTransactionBean.setEmailSubject(subject);
					emailSMSFp.add(emailSMSTransactionBean);
					result = insertEmailSMSDetails(commonService, emailSMSFp, userName);
				}
				if (emailReq.equals(1)) {
					    mailContent = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.FORGOT_PASSWORD_EMAIL);
						EmailSMSTransactionBean emailSMSTransactionBean = new EmailSMSTransactionBean();
						String toEmailAddress = mobileNo1;
						emailSMSTransactionBean.setCandidateMailAddress(toEmailAddress);
						candidateValues.put("otp_email", otp);
						mailContent = replaceSystemParametersInEmailSMSObject(mailContent, candidateValues);
						emailSMSTransactionBean.setSmsEmailContent(mailContent);
						emailSMSTransactionBean.setStatusPk(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.FORGOT_PASSWORD_STATUS) + "");
						emailSMSTransactionBean.setSmsMailFlag(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.EMAIL_SMS_FLAG_EMAIL_VALUE));
						emailSMSTransactionBean.setEmailSubject(subject);
						emailSMSFp.add(emailSMSTransactionBean);
						result = insertEmailSMSDetails(commonService, emailSMSFp, userName);
				}
			
				
		} catch (Exception e) {
			LoggerHome.getLogger(EmailSMSUtil.class).fatal(e, e);
		}
		return 1;
	}
}
