package com.nseit.otbs.action;
import static com.nseit.generic.util.GenericConstants.DESTINATION_URL;
import static com.nseit.generic.util.GenericConstants.GLOBAL_PLAIN_TEXT_MESSAGE;
import static com.nseit.generic.util.GenericConstants.REDIRECT_ACTION_URL;
import static com.nseit.generic.util.GenericConstants.SESSION_USER;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;

import org.apache.logging.log4j.Logger;
import org.apache.struts2.dispatcher.HttpParameters;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import com.nseit.generic.action.BaseAction;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.HallTicketBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.CandidateService;
import com.nseit.generic.service.CommonService;
import com.nseit.generic.service.ReportService;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.DESEncrypter;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.aware.UserAware;
import com.nseit.generic.util.emailsms.EmailSMSUtil;
import com.nseit.otbs.model.BatchDetailsBean;
import com.nseit.otbs.model.CommonBean;
import com.nseit.otbs.model.EnrollmentDetailsBean;
import com.nseit.otbs.model.SchedulingTestBean;
import com.nseit.otbs.model.TestMasterBean;
import com.nseit.otbs.service.SchedulingTestService;
import com.opensymphony.xwork2.ModelDriven;



@SuppressWarnings("serial")
public class SchedulingTestAction extends BaseAction implements ModelDriven<SchedulingTestBean>, UserAware {
	
	private SchedulingTestBean schedulingTestBean = new SchedulingTestBean();
	private SchedulingTestService schedulingTestService ;
	private CommonService commonService;
	private CandidateService candidateService;
	
    private ReportService reportService; 
	private HashMap parameterValues;
	private JREmptyDataSource jremptyDataSource;
	private Logger logger = LoggerHome.getLogger(getClass());

	public SchedulingTestAction() {
		//logger.info("SchedulingTestAction constructor is calling");
		displayMenus();
	}
	
	@Override
	public void resetModel() {
		 schedulingTestBean = new SchedulingTestBean();
		
	}

	@Override
	public SchedulingTestBean getModel() {
		return schedulingTestBean;
	}

	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	@Override
	public void setLoggedInUser(Users users) {
		this.loggedInUser = users;
		
	}
	
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

    public HashMap getParameterValues() {
		return parameterValues;
	}

	public JREmptyDataSource getJremptyDataSource() {
		return jremptyDataSource;
	}

	public String  getCityForTestCenters() throws Throwable {	
		//logger.info("getCityForTestCenters()");
		String result=null;
		 //SchedulingTestBean schedulingTestBean = null;
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Map<Integer,List< Long>> dateWindowMap = ConfigurationConstants.getInstance().getDateWindowMap();
		List<Long> dateList = dateWindowMap.get(3);
		Long startDate =dateList.get(0);
		Long endDate = dateList.get(1);
		String enrollmentId = null;
		
		long today = CommonUtil.getDateOnly().getTime();
		
		
		if ((today==startDate || today>startDate) && (today==endDate  || today<endDate )){
			TestMasterBean testmasterBeanfromCache = null;
			Map<Integer,TestMasterBean>  testDetailsMapFromCache= null;
			String currentMenuKey= request.getAttribute("menuKey").toString();
			
			 try {
				 /*if(Integer.parseInt(loggedInUser.getStage())==ConfigurationConstants.getInstance().getStageKey(GenericConstants.STAGE_FIRST_ATTEMPT_SEAT_BOOKED))
				 {
					 schedulingTestBean.setBookingAttempt(2);
				 }*/

				 enrollmentId = schedulingTestService.getEnrollmentDetails(loggedInUser.getUserFk());
				 if(enrollmentId!=null &&!enrollmentId.equals("")){
					 schedulingTestBean.setBookingAttempt(2);
					 if(currentMenuKey!=null){
						 request.setAttribute("menuKey", currentMenuKey.split("\\.")[0].concat(".2"));
					 }
				 }else {
					 schedulingTestBean.setBookingAttempt(1);
				}
				 loggedInUser.setTempStage(currentMenuKey);
				 
			/*	 if (loggedInUser.getCandidateStatusId()>=(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.ATTEMPT_ONE_BOOKED))){
					 result = "showAttemptTwoPage";
				 }else {*/
					/*if (currentMenuKey!=null && !currentMenuKey.equals("")){
						 String[] strArr = currentMenuKey.split("\\.");
						 
						 if (strArr!=null){
							 schedulingTestBean.setBookingAttempt(Integer.parseInt(strArr[1]));
						 }
					 }*/
					 
					//schedulingTestBean.setBookingAttempt(1);
					testDetailsMapFromCache = ConfigurationConstants.getInstance().getTestDetailsMap();
					testmasterBeanfromCache =testDetailsMapFromCache.get(Integer.parseInt(loggedInUser.getDisciplineID()));
					
					if (schedulingTestService.getCityForTestCenters()!=null){
						schedulingTestBean.setCityList(schedulingTestService.getCityForTestCenters());
					}
					schedulingTestBean.setCandidateName(loggedInUser.getFirstName());
					schedulingTestBean.setDiscipline(testmasterBeanfromCache.getTestName());
					schedulingTestBean.getBookingAttempt();
					schedulingTestBean.setUserId(loggedInUser.getUsername());
					result = "success";
			//	}
			//	 }
			} catch (Exception e) {
				  LoggerHome.getLogger(getClass()).fatal(e.getMessage(), e);
			}
		}
		else{
			schedulingTestBean.setOtbsStartDt(CommonUtil.formatDate(new Date(startDate), GenericConstants.DATE_FORMAT_DEFAULT));
			schedulingTestBean.setOtbsEndDt(CommonUtil.formatDate(new Date(endDate), GenericConstants.DATE_FORMAT_DEFAULT));
			schedulingTestBean.setOtbsDisplayFlag("true");
			result = "CloseWindow";
		}
		return result;
	}
	public String  showAttemptTwoPage() throws Throwable {	
		//logger.info("showAttemptTwoPage()");
		String result=null;
		 //SchedulingTestBean schedulingTestBean = null;
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Map<Integer,List< Long>> dateWindowMap = ConfigurationConstants.getInstance().getDateWindowMap();
		List<Long> dateList = dateWindowMap.get(3);
		Long startDate =dateList.get(0);
		Long endDate = dateList.get(1);
		
		long today = CommonUtil.getDateOnly().getTime();
		
		if ((today==startDate || today>startDate) && (today==endDate  || today<endDate )){
			TestMasterBean testmasterBeanfromCache = null;
			Map<Integer,TestMasterBean>  testDetailsMapFromCache= null;
			String currentMenuKey= request.getAttribute("menuKey").toString();
			//String currentMenuKey= loggedInUser.getStatus();
			 loggedInUser.setTempStage(currentMenuKey);
				 /*if(Integer.parseInt(loggedInUser.getStage())==ConfigurationConstants.getInstance().getStageKey(GenericConstants.STAGE_FIRST_ATTEMPT_SEAT_BOOKED))
				 {
					 schedulingTestBean.setBookingAttempt(2);
				 }*/
				 /*if(currentMenuKey!=null && currentMenuKey.equals(loggedInUser.getStage())){
					 schedulingTestBean.setBookingAttempt(2);
				 }*/
				 
				if(loggedInUser.getCandidateStatusId()>=(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.ATTEMPT_TWO_BOOKED))){
					showOtbsSuccessPage();
					 result = "successPage";
				}else {
					 /*if (currentMenuKey!=null && !currentMenuKey.equals("")){
						 String[] strArr = currentMenuKey.split("\\.");
						 
						 if (strArr!=null){
							 schedulingTestBean.setBookingAttempt(Integer.parseInt(strArr[1]));
						 }
					 }*/
					schedulingTestBean.setBookingAttempt(2);
					testDetailsMapFromCache = ConfigurationConstants.getInstance().getTestDetailsMap();
					testmasterBeanfromCache =testDetailsMapFromCache.get(Integer.parseInt(loggedInUser.getDisciplineID()));
				
					schedulingTestBean.setCityList(schedulingTestService.getCityForTestCenters());
					schedulingTestBean.setCandidateName(loggedInUser.getFirstName());
					schedulingTestBean.setDiscipline(testmasterBeanfromCache.getTestName());
					schedulingTestBean.getBookingAttempt();
					schedulingTestBean.setUserId(loggedInUser.getUsername());
					result = "success";
				}
		}else{
			schedulingTestBean.setOtbsStartDt(CommonUtil.formatDate(new Date(startDate), GenericConstants.DATE_FORMAT_DEFAULT));
			schedulingTestBean.setOtbsEndDt(CommonUtil.formatDate(new Date(endDate), GenericConstants.DATE_FORMAT_DEFAULT));
			schedulingTestBean.setOtbsDisplayFlag("true");
			result = "CloseWindow";
		}
		return result;
	}

	public String getTestCentreForCity() throws Throwable {
		//logger.info("getTestCentreForCity()");
		List<CommonBean> tstCenterList =  null;
		String responseText= "";
		String currentMenuKey= request.getParameter("menuKey");
		 try {
			 /*if(Integer.parseInt(loggedInUser.getStage())==ConfigurationConstants.getInstance().getStageKey(GenericConstants.STAGE_FIRST_ATTEMPT_SEAT_BOOKED))
			 {
				 schedulingTestBean.setBookingAttempt(2);
			 }*/
			 if(currentMenuKey!=null && currentMenuKey.equals(loggedInUser.getStage())){
				 schedulingTestBean.setBookingAttempt(2);
			 }
			tstCenterList =  schedulingTestService.getTestCenterBasedOnCity(schedulingTestBean);
			
		if(tstCenterList != null && !tstCenterList.isEmpty() ){
			for(CommonBean commonBean : tstCenterList){
				responseText += (commonBean.getLabelId() + "$$" + commonBean.getLabelValue()+ "##");
			}
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, responseText.substring(0, responseText.length()-2));
		}
		 getCityForTestCenters();
		} catch (Exception e) {
			  LoggerHome.getLogger(getClass()).fatal(e.getMessage(), e);
		}
		return "writePlainText";
	}
	
	
	public String getTestCenterAddress(){
		//logger.info("getTestCenterAddress()");
		String responseText= "";
		List<String> tstDateList = null;
		String tstCenterAddress=null;
		String currentMenuKey= request.getParameter("menuKey");
		 try {
			 /*if(Integer.parseInt(loggedInUser.getStage())==ConfigurationConstants.getInstance().getStageKey(GenericConstants.STAGE_FIRST_ATTEMPT_SEAT_BOOKED))
			 {
				 schedulingTestBean.setBookingAttempt(2);
			 }*/
			 if(loggedInUser.getCandidateStatusId()>=ConfigurationConstants.getInstance().getStatusKey(GenericConstants.ATTEMPT_ONE_BOOKED)){
				 schedulingTestBean.setBookingAttempt(2);
			 }else{
				 schedulingTestBean.setBookingAttempt(1); 
			 }
			tstCenterAddress = schedulingTestService.getTestCenterAddress(schedulingTestBean.getCmbTstCenter());
			schedulingTestBean.setTstCenterAddress(tstCenterAddress);
			responseText = tstCenterAddress +"##";
			tstDateList=schedulingTestService.getTestDatesForTstCenter(schedulingTestBean,loggedInUser);
			for(String date : tstDateList ){
				responseText = responseText+","+date ;
			}
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, responseText);
		} catch (Exception e) {
			  LoggerHome.getLogger(getClass()).fatal(e.getMessage(), e);
		}
		return "writePlainText";
	}
	
	public String getBatchDetailsToBookSeat(){
		//logger.info("getBatchDetailsToBookSeat()");
		String responseText = "";
		List<BatchDetailsBean> lstBatcDeaBatchDetailsBeans = null;
		TestMasterBean testmasterBeanfromCache = null;
		Map<Integer,TestMasterBean>  testDetailsMapFromCache= null;
		String currentMenuKey= request.getParameter("menuKey");
		 try {
			 /*if(Integer.parseInt(loggedInUser.getStage())==ConfigurationConstants.getInstance().getStageKey(GenericConstants.STAGE_FIRST_ATTEMPT_SEAT_BOOKED))
			 {
				 schedulingTestBean.setBookingAttempt(2);
			 }*/
			 if(currentMenuKey!=null && currentMenuKey.equals(loggedInUser.getStage())){
				 schedulingTestBean.setBookingAttempt(2);
			 }
			schedulingTestBean.setBatchDisplayFlag("Y");
			lstBatcDeaBatchDetailsBeans = schedulingTestService.getBatchDetails(schedulingTestBean,loggedInUser);

			 testDetailsMapFromCache = ConfigurationConstants.getInstance().getTestDetailsMap();
			 testmasterBeanfromCache =testDetailsMapFromCache.get(Integer.parseInt(loggedInUser.getDisciplineID()));
		
			if(lstBatcDeaBatchDetailsBeans!= null && !lstBatcDeaBatchDetailsBeans.isEmpty()){
				//schedulingTestBean.setLstBatchDetails(lstBatcDeaBatchDetailsBeans);
				for(BatchDetailsBean tmpBatchDetails : lstBatcDeaBatchDetailsBeans){
					if(tmpBatchDetails.getTestDate() != null){
					responseText = responseText +tmpBatchDetails.getBatchPK()+"," +tmpBatchDetails.getTestDate()+","+
									tmpBatchDetails.getTestDay()+","+tmpBatchDetails.getTestStartTime()+","+testmasterBeanfromCache.getTestDuration()+","+tmpBatchDetails.getAvailableSeats()+","+tmpBatchDetails.getReportingTime()+"##";
					}
				}
				//result="showBatchDetails";
				if("".equals(responseText)){
					responseText = "empty";
				}
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, responseText);
			}
		}catch (Exception e) {
			  LoggerHome.getLogger(getClass()).fatal(e.getMessage(), e);
		}
		return "writePlainText";
	}
	public String isSeatsAvailableForBooking(){
		//logger.info("isSeatsAvailableForBooking()");
		try{
		int availableSeats =0;
		String responseText = "";
		 if(Integer.parseInt(loggedInUser.getStage())==ConfigurationConstants.getInstance().getStageKey(GenericConstants.STAGE_FIRST_ATTEMPT_SEAT_BOOKED)){
			 schedulingTestBean.setBookingAttempt(2);
		 }
		availableSeats=schedulingTestService.getSeatAvaibilityForBatch(schedulingTestBean.getBatchPK());
		if(availableSeats>0){
			responseText = "true";
		}else{
			responseText="false";
		}
		request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, responseText);
		}catch (Exception e) {
			  LoggerHome.getLogger(getClass()).fatal(e.getMessage(), e);
		}
		return "writePlainText";
	}
	
	
	
	public String updateSeatDetailsOfBatch(){
		//logger.info("updateSeatDetailsOfBatch()");
		int stageUpdate = 0;
		String responseText = "";
		String currentMenuKey= request.getParameter("menuKey");
		String enrollmentId = null;
		 try {
			 /*if(Integer.parseInt(loggedInUser.getStage())==ConfigurationConstants.getInstance().getStageKey(GenericConstants.STAGE_FIRST_ATTEMPT_SEAT_BOOKED))
			 {
				 schedulingTestBean.setBookingAttempt(2);
			 }*/
			 

	/*		 if (currentMenuKey!=null && !currentMenuKey.equals("")){
				 strArr  = currentMenuKey.split("\\.");
				 
				 if (strArr!=null){
					 schedulingTestBean.setBookingAttempt(Integer.parseInt(strArr[1]));
				 }
			 }*/
			 
			 enrollmentId = schedulingTestService.getEnrollmentDetails(loggedInUser.getUserFk());
			 
			 if (enrollmentId!=null && !enrollmentId.equals("")){
				 schedulingTestBean.setBookingAttempt(2);
			 }else{
				 schedulingTestBean.setBookingAttempt(1);
			 }
			 
		responseText = schedulingTestService.blockSeatForCandidate(schedulingTestBean,loggedInUser);
		if (responseText.equals("Confirmed")){
			stageUpdate = commonService.updateCandidateStage(loggedInUser,currentMenuKey);
			if (stageUpdate>0){
				loggedInUser.setStage(currentMenuKey);
			}
			if (schedulingTestBean.getBookingAttempt()==1){
				if (loggedInUser.getStatusID()< ConfigurationConstants.getInstance().getStatusKey(GenericConstants.ATTEMPT_ONE_BOOKED)){
					int updateStage = commonService.updateCandidateStatus(loggedInUser, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.ATTEMPT_ONE_BOOKED));
					if (updateStage>0){
						loggedInUser.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.ATTEMPT_ONE_BOOKED));
						String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.ATTEMPT_ONE_BOOKED)+"";
						Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.ATTEMPT_ONE_BOOKED);
						Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.ATTEMPT_ONE_BOOKED);
						//EmailSMSUtil.insertEmailNSMSForActivitynTestBooking(ConfigurationConstants.getInstance().getActivtyKeyForName(GenericConstants.ATTEMPT_ONE_BOOKED),Integer.parseInt(loggedInUser.getDisciplineID()), loggedInUser.getUsername(), loggedInUser.getUsername(),new Integer(1),staus,loggedInUser,emailReq,smsReq);
						
					}
					
				}
			}else if (schedulingTestBean.getBookingAttempt()==2) {
				if (loggedInUser.getStatusID()< ConfigurationConstants.getInstance().getStatusKey(GenericConstants.ATTEMPT_TWO_BOOKED)){
					int updateStage = commonService.updateCandidateStatus(loggedInUser, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.ATTEMPT_TWO_BOOKED));
					if (updateStage>0){
						loggedInUser.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.ATTEMPT_TWO_BOOKED));
						
						String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.ATTEMPT_TWO_BOOKED)+"";
						Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.ATTEMPT_TWO_BOOKED);
						Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.ATTEMPT_TWO_BOOKED);
						//EmailSMSUtil.insertEmailNSMSForActivitynTestBooking(ConfigurationConstants.getInstance().getActivtyKeyForName(GenericConstants.ATTEMPT_TWO_BOOKED),Integer.parseInt(loggedInUser.getDisciplineID()), loggedInUser.getUsername(), loggedInUser.getUsername(),new Integer(2),staus,loggedInUser,emailReq,smsReq);
						
					}
				}
			}
			sessionAttributes.put(SESSION_USER, loggedInUser);
			ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger().info(loggedInUser.getUsername()+", "+ request.getRemoteAddr() +", Seat Booking for Attempt "+schedulingTestBean.getBookingAttempt() +" , "+CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss") );
			
		}

		 }catch (Exception e) {
			if("Confirm".equalsIgnoreCase(schedulingTestBean.getSeatStatus())){
				responseText = "Error";
			}
			  LoggerHome.getLogger(getClass()).fatal(e.getMessage(), e);
		}
		request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, responseText);
		return "writePlainText";
	}
	
	public String showOtbsSuccessPage() throws Throwable {
		
		//logger.info("showOtbsSuccessPage()");
		//String result = "ShowHallTicket";
		String result = "successPage";
		String currentMenuKey2 = null;
		if (request.getAttribute("menuKey")!=null && !request.getAttribute("menuKey").equals("")){
			currentMenuKey2 = request.getAttribute("menuKey").toString();
		}
		try {
			 if (currentMenuKey2!=null && !currentMenuKey2.equals("")){
				 String[] strArr = currentMenuKey2.split("\\.");
				 
				 if (strArr!=null){
					 schedulingTestBean.setBookingAttempt(Integer.parseInt(strArr[1]));
				 }
			 }
			/*
			EnrollmentDetailsBean enrllmntBean = null;
			if (loggedInUser.getDateOfBirth()!=null && !loggedInUser.getDateOfBirth().equals("")){
				schedulingTestBean.setDateOfBirth(CommonUtil.getStringDateStrForAdmin(loggedInUser.getDateOfBirth()));
			}
		//	schedulingTestBean.getBatchDisplayFlag();
			
			if (currentMenuKey!=null && !currentMenuKey.equals("")){
				if (currentMenuKey.equals(loggedInUser.getStage())){
					schedulingTestBean.setBookingAttempt(1);
				}else{
					schedulingTestBean.setBookingAttempt(1);
				}
			}
			
			
			 if(Integer.parseInt(loggedInUser.getStage())==ConfigurationConstants.getInstance().getStageKey(GenericConstants.STAGE_FIRST_ATTEMPT_SEAT_BOOKED))
			 {
				 schedulingTestBean.setBookingAttempt(1);
			 }
			
			 if (currentMenuKey!=null && !currentMenuKey.equals("")){
				 String[] strArr = currentMenuKey.split("\\.");
				 
				 if (strArr!=null){
					 schedulingTestBean.setBookingAttempt(Integer.parseInt(strArr[1]));
				 }
			 }
			 
			enrllmntBean =  schedulingTestService.getEnrollmentDetailsBean(loggedInUser,  schedulingTestBean.getBookingAttempt());
			schedulingTestBean.setUserFK(loggedInUser.getUserId());
			schedulingTestBean.setUserId(loggedInUser.getUsername());
			schedulingTestBean.setEnrollmenBean(enrllmntBean);
			String password = DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).decrypt(loggedInUser.getPassword());
			schedulingTestBean.setPassword(password);
			
			//OutputStream outputStream = response.getOutputStream();
//        	outputStream.write(barCodeImageString);
//        	outputStream.flush();
			
			//preparePDFForHallTicket();
			
			// code for email sms
			if (enrllmntBean.getBatchAttempt() == 1){
				String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.ATTEMPT_ONE_BOOKED)+"";
				Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.ATTEMPT_ONE_BOOKED);
				Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.ATTEMPT_ONE_BOOKED);
				EmailSMSUtil.insertEmailNSMSForActivitynTestBooking(ConfigurationConstants.getInstance().getActivtyKeyForName(GenericConstants.SEAT_BOOKING_1), 
				       Integer.parseInt(loggedInUser.getDisciplineID()), loggedInUser.getUsername(), loggedInUser.getUsername(),enrllmntBean.getBatchAttempt(),staus,loggedInUser,emailReq,smsReq);
			}
			if (enrllmntBean.getBatchAttempt() == 2){
				String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.ATTEMPT_TWO_BOOKED)+"";
				Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.ATTEMPT_TWO_BOOKED);
				Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.ATTEMPT_TWO_BOOKED);
				EmailSMSUtil.insertEmailNSMSForActivitynTestBooking(ConfigurationConstants.getInstance().getActivtyKeyForName(GenericConstants.SEAT_BOOKING_2), 
						Integer.parseInt(loggedInUser.getDisciplineID()), loggedInUser.getUsername(), loggedInUser.getUsername(),enrllmntBean.getBatchAttempt(),staus,loggedInUser,emailReq,smsReq);
			}
		*/} catch (Exception e) {
			  LoggerHome.getLogger(getClass()).fatal(e.getMessage(), e);
		}
		
		return result;
	}
	
	public String printHallTicketJasper() throws Throwable {
		//logger.info("printHallTicketJasper()");
		String result = "PrintHallTicket";
		EnrollmentDetailsBean enrllmntBean = null;
		if(loggedInUser.getDateOfBirth() != null){
			schedulingTestBean.setDateOfBirth(CommonUtil.getStringDateStrForAdmin(loggedInUser.getDateOfBirth()));
		}
		schedulingTestBean.getBatchDisplayFlag();
		/*if(loggedInUser.getCandidateStatusId() == ConfigurationConstants.getInstance().getStatusKey(GenericConstants.ATTEMPT_ONE_BOOKED))
		{
		 //if(Integer.parseInt(loggedInUser.getStage())==ConfigurationConstants.getInstance().getStageKey(GenericConstants.STAGE_FIRST_ATTEMPT_SEAT_BOOKED)){
			 schedulingTestBean.setBookingAttempt(1);
		}
		else
		{
			schedulingTestBean.setBookingAttempt(2);
		}*/
		 
		 ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger().info(loggedInUser.getUsername()+", "+ request.getRemoteAddr() +", Print Hall-ticket for Attempt "+schedulingTestBean.getBookingAttempt() + " , "+CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss") );
		
		 enrllmntBean =  schedulingTestService.getEnrollmentDetailsBean(loggedInUser,  schedulingTestBean.getBookingAttempt());
		 schedulingTestBean.setUserFK(loggedInUser.getUserId());
		
		 schedulingTestBean.setUserId(loggedInUser.getUsername());
		 schedulingTestBean.setEnrollmenBean(enrllmntBean);
		 schedulingTestBean.setPassword(loggedInUser.getPassword());
		 
		 result = preparePDFForHallTicket();
		 
		 return result;
		
	}
	 	
	public String printHallTicketJasperFromHelpCenter() throws Throwable {
		//logger.info("printHallTicketJasperFromHelpCenter()");
		String result = "PrintHallTicket";
		/*EnrollmentDetailsBean enrllmntBean = null;
		schedulingTestBean.getBatchDisplayFlag();
		schedulingTestBean.getBatchPK();
		 if(Integer.parseInt(loggedInUser.getStage())==ConfigurationConstants.getInstance().getStageKey(GenericConstants.STAGE_FIRST_ATTEMPT_SEAT_BOOKED))
		 {
			 schedulingTestBean.setBookingAttempt(1);
		 }
		enrllmntBean =  schedulingTestService.getEnrollmentDetailsBean(loggedInUser,  schedulingTestBean.getBookingAttempt());
		schedulingTestBean.setUserFK(loggedInUser.getUserId());
		
		schedulingTestBean.setUserId(loggedInUser.getUsername());
		schedulingTestBean.setEnrollmenBean(enrllmntBean);
		schedulingTestBean.setPassword(loggedInUser.getPassword());*/
		result = preparePDFForHallTicket();
		return result;
		
	}
	public void setSchedulingTestService(SchedulingTestService schedulingTestService) {
		this.schedulingTestService = schedulingTestService;
	}

	 public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	private String preparePDFForHallTicket() throws Exception {
		//logger.info("preparePDFForHallTicket()");
		String result = null;
		parameterValues = new HashMap();
		//java.awt.Image awtImagePhoto = null;
		InputStream inputStreamForImage = null;
		InputStream inputStreamSign = null;
		String discipline = null;
		String enrollmentPK = null;
		String candidateName = null;
		String testCenter = null;
		
		String testCenterAddress = null;
		String testDate = null;
		String reportingTime = null;
		String testTime = null;
		
		String rollNo = null;
		try {
			
			String attemptType = schedulingTestBean.getBookingAttempt() == 1 ? "I"
					: "II";
			
			int attempt = schedulingTestBean.getBookingAttempt() == 1 ? 1 : 2;
			getHallTicketData(attempt);

			enrollmentPK = schedulingTestBean.getHallTicketBean().getEnrollmetPk();
			if("C".equalsIgnoreCase(loggedInUser.getUserType()))
			{
				/*awtImagePhoto = ImageIO.read(candidateService
						.getCandidateImage(loggedInUser.getUserId())
						.getInputStreamForImage());*/
				
				// START
				CandidateBean candidateBeanphoto = candidateService
						.getCandidateImage(loggedInUser.getUserId());

				if (candidateBeanphoto != null) {
					inputStreamForImage = candidateBeanphoto.getInputStreamForImage();
				}
				

				CandidateBean candidateBeanSignature = candidateService
						.getCandidateSignature(loggedInUser.getUserId());

				
				if (candidateBeanSignature != null) {
					inputStreamSign = candidateBeanSignature
							.getInputStreamForImage();
				}
				
			}
			else
			{
				
				CandidateBean candidateBeanphoto = candidateService
						.getCandidateImage(schedulingTestBean.getUserFK());

				if (candidateBeanphoto != null) {
					inputStreamForImage = candidateBeanphoto
							.getInputStreamForImage();
				}
				
				CandidateBean candidateBeanSignature = candidateService
						.getCandidateSignature(schedulingTestBean.getUserFK());

				if (candidateBeanSignature != null) {
					inputStreamSign = candidateBeanSignature
							.getInputStreamForImage();
				}
				
				parameterValues.put("USER_ID", schedulingTestBean.getHallTicketBean().getUserId());
			}
				java.awt.Image companyLogo = ImageIO.read(new FileInputStream(
						"nseit-NCFE-logo.png"));
				
				if("C".equalsIgnoreCase(loggedInUser.getUserType()))
				{
					parameterValues.put("USER_ID", loggedInUser.getUsername());
				}
				

//				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//				Code128 barcode = new Code128();
//				barcode.setData(enrollmentPK);								
//				barcode.drawBarcode(byteArrayOutputStream);
//				byte [] barCodeImageString = byteArrayOutputStream.toByteArray();
				
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				BitmapCanvasProvider provider = new BitmapCanvasProvider(
						byteArrayOutputStream, "image/x-png", 300, BufferedImage.TYPE_BYTE_GRAY, true, 0);
				
				
				org.krysalis.barcode4j.impl.code128.Code128 code128 = new org.krysalis.barcode4j.impl.code128.Code128();			
				code128.generateBarcode(provider, enrollmentPK);
				
				provider.finish();
				byte [] barCodeImageString = byteArrayOutputStream.toByteArray();
				
				schedulingTestBean.setBarCodeImage(barCodeImageString);
				
				java.awt.Image barcodeImage = ImageIO.read(new ByteArrayInputStream(barCodeImageString));
		

				parameterValues.put("ATTEMPT_TYPE", attemptType);
				parameterValues.put("GCI_PHOTO_IMAGE", inputStreamForImage);
				parameterValues.put("OCI_SIGNATURE_IMAGE", inputStreamSign);
				parameterValues.put("GCET_LOGO_IMAGE", companyLogo);
				
				parameterValues.put("BARCODE_IMAGE", barcodeImage);
				
				
				if("dashboard".equalsIgnoreCase(schedulingTestBean.getAction()))
				{
					SchedulingTestBean schedulingTestBeanNew = schedulingTestService.getSchedulingTestBean(loggedInUser, schedulingTestBean.getBookingAttempt(), enrollmentPK);
					discipline = schedulingTestBeanNew.getDiscipline();
					candidateName = schedulingTestBeanNew.getCandidateName();
					testCenter = schedulingTestBeanNew.getTestCenter();
					testCenterAddress = schedulingTestBeanNew.getTstCenterAddress();
					testDate = schedulingTestBeanNew.getTestDate();
					
					reportingTime = schedulingTestBeanNew.getReportingTime();
					testTime = schedulingTestBeanNew.getTestTime();
					String password = DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).decrypt(loggedInUser.getPassword());
					parameterValues.put("PASSWORD", password);
					
					rollNo = schedulingTestBean.getRollNumberForHallTicket();
				}
				else
				{
					
						discipline = schedulingTestBean.getHallTicketBean().getDisciplineType();
						
						candidateName = schedulingTestBean.getHallTicketBean().getUserName();
						testCenter = schedulingTestBean.getHallTicketBean().getTestCenterName();
						testCenterAddress = schedulingTestBean.getHallTicketBean().getTestCenterAddress();
						testDate = schedulingTestBean.getHallTicketBean().getTestDate();
						reportingTime = schedulingTestBean.getHallTicketBean().getReportingTime();
						testTime = schedulingTestBean.getHallTicketBean().getTestStartTime();
						
						parameterValues.put("PASSWORD", schedulingTestBean.getHallTicketBean().getUserPassword());
						
						rollNo = schedulingTestBean.getHallTicketBean().getRollNumber();
				}
				
				parameterValues.put("DISCIPLINE", discipline);
				parameterValues.put("ENROLLMENT_ID", enrollmentPK);
				parameterValues.put("NAME", candidateName);
				parameterValues.put("TEST_CENTRE_NAME", testCenter);
				parameterValues.put("TEST_CENTRE_ADDRESS", testCenterAddress);
				parameterValues.put("TEST_DATE", testDate);
				parameterValues.put("REPORTING_TIME", reportingTime);
				parameterValues.put("EXAM_START_TIME", testTime);
				
				parameterValues.put("ROLL_NO", rollNo);
				
				String isRollNoGenerated = ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.GENERATE_ROLL_NUMBER);
				
				if("A".equals(isRollNoGenerated))
				{
					parameterValues.put("isFieldHidden", true);
				}
				else
				{
					parameterValues.put("isFieldHidden", false);
				}
				

				result ="PrintHallTicket";
		} catch (Exception e) {
			  LoggerHome.getLogger(getClass()).fatal(e.getMessage(), e);
		}
		return result;
	}

	 
	 /*public String downloadSettingDetailsForTraining()
	    {
	    	String result = null;
	        try
	        {
	            String module = schedulingTestBean.getPathName();

	            File file = new File("");
	            String path = file.getAbsolutePath();
	            String downloadPath = null;

	            if (module != null)
	            {
	                downloadPath = schedulingTestService.getDownloadDetails(module);
	            }

	            File fileToDownload = new File(path + downloadPath);

	            String actualFileName = "";
	            File[] listOfFiles = fileToDownload.listFiles();

	            if (listOfFiles != null)
	            {
	                for (int i = 0; i < listOfFiles.length; i++)
	                {
	                    if (listOfFiles[i].getName().startsWith(loggedInUser.getUsername()))
	                    {
	                    	actualFileName = listOfFiles[i].toString();
	                    	break;
	                    }
	                }
	            }
	           
	            
	            if (actualFileName!=null && !actualFileName.equals("")){
	            	String downloadPath2 = path + downloadPath + "\\" + actualFileName;
	                File fileToDownload2 = new File(actualFileName);
	                
	                String extn = fileToDownload2.getName();

	                int pos = extn.lastIndexOf('.');
	                 String ext = extn.substring(pos+1);
	                
	                response.setContentType(ext);
	                
	                String disHeader = "Attachment; Filename="+extn;
	                response.setHeader("Content-Disposition", disHeader);
	                
	                InputStream in = null;
	                ServletOutputStream outs = response.getOutputStream();

	                in = new BufferedInputStream(new FileInputStream(fileToDownload2));
	                int ch;

	                while ((ch = in.read()) != -1)
	                {
	                    outs.print((char) ch);
	                }

	                outs.flush();
	                outs.close();
	                in.close();
	                
	                result = SUCCESS;
	            }else{
	            	request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Sorry, No Downloads Available");
	                request.setAttribute(GenericConstants.MESSAGE_TYPE,GenericConstants.MESSAGE_TYPE_ERROR);
	                request.setAttribute(GenericConstants.DESTINATION_PATH,"CandidateAction_home.action");
	                result = "displayMessage";
	            }
	            
	            
	            
	        }
	        catch (Exception e)
	        {
	              LoggerHome.getLogger(getClass()).fatal(e.getMessage(), e);
	        }
	        return result;

	    }*/
	 
	 /*public String downloadJapserPDF(String downloadPath, String startsWith)
	    {
	    	String result = null;
	    	try
	        {
	            File file = new File("");
	            String path = file.getAbsolutePath();
	            File fileToDownload = new File(downloadPath);

	            String actualFileName = "";
	            File[] listOfFiles = fileToDownload.listFiles();

	            if (listOfFiles != null)
	            {
	                for (int i = 0; i < listOfFiles.length; i++)
	                {
	                	
	                	
	                    if (listOfFiles[i].getName().startsWith(startsWith))
	                    {
	                    	actualFileName = listOfFiles[i].toString();
	                    	break;
	                    }
	                }
	            }
	           
	            
	            if (actualFileName!=null && !actualFileName.equals("")){
	            	String downloadPath2 = downloadPath + "\\" + actualFileName;
	                File fileToDownload2 = new File(actualFileName);
	                
	                String extn = fileToDownload2.getName();

	                int pos = extn.lastIndexOf('.');
	                 String ext = extn.substring(pos+1);
	                
	                response.setContentType(ext);
	                
	                String disHeader = "Attachment; Filename="+extn;
	                response.setHeader("Content-Disposition", disHeader);
	                
	                InputStream in = null;
	                ServletOutputStream outs = response.getOutputStream();

	                in = new BufferedInputStream(new FileInputStream(fileToDownload2));
	                int ch;

	                while ((ch = in.read()) != -1)
	                {
	                    outs.print((char) ch);
	                }

	                outs.flush();
	                outs.close();
	                in.close();
	                
	                result = "SUCCESS";
	            }else{
	            	request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Sorry, No Downloads Available");
	                request.setAttribute(GenericConstants.MESSAGE_TYPE,GenericConstants.MESSAGE_TYPE_ERROR);
	                request.setAttribute(GenericConstants.DESTINATION_PATH,"CandidateAction_home.action");
	                result = "displayMessage";
	            }
	        }
	        catch (Exception e)
	        {
	              LoggerHome.getLogger(getClass()).fatal(e.getMessage(), e);
	        }
	        return result;

	    }*/
	
	public String goForSecondAttemptBokking() throws Exception {
		//logger.info("goForSecondAttemptBokking()");
		String result = REDIRECT_ACTION_URL;
		Users users = (Users) sessionAttributes.get(SESSION_USER);
		String actionUrl = null;
		try {
			String currentMenuKey = request.getParameter("menuKey");
			String nextMenuKey = ConfigurationConstants.getInstance().getNextStageForMenuKey(currentMenuKey);
			actionUrl = ConfigurationConstants.getInstance().getActionUrl(nextMenuKey);
			users.setNextStage(nextMenuKey);
			sessionAttributes.put(SESSION_USER, users);
			request.setAttribute(DESTINATION_URL,actionUrl);
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return result;
	}

	public String getHallTicketData(int batchAttempt) {
		//logger.info("getHallTicketData()");
		String result = null;
		HallTicketBean hallTicketBean = null;
		
		
		try {
				if("C".equalsIgnoreCase(loggedInUser.getUserType()))
				{
					hallTicketBean = schedulingTestService.getHallTicketDetailsBean(loggedInUser, batchAttempt);
				}
				else
				{
					hallTicketBean = schedulingTestService.getHallTicketDetailsBeanBasedonUserId(schedulingTestBean.getCandidateID(), batchAttempt);
				}
				if (hallTicketBean!=null){
					hallTicketBean.setUserPassword(DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).decrypt(hallTicketBean.getUserPassword()));
					hallTicketBean.setBookingAttempt(batchAttempt);
					schedulingTestBean.setBookingAttempt(batchAttempt);
					hallTicketBean.setUserFK(loggedInUser.getUserFk());
					hallTicketBean.setBookingAttmptStatus("Y");
					schedulingTestBean.setHallTicketBean(hallTicketBean);
					
					String  admitCardstatus = ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.GENERATE_ADMIT_CARD);
					
					if (admitCardstatus!=null && !admitCardstatus.equals("") && !admitCardstatus.equals("D")){
						if (hallTicketBean.getHallticketFlag().equals("0")|| hallTicketBean.getHallticketFlag().equals("")|| hallTicketBean.getHallticketFlag()==null){
							hallTicketBean.setBookingAttmptStatus("D");
							hallTicketBean.setBookingAttempt(batchAttempt);
							schedulingTestBean.setBookingAttempt(batchAttempt);
							schedulingTestBean.setHallTicketBean(hallTicketBean);
						}	
					}
					
					if (ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.GENERATE_ROLL_NUMBER).equals("A")){
						schedulingTestBean.setRollNoDisplayFlag("true");
					}
			}
			
			if (hallTicketBean==null){
				hallTicketBean = new HallTicketBean();
				hallTicketBean.setBookingAttmptStatus("N");
				hallTicketBean.setBookingAttempt(batchAttempt);
				schedulingTestBean.setBookingAttempt(batchAttempt);
				schedulingTestBean.setHallTicketBean(hallTicketBean);
			}
		} catch (Exception e) {
			logger.fatal(e,e);
		}
		return result;
	}
	
	public String showHallticketForAttemptOne() {
		//logger.info("showHallticketForAttemptOne()");
		String result = null;
		String currentMenuKey = null;
		try {
			result = "hallTicket";
			if (request.getAttribute("menuKey")!=null){
			  currentMenuKey = request.getAttribute("menuKey").toString();
			}
			loggedInUser.setTempStage(currentMenuKey);
			getHallTicketData(1);
		} catch (Exception e) {
			logger.fatal(e,e);
		}
		return result;
	}
	
	public String showHallticketForAttemptTwo() {
		//logger.info("showHallticketForAttemptTwo()");
		String result = null;
		String currentMenuKey = null;
		try {
			result = "hallTicket";
			if (request.getAttribute("menuKey")!=null){
				currentMenuKey = request.getAttribute("menuKey").toString();
			}
			loggedInUser.setTempStage(currentMenuKey);
			getHallTicketData(2);
		} catch (Exception e) {
			logger.fatal(e,e);
		}
		return result;
		
	}

	@Override
	public void withSession(Map<String, Object> sessionAttributes) {
		this.sessionAttributes = sessionAttributes;
		
	}

	@Override
	public void withParameters(HttpParameters httpParameters) {
		this.httpParameters = httpParameters;
		
	}

	@Override
	public void withServletRequest(HttpServletRequest request) {
		this.request = request;
		
	}

	@Override
	public void withServletResponse(HttpServletResponse response) {
		this.response = response;
		
	}
	
}
