package com.nseit.payment.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProdDetails {
	private String prodName;
	private String prodAmount;
	private String prodRefundAmount;
	private String prodRefundId;
}
