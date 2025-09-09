package com.nseit.generic.action;

import static com.nseit.generic.util.GenericConstants.DESTINATION_URL;
import static com.nseit.generic.util.GenericConstants.GLOBAL_PLAIN_TEXT_MESSAGE;
import static com.nseit.generic.util.GenericConstants.REDIRECT_ACTION_URL;
import static com.nseit.generic.util.GenericConstants.SESSION_USER;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.dispatcher.HttpParameters;

import com.nseit.generic.dao.CandidateDao;
import com.nseit.generic.dao.CommonDao;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.EducationDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.models.WorkExperienceBean;
import com.nseit.generic.models.WorkExperienceDetailsBean;
import com.nseit.generic.service.CandidatePersonalDetailsService;
import com.nseit.generic.service.CandidateService;
import com.nseit.generic.service.CommonService;
import com.nseit.generic.service.WorkExperienceService;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.ValidatorUtil;
import com.nseit.generic.util.aware.UserAware;
import com.nseit.generic.util.validation.PersonalBeanValidator;
import com.nseit.generic.util.validation.WorkExperienceValidator;
import com.opensymphony.xwork2.ModelDriven;

import lombok.Getter;
import lombok.Setter;

public class WorkExperienceAction extends BaseAction implements ModelDriven<WorkExperienceBean>, UserAware {

	private static final long serialVersionUID = -7883478322664991439L;
	private Logger logger = LoggerHome.getLogger(getClass());

	private WorkExperienceBean workExperienceBean = new WorkExperienceBean();

	private CandidatePersonalDetailsService candidatePersonalDetailsService;
	private WorkExperienceService workExperienceService;
	
	@Getter
	@Setter
	private WorkExperienceValidator workExperienceValidator;
	
	public void setWorkExperienceService(WorkExperienceService workExperienceService) {
		this.workExperienceService = workExperienceService;
	}

	public void setCandidatePersonalDetailsService(CandidatePersonalDetailsService candidatePersonalDetailsService) {
		this.candidatePersonalDetailsService = candidatePersonalDetailsService;
	}

	private CandidateService candidateService;
	private CandidateBean candidateBean = new CandidateBean();
	private CommonService commonService;
	private CommonDao commonDao;
	private CandidateDao candidateDao;

	public WorkExperienceAction() {
		displayMenus();
	}

	public WorkExperienceBean getModel() {
		return workExperienceBean;
	}

	@Override
	public void resetModel() {
		workExperienceBean = new WorkExperienceBean();
	}

	@Override
	public void setLoggedInUser(Users users) {
		this.loggedInUser = users;
	}

	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public String updateCandidateStage() throws Exception {
		String result = REDIRECT_ACTION_URL;
		HttpSession session = request.getSession();
		Users users = (Users) sessionAttributes.get(SESSION_USER);
		String flagValue = request.getParameter("isDataFound");

		int stageUpdate = 0;

		String currentMenuKey = request.getParameter("menuKey");
		if (currentMenuKey.equals("")) {
			currentMenuKey = "2.31";
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

	public String getUnit() throws Exception {
		String unit = workExperienceService.getUnit(request, workExperienceBean);
		request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, unit);
		return "writePlainText";
	}

	public String getDistrict() throws Exception {
		String district = workExperienceService.getDistrict(workExperienceBean);
		request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, district);
		return "writePlainText";
	}

