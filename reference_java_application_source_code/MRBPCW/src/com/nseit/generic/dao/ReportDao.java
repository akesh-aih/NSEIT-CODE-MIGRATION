package com.nseit.generic.dao;

import java.sql.Connection;
import java.util.List;

import com.nseit.generic.models.HallTicketBean;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.PaymentReconciliationReportBean;
import com.nseit.generic.models.PaymentReportBean;
import com.nseit.generic.models.ReportBean;
import com.nseit.generic.models.ScheduleReportBean;

public interface ReportDao {

	public Connection getDataSourceConnection() throws Exception;

	public List<List> getSeatUtilizationReport() throws Exception;

	public List<String> getTestDates(String tstCntrPk) throws Exception;

	public List<String> getTestsSlots(String testDate) throws Exception;

	public List<HallTicketBean> getBulkDownloadDataForHallTicket(ReportBean reportBean, boolean isRollNumGen) throws Exception;

	public int getScheduleReportDetailsCount(ReportBean reportBean);

	public List<ScheduleReportBean> getScheduleReportDetail(ReportBean reportBean, Pagination pagination);

	public List<PaymentReportBean> getPaymentReportDetail(ReportBean reportBean);

	public int getPaymentCollectionReportDetailCount(ReportBean reportBean);

	public List<PaymentReportBean> getPaymentCollectionReportDetail(ReportBean reportBean, Pagination pagination);

	public List<PaymentReportBean> getPaymentCollectionReportDetail(ReportBean reportBean);

	public List<PaymentReconciliationReportBean> getPaymentReconcilliationStatusResult(ReportBean reportBean) throws Exception;

	public PaymentReconciliationReportBean getPaymentReconcilliationDetailsForUserID(ReportBean reportBean) throws Exception;

	public List<PaymentReconciliationReportBean> getPaymentReconcilliationCandidateWiseDetails(ReportBean reportBean) throws Exception;

	public List<PaymentReconciliationReportBean> getPaymentReconcilliationCandidateWiseDetails(ReportBean reportBean, Pagination pagination) throws Exception;

	public List<HallTicketBean> getBulkAdmitCard(String test) throws Exception;

}