package com.nseit.generic.dao.impl;

/**
 * @author Pankaj
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.nseit.generic.dao.AgencyDao;
import com.nseit.generic.dao.BaseDAO;
import com.nseit.generic.models.AdditionalDetailsBean;
import com.nseit.generic.models.AddressBean;
import com.nseit.generic.models.AgencyBean;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.CandidateMgmtBean;
import com.nseit.generic.models.CommonBean;
import com.nseit.generic.models.EducationDetailsBean;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.PersonalDetailsBean;
import com.nseit.generic.models.RegistrationBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.models.WorkExperienceBean;
import com.nseit.generic.queries.AgencyQueries;
import com.nseit.generic.queries.CommonQueries;
import com.nseit.generic.queries.RegistrationQueries;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.DESEncrypter;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.ValidatorUtil;
import com.nseit.payment.queries.PaymentOfflineQueries;

public class AgencyDaoImpl extends BaseDAO implements AgencyDao{
	
	private JdbcTemplate writeJdbcTemplate;
	
	public void setWriteJdbcTemplate(JdbcTemplate writeJdbcTemplate) {
		this.writeJdbcTemplate = writeJdbcTemplate;
	}
	
	/**
	 * @author Pankaj Sh
	 * Generate login credential for candidate login
	 * return Map 
	 * @throws Exception 
	 */
	
	@SuppressWarnings("unchecked")
	public Integer generateLoginCregentials(AgencyBean agencyBean, Users users) throws Exception{
		
		int insertedvalues = 0;
		int nextValue;
		String ipin = "";
		String password = "";
		String discipline = "";
		int requestID;
		try{
			requestID = getJdbcTemplate().queryForObject(AgencyQueries.SELECT_REQUEST_ID, Integer.class) + 1;
			discipline = (String) getJdbcTemplate().query(AgencyQueries.SELECT_TEST_NAME, new Object[]{agencyBean.getDisciplineType()}, new ResultSetExtractor(){

				@Override
				public Object extractData(ResultSet rs) throws SQLException,
						DataAccessException {
					String test = "";
					if(rs.next())
						test = rs.getString("OTM_TEST_NAME");
					return test;
				}
				
			});
			
			for(int i=0;i<agencyBean.getCandidateCount();i++){
				
				nextValue = getJdbcTemplate().queryForObject(AgencyQueries.SELECT_NEXT_VALUE, Integer.class);
				
				ipin = discipline+ i;
				password = DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).encrypt("abcd1234");
				
				insertedvalues =  insertedvalues + writeJdbcTemplate.update(AgencyQueries.INSERT_LOGIN_DETAILS, new Object[]{nextValue, 
						discipline, nextValue, password, ipin, requestID, agencyBean.getDisciplineType(), users.getUsername()});
			}
			
			agencyBean.setRequestID(requestID);
			
		}
		catch (Exception e) {
			
			throw new GenericException(e);
		}
		
		return insertedvalues;
	}
	
	@SuppressWarnings("unchecked")
	public List<List<String>> getLoginCredentials()throws Exception{
		
		List<List<String>> detailList = new ArrayList<List<String>>();
		try{
			detailList = getJdbcTemplate().query(AgencyQueries.SELECT_LOGIN_CREDENTIAL_DISPLAY, 
					new RowMapper<List<String>>(){

						@Override
						public List<String> mapRow(ResultSet rs, int arg1)
								throws SQLException {
							List<String> list = new ArrayList<String>();
							try{
							
								list.add(rs.getString("OUM_REQUEST_ID"));
								list.add(rs.getString("count1"));
								list.add(rs.getString("date1"));
								list.add(rs.getString("date2"));
							
							return list;
							}
							catch (Exception e) {
							
								throw new SQLException(e);
							}
						}
			});
		}
		catch (Exception e) {
			
			throw new GenericException(e);
		}
		
		return detailList;
	}
	
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getTestMasterDetails()throws Exception{
		
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		try{
			map = (Map<Integer, String>) getJdbcTemplate().query(AgencyQueries.SELECT_TEST_MASTER, new ResultSetExtractor(){

				@Override
				public Object extractData(ResultSet rs) throws SQLException,
						DataAccessException {
					Map<Integer, String> map = new LinkedHashMap<Integer, String>();
					while(rs.next()){
						map.put(rs.getInt("OTM_TEST_PK"), rs.getString("OTM_TEST_NAME"));
					}
					return map;
				}

				
				
			}	
			);
			
		}
		catch (Exception e) {
			
			throw new GenericException(e);
		}
		
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public List<List<String>> exportToExcel()throws Exception{
		
		List<List<String>> detailList = new ArrayList<List<String>>();
		try{
			detailList = getJdbcTemplate().query(AgencyQueries.SELECT_LOGIN_CREDENTIALS,  
					new RowMapper<List<String>>(){

						@Override
						public List<String> mapRow(ResultSet rs, int arg1)
								throws SQLException {
							List<String> list = new ArrayList<String>();
							try{
							
								list.add(rs.getString("OUM_USER_ID"));
								list.add(DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).decrypt(rs.getString("OUM_PASSWORD")));
								list.add(rs.getString("OUM_IPIN"));
								list.add(rs.getString("OUM_REQUEST_ID"));
								list.add(rs.getString("date1"));
								list.add(rs.getString("date2"));
								list.add(rs.getString("OTM_TEST_NAME"));
							
							return list;
							}
							catch (Exception e) {
								
								throw new SQLException();
							}
						}
			});
		}
		
		catch (Exception e) {
			
			throw new GenericException(e);
		}
		
		return detailList;
	}
	
	public long getCandidateLoginCount()throws Exception{
		long count = 0;
		
		try{
			count = getJdbcTemplate().queryForObject(AgencyQueries.SELECT_LOGIN_COUNT, Long.class);
		}
		catch (Exception e) {
			
			throw new GenericException(e);
		}
		
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<List<String>> exportAllDataToExcel()throws Exception{
		
		List<List<String>> detailList = new ArrayList<List<String>>();
		try{
			detailList = getJdbcTemplate().query(AgencyQueries.SELECT_ALL_DATA_TO_EXPORT,  
					new RowMapper<List<String>>(){

						@Override
						public List<String> mapRow(ResultSet rs, int arg1)
								throws SQLException {
							List<String> list = new ArrayList<String>();
							try{
							
								list.add(rs.getString("OUM_USER_ID"));
								list.add(DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).decrypt(rs.getString("OUM_PASSWORD")));
								list.add(rs.getString("OUM_IPIN"));
								list.add(rs.getString("OUM_REQUEST_ID"));
								list.add(rs.getString("date1"));
								list.add(rs.getString("date2"));
								list.add(rs.getString("OTM_TEST_NAME"));
							
							return list;
							}
							catch (Exception e) {
								
								throw new SQLException();
							}
						}
			});
		}
		catch (Exception e) {
			
			throw new GenericException(e);
		}
		
		return detailList;
	}
	
	@SuppressWarnings("unchecked")
	public List<List<String>> exportDataForRequestIDToExcel(AgencyBean agencyBean)throws Exception{
		
		List<List<String>> detailList = new ArrayList<List<String>>();
		try{
			detailList = getJdbcTemplate().query(AgencyQueries.SELECT_DATA_FOR_REQUESTID_TO_EXPORT, new Object[]{agencyBean.getRequestID()}, 
					new RowMapper<List<String>>(){

						@Override
						public List<String> mapRow(ResultSet rs, int arg1)
								throws SQLException {
							List<String> list = new ArrayList<String>();
							try{
							
								list.add(rs.getString("OUM_USER_ID"));
								list.add(DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).decrypt(rs.getString("OUM_PASSWORD")));
								list.add(rs.getString("OUM_IPIN"));
								list.add(rs.getString("OUM_REQUEST_ID"));
								list.add(rs.getString("date1"));
								list.add(rs.getString("date2"));
								list.add(rs.getString("OTM_TEST_NAME"));
								
							return list;
							}
							catch (Exception e) {
								
								throw new SQLException();
							}
						}
			});
		}
		catch (Exception e) {
		
			throw new GenericException(e);
		}
		
		return detailList;
	}
	
	public Map<String,Integer> getGcetAdminHomeData() throws Exception
	{
		
		int data = 0;
		Map<String,Integer> gcetAdminDataMap = new HashMap<String, Integer>();
		try{

			//START :: Application Form Management 
		    
		    //Total Candidates
		    data = getJdbcTemplate().queryForObject(AgencyQueries.GET_TOTAL_NO_OF_CANDIDATES, Integer.class);
            gcetAdminDataMap.put("TOTAL_NO_OF_CANDIDATES", data);
            
            //For Not Initiated
            data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_NOT_INITIATED_CANDIDATE, Integer.class);
            gcetAdminDataMap.put("NO_OF_NOT_INITIATED_CANDIDATE", data);
            
            //For Initiated
            StringBuilder queryForInitiatedCandidate = new StringBuilder();
            queryForInitiatedCandidate.append(AgencyQueries.GET_NO_OF_INITIATED_CANDIDATE);
            //queryForInitiatedCandidate.append("AND osm.osm_status_pk in(");
            //queryForInitiatedCandidate.append(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_INTITIATED)).append(",");
            //queryForInitiatedCandidate.append(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_CONFIRMATION)).append(")");
			data = getJdbcTemplate().queryForObject(queryForInitiatedCandidate.toString(), new Object[]{}, Integer.class);
			gcetAdminDataMap.put("NO_OF_INITIATED_CANDIDATE", data);
			
			//For Submitted
			data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_APPLICATION_SUBMITTED,new Object[]{}, Integer.class);
			gcetAdminDataMap.put("NO_OF_CANDIDATE_APPLICATION_SUBMITED", data);
			
			//For Approved
			data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_APPROVED,new Object[]{"1"}, Integer.class);
			gcetAdminDataMap.put("NO_OF_APPROVED_CANDIDATE", data);

			//For Rejected
			data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_APPROVED,new Object[]{"0"}, Integer.class);
			gcetAdminDataMap.put("NO_OF_REJECTED_CANDIDATE", data);
			
			//For Pending
			data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_APPLICATION_PENDING,new Object[]{ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED)}, Integer.class);
            gcetAdminDataMap.put("NO_OF_PENDING_CANDIDATE", data);
			//END :: Application Form Management
            
            //For application Submitted Shreyas
            data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_APPLICATION_SUBMITTED,new Object[]{}, Integer.class);
            gcetAdminDataMap.put("NO_OF_APPLICATION_SUBMITTED", data);
            //End application Submitted Shreyas
            
            //For ADMIT_CARD DOWNLOAD COUNT Amruta
           // data = getJdbcTemplate().queryForObject(AgencyQueries.ADMIT_CARD_DOWNLOAD_COUNT,new Object[]{}, Integer.class);
           // gcetAdminDataMap.put("ADMIT_CARD_DOWNLOAD_COUNT", data);
            //End ADMIT_CARD DOWNLOAD COUNT Amruta
            
            //For Prac test attempt count Amruta
           // data = getJdbcTemplate().queryForObject(AgencyQueries.PRAC_TEST_DOWNLOAD_COUNT,new Object[]{}, Integer.class);
           // gcetAdminDataMap.put("PRAC_TEST_DOWNLOAD_COUNT", data);
            //End Prac test attempt count Amruta
            
            //START :: Payment Management
            
            data = getJdbcTemplate().queryForObject(AgencyQueries.GET_PAYMENT_SUBMITTED_COUNT, Integer.class);
            gcetAdminDataMap.put("NO_OF_PAYMENT_SUBMITTED", data);
            
            /*data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_PAYMENT,new Object[]{"A", "3","A", "3"}, Integer.class);
			gcetAdminDataMap.put("NO_OF_CANDIDATE_PAYMENT_APPROVED_DD", data);
			
			data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_PAYMENT,new Object[]{"A", "4","A", "4"}, Integer.class);
            gcetAdminDataMap.put("NO_OF_CANDIDATE_PAYMENT_APPROVED_CH", data);
            
            data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_PAYMENT,new Object[]{"A", "1","A", "1"}, Integer.class);*/
            data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_PAYMENT, Integer.class);
            gcetAdminDataMap.put("NO_OF_CANDIDATE_PAYMENT_APPROVED_NB", data);
            
           /* data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_PAYMENT,new Object[]{"A", "2","A", "2"}, Integer.class);
            gcetAdminDataMap.put("NO_OF_CANDIDATE_PAYMENT_APPROVED_CR", data);
            
            data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_PAYMENT,new Object[]{"A", "5","A", "5"}, Integer.class);
            gcetAdminDataMap.put("NO_OF_CANDIDATE_PAYMENT_APPROVED_CASH", data);
            
            data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_PAYMENT,new Object[]{"R", "3","R", "3"}, Integer.class);
			gcetAdminDataMap.put("NO_OF_CANDIDATE_PAYMENT_REJECTED_DD", data);
			
			data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_PAYMENT,new Object[]{"R", "4","R", "4"}, Integer.class);
            gcetAdminDataMap.put("NO_OF_CANDIDATE_PAYMENT_REJECTED_CH", data);
            
            data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_PAYMENT,new Object[]{"R", "5","R", "5"}, Integer.class);
            gcetAdminDataMap.put("NO_OF_CANDIDATE_PAYMENT_REJECTED_CASH", data);
            
            data = getJdbcTemplate().queryForObject(AgencyQueries.GET_CANDIDATE_COUNT_FOR_PAYMENT_FAILED,new Object[]{ConfigurationConstants.getInstance().getPaymentKeyByDesc(GenericConstants.NET_BANKING),GenericConstants.PAYMENT_REJECT_STATUS,ConfigurationConstants.getInstance().getPaymentKeyByDesc(GenericConstants.NET_BANKING),GenericConstants.PAYMENT_REJECT_STATUS}, Integer.class);*/
            data = getJdbcTemplate().queryForObject(AgencyQueries.GET_CANDIDATE_COUNT_FOR_PAYMENT_FAILED, Integer.class);//Start : Added by SANTOSHKUMAR SINGH(Kohinoor) on 07th April 2018
            gcetAdminDataMap.put("NO_OF_CANDIDATE_PAYMENT_REJECTED_NB", data);
            
            /*data = getJdbcTemplate().queryForObject(AgencyQueries.GET_CANDIDATE_COUNT_FOR_PAYMENT_FAILED,new Object[]{ConfigurationConstants.getInstance().getPaymentKeyByDesc(GenericConstants.CREDIT_CARD),GenericConstants.PAYMENT_REJECT_STATUS,ConfigurationConstants.getInstance().getPaymentKeyByDesc(GenericConstants.CREDIT_CARD),GenericConstants.PAYMENT_REJECT_STATUS}, Integer.class);
            gcetAdminDataMap.put("NO_OF_CANDIDATE_PAYMENT_REJECTED_CR", data);*/
            
            
            
            
            
			data = getJdbcTemplate().queryForObject(AgencyQueries.GET_PAYMENT_APPROVAL_PENDING_COUNT, new Object[]{"3"}, Integer.class);
            gcetAdminDataMap.put("NO_OF_CANDIDATE_PENDING_PAYMENT_APPROVAL_DD", data);
            

            data = getJdbcTemplate().queryForObject(AgencyQueries.GET_PAYMENT_APPROVAL_PENDING_COUNT, new Object[]{"4"}, Integer.class);
            gcetAdminDataMap.put("NO_OF_CANDIDATE_PENDING_PAYMENT_APPROVAL_CH", data);
            
            data = getJdbcTemplate().queryForObject(AgencyQueries.GET_PAYMENT_APPROVAL_PENDING_COUNT, new Object[]{"5"}, Integer.class);
            gcetAdminDataMap.put("NO_OF_CANDIDATE_PENDING_PAYMENT_APPROVAL_CASH", data);
            
            data = getJdbcTemplate().queryForObject(AgencyQueries.GET_PAYMENT_APPROVAL_PENDING_COUNT, new Object[]{"1"}, Integer.class);
            gcetAdminDataMap.put("NO_OF_CANDIDATE_PENDING_PAYMENT_APPROVAL_NB", data);
            
          /*  data = getJdbcTemplate().queryForObject(AgencyQueries.GET_PAYMENT_APPROVAL_PENDING_COUNT, new Object[]{"2"}, Integer.class);
            gcetAdminDataMap.put("NO_OF_CANDIDATE_PENDING_PAYMENT_APPROVAL_CR", data);*/
			
			//END :: Payment Management
			
            //START :: Schedule Management
			
            
            
            
			
			//*** schedule management - scenario 1 (application form - payment - otbs) *******

			//not scheduled ----- Total no. of candidates whose application form approved but payment details not submitted 
            //if(ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.PAYMENT).equals("A")){
                StringBuilder builder = new StringBuilder();
                builder.append(AgencyQueries.GET_COUNT_OF_NON_SCHEDULED_CANDIDATE_PAYMENT_ON);
                if (ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.PAYMENT_APPROVE_REJECT).equals("A")){
                	builder.append(" or OPD_VALIDATED_STATUS ='R' ");
                }
                builder.append(" ) ) and ocd.ocd_user_fk not in( select OED_GCPD_USER_FK from oes_entrollment_details) v and ocd.ocd_validated_status = 1") ;
                
              //  data = getJdbcTemplate().queryForObject(builder.toString(), Integer.class);
                gcetAdminDataMap.put("NO_OF_CANDIDATE_NON_SCHEDULED", 0);
                
                
                StringBuilder builderPendingForScheduling = new StringBuilder();
                builderPendingForScheduling.append(AgencyQueries.GET_COUNT_OF_PENDING_FOR_SCHEDULED_CANDIDATE_PAYMENT_ON);
                
                if (ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.PAYMENT_APPROVE_REJECT).equals("A")){
                	builderPendingForScheduling.append(" and opd_validated_status = 'A' ");
                }
                builderPendingForScheduling.append(" and opd_user_fk not in( select oed_gcpd_user_fk from oes_entrollment_details) v) ");
                //For Pending For Schedule Candidates
                //Total no. of Candidates whose application form approved and submitted payment details also approved but Candidate has not done OTBS/scheduling
              //  data = getJdbcTemplate().queryForObject(builderPendingForScheduling.toString(), Integer.class);
                gcetAdminDataMap.put("NO_OF_CANDIDATE_PENDING_FOR_SCHEDULED", 0);
			//}
            //else{
                //For not scheduled
                ///data = getJdbcTemplate().queryForObject(AgencyQueries.GET_COUNT_OF_NON_SCHEDULED_CANDIDATE_PAYMENT_OFF, Integer.class);
                //gcetAdminDataMap.put("NO_OF_CANDIDATE_NON_SCHEDULED", data);
                
                //For Pending For Schedule Candidates
                //data = getJdbcTemplate().queryForObject(AgencyQueries.GET_COUNT_OF_PENDING_FOR_SCHEDULED_CANDIDATE_PAYMENT_OFF, Integer.class);
                //gcetAdminDataMap.put("NO_OF_CANDIDATE_PENDING_FOR_SCHEDULED", data);
           // }
            
            // For Schedule Candidates
            data = getJdbcTemplate().queryForObject(AgencyQueries.GET_COUNT_OF_SCHEDULED_CANDIDATE, Integer.class);
            gcetAdminDataMap.put("NO_OF_CANDIDATE_SCHEDULED", data);
            
			
    			if(ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.GENERATE_ADMIT_CARD).equals("A"))
                {
                    // Admit Card for all attempts 
                    data = getJdbcTemplate().queryForObject(AgencyQueries.GET_CANDIDATE_COUNT_OF_ADMIT_CARD, Integer.class);
                    gcetAdminDataMap.put("NO_OF_CANDIDATE_FOR_ADMIT_CARD", data);
                }
			    
            
			    
                
                
                
			//END :: Schedule Management
			
			// START COMMENTED BY VIJAYA
			/*data = getJdbcTemplate().queryForObject(AgencyQueries.GET_DATA_FOR_ATTEMPT ,new Object[]{new Integer(1)}, Integer.class);
            gcetAdminDataMap.put("NO_OF_ATTEMPT_1_CANDIDATE", data);
            
            data = getJdbcTemplate().queryForObject(AgencyQueries.GET_DATA_FOR_ATTEMPT,new Object[]{new Integer(2)}, Integer.class);
            gcetAdminDataMap.put("NO_OF_ATTEMPT_2_CANDIDATE", data);
            
			data = getJdbcTemplate().queryForObject(AgencyQueries.GET_CANDIDATE_COUNT_OF_ADMIT_CARD,new Object[]{new Integer(1),1}, Integer.class);
			gcetAdminDataMap.put("NO_OF_CANDIDATE_FOR_ADMIT_CARD_ATTEMPT_1", data);
			
			data = getJdbcTemplate().queryForObject(AgencyQueries.GET_CANDIDATE_COUNT_OF_ADMIT_CARD,new Object[]{new Integer(2),1}, Integer.class);
			gcetAdminDataMap.put("NO_OF_CANDIDATE_FOR_ADMIT_CARD_ATTEMPT_2", data);*/
			// END COMMENTED BY VIJAYA
			
			
			
			/*data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_FOR_REGISTERED,new Object[]{ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED)}, Integer.class);
			gcetAdminDataMap.put("NO_OF_REGISTERED_CANDIDATE", data);
			
			data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_BASED_ON_STAGE ,new Object[]{ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED)}, Integer.class);
			gcetAdminDataMap.put("NO_OF_CONFIRMED_CANDIDATE", data);
			
			data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_FOR_APPROVED ,new Object[]{ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_APPROVED)}, Integer.class);
			gcetAdminDataMap.put("NO_OF_APPROVED_CANDIDATE", data);
			
			data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_FOR_APPROVED ,new Object[]{ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_REJECTED)}, Integer.class);
			gcetAdminDataMap.put("NO_OF_REJECTED_CANDIDATE", data);
			
			data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_BASED_ON_STAGE ,new Object[]{ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_REJECTED)}, Integer.class);
			gcetAdminDataMap.put("NO_OF_ATTEMPT_1_CANDIDATE", data);
			
			data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_BASED_ON_STAGE ,new Object[]{ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_REJECTED)}, Integer.class);
			gcetAdminDataMap.put("NO_OF_ATTEMPT_2_CANDIDATE", data);*/

		}catch (Exception e) {
			throw new GenericException(e);
		}
		return gcetAdminDataMap;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<String> getTestSlots() throws Exception {
		List<String> testDateList = null;
		
		try {
			testDateList = getJdbcTemplate().query(AgencyQueries.GET_TEST_SLOTS,
													new Object[]{
																	//candidateMgmtBean.getTestDateForSlot()
																},
													new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet rs, int rowCount)throws SQLException{
					CandidateMgmtBean candidateMgmtBean = new CandidateMgmtBean();
					String slot = new String();
					
					candidateMgmtBean.setCandidateTestStartTime(rs.getString("start_date"));
					candidateMgmtBean.setCandidateTestEndTime(rs.getString("end_date"));
					
					if (rs.getString("start_date")!=null && !rs.getString("start_date").equals("") && rs.getString("end_date")!=null &&!rs.getString("end_date").equals("")){ 
						slot =slot + rs.getString("start_date")+"-"+rs.getString("end_date");
					}
					
					return slot;
				}
			});
		} catch (DataAccessException e) {
			throw new GenericException(e);
		}
		
		if (testDateList!=null && !testDateList.isEmpty()){
			return testDateList;
		}else {
			return null;
		}
	}
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<AgencyBean> getDiscipline() throws Exception {
		
		List<AgencyBean> testDateList = null;
		
		try {
			testDateList = getJdbcTemplate().query(AgencyQueries.GET_DISCIPLINE,
													new Object[]{
																	//candidateMgmtBean.getTestDateForSlot()
																},
													new RowMapper<AgencyBean>() {

				@Override
				public AgencyBean mapRow(ResultSet rs, int rowCount)throws SQLException{
					AgencyBean agencyBean= new AgencyBean();
					
					agencyBean.setDisciplineName(rs.getString("OTM_TEST_NAME"));
					
					
					return agencyBean;
				}
			});
		} catch (DataAccessException e) {
			throw new GenericException(e);
		}
		
		if (testDateList!=null && !testDateList.isEmpty()){
			return testDateList;
		}else {
			return null;
		}
	}
	
	public int insertEmailSMSDetails(AgencyBean agencyBean,String emailSmsFlag,Users users,String mailAddress,String mobileNo,String userId,AgencyBean agencyBean2) throws Exception
	{
		
		String toAddressStr = mailAddress;
		String ccAddressStr = "";
		 ccAddressStr = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.CC_ADDRESS);
		 String mailContent = "";
		 mailContent = "Dear "+userId+" ,"+agencyBean.getMailContent();
		int count =0;
		try{
		count= writeJdbcTemplate.update(CommonQueries.INSERT_EMAIL_SMS_DETAIL, new Object[] {agencyBean.getSubject(), 
				mailContent, toAddressStr, ccAddressStr, emailSmsFlag, "N", "A", users.getUsername(),agencyBean2.getUserPk(),null});
		}catch (Exception e) {
		throw new GenericException(e);
	}
		
		return count;
		
	}
	
	public int insertEmailSMSDetailsForSms(AgencyBean agencyBean,String emailSmsFlag,Users users,String mobileNo,String userId,AgencyBean agencyBean2) throws Exception
	{
		
		String toAddressStr = mobileNo;
		String ccAddressStr = "";
		int count =0;
		 String smsContent = "";
		 smsContent = "Dear "+userId+", "+agencyBean.getSmsContent();
		try{
			count = writeJdbcTemplate.update(CommonQueries.INSERT_EMAIL_SMS_DETAIL, new Object[] {"", 
					smsContent, toAddressStr, ccAddressStr, emailSmsFlag, "N", "A", users.getUsername(),agencyBean2.getUserPk(),null});
		
		
		}catch (Exception e) {
			throw new GenericException(e);
		}
		
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<AgencyBean> getDataForGenericMails(AgencyBean agencyBean) throws Exception {
		
		List<AgencyBean> candidateDataForGenericMailsList = null;
		
		try {
			String sql = AgencyQueries.SELECT_CANDIDATE_DATA_FOR_GENERIC_MAILS;
			sql = String.format(sql, agencyBean.getDisciplineType(),agencyBean.getTestMasterValue());
			
			candidateDataForGenericMailsList = getJdbcTemplate().query(sql,
					new Object[]{
									agencyBean.getStartDate(),
									agencyBean.getEndDate()
								},
					new RowMapper<AgencyBean>() {

				@Override
				public AgencyBean mapRow(ResultSet rs, int rowCount)throws SQLException{
				AgencyBean agencyBean= new AgencyBean();
				agencyBean.setUserId(rs.getString("OUM_USER_ID"));
				agencyBean.setMobileNo(rs.getString("OUM_MOBILE_NO"));
				agencyBean.setEmail(rs.getString("OUM_EMAIL_ID"));
				agencyBean.setUserPk(rs.getString("OUM_USER_PK"));
				return agencyBean;
				}
				});
		} catch (DataAccessException e) {
			 throw new GenericException(e);
		}
		
		if (candidateDataForGenericMailsList!=null && !candidateDataForGenericMailsList.isEmpty()){
			return candidateDataForGenericMailsList;
		}else {
			return null;
		}
	}
	
	public int insertUserDetails(String userId,String password ,String ipin,Integer discipline, String emailId, String mobileNo, Users users)throws Exception
	{

		int updateCount = 0;
		try {
			if (userId == ""){
				userId = null;
			}
			if (password!=null && !password.equals("")){
				password = DESEncrypter.getInstance(
						CommonUtil.getPasswordEncryptionKey()).encrypt(password);
			}else{
				password = null;
			}
			if (ipin!=null && !ipin.equals("")){
				ipin = DESEncrypter.getInstance(
						CommonUtil.getPasswordEncryptionKey()).encrypt(ipin);
			}else{
				ipin = null;
			}

			updateCount = writeJdbcTemplate.update(
					AgencyQueries.INSERT_USER_IMPORT_DETAILS,
					new Object[] {userId, emailId, mobileNo, password, ipin, "N", 0, "C",
							discipline,1, users.getUsername()

					});
		
		   updateCount = getJdbcTemplate().queryForObject("SELECT max(OUM_USER_PK) from OES_USER_MASTER", Integer.class);
			
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("insertUserDetails : exception occrured for User => " + userId);
			throw new Exception(e);
		}

		return updateCount;
	}
	
	private Logger logger = LoggerHome.getLogger(getClass());
	
	
public List<CommonBean> getHelpCenterList() throws Exception {
		
		
		
		List<CommonBean> helpCenterList = null;
		try {
			helpCenterList = getJdbcTemplate().query(AgencyQueries.GET_HELP_CENTER_LIST,
					new Object[] {}, new RowMapper<CommonBean>() {
						public CommonBean mapRow(ResultSet rs, int rowCount)
								throws SQLException {
							CommonBean commonBean = new CommonBean();
							commonBean.setLabelId(rs.getString("GHCM_HELP_CENTER_PK"));
							commonBean.setLabelValue(rs.getString("GHCM_HELP_CENTER_NAME"));
							return commonBean;
						}
					});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		
		return helpCenterList;
	}
public int updatePasswordForHelpCenter(AgencyBean agencyBean) throws Exception
{
	
	int updatePassword = 0;
	try{
		updatePassword = writeJdbcTemplate.update(AgencyQueries.UPDATE_HELP_CENTER_USER_PASSWORD, new Object[]{DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).encrypt(agencyBean.getNewPassword()),agencyBean.getCmbHelpCenter()});
	}catch(Exception ex){
		throw new GenericException(ex);
	}
	
	return updatePassword;
	
}

public RegistrationBean generateUserID(RegistrationBean registrationBean) throws Exception {

	List<RegistrationBean> getRegistrationDetailsList = null;
	String nextValue;
	try {
			getRegistrationDetailsList = getJdbcTemplate().query(RegistrationQueries.SELECT_GENERATED_USER_ID,
					new Object[] {registrationBean.getMobileNo(),registrationBean.getEmailAddress()}, new RowMapper<RegistrationBean>(){
			public RegistrationBean mapRow(ResultSet rs, int rowCount) throws SQLException {
				RegistrationBean registrationBean = new RegistrationBean ();
				
				registrationBean.setUserName(rs.getString("OUM_USER_ID"));
				registrationBean.setUserId(rs.getString("OUM_USER_PK"));
				return registrationBean;
			}
			
		});

			} catch (Exception e) {

		throw new GenericException(e);
	}

			if(getRegistrationDetailsList!=null && !getRegistrationDetailsList.isEmpty()){
				return getRegistrationDetailsList.get(0);
			}else {
				return null; 
			}
}

public int getCandidateDetailsListCountForReport(AgencyBean agencyBean)throws Exception{
    int candidateDetailsListCount = 0;
    String statusId = agencyBean.getRegistrationStatus();
    try{
        StringBuffer strBld = new StringBuffer();
        
        String strFromDate1 = null;
	     String strToDate2 = null;
	     
	        if(!ValidatorUtil.isEmpty(agencyBean.getRegistrationFromDate()) && !ValidatorUtil.isEmpty(agencyBean.getRegistrationToDate())){
	     Date date1 = new Date(agencyBean.getRegistrationFromDate().toUpperCase());
	    	SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
	    	 strFromDate1= formatter1.format(date1);
	    	
	    	Date date2 = new Date(agencyBean.getRegistrationToDate().toUpperCase());
	    	SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
	    	 strToDate2= formatter2.format(date2);
	    	}
        
        
        
        strBld.append(AgencyQueries.GET_CANDIDATE_DETAILS_COUNT_FOR_REPORT_DISPLAY);
        
        if(!ValidatorUtil.isEmpty(agencyBean.getRegistrationFromDate()) && !ValidatorUtil.isEmpty(agencyBean.getRegistrationToDate())){
            strBld.append(" AND ocd.ocd_created_date::date BETWEEN '"+strFromDate1+"' AND '"+strToDate2+"'");
        }
        
        if(!ValidatorUtil.isEmpty(agencyBean.getRegistrationFromDate()) && ValidatorUtil.isEmpty(agencyBean.getRegistrationToDate())){
        	strBld.append("  AND to_char (ocd_created_date,'dd-MON-yyyy') >= '"+agencyBean.getRegistrationFromDate().toUpperCase()+"'");
		}
		if(ValidatorUtil.isEmpty(agencyBean.getRegistrationFromDate()) && !ValidatorUtil.isEmpty(agencyBean.getRegistrationToDate())){
			strBld.append("  AND to_char (ocd_created_date,'dd-MON-yyyy') <= '"+agencyBean.getRegistrationToDate().toUpperCase()+"'");
		}
        
        if(statusId.equals("0") || statusId.equals("-1") || statusId.equals("All")){
            strBld.append(" AND ocd.ocd_status_id_fk = CASE WHEN -1 = -1 THEN ocd_status_id_fk ELSE -1 END");
        }
        else if(agencyBean.getRegistrationStatus().equals(String.valueOf(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.NEW_REGISTERATION)))){
        	strBld.append(" AND (oum_user_pk not in (select ocd_user_fk from oes_candidate_details) and OUM_USER_TYPE = 'C') ");
        }
        else if(agencyBean.getRegistrationStatus().equals(String.valueOf(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_INTITIATED)))){
        	strBld.append(" AND ocd.ocd_status_id_fk <5 AND ocd.ocd_form_submited_date IS NULL "); 	
            //strBld.append(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_CONFIRMATION)).append(")");
        }else if(agencyBean.getRegistrationStatus().equals(String.valueOf(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED)))){
        	strBld.append(" AND ocd.ocd_status_id_fk >=5 AND ocd.ocd_form_submited_date IS NOT NULL ");
        }
        else{
        	strBld.append(" AND osm.osm_status_pk = CASE WHEN "+ agencyBean.getRegistrationStatus() +" = 0 THEN osm_status_pk ELSE " +agencyBean.getRegistrationStatus() +" END ");
        }
       /* strBld.append(" GROUP BY otm_test_name, oum_user_id, oum_candidate_name,oum_initial , ocd_gender_fk,oum_date_of_birth, OUM_DISABILITY,oum_scribe_requied, ocd_nativity,ocd_nativity_other,\r\n" + 
        		"				     ocd_community_cert,ocd_community,ocd_perm_city,oum_created_date,ocd_perm_state_fk,oum_email_id, oum_mobile_no,ACDH_USER_ID ,SCDH_USER_ID,cldh_user_id ,\r\n" + 
        		"				     oacd3.oacd_degree_subject_fk,oacd3.oacd_major_subject_fk,oacd4.oacd_degree_subject_fk,oacd4.oacd_major_subject_fk,oacd5.oacd_degree_subject_fk,oacd5.oacd_major_subject_fk,\r\n" + 
        		"					ocd_form_submited_date,  opd.opd_transaction_date,opd.opd_amount,opd.opd_validated_status,optm_payment_desc,OCPD_PREF_TEST_CENTRE_1_FK,OCPD_PREF_TEST_CENTRE_2_FK,OCPD_PREF_TEST_CENTRE_3_FK \r\n" + 
        		" ) a " );*/
        if(agencyBean.getProgramName().equals("All") || agencyBean.getProgramName().equals("0"))
        {
        	agencyBean.setProgramName("-1");	
        }
        if(agencyBean.getCategory().equals("All") || agencyBean.getCategory().equals("0"))
        {
        	agencyBean.setCategory("-1");	
        }
        //logger.info("strBld :: "+strBld.toString());
        candidateDetailsListCount = getJdbcTemplate().queryForObject(strBld.toString(),  new Object[]{
						            Integer.parseInt(agencyBean.getProgramName()),
						            Integer.parseInt(agencyBean.getProgramName()),
						            Integer.parseInt(agencyBean.getCategory()),
						            Integer.parseInt(agencyBean.getCategory())}, Integer.class); 
    }
    catch (Exception ex) {
    	candidateDetailsListCount = 0;
        throw new GenericException(ex);
    }
	return candidateDetailsListCount;
}

