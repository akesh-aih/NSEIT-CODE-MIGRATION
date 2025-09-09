package com.nseit.generic.service;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import com.nseit.generic.models.AgeMatrix;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.ConfigParam;
import com.nseit.generic.models.EmailSMSTransactionBean;
import com.nseit.generic.models.MenuBean;
import com.nseit.generic.models.RegistrationBean;
import com.nseit.generic.models.UploadManagementBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.MenuMasterChildBean;
import com.nseit.generic.util.MenuMasterParentBean;
import com.nseit.generic.util.StageOffBean;
import com.nseit.generic.util.StageOnBean;
import com.nseit.otbs.model.TestMasterBean;

public interface CommonService {
	public Map<String, ConfigParam> getAllConfigParams() throws Exception;

	public Map<Integer, String> getActivityMap() throws Exception;

	public int[] insertEmailSMSDetails(List<EmailSMSTransactionBean> emailSMS, String username) throws Exception;

	public List<EmailSMSTransactionBean> getEmailForMailing(Integer failureCount, int maxRecords) throws Exception;

	public List<EmailSMSTransactionBean> getSMSForSending(Integer failureCount, int maxRecords) throws Exception;

	public void updateEmailSMSMasterMap() throws GenericException;
	
	public Map<Integer, String> getBoardMasterMap() throws Exception;

	// public int updateCandidateStage(Users users,int stage) throws Exception;
	public int updateCandidateStage(Users users, String stage) throws Exception;

	public Map<String, String> getCandidatesDataForEmailSMS(String userId) throws Exception;

	public Map<String, SortedMap<String, MenuBean>> getMenuForAll() throws Exception;

	public void updateEmailSMSStatusPostSending(List<EmailSMSTransactionBean> lstEmailSMSTransactionBeans) throws Exception;

	public String getCandidateStage(String username) throws Exception;

	public Map<String, Map<Integer, String>> getReferenceDomainNameNKeyValueMap() throws Exception;

	public Map<Integer, Map<Integer, String>> getCountryStateMapping() throws Exception;

	public Map<Integer, String> getCountryMap() throws Exception;

	public Map<Integer, Map<Integer, String>> getStateDistrictMapping() throws Exception;

	public Map<Integer, String> getStageMap() throws Exception;

	public int getCandidateStage(Users users) throws Exception;

	public Map<Integer, List<Long>> getDateWindowData() throws Exception;

	public Map<Integer, TestMasterBean> getTestDetailsMap() throws Exception;

	public Map<Integer, String> getDisciplineMap() throws Exception;

	public Map<Integer, String> getApplyDisciplineMap() throws Exception;

	public Map<String, String> getCandidatesDataForEmailSMSForSeatBooking(String userId, int attempt) throws Exception;

	public Map<Integer, String> getStageActionUrlMap() throws Exception;

	public Map<Integer, String> getStageStatusMap() throws Exception;

	public Map<Integer, String> getTestCenterMaster() throws Exception;

	public Map<Integer, Integer> getSelectedPreferredCenterMaster(Users loggedInUser, String flag) throws Exception;

	public List<String> getTestCentresforDisplay(Users loggedInUser, String flag) throws Exception;

	public List<MenuMasterParentBean> getParentMenuList() throws Exception;

	public List<MenuMasterChildBean> getChildMenuList(String parentMenuKey, String roleFK);

	public List<MenuMasterChildBean> getChildList();

	public Map<String, List<MenuMasterChildBean>> getMenuMasterList() throws Exception;

	public Map<String, String> getMenuKeyLinkMap() throws GenericException;

	public Map<String, String> getMenuKeyMenuLinkMap() throws GenericException;

	public Map<String, String> getMenuKeyNextStageMap() throws GenericException;

	public Map<String, String> getMenuKeyMandatoryMap() throws GenericException;

	public Map<String, String> getMenuKeyStatusMap() throws GenericException;

	public Map<String, String> getMenuDescMenuKeyMap() throws GenericException;

	public Map<String, String> getMenuDescActiveStatusMap() throws GenericException;

	public Map<String, List<StageOffBean>> getStageOffListMap() throws GenericException;

	public Map<String, List<StageOnBean>> getStageOnListMap() throws GenericException;

	public Map<Integer, String> getStatusMasterMap() throws GenericException;

	public Map<Integer, String> getPaymentMasterMap() throws GenericException;

	public Map<String, String> getStatusDescStatusMap() throws GenericException;

