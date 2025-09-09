package com.nseit.payment.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayDetails {

private List<ProdDetails> prodDetails;
	
	private String amount;
	private String surchargeAmount;
	private String totalAmount;
	private String custAccNo;
	private String custAccIfsc;
	private String clientCode;
	private String txnCurrency;
	private String signature;
	private String atomTxnId;
	private String totalRefundAmount;
	private String txnInitDate;
	private String txnCompleteDate;
	private String product;
}
