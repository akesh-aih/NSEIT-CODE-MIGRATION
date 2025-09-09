package com.nseit.payment.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustDetails {

	private String custFirstName;
	private String custEmail;
	private String custMobile;
	private BillingInfo billingInfo;
}
