package com.nseit.generic.service;

import java.util.List;
import java.util.Map;

import com.nseit.generic.models.AgencyBean;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.CommonBean;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.RegistrationBean;
import com.nseit.generic.models.Users;

public interface AgencyService {
	
	public Integer generateLoginCregentials(AgencyBean agencyBean, Users users) throws Exception;
	
	public List<List<String>> getLoginCredentials()throws Exception;
	
	public Map<Integer, String> getTestMasterDetails()throws Exception;
	
	public List<List<String>> exportToExcel()throws Exception;
	
	public List<List<String>> exportAllDataToExcel()throws Exception;
	
	public List<List<String>> exportDataForRequestIDToExcel(AgencyBean agencyBean)throws Exception;
	
	public long getCandidateLoginCount()throws Exception;
	
	public Map<String,Integer> getGcetAdminHomeData()throws Exception;

	public List<String> getTestSlots()throws Exception;
	
	
	public List<AgencyBean> getDiscipline()throws Exception;
	
	public int insertEmailSMSDetails(AgencyBean agencyBean,String emailSmsFlag,Users users,String mailAddress,String mobileNo,String userId,AgencyBean agencyBean2)throws Exception;
	
	public List<AgencyBean> getDataForGenericMails(AgencyBean agencyBean)throws Exception;
	
	public int insertUserDetails(String userId,String password ,String ipin,Integer discipline, String emailId, String mobileNo, Users users)throws Exception;
	
	public List<CommonBean> getHelpCenterList() throws Exception;
	public int updatePasswordForHelpCenter(AgencyBean agencyBean) throws Exception;
	
	public RegistrationBean generateUserID(RegistrationBean registrationBean) throws Exception;
	
	public List<CandidateBean> getCandidateDetailsListForReport(AgencyBean agencyBean,Pagination pagination)throws Exception;
	public int getCandidateDetailsListCountForReport(AgencyBean agencyBean)throws Exception;
	
	public List<CandidateBean> getCandidateDetailsListForReportForApproved(AgencyBean agencyBean, String validateValue,Pagination pagination)throws Exception;
	public int getCandidateDetailsListCountForReportForApproved(AgencyBean agencyBean, String validateValue)throws Exception;
	public List<CandidateBean> getCandidateDetailsListForReportForApproved(AgencyBean agencyBean, String validateValue)throws Exception;
	public List<CandidateBean> getCandidateDetailsListForReport(AgencyBean agencyBean)throws Exception;
	
	public long getTrainingCentreForAccUser(Users loggedInUser) throws Exception;
	
	public Map<String, Integer> getGcetACCHomeData(long testCentreID) throws Exception;
	
	
	public List<CandidateBean> getPondiCategoryWiseReport()throws Exception;

	public List<CandidateBean> getPondiTestCenterWiseReport()throws Exception;

}
