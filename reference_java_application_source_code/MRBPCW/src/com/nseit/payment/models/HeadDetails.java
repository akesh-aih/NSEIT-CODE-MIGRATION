package com.nseit.payment.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HeadDetails {

	private String version;
	private String payMode;
	private String channel;
	private String api;
	private String stage;
	private String platform;
	private String source;
}
