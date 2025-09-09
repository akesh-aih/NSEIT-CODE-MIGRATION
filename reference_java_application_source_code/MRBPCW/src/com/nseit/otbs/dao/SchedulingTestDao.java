package com.nseit.otbs.dao;

import java.util.List;



import com.nseit.generic.models.HallTicketBean;
import com.nseit.generic.models.Users;
import com.nseit.otbs.model.BatchDetailsBean;
import com.nseit.otbs.model.CommonBean;
import com.nseit.otbs.model.EnrollmentDetailsBean;
import com.nseit.otbs.model.SchedulingTestBean;

public interface SchedulingTestDao {

	public List<CommonBean> getCityForTestCenters() throws Exception;
	public List<CommonBean> getTestCenterBasedOnCity(SchedulingTestBean schedulingTestBean ) throws Exception;
	public String getTestCenterAddress(String tstCntrId) throws Exception;
	public List<String> getTestDatesForTstCenter (SchedulingTestBean schedulingTestBean) throws Exception;
	public List<BatchDetailsBean> getBatchDetails(SchedulingTestBean schedulingTestBean, Users loggedInUser) throws Exception;
	public String blockSeatForCandidate( long user, int batchPK,String seatStatus)throws Exception;
	public int insertCandidateEnrollmentDetails(EnrollmentDetailsBean enrllmntBean, Users loggedInUser) throws Exception;
	public int getSeatAvaibilityForBatch(int bathPK) throws Exception;
	public List<BatchDetailsBean>  getBatchDetailsForSecondAttempt(SchedulingTestBean schedulingTestBean,BatchDetailsBean batchDtlsBean,Users loggedInUsers) throws Exception;
	public BatchDetailsBean getBatchDetailsOfFirstAttempt(Users loggedInUser) throws Exception;
	public List<String> getTestDatesForTstCenterForSecondAttempt(SchedulingTestBean schedulingTestBean,Users loggedInUser,BatchDetailsBean batchDtlsBean) throws Exception;
	public String getDownloadDetails(String activityName) throws Exception;
	public EnrollmentDetailsBean getEnrollmentDetailsBean(Users loggedInUser,int batchAttempt) throws Exception;
	public SchedulingTestBean getSchedulingTestBean(Users loggedInUser,int batchAttempt, String enrollmentPK) throws Exception;
	public HallTicketBean getHallTicketDetailsBean(Users loggedInUser,int batchAttempt) throws Exception;
	
	public String getEnrollmentDetails(Long userFk) throws Exception;
	
	public HallTicketBean getHallTicketDetailsBeanBasedonUserId(String userId,int batchAttempt) throws Exception;
}

