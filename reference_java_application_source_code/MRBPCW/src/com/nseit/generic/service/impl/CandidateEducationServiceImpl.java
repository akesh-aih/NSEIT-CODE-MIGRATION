package com.nseit.generic.service.impl;

import static com.nseit.generic.util.GenericConstants.SESSION_USER;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import com.nseit.generic.dao.CandidateDao;
import com.nseit.generic.dao.CandidateEducationDao;
import com.nseit.generic.dao.CommonDao;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.EducationDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.CandidateEducationService;
import com.nseit.generic.service.CandidatePersonalDetailsService.ErrorCodeEnum;
import com.nseit.generic.util.AuditTrailConstants;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;

@SuppressWarnings("unchecked")
public class CandidateEducationServiceImpl implements CandidateEducationService {

	private CandidateDao candidateDao;

	private CommonDao commonDao;
	private CandidateEducationDao candidateEducationDao;

	private Logger logger = LoggerHome.getLogger(getClass());

	public void setCandidateDao(CandidateDao candidateDAO) {
		this.candidateDao = candidateDAO;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public void setCandidateEducationDao(CandidateEducationDao candidateEducationDao) {
		this.candidateEducationDao = candidateEducationDao;
	}

	// For Academic Details Bhushan03.05.2018
	@Override
	public String getCandidateEducationalDetailsPage(Users loggedInUser, CandidateBean candidateBean, Map<String, Object> sessionAttributes, HttpServletRequest request)
			throws Exception {

		String result = "educationalDetailsPage";

		List<EducationDetailsBean> educationalDetailsList = new ArrayList<EducationDetailsBean>();
		List<EducationDetailsBean> candidateEducationalDetailsList = null;
		List<EducationDetailsBean> candidateEducationalDetailsNewList = new ArrayList<EducationDetailsBean>();
		Users users = (Users) sessionAttributes.get(SESSION_USER);
		sessionAttributes.put(SESSION_USER, users);

		candidateBean.setServerSideErrorMessage("false");
		candidateBean.setSaveFlag("false");

		candidateBean.setSscUnivList(ConfigurationConstants.getInstance().getUniversityMap().get(1));
		candidateBean.setHscUnivList(ConfigurationConstants.getInstance().getUniversityMap().get(2));
		candidateBean.setUGUnivList(ConfigurationConstants.getInstance().getUniversityMap().get(3));
		candidateBean.setYesNo(new TreeMap<>(ConfigurationConstants.getInstance().getParameterMapForDomainName("Yes_No_Map")));
		candidateBean.setMedOfInstructionList(ConfigurationConstants.getInstance().getParameterMapForDomainName("medium_of_instruction"));

		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("global.properties");
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			logger.error(e, e);
		}

		// getting existing education details if already saved in DB
		candidateEducationalDetailsList = candidateEducationDao.getCandidateDataForAcademicDetails(candidateBean, loggedInUser);
		if (candidateEducationalDetailsList != null && !candidateEducationalDetailsList.isEmpty()) {
			candidateBean.setExamName(candidateEducationalDetailsList.get(0).getExamName());
			candidateBean.setDataFound(true);
			candidateBean.setDegreeTypeVal("56");
			List academicDetailsList = (List) ConfigurationConstants.getInstance().getDegreeDetailsMap().get(Integer.parseInt(candidateEducationalDetailsList.get(0).getDegree()));

			if (candidateEducationalDetailsList.size() <= academicDetailsList.size()) {
				for (int i = 0; i < academicDetailsList.size(); i++) {
					if (i < 7) {
						int count = 0;
						for (int j = 0; j < candidateEducationalDetailsList.size(); j++) {
							if (candidateEducationalDetailsList.get(j).getEducationPk().equals(academicDetailsList.get(i))) {
								count++;
								EducationDetailsBean educationDetailsBean = candidateEducationalDetailsList.get(j);// existing details
								if (ConfigurationConstants.getInstance().getAcademicDetailsForPk(String.valueOf(academicDetailsList.get(i))) != null
										&& !ConfigurationConstants.getInstance().getAcademicDetailsForPk(String.valueOf(academicDetailsList.get(i))).equals("")) {
									educationDetailsBean.setExamination(ConfigurationConstants.getInstance().getAcademicDetailsForPk(String.valueOf(academicDetailsList.get(i))));
									educationDetailsBean
											.setMandatory(ConfigurationConstants.getInstance().getAcademicMandatoryMap().get(String.valueOf(academicDetailsList.get(i))));
								}
								candidateEducationalDetailsNewList.add(educationDetailsBean);
							}
						}
						// below count states that examination details does not exist in DB so create new bean with basic details like Examination and mandatory
						if (count == 0) {
							EducationDetailsBean educationDetailsBean = new EducationDetailsBean();
							if (ConfigurationConstants.getInstance().getAcademicDetailsForPk(String.valueOf(academicDetailsList.get(i))) != null
									&& !ConfigurationConstants.getInstance().getAcademicDetailsForPk(String.valueOf(academicDetailsList.get(i))).equals("")) {
								educationDetailsBean.setExamination(ConfigurationConstants.getInstance().getAcademicDetailsForPk(String.valueOf(academicDetailsList.get(i))));
								educationDetailsBean.setMandatory(ConfigurationConstants.getInstance().getAcademicMandatoryMap().get(String.valueOf(academicDetailsList.get(i))));
							}
							candidateEducationalDetailsNewList.add(educationDetailsBean);
						}
					}
				}
			}
			candidateBean.setEducationDtlsList(candidateEducationalDetailsNewList);
		}
		if (candidateEducationalDetailsList == null || candidateEducationalDetailsList.isEmpty()) {
			candidateBean.setExamName("5"); // Set selected UG Option
			List academicDetailsList = (List) ConfigurationConstants.getInstance().getDegreeDetailsMap().get(56);

			for (int i = 0; i < academicDetailsList.size(); i++) {
				if (i < 12) {
					EducationDetailsBean educationDetailsBean = new EducationDetailsBean();
					if (ConfigurationConstants.getInstance().getAcademicDetailsForPk(String.valueOf(academicDetailsList.get(i))) != null
							&& !ConfigurationConstants.getInstance().getAcademicDetailsForPk(String.valueOf(academicDetailsList.get(i))).equals("")) {
						educationDetailsBean.setExamination(ConfigurationConstants.getInstance().getAcademicDetailsForPk(String.valueOf(academicDetailsList.get(i))));
						educationDetailsBean.setMandatory(ConfigurationConstants.getInstance().getAcademicMandatoryMap().get(String.valueOf(academicDetailsList.get(i))));
						educationalDetailsList.add(educationDetailsBean);
					}
				}
			}
			if (educationalDetailsList != null && !educationalDetailsList.isEmpty()) {
				candidateBean.setEducationDtlsList(educationalDetailsList);
			}
		}

		if (request.getAttribute("menuKey") != null) {
			String mandatory = ConfigurationConstants.getInstance().getMenuKeyMandatory(request.getAttribute("menuKey").toString());
			if (mandatory != null && mandatory.equals(GenericConstants.MANDATORY)) {
				candidateBean.setEducationalDetailsMandatory(true);
			}
		}

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

		return result;
	}
	

