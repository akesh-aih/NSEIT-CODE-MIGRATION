package com.nseit.generic.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;

import com.nseit.generic.dao.CandidateDao;
import com.nseit.generic.dao.CandidatePersonalDetailsDAO;
import com.nseit.generic.dao.CommonDao;
import com.nseit.generic.dao.OTPMasterDao;
import com.nseit.generic.dao.RegistraionDAO;
import com.nseit.generic.models.AddressBean;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.EducationDetailsBean;
import com.nseit.generic.models.PersonalDetailsBean;
import com.nseit.generic.models.RegistrationBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.CandidatePersonalDetailsService;
import com.nseit.generic.service.CommonService;
import com.nseit.generic.util.AuditTrailConstants;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.validation.RegistrationBeanValidator;

public class CandidatePersonalDetailsServiceImpl implements CandidatePersonalDetailsService {

	private JavaMailSender mailSender;
	private CommonDao commonDao;
	private CandidatePersonalDetailsDAO candidatePersonalDetailsDAO;
	private Logger logger = LoggerHome.getLogger(getClass());
	private CandidateDao candidateDao;
	private CandidateBean candidateBean = new CandidateBean();
	private String token;
	private RegistraionDAO registraionDAO;
	private OTPMasterDao m_OTPMasterDao;
	private CommonService commonService;

	public void setOTPMasterDao(OTPMasterDao otpMasterDao) {
		this.m_OTPMasterDao = otpMasterDao;
	}

	public void setRegistraionDAO(RegistraionDAO registraionDAO) {
		this.registraionDAO = registraionDAO;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setCandidateDao(CandidateDao candidateDao) {
		this.candidateDao = candidateDao;
	}

	public void setCandidatePersonalDetailsDAO(CandidatePersonalDetailsDAO candidatePersonalDetailsDAO) {
		this.candidatePersonalDetailsDAO = candidatePersonalDetailsDAO;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	private void createAudittrail(String key, String value, StringBuffer result, boolean concat) {
		result.append(key);
		result.append(":");
		result.append(value);
		if (concat) {
			result.append("~");
		}
	}
	
	private String personalAudit(CandidateBean candidateBean, AddressBean addressBean, PersonalDetailsBean personalDetailsBean, Users loggedInUser) {
		String dataString = "Candidate User ID : " + loggedInUser.getUsername() + " Personal Details Saved As: Post Applying for : " + candidateBean.getDisciplinName()
				+ "~ Applicants's First Name : " + personalDetailsBean.getCandidateFirstName() + "~ Are you a domicile of Tamil Nadu? : " + candidateBean.getDomicileUp()
				+ "~ Date Of Birth : " + candidateBean.getDateOfBirth() + "~ Age as on 01/07/2025 : " + candidateBean.getAgeInYears() + "Years" + candidateBean.getAgeInMonths()
				+ "Months" + candidateBean.getAgeInDays() + "Days" + "~ Gender : " + candidateBean.getGenderValDesc() + "~ Marital Status : " + candidateBean.getMariatalStatus()
				+ "~ Father's Name : " + candidateBean.getFathersName() + "~ Father's Initial : " + candidateBean.getFathersInitial() + "~ Mother's Name : "
				+ candidateBean.getMothersName() + "~ Mother's Initial : " + candidateBean.getMothersInitial() + "~ Religion : " + candidateBean.getReligionBelief()
				+ "~ Other Religion : " + candidateBean.getReligionBeliefOthers() + "~ Do you possess Community certificate issued by Tamil Nadu Government? : "
				+ candidateBean.getCategorycertificate() + "~ Community : " + candidateBean.getCategoryValDesc() + "~ Permanent Address Line 1 : " + addressBean.getAddressFiled1()
				+ "~ Permanent Address - State : " + candidateBean.getStateVal() + "~ Permanent Address - District / City : " + candidateBean.getDistrictVal()
				+ "~ Permanent Address - Pincode : " + addressBean.getPinCode() + "~ Address for Communication - Same as Permanent Address : " + candidateBean.isAddressChkBox()
				+ "~ Communication Address Line 1 : " + addressBean.getAlternateAddressFiled1() + "~ Communication Address - State : " + candidateBean.getAltStateVal()
				+ "~ Communication Address - District / City : " + candidateBean.getAltDistrictVal() + "~ Communication Address - Pincode : " + addressBean.getAlternatePinCode();

		return dataString;
	}
	
	
	
	@Override
	public ErrorCodeEnum saveCandidateDetails(CandidateBean candidateBean, AddressBean addressBean, PersonalDetailsBean personalDetailsBean, Users loggedInUser,
			Integer recordCount, Map<String, Object> sessionAttributes, HttpServletRequest request) throws Exception {
		// int saveCandidateDetails = 0;
		//String dataString = personalAudit(candidateBean, addressBean, personalDetailsBean, loggedInUser);
		
		StringBuffer newaudit = new StringBuffer();
		createAudittrail(AuditTrailConstants.PARENT_OR_GUARDIAN, candidateBean.getParentAndGuardian()+"", newaudit, true);
		if(candidateBean.getParentAndGuardian()==220) {
			createAudittrail(AuditTrailConstants.FATHER_NAME,candidateBean.getFathersName(),newaudit,true);
			createAudittrail(AuditTrailConstants.MOTHER_NAME,candidateBean.getMothersName(),newaudit,true);
		}else {
			createAudittrail(AuditTrailConstants.GUARDIAN_NAME,candidateBean.getGuardianName(),newaudit,true);
		}
		createAudittrail(AuditTrailConstants.MARITAL_STATUS,ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(candidateBean.getMariatalStatus())),newaudit,true);
		createAudittrail(AuditTrailConstants.SPOUSE_NAME,candidateBean.getSpouseName(),newaudit,true);
		createAudittrail(AuditTrailConstants.NATIVITY,candidateBean.getNativity(),newaudit,true);
		createAudittrail(AuditTrailConstants.OTHER_NATIVITY,candidateBean.getOtherNativity(),newaudit,true);
		createAudittrail(AuditTrailConstants.RELIGION,ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(candidateBean.getReligionBelief())),newaudit,true);
		createAudittrail(AuditTrailConstants.OTHER_RELIGION,candidateBean.getReligionBeliefOthers(),newaudit,true);
		createAudittrail(AuditTrailConstants.IS_GOVT_SERVICE,ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(candidateBean.getIsGovtService())),newaudit,true);
		if(candidateBean.isGovtServChkBox()) {
			createAudittrail(AuditTrailConstants.GOVT_SERVICE_CHKBOX,6+"",newaudit,true);
		}else {
			createAudittrail(AuditTrailConstants.GOVT_SERVICE_CHKBOX,7+"",newaudit,true);
		}
		createAudittrail(AuditTrailConstants.ORG_NAME,candidateBean.getOrgName(),newaudit,true);
		createAudittrail(AuditTrailConstants.CURR_DESIGNATION,candidateBean.getCurrentDesig(),newaudit,true);
		createAudittrail(AuditTrailConstants.PLACE_OF_WORK,candidateBean.getPlaceOfWork(),newaudit,true);
		createAudittrail(AuditTrailConstants.GOVT_DATE,candidateBean.getGovtDate(),newaudit,true);
		createAudittrail(AuditTrailConstants.GOVT_AGE,candidateBean.getAgeInYears() + " years " + candidateBean.getAgeInMonths() + " months " + candidateBean.getAgeInDays() + " days",newaudit,true);
		createAudittrail(AuditTrailConstants.PHOTO_ID,ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(candidateBean.getPhotoIDProof1())),newaudit,true);
		createAudittrail(AuditTrailConstants.PHOTO_ID_PROOF_NO,candidateBean.getPhotoIDProof1Val(),newaudit,true);
		createAudittrail(AuditTrailConstants.PERMANENT_ADDRESS_LINE_1,addressBean.getAddressFiled1(),newaudit,true);
		createAudittrail(AuditTrailConstants.PERM_STATE,candidateBean.getStateVal(),newaudit,true);
		createAudittrail(AuditTrailConstants.PERM_DISTRICT_DROPDOWN,candidateBean.getDistrictVal(),newaudit,true);
		createAudittrail(AuditTrailConstants.PERM_DISTRICT_TEXTBOX,candidateBean.getDistrictValother(),newaudit,true);
		createAudittrail(AuditTrailConstants.PERM_CITY,addressBean.getCityName(),newaudit,true);
		createAudittrail(AuditTrailConstants.PERM_PINCODE,addressBean.getPinCode(),newaudit,true);
		createAudittrail(AuditTrailConstants.SAME_AS_PERM,candidateBean.isAddressChkBox()?"true":"false",newaudit,true);
		createAudittrail(AuditTrailConstants.CORRESPONDENCE_ADDRESS_LINE_1,addressBean.getAlternateAddressFiled1(),newaudit,true);
		createAudittrail(AuditTrailConstants.CORS_STATE,candidateBean.getAltStateVal(),newaudit,true);
		createAudittrail(AuditTrailConstants.CORS_DISTRICT_DROPDOWN,candidateBean.getAltDistrictVal(),newaudit,true);
		createAudittrail(AuditTrailConstants.CORS_DISTRICT_TEXTBOX,candidateBean.getAltDistrictValOthers(),newaudit,true);
		createAudittrail(AuditTrailConstants.CORS_CITY,addressBean.getAlternateCity(),newaudit,true);
		createAudittrail(AuditTrailConstants.CORS_PINCODE,addressBean.getAlternatePinCode(),newaudit,true);
		createAudittrail(AuditTrailConstants.MOTHER_TONGUE,ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(candidateBean.getMotherTongue())),newaudit,false);
		
		
		int saveCandidateDetails = candidatePersonalDetailsDAO.saveCandidateDetails(candidateBean, addressBean, personalDetailsBean, loggedInUser);
		if (saveCandidateDetails > 0) {

			commonDao.insertCandidateAuditrail(loggedInUser, "Personal Page - Save & Continue", newaudit.toString());
			loggedInUser.setDate_Of_Birth(candidateBean.getDateOfBirth());

			loggedInUser.setCategoryValDesc(candidateBean.getCategoryValDesc());
			/*loggedInUser.setPhysicalDisability(candidateBean.getBenchmarkDisability());*/
			prepopulateForm(loggedInUser);

			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(candidateBean.getInitials()).append(" ");

			stringBuilder.append(candidateBean.getPersonalDetailsBean().getCandidateFirstName()).append(" ");
			// if (candidateBean.getPersonalDetailsBean().getCandidateMiddleName() != null && !candidateBean.getPersonalDetailsBean().getCandidateMiddleName().equals("")) {
			// stringBuilder.append(candidateBean.getPersonalDetailsBean().getCandidateMiddleName()).append(" ");
			// }
			// if (candidateBean.getPersonalDetailsBean().getCandidateLastName() != null && !candidateBean.getPersonalDetailsBean().getCandidateLastName().equals("")) {
			// stringBuilder.append(candidateBean.getPersonalDetailsBean().getCandidateLastName());
			// }

			loggedInUser.setUserFk(loggedInUser.getUserId());

			candidateBean.setStatusID(loggedInUser.getCandidateStatusId());
			int updateStage = 0;
			if (loggedInUser.getCandidateStatusId() < ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_INTITIATED)) {
				updateStage = commonDao.updateCandidateStatus(loggedInUser, ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_INTITIATED));
			}

			if (updateStage > 0) {
				String status = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_INTITIATED) + "";
				loggedInUser.setCandidateStatusId(Integer.parseInt(status));
			}

			loggedInUser.setRemoteIp(request.getRemoteAddr());
			/*
			 * commonDao.insertCandidateAuditrail(loggedInUser, "Save & Continue", "Candidate with User Id " + loggedInUser.getUsername() +
			 * " has clicked Save & Continue on Personal Details page.");
			 */

			if (loggedInUser.getUserType().compareTo("C") == 0) {
				if (loggedInUser.getDisciplineID() != null && !loggedInUser.getDisciplineID().equals("")) {
					candidateBean.setDisciplinName(ConfigurationConstants.getInstance().getTestDetailsFromMapForTestPk(Integer.parseInt(loggedInUser.getDisciplineID())).getTestName());
				}
			}
			candidateBean.setVerifymobileOTPFlag(true);
			return ErrorCodeEnum.UPDATE_CANDIDATE_STAGE;
		} else {
			return ErrorCodeEnum.ERROR;
		}
	}

	private void prepopulateForm(Users loggedInUser) {
		
		Map<Integer, String> parentAndGuardianList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Relative");
		Map<Integer, String> parentAndGuardianListMap = new TreeMap<Integer, String>(parentAndGuardianList);
		candidateBean.setParentAndGuardianList(parentAndGuardianListMap);
		
		Map<Integer, String> motherTongueList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Mother_Tongue"); 
		Map<Integer, String> motherTongueMap = new TreeMap<Integer, String>(motherTongueList); 
		candidateBean.setMotherTongueList(motherTongueMap);

		Map<Integer, String> religionBeliefList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Religion_Belief");
		Map<Integer, String> religionBeliefListtMap = new TreeMap<Integer, String>(religionBeliefList);
		candidateBean.setReligionBeliefList(religionBeliefListtMap);
		
		Map<Integer, String> maritalStatusList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Marital_Status");
		Map<Integer, String> maritalStatusListMap = new TreeMap<Integer, String>(maritalStatusList);
		candidateBean.setMaritalStatusList(maritalStatusListMap);
		
		Map<Integer, String> photoIdProofList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Photo_ID_Proof"); 
		  Map<Integer, String> photoIdProofListMap = new TreeMap<Integer, String>(photoIdProofList); 
		  candidateBean.setPhotoIdProof(photoIdProofListMap);
		  
		Map<Integer, String> yesNo = ConfigurationConstants.getInstance().getParameterMapForDomainName("Yes_No_Map");
		Map<Integer, String> yesNoMap = new TreeMap<Integer, String>(yesNo);
		candidateBean.setYesNo(yesNoMap);
		
		Map<Integer, String> genderList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Gender");
		Map<Integer, String> genderMap = new TreeMap<Integer, String>(genderList);
		candidateBean.setGenderList(genderMap);
		
		Map<Integer, String> categoryList = ConfigurationConstants.getInstance().getCategoryListMap();
		candidateBean.setCategoryList(categoryList);

		candidateBean.setStateList(ConfigurationConstants.getInstance().getStateMap(1));
		
		Map<Integer, String> districtList = ConfigurationConstants.getInstance().getDistrictMap(1);
		Map<Integer, String> districtListMap = new TreeMap<Integer, String>(districtList);
		districtListMap.remove(585);
		candidateBean.setDistrictList(districtListMap);
		
		Map<Integer, String> altDistrictList = ConfigurationConstants.getInstance().getDistrictMap(1);
		Map<Integer, String> altDistrictListMap = new TreeMap<Integer, String>(altDistrictList);
		altDistrictListMap.remove(585);
		candidateBean.setAltDistrictList(altDistrictListMap);

		if (StringUtils.isNotBlank(loggedInUser.getNationality())) {
			candidateBean.setNationality(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(loggedInUser.getNationality())));
		}

		if (StringUtils.isNotBlank(loggedInUser.getNativityCertDist())) {
			candidateBean.getPersonalDetailsBean().setAppliedDist(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(loggedInUser.getNativityCertDist())));
		}
		
		if (StringUtils.isNotBlank(loggedInUser.getCandidateFirstName())) {
			candidateBean.getPersonalDetailsBean().setCandidateFirstName(loggedInUser.getCandidateFirstName());
		}
		
		if (StringUtils.isNotBlank(loggedInUser.getInitials())) {
			candidateBean.getPersonalDetailsBean().setCandidatePrefix(loggedInUser.getInitials());
		}
		
		if (loggedInUser.getEmailAddress() != null && !loggedInUser.getEmailAddress().equals("")) {
			candidateBean.getPersonalDetailsBean().setEmail(loggedInUser.getEmailAddress());
		}

		if (loggedInUser.getMobile() != null && !loggedInUser.getMobile().equals("")) {
			candidateBean.getPersonalDetailsBean().setMobileNo(loggedInUser.getMobile());
		}

