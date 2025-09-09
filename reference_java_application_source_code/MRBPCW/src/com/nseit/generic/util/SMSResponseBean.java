package com.nseit.generic.util;

import java.io.Serializable;

public class SMSResponseBean implements Serializable {

	public static final String ROOTELEMENT = "RESULT";
	public static final String CODE = "CODE";
	public static final String DESC = "DESC";
	public static final String ERROR = "ERROR";
	public static final String REQUESTERROR = "REQUEST-ERROR";
	public static final String MID = "MID";
	public static final String ID = "ID";
	public static final String TAG = "TAG";
	public static final String TID = "TID";
	public static final String REQID = "REQID";
	public static final String SUBMITDATE = "SUBMITDATE";
	
	//public static final String docType = " <!DOCTYPE RESULT SYSTEM 'D:/IIIjar/BulkSmsRespV1.00.dtd'> "; 
	public static final String smsResponseTypeSuccess = "SUCCESS";
	public static final String smsResponseTypeError = "ERROR";
	
	private String code;
	private String desc;
	private String id;
	private String tag;
	private String tid;
	private String reqId;
	private String submitDate;
	private String mobileNo;
	private String moduleName;    // SCHEDULING, RESCHEDULING OR ALERTS
	private String responseType;  // success or error
	
	
	public SMSResponseBean(){
		this.setResponseType(smsResponseTypeSuccess);
		this.desc = "";
	}
	
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	public String getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	
	public static void setObjectProperties(String propName, String propValue, SMSResponseBean smsResponseJB){
		try{
			if(propName!=null
					&& !propName.equals("")
					&& !propName.equalsIgnoreCase("null")
					&& propValue!=null
					&& smsResponseJB!=null){
				
				if(propName.equalsIgnoreCase(REQID)){
					smsResponseJB.setReqId(propValue);
				}else if(propName.equalsIgnoreCase(SUBMITDATE)){
					smsResponseJB.setSubmitDate(propValue);
				}else if(propName.equalsIgnoreCase(ID)){
					smsResponseJB.setId(propValue);
				}else if(propName.equalsIgnoreCase(TAG)){
					smsResponseJB.setTag(propValue);
				}else if(propName.equalsIgnoreCase(TID)){
					smsResponseJB.setTid(propValue);
				}else if(propName.equalsIgnoreCase(CODE)){
					smsResponseJB.setCode(propValue);
					smsResponseJB.setResponseType(smsResponseTypeError);
				}else if(propName.equalsIgnoreCase(DESC)){
					smsResponseJB.setDesc(propValue);
				}
			}
		}catch(Exception e){
			
			e.printStackTrace();
		}
	}
}

