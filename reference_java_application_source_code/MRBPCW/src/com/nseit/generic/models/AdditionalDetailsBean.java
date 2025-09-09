package com.nseit.generic.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdditionalDetailsBean {

	private Map<Integer, String> advertList;
	private Map<Integer, String> yesNo;
	private Map<Integer, String> refStateList;
	private List<String> errorField;
	private String academicAward;
	private String advertisement;
	private String appliedInPast;
	private String yearsOfApply;
	private List<String> yearsOfApplyList = new ArrayList<String>();
	private String reasonForNotJoining;
	private String stmtOfPurpose;
	private String otherInfo;
	private String ref1Name;
	private String ref1Desig;
	private String ref1IsAcademician;
	private String ref1Add1;
	private String ref1Add2;
	private String ref1State;
	private String ref1District;
	private String ref1City;
	private String ref1Pincode;
	private String ref1Contact;
	private String ref2Name;
	private String ref2Desig;
	private String ref2IsAcademician;
	private String ref2Add1;
	private String ref2Add2;
	private String ref2State;
	private String ref2District;
	private String ref2City;
	private String ref2Pincode;
	private String ref2Contact;
	private String ref1EmailAddress;
	private String ref2EmailAddress;

}
