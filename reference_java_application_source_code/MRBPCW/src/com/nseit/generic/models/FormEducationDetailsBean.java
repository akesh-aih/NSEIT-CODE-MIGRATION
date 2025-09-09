package com.nseit.generic.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormEducationDetailsBean {

	private String highestDegree;
	private String institute;
	private String university;	
	private String headOfDept;
	private String yearOfPassing;
	private String aggregate;
	private String dtbTrainingProg;
	private String sslcRegistrationNumber;
	private String sslcPassing;
	private String educationBorad;
	
}

