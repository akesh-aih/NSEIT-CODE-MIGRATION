package com.nseit.payment.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayInstrument {

	private HeadDetails headDetails;
	private MerchDetails merchDetails;
	private PayDetails payDetails;
	private Extras extras;
	private CustDetails custDetails;
	private ResponseDetails responseDetails;
	private PayModeSpecificData payModeSpecificData;
}
