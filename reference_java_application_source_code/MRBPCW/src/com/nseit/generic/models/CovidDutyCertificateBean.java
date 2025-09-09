package com.nseit.generic.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CovidDutyCertificateBean {

	private Map<Integer, String> institutionTypeMap;
	private Map<Integer, String> districtListMap = new LinkedHashMap<Integer, String>();

	private String disciplineId;

	private Map<Integer, String> yesNoMap;
	private String yesNo;

	private List<CovidDutyCertDetailsBean> covidDutyCertDetailsList;

	private String yearOfTotalService;
	private String monthOfTotalService;
	private String dayOfTotalService;
	private String dayOfTotalServiceHidden;
	private String yearOfTotalServiceHidden;
	private String monthOfTotalServiceHidden;

	private String workexp_pk;
	
	private String di1;
	private String di2;
	private List<String> discliplineSelectedList = new ArrayList<String>();

	private List<String> errorField;

	private List<String> yearExperienceList = new ArrayList<String>();
	private List<String> monthExperienceList = new ArrayList<String>();
	private List<String> yearList = new ArrayList<String>();
	private List<String> monthList = new ArrayList<String>();
	private String serverSideErrorMessage = "false";
	private boolean workExperienceDetailsMandatory = false;
	private boolean dataFound = false;
	private String saveFlag = "false";
	private String workExp;
	private Map<Integer, String> districtList = new LinkedHashMap<Integer, String>();
	private List<CovidDutyCertDetailsBean> covidDutyCertSaveList;
}