	public String getPoliceStationList() throws Exception {
		String PresentPostingUnit = workExperienceService.getPoliceStationList(workExperienceBean);
		request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, PresentPostingUnit);
		return "writePlainText";
	}
	
	public String getWorkExperiencePage() throws Exception {
		try {
			List<WorkExperienceDetailsBean> workExpFinalLst = new ArrayList<WorkExperienceDetailsBean>();
			workExperienceBean.setDisciplineId(loggedInUser.getDisciplineID());
			List<WorkExperienceDetailsBean> workExpLst = workExperienceService.getWorkExperienceDetailsForCandidate(loggedInUser.getUserId());
			if (workExpLst != null && workExpLst.size() >= 1) {
				workExperienceBean.setWorkExperienceDetailsList(workExpLst);
				workExperienceBean.setYearOfExperience(workExpLst.get(0).getYearExp());
				workExperienceBean.setMonthOfExperience(workExpLst.get(0).getMonthExp());
				workExperienceBean.setDayOfExperience(workExpLst.get(0).getExpierence());
				workExperienceBean.setDataFound(true);
				WorkExperienceBean workExpAddDetailsBean = workExperienceService.getWorkExpAddDetails(loggedInUser);
				if (workExpAddDetailsBean != null) {
					workExperienceBean.setYearOfExperience(workExpAddDetailsBean.getYearOfExperience());
					workExperienceBean.setMonthOfExperience(workExpAddDetailsBean.getMonthOfExperience());
					workExperienceBean.setDayOfExperience(workExpAddDetailsBean.getDayOfExperience());
					// For Validation
					workExperienceBean.setYearOfExperienceHidden(workExpAddDetailsBean.getYearOfExperience());
					workExperienceBean.setMonthOfExperienceHidden(workExpAddDetailsBean.getMonthOfExperience());
					workExperienceBean.setDayOfExperienceHidden(workExpAddDetailsBean.getDayOfExperience());
					workExperienceBean.setYesNo(workExpAddDetailsBean.getYesNo());
					workExperienceBean.setSponsor(workExpAddDetailsBean.getSponsor());
				}
			} else {
				int WORK_EXP_COUNT = Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.WORK_EXP_COUNT));
				for (int i = 0; i < WORK_EXP_COUNT; i++) {
					WorkExperienceDetailsBean workExperienceDetailsBean = new WorkExperienceDetailsBean();
					workExpFinalLst.add(workExperienceDetailsBean);
				}
				WorkExperienceBean workExpAddDetailsBean = workExperienceService.getWorkExpAddDetails(loggedInUser);
				workExperienceBean.setWorkExperienceDetailsList(workExpFinalLst);
				if (workExpAddDetailsBean != null) {
					workExperienceBean.setYesNo(workExpAddDetailsBean.getYesNo());
					workExperienceBean.setSponsor(workExpAddDetailsBean.getSponsor());
				}
			}
			String disciplineId = loggedInUser.getDisciplineID();
			if (disciplineId != null && !disciplineId.equals("")) {
				workExperienceBean.setDisciplineId(disciplineId);
			}
			getBaseDataForWorkExperienceDetails();
		} catch (Exception e) {
			logger.error(e, e);
		}
		displayMenus();
		return "workExperience";
	}
	
	private void getBaseDataForWorkExperienceDetails() throws Exception {
		try {
			Map<Integer, String> yesNo = ConfigurationConstants.getInstance().getParameterMapForDomainName("Yes_No_Map");
			Map<Integer, String> yesNoMap = new TreeMap<Integer, String>(yesNo);
			workExperienceBean.setYesNoMap(yesNoMap);
			
			List<EducationDetailsBean> candidateEducationalDetails = null;
			candidateEducationalDetails = candidateService.getCandiateAcademicDetails(candidateBean,loggedInUser);
			String dateofpassing = null;
			if (!candidateEducationalDetails.isEmpty()) {
				dateofpassing = candidateEducationalDetails.get(0).getYearOfPassing();
			}
			if (dateofpassing != null) {
				String[] startDate = dateofpassing.split(" ");
				int startDate1 = Integer.parseInt(startDate[1]);
				workExperienceBean.setFirstDate(startDate1);
				workExperienceBean.setEduDate(startDate[0] + " 01," + startDate[1]);
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	public String saveWorkExperienceDetails() throws Exception {
		String result = null;
		try {
			result = "workExperience";
			String serverSideWorkExperienceDetailsErrors = "";
			//serverSideWorkExperienceDetailsErrors = saveWorkExperienceDetailsValidation();
			serverSideWorkExperienceDetailsErrors = workExperienceValidator.validateWorkExp(candidateBean,
					loggedInUser,workExperienceBean,"form");
			if ((serverSideWorkExperienceDetailsErrors != null && !serverSideWorkExperienceDetailsErrors.equals(""))) {
				addActionMessage(serverSideWorkExperienceDetailsErrors);
				getBaseDataForWorkExperienceDetails();
				result = "workExperience";
				workExperienceBean.setServerSideErrorMessage("true");
			} else {
				workExperienceBean.setDataFound(true);
				List<WorkExperienceDetailsBean> workExperienceDetailsList = workExperienceBean.getWorkExperienceDetailsList();
				String saveResult = workExperienceService.saveWorkExperienceDetails(workExperienceDetailsList, loggedInUser, workExperienceBean);
				if (GenericConstants.SAVE_SUCCESS.equals(saveResult)) {
					StringBuffer eduDataStr = new StringBuffer();
					eduDataStr.append(" Do you have Post Qualification work experience? : " + workExperienceBean.getYesNo() + " ");
					eduDataStr.append(" Will you be sponsored by your employer for the programme? : " + workExperienceBean.getSponsor() + " ");
					int i = 1;
					if (workExperienceDetailsList != null && !workExperienceDetailsList.isEmpty()) {
						for (WorkExperienceDetailsBean workExperienceDetailsBean : workExperienceDetailsList) {
							if (workExperienceDetailsBean != null) {
								eduDataStr.append("** Relevant experience: " + i + "** " + "Organisation Name : " + workExperienceDetailsBean.getArea() + " || Job Profile : "
										+ workExperienceDetailsBean.getJobRole() + " || Date of Joining : " + workExperienceDetailsBean.getFromYear()
										+ " || Is this your current organisation : " + workExperienceDetailsBean.getYesNoA() + " || Date of Leaving : "
										+ workExperienceDetailsBean.getToYear() + " || Duration of Experience : " + workExperienceDetailsBean.getWorkduration()
										+ " || Last Drawn Salary (per annum) : " + workExperienceDetailsBean.getLastSalary());
								i++;
							}
						}
						commonService.insertCandidateAuditrail(loggedInUser, "Work Experience Details : ", eduDataStr.toString());
					}
					logger.info(loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ", Work Experience Details saved successfully, "
							+ CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
					workExperienceBean.setSaveFlag("true");

					result = updateCandidateStage();
				} else {
					logger.info(loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ", Unable to save Work Experience Details. redirected to Work Experience page again, "
							+ CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
					getBaseDataForWorkExperienceDetails();
					addActionMessage("<li>An Error occured while saving your data..!! Please try again after some time.</li>");
					workExperienceBean.setServerSideErrorMessage("true");
					result = "workExperience";
				}
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		displayMenus();
		return result;
	}
	
	private String saveWorkExperienceDetailsValidation() throws Exception {

		String result = null;
		List<WorkExperienceDetailsBean> workExperienceDetailsList = null;
		ArrayList<String> errorField = new ArrayList<String>();
		List<String> errorList = new ArrayList<String>();
		List<WorkExperienceDetailsBean> workExperienceList = new ArrayList<WorkExperienceDetailsBean>();
		int count = 1;
		try {
			workExperienceDetailsList = workExperienceBean.getWorkExperienceDetailsList();
			workExperienceDetailsList.removeIf(Objects::isNull);

			String postAppliedFor = loggedInUser.getDisciplineID();
			workExperienceBean.setWrkExpPosts(postAppliedFor);

			List<EducationDetailsBean> candidateEducationalDetails = null;
			candidateEducationalDetails = candidateService.getCandiateAcademicDetails(candidateBean,loggedInUser);
			String dateofpassing = null;
			String dateofpassingOther = null;
			if (candidateEducationalDetails != null) {
				if(candidateEducationalDetails.size() > 1) {
					dateofpassing = candidateEducationalDetails.get(1).getYearOfPassing();
				}
				if (dateofpassing==null) {
					dateofpassingOther = candidateEducationalDetails.get(0).getYearOfPassing();
				}
			}
			if (dateofpassing != null) {	
				workExperienceBean.setEduDate(dateofpassing);
			} else {
				workExperienceBean.setEduDate(dateofpassingOther);
			}

			SimpleDateFormat sdfExp = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat sdfMnY = new SimpleDateFormat("MMMM yyyy");
			Date eduPass = new Date();
			
			
			if(workExperienceBean.getEduDate() != null)
			 eduPass = sdfMnY.parse(workExperienceBean.getEduDate());

			if (workExperienceBean.getYesNo() == null || workExperienceBean.getYesNo().trim().equals("")) {
				errorField.add("yesNo");
				errorList.add("<li>" + "Please select Do you have Post Qualification work experience? </li>");
			} else if (workExperienceBean.getYesNo().equals("6")) {
				if (workExperienceBean.getSponsor() == null || workExperienceBean.getSponsor().equals("")) {
					errorField.add("sponsor");
					errorList.add("<li>" + "Please select Will you be sponsored by your employer for the programme? </li>");
				}
				int counter = 0;
				if (workExperienceDetailsList != null && !workExperienceDetailsList.isEmpty()) {
					for (WorkExperienceDetailsBean workExperienceDetailsBean : workExperienceDetailsList) {
						if (workExperienceDetailsBean != null) {

							if (workExperienceDetailsBean.getYesNoA() != null || !workExperienceDetailsBean.getYesNoA().equals("")) {
								if (workExperienceDetailsBean.getYesNoA().equals("6")) {
									counter++;
								}
							}
							if (counter > 1) {
								errorList.add("<li>Current organisation cannot be more than one.</li>");
							}

							if (workExperienceDetailsBean.getYesNoA() != null && workExperienceDetailsBean.getYesNoA().equals("6")) {
								workExperienceDetailsBean.setToYear("31-Mar-2023");
							}
							workExperienceList.add(workExperienceDetailsBean);

							if (workExperienceDetailsBean.getArea() == null || workExperienceDetailsBean.getArea().trim().equals("")) {
								errorField.add("areaArpSop" + count);
								errorList.add("<li>Please enter Organization Name in row " + count + ".</li>");
							} else if (!Pattern.matches("[a-zA-Z0-9 ]{1,100}", workExperienceDetailsBean.getArea().trim())) {
								errorField.add("areaArpSop" + count);
								errorList.add("<li>Please enter valid Organization Name in row " + count + ".</li>");
							}

							if (workExperienceDetailsBean.getJobRole() == null || workExperienceDetailsBean.getJobRole().trim().equals("")) {
								errorField.add("jobRole" + count);
								errorList.add("<li>Please enter Job Profile in row " + count + ".</li>");
							} else if (!Pattern.matches("[a-zA-Z0-9 ]{1,250}", workExperienceDetailsBean.getJobRole().trim())) {
								errorField.add("jobRole" + count);
								errorList.add("<li>Please enter valid Job Profile in row " + count + ".</li>");
							}

							if (workExperienceDetailsBean.getFromYear() == null || workExperienceDetailsBean.getFromYear().equals("")) {
								errorField.add("fromYear_" + count);
								errorList.add("<li>Please select Date of Joining in row " + count + ".</li>");
							}
							if (workExperienceDetailsBean.getYesNoA() == null || workExperienceDetailsBean.getYesNoA().equals("")) {
								errorField.add("yesNoA" + count);
								errorList.add("<li>Please select Is this your current organization in row " + count + ".</li>");
							}
							if (workExperienceDetailsBean.getYesNoA() != null && workExperienceDetailsBean.getYesNoA().equals("7")) {
								if (workExperienceDetailsBean.getToYear() == null || workExperienceDetailsBean.getToYear().equals("")) {
									errorField.add("toYear_" + count);
									errorList.add("<li>Please select Date of Leaving  in row " + count + ".</li>");
								}
							}
							if (workExperienceDetailsBean.getFromYear() != null && !workExperienceDetailsBean.getFromYear().equals("")
									&& workExperienceDetailsBean.getToYear() != null && !workExperienceDetailsBean.getToYear().equals("")
									&& workExperienceDetailsBean.getFromYear().equals(workExperienceDetailsBean.getToYear())) {
								errorList.add("<li>Date of Joining and Date of Leaving cannot be same. Please enter correct Date of Joining and Date of Leaving in row " + count
										+ ".</li>");
							}

							Date row1FromDate = null;
							Date row1ToDate = null;

							if (workExperienceDetailsBean.getFromYear() != null && !workExperienceDetailsBean.getFromYear().equals("")
									&& workExperienceDetailsBean.getToYear() != null && !workExperienceDetailsBean.getToYear().equals("")) {
								row1FromDate = sdfExp.parse(workExperienceDetailsBean.getFromYear());
								row1ToDate = sdfExp.parse(workExperienceDetailsBean.getToYear());
							}

							if (row1FromDate != null && row1ToDate != null) {
								for (int i = count; i < workExperienceDetailsList.size(); i++) {

									WorkExperienceDetailsBean workExpDetailsBean = workExperienceDetailsList.get(i);

									Date row2FromDate = null;
									Date row2ToDate = null;
									Date fromDate = null;
									Date cutOffDate = sdfExp.parse("31-Mar-2023");
									;

									if (workExpDetailsBean.getFromYear() != null && !workExpDetailsBean.getFromYear().equals("")) {
										fromDate = sdfExp.parse(workExpDetailsBean.getFromYear());
									}

									if (!workExpDetailsBean.getYesNoA().equals("") && workExpDetailsBean.getYesNoA().equals("6")) {
										if (fromDate != null && fromDate.before(cutOffDate)) {
											workExpDetailsBean.setToYear("31-Mar-2023");
										} else {
											Date today = new Date();
											String todaysDate = sdfExp.format(today);
											workExpDetailsBean.setToYear(todaysDate);
										}
									}

									if (workExpDetailsBean.getFromYear() != null && !workExpDetailsBean.getFromYear().equals("") && workExpDetailsBean.getToYear() != null
											&& !workExpDetailsBean.getToYear().equals("")) {
										row2FromDate = sdfExp.parse(workExpDetailsBean.getFromYear());
										row2ToDate = sdfExp.parse(workExpDetailsBean.getToYear());
									}

									if (row2FromDate != null && row2ToDate != null) {
										if ((row2FromDate.after(row1FromDate) && row2FromDate.before(row1ToDate))
												|| (row2ToDate.after(row1FromDate) && row2ToDate.before(row1ToDate))) {
											errorList.add("<li>Work Experience date entered in row " + count + " is overlapping with row " + (i + 1) + ".</li>");
										} else if (row2FromDate.equals(row1FromDate) || row2FromDate.equals(row1ToDate) || row2ToDate.equals(row1FromDate)
												|| row2ToDate.equals(row1ToDate)) {
											errorList.add("<li>Work Experience date entered in row " + count + " is overlapping with row " + (i + 1) + ".</li>");
										} else if ((row1FromDate.after(row2FromDate) && row1FromDate.before(row2ToDate))
												|| (row1ToDate.after(row2FromDate) && row1ToDate.before(row2ToDate))) {
											errorList.add("<li>Work Experience date entered in row " + count + " is overlapping with row " + (i + 1) + ".</li>");
										}
									}
								}
							}

							// add here for education passing year and work
							// experience gap
							if (eduPass != null && !eduPass.equals("") && workExperienceDetailsBean.getFromYear() != null && !workExperienceDetailsBean.getFromYear().equals("")) {
								Date fromYear = sdfExp.parse(workExperienceDetailsBean.getFromYear());

								if (eduPass.after(fromYear) || eduPass.equals(fromYear)) {
									errorField.add("fromYear_" + count);
									errorList.add("<li>" + "Date of Period of Experience From in row " + count + " should be greater than Graduation/Equivalent - Date of Passing."
											+ "</li>");
								}
							}

							if (workExperienceDetailsBean.getLastSalary() == null || workExperienceDetailsBean.getLastSalary().equals("")) {
								errorField.add("lastSalary" + count);
								errorList.add("<li>Please enter Last Drawn Salary (per annum) in row " + count + ".</li>");
							} else if (!Pattern.matches("[0-9]{1,9}", workExperienceDetailsBean.getLastSalary().trim())) {
								errorField.add("lastSalary" + count);
								errorList.add("<li>Please enter valid Last Drawn Salary (per annum) in row " + count + ".</li>");
							} else if (Integer.parseInt(workExperienceDetailsBean.getLastSalary().trim()) < 1
									|| Integer.parseInt(workExperienceDetailsBean.getLastSalary().trim()) > 999999999) {
								errorField.add("lastSalary" + count);
								errorList.add("<li>Please enter valid Last Drawn Salary (per annum) between 1 and 999999999 in row " + count + ".</li>");
							}
						}
						count++;
					} // for loop closed

					if (workExperienceList.isEmpty()) {
						errorList.add("<li>Please enter Work Experience Details.</li>");
					}

					if (workExperienceBean.getMonthOfExperience() == null
							|| "".equals(workExperienceBean.getMonthOfExperience()) || workExperienceBean.getDayOfExperience() == null
							|| "".equals(workExperienceBean.getDayOfExperience())) {
						errorList.add("<li>Total Work Experience should not be Blank.</li>");
					} else if ((workExperienceBean.getMonthOfExperienceHidden() != null && !workExperienceBean.getMonthOfExperienceHidden().equals("")
									&& !workExperienceBean.getMonthOfExperienceHidden().equals(workExperienceBean.getMonthOfExperience()))
							|| (workExperienceBean.getDayOfExperienceHidden() != null && !workExperienceBean.getDayOfExperienceHidden().equals("")
									&& !workExperienceBean.getDayOfExperienceHidden().equals(workExperienceBean.getDayOfExperience()))) {
						errorList.add("<li>Total Work Experience does not match with entered work experience dates. Kindly do not change Total Work Experience manually.</li>");
					}
					workExperienceBean.setWorkExperienceSaveList(workExperienceList);
				}
			}
			if (errorList != null && !errorList.isEmpty()) {
				workExperienceBean.setErrorField(errorField);
				result = ValidatorUtil.validationMessageFormatter(errorList);
			}

		} catch (Exception e) {
			result = "<li>Some internal error occured, Please try again..!!</li>";
			logger.fatal(e, e);
		}
		return result;

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