package com.nseit.generic.util.emailsms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.nseit.generic.models.EmailSMSTransactionBean;
import com.nseit.generic.service.CommonService;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.ParseXml;
import com.nseit.generic.util.SMSResponseBean;
import com.nseit.generic.util.SpringUtil;

public class SmsSenderNetCore implements Runnable
{
	
	EmailSMSTransactionBean emailSMSTransactionBean = null;
	JavaMailSender mailSender = null;
	

	
	@Override
	public void run()
	{
		try {
			Logger logger = LoggerHome.getLogger(getClass());
			List<String> lstMobileNumbers = emailSMSTransactionBean.getToAddress();
			String message = emailSMSTransactionBean.getSmsEmailContent();
			List<SMSResponseBean> returnList = new ArrayList<SMSResponseBean>();
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

			String smsURL = CommonUtil.getParameter(ConfigurationConstants
					.getInstance().getPropertyVal(GenericConstants.SMS_URL));
			String username = CommonUtil.getParameter(ConfigurationConstants
					.getInstance()
					.getPropertyVal(GenericConstants.SMS_USERNAME));
			String password = CommonUtil.getParameter(ConfigurationConstants
					.getInstance()
					.getPropertyVal(GenericConstants.SMS_PASSWORD));
			String senderId = CommonUtil.getParameter(ConfigurationConstants
					.getInstance().getPropertyVal(
							GenericConstants.SMS_SENDER_ID));
			String smsFeedId = CommonUtil.getParameter(ConfigurationConstants
					.getInstance().getPropertyVal(GenericConstants.SMS_FEED_ID));
			String keyword = CommonUtil.getParameter(ConfigurationConstants
					.getInstance().getPropertyVal(GenericConstants.SMS_KEYWORD));
			
			String finalURL = null;

			smsResponseBean = new SMSResponseBean();
			if (smsURL.compareTo("") == 0) {
				smsResponseBean.setCode("system");
				smsResponseBean.setDesc(smsResponseBean.getDesc() + " || "
						+ " SMS Service URL not Proper ! ");
				sendFlag = false;
			}

			if (smsURL.compareTo("") == 0) {
				smsResponseBean.setCode("system");
				smsResponseBean.setDesc(smsResponseBean.getDesc() + " || "
						+ " SMS Service URL not Proper ! ");
				sendFlag = false;
			}

			if (username.compareTo("") == 0) {
				smsResponseBean.setCode("system");
				smsResponseBean.setDesc(smsResponseBean.getDesc() + " || "
						+ " SMS UserName not Proper ! ");
				sendFlag = false;
			}

			if (password.compareTo("") == 0) {
				smsResponseBean.setCode("system");
				smsResponseBean.setDesc(smsResponseBean.getDesc() + " || "
						+ " SMS Password not Proper ! ");
				sendFlag = false;
			}

			if (message.compareTo("") == 0) {
				smsResponseBean.setCode("system");
				smsResponseBean.setDesc(smsResponseBean.getDesc() + " || "
						+ " No Message available to be sent ! ");
				sendFlag = false;
			}

			if (lstMobileNumbers == null || lstMobileNumbers.size() == 0) {
				smsResponseBean.setCode("system");
				smsResponseBean.setDesc(smsResponseBean.getDesc() + " || "
						+ " No Receipt for SMS ! ");
				sendFlag = false;
			}
			
			

			if (sendFlag) {
				strSmsUrl.append(smsURL);
				strSmsUrl.append("feedid=" + smsFeedId);
				strSmsUrl.append("&username=" + username);
				strSmsUrl.append("&password=" + password);
				strSmsUrl.append("&senderid=" + senderId); //added by nikhil
				strSmsUrl.append("&keyword=" + keyword);
				//strSmsUrl.append("&from=" + senderId);
				
				for (int currMobileNoIndex = 0; currMobileNoIndex < lstMobileNumbers
						.size(); currMobileNoIndex++) {
					smsResponseBean = new SMSResponseBean();

					strMobileNo = lstMobileNumbers.get(currMobileNoIndex);
					if (CommonUtil.getParameter(strMobileNo).compareTo("") == 0) {
						smsResponseBean
								.setDesc("No Mobile Number specified at index "
										+ currMobileNoIndex + 1);
						continue;
					}

					smsResponseBean.setMobileNo(strMobileNo);

					if (sendFlag) {
						finalURL = strSmsUrl.toString() + "&to=" + strMobileNo
						+ "&text=" + URLEncoder.encode(message);

						logger.info("Sending SMS==> "+finalURL);
						
						try {
							url = new URL(finalURL);
							urlConnection = url.openConnection();
							urlConnection
									.setConnectTimeout(Integer
											.parseInt(GenericConstants.smsConnectionTimeOut));
							urlConnection
									.setReadTimeout(Integer
											.parseInt(GenericConstants.smsReadOutputTimeOut));
							urlConnection.setDoOutput(true);

							bufferedReader = new BufferedReader(
									new InputStreamReader(urlConnection
											.getInputStream()));

							if (!strXmlString.toString().equals("")) {
								strXmlString.delete(0, strXmlString.toString()
										.length());
							}

							while ((responseString = bufferedReader.readLine()) != null) {
								strXmlString.append(responseString);
							}
						} catch (Exception e) {
							e.printStackTrace();
							strXmlString = new StringBuffer("");
						}

						// Parse Response XML String
						if (strXmlString.toString() != null
								&& !strXmlString.toString().equals("")
								&& !strXmlString.toString().equalsIgnoreCase(
										"null")) {
							parseXml.processReturnResult(strXmlString
									.toString(), smsResponseBean);
						} else {
							smsResponseBean.setCode("system");
							smsResponseBean.setDesc(smsResponseBean.getDesc()
									+ " || " + " SMS Response Not Received ! ");
						}
					}
					returnList.add(smsResponseBean);
				}
			}
		} catch (MailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public SmsSenderNetCore(EmailSMSTransactionBean emailSMSTransactionBean,
			JavaMailSender mailSender) {
		super();
		this.emailSMSTransactionBean = emailSMSTransactionBean;
		this.mailSender = mailSender;
	}

	
}
