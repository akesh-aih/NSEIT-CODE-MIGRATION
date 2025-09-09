package com.nseit.generic.models;

public class PaymentReportBean {

	private String OUM_USER_ID;
	private String CND_NAME;
	private String OTM_TEST_NAME;
	private String OPTM_PAYMENT_CODE;
	private String OPD_CREATED_DATE;
	private Double OPD_AMOUNT;
	private String OPD_BANK_NAME;
	private String OPD_VALIDATED_STATUS;
	private String OPD_REMARKS;
	private String OPTM_PAYMENT_DESC;
	private String appNo;
	private String candName;
	private String transSerial;
	private String reason;
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getCandName() {
		return candName;
	}
	public void setCandName(String candName) {
		this.candName = candName;
	}
	public String getTransSerial() {
		return transSerial;
	}
	public void setTransSerial(String transSerial) {
		this.transSerial = transSerial;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	private int num;
	
	
	public String getOUM_USER_ID() {
		return OUM_USER_ID;
	}
	public void setOUM_USER_ID(String oUMUSERID) {
		OUM_USER_ID = oUMUSERID;
	}
	public String getCND_NAME() {
		return CND_NAME;
	}
	public void setCND_NAME(String cNDNAME) {
		CND_NAME = cNDNAME;
	}
	public String getOTM_TEST_NAME() {
		return OTM_TEST_NAME;
	}
	public void setOTM_TEST_NAME(String oTMTESTNAME) {
		OTM_TEST_NAME = oTMTESTNAME;
	}
	public String getOPTM_PAYMENT_CODE() {
		return OPTM_PAYMENT_CODE;
	}
	public void setOPTM_PAYMENT_CODE(String oPTMPAYMENTCODE) {
		OPTM_PAYMENT_CODE = oPTMPAYMENTCODE;
	}
	public String getOPD_CREATED_DATE() {
		return OPD_CREATED_DATE;
	}
	public void setOPD_CREATED_DATE(String oPDCREATEDDATE) {
		OPD_CREATED_DATE = oPDCREATEDDATE;
	}
	public Double getOPD_AMOUNT() {
		return OPD_AMOUNT;
	}
	public void setOPD_AMOUNT(Double oPDAMOUNT) {
		OPD_AMOUNT = oPDAMOUNT;
	}
	public String getOPD_BANK_NAME() {
		return OPD_BANK_NAME;
	}
	public void setOPD_BANK_NAME(String oPDBANKNAME) {
		OPD_BANK_NAME = oPDBANKNAME;
	}
	public String getOPD_VALIDATED_STATUS() {
		return OPD_VALIDATED_STATUS;
	}
	public void setOPD_VALIDATED_STATUS(String oPDVALIDATEDSTATUS) {
		OPD_VALIDATED_STATUS = oPDVALIDATEDSTATUS;
	}
	public String getOPD_REMARKS() {
		return OPD_REMARKS;
	}
	public void setOPD_REMARKS(String oPDREMARKS) {
		OPD_REMARKS = oPDREMARKS;
	}
	public String getOPTM_PAYMENT_DESC() {
		return OPTM_PAYMENT_DESC;
	}
	public void setOPTM_PAYMENT_DESC(String oPTMPAYMENTDESC) {
		OPTM_PAYMENT_DESC = oPTMPAYMENTDESC;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getNum() {
		return num;
	}
}
