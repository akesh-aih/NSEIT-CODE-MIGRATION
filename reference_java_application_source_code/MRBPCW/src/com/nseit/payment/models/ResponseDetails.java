package com.nseit.payment.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDetails {
	private String statusCode;
	private String message;
	private String description;
}
