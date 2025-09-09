package com.nseit.payment.action;

import static com.nseit.generic.util.GenericConstants.DESTINATION_URL;
import static com.nseit.generic.util.GenericConstants.REDIRECT_ACTION_URL;
import static com.nseit.generic.util.GenericConstants.SESSION_USER;

import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.struts2.dispatcher.HttpParameters;

import com.nseit.generic.action.BaseAction;
import com.nseit.generic.models.CandidateMgmtBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.CandidateMgmtService;
import com.nseit.generic.service.CommonService;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.aware.UserAware;
import com.nseit.generic.util.emailsms.EmailSMSUtil;
import com.nseit.payment.models.PaymentOnlineBean;
import com.nseit.payment.models.TechProcessResponseBean;
import com.nseit.payment.service.PaymentService;
import com.opensymphony.xwork2.ModelDriven;
import com.tp.pg.util.TransactionResponseBean;

public class TechProcessResponseAction extends BaseAction implements ModelDriven<TechProcessResponseBean>, UserAware {

	private static final long serialVersionUID = -3649023101779183761L;

	public TechProcessResponseBean techProcessResponseBean;
	private Logger logger = LoggerHome.getLogger(getClass());
	private CandidateMgmtBean candidateMgmtBean = new CandidateMgmtBean();
	private PaymentOnlineBean paymentBean = new PaymentOnlineBean();
	private PaymentService paymentService;
	private CommonService commonService;
	private CandidateMgmtService candidateMgmtService;

