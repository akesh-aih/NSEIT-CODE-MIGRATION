package com.nseit.generic.models;

import java.util.ArrayList;
import java.util.List;

public class TestMgmtBean {
	//add Required fields here  
	
	
	private String total;
	private String available;
	private String firstPreference;
	private String secondPreference;
	private String thirdPreference;
	private String testCenterPK;
	private String discipiline;
	private String testDate;
	private String testCentre;
	private String testSlot;
	private String totalEnrollment;
	private String totalAppered;
	private String discipilinePK;
	private String startDate;
	private String endDate;
	private List<CommonBean> disciplineList    = new ArrayList<CommonBean>();
	private List<CommonBean> testCenterList        = new ArrayList<CommonBean>();
	private List<TestMgmtBean> testMgmtBeanList = new ArrayList<TestMgmtBean>();
	private String testFromDate;
	private String testToDate;
	private List<String> checkedValue;
	private String searchClickedFlag;
	private String noOFRegistation;
	//This Block for Black List
	
	private String ipAddress;
	private String addressPK;
	private String noRegistation;	
	private String status;
	private String blackListed;
	private String macAddress;
	 
	private String viewAttendanceDisplay;
	

	//This Block Contains Setter And Getter method 
	
	
	public String getTestCentre() {
		return testCentre;
	}
	public void setTestCentre(String testCentre) {
		this.testCentre = testCentre;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}
	public String getFirstPreference() {
		return firstPreference;
	}
	public void setFirstPreference(String firstPreference) {
		this.firstPreference = firstPreference;
	}
	public String getSecondPreference() {
		return secondPreference;
	}
	public void setSecondPreference(String secondPreference) {
		this.secondPreference = secondPreference;
	}
	public String getThirdPreference() {
		return thirdPreference;
	}
	public void setThirdPreference(String thirdPreference) {
		this.thirdPreference = thirdPreference;
	}
	
	
	
	public String getTestCenterPK() {
		return testCenterPK;
	}
	public void setTestCenterPK(String testCenterPK) {
		this.testCenterPK = testCenterPK;
	}
		
	public String getDiscipilinePK() {
		return discipilinePK;
	}
	public void setDiscipilinePK(String discipilinePK) {
		this.discipilinePK = discipilinePK;
	}
	
	
	
	public String getDiscipiline() {
		return discipiline;
	}
	public void setDiscipiline(String discipiline) {
		this.discipiline = discipiline;
	}
	public String getTestDate() {
		return testDate;
	}
	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}
	public String getTestSlot() {
		return testSlot;
	}
	public void setTestSlot(String testSlot) {
		this.testSlot = testSlot;
	}
	public String getTotalEnrollment() {
		return totalEnrollment;
	}
	public void setTotalEnrollment(String totalEnrollment) {
		this.totalEnrollment = totalEnrollment;
	}
	public String getTotalAppered() {
		return totalAppered;
	}
	public void setTotalAppered(String totalAppered) {
		this.totalAppered = totalAppered;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<CommonBean> getDisciplineList() {
		return disciplineList;
	}
	public void setDisciplineList(List<CommonBean> disciplineList) {
		this.disciplineList = disciplineList;
	}
	public List<CommonBean> getTestCenterList() {
		return testCenterList;
	}
	public void setTestCenterList(List<CommonBean> testCenterList) {
		this.testCenterList = testCenterList;
	}
		
	
	public List<TestMgmtBean> getTestMgmtBeanList() {
		return testMgmtBeanList;
	}
	public void setTestMgmtBeanList(List<TestMgmtBean> testMgmtBeanList) {
		this.testMgmtBeanList = testMgmtBeanList;
	}
	public String getTestFromDate() {
		return testFromDate;
	}
	public void setTestFromDate(String testFromDate) {
		this.testFromDate = testFromDate;
	}
	public String getTestToDate() {
		return testToDate;
	}
	public void setTestToDate(String testToDate) {
		this.testToDate = testToDate;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getNoOFRegistation() {
		return noOFRegistation;
	}
	public void setNoOFRegistation(String noOFRegistation) {
		this.noOFRegistation = noOFRegistation;
	}
		 
	

	
	public String getAddressPK() {
		return addressPK;
	}
	
	 
	public void setAddressPK(String addressPK) {
		this.addressPK = addressPK;
	}
	
	public String getNoRegistation() {
		return noRegistation;
	}
	public void setNoRegistation(String noRegistation) {
		this.noRegistation = noRegistation;
	}
	public String getBlackListed() {
		return blackListed;
	}
	public void setBlackListed(String blackListed) {
		this.blackListed = blackListed;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public List<String> getCheckedValue() {
		return checkedValue;
	}
	public void setCheckedValue(List<String> checkedValue) {
		this.checkedValue = checkedValue;
	}
	public String getSearchClickedFlag() {
		return searchClickedFlag;
	}
	public void setSearchClickedFlag(String searchClickedFlag) {
		this.searchClickedFlag = searchClickedFlag;
	}
	public String getViewAttendanceDisplay() {
		return viewAttendanceDisplay;
	}
	public void setViewAttendanceDisplay(String viewAttendanceDisplay) {
		this.viewAttendanceDisplay = viewAttendanceDisplay;
	}
	
}  


