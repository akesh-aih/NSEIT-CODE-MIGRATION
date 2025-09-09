package com.nseit.generic.action;

import static com.nseit.generic.util.GenericConstants.DESTINATION_URL;
import static com.nseit.generic.util.GenericConstants.GLOBAL_PLAIN_TEXT_MESSAGE;
import static com.nseit.generic.util.GenericConstants.REDIRECT_ACTION_URL;

import java.io.File;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.struts2.dispatcher.HttpParameters;

import com.csvreader.CsvReader;
import com.nseit.generic.models.AgencyBean;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.RegistrationBean;
import com.nseit.generic.models.ReportBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.AgencyService;
import com.nseit.generic.service.CandidateMgmtService;
import com.nseit.generic.service.ReportService;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.ExcelCreator;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.GenericServiceException;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.aware.UserAware;
import com.nseit.generic.util.emailsms.EmailSMSUtil;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRParameter;


public class AgencyAction extends BaseAction implements ModelDriven<AgencyBean>, UserAware
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerHome.getLogger(getClass());
	
	private AgencyService agencyService;
	private CandidateMgmtService candidateMgmtService;
	private AgencyBean agencyBean= new AgencyBean();
	//private Users users = new Users();
	/** The data list. */
    private List<ReportBean> dataList;
    
    /** The parameter values. */
    private HashMap parameterValues;
    
    /** The connection. */
    private Connection connection;

    /** The report service. */
    private ReportService reportService;
    
    /** The jrempty data source. */
    private JREmptyDataSource jremptyDataSource;
    
    private List<CandidateBean> candidateDetailsList;
    
    private Pagination pagination = new Pagination(10, 1);
    
    public AgencyAction() {
    	//logger.info("AgencyAction constructor call ");
		displayMenus();
	}
    
	@Override
	public void setLoggedInUser(Users users)
	{
		loggedInUser = users;
	}
	/**
     * Sets the report service.
     *
     * @param reportService the new report service
     */
    public void setReportService(ReportService reportService)
    {
        this.reportService = reportService;
    }
	/*public String home() throws Exception
	{
		String home = null;
		//home = 
		return "home";
	}*/

	@Override
	public void resetModel() {
		agencyBean = new AgencyBean();
	}

	@Override
	public AgencyBean getModel() {
		return agencyBean;
	}

	public void setAgencyService(AgencyService agencyService) {
		this.agencyService = agencyService;
	}
	
		
	public void setCandidateMgmtService(CandidateMgmtService candidateMgmtService) {
		this.candidateMgmtService = candidateMgmtService;
	}
	
	/**
     * Gets the data list.
     *
     * @return the data list
     */
    public List<ReportBean> getDataList()
    {
        return dataList;
    }

    /**
     * Gets the jrempty data source.
     *
     * @return the jrempty data source
     */
    public JREmptyDataSource getJremptyDataSource()
    {
        return jremptyDataSource;
    }

    /**
     * Gets the parameter values.
     *
     * @return the parameter values
     */
    public HashMap getParameterValues()
    {
        return parameterValues;
    }
    
    /**
     * Gets the connection.
     *
     * @return the connection
     */
    public Connection getConnection()
    {
        return connection;
    }
    
    public List<CandidateBean> getCandidateDetailsList()
    {
        return candidateDetailsList;
    }
    
	public Pagination getPagination() {
		return pagination;
	}

    public void setPagination(Pagination pagination) {
		this.pagination = pagination;
    }
    
    public String getCredentialsPage() {
		logger.info("getCredentialsPage()");		
		Map<Integer, String> discliplineList = new LinkedHashMap<Integer, String>();
		try {
			discliplineList = agencyService.getTestMasterDetails();
			
			if (discliplineList!=null){
				agencyBean.setDiscliplineList(discliplineList);
			}
			
			
		} catch (Exception e) {
			 logger.fatal(e,e);
		}
		
		
		return "successAgency";
	}
	
	
	public String generateLoginCregentials() {
		logger.info("generateLoginCregentials()");
		Integer value = 0;
		
		
		try {
			value = agencyService.generateLoginCregentials(agencyBean, loggedInUser);
			if((long)value == agencyBean.getCandidateCount()){
				
				//agencyBean.setExportExcel(detailList);
				//addActionMessage(value+"  Login credentials generated successfully");
				//addActionMessage("Your request ID for this operation is : " + agencyBean.getRequestID());
				agencyBean.setShowExport("Y");
				agencyBean.setFlag("true");
				Map<Integer, String> discliplineList = new LinkedHashMap<Integer, String>();
				discliplineList = agencyService.getTestMasterDetails();
				
				if (discliplineList!=null){
					agencyBean.setDiscliplineList(discliplineList);
				}
			}
			else{
				//addActionMessage("Error in login credential generation");
			}
			
			
		} catch (Exception e) {
			logger.fatal(e,e);
		}
		
		return "successAgency";
	}
	
	public String getLoginCredentials(){
		logger.info("getLoginCredentials()");		
		//String result = "";
		List<List<String>> detailList = new ArrayList<List<String>>();
		try{
			detailList = agencyService.getLoginCredentials();
			if(detailList!=null && !detailList.isEmpty()){
				agencyBean.setExcelList(detailList);
			}
		}
		catch (Exception e) {
			logger.fatal(e,e);
		}
		
		return "displayLoginData";
	}

	public String generateExcelFile(List<List<String>> detailList) throws Exception{
		logger.info("generateExcelFile(List<List<String>> detailList) "+detailList);		
		try{
			ExcelCreator excelCreator = new ExcelCreator();
			HSSFWorkbook workbook = excelCreator.createWorkbook(detailList);
			response.setHeader("Content-Disposition", "attachment; filename=UserDetails.xls");
			response.setHeader("Cache-control","no-cache, no-store");
			response.setHeader("Pragma","no-cache");
			response.setHeader("Expires","-1");
			ServletOutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();
		}
		catch (Throwable e) {
			logger.fatal(e,e);
		}
		
		
		return "successAgency";
	}
	
	
	public String exportToExcel()throws Exception{
		logger.info("exportToExcel()");		
		List<List<String>> detailList = new ArrayList<List<String>>();
		String result = "";
		try{
			detailList = agencyService.exportToExcel();
			if(detailList.size()>0){
				agencyBean.setExportExcel(detailList);
				result = generateExcelFile(detailList);
			}
			else
				result = "error";
		}
		catch (Exception e) {
			logger.fatal(e,e);
		}
		return result;
	}
	
	
	public String exportAllDataToExcel()throws Exception{
		logger.info("exportAllDataToExcel()");				
		List<List<String>> detailList = new ArrayList<List<String>>();
		String result = "";
		long count = 0;
		try{
			count = agencyService.getCandidateLoginCount();
			if(count>65000){
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Sorry, Total login credentials count is more than 65000.\n" +
						" So Excel file cannot be generated.");
                request.setAttribute(GenericConstants.MESSAGE_TYPE,GenericConstants.MESSAGE_TYPE_ERROR);
                request.setAttribute(GenericConstants.DESTINATION_PATH,"AgencyAction_getLoginCredentials.action");
                result = "displayMessage";
			}
			else{
				detailList = agencyService.exportAllDataToExcel();
				if(detailList.size()>0){
					//agencyBean.setExportExcel(detailList);
					result = generateExcelFile(detailList);
				}
				else
					result = "error";
			}
		}
		catch (Exception e) {
			logger.fatal(e,e);
		}
		
		return result;
	}
	
	
	public String exportDataForRequestID()throws Exception{
		logger.info("exportDataForRequestID()");	
		List<List<String>> detailList = new ArrayList<List<String>>();
		String result = "";
		try{
			detailList = agencyService.exportDataForRequestIDToExcel(agencyBean);
			if(detailList.size()>0){
				//agencyBean.setExportExcel(detailList);
				result = generateExcelFile(detailList);
			}
			else
				result = "error";
		}
		catch (Exception e) {
			logger.fatal(e,e);
		}
		
		return result;
	}
	
