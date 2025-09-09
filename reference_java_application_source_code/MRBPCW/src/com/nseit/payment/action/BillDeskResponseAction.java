package com.nseit.payment.action;

import static com.nseit.generic.util.GenericConstants.DESTINATION_URL;
import static com.nseit.generic.util.GenericConstants.REDIRECT_ACTION_URL;
import static com.nseit.generic.util.GenericConstants.SESSION_USER;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.struts2.dispatcher.HttpParameters;

import com.nseit.generic.action.BaseAction;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.CommonService;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.aware.UserAware;
import com.nseit.payment.models.BillDeskResponseBean;
import com.nseit.payment.service.BillDeskResposeService;
import com.opensymphony.xwork2.ModelDriven;

public class BillDeskResponseAction extends BaseAction implements ModelDriven<BillDeskResponseBean>, UserAware {

	public BillDeskResponseBean billDeskResponseBean; 
	private Logger logger = LoggerHome.getLogger(getClass());
	
	private BillDeskResposeService billDeskResposeService; 
	private CommonService commonService;
	public BillDeskResponseAction() {
		displayMenus();
	}
	
	
	public String execute(){
		System.out.println("method");
		String responseMsg = request.getParameter("msg");
		String result = REDIRECT_ACTION_URL;
		/*String responseMsg = "MERCHANTID|ARP10234|MSBI0412001668|NA|00000094.00|SBI|22270726|NA|INR|NA|NA|NA|NA|12-12-2004 16:08:56|0300|NA|NA|NA|NA|NA|NA|NA|NA|NA|NA|3734835005";*/
		BillDeskResponseBean billDeskResponseBeanTmp = null;
		
		
		Users users = (Users) sessionAttributes.get(SESSION_USER);
		
		String actionUrl = "";
		if (responseMsg !=null  && !"".equals(responseMsg)) {
			try {
				logger.info("responseMsg  :"+responseMsg); 
				billDeskResponseBeanTmp = billDeskResposeService.insertOnlinTransactionDetails(responseMsg,users);
			
			if (billDeskResponseBeanTmp!=null && billDeskResponseBeanTmp.getSuccessFlag()!=null){
				if (billDeskResponseBeanTmp.getAuthStatus().equals("0300")){
					//billDeskResponseBean.setResponseMsg(GenericConstants.PAYMENTSUCCESS);
					logger.info("Payment Success.");
					int updateVal = commonService.updateCandidateStatus(users, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
					actionUrl ="BillDeskResponseAction_statusMsgDisplay.action?statusMsg="+GenericConstants.PAYMENTSUCCESS+billDeskResponseBeanTmp.getTxnReferenceNo();
					users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
					ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger().info(loggedInUser.getUsername()+ ", "+ request.getRemoteAddr()+ ", Payment Approved for Online , "+ CommonUtil.formatDate(new Date(),"dd-MMM-yyyy HH:mm:ss"));
				}
				else {
					//billDeskResponseBean.setResponseMsg(GenericConstants.PAYMENTFAILURE);
					logger.info("Payment Failure.");
					int updateVal = commonService.updateCandidateStatus(users, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
					actionUrl ="BillDeskResponseAction_statusMsgDisplay.action?statusMsg="+GenericConstants.PAYMENTFAILURE+billDeskResponseBeanTmp.getTxnReferenceNo();
					ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger().info(loggedInUser.getUsername()+ ", "+ request.getRemoteAddr()+ ",Payment Rejected for online , "+ CommonUtil.formatDate(new Date(),"dd-MMM-yyyy HH:mm:ss"));
					users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
				}
				
			}
			 sessionAttributes.put(SESSION_USER, users);
			request.setAttribute(DESTINATION_URL,actionUrl);
			} catch (Exception e) {
				e.printStackTrace();
				return "errorPage";
			}
		}
		
		return result;
	}
	
	public String statusMsgDisplay() {
		String returnType = "success";
		String statusMsg = request.getParameter("statusMsg");
		System.out.println("statusMsg   "+statusMsg);
		billDeskResponseBean.setResponseMsg(statusMsg);
		return returnType;
	}
	
	@Override
	public void resetModel() {
		billDeskResponseBean = new BillDeskResponseBean();
	}
	public void setBillDeskResposeService(BillDeskResposeService billDeskResposeService) {
		this.billDeskResposeService = billDeskResposeService;
	}


	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}


	@Override
	public BillDeskResponseBean getModel() {
		return billDeskResponseBean;
	}


	@Override
	public void setLoggedInUser(Users users) {
		this.loggedInUser = users;
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
