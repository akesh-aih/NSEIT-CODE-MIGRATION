package com.nseit.generic.models;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class LoginBean {
	
	private String dischargeEndDate;
	private int dischargeEndYear;
	private String appFormStrtDate;
	private String appFormEndDate;
	private String displayAppFlag;
	private String paymentStartDate;
	private String paymentEndDate;
	private String displayPaymentFlag;
	private String newPassword;
	private String confirmPassword;
	private String captcha;
	private String ageAsOn;
	private String minDate;
	private String maxDate;
	private String username;
	private String changePasswordFlag;
	private String password;
	private String encryptedusername;
	private String encryptedpassword;
	private String displayRegFlag;
	private String loginFooter;
	private String dateOfBirth;
	private boolean verifyemailOTPFlag;
	private boolean registrationActive;
	private String regStrtDate;
	private String regEndDate;
	private int invalidCount;
	private long userFk;
	private String activeStatus;
	private Map<Integer, String> genderList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> discliplineList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> nationalityList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> nativityCertDistList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> initialstitle;
	private Map<Integer, String> yesNo = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> categoryList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> personDisabilityList = new LinkedHashMap<Integer, String>();//added by vijay
	private Map<Integer, String> stateList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> districtList = new LinkedHashMap<Integer, String>();
	public boolean isVerifyemailOTPFlag() {
		return verifyemailOTPFlag;
	}

	public void setVerifyemailOTPFlag(boolean verifyemailOTPFlag) {
		this.verifyemailOTPFlag = verifyemailOTPFlag;
	}

	public String getChangePasswordFlag() {
		return changePasswordFlag;
	}

	public void setChangePasswordFlag(String changePasswordFlag) {
		this.changePasswordFlag = changePasswordFlag;
	}

	public String getMinDate() {
		return minDate;
	}

	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}

	public String getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}

	public String getAgeAsOn() {
		return ageAsOn;
	}

	public void setAgeAsOn(String ageAsOn) {
		this.ageAsOn = ageAsOn;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setGenderList(Map<Integer, String> genderList) {
		this.genderList = genderList;
	}

	public Map<Integer, String> getGenderList() {
		return genderList;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getCaptcha() {
		return captcha;
	}

	public String getEncryptedusername() {
		return encryptedusername;
	}

	public void setEncryptedusername(String encryptedusername) {
		this.encryptedusername = encryptedusername;
	}

	public String getEncryptedpassword() {
		return encryptedpassword;
	}

	public void setEncryptedpassword(String encryptedpassword) {
		this.encryptedpassword = encryptedpassword;
	}

	public String getDisplayRegFlag() {
		return displayRegFlag;
	}

	public void setDisplayRegFlag(String displayRegFlag) {
		this.displayRegFlag = displayRegFlag;
	}

	public String getLoginFooter() {
		return loginFooter;
	}

	public String setLoginFooter(String loginFooter) {
		return this.loginFooter = loginFooter;
	}

	public Map<Integer, String> getDiscliplineList() {
		return discliplineList;
	}

	public void setDiscliplineList(Map<Integer, String> discliplineList) {
		this.discliplineList = discliplineList;
	}

	public Map<Integer, String> getNationalityList() {
		return nationalityList;
	}

	public void setNationalityList(Map<Integer, String> nationalityList) {
		this.nationalityList = nationalityList;
	}

	public Map<Integer, String> getInitialstitle() {
		return initialstitle;
	}

	public void setInitialstitle(Map<Integer, String> initialstitle) {
		this.initialstitle = initialstitle;
	}

	public boolean isRegistrationActive() {
		return registrationActive;
	}

	public void setRegistrationActive(boolean registrationActive) {
		this.registrationActive = registrationActive;
	}

	public String getRegStrtDate() {
		return regStrtDate;
	}

	public void setRegStrtDate(String regStrtDate) {
		this.regStrtDate = regStrtDate;
	}

	public String getRegEndDate() {
		return regEndDate;
	}

	public void setRegEndDate(String regEndDate) {
		this.regEndDate = regEndDate;
	}

	public int getInvalidCount() {
		return invalidCount;
	}

	public void setInvalidCount(int invalidCount) {
		this.invalidCount = invalidCount;
	}

	public long getUserFk() {
		return userFk;
	}

	public void setUserFk(long userFk) {
		this.userFk = userFk;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Map<Integer, String> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(Map<Integer, String> categoryList) {
		this.categoryList = categoryList;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Map<Integer, String> getNativityCertDistList() {
		return nativityCertDistList;
	}

	public void setNativityCertDistList(Map<Integer, String> nativityCertDistList) {
		this.nativityCertDistList = nativityCertDistList;
	}

	public String getDisplayAppFlag() {
		return displayAppFlag;
	}

	public void setDisplayAppFlag(String displayAppFlag) {
		this.displayAppFlag = displayAppFlag;
	}

	public String getPaymentStartDate() {
		return paymentStartDate;
	}

	public void setPaymentStartDate(String paymentStartDate) {
		this.paymentStartDate = paymentStartDate;
	}

	public String getPaymentEndDate() {
		return paymentEndDate;
	}

	public void setPaymentEndDate(String paymentEndDate) {
		this.paymentEndDate = paymentEndDate;
	}

	public String getDisplayPaymentFlag() {
		return displayPaymentFlag;
	}

	public void setDisplayPaymentFlag(String displayPaymentFlag) {
		this.displayPaymentFlag = displayPaymentFlag;
	}

	public String getAppFormStrtDate() {
		return appFormStrtDate;
	}

	public void setAppFormStrtDate(String appFormStrtDate) {
		this.appFormStrtDate = appFormStrtDate;
	}

	public String getAppFormEndDate() {
		return appFormEndDate;
	}

	public void setAppFormEndDate(String appFormEndDate) {
		this.appFormEndDate = appFormEndDate;
	}

	public Map<Integer, String> getYesNo() {
		return yesNo;
	}

	public void setYesNo(Map<Integer, String> yesNo) {
		this.yesNo = yesNo;
	}

	public Map<Integer, String> getPersonDisabilityList() {
		return personDisabilityList;
	}

	public void setPersonDisabilityList(Map<Integer, String> personDisabilityList) {
		this.personDisabilityList = personDisabilityList;
	}

	public String getDischargeEndDate() {
		return dischargeEndDate;
	}

	public void setDischargeEndDate(String dischargeEndDate) {
		this.dischargeEndDate = dischargeEndDate;
	}

	public int getDischargeEndYear() {
		return dischargeEndYear;
	}

	public void setDischargeEndYear(int dischargeEndYear) {
		this.dischargeEndYear = dischargeEndYear;
	}

	public Map<Integer, String> getStateList() {
		return stateList;
	}

	public void setStateList(Map<Integer, String> stateList) {
		this.stateList = stateList;
	}

	public Map<Integer, String> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(Map<Integer, String> districtList) {
		this.districtList = districtList;
	}

}