/*	public String getGcetLoginHome() {
		logger.info("getGcetLoginHome()");			
		String result = "gcetHome";
		Map<String,Integer>  gcetHomeDataMap = null;
		
		Map<Integer, String> paymentModeTypeMap  = ConfigurationConstants.getInstance().getPaymentMasterMap();
		
		try
		{
			gcetHomeDataMap = 	agencyService.getGcetAdminHomeData();
			
			Map<Integer, String> discliplineList = new LinkedHashMap<Integer, String>();
	        
	        discliplineList = agencyService.getTestMasterDetails();
	            
	        if (discliplineList!=null)
	        {
	                agencyBean.setDiscliplineList(discliplineList);
	        }
	            
	        //START :: Application Form Management 
	        agencyBean.setTotalNoOfCandidate(gcetHomeDataMap.get("TOTAL_NO_OF_CANDIDATES")); // Total Candidates
            agencyBean.setNoOfInitiatedCand(gcetHomeDataMap.get("NO_OF_INITIATED_CANDIDATE")); // Initiated
            agencyBean.setNoOfCandidatesApplicationSubmitted(gcetHomeDataMap.get("NO_OF_CANDIDATE_APPLICATION_SUBMITED"));
            agencyBean.setNoOfApprovedCand(gcetHomeDataMap.get("NO_OF_APPROVED_CANDIDATE"));
            agencyBean.setNoOfRejectedCand(gcetHomeDataMap.get("NO_OF_REJECTED_CANDIDATE"));
            agencyBean.setNoOfPendingCand(gcetHomeDataMap.get("NO_OF_PENDING_CANDIDATE"));
            agencyBean.setCandidateApproveRejectStatus(ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.CANDIDATE_APPROVE_REJECT));
            //END :: Application Form Management
            
            
            //START :: Payment Management
            agencyBean.setNoOfCandPaymentSubmitted(gcetHomeDataMap.get("NO_OF_PAYMENT_SUBMITTED")+""); // Payment Submitted
            agencyBean.setNoOfCandPaymentApprovedForDD(gcetHomeDataMap.get("NO_OF_CANDIDATE_PAYMENT_APPROVED_DD")); // Approved for DD
            agencyBean.setNoOfCandPaymentApprovedForCH(gcetHomeDataMap.get("NO_OF_CANDIDATE_PAYMENT_APPROVED_CH")); // Approved for CH
            agencyBean.setNoOfCandPaymentApprovedForEP(gcetHomeDataMap.get("NO_OF_CANDIDATE_PAYMENT_APPROVED_EP")); // Approved for EP
            agencyBean.setNoOfCandPaymentApprovedForNB(gcetHomeDataMap.get("NO_OF_CANDIDATE_PAYMENT_APPROVED_NB")); // Approved for Net Banking
            agencyBean.setNoOfCandPaymentApprovedForCR(gcetHomeDataMap.get("NO_OF_CANDIDATE_PAYMENT_APPROVED_CR")); // Approved for Credit Card / Debit Card
            
            agencyBean.setNoOfCandPaymentRejectedForDD(gcetHomeDataMap.get("NO_OF_CANDIDATE_PAYMENT_REJECTED_DD")); // Rejected for DD
            agencyBean.setNoOfCandPaymentRejectedForCH(gcetHomeDataMap.get("NO_OF_CANDIDATE_PAYMENT_REJECTED_CH")); // Rejected for CH
            agencyBean.setNoOfCandPaymentRejectedForEP(gcetHomeDataMap.get("NO_OF_CANDIDATE_PAYMENT_REJECTED_EP")); // Rejected for EP
            agencyBean.setNoOfCandPaymentRejectedForNB(gcetHomeDataMap.get("NO_OF_CANDIDATE_PAYMENT_REJECTED_NB")); // Rejected for Net Banking
            agencyBean.setNoOfCandPaymentRejectedForCR(gcetHomeDataMap.get("NO_OF_CANDIDATE_PAYMENT_REJECTED_CR")); // Rejected for Credit Card / Debit Card
            
            agencyBean.setNoOfPaymentPendingForApprovalCandForDD(gcetHomeDataMap.get("NO_OF_CANDIDATE_PENDING_PAYMENT_APPROVAL_DD")); // Pending for approval DD
            agencyBean.setNoOfPaymentPendingForApprovalCandForCH(gcetHomeDataMap.get("NO_OF_CANDIDATE_PENDING_PAYMENT_APPROVAL_CH")); // Pending for approval CH
            agencyBean.setNoOfPaymentPendingForApprovalCandForEP(gcetHomeDataMap.get("NO_OF_CANDIDATE_PENDING_PAYMENT_APPROVAL_EP")); // Pending for approval CH
            //agencyBean.setNoOfPaymentPendingForApprovalCandForNB(gcetHomeDataMap.get("NO_OF_CANDIDATE_PENDING_PAYMENT_APPROVAL_NB")); // Pending for Net Banking
            //agencyBean.setNoOfPaymentPendingForApprovalCandForCR(gcetHomeDataMap.get("NO_OF_CANDIDATE_PENDING_PAYMENT_APPROVAL_CR")); // Pending for Credit Card / Debit Card
            
            //agencyBean.setPaymentSubmittedStatus(ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.PAYMENT));
            agencyBean.setPaymentSubmittedStatus("A");
            agencyBean.setPaymentApproveStatus(ConfigurationConstants.getInstance().checkStatusOfStatusMasterTableData(GenericConstants.PAYMENT_APPROVED));
            agencyBean.setPaymentRejectStatus(ConfigurationConstants.getInstance().checkStatusOfStatusMasterTableData(GenericConstants.PAYMENT_REJECTED));
            
            
            	ConfigurationConstants.getInstance().getPaymentKeyByDesc(GenericConstants.NET_BANKING);
            
                agencyBean.setNbPaymentStatusFlag(ConfigurationConstants.getInstance().getPaymentMasterMapForStatusForDesc(GenericConstants.ONLINE));
            
                agencyBean.setCrPaymentStatusFlag(ConfigurationConstants.getInstance().getPaymentMasterMapForStatusForDesc(GenericConstants.CREDIT_CARD));
            
                agencyBean.setDdPaymentStatusFlag(ConfigurationConstants.getInstance().getPaymentMasterMapForStatusForDesc(GenericConstants.DEMAND_DRAFT));
            
                agencyBean.setChPaymentStatusFlag(ConfigurationConstants.getInstance().getPaymentMasterMapForStatusForDesc(GenericConstants.CHALLAN));
                
                agencyBean.setEpPaymentStatusFlag(ConfigurationConstants.getInstance().getPaymentMasterMapForStatusForDesc(GenericConstants.EPOST));
            
            //END :: Payment Management
            
            
            //START :: Schedule Management
            
            //agencyBean.setNoOfNonScheduledCand(gcetHomeDataMap.get("NO_OF_CANDIDATE_NON_SCHEDULED"));
            //agencyBean.setNoOfScheduledCand(gcetHomeDataMap.get("NO_OF_CANDIDATE_SCHEDULED"));
            //agencyBean.setNoOfPendingForSchedulingCand(gcetHomeDataMap.get("NO_OF_CANDIDATE_PENDING_FOR_SCHEDULED"));
            
            agencyBean.setGenerateAdmitCardStatus(ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.GENERATE_ADMIT_CARD));
            if (gcetHomeDataMap.get("NO_OF_CANDIDATE_FOR_ADMIT_CARD")!=null){
            	agencyBean.setNoOfAdmitCardGenCand(gcetHomeDataMap.get("NO_OF_CANDIDATE_FOR_ADMIT_CARD"));
            }
            
            if("A".equals(ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.SELECT_TEST_CENTER_PREFERENCE)) 
                    || "A".equals(ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.OTBS)))
            {
                agencyBean.setScheduleMgmtStatus("A");
            }
            
            
            /*agencyBean.setNoOfAttmpt1Cand(gcetHomeDataMap.get("NO_OF_ATTEMPT_1_CANDIDATE"));
            agencyBean.setNoOfAttmpt2Cand(gcetHomeDataMap.get("NO_OF_ATTEMPT_2_CANDIDATE"));
            
            agencyBean.setAttemp1Status(ConfigurationConstants.getInstance().checkStatusOfStatusMasterTableData(GenericConstants.ATTEMPT_ONE_BOOKED));
            agencyBean.setAttemp2Status(ConfigurationConstants.getInstance().checkStatusOfStatusMasterTableData(GenericConstants.ATTEMPT_TWO_BOOKED));
            
            agencyBean.setNoOfCandidatesAdmitCardGeneratedForAttmpt1(gcetHomeDataMap.get("NO_OF_CANDIDATE_FOR_ADMIT_CARD_ATTEMPT_1"));
            agencyBean.setNoOfCandidatesAdmitCardGeneratedForAttmpt2(gcetHomeDataMap.get("NO_OF_CANDIDATE_FOR_ADMIT_CARD_ATTEMPT_2"));
            agencyBean.setGenerateAdmitCardStatus(ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.GENERATE_ADMIT_CARD));*/
            //END :: Schedule Management
	/*	}catch (Exception e) {
			logger.fatal(e,e);
		}
		
		return result;
	}*/
	
	public String getGcetLoginHome() {
		//logger.info("getGcetLoginHome()");			
		String result = "gcetHome";
		Map<String,Integer>  gcetHomeDataMap = null;
		
		Map<Integer, String> paymentModeTypeMap  = ConfigurationConstants.getInstance().getPaymentMasterMap();
		
		try
		{
			if(loggedInUser.getUserType().compareTo(GenericConstants.USER_TYPE_ACCOUNTS)==0)
			{
				long testCentreID = 0;
								
				testCentreID = agencyService.getTrainingCentreForAccUser(loggedInUser);
				
				gcetHomeDataMap = 	agencyService.getGcetACCHomeData(testCentreID);
			}
			else
			{
				gcetHomeDataMap = 	agencyService.getGcetAdminHomeData();
			}
			
			Map<Integer, String> discliplineList = new LinkedHashMap<Integer, String>();
	        
	        discliplineList = agencyService.getTestMasterDetails();
	            
	        if (discliplineList!=null)
	        {
	                agencyBean.setDiscliplineList(discliplineList);
	        }
	            
	        //START :: Application Form Management 
	        if(loggedInUser.getUserType().compareTo(GenericConstants.USER_TYPE_ACCOUNTS)==0)
			{
	        	agencyBean.setTotalNoOfCandidate(gcetHomeDataMap.get("NO_OF_INITIATED_CANDIDATE")+gcetHomeDataMap.get("NO_OF_CANDIDATE_APPLICATION_SUBMITED")); // Total Candidates
			}
	        else
	        {
	        	agencyBean.setTotalNoOfCandidate(gcetHomeDataMap.get("TOTAL_NO_OF_CANDIDATES")); // Total Candidates
	        }
            agencyBean.setNoOfInitiatedCand(gcetHomeDataMap.get("NO_OF_INITIATED_CANDIDATE")); // Initiated
            agencyBean.setNoOfNotInitiatedCand(gcetHomeDataMap.get("NO_OF_NOT_INITIATED_CANDIDATE")); // NOT Initiated
            agencyBean.setNoOfCandidatesApplicationSubmitted(gcetHomeDataMap.get("NO_OF_CANDIDATE_APPLICATION_SUBMITED"));//submitted
            agencyBean.setNoOfApprovedCand(gcetHomeDataMap.get("NO_OF_APPROVED_CANDIDATE"));
            agencyBean.setNoOfRejectedCand(gcetHomeDataMap.get("NO_OF_REJECTED_CANDIDATE"));
            agencyBean.setNoOfPendingCand(gcetHomeDataMap.get("NO_OF_PENDING_CANDIDATE"));
            agencyBean.setCandidateApproveRejectStatus(ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.CANDIDATE_APPROVE_REJECT));
            agencyBean.setApplicationSubmittedCount(gcetHomeDataMap.get("NO_OF_APPLICATION_SUBMITTED"));//application submitted
            
         //   agencyBean.setAdmitCardDownloadCount(gcetHomeDataMap.get("ADMIT_CARD_DOWNLOAD_COUNT"));
        //    agencyBean.setPracTestAttemptCount(gcetHomeDataMap.get("PRAC_TEST_DOWNLOAD_COUNT"));
            //END :: Application Form Management
            
            
            //START :: Payment Management
            agencyBean.setNoOfCandPaymentSubmitted(gcetHomeDataMap.get("NO_OF_PAYMENT_SUBMITTED")+""); // Payment Submitted
 // Approved for CH
            agencyBean.setNoOfCandPaymentApprovedForNB(gcetHomeDataMap.get("NO_OF_CANDIDATE_PAYMENT_APPROVED_NB")); // Approved for Net Banking
          
            agencyBean.setNoOfCandPaymentRejectedForNB(gcetHomeDataMap.get("NO_OF_CANDIDATE_PAYMENT_REJECTED_NB")); // Rejected for Net Banking
 // Rejected for CASH
            
            agencyBean.setNoOfPaymentPendingForApprovalCandForDD(gcetHomeDataMap.get("NO_OF_CANDIDATE_PENDING_PAYMENT_APPROVAL_DD")); // Pending for approval DD
            agencyBean.setNoOfPaymentPendingForApprovalCandForCH(gcetHomeDataMap.get("NO_OF_CANDIDATE_PENDING_PAYMENT_APPROVAL_CH")); // Pending for approval CH
            agencyBean.setNoOfPaymentPendingForApprovalCandForCASH(gcetHomeDataMap.get("NO_OF_CANDIDATE_PENDING_PAYMENT_APPROVAL_CASH")); // Pending for approval CASH
            agencyBean.setNoOfPaymentPendingForApprovalCandForNB(gcetHomeDataMap.get("NO_OF_CANDIDATE_PENDING_PAYMENT_APPROVAL_NB")); // Pending for Net Banking
            //agencyBean.setNoOfPaymentPendingForApprovalCandForCR(gcetHomeDataMap.get("NO_OF_CANDIDATE_PENDING_PAYMENT_APPROVAL_CR")); // Pending for Credit Card / Debit Card
            
            //agencyBean.setPaymentSubmittedStatus(ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.PAYMENT));
            agencyBean.setPaymentSubmittedStatus("A");
            agencyBean.setPaymentApproveStatus(ConfigurationConstants.getInstance().checkStatusOfStatusMasterTableData(GenericConstants.PAYMENT_APPROVED));
            agencyBean.setPaymentRejectStatus(ConfigurationConstants.getInstance().checkStatusOfStatusMasterTableData(GenericConstants.PAYMENT_REJECTED));
            
            
            ConfigurationConstants.getInstance().getPaymentKeyByDesc(GenericConstants.ONLINE);//Start : Added by SANTOSHKUMAR SINGH(Kohinoor) on 07th April 2018
            
                agencyBean.setNbPaymentStatusFlag(ConfigurationConstants.getInstance().getPaymentMasterMapForStatusForDesc(GenericConstants.ONLINE));//Start : Added by SANTOSHKUMAR SINGH(Kohinoor) on 07th April 2018
            
                agencyBean.setCrPaymentStatusFlag(ConfigurationConstants.getInstance().getPaymentMasterMapForStatusForDesc(GenericConstants.CREDIT_CARD));
            
                agencyBean.setDdPaymentStatusFlag(ConfigurationConstants.getInstance().getPaymentMasterMapForStatusForDesc(GenericConstants.DEMAND_DRAFT));
            
                agencyBean.setChPaymentStatusFlag(ConfigurationConstants.getInstance().getPaymentMasterMapForStatusForDesc(GenericConstants.CHALLAN));
                
                agencyBean.setCashPaymentStatusFlag(ConfigurationConstants.getInstance().getPaymentMasterMapForStatusForDesc(GenericConstants.CASH));
            
            //END :: Payment Management
            
            
            //START :: Schedule Management
            
            agencyBean.setNoOfNonScheduledCand(gcetHomeDataMap.get("NO_OF_CANDIDATE_NON_SCHEDULED"));
            agencyBean.setNoOfScheduledCand(gcetHomeDataMap.get("NO_OF_CANDIDATE_SCHEDULED"));
            agencyBean.setNoOfPendingForSchedulingCand(gcetHomeDataMap.get("NO_OF_CANDIDATE_PENDING_FOR_SCHEDULED"));
            
            agencyBean.setGenerateAdmitCardStatus(ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.GENERATE_ADMIT_CARD));
            if (gcetHomeDataMap.get("NO_OF_CANDIDATE_FOR_ADMIT_CARD")!=null){
            	agencyBean.setNoOfAdmitCardGenCand(gcetHomeDataMap.get("NO_OF_CANDIDATE_FOR_ADMIT_CARD"));
            }
            
            if("A".equals(ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.SELECT_TEST_CENTER_PREFERENCE)) 
                    || "A".equals(ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.OTBS)))
            {
                agencyBean.setScheduleMgmtStatus("A");
            }
            if(loggedInUser.getUserType().compareTo(GenericConstants.USER_TYPE_MANISH)==0){
            	agencyBean.setShowPayment(false);
            	result = "accountsHome";
			}else if(loggedInUser.getUserType().compareTo(GenericConstants.USER_TYPE_ACCOUNTS)==0 || loggedInUser.getUserType().compareTo(GenericConstants.USER_TYPE_ACCOUNTS_ADMIN)==0){ //Anupa - Accounts user
				agencyBean.setShowPayment(true);
				result = "accountsHome";
			}
            
            /*agencyBean.setNoOfAttmpt1Cand(gcetHomeDataMap.get("NO_OF_ATTEMPT_1_CANDIDATE"));
            agencyBean.setNoOfAttmpt2Cand(gcetHomeDataMap.get("NO_OF_ATTEMPT_2_CANDIDATE"));
            
            agencyBean.setAttemp1Status(ConfigurationConstants.getInstance().checkStatusOfStatusMasterTableData(GenericConstants.ATTEMPT_ONE_BOOKED));
            agencyBean.setAttemp2Status(ConfigurationConstants.getInstance().checkStatusOfStatusMasterTableData(GenericConstants.ATTEMPT_TWO_BOOKED));
            
            agencyBean.setNoOfCandidatesAdmitCardGeneratedForAttmpt1(gcetHomeDataMap.get("NO_OF_CANDIDATE_FOR_ADMIT_CARD_ATTEMPT_1"));
            agencyBean.setNoOfCandidatesAdmitCardGeneratedForAttmpt2(gcetHomeDataMap.get("NO_OF_CANDIDATE_FOR_ADMIT_CARD_ATTEMPT_2"));
            agencyBean.setGenerateAdmitCardStatus(ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.GENERATE_ADMIT_CARD));*/
            //END :: Schedule Management
		}catch (Exception e) {
			logger.fatal(e,e);
		}
		
		return result;
	}
	
	
	
	public String goToActiveMenu() {
		logger.info("goToActiveMenu");
		String result = "dynamicMenu";
		Map<String,Integer>  gcetHomeDataMap = null;
		
		Map<Integer, String> paymentModeTypeMap  = ConfigurationConstants.getInstance().getPaymentMasterMap();
		
		try
		{
			gcetHomeDataMap = 	agencyService.getGcetAdminHomeData();
			
			Map<Integer, String> discliplineList = new LinkedHashMap<Integer, String>();
	        
	        discliplineList = agencyService.getTestMasterDetails();
	            
	        if (discliplineList!=null)
	        {
	                agencyBean.setDiscliplineList(discliplineList);
	        }
	            
	        //START :: Application Form Management 
	        agencyBean.setTotalNoOfCandidate(gcetHomeDataMap.get("TOTAL_NO_OF_CANDIDATES")); // Total Candidates
            agencyBean.setNoOfInitiatedCand(gcetHomeDataMap.get("NO_OF_INITIATED_CANDIDATE")); // Initiated
            agencyBean.setNoOfCandidatesApplicationSubmitted(gcetHomeDataMap.get("NO_OF_CANDIDATE_APPLICATION_SUBMITED"));
            agencyBean.setNoOfApprovedCand(gcetHomeDataMap.get("NO_OF_APPROVED_CANDIDATE"));
            agencyBean.setNoOfRejectedCand(gcetHomeDataMap.get("NO_OF_REJECTED_CANDIDATE"));
            agencyBean.setNoOfPendingCand(gcetHomeDataMap.get("NO_OF_PENDING_CANDIDATE"));
            agencyBean.setCandidateApproveRejectStatus(ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.CANDIDATE_APPROVE_REJECT));
            //END :: Application Form Management
            
            
            //START :: Payment Management
            agencyBean.setNoOfCandPaymentSubmitted(gcetHomeDataMap.get("NO_OF_PAYMENT_SUBMITTED")+""); // Payment Submitted
            agencyBean.setNoOfCandPaymentApprovedForDD(gcetHomeDataMap.get("NO_OF_CANDIDATE_PAYMENT_APPROVED_DD")); // Approved for DD
            agencyBean.setNoOfCandPaymentApprovedForCH(gcetHomeDataMap.get("NO_OF_CANDIDATE_PAYMENT_APPROVED_CH")); // Approved for CH
            agencyBean.setNoOfCandPaymentApprovedForNB(gcetHomeDataMap.get("NO_OF_CANDIDATE_PAYMENT_APPROVED_NB")); // Approved for Net Banking
            agencyBean.setNoOfCandPaymentApprovedForCR(gcetHomeDataMap.get("NO_OF_CANDIDATE_PAYMENT_APPROVED_CR")); // Approved for Credit Card / Debit Card
            
            agencyBean.setNoOfCandPaymentRejectedForDD(gcetHomeDataMap.get("NO_OF_CANDIDATE_PAYMENT_REJECTED_DD")); // Rejected for DD
            agencyBean.setNoOfCandPaymentRejectedForCH(gcetHomeDataMap.get("NO_OF_CANDIDATE_PAYMENT_REJECTED_CH")); // Rejected for CH
            agencyBean.setNoOfCandPaymentRejectedForNB(gcetHomeDataMap.get("NO_OF_CANDIDATE_PAYMENT_REJECTED_NB")); // Rejected for Net Banking
            agencyBean.setNoOfCandPaymentRejectedForCR(gcetHomeDataMap.get("NO_OF_CANDIDATE_PAYMENT_REJECTED_CR")); // Rejected for Credit Card / Debit Card
            
            agencyBean.setNoOfPaymentPendingForApprovalCandForDD(gcetHomeDataMap.get("NO_OF_CANDIDATE_PENDING_PAYMENT_APPROVAL_DD")); // Pending for approval DD
            agencyBean.setNoOfPaymentPendingForApprovalCandForCH(gcetHomeDataMap.get("NO_OF_CANDIDATE_PENDING_PAYMENT_APPROVAL_CH")); // Pending for approval CH
            //agencyBean.setNoOfPaymentPendingForApprovalCandForNB(gcetHomeDataMap.get("NO_OF_CANDIDATE_PENDING_PAYMENT_APPROVAL_NB")); // Pending for Net Banking
            //agencyBean.setNoOfPaymentPendingForApprovalCandForCR(gcetHomeDataMap.get("NO_OF_CANDIDATE_PENDING_PAYMENT_APPROVAL_CR")); // Pending for Credit Card / Debit Card
            
            agencyBean.setPaymentSubmittedStatus(ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.PAYMENT));
            agencyBean.setPaymentApproveStatus(ConfigurationConstants.getInstance().checkStatusOfStatusMasterTableData(GenericConstants.PAYMENT_APPROVED));
            agencyBean.setPaymentRejectStatus(ConfigurationConstants.getInstance().checkStatusOfStatusMasterTableData(GenericConstants.PAYMENT_REJECTED));
            
            if(paymentModeTypeMap.get(1) != null && paymentModeTypeMap.get(1).equals("Net Banking"))
            {
                agencyBean.setNbPaymentStatusFlag("A");
            }
            
            if(paymentModeTypeMap.get(2) != null && paymentModeTypeMap.get(2).equals("Credit Card / Debit Card"))
            {
                agencyBean.setCrPaymentStatusFlag("A");
            }
            
            if(paymentModeTypeMap.get(3) != null && paymentModeTypeMap.get(3).equals("Demand Draft"))
            {
                agencyBean.setDdPaymentStatusFlag("A");
            }
            
            if(paymentModeTypeMap.get(4) != null && paymentModeTypeMap.get(4).equals("Challan"))
            {
                agencyBean.setChPaymentStatusFlag("A");
            }
            
            //END :: Payment Management
            
            
            //START :: Schedule Management
            
            agencyBean.setNoOfNonScheduledCand(gcetHomeDataMap.get("NO_OF_CANDIDATE_NON_SCHEDULED"));
            agencyBean.setNoOfScheduledCand(gcetHomeDataMap.get("NO_OF_CANDIDATE_SCHEDULED"));
            agencyBean.setNoOfPendingForSchedulingCand(gcetHomeDataMap.get("NO_OF_CANDIDATE_PENDING_FOR_SCHEDULED"));
            
            agencyBean.setGenerateAdmitCardStatus(ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.GENERATE_ADMIT_CARD));
            if (gcetHomeDataMap.get("NO_OF_CANDIDATE_FOR_ADMIT_CARD")!=null && !gcetHomeDataMap.get("NO_OF_CANDIDATE_FOR_ADMIT_CARD").equals("")){
            	agencyBean.setNoOfAdmitCardGenCand(gcetHomeDataMap.get("NO_OF_CANDIDATE_FOR_ADMIT_CARD"));
            }
            
            if("A".equals(ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.SELECT_TEST_CENTER_PREFERENCE)) 
                    || "A".equals(ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.OTBS)))
            {
                agencyBean.setScheduleMgmtStatus("A");
            }
     	}catch (Exception e) {
			logger.fatal(e,e);
		}
		
		return result;
	}
	
	
	public String getReport() {
		logger.info("getReport");
		String menuKey = null;
		String result = null;
		try {
		if(request.getAttribute("parentMenuKey")!=null){
			menuKey = request.getAttribute("parentMenuKey").toString();
		}
		String actionUrl = redirectToNextActivePage(menuKey,loggedInUser);
		if(actionUrl==null){
			request.setAttribute(DESTINATION_URL,actionUrl);
			result = "childNotFoundForMenu";
		}else{
			request.setAttribute(DESTINATION_URL,actionUrl);
			result = REDIRECT_ACTION_URL;
		}
		} catch (Exception e) {
			logger.fatal(e,e);
		}
		return result;
	}
	
	
	/**
	 * @author vijaya 
	 * 
	 * To display the Registration Candidate Details Report.
	 * 
	 * @return
	 */
	public String getReportPage() {
		logger.info("getReportPage()");			
		String result = null;
		Map<Integer, String> interviewLocMap =null;
		Map<Integer, String> programList = null;
		Map<Integer, String> categoryList = null;
		Map<Integer, String> SubcategoryList = null;
		Map<Integer, String> registrationStatusList = null;
		Map<Integer, String> registrationStatusMap = new HashMap<Integer, String>();
		
		try 
		{
			result = "reportPage";
			
			programList = ConfigurationConstants.getInstance().getProgramMasterMap();
			agencyBean.setProgramDetailsList(programList);
			categoryList = ConfigurationConstants.getInstance().getCategoryListMap();
			agencyBean.setCategoryList(categoryList);
			
			interviewLocMap = ConfigurationConstants.getInstance().getInterviewLocMap();
			agencyBean.setInterviewLocMap(interviewLocMap);
            
			
			registrationStatusList = ConfigurationConstants.getInstance().getStatusMasterMapForReport();
			
			for (Entry<Integer, String> entry : registrationStatusList.entrySet()) 
            {

                if(entry.getValue().equals(GenericConstants.REGISTRATION_INTITIATED))
                {
                    registrationStatusMap.put(entry.getKey(), "Initiated");
                }
               /* if(entry.getValue().equals(GenericConstants.REGISTRATION_SUBMITED))
                {
                    registrationStatusMap.put(entry.getKey(), "Application Form Submitted");
                }*/
                if(entry.getValue().equals(GenericConstants.NEW_REGISTERATION))
                {
                    registrationStatusMap.put(entry.getKey(), "Not Initiated");
                }
                if(entry.getValue().equals(GenericConstants.REGISTRATION_SUBMITED))
                {
                	registrationStatusMap.put(entry.getKey(), "Profile Submitted");
                }
            }
            
            agencyBean.setRegistrationStatusList(registrationStatusMap);
			
		} catch (Exception e) {
			logger.fatal(e,e);
		}
		
		return result;
	}
	
	
	public String generateHTMLReportForCandidateDetails() {
        logger.info("generateReport()");    
        String result = "reportPage";
        
        Map<Integer, String> programList = null;
        Map<Integer, String> categoryList = null;
        Map<Integer, String> SubcategoryList = null;
        Map<Integer, String> registrationStatusList = null;
        Map<Integer, String> interviewLocMap = null;
        
        Map<Integer, String> registrationStatusMap = new HashMap<Integer, String>();
        try 
        {
        	String hidTotalPages = request.getParameter("hidTotalPages");
            logger.info("Program Name = " + agencyBean.getProgramName());
            logger.info("Category Name = " + agencyBean.getCategory());
            logger.info("Registration Status = " + agencyBean.getRegistrationStatus());
            logger.info("Registration From Date = " + agencyBean.getRegistrationFromDate());
            logger.info("Registration From Date = " + agencyBean.getRegistrationToDate());
            logger.info("Interview = " + agencyBean.getInterviewVal());
            programList = ConfigurationConstants.getInstance().getProgramMasterMap();
            agencyBean.setProgramDetailsList(programList);
            categoryList = ConfigurationConstants.getInstance().getCategoryListMap();
            agencyBean.setCategoryList(categoryList);
            interviewLocMap = ConfigurationConstants.getInstance().getInterviewLocMap();
			agencyBean.setInterviewLocMap(interviewLocMap);
            
            registrationStatusList = ConfigurationConstants.getInstance().getStatusMasterMapForReport();
            
            for (Entry<Integer, String> entry : registrationStatusList.entrySet()) 
            {
                if(entry.getValue().equals(GenericConstants.REGISTRATION_INTITIATED))
                {
                    registrationStatusMap.put(entry.getKey(), "Initiated");
                }
               /* if(entry.getValue().equals(GenericConstants.REGISTRATION_SUBMITED))
                {
                    registrationStatusMap.put(entry.getKey(), "Application Form Submitted");
                }*/
                if(entry.getValue().equals(GenericConstants.NEW_REGISTERATION))
                {
                    registrationStatusMap.put(entry.getKey(), "Not Initiated");
                }
                if(entry.getValue().equals(GenericConstants.REGISTRATION_SUBMITED))
                {
                	registrationStatusMap.put(entry.getKey(), "Profile Submitted");
                }
            }
            
            // Incase in future need to change this value for 20
            agencyBean.setRegistrationStatusList(registrationStatusMap);
           /* if(agencyBean.getRegistrationStatus()!=null){
            	agencyBean.setRegistrationStatus(agencyBean.getRegistrationStatus());
            }
            */
           /* if(agencyBean.getRegistrationStatus().equals(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED)+""))
            {
                pagination.setProperties(agencyService.getCandidateDetailsListCountForReportForApproved(agencyBean, "5"));
                candidateDetailsList = agencyService.getCandidateDetailsListForReportForApproved(agencyBean, "5",pagination);
            }
            
            else if(agencyBean.getRegistrationStatus().equals(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_INTITIATED)+""))
            {
            	pagination.setProperties(agencyService.getCandidateDetailsListCountForReportForApproved(agencyBean, "3"));
                candidateDetailsList = agencyService.getCandidateDetailsListForReportForApproved(agencyBean, "3",pagination);
            }
            else
            {*/
            	pagination.setProperties(agencyService.getCandidateDetailsListCountForReport(agencyBean));
                candidateDetailsList = agencyService.getCandidateDetailsListForReport(agencyBean,pagination);
           // }
            
            if(candidateDetailsList != null)
            {
            	if(pagination.getTotal_records()==0)
            		agencyBean.setCandidateDetailsListSize(agencyBean.getCandidateDetailsListSize());
            	else
                agencyBean.setCandidateDetailsListSize(pagination.getTotal_records());
            }
            else
            {
                agencyBean.setCandidateDetailsListSize(0);
            }
            
            //if (candidateDetailsList != null && !candidateDetailsList.isEmpty())
            //{
                agencyBean.setCandidateDetailsList(candidateDetailsList);
                agencyBean.setShowCandidateDetailsReport("Y");
            //}
            
                if(agencyBean.getMenuKey() != null)
                {
                    request.setAttribute("menuKey", agencyBean.getMenuKey());
                }
            //pagination.setTotal_pages(Integer.parseInt(hidTotalPages));
            
            //ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger().info(loggedInUser.getUsername()+ ", "+ request.getRemoteAddr()+ ", Generated Registered Candidate Details Report in HTML  , "+ CommonUtil.formatDate(new Date(),"dd-MMM-yyyy HH:mm:ss"));
        } catch (Exception e) {
            logger.fatal(e,e);
        }
        
        return result;
    }
	/**
	 * @author vijaya 
	 * To Generate Registered Candidate Details Report
	 * @return
	 */
	public void generateReport() 
	{
	    logger.info("generateReport()");	
		String result = "excel";
		
		try 
		{
			parameterValues = new HashMap();
	        
	        logger.info("Program Name = " + agencyBean.getProgramName());
	        logger.info("Category Name = " + agencyBean.getCategory());
	        
	        logger.info("Registration Status = " + agencyBean.getRegistrationStatus());
	        
	        logger.info("Registration From Date = " + agencyBean.getRegistrationFromDate());
	        logger.info("Registration From Date = " + agencyBean.getRegistrationToDate());
	        
	        if (agencyBean.getFormat() != null && !agencyBean.getFormat().equals("")
	                && agencyBean.getFormat().equals("EXCEL"))
	        {
	            result = "excel";
	        }
	        
	       /* if(agencyBean.getRegistrationStatus().equals(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_APPROVED)+""))
	        {
                candidateDetailsList = agencyService.getCandidateDetailsListForReportForApproved(agencyBean, "1");
	        }
	        else if(agencyBean.getRegistrationStatus().equals(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_REJECTED)+""))
            {
                candidateDetailsList = agencyService.getCandidateDetailsListForReportForApproved(agencyBean, "0");
            }
            else
	        {*/
                candidateDetailsList = agencyService.getCandidateDetailsListForReport(agencyBean);
	        //}
	        
            if (candidateDetailsList != null && !candidateDetailsList.isEmpty())
            {
                agencyBean.setCandidateDetailsList(candidateDetailsList);
            }
            generateCandidateExcelFile(candidateDetailsList);
/*	        ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger().info(loggedInUser.getUsername()+ ", "+ request.getRemoteAddr()+ ", Generated Registered Candidate Details Report  , "+ CommonUtil.formatDate(new Date(),"dd-MMM-yyyy HH:mm:ss"));
*/		} catch (Exception e) {
			logger.fatal(e,e);
		}
		
		//return result;
	}
	
	public void generateCandidateExcelFile(List<CandidateBean> candidateDetailsList) throws Exception{
		logger.info("generateCandidateExcelFile "+candidateDetailsList);		
		try{
			ExcelCreator excelCreator = new ExcelCreator();
			HSSFWorkbook workbook = excelCreator.createWorkbookForCandidateDetailsReport(candidateDetailsList);
			response.setHeader("Content-Disposition", "attachment; filename=CandidateDetailReport.xls");
			response.setHeader("Cache-control","no-cache, no-store");
			response.setHeader("Pragma","no-cache");
			response.setHeader("Expires","-1");
			ServletOutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();
		}
		catch (Throwable e) {
			logger.fatal(e,e);
		}
	}
	
	/**
	 * @author vijaya 
	 * 
	 * To display the Shortlisted Candidate Details Report.
	 * 
	 * @return
	 */
	public String getShortlistedCandidateReportPage() {
		logger.info("getShortlistedCandidateReportPage()");			
		String result = null;
		
		Map<Integer, String> programList = null;
		Map<Integer, String> categoryList = null;
		Map<Integer, String> SubcategoryList = null;
		Map<Integer, String> interviewTypeList = null;
		
		try 
		{
			result = "getShortlistedCandidateReportPage";
			
			programList = ConfigurationConstants.getInstance().getProgramMasterMap();
			agencyBean.setProgramDetailsList(programList);
			categoryList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Category");
			agencyBean.setCategoryList(categoryList);
			
			interviewTypeList = ConfigurationConstants.getInstance().getParameterMapForDomainName("InterviewType");
			
			agencyBean.setInterviewTypeList(interviewTypeList);
			
			/*agencyBean.getFormatList().clear();
			agencyBean.getFormatList().add("PDF");
			agencyBean.getFormatList().add("EXCEL");*/
			
		} catch (Exception e) {
			logger.fatal(e,e);
		}
		
		return result;
	}
	
	/**
	 * @author vijaya 
	 * To Generate Shorlisted Candidate Report
	 * @return
	 */
	public String generateShortlistedCandidateReport() {
		logger.info("generateShortlistedCandidateReport()");	
		String result = "ShortlistedCandidateReport";
		
		try {
			dataList = new ArrayList();

	        parameterValues = new HashMap();
	        
	        jremptyDataSource = new JREmptyDataSource();
	        
	        logger.info("Program Name = " + agencyBean.getProgramName());
	        logger.info("Category Name = " + agencyBean.getCategory());
	        
	        logger.info("Interview Type = " + agencyBean.getInterviewType());
	        
	        parameterValues.put("PROGRAM_NAME", agencyBean.getProgramName());
	        parameterValues.put("CATEGORY", agencyBean.getCategory());        
	       // parameterValues.put("REGISTRATION_STATUS", agencyBean.getRegistrationStatus());
	       // parameterValues.put("FROM_DATE", agencyBean.getRegistrationFromDate());
	       // parameterValues.put("TO_DATE", agencyBean.getRegistrationToDate());
	        
	        if (agencyBean.getFormat() != null && !agencyBean.getFormat().equals("")
	                && agencyBean.getFormat().equals("EXCEL"))
	        {
	            result = "excel";
	        }
	        
	        connection = reportService.getDataSourceConnection();
	        parameterValues.put(JRParameter.REPORT_CONNECTION, connection);
	        
	        ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger().info(loggedInUser.getUsername()+ ", "+ request.getRemoteAddr()+ ", Generated Selected Candidate Details Report  , "+ CommonUtil.formatDate(new Date(),"dd-MMM-yyyy HH:mm:ss"));
		} catch (Exception e) {
			logger.fatal(e,e);
		}
		
		return result;
	}
	
	/**
	 * @author vijaya 
	 * 
	 * To display the Registration Candidate Details Report's master data.
	 * 
	 * @return
	 */
	public String getSelectedCandidateReportPage() {
		logger.info("getSelectedCandidateReportPage()");			
		String result = null;
		
		Map<Integer, String> programList = null;
		Map<Integer, String> categoryList = null;
		Map<Integer, String> SubcategoryList = null;
		Map<Integer, String> interviewTypeList = null;
		
		try 
		{
			result = "getSelectedCandidateReportPage";
			
			programList = ConfigurationConstants.getInstance().getProgramMasterMap();
			agencyBean.setProgramDetailsList(programList);
			categoryList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Category");
			agencyBean.setCategoryList(categoryList);
			
			interviewTypeList = ConfigurationConstants.getInstance().getParameterMapForDomainName("InterviewType");
			
			agencyBean.setInterviewTypeList(interviewTypeList);
			
			/*agencyBean.getFormatList().clear();
			agencyBean.getFormatList().add("PDF");
			agencyBean.getFormatList().add("EXCEL");*/
			
		} catch (Exception e) {
			logger.fatal(e,e);
		}
		
		return result;
	}
	
	
	
	
	
	public String generateSelectedCandidateReport() {
		logger.info("generateSelectedCandidateReport()");	
		String result = "SelectedCandidateReport";
		
		try {
			dataList = new ArrayList();

	        parameterValues = new HashMap();
	        
	        jremptyDataSource = new JREmptyDataSource();
	        
	        System.out.println("Program Name = " + agencyBean.getProgramName());
	        System.out.println("Category Name = " + agencyBean.getCategory());
	        
	        System.out.println("Interview Type = " + agencyBean.getInterviewType());
	        
	        /*parameterValues.put("GTCM_TEST_CENTRE_PK", agencyBean.getTestMasterValue());
	        parameterValues.put("OTM_TEST_PK", agencyBean.getDisciplineName());        
	        parameterValues.put("TEST_SLOT", agencyBean.getSelectedSlot());
	        parameterValues.put("TEST_DATE", agencyBean.getTestDate());*/
	        
	        if (agencyBean.getFormat() != null && !agencyBean.getFormat().equals("")
	                && agencyBean.getFormat().equals("EXCEL"))
	        {
	            result = "excel";
	        }
	        
	        connection = reportService.getDataSourceConnection();
	        parameterValues.put(JRParameter.REPORT_CONNECTION, connection);
		} catch (Exception e) {
			logger.fatal(e,e);
		}
		
		return result;
	}
	
	public String getMailPage(){
		logger.info("getMailPage()");
		String result = null;
		
		Map<Integer, String> discliplineList = null;
		Map<Integer, String> testCenterMasterDetails = null;
		List<String> testDates = null;
		
		try {
			discliplineList = agencyService.getTestMasterDetails();
			testCenterMasterDetails = candidateMgmtService.getTestCenterMasterDetails();
			testDates = candidateMgmtService.getTestDates();
			
			if (discliplineList!=null && !discliplineList.isEmpty()){
				agencyBean.setDisciplineList(discliplineList);
			}
			
			if (testCenterMasterDetails!=null && !testCenterMasterDetails.isEmpty()){
				agencyBean.setTestCenterMasterDetails(testCenterMasterDetails);
			}
			
			if (testDates!=null && !testDates.isEmpty()){
				agencyBean.setTestDateListForAdminList(testDates);
			}
			
			
			
			result = "mailPage";
		} catch (Exception e) {
			logger.fatal(e,e);
		}
		
		return result;
		
	}
	
	public String sendMail(){
		logger.info("sendMail()");
		String result = null;
		int insert = 0;
		List <AgencyBean> candidateDateForEmail = null;
		
		try {
			candidateDateForEmail = agencyService.getDataForGenericMails(agencyBean);
			
			if (candidateDateForEmail!=null && !candidateDateForEmail.isEmpty()){
				for (AgencyBean agencyBean2 : candidateDateForEmail) {
					if (agencyBean2!=null){
						insert = agencyService.insertEmailSMSDetails(agencyBean, "E", loggedInUser,agencyBean2.getEmail(),agencyBean2.getMobileNo(),agencyBean2.getUserId(),agencyBean2);	
					}
				}
				 if (insert > 0){
	                  request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Record Inserted Successfully");
	                  request.setAttribute(GenericConstants.DESTINATION_PATH,"AgencyAction_getMailPage.action");
	                  result = "displayMessage";
	              }
	              else{
	                  request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Record Insertion failed");
	                  request.setAttribute(GenericConstants.MESSAGE_TYPE,GenericConstants.MESSAGE_TYPE_ERROR);
	                  request.setAttribute(GenericConstants.DESTINATION_PATH,"AgencyAction_getMailPage.action");
	                  result = "displayMessage";
	              }
			}else {
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "No candidates found for Selected criteria");
               // request.setAttribute(GenericConstants.MESSAGE_TYPE,GenericConstants.MESSAGE_TYPE_ERROR);
                request.setAttribute(GenericConstants.DESTINATION_PATH,"AgencyAction_getMailPage.action");
                result = "displayMessage";
			}
			
		} catch (Exception e) {
			logger.fatal(e,e);
		}
		
		return result;
		
	}
	
	public String importCredentials(){
		logger.info("importCredentials()");
		String result = null;
		try {
			result = "importCredentials";
		} catch (Exception e) {
			logger.fatal(e,e);
		}
		
		return result;
	}
	
	
