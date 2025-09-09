package com.nseit.generic.models;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SettingsBean {
	private Map<Integer, String> disciplineList = new LinkedHashMap<Integer, String>();
	private List<AcademicDetailsBean> getParameterListForEmailSms = new ArrayList<AcademicDetailsBean>();
	
	private String activityId;
	private String activityDescription;
	private String testId;
	
	private Timestamp startDate;
	private Timestamp endDate;
	private String testFk;	
	private String stage;
	
	private String regStartDate;
	private String regEndDate;
	
	private String admitCardStartDate;
    private String admitCardEndDate;
    
	private String enrollmentStartDate;
	private String enrollmentEndDate;

	private String paymentStartDate;
	private String paymentEndDate;
	
	private String retestStartDate;
	private String retestEndDate;
	
	private String regDateFlag;
	private String regDateStatus;
	private String admitcardDateFlag;
	
	private String enrollDateFlag;
	private String enrollDateStatus;
	
	private String paymentDateFlag;
	private String paymentDateStatus;
	
	private String reTestDateFlag;
	private String reTestDateStatus;
	
	private String registrationStage;
	private String enrollmentStage;
	private String paymentStage;
	private String retestStage; 
	private String admitCardStage; 
	
	private String stageVal;
	private String stageValFromDb;

	private String disciplineId;
	
	private String desc;
	private String admitCardDateStatus;
	
	// for upload /download
	private String module;
	private File trainingUploadFile;
	private File faqUploadFile;
	private File testInstructionUploadFile;
	private File eligibilityCriteriaUploadFile;

	private String test;
	
	private File file1;
	private File file2;
	private File file3;
	private File file4;
	
	// for training details
	private String pathPk;
	private String pathValue;
	
	private Map<Integer,String> activityDetailsMap =new LinkedHashMap<Integer,String>();
	private String activityBox; 
	private String emailChkBox;
	private String smsChkBox;
	private String emailSmsSubject;
	private String emailSmsCCAddress;
	private String smsContent;
	private String emailContent;
	
	private String fileName;	
	private String fileNameEligibility;
	private String fileNameTestInstr;
	private String fileNameFaq;
	
	private String a;
	private String startDateDB;
	private String endDateDB;
	
	private String newRegStartDate;
	private String newRegEndDate;

	private String disciplineType;
	private String mailChkBoxFlag;
	private String smsChkBoxFlag;
	/**
	 * @author Pankaj Sh
	 * @return
	 */
	private String appFormStartDt;
	private String appFormEndDt;
	private String aprvCandStartDt;
	private String aprvCandEndDt;
	private String otbsStartDt;
	private String otbsEndDt;
	private String attempt1StartDt;
	private String attempt1EndDt;
	private String attempt2StartDt;
	private String attempt2EndDt;
	private Integer dateDefinitionType;
	private String otbsStatus;
	private String attemp1;
	private String attemp2;
	private String paymentStatus;
	private String candidateApproveRejectStatus;

	public Map<Integer, String> getDisciplineList() {
		return disciplineList;
	}
	
	public void setDisciplineList(Map<Integer, String> disciplineList) {
		this.disciplineList = disciplineList;
	}
	
	public List<AcademicDetailsBean> getGetParameterListForEmailSms() {
		return getParameterListForEmailSms;
	}
	
	public void setGetParameterListForEmailSms(
			List<AcademicDetailsBean> getParameterListForEmailSms) {
		this.getParameterListForEmailSms = getParameterListForEmailSms;
	}
	
	public String getActivityId() {
		return activityId;
	}
	
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	public String getActivityDescription() {
		return activityDescription;
	}
	
	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getTestFk() {
		return testFk;
	}

	public void setTestFk(String testFk) {
		this.testFk = testFk;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getRegStartDate() {
		return regStartDate;
	}

	public void setRegStartDate(String regStartDate) {
		this.regStartDate = regStartDate;
	}

	public String getRegEndDate() {
		return regEndDate;
	}

	public void setRegEndDate(String regEndDate) {
		this.regEndDate = regEndDate;
	}

	public String getEnrollmentStartDate() {
		return enrollmentStartDate;
	}

	public void setEnrollmentStartDate(String enrollmentStartDate) {
		this.enrollmentStartDate = enrollmentStartDate;
	}

	public String getEnrollmentEndDate() {
		return enrollmentEndDate;
	}

	public void setEnrollmentEndDate(String enrollmentEndDate) {
		this.enrollmentEndDate = enrollmentEndDate;
	}

	public String getPaymentStartDate() {
		return paymentStartDate;
	}

	public void setPaymentStartDate(String paymentStartDate) {
		this.paymentStartDate = paymentStartDate;
	}

	public String getPaymentEndDate() {
		return paymentEndDate;
	}

	public void setPaymentEndDate(String paymentEndDate) {
		this.paymentEndDate = paymentEndDate;
	}

	public String getRetestStartDate() {
		return retestStartDate;
	}

	public void setRetestStartDate(String retestStartDate) {
		this.retestStartDate = retestStartDate;
	}

	public String getRetestEndDate() {
		return retestEndDate;
	}

	public void setRetestEndDate(String retestEndDate) {
		this.retestEndDate = retestEndDate;
	}



	public String getRegDateFlag() {
		return regDateFlag;
	}



	public void setRegDateFlag(String regDateFlag) {
		this.regDateFlag = regDateFlag;
	}



	public String getRegDateStatus() {
		return regDateStatus;
	}



	public void setRegDateStatus(String regDateStatus) {
		this.regDateStatus = regDateStatus;
	}



	public String getEnrollDateFlag() {
		return enrollDateFlag;
	}



	public void setEnrollDateFlag(String enrollDateFlag) {
		this.enrollDateFlag = enrollDateFlag;
	}



	public String getEnrollDateStatus() {
		return enrollDateStatus;
	}



	public void setEnrollDateStatus(String enrollDateStatus) {
		this.enrollDateStatus = enrollDateStatus;
	}



	public String getPaymentDateFlag() {
		return paymentDateFlag;
	}



	public void setPaymentDateFlag(String paymentDateFlag) {
		this.paymentDateFlag = paymentDateFlag;
	}



	public String getPaymentDateStatus() {
		return paymentDateStatus;
	}



	public void setPaymentDateStatus(String paymentDateStatus) {
		this.paymentDateStatus = paymentDateStatus;
	}



	public String getReTestDateFlag() {
		return reTestDateFlag;
	}



	public void setReTestDateFlag(String reTestDateFlag) {
		this.reTestDateFlag = reTestDateFlag;
	}



	public String getReTestDateStatus() {
		return reTestDateStatus;
	}



	public void setReTestDateStatus(String reTestDateStatus) {
		this.reTestDateStatus = reTestDateStatus;
	}



	public String getRegistrationStage() {
		return registrationStage;
	}



	public void setRegistrationStage(String registrationStage) {
		this.registrationStage = registrationStage;
	}



	public String getEnrollmentStage() {
		return enrollmentStage;
	}



	public void setEnrollmentStage(String enrollmentStage) {
		this.enrollmentStage = enrollmentStage;
	}



	public String getPaymentStage() {
		return paymentStage;
	}



	public void setPaymentStage(String paymentStage) {
		this.paymentStage = paymentStage;
	}



	public String getRetestStage() {
		return retestStage;
	}



	public void setRetestStage(String retestStage) {
		this.retestStage = retestStage;
	}



	public String getStageVal() {
		return stageVal;
	}



	public void setStageVal(String stageVal) {
		this.stageVal = stageVal;
	}



	public String getStageValFromDb() {
		return stageValFromDb;
	}



	public void setStageValFromDb(String stageValFromDb) {
		this.stageValFromDb = stageValFromDb;
	}



	public String getDesc() {
		return desc;
	}



	public void setDesc(String desc) {
		this.desc = desc;
	}



	public String getModule() {
		return module;
	}



	public void setModule(String module) {
		this.module = module;
	}



	public File getTrainingUploadFile() {
		return trainingUploadFile;
	}



	public void setTrainingUploadFile(File trainingUploadFile) {
		this.trainingUploadFile = trainingUploadFile;
	}



	public File getFaqUploadFile() {
		return faqUploadFile;
	}



	public void setFaqUploadFile(File faqUploadFile) {
		this.faqUploadFile = faqUploadFile;
	}



	public File getTestInstructionUploadFile() {
		return testInstructionUploadFile;
	}



	public void setTestInstructionUploadFile(File testInstructionUploadFile) {
		this.testInstructionUploadFile = testInstructionUploadFile;
	}



	public File getEligibilityCriteriaUploadFile() {
		return eligibilityCriteriaUploadFile;
	}



	public void setEligibilityCriteriaUploadFile(File eligibilityCriteriaUploadFile) {
		this.eligibilityCriteriaUploadFile = eligibilityCriteriaUploadFile;
	}



	public String getTest() {
		return test;
	}



	public void setTest(String test) {
		this.test = test;
	}

	public File getFile1() {
		return file1;
	}



	public void setFile1(File file1) {
		this.file1 = file1;
	}


	public File getFile2() {
		return file2;
	}



	public void setFile2(File file2) {
		this.file2 = file2;
	}



	public File getFile4() {
		return file4;
	}



	public File getFile3() {
		return file3;
	}



	public void setFile3(File file3) {
		this.file3 = file3;
	}



	public File ge4() {
		return file4;
	}



	public void setFile4(File file4) {
		this.file4 = file4;
	}



	public String getPathPk() {
		return pathPk;
	}



	public void setPathPk(String pathPk) {
		this.pathPk = pathPk;
	}



	public String getPathValue() {
		return pathValue;
	}



	public void setPathValue(String pathValue) {
		this.pathValue = pathValue;
	}





	public String getDisciplineId() {
		return disciplineId;
	}



	public void setDisciplineId(String disciplineId) {
		this.disciplineId = disciplineId;
	}



	public String getActivityBox() {
		return activityBox;
	}



	public void setActivityBox(String activityBox) {
		this.activityBox = activityBox;
	}



	public String getEmailChkBox() {
		return emailChkBox;
	}



	public void setEmailChkBox(String emailChkBox) {
		this.emailChkBox = emailChkBox;
	}



	public String getSmsChkBox() {
		return smsChkBox;
	}



	public void setSmsChkBox(String smsChkBox) {
		this.smsChkBox = smsChkBox;
	}



	public String getEmailSmsSubject() {
		return emailSmsSubject;
	}



	public void setEmailSmsSubject(String emailSmsSubject) {
		this.emailSmsSubject = emailSmsSubject;
	}



	public String getEmailSmsCCAddress() {
		return emailSmsCCAddress;
	}



	public void setEmailSmsCCAddress(String emailSmsCCAddress) {
		this.emailSmsCCAddress = emailSmsCCAddress;
	}



	public String getSmsContent() {
		return smsContent;
	}



	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}



	public String getEmailContent() {
		return emailContent;
	}



	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}



	public String getFileName() {
		return fileName;
	}



	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



    public String getAdmitCardDateStatus()
    {
        return admitCardDateStatus;
    }



    public void setAdmitCardDateStatus(String admitCardDateStatus)
    {
        this.admitCardDateStatus = admitCardDateStatus;
    }



    public String getAdmitCardStage()
    {
        return admitCardStage;
    }



    public void setAdmitCardStage(String admitCardStage)
    {
        this.admitCardStage = admitCardStage;
    }



    public String getAdmitcardDateFlag()
    {
        return admitcardDateFlag;
    }



    public void setAdmitcardDateFlag(String admitcardDateFlag)
    {
        this.admitcardDateFlag = admitcardDateFlag;
    }



    public String getAdmitCardStartDate()
    {
        return admitCardStartDate;
    }



    public void setAdmitCardStartDate(String admitCardStartDate)
    {
        this.admitCardStartDate = admitCardStartDate;
    }



    public String getAdmitCardEndDate()
    {
        return admitCardEndDate;
    }



    public void setAdmitCardEndDate(String admitCardEndDate)
    {
        this.admitCardEndDate = admitCardEndDate;
    }



	public String getStartDateDB() {
		return startDateDB;
	}



	public void setStartDateDB(String startDateDB) {
		this.startDateDB = startDateDB;
	}



	public String getEndDateDB() {
		return endDateDB;
	}



	public void setEndDateDB(String endDateDB) {
		this.endDateDB = endDateDB;
	}



	public Map<Integer, String> getActivityDetailsMap() {
		return activityDetailsMap;
	}



	public void setActivityDetailsMap(Map<Integer, String> activityDetailsMap) {
		this.activityDetailsMap = activityDetailsMap;
	}



	public String getFileNameEligibility() {
		return fileNameEligibility;
	}



	public void setFileNameEligibility(String fileNameEligibility) {
		this.fileNameEligibility = fileNameEligibility;
	}



	public String getFileNameTestInstr() {
		return fileNameTestInstr;
	}



	public void setFileNameTestInstr(String fileNameTestInstr) {
		this.fileNameTestInstr = fileNameTestInstr;
	}



	public String getFileNameFaq() {
		return fileNameFaq;
	}



	public void setFileNameFaq(String fileNameFaq) {
		this.fileNameFaq = fileNameFaq;
	}



	public String getA() {
		return a;
	}



	public void setA(String a) {
		this.a = a;
	}



	public String getNewRegStartDate() {
		return newRegStartDate;
	}



	public void setNewRegStartDate(String newRegStartDate) {
		this.newRegStartDate = newRegStartDate;
	}



	public String getNewRegEndDate() {
		return newRegEndDate;
	}



	public void setNewRegEndDate(String newRegEndDate) {
		this.newRegEndDate = newRegEndDate;
	}



	public String getDisciplineType() {
		return disciplineType;
	}



	public void setDisciplineType(String disciplineType) {
		this.disciplineType = disciplineType;
	}



	public String getMailChkBoxFlag() {
		return mailChkBoxFlag;
	}



	public void setMailChkBoxFlag(String mailChkBoxFlag) {
		this.mailChkBoxFlag = mailChkBoxFlag;
	}



	public String getSmsChkBoxFlag() {
		return smsChkBoxFlag;
	}



	public void setSmsChkBoxFlag(String smsChkBoxFlag) {
		this.smsChkBoxFlag = smsChkBoxFlag;
	}



	public String getAppFormStartDt() {
		return appFormStartDt;
	}



	public void setAppFormStartDt(String appFormStartDt) {
		this.appFormStartDt = appFormStartDt;
	}



	public String getAppFormEndDt() {
		return appFormEndDt;
	}



	public void setAppFormEndDt(String appFormEndDt) {
		this.appFormEndDt = appFormEndDt;
	}



	public String getAprvCandStartDt() {
		return aprvCandStartDt;
	}



	public void setAprvCandStartDt(String aprvCandStartDt) {
		this.aprvCandStartDt = aprvCandStartDt;
	}



	public String getAprvCandEndDt() {
		return aprvCandEndDt;
	}



	public void setAprvCandEndDt(String aprvCandEndDt) {
		this.aprvCandEndDt = aprvCandEndDt;
	}



	public String getOtbsStartDt() {
		return otbsStartDt;
	}



	public void setOtbsStartDt(String otbsStartDt) {
		this.otbsStartDt = otbsStartDt;
	}



	public String getOtbsEndDt() {
		return otbsEndDt;
	}
	public void setOtbsEndDt(String otbsEndDt) {
		this.otbsEndDt = otbsEndDt;
	}
	public String getAttempt1StartDt() {
		return attempt1StartDt;
	}
	public void setAttempt1StartDt(String attempt1StartDt) {
		this.attempt1StartDt = attempt1StartDt;
	}
	public String getAttempt1EndDt() {
		return attempt1EndDt;
	}
	public void setAttempt1EndDt(String attempt1EndDt) {
		this.attempt1EndDt = attempt1EndDt;
	}
	public String getAttempt2StartDt() {
		return attempt2StartDt;
	}
	public void setAttempt2StartDt(String attempt2StartDt) {
		this.attempt2StartDt = attempt2StartDt;
	}
	public String getAttempt2EndDt() {
		return attempt2EndDt;
	}
	public void setAttempt2EndDt(String attempt2EndDt) {
		this.attempt2EndDt = attempt2EndDt;
	}
	public Integer getDateDefinitionType() {
		return dateDefinitionType;
	}
	public void setDateDefinitionType(Integer dateDefinitionType) {
		this.dateDefinitionType = dateDefinitionType;
	}
	public String getOtbsStatus() {
		return otbsStatus;
	}
	public void setOtbsStatus(String otbsStatus) {
		this.otbsStatus = otbsStatus;
	}

	public String getAttemp1() {
		return attemp1;
	}

	public void setAttemp1(String attemp1) {
		this.attemp1 = attemp1;
	}

	public String getAttemp2() {
		return attemp2;
	}

	public void setAttemp2(String attemp2) {
		this.attemp2 = attemp2;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getCandidateApproveRejectStatus() {
		return candidateApproveRejectStatus;
	}

	public void setCandidateApproveRejectStatus(String candidateApproveRejectStatus) {
		this.candidateApproveRejectStatus = candidateApproveRejectStatus;
	}
}