	public void setCandidateMgmtService(CandidateMgmtService candidateMgmtService) {
		this.candidateMgmtService = candidateMgmtService;
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public TechProcessResponseAction() {
		displayMenus();
	}

	public String execute() throws Exception {
		logger.info("Tech Process Response Method execute() is calling! ");
		Users users = (Users) sessionAttributes.get(SESSION_USER);
		String responseMsg = request.getParameter("msg");
		String tpsl_mrct_code = request.getParameter("tpsl_mrct_cd");
		String transactionId = request.getParameter("mer_tran_id");
		String result = REDIRECT_ACTION_URL;
		String actionUrl = "";
		int updatePaymentDetails = 0;
		try {
			String logTPPath = "D://OutTWC.log";
			File file = new File(logTPPath);
			if (!file.exists()) {
				file.createNewFile();
			}

			if (responseMsg != null && !responseMsg.trim().equals("")) {
				String encryptionKey = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.PG_ENCRYPTION_KEY);
				String encryptionIV = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.PG_ENCRYPTION_IV);
				TransactionResponseBean objTransactionResponseBean = new TransactionResponseBean();
				objTransactionResponseBean.setKey(encryptionKey.getBytes());
				objTransactionResponseBean.setIv(encryptionIV.getBytes());
				objTransactionResponseBean.setResponsePayload(responseMsg);
				objTransactionResponseBean.setLogPath(logTPPath);

				String decryptedData = "";
				try {
					decryptedData = objTransactionResponseBean.getResponsePayload();
				} catch (Exception e) {
					logger.error("Error while decrypting TP response");
					logger.fatal(e, e);
				}
				logger.info("Tech Process Response = " + decryptedData);

				// logger.info("=====tech process response====="+decryptedData);
				StringTokenizer strTok = new StringTokenizer(decryptedData, "|");
				techProcessResponseBean.setMerchantId(tpsl_mrct_code);
				String authStatus[] = strTok.nextToken().split("=");
				techProcessResponseBean.setAuthStatus(authStatus[1]);
				String txnMsg[] = strTok.nextToken().split("=");
				techProcessResponseBean.setTransactionMessage(txnMsg[1]);
				String errDesc[] = strTok.nextToken().split("=");
				techProcessResponseBean.setErrorDEsc(errDesc[0]);
				String custId[] = strTok.nextToken().split("=");
				techProcessResponseBean.setCustomerId(custId[1]);
				String bankId[] = strTok.nextToken().split("=");
				techProcessResponseBean.setBankId(bankId[1]);
				String txnRefNo[] = strTok.nextToken().split("=");
				techProcessResponseBean.setTxnReferenceNo(txnRefNo[1]);
				String txnAmt[] = strTok.nextToken().split("=");
				techProcessResponseBean.setTxnAmount(txnAmt[1]);
				String clMetDt[] = strTok.nextToken().split("=");
				techProcessResponseBean.setClientReqMeta(clMetDt[1]);
				String txnDate[] = strTok.nextToken().split("=");
				techProcessResponseBean.setTxnDate(txnDate[1]);
				String refId[] = strTok.nextToken().split("=");
				techProcessResponseBean.setRefundId(refId[1]);
				String balAmt[] = strTok.nextToken().split("=");
				techProcessResponseBean.setBalAmt(balAmt[1]);
				String token[] = strTok.nextToken().split("=");

				techProcessResponseBean.setToken(token[1]);
				String ckSum[] = strTok.nextToken().split("=");
				techProcessResponseBean.setCheckSum(ckSum[1]);

				logger.info("Client ID : " + techProcessResponseBean.getCustomerId());
				logger.info("Payment ID : " + techProcessResponseBean.getTxnReferenceNo());
				logger.info("Auth Status : " + techProcessResponseBean.getAuthStatus());

				String payAuditStr = "Candidate with User Id " + loggedInUser.getUsername() + " has Payment Status :" + techProcessResponseBean.getAuthStatus()
						+ " TransactionMessage :" + techProcessResponseBean.getTransactionMessage() + " TxnReferenceNo :" + techProcessResponseBean.getTxnReferenceNo()
						+ " TxnReference ID:" + techProcessResponseBean.getCustomerId();

				commonService.insertCandidateAuditrail(loggedInUser, "Get Payment Response", payAuditStr);

				if (techProcessResponseBean != null && techProcessResponseBean.getTransactionMessage() != null) {
					updatePaymentDetails = paymentService.insertOnlinTransactionDetails(techProcessResponseBean, users);
					if (updatePaymentDetails > 0) {
						if ("success".equalsIgnoreCase(techProcessResponseBean.getTransactionMessage())) {
							sessionAttributes.put("TRANSACTION", "success");
							sessionAttributes.put("MESSAGE", GenericConstants.PAYMENTSUCCESS + techProcessResponseBean.getTxnReferenceNo());
							logger.info("Tech Process Response Payment Success for Customer ID = " + techProcessResponseBean.getCustomerId());

							int updateVal = commonService.updateCandidateStatus(users, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
							actionUrl = "TechProcessResponseAction_statusMsgDisplay.action?statusMsg=" + GenericConstants.PAYMENTSUCCESS + " "
									+ techProcessResponseBean.getTxnReferenceNo();

							// logger.info("Status Updated for Payment Success.
							// "+updateVal);
							users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));

							String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED) + "";
							Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
							Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
							// EmailSMSUtil.insertEmailNSMSForActivitynTestForPayment(ConfigurationConstants.getInstance().getActivtyKeyForName(GenericConstants.PAYMENT_APPROVED),Integer.parseInt(loggedInUser.getDisciplineID()),
							// loggedInUser.getUsername(),
							// loggedInUser.getUsername(),staus,loggedInUser,emailReq,smsReq);
							EmailSMSUtil.insertEmailNSMSForPaymentApproved(users, staus, emailReq, smsReq);
							logger.info(loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ", Payment Approved for Online , "
									+ CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
						} else {
							sessionAttributes.put("TRANSACTION", "failed");
							sessionAttributes.put("MESSAGE", GenericConstants.PAYMENTFAILURE);
							logger.info("Tech Process Response Payment Failure for Customer ID = " + techProcessResponseBean.getCustomerId());

							int updateVal = commonService.updateCandidateStatus(users, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
							actionUrl = "TechProcessResponseAction_statusMsgDisplay.action?statusMsg=" + GenericConstants.PAYMENTFAILURE + " "
									+ techProcessResponseBean.getTxnReferenceNo();

							// logger.info("Status Updated Payment Failure.
							// "+updateVal);
							users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));

							String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED) + "";
							Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_REJECTED);
							Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_REJECTED);
							// EmailSMSUtil.insertEmailNSMSForActivitynTestForPayment(ConfigurationConstants.getInstance().getActivtyKeyForName(GenericConstants.ONLINE_PAYMENT_TRANSACTION_FAILED),Integer.parseInt(loggedInUser.getDisciplineID()),
							// loggedInUser.getUsername(),
							// loggedInUser.getUsername(),staus,loggedInUser,emailReq,smsReq);
							EmailSMSUtil.insertEmailNSMSForPaymentFailure(users, staus, emailReq, smsReq);