public String insertCsvFileData() 
{
	
	String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.NEW_REGISTERATION)+"";
	Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.NEW_REGISTERATION);
	Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.NEW_REGISTERATION);
	
	StringBuilder importSummary = new StringBuilder();
	Integer successCounter = 0;
	
	Integer failCounter = 0;
	
	Integer totalCounter = 0;
	
	Boolean isValidatedToInsert = true;
	
	//Pattern p=Pattern.compile("[a-zA-Z]*[0-9]*@[a-zA-Z]*.[a-zA-Z]*");
	
	Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
	
	
	Pattern pattern = Pattern.compile("\\d{10}");
	
	Matcher mobileMatcher = null;
	
	Matcher emailMatcher = null;

	boolean emailValidFlag = false;
	
	boolean mobileValidFlag = false;
		
		String result = null;
		try {
			
			File csvFile = null;
			csvFile = agencyBean.getCsvFile();

			String csvFilePath = System.getProperty("user.dir") + "//";
			final String newFilePath = csvFilePath+ agencyBean.getCsvFileFileName();
			int pos = newFilePath.lastIndexOf('.');
            String extn = newFilePath.substring(pos+1);
            if (extn != null && !extn.equals("")){
            	if(!(extn.equalsIgnoreCase("csv"))){
            		addActionMessage("Please upload a valid .CSV file");
            		displayMenus();
            		return "importCredentials";
            	}
            }	
			
			if (csvFile!=null){
				
				agencyBean.setFlagForDisplay("true");
				
				CsvReader csvReader = null;
				csvReader = new CsvReader(csvFile.toString());
				
				csvReader.readHeaders();
				
				if(csvReader.readHeaders())
				{
				
				List<String> userIdListFromFile = new ArrayList<String>();
				List<String> userIdListFromInsert = new ArrayList<String>();
				List<String> passwordListFromFile = new ArrayList<String>();
				List<String> ipinListFromFile = new ArrayList<String>();
				
				if (csvReader!=null){
					csvReader.readHeaders();
					while(csvReader.readRecord()){
						if (csvReader.get("User ID")!=null && !csvReader.get("User ID").equals("")){
							userIdListFromFile.add(csvReader.get("User ID"));
						}
					}
				}
				
				csvReader = null;
				csvReader = new CsvReader(csvFile.toString());
					if (csvReader!=null)
					{
						csvReader.readHeaders();
						HashSet<String> set = new HashSet<String>();
						Integer disciplineId = null; 
						while(csvReader.readRecord())
						{
							isValidatedToInsert = true;
							totalCounter = totalCounter + 1;
							String userId = csvReader.get("User ID");
							if(userId != null)
							{

								if(userId != null && userId.equals(""))
								{
									isValidatedToInsert = false;
									importSummary.append("<b>User ID </b> is mandatory.<br/>");
								}
								
								if(set.add(userId))
								{	
									if (csvReader.get("Discipline")!=null && !csvReader.get("Discipline").equals("")){
										disciplineId = ConfigurationConstants.getInstance().getDisciplineKeyForName(csvReader.get("Discipline"));
										
										if(disciplineId.equals(0))
										{
											importSummary.append("User ID "+ userId + " : <b>Discipline </b> is invalid"+"<br/>");
											isValidatedToInsert = false;
										}
									}
									else 
									{
										importSummary.append("User ID "+ userId + " : <b>Discipline </b> is absent" +"<br/>");
										isValidatedToInsert = false;
									}
									
									if (csvReader.get("Password") == null || csvReader.get("Password").equals(""))
									{
										importSummary.append("User ID "+ userId + " : <b>Password </b> is absent" +"<br/>");
										isValidatedToInsert = false;
									}
									else if(csvReader.get("Password").length() < 8)
									{
										importSummary.append("User ID "+ userId + " : <b>Password </b> should be of minimum 8 characters" +"<br/>");
										isValidatedToInsert = false;
									}
									
									if (csvReader.get("IPIN") == null || csvReader.get("IPIN").equals(""))
									{
										importSummary.append("IPIN : <b>IPIN </b> is absent" +"<br/>");
										isValidatedToInsert = false;
									}
									else if(csvReader.get("IPIN").length() < 8)
									{
										importSummary.append("IPIN "+ csvReader.get("IPIN") + " : <b>IPIN </b> should be of minimum 8 characters" +"<br/>");
										isValidatedToInsert = false;
									}
									
									if (csvReader.get("EmailID") == null || csvReader.get("EmailID").equals(""))
									{
										importSummary.append("User ID "+ userId + " : <b>Email ID </b> is absent" +"<br/>");
										isValidatedToInsert = false;
									}
									else
									{
										emailMatcher = p.matcher(csvReader.get("EmailID"));
										emailValidFlag = emailMatcher.matches();
										if(emailValidFlag == false)
										{
											importSummary.append("User ID "+ userId + " : <b>Email ID </b> is invalid" +"<br/>");
											isValidatedToInsert = false;
										}
									}
									
									if (csvReader.get("MobileNo") == null || csvReader.get("MobileNo").equals(""))
									{
										importSummary.append("User ID "+ userId + " : <b>Mobile No </b> is absent" +"<br/>");
										isValidatedToInsert = false;
									}
									else
									{
										mobileMatcher = pattern.matcher(csvReader.get("MobileNo"));
										mobileValidFlag = mobileMatcher.matches();
										if(mobileValidFlag == false)
										{
											importSummary.append("User ID "+ userId + " : <b>Mobile No </b> is invalid" +"<br/>");
											isValidatedToInsert = false;
										}
									}
									
									if(isValidatedToInsert)
									{
										try
										{
											
											int insert = agencyService.insertUserDetails(userId, csvReader.get("Password"), csvReader.get("IPIN"),disciplineId, csvReader.get("EmailID"), csvReader.get("MobileNo"), loggedInUser);
											
											if (insert > 0)
											{
												ConfigurationConstants.getInstance().getImportLogger().info("Candidate "+userId+" inserted successfully ");
												
												successCounter = successCounter + 1;
												
												RegistrationBean registrationBean = new RegistrationBean();
												
												registrationBean.setMobileNo(csvReader.get("MobileNo"));
												
												registrationBean.setEmailAddress(csvReader.get("EmailID"));
												
												RegistrationBean newRegistrationBean = getSystemGeneratedUserID(registrationBean);
												
												EmailSMSUtil.insertEmailNSMSForNewRegisteration(emailReq, smsReq,staus, newRegistrationBean);
											}
										}
										catch(GenericServiceException ex)
										{
											
											if(ex.getCause().getCause().toString().startsWith("org.springframework.dao.DataIntegrityViolationException"))
											{
												agencyBean.setFlagForDisplay("false");
												failCounter = failCounter + 1;
												importSummary.append("User with User ID " + userId + ", Mobile No. " + csvReader.get("MobileNo") + " and Email ID " + csvReader.get("EmailID") + " already exists <br/>");
												ConfigurationConstants.getInstance().getImportLogger().info("Candidate "+userId+" insertion failed ");
											}
										}
									}
									else
									{
										agencyBean.setFlagForDisplay("false");
										failCounter = failCounter + 1;
									}
								}
								else
								{
									agencyBean.setFlagForDisplay("false");
									failCounter = failCounter + 1;
									importSummary.append("User with User ID " + userId + ", Mobile No. " + csvReader.get("MobileNo") + " and Email ID " + csvReader.get("EmailID") + " already exists <br/>");
									ConfigurationConstants.getInstance().getImportLogger().info("Candidate "+userId+" insertion failed ");
								}
						} // end if
						else
						{
							importSummary.append("<b>User ID </b> is mandatory.<br/>");
							failCounter = failCounter + 1;
							agencyBean.setFlagForDisplay("false");
						}
					  }
					}
				}
				else
				{
					agencyBean.setFlagForDisplay("false");
					addActionMessage("User ID, Password, IPIN, Discipline, EmailID and MobileNo fields are mandatory");
				}
			}
			
			agencyBean.setImportCredentialSuccessCount(successCounter);
			agencyBean.setImportCredentialFailCount(failCounter);
			agencyBean.setImportTotalCount(totalCounter);
			agencyBean.setImportSummary(importSummary.toString());
			displayMenus();
            result = "importCredentials";
		} catch (Exception e) {
			logger.fatal(e,e);
		}
		
		return result;
		
	}

	