	public Map<String, String> getMapValueForUniversityBoard(Map<Integer, String> map) {
		Map<Integer, String> mapx = new LinkedHashMap<Integer, String>();

		mapx = sortMapUsingKeyComparator(map);

		Map<String, String> MapValueForUniversityBoard = new LinkedHashMap<String, String>();

		for (Entry<Integer, String> x : mapx.entrySet()) {
			String value1 = x.getValue();
			String value2 = x.getValue();
			MapValueForUniversityBoard.put(value1, value2);
		}

		return MapValueForUniversityBoard;

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

	@Override
	public ErrorCodeEnum saveEducationalDetails(Users loggedInUser, CandidateBean candidateBean, Map<String, Object> sessionAttributes, HttpServletRequest request)
			throws Exception {

		int[] update = null;
		int save = 0;
		List<EducationDetailsBean> educationalDetailsList = new ArrayList<EducationDetailsBean>();

		educationalDetailsList = candidateBean.getSaveEducationDtlsList();
		if (educationalDetailsList != null && !educationalDetailsList.isEmpty()) {
			for (EducationDetailsBean educationDetailsBean : educationalDetailsList) {
				if (educationDetailsBean != null) {
					String examinationPk = ConfigurationConstants.getInstance().getAcademicDetailsPkForDesc(educationDetailsBean.getExamination());
					educationDetailsBean.setExamination(examinationPk);
					educationDetailsBean.setMandatory(ConfigurationConstants.getInstance().getAcademicMandatoryMap().get(String.valueOf(examinationPk)));
					educationDetailsBean.setDegreeSelected(String.valueOf(56));

				}
			}
		}
		// audit trial for educational detail start
		StringBuffer newaudit = new StringBuffer();
		createAudittrail(AuditTrailConstants.USER_ID, loggedInUser.getUsername(), newaudit, true);

		for (EducationDetailsBean educationDetailsBean : educationalDetailsList) {
			if (educationDetailsBean != null) {

				if (educationDetailsBean.getExamination() != null) {
					// 10 12
					if (Arrays.asList("1", "2").contains(educationDetailsBean.getExamination())) {
						if (educationDetailsBean.getExamination().equals("1")) {
							createAudittrail(AuditTrailConstants.SSC_DETAILS, "", newaudit, false);
						} else if (educationDetailsBean.getExamination().equals("2")) {
							newaudit.append(AuditTrailConstants.TWELVE_DETAILS);
						}

						createAudittrail(AuditTrailConstants.NAME_OF_BOARD, educationDetailsBean.getUniversity(), newaudit, true);
						createAudittrail(AuditTrailConstants.OTHER_BOARD, educationDetailsBean.getUniversityOth(), newaudit, true);
						createAudittrail(AuditTrailConstants.M_Y_PASSING, educationDetailsBean.getDateOfPassing(), newaudit, true);
						createAudittrail(AuditTrailConstants.TOTAL_MARKS, educationDetailsBean.getTotalMarks(), newaudit, true);
						createAudittrail(AuditTrailConstants.OBTAINED_MARKS, educationDetailsBean.getObtainedMarks(), newaudit, true);
						createAudittrail(AuditTrailConstants.PERCENTAGE, educationDetailsBean.getPercentage(), newaudit, true);
						createAudittrail(AuditTrailConstants.MEDIUM_INSTRUCTION, educationDetailsBean.getMedOfInstruction(), newaudit, true);
						createAudittrail(AuditTrailConstants.TAMIL_LANG, educationDetailsBean.getTamilLang(), newaudit, true);

					} else if (educationDetailsBean.getExamination().equals("3")) {
						// ug
						newaudit.append(AuditTrailConstants.DIPLOMA_DETAILS);
						createAudittrail(AuditTrailConstants.DIPLOMA_NAME, educationDetailsBean.getUniversity(), newaudit, true);
						createAudittrail(AuditTrailConstants.OTHER_DIPLOMA, educationDetailsBean.getUniversityOth(), newaudit, true);
						createAudittrail(AuditTrailConstants.PRD_OF_STUDY_FROM, educationDetailsBean.getPrdOfStudyFrm(), newaudit, true);
						createAudittrail(AuditTrailConstants.PRD_OF_STUDY_TO, educationDetailsBean.getPrdOfStudyTo(), newaudit, true);
						createAudittrail(AuditTrailConstants.DURATION_OF_STUDY, educationDetailsBean.getDuration(), newaudit, true);
						createAudittrail(AuditTrailConstants.NAME_OF_INSTIUTION, educationDetailsBean.getInstitution(), newaudit, true);
						createAudittrail(AuditTrailConstants.M_Y_PASSING, educationDetailsBean.getDateOfPassing(), newaudit, true);
						createAudittrail(AuditTrailConstants.DIP_MARKS_YESNO, educationDetailsBean.getDipMarksYesNo(), newaudit, true);
						createAudittrail(AuditTrailConstants.TOTAL_MARKS, educationDetailsBean.getTotalMarks(), newaudit, true);
						createAudittrail(AuditTrailConstants.OBTAINED_MARKS, educationDetailsBean.getObtainedMarks(), newaudit, true);
						createAudittrail(AuditTrailConstants.PERCENTAGE, educationDetailsBean.getPercentage(), newaudit, true);
						createAudittrail(AuditTrailConstants.MEDIUM_INSTRUCTION, educationDetailsBean.getMedOfInstruction(), newaudit, true);
						createAudittrail(AuditTrailConstants.TAMIL_LANG, educationDetailsBean.getTamilLang(), newaudit, true);

					} else if (Arrays.asList("4", "5").contains(educationDetailsBean.getExamination())) {
						// Pg
						if (educationDetailsBean.getExamination().equals("4")) {
							newaudit.append(AuditTrailConstants.GRADUATION_DETAILS);
							createAudittrail(AuditTrailConstants.UG_YESNO, educationDetailsBean.getUgYesNo(), newaudit, true);
						} else if (educationDetailsBean.getExamination().equals("5")) {
							newaudit.append(AuditTrailConstants.PG_DETAILS);
							createAudittrail(AuditTrailConstants.PG_YESNO, educationDetailsBean.getPgYesNo(), newaudit, true);
						}

						createAudittrail(AuditTrailConstants.SPECIALIZATION, educationDetailsBean.getSpecialization(), newaudit, true);
						createAudittrail(AuditTrailConstants.M_Y_PASSING, educationDetailsBean.getDateOfPassing(), newaudit, true);

						if (educationDetailsBean.getExamination().equals("5")) {
							createAudittrail(AuditTrailConstants.PGDIP_YESNO, educationDetailsBean.getPgDipYesNo(), newaudit, true);
							createAudittrail(AuditTrailConstants.SPECIALIZATION, educationDetailsBean.getPgDipSpecialization(), newaudit, true);
							createAudittrail(AuditTrailConstants.M_Y_PASSING, educationDetailsBean.getPgDipDateofpassing(), newaudit, true);
						}

					} else if (educationDetailsBean.getExamination().equals("7")) {
						
						if(StringUtils.isBlank(educationDetailsBean.getPstmPreference()))
						{
							createAudittrail(AuditTrailConstants.PSTM_PREFERENCE, "7", newaudit, true);
							createAudittrail(AuditTrailConstants.TAMIL_MED_SSC, educationDetailsBean.getTamilMedium(), newaudit, true);
							createAudittrail(AuditTrailConstants.TAMIL_MED_UG, educationDetailsBean.getUgTamilMedium(), newaudit, true);
						}
						else
						{
							createAudittrail(AuditTrailConstants.PSTM_PREFERENCE, educationDetailsBean.getPstmPreference(), newaudit, true);
							createAudittrail(AuditTrailConstants.TAMIL_MED_SSC, educationDetailsBean.getTamilMedium(), newaudit, true);
							createAudittrail(AuditTrailConstants.TAMIL_MED_UG, educationDetailsBean.getUgTamilMedium(), newaudit, true);
						}
					}
				}
			}
		}
		commonDao.insertCandidateAuditrail(loggedInUser, AuditTrailConstants.EDU_SAVE_DETAILS, newaudit.toString());

		Integer recordCount = null;
		recordCount = candidateEducationDao.getAcademicDetailsRecordCount(loggedInUser.getUserId());
		if (recordCount != null && recordCount > 0) {
			save = candidateEducationDao.deleteAcademicDetailsEntryForCandidate(educationalDetailsList, loggedInUser, candidateBean.getSslcTamil());
			if (save > 0) {
				candidateBean.setDataFound(true);
				candidateBean.setSaveFlag("true");
				candidateBean.setTestFlag("true");
				// Set Basic Data
				getBaseDataForEducationalDetails(candidateBean, loggedInUser);

				for (EducationDetailsBean educationDetailsBean : educationalDetailsList) {
					if (educationDetailsBean != null) {
						educationDetailsBean.setExamination(ConfigurationConstants.getInstance().getAcademicDetailsForPk(educationDetailsBean.getExamination()));
						educationDetailsBean.setMandatory(educationDetailsBean.getMandatory());
					}
				}

				loggedInUser.setRemoteIp(request.getRemoteAddr());

				return ErrorCodeEnum.UPDATE_CANDIDATE_STAGE;
			} else {
				return ErrorCodeEnum.ERROR;
			}
		} else {
			update = candidateEducationDao.insertCandidateAcademicDetails(educationalDetailsList, loggedInUser);
			if (update != null && update.length > 0) {
				candidateBean.setDataFound(true);
				candidateBean.setTestFlag("true");
				candidateBean.setSaveFlag("true");
				// Set Basic Data
				getBaseDataForEducationalDetails(candidateBean, loggedInUser);

				for (EducationDetailsBean educationDetailsBean : educationalDetailsList) {
					if (educationDetailsBean != null) {
						educationDetailsBean.setExamination(ConfigurationConstants.getInstance().getAcademicDetailsForPk(educationDetailsBean.getExamination()));
						educationDetailsBean.setMandatory(educationDetailsBean.getMandatory());
					}
				}

				loggedInUser.setRemoteIp(request.getRemoteAddr());
				commonDao.insertCandidateAuditrail(loggedInUser, "Education - Save & Continue",
						"Candidate with User Id " + loggedInUser.getUsername() + " has Save & Continue Educational Qualification.");

				return ErrorCodeEnum.UPDATE_CANDIDATE_STAGE;
			} else {
				return ErrorCodeEnum.ERROR;
			}
		}
	}

	public void getBaseDataForEducationalDetails(CandidateBean candidateBean, Users loggedInUser) throws Exception {

		candidateBean.setServerSideErrorMessage("true");
		candidateBean.setSaveFlag("true");
		
		candidateBean.setSscUnivList(ConfigurationConstants.getInstance().getUniversityMap().get(1));
		candidateBean.setHscUnivList(ConfigurationConstants.getInstance().getUniversityMap().get(2));
		candidateBean.setUGUnivList(ConfigurationConstants.getInstance().getUniversityMap().get(3));
		candidateBean.setYesNo(new TreeMap<>(ConfigurationConstants.getInstance().getParameterMapForDomainName("Yes_No_Map")));
		candidateBean.setCardTypeList(new TreeMap<>(ConfigurationConstants.getInstance().getParameterMapForDomainName("CARD_TYPE")));
		candidateBean.setMedOfInstructionList(ConfigurationConstants.getInstance().getParameterMapForDomainName("medium_of_instruction"));
		
		String dateofbirthvalue = null;
		if (loggedInUser.getDate_Of_Birth() != null && !loggedInUser.getDate_Of_Birth().equals("")) {
			dateofbirthvalue = loggedInUser.getDate_Of_Birth();
		}
		if (dateofbirthvalue != null && !dateofbirthvalue.equals("")) {
			String d[] = dateofbirthvalue.split("-");
			int dobYr = Integer.parseInt(d[2]);
			candidateBean.setFirstDate(dobYr+13);
	  		candidateBean.setHscDate(dobYr+14);
	  		candidateBean.setGraduationDate(dobYr+15);
	  		candidateBean.setPgDate(dobYr + 16);
		}
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
