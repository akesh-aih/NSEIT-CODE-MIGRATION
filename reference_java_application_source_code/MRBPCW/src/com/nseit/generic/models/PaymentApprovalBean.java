/**
 * 
 */
package com.nseit.generic.models;

import java.util.List;

/**
 * @author Sanjeev Kumar Rai
 *
 */

/**
 * @author Hrishikesh
 *
 */
public class PaymentApprovalBean {
	
	
	//===========For DD==========
	private String enrollment_pk;
	private String firstName;
	private String lastName;
	private String testName;
	private String testCenterName;
	private String batchTestStartTime;
	private String batchTestEndTime;
	private String ddChalanReciptNO;
	private String ddDate;
	private String bankName;
	private Long testPK;
//	private Double amount;
	private String amount;
	private String paymentMode;
	private String paymentPK;
	private List<String> paymentCheckedValue;
	private String branchName;
	private String branchCode;
	private String userPK;
	
	private String remark;
	
	private String applicableFee;
	
	private String contactNumber;
	
	private String bankCity;
	
	private String flagForEdit;
	
	private String editBtnFlag;
	
	private String createdBy;
	
	private String validatedStatus;
	
	public String getEnrollment_pk() {
		return enrollment_pk;
	}
	public void setEnrollment_pk(String enrollmentPk) {
		enrollment_pk = enrollmentPk;
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
	public String getBatchTestStartTime() {
		return batchTestStartTime;
	}
	public void setBatchTestStartTime(String batchTestStartTime) {
		this.batchTestStartTime = batchTestStartTime;
	}
	public String getBatchTestEndTime() {
		return batchTestEndTime;
	}
	public void setBatchTestEndTime(String batchTestEndTime) {
		this.batchTestEndTime = batchTestEndTime;
	}
	public String getDdChalanReciptNO() {
		return ddChalanReciptNO;
	}
	public void setDdChalanReciptNO(String ddChalanReciptNO) {
		this.ddChalanReciptNO = ddChalanReciptNO;
	}
	public String getDdDate() {
		return ddDate;
	}
	public void setDdDate(String ddDate) {
		this.ddDate = ddDate;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public Long getTestPK() {
		return testPK;
	}
	public void setTestPK(Long testPK) {
		this.testPK = testPK;
	}
	/*public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}*/
	public String getPaymentMode() {
		return paymentMode;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	
	public String getPaymentPK()
    {
        return paymentPK;
    }
    public void setPaymentPK(String paymentPK)
    {
        this.paymentPK = paymentPK;
    }
    public List<String> getPaymentCheckedValue() {
		return paymentCheckedValue;
	}
	public void setPaymentCheckedValue(List<String> paymentCheckedValue) {
		this.paymentCheckedValue = paymentCheckedValue;
	}
	public void setBranchName(String branchName)
	{
		this.branchName = branchName;
	}
	public String getBranchName()
	{
		return branchName;
	}
	public void setUserPK(String userPK)
	{
		this.userPK = userPK;
	}
	public String getUserPK()
	{
		return userPK;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
    public String getApplicableFee()
    {
        return applicableFee;
    }
    public void setApplicableFee(String applicableFee)
    {
        this.applicableFee = applicableFee;
    }
    public String getContactNumber()
    {
        return contactNumber;
    }
    public void setContactNumber(String contactNumber)
    {
        this.contactNumber = contactNumber;
    }
    public String getBranchCode()
    {
        return branchCode;
    }
    public void setBranchCode(String branchCode)
    {
        this.branchCode = branchCode;
    }
    public String getBankCity()
    {
        return bankCity;
    }
    public void setBankCity(String bankCity)
    {
        this.bankCity = bankCity;
    }
    public String getFlagForEdit()
    {
        return flagForEdit;
    }
    public void setFlagForEdit(String flagForEdit)
    {
        this.flagForEdit = flagForEdit;
    }
    public String getCreatedBy()
    {
        return createdBy;
    }
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }
    public String getEditBtnFlag()
    {
        return editBtnFlag;
    }
    public void setEditBtnFlag(String editBtnFlag)
    {
        this.editBtnFlag = editBtnFlag;
    }
    public String getValidatedStatus()
    {
        return validatedStatus;
    }
    public void setValidatedStatus(String validatedStatus)
    {
        this.validatedStatus = validatedStatus;
    }
	
    
	
}