public String getHelpCenterListToChangePwd(){
	logger.info("getHelpCenterListToChangePwd()");	
		String result = null;
		try {
			agencyBean.setHelpCenterList(agencyService.getHelpCenterList());
			result = "getHelpCenterList";
		} catch (Exception e) {
			logger.fatal(e,e);
		}
		
		return result;
	}

public String changeHelpCentrePassword(){
	logger.info("changeHelpCentrePassword()");	
	String result ="";
		int count=0;
		try {
			
			count =agencyService.updatePasswordForHelpCenter(agencyBean);
			if (count>0)
			{
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "0,Your Password has been changed.");
			result = "writePlainText";
			}else{
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "1,Invalid UserID / IPIN.");
				result = "writePlainText";
			}
		}catch (Exception e) {
			logger.fatal(e,e);
		}
		return result;
}

private RegistrationBean getSystemGeneratedUserID(RegistrationBean registrationBean) {
	logger.info("getSystemGeneratedUserID()");
	String userIdnum = null;
	try
	{
		return agencyService.generateUserID(registrationBean);
	} catch (Exception e) {
		logger.fatal(e,e);
	}
	
	return null;
}

public String pondiReports() {
	//logger.info("pondiReports()");			
	String result = null;
	Map<Integer, String> pondiReportMap = new HashMap<Integer, String>();
	Map<String, String> pondiTestDateMap = new HashMap<String, String>();
	Map<String, String> pondiTestBatchMap = new HashMap<String, String>();
	try 
	{
		result = "pondiReportPage";
		//Report Name
		pondiReportMap.put(1, "Category wise report");
		//pondiReportMap.put(2, "City wise report");
		pondiReportMap.put(2, "Test city wise report");
		//pondiReportMap.put(3, "Test slot wise report");
		agencyBean.setPondiReportsList(pondiReportMap);
		//Test Date
		pondiTestDateMap.put("26-May-2017", "26-May-2017");
		pondiTestDateMap.put("27-May-2017", "27-May-2017");
		pondiTestDateMap.put("28-May-2017", "28-May-2017");
		agencyBean.setTestDatList(pondiTestDateMap);
		//Test Batch
		pondiTestBatchMap.put("09:00-11:00", "09:00-11:00");
		pondiTestBatchMap.put("12:30-14:30", "12:30-14:30");
		pondiTestBatchMap.put("16:00-18:00", "16:00-18:00");
		agencyBean.setTestBatchList(pondiTestBatchMap);
		
	} catch (Exception e) {
		logger.fatal(e,e);
	}
	
	return result;
}