@SuppressWarnings("unchecked")
public List<CandidateBean> getCandidateDetailsListForReport(AgencyBean agencyBean,Pagination pagination)throws Exception
{
    List<CandidateBean> candidateDetailsList = null;
    
    String statusId = agencyBean.getRegistrationStatus();
    String strFromDate1 = null;
    String strToDate2 = null;
    try 
    {
    	if(!ValidatorUtil.isEmpty(agencyBean.getRegistrationFromDate()) && !ValidatorUtil.isEmpty(agencyBean.getRegistrationToDate())){
    	Date date1 = new Date(agencyBean.getRegistrationFromDate().toUpperCase());
    	SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
    	strFromDate1= formatter1.format(date1);
    	
    	Date date2 = new Date(agencyBean.getRegistrationToDate().toUpperCase());
    	SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
    	strToDate2= formatter2.format(date2);
    	}
    	
        StringBuffer strBld = new StringBuffer();
        
        if(agencyBean.getRegistrationStatus().equals(String.valueOf(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.NEW_REGISTERATION)))){
         	String sql = AgencyQueries.GET_CANDIDATE_DETAILS_FOR_REPORT_DISPLAY;
         	sql = sql.replace("inner join oes_candidate_details ocd", " LEFT OUTER JOIN oes_candidate_details ocd ");
         	strBld.append(sql);
         }else {
        
             strBld.append(AgencyQueries.GET_CANDIDATE_DETAILS_FOR_REPORT_DISPLAY);
         }
        
        if(!ValidatorUtil.isEmpty(agencyBean.getRegistrationFromDate()) && !ValidatorUtil.isEmpty(agencyBean.getRegistrationToDate())){
            strBld.append(" AND ocd.ocd_created_date::date BETWEEN '"+strFromDate1+"' AND '"+strToDate2+"'");
        }
        
        if(!ValidatorUtil.isEmpty(agencyBean.getRegistrationFromDate()) && ValidatorUtil.isEmpty(agencyBean.getRegistrationToDate())){
        	strBld.append("  AND to_char (ocd_created_date,'dd-MON-yyyy') >= '"+agencyBean.getRegistrationFromDate().toUpperCase()+"'");
		}
		if(ValidatorUtil.isEmpty(agencyBean.getRegistrationFromDate()) && !ValidatorUtil.isEmpty(agencyBean.getRegistrationToDate())){
			strBld.append("  AND to_char (ocd_created_date,'dd-MON-yyyy') <= '"+agencyBean.getRegistrationToDate().toUpperCase()+"'");
		}
        
        /*if(statusId.equals("0")){
           // strBld.append(" AND osm.osm_status_pk = CASE WHEN 0 = 0 THEN osm_status_pk ELSE 0 END");
            strBld.append(" and (osm_status_pk in(-1)  or  -1 in  (-1)) ");
        }else if(agencyBean.getRegistrationStatus().equals("50")){
            strBld.append(" AND ocd.ocd_form_submited_date IS NOT NULL ");
            strBld.append(" AND ocd.ocd_validated_status IS NULL ");
        }else*/ 
		if(agencyBean.getRegistrationStatus().equals(String.valueOf(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_INTITIATED)))){
        	strBld.append(" and ocd.ocd_status_id_fk <5 AND ocd.ocd_form_submited_date IS NULL "); 	
        	/*strBld.append("AND osm.osm_status_pk in(");
            strBld.append(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_INTITIATED)).append(",");
            strBld.append(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_CONFIRMATION)).append(")");*/
        }else if(agencyBean.getRegistrationStatus().equals(String.valueOf(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.NEW_REGISTERATION)))){
           // strBld.append("AND (ocd.OCD_STATUS_ID_FK < 5)");
        	strBld.append(" AND (oum_user_pk not in (select ocd_user_fk from oes_candidate_details) and OUM_USER_TYPE = 'C') ");
        }
		
		else if(agencyBean.getRegistrationStatus().equals(String.valueOf(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED)))){
        	/*strBld.append("AND osm.osm_status_pk >="+ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED));*/
        	strBld.append(" and ocd.ocd_status_id_fk >=5 AND ocd.ocd_form_submited_date IS NOT NULL ");
        }
        /*else{
        	strBld.append(" AND osm.osm_status_pk = CASE WHEN " + agencyBean.getRegistrationStatus() +" = 0 THEN osm_status_pk ELSE " +agencyBean.getRegistrationStatus() +" END ");
        }*/
		
		strBld.append("GROUP BY otm_test_name, oum_user_id, oum_candidate_name,oum_initial , ocd_gender_fk,oum_date_of_birth, OUM_DISABILITY,oum_scribe_requied, ocd_nativity,ocd_nativity_other,\r\n" + 
				"      ocd_community_cert,ocd_community,ocd_perm_city,oum_created_date,ocd_perm_state_fk,oum_email_id, oum_mobile_no,ACDH_USER_ID ,SCDH_USER_ID,cldh_user_id ,\r\n" + 
				"     oacd3.oacd_degree_subject_fk,oacd3.oacd_major_subject_fk,oacd4.oacd_degree_subject_fk,oacd4.oacd_major_subject_fk,oacd5.oacd_degree_subject_fk,oacd5.oacd_major_subject_fk, \r\n"+
				"	ocd_form_submited_date,  opd.opd_transaction_date,opd.opd_amount,opd.opd_validated_status,optm_payment_desc,OCPD_PREF_TEST_CENTRE_1_FK,OCPD_PREF_TEST_CENTRE_2_FK,OCPD_PREF_TEST_CENTRE_3_FK \r\n" + 
				"	");
        strBld.append(" ORDER BY oum_user_id)").append(" as aliasTEST");
        strBld.append(" WHERE num BETWEEN ").append(pagination.getStart()).append(" AND ").append(pagination.getEnd()) ;
        if(agencyBean.getProgramName().equals("0"))
        {
        	agencyBean.setProgramName("-1");
        }
        if(agencyBean.getCategory().equals("0"))
        {
        	agencyBean.setCategory("-1");
        }
        if(agencyBean.getRegistrationStatus().equals("0"))
        {
        	agencyBean.setRegistrationStatus("-1");
        }
        //logger.info("CandidateReport SQL :: "+strBld.toString());
        candidateDetailsList =  getJdbcTemplate().query(strBld.toString(),
                    new Object[]{
                            Integer.parseInt(agencyBean.getProgramName()),
                            Integer.parseInt(agencyBean.getProgramName()),
                            Integer.parseInt(agencyBean.getCategory()),
                            Integer.parseInt(agencyBean.getCategory())
                    }, new RowMapper<CandidateBean>(){   
                        public CandidateBean mapRow(ResultSet rs, int rowNum){   
                            
                            CandidateBean candidateBean = new CandidateBean();
                            PersonalDetailsBean personalBean = new PersonalDetailsBean();
                            AddressBean address = new AddressBean();
                            EducationDetailsBean educationDtlBean=new EducationDetailsBean();
                            AdditionalDetailsBean ad=new AdditionalDetailsBean();
                            String candName ="";
                            try 
                            {
                            	candidateBean.setSr_no(rs.getInt("num"));
                            	candidateBean.setPostName(rs.getString("Post Applied for"));
                            	candidateBean.setUserId(rs.getString("oum_user_id"));
                            	candName = rs.getString("oum_initial") + " "+rs.getString("name");
                            	candidateBean.setFirstName(candName);
                            	personalBean.setGender(rs.getString("ocd_gender_fk"));
                            	personalBean.setDateOfBirth(rs.getString("date_of_birth"));
                            	personalBean.setCategory(rs.getString("ocd_community"));
                            	personalBean.setEmail(rs.getString("oum_email_id"));
                                personalBean.setMobileNo(rs.getString("oum_mobile_no"));
                                candidateBean.setPersonalDetailsBean(personalBean);
                            	candidateBean.setNativity(rs.getString("ocd_nativity"));
                       		    candidateBean.setOtherNativity(rs.getString("ocd_nativity_other"));
                       	//	    candidateBean.setPhysicalDisability(rs.getString("OUM_DISABILITY"));
                            	candidateBean.setCityName(rs.getString("ocd_perm_city"));
                            	candidateBean.setStateValDesc(rs.getString("ocd_perm_state_fk"));
                            	candidateBean.setPreferredTestDate1(rs.getString("OCPD_PREF_TEST_CENTRE_1_FK"));	
                            	candidateBean.setPreferredTestDate2(rs.getString("OCPD_PREF_TEST_CENTRE_2_FK"));
                            	candidateBean.setPreferredTestDate3(rs.getString("OCPD_PREF_TEST_CENTRE_3_FK"));
                            	candidateBean.setAdmitCardExist(rs.getString("Admit Card Download"));
                            	candidateBean.setAdmitcardDownloadDate(rs.getString("LatestAdmitCard DownloadDate"));  
                            	candidateBean.setScoreCardExist(rs.getString("Score Card Downloaded"));
                            	candidateBean.setScoreCardDownloadDate(rs.getString("LatestscoreCard DownloadDate"));
                            	candidateBean.setCallLetterExist(rs.getString("Call Letter Downloaded"));
                            	candidateBean.setCallLetterDownloadDate(rs.getString("LatestCall Letter DownloadDate"));
                            	educationDtlBean.setUgDegreeName(rs.getString("ug_degree_name"));
                            	educationDtlBean.setUgDegreeSubject(rs.getString("ug_subject_name"));
                            	educationDtlBean.setPgDegreeName(rs.getString("pg_degree_name"));
                            	educationDtlBean.setPgDegreeSubject(rs.getString("pg_subject_name"));
                            	educationDtlBean.setEduDegreeName(rs.getString("edu_degree_name"));
                            	educationDtlBean.setEduSubjectName(rs.getString("edu_subject_name"));
                            	candidateBean.setEducationDetailsBean(educationDtlBean);
                            	candidateBean.setRegFormSubmitedDate(rs.getString("ocd_form_submited_date"));
                        		 if(rs.getString("opd_transaction_date")!=null && !rs.getString("opd_transaction_date").equals(""))
                                 {
                                 	String a[]=rs.getString("opd_transaction_date").split(" ");
                                    candidateBean.setPaymentTransactionDate(a[0]);
                                 }
                                 else
                                 {
                                 	candidateBean.setPaymentTransactionDate(rs.getString("opd_transaction_date"));
                                 }      
                            	 candidateBean.setRegStrtDate(rs.getString("basic_register_date"));       
                            	 candidateBean.setPaymentMode(rs.getString("optm_payment_desc"));
                            	 candidateBean.setPaymentStatus(rs.getString("Payment_status"));
                            	 candidateBean.setReceiptAmount(rs.getString("opd_amount"));
                            	  
                            	   
                            } 
                            catch (Exception ex) 
                            {
                                LoggerHome.getLogger(getClass()).fatal(ex);
                            }
                            return candidateBean;
                         }   
                    });   
            if(candidateDetailsList != null && !candidateDetailsList.isEmpty()){
                return candidateDetailsList;
            }else{
                return  null;
            }
    }
    catch (Exception ex) 
    {
        throw new GenericException(ex);
    }
}

