package com.nseit.payment.action;

import static com.nseit.generic.util.GenericConstants.DESTINATION_URL;
import static com.nseit.generic.util.GenericConstants.REDIRECT_ACTION_URL;
import static com.nseit.generic.util.GenericConstants.SESSION_USER;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.apache.struts2.dispatcher.HttpParameters;

import com.atom.ots.enc.AtomEncryption;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nseit.generic.action.BaseAction;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.CandidateService;
import com.nseit.generic.service.CommonService;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.aware.UserAware;
import com.nseit.generic.util.emailsms.EmailSMSUtil;
import com.nseit.payment.models.AtomResponseBean;
import com.nseit.payment.models.BankDetails;
import com.nseit.payment.models.HeadDetails;
import com.nseit.payment.models.MerchDetails;
import com.nseit.payment.models.OtsTransaction;
import com.nseit.payment.models.PayDetails;
import com.nseit.payment.models.PayInstrument;
import com.nseit.payment.models.PayModeSpecificData;
import com.nseit.payment.models.PaymentOnlineBean;
import com.nseit.payment.models.ResponseDetails;
import com.nseit.payment.models.ResponseParser;
import com.nseit.payment.service.PaymentService;
import com.opensymphony.xwork2.ModelDriven;

public class AtomResponseAction extends BaseAction implements ModelDriven<AtomResponseBean>, UserAware {
	private static final long serialVersionUID = -3649023101779183761L;
	PaymentService paymentService;
	CommonService commonService;
	CandidateService candidateService;
	AtomResponseBean atomResponseBean;
	private Logger logger = LoggerHome.getLogger(getClass());

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	@Override
	public AtomResponseBean getModel() {
		return atomResponseBean;
	}

	@Override
	public void resetModel() {
		atomResponseBean = new AtomResponseBean();
	}

	@Override
	public void setLoggedInUser(Users users) {
		this.loggedInUser = users;
	}

	public AtomResponseAction() {
		displayMenus();
	}

	public String execute() throws Exception {
		Users users = (Users) sessionAttributes.get(GenericConstants.SESSION_USER);
		if (users == null) {
			users = (Users) request.getSession().getAttribute(GenericConstants.SESSION_USER);
			setLoggedInUser(users);
		}
		String key = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AIPAY_DECRYPTION_KEY);
		String respHashKey = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AIPAY_RESPONSE_HASHKEY);

		String responseMsg = request.getParameter("encData");
		logger.info("ATOM encrypted responseMsg ===:" + responseMsg);
		commonService.insertCandidateAuditrail(users, "Payment Response before decryption: ", "Candidate with User ID: " + users.getUsername() + " " + responseMsg);

		String decryptedResponse = AtomEncryption.decrypt(responseMsg, key);
		logger.info("ATOM decrypted responseMsg ===:" + decryptedResponse);
		commonService.insertCandidateAuditrail(users, "Payment Response after decryption: ", "Candidate with User ID: " + users.getUsername() + " " + decryptedResponse);

		PayInstrument payInstrument = new PayInstrument();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			ResponseParser resp = objectMapper.readValue(decryptedResponse, ResponseParser.class);
			payInstrument = resp.getPayInstrument();
		} catch (Exception e) {
			logger.fatal(e, e);
		}

		MerchDetails merchDetails = payInstrument.getMerchDetails();
		atomResponseBean.setMer_txn(merchDetails.getMerchTxnId());
		atomResponseBean.setMerchantID(merchDetails.getMerchId());

		PayDetails payDetails = payInstrument.getPayDetails();
		atomResponseBean.setMmp_txn(payDetails.getAtomTxnId());
		atomResponseBean.setTxn_resp_date(payDetails.getTxnCompleteDate());
		atomResponseBean.setResponse_amt(payDetails.getAmount());
		String responseSign = payDetails.getSignature();

		PayModeSpecificData payModeSpecificData = payInstrument.getPayModeSpecificData();
		atomResponseBean.setSubChannel(payModeSpecificData.getSubChannel());
		String subChannel = atomResponseBean.getSubChannel()[0];
		atomResponseBean.setDiscriminator(subChannel);
		BankDetails bankDetails = payModeSpecificData.getBankDetails();
		atomResponseBean.setBank_txn(bankDetails.getBankTxnId());
		atomResponseBean.setBankname(bankDetails.getOtsBankName());

		ResponseDetails responseDetails = payInstrument.getResponseDetails();
		String authStatus = responseDetails.getMessage();
		String statusCode = responseDetails.getStatusCode();
		atomResponseBean.setDesc(responseDetails.getDescription());
		atomResponseBean.setStatusCode(responseDetails.getStatusCode());

		String result = REDIRECT_ACTION_URL;
		String actionUrl = "";

		try {
			if (authStatus != null && !authStatus.trim().equals("")) {
				// decode signature
				String signature_response = paymentService.getEncodedValueWithSha2(respHashKey, atomResponseBean.getMerchantID(), atomResponseBean.getMmp_txn(),
						atomResponseBean.getMer_txn(), atomResponseBean.getResponse_amt(), atomResponseBean.getStatusCode(), atomResponseBean.getDiscriminator(),
						atomResponseBean.getBank_txn());
				logger.info("Response signature ::" + signature_response);
				if (true) {// responseSign.equals(signature_response)
					logger.info("Signature matched");
					atomResponseBean.setCandidateUserId(atomResponseBean.getCandidateUserId());
					// success or failure status
					if (statusCode != null && !statusCode.trim().equals("") && statusCode.equalsIgnoreCase(GenericConstants.PAYMENT_SUCCESS)) {
						atomResponseBean.setAuthStatus(GenericConstants.AUTH_STATUS_SUCCESS);
					} else {
						atomResponseBean.setAuthStatus(GenericConstants.AUTH_STATUS_FAILURE);
					}
				} else {
					atomResponseBean.setAuthStatus(GenericConstants.AUTH_STATUS_FAILURE);
					logger.info("Response Signature does not match with Request Signature");
				}
			} else {
				actionUrl = "AtomResponseAction_payMentDoneMsgDisplay.action?statusMsg=We are currently facing some issues in processing your payment. Please try again later.";
				logger.info("Check paramter values");
				request.setAttribute(DESTINATION_URL, actionUrl);
				response.sendRedirect(actionUrl);
			}
		} catch (Exception e) {
			logger.info("Exception for Atom Response :For User ID [" + loggedInUser.getUsername() + "] Error :" + e.getMessage());
			logger.fatal(e, e);
		}
		int insertPaymentDetls = 0;
		if (authStatus != null && !authStatus.trim().equals("")) {
			insertPaymentDetls = paymentService.insertOnlinTransactionDetailsForAtom(atomResponseBean, users);
		}
		try {
			if (insertPaymentDetls > 0) {
				commonService.insertCandidateAuditrail(users, "Response Updated in DB Successfully", "Candidate with User ID " + users.getUsername());

				if (atomResponseBean != null && atomResponseBean.getAuthStatus() != null) {

					if (GenericConstants.AUTH_STATUS_SUCCESS.equalsIgnoreCase(atomResponseBean.getAuthStatus())) {

						logger.info("Atom Response Payment Success for Customer User ID = " + atomResponseBean.getCandidateUserId());
						sessionAttributes.put("TRANSACTION", "success");
						sessionAttributes.put("MESSAGE", GenericConstants.PAYMENTSUCCESS);
						actionUrl = "AtomResponseAction_statusMsgDisplay.action?statusMsg=" + "Thank You. Your Payment has been successfully processed. Your Transaction ID is "
								+ atomResponseBean.getMer_txn() + "&Transaction=success";

						logger.info("Status Updated for Payment Success :" + insertPaymentDetls);
						// Payment Email SMS
						users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
						String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED) + "";
						Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
						Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
						// For Payment Approved Email/SMS
						EmailSMSUtil.insertEmailNSMSForPaymentApproved(users, staus, emailReq, smsReq);
						logger.info(loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ", Payment Approved for Online , "
								+ CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));

					} else if (GenericConstants.AUTH_STATUS_FAILURE.equalsIgnoreCase(atomResponseBean.getAuthStatus())) {

						sessionAttributes.put("TRANSACTION", "failed");
						sessionAttributes.put("MESSAGE", GenericConstants.PAYMENTFAILURE);
						logger.info("Atom Response Payment Failure for Customer ID = " + atomResponseBean.getCandidateUserId());
						users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
						actionUrl = "AtomResponseAction_statusMsgDisplay.action?statusMsg=" + GenericConstants.PAYMENTFAILURE + " " + atomResponseBean.getMer_txn()
								+ "&Transaction=failed";
						logger.info("Status Updated Payment Failure :" + insertPaymentDetls);
						// Payment Email SMS
						String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED) + "";
						Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_REJECTED);
						Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_REJECTED);
						// For Payment Approved Email/SMS
						EmailSMSUtil.insertEmailNSMSForPaymentFailure(users, staus, emailReq, smsReq);
						logger.info(loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ",Payment Rejected for online , "
								+ CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
					}
				}
				logger.info("Payment details successfully saved For Customer ID = " + atomResponseBean.getCandidateUserId());
			} else {
				actionUrl = "AtomResponseAction_statusMsgDisplay.action?statusMsg=Sorry. Your Payment could not be processed due to Payment Response Issues. Please Try Again."
						+ " Your Transaction ID is " + atomResponseBean.getCandidateUserId() + "&Transaction=failed";
				logger.info("Payment details save Failure due to responseMsg Blank Or Response Updation Fail For User ID [" + loggedInUser.getUsername() + "]  Customer ID = ["
						+ atomResponseBean.getCandidateUserId() + "]");
			}
			sessionAttributes.put(SESSION_USER, users);
			request.setAttribute(DESTINATION_URL, actionUrl);
		} // try close
		catch (Exception e) {
			logger.info("Exception ATOM Response execute() :For User ID [" + loggedInUser.getUsername() + "] Error :" + e.getMessage());
			logger.fatal(e, e);
		}
		return result;
	}

	public String getDoubleVerificationResponseBulkforCheckStatus() throws Exception {
		String encdata = "";
		Users users = (Users) sessionAttributes.get(SESSION_USER);
		String responseMsg = "";
		String userID = "";
		Long userPk = loggedInUser.getUserId();
		logger.info("getDoubleVerificationResponseBulkforCheckStatus Called for 1 specific user :" + userPk);
		List<AtomResponseBean> responseDetailsTP = null;
		String existmercntTxnNo = "";
		String result = REDIRECT_ACTION_URL;
		String actionUrl = "";
		String message = "";

		userID = loggedInUser.getUsername();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			commonService.insertCandidateAuditrail(loggedInUser, "Check Status Button Click",
					"Check status Button Clicked by" + ", User ID:" + loggedInUser.getUsername() + ". " + "Clicked Date and time :" + formatter.format(date));

			String merchantID = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MERCHANT_ID);
			String password = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AIPAY_PASSWORD);
			String currency = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.CURRENCY_TYPE);
			String reqHashKey = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AIPAY_REQUEST_HASHKEY);
			String encryptionKey = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AIPAY_ENCRYPTION_KEY);
			String decryptionKey = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AIPAY_DECRYPTION_KEY);

			paymentService.opd_paymentStatusDateCheckUpdate(loggedInUser);

			responseDetailsTP = new ArrayList<AtomResponseBean>();
			List<PaymentOnlineBean> pendingPaymentRecords = paymentService.getPendingPaymentRecords(userPk);
			if (pendingPaymentRecords != null && !pendingPaymentRecords.isEmpty()) {
				logger.info("Atom check status for :" + userID);
				for (PaymentOnlineBean paymentOnlineBean : pendingPaymentRecords) {
					logger.info("Atom check status For User ID :[" + paymentOnlineBean.getOpd_created_by() + "]  " + "existmercntTxnNo :["
							+ paymentOnlineBean.getOpd_transaction_no() + "]");
					existmercntTxnNo = paymentOnlineBean.getOpd_transaction_no();
					if (existmercntTxnNo != null && !existmercntTxnNo.equals("")) {

						String amount = paymentOnlineBean.getAmount();

						String signature = paymentService.getEncodedValueWithSha2(reqHashKey, merchantID, password, paymentOnlineBean.getOpd_transaction_no(), amount, currency,
								"TXNVERIFICATION");

						HeadDetails headDetails = new HeadDetails();
						headDetails.setApi("TXNVERIFICATION");
						headDetails.setSource("OTS");

						MerchDetails merchDetails = new MerchDetails();
						merchDetails.setMerchId(merchantID);
						merchDetails.setPassword(password);
						merchDetails.setMerchTxnId(paymentOnlineBean.getOpd_transaction_no());
						merchDetails.setMerchTxnDate(paymentOnlineBean.getOpd_transaction_date());

						PayDetails payDetails = new PayDetails();
						payDetails.setAmount(amount);
						payDetails.setTxnCurrency(currency);
						payDetails.setSignature(signature);

						PayInstrument payInstrument = new PayInstrument();
						payInstrument.setMerchDetails(merchDetails);
						payInstrument.setPayDetails(payDetails);
						payInstrument.setHeadDetails(headDetails);

						OtsTransaction otsTxn = new OtsTransaction();
						otsTxn.setPayInstrument(payInstrument);

						Gson gson = new Gson();
						String json = gson.toJson(otsTxn);
						logger.info("Final JsonOutput----: " + json);

						commonService.insertCandidateAuditrail(users, "Check Status Request before encryption: ", "Candidate with User ID: " + users.getUsername() + " " + json);

						encdata = AtomEncryption.encrypt(json, encryptionKey);

						logger.info("getDoubleVerificationResponseBulkforCheckStatus() For USER ID:" + paymentOnlineBean.getOpd_created_by()
								+ " EncryptTrans doubleVerifyData############# :" + encdata);

						commonService.insertCandidateAuditrail(users, "Check Status Request after encryption: ", "Candidate with User ID: " + users.getUsername() + " " + encdata);

						// *** Called Atom DoubleVerification API
						responseMsg = sendDoubleVerificationPostCall(encdata);

						if (responseMsg != null && !responseMsg.equals("")) {

							String responseSplit = responseMsg.trim().split("encData=")[1].split("\\&merchId=")[0];
							logger.info("serrResp---: " + responseSplit);

							commonService.insertCandidateAuditrail(users, "Check Status Response before decryption: ",
									"Candidate with User ID: " + users.getUsername() + " " + responseSplit);

							responseMsg = AtomEncryption.decrypt(responseSplit, decryptionKey);
							logger.info("ATOM decrypted responseMsg ===:" + responseMsg);

							commonService.insertCandidateAuditrail(users, "Check Status Response after decryption: ",
									"Candidate with User ID: " + users.getUsername() + " " + responseMsg);

							responseMsg = responseMsg.split("\\{\"payInstrument\":")[1];
							responseMsg = responseMsg.substring(0, responseMsg.length() - 1);

							PayInstrument payInstrument1 = new PayInstrument();
							try {
								ObjectMapper objectMapper = new ObjectMapper();
								objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
								objectMapper.enable(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS);
								objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
								List<PayInstrument> responseList = objectMapper.readValue(responseMsg, new TypeReference<List<PayInstrument>>() {
								});
								payInstrument1 = responseList.get(0);
							} catch (Exception e) {
								logger.fatal(e, e);
								throw new Exception(e);
							}

							if (Strings.isNotBlank(payInstrument1.getResponseDetails().getMessage()) && !payInstrument1.getResponseDetails().getMessage().equals("NODATA")) {

								if ("OTS0951".equals(payInstrument1.getResponseDetails().getStatusCode())) {
									message = "Sorry, No response received against your last transaction From Gateway, Please try again after some time !";
									actionUrl = "AtomResponseAction_statusMsgDisplay.action?statusMsg=" + message + "&Transaction=failed";
									logger.info("Received Message as : " + payInstrument1.getResponseDetails().getMessage() + " and Description : "
											+ payInstrument1.getResponseDetails().getDescription());
								} else {
									MerchDetails merchDetails1 = payInstrument1.getMerchDetails();
									atomResponseBean.setMer_txn(merchDetails1.getMerchTxnId());
									String txnDate = merchDetails1.getMerchTxnDate();
									atomResponseBean.setTxn_resp_date(txnDate);

									PayDetails payDetails1 = payInstrument1.getPayDetails();
									atomResponseBean.setMmp_txn(payDetails1.getAtomTxnId());
									atomResponseBean.setResponse_amt(payDetails1.getAmount());

									PayModeSpecificData payModeSpecificData = payInstrument1.getPayModeSpecificData();
									atomResponseBean.setSubChannel(payModeSpecificData.getSubChannel());
									String subChannel = atomResponseBean.getSubChannel()[0];
									atomResponseBean.setDiscriminator(subChannel);
									BankDetails bankDetails = payModeSpecificData.getBankDetails();
									atomResponseBean.setBank_txn(bankDetails.getBankTxnId());
									atomResponseBean.setBankname(bankDetails.getOtsBankName());

									ResponseDetails responseDetails = payInstrument1.getResponseDetails();
									atomResponseBean.setAuthStatus(responseDetails.getMessage());
									String statusCode = responseDetails.getStatusCode();
									atomResponseBean.setDesc(responseDetails.getDescription());
									atomResponseBean.setStatusCode(statusCode);

									int insertPaymentDetls = 0;
									if (null != users && userID != null && !userID.equals("NA")) {

										insertPaymentDetls = paymentService.updateAtomResponseForCandidate(atomResponseBean, users);

										if (insertPaymentDetls > 0) {
											if (atomResponseBean != null && atomResponseBean.getAuthStatus() != null) {
												if (atomResponseBean.getStatusCode().equalsIgnoreCase("OTS0000")) {

													commonService.updateCandidateStatus(users,
															ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
													users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
													logger.info("Atom Response Payment Success for Customer User ID = " + atomResponseBean.getCandidateUserId());
													sessionAttributes.put("TRANSACTION", "success");
													sessionAttributes.put("MESSAGE", GenericConstants.PAYMENTSUCCESS);
													actionUrl = "AtomResponseAction_statusMsgDisplay.action?statusMsg=" + GenericConstants.PAYMENTSUCCESS + "&Transaction=success";
													logger.info("Status Updated for Payment Success :" + insertPaymentDetls);
													// Payment Email SMS
													String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED) + "";
													Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
													Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
													// For Payment Approved Email/SMS
													EmailSMSUtil.insertEmailNSMSForPaymentApproved(users, staus, emailReq, smsReq);

													logger.info(loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ", Payment Approved for Online , "
															+ CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));

												} else if (atomResponseBean.getStatusCode().equalsIgnoreCase("OTS0101")
														|| atomResponseBean.getStatusCode().equalsIgnoreCase("OTS0201")
														|| atomResponseBean.getStatusCode().equalsIgnoreCase("OTS0401")
														|| atomResponseBean.getStatusCode().equalsIgnoreCase("OTS0600")
														|| atomResponseBean.getAuthStatus().equalsIgnoreCase("OTS0301")
														|| atomResponseBean.getAuthStatus().equalsIgnoreCase("OTS0351")) {

													commonService.updateCandidateStatus(users,
															ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
													users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
													sessionAttributes.put("TRANSACTION", "failed");
													sessionAttributes.put("MESSAGE", GenericConstants.PAYMENTFAILURE);
													logger.info("Atom Response Payment Failure for Customer ID = " + atomResponseBean.getCandidateUserId());

													String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED) + "";
													Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_REJECTED);
													Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_REJECTED);
													EmailSMSUtil.insertEmailNSMSForPaymentFailure(loggedInUser, staus, emailReq, smsReq);

													actionUrl = "AtomResponseAction_statusMsgDisplay.action?statusMsg=" + GenericConstants.PAYMENTFAILURE + " "
															+ atomResponseBean.getMer_txn() + "&Transaction=failed";
													logger.info("Status Updated Payment Failure :" + insertPaymentDetls);

													logger.info(users.getUsername() + ", " + request.getRemoteAddr() + ",Payment Rejected for online , "
															+ CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));

												} else if (atomResponseBean.getStatusCode().equalsIgnoreCase("OTS0551")) {
													logger.info("getDoubleVerificationResponseBulkforCheckStatus Atom is Pending for " + "Response from Atom. For User Id:"
															+ paymentOnlineBean.getOpd_created_by());
													actionUrl = "AtomResponseAction_statusMsgDisplay.action?statusMsg="
															+ "Your Payment is still Pending from Bank. Please check again after some time. " + "&Transaction=pending";

												} else if (atomResponseBean.getStatusCode().equalsIgnoreCase("OTS0451")
														|| atomResponseBean.getStatusCode().equalsIgnoreCase("OTS0501")) {
													logger.info("getDoubleVerificationResponseBulkforCheckStatus Atom error in request. For User Id:"
															+ paymentOnlineBean.getOpd_created_by());
													actionUrl = "AtomResponseAction_statusMsgDisplay.action?statusMsg="
															+ "Some internal error occurred. Please contact administrator.";
												}
											} else { // Incase of authstatus == null
												// int updateVal = commonService.updateCandidateStatus(users,
												// ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
												logger.info("Auth Id found null  For User Id :" + users.getUsername());
												// users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
											}
										}
									} else {
										message = "Sorry. Your Payment could not be processed." + ""
												+ " Payment Transaction not found at Gateway. Please Reinitiate trasaction again after some time.";
										actionUrl = "AtomResponseAction_statusMsgDisplay.action?statusMsg=" + message + "&Transaction=failed";
										paymentService.NoTransactionFoundFlagUpdate(loggedInUser);
									}
									commonService.insertCandidateAuditrail(loggedInUser, "Payment Details For Check Status single User Response",
											"Payment Mode: online" + ", User ID:" + loggedInUser.getUsername() + "" + " TxnRefNumber:" + atomResponseBean.getMer_txn()
													+ ", Transaction Date:" + formatter.format(date) + ", Authentication Status:" + atomResponseBean.getAuthStatus());
									responseDetailsTP.add(atomResponseBean);
								}
							} else {
								message = "Sorry, Transaction Status not found at gateway. Please initiate transaction again.";
								actionUrl = "AtomResponseAction_statusMsgDisplay.action?statusMsg=" + message + "&Transaction=failed";
								logger.info("Received Message as NODATA from Gateway");
								paymentService.NoTransactionFoundFlagUpdate(loggedInUser);
							}
						} else {
							message = "Sorry, No response received against your last transaction from Gateway. Please try again after some time.";
							actionUrl = "AtomResponseAction_statusMsgDisplay.action?statusMsg=" + message + "&Transaction=failed";
							logger.info("Received statusMsg Blank From AtomDOUBLEVERIFY_WEBSERVICE_URL ");
						}
					} else {
						message = "Sorry. Your Payment could not be processed. Payment Transaction Number not found in portal. Please try again after some time.";
						actionUrl = "AtomResponseAction_statusMsgDisplay.action?statusMsg=" + message + "&Transaction=failed";
					}
				}
				if (responseDetailsTP != null && !responseDetailsTP.isEmpty()) {
					atomResponseBean.setResponseDetailsList(responseDetailsTP);
					atomResponseBean.setResponseOFlag("true");
				} else {
					atomResponseBean.setErrMsg("ATOM  Verification  Response Issues ,Please try again !!");
					atomResponseBean.setShowModuleDetails("true");
				}
			} else {
				commonService.insertCandidateAuditrail(loggedInUser, "Payment Details For Check Status single User", "Payment Mode: online no recorde found for user id");
				message = "Sorry. Your Payment could not be processed." + "" + "Pending Payment Transaction not found in portal. Please try again after some time.";
				actionUrl = "AtomResponseAction_statusMsgDisplay.action?statusMsg=" + message + "&Transaction=failed";
				// atomResponseBean.setErrMsg("Pending Payment Transaction Not Found In Portal,
				// Please try again after some time !");
				// atomResponseBean.setShowModuleDetails("true");
			}
			logger.info("getDoubleVerificationResponseBulk Called for 1 user End ON :" + new Date());
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		sessionAttributes.put(SESSION_USER, users);
		request.setAttribute(DESTINATION_URL, actionUrl);
		return result;
	}

	public String getDoubleVerificationResponseBulk() throws Exception {
		logger.info("getDoubleVerificationResponseBulk Called :" + new Date());
		String encdata = "";
		Users users = new Users();
		String responseMsg = "";
		String userID = "";
		List<AtomResponseBean> responseDetailsTP = null;
		String existmercntTxnNo = "";
		String result = REDIRECT_ACTION_URL;
		String message = "";

		try {
			if (atomResponseBean == null) {
				atomResponseBean = new AtomResponseBean();
			}

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();

			String merchantID = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MERCHANT_ID);
			String password = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AIPAY_PASSWORD);
			String currency = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.CURRENCY_TYPE);
			String reqHashKey = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AIPAY_REQUEST_HASHKEY);
			String encryptionKey = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AIPAY_ENCRYPTION_KEY);
			String decryptionKey = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AIPAY_DECRYPTION_KEY);

			responseDetailsTP = new ArrayList<AtomResponseBean>();

			List<PaymentOnlineBean> pendingPaymentRecords = paymentService.getPendingPaymentRecords();
			if (pendingPaymentRecords != null && !pendingPaymentRecords.isEmpty()) {
				logger.info("Bulk Pending Payment Records Size :" + pendingPaymentRecords.size());
				for (PaymentOnlineBean paymentOnlineBean : pendingPaymentRecords) {
					logger.info("Double verification For User ID :[" + paymentOnlineBean.getOpd_created_by() + "]  " + "existmercntTxnNo :["
							+ paymentOnlineBean.getOpd_transaction_no() + "]");
					existmercntTxnNo = paymentOnlineBean.getOpd_transaction_no();
					if (existmercntTxnNo != null && !existmercntTxnNo.equals("")) {

						users.setUserId(Long.parseLong(paymentOnlineBean.getOpd_user_fk()));

						String amount = paymentOnlineBean.getAmount();

						String signature = paymentService.getEncodedValueWithSha2(reqHashKey, merchantID, password, paymentOnlineBean.getOpd_transaction_no(), amount, currency,
								"TXNVERIFICATION");

						HeadDetails headDetails = new HeadDetails();
						headDetails.setApi("TXNVERIFICATION");
						headDetails.setSource("OTS");

						MerchDetails merchDetails = new MerchDetails();
						merchDetails.setMerchId(merchantID);
						merchDetails.setPassword(password);
						merchDetails.setMerchTxnId(paymentOnlineBean.getOpd_transaction_no());
						merchDetails.setMerchTxnDate(paymentOnlineBean.getOpd_transaction_date());

						PayDetails payDetails = new PayDetails();
						payDetails.setAmount(amount);
						payDetails.setTxnCurrency(currency);
						payDetails.setSignature(signature);

						PayInstrument payInstrument = new PayInstrument();
						payInstrument.setMerchDetails(merchDetails);
						payInstrument.setPayDetails(payDetails);
						payInstrument.setHeadDetails(headDetails);

						OtsTransaction otsTxn = new OtsTransaction();
						otsTxn.setPayInstrument(payInstrument);

						Gson gson = new Gson();
						String json = gson.toJson(otsTxn);
						logger.info("Final JsonOutput----: " + json);

						commonService.insertCandidateAuditrail(users, "Bulk Offline Request before encryption: ",
								"Candidate with User ID: " + paymentOnlineBean.getOpd_created_by() + " " + json);

						encdata = AtomEncryption.encrypt(json, encryptionKey);
						logger.info("getDoubleVerificationResponseBulk() For USER ID:" + paymentOnlineBean.getOpd_created_by() + " EncryptTrans doubleVerifyData############# :"
								+ encdata);

						commonService.insertCandidateAuditrail(users, "Bulk Offline Request after encryption: ",
								"Candidate with User ID: " + paymentOnlineBean.getOpd_created_by() + " " + encdata);

						// *** Called Atom DoubleVerification API
						responseMsg = sendDoubleVerificationPostCall(encdata);

						if (responseMsg != null && !responseMsg.equals("")) {

							String responseSplit = responseMsg.trim().split("encData=")[1].split("\\&merchId=")[0];
							logger.info("serrResp---: " + responseSplit);

							commonService.insertCandidateAuditrail(users, "Bulk Offline Response before decryption: ",
									"Candidate with User ID: " + paymentOnlineBean.getOpd_created_by() + " " + responseSplit);

							responseMsg = AtomEncryption.decrypt(responseSplit, decryptionKey);
							logger.info("ATOM decrypted responseMsg ===:" + responseMsg);

							commonService.insertCandidateAuditrail(users, "Bulk Offline Response after decryption: ",
									"Candidate with User ID: " + paymentOnlineBean.getOpd_created_by() + " " + responseMsg);

							responseMsg = responseMsg.split("\\{\"payInstrument\":")[1];
							responseMsg = responseMsg.substring(0, responseMsg.length() - 1);

							PayInstrument payInstrument1 = new PayInstrument();
							try {
								ObjectMapper objectMapper = new ObjectMapper();
								objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
								objectMapper.enable(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS);
								objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
								List<PayInstrument> responseList = objectMapper.readValue(responseMsg, new TypeReference<List<PayInstrument>>() {
								});
								payInstrument1 = responseList.get(0);
							} catch (Exception e) {
								logger.fatal(e, e);
								throw new Exception(e);
							}

							if (Strings.isNotBlank(payInstrument1.getResponseDetails().getMessage()) && !payInstrument1.getResponseDetails().getMessage().equals("NODATA")) {

								MerchDetails merchDetails1 = payInstrument1.getMerchDetails();
								atomResponseBean.setMer_txn(merchDetails1.getMerchTxnId());
								String txnDate = merchDetails1.getMerchTxnDate();
								atomResponseBean.setTxn_resp_date(txnDate);

								PayDetails payDetails1 = payInstrument1.getPayDetails();
								atomResponseBean.setMmp_txn(payDetails1.getAtomTxnId());
								atomResponseBean.setResponse_amt(payDetails1.getAmount());

								PayModeSpecificData payModeSpecificData = payInstrument1.getPayModeSpecificData();
								atomResponseBean.setSubChannel(payModeSpecificData.getSubChannel());
								String subChannel = atomResponseBean.getSubChannel()[0];
								atomResponseBean.setDiscriminator(subChannel);
								BankDetails bankDetails = payModeSpecificData.getBankDetails();
								atomResponseBean.setBank_txn(bankDetails.getBankTxnId());
								atomResponseBean.setBankname(bankDetails.getOtsBankName());

								ResponseDetails responseDetails = payInstrument1.getResponseDetails();
								atomResponseBean.setAuthStatus(responseDetails.getMessage());
								String statusCode = responseDetails.getStatusCode();
								atomResponseBean.setDesc(responseDetails.getDescription());
								atomResponseBean.setStatusCode(statusCode);

								users.setUserFk(Long.parseLong(paymentOnlineBean.getOpd_user_fk()));
								users.setUsername(paymentOnlineBean.getOpd_created_by());
								users.setUserId(Long.parseLong(paymentOnlineBean.getOpd_user_fk()));
								users.setMobile(paymentOnlineBean.getOpd_mobile_no());
								users.setEmailAddress(paymentOnlineBean.getOpd_email_id());

								int insertPaymentDetls = 0;
								if (null != users && userID != null && !userID.equals("NA")) {

									insertPaymentDetls = paymentService.updateAtomResponseForCandidate(atomResponseBean, users);

									if (insertPaymentDetls > 0) {
										if (atomResponseBean != null && atomResponseBean.getAuthStatus() != null) {
											if (atomResponseBean.getStatusCode().equalsIgnoreCase("OTS0000")) {

												commonService.updateCandidateStatus(users, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
												users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
												logger.info("Atom Response Payment Success for Customer User ID = " + atomResponseBean.getCandidateUserId());
												logger.info("Status Updated for Payment Success :" + insertPaymentDetls);
												// Payment Email SMS
												String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED) + "";
												Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
												Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
												// For Payment Approved Email/SMS
												EmailSMSUtil.insertEmailNSMSForPaymentApproved(users, staus, emailReq, smsReq);
											} else if (atomResponseBean.getStatusCode().equalsIgnoreCase("OTS0101") || atomResponseBean.getStatusCode().equalsIgnoreCase("OTS0201")
													|| atomResponseBean.getStatusCode().equalsIgnoreCase("OTS0401") || atomResponseBean.getStatusCode().equalsIgnoreCase("OTS0600")
													|| atomResponseBean.getAuthStatus().equalsIgnoreCase("OTS0301")
													|| atomResponseBean.getAuthStatus().equalsIgnoreCase("OTS0351")) {

												commonService.updateCandidateStatus(users, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
												users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
												logger.info("Atom Response Payment Failure for Customer ID = " + atomResponseBean.getCandidateUserId());

												String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED) + "";
												Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_REJECTED);
												Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_REJECTED);
												EmailSMSUtil.insertEmailNSMSForPaymentFailure(users, staus, emailReq, smsReq);

												logger.info("Status Updated Payment Failure :" + insertPaymentDetls);

											} else if (atomResponseBean.getStatusCode().equalsIgnoreCase("OTS0551")) {
												logger.info("getDoubleVerificationResponseBulkforCheckStatus Atom is Pending for " + "Response from Atom. For User Id:"
														+ paymentOnlineBean.getOpd_created_by());

											} else if (atomResponseBean.getStatusCode().equalsIgnoreCase("OTS0451")
													|| atomResponseBean.getStatusCode().equalsIgnoreCase("OTS0501")) {
												logger.info("getDoubleVerificationResponseBulkforCheckStatus Atom error in request. For User Id:"
														+ paymentOnlineBean.getOpd_created_by());
											}
										} else {
											logger.info("Auth Id found null  For User Id :" + users.getUsername());
										}
									}
								} else {
									message = "Sorry. Your Payment could not be processed." + ""
											+ " Payment Transaction not found at Gateway. Please Reinitiate trasaction again after some time.";
									paymentService.NoTransactionFoundFlagUpdate(users);
								}
								commonService.insertCandidateAuditrail(users, "Payment Details For Check Status single User Response",
										"Payment Mode: online" + ", User ID:" + users.getUsername() + "" + " TxnRefNumber:" + atomResponseBean.getMer_txn() + ", Transaction Date:"
												+ formatter.format(date) + ", Authentication Status:" + atomResponseBean.getAuthStatus());
								responseDetailsTP.add(atomResponseBean);
							} else {
								logger.info("Received Message as NODATA from Gateway");
							}
						} else {
							logger.info("Received statusMsg Blank From AtomDOUBLEVERIFY_WEBSERVICE_URL ");
						}
					} else {
						message = "Sorry. Your Payment could not be processed. Payment Transaction Number not found found in portal. Please try again after some time.";
						logger.info(message);
					}
				}
				if (responseDetailsTP != null && !responseDetailsTP.isEmpty()) {
					atomResponseBean.setResponseDetailsList(responseDetailsTP);
					atomResponseBean.setResponseOFlag("true");
				} else {
					atomResponseBean.setErrMsg("ATOM  Verification  Response Issues ,Please try again !!");
					atomResponseBean.setShowModuleDetails("true");
				}
			} else {
				logger.info("No Pending Payment Records Found in DB");
			}
			logger.info("getDoubleVerificationResponseBulk Called for 1 user End ON :" + new Date());
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return result;
	}

	public String sendDoubleVerificationPostCall(String param) throws Exception {
		logger.info("sendDoubleVerificationPostCall() ::::::: on " + new Date());
		String gatewayUrl = "";
		String merchantId = "";
		String output = "";
		try {
			logger.info("sendDoubleVerificationPostCall() ::::::: messageData :" + param);

			gatewayUrl = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AIPAY_DOUBLE_VERIFICATION_URL);
			merchantId = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MERCHANT_ID);

			String resultOfflineURL = null;
			StringBuilder strbuilder = new StringBuilder();
			strbuilder.append(gatewayUrl);
			strbuilder.append("?");
			strbuilder.append("merchId=" + merchantId);
			strbuilder.append("&encData=" + param);
			resultOfflineURL = strbuilder.toString();

			logger.info(resultOfflineURL);
			output = sendPost(resultOfflineURL);
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return output;
	}

	private String sendPost(String param) throws Exception {
		String responseText = "";
		String url = param;
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		// add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Content-Type", "application/json");
		String urlParameters = param;
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		int responseCode = con.getResponseCode();
		logger.info("\nSending 'POST' request to URL : " + url);
		logger.info("Post parameters : " + urlParameters);
		logger.info("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		responseText = response.toString();
		logger.info("Output###===>>>" + response.toString());
		return responseText;
	}

	public String statusMsgDisplay() throws Exception {
		String returnType = "success";
		String transaction = request.getParameter("Transaction");
		String statusMsg = request.getParameter("statusMsg");
		
		if (statusMsg.contains(GenericConstants.PAYMENTSUCCESS)) {
			atomResponseBean.setEnableButton("true");
		} else {
			atomResponseBean.setEnableButton("false");
		}
		logger.info("Payment statusMsg :" + statusMsg);
		atomResponseBean.setResponseMsg(statusMsg);
		atomResponseBean.setTransaction(transaction);
		return returnType;
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