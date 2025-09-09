package com.nseit.generic.dao;

import java.util.List;
import java.util.Map;

import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.CandidateDocBean;
import com.nseit.generic.models.CommonBean;
import com.nseit.generic.models.EducationDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.util.GenericException;

public interface CandidateDao {
	public int insertCandidateImages(CandidateBean candidateBean, Users loggedInUser, boolean imageExists) throws Exception;

	public CandidateBean getCandidateImage(Long userId) throws Exception;

	public int checkDataForUserExistInPaymentDetails(Users users) throws Exception;

	public Users getBasicCandidateInfo(Users user) throws Exception;

	public boolean checkIfImageUploaded(Users users) throws Exception;

	public int updateRegDate(Users users) throws Exception;

	public int insertCandidateSignature(CandidateBean candidateBean, Users loggedInUser, boolean signExists) throws Exception;;

	// new add
	public CandidateBean getCandidateSignature(Long userId) throws Exception;

	// new add
	public boolean checkIfSignatureUploaded(Users users) throws Exception;

	public int insertCandidateDocuments(String fileName, byte[] file, String flagValue, String getCheckCandidateAcceptance1, Users loggedInUser) throws Exception;

	public boolean isUserExists(Users user, CandidateBean candidateBean) throws Exception;

	public String getCandidateDocPk(Users user, CandidateBean candidateBean) throws Exception;

	public int updateCandidateDocuments(String candidateDocPk, String fileName, byte[] file, String checkbox, Users loggedInUser) throws Exception;

	public Map<String, String> getCandidateDataForPaymentReceipt(Users loggedInUser) throws Exception;

	public List<CandidateDocBean> getDocumentUploaded(Users users) throws Exception;

	public CandidateBean getDocument(String docId, Users loogedInUser) throws GenericException;

	public CandidateBean getCandidateDetailsForFinalPrintPage(Users loggedInUser) throws Exception;

	public String getUserName(Long userId) throws Exception;

	public List<CommonBean> getDisciplineList() throws Exception;

	public int[] insertCandidateAcademicDetails(final List<EducationDetailsBean> candiadteList, final Users users, String sslcTamil) throws Exception;

	public int[] updateCandidateAcademicDetails(final List<EducationDetailsBean> candiadteList, final Users users) throws Exception;

	public List<EducationDetailsBean> getCandiateAcademicDetails(CandidateBean candidateBean, Users users) throws Exception;

	public int deleteAcademicDetailsEntryForCandidate(List<EducationDetailsBean> candiadteList, Users users, String sslcTamil) throws Exception;

	public List<EducationDetailsBean> getCandidateDataForAcademicDetails(CandidateBean candidateBean, Users users) throws Exception;

	public List<EducationDetailsBean> getGraduateDetailsForCandidate(Long usrFk) throws Exception;

	public int updateValidateStatus(Users users) throws Exception;

	public String getValidateStatus(Long userId) throws Exception;

	public int updateCandidateStatus(Users loggedInUser, String status) throws Exception;

	public CandidateBean getCandidateDetailsForDashboard(Users loggedInUser) throws Exception;

	public int updateCandidate(Users loggedInUser, CandidateBean candidateBean) throws Exception;

	public int getDocCountInDB(Users loggedInUser, String checkingDocInDB) throws Exception;

	public Map<Integer, String> getExaminationList() throws Exception;

	public Map<Integer, String> getDegreeList() throws Exception;

	public Map<Integer, String> getDiplomaList() throws Exception;

	public Map<Integer, String> getAddExaminationList() throws Exception;

	public Map<Integer, String> getPgList() throws Exception;

	// added for additional academic sravyav
	public EducationDetailsBean getAdditionalEduDetailsForCandidate(Long usrFk) throws Exception;

	public Map<Integer, String> getAddDiplomaList() throws Exception;

	public Map<Integer, String> getAddDegreeList() throws Exception;

	public Map<Integer, String> getAddPgList() throws Exception;

	public int deletePreviousDocumentsforAll(String combination, Users loggedInUser) throws Exception;

	public int deleteDocumentsforAdditionaliDoc(String combination, Users loggedInUser) throws Exception;

	public String getCandidateDob(Users loggedInUser) throws Exception;

	public String getCandidateChallanDownloadedDate(Users loggedInUser) throws Exception;

	public int checkCandidateAlreadyAppliedPostforID(Users loggedInUser) throws Exception;

	public Long getUsersStatusId(String userName) throws Exception;

	public String insertPDFJsonString1(long UserFk, long testfk, String encodedBytes, String UserId) throws Exception;

	public String getPaymentStatusForPdfApplicationNumber(long userFk, long testfk) throws Exception;

	public String getCandidatePhotoName(Long userId) throws Exception;

	public String getCandidateSignatureName(Long userId) throws Exception;

	public int exceededMOBAttempts(String mobileNo1, String courseVal);

	public int checkMobNo(CandidateBean candidateBean) throws Exception;

	public String getCandidateCategory(Users loggedInUser) throws Exception;

	public Map<Integer, String> getOtherNativeList() throws Exception;

	public String saveScoreCardDownloadStatus(String downloadFlag, Users loggedInUser);

	public String saveCallLetterDownloadStatus(String downloadFlag, Users loggedInUser);

	public String savePracticeTestStatus(String downloadFlag, Users loggedInUser);
	
	public String saveAdmitCardDownloadStatus(String downloadFlag, Users loggedInUser);

	public String getUserDisability(Long userFk) throws Exception;

	public String getDiscipline(Long userFk) throws Exception;

	public Map<Integer, String> getDiplomaLawList() throws Exception;

	// Added for age matrix starts
	public Map<String, String> getAgeMatrixDetails(String categoryVal, String isExServiceMen, String governmentEmp) throws Exception;

	// Added for age matrix ends
	public String checkMobNoOTP(String mobileNo);

	public Users getBasicInfoForUploadDoc(Users loggedInUser);

	public String documentVerificationStatus(long userPk, int parseInt, String documentFileName) throws Exception;

	public boolean checkCandidateImageExist(Users loggedInUser);

	public boolean checkCandidateSignExists(Users loggedInUser);
}