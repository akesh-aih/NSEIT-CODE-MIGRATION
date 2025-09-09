package com.nseit.generic.models;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nseit.payment.models.OesPaymentDetails;

	
public class CandidateMgmtBean {
	private List<CommonBean> disciplineList = new ArrayList<CommonBean>();
	private List<CommonBean> paymentModeReportList = new ArrayList<CommonBean>();
	private Map<Integer, String> disciplineListMap = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> testCenterListMap = new LinkedHashMap<Integer, String>();
	private List<String> attemptsList = new ArrayList<String>();
	private String attemptVal;
	
	private String testCenterVal;
	private List<String> paymentModeList;
	private String disciplineId;
	private String fromDate; 
	private String toDate; 
	private String paymentMode;
	private String testCenterId;
	private String examDate;
	private String timeSlotId;
	private String eligibleListId;
	
	private Long candidateImageStatus;
	private List<PaymentApprovalBean> ddPaymentApprovalList ;
	private List<PaymentApprovalBean> chalanPaymentApprovalList;
	private List<EnrollmentBean> eligibleNonElibileList ;
	private String attachmentSignature;
	private String attachmentPhoto;
	private List<CommonBean> testCenterList ;
	private List<String> timesSlotList = new ArrayList<String>();
	private List<CommonBean> dateList = new ArrayList<CommonBean>();
	private List<String> eligibleList = new ArrayList<String>();
	private List<String> testDateList = new ArrayList<String>();
	private List<EnrollmentBean> enrollmentDetailsList =new ArrayList<EnrollmentBean>();
	private Long enrollemntId;
	private List<String> paymentCheckedValue;
	private List<String> remarksValue;
	private String candidateImagePK;
	private String enrollmentDate;
	private String remarks; 
	private String enrollmentId;
 
	private List<EnrollmentBean> admitCardList = new ArrayList<EnrollmentBean>();
	private List<HallTicketBean> candidateListForAdmitCard = new ArrayList<HallTicketBean>();
	private List<String> genderList = new ArrayList<String>();
	private List<CommonBean> cityList = new ArrayList<CommonBean>();
	private List<CommonBean> altCityList = new ArrayList<CommonBean>();
	private List<CommonBean> statList = new ArrayList<CommonBean>();
	private List<PaymentApprovalBean> enrollApprovalList = new ArrayList<PaymentApprovalBean>();
	 private InputStream inputStreamForImage;
	 private InputStream inputStreamForSignature;
	 private List<String> userPKForAdmitCard = new ArrayList<String>();
	 private List<String> enrollmentPKForAdmitCard = new ArrayList<String>();
	 private List<Long> userFKForCandidate = new ArrayList<Long>();
	 
	 
	 
	private String userPK;
	private String title;
	private String firstName;
	private String lastName; 
	private String candidateCategory;
	private String dateOfBirth;
	private String gender;
	private String eligible;
	private String  reason ;
	private String 	IpAddress;
	private String address;
	private String AlternateAddress;
	private String cityFK;
	private String stateFK;
	private String countryFK;
	private String alternateCityFK;
	private String alternateStateFK;
	private String alternateCountryFK;
	private String emailAddress;
	private String alternateEmailAddress;
	private String phoneNO;
	private String mobileNO;
	private String stage;
	private String paymentStatus;
	private String selectedForInterview;
	private String selected;
	private String userType; 
	private String status;
	private String fatherName;
	private String stateListId;
	private String alternateStateListId;
	private String cityListId;
	private String alternateCityListId;
	
	
	private String pincode = "";
	private String alternatePincode = "";	
	private String admitCardDisplayFlag; 
	
	private String eligibleDisplayFlag;
	private String enrollDisplayFlag;
	
	
	private String enrollmentIdIsNull;
	private String admitCardHeaderDisplayFlag;
	
	
	
	
	// for gcet
	private String remarksForCandidate;
	private String userPrimaryKey;
	private String approveFlag;
	private String userName;
	private String userId;
	
	private String candidateName;
	private String candidateDateOfBirth;
	private String candidateGender;
	private String candidateTestDate;
	private String candidateTestStartTime;
	private String candidateTestEndTime;
	private String candidateTestCenterAddress;
	private String hallticketDisplayFlag;
	private String userIdForHallticket;
	private List<CandidateMgmtBean> hallTicketDetails;
	private String testName;
	private String testCenterName;
	private List<CandidateMgmtBean> candidateDetailsList;
	private String regDtlsDisplayFlag;  
	private Map<Integer, String> discliplineList = new LinkedHashMap<Integer, String>();
	private String disciplineType;
	private Map<Integer, String> testCenterMasterDetails = new LinkedHashMap<Integer, String>();
	private String testCenterValue;
	private String testDates;
	private String testDateForSlot;
	private String finalTestSlots;
	
	
	private String testDateSelected;
	private String testSlotSelected;
	
	private Map<Integer, String> testDateListForAdmin = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> testSlotListForAdmin = new LinkedHashMap<Integer, String>();
	
	
	private List<String> testDateListForAdminList = new ArrayList<String>();
	private List<String> testSlotListForAdminList = new ArrayList<String>();
	
	private String firstAttemptFlag;
	private String secondAttemptFlag;
	
