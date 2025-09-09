package com.nseit.generic.models;

public class SessionDetailsBean {

	private String OSD_USER_ID;
	private String OSD_SESSION;
	private String OSD_STATUS;
	private String OSD_CREATED_BY;
	private String OSD_CREATED_DATE;
	private String OSD_UPDATED_BY;
	private String OSD_UPDATED_DATE;
	private String OSD_USER_TYPE;
	private String OUM_STATUS;
	private int OUM_INVAL_ATTEMPT_COUNT;
	private String OUM_USER_ID;

	public String getOUM_STATUS() {
		return OUM_STATUS;
	}

	public void setOUM_STATUS(String oUMSTATUS) {
		OUM_STATUS = oUMSTATUS;
	}

	public int getOUM_INVAL_ATTEMPT_COUNT() {
		return OUM_INVAL_ATTEMPT_COUNT;
	}

	public void setOUM_INVAL_ATTEMPT_COUNT(int oUMINVALATTEMPTCOUNT) {
		OUM_INVAL_ATTEMPT_COUNT = oUMINVALATTEMPTCOUNT;
	}

	public String getOUM_USER_ID() {
		return OUM_USER_ID;
	}

	public void setOUM_USER_ID(String oUMUSERID) {
		OUM_USER_ID = oUMUSERID;
	}

	public String getOSD_USER_ID() {
		return OSD_USER_ID;
	}

	public void setOSD_USER_ID(String oSDUSERID) {
		OSD_USER_ID = oSDUSERID;
	}

	public String getOSD_SESSION() {
		return OSD_SESSION;
	}

	public void setOSD_SESSION(String oSDSESSION) {
		OSD_SESSION = oSDSESSION;
	}

	public String getOSD_STATUS() {
		return OSD_STATUS;
	}

	public void setOSD_STATUS(String oSDSTATUS) {
		OSD_STATUS = oSDSTATUS;
	}

	public String getOSD_CREATED_BY() {
		return OSD_CREATED_BY;
	}

	public void setOSD_CREATED_BY(String oSDCREATEDBY) {
		OSD_CREATED_BY = oSDCREATEDBY;
	}

	public String getOSD_CREATED_DATE() {
		return OSD_CREATED_DATE;
	}

	public void setOSD_CREATED_DATE(String oSDCREATEDDATE) {
		OSD_CREATED_DATE = oSDCREATEDDATE;
	}

	public String getOSD_UPDATED_BY() {
		return OSD_UPDATED_BY;
	}

	public void setOSD_UPDATED_BY(String oSDUPDATEDBY) {
		OSD_UPDATED_BY = oSDUPDATEDBY;
	}

	public String getOSD_UPDATED_DATE() {
		return OSD_UPDATED_DATE;
	}

	public void setOSD_UPDATED_DATE(String oSDUPDATEDDATE) {
		OSD_UPDATED_DATE = oSDUPDATEDDATE;
	}

	public String getOSD_USER_TYPE() {
		return OSD_USER_TYPE;
	}

	public void setOSD_USER_TYPE(String oSDUSERTYPE) {
		OSD_USER_TYPE = oSDUSERTYPE;
	}
}
