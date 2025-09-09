package com.nseit.payment.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.nseit.generic.dao.CommonDao;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.CandidateMgmtBean;
import com.nseit.generic.models.CommonBean;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.PaymentApprovalBean;
import com.nseit.generic.models.PaymentBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.GenericServiceException;
import com.nseit.generic.util.LoggerHome;
import com.nseit.payment.dao.PaymentDAO;
import com.nseit.payment.models.AtomResponseBean;
import com.nseit.payment.models.OesBankMaster;
import com.nseit.payment.models.PaymentOnlineBean;
import com.nseit.payment.models.SBIResponseBean;
import com.nseit.payment.models.SafexResponseBean;
import com.nseit.payment.models.TechProcessResponseBean;
import com.nseit.payment.service.PaymentService;
import com.tp.pg.util.TransactionRequestBean;

public class PaymentServiceImpl implements PaymentService {

	public PaymentDAO paymentDAO;
	public CommonDao commonDao;
	private Logger logger = LoggerHome.getLogger(getClass());

	public void setPaymentDAO(PaymentDAO paymentDAO) {
		this.paymentDAO = paymentDAO;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public List<OesBankMaster> getBankList() throws Exception {
		return paymentDAO.getBankList();
	}

	public String getFeesDetail(String userId) throws Exception {
		return paymentDAO.getFeesDetail(userId);
	}

	public List<PaymentBean> getBankDetails() throws Exception {
		List<PaymentBean> bankNameList = null;
		try {
			bankNameList = paymentDAO.getBankDetails();
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return bankNameList;
	}

	public List<PaymentBean> getCityDetails() throws Exception {
		List<PaymentBean> cityNameList = null;
		try {
			cityNameList = paymentDAO.getCityDetails();
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return cityNameList;
	}

	public List<PaymentBean> getOnlinePaymentDetails(Users users) throws Exception {
		List<PaymentBean> onLinePaymentDetailsList = null;
		try {
			onLinePaymentDetailsList = paymentDAO.getOnlinePaymentDetails(users);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return onLinePaymentDetailsList;

	}

	public PaymentBean getDemandDraftDetailsForEnrollment(Users users) throws Exception {
		PaymentBean paymentBean = null;
		try {
			paymentBean = paymentDAO.getDemandDraftDetailsForEnrollment(users);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return paymentBean;

	}

	public PaymentBean getChallanDetailsForEnrollment(Users users) throws Exception {
		PaymentBean paymentBean = null;
		try {
			paymentBean = paymentDAO.getChallanDetailsForEnrollment(users);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return paymentBean;

	}

	/**
	 * @author Pankaj
	 */
	public int isDDExist(PaymentOnlineBean paymentBean, Users loggedInUser) throws Exception {
		int existingDD = 0;
		try {
			existingDD = paymentDAO.isDDExist(paymentBean, loggedInUser);
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
		return existingDD;
	}

	public int isChallanExist(PaymentOnlineBean paymentBean, Users loggedInUser) throws Exception {
		int existingChallan = 0;
		try {
			existingChallan = paymentDAO.isChallanExist(paymentBean, loggedInUser);
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
		return existingChallan;
	}

	public int insertChallanDetails(PaymentOnlineBean paymentBean, Users users) throws Exception {
		int insertChallanDetailsFlagVal = 0;
		try {
			insertChallanDetailsFlagVal = paymentDAO.insertChallanDetails(paymentBean, users);
			if (insertChallanDetailsFlagVal > 0) {
				int updateStage = commonDao.updateCandidateStatus(users, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_SUBMITED));

				if (updateStage > 0) {
					users.setStage(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_SUBMITED) + "");
					users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_SUBMITED));
				}
			}
		} catch (Exception e) {
			throw new GenericException(e);

		}

		return insertChallanDetailsFlagVal;
	}

	public int updateChallanDetails(PaymentOnlineBean paymentBean, Users users) throws Exception {
		int updateChallanDetailsFlagVal = 0;
		try {
			updateChallanDetailsFlagVal = paymentDAO.updateChallanDetails(paymentBean, users);
			if (updateChallanDetailsFlagVal > 0) {
				int updateStage = commonDao.updateCandidateStatus(users, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_SUBMITED));

				if (updateStage > 0) {
					users.setStage(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_SUBMITED) + "");
					users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_SUBMITED));

				}
			}

		} catch (Exception e) {
			throw new GenericException(e);
		}

		return updateChallanDetailsFlagVal;
	}

