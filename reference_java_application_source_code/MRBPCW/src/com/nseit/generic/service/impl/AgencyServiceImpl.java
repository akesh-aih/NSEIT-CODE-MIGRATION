package com.nseit.generic.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nseit.generic.dao.AgencyDao;
import com.nseit.generic.models.AgencyBean;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.CommonBean;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.RegistrationBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.AgencyService;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.GenericServiceException;

/**
 * The Class AgencyServiceImpl.
 */
public class AgencyServiceImpl implements AgencyService{
	
	/** The agency dao. */
	private AgencyDao agencyDao;

	/**
	 * Sets the agency dao.
	 *
	 * @param agencyDao the new agency dao
	 */
	public void setAgencyDao(AgencyDao agencyDao) {
		this.agencyDao = agencyDao;
	}
	
	/* (non-Javadoc)
	 * @see com.nseit.generic.service.AgencyService#generateLoginCregentials(com.nseit.generic.models.AgencyBean, com.nseit.generic.models.Users)
	 */
	public Integer generateLoginCregentials(AgencyBean agencyBean, Users users) throws Exception
	{
		
		
		Integer value = 0;
		try{
			value = agencyDao.generateLoginCregentials(agencyBean, users);
		}
		catch (Exception e) {
			throw new GenericServiceException(e);
		}
		
		
		return value;
	}
	
