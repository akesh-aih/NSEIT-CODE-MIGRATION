package com.nseit.generic.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.mail.javamail.JavaMailSender;

import com.nseit.generic.dao.CandidateAdditionalDetailsDao;
import com.nseit.generic.dao.CandidateDao;
import com.nseit.generic.dao.CandidateEducationDao;
import com.nseit.generic.dao.CommonDao;
import com.nseit.generic.dao.CovidDutyCertificateDao;
import com.nseit.generic.dao.WorkExperienceDao;
import com.nseit.generic.models.AdditionalDetailsBean;
import com.nseit.generic.models.AddressBean;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.CandidateDocBean;
import com.nseit.generic.models.CommonBean;
import com.nseit.generic.models.CovidDutyCertDetailsBean;
import com.nseit.generic.models.CovidDutyCertificateBean;
import com.nseit.generic.models.EducationDetailsBean;
import com.nseit.generic.models.PersonalDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.models.WorkExperienceBean;
import com.nseit.generic.models.WorkExperienceDetailsBean;
import com.nseit.generic.service.CandidateService;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.GenericServiceException;
import com.nseit.generic.util.LoggerHome;

public class CandidateServiceImpl implements CandidateService {
	private CandidateDao candidateDao;
	private CandidateEducationDao candidateEducationDao;
	private JavaMailSender mailSender;
	private CommonDao commonDao;
	private Logger logger = LoggerHome.getLogger(getClass());
	private WorkExperienceDao workExperienceDao;
	private CovidDutyCertificateDao covidDutyCertificateDao;
	private CandidateAdditionalDetailsDao candidateAdditionalDetailsDao;

	public void setWorkExperienceDao(WorkExperienceDao workExperienceDao) {
		this.workExperienceDao = workExperienceDao;
	}
	
	public void setCovidDutyCertificateDao(CovidDutyCertificateDao covidDutyCertificateDao) {
		this.covidDutyCertificateDao = covidDutyCertificateDao;
	}

	public void setCandidateDao(CandidateDao candidateDAO) {
		this.candidateDao = candidateDAO;
	}

	public void setcandidateEducationDao(CandidateEducationDao candidateEducationDao) {
		this.candidateEducationDao = candidateEducationDao;
	}

	public void setcandidateAdditionalDetailsDao(CandidateAdditionalDetailsDao candidateAdditionalDetailsDao) {
		this.candidateAdditionalDetailsDao = candidateAdditionalDetailsDao;
	}
	
	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public int insertCandidateImages(CandidateBean candidateBean, Users loggedInUser) throws GenericServiceException {
		int insertCandidateImage = 0;
		try {
			boolean imageExists = candidateDao.checkCandidateImageExist(loggedInUser);
			insertCandidateImage = candidateDao.insertCandidateImages(candidateBean, loggedInUser,imageExists);
		} catch (Exception ex) {
			try {
				throw new GenericException(ex);
			} catch (GenericException e) {
				throw new GenericServiceException(e);
			}
		}
		return insertCandidateImage;
	}

	public CandidateBean getCandidateImage(Long userId) throws Exception {

		CandidateBean newCandidateBean = null;
		try {
			newCandidateBean = candidateDao.getCandidateImage(userId);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return newCandidateBean;
	}

	public Users getBasicCandidateInfo(Users user) throws Exception {
		return candidateDao.getBasicCandidateInfo(user);
	}

	public boolean checkIfImageUploaded(Users users) throws Exception {

		try {
			return candidateDao.checkIfImageUploaded(users);
		} catch (Exception ex) {
			throw new GenericServiceException(ex);
		}
	}

	public List<CandidateDocBean> getDocumentUploaded(Users users) throws Exception {
		try {
			return candidateDao.getDocumentUploaded(users);
		} catch (Exception ex) {
			throw new GenericServiceException(ex);
		}
	}

	public int updateRegDate(Users users) throws Exception {

		int update = 0;
		try {
			update = candidateDao.updateRegDate(users);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return update;
	}

	public int insertCandidateSignature(CandidateBean candidateBean, Users loggedInUser) throws GenericServiceException {

		int insertCandidateImage = 0;
		try {
			boolean signExists = candidateDao.checkCandidateSignExists(loggedInUser);
			insertCandidateImage = candidateDao.insertCandidateSignature(candidateBean, loggedInUser,signExists);
		} catch (Exception ex) {
			try {
				throw new GenericException(ex);
			} catch (GenericException e) {
				throw new GenericServiceException(e);
			}
		}

		return insertCandidateImage;

	}

	// new add
	public CandidateBean getCandidateSignature(Long userId) throws Exception {

		CandidateBean newCandidateBean = null;
		try {
			newCandidateBean = candidateDao.getCandidateSignature(userId);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return newCandidateBean;
	}

	public boolean checkIfSignatureUploaded(Users users) throws Exception {

		try {
			return candidateDao.checkIfSignatureUploaded(users);
		} catch (Exception ex) {
			throw new GenericServiceException(ex);
		}
	}

	public int insertCandidateDocuments(String fileName, byte[] file, String flagValue, String getCheckCandidateAcceptance1, Users loggedInUser) throws Exception {
		int recordCount = 0;
		try {
			recordCount = candidateDao.insertCandidateDocuments(fileName, file, flagValue, getCheckCandidateAcceptance1, loggedInUser);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		return recordCount;
	}

	public boolean isUserExists(Users user, CandidateBean candidateBean) throws Exception {
		boolean isUserExists = false;
		try {
			isUserExists = candidateDao.isUserExists(user, candidateBean);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		return isUserExists;
	}

	public String getCandidateDocPk(Users user, CandidateBean candidateBean) throws Exception {
		String candidateDocPk = null;
		try {
			candidateDocPk = candidateDao.getCandidateDocPk(user, candidateBean);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		return candidateDocPk;
	}

	public int updateCandidateDocuments(String candidateDocPk, String fileName, byte[] file, String checkbox, Users loggedInUser) throws Exception {
		int recordCount = 0;
		try {
			recordCount = candidateDao.updateCandidateDocuments(candidateDocPk, fileName, file, checkbox, loggedInUser);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		return recordCount;
	}

	public Map<String, String> getCandidateDataForPaymentReceipt(Users loggedInUser) throws Exception {
		Map<String, String> paymentRelatedDataList = null;
		try {
			paymentRelatedDataList = candidateDao.getCandidateDataForPaymentReceipt(loggedInUser);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		return paymentRelatedDataList;
	}

	public CandidateBean getDocument(String docId, Users user) throws GenericException {
		return candidateDao.getDocument(docId, user);
	}

	public CandidateBean getCandidateDetailsForFinalPrintPage(Users loggedInUser) throws Exception {

		CandidateBean newCandidateBean = null;
		try {
			newCandidateBean = candidateDao.getCandidateDetailsForFinalPrintPage(loggedInUser);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return newCandidateBean;
	}

	public int checkDataForUserExistInPaymentDetails(Users users) throws Exception {
		int checkDataForUserExistInPaymentDetails = 0;
		try {
			checkDataForUserExistInPaymentDetails = candidateDao.checkDataForUserExistInPaymentDetails(users);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return checkDataForUserExistInPaymentDetails;
	}

	public CandidateBean getCandidateDetailsForDashboard(Users loggedInUser) throws Exception {

		CandidateBean newCandidateBean = null;
		try {
			newCandidateBean = candidateDao.getCandidateDetailsForDashboard(loggedInUser);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return newCandidateBean;
	}

	public String getUserName(Long userId) throws Exception {
		return candidateDao.getUserName(userId);
	}

	public List<CommonBean> getDisciplineList(Users loggedInUser) throws Exception {
		return candidateDao.getDisciplineList();
	}

	public int[] insertCandidateAcademicDetails(final List<EducationDetailsBean> candiadteList, final Users users, String sslcTamil) throws Exception {
		return candidateDao.insertCandidateAcademicDetails(candiadteList, users, sslcTamil);
	}

	public int[] updateCandidateAcademicDetails(final List<EducationDetailsBean> candiadteList, final Users users) throws Exception {
		return candidateDao.updateCandidateAcademicDetails(candiadteList, users);
	}

	public List<EducationDetailsBean> getCandiateAcademicDetails(CandidateBean candidateBean, Users users) throws Exception {
		return candidateDao.getCandiateAcademicDetails(candidateBean, users);
	}

	public int deleteAcademicDetailsEntryForCandidate(List<EducationDetailsBean> candiadteList, Users users, String sslcTamil) throws Exception {
		return candidateDao.deleteAcademicDetailsEntryForCandidate(candiadteList, users, sslcTamil);
	}

	public List<EducationDetailsBean> getCandidateDataForAcademicDetails(CandidateBean candidateBean, Users users) throws Exception {
		return candidateDao.getCandidateDataForAcademicDetails(candidateBean, users);
	}

	public List<EducationDetailsBean> getGraduateDetailsForCandidate(Long usrFk) throws Exception {
		return candidateDao.getGraduateDetailsForCandidate(usrFk);
	}

	public List<EducationDetailsBean> populateEducationalDetailsForFinalPrintPage(CandidateBean candidateBean, Users loggedInUser) throws Exception {
		List<EducationDetailsBean> educationDetailsList = null;
		try {
			educationDetailsList = candidateDao.getGraduateDetailsForCandidate(loggedInUser.getUserFk());
			if (educationDetailsList.size() >= 4) {
				boolean allMatch = true;
				// Check if mediumOfInstruction for index 0, 1, and 2 is 161
				for (int i = 0; i < 3; i++) {
					if (!"Tamil".equalsIgnoreCase(educationDetailsList.get(i).getMedOfInstruction())) {
						allMatch = false;
						break;
					}
				}
				if (!allMatch) {
					educationDetailsList.get(5).setPstmPreferenceVal("NO");
				}
			}

			candidateBean.setEducationDtlsList(educationDetailsList);
			candidateBean.setEducationDetailsDisplayFlag(true);
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return educationDetailsList;
	}

	public List<EducationDetailsBean> populateAddEducationalDetailsForFinalPrintPage(Users loggedInUser, CandidateBean candidateBean) {
		List<EducationDetailsBean> educationDetailsList = null;
		try {
			candidateBean.setEducationDetailsBean(candidateDao.getAdditionalEduDetailsForCandidate(loggedInUser.getUserFk()));
			if (candidateBean.getEducationDetailsBean() != null) {
				candidateBean.setDoeacc(candidateBean.getEducationDetailsBean().getDoeacc());
				candidateBean.setTerriArmy(candidateBean.getEducationDetailsBean().getTerriArmy());
				candidateBean.setCertiB(candidateBean.getEducationDetailsBean().getCertiB());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return educationDetailsList;
	}

	public int updateValidateStatus(Users users) throws Exception {
		return candidateDao.updateValidateStatus(users);
	}

	public String getValidateStatus(Long userId) throws Exception {
		return candidateDao.getValidateStatus(userId);
	}

	@Override
	public int updateStatus(Users loggedInUser, String status) throws Exception {
		// TODO Auto-generated method stub
		int updateStatus = 0;
		try {
			updateStatus = candidateDao.updateCandidateStatus(loggedInUser, status);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		return updateStatus;
	}

	@Override
	public int updateCandidate(Users loggedInUser, CandidateBean candidateBean) throws Exception {
		int updateCandidateDetails = 0;
		try {
			updateCandidateDetails = candidateDao.updateCandidate(loggedInUser, candidateBean);

		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		return updateCandidateDetails;
	}

	@Override
	public int getDocCountInDB(Users loggedInUser, String checkingDocInDB) throws Exception {
		return candidateDao.getDocCountInDB(loggedInUser, checkingDocInDB);
	}

	@Override
	public Map<Integer, String> getExaminationList() throws Exception {
		return candidateDao.getExaminationList();
	}

	@Override
	public Map<Integer, String> getDegreeList() throws Exception {
		// TODO Auto-generated method stub
		return candidateDao.getDegreeList();
	}

	@Override
	public Map<Integer, String> getDiplomaList() throws Exception {
		// TODO Auto-generated method stub
		return candidateDao.getDiplomaList();
	}

	@Override
	public Map<Integer, String> getAddExaminationList() throws Exception {
		// TODO Auto-generated method stub
		return candidateDao.getAddExaminationList();
	}

	@Override
	public Map<Integer, String> getPgList() throws Exception {
		return candidateDao.getPgList();
	}

	@Override
	public Map<Integer, String> getAddDegreeList() throws Exception {
		return candidateDao.getAddDegreeList();
	}

	@Override
	public Map<Integer, String> getAddDiplomaList() throws Exception {
		return candidateDao.getAddDiplomaList();
	}

	@Override
	public Map<Integer, String> getAddPgList() throws Exception {
		return candidateDao.getAddPgList();
	}

	public int deletePreviousDocumentsforAll(String combination, Users loggedInUser) throws Exception {
		int recordCount = 0;
		try {
			recordCount = candidateDao.deletePreviousDocumentsforAll(combination, loggedInUser);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		return recordCount;
	}

	public int deleteDocumentsforAdditionaliDoc(String combination, Users loggedInUser) throws Exception {
		int recordCount = 0;
		try {
			recordCount = candidateDao.deleteDocumentsforAdditionaliDoc(combination, loggedInUser);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}
		return recordCount;
	}

	public String getCandidateDob(Users loggedInUser) throws Exception {
		return candidateDao.getCandidateDob(loggedInUser);
	}

	public String getCandidateChallanDownloadedDate(Users loggedInUser) throws Exception {
		return candidateDao.getCandidateChallanDownloadedDate(loggedInUser);
	}

	public int checkCandidateAlreadyAppliedPostforID(Users loggedInUser) throws Exception {
		return candidateDao.checkCandidateAlreadyAppliedPostforID(loggedInUser);
	}

	public Long getUsersStatusId(String userName) throws Exception {
		return candidateDao.getUsersStatusId(userName);
	}

	@Override
	public String insertPDFJsonString1(long UserFk, long testfk, String encodedBytes, String UserId) throws Exception {
		return candidateDao.insertPDFJsonString1(UserFk, testfk, encodedBytes, UserId);
	}

	@Override
	public String getStateList(CandidateBean candidateBean) throws Exception {
		Map<Integer, String> stateListMap = new LinkedHashMap<Integer, String>();
		String countryVal = null;
		String stateVal = "";

		countryVal = candidateBean.getCountryVal();
		if (countryVal != null && !countryVal.equals("") && !countryVal.equals("null")) {
			stateListMap = ConfigurationConstants.getInstance().getStateMap(Integer.parseInt(countryVal));
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

		return stateVal;
	}

	@Override
	public String getUTList(CandidateBean candidateBean) throws Exception {
		Map<Integer, String> utListMap = new LinkedHashMap<Integer, String>();
		String countryVal = null;
		String utVal = "";
		countryVal = candidateBean.getCountryVal();
		if (countryVal != null && !countryVal.equals("") && !countryVal.equals("null")) {
			utListMap = ConfigurationConstants.getInstance().getUTMap(Integer.parseInt(countryVal));
		}

		if (utListMap != null) {
			for (Map.Entry<Integer, String> entry : utListMap.entrySet()) {
				if (entry != null) {
					if (entry.getValue() != null && !entry.getValue().equals("") && entry.getKey() != null && !entry.getKey().equals("")) {
						utVal = utVal + (CommonUtil.getParameter(entry.getValue()) + "#" + entry.getKey()) + ",";
					}
				}
			}
		}

		if (utVal != null && !utVal.equals("") && utVal.endsWith(",")) {
			utVal = utVal.substring(0, utVal.length() - 1);
		}

		return utVal;
	}

	@Override
	public String getCityList(CandidateBean candidateBean) throws Exception {
		Map<Integer, String> PoliceStationListMap = new LinkedHashMap<Integer, String>();
		String PresentPostingUnit = null;
		String PresentPostingUnit1 = "";
		PresentPostingUnit = candidateBean.getDistrictVal();

		if (PresentPostingUnit != null && !PresentPostingUnit.equals("") && !PresentPostingUnit.equals("null")) {

			PoliceStationListMap = ConfigurationConstants.getInstance().getPoliceStationMap1(Integer.parseInt(PresentPostingUnit));
		}

		if (PoliceStationListMap != null) {
			for (Map.Entry<Integer, String> entry : PoliceStationListMap.entrySet()) {
				if (entry != null) {
					if (entry.getValue() != null && !entry.getValue().equals("") && entry.getKey() != null && !entry.getKey().equals("")) {
						PresentPostingUnit1 = PresentPostingUnit1 + (entry.getValue() + "#" + entry.getKey()) + ",";
					}
				}
			}
		}

		if (PresentPostingUnit1 != null && !PresentPostingUnit1.equals("") && PresentPostingUnit1.endsWith(",")) {
			PresentPostingUnit1 = PresentPostingUnit1.substring(0, PresentPostingUnit1.length() - 1);
		}
		return PresentPostingUnit1;
	}

	@Override
	public String getDistrictList(CandidateBean candidateBean) throws Exception {
		Map<Integer, String> districtListMap = new LinkedHashMap<Integer, String>();

		String stateId = null;
		String districtVal = "";
		stateId = candidateBean.getStateVal();
		if (stateId != null && !stateId.equals("")) {
			stateId = stateId;
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

		return districtVal;
	}

	@Override
	public String getAlternateDistrictList(CandidateBean candidateBean) throws Exception {
		Map<Integer, String> districtListMap = new LinkedHashMap<Integer, String>();
		String stateId = null;
		String districtVal = "";

		stateId = candidateBean.getAltStateVal();
		if (stateId != null && !stateId.equals("") && !stateId.equals("null")) {
			districtListMap = ConfigurationConstants.getInstance().getDistrictMap(Integer.parseInt(stateId));
		}

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
		return districtVal;
	}

	@Override
	public String getSubcasteList(CandidateBean candidateBean) throws Exception {
		Map<Integer, String> utListMap = new LinkedHashMap<Integer, String>();
		String countryVal = null;
		String utVal = "";
		countryVal = candidateBean.getCategoryVal();
		if (countryVal != null && !countryVal.equals("") && !countryVal.equals("null")) {
			utListMap = ConfigurationConstants.getInstance().getSubCategoryListMap1(Integer.parseInt(countryVal));
		}

		if (utListMap != null) {
			for (Map.Entry<Integer, String> entry : utListMap.entrySet()) {
				if (entry != null) {
					if (entry.getValue() != null && !entry.getValue().equals("") && entry.getKey() != null && !entry.getKey().equals("")) {
						utVal = utVal + (CommonUtil.getParameter(entry.getValue()) + "#" + entry.getKey()) + "*";
					}
				}
			}
		}

		if (utVal != null && !utVal.equals("") && utVal.endsWith("*")) {
			utVal = utVal.substring(0, utVal.length() - 1);
		}
		return utVal;
	}

	@Override
	public String getPaymentStatusForPdfApplicationNumber(long userFk, long testfk) throws Exception {
		return candidateDao.getPaymentStatusForPdfApplicationNumber(userFk, testfk);
	}

	@Override
	public String getCandidatePhotoName(Long userId) throws Exception {
		return candidateDao.getCandidatePhotoName(userId);
	}

	@Override
	public String getCandidateSignatureName(Long userId) throws Exception {
		return candidateDao.getCandidateSignatureName(userId);
	}

	@Override
	public int checkMobNo(CandidateBean candidateBean) throws Exception {
		return candidateDao.checkMobNo(candidateBean);
	}

	@Override
	public int exceededMOBAttempts(String mobileNo1, String courseVal) {
		int attempts = candidateDao.exceededMOBAttempts(mobileNo1, courseVal);
		return attempts;
	}

	@Override
	public String getCandidateCategory(Users loggedInUser) throws Exception {
		return candidateDao.getCandidateCategory(loggedInUser);
	}

	@Override
	public Map<Integer, String> getOtherNativeList() throws Exception {

		return candidateDao.getOtherNativeList();
	}

	@Override
	public String saveScoreCardDownloadStatus(String downloadFlag, Users loggedInUser) {
		return candidateDao.saveScoreCardDownloadStatus(downloadFlag, loggedInUser);
	}

	@Override
	public String saveCallLetterDownloadStatus(String downloadFlag, Users loggedInUser) {
		return candidateDao.saveCallLetterDownloadStatus(downloadFlag, loggedInUser);
	}
	
	@Override
	public String savePracticeTestStatus(String downloadFlag, Users loggedInUser) {
		return candidateDao.savePracticeTestStatus(downloadFlag,loggedInUser);
	}

	@Override
	public String saveAdmitCardDownloadStatus(String downloadFlag, Users loggedInUser) {
		return candidateDao.saveAdmitCardDownloadStatus(downloadFlag, loggedInUser);
	}

	@Override
	public String getUserDisability(Long userFk) throws Exception {
		return candidateDao.getUserDisability(userFk);
	}

	@Override
	public String getDiscipline(Long userFk) throws Exception {
		return candidateDao.getDiscipline(userFk);
	}

	@Override
	public Map<Integer, String> getDiplomaLawList() throws Exception {
		return candidateDao.getDiplomaLawList();
	}

	@Override
	public Map<String, String> getAgeMatrixDetails(String categoryVal, String isExServiceMen, String governmentEmp) throws Exception {
		return candidateDao.getAgeMatrixDetails(categoryVal, isExServiceMen, governmentEmp);

	}

	@Override
	public String checkMobileExistInMaster(String mobileNo) {
		return candidateDao.checkMobNoOTP(mobileNo);
	}

	public void populateCandidateDetailsForPrintPage(Users users, CandidateBean candidateBean) {
		try {
			if (users.getUserFk() != null && !users.getUserFk().equals("")) {
				CandidateBean newCandidateBean = candidateDao.getCandidateDetailsForFinalPrintPage(users);
				PersonalDetailsBean personalDetailsBean = newCandidateBean.getPersonalDetailsBean();
				AddressBean addressBean = newCandidateBean.getAddressBean();

				if (Strings.isNotBlank(newCandidateBean.getDisciplineTypeDesc())) {
					candidateBean.setDisciplinName(newCandidateBean.getDisciplineTypeDesc());
				}

				if (personalDetailsBean != null) {
					if (StringUtils.isNotBlank(newCandidateBean.getPersonalDetailsBean().getCandidateName())) {
						candidateBean.getPersonalDetailsBean().setCandidateName(newCandidateBean.getPersonalDetailsBean().getCandidateName());
					}
					
					if (StringUtils.isNotBlank(newCandidateBean.getPersonalDetailsBean().getCandidatePrefix())) {
						candidateBean.getPersonalDetailsBean().setCandidatePrefix(newCandidateBean.getPersonalDetailsBean().getCandidatePrefix());
					}
					
					if (StringUtils.isNotBlank(newCandidateBean.getPersonalDetailsBean().getMobileNo())) {
						candidateBean.getPersonalDetailsBean().setMobileNo(newCandidateBean.getPersonalDetailsBean().getMobileNo());
					}

					if (StringUtils.isNotBlank(newCandidateBean.getPersonalDetailsBean().getEmail())) {
						candidateBean.getPersonalDetailsBean().setEmail(newCandidateBean.getPersonalDetailsBean().getEmail());
					}
					
					if (StringUtils.isNotBlank(newCandidateBean.getPersonalDetailsBean().getAge())) {
						candidateBean.getPersonalDetailsBean().setAge(newCandidateBean.getPersonalDetailsBean().getAge());
					}
					
					if (StringUtils.isNotBlank(newCandidateBean.getPersonalDetailsBean().getAppliedDist())) {
						candidateBean.getPersonalDetailsBean().setAppliedDist(ConfigurationConstants.getInstance()
								.getParameterValueForKey(Integer.parseInt(newCandidateBean.getPersonalDetailsBean().getAppliedDist())));
					}
				}
				
				if (StringUtils.isNotBlank(newCandidateBean.getNationality())) {
					candidateBean.setNationality(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(newCandidateBean.getNationality())));
				}
				
				if (StringUtils.isNotBlank(newCandidateBean.getDomicileUp())) {
					candidateBean.setDomicileUp(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(newCandidateBean.getDomicileUp())));
				}
				
				if (StringUtils.isNotBlank(newCandidateBean.getDateOfBirth())) {
					candidateBean.setDateOfBirth(newCandidateBean.getDateOfBirth());
				}
				
				if (StringUtils.isNotBlank(newCandidateBean.getGenderVal())) {
					candidateBean.setGenderVal(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(newCandidateBean.getGenderVal())));
				}
				
				if (StringUtils.isNotBlank(newCandidateBean.getMariatalStatus())) {
					candidateBean.setMariatalStatus(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(newCandidateBean.getMariatalStatus())));
				}
				
				if (StringUtils.isNotBlank(newCandidateBean.getSpouseName())) {
					candidateBean.setSpouseName(newCandidateBean.getSpouseName());
				}
				
				if (Optional.ofNullable(newCandidateBean.getParentAndGuardianVal()).isPresent()) {
				candidateBean.setParentAndGuardianVal(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(newCandidateBean.getParentAndGuardianVal())));
				}
				
				if (StringUtils.isNotBlank(newCandidateBean.getFathersName())) {
					candidateBean.setFathersName(newCandidateBean.getFathersName());
				}
				
				if (StringUtils.isNotBlank(newCandidateBean.getFathersInitial())) {
					candidateBean.setFathersInitial(newCandidateBean.getFathersInitial());
				}
				
				if (StringUtils.isNotBlank(newCandidateBean.getMothersName())) {
					candidateBean.setMothersName(newCandidateBean.getMothersName());
				}
				
				if (StringUtils.isNotBlank(newCandidateBean.getMothersInitial())) {
					candidateBean.setMothersInitial(newCandidateBean.getMothersInitial());
				}
				
				if (StringUtils.isNotBlank(newCandidateBean.getGuardianName())) {
					candidateBean.setGuardianName(newCandidateBean.getGuardianName());
				}
				
				if (StringUtils.isNotBlank(newCandidateBean.getReligionBelief())) {
					candidateBean.setReligionBelief(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(newCandidateBean.getReligionBelief())));
				}
				
				if (StringUtils.isNotBlank(newCandidateBean.getReligionBeliefOthers())) {
					candidateBean.setReligionBeliefOthers(newCandidateBean.getReligionBeliefOthers());
				}
				
				if (StringUtils.isNotBlank(newCandidateBean.getCategorycertificate())) {
					candidateBean.setCategorycertificate(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(newCandidateBean.getCategorycertificate())));
				}
				
				if (StringUtils.isNotBlank(newCandidateBean.getCategoryValDesc())) {
					candidateBean.setCategoryValDesc(ConfigurationConstants.getInstance().getCategoryValueForKey(Integer.parseInt(newCandidateBean.getCategoryValDesc())));
				}
				
				/*if (Strings.isNotBlank(newCandidateBean.getCategoryValDesc())) {
					candidateBean.setCategoryValDesc(ConfigurationConstants.getInstance().getCategoryValueForKey(Integer.parseInt(newCandidateBean.getCategoryValDesc())));
				}*/
				
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
									addressBean.setAddressFiled2(temp[i].trim());
								}
								if (i == 2) {
									addressBean.setAddressFiled3(temp[i]);
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
									addressBean.setAlternateAddressFiled2(temp[i].trim());
								}
								if (i == 2) {
									addressBean.setAlternateAddressFiled3(temp[i]);
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
				if (StringUtils.isNotBlank(newCandidateBean.getStateValDesc())) {
					candidateBean.setStateVal(newCandidateBean.getStateValDesc());
				}
				
				if (StringUtils.isNotBlank(newCandidateBean.getAltStateValDesc())) {
					candidateBean.setAltStateVal(newCandidateBean.getAltStateValDesc());
				}
				
				if (StringUtils.isNotBlank(newCandidateBean.getDistrictVal())) {
					candidateBean.setDistrictVal(newCandidateBean.getDistrictVal());
				}
				
				if (StringUtils.isNotBlank(newCandidateBean.getDistrictValother())) {
					candidateBean.setDistrictValother(newCandidateBean.getDistrictValother());
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
				
				candidateBean.setDiffAbledChkBox(newCandidateBean.isDiffAbledChkBox());
				
				if (StringUtils.isNotBlank(newCandidateBean.getDisableType())) {
					candidateBean.setDisableType(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(newCandidateBean.getDisableType())));
				}
				if (StringUtils.isNotBlank(newCandidateBean.getDisablityPercent())) {
					candidateBean.setDisablityPercent(newCandidateBean.getDisablityPercent());
				}
				if (StringUtils.isNotBlank(newCandidateBean.getIsGovtService())) {
					candidateBean.setIsGovtService(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(newCandidateBean.getIsGovtService())));
				}
				if (StringUtils.isNotBlank(newCandidateBean.getIsGovtService()) && (newCandidateBean.getIsGovtService().equals("6") || newCandidateBean.getIsGovtService().equals("Yes"))) {
					candidateBean.setGovtServChkBox(newCandidateBean.isGovtServChkBox());
				}
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
					candidateBean.setPhotoIDProof1(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(newCandidateBean.getPhotoIDProof1())));
				}
				if (StringUtils.isNotBlank(newCandidateBean.getPhotoIDProof1Val())) {
					candidateBean.setPhotoIDProof1Val(newCandidateBean.getPhotoIDProof1Val());
				}
				if (StringUtils.isNotBlank(newCandidateBean.getMotherTongue())) {
					candidateBean.setMotherTongue(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(newCandidateBean.getMotherTongue())));
				}
				if (StringUtils.isNotBlank(newCandidateBean.getNativity())) {
					candidateBean.setNativity(newCandidateBean.getNativity());
				}
				if (StringUtils.isNotBlank(newCandidateBean.getOtherNativity())) {
					candidateBean.setOtherNativity(newCandidateBean.getOtherNativity());
				}
				
				if (StringUtils.isNotBlank(newCandidateBean.getAltDistrictVal())) {
					candidateBean.setAltDistrictVal(newCandidateBean.getAltDistrictVal());
				}
				if (StringUtils.isNotBlank(newCandidateBean.getAltDistrictValOthers())) {
					candidateBean.setAltDistrictValOthers(newCandidateBean.getAltDistrictValOthers());
				}
				if (StringUtils.isNotBlank(newCandidateBean.getDistrictVal())) {
					candidateBean.setDistrictVal(newCandidateBean.getDistrictVal());
				}
				if (StringUtils.isNotBlank(newCandidateBean.getDistrictValother())) {
					candidateBean.setDistrictValother(newCandidateBean.getDistrictValother());
				}
				
				if (StringUtils.isNotBlank(newCandidateBean.getAddressBean().getCityName())) {
					candidateBean.getAddressBean().setCityName(newCandidateBean.getAddressBean().getCityName());
				}
				
				if (StringUtils.isNotBlank(newCandidateBean.getAddressBean().getAlternateCity())) {
					candidateBean.getAddressBean().setAlternateCity(newCandidateBean.getAddressBean().getAlternateCity());
				}
				
				if (StringUtils.isNotBlank(newCandidateBean.getAge())) {
					candidateBean.setAge(newCandidateBean.getAge());
				}
				
				if(StringUtils.isNotBlank(newCandidateBean.getWidowYesNo()))
				{
					candidateBean.setWidowYesNo(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(newCandidateBean.getWidowYesNo())));
				}
				if(StringUtils.isNotBlank(newCandidateBean.getDesWidowCertNo()))
				{
					candidateBean.setDesWidowCertNo(newCandidateBean.getDesWidowCertNo());
				}
				if(StringUtils.isNotBlank(newCandidateBean.getWidowIssueDate()))
				{
					candidateBean.setWidowIssueDate(newCandidateBean.getWidowIssueDate());
				}
				if(StringUtils.isNotBlank(newCandidateBean.getWidowIssueAuth()))
				{
					candidateBean.setWidowIssueAuth(newCandidateBean.getWidowIssueAuth());
				}
				if(StringUtils.isNotBlank(newCandidateBean.getWidowDistrict()))
				{
					Map<Integer, String> districtList = ConfigurationConstants.getInstance().getDistrictMap(1);
					Map<Integer, String> districtListMap = new TreeMap<Integer, String>(districtList);
					candidateBean.setWidowDistrict(districtListMap.get(Integer.parseInt(newCandidateBean.getWidowDistrict())));
				}
				
				if(StringUtils.isNotBlank(newCandidateBean.getWidowOtherDistrict()))
				{
					candidateBean.setWidowOtherDistrict(newCandidateBean.getWidowOtherDistrict());
				}
				if(StringUtils.isNotBlank(newCandidateBean.getWidowSubDivision()))
				{
					candidateBean.setWidowSubDivision(newCandidateBean.getWidowSubDivision());
				}
				if (StringUtils.isNotBlank(newCandidateBean.getGovtDate())) {
					candidateBean.setGovtDate(newCandidateBean.getGovtDate());
				}
				
				if (StringUtils.isNotBlank(newCandidateBean.getGovtAge())) {
					candidateBean.setGovtAge(newCandidateBean.getGovtAge());
				}
				
				candidateBean.setAddressChkBox(newCandidateBean.isAddressChkBox());
				
				String selectedPost = candidateBean.getDisciplinName();
				String commCertYesNo = candidateBean.getCommCertYesNo();
				String category="";
						
				if(commCertYesNo!=null && commCertYesNo.equals("Yes"))
				{
					category = candidateBean.getCommunity();
				}else
				{
					category = "OC"; //by default OC
				}
				String pwd = candidateBean.getDisableYesNo();
				String fee = "";
				
				if(pwd.equals("Yes"))
				{
					fee = "DISABILITY_AMOUNT";//500
				}else if (category!=null && (category.equals("SC") || category.equals("SCA") || category.equals("ST"))) {
					fee = "SC_ST_AMOUNT"; //500
				}else if (category!=null && (category.equals("BC") || category.equals("BCM") || category.equals("MBC/DNC")|| category.equals("OC"))) {
					fee = "GENERAL_OBC_AMOUNT"; //1000
				}
				candidateBean.setApplicableFee(ConfigurationConstants.getInstance().getPropertyVal(fee));

				candidateBean.setPhotoupdateStatus(newCandidateBean.getPhotoupdateStatus());
				candidateBean.setSignupdateStatus(newCandidateBean.getSignupdateStatus());

				Map<String, String> map = new LinkedHashMap<String, String>();
				map.putAll(ConfigurationConstants.getInstance().getGetLabelMap());

				InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("global.properties");
				Properties properties = new Properties();
				try {
					properties.load(inputStream);
				} catch (IOException e) {
					e.printStackTrace();
				}

				List<CandidateDocBean> candidateDocBeans = getDocumentUploaded(users);
				List<CandidateBean> uploadListDoc = new ArrayList<CandidateBean>();

				if (candidateDocBeans != null && candidateDocBeans.size() > 0) {
					for (Map.Entry<String, String> entry : map.entrySet()) {
						for (CandidateDocBean candidateDocBean : candidateDocBeans) {
							if (candidateDocBean.getOCD_FLAG() != null && candidateDocBean.getOCD_FLAG().equals(entry.getKey())) {
								CandidateBean c1 = new CandidateBean();
								c1.setOcdFlagValue(candidateDocBean.getOCD_FLAG());
								c1.setDocumentUploaded1(true);
								c1.setDocumentFileName1(candidateDocBean.getOCD_DOC_FILE_NAME());
								c1.setCandidateDocPk1(candidateDocBean.getOCD_CAND_DOC_PK());
								c1.setDocVerify1(candidateDocBean.getOcd_doc_verify_status());
								String docLabel1Desc = map.get(candidateDocBean.getOCD_FLAG());
								
								if (docLabel1Desc.equals("Destitute Widow Certificate")) {
									c1.setDocLabel1(docLabel1Desc);
								}else if (docLabel1Desc.equals("Ex-servicemen Certificate")) {
									c1.setDocLabel1(docLabel1Desc);
								}else if (docLabel1Desc.equals("Community Certificate")) {
									c1.setDocLabel1(docLabel1Desc);// + "/" /*+ properties.getProperty("xii") */);
								} else if (docLabel1Desc.equals("SSLC Certificate")) {
									c1.setDocLabel1(docLabel1Desc);// + " / ");
								} else if (docLabel1Desc.equals("HSC Certificate")) {
									c1.setDocLabel1(docLabel1Desc);// + " / ");
								} else if (docLabel1Desc.equals("Diploma Certificate")) {
									c1.setDocLabel1(docLabel1Desc);// + " / ");
								} else if (docLabel1Desc.equals("UG Degree Certificate")) {
									c1.setDocLabel1(docLabel1Desc);// + " / ");
								} else if (docLabel1Desc.equals("PG Degree Certificate")) {
									c1.setDocLabel1(docLabel1Desc);// + " / ");
								} else if (docLabel1Desc.equals("PG Diploma Certificate")) {
									c1.setDocLabel1(docLabel1Desc);// + " / ");
								} else if (docLabel1Desc.equals("PSTM Certificate (1st to 12th Standard & Diploma)")) {
									c1.setDocLabel1(docLabel1Desc);// + " / ");
								}

								if (c1.getDocLabel1() != null) {
									uploadListDoc.add(c1); // added From DB Doc
								}
							}
						}
					}
				}
				candidateBean.setUploadList(uploadListDoc);
				if (newCandidateBean.getStatusID() != null && !newCandidateBean.getStatusID().equals("")) {
					candidateBean.setStatusID(newCandidateBean.getStatusID());
				}
				candidateBean.setRegFormSubmitedDate(newCandidateBean.getRegFormSubmitedDate());
				List<CandidateBean> removeNonMandatoryDoc = new ArrayList<CandidateBean>();
				for (int i = 0; i < candidateBean.getUploadList().size(); i++) {
					if (candidateBean.getUploadList().get(i).getDocumentFileName1() == null || candidateBean.getUploadList().get(i).getDocumentFileName1().equals("")) {
						removeNonMandatoryDoc.add(candidateBean.getUploadList().get(i));
					}
				}
				candidateBean.getUploadList().removeAll(removeNonMandatoryDoc);
			} else {
				candidateBean.setFlag("false");
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}
	
	@Override
	public void serverSidePDFGenRequest(String username) {
		
			URL url = null;
			HttpURLConnection  httpConn	= null;
			StringBuffer requestParams = new StringBuffer();
			InputStream stream = null;
			InputStreamReader isr = null;
			BufferedReader reader = null;
			try {
			String requestUrl = "http://localhost:3000/singleId/"+username;
			url = new URL(requestUrl);
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
			writer.write(requestParams.toString());
			writer.flush();
			stream = httpConn.getInputStream();
			isr = new InputStreamReader(stream);
			reader = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			String line = null ;
			while((line = reader.readLine()) != null) 
			{
				  sb.append(line);
			}
			stream.close();
			if(sb.toString().trim().equalsIgnoreCase("ServerSide_PDF_Generated")){
				logger.info("Server Side Pdf Generated for : " + username);	
			}else if(sb.toString().trim().equalsIgnoreCase("ServerSide_PDF_Existed")){
				logger.info("Server Side Pdf Already Exist for : " + username);
			}else if(sb.toString().trim().equalsIgnoreCase("Json File Not Generated")) {
				logger.info("Json File missing for : " + username);
			}
		}
		catch(ConnectException e) {
			logger.info("Failed to Connect!! Make sure node server is running: " + e.getMessage());			
			}
		catch (Exception e) {
			logger.info("Exception Error: " + e.getMessage());
		} 
		finally {
			try {
				if (reader != null) {
					reader.close();
					reader = null;
				}
				if (isr != null) {
					isr.close();
					isr = null;
				}
				if (stream != null) {
					stream.close();
					stream = null;
				}
			} catch (Exception e) {
				logger.info("Exception sendDoubleVerificationPostCall() Error: " + e.getMessage());
				}
			}
	}
	
	@Override
	public String documentVerificationStatus(long userPk,int parseInt, String documentFileName) throws Exception {
		String data = "";
		try {
			data = candidateDao.documentVerificationStatus(userPk, parseInt, documentFileName);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return data;
	}
	
	@Override
	public AdditionalDetailsBean populateAdditionalDetailsForPrintPage(Users loggedInUser, CandidateBean candidateBean)
			throws Exception {

		AdditionalDetailsBean additionalDetailsBean = null;
		try {
			additionalDetailsBean = candidateAdditionalDetailsDao
					.getCandidateAdditionalDetails(loggedInUser.getUserId());
			if (additionalDetailsBean != null) {
				additionalDetailsBean.setAdvertisement(ConfigurationConstants.getInstance()
						.getParameterValueForKey(Integer.parseInt(additionalDetailsBean.getAdvertisement())));
				additionalDetailsBean.setAppliedInPast(ConfigurationConstants.getInstance()
						.getParameterValueForKey(Integer.parseInt(additionalDetailsBean.getAppliedInPast())));
				if (additionalDetailsBean.getRef1IsAcademician() != null
						&& !"".equals(additionalDetailsBean.getRef1IsAcademician())) {
					additionalDetailsBean.setRef1IsAcademician(ConfigurationConstants.getInstance()
							.getParameterValueForKey(Integer.parseInt(additionalDetailsBean.getRef1IsAcademician())));
				}

				if (additionalDetailsBean.getRef1State() != null && !"".equals(additionalDetailsBean.getRef1State())
						&& additionalDetailsBean.getRef1District() != null
						&& !"".equals(additionalDetailsBean.getRef1District())) {
					additionalDetailsBean.setRef1District(ConfigurationConstants.getInstance()
							.getDistrictMap(Integer.parseInt(additionalDetailsBean.getRef1State()))
							.get(Integer.parseInt(additionalDetailsBean.getRef1District())));
				}
				if (additionalDetailsBean.getRef1State() != null && !"".equals(additionalDetailsBean.getRef1State())) {
					additionalDetailsBean.setRef1State(ConfigurationConstants.getInstance().getStateMap(1)
							.get(Integer.parseInt(additionalDetailsBean.getRef1State())));
				}
				if (additionalDetailsBean.getRef2IsAcademician() != null
						&& !"".equals(additionalDetailsBean.getRef2IsAcademician())) {
					additionalDetailsBean.setRef2IsAcademician(ConfigurationConstants.getInstance()
							.getParameterValueForKey(Integer.parseInt(additionalDetailsBean.getRef2IsAcademician())));
				}

				if (additionalDetailsBean.getRef2State() != null && !"".equals(additionalDetailsBean.getRef2State())
						&& additionalDetailsBean.getRef2District() != null
						&& !"".equals(additionalDetailsBean.getRef2District())) {
					additionalDetailsBean.setRef2District(ConfigurationConstants.getInstance()
							.getDistrictMap(Integer.parseInt(additionalDetailsBean.getRef2State()))
							.get(Integer.parseInt(additionalDetailsBean.getRef2District())));
				}
				if (additionalDetailsBean.getRef2State() != null && !"".equals(additionalDetailsBean.getRef2State())) {
					additionalDetailsBean.setRef2State(ConfigurationConstants.getInstance().getStateMap(1)
							.get(Integer.parseInt(additionalDetailsBean.getRef2State())));
				}
			}
			candidateBean.setAdditionalDetailsBean(additionalDetailsBean);

		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return additionalDetailsBean;
	}

	@Override
	public List<WorkExperienceDetailsBean> populateWorkExpDetailsForPrintPage(Users loggedInUser, CandidateBean candidateBean) throws Exception {
		List<WorkExperienceDetailsBean> wkExperienceList = null;
		try {
		    wkExperienceList = workExperienceDao.getWorkExperienceDetailsForCandidate(loggedInUser.getUserId());
	        if (wkExperienceList != null && !wkExperienceList.isEmpty()) {
	          candidateBean.setWkExperienceDtlsList(wkExperienceList);
	          /*candidateBean.setArea(wkExperienceList.get(0).getArea());
	          candidateBean.setOrganizationOthers(wkExperienceList.get(0).getOrganizationOthers());
	          candidateBean.setJobRole(wkExperienceList.get(0).getJobRole());
	          candidateBean.setFromYear(wkExperienceList.get(0).getFromYear());
	          candidateBean.setYesNoA( wkExperienceList.get(0).getYesNoA());
	          candidateBean.setToYear(wkExperienceList.get(0).getToYear());
	          candidateBean.setWorkduration(wkExperienceList.get(0).getWorkduration());
	          candidateBean.setTotalMonthExp(wkExperienceList.get(0).getMonthExp());
	          candidateBean.setWorkExperienceDisplayFlag(true);*/
	        }
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return wkExperienceList;
	}

	@Override
	public WorkExperienceBean populateAdditionalWorkExpDetailsForPrintPage(Users loggedInUser, CandidateBean candidateBean) throws Exception {
		WorkExperienceBean workExpAddDetailsBean = null;
		try {
			workExpAddDetailsBean = workExperienceDao.getWorkExpAddDetails(loggedInUser);
			if (workExpAddDetailsBean != null) {
				//candidateBean.setYesNoExp(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(workExpAddDetailsBean.getYesNo())));
				//if (workExpAddDetailsBean.getYesNo() != null && !workExpAddDetailsBean.getYesNo().equals("") && workExpAddDetailsBean.getYesNo().equals("6")) {
					//candidateBean.setSponsor(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(workExpAddDetailsBean.getSponsor())));
					candidateBean.setTotalYearExp(workExpAddDetailsBean.getMonthOfExperience() + " months "
							+ workExpAddDetailsBean.getDayOfExperience() + " days");
				//}
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return workExpAddDetailsBean;
	}
	
	// for Covid Duty Certificate page

//		public String getCovidDutyCertificate(Users users) {
//
//			String result = "covidDutyCertificate";
//			return result;
//		}
	
	@Override
	public List<CovidDutyCertDetailsBean> populateCovidDutyCertiForPrintPage(Users loggedInUser, CandidateBean candidateBean) throws Exception {
		List<CovidDutyCertDetailsBean> covidDutyCertiList = null;
		try {
		    covidDutyCertiList = covidDutyCertificateDao.getCovidDutyCertiDetails(loggedInUser.getUserId());
	        if (covidDutyCertiList != null && !covidDutyCertiList.isEmpty()) {
	        	
	        	Map<Integer, String> institutionType = ConfigurationConstants.getInstance().getParameterMapForDomainName("List_of_InstitutionType");
	        	Map<Integer, String> districtList = ConfigurationConstants.getInstance().getDistrictMap(1);
	        	
	        	for(CovidDutyCertDetailsBean bean : covidDutyCertiList) {
					if (Strings.isNotBlank(bean.getInstitutionType()))
						bean.setInstitutionType(institutionType.get(Integer.parseInt(bean.getInstitutionType())));

					if (Strings.isNotBlank(bean.getDistrictVal()))
						bean.setDistrictVal(districtList.get(Integer.parseInt(bean.getDistrictVal())));
	        	}
	        	
	        	candidateBean.setCovidDutyCertDtlsList(covidDutyCertiList);
	        }
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return covidDutyCertiList;
	}
	
	@Override
	public CovidDutyCertificateBean populateAddCovidDutyCertiForPrintPage(Users loggedInUser, CandidateBean candidateBean) throws Exception {
		CovidDutyCertificateBean covidDutyCertiAddDetailsBean = null;
		try {
			covidDutyCertiAddDetailsBean = covidDutyCertificateDao.getCovidDutyCertiAddDetails(loggedInUser);
			if (covidDutyCertiAddDetailsBean != null) {
				//candidateBean.setYesNoExp(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(workExpAddDetailsBean.getYesNo())));
				//if (workExpAddDetailsBean.getYesNo() != null && !workExpAddDetailsBean.getYesNo().equals("") && workExpAddDetailsBean.getYesNo().equals("6")) {
					//candidateBean.setSponsor(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(workExpAddDetailsBean.getSponsor())));
				candidateBean.setYesOrNo(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(covidDutyCertiAddDetailsBean.getYesNo())));	
				candidateBean.setTotalDurationOfService(covidDutyCertiAddDetailsBean.getYearOfTotalService() + " years " +
							covidDutyCertiAddDetailsBean.getMonthOfTotalService() + " months "
							+ covidDutyCertiAddDetailsBean.getDayOfTotalService() + " days");
				//}
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return covidDutyCertiAddDetailsBean;
	}

}