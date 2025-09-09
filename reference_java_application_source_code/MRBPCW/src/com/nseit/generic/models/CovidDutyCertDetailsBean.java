package com.nseit.generic.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CovidDutyCertDetailsBean {

	private String institutionType;
	private String nameOfMedInstitution;
	private String districtVal;
	private String addressOfInstitute;

	private String fromYear;
	private String fromMonth;
	private String toYear;
	private String toMonth;
	private String durationOfCovidService;
	private String expierence;

	private String certificateSignedBy;
	private String certiCounterSignedBy;
	private Integer ocdc_cdc_pk;
	private String yesNo;

	private String wrkexp_id;
	private String yearExp;
	private String monthExp;
	
}
