package com.nseit.payment.dao;

import java.util.List;
import java.util.Map;

import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.CandidateMgmtBean;
import com.nseit.generic.models.CommonBean;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.PaymentApprovalBean;
import com.nseit.generic.models.PaymentBean;
import com.nseit.generic.models.Users;
import com.nseit.payment.models.AtomResponseBean;
import com.nseit.payment.models.OesBankMaster;
import com.nseit.payment.models.PaymentOnlineBean;
import com.nseit.payment.models.SBIResponseBean;
import com.nseit.payment.models.SBIePayResponseBean;
import com.nseit.payment.models.SafexResponseBean;
import com.nseit.payment.models.TechProcessResponseBean;

public interface PaymentDAO {

	public int insertOnlinepaymentDetails(PaymentOnlineBean paymentBean) throws Exception;

	public List<PaymentBean> getBankDetails() throws Exception;

	public List<PaymentBean> getCityDetails() throws Exception;

	public List<PaymentBean> getOnlinePaymentDetails(Users users) throws Exception;

	public PaymentBean getDemandDraftDetailsForEnrollment(Users users) throws Exception;

	public PaymentBean getChallanDetailsForEnrollment(Users users) throws Exception;

	public int insertDDDetails(PaymentOnlineBean paymentBean, Users users) throws Exception;;

	public int insertChallanDetails(PaymentOnlineBean paymentBean, Users loggedInUser) throws Exception;

	public int updateDDDetails(PaymentOnlineBean paymentBean, Users loggedInUser) throws Exception;

	public int insertChallanBarcodeDetails(CandidateBean candidateBean) throws Exception;

	public int updateChallanDetails(PaymentOnlineBean paymentBean, Users loggedInUser) throws Exception;

	public int isDDExist(PaymentOnlineBean PaymentOnlineBean, Users loggedInUser) throws Exception;

	public String getFeesForOnlinePayment(long getFeesForOnlinePayment);

	public int insertpagedetails(PaymentBean paymentBean, String enrollmentpk, Users loggedInUsers) throws Exception;

	public String getBankCodeForChallanReceipt(Users loggedInUser) throws Exception;

	public PaymentBean getBankLogoImage(String bankID) throws Exception;

	public List<CommonBean> getBankDetails(Users loggedInUser) throws Exception;

	public List<CommonBean> getCityDetails(Users users) throws Exception;

	public boolean isStageActive(String stageId) throws Exception;

	public CandidateBean getCandidateDetailsForPayment(Users loggedInUser, int id) throws Exception;

	public List<CommonBean> getDisciplineList() throws Exception;

	public List<CommonBean> getPaymentModeList() throws Exception;

	public List<PaymentApprovalBean> getDDDetailsForPaymentApproval(CandidateMgmtBean candidateMgmtBean, Pagination pagination) throws Exception;

	public int getDDDetailsCountForPaymentApproval(CandidateMgmtBean candidateMgmtBean) throws Exception;

	public List<PaymentApprovalBean> getChalanDetailsForPaymentApproval(CandidateMgmtBean candidateMgmtBean, Pagination pagination) throws Exception;

	public int getCountForChalanDetailsForPaymentApproval(CandidateMgmtBean candidateMgmtBean) throws Exception;

	public PaymentApprovalBean getPaymentApprovalByEnrollmentId(CandidateMgmtBean candidateMgmtBean) throws Exception;

	public int updateDDPaymentApproval(Users users, CandidateMgmtBean candidateMgmtBean) throws Exception;

	public int updateDDPaymentRejection(Users users, CandidateMgmtBean candidateMgmtBean) throws Exception;

	public List<OesBankMaster> getBankList() throws Exception;

	public String getFeesDetail(String userId) throws Exception;

	public int isChallanExist(PaymentOnlineBean paymentBean, Users loggedInUser) throws Exception;

	public PaymentBean getPaymentDetailsForEnrollment(Users users) throws Exception;

	public int deletePrevPaymentDetailsForEnrollment(Users users) throws Exception;

	public int updateDDDetailsForRegisteredID(PaymentApprovalBean paymentApprovalBean, Users users) throws Exception;

	public int updateChallanDetailsForRegisteredID(PaymentApprovalBean paymentApprovalBean, Users users) throws Exception;

	public int insertOnlinepaymentDetailsForNetBankingOrCreditCard(PaymentOnlineBean paymentBean) throws Exception;

	public int insertEPostDetails(PaymentOnlineBean paymentBean, Users loggedInUser) throws Exception;

	public int getCountForEPostDetailsForPaymentApproval(CandidateMgmtBean candidateMgmtBean) throws Exception;

	public List<PaymentApprovalBean> getEPostDetailsForPaymentApproval(CandidateMgmtBean candidateMgmtBean, Pagination pagination) throws Exception;

	public PaymentOnlineBean getPaymentDetails(Users users) throws Exception;

	public int insertOnlinTransactionDetails(TechProcessResponseBean techProcessResponseBean, Users users) throws Exception;

	public int updateSbiResponseForCandidate(SBIResponseBean sbiResponseBean, Users users) throws Exception;
	
	public int updateSafexResponseForCandidate(SafexResponseBean safexResponseBean, Users users) throws Exception;

	public int insertTransactionIDForCandidate(PaymentOnlineBean paymentBean, Users loggedInUser, String merchantTxnSeqNumber, String merchantTxnRefNumber) throws Exception;

