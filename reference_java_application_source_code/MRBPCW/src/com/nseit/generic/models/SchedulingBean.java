package com.nseit.generic.models;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author Sanjeev
 *
 */
public class SchedulingBean {
	//GET_TEST_TESTCENTRE_FOR_CANDIDATE
	private String testName;
	private String testCentreName; 
	
	
	private String enrollmentPK;
	private String userFK;
	private String testFK;
	private String preferredTestCentre1FK;
	private String preferredTestCentre2FK;
	private String preferredTestCentre3FK; 
	private String status;
	private String createdBy;
	private Date createdDate;
	
	//GET_BATCH_DETAILS_FOR_TEST_CENTRE
	private String batchPK;
	private String testCentreFK;
	private String testDate;
	private Timestamp testStartime;
	private Timestamp testEndTime;
	private String batchType;
	private String availableCapacity;
	//variable def for parameter in query GET_BATCH_DETAILS_FOR_TEST_CENTRE
	private Long testCenterPk;
	
	private String startTimeStr;
	private String endTimeStr;

	private String stage;
	
	//GET_INTERVIEW_ENROLLMENT_DETAILS_FOR_CANDIDATE
	private String interivewCentername = "";
	private String rollNo;
	private String firstName;
	private String lastName;
	private Date batchTestDate;
	private Timestamp batchTestStartTime; 
	private Timestamp batchTestEndTime; 
	
	private String  slotBatchId = "";
	private String  slotAvailablity = "";
	

	//INSERT_INTERVIEW_ENROLLMENT_DETAILS
	
	
	private String batchFK;
	private String interviewEnrollmentPK;
	private String testCenterFkForInterview;	
	
	//flags for disabling button
	private Boolean testFlag = false;
	private String  interviewFlag = "true";
	
	
	
	//for request reTest
	
	private List<CommonBean> remarksDtailsList;
	private String reasonListId;
	private String retestReason;
	private Timestamp reTestDate;
	private String retestCenterName;
	
	private String reTestDateStr;
	private String reasonFlag ;
	private String retestDisplayFlag;
	private String requestRetstApprovalFlag;
	
	
	
	private String batchSeatAllotedFlag;
	private String interviewSeatAllotedFlag;
	
	
	
	private String retestStageCompareFlag;
	
	
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public String getTestCentreName() {
		return testCentreName;
	}
	public void setTestCentreName(String testCentreName) {
		this.testCentreName = testCentreName;
	}
	public String getEnrollmentPK() {
		return enrollmentPK;
	}
	public void setEnrollmentPK(String enrollmentPK) {
		this.enrollmentPK = enrollmentPK;
	}
	public String getUserFK() {
		return userFK;
	}
	public void setUserFK(String userFK) {
		this.userFK = userFK;
	}
	public String getTestFK() {
		return testFK;
	}
	public void setTestFK(String testFK) {
		this.testFK = testFK;
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
	public String getPreferredTestCentre3FK() {
		return preferredTestCentre3FK;
	}
	public void setPreferredTestCentre3FK(String preferredTestCentre3FK) {
		this.preferredTestCentre3FK = preferredTestCentre3FK;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getBatchPK() {
		return batchPK;
	}
	public void setBatchPK(String batchPK) {
		this.batchPK = batchPK;
	}
	public String getTestCentreFK() {
		return testCentreFK;
	}
	public void setTestCentreFK(String testCentreFK) {
		this.testCentreFK = testCentreFK;
	}
	public String getTestDate() {
		return testDate;
	}
	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}
	public Timestamp getTestStartime() {
		return testStartime;
	}
	public void setTestStartime(Timestamp testStartime) {
		this.testStartime = testStartime;
	}
	public Timestamp getTestEndTime() {
		return testEndTime;
	}
	public void setTestEndTime(Timestamp testEndTime) {
		this.testEndTime = testEndTime;
	}
	public String getBatchType() {
		return batchType;
	}
	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}
	public String getAvailableCapacity() {
		return availableCapacity;
	}
	public void setAvailableCapacity(String availableCapacity) {
		this.availableCapacity = availableCapacity;
	}
	public Long getTestCenterPk() {
		return testCenterPk;
	}
	public void setTestCenterPk(Long testCenterPk) {
		this.testCenterPk = testCenterPk;
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
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
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
	public Date getBatchTestDate() {
		return batchTestDate;
	}
	public void setBatchTestDate(Date batchTestDate) {
		this.batchTestDate = batchTestDate;
	}
	public Timestamp getBatchTestStartTime() {
		return batchTestStartTime;
	}
	public void setBatchTestStartTime(Timestamp batchTestStartTime) {
		this.batchTestStartTime = batchTestStartTime;
	}
	public Timestamp getBatchTestEndTime() {
		return batchTestEndTime;
	}
	public void setBatchTestEndTime(Timestamp batchTestEndTime) {
		this.batchTestEndTime = batchTestEndTime;
	}
	public String getSlotBatchId() {
		return slotBatchId;
	}
	public void setSlotBatchId(String slotBatchId) {
		this.slotBatchId = slotBatchId;
	}
	public String getSlotAvailablity() {
		return slotAvailablity;
	}
	public void setSlotAvailablity(String slotAvailablity) {
		this.slotAvailablity = slotAvailablity;
	}
	public String getBatchFK() {
		return batchFK;
	}
	public void setBatchFK(String batchFK) {
		this.batchFK = batchFK;
	}
	public String getInterviewEnrollmentPK() {
		return interviewEnrollmentPK;
	}
	public void setInterviewEnrollmentPK(String interviewEnrollmentPK) {
		this.interviewEnrollmentPK = interviewEnrollmentPK;
	}
	public Boolean getTestFlag() {
		return testFlag;
	}
	public void setTestFlag(Boolean testFlag) {
		this.testFlag = testFlag;
	}
	public String getInterviewFlag() {
		return interviewFlag;
	}
	public void setInterviewFlag(String interviewFlag) {
		this.interviewFlag = interviewFlag;
	}
	public String getRetestReason() {
		return retestReason;
	}
	public void setRetestReason(String retestReason) {
		this.retestReason = retestReason;
	}
	public List<CommonBean> getRemarksDtailsList() {
		return remarksDtailsList;
	}
	public void setRemarksDtailsList(List<CommonBean> remarksDtailsList) {
		this.remarksDtailsList = remarksDtailsList;
	}
	public String getReasonListId() {
		return reasonListId;
	}
	public void setReasonListId(String reasonListId) {
		this.reasonListId = reasonListId;
	}
	public Timestamp getReTestDate() {
		return reTestDate;
	}
	public void setReTestDate(Timestamp reTestDate) {
		this.reTestDate = reTestDate;
	}
	public String getRetestCenterName() {
		return retestCenterName;
	}
	public void setRetestCenterName(String retestCenterName) {
		this.retestCenterName = retestCenterName;
	}
	public String getReTestDateStr() {
		return reTestDateStr;
	}
	public void setReTestDateStr(String reTestDateStr) {
		this.reTestDateStr = reTestDateStr;
	}
	public String getInterivewCentername() {
		return interivewCentername;
	}
	public void setInterivewCentername(String interivewCentername) {
		this.interivewCentername = interivewCentername;
	}
	public String getTestCenterFkForInterview() {
		return testCenterFkForInterview;
	}
	public void setTestCenterFkForInterview(String testCenterFkForInterview) {
		this.testCenterFkForInterview = testCenterFkForInterview;
	}
	public String getReasonFlag() {
		return reasonFlag;
	}
	public void setReasonFlag(String reasonFlag) {
		this.reasonFlag = reasonFlag;
	}
	public String getRetestDisplayFlag() {
		return retestDisplayFlag;
	}
	public void setRetestDisplayFlag(String retestDisplayFlag) {
		this.retestDisplayFlag = retestDisplayFlag;
	}
	public String getBatchSeatAllotedFlag() {
		return batchSeatAllotedFlag;
	}
	public void setBatchSeatAllotedFlag(String batchSeatAllotedFlag) {
		this.batchSeatAllotedFlag = batchSeatAllotedFlag;
	}
	public String getInterviewSeatAllotedFlag() {
		return interviewSeatAllotedFlag;
	}
	public void setInterviewSeatAllotedFlag(String interviewSeatAllotedFlag) {
		this.interviewSeatAllotedFlag = interviewSeatAllotedFlag;
	}
	public String getRequestRetstApprovalFlag() {
		return requestRetstApprovalFlag;
	}
	public void setRequestRetstApprovalFlag(String requestRetstApprovalFlag) {
		this.requestRetstApprovalFlag = requestRetstApprovalFlag;
	}
	public String getRetestStageCompareFlag() {
		return retestStageCompareFlag;
	}
	public void setRetestStageCompareFlag(String retestStageCompareFlag) {
		this.retestStageCompareFlag = retestStageCompareFlag;
	}
	
}
