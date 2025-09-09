package com.nseit.generic.models;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class AgencyBean {
	
	private Long candidateCount;
	private Integer requestID;
	
	private String disciplineType;
	private String showExport;
	private String flag;
	
	private Map<Integer, String> discliplineList = new LinkedHashMap<Integer, String>();
	private List<List<String>> excelList = new ArrayList<List<String>>();
	private List<List<String>> exportExcel = new ArrayList<List<String>>();
	
	private int noOfInitiatedCand;
	private int noOfConfirmedCand;
	private int noOfApprovedCand;
	private int noOfRejectedCand;
	private int noOfPendingCand;
	private int noOfAttmpt1Cand;
	private int noOfAttmpt2Cand;

	private int noOfCandPaymentApprovedForDD;
	private int noOfCandPaymentApprovedForCH;
	private int noOfCandPaymentApprovedForEP;
	private int noOfCandPaymentApprovedForNB;
	private int noOfCandPaymentApprovedForCR;
	
	private int noOfCandPaymentRejectedForDD;
	private int noOfCandPaymentRejectedForCH;
	private int noOfCandPaymentRejectedForEP;
	private int noOfCandPaymentRejectedForNB;
	private int noOfCandPaymentRejectedForCR;
	
	private int noOfCandidatesApplicationSubmitted;
	
	private int noOfPaymentPendingForApprovalCandForDD;
	private int noOfPaymentPendingForApprovalCandForCH;
	private int noOfPaymentPendingForApprovalCandForEP;
	private int noOfPaymentPendingForApprovalCandForNB;
	private int noOfPaymentPendingForApprovalCandForCR;
	
	private String ddPaymentStatusFlag;
	private String chPaymentStatusFlag;
	private String epPaymentStatusFlag;
	private String nbPaymentStatusFlag;
	private String crPaymentStatusFlag;
	
	private int noOfPendingForSchedulingCand;
	
	private int noOfCandidatesAdmitCardGeneratedForAttmpt1;
	private int noOfCandidatesAdmitCardGeneratedForAttmpt2;
	
	private int noOfScheduledCand;
	private int noOfNonScheduledCand;
	private int noOfAdmitCardGenCand;
	
	private int totalNoOfCandidate;
	
	 private boolean showPayment;
	 
	 private int noOfPaymentPendingForApprovalCandForCASH;
	 
	 private int noOfNotInitiatedCand;
	 
	 private int applicationSubmittedCount;
	 
	 private int admitCardDownloadCount;
		private int pracTestAttemptCount;
		
		private String cashPaymentStatusFlag;
		
		private Map<Integer, String> pondiReportsList = new LinkedHashMap<Integer, String>();
		 private Map<String, String> testDatList = new LinkedHashMap<String, String>();
		 private Map<String, String> testBatchList = new LinkedHashMap<String, String>();
		 
		 
		 private String pondiReports;
		 private String pondiReportFlag;
		 private String pondiTestDate;
		 private String pondiTestBatch;
		 
		 
	
	public String getPondiReports() {
			return pondiReports;
		}

		public void setPondiReports(String pondiReports) {
			this.pondiReports = pondiReports;
		}

		public String getPondiReportFlag() {
			return pondiReportFlag;
		}

		public void setPondiReportFlag(String pondiReportFlag) {
			this.pondiReportFlag = pondiReportFlag;
		}

		public String getPondiTestDate() {
			return pondiTestDate;
		}

		public void setPondiTestDate(String pondiTestDate) {
			this.pondiTestDate = pondiTestDate;
		}

		public String getPondiTestBatch() {
			return pondiTestBatch;
		}

		public void setPondiTestBatch(String pondiTestBatch) {
			this.pondiTestBatch = pondiTestBatch;
		}

	public Map<Integer, String> getPondiReportsList() {
			return pondiReportsList;
		}

		public void setPondiReportsList(Map<Integer, String> pondiReportsList) {
			this.pondiReportsList = pondiReportsList;
		}

		public Map<String, String> getTestDatList() {
			return testDatList;
		}

		public void setTestDatList(Map<String, String> testDatList) {
			this.testDatList = testDatList;
		}

		public Map<String, String> getTestBatchList() {
			return testBatchList;
		}

		public void setTestBatchList(Map<String, String> testBatchList) {
			this.testBatchList = testBatchList;
		}

	public String getCashPaymentStatusFlag() {
			return cashPaymentStatusFlag;
		}

		public void setCashPaymentStatusFlag(String cashPaymentStatusFlag) {
			this.cashPaymentStatusFlag = cashPaymentStatusFlag;
		}

	public int getNoOfNotInitiatedCand() {
			return noOfNotInitiatedCand;
		}

		public void setNoOfNotInitiatedCand(int noOfNotInitiatedCand) {
			this.noOfNotInitiatedCand = noOfNotInitiatedCand;
		}

		public int getApplicationSubmittedCount() {
			return applicationSubmittedCount;
		}

		public void setApplicationSubmittedCount(int applicationSubmittedCount) {
			this.applicationSubmittedCount = applicationSubmittedCount;
		}

		public int getAdmitCardDownloadCount() {
			return admitCardDownloadCount;
		}

		public void setAdmitCardDownloadCount(int admitCardDownloadCount) {
			this.admitCardDownloadCount = admitCardDownloadCount;
		}

		public int getPracTestAttemptCount() {
			return pracTestAttemptCount;
		}

		public void setPracTestAttemptCount(int pracTestAttemptCount) {
			this.pracTestAttemptCount = pracTestAttemptCount;
		}

	public int getNoOfPaymentPendingForApprovalCandForCASH() {
		return noOfPaymentPendingForApprovalCandForCASH;
	}

	public void setNoOfPaymentPendingForApprovalCandForCASH(int noOfPaymentPendingForApprovalCandForCASH) {
		this.noOfPaymentPendingForApprovalCandForCASH = noOfPaymentPendingForApprovalCandForCASH;
	}

	public boolean isShowPayment() {
		return showPayment;
	}

	public void setShowPayment(boolean showPayment) {
		this.showPayment = showPayment;
	}

	private String testDate;
	private List<String> testDateListForAdminList = new ArrayList<String>();
	
	private String selectedSlot;
	private List<String> testSlot = new ArrayList<String>();
	
	private String disciplineName;
	private Map<Integer, String>disciplineList =  new LinkedHashMap<Integer, String> ();	
	
	
	private String cmbHelpCenter;
	private String newPassword;
	private List<CommonBean> helpCenterList = new ArrayList<CommonBean>();
	
	private String programName;
	
	private Map<Integer, String> programDetailsList =  new LinkedHashMap<Integer, String> ();
	
	
	private Map<Integer, String> categoryList = new LinkedHashMap<Integer, String>();
	
	private Map<Integer, String> registrationStatusList = new LinkedHashMap<Integer, String>();
	
	private Map<Integer, String> interviewTypeList = new LinkedHashMap<Integer, String>();
	
	private String interviewType;
	private String category;
	
	private String registrationStatus;
	
	private List<String> formatList = new ArrayList<String>();
	private String format;
	 
	 
	 private File csvFile;
	 private String csvFileFileName;
	 private String fileName;
	 
	 
	 
	 // for generic mails
	 private String mailContent;
	 private String subject;
	 private String testDateForSlot;
	 private String endDate;
	 private String startDate;
	 private String email;
	 private String mobileNo;
	 
	 private String userId;
	 private String flagForDisplay;
	 private String smsContent;
	 
	 private String registrationFromDate;
	 
	 private String registrationToDate;
	 
	 private String dashboardFromDate;
     
     private String dashboardToDate;
	 
     private String userPk;
	 
	 private Integer importCredentialSuccessCount = 0;
	 
	 private Integer importCredentialFailCount = 0;
	 
	 private Integer importTotalCount = 0;
	 
	 private String importSummary;
	 private String paymentApproveStatus;
	 private String paymentRejectStatus;
	 private String paymentSubmittedStatus;
	 private String attemp1Status;
	 private String attemp2Status;
	 private String noOfCandPaymentSubmitted;
	
	 
	 private String candidateApproveRejectStatus;
	 private String generateAdmitCardStatus;
	 
	 private String searchDashboardResultFlag;
	 
	 private String disciplineId;
	 
	 private List<CandidateBean> candidateDetailsList = new ArrayList<CandidateBean>();
	 
	 private String showCandidateDetailsReport;
	 
	 private Integer candidateDetailsListSize;
	 
	 private String menuKey;
	 
	 private String scheduleMgmtStatus;
	 
	 private Map<Integer, String> interviewLocMap = new LinkedHashMap<Integer, String>();
	 private String interviewVal;
	 
	public AgencyBean() {
	}

	public AgencyBean(Long candidateCount, String disciplineType,
			Map<Integer, String> discliplineList, List<List<String>> excelList) {
		super();
		this.candidateCount = candidateCount;
		this.disciplineType = disciplineType;
		this.discliplineList = discliplineList;
		this.excelList = excelList;
	}

	public Long getCandidateCount() {
		return candidateCount;
	}

	public void setCandidateCount(Long candidateCount) {
		this.candidateCount = candidateCount;
	}

	public String getDisciplineType() {
		return disciplineType;
	}

	public void setDisciplineType(String disciplineType) {
		this.disciplineType = disciplineType;
	}

	public Map<Integer, String> getDiscliplineList() {
		return discliplineList;
	}

	public void setDiscliplineList(Map<Integer, String> discliplineList) {
		this.discliplineList = discliplineList;
	}

	public List<List<String>> getExcelList() {
		return excelList;
	}

	public void setExcelList(List<List<String>> excelList) {
		this.excelList = excelList;
	}

	public Integer getRequestID() {
		return requestID;
	}

	public void setRequestID(Integer requestID) {
		this.requestID = requestID;
	}

	public List<List<String>> getExportExcel() {
		return exportExcel;
	}

	public void setExportExcel(List<List<String>> exportExcel) {
		this.exportExcel = exportExcel;
	}

	public String getShowExport() {
		return showExport;
	}

	public void setShowExport(String showExport) {
		this.showExport = showExport;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getNoOfInitiatedCand()
    {
        return noOfInitiatedCand;
    }

    public void setNoOfInitiatedCand(int noOfInitiatedCand)
    {
        this.noOfInitiatedCand = noOfInitiatedCand;
    }

    public int getNoOfApprovedCand() {
		return noOfApprovedCand;
	}

	public void setNoOfApprovedCand(int noOfApprovedCand) {
		this.noOfApprovedCand = noOfApprovedCand;
	}

	public int getNoOfRejectedCand() {
		return noOfRejectedCand;
	}

	public void setNoOfRejectedCand(int noOfRejectedCand) {
		this.noOfRejectedCand = noOfRejectedCand;
	}

	
	public int getNoOfPendingCand()
    {
        return noOfPendingCand;
    }

    public void setNoOfPendingCand(int noOfPendingCand)
    {
        this.noOfPendingCand = noOfPendingCand;
    }

    public int getNoOfAttmpt1Cand() {
		return noOfAttmpt1Cand;
	}

	public void setNoOfAttmpt1Cand(int noOfAttmpt1Cand) {
		this.noOfAttmpt1Cand = noOfAttmpt1Cand;
	}

	public int getNoOfAttmpt2Cand() {
		return noOfAttmpt2Cand;
	}

	public void setNoOfAttmpt2Cand(int noOfAttmpt2Cand) {
		this.noOfAttmpt2Cand = noOfAttmpt2Cand;
	}

	public int getNoOfConfirmedCand() {
		return noOfConfirmedCand;
	}

	public void setNoOfConfirmedCand(int noOfConfirmedCand) {
		this.noOfConfirmedCand = noOfConfirmedCand;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public List<String> getTestDateListForAdminList() {
		return testDateListForAdminList;
	}

	public void setTestDateListForAdminList(List<String> testDateListForAdminList) {
		this.testDateListForAdminList = testDateListForAdminList;
	}

	public List<String> getTestSlot() {
		return testSlot;
	}

	public void setTestSlot(List<String> testSlot) {
		this.testSlot = testSlot;
	}

	public String getSelectedSlot() {
		return selectedSlot;
	}

	public void setSelectedSlot(String selectedSlot) {
		this.selectedSlot = selectedSlot;
	}

	public String getDisciplineName() {
		return disciplineName;
	}

	public void setDisciplineName(String disciplineName) {
		this.disciplineName = disciplineName;
	}

	public Map<Integer, String> getDisciplineList() {
		return disciplineList;
	}

	public void setDisciplineList(Map<Integer, String> disciplineList) {
		this.disciplineList = disciplineList;
	}

	public Map<Integer, String> getTestCenterMasterDetails() {
		return programDetailsList;
	}

	public void setTestCenterMasterDetails(
			Map<Integer, String> testCenterMasterDetails) {
		this.programDetailsList = testCenterMasterDetails;
	}

	public String getTestMasterValue() {
		return programName;
	}

	public void setTestMasterValue(String testMasterValue) {
		this.programName = testMasterValue;
	}

	public List<String> getFormatList() {
		return formatList;
	}

	public void setFormatList(List<String> formatList) {
		this.formatList = formatList;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public File getCsvFile() {
		return csvFile;
	}

	public void setCsvFile(File csvFile) {
		this.csvFile = csvFile;
	}

	public String getCsvFileFileName() {
		return csvFileFileName;
	}

	public void setCsvFileFileName(String csvFileFileName) {
		this.csvFileFileName = csvFileFileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTestDateForSlot() {
		return testDateForSlot;
	}

	public void setTestDateForSlot(String testDateForSlot) {
		this.testDateForSlot = testDateForSlot;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getFlagForDisplay() {
		return flagForDisplay;
	}

	public void setFlagForDisplay(String flagForDisplay) {
		this.flagForDisplay = flagForDisplay;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	

	public List<CommonBean> getHelpCenterList() {
		return helpCenterList;
	}

	public void setHelpCenterList(
			List<CommonBean> helpCenterList2) {
		helpCenterList = helpCenterList2;
		
	}

	public void setCmbHelpCenter(String cmbHelpCenter) {
		this.cmbHelpCenter = cmbHelpCenter;
	}

	public String getCmbHelpCenter() {
		return cmbHelpCenter;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}
	
	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public Map<Integer, String> getProgramDetailsList() {
		return programDetailsList;
	}

	public void setProgramDetailsList(Map<Integer, String> programDetailsList) {
		this.programDetailsList = programDetailsList;
	}

	public Map<Integer, String> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(Map<Integer, String> categoryList) {
		this.categoryList = categoryList;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Map<Integer, String> getRegistrationStatusList() {
		return registrationStatusList;
	}

	public void setRegistrationStatusList(
			Map<Integer, String> registrationStatusList) {
		this.registrationStatusList = registrationStatusList;
	}

	public String getRegistrationStatus() {
		return registrationStatus;
	}

	public void setRegistrationStatus(String registrationStatus) {
		this.registrationStatus = registrationStatus;
	}

	public String getRegistrationFromDate() {
		return registrationFromDate;
	}

	public void setRegistrationFromDate(String registrationFromDate) {
		this.registrationFromDate = registrationFromDate;
	}

	public String getRegistrationToDate() {
		return registrationToDate;
	}

	public void setRegistrationToDate(String registrationToDate) {
		this.registrationToDate = registrationToDate;
	}

	public Map<Integer, String> getInterviewTypeList() {
		return interviewTypeList;
	}

	public void setInterviewTypeList(Map<Integer, String> interviewTypeList) {
		this.interviewTypeList = interviewTypeList;
	}

	public String getInterviewType() {
		return interviewType;
	}

	public void setInterviewType(String interviewType) {
		this.interviewType = interviewType;
	}

	
	

	public int getNoOfCandidatesApplicationSubmitted() {
		return noOfCandidatesApplicationSubmitted;
	}

	public void setNoOfCandidatesApplicationSubmitted(
			int noOfCandidatesApplicationSubmitted) {
		this.noOfCandidatesApplicationSubmitted = noOfCandidatesApplicationSubmitted;
	}

	public String getUserPk() {
		return userPk;
	}

	public void setUserPk(String userPk) {
		this.userPk = userPk;
	}

	public Integer getImportCredentialSuccessCount() {
		return importCredentialSuccessCount;
	}

	public void setImportCredentialSuccessCount(Integer importCredentialSuccessCount) {
		this.importCredentialSuccessCount = importCredentialSuccessCount;
	}

	public Integer getImportCredentialFailCount() {
		return importCredentialFailCount;
	}

	public void setImportCredentialFailCount(Integer importCredentialFailCount) {
		this.importCredentialFailCount = importCredentialFailCount;
	}

	public String getImportSummary() {
		return importSummary;
	}

	public void setImportSummary(String importSummary) {
		this.importSummary = importSummary;
	}

	public Integer getImportTotalCount() {
		return importTotalCount;
	}

	public void setImportTotalCount(Integer importTotalCount) {
		this.importTotalCount = importTotalCount;
	}

	public String getPaymentApproveStatus() {
		return paymentApproveStatus;
	}

	public void setPaymentApproveStatus(String paymentApproveStatus) {
		this.paymentApproveStatus = paymentApproveStatus;
	}

	public String getPaymentRejectStatus() {
		return paymentRejectStatus;
	}

	public void setPaymentRejectStatus(String paymentRejectStatus) {
		this.paymentRejectStatus = paymentRejectStatus;
	}

	public String getAttemp1Status() {
		return attemp1Status;
	}

	public void setAttemp1Status(String attemp1Status) {
		this.attemp1Status = attemp1Status;
	}

	public String getAttemp2Status() {
		return attemp2Status;
	}

	public void setAttemp2Status(String attemp2Status) {
		this.attemp2Status = attemp2Status;
	}

	public String getNoOfCandPaymentSubmitted() {
		return noOfCandPaymentSubmitted;
	}

	public void setNoOfCandPaymentSubmitted(String noOfCandPaymentSubmitted) {
		this.noOfCandPaymentSubmitted = noOfCandPaymentSubmitted;
	}

	public String getPaymentSubmittedStatus() {
		return paymentSubmittedStatus;
	}

	public void setPaymentSubmittedStatus(String paymentSubmittedStatus) {
		this.paymentSubmittedStatus = paymentSubmittedStatus;
	}

	public int getNoOfCandidatesAdmitCardGeneratedForAttmpt1() {
		return noOfCandidatesAdmitCardGeneratedForAttmpt1;
	}

	public void setNoOfCandidatesAdmitCardGeneratedForAttmpt1(
			int noOfCandidatesAdmitCardGeneratedForAttmpt1) {
		this.noOfCandidatesAdmitCardGeneratedForAttmpt1 = noOfCandidatesAdmitCardGeneratedForAttmpt1;
	}

	public int getNoOfCandidatesAdmitCardGeneratedForAttmpt2() {
		return noOfCandidatesAdmitCardGeneratedForAttmpt2;
	}

	public void setNoOfCandidatesAdmitCardGeneratedForAttmpt2(
			int noOfCandidatesAdmitCardGeneratedForAttmpt2) {
		this.noOfCandidatesAdmitCardGeneratedForAttmpt2 = noOfCandidatesAdmitCardGeneratedForAttmpt2;
	}

	public String getCandidateApproveRejectStatus() {
		return candidateApproveRejectStatus;
	}

	public void setCandidateApproveRejectStatus(String candidateApproveRejectStatus) {
		this.candidateApproveRejectStatus = candidateApproveRejectStatus;
	}

	public String getGenerateAdmitCardStatus() {
		return generateAdmitCardStatus;
	}

	public void setGenerateAdmitCardStatus(String generateAdmitCardStatus) {
		this.generateAdmitCardStatus = generateAdmitCardStatus;
	}

    public String getDashboardFromDate()
    {
        return dashboardFromDate;
    }

    public void setDashboardFromDate(String dashboardFromDate)
    {
        this.dashboardFromDate = dashboardFromDate;
    }

    public String getDashboardToDate()
    {
        return dashboardToDate;
    }

    public void setDashboardToDate(String dashboardToDate)
    {
        this.dashboardToDate = dashboardToDate;
    }

    public String getSearchDashboardResultFlag()
    {
        return searchDashboardResultFlag;
    }

    public void setSearchDashboardResultFlag(String searchDashboardResultFlag)
    {
        this.searchDashboardResultFlag = searchDashboardResultFlag;
    }

    public int getNoOfScheduledCand()
    {
        return noOfScheduledCand;
    }

    public void setNoOfScheduledCand(int noOfScheduledCand)
    {
        this.noOfScheduledCand = noOfScheduledCand;
    }

    public int getNoOfNonScheduledCand()
    {
        return noOfNonScheduledCand;
    }

    public void setNoOfNonScheduledCand(int noOfNonScheduledCand)
    {
        this.noOfNonScheduledCand = noOfNonScheduledCand;
    }

    public int getNoOfAdmitCardGenCand()
    {
        return noOfAdmitCardGenCand;
    }

    public void setNoOfAdmitCardGenCand(int noOfAdmitCardGenCand)
    {
        this.noOfAdmitCardGenCand = noOfAdmitCardGenCand;
    }

    public String getDisciplineId()
    {
        return disciplineId;
    }

    public void setDisciplineId(String disciplineId)
    {
        this.disciplineId = disciplineId;
    }

    public int getNoOfPendingForSchedulingCand()
    {
        return noOfPendingForSchedulingCand;
    }

    public void setNoOfPendingForSchedulingCand(int noOfPendingForSchedulingCand)
    {
        this.noOfPendingForSchedulingCand = noOfPendingForSchedulingCand;
    }

    public List<CandidateBean> getCandidateDetailsList()
    {
        return candidateDetailsList;
    }

    public void setCandidateDetailsList(List<CandidateBean> candidateDetailsList)
    {
        this.candidateDetailsList = candidateDetailsList;
    }

    public String getShowCandidateDetailsReport()
    {
        return showCandidateDetailsReport;
    }

    public void setShowCandidateDetailsReport(String showCandidateDetailsReport)
    {
        this.showCandidateDetailsReport = showCandidateDetailsReport;
    }

    public int getTotalNoOfCandidate()
    {
        return totalNoOfCandidate;
    }

    public void setTotalNoOfCandidate(int totalNoOfCandidate)
    {
        this.totalNoOfCandidate = totalNoOfCandidate;
    }

    public int getNoOfCandPaymentApprovedForDD()
    {
        return noOfCandPaymentApprovedForDD;
    }

    public void setNoOfCandPaymentApprovedForDD(int noOfCandPaymentApprovedForDD)
    {
        this.noOfCandPaymentApprovedForDD = noOfCandPaymentApprovedForDD;
    }

    public int getNoOfCandPaymentApprovedForCH()
    {
        return noOfCandPaymentApprovedForCH;
    }

    public void setNoOfCandPaymentApprovedForCH(int noOfCandPaymentApprovedForCH)
    {
        this.noOfCandPaymentApprovedForCH = noOfCandPaymentApprovedForCH;
    }

    public int getNoOfCandPaymentApprovedForNB()
    {
        return noOfCandPaymentApprovedForNB;
    }

    public void setNoOfCandPaymentApprovedForNB(int noOfCandPaymentApprovedForNB)
    {
        this.noOfCandPaymentApprovedForNB = noOfCandPaymentApprovedForNB;
    }

    public int getNoOfCandPaymentRejectedForDD()
    {
        return noOfCandPaymentRejectedForDD;
    }

    public void setNoOfCandPaymentRejectedForDD(int noOfCandPaymentRejectedForDD)
    {
        this.noOfCandPaymentRejectedForDD = noOfCandPaymentRejectedForDD;
    }

    public int getNoOfCandPaymentRejectedForCH()
    {
        return noOfCandPaymentRejectedForCH;
    }

    public void setNoOfCandPaymentRejectedForCH(int noOfCandPaymentRejectedForCH)
    {
        this.noOfCandPaymentRejectedForCH = noOfCandPaymentRejectedForCH;
    }

    public int getNoOfCandPaymentRejectedForNB()
    {
        return noOfCandPaymentRejectedForNB;
    }

    public void setNoOfCandPaymentRejectedForNB(int noOfCandPaymentRejectedForNB)
    {
        this.noOfCandPaymentRejectedForNB = noOfCandPaymentRejectedForNB;
    }

    public int getNoOfPaymentPendingForApprovalCandForDD()
    {
        return noOfPaymentPendingForApprovalCandForDD;
    }

    public void setNoOfPaymentPendingForApprovalCandForDD(int noOfPaymentPendingForApprovalCandForDD)
    {
        this.noOfPaymentPendingForApprovalCandForDD = noOfPaymentPendingForApprovalCandForDD;
    }

    public int getNoOfPaymentPendingForApprovalCandForCH()
    {
        return noOfPaymentPendingForApprovalCandForCH;
    }

    public void setNoOfPaymentPendingForApprovalCandForCH(int noOfPaymentPendingForApprovalCandForCH)
    {
        this.noOfPaymentPendingForApprovalCandForCH = noOfPaymentPendingForApprovalCandForCH;
    }

    public int getNoOfPaymentPendingForApprovalCandForNB()
    {
        return noOfPaymentPendingForApprovalCandForNB;
    }

    public void setNoOfPaymentPendingForApprovalCandForNB(int noOfPaymentPendingForApprovalCandForNB)
    {
        this.noOfPaymentPendingForApprovalCandForNB = noOfPaymentPendingForApprovalCandForNB;
    }

    public int getNoOfCandPaymentApprovedForCR()
    {
        return noOfCandPaymentApprovedForCR;
    }

    public void setNoOfCandPaymentApprovedForCR(int noOfCandPaymentApprovedForCR)
    {
        this.noOfCandPaymentApprovedForCR = noOfCandPaymentApprovedForCR;
    }

    public int getNoOfCandPaymentRejectedForCR()
    {
        return noOfCandPaymentRejectedForCR;
    }

    public void setNoOfCandPaymentRejectedForCR(int noOfCandPaymentRejectedForCR)
    {
        this.noOfCandPaymentRejectedForCR = noOfCandPaymentRejectedForCR;
    }

    public int getNoOfPaymentPendingForApprovalCandForCR()
    {
        return noOfPaymentPendingForApprovalCandForCR;
    }

    public void setNoOfPaymentPendingForApprovalCandForCR(int noOfPaymentPendingForApprovalCandForCR)
    {
        this.noOfPaymentPendingForApprovalCandForCR = noOfPaymentPendingForApprovalCandForCR;
    }

    public String getDdPaymentStatusFlag()
    {
        return ddPaymentStatusFlag;
    }

    public void setDdPaymentStatusFlag(String ddPaymentStatusFlag)
    {
        this.ddPaymentStatusFlag = ddPaymentStatusFlag;
    }

    public String getChPaymentStatusFlag()
    {
        return chPaymentStatusFlag;
    }

    public void setChPaymentStatusFlag(String chPaymentStatusFlag)
    {
        this.chPaymentStatusFlag = chPaymentStatusFlag;
    }

    public String getNbPaymentStatusFlag()
    {
        return nbPaymentStatusFlag;
    }

    public void setNbPaymentStatusFlag(String nbPaymentStatusFlag)
    {
        this.nbPaymentStatusFlag = nbPaymentStatusFlag;
    }

    public String getCrPaymentStatusFlag()
    {
        return crPaymentStatusFlag;
    }

    public void setCrPaymentStatusFlag(String crPaymentStatusFlag)
    {
        this.crPaymentStatusFlag = crPaymentStatusFlag;
    }

    public Integer getCandidateDetailsListSize()
    {
        return candidateDetailsListSize;
    }

    public void setCandidateDetailsListSize(Integer candidateDetailsListSize)
    {
        this.candidateDetailsListSize = candidateDetailsListSize;
    }

    public String getMenuKey()
    {
        return menuKey;
    }

    public void setMenuKey(String menuKey)
    {
        this.menuKey = menuKey;
    }

    public String getScheduleMgmtStatus()
    {
        return scheduleMgmtStatus;
    }

    public void setScheduleMgmtStatus(String scheduleMgmtStatus)
    {
        this.scheduleMgmtStatus = scheduleMgmtStatus;
    }

	public Map<Integer, String> getInterviewLocMap() {
		return interviewLocMap;
	}

	public void setInterviewLocMap(Map<Integer, String> interviewLocMap) {
		this.interviewLocMap = interviewLocMap;
	}

	/**
	 * @return the interviewVal
	 */
	public String getInterviewVal() {
		return interviewVal;
	}

	/**
	 * @param interviewVal the interviewVal to set
	 */
	public void setInterviewVal(String interviewVal) {
		this.interviewVal = interviewVal;
	}

	public void setNoOfCandPaymentApprovedForEP(int noOfCandPaymentApprovedForEP) {
		this.noOfCandPaymentApprovedForEP = noOfCandPaymentApprovedForEP;
	}

	public int getNoOfCandPaymentApprovedForEP() {
		return noOfCandPaymentApprovedForEP;
	}

	public void setNoOfCandPaymentRejectedForEP(int noOfCandPaymentRejectedForEP) {
		this.noOfCandPaymentRejectedForEP = noOfCandPaymentRejectedForEP;
	}

	public int getNoOfCandPaymentRejectedForEP() {
		return noOfCandPaymentRejectedForEP;
	}

	public void setNoOfPaymentPendingForApprovalCandForEP(
			int noOfPaymentPendingForApprovalCandForEP) {
		this.noOfPaymentPendingForApprovalCandForEP = noOfPaymentPendingForApprovalCandForEP;
	}

	public int getNoOfPaymentPendingForApprovalCandForEP() {
		return noOfPaymentPendingForApprovalCandForEP;
	}

	public void setEpPaymentStatusFlag(String epPaymentStatusFlag) {
		this.epPaymentStatusFlag = epPaymentStatusFlag;
	}

	public String getEpPaymentStatusFlag() {
		return epPaymentStatusFlag;
	}    
	
    
    
    
}
