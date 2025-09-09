package com.nseit.generic.action;

import static com.nseit.generic.util.GenericConstants.DESTINATION_URL;
import static com.nseit.generic.util.GenericConstants.GLOBAL_PLAIN_TEXT_MESSAGE;
import static com.nseit.generic.util.GenericConstants.REDIRECT_ACTION_URL;
import static com.nseit.generic.util.GenericConstants.SESSION_USER;

import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.dispatcher.multipart.UploadedFile;
import org.apache.commons.io.FileUtils;
import java.nio.file.FileSystems;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.HttpParameters;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.util.TokenHelper;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nseit.generic.aws.util.UploadDocInS3sdkV2;
import com.nseit.generic.aws.util.UploadPhotoSignInS3sdkV2;
import com.nseit.generic.dao.CommonDao;
import com.nseit.generic.models.AddressBean;
import com.nseit.generic.models.CandidateApproveRejectBean;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.CommonBean;
import com.nseit.generic.models.EducationDetailsBean;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.PersonalDetailsBean;
import com.nseit.generic.models.RegistrationBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.CandidateAdditionalDetailsService;
import com.nseit.generic.service.CandidateAgeQuotaSpecialMarksService;
import com.nseit.generic.service.CandidateEducationService;
import com.nseit.generic.service.CandidateMgmtService;
import com.nseit.generic.service.CandidatePersonalDetailsService;
import com.nseit.generic.service.CandidatePersonalDetailsService.ErrorCodeEnum;
import com.nseit.generic.service.CandidatePhotoSignatureService;
import com.nseit.generic.service.CandidatePhotoSignatureService.CandidateSignatureServiceConstants;
import com.nseit.generic.service.CandidateService;
import com.nseit.generic.service.CandidateUploadDocService;
import com.nseit.generic.service.CandidateUploadDocService.CandidateUploadDocConstants;
import com.nseit.generic.service.CommonService;
import com.nseit.generic.service.ReportService;
import com.nseit.generic.service.WorkExperienceService;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.LogicError;
import com.nseit.generic.util.aware.UserAware;
import com.nseit.generic.util.validation.AdditionalBeanValidator;
import com.nseit.generic.util.validation.DocumentBeanValidator;
import com.nseit.generic.util.validation.EducationBeanValidator;
import com.nseit.generic.util.validation.PersonalBeanValidator;
import com.nseit.generic.util.validation.PhotoBeanValidator;
import com.nseit.generic.util.validation.PreviewBeanValidator;
import com.nseit.generic.util.validation.SignatureBeanValidator;
import com.nseit.generic.util.validation.WorkExperienceValidator;
import com.nseit.payment.service.PaymentService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JREmptyDataSource;

public class CandidateAction extends BaseAction implements ModelDriven<CandidateBean>, UserAware {

	private static final long serialVersionUID = 622113247185779115L;
	private Logger logger = LoggerHome.getLogger(getClass());
	/** The candidate bean. */
	@Getter
	@Setter
	private CandidateBean candidateBean;
	@Getter
	@Setter
	private PersonalBeanValidator personalBeanValidator;
	@Getter
	@Setter
	private AdditionalBeanValidator additionalBeanValidator;
	@Getter
	@Setter
	private WorkExperienceValidator workExperienceValidator;
	@Getter
	@Setter
	private PhotoBeanValidator photoBeanValidator;
	@Getter
	@Setter
	private PreviewBeanValidator previewBeanValidator;
	@Getter
	@Setter
	private EducationBeanValidator educationBeanValidator;
	@Getter
	@Setter
	private SignatureBeanValidator  signatureBeanValidator;
	@Getter
	@Setter
	private UploadPhotoSignInS3sdkV2 uploadPhotoSignInS3sdkV2;
	
	private RegistrationBean registrationBean = new RegistrationBean();
	Map<Integer, String> testCentersMap = new HashMap<Integer, String>();
	Map<Integer, Integer> selectedTestCenterMap = new HashMap<Integer, Integer>();
	/** The candidate service. */
	private CandidateService candidateService;
	private CandidateEducationService candidateEducationService;
	private CandidateAdditionalDetailsService candidateAdditionalDetailsService;
	private CandidatePhotoSignatureService candidatePhotoSignatureService;
	private CandidateUploadDocService candidateUploadDocService;
	private CandidateAgeQuotaSpecialMarksService candidateAgeQuotaSpecialMarksService;
	private WorkExperienceService workExperienceService;

	@Setter
	private DocumentBeanValidator documentBeanValidator;
	@Override
	public void setLoggedInUser(Users users) {
		this.loggedInUser = users;
	}

	@Override
	public void resetModel() {
		candidateBean = new CandidateBean();
	}

	public void setWorkExperienceService(WorkExperienceService workExperienceService) {
		this.workExperienceService = workExperienceService;
	}

	public void setCandidateAgeQuotaSpecialMarksService(CandidateAgeQuotaSpecialMarksService candidateAgeQuotaSpecialMarksService) {
		this.candidateAgeQuotaSpecialMarksService = candidateAgeQuotaSpecialMarksService;
	}

	public void setCandidatePhotoSignatureService(CandidatePhotoSignatureService candidatePhotoSignatureService) {
		this.candidatePhotoSignatureService = candidatePhotoSignatureService;
	}

	public void setCandidateAdditionalDetailsService(CandidateAdditionalDetailsService candidateAdditionalDetailsService) {
		this.candidateAdditionalDetailsService = candidateAdditionalDetailsService;
	}

	public void setCandidateEducationService(CandidateEducationService candidateEducationService) {
		this.candidateEducationService = candidateEducationService;
	}

	public void setCandidateUploadDocService(CandidateUploadDocService candidateUploadDocService) {
		this.candidateUploadDocService = candidateUploadDocService;
	}

	private CandidateMgmtService candidateMgmtService;

	/** The common service. */
	private CommonService commonService;

	private ReportService reportService;

	private PaymentService paymentOfflineService;
	private CandidatePersonalDetailsService candidatePersonalDetailsService;
	private CommonDao commonDao;
	private HashMap parameterValues;
	private JREmptyDataSource jremptyDataSource;
	private Connection connection;
	private InputStream documentInputStream;
	private Pagination pagination = new Pagination(10, 1);
	private String token;
	private String val;
	private String post;
	private String dept;

	DateTimeFormatter yyyy_MM_dd_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	DateTimeFormatter dd_MM_yyyy_formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private static String ageAsOn = "2023-06-20";
	private static int maxAge = 60;
	private static int minAge = 21;

	public CandidateAction() {
		displayMenus();
	}

	@Override
	public CandidateBean getModel() {
		return this.candidateBean;
	}

	public void setCandidateMgmtService(CandidateMgmtService candidateMgmtService) {
		this.candidateMgmtService = candidateMgmtService;
	}

	public void setPaymentService(PaymentService paymentOfflineService) {
		this.paymentOfflineService = paymentOfflineService;
	}

	public void setCandidatePersonalDetailsService(CandidatePersonalDetailsService candidatePersonalDetailsService) {
		this.candidatePersonalDetailsService = candidatePersonalDetailsService;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	/**
	 * Sets the candidate service.
	 * 
	 * @param candidateService the new candidate service
	 */
	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	/**
	 * Sets the common service.
	 * 
	 * @param commonService the new common service
	 */
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
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

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#input()
	 */
	public String input() {
		return INPUT;
	}

	/*
	 * Method Action requires to fetch Details from registration and personal Page
	 */
	public String getCandidateDetails() throws Exception {
		String result = null;
		Map<Integer, List<Long>> dateWindowMap = ConfigurationConstants.getInstance().getDateWindowMap();
		List<Long> dateList = dateWindowMap.get(1);
		Long startDate = dateList.get(0);
		Long endDate = dateList.get(1);
		long today = ZonedDateTime.now().toInstant().toEpochMilli();
		candidateBean.setMaxDate(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_DATE_FOR_DATEPICKER));
		
		List<Long> notificationDate = dateWindowMap.get(8);
		Long notificationEndDate = notificationDate.get(1);
		
		candidateBean.setNotificationEndDate(CommonUtil.formatDate(new Date(notificationEndDate), GenericConstants.DATE_FORMAT_DEFAULT));
		
		candidateBean.setAgeAsOn(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AGE_AS_ON));
		if ((today == startDate || today > startDate) && (today == endDate || today < endDate)) {
			Users users = (Users) sessionAttributes.get(SESSION_USER);
			users.setCurrentStage(Double.valueOf(ConfigurationConstants.getInstance().getStageKey(GenericConstants.STAGE_REGISTRATION_CONFIRMED)));
			result = candidatePersonalDetailsService.getCandidateDetails(users, candidateBean, sessionAttributes, request);
			commonService.insertCandidateAuditrail(loggedInUser, "Personal Page-Loaded",
					"Candidate User Id : " + loggedInUser.getUsername() + " ~ Personal Page : Loaded");

			if (result != null && result.equals("notloggedin")) {
				candidateBean.setDeclarationhid("false");
				result = "notloggedin";
			}
		} else {
			// Used for displaying closure date in closewindow.jsp
			candidateBean.setAppFormStrtDate(CommonUtil.formatDate(new Date(startDate), GenericConstants.DATE_FORMAT_DEFAULT1));
			String newStartDate = CommonUtil.formatDate(new Date(startDate), GenericConstants.DATE_FORMAT_DEFAULT1);
			String newEndDate = CommonUtil.formatDate(new Date(endDate), GenericConstants.DATE_FORMAT_DEFAULT1);
			String[] endDateFinal = newEndDate.split(" ");
			String[] startDateFinal = newStartDate.split(" ");
			candidateBean.setAppFormEndDate(" - "+ startDateFinal[0]+" " +startDateFinal[1]+" to "+endDateFinal[0]+" " +endDateFinal[1]+" hrs .");
			candidateBean.setDisplayAppFlag("true");		
			return "CloseWindow";
		}
		return result;
	}

	private static LocalDate substractDate(LocalDate ldAgeAsOn, long years, long months, long days) {// LocalDate.minusDays
		LocalDate newYear = ldAgeAsOn.minusYears(years);
		LocalDate newMonth = newYear.minusMonths(months);
		LocalDate newDays = newMonth.minusDays(days);
		return newDays;
	}

	public String getAgeMatrixDetails() { // to get Max age and Max date from DB
		Date minDate = null;
		String mindate = "";
		try {

			String isExServiceMen = "N";
			String governmentEmp = "N";

			String categoryVal = request.getParameter("categoryValDesc");
			isExServiceMen = request.getParameter("isExServicemen");
			governmentEmp = request.getParameter("governmentEmp");
			String dtOfEnlistment = request.getParameter("enlistmentDate");
			String dtOfDischarge = request.getParameter("dischargeDate");

			if (isExServiceMen != null && !isExServiceMen.equals("") && isExServiceMen.equals("6") && dtOfEnlistment != null && !dtOfEnlistment.equals("") && dtOfDischarge != null
					&& !dtOfDischarge.equals("") && !dtOfEnlistment.equalsIgnoreCase("NaN") && !dtOfEnlistment.equalsIgnoreCase("undefined")
					&& !dtOfDischarge.equalsIgnoreCase("NaN") && !dtOfDischarge.equalsIgnoreCase("undefined")) {
				isExServiceMen = "Y";
			} else {
				isExServiceMen = "N";
			}

			if (governmentEmp != null && !governmentEmp.equals("") && governmentEmp.equals("6")) {
				governmentEmp = "Y";
			} else {
				governmentEmp = "N";
			}

			minDate = ageMatrix(ageAsOn, categoryVal, isExServiceMen, governmentEmp, dtOfEnlistment, dtOfDischarge);
			DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			mindate = dateformat.format(minDate);

		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, mindate);
		return "writePlainText";
	}

	private Date ageMatrix(String ageAsOn, String category, String isESM, String isUPGovtEmp, String esmDateOfEnlistment, String esmDateOfDischarge) throws Exception {

		int maxRelaxation = 0;
		Date startdate = null;
		LocalDate actualAgeFmt = LocalDate.parse(ageAsOn, yyyy_MM_dd_formatter);

		if (isESM.equals("N")) {
			if (isUPGovtEmp.equals("N")) {
				if (Arrays.asList("1", "2").contains(category)) {
					maxRelaxation = 28;
				} else if (Arrays.asList("3", "5", "7").contains(category)) {
					maxRelaxation = 33;
				}
			} else {
				maxRelaxation = 33;
			}
			startdate = substractDate(actualAgeFmt, maxRelaxation, 0, 0, false);
		} else {
			if (isUPGovtEmp.equals("N")) {
				if (!isBlankString(esmDateOfEnlistment) && !isBlankString(esmDateOfDischarge) && !esmDateOfDischarge.equalsIgnoreCase("NaN")
						&& !esmDateOfDischarge.equalsIgnoreCase("undefined") && !esmDateOfEnlistment.equalsIgnoreCase("NaN")
						&& !esmDateOfEnlistment.equalsIgnoreCase("undefined")) {
					LocalDate esmDateOfEnlistmentFmt = LocalDate.parse(esmDateOfEnlistment, dd_MM_yyyy_formatter);
					LocalDate esmDateOfDischargeFmt = LocalDate.parse(esmDateOfDischarge, dd_MM_yyyy_formatter);
					Period esmServiceRendered = Period.between(esmDateOfEnlistmentFmt, esmDateOfDischargeFmt);
					maxRelaxation = 31;
					if (Arrays.asList("1", "2").contains(category)) {
						startdate = substractDate(actualAgeFmt, esmServiceRendered.getYears() + maxRelaxation, esmServiceRendered.getMonths(), esmServiceRendered.getDays(), true);
					} else if (Arrays.asList("3", "5", "7").contains(category)) {
						int catRelax = 33;
						Date startdateAsPerCat = substractDate(actualAgeFmt, catRelax, 0, 0, false);
						Date startdateAsPerESM = substractDate(actualAgeFmt, esmServiceRendered.getYears() + maxRelaxation, esmServiceRendered.getMonths(),
								esmServiceRendered.getDays(), true);
						if (startdateAsPerCat.compareTo(startdateAsPerESM) < 0) {
							startdate = startdateAsPerCat;
						} else {
							startdate = startdateAsPerESM;
						}
					}
				}
			} else {
				int upGovtEmpRelax = 33;
				int esmRelax = 31;
				if (!isBlankString(esmDateOfEnlistment) && !isBlankString(esmDateOfDischarge) && !esmDateOfDischarge.equalsIgnoreCase("NaN")
						&& !esmDateOfDischarge.equalsIgnoreCase("undefined") && !esmDateOfEnlistment.equalsIgnoreCase("NaN")
						&& !esmDateOfEnlistment.equalsIgnoreCase("undefined")) {
					LocalDate esmDateOfEnlistmentFmt = LocalDate.parse(esmDateOfEnlistment, dd_MM_yyyy_formatter);
					LocalDate esmDateOfDischargeFmt = LocalDate.parse(esmDateOfDischarge, dd_MM_yyyy_formatter);
					Period esmServiceRendered = Period.between(esmDateOfEnlistmentFmt, esmDateOfDischargeFmt);
					Date startdateAsPerUPGovtEmpRelax = substractDate(actualAgeFmt, upGovtEmpRelax, 0, 0, false);
					Date startdateAsPerEsmRelax = substractDate(actualAgeFmt, esmServiceRendered.getYears() + esmRelax, esmServiceRendered.getMonths(),
							esmServiceRendered.getDays(), true);
					if (startdateAsPerEsmRelax.compareTo(startdateAsPerUPGovtEmpRelax) < 0) {
						startdate = startdateAsPerEsmRelax;
					} else {
						startdate = startdateAsPerUPGovtEmpRelax;
					}
				}
			}
		}
		// General/SC + Gov => 22 + 5
		// General/SC + Esm => 22 + 3 + rendered
		return startdate;
	}

	private Date substractDate(LocalDate ldAgeAsOn, long years, long months, long days, boolean limitCheck) {
		LocalDate newYear = ldAgeAsOn.minusYears(years);
		LocalDate newMonth = newYear.minusMonths(months);
		LocalDate newDays = newMonth.minusDays(days);
		// newDays = newDays.plusDays(1);
		if (limitCheck) {
			LocalDate maxStartDate = LocalDate.of(1962, Month.JULY, 2);
			if (maxStartDate.isAfter(newDays)) {
				newDays = maxStartDate;
			}
		}

		java.time.Instant instant = newDays.atTime(LocalTime.MIDNIGHT).atZone(ZoneId.systemDefault()).toInstant();
		Date startdateFmt = Date.from(instant);
		return startdateFmt;
	}

	private static boolean isBlankString(String string) {
		return string == null || string.trim().isEmpty();
	}

	// for candidate Photo
	public String insertCandidateImage() throws Exception {
		int candidateImage = 0;
		String path = "";
		String result = "showUploadPhotograph";
		String message = "";
		String errorMessage = null;
		int updateupload;
		StringBuffer errorMessageBuffer = new StringBuffer();
		String errors = "";
		
		photoBeanValidator.setCandidateBean(candidateBean);
		photoBeanValidator.setType("form");
		photoBeanValidator.setLoggedInUser(loggedInUser);
		photoBeanValidator.setUploadPhotoSignInS3sdkV2(uploadPhotoSignInS3sdkV2);
		errorMessage = photoBeanValidator.validateUploadedPhoto();
		token = TokenHelper.getToken();
		if (errorMessage != null && !errorMessage.equals("")) {			
			errorMessageBuffer.append(errorMessage);	
		}
		if (errorMessage == null || errorMessage.equals("")) {
			// saving candidate image in folder
			CandidateSignatureServiceConstants serviceresponse = candidatePhotoSignatureService.saveCandidatePhoto(loggedInUser, candidateBean, requestAttributes, request);

			switch (serviceresponse) {
			case FILE_UPLOAD_FAILE:
				addActionMessage("ER2: File upload failed.Kindly re-upload the file.");
				commonService.insertCandidateAuditrail(loggedInUser, "Upload Photo Failed",
						"Candidate with User Id " + loggedInUser.getUsername() + " Image name: " + candidateBean.getAttachmentPhotoFileName() + " Upload Photo Failed.");
				result = "showUploadPhotograph";
				break;
			case SUCCESS:
				updateupload = candidatePhotoSignatureService.updateCandidatePhotoStatus(candidateBean, loggedInUser, "Photo");
				String action = candidateBean.getAttachmentPhotoFileName();
				
				commonService.insertCandidateAuditrail(loggedInUser, "Photo Page - Save & Continue",
						"Candidate with User Id " + loggedInUser.getUsername() + " : " + action + " uploaded ");

				result = showUploadPhotograph1();
				break;
			default:
				addActionMessage("Error uploading image, please contact Administrator.");
				commonService.insertCandidateAuditrail(loggedInUser, "Upload Photo Failed with Error",
						"Candidate with User Id " + loggedInUser.getUsername() + " Image name: " + candidateBean.getAttachmentPhotoFileName() + " Upload Photo Failed with Error.");
				result = "showUploadPhotograph";
			}
			return result;
		} else {
			if (request.getAttribute("menuKey") != null) {
				String mandatory = ConfigurationConstants.getInstance().getMenuKeyMandatory(request.getAttribute("menuKey").toString());
				if (mandatory != null && mandatory.equals(GenericConstants.MANDATORY)) {
					candidateBean.setImageMandatory(true);
				}
			}
			if (errorMessageBuffer.length() > 0) {
				errors = errorMessageBuffer.toString();
				commonService.insertCandidateAuditrail(loggedInUser, "Photo Page - Exception or Error", "Candidate with User Id " + loggedInUser.getUsername() + ": " + errors);
			}
			
			message = "(message)" + errorMessage + "(/message)";
			Users users = candidateService.getBasicCandidateInfo(loggedInUser);
			candidateBean.getPersonalDetailsBean().setCandidateFirstName(users.getFirstName());
			candidateBean.getPersonalDetailsBean().setDateOfBirth(users.getDate_Of_Birth());
			//candidateBean.setGenderValDesc(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(loggedInUser.getGender())));
			candidateBean.setGenderValDesc(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(users.getGender())));
			addActionMessage(errorMessage);
			candidateBean.setImageUploaded(true);
			result = "showUploadPhotograph";			
		}		
		displayMenus();
		return result;
	}

	public String showUploadPhotograph() throws Exception {
		String result = "";
		Map<Integer, List<Long>> dateWindowMap = ConfigurationConstants.getInstance().getDateWindowMap();
		List<Long> dateList = dateWindowMap.get(1);
		Long startDate = dateList.get(0);
		Long endDate = dateList.get(1);
		long today = ZonedDateTime.now().toInstant().toEpochMilli();
		if ((today == startDate || today > startDate) && (today == endDate || today < endDate)) {
		CandidateSignatureServiceConstants serviceResponse = candidatePhotoSignatureService.getUploadedPhoto(loggedInUser, candidateBean, request);
		if (serviceResponse != null && serviceResponse.toString().equals("UPLOAD_PHOTOGRAPH")) 
			result = "showUploadPhotograph";
			commonService.insertCandidateAuditrail(loggedInUser, "Photo Page - Loaded", "Candidate User Id : " + loggedInUser.getUsername() + " ~ Photo Page Loaded.");
			return result;
	}else {
		// Used for displaying closure date in closewindow.jsp
		loggedInUser.setEditFlag(false);
		candidateBean.setEditBtnFlag(false);
		candidateBean.setRegStrtDate(CommonUtil.formatDate(new Date(startDate), GenericConstants.DATE_FORMAT_DEFAULT1));
		String newEndDate = CommonUtil.formatDate(new Date(endDate), GenericConstants.DATE_FORMAT_DEFAULT1);
		String[] endDateFinal = newEndDate.split(" ");
		candidateBean.setRegEndDate("at "+endDateFinal[1]+" hrs on " +endDateFinal[0]+".");
		candidateBean.setDisplayRegFlag("true");
		result = "CloseWindow";
		return result;
	}
	}
	public String showUploadPhotograph1() throws Exception {
		String result = "";
		CandidateSignatureServiceConstants serviceResponse = candidatePhotoSignatureService.getUploadedPhoto(loggedInUser, candidateBean, request);
		if (serviceResponse != null && serviceResponse.toString().equals("UPLOAD_PHOTOGRAPH")) 
			result = "showUploadPhotograph";
		//	commonService.insertCandidateAuditrail(loggedInUser, "Photo Page - Loaded", "Candidate with User Id " + loggedInUser.getUsername() + " : Photo Page Loaded.");
		return result;
	}

	public String checkPhotoExist() throws Exception {

		boolean existStatus = candidatePhotoSignatureService.checkIfImageUploaded(loggedInUser);
		if (existStatus)
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Success");
		else
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Photo upload failed. Please try again!");

		return "writePlainText";

	}

	public String checkSignExist() throws Exception {

		boolean existStatus = candidatePhotoSignatureService.checkIfSignatureUploaded(loggedInUser);
		if (existStatus)
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Success");
		else
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Sign upload failed. Please try again!");

		return "writePlainText";

	}
	// populating alternate state list

	public String getAlternateStateList() {
		Map<Integer, String> stateListMap = new LinkedHashMap<Integer, String>();
		String altCountryVal = null;
		String stateVal = "";
		try {
			altCountryVal = candidateBean.getAltCountryVal();

			if (altCountryVal != null && !altCountryVal.equals("") && !altCountryVal.equals("null")) {
				stateListMap = ConfigurationConstants.getInstance().getStateMap(Integer.parseInt(altCountryVal));
			}

			if (stateListMap != null) {
				for (Map.Entry<Integer, String> entry : stateListMap.entrySet()) {
					if (entry != null) {
						if (entry.getValue() != null && !entry.getValue().equals("") && entry.getKey() != null && !entry.getKey().equals("")) {
							stateVal = stateVal + (entry.getValue() + "#" + entry.getKey()) + ",";
						}
					}
				}
			}

			if (stateVal != null && !stateVal.equals("") && stateVal.endsWith(",")) {
				stateVal = stateVal.substring(0, stateVal.length() - 1);
			}

			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, stateVal);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		return "writePlainText";
	}

	public String getDistrictList() throws Exception {
		String district = candidateService.getDistrictList(candidateBean);
		request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, district);
		return "writePlainText";
	}

	public String getAltDistrictList() {
		Map<Integer, String> districtListMap = new LinkedHashMap<Integer, String>();
		Integer countryVal = null;
		String stateId = null;
		String districtVal = "";
		List<CommonBean> stateList = new ArrayList<CommonBean>();
		try {
			String stateVal = request.getParameter("stateVal");
			stateId = candidateBean.getStateVal();
			if (stateVal != null && !stateVal.equals("")) {
				stateId = stateVal;
			} else {
				stateId = "0";
			}
			if (stateId != null && !stateId.equals("") && !stateId.equals("null")) {
				districtListMap = ConfigurationConstants.getInstance().getDistrictMap(Integer.parseInt(stateId));
			}
			districtListMap.remove(585);
			if (districtListMap != null) {
				for (Map.Entry<Integer, String> entry : districtListMap.entrySet()) {
					if (entry != null) {
						if (entry.getValue() != null && !entry.getValue().equals("") && entry.getKey() != null && !entry.getKey().equals("")) {
							districtVal = districtVal + (entry.getValue() + "#" + entry.getKey()) + ",";
						}
					}
				}
			}

			if (districtVal != null && !districtVal.equals("") && districtVal.endsWith(",")) {
				districtVal = districtVal.substring(0, districtVal.length() - 1);
			}
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, districtVal);

		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		return "writePlainText";
	}

	public String getAlternateDistrictList() throws Exception {

		String alternateDistrict = candidateService.getAlternateDistrictList(candidateBean);
		request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, alternateDistrict);

		return "writePlainText";
	}

	public String updateCandidateStageFinalSubmit() throws Exception {
		String result = REDIRECT_ACTION_URL;
		Users users = (Users) sessionAttributes.get(SESSION_USER);
		String flagValue = request.getParameter("isDataFound");
		int stageUpdate = 0;
		String nextMenuKey = null;
		String actionUrl;
		Users user1= candidateService.getBasicCandidateInfo(loggedInUser);
		if(user1.getIsdisabled()!=null && ! user1.getIsdisabled().equals(""))
		{
			loggedInUser.setIsdisabled(user1.getIsdisabled());
			loggedInUser.setCategoryVal(user1.getCategory().toString());
			loggedInUser.setDate_Of_Birth(user1.getDate_Of_Birth());
		}
		String serverSideErrors = validateCandidateBean();
		StringBuilder severSideErrorsAppend = new StringBuilder();
	
		if (StringUtils.isNotBlank(serverSideErrors)) {
		severSideErrorsAppend.append(serverSideErrors);
			addActionMessage(severSideErrorsAppend.toString());
			result = "showFinalPersonalDetails";
		} else {
			commonService.insertCandidateAuditrail(loggedInUser, "Preview Page - Serverside validation",
					"Candidate User Id : " + loggedInUser.getUsername() + " ~ Status : No issue found");
			try {
				token = TokenHelper.getToken();
				String currentMenuKey = request.getParameter("menuKey");
				String mandatory = ConfigurationConstants.getInstance().getMenuKeyMandatory(currentMenuKey);
				nextMenuKey = ConfigurationConstants.getInstance().getNextStageForMenuKey(currentMenuKey);

				if (currentMenuKey.equals(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.REGISTER_CONFIRMATION))) {
					int updateStage = commonService.updateCandidateStatus(loggedInUser,
							ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_CONFIRMATION));
					if (updateStage > 0) {
						users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_CONFIRMATION));
					}
				}

				if (currentMenuKey.equals(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.REGISTER_SUBMITTED))) {
					int update = 0;
					int updateValidateStatus = 0;					
					int updateStage = commonService.updateCandidateStatus(loggedInUser, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED));
					String validateStatus = candidateService.getValidateStatus(loggedInUser.getUserId());

					if (validateStatus != null && !validateStatus.equals("") && validateStatus.equals(GenericConstants.REJECT_VAL)) {
						updateValidateStatus = candidateService.updateValidateStatus(loggedInUser);
					}

					if (updateStage > 0) {

						// Added For AppNo Generation Logic
						String viewDataVal = "";
						viewDataVal = viewPrintFinalDetails(); // Create JSONData from
						if (viewDataVal != null && !viewDataVal.equals("") && viewDataVal.equals("showViewPrintDetails")) {
							//createJson(); // For added JSONData into Table
						}
						users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED));

						loggedInUser.setRemoteIp(request.getRemoteAddr());
