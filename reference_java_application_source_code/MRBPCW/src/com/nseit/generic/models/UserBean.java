package com.nseit.generic.models;

import java.util.List;

public class UserBean {
	private List<CommonBean> selectList;
	private String category;
	private String roleType;
	private String role;
	private String userName;
	private String userPk;
	private String userId;
	private String email;
	private String mobile;
	private String password;
	private String confirmPassword;
	private String status;
	private String loggedIn;
	private List<UserBean> searchDetailsList;
	private String showGrid;
	private String totalRecord;
	private String availableRecordFlag;
	private String sucMsg;
	private String errMsg;
	private String showModuleDetails;
	private String  setFlag;
	
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setSelectList(List<CommonBean> selectList) {
		this.selectList = selectList;
	}

	public List<CommonBean> getSelectList() {
		return selectList;
	}

	public void setLoggedIn(String loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getLoggedIn() {
		return loggedIn;
	}

	public void setUserPk(String userPk) {
		this.userPk = userPk;
	}

	public String getUserPk() {
		return userPk;
	}

	public void setSearchDetailsList(List<UserBean> searchDetailsList) {
		this.searchDetailsList = searchDetailsList;
	}

	public List<UserBean> getSearchDetailsList() {
		return searchDetailsList;
	}

	public void setShowGrid(String showGrid) {
		this.showGrid = showGrid;
	}

	public String getShowGrid() {
		return showGrid;
	}

	public void setTotalRecord(String totalRecord) {
		this.totalRecord = totalRecord;
	}

	public String getTotalRecord() {
		return totalRecord;
	}

	public void setAvailableRecordFlag(String availableRecordFlag) {
		this.availableRecordFlag = availableRecordFlag;
	}

	public String getAvailableRecordFlag() {
		return availableRecordFlag;
	}

	public void setSucMsg(String sucMsg) {
		this.sucMsg = sucMsg;
	}

	public String getSucMsg() {
		return sucMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setShowModuleDetails(String showModuleDetails) {
		this.showModuleDetails = showModuleDetails;
	}

	public String getShowModuleDetails() {
		return showModuleDetails;
	}

	public void setSetFlag(String setFlag) {
		this.setFlag = setFlag;
	}

	public String getSetFlag() {
		return setFlag;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
}
