package com.nseit.generic.models;

import java.sql.Timestamp;
import java.util.List;

public class EmailSMSMasterBean
{
	private int emailSMSMasterId;
	private int testId;
	private int activityId;
	private String mailObject;
	private String smsObject;
	private String emailSubject;
	private List<String> ccAddress;
	private List<String> bccAddress;
	private boolean mailApplicable;
	private boolean smsApplicable;
	private Timestamp lastUpdatedTimestamp;
	
	public int getEmailSMSMasterId()
	{
		return emailSMSMasterId;
	}
	public void setEmailSMSMasterId(int emailSMSMasterId)
	{
		this.emailSMSMasterId = emailSMSMasterId;
	}
	public int getTestId()
	{
		return testId;
	}
	public void setTestId(int testId)
	{
		this.testId = testId;
	}
	public int getActivityId()
	{
		return activityId;
	}
	public void setActivityId(int activityId)
	{
		this.activityId = activityId;
	}
	public String getMailObject()
	{
		return mailObject;
	}
	public void setMailObject(String mailObject)
	{
		this.mailObject = mailObject;
	}
	public String getSmsObject()
	{
		return smsObject;
	}
	public void setSmsObject(String smsObject)
	{
		this.smsObject = smsObject;
	}
	public String getEmailSubject()
	{
		return emailSubject;
	}
	public void setEmailSubject(String emailSubject)
	{
		this.emailSubject = emailSubject;
	}
	public List<String> getCcAddress()
	{
		return ccAddress;
	}
	public void setCcAddress(List<String> ccAddress)
	{
		this.ccAddress = ccAddress;
	}
	public List<String> getBccAddress()
	{
		return bccAddress;
	}
	public void setBccAddress(List<String> bccAddress)
	{
		this.bccAddress = bccAddress;
	}
	public boolean isMailApplicable()
	{
		return mailApplicable;
	}
	public void setMailApplicable(boolean mailApplicable)
	{
		this.mailApplicable = mailApplicable;
	}
	public boolean isSmsApplicable()
	{
		return smsApplicable;
	}
	public void setSmsApplicable(boolean smsApplicable)
	{
		this.smsApplicable = smsApplicable;
	}
	public Timestamp getLastUpdatedTimestamp()
	{
		return lastUpdatedTimestamp;
	}
	public void setLastUpdatedTimestamp(Timestamp lastUpdatedTimestamp)
	{
		this.lastUpdatedTimestamp = lastUpdatedTimestamp;
	}
}
