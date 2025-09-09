package com.nseit.generic.action;

import static com.nseit.generic.util.GenericConstants.DESTINATION_URL;
import static com.nseit.generic.util.GenericConstants.GLOBAL_PLAIN_TEXT_MESSAGE;
import static com.nseit.generic.util.GenericConstants.REDIRECT_ACTION_URL;
import static com.nseit.generic.util.GenericConstants.SESSION_USER;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.dispatcher.HttpParameters;

import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.CandidateMgmtBean;
import com.nseit.generic.models.CommonBean;
import com.nseit.generic.models.HallTicketBean;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.PaymentApprovalBean;
import com.nseit.generic.models.PaymentBean;
import com.nseit.generic.models.UserDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.AgencyService;
import com.nseit.generic.service.CandidateMgmtService;
import com.nseit.generic.service.CandidateService;
import com.nseit.generic.service.CommonService;
import com.nseit.generic.service.ReportService;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.DESEncrypter;
import com.nseit.generic.util.ExcelCreator;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.ValidatorUtil;
import com.nseit.generic.util.aware.UserAware;
import com.nseit.generic.util.emailsms.EmailSMSUtil;
import com.nseit.generic.util.resource.ResourceUtil;
import com.nseit.generic.util.resource.ValidationMessageConstants;
import com.nseit.otbs.service.SchedulingTestService;
import com.nseit.payment.service.PaymentService;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.jasperreports.engine.JREmptyDataSource;

@SuppressWarnings("unused")
public class CandidateMgmtAction extends BaseAction implements ModelDriven<CandidateMgmtBean>, UserAware {

	/**
	 * 
	 */
	private Logger logger = LoggerHome.getLogger(getClass());
	private static final long serialVersionUID = 1L;
	private CandidateMgmtBean candidateMgmtBean = new CandidateMgmtBean();
	private Users loggedInUser;
	private CandidateMgmtService candidateMgmtService;
	private SchedulingTestService schedulingTestService;
	private CommonService commonService;

	private AgencyService agencyService;
	private CandidateService candidateService;

	private ReportService reportService;
	private HashMap parameterValues;

	List<CandidateMgmtBean> paymentReportExcelResultBeanList;
	private JREmptyDataSource jremptyDataSource;
	private Connection connection;

	private List<HallTicketBean> hallTicketBeanBulkList;

	/**
	 * @author vijaya
	 * @category Online / Offline Payment Approval Date : 2nd July 2012
	 * 
	 */
	private PaymentService paymentService;

	private List<String> paymentModeList;
	private List<CommonBean> paymentModeReportList = new ArrayList<CommonBean>();
	private List<CommonBean> disciplineList = new ArrayList<CommonBean>();
	private List<PaymentApprovalBean> ddPaymentApprovalList = new ArrayList<PaymentApprovalBean>();
	private List<PaymentApprovalBean> chalanPaymentApprovalList = new ArrayList<PaymentApprovalBean>();
	private PaymentApprovalBean paymentApprovalBean = new PaymentApprovalBean();
	private Pagination pagination = new Pagination(0, 1);

	public CandidateMgmtAction() {
		// logger.info("CandidateMgmtAction constructor is calling");
		displayMenus();
	}

	public List<CandidateMgmtBean> getPaymentReportExcelResultBeanList() {
		return paymentReportExcelResultBeanList;
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@Override
	public CandidateMgmtBean getModel() {
		return candidateMgmtBean;
	}

	@Override
	public void setLoggedInUser(Users users) {
		loggedInUser = users;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setCandidateMgmtService(CandidateMgmtService candidateMgmtService) {
		this.candidateMgmtService = candidateMgmtService;
	}

	public void setAgencyService(AgencyService agencyService) {
		this.agencyService = agencyService;
	}

	public void setSchedulingTestService(SchedulingTestService schedulingTestService) {
		this.schedulingTestService = schedulingTestService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
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

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public List<HallTicketBean> getHallTicketBeanBulkList() {
		return hallTicketBeanBulkList;
	}

	public String admitCard() {
		// logger.info("admitCard");
		String menuKey = null;
		String result = null;
		try {
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

	/**
	 * Gets the dD details for payment approval validation.
	 *
	 * @return the dD details for payment approval validation
	 * 
	 * @author vijaya
	 * @category Online / Offline Payment Approval Date : 2nd July 2012
	 */
	public List<String> getDDDetailsForPaymentApprovalValidation() {
		// logger.info("getDDDetailsForPaymentApprovalValidation()");
		List<String> errorList = new ArrayList<String>();
		try {
			if (candidateMgmtBean.getEnrollmentId() == null || candidateMgmtBean.getEnrollmentId().equals("")) {
				if (ValidatorUtil.isEmpty(candidateMgmtBean.getDisciplineId()) && ValidatorUtil.isEmpty(candidateMgmtBean.getPaymentMode())
						&& ValidatorUtil.isEmpty(candidateMgmtBean.getFromDate()) && ValidatorUtil.isEmpty(candidateMgmtBean.getToDate()))
					errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.PAYMENT_SEARCH_PARAM));
			}

		} catch (Exception e) {
			logger.fatal(e, e);
		}

		return errorList;
	}

	/**
	 * Gets the dD details for payment approval.
	 *
	 * @return the dD details for payment approval
	 * @throws Exception
	 *             the exception
	 * 
	 * @author vijaya
	 * @category Online / Offline Payment Approval Date : 2nd July 2012
	 */
	public String getDDDetailsForPaymentApproval() throws Exception {
		// logger.info("getDDDetailsForPaymentApproval()");
		List<String> errorsList = new ArrayList<String>();

		List<String> remarksList = new ArrayList<String>();
		candidateMgmtBean.setRemarksValue(remarksList);
		// errorsList = getDDDetailsForPaymentApprovalValidation();
		if (!errorsList.isEmpty()) {

			disciplineList = paymentService.getDisciplineList(loggedInUser);
			paymentModeList = getPaymentModeList();
			candidateMgmtBean.setEnrollmentIdIsNull("true");
			candidateMgmtBean.setDisciplineList(disciplineList);
			candidateMgmtBean.setPaymentModeList(paymentModeList);

			candidateMgmtBean.setRemarksValue(remarksList);

			addActionMessage(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SS_COMMON_WARNING_START));
			for (int i = 0; i < errorsList.size(); i++) {
				addActionMessage(errorsList.get(i));
			}
			addActionMessage(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SS_COMMON_WARNING_END));
			addActionError(" ");

		} else {
			disciplineList = paymentService.getDisciplineList(loggedInUser);
			paymentModeList = getPaymentModeList();

			if (paymentModeList != null) {
				candidateMgmtBean.setPaymentModeList(paymentModeList);
			}

			if (disciplineList != null) {
				candidateMgmtBean.setDisciplineList(disciplineList);

				// candidateMgmtBean.setDisciplineId(loggedInUser.getDisciplineID());
			}
			if (candidateMgmtBean.getPaymentMode() != null) {
				// while pagination: payment mode is getting null because of
				// multiple forms are present in the jsp
				candidateMgmtBean.setPaymentMode(candidateMgmtBean.getPaymentMode());
			}
			if (candidateMgmtBean.getEnrollmentId() == null || candidateMgmtBean.getEnrollmentId().equals("")) {
				if (candidateMgmtBean.getPaymentMode().equalsIgnoreCase("DD")) {
					/**
					 * Pagination Code: set total number of record
					 */
					int totalNumberOfRecord = paymentService.getDDDetailsCountForPaymentApproval(candidateMgmtBean);
					pagination.setProperties(totalNumberOfRecord);

					ddPaymentApprovalList = paymentService.getDDDetailsForPaymentApproval(candidateMgmtBean, pagination);
					candidateMgmtBean.setTotalCandidateCount(totalNumberOfRecord);

					if (ddPaymentApprovalList != null && !ddPaymentApprovalList.isEmpty()) {
						candidateMgmtBean.setDdPaymentApprovalList(ddPaymentApprovalList);

						/**
						 * Pagination Code: set the page record that should be
						 * show to end user
						 */
						pagination.setPage_records(chalanPaymentApprovalList.size());
					} else {
						candidateMgmtBean.setDdPaymentApprovalList(null);
						// addActionMessage("No record found");
					}
				} else if (candidateMgmtBean.getPaymentMode().equalsIgnoreCase("Challan")) {
					/**
					 * Pagination Code: set total number of record
					 */
					int totalNumberOfRecord = paymentService.getCountForChalanDetailsForPaymentApproval(candidateMgmtBean);
					pagination.setProperties(totalNumberOfRecord);
					chalanPaymentApprovalList = paymentService.getChalanDetailsForPaymentApproval(candidateMgmtBean, pagination);
					candidateMgmtBean.setTotalCandidateCount(totalNumberOfRecord);

					if (chalanPaymentApprovalList != null && !chalanPaymentApprovalList.isEmpty()) {
						candidateMgmtBean.setChalanPaymentApprovalList(chalanPaymentApprovalList);

						/**
						 * Pagination Code: set the page record that should be
						 * show to end user
						 */
						pagination.setPage_records(chalanPaymentApprovalList.size());
						// else
						// addActionMessage("No record found");
					}
				} else if (candidateMgmtBean.getPaymentMode().equalsIgnoreCase("e-Post")) {
					/**
					 * Pagination Code: set total number of record
					 */
					int totalNumberOfRecord = paymentService.getCountForEPostDetailsForPaymentApproval(candidateMgmtBean);
					pagination.setProperties(totalNumberOfRecord);
					chalanPaymentApprovalList = paymentService.getEPostDetailsForPaymentApproval(candidateMgmtBean, pagination);
					candidateMgmtBean.setTotalCandidateCount(totalNumberOfRecord);

					if (chalanPaymentApprovalList != null && !chalanPaymentApprovalList.isEmpty()) {
						candidateMgmtBean.setChalanPaymentApprovalList(chalanPaymentApprovalList);

						/**
						 * Pagination Code: set the page record that should be
						 * show to end user
						 */
						pagination.setPage_records(chalanPaymentApprovalList.size());
						// else
						// addActionMessage("No record found");
					}
				}
			} else {
				paymentApprovalBean = paymentService.getPaymentApprovalByEnrollmentId(candidateMgmtBean);

				List<PaymentApprovalBean> enrollApprovalList = new ArrayList<PaymentApprovalBean>();

				if (paymentApprovalBean != null) {
					enrollApprovalList.add(paymentApprovalBean);
					pagination.setProperties(1);
					candidateMgmtBean.setTotalCandidateCount(1);
					pagination.setPage_records(enrollApprovalList.size());

					if (paymentApprovalBean.getPaymentMode() != null) {
						if (paymentApprovalBean.getPaymentMode().compareTo("3") == 0) {
							candidateMgmtBean.setPaymentMode("DD");
							candidateMgmtBean.setDdPaymentApprovalList(enrollApprovalList);

							// remarksValue.add(paymentApprovalBean.getRemark());
							// candidateMgmtBean.setRemarksValue(remarksValue);
							// candidateMgmtBean.setRemarks(paymentApprovalBean.getRemark());
							candidateMgmtBean.getRemarksValue().add(paymentApprovalBean.getRemark());
						} else if (paymentApprovalBean.getPaymentMode().compareTo("5") == 0) {
							candidateMgmtBean.setPaymentMode("e-Post");
							candidateMgmtBean.setChalanPaymentApprovalList(enrollApprovalList);

							// remarksValue.add(paymentApprovalBean.getRemark());
							// candidateMgmtBean.setRemarksValue(remarksValue);
							// candidateMgmtBean.setRemarks(paymentApprovalBean.getRemark());
							candidateMgmtBean.getRemarksValue().add(paymentApprovalBean.getRemark());
						} else {
							candidateMgmtBean.setPaymentMode("Challan");
							candidateMgmtBean.setChalanPaymentApprovalList(enrollApprovalList);
							// remarksValue.add(paymentApprovalBean.getRemark());
							// candidateMgmtBean.setRemarks(paymentApprovalBean.getRemark());
							candidateMgmtBean.getRemarksValue().add(paymentApprovalBean.getRemark());
						}
					} else {
						candidateMgmtBean.setPaymentMode("Blank");
					}
				} else {
					candidateMgmtBean.setPaymentMode("Blank");
					// candidateMgmtBean.setDdPaymentApprovalList(enrollApprovalList);

					// addActionMessage("No record found");
				}

			}
		}
		if (candidateMgmtBean.getMenuKey() != null) {
			request.setAttribute("menuKey", candidateMgmtBean.getMenuKey());
		}
		candidateMgmtBean.setPaymentStatusMap(getPaymentStatusMapForPayementApproveReject());
		return "displayPaymentApproval";
	}

	/**
	 * Gets the payment details for registered id.
	 *
	 * @return the payment details for registered id
	 * @throws Exception
	 *             the exception
	 */
	public String getPaymentDetailsForRegisteredID() throws Exception {

		disciplineList = paymentService.getDisciplineList(loggedInUser);
		paymentModeList = getPaymentModeList();

		if (paymentModeList != null) {
			candidateMgmtBean.setPaymentModeList(paymentModeList);
		}

		if (disciplineList != null) {
			candidateMgmtBean.setDisciplineList(disciplineList);
		}

		paymentApprovalBean = paymentService.getPaymentApprovalByEnrollmentId(candidateMgmtBean);

		if (paymentApprovalBean != null) {
			paymentApprovalBean.setFlagForEdit("true");
			paymentApprovalBean.setEditBtnFlag("true");
			candidateMgmtBean.setPaymentDetailsForUserID(paymentApprovalBean);

		}

		return "displayPaymentDetailsForCandidate";
	}

	public String insertDDDetailsForRegisteredID() throws Exception {
		String result = "";
		List<String> errList = new ArrayList<String>();
		PaymentBean ddpaymentBean = null;
		int insertDDDetailsFlagVal = 0;
		int updateDDDetailsFlagVal = 0;
		int deletePaymentFlag = 0;
		// int existingDD = 0;

		boolean duplicateDDFlag = false;

		boolean alreadyChecked = false;

		PaymentApprovalBean paymentApprovalDetailsForUsr = candidateMgmtBean.getPaymentDetailsForUserID();

		Users user = new Users();
		user.setUserFk(Long.parseLong(paymentApprovalDetailsForUsr.getUserPK()));
		try {

			paymentApprovalDetailsForUsr.setCreatedBy(loggedInUser.getUsername());

			updateDDDetailsFlagVal = paymentService.updateDDDetailsForRegisteredID(paymentApprovalDetailsForUsr, loggedInUser);

			if (updateDDDetailsFlagVal == 0) {
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Error updating payment details.");
			} else {
				ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger().info(
						loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ", Submitted Offline Payment , " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
				String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_SUBMITED) + "";
				Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_SUBMITED);
				Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_SUBMITED);
				/*EmailSMSUtil.insertEmailNSMSForActivitynTestForPayment(ConfigurationConstants.getInstance().getActivtyKeyForName(GenericConstants.PAYMENT_SUBMITED),
						Integer.parseInt(loggedInUser.getDisciplineID()), loggedInUser.getUsername(), loggedInUser.getUsername(), staus, loggedInUser, emailReq, smsReq);
*/
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Payment updated successfully for UserID : " + paymentApprovalDetailsForUsr.getEnrollment_pk());

				addActionMessage("Payment updated successfully for UserID : " + paymentApprovalDetailsForUsr.getEnrollment_pk());
			}

			sessionAttributes.put(SESSION_USER, loggedInUser);

			result = "displayPaymentDetailsForCandidate";

		} catch (Exception ex) {
			throw new GenericException(ex);
		}

