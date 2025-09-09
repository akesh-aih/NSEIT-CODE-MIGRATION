package com.nseit.generic.models;

import java.io.File;

/**
 * @author shailendras
 *
 */
public class UploadManagementBean {

	private File fileUpload;
	private String fileUploadContentType;
	private String fileUploadFileName;
	private byte[] byteArrayImage;
	private String imageType;
	private String signType;
	private String candidateDetailsType;
	private String imageSummary;
	private String signSummary;
	private Integer imageCount = new Integer(0);
	private Integer signCount = new Integer(0);
	private String uploaded;
	private String executionTime;
	
	private String candidateID;
	private String candidateName;
	private Long candidateProgram;
	private Long candidateMarks;
	private Long programsTotalMark;
	private String uploadType;
	private Integer csvRecordCountTotal = new Integer(0);
	private Integer csvRecordCountSuccess = new Integer(0);
	private Integer csvRecordCountFailure = new Integer(0);
	private String marksSummary;
	
	

	private String transactionType;
	private String transactionNo;
	private String transactionDate;
	private String bankName;
	private String bankCode;
	private String bankBranchName;
	private String bankBranchCode;
	private String bankCityName;
	private String transactionDtls;
	private String paymentStatus;
	private String paymentSummary;
	
	private String enrollmentID;
	private String testCenterID;
	private String testCenterName;
	private String moduleID;
	private String testDate;
	private String testTime;
	private String marks;
	private String examStatus;
	private String eligible;
	private String prefix;
	private String fname;
	private String mname;
	private String lname;
	private String phone;
	private String mobile;
	private String email;
	private String dob;
	private String address;
	private String pincode;
	private String city;
	private String othercity;
	private String state;
	private String rollno;
	private String password;
	private String photo;
	private String sign;
	private String gender;
	private String education;
	private String graduationpercentage;
	private String clientRollNo;
	private String clientregno;
	private String roomNo;
	private String gracetimeallowed;
	private String gracetime;
	private String category;
	private String testcityId;
	private String exemptionId1;
	private String exemptionId2;
	private String candidateStatus;
	private String testcity;
	private String testcitymastercode;
	private String fieldTestCity;
	private String fieldTestDate;
	private String interviewCity;
	private String interviewDate;
	private String medicalCity;
	private String medicalDate;
	private String interviewLocation;
	private String interviewAddress;
	private String interviewCityId;
	private String interviewReportTime;
	private String medicalTestLocation;
	private String medicalTestAddress;
	private String medicalTestCityId;
	private String medicalTestReportingTime;
	private String entrollmentFk;
	private String fieldTestStatus;
	private String fieldTestReportingTime;
	private String interviewStatus;
	private String medicalStatus;
	private String medicalTestDate;
	private String scoreForFieldTest;
	private String scoreForInterview;
	
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

	public byte[] getByteArrayImage() {
		return byteArrayImage;
	}

