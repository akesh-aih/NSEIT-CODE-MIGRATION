package com.nseit.generic.action;

import static com.nseit.generic.util.GenericConstants.GLOBAL_PLAIN_TEXT_MESSAGE;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.dispatcher.HttpParameters;

import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.HallTicketBean;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.PaymentReconciliationReportBean;
import com.nseit.generic.models.PaymentReportBean;
import com.nseit.generic.models.ReportBean;
import com.nseit.generic.models.ScheduleReportBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.CandidateService;
import com.nseit.generic.service.ReportService;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.ExcelCreator;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.aware.UserAware;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.query.JRJdbcQueryExecuter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * The Class ReportAction.
 */
public class ReportAction extends BaseAction implements ModelDriven<ReportBean>, UserAware {
	private static final long serialVersionUID = 6269211425788474393L;

	private Logger logger = LoggerHome.getLogger(getClass());

	/** The report bean. */
	ReportBean reportBean = new ReportBean();

	/** The data list. */
	private List<ReportBean> dataList;

	/** The jasper parameter. */
	private Map jasperParameter = new HashMap();

	/** The jrempty data source. */
	private JREmptyDataSource jremptyDataSource;

	/** The connection. */
	private Connection connection;

	/** The report service. */
	private ReportService reportService;

	private CandidateService candidateService;

	/** The parameter values. */
	private HashMap parameterValues;

	/** The query executer. */
	private JRJdbcQueryExecuter queryExecuter;

	private List<HallTicketBean> hallTicketBeanBulkList;

	List<PaymentReconciliationReportBean> paymentReconciliationCandExport;

	private Pagination pagination = new Pagination(10, 1);

	public ReportAction() {
		// logger.info("ReportAction constructor is calling");
		displayMenus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nseit.generic.util.aware.UserAware#setLoggedInUser(com.nseit.generic.
	 * models.Users)
	 */
	public void setLoggedInUser(Users users) {
		loggedInUser = users;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nseit.generic.action.BaseAction#resetModel()
	 */
	@Override
	public void resetModel() {

		reportBean = new ReportBean();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	@Override
	public ReportBean getModel() {
		return reportBean;
	}

	/**
	 * Sets the report service.
	 *
	 * @param reportService
	 *            the new report service
	 */
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public HashMap getParameterValues() {
		return parameterValues;
	}

	public JREmptyDataSource getJremptyDataSource() {
		return jremptyDataSource;
	}

	public Connection getConnection() {
		return connection;
	}

	public List<HallTicketBean> getHallTicketBeanBulkList() {
		return hallTicketBeanBulkList;
	}

	public List<PaymentReconciliationReportBean> getPaymentReconciliationCandExport() {
		return paymentReconciliationCandExport;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	/**
	 * Gets the schedule details.
	 *
	 * @return the schedule details
	 */
	public String getScheduleDetails() {
		String result = null;
		logger.debug("Method call started " + this.getClass() + " : getScheduleDetails  ");
		try {

			dataList = new ArrayList();

			parameterValues = new HashMap();

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			java.util.Date date = formatter.parse(reportBean.getStartDate());

			Date startDate = new Date(date.getTime());

			java.sql.Timestamp startDate1 = new java.sql.Timestamp(startDate.getTime());

			date = formatter.parse(reportBean.getEndDate());

			Date endDate = new Date(date.getTime());

			java.sql.Timestamp endDate1 = new java.sql.Timestamp(endDate.getTime());

			parameterValues.put("START_DATE", startDate1);
			parameterValues.put("END_DATE", endDate1);

			parameterValues.put("TEST_CENTER_CODE", reportBean.getTestCenterId());

			jremptyDataSource = new JREmptyDataSource();

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("PDF")) {
				result = "pdf_schedule";
			}

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("EXCEL")) {
				result = "excel_schedule";
			}

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("HTML")) {
				result = "html_schedule";
			}

			connection = reportService.getDataSourceConnection();
			parameterValues.put(JRParameter.REPORT_CONNECTION, connection);

		} catch (Exception e) {
			logger.fatal(e, e);
		}
		logger.debug("Method call end " + this.getClass() + " : getScheduleDetails  ");
		return result;

	}

	/**
	 * Gets the discipline wise pie chart report.
	 *
	 * @return the discipline wise pie chart report
	 */
	public String getDisciplineWisePieChartReport() {
		logger.debug("Method call started " + this.getClass() + " : getDisciplineWisePieChartReport  ");
		try {

			populateFileFormat();

		} catch (Exception e) {
			logger.fatal(e, e);
		}
		logger.debug("Method call end " + this.getClass() + " : getDisciplineWisePieChartReport  ");
		return "DisciplineWisePieChartReport";
	}

	/**
	 * Gets the day wise line chart report for registration.
	 *
	 * @return the day wise line chart report for registration
	 */
	public String getDayWiseLineChartReportForRegistration() {
		logger.debug("Method call started " + this.getClass() + " : getDayWiseLineChartReportForRegistration  ");

		try {

			populateFileFormat();

		} catch (Exception e) {
			logger.fatal(e, e);
		}
		logger.debug("Method call end " + this.getClass() + " : getDayWiseLineChartReportForRegistration  ");
		return "DayWiseLineChartReportForRegistration";
	}

