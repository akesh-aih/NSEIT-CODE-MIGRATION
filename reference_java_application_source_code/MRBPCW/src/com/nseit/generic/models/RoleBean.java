package com.nseit.generic.models;

import java.util.List;
import java.util.Map;

import com.nseit.generic.models.CommonBean;
import com.nseit.generic.util.MenuMasterChildBean;
import com.nseit.generic.util.MenuMasterParentBean;

public class RoleBean {
	private List<CommonBean> selectList;
	private String status;
	private List<RoleBean> searchDetailsList;
	private String showGrid;
	private String totalRecord;
	private String availableRecordFlag;
	private String showModuleDetails;
	private String sucMsg;
	private String errMsg;
	private String moduleId;
	private String moduleDescription;
	private String username;
	private String moduleName;
	private String  OEM_EXEMPTION_MASTER_PK;
	private String  editFlag;
	private String  setFlag;
	private String  popUpFlag;
	private String roleCode;
	private String deleteCountMsg;

	private String examCodeValue;
	private String examName;
	
	private String ORM_ROLE_PK;
	private String ORM_ROLE_CODE;
	private String ORM_ROLE_DESC;
	private String ORM_STATUS;
	private List<MenuMasterParentBean> parentMenuDetailsList;
	private List<MenuMasterChildBean> childMenuDetailsList;
	private Map<Integer,String> roleMap;
	private String roleType;
	private String role;

	public List<CommonBean> getSelectList() {
		return selectList;
	}

	public void setSelectList(List<CommonBean> list) {
		this.selectList = list;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<RoleBean> getSearchDetailsList() {
		return searchDetailsList;
	}

	public void setSearchDetailsList(
			List<RoleBean> searchDetailsList) {
		this.searchDetailsList = searchDetailsList;
	}

	public String getShowGrid() {
		return showGrid;
	}

	public void setShowGrid(String showGrid) {
		this.showGrid = showGrid;
	}

	public String getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(String totalRecord) {
		this.totalRecord = totalRecord;
	}

	public String getAvailableRecordFlag() {
		return availableRecordFlag;
	}

	public void setAvailableRecordFlag(String availableRecordFlag) {
		this.availableRecordFlag = availableRecordFlag;
	}

	public String getShowModuleDetails() {
		return showModuleDetails;
	}

	public void setShowModuleDetails(String showModuleDetails) {
		this.showModuleDetails = showModuleDetails;
	}

	public String getSucMsg() {
		return sucMsg;
	}

	public void setSucMsg(String sucMsg) {
		this.sucMsg = sucMsg;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getmoduleDescription(){
		return moduleDescription;
	}

	public void setmoduleDescription(String moduleDescription) {
		this.moduleDescription = moduleDescription;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleDescription() {
		return moduleDescription;
	}

	public void setModuleDescription(String moduleDescription) {
		this.moduleDescription = moduleDescription;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getOEM_EXEMPTION_MASTER_PK() {
		return OEM_EXEMPTION_MASTER_PK;
	}

	public void setOEM_EXEMPTION_MASTER_PK(String oEMEXEMPTIONMASTERPK) {
		OEM_EXEMPTION_MASTER_PK = oEMEXEMPTIONMASTERPK;
	}

	public String getEditFlag() {
		return editFlag;
	}

	public void setEditFlag(String editFlag) {
		this.editFlag = editFlag;
	}

	public String getSetFlag() {
		return setFlag;
	}

	public void setSetFlag(String setFlag) {
		this.setFlag = setFlag;
	}

	public String getPopUpFlag() {
		return popUpFlag;
	}

	public void setPopUpFlag(String popUpFlag) {
		this.popUpFlag = popUpFlag;
	}

	public String getDeleteCountMsg() {
		return deleteCountMsg;
	}

	public void setDeleteCountMsg(String deleteCountMsg) {
		this.deleteCountMsg = deleteCountMsg;
	}

	/*public List<ExamMasterBean> getExamMasterList() {
		return examMasterList;
	}

	public void setExamMasterList(List<ExamMasterBean> examMasterList) {
		this.examMasterList = examMasterList;
	}*/

	public String getExamCodeValue() {
		return examCodeValue;
	}

	public void setExamCodeValue(String examCodeValue) {
		this.examCodeValue = examCodeValue;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getORM_ROLE_PK() {
		return ORM_ROLE_PK;
	}

	public void setORM_ROLE_PK(String oRMROLEPK) {
		ORM_ROLE_PK = oRMROLEPK;
	}

	public String getORM_ROLE_CODE() {
		return ORM_ROLE_CODE;
	}

	public void setORM_ROLE_CODE(String oRMROLECODE) {
		ORM_ROLE_CODE = oRMROLECODE;
	}

	public String getORM_ROLE_DESC() {
		return ORM_ROLE_DESC;
	}

	public void setORM_ROLE_DESC(String oRMROLEDESC) {
		ORM_ROLE_DESC = oRMROLEDESC;
	}

	public String getORM_STATUS() {
		return ORM_STATUS;
	}

	public void setORM_STATUS(String oRMSTATUS) {
		ORM_STATUS = oRMSTATUS;
	}

	public void setParentMenuDetailsList(List<MenuMasterParentBean> parentMenuDetailsList) {
		this.parentMenuDetailsList = parentMenuDetailsList;
	}

	public List<MenuMasterParentBean> getParentMenuDetailsList() {
		return parentMenuDetailsList;
	}

	public void setChildMenuDetailsList(List<MenuMasterChildBean> childMenuDetailsList) {
		this.childMenuDetailsList = childMenuDetailsList;
	}

	public List<MenuMasterChildBean> getChildMenuDetailsList() {
		return childMenuDetailsList;
	}

	public void setRoleMap(Map<Integer,String> roleMap) {
		this.roleMap = roleMap;
	}

	public Map<Integer,String> getRoleMap() {
		return roleMap;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
}