	@Override
	public int insertChallanBarcodeDetails(CandidateBean candidateBean) throws Exception {
		int insertChallanBarcodeDetailsFlagVal = 0;
		try {
			insertChallanBarcodeDetailsFlagVal = paymentDAO.insertChallanBarcodeDetails(candidateBean);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return insertChallanBarcodeDetailsFlagVal;
	}

	public int insertDDDetails(PaymentOnlineBean paymentBean, Users users) throws Exception {
		int insertDDDetailsFlagVal = 0;
		try {
			insertDDDetailsFlagVal = paymentDAO.insertDDDetails(paymentBean, users);
			if (insertDDDetailsFlagVal > 0) {
				int updateStage = commonDao.updateCandidateStatus(users, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_SUBMITED));

				if (updateStage > 0) {
					users.setStage(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_SUBMITED) + "");
					users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_SUBMITED));
				}
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return insertDDDetailsFlagVal;
	}

	public int updateDDDetailsForRegisteredID(PaymentApprovalBean paymentApprovalBean, Users users) throws Exception {
		int insertDDDetailsFlagVal = 0;
		try {
			insertDDDetailsFlagVal = paymentDAO.updateDDDetailsForRegisteredID(paymentApprovalBean, users);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return insertDDDetailsFlagVal;
	}

	public int updateChallanDetailsForRegisteredID(PaymentApprovalBean paymentApprovalBean, Users users) throws Exception {
		int updateChallanDetailsFlagVal = 0;
		try {
			updateChallanDetailsFlagVal = paymentDAO.updateChallanDetailsForRegisteredID(paymentApprovalBean, users);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return updateChallanDetailsFlagVal;
	}

	public int updateDDDetails(PaymentOnlineBean paymentBean, Users users) throws Exception {
		int updateDDDetailsFlagVal = 0;
		try {
			updateDDDetailsFlagVal = paymentDAO.updateDDDetails(paymentBean, users);
			if (updateDDDetailsFlagVal > 0) {
				int updateStage = commonDao.updateCandidateStatus(users, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_SUBMITED));

				if (updateStage > 0) {
					users.setStage(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_SUBMITED) + "");
					users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_SUBMITED));
				}
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return updateDDDetailsFlagVal;
	}

	public int insertOnlinepaymentDetails(PaymentOnlineBean paymentBean, Users users) throws Exception {
		int insertOnlinepaymentFlagVal = 0;
		try {
			insertOnlinepaymentFlagVal = paymentDAO.insertOnlinepaymentDetails(paymentBean);
		} catch (Exception e) {

			throw new GenericException(e);
		}
		return insertOnlinepaymentFlagVal;
	}

	public String getFeesForOnlinePayment(long getFeesForOnlinePayment) {
		String fees = null;
		try {
			fees = paymentDAO.getFeesForOnlinePayment(getFeesForOnlinePayment);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return fees;
	}

	public int insertpagedetails(PaymentBean paymentBean, String enrollmentpk, Users loggedInUsers) throws Exception {
		int insertpagedetails = 0;
		try {
			insertpagedetails = paymentDAO.insertpagedetails(paymentBean, enrollmentpk, loggedInUsers);

			if (insertpagedetails > 0 && paymentBean.getPAID() != null && !paymentBean.getPAID().equals("") && paymentBean.getPAID().equals("Y")) {
				Integer stageVal = null;
				stageVal = ConfigurationConstants.getInstance().getActivtyKeyForName(GenericConstants.CANDIDATE_PAYMENT_APPROVAL);
			}

		} catch (Exception e) {
			throw new GenericException(e);
		}
		return insertpagedetails;
	}

	public String getBankCodeForChallanReceipt(Users loggedInUser) throws Exception {
		String bankFK = null;
		try {
			bankFK = paymentDAO.getBankCodeForChallanReceipt(loggedInUser);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return bankFK;
	}

	public PaymentBean getBankLogoImage(String bankID) throws Exception {

		PaymentBean newPaymentBean = null;
		try {
			newPaymentBean = paymentDAO.getBankLogoImage(bankID);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return newPaymentBean;
	}

	public List<CommonBean> getBankDetails(Users logUser) throws Exception {
		List<CommonBean> bankList = null;
		try {
			bankList = paymentDAO.getBankDetails(logUser);
		} catch (Exception ex) {
			throw new GenericServiceException(ex);
		}
		return bankList;
	}

	public List<CommonBean> getCityDetails(Users users) throws Exception {

		List<CommonBean> cityList = null;

		try {
			cityList = paymentDAO.getCityDetails(users);
		} catch (Exception ex) {
			throw new GenericServiceException(ex);
		}
		return cityList;
	}

	public boolean isStageActive(String stageId) throws Exception {
		return paymentDAO.isStageActive(stageId);
	}

	public CandidateBean getCandidateDetailsForPayment(Users loggedInUser, int id) throws Exception {
		CandidateBean newCandidateBean = null;
		try {
			newCandidateBean = paymentDAO.getCandidateDetailsForPayment(loggedInUser, id);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return newCandidateBean;
	}

	public List<CommonBean> getDisciplineList(Users loggedInUser) throws Exception {
		List<CommonBean> discilineList = null;

		try {
			discilineList = paymentDAO.getDisciplineList();
		} catch (Exception e) {

			throw new GenericException(e);
		}
		return discilineList;
	}

	public List<CommonBean> getPaymentModeList() throws Exception {
		List<CommonBean> paymentModeList = null;

		try {
			paymentModeList = paymentDAO.getPaymentModeList();
		} catch (Exception e) {

			throw new GenericException(e);
		}
		return paymentModeList;
	}

	public int getDDDetailsCountForPaymentApproval(CandidateMgmtBean candidateMgmtBean) throws Exception {
		int ddPaymentApprovalCount = 0;
		try {
			ddPaymentApprovalCount = paymentDAO.getDDDetailsCountForPaymentApproval(candidateMgmtBean);
		} catch (Exception e) {
			throw new GenericException(e);
		}

		return ddPaymentApprovalCount;
	}

	public List<PaymentApprovalBean> getDDDetailsForPaymentApproval(CandidateMgmtBean candidateMgmtBean, Pagination pagination) throws Exception {
		List<PaymentApprovalBean> ddPaymentApprovalList = null;
		try {
			ddPaymentApprovalList = paymentDAO.getDDDetailsForPaymentApproval(candidateMgmtBean, pagination);
		} catch (Exception e) {
			throw new GenericException(e);
		}

		return ddPaymentApprovalList;
	}

	public PaymentOnlineBean getPaymentDetails(Users users) throws Exception {
		PaymentOnlineBean paymentList = null;
		try {
			paymentList = paymentDAO.getPaymentDetails(users);
		} catch (Exception e) {
			throw new GenericException(e);
		}

		return paymentList;
	}

	@Override
	public int getCountForChalanDetailsForPaymentApproval(CandidateMgmtBean candidateMgmtBean) throws Exception {
		int ddPaymentApprovalCount = 0;
		try {
			ddPaymentApprovalCount = paymentDAO.getCountForChalanDetailsForPaymentApproval(candidateMgmtBean);
		} catch (Exception e) {
			throw new GenericException(e);
		}

		return ddPaymentApprovalCount;
	}

	public int getCountForEPostDetailsForPaymentApproval(CandidateMgmtBean candidateMgmtBean) throws Exception {
		int ddPaymentApprovalCount = 0;
		try {
			ddPaymentApprovalCount = paymentDAO.getCountForEPostDetailsForPaymentApproval(candidateMgmtBean);
		} catch (Exception e) {
			throw new GenericException(e);
		}

		return ddPaymentApprovalCount;
	}

	@Override
	public List<PaymentApprovalBean> getChalanDetailsForPaymentApproval(CandidateMgmtBean candidateMgmtBean, Pagination pagination) throws Exception {
		List<PaymentApprovalBean> ddPaymentApprovalList = null;
		try {
			ddPaymentApprovalList = paymentDAO.getChalanDetailsForPaymentApproval(candidateMgmtBean, pagination);
		} catch (Exception e) {
			throw new GenericException(e);
		}

		return ddPaymentApprovalList;
	}

	public List<PaymentApprovalBean> getEPostDetailsForPaymentApproval(CandidateMgmtBean candidateMgmtBean, Pagination pagination) throws Exception {
		List<PaymentApprovalBean> ddPaymentApprovalList = null;
		try {
			ddPaymentApprovalList = paymentDAO.getEPostDetailsForPaymentApproval(candidateMgmtBean, pagination);
		} catch (Exception e) {
			throw new GenericException(e);
		}

		return ddPaymentApprovalList;
	}

	@Override
	public PaymentApprovalBean getPaymentApprovalByEnrollmentId(CandidateMgmtBean candidateMgmtBean) throws Exception {
		PaymentApprovalBean paymentApproval = null;
		try {
			paymentApproval = paymentDAO.getPaymentApprovalByEnrollmentId(candidateMgmtBean);
		} catch (Exception e) {
			throw new GenericException(e);
		}

		return paymentApproval;
	}

	public int updateDDPaymentApproval(Users users, CandidateMgmtBean candidateMgmtBean) throws Exception {
		int updateDDpaymentStatus = 0;
		int updateStage = 0;
		List<PaymentApprovalBean> paymentApprovalBeanList = null;

		String checkValue = null;
		try {
			updateDDpaymentStatus = paymentDAO.updateDDPaymentApproval(users, candidateMgmtBean);
			if (updateDDpaymentStatus > 0) {

				if (candidateMgmtBean.getDdPaymentApprovalList() != null) {
					paymentApprovalBeanList = candidateMgmtBean.getDdPaymentApprovalList();
				}

				if (candidateMgmtBean.getChalanPaymentApprovalList() != null) {
					paymentApprovalBeanList = candidateMgmtBean.getChalanPaymentApprovalList();
				}

				String[] paymentPKNUserPKPair = null;
				Users candidate = null;
				List<Users> lstUsers = new ArrayList<Users>();

				if (paymentApprovalBeanList != null) {
					for (PaymentApprovalBean paymentApprovalBean : paymentApprovalBeanList) {
						if (paymentApprovalBean != null) {
							if (paymentApprovalBean.getPaymentCheckedValue() != null && !paymentApprovalBean.getPaymentCheckedValue().get(0).equals("false")) {
								checkValue = paymentApprovalBean.getPaymentCheckedValue().get(0);
								paymentPKNUserPKPair = checkValue.split("\\,");
								if (paymentPKNUserPKPair.length == 2) {
									candidate = new Users();
									candidate.setUserId(Long.parseLong(paymentPKNUserPKPair[1]));
									lstUsers.add(candidate);
								}
							}
						}
					}
				}
				if (lstUsers.size() > 0) {

					for (Users user : lstUsers) {
						updateStage = commonDao.updateCandidateStatus(user, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));

						if (updateStage > 0) {
							user.setStage(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED) + "");
							users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
						}
					}
				}
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return updateDDpaymentStatus;
	}

	public int updateDDPaymentRejection(Users users, CandidateMgmtBean candidateMgmtBean) throws Exception {
		int updateDDpaymentStatus = 0;
		int updateStage = 0;

		List<PaymentApprovalBean> paymentApprovalBeanList = null;

		String checkValue = null;
		try {
			updateDDpaymentStatus = paymentDAO.updateDDPaymentRejection(users, candidateMgmtBean);
			if (updateDDpaymentStatus > 0) {
				if (candidateMgmtBean.getDdPaymentApprovalList() != null) {
					paymentApprovalBeanList = candidateMgmtBean.getDdPaymentApprovalList();
				}

				if (candidateMgmtBean.getChalanPaymentApprovalList() != null) {
					paymentApprovalBeanList = candidateMgmtBean.getChalanPaymentApprovalList();
				}

				String[] paymentPKNUserPKPair = null;
				Users candidate = null;
				List<Users> lstUsers = new ArrayList<Users>();

				if (paymentApprovalBeanList != null) {
					for (PaymentApprovalBean paymentApprovalBean : paymentApprovalBeanList) {
						if (paymentApprovalBean != null) {
							if (paymentApprovalBean.getPaymentCheckedValue() != null && !paymentApprovalBean.getPaymentCheckedValue().get(0).equals("false")) {
								checkValue = paymentApprovalBean.getPaymentCheckedValue().get(0);
								paymentPKNUserPKPair = checkValue.split("\\,");
								if (paymentPKNUserPKPair.length == 2) {
									candidate = new Users();
									candidate.setUserId(Long.parseLong(paymentPKNUserPKPair[1]));
									lstUsers.add(candidate);
								}
							}
						}
					}
				}

				if (lstUsers.size() > 0) {

					for (Users user : lstUsers) {
						updateStage = commonDao.updateCandidateStatus(user, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));

						if (updateStage > 0) {
							user.setStage(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED) + "");
							users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
						}
					}
				}
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return updateDDpaymentStatus;
	}

	public PaymentBean getPaymentDetailsForEnrollment(Users users) throws Exception {
		PaymentBean paymentBean = null;
		try {
			paymentBean = paymentDAO.getPaymentDetailsForEnrollment(users);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return paymentBean;
	}

	public int deletePrevPaymentDetailsForEnrollment(Users users) throws Exception {
		int deletePaymentFlag = 0;
		try {
			deletePaymentFlag = paymentDAO.deletePrevPaymentDetailsForEnrollment(users);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return deletePaymentFlag;
	}

	public int insertOnlinepaymentDetailsForNetBankingOrCreditCard(PaymentOnlineBean paymentBean) throws Exception {
		return paymentDAO.insertOnlinepaymentDetailsForNetBankingOrCreditCard(paymentBean);
	}

	public int insertEPostDetails(PaymentOnlineBean paymentBean, Users users) throws Exception {

		int insertEPostDetailsFlagVal = 0;
		try {
			insertEPostDetailsFlagVal = paymentDAO.insertEPostDetails(paymentBean, users);
			if (insertEPostDetailsFlagVal > 0) {
				int updateStage = commonDao.updateCandidateStatus(users, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_SUBMITED));

				if (updateStage > 0) {
					users.setStage(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_SUBMITED) + "");
					users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_SUBMITED));
				}
			}
		} catch (Exception e) {
			throw new GenericException(e);

		}

		return insertEPostDetailsFlagVal;
	}

	@Override
	public int insertTransactionIDForCandidate(PaymentOnlineBean paymentBean, Users loggedInUser, String merchantTxnSeqNumber, String merchantTxnRefNumber) throws Exception {
		 return paymentDAO.insertTransactionIDForCandidate(paymentBean, loggedInUser, merchantTxnSeqNumber, merchantTxnRefNumber);
	}

	@Override
	public void insertTransactionIDForCandidate1(PaymentOnlineBean paymentBean, Users loggedInUser, String merchantTxnSeqNumber, String merchantTxnRefNumber) throws Exception {
		paymentDAO.insertTransactionIDForCandidate1(paymentBean, loggedInUser, merchantTxnSeqNumber, merchantTxnRefNumber);
	}

	@Override
	public void updateTransactionIDForCandidate(Users loggedInUser, String merchantTxnRefNumber) throws Exception {
		paymentDAO.updateTransactionIDForCandidate(loggedInUser, merchantTxnRefNumber);
	}

	@Override
	public String getExistingMerchantTxnNumber(Users loggedInUser, PaymentOnlineBean paymentBean) throws Exception {
		return paymentDAO.getExistingMerchantTxnNumber(loggedInUser, paymentBean);
	}

	@Override
	public String getExistingMerchantTxnNumber1(Users loggedInUser, PaymentOnlineBean paymentBean) throws Exception {
		return paymentDAO.getExistingMerchantTxnNumber1(loggedInUser, paymentBean);
	}

	@Override
	public String getCandidateCurrentJob(Users loggedInUser) throws Exception {
		return paymentDAO.getCandidateCurrentJob(loggedInUser);
	}

	@Override
	public Map<Integer, String> getAppliedData(Long userFk, String disciplineID) throws Exception {
		return paymentDAO.getAppliedData(userFk, disciplineID);
	}

	@Override
	public List<PaymentOnlineBean> getApplicationDetails(Long userFk) throws Exception {
		return paymentDAO.getApplicationDetails(userFk);
	}

	@Override
	public List<PaymentOnlineBean> getPendingPaymentRecords() throws Exception {
		return paymentDAO.getPendingPaymentRecords();
	}

	public List<PaymentOnlineBean> getPendingPaymentRecords(Long userPk) throws Exception {
		return paymentDAO.getPendingPaymentRecords(userPk);
	}

	@Override
	public PaymentOnlineBean getCandidateTransactionDetailsForOfflineCall(String transactionNumber) throws Exception {
		return paymentDAO.getCandidateTransactionDetailsForOfflineCall(transactionNumber);
	}

	// added by Prudhvi 17-09-2017
	@Override
	public PaymentOnlineBean getApplicantProvidedInfo(String applicationid) throws Exception {
		return paymentDAO.getApplicantProvidedInfo(applicationid);
	}

	@Override
	public PaymentOnlineBean getCandidateJobMappingInfo(Long UserFk) throws Exception {
		// TODO Auto-generated method stub
		return paymentDAO.getCandidateJobMappingInfo(UserFk);
	}

	@Override
	public PaymentOnlineBean getApplicant_OPD_Transaction_NO(String applicationid) throws Exception {
		return paymentDAO.getApplicant_OPD_Transaction_NO(applicationid);
	}

	@Override
	public void saveOfflineTPResponse(TechProcessResponseBean techProcessResponseBean) throws Exception {
		paymentDAO.saveOfflineTPResponse(techProcessResponseBean);
	}

	@Override
	public void saveOfflineTPResponseSuccess(TechProcessResponseBean techProcessResponseBean) throws Exception {
		paymentDAO.saveOfflineTPResponseSuccess(techProcessResponseBean);
	}

	@Override
	public int getDocVerified(Long userFk) throws Exception {
		return paymentDAO.getDocVerified(userFk);
	}

	@Override
	public int updateStatus(Users loggedInUser, String status) throws Exception {
		int updateStatus = 0;
		try {
			updateStatus = paymentDAO.updateCandidateStatus(loggedInUser, status);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		return updateStatus;
	}

	public CandidateBean getCandidateDetailsForPaymentReceipt(Users loggedInUser, int id) throws Exception {
		CandidateBean newCandidateBean = null;
		try {
			newCandidateBean = paymentDAO.getCandidateDetailsForPaymentReceipt(loggedInUser, id);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return newCandidateBean;
	}

	public CandidateBean getCandidateDetailsForChallanReceipt(Users loggedInUser, int id) throws Exception {
		CandidateBean newCandidateBean = null;
		try {
			newCandidateBean = paymentDAO.getCandidateDetailsForChallanReceipt(loggedInUser, id);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return newCandidateBean;
	}

	public List<PaymentOnlineBean> newGetPendingPaymentRecords(String UserId) throws Exception {
		return paymentDAO.newGetPendingPaymentRecords(UserId);
	}

	public int copyPaymentDetailsForEnrollment(Users loggedInUser) throws Exception {
		int paymentFlag = 0;
		try {
			paymentFlag = paymentDAO.copyPaymentDetailsForEnrollment(loggedInUser);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return paymentFlag;
	}

	public int opd_paymentStatusDateCheckUpdate(Users loggedInUser) throws Exception {
		int opaymentStatusDateCheckUpdate = 0;
		try {
			opaymentStatusDateCheckUpdate = paymentDAO.opd_paymentStatusDateCheckUpdate(loggedInUser);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return opaymentStatusDateCheckUpdate;
	}

	public int NoTransactionFoundFlagUpdate(Users loggedInUser) throws Exception {
		int opaymentStatusDateCheckUpdate = 0;
		try {
			opaymentStatusDateCheckUpdate = paymentDAO.NoTransactionFoundFlagUpdate(loggedInUser);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return opaymentStatusDateCheckUpdate;
	}

	public Long getUserPK(String userName) throws Exception {
		return paymentDAO.getUserPK(userName);
	}

	public int getApprovedPaymentStatusCount(Users users) throws Exception {
		int approvPaymentCount = 0;
		try {
			approvPaymentCount = paymentDAO.getApprovedPaymentStatusCount(users);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return approvPaymentCount;
	}

	public int getApprovedPaymentStatusCount1(Users users) throws Exception {
		int approvPaymentCount = 0;
		try {
			approvPaymentCount = paymentDAO.getApprovedPaymentStatusCount1(users);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return approvPaymentCount;
	}

	public int reconcilePaymentAjaxckeck(Users users) throws Exception {
		int reconcilePaymentAjaxckeck = 0;
		try {
			reconcilePaymentAjaxckeck = paymentDAO.reconcilePaymentAjaxckeck(users);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return reconcilePaymentAjaxckeck;
	}

	public int getApprovedPaymentStatusCount2(Users users) throws Exception {
		int approvPaymentCount = 0;
		try {
			approvPaymentCount = paymentDAO.getApprovedPaymentStatusCount2(users);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return approvPaymentCount;
	}

	public int opd_paymentStatusDateCheck(Users users) throws Exception {
		int opd_paymentStatusDateCheck = 0;
		try {
			opd_paymentStatusDateCheck = paymentDAO.opd_paymentStatusDateCheck(users);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return opd_paymentStatusDateCheck;
	}

	public int updatePaymentCreatedDate(Users users) throws Exception {
		int approvPaymentCount = 0;
		try {
			approvPaymentCount = paymentDAO.updatePaymentCreatedDate(users);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return approvPaymentCount;
	}

	public int getIncompleteOnlinePaymentCount(Users users) throws Exception {
		int incompletePaymentCount = 0;
		try {
			incompletePaymentCount = paymentDAO.getIncompleteOnlinePaymentCount(users);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return incompletePaymentCount;
	}

	public int getIncompleteOnlinePaymentCount1(Users users) throws Exception {
		int incompletePaymentCount = 0;
		try {
			incompletePaymentCount = paymentDAO.getIncompleteOnlinePaymentCount1(users);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return incompletePaymentCount;
	}

	public int checkSuccReInitiatePaymentCount(Users users) throws Exception {
		int incompletePaymentCount = 0;
		try {
			incompletePaymentCount = paymentDAO.checkSuccReInitiatePaymentCount(users);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return incompletePaymentCount;
	}

	public int checkPaymentCashBookedCount(Users users) throws Exception {
		int incompletePaymentCount = 0;
		try {
			incompletePaymentCount = paymentDAO.checkPaymentCashBookedCount(users);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return incompletePaymentCount;
	}

	@Override
	public String getUnderQuota(Long userId) throws Exception {
		return paymentDAO.getUnderQuota(userId);
	}

	@Override
	public List<PaymentOnlineBean> getPendingPaymentRecordsForTechProcess() throws Exception {
		return paymentDAO.getPendingPaymentRecordsForTechProcess();
	}

	@Override
	public void saveOfflineTPResponseSuccess(TechProcessResponseBean techProcessResponseBean, String userFk) throws Exception {

		paymentDAO.saveOfflineTPResponseSuccess(techProcessResponseBean, userFk);

		Users users = new Users();
		users.setUserId(Long.parseLong(userFk));
		users.setUsername("TPOfflineAdmin");
		try {
			int updateVal = commonDao.updateCandidateStatus(users, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
		} catch (Exception e) {
			logger.error("Error occoured while updating Candidate status from O call TP for candidate PK : " + userFk);
			throw new Exception();
		}
	}

	@Override
	public void saveOfflineTPOfflineFailedResponse(List<PaymentOnlineBean> failedTypeOList) throws Exception {
		try {
			paymentDAO.saveOfflineTPOfflineFailedResponse(failedTypeOList);
		} catch (Exception e) {
			logger.error("Error occoured while inserting records of  Type O call into OES_PAYMENT_TYPE_O_HISTORY");
			throw new Exception();
		}
	}

	@Override
	public String getTokenGenerated(TechProcessResponseBean techProcessResponseBean) throws Exception {
		String token = null;
		logger.info("getTokenGenerated Service Start");
		try {

			TransactionRequestBean objTransactionRequestBean = new TransactionRequestBean();
			objTransactionRequestBean.setStrRequestType(techProcessResponseBean.getRequestType());
			objTransactionRequestBean.setStrMerchantCode(techProcessResponseBean.getMerchantCode());
			objTransactionRequestBean.setMerchantTxnRefNumber(techProcessResponseBean.getMerchantTxnRefNumber());
			objTransactionRequestBean.setStrAmount(techProcessResponseBean.getAmount());
			objTransactionRequestBean.setStrCurrencyCode(techProcessResponseBean.getCurrencyCode());
			objTransactionRequestBean.setStrITC(techProcessResponseBean.getITC());
			objTransactionRequestBean.setStrShoppingCartDetails(techProcessResponseBean.getCartDetails());
			objTransactionRequestBean.setTxnDate(techProcessResponseBean.getTxnDate());
			objTransactionRequestBean.setStrBankCode(techProcessResponseBean.getBankCode());
			objTransactionRequestBean.setWebServiceLocator(techProcessResponseBean.getPaymentUrl());
			objTransactionRequestBean.setStrTPSLTxnID("");
			objTransactionRequestBean.setCardID("");
			objTransactionRequestBean.setCustID("");
			objTransactionRequestBean.setAccountNo(techProcessResponseBean.getAccountNumber());
			objTransactionRequestBean.setStrTimeOut(techProcessResponseBean.getTransactionTimeOut());
			objTransactionRequestBean.setStrS2SReturnURL("");
			objTransactionRequestBean.setStrMobileNumber(techProcessResponseBean.getMobileNumber());
			objTransactionRequestBean.setStrEmail(techProcessResponseBean.getEmailID());
			objTransactionRequestBean.setStrReturnURL(techProcessResponseBean.getResponseUrl());
			objTransactionRequestBean.setKey(techProcessResponseBean.getEncryptionKey().getBytes());
			objTransactionRequestBean.setIv(techProcessResponseBean.getEncryptionIV().getBytes());

			logger.info("Payment Request Message: " + objTransactionRequestBean.toString());
			token = objTransactionRequestBean.getTransactionToken();
			logger.info("Payment Request For Transaction ID :" + techProcessResponseBean.getMerchantTxnRefNumber() + " Job ID :" + techProcessResponseBean.getITC() + " Token :"
					+ token);

		} catch (Exception e) {
			logger.error(e, e);
		}
		return token;

	}

	@Override
	public String getTransactionUniqueNumber() throws Exception {
		return paymentDAO.getTransactionUniqueNumber();
	}

	@Override
	public int deleteAndInsertTransactionIDForCandidate(PaymentOnlineBean paymentBean, Users loggedInUser, String merchantTxnSeqNumber, String merchantTxnRefNumber)
			throws Exception {
		return paymentDAO.deleteAndInsertTransactionIDForCandidate(paymentBean, loggedInUser, merchantTxnSeqNumber, merchantTxnRefNumber);
	}

	@Override
	public String getCandidateCaste(Users loggedInUser) throws Exception {
		return paymentDAO.getCandidateCaste(loggedInUser);
	}

	@Override
	public String getCandidateDisabilityForPayment(Users loggedInUser) throws Exception {
		return paymentDAO.getCandidateDisabilityForPayment(loggedInUser);
	}

	@Override
	public String getCandidatePaymentStatus(Users loggedInUser) throws Exception {
		return paymentDAO.getCandidatePaymentStatus(loggedInUser);
	}

	@Override
	public int updateSbiResponseForCandidate(SBIResponseBean sbiResponseBean, Users users) throws Exception {
		return paymentDAO.updateSbiResponseForCandidate(sbiResponseBean, users);
	}

	@Override
	public String getCandidateCasteForAdmin(String loggedInUser) throws Exception {
		return paymentDAO.getCandidateCasteForAdmin(loggedInUser);
	}

	@Override
	public String getCandidateDisabilityForPaymentForAdmin(String userFk) throws Exception {
		return paymentDAO.getCandidateDisabilityForPaymentForAdmin(userFk);
	}

	@Override
	public Boolean getCandidateApplicableForDualPost(Users loggedInUser) {
		return paymentDAO.getCandidateApplicableForDualPost(loggedInUser);
	}

	@Override
	public int insertOnlinTransactionDetails(TechProcessResponseBean techProcessResponseBean, Users users) throws Exception {
		int updateVal = 0;
		updateVal = paymentDAO.insertOnlinTransactionDetails(techProcessResponseBean, users);
		return updateVal;
	}

	@Override
	public String getPaymentUserId(String transNo) throws Exception {
		return paymentDAO.getPaymentUserId(transNo);
	}

	@Override
	public void updateTransactionToNull(PaymentOnlineBean paymentBean, Users loggedInUser, String existmercntTxnNo) {
		paymentDAO.updateTransactionToNull(paymentBean, loggedInUser, existmercntTxnNo);

	}
	public CandidateBean getCandidateDetailsForPaymentUrl(Users loggedInUser) {
		CandidateBean newCandidateBean = null;
		try {
				newCandidateBean = paymentDAO.getCandidateDetailsForPaymentUrl(loggedInUser);
			} catch (Exception e) {
				logger.fatal(e,e);
			}

		return newCandidateBean;
	}
	
	//########################################################################################################################## TESTING
	
	@Override
	public int insertAutoApprovePayment(Users loggedInUser) throws Exception {
		int updateVal = paymentDAO.insertAutoApprovePayment(loggedInUser);
		int updateStage = commonDao.updateCandidateStatus(loggedInUser, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
		return updateVal > 0 && updateStage > 0 ? 1 : 0;
	}

	@Override
	public int insertAutoRejectPayment(Users loggedInUser) throws Exception {
		int updateVal = paymentDAO.insertAutoRejectPayment(loggedInUser);
		int updateStage = commonDao.updateCandidateStatus(loggedInUser, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
		return updateVal > 0 && updateStage > 0 ? 1 : 0;
	}

	@Override
	public int updateSafexResponseForCandidate(SafexResponseBean safexResponseBean, Users users) throws Exception {
		return paymentDAO.updateSafexResponseForCandidate(safexResponseBean, users);
		}
	
	@Override
	public String getEncodedValueWithSha2(String hashKey, String... param) {
		String resp = null;
			
		StringBuilder sb = new StringBuilder();
		for (String s : param) {
			sb.append(s);
		}
		try{
			System.out.println("[getEncodedValueWithSha2]String to Encode =" + sb.toString());
			resp = byteToHexString(encodeWithHMACSHA2(sb.toString(), hashKey));
			//resp = URLEncoder.encode(resp,"UTF-8");
		}catch(Exception e)
		{
			System.out.println("[getEncodedValueWithSha2]Unable to encode value with key :" + hashKey + " and input :" + sb.toString());
			e.printStackTrace();
		}
		return resp;
	}
	
	public static byte[] encodeWithHMACSHA2(String text,String keyString) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.io.UnsupportedEncodingException
	{
		
		java.security.Key sk = new javax.crypto.spec.SecretKeySpec(keyString.getBytes("UTF-8"),"HMACSHA512");
		javax.crypto.Mac mac = javax.crypto.Mac.getInstance(sk.getAlgorithm());
		mac.init(sk);
			
		byte[] hmac = mac.doFinal(text.getBytes("UTF-8"));
			
		return hmac;
	}
	
	public static String byteToHexString(byte byData[])
	{
		StringBuilder sb = new StringBuilder(byData.length * 2);
		
		for(int i = 0; i < byData.length; i++)
		{
			int v = byData[i] & 0xff;
			if(v < 16)
				sb.append('0');
			sb.append(Integer.toHexString(v));
		}
		
		return sb.toString();
	}
	
	@Override
	public int insertOnlinTransactionDetailsForAtom(AtomResponseBean atomResponseBean, Users users) throws Exception {
		int updateVal = 0;
		int updateVal1 = 0;
		updateVal = paymentDAO.insertOnlinTransactionDetailsForAtom(atomResponseBean,users);
		
		if(updateVal > 0) {
		if (GenericConstants.AUTH_STATUS_SUCCESS.equalsIgnoreCase(atomResponseBean.getAuthStatus())){
			updateVal1 =  commonDao.updateCandidateStatus(users, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED));
			logger.info("Status Updated for Payment Success. "+updateVal);
		} else {
			updateVal1 = commonDao.updateCandidateStatus(users, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED));
			logger.info("Status Updated Payment Failure. "+updateVal);
			
		}
		}
		return updateVal;
	}
	
	@Override
	public int updateAtomResponseForCandidate(AtomResponseBean atomResponseBean, Users users) throws Exception {
		return paymentDAO.updateAtomResponseForCandidate(atomResponseBean, users);
		}
}
