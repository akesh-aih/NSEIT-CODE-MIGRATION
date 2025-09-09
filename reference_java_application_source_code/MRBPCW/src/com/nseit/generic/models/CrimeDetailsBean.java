package com.nseit.generic.models;

import java.util.Map;

public class CrimeDetailsBean {
	private Long user_fk; 
	private String crimeNumber;
	private String stateVal;
	private String districtVal;
	private String districtValother;
	private String policeStation;
	private String policeStationOther;
	private String dateOfCrime;
	private String caseStationVal;
	private String policeStationDisplay;
	private String districtyDisplay;
	
	public String getPoliceStationDisplay() {
		return policeStationDisplay;
	}
	public void setPoliceStationDisplay(String policeStationDisplay) {
		this.policeStationDisplay = policeStationDisplay;
	}
	public String getDistrictyDisplay() {
		return districtyDisplay;
	}
	public void setDistrictyDisplay(String districtyDisplay) {
		this.districtyDisplay = districtyDisplay;
	}


	private Map<Integer, String> policeStationList;
	public String getDistrictVal() {
		return districtVal;
	}
	public void setDistrictVal(String districtVal) {
		this.districtVal = districtVal;
	}

	
	
	public String getPoliceStation() {
		return policeStation;
	}
	public void setPoliceStation(String policeStation) {
		this.policeStation = policeStation;
	}
	
	public Map<Integer, String> getPoliceStationList() {
		return policeStationList;
	}
	public void setPoliceStationList(Map<Integer, String> policeStationList) {
		this.policeStationList = policeStationList;
	}
	
	
	public String getCaseStationVal() {
		return caseStationVal;
	}
	public void setCaseStationVal(String caseStationVal) {
		this.caseStationVal = caseStationVal;
	}
	public Map<String, String> getStateList() {
		return stateList;
	}
	public void setStateList(Map<String, String> stateList) {
		this.stateList = stateList;
	}
	public Map<Integer, String> getDistrictList() {
		return districtList;
	}
	public void setDistrictList(Map<Integer, String> districtList) {
		this.districtList = districtList;
	}
	public Map<String, String> getCaseStationList() {
		return caseStationList;
	}
	public void setCaseStationList(Map<String, String> caseStationList) {
		this.caseStationList = caseStationList;
	}
	private Map<String, String> stateList;
	private Map<Integer, String> districtList;
	private Map<String, String> caseStationList;

	public String getCrimeNumber() {
		return crimeNumber;
	}
	public void setCrimeNumber(String crimeNumber) {
		this.crimeNumber = crimeNumber;
	}
	public String getStateVal() {
		return stateVal;
	}
	public void setStateVal(String stateVal) {
		this.stateVal = stateVal;
	}
	public String getDistrictValother() {
		return districtValother;
	}
	public void setDistrictValother(String districtValother) {
		this.districtValother = districtValother;
	}
	public String getPoliceStationOther() {
		return policeStationOther;
	}
	public void setPoliceStationOther(String policeStationOther) {
		this.policeStationOther = policeStationOther;
	}
	public String getDateOfCrime() {
		return dateOfCrime;
	}
	public void setDateOfCrime(String dateOfCrime) {
		this.dateOfCrime = dateOfCrime;
	}
	public Long getUser_fk() {
		return user_fk;
	}
	public void setUser_fk(Long userFk) {
		user_fk = userFk;
	}

	
		
}