public List<CandidateBean> getCandidateDetailsListForReport(AgencyBean agencyBean)throws Exception
{
    List<CandidateBean> candidateDetailsList = null;
    
    String statusId = agencyBean.getRegistrationStatus();
    String strFromDate1 = null;
    String strToDate2 = null;
    try 
    {
    	if(!ValidatorUtil.isEmpty(agencyBean.getRegistrationFromDate()) && !ValidatorUtil.isEmpty(agencyBean.getRegistrationToDate())){
        	Date date1 = new Date(agencyBean.getRegistrationFromDate().toUpperCase());
        	SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        	strFromDate1= formatter1.format(date1);
        	
        	Date date2 = new Date(agencyBean.getRegistrationToDate().toUpperCase());
        	SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        	strToDate2= formatter2.format(date2);
        	}
    	
        StringBuffer strBld = new StringBuffer();
        
        strBld.append(AgencyQueries.GET_CANDIDATE_DETAILS_FOR_REPORT_DISPLAY);
        
        if(!ValidatorUtil.isEmpty(agencyBean.getRegistrationFromDate()) && !ValidatorUtil.isEmpty(agencyBean.getRegistrationToDate())){
            strBld.append(" AND ocd.ocd_created_date::date BETWEEN '"+strFromDate1+"' AND '"+strToDate2+"'");
        }
        
        if(!ValidatorUtil.isEmpty(agencyBean.getRegistrationFromDate()) && ValidatorUtil.isEmpty(agencyBean.getRegistrationToDate())){
        	strBld.append("  AND to_char (ocd_created_date,'dd-MON-yyyy') >= '"+agencyBean.getRegistrationFromDate().toUpperCase()+"'");
		}
		if(ValidatorUtil.isEmpty(agencyBean.getRegistrationFromDate()) && !ValidatorUtil.isEmpty(agencyBean.getRegistrationToDate())){
			strBld.append("  AND to_char (ocd_created_date,'dd-MON-yyyy') <= '"+agencyBean.getRegistrationToDate().toUpperCase()+"'");
		}
        
        /*if(statusId.equals("0")){
           // strBld.append(" AND osm.osm_status_pk = CASE WHEN 0 = 0 THEN osm_status_pk ELSE 0 END");
            strBld.append(" and (osm_status_pk in(-1)  or  -1 in  (-1)) ");
        }else if(agencyBean.getRegistrationStatus().equals("50")){
            strBld.append(" AND ocd.ocd_form_submited_date IS NOT NULL ");
            strBld.append(" AND ocd.ocd_validated_status IS NULL ");
        }else*/ 
		if(agencyBean.getRegistrationStatus().equals(String.valueOf(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_INTITIATED)))){
        	strBld.append(" and ocd.ocd_status_id_fk=3 AND ocd.ocd_form_submited_date IS NULL "); 	
        	/*strBld.append("AND osm.osm_status_pk in(");
            strBld.append(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_INTITIATED)).append(",");
            strBld.append(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_CONFIRMATION)).append(")");*/
        }else if(agencyBean.getRegistrationStatus().equals(String.valueOf(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED)))){
        	/*strBld.append("AND osm.osm_status_pk >="+ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED));*/
        	strBld.append(" and ocd.ocd_status_id_fk >=5 AND ocd.ocd_form_submited_date IS NOT NULL ");
        }
        /*else{
        	strBld.append(" AND osm.osm_status_pk = CASE WHEN " + agencyBean.getRegistrationStatus() +" = 0 THEN osm_status_pk ELSE " +agencyBean.getRegistrationStatus() +" END ");
        }*/
		strBld.append("GROUP BY otm_test_name, oum_user_id, oum_candidate_name,oum_initial , ocd_gender_fk,ocd_date_of_birth, OUM_DISABILITY,oum_scribe_requied, ocd_nativity,ocd_nativity_other,\r\n" + 
				"      ocd_community_cert,ocd_community,ocd_perm_city,oum_created_date,ocd_perm_state_fk,oum_email_id, oum_mobile_no,ACDH_USER_ID ,SCDH_USER_ID,cldh_user_id ,\r\n" + 
				"     oacd3.oacd_degree_subject_fk,oacd3.oacd_major_subject_fk,oacd4.oacd_degree_subject_fk,oacd4.oacd_major_subject_fk,oacd5.oacd_degree_subject_fk,oacd5.oacd_major_subject_fk, \r\n"+
				"	ocd_form_submited_date,  opd.opd_transaction_date,opd.opd_amount,opd.opd_validated_status,optm_payment_desc,OCPD_PREF_TEST_CENTRE_1_FK,OCPD_PREF_TEST_CENTRE_2_FK,OCPD_PREF_TEST_CENTRE_3_FK \r\n" + 
				"	");
        strBld.append(" ORDER BY oum_user_id)").append(" as aliasTEST");
        if(agencyBean.getProgramName().equals("0"))
        {
        	agencyBean.setProgramName("-1");
        }
        if(agencyBean.getCategory().equals("0"))
        {
        	agencyBean.setCategory("-1");
        }
        if(agencyBean.getRegistrationStatus().equals("0"))
        {
        	agencyBean.setRegistrationStatus("-1");
        }
        //logger.info("CandidateReport SQL :: "+strBld.toString());
        candidateDetailsList =  getJdbcTemplate().query(strBld.toString(),
                    new Object[]{
                            Integer.parseInt(agencyBean.getProgramName()),
                            Integer.parseInt(agencyBean.getProgramName()),
                            Integer.parseInt(agencyBean.getCategory()),
                            Integer.parseInt(agencyBean.getCategory())
                    }, new RowMapper<CandidateBean>(){   
                        public CandidateBean mapRow(ResultSet rs, int rowNum){   
                            
                            CandidateBean candidateBean = new CandidateBean();
                            PersonalDetailsBean personalBean = new PersonalDetailsBean();
                            AddressBean address = new AddressBean();
                            EducationDetailsBean educationDtlBean=new EducationDetailsBean();
                            AdditionalDetailsBean ad=new AdditionalDetailsBean();
                            String candName ="";
                            try 
                            {
                            	candidateBean.setSr_no(rs.getInt("num"));
                            	candidateBean.setPostName(rs.getString("Post Applied for"));
                            	candidateBean.setUserId(rs.getString("oum_user_id"));
                            	candName = rs.getString("oum_initial") + " "+rs.getString("name");
                            	candidateBean.setFirstName(candName);
                            	personalBean.setGender(rs.getString("ocd_gender_fk"));
                            	personalBean.setDateOfBirth(rs.getString("date_of_birth"));
                            	personalBean.setCategory(rs.getString("ocd_community"));
                            	personalBean.setEmail(rs.getString("oum_email_id"));
                                personalBean.setMobileNo(rs.getString("oum_mobile_no"));
                                candidateBean.setPersonalDetailsBean(personalBean);
                            	candidateBean.setNativity(rs.getString("ocd_nativity"));
                       		    candidateBean.setOtherNativity(rs.getString("ocd_nativity_other"));
                    //   		    candidateBean.setPhysicalDisability(rs.getString("OUM_DISABILITY"));
                            	candidateBean.setCityName(rs.getString("ocd_perm_city"));
                            	candidateBean.setStateValDesc(rs.getString("ocd_perm_state_fk"));
                            	candidateBean.setPreferredTestDate1(rs.getString("OCPD_PREF_TEST_CENTRE_1_FK"));	
                            	candidateBean.setPreferredTestDate2(rs.getString("OCPD_PREF_TEST_CENTRE_2_FK"));
                            	candidateBean.setPreferredTestDate3(rs.getString("OCPD_PREF_TEST_CENTRE_3_FK"));
                            	candidateBean.setAdmitCardExist(rs.getString("Admit Card Download"));
                            	candidateBean.setAdmitcardDownloadDate(rs.getString("LatestAdmitCard DownloadDate"));  
                            	candidateBean.setScoreCardExist(rs.getString("Score Card Downloaded"));
                            	candidateBean.setScoreCardDownloadDate(rs.getString("LatestscoreCard DownloadDate"));
                            	candidateBean.setCallLetterExist(rs.getString("Call Letter Downloaded"));
                            	candidateBean.setCallLetterDownloadDate(rs.getString("LatestCall Letter DownloadDate"));
                            	educationDtlBean.setUgDegreeName(rs.getString("ug_degree_name"));
                            	educationDtlBean.setUgDegreeSubject(rs.getString("ug_subject_name"));
                            	educationDtlBean.setPgDegreeName(rs.getString("pg_degree_name"));
                            	educationDtlBean.setPgDegreeSubject(rs.getString("pg_subject_name"));
                            	educationDtlBean.setEduDegreeName(rs.getString("edu_degree_name"));
                            	educationDtlBean.setEduSubjectName(rs.getString("edu_subject_name"));
                            	candidateBean.setEducationDetailsBean(educationDtlBean);
                            	candidateBean.setRegFormSubmitedDate(rs.getString("ocd_form_submited_date"));
                        		 if(rs.getString("opd_transaction_date")!=null && !rs.getString("opd_transaction_date").equals(""))
                                 {
                                 	String a[]=rs.getString("opd_transaction_date").split(" ");
                                    candidateBean.setPaymentTransactionDate(a[0]);
                                 }
                                 else
                                 {
                                 	candidateBean.setPaymentTransactionDate(rs.getString("opd_transaction_date"));
                                 }      
                            	 candidateBean.setRegStrtDate(rs.getString("basic_register_date"));       
                            	 candidateBean.setPaymentMode(rs.getString("optm_payment_desc"));
                            	 candidateBean.setPaymentStatus(rs.getString("Payment_status"));
                            	 candidateBean.setReceiptAmount(rs.getString("opd_amount"));
                            	  
                            	   
                            } 
                            catch (Exception ex) 
                            {
                                LoggerHome.getLogger(getClass()).fatal(ex);
                            }
                            return candidateBean;
                         }   
                    });   
            if(candidateDetailsList != null && !candidateDetailsList.isEmpty()){
                return candidateDetailsList;
            }else{
                return  null;
            }
    }
    catch (Exception ex) 
    {
        throw new GenericException(ex);
    }
}