	private String hallTicket2Disp;
	private String hallTicket1Disp;
	private String attempt;
	
	private String flag;
	private String approveFlagForView;
	private String rejectFlag; 
	
	
	private String attemptDownload; 
	private String enrollmentIdForHallTicket;
	private String duration;
	private String userIdHallTicket;
	private String flagForNoRecord;
	private String  candidateReportingTime;
	
	private String approvalStartDate;
	private String approvalEndDate;
	
	private String password;
	private byte[] barCodeImage;
	
	private String candidateCountFlag;
	private String candidateCountMsg;
	private String rollNumberSuccMsg;
	private String rollNumberSuccFlag;
	private Integer admitCardRecordCount;
	
	private String rollNumberForHallTicket;

	private Map<Integer, String> attemptsListMap = new LinkedHashMap<Integer, String>();
	private String attmpt2Status;
	private List<CandidateApproveRejectBean> listOfRegisteredCandidate = new ArrayList<CandidateApproveRejectBean>();
	private Map<Integer, String> candidateStatusList = new LinkedHashMap<Integer, String>();
	private Integer candidateStatusId;
	private File fileUpload;
	private String fileUploadContentType;
	private String fileUploadFileName;
	private String executionTime;
	private Integer successfulReconcileCount;
	private Integer unSuccessfulReconcileCount;
	private List<BankChallanDetailsBean> unSuccessfulReconcileList = new ArrayList<BankChallanDetailsBean>();
	private List<OesPaymentDetails> unSuccessfulReconcileListEPost = new ArrayList<OesPaymentDetails>();
	
	private PaymentApprovalBean paymentDetailsForUserID;
	
	private List<CandidateMgmtBean> paymentReportResultBeanList;
	

	private List<CandidateMgmtBean> preferredTestResultBeanList;
	
	private Integer allocatedCandidateCount;
	private Integer notAllocatedCandidateCount;
	private Map<Integer, String> paymentStatusMap;
	private String paymentStatusValue;
	
	private String showPaymentReportSearchFlag;
	
	private String applicableFees;
	
	private String paymentSubmittedDate;
	private String paymentApprovalDate;
	
	private Integer paymentReportResultBeanListSize;
	private Integer preferredTestResultBeanListSize;
	
	
	private String ddChallanDate;
	private String ddChallanReceiptNo;
	private String challanBranchName;
	private String challanBranchCode;
	private String ddBankName;
	private String ddCityName;
	private String onlineTransactionNo;
	private String onlineTransactionDate;
	
	private String menuKey;
	private String attemptDropdownFlag;
	private String rollNoDisplayFlag; 
	private String guidelinesMsg;
	private String buttonDispFlag;
	private Integer totalCandidateCount;	
	private String paymentDetailsDisplayFlag;
	private String preferredTestCentreDisplayFlag;

	private String paymentModeValue;
	
	private String ePostSummary;
	private String challanSummary;
	
	public CandidateMgmtBean() {
	}
	
	public String getDisciplineId() {
		return disciplineId;
	}

	public void setDisciplineId(String disciplineId) {
		this.disciplineId = disciplineId;
	}

	public List<String> getPaymentModeList() {
		return paymentModeList;
	}

	public void setPaymentModeList(List<String> paymentModeList) {
		this.paymentModeList = paymentModeList;
	}

	public List<CommonBean> getDisciplineList() {
		return disciplineList;
	}

	public void setDisciplineList(List<CommonBean> disciplineList) {
		this.disciplineList = disciplineList;
	}

	public String getFromDate() {
		return fromDate;
	}

	

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public List<PaymentApprovalBean> getDdPaymentApprovalList() {
		return ddPaymentApprovalList;
	}

	public void setDdPaymentApprovalList(
			List<PaymentApprovalBean> ddPaymentApprovalList) {
		this.ddPaymentApprovalList = ddPaymentApprovalList;
	}

	public List<PaymentApprovalBean> getChalanPaymentApprovalList() {
		return chalanPaymentApprovalList;
	}

	public void setChalanPaymentApprovalList(
			List<PaymentApprovalBean> chalanPaymentApprovalList) {
		this.chalanPaymentApprovalList = chalanPaymentApprovalList;
	}

	public String getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(String enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public List<CommonBean> getTestCenterList() {
		return testCenterList;
	}

	public void setTestCenterList(List<CommonBean> testCenterList) {
		this.testCenterList = testCenterList;
	}

	public List<String> getTimesSlotList() {
		return timesSlotList;
	}

	public void setTimesSlotList(List<String> timesSlotList) {
		this.timesSlotList = timesSlotList;
	}

	public List<CommonBean> getDateList() {
		return dateList;
	}

	public void setDateList(List<CommonBean> dateList) {
		this.dateList = dateList;
	}

	public List<String> getEligibleList() {
		return eligibleList;
	}

	public void setEligibleList(List<String> eligibleList) {
		this.eligibleList = eligibleList;
	}

	public List<String> getTestDateList() {
		return testDateList;
	}

	public void setTestDateList(List<String> testDateList) {
		this.testDateList = testDateList;
	}

	public String getTestCenterId() {
		return testCenterId;
	}

	public void setTestCenterId(String testCenterId) {
		this.testCenterId = testCenterId;
	}

	public String getExamDate() {
		return examDate;
	}

	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}

