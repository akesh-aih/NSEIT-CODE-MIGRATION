package com.nseit.generic.models;

import java.io.InputStream;

public class HallTicketBean {

	private String disciplineType;
	private String userId;
	private String userName;
	private String userPassword;
	private String testCenterAddress;
	private String testCenterName;
	private String testDate;
	private String testStartTime;
	private String reportingTime;
	private String enrollmetPk;
	private int bookingAttempt;
	private Long userFK;
	private String bookingAttmptStatus;
	private String batchPk;
	private String testCenterId;
	private String rollNumber;
	private String testSlot;
	private String hallticketFlag;
	private String bookingAttemptVal;
	private String category;
	private String candidateName;
	private String testName;
	private String candidateAddress;
	
	private InputStream inputStreamForImage;
	
	private InputStream inputStreamForSignature;
	
	public HallTicketBean() {
	}

	public String getDisciplineType() {
		return disciplineType;
	}

	public void setDisciplineType(String disciplineType) {
		this.disciplineType = disciplineType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getTestCenterAddress() {
		return testCenterAddress;
	}

	public void setTestCenterAddress(String testCenterAddress) {
		this.testCenterAddress = testCenterAddress;
	}

	public String getTestCenterName() {
		return testCenterName;
	}

	public void setTestCenterName(String testCenterName) {
		this.testCenterName = testCenterName;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public String getTestStartTime() {
		return testStartTime;
	}

	public void setTestStartTime(String testStartTime) {
		this.testStartTime = testStartTime;
	}

	public String getReportingTime() {
		return reportingTime;
	}

	public void setReportingTime(String reportingTime) {
		this.reportingTime = reportingTime;
	}

	public String getEnrollmetPk() {
		return enrollmetPk;
	}

	public void setEnrollmetPk(String enrollmetPk) {
		this.enrollmetPk = enrollmetPk;
	}

	public int getBookingAttempt() {
		return bookingAttempt;
	}

	public void setBookingAttempt(int bookingAttempt) {
		this.bookingAttempt = bookingAttempt;
	}

	public Long getUserFK() {
		return userFK;
	}

	public void setUserFK(Long userFK) {
		this.userFK = userFK;
	}

	public String getBookingAttmptStatus() {
		return bookingAttmptStatus;
	}

	public void setBookingAttmptStatus(String bookingAttmptStatus) {
		this.bookingAttmptStatus = bookingAttmptStatus;
	}

	public String getBatchPk() {
		return batchPk;
	}

	public void setBatchPk(String batchPk) {
		this.batchPk = batchPk;
	}

	public String getTestCenterId() {
		return testCenterId;
	}

	public void setTestCenterId(String testCenterId) {
		this.testCenterId = testCenterId;
	}

	public String getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}

	public String getTestSlot() {
		return testSlot;
	}

	public void setTestSlot(String testSlot) {
		this.testSlot = testSlot;
	}

	public String getHallticketFlag() {
		return hallticketFlag;
	}

	public void setHallticketFlag(String hallticketFlag) {
		this.hallticketFlag = hallticketFlag;
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

	public String getBookingAttemptVal() {
		return bookingAttemptVal;
	}

	public void setBookingAttemptVal(String bookingAttemptVal) {
		this.bookingAttemptVal = bookingAttemptVal;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestName() {
		return testName;
	}

	public void setCandidateAddress(String candidateAddress) {
		this.candidateAddress = candidateAddress;
	}

	public String getCandidateAddress() {
		return candidateAddress;
	}
	
	

	
}