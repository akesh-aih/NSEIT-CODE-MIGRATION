package com.nseit.payment.action;

import static com.nseit.generic.util.GenericConstants.DESTINATION_URL;
import static com.nseit.generic.util.GenericConstants.REDIRECT_ACTION_URL;
import static com.nseit.generic.util.GenericConstants.SESSION_USER;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.struts2.dispatcher.HttpParameters;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nseit.generic.action.BaseAction;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.CommonService;
import com.nseit.generic.service.LoginService;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.aware.UserAware;
import com.nseit.generic.util.emailsms.EmailSMSUtil;
import com.nseit.payment.models.PaymentOnlineBean;
import com.nseit.payment.models.SBIResponseBean;
import com.nseit.payment.models.SafexResponseBean;
import com.nseit.payment.service.PaymentService;
import com.opensymphony.xwork2.ModelDriven;
import com.paygate.ag.common.utils.PayGateCryptoUtils;

public class SafexResponseAction extends BaseAction implements ModelDriven<SafexResponseBean>, UserAware{

	private static final long serialVersionUID = 4271059779274652365L;

	private Logger logger = LoggerHome.getLogger(getClass());
	
	private SafexResponseBean safexResponseBean;
	private CommonService commonService;
	private PaymentService paymentService;
	private LoginService loginService;
	private PaymentOnlineBean paymentBean = new PaymentOnlineBean();
	
	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}
	
	public SafexResponseAction() {
		displayMenus();
	}

	@Override
	public void resetModel() {
		safexResponseBean = new SafexResponseBean(); 
		
	}

	@Override
	public void setLoggedInUser(Users users) {
		this.loggedInUser = users;
		}

	@Override
	public SafexResponseBean getModel() {
		return safexResponseBean;
	}
	
	public String execute() throws Exception{
		
		Users users = null;
		String result = REDIRECT_ACTION_URL;
		if (sessionAttributes.get(SESSION_USER) == null) {
			sessionAttributes.put(SESSION_USER, request.getSession().getAttribute(GenericConstants.SESSION_USER));
			users = (Users) sessionAttributes.get(SESSION_USER);
		} else {
			users = (Users) sessionAttributes.get(SESSION_USER);
		}
		logger.info("SafexResponseAction called");
		String merchant_key = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MERCHANT_KEY);
		
		String responseMsg = request.getParameter("txn_response");
		String pg_details = request.getParameter("pg_details");
		String fraud_details = request.getParameter("fraud_details");
		String other_details = request.getParameter("other_details");//response before decryption
		commonService.insertCandidateAuditrail(loggedInUser, "Payment Page - Payment Response  Before Decryption",
				"Candidate with User Id " + loggedInUser.getUsername() + " :"
						+ " "+" Response Message : " +responseMsg+" pg Details : "+pg_details+" Fraud details : "+fraud_details
						+" Other details : "+other_details);
		
		
		String txn_response = PayGateCryptoUtils.decrypt(responseMsg, merchant_key);
		String decryptedPg_details = PayGateCryptoUtils.decrypt(pg_details, merchant_key);
		logger.info("Pg details: "+decryptedPg_details);
		String decryptedFraud_details = PayGateCryptoUtils.decrypt(fraud_details, merchant_key);
		logger.info("Fraud details: "+decryptedFraud_details);
		String decryptedOther_details = PayGateCryptoUtils.decrypt(other_details, merchant_key);
		logger.info("other details: "+decryptedOther_details);// response after decryption
		
		commonService.insertCandidateAuditrail(loggedInUser, "Payment Page - Payment Response  After Decryption",
				"Candidate with User Id " + loggedInUser.getUsername() + " : "
						+ " "+" Response Message : " +txn_response+" pg Details : "+decryptedPg_details+" Fraud details : "+decryptedFraud_details
						+" Other details : "+decryptedOther_details);
		
		logger.info("Decrypted Response: "+txn_response);
		
		String[]  txn_response_hash = txn_response.split("~"); 
		String txn_res_hash = txn_response_hash[1];
		String txn_response_actual = txn_response_hash[0]+""+txn_response_hash[2];
		String[] txn_response_List = txn_response_actual.split("\\|", -1);  
		String hash = ((txn_response_List[10] != null) ? txn_response_List[10] : "")+"~"+((txn_response_List[1] != null)
				? txn_response_List[1] : "")+"~"+((txn_response_List[2] != null) ? txn_response_List[2] : "")+"~"
				+((txn_response_List[3] != null) ? txn_response_List[3] : "")+"~"+((txn_response_List[4] != null)
						? txn_response_List[4] : "")+"~"+((txn_response_List[5] != null) ? txn_response_List[5] : "")
				+"~"+((txn_response_List[8] != null) ? txn_response_List[8] : "");
		
		String checksum =   org.apache.commons.codec.digest.DigestUtils.sha256Hex(hash);
		String create_hash = PayGateCryptoUtils.encrypt(checksum, merchant_key);
		String protocol  =  "fake";
		if (txn_res_hash.equals(create_hash)) {
			  protocol  =  "Genuine"; 
		} 
		
		
		
		
		String[] pg_details_List=decryptedPg_details.split("\\|", -1);
		String[] fraud_details_List=decryptedFraud_details.split("\\|", -1);
		String[] other_details_List=decryptedOther_details.split("\\|", -1); 
		
		safexResponseBean.setAg_id(txn_response_List[0]);
		safexResponseBean.setMe_id(txn_response_List[1]);
		safexResponseBean.setOrder_no(txn_response_List[2]);
		safexResponseBean.setAmount(txn_response_List[3]);
		safexResponseBean.setCountry(txn_response_List[4]);
		safexResponseBean.setCurrency(txn_response_List[5]);
		safexResponseBean.setTxn_date(txn_response_List[6]);
		safexResponseBean.setTxn_time(txn_response_List[7]);
		safexResponseBean.setAg_ref(txn_response_List[8]);
		safexResponseBean.setPg_ref(txn_response_List[9]);
		
		if(txn_response_List[10] != null) {
			String status = txn_response_List[10].split("~")[0];
			safexResponseBean.setStatus(status);
		}
		safexResponseBean.setRes_code(txn_response_List[11]);
		safexResponseBean.setRes_message(txn_response_List[12]);

		safexResponseBean.setPg_id(pg_details_List[0]);
		safexResponseBean.setPg_name(pg_details_List[1]);
		safexResponseBean.setPaymode(pg_details_List[2]);
		safexResponseBean.setEmi_months(pg_details_List[3]);

		safexResponseBean.setFraud_action(fraud_details_List[0]);
		safexResponseBean.setFraud_message(fraud_details_List[1]);
	
		safexResponseBean.setUdf_1(other_details_List[0]);
		safexResponseBean.setUdf_2(other_details_List[1]);
		safexResponseBean.setUdf_3(other_details_List[2]);
		safexResponseBean.setUdf_4(other_details_List[3]); 
		
		try {
			String actionUrl = "";
			int updatePaymentResponse = 0;
			String message = "";
			
			updatePaymentResponse = paymentService.updateSafexResponseForCandidate(safexResponseBean, users);
			
			if (updatePaymentResponse > 0) {
				if (safexResponseBean != null && safexResponseBean.getStatus() != null) {
					if ("Successful".equalsIgnoreCase(safexResponseBean.getStatus())) {
						logger.info("SafexResponseAction Payment Success For User Id :" + users.getUsername());
						commonService.updateCandidateStatus(users, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
						message = GenericConstants.PAYMENTSUCCESS + safexResponseBean.getOrder_no() + "";
						actionUrl = "SafexResponseAction_statusMsgDisplay.action?statusMsg=" + message;
						commonService.insertCandidateAuditrail(loggedInUser, "Payment Page - Payment Status",
								"Candidate with User Id " + loggedInUser.getUsername() + " : "+message);
						users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
						users.setStatusID(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
						String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED) + "";
						Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
						Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
						EmailSMSUtil.insertEmailNSMSForPaymentApproved(users, staus, emailReq, smsReq);
						
					} else if ("Failed".equalsIgnoreCase(safexResponseBean.getStatus())) {
						logger.info("SafexResponseAction Payment Failure For User Id :" + users.getUsername());
						commonService.updateCandidateStatus(users, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
						message = GenericConstants.PAYMENTFAILURE + safexResponseBean.getOrder_no() + " " + safexResponseBean.getRes_message();
						actionUrl = "SafexResponseAction_statusMsgDisplay.action?statusMsg=" + message;
						users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
						String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED) + "";
						Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_REJECTED);
						Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_REJECTED);
						EmailSMSUtil.insertEmailNSMSForPaymentFailure(users, staus, emailReq, smsReq);
						
					} 
					else if("Pending".equalsIgnoreCase(safexResponseBean.getStatus()))
					{
						message = GenericConstants.PAYMENTWAITING;
						actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
						logger.info("Following payment is pending at the branch For User Id :" + users.getUsername());
					}
				}
			}
			
			if (updatePaymentResponse > 0) {
				logger.info("SafexResponseAction Payment details successfully saved and completed For User Id :" + users.getUsername());
			} else {
				logger.info("SafexResponseAction Payment details DB Updation Issues For User Id :" + users.getUsername() + " TxnReferenceNo :"
						+ safexResponseBean.getOrder_no());
				message = GenericConstants.PAYMENTFAILURE + safexResponseBean.getOrder_no() + " " + safexResponseBean.getRes_message();
				actionUrl = "SBIEPayResponseAction_statusMsgDisplay.action?statusMsg=" + message;
			}

			sessionAttributes.put(SESSION_USER, users);
			request.setAttribute(DESTINATION_URL, actionUrl);
		}catch(Exception e) {
			logger.info("Exception SafexResponseAction For User ID:" + loggedInUser.getUsername() + " Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String statusMsgDisplay() {
		String returnType = "success";
		String statusMsg = request.getParameter("statusMsg");
		if (statusMsg.contains(GenericConstants.PAYMENTSUCCESS)) {
			safexResponseBean.setEnableButton("true");
		} else {
			safexResponseBean.setEnableButton("false");
		}
		logger.info("Payment statusMsg :" + statusMsg);
		safexResponseBean.setResponseMsg(statusMsg);
		return returnType;
	}
	
	@SuppressWarnings("unchecked")
	public String getDoubleVerificationResponseBulk() throws Exception {
		logger.info("getDoubleVerificationResponseBulkforCheckStatus Called for 1 specific user :" + new Date());
		String encdata = "";
		String generatedChecksum = "";
		String requestParameters = "";
		String responseMsg = "";
		String userID = "";
		//Long userPk = loggedInUser.getUserId();
		List<SBIResponseBean> responseDetailsTP = null;
		String existmercntTxnNo = "";
		String msg = "";
		String result = REDIRECT_ACTION_URL;
		String actionUrl = "";
		String message = "";
		//userID = loggedInUser.getUsername();
		
		try {
			
			String ag_id = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AGGREGATOR_ID);
			String merchant_key  = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MERCHANT_KEY);
			String merchantId = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MERCHANT_ID);
			
			List<PaymentOnlineBean> pendingPaymentRecords = paymentService.getPendingPaymentRecords();
			if (pendingPaymentRecords != null && !pendingPaymentRecords.isEmpty()) {
				for (PaymentOnlineBean paymentOnlineBean : pendingPaymentRecords) {
					
					Users users = loginService.getUserForUsername(paymentOnlineBean.getOpd_created_by());
					userID = users.getUsername();
					
					logger.info("Double verification for bulk Offline Call For User ID :[" + paymentOnlineBean.getOpd_created_by() + "]  " + "existmercntTxnNo :["
							+ paymentOnlineBean.getOpd_transaction_no() + "]");
					existmercntTxnNo = paymentOnlineBean.getOpd_transaction_no();
					if (existmercntTxnNo != null && !existmercntTxnNo.equals("")) {
						
						String me_id= merchantId;
						String  order_no= paymentOnlineBean.getOpd_transaction_no();
						
						String enc_status_order_no = PayGateCryptoUtils.encrypt(order_no, merchant_key);
						String valuesforchecksum = ag_id+'~'+me_id +'~'+ order_no;
						String checksum = org.apache.commons.codec.digest.DigestUtils.sha256Hex(valuesforchecksum);
						
						Map<String, String> api_request_params = new HashMap<String, String>();
						api_request_params.put("ag_id", ag_id); 
						api_request_params.put("me_id", me_id); 
						api_request_params.put("ag_ref", ""); 
						api_request_params.put("order_no",  enc_status_order_no);
						api_request_params.put("checksum", checksum);
						
						JSONObject request_json = new JSONObject(api_request_params);
						logger.info(request_json);
						
						responseMsg = sendDoubleVerificationPostCall(request_json.toString());
						
						Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
						Map<String, Object> transaction_data_object = new HashMap<String, Object>();
						transaction_data_object = new Gson().fromJson(responseMsg, mapType); 
						logger.info("transaction Data Object : " + transaction_data_object);
						
						Map<String, Object> txn_response = new HashMap<String, Object>();
						txn_response=(Map<String, Object>)transaction_data_object; 
						logger.info("transaction  Response : " + txn_response);
						
						String error_message ="";
						if (txn_response == null)
						{
						   
							Type mapType1 = new TypeToken<Map<String, Object>>(){}.getType();
							Map<Object,Object> errorMap = new HashMap<>();
							transaction_data_object = new Gson().fromJson(responseMsg, mapType1);   
							errorMap=(Map<Object, Object>)transaction_data_object.get("error_details"); 
							System.out.println("error details1 : " + errorMap.get("error_message"));
							error_message=errorMap.get("error_message").toString();
						}else {
							valuesforchecksum = ag_id+'~'+me_id+'~'+enc_status_order_no;
							String hash_data = org.apache.commons.codec.digest.DigestUtils.sha256Hex(valuesforchecksum);
							String protocol = "Not Matched";
							System.out.println("responseChecksum : " + txn_response.get("responseChecksum").toString());
							System.out.println("hash_data : " + hash_data);
							if (txn_response.get("responseChecksum").toString()!= null)
						    {
								String responseChecksum=txn_response.get("responseChecksum").toString();
						        if(responseChecksum.equals(hash_data))
						        {
						            protocol = "Matched";
						        }
						    }
							txn_response=(Map<String, Object>)transaction_data_object.get("txn_response"); 
							ag_id=txn_response.get("ag_id").toString();
							order_no=txn_response.get("order_no").toString();
							safexResponseBean.setOrder_no(txn_response.get("order_no").toString());
							safexResponseBean.setAmount(txn_response.get("amount").toString());
							safexResponseBean.setCountry(txn_response.get("country").toString());
							safexResponseBean.setCurrency(txn_response.get("currency").toString());
							safexResponseBean.setTxn_date(txn_response.get("txn_date").toString());
							safexResponseBean.setTxn_time(txn_response.get("txn_time").toString()); 
							safexResponseBean.setAg_ref(txn_response.get("ag_ref").toString());
							safexResponseBean.setPg_ref(txn_response.get("pg_ref").toString());
							safexResponseBean.setStatus(txn_response.get("status").toString()); 
							safexResponseBean.setRes_code(txn_response.get("res_code").toString()); 
							safexResponseBean.setRes_message(txn_response.get("res_message").toString()); 
							
							Map<String, Object> txn_pg_details = new HashMap<String, Object>();
							txn_pg_details=(Map<String, Object>)transaction_data_object.get("pg_details");
							if(txn_pg_details.get("pg_id") != null) {
							safexResponseBean.setPg_id(txn_pg_details.get("pg_id").toString());
							}
							if(txn_pg_details.get("pg_name") != null) {
							safexResponseBean.setPg_id(txn_pg_details.get("pg_name").toString());
							}
							if(txn_pg_details.get("paymode") != null) {
							safexResponseBean.setPg_id(txn_pg_details.get("paymode").toString());
							}
							
							/*Map<String, Object> txn_fraud_details = new HashMap<String, Object>();
							txn_fraud_details=(Map<String, Object>)transaction_data_object.get("fraud_details");
							String fraud_action = txn_fraud_details.get("fraud_action").toString();
							String fraud_message = txn_fraud_details.get("fraud_message").toString();
							String score = txn_fraud_details.get("score").toString();*/
							
							Map<String, Object> txn_other_details = new HashMap<String, Object>();
							txn_other_details=(Map<String, Object>)transaction_data_object.get("other_details");
							String udf_1 = txn_other_details.get("udf_1").toString();
							String udf_2 = txn_other_details.get("udf_2").toString();
							String udf_3 = txn_other_details.get("udf_3").toString();
							
							logger.info("getDoubleVerificationResponseBulkforCheckStatus For User Id :" + paymentOnlineBean.getOpd_created_by() + " Payment merchOrderno ID :["
									+ safexResponseBean.getOrder_no() + "] Payment AuthStatus :[" + safexResponseBean.getStatus() + "] StatusDesc : "
									+ safexResponseBean.getRes_message());
							
							int insertPaymentDetls = 0;
							if (null != users && userID != null && !userID.equals("NA")) {
								insertPaymentDetls = paymentService.updateSafexResponseForCandidate(safexResponseBean, users);
								if (insertPaymentDetls > 0) {
									if (safexResponseBean != null && safexResponseBean.getStatus() != null) {
										if (safexResponseBean.getStatus().equalsIgnoreCase("Successful")) {
											int updateVal = commonService.updateCandidateStatus(users,
													ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
											logger.info("getDoubleVerificationResponseBulkforCheckStatus Payment Success For User Id :" + users.getUsername()
													+ " updateCandidateStatus:" + updateVal);
											message = GenericConstants.PAYMENTSUCCESS + "" + existmercntTxnNo + "";
											actionUrl = "SafexResponseAction_statusMsgDisplay.action?statusMsg=" + message;
											users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
											String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED) + "";
											Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
											Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
											EmailSMSUtil.insertEmailNSMSForPaymentApproved(users, staus, emailReq, smsReq);
											ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger().info(users.getUsername() + ", " + request.getRemoteAddr()
													+ ", Payment Approved for Offline , " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
										} else if (safexResponseBean.getStatus().equalsIgnoreCase("Pending")) {
											message = GenericConstants.PAYMENTWAITING;
											actionUrl = "SafexResponseAction_statusMsgDisplay.action?statusMsg=" + message;
											logger.info("getDoubleVerificationResponseBulkforCheckStatus SBIePay is Pending for Response from SBI Bank. For User Id:"
													+ users.getUsername());

										} else if (safexResponseBean.getStatus().equalsIgnoreCase("Failed")) {
											int updateVal = commonService.updateCandidateStatus(users,
													ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
											logger.info("getDoubleVerificationResponseBulkforCheckStatus Payment Failure For User Id :" + users.getUsername()
													+ " updateCandidateStatus:" + updateVal);
											users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
											String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED) + "";
											Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_REJECTED);
											Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_REJECTED);
											EmailSMSUtil.insertEmailNSMSForPaymentFailure(users, staus, emailReq, smsReq);
											message = GenericConstants.PAYMENTFAILURE + safexResponseBean.getOrder_no();
											actionUrl = "SafexResponseAction_statusMsgDisplay.action?statusMsg=" + message;
										}
									} else { // Incase of authstatus == null
										message = GenericConstants.PAYMENTFAILURE + safexResponseBean.getOrder_no();
										actionUrl = "SafexResponseAction_statusMsgDisplay.action?statusMsg=" + message;
										int updateVal = commonService.updateCandidateStatus(users,
												ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
										logger.info("Auth Id found null  For User Id :" + users.getUsername());
										users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
									}
								}
							}
							}
					}else {
						msg = "TXNNONOTFOUND";
						message = "Sorry. Your Payment could not be processed." + "" + " Payment Transaction NO Not Found Found In Portal, Please try again after some time !";
						actionUrl = "SafexResponseAction_statusMsgDisplay.action?statusMsg=" + message;
					}
				}
			}
		}catch(Exception e) {
			logger.fatal(e,e);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
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
					"Check status Button Clicked by" + ", User ID:" + loggedInUser.getUsername() + ". "
			+ "Clicked Date and time :" + formatter.format(date));
			
			String ag_id = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AGGREGATOR_ID);
			String merchant_key  = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MERCHANT_KEY);
			String merchantId = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MERCHANT_ID);
			
			int opaymentStatusDateCheckUpdate = paymentService.opd_paymentStatusDateCheckUpdate(loggedInUser);
			
			List<PaymentOnlineBean> pendingPaymentRecords = paymentService.getPendingPaymentRecords(userPk);
			if (pendingPaymentRecords != null && !pendingPaymentRecords.isEmpty()) {
				logger.info("Safex Double Verification Bulk used for 1 specific user :" + userID);
				for (PaymentOnlineBean paymentOnlineBean : pendingPaymentRecords) {
					logger.info("Double verification for Single Offline Call For User ID :[" + paymentOnlineBean.getOpd_created_by() + "]  " + "existmercntTxnNo :["
							+ paymentOnlineBean.getOpd_transaction_no() + "]");
					existmercntTxnNo = paymentOnlineBean.getOpd_transaction_no();
					if (existmercntTxnNo != null && !existmercntTxnNo.equals("")) {
						
						String me_id= merchantId;
						String  order_no= paymentOnlineBean.getOpd_transaction_no();
						
						String enc_status_order_no = PayGateCryptoUtils.encrypt(order_no, merchant_key);
						String valuesforchecksum = ag_id+'~'+me_id +'~'+ order_no;
						String checksum = org.apache.commons.codec.digest.DigestUtils.sha256Hex(valuesforchecksum);
						
						Map<String, String> api_request_params = new HashMap<String, String>();
						api_request_params.put("ag_id", ag_id); 
						api_request_params.put("me_id", me_id); 
						api_request_params.put("ag_ref", ""); 
						api_request_params.put("order_no",  enc_status_order_no);
						api_request_params.put("checksum", checksum);
						
						JSONObject request_json = new JSONObject(api_request_params);
						logger.info(request_json);
						
						responseMsg = sendDoubleVerificationPostCall(request_json.toString());
						
						Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
						Map<String, Object> transaction_data_object = new HashMap<String, Object>();
						transaction_data_object = new Gson().fromJson(responseMsg, mapType); 
						logger.info("transaction Data Object : " + transaction_data_object);
						
						Map<String, Object> txn_response = new HashMap<String, Object>();
						txn_response=(Map<String, Object>)transaction_data_object; 
						logger.info("transaction  Response : " + txn_response);
						
						String error_message ="";
						if (txn_response == null)
						{
						   
							Type mapType1 = new TypeToken<Map<String, Object>>(){}.getType();
							Map<Object,Object> errorMap = new HashMap<>();
							transaction_data_object = new Gson().fromJson(responseMsg, mapType1);   
							errorMap=(Map<Object, Object>)transaction_data_object.get("error_details"); 
							System.out.println("error details1 : " + errorMap.get("error_message"));
							error_message=errorMap.get("error_message").toString();
						}else if(responseMsg == null || responseMsg.equals("")){
							safexResponseBean.setOrder_no(order_no);
							safexResponseBean.setStatus("Failed");
							paymentService.updateSafexResponseForCandidate(safexResponseBean, users);
							actionUrl = "PaymentOnlineAction_showPaymentScreen.action";
							sessionAttributes.put(SESSION_USER, users);
							request.setAttribute(DESTINATION_URL, actionUrl);
							return result;
						}
						else {
							valuesforchecksum = ag_id+'~'+me_id+'~'+enc_status_order_no;
							String hash_data = org.apache.commons.codec.digest.DigestUtils.sha256Hex(valuesforchecksum);
							String protocol = "Not Matched";
							System.out.println("responseChecksum : " + txn_response.get("responseChecksum").toString());
							System.out.println("hash_data : " + hash_data);
							if (txn_response.get("responseChecksum").toString()!= null)
						    {
								String responseChecksum=txn_response.get("responseChecksum").toString();
						        if(responseChecksum.equals(hash_data))
						        {
						            protocol = "Matched";
						        }
						    }
							
							if(transaction_data_object.containsKey("error_details"))  {
								int updateVal = commonService.updateCandidateStatus(users,
										ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
								logger.info("getDoubleVerificationResponseBulkforCheckStatus Payment Failure For User Id :" + users.getUsername()
										+ " updateCandidateStatus:" + updateVal);
								users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
								Map<String, Object> failed_response = new HashMap<String, Object>();
								failed_response = (Map<String, Object>)transaction_data_object.get("error_details");
								safexResponseBean.setOrder_no(failed_response.get("orderNumber").toString());
								safexResponseBean.setStatus("Failed");
								paymentService.updateSafexResponseForCandidate(safexResponseBean, users);
								message = GenericConstants.PAYMENTFAILURE + safexResponseBean.getOrder_no();
								actionUrl = "SafexResponseAction_statusMsgDisplay.action?statusMsg=" + message;
								sessionAttributes.put(SESSION_USER, users);
								request.setAttribute(DESTINATION_URL, actionUrl);
								return result;
							}
							txn_response=(Map<String, Object>)transaction_data_object.get("txn_response"); 
							ag_id=txn_response.get("ag_id").toString();
							order_no=txn_response.get("order_no").toString();
							safexResponseBean.setOrder_no(txn_response.get("order_no").toString());
							safexResponseBean.setAmount(txn_response.get("amount").toString());
							safexResponseBean.setCountry(txn_response.get("country").toString());
							safexResponseBean.setCurrency(txn_response.get("currency").toString());
							safexResponseBean.setTxn_date(txn_response.get("txn_date").toString());
							safexResponseBean.setTxn_time(txn_response.get("txn_time").toString()); 
							safexResponseBean.setAg_ref(txn_response.get("ag_ref").toString());
							safexResponseBean.setPg_ref(txn_response.get("pg_ref").toString());
							safexResponseBean.setStatus(txn_response.get("status").toString()); 
							safexResponseBean.setRes_code(txn_response.get("res_code").toString()); 
							safexResponseBean.setRes_message(txn_response.get("res_message").toString()); 
							
							Map<String, Object> txn_pg_details = new HashMap<String, Object>();
							txn_pg_details=(Map<String, Object>)transaction_data_object.get("pg_details");
							if(txn_pg_details.get("pg_id") != null) {
							safexResponseBean.setPg_id(txn_pg_details.get("pg_id").toString());
							}
							if(txn_pg_details.get("pg_name") != null) {
							safexResponseBean.setPg_id(txn_pg_details.get("pg_name").toString());
							}
							if(txn_pg_details.get("paymode") != null) {
							safexResponseBean.setPg_id(txn_pg_details.get("paymode").toString());
							}
							
							/*Map<String, Object> txn_fraud_details = new HashMap<String, Object>();
							txn_fraud_details=(Map<String, ObjecA%t>)transaction_data_object.get("fraud_details");
							String fraud_action = txn_fraud_details.get("fraud_action").toString();
							String fraud_message = txn_fraud_details.get("fraud_message").toString();
							String score = txn_fraud_details.get("score").toString();*/
							
							Map<String, Object> txn_other_details = new HashMap<String, Object>();
							txn_other_details=(Map<String, Object>)transaction_data_object.get("other_details");
							String udf_1 = txn_other_details.get("udf_1").toString();
							String udf_2 = txn_other_details.get("udf_2").toString();
							String udf_3 = txn_other_details.get("udf_3").toString();
							
							logger.info("getDoubleVerificationResponseBulkforCheckStatus For User Id :" + paymentOnlineBean.getOpd_created_by() + " Payment merchOrderno ID :["
									+ safexResponseBean.getOrder_no() + "] Payment AuthStatus :[" + safexResponseBean.getStatus() + "] StatusDesc : "
									+ safexResponseBean.getRes_message());
							
							int insertPaymentDetls = 0;
							if (null != users && userID != null && !userID.equals("NA")) {
								insertPaymentDetls = paymentService.updateSafexResponseForCandidate(safexResponseBean, users);
								if (insertPaymentDetls > 0) {
									if (safexResponseBean != null && safexResponseBean.getStatus() != null) {
										if (safexResponseBean.getStatus().equalsIgnoreCase("Successful")) {
											int updateVal = commonService.updateCandidateStatus(users,
													ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
											logger.info("getDoubleVerificationResponseBulkforCheckStatus Payment Success For User Id :" + users.getUsername()
													+ " updateCandidateStatus:" + updateVal);
											message = GenericConstants.PAYMENTSUCCESS + "" + existmercntTxnNo + "";
											actionUrl = "SafexResponseAction_statusMsgDisplay.action?statusMsg=" + message;
											users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
											String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED) + "";
											Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
											Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
											EmailSMSUtil.insertEmailNSMSForPaymentApproved(users, staus, emailReq, smsReq);
											ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger().info(users.getUsername() + ", " + request.getRemoteAddr()
													+ ", Payment Approved for Offline , " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
										} else if (safexResponseBean.getStatus().equalsIgnoreCase("Pending")) {
											message = GenericConstants.PAYMENTWAITING;
											actionUrl = "SafexResponseAction_statusMsgDisplay.action?statusMsg=" + message;
											logger.info("getDoubleVerificationResponseBulkforCheckStatus SBIePay is Pending for Response from SBI Bank. For User Id:"
													+ users.getUsername());

										} else if (safexResponseBean.getStatus().equalsIgnoreCase("Failed")) {
											int updateVal = commonService.updateCandidateStatus(users,
													ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
											logger.info("getDoubleVerificationResponseBulkforCheckStatus Payment Failure For User Id :" + users.getUsername()
													+ " updateCandidateStatus:" + updateVal);
											users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
											String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED) + "";
											Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_REJECTED);
											Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_REJECTED);
											EmailSMSUtil.insertEmailNSMSForPaymentFailure(users, staus, emailReq, smsReq);
											message = GenericConstants.PAYMENTFAILURE + safexResponseBean.getOrder_no();
											actionUrl = "SafexResponseAction_statusMsgDisplay.action?statusMsg=" + message;
										}
									} else { // Incase of authstatus == null
										message = GenericConstants.PAYMENTFAILURE + safexResponseBean.getOrder_no();
										actionUrl = "SafexResponseAction_statusMsgDisplay.action?statusMsg=" + message;
										int updateVal = commonService.updateCandidateStatus(users,
												ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
										logger.info("Auth Id found null  For User Id :" + users.getUsername());
										users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));

									}

								}
							}
							
							}
						
						
					}else {
						msg = "TXNNONOTFOUND";
						message = "Sorry. Your Payment could not be processed." + "" + " Payment Transaction NO Not Found Found In Portal, Please try again after some time !";
						actionUrl = "SafexResponseAction_statusMsgDisplay.action?statusMsg=" + message;
					}
				}
			}
			
		}catch(Exception e) {
			logger.fatal(e,e);
		}
		sessionAttributes.put(SESSION_USER, users);
		request.setAttribute(DESTINATION_URL, actionUrl);
		return result;
	}
	
	public String sendDoubleVerificationPostCall(String requestJson) throws Exception {
		String url = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SAFEX_STATUS_VERIFICATION_URL);
		String merchant_key = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MERCHANT_KEY);
		String responseStr = "";
		
		try {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setRequestMethod("POST");  
		con.setRequestProperty("Content-Type","application/json");
		
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(requestJson);
		wr.flush();
		wr.close();
		
		int responseCode = con.getResponseCode();
		logger.info("Response Code : " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) {
			logger.info("nSending 'POST' request to URL : " + url);
			logger.info("Post Data : " + requestJson);
			logger.info("Response Code : " + responseCode);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String output;
			StringBuffer response1 = new StringBuffer();
			
			while ((output = in.readLine()) != null) {
				 response1.append(output);
				}
				in.close();  
				
				logger.info("Encrypted Response : " + response1);
				String transaction_data = PayGateCryptoUtils.decrypt(response1.toString(), merchant_key); 
				logger.info("Decrypted Response : " + transaction_data);
				responseStr = transaction_data;
		}
		else {
			responseStr = "";
		}
		}catch (MalformedURLException e) {
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
		}
		return responseStr;
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