	public String getTimeSlotId() {
		return timeSlotId;
	}

	public void setTimeSlotId(String timeSlotId) {
		this.timeSlotId = timeSlotId;
	}

	public List<EnrollmentBean> getEnrollmentDetailsList() {
		return enrollmentDetailsList;
	}

	public void setEnrollmentDetailsList(List<EnrollmentBean> enrollmentDetailsList) {
		this.enrollmentDetailsList = enrollmentDetailsList;
	}

	public Long getEnrollemntId() {
		return enrollemntId;
	}

	public void setEnrollemntId(Long enrollemntId) {
		this.enrollemntId = enrollemntId;
	}

	public List<String> getPaymentCheckedValue() {
		return paymentCheckedValue;
	}

	public void setPaymentCheckedValue(List<String> paymentCheckedValue) {
		this.paymentCheckedValue = paymentCheckedValue;
	}

	public List<EnrollmentBean> getEligibleNonElibileList() {
		return eligibleNonElibileList;
	}

	public void setEligibleNonElibileList(
			List<EnrollmentBean> eligibleNonElibileList) {
		this.eligibleNonElibileList = eligibleNonElibileList;
	}

	public String getEligibleListId() {
		return eligibleListId;
	}

	public void setEligibleListId(String eligibleListId) {
		this.eligibleListId = eligibleListId;
	}