							logger.info(loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ",Payment Rejected for online , "
									+ CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
						}
					}
				}
			}
			sessionAttributes.put(SESSION_USER, users);
			request.setAttribute(DESTINATION_URL, actionUrl);
		} catch (Exception e) {
			logger.error(TechProcessResponseAction.class + " : Error while Updating TP response");
			logger.fatal(e, e);
			return "errorPage";
		}
		return result;
	}

	public String statusMsgDisplay() throws Exception {
		String returnType = "success";
		String responseMsg = request.getParameter("statusMsg");
		techProcessResponseBean.setResponseMsg(responseMsg);
		return returnType;
	}

	public String showPaymentResponse() throws Exception {
		String returnType = "successResp";
		String filename = System.getProperty("user.dir") + "\\properties\\MerchantDetails.pro";
		String responseMsg = request.getParameter("responseMsg");

		if (responseMsg != null && !responseMsg.trim().equals("")) {
			StringTokenizer strTok = new StringTokenizer(responseMsg, "|");
			techProcessResponseBean.setMerchantId(strTok.nextToken());
			techProcessResponseBean.setTxnReferenceNo(strTok.nextToken());
			techProcessResponseBean.setCustomerId(strTok.nextToken());
			techProcessResponseBean.setBankReferenceNo(strTok.nextToken());
			techProcessResponseBean.setTxnAmount(strTok.nextToken());
			techProcessResponseBean.setBankId(strTok.nextToken());
			techProcessResponseBean.setBankMerchantId(strTok.nextToken());
			techProcessResponseBean.setTxnType(strTok.nextToken());
			techProcessResponseBean.setCurrencyName(strTok.nextToken());
			techProcessResponseBean.setItemCode(strTok.nextToken());
			techProcessResponseBean.setSecurityType(strTok.nextToken());
			techProcessResponseBean.setSecurityID(strTok.nextToken());
			techProcessResponseBean.setSecurityPassword(strTok.nextToken());
			techProcessResponseBean.setTxnDate(strTok.nextToken());
			techProcessResponseBean.setAuthStatus(strTok.nextToken());
			techProcessResponseBean.setSettlementType(strTok.nextToken());
			techProcessResponseBean.setAdditionalInfo1(strTok.nextToken());
			techProcessResponseBean.setAdditionalInfo2(strTok.nextToken());
			techProcessResponseBean.setAdditionalInfo3(strTok.nextToken());
			techProcessResponseBean.setAdditionalInfo4(strTok.nextToken());
			techProcessResponseBean.setAdditionalInfo5(strTok.nextToken());
			techProcessResponseBean.setAdditionalInfo6(strTok.nextToken());
			techProcessResponseBean.setAdditionalInfo7(strTok.nextToken());
			techProcessResponseBean.setAdditionalInfo(techProcessResponseBean.getAdditionalInfo1() + "##" + techProcessResponseBean.getAdditionalInfo2() + "##"
					+ techProcessResponseBean.getAdditionalInfo3() + "##" + techProcessResponseBean.getAdditionalInfo4() + "##" + techProcessResponseBean.getAdditionalInfo5()
					+ "##" + techProcessResponseBean.getAdditionalInfo6() + "##" + techProcessResponseBean.getAdditionalInfo7());
			techProcessResponseBean.setErrorStatus(strTok.nextToken());
			techProcessResponseBean.setErrorDEsc(strTok.nextToken());
			techProcessResponseBean.setCheckSum(strTok.nextToken());

			if (techProcessResponseBean.getAuthStatus() != null && techProcessResponseBean.getAuthStatus().equals("0300")) {
				techProcessResponseBean.setAuthStatus("Success");
			}

			String paymentID = techProcessResponseBean.getTxnReferenceNo();
			// logger.info("Payment ID : " + paymentID);
		}

		com.CheckSumResponseBean objResTranDetails = new com.CheckSumResponseBean();
		objResTranDetails.setStrMSG(responseMsg);
		objResTranDetails.setStrPropertyPath(filename);
		com.TPSLUtil util = new com.TPSLUtil();
		String strCheckSumValue = util.transactionResponseMessage(objResTranDetails);
		logger.info("Checksum Returned : " + techProcessResponseBean.getCheckSum() + " -- Calc Checksum : " + strCheckSumValue);
		return returnType;
	}

	@Override
	public void resetModel() {
		techProcessResponseBean = new TechProcessResponseBean();
	}

	@Override
	public TechProcessResponseBean getModel() {
		return techProcessResponseBean;
	}

	@Override
	public void setLoggedInUser(Users users) {
		this.loggedInUser = users;
	}

	public String payMentDoneMsgDisplay() throws Exception {
		String returnType = "success";
		String responseMsg = request.getParameter("statusMsg");
		techProcessResponseBean.setResponseMsg(responseMsg);
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
