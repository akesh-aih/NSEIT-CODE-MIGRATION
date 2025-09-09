package com.nseit.generic.action;

import static com.nseit.generic.util.GenericConstants.DESTINATION_URL;
import static com.nseit.generic.util.GenericConstants.GLOBAL_PLAIN_TEXT_MESSAGE;
import static com.nseit.generic.util.GenericConstants.REDIRECT_ACTION_URL;
import static com.nseit.generic.util.GenericConstants.SESSION_USER;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.dispatcher.HttpParameters;

import com.nseit.generic.models.CovidDutyCertificateBean;

import com.nseit.generic.models.Users;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.CovidDutyCertDetailsBean;
import com.nseit.generic.service.CandidateService;
import com.nseit.generic.service.CommonService;
import com.nseit.generic.service.CovidDutyCertificateService;
import com.nseit.generic.util.AuditTrailConstants;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.aware.UserAware;
import com.nseit.generic.util.validation.CovidDutyCertificateValidator;
import com.opensymphony.xwork2.ModelDriven;

import lombok.Getter;
import lombok.Setter;

public class CovidDutyCertificateAction extends BaseAction implements ModelDriven<CovidDutyCertificateBean>, UserAware {

	private Logger logger = LoggerHome.getLogger(getClass());
	private CovidDutyCertificateBean covidDutyCertificateBean = new CovidDutyCertificateBean();
	private CovidDutyCertificateService covidDutyCertificateService;
	private CandidateService candidateService;
	private CandidateBean candidateBean = new CandidateBean();
	private CommonService commonService;
	@Getter
	@Setter
	private CovidDutyCertificateValidator covidDutyCertificateValidator;

	public void setCovidDutyCertificateService(CovidDutyCertificateService covidDutyCertificateService) {
		this.covidDutyCertificateService = covidDutyCertificateService;
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

	public CovidDutyCertificateBean getModel() {
		return covidDutyCertificateBean;
	}

	@Override
	public void resetModel() {
		covidDutyCertificateBean = new CovidDutyCertificateBean();
	}

	public CovidDutyCertificateAction() {
		displayMenus();
	}

	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	@Override
	public void setLoggedInUser(Users users) {
		this.loggedInUser = users;
	}

	public String getCovidDutyCertificate() throws Exception {
		Map<Integer, List<Long>> dateWindowMap = ConfigurationConstants.getInstance().getDateWindowMap();
		List<Long> dateList = dateWindowMap.get(1);
		Long startDate = dateList.get(0);
		Long endDate = dateList.get(1);
		long today = ZonedDateTime.now().toInstant().toEpochMilli();
		candidateBean.setMaxDate(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_DATE_FOR_DATEPICKER));

		if ((today == startDate || today > startDate) && (today == endDate || today < endDate)) {
			try {
				List<CovidDutyCertDetailsBean> covidDutyCertFinalLst = new ArrayList<CovidDutyCertDetailsBean>();
				List<CovidDutyCertDetailsBean> covidDutyCertLst = covidDutyCertificateService.getCovidDutyCertiDetails(loggedInUser.getUserId());
				if (covidDutyCertLst != null && covidDutyCertLst.size() >= 1) {
					covidDutyCertificateBean.setCovidDutyCertDetailsList(covidDutyCertLst);
					covidDutyCertificateBean.setYearOfTotalService(covidDutyCertLst.get(0).getYearExp());
					covidDutyCertificateBean.setMonthOfTotalService(covidDutyCertLst.get(0).getMonthExp());
					covidDutyCertificateBean.setDayOfTotalService(covidDutyCertLst.get(0).getExpierence());
					// covidDutyCertificateBean.setYesNo(covidDutyCertLst.get(0).getYesNo());
					covidDutyCertificateBean.setDataFound(true);
					CovidDutyCertificateBean covidDutyCertAddDetailsBean = covidDutyCertificateService.getCovidDutyCertiAddDetails(loggedInUser);
					if (covidDutyCertAddDetailsBean != null) {
						covidDutyCertificateBean.setYearOfTotalService(covidDutyCertAddDetailsBean.getYearOfTotalService());
						covidDutyCertificateBean.setMonthOfTotalService(covidDutyCertAddDetailsBean.getMonthOfTotalService());
						covidDutyCertificateBean.setDayOfTotalService(covidDutyCertAddDetailsBean.getDayOfTotalService());
						// For Validation
						covidDutyCertificateBean.setYearOfTotalServiceHidden(covidDutyCertAddDetailsBean.getYearOfTotalService());
						covidDutyCertificateBean.setMonthOfTotalServiceHidden(covidDutyCertAddDetailsBean.getMonthOfTotalService());
						covidDutyCertificateBean.setDayOfTotalServiceHidden(covidDutyCertAddDetailsBean.getDayOfTotalService());
						covidDutyCertificateBean.setYesNo(covidDutyCertAddDetailsBean.getYesNo());
					}
				} else {
					int COVID_DUTY_CERTIFICATE_COUNT = 1;
					for (int i = 0; i < COVID_DUTY_CERTIFICATE_COUNT; i++) {
						CovidDutyCertDetailsBean covidDutyCertDetailsBean = new CovidDutyCertDetailsBean();
						// covidDutyCertDetailsBean.setYesNo(covidDutyCertDetailsBean.getYesNo());
						covidDutyCertFinalLst.add(covidDutyCertDetailsBean);
					}
					CovidDutyCertificateBean covidDutyCertAddDetailsBean = covidDutyCertificateService.getCovidDutyCertiAddDetails(loggedInUser);
					covidDutyCertificateBean.setCovidDutyCertDetailsList(covidDutyCertFinalLst);
					if (covidDutyCertAddDetailsBean != null) {
						covidDutyCertificateBean.setYesNo(covidDutyCertAddDetailsBean.getYesNo());
					}

				}
				getBaseDataForCovidDutyCertificateDetails();
			} catch (Exception e) {
				logger.error(e, e);
			}
			displayMenus();
			commonService.insertCandidateAuditrail(loggedInUser, "Covid Duty Certificate Page - Loaded",
					"Candidate Id : " + loggedInUser.getUsername() + " ~ Covid Duty Certificate Page : Loaded ");
			return "covidDutyCertificate";

		} else {
			// Used for displaying closure date in closewindow.jsp
			candidateBean.setRegStrtDate(CommonUtil.formatDate(new Date(startDate), GenericConstants.DATE_FORMAT_DEFAULT1));
			String newEndDate = CommonUtil.formatDate(new Date(endDate), GenericConstants.DATE_FORMAT_DEFAULT1);
			String[] endDateFinal = newEndDate.split(" ");
			candidateBean.setRegEndDate("at " + endDateFinal[1] + " hrs on " + endDateFinal[0] + ".");
			candidateBean.setDisplayRegFlag("true");
			return "CloseWindow";
		}
	}

	public String updateCandidateStage() throws Exception {
		String result = REDIRECT_ACTION_URL;
		HttpSession session = request.getSession();
		Users users = (Users) sessionAttributes.get(SESSION_USER);
		String flagValue = request.getParameter("isDataFound");

		int stageUpdate = 0;

		String currentMenuKey = request.getParameter("menuKey");
		if (currentMenuKey.equals("")) {
			currentMenuKey = "2.40";
		}
		String nextMenuKey = ConfigurationConstants.getInstance().getNextStageForMenuKey(currentMenuKey);
		String actionUrl = ConfigurationConstants.getInstance().getActionUrl(nextMenuKey);
		String mandatory = ConfigurationConstants.getInstance().getMenuKeyMandatory(currentMenuKey);

		if (currentMenuKey.equals(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.REGISTER_CONFIRMATION))) {
			int updateStage = commonService.updateCandidateStatus(loggedInUser, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_CONFIRMATION));
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
				users.setCandidateStatusId(ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED));

				if (ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.SELECT_TEST_CENTER_PREFERENCE).equals("A")) {
				}
				Integer disciplineId = 0;
				if (loggedInUser.getDisciplineID() != null) {
					disciplineId = Integer.parseInt(loggedInUser.getDisciplineID());
				}
				ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger().info(loggedInUser.getUsername() + ", " + request.getRemoteAddr()
						+ ", Submitted Application Form , " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
				String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED) + "";
				Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.REGISTRATION_SUBMITED);
				Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.REGISTRATION_SUBMITED);
			}
		}

		if ((mandatory != null && mandatory.equals("1"))) {
			if ((flagValue == null && flagValue == "") || flagValue.equals("false")) {
				request.setAttribute("dataNotFound", "This is Mandatory Stage");
				actionUrl = ConfigurationConstants.getInstance().getActionUrl(nextMenuKey);
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
		loggedInUser.setRemoteIp(request.getRemoteAddr());
		sessionAttributes.put(SESSION_USER, users);
		request.setAttribute(DESTINATION_URL, actionUrl);

		return result;
	}

	private void getBaseDataForCovidDutyCertificateDetails() throws Exception {
		try {
			Map<Integer, String> institutionType = ConfigurationConstants.getInstance().getParameterMapForDomainName("List_of_InstitutionType");
			Map<Integer, String> institutionTypeMap = new TreeMap<Integer, String>(institutionType);
			covidDutyCertificateBean.setInstitutionTypeMap(institutionTypeMap);

			Map<Integer, String> yesNo = ConfigurationConstants.getInstance().getParameterMapForDomainName("Yes_No_Map");
			Map<Integer, String> yesNoMap = new TreeMap<Integer, String>(yesNo);
			covidDutyCertificateBean.setYesNoMap(yesNoMap);

			Map<Integer, String> districtList = ConfigurationConstants.getInstance().getDistrictMap(1);
			Map<Integer, String> districtListMap = new TreeMap<Integer, String>(districtList);
			covidDutyCertificateBean.setDistrictList(districtListMap);

		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	public String saveCovidDutyCertificateDetails() throws Exception {
		String result = null;
		try {
			result = "covidDutyCertificate";
			String serverSideCovidDutyCertiDetailsErrors = "";
			StringBuffer newaudit = new StringBuffer();
			if (covidDutyCertificateBean.getYesNo().equals("7") || covidDutyCertificateBean.getYesNo().equalsIgnoreCase("no")) {
				List<CovidDutyCertDetailsBean> covidDutyCertDetailsList = covidDutyCertificateBean.getCovidDutyCertDetailsList();
				createAudittrail(AuditTrailConstants.RELEVANT_EXP,covidDutyCertificateBean.getYesNo(),newaudit,false);
				String saveResult = covidDutyCertificateService.saveCovidDutyCertificateDetails(covidDutyCertDetailsList, loggedInUser, covidDutyCertificateBean);
				
				if (GenericConstants.SAVE_SUCCESS.equals(saveResult)) {
					loggedInUser.setCovidDutyCertificate(covidDutyCertificateBean.getYesNo());
					commonService.insertCandidateAuditrail(loggedInUser, "Covid Duty Certificate Details Page - Save & Continue", newaudit.toString());
					result = updateCandidateStage();
				} else {
					logger.info(loggedInUser.getUsername() + ", " + request.getRemoteAddr()
							+ ", Covid Duty Certificate Details. Redirected to Covid Duty Certificate page again, " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
					getBaseDataForCovidDutyCertificateDetails();
					addActionMessage("<li>An error occured while saving your data. Please try again after some time.</li>");
					covidDutyCertificateBean.setServerSideErrorMessage("true");
					result = "covidDutyCertificate";
				}
			} else {
				serverSideCovidDutyCertiDetailsErrors = covidDutyCertificateValidator.validateCovidDutyCert(candidateBean, loggedInUser, covidDutyCertificateBean, "form");

				if ((serverSideCovidDutyCertiDetailsErrors != null && !serverSideCovidDutyCertiDetailsErrors.equals(""))) {
					addActionMessage(serverSideCovidDutyCertiDetailsErrors);
					getBaseDataForCovidDutyCertificateDetails();
					result = "covidDutyCertificate";
					covidDutyCertificateBean.setServerSideErrorMessage("true");
				} else {

					covidDutyCertificateBean.setDataFound(true);
					List<CovidDutyCertDetailsBean> covidDutyCertDetailsList = covidDutyCertificateBean.getCovidDutyCertDetailsList();
					String saveResult = covidDutyCertificateService.saveCovidDutyCertificateDetails(covidDutyCertDetailsList, loggedInUser, covidDutyCertificateBean);
					if (GenericConstants.SAVE_SUCCESS.equals(saveResult)) {
						//StringBuffer covidDataStr = new StringBuffer();
						//covidDataStr.append(" Have you worked in covid period : " + covidDutyCertificateBean.getYesNo() + " ");
						int i = 1;
						if (covidDutyCertDetailsList != null && !covidDutyCertDetailsList.isEmpty()) {
							for (CovidDutyCertDetailsBean covidDutyCertDetailsBean : covidDutyCertDetailsList) {
								if (covidDutyCertDetailsBean != null) {
									
									createAudittrail(AuditTrailConstants.RELEVANT_EXP,covidDutyCertificateBean.getYesNo(),newaudit,true);
									createAudittrail(AuditTrailConstants.INSTITUTION_TYPE,covidDutyCertDetailsBean.getInstitutionType(),newaudit,true);
									createAudittrail(AuditTrailConstants.NAME_OF_MED_INST,covidDutyCertDetailsBean.getNameOfMedInstitution(),newaudit,true);
									createAudittrail(AuditTrailConstants.DISTRICT,covidDutyCertDetailsBean.getDistrictVal(),newaudit,true);
									createAudittrail(AuditTrailConstants.ADDRESS_OF_INST,covidDutyCertDetailsBean.getAddressOfInstitute(),newaudit,true);
									createAudittrail(AuditTrailConstants.PERIOD_OF_WORK_FROM,covidDutyCertDetailsBean.getFromYear(),newaudit,true);
									createAudittrail(AuditTrailConstants.PERIOD_OF_WORK_TO,covidDutyCertDetailsBean.getToYear(),newaudit,true);
									createAudittrail(AuditTrailConstants.DURATION_OF_COVID_SERV,covidDutyCertDetailsBean.getDurationOfCovidService(),newaudit,true);
									createAudittrail(AuditTrailConstants.CERTI_SIGNED_BY,covidDutyCertDetailsBean.getCertificateSignedBy(),newaudit,true);
									createAudittrail(AuditTrailConstants.CERTI_COUNTER_SIGNED_BY,covidDutyCertDetailsBean.getCertiCounterSignedBy(),newaudit,true);
									createAudittrail(AuditTrailConstants.YEAR_OF_TOTAL_SERVICE,covidDutyCertificateBean.getYearOfTotalService(),newaudit,true);
									createAudittrail(AuditTrailConstants.MONTH_OF_TOTAL_SERVICE,covidDutyCertificateBean.getMonthOfTotalService(),newaudit,true);
									createAudittrail(AuditTrailConstants.DAY_OF_TOTAL_SERVICE,covidDutyCertificateBean.getDayOfTotalService(),newaudit,true);
									String totalwrkexp = (covidDutyCertificateBean.getYearOfTotalService() + " years " + covidDutyCertificateBean.getMonthOfTotalService() + " months " + covidDutyCertificateBean.getDayOfTotalService() + " days");
									createAudittrail(AuditTrailConstants.TOTAL_WRK_EXP,totalwrkexp,newaudit,false);
									/*covidDataStr.append("** Relevant experience: " + i + "** " + " || Institution Type : " + covidDutyCertDetailsBean.getInstitutionType()
											+ " || Name of the Medical Institution : " + covidDutyCertDetailsBean.getNameOfMedInstitution() + " || District : "
											+ covidDutyCertDetailsBean.getDistrictVal() + " || Address of the Institution : " + covidDutyCertDetailsBean.getAddressOfInstitute()
											+ " || Period of Work-From : " + covidDutyCertDetailsBean.getFromYear() + " || Period of Work-To : "
											+ covidDutyCertDetailsBean.getToYear() + " || Duration of Covid Service : " + covidDutyCertDetailsBean.getDurationOfCovidService()
											+ " || Certificate Signed by : " + covidDutyCertDetailsBean.getCertificateSignedBy() + " || Certificate Counter Signed by : "
											+ covidDutyCertDetailsBean.getCertiCounterSignedBy());*/

									i++;
								}
							}
						}
						logger.info(loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ", Covid Duty Certificate Details saved successfully, "
								+ CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
						covidDutyCertificateBean.setSaveFlag("true");
						loggedInUser.setCovidDutyCertificate(covidDutyCertificateBean.getYesNo());
						commonService.insertCandidateAuditrail(loggedInUser, "Covid Duty Certificate Details Page - Save & Continue", newaudit.toString());
						result = updateCandidateStage();
					} else {
						logger.info(loggedInUser.getUsername() + ", " + request.getRemoteAddr()
								+ ", Unable to save Covid Duty Certificate Details. redirected to Covid Duty Certificate Details page again, "
								+ CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
						getBaseDataForCovidDutyCertificateDetails();
						addActionMessage("<li>An Error occured while saving your data..!! Please try again after some time.</li>");
						covidDutyCertificateBean.setServerSideErrorMessage("true");
						result = "covidDutyCertificate";
					}
				}
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		displayMenus();
		return result;
	}

	public String displaySignedBy() throws Exception {
		String result = null;
		String optionSelected = request.getParameter("institutionType");
		if (StringUtils.isNotBlank(optionSelected)) {
			result = ConfigurationConstants.getInstance().getSignedByDataMap().get(Integer.parseInt(optionSelected));
		}
		request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, result);
		return "writePlainText";
	}
	
	private void createAudittrail(String key, String value, StringBuffer result, boolean concat) {
		result.append(key);
		result.append(":");
		result.append(value);
		if (concat) {
			result.append("~");
		}
	}

}