//		if (loggedInUser.getDate_Of_Birth() != null && !loggedInUser.getDate_Of_Birth().equals("")) {
//			candidateBean.setDateOfBirth(loggedInUser.getDate_Of_Birth());
//		}
//		if (loggedInUser.getAge() != null && !loggedInUser.getAge().equals("")) {
//			candidateBean.getPersonalDetailsBean().setAge(loggedInUser.getAge());
//		}

		/*if (loggedInUser.getGender() != null && !loggedInUser.getGender().equals("")) {
			candidateBean.setGenderValDesc(ConfigurationConstants.getInstance()
					.getParameterValueForKey(Integer.parseInt(loggedInUser.getGender())));
		}*/

		if (loggedInUser.getDisciplineID() != null && !loggedInUser.getDisciplineID().equals("")) {
			candidateBean.setDisciplinName(ConfigurationConstants.getInstance()
					.getTestDetailsFromMapForTestPk(Integer.parseInt(loggedInUser.getDisciplineID())).getTestName());
		}
		
		/*if (loggedInUser.getNationality() != null && !loggedInUser.getNationality().equals("")) {
			candidateBean.setNationality(ConfigurationConstants.getInstance()
					.getParameterValueForKey(Integer.parseInt(loggedInUser.getNationality())));
		}*/
