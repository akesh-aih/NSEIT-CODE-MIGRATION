package com.nseit.generic.models;

import java.io.InputStream;
import java.util.List;

public class PaymentBean {
	private String updated_trnx_date;
	private String bankID;
	private String bankName;
	private InputStream bankLogoImage;
	private String paymentPK;
	private String paymentModeDD;
	private String paymentModeCH;
	private String checkStatusDate;
	private String paymentModeOnline;
	private long userFK;
	private String transactionDate;
	private String transactionNo;
	private String bid;
	private String itc;
	private String currency;
	private String amount;
	private String stage;
	private String createdBy;
	private String createdDate;
	private String bank_fk;
	private String ddChallanReceiptNo;
	private int disciplineId;
	private int ocjm_user_fk;
	private int ocjm_test_fk;
	private String enableButton;
	// Here Set all DD Details List

	public int getOcjm_user_fk() {
		return ocjm_user_fk;
	}

	public void setOcjm_user_fk(int ocjmUserFk) {
		ocjm_user_fk = ocjmUserFk;
	}

	public int getOcjm_test_fk() {
		return ocjm_test_fk;
	}

	public void setOcjm_test_fk(int ocjmTestFk) {
		ocjm_test_fk = ocjmTestFk;
	}

	private String ddDate;
	private String ddBankCode;
	private String ddCityCode;
	private String ddNumber;
	// here set Challan Details

	private String scrollNumber;
	private String challanDate;
	private String challanCityCode;
	private String challanBankCode;

	private String submitVal = "";

	private String challanBranchName;
	// End Challan

	private PaymentBean ddpaymentBean;
	private PaymentBean challanpaymentBean;

	private PaymentBean onlinePaymentBean;
	private String paymentDisplayFlag;

	private List<CommonBean> cityList;
	private List<CommonBean> bankList;

	private String PRN;
	private String AMT;
	private String Bank;
	private String BID;
	private String EEDID;
	private String PAID;
	private String paymentStartDate;
	private String paymentEndDate;
	private String displayPaymentFlag;
	private String displayRegFlag;
	private String otbsDisplayFlag;

	private String paymentModeType;
	private String errMsg;
	private String clientTransactionNo;
	private String defaultValue;

	private String authenticateStatus;
	private String validatedStatus;
	private String opdStatus;

	public String getAuthenticateStatus() {
		return authenticateStatus;
	}

	public void setAuthenticateStatus(String authenticateStatus) {
		this.authenticateStatus = authenticateStatus;
	}

	public String getValidatedStatus() {
		return validatedStatus;
	}

	public void setValidatedStatus(String validatedStatus) {
		this.validatedStatus = validatedStatus;
	}

	public String getOpdStatus() {
		return opdStatus;
	}

	public void setOpdStatus(String opdStatus) {
		this.opdStatus = opdStatus;
	}

	public String getChallanBranchName() {
		return challanBranchName;
	}

	public void setChallanBranchName(String challanBranchName) {
		this.challanBranchName = challanBranchName;
	}

	public String getBank_fk() {
		return bank_fk;
	}

	public void setBank_fk(String bankFk) {
		bank_fk = bankFk;
	}

	public String getDdChallanReceiptNo() {
		return ddChallanReceiptNo;
	}

	public void setDdChallanReceiptNo(String ddChallanReceiptNo) {
		this.ddChallanReceiptNo = ddChallanReceiptNo;
	}

	// Setter and Getter Method
	public String getBankID() {
		return bankID;
	}