	public void setByteArrayImage(byte[] byteArrayImage) {
		this.byteArrayImage = byteArrayImage;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getCandidateDetailsType() {
		return candidateDetailsType;
	}

	public void setCandidateDetailsType(String candidateDetailsType) {
		this.candidateDetailsType = candidateDetailsType;
	}

	public String getImageSummary() {
		return imageSummary;
	}

	public void setImageSummary(String imageSummary) {
		this.imageSummary = imageSummary;
	}

	public String getSignSummary() {
		return signSummary;
	}

	public void setSignSummary(String signSummary) {
		this.signSummary = signSummary;
	}

	public Integer getImageCount() {
		return imageCount;
	}

	public void setImageCount(Integer imageCount) {
		this.imageCount = imageCount;
	}

	public Integer getSignCount() {
		return signCount;
	}

	public void setSignCount(Integer signCount) {
		this.signCount = signCount;
	}

	public String getUploaded() {
		return uploaded;
	}

	public void setUploaded(String uploaded) {
		this.uploaded = uploaded;
	}

	public String getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(String executionTime) {
		this.executionTime = executionTime;
	}

	public String getCandidateID() {
		return candidateID;
	}

	public void setCandidateID(String candidateID) {
		this.candidateID = candidateID;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public Long getCandidateProgram() {
		return candidateProgram;
	}

	public void setCandidateProgram(Long candidateProgram) {
		this.candidateProgram = candidateProgram;
	}

	public Long getCandidateMarks() {
		return candidateMarks;
	}

	public void setCandidateMarks(Long candidateMarks) {
		this.candidateMarks = candidateMarks;
	}

	public String getUploadType() {
		return uploadType;
	}

	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}

	public Integer getCsvRecordCountTotal() {
		return csvRecordCountTotal;
	}

	public void setCsvRecordCountTotal(Integer csvRecordCountTotal) {
		this.csvRecordCountTotal = csvRecordCountTotal;
	}

	public Integer getCsvRecordCountSuccess() {
		return csvRecordCountSuccess;
	}

	public void setCsvRecordCountSuccess(Integer csvRecordCountSuccess) {
		this.csvRecordCountSuccess = csvRecordCountSuccess;
	}

	public Integer getCsvRecordCountFailure() {
		return csvRecordCountFailure;
	}

	public void setCsvRecordCountFailure(Integer csvRecordCountFailure) {
		this.csvRecordCountFailure = csvRecordCountFailure;
	}

	public String getMarksSummary() {
		return marksSummary;
	}

	public void setMarksSummary(String marksSummary) {
		this.marksSummary = marksSummary;
	}

	public String getPaymentSummary() {
		return paymentSummary;
	}

	public void setPaymentSummary(String paymentSummary) {
		this.paymentSummary = paymentSummary;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankBranchName() {
		return bankBranchName;
	}

	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	public String getBankBranchCode() {
		return bankBranchCode;
	}

	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}

	public String getBankCityName() {
		return bankCityName;
	}

	public void setBankCityName(String bankCityName) {
		this.bankCityName = bankCityName;
	}

	public String getTransactionDtls() {
		return transactionDtls;
	}

	public void setTransactionDtls(String transactionDtls) {
		this.transactionDtls = transactionDtls;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Long getProgramsTotalMark() {
		return programsTotalMark;
	}

	public void setProgramsTotalMark(Long programsTotalMark) {
		this.programsTotalMark = programsTotalMark;
	}

	public void setEnrollmentID(String enrollmentID) {
		this.enrollmentID = enrollmentID;
	}

	public String getEnrollmentID() {
		return enrollmentID;
	}

	public void setTestCenterID(String testCenterID) {
		this.testCenterID = testCenterID;
	}

	public String getTestCenterID() {
		return testCenterID;
	}

	public void setTestCenterName(String testCenterName) {
		this.testCenterName = testCenterName;
	}

	public String getTestCenterName() {
		return testCenterName;
	}

	public void setModuleID(String moduleID) {
		this.moduleID = moduleID;
	}

	public String getModuleID() {
		return moduleID;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}

	public String getTestTime() {
		return testTime;
	}

	public void setExamStatus(String examStatus) {
		this.examStatus = examStatus;
	}

	public String getExamStatus() {
		return examStatus;
	}

	public void setEligible(String eligible) {
		this.eligible = eligible;
	}

	public String getEligible() {
		return eligible;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	public String getMarks() {
		return marks;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getFname() {
		return fname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getMname() {
		return mname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getLname() {
		return lname;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return mobile;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getDob() {
		return dob;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getPincode() {
		return pincode;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setOthercity(String othercity) {
		this.othercity = othercity;
	}

	public String getOthercity() {
		return othercity;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setRollno(String rollno) {
		this.rollno = rollno;
	}

	public String getRollno() {
		return rollno;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPhoto() {
		return photo;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSign() {
		return sign;
	}

	public void setGraduationpercentage(String graduationpercentage) {
		this.graduationpercentage = graduationpercentage;
	}

	public String getGraduationpercentage() {
		return graduationpercentage;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getEducation() {
		return education;
	}

	public void setGracetimeallowed(String gracetimeallowed) {
		this.gracetimeallowed = gracetimeallowed;
	}

	public String getGracetimeallowed() {
		return gracetimeallowed;
	}

	public void setClientRollNo(String clientRollNo) {
		this.clientRollNo = clientRollNo;
	}

	public String getClientRollNo() {
		return clientRollNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public void setCandidateStatus(String candidateStatus) {
		this.candidateStatus = candidateStatus;
	}

	public String getCandidateStatus() {
		return candidateStatus;
	}

	public void setGracetime(String gracetime) {
		this.gracetime = gracetime;
	}

	public String getGracetime() {
		return gracetime;
	}

	public void setTestcityId(String testcityId) {
		this.testcityId = testcityId;
	}

	public String getTestcityId() {
		return testcityId;
	}

	public void setExemptionId1(String exemptionId1) {
		this.exemptionId1 = exemptionId1;
	}

	public String getExemptionId1() {
		return exemptionId1;
	}

	public void setExemptionId2(String exemptionId2) {
		this.exemptionId2 = exemptionId2;
	}

	public String getExemptionId2() {
		return exemptionId2;
	}

	public void setTestcity(String testcity) {
		this.testcity = testcity;
	}

	public String getTestcity() {
		return testcity;
	}

	public void setClientregno(String clientregno) {
		this.clientregno = clientregno;
	}

	public String getClientregno() {
		return clientregno;
	}

	public void setTestcitymastercode(String testcitymastercode) {
		this.testcitymastercode = testcitymastercode;
	}

	public String getTestcitymastercode() {
		return testcitymastercode;
	}

	public void setFieldTestCity(String fieldTestCity) {
		this.fieldTestCity = fieldTestCity;
	}

	public String getFieldTestCity() {
		return fieldTestCity;
	}

	public void setFieldTestDate(String fieldTestDate) {
		this.fieldTestDate = fieldTestDate;
	}

	public String getFieldTestDate() {
		return fieldTestDate;
	}

	public void setInterviewCity(String interviewCity) {
		this.interviewCity = interviewCity;
	}

	public String getInterviewCity() {
		return interviewCity;
	}

	public void setInterviewDate(String interviewDate) {
		this.interviewDate = interviewDate;
	}

	public String getInterviewDate() {
		return interviewDate;
	}
	public void setMedicalCity(String medicalCity) {
		this.medicalCity = medicalCity;
	}

	public String getMedicalCity() {
		return medicalCity;
	}

	public void setMedicalDate(String medicalDate) {
		this.medicalDate = medicalDate;
	}
	
	public void setEntrollmentFk(String entrollmentFk) {
		this.entrollmentFk = entrollmentFk;
	}

	public String getEntrollmentFk() {
		return entrollmentFk;
	}

	public String getMedicalDate() {
		return medicalDate;
	}

	public void setInterviewLocation(String interviewLocation) {
		this.interviewLocation = interviewLocation;
	}

	public String getInterviewLocation() {
		return interviewLocation;
	}

	public void setInterviewAddress(String interviewAddress) {
		this.interviewAddress = interviewAddress;
	}

	public String getInterviewAddress() {
		return interviewAddress;
	}

	public void setInterviewCityId(String interviewCityId) {
		this.interviewCityId = interviewCityId;
	}

	public String getInterviewCityId() {
		return interviewCityId;
	}

	public void setInterviewReportTime(String interviewReportTime) {
		this.interviewReportTime = interviewReportTime;
	}

	public String getInterviewReportTime() {
		return interviewReportTime;
	}

	public void setMedicalTestLocation(String medicalTestLocation) {
		this.medicalTestLocation = medicalTestLocation;
	}

	public String getMedicalTestLocation() {
		return medicalTestLocation;
	}

	public void setMedicalTestAddress(String medicalTestAddress) {
		this.medicalTestAddress = medicalTestAddress;
	}

	public String getMedicalTestAddress() {
		return medicalTestAddress;
	}

	public void setMedicalTestCityId(String medicalTestCityId) {
		this.medicalTestCityId = medicalTestCityId;
	}

	public String getMedicalTestCityId() {
		return medicalTestCityId;
	}

	public void setMedicalTestReportingTime(String medicalTestReportingTime) {
		this.medicalTestReportingTime = medicalTestReportingTime;
	}

	public String getMedicalTestReportingTime() {
		return medicalTestReportingTime;
	}

	public String getFieldTestStatus() {
		return fieldTestStatus;
	}

	public void setFieldTestStatus(String fieldTestStatus) {
		this.fieldTestStatus = fieldTestStatus;
	}

	public String getFieldTestReportingTime() {
		return fieldTestReportingTime;
	}

	public void setFieldTestReportingTime(String fieldTestReportingTime) {
		this.fieldTestReportingTime = fieldTestReportingTime;
	}

	public void setInterviewStatus(String interviewStatus) {
		this.interviewStatus = interviewStatus;
	}

	public String getInterviewStatus() {
		return interviewStatus;
	}

	public void setMedicalStatus(String medicalStatus) {
		this.medicalStatus = medicalStatus;
	}

	public String getMedicalStatus() {
		return medicalStatus;
	}

	public void setMedicalTestDate(String medicalTestDate) {
		this.medicalTestDate = medicalTestDate;
	}

	public String getMedicalTestDate() {
		return medicalTestDate;
	}

	public String getScoreForFieldTest() {
		return scoreForFieldTest;
	}

	public void setScoreForFieldTest(String scoreForFieldTest) {
		this.scoreForFieldTest = scoreForFieldTest;
	}

	public String getScoreForInterview() {
		return scoreForInterview;
	}

	public void setScoreForInterview(String scoreForInterview) {
		this.scoreForInterview = scoreForInterview;
	}

	
}
