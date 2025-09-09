package com.nseit.otbs.model;

public class BatchDetailsBean {
	
	private String testDate;
	private String testDay;
	private String testStartTime;
	private String testEndTime;
	private String availableSeats;
	private int batchPK;
	private String reportingTime;
	private String OBD_BATCH_PK;
	private String OBD_AVAILABLE_CAPACITY;
	
	
	public String getTestDate() {
		return testDate;
	}
	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}
	public String getTestDay() {
		return testDay;
	}
	public void setTestDay(String testDay) {
		this.testDay = testDay;
	}
	public String getTestStartTime() {
		return testStartTime;
	}
	public void setTestStartTime(String testStartTime) {
		this.testStartTime = testStartTime;
	}
	public String getTestEndTime() {
		return testEndTime;
	}
	public void setTestEndTime(String testEndTime) {
		this.testEndTime = testEndTime;
	}
	public String getAvailableSeats() {
		return availableSeats;
	}
	public void setAvailableSeats(String availableSeats) {
		this.availableSeats = availableSeats;
	}
	public void setBatchPK(int batchPK) {
		this.batchPK = batchPK;
	}
	public int getBatchPK() {
		return batchPK;
	}
	public void setReportingTime(String reportingTime) {
		this.reportingTime = reportingTime;
	}
	public String getReportingTime() {
		return reportingTime;
	}
	public String getOBD_BATCH_PK() {
		return OBD_BATCH_PK;
	}
	public void setOBD_BATCH_PK(String oBDBATCHPK) {
		OBD_BATCH_PK = oBDBATCHPK;
	}
	public String getOBD_AVAILABLE_CAPACITY() {
		return OBD_AVAILABLE_CAPACITY;
	}
	public void setOBD_AVAILABLE_CAPACITY(String oBDAVAILABLECAPACITY) {
		OBD_AVAILABLE_CAPACITY = oBDAVAILABLECAPACITY;
	}
}