	/**
	 * Gets the day wise line chart reg report details.
	 *
	 * @return the day wise line chart reg report details
	 */
	public String getDayWiseLineChartRegReportDetails() {
		String result = null;

		try {
			logger.debug("Method call started " + this.getClass() + " : getDayWiseLineChartRegReportDetails  ");
			dataList = new ArrayList();

			parameterValues = new HashMap();

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			java.util.Date date = formatter.parse(reportBean.getStartDate());

			Date startDate = new Date(date.getTime());

			java.sql.Timestamp startDate1 = new java.sql.Timestamp(startDate.getTime());

			date = formatter.parse(reportBean.getEndDate());

			Date endDate = new Date(date.getTime());

			java.sql.Timestamp endDate1 = new java.sql.Timestamp(endDate.getTime());

			parameterValues.put("From_Date", startDate1);
			parameterValues.put("To_Date", endDate1);

			jremptyDataSource = new JREmptyDataSource();

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("PDF")) {
				result = "pdf_line";
			}

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("EXCEL")) {
				result = "excel_line";
			}

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("HTML")) {
				result = "html_line";
			}

			connection = reportService.getDataSourceConnection();
			parameterValues.put(JRParameter.REPORT_CONNECTION, connection);

		} catch (Exception e) {
			logger.fatal(e, e);
		}
		logger.debug("Method call end " + this.getClass() + " : getDayWiseLineChartRegReportDetails  ");
		return result;
	}

	/**
	 * Gets the discipline pie chart report details.
	 *
	 * @return the discipline pie chart report details
	 */
	public String getDisciplinePieChartReportDetails() {
		logger.debug("Method call started " + this.getClass() + " : getDisciplinePieChartReportDetails  ");
		String result = null;

		try {

			dataList = new ArrayList();

			parameterValues = new HashMap();

			jremptyDataSource = new JREmptyDataSource();

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("PDF")) {
				result = "pdf_pieChart";
			}

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("EXCEL")) {
				result = "excel_pieChart";
			}

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("HTML")) {
				result = "html_pieChart";
			}

			connection = reportService.getDataSourceConnection();
			parameterValues.put(JRParameter.REPORT_CONNECTION, connection);

		} catch (Exception e) {
			logger.fatal(e, e);
		}
		logger.debug("Method call end " + this.getClass() + " : getDisciplinePieChartReportDetails  ");
		return result;
	}

	/**
	 * Gets the registration report details.
	 *
	 * @return the registration report details
	 */
	public String getRegistrationReportDetails() {
		String result = null;
		logger.debug("Method call started " + this.getClass() + " : getRegistrationReportDetails  ");
		try {

			String reportId = reportBean.getReportId();

			if (reportId != null && !reportId.equals("") && reportId.equals("12")) {
				result = generateRegistrationReport();
			}

			if (reportId != null && !reportId.equals("") && reportId.equals("15")) {
				result = getGenderWiseRegistrationReport();
			}

			if (reportId != null && !reportId.equals("") && reportId.equals("14")) {
				result = getStateWiseSpiderChartReport();
			}
			if (reportId != null && !reportId.equals("") && reportId.equals("19")) {
				result = getStageWiseEnrollmentReport();
			}

			if (reportId != null && !reportId.equals("") && reportId.equals("18")) {
				result = getQualificationWiseEnrollmentReport();
			}

			if (reportId != null && !reportId.equals("") && reportId.equals("13")) {
				result = getDayWiseRegistrationReport();
			}
			if (reportId != null && !reportId.equals("") && reportId.equals("17")) {
				result = getDesciplineEnrollmentReport();
			}

			if (reportId != null && !reportId.equals("") && reportId.equals("16")) {
				result = getEnrollmentDetailsReport();
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		logger.debug("Method call end " + this.getClass() + " : getRegistrationReportDetails  ");
		return result;
	}

	// nimish for GenderWiseReport
	/**
	 * Gets the gender wise report.
	 *
	 * @return the gender wise report
	 */
	public String getGenderWiseReport() {
		logger.debug("Method call started " + this.getClass() + " : getGenderWiseReport  ");
		try {
			populateFileFormat();
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		logger.debug("Method call end " + this.getClass() + " : getGenderWiseReport  ");
		return "genderReport";
	}

	/**
	 * Generate registration report.
	 *
	 * @return the string
	 */
	private String generateRegistrationReport() {
		logger.debug("Method call start " + this.getClass() + " : generateRegistrationReport  ");

		String result = null;
		try {
			dataList = new ArrayList();

			parameterValues = new HashMap();

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			java.util.Date date = formatter.parse(reportBean.getStartDate());

			Date startDate = new Date(date.getTime());

			java.sql.Timestamp startDate1 = new java.sql.Timestamp(startDate.getTime());

			date = formatter.parse(reportBean.getEndDate());

			Date endDate = new Date(date.getTime());

			java.sql.Timestamp endDate1 = new java.sql.Timestamp(endDate.getTime());

			parameterValues.put("From_Date", startDate1);
			parameterValues.put("To_Date", endDate1);

			jremptyDataSource = new JREmptyDataSource();

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("PDF")) {
				result = "pdf_registration";
			}

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("EXCEL")) {
				result = "excel_registration";
			}

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("HTML")) {
				result = "html_registration";
			}

			connection = reportService.getDataSourceConnection();
			parameterValues.put(JRParameter.REPORT_CONNECTION, connection);

		} catch (Exception e) {
			logger.fatal(e, e);
		}

		logger.debug("Method call end " + this.getClass() + " : generateRegistrationReport  ");
		return result;
	}

	/**
	 * Gets the gender wise pie chart report.
	 *
	 * @return the gender wise pie chart report
	 */
	public String getGenderWiseRegistrationReport() {
		logger.debug("Method call start " + this.getClass() + " : getGenderWiseRegistrationReport  ");

		String result = null;

		try {

			dataList = new ArrayList();

			parameterValues = new HashMap();

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			java.util.Date date = formatter.parse(reportBean.getStartDate());

			Date startDate = new Date(date.getTime());

			java.sql.Timestamp startDate1 = new java.sql.Timestamp(startDate.getTime());

			date = formatter.parse(reportBean.getEndDate());

			Date endDate = new Date(date.getTime());

			java.sql.Timestamp endDate1 = new java.sql.Timestamp(endDate.getTime());

			parameterValues.put("From_Date", startDate1);
			parameterValues.put("To_Date", endDate1);

			jremptyDataSource = new JREmptyDataSource();

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("PDF")) {
				result = "pdf_gender";
			}

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("EXCEL")) {
				result = "excel_gender";
			}

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("HTML")) {
				result = "html_gender";
			}

			connection = reportService.getDataSourceConnection();
			parameterValues.put(JRParameter.REPORT_CONNECTION, connection);

		} catch (Exception e) {
			logger.fatal(e, e);
		}
		logger.debug("Method call end " + this.getClass() + " : getGenderWiseRegistrationReport  ");

		return result;
	}

	/**
	 * Gets the state wise pie chart report.
	 *
	 * @return the state wise pie chart report
	 */
	public String getStateWiseSpiderChartReport() {

		logger.debug("Method call start " + this.getClass() + " : getStateWiseSpiderChartReport  ");
		String result = null;

		try {

			dataList = new ArrayList();

			parameterValues = new HashMap();

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			java.util.Date date = formatter.parse(reportBean.getStartDate());

			Date startDate = new Date(date.getTime());

			java.sql.Timestamp startDate1 = new java.sql.Timestamp(startDate.getTime());

			date = formatter.parse(reportBean.getEndDate());

			Date endDate = new Date(date.getTime());

			java.sql.Timestamp endDate1 = new java.sql.Timestamp(endDate.getTime());

			parameterValues.put("From_Date", startDate1);
			parameterValues.put("To_Date", endDate1);

			jremptyDataSource = new JREmptyDataSource();

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("PDF")) {
				result = "pdf_state";
			}

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("EXCEL")) {
				result = "excel_state";
			}

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("HTML")) {
				result = "html_state";
			}

			connection = reportService.getDataSourceConnection();
			parameterValues.put(JRParameter.REPORT_CONNECTION, connection);

		} catch (Exception e) {
			logger.fatal(e, e);
		}
		logger.debug("Method call end " + this.getClass() + " : getStateWiseSpiderChartReport  ");
		return result;
	}

	/**
	 * Gets the day wise registration report.
	 *
	 * @return the day wise registration report
	 */
	public String getDayWiseRegistrationReport() {
		logger.debug("Method call start " + this.getClass() + " : getDayWiseRegistrationReport  ");

		String result = null;

		try {

			dataList = new ArrayList();

			parameterValues = new HashMap();

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			java.util.Date date = formatter.parse(reportBean.getStartDate());

			Date startDate = new Date(date.getTime());

			java.sql.Timestamp startDate1 = new java.sql.Timestamp(startDate.getTime());

			date = formatter.parse(reportBean.getEndDate());

			Date endDate = new Date(date.getTime());

			java.sql.Timestamp endDate1 = new java.sql.Timestamp(endDate.getTime());

			parameterValues.put("From_Date", startDate1);
			parameterValues.put("To_Date", endDate1);

			jremptyDataSource = new JREmptyDataSource();

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("PDF")) {
				result = "pdf_lineRprt";
			}

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("EXCEL")) {
				result = "excel_lineRprt";
			}

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("HTML")) {
				result = "html_lineRprt";
			}

			connection = reportService.getDataSourceConnection();
			parameterValues.put(JRParameter.REPORT_CONNECTION, connection);

		} catch (Exception e) {
			logger.fatal(e, e);
		}
		logger.debug("Method call end " + this.getClass() + " : getDayWiseRegistrationReport  ");
		return result;
	}

	/**
	 * Gets the qualification wise pie chart report.
	 *
	 * @return the qualification wise pie chart report
	 */
	public String getQualificationWiseEnrollmentReport() {

		logger.debug("Method call start " + this.getClass() + " : getQualificationWiseEnrollmentReport  ");

		String result = null;

		try {

			dataList = new ArrayList();

			parameterValues = new HashMap();

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			java.util.Date date = formatter.parse(reportBean.getStartDate());

			Date startDate = new Date(date.getTime());

			java.sql.Timestamp startDate1 = new java.sql.Timestamp(startDate.getTime());

			date = formatter.parse(reportBean.getEndDate());

			Date endDate = new Date(date.getTime());

			java.sql.Timestamp endDate1 = new java.sql.Timestamp(endDate.getTime());

			parameterValues.put("From_Date", startDate1);
			parameterValues.put("To_Date", endDate1);

			jremptyDataSource = new JREmptyDataSource();

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("PDF")) {
				result = "pdf_qualification";
			}

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("EXCEL")) {
				result = "excel_qualification";
			}

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("HTML")) {
				result = "html_qualification";
			}

			connection = reportService.getDataSourceConnection();
			parameterValues.put(JRParameter.REPORT_CONNECTION, connection);

		} catch (Exception e) {
			logger.fatal(e, e);
		}
		logger.debug("Method call end " + this.getClass() + " : getQualificationWiseEnrollmentReport  ");

		return result;
	}

	/**
	 * Gets the descipline wise pie chart report.
	 *
	 * @return the descipline wise pie chart report
	 */
	public String getDesciplineEnrollmentReport() {

		logger.debug("Method call start " + this.getClass() + " : getDesciplineEnrollmentReport  ");

		String result = null;

		try {

			dataList = new ArrayList();

			parameterValues = new HashMap();

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			java.util.Date date = formatter.parse(reportBean.getStartDate());

			Date startDate = new Date(date.getTime());

			java.sql.Timestamp startDate1 = new java.sql.Timestamp(startDate.getTime());

			date = formatter.parse(reportBean.getEndDate());

			Date endDate = new Date(date.getTime());

			java.sql.Timestamp endDate1 = new java.sql.Timestamp(endDate.getTime());

			parameterValues.put("From_Date", startDate1);
			parameterValues.put("To_Date", endDate1);

			jremptyDataSource = new JREmptyDataSource();

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("PDF")) {
				result = "pdf_discipline";
			}

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("EXCEL")) {
				result = "excel_discipline";
			}

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("HTML")) {
				result = "html_discipline";
			}

			connection = reportService.getDataSourceConnection();
			parameterValues.put(JRParameter.REPORT_CONNECTION, connection);

		} catch (Exception e) {
			logger.fatal(e, e);
		}
		logger.debug("Method call end " + this.getClass() + " : getDesciplineEnrollmentReport  ");
		return result;
	}

	/**
	 * Gets the stage wise pie chart report.
	 *
	 * @return the stage wise pie chart report
	 */
	public String getStageWiseEnrollmentReport() {

		logger.debug("Method call start " + this.getClass() + " : getStageWiseEnrollmentReport  ");

		String result = null;
		try {
			dataList = new ArrayList();

			parameterValues = new HashMap();

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			java.util.Date date = formatter.parse(reportBean.getStartDate());

			Date startDate = new Date(date.getTime());

			java.sql.Timestamp startDate1 = new java.sql.Timestamp(startDate.getTime());

			date = formatter.parse(reportBean.getEndDate());

			Date endDate = new Date(date.getTime());

			java.sql.Timestamp endDate1 = new java.sql.Timestamp(endDate.getTime());

			parameterValues.put("From_Date", startDate1);
			parameterValues.put("To_Date", endDate1);

			jremptyDataSource = new JREmptyDataSource();

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("PDF")) {
				result = "pdf_stage";
			}

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("EXCEL")) {
				result = "excel_stage";
			}

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("HTML")) {
				result = "html_stage";
			}

			connection = reportService.getDataSourceConnection();
			parameterValues.put(JRParameter.REPORT_CONNECTION, connection);

		} catch (Exception e) {
			logger.fatal(e, e);
		}
		logger.debug("Method call end " + this.getClass() + " : getStageWiseEnrollmentReport  ");
		return result;
	}

	/**
	 * Gets the enrollment details report.
	 *
	 * @return the enrollment details report
	 */
	private String getEnrollmentDetailsReport() {
		String result = null;
		logger.debug("Method call start " + this.getClass() + " : getEnrollmentDetailsReport  ");
		try {
			dataList = new ArrayList();

			parameterValues = new HashMap();

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			java.util.Date date = formatter.parse(reportBean.getStartDate());

			Date startDate = new Date(date.getTime());

			java.sql.Timestamp startDate1 = new java.sql.Timestamp(startDate.getTime());

			date = formatter.parse(reportBean.getEndDate());

			Date endDate = new Date(date.getTime());

			java.sql.Timestamp endDate1 = new java.sql.Timestamp(endDate.getTime());
			/* testCenterName = reportBean.getTestCenterId().toString(); */

			parameterValues.put("From_Date", startDate1);
			parameterValues.put("To_Date", endDate1);

			jremptyDataSource = new JREmptyDataSource();

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("PDF")) {
				result = "pdf_enrollRprt";
			}

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("EXCEL")) {
				result = "excel_enrollRprt";
			}

			if (reportBean.getFormat() != null && !reportBean.getFormat().equals("") && reportBean.getFormat().equals("HTML")) {
				result = "html_enrollRprt";
			}

			connection = reportService.getDataSourceConnection();
			parameterValues.put(JRParameter.REPORT_CONNECTION, connection);

		} catch (Exception e) {
			logger.fatal(e, e);
		}

		logger.debug("Method call end " + this.getClass() + " : getEnrollmentDetailsReport  ");
		return result;
	}

	/**
	 * Gets the data list.
	 *
	 * @return the data list
	 */
	public List<ReportBean> getDataList() {
		return dataList;
	}

	/**
	 * Populate file format.
	 */
	private void populateFileFormat() {
		logger.debug("Method call start " + this.getClass() + " : populateFileFormat  ");

		reportBean.getFormatList().clear();
		reportBean.getFormatList().add("PDF");
		reportBean.getFormatList().add("EXCEL");
		reportBean.getFormatList().add("HTML");

		logger.debug("Method call end " + this.getClass() + " : populateFileFormat  ");
	}

	/**
	 * Gets the jasper parameter.
	 *
	 * @return the jasper parameter
	 */
	public Map getJasperParameter() {
		return jasperParameter;
	}

	public String printEnrollments() {
		logger.debug("Method call start " + this.getClass() + " : printEnrollments  ");
		try {

			parameterValues = new HashMap();

			parameterValues.put("USER_ID", loggedInUser.getUserId());

			jremptyDataSource = new JREmptyDataSource();

			connection = reportService.getDataSourceConnection();
			parameterValues.put(JRParameter.REPORT_CONNECTION, connection);

		} catch (Exception e) {
			logger.fatal(e, e);
		}
		logger.debug("Method call end " + this.getClass() + " : printEnrollments  ");
		return "printEnrollment_pdf";
	}

	public String getSeatUtilizationReport() {
		try {
			List<List> list = reportService.getSeatUtilizationReport();
			request.setAttribute("reportList", list);
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return SUCCESS;
	}

	public String getAttendenceReport() {
		String result = null;
		Map<Integer, String> discliplineListMap = new LinkedHashMap<Integer, String>();
		Map<Integer, String> testCenterListMap = new LinkedHashMap<Integer, String>();
		try {

			discliplineListMap = ConfigurationConstants.getInstance().getDisciplineMap();
			testCenterListMap = ConfigurationConstants.getInstance().getTestCenterMasterMap();

			if (discliplineListMap != null && !discliplineListMap.isEmpty()) {
				reportBean.setDisciplineListMap(discliplineListMap);
			}

			if (testCenterListMap != null && !testCenterListMap.isEmpty()) {
				reportBean.setTestCenterListMap(testCenterListMap);
			}

			result = "attendenceReportPage";
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return result;
	}

	public String downloadAdmitCardWrittenTest() throws Exception {
		// logger.info("calling downloadAdmitCardWrittenTest");
		String jasperPath = null;

		Integer admitCardCount = 0;

		try {
			jasperPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.ADMIT_CARD_DOWNLOAD_LOCATION) + "Written_Test";
			hallTicketBeanBulkList = reportService.getBulkAdmitCard("Written Test");
			if (hallTicketBeanBulkList != null) {

				for (HallTicketBean hallTicket : hallTicketBeanBulkList) {

					parameterValues = new HashMap<String, Object>();
					parameterValues.clear();

					String commAddress = null;
					commAddress = hallTicket.getCandidateAddress();

					if (commAddress != null && !commAddress.equals("")) {
						String[] temp = null;
						String delimiter = "\\$\\$";
						temp = commAddress.split(delimiter);
						String address = "";
						if (temp != null) {
							for (int i = 0; i < temp.length; i++) {
								if (i == 0) {
									address = temp[i];
								}
								if (i == 1) {
									address = address + "," + temp[i];
								}
								if (i == 2) {
									address = address + "," + temp[i];
								}
								if (i == 3) {
									address = address + "," + temp[i];
								}
								parameterValues.put("OCD_COMM_ADDRESS", address);
							}

						}
					}
					java.awt.Image logo = ImageIO.read(new FileInputStream("tnu_logo.jpg"));
					java.awt.Image authSign = ImageIO.read(new FileInputStream("Authorised_Sign.png"));
					CandidateBean candidateBeanphoto = candidateService.getCandidateImage(hallTicket.getUserFK());

					InputStream inputStream = null;
					if (candidateBeanphoto != null) {
						inputStream = candidateBeanphoto.getInputStreamForImage();
					}
					parameterValues.put("CAND_IMAGE", inputStream);
					parameterValues.put("TNU_LOGO", logo);
					parameterValues.put("Authorised_Sign", authSign);
					parameterValues.put("candidateName", hallTicket.getCandidateName());
					parameterValues.put("userId", hallTicket.getUserId());
					parameterValues.put("octm_category_code", hallTicket.getCategory());
					parameterValues.put("oed_roll_no", hallTicket.getRollNumber());
					parameterValues.put("otm_test_name", hallTicket.getTestName());
					parameterValues.put("otcm_test_centre_name", hallTicket.getTestCenterName());
					parameterValues.put("otcm_address", hallTicket.getTestCenterAddress() == null ? "" : hallTicket.getTestCenterAddress());
					parameterValues.put("oed_test_date", hallTicket.getTestDate());
					parameterValues.put("oed_test_time", hallTicket.getTestStartTime());
					parameterValues.put("oed_reporting_time", hallTicket.getReportingTime());

					try {
						FileInputStream inputs = new FileInputStream(request.getRealPath("/Jasper/Admit_Card_Written_Test.jasper"));
						JasperReport jr = (JasperReport) JRLoader.loadObject(inputs);
						JasperPrint jp = JasperFillManager.fillReport(jr, parameterValues, new JREmptyDataSource());
						String name = hallTicket.getUserId().replaceAll("/", "");
						JasperExportManager.exportReportToPdfFile(jp, jasperPath + "/" + name + ".pdf");

						admitCardCount++;
					} catch (Exception e) {
						e.printStackTrace();
					}
					ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger()
							.info(loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ", Generated Bulk Download For Hallticket");

					String status = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.HALL_TICKET_GENERATED_FOR_ATTEMPT_1) + "";
					Users user = loggedInUser;
					user.setUserId(hallTicket.getUserFK());
					candidateService.updateStatus(user, status);
				}

				addActionMessage("Admit Card generated for " + admitCardCount + " candidates");
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "" + admitCardCount + " Admit Card generated successfully");

			} else {
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "No Records Found");

			}

		}

		catch (Exception e) {
			addActionMessage("Error while generating admit card");
			logger.fatal(e, e);
			throw new GenericException(e);

		}
		return "admitCardforWrittenTest";
	}

	public String downloadCallLetterFieldTest() throws Exception {
		// logger.info("calling downloadCallLetterFieldTest");

		String jasperPath = null;
		Integer admitCardCount = 0;
		try {
			jasperPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.ADMIT_CARD_DOWNLOAD_LOCATION) + "Field_Test";
			hallTicketBeanBulkList = reportService.getBulkAdmitCard("Field Test");
			if (hallTicketBeanBulkList != null) {

				for (HallTicketBean hallTicket : hallTicketBeanBulkList) {

					parameterValues = new HashMap<String, Object>();
					parameterValues.clear();

					String commAddress = null;
					commAddress = hallTicket.getCandidateAddress();

					if (commAddress != null && !commAddress.equals("")) {
						String[] temp = null;
						String delimiter = "\\$\\$";
						temp = commAddress.split(delimiter);
						String address = "";
						if (temp != null) {
							for (int i = 0; i < temp.length; i++) {
								if (i == 0) {
									address = temp[i];
								}
								if (i == 1) {
									address = address + "," + temp[i];
								}
								if (i == 2) {
									address = address + "," + temp[i];
								}
								if (i == 3) {
									address = address + "," + temp[i];
								}
								parameterValues.put("OCD_COMM_ADDRESS", address);
							}

						}
					}
					java.awt.Image logo = ImageIO.read(new FileInputStream("tnu_logo.jpg"));
					java.awt.Image authSign = ImageIO.read(new FileInputStream("Authorised_Sign.png"));
					CandidateBean candidateBeanphoto = candidateService.getCandidateImage(hallTicket.getUserFK());

					InputStream inputStream = null;

					if (candidateBeanphoto != null) {
						inputStream = candidateBeanphoto.getInputStreamForImage();

					}
					parameterValues.put("CAND_IMAGE", inputStream);
					parameterValues.put("TNU_LOGO", logo);
					parameterValues.put("Authorised_Sign", authSign);
					parameterValues.put("candidateName", hallTicket.getCandidateName());
					parameterValues.put("userId", hallTicket.getUserId());
					parameterValues.put("octm_category_code", hallTicket.getCategory());
					parameterValues.put("oed_roll_no", hallTicket.getRollNumber());
					parameterValues.put("otm_test_name", hallTicket.getTestName());
					parameterValues.put("otcm_test_centre_name", hallTicket.getTestCenterName());
					parameterValues.put("otcm_address", hallTicket.getTestCenterAddress() == null ? "" : hallTicket.getTestCenterAddress());
					parameterValues.put("oed_test_date", hallTicket.getTestDate());
					parameterValues.put("oed_test_time", hallTicket.getTestStartTime());
					parameterValues.put("oed_reporting_time", hallTicket.getReportingTime());

					try {

						FileInputStream inputs = new FileInputStream(request.getRealPath("/Jasper/Admit_Card_Field_Test.jasper"));
						JasperReport jr = (JasperReport) JRLoader.loadObject(inputs);
						JasperPrint jp = JasperFillManager.fillReport(jr, parameterValues, new JREmptyDataSource());
						String name = hallTicket.getUserId().replaceAll("/", "");
						JasperExportManager.exportReportToPdfFile(jp, jasperPath + "/" + name + ".pdf");

					} catch (Exception e) {
						e.printStackTrace();
					}

					ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger()
							.info(loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ", Generated Bulk Download For Hallticket");
					admitCardCount++;
					String status = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.CALL_LETTER_FIELD_TEST) + "";
					Users user = loggedInUser;
					user.setUserId(hallTicket.getUserFK());
					candidateService.updateStatus(user, status);
				}

				addActionMessage("Call Letter generated for " + admitCardCount + " candidates");
			}
		}

		catch (Exception e) {
			addActionMessage("Error while generating admit card");
			logger.fatal(e, e);
			throw new GenericException(e);
		}

		return "admitCardforFieldTest";
	}

	public String downloadCallLetterInterview() throws Exception {
		// logger.info("calling downloadCallLetterInterview");
		Integer admitCardCount = 0;
		String jasperPath = null;
		try {
			jasperPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.ADMIT_CARD_DOWNLOAD_LOCATION) + "Interview";
			hallTicketBeanBulkList = reportService.getBulkAdmitCard("Interview");
			if (hallTicketBeanBulkList != null) {

				for (HallTicketBean hallTicket : hallTicketBeanBulkList) {

					parameterValues = new HashMap<String, Object>();
					parameterValues.clear();

					String commAddress = null;
					commAddress = hallTicket.getCandidateAddress();

					if (commAddress != null && !commAddress.equals("")) {
						String[] temp = null;
						String delimiter = "\\$\\$";
						temp = commAddress.split(delimiter);
						String address = "";
						if (temp != null) {
							for (int i = 0; i < temp.length; i++) {
								if (i == 0) {
									address = temp[i];
								}
								if (i == 1) {
									address = address + "," + temp[i];
								}
								if (i == 2) {
									address = address + "," + temp[i];
								}
								if (i == 3) {
									address = address + "," + temp[i];
								}
								parameterValues.put("OCD_COMM_ADDRESS", address);
							}

						}
					}
					java.awt.Image logo = ImageIO.read(new FileInputStream("tnu_logo.jpg"));
					java.awt.Image authSign = ImageIO.read(new FileInputStream("Authorised_Sign.png"));
					CandidateBean candidateBeanphoto = candidateService.getCandidateImage(hallTicket.getUserFK());

					InputStream inputStream = null;

					if (candidateBeanphoto != null) {
						inputStream = candidateBeanphoto.getInputStreamForImage();

					}
					parameterValues.put("CAND_IMAGE", inputStream);
					parameterValues.put("TNU_LOGO", logo);
					parameterValues.put("Authorised_Sign", authSign);
					parameterValues.put("candidateName", hallTicket.getCandidateName());
					parameterValues.put("userId", hallTicket.getUserId());
					parameterValues.put("octm_category_code", hallTicket.getCategory());
					parameterValues.put("oed_roll_no", hallTicket.getRollNumber());
					parameterValues.put("otm_test_name", hallTicket.getTestName());
					parameterValues.put("otcm_test_centre_name", hallTicket.getTestCenterName());
					parameterValues.put("otcm_address", hallTicket.getTestCenterAddress() == null ? "" : hallTicket.getTestCenterAddress());
					parameterValues.put("oed_test_date", hallTicket.getTestDate());
					parameterValues.put("oed_test_time", hallTicket.getTestStartTime());
					parameterValues.put("oed_reporting_time", hallTicket.getReportingTime());

					try {

						FileInputStream inputs = new FileInputStream(request.getRealPath("/Jasper/Admit_Card_Interview.jasper"));
						JasperReport jr = (JasperReport) JRLoader.loadObject(inputs);
						JasperPrint jp = JasperFillManager.fillReport(jr, parameterValues, new JREmptyDataSource());
						String name = hallTicket.getUserId().replaceAll("/", "");
						JasperExportManager.exportReportToPdfFile(jp, jasperPath + "/" + name + ".pdf");

					} catch (Exception e) {
						e.printStackTrace();
					}

					ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger()
							.info(loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ", Generated Bulk Download For Hallticket");
					admitCardCount++;
					String status = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.CALL_LETTER_INTERVIEW) + "";
					Users user = loggedInUser;
					user.setUserId(hallTicket.getUserFK());
					candidateService.updateStatus(user, status);

				}

				addActionMessage("Call Letter generated for " + admitCardCount + " candidates");
			}
		}

		catch (Exception e) {
			addActionMessage("Error while generating admit card");
			logger.fatal(e, e);
			throw new GenericException(e);
		}
		return "admitCardforInterview";
	}

	public String downloadCallLetterMedicalTest() throws Exception {
		// logger.info("calling downloadCallLetterMedicalTest");
		Integer admitCardCount = 0;
		String jasperPath = null;
		try {
			jasperPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.ADMIT_CARD_DOWNLOAD_LOCATION) + "Medical_Test";
			hallTicketBeanBulkList = reportService.getBulkAdmitCard("Medical Test");
			if (hallTicketBeanBulkList != null) {

				for (HallTicketBean hallTicket : hallTicketBeanBulkList) {

					parameterValues = new HashMap<String, Object>();
					parameterValues.clear();

					String commAddress = null;
					commAddress = hallTicket.getCandidateAddress();

					if (commAddress != null && !commAddress.equals("")) {
						String[] temp = null;
						String delimiter = "\\$\\$";
						temp = commAddress.split(delimiter);
						String address = "";
						if (temp != null) {
							for (int i = 0; i < temp.length; i++) {
								if (i == 0) {
									address = temp[i];
								}
								if (i == 1) {
									address = address + "," + temp[i];
								}
								if (i == 2) {
									address = address + "," + temp[i];
								}
								if (i == 3) {
									address = address + "," + temp[i];
								}
								parameterValues.put("OCD_COMM_ADDRESS", address);
							}

						}
					}
					java.awt.Image logo = ImageIO.read(new FileInputStream("tnu_logo.jpg"));
					java.awt.Image authSign = ImageIO.read(new FileInputStream("Authorised_Sign.png"));
					CandidateBean candidateBeanphoto = candidateService.getCandidateImage(hallTicket.getUserFK());

					InputStream inputStream = null;

					if (candidateBeanphoto != null) {
						inputStream = candidateBeanphoto.getInputStreamForImage();

					}
					parameterValues.put("CAND_IMAGE", inputStream);
					parameterValues.put("TNU_LOGO", logo);
					parameterValues.put("Authorised_Sign", authSign);
					parameterValues.put("candidateName", hallTicket.getCandidateName());
					parameterValues.put("userId", hallTicket.getUserId());
					parameterValues.put("octm_category_code", hallTicket.getCategory());
					parameterValues.put("oed_roll_no", hallTicket.getRollNumber());
					parameterValues.put("otm_test_name", hallTicket.getTestName());
					parameterValues.put("otcm_test_centre_name", hallTicket.getTestCenterName());
					parameterValues.put("otcm_address", hallTicket.getTestCenterAddress() == null ? "" : hallTicket.getTestCenterAddress());
					parameterValues.put("oed_test_date", hallTicket.getTestDate());
					parameterValues.put("oed_test_time", hallTicket.getTestStartTime());
					parameterValues.put("oed_reporting_time", hallTicket.getReportingTime());

					try {

						FileInputStream inputs = new FileInputStream(request.getRealPath("/Jasper/Admit_Card_Medical_Test.jasper"));
						JasperReport jr = (JasperReport) JRLoader.loadObject(inputs);
						JasperPrint jp = JasperFillManager.fillReport(jr, parameterValues, new JREmptyDataSource());
						String name = hallTicket.getUserId().replaceAll("/", "");
						JasperExportManager.exportReportToPdfFile(jp, jasperPath + "/" + name + ".pdf");

					} catch (Exception e) {
						e.printStackTrace();
					}

					ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger()
							.info(loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ", Generated Bulk Download For Hallticket");
					admitCardCount++;
					String status = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.CALL_LETTER_MEDICAL) + "";
					Users user = loggedInUser;
					user.setUserId(hallTicket.getUserFK());
					candidateService.updateStatus(user, status);
				}

				addActionMessage("Call Letter generated for " + admitCardCount + " candidates");
			}
		}

		catch (Exception e) {
			addActionMessage("Error while generating admit card");
			logger.fatal(e, e);
			throw new GenericException(e);
		}
		return "admitCardforMedicalTest";
	}

	public String getTestDates() {
		String responseText = "";
		List<String> testDates = null;
		try {

			if (reportBean.getTestCenterVal() != null && !reportBean.getTestCenterVal().equals("All")) {
				testDates = reportService.getTestDates(reportBean.getTestCenterVal());

				for (String date : testDates) {
					responseText = responseText + "," + date;
				}
				responseText = responseText.substring(1, responseText.length());
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, responseText);
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return "writePlainText";
	}

	public String getTestSlots() {
		String responseText = "";
		List<String> testDates = null;
		try {

			if (reportBean.getTestDate() != null && !reportBean.getTestDate().equals("All") && !reportBean.getTestDate().equals("")) {
				testDates = reportService.getTestsSlots(reportBean.getTestDate());

				for (String date : testDates) {
					responseText = responseText + "," + date;
				}
				responseText = responseText.substring(1, responseText.length());
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, responseText);
			}

		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return "writePlainText";
	}

	public String getDataForAttendenceReport() {
		reportBean.getTestCenterVal();
		reportBean.getTestDate();
		reportBean.getTestSlot();
		reportBean.getDisciplineVal();

		return null;
	}

	public String generateAttendanceReport() {
		String result = null;

		try {

			dataList = new ArrayList();

			parameterValues = new HashMap();

			jremptyDataSource = new JREmptyDataSource();

			String discipline = reportBean.getDisciplineVal();

			String testcenter = reportBean.getTestCenterVal();

			String testdate = reportBean.getTestDate();

			String testslot = reportBean.getTestSlot();

			if (testdate == null || testdate.equals("")) {
				testdate = "All";
			}

			if (discipline == null || discipline.equals("")) {
				discipline = "All";
			}

			if (testcenter == null || testcenter.equals("")) {
				testcenter = "All";
			}

			if (testslot == null || testslot.equals("")) {
				testslot = "All";
			}

			/*
			 * logger.info("Discipline : " + discipline);
			 * logger.info("testcenter : " + testcenter);
			 * logger.info("testdate : " + testdate); logger.info("testslot : "
			 * + testslot);
			 */

			parameterValues.put("DISCIPLINE", discipline);
			parameterValues.put("TEST_CENTER", testcenter);
			parameterValues.put("TEST_DATE", testdate);
			parameterValues.put("TEST_SLOT", testslot);

			connection = reportService.getDataSourceConnection();
			parameterValues.put(JRParameter.REPORT_CONNECTION, connection);

			/*
			 * String isRollNoGenerated =
			 * ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(
			 * GenericConstants.GENERATE_ROLL_NUMBER);
			 * 
			 * if("A".equals(isRollNoGenerated)) {
			 * parameterValues.put("isFieldHidden", true); } else {
			 * parameterValues.put("isFieldHidden", false); }
			 */

			String photoMenuStatus = ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.UPLOAD_PHOTO);
			String signatureMenuStatus = ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.UPLOAD_SIGNATURE);

			String genRollNumStatus = ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.GENERATE_ROLL_NUMBER);

			if ("A".equals(photoMenuStatus) && "A".equals(signatureMenuStatus) && "A".equals(genRollNumStatus)) {
				result = "attendanceReportWithRollNo";
			}

			if ("A".equals(photoMenuStatus) && "A".equals(signatureMenuStatus) && !"A".equals(genRollNumStatus)) {
				result = "attendanceReportWithoutRollNo";
			}

			if (!"A".equals(photoMenuStatus) && "A".equals(signatureMenuStatus) && "A".equals(genRollNumStatus)) {
				result = "attendanceReportOnlySignatureWithRollNo";
			}

			if (!"A".equals(photoMenuStatus) && "A".equals(signatureMenuStatus) && !"A".equals(genRollNumStatus)) {
				result = "attendanceReportOnlySignatureWithoutRollNo";
			}

			if ("A".equals(photoMenuStatus) && !"A".equals(signatureMenuStatus) && "A".equals(genRollNumStatus)) {
				result = "attendanceReportOnlyPhotoWithRollNo";
			}

			if ("A".equals(photoMenuStatus) && !"A".equals(signatureMenuStatus) && !"A".equals(genRollNumStatus)) {
				result = "attendanceReportOnlyPhotoWithoutRollNo";
			}

			if (!"A".equals(photoMenuStatus) && !"A".equals(signatureMenuStatus) && "A".equals(genRollNumStatus)) {
				result = "attendanceReportwihtoutPhotoSignWithRollNo";
			}

			if (!"A".equals(photoMenuStatus) && !"A".equals(signatureMenuStatus) && !"A".equals(genRollNumStatus)) {
				result = "attendanceReportwihtoutPhotoSignRollNo";
			}

			// ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger().info(loggedInUser.getUsername()+
			// ", "+ request.getRemoteAddr()+ ", Generated Candidate Attendance
			// Details Report");

		} catch (Exception e) {
			logger.fatal(e, e);
		}

		return result;
	}

	public String showScheduleReportView() {
		reportBean.setDisciplineListMap(ConfigurationConstants.getInstance().getDisciplineMap());
		reportBean.setTestCenterListMap(ConfigurationConstants.getInstance().getTestCenterMasterMap());
		Map<Integer, String> scheduleStatusListMap = new LinkedHashMap<Integer, String>();
		scheduleStatusListMap.put(1, "Scheduled");
		scheduleStatusListMap.put(2, "Not Scheduled");
		scheduleStatusListMap.put(3, "Pending For Scheduling");
		scheduleStatusListMap.put(4, "Admit Card Generated");
		reportBean.setScheduleStatusList(scheduleStatusListMap);
		return "scheduleReportPage";
	}

	public String getSechduleReport() {
		// logger.info("Method getSechduleReport is calling");
		try {
			/**
			 * @author shailendras Pagination Code: set total number of record
			 */
			int totalNumberOfRecord = reportService.getScheduleReportDetailsCount(reportBean);
			pagination.setProperties(totalNumberOfRecord);

			List<ScheduleReportBean> scheduleReportDetailList = reportService.getScheduleReportDetail(reportBean, pagination);
			reportBean.setScheduleReportDetailList(scheduleReportDetailList);
			pagination.setPage_records(scheduleReportDetailList.size());

			if (scheduleReportDetailList == null || scheduleReportDetailList.size() == 0) {
				reportBean.setAvailableRecordFlag("false");
			} else {
				reportBean.setScheduleReportDetailListSize(totalNumberOfRecord);
			}
			reportBean.setShowDataGridFlag("true");
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		// Loading Basic Data
		reportBean.setDisciplineListMap(ConfigurationConstants.getInstance().getDisciplineMap());
		reportBean.setTestCenterListMap(ConfigurationConstants.getInstance().getTestCenterMasterMap());
		Map<Integer, String> scheduleStatusListMap = new LinkedHashMap<Integer, String>();
		scheduleStatusListMap.put(1, "Scheduled");
		scheduleStatusListMap.put(2, "Not Scheduled");
		scheduleStatusListMap.put(3, "Pending For Scheduling");
		scheduleStatusListMap.put(4, "Admit Card Generated");
		reportBean.setScheduleStatusList(scheduleStatusListMap);

		if (reportBean.getMenuKey() != null) {
			request.setAttribute("menuKey", reportBean.getMenuKey());
		}

		return "scheduleReportPage";
	}

	public String showPaymentReportView() {
		reportBean.setDisciplineListMap(ConfigurationConstants.getInstance().getDisciplineMap());
		Map<String, String> paymentStatusListMap = new LinkedHashMap<String, String>();
		paymentStatusListMap.put("A", "Approved");
		paymentStatusListMap.put("R", "Rejected");
		reportBean.setPaymentStatusList(paymentStatusListMap);
		reportBean.setPaymentModeMap(ConfigurationConstants.getInstance().getPaymentMasterMap());
		return "paymentReportPage";
	}

	public String getPaymentReport() {
		// logger.info("Method getPaymentReport is calling");
		try {
			if (reportBean.getPaymentStatusId() != null && reportBean.getPaymentStatusId().equals("0")) {
				reportBean.setPaymentStatusId("All");
			}
			List<PaymentReportBean> paymentReportDetailList = reportService.getPaymentReportDetail(reportBean);
			reportBean.setPaymentReportDetailList(paymentReportDetailList);
			if (paymentReportDetailList == null || paymentReportDetailList.size() == 0) {
				reportBean.setAvailableRecordFlag("false");
				reportBean.setPaymentReportDetailListSize(paymentReportDetailList.size());
			} else {
				reportBean.setPaymentReportDetailListSize(paymentReportDetailList.size());
			}
			reportBean.setShowDataGridFlag("true");
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		// Loading Basic Data
		reportBean.setDisciplineListMap(ConfigurationConstants.getInstance().getDisciplineMap());
		Map<String, String> paymentStatusListMap = new LinkedHashMap<String, String>();
		paymentStatusListMap.put("A", "Approved");
		paymentStatusListMap.put("R", "Rejected");
		reportBean.setPaymentStatusList(paymentStatusListMap);
		reportBean.setPaymentModeMap(ConfigurationConstants.getInstance().getPaymentMasterMap());

		return "paymentReportPage";
	}

	public String showPaymentCollectionReportView() {
		reportBean.setPaymentModeMap(ConfigurationConstants.getInstance().getPaymentMasterMap());
		return "paymentCollectionReportPage";
	}

	public String getPaymentCollectionReport() {
		// logger.info("Method getPaymentCollectionReport is calling");
		try {
			/**
			 * @author shailendras Pagination Code: set total number of record
			 */
			int totalNumberOfRecord = reportService.getPaymentCollectionReportDetailCount(reportBean);
			pagination.setProperties(totalNumberOfRecord);

			List<PaymentReportBean> paymentCollectionReportDetailList = reportService.getPaymentCollectionReportDetail(reportBean, pagination);
			pagination.setPage_records(paymentCollectionReportDetailList.size());

			reportBean.setPaymentCollectionReportDetailList(paymentCollectionReportDetailList);
			if (paymentCollectionReportDetailList == null || paymentCollectionReportDetailList.size() == 0) {
				reportBean.setAvailableRecordFlag("false");
				reportBean.setPaymentCollectionReportDetailListSize(totalNumberOfRecord);
			} else {
				reportBean.setPaymentCollectionReportDetailListSize(totalNumberOfRecord);
			}
			reportBean.setShowDataGridFlag("true");
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		// Loading Basic Data
		reportBean.setPaymentModeMap(ConfigurationConstants.getInstance().getPaymentMasterMap());

		return "paymentCollectionReportPage";
	}

	public void exportPaymentCollectionReportToExcel() {
		// logger.info("Method exportPaymentCollectionReportToExcel is
		// calling");
		List<PaymentReportBean> paymentCollectionReportDetailList = null;
		String result = null;
		try {
			paymentCollectionReportDetailList = reportService.getPaymentCollectionReportDetail(reportBean);
			if (paymentCollectionReportDetailList != null && !paymentCollectionReportDetailList.isEmpty()) {
				result = generateExcelFile(paymentCollectionReportDetailList);
			} else {
				reportBean.setPaymentCollReportFlag("true");
				reportBean.setPaymentCollReportMsg("No Records found for selected criteria");
				reportBean.setPaymentModeMap(ConfigurationConstants.getInstance().getPaymentMasterMap());
				result = "paymentCollectionReportPage";
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		// return result;
	}

	public String generateExcelFile(List<PaymentReportBean> paymentCollectionReportDetailList) throws Exception {
		// logger.info("generateExcelFile(List<PaymentReportBean>
		// paymentCollectionReportDetailList)
		// "+paymentCollectionReportDetailList);
		try {
			ExcelCreator excelCreator = new ExcelCreator();
			HSSFWorkbook workbook = excelCreator.createWorkbookForPaymentReport(paymentCollectionReportDetailList);
			response.setHeader("Content-Disposition", "attachment; filename=PaymentCollectionReport.xls");
			response.setHeader("Cache-control", "no-cache, no-store");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Expires", "-1");
			ServletOutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Throwable e) {
			logger.fatal(e, e);
		}
		return "paymentCollectionReportPage";
	}

	public String displayPaymentReconcilliationScreen() throws Exception {
		// logger.info("displayPaymentReconcilliationScreen()");

		Map<Integer, String> discliplineListMap = new LinkedHashMap<Integer, String>();

		discliplineListMap = ConfigurationConstants.getInstance().getDisciplineMap();

		if (discliplineListMap != null && !discliplineListMap.isEmpty()) {
			reportBean.setDisciplineListMap(discliplineListMap);
		}

		Map<Integer, String> statusListMap = getStatusListMap();

		reportBean.setStatusListMap(statusListMap);

		return "paymentReconcilliationReport";

	}

	public String showPaymentReconcilliationResult() throws Exception {
		// logger.info("showPaymentReconcilliationResult()");

		Map<Integer, String> discliplineListMap = new LinkedHashMap<Integer, String>();
		discliplineListMap = ConfigurationConstants.getInstance().getDisciplineMap();
		if (discliplineListMap != null && !discliplineListMap.isEmpty()) {
			reportBean.setDisciplineListMap(discliplineListMap);
		}
		Map<Integer, String> statusListMap = getStatusListMap();
		reportBean.setStatusListMap(statusListMap);

		/*
		 * logger.info("reportBean.getUserId()   :    " +
		 * reportBean.getUserId());
		 * 
		 * logger.info("reportBean.getDisciplineVal()    :     " +
		 * reportBean.getDisciplineVal());
		 * logger.info("reportBean.getPaymentReconcStatusVal()     :     " +
		 * reportBean.getPaymentReconcStatusVal());
		 * logger.info("reportBean.getFromDate()     :    " +
		 * reportBean.getFromDate());
		 * logger.info("reportBean.getToDate()    :     " +
		 * reportBean.getToDate());
		 */

		// For User ID

		if (reportBean.getUserId() != null && !reportBean.getUserId().equals("")) {
			getReconcilliationReportForUserID();
		} else {
			getReconcilliationReportForStatus();
		}
		return "paymentReconcilliationReport";

	}

	public String showPaymentReconcilliationCandidateWiseData() throws Exception {
		// logger.info("showPaymentReconcilliationCandidateWiseData()");

		Map<Integer, String> discliplineListMap = new LinkedHashMap<Integer, String>();
		discliplineListMap = ConfigurationConstants.getInstance().getDisciplineMap();
		if (discliplineListMap != null && !discliplineListMap.isEmpty()) {
			reportBean.setDisciplineListMap(discliplineListMap);
		}
		Map<Integer, String> statusListMap = getStatusListMap();
		reportBean.setStatusListMap(statusListMap);

		/*
		 * logger.info("reportBean.getUserId()   :    " +
		 * reportBean.getUserId());
		 * 
		 * logger.info("reportBean.getDisciplineVal()    :     " +
		 * reportBean.getDisciplineVal());
		 * logger.info("reportBean.getPaymentReconcStatusVal()     :     " +
		 * reportBean.getPaymentReconcStatusVal());
		 * logger.info("reportBean.getFromDate()     :    " +
		 * reportBean.getFromDate());
		 * logger.info("reportBean.getToDate()    :     " +
		 * reportBean.getToDate());
		 */

		getReconcilliationReportForStatusCandidateWise();
		getReconcilliationReportForStatus();
		return "paymentReconcilliationReport";
	}

	private Map<Integer, String> getStatusListMap() {
		Map<Integer, String> statusListMap = new LinkedHashMap<Integer, String>();
		statusListMap.put(0, "Reconciled");
		statusListMap.put(1, "Not Reconciled");
		/*
		 * statusListMap.put(2, "Payment Submitted"); statusListMap.put(3,
		 * "Payment Not Submitted");
		 */

		return statusListMap;
	}

	private void getReconcilliationReportForUserID() {
		PaymentReconciliationReportBean paymentReconcilliationForUserID;
		try {
			paymentReconcilliationForUserID = reportService.getPaymentReconcilliationDetailsForUserID(reportBean);
			reportBean.setPaymentReconcilliationForUserID(paymentReconcilliationForUserID);
			reportBean.setDisplayCanidateWiseResult("Y");

			List<PaymentReconciliationReportBean> mainResult = new ArrayList<PaymentReconciliationReportBean>();
			mainResult.add(paymentReconcilliationForUserID);
			paymentReconciliationCandExport = mainResult;

		} catch (Throwable e) {
			logger.fatal(e, e);
		}

	}

	private void getReconcilliationReportForStatus() {
		try {
			List<PaymentReconciliationReportBean> mainResult = reportService.getPaymentReconcilliationStatusResult(reportBean);

			setResultDetailsForReconcilliation(mainResult);
		} catch (Throwable e) {
			logger.fatal(e, e);
		}
	}

	private void setResultDetailsForReconcilliation(List<PaymentReconciliationReportBean> mainResult) {
		reportBean.setPaymentReconciliationReportData(mainResult);

		BigDecimal totalApplicableFees = BigDecimal.valueOf(0);

		if (mainResult != null) {
			for (PaymentReconciliationReportBean databean : mainResult) {
				if (databean.getApplicableFeesAmt() != null) {
					totalApplicableFees = totalApplicableFees.add(databean.getApplicableFeesAmt());
				}
			}
		}

		PaymentReconciliationReportBean totAmtPymtReconcMainResult = new PaymentReconciliationReportBean();
		totAmtPymtReconcMainResult.setTotalApplicableFeesAmt(totalApplicableFees);

		if (reportBean.getPaymentReconcStatusVal().equals("")) {
			totAmtPymtReconcMainResult.setStatus("All");
		}

		if (reportBean.getPaymentReconcStatusVal().equals("0")) {
			totAmtPymtReconcMainResult.setStatus("Reconciled");
		}

		if (reportBean.getPaymentReconcStatusVal().equals("1")) {
			totAmtPymtReconcMainResult.setStatus("Not Reconciled");
		}

		if (reportBean.getPaymentReconcStatusVal().equals("2")) {
			totAmtPymtReconcMainResult.setStatus("Payment Submitted");
		}

		if (reportBean.getPaymentReconcStatusVal().equals("3")) {
			totAmtPymtReconcMainResult.setStatus("Payment Not Submitted");
		}

		reportBean.setTotAmtPymtReconcMainResult(totAmtPymtReconcMainResult);
		reportBean.setDisplayMainResult("Y");
	}

	private void getReconcilliationReportForStatusCandidateWise() {
		try {
			ReportBean bean = new ReportBean();
			bean.setPaymentReconcStatusVal(request.getParameter("statusVal"));
			bean.setDisciplineVal(request.getParameter("disciplineScriptVal"));
			bean.setFromDate(request.getParameter("startDate"));
			bean.setToDate(request.getParameter("endDate"));

			pagination.setProperties(Integer.parseInt(request.getParameter("totalCount")));
			List<PaymentReconciliationReportBean> mainResult = reportService.getPaymentReconcilliationCandidateWiseDetails(bean, pagination);

			if (mainResult != null) {
				pagination.setPage_records(mainResult.size());
			}
			// reportBean.setPaymentReconciliationReportData(mainResult);
			reportBean.setPaymentReconciliationReportDataWithDetail(mainResult);

			paymentReconciliationCandExport = mainResult;
		} catch (Throwable e) {
			logger.fatal(e, e);
		}
	}

	private void getReconcilliationReportForStatusCandidateWiseExcel() {
		try {
			ReportBean bean = new ReportBean();
			bean.setPaymentReconcStatusVal(request.getParameter("statusVal"));
			bean.setDisciplineVal(request.getParameter("disciplineScriptVal"));
			bean.setFromDate(request.getParameter("startDate"));
			bean.setToDate(request.getParameter("endDate"));
			List<PaymentReconciliationReportBean> mainResult = reportService.getPaymentReconcilliationCandidateWiseDetails(bean);
			reportBean.setPaymentReconciliationReportDataWithDetail(mainResult);
			paymentReconciliationCandExport = mainResult;
		} catch (Throwable e) {
			logger.fatal(e, e);
		}
	}

	public String generatePaymentReconcilliationExcelReport() {
		// logger.info("generatePaymentReconcilliationExcelReport()");
		String result = "exportToExcelReconcilliationDetails";

		try {
			parameterValues = new HashMap();

			/*
			 * logger.info("reportBean.getUserId()   :    " +
			 * reportBean.getUserId());
			 * 
			 * logger.info("reportBean.getDisciplineVal()    :     " +
			 * reportBean.getDisciplineVal());
			 * logger.info("reportBean.getPaymentReconcStatusVal()     :     " +
			 * reportBean.getPaymentReconcStatusVal());
			 * logger.info("reportBean.getFromDate()     :    " +
			 * reportBean.getFromDate());
			 * logger.info("reportBean.getToDate()    :     " +
			 * reportBean.getToDate());
			 */

			if (reportBean.getUserId() != null && !reportBean.getUserId().equals("")) {
				getReconcilliationReportForUserID();
			} else {
				getReconcilliationReportForStatusCandidateWiseExcel();
			}

		} catch (Exception e) {
			logger.fatal(e, e);
		}

		return result;
	}

	public String generateReconcileExcelFile(List<PaymentReportBean> paymentCollectionReportDetailList) throws Exception {
		// logger.info("generateExcelFile(List<PaymentReportBean>
		// paymentCollectionReportDetailList)
		// "+paymentCollectionReportDetailList);
		try {
			ExcelCreator excelCreator = new ExcelCreator();
			HSSFWorkbook workbook = excelCreator.createWorkbookForReconcileReport(paymentCollectionReportDetailList);
			response.setHeader("Content-Disposition", "attachment; filename=ReconciliationReport.xls");
			response.setHeader("Cache-control", "no-cache, no-store");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Expires", "-1");
			ServletOutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Throwable e) {
			logger.fatal(e, e);
		}
		return "paymentCollectionReportPage";
	}

	
	@Override
	public void withSession(Map<String, Object> sessionAttributes) {
		this.sessionAttributes = sessionAttributes;
		
	}

	@Override
	public void withParameters(HttpParameters httpParameters) {
		this.httpParameters = httpParameters;
		
	}

	@Override
	public void withServletRequest(HttpServletRequest request) {
		this.request = request;
		
	}

	@Override
	public void withServletResponse(HttpServletResponse response) {
		this.response = response;
		
	}

}