	public Map<Integer, String> getStatusMasterMapForReport() throws GenericException;

	public int updateCandidateStatus(Users users, Integer statusId) throws GenericException;

	public Map<String, Integer> getEmailStatusMap() throws Exception;

	public Map<String, Integer> getSmsStatusMap() throws Exception;

	public Map<String, String> getCandidatesDataForEmailSMSForPayment(String userId) throws Exception;

	public Map<Integer, String> getCategoryListMap() throws Exception;

	public Map<Integer, String> getPostMasterListMap() throws Exception;

	public Map<String, String> getStaticDataMap() throws Exception;
	
	public Map<String, Integer> getDateWindowMap() throws Exception;

	public Map<String, String> getCandidatesDataForEmailSMSForAdmitCard(String userId, Integer attempt) throws Exception;

	public Map<String, String> getCandidatesDataForNewReg(String userId) throws Exception;

	public int[] insertEmailSMSDetailsForNewReg(List<EmailSMSTransactionBean> emailSMS, RegistrationBean registrationBean);

	public Map<String, String> getCandidatesDataForEmailSMSForPaymentFailure(String userId) throws Exception;

	public Map<String, String> getCandidatesDataAutoApprovePayment(String userId) throws Exception;

	public Map<String, String> getCandidatesDataForEmailSMSForTestCenterAllocation(String userId) throws Exception;

	public Map<String, String> getAcademicMasterMap() throws Exception;

	public Map<String, String> getAcademicMandatoryMap() throws Exception;

	public Map getDegreeMappingDetails() throws Exception;

	public Map<String, String> getAcademicMasterReverseMap() throws Exception;

	public int[] insertEmailSMSDetailsForForgotUserId(List<EmailSMSTransactionBean> emailSMS, String loggedInUserName) throws Exception;
	
	public int[] insertEmailSMSDetailsForForgotPassword(List<EmailSMSTransactionBean> emailSMS,
			String loggedInUserName) throws Exception;
	
	public Map<String, String> getCandidatesDataForForgotUserId(String userId) throws Exception;

	public Map<String, Integer> getPaymentMasterReverseMap() throws GenericException;

	public Map<String, String> getReferenceValueActiveMap() throws Exception;

	public Map<Integer, String> getDegreeMap() throws Exception;

	public Map<Integer, Map<Integer, String>> getUniversityMap() throws Exception;

	public Map<Integer, String> getSubDegreeMap() throws Exception;

	public UploadManagementBean getPostDetails(Users loggedInUser) throws Exception;

	public UploadManagementBean getPostEntrollmentTestDetails(Users loggedInUser) throws Exception;

	// shekharc apply page confirmation msg start
	// shekharc apply page confirmation msg end
	public int insertCandidateAuditrail(Users users, String action, String auditTrail) throws Exception;

	public Map<Integer, Map<Integer, String>> getAcademicMapping() throws Exception;

	public String getTransactionUniqueNumber() throws Exception;

	public Map<String, String> getCandidateLabelMap() throws Exception;

	public int insertOTPDetails(EmailSMSTransactionBean emailSMSBean, String emailId) throws Exception;

	public int updateEmailSmsOTPDetails(EmailSMSTransactionBean emailSMSBean, String emailId) throws Exception;

	public List<CandidateBean> getCandidateAppliedPost(Users loggedInUser, int status) throws Exception;

	public int updateForgotPassword(String userid, String key) throws Exception;

	public Long getUserFK(String customerId, String txnReferenceNo) throws Exception;

	public void insertCheckboxValue(String checkBoxValue, long userId) throws Exception;

	public String getCandidateStatus(String userId) throws Exception;

	public int getTrainingCentreFk(long userId) throws Exception;

	public Map<Integer, String> getNativityMap() throws Exception;

	public Boolean getEligibilityToEditSubjectApplied(long userId) throws Exception;

	public String getPostAppliedFor(Long usrId) throws Exception;

	public Map<Integer, AgeMatrix> getAllMatrixParams() throws Exception;
	
	public String getRegistrationId(Users loggedInUser) throws Exception;

	public Map<String, String> getCandidatesDataForForgotPassword(String userID) throws Exception;

	// for Covid Duty Certificate page
	public Map<Integer, String> getSignedByDataMap() throws Exception;

	public String getCandidateDetailsByLoginId(String loginId);

	
	
	//public Boolean getcandidatEditStatus(long userId) throws Exception;
}
