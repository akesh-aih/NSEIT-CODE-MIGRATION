package com.nseit.generic.models;

import java.util.List;

public class EmailSMSTransactionBean
{
	private long emailSMSTransactionId;
	private String emailSubject;
	private String smsEmailContent;
	private List<String> toAddress;
	private List<String> ccAddress;
	private List<String> bccAddress;
	private String smsMailFlag;
	private String fromAddress;
	private Integer emailSmsFailureCount;
	private String sentStatus;
	private String startTime;
	private Long userPk;
	private String statusPk;
	private String candidateMailAddress;	
	public List<String> getToAddress()
	{
		return toAddress;
	}
	public void setToAddress(List<String> toAddress)
	{
		this.toAddress = toAddress;
	}
	public List<String> getCcAddress()
	{
		return ccAddress;
	}
	public void setCcAddress(List<String> ccAddress)
	{
		this.ccAddress = ccAddress;
	}
	public String getEmailSubject()
	{
		return emailSubject;
	}
	public void setEmailSubject(String emailSubject)
	{
		this.emailSubject = emailSubject;
	}
	public String getSmsEmailContent()
	{
		return smsEmailContent;
	}
	public void setSmsEmailContent(String smsEmailContent)
	{
		this.smsEmailContent = smsEmailContent;
	}
	public void setSmsMailFlag(String smsMailFlag)
	{
		this.smsMailFlag = smsMailFlag;
	}
	public String getSmsMailFlag()
	{
		return smsMailFlag;
	}
	public void setBccAddress(List<String> bccAddress)
	{
		this.bccAddress = bccAddress;
	}
	public List<String> getBccAddress()
	{
		return bccAddress;
	}
	public void setFromAddress(String fromAddress)
	{
		this.fromAddress = fromAddress;
	}
	public String getFromAddress()
	{
		return fromAddress;
	}
	public long getEmailSMSTransactionId()
	{
		return emailSMSTransactionId;
	}
	public void setEmailSMSTransactionId(long emailSMSTransactionId)
	{
		this.emailSMSTransactionId = emailSMSTransactionId;
	}
	public Integer getEmailSmsFailureCount()
	{
		return emailSmsFailureCount;
	}
	public void setEmailSmsFailureCount(Integer emailSmsFailureCount)
	{
		this.emailSmsFailureCount = emailSmsFailureCount;
	}
	public void setSentStatus(String sentStatus)
	{
		this.sentStatus = sentStatus;
	}
	public String getSentStatus()
	{
		return sentStatus;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public Long getUserPk() {
		return userPk;
	}
	public void setUserPk(Long userPk) {
		this.userPk = userPk;
	}
	public String getStatusPk() {
		return statusPk;
	}
	public void setStatusPk(String statusPk) {
		this.statusPk = statusPk;
	}
	public String getCandidateMailAddress() {
		return candidateMailAddress;
	}
	public void setCandidateMailAddress(String candidateMailAddress) {
		this.candidateMailAddress = candidateMailAddress;
	}
}
