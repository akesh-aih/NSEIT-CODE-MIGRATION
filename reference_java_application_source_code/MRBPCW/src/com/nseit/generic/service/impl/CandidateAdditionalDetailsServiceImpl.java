package com.nseit.generic.service.impl;

import static com.nseit.generic.util.GenericConstants.SESSION_USER;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.nseit.generic.dao.CandidateAdditionalDetailsDao;
import com.nseit.generic.dao.CandidateDao;
import com.nseit.generic.dao.CommonDao;
import com.nseit.generic.models.AdditionalDetailsBean;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.CandidateAdditionalDetailsService;
import com.nseit.generic.service.CandidatePersonalDetailsService.ErrorCodeEnum;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;

public class CandidateAdditionalDetailsServiceImpl implements CandidateAdditionalDetailsService {

	private CandidateDao candidateDao;
	private CommonDao commonDao;
	private CandidateAdditionalDetailsDao candidateAdditionalDetailsDao;

	public void setCandidateDao(CandidateDao candidateDAO) {
		this.candidateDao = candidateDAO;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public void setCandidateAdditionalDetailsDao(CandidateAdditionalDetailsDao candidateAdditionalDetailsDao) {
		this.candidateAdditionalDetailsDao = candidateAdditionalDetailsDao;
	}

	@Override
	public String getCandidateAdditionalDetails(Users loggedInUser, CandidateBean candidateBean, Map<String, Object> sessionAttributes, HttpServletRequest request)
			throws Exception {

		String result = null;

		Map<Integer, String> advertList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Advert_List");
		Map<Integer, String> advertMap = new TreeMap<Integer, String>(advertList);
		candidateBean.setAdvertList(advertMap);
		Map<Integer, String> yesNo = ConfigurationConstants.getInstance().getParameterMapForDomainName("Yes_No_Map");
		Map<Integer, String> yesNoMap = new TreeMap<Integer, String>(yesNo);
		candidateBean.setYesNo(yesNoMap);
		candidateBean.setRefStateList(ConfigurationConstants.getInstance().getStateMap(1));
		
		List<Integer> yearList = new ArrayList<Integer>();
		String dob = loggedInUser.getDate_Of_Birth();
		String year[] = dob.split("-"); //todo
		int start = Integer.parseInt(year[2])+10;
		/*int start = 1980;*/
		int end = 2023;
		while(start <= end) {
			yearList.add(start++);
		}
		candidateBean.setYearsAppliedList(yearList);

		AdditionalDetailsBean additionalDetailsBean = candidateAdditionalDetailsDao.getCandidateAdditionalDetails(loggedInUser.getUserId());
		if (additionalDetailsBean != null) {
			if (additionalDetailsBean.getYearsOfApply() != null && !additionalDetailsBean.getYearsOfApply().equals("")) {
				String selectedYears = additionalDetailsBean.getYearsOfApply();
			    String yearsSelectedList[] = selectedYears.split(", ");
			    List<String> yearsList = new ArrayList<String>(yearsSelectedList.length);
			    for(int i=0; i<yearsSelectedList.length; i++) {
			    	yearsList.add(yearsSelectedList[i]);
			    }
			    additionalDetailsBean.setYearsOfApplyList(yearsList);
			}
			if (additionalDetailsBean.getRef1State() != null && !additionalDetailsBean.getRef1State().equals("")) {
				candidateBean.setRef1DistrictListMap(ConfigurationConstants.getInstance().getDistrictMap(Integer.parseInt(additionalDetailsBean.getRef1State())));
				Map<Integer, String> districtMap = ConfigurationConstants.getInstance().getDistrictMap(Integer.parseInt(additionalDetailsBean.getRef1State()));
				candidateBean.setRef1DistrictList(districtMap);
			}
			if (additionalDetailsBean.getRef2State() != null && !additionalDetailsBean.getRef2State().equals("")) {
				candidateBean.setRef2DistrictListMap(ConfigurationConstants.getInstance().getDistrictMap(Integer.parseInt(additionalDetailsBean.getRef2State())));
				Map<Integer, String> ref2DistrictMap = ConfigurationConstants.getInstance().getDistrictMap(Integer.parseInt(additionalDetailsBean.getRef2State()));
				candidateBean.setRef2DistrictList(ref2DistrictMap);
			}
			
			
			candidateBean.setAdditionalDetailsBean(additionalDetailsBean);
			candidateBean.setCandiateDetailsMandatory(false);
		}
		
		result = "additionalDetailsForm";
		candidateBean.setServerSideErrorMessage("false");
		candidateBean.setTestFlag("false");
		candidateBean.setSaveFlag("false");

		Users users = candidateDao.getBasicCandidateInfo(loggedInUser);

		Users users1 = (Users) sessionAttributes.get(SESSION_USER);
		users1.setGender(users.getGender());
		users1.setStatus(users.getStatus());
		sessionAttributes.put(SESSION_USER, users1);

		//candidateBean.setGenderVal(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(users.getGender())));
		candidateBean.setGenderVal(users.getGender());
		candidateBean.setStatus(users.getStatus());
		candidateBean.setDisciplineId(Integer.parseInt(loggedInUser.getDisciplineID())); //todo 99 to 105
		
		//candidateBean.setDisciplineId(1);

		if (request.getAttribute("menuKey") != null) {
			String mandatory = ConfigurationConstants.getInstance().getMenuKeyMandatory(request.getAttribute("menuKey").toString());
			if (mandatory != null && mandatory.equals(GenericConstants.MANDATORY)) {
				candidateBean.setCandiateDetailsMandatory(true);
			}
		}
		return result;
	}

	@Override
	public ErrorCodeEnum saveCandidateAdditionalDetails(CandidateBean candidateBean, Users loggedInUser, Map<String, Object> sessionAttributes, HttpServletRequest request)
			throws Exception {
		int saveAdditionalDetails = 0;

		AdditionalDetailsBean additionalDetailsBean = candidateBean.getAdditionalDetailsBean();

		String dataString = "Candidate User ID: " + loggedInUser.getUsername() + " has saved Additional Details as " + " || Academic Awards (If any) : "
				+ additionalDetailsBean.getAcademicAward() + " || Where did you see the advertisement? : " + additionalDetailsBean.getAdvertisement()
				+ " || Have you applied for this programme in the past? : " + additionalDetailsBean.getAppliedInPast() + " || Year(s) of application : "
				+ additionalDetailsBean.getYearsOfApply() + " || If you were offered admission, give brief reasons for not joining the programme : "
				+ additionalDetailsBean.getReasonForNotJoining() + " || Statement of Purpose : " + additionalDetailsBean.getStmtOfPurpose() + " || Other Information : "
				+ additionalDetailsBean.getOtherInfo() + " || Referee 1 - Name : " + additionalDetailsBean.getRef1Name() + " || Referee 1 - Designation : "
				+ additionalDetailsBean.getRef1Desig() + " || Referee 1 - Is he/she an academician? : " + additionalDetailsBean.getRef1IsAcademician()
				+ " || Referee 1 - Address Line 1 : " + additionalDetailsBean.getRef1Add1() + " || Referee 1 - Address Line 2 : " + additionalDetailsBean.getRef1Add2()
				+ " || Referee 1 - State / Union Territory : " + additionalDetailsBean.getRef1State() + " || Referee 1 - District : " + additionalDetailsBean.getRef1District()
				+ " || Referee 1 - City / Village / Town : " + additionalDetailsBean.getRef1City() + " || Referee 1 - Pin-code : " + additionalDetailsBean.getRef1Pincode()
				+ " || Referee 1 - Telephone number / International Phone Number / Mobile number : " + additionalDetailsBean.getRef1Contact() + " || Referee 2 - Name : "
				+ additionalDetailsBean.getRef2Name() + " || Referee 2 - Designation : " + additionalDetailsBean.getRef2Desig() + " || Referee 2 - Is he/she an academician? : "
				+ additionalDetailsBean.getRef2IsAcademician() + " || Referee 2 - Address Line 1 : " + additionalDetailsBean.getRef2Add1() + " || Referee 2 - Address Line 2 : "
				+ additionalDetailsBean.getRef2Add2() + " || Referee 2 - State / Union Territory : " + additionalDetailsBean.getRef2State() + " || Referee 2 - District : "
				+ additionalDetailsBean.getRef2District() + " || Referee 2 - City / Village / Town : " + additionalDetailsBean.getRef2City() + " || Referee 2 - Pin-code : "
				+ additionalDetailsBean.getRef2Pincode() + " || Referee 2 - Telephone number / International Phone Number / Mobile number : "
				+ additionalDetailsBean.getRef2Contact()+ " || Referee 1 - Email : " +additionalDetailsBean.getRef1EmailAddress()
				+ " || Referee 2 - Email : " +additionalDetailsBean.getRef2EmailAddress();

		saveAdditionalDetails = candidateAdditionalDetailsDao.saveCandidateAdditionalDetails(additionalDetailsBean, loggedInUser);

		if (saveAdditionalDetails > 0) {
			candidateBean.setDataFound(true);
			candidateBean.setSaveFlag("true");
			candidateBean.setTestFlag("true");
			commonDao.insertCandidateAuditrail(loggedInUser, "Saved Additional Details:", dataString);

			/*Map<Integer, String> advertList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Advert_List");
			Map<Integer, String> advertMap = new TreeMap<Integer, String>(advertList);
			candidateBean.setAdvertList(advertMap);
			Map<Integer, String> yesNo = ConfigurationConstants.getInstance().getParameterMapForDomainName("Yes_No_Map");
			Map<Integer, String> yesNoMap = new TreeMap<Integer, String>(yesNo);
			candidateBean.setYesNo(yesNoMap);
			candidateBean.setRefStateList(ConfigurationConstants.getInstance().getStateMap(1));
			List<Integer> yearList = new ArrayList<Integer>();
			String dob = loggedInUser.getDate_Of_Birth();
			String year[] = dob.split("-");
			int start = Integer.parseInt(year[2])+10;
			int end = 2022;
			while(start <= end) {
				yearList.add(start++);
			}
			candidateBean.setYearsAppliedList(yearList);*/
			
			loggedInUser.setRemoteIp(request.getRemoteAddr());
			commonDao.insertCandidateAuditrail(loggedInUser, "Save & Continue",
					"Candidate with User ID" + loggedInUser.getUsername() + " has clicked Save & Continue on Additional Details page.");
			return ErrorCodeEnum.UPDATE_CANDIDATE_STAGE;
		} else {
			return ErrorCodeEnum.ERROR;
		}
	}

}
