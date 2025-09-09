package com.nseit.generic.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReportBean {

	private String startDate;
	private String endDate;

	private String firstName;
	private String lastName;
	private String fileType;
	private String contentDisposition;
	private String format;
	private List<CommonBean> testCenterList = new ArrayList<CommonBean>();
	private String candiadatesCount;
	private String date;
	private Integer testCenterId;
	private List<String> formatList = new ArrayList<String>();

	private String referncePk;
	private String refernceValue;

	private List<CommonBean> reportTypeList;
	private List<CommonBean> reportTypeListForEnrollment;
	private String reportId;
	private String selectedOption;

	private Map<Integer, String> disciplineListMap = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> statusListMap = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> testCenterListMap = new LinkedHashMap<Integer, String>();
	private String testCenterVal;
	private String disciplineVal;
	private String paymentReconcStatusVal;
	private String testDate;
	private String testSlot;

	private Map<Integer, String> attemptsListMap = new LinkedHashMap<Integer, String>();
	private String attemptVal;
	private String userId;
	private String disciplineId;
	private String fromDate;
	private String toDate;
	private Map<Integer, String> scheduleStatusList = new LinkedHashMap<Integer, String>();
	private String scheduleStatusId;
	private String showDataGridFlag;
	private List<ScheduleReportBean> scheduleReportDetailList = new ArrayList<ScheduleReportBean>();
	private Integer scheduleReportDetailListSize = new Integer(0);
	private String availableRecordFlag;
	
	private Map<Integer,String> paymentModeMap = new LinkedHashMap<Integer, String>();
	private Integer paymentModeId;
	private Map<String, String> paymentStatusList = new LinkedHashMap<String, String>();
	private String paymentStatusId;
	private List<PaymentReportBean> paymentReportDetailList = new ArrayList<PaymentReportBean>();
	private Integer paymentReportDetailListSize;
	private List<PaymentReportBean> paymentCollectionReportDetailList = new ArrayList<PaymentReportBean>();
	private Integer paymentCollectionReportDetailListSize;
	
	private String menuKey;
	
	private List<PaymentReconciliationReportBean> paymentReconciliationReportData = new ArrayList<PaymentReconciliationReportBean>();
	private List<PaymentReconciliationReportBean> paymentReconciliationReportDataWithDetail = new ArrayList<PaymentReconciliationReportBean>();
	
	private PaymentReconciliationReportBean totAmtPymtReconcMainResult;
	
	private String displayMainResult;
	
	private String displayCanidateWiseResult;
	
	private PaymentReconciliationReportBean paymentReconcilliationForUserID;
	
	private String attemptFlag;
	
	private String paymentCollReportMsg;
	private String paymentCollReportFlag;

	private List<String> testDatesList;
	
	public ReportBean() {
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setFormatList(List<String> formatList) {
		this.formatList = formatList;
	}

	public List<String> getFormatList() {
		return formatList;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getCandiadatesCount() {
		return candiadatesCount;
	}

	public void setCandiadatesCount(String candiadatesCount) {
		this.candiadatesCount = candiadatesCount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getReferncePk() {
		return referncePk;
	}

	public void setReferncePk(String referncePk) {
		this.referncePk = referncePk;
	}

	public String getRefernceValue() {
		return refernceValue;
	}

	public void setRefernceValue(String refernceValue) {
		this.refernceValue = refernceValue;
	}

	public List<CommonBean> getReportTypeList() {
		return reportTypeList;
	}

	public void setReportTypeList(List<CommonBean> reportTypeList) {
		this.reportTypeList = reportTypeList;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public List<CommonBean> getReportTypeListForEnrollment() {
		return reportTypeListForEnrollment;
	}

	public void setReportTypeListForEnrollment(
			List<CommonBean> reportTypeListForEnrollment) {
		this.reportTypeListForEnrollment = reportTypeListForEnrollment;
	}

	public void setTestCenterList(List<CommonBean> testCenterList) {
		this.testCenterList = testCenterList;
	}

	public List<CommonBean> getTestCenterList() {
		return testCenterList;
	}

	public void setTestCenterId(Integer testCenterId) {
		this.testCenterId = testCenterId;
	}

	public Integer getTestCenterId() {
		return testCenterId;
	}

	public String getSelectedOption() {
		return selectedOption;
	}

	public void setSelectedOption(String selectedOption) {
		this.selectedOption = selectedOption;
	}

	public Map<Integer, String> getDisciplineListMap() {
		return disciplineListMap;
	}

	public void setDisciplineListMap(Map<Integer, String> disciplineListMap) {
		this.disciplineListMap = disciplineListMap;
	}

	public Map<Integer, String> getTestCenterListMap() {
		return testCenterListMap;
	}

	public void setTestCenterListMap(Map<Integer, String> testCenterListMap) {
		this.testCenterListMap = testCenterListMap;
	}

	public String getTestCenterVal() {
		return testCenterVal;
	}

	public void setTestCenterVal(String testCenterVal) {
		this.testCenterVal = testCenterVal;
	}

	public String getDisciplineVal() {
		return disciplineVal;
	}

	public void setDisciplineVal(String disciplineVal) {
		this.disciplineVal = disciplineVal;
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

	public Map<Integer, String> getAttemptsListMap() {
		return attemptsListMap;
	}

	public void setAttemptsListMap(Map<Integer, String> attemptsListMap) {
		this.attemptsListMap = attemptsListMap;
	}

	public String getAttemptVal() {
		return attemptVal;
	}

	public void setAttemptVal(String attemptVal) {
		this.attemptVal = attemptVal;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDisciplineId() {
		return disciplineId;
	}

	public void setDisciplineId(String disciplineId) {
		this.disciplineId = disciplineId;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public Map<Integer, String> getScheduleStatusList() {
		return scheduleStatusList;
	}

	public void setScheduleStatusList(Map<Integer, String> scheduleStatusList) {
		this.scheduleStatusList = scheduleStatusList;
	}

	public String getScheduleStatusId() {
		return scheduleStatusId;
	}

	public void setScheduleStatusId(String scheduleStatusId) {
		this.scheduleStatusId = scheduleStatusId;
	}

	public String getShowDataGridFlag() {
		return showDataGridFlag;
	}

	public void setShowDataGridFlag(String showDataGridFlag) {
		this.showDataGridFlag = showDataGridFlag;
	}

	public List<ScheduleReportBean> getScheduleReportDetailList() {
		return scheduleReportDetailList;
	}

	public void setScheduleReportDetailList(
			List<ScheduleReportBean> scheduleReportDetailList) {
		this.scheduleReportDetailList = scheduleReportDetailList;
	}

	public String getAvailableRecordFlag() {
		return availableRecordFlag;
	}

	public void setAvailableRecordFlag(String availableRecordFlag) {
		this.availableRecordFlag = availableRecordFlag;
	}

	public Integer getScheduleReportDetailListSize() {
		return scheduleReportDetailListSize;
	}

	public void setScheduleReportDetailListSize(Integer scheduleReportDetailListSize) {
		this.scheduleReportDetailListSize = scheduleReportDetailListSize;
	}

	public Map<Integer, String> getPaymentModeMap() {
		return paymentModeMap;
	}

	public void setPaymentModeMap(Map<Integer, String> paymentModeMap) {
		this.paymentModeMap = paymentModeMap;
	}

	public Integer getPaymentModeId() {
		return paymentModeId;
	}

	public void setPaymentModeId(Integer paymentModeId) {
		this.paymentModeId = paymentModeId;
	}

	public Map<String, String> getPaymentStatusList() {
		return paymentStatusList;
	}

	public void setPaymentStatusList(Map<String, String> paymentStatusList) {
		this.paymentStatusList = paymentStatusList;
	}

	public String getPaymentStatusId() {
		return paymentStatusId;
	}

	public void setPaymentStatusId(String paymentStatusId) {
		this.paymentStatusId = paymentStatusId;
	}

	public List<PaymentReportBean> getPaymentReportDetailList() {
		return paymentReportDetailList;
	}

	public void setPaymentReportDetailList(
			List<PaymentReportBean> paymentReportDetailList) {
		this.paymentReportDetailList = paymentReportDetailList;
	}

	public Integer getPaymentReportDetailListSize() {
		return paymentReportDetailListSize;
	}

	public void setPaymentReportDetailListSize(Integer paymentReportDetailListSize) {
		this.paymentReportDetailListSize = paymentReportDetailListSize;
	}

	public List<PaymentReportBean> getPaymentCollectionReportDetailList() {
		return paymentCollectionReportDetailList;
	}

	public void setPaymentCollectionReportDetailList(
			List<PaymentReportBean> paymentCollectionReportDetailList) {
		this.paymentCollectionReportDetailList = paymentCollectionReportDetailList;
	}

	public Integer getPaymentCollectionReportDetailListSize() {
		return paymentCollectionReportDetailListSize;
	}

	public void setPaymentCollectionReportDetailListSize(
			Integer paymentCollectionReportDetailListSize) {
		this.paymentCollectionReportDetailListSize = paymentCollectionReportDetailListSize;
	}

    public String getMenuKey()
    {
        return menuKey;
    }

    public void setMenuKey(String menuKey)
    {
        this.menuKey = menuKey;
    }

    public Map<Integer, String> getStatusListMap()
    {
        return statusListMap;
    }

    public void setStatusListMap(Map<Integer, String> statusListMap)
    {
        this.statusListMap = statusListMap;
    }

    public String getPaymentReconcStatusVal()
    {
        return paymentReconcStatusVal;
    }

    public void setPaymentReconcStatusVal(String paymentReconcStatusVal)
    {
        this.paymentReconcStatusVal = paymentReconcStatusVal;
    }

    public List<PaymentReconciliationReportBean> getPaymentReconciliationReportData()
    {
        return paymentReconciliationReportData;
    }

    public void setPaymentReconciliationReportData(
            List<PaymentReconciliationReportBean> paymentReconciliationReportData)
    {
        this.paymentReconciliationReportData = paymentReconciliationReportData;
    }
    
    public List<PaymentReconciliationReportBean> getPaymentReconciliationReportDataWithDetail() {
		return paymentReconciliationReportDataWithDetail;
	}

	public void setPaymentReconciliationReportDataWithDetail(
			List<PaymentReconciliationReportBean> paymentReconciliationReportDataWithDetail) {
		this.paymentReconciliationReportDataWithDetail = paymentReconciliationReportDataWithDetail;
	}

	public String getDisplayMainResult()
    {
        return displayMainResult;
    }

    public void setDisplayMainResult(String displayMainResult)
    {
        this.displayMainResult = displayMainResult;
    }

    public String getDisplayCanidateWiseResult()
    {
        return displayCanidateWiseResult;
    }

    public void setDisplayCanidateWiseResult(String displayCanidateWiseResult)
    {
        this.displayCanidateWiseResult = displayCanidateWiseResult;
    }

    public PaymentReconciliationReportBean getTotAmtPymtReconcMainResult()
    {
        return totAmtPymtReconcMainResult;
    }

    public void setTotAmtPymtReconcMainResult(
            PaymentReconciliationReportBean totAmtPymtReconcMainResult)
    {
        this.totAmtPymtReconcMainResult = totAmtPymtReconcMainResult;
    }

    public PaymentReconciliationReportBean getPaymentReconcilliationForUserID()
    {
        return paymentReconcilliationForUserID;
    }

    public void setPaymentReconcilliationForUserID(
            PaymentReconciliationReportBean paymentReconcilliationForUserID)
    {
        this.paymentReconcilliationForUserID = paymentReconcilliationForUserID;
    }

    public String getAttemptFlag()
    {
        return attemptFlag;
    }

    public void setAttemptFlag(String attemptFlag)
    {
        this.attemptFlag = attemptFlag;
    }

	public String getPaymentCollReportMsg() {
		return paymentCollReportMsg;
	}

	public void setPaymentCollReportMsg(String paymentCollReportMsg) {
		this.paymentCollReportMsg = paymentCollReportMsg;
	}

	public String getPaymentCollReportFlag() {
		return paymentCollReportFlag;
	}

	public void setPaymentCollReportFlag(String paymentCollReportFlag) {
		this.paymentCollReportFlag = paymentCollReportFlag;
	}

	public List<String> getTestDatesList() {
		return testDatesList;
	}

	public void setTestDatesList(List<String> testDatesList) {
		this.testDatesList = testDatesList;
	}
    
}