public int getCandidateDetailsListCountForReportForApproved(AgencyBean agencyBean, String validateValue)throws Exception {
    int totalNumberOfRecord = 0;
    try {
        StringBuffer strBld = new StringBuffer();
        strBld.append(AgencyQueries.GET_DETAILS_OF_CANDIDATE_APPROVED_COUNT);
        
        if(!ValidatorUtil.isEmpty(agencyBean.getRegistrationFromDate()) && !ValidatorUtil.isEmpty(agencyBean.getRegistrationToDate())){
            strBld.append(" AND to_char(ocd.ocd_created_date,'HH12:MI:SS') BETWEEN '"+agencyBean.getRegistrationFromDate()+"' AND '"+agencyBean.getRegistrationToDate() +"'");
        }
        //strBld.append(" )");
		totalNumberOfRecord = getJdbcTemplate().queryForObject(strBld.toString(), new Object[] {validateValue, Integer.parseInt(agencyBean.getProgramName()),
					            Integer.parseInt(agencyBean.getProgramName()),
					            Integer.parseInt(agencyBean.getCategory()),
					            Integer.parseInt(agencyBean.getCategory()),0,0}, Integer.class);
        
    }catch (Exception ex) 
    {
    	totalNumberOfRecord = 0;
        throw new GenericException(ex);
    }
	return totalNumberOfRecord;
}
    
