package com.nseit.generic.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.nseit.generic.models.AcademicDetailsBean;
import com.nseit.generic.models.CommonBean;
import com.nseit.generic.models.SettingsBean;
import com.nseit.generic.models.Users;

public interface SettingsService {

	public int insertEmailSMSSettingDetails(SettingsBean settingsBean,Users loggedInUser) throws Exception;
	
	public void downloadSettingDetails () throws Exception;
	
	public int insertSettingDetails (String filePath,String module) throws Exception;
	
	public int updateDateRangeDetails(Timestamp startTime,Timestamp endTime,Users users, int datePK) throws Exception;

	
	
	// for loading the avalialble data 
	public Map<Long, List<SettingsBean>> getDateRangeDetails () throws Exception;

	public void getUploadManagement () throws Exception;

	public String getActivityDetails(String activityName) throws Exception;

	public SettingsBean getEmailSMSDetailsForDiscipline(SettingsBean settingsBean) throws Exception;
}