//		candidateBean.setAgeAsOn(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AGE_AS_ON));

	}

	private List<String> getCandidateTitle() {
		List<String> titleList = new ArrayList<String>();

		titleList.add("Mr.");
		titleList.add("Mrs.");
		titleList.add("Ms.");
		// titleList.add("Dr.");

		return titleList;
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

	private void getDescriptionForReference() {
		// logger.info("getDescriptionForReference()");
		int stateId = 0;
		int altStateId = 0;

		try {


			if (candidateBean.getUniversityVal() != null && !candidateBean.getUniversityVal().equals("")) {
			}

			if (candidateBean.getPatternVal() != null && !candidateBean.getPatternVal().trim().equalsIgnoreCase("")) {
				candidateBean.setExamPatternDesc(ConfigurationConstants.getInstance().getPatternVal(Integer.parseInt(candidateBean.getPatternVal())));
			}
			if (candidateBean.getNationality() != null && !candidateBean.getNationality().equals("")) {

				candidateBean.setNationalityDesc(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(candidateBean.getNationality())));
			}

			if (candidateBean.getGenderValDesc() != null && !candidateBean.getGenderValDesc().equals("")) {

				candidateBean.setGenderValDesc(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(candidateBean.getGenderValDesc())));
			}

			if (candidateBean.getCountryVal() != null && !candidateBean.getCountryVal().equals("")) {
				candidateBean.setCountryValDesc(ConfigurationConstants.getInstance().getCountryMap().get(Integer.parseInt(candidateBean.getCountryVal())));
			}

			if (candidateBean.getStateVal() != null && !candidateBean.getStateVal().equals("")) {
				stateId = Integer.parseInt(candidateBean.getStateVal());
				candidateBean.setStateValDesc(ConfigurationConstants.getInstance().getStateMap(1).get(Integer.parseInt(candidateBean.getStateVal())));
			} 

			if (candidateBean.getAltStateVal() != null && !candidateBean.getAltStateVal().equals("")) {
				altStateId = Integer.parseInt(candidateBean.getAltStateVal());
				candidateBean.setAltStateValDesc(ConfigurationConstants.getInstance().getStateMap(1).get(Integer.parseInt(candidateBean.getAltStateVal())));
			} 

			if (candidateBean.getTalukaVal() != null && !candidateBean.getTalukaVal().equals("")) {
				candidateBean
						.setTalukaValDesc(ConfigurationConstants.getInstance().getTalukaMap(Integer.parseInt(candidateBean.getDistrictVal())).get(candidateBean.getTalukaVal()));

			}

			if (candidateBean.getAltTalukaVal() != null && !candidateBean.getAltTalukaVal().equals("")) {
				candidateBean.setAltTalukaValDesc(
						ConfigurationConstants.getInstance().getTalukaMap(Integer.parseInt(candidateBean.getAltDistrictVal())).get(candidateBean.getAltTalukaVal()));
			}
			if (stateId != 0 && candidateBean.getDistrictVal() != null && !candidateBean.getDistrictVal().equals("")) {
				candidateBean.setDistrictListMap(ConfigurationConstants.getInstance().getDistrictMap(Integer.parseInt(candidateBean.getStateVal())));
				candidateBean.setDistrictValDesc(ConfigurationConstants.getInstance().getDistrictMap(stateId).get(Integer.parseInt(candidateBean.getDistrictVal())));
			}

			if (candidateBean.getAddressBean() != null) {
				if (CommonUtil.getParameter(candidateBean.getAddressBean().getOtherDistrictField()).compareTo("") != 0) {
					candidateBean.setDistrictValDesc(CommonUtil.getParameter(candidateBean.getAddressBean().getOtherDistrictField()));
				}
			}

			if (candidateBean.getAltCountryVal() != null && !candidateBean.getAltCountryVal().equals("")) {
				candidateBean.setAltCountryValDesc(ConfigurationConstants.getInstance().getCountryMap().get(Integer.parseInt(candidateBean.getAltCountryVal())));
			}

			if (altStateId != 0 && candidateBean.getAltDistrictVal() != null && !candidateBean.getAltDistrictVal().equals("")) {
				candidateBean.setAltdistrictListMap(ConfigurationConstants.getInstance().getDistrictMap(Integer.parseInt(candidateBean.getAltStateVal())));
				candidateBean.setAltDistrictValDesc(ConfigurationConstants.getInstance().getDistrictMap(altStateId).get(Integer.parseInt(candidateBean.getAltDistrictVal())));
			}

			if (candidateBean.getCategoryValDesc() != null && !candidateBean.getCategoryValDesc().equals("")) {
				candidateBean.setCategoryValDesc(ConfigurationConstants.getInstance().getCategoryValueForKey(Integer.parseInt(candidateBean.getCategoryValDesc())));
			}

			PersonalDetailsBean personalDetails = null;
			personalDetails = candidateBean.getPersonalDetailsBean();

			if (personalDetails != null) {
				if (personalDetails.getJnkMigrant() != null && !personalDetails.getJnkMigrant().equals("")) {
					personalDetails.setJnkMigrantDesc(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(personalDetails.getJnkMigrant())));
				}
				if (personalDetails.getPoverty() != null && !personalDetails.getPoverty().equals("")) {
					personalDetails.setPovertyDesc(ConfigurationConstants.getInstance().getParameterValueForKey(personalDetails.getPoverty()));
				}
				candidateBean.setPersonalDetailsBean(personalDetails);

			}

			EducationDetailsBean educationDetails = null;
			educationDetails = candidateBean.getEducationDetailsBean();

			if (educationDetails != null) {

				if (educationDetails.getDegreeType() != null && !educationDetails.getDegreeType().equals("")) {
					educationDetails.setDegreeTypeDesc(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(educationDetails.getDegreeType())));
				}

				if (educationDetails.getDegree() != null && !educationDetails.getDegree().equals("")) {
					educationDetails.setDegreeDesc(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(educationDetails.getDegree())));
				}

				if (educationDetails.getResultOfGraduation() != null && !educationDetails.getResultOfGraduation().equals("")) {
					educationDetails
							.setResultOfGraduationDesc(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(educationDetails.getResultOfGraduation())));
				}

				if (educationDetails.getGraduationFromUniv() != null && !educationDetails.getGraduationFromUniv().equals("")) {
					educationDetails
							.setGraduationFromUnivDesc(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(educationDetails.getGraduationFromUniv())));
				}

				if (educationDetails.getSchoolLocation() != null && !educationDetails.getSchoolLocation().equals("")) {
					educationDetails.setSchoolLocationDesc(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(educationDetails.getSchoolLocation())));
				}
			}

			candidateBean.setEducationDetailsBean(educationDetails);

			if (candidateBean != null) {
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	@Override
	public String getCandidateDetails(Users users, CandidateBean candidateBean, Map<String, Object> sessionAttributes, HttpServletRequest request) throws Exception {
		String result = null;
		/* Get Registration Data of User */
		getCandidateRegistrationData(candidateBean, users);
		// Get Data from DB for User Returning Back to personal Page
		populateCandidateDetails(candidateBean, users);
		result = "applicationForm";
		if (request.getAttribute("menuKey") != null) {
			String mandatory = ConfigurationConstants.getInstance().getMenuKeyMandatory(request.getAttribute("menuKey").toString());
			if (mandatory != null && mandatory.equals(GenericConstants.MANDATORY)) {
				candidateBean.setCandiateDetailsMandatory(true);
			}
		}
		return result;
	}

	private void getCandidateRegistrationData(CandidateBean candidateBean, Users loggedInUser) throws Exception {

		Map<Integer, String> yesNo = ConfigurationConstants.getInstance().getParameterMapForDomainName("Yes_No_Map");
		Map<Integer, String> yesNoMap = new TreeMap<Integer, String>(yesNo);
		candidateBean.setYesNo(yesNoMap);

		Map<Integer, String> religionBeliefList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Religion_Belief");
		Map<Integer, String> religionBeliefListtMap = new TreeMap<Integer, String>(religionBeliefList);
		candidateBean.setReligionBeliefList(religionBeliefListtMap);
		
		Map<Integer, String> maritalStatusList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Marital_Status");
		Map<Integer, String> maritalStatusListMap = new TreeMap<Integer, String>(maritalStatusList);
		candidateBean.setMaritalStatusList(maritalStatusListMap);

		Map<Integer, String> parentAndGuardianList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Relative");
		Map<Integer, String> parentAndGuardianListMap = new TreeMap<Integer, String>(parentAndGuardianList);
		candidateBean.setParentAndGuardianList(parentAndGuardianListMap);

		Map<Integer, String> nativityList;
		nativityList = ConfigurationConstants.getInstance().getNativityMap();
		candidateBean.setNativityList(nativityList);
		
		Map<Integer, String> photoIdProofList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Photo_ID_Proof");
		Map<Integer, String> photoIdProofListMap = new TreeMap<Integer, String>(photoIdProofList);
		candidateBean.setPhotoIdProof(photoIdProofListMap);

		Map<Integer, String> motherTongueList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Mother_Tongue");
		Map<Integer, String> motherTongueMap = new TreeMap<Integer, String>(motherTongueList);
		candidateBean.setMotherTongueList(motherTongueMap);
		  

		Map<Integer, String> genderList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Gender");
		Map<Integer, String> genderMap = new TreeMap<Integer, String>(genderList);
		candidateBean.setGenderList(genderMap);
		
		candidateBean.setStateList(ConfigurationConstants.getInstance().getStateMap(1));
		
		Map<Integer, String> stateListWoTamilList = ConfigurationConstants.getInstance().getStateMap(1);
		Map<Integer, String> stateListWoTamilMap = new TreeMap<Integer, String>(stateListWoTamilList);
		stateListWoTamilMap.remove(1); // removing tamil nadu from state list
		candidateBean.setStateListWoTamil(stateListWoTamilMap);

		Map<Integer, String> districtList = ConfigurationConstants.getInstance().getDistrictMap(1);
		Map<Integer, String> districtListMap = new TreeMap<Integer, String>(districtList);
		districtListMap.remove(585);
		candidateBean.setDistrictList(districtListMap);
		
		Map<Integer, String> altDistrictList = ConfigurationConstants.getInstance().getDistrictMap(1);
		Map<Integer, String> altDistrictListMap = new TreeMap<Integer, String>(altDistrictList);
		altDistrictListMap.remove(585);
		candidateBean.setAltDistrictList(altDistrictListMap);
		
		Map<Integer, String> categoryList = ConfigurationConstants.getInstance().getCategoryListMap();
		candidateBean.setCategoryList(categoryList);

		if (StringUtils.isNotBlank(loggedInUser.getNationality())) {
			candidateBean.setNationality(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(loggedInUser.getNationality())));
		}

		if (StringUtils.isNotBlank(loggedInUser.getNativityCertDist())) {
			candidateBean.getPersonalDetailsBean().setAppliedDist(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(loggedInUser.getNativityCertDist())));
		}
		
		if (StringUtils.isNotBlank(loggedInUser.getCandidateName())) {
			candidateBean.getPersonalDetailsBean().setCandidateName(loggedInUser.getCandidateName());
		}
		
		if (StringUtils.isNotBlank(loggedInUser.getInitials())) {
			candidateBean.getPersonalDetailsBean().setCandidatePrefix(loggedInUser.getInitials());
		}

		if (StringUtils.isNotBlank(loggedInUser.getEmailAddress())) {
			candidateBean.getPersonalDetailsBean().setEmail(loggedInUser.getEmailAddress());
		}

		if (StringUtils.isNotBlank(loggedInUser.getMobile())) {
			candidateBean.getPersonalDetailsBean().setMobileNo(loggedInUser.getMobile());
		}
		
		if(StringUtils.isNotBlank(loggedInUser.getExServiceMen()))
		{
			candidateBean.setExServiceMen(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(loggedInUser.getExServiceMen())));
		}
		if(StringUtils.isNotBlank(loggedInUser.getDischargeDate()))
		{
			candidateBean.setDischargeDate(loggedInUser.getDischargeDate());
		}
		if(StringUtils.isNotBlank(loggedInUser.getPpoNumber()))
		{
			candidateBean.setPpoNumber(loggedInUser.getPpoNumber());
		}
		if(StringUtils.isNotBlank(loggedInUser.getCommCertYesNo()))
		{
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
		if(StringUtils.isNotBlank(loggedInUser.getDisableYesNo()))
		{
			candidateBean.setDisableYesNo(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(loggedInUser.getDisableYesNo())));
		}
		
		candidateBean.setDiffAbledChkBox(loggedInUser.isDiffAbledChkBox());
		
		if(StringUtils.isNotBlank(loggedInUser.getDisableType()))
		{
			candidateBean.setDisableType(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(loggedInUser.getDisableType())));
		}
		if(StringUtils.isNotBlank(loggedInUser.getDisablityPercent()))
		{
			candidateBean.setDisablityPercent(loggedInUser.getDisablityPercent());
		}
		if (loggedInUser.getDate_Of_Birth() != null && !loggedInUser.getDate_Of_Birth().equals("")) 
  		{ 
		  candidateBean.setDateOfBirth(loggedInUser.getDate_Of_Birth()); 
  		}
		if (loggedInUser.getGenderValDesc() != null && !loggedInUser.getGenderValDesc().equals("")) 
  		{ 
		  candidateBean.setGenderValDesc(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(loggedInUser.getGenderValDesc()))); 
  		}
		if (loggedInUser.getAge() != null && !loggedInUser.getAge().equals("")) 
  		{ 
		  candidateBean.setAge(loggedInUser.getAge()); 
  		}

		if (loggedInUser.getDisciplineID() != null && !loggedInUser.getDisciplineID().equals("")) {
			candidateBean.setDisciplinName(ConfigurationConstants.getInstance().getTestDetailsFromMapForTestPk(Integer.parseInt(loggedInUser.getDisciplineID())).getTestName());
		}
		
		if(StringUtils.isNotBlank(loggedInUser.getWidowYesNo()))
		{
			candidateBean.setWidowYesNo(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(loggedInUser.getWidowYesNo())));
		}
		if(StringUtils.isNotBlank(loggedInUser.getDesWidowCertNo()))
		{
			candidateBean.setDesWidowCertNo(loggedInUser.getDesWidowCertNo());
		}
		if(StringUtils.isNotBlank(loggedInUser.getWidowIssueDate()))
		{
			candidateBean.setWidowIssueDate(loggedInUser.getWidowIssueDate());
		}
		if(StringUtils.isNotBlank(loggedInUser.getWidowIssueAuth()))
		{
			candidateBean.setWidowIssueAuth(loggedInUser.getWidowIssueAuth());
		}
		if(StringUtils.isNotBlank(loggedInUser.getWidowDistrict()))
		{
			candidateBean.setWidowDistrict(districtListMap.get(Integer.parseInt(loggedInUser.getWidowDistrict())));
		}
		if(StringUtils.isNotBlank(loggedInUser.getWidowOtherDistrict()))
		{
			candidateBean.setWidowOtherDistrict(loggedInUser.getWidowOtherDistrict());
		}
		if(StringUtils.isNotBlank(loggedInUser.getWidowSubDivision()))
		{
			candidateBean.setWidowSubDivision(loggedInUser.getWidowSubDivision());
		}

	}

	private void populateCandidateDetails(CandidateBean candidateBean, Users users) throws Exception {
		CandidateBean newCandidateBean = null;
		String isInstLinkReqd = null;
		isInstLinkReqd = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.INSTRUCTION_FILED);
		if (isInstLinkReqd != null && !isInstLinkReqd.equals("") && isInstLinkReqd.equals(GenericConstants.INSTRUCTION_REQD)) {
			candidateBean.setInstrReqd(true);
		} else {
			candidateBean.setInstrReqd(false);
		}

		if (users.getUserFk() != 0 && users.getUserFk() != null && !users.getUserFk().equals("")) {
			newCandidateBean = candidatePersonalDetailsDAO.getCandidateDetails(users, candidateBean);

			PersonalDetailsBean personalDetailsBean = newCandidateBean.getPersonalDetailsBean();
			AddressBean addressBean = newCandidateBean.getAddressBean();

			candidateBean.setPersonalDetailsBean(personalDetailsBean);
			
			if (StringUtils.isNotBlank(newCandidateBean.getPersonalDetailsBean().getCandidateName())) {
				candidateBean.getPersonalDetailsBean().setCandidateName(newCandidateBean.getPersonalDetailsBean().getCandidateName());
			}
			
			if (StringUtils.isNotBlank(newCandidateBean.getPersonalDetailsBean().getMobileNo())) {
				candidateBean.getPersonalDetailsBean().setMobileNo(newCandidateBean.getPersonalDetailsBean().getMobileNo());
			}

			if (StringUtils.isNotBlank(newCandidateBean.getPersonalDetailsBean().getEmail())) {
				candidateBean.getPersonalDetailsBean().setEmail(newCandidateBean.getPersonalDetailsBean().getEmail());
			}
			
			candidateBean.getPersonalDetailsBean().setNationality(newCandidateBean.getPersonalDetailsBean().getNationality());
			
			if (StringUtils.isNotBlank(newCandidateBean.getDateOfBirth())) {
				candidateBean.setDateOfBirth(newCandidateBean.getDateOfBirth());
				candidateBean.setAge(newCandidateBean.getAge());
			}
			
			if (StringUtils.isNotBlank(newCandidateBean.getGenderValDesc())) {
				candidateBean.setGenderValDesc(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(newCandidateBean.getGenderValDesc())));
			}
			
			if (StringUtils.isNotBlank(newCandidateBean.getMariatalStatus())) {
				candidateBean.setMariatalStatus(newCandidateBean.getMariatalStatus());
			}
			
			if (StringUtils.isNotBlank(newCandidateBean.getSpouseName())) {
				candidateBean.setSpouseName(newCandidateBean.getSpouseName());
			}
			
			if (StringUtils.isNotBlank(newCandidateBean.getNativity())) {
				candidateBean.setNativity(newCandidateBean.getNativity());
			}
			
			if (StringUtils.isNotBlank(newCandidateBean.getOtherNativity())) {
				candidateBean.setOtherNativity(newCandidateBean.getOtherNativity());
			}
			
			if (newCandidateBean.getParentAndGuardian() != 0) {
			    candidateBean.setParentAndGuardian(newCandidateBean.getParentAndGuardian());
			}
			
			if (StringUtils.isNotBlank(newCandidateBean.getFathersName())) {
				candidateBean.setFathersName(newCandidateBean.getFathersName());
			}
			
			if (StringUtils.isNotBlank(newCandidateBean.getMothersName())) {
				candidateBean.setMothersName(newCandidateBean.getMothersName());
			}
			
			if (StringUtils.isNotBlank(newCandidateBean.getGuardianName())) {
				candidateBean.setGuardianName(newCandidateBean.getGuardianName());
			}
			
			if (StringUtils.isNotBlank(newCandidateBean.getReligionBelief())) {
				candidateBean.setReligionBelief(newCandidateBean.getReligionBelief());
			}
			
			if (StringUtils.isNotBlank(newCandidateBean.getReligionBeliefOthers())) {
				candidateBean.setReligionBeliefOthers(newCandidateBean.getReligionBeliefOthers());
			}
			
			if (StringUtils.isNotBlank(newCandidateBean.getCategorycertificate())) {
				candidateBean.setCategorycertificate(newCandidateBean.getCategorycertificate());
			}
			
			if (StringUtils.isNotBlank(newCandidateBean.getCategoryValDesc())) {
				candidateBean.setCategoryValDesc(newCandidateBean.getCategoryValDesc());
			}
			
			if (addressBean != null) {
				String permAddress = null;
				permAddress = addressBean.getAddress();
				if (permAddress != null && !permAddress.equals("")) {
					String[] temp = null;
					String delimiter = "\\$\\$";
					temp = permAddress.split(delimiter);
					if (temp != null) {
						for (int i = 0; i < temp.length; i++) {
							if (i == 0) {
								addressBean.setAddressFiled1(temp[i]);
							}
							if (i == 1) {
								addressBean.setAddressFiled2(temp[i]);
							}
							if (i == 2) {
								addressBean.setAddressFiled3(temp[i]);
							}
							if (i == 3) {
								addressBean.setAddressFiled4(temp[i]);
							}
						}

					}
				}

				String commAddress = null;
				commAddress = addressBean.getAlternateAddress();
				if (commAddress != null && !commAddress.equals("")) {
					String[] temp = null;
					String delimiter = "\\$\\$";
					temp = commAddress.split(delimiter);
					if (temp != null) {
						for (int i = 0; i < temp.length; i++) {
							if (i == 0) {
								addressBean.setAlternateAddressFiled1(temp[i]);
							}
							if (i == 1) {
								addressBean.setAlternateAddressFiled2(temp[i]);
							}
							if (i == 2) {
								addressBean.setAlternateAddressFiled3(temp[i]);
							}
							if (i == 3) {
								addressBean.setAlternateAddressFiled4(temp[i]);
							}
						}
					}
				}
				if (StringUtils.isNotBlank(newCandidateBean.getAddressBean().getPinCode())) {
					candidateBean.getAddressBean().setPinCode(newCandidateBean.getAddressBean().getPinCode());
				}
				
				if (StringUtils.isNotBlank(newCandidateBean.getAddressBean().getAlternatePinCode())) {
					candidateBean.getAddressBean().setAlternatePinCode(newCandidateBean.getAddressBean().getAlternatePinCode());
				}
				
				candidateBean.setAddressBean(addressBean);
			}
			
			if (StringUtils.isNotBlank(newCandidateBean.getStateVal())) {
				candidateBean.setStateVal(newCandidateBean.getStateVal());
			}
			
			if (StringUtils.isNotBlank(newCandidateBean.getAltStateVal())) {
				candidateBean.setAltStateVal(newCandidateBean.getAltStateVal());
			}
			
			if (StringUtils.isNotBlank(newCandidateBean.getDistrictVal())) {
				candidateBean.setDistrictVal(newCandidateBean.getDistrictVal());
			}
			if (StringUtils.isNotBlank(newCandidateBean.getDistrictValother())) {
				candidateBean.setDistrictValother(newCandidateBean.getDistrictValother());
			}
			
			if (StringUtils.isNotBlank(newCandidateBean.getAltDistrictVal())) {
				candidateBean.setAltDistrictVal(newCandidateBean.getAltDistrictVal());
			}
			if (StringUtils.isNotBlank(newCandidateBean.getAltDistrictValOthers())) {
				candidateBean.setAltDistrictValOthers(newCandidateBean.getAltDistrictValOthers());
			}
			
			
			if (StringUtils.isNotBlank(newCandidateBean.getAddressBean().getCityName())) {
				candidateBean.getAddressBean().setCityName(newCandidateBean.getAddressBean().getCityName());
			}
			
			if (StringUtils.isNotBlank(newCandidateBean.getAddressBean().getAlternateCity())) {
				candidateBean.getAddressBean().setAlternateCity(newCandidateBean.getAddressBean().getAlternateCity());
			}
			
			if (StringUtils.isNotBlank(newCandidateBean.getExServiceMen())) {
				candidateBean.setExServiceMen(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(newCandidateBean.getExServiceMen())));
			}
			if (StringUtils.isNotBlank(newCandidateBean.getDischargeDate())) {
				candidateBean.setDischargeDate(newCandidateBean.getDischargeDate());
			}
			if (StringUtils.isNotBlank(newCandidateBean.getPpoNumber())) {
				candidateBean.setPpoNumber(newCandidateBean.getPpoNumber());
			}
			if (StringUtils.isNotBlank(newCandidateBean.getCommCertYesNo())) {
				candidateBean.setCommCertYesNo(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(newCandidateBean.getCommCertYesNo())));
			}
			if (StringUtils.isNotBlank(newCandidateBean.getCommunity())) {
				candidateBean.setCommunity(ConfigurationConstants.getInstance().getCategoryValueForKey(Integer.parseInt(newCandidateBean.getCommunity())));
			}
			if (StringUtils.isNotBlank(newCandidateBean.getSubCaste())) {
				candidateBean.setSubCaste(newCandidateBean.getSubCaste());
			}
			if (StringUtils.isNotBlank(newCandidateBean.getIssueAuthCommCert())) {
				candidateBean.setIssueAuthCommCert(newCandidateBean.getIssueAuthCommCert());
			}
			if (StringUtils.isNotBlank(newCandidateBean.getCommCertNo())) {
				candidateBean.setCommCertNo(newCandidateBean.getCommCertNo());
			}
			if (StringUtils.isNotBlank(newCandidateBean.getCommCertPlace())) {
				candidateBean.setCommCertPlace(newCandidateBean.getCommCertPlace());
			}
			if (StringUtils.isNotBlank(newCandidateBean.getCommIssueDate())) {
				candidateBean.setCommIssueDate(newCandidateBean.getCommIssueDate());
			}
			if (StringUtils.isNotBlank(newCandidateBean.getDisableYesNo())) {
				candidateBean.setDisableYesNo(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(newCandidateBean.getDisableYesNo())));
			}
			if (StringUtils.isNotBlank(newCandidateBean.getDisableType())) {
				candidateBean.setDisableType(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(newCandidateBean.getDisableType())));
			}
			if (StringUtils.isNotBlank(newCandidateBean.getDisablityPercent())) {
				candidateBean.setDisablityPercent(newCandidateBean.getDisablityPercent());
			}
			if (StringUtils.isNotBlank(newCandidateBean.getIsGovtService())) {
				candidateBean.setIsGovtService(newCandidateBean.getIsGovtService());
			}
			
			if(newCandidateBean.isGovtServChkBox())
			candidateBean.setGovtServChkBox(newCandidateBean.isGovtServChkBox());

			if (StringUtils.isNotBlank(newCandidateBean.getOrgName())) {
				candidateBean.setOrgName(newCandidateBean.getOrgName());
			}
			if (StringUtils.isNotBlank(newCandidateBean.getCurrentDesig())) {
				candidateBean.setCurrentDesig(newCandidateBean.getCurrentDesig());
			}
			if (StringUtils.isNotBlank(newCandidateBean.getPlaceOfWork())) {
				candidateBean.setPlaceOfWork(newCandidateBean.getPlaceOfWork());
			}
			if (StringUtils.isNotBlank(newCandidateBean.getPhotoIDProof1())) {
				candidateBean.setPhotoIDProof1(newCandidateBean.getPhotoIDProof1());
			}
			if (StringUtils.isNotBlank(newCandidateBean.getPhotoIDProof1Val())) {
				candidateBean.setPhotoIDProof1Val(newCandidateBean.getPhotoIDProof1Val());
			}
			
			if (StringUtils.isNotBlank(newCandidateBean.getMotherTongue())) {
				candidateBean.setMotherTongue(newCandidateBean.getMotherTongue());
			}
			
			if (StringUtils.isNotBlank(newCandidateBean.getGovtDate())) {
				candidateBean.setGovtDate(newCandidateBean.getGovtDate());
			}
			
			if (StringUtils.isNotBlank(newCandidateBean.getGovtAge())) {
				String age[] = newCandidateBean.getGovtAge().split(" ");
				candidateBean.setAgeInYears(age[0]);
				candidateBean.setAgeInMonths(age[2]);
				candidateBean.setAgeInDays(age[4]);
			}
			
			
			
			candidateBean.setAddressChkBox(newCandidateBean.isAddressChkBox());
			candidateBean.setStatusID(newCandidateBean.getStatusID());
		} else {
			candidateBean.setCountryVal("1");
			candidateBean.setAltCountryVal("1");
		}
	}

	@Override
	public Map<Integer, String> getOtherNativeList() throws Exception {

		return candidateDao.getOtherNativeList();
	}

}
