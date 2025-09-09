package com.nseit.otbs.model;

import java.util.ArrayList;
import java.util.List;

import com.nseit.generic.models.HallTicketBean;

/**
 * @author Raman Pawar 
 * Added for OTBS
 */
public class SchedulingTestBean {
	
/* Added for City combo in OTBS  starts*/
private List<CommonBean> cityList = new ArrayList<CommonBean>();
private String cmbCity;
/* Added for City combo in OTBS  ends*/
/* Added for Date combo in OTBS  starts*/
private List<String>  dateList = new ArrayList<String>();	
private String cmbTestDate;
/* Added for Date combo in OTBS  ends*/
/* Added for Test Center combo in OTBS  Starts*/
private List<CommonBean> testCenterList = new ArrayList<CommonBean>();
private String cmbTstCenter;
/* Added for Test Center combo in OTBS  ends*/

private String cmbTestToDate;
private String cmbTestFromDate;
private String batchDisplayFlag;
private String tstCenterAddress;
private String otbsStartDt;
private String otbsEndDt;
private String downloadPath;
private String pathName;


private int bookingAttempt;
private int testDuration;
private String reportingTime;



private String candidateName;
private String discipline;
private String userId;
private int batchPK;
private String seatStatus;
private String testDate;
private String testCenter;
private String testTime;
private long userFK;
private String dateOfBirth;
private String enrollmentPK;
private String otbsDisplayFlag;
private String password;

private String candidateID;
private String rollNumberForHallTicket;

private String action;
private List<BatchDetailsBean> lstBatchDetails = new ArrayList<BatchDetailsBean>(); 

private byte[] barCodeImage;

EnrollmentDetailsBean enrollmenBean = new EnrollmentDetailsBean();
private HallTicketBean hallTicketBean = new HallTicketBean();
private String rollNoDisplayFlag;



public EnrollmentDetailsBean getEnrollmenBean() {
	return enrollmenBean;
}
public void setEnrollmenBean(EnrollmentDetailsBean enrollmenBean) {
	this.enrollmenBean = enrollmenBean;
}
public List<CommonBean> getCityList() {
	return cityList;
}
public String getCmbTestDate() {
	return cmbTestDate;
}
public void setCmbTestDate(String testDate) {
	this.cmbTestDate = testDate;
}
public String getCmbTstCenter() {
	return cmbTstCenter;
}
public void setCmbTstCenter(String tstCenter) {
	this.cmbTstCenter = tstCenter;
}
public void setCityList(List<CommonBean> cityList) {
	this.cityList = cityList;
}
public List<String> getDateList() {
	return dateList;
}
public void setDateList(List<String> dateList) {
	this.dateList = dateList;
}
public List<CommonBean> getTestCenterList() {
	return testCenterList;
}
public void setTestCenterList(List<CommonBean> testCenterList) {
	this.testCenterList = testCenterList;
}
public String getCmbCity() {
	return cmbCity;
}
public void setCmbCity(String cmbCity) {
	this.cmbCity = cmbCity;
}
public void setLstBatchDetails(List<BatchDetailsBean> lstBatchDetails) {
	this.lstBatchDetails = lstBatchDetails;
}
public List<BatchDetailsBean> getLstBatchDetails() {
	return lstBatchDetails;
}
public void setCmbTestFromDate(String cmbTestFromDate) {
	this.cmbTestFromDate = cmbTestFromDate;
}
public String getCmbTestFromDate() {
	return cmbTestFromDate;
}
public void setCmbTestToDate(String cmbTestToDate) {
	this.cmbTestToDate = cmbTestToDate;
}
public String getCmbTestToDate() {
	return cmbTestToDate;
}
public void setBatchDisplayFlag(String batchDisplayFlag) {
	this.batchDisplayFlag = batchDisplayFlag;
}
public String getBatchDisplayFlag() {
	return batchDisplayFlag;
}
public void setTstCenterAddress(String tstCenterAddress) {
	this.tstCenterAddress = tstCenterAddress;
}
public String getTstCenterAddress() {
	return tstCenterAddress;
}

public void setDiscipline(String discipline) {
	this.discipline = discipline;
}
public String getDiscipline() {
	return discipline;
}
public void setCandidateName(String candidateName) {
	this.candidateName = candidateName;
}
public String getCandidateName() {
	return candidateName;
}
public void setUserId(String userId) {
	this.userId = userId;
}
public String getUserId() {
	return userId;
}
public void setBatchPK(int batchPK) {
	this.batchPK = batchPK;
}
public int getBatchPK() {
	return batchPK;
}
public void setSeatStatus(String seatStatus) {
	this.seatStatus = seatStatus;
}
public String getSeatStatus() {
	return seatStatus;
}
public void setTestDate(String testDate) {
	this.testDate = testDate;
}
public String getTestDate() {
	return testDate;
}
public void setTestCenter(String testCenter) {
	this.testCenter = testCenter;
}
public String getTestCenter() {
	return testCenter;
}
public void setTestTime(String testTime) {
	this.testTime = testTime;
}
public String getTestTime() {
	return testTime;
}
public void setUserFK(long userFK) {
	this.userFK = userFK;
}
public long getUserFK() {
	return userFK;
}
public void setBookingAttempt(int bookingAttempt) {
	this.bookingAttempt = bookingAttempt;
}
public int getBookingAttempt() {
	return bookingAttempt;
}
public void setTestDuration(int testDuration) {
	this.testDuration = testDuration;
}
public int getTestDuration() {
	return testDuration;
}
public void setDateOfBirth(String dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
}
public String getDateOfBirth() {
	return dateOfBirth;
}
public String getPathName() {
	return pathName;
}
public void setPathName(String pathName) {
	this.pathName = pathName;
}
public String getDownloadPath() {
	return downloadPath;
}
public void setDownloadPath(String downloadPath) {
	this.downloadPath = downloadPath;
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
public void setEnrollmentPK(String enrollmentPK) {
	this.enrollmentPK = enrollmentPK;
}
public String getEnrollmentPK() {
	return enrollmentPK;
}
public void setReportingTime(String reportingTime) {
	this.reportingTime = reportingTime;
}
public String getReportingTime() {
	return reportingTime;
}
public String getOtbsDisplayFlag() {
	return otbsDisplayFlag;
}
public void setOtbsDisplayFlag(String otbsDisplayFlag) {
	this.otbsDisplayFlag = otbsDisplayFlag;
}
public String getAction() {
	return action;
}
public void setAction(String action) {
	this.action = action;
}

public byte [] getBarCodeImage() {
	return barCodeImage;
}
public void setBarCodeImage(byte [] barCodeImage) {
	this.barCodeImage = barCodeImage;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getCandidateID() {
	return candidateID;
}
public void setCandidateID(String candidateID) {
	this.candidateID = candidateID;
}
public HallTicketBean getHallTicketBean() {
	return hallTicketBean;
}
public void setHallTicketBean(HallTicketBean hallTicketBean) {
	this.hallTicketBean = hallTicketBean;
}
public String getRollNumberForHallTicket() {
	return rollNumberForHallTicket;
}
public void setRollNumberForHallTicket(String rollNumberForHallTicket) {
	this.rollNumberForHallTicket = rollNumberForHallTicket;
}
public String getRollNoDisplayFlag() {
	return rollNoDisplayFlag;
}
public void setRollNoDisplayFlag(String rollNoDisplayFlag) {
	this.rollNoDisplayFlag = rollNoDisplayFlag;
}
}
