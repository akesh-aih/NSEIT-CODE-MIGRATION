package com.nseit.payment.models;

import java.util.List;

public class SBIResponseBean {

	private String enableButton;

	private String merchantId;

	private String customerId;

	private String txnReferenceNo;

	private String bankReferenceNo;

	private String txnAmount;

	private String bankId;

	private String bankMerchantId;

	private String txnType;

	private String securityID;

	private String txnDate;

	private String authStatus;

	private String errorStatus;

	private String errorDescription;

	private String checkSum;

	private String successFlag;

	private String transactionDate;

	private String paymentType;

	private String responseMsg;

	private String validateStatus;
	private String ShowModuleDetails;
	private String errMsg;

	private String sbiRefNo;

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getSecurityType() {
		return securityType;
	}

	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}

	public String getSecurityPassword() {
		return securityPassword;
	}

	public void setSecurityPassword(String securityPassword) {
		this.securityPassword = securityPassword;
	}

	public String getSettlementType() {
		return settlementType;
	}

	public void setSettlementType(String settlementType) {
		this.settlementType = settlementType;
	}

	public String getAdditionalInfo1() {
		return additionalInfo1;
	}

	public void setAdditionalInfo1(String additionalInfo1) {
		this.additionalInfo1 = additionalInfo1;
	}

	public String getAdditionalInfo2() {
		return additionalInfo2;
	}

	public void setAdditionalInfo2(String additionalInfo2) {
		this.additionalInfo2 = additionalInfo2;
	}

	public String getAdditionalInfo3() {
		return additionalInfo3;
	}

	public void setAdditionalInfo3(String additionalInfo3) {
		this.additionalInfo3 = additionalInfo3;
	}

	public String getAdditionalInfo4() {
		return additionalInfo4;
	}

	public void setAdditionalInfo4(String additionalInfo4) {
		this.additionalInfo4 = additionalInfo4;
	}

	public String getAdditionalInfo5() {
		return additionalInfo5;
	}

	public void setAdditionalInfo5(String additionalInfo5) {
		this.additionalInfo5 = additionalInfo5;
	}

	public String getAdditionalInfo6() {
		return additionalInfo6;
	}

	public void setAdditionalInfo6(String additionalInfo6) {
		this.additionalInfo6 = additionalInfo6;
	}

	public String getAdditionalInfo7() {
		return additionalInfo7;
	}

	public void setAdditionalInfo7(String additionalInfo7) {
		this.additionalInfo7 = additionalInfo7;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getErrorDEsc() {
		return errorDEsc;
	}

	public void setErrorDEsc(String errorDEsc) {
		this.errorDEsc = errorDEsc;
	}

	private String currencyName;
	private String itemCode;
	private String securityType;
	private String securityPassword;
	private String settlementType;
	private String additionalInfo1;
	private String additionalInfo2;
	private String additionalInfo3;
	private String additionalInfo4;
	private String additionalInfo5;
	private String additionalInfo6;
	private String additionalInfo7;
	private String additionalInfo;
	private String errorDEsc;

	// Added for Bill Desk transaction Query API
	private String filler1;
	private String refundStatus;
	private String totalRefundAmount;
	private String lastRefundDate;
	private String lastRefundRefNo;
	private String queryStatus;

	private List<SBIResponseBean> responseDetailsList;
	private String responseOFlag;
	private String transactionMessage;

	/**
	 * @return the responseMsg
	 */
	public String getResponseMsg() {
		return responseMsg;
	}

	/**
	 * @param responseMsg
	 *            the responseMsg to set
	 */
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	/**
	 * @return the merchantId
	 */
	public String getMerchantId() {
		return merchantId;
	}

	/**
	 * @param merchantId
	 *            the merchantId to set
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the txnReferenceNo
	 */
	public String getTxnReferenceNo() {
		return txnReferenceNo;
	}

	/**
	 * @param txnReferenceNo
	 *            the txnReferenceNo to set
	 */
	public void setTxnReferenceNo(String txnReferenceNo) {
		this.txnReferenceNo = txnReferenceNo;
	}

	/**
	 * @return the bankReferenceNo
	 */
	public String getBankReferenceNo() {
		return bankReferenceNo;
	}

	/**
	 * @param bankReferenceNo
	 *            the bankReferenceNo to set
	 */
	public void setBankReferenceNo(String bankReferenceNo) {
		this.bankReferenceNo = bankReferenceNo;
	}

	/**
	 * @return the txnAmount
	 */
	public String getTxnAmount() {
		return txnAmount;
	}

	/**
	 * @param txnAmount
	 *            the txnAmount to set
	 */
	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}

	/**
	 * @return the bankId
	 */
	public String getBankId() {
		return bankId;
	}

	/**
	 * @param bankId
	 *            the bankId to set
	 */
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	/**
	 * @return the bankMerchantId
	 */
	public String getBankMerchantId() {
		return bankMerchantId;
	}

	/**
	 * @param bankMerchantId
	 *            the bankMerchantId to set
	 */
	public void setBankMerchantId(String bankMerchantId) {
		this.bankMerchantId = bankMerchantId;
	}

	/**
	 * @return the txnType
	 */
	public String getTxnType() {
		return txnType;
	}

	/**
	 * @param txnType
	 *            the txnType to set
	 */
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	/**
	 * @return the securityID
	 */
	public String getSecurityID() {
		return securityID;
	}

	/**
	 * @param securityID
	 *            the securityID to set
	 */
	public void setSecurityID(String securityID) {
		this.securityID = securityID;
	}

	/**
	 * @return the txnDate
	 */
	public String getTxnDate() {
		return txnDate;
	}

	/**
	 * @param txnDate
	 *            the txnDate to set
	 */
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	/**
	 * @return the authStatus
	 */
	public String getAuthStatus() {
		return authStatus;
	}

	/**
	 * @param authStatus
	 *            the authStatus to set
	 */
	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	/**
	 * @return the errorStatus
	 */
	public String getErrorStatus() {
		return errorStatus;
	}

	/**
	 * @param errorStatus
	 *            the errorStatus to set
	 */
	public void setErrorStatus(String errorStatus) {
		this.errorStatus = errorStatus;
	}

	/**
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * @param errorDescription
	 *            the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	/**
	 * @return the checkSum
	 */
	public String getCheckSum() {
		return checkSum;
	}

	/**
	 * @param checkSum
	 *            the checkSum to set
	 */
	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

	/**
	 * @return the successFlag
	 */
	public String getSuccessFlag() {
		return successFlag;
	}

	/**
	 * @param successFlag
	 *            the successFlag to set
	 */
	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}

	/**
	 * @return the transactionDate
	 */
	public String getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate
	 *            the transactionDate to set
	 */
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType
	 *            the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return the validateStatus
	 */
	public String getValidateStatus() {
		return validateStatus;
	}

	public void setShowModuleDetails(String showModuleDetails) {
		ShowModuleDetails = showModuleDetails;
	}

	public String getShowModuleDetails() {
		return ShowModuleDetails;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * @param validateStatus
	 *            the validateStatus to set
	 */
	public void setValidateStatus(String validateStatus) {
		this.validateStatus = validateStatus;
	}

	public void setFiller1(String filler1) {
		this.filler1 = filler1;
	}

	public String getFiller1() {
		return filler1;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setTotalRefundAmount(String totalRefundAmount) {
		this.totalRefundAmount = totalRefundAmount;
	}

	public String getTotalRefundAmount() {
		return totalRefundAmount;
	}

	public void setLastRefundDate(String lastRefundDate) {
		this.lastRefundDate = lastRefundDate;
	}

	public String getLastRefundDate() {
		return lastRefundDate;
	}

	public void setLastRefundRefNo(String lastRefundRefNo) {
		this.lastRefundRefNo = lastRefundRefNo;
	}

	public String getLastRefundRefNo() {
		return lastRefundRefNo;
	}

	public void setQueryStatus(String queryStatus) {
		this.queryStatus = queryStatus;
	}

	public String getQueryStatus() {
		return queryStatus;
	}

	public void setResponseDetailsList(List<SBIResponseBean> responseDetailsList) {
		this.responseDetailsList = responseDetailsList;
	}

	public List<SBIResponseBean> getResponseDetailsList() {
		return responseDetailsList;
	}

	public void setResponseOFlag(String responseOFlag) {
		this.responseOFlag = responseOFlag;
	}

	public String getResponseOFlag() {
		return responseOFlag;
	}

	public void setTransactionMessage(String transactionMessage) {
		this.transactionMessage = transactionMessage;
	}

	public String getTransactionMessage() {
		return transactionMessage;
	}

	public String getEnableButton() {
		return enableButton;
	}

	public void setEnableButton(String enableButton) {
		this.enableButton = enableButton;
	}

	public String getSbiRefNo() {
		return sbiRefNo;
	}

	public void setSbiRefNo(String sbiRefNo) {
		this.sbiRefNo = sbiRefNo;
	}
}
