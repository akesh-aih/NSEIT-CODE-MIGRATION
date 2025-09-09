package com.nseit.payment.action;

import static com.nseit.generic.util.GenericConstants.DESTINATION_URL;
import static com.nseit.generic.util.GenericConstants.GLOBAL_PLAIN_TEXT_MESSAGE;
import static com.nseit.generic.util.GenericConstants.REDIRECT_ACTION_URL;
import static com.nseit.generic.util.GenericConstants.SESSION_USER;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.HttpParameters;

import com.atom.ots.enc.AtomEncryption;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nseit.generic.action.BaseAction;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.PaymentBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.CommonService;
import com.nseit.generic.service.ReportService;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.aware.UserAware;
import com.nseit.payment.models.CustDetails;
import com.nseit.payment.models.Extras;
import com.nseit.payment.models.HeadDetails;
import com.nseit.payment.models.MerchDetails;
import com.nseit.payment.models.OtsTransaction;
import com.nseit.payment.models.PayDetails;
import com.nseit.payment.models.PayInstrument;
import com.nseit.payment.models.PaymentOnlineBean;
import com.nseit.payment.models.ServerResponse;
import com.nseit.payment.service.PaymentService;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.jasperreports.engine.JREmptyDataSource;

public class PaymentOnlineAction extends BaseAction implements ModelDriven<PaymentOnlineBean>, UserAware {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerHome.getLogger(getClass());
	private PaymentService paymentService;
	private CommonService commonService;
	private PaymentOnlineBean paymentBean = new PaymentOnlineBean();

	private ReportService reportService;
	private HashMap parameterValues;
	private JREmptyDataSource jremptyDataSource;
	private Connection connection;

	public PaymentOnlineAction() {
		// logger.info("PaymentOnlineAction Constructor is calling");
		displayMenus();
	}

	public PaymentOnlineBean getModel() {
		return paymentBean;
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public HashMap getParameterValues() {
		return parameterValues;
	}

	public JREmptyDataSource getJremptyDataSource() {
		return jremptyDataSource;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public String getPaymentManagement() {
		String menuKey = null;
		String result = null;
		try {

			Map<Integer, String> paymentModeTypeMap = ConfigurationConstants.getInstance().getPaymentMasterMap();
			paymentBean.setPaymentModeList(paymentModeTypeMap);

			if (request.getAttribute("parentMenuKey") != null) {
				menuKey = request.getAttribute("parentMenuKey").toString();
			}
			String actionUrl = redirectToNextActivePage(menuKey, loggedInUser);
			if (actionUrl == null) {
				request.setAttribute(DESTINATION_URL, actionUrl);
				result = "childNotFoundForMenu";
			} else {
				request.setAttribute(DESTINATION_URL, actionUrl);
				result = REDIRECT_ACTION_URL;
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return result;
	}

	public String showPaymentScreen() throws Exception {
		String result = "";
		Users users = (Users) sessionAttributes.get(SESSION_USER);
		if (users == null) {
			return "errorPage";
		}

		String error = (String) sessionAttributes.get(GLOBAL_PLAIN_TEXT_MESSAGE);
		if (StringUtils.isNotBlank(error)) {
			addActionMessage(error);
			sessionAttributes.put(GLOBAL_PLAIN_TEXT_MESSAGE, "");
			result = "showOnlinePaymentScreen";
		} else {
			sessionAttributes.put(GLOBAL_PLAIN_TEXT_MESSAGE, "");
		}

		Map<Integer, List<Long>> dateWindowMap = ConfigurationConstants.getInstance().getDateWindowMap();
		List<Long> dateList = dateWindowMap.get(6);
		Long startDate = dateList.get(0);
		Long endDate = dateList.get(1);

		try {
			Map<Integer, String> paymentModeTypeMap = ConfigurationConstants.getInstance().getPaymentMasterMap();
			if (loggedInUser.getDisciplineID().equals("1")) {
				paymentBean.setPost1("Prosthetic Craftsman");
			}

			String amount = "";
			String commCertYesNo = loggedInUser.getCommCertYesNo();
			String category = "";

			if (StringUtils.isNotBlank(commCertYesNo) && commCertYesNo.equals("6")) {
				category = loggedInUser.getCommunity();
			} else {
				category = "7"; // by default OC
			}
			String pwd = loggedInUser.getDisableYesNo();
			String destWidow = loggedInUser.getWidowYesNo();

			if ((StringUtils.isNotBlank(pwd) && pwd.equals("6")) || (StringUtils.isNotBlank(destWidow) && destWidow.equals("6"))) {
				amount = ConfigurationConstants.getInstance().getPropertyVal("DISABILITY_AMOUNT");// 300
			} else if (StringUtils.isNotBlank(category) && Arrays.asList("4", "5", "6").contains(category)) {
				amount = ConfigurationConstants.getInstance().getPropertyVal("SC_ST_AMOUNT"); // 300
			} else if (StringUtils.isNotBlank(category) && Arrays.asList("1", "2", "3", "7").contains(category)) {
				amount = ConfigurationConstants.getInstance().getPropertyVal("GENERAL_OBC_AMOUNT"); // 600
			}

			if (Strings.isNotBlank(amount)) {
				amount = amount.trim();
			}

			commonService.insertCandidateAuditrail(loggedInUser, "Amount on Payment Screen", "Candidate with User Id " + loggedInUser.getUsername() + "" + "with category : "
					+ category + " and pwd : " + pwd + " and destitute widow : " + destWidow + " has to pay Rs. " + amount);

			paymentBean.setPaymentModeList(paymentModeTypeMap);
			paymentBean.setApplicantName(loggedInUser.getUsername());
			paymentBean.setAmount(amount);
			long today = ZonedDateTime.now().toInstant().toEpochMilli();
			if ((today == startDate || today > startDate) && (today == endDate || today < endDate)) {
				// Check payment is Already done Or Just initiated
				List<PaymentBean> candidatePaymentData = paymentService.getOnlinePaymentDetails(loggedInUser);
				if (null != candidatePaymentData && !candidatePaymentData.isEmpty()) {
					paymentBean.setCreatedDate(candidatePaymentData.get(0).getCreatedDate());
					if (Strings.isNotBlank(candidatePaymentData.get(0).getCheckStatusDate())) {
						paymentBean.setCheckStatusDate(candidatePaymentData.get(0).getCheckStatusDate());
					} else {
						paymentBean.setCheckStatusDate(candidatePaymentData.get(0).getCreatedDate());
					}

					if (null != candidatePaymentData.get(0).getValidatedStatus() && ("A".equalsIgnoreCase(candidatePaymentData.get(0).getValidatedStatus()))) {
						paymentBean.setDisplayPaymentFlag("true");
						paymentBean.setPaymentStatus(candidatePaymentData.get(0).getValidatedStatus());
						String message = "Your Previous Transaction was found successful, Please proceed with application download.";
						String actionUrl = "AtomResponseAction_statusMsgDisplay.action?statusMsg=" + message;
						commonService.updateCandidateStatus(users, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
						users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
						sessionAttributes.put(SESSION_USER, users);
						request.setAttribute(DESTINATION_URL, actionUrl);

						return result = REDIRECT_ACTION_URL;
					}
					if ("R".equalsIgnoreCase(candidatePaymentData.get(0).getValidatedStatus())) {
						paymentBean.setReconcile(false);
						paymentBean.setOpd_validated_status("R");
					} else if ("P".equalsIgnoreCase(candidatePaymentData.get(0).getValidatedStatus())) {
						paymentBean.setReconcile(false);
						paymentBean.setOpd_validated_status("P");
					} else if (null == candidatePaymentData.get(0).getValidatedStatus() || "P".equalsIgnoreCase(candidatePaymentData.get(0).getValidatedStatus())) {
						paymentBean.setReconcile(true);
					} else {
						paymentBean.setReconcile(false);
						paymentBean.setOpd_validated_status("P");
					}
				}
				commonService.insertCandidateAuditrail(loggedInUser, "Payment Page - Loaded", "Candidate with User Id " + loggedInUser.getUsername() + " arrived at Payment page.");
				result = "displayChallanScreen";
			} else {

				paymentBean.setPaymentStartDate(CommonUtil.formatDate(new Date(startDate), GenericConstants.DATE_FORMAT_DEFAULT));
				String newEndDate = CommonUtil.formatDate(new Date(endDate), GenericConstants.DATE_FORMAT_DEFAULT1);
				String[] endDateFinal = newEndDate.split(" ");
				String newStartDate = CommonUtil.formatDate(new Date(startDate), GenericConstants.DATE_FORMAT_DEFAULT1);
				String[] startDateFinal = newStartDate.split(" ");
				paymentBean.setPaymentEndDate(" - " + startDateFinal[0] + " " + startDateFinal[1] + " to " + endDateFinal[0] + " " + endDateFinal[1] + " hrs .");
				paymentBean.setDisplayPaymentFlag("true");
				paymentBean.setDisplayRegFlag("false");
				paymentBean.setPaymentModeList(paymentModeTypeMap);
				return "CloseWindow";
			}

		} catch (Exception e) {
			logger.error(e, e);
			return "errorPage";
		}
		return result;
	}

	public String connectToAIPay() {
		String result = "";
		Users users = (Users) sessionAttributes.get(SESSION_USER);
		String encryptedData = "";
		String encryptedToken = "";
		String responseSplit = "";
		String decryptedData = "";
		String merchantID = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MERCHANT_ID);
		String encryptionKey = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AIPAY_ENCRYPTION_KEY);
		String decryptionKey = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AIPAY_DECRYPTION_KEY);
		String amount = "";/*ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.ASSISTANT_MANAGER_AMOUNT);*/

		String commCertYesNo = loggedInUser.getCommCertYesNo();
		String category = "";
		String pwd = loggedInUser.getDisableYesNo();
		String destWidow = loggedInUser.getWidowYesNo();
		
		if (StringUtils.isNotBlank(commCertYesNo) && commCertYesNo.equals("6")) {
			category = loggedInUser.getCommunity();
		} else {
			category = "7"; // by default OC
		}

		if ((StringUtils.isNotBlank(pwd) && pwd.equals("6")) || (StringUtils.isNotBlank(destWidow) && destWidow.equals("6"))) {
			amount = ConfigurationConstants.getInstance().getPropertyVal("DISABILITY_AMOUNT");// 300
		} else if (StringUtils.isNotBlank(category) && Arrays.asList("4", "5", "6").contains(category)) {
			amount = ConfigurationConstants.getInstance().getPropertyVal("SC_ST_AMOUNT"); // 300
		} else if (StringUtils.isNotBlank(category) && Arrays.asList("1", "2", "3", "7").contains(category)) {
			amount = ConfigurationConstants.getInstance().getPropertyVal("GENERAL_OBC_AMOUNT"); // 600
		}

		if (Strings.isNotBlank(amount)) {
			amount = amount.trim();
		}
		
		String merchantTxnRefNumberPrefix = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.TXN_REF_NUMBER_PREFIX);
		String merchantTxnSeqNumber = null;
		try {
			merchantTxnSeqNumber = paymentService.getTransactionUniqueNumber();
		} catch (Exception e1) {
			logger.error(e1,e1);
		}
		StringBuilder txnRefnumber = new StringBuilder();
		txnRefnumber.append(merchantTxnRefNumberPrefix);
		txnRefnumber.append("-");
		txnRefnumber.append(merchantTxnSeqNumber);
		String txnid = txnRefnumber.toString();

		MerchDetails merchDetails = new MerchDetails();
		merchDetails.setMerchId(merchantID);
		merchDetails.setMerchTxnId(txnid);
		merchDetails.setUserId(users.getUsername());
		merchDetails.setPassword(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AIPAY_PASSWORD));
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime date = LocalDateTime.now();
		String dateFormat = myFormat.format(date);
		merchDetails.setMerchTxnDate(dateFormat);

		PayDetails payDetails = new PayDetails();
		payDetails.setAmount(amount);
		payDetails.setCustAccNo(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AIPAY_ACCOUNT_NO));
		payDetails.setTxnCurrency(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.CURRENCY_TYPE));
		payDetails.setProduct(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AIPAY_PRODID));

		CustDetails custDetails = new CustDetails();
		custDetails.setCustEmail(users.getEmailAddress());
		custDetails.setCustMobile(users.getMobile());

		HeadDetails headDetails = new HeadDetails();
		headDetails.setApi(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AIPAY_API));
		headDetails.setVersion(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AIPAY_VERSION));
		headDetails.setPlatform(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AIPAY_PLATFORM));

		Extras extras = new Extras();
		extras.setUdf1(users.getUsername());
		extras.setUdf2("");
		extras.setUdf3("");
		extras.setUdf4("");
		extras.setUdf5("");

		PayInstrument payInstrument = new PayInstrument();

		payInstrument.setMerchDetails(merchDetails);
		payInstrument.setPayDetails(payDetails);
		payInstrument.setCustDetails(custDetails);
		payInstrument.setHeadDetails(headDetails);
		payInstrument.setExtras(extras);

		OtsTransaction otsTxn = new OtsTransaction();
		otsTxn.setPayInstrument(payInstrument);

		Gson gson = new Gson();
		String json = gson.toJson(otsTxn);
		logger.info("Final JsonOutput----: " + json);
		try {
			commonService.insertCandidateAuditrail(users, "Payment Request before Encryption", "Candidate with User ID " + users.getUsername() + " : " + json);

			encryptedData = AtomEncryption.encrypt(json, encryptionKey);

			commonService.insertCandidateAuditrail(users, "Payment Request after Encryption", "Candidate with User ID " + users.getUsername() + " : " + encryptedData);

			encryptedToken = sendRequestPostCall(encryptedData);

			commonService.insertCandidateAuditrail(users, "Encrypted Token received from Gateway", "Candidate with User ID " + users.getUsername() + " : " + encryptedToken);

			if (encryptedToken != null && encryptedToken.startsWith("merchId")) {
				responseSplit = encryptedToken.split("\\&encData=")[1];
				logger.info("Encrypted Response : " + response);

				decryptedData = AtomEncryption.decrypt(responseSplit, decryptionKey);
				logger.info("Decrypted Response : " + decryptedData);

				commonService.insertCandidateAuditrail(users, "Decrypted Token received from Gateway", "Candidate with User ID " + users.getUsername() + " : " + decryptedData);

				ServerResponse serverResponse = new ServerResponse();
				serverResponse = (ServerResponse) gson.fromJson(decryptedData, ServerResponse.class);

				String atomTokenId = serverResponse.getAtomTokenId();
				logger.info("serverResponse-----: " + serverResponse + " ~~~ TokenId------------: " + atomTokenId);
				paymentBean.setAiPayToken(atomTokenId);
				paymentBean.setMerchantId(merchantID);
				paymentBean.setEmailID(users.getEmailAddress());
				paymentBean.setMobileNo(users.getMobile());
				paymentBean.setAmount(amount);
				paymentBean.setReturnUrl(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AIPAY_RESPONSE_URL));
				paymentBean.setEnvironment(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AIPAY_ENVIRONMENT));

				String transactionAlreadyExist = paymentService.getExistingMerchantTxnNumber(loggedInUser, paymentBean);
				int updateStatus = 0;
				if (transactionAlreadyExist != null) {
					logger.info("Transaction already exist : Delete and insert transaction no");
					updateStatus = paymentService.deleteAndInsertTransactionIDForCandidate(paymentBean, loggedInUser, merchantTxnSeqNumber, txnid);
				} else {
					updateStatus = paymentService.insertTransactionIDForCandidate(paymentBean, loggedInUser, merchantTxnSeqNumber, txnid);
				}
				if (updateStatus > 0) {
					result = "checkout";
				} else {
					result = "displayChallanScreen";
				}
			} else {
				String actionUrl = "AtomResponseAction_statusMsgDisplay.action?statusMsg=We are currently facing some issues in processing your payment. Please try again later.";
				logger.info("Check the generation of URL.");
				request.setAttribute(DESTINATION_URL, actionUrl);
				result = REDIRECT_ACTION_URL;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String sendRequestPostCall(String param) throws Exception {
		String gatewayUrl = "";
		String merchantId = "";
		String output = "";

		try {
			logger.info("sendDoubleVerificationPostCall() ::::::: messageData :" + param);

			gatewayUrl = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AIPAY_REQUEST_URL);
			merchantId = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MERCHANT_ID);

			String resultOfflineURL = null;
			StringBuilder strbuilder = new StringBuilder();
			strbuilder.append(gatewayUrl);
			strbuilder.append("?");
			strbuilder.append("encData=" + param);
			strbuilder.append("&merchId=" + merchantId);

			resultOfflineURL = strbuilder.toString();

			output = sendPost(resultOfflineURL);

		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return output;
	}
			
	private String sendPost(String param) throws Exception {
    	
    	String responseText = "";
		String url = param;
		
		try {
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Content-Type", "application/json");

		String urlParameters = param;

		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		logger.info("Sending 'POST' request to URL : " + url + "~~~ Post parameters : " + urlParameters + "~~~ Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		responseText = response.toString();
		logger.info("Output###===>>>"+response.toString());
		}catch(Exception e) {
			logger.fatal(e,e);
		}
		return responseText;
	}	
	
	public String statusMsgDisplay() {
		String returnType = "success";
		String statusMsg = request.getParameter("statusMsg");
		if (statusMsg.contains(GenericConstants.PAYMENTSUCCESS)) {
			paymentBean.setEnableButton("true");
		} else {
			paymentBean.setEnableButton("false");
		}
		paymentBean.setTransactionMessage(statusMsg);
		return returnType;
	}

	public String insertOnlinepaymentDetails() throws Exception {
		String result = "";
		try {
			paymentBean.setCreatedBy(loggedInUser.getUsername());
			paymentBean.setUserFK(loggedInUser.getUserId());
			paymentBean.setPaymentModeOnline(request.getParameter("paymentModeType"));
			String amount = null;
			int total = Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal("GENERAL_OBC_AMOUNT"));
			amount = Integer.toString(total);
			paymentBean.setAmount(amount);
			commonService.insertCandidateAuditrail(loggedInUser, "Proceed to pay", "Candidate with User Id " + loggedInUser.getUsername() + " has clicked on Proceed to pay.");

			String existmercntTxnNo = paymentService.getExistingMerchantTxnNumber(loggedInUser, paymentBean);
			// Add New record in OES_PAYMENT_DETAILS Table before Payment HitUrl
			String merchantTxnSeqNumber = loggedInUser.getMerchantTxnSeqNumber();
			String merchantTxnRefNumber = loggedInUser.getMerchantTxnRefNumber();
			try {
				if (existmercntTxnNo == null || existmercntTxnNo.equals("")) {
					paymentService.insertTransactionIDForCandidate(paymentBean, loggedInUser, merchantTxnSeqNumber, merchantTxnRefNumber);
					result = "PAYSUCC";
				} else if (existmercntTxnNo != null && merchantTxnRefNumber != null) {
					//paymentService.updateTransactionToNull(paymentBean, loggedInUser, existmercntTxnNo);
					paymentService.deleteAndInsertTransactionIDForCandidate(paymentBean, loggedInUser, merchantTxnSeqNumber, merchantTxnRefNumber);
					logger.info("moving Transaction to history table" + existmercntTxnNo + "]");
					result = "PAYSUCC";
				}

			} catch (Exception ex) {
				result = "PAYFAIL";
				logger.info("Exception insertOnlinepaymentDetails()For User ID:" + loggedInUser.getUsername() + " Error: " + ex.getMessage());
			}

		} catch (Exception ex) {
			logger.fatal(ex, ex);
		}
		sessionAttributes.put(SESSION_USER, loggedInUser);
		request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, result);
		return "writePlainText";
}

	@Override
	public void setLoggedInUser(Users users) {
		this.loggedInUser = users;
	}

	@Override
	public void resetModel() {
		paymentBean = new PaymentOnlineBean();
	}

	// for challan receipt
	/*
	 * @SuppressWarnings("unchecked") public String generateChallanReceipt()
	 * throws Exception{ String result = null; String bankFK = null; int
	 * challanBarcode_details_status=0; try {
	 * 
	 * parameterValues = new HashMap(); CandidateBean candidateBean =
	 * paymentService.getCandidateDetailsForPayment(loggedInUser,Integer.
	 * parseInt(request.getParameter("epost"))); StringBuffer userNamebf = new
	 * StringBuffer(); if(candidateBean.getTitleValue() != null ||
	 * !candidateBean.getTitleValue().equals("")) {
	 * userNamebf.append(candidateBean.getTitleValue() + " "); }
	 * userNamebf.append(candidateBean.getPersonalDetailsBean().
	 * getCandidateFirstName() + " ");
	 * 
	 * if(candidateBean.getPersonalDetailsBean().getCandidateMiddleName() !=
	 * null &&
	 * !candidateBean.getPersonalDetailsBean().getCandidateMiddleName().equals(
	 * "")) { userNamebf.append(candidateBean.getPersonalDetailsBean().
	 * getCandidateMiddleName() + " "); }
	 * userNamebf.append(candidateBean.getPersonalDetailsBean().
	 * getCandidateLastName()); String userName = userNamebf.toString();
	 * parameterValues.put("USER_ID", candidateBean.getAppNumber());
	 * parameterValues.put("community", candidateBean.getCategoryVal());
	 * parameterValues.put("DATE_OF_BIRTH",
	 * candidateBean.getPersonalDetailsBean().getDateOfBirth());
	 * parameterValues.put("CANDIDATE_NAME", userName); //
	 * parameterValues.put("DISCIPLINE",
	 * ConfigurationConstants.getInstance().getTestDetailsFromMapForTestPk(
	 * Integer.parseInt(loggedInUser.getDisciplineID())).getTestName());
	 * parameterValues.put("MOBILE_NO",
	 * candidateBean.getPersonalDetailsBean().getMobileNo());
	 * parameterValues.put("FEES", candidateBean.getReceiptAmount());
	 * parameterValues.put("FEES1", candidateBean.getCandidateDocPk3());
	 * parameterValues.put("FEES_IN_WORDS",
	 * candidateBean.getReceiptAmountInWords());
	 * parameterValues.put("EMAIL_ADDRESS",
	 * candidateBean.getPersonalDetailsBean().getEmail());
	 * 
	 * 
	 * 
	 * 
	 * 
	 * String commAddress =
	 * candidateBean.getAddressBean().getAlternateAddress();
	 * 
	 * if (commAddress != null && !commAddress.equals("")) { String[] temp =
	 * null; String delimiter = "\\$\\$"; temp = commAddress.split(delimiter);
	 * 
	 * if (temp != null) { for (int i = 0; i < temp.length; i++) { if (i == 0) {
	 * parameterValues.put("COM_ADDRESS_1", temp[i]); } if (i == 1) {
	 * parameterValues.put("COM_ADDRESS_2", temp[i]); } if (i == 2) {
	 * parameterValues.put("COM_ADDRESS_3", temp[i]); } if (i == 3) {
	 * parameterValues.put("COM_ADDRESS_4", temp[i]); } }
	 * 
	 * } }
	 * 
	 * parameterValues.put("CITY",
	 * candidateBean.getAddressBean().getAlternateCity());
	 * 
	 * //parameterValues.put("COUNTRY", candidateBean.getAltCountryVal());
	 * 
	 * parameterValues.put("PINCODE",
	 * candidateBean.getAddressBean().getAlternatePinCode());
	 * 
	 * 
	 * if (candidateBean.getAltCountryVal() != null &&
	 * !candidateBean.getAltCountryVal().equals("")) {
	 * 
	 * parameterValues.put("COUNTRY", ConfigurationConstants
	 * .getInstance().getCountryMap().get( Integer.parseInt(candidateBean
	 * .getAltCountryVal())));
	 * 
	 * }
	 * 
	 * 
	 * if (candidateBean.getAltStateVal() != null &&
	 * !candidateBean.getAltStateVal().equals("")) {
	 * 
	 * parameterValues.put("STATE", ConfigurationConstants
	 * .getInstance().getStateMap( Integer.parseInt(candidateBean
	 * .getAltCountryVal())).get( Integer .parseInt(candidateBean
	 * .getAltStateVal())));
	 * 
	 * } else if (CommonUtil.getParameter(
	 * candidateBean.getAltUnionTerritoryVal()).compareTo("") != 0) {
	 * parameterValues.put("STATE", ConfigurationConstants
	 * .getInstance().getUTMap( Integer.parseInt(candidateBean
	 * .getAltCountryVal())).get( Integer.parseInt(candidateBean
	 * .getAltUnionTerritoryVal()))); }
	 * 
	 * String bankName = paymentBean.getChallanBankCode();
	 * 
	 * parameterValues.put("BANK_NAME", bankName);
	 * 
	 * java.awt.Image scissorImage = ImageIO.read(new FileInputStream(
	 * "scissor_with_dotted_line.png"));
	 * 
	 * parameterValues.put("SCISSOR_IMAGE", scissorImage);
	 * 
	 * Code128 barcode = new Code128();
	 * barcode.setData(candidateBean.getAppNumber());
	 * //barcode.setData("1234567890"); File outputFile = new
	 * File(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants
	 * .OES_DOCUMNETS_AND_PHOTO_PATH)+"barcode"); if(!outputFile.exists()) {
	 * outputFile.mkdir(); } outputFile=new
	 * File(outputFile+"//"+candidateBean.getAppNumber()+".jpg");
	 * barcode.drawBarcode(outputFile.toString());
	 * candidateBean.setChallanbarcodeno(candidateBean.getAppNumber());
	 * //--barcode String barcodeString =candidateBean.getAppNumber();
	 * Code128Bean barcode128Bean = new Code128Bean();
	 * barcode128Bean.setCodeset(Code128Constants.CODESET_B); final int dpi =
	 * 100; barcode128Bean.setModuleWidth(UnitConv.in2mm(5.0f / dpi));
	 * barcode128Bean.doQuietZone(false);
	 * 
	 * //Open output file File outputFile = new
	 * File(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants
	 * .OES_DOCUMNETS_AND_PHOTO_PATH)+"barcode"); if(!outputFile.exists()) {
	 * outputFile.mkdir(); } outputFile=new
	 * File(outputFile+"//"+candidateBean.getAppNumber()+".png"); OutputStream
	 * out = new FileOutputStream(outputFile); try { BitmapCanvasProvider
	 * canvasProvider = new BitmapCanvasProvider(out, "image/x-png", dpi,
	 * BufferedImage.TYPE_BYTE_BINARY, false, 0);
	 * barcode128Bean.generateBarcode(canvasProvider,barcodeString);
	 * canvasProvider.finish(); } finally { out.close(); } //barcode
	 * 
	 * ServletContext context = ServletActionContext.getServletContext(); String
	 * imgfullPath = context.getRealPath("/images/"); java.awt.Image
	 * ePostChallan = ImageIO.read(new
	 * FileInputStream(imgfullPath+"/EPost_Challan.png")); java.awt.Image
	 * userBarcode = ImageIO.read(new FileInputStream(outputFile));//barcode
	 * parameterValues.put("ePostChallan", ePostChallan);
	 * parameterValues.put("userBarcode", userBarcode);//barcode
	 * //parameterValues.put("SUBREPORT_DIR", "/WEB-INF/Jasper/"); //
	 * jremptyDataSource = new JREmptyDataSource(); connection =
	 * reportService.getDataSourceConnection();
	 * parameterValues.put(JRParameter.REPORT_CONNECTION, connection);
	 * 
	 * ConfigurationConstants.getInstance()
	 * .getCandidateActivityAuditTrailLogger().info( loggedInUser.getUsername()
	 * + ", " + request.getRemoteAddr() +
	 * ", Print Challan Receipt , "+CommonUtil.formatDate(new Date(),
	 * "dd-MMM-yyyy HH:mm:ss") );
	 * commonService.insertCandidateAuditrail(loggedInUser, "Generate Challan",
	 * "Candidate with User Id "+loggedInUser.getUsername()
	 * +" has clicked on Generate Challan.");
	 * challanBarcode_details_status=paymentService.insertChallanBarcodeDetails(
	 * candidateBean); result ="pdf"; } catch (Exception ex) { throw new
	 * GenericException(ex); } return result; }
	 */

	// for receipt
	@SuppressWarnings("unchecked")
	public String generatePaymentReceipt() throws Exception {
		String result = null;
		try {
			String retTypeValue = null;
			String jsonPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
			String UserId = loggedInUser.getUsername();
			String DocumentPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
			
			parameterValues = new HashMap();
			CandidateBean candidateBean = paymentService.getCandidateDetailsForPaymentReceipt(loggedInUser, Integer.parseInt(request.getParameter("epost")));
			
			/*String district = null;
			if(Strings.isNotBlank(loggedInUser.getNativityCertDist())) {
				district = ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(loggedInUser.getNativityCertDist()));
			}
			parameterValues.put("USER_ID", loggedInUser.getUsername());
			parameterValues.put("CANDIDATE_NAME", candidateBean.getPersonalDetailsBean().getCandidateName().toUpperCase());
			parameterValues.put("otm_test_name", "Assistant Surgeon (Dental)");
			parameterValues.put("district", district.toUpperCase());
			parameterValues.put("onlineAmount", candidateBean.getIDproof());
			parameterValues.put("transaction_Date", candidateBean.getPersonalDetailsBean().getDateOfBirth());
			parameterValues.put("optm_payment_desc", candidateBean.getReceiptAmountInWords().toUpperCase());
			parameterValues.put("tp_transaction_no", candidateBean.getPersonalDetailsBean().getMobileNo());
            parameterValues.put("opd_transaction_no", candidateBean.getAppNumber());
			ServletContext context = ServletActionContext.getServletContext();
			String imgfullPath = context.getRealPath("/WebContent/images/");*/
			//java.awt.Image ePostChallan = ImageIO.read(new FileInputStream(imgfullPath + "/TRBAPR_Payment_Receipt.png"));
			//parameterValues.put("ePostChallan", ePostChallan);
			
			candidateBean.setUserId(loggedInUser.getUsername());
			File logoPath = new File(DocumentPath + File.separator + "logo" + File.separator + "logo.png");
			if (logoPath != null && !logoPath.equals("")) {
				candidateBean.setLogoCast(getImageCastFromLocal(logoPath));
			}
			createPaymentJson(candidateBean);
			

			Path path = Paths.get(jsonPath + File.separator + UserId + File.separator + UserId + "_pay.json");
			if (Files.exists(path)) {
				String lPdfString = new String(Files.readAllBytes(path));
				if (lPdfString != null && !lPdfString.trim().equals("")) {
					request.setAttribute("PAYJSONSTRING", lPdfString);
					retTypeValue = "downloadPaymentPdf";
				}
			} else {
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "File doesn't exist,please contact System Administrator.");
				retTypeValue = "writePlainText";
			}

			commonService.insertCandidateAuditrail(loggedInUser, "Generating Payment Receipt",
					"Candidate with User Id " + loggedInUser.getUsername() + " has clicked on Payment Receipt .");

			
			result = "downloadPaymentPdf";
		} catch (Exception ex) {
			logger.fatal(ex,ex);
		}
		return result;
	}
	
	
	private byte[] createPaymentJson(CandidateBean cb) 
	{

		byte[] bytesToWrite = {};
		ObjectMapper mapper = new ObjectMapper();
		try {
			commonService.insertCandidateAuditrail(loggedInUser, "Payment JSON Save", "JSON String saved for pdf payment generation of " + loggedInUser.getUsername() + ".");
			String UserId = loggedInUser.getUsername();

			String jsonPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
			File jsonFolderPath = new File(jsonPath);
			if (!jsonFolderPath.exists()) {
				jsonFolderPath.mkdirs();
			}
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.writeValue(new File(jsonPath + File.separator + UserId + File.separator + UserId + "_pay.json"), cb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytesToWrite;
	
	}

	private String getImageCastFromLocal(File fnew) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		StringBuilder imageStringBuilder = new StringBuilder();
		BufferedImage originalImage = null;
		try {
			originalImage = ImageIO.read(fnew);
			ImageIO.write(originalImage, "png", baos);
			byte[] imageInByte = baos.toByteArray();
			imageStringBuilder.append("data:image/png;base64,");
			imageStringBuilder.append(Base64.getEncoder().encodeToString(imageInByte));
		} catch (IOException ex) {
			logger.info(ex.getMessage());
		}
		return imageStringBuilder.toString();
	
	}
	
	public String checkIncompletePaymentStatus() throws Exception {
		logger.info("checkIncompletePaymentStatus() called User Id:" + loggedInUser.getUsername());
		String isPaymentStatusIncomplete = "Y";
		String currentUserId = request.getParameter("currentUserId");
		int incompleteOnlinePaymentCount = 0;
		int approvPaymentCount = 0;
		int checkSuccReInitiateCnt = 0;
		try {
			logger.info("checkIncompletePaymentStatus() currentUserId :[" + currentUserId + "]");
			if (currentUserId != null && !loggedInUser.getUsername().equals(currentUserId)) {
				isPaymentStatusIncomplete = "INVDUSER";
			} else {
				// Check Approved Status
				approvPaymentCount = paymentService.getApprovedPaymentStatusCount(loggedInUser);
				logger.info("checkIncompletePaymentStatus() approvPaymentCount :" + approvPaymentCount);
				if (approvPaymentCount == 1) {
					isPaymentStatusIncomplete = "A"; // For Do restrict Payment
				} else {
					checkSuccReInitiateCnt = paymentService.checkSuccReInitiatePaymentCount(loggedInUser);
					logger.info("checkIncompletePaymentStatus() checkSuccReInitiateCnt :" + checkSuccReInitiateCnt);
					if (checkSuccReInitiateCnt > 0) {

						isPaymentStatusIncomplete = "REINITIATE";
					} else {

						incompleteOnlinePaymentCount = paymentService.getIncompleteOnlinePaymentCount(loggedInUser);
						logger.info("checkIncompletePaymentStatus() incompleteOnlinePaymentCount :" + incompleteOnlinePaymentCount);
						if (incompleteOnlinePaymentCount == 0) {

							isPaymentStatusIncomplete = "N";
						} else if (incompleteOnlinePaymentCount == 1) {

							isPaymentStatusIncomplete = "Y";
						}
					}
				}
			}
		} catch (Exception e) {
			logger.info("Exception checkIncompletePaymentStatus() For User Id :" + loggedInUser.getUsername() + " Error :" + e.getMessage());
			isPaymentStatusIncomplete = "Y";
		}

		logger.info("checkIncompletePaymentStatus() PaymentStatus :" + isPaymentStatusIncomplete);
		sessionAttributes.put(SESSION_USER, loggedInUser);
		request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, isPaymentStatusIncomplete);
		return "writePlainText";
	}

	// enable button or not start
	public String reconcilePaymentAjaxckeck() throws Exception {
		String isPaymentStatusIncomplete = "Y";
		String currentUserId = request.getParameter("currentUserId");
		int reconcilePaymentAjaxckeck = 0;
		int checkforCreatedDate = 0;
		try {
			if (currentUserId != null && !loggedInUser.getUsername().equals(currentUserId)) {

				isPaymentStatusIncomplete = "INVDUSER";
			} else {// Check Approved Status
				reconcilePaymentAjaxckeck = paymentService.reconcilePaymentAjaxckeck(loggedInUser);
				if (reconcilePaymentAjaxckeck == 1) {

					isPaymentStatusIncomplete = "YE";// present h show button in enable state

					checkforCreatedDate = paymentService.opd_paymentStatusDateCheck(loggedInUser);// for 10 mins check
					if (checkforCreatedDate == 1) {
						// if true means entry present but last insert was below 10 mins.
						isPaymentStatusIncomplete = "YD"; // present h show button in disable state
					}
				} else {
					isPaymentStatusIncomplete = "H"; // hide that button not required
				}
			}
		} catch (Exception e) {
			logger.info("Exception reconcilePaymentAjaxckeck() For User Id :" + loggedInUser.getUsername() + " Error :" + e.getMessage());
			isPaymentStatusIncomplete = "Y";
			e.printStackTrace();
		}

		sessionAttributes.put(SESSION_USER, loggedInUser);
		request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, isPaymentStatusIncomplete);
		return "writePlainText";
	}

	// enable button

	public String insertOnlinepaymentDetailsSBIEPay() throws Exception {
		logger.info("insertOnlinepaymentDetailsSBIEPay() called User Id:" + loggedInUser.getUsername());
		String result = "";
		try {
			paymentBean.setCreatedBy(loggedInUser.getUsername());
			paymentBean.setUserFK(loggedInUser.getUserId());
			paymentBean.setPaymentModeOnline(request.getParameter("paymentModeType"));
			String amount = null;
			int total = Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal("GENERAL_OBC_AMOUNT"));
			amount = Integer.toString(total);
			paymentBean.setAmount(amount);
			commonService.insertCandidateAuditrail(loggedInUser, "Proceed to pay", "Candidate with User Id " + loggedInUser.getUsername() + " has clicked on Proceed to pay.");

			String existmercntTxnNo = paymentService.getExistingMerchantTxnNumber(loggedInUser, paymentBean);
			logger.info("Find existmercntTxnNo :[" + existmercntTxnNo + "]");
			// Add New record in OES_PAYMENT_DETAILS Table before Payment HitUrl
			String merchantTxnSeqNumber = loggedInUser.getMerchantTxnSeqNumber();
			String merchantTxnRefNumber = loggedInUser.getMerchantTxnRefNumber();
			logger.info("merchantTxnSeqNumber :[" + merchantTxnSeqNumber + "]  merchantTxnRefNumber :[" + merchantTxnRefNumber + "]");
			try {
				if (existmercntTxnNo == null || existmercntTxnNo.equals("")) {
					paymentService.insertTransactionIDForCandidate(paymentBean, loggedInUser, merchantTxnSeqNumber, merchantTxnRefNumber);
					result = "PAYSUCC";
				} else if (existmercntTxnNo != null && merchantTxnRefNumber != null) {
					paymentService.updateTransactionToNull(paymentBean, loggedInUser, existmercntTxnNo);
					logger.info("Not inserting TransactionDetail : Used existing existmercntTxnNo  :[" + existmercntTxnNo + "]");
					result = "PAYSUCC";
				}

			} catch (Exception ex) {
				result = "PAYFAIL";
				logger.info("Exception insertOnlinepaymentDetailsSBIEPay()For User ID:" + loggedInUser.getUsername() + " Error: " + ex.getMessage());
			}

		} catch (Exception ex) {
			logger.fatal(ex, ex);
		}
		logger.info("insertOnlinepaymentDetailsSBIEPay result :[" + result + "]");
		sessionAttributes.put(SESSION_USER, loggedInUser);
		request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, result);
		return "writePlainText";
}

	//########################################################################################################################## TESTING
	public String proceesTestPayment() throws Exception {
		logger.info("Test Payment Success Call Started");
		String result = null, message, actionUrl, status = "10";
		int i = paymentService.insertAutoApprovePayment(loggedInUser);
		int j = paymentService.updateStatus(loggedInUser, status);

		if (i > 0 && j > 0) {
			loggedInUser.setCandidateStatusId(10);
			message = GenericConstants.PAYMENTSUCCESS + "7021771904" + "";
			actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
			sessionAttributes.put(SESSION_USER, loggedInUser);
			request.setAttribute(DESTINATION_URL, actionUrl);
			result = REDIRECT_ACTION_URL;
		}
		return result;
	}
	
	public String successPayment() throws Exception{
		String result = null, message, actionUrl, status = "10";
		int i = paymentService.insertAutoApprovePayment(loggedInUser);
		int j = paymentService.updateStatus(loggedInUser, status);

		if (i > 0 && j > 0) {
			loggedInUser.setCandidateStatusId(10);
			message = GenericConstants.PAYMENTSUCCESS + "7021771904" + "";
			actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
			sessionAttributes.put(SESSION_USER, loggedInUser);
			request.setAttribute(DESTINATION_URL, actionUrl);
			result = REDIRECT_ACTION_URL;
		}
		return result;
	}
	
	public String rejectPayment() throws Exception{
		String result = null, message, actionUrl, status = "11";
		int i = paymentService.insertAutoRejectPayment(loggedInUser);
		int j = paymentService.updateStatus(loggedInUser, status);
		String PAYMENTREJECTED = "Sorry. Your Payment has been rejected processed. Your Transaction ID is ";

		if (i > 0 && j > 0) {
			loggedInUser.setCandidateStatusId(10);
			message = PAYMENTREJECTED + " 7021771904" + "";
			actionUrl = "SBIEPayResponseAction_statusRejectMsgDisplay.action?statusMsg=" + message;
			sessionAttributes.put(SESSION_USER, loggedInUser);
			request.setAttribute(DESTINATION_URL, actionUrl);
			result = REDIRECT_ACTION_URL;
		}
		return result;
	}
	
	
	
	/*
	 * public String connectToAtom() { String multiProd =
	 * "false";//ConfigurationConstants.getInstance().getPropertyVal(
	 * GenericConstants.ATOM_MULTI_PRODUCT).trim(); Users users = (Users)
	 * sessionAttributes.get(SESSION_USER); PaymentOnlineBean paymentOnlineBean =
	 * new PaymentOnlineBean();
	 * logger.info("Atom Online Payment connetToAtom() calling"); try{ String
	 * vendorID =
	 * ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.
	 * MERCHANT_ID).trim();//9273 String password =
	 * ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.
	 * AIPAY_PASSWORD).trim();//Test@123 String ttype =
	 * ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.
	 * ATOM_TTYPE).trim();//NBFundTransfer String prodid =
	 * ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.
	 * AIPAY_PRODID).trim();//Multi String amount = ""; String amountHalf = "";
	 * String txncurr =
	 * ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.
	 * CURRENCY_TYPE).trim();//INR String txnscamt =
	 * ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.
	 * ATOM_TXNSCAMT).trim();// String custacc =
	 * ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.
	 * ATOM_CUST_ACC).trim();// String responseURL =
	 * ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.
	 * ATOM_RESPONSE_URL).trim();// String requestHashKey =
	 * ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.
	 * ATOM_REQUEST_HASHKEY).trim();//83D1E1EC3DEE483BB698935F9B312A82 String
	 * requestURL =
	 * ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.
	 * ATOM_REQUEST_URL).trim();// String userFK = Long.toString(users.getUserFk());
	 * String candidateEmail = users.getEmailAddress(); String candidateMobile =
	 * users.getMobile();
	 * 
	 * //As passed by merchant, encoded with base 64. String
	 * clientCode=users.getUsername(); byte[] encodedBytes =
	 * Base64.getEncoder().encode(clientCode.getBytes()); clientCode = new
	 * String(encodedBytes, StandardCharsets.UTF_8);
	 * logger.info("encodedClientCode " + new String(clientCode));
	 * 
	 * //generate transaction ID String merchantTxnRefNumberPrefix =
	 * ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.
	 * TXN_REF_NUMBER_PREFIX).trim(); String merchantTxnSeqNumber =
	 * paymentService.getTransactionUniqueNumber(); StringBuilder txnRefnumber = new
	 * StringBuilder(); txnRefnumber.append(merchantTxnRefNumberPrefix);
	 * txnRefnumber.append("-"); txnRefnumber.append(merchantTxnSeqNumber); String
	 * txnid = txnRefnumber.toString();
	 * 
	 * String prefix = "";
	 * 
	 * if(loggedInUser.getDisciplineID().equals("1")) { prefix = "IGIP"; }
	 * if(loggedInUser.getDisciplineID().equals("2")) { prefix = "IGIM"; }
	 * 
	 * 
	 * paymentOnlineBean.setRegistrationNumber(prefix+loggedInUser.getUsername().
	 * substring(5));
	 * 
	 * try { String caste = loggedInUser.getCategoryValDesc(); String pwd =
	 * loggedInUser.getPhysicalDisability();
	 * 
	 * if(pwd!= null && caste!= null) {
	 * if(loggedInUser.getDisciplineID().equals("1")) { if(pwd.equals("6")) { amount
	 * = ConfigurationConstants.getInstance().getPropertyVal("PHD_OTHER_AMOUNT");
	 * }else { if(caste.equals("1") || caste.equals("2") || caste.equals("3") ||
	 * caste.equals("4")) { amount =
	 * ConfigurationConstants.getInstance().getPropertyVal("PHD_OTHER_AMOUNT"); }
	 * else { amount =
	 * ConfigurationConstants.getInstance().getPropertyVal("PHD_GENERAL_AMOUNT"); }
	 * } }
	 * 
	 * if(loggedInUser.getDisciplineID().equals("2")) { if(pwd.equals("6")) { amount
	 * = ConfigurationConstants.getInstance().getPropertyVal("MSC_OTHER_AMOUNT");
	 * }else { if(caste.equals("1") || caste.equals("2") || caste.equals("3") ||
	 * caste.equals("4")) { amount =
	 * ConfigurationConstants.getInstance().getPropertyVal("MSC_OTHER_AMOUNT"); }
	 * else { amount =
	 * ConfigurationConstants.getInstance().getPropertyVal("MSC_GENERAL_AMOUNT"); }
	 * } } }else { amount =
	 * ConfigurationConstants.getInstance().getPropertyVal("MSC_GENERAL_AMOUNT"); }
	 * 
	 * if(Strings.isNotBlank(amount)) { amount = amount.trim(); }
	 * 
	 * float halfAmount = Float.parseFloat(amount) / 2;
	 * 
	 * amountHalf = String.valueOf(halfAmount);
	 * 
	 * }catch (Exception e) { logger.fatal(e, e);
	 * logger.info("Candidate category is null."); } //generate signature String
	 * signature = paymentService.getEncodedValueWithSha2(requestHashKey,
	 * vendorID,password,ttype,prodid,txnid,amount,txncurr); logger.info(signature);
	 * 
	 * String time = CommonUtil.formatDate(new Date(),
	 * GenericConstants.TIME_DEFAULT); String date = CommonUtil.formatDate(new
	 * Date(), GenericConstants.DATE_DEFAULT); String txndate=date+"%20"+time;
	 * logger.info("Candidate transaction time is : "+txndate); String candidateName
	 * = users.getCandidateFullName(); candidateName = candidateName.trim();
	 * candidateName = candidateName.replaceAll("\\s", "%20");
	 * logger.info("Candidate name is : "+candidateName);
	 * paymentOnlineBean.setRequestURL(requestURL);
	 * paymentOnlineBean.setVendorID(vendorID);
	 * paymentOnlineBean.setPassword(password); paymentOnlineBean.setTtype(ttype);
	 * paymentOnlineBean.setProdid(prodid); paymentOnlineBean.setTxncurr(txncurr);
	 * paymentOnlineBean.setTxnscamt(txnscamt);
	 * paymentOnlineBean.setClientCode(clientCode);
	 * paymentOnlineBean.setTxnid(txnid); paymentOnlineBean.setTxnDate(txndate);
	 * paymentOnlineBean.setCustacc(custacc);
	 * paymentOnlineBean.setResponseURL(responseURL);
	 * paymentOnlineBean.setSignature(signature);
	 * paymentOnlineBean.setAmount(amount);
	 * paymentOnlineBean.setUdf1(candidateName);
	 * paymentOnlineBean.setUdf9("userId="+users.getUsername()+"~Name="+
	 * candidateName); paymentOnlineBean.setUdf2(candidateEmail);
	 * paymentOnlineBean.setUdf3(candidateMobile);
	 * paymentOnlineBean.setUdf21(users.getUsername()+"~"+"NCFE"+
	 * "~"+"https://NCFE23.onlineapplicationform.org/NCFE ");
	 * 
	 * String
	 * str="login="+vendorID+"&pass="+password+"&ttype="+ttype+"&prodid="+prodid+
	 * "&amt="+amount+
	 * "&txncurr="+txncurr+"&txnscamt="+txnscamt+"&clientcode="+clientCode+"&txnid="
	 * +txnid+ "&date="+txndate+"&custacc="+custacc+"&udf1="+candidateName+"&udf9="+
	 * paymentOnlineBean.getUdf9()+
	 * "&ru="+responseURL+"&udf2="+candidateEmail+"&udf3="+candidateMobile+"&udf21="
	 * +paymentOnlineBean.getUdf21() +"&signature="+signature;
	 * 
	 * 
	 * if (multiProd.equals("true")) { str =
	 * str.concat("&mprod=<products><product><id>1</id><name>NSE</name><amount>" +
	 * amountHalf + "</amount></product><product><id>1</id><name>TWO</name><amount>"
	 * + amountHalf + "</amount></product></products>"); }
	 * 
	 * logger.info("Request before encryption: "+str);
	 * commonService.insertCandidateAuditrail(users,
	 * "Payment Request before Encryption", "Candidate with User ID: "+
	 * users.getUsername()+" Request: "+str);
	 * 
	 * String enc = null; try { enc = Encrypt(str);
	 * logger.info("Encrypted String is :: " + enc);
	 * commonService.insertCandidateAuditrail(users,
	 * "Payment Request after Encryption", "Candidate with User ID: "+
	 * users.getUsername()+" Request: "+enc); } catch(Exception e) {
	 * logger.fatal(e,e); } String output =
	 * generateRequestURL(paymentOnlineBean,enc);
	 * logger.info("Payment request URL: "+output);
	 * 
	 * if(output!=null) { if(output.equals(GenericConstants.ERROR_MSG)) { String
	 * actionUrl
	 * ="AtomResponseAction_payMentDoneMsgDisplay.action?statusMsg=We are currently facing some issues in processing your payment. Please try again later."
	 * ; logger.info("Check the generation of URL.");
	 * request.setAttribute(DESTINATION_URL,actionUrl);
	 * response.sendRedirect(actionUrl); } String transactionAlreadyExist =
	 * paymentService.getExistingMerchantTxnNumber(loggedInUser, paymentOnlineBean);
	 * if(transactionAlreadyExist != null) {
	 * logger.info("Transaction already exist : Delete and insert transaction no");
	 * paymentService.deleteAndInsertTransactionIDForCandidate(paymentOnlineBean,
	 * loggedInUser, merchantTxnSeqNumber, txnid); } else {
	 * paymentService.insertTransactionIDForCandidate(paymentOnlineBean,
	 * loggedInUser, merchantTxnSeqNumber, txnid); } response.sendRedirect(output);
	 * logger.info("actionUrl"+output);
	 * 
	 * }else { output = "error";
	 * logger.error(PaymentOnlineAction.class+" :Response from Web Service "+output)
	 * ; return "errorPage"; } } catch (Exception e) {
	 * logger.info("connetToAtom() Error Found For User ID:"+loggedInUser.
	 * getUsername()+" Error:"+e.getMessage()); logger.error(e, e); } return null; }
	 */

	 
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