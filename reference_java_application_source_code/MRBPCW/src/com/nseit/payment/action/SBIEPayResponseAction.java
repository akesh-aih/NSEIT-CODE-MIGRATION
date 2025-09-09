package com.nseit.payment.action;

import static com.nseit.generic.util.GenericConstants.DESTINATION_URL;
import static com.nseit.generic.util.GenericConstants.GLOBAL_PLAIN_TEXT_MESSAGE;
import static com.nseit.generic.util.GenericConstants.REDIRECT_ACTION_URL;
import static com.nseit.generic.util.GenericConstants.SESSION_USER;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
//import org.apache.log4j.Logger;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.dispatcher.HttpParameters;

import com.nseit.generic.action.BaseAction;
import com.nseit.generic.models.PaymentBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.CommonService;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.aware.UserAware;
import com.nseit.generic.util.emailsms.EmailSMSUtil;
import com.nseit.payment.models.PaymentOnlineBean;
import com.nseit.payment.models.SBIResponseBean;
import com.nseit.payment.service.PaymentService;
import com.opensymphony.xwork2.ModelDriven;
import com.toml.dp.util.AES128Bit;

public class SBIEPayResponseAction extends BaseAction implements ModelDriven<SBIResponseBean>, UserAware {

	private static final long serialVersionUID = -3649023101779183761L;
	public SBIResponseBean sbiResponseBean;
	private Logger logger = LoggerHome.getLogger(getClass());

	private CommonService commonService;
	private PaymentService paymentService;

	private PaymentOnlineBean paymentBean = new PaymentOnlineBean();

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public SBIEPayResponseAction() {
		displayMenus();
	}

	@Override
	public void resetModel() {
		sbiResponseBean = new SBIResponseBean();
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	@Override
	public SBIResponseBean getModel() {
		return sbiResponseBean;
	}

	@Override
	public void setLoggedInUser(Users users) {
		this.loggedInUser = users;
	}

	// SBIePay HitURL
	static String Decrypt(String encData) {
		System.out.println("decrypt(String encData, String merchantCode, String delimiter) method begin");
		String decdata = null;
		try {
			String fileUrl = null;
			String RSAKey = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.RSA_KEY_PATH);
			Path path = Paths.get(RSAKey + "\\UP_PRPB.key");
			if (Files.exists(path)) {
				fileUrl = path.toString();
			}
			byte[] key = null;
			key = returnbyte(fileUrl);
			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
			byte[] results = Base64.decodeBase64(encData);
			byte[] iv = Arrays.copyOfRange(results, 0, cipher.getBlockSize());
			cipher.init(2, keySpec, new GCMParameterSpec(128, iv));
			byte[] results1 = Arrays.copyOfRange(results, cipher.getBlockSize(), results.length);
			byte[] ciphertext = cipher.doFinal(results1);
			decdata = new String(ciphertext).trim();
		} catch (Exception ex) {
			System.out.println("Exception occured :" + ex);
		}
		System.out.println("decrypt(String encData, String merchantCode, String delimiter) method end");
		return decdata;
	}

	static byte[] returnbyte(String path) {
		FileInputStream fileinputstream;
		byte[] abyte = null;
		try {
			fileinputstream = new FileInputStream(path);
			abyte = new byte[fileinputstream.available()];
			fileinputstream.read(abyte);
			fileinputstream.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return abyte;
	}

	public String execute() {
		logger.info("SBIMOPSResponseAction called");
		String responseMsg = request.getParameter("encdata");
		System.out.println("Encrypted Response from SBI :: " + responseMsg);
		String regPrefix = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.REGISTRATION_PREFIX);
		String result = REDIRECT_ACTION_URL;
		Users users = null;
		if (sessionAttributes.get(SESSION_USER) == null) {
			sessionAttributes.put(SESSION_USER, request.getSession().getAttribute(GenericConstants.SESSION_USER));
			users = (Users) sessionAttributes.get(SESSION_USER);
		} else {
			users = (Users) sessionAttributes.get(SESSION_USER);
		}
		logger.info("SBIMOPSResponseAction encData encrypt responseMsg for" + users.getUsername() + ":" + responseMsg);

		String actionUrl = "";
		if (responseMsg != null && !"".equals(responseMsg)) {
			try {
				responseMsg = Decrypt(responseMsg);
				logger.info("SBI decrypted responseMsg ===:" + responseMsg);
				Map<String, String> map = new HashMap<String, String>();
				for (String actualElement : responseMsg.split("\\|")) {
					if (actualElement.split("=").length > 1) {
						map.put(actualElement.split("=")[0], actualElement.split("=")[1]);
					} else {
						map.put(actualElement.split("=")[0], null);
					}
				}
				sbiResponseBean.setTxnReferenceNo(map.get("Refno"));
				sbiResponseBean.setTxnType(map.get("ttype"));
				sbiResponseBean.setTxnAmount(map.get("amount"));
				sbiResponseBean.setErrorDescription(map.get("desc"));
				sbiResponseBean.setTransactionDate(map.get("Date"));
				sbiResponseBean.setSbiRefNo(map.get("sbirefno"));
				sbiResponseBean.setAuthStatus(map.get("status"));
				sbiResponseBean.setCheckSum(map.get("checkSum"));
				paymentBean.setCreatedBy(loggedInUser.getUsername());
				paymentBean.setUserFK(loggedInUser.getUserId());
				loggedInUser.setRemoteIp(request.getRemoteAddr());
				sbiResponseBean.setCustomerId(regPrefix+loggedInUser.getUsername().substring(4));
				commonService.insertCandidateAuditrail(loggedInUser, "Payment Details :",
						"Payment Mode:" + sbiResponseBean.getTxnAmount() + ", User ID:" + loggedInUser.getUsername() + ", Fees:" + sbiResponseBean.getTxnAmount() + " TxnRefNumber:"
								+ sbiResponseBean.getTxnReferenceNo() + ", Transaction Date:" + sbiResponseBean.getTransactionDate() + ", Authentication Status:"
								+ sbiResponseBean.getAuthStatus() + ", Sbi ref no. :" + sbiResponseBean.getSbiRefNo()+ "Transaction Type : "+sbiResponseBean.getTxnType());

			} catch (Exception e) {
				logger.info("Exception SBIMOPSResponseAction  inside responseMsg For User ID:" + loggedInUser.getUsername() + " Error: " + e.getMessage());
				e.printStackTrace();
				return "errorPage";
			}
		}

		try {
			int updatePaymentResponse = 0;
			String message = "";
			// if (!updtFlag && userPk > 0) {
			updatePaymentResponse = paymentService.updateSbiResponseForCandidate(sbiResponseBean, users);
			if (updatePaymentResponse > 0) {
				if (sbiResponseBean != null && sbiResponseBean.getAuthStatus() != null) {
					if ((sbiResponseBean.getAuthStatus().equalsIgnoreCase("Y")) || sbiResponseBean.getAuthStatus().equalsIgnoreCase("Success")) {
						logger.info("SBIMOPSResponseAction Payment Success For User Id :" + users.getUsername());
						commonService.updateCandidateStatus(users, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
						message = GenericConstants.PAYMENTSUCCESS + sbiResponseBean.getTxnReferenceNo() + "";
						actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
						users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
						users.setStatusID(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
						String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED) + "";
						Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
						Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
						EmailSMSUtil.insertEmailNSMSForPaymentApproved(users, staus, emailReq, smsReq);
						ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger().info(loggedInUser.getUsername() + ", " + request.getRemoteAddr()
								+ ", Payment Approved for Online , " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
					} else if (sbiResponseBean.getAuthStatus().equals("N") || "Failure".equalsIgnoreCase(sbiResponseBean.getAuthStatus())) {
						logger.info("SBIMOPSResponseAction Payment Failure For User Id :" + users.getUsername());
						commonService.updateCandidateStatus(users, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
						message = GenericConstants.PAYMENTFAILURE + sbiResponseBean.getTxnReferenceNo() + " " + sbiResponseBean.getErrorDescription();
						actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
						users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
						String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED) + "";
						Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_REJECTED);
						Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_REJECTED);
						EmailSMSUtil.insertEmailNSMSForPaymentFailure(users, staus, emailReq, smsReq);
						ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger().info(loggedInUser.getUsername() + ", " + request.getRemoteAddr()
								+ ",Payment Rejected for online , " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
					} else if (sbiResponseBean.getTxnType().equalsIgnoreCase("B") || sbiResponseBean.getTxnType().equalsIgnoreCase("P") || sbiResponseBean.getTxnType().equalsIgnoreCase("N")) {
						if(sbiResponseBean.getTxnType().equalsIgnoreCase("B")) {
							message = GenericConstants.BRANCH_PAYMENT_PENDING;
						}else if(sbiResponseBean.getTxnType().equalsIgnoreCase("P")) {
							message = GenericConstants.POS_PAYMENT_PENDING;
						}else if(sbiResponseBean.getTxnType().equalsIgnoreCase("N")) {
							message = GenericConstants.NEFT_PAYMENT_WAITING;
						}else {
							message = GenericConstants.PAYMENTWAITING;
						}
						actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
						logger.info("Following payment is pending at the branch For User Id :" + users.getUsername());
						//message = "Please visit the Branch to complete the payment transaction. Transaction No. for Reference" + sbiResponseBean.getTxnReferenceNo();
						actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
					}
					else if(sbiResponseBean.getAuthStatus().equals("P") || "Pending".equalsIgnoreCase(sbiResponseBean.getAuthStatus()))
					{
						message = GenericConstants.PAYMENTWAITING;
						actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
						logger.info("Following payment is pending at the branch For User Id :" + users.getUsername());
					}
				}
			}
			if (updatePaymentResponse > 0) {
				logger.info("SBIMOPSResponseAction SBIMOPS Payment details successfully saved and completed For User Id :" + users.getUsername());
			} else {
				logger.info("SBIMOPSResponseAction SBIMOPS Payment details DB Updation Issues For User Id :" + users.getUsername() + " TxnReferenceNo :"
						+ sbiResponseBean.getTxnReferenceNo());
				message = GenericConstants.PAYMENTFAILURE + sbiResponseBean.getTxnReferenceNo() + " " + sbiResponseBean.getErrorDescription();
				actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
			}

			sessionAttributes.put(SESSION_USER, users);
			request.setAttribute(DESTINATION_URL, actionUrl);
		} catch (Exception e) {
			logger.info("Exception SBIMOPSResponseAction For User ID:" + loggedInUser.getUsername() + " Error: " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	public String statusMsgDisplay() {
		String returnType = "success";
		String statusMsg = request.getParameter("statusMsg");
		if (statusMsg.contains(GenericConstants.PAYMENTSUCCESS)) {
			sbiResponseBean.setEnableButton("true");
		} else {
			sbiResponseBean.setEnableButton("false");
		}
		logger.info("Payment statusMsg :" + statusMsg);
		sbiResponseBean.setResponseMsg(statusMsg);
		return returnType;
	}

	public String statusRejectMsgDisplay() {
		sbiResponseBean.setEnableButton("true");
		sbiResponseBean.setResponseMsg(request.getParameter("statusMsg"));
		return "success";
	}
	
	public String typeSBIEPayCallHome() {
		logger.info("SBIePay Offline Call");
		return "typeSBIEPayCallHome";
	}

	// For SBIePay Double Verification Request Single
	public String connectToSBIEPayDoubleVerification() throws Exception {
		logger.info("connectToSBIEPayDoubleVerification() calling : " + loggedInUser.getUsername());
		String strMsg = "";
		String msg = "";
		String responseUrl = "";
		int testFK = 0;
		Long userPk;
		String existmercntTxnNo = "";
		String doubleVerifyData = "";
		String merchantId = "";
		StringBuilder sbuilder = new StringBuilder();
		try {
			String userID = loggedInUser.getUsername();
			logger.info("connectToSBIEPayDoubleVerification() calling For User ID : [" + userID + "]");
			Users users = new Users();
			// requestparameter = Atrn|MerchantId|MerchantOrderNo|ReturnUrl
			// SBIDOUBLEVERIFY_URL RESP_DOUBLEVERIFY_URL

			if (userID != null && !userID.equals("")) {
				userPk = paymentService.getUserPK(userID);
				if (userPk > 0) {

					users.setUsername(userID);
					users.setUserId(userPk);
					users.setUserFk(userPk);

					// "http://172.25.18.27:8180/TNU/SBIEPayResponseAction_getDoubleVerificationResponse.action";
					responseUrl = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.RESP_DOUBLEVERIFY_URL);
					merchantId = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MERCHANT_ID); // 1000003

					paymentBean = paymentService.getCandidateJobMappingInfo(users.getUserFk());

					existmercntTxnNo = paymentService.getExistingMerchantTxnNumber(users, paymentBean);
					logger.info("connectToSBIEPayDoubleVerification() existmercntTxnNo : [" + existmercntTxnNo + "]");

					if (existmercntTxnNo != null && !existmercntTxnNo.equals("")) {

						// sbuilder.append("");//Atrn --Non mandatory
						sbuilder.append("|");
						sbuilder.append(merchantId); // MerchantId 1000003
						sbuilder.append("|");
						sbuilder.append(existmercntTxnNo);// MerchantOrderNo
						sbuilder.append("|");
						sbuilder.append(responseUrl);// ReturnUrl

						doubleVerifyData = sbuilder.toString();
						logger.info("EncryptTrans doubleVerifyData############# :" + doubleVerifyData);
						String encSBIEPayKey = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.CHECKSUMKEY);// fBc5628ybRQf88f/aqDUOQ==

						msg = AES128Bit.encrypt(doubleVerifyData, encSBIEPayKey);
					} else {
						msg = "TXNNONOTFOUND";
					}
				} else {
					msg = "INVDUSERID";
				}
			} else {
				msg = "";
			}
			logger.info("For Call SBIePay PaymentGeteway");
			logger.info("EncryptTrans msg############# :" + msg);

		} catch (Exception e) {
			msg = "";
			logger.info("Exception connectToSBIEPayDoubleVerification() For User ID " + loggedInUser.getUsername() + " Error :" + e.getMessage());
			logger.fatal(e);
		}

		sessionAttributes.put(SESSION_USER, loggedInUser);
		request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, msg);
		return "writePlainText";
		// return msg;
	}

	// For SBIePay Double Verification Bulk used for 1 specific user
	public String getDoubleVerificationResponseBulkforCheckStatus() throws Exception {
		logger.info("getDoubleVerificationResponseBulkforCheckStatus Called for 1 specific user :" + new Date());
		String encdata = "";
		String generatedChecksum = "";
		String requestParameters = "";
		Users users = (Users) sessionAttributes.get(SESSION_USER);
		String responseMsg = "";
		String userID = "";
		Long userPk = loggedInUser.getUserId();
		List<SBIResponseBean> responseDetailsTP = null;
		String existmercntTxnNo = "";
		String msg = "";
		String result = REDIRECT_ACTION_URL;
		String actionUrl = "";
		String message = "";

		userID = loggedInUser.getUsername();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			commonService.insertCandidateAuditrail(loggedInUser, "check Status Button Click",
					"Check status Button Clicked by" + ", User ID:" + loggedInUser.getUsername() + ". " + "Clicked Date and time :" + formatter.format(date));

			String amount = ConfigurationConstants.getInstance().getPropertyVal("GENERAL_OBC_AMOUNT");
			String currencyType = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.CURRENCY_TYPE);
			String returnUrl = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.PAYMENT_RESPONSE_URL);
			String regPrefix = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.REGISTRATION_PREFIX);
			
			String applicationId = loggedInUser.getUserId() + "";
			
			int opaymentStatusDateCheckUpdate = paymentService.opd_paymentStatusDateCheckUpdate(loggedInUser);
			
			responseDetailsTP = new ArrayList<SBIResponseBean>();
			List<PaymentOnlineBean> pendingPaymentRecords = paymentService.getPendingPaymentRecords(userPk);
			if (pendingPaymentRecords != null && !pendingPaymentRecords.isEmpty()) {
				logger.info("SBIePay Double Verification Bulk used for 1 specific user :" + userID);
				for (PaymentOnlineBean paymentOnlineBean : pendingPaymentRecords) {
					logger.info("Double verification for Single Offline Call For User ID :[" + paymentOnlineBean.getOpd_created_by() + "]  " + "existmercntTxnNo :["
							+ paymentOnlineBean.getOpd_transaction_no() + "]");
					existmercntTxnNo = paymentOnlineBean.getOpd_transaction_no();
					if (existmercntTxnNo != null && !existmercntTxnNo.equals("")) {
						requestParameters = createRequestSBIEPay(existmercntTxnNo, amount, currencyType, returnUrl, Integer.parseInt(applicationId), loggedInUser.getUserFk(),
								loggedInUser.getUsername());
						generatedChecksum = getSHA2Checksum(requestParameters);
						String requestParamCheckSum = requestParameters + "|checkSum=" + generatedChecksum;
						encdata = Encrypt(requestParamCheckSum);
						logger.info("getDoubleVerificationResponseBulkforCheckStatus() For USER ID:" + paymentOnlineBean.getOpd_created_by()
								+ " EncryptTrans doubleVerifyData############# :" + encdata);

						// *** Called SBIePay DoubleVerification API
						responseMsg = sendDoubleVerificationPostCall(encdata);

						if (responseMsg != null && !responseMsg.equals("")) {
							SBIResponseBean sbiResponseBeanTemp = new SBIResponseBean();
							responseMsg = Decrypt(responseMsg);
							logger.info("SBI decrypted responseMsg ===:" + responseMsg);

							// sorting response from bank
							Map<String, String> map = new HashMap<String, String>();
							for (String actualElement : responseMsg.split("\\|")) {
								if (actualElement.split("=").length > 1) {
									map.put(actualElement.split("=")[0], actualElement.split("=")[1]);
								} else {
									map.put(actualElement.split("=")[0], null);
								}
							}
							sbiResponseBeanTemp.setTxnReferenceNo(map.get("Refno"));
							sbiResponseBeanTemp.setTxnType(map.get("ttype"));
							sbiResponseBeanTemp.setTxnAmount(map.get("amount"));
							sbiResponseBeanTemp.setErrorDescription(map.get("desc"));
							sbiResponseBeanTemp.setTransactionDate(map.get("Date"));
							sbiResponseBeanTemp.setSbiRefNo(map.get("sbirefno"));
							sbiResponseBeanTemp.setAuthStatus(map.get("status"));
							sbiResponseBeanTemp.setCheckSum(map.get("checkSum"));
							sbiResponseBeanTemp.setCustomerId(regPrefix+loggedInUser.getUsername().substring(4));
							logger.info("getDoubleVerificationResponseBulkforCheckStatus For User Id :" + paymentOnlineBean.getOpd_created_by() + " Payment merchOrderno ID :["
									+ sbiResponseBeanTemp.getTxnReferenceNo() + "] Payment AuthStatus :[" + sbiResponseBeanTemp.getAuthStatus() + "] StatusDesc : "
									+ sbiResponseBeanTemp.getErrorDescription());

							int insertPaymentDetls = 0;
							if (null != users && userID != null && !userID.equals("NA")) {
								insertPaymentDetls = paymentService.updateSbiResponseForCandidate(sbiResponseBeanTemp, users);
								if (insertPaymentDetls > 0) {
									if (sbiResponseBeanTemp != null && sbiResponseBeanTemp.getAuthStatus() != null) {
										if ((sbiResponseBeanTemp.getAuthStatus().equalsIgnoreCase("Y")) || sbiResponseBeanTemp.getAuthStatus().equalsIgnoreCase("Success")) {
											int updateVal = commonService.updateCandidateStatus(users,
													ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
											logger.info("getDoubleVerificationResponseBulkforCheckStatus Payment Success For User Id :" + users.getUsername()
													+ " updateCandidateStatus:" + updateVal);
											message = GenericConstants.PAYMENTSUCCESS + "" + existmercntTxnNo + "";
											actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
											users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
											String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED) + "";
											Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
											Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
											EmailSMSUtil.insertEmailNSMSForPaymentApproved(users, staus, emailReq, smsReq);
											ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger().info(users.getUsername() + ", " + request.getRemoteAddr()
													+ ", Payment Approved for Offline , " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
										} else if (sbiResponseBeanTemp.getAuthStatus().equalsIgnoreCase("P") || sbiResponseBeanTemp.getAuthStatus().equalsIgnoreCase("Pending")) {
											message = GenericConstants.PAYMENTWAITING;
											actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
											logger.info("getDoubleVerificationResponseBulkforCheckStatus SBIePay is Pending for Response from SBI Bank. For User Id:"
													+ users.getUsername());

										} else if (sbiResponseBeanTemp.getAuthStatus().equalsIgnoreCase("N") || sbiResponseBeanTemp.getAuthStatus().equalsIgnoreCase("Failure")) {
											int updateVal = commonService.updateCandidateStatus(users,
													ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
											logger.info("getDoubleVerificationResponseBulkforCheckStatus Payment Failure For User Id :" + users.getUsername()
													+ " updateCandidateStatus:" + updateVal);
											users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
											String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED) + "";
											Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_REJECTED);
											Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_REJECTED);
											EmailSMSUtil.insertEmailNSMSForPaymentFailure(users, staus, emailReq, smsReq);
											message = GenericConstants.PAYMENTFAILURE + sbiResponseBeanTemp.getTxnReferenceNo() + " " + sbiResponseBeanTemp.getErrorDescription();
											actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
										}
									} else { // Incase of authstatus == null
										message = GenericConstants.PAYMENTFAILURE + sbiResponseBeanTemp.getTxnReferenceNo() + " " + sbiResponseBeanTemp.getErrorDescription();
										actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
										int updateVal = commonService.updateCandidateStatus(users,
												ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
										logger.info("Auth Id found null  For User Id :" + users.getUsername());
										users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));

									}

								}
							} else {
								message = "Sorry. Your Payment could not be processed." + ""
										+ " Payment Transaction Not Found From SBIePay, Please Reinitiate trasaction again after some time !";
								actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
								paymentService.NoTransactionFoundFlagUpdate(loggedInUser);
							}
							commonService.insertCandidateAuditrail(loggedInUser, "Payment Details For Check Status single User Response",
									"Payment Mode: online" + ", User ID:" + loggedInUser.getUsername() + "" + " TxnRefNumber:" + sbiResponseBeanTemp.getTxnReferenceNo()
											+ ", Transaction Date:" + formatter.format(date) + ", Authentication Status:" + sbiResponseBeanTemp.getAuthStatus());

							// Show Records in ForntSide
							if ((sbiResponseBeanTemp.getAuthStatus().equalsIgnoreCase("Y")) || sbiResponseBeanTemp.getAuthStatus().equalsIgnoreCase("Success")) {
								sbiResponseBeanTemp.setTransactionMessage("Payment Successfully Processed");
							} else if ("N".equalsIgnoreCase(sbiResponseBeanTemp.getAuthStatus()) || "Failure".equalsIgnoreCase(sbiResponseBeanTemp.getAuthStatus())) {
								sbiResponseBeanTemp.setTransactionMessage("Payment Failure Processed");
							} else if ("Pending".equals(sbiResponseBeanTemp.getAuthStatus()) || "P".equalsIgnoreCase(sbiResponseBeanTemp.getAuthStatus())) {
								sbiResponseBeanTemp.setTransactionMessage("Payment Pending Processed");
							}
							responseDetailsTP.add(sbiResponseBeanTemp);

						} else {
							message = "Sorry, No response received against your last transaction From SBIePay, Please try again after some time !";
							actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
							logger.info("Received statusMsg Blank From SBIDOUBLEVERIFY_WEBSERVICE_URL ");
						}
					} else {
						msg = "TXNNONOTFOUND";
						message = "Sorry. Your Payment could not be processed." + "" + " Payment Transaction NO Not Found Found In Portal, Please try again after some time !";
						actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
					}

				}
				if (responseDetailsTP != null && !responseDetailsTP.isEmpty()) {
					sbiResponseBean.setResponseDetailsList(responseDetailsTP);
					sbiResponseBean.setResponseOFlag("true");
				} else {
					sbiResponseBean.setErrMsg("SBIePay  Verification  Response Issues ,Please try again !!");
					sbiResponseBean.setShowModuleDetails("true");
				}
			} else {
				commonService.insertCandidateAuditrail(loggedInUser, "Payment Details For Check Status single User", "Payment Mode: online no recorde found for user id");
				message = "Sorry. Your Payment could not be processed." + "" + "Pending Payment Transaction Not Found In Portal, Please try again after some time !";
				actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
				sbiResponseBean.setErrMsg("Pending Payment Transaction Not Found In Portal, Please try again after some time !");
				sbiResponseBean.setShowModuleDetails("true");
			}

			logger.info("getDoubleVerificationResponseBulk Called for 1 user End ON :" + new Date());
		} catch (Exception e) {
			logger.fatal(e, e);
			sbiResponseBean.setErrMsg("SBIePay Double Verification Bulk Issues for 1 user ,Please try again !");
		}
		sessionAttributes.put(SESSION_USER, users);
		request.setAttribute(DESTINATION_URL, actionUrl);
		return result;
	}

	private String createRequestSBIEPay(String transactionId, String paymentAmount, String currencyType, String returnUrl, int applicationId, long userFK, String userId) {
		String requestUrl = "";
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append("Refno=");
		sbuilder.append(transactionId);
		sbuilder.append("|");
		sbuilder.append("amount=");
		sbuilder.append(paymentAmount);
		sbuilder.append("|");
		sbuilder.append("purpose=Registration");
		sbuilder.append("|");
		sbuilder.append("feetype=Online");
		sbuilder.append("|");
		sbuilder.append("agency=NCFE");
		sbuilder.append("|");
		sbuilder.append("UniqueId=");
		sbuilder.append(userId);
		sbuilder.append("|");
		sbuilder.append("name=");
		sbuilder.append(loggedInUser.getCandidateFirstName());
		sbuilder.append("|");
		sbuilder.append("Address=Lucknow");
		sbuilder.append("|");
		sbuilder.append("futureuse=1");
		sbuilder.append("|");
		sbuilder.append("validupto=2024");
		requestUrl = sbuilder.toString();
		return requestUrl;
	}

	private static String getSHA2Checksum(String data) {

		MessageDigest md;
		StringBuffer hexString = new StringBuffer();
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(data.getBytes());
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			for (int i = 0; i < byteData.length; i++) {
				String hex = Integer.toHexString(0xff & byteData[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hexString.toString();
	}

	private String Encrypt(String data) throws FileNotFoundException, IOException, URISyntaxException {
		System.out.println("Encrypt() method begin");
		String encData = null;
		try {
			String fileUrl = null;
			String RSAKey = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.RSA_KEY_PATH);
			Path path = Paths.get(RSAKey + "\\UP_PRPB.key");
			if (Files.exists(path)) {
				fileUrl = path.toString();
			}
			byte[] key = null;
			key = returnbyte(fileUrl);
			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			int blockSize = cipher.getBlockSize();
			System.out.println(blockSize);
			byte[] iv = new byte[cipher.getBlockSize()];
			System.out.println(iv.length);
			byte[] dataBytes = data.getBytes();
			int plaintextLength = dataBytes.length;
			int remainder = plaintextLength % blockSize;
			if (remainder != 0) {
				plaintextLength += blockSize - remainder;
			}
			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
			SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
			SecureRandom randomSecureRandom = SecureRandom.getInstance("SHA1PRNG");
			randomSecureRandom.nextBytes(iv);
			GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
			cipher.init(1, keySpec, parameterSpec);
			byte[] results = cipher.doFinal(plaintext);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			outputStream.write(iv);
			outputStream.write(results);
			byte[] encrypteddata = outputStream.toByteArray();
			encData = Base64.encodeBase64String(encrypteddata);
			encData = encData.replace("\n", "").replace("\r", "");
		} catch (Exception ex) {
			System.out.println("Exception occured :" + ex);
		}
		System.out.println("decrypt(String encData, String merchantCode, String delimiter) method end");
		return encData;
	}

	// For SBIePay Double Verification Response Single
	public String getDoubleVerificationResponse() throws Exception {
		logger.info("getDoubleVerificationResponse Called :");
		Users users = (Users) sessionAttributes.get(SESSION_USER);
		String returnType = "success";
		String statusMsg = "";
		String userID = "";
		// Users users = new Users();
		Long userPk;
		PaymentBean pBean = null;
		String actionUrl = "";
		String message = "";
		String result = REDIRECT_ACTION_URL;
		try {
			statusMsg = request.getParameter("encStatusData");
			logger.info("getDoubleVerificationResponse encStatusData statusMsg :" + statusMsg);

			if (statusMsg != null && !statusMsg.equals("")) {

				String encSBIEPayKey = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.CHECKSUMKEY);// fBc5628ybRQf88f/aqDUOQ==
				statusMsg = AES128Bit.decrypt(statusMsg, encSBIEPayKey);
				logger.info("getDoubleVerificationResponse encStatusData decrypt statusMsg ===:" + statusMsg);
				// TODO 2:
				// Update DoubleVerification statusMsg in DB
				// atrn|status|country|currency|otherDetails|merchOrderno|amount|message|gatewayCode|traceNumber|instructionDate|Paymode|CIN||||||||||
				// 8467814736501|SUCCESS|IN|INR|GS12000019|GSECLMI-20|250|Payment
				// In Clearing|SBIT|000071581|2017-11-04
				// 12:25:12|NB|SBIN8467814736501|1000003|0.00^0.00||||||||||

				StringTokenizer strTok = new StringTokenizer(statusMsg, "|");

				sbiResponseBean.setItemCode(strTok.nextToken()); // atrn
				sbiResponseBean.setAuthStatus(strTok.nextToken());// status
				sbiResponseBean.setAdditionalInfo1(strTok.nextToken());// Country
				sbiResponseBean.setCurrencyName(strTok.nextToken()); // currency
				// sbiResponseBean.setCustomerId(strTok.nextToken());//otherDetails
				String sbiOtherDetails = strTok.nextToken(); // otherDetails
				if (sbiOtherDetails != null && !sbiOtherDetails.equals("NA")) {
					String[] sbiOtherDetailsArr = sbiOtherDetails.split(" "); // For
																				// Ex.
																				// User
																				// ID:
																				// USRB000001
																				// Post:
																				// Sub-Inspector
																				// of
																				// Police
																				// (Technical)
					String sbiUserId = sbiOtherDetailsArr[2];
					sbiResponseBean.setCustomerId(sbiUserId);
				} else {
					sbiResponseBean.setCustomerId(sbiOtherDetails);
				}

				sbiResponseBean.setTxnReferenceNo(strTok.nextToken());// merchOrderno
				sbiResponseBean.setTxnAmount(strTok.nextToken()); // amount
				sbiResponseBean.setErrorDEsc(strTok.nextToken());// message
				sbiResponseBean.setBankId(strTok.nextToken()); // bankCode
				sbiResponseBean.setBankReferenceNo(strTok.nextToken());// traceNumber
				sbiResponseBean.setTxnDate(strTok.nextToken()); // trascationdate
				sbiResponseBean.setTxnType(strTok.nextToken());// paymode
				sbiResponseBean.setAdditionalInfo2(strTok.nextToken());// CIN
																		// Challan
																		// Identification
																		// Number
				sbiResponseBean.setMerchantId(strTok.nextToken()); // MerchantId
																	// 1000003
				sbiResponseBean.setAdditionalInfo3(strTok.nextToken());
				// For Admin DoubleVerification
				sbiResponseBean.setAdditionalInfo4("SBIEPay DoubleVerification Single"); // add
																							// OPD_OFFLINE_STATUS

				if (sbiResponseBean.getAuthStatus() != null && sbiResponseBean.getAuthStatus().equals("SUCCESS")) {
					sbiResponseBean.setAuthStatus("Success");
				} else if (sbiResponseBean.getAuthStatus() != null && sbiResponseBean.getAuthStatus().equals("FAIL")) {
					sbiResponseBean.setAuthStatus("Failure");
				} else if (sbiResponseBean.getAuthStatus() != null && sbiResponseBean.getAuthStatus().equals("FAIL")) {

				} else if (sbiResponseBean.getAuthStatus() != null && sbiResponseBean.getAuthStatus().equals("PENDING")) {
					sbiResponseBean.setAuthStatus("Pending");
				} else {
					sbiResponseBean.setAuthStatus("Failure");
				}

				String paymentID = sbiResponseBean.getTxnReferenceNo();
				logger.info("getDoubleVerificationResponse For User Id :" + sbiResponseBean.getCustomerId() + " Payment merchOrderno ID :[" + paymentID + "] Payment AuthStatus :["
						+ sbiResponseBean.getAuthStatus() + "] StatusDesc : " + sbiResponseBean.getErrorDEsc());

				userID = sbiResponseBean.getCustomerId();
				userPk = paymentService.getUserPK(userID);
				if (userPk > 0) {
					users.setUsername(userID);
					users.setUserId(userPk);
					users.setUserFk(userPk);
					logger.info("getDoubleVerificationResponse User Id Found userPk :[" + userPk + "]");
				} else {
					logger.info("getDoubleVerificationResponse User Id Not Found userPk :[" + userPk + "]");
				}
				// Get OPD_VALIDATED_STATUS from OES_PAYMENT_DETAILS
				boolean updtFlag = false;
				pBean = paymentService.getPaymentDetailsForEnrollment(users);
				if (pBean != null) {
					String validateStatus = pBean.getValidatedStatus();
					String opdStatus = pBean.getOpdStatus();

					if (validateStatus != null && validateStatus.equals("A") && opdStatus != null && opdStatus.equals("A")) {
						updtFlag = true;
					}
				}

				int insertPaymentDetls = 0;
				// TODO Need To Change For SBIePAY Update OES_PAYMENT_DETAILS
				// Table after get PaymentResponse
				if (!updtFlag && userPk > 0) {

					insertPaymentDetls = paymentService.updateSbiResponseForCandidate(sbiResponseBean, users);

					if (insertPaymentDetls > 0) {
						// Added By Bhushan on 29-11-2017
						if (sbiResponseBean != null && sbiResponseBean.getAuthStatus() != null) {
							if (sbiResponseBean.getAuthStatus().equals("Success")) {

								int updateVal = commonService.updateCandidateStatus(users, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
								logger.info("getDoubleVerificationResponse Payment Success For User Id :" + users.getUsername() + " updateCandidateStatus:" + updateVal);

								// added for redirection
								message = GenericConstants.PAYMENTSUCCESS + sbiResponseBean.getAdditionalInfo2() + "";
								actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
								// added for redirection

								users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));

								String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED) + "";
								Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
								Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
								// For Payment Approved Email/SMS
								// EmailSMSUtil.insertEmailNSMSForActivitynTestForPayment(ConfigurationConstants.getInstance().getActivtyKeyForName(GenericConstants.PAYMENT_APPROVED),Integer.parseInt(users.getDisciplineID()),
								// users.getUsername(),
								// users.getUsername(),staus,users,emailReq,smsReq);

								EmailSMSUtil.insertEmailNSMSForPaymentApproved(users, staus, emailReq, smsReq);

								ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger().info(loggedInUser.getUsername() + ", " + request.getRemoteAddr()
										+ ", Payment Approved for Offline , " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
							} else if (sbiResponseBean.getAuthStatus().equals("Pending")) {
								// added for redirection
								message = GenericConstants.PAYMENTWAITING;
								actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
								// added for redirection

								logger.info("getDoubleVerificationResponse SBIePay is Pending for Response from SBI Bank. For User Id:" + users.getUsername());

							} else if (sbiResponseBean.getAuthStatus().equals("Failure")) {

								int updateVal = commonService.updateCandidateStatus(users, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
								logger.info("getDoubleVerificationResponse Payment Failure For User Id :" + users.getUsername() + " updateCandidateStatus:" + updateVal);

								users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));

								String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED) + "";
								Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_REJECTED);
								Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_REJECTED);
								// EmailSMSUtil.insertEmailNSMSForActivitynTestForPayment(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED),Integer.parseInt(users.getDisciplineID()),
								// users.getUsername(),
								// users.getUsername(),staus,users,emailReq,smsReq);

								EmailSMSUtil.insertEmailNSMSForPaymentFailure(users, staus, emailReq, smsReq);

								ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger().info(loggedInUser.getUsername() + ", " + request.getRemoteAddr()
										+ ",Payment Rejected for offline , " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));

								String errorMsg = sbiResponseBean.getErrorDEsc();
								if (errorMsg != null && errorMsg.equalsIgnoreCase("N")) {
									errorMsg = "";
								} else if (errorMsg != null && !errorMsg.equalsIgnoreCase("N")) {
									errorMsg = "," + errorMsg;
								} else {
									errorMsg = "";
								}
								message = GenericConstants.PAYMENTFAILURE + sbiResponseBean.getTxnReferenceNo() + " " + errorMsg;
								actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;

							}
						}
						logger.info("getDoubleVerificationResponse SBIePay Payment details successfully saved and completed For User Id :" + users.getUsername());

					}

				}

				List<SBIResponseBean> responseDetailsTP = new ArrayList<SBIResponseBean>();

				if ("Success".equals(sbiResponseBean.getAuthStatus())) {
					sbiResponseBean.setTransactionMessage("Payment Successfully Processed");
				} else if ("Failure".equals(sbiResponseBean.getAuthStatus())) {
					sbiResponseBean.setTransactionMessage("Payment Failure Processed");
				} else if ("Pending".equals(sbiResponseBean.getAuthStatus())) {
					sbiResponseBean.setTransactionMessage("Payment Pending Processed");
				}

				responseDetailsTP.add(sbiResponseBean);
				if (responseDetailsTP != null && !responseDetailsTP.isEmpty()) {
					sbiResponseBean.setResponseDetailsList(responseDetailsTP);
					sbiResponseBean.setResponseOFlag("true");
				}

			} // if statusMsg
			else {

				message = GenericConstants.PAYMENTFAILURE + "" + " Payment Transaction Not Found From SBIePay, Please try again after some time !";
				actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
				sbiResponseBean.setErrMsg("Payment Transaction Not Found From SBIePay, Please try again after some time !");
				sbiResponseBean.setShowModuleDetails("true");
			}
		} catch (Exception e) {
			sbiResponseBean.setErrMsg("SBIePay Double Verification Single Issues ,Please try again !!");
			logger.info("Exception getDoubleVerificationResponse For User ID:" + loggedInUser.getUsername() + " Error: " + e.getMessage());
			logger.fatal(e, e);
		}
		// sessionAttributes.put(SESSION_USER, loggedInUser);
		// request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, statusMsg);
		// return "writePlainText";
		// return returnType;

		sessionAttributes.put(SESSION_USER, users);
		request.setAttribute(DESTINATION_URL, actionUrl);

		return result;
	}

	// For SBIePay Double Verification Bulk
	public String getDoubleVerificationResponseBulk() throws Exception {
		logger.info("getDoubleVerificationResponseBulk Called Start ON :" + new Date());
		String returnType = "success";
		String statusMsg = "";
		String userID = "";
		String createdUserId = "";
		Users users = new Users();
		Long userPk;
		StringBuilder sbuilder = null;
		List<SBIResponseBean> responseDetailsTP = null;
		String existmercntTxnNo = "";
		String requestUrl = "";
		String msg = "";
		String message = "";
		String actionUrl = "";
		try {
			if (sbiResponseBean == null) {
				sbiResponseBean = new SBIResponseBean();
			}
			List<PaymentOnlineBean> pendingPaymentRecords = paymentService.getPendingPaymentRecords();
			responseDetailsTP = new ArrayList<SBIResponseBean>();

			if (pendingPaymentRecords != null && !pendingPaymentRecords.isEmpty()) {
				logger.info("Bulk Pending Payment Records Size :" + pendingPaymentRecords.size());
				for (PaymentOnlineBean paymentOnlineBean : pendingPaymentRecords) {
					logger.info("Double verification for Single Offline Call For User ID :[" + paymentOnlineBean.getOpd_created_by() + "]  " + "existmercntTxnNo :["
							+ paymentOnlineBean.getOpd_transaction_no() + "]");
					existmercntTxnNo = paymentOnlineBean.getOpd_transaction_no();
					String amount = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.GENERAL_OBC_AMOUNT);
					String currencyType = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.CURRENCY_TYPE);
					String returnUrl = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.PAYMENT_RESPONSE_URL);
					String applicationId = loggedInUser.getUserId() + "";
					if (existmercntTxnNo != null && !existmercntTxnNo.equals("")) {
						requestUrl = createRequestSBIEPay(existmercntTxnNo, amount, currencyType, returnUrl, Integer.parseInt(applicationId), loggedInUser.getUserFk(),
								loggedInUser.getUsername());
						String generatedChecksum = getSHA2Checksum(requestUrl);
						String requestParamCheckSum = requestUrl + "|checkSum=" + generatedChecksum;
						String encdata = Encrypt(requestParamCheckSum);
						logger.info("getDoubleVerificationResponseBulkforCheckStatus() For USER ID:" + paymentOnlineBean.getOpd_created_by()
								+ " EncryptTrans doubleVerifyData############# :" + encdata);

						// *** Called SBIePay DoubleVerification API
						String responseMsg = sendDoubleVerificationPostCall(encdata);

						if (responseMsg != null && !responseMsg.equals("")) {
							SBIResponseBean sbiResponseBeanTemp = new SBIResponseBean();
							responseMsg = Decrypt(responseMsg);
							logger.info("SBI decrypted responseMsg ===:" + responseMsg);

							// sorting response from bank
							Map<String, String> map = new HashMap<String, String>();
							for (String actualElement : responseMsg.split("\\|")) {
								if (actualElement.split("=").length > 1) {
									map.put(actualElement.split("=")[0], actualElement.split("=")[1]);
								} else {
									map.put(actualElement.split("=")[0], null);
								}
							}
							sbiResponseBeanTemp.setTxnReferenceNo(map.get("Refno"));
							sbiResponseBeanTemp.setTxnType(map.get("ttype"));
							sbiResponseBeanTemp.setTxnAmount(map.get("amount"));
							sbiResponseBeanTemp.setErrorDescription(map.get("desc"));
							sbiResponseBeanTemp.setTransactionDate(map.get("Date"));
							sbiResponseBeanTemp.setSbiRefNo(map.get("sbirefno"));
							sbiResponseBeanTemp.setAuthStatus(map.get("status"));
							sbiResponseBeanTemp.setCheckSum(map.get("checkSum"));

							logger.info("getDoubleVerificationResponseBulkforCheckStatus For User Id :" + paymentOnlineBean.getOpd_created_by() + " Payment merchOrderno ID :["
									+ sbiResponseBeanTemp.getTxnReferenceNo() + "] Payment AuthStatus :[" + sbiResponseBeanTemp.getAuthStatus() + "] StatusDesc : "
									+ sbiResponseBeanTemp.getErrorDescription());

							int insertPaymentDetls = 0;
							if (null != users && userID != null && !userID.equals("NA")) {
								insertPaymentDetls = paymentService.updateSbiResponseForCandidate(sbiResponseBeanTemp, users);
								if (insertPaymentDetls > 0) {
									if (sbiResponseBeanTemp != null && sbiResponseBeanTemp.getAuthStatus() != null) {
										if ((sbiResponseBeanTemp.getAuthStatus().equalsIgnoreCase("Y")) || sbiResponseBeanTemp.getAuthStatus().equalsIgnoreCase("Success")) {
											int updateVal = commonService.updateCandidateStatus(users,
													ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
											logger.info("getDoubleVerificationResponseBulkforCheckStatus Payment Success For User Id :" + users.getUsername()
													+ " updateCandidateStatus:" + updateVal);
											message = GenericConstants.PAYMENTSUCCESS + "" + existmercntTxnNo + "";
											actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
											users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
											String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED) + "";
											Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
											Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
											EmailSMSUtil.insertEmailNSMSForPaymentApproved(users, staus, emailReq, smsReq);
											ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger().info(users.getUsername() + ", " + request.getRemoteAddr()
													+ ", Payment Approved for Offline , " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));

										} else if (sbiResponseBeanTemp.getAuthStatus().equalsIgnoreCase("P") || sbiResponseBeanTemp.getAuthStatus().equalsIgnoreCase("Pending")) {
											message = GenericConstants.PAYMENTWAITING;
											actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
											logger.info("getDoubleVerificationResponseBulkforCheckStatus SBIePay is Pending for Response from SBI Bank. For User Id:"
													+ users.getUsername());

										} else if (sbiResponseBeanTemp.getAuthStatus().equalsIgnoreCase("N") || sbiResponseBeanTemp.getAuthStatus().equalsIgnoreCase("Failure")) {
											int updateVal = commonService.updateCandidateStatus(users,
													ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
											logger.info("getDoubleVerificationResponseBulkforCheckStatus Payment Failure For User Id :" + users.getUsername()
													+ " updateCandidateStatus:" + updateVal);
											users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
											String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED) + "";
											Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_REJECTED);
											Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_REJECTED);
											EmailSMSUtil.insertEmailNSMSForPaymentFailure(users, staus, emailReq, smsReq);
											message = GenericConstants.PAYMENTFAILURE + sbiResponseBeanTemp.getTxnReferenceNo() + " " + sbiResponseBeanTemp.getErrorDescription();
											actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
										}
									} else { // Incase of authstatus == null
										int updateVal = commonService.updateCandidateStatus(users,
												ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
										logger.info("Auth Id found null  For User Id :" + users.getUsername());
										users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));

									}

								}
							} else {
								paymentService.NoTransactionFoundFlagUpdate(loggedInUser);
							}
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
							Date date = new Date();
							commonService.insertCandidateAuditrail(loggedInUser, "Payment Details For Check Status single User Response",
									"Payment Mode: online" + ", User ID:" + loggedInUser.getUsername() + "" + " TxnRefNumber:" + sbiResponseBeanTemp.getTxnReferenceNo()
											+ ", Transaction Date:" + formatter.format(date) + ", Authentication Status:" + sbiResponseBeanTemp.getAuthStatus());

							responseDetailsTP.add(sbiResponseBeanTemp);
						} else {
							message = "Sorry, No response received against your last transaction From SBIePay, Please try again after some time !";
							actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
							logger.info("Received statusMsg Blank From SBIDOUBLEVERIFY_WEBSERVICE_URL ");
						}
					} else {
						msg = "TXNNONOTFOUND";
						message = "Sorry. Your Payment could not be processed." + "" + " Payment Transaction NO Not Found Found In Portal, Please try again after some time !";
						actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
					}
				} // for loop Close

				if (responseDetailsTP != null && !responseDetailsTP.isEmpty()) {
					sbiResponseBean.setResponseDetailsList(responseDetailsTP);
					sbiResponseBean.setResponseOFlag("true");
				} else {
					sbiResponseBean.setErrMsg("SBIePay Double Verification Bulk Response Issues ,Please try again !!");
					sbiResponseBean.setShowModuleDetails("false");
				}
			} else {
				sbiResponseBean.setErrMsg("Pending Payment Transaction Not Found In Portal, Please try again after some time !");
				sbiResponseBean.setShowModuleDetails("false");
			}

			logger.info("getDoubleVerificationResponseBulk Called End ON :" + new Date());
		} catch (Exception e) {
			logger.fatal(e, e);
			sbiResponseBean.setErrMsg("SBIePay Double Verification Bulk Issues ,Please try again !");
		}
		return "typeSBIEPayCallHome";
	}

	public String sendDoubleVerificationPostCall(String param) throws Exception {
		logger.info("sendDoubleVerificationPostCall() ::::::: on " + new Date());
		String merchantId = "";
		String gatewayUrl = "";
		String responseStr = "";

		URL url = null;
		HttpURLConnection httpConn = null;
		HashMap<String, String> params = new HashMap<String, String>();
		StringBuffer requestParams = new StringBuffer();
		InputStream stream = null;
		InputStreamReader isr = null;
		BufferedReader reader = null;

		try {
			logger.info("sendDoubleVerificationPostCall() ::::::: messageData :" + param);

			gatewayUrl = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SBIDOUBLEVERIFY_WEBSERVICE_URL);
			merchantId = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MERCHANT_ID);

			params.put("encdata", param);
			params.put("merchant_code", merchantId);

			url = new URL(gatewayUrl);

			httpConn = (HttpURLConnection) url.openConnection();

			httpConn.setDoInput(true); // true indicates the server returns

			if (params != null && params.size() > 0) {
				httpConn.setDoOutput(true); // true indicates POST request
				// creates the params string, encode them using URLEncoder
				Iterator<String> paramIterator = params.keySet().iterator();

				while (paramIterator.hasNext()) {

					String key = paramIterator.next();
					String value = params.get(key);

					requestParams.append(URLEncoder.encode(key, "UTF-8"));
					requestParams.append("=").append(URLEncoder.encode(value, "UTF-8"));
					if (key == "merchant_code")
						requestParams.append("&");
				}

				// sends POST data
				logger.info("Encode requestParams:" + requestParams);

				OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
				writer.write(requestParams.toString());
				writer.flush();

				// Response Code
				int responseMsg = httpConn.getResponseCode();
				logger.info("responseMsgCode :: " + responseMsg);

				// Reading Response
				stream = httpConn.getInputStream();
				isr = new InputStreamReader(stream);
				reader = new BufferedReader(isr);

				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line).append("\n");
				}

				stream.close();

				responseStr = sb.toString();
				responseStr = responseStr.trim();
				logger.info("Received SBIEPAY WEBSERVICE responseStr ::: " + responseStr);
			}

		} // try Closed
		catch (MalformedURLException e) {
			logger.info("MalformedURLException sendDoubleVerificationPostCall() Error: " + e.getMessage());
			responseStr = "";

		} catch (ProtocolException e) {
			logger.info("ProtocolException sendDoubleVerificationPostCall() Error: " + e.getMessage());
			responseStr = "";

		} catch (IOException e) {
			logger.info("IOException sendDoubleVerificationPostCall() Error: " + e.getMessage());
			logger.fatal(e, e);
			responseStr = "";

		} catch (Exception e) {
			logger.info("Exception sendDoubleVerificationPostCall() Error: " + e.getMessage());
			responseStr = "";

		} finally {
			try {
				if (reader != null) {
					reader.close();
					reader = null;
				}
				if (isr != null) {
					isr.close();
					isr = null;
				}
				if (stream != null) {
					stream.close();
					stream = null;
				}
			} catch (Exception e) {
				logger.info("Exception sendDoubleVerificationPostCall() Error: " + e.getMessage());
			}
		}
		return responseStr;
	}

	// For SBIEPAY Push Responce Call from Server to Server
	public String getSBIPushResponse() throws Exception {
		logger.info("getSBIPushResponse Called :::: Satrt on " + new Date());
		String returnType = "success";
		String statusMsg = "";
		String userID = "";
		String existmercntTxnNo = "";
		Users users = new Users();
		Long userPk;
		PaymentBean pBean = null;
		//// Test *****
		statusMsg = request.getParameter("pushRespData"); // From SBIePay
															// PushResponse
															// Service
		logger.info("getSBIPushResponse pushRespData encrypt statusMsg ::::::" + statusMsg);

		try {
			// statusMsg = request.getParameter("encStatusData");
			// logger.info("getSBIPushResponse pushRespData statusMsg
			// :"+statusMsg);

			if (statusMsg != null && !statusMsg.equals("")) {

				String encSBIEPayKey = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.CHECKSUMKEY);// fBc5628ybRQf88f/aqDUOQ==
				statusMsg = AES128Bit.decrypt(statusMsg, encSBIEPayKey);
				logger.info("getSBIPushResponse pushRespData decrypt statusMsg ===:" + statusMsg);
				// TODO 2:
				// Update SBIPushResponse statusMsg in DB
				// orderReqId|atrn|transStatus|amount|currency|paymode|otherDetails|message|bankCode|bankRefNumber|trascationdate|Country|CIN||||||||||

				// GSECLMI-32|NA|FAIL|500|INR|NB|GS12000032|User Cancel
				// Transaction|NA|NA|2017-11-09
				// 14:39:23|IN|00|1000003|0.00^0.00|||||||||
				// GSECLMI-16|1354843502901|SUCCESS|500|INR|NB|GS12000022|Y|SBIT|000071581|2017-11-09
				// 15:31:00|IN|SBIN1354843502901|1000003|0.00^0.00|||||||||

				SBIResponseBean sbiResponseBeanTemp = new SBIResponseBean();
				StringTokenizer strTok = new StringTokenizer(statusMsg, "|");

				sbiResponseBeanTemp.setTxnReferenceNo(strTok.nextToken());// merchOrderno
				sbiResponseBeanTemp.setItemCode(strTok.nextToken()); // atrn
				sbiResponseBeanTemp.setAuthStatus(strTok.nextToken());// status
				sbiResponseBeanTemp.setTxnAmount(strTok.nextToken()); // amount
				sbiResponseBeanTemp.setCurrencyName(strTok.nextToken()); // currency
				sbiResponseBeanTemp.setTxnType(strTok.nextToken());// paymode
				sbiResponseBeanTemp.setCustomerId(strTok.nextToken());// otherDetails

				sbiResponseBeanTemp.setErrorDEsc(strTok.nextToken());// message
				sbiResponseBeanTemp.setBankId(strTok.nextToken()); // bankCode
				sbiResponseBeanTemp.setBankReferenceNo(strTok.nextToken());// bankRefNumber
				sbiResponseBeanTemp.setTxnDate(strTok.nextToken()); // trascationdate
				sbiResponseBeanTemp.setAdditionalInfo1(strTok.nextToken());// Country
																			// IN

				sbiResponseBeanTemp.setAdditionalInfo2(strTok.nextToken());// CIN
																			// Challan
																			// Identification
																			// Number
				sbiResponseBeanTemp.setMerchantId(strTok.nextToken()); // MerchantId
																		// 1000003
				sbiResponseBeanTemp.setAdditionalInfo3(strTok.nextToken());
				// For Admin DoubleVerification
				sbiResponseBeanTemp.setAdditionalInfo4("SBIEPay Push Response"); // add
																					// OPD_OFFLINE_STATUS

				if (sbiResponseBeanTemp.getAuthStatus() != null && sbiResponseBeanTemp.getAuthStatus().equals("SUCCESS")) {
					sbiResponseBeanTemp.setAuthStatus("Success");
				} else if (sbiResponseBeanTemp.getAuthStatus() != null && sbiResponseBeanTemp.getAuthStatus().equals("FAIL")) {
					sbiResponseBeanTemp.setAuthStatus("Failure");
				} else if (sbiResponseBeanTemp.getAuthStatus() != null && sbiResponseBeanTemp.getAuthStatus().equals("FAIL")) {

				} else if (sbiResponseBeanTemp.getAuthStatus() != null && sbiResponseBeanTemp.getAuthStatus().equals("PENDING")) {
					sbiResponseBeanTemp.setAuthStatus("Pending");
				} else {
					sbiResponseBeanTemp.setAuthStatus("Failure");
				}

				String paymentID = sbiResponseBeanTemp.getTxnReferenceNo();
				logger.info("getSBIPushResponse For User Id :" + sbiResponseBeanTemp.getCustomerId() + " Payment merchOrderno ID :[" + paymentID + "] " + "Payment AuthStatus :["
						+ sbiResponseBeanTemp.getAuthStatus() + "] StatusDesc : " + sbiResponseBeanTemp.getErrorDEsc());

				userID = sbiResponseBeanTemp.getCustomerId();
				userPk = paymentService.getUserPK(userID);
				if (userPk > 0) {
					users.setUsername(userID);
					users.setUserId(userPk);
					users.setUserFk(userPk);
				} else {
					logger.info("getSBIPushResponse User Id Not Found ExitPushLoop===:userPk :" + userPk);
					return null;
				}
				paymentBean = paymentService.getCandidateJobMappingInfo(users.getUserFk());

				existmercntTxnNo = paymentService.getExistingMerchantTxnNumber(users, paymentBean);
				logger.info("getSBIPushResponse Find existmercntTxnNo :[" + existmercntTxnNo + "]");

				if (existmercntTxnNo != null && paymentID != null && existmercntTxnNo.equals(paymentID)) {

					// logger.info("Used existing existmercntTxnNo
					// :["+existmercntTxnNo +"]"); // Then Go to Futher Process
				} else if (existmercntTxnNo != null && paymentID != null && !existmercntTxnNo.equals(paymentID)) {

					logger.info("getSBIPushResponse existmercntTxnNo and Push OrderID are not equals ExitPushLoop===:User ID :" + userID);
					return null;
				} else if (existmercntTxnNo == null) {

					logger.info("getSBIPushResponse existmercntTxnNo Not Found OR Payment Already Completed Found ExitPushLoop===:User ID :" + userID);
					return null;
				}

				// Get OPD_VALIDATED_STATUS from OES_PAYMENT_DETAILS
				boolean updtFlag = false;
				pBean = paymentService.getPaymentDetailsForEnrollment(users);
				if (pBean != null) {
					String validateStatus = pBean.getValidatedStatus();
					String opdStatus = pBean.getOpdStatus();
					if (validateStatus != null && validateStatus.equals("A") && opdStatus != null && opdStatus.equals("A")) {
						updtFlag = true;
						logger.info("getSBIPushResponse Payment Already Successfully Completed For User Id :" + users.getUsername() + " validateStatus:[" + validateStatus + "]");
					}

				}

				int insertPaymentDetls = 0;
				// TODO Need To Change For SBIePAY UPDATE OES_PAYMENT_DETAILS
				// Table after get PaymentResponse
				if (!updtFlag && userPk > 0) {
					insertPaymentDetls = paymentService.updateSbiResponseForCandidate(sbiResponseBeanTemp, users);

					if (insertPaymentDetls > 0) {
						// Added By Bhushan on 29-11-2017
						if (sbiResponseBeanTemp != null && sbiResponseBeanTemp.getAuthStatus() != null) {
							if (sbiResponseBeanTemp.getAuthStatus().equals("Success")) {

								int updateVal = commonService.updateCandidateStatus(users, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
								logger.info("getSBIPushResponse Payment Success For User Id :" + users.getUsername() + " updateCandidateStatus:" + updateVal);

								users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));

							} else if (sbiResponseBeanTemp.getAuthStatus().equals("Pending")) {
								logger.info("getSBIPushResponse SBIePay is Pending for Response from SBI Bank. For User Id:" + users.getUsername());

							} else if (sbiResponseBeanTemp.getAuthStatus().equals("Failure")) {

								int updateVal = commonService.updateCandidateStatus(users, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
								logger.info("getSBIPushResponse Payment Failure For User Id :" + users.getUsername() + " updateCandidateStatus:" + updateVal);

								users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));

							}

						}
						logger.info("getSBIPushResponse SBIePay Payment details successfully saved and completed For User Id :" + users.getUsername());

					}

				}

			} // if statusMsg
			else {
				logger.info("getSBIPushResponse pushRespData statusMsg Blank ::" + statusMsg);
			}

			logger.info("getSBIPushResponse Called :::: End on " + new Date());
		} catch (Exception e) {
			logger.info("Exception getSBIPushResponse For User ID:" + userID + " Error: " + e.getMessage());
			e.printStackTrace();
		}
		// sessionAttributes.put(SESSION_USER, loggedInUser);
		// request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, statusMsg);
		// return "writePlainText";
		// return returnType;
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