@SuppressWarnings("unchecked")
public List<CandidateBean> getCandidateDetailsListForReportForApproved(AgencyBean agencyBean, String validateValue,Pagination pagination)throws Exception
{
    List<CandidateBean> candidateDetailsList = null;
    try 
    {
        StringBuffer strBld = new StringBuffer();
        //dasdasd
        strBld.append(AgencyQueries.GET_DETAILS_OF_CANDIDATE_APPROVED);
        
        if(!ValidatorUtil.isEmpty(agencyBean.getRegistrationFromDate()) && !ValidatorUtil.isEmpty(agencyBean.getRegistrationToDate())){
            strBld.append(" AND to_char(ocd.ocd_created_date,'HH12:MI:SS') BETWEEN '"+agencyBean.getRegistrationFromDate()+"' AND '"+agencyBean.getRegistrationToDate() +"'");
        }
        
        strBld.append(" ORDER BY oum_user_id )");
        strBld.append(" WHERE num BETWEEN ").append(pagination.getStart()).append(" AND ").append(pagination.getEnd());
        
        candidateDetailsList =  getJdbcTemplate().query(strBld.toString(),new Object[]{validateValue, Integer.parseInt(agencyBean.getProgramName()),
            Integer.parseInt(agencyBean.getProgramName()),
            Integer.parseInt(agencyBean.getCategory()),
            Integer.parseInt(agencyBean.getCategory())}, new RowMapper<CandidateBean>(){   
                        public CandidateBean mapRow(ResultSet rs, int rowNum){   
                            
                            CandidateBean candidateBean = new CandidateBean();
                            PersonalDetailsBean personalBean = new PersonalDetailsBean();
                            AddressBean address = new AddressBean();
                            EducationDetailsBean edu=new EducationDetailsBean();
                            AdditionalDetailsBean ad=new AdditionalDetailsBean();
                            try 
                            {
                            	candidateBean.setUserId(rs.getString("oum_user_id"));
                                candidateBean.setFirstName(rs.getString("name"));
                                personalBean.setDateOfBirth(rs.getString("date_of_birth"));
                                if(rs.getString("perm_state")!=null && !rs.getString("perm_state").equals("") && 
                                		rs.getString("perm_state").equals("Tamil Nadu"))
                                {
                                	candidateBean.setStateVal(rs.getString("perm_state"));
                                    candidateBean.setDistrictVal(rs.getString("perm_district"));
                                    candidateBean.setCityName(rs.getString("city"));
                                }
                                else
                                {
                                	candidateBean.setStateVal(rs.getString("OCD_PERM_STATE_OTS"));
                                    candidateBean.setDistrictVal(rs.getString("ocd_perm_district_other"));
                                    candidateBean.setCityName(rs.getString("ocd_perm_POLICE_other"));
                                }
                                if(rs.getString("comm_state")!=null && !rs.getString("comm_state").equals("") && 
                                		rs.getString("comm_state").equals("Tamil Nadu"))
                                {
                                	candidateBean.setAltStateVal(rs.getString("comm_state"));
                                	candidateBean.setAltDistrictVal(rs.getString("comm_district"));
                                	address.setAlternateCity(rs.getString("alt_city"));
                                	
                                }
                                else
                                {
                                	candidateBean.setAltStateVal(rs.getString("OCD_COMM_STATE_OTS"));
                                	candidateBean.setAltDistrictVal(rs.getString("OCD_COMM_DISTRICT_other"));
                                	address.setAlternateCity(rs.getString("OCD_COMM_POLICE_other"));
                                }
                                personalBean.setEmail(rs.getString("oum_email_id"));
                                personalBean.setMobileNo(rs.getString("oum_mobile_no"));
                                candidateBean.setCategoryVal(rs.getString("octm_category_code"));                                
                                //candidateBean.setSubcaste(rs.getString("Subcaste"));
                                candidateBean.setAddressBean(address);
                                edu.setDegreeSelected(rs.getString("oatm_acdm_code"));
                                edu.setPartTimeFullTimeSelected(rs.getString("medium"));
                                edu.setDegreeSubject(rs.getString("odm_degree_desc"));
                                edu.setMajorSubject(rs.getString("oacd_major_subject_fk"));
                                edu.setSslcTamil(rs.getString("tamil_ssc"));
                                candidateBean.setEducationDetailsBean(edu);
                                /*ad.setCrime(rs.getString("ocad_crime"));
                                ad.setBoardName(rs.getString("ocad_board_name"));
                                ad.setExServiceman(rs.getString("exserviceman"));
                                ad.setWidow(rs.getString("ocad_widow"));
                                ad.setSportsName(rs.getString("ocad_sports_quaota"));
                                ad.setWardname(rs.getString("ocad_wards_quaota"));
                                ad.setNccCertificate(rs.getString("ocad_ncc_certificate"));
                                ad.setNssCertificate(rs.getString("ocad_nss_certificate"));
                                ad.setAppFormSubmitedDate(rs.getString("app_date"));*/ //todo
                                
                                
                                /*candidateBean.setAdditionalDetailsBean(ad);*/
                                candidateBean.setDisciplineTypeDesc(rs.getString("otm_test_name"));
                                candidateBean.setTestCenter1(rs.getString("otcm_test_centre_name"));
                                candidateBean.setRegFormSubmitedDate(rs.getString("ocd_form_submited_date"));
                                candidateBean.setPaymentMode(rs.getString("optm_payment_desc"));
                                candidateBean.setPaymentStatus(rs.getString("opd_stage"));
                            } 
                            catch (Exception ex) 
                            {
                                LoggerHome.getLogger(getClass()).fatal(ex);
                            }
                            return candidateBean;
                         }   
                    });   
            
        
            if(candidateDetailsList != null && !candidateDetailsList.isEmpty()){
                return candidateDetailsList;
            }else{
                return  null;
            }
        
        
    }
    catch (Exception ex) 
    {
        throw new GenericException(ex);
    }
}
@SuppressWarnings("unchecked")
public List<CandidateBean> getCandidateDetailsListForReportForApproved(AgencyBean agencyBean, String validateValue)throws Exception
{
    List<CandidateBean> candidateDetailsList = null;
    try 
    {
        StringBuffer strBld = new StringBuffer();
        strBld.append(AgencyQueries.GET_DETAILS_OF_CANDIDATE_APPROVED_EXCEL);
        
        if(!ValidatorUtil.isEmpty(agencyBean.getRegistrationFromDate()) && !ValidatorUtil.isEmpty(agencyBean.getRegistrationToDate())){
            strBld.append(" AND to_char(ocd.ocd_created_date,'HH12:MI:SS') BETWEEN '"+agencyBean.getRegistrationFromDate()+"' AND '"+agencyBean.getRegistrationToDate() +"'");
        }
        strBld.append(" ORDER BY oum_user_id");
        
        candidateDetailsList =  getJdbcTemplate().query(strBld.toString(),new Object[]{validateValue, Integer.parseInt(agencyBean.getProgramName()),
            Integer.parseInt(agencyBean.getProgramName()),
            Integer.parseInt(agencyBean.getCategory()),
            Integer.parseInt(agencyBean.getCategory()),0,0}, new RowMapper<CandidateBean>(){   
                        public CandidateBean mapRow(ResultSet rs, int rowNum){   
                            
                            CandidateBean candidateBean = new CandidateBean();
                            PersonalDetailsBean personalBean = new PersonalDetailsBean();
                            StringBuilder stringBuilder = new StringBuilder();
                            try 
                            {
                                candidateBean.setUserId(rs.getString("oum_user_id"));
                                
                               // stringBuilder.append(rs.getString("OCD_CAND_TITLE")).append(" ");
			        			stringBuilder.append(rs.getString("OCD_FIRST_NAME")).append(" ");
			        			if (rs.getString("OCD_MIDDLE_NAME")!=null){
			        				stringBuilder.append(rs.getString("OCD_MIDDLE_NAME")).append(" ");
			        			}
			        			stringBuilder.append(rs.getString("OCD_LAST_NAME")).append(" ");
                                
			        			candidateBean.setFirstName(stringBuilder.toString());
                                
                                personalBean.setDateOfBirth(CommonUtil.formatTimeStamp(rs.getTimestamp("date_of_birth")));
                                candidateBean.setCommCity(rs.getString("ocd_comm_city"));
                                candidateBean.setStateVal(rs.getString("osm_state_name"));
                                personalBean.setEmail(rs.getString("oum_email_id"));
                                personalBean.setMobileNo(rs.getString("oum_mobile_no"));
                                candidateBean.setDisciplinName(rs.getString("otm_test_name"));
                                personalBean.setCategory(rs.getString("octm_category_code"));
                                candidateBean.setRegStrtDate(CommonUtil.formatTimeStamp(rs.getTimestamp("created_date")));
                                candidateBean.setTestCenterName1(ConfigurationConstants.getInstance().getTestCenterName(rs.getInt("centre1")));
                                candidateBean.setTestCenterName2(ConfigurationConstants.getInstance().getTestCenterName(rs.getInt("centre2")));
                                candidateBean.setInterviewLocation(rs.getString("OILM_INT_LOC_DESC"));
                                candidateBean.setPersonalDetailsBean(personalBean);
                                
                            } 
                            catch (Exception ex) 
                            {
                                LoggerHome.getLogger(getClass()).fatal(ex);
                            }
                            
                            return candidateBean;
                         }   
                    });   
            
        
            if(candidateDetailsList != null && !candidateDetailsList.isEmpty()){
                return candidateDetailsList;
            }else{
                return  null;
            }
        
        
    }
    catch (Exception ex) 
    {
        throw new GenericException(ex);
    }
}