public String generateHTMLReportForPondi() {
    //logger.info("generateHTMLReportForPondi()");    
    String result = "pondiReportPage";
    Map<Integer, String> pondiReportMap = new HashMap<Integer, String>();
    Map<String, String> pondiTestDateMap = new HashMap<String, String>();
	Map<String, String> pondiTestBatchMap = new HashMap<String, String>();
    try 
    {
        logger.info("Report Name = " + agencyBean.getPondiReports());
        //Test Report
        pondiReportMap.put(1, "Category wise report");
		//pondiReportMap.put(2, "City wise report");
        pondiReportMap.put(2, "Test city wise report");//Issue ID : 118178
		//pondiReportMap.put(3, "Test slot wise report"); Issue ID : 118180
		//Test Date
		pondiTestDateMap.put("26-May-2017", "26-May-2017");
		pondiTestDateMap.put("27-May-2017", "27-May-2017");
		pondiTestDateMap.put("28-May-2017", "28-May-2017");
		//Test Batch
		pondiTestBatchMap.put("09:00-11:00", "09:00-11:00");
		pondiTestBatchMap.put("12:30-14:30", "12:30-14:30");
		pondiTestBatchMap.put("16:00-18:00", "16:00-18:00");

		if(agencyBean.getPondiReports() != null)
		{
			if(agencyBean.getPondiReports().equals("1")) // For category wise report
			{
				candidateDetailsList = agencyService.getPondiCategoryWiseReport();
				agencyBean.setPondiReportFlag("1");
			}
			else if(agencyBean.getPondiReports().equals("2"))// For test city wise
			{
				candidateDetailsList = agencyService.getPondiTestCenterWiseReport();
				//candidateDetailsList = null;
				agencyBean.setPondiReportFlag("2");
			}
			else if(agencyBean.getPondiReports().equals("3"))
			{
		//		candidateDetailsList = agencyService.getPondiTestSlotWiseReport(agencyBean.getPondiTestDate(),agencyBean.getPondiTestBatch());
				agencyBean.setPondiReportFlag("2");
			}
		}            
        if(candidateDetailsList != null)
        {
            agencyBean.setCandidateDetailsListSize(candidateDetailsList.size());
        }
        else
        {
            agencyBean.setCandidateDetailsListSize(0);
        }
        
            agencyBean.setCandidateDetailsList(candidateDetailsList);
            agencyBean.setShowCandidateDetailsReport("Y");
        
        if(agencyBean.getMenuKey() != null)
        {
            request.setAttribute("menuKey", agencyBean.getMenuKey());
        }
        agencyBean.setPondiReportsList(pondiReportMap);
        agencyBean.setTestDatList(pondiTestDateMap);
        agencyBean.setTestBatchList(pondiTestBatchMap);
        logger.info(loggedInUser.getUsername()+ ", "+ request.getRemoteAddr()+ ", Generated Registered Candidate Details Report in HTML  , "+ CommonUtil.formatDate(new Date(),"dd-MMM-yyyy HH:mm:ss"));
    } catch (Exception e) {
        logger.fatal(e,e);
    }
    
    return result;
}

