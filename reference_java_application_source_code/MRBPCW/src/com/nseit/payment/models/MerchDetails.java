package com.nseit.payment.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MerchDetails {

	private String merchId;
	private String userId;
	private String password;
	private String merchTxnId;
	private String merchType;
	private String mccCode;
	private String merchTxnDate;
}