@Override
public long getTrainingCentreForAccUser(Users loggedInUser)
		throws Exception {
	int testCentreID = 0;
	try {
		testCentreID = getJdbcTemplate().queryForObject(PaymentOfflineQueries.SELECT_TRAINING_CENTRE_FOR_ACC_USER, new Object[] {loggedInUser.getUserId()}, Integer.class);
				
	} catch (Exception e) {
		testCentreID = 0;
	}
	return testCentreID;
}

public Map<String, Integer> getGcetACCHomeData(long testCentreID) throws Exception 
{		
	int data = 0;
	Map<String,Integer> gcetAdminDataMap = new HashMap<String, Integer>();
	try{
		//START :: Application Form Management 
	    
	    //Total Candidates
	    data = getJdbcTemplate().queryForObject(AgencyQueries.GET_TOTAL_NO_OF_CANDIDATES_ACC, Integer.class);
        gcetAdminDataMap.put("TOTAL_NO_OF_CANDIDATES", data);
        
        //For Initiated
        StringBuilder queryForInitiatedCandidate = new StringBuilder();
        queryForInitiatedCandidate.append(AgencyQueries.GET_NO_OF_INITIATED_CANDIDATE_ACC);
        queryForInitiatedCandidate.append("AND osm.osm_status_pk in(");
        queryForInitiatedCandidate.append(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_INTITIATED)).append(",");
        queryForInitiatedCandidate.append(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_CONFIRMATION)).append(")");
        queryForInitiatedCandidate.append("AND ocd.OCD_TRAINING_CENTRE_FK = "+testCentreID);
		data = getJdbcTemplate().queryForObject(queryForInitiatedCandidate.toString(), new Object[]{}, Integer.class);
		gcetAdminDataMap.put("NO_OF_INITIATED_CANDIDATE", data);
		
		//For Submitted
		data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_APPLICATION_SUBMITTED_ACC,new Object[]{ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED),testCentreID}, Integer.class);
		gcetAdminDataMap.put("NO_OF_CANDIDATE_APPLICATION_SUBMITED", data);
		
		//For Approved
		data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_APPROVED,new Object[]{"1"}, Integer.class);
		gcetAdminDataMap.put("NO_OF_APPROVED_CANDIDATE", data);

		//For Rejected
		data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_APPROVED,new Object[]{"0"}, Integer.class);
		gcetAdminDataMap.put("NO_OF_REJECTED_CANDIDATE", data);
		
		//For Pending
		data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_APPLICATION_PENDING,new Object[]{ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED)}, Integer.class);
        gcetAdminDataMap.put("NO_OF_PENDING_CANDIDATE", data);
		//END :: Application Form Management
        
        //START :: Payment Management
        
        data = getJdbcTemplate().queryForObject(AgencyQueries.GET_PAYMENT_SUBMITTED_COUNT_ACC,new Object[]{testCentreID,testCentreID}, Integer.class);
        gcetAdminDataMap.put("NO_OF_PAYMENT_SUBMITTED", data);
        
        data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_PAYMENT_ACC,new Object[]{"A", "3",testCentreID,"A", "3",testCentreID}, Integer.class);
		gcetAdminDataMap.put("NO_OF_CANDIDATE_PAYMENT_APPROVED_DD", data);
		
		data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_PAYMENT_ACC,new Object[]{"A", "4",testCentreID,"A", "4",testCentreID}, Integer.class);
        gcetAdminDataMap.put("NO_OF_CANDIDATE_PAYMENT_APPROVED_CH", data);
        
        data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_PAYMENT_ACC,new Object[]{"A", "1",testCentreID,"A", "1",testCentreID}, Integer.class);
        gcetAdminDataMap.put("NO_OF_CANDIDATE_PAYMENT_APPROVED_NB", data);
        
        data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_PAYMENT_ACC,new Object[]{"A", "2",testCentreID,"A", "2",testCentreID}, Integer.class);
        gcetAdminDataMap.put("NO_OF_CANDIDATE_PAYMENT_APPROVED_CR", data);
        
        data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_PAYMENT_ACC,new Object[]{"A", "5",testCentreID,"A", "5",testCentreID}, Integer.class);
        gcetAdminDataMap.put("NO_OF_CANDIDATE_PAYMENT_APPROVED_CASH", data);
        
        data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_PAYMENT_ACC,new Object[]{"R", "3",testCentreID,"R", "3",testCentreID}, Integer.class);
		gcetAdminDataMap.put("NO_OF_CANDIDATE_PAYMENT_REJECTED_DD", data);
		
		data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_PAYMENT_ACC,new Object[]{"R", "4",testCentreID,"R", "4",testCentreID}, Integer.class);
        gcetAdminDataMap.put("NO_OF_CANDIDATE_PAYMENT_REJECTED_CH", data);
        
        data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_PAYMENT_ACC,new Object[]{"R", "5",testCentreID,"R", "5",testCentreID}, Integer.class);
        gcetAdminDataMap.put("NO_OF_CANDIDATE_PAYMENT_REJECTED_CASH", data);
        
        /*data = getJdbcTemplate().queryForObject(AgencyQueries.GET_CANDIDATE_COUNT_FOR_PAYMENT_FAILED_ACC,new Object[]{ConfigurationConstants.getInstance().getPaymentKeyByDesc(GenericConstants.NET_BANKING),GenericConstants.PAYMENT_REJECT_STATUS,testCentreID,ConfigurationConstants.getInstance().getPaymentKeyByDesc(GenericConstants.NET_BANKING),GenericConstants.PAYMENT_REJECT_STATUS,testCentreID}, Integer.class);*/
        data = getJdbcTemplate().queryForObject(AgencyQueries.GET_CANDIDATE_COUNT_FOR_PAYMENT_FAILED_ACC,new Object[]{ConfigurationConstants.getInstance().getPaymentKeyByDesc(GenericConstants.ONLINE),GenericConstants.PAYMENT_REJECT_STATUS,testCentreID,ConfigurationConstants.getInstance().getPaymentKeyByDesc(GenericConstants.ONLINE),GenericConstants.PAYMENT_REJECT_STATUS,testCentreID}, Integer.class);//Start : Added by SANTOSHKUMAR SINGH(Kohinoor) on 07th April 2018
        gcetAdminDataMap.put("NO_OF_CANDIDATE_PAYMENT_REJECTED_NB", data);
        
       /* data = getJdbcTemplate().queryForObject(AgencyQueries.GET_CANDIDATE_COUNT_FOR_PAYMENT_FAILED_ACC,new Object[]{ConfigurationConstants.getInstance().getPaymentKeyByDesc(GenericConstants.CREDIT_CARD),GenericConstants.PAYMENT_REJECT_STATUS,testCentreID,ConfigurationConstants.getInstance().getPaymentKeyByDesc(GenericConstants.NET_BANKING),GenericConstants.PAYMENT_REJECT_STATUS,testCentreID}, Integer.class);*/
        data = getJdbcTemplate().queryForObject(AgencyQueries.GET_CANDIDATE_COUNT_FOR_PAYMENT_FAILED_ACC,new Object[]{ConfigurationConstants.getInstance().getPaymentKeyByDesc(GenericConstants.CREDIT_CARD),GenericConstants.PAYMENT_REJECT_STATUS,testCentreID,ConfigurationConstants.getInstance().getPaymentKeyByDesc(GenericConstants.ONLINE),GenericConstants.PAYMENT_REJECT_STATUS,testCentreID}, Integer.class);//Start : Added by SANTOSHKUMAR SINGH(Kohinoor) on 07th April 2018
        gcetAdminDataMap.put("NO_OF_CANDIDATE_PAYMENT_REJECTED_CR", data);
        
        
        
        
        
		data = getJdbcTemplate().queryForObject(AgencyQueries.GET_PAYMENT_APPROVAL_PENDING_COUNT_ACC, new Object[]{"3",testCentreID}, Integer.class);
        gcetAdminDataMap.put("NO_OF_CANDIDATE_PENDING_PAYMENT_APPROVAL_DD", data);
        
        data = getJdbcTemplate().queryForObject(AgencyQueries.GET_PAYMENT_APPROVAL_PENDING_COUNT_ACC, new Object[]{"4",testCentreID}, Integer.class);
        gcetAdminDataMap.put("NO_OF_CANDIDATE_PENDING_PAYMENT_APPROVAL_CH", data);
        
        data = getJdbcTemplate().queryForObject(AgencyQueries.GET_PAYMENT_APPROVAL_PENDING_COUNT_ACC, new Object[]{"5",testCentreID}, Integer.class);
        gcetAdminDataMap.put("NO_OF_CANDIDATE_PENDING_PAYMENT_APPROVAL_CASH", data);
        
        /*data = getJdbcTemplate().queryForObject(AgencyQueries.GET_PAYMENT_APPROVAL_PENDING_COUNT, new Object[]{"1"}, Integer.class);
        gcetAdminDataMap.put("NO_OF_CANDIDATE_PENDING_PAYMENT_APPROVAL_NB", data);
        
        data = getJdbcTemplate().queryForObject(AgencyQueries.GET_PAYMENT_APPROVAL_PENDING_COUNT, new Object[]{"2"}, Integer.class);
        gcetAdminDataMap.put("NO_OF_CANDIDATE_PENDING_PAYMENT_APPROVAL_CR", data);*/
		
		//END :: Payment Management
		
        //START :: Schedule Management
		
        
        
        
		
		//*** schedule management - scenario 1 (application form - payment - otbs) *******

		//not scheduled ----- Total no. of candidates whose application form approved but payment details not submitted 
        if(ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.PAYMENT).equals("A")){
            StringBuilder builder = new StringBuilder();
            builder.append(AgencyQueries.GET_COUNT_OF_NON_SCHEDULED_CANDIDATE_PAYMENT_ON);
            if (ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.PAYMENT_APPROVE_REJECT).equals("A")){
            	builder.append(" or OPD_VALIDATED_STATUS ='R' ");
            }
            builder.append(" ) ) and ocd.ocd_user_fk not in( select OED_GCPD_USER_FK from oes_entrollment_details) and ocd.ocd_validated_status = 1");
            
            data = getJdbcTemplate().queryForObject(builder.toString(), Integer.class);
            gcetAdminDataMap.put("NO_OF_CANDIDATE_NON_SCHEDULED", data);
            
            
            
            
            
            StringBuilder builderPendingForScheduling = new StringBuilder();
            builderPendingForScheduling.append(AgencyQueries.GET_COUNT_OF_PENDING_FOR_SCHEDULED_CANDIDATE_PAYMENT_ON);
            
            if (ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.PAYMENT_APPROVE_REJECT).equals("A")){
            	builderPendingForScheduling.append(" and opd_validated_status = 'A' ");
            }
            builderPendingForScheduling.append(" and opd_user_fk not in( select oed_gcpd_user_fk from oes_entrollment_details)) ");
            //For Pending For Schedule Candidates
            //Total no. of Candidates whose application form approved and submitted payment details also approved but Candidate has not done OTBS/scheduling
            data = getJdbcTemplate().queryForObject(builderPendingForScheduling.toString(), Integer.class);
            gcetAdminDataMap.put("NO_OF_CANDIDATE_PENDING_FOR_SCHEDULED", data);
		}
        else{
            //For not scheduled
            data = getJdbcTemplate().queryForObject(AgencyQueries.GET_COUNT_OF_NON_SCHEDULED_CANDIDATE_PAYMENT_OFF, Integer.class);
            gcetAdminDataMap.put("NO_OF_CANDIDATE_NON_SCHEDULED", data);
            
            //For Pending For Schedule Candidates
            data = getJdbcTemplate().queryForObject(AgencyQueries.GET_COUNT_OF_PENDING_FOR_SCHEDULED_CANDIDATE_PAYMENT_OFF, Integer.class);
            gcetAdminDataMap.put("NO_OF_CANDIDATE_PENDING_FOR_SCHEDULED", data);
        }
        
        // For Schedule Candidates
        data = getJdbcTemplate().queryForObject(AgencyQueries.GET_COUNT_OF_SCHEDULED_CANDIDATE, Integer.class);
        gcetAdminDataMap.put("NO_OF_CANDIDATE_SCHEDULED", data);
        
		
			if(ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.GENERATE_ADMIT_CARD).equals("A"))
            {
                // Admit Card for all attempts 
                data = getJdbcTemplate().queryForObject(AgencyQueries.GET_CANDIDATE_COUNT_OF_ADMIT_CARD, Integer.class);
                gcetAdminDataMap.put("NO_OF_CANDIDATE_FOR_ADMIT_CARD", data);
            }
		    
        
		    
            
            
            
		//END :: Schedule Management
		
		// START COMMENTED BY VIJAYA
		/*data = getJdbcTemplate().queryForObject(AgencyQueries.GET_DATA_FOR_ATTEMPT ,new Object[]{new Integer(1)}, Integer.class);
        gcetAdminDataMap.put("NO_OF_ATTEMPT_1_CANDIDATE", data);
        
        data = getJdbcTemplate().queryForObject(AgencyQueries.GET_DATA_FOR_ATTEMPT,new Object[]{new Integer(2)}, Integer.class);
        gcetAdminDataMap.put("NO_OF_ATTEMPT_2_CANDIDATE", data);
        
		data = getJdbcTemplate().queryForObject(AgencyQueries.GET_CANDIDATE_COUNT_OF_ADMIT_CARD,new Object[]{new Integer(1),1}, Integer.class);
		gcetAdminDataMap.put("NO_OF_CANDIDATE_FOR_ADMIT_CARD_ATTEMPT_1", data);
		
		data = getJdbcTemplate().queryForObject(AgencyQueries.GET_CANDIDATE_COUNT_OF_ADMIT_CARD,new Object[]{new Integer(2),1}, Integer.class);
		gcetAdminDataMap.put("NO_OF_CANDIDATE_FOR_ADMIT_CARD_ATTEMPT_2", data);*/
		// END COMMENTED BY VIJAYA
		
		
		
		/*data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_FOR_REGISTERED,new Object[]{ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED)}, Integer.class);
		gcetAdminDataMap.put("NO_OF_REGISTERED_CANDIDATE", data);
		
		data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_BASED_ON_STAGE ,new Object[]{ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED)}, Integer.class);
		gcetAdminDataMap.put("NO_OF_CONFIRMED_CANDIDATE", data);
		
		data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_FOR_APPROVED ,new Object[]{ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_APPROVED)}, Integer.class);
		gcetAdminDataMap.put("NO_OF_APPROVED_CANDIDATE", data);
		
		data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_FOR_APPROVED ,new Object[]{ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_REJECTED)}, Integer.class);
		gcetAdminDataMap.put("NO_OF_REJECTED_CANDIDATE", data);
		
		data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_BASED_ON_STAGE ,new Object[]{ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_REJECTED)}, Integer.class);
		gcetAdminDataMap.put("NO_OF_ATTEMPT_1_CANDIDATE", data);
		
		data = getJdbcTemplate().queryForObject(AgencyQueries.GET_NO_OF_CANDIDATE_BASED_ON_STAGE ,new Object[]{ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_REJECTED)}, Integer.class);
		gcetAdminDataMap.put("NO_OF_ATTEMPT_2_CANDIDATE", data);*/

	}catch (Exception e) {
		throw new GenericException(e);
	}
	return gcetAdminDataMap;

}