	public String getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(String enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	public List<EnrollmentBean> getAdmitCardList() {
		return admitCardList;
	}

	public void setAdmitCardList(List<EnrollmentBean> admitCardList) {
		this.admitCardList = admitCardList;
	}

	public String getUserPK() {
		return userPK;
	}

	public void setUserPK(String userPK) {
		this.userPK = userPK;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCandidateCategory() {
		return candidateCategory;
	}

	public void setCandidateCategory(String candidateCategory) {
		this.candidateCategory = candidateCategory;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEligible() {
		return eligible;
	}

	public void setEligible(String eligible) {
		this.eligible = eligible;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getIpAddress() {
		return IpAddress;
	}

	public void setIpAddress(String ipAddress) {
		IpAddress = ipAddress;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAlternateAddress() {
		return AlternateAddress;
	}

	public void setAlternateAddress(String alternateAddress) {
		AlternateAddress = alternateAddress;
	}

	public String getCityFK() {
		return cityFK;
	}

	public void setCityFK(String cityFK) {
		this.cityFK = cityFK;
	}

	public String getStateFK() {
		return stateFK;
	}

	public void setStateFK(String stateFK) {
		this.stateFK = stateFK;
	}

	public String getCountryFK() {
		return countryFK;
	}

	public void setCountryFK(String countryFK) {
		this.countryFK = countryFK;
	}

	public String getAlternateCityFK() {
		return alternateCityFK;
	}

	public void setAlternateCityFK(String alternateCityFK) {
		this.alternateCityFK = alternateCityFK;
	}

	public String getAlternateStateFK() {
		return alternateStateFK;
	}

	public void setAlternateStateFK(String alternateStateFK) {
		this.alternateStateFK = alternateStateFK;
	}

	public String getAlternateCountryFK() {
		return alternateCountryFK;
	}

	public void setAlternateCountryFK(String alternateCountryFK) {
		this.alternateCountryFK = alternateCountryFK;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getAlternateEmailAddress() {
		return alternateEmailAddress;
	}

	public void setAlternateEmailAddress(String alternateEmailAddress) {
		this.alternateEmailAddress = alternateEmailAddress;
	}

	public String getPhoneNO() {
		return phoneNO;
	}

	public void setPhoneNO(String phoneNO) {
		this.phoneNO = phoneNO;
	}

	public String getMobileNO() {
		return mobileNO;
	}

	public void setMobileNO(String mobileNO) {
		this.mobileNO = mobileNO;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getSelectedForInterview() {
		return selectedForInterview;
	}

	public void setSelectedForInterview(String selectedForInterview) {
		this.selectedForInterview = selectedForInterview;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getAlternatePincode() {
		return alternatePincode;
	}

	public void setAlternatePincode(String alternatePincode) {
		this.alternatePincode = alternatePincode;
	}

	public List<String> getGenderList() {
		return genderList;
	}

	public void setGenderList(List<String> genderList) {
		this.genderList = genderList;
	}

	public List<CommonBean> getCityList() {
		return cityList;
	}

	public void setCityList(List<CommonBean> cityList) {
		this.cityList = cityList;
	}

	public List<CommonBean> getStatList() {
		return statList;
	}

	public void setStatList(List<CommonBean> statList) {
		this.statList = statList;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getAlternateCityListId() {
		return alternateCityListId;
	}

	public void setAlternateCityListId(String alternateCityListId) {
		this.alternateCityListId = alternateCityListId;
	}

	public String getStateListId() {
		return stateListId;
	}

	public void setStateListId(String stateListId) {
		this.stateListId = stateListId;
	}

	public String getAlternateStateListId() {
		return alternateStateListId;
	}

	public void setAlternateStateListId(String alternateStateListId) {
		this.alternateStateListId = alternateStateListId;
	}

	public String getCityListId() {
		return cityListId;
	}

	public void setCityListId(String cityListId) {
		this.cityListId = cityListId;
	}

	public InputStream getInputStreamForImage() {
		return inputStreamForImage;
	}

	public void setInputStreamForImage(InputStream inputStreamForImage) {
		this.inputStreamForImage = inputStreamForImage;
	}

	public InputStream getInputStreamForSignature() {
		return inputStreamForSignature;
	}

	public void setInputStreamForSignature(InputStream inputStreamForSignature) {
		this.inputStreamForSignature = inputStreamForSignature;
	}

	public String getCandidateImagePK() {
		return candidateImagePK;
	}

	public void setCandidateImagePK(String candidateImagePK) {
		this.candidateImagePK = candidateImagePK;
	}

	public String getAttachmentSignature() {
		return attachmentSignature;
	}

	public void setAttachmentSignature(String attachmentSignature) {
		this.attachmentSignature = attachmentSignature;
	}

	public String getAttachmentPhoto() {
		return attachmentPhoto;
	}

	public void setAttachmentPhoto(String attachmentPhoto) {
		this.attachmentPhoto = attachmentPhoto;
	}

	public Long getCandidateImageStatus() {
		return candidateImageStatus;
	}

	public void setCandidateImageStatus(Long candidateImageStatus) {
		this.candidateImageStatus = candidateImageStatus;
	}

	public String getAdmitCardDisplayFlag() {
		return admitCardDisplayFlag;
	}

	public void setAdmitCardDisplayFlag(String admitCardDisplayFlag) {
		this.admitCardDisplayFlag = admitCardDisplayFlag;
	}

	public void setAltCityList(List<CommonBean> altCityList)
	{
		this.altCityList = altCityList;
	}

	public List<CommonBean> getAltCityList()
	{
		return altCityList;
	}

	public void setRemarks(String remarks)
	{
		this.remarks = remarks;
	}

	public String getRemarks()
	{
		return remarks;
	}

	public void setEnrollApprovalList(List<PaymentApprovalBean> enrollApprovalList)
	{
		this.enrollApprovalList = enrollApprovalList;
	}

	public List<PaymentApprovalBean> getEnrollApprovalList()
	{
		return enrollApprovalList;
	}

	public void setUserPKForAdmitCard(List<String> userPKForAdmitCard)
	{
		this.userPKForAdmitCard = userPKForAdmitCard;
	}

	public List<String> getUserPKForAdmitCard()
	{
		return userPKForAdmitCard;
	}

	public String getEligibleDisplayFlag() {
		return eligibleDisplayFlag;
	}

	public void setEligibleDisplayFlag(String eligibleDisplayFlag) {
		this.eligibleDisplayFlag = eligibleDisplayFlag;
	}

	public String getEnrollDisplayFlag() {
		return enrollDisplayFlag;
	}

	public void setEnrollDisplayFlag(String enrollDisplayFlag) {
		this.enrollDisplayFlag = enrollDisplayFlag;
	}

	public String getEnrollmentIdIsNull() {
		return enrollmentIdIsNull;
	}

	public void setEnrollmentIdIsNull(String enrollmentIdIsNull) {
		this.enrollmentIdIsNull = enrollmentIdIsNull;
	}

	public String getAdmitCardHeaderDisplayFlag() {
		return admitCardHeaderDisplayFlag;
	}

	public void setAdmitCardHeaderDisplayFlag(String admitCardHeaderDisplayFlag) {
		this.admitCardHeaderDisplayFlag = admitCardHeaderDisplayFlag;
	}

	// for gcet
	public String getRemarksForCandidate() {
		return remarksForCandidate;
	}

	public void setRemarksForCandidate(String remarksForCandidate) {
		this.remarksForCandidate = remarksForCandidate;
	}

	public String getUserPrimaryKey() {
		return userPrimaryKey;
	}

	public void setUserPrimaryKey(String userPrimaryKey) {
		this.userPrimaryKey = userPrimaryKey;
	}

	public String getApproveFlag() {
		return approveFlag;
	}

	public void setApproveFlag(String approveFlag) {
		this.approveFlag = approveFlag;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getCandidateDateOfBirth() {
		return candidateDateOfBirth;
	}

	public void setCandidateDateOfBirth(String candidateDateOfBirth) {
		this.candidateDateOfBirth = candidateDateOfBirth;
	}

	public String getCandidateGender() {
		return candidateGender;
	}

	public void setCandidateGender(String candidateGender) {
		this.candidateGender = candidateGender;
	}

	public String getCandidateTestDate() {
		return candidateTestDate;
	}

	public void setCandidateTestDate(String candidateTestDate) {
		this.candidateTestDate = candidateTestDate;
	}

	public String getCandidateTestStartTime() {
		return candidateTestStartTime;
	}

	public void setCandidateTestStartTime(String candidateTestStartTime) {
		this.candidateTestStartTime = candidateTestStartTime;
	}

	public String getCandidateTestEndTime() {
		return candidateTestEndTime;
	}

	public void setCandidateTestEndTime(String candidateTestEndTime) {
		this.candidateTestEndTime = candidateTestEndTime;
	}

	public String getCandidateTestCenterAddress() {
		return candidateTestCenterAddress;
	}

	public void setCandidateTestCenterAddress(String candidateTestCenterAddress) {
		this.candidateTestCenterAddress = candidateTestCenterAddress;
	}

	public String getHallticketDisplayFlag() {
		return hallticketDisplayFlag;
	}

	public void setHallticketDisplayFlag(String hallticketDisplayFlag) {
		this.hallticketDisplayFlag = hallticketDisplayFlag;
	}

	public List<CandidateMgmtBean> getHallTicketDetails() {
		return hallTicketDetails;
	}

	public void setHallTicketDetails(List<CandidateMgmtBean> hallTicketDetails) {
		this.hallTicketDetails = hallTicketDetails;
	}

	public String getUserIdForHallticket() {
		return userIdForHallticket;
	}

	public void setUserIdForHallticket(String userIdForHallticket) {
		this.userIdForHallticket = userIdForHallticket;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestCenterName() {
		return testCenterName;
	}

	public void setTestCenterName(String testCenterName) {
		this.testCenterName = testCenterName;
	}

	public List<CandidateMgmtBean> getCandidateDetailsList() {
		return candidateDetailsList;
	}

	public void setCandidateDetailsList(List<CandidateMgmtBean> candidateDetailsList) {
		this.candidateDetailsList = candidateDetailsList;
	}

	public String getRegDtlsDisplayFlag() {
		return regDtlsDisplayFlag;
	}

	public void setRegDtlsDisplayFlag(String regDtlsDisplayFlag) {
		this.regDtlsDisplayFlag = regDtlsDisplayFlag;
	}

	public Map<Integer, String> getDiscliplineList() {
		return discliplineList;
	}

	public void setDiscliplineList(Map<Integer, String> discliplineList) {
		this.discliplineList = discliplineList;
	}

	public String getDisciplineType() {
		return disciplineType;
	}

	public void setDisciplineType(String disciplineType) {
		this.disciplineType = disciplineType;
	}

	public Map<Integer, String> getTestCenterMasterDetails() {
		return testCenterMasterDetails;
	}

	public void setTestCenterMasterDetails(
			Map<Integer, String> testCenterMasterDetails) {
		this.testCenterMasterDetails = testCenterMasterDetails;
	}

	public String getTestCenterValue() {
		return testCenterValue;
	}

	public void setTestCenterValue(String testCenterValue) {
		this.testCenterValue = testCenterValue;
	}

	public String getTestDates() {
		return testDates;
	}

	public void setTestDates(String testDates) {
		this.testDates = testDates;
	}

	public String getTestDateForSlot() {
		return testDateForSlot;
	}

	public void setTestDateForSlot(String testDateForSlot) {
		this.testDateForSlot = testDateForSlot;
	}

	public String getFinalTestSlots() {
		return finalTestSlots;
	}

	public void setFinalTestSlots(String finalTestSlots) {
		this.finalTestSlots = finalTestSlots;
	}

	public String getTestDateSelected() {
		return testDateSelected;
	}

	public void setTestDateSelected(String testDateSelected) {
		this.testDateSelected = testDateSelected;
	}

	public String getTestSlotSelected() {
		return testSlotSelected;
	}

	public void setTestSlotSelected(String testSlotSelected) {
		this.testSlotSelected = testSlotSelected;
	}

	public Map<Integer, String> getTestDateListForAdmin() {
		return testDateListForAdmin;
	}

	public void setTestDateListForAdmin(Map<Integer, String> testDateListForAdmin) {
		this.testDateListForAdmin = testDateListForAdmin;
	}

	public Map<Integer, String> getTestSlotListForAdmin() {
		return testSlotListForAdmin;
	}

	public void setTestSlotListForAdmin(Map<Integer, String> testSlotListForAdmin) {
		this.testSlotListForAdmin = testSlotListForAdmin;
	}

	public String getFirstAttemptFlag() {
		return firstAttemptFlag;
	}

	public void setFirstAttemptFlag(String firstAttemptFlag) {
		this.firstAttemptFlag = firstAttemptFlag;
	}

	public String getSecondAttemptFlag() {
		return secondAttemptFlag;
	}

	public void setSecondAttemptFlag(String secondAttemptFlag) {
		this.secondAttemptFlag = secondAttemptFlag;
	}

	public List<String> getTestDateListForAdminList() {
		return testDateListForAdminList;
	}

	public void setTestDateListForAdminList(List<String> testDateListForAdminList) {
		this.testDateListForAdminList = testDateListForAdminList;
	}

	public List<String> getTestSlotListForAdminList() {
		return testSlotListForAdminList;
	}

	public void setTestSlotListForAdminList(List<String> testSlotListForAdminList) {
		this.testSlotListForAdminList = testSlotListForAdminList;
	}

	public String getHallTicket2Disp() {
		return hallTicket2Disp;
	}

	public void setHallTicket2Disp(String hallTicket2Disp) {
		this.hallTicket2Disp = hallTicket2Disp;
	}

	public String getHallTicket1Disp() {
		return hallTicket1Disp;
	}

	public void setHallTicket1Disp(String hallTicket1Disp) {
		this.hallTicket1Disp = hallTicket1Disp;
	}

	public String getAttempt() {
		return attempt;
	}

	public void setAttempt(String attempt) {
		this.attempt = attempt;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getApproveFlagForView() {
		return approveFlagForView;
	}

	public void setApproveFlagForView(String approveFlagForView) {
		this.approveFlagForView = approveFlagForView;
	}

	public String getRejectFlag() {
		return rejectFlag;
	}

	public void setRejectFlag(String rejectFlag) {
		this.rejectFlag = rejectFlag;
	}

	public String getAttemptDownload() {
		return attemptDownload;
	}

	public void setAttemptDownload(String attemptDownload) {
		this.attemptDownload = attemptDownload;
	}

	public String getEnrollmentIdForHallTicket() {
		return enrollmentIdForHallTicket;
	}

	public void setEnrollmentIdForHallTicket(String enrollmentIdForHallTicket) {
		this.enrollmentIdForHallTicket = enrollmentIdForHallTicket;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getUserIdHallTicket() {
		return userIdHallTicket;
	}

	public void setUserIdHallTicket(String userIdHallTicket) {
		this.userIdHallTicket = userIdHallTicket;
	}

	public String getFlagForNoRecord() {
		return flagForNoRecord;
	}

	public void setFlagForNoRecord(String flagForNoRecord) {
		this.flagForNoRecord = flagForNoRecord;
	}

	public String getCandidateReportingTime() {
		return candidateReportingTime;
	}

	public void setCandidateReportingTime(String candidateReportingTime) {
		this.candidateReportingTime = candidateReportingTime;
	}

	public String getApprovalEndDate() {
		return approvalEndDate;
	}

	public void setApprovalEndDate(String approvalEndDate) {
		this.approvalEndDate = approvalEndDate;
	}

	public String getApprovalStartDate() {
		return approvalStartDate;
	}

	public void setApprovalStartDate(String approvalStartDate) {
		this.approvalStartDate = approvalStartDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public byte [] getBarCodeImage() {
		return barCodeImage;
	}
	public void setBarCodeImage(byte [] barCodeImage) {
		this.barCodeImage = barCodeImage;
	}

	public String getCandidateCountFlag() {
		return candidateCountFlag;
	}

	public void setCandidateCountFlag(String candidateCountFlag) {
		this.candidateCountFlag = candidateCountFlag;
	}

	public String getCandidateCountMsg() {
		return candidateCountMsg;
	}

	public void setCandidateCountMsg(String candidateCountMsg) {
		this.candidateCountMsg = candidateCountMsg;
	}

	public List<String> getRemarksValue() {
		return remarksValue;
	}

	public void setRemarksValue(List<String> remarksValue) {
		this.remarksValue = remarksValue;
	}

	public Map<Integer, String> getDisciplineListMap() {
		return disciplineListMap;
	}

	public void setDisciplineListMap(Map<Integer, String> disciplineListMap) {
		this.disciplineListMap = disciplineListMap;
	}

	public Map<Integer, String> getTestCenterListMap() {
		return testCenterListMap;
	}

	public void setTestCenterListMap(Map<Integer, String> testCenterListMap) {
		this.testCenterListMap = testCenterListMap;
	}

	public String getTestCenterVal() {
		return testCenterVal;
	}

	public void setTestCenterVal(String testCenterVal) {
		this.testCenterVal = testCenterVal;
	}

	public List<String> getAttemptsList() {
		return attemptsList;
	}

	public void setAttemptsList(List<String> attemptsList) {
		this.attemptsList = attemptsList;
	}

	public String getAttemptVal() {
		return attemptVal;
	}

	public void setAttemptVal(String attemptVal) {
		this.attemptVal = attemptVal;
	}

	public String getRollNumberSuccMsg() {
		return rollNumberSuccMsg;
	}

	public void setRollNumberSuccMsg(String rollNumberSuccMsg) {
		this.rollNumberSuccMsg = rollNumberSuccMsg;
	}

	public String getRollNumberSuccFlag() {
		return rollNumberSuccFlag;
	}

	public void setRollNumberSuccFlag(String rollNumberSuccFlag) {
		this.rollNumberSuccFlag = rollNumberSuccFlag;
	}

	public List<HallTicketBean> getCandidateListForAdmitCard() {
		return candidateListForAdmitCard;
	}

	public void setCandidateListForAdmitCard(
			List<HallTicketBean> candidateListForAdmitCard) {
		this.candidateListForAdmitCard = candidateListForAdmitCard;
	}

	public List<Long> getUserFKForCandidate() {
		return userFKForCandidate;
	}

	public void setUserFKForCandidate(List<Long> userFKForCandidate) {
		this.userFKForCandidate = userFKForCandidate;
	}

	public List<String> getEnrollmentPKForAdmitCard() {
		return enrollmentPKForAdmitCard;
	}

	public void setEnrollmentPKForAdmitCard(List<String> enrollmentPKForAdmitCard) {
		this.enrollmentPKForAdmitCard = enrollmentPKForAdmitCard;
	}

	public String getRollNumberForHallTicket() {
		return rollNumberForHallTicket;
	}

	public void setRollNumberForHallTicket(String rollNumberForHallTicket) {
		this.rollNumberForHallTicket = rollNumberForHallTicket;
	}

	public Integer getAdmitCardRecordCount() {
		return admitCardRecordCount;
	}

	public void setAdmitCardRecordCount(Integer admitCardRecordCount) {
		this.admitCardRecordCount = admitCardRecordCount;
	}

	public Map<Integer, String> getAttemptsListMap() {
		return attemptsListMap;
	}

	public void setAttemptsListMap(Map<Integer, String> attemptsListMap) {
		this.attemptsListMap = attemptsListMap;
	}

	public String getAttmpt2Status() {
		return attmpt2Status;
	}

	public void setAttmpt2Status(String attmpt2Status) {
		this.attmpt2Status = attmpt2Status;
	}

	public List<CandidateApproveRejectBean> getListOfRegisteredCandidate() {
		return listOfRegisteredCandidate;
	}

	public void setListOfRegisteredCandidate(
			List<CandidateApproveRejectBean> listOfRegisteredCandidate) {
		this.listOfRegisteredCandidate = listOfRegisteredCandidate;
	}

	public Map<Integer, String> getCandidateStatusList() {
		return candidateStatusList;
	}

	public void setCandidateStatusList(Map<Integer, String> candidateStatusList) {
		this.candidateStatusList = candidateStatusList;
	}

	public Integer getCandidateStatusId() {
		return candidateStatusId;
	}

	public void setCandidateStatusId(Integer candidateStatusId) {
		this.candidateStatusId = candidateStatusId;
	}

	public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getFileUploadContentType() {
		return fileUploadContentType;
	}

	public void setFileUploadContentType(String fileUploadContentType) {
		this.fileUploadContentType = fileUploadContentType;
	}

	public String getFileUploadFileName() {
		return fileUploadFileName;
	}

	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}

	public String getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(String executionTime) {
		this.executionTime = executionTime;
	}

	public Integer getSuccessfulReconcileCount() {
		return successfulReconcileCount;
	}

	public void setSuccessfulReconcileCount(Integer successfulReconcileCount) {
		this.successfulReconcileCount = successfulReconcileCount;
	}

	public Integer getUnSuccessfulReconcileCount() {
		return unSuccessfulReconcileCount;
	}

	public void setUnSuccessfulReconcileCount(Integer unSuccessfulReconcileCount) {
		this.unSuccessfulReconcileCount = unSuccessfulReconcileCount;
	}

	public List<BankChallanDetailsBean> getUnSuccessfulReconcileList() {
		return unSuccessfulReconcileList;
	}

	public void setUnSuccessfulReconcileList(
			List<BankChallanDetailsBean> unSuccessfulReconcileList) {
		this.unSuccessfulReconcileList = unSuccessfulReconcileList;
	}

    public PaymentApprovalBean getPaymentDetailsForUserID()
    {
        return paymentDetailsForUserID;
    }

    public void setPaymentDetailsForUserID(PaymentApprovalBean paymentDetailsForUserID)
    {
        this.paymentDetailsForUserID = paymentDetailsForUserID;
    }

	public Integer getAllocatedCandidateCount() {
		return allocatedCandidateCount;
	}

	public void setAllocatedCandidateCount(Integer allocatedCandidateCount) {
		this.allocatedCandidateCount = allocatedCandidateCount;
	}

	public Integer getNotAllocatedCandidateCount() {
		return notAllocatedCandidateCount;
	}

	public void setNotAllocatedCandidateCount(Integer notAllocatedCandidateCount) {
		this.notAllocatedCandidateCount = notAllocatedCandidateCount;
	}

	public Map<Integer, String> getPaymentStatusMap() {
		return paymentStatusMap;
	}

	public void setPaymentStatusMap(Map<Integer, String> paymentStatusMap) {
		this.paymentStatusMap = paymentStatusMap;
	}

	public String getPaymentStatusValue() {
		return paymentStatusValue;
	}

	public void setPaymentStatusValue(String paymentStatusValue) {
		this.paymentStatusValue = paymentStatusValue;
	}

    public List<CommonBean> getPaymentModeReportList()
    {
        return paymentModeReportList;
    }

    public void setPaymentModeReportList(List<CommonBean> paymentModeReportList)
    {
        this.paymentModeReportList = paymentModeReportList;
    }

    public String getShowPaymentReportSearchFlag()
    {
        return showPaymentReportSearchFlag;
    }

    public void setShowPaymentReportSearchFlag(String showPaymentReportSearchFlag)
    {
        this.showPaymentReportSearchFlag = showPaymentReportSearchFlag;
    }

    
    public String getApplicableFees()
    {
        return applicableFees;
    }

    public void setApplicableFees(String applicableFees)
    {
        this.applicableFees = applicableFees;
    }

    public List<CandidateMgmtBean> getPaymentReportResultBeanList()
    {
        return paymentReportResultBeanList;
    }

    public void setPaymentReportResultBeanList(List<CandidateMgmtBean> paymentReportResultBeanList)
    {
        this.paymentReportResultBeanList = paymentReportResultBeanList;
    }

    public String getPaymentSubmittedDate()
    {
        return paymentSubmittedDate;
    }

    public void setPaymentSubmittedDate(String paymentSubmittedDate)
    {
        this.paymentSubmittedDate = paymentSubmittedDate;
    }

    public String getPaymentApprovalDate()
    {
        return paymentApprovalDate;
    }

    public void setPaymentApprovalDate(String paymentApprovalDate)
    {
        this.paymentApprovalDate = paymentApprovalDate;
    }

    public Integer getPaymentReportResultBeanListSize()
    {
        return paymentReportResultBeanListSize;
    }

    public void setPaymentReportResultBeanListSize(Integer paymentReportResultBeanListSize)
    {
        this.paymentReportResultBeanListSize = paymentReportResultBeanListSize;
    }

    public String getDdChallanDate()
    {
        return ddChallanDate;
    }

    public void setDdChallanDate(String ddChallanDate)
    {
        this.ddChallanDate = ddChallanDate;
    }

    public String getDdChallanReceiptNo()
    {
        return ddChallanReceiptNo;
    }

    public void setDdChallanReceiptNo(String ddChallanReceiptNo)
    {
        this.ddChallanReceiptNo = ddChallanReceiptNo;
    }

    public String getChallanBranchName()
    {
        return challanBranchName;
    }

    public void setChallanBranchName(String challanBranchName)
    {
        this.challanBranchName = challanBranchName;
    }

    public String getChallanBranchCode()
    {
        return challanBranchCode;
    }

    public void setChallanBranchCode(String challanBranchCode)
    {
        this.challanBranchCode = challanBranchCode;
    }

    public String getDdBankName()
    {
        return ddBankName;
    }

    public void setDdBankName(String ddBankName)
    {
        this.ddBankName = ddBankName;
    }

    public String getDdCityName()
    {
        return ddCityName;
    }

    public void setDdCityName(String ddCityName)
    {
        this.ddCityName = ddCityName;
    }

    public String getOnlineTransactionNo()
    {
        return onlineTransactionNo;
    }

    public void setOnlineTransactionNo(String onlineTransactionNo)
    {
        this.onlineTransactionNo = onlineTransactionNo;
    }

    public String getOnlineTransactionDate()
    {
        return onlineTransactionDate;
    }

    public void setOnlineTransactionDate(String onlineTransactionDate)
    {
        this.onlineTransactionDate = onlineTransactionDate;
    }

    public String getMenuKey()
    {
        return menuKey;
    }

    public void setMenuKey(String menuKey)
    {
        this.menuKey = menuKey;
    }

	public String getAttemptDropdownFlag() {
		return attemptDropdownFlag;
	}

	public void setAttemptDropdownFlag(String attemptDropdownFlag) {
		this.attemptDropdownFlag = attemptDropdownFlag;
	}

	public String getRollNoDisplayFlag() {
		return rollNoDisplayFlag;
	}

	public void setRollNoDisplayFlag(String rollNoDisplayFlag) {
		this.rollNoDisplayFlag = rollNoDisplayFlag;
	}

	public String getGuidelinesMsg() {
		return guidelinesMsg;
	}

	public void setGuidelinesMsg(String guidelinesMsg) {
		this.guidelinesMsg = guidelinesMsg;
	}

	public String getButtonDispFlag() {
		return buttonDispFlag;
	}

	public void setButtonDispFlag(String buttonDispFlag) {
		this.buttonDispFlag = buttonDispFlag;
	}

	public Integer getTotalCandidateCount() {
		return totalCandidateCount;
	}

	public void setTotalCandidateCount(Integer totalCandidateCount) {
		this.totalCandidateCount = totalCandidateCount;
	}

	public String getPaymentDetailsDisplayFlag() {
		return paymentDetailsDisplayFlag;
	}

	public void setPaymentDetailsDisplayFlag(String paymentDetailsDisplayFlag) {
		this.paymentDetailsDisplayFlag = paymentDetailsDisplayFlag;
	}

	public String getPaymentModeValue() {
		return paymentModeValue;
	}

	public void setPaymentModeValue(String paymentModeValue) {
		this.paymentModeValue = paymentModeValue;
	}

	public void setEPostSummary(String ePostSummary) {
		this.ePostSummary = ePostSummary;
	}

	public String getEPostSummary() {
		return ePostSummary;
	}

	public void setChallanSummary(String challanSummary) {
		this.challanSummary = challanSummary;
	}

	public String getChallanSummary() {
		return challanSummary;
	}

	public void setUnSuccessfulReconcileListEPost(
			List<OesPaymentDetails> unSuccessfulReconcileListEPost) {
		this.unSuccessfulReconcileListEPost = unSuccessfulReconcileListEPost;
	}

	public List<OesPaymentDetails> getUnSuccessfulReconcileListEPost() {
		return unSuccessfulReconcileListEPost;
	}
	public List<CandidateMgmtBean> getPreferredTestResultBeanList() {
		return preferredTestResultBeanList;
	}

	public void setPreferredTestResultBeanList(
			List<CandidateMgmtBean> preferredTestResultBeanList) {
		this.preferredTestResultBeanList = preferredTestResultBeanList;
	}
	
	public Integer getPreferredTestResultBeanListSize() {
		return preferredTestResultBeanListSize;
	}

	public void setPreferredTestResultBeanListSize(
			Integer preferredTestResultBeanListSize) {
		this.preferredTestResultBeanListSize = preferredTestResultBeanListSize;
	}
	public String getPreferredTestCentreDisplayFlag() {
		return preferredTestCentreDisplayFlag;
	}

	public void setPreferredTestCentreDisplayFlag(
			String preferredTestCentreDisplayFlag) {
		this.preferredTestCentreDisplayFlag = preferredTestCentreDisplayFlag;
	}




}