package com.nseit.otbs.service.impl;


import java.util.List;



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
import com.nseit.otbs.service.SchedulingTestService;

public class SchedulingTestServiceImpl implements SchedulingTestService{
	private SchedulingTestDao schedulingTestDao;
	
	

	public List<CommonBean> getCityForTestCenters() throws Exception
	{
		return schedulingTestDao.getCityForTestCenters();
	}



	public void setSchedulingTestDao(SchedulingTestDao schedulingTestDao) {
		this.schedulingTestDao = schedulingTestDao;
	}
	public List<CommonBean> getTestCenterBasedOnCity(SchedulingTestBean schedulingTestBean ) throws Exception
	{
		return schedulingTestDao.getTestCenterBasedOnCity(schedulingTestBean);
	}

	public String getTestCenterAddress(String tstCntrPk) throws Exception
	{
		return schedulingTestDao.getTestCenterAddress(tstCntrPk);
	}
	
	public List<String> getTestDatesForTstCenter (SchedulingTestBean schedulingTestBean,Users loggedInUser) throws Exception
	{
		List<String> testDates =null;
		BatchDetailsBean btachBean = null;

		if(schedulingTestBean.getBookingAttempt()==1)
		{
			testDates = schedulingTestDao.getTestDatesForTstCenter(schedulingTestBean);
		}else
		{
			btachBean=schedulingTestDao.getBatchDetailsOfFirstAttempt(loggedInUser);
			testDates=  schedulingTestDao.getTestDatesForTstCenterForSecondAttempt(schedulingTestBean, loggedInUser,btachBean);
		}
		return testDates;
	}
	public List<BatchDetailsBean> getBatchDetails(SchedulingTestBean schedulingTestBean,Users loggedInUser) throws Exception
	{
		
			List<BatchDetailsBean> batchDtlsList = null;
			BatchDetailsBean btachBean = null;
			/*
			 * Commented to change condition of displaying only those slots which are after the date of First attempt seat booked
		if(schedulingTestBean.getBookingAttempt()==2)
		{
			
			btachBean=schedulingTestDao.getBatchDetailsOfFirstAttempt(loggedInUser);
			Date testDate= CommonUtil.getDate(btachBean.getTestDate(),"dd-MMM-yyyy");
			Date testFromDate =  CommonUtil.getDate(schedulingTestBean.getCmbTestFromDate(),"dd-MMM-yyyy");
			Date testToDate = CommonUtil.getDate(schedulingTestBean.getCmbTestToDate(),"dd-MMM-yyyy");
			if(testFromDate.getTime()<=   testDate.getTime() && testDate.getTime() <= testToDate.getTime())
			{
				batchDtlsList = schedulingTestDao.getBatchDetailsForSecondAttempt(schedulingTestBean,btachBean,loggedInUser);
				
			}else
			{
				batchDtlsList =schedulingTestDao.getBatchDetails(schedulingTestBean,loggedInUser);
			}
			
			
		}else{
			batchDtlsList =schedulingTestDao.getBatchDetails(schedulingTestBean,loggedInUser);
		}*/
		batchDtlsList =schedulingTestDao.getBatchDetails(schedulingTestBean,loggedInUser);
		return  batchDtlsList;
	}
	public String blockSeatForCandidate(SchedulingTestBean schedulingTestBean,Users loggedInUser) throws Exception
	{
		String  regOutput = null;
		try
		{
			regOutput = schedulingTestDao.blockSeatForCandidate(loggedInUser.getUserId(), schedulingTestBean.getBatchPK(), schedulingTestBean.getSeatStatus());
			
			if("Confirmed".equalsIgnoreCase(regOutput))
			{
				EnrollmentDetailsBean enrllmntBean = new  EnrollmentDetailsBean();
				enrllmntBean.setBatch_FK(schedulingTestBean.getBatchPK());
				enrllmntBean.setUserFK(loggedInUser.getUserId());
				if(schedulingTestBean.getBookingAttempt()==1)
				{
					enrllmntBean.setStage(ConfigurationConstants.getInstance().getStageKey(GenericConstants.STAGE_FIRST_ATTEMPT_SEAT_BOOKED));
					loggedInUser.setStage(""+ConfigurationConstants.getInstance().getStageKey(GenericConstants.STAGE_FIRST_ATTEMPT_SEAT_BOOKED));
				}
				else if(schedulingTestBean.getBookingAttempt()==2)
				{
					enrllmntBean.setStage(ConfigurationConstants.getInstance().getStageKey(GenericConstants.STAGE_SECOND_ATTEMPT_SEAT_BOOKED));
					loggedInUser.setStage(""+ConfigurationConstants.getInstance().getStageKey(GenericConstants.STAGE_SECOND_ATTEMPT_SEAT_BOOKED));
				}
				enrllmntBean.setBatchAttempt(schedulingTestBean.getBookingAttempt());
				
				schedulingTestDao.insertCandidateEnrollmentDetails(enrllmntBean,loggedInUser);
				
				/*
				if(schedulingTestBean.getBookingAttempt()==1)
				{
					
				}
				else if(schedulingTestBean.getBookingAttempt()==2)
				{
					
				}
				*/
			}
		}
		catch (Exception e)
		{
			throw e;
		}
		
		return regOutput;
	}
	
	public int getSeatAvaibilityForBatch(int bathPK) throws Exception
	{
		return  schedulingTestDao.getSeatAvaibilityForBatch(bathPK);
	}
	
	public String getDownloadDetails(String activityName) throws Exception{
		return schedulingTestDao.getDownloadDetails(activityName);
	}
	
	public EnrollmentDetailsBean getEnrollmentDetailsBean(Users loggedInUser,int batchAttempt) throws Exception
	{
		return schedulingTestDao.getEnrollmentDetailsBean(loggedInUser,batchAttempt);
	}

	public HallTicketBean getHallTicketDetailsBean(Users loggedInUser,int batchAttempt) throws Exception{
		return schedulingTestDao.getHallTicketDetailsBean(loggedInUser,batchAttempt);
	}

	public HallTicketBean getHallTicketDetailsBeanBasedonUserId(String userId,int batchAttempt) throws Exception{
		return schedulingTestDao.getHallTicketDetailsBeanBasedonUserId(userId,batchAttempt);
	}


	@Override
	public SchedulingTestBean getSchedulingTestBean(Users loggedInUser,
			int batchAttempt, String enrollmentPK)  throws Exception{
		return schedulingTestDao.getSchedulingTestBean(loggedInUser,batchAttempt,enrollmentPK);
	}
	
	public String getEnrollmentDetails(Long userFk) throws Exception{
		String enrollmentPk = null;
		try {
			enrollmentPk = schedulingTestDao.getEnrollmentDetails(userFk);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return enrollmentPk;
	}
	
}
