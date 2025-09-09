package com.nseit.generic.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nseit.generic.dao.SettingsDao;
import com.nseit.generic.models.SettingsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.SettingsService;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.GenericServiceException;

public class SettingsServiceImpl implements SettingsService {
	
	private SettingsDao settingsDao;
	
	public void setSettingsDao(SettingsDao settingsDao) {
		this.settingsDao = settingsDao;
	}



	public int insertEmailSMSSettingDetails(SettingsBean settingsBean,Users loggedInUser) throws Exception{
		
		int emailSMS = 0;
		try {
			emailSMS = settingsDao.insertEmailSMSSettingDetails(settingsBean,loggedInUser);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		
		return emailSMS;
	}

	
	
	
	public void downloadSettingDetails () throws Exception{
		
		try {
			settingsDao.downloadTrainingDetails ();
			
			settingsDao.downloadEligibleCriteria ();
			
			settingsDao.downloadTestInstruction ();
			
			settingsDao.downloadFAQ ();
			
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		
		
	}
	
	
	
	public int insertSettingDetails (String filePath,String module) throws Exception{
		
		int trainingUpload = 0;
		try {
			if (module!=null && !module.equals("") && module.equals("TrainingUpload")){
				trainingUpload = settingsDao.insertTrainingDetails(filePath,module);
			}
			
			if (module!=null && !module.equals("") && module.equals("EligibilityUpload")){
			    trainingUpload = settingsDao.insertEligibleCriteria(filePath,module);
			}
			if (module!=null && !module.equals("") && module.equals("TestInstructionUpload")){
			    trainingUpload = settingsDao.insertTestInstruction(filePath,module);
			}
			if (module!=null && !module.equals("") && module.equals("FAQUpload")){
			    trainingUpload = settingsDao.insertFAQ(filePath,module);
			}
			
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		
		return trainingUpload;
	}

	
	public int updateDateRangeDetails(Timestamp startTime,Timestamp endTime,Users users,int datePK) throws Exception{
		
		int updateVal = 0;
		try {
			updateVal = settingsDao.updateDateRangeDetails(startTime,endTime,users,datePK);
			
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		
		return updateVal;
	}
	
	/**
	 * @author Pankaj Sh
	 * Get the existing date definition
	 * return Map
	 * @throws Exception 
	 */
	
	
	public Map<Long, List<SettingsBean>> getDateRangeDetails () throws Exception{
		
		List<SettingsBean> dateList = null;
		Map<Long, List<SettingsBean>> dateMap = new HashMap<Long, List<SettingsBean>>();
		try {
			// new method
			dateList = settingsDao.getAllDates();
			
			List<SettingsBean> appFormDateList = new ArrayList<SettingsBean>();
			List<SettingsBean> approveCanDateList = new ArrayList<SettingsBean>();
			List<SettingsBean> otbsDateList = new ArrayList<SettingsBean>();
			List<SettingsBean> attemptOneDateList = new ArrayList<SettingsBean>();
			List<SettingsBean> attemptTwoList = new ArrayList<SettingsBean>();
			List<SettingsBean> paymentDateList = new ArrayList<SettingsBean>();
			
			if (dateList!=null && !dateList.isEmpty()){
				for (SettingsBean settingsBean : dateList) {
					if (settingsBean!=null){
									    		
			    		if(settingsBean.getDateDefinitionType()!=null && settingsBean.getDateDefinitionType() == GenericConstants.APPLICATION_FORM_DATES){
			    			appFormDateList.add(settingsBean);
						}
						
			    		if(settingsBean.getDateDefinitionType()!=null && settingsBean.getDateDefinitionType() == GenericConstants.APPROVE_CANDIDATE_DATES){
			    			approveCanDateList.add(settingsBean);
						}
						
			    		if(settingsBean.getDateDefinitionType()!=null && settingsBean.getDateDefinitionType() == GenericConstants.OTBS_DATES){
			    			otbsDateList.add(settingsBean);
						}
						
			    		if(settingsBean.getDateDefinitionType()!=null && settingsBean.getDateDefinitionType() == GenericConstants.ATTEMPT_ONE_DATES){
			    			attemptOneDateList.add(settingsBean);
						}
						
			    		if(settingsBean.getDateDefinitionType()!=null && settingsBean.getDateDefinitionType() == GenericConstants.ATTEMPT_TWO_DATES){
			    			attemptTwoList.add(settingsBean);
                        }
			    		
			    		if(settingsBean.getDateDefinitionType()!=null && settingsBean.getDateDefinitionType() == GenericConstants.PAYMENT_DATES){
			    			paymentDateList.add(settingsBean);
						}
						
					}
				}
			}
			
			dateMap.put(new Long(1), appFormDateList);
			dateMap.put(new Long(2), approveCanDateList);
			dateMap.put(new Long(3), otbsDateList);
			dateMap.put(new Long(4), attemptOneDateList);
	        dateMap.put(new Long(5), attemptTwoList);
	        dateMap.put(new Long(6), paymentDateList);
			
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		
		return dateMap;
	}
	
	
	
	
	
	
	public void getUploadManagement () throws Exception{
		
		try {
			settingsDao.getUploadManagement();
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		
	}

	public String getActivityDetails(String activityName) throws Exception {
		
		String activityDesc = null;
		try {
			activityDesc = settingsDao.getActivityDetails(activityName);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		
		return activityDesc;
	}

	public SettingsBean getEmailSMSDetailsForDiscipline(SettingsBean settingsBean) throws Exception{
		
		SettingsBean newSettingsBean = null;
		try {
			newSettingsBean  = settingsDao.getEmailSMSSettingDetailsForDiscipline(settingsBean);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		
		return newSettingsBean;
		
	}
	
}
