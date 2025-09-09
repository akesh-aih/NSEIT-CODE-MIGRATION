package com.nseit.generic.models;

import java.util.List;

import com.nseit.generic.models.CommonBean;

public class ModuleBean {
	private String syllabusValue;
	List<CommonBean> syllabusList;
	List<TestGroup> testGroupList;
	

	private List<CommonBean> selectList;
	private String status;
	private List<ModuleBean> searchDetailsList;
	private List<ModuleBean> searchCategoryList;
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
	private String moduleCode;
	private String deleteCountMsg;
	private String fromDate;
	private String toDate;
	private String paymentStartDate;
	private String paymentEndDate;
//	private List<ExamMasterBean> examMasterList;
	private String examCodeValue;
	private String examName;
	private String OTM_TEST_PK;
	private String OTM_TEST_NAME;
	private String OTM_DESCRIPTION;
	private String OTM_STATUS;
	private String OTM_FROM_DATE;
	private String OTM_TO_DATE;
	private String OTM_PAYMENT_START_DATE;
	private String OTM_PAYMENT_END_DATE;
	private String fees;
	private String feesInWords;
	private String advertisementnumber;
	
	private String OCTM_CATEGORY_CODE;
	private String OCTM_CATEGORY_PK;
	private String dateOfNotification;
	private Long testGroup;
	private List<String> errorField;
	
	
	
	public Long getTestGroup() {
		return testGroup;
	}

	public void setTestGroup(Long testGroup) {
		this.testGroup = testGroup;
	}

	public List<TestGroup> getTestGroupList() {
		return testGroupList;
	}

	public void setTestGroupList(List<TestGroup> testGroupList) {
		this.testGroupList = testGroupList;
	}

	public String getSyllabusValue() {
		return syllabusValue;
	}

	public void setSyllabusValue(String syllabusValue) {
		this.syllabusValue = syllabusValue;
	}

	public List<CommonBean> getSyllabusList() {
		return syllabusList;
	}

	public void setSyllabusList(List<CommonBean> list) {
		this.syllabusList = list;
	}

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

	/*public List<ModuleSyllabusDetails> getSearchDetailsList() {
		return searchDetailsList;
	}

	public void setSearchDetailsList(
			List<ModuleSyllabusDetails> searchDetailsList) {
		this.searchDetailsList = searchDetailsList;
	}*/

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

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
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

	public void setSearchDetailsList(List<ModuleBean> searchDetailsList) {
		this.searchDetailsList = searchDetailsList;
	}

	public List<ModuleBean> getSearchDetailsList() {
		return searchDetailsList;
	}

	public void setOTM_TEST_NAME(String oTM_TEST_NAME) {
		OTM_TEST_NAME = oTM_TEST_NAME;
	}

	public String getOTM_TEST_NAME() {
		return OTM_TEST_NAME;
	}

	public void setOTM_DESCRIPTION(String oTM_DESCRIPTION) {
		OTM_DESCRIPTION = oTM_DESCRIPTION;
	}

	public String getOTM_DESCRIPTION() {
		return OTM_DESCRIPTION;
	}

	public void setOTM_STATUS(String oTM_STATUS) {
		OTM_STATUS = oTM_STATUS;
	}

	public String getOTM_STATUS() {
		return OTM_STATUS;
	}

	public void setOTM_FROM_DATE(String oTM_FROM_DATE) {
		OTM_FROM_DATE = oTM_FROM_DATE;
	}

	public String getOTM_FROM_DATE() {
		return OTM_FROM_DATE;
	}

	public void setOTM_TO_DATE(String oTM_TO_DATE) {
		OTM_TO_DATE = oTM_TO_DATE;
	}

	public String getOTM_TO_DATE() {
		return OTM_TO_DATE;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setSearchCategoryList(List<ModuleBean> searchCategoryList) {
		this.searchCategoryList = searchCategoryList;
	}

	public List<ModuleBean> getSearchCategoryList() {
		return searchCategoryList;
	}
	

	public void setOCTM_CATEGORY_CODE(String oCTM_CATEGORY_CODE) {
		OCTM_CATEGORY_CODE = oCTM_CATEGORY_CODE;
	}

	public String getOCTM_CATEGORY_CODE() {
		return OCTM_CATEGORY_CODE;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public String getFees() {
		return fees;
	}

	public void setFeesInWords(String feesInWords) {
		this.feesInWords = feesInWords;
	}

	public String getFeesInWords() {
		return feesInWords;
	}

	public void setOCTM_CATEGORY_PK(String oCTM_CATEGORY_PK) {
		OCTM_CATEGORY_PK = oCTM_CATEGORY_PK;
	}

	public String getOCTM_CATEGORY_PK() {
		return OCTM_CATEGORY_PK;
	}

	public void setOTM_TEST_PK(String oTM_TEST_PK) {
		OTM_TEST_PK = oTM_TEST_PK;
	}

	public String getOTM_TEST_PK() {
		return OTM_TEST_PK;
	}

	public void setPaymentStartDate(String paymentStartDate) {
		this.paymentStartDate = paymentStartDate;
	}

	public String getPaymentStartDate() {
		return paymentStartDate;
	}

	public void setPaymentEndDate(String paymentEndDate) {
		this.paymentEndDate = paymentEndDate;
	}

	public String getPaymentEndDate() {
		return paymentEndDate;
	}

	public void setOTM_PAYMENT_START_DATE(String oTM_PAYMENT_START_DATE) {
		OTM_PAYMENT_START_DATE = oTM_PAYMENT_START_DATE;
	}

	public String getOTM_PAYMENT_START_DATE() {
		return OTM_PAYMENT_START_DATE;
	}

	public void setOTM_PAYMENT_END_DATE(String oTM_PAYMENT_END_DATE) {
		OTM_PAYMENT_END_DATE = oTM_PAYMENT_END_DATE;
	}

	public String getOTM_PAYMENT_END_DATE() {
		return OTM_PAYMENT_END_DATE;
	}

	public String getDateOfNotification() {
		return dateOfNotification;
	}

	public void setDateOfNotification(String dateOfNotification) {
		this.dateOfNotification = dateOfNotification;
	}
	public String getAdvertisementnumber() {
		return advertisementnumber;
	}

	public void setAdvertisementnumber(String advertisementnumber) {
		this.advertisementnumber = advertisementnumber;
	}

	public void setErrorField(List<String> errorField) {
		this.errorField = errorField;
	}

	public List<String> getErrorField() {
		return errorField;
	}
	
	
}