	/* (non-Javadoc)
	 * @see com.nseit.generic.service.AgencyService#getLoginCredentials()
	 */
	public List<List<String>> getLoginCredentials()throws Exception{
		
		List<List<String>> list = new ArrayList<List<String>>();
		try{
			list = agencyDao.getLoginCredentials();
		}
		catch (Exception e) {
			throw new GenericServiceException(e);
		}
		
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.nseit.generic.service.AgencyService#getTestMasterDetails()
	 */
	public Map<Integer, String> getTestMasterDetails()throws Exception{
		
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		try{
			map = agencyDao.getTestMasterDetails();
		}
		catch (Exception e) {
			throw new GenericServiceException(e);
		}
		
		return map;
	}
	
	/* (non-Javadoc)
	 * @see com.nseit.generic.service.AgencyService#exportToExcel()
	 */
	public List<List<String>> exportToExcel()throws Exception{
		
		List<List<String>> detailList = new ArrayList<List<String>>();
		try{
			detailList = agencyDao.exportToExcel();
		}
		catch (Exception e) {
			
			throw new GenericServiceException(e);
		}
		
		return detailList;
	}
	
	/* (non-Javadoc)
	 * @see com.nseit.generic.service.AgencyService#exportAllDataToExcel()
	 */
	public List<List<String>> exportAllDataToExcel()throws Exception{
		
		List<List<String>> detailList = new ArrayList<List<String>>();
		try{
			detailList = agencyDao.exportAllDataToExcel();
		}
		catch (Exception e) {
			
			throw new GenericServiceException(e);
		}
		
		return detailList;
	}
	
	/* (non-Javadoc)
	 * @see com.nseit.generic.service.AgencyService#exportDataForRequestIDToExcel(com.nseit.generic.models.AgencyBean)
	 */
	public List<List<String>> exportDataForRequestIDToExcel(AgencyBean agencyBean)throws Exception{
		
		List<List<String>> detailList = new ArrayList<List<String>>();
		try{
			detailList = agencyDao.exportDataForRequestIDToExcel(agencyBean);
		}
		catch (Exception e) {
			
			throw new GenericServiceException(e);
		}
		
		return detailList;
	}
	
	/* (non-Javadoc)
	 * @see com.nseit.generic.service.AgencyService#getCandidateLoginCount()
	 */
	public long getCandidateLoginCount()throws Exception
	{
		
		try
		{
			return agencyDao.getCandidateLoginCount();
		}
		catch (Exception e)
		{
			throw new GenericServiceException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nseit.generic.service.AgencyService#getGcetAdminHomeData()
	 */
	public Map<String,Integer> getGcetAdminHomeData() throws Exception
	{
		try{
			return agencyDao.getGcetAdminHomeData();
		}catch (Exception e){
			throw new GenericServiceException(e);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.nseit.generic.service.AgencyService#getTestSlots()
	 */
	public List<String> getTestSlots() throws Exception{
		
		List<String> testSlots = null;
		try {
				testSlots = agencyDao.getTestSlots();
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		
		return testSlots;
	}
	
	/* (non-Javadoc)
	 * @see com.nseit.generic.service.AgencyService#getDiscipline()
	 */
	public List<AgencyBean> getDiscipline() throws Exception{
		
		List<AgencyBean> disciplineList=  null;
		try {
				disciplineList = agencyDao.getDiscipline();
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		
		return disciplineList;
	}
	
	/* (non-Javadoc)
	 * @see com.nseit.generic.service.AgencyService#insertEmailSMSDetails(com.nseit.generic.models.AgencyBean, java.lang.String, com.nseit.generic.models.Users, java.lang.String, java.lang.String, java.lang.String, com.nseit.generic.models.AgencyBean)
	 */
	public int insertEmailSMSDetails(AgencyBean agencyBean,String emailSmsFlag,Users users,String mailAddress,String mobileNo,String userId,AgencyBean agencyBean2) throws Exception{
		
		int insertEmailSMSDetails = 0;
		int insertSMSDetails = 0;
		int insert = 0;
		
		try {
			if (mailAddress!=null&& !mailAddress.equals("")){
				insertEmailSMSDetails = agencyDao.insertEmailSMSDetails(agencyBean, emailSmsFlag, users,mailAddress,mobileNo,userId,agencyBean2);
			}
			if (mobileNo!=null && !mobileNo.equals("")&& agencyBean.getSmsContent()!=null && !agencyBean.getSmsContent().equals("")){
				insertSMSDetails = agencyDao.insertEmailSMSDetailsForSms(agencyBean, "S", users,mobileNo,userId,agencyBean2);
			}
			
			if (insertEmailSMSDetails > 0){
				insert = 1;
			}else {
				insert = 0;
			}
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		
		return insert;
		
	}
	
	/* (non-Javadoc)
	 * @see com.nseit.generic.service.AgencyService#getDataForGenericMails(com.nseit.generic.models.AgencyBean)
	 */
	public List<AgencyBean> getDataForGenericMails(AgencyBean agencyBean) throws Exception {
		
		List<AgencyBean> candidateDataForGenericMailsList = null;
		try {
			candidateDataForGenericMailsList = agencyDao.getDataForGenericMails(agencyBean);
			
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		
		return candidateDataForGenericMailsList;
	}
	
	/* (non-Javadoc)
	 * @see com.nseit.generic.service.AgencyService#insertUserDetails(java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, com.nseit.generic.models.Users)
	 */
	public int insertUserDetails(String userId,String password ,String ipin,Integer discipline, String emailId, String mobileNo, Users users)throws Exception
	{
		
		int insert =0;
		try {
			insert = agencyDao.insertUserDetails(userId, password, ipin,discipline, emailId, mobileNo, users);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		
		return insert;
	}

	/* (non-Javadoc)
	 * @see com.nseit.generic.service.AgencyService#getHelpCenterList()
	 */
	public List<CommonBean> getHelpCenterList() throws Exception 
	{
		try {
			return agencyDao.getHelpCenterList();
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nseit.generic.service.AgencyService#updatePasswordForHelpCenter(com.nseit.generic.models.AgencyBean)
	 */
	public int updatePasswordForHelpCenter(AgencyBean agencyBean) throws Exception{
		try {
			return agencyDao.updatePasswordForHelpCenter(agencyBean);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.nseit.generic.service.AgencyService#generateUserID(com.nseit.generic.models.RegistrationBean)
	 */
	public RegistrationBean generateUserID(RegistrationBean registrationBean) throws Exception
	{
		
		try{
			return agencyDao.generateUserID(registrationBean);
		}
		catch (Exception e) {
			throw new GenericException(e);
		}
	}
	
	
	public int getCandidateDetailsListCountForReport(AgencyBean agencyBean)throws Exception{
	        int candidateDetailsListCount = 0;
	        try {
	        	candidateDetailsListCount = agencyDao.getCandidateDetailsListCountForReport(agencyBean);
	        } catch (Exception e) {
	            throw new GenericException(e);
	        }

	        return candidateDetailsListCount;
	}
	
	/* (non-Javadoc)
	 * @see com.nseit.generic.service.AgencyService#getCandidateDetailsListForReport(com.nseit.generic.models.AgencyBean)
	 */
	public List<CandidateBean> getCandidateDetailsListForReport(AgencyBean agencyBean,Pagination pagination)throws Exception{
	        List<CandidateBean> candidateDetailsList = null;
	        try {
	            candidateDetailsList = agencyDao.getCandidateDetailsListForReport(agencyBean,pagination);
	        } catch (Exception e) {
	            throw new GenericException(e);
	        }

	        return candidateDetailsList;
	}
	
	public List<CandidateBean> getCandidateDetailsListForReport(AgencyBean agencyBean)throws Exception{
        List<CandidateBean> candidateDetailsList = null;
        try {
            candidateDetailsList = agencyDao.getCandidateDetailsListForReport(agencyBean);
        } catch (Exception e) {
            throw new GenericException(e);
        }

        return candidateDetailsList;
}
	
	public int getCandidateDetailsListCountForReportForApproved(AgencyBean agencyBean, String validateValue)throws Exception{
	    int candidateDetailsListCount = 0;
        try {
        	candidateDetailsListCount = agencyDao.getCandidateDetailsListCountForReportForApproved(agencyBean, validateValue);
        } catch (Exception e) {
            throw new GenericException(e);
        }

        return candidateDetailsListCount;
	}
	
	public List<CandidateBean> getCandidateDetailsListForReportForApproved(AgencyBean agencyBean, String validateValue,Pagination pagination)throws Exception{
	    List<CandidateBean> candidateDetailsList = null;
        try {
            candidateDetailsList = agencyDao.getCandidateDetailsListForReportForApproved(agencyBean, validateValue,pagination);
        } catch (Exception e) {
            throw new GenericException(e);
        }

        return candidateDetailsList;
	}
	public List<CandidateBean> getCandidateDetailsListForReportForApproved(AgencyBean agencyBean, String validateValue)throws Exception{
	    List<CandidateBean> candidateDetailsList = null;
        try {
            candidateDetailsList = agencyDao.getCandidateDetailsListForReportForApproved(agencyBean, validateValue);
        } catch (Exception e) {
            throw new GenericException(e);
        }

        return candidateDetailsList;
	}
	
	@Override
	public long getTrainingCentreForAccUser(Users loggedInUser)
			throws Exception {
		long testCentreID = 0;
		try {
			testCentreID = agencyDao.getTrainingCentreForAccUser(loggedInUser);
		} catch (Exception e) {
			testCentreID = 0;
		}

		return testCentreID;
	}
	
	@Override
	public Map<String, Integer> getGcetACCHomeData(long testCentreID) throws Exception {
		try{
			return agencyDao.getGcetACCHomeData(testCentreID);
		}catch (Exception e){
			throw new GenericServiceException(e);
		}
	}
	
	@Override
	public List<CandidateBean> getPondiCategoryWiseReport() throws Exception {
		 List<CandidateBean> categoryWiseReportList = null;
	        try {
	        	categoryWiseReportList = agencyDao.getPondiCategoryWiseReport();
	        } catch (Exception e) {
	            throw new GenericException(e);
	        }

	        return categoryWiseReportList;
	}

	@Override
	public List<CandidateBean> getPondiTestCenterWiseReport() throws Exception {
		 List<CandidateBean> testCenterWiseReportList = null;
	        try {
	        	testCenterWiseReportList = agencyDao.getPondiTestCenterWiseReport();
	        } catch (Exception e) {
	            throw new GenericException(e);
	        }

	        return testCenterWiseReportList;
	}

	
}
