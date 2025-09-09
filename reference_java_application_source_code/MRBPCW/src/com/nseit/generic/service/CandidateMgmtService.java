package com.nseit.generic.service;

import java.util.List;
import java.util.Map;

import com.nseit.generic.models.CandidateApproveRejectBean;
import com.nseit.generic.models.CandidateMgmtBean;
import com.nseit.generic.models.HallTicketBean;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.PaymentBean;
import com.nseit.generic.models.UserDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.util.GenericException;

public interface CandidateMgmtService {

	public List<CandidateMgmtBean> getCandidateHallTicket(CandidateMgmtBean candidateMgmtBean, String stage, Integer attempt) throws Exception;

	public List<CandidateMgmtBean> getCandidateCandidateDetailsById(CandidateMgmtBean candidateMgmtBean) throws Exception;

	public Map<Integer, String> getTestCenterMasterDetails() throws Exception;

	public List<String> getTestDates() throws Exception;

	public List<String> getTestSlots(CandidateMgmtBean candidateMgmtBean) throws Exception;

	public List<CandidateMgmtBean> getCandidateDetailsForRegistration(CandidateMgmtBean candidateMgmtBean) throws Exception;

	public UserDetailsBean getCandidateDisciplineForPayment(Users users) throws GenericException;

	public List<HallTicketBean> getCandidateDataForRollNumberGeneration() throws Exception;

	public String getRollNumberSeq(Integer maxDigitsForRollNumber) throws Exception;

	public int updateCandidateRollNumbers(final Users users, final List<HallTicketBean> candidateDataForRollNumber) throws GenericException;

	public List<HallTicketBean> getCandidateDataForAdmitCard(CandidateMgmtBean candidateMgmtBean) throws Exception;

	public int getCandidateDataForAdmitCardForSearchCount(CandidateMgmtBean candidateMgmtBean) throws Exception;

	public List<HallTicketBean> getCandidateDataForAdmitCardForSearch(CandidateMgmtBean candidateMgmtBean, Pagination pagination) throws Exception;

	public int[] updateGenerateHallticket(final List<String> enrollmentList, final Users users) throws GenericException;

	public String generateRollNumberForCandidates() throws Exception;

	public UserDetailsBean getUserDetailsForEnrollmentId(String enrollmentPk) throws Exception;

	public int getRegisteredCandidateDetails(String userId, String fromDate, String toDate, Integer disciplineId, Integer candidateStatusId, int formSubmitStatus);

	public List<CandidateApproveRejectBean> getRegisteredCandidateDetails(String userId, String fromDate, String toDate, Integer disciplineId, Integer candidateStatusId,
			int formStatusId, Pagination pagination);

	public int updateOesPaymentDetails(String challanNumber, Integer paymentModeId) throws Exception;

	public int updateOesPaymentDetailsWithPaymentReject(String challanNumber, Integer paymentModeId) throws Exception;

	public int updateCandidateStatus(int candidateStatus, Integer userId) throws Exception;

	public int getPaymentReportSearchResultCount(CandidateMgmtBean candidateMgmtBean) throws Exception;

	public List<CandidateMgmtBean> getPaymentReportSearchResult(CandidateMgmtBean candidateMgmtBean, Pagination pagination) throws Exception;

	public List<CandidateMgmtBean> getPaymentReportSearchResult(CandidateMgmtBean candidateMgmtBean) throws Exception;

	public int getCandidateDetailsForPaymentNotSubmittedCount(CandidateMgmtBean candidateMgmtBean) throws Exception;

	public List<CandidateMgmtBean> getCandidateDetailsForPaymentNotSubmitted(CandidateMgmtBean candidateMgmtBean, Pagination pagination) throws Exception;

	public List<CandidateMgmtBean> getCandidateDetailsForPaymentNotSubmitted(CandidateMgmtBean candidateMgmtBean) throws Exception;

	public void insertEpostPaymentDetails(PaymentBean pb, String applicationNo, Integer paymentKeyByDesc, String tranId) throws Exception;

}
