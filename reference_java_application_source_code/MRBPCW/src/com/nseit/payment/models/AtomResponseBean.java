package com.nseit.payment.models;

import java.util.List;

import com.nseit.generic.models.Users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtomResponseBean {
	private Users logUser;
	private int applicationNumber;
	private int app_id;
	private String responseMsg;
	private String transaction;
	private String authStatus;
	private String candidateUserId;
	private String mmp_txn;
	private String transactionMessage;
	private String mer_txn;
	private String response_amt;
	private String txn_resp_date;
	private String bank_txn;
	private String statusCode;
	private String jobCode;
	private List<AtomResponseBean> responseDetailsList;
	private String prod;
	private String responseOFlag; 
	private String enableButton;
	private String desc;
	private String discriminator;
	private String errMsg;
	private String showModuleDetails;
	private String customerName;
	private String clientcode;
	private String customerMobile;
	private String customerEmail;
	private String candidateFk;
	private String MerchantID;
	private String MerchantTxnID;
	private String AMT;
	private String VERIFIED;
	private String BID;
	private String bankname;
	private String atomtxnId;
	private String surcharge;
	private String CardNumber;
	private String TxnDate;
	private String UDF9;
	private String reconstatus;
	private String sdt;
	private String[] subChannel;
}
