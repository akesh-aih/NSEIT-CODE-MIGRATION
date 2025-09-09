package com.nseit.payment.action;

import static com.nseit.generic.util.GenericConstants.DESTINATION_URL;
import static com.nseit.generic.util.GenericConstants.SESSION_USER;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Logger;
import org.apache.struts2.dispatcher.HttpParameters;

import com.nseit.generic.action.BaseAction;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.CommonService;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.aware.UserAware;
import com.nseit.payment.models.PaymentOnlineBean;
import com.nseit.payment.models.TechProcessResponseBean;
import com.nseit.payment.service.PaymentService;
import com.opensymphony.xwork2.ModelDriven;

public class TechProcessOnlineAction extends BaseAction implements ModelDriven<PaymentOnlineBean>, UserAware {

	private static final long serialVersionUID = -3029055441573858573L;

	private Logger logger = LoggerHome.getLogger(getClass());
	private PaymentOnlineBean paymentBean = new PaymentOnlineBean();
	private PaymentService paymentService;
	private CommonService commonService;

	@Override
	public void setLoggedInUser(Users users) {
		this.loggedInUser = users;
	}

	@Override
	public void resetModel() {
		// TODO Auto-generated method stub

	}

	@Override
	public PaymentOnlineBean getModel() {
		return paymentBean;
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public TechProcessOnlineAction() {
		displayMenus();
	}

	public String connetToTechProcessSingleURL() {
		HttpSession session = request.getSession(true);
		Users loggedInUser = (Users) session.getAttribute(SESSION_USER);
		TechProcessResponseBean techProcessResponseBean = new TechProcessResponseBean();
		logger.info("Tech Process Online Payment connetToTechProcessSingleURL() calling For User Id :" + loggedInUser.getUsername());
		try {
			String requestType = "T";
			String merchantCode = "";
			String merchantTxnRefNumberPrefix = "";
			String merchantTxnSeqNumber = "";
			String merchantTxnRefNumber = "";
			String schemeCode = "";
			String encryptionKey = "";
			String encryptionIV = "";
			String additionInfo = "";
			String paymentUrl = "";
			String responseUrl = "";
			String transactionTimeOut = "";
			String currencyCode = "";
			// String candidateCourse =
			// paymentService.getCandidateCourse(loggedInUser);

			/*
			 * paymentBean.setTestFK(candidateCourse);
			 * loggedInUser.setDisciplineID(candidateCourse);
			 */
			// Prerequisite for making URL for TP-PG [Start]
			merchantCode = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MERCHANT_ID);
			merchantTxnRefNumberPrefix = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.TXN_REF_NUMBER_PREFIX);// Need
																																		// to
																																		// insert
																																		// in
																																		// config
																																		// table
			// getting the TestFk,Application number

			String transactionAlreadyExist = paymentService.getExistingMerchantTxnNumber(loggedInUser, paymentBean);

			// paymentService.getExistingMerchantTxnNumber(loggedInUser,
			// paymentBean);

			merchantTxnSeqNumber = paymentService.getTransactionUniqueNumber();

			StringBuilder txnRefnumber = new StringBuilder();
			txnRefnumber.append(merchantTxnRefNumberPrefix);
			txnRefnumber.append("-");
			txnRefnumber.append(merchantTxnSeqNumber);
			merchantTxnRefNumber = txnRefnumber.toString();

			// bankCode =
			// "";//ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.BANK_CODE);
			encryptionKey = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.PG_ENCRYPTION_KEY);// Config
																													// Param
			encryptionIV = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.PG_ENCRYPTION_IV);
			// accountNumber =
			// ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.ACCOUNT_NUMBER);
			schemeCode = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SCHEME_CODE);
			// internalPGWebServiceUrl =
			// ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_PAYMENT_WEB_SERVICE_URL);
			paymentUrl = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.TP_REQUEST_URL);
			responseUrl = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.TP_RETURN_URL);
			transactionTimeOut = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.TP_TIME_OUT);
			currencyCode = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.CURRENCY_TYPE);
			// Prerequisite for making URL for TP-PG [End] []

			// Fetching Candidate data for URL for TP-PG [Start] []
			String postApp = "";
			String amount = "";
			String cartDetails = "";
			String txnDate = "";
			String caste = "";
			String category = "";
			String disability = "";
			String gender = "";
			String paymentAmount = "";
			try {

				Map<String, String> candidateValues = commonService.getCandidatesDataAutoApprovePayment(loggedInUser.getUsername());

				category = candidateValues.get("category");
				disability = candidateValues.get("disabled");
				gender = candidateValues.get("gender");
				String exservice = candidateValues.get("isExserviceman");
				if ((gender != null && !gender.equals("") && gender.equals("11"))
						|| (category != null && !category.equals("") && (category.equals("2") || category.equals("5") || category.equals("7")))
						|| (disability != null && !disability.equals("") && disability.equals("352")) || (exservice != null && !exservice.equals("") && exservice.equals("352"))) {
					amount = ConfigurationConstants.getInstance().getPropertyVal("SC_ST_AMOUNT");
				} else {
					amount = ConfigurationConstants.getInstance().getPropertyVal("GENERAL_OBC_AMOUNT");
				}
				String postAppliedFor = commonService.getPostAppliedFor(loggedInUser.getUserFk());
				if (postAppliedFor != null) {
					String post = postAppliedFor;
					String discliplineSelectedList[] = post.split(", ");
					postApp = discliplineSelectedList[0];
					int amt = Integer.valueOf(amount) * discliplineSelectedList.length;
					String totalFees = String.valueOf(amt);
					paymentAmount = totalFees;
				}
			} catch (Exception e) {
				logger.error("Amount = " + amount);
				logger.error(TechProcessOnlineAction.class + " :Error in getting candidate caste and fees amount");
				return "errorPage";
			}
			// amount = paymentService.getFeesDetail(Integer.parseInt(post));
			logger.info("connetToTechProcessSingleURL() calling For User Id :" + loggedInUser.getUsername() + " disability :" + disability + " caste :" + caste);
			logger.info("connetToTechProcessSingleURL() calling For User Id :" + loggedInUser.getUsername() + " Amount :" + amount);

			StringBuilder cartDetailBuilder = new StringBuilder();
			cartDetailBuilder.append(schemeCode);
			// cartDetailBuilder.append("FIRST");
			cartDetailBuilder.append("_");
			cartDetailBuilder.append(amount);
			cartDetailBuilder.append(".0_");
			cartDetailBuilder.append("0.0");
			cartDetails = cartDetailBuilder.toString();

			StringBuilder additionalInfoBuilder = new StringBuilder();
			additionalInfoBuilder.append(loggedInUser.getUsername());
			additionalInfoBuilder.append("-");

			additionalInfoBuilder.append(postApp); // module/job ID
			additionInfo = additionalInfoBuilder.toString();

			SimpleDateFormat sdfmt1 = new SimpleDateFormat("dd-MM-yyyy");
			Date currentDate = new Date();
			txnDate = sdfmt1.format(currentDate);
			/*
			 * if(loggedInUser.getDisciplineID().equals("29"))
			 * loggedInUser.setDiscipline("Paper-I");
			 * 
			 * if(loggedInUser.getDisciplineID().equals("30"))
			 * loggedInUser.setDiscipline("Paper-II");
			 */
			// Fetching Candidate data for URL for TP-PG [End] []

			// Calling NSEIT Payment Service [Start] []

			techProcessResponseBean.setRequestType(requestType);
			techProcessResponseBean.setMerchantCode(merchantCode);
			techProcessResponseBean.setMerchantTxnRefNumber(merchantTxnRefNumber);
			techProcessResponseBean.setAmount(amount + ".00");
			techProcessResponseBean.setCartDetails(cartDetails);
			techProcessResponseBean.setTxnDate(txnDate);
			techProcessResponseBean.setTPSLTransactionID("");
			techProcessResponseBean.setBankCode("");
			techProcessResponseBean.setAccountNumber("");
			techProcessResponseBean.setCustID("");
			// techProcessResponseBean.setMobileNumber(loggedInUser.getMobile()+loggedInUser.getDisciplineID());
			// // For ERROR107 Duplicate Dedup
			techProcessResponseBean.setMobileNumber(loggedInUser.getMobile() + merchantTxnSeqNumber);
			techProcessResponseBean.setCustomerName("");
			techProcessResponseBean.setEmailID(loggedInUser.getEmailAddress());
			techProcessResponseBean.setEncryptionKey(encryptionKey);
			techProcessResponseBean.setEncryptionIV(encryptionIV);
			techProcessResponseBean.setITC(additionInfo);
			techProcessResponseBean.setPaymentUrl(paymentUrl);
			techProcessResponseBean.setResponseUrl(responseUrl);
			techProcessResponseBean.setTransactionTimeOut(transactionTimeOut);
			techProcessResponseBean.setCurrencyCode(currencyCode);
			String output = "";
			output = paymentService.getTokenGenerated(techProcessResponseBean);

			String payAuditStr = "Candidate with User Id " + loggedInUser.getUsername() + " has clicked on Proceed to pay " + " with Payment Id :" + merchantTxnRefNumber
					+ " caste :" + caste + " Amount :" + amount;
			commonService.insertCandidateAuditrail(loggedInUser, "Proceed to pay", payAuditStr);

			// if(CResponse.getStatus() == 200)
			if (output != null) {
				if (output.contains("ERROR")) {
					String actionUrl = "TechProcessResponseAction_payMentDoneMsgDisplay.action?statusMsg=Your Payment is awaiting confirmation from Payment Gateway, Kindly check your Payment status on dashboard after 24 hours. If Payment Status is approved you will be able to download Payment Receipt.";
					request.setAttribute(DESTINATION_URL, actionUrl);
					response.sendRedirect(actionUrl);
				} else {
					if (transactionAlreadyExist != null) {
						logger.info("Transaction already exist : Delete and insert transaction no for User Id :" + loggedInUser.getUsername());
						paymentService.deleteAndInsertTransactionIDForCandidate(paymentBean, loggedInUser, merchantTxnSeqNumber, merchantTxnRefNumber);

					} else {
						paymentService.insertTransactionIDForCandidate(paymentBean, loggedInUser, merchantTxnSeqNumber, merchantTxnRefNumber);
					}
					response.sendRedirect(output);
				}
			} else {
				output = "error";
				// logger.info("Error in connecting Web Service");
				// logger.error(TechProcessOnlineAction.class+" :Response from
				// Web Service "+CResponse.getStatus());
				logger.error(TechProcessOnlineAction.class + " :Response from Web Service " + output);
				return "errorPage";
			}
		} catch (Exception e) {
			logger.error(e, e);
			logger.error(TechProcessOnlineAction.class + " :Error in connecting Web Service");
			return "errorPage";
		}
		return null;
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