	public void setBankID(String bankID) {
		this.bankID = bankID;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getPaymentPK() {
		return paymentPK;
	}

	public void setPaymentPK(String paymentPK) {
		this.paymentPK = paymentPK;
	}

	public String getPaymentModeDD() {
		return paymentModeDD;
	}

	public void setPaymentModeDD(String paymentModeDD) {
		this.paymentModeDD = paymentModeDD;
	}

	public String getPaymentModeCH() {
		return paymentModeCH;
	}

	public void setPaymentModeCH(String paymentModeCH) {
		this.paymentModeCH = paymentModeCH;
	}

	public long getUserFK() {
		return userFK;
	}

	public void setUserFK(long userFK) {
		this.userFK = userFK;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getItc() {
		return itc;
	}

	public void setItc(String itc) {
		this.itc = itc;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getDdDate() {
		return ddDate;
	}

	public void setDdDate(String ddDate) {
		this.ddDate = ddDate;
	}

	public String getDdBankCode() {
		return ddBankCode;
	}

	public void setDdBankCode(String ddBankCode) {
		this.ddBankCode = ddBankCode;
	}

	public String getDdNumber() {
		return ddNumber;
	}

	public String getScrollNumber() {
		return scrollNumber;
	}

	public void setScrollNumber(String scrollNumber) {
		this.scrollNumber = scrollNumber;
	}

	public String getChallanDate() {
		return challanDate;
	}

	public void setChallanDate(String challanDate) {
		this.challanDate = challanDate;
	}

	public String getChallanCityCode() {
		return challanCityCode;
	}

	public void setChallanCityCode(String challanCityCode) {
		this.challanCityCode = challanCityCode;
	}

	public String getChallanBankCode() {
		return challanBankCode;
	}

	public void setChallanBankCode(String challanBankCode) {
		this.challanBankCode = challanBankCode;
	}

	public void setDdNumber(String ddNumber) {
		this.ddNumber = ddNumber;
	}

	public String getDdCityCode() {
		return ddCityCode;
	}

	public void setDdCityCode(String ddCityCode) {
		this.ddCityCode = ddCityCode;
	}

	public String getSubmitVal() {
		return submitVal;
	}

	public void setSubmitVal(String submitVal) {
		this.submitVal = submitVal;
	}

	public void setDisciplineId(int disciplineId) {
		this.disciplineId = disciplineId;
	}

	public int getDisciplineId() {
		return disciplineId;
	}

	public void setDdpaymentBean(PaymentBean ddpaymentBean) {
		this.ddpaymentBean = ddpaymentBean;
	}

	public PaymentBean getDdpaymentBean() {
		return ddpaymentBean;
	}

	public void setChallanpaymentBean(PaymentBean challanpaymentBean) {
		this.challanpaymentBean = challanpaymentBean;
	}

	public PaymentBean getChallanpaymentBean() {
		return challanpaymentBean;
	}

	public void setPaymentDisplayFlag(String paymentDisplayFlag) {
		this.paymentDisplayFlag = paymentDisplayFlag;
	}

	public String getPaymentDisplayFlag() {
		return paymentDisplayFlag;
	}

	public void setCityList(List<CommonBean> cityList) {
		this.cityList = cityList;
	}

	public List<CommonBean> getCityList() {
		return cityList;
	}

	public void setBankList(List<CommonBean> bankList) {
		this.bankList = bankList;
	}

	public List<CommonBean> getBankList() {
		return bankList;
	}

	public String getPRN() {
		return PRN;
	}

	public void setPRN(String pRN) {
		PRN = pRN;
	}

	public String getAMT() {
		return AMT;
	}

	public void setAMT(String aMT) {
		AMT = aMT;
	}

	public String getBank() {
		return Bank;
	}

	public void setBank(String bank) {
		Bank = bank;
	}

	public String getBID() {
		return BID;
	}

	public void setBID(String bID) {
		BID = bID;
	}

	public String getEEDID() {
		return EEDID;
	}

	public void setEEDID(String eEDID) {
		EEDID = eEDID;
	}

	public String getPAID() {
		return PAID;
	}

	public void setPAID(String pAID) {
		PAID = pAID;
	}

	public InputStream getBankLogoImage() {
		return bankLogoImage;
	}

	public void setBankLogoImage(InputStream bankLogoImage) {
		this.bankLogoImage = bankLogoImage;
	}

	public PaymentBean getOnlinePaymentBean() {
		return onlinePaymentBean;
	}

	public void setOnlinePaymentBean(PaymentBean onlinePaymentBean) {
		this.onlinePaymentBean = onlinePaymentBean;
	}

	public String getPaymentModeOnline() {
		return paymentModeOnline;
	}

	public void setPaymentModeOnline(String paymentModeOnline) {
		this.paymentModeOnline = paymentModeOnline;
	}

	public String getPaymentStartDate() {
		return paymentStartDate;
	}

	public void setPaymentStartDate(String paymentStartDate) {
		this.paymentStartDate = paymentStartDate;
	}

	public String getPaymentEndDate() {
		return paymentEndDate;
	}

	public void setPaymentEndDate(String paymentEndDate) {
		this.paymentEndDate = paymentEndDate;
	}

	public String getDisplayPaymentFlag() {
		return displayPaymentFlag;
	}

	public void setDisplayPaymentFlag(String displayPaymentFlag) {
		this.displayPaymentFlag = displayPaymentFlag;
	}

	public String getDisplayRegFlag() {
		return displayRegFlag;
	}

	public void setDisplayRegFlag(String displayRegFlag) {
		this.displayRegFlag = displayRegFlag;
	}

	public String getOtbsDisplayFlag() {
		return otbsDisplayFlag;
	}

	public void setOtbsDisplayFlag(String otbsDisplayFlag) {
		this.otbsDisplayFlag = otbsDisplayFlag;
	}

	public String getPaymentModeType() {
		return paymentModeType;
	}

	public void setPaymentModeType(String paymentModeType) {
		this.paymentModeType = paymentModeType;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setClientTransactionNo(String clientTransactionNo) {
		this.clientTransactionNo = clientTransactionNo;
	}

	public String getClientTransactionNo() {
		return clientTransactionNo;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public String getUpdated_trnx_date() {
		return updated_trnx_date;
	}

	public void setUpdated_trnx_date(String updated_trnx_date) {
		this.updated_trnx_date = updated_trnx_date;
	}

	public String getEnableButton() {
		return enableButton;
	}

	public void setEnableButton(String enableButton) {
		this.enableButton = enableButton;
	}

	public String getCheckStatusDate() {
		return checkStatusDate;
	}

	public void setCheckStatusDate(String checkStatusDate) {
		this.checkStatusDate = checkStatusDate;
	}

}