@SuppressWarnings("unchecked")
@Override
public List<CandidateBean> getPondiCategoryWiseReport() throws Exception {
	List<CandidateBean> categoryWiseReportList = null;

    try 
    {
    	categoryWiseReportList =  getJdbcTemplate().query(AgencyQueries.GET_CATEGORY_WISE_REPORT_FOR_PU, new Object[]{}, new RowMapper<CandidateBean>(){   
                        public CandidateBean mapRow(ResultSet rs, int rowNum){   
                        	CandidateBean candidateBean = new CandidateBean();
                            try 
                            {
                            	candidateBean.setOtmPostName(rs.getString("OTM_TEST_NAME"));
                            	//candidateBean.setGeneral_unreserved(rs.getString("general"));
                            	candidateBean.setObc(rs.getString("oc"));
                            	candidateBean.setSc(rs.getString("sc"));
                            	//candidateBean.setSca(rs.getString("sca"));
                            	candidateBean.setSt(rs.getString("st"));
                            	candidateBean.setMbc(rs.getString("mbc"));
                            	candidateBean.setBc(rs.getString("bc"));
                            	candidateBean.setBcm(rs.getString("bcm"));
                            	candidateBean.setTotal(rs.getString("Total"));
                            } 
                            catch (Exception ex) 
                            {
                                LoggerHome.getLogger(getClass()).fatal(ex);
                                logger.fatal(ex, ex);
                            }
                            return candidateBean;
                         }   
                    });   
            
        
            if(categoryWiseReportList != null && !categoryWiseReportList.isEmpty()){
                return categoryWiseReportList;
            }else{
                return  null;
            }     
    }
    catch (Exception ex) 
    {
        throw new GenericException(ex);
    }
}

@SuppressWarnings("unchecked")
@Override
public List<CandidateBean> getPondiTestCenterWiseReport() throws Exception {
	List<CandidateBean> testCenterWiseReportList = null;

    try 
    {
    	testCenterWiseReportList =  getJdbcTemplate().query(AgencyQueries.GET_TEST_CENTER_WISE_REPORT_FOR_PU, new Object[]{}, new RowMapper<CandidateBean>(){   
                        public CandidateBean mapRow(ResultSet rs, int rowNum){   
                        	CandidateBean candidateBean = new CandidateBean();
                            try 
                            {
                            	candidateBean.setOtmPostName(rs.getString("OTCM_TEST_CENTRE_NAME"));
                            	candidateBean.setAm_java(rs.getString("teti"));
                            	candidateBean.setAm_dotnet(rs.getString("tetii"));
                            //	candidateBean.setAm_networking(rs.getString("JuniorAssistant"));
                            //	candidateBean.setMt_adminhr(rs.getString("Typist"));
                            	candidateBean.setTotal(rs.getString("Total"));
                            } 
                            catch (Exception ex) 
                            {
                                LoggerHome.getLogger(getClass()).fatal(ex);
                                logger.fatal(ex, ex);
                            }
                            return candidateBean;
                         }   
                    });   
            
        
            if(testCenterWiseReportList != null && !testCenterWiseReportList.isEmpty()){
                return testCenterWiseReportList;
            }else{
                return  null;
            }     
    }
    catch (Exception ex) 
    {
        throw new GenericException(ex);
    }
}



}