	public void insertTransactionIDForCandidate1(PaymentOnlineBean paymentBean, Users loggedInUser, String merchantTxnSeqNumber, String merchantTxnRefNumber) throws Exception;

	public void updateTransactionIDForCandidate(Users loggedInUser, String merchantTxnRefNumber) throws Exception;

	public String getExistingMerchantTxnNumber(Users loggedInUser, PaymentOnlineBean paymentBean) throws Exception;

	public String getExistingMerchantTxnNumber1(Users loggedInUser, PaymentOnlineBean paymentBean) throws Exception;

	public String getCandidateCurrentJob(Users loggedInUser) throws Exception;

	public Map<Integer, String> getAppliedData(Long userFk, String disciplineID) throws Exception;

	public List<PaymentOnlineBean> getApplicationDetails(Long userFk) throws Exception;

	public List<PaymentOnlineBean> getPendingPaymentRecords() throws Exception;

	public List<PaymentOnlineBean> getPendingPaymentRecords(Long userPk) throws Exception;

	public List<PaymentOnlineBean> newGetPendingPaymentRecords(String UserId) throws Exception;

	public PaymentOnlineBean getCandidateTransactionDetailsForOfflineCall(String transactionNumber) throws Exception;

	// added by Prudhvi 17-09-2017
	public PaymentOnlineBean getApplicantProvidedInfo(String applicationid) throws Exception;

	public PaymentOnlineBean getCandidateJobMappingInfo(Long UserFk) throws Exception;

	public PaymentOnlineBean getApplicant_OPD_Transaction_NO(String applicationid) throws Exception;

	public void saveOfflineTPResponse(TechProcessResponseBean techProcessResponseBean) throws Exception;

	public void saveOfflineTPResponseSuccess(TechProcessResponseBean techProcessResponseBean) throws Exception;

	public int getDocVerified(Long userFk) throws Exception;

	public int updateCandidateStatus(Users loggedInUser, String status) throws Exception;

	public CandidateBean getCandidateDetailsForPaymentReceipt(Users loggedInUser, int id) throws Exception;

	public CandidateBean getCandidateDetailsForChallanReceipt(Users loggedInUser, int id) throws Exception;

	public int copyPaymentDetailsForEnrollment(Users loggedInUser) throws Exception;

	public int opd_paymentStatusDateCheckUpdate(Users loggedInUser) throws Exception;

	public int NoTransactionFoundFlagUpdate(Users loggedInUser) throws Exception;

	public Long getUserPK(String userName) throws Exception;

	public int getApprovedPaymentStatusCount(Users users) throws Exception;

	public int getApprovedPaymentStatusCount1(Users users) throws Exception;

	public int reconcilePaymentAjaxckeck(Users users) throws Exception;

	public int getApprovedPaymentStatusCount2(Users users) throws Exception;

	public int opd_paymentStatusDateCheck(Users users) throws Exception;

	public int updatePaymentCreatedDate(Users users) throws Exception;

	public int getIncompleteOnlinePaymentCount(Users loggedInUser) throws Exception;

	public int getIncompleteOnlinePaymentCount1(Users loggedInUser) throws Exception;

	public int checkSuccReInitiatePaymentCount(Users users) throws Exception;

	public int checkPaymentCashBookedCount(Users users) throws Exception;

	public String getUnderQuota(Long userId) throws Exception;

	public List<PaymentOnlineBean> getPendingPaymentRecordsForTechProcess() throws Exception;

	public void saveOfflineTPResponseSuccess(TechProcessResponseBean techProcessResponseBean, String userFk) throws Exception;

	public Users getUserDetailsForEmailForApplicationNo(Long userPk) throws Exception;

	public void saveOfflineTPOfflineFailedResponse(List<PaymentOnlineBean> failedTypeOList) throws Exception;

	public String getTransactionUniqueNumber() throws Exception;

	public int deleteAndInsertTransactionIDForCandidate(PaymentOnlineBean paymentBean, Users loggedInUser, String merchantTxnSeqNumber, String merchantTxnRefNumber)
			throws Exception;

	public String getCandidateCaste(Users loggedInUser) throws Exception;

	public String getCandidateDisabilityForPayment(Users loggedInUser) throws Exception;

	public String getCandidatePaymentStatus(Users loggedInUser) throws Exception;

	public String getCandidateCasteForAdmin(String loggedInUser) throws Exception;

	public String getCandidateDisabilityForPaymentForAdmin(String userFK) throws Exception;

	int insertOnlinTransactionDetails(SBIePayResponseBean sbiResponseBean, Users users) throws Exception;

	public Boolean getCandidateApplicableForDualPost(Users loggedInUser);

	String getPaymentUserId(String transNo) throws Exception;
	public CandidateBean getCandidateDetailsForPaymentUrl(Users loggedInUser);
	public void updateTransactionToNull(PaymentOnlineBean paymentBean, Users loggedInUser, String existmercntTxnNo);
	
	public int insertAutoApprovePayment(Users loggedInUser) throws Exception;
	public int insertAutoRejectPayment(Users loggedInUser) throws Exception;
	
	public int insertOnlinTransactionDetailsForAtom(AtomResponseBean atomResponseBean, Users users)throws Exception;
	
	public int updateAtomResponseForCandidate(AtomResponseBean atomResponseBean, Users users) throws Exception;
}
