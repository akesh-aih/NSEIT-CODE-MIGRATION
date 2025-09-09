package com.nseit.generic.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkExperienceBean {

	private String highEduDate;
	private String specialOption;
	private String applyNotifyDate;
	private String staffMiniExexutiveOption;
	private Map<Integer, String> yesNoMap;
	private Map<Integer, String> yesNoMap1;

	private String workexp_pk;
	private String phdDateOfReg;
	private String DateOfViva;
	private String jobRole;
	private String maxPay;
	private String minPay;
	private String yesNo;
	private String sponsor;
	private String eduDate;
	private String yesNoB;
	private int firstDate;
	private String orgOthers;
	private String di1;
	private String di2;
	private List<String> discliplineSelectedList = new ArrayList<String>();

	private List<String> errorField;

	private Map<Integer, String> sectorList;
	private Map<Integer, String> areaList;
	private Map<Integer, String> organizationNatureList;
	private Map<Integer, String> organizationList;
	private List<WorkExperienceDetailsBean> workExperienceDetailsList;
	private List<String> yearExperienceList = new ArrayList<String>();
	private List<String> monthExperienceList = new ArrayList<String>();
	private List<String> yearList = new ArrayList<String>();
	private List<String> monthList = new ArrayList<String>();
	private String serverSideErrorMessage = "false";
	private boolean workExperienceDetailsMandatory = false;
	private boolean dataFound = false;
	private String saveFlag = "false";
	private String yearOfExperience;
	private String monthOfExperience;
	private String workExp;
	private String policedept;
	private String GpfNumber;
	private String Enlistment;
	private String presentRank;
	private String Govtemp;
	private String policeMedals;
	private String policeMedalsName;
	private String unitsOther;
	private String dutyYear;
	private String event;
	private String PresentPostingUnit;
	private String presentPosting;
	private Map<String, String> presentRankList = new LinkedHashMap<String, String>();
	private Map<String, String> unitList = new LinkedHashMap<String, String>();
	private Map<Integer, String> districtList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> policeStationList = new LinkedHashMap<Integer, String>();
	private String policeStation;
	private String stageUpdate;
	private String dayOfExperience;
	private String yearOfExperienceHidden;
	private String monthOfExperienceHidden;
	private String dayOfExperienceHidden;
	private String disciplineId;
	private String wrkExpPosts;
	private String wrkExpFlag;

	Map<Integer, String> MedalList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> medalList1 = new LinkedHashMap<Integer, String>();

	private List<WorkExperienceDetailsBean> workExperienceSaveList;
}