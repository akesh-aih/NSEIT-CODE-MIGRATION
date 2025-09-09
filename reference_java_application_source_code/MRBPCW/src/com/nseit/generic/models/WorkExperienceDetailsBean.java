/**
*Added for Work Experience details - Anupa
*/
package com.nseit.generic.models;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkExperienceDetailsBean {

	private String maxPayLakh;
	private String maxPayThousnd;
	private String presentRankMini;
	private String presentPostingUnitMini;
	private String yesNo;
	private String workduration;
	private String area;
	private String organizationNature;
	private String organizationOthers;
	private String wrkexp_id;
	private String phdRegDate;
	private String vivaDate;
	private String isPhd;
	private String isSlet;
	private String isNet;
	private String mphilPassDate;
	private String phdDateofReg;
	private String vivaVoceDate;
	private String maxPay;
	private String minPay;
	private String yesNoA;
	private String yesNoB;
	private String jobRole;
	private String orgOthers;
	private String lastSalary;
	
	private String miniExecutiveStaff;
	private Integer OWE_WE_PK;
	private String organization;
	private String fromYear;
	private String fromMonth;
	private String toYear;
	private String toMonth;
	private String expierence;
	private String role;
	private String salary;
	private String jobDescription;
	private String yearExp;
	private String monthExp;
	private String policedept;
	private String GpfNumber;
	private String Enlistment;
	private String presentRank;
	private String presentPostingUnit;
	private String Govtemp;
	private String GovTempVal;
	private String policeMedals;
	private String policeMedalsName;
	private String PresentPosting;
	private String unitsOther;
	private String dutyYear;
	private String event;
	private String policeStation;
	private String yearsOfCompletion;
	private String specialOption;
	
	private String rank;
	private String unit;
	private String department;
	private String dateOfenlistment;
	private String dept_dateOfenlistment;
	//newly added feilds for department
	private String applyNotify;
	private String certificateNumber;
	private String designationOfIssuingAuthority;
	private String designationOfIssuingAuthorityOTH;
	private String nocDate;
	private String serviceAsOn;
	private String awardPunishment;
	private String underQuota;
	private String miniExeStaff;
	
	Map<Integer, String> MedalList=new LinkedHashMap<Integer, String>();

	private String highestEduDate;
	private String acdmPk;
}