public void generateReportForPU() 
{
    //logger.info("generateReportForPU()");	
	String result = "excel";
	HttpSession session = request.getSession();
	String token =null;
	//token = TokenHelper.getTokenName();
	//logger.info("Session token :: "+session.getAttribute(token));
	try 
	{	        
        //logger.info("Program Name = " + agencyBean.getPondiReports());
    	        
        if (agencyBean.getFormat() != null && !agencyBean.getFormat().equals("")
                && agencyBean.getFormat().equals("EXCEL"))
        {
            result = "excel";
        }
        
        if(agencyBean.getPondiReports() != null)
		{
			if(agencyBean.getPondiReports().equals("1")) // For category wise report
			{
				candidateDetailsList = agencyService.getPondiCategoryWiseReport();
			}
			else if(agencyBean.getPondiReports().equals("2"))
			{
				candidateDetailsList = agencyService.getPondiTestCenterWiseReport();
			}
			else if(agencyBean.getPondiReports().equals("3"))
			{
				//candidateDetailsList = agencyService.getPondiTestSlotWiseReport(agencyBean.getPondiTestDate(),agencyBean.getPondiTestBatch());
			}
		}
        
        if (candidateDetailsList != null && !candidateDetailsList.isEmpty())
        {
            agencyBean.setCandidateDetailsList(candidateDetailsList);
        }

        if(agencyBean.getPondiReports().equals("1"))
        {
        	generateCategoryWiseExcelFile(candidateDetailsList); // category wise excel
        }
        else if(agencyBean.getPondiReports().equals("2"))
        {
        	generateTestCenterExcelFile(candidateDetailsList); //test center wise excel
        }
        else if(agencyBean.getPondiReports().equals("3"))
        {
        	// generateTestSlotExcelFile(candidateDetailsList); //test slot wise excel
        }
       
        
	} catch (Exception e) {
		logger.fatal(e,e);
	}
}

