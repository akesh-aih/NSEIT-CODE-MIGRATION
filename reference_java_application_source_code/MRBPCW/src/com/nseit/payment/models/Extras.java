package com.nseit.payment.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Extras {

	private String udf1;
	private String udf2;
	private String udf3;
	private String udf4;
	private String udf5;
}
