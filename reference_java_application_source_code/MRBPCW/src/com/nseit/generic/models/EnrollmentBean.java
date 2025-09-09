package com.nseit.generic.models;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentBean {
	
	
	
	//GET_CANDIDATE_DETAILS
	private String userPK;
	private String title;
	private String firstName;
	private String lastName; 
	private String candidateCategory;
	private String dateOfBirth;
	private String gender;
	private String eligible;
	private String reason ;
	private String IpAddress;
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
	private String pincode ;
	private String alternatePincode ;
	private Long enrolValue;

	//GET_PREFERRED_TEST_CENTRE_FOR_CANDIDATE
	
	private String enrollmentPK;
	private String testName;
	private String preferredTestcentre1FK;
	private String preferredTestcentre2FK; 
	private String preferredTestCentre3FK;
	private String preferredTestcentre1Name;
	private String preferredTestcentre2Name; 
	private String preferredTestCentre3Name;
	private String testCenterName;
	private String startTimeStr;
	private String endTimeStr;
	private String testDate;
	private String timeSlot;
	private int assignedTestCenter;
	
	//GET_ACADEMIC_DETAILS

	 private String academicPK;
 	 private String exam;
 	 private String percentage;
 	 private String passingYear;
 	 private String boardUniversity; 
 	 private String degree; 
 	 private String boardOther;
 	 private String examName;
 	 private String boardUniversityName;
 	 
 	 //GET_PREFERRED_TEST_CENTRE_FOR_CANDIDATE 
		 
	private String enrollment_PK;
	private String preferredTestCentre1FK;
	private String preferredTestCentre2FK; 
	
	 // GET_CANDIDATE_IMAGES  
			
	 private String candidateImagePK;
	 private String photoImage;
	 private String signImage; 
	 private String uploadType;
	 
	 //GET_TEST_DETAILS_FOR_CANDIDATE
	 
	 private Long testPK = 0l;
	 private String testResult;
	 
	 //String GET_REGISTRATION_DETAILS = 
	 private String fatherName;

	 
	 //disciplineList
	 
	 private List<CommonBean> disciplineList = null;
	 
	 private List<CommonBean> bankList = null;
	 private List<CommonBean> cityList = null;
	 private List<CommonBean> statList = null;
	 private List<CommonBean> countryList = null;
	 private List<CommonBean> testCenterList = null;
	 
	 private List<AcademicDetailsBean> qualificationListBean = new ArrayList<AcademicDetailsBean>(); 
	 private List<AcademicDetailsBean> boardUniversityList = new ArrayList<AcademicDetailsBean>();
	 private List<EnrollmentBean> academicDetailList = null;
	 private List<String> yearList = null;
	 //private String selectYear = "";
	 private List<String>  boardUniversityListId = null;
	 private List<String>   qualificationListId ;
	 private List<String> selectYear ;
	 private List<String> markParcentage;
	 private List<String> otherBoard;
	 private List<String> examNameList;
 	 private List<String> boardUniversityNameList;
	 
 	 private String disciplineId = "";
	 private int academicListSize = 0;;
	 
	 private String firstTestCenterId;
	 private String secondTestCenterId;
	 private String thirdTestCenterId;
	 
	 private String cityListId = "";
	 private String alternateCityListId = "";
	 
	 private String stateListId = "";
	 private String alternateStateListId = "";
	 
	 private String disabled="";
	
	//Payment 
	 private PaymentBean ddpaymentBean = null;
	 private PaymentBean challanpaymentBean = null;
	 //upload photo and signature
	 
	 private String attachmentSignature ;
	 private String attachmentPhoto;
	 
	 private InputStream inputStreamForImage;
	 private InputStream inputStreamForSignature;
	 private String remark;
	 private Long changeRequestdtlPK;
	 
	 private String nonEligibleRemarks;
	 private String candidateFirstName;
	 private String candidateLastName;
	 private String enrollmentDisplayFlag;
	 private String paymentDisplayFlag;
	 
	 
	 
	 private String requestChangeDisplayFlag;
	 private String challanBranchName;
	 
	 private String currSectionToDisplay;
	 private String actionName;
	 /* Added by Raman Pawar to generate roll no. Starts*/
	 private String testCenterCode;
	 private String desciplineCode;
	 private String rollNo;
	 private String batchPk;
	 /* Added by Raman Pawar to generate roll no. ends*/
	 
	 
	 private String stateName;
	 private String cityName;
	 private String altStateName;
	 private String altCityName;
	 
	 private String preferedTestDate1;
	 private String preferedTestDate2;
	 private String preferedTestDate3;
	
	public String getUserPK() {
		return userPK;
	}
	public void setUserPK(String userPK) {
		this.userPK = userPK;
	}
	public String getTitle() {
		return title;
	}
	public String getAttachmentPhoto() {
		return attachmentPhoto;
	}
	public void setAttachmentPhoto(String attachmentPhoto) {
		this.attachmentPhoto = attachmentPhoto;
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
	public String getEnrollmentPK() {
		return enrollmentPK;
	}
	public void setEnrollmentPK(String enrollmentPK) {
		this.enrollmentPK = enrollmentPK;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public String getPreferredTestcentre1FK() {
		return preferredTestcentre1FK;
	}
	public void setPreferredTestcentre1FK(String preferredTestcentre1FK) {
		this.preferredTestcentre1FK = preferredTestcentre1FK;
	}
	public String getPreferredTestcentre2FK() {
		return preferredTestcentre2FK;
	}
	public void setPreferredTestcentre2FK(String preferredTestcentre2FK) {
		this.preferredTestcentre2FK = preferredTestcentre2FK;
	}
	public String getPreferredTestCentre3FK() {
		return preferredTestCentre3FK;
	}
	public void setPreferredTestCentre3FK(String preferredTestCentre3FK) {
		this.preferredTestCentre3FK = preferredTestCentre3FK;
	}
	public String getAcademicPK() {
		return academicPK;
	}
	public void setAcademicPK(String academicPK) {
		this.academicPK = academicPK;
	}
	public String getExam() {
		return exam;
	}
	public void setExam(String exam) {
		this.exam = exam;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public String getPassingYear() {
		return passingYear;
	}
	public void setPassingYear(String passingYear) {
		this.passingYear = passingYear;
	}
	public String getBoardUniversity() {
		return boardUniversity;
	}
	public void setBoardUniversity(String boardUniversity) {
		this.boardUniversity = boardUniversity;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getEnrollment_PK() {
		return enrollment_PK;
	}
	public void setEnrollment_PK(String enrollmentPK) {
		enrollment_PK = enrollmentPK;
	}
	public String getPreferredTestCentre1FK() {
		return preferredTestCentre1FK;
	}
	public void setPreferredTestCentre1FK(String preferredTestCentre1FK) {
		this.preferredTestCentre1FK = preferredTestCentre1FK;
	}
	public String getPreferredTestCentre2FK() {
		return preferredTestCentre2FK;
	}
	public void setPreferredTestCentre2FK(String preferredTestCentre2FK) {
		this.preferredTestCentre2FK = preferredTestCentre2FK;
	}
	public String getCandidateImagePK() {
		return candidateImagePK;
	}
	public void setCandidateImagePK(String candidateImagePK) {
		this.candidateImagePK = candidateImagePK;
	}
	public String getPhotoImage() {
		return photoImage;
	}
	public void setPhotoImage(String photoImage) {
		this.photoImage = photoImage;
	}
	public String getSignImage() {
		return signImage;
	}
	public void setSignImage(String signImage) {
		this.signImage = signImage;
	}
	public Long getTestPK() {
		return testPK;
	}
	public void setTestPK(Long testPK) {
		this.testPK = testPK;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<CommonBean> getDisciplineList() {
		return disciplineList;
	}
	public void setDisciplineList(List<CommonBean> disciplineList) {
		this.disciplineList = disciplineList;
	}
	public List<CommonBean> getBankList() {
		return bankList;
	}
	public void setBankList(List<CommonBean> bankList) {
		this.bankList = bankList;
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
	public List<CommonBean> getCountryList() {
		return countryList;
	}
	public void setCountryList(List<CommonBean> countryList) {
		this.countryList = countryList;
	}
	public String getDisciplineId() {
		return disciplineId;
	}
	public void setDisciplineId(String disciplineId) {
		this.disciplineId = disciplineId;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public String getCityListId() {
		return cityListId;
	}
	public void setCityListId(String cityListId) {
		this.cityListId = cityListId;
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
	public List<AcademicDetailsBean> getQualificationListBean() {
		return qualificationListBean;
	}
	public void setQualificationListBean(
			List<AcademicDetailsBean> qualificationListBean) {
		this.qualificationListBean = qualificationListBean;
	}
	public List<AcademicDetailsBean> getBoardUniversityList() {
		return boardUniversityList;
	}
	public void setBoardUniversityList(List<AcademicDetailsBean> boardUniversityList) {
		this.boardUniversityList = boardUniversityList;
	}
	public List<EnrollmentBean> getAcademicDetailList() {
		return academicDetailList;
	}
	public void setAcademicDetailList(List<EnrollmentBean> academicDetailList) {
		this.academicDetailList = academicDetailList;
	}
	public List<String> getYearList() {
		return yearList;
	}
	public void setYearList(List<String> yearList) {
		this.yearList = yearList;
	}
		
	public List<String> getSelectYear() {
		return selectYear;
	}
	public void setSelectYear(List<String> selectYear) {
		this.selectYear = selectYear;
	}
	public List<String> getBoardUniversityListId() {
		return boardUniversityListId;
	}
	public void setBoardUniversityListId(List<String> boardUniversityListId) {
		this.boardUniversityListId = boardUniversityListId;
	}
	public List<String> getQualificationListId() {
		return qualificationListId;
	}
	public void setQualificationListId(List<String> qualificationListId) {
		this.qualificationListId = qualificationListId;
	}
	public List<String> getMarkParcentage() {
		return markParcentage;
	}
	public void setMarkParcentage(List<String> markParcentage) {
		this.markParcentage = markParcentage;
	}
	public int getAcademicListSize() {
		return academicListSize;
	}
	public void setAcademicListSize(int academicListSize) {
		this.academicListSize = academicListSize;
	}
	public List<CommonBean> getTestCenterList() {
		return testCenterList;
	}
	public void setTestCenterList(List<CommonBean> testCenterList) {
		this.testCenterList = testCenterList;
	}
	public String getFirstTestCenterId() {
		return firstTestCenterId;
	}
	public void setFirstTestCenterId(String firstTestCenterId) {
		this.firstTestCenterId = firstTestCenterId;
	}
	public String getSecondTestCenterId() {
		return secondTestCenterId;
	}
	public void setSecondTestCenterId(String secondTestCenterId) {
		this.secondTestCenterId = secondTestCenterId;
	}
	public String getThirdTestCenterId() {
		return thirdTestCenterId;
	}
	public void setThirdTestCenterId(String thirdTestCenterId) {
		this.thirdTestCenterId = thirdTestCenterId;
	}
	public String getAttachmentSignature() {
		return attachmentSignature;
	}
	public void setAttachmentSignature(String attachmentSignature) {
		this.attachmentSignature = attachmentSignature;
	}
	
	public PaymentBean getDdpaymentBean() {
		return ddpaymentBean;
	}
	public void setDdpaymentBean(PaymentBean ddpaymentBean) {
		this.ddpaymentBean = ddpaymentBean;
	}
	public PaymentBean getChallanpaymentBean() {
		return challanpaymentBean;
	}
	public void setChallanpaymentBean(PaymentBean challanpaymentBean) {
		this.challanpaymentBean = challanpaymentBean;
	}

	public String getTestCenterName() {
		return testCenterName;
	}
	
	public void setTestCenterName(String testCenterName) {
		this.testCenterName = testCenterName;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public String getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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
	
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
		
	
	public Long getChangeRequestdtlPK() {
		return changeRequestdtlPK;
	}
	public void setChangeRequestdtlPK(Long changeRequestdtlPK) {
		this.changeRequestdtlPK = changeRequestdtlPK;
	}
	public String getNonEligibleRemarks() {
		return nonEligibleRemarks;
	}
	public void setNonEligibleRemarks(String nonEligibleRemarks) {
		this.nonEligibleRemarks = nonEligibleRemarks;
	}
	public String getCandidateFirstName() {
		return candidateFirstName;
	}
	public void setCandidateFirstName(String candidateFirstName) {
		this.candidateFirstName = candidateFirstName;
	}
	public String getCandidateLastName() {
		return candidateLastName;
	}
	public void setCandidateLastName(String candidateLastName) {
		this.candidateLastName = candidateLastName;
	}
	public Long getEnrolValue() {
		return enrolValue;
	}
	public void setEnrolValue(Long enrolValue) {
		this.enrolValue = enrolValue;
	}
	public void setAssignedTestCenter(int assignedTestCenter)
	{
		this.assignedTestCenter = assignedTestCenter;
	}
	public int getAssignedTestCenter()
	{
		return assignedTestCenter;
	}
	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}
	public String getTestResult() {
		return testResult;
	}
	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}
	public String getUploadType() {
		return uploadType;
	}
	public String getEnrollmentDisplayFlag() {
		return enrollmentDisplayFlag;
	}
	public void setEnrollmentDisplayFlag(String enrollmentDisplayFlag) {
		this.enrollmentDisplayFlag = enrollmentDisplayFlag;
	}
	public String getPaymentDisplayFlag() {
		return paymentDisplayFlag;
	}
	public void setPaymentDisplayFlag(String paymentDisplayFlag) {
		this.paymentDisplayFlag = paymentDisplayFlag;
	}
	public String getRequestChangeDisplayFlag() {
		return requestChangeDisplayFlag;
	}
	public void setRequestChangeDisplayFlag(String requestChangeDisplayFlag) {
		this.requestChangeDisplayFlag = requestChangeDisplayFlag;
	}
	public void setOtherBoard(List<String> otherBoard)
	{
		this.otherBoard = otherBoard;
	}
	public List<String> getOtherBoard()
	{
		return otherBoard;
	}
	public void setBoardOther(String boardOther)
	{
		this.boardOther = boardOther;
	}
	public String getBoardOther()
	{
		return boardOther;
	}
	public String getChallanBranchName() {
		return challanBranchName;
	}
	public void setChallanBranchName(String challanBranchName) {
		this.challanBranchName = challanBranchName;
	}
	public void setCurrSectionToDisplay(String currSectionToDisplay)
	{
		this.currSectionToDisplay = currSectionToDisplay;
	}
	public String getCurrSectionToDisplay()
	{
		return currSectionToDisplay;
	}
	public void setActionName(String actionName)
	{
		this.actionName = actionName;
	}
	public String getActionName()
	{
		return actionName;
	}
	public String getStateName()
	{
		return stateName;
	}
	public void setStateName(String stateName)
	{
		this.stateName = stateName;
	}
	public String getCityName()
	{
		return cityName;
	}
	public void setCityName(String cityName)
	{
		this.cityName = cityName;
	}
	public String getAltStateName()
	{
		return altStateName;
	}
	public void setAltStateName(String altStateName)
	{
		this.altStateName = altStateName;
	}
	public String getAltCityName()
	{
		return altCityName;
	}
	public void setAltCityName(String altCityName)
	{
		this.altCityName = altCityName;
	}
	public void setExamName(String examName)
	{
		this.examName = examName;
	}
	public String getExamName()
	{
		return examName;
	}
	public void setBoardUniversityName(String boardUniversityName)
	{
		this.boardUniversityName = boardUniversityName;
	}
	public String getBoardUniversityName()
	{
		return boardUniversityName;
	}
	public List<String> getExamNameList()
	{
		return examNameList;
	}
	public void setExamNameList(List<String> examNameList)
	{
		this.examNameList = examNameList;
	}
	public List<String> getBoardUniversityNameList()
	{
		return boardUniversityNameList;
	}
	public void setBoardUniversityNameList(List<String> boardUniversityNameList)
	{
		this.boardUniversityNameList = boardUniversityNameList;
	}
	public void setPreferredTestcentre1Name(String preferredTestcentre1Name)
	{
		this.preferredTestcentre1Name = preferredTestcentre1Name;
	}
	public String getPreferredTestcentre1Name()
	{
		return preferredTestcentre1Name;
	}
	public void setPreferredTestcentre2Name(String preferredTestcentre2Name)
	{
		this.preferredTestcentre2Name = preferredTestcentre2Name;
	}
	public String getPreferredTestcentre2Name()
	{
		return preferredTestcentre2Name;
	}
	public void setPreferredTestCentre3Name(String preferredTestCentre3Name)
	{
		this.preferredTestCentre3Name = preferredTestCentre3Name;
	}
	public String getPreferredTestCentre3Name()
	{
		return preferredTestCentre3Name;
	}
	public void setTestCenterCode(String testCenterCode) {
		this.testCenterCode = testCenterCode;
	}
	public String getTestCenterCode() {
		return testCenterCode;
	}
	public void setDesciplineCode(String desciplineCode) {
		this.desciplineCode = desciplineCode;
	}
	public String getDesciplineCode() {
		return desciplineCode;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public String getRollNo() {
		return rollNo;
	}
	public String getBatchPk() {
		return batchPk;
	}
	public void setBatchPk(String batchPk) {
		this.batchPk = batchPk;
	}
	public String getPreferedTestDate1() {
		return preferedTestDate1;
	}
	public void setPreferedTestDate1(String preferedTestDate1) {
		this.preferedTestDate1 = preferedTestDate1;
	}
	public String getPreferedTestDate2() {
		return preferedTestDate2;
	}
	public void setPreferedTestDate2(String preferedTestDate2) {
		this.preferedTestDate2 = preferedTestDate2;
	}
	public String getPreferedTestDate3() {
		return preferedTestDate3;
	}
	public void setPreferedTestDate3(String preferedTestDate3) {
		this.preferedTestDate3 = preferedTestDate3;
	}
}
