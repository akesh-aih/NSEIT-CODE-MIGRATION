package com.nseit.generic.models;

public class ConfigParam
{
	private long PK;
	private String keyName;
	private String keyValue;
	private String description;
	private String dataType;
	private int minLength;
	private int maxLength;
	private String restartReq;
	private String remarks;
	private String status;
	
	public ConfigParam(long pK, String keyName, String keyValue,
			String description, String dataType, int minLength, int maxLength,
			String restartReq, String remarks, String status) {
		super();
		PK = pK;
		this.keyName = keyName;
		this.keyValue = keyValue;
		this.description = description;
		this.dataType = dataType;
		this.minLength = minLength;
		this.maxLength = maxLength;
		this.restartReq = restartReq;
		this.remarks = remarks;
		this.status = status;
	}
	
	public ConfigParam(String keyName, String keyValue, String description,
			String dataType, String restartReq, String remarks, String status) {
		super();
		this.keyName = keyName;
		this.keyValue = keyValue;
		this.description = description;
		this.dataType = dataType;
		this.restartReq = restartReq;
		this.remarks = remarks;
		this.status = status;
	}
	
	public ConfigParam(String keyName, String keyValue, String dataType) {
		super();
		this.keyName = keyName;
		this.keyValue = keyValue;
		this.dataType = dataType;
	}

	public long getPK()
	{
		return PK;
	}
	public void setPK(long pK)
	{
		PK = pK;
	}
	public String getKeyName()
	{
		return keyName;
	}
	public void setKeyName(String keyName)
	{
		this.keyName = keyName;
	}
	public String getKeyValue()
	{
		return keyValue;
	}
	public void setKeyValue(String keyValue)
	{
		this.keyValue = keyValue;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public String getDataType()
	{
		return dataType;
	}
	public void setDataType(String dataType)
	{
		this.dataType = dataType;
	}
	public int getMinLength()
	{
		return minLength;
	}
	public void setMinLength(int minLength)
	{
		this.minLength = minLength;
	}
	public int getMaxLength()
	{
		return maxLength;
	}
	public void setMaxLength(int maxLength)
	{
		this.maxLength = maxLength;
	}
	public String getRestartReq()
	{
		return restartReq;
	}
	public void setRestartReq(String restartReq)
	{
		this.restartReq = restartReq;
	}
	public String getRemarks()
	{
		return remarks;
	}
	public void setRemarks(String remarks)
	{
		this.remarks = remarks;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
}
