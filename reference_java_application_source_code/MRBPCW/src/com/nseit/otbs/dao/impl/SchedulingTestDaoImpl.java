package com.nseit.otbs.dao.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.RowMapper;

import com.nseit.generic.dao.BaseDAO;
import com.nseit.generic.models.HallTicketBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.GenericException;
import com.nseit.otbs.dao.SchedulingTestDao;
import com.nseit.otbs.model.BatchDetailsBean;
import com.nseit.otbs.model.CommonBean;
import com.nseit.otbs.model.EnrollmentDetailsBean;
import com.nseit.otbs.model.SchedulingTestBean;
import com.nseit.otbs.queries.SchedulingTestQueries;


public class SchedulingTestDaoImpl extends BaseDAO implements SchedulingTestDao {
	
	private JdbcTemplate writeJdbcTemplate;
	
	public void setWriteJdbcTemplate(JdbcTemplate writeJdbcTemplate) {
		this.writeJdbcTemplate = writeJdbcTemplate;
	}
	
	@SuppressWarnings("unchecked")
	public List<CommonBean> getCityForTestCenters() throws Exception {
		
		
		
		List<CommonBean> cityList = null;
		try {
			cityList = getJdbcTemplate().query(SchedulingTestQueries.GET_CITY,
					new Object[] {}, new RowMapper<CommonBean>() {
						public CommonBean mapRow(ResultSet rs, int rowCount)
								throws SQLException {
							CommonBean commonBean = new CommonBean();
							commonBean.setLabelId(rs.getString("otcm_CITY"));
							commonBean.setLabelValue(rs.getString("otcm_CITY"));
							return commonBean;
						}
					});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		
		return cityList;
	}

	@SuppressWarnings("unchecked")
	public List<CommonBean> getTestCenterBasedOnCity(SchedulingTestBean schedulingTestBean)
			throws Exception {
		
		List<CommonBean> tstCenterList = null;
		try {
			if(schedulingTestBean.getBookingAttempt() == 1)
			{
				tstCenterList = getJdbcTemplate().query(
						SchedulingTestQueries.GET_TEST_CENTRE_BASED_ON_CITY_FOR_ATTEMPT_1,
						new Object[] { schedulingTestBean.getCmbTstCenter() },
						new RowMapper<CommonBean>() {
							public CommonBean mapRow(ResultSet rs, int rowCount)
									throws SQLException {
								CommonBean commonBean = new CommonBean();
								commonBean.setLabelId(rs.getString("otcm_TEST_CENTRE_PK")== null ? "" :rs.getString("otcm_TEST_CENTRE_PK") );
								commonBean.setLabelValue(rs.getString("otcm_TEST_CENTRE_NAME")== null ? "" :rs.getString("otcm_TEST_CENTRE_NAME") );
										
								return commonBean;
							}
						});
			}else if(schedulingTestBean.getBookingAttempt() == 2)
			{
				tstCenterList = getJdbcTemplate().query(
						SchedulingTestQueries.GET_TEST_CENTRE_BASED_ON_CITY_FOR_ATTEMPT_2,
						new Object[] { schedulingTestBean.getCmbTstCenter() },
						new RowMapper<CommonBean>() {
							public CommonBean mapRow(ResultSet rs, int rowCount)
									throws SQLException {
								CommonBean commonBean = new CommonBean();
								commonBean.setLabelId(rs.getString("otcm_TEST_CENTRE_PK")== null ? "" :rs.getString("otcm_TEST_CENTRE_PK") );
								commonBean.setLabelValue(rs.getString("otcm_TEST_CENTRE_NAME")== null ? "" :rs.getString("otcm_TEST_CENTRE_NAME") );
										
								return commonBean;
							}
						});
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		
		return tstCenterList;
	}

	public String getTestCenterAddress(String tstCntrPk) throws Exception {
		
		String address = "";

		try {
			address = (String) getJdbcTemplate().queryForObject(
					SchedulingTestQueries.GET_ADDRESS_FOR_TEST_CENTER,
					new Object[] { Integer.parseInt(tstCntrPk) }, String.class);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		
		return address == null? "" : address ;

	}

	@SuppressWarnings("unchecked")
	public List<String> getTestDatesForTstCenter(SchedulingTestBean schedulingTestBean)
			throws Exception {
		
		List<String> tstDateList = null;
		List<Long> definedDatesForExam =  null;
		Integer attempt = null;
		attempt = ConfigurationConstants.getInstance().getDateWindowPkForDesc(GenericConstants.EXAM_DATE_FOR_ATTEMPT_ONE);

		try {
			if(schedulingTestBean.getBookingAttempt()==1)
			{
				definedDatesForExam = ConfigurationConstants.getInstance().getDateListFromDateWindow(GenericConstants.ATTEMPT_ONE_DATES);
				
			}else{
				definedDatesForExam = ConfigurationConstants.getInstance().getDateListFromDateWindow(GenericConstants.ATTEMPT_TWO_DATES);
				
			}
			tstDateList = getJdbcTemplate().query(
					SchedulingTestQueries.GET_TEST_DATES_FOR_TEST_CENTER,
					new Object[] { Integer.parseInt( schedulingTestBean.getCmbTstCenter()) , new Date(definedDatesForExam.get(0)) , new Date(definedDatesForExam.get(1)),attempt },
					new RowMapper<String>() {
						public String mapRow(ResultSet rs, int rowCount)
								throws SQLException {
							String tstDate = rs.getString("TEST_DATE");
							return tstDate;
						}
					});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		
		return tstDateList;

	}
	@SuppressWarnings("unchecked")
	public List<String> getTestDatesForTstCenterForSecondAttempt(SchedulingTestBean schedulingTestBean,Users loggedInUser,BatchDetailsBean batchDtlsBean) throws Exception{
		
			List<String> tstDateList = null;
			List<Long> definedDatesForExam =  null;
			Integer attempt = null;
			attempt = ConfigurationConstants.getInstance().getDateWindowPkForDesc(GenericConstants.EXAM_DATE_FOR_ATTEMPT_TWO);
			try {
				definedDatesForExam = ConfigurationConstants.getInstance().getDateListFromDateWindow(GenericConstants.ATTEMPT_TWO_DATES);
				
				tstDateList = getJdbcTemplate().query(
						SchedulingTestQueries.GET_TEST_DATES_FOR_TEST_CENTER_FOR_SECOND_ATTEMPT,
						new Object[] { Integer.parseInt( schedulingTestBean.getCmbTstCenter()),batchDtlsBean.getTestDate() ,new Date(definedDatesForExam.get(0)) , new Date(definedDatesForExam.get(1)),attempt },
						new RowMapper<String>() {
							public String mapRow(ResultSet rs, int rowCount)
									throws SQLException {
								String tstDate = rs.getString("TEST_DATE");
								return tstDate;
							}
						});
			} catch (Exception e) {
				throw new GenericException(e);
			}
			return tstDateList;
			
	}

	@SuppressWarnings("unchecked")
	public List<BatchDetailsBean> getBatchDetails(
			SchedulingTestBean schedulingTestBean,Users loggedInUsers) throws Exception {
		
		List<BatchDetailsBean> batchDtlsList = null;
		Integer attempt = null;

		if (schedulingTestBean!=null && schedulingTestBean.getBookingAttempt() == new Integer(1)){
			attempt = ConfigurationConstants.getInstance().getDateWindowPkForDesc(GenericConstants.EXAM_DATE_FOR_ATTEMPT_ONE);
		}
		
		if (schedulingTestBean!=null && schedulingTestBean.getBookingAttempt() == new Integer(2)){
			attempt = ConfigurationConstants.getInstance().getDateWindowPkForDesc(GenericConstants.EXAM_DATE_FOR_ATTEMPT_TWO);
		}


		try {
			batchDtlsList = getJdbcTemplate().query(
					SchedulingTestQueries.GET_BATCH_DETAILS_FOR_TEST_CENTER,
					new Object[] {
									Integer.parseInt(schedulingTestBean.getCmbTstCenter()),
									schedulingTestBean.getCmbTestFromDate(),
									schedulingTestBean.getCmbTestToDate(),
									attempt 
								},
					new RowMapper<BatchDetailsBean>() {
						public BatchDetailsBean mapRow(ResultSet rs,
								int rowCount) throws SQLException {
							BatchDetailsBean tmpBatchDetailsBean = new BatchDetailsBean();
							;
							if (Integer.parseInt(rs
									.getString("AVAILABLE_SEATS")) > 0) {

								tmpBatchDetailsBean.setTestDate(rs
										.getString("TEST_DATE"));
								tmpBatchDetailsBean.setTestDay(rs
										.getString("TEST_DAY"));
								tmpBatchDetailsBean.setTestStartTime(rs
										.getString("START_TIME"));
								tmpBatchDetailsBean.setTestEndTime(rs
										.getString("END_TIME"));
								tmpBatchDetailsBean.setAvailableSeats(rs
										.getString("AVAILABLE_SEATS"));
								tmpBatchDetailsBean.setBatchPK(rs
										.getInt("obd_BATCH_PK"));
								tmpBatchDetailsBean.setReportingTime(rs.getString("REPORTING_TIME"));
							}
							return tmpBatchDetailsBean;
						}
					});
			}
		 catch (Exception e) {
			 throw new GenericException(e);
		}
		 
		return batchDtlsList; 
	}
	
	public BatchDetailsBean getBatchDetailsOfFirstAttempt(Users loggedInUser) throws Exception
	{
		
		BatchDetailsBean batchDetailFirstAttempt = null;
		try{
			batchDetailFirstAttempt = (BatchDetailsBean)getJdbcTemplate().queryForObject(
					SchedulingTestQueries.GET_BATCH_DETAILS_OF_FIRST_ATTEMPT,new Object[] { loggedInUser.getUserId() }, 
					new BeanPropertyRowMapper(BatchDetailsBean.class));
		 
		

		}catch (Exception e) {
			throw new GenericException(e);
		}
		
		return batchDetailFirstAttempt;
	}
	
	@SuppressWarnings("unchecked")
	public List<BatchDetailsBean>  getBatchDetailsForSecondAttempt(SchedulingTestBean schedulingTestBean,BatchDetailsBean batchDtlsBean,Users loggedInUsers) throws Exception
	{
		
		List<BatchDetailsBean> batchDtlsList = null;
		try{
			batchDtlsList = getJdbcTemplate().query(
				SchedulingTestQueries.GET_BATCH_DETAILS_FOR_SECOND_ATTEMPT,
				new Object[] {
						Integer.parseInt(schedulingTestBean.getCmbTstCenter()),
						schedulingTestBean.getCmbTestFromDate(),
						schedulingTestBean.getCmbTestToDate(),
						batchDtlsBean.getTestDate(),
						Integer.parseInt(schedulingTestBean.getCmbTstCenter()),
						batchDtlsBean.getTestDate(),
						batchDtlsBean.getTestEndTime() },
				new RowMapper<BatchDetailsBean>() {
					public BatchDetailsBean mapRow(ResultSet rs,
							int rowCount) throws SQLException {
						BatchDetailsBean tmpBatchDetailsBean = new BatchDetailsBean();
						;
						if (Integer.parseInt(rs
								.getString("AVAILABLE_SEATS")) > 0) {

							tmpBatchDetailsBean.setTestDate(rs
									.getString("TEST_DATE"));
							tmpBatchDetailsBean.setTestDay(rs
									.getString("TEST_DAY"));
							tmpBatchDetailsBean.setTestStartTime(rs
									.getString("START_TIME"));
							tmpBatchDetailsBean.setTestEndTime(rs
									.getString("END_TIME"));
							tmpBatchDetailsBean.setAvailableSeats(rs
									.getString("AVAILABLE_SEATS"));
							tmpBatchDetailsBean.setBatchPK(rs
									.getInt("obd_BATCH_PK"));
						}
						return tmpBatchDetailsBean;
					}
				});
		}catch (Exception e) {
			throw new GenericException(e);
		}
		
		return batchDtlsList;
	}

	public String blockSeatForCandidate(final long user, final int batchPK,final String seatStatus) throws Exception {
		
		String regOutput = null;
		Map outputMap = null;
		List<SqlParameter> declaredParameters = new ArrayList<SqlParameter>();
		try {
			// declaredParameters.add(new
			// SqlOutParameter("v_out",Types.VARCHAR));
			declaredParameters.add(new SqlParameter("v_user", Types.INTEGER));
			declaredParameters.add(new SqlParameter("v_batch", Types.INTEGER));
			declaredParameters.add(new SqlParameter("v_batch_state",Types.VARCHAR));
			declaredParameters.add(new SqlOutParameter("v_out", Types.VARCHAR));

			/*
			 * writeJdbcTemplate.update("call sp_batch_assign(?, ?, ?,?)", new
			 * Object[] { user, batchPK, seatStatus, ret });
			 */

			outputMap = getJdbcTemplate().call(new CallableStatementCreator() {
				@Override
				public CallableStatement createCallableStatement(Connection con)throws SQLException {
					CallableStatement stmnt = con.prepareCall("{ call sp_batch_assign(?,?,?,?)}");
					stmnt.setLong(1, user);
					stmnt.setInt(2, batchPK);
					stmnt.setString(3, seatStatus);
					stmnt.registerOutParameter(4, Types.VARCHAR);

					return stmnt;
				}
			}, declaredParameters);

			regOutput = (String) outputMap.get("v_out");
			if("Blocked".compareTo(regOutput)==0 || "Confirmed".compareTo(regOutput)==0 || "Released".compareTo(regOutput)==0 || "No Seat Available".compareTo(regOutput)==0){
				
			}
			else{
				throw new Exception();
			}

		} catch (Exception e) {
			throw new GenericException(e);
		}
		
		return regOutput;
	}

	public int getSeatAvaibilityForBatch(int bathPK)  throws Exception{
		

		int count = 0;
		try {
			count = getJdbcTemplate().queryForObject(
					SchedulingTestQueries.GET_SEAT_COUNT_FOR_CANDIDATE,
					new Object[] { bathPK },Integer.class);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		
		return count;
	}

	public int insertCandidateEnrollmentDetails(
			EnrollmentDetailsBean enrllmntBean, Users loggedInUser) throws Exception {
		
		int count = 0;
		try {

			count = writeJdbcTemplate.update(
					SchedulingTestQueries.INSERT_ENROLLMENT_DTLS,
					new Object[] { enrllmntBean.getUserFK(), null,
							enrllmntBean.getBatch_FK(), 
							enrllmntBean.getBatchAttempt(),
							loggedInUser.getUsername(),
							loggedInUser.getUsername() });
			if (count > 0 ) {
				count = writeJdbcTemplate.update(
						SchedulingTestQueries.UPDATE_STAGE_CANDIDATE,
						new Object[] { enrllmntBean.getStage(),
								loggedInUser.getUsername(),
								enrllmntBean.getUserFK() });
				
				
				enrllmntBean = getEnrollmentDetailsBean(loggedInUser, enrllmntBean.getBatchAttempt()); 
				
//				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//				Code128 barcode = new Code128();
//				barcode.setData(loggedInUser.getUsername());								
//				barcode.drawBarcode(byteArrayOutputStream);
//				byte [] barCodeImageString = byteArrayOutputStream.toByteArray();
//				
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				BitmapCanvasProvider provider = new BitmapCanvasProvider(
						byteArrayOutputStream, "image/x-png", 300, BufferedImage.TYPE_BYTE_GRAY, true, 0);
				
				
				org.krysalis.barcode4j.impl.code128.Code128 code128 = new org.krysalis.barcode4j.impl.code128.Code128();			
				code128.generateBarcode(provider, enrllmntBean.getEnrollmentPK());
				
				provider.finish();
				byte [] barCodeImageString = byteArrayOutputStream.toByteArray();
				
				count = writeJdbcTemplate.update(
						SchedulingTestQueries.INSERT_BAR_CODE_IMAGE,
						new Object[] { enrllmntBean.getEnrollmentPK(),
								barCodeImageString
								});
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		
		return count;
	}

	public int blockSeatForCandidate(int batchID, int user_pk) {
		int batchEntry = 0;
		int blockDtlsEntry = 0;
		
		batchEntry = writeJdbcTemplate.update(
				SchedulingTestQueries.UPDATE_BATCH_CAPCITY_TO_HOLD_SEAT,
				new Object[] { batchID });
		if (batchEntry > 0) {
			blockDtlsEntry = writeJdbcTemplate.update(
					SchedulingTestQueries.INSERT_BLOCKED_DETAILS,
					new Object[] { batchID, user_pk });
		}
		return blockDtlsEntry;
	}

	public int confirmSeatForCandidate(int batchID, int user_pk) {
		int batchEntry = 0;
		int releaseBlockEnry = 0;

		batchEntry = writeJdbcTemplate.update(
				SchedulingTestQueries.UPDATE_BATCH_CAPCITY_TO_CONFIRM_SEAT,
				new Object[] { batchID });
		if (batchEntry > 0) {
			releaseBlockEnry = writeJdbcTemplate.update(
					SchedulingTestQueries.DELETE_BLOCKED_DETAILS,
					new Object[] { user_pk });
		}
		return releaseBlockEnry;
	}

	public int releaseSeatForCandidate(int batchID, int user_pk) {
		int batchEntry = 0;
		int releaseBlockEnry = 0;

		batchEntry = writeJdbcTemplate.update(
				SchedulingTestQueries.UPDATE_BATCH_CAPCITY_RELEASE_SEAT,
				new Object[] { batchID });
		if (batchEntry > 0) {
			releaseBlockEnry = writeJdbcTemplate.update(
					SchedulingTestQueries.DELETE_BLOCKED_DETAILS,
					new Object[] { user_pk });
		}

		return releaseBlockEnry;
	}
	
	@SuppressWarnings("unchecked")
	public String getDownloadDetails(String activityName) throws Exception{
		List<SchedulingTestBean> activityList = null;
		try{
		activityList =  getJdbcTemplate().query(
		SchedulingTestQueries.GET_DOWNLOAD_PATH, new Object[]{activityName},new RowMapper<SchedulingTestBean>(){   
        	public SchedulingTestBean mapRow(ResultSet rs, int rowNum){   
        		SchedulingTestBean candidateBean = new SchedulingTestBean();
        		try {
        			//PATH_VALUE
            			candidateBean.setDownloadPath(rs.getString("OCPT_PATH_VALUE"));
				} catch (Exception e) {
					e.printStackTrace();
				}
        		return candidateBean;
             }   
        });   

	if(activityList != null && !activityList.isEmpty()){
		return activityList.get(0).getDownloadPath();
	}else{
		return  null;
	}
	}catch (Exception e) {
		throw new GenericException(e);
	}
	
}
	public EnrollmentDetailsBean getEnrollmentDetailsBean(Users loggedInUser,int batchAttempt) throws Exception
	{
		
		EnrollmentDetailsBean enrollmntDetailFirstAttempt = null;
		try
		{
			enrollmntDetailFirstAttempt = (EnrollmentDetailsBean)getJdbcTemplate().queryForObject(
					SchedulingTestQueries.GET_ENROLLMENT_DETAILS_BASED_ON_USER_ID,new Object[] { loggedInUser.getUserId(),batchAttempt }, 
					new BeanPropertyRowMapper(EnrollmentDetailsBean.class));
		}
		catch (Exception e)
		{
			throw new GenericException(e);
		}
		
		return enrollmntDetailFirstAttempt;
	}

	@Override
	public SchedulingTestBean getSchedulingTestBean(Users loggedInUser,
			int batchAttempt, String enrollmentPK) throws Exception {
		
		SchedulingTestBean schedulingTestBean = null;
		try{
			schedulingTestBean = (SchedulingTestBean)getJdbcTemplate().queryForObject(
					SchedulingTestQueries.GET_SCHEDULINGTEST_BEAN_BASED_ON_USER_ID_ATTEMPT_TYPE,new Object[] {loggedInUser.getUserId(),enrollmentPK, batchAttempt}, 
					new BeanPropertyRowMapper(SchedulingTestBean.class));
			
			

		}catch (Exception e) {
			throw new GenericException(e);
		}
		
		return schedulingTestBean;
	} 
	@SuppressWarnings("unchecked")
	public HallTicketBean getHallTicketDetailsBean(Users loggedInUser,int batchAttempt) throws Exception{
		List<HallTicketBean> hallTicketBeanList = null;
		try{
			hallTicketBeanList =  getJdbcTemplate().query(SchedulingTestQueries.GET_HALLTICKET_DATA, new Object[]{batchAttempt,loggedInUser.getUserFk()},new RowMapper<HallTicketBean>(){   
			        	public HallTicketBean mapRow(ResultSet rs, int rowNum){  
			        		HallTicketBean hallTicketBean = new HallTicketBean();
			        		StringBuilder stringBuilder = new StringBuilder();
			        		try {
			        			stringBuilder.append(rs.getString("OCD_CAND_TITLE")).append(" ");
			        			stringBuilder.append(rs.getString("OCD_FIRST_NAME")).append(" ");
			        			if (rs.getString("OCD_MIDDLE_NAME")!=null){
			        				stringBuilder.append(rs.getString("OCD_MIDDLE_NAME")).append(" ");
			        			}
			        			stringBuilder.append(rs.getString("OCD_LAST_NAME")).append(" ");
			        			
			        			
			        			hallTicketBean.setDisciplineType(rs.getString("otm_test_name"));
			        			hallTicketBean.setUserId(rs.getString("oum_user_id"));
			        			hallTicketBean.setEnrollmetPk(rs.getString("oed_enrollment_pk"));
			        			
			        			hallTicketBean.setUserPassword(rs.getString("oum_password"));
			        			
			        			
			        			hallTicketBean.setUserName(stringBuilder.toString());
			        			
			        			hallTicketBean.setTestCenterAddress(rs.getString("otcm_address"));
			        			hallTicketBean.setTestCenterName(rs.getString("otcm_test_centre_name"));
			        			hallTicketBean.setTestDate(rs.getString("test_date"));
			        			hallTicketBean.setTestStartTime(rs.getString("start_time"));
			        			hallTicketBean.setReportingTime(rs.getString("reporting_time"));
			        			hallTicketBean.setRollNumber(rs.getString("OED_ROLL_NO"));
			        			hallTicketBean.setHallticketFlag(rs.getString("OED_FLAG"));
							} catch (Exception e) {
								e.printStackTrace();
							}
			        		return hallTicketBean;
			             }   
			        });   

				if(hallTicketBeanList != null && !hallTicketBeanList.isEmpty()){
					return hallTicketBeanList.get(0);
				}else{
					return  null;
				}
				}
		catch (Exception e){
			throw new GenericException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public HallTicketBean getHallTicketDetailsBeanBasedonUserId(String userId,int batchAttempt) throws Exception{
		List<HallTicketBean> hallTicketBeanList = null;
		try{
			hallTicketBeanList =  getJdbcTemplate().query(SchedulingTestQueries.GET_HALLTICKET_DATA_FOR_USER_ID, new Object[]{batchAttempt,userId},new RowMapper<HallTicketBean>(){   
			        	public HallTicketBean mapRow(ResultSet rs, int rowNum){  
			        		HallTicketBean hallTicketBean = new HallTicketBean();
			        		StringBuilder  stringBuilder = new StringBuilder();
			        		try {
			        			stringBuilder.append(rs.getString("OCD_CAND_TITLE")).append(" ");
			        			stringBuilder.append(rs.getString("OCD_FIRST_NAME")).append(" ");
			        			if (rs.getString("OCD_MIDDLE_NAME")!=null){
			        				stringBuilder.append(rs.getString("OCD_MIDDLE_NAME")).append(" ");
			        			}
			        			stringBuilder.append(rs.getString("OCD_LAST_NAME")).append(" ");
			        			
			        			
			        			hallTicketBean.setDisciplineType(rs.getString("otm_test_name"));
			        			hallTicketBean.setUserId(rs.getString("oum_user_id"));
			        			hallTicketBean.setEnrollmetPk(rs.getString("oed_enrollment_pk"));
			        			
			        			hallTicketBean.setUserPassword(rs.getString("oum_password"));
			        			
			        			hallTicketBean.setUserName(stringBuilder.toString());
			        			
			        			hallTicketBean.setTestCenterAddress(rs.getString("otcm_address"));
			        			hallTicketBean.setTestCenterName(rs.getString("otcm_test_centre_name"));
			        			hallTicketBean.setTestDate(rs.getString("test_date"));
			        			hallTicketBean.setTestStartTime(rs.getString("start_time"));
			        			hallTicketBean.setReportingTime(rs.getString("reporting_time"));
			        			hallTicketBean.setRollNumber(rs.getString("OED_ROLL_NO"));
			        			hallTicketBean.setHallticketFlag(rs.getString("OED_FLAG"));
							} catch (Exception e) {
								e.printStackTrace();
							}
			        		return hallTicketBean;
			             }   
			        });   

				if(hallTicketBeanList != null && !hallTicketBeanList.isEmpty()){
					return hallTicketBeanList.get(0);
				}else{
					return  null;
				}
				}
		catch (Exception e){
			throw new GenericException(e);
		}
	}
	@SuppressWarnings("unchecked")
	public String getEnrollmentDetails(Long userFk) throws Exception{
		String enrollmentPk = "";
		List<SchedulingTestBean> candidateDetailsList = null;
		try{
			candidateDetailsList =  getJdbcTemplate().query(SchedulingTestQueries.GET_CANDIDATE_DETAILS_OF_BOOKING, new Object[]{userFk},new RowMapper<SchedulingTestBean>(){   
			        	public SchedulingTestBean mapRow(ResultSet rs, int rowNum){  
			        		SchedulingTestBean schedulingTestBean = new SchedulingTestBean();
			        		try {
			        			schedulingTestBean.setEnrollmentPK(rs.getString("OED_ENROLLMENT_PK"));
							} catch (Exception e) {
								e.printStackTrace();
							}
			        		return schedulingTestBean;
			             }   
			        });   

				if(candidateDetailsList != null && !candidateDetailsList.isEmpty()){
					return candidateDetailsList.get(0).getEnrollmentPK();
				}else{
					return  null;
				}
				}
		catch (Exception e){
			throw new GenericException(e);
		}
	}
	
	
}
