package com.nseit.payment.models;

public class OesPaymentDetails {

	private String oum_user_pk;
	private String user_id;
	private String user_name;
	private String OPD_DD_CHALLAN_RECEIPT_NO;
	private String OPD_AMOUNT;
	private String branchname;
	private String branchcode;
	private String challan_date;
	private String mobile_number;
	private String ocd_test_fk;
	private String ocjm_applicationnumber;
	private String rec_cand_name;
	private String rec_trans_ser;
	private String rec_reason;

	public String getRec_cand_name() {
		return rec_cand_name;
	}

	public void setRec_cand_name(String recCandName) {
		rec_cand_name = recCandName;
	}

	public String getRec_trans_ser() {
		return rec_trans_ser;
	}

	public void setRec_trans_ser(String recTransSer) {
		rec_trans_ser = recTransSer;
	}

	public String getRec_reason() {
		return rec_reason;
	}

	public void setRec_reason(String recReason) {
		rec_reason = recReason;
	}

	public String getOum_user_pk() {
		return oum_user_pk;
	}

	public void setOum_user_pk(String oumUserPk) {
		oum_user_pk = oumUserPk;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String userId) {
		user_id = userId;
	}

	public String getOPD_DD_CHALLAN_RECEIPT_NO() {
		return OPD_DD_CHALLAN_RECEIPT_NO;
	}

	public void setOPD_DD_CHALLAN_RECEIPT_NO(String oPDDDCHALLANRECEIPTNO) {
		OPD_DD_CHALLAN_RECEIPT_NO = oPDDDCHALLANRECEIPTNO;
	}

	public String getOPD_AMOUNT() {
		return OPD_AMOUNT;
	}

	public void setOPD_AMOUNT(String oPDAMOUNT) {
		OPD_AMOUNT = oPDAMOUNT;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String userName) {
		user_name = userName;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public String getBranchcode() {
		return branchcode;
	}

	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}

	public String getChallan_date() {
		return challan_date;
	}

	public void setChallan_date(String challanDate) {
		challan_date = challanDate;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobileNumber) {
		mobile_number = mobileNumber;
	}

	public String getOcd_test_fk() {
		return ocd_test_fk;
	}

	public void setOcd_test_fk(String ocdTestFk) {
		ocd_test_fk = ocdTestFk;
	}

	public void setOcjm_applicationnumber(String ocjm_applicationnumber) {
		this.ocjm_applicationnumber = ocjm_applicationnumber;
	}

	public String getOcjm_applicationnumber() {
		return ocjm_applicationnumber;
	}
}