//						String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED) + "";
//						Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.REGISTRATION_SUBMITED);
//						Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.REGISTRATION_SUBMITED);

						Integer disciplineId = 0;
						if (loggedInUser.getDisciplineID() != null) {
							disciplineId = Integer.parseInt(loggedInUser.getDisciplineID());
						}
					}
				}
				loggedInUser.setRemoteIp(request.getRemoteAddr());

				if ((mandatory != null && mandatory.equals("1"))) {
					if ((flagValue == null && flagValue == "") || (null != flagValue && flagValue.equals("false"))) {
						request.setAttribute("dataNotFound", "This is Mandatory Stage");
						actionUrl = ConfigurationConstants.getInstance().getActionUrl(currentMenuKey);
					} else {
						if (Double.valueOf(currentMenuKey) > Double.valueOf(loggedInUser.getStage())) {
							stageUpdate = commonService.updateCandidateStage(loggedInUser, currentMenuKey);
						}
						actionUrl = ConfigurationConstants.getInstance().getActionUrl(nextMenuKey);
					}
				} else {
					if (Double.valueOf(currentMenuKey) > Double.valueOf(loggedInUser.getStage())) {
						stageUpdate = commonService.updateCandidateStage(loggedInUser, currentMenuKey);
					}
					
					actionUrl = ConfigurationConstants.getInstance().getActionUrl(nextMenuKey);
				}

				if (stageUpdate > 0) {
					users.setStage(currentMenuKey);
				}
				sessionAttributes.put(SESSION_USER, users);
				request.setAttribute(DESTINATION_URL, actionUrl);

				
				commonService.insertCandidateAuditrail(loggedInUser, "Preview Page - Serverside validation",
						"Candidate User Id : " + loggedInUser.getUsername() + " ~ Status : Data saved");
				//Form submit pdf download
				String flag="submit";
				request.setAttribute(DESTINATION_URL, "CandidateAction_getApplicationPDF.action?flag="+flag);
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
		}
		return result;
	}

/*	public String previewValidation() {
		String errors = "";
		String error1 = "";
		try {

			PersonalBeanValidator pbv = new PersonalBeanValidator(candidateBean, "database", loggedInUser);
			errors = errors.concat(pbv.validatePersonalDetails());

			EducationBeanValidator ebv = new EducationBeanValidator(candidateBean, "database", loggedInUser);
			errors = errors.concat(ebv.validateEducationalDetails());

			PhotoBeanValidator phbv = new PhotoBeanValidator(candidateBean, "database", loggedInUser);
			errors = errors.concat(phbv.validateUploadedPhoto());

			SignatureBeanValidator sbv = new SignatureBeanValidator(candidateBean, "database", loggedInUser);
			errors = errors.concat(sbv.validateUploadedSignature());

			DocumentBeanValidator dbv = new DocumentBeanValidator(candidateBean, "database", loggedInUser);
			errors = errors.concat(dbv.validateUploadedDocuments());

			// errors = validateCandidateBean();
			if (errors.length() > 0) {
				// errors = ValidatorUtil.validationMessageFormatter(errorsList);
				error1 = errors.toString();
				commonService.insertCandidateAuditrail(loggedInUser, "Preview Page - Exception or Error", "Candidate with User Id " + loggedInUser.getUsername() + " : " + error1);
			}

			if (errors.length() == 0) {
				commonService.insertCandidateAuditrail(loggedInUser, "Preview Page - Serverside validation",
						"Candidate with User Id " + loggedInUser.getUsername() + " : No issue found in server side validation");
			}
		} catch (Exception e) {
			errors = "Error while submitting page, Please contact System administrator.";
			logger.fatal(e, e);
		}

		return errors;
	}*/

	public String paymentValidation() {
		String serverSideErrors = "";
		String actionUrl = null;
		String result = REDIRECT_ACTION_URL;
		try {
			serverSideErrors = validateCandidateBean();
			if (loggedInUser != null) {
				if (serverSideErrors.length() == 0) {
					commonService.insertCandidateAuditrail(loggedInUser, "Payment Page - Serverside Validation",
							"Candidate with User Id " + loggedInUser.getUsername()
									+ " : No issue found in server side validation");
				}
			}
		} catch (Exception e) {
			serverSideErrors = "Error while submitting page, Please contact System administrator.";
			logger.fatal(e,e);
		}
		StringBuilder severSideErrorsAppend = new StringBuilder();
		if (candidateBean.getStatusID() != null
				&& (candidateBean.getStatusID().equals(5) || candidateBean.getStatusID().equals(11))
				&& StringUtils.isNotBlank(serverSideErrors)) {

			logger.info("Errors found in payment validation " + serverSideErrors + " User ID : "
					+ loggedInUser.getUsername());
			try {
				commonService.insertCandidateAuditrail(loggedInUser, " Errors found in payment validation",
						"Candidate with User ID " + loggedInUser.getUsername() + " *** " + serverSideErrors);
				logger.info("Errors in payment Validation called or User ID: " + loggedInUser.getUsername()
						+ ". Errors: " + serverSideErrors);
				severSideErrorsAppend.append("<li>There is some data/document missing. Kindly contact helpldesk on 022 62507743 (Monday to Saturday 9 AM to 6 PM"
						+ " - Except Public Holidays) or mail us on  mrbpcw2025@onlineregistrationform.org</li>");
				addActionMessage(severSideErrorsAppend.toString());
				sessionAttributes.put(GLOBAL_PLAIN_TEXT_MESSAGE, severSideErrorsAppend.toString());
				actionUrl = "PaymentOnlineAction_showPaymentScreen.action";

			} catch (Exception e) {
				logger.fatal(e, e);
			}
		} else {
			logger.info("No errors in payment Validation called or User ID: " + loggedInUser.getUsername());
			try {
				commonService.insertCandidateAuditrail(loggedInUser, "No errors found in payment validation",
						"Candidate with User ID " + loggedInUser.getUsername());
			} catch (Exception e) {
				logger.fatal(e, e);
			}
			actionUrl = "PaymentOnlineAction_connectToAIPay.action";
			sessionAttributes.put(GLOBAL_PLAIN_TEXT_MESSAGE, "");
		}
		request.setAttribute(DESTINATION_URL, actionUrl);
		return result;
	}
	

	public String updateCandidateStage() throws Exception {
		String result = REDIRECT_ACTION_URL;
		Users users = (Users) sessionAttributes.get(SESSION_USER);
		String flagValue = request.getParameter("isDataFound");
		int stageUpdate = 0;
		String nextMenuKey = null;
		String actionUrl;
		SessionMap session = (SessionMap) ActionContext.getContext().getSession();
		try {
			
			String stagefromDb = commonService.getCandidateStage(users.getUsername());
			if(stagefromDb!="" && stagefromDb!=null)
			{
				int currentStageInDb = Integer.parseInt(stagefromDb);
				if(currentStageInDb==5)
				{
					session.invalidate();
					return "notloggedin";
				}
			}
			
			token = TokenHelper.getToken();

			String currentMenuKey = request.getParameter("menuKey");
			nextMenuKey = ConfigurationConstants.getInstance().getNextStageForMenuKey(currentMenuKey);
			if(currentMenuKey.equals("2.70")) {
				commonService.insertCandidateAuditrail(loggedInUser, "Document upload Page  - Save & Continue",
						"Candidate with User Id " + loggedInUser.getUsername() + " : All docments uploaded successfully");
			}
			actionUrl = ConfigurationConstants.getInstance().getActionUrl(nextMenuKey);
			sessionAttributes.put(SESSION_USER, users);
			request.setAttribute(DESTINATION_URL, actionUrl);
			String mandatory = ConfigurationConstants.getInstance().getMenuKeyMandatory(currentMenuKey);

			if (currentMenuKey.equals(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.REGISTER_CONFIRMATION))) {
				if (candidateBean.getStatusID() < 10) {
					int updateStage = commonService.updateCandidateStatus(loggedInUser,
							ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_CONFIRMATION));

					if (updateStage > 0) {
						users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_CONFIRMATION));

					}
				}
			}
			if (currentMenuKey.equals(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.REGISTER_SUBMITTED))) {
				int update = 0;
				int updateValidateStatus = 0;
				int updateStage = commonService.updateCandidateStatus(loggedInUser, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED));
				String validateStatus = candidateService.getValidateStatus(loggedInUser.getUserId());

				if (validateStatus != null && !validateStatus.equals("") && validateStatus.equals(GenericConstants.REJECT_VAL)) {
					updateValidateStatus = candidateService.updateValidateStatus(loggedInUser);
				}
				if (updateStage > 0) {
					users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED));

					ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger().info(loggedInUser.getUsername() + ", " + request.getRemoteAddr()
							+ ", Submitted Application Form , " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
					loggedInUser.setRemoteIp(request.getRemoteAddr());
					String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED) + "";
					Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.REGISTRATION_SUBMITED);
					Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.REGISTRATION_SUBMITED);

					Integer disciplineId = 0;
					if (loggedInUser.getDisciplineID() != null) {
						disciplineId = Integer.parseInt(loggedInUser.getDisciplineID());
					}

					/*
					 * EmailSMSUtil.insertEmailNSMSForActivitynTest(
					 * ConfigurationConstants.getInstance().getActivtyKeyForName
					 * (GenericConstants.REGISTRATION_SUBMITED), disciplineId, users.getUsername(),
					 * users.getUsername(), staus, users, emailReq, smsReq);
					 */
				}
			}
			loggedInUser.setRemoteIp(request.getRemoteAddr());
			
			if ((mandatory != null && mandatory.equals("1"))) {
				if ((flagValue == null && flagValue == "") || (null != flagValue && flagValue.equals("false"))) {
					request.setAttribute("dataNotFound", "This is Mandatory Stage");
					actionUrl = ConfigurationConstants.getInstance().getActionUrl(currentMenuKey);
				} else {
					if (Double.valueOf(currentMenuKey) > Double.valueOf(loggedInUser.getStage())) {
						stageUpdate = commonService.updateCandidateStage(loggedInUser, currentMenuKey);
					}
					actionUrl = ConfigurationConstants.getInstance().getActionUrl(nextMenuKey);
				}
			} else {
				if (Double.valueOf(currentMenuKey) > Double.valueOf(loggedInUser.getStage())) {
					stageUpdate = commonService.updateCandidateStage(loggedInUser, currentMenuKey);
				}
				actionUrl = ConfigurationConstants.getInstance().getActionUrl(nextMenuKey);
			}

			if (stageUpdate > 0) {
				users.setStage(currentMenuKey);
			}
			sessionAttributes.put(SESSION_USER, users);
			request.setAttribute(DESTINATION_URL, actionUrl);

		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return result;
	}

	public String showFinalPersonalDetails() {
		String result = "showFinalPersonalDetails";
		try {
			candidateService.populateCandidateDetailsForPrintPage(loggedInUser, candidateBean);
			candidateService.populateEducationalDetailsForFinalPrintPage(candidateBean, loggedInUser);
			
			Map<Integer, List<Long>> dateWindowMap = ConfigurationConstants.getInstance().getDateWindowMap();
			List<Long> notificationDate = dateWindowMap.get(8);
			Long notificationEndDate = notificationDate.get(1);
			candidateBean.setNotificationEndDate(CommonUtil.formatDate(new Date(notificationEndDate), GenericConstants.DATE_FORMAT_DEFAULT));
			
			candidateBean.setUploadImageStatus(ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.UPLOAD_SIGNATURE));
			candidateBean.setAgeAsOn(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AGE_AS_ON));
			request.setAttribute("submitStage", ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED));
			request.setAttribute("candidateApproved", ConfigurationConstants.getInstance().getStatusKey(GenericConstants.CANDIDATE_APPROVED));
			commonService.insertCandidateAuditrail(loggedInUser, "Preview Page - Loaded", "Candidate User Id : " + loggedInUser.getUsername() + " ~ Preview Page Loaded.");
			displayMenus();
		} catch (Exception ex) {
			logger.fatal(ex, ex);
		}
		return result;
	}
	
	public String finalFormSubmition() {
		String result = "finalPersonalDetails";
		try {
			String currentMenuKey = request.getParameter("menuKey");
			
			candidateService.populateCandidateDetailsForPrintPage(loggedInUser, candidateBean);
			candidateService.populateEducationalDetailsForFinalPrintPage(candidateBean, loggedInUser);
			//candidateService.populateAddCoivdDutyCertiForPrintPage(loggedInUser, candidateBean);
			//candidateBean.setStudiedMath(candidateBean.getEducationDtlsList().get(0).getStudiedMath());
			//candidateService.populateAddEducationalDetailsForFinalPrintPage(loggedInUser, candidateBean);
			//candidateService.populateWorkExpDetailsForPrintPage(loggedInUser, candidateBean);
			//candidateService.populateAdditionalWorkExpDetailsForPrintPage(loggedInUser, candidateBean);
			//candidateService.populateAdditionalDetailsForPrintPage(loggedInUser, candidateBean);
			candidateBean.setUploadImageStatus(ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.UPLOAD_SIGNATURE));
			request.setAttribute("submitStage", ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED));
			request.setAttribute("candidateApproved", ConfigurationConstants.getInstance().getStatusKey(GenericConstants.CANDIDATE_APPROVED));
			displayMenus();
			// viewPrintFinalDetails();

		} catch (Exception ex) {
			logger.fatal(ex,ex);
		}
		return result;
	}

	private String getImageCastForS3(BufferedImage originalImage) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		StringBuilder imageStringBuilder = new StringBuilder();
		try {
			ImageIO.write(originalImage, "png", baos);
			byte[] imageInByte = baos.toByteArray();
			imageStringBuilder.append("data:image/png;base64,");
			imageStringBuilder.append(Base64.getEncoder().encodeToString(imageInByte));
		} catch (IOException ex) {
			logger.info(ex.getMessage());
		}
		return imageStringBuilder.toString();
	}

	public String viewPrintFinalDetails() {
		String result = "showViewPrintDetails";
		try {
			if (null != loggedInUser.getCandidateStatusId() && !loggedInUser.getCandidateStatusId().equals("") && loggedInUser.getCandidateStatusId().equals(10)) {
				candidateBean.setRegistrationId(commonService.getRegistrationId(loggedInUser));
			}
			Map<Integer, List<Long>> dateWindowMap = ConfigurationConstants.getInstance().getDateWindowMap();
			List<Long> notificationDate = dateWindowMap.get(8);
			Long notificationEndDate = notificationDate.get(1);
			
			candidateBean.setNotificationEndDate(CommonUtil.formatDate(new Date(notificationEndDate), GenericConstants.DATE_FORMAT_DEFAULT));
			candidateService.populateCandidateDetailsForPrintPage(loggedInUser, candidateBean);
			candidateBean.setUserId(loggedInUser.getUsername());
			candidateService.populateEducationalDetailsForFinalPrintPage(candidateBean, loggedInUser);
			candidateService.populateCovidDutyCertiForPrintPage(loggedInUser, candidateBean);
			candidateService.populateAddCovidDutyCertiForPrintPage(loggedInUser, candidateBean);
			commonService.insertCandidateAuditrail(loggedInUser, "View Page - Loaded", "Candidate with User Id " + loggedInUser.getUsername() + " has come to the View page.");
			//candidateService.populateAddCoivdDutyCertiForPrintPage(loggedInUser, candidateBean);
			/*
			 * candidateService.populateAddEducationalDetailsForFinalPrintPage(loggedInUser, candidateBean); candidateService.populateWorkExpDetailsForPrintPage(loggedInUser,
			 * candidateBean); candidateService.populateAdditionalWorkExpDetailsForPrintPage(loggedInUser, candidateBean);
			 * candidateService.populateAdditionalDetailsForPrintPage(loggedInUser, candidateBean);
			 */
			candidateBean.setAgeAsOn(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AGE_AS_ON));
			
			candidateBean.setAppPrint("");
			/*if (candidateBean.getEducationDtlsList().size() > 0) {
				candidateBean.setStudiedMath(candidateBean.getEducationDtlsList().get(0).getStudiedMath());
			}*/
			request.setAttribute("submitStage", ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED));
			request.setAttribute("candidateApproved", ConfigurationConstants.getInstance().getStatusKey(GenericConstants.CANDIDATE_APPROVED));

			String DocumentPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
			String strSaveInS3 = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SAVE_IN_S3);

			if (strSaveInS3 != null && !Strings.isBlank(strSaveInS3) && "true".equals(strSaveInS3.toLowerCase())) {
				UploadPhotoSignInS3sdkV2 uploadPhotoSignInS3sdkV2 = new UploadPhotoSignInS3sdkV2();

				BufferedImage bufferedImageLogo = uploadPhotoSignInS3sdkV2.getLogoFromBufferedImageFromS3("NCFE-logo.png");
				if (bufferedImageLogo != null) {
					candidateBean.setLogoCast(getImageCastForS3(bufferedImageLogo));
				}

				BufferedImage bufferedImagePhoto = uploadPhotoSignInS3sdkV2.getPhotoSignatureBufferedImageFromS3(loggedInUser, "_photo.jpg");
				if (bufferedImagePhoto != null) {
					candidateBean.setImageCast(getImageCastForS3(bufferedImagePhoto));
				}

				BufferedImage bufferedImageSign = uploadPhotoSignInS3sdkV2.getPhotoSignatureBufferedImageFromS3(loggedInUser, "_sign.jpg");
				if (bufferedImageSign != null) {
					candidateBean.setSignatureCast(getImageCastForS3(bufferedImageSign));
				}
			} else {
				File photoPath = new File(DocumentPath + File.separator + loggedInUser.getUsername() + File.separator + loggedInUser.getUsername() + "_photo.jpg");
				if (photoPath != null && !photoPath.equals("")) {
					candidateBean.setImageCast(getImageCastFromLocal(photoPath));
				}
				File signPath = new File(DocumentPath + File.separator + loggedInUser.getUsername() + File.separator + loggedInUser.getUsername() + "_sign.jpg");
				if (signPath != null && !signPath.equals("")) {
					candidateBean.setSignatureCast(getImageCastFromLocal(signPath));
				}
				File logoPath = new File(DocumentPath + File.separator + "logo" + File.separator + "logo.png");
				if (logoPath != null && !logoPath.equals("")) {
					candidateBean.setLogoCast(getImageCastFromLocal(logoPath));
				}
			}

			DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmssZ");
			Date date = new Date();
			candidateBean.setJsonDate(dateFormat.format(date));

			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			String jsonInString = mapper.writeValueAsString(candidateBean);

			// Convert object to JSON string and pretty print
			jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(candidateBean);
			String finalString = jsonInString.replace("\'", "\\'");
			candidateBean.setJasperString(finalString.replace("\"", "'"));
			HttpSession session = ServletActionContext.getRequest().getSession(false);
			String jsonString = finalString.replace("\"", "'");
			session.setAttribute("JSONSTRING", jsonString);
			request.setAttribute("JSONSTRING", jsonString);
		} catch (Exception ex) {
			result = "";
			logger.error(ex,ex);
		}
		return result;
	}
	
	private String getImageCastFromLocal(File fnew) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		StringBuilder imageStringBuilder = new StringBuilder();
		BufferedImage originalImage = null;
		try {
			originalImage = ImageIO.read(fnew);
			ImageIO.write(originalImage, "png", baos);
			byte[] imageInByte = baos.toByteArray();
			imageStringBuilder.append("data:image/png;base64,");
			imageStringBuilder.append(Base64.getEncoder().encodeToString(imageInByte));
		} catch (IOException ex) {
			logger.info(ex.getMessage());
		}
		return imageStringBuilder.toString();
	
	}

	public String saveCandidateDetails() throws LogicError, Exception {
		AddressBean addressBean = new AddressBean();
		PersonalDetailsBean personalDetailsBean = new PersonalDetailsBean();
		String result = null;
		StringBuffer errorMessageBuffer = new StringBuffer();
		String errors = "";

		personalBeanValidator.setCandidateBean(candidateBean);
		String serverSideErrors = personalBeanValidator.validatePersonalDetails("form");
		errorMessageBuffer.append(serverSideErrors);

		StringBuilder severSideErrorsAppend = new StringBuilder();
		if (StringUtils.isNotBlank(serverSideErrors)) {
			severSideErrorsAppend.append(serverSideErrors);
			addActionMessage(severSideErrorsAppend.toString());
			candidateBean.setStatusID(candidateBean.getCandidateStatusId());

			prepopulateForm();

			result = "applicationForm";
		} else {
			// Start: Save Candidate Data IN DB
			loggedInUser.setStatevalue(candidateBean.getStateVal());
			addressBean = candidateBean.getAddressBean();

			if (candidateBean.isAddressChkBox()) {
				candidateBean.copyPermenantToComm();
			}

			personalDetailsBean = candidateBean.getPersonalDetailsBean();

			String permenantAddress = addressBean.getAddressFiled1();

			String communicationAddress = addressBean.getAlternateAddressFiled1();
			addressBean.setAddress(permenantAddress);
			addressBean.setAlternateAddress(communicationAddress);
			
			try {
				ErrorCodeEnum serviceresult = candidatePersonalDetailsService.saveCandidateDetails(candidateBean, addressBean, personalDetailsBean, loggedInUser, 0,
						sessionAttributes, request);

				switch (serviceresult) {
				case UPDATE_CANDIDATE_STAGE:
					result = updateCandidateStage();
					break;
				case ERROR:
					result = ERROR;
					logger.error("Error ocurred while saving the candidate details.");
					break;
				default:
					throw new LogicError("Error ocurred while saving the candidate details.");
				}
			} catch (Exception e) {
				logger.info("Exception occured while saving Personal details transaction rolled back for User ID: " + loggedInUser.getUsername() + e.getMessage());
				addActionMessage("Error Occured while saving Personal Details. Please try again.");
				prepopulateForm();
				result = "applicationForm";
			}
		}

		if (request.getAttribute("menuKey") != null) {
			String mandatory = ConfigurationConstants.getInstance().getMenuKeyMandatory(request.getAttribute("menuKey").toString());
			if (mandatory != null && mandatory.equals(GenericConstants.MANDATORY)) {
				candidateBean.setCandiateDetailsMandatory(true);
			}
		}
		if (errorMessageBuffer.length() > 0) {
			errors = errorMessageBuffer.toString().replaceAll("</li> <br/><li>", ",");
			commonService.insertCandidateAuditrail(loggedInUser, "Personal Page - Exception or Error", "Candidate with User Id " + loggedInUser.getUsername() + ": " + errors);
		}
		displayMenus();
		return result;
	}

	public String home() {
		try {

			Users loggedInUser = (Users) sessionAttributes.get(SESSION_USER);

			// candidateBean.setCandidateFirstLogin(loggedInUser.getFirstLogin());
			// start of value to be fetched for checkbox and menu enabling and
			// disabling
			String declarationHid = loggedInUser.getDeclaration();
			commonService.insertCandidateAuditrail(loggedInUser, "Home Page-Loaded",
					"Candidate User Id : " + loggedInUser.getUsername() + " ~ Home Page : Loaded");
			// Code for Scribe data capture
			candidateBean.setScribe_flag(loggedInUser.getScribe_flag());
			candidateBean.setScribe_flagList(ConfigurationConstants.getInstance().getParameterMapForDomainName("Yes_No_Map"));

			if ("true".equals(declarationHid)) {
				candidateBean.setDeclarationhid("true");
				commonService.insertCandidateAuditrail(loggedInUser, "Home Page-Continue",
						"Candidate User Id : " + loggedInUser.getUsername() + "~ Checked : Declaration");
			} else {
				candidateBean.setDeclarationhid("false");
			}
			String isInstLinkReqd = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.INSTRUCTION_FILED);

			if (isInstLinkReqd != null && !isInstLinkReqd.equals("") && isInstLinkReqd.equals(GenericConstants.INSTRUCTION_REQD)) {
				candidateBean.setInstrReqd(true);
			} else {
				candidateBean.setInstrReqd(false);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		return "home";
	}

	public String showInstructionPage() {

		String result = null;
		try {
			Map<Integer, List<Long>> dateWindowMap = ConfigurationConstants.getInstance().getDateWindowMap();
			List<Long> dateList = dateWindowMap.get(1);
			Long startDate = dateList.get(0);
			Long endDate = dateList.get(1);

			long today = CommonUtil.getCurrentDateInMillis();

			if ((today == startDate || today > startDate) && (today == endDate || today < endDate)) {
				result = "instructionPage";
			} else {
				candidateBean.setRegStrtDate(CommonUtil.formatDate(new Date(startDate), GenericConstants.DATE_FORMAT_DEFAULT));
				candidateBean.setRegEndDate(CommonUtil.formatDate(new Date(endDate), GenericConstants.DATE_FORMAT_DEFAULT));
				candidateBean.setDisplayRegFlag("true");

				result = "CloseWindow";
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		return result;
	}

	public String registrationClick() {
		String result = "";
		try {

			if (null != loggedInUser) {
				if (Double.valueOf(loggedInUser.getStage()).equals(Double.valueOf(ConfigurationConstants.getInstance().getMenuKeyByMenuDesc(GenericConstants.SUBMIT_FORM)))) {
					String currentMenuKey = null;
					if (Double.valueOf(loggedInUser.getCandidateStatusId())
							.equals(Double.valueOf(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_REJECTED) + ""))) {
						currentMenuKey = "2.10";
						String actionUrl = ConfigurationConstants.getInstance().getActionUrl(currentMenuKey);
						sessionAttributes.put(SESSION_USER, loggedInUser);
						request.setAttribute(DESTINATION_URL, actionUrl);
					} else {
						if (loggedInUser.getStage().equals("0.0")) {
							currentMenuKey = request.getAttribute("menuKey").toString();
						} else {
							String currentStage = loggedInUser.getStage();
							if (currentStage.contains(".")) {
								currentMenuKey = currentStage.split("\\.")[0];
							} else {
								currentMenuKey = String.valueOf(loggedInUser.getStage());
							}
						}
						String actionUrl = ConfigurationConstants.getInstance().getActionUrl(currentMenuKey);
						if (actionUrl == null) {
							actionUrl = "CandidateAction_getCandidateDetails.action";
						}
						sessionAttributes.put(SESSION_USER, loggedInUser);
						request.setAttribute(DESTINATION_URL, actionUrl);
					}
				} else if (Double.valueOf(loggedInUser.getCandidateStatusId())
						.equals(Double.valueOf(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_CONFIRMATION) + ""))) {
					String currentMenuKey = "2.10";
					String actionUrl = ConfigurationConstants.getInstance().getActionUrl(currentMenuKey);
					sessionAttributes.put(SESSION_USER, loggedInUser);
					request.setAttribute(DESTINATION_URL, actionUrl);
				} // This line of code will check that user still did not submit
					// the form so it will redirect the next stage for
					// completion
				else if (Double.valueOf(loggedInUser.getStage()) < Double.valueOf((ConfigurationConstants.getInstance().getMenuKeyByMenuDesc(GenericConstants.SUBMIT_FORM)))) {
					String currentMenuKey = null;
					if (loggedInUser.getStage().equals("0.0")) {
						currentMenuKey = request.getAttribute("menuKey").toString();
					} else {
						currentMenuKey = request.getAttribute("menuKey").toString();
					}
				String nextMenuKey = ConfigurationConstants.getInstance().getNextStageForMenuKey(currentMenuKey);
					//String nextMenuKey = "2.30";
					
					String actionUrl = ConfigurationConstants.getInstance().getActionUrl(nextMenuKey);
					sessionAttributes.put(SESSION_USER, loggedInUser);
					request.setAttribute(DESTINATION_URL, actionUrl);
				}

				else if (Double.valueOf(loggedInUser.getStage()) > Double.valueOf((ConfigurationConstants.getInstance().getMenuKeyByMenuDesc(GenericConstants.SUBMIT_FORM)))) {
					String currentMenuKey = null;
					if (loggedInUser.getStage().equals("0.0")) {
						currentMenuKey = request.getAttribute("menuKey").toString();
					} else {
						currentMenuKey = request.getAttribute("menuKey").toString();
					}
					String nextMenuKey = ConfigurationConstants.getInstance().getNextStageForMenuKey(currentMenuKey);
					String actionUrl = ConfigurationConstants.getInstance().getActionUrl(nextMenuKey);
					sessionAttributes.put(SESSION_USER, loggedInUser);
					request.setAttribute(DESTINATION_URL, actionUrl);
				}
			}
			result = REDIRECT_ACTION_URL;
		} catch (Exception ex) {
			logger.info(ex.getMessage());
		}

		return result;
	}

	// used for superadmin
	public String searchCandidate() {
		String result = "";
		try {
			Map<Integer, List<Long>> dateWindowMap = ConfigurationConstants.getInstance().getDateWindowMap();
			List<Long> dateList = dateWindowMap.get(2);
			Long startDate = dateList.get(0);
			Long endDate = dateList.get(1);

			long today = CommonUtil.getCurrentDateInMillis();

			if ((today == startDate || today > startDate) && (today == endDate || today < endDate)) {
				String userId = null;
				String fromDate = null;
				String toDate = null;
				if (candidateBean.getUserId() != null) {
					userId = candidateBean.getUserId().trim();
				}

				if (candidateBean.getFromDate() != null) {
					fromDate = candidateBean.getFromDate().trim();
				}

				if (candidateBean.getToDate() != null) {
					toDate = candidateBean.getToDate().trim();
				}
				Integer disciplineId = candidateBean.getDisciplineId();
				Integer candidateStatusId = candidateBean.getCandidateStatusId();

				int totalNumberOfRecord = candidateMgmtService.getRegisteredCandidateDetails(userId, fromDate, toDate, disciplineId, candidateStatusId,
						ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED));
				pagination.setProperties(totalNumberOfRecord);

				List<CandidateApproveRejectBean> listOfRegisteredCandidate = candidateMgmtService.getRegisteredCandidateDetails(userId, fromDate, toDate, disciplineId,
						candidateStatusId, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED), pagination);
				candidateBean.setListOfRegisteredCandidate(listOfRegisteredCandidate);
				candidateBean.setCandidateListSize(totalNumberOfRecord);

				pagination.setPage_records(listOfRegisteredCandidate.size());

				if (listOfRegisteredCandidate.size() == 0) {
					candidateBean.setAvailableRecordFlag("false");
				} else {
					candidateBean.setAvailableRecordFlag("true");
				}
				if (ConfigurationConstants.getInstance().getDisciplineMap() != null && !ConfigurationConstants.getInstance().getDisciplineMap().isEmpty()) {
					candidateBean.setDiscliplineList(ConfigurationConstants.getInstance().getDisciplineMap());
				}
				Map<Integer, String> candidateStatusListMap = new LinkedHashMap<Integer, String>();
				candidateStatusListMap.put(1, "Approved");
				candidateStatusListMap.put(0, "Rejected");
				candidateStatusListMap.put(2, "Not Approved");
				candidateBean.setCandidateStatusList(candidateStatusListMap);
				candidateBean.setFlag("true");
				if (candidateBean.getMenuKey() != null) {
					request.setAttribute("menuKey", candidateBean.getMenuKey());
				}
				displayMenus();
				return "eligibleForNodal";
			} else {
				candidateBean.setRegStrtDate(CommonUtil.formatDate(new Date(startDate), GenericConstants.DATE_FORMAT_DEFAULT));
				candidateBean.setRegEndDate(CommonUtil.formatDate(new Date(endDate), GenericConstants.DATE_FORMAT_DEFAULT));
				candidateBean.setDisplayRegFlag("true");

				return "CloseWindow";
			}
		} catch (Exception ex) {
			logger.info(ex.getMessage());
		}
		displayMenus();
		return result;
	}

	public String mockTest() {
		String result = null;
		try {
			result = "mockTest";
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return result;
	}

	public String showUploadSignature() throws Exception {
		String result = "";
		Map<Integer, List<Long>> dateWindowMap = ConfigurationConstants.getInstance().getDateWindowMap();
		List<Long> dateList = dateWindowMap.get(1);
		Long startDate = dateList.get(0);
		Long endDate = dateList.get(1);
		long today = ZonedDateTime.now().toInstant().toEpochMilli();
		if ((today == startDate || today > startDate) && (today == endDate || today < endDate)) {
		commonService.insertCandidateAuditrail(loggedInUser, "Signature Page - Loaded",
				"Candidate User Id : " + loggedInUser.getUsername() + " ~ Signature Page Loaded.");
		
		CandidateSignatureServiceConstants serviceResponse = candidatePhotoSignatureService.getUploadedSignature(loggedInUser, candidateBean, request);
		if (serviceResponse != null && serviceResponse.toString().equals("UPLOAD_SIGNATURE"))
			result = "showUploadSignature";
		return result;
	}else {
		// Used for displaying closure date in closewindow.jsp
		loggedInUser.setEditFlag(false);
		candidateBean.setEditBtnFlag(false);
		candidateBean.setRegStrtDate(CommonUtil.formatDate(new Date(startDate), GenericConstants.DATE_FORMAT_DEFAULT1));
		String newEndDate = CommonUtil.formatDate(new Date(endDate), GenericConstants.DATE_FORMAT_DEFAULT1);
		String[] endDateFinal = newEndDate.split(" ");
		candidateBean.setRegEndDate("at "+endDateFinal[1]+" hrs on " +endDateFinal[0]+".");
		candidateBean.setDisplayRegFlag("true");
		result = "CloseWindow";
		return result;
	}
	}

	public String showUploadSignature1() throws Exception {
		String result = "";
	//	commonService.insertCandidateAuditrail(loggedInUser, "Signature Page - Loaded", "Candidate with User Id " + loggedInUser.getUsername() + " : Signature Page Loaded.");

		CandidateSignatureServiceConstants serviceResponse = candidatePhotoSignatureService.getUploadedSignature(loggedInUser, candidateBean, request);
		if (serviceResponse != null && serviceResponse.toString().equals("UPLOAD_SIGNATURE"))
			result = "showUploadSignature";
		return result;
	}

	public String insertCandidateSignature() throws Exception {
		String result = "showUploadSignature";
		String message = "";
		String errorMessage = null;
		int updateupload;
		StringBuffer errorMessageBuffer = new StringBuffer();
		String errors = "";
		
		signatureBeanValidator.setCandidateBean(candidateBean);
		signatureBeanValidator.setLoggedInUser(loggedInUser);
		signatureBeanValidator.setType("form");
		signatureBeanValidator.setUploadPhotoSignInS3sdkV2(uploadPhotoSignInS3sdkV2);
		errorMessage = signatureBeanValidator.validateUploadedSignature();
		
		if (errorMessage != null && !errorMessage.equals("")) {
			errorMessageBuffer.append(errorMessage);
		}
		if (errorMessage == null || errorMessage.equals("")) {
			// saving candidate image in folder
			CandidateSignatureServiceConstants serviceresponse = candidatePhotoSignatureService.saveCandidateSignature(loggedInUser, candidateBean, requestAttributes, request);
			switch (serviceresponse) {
			case FILE_UPLOAD_FAILE:
				addActionMessage("ER2: File upload failed.Kindly re-upload the file.");
				result = "showUploadSignature";
				commonService.insertCandidateAuditrail(loggedInUser, "Upload Signature Failed",
						"Candidate with User Id " + loggedInUser.getUsername() + " File name : " + candidateBean.getAttachmentSignatureFileName() + " Upload Signature failed.");
				break;
			case SUCCESS:

				updateupload = candidatePhotoSignatureService.updateCandidatePhotoStatus(candidateBean, loggedInUser, "Sign");
				commonService.insertCandidateAuditrail(loggedInUser, "Signature Page - Save & Continue",
						"Candidate with User Id " + loggedInUser.getUsername() + " : " + candidateBean.getAttachmentSignatureFileName() +" uploaded");
				result = showUploadSignature1();
				break;
			default:
				addActionMessage("Error uploading signature, please contact Administrator.");
				commonService.insertCandidateAuditrail(loggedInUser, "Upload Signature Failed with Error", "Candidate with User Id " + loggedInUser.getUsername() + " File name : "
						+ candidateBean.getAttachmentSignatureFileName() + " Upload Signature failed with error.");
				result = "showUploadSignature";
			}
			return result;

		} else {
			if (request.getAttribute("menuKey") != null) {
				String mandatory = ConfigurationConstants.getInstance().getMenuKeyMandatory(request.getAttribute("menuKey").toString());
				if (mandatory != null && mandatory.equals(GenericConstants.MANDATORY)) {
					candidateBean.setSignatureMandatory(true);
				}
			}	
			if (errorMessageBuffer.length() > 0) {
				errors = errorMessageBuffer.toString();
				commonService.insertCandidateAuditrail(loggedInUser, "Signature Page - Exception or Error", "Candidate with User Id " + loggedInUser.getUsername() + ": " + errors);
			}
			message = "(message)" + errorMessage + "(/message)";
			addActionMessage(errorMessage);
			Users users = candidateService.getBasicCandidateInfo(loggedInUser);
			candidateBean.getPersonalDetailsBean().setCandidateName(users.getFirstName());
			candidateBean.getPersonalDetailsBean().setDateOfBirth(users.getDate_Of_Birth());
			//candidateBean.setGenderValDesc(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(loggedInUser.getGender())));
			candidateBean.setGenderValDesc(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(users.getGender())));
			candidateBean.setImageUploaded(true);
			result = "showUploadSignature";			
		}
		
		displayMenus();
		return result;
	} // new add

	public String showUploadDocument() throws Exception {
		String result = "getUploadManagement";
		Map<Integer, List<Long>> dateWindowMap = ConfigurationConstants.getInstance().getDateWindowMap();
		List<Long> dateList = dateWindowMap.get(1);
		Long startDate = dateList.get(0);
		Long endDate = dateList.get(1);
		long today = ZonedDateTime.now().toInstant().toEpochMilli();
		if ((today == startDate || today > startDate) && (today == endDate || today < endDate)) {
			commonService.insertCandidateAuditrail(loggedInUser, "Document upload Page - Loaded",
					"Candidate User Id : " + loggedInUser.getUsername() + " ~ Document Page Loaded.");

			result = candidateUploadDocService.showUploadDocument(loggedInUser, candidateBean, sessionAttributes, request);

			displayMenus();

			return result;
		} else {
			// Used for displaying closure date in closewindow.jsp
			loggedInUser.setEditFlag(false);
			candidateBean.setEditBtnFlag(false);
			candidateBean.setRegStrtDate(CommonUtil.formatDate(new Date(startDate), GenericConstants.DATE_FORMAT_DEFAULT1));
			String newEndDate = CommonUtil.formatDate(new Date(endDate), GenericConstants.DATE_FORMAT_DEFAULT1);
			String[] endDateFinal = newEndDate.split(" ");
			candidateBean.setRegEndDate("at " + endDateFinal[1] + " hrs on " + endDateFinal[0] + ".");
			candidateBean.setDisplayRegFlag("true");
			result = "CloseWindow";
			return result;

		}
	}
	
	public String getDocument() {
		String candidateDocId = request.getParameter("candidateDocId");
		String retTypeValue = null;
		try {
			if (candidateDocId != null) {
				CandidateBean candBean = candidateService.getDocument(candidateDocId, loggedInUser);

				candidateBean.setDocumentFileName(candBean.getDocumentFileName());
				// getting the view of uploaded doc from the local system
				// instead of db
				// binarydata[start]

				String strSaveInS3 = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SAVE_IN_S3);
				if (strSaveInS3 != null && !Strings.isBlank(strSaveInS3) && "true".equals(strSaveInS3.toLowerCase())) {
					UploadDocInS3sdkV2 uploadDocInS3sdkV2 = new UploadDocInS3sdkV2();
					InputStream inputStream = uploadDocInS3sdkV2.getInputStreamOfDocFromCandFolderInS3(loggedInUser, candBean);
					setDocumentInputStream(inputStream);
				} else {
					String DocumentPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
					FileInputStream fi = new FileInputStream(DocumentPath + loggedInUser.getUsername() + File.separator + loggedInUser.getUsername() + "_" + candBean.getOcdFlagValue() + "_"
							+ candidateBean.getDocumentFileName());
					BufferedInputStream bis = new BufferedInputStream(fi);
					setDocumentInputStream(bis);
				}

				// getting the view of uploaded doc from the local system
				// instead of db
				// binarydata[start]
				if (candBean.getDocumentFileName() != null) {
					String fileExtension = candBean.getDocumentFileName().split("\\.")[1];
					if (fileExtension != null && fileExtension.toLowerCase().equals("pdf")) {
						retTypeValue = "successPDF";
					} else if (fileExtension.toLowerCase() != null && (fileExtension.toLowerCase().equals("doc") || fileExtension.toLowerCase().equals("docx"))) {
						retTypeValue = "successDOC";
					} else if (fileExtension.toLowerCase() != null && (fileExtension.toLowerCase().equals("xls") || fileExtension.toLowerCase().equals("xlsx"))) {
						retTypeValue = "successXLS";
					} else if (fileExtension.toLowerCase() != null && (fileExtension.toLowerCase().equals("jpeg") || fileExtension.toLowerCase().equals("jpg")
							|| fileExtension.toLowerCase().equals("png") || fileExtension.toLowerCase().equals("gif"))) {
						retTypeValue = "successIMAGE";
					} else if (fileExtension.toLowerCase() != null && (fileExtension.toLowerCase().equals("csv"))) {
						retTypeValue = "successCsv";
					}
				}
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return retTypeValue;
	}

	public String insertCandidateDocuments() throws Exception {
	//	String serverSideErrors = "";
		String result = null;
	//	StringBuffer errorMessageBuffer = new StringBuffer();
		String errors = "";
		// serverSideErrors = insertCandidateDocumentsValidation();
		// ############################################################### new Impl
	//	DocumentBeanValidator documentBeanValidation = new DocumentBeanValidator(candidateBean, "form", loggedInUser);
		String serverSideErrors = documentBeanValidator.validateUploadedDocuments(candidateBean, "form", loggedInUser);
		
//		if (serverSideErrors != null && !serverSideErrors.equals("")) {
//			errorMessageBuffer.append(serverSideErrors);
//		}		
		if (candidateBean != null) {
			if (serverSideErrors != null && !serverSideErrors.equals("")) {				
				addActionMessage(serverSideErrors);
				showUploadDocument();
				result = "getUploadManagement";	
			} else {
				// Saving Doc Logic Start
				CandidateUploadDocConstants serviceresponse = candidateUploadDocService.saveUploadDocument(loggedInUser, candidateBean, requestAttributes, request);
				switch (serviceresponse) {
				case FILE_UPLOAD_FAILE:
					addActionMessage("ER2: File upload failed.Kindly re-upload the file.");
					/*commonService.insertCandidateAuditrail(loggedInUser, "Upload Doc Failed",
							"Candidate with User Id " + loggedInUser.getUsername() + " Document name: " + candidateBean.getData1FileName() + " Upload Document Failed");*/
					result = "getUploadManagement";
					break;
				case UPLOAD_DOC_FAIL:
					addActionMessage("ER1: File upload for " + candidateBean.getData1FileName() + " failed.Kindly re-upload the file. ");
					/*commonService.insertCandidateAuditrail(loggedInUser, "Upload Doc Failed",
							"Candidate with User Id " + loggedInUser.getUsername() + " Document name: " + candidateBean.getData1FileName() + " Upload Document Failed");*/
					result = "getUploadManagement";
					break;
				case SUCCESS:
					String ext="";
					if(Strings.isNotBlank(candidateBean.getData1FileName())) {
						ext=candidateBean.getData1FileName().split("[.]")[1];
					}
					commonService.insertCandidateAuditrail(loggedInUser, "Document upload Page - upload",
							"Candidate with User Id " + loggedInUser.getUsername() + " : Original Document Name - " + candidateBean.getData1FileName() + " New Document Name in Physical Path - "+loggedInUser.getUsername()+"_" +candidateBean.getOcdFlagValue()+"."+ext+" has Uploaded Successfully for "+candidateBean.getOcdFlagValue());
					result = "getUploadManagement";
					break;
				default:
					addActionMessage("Error uploading document, please contact Administrator.");
					commonService.insertCandidateAuditrail(loggedInUser, "Upload Doc Failed with Error",
							"Candidate with User Id " + loggedInUser.getUsername() + " Document name: " + candidateBean.getData1FileName() + " Upload Document Failed with Error");
					result = "getUploadManagement";
				}
				showUploadDocument();				
				result = "getUploadManagement";
								
			}
		}

		displayMenus();
		if (request.getAttribute("menuKey") == null) {
			HttpSession session = request.getSession();
			request.setAttribute("menuKey", "2.70");
			session.setAttribute("menuKey", "2.70");
		}
		if (request.getAttribute("menuKey") != null) {
			String mandatory = ConfigurationConstants.getInstance().getMenuKeyMandatory(request.getAttribute("menuKey").toString());
			if (mandatory != null && mandatory.equals(GenericConstants.MANDATORY)) {
				candidateBean.setDocumentMandatory(true);
			}
		}
		if (errors.length() > 0) {
//			errors = errorMessageBuffer.toString();
			commonService.insertCandidateAuditrail(loggedInUser, "Document upload Page - Exception or Error", "Candidate with User Id " + loggedInUser.getUsername() + ": " + errors);
		}
		return result;
	}

	/**
	 * Populate payment receipt data.
	 * 
	 * @throws Exception the exception
	 */
	private void populatePaymentReceipt() throws Exception {
		try {
			Map candidatePaymentRelatedData = candidateService.getCandidateDataForPaymentReceipt(loggedInUser);

			candidateBean.setInitials((String) candidatePaymentRelatedData.get("OCD_CAND_TITLE"));
			candidateBean.getPersonalDetailsBean().setCandidateFirstName((String) candidatePaymentRelatedData.get("OCD_FIRST_NAME"));
			candidateBean.getPersonalDetailsBean().setCandidateMiddleName((String) candidatePaymentRelatedData.get("OCD_MIDDLE_NAME"));
			candidateBean.getPersonalDetailsBean().setCandidateLastName((String) candidatePaymentRelatedData.get("OCD_LAST_NAME"));

			if (candidatePaymentRelatedData.get("OES_PAYMENT_DTL_PK") != null && !candidatePaymentRelatedData.get("OES_PAYMENT_DTL_PK").equals("")) {
				candidateBean.setPaymentPkAsReceiptNo(((BigDecimal) candidatePaymentRelatedData.get("OES_PAYMENT_DTL_PK")).toString());
			}

			if (candidatePaymentRelatedData.get("OPTM_PAYMENT_DESC") != null && !candidatePaymentRelatedData.get("OPTM_PAYMENT_DESC").equals("")) {
				candidateBean.setPaymentMode((String) candidatePaymentRelatedData.get("OPTM_PAYMENT_DESC"));
			}

			if (candidatePaymentRelatedData.get("OPD_AMOUNT") != null && !candidatePaymentRelatedData.get("OPD_AMOUNT").equals("")) {
				candidateBean.setReceiptAmount((String) candidatePaymentRelatedData.get("OPD_AMOUNT"));
			}

			if (candidatePaymentRelatedData.get("OPD_TRANSACTION_NO") != null && !candidatePaymentRelatedData.get("OPD_TRANSACTION_NO").equals("")) {
				candidateBean.setPaymentTransactionNo((String) candidatePaymentRelatedData.get("OPD_TRANSACTION_NO"));
			}

			if (candidatePaymentRelatedData.get("transactiondate") != null && !candidatePaymentRelatedData.get("transactiondate").equals("")) {
				candidateBean.setPaymentTransactionDate((String) candidatePaymentRelatedData.get("transactiondate"));
			}

			if (candidatePaymentRelatedData.get("OPD_DD_CHALLAN_RECEIPT_NO") != null && !candidatePaymentRelatedData.get("OPD_DD_CHALLAN_RECEIPT_NO").equals("")) {
				candidateBean.setDraftOrChallanNo((String) candidatePaymentRelatedData.get("OPD_DD_CHALLAN_RECEIPT_NO"));
			}
		} catch (Exception ex) {
			logger.info(ex.getMessage());
		}
	}

	/**
	 * Prepare pdf for payment receipt.
	 * 
	 * @return the string
	 * @throws Exception the exception
	 */
	public String preparePDFForPaymentReceipt() throws Exception {
		populatePaymentReceipt();

		try {
			parameterValues = new HashMap();

			String disciplinName = "";

			if (loggedInUser.getUserType().compareTo("C") == 0) {
				disciplinName = (ConfigurationConstants.getInstance().getTestDetailsFromMapForTestPk(Integer.parseInt(loggedInUser.getDisciplineID())).getTestName());
			}
			parameterValues.put("DISCIPLINE", disciplinName);

			StringBuffer userNamebf = new StringBuffer();

			if (candidateBean.getInitials() != null && !candidateBean.getInitials().equals("")) {
				userNamebf.append(candidateBean.getInitials() + " ");
			}
			userNamebf.append(candidateBean.getPersonalDetailsBean().getCandidateFirstName() + " ");

			if (candidateBean.getPersonalDetailsBean().getCandidateMiddleName() != null && !candidateBean.getPersonalDetailsBean().getCandidateMiddleName().equals("")) {
				userNamebf.append(candidateBean.getPersonalDetailsBean().getCandidateMiddleName() + " ");
			}
			userNamebf.append(candidateBean.getPersonalDetailsBean().getCandidateLastName());

			String userName = userNamebf.toString();

			parameterValues.put("NAME", userName);

			java.awt.Image companyLogo = ImageIO.read(new FileInputStream("tnu_logo.jpg"));

			parameterValues.put("LOGO_IMAGE", companyLogo);

			parameterValues.put("USER_ID", loggedInUser.getUsername());

			parameterValues.put("OPD_PAYMENT_PK", candidateBean.getDraftOrChallanNo());

			parameterValues.put("OPD_AMOUNT", candidateBean.getReceiptAmount());

			String paymentMode = null;
			if (candidateBean.getPaymentMode() != null) {

				if (candidateBean.getPaymentMode().equals("CH")) {
					paymentMode = "Challan";
				}
				if (candidateBean.getPaymentMode().equals("DD")) {
					paymentMode = "Demand Draft";
				}

				if (candidateBean.getPaymentMode().equals("NB")) {
					paymentMode = "Net Banking";
				}

				if (!candidateBean.getPaymentMode().equals("NB") && !candidateBean.getPaymentMode().equals("CH") && !candidateBean.getPaymentMode().equals("DD")) {
					paymentMode = candidateBean.getPaymentMode();
				}

			}
			if (paymentMode.equals("Demand Draft")) {
				paymentMode = "Demand Draft / NEFT";
			}
			parameterValues.put("OPD_PAYMENT_MODE", paymentMode);

			if (candidateBean.getPaymentTransactionNo() == null || candidateBean.getPaymentTransactionNo() == "") {
				parameterValues.put("OPD_TRANSACTION_NO", "NA");
			} else {
				parameterValues.put("OPD_TRANSACTION_NO", candidateBean.getPaymentTransactionNo());
			}

			parameterValues.put("OPD_TRANSACTION_DATE", candidateBean.getPaymentTransactionDate());

			if (candidateBean.getDraftOrChallanNo() == null || candidateBean.getDraftOrChallanNo() == "") {
				parameterValues.put("OPD_DD_CHALLAN_RECEIPT_NO", "NA");
			} else {
				parameterValues.put("OPD_DD_CHALLAN_RECEIPT_NO", candidateBean.getDraftOrChallanNo());
			}

			ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger()
					.info(loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ", Print Payment Receipt, " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));

		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		return "PrintPaymentReceipt";

	}

	public String continueTestBooking() throws Exception {
		String result = REDIRECT_ACTION_URL;
		String destinationUrl = null;
		Double onlineBookingStage = 0d;
		Users users = (Users) sessionAttributes.get(SESSION_USER);
		try {
			String dataBaseStage = loggedInUser.getStage();
			final int newStage = Integer.parseInt(dataBaseStage) + 1;
			// This current stage is set as data base stage in
			// registrationClick() method so i have to again increment it for
			// sending it to next stage
			// so current stage for next page will be below
			Double newCurrentStage = loggedInUser.getCurrentStage() + 1;
			// This is the OTBS stage
			onlineBookingStage = newCurrentStage + 1;
			// There is a check that will identify that the user is going from
			// print page by submitting the CONTINUE TEST BOOKING button
			if (newCurrentStage == ConfigurationConstants.getInstance().getStageKey(GenericConstants.STAGE_REGISTRATION_SUBMITTED)) {
				loggedInUser.setCurrentStage(newCurrentStage);
			}
			int stageUpdated = commonService.updateCandidateStage(loggedInUser, dataBaseStage);
			if (stageUpdated > 0) {
				destinationUrl = null;
			} else {
				destinationUrl = null;
			}
			request.setAttribute(DESTINATION_URL, destinationUrl);
			if (stageUpdated > 0) {
				users.setStage(String.valueOf(newStage));
				sessionAttributes.put(SESSION_USER, users);
			}

		} catch (Exception e) {
			logger.fatal(e, e);
		}

		return result;
	}

	private void prepopulateForm() {
		
		Map<Integer, List<Long>> dateWindowMap = ConfigurationConstants.getInstance().getDateWindowMap();
		List<Long> notificationDate = dateWindowMap.get(8);
		Long notificationEndDate = notificationDate.get(1);
		
		candidateBean.setNotificationEndDate(CommonUtil.formatDate(new Date(notificationEndDate), GenericConstants.DATE_FORMAT_DEFAULT));

		Map<Integer, String> motherTongueList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Mother_Tongue"); 
		Map<Integer, String> motherTongueMap = new TreeMap<Integer, String>(motherTongueList); 
		candidateBean.setMotherTongueList(motherTongueMap);
		
		Map<Integer, String> parentAndGuardianList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Relative");
		Map<Integer, String> parentAndGuardianListMap = new TreeMap<Integer, String>(parentAndGuardianList);
		candidateBean.setParentAndGuardianList(parentAndGuardianListMap);

		Map<Integer, String> religionBeliefList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Religion_Belief");
		Map<Integer, String> religionBeliefListtMap = new TreeMap<Integer, String>(religionBeliefList);
		candidateBean.setReligionBeliefList(religionBeliefListtMap);
		
		Map<Integer, String> maritalStatusList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Marital_Status");
		Map<Integer, String> maritalStatusListMap = new TreeMap<Integer, String>(maritalStatusList);
		candidateBean.setMaritalStatusList(maritalStatusListMap);
		
		Map<Integer, String> yesNo = ConfigurationConstants.getInstance().getParameterMapForDomainName("Yes_No_Map");
		Map<Integer, String> yesNoMap = new TreeMap<Integer, String>(yesNo);
		candidateBean.setYesNo(yesNoMap);
		
		Map<Integer, String> nativityList;
		nativityList = ConfigurationConstants.getInstance().getNativityMap();
		candidateBean.setNativityList(nativityList);
		
		Map<Integer, String> genderList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Gender");
		Map<Integer, String> genderMap = new TreeMap<Integer, String>(genderList);
		candidateBean.setGenderList(genderMap);
		
		Map<Integer, String> photoIdProofList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Photo_ID_Proof"); 
		  Map<Integer, String> photoIdProofListMap = new TreeMap<Integer, String>(photoIdProofList); 
		  candidateBean.setPhotoIdProof(photoIdProofListMap);
		
		candidateBean.setStateList(ConfigurationConstants.getInstance().getStateMap(1));
		
		Map<Integer, String> stateListWoTamilList = ConfigurationConstants.getInstance().getStateMap(1);
		Map<Integer, String> stateListWoTamilMap = new TreeMap<Integer, String>(stateListWoTamilList);
		stateListWoTamilMap.remove(1); // removing tamil nadu from state list
		candidateBean.setStateListWoTamil(stateListWoTamilMap);

		Map<Integer, String> countryList = ConfigurationConstants.getInstance().getCountryMap();
		candidateBean.setCountryList(countryList);

		Map<Integer, String> districtList = ConfigurationConstants.getInstance().getDistrictMap(1);
		Map<Integer, String> districtListMap = new TreeMap<Integer, String>(districtList);
		candidateBean.setDistrictList(districtListMap);
		
		Map<Integer, String> altDistrictList = ConfigurationConstants.getInstance().getDistrictMap(1);
		Map<Integer, String> altDistrictListMap = new TreeMap<Integer, String>(altDistrictList);
		candidateBean.setAltDistrictList(altDistrictListMap);
		
		Map<Integer, String> categoryList = ConfigurationConstants.getInstance().getCategoryListMap();
		candidateBean.setCategoryList(categoryList);

		if (StringUtils.isNotBlank(loggedInUser.getNationality())) {
			candidateBean.setNationality(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(loggedInUser.getNationality())));
		}

		if (StringUtils.isNotBlank(loggedInUser.getNativityCertDist())) {
			candidateBean.getPersonalDetailsBean().setAppliedDist(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(loggedInUser.getNativityCertDist())));
		}
		
		if (StringUtils.isNotBlank(loggedInUser.getCandidateFirstName())) {
			candidateBean.getPersonalDetailsBean().setCandidateFirstName(loggedInUser.getCandidateFirstName());
		}
		
		if (StringUtils.isNotBlank(loggedInUser.getDisableYesNo())) {
			candidateBean.setDisableYesNo(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(loggedInUser.getDisableYesNo())));
		}
		
		candidateBean.setDiffAbledChkBox(loggedInUser.isDiffAbledChkBox());
		
		if (loggedInUser.getEmailAddress() != null && !loggedInUser.getEmailAddress().equals("")) {
			candidateBean.getPersonalDetailsBean().setEmail(loggedInUser.getEmailAddress());
		}
		
		if (loggedInUser.getMobile() != null && !loggedInUser.getMobile().equals("")) {
			candidateBean.getPersonalDetailsBean().setMobileNo(loggedInUser.getMobile());
		}

		if (loggedInUser.getDate_Of_Birth() != null && !loggedInUser.getDate_Of_Birth().equals("")) {
			candidateBean.setDateOfBirth(loggedInUser.getDate_Of_Birth());
		}
		if (loggedInUser.getAge() != null && !loggedInUser.getAge().equals("")) {
			candidateBean.getPersonalDetailsBean().setAge(loggedInUser.getAge());
		}
		
		if (loggedInUser.getDisciplineID() != null && !loggedInUser.getDisciplineID().equals("")) {
			candidateBean.setDisciplinName(ConfigurationConstants.getInstance().getTestDetailsFromMapForTestPk(Integer.parseInt(loggedInUser.getDisciplineID())).getTestName());
		}
		
		if (StringUtils.isNotBlank(loggedInUser.getCommCertYesNo())) {
			candidateBean.setCommCertYesNo(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(loggedInUser.getCommCertYesNo())));
		}
		if(StringUtils.isNotBlank(loggedInUser.getCommunity()))
		{
			candidateBean.setCommunity(ConfigurationConstants.getInstance().getCategoryValueForKey(Integer.parseInt(loggedInUser.getCommunity())));
		}
		if(StringUtils.isNotBlank(loggedInUser.getSubCaste()))
		{
			candidateBean.setSubCaste(loggedInUser.getSubCaste());
		}
		if(StringUtils.isNotBlank(loggedInUser.getIssueAuthCommCert()))
		{
			candidateBean.setIssueAuthCommCert(loggedInUser.getIssueAuthCommCert());
		}
		if(StringUtils.isNotBlank(loggedInUser.getCommCertNo()))
		{
			candidateBean.setCommCertNo(loggedInUser.getCommCertNo());
		}
		if(StringUtils.isNotBlank(loggedInUser.getCommCertPlace()))
		{
			candidateBean.setCommCertPlace(loggedInUser.getCommCertPlace());
		}
		if(StringUtils.isNotBlank(loggedInUser.getCommIssueDate()))
		{
			candidateBean.setCommIssueDate(loggedInUser.getCommIssueDate());
		}
		
		candidateBean.setAgeAsOn(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AGE_AS_ON));
		
	}

	public InputStream getDocumentInputStream() {
		return documentInputStream;
	}

	public void setDocumentInputStream(InputStream documentInputStream) {
		this.documentInputStream = documentInputStream;
	}

	public String displayPaymentReceipt() {
		return "showPaymentReceipt";
	}

	public String getFileExtension(String fileName) {
		String fname = "";
		String ext = "";
		int mid = fileName.lastIndexOf(".");
		if (mid > 0) {
			fname = fileName.substring(0, mid);
			ext = fileName.substring(mid + 1, fileName.length());
		} else {
			ext = "noext";
		}
		return ext;
	}

	public String getCandidateEducationalDetailsPage() throws Exception {
		String result = null;
		Map<Integer, List<Long>> dateWindowMap = ConfigurationConstants.getInstance().getDateWindowMap();
		List<Long> dateList = dateWindowMap.get(7);
		Long endDate = dateList.get(1);
		
		String extractedEndDate = CommonUtil.formatDate(new Date(endDate), GenericConstants.DATE_FORMAT_DEFAULT1);
		int nextYear = LocalDateTime.parse(extractedEndDate, DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss")).getYear() + 5;
		candidateBean.setRegValidEndYr(nextYear);
		
		String regValidUptoDate = java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy")
			    .format(java.time.LocalDateTime.parse(extractedEndDate, 
			    	    java.time.format.DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss")).plusYears(5));
		candidateBean.setRegValidEndDate(regValidUptoDate);
		
		List<Long> dateList1 = dateWindowMap.get(8);
		Long startDate = dateList1.get(0);
		String extractedStartDate = CommonUtil.formatDate(new Date(startDate), GenericConstants.DATE_FORMAT_DEFAULT1);
		int strYear = LocalDateTime.parse(extractedStartDate, DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss")).getYear();
		candidateBean.setRegValidStartYr(strYear);
		
		String regValidStartDate = java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy")
			    .format(java.time.LocalDateTime.parse(extractedStartDate, 
			    	    java.time.format.DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss")));
		candidateBean.setRegValidUptoDate(regValidStartDate);
		
		result = candidateEducationService.getCandidateEducationalDetailsPage(loggedInUser, candidateBean, sessionAttributes, request);
		commonService.insertCandidateAuditrail(loggedInUser, "Educational Qualification Page-Loaded",
				"Candidate User Id : " + loggedInUser.getUsername() + " ~ Educational Qualification Page : Loaded");
		return result;
	}

	private void getBaseDataForEducationalDetails() throws Exception {

		candidateBean.setServerSideErrorMessage("true");
		candidateBean.setSaveFlag("true");

		candidateBean.setSscUnivList(ConfigurationConstants.getInstance().getUniversityMap().get(1));
		candidateBean.setHscUnivList(ConfigurationConstants.getInstance().getUniversityMap().get(2));
		candidateBean.setUGUnivList(ConfigurationConstants.getInstance().getUniversityMap().get(3));
		candidateBean.setYesNo(new TreeMap<>(ConfigurationConstants.getInstance().getParameterMapForDomainName("Yes_No_Map")));
		candidateBean.setMedOfInstructionList(ConfigurationConstants.getInstance().getParameterMapForDomainName("medium_of_instruction"));

		String dateOfBirthValue = loggedInUser.getDate_Of_Birth();

		if (StringUtils.isNotEmpty(dateOfBirthValue)) {
			String[] d = dateOfBirthValue.split("-");
			int dobYear = Integer.parseInt(d[2]);
			candidateBean.setFirstDate(dobYear + 12);
			candidateBean.setHscDate(dobYear + 13);
			candidateBean.setDiplomaDate(dobYear + 13);
			candidateBean.setGraduationDate(dobYear + 14);
			candidateBean.setPgDate(dobYear + 15);
		}
		Map<Integer, List<Long>> dateWindowMap = ConfigurationConstants.getInstance().getDateWindowMap();
		List<Long> dateList = dateWindowMap.get(7);
		Long endDate = dateList.get(1);

		String extractedEndDate = CommonUtil.formatDate(new Date(endDate), GenericConstants.DATE_FORMAT_DEFAULT1);
		int nextYear = LocalDateTime.parse(extractedEndDate, DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss")).getYear() + 5;
		candidateBean.setRegValidEndYr(nextYear);

		String regValidUptoDate = java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy")
				.format(java.time.LocalDateTime.parse(extractedEndDate, java.time.format.DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss")).plusYears(5));
		candidateBean.setRegValidEndDate(regValidUptoDate);

		List<Long> dateList1 = dateWindowMap.get(8);
		Long startDate = dateList1.get(0);
		String extractedStartDate = CommonUtil.formatDate(new Date(startDate), GenericConstants.DATE_FORMAT_DEFAULT1);
		int strYear = LocalDateTime.parse(extractedStartDate, DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss")).getYear();
		candidateBean.setRegValidStartYr(strYear);

		String regValidStartDate = java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy")
				.format(java.time.LocalDateTime.parse(extractedStartDate, java.time.format.DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss")));
		candidateBean.setRegValidUptoDate(regValidStartDate);
	}

	public String saveAcademicDetails() throws LogicError, Exception {
		String result = "educationalDetailsPage";
		StringBuffer errorMessageBuffer = new StringBuffer();
		String errors = "";
		String serverSideEducationDetailsErrors ="";
		serverSideEducationDetailsErrors = educationBeanValidator.validateEducationalDetails(candidateBean,loggedInUser, "form");

		if (Strings.isNotBlank(serverSideEducationDetailsErrors)) {
			addActionMessage(serverSideEducationDetailsErrors);
			candidateBean.setServerSideErrorMessage("true");
			getBaseDataForEducationalDetails();
			result = "educationalDetailsPage";
		} else {
			try {
				// Added in CandidateEducationService saveEducationalDetails() for Saved in DB
				ErrorCodeEnum serviceresult = candidateEducationService.saveEducationalDetails(loggedInUser,
						candidateBean, sessionAttributes, request);
				switch (serviceresult) {
				case UPDATE_CANDIDATE_STAGE:
					result = updateCandidateStage();
					break;
				case ERROR:
					getBaseDataForEducationalDetails();
					result = ERROR;
					logger.error("Error ocurred while saving the candidate Education details.");
					break;
				default:
					getBaseDataForEducationalDetails();
					throw new LogicError("Error ocurred while saving the candidate Education details.");
				}
			} catch (Exception e) {
				logger.error(e,e);
				logger.info("Exception occured while saving Education Details for User ID: "+ loggedInUser.getUsername() + e.getMessage());
				addActionMessage("Error Occured while saving Education Details. Please try again !");
				getBaseDataForEducationalDetails();
				result = "educationalDetailsPage";
			}
		}
		if (request.getAttribute("menuKey") != null) {
			String mandatory = ConfigurationConstants.getInstance()
					.getMenuKeyMandatory(request.getAttribute("menuKey").toString());
			if (mandatory != null && mandatory.equals(GenericConstants.MANDATORY)) {
				candidateBean.setEducationalDetailsMandatory(true);
			}
		}
		if (errorMessageBuffer.length() > 0) {
			errors = errorMessageBuffer.toString();
			commonService.insertCandidateAuditrail(loggedInUser, "Education Page - Exception or Error",
					"Candidate with User Id " + loggedInUser.getUsername() + ": " + errors);
		}
		displayMenus();
		return result;
	}

	private void populateCandidateDashboard(Users users) throws Exception {
		candidateBean.setFlag("false");

		CandidateBean newCandidateBean = null;
		List<CandidateBean> candidatePostList = null;

		if (users.getUserFk() != 0 && users.getUserFk() != null && !users.getUserFk().equals("")) {
			newCandidateBean = candidateService.getCandidateDetailsForDashboard(users);
			PersonalDetailsBean personalDetailsBean = newCandidateBean.getPersonalDetailsBean();
			String sd = null;

			personalDetailsBean.setPost1(sd);
			if (personalDetailsBean != null) {
				candidateBean.setPersonalDetailsBean(personalDetailsBean);
			}

			if (StringUtils.isNotBlank(loggedInUser.getDiscipline())) {
				candidateBean.setDisciplineType(loggedInUser.getDiscipline());
			} else {
				candidateBean.setDisciplineType(newCandidateBean.getDisciplineType());
			}
			if (StringUtils.isNotBlank(newCandidateBean.getGenderValDesc()))
				candidateBean.setGenderValDesc(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(newCandidateBean.getGenderValDesc())));

			if (newCandidateBean.getStatusID() < 5) {
				candidateBean.setStatus(ConfigurationConstants.getInstance().getStatusMasterMap().get(0));
			}
			if (newCandidateBean.getStatusID() >= 5) {
				candidateBean.setStatus("Application Form Submitted");
			}
			candidateBean.setStatusID(newCandidateBean.getStatusID());

			if (ConfigurationConstants.getInstance().getStatusKey(GenericConstants.APPLIED_FOR_POST) == newCandidateBean.getStatusID()
					|| ConfigurationConstants.getInstance().getStatusKey(GenericConstants.APPLIED_FOR_POST_INITIATED) == newCandidateBean.getStatusID()
					|| ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_SUBMITED) == newCandidateBean.getStatusID()
					|| ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED) == newCandidateBean.getStatusID()
					|| ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_REJECTED) == newCandidateBean.getStatusID()) {
				candidateBean.setDisplayPostDetails(true);
			}
		} else {
			PersonalDetailsBean personalDetailsBean = new PersonalDetailsBean();

			if (StringUtils.isNotBlank(loggedInUser.getCandidateName())) {
				personalDetailsBean.setCandidateName(loggedInUser.getCandidateName());
			}

			if (StringUtils.isNotBlank(loggedInUser.getEmailAddress())) {
				personalDetailsBean.setEmail(loggedInUser.getEmailAddress());
			}

			if (StringUtils.isNotBlank(loggedInUser.getDate_Of_Birth())) {
				personalDetailsBean.setDateOfBirth(loggedInUser.getDate_Of_Birth());
			}

			if (StringUtils.isNotBlank(loggedInUser.getMobile())) {
				personalDetailsBean.setMobileNo(loggedInUser.getMobile());
			}
			if (StringUtils.isNotBlank(loggedInUser.getGenderValDesc())) {
				candidateBean.setGenderValDesc(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(loggedInUser.getGenderValDesc())));
			}

			candidateBean.setDisciplineType(loggedInUser.getDiscipline());
			candidateBean.setPersonalDetailsBean(personalDetailsBean);
			candidateBean.setStatus(ConfigurationConstants.getInstance().getStatusMasterMap().get(loggedInUser.getStatusID()));
			candidateBean.setStatusID(loggedInUser.getStatusID());
		}
	}

	public String audit() throws Exception {
		String result = null;
		Users users = (Users) sessionAttributes.get(SESSION_USER);

		commonService.insertCandidateAuditrail(loggedInUser, "Dashboard Page Redirection From Payment Success page",
				"Candidate with User Id " + loggedInUser.getUsername() + " has clicked on submit Button after succesfull payment.");

		result = dashboard();
		return result;

	}

	public String dashboard() throws Exception {
		String result = null;
		Users users = (Users) sessionAttributes.get(SESSION_USER);
		String declerechkdbValue = loggedInUser.getDeclaration();
		String declarecheck = candidateBean.getDeclaration();
		String declarechkhdn = candidateBean.getDeclarationhid();
		if (declerechkdbValue == null || "".equals(declerechkdbValue)) {
			if (declarecheck == null || declarecheck.isEmpty() || "false".equals(declarecheck)) {
				// user did not check the decleration
				logger.info("user did not check the decleration check box");
				candidateBean.setDeclarationhid("false");
				result = "notloggedin";
				return result;

			}
		}

		commonService.insertCandidateAuditrail(loggedInUser, "Dashboard Page Load", "Candidate with User Id " + loggedInUser.getUsername() + " has come to the Dashboard page.");

		if ("true".equals(declerechkdbValue)) {
			candidateBean.setDeclarationhid("true");
			users.setDeclaration("true");

		}
		populateCandidateDashboard(loggedInUser);

		Integer status = candidateBean.getStatusID();

		candidateBean.setCandidatePostList(commonService.getCandidateAppliedPost(loggedInUser, status));
		candidateBean.setStageUpdate(String.valueOf(status));

		String strSaveInS3 = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SAVE_IN_S3);
		if (strSaveInS3 != null && !Strings.isBlank(strSaveInS3) && "true".equals(strSaveInS3.toLowerCase())) {
			UploadDocInS3sdkV2 uploadDocInS3sdkV2 = new UploadDocInS3sdkV2();
			candidateBean.setAdmitCardLabel(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_ADMIT_CARD_LABEL));
			String admitCardPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.S3_ADMIT_CARD_PATH);
			if (uploadDocInS3sdkV2.isDocFilePresentInProjectFolderOfS3(admitCardPath, loggedInUser)) {
				candidateBean.setAdmitCardExist("true");
			} else {
				candidateBean.setAdmitCardExist("false");
			}

			candidateBean.setCallLetterLabel(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_CALL_LETTER_LABEL));
			String callLetterPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.S3_CALL_LETTER_PATH);
			if (uploadDocInS3sdkV2.isDocFilePresentInProjectFolderOfS3(callLetterPath, loggedInUser)) {
				candidateBean.setCallLetterExist("true");
			} else {
				candidateBean.setCallLetterExist("false");
			}

			candidateBean.setScoreCardLabel(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_SCORE_CARD_LABEL));
			String scoreCardPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.S3_SCORE_CARD_PATH);
			if (uploadDocInS3sdkV2.isDocFilePresentInProjectFolderOfS3(scoreCardPath, loggedInUser)) {
				candidateBean.setScoreCardExist("true");
			} else {
				candidateBean.setScoreCardExist("false");
			}

		} else {
			// Admit Card
			candidateBean.setAdmitCardLabel(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_ADMIT_CARD_LABEL));

			String admitCardPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_ADMIT_CARD_PATH);
			File admitCardPathFile = new File(admitCardPath + File.separator + loggedInUser.getUsername() + ".pdf");
			if (admitCardPathFile.exists()) {
				candidateBean.setAdmitCardExist("true");
			} else {
				candidateBean.setAdmitCardExist("false");
			}

			// Call Letter
			candidateBean.setCallLetterLabel(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_CALL_LETTER_LABEL));
			String callLetterPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_CALL_LETTER_PATH);
			File callLetterFile = new File(callLetterPath + File.separator + loggedInUser.getUsername() + ".pdf");
			if (callLetterFile.exists()) {
				candidateBean.setCallLetterExist("true");
			} else {
				candidateBean.setCallLetterExist("false");
			}

			// Score Card
			candidateBean.setScoreCardLabel(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_SCORE_CARD_LABEL));
			String scoreCardPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_SCORE_CARD_PATH);
			File scoreCardPathFile = new File(scoreCardPath + File.separator + loggedInUser.getUsername() + ".pdf");
			if (scoreCardPathFile.exists()) {
				candidateBean.setScoreCardExist("true");
			} else {
				candidateBean.setScoreCardExist("false");
			}
		}
		// Pratice Test
		candidateBean.setShowPracticeTestLink(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_SHOW_PRACTICE_LINK));
		if (candidateBean.getShowPracticeTestLink().equalsIgnoreCase("Y")) {
			candidateBean.setPracticeTestLabel(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_PRACTICE_TEST_LABEL));
		}
		// TODO 1: DO we require REGH ??
//		if (status.equals(10)) {
//			candidateBean.setRegistrationId(commonService.getRegistrationId(loggedInUser));// IGIP00036 OR IGIM00036
//		}
		candidateBean.setUserId(loggedInUser.getUsername());
		return "showCandidateDashboard";
	}

	public String saveChallanDetails() throws Exception {
		String result = null;
		Users users = new Users();
		users = (Users) sessionAttributes.get(SESSION_USER);
		candidateBean.getApplicablefeeamt();
		result = dashboard();
		return result;
	}

	public String getCandidateAdditionalDetails() throws Exception {
		String result = null;
		Map<Integer, List<Long>> dateWindowMap = ConfigurationConstants.getInstance().getDateWindowMap();
		List<Long> dateList = dateWindowMap.get(1);
		Long startDate = dateList.get(0);
		Long endDate = dateList.get(1);
		long today = CommonUtil.getCurrentDateInMillis();
		candidateBean.setMaxDate(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_DATE_FOR_DATEPICKER));

		/*List<Long> dateList1 = dateWindowMap.get(7);
		Long editStartDate = dateList1.get(0);
		Long editEndDate = dateList1.get(1); */ //todo

		Boolean editflag = false;
		//editflag = commonService.getcandidatEditStatus(loggedInUser.getUserId());
		if ((today == startDate || today > startDate) && (today == endDate || today < endDate)) {
			commonService.insertCandidateAuditrail(loggedInUser, "Additional Details Page load",
					"Candidate with User ID " + loggedInUser.getUsername() + " has come to Additional Details Page");
			result = candidateAdditionalDetailsService.getCandidateAdditionalDetails(loggedInUser, candidateBean, sessionAttributes, request);
			/*loggedInUser.setEditFlag(false);
			candidateBean.setEditBtnFlag(false);
			if (result != null && result.equals("notloggedin")) {
				logger.debug("user did not check the declaration check box");
				candidateBean.setDeclarationhid("false");
				result = "notloggedin";
			}*/
		} /*else if ((today == editStartDate || today > editStartDate) && (today == editEndDate || today < editEndDate) && editflag) {

			loggedInUser.setEditFlag(true);
			candidateBean.setEditBtnFlag(true);
			commonService.insertCandidateAuditrail(loggedInUser, "Edit Additional Details Page load",
					"Candidate with User Id " + loggedInUser.getUsername() + " has come to Edit Additional Details Page");
			result = candidateAdditionalDetailsService.getCandidateAdditionalDetails(loggedInUser, candidateBean, sessionAttributes, request);

			if (result != null && result.equals("notloggedin")) {
				logger.debug("user did not check the declaration check box");
				candidateBean.setDeclarationhid("false");
				result = "notloggedin";
			}
		}*/ //todo
		
		else {
			// Used for displaying closure date in closewindow.jsp
			candidateBean.setRegStrtDate(CommonUtil.formatDate(new Date(startDate), GenericConstants.DATE_FORMAT_DEFAULT));
			candidateBean.setRegEndDate(CommonUtil.formatDate(new Date(endDate), GenericConstants.DATE_FORMAT_DEFAULT));
			candidateBean.setDisplayRegFlag("true");
			result = "CloseWindow";
		}
		return result;
	}

	public String AuditTrailForDeclaration() {
		loggedInUser.setRemoteIp(request.getRemoteAddr());
		String action = candidateBean.getAction();
		String audittrail = candidateBean.getAudittrail();
		try {
			commonService.insertCandidateAuditrail(loggedInUser, action, "Candidate with User Id " + loggedInUser.getUsername() + " " + audittrail + ".");
		} catch (Exception e) {
			logger.error("UserId :" + loggedInUser.getUsername() + "Error Message :" + e.getMessage());
		}
		return "writePlainText";
	}

	public void setToken(String token) {
		this.token = token;
	}

	// added by sravyav for academic
	public String getDegreeList() {
		Integer countryVal = null;
		String exam = null;
		String districtVal = "";
		Map<Integer, String> degreeList = new LinkedHashMap<Integer, String>();
		List<CommonBean> stateList = new ArrayList<CommonBean>();
		try {
			if (candidateBean.getAddDegreeCourse() != null && !candidateBean.getAddDegreeCourse().equals(""))
				degreeList = ConfigurationConstants.getInstance().getAcademicMap1(Integer.parseInt(candidateBean.getAddDegreeCourse()));
			if (degreeList != null) {
				for (Map.Entry<Integer, String> entry : degreeList.entrySet()) {
					if (entry != null) {
						if (entry.getValue() != null && !entry.getValue().equals("") && entry.getKey() != null && !entry.getKey().equals("")) {
							districtVal = districtVal + (entry.getValue() + "#" + entry.getKey()) + ",";
						}
					}
				}
			}

			if (districtVal != null && !districtVal.equals("") && districtVal.endsWith(",")) {
				districtVal = districtVal.substring(0, districtVal.length() - 1);
			}

			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, districtVal);

		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		return "writePlainText";
	}

	public String getToken() {

		return token;
	}

	public String getSubcasteList() throws Exception {
		String subcast = candidateService.getSubcasteList(candidateBean);
		request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, subcast);
		return "writePlainText";
	}

	public String redirectPaymentUpload() throws Exception {
		String result = "redirectPaymentUpload";
		try {

		} catch (Exception e) {
			// TODO 2: handle exception
		}
		return result;

	}

	public String getCandidateEducationalDetailsPageForUpdate() {
		String result = "";
		try {
			result = getCandidateEducationalDetailsPage();
		} catch (Exception e) {
			// TODO 2: handle exception
		}
		return result;
	}

	private static Map sortByComparator(Map unsortMap) {
		List list = new LinkedList(unsortMap.entrySet());
		// sort list based on comparator
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o2)).getKey()).compareTo(((Map.Entry) (o1)).getKey());
			}
		});

		// put sorted list into map again
		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	private static Map sortMapUsingKeyComparator(Map unsortMap) {
		Set<Entry<Integer, String>> entries = unsortMap.entrySet();
		List<Entry<Integer, String>> aList = new LinkedList<Entry<Integer, String>>(entries);
		Collections.sort(aList, new Comparator<Entry<Integer, String>>() {

			@Override
			public int compare(Entry<Integer, String> ele1, Entry<Integer, String> ele2) {
				return ele1.getKey().compareTo(ele2.getKey());
			}
		});
		Map<Integer, String> aMap2 = new LinkedHashMap<Integer, String>();
		for (Entry<Integer, String> entry : aList) {
			aMap2.put(entry.getKey(), entry.getValue());
		}
		return aMap2;

	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getDept() {
		return dept;
	}

	private String saveFileonLocal(String docName, byte[] s, String flag) {
		// saving candidate documents in folder
		String path = "";
		String DocumentPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
		File DocumentPathFile = new File(DocumentPath);
		if (!DocumentPathFile.exists()) {
			DocumentPathFile.mkdir();
		}
		if (DocumentPathFile.exists()) {
			String candidateFolder = loggedInUser.getUsername();
			File candidateFolderFile = new File(DocumentPath + "" + candidateFolder);
			if (!candidateFolderFile.exists()) {
				candidateFolderFile.mkdir();
			}
			if (candidateFolderFile.exists()) {

				File documentFolder = new File(DocumentPath + "" + candidateFolder);
				if (!documentFolder.exists()) {
					documentFolder.mkdir();
				}
				if (documentFolder.exists()) {
					if (docName != null) {
						int BUFFER_SIZE = 1024;
						int bytesRead = -1;
						byte[] imageInByte = null;
						imageInByte = s;
						if (imageInByte != null) {
							if (docName.toLowerCase().endsWith(".jpg")) {
								ByteArrayInputStream in = new ByteArrayInputStream(imageInByte);
								FileOutputStream out = null;
								try {
									out = new FileOutputStream(new File(documentFolder + File.separator + flag + "_" + docName));
								} catch (FileNotFoundException e) {
									path = "ER2: File upload failed.Kindly re-upload the file.";
								}
								try {
									IOUtils.copy(in, out);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								IOUtils.closeQuietly(in);
								IOUtils.closeQuietly(out);

								MessageDigest md = null;
								try {
									md = MessageDigest.getInstance("MD5");
								} catch (NoSuchAlgorithmException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								FileInputStream fis = null;
								try {
									fis = new FileInputStream(documentFolder + File.separator + flag + "_" + docName);
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									path = "ER2: File upload failed.Kindly re-upload the file.";
								}
								byte[] dataBytes = new byte[1024];
								int nread = 0;
								try {
									if (fis != null) {
										while ((nread = fis.read(dataBytes)) != -1) {
											md.update(dataBytes, 0, nread);
										}
									}

								} catch (IOException e) {
									// TODO Auto-generated catch block
									path = "ER2: File upload failed.Kindly re-upload the file.";
								}
								;
								if (fis != null) {
									byte[] mdbytes = md.digest();
									StringBuffer sb = new StringBuffer();
									for (int i = 0; i < mdbytes.length; i++) {
										sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
									}
									path = sb.toString();
									try {
										fis.close();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}

							if (docName.toLowerCase().endsWith(".jpeg")) {
								ByteArrayInputStream in = new ByteArrayInputStream(imageInByte);
								FileOutputStream out = null;
								try {
									out = new FileOutputStream(new File(documentFolder + File.separator + flag + "_" + docName));
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									path = "ER2: File upload failed.Kindly re-upload the file.";
								}
								try {
									IOUtils.copy(in, out);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								IOUtils.closeQuietly(in);
								IOUtils.closeQuietly(out);

								MessageDigest md = null;
								try {
									md = MessageDigest.getInstance("MD5");
								} catch (NoSuchAlgorithmException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								FileInputStream fis = null;
								try {
									fis = new FileInputStream(documentFolder + File.separator + flag + "_" + docName);
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									path = "ER2: File upload failed.Kindly re-upload the file.";
								}
								byte[] dataBytes = new byte[1024];
								int nread = 0;
								try {
									if (fis != null) {
										while ((nread = fis.read(dataBytes)) != -1) {
											md.update(dataBytes, 0, nread);
										}
									}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									path = "ER2: File upload failed.Kindly re-upload the file.";
								}
								;
								if (fis != null) {
									byte[] mdbytes = md.digest();
									StringBuffer sb = new StringBuffer();
									for (int i = 0; i < mdbytes.length; i++) {
										sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
									}
									path = sb.toString();
									try {
										fis.close();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
							if (docName.toLowerCase().endsWith(".png")) {
								ByteArrayInputStream in = new ByteArrayInputStream(imageInByte);
								FileOutputStream out = null;
								try {
									out = new FileOutputStream(new File(documentFolder + File.separator + flag + "_" + docName));
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									path = "ER2: File upload failed.Kindly re-upload the file.";
								}
								try {
									IOUtils.copy(in, out);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								IOUtils.closeQuietly(in);
								IOUtils.closeQuietly(out);

								MessageDigest md = null;
								try {
									md = MessageDigest.getInstance("MD5");
								} catch (NoSuchAlgorithmException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								FileInputStream fis = null;
								try {
									fis = new FileInputStream(documentFolder + File.separator + flag + "_" + docName);
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									path = "ER2: File upload failed.Kindly re-upload the file.";
								}
								byte[] dataBytes = new byte[1024];
								int nread = 0;
								try {
									if (fis != null) {
										while ((nread = fis.read(dataBytes)) != -1) {
											md.update(dataBytes, 0, nread);
										}
									}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									path = "ER2: File upload failed.Kindly re-upload the file.";
								}
								;
								if (fis != null) {
									byte[] mdbytes = md.digest();
									StringBuffer sb = new StringBuffer();
									for (int i = 0; i < mdbytes.length; i++) {
										sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
									}
									path = sb.toString();
									try {
										fis.close();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
							if (docName.toLowerCase().endsWith(".pdf")) {
								InputStream in = new ByteArrayInputStream(imageInByte);
								java.io.OutputStream outputstream = null;
								try {
									outputstream = new FileOutputStream(documentFolder + File.separator + flag + "_" + docName);
								} catch (FileNotFoundException e1) {
									// TODO Auto-generated catch block
									path = "ER2: File upload failed.Kindly re-upload the file.";
								}
								byte[] buffer = new byte[BUFFER_SIZE];
								try {
									while ((bytesRead = in.read(buffer)) != -1) {
										outputstream.write(buffer, 0, bytesRead);
									}
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								try {
									in.close();
									outputstream.close();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								MessageDigest md = null;
								try {
									md = MessageDigest.getInstance("MD5");
								} catch (NoSuchAlgorithmException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								FileInputStream fis = null;
								try {
									fis = new FileInputStream(documentFolder + File.separator + flag + "_" + docName);
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									path = "ER2: File upload failed.Kindly re-upload the file.";
								}
								byte[] dataBytes = new byte[1024];
								int nread = 0;
								try {
									if (fis != null) {
										while ((nread = fis.read(dataBytes)) != -1) {
											md.update(dataBytes, 0, nread);
										}
									}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									path = "ER2: File upload failed.Kindly re-upload the file.";
								}
								;
								if (fis != null) {
									byte[] mdbytes = md.digest();
									StringBuffer sb = new StringBuffer();
									for (int i = 0; i < mdbytes.length; i++) {
										sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
									}
									path = sb.toString();
									try {
										fis.close();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
		}
		return path;
	}

	public String checkMissingUploadedDoc() throws Exception {
		String val = candidateUploadDocService.getDocumentUploaded(loggedInUser);
		request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, val);
		return "writePlainText";
	}
//

// ################################################################################################################### downloads
	// TODO 1: ScoreCard - completed
	public String printScoreCardPDF() throws Exception {
		try {
			String strSaveInS3 = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SAVE_IN_S3);
			if (strSaveInS3 != null && !Strings.isBlank(strSaveInS3) && "true".equals(strSaveInS3.toLowerCase())) {
				UploadDocInS3sdkV2 uploadDocInS3sdkV2 = new UploadDocInS3sdkV2();
				InputStream inputStream = uploadDocInS3sdkV2.getDocumentsFromS3(loggedInUser,
						ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.S3_SCORE_CARD_PATH));
				setDocumentInputStream(inputStream);
				candidateBean.setDocumentFileName(loggedInUser.getUsername() + "_scorecard.pdf");
			} else {
				String documentPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_SCORE_CARD_PATH);
				downloadDocument(documentPath);
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String downloadFlag = request.getParameter("downloadFlag");
			commonService.insertCandidateAuditrail(loggedInUser, "ScoreCard Download",
					"ScoreCard Downloaded by " + loggedInUser.getUsername() + " at " + dateFormat.format(new Date()) + ".");
			logger.info(loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ", ScoreCard Downloaded at " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
			String updtStatus = candidateService.saveScoreCardDownloadStatus(downloadFlag, loggedInUser);
			return "successPDF";
		} catch (Exception e) {
			logger.error("User Id :" + loggedInUser.getUsername() + "Error Message " + e.getMessage());
		}
		return null;
	}

	// TODO 1: CallLetter - completed
	public String printCallLetterPDF() throws Exception {

		try {
			String strSaveInS3 = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SAVE_IN_S3);
			if (strSaveInS3 != null && !Strings.isBlank(strSaveInS3) && "true".equals(strSaveInS3.toLowerCase())) {
				UploadDocInS3sdkV2 uploadDocInS3sdkV2 = new UploadDocInS3sdkV2();
				InputStream inputStream = uploadDocInS3sdkV2.getDocumentsFromS3(loggedInUser,
						ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.S3_CALL_LETTER_PATH));
				setDocumentInputStream(inputStream);
				candidateBean.setDocumentFileName(loggedInUser.getUsername() + "_callletter.pdf");
			} else {
				String documentPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_CALL_LETTER_PATH);
				downloadDocument(documentPath);
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String downloadFlag = request.getParameter("downloadFlag");
			commonService.insertCandidateAuditrail(loggedInUser, "Viva-Voce Call letter Download",
					"Viva-Voce Call letter Downloaded by " + loggedInUser.getUsername() + " at " + dateFormat.format(new Date()) + ".");

			logger.info(loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ", Viva-Voce Call letter Download at "
					+ CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
			String updtStatus = candidateService.saveCallLetterDownloadStatus(downloadFlag, loggedInUser);
			return "successPDF";
		} catch (Exception e) {
			logger.error("User Id :" + loggedInUser.getUsername() + "Error Message " + e.getMessage());
		}
		return null;
	}

	// For printAdmitCardPDF Download changes on 17-04-2019
	// TODO 1: AdmitCard - completed
	public String printAdmitCardPDF() throws Exception {
		try {
			String strSaveInS3 = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SAVE_IN_S3);
			if (strSaveInS3 != null && !Strings.isBlank(strSaveInS3) && "true".equals(strSaveInS3.toLowerCase())) {
				UploadDocInS3sdkV2 uploadDocInS3sdkV2 = new UploadDocInS3sdkV2();
				InputStream inputStream = uploadDocInS3sdkV2.getDocumentsFromS3(loggedInUser,
						ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.S3_ADMIT_CARD_PATH));
				setDocumentInputStream(inputStream);
				candidateBean.setDocumentFileName(loggedInUser.getUsername() + "_admitcard.pdf");
			} else {
				String documentPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_ADMIT_CARD_PATH);
				downloadDocument(documentPath);

			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String downloadFlag = request.getParameter("downloadFlag");
			commonService.insertCandidateAuditrail(loggedInUser, "AdmitCard Download",
					"AdmitCard Downloaded by " + loggedInUser.getUsername() + " at " + dateFormat.format(new Date()) + ".");

			logger.info(loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ", AdmitCard Downloaded at " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
			String updtStatus = candidateService.saveAdmitCardDownloadStatus(downloadFlag, loggedInUser);
			return "successPDF";
		} catch (Exception e) {
			logger.error("User Id :" + loggedInUser.getUsername() + "Error Message " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	// TODO 1: PracticeTest - no change needed
	public String openPracticeTest() throws Exception {
		try {
			String downloadFlag = request.getParameter("downloadFlag");
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			logger.info("openPracticeTest For User ID :" + loggedInUser.getUsername());
			String updtStatus = candidateService.savePracticeTestStatus(downloadFlag, loggedInUser);

			commonService.insertCandidateAuditrail(loggedInUser, "Practice Test Link",
					"Practice Test Link clicked by " + loggedInUser.getUsername() + " at " + dateFormat.format(new Date()) + ".");

			ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger().info(
					loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ", Practice Test Link clicked at " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
		} catch (Exception e) {
			logger.error("User Id :" + loggedInUser.getUsername() + "Error Message " + e.getMessage());
		}
		return null;
	}

	public String printHallTicket() {
		try {
			String downloadPath = null;
			String downloadPathS3 = null;
			Integer status = loggedInUser.getStatusID();
			if (ConfigurationConstants.getInstance().getStatusKey(GenericConstants.HALL_TICKET_GENERATED_FOR_ATTEMPT_1) == status) {
				downloadPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.ADMIT_CARD_LOCATION) + "Written_Test\\";
				downloadPathS3 = "Written_Test";
			} else if (ConfigurationConstants.getInstance().getStatusKey(GenericConstants.CALL_LETTER_FIELD_TEST) == status) {
				downloadPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.ADMIT_CARD_LOCATION) + "Field_Test\\";
				downloadPathS3 = "Field_Test";
			} else if (ConfigurationConstants.getInstance().getStatusKey(GenericConstants.CALL_LETTER_INTERVIEW) == status) {
				downloadPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.ADMIT_CARD_LOCATION) + "Interview\\";
				downloadPathS3 = "Interview";
			} else if (ConfigurationConstants.getInstance().getStatusKey(GenericConstants.CALL_LETTER_MEDICAL) == status) {
				downloadPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.ADMIT_CARD_LOCATION) + "Medical_Test\\";
				downloadPathS3 = "Medical_Test";
			}

			String strSaveInS3 = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SAVE_IN_S3);
			if (strSaveInS3 != null && !Strings.isBlank(strSaveInS3) && "true".equals(strSaveInS3.toLowerCase())) {
				// TODO 1: HallTicket -> need to re check this
				UploadDocInS3sdkV2 uploadDocInS3sdkV2 = new UploadDocInS3sdkV2();
				InputStream inputStream = uploadDocInS3sdkV2.getDocumentsFromS3(loggedInUser, downloadPathS3);
				setDocumentInputStream(inputStream);
				candidateBean.setDocumentFileName(loggedInUser.getUsername() + "_" + downloadPathS3 + ".pdf");
			} else {

				File downloadFilePath = new File(downloadPath);
				String actualFileName = "";
				File[] listOfFiles = downloadFilePath.listFiles();
				if (listOfFiles != null) {
					for (int i = 0; i < listOfFiles.length; i++) {
						if (listOfFiles[i].getName().startsWith(loggedInUser.getUsername())) {
							actualFileName = listOfFiles[i].toString();
							break;
						}
					}
					if (actualFileName != null && !actualFileName.equals("")) {
						downloadDocument(actualFileName);
					}
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					String downloadFlag = request.getParameter("downloadFlag");
					commonService.insertCandidateAuditrail(loggedInUser, "AdmitCard Download",
							"AdmitCard Downloaded by " + loggedInUser.getUsername() + " at " + dateFormat.format(new Date()) + ".");

					logger.info(
							loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ", AdmitCard Downloaded at " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
					// String updtStatus =
					// candidateService.saveAdmitCardDownloadStatus(downloadFlag, loggedInUser);
					return "successPDF";
				} else {
					request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Sorry, No Downloads Available");
					request.setAttribute(GenericConstants.MESSAGE_TYPE, GenericConstants.MESSAGE_TYPE_ERROR);
				}
			}
		} catch (Exception e) {
			logger.error("User Id :" + loggedInUser.getUsername() + "Error Message " + e.getMessage());
		}
		return null;
	}

	private void downloadDocument(String documentPath) {
		File pdfPath = new File(documentPath + File.separator + loggedInUser.getUsername() + ".pdf");
		OutputStream outputStream = null;
		if (pdfPath != null && pdfPath.exists()) {
			try {
				outputStream = response.getOutputStream();
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "attachment; filename=" + loggedInUser.getUsername() + ".pdf");
				response.setHeader("Cache-Control", "no-cache");

				FileInputStream fi = new FileInputStream(pdfPath);
				BufferedInputStream bis = new BufferedInputStream(fi);
				int abyte;
				while ((abyte = bis.read()) != -1) {
					outputStream.write(abyte);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (outputStream != null) {
						outputStream.flush();
						outputStream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

// ################################################################################################################### Candidate Bean Validation Logic
	private String validateCandidateBean() throws Exception {
		String errors = "";
		try {
		//commonService.insertCandidateAuditrail(loggedInUser, "Preview Method Call", "Preview Method is called inside preview valiadtion");
		showFinalPersonalDetails(); // prepopulate
		previewBeanValidator.setLoggedInUser(loggedInUser);
		candidateBean.setLoggedInUser(loggedInUser);
		previewBeanValidator.setCommonService(commonService);
		previewBeanValidator.getPhotoBeanValidator().setUploadPhotoSignInS3sdkV2(uploadPhotoSignInS3sdkV2);
		previewBeanValidator.getSignatureBeanValidator().setUploadPhotoSignInS3sdkV2(uploadPhotoSignInS3sdkV2);
		errors = previewBeanValidator.previewValidation(candidateBean);
		}catch(Exception e) {
			logger.fatal(e,e);
		}
		return errors;
	}

// ################################################################################################################### Pdf Generation Code
//	public String downloadPdf() {
//
//		try {
//			commonService.insertCandidateAuditrail(loggedInUser, "Download", "Candidate with User Id " + loggedInUser.getUsername() + " has clicked on Download.");
//
//			ProcessBuilder pb = new ProcessBuilder("node", "server.js", '"' + loggedInUser.getUsername() + '"');
//			pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
//			pb.redirectError(ProcessBuilder.Redirect.INHERIT);
//			Process p = pb.start();
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		HttpSession session = ServletActionContext.getRequest().getSession();
//		request.setAttribute("JSONSTRING", session.getAttribute("JSONSTRING"));
//
//		return "downloadPdf";
//
//	}

	public String createServerSidePDF() throws Exception {
        try {
            String pdfServerPath = ConfigurationConstants.getInstance()
                    .getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
            MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) request;
            File f1 = new File(pdfServerPath + "\\Bulk_Pdf");
            if (!(f1.exists() && f1.isDirectory())) {
                f1.mkdir();
            }
            if (multiWrapper != null) {
                UploadedFile[] files = multiWrapper.getFiles("serverPdf");
                for (UploadedFile cf : files) {
                    File destFile = new File(pdfServerPath + FileSystems.getDefault().getSeparator() + "\\Bulk_Pdf\\"
                            + FileSystems.getDefault().getSeparator() + loggedInUser.getUsername() + ".pdf");
                    File ogFile = new File(cf.getAbsolutePath());
                    FileUtils.copyFile(ogFile, destFile);
                    FileUtils.deleteQuietly(ogFile);
                }
            }
            request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "success");
            logger.info("Created Server Side Pdf for user :" + loggedInUser.getUsername());
            commonService.insertCandidateAuditrail(loggedInUser, "Server Side PDF", "Server Side PDF generated for " + loggedInUser.getUsername() + ".");
        } catch (Exception e) {
            logger.info("Server Side PDF Generation failed for  " + loggedInUser.getUsername());
            request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "failed");
            commonService.insertCandidateAuditrail(loggedInUser, "Server Side PDF ERROR",
                    "PDF generated Failed for the user " + loggedInUser.getUsername());
            logger.error(e, e);
        }
        return "writePlainText";
    }

    public String navigateToAction() {
        try {
            if (request.getParameter("flag") != null && request.getParameter("flag").equalsIgnoreCase("submit")) {
                logger.info("Downloading Application on Submit for User : " + loggedInUser.getUsername());
                request.setAttribute(DESTINATION_URL, "PaymentOnlineAction_showPaymentScreen.action");
            } else if (request.getParameter("flag") != null && request.getParameter("flag").equalsIgnoreCase("view")) {
                logger.info("Downloading Application on View for User : " + loggedInUser.getUsername());
                request.setAttribute(DESTINATION_URL, "CandidateAction_viewPrintFinalDetails.action");
            } else {
                request.setAttribute(DESTINATION_URL, "CandidateAction_dashboard.action");
            }
        } catch (Exception e) {
            logger.fatal(e, e);
            request.setAttribute(DESTINATION_URL, "CandidateAction_dashboard.action");
        }
        return REDIRECT_ACTION_URL;
    }

	// index_pdf.jsp
	public String getApplicationPDF() {
		String retTypeValue = null;
		try {
			String jsonPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
			// this section is for bulk pdf folder creation
			File bulkFolder = new File(jsonPath + File.separator + "Bulk_Pdf");
			if (!bulkFolder.exists()) {
				bulkFolder.mkdir();
			}
			commonService.insertCandidateAuditrail(loggedInUser, "Final Preview Page - Declaration",
					"Candidate with User Id " + loggedInUser.getUsername() + " : Declaration is succeed.");
			String UserId = loggedInUser.getUsername();
			String strSaveInS3 = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SAVE_IN_S3);
			if (strSaveInS3 != null && !Strings.isBlank(strSaveInS3) && "true".equals(strSaveInS3.toLowerCase())) {
				// update candidate bean
				String viewDataVal = viewPrintFinalDetails();

				// Update Json in S3
				byte[] bytesToWrite = {};
				if (viewDataVal != null && !viewDataVal.equals("") && viewDataVal.equals("showViewPrintDetails")) {
					bytesToWrite = createJson();
				}

				// byte Array to String
				String lPdfString = new String(bytesToWrite);

				request.setAttribute("JSONSTRING", lPdfString);
				retTypeValue = "downloadPdf";
			} else {
				if (null != loggedInUser.getCandidateStatusId() && !loggedInUser.getCandidateStatusId().equals("") && loggedInUser.getCandidateStatusId().equals(10)) {
					candidateBean.setRegistrationId(commonService.getRegistrationId(loggedInUser));
				}
				String jsonPathOnly = jsonPath + UserId + File.separator + UserId + ".json";
				File jsonFile = new File(jsonPathOnly);
				if (jsonFile.exists()) {
					jsonFile.delete();
				}

				String viewDataVal = viewPrintFinalDetails();
				if (viewDataVal != null && !viewDataVal.equals("") && viewDataVal.equals("showViewPrintDetails")) {
					createJson(); // For added JSONData into Table
				}

				Path path = Paths.get(jsonPath + File.separator + UserId + File.separator + UserId + ".json");
				if (Files.exists(path)) {
					String lPdfString = new String(Files.readAllBytes(path));
					if (lPdfString != null && !lPdfString.trim().equals("")) {
						request.setAttribute("JSONSTRING", lPdfString);
						commonService.insertCandidateAuditrail(loggedInUser, "Application PDF Generated",
								"Candidate with User Id " + loggedInUser.getUsername() + " : has generated Application PDF.");
						retTypeValue = "downloadPdf";
					}
				} else {
					request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "File doesn't exist,please contact System Administrator.");
					retTypeValue = "writePlainText";
				}

			}
		} catch (Exception e) {
			logger.error(e,e);
		}
		return retTypeValue;
	}

	private byte[] createJson() {
		byte[] bytesToWrite = {};
		ObjectMapper mapper = new ObjectMapper();
		try {
			String UserId = loggedInUser.getUsername();

			String strSaveInS3 = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SAVE_IN_S3);
			if (strSaveInS3 != null && !Strings.isBlank(strSaveInS3) && "true".equals(strSaveInS3.toLowerCase())) {
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.setSerializationInclusion(Include.NON_NULL);
				bytesToWrite = objectMapper.writeValueAsBytes(candidateBean);
				UploadDocInS3sdkV2 uploadDocInS3sdkV2 = new UploadDocInS3sdkV2();
				uploadDocInS3sdkV2.saveJsonInS3(loggedInUser, candidateBean, bytesToWrite);
			} else {
				String jsonPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
				File jsonFolderPath = new File(jsonPath);
				if (!jsonFolderPath.exists()) {
					jsonFolderPath.mkdirs();
				}
				mapper.setSerializationInclusion(Include.NON_NULL);
				mapper.writeValue(new File(jsonPath + File.separator + UserId + File.separator + UserId + ".json"), candidateBean);
				logger.info("JSON file Generated for Candidate ID "+UserId);
				commonService.insertCandidateAuditrail(loggedInUser, "JSON Save", "JSON file saved for " + loggedInUser.getUsername() + ".");
			}
		} catch (Exception e) {
			logger.error(e,e);
		}
		return bytesToWrite;
	}
	
	public String saveDocVerify() {
		String data = "";
		try {
			data = candidateService.documentVerificationStatus(loggedInUser.getUserFk(), Integer.parseInt(candidateBean.getCandidateDocPk()), candidateBean.getDocumentFileName());
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, data);
		} catch (Exception e) {
			logger.info("Exception in saveDocVerify() CandidateAction for User ID : " + loggedInUser.getUserId()+ e.getMessage());
		}
		return "writePlainText";
	}
	public String getRef1DistrictList() throws Exception {
		Map<Integer, String> ref1DistrictListMap = new LinkedHashMap<Integer, String>();
		String ref1StateId = null;
		String ref1DistrictVal = "";
		List<CommonBean> ref1StateList = new ArrayList<CommonBean>();
		try {
			String ref1StateVal = request.getParameter("ref1StateVal");
			ref1StateId = candidateBean.getAdditionalDetailsBean().getRef1State();
			if (ref1StateVal != null && !ref1StateVal.equals("")) {
				ref1StateId = ref1StateVal;
			} else {
				ref1StateId = "0";
			}
			if (ref1StateId != null && !ref1StateId.equals("") && !ref1StateId.equals("null")) {
				ref1DistrictListMap = ConfigurationConstants.getInstance().getDistrictMap(Integer.parseInt(ref1StateId));
			}

			if (ref1DistrictListMap != null) {
				for (Map.Entry<Integer, String> entry : ref1DistrictListMap.entrySet()) {
					if (entry != null) {
						if (entry.getValue() != null && !entry.getValue().equals("") && entry.getKey() != null
								&& !entry.getKey().equals("")) {
							ref1DistrictVal = ref1DistrictVal + (entry.getValue() + "#" + entry.getKey()) + ",";
						}
					}
				}
			}

			if (ref1DistrictVal != null && !ref1DistrictVal.equals("") && ref1DistrictVal.endsWith(",")) {
				ref1DistrictVal = ref1DistrictVal.substring(0, ref1DistrictVal.length() - 1);
			}
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, ref1DistrictVal);

		} catch (Exception e) {
			logger.fatal(e, e);
		}

		return "writePlainText";
	}
	
	public String getRef2DistrictList() {
		Map<Integer, String> ref2DistrictListMap = new LinkedHashMap<Integer, String>();
		String ref2StateId = null;
		String ref2DistrictVal = "";
		List<CommonBean> ref2StateList = new ArrayList<CommonBean>();
		try {
			String ref2StateVal = request.getParameter("ref2StateVal");
			ref2StateId = candidateBean.getAdditionalDetailsBean().getRef2State();
			if (ref2StateVal != null && !ref2StateVal.equals("")) {
				ref2StateId = ref2StateVal;
			} else {
				ref2StateId = "0";
			}
			if (ref2StateId != null && !ref2StateId.equals("") && !ref2StateId.equals("null")) {
				ref2DistrictListMap = ConfigurationConstants.getInstance().getDistrictMap(Integer.parseInt(ref2StateId));
			}

			if (ref2DistrictListMap != null) {
				for (Map.Entry<Integer, String> entry : ref2DistrictListMap.entrySet()) {
					if (entry != null) {
						if (entry.getValue() != null && !entry.getValue().equals("") && entry.getKey() != null
								&& !entry.getKey().equals("")) {
							ref2DistrictVal = ref2DistrictVal + (entry.getValue() + "#" + entry.getKey()) + ",";
						}
					}
				}
			}

			if (ref2DistrictVal != null && !ref2DistrictVal.equals("") && ref2DistrictVal.endsWith(",")) {
				ref2DistrictVal = ref2DistrictVal.substring(0, ref2DistrictVal.length() - 1);
			}
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, ref2DistrictVal);

		} catch (Exception e) {
			logger.fatal(e, e);
		}

		return "writePlainText";
	}
	
	public String saveCandidateAdditionalDetails() throws LogicError, Exception {

		String result = null;
		String serverSideErrors = "";
		/*if(loggedInUser.getEditFlag()) {
		 candidateBean.setEditBtnFlag(true);
		}*/
		// For Validation
		//serverSideErrors = saveCandidateAdditionalDetailsValidation();
		AdditionalBeanValidator beanValidation = new AdditionalBeanValidator();
		beanValidation.setCandidateBean(candidateBean);
				 serverSideErrors = beanValidation.validateAdditionalDetails(candidateBean, loggedInUser);

		StringBuilder severSideErrorsAppend = new StringBuilder();
		if (serverSideErrors != null && !serverSideErrors.equals("")) {
			if (serverSideErrors != null && !serverSideErrors.equals("")) {
				severSideErrorsAppend.append(serverSideErrors);
			}
			populateAdditionalDetails();
			addActionMessage(severSideErrorsAppend.toString());
			candidateBean.setStatusID(candidateBean.getCandidateStatusId());
			
			Map<Integer, String> advertList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Advert_List");
			Map<Integer, String> advertMap = new TreeMap<Integer, String>(advertList);
			candidateBean.getAdditionalDetailsBean().setAdvertList(advertMap);
			Map<Integer, String> yesNo = ConfigurationConstants.getInstance().getParameterMapForDomainName("Yes_No_Map");
			Map<Integer, String> yesNoMap = new TreeMap<Integer, String>(yesNo);
			candidateBean.getAdditionalDetailsBean().setYesNo(yesNoMap);
			candidateBean.getAdditionalDetailsBean().setRefStateList(ConfigurationConstants.getInstance().getStateMap(1));
			
			if (candidateBean.getAdditionalDetailsBean().getRef1State() != null && !candidateBean.getAdditionalDetailsBean().getRef1State().equals("")) {
				candidateBean.setRef1DistrictListMap(ConfigurationConstants.getInstance().getDistrictMap(Integer.parseInt(candidateBean.getAdditionalDetailsBean().getRef1State())));
				Map<Integer, String> districtMap = ConfigurationConstants.getInstance().getDistrictMap(Integer.parseInt(candidateBean.getAdditionalDetailsBean().getRef1State()));
				candidateBean.setRef1DistrictList(districtMap);
			}
			if (candidateBean.getAdditionalDetailsBean().getRef2State() != null && !candidateBean.getAdditionalDetailsBean().getRef2State().equals("")) {
				candidateBean.setRef2DistrictListMap(ConfigurationConstants.getInstance().getDistrictMap(Integer.parseInt(candidateBean.getAdditionalDetailsBean().getRef2State())));
				Map<Integer, String> ref2DistrictMap = ConfigurationConstants.getInstance().getDistrictMap(Integer.parseInt(candidateBean.getAdditionalDetailsBean().getRef2State()));
				candidateBean.setRef2DistrictList(ref2DistrictMap);
			}		
			
			List<Integer> yearList = new ArrayList<Integer>();
			String dob = loggedInUser.getDate_Of_Birth();
			String year[] = dob.split("-");
			int start = Integer.parseInt(year[2])+10;
			int end = 2023;
			while(start <= end) {
				yearList.add(start++);
			}
			candidateBean.setYearsAppliedList(yearList);
			
			String selectedYears = candidateBean.getAdditionalDetailsBean().getYearsOfApply();
		    String yearsSelectedList[] = selectedYears.split(", ");
		    List<String> yearsList = new ArrayList<String>(yearsSelectedList.length);
		    for(int i=0; i<yearsSelectedList.length; i++) {
		    	yearsList.add(yearsSelectedList[i]);
		    }
		    candidateBean.getAdditionalDetailsBean().setYearsOfApplyList(yearsList);

			candidateBean.setAdditionalDetailsBean(candidateBean.getAdditionalDetailsBean());
			
			result = "additionalDetailsForm";
		} else {
			// Start: Save Candidate Data IN DB
			try {
				
				ErrorCodeEnum serviceresult = candidateAdditionalDetailsService.saveCandidateAdditionalDetails(candidateBean, loggedInUser, sessionAttributes, request);

				switch (serviceresult) {
				case UPDATE_CANDIDATE_STAGE:
					result = updateCandidateStage();
					break;
				case ERROR:
					result = ERROR;
					logger.error("Error ocurred while saving candidate's additional details.");
					break;
				default:
					throw new LogicError("Error ocurred while saving candidate's additional details.");
				}
			} catch (Exception e) {
				logger.info("Excepton occured while saving Additional details transaction rolled back for User ID: " + loggedInUser.getUsername());
				logger.fatal(e, e);
				addActionMessage("Error Occured while saving Additional Details. Please try again!");
				populateAdditionalDetails();
				result = "additionalDetailsForm";
			}
		}

		if (request.getAttribute("menuKey") != null) {
			String mandatory = ConfigurationConstants.getInstance().getMenuKeyMandatory(request.getAttribute("menuKey").toString());
			if (mandatory != null && mandatory.equals(GenericConstants.MANDATORY)) {
				candidateBean.setCandiateDetailsMandatory(true);
			}
		}
		displayMenus();
		return result;

	}
	
	
	private void populateAdditionalDetails() {
		// TODO Auto-generated method stub
		Map<Integer, String> advertList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Advert_List");
		Map<Integer, String> advertMap = new TreeMap<Integer, String>(advertList);
		candidateBean.setAdvertList(advertMap);
		Map<Integer, String> yesNo = ConfigurationConstants.getInstance().getParameterMapForDomainName("Yes_No_Map");
		Map<Integer, String> yesNoMap = new TreeMap<Integer, String>(yesNo);
		candidateBean.setYesNo(yesNoMap);
		candidateBean.setRefStateList(ConfigurationConstants.getInstance().getStateMap(1));
	}

	// for gender confirmation audit
	public String auditTrailForGenderConfirmation() {
		Users users = (Users) sessionAttributes.get(SESSION_USER);
		if (users == null || users.getUsername().equals("")) {
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Session has expired. Please login again.");
			return "writePlainText";
		}
		loggedInUser.setRemoteIp(request.getRemoteAddr());
		int insetCnt = 0;
		String audittrail = "";
		String genderval = request.getParameter("genderval");
		String btnVal = request.getParameter("btnVal");
		String actionAudit = request.getParameter("actionAudit");
		audittrail = "Candidate with User ID " + loggedInUser.getUsername() + " " + actionAudit + " selected as " + genderval + ", button clicked as " + btnVal;
		try {
			insetCnt = commonService.insertCandidateAuditrail(loggedInUser, "Personal Page - " + actionAudit, audittrail);
		} catch (Exception e) {
			logger.info("Exception in gender confirmation audit trail for User ID : " + loggedInUser.getUsername());
			logger.fatal(e, e);
		}
		request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, actionAudit);
		return "writePlainText";
	}
	
	public String createServerSidePaymentPdf() throws Exception {
		try {
			logger.info("Create Server Side Payment Pdf Initiated for user :" + loggedInUser.getUsername());
            String pdfServerPath = ConfigurationConstants.getInstance()
                    .getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
            MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) request;
            String transactionId = request.getParameter("transactionId");
            
            if (multiWrapper != null) {
                UploadedFile[] files = multiWrapper.getFiles("serverPdf");
                for (UploadedFile cf : files) {
                    File destFile = new File(pdfServerPath + File.separator + loggedInUser.getUsername() + File.separator + loggedInUser.getUsername() + "_" + transactionId + "_payment.pdf");
                    File ogFile = new File(cf.getAbsolutePath());
                    FileUtils.copyFile(ogFile, destFile);
                    FileUtils.deleteQuietly(ogFile);
                }
            }
            request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "success");
            logger.info("Created Server Side Payment Pdf for user :" + loggedInUser.getUsername());
            commonService.insertCandidateAuditrail(loggedInUser, "Server Side Payment PDF", "Server Side PDF generated for " + loggedInUser.getUsername() + ".");
        } catch (Exception e) {
            logger.info("Server Side PDF Generation failed for  " + loggedInUser.getUsername());
            request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "failed");
            commonService.insertCandidateAuditrail(loggedInUser, "Server Side Payment Pdf ERROR",
                    "Payment Pdf generated Failed for the user " + loggedInUser.getUsername());
            logger.fatal(e, e);
        }
        return "writePlainText";
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