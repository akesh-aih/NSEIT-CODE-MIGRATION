package com.nseit.generic.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.nseit.generic.dao.BaseDAO;
import com.nseit.generic.dao.SettingsDao;
import com.nseit.generic.models.SettingsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.queries.SettingsQueries;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.LoggerHome;

public class SettingsDaoImpl  extends BaseDAO implements SettingsDao{
	
	private JdbcTemplate writeJdbcTemplate;
	
	public void setWriteJdbcTemplate(JdbcTemplate writeJdbcTemplate) {
		this.writeJdbcTemplate = writeJdbcTemplate;
	}
	
	private Logger logger = LoggerHome.getLogger(getClass());
	
	public int insertEmailSMSSettingDetails (SettingsBean settingsBean,Users loggedInUser) throws Exception
	{
		
		
		int insertEmailSMS = 0;
		int updateEmailSMS = 0;
		String emailApplicable = null;
		String smsApplicable = null;
		String createdBy = "";
		String smscontent=null;
		String emailcontent=null;
		
		if(settingsBean.getEmailContent()!=null&&!settingsBean.getEmailContent().equals(""))
		 //emailcontent = JavascriptAESDecrypter.decrypt(settingsBean.getEmailContent(), "pass!dt@12-!e", 256);
			emailcontent = settingsBean.getEmailContent();
		if(settingsBean.getSmsContent()!=null && !settingsBean.getSmsContent().equals(""))
		 //smscontent =  JavascriptAESDecrypter.decrypt(settingsBean.getSmsContent(), "pass!dt@12-!e", 256);
			smscontent =  settingsBean.getSmsContent();
		
		settingsBean.setEmailContent(emailcontent);
		settingsBean.setSmsContent(smscontent);
		if (loggedInUser!=null){
			createdBy = loggedInUser.getUsername();
		}
		
		try {
			SettingsBean newSettingsBean = null;
			newSettingsBean = getEmailSMSSettingDetailsForDiscipline(settingsBean);
		
			
			if (settingsBean!=null && settingsBean.getEmailChkBox().equals("true")){
				emailApplicable = "Y";
			}
			if (settingsBean!=null && settingsBean.getEmailChkBox().equals("false")){
				emailApplicable = "N";
			}
			
			if (settingsBean!=null && settingsBean.getSmsChkBox().equals("true")){
				smsApplicable = "Y";
			}
			if (settingsBean!=null && settingsBean.getSmsChkBox().equals("false")){
				smsApplicable = "N";
			}
			if (newSettingsBean == null){
				
				insertEmailSMS = writeJdbcTemplate.update(SettingsQueries.INSERT_EMAIL_SMS_DETAILS,
						new Object[]{
						Integer.parseInt(settingsBean.getDisciplineType()),
						Integer.parseInt(settingsBean.getActivityId()),
						settingsBean.getEmailContent(),
						settingsBean.getSmsContent(),
						settingsBean.getEmailSmsSubject(),
						settingsBean.getEmailSmsCCAddress(),
						//"ddd",//bcc address removed this by vijaya for
						emailApplicable,
						smsApplicable,
						createdBy//CREATED BY
						}
				); 
			}else{
				updateEmailSMS = writeJdbcTemplate.update(SettingsQueries.UPDATE_EMAIL_SMS_DETAILS,
						new Object[]{
						Integer.parseInt(settingsBean.getActivityId()),
						settingsBean.getEmailContent(),
						settingsBean.getSmsContent(),
						settingsBean.getEmailSmsSubject(),
						settingsBean.getEmailSmsCCAddress(),
						//"bcc", removed this by vijaya for
						emailApplicable,
						smsApplicable,
						createdBy,//UPDATED BY
						Integer.parseInt(settingsBean.getDisciplineType()),
						Integer.parseInt(settingsBean.getActivityId())
						}
				); 
			}
			
		} catch (Exception e) {
			throw new GenericException(e);
		}
		
		if (insertEmailSMS>0){
			return insertEmailSMS;
		}
		else{
			return updateEmailSMS;
		}
	}
	
	@SuppressWarnings("unchecked")
	public SettingsBean getEmailSMSSettingDetailsForDiscipline (SettingsBean settingsBean) throws Exception{
		
		List<SettingsBean> emailSMSDetailsList = null;
		try {
			emailSMSDetailsList =  getJdbcTemplate().query(
					SettingsQueries.GET_EMAIL_SMS_DETAILS, new Object[]{
							Integer.parseInt(settingsBean.getDisciplineType()),
							Integer.parseInt(settingsBean.getActivityId())} ,
							new RowMapper<SettingsBean>(){   
		            	public SettingsBean mapRow(ResultSet rs, int rowNum){   
		            		SettingsBean settingsBean = new SettingsBean();
		            		
		            		try {
		            			settingsBean.setDisciplineId(rs.getString("OSEM_TEST_FK"));
		            			settingsBean.setActivityId(rs.getString("OSEM_ACTIVITY_FK"));
		            			settingsBean.setEmailChkBox(rs.getString("OSEM_MAIL_APPLICABLE"));
		            			settingsBean.setSmsChkBox(rs.getString("OSEM_SMS_APPLICABLE"));
		            			settingsBean.setEmailSmsSubject(rs.getString("OSEM_SUBJECT"));
		            			settingsBean.setEmailSmsCCAddress(rs.getString("OSEM_CC_ADDRESS"));
		            			settingsBean.setEmailContent(rs.getString("OSEM_MAIL_OBJECT"));
		            			settingsBean.setSmsContent(rs.getString("OSEM_SMS_OBJECT"));
		            			
		            				
							} catch (Exception e) {
								logger.fatal(e,e);
							}
		            		return settingsBean;
		                 }   
		            }); 
		} catch (Exception e) {
			//throw new GenericException(e);
		}
		
			
		if(emailSMSDetailsList != null && !emailSMSDetailsList.isEmpty()){
			return emailSMSDetailsList.get(0);
		}else{
			return  null;
		}
	}
	
	
	
	
	
	public void downloadTrainingDetails () throws Exception{
		
		try {
				
			} catch (Exception e) {
				throw new GenericException(e);
			}
			
	}
	
	
	public int insertTrainingDetails (String filePath,String module) throws Exception{
		
		String updatedBy = "abc";
		int updateVal = 0;
		
		try {
			updateVal = writeJdbcTemplate.update(SettingsQueries.UPDATE_TRAINING_DETAILS_UPLOAD,
							new Object[]{filePath,updatedBy,module}
				); 
		} catch (Exception e) {
			throw new GenericException(e);
		}
		
			return updateVal;
	}

	public void downloadEligibleCriteria () throws Exception{
		
		try {
				
			} catch (Exception e) {
				throw new GenericException(e);
			}
			
			
	}
	
	public int insertEligibleCriteria (String filePath,String module) throws Exception{
		String updatedBy = "abc";
		int updateTraining = 0;
		
		try {
				updateTraining = writeJdbcTemplate.update(SettingsQueries.UPDATE_TRAINING_DETAILS_UPLOAD,
							new Object[]{filePath,updatedBy,module}
				); 
		} catch (Exception e) {
			throw new GenericException(e);
			}
		
			return updateTraining;
	}
	
	public void downloadTestInstruction () throws Exception {
		try {
				
			} catch (Exception e) {
				throw new GenericException(e);
			}
		
	}
	
	public int insertTestInstruction (String filePath,String module) throws Exception{
		String updatedBy = "abc";
		int updateTraining = 0;
		
		try {
				updateTraining = writeJdbcTemplate.update(SettingsQueries.UPDATE_TRAINING_DETAILS_UPLOAD,
							new Object[]{filePath,updatedBy,module}
				); 
			
		} catch (Exception e) {
				throw new GenericException(e);
		}
		
			return updateTraining;
	}
	
	public void downloadFAQ ()throws Exception{
		try {
				
			} catch (Exception e) {
				throw new GenericException(e);
			}
	}
	
	public int insertFAQ (String filePath,String module) throws Exception{
		
		String updatedBy = "abc";
		int updateTraining = 0;
		
		try {
				updateTraining = writeJdbcTemplate.update(SettingsQueries.UPDATE_TRAINING_DETAILS_UPLOAD,
							new Object[]{filePath,updatedBy,module}
				); 
			
		} catch (Exception e) {
			throw new GenericException(e);
			}
		
			return updateTraining;
	}
	

	public void getRegistrationDateRangeDetails (String testId) throws Exception{
		try {
				
			} catch (Exception e) {
				throw new GenericException(e);
			}
	}
	
	
	
	public void getEnrollmentDateRangeDetails (String testId) throws Exception{
		try {
				
			} catch (Exception e) {
				throw new GenericException(e);
			}
	}
	
	
	
	public void getPaymentDateRangeDetails (String testId) throws Exception{
		try {
				
			} catch (Exception e) {
				throw new GenericException(e);
			}
	}
	
	
	
	public void getRetestDateRangeDetails (String testId) throws Exception{
		try {
				
			} catch (Exception e) {
				throw new GenericException(e);
			}
	}
	
	
	//
	public void getUploadManagement () throws Exception{
		try {
			} catch (Exception e) {
				throw new GenericException(e);
			}
	}
	
	
	//
	public void getDateDefinitionDetails () throws Exception{
		try {
			} catch (Exception e) {
				throw new GenericException(e);
			}
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	// new method added for getting all the dates
	public List<SettingsBean> getAllDates() throws Exception{
		List<SettingsBean> dateList = null;
		
	try {
		dateList =  getJdbcTemplate().query(
				SettingsQueries.GET_TEST_DATES, new Object[]{},new RowMapper<SettingsBean>(){   
		        	public SettingsBean mapRow(ResultSet rs, int rowNum){   
		        		SettingsBean settingsBean = new SettingsBean();
		        		try {
		            		settingsBean.setDateDefinitionType(rs.getInt("ODW_DATE_WINDOW_PK"));	
		        			settingsBean.setStartDate(rs.getTimestamp("ODW_START_DATE"));
		            		settingsBean.setEndDate(rs.getTimestamp("ODW_END_DATE"));
		            			
						} catch (SQLException e) {
							logger.fatal(e, e);
						}
		        		return settingsBean;
		             }   
		        });  
		
	} catch (Exception e) {
		throw new GenericException(e);
	}
	 
	
	if(dateList != null && !dateList.isEmpty()){
		return dateList;
	}else{
		return  null;
	}
	
}
	
	public int updateDateRangeDetails(Timestamp startDate,Timestamp endDate,Users users,int datePK) throws Exception{
		
		int updateVal = 0;
		String updatedBy = "";
		
		if (users!=null){
			updatedBy = users.getUsername();
		}
		
		try {
			updateVal = writeJdbcTemplate.update(SettingsQueries.UPDATE_DATE_RANGES, new Object[] 
	        { startDate, endDate, updatedBy, datePK
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		
		return updateVal;
	}

	
	@SuppressWarnings("unchecked")
	public String getActivityDetails(String activityName) throws Exception{
		List<SettingsBean> activityList = null;
		
		
		try {

			activityList =  getJdbcTemplate().query(
			SettingsQueries.GET_ACTIVITY_DETAILS, new Object[]{activityName},new RowMapper<SettingsBean>(){   
	        	public SettingsBean mapRow(ResultSet rs, int rowNum){   
	        		SettingsBean settingsBean = new SettingsBean();
	        		try {
	            			settingsBean.setActivityDescription(rs.getString("OCP_KEY_VALUE"));
					} catch (SQLException e) {
						logger.fatal(e, e);
					}
	        		return settingsBean;
	             }   
	        });   

		} catch (Exception e) {
			throw new GenericException(e);
		}
		
		
	if(activityList != null && !activityList.isEmpty()){
		return activityList.get(0).getActivityDescription();
	}else{
		return  null;
	}
	
}

}
