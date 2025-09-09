package com.nseit.generic.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.apache.logging.log4j.Logger;

import com.nseit.generic.dao.CandidateDao;
import com.nseit.generic.dao.CommonDao;
import com.nseit.generic.models.AgeMatrix;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.ConfigParam;
import com.nseit.generic.models.EmailSMSMasterBean;
import com.nseit.generic.models.EmailSMSTransactionBean;
import com.nseit.generic.models.MenuBean;
import com.nseit.generic.models.RegistrationBean;
import com.nseit.generic.models.UploadManagementBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.CommonService;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.GenericServiceException;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.MenuMasterChildBean;
import com.nseit.generic.util.MenuMasterParentBean;
import com.nseit.generic.util.StageOffBean;
import com.nseit.generic.util.StageOnBean;
import com.nseit.otbs.model.TestMasterBean;

public class CommonServiceImpl implements CommonService {
	private Logger logger = LoggerHome.getLogger(getClass());
	private CandidateDao candidateDao;
	/** The common dao. */
	private CommonDao commonDao;

	/**
	 * Sets the common dao.
	 *
	 * @param configDao
	 *            the new common dao
	 */
	public void setCommonDao(CommonDao configDao) {
		this.commonDao = configDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nseit.generic.service.CommonService#getAllConfigParams()
	 */
	public Map<String, ConfigParam> getAllConfigParams() throws Exception {

		List<ConfigParam> lstConfigParams = commonDao.getConfigParam();

		Map<String, ConfigParam> configParamMap = new HashMap<String, ConfigParam>((int) ((lstConfigParams.size() / 0.5) + 1), 0.5f);

		for (ConfigParam configParam : lstConfigParams) {
			configParamMap.put(configParam.getKeyName(), configParam);
		}

		lstConfigParams = null;

		return configParamMap;
	}
	
	@Override
	public Map<Integer, AgeMatrix> getAllMatrixParams() throws Exception {
		List<AgeMatrix> lstAgeMatrix = commonDao.getAgeMatrixData();
		Map<Integer,AgeMatrix> ageMatrixMap = new HashMap<Integer, AgeMatrix>();
		for(AgeMatrix agematrixData : lstAgeMatrix) {
			ageMatrixMap.put(agematrixData.getRecord_pk(), agematrixData);
		}
		lstAgeMatrix = null;
		return ageMatrixMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nseit.generic.service.CommonService#getActivityMap()
	 */
	public Map<Integer, String> getActivityMap() throws Exception {

		return commonDao.getActivityMap();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nseit.generic.service.CommonService#insertEmailSMSDetails(com.nseit.
	 * generic.models.EmailSMSTransactionBean, java.lang.String)
	 */
	public int[] insertEmailSMSDetails(List<EmailSMSTransactionBean> emailSMS, String username) throws Exception {

		return commonDao.insertEmailSMSDetails(emailSMS, username);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nseit.generic.service.CommonService#getEmailForMailing(java.lang.
	 * Integer, int)
	 */
	public List<EmailSMSTransactionBean> getEmailForMailing(Integer failureCount, int maxRecords) throws Exception {

		return commonDao.getEmailForMailing(failureCount, maxRecords);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nseit.generic.service.CommonService#getSMSForSending(java.lang.
	 * Integer, int)
	 */
	public List<EmailSMSTransactionBean> getSMSForSending(Integer failureCount, int maxRecords) throws Exception {

		return commonDao.getSMSForSending(failureCount, maxRecords);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nseit.generic.service.CommonService#updateEmailSMSMasterMap()
	 */
	public void updateEmailSMSMasterMap() throws GenericException {

		List<EmailSMSMasterBean> lstEmailSMSMasterBeans = null;
		Map<String, EmailSMSMasterBean> emailSMSMasterMap = new HashMap<String, EmailSMSMasterBean>();
		EmailSMSMasterBean emailSMSMasterBean = null;

		try {
			lstEmailSMSMasterBeans = commonDao.getEmailSMSMasterList();
			if (lstEmailSMSMasterBeans != null) {
				for (int currIndex = 0; currIndex < lstEmailSMSMasterBeans.size(); currIndex++) {
					emailSMSMasterBean = lstEmailSMSMasterBeans.get(currIndex);
					emailSMSMasterMap.put(emailSMSMasterBean.getTestId() + "_" + emailSMSMasterBean.getActivityId(), emailSMSMasterBean);
				}

				ConfigurationConstants.getInstance().updateEmailSMSMasterMap(emailSMSMasterMap);
			}
		} catch (Exception ex) {
			throw new GenericServiceException(ex);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nseit.generic.service.CommonService#updateCandidateStage(com.nseit.
	 * generic.models.Users, int)
	 */
	public int updateCandidateStage(Users users, String stage) throws Exception {

		int updateVal = 0;

		try {
			updateVal = commonDao.updateCandidateStage(users, stage);
		} catch (Exception ex) {
			throw new GenericServiceException(ex);
		}

		return updateVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nseit.generic.service.CommonService#updateCandidateStage(com.nseit.
	 * generic.models.Users, java.lang.String)
	 * 
	 * public int updateCandidateStage(Users users, String stage) throws
	 * Exception {
	 * 
	 * int updateVal = 0;
	 * 
	 * try { updateVal = updateCandidateStage(users, stage); } catch (Exception
	 * ex) { throw new GenericServiceException(ex); }
	 * 
	 * return updateVal; }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nseit.generic.service.CommonService#getCandidatesDataForEmailSMS(java
	 * .lang.String)
	 */
	public Map<String, String> getCandidatesDataForEmailSMS(String userId) throws Exception {

		return commonDao.getCandidatesDataForEmailSMS(userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nseit.generic.service.CommonService#
	 * getCandidatesDataForEmailSMSForSeatBooking(java.lang.String, int)
	 */
	public Map<String, String> getCandidatesDataForEmailSMSForSeatBooking(String userId, int attempt) throws Exception {
		return commonDao.getCandidatesDataForEmailSMSForSeatBooking(userId, attempt);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nseit.generic.service.CommonService#getMenuForAll()
	 */
	public Map<String, SortedMap<String, MenuBean>> getMenuForAll() throws Exception {

		List<Map<String, String>> menuFromDB = commonDao.getMenuForAll();
		Map<String, SortedMap<String, MenuBean>> userTypeMenuMap = new HashMap<String, SortedMap<String, MenuBean>>();
		MenuBean masterMenu = new MenuBean("-1", null, null);
		MenuBean subMenu = null;
		String menuKey;
		String parentKey;
		String displayName;
		String menuLink;
		String prevUserType = null;

		for (Map<String, String> columnNamenDataMap : menuFromDB) {
			if (prevUserType == null) {
				masterMenu = new MenuBean("-1", null, null);
				prevUserType = columnNamenDataMap.get("OMM_user_type");
			}

			if (prevUserType.compareTo(columnNamenDataMap.get("OMM_user_type")) != 0) {
				userTypeMenuMap.put(prevUserType, masterMenu.getSubMenu());

				masterMenu = new MenuBean("-1", null, null);
				prevUserType = columnNamenDataMap.get("OMM_user_type");
			}

			menuKey = columnNamenDataMap.get("OMM_menu_key");
			parentKey = columnNamenDataMap.get("OMM_parent_menu_key");
			displayName = columnNamenDataMap.get("OMM_display_name");
			menuLink = columnNamenDataMap.get("OMM_menu_link");

			subMenu = new MenuBean(menuKey, displayName, menuLink);
			if (masterMenu.isParentExists(parentKey)) {
				masterMenu.addSubMenu(parentKey, subMenu);
			}
		}

		if (prevUserType.compareTo("") != 0) {
			userTypeMenuMap.put(prevUserType, masterMenu.getSubMenu());
		}

		return userTypeMenuMap;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List<MenuMasterParentBean> getParentMenuList() throws Exception {
		List<MenuMasterParentBean> parentMenuList = commonDao.getParentMenuList();
		// System.out.println(parentMenuList);
		return parentMenuList;
	}

	public Map<String, List<MenuMasterChildBean>> getMenuMasterList() throws Exception {
		return commonDao.getMenuMasterMap();
	}

	/**
	 * @param parentMenuKey
	 * @return
	 */
	public List<MenuMasterChildBean> getChildMenuList(String parentMenuKey, String roleFK) {
		return commonDao.getChildMenuList(parentMenuKey, roleFK);
	}

	public List<MenuMasterChildBean> getChildList() {
		return commonDao.getChildList();
	}

	public Map<String, String> getMenuKeyLinkMap() throws GenericException {
		return commonDao.getMenuKeyLinkMap();
	}

	public Map<String, String> getMenuKeyMenuLinkMap() throws GenericException {
		return commonDao.getMenuKeyMenuLinkMap();
	}

	public Map<String, String> getMenuKeyNextStageMap() throws GenericException {
		return commonDao.getMenuKeyNextStageMap();
	}

	public Map<String, String> getMenuKeyMandatoryMap() throws GenericException {
		return commonDao.getMenuKeyMandatoryMap();
	}

	public Map<String, String> getMenuKeyStatusMap() throws GenericException {
		return commonDao.getMenuKeyStatusMap();
	}

	public Map<String, String> getMenuDescMenuKeyMap() throws GenericException {
		return commonDao.getMenuDescMenuKeyMap();
	}

	public Map<String, String> getMenuDescActiveStatusMap() throws GenericException {
		return commonDao.getMenuDescActiveStatusMap();
	}

	public Map<String, List<StageOffBean>> getStageOffListMap() throws GenericException {
		return commonDao.getStageOffListMap();
	}

	public Map<String, List<StageOnBean>> getStageOnListMap() throws GenericException {
		return commonDao.getStageOnListMap();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nseit.generic.service.CommonService#updateEmailSMSStatusPostSending(
	 * java.util.List)
	 */
	public void updateEmailSMSStatusPostSending(List<EmailSMSTransactionBean> lstEmailSMSTransactionBeans) throws Exception {
		commonDao.updateEmailSMSStatusPostSending(lstEmailSMSTransactionBeans);
	}

	@Override
	public Map<Integer, String> getBoardMasterMap() throws Exception {
		return commonDao.getBoardMasterMap();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nseit.generic.service.CommonService#getCandidateStage(java.lang.
	 * String)
	 */
	public String getCandidateStage(String username) throws Exception {
		return commonDao.getCandidateStage(username);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nseit.generic.service.CommonService#
	 * getReferenceDomainNameNKeyValueMap()
	 */
	public Map<String, Map<Integer, String>> getReferenceDomainNameNKeyValueMap() throws Exception {

		try {
			return commonDao.getReferenceDomainNameNKeyValueMap();
		} catch (Exception ex) {
			throw new GenericServiceException(ex);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nseit.generic.service.CommonService#getCountryMap()
	 */
	public Map<Integer, String> getCountryMap() throws Exception {

		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		try {
			map = commonDao.getCountryMap();
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nseit.generic.service.CommonService#getCountryStateMapping()
	 */
	public Map<Integer, Map<Integer, String>> getCountryStateMapping() throws Exception {

		Map<Integer, Map<Integer, String>> dataMap = new LinkedHashMap<Integer, Map<Integer, String>>();
		try {
			dataMap = commonDao.getCountryStateMapping();
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return dataMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nseit.generic.service.CommonService#getStateDistrictMapping()
	 */
	public Map<Integer, Map<Integer, String>> getStateDistrictMapping() throws Exception {

		Map<Integer, Map<Integer, String>> dataMap = new LinkedHashMap<Integer, Map<Integer, String>>();
		try {
			dataMap = commonDao.getStateDistrictMapping();
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return dataMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nseit.generic.service.CommonService#getStageMap()
	 */
	public Map<Integer, String> getStageMap() throws Exception {

		return commonDao.getStageMap();
	}

	/**
	 * @author shailendra sharma
	 */
	public Map<Integer, String> getStageStatusMap() throws Exception {

		return commonDao.getStageStatusMap();
	}

	/**
	 * @author shailendra sharma
	 */

	public Map<Integer, String> getStageActionUrlMap() throws Exception {

		return commonDao.getStageActionUrlMap();
	}

	/**
	 * @author shailendra sharma
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nseit.generic.service.CommonService#getHelpTestCenterMaster()
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nseit.generic.service.CommonService#getCountryUTMapping()
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nseit.generic.service.CommonService#getCandidateStage(com.nseit.
	 * generic.models.Users)
	 */
	public int getCandidateStage(Users users) throws Exception {

		try {
			return commonDao.getCandidateStage(users);
		} catch (Exception ex) {
			throw new GenericServiceException(ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nseit.generic.service.CommonService#getDownloadPaths()
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nseit.generic.service.CommonService#getDateWindowData()
	 */
	public Map<Integer, List<Long>> getDateWindowData() throws Exception {

		Map<Integer, List<Long>> dateWindowMap = null;
		try {
			dateWindowMap = commonDao.getDateWindowData();
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return dateWindowMap;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nseit.generic.service.CommonService#getTestDetailsMap()
	 */
	public Map<Integer, TestMasterBean> getTestDetailsMap() throws Exception {

		try {
			return commonDao.getTestDetailsMap();
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
	}

	public Map<Integer, String> getDisciplineMap() throws Exception {

		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		try {
			map = commonDao.getDisciplineMap();
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return map;
	}

	public Map<Integer, String> getApplyDisciplineMap() throws Exception {

		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		try {
			map = commonDao.getApplyDisciplineMap();
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nseit.generic.service.CommonService#getDistrictTalukaMapping()
	 */
	public Map<Integer, String> getTestCenterMaster() throws Exception {
		return commonDao.getTestCenterMaster();
	}

	@Override
	public Map<Integer, Integer> getSelectedPreferredCenterMaster(Users loggedInUser, String flag) throws Exception {
		return commonDao.getSelectedPreferredCenterMaster(loggedInUser, flag);
	}

	@Override
	public List<String> getTestCentresforDisplay(Users loggedInUser, String flag) throws Exception {
		return commonDao.getSelectedTestCntrListforPreview(loggedInUser, flag);
	}

	public Map<Integer, String> getStatusMasterMap() throws GenericException {
		return commonDao.getStatusMasterMap();
	}

	public Map<Integer, String> getPaymentMasterMap() throws GenericException {
		return commonDao.getPaymentMasterMap();
	}

	public Map<String, Integer> getPaymentMasterReverseMap() throws GenericException {
		return commonDao.getPaymentMasterReverseMap();
	}

	public Map<String, String> getStatusDescStatusMap() throws GenericException {
		return commonDao.getStatusDescStatusMap();
	}

	public Map<Integer, String> getStatusMasterMapForReport() throws GenericException {
		return commonDao.getStatusMasterMapForReport();
	}

	public int updateCandidateStatus(Users users, Integer statusId) throws GenericException {
		int updateVal = commonDao.updateCandidateStatus(users, statusId);
		return updateVal;
	}

	public Map<String, String> getCandidatesDataForEmailSMSForPayment(String userId) throws Exception {
		return commonDao.getCandidatesDataForEmailSMSForPayment(userId);
	}

	public Map<String, Integer> getEmailStatusMap() throws Exception {
		return commonDao.getEmailStatusMap();
	}

	public Map<String, Integer> getSmsStatusMap() throws Exception {
		return commonDao.getSmsStatusMap();
	}

	public Map<Integer, String> getCategoryListMap() throws Exception {
		return commonDao.getCategoryListMap();
	}

	public Map<Integer, String> getPostMasterListMap() throws Exception {
		return commonDao.getCategoryListMap();
	}

	@Override
	public Map<String, String> getStaticDataMap() throws Exception {
		return commonDao.getStaticDataMap();
	}
	
	public Map<String, Integer> getDateWindowMap() throws Exception {
		return commonDao.getDateWindowMap();
	}

	public Map<String, String> getCandidatesDataForEmailSMSForAdmitCard(String userId, Integer attempt) throws Exception {
		return commonDao.getCandidatesDataForEmailSMSForAdmitCard(userId, attempt);
	}

	public Map<String, String> getCandidatesDataForNewReg(String userId) throws Exception {
		return commonDao.getCandidatesDataForNewReg(userId);
	}

	public int[] insertEmailSMSDetailsForNewReg(List<EmailSMSTransactionBean> emailSMS, RegistrationBean registrationBean) {
		return commonDao.insertEmailSMSDetailsForNewReg(emailSMS, registrationBean);
	}

	public Map<String, String> getCandidatesDataForEmailSMSForPaymentFailure(String userId) throws Exception {
		return commonDao.getCandidatesDataForEmailSMSForPaymentFailure(userId);
	}

	public Map<String, String> getCandidatesDataForEmailSMSForTestCenterAllocation(String userId) throws Exception {
		return commonDao.getCandidatesDataForEmailSMSForTestCenterAllocation(userId);
	}

	public Map<String, String> getAcademicMasterMap() throws Exception {
		return commonDao.getAcademicMasterMap();
	}

	public Map<String, String> getAcademicMandatoryMap() throws Exception {
		return commonDao.getAcademicMandatoryMap();
	}

	public Map getDegreeMappingDetails() throws Exception {
		return commonDao.getDegreeMappingDetails();
	}

	public Map<String, String> getAcademicMasterReverseMap() throws Exception {
		return commonDao.getAcademicMasterReverseMap();
	}

	public int[] insertEmailSMSDetailsForForgotUserId(List<EmailSMSTransactionBean> emailSMS, String loggedInUserName) throws Exception {
		return commonDao.insertEmailSMSDetailsForForgotUserId(emailSMS, loggedInUserName);
	}

	public int[] insertEmailSMSDetailsForForgotPassword(List<EmailSMSTransactionBean> emailSMS,
			String loggedInUserName) throws Exception{
		return commonDao.insertEmailSMSDetailsForForgotUserId(emailSMS, loggedInUserName);
	}
	
	public Map<String, String> getCandidatesDataForForgotUserId(String userId) throws Exception {
		return commonDao.getCandidatesDataForForgotUserId(userId);
	}

	public Map<String, String> getCandidatesDataForForgotPassword(String userId) throws Exception {
		return commonDao.getCandidatesDataForForgotPassword(userId);
	}
	
	public Map<String, String> getReferenceValueActiveMap() throws Exception {
		return commonDao.getReferenceValueActiveMap();
	}

	public Map<Integer, String> getDegreeMap() throws Exception {
		return commonDao.getDegreeMap();
	}

	public Map<Integer, Map<Integer, String>> getUniversityMap() throws Exception {
		return commonDao.getUniversityMap();
	}

	public UploadManagementBean getPostDetails(Users loggedInUser) throws Exception {

		return commonDao.getPostDetails(loggedInUser);

	}

	public UploadManagementBean getPostEntrollmentTestDetails(Users loggedInUser) throws Exception {

		return commonDao.getPostEntrollmentTestDetails(loggedInUser);

	}

	// shekharc apply page confirmation msg start
	// shekharc apply page confirmation msg end
	@Override
	public String getTransactionUniqueNumber() throws Exception {
		return commonDao.getTransactionUniqueNumber();
	}

	@Override
	public int insertCandidateAuditrail(Users users, String action, String auditTrail) throws Exception {
		return commonDao.insertCandidateAuditrail(users, action, auditTrail);
	}

	public Map<Integer, Map<Integer, String>> getAcademicMapping() throws Exception {

		Map<Integer, Map<Integer, String>> dataMap = new LinkedHashMap<Integer, Map<Integer, String>>();
		try {
			dataMap = commonDao.getAcademicMapping();
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return dataMap;
	}

	public Map<String, String> getCandidateLabelMap() throws Exception {
		return commonDao.getCandidateLabelMap();
	}

	public int insertOTPDetails(EmailSMSTransactionBean emailSMSBean, String emailId) throws Exception {
		try {
			return commonDao.insertOTPDetails(emailSMSBean, emailId);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
	}

	public int updateEmailSmsOTPDetails(EmailSMSTransactionBean emailSMSBean, String emailId) throws Exception {
		try {
			return commonDao.updateEmailSmsOTPDetails(emailSMSBean, emailId);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
	}

	public List<CandidateBean> getCandidateAppliedPost(Users loggedInUser, int status) throws Exception {
		return commonDao.getCandidateAppliedPost(loggedInUser, status);
	}

	@Override
	public int updateForgotPassword(String userid, String key) throws Exception {
		return commonDao.updateForgotPassword(userid, key);
	}

	@Override
	public Long getUserFK(String customerId, String txnReferenceNo) throws Exception {
		return commonDao.getUserFK(customerId, txnReferenceNo);
	}

	@Override
	public void insertCheckboxValue(String checkBoxValue, long userId) throws Exception {
		commonDao.insertCheckboxValue(checkBoxValue, userId);
	}

	public String getCandidateStatus(String userId) throws Exception {
		return commonDao.getCandidateStatus(userId);
	}

	@Override
	public int getTrainingCentreFk(long userId) throws Exception {
		return commonDao.getTrainingCentreFK(userId);
	}

	@Override
	public Map<Integer, String> getNativityMap() throws Exception {

		return commonDao.getNativityMap();
	}

	@Override
	public Boolean getEligibilityToEditSubjectApplied(long userId) throws Exception {
		return commonDao.getEligibilityToEditSubjectApplied(userId);
	}

	@Override
	public String getPostAppliedFor(Long usrId) throws Exception {
		return commonDao.getPostAppliedFor(usrId);
	}

	@Override
	public Map<String, String> getCandidatesDataAutoApprovePayment(String userId) throws Exception {
		return commonDao.getCandidatesDataAutoApprovePayment(userId);
	}

	@Override
	public Map<Integer, String> getSubDegreeMap() throws Exception {
		return commonDao.getSubDegreeMap();
	}

	public String getRegistrationId(Users loggedInUser) throws Exception{
		String result = "";
		try {
			String discipline = commonDao.getPostAppliedFor(loggedInUser.getUserId());
			if(null!=discipline && !discipline.equals(""))
			{
				if (discipline.equals("Ph.D (Development Studies)")) {
					result = ("IGIP" + loggedInUser.getUsername().substring(5));
				} else if (discipline.equals("M.Sc (Economics)")) {
					result = ("IGIM" + loggedInUser.getUsername().substring(5));
				} 
			}
		} catch (Exception ex) {
			result = "";
			logger.fatal(ex, ex);
		}
		return result;
	}
	
	// for Covid Duty Certificate page
		@Override
		public Map<Integer, String> getSignedByDataMap() throws Exception {
			return commonDao.getSignedByDataMap();
		}
		
		@Override
		public String getCandidateDetailsByLoginId(String loginId) {
			return commonDao.getCandidateDetailsByLoginId(loginId);
		}
	
	/*@Override
	public Boolean getcandidatEditStatus(long userId) throws Exception {
		Boolean flagval=false;
		flagval=commonDao.getcandidatEditStatus(userId);
		return flagval;
	}*/
}
