package com.nseit.generic.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgeMatrix {
	private int record_pk;
	private String category;
	private String isesm;
	private int max_age;
	private Date minDate;
	private String isUpGovTemp;

	public AgeMatrix(int record_pk, String category,String isesm,int max_age,Date minDate,String isUpGoveTemp) {
		this.record_pk=record_pk;
		this.category=category;
		this.isesm=isesm;
		this.max_age=max_age;
		this.minDate=minDate;
		this.isUpGovTemp=isUpGoveTemp;
	} 

	
}