		return result;
	}

	public String insertChallanDetailsForRegisteredID() throws Exception {
		String result = "";
		int updateChallanDetailsFlagVal = 0;

		PaymentApprovalBean paymentApprovalDetailsForUsr = candidateMgmtBean.getPaymentDetailsForUserID();

		Users user = new Users();
		user.setUserFk(Long.parseLong(paymentApprovalDetailsForUsr.getUserPK()));
		try {

			paymentApprovalDetailsForUsr.setCreatedBy(loggedInUser.getUsername());

			updateChallanDetailsFlagVal = paymentService.updateChallanDetailsForRegisteredID(paymentApprovalDetailsForUsr, loggedInUser);

			if (updateChallanDetailsFlagVal == 0) {
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Error updating payment details.");
			} else {
				ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger().info(
						loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ", Submitted Offline Payment , " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
				String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_SUBMITED) + "";
				Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_SUBMITED);
				Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_SUBMITED);
				/*EmailSMSUtil.insertEmailNSMSForActivitynTestForPayment(ConfigurationConstants.getInstance().getActivtyKeyForName(GenericConstants.PAYMENT_SUBMITED),
						Integer.parseInt(loggedInUser.getDisciplineID()), loggedInUser.getUsername(), loggedInUser.getUsername(), staus, loggedInUser, emailReq, smsReq);
*/
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Payment updated successfully for UserID : " + paymentApprovalDetailsForUsr.getEnrollment_pk());
				addActionMessage("Payment updated successfully for UserID : " + paymentApprovalDetailsForUsr.getEnrollment_pk());
			}

			sessionAttributes.put(SESSION_USER, loggedInUser);

			result = "displayPaymentDetailsForCandidate";

		} catch (Exception ex) {
			throw new GenericException(ex);
		}

		return result;
	}

	/**
	 * Dd payment approval.
	 *
	 * @return the string
	 * @throws Exception
	 *             the exception
	 * 
	 * @author vijaya
	 * @category Online / Offline Payment Approval Date : 2nd July 2012
	 */
	public String ddPaymentApproval() throws Exception {
		// logger.info("ddPaymentApproval()");
		String result = "";
		String message = "";

		disciplineList = paymentService.getDisciplineList(loggedInUser);
		paymentModeList = getPaymentModeList();

		if (paymentModeList != null) {
			candidateMgmtBean.setPaymentModeList(paymentModeList);
		}

		if (disciplineList != null) {
			candidateMgmtBean.setDisciplineList(disciplineList);
		}

		int updateStatus = paymentService.updateDDPaymentApproval(loggedInUser, candidateMgmtBean);

		List<PaymentApprovalBean> paymentApprovalBeanList = null;
		if (updateStatus > 0) {
			String checkValue = null;
			List<String> userPkList = new ArrayList<String>();

			if (candidateMgmtBean.getDdPaymentApprovalList() != null) {
				paymentApprovalBeanList = candidateMgmtBean.getDdPaymentApprovalList();
			}

			if (candidateMgmtBean.getChalanPaymentApprovalList() != null) {
				paymentApprovalBeanList = candidateMgmtBean.getChalanPaymentApprovalList();
			}

			String[] paymentPKNUserPKPair = null;

			if (paymentApprovalBeanList != null) {
				for (PaymentApprovalBean paymentApprovalBean : paymentApprovalBeanList) {
					if (paymentApprovalBean != null) {
						if (paymentApprovalBean.getPaymentCheckedValue() != null && !paymentApprovalBean.getPaymentCheckedValue().get(0).equals("false")) {
							checkValue = paymentApprovalBean.getPaymentCheckedValue().get(0);
							paymentPKNUserPKPair = checkValue.split("\\,");
							if (paymentPKNUserPKPair.length == 2) {
								userPkList.add(paymentPKNUserPKPair[1]);
							}
						}
					}
				}
			}

			for (String userPk : userPkList) {
				if (userPk != null && !userPk.equals("")) {
					UserDetailsBean userDetailsBean = null;
					Users users = new Users();
					users.setUserFk(Long.parseLong(userPk));
					userDetailsBean = candidateMgmtService.getCandidateDisciplineForPayment(users);
					String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED) + "";
					Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
					Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
					/*EmailSMSUtil.insertEmailNSMSForActivitynTestForPayment(ConfigurationConstants.getInstance().getActivtyKeyForName(GenericConstants.PAYMENT_APPROVED),
							Integer.parseInt(userDetailsBean.getOUM_TEST_FK()), loggedInUser.getUsername(), userDetailsBean.getOUM_USER_ID(), staus, users, emailReq, smsReq);*/
				}

			}

			/*
			 * for (String string : checkValueList) { if (string!=null &&
			 * !string.equals("")){ UserDetailsBean userDetailsBean = null;
			 * Users users = new Users();
			 * users.setUserFk(Long.parseLong(string)); userDetailsBean =
			 * candidateMgmtService.getCandidateDisciplineForPayment(users);
			 * String staus = ConfigurationConstants.getInstance().getStatusKey(
			 * GenericConstants.PAYMENT_APPROVED)+""; Integer emailReq =
			 * ConfigurationConstants.getInstance().getEmailValForStatusDesc(
			 * GenericConstants.PAYMENT_APPROVED); Integer smsReq =
			 * ConfigurationConstants.getInstance().getSmsValForStatusDesc(
			 * GenericConstants.PAYMENT_APPROVED);
			 * EmailSMSUtil.insertEmailNSMSForActivitynTestForPayment(
			 * ConfigurationConstants.getInstance().getActivtyKeyForName(
			 * GenericConstants.PAYMENT_APPROVED),Integer.parseInt(
			 * userDetailsBean.getDisciplineId()), loggedInUser.getUsername(),
			 * userDetailsBean.getUserId(),staus,loggedInUser,emailReq,smsReq);
			 * }
			 * 
			 * }
			 */

			message = "Payment approved successfully.";
		} else {
			message = "Unable to approve payment.";
		}

		request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, message);
		request.setAttribute(GenericConstants.MESSAGE_TYPE, GenericConstants.MESSAGE_TYPE_INFORMATION);
		request.setAttribute(GenericConstants.DESTINATION_PATH, "CandidateMgmtAction_papulatePaymentApproval.action");
		result = "displayMessage";

		// return "displayPaymentApproval";
		return result;
	}

	public String ddPaymentRejection() throws Exception {
		// logger.info("ddPaymentRejection()");
		String result = "";
		String message = "";

		disciplineList = paymentService.getDisciplineList(loggedInUser);
		paymentModeList = getPaymentModeList();

		if (paymentModeList != null) {
			candidateMgmtBean.setPaymentModeList(paymentModeList);
		}

		if (disciplineList != null) {
			candidateMgmtBean.setDisciplineList(disciplineList);
		}

		int updateStatus = paymentService.updateDDPaymentRejection(loggedInUser, candidateMgmtBean);
		if (updateStatus > 0) {

			String checkValue = null;
			List<String> userPkList = new ArrayList<String>();
			List<PaymentApprovalBean> paymentApprovalBeanList = null;

			if (candidateMgmtBean.getDdPaymentApprovalList() != null) {
				paymentApprovalBeanList = candidateMgmtBean.getDdPaymentApprovalList();
			}

			if (candidateMgmtBean.getChalanPaymentApprovalList() != null) {
				paymentApprovalBeanList = candidateMgmtBean.getChalanPaymentApprovalList();
			}

			String[] paymentPKNUserPKPair = null;

			if (paymentApprovalBeanList != null) {
				for (PaymentApprovalBean paymentApprovalBean : paymentApprovalBeanList) {
					if (paymentApprovalBean != null) {
						if (paymentApprovalBean.getPaymentCheckedValue() != null && !paymentApprovalBean.getPaymentCheckedValue().get(0).equals("false")) {
							checkValue = paymentApprovalBean.getPaymentCheckedValue().get(0);
							paymentPKNUserPKPair = checkValue.split("\\,");
							if (paymentPKNUserPKPair.length == 2) {
								userPkList.add(paymentPKNUserPKPair[1]);
							}
						}
					}
				}
			}

			for (String userPk : userPkList) {
				if (userPk != null && !userPk.equals("")) {
					UserDetailsBean userDetailsBean = null;
					Users users = new Users();
					users.setUserFk(Long.parseLong(userPk));
					userDetailsBean = candidateMgmtService.getCandidateDisciplineForPayment(users);
					String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED) + "";
					Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_REJECTED);
					Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_REJECTED);
					/*EmailSMSUtil.insertEmailNSMSForActivitynTestForPayment(ConfigurationConstants.getInstance().getActivtyKeyForName(GenericConstants.PAYMENT_REJECTED),
							Integer.parseInt(userDetailsBean.getOUM_TEST_FK()), loggedInUser.getUsername(), userDetailsBean.getOUM_USER_ID(), staus, users, emailReq, smsReq);*/
				}

			}
			message = "Payment rejected successfully.";
		} else {
			message = "Unable to reject payment.";
		}

		request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, message);
		request.setAttribute(GenericConstants.MESSAGE_TYPE, GenericConstants.MESSAGE_TYPE_INFORMATION);
		request.setAttribute(GenericConstants.DESTINATION_PATH, "CandidateMgmtAction_papulatePaymentApproval.action");
		result = "displayMessage";

		// return "displayPaymentApproval";
		return result;
	}

	/**
	 * Gets the payment mode list.
	 *
	 * @return the payment mode list
	 * 
	 * @author vijaya
	 * @category Online / Offline Payment Approval Date : 2nd July 2012
	 */
	public List<String> getPaymentModeList() throws Exception {
		// logger.info("getPaymentModeList()");
		List<String> paymentModeList = new ArrayList<String>();
		paymentModeList.add("Online");
		paymentModeList.add("Challan");
		// paymentModeList.add("DD");
		paymentModeList.add("e-Post");
		return paymentModeList;
	}

	public Map<Integer, String> getPaymentStatusMap() throws Exception {
		Map<Integer, String> paymentStatusMap = new HashMap<Integer, String>();
		paymentStatusMap.put(0, "Rejected");
		paymentStatusMap.put(1, "Approved");
		paymentStatusMap.put(-1, "Pending For Approval");
		paymentStatusMap.put(2, "Payment Not Submitted");
		return paymentStatusMap;
	}

	public Map<Integer, String> getPaymentStatusMapForRep() throws Exception {
		Map<Integer, String> paymentStatusMap = new HashMap<Integer, String>();
		paymentStatusMap.put(1, "Rejected");
		paymentStatusMap.put(2, "Approved");
		paymentStatusMap.put(3, "Payment Not Submitted");
		return paymentStatusMap;
	}

	public Map<Integer, String> getPaymentStatusMapForPayementApproveReject() throws Exception {
		Map<Integer, String> paymentStatusMap = new HashMap<Integer, String>();
		paymentStatusMap.put(0, "Rejected");
		paymentStatusMap.put(1, "Approved");
		paymentStatusMap.put(-1, "Pending For Approval");
		return paymentStatusMap;
	}

	/**
	 * Papulate payment approval.
	 *
	 * @return the string
	 * @throws Exception
	 *             the exception
	 * 
	 * @author vijaya
	 * @category Online / Offline Payment Approval Date : 2nd July 2012
	 */
	public String papulatePaymentApproval() throws Exception {
		// logger.info("papulatePaymentApproval()");
		disciplineList = paymentService.getDisciplineList(loggedInUser);
		paymentModeList = getPaymentModeList();

		candidateMgmtBean.setDisciplineList(disciplineList);
		candidateMgmtBean.setPaymentModeList(paymentModeList);
		candidateMgmtBean.setPaymentStatusMap(getPaymentStatusMapForPayementApproveReject());

		return "papulatePaymentApproval";

	}

	public List<String> getEligibleStatusList() {
		// logger.info("getEligibleStatusList()");
		List<String> eligibleStatusList = new ArrayList<String>();
		eligibleStatusList.add("Eligible");
		eligibleStatusList.add("Non Eligible");

		return eligibleStatusList;
	}

	@Override
	public void resetModel() {
		candidateMgmtBean = new CandidateMgmtBean();
	}

	/**
	 * Server side validation for updateNonEligibleToEligible
	 * 
	 * @author Pankaj Shinde
	 * @return List
	 */
	public List<String> updateNonEligibleToEligibleValidation() {
		// logger.info("updateNonEligibleToEligibleValidation()");
		List<String> errorList = new ArrayList<String>();
		try {
			if (candidateMgmtBean.getPaymentCheckedValue().size() == 0)
				errorList.add("Select candidate to mark non eligible");

			if (candidateMgmtBean.getPaymentCheckedValue().size() > 0) {
				for (int i = 0; i < candidateMgmtBean.getPaymentCheckedValue().size(); i++) {
					if (ValidatorUtil.isEmpty(candidateMgmtBean.getPaymentCheckedValue().get(i)))
						errorList.add("Select candidate to mark non eligible");

					if (errorList.size() > 0)
						break;
				}
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}

		return errorList;
	}

	/**
	 * Server side validation for updateEligibleToNonEligible
	 * 
	 * @author Pankaj Shinde
	 * @return List
	 */
	public List<String> updateEligibleToNonEligibleValidation() {
		// logger.info("updateEligibleToNonEligibleValidation()");
		List<String> errorList = new ArrayList<String>();
		try {
			if (candidateMgmtBean.getPaymentCheckedValue().size() == 0)
				errorList.add("Select candidate to mark non eligible");

			if (ValidatorUtil.isEmpty(candidateMgmtBean.getReason()))
				errorList.add("Type reason to mark non eligible");

			if (candidateMgmtBean.getPaymentCheckedValue().size() > 0) {
				String reason = candidateMgmtBean.getReason();
				String[] strings = reason.split(",");
				List<String> list = Arrays.asList(strings);

				for (int i = 0; i < candidateMgmtBean.getPaymentCheckedValue().size(); i++) {
					if (ValidatorUtil.isEmpty(candidateMgmtBean.getPaymentCheckedValue().get(i)))
						errorList.add("Select candidate to mark non eligible");

					if (ValidatorUtil.isEmpty(list.get(i).trim()))
						errorList.add("Type reason to mark non eligible");

					if (errorList.size() > 0)
						break;
				}
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}

		return errorList;
	}

	public String getCandidateDetailsForUpdation() {
		// logger.info("getCandidateDetailsForUpdation()");
		request.setAttribute("showCandidateDetails", new Boolean(false));
		return "updateCandidateDetails";
	}

	/**
	 * Server side validation for getCandidateDetailsById
	 * 
	 * @return List
	 * @author Pankaj Shinde
	 */
	public List<String> getCandidateDetailsByIdValidation() {
		// logger.info("getCandidateDetailsByIdValidation()");
		List<String> errorList = new ArrayList<String>();
		try {
			if (ValidatorUtil.isEmpty(candidateMgmtBean.getEnrollmentId()))
				errorList.add("Please enter enrollment ID.");

			if (!ValidatorUtil.isNumeric(candidateMgmtBean.getEnrollmentId()))
				errorList.add("Enrollment ID should be numeric");
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return errorList;
	}

	/**
	 * Server side validation for updateCandidateDetails
	 * 
	 * @return List
	 * @author Pankaj Shinde
	 */
	public List<String> updateCandidateDetailsValidation() {
		// logger.info("updateCandidateDetailsValidation()");
		List<String> errorList = new ArrayList<String>();
		try {
			if (ValidatorUtil.isEmpty(candidateMgmtBean.getEnrollmentId()))
				errorList.add("Please select enrollment ID");

			if (!ValidatorUtil.isNumeric(candidateMgmtBean.getEnrollmentId()))
				errorList.add("Enrollment ID should be numeric");

			if (ValidatorUtil.isEmpty(candidateMgmtBean.getUserPK()))
				errorList.add("Please enter user PK");

			if (!ValidatorUtil.isNumeric(candidateMgmtBean.getUserPK()))
				errorList.add("User PK should be numeric");
			/**
			 * commented as fields are disabled
			 */
			/*
			 * if(ValidatorUtil.isEmpty(candidateMgmtBean.getFirstName()))
			 * errorList.add(ResourceUtil.getValidationErrorMessageProperty(
			 * ValidationMessageConstants.ENROLL_FIRST_NAME));
			 * 
			 * if(ValidatorUtil.isEmpty(candidateMgmtBean.getLastName()))
			 * errorList.add(ResourceUtil.getValidationErrorMessageProperty(
			 * ValidationMessageConstants.ENROLL_LAST_NAME));
			 * 
			 * if(ValidatorUtil.isEmpty(candidateMgmtBean.getFatherName()))
			 * errorList.add(ResourceUtil.getValidationErrorMessageProperty(
			 * ValidationMessageConstants.ENROLL_FATHER_NAME));
			 * 
			 * if(ValidatorUtil.isEmpty(candidateMgmtBean.getDateOfBirth()))
			 * errorList.add(ResourceUtil.getValidationErrorMessageProperty(
			 * ValidationMessageConstants.ENROLL_DOB));
			 * 
			 * if(ValidatorUtil.isEmpty(candidateMgmtBean.getGender()))
			 * errorList.add(ResourceUtil.getValidationErrorMessageProperty(
			 * ValidationMessageConstants.ENROLL_GENDER));
			 */

			if (ValidatorUtil.isEmpty(candidateMgmtBean.getAddress()))
				errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.ENROLL_ADDR_NAME));

			if (ValidatorUtil.isEmpty(candidateMgmtBean.getStateListId()))
				errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.ENROLL_STATE));

			if (!ValidatorUtil.isNumeric(candidateMgmtBean.getStateListId()))
				errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.ENROLL_STATE_FK_NUMERIC));

			if (ValidatorUtil.isEmpty(candidateMgmtBean.getCityListId()))
				errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.ENROLL_CITY));

			if (!ValidatorUtil.isNumeric(candidateMgmtBean.getCityListId()))
				errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.ENROLL_CITY_NUMERIC));

			if (ValidatorUtil.isEmpty(candidateMgmtBean.getPincode()))
				errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.ENROLL_PINCODE));

			if (!ValidatorUtil.isNumeric(candidateMgmtBean.getPincode()))
				errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.ENROLL_PINCODE_NUMERIC));

			if (ValidatorUtil.isEmpty(candidateMgmtBean.getEmailAddress()))
				errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.ENROLL_EMAIL_ADD));

			if (ValidatorUtil.isEmpty(candidateMgmtBean.getMobileNO()))
				errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.ENROLL_MOBILE_NO));

			if (!ValidatorUtil.isNumeric(candidateMgmtBean.getMobileNO()))
				errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.ENROLL_MOBILE_NUMERIC));

		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return errorList;
	}

	/**
	 * Server side validation for generateAdmitCard
	 * 
	 * @return String
	 * @author Pankaj Shinde
	 */
	public String generateAdmitCardValidation() {
		// logger.info("generateAdmitCardValidation()");
		String errors = "";
		List<String> errorList = new ArrayList<String>();
		try {
			if (candidateMgmtBean.getUserPKForAdmitCard().size() == 0)
				errorList.add("Please select candidate.");

			if (!errorList.isEmpty())
				errors = ValidatorUtil.validationMessageFormatter(errorList);
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return errors;
	}

	public String getDataForCandidateNodal() {
		// logger.info("getCandidateDataForNodal");
		String menuKey = null;
		String result = null;
		try {
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

	public String getEligibleAndNonEligibleCandidateDataForNodal() {
		String result = null;
		try {
			candidateMgmtBean.setUserId("");
			Map<Integer, List<Long>> dateWindowMap = ConfigurationConstants.getInstance().getDateWindowMap();
			List<Long> dateList = dateWindowMap.get(2);
			Long startDate = dateList.get(0);
			Long endDate = dateList.get(1);

			long today = CommonUtil.getCurrentDateInMillis();

			if ((today == startDate || today > startDate) && (today == endDate || today < endDate)) {
				candidateMgmtBean.setUserId("");
				candidateMgmtBean.setDiscliplineList(ConfigurationConstants.getInstance().getDisciplineMap());
				Map<Integer, String> candidateStatusListMap = new LinkedHashMap<Integer, String>();
				candidateStatusListMap.put(1, "Approved");
				candidateStatusListMap.put(0, "Rejected");
				candidateStatusListMap.put(2, "Pending");
				candidateMgmtBean.setCandidateStatusList(candidateStatusListMap);
				candidateMgmtBean.setFlag("false");
				/*
				 * if(ConfigurationConstants.getInstance().getDisciplineMap() !=
				 * null &&
				 * !ConfigurationConstants.getInstance().getDisciplineMap().
				 * isEmpty()){
				 * candidateMgmtBean.setDisciplineListMap(ConfigurationConstants
				 * .getInstance().getDisciplineMap()); }
				 */
				result = "eligibleForNodal";
			} else {
				candidateMgmtBean.setApprovalStartDate(CommonUtil.formatDate(new Date(startDate), GenericConstants.DATE_FORMAT_DEFAULT));
				candidateMgmtBean.setApprovalEndDate(CommonUtil.formatDate(new Date(endDate), GenericConstants.DATE_FORMAT_DEFAULT));

				result = "showMessage";
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return result;

	}

	public String getEligibleAndNonEligibleCandidateData() {
		// logger.info("getEligibleAndNonEligibleCandidateData()");
		String result = null;

		try {
			candidateMgmtBean.setUserId("");
			Map<Integer, List<Long>> dateWindowMap = ConfigurationConstants.getInstance().getDateWindowMap();
			List<Long> dateList = dateWindowMap.get(2);
			Long startDate = dateList.get(0);
			Long endDate = dateList.get(1);

			long today = CommonUtil.getCurrentDateInMillis();

			if ((today == startDate || today > startDate) && (today == endDate || today < endDate)) {
				candidateMgmtBean.setUserId("");
				result = "eligible";
			} else {
				candidateMgmtBean.setApprovalStartDate(CommonUtil.formatDate(new Date(startDate), GenericConstants.DATE_FORMAT_DEFAULT));
				candidateMgmtBean.setApprovalEndDate(CommonUtil.formatDate(new Date(endDate), GenericConstants.DATE_FORMAT_DEFAULT));

				result = "showMessage";
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}

		return result;
	}

	public String getCandidateData() {
		// logger.info("getCandidateData()");
		String result = null;

		try {

			result = "viewCandidate";
		} catch (Exception e) {
			logger.fatal(e, e);
		}

		return result;
	}

	public String getCandidateDataForNodal() {
		// logger.info("getCandidateDataForNodal()");
		String result = null;

		try {

			result = "viewCandidateForNodal";
		} catch (Exception e) {
			logger.fatal(e, e);
		}

		return result;
	}

	public String getHallTicketData() {
		// logger.info("getHallTicketData()");
		String result = null;

		try {

			Integer attempts = Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.NUMBER_OF_ATTEMPTS));
			if (attempts != null && attempts.equals(new Integer(1))) {
				candidateMgmtBean.setButtonDispFlag("true");
			} else {
				String attmpt2Status = ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.ATTEMPT_2);

				candidateMgmtBean.setAttmpt2Status(attmpt2Status);
			}

			result = "viewHallTicketForAdmin";
		} catch (Exception e) {
			logger.fatal(e, e);
		}

		return result;
	}

	public String getCandidateHallTicketForFirstAttempt() {
		// logger.info("getCandidateHallTicketForFirstAttempt()");
		String result = null;
		List<CandidateMgmtBean> hallTicketDetails = null;
		CandidateMgmtBean newCandidateMgmtBean = null;
		Integer stage = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED);

		try {
			Integer attempts = Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.NUMBER_OF_ATTEMPTS));
			if (attempts != null && attempts.equals(new Integer(1))) {
				candidateMgmtBean.setButtonDispFlag("true");
			} else {
				String attmpt2Status = ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.ATTEMPT_2);

				candidateMgmtBean.setAttmpt2Status(attmpt2Status);
			}
			hallTicketDetails = candidateMgmtService.getCandidateHallTicket(candidateMgmtBean, stage.toString(), 1);
			candidateMgmtBean.setHallticketDisplayFlag("true");

			if (hallTicketDetails != null && !hallTicketDetails.isEmpty()) {
				newCandidateMgmtBean = hallTicketDetails.get(0);
			}

			if (newCandidateMgmtBean != null) {

				candidateMgmtBean.setCandidateReportingTime(newCandidateMgmtBean.getCandidateReportingTime());

				candidateMgmtBean.setTestCenterName(newCandidateMgmtBean.getTestCenterName());

				candidateMgmtBean.setUserIdHallTicket(newCandidateMgmtBean.getUserIdHallTicket());

				candidateMgmtBean.setDuration(newCandidateMgmtBean.getDuration());

				candidateMgmtBean.setEnrollmentIdForHallTicket(newCandidateMgmtBean.getEnrollmentIdForHallTicket());

				candidateMgmtBean.setTestCenterValue(newCandidateMgmtBean.getTestCenterValue());

				candidateMgmtBean.setUserIdForHallticket(newCandidateMgmtBean.getUserIdForHallticket());

				candidateMgmtBean.setAttempt("One");

				candidateMgmtBean.setAttemptVal(newCandidateMgmtBean.getAttemptVal());

				String password = DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).decrypt(newCandidateMgmtBean.getPassword());
				candidateMgmtBean.setPassword(password);

				candidateMgmtBean.setCandidateName(newCandidateMgmtBean.getCandidateName());

				candidateMgmtBean.setCandidateDateOfBirth(newCandidateMgmtBean.getCandidateDateOfBirth());

				candidateMgmtBean.setCandidateTestCenterAddress(newCandidateMgmtBean.getCandidateTestCenterAddress());

				candidateMgmtBean.setCandidateTestDate(newCandidateMgmtBean.getCandidateTestDate());

				candidateMgmtBean.setCandidateTestStartTime(newCandidateMgmtBean.getCandidateTestStartTime());

				candidateMgmtBean.setCandidateTestEndTime(newCandidateMgmtBean.getCandidateTestEndTime());

				candidateMgmtBean.setFirstAttemptFlag("true");

				candidateMgmtBean.setSecondAttemptFlag("false");

				if (ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.GENERATE_ROLL_NUMBER).equals("A")) {
					candidateMgmtBean.setRollNoDisplayFlag("true");
				}

				candidateMgmtBean.setRollNumberForHallTicket(newCandidateMgmtBean.getRollNumberForHallTicket());
				/*
				 * ByteArrayOutputStream byteArrayOutputStream = new
				 * ByteArrayOutputStream(); Code128 barcode = new Code128();
				 * barcode.setData(newCandidateMgmtBean.
				 * getEnrollmentIdForHallTicket());
				 * barcode.drawBarcode(byteArrayOutputStream); byte []
				 * barCodeImageString = byteArrayOutputStream.toByteArray();
				 * 
				 * candidateMgmtBean.setBarCodeImage(barCodeImageString);
				 */

			} else {
				candidateMgmtBean.setHallTicket1Disp("true");
				// candidateMgmtBean.setHallTicket2Disp("false");
			}

			result = "viewHallTicketForAdmin";
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return result;
	}

	public String getCandidateHallTicketForSecondAttempt() {
		// logger.info("getCandidateHallTicketForSecondAttempt()");
		String result = null;
		List<CandidateMgmtBean> hallTicketDetails = null;
		CandidateMgmtBean newCandidateMgmtBean = null;
		Integer stage = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED);

		try {

			Integer attempts = Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.NUMBER_OF_ATTEMPTS));
			if (attempts != null && attempts.equals(new Integer(1))) {
				candidateMgmtBean.setButtonDispFlag("true");
			} else {
				String attmpt2Status = ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.ATTEMPT_2);

				candidateMgmtBean.setAttmpt2Status(attmpt2Status);
			}

			hallTicketDetails = candidateMgmtService.getCandidateHallTicket(candidateMgmtBean, stage.toString(), 2);
			candidateMgmtBean.setHallticketDisplayFlag("true");

			if (hallTicketDetails != null && !hallTicketDetails.isEmpty()) {
				newCandidateMgmtBean = hallTicketDetails.get(0);
			}

			if (newCandidateMgmtBean != null) {

				candidateMgmtBean.setCandidateReportingTime(newCandidateMgmtBean.getCandidateReportingTime());

				candidateMgmtBean.setTestCenterName(newCandidateMgmtBean.getTestCenterName());

				candidateMgmtBean.setEnrollmentIdForHallTicket(newCandidateMgmtBean.getEnrollmentIdForHallTicket());

				candidateMgmtBean.setTestCenterValue(newCandidateMgmtBean.getTestCenterValue());

				candidateMgmtBean.setUserIdForHallticket(newCandidateMgmtBean.getUserIdForHallticket());

				candidateMgmtBean.setCandidateName(newCandidateMgmtBean.getCandidateName());

				candidateMgmtBean.setUserIdHallTicket(newCandidateMgmtBean.getUserIdHallTicket());
				candidateMgmtBean.setAttempt("Two");

				String password = DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).decrypt(newCandidateMgmtBean.getPassword());
				candidateMgmtBean.setPassword(password);

				candidateMgmtBean.setCandidateDateOfBirth(newCandidateMgmtBean.getCandidateDateOfBirth());

				candidateMgmtBean.setCandidateTestCenterAddress(newCandidateMgmtBean.getCandidateTestCenterAddress());

				candidateMgmtBean.setCandidateTestDate(newCandidateMgmtBean.getCandidateTestDate());

				candidateMgmtBean.setCandidateTestStartTime(newCandidateMgmtBean.getCandidateTestStartTime());

				candidateMgmtBean.setCandidateTestEndTime(newCandidateMgmtBean.getCandidateTestEndTime());

				candidateMgmtBean.setSecondAttemptFlag("true");

				candidateMgmtBean.setFirstAttemptFlag("false");

				if (ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.GENERATE_ROLL_NUMBER).equals("A")) {
					candidateMgmtBean.setRollNoDisplayFlag("true");
				}

				candidateMgmtBean.setRollNumberForHallTicket(newCandidateMgmtBean.getRollNumberForHallTicket());
				/*
				 * ByteArrayOutputStream byteArrayOutputStream = new
				 * ByteArrayOutputStream(); Code128 barcode = new Code128();
				 * barcode.setData(newCandidateMgmtBean.
				 * getEnrollmentIdForHallTicket());
				 * barcode.drawBarcode(byteArrayOutputStream); byte []
				 * barCodeImageString = byteArrayOutputStream.toByteArray();
				 * 
				 * candidateMgmtBean.setBarCodeImage(barCodeImageString);
				 */

			} else {
				candidateMgmtBean.setHallTicket2Disp("true");
				// candidateMgmtBean.setHallTicket1Disp("f");
			}

			result = "viewHallTicketForAdmin";
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return result;
	}

	public String getRegistrationDetails() {
		// logger.info("getRegistrationDetails()");
		String result = null;
		Map<Integer, String> discliplineList = new LinkedHashMap<Integer, String>();
		Map<Integer, String> testCenterMasterDetails = null;

		try {
			List<String> testDateList = candidateMgmtService.getTestDates();

			if (testDateList != null && !testDateList.isEmpty()) {
				candidateMgmtBean.setTestDateListForAdminList(testDateList);
			}

			discliplineList = agencyService.getTestMasterDetails();
			testCenterMasterDetails = candidateMgmtService.getTestCenterMasterDetails();

			if (discliplineList != null) {
				candidateMgmtBean.setDiscliplineList(discliplineList);
			}

			if (testCenterMasterDetails != null) {
				candidateMgmtBean.setTestCenterMasterDetails(testCenterMasterDetails);
			}
			candidateMgmtBean.setTestDateListForAdmin(new LinkedHashMap<Integer, String>());

			candidateMgmtBean.setTestSlotListForAdmin(new LinkedHashMap<Integer, String>());

			result = "registrationDetails";

		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return result;
	}

	public String getTestDatesForTestCenter() {
		// logger.info("getTestDatesForTestCenter()");
		try {
			String testCenterValue = candidateMgmtBean.getTestCenterValue();
			List<String> testDates = null;
			String allTestDates = "";
			if (testCenterValue != null && !testCenterValue.equals("")) {
				testDates = candidateMgmtService.getTestDates();

				if (testDates != null && !testDates.isEmpty()) {
					for (String dateValue : testDates) {
						if (dateValue != null) {
							if (dateValue != null && !dateValue.equals("")) {
								allTestDates = allTestDates + dateValue + ",";
							}
						}
					}
				}

				if (allTestDates != null && !allTestDates.equals("")) {
					allTestDates = allTestDates.substring(0, allTestDates.length() - 1);
				}
				if (allTestDates != null && !allTestDates.equals("")) {
					request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, allTestDates);
				} else {
					request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "");
				}

			}

		} catch (Exception e) {
			logger.fatal(e, e);
		}

		return "writePlainText";
	}

	public String getTestSlots() {
		// logger.info("getTestSlots()");
		List<String> timeSolts = null;
		String finalSlots = "";
		try {
			timeSolts = candidateMgmtService.getTestSlots(candidateMgmtBean);

			if (timeSolts != null && !timeSolts.isEmpty()) {
				for (String slotValue : timeSolts) {
					if (slotValue != null) {
						if (slotValue != null && !slotValue.equals("")) {
							finalSlots = finalSlots + slotValue + ",";
						}
					}
				}
			}

			finalSlots = finalSlots.substring(0, finalSlots.length() - 1);
			if (finalSlots != null && !finalSlots.equals("")) {
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, finalSlots);
			} else {
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "");
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}

		return "writePlainText";
	}

	public String getRegistrationDetailsByCandidateId() {
		// logger.info("getRegistrationDetailsByCandidateId()");
		String result = null;
		List<CandidateMgmtBean> candidateDetailsList = null;
		Map<Integer, String> discliplineList = new LinkedHashMap<Integer, String>();
		Map<Integer, String> testCenterMasterDetails = null;
		List<CandidateMgmtBean> testDates = null;
		List<CandidateMgmtBean> testSlots = null;
		try {
			discliplineList = agencyService.getTestMasterDetails();
			testCenterMasterDetails = candidateMgmtService.getTestCenterMasterDetails();
			List<String> testDateList = candidateMgmtService.getTestDates();

			if (testDateList != null && !testDateList.isEmpty()) {
				candidateMgmtBean.setTestDateListForAdminList(testDateList);
			}
			if (discliplineList != null) {
				candidateMgmtBean.setDiscliplineList(discliplineList);
			}

			if (testCenterMasterDetails != null) {
				candidateMgmtBean.setTestCenterMasterDetails(testCenterMasterDetails);
			}
			if (candidateMgmtBean.getUserId() == null || candidateMgmtBean.getUserId().equals("")) {
				/*
				 * if (candidateMgmtBean.getTestCenterValue()!=null &&
				 * !candidateMgmtBean.getTestCenterValue().equals("")){ List
				 * <String> testDateList =
				 * candidateMgmtService.getTestDates(candidateMgmtBean);
				 * 
				 * if (testDateList!=null && !testDateList.isEmpty()){
				 * candidateMgmtBean.setTestDateListForAdminList(testDateList);
				 * } }
				 */
				if (candidateMgmtBean.getTestSlotSelected() != null && !candidateMgmtBean.getTestSlotSelected().equals("")) {
					/*
					 * List <String> testSlotList =
					 * candidateMgmtService.getTestSlots(candidateMgmtBean);
					 * candidateMgmtBean.setTestSlotListForAdminList(
					 * testSlotList);
					 */
				}

				candidateDetailsList = candidateMgmtService.getCandidateDetailsForRegistration(candidateMgmtBean);
			} else {

				CandidateBean newCandidateBean = null;
				Users users = new Users();
				users.setUsername(candidateMgmtBean.getUserId());
				newCandidateBean = null;// candidateService.getCandidateDetailsForAdmin(users);
				if (newCandidateBean == null) {
					candidateMgmtBean.setFlagForNoRecord("true");
				}
				if (newCandidateBean != null && newCandidateBean.getCandidateStage() != null && !newCandidateBean.getCandidateStage().equals("")) {
					if (newCandidateBean.getCandidateStage().equals("0") || newCandidateBean.getCandidateStage().equals("1") || newCandidateBean.getCandidateStage().equals("2")
							|| newCandidateBean.getCandidateStage().equals("3") || newCandidateBean.getCandidateStage().equals("4")
							|| newCandidateBean.getCandidateStage().equals("5") || newCandidateBean.getCandidateStage().equals("6")) {
						candidateMgmtBean.setFlagForNoRecord("true");
					}
				}

				candidateDetailsList = candidateMgmtService.getCandidateCandidateDetailsById(candidateMgmtBean);
			}

			if (candidateDetailsList != null && !candidateDetailsList.isEmpty()) {
				candidateMgmtBean.setCandidateDetailsList(candidateDetailsList);
				candidateMgmtBean.setRegDtlsDisplayFlag("true");

			} else {
				candidateMgmtBean.setRegDtlsDisplayFlag("false");
			}

			result = "registrationDetails";
		} catch (Exception e) {
			logger.fatal(e, e);
		}

		return result;
	}

	public String downloadFile1() {
		// logger.info("downloadFile1()");
		String result = null;
		try {
			String module = "BOOKING_ATTEMPT_1";

			File file = new File("");
			String path = file.getAbsolutePath();
			String downloadPath = null;

			if (module != null) {
				downloadPath = schedulingTestService.getDownloadDetails(module);
			}

			File fileToDownload = new File(path + downloadPath);

			String actualFileName = "";
			File[] listOfFiles = fileToDownload.listFiles();

			if (listOfFiles != null) {
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].getName().startsWith(candidateMgmtBean.getUserId())) {
						actualFileName = listOfFiles[i].toString();
						break;
					}
				}
			}

			if (actualFileName != null && !actualFileName.equals("")) {
				String downloadPath2 = path + downloadPath + "\\" + actualFileName;
				File fileToDownload2 = new File(actualFileName);

				String extn = fileToDownload2.getName();

				int pos = extn.lastIndexOf('.');
				String ext = extn.substring(pos + 1);

				response.setContentType(ext);
				String disHeader = "Attachment; Filename=" + module + ".pdf";
				response.setHeader("Content-Disposition", disHeader);
				response.setHeader("Cache-control", "no-cache, no-store");
				response.setHeader("Pragma", "no-cache");
				response.setHeader("Expires", "-1");
				// response.setHeader( "Content-Disposition", "Attachment;
				// Filename="+actualFileName);

				InputStream in = null;
				ServletOutputStream outs = response.getOutputStream();

				in = new BufferedInputStream(new FileInputStream(fileToDownload2));
				int ch;

				while ((ch = in.read()) != -1) {
					outs.print((char) ch);
				}

				outs.flush();
				outs.close();
				in.close();

				result = SUCCESS;
			} else {
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Sorry, No Downloads Available");
				request.setAttribute(GenericConstants.MESSAGE_TYPE, GenericConstants.MESSAGE_TYPE_ERROR);
				request.setAttribute(GenericConstants.DESTINATION_PATH, "SettingsAction_getUploadManagement.action");
				result = "displayMessage";
			}

		} catch (Exception e) {
			logger.fatal(e, e);
		}

		return result;

	}

	public String downloadFile2() {
		// logger.info("downloadFile2()");
		String result = null;

		try {
			String module = "BOOKING_ATTEMPT_2";

			File file = new File("");
			String path = file.getAbsolutePath();
			String downloadPath = null;

			if (module != null) {
				downloadPath = schedulingTestService.getDownloadDetails(module);
			}

			File fileToDownload = new File(path + downloadPath);

			String actualFileName = "";
			File[] listOfFiles = fileToDownload.listFiles();

			if (listOfFiles != null) {
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].getName().startsWith(candidateMgmtBean.getUserId())) {
						actualFileName = listOfFiles[i].toString();
						break;
					}
				}
			}

			if (actualFileName != null && !actualFileName.equals("")) {
				String downloadPath2 = path + downloadPath + "\\" + actualFileName;
				File fileToDownload2 = new File(actualFileName);

				String extn = fileToDownload2.getName();

				int pos = extn.lastIndexOf('.');
				String ext = extn.substring(pos + 1);

				response.setContentType(ext);
				String disHeader = "Attachment; Filename=" + module + ".pdf";
				response.setHeader("Content-Disposition", disHeader);
				response.setHeader("Cache-control", "no-cache, no-store");
				response.setHeader("Pragma", "no-cache");
				response.setHeader("Expires", "-1");
				// response.setHeader( "Content-Disposition", "Attachment;
				// Filename="+actualFileName);

				InputStream in = null;
				ServletOutputStream outs = response.getOutputStream();

				in = new BufferedInputStream(new FileInputStream(fileToDownload2));
				int ch;

				while ((ch = in.read()) != -1) {
					outs.print((char) ch);
				}

				outs.flush();
				outs.close();
				in.close();

				result = SUCCESS;
			} else {
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Sorry, No Downloads Available");
				request.setAttribute(GenericConstants.MESSAGE_TYPE, GenericConstants.MESSAGE_TYPE_ERROR);
				request.setAttribute(GenericConstants.DESTINATION_PATH, "SettingsAction_getUploadManagement.action");
				result = "displayMessage";
			}

		} catch (Exception e) {
			logger.fatal(e, e);
		}

		return result;

	}

	public String getAllocateTestCanterPage() {
		// logger.info("getAllocateTestCanterPage()");
		String result = null;
		try {
			result = "allocateTestCenter";
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return result;
	}

	public String getAdmitCatdDetails() {
		// logger.info("getAdmitCatdDetails()");
		try {

			getBaseDataForAdmitCard();
			// logger.info("flag "+candidateMgmtBean.getAttemptDropdownFlag());

		} catch (Exception e) {
			LoggerHome.getLogger(getClass()).fatal(e.getMessage(), e);
		}
		return "admitCatdDetails";
	}

	public String getAdmitCardDetailsforWrittenTest() throws Exception {
		// logger.info("getAdmitCardDetailsforWrittenTest()");

		return "admitCardforWrittenTest";
	}

	public String getCallLetterDetailsforFieldTest() throws Exception {
		// logger.info("getCallLetterDetailsforFieldTest()");

		return "admitCardforFieldTest";
	}

	public String getCallLetterDetailsforInterview() throws Exception {
		// logger.info("getCallLetterDetailsforInterview()");

		return "admitCardforInterview";
	}

	public String getCallLetterDetailsforMedicalTest() throws Exception {
		// logger.info("getCallLetterDetailsforMedicalTest()");

		return "admitCardforMedicalTest";
	}

	public String getRollNumberGenerationPage() {
		// logger.info("getRollNumberGenerationPage()");
		String result = null;
		try {
			// candidateMgmtBean.setRollNumberSuccFlag("false");
			candidateMgmtBean.setRollNumberSuccFlag("false");
			result = "generateRollNumber";
		} catch (Exception e) {
			LoggerHome.getLogger(getClass()).fatal(e.getMessage(), e);
		}
		return result;
	}

	public String generateRollNumbers() {
		// logger.info("generateRollNumbers()");
		String result = null;
		String output = null;
		int updateCount = 0;
		try {
			output = candidateMgmtService.generateRollNumberForCandidates();
			if (output != null && !output.equals("") && output.equals("Successful")) {
				candidateMgmtBean.setRollNumberSuccFlag("true");
				candidateMgmtBean.setRollNumberSuccMsg("Roll Numbers Successfully Generated");
			}
			if (output != null && !output.equals("") && output.equals("Error")) {
				candidateMgmtBean.setRollNumberSuccFlag("true");
				candidateMgmtBean.setRollNumberSuccMsg("Roll Number Generation Failed");
			}

			System.out.println("output     " + output);
			result = "generateRollNumber";
		} catch (Exception e) {
			LoggerHome.getLogger(getClass()).fatal(e.getMessage(), e);
		}
		return result;
	}

	public String getAdmitCardList() {
		String result = null;
		List<HallTicketBean> candidateListForAdmitCard = null;
		try {
			result = "admitCatdDetails";
			int totalNumberOfRecord = 0;
			if (candidateMgmtBean.getUserId() == null || candidateMgmtBean.getUserId().equals("")) {
				/**
				 * @author shailendras Pagination Code: set total number of
				 *         record
				 */
				totalNumberOfRecord = candidateMgmtService.getCandidateDataForAdmitCardForSearchCount(candidateMgmtBean);
				pagination.setProperties(totalNumberOfRecord);

				candidateListForAdmitCard = candidateMgmtService.getCandidateDataForAdmitCardForSearch(candidateMgmtBean, pagination);
			} else {
				totalNumberOfRecord = 1;
				pagination.setProperties(totalNumberOfRecord);
				candidateListForAdmitCard = candidateMgmtService.getCandidateDataForAdmitCard(candidateMgmtBean);
			}

			getBaseDataForAdmitCard();

			if (candidateListForAdmitCard != null && !candidateListForAdmitCard.isEmpty()) {
				/**
				 * @author shailendras Pagination Code: set the page record that
				 *         should be show to end user
				 */
				pagination.setPage_records(candidateListForAdmitCard.size());
				candidateMgmtBean.setAdmitCardRecordCount(totalNumberOfRecord);
				candidateMgmtBean.setCandidateListForAdmitCard(candidateListForAdmitCard);
				candidateMgmtBean.setAdmitCardHeaderDisplayFlag("true");
				candidateMgmtBean.setAdmitCardDisplayFlag("true");
			} else {
				candidateMgmtBean.setAdmitCardDisplayFlag("true");
				candidateMgmtBean.setCandidateListForAdmitCard(null);
			}
		} catch (Exception e) {
			LoggerHome.getLogger(getClass()).fatal(e.getMessage(), e);
		}
		return result;
	}

	public String generateAdmitCard() {
		String result = null;
		int[] cadidateAdmitCardCount = null;
		try {
			getBaseDataForAdmitCard();

			List<String> enrollmentList = candidateMgmtBean.getEnrollmentPKForAdmitCard();
			if (enrollmentList != null && !enrollmentList.isEmpty() && !enrollmentList.contains(new String("false"))) {
				cadidateAdmitCardCount = candidateMgmtService.updateGenerateHallticket(enrollmentList, loggedInUser);
			} else {
				addActionMessage("Please select atleast one checkbox");
				getAdmitCardList();
				result = "admitCatdDetails";
			}

			if (cadidateAdmitCardCount != null && cadidateAdmitCardCount.length > 0) {
				UserDetailsBean userDetailsBean = null;
				Users users = new Users();
				for (String enrollmentPk : enrollmentList) {
					if (enrollmentPk != null && !enrollmentPk.equals("")) {
						userDetailsBean = candidateMgmtService.getUserDetailsForEnrollmentId(enrollmentPk);
						if (userDetailsBean != null && userDetailsBean.getOED_ATTEMPT().equals(new Integer(1))) {
							users.setUsername(loggedInUser.getUsername());
							users.setUserId(Long.parseLong(userDetailsBean.getOUM_USER_PK()));
							int update = commonService.updateCandidateStatus(users,
									ConfigurationConstants.getInstance().getStatusKey(GenericConstants.HALL_TICKET_GENERATED_FOR_ATTEMPT_1));
							if (update > 0) {
								String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.HALL_TICKET_GENERATED_FOR_ATTEMPT_1) + "";
								Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.HALL_TICKET_GENERATED_FOR_ATTEMPT_1);
								Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.HALL_TICKET_GENERATED_FOR_ATTEMPT_1);
								/*EmailSMSUtil.insertEmailNSMSForActivitynTestForAdmitCard(
										ConfigurationConstants.getInstance().getActivtyKeyForName(GenericConstants.HALL_TICKET_GENERATED_FOR_ATTEMPT_1),
										Integer.parseInt(userDetailsBean.getOUM_TEST_FK()), loggedInUser.getUsername(), userDetailsBean.getOUM_USER_ID(), staus, loggedInUser,
										emailReq, smsReq, new Integer(1));*/
							}

						}
						if (userDetailsBean != null && userDetailsBean.getOED_ATTEMPT().equals(new Integer(2))) {
							users.setUsername(loggedInUser.getUsername());
							users.setUserId(Long.parseLong(userDetailsBean.getOUM_USER_PK()));
							int update = commonService.updateCandidateStatus(users,
									ConfigurationConstants.getInstance().getStatusKey(GenericConstants.HALL_TICKET_GENERATED_FOR_ATTEMPT_2));
							if (update > 0) {
								String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.HALL_TICKET_GENERATED_FOR_ATTEMPT_2) + "";
								Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.HALL_TICKET_GENERATED_FOR_ATTEMPT_2);
								Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.HALL_TICKET_GENERATED_FOR_ATTEMPT_2);
								/*EmailSMSUtil.insertEmailNSMSForActivitynTestForAdmitCard(
										ConfigurationConstants.getInstance().getActivtyKeyForName(GenericConstants.HALL_TICKET_GENERATED_FOR_ATTEMPT_2),
										Integer.parseInt(userDetailsBean.getOUM_TEST_FK()), loggedInUser.getUsername(), userDetailsBean.getOUM_USER_ID(), staus, loggedInUser,
										emailReq, smsReq, new Integer(2));*/
							}
						}

					}
				}

				// List <Long> userFkList =
				// candidateMgmtBean.getUserFKForCandidate();

				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Admit Card generated successfully.");
				request.setAttribute(GenericConstants.MESSAGE_TYPE, GenericConstants.MESSAGE_TYPE_INFORMATION);
				request.setAttribute(GenericConstants.DESTINATION_PATH, "CandidateMgmtAction_getAdmitCatdDetails.action");
				result = "displayMessage";
			} else {
				/*
				 * addActionMessage("Please select atleast one checkbox");
				 * getAdmitCardList(); result = "admitCatdDetails";
				 * request.setAttribute(
				 * GLOBAL_PLAIN_TEXT_MESSAGE,"Error in Admit Card Generation.");
				 * request.setAttribute(GenericConstants.MESSAGE_TYPE,
				 * GenericConstants.MESSAGE_TYPE_INFORMATION);
				 * request.setAttribute(GenericConstants.DESTINATION_PATH,
				 * "CandidateMgmtAction_getAdmitCatdDetails.action"); result =
				 * "displayMessage";
				 */
			}

		} catch (Exception e) {
			LoggerHome.getLogger(getClass()).fatal(e.getMessage(), e);
		}
		return result;

	}

	private void getBaseDataForAdmitCard() {
		Map<Integer, String> discliplineListMap = new LinkedHashMap<Integer, String>();
		Map<Integer, String> testCenterListMap = new LinkedHashMap<Integer, String>();
		Integer attempts = null;
		List<String> attemptsList = new ArrayList<String>();

		Map<Integer, String> attemptsListMap = new LinkedHashMap<Integer, String>();

		discliplineListMap = ConfigurationConstants.getInstance().getDisciplineMap();
		testCenterListMap = ConfigurationConstants.getInstance().getTestCenterMasterMap();

		if (discliplineListMap != null && !discliplineListMap.isEmpty()) {
			candidateMgmtBean.setDisciplineListMap(discliplineListMap);
		}

		if (testCenterListMap != null && !testCenterListMap.isEmpty()) {
			candidateMgmtBean.setTestCenterListMap(testCenterListMap);
		}

		attempts = Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.NUMBER_OF_ATTEMPTS));
		int attemptsCount = 1;

		if (attempts != null && !attempts.equals("")) {
			if (attempts.equals(new Integer(1))) {
				candidateMgmtBean.setAttemptDropdownFlag("false");
			} else {
				for (int i = 0; i < attempts; i++) {
					StringBuilder sb = new StringBuilder();
					// attemptsList.add(sb.append("Attempt").append(attemptsCount).toString());
					attemptsListMap.put(attemptsCount, sb.append("Attempt").append(attemptsCount).toString());
					attemptsCount++;
				}

				if (attemptsListMap != null && !attemptsListMap.isEmpty()) {
					candidateMgmtBean.setAttemptsListMap(attemptsListMap);
				}
				candidateMgmtBean.setAttemptDropdownFlag("true");
			}

		}

	}

	public String getAllocateTestSlotPage() {
		// logger.info("getAllocateTestSlotPage()");
		String result = null;
		try {
			result = "allocateTestSlot";
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return result;
	}

	public String challanReconciliationScreen() throws Exception {
		// logger.info("method challanReconciliationScreen is calling");
		String guidelinesMsg = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.GUIDELINES_CHALLAN_RECONCILIATION);

		if (guidelinesMsg != null && !guidelinesMsg.equals("")) {
			candidateMgmtBean.setGuidelinesMsg(guidelinesMsg);
		}
		return "showChallanReconcile";
	}

	public String ePostReconciliationScreen() throws Exception {
		// logger.info("method ePostReconciliationScreen is calling");
		String guidelinesMsg = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.GUIDELINES_CHALLAN_RECONCILIATION);

		if (guidelinesMsg != null && !guidelinesMsg.equals("")) {
			candidateMgmtBean.setGuidelinesMsg(guidelinesMsg);
		}
		return "showEPostReconcile";
	}

	public String getExecutionTime(long timeMillis) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		return sdf.format(timeMillis);
	}

	public String showPaymentReportScreen() throws Exception {
		// logger.info("showPaymentReportScreen()");
		disciplineList = paymentService.getDisciplineList(loggedInUser);

		Map<Integer, String> paymentModeTypeMap = ConfigurationConstants.getInstance().getPaymentMasterMap();

		paymentModeReportList = paymentService.getPaymentModeList();

		candidateMgmtBean.setDisciplineList(disciplineList);
		candidateMgmtBean.setPaymentModeReportList(paymentModeReportList);
		candidateMgmtBean.setPaymentStatusMap(getPaymentStatusMapForRep());

		return "showPaymentReportScreen";

	}

	public String showPaymentReportSearchResult() throws Exception {
		String result = "showPaymentReportScreen";
		List<CandidateMgmtBean> paymentReportResultBeanList = null;

		Map<Integer, String> paymentModeTypeMap = ConfigurationConstants.getInstance().getPaymentMasterMap();
		disciplineList = paymentService.getDisciplineList(loggedInUser);
		paymentModeReportList = paymentService.getPaymentModeList();

		if (disciplineList != null && !disciplineList.isEmpty()) {
			candidateMgmtBean.setDisciplineList(disciplineList);
		}
		if (paymentModeReportList != null && !paymentModeReportList.isEmpty()) {
			candidateMgmtBean.setPaymentModeReportList(paymentModeReportList);
		}
		candidateMgmtBean.setPaymentStatusMap(getPaymentStatusMapForRep());

		int totalNumberOfRecord = 0;
		if (candidateMgmtBean.getPaymentStatus() != null && candidateMgmtBean.getPaymentStatus().equals("3")) {
			candidateMgmtBean.setPaymentModeValue("Pending For Approval");
		}
		if (candidateMgmtBean.getPaymentModeValue() != null && !candidateMgmtBean.getPaymentModeValue().equals("")
				&& candidateMgmtBean.getPaymentModeValue().equalsIgnoreCase("Payment Not Submitted")) {
			candidateMgmtBean.setPaymentDetailsDisplayFlag("true");
			// For Payment Not Submitted
			totalNumberOfRecord = candidateMgmtService.getCandidateDetailsForPaymentNotSubmittedCount(candidateMgmtBean);
			pagination.setProperties(totalNumberOfRecord);

			paymentReportResultBeanList = candidateMgmtService.getCandidateDetailsForPaymentNotSubmitted(candidateMgmtBean, pagination);
			if (paymentReportResultBeanList != null && !paymentReportResultBeanList.isEmpty()) {
				candidateMgmtBean.setPaymentReportResultBeanList(paymentReportResultBeanList);
				pagination.setPage_records(paymentReportResultBeanList.size());
			}
			candidateMgmtBean.setShowPaymentReportSearchFlag("TRUE");
		} else {
			totalNumberOfRecord = candidateMgmtService.getPaymentReportSearchResultCount(candidateMgmtBean);
			pagination.setProperties(totalNumberOfRecord);
			paymentReportResultBeanList = candidateMgmtService.getPaymentReportSearchResult(candidateMgmtBean, pagination);

			if (paymentReportResultBeanList != null && !paymentReportResultBeanList.isEmpty()) {
				candidateMgmtBean.setPaymentReportResultBeanList(paymentReportResultBeanList);
				pagination.setPage_records(paymentReportResultBeanList.size());
			}
			candidateMgmtBean.setShowPaymentReportSearchFlag("TRUE");
			candidateMgmtBean.setPaymentDetailsDisplayFlag("false");
		}
		if (paymentReportResultBeanList != null) {
			candidateMgmtBean.setPaymentReportResultBeanListSize(totalNumberOfRecord);
		} else {
			candidateMgmtBean.setPaymentReportResultBeanListSize(0);
		}

		if (candidateMgmtBean.getMenuKey() != null) {
			request.setAttribute("menuKey", candidateMgmtBean.getMenuKey());
		}

		return result;
	}

	public void generatePaymentReport() throws Exception {
		// logger.info("generatePaymentReport()");
		String result = "showPaymentReportScreen";
		List<CandidateMgmtBean> paymentReportResultBeanList = null;
		;
		try {
			String paymentMode = candidateMgmtBean.getPaymentModeValue();
			if (paymentMode.equals("")) {
				paymentReportResultBeanList = candidateMgmtService.getCandidateDetailsForPaymentNotSubmitted(candidateMgmtBean);
				generateExcelFileForPaymentNotSubmitted(paymentReportResultBeanList);
			} else if (paymentMode.equals(GenericConstants.CHALLAN)) {
				candidateMgmtBean.setPaymentMode(ConfigurationConstants.getInstance().getPaymentKeyByDesc(GenericConstants.CHALLAN).toString());
				paymentReportResultBeanList = candidateMgmtService.getPaymentReportSearchResult(candidateMgmtBean);
				if (paymentReportResultBeanList != null) {
					generateExcelFileForChallan(paymentReportResultBeanList);
				}
			} else if (paymentMode.equals(GenericConstants.EPOST)) {
				candidateMgmtBean.setPaymentMode(ConfigurationConstants.getInstance().getPaymentKeyByDesc(GenericConstants.EPOST).toString());
				paymentReportResultBeanList = candidateMgmtService.getPaymentReportSearchResult(candidateMgmtBean);
				if (paymentReportResultBeanList != null) {
					generateExcelFileForChallan(paymentReportResultBeanList);
				}
			} else if (paymentMode.equals(GenericConstants.DEMAND_DRAFT)) {
				candidateMgmtBean.setPaymentMode(ConfigurationConstants.getInstance().getPaymentKeyByDesc(GenericConstants.DEMAND_DRAFT).toString());
				paymentReportResultBeanList = candidateMgmtService.getPaymentReportSearchResult(candidateMgmtBean);
				if (paymentReportResultBeanList != null) {
					generateExcelFileForDemandDraft(paymentReportResultBeanList);
				}
			} else if (paymentMode.equals(GenericConstants.CREDIT_CARD)) {
				candidateMgmtBean.setPaymentMode(ConfigurationConstants.getInstance().getPaymentKeyByDesc(GenericConstants.CREDIT_CARD).toString());
				paymentReportResultBeanList = candidateMgmtService.getPaymentReportSearchResult(candidateMgmtBean);
				if (paymentReportResultBeanList != null) {
					generateExcelFileForOnlinePayment(paymentReportResultBeanList);
				}
			} else if (paymentMode.equals(GenericConstants.NET_BANKING)) {
				candidateMgmtBean.setPaymentMode(ConfigurationConstants.getInstance().getPaymentKeyByDesc(GenericConstants.NET_BANKING).toString());
				paymentReportResultBeanList = candidateMgmtService.getPaymentReportSearchResult(candidateMgmtBean);
				if (paymentReportResultBeanList != null) {
					generateExcelFileForOnlinePayment(paymentReportResultBeanList);
				}
			} else if (paymentMode.equals(GenericConstants.ONLINE)) {
				candidateMgmtBean.setPaymentMode(ConfigurationConstants.getInstance().getPaymentKeyByDesc(GenericConstants.ONLINE).toString());
				paymentReportResultBeanList = candidateMgmtService.getPaymentReportSearchResult(candidateMgmtBean);
				if (paymentReportResultBeanList != null) {
					generateExcelFileForOnlinePayment(paymentReportResultBeanList);
				}
			}
			// showPaymentReportScreen();

		} catch (Exception e) {
			logger.fatal(e, e);
		}

		// return result;
	}

	public String generateExcelFileForOnlinePayment(List<CandidateMgmtBean> paymentDetailsList) throws Exception {
		// logger.info("generateExcelFile(List<CandidateMgmtBean>
		// paymentDetailsList) "+paymentDetailsList);
		try {
			ExcelCreator excelCreator = new ExcelCreator();
			HSSFWorkbook workbook = excelCreator.createWorkbookForOnlinePayment(paymentDetailsList);
			response.setHeader("Content-Disposition", "attachment; filename=PaymentReport.xls");
			response.setHeader("Cache-control", "no-cache, no-store");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Expires", "-1");
			ServletOutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Throwable e) {
			logger.fatal(e, e);
		}
		return null;
	}

	public String generateExcelFileForDemandDraft(List<CandidateMgmtBean> paymentDetailsList) throws Exception {
		// logger.info("generateExcelFileForDemandDraft(List<CandidateMgmtBean>
		// paymentDetailsList) "+paymentDetailsList);
		try {
			ExcelCreator excelCreator = new ExcelCreator();
			HSSFWorkbook workbook = excelCreator.createWorkbookForDemandDraft(paymentDetailsList);
			response.setHeader("Content-Disposition", "attachment; filename=PaymentReport.xls");
			response.setHeader("Cache-control", "no-cache, no-store");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Expires", "-1");
			ServletOutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Throwable e) {
			logger.fatal(e, e);
		}
		return "showPaymentReportScreen";
	}

	public String generateExcelFileForChallan(List<CandidateMgmtBean> paymentDetailsList) throws Exception {
		// logger.info("generateExcelFileForChallan(List<CandidateMgmtBean>
		// paymentDetailsList) "+paymentDetailsList);
		try {
			ExcelCreator excelCreator = new ExcelCreator();
			HSSFWorkbook workbook = excelCreator.createWorkbookForChallan(paymentDetailsList);
			response.setHeader("Content-Disposition", "attachment; filename=PaymentReport.xls");
			response.setHeader("Cache-control", "no-cache, no-store");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Expires", "-1");
			ServletOutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Throwable e) {
			logger.fatal(e, e);
		}
		return "showPaymentReportScreen";
	}

	public String generateExcelFileForPaymentNotSubmitted(List<CandidateMgmtBean> paymentDetailsList) throws Exception {
		// logger.info("generateExcelFile(List<CandidateMgmtBean>
		// paymentDetailsList) "+paymentDetailsList);
		try {
			ExcelCreator excelCreator = new ExcelCreator();
			HSSFWorkbook workbook = excelCreator.createWorkbookForPaymentNotsubmitted(paymentDetailsList);
			response.setHeader("Content-Disposition", "attachment; filename=PaymentReport.xls");
			response.setHeader("Cache-control", "no-cache, no-store");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Expires", "-1");
			ServletOutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Throwable e) {
			logger.fatal(e, e);
		}
		return "showPaymentReportScreen";
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
