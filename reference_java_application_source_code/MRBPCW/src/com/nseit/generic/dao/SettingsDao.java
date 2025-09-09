package com.nseit.generic.dao;

import java.sql.Timestamp;
import java.util.List;

import com.nseit.generic.models.SettingsBean;
import com.nseit.generic.models.Users;

public interface SettingsDao {
	
	
	public int insertEmailSMSSettingDetails (SettingsBean settingsBean,Users loggedInUser) throws Exception;
		
	public void downloadTrainingDetails () throws Exception;
	
	public int insertTrainingDetails (String filePath,String module)throws Exception;

	public void downloadEligibleCriteria ()throws Exception;
	
	public int insertEligibleCriteria (String filePath,String module)throws Exception;
	
	public void downloadTestInstruction () throws Exception;
	
	public int insertTestInstruction (String filePath,String module) throws Exception;
	
	public void downloadFAQ () throws Exception;
	
	public int insertFAQ (String filePath,String module) throws Exception;
	

	
	public void getRegistrationDateRangeDetails (String testId) throws Exception;
	

	
	public void getEnrollmentDateRangeDetails (String testId) throws Exception;
	

	
	public void getPaymentDateRangeDetails (String testId) throws Exception;
	

	
	public void getRetestDateRangeDetails (String testId) throws Exception;
	
	
	
	
	
	public void getUploadManagement () throws Exception;

	public void getDateDefinitionDetails () throws Exception;

	
	public List<SettingsBean> getAllDates() throws Exception;
	

	public int updateDateRangeDetails(Timestamp startTime,Timestamp endTime,Users users, int datePK) throws Exception;
	
	
	public String getActivityDetails(String activityName) throws Exception;
	
	
	public SettingsBean getEmailSMSSettingDetailsForDiscipline (SettingsBean settingsBean) throws Exception;

}