public void generateCategoryWiseExcelFile(List<CandidateBean> candidateDetailsList) throws Exception{
	try{
		ExcelCreator excelCreator = new ExcelCreator();
		HSSFWorkbook workbook = excelCreator.createWorkbookForCategoryWiseReport(candidateDetailsList);
		response.setHeader("Content-Disposition", "attachment; filename=CategoryWiseReport.xls");
		response.setHeader("Cache-control","no-cache, no-store");
		response.setHeader("Pragma","no-cache");
		response.setHeader("Expires","-1");
		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
		out.close();
	}
	catch (Throwable e) {
		logger.fatal(e,e);
	}
}

public void generateTestCenterExcelFile(List<CandidateBean> candidateDetailsList) throws Exception{		
	try{
		ExcelCreator excelCreator = new ExcelCreator();
		HSSFWorkbook workbook = excelCreator.createWorkbookForTestCenterWiseReport(candidateDetailsList);
		response.setHeader("Content-Disposition", "attachment; filename=TestCityWiseReport.xls");
		response.setHeader("Cache-control","no-cache, no-store");
		response.setHeader("Pragma","no-cache");
		response.setHeader("Expires","-1");
		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
		out.close();
	}
	catch (Throwable e) {
		logger.fatal(e,e);
	}
}

public HSSFWorkbook createWorkbookForTestCenterWiseReport(
		List<CandidateBean> candidateDetailsList) throws ParseException {
	HSSFWorkbook wb = new HSSFWorkbook();
	HSSFSheet sheet = wb.createSheet("Test City Wise Report");
	
	/**
	 * Setting the width of the first three columns.
	 */
	sheet.setColumnWidth(0, 10000);
	sheet.setColumnWidth(1, 4500);
	sheet.setColumnWidth(2, 5000);
	sheet.setColumnWidth(3, 7000);
	sheet.setColumnWidth(4, 7000);
	sheet.setColumnWidth(5, 7000);
	sheet.setColumnWidth(6, 7000);
	sheet.setColumnWidth(7, 7000);
	sheet.setColumnWidth(8, 7000);
	sheet.setColumnWidth(9, 7000);
	sheet.setColumnWidth(10, 7000);
	/*sheet.setColumnWidth(11, 7000);
	sheet.setColumnWidth(12, 7000);
	sheet.setColumnWidth(13, 7000);
	sheet.setColumnWidth(14, 7000);
	sheet.setColumnWidth(15, 7000);
	sheet.setColumnWidth(16, 7000);
	sheet.setColumnWidth(17, 7000);
	sheet.setColumnWidth(18, 7000);
	sheet.setColumnWidth(19, 7000);
	sheet.setColumnWidth(20, 7000);
	sheet.setColumnWidth(21, 7000);
	sheet.setColumnWidth(22, 7000);
	sheet.setColumnWidth(23, 7000);
	sheet.setColumnWidth(24, 7000);
	sheet.setColumnWidth(25, 7000);
	sheet.setColumnWidth(26, 7000);
	sheet.setColumnWidth(27, 7000);
	sheet.setColumnWidth(28, 7000);
	sheet.setColumnWidth(29, 7000);
	sheet.setColumnWidth(30, 7000);
	sheet.setColumnWidth(31, 7000);
	sheet.setColumnWidth(32, 7000);
	
	sheet.setColumnWidth(33, 7000);
	sheet.setColumnWidth(34, 7000);
	sheet.setColumnWidth(35, 7000);
	sheet.setColumnWidth(36, 7000);
	*/
	/**
	 * Style for the header cells.
	 */
	
	
	HSSFCellStyle headerCellStyle = wb.createCellStyle();
	HSSFCellStyle headerCellStyle2 = wb.createCellStyle();
	HSSFCellStyle headerCellStyle3 = wb.createCellStyle();
	headerCellStyle3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	HSSFCellStyle dateCellStyle = wb.createCellStyle();
	HSSFCellStyle wrapText = wb.createCellStyle();
	wrapText.setWrapText(true);
	
	DataFormat poiFormat = wb.createDataFormat();
	dateCellStyle.setDataFormat(poiFormat.getFormat("dd-MMM-yyyy"));

	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
	Date date = new Date();
	dateFormat.format(date);
	
	
	Font f = wb.createFont();
	f.setFontHeightInPoints((short) 16);
	headerCellStyle2.setFont(f);
	
	HSSFFont boldFont = wb.createFont();
	boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	headerCellStyle3.setFont(boldFont);
	
	HSSFRow row = sheet.createRow(0);
	HSSFCell cellHeader = row.createCell(3);
	cellHeader.setCellStyle(headerCellStyle2);
	cellHeader.setCellValue(new HSSFRichTextString("Test City Wise Report"));
	
	cellHeader = row.createCell(7);
	cellHeader.setCellStyle(headerCellStyle);

	HSSFRichTextString richString = new HSSFRichTextString("Generated on");
	cellHeader.setCellValue(richString);
	
	cellHeader = row.createCell(9);
	cellHeader.setCellStyle(headerCellStyle);
	cellHeader.setCellValue(dateFormat.format(date));
	
	row = sheet.createRow(2);
	HSSFCell cell = row.createCell(0);
	cell.setCellStyle(headerCellStyle3);
	cell.setCellValue(new HSSFRichTextString("Name of City"));
	
	cell = row.createCell(1);
	cell.setCellStyle(headerCellStyle3);
	cell.setCellValue(new HSSFRichTextString("TET I"));
	
	cell = row.createCell(2);
	cell.setCellStyle(headerCellStyle3);
	cell.setCellValue(new HSSFRichTextString("TET II"));
	
	//cell = row.createCell(3);
//	cell.setCellStyle(headerCellStyle3);
//	cell.setCellValue(new HSSFRichTextString("Assistant (Junior Assistant)"));
	
	/*cell = row.createCell(2);
	cell.setCellStyle(headerCellStyle3);
	cell.setCellValue(new HSSFRichTextString("AM(SYS-DOT NET)"));
	
	cell = row.createCell(3);
	cell.setCellStyle(headerCellStyle3);
	cell.setCellValue(new HSSFRichTextString("AM(SYS-NETWORKING)"));*/
	
//	cell = row.createCell(3);
//	cell.setCellStyle(headerCellStyle3);
//	cell.setCellValue(new HSSFRichTextString("Typist"));
	
	/*cell = row.createCell(5);
	cell.setCellStyle(headerCellStyle3);
	cell.setCellValue(new HSSFRichTextString("MT(HR/ADMIN)"));
	
	cell = row.createCell(6);
	cell.setCellStyle(headerCellStyle3);
	cell.setCellValue(new HSSFRichTextString("MT(F&A)"));*/
	
//	cell = row.createCell(7);
//	cell.setCellStyle(headerCellStyle3);
//	cell.setCellValue(new HSSFRichTextString("Chennai"));
//	
//	cell = row.createCell(8);
//	cell.setCellStyle(headerCellStyle3);
//	cell.setCellValue(new HSSFRichTextString("Dehradun"));
//	
//	cell = row.createCell(9);
//	cell.setCellStyle(headerCellStyle3);
//	cell.setCellValue(new HSSFRichTextString("Guwahati"));
//	
//	cell = row.createCell(10);
//	cell.setCellStyle(headerCellStyle3);
//	cell.setCellValue(new HSSFRichTextString("Hyderabad"));
//	
//	cell = row.createCell(11);
//	cell.setCellStyle(headerCellStyle3);
//	cell.setCellValue(new HSSFRichTextString("Imphal"));
//	
//	cell = row.createCell(12);
//	cell.setCellStyle(headerCellStyle3);
//	cell.setCellValue(new HSSFRichTextString("Jaipur"));
//	
//	cell = row.createCell(13);
//	cell.setCellStyle(headerCellStyle3);
//	cell.setCellValue(new HSSFRichTextString("Jamshedpur"));
//	
//	cell = row.createCell(14);
//	cell.setCellStyle(headerCellStyle3);
//	cell.setCellValue(new HSSFRichTextString("Kolkata"));
//	
//	
//	cell = row.createCell(15);
//	cell.setCellStyle(headerCellStyle3);
//	cell.setCellValue(new HSSFRichTextString("Lucknow"));
//	
//	cell = row.createCell(16);
//	cell.setCellStyle(headerCellStyle3);
//	cell.setCellValue(new HSSFRichTextString("Mumbai"));
//	
//	cell = row.createCell(17);
//	cell.setCellStyle(headerCellStyle3);
//	cell.setCellValue(new HSSFRichTextString("New Delhi"));
//	
//	cell = row.createCell(18);
//	cell.setCellStyle(headerCellStyle3);
//	cell.setCellValue(new HSSFRichTextString("Patna"));
//	
//	cell = row.createCell(19);
//	cell.setCellStyle(headerCellStyle3);
//	cell.setCellValue(new HSSFRichTextString("Pune"));
//	
//	cell = row.createCell(20);
//	cell.setCellStyle(headerCellStyle3);
//	cell.setCellValue(new HSSFRichTextString("Raipur"));
//
//	cell = row.createCell(21);
//	cell.setCellStyle(headerCellStyle3);
//	cell.setCellValue(new HSSFRichTextString("Ranchi"));
//	
//	cell = row.createCell(22);
//	cell.setCellStyle(headerCellStyle3);
//	cell.setCellValue(new HSSFRichTextString("Shillong"));
//	
//	cell = row.createCell(23);
//	cell.setCellStyle(headerCellStyle3);
//	cell.setCellValue(new HSSFRichTextString("Shimala"));
//	
//	cell = row.createCell(24);
//	cell.setCellStyle(headerCellStyle3);
//	cell.setCellValue(new HSSFRichTextString("Srinagar"));
//	
//	cell = row.createCell(25);
//	cell.setCellStyle(headerCellStyle3);
//	cell.setCellValue(new HSSFRichTextString("Trichy"));
//	
//	cell = row.createCell(26);
//	cell.setCellStyle(headerCellStyle3);
//	cell.setCellValue(new HSSFRichTextString("Vizag"));
//	
	cell = row.createCell(3);
	cell.setCellStyle(headerCellStyle3);
	cell.setCellValue(new HSSFRichTextString("Grand Total"));
	

			
	int count = 0;
	SimpleDateFormat simpledateformat = new SimpleDateFormat("dd-MMM-yyyy");
for (CandidateBean candidateDetailsBean : candidateDetailsList) {
		
		if (candidateDetailsBean!=null){
			row = sheet.createRow(count+3);
			cell = row.createCell(0);
			cell.setCellValue(candidateDetailsBean.getOtmPostName());
			cell.setCellStyle(wrapText);
			
		
			cell = row.createCell(1);
			cell.setCellValue(candidateDetailsBean.getAm_java());
			cell.setCellStyle(wrapText);
			
			cell = row.createCell(2);
			cell.setCellValue(candidateDetailsBean.getAm_dotnet());
			
			//cell = row.createCell(3);
			//cell.setCellValue(candidateDetailsBean.getAm_networking());
			
			//cell = row.createCell(4);
			//cell.setCellValue(candidateDetailsBean.getMt_adminhr());

			
			cell = row.createCell(3);
			cell.setCellValue(candidateDetailsBean.getTotal());
			
			/*cell = row.createCell(6);
			cell.setCellValue(candidateDetailsBean.getMt_fa());*/
			
//			cell = row.createCell(7);
//			cell.setCellValue(candidateDetailsBean.getChennai());
//			
//
//			cell = row.createCell(8);
//			cell.setCellValue(candidateDetailsBean.getDehradun());
//			
//
//			
//			cell = row.createCell(9);
//			cell.setCellValue(candidateDetailsBean.getGuwahati());
//			
//
//			cell = row.createCell(10);
//			cell.setCellValue(candidateDetailsBean.getHyderabad());
//			
//			cell = row.createCell(11);
//			cell.setCellValue(candidateDetailsBean.getImphal());
//			
//
//			cell = row.createCell(12);
//			cell.setCellValue(candidateDetailsBean.getJaipur());
//			
//			cell = row.createCell(13);
//			cell.setCellValue(candidateDetailsBean.getJamshedpur());
//			
//			cell = row.createCell(14);
//			cell.setCellValue(candidateDetailsBean.getKolkatta());
//			
//			cell = row.createCell(15);
//			cell.setCellValue(candidateDetailsBean.getLucknow());
//			
//			cell = row.createCell(16);
//			cell.setCellValue(candidateDetailsBean.getMumbai());
//			
//			cell = row.createCell(17);
//			cell.setCellValue(candidateDetailsBean.getNew_delhi());
//			
//			cell = row.createCell(18);
//			cell.setCellValue(candidateDetailsBean.getPatna());
//			
//			cell = row.createCell(19);
//			cell.setCellValue(candidateDetailsBean.getPune());
//			
//			cell = row.createCell(20);
//			cell.setCellValue(candidateDetailsBean.getRaipur());
//			
//			cell = row.createCell(21);
//			cell.setCellValue(candidateDetailsBean.getRanchi());
//			
//			cell = row.createCell(22);
//			cell.setCellValue(candidateDetailsBean.getShillong());
//			
//			cell = row.createCell(23);
//			cell.setCellValue(candidateDetailsBean.getShimla());
//			
//			cell = row.createCell(24);
//			cell.setCellValue(candidateDetailsBean.getSrinagar());
////			
//			cell = row.createCell(25);
//			cell.setCellValue(candidateDetailsBean.getTrichy());
//			
//			cell = row.createCell(26);
//			cell.setCellValue(candidateDetailsBean.getVizag());
//			
//		
			//cell = row.createCell(6);
			//cell.setCellValue(candidateDetailsBean.getTotal());
							
			count++;
		}
	}
	
	return wb;
}


