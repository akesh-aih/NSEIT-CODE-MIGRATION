package com.nseit.generic.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.Logger;

import com.nseit.generic.dao.ReportDao;
import com.nseit.generic.models.HallTicketBean;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.PaymentReconciliationReportBean;
import com.nseit.generic.models.PaymentReportBean;
import com.nseit.generic.models.ReportBean;
import com.nseit.generic.models.ScheduleReportBean;
import com.nseit.generic.service.ReportService;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.GenericServiceException;
import com.nseit.generic.util.LoggerHome;

public class ReportServiceImpl implements ReportService {

	private ReportDao reportDao;
	private Logger logger = LoggerHome.getLogger(getClass());

	public void setReportDao(ReportDao reportDao) {
		this.reportDao = reportDao;
	}

	public Connection getDataSourceConnection() throws Exception {
		logger.debug("Method call Started " + this.getClass() + " : getDataSourceConnection");
		return reportDao.getDataSourceConnection();

	}

	public List<List> getSeatUtilizationReport() throws Exception {
		return reportDao.getSeatUtilizationReport();
	}

	public List<String> getTestDates(String tstCntrPk) throws Exception {
		List<String> testDates = null;
		try {
			testDates = reportDao.getTestDates(tstCntrPk);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return testDates;
	}

	public List<String> getTestsSlots(String testDate) throws Exception {
		List<String> testSlots = null;
		try {
			testSlots = reportDao.getTestsSlots(testDate);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return testSlots;
	}

	public List<HallTicketBean> getBulkDownloadDataForHallTicket(ReportBean reportBean, boolean isRollNumGen) throws Exception {
		List<HallTicketBean> hallTicketBeanBulkList = null;
		try {
			hallTicketBeanBulkList = reportDao.getBulkDownloadDataForHallTicket(reportBean, isRollNumGen);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return hallTicketBeanBulkList;
	}

	public List<HallTicketBean> getBulkAdmitCard(String test) throws Exception {
		List<HallTicketBean> hallTicketBeanBulkList = null;
		try {
			hallTicketBeanBulkList = reportDao.getBulkAdmitCard(test);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return hallTicketBeanBulkList;
	}

	public int getScheduleReportDetailsCount(ReportBean reportBean) throws Exception {
		return reportDao.getScheduleReportDetailsCount(reportBean);
	}

	public List<ScheduleReportBean> getScheduleReportDetail(ReportBean reportBean, Pagination pagination) throws Exception {
		return reportDao.getScheduleReportDetail(reportBean, pagination);
	}

	public List<PaymentReportBean> getPaymentReportDetail(ReportBean reportBean) throws Exception {
		return reportDao.getPaymentReportDetail(reportBean);
	}

	public int getPaymentCollectionReportDetailCount(ReportBean reportBean) throws Exception {
		return reportDao.getPaymentCollectionReportDetailCount(reportBean);
	}

	public List<PaymentReportBean> getPaymentCollectionReportDetail(ReportBean reportBean, Pagination pagination) throws Exception {
		return reportDao.getPaymentCollectionReportDetail(reportBean, pagination);
	}

	public List<PaymentReportBean> getPaymentCollectionReportDetail(ReportBean reportBean) throws Exception {
		return reportDao.getPaymentCollectionReportDetail(reportBean);
	}

	public List<PaymentReconciliationReportBean> getPaymentReconcilliationStatusResult(ReportBean reportBean) throws Exception {
		return reportDao.getPaymentReconcilliationStatusResult(reportBean);
	}

	public PaymentReconciliationReportBean getPaymentReconcilliationDetailsForUserID(ReportBean reportBean) throws Exception {
		return reportDao.getPaymentReconcilliationDetailsForUserID(reportBean);
	}

	public List<PaymentReconciliationReportBean> getPaymentReconcilliationCandidateWiseDetails(ReportBean reportBean) throws Exception {
		return reportDao.getPaymentReconcilliationCandidateWiseDetails(reportBean);
	}

	public List<PaymentReconciliationReportBean> getPaymentReconcilliationCandidateWiseDetails(ReportBean reportBean, Pagination pagination) throws Exception {
		return reportDao.getPaymentReconcilliationCandidateWiseDetails(reportBean, pagination);
	}
}
