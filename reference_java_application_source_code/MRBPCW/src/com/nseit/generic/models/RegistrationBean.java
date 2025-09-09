package com.nseit.generic.models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class RegistrationBean {
	private String dischargeEndDate;
	private int dischargeEndYear;
	private boolean diffAbledChkBox;
	private String signupFormShow;
	private String mobileprefix;
	private String nativityCertDist;
	private String categoryValDesc;
	private Map<Integer, String> categoryList;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	private String mobileNo;
	private String emailAddress;
	private String userId;
	private String ipAddress;
	private String userName;
	private String userType;
	private String ipin;
	private String newPwd;
	private String disciplineType;
	private String postApplied;
	private String gender;
	private String exServiceMen;
	private String dischargeDate;
	private String ppoNumber;
	private String commCertYesNo;
	private String community;
	private String categoryVal1;
	private String subCaste;
	private String issueAuthCommCert;
	private String commCertNo;
	private String commCertPlace;
	private String commIssueDate;
	private String disableYesNo;
	private String disableType;
	private String disablityPercent;
	private String minDate;
	private String dateOfBirth;
	private String ageInDays;
	private String ageInMonths;
	private String ageInYears;
	private String ageAsOn;
	private Map<Integer, String> discliplineList;
	private Map<Integer, String> nationalityList;
	private Map<Integer, String> nativityCertDistList;
	private Map<Integer, String> initialstitle;
	private Map<Integer, String> genderList;
	private Map<Integer, String> personDisabilityList = new LinkedHashMap<Integer, String>();//added by vijay
	private Map<Integer, String> yesNo = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> stateList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> districtList = new LinkedHashMap<Integer, String>();
	private String candidateFirstName;
	private String candidateMiddleName;
	private String candidateLastName;
	private String otp;
	private String otpHdn;
	private String captchaStringFromSession;
	private String sa;
	private String iv;
	private Integer otpLockCount;
	private String otpSate;
	private String otpGeneratedTime;
	private String otpUpdatedTime;
	private String rePwd;
	private String emailotp;
	private String mobotp;
	private String verifyemailOTPFlag;
	private String verifyMobileOTPFlag;
	private String initials;
	private String captcha;
	private String errorMsg;
	private String hidCaptchaID;
	private String courses;
	private String nationVal;
	private String mobileNoHid;
	private String regStrtDate;
	private String regEndDate;
	private String emailAddressHid;
	private String answerHid;
	private String newPasswordHid;
	private String confirmPasswordHid;
	private String captchaHid;
	private String candidateName;
	private String confirmEmailAddress;
	private String candidateNameHid;
	private String confirmEmailAddressHid;
	private String fpOtp;
	private List<String> errorField;
	private List<String> errorField1;
	private String emailId;
	
	private String widowYesNo;
	private String desWidowCertNo;
	private String widowIssueDate;
	private String widowIssueAuth;
	private String widowDistrict;
	private String widowOtherDistrict;
	private String widowSubDivision;
	private boolean widowCheckbox;
	}