public HSSFWorkbook createWorkbookForCategoryWiseReport(
		List<CandidateBean> candidateDetailsList) throws ParseException {
	HSSFWorkbook wb = new HSSFWorkbook();
	HSSFSheet sheet = wb.createSheet("Category Wise Report");
	
	/**
	 * Setting the width of the first three columns.
	 */
	sheet.setColumnWidth(0, 15000);
	sheet.setColumnWidth(1, 9000);
	sheet.setColumnWidth(2, 7000);
	sheet.setColumnWidth(3, 7000);
	sheet.setColumnWidth(4, 7000);
	sheet.setColumnWidth(5, 7000);
	sheet.setColumnWidth(6, 7000);
	sheet.setColumnWidth(7, 7000);
	sheet.setColumnWidth(8, 7000);
	sheet.setColumnWidth(9, 7000);
	sheet.setColumnWidth(10, 7000);
	sheet.setColumnWidth(11, 7000);
	sheet.setColumnWidth(12, 7000);
	sheet.setColumnWidth(13, 9000);
	sheet.setColumnWidth(14, 9000);
	
	/**
	 * Style for the header cells.
	 */
	
	
	HSSFCellStyle headerCellStyle = wb.createCellStyle();
	HSSFCellStyle headerCellStyle2 = wb.createCellStyle();
	HSSFCellStyle headerCellStyle3 = wb.createCellStyle();
	headerCellStyle3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	HSSFCellStyle dateCellStyle = wb.createCellStyle();
	HSSFCellStyle wrapText = wb.createCellStyle();
	wrapText.setWrapText(true);
	
	DataFormat poiFormat = wb.createDataFormat();
	dateCellStyle.setDataFormat(poiFormat.getFormat("dd-MMM-yyyy"));

	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
	Date date = new Date();
	dateFormat.format(date);
	
	
	Font f = wb.createFont();
	f.setFontHeightInPoints((short) 16);
	headerCellStyle2.setFont(f);
	
	HSSFFont boldFont = wb.createFont();
	boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	headerCellStyle3.setFont(boldFont);
	
	HSSFRow row = sheet.createRow(0);
	HSSFCell cellHeader = row.createCell(2);
	cellHeader.setCellStyle(headerCellStyle2);
	cellHeader.setCellValue(new HSSFRichTextString("Category Wise Report"));
	
	cellHeader = row.createCell(6);
	cellHeader.setCellStyle(headerCellStyle);

	HSSFRichTextString richString = new HSSFRichTextString("Generated on");
	cellHeader.setCellValue(richString);
	
	cellHeader = row.createCell(8);
	cellHeader.setCellStyle(headerCellStyle);
	cellHeader.setCellValue(dateFormat.format(date));
	
	row = sheet.createRow(2);
	
	HSSFCell cell = row.createCell(0);
	cell.setCellStyle(headerCellStyle3);
	cell.setCellValue(new HSSFRichTextString("Name of Post"));
	
	/*cell = row.createCell(1);
	cell.setCellStyle(headerCellStyle3);
	cell.setCellValue(new HSSFRichTextString("General"));*/
	
	cell = row.createCell(1);
	cell.setCellStyle(headerCellStyle3);
	cell.setCellValue(new HSSFRichTextString("OC"));
	
	cell = row.createCell(2);
	cell.setCellStyle(headerCellStyle3);
	cell.setCellValue(new HSSFRichTextString("SC"));
	
	cell = row.createCell(3);
	cell.setCellStyle(headerCellStyle3);
	cell.setCellValue(new HSSFRichTextString("SC (A)"));

	cell = row.createCell(4);
	cell.setCellStyle(headerCellStyle3);
	cell.setCellValue(new HSSFRichTextString("ST"));
	
	cell = row.createCell(5);
	cell.setCellStyle(headerCellStyle3);
	cell.setCellValue(new HSSFRichTextString("MBC / DC"));
	
	cell = row.createCell(6);
	cell.setCellStyle(headerCellStyle3);
	cell.setCellValue(new HSSFRichTextString("BC"));
	
	cell = row.createCell(7);
	cell.setCellStyle(headerCellStyle3);
	cell.setCellValue(new HSSFRichTextString("BCM"));
	
	cell = row.createCell(8);
	cell.setCellStyle(headerCellStyle3);
	cell.setCellValue(new HSSFRichTextString("Total"));
	
			
	int count = 0;
	SimpleDateFormat simpledateformat = new SimpleDateFormat("dd-MMM-yyyy");
	for (CandidateBean candidateDetailsBean : candidateDetailsList) {
		
		if (candidateDetailsBean!=null){
			row = sheet.createRow(count+3);
			cell = row.createCell(0);
			cell.setCellValue(candidateDetailsBean.getOtmPostName());
			
			/*cell = row.createCell(1);
			cell.setCellValue(candidateDetailsBean.getGeneral_unreserved());*/
			
			cell = row.createCell(1);
			cell.setCellValue(candidateDetailsBean.getObc());
			cell.setCellStyle(wrapText);
			
			cell = row.createCell(2);
			cell.setCellValue(candidateDetailsBean.getSc());
			
			cell = row.createCell(3);
			cell.setCellValue(candidateDetailsBean.getSca());
			
			cell = row.createCell(4);
			cell.setCellValue(candidateDetailsBean.getSt());

			cell = row.createCell(5);
			cell.setCellValue(candidateDetailsBean.getMbc());
			
			cell = row.createCell(6);
			cell.setCellValue(candidateDetailsBean.getBc());
			
			cell = row.createCell(7);
			cell.setCellValue(candidateDetailsBean.getBcm());
			
			cell = row.createCell(8);
			cell.setCellValue(candidateDetailsBean.getTotal());
			
			count++;
		}
	}
	
	return wb;
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
