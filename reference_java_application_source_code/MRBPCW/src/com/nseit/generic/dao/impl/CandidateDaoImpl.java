package com.nseit.generic.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.postgresql.util.Base64;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.nseit.generic.dao.BaseDAO;
import com.nseit.generic.dao.CandidateDao;
import com.nseit.generic.models.AdditionalDetailsBean;
import com.nseit.generic.models.AddressBean;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.CandidateDocBean;
import com.nseit.generic.models.CandidateDocumentUploadBean;
import com.nseit.generic.models.CommonBean;
import com.nseit.generic.models.EducationDetailsBean;
import com.nseit.generic.models.PersonalDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.queries.CandidateQueries;
import com.nseit.generic.queries.CommonQueries;
import com.nseit.generic.queries.MasterQueries;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.LogicError;
import com.nseit.payment.queries.PaymentOfflineQueries;

public class CandidateDaoImpl extends BaseDAO implements CandidateDao {
	private String ENC_KEY = ConfigurationConstants.getInstance().getENC_KEY();
	private JdbcTemplate writeJdbcTemplate;

	public void setWriteJdbcTemplate(JdbcTemplate writeJdbcTemplate) {
		this.writeJdbcTemplate = writeJdbcTemplate;
	}

	private byte[] byteArrImage = null;
	private Logger logger = LoggerHome.getLogger(getClass());

	// for upload image
	public int insertCandidateImages(CandidateBean candidateBean, final Users loggedInUser, boolean imageExists) throws Exception {
		byteArrImage = null;
		int updateAndInsertCandidate = 0;
		try {
			if (candidateBean.getAttachmentPhoto() != null && candidateBean.getAttachmentPhoto().length() > 0) {
				if (imageExists) {
					updateAndInsertCandidate = writeJdbcTemplate.update(CandidateQueries.INSERT_CANDIDATE_IMAGES,
							new Object[] { loggedInUser.getUserId(), null, loggedInUser.getUsername(), candidateBean.getAttachmentPhotoFileName() });
				} else {
					updateAndInsertCandidate = writeJdbcTemplate.update(CandidateQueries.UPDATE_CANDIDATE_IMAGES,
							new Object[] { null, loggedInUser.getUsername(), candidateBean.getAttachmentPhotoFileName(), loggedInUser.getUserId() });
				}
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return updateAndInsertCandidate;
	}

	@Override
	public boolean checkCandidateImageExist(Users loggedInUser) {
		int recordCount = getJdbcTemplate().queryForObject(CandidateQueries.CHECK_IMAGE_EXISTS_FOR_USERFK, Integer.class, new Object[] { loggedInUser.getUserId() });
		return recordCount > 0;
	}

	// for upload image
	public CandidateBean getCandidateImage(Long userId) throws Exception {
		List<CandidateBean> lstImageAndSignature;
		try {
			lstImageAndSignature = getJdbcTemplate().query(CandidateQueries.GET_CANDIDATE_IMAGES, new RowMapper<CandidateBean>() {
				public CandidateBean mapRow(ResultSet rs, int rowNum) {
					CandidateBean imageDetailBean = new CandidateBean();
					ByteArrayInputStream byteArrayInputStreamForImage = null;
					try {
						if (rs.getBytes("oci_photo_image") != null) {
							String test = Base64.encodeBytes(rs.getBytes("oci_photo_image"));
							imageDetailBean.setImageCast(test);
							byteArrayInputStreamForImage = new ByteArrayInputStream(rs.getBytes("oci_photo_image"), 0, rs.getBytes("oci_photo_image").length);
							imageDetailBean.setInputStreamForImage(byteArrayInputStreamForImage);
						}
						imageDetailBean.setCandidateImagePK(rs.getString("oci_candidate_image_pk"));
					} catch (SQLException e) {
						LoggerHome.getLogger(getClass()).fatal(e, e);
					}
					return imageDetailBean;
				}
			}, new Object[] { 0 == userId ? null : userId });
		} catch (Exception e) {
			throw new GenericException(e);
		}

		if (lstImageAndSignature != null && !lstImageAndSignature.isEmpty()) {
			return lstImageAndSignature.get(0);
		} else {
			return null;
		}

	}

	// for upload images
	public byte[] getImageBytes() {
		return byteArrImage;
	}

	public Users getBasicCandidateInfo(Users user) throws LogicError, Exception {

		Users users = null;
		try {
			users = getJdbcTemplate().query(CandidateQueries.SELECT_BASIC_CANDIDATE_DETAILS_ENC1, (PreparedStatementSetter) ps -> {
				ps.setString(1, ConfigurationConstants.getInstance().getENC_KEY());
				ps.setString(2, ConfigurationConstants.getInstance().getENC_KEY());
				ps.setString(3, ConfigurationConstants.getInstance().getENC_KEY());
				ps.setLong(4, user.getUserId());
			}, new ResultSetExtractor<Users>() {
				@Override
				public Users extractData(ResultSet rs) throws SQLException, DataAccessException {
					Users users = null;
					if (rs.next()) {
						try {
							users = new Users();
							users.setFirstName(rs.getString("oum_full_name"));
							users.setGender(rs.getString("oum_genderfk"));
							users.setCategory(rs.getInt("ocd_category_fk"));
							users.setIsdisabled(rs.getString("ocd_benchmarkdisability"));
							users.setDate_Of_Birth(rs.getString("oum_date_of_birth"));
							users.setDisciplinName(rs.getString("ocd_discipline"));
							
						} catch (Exception ex) {
							logger.info("Error occured for userfk : {} and error message : {} ", user.getUserId(), ex.getMessage());
						}
					}
					return users;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	public boolean checkIfImageUploaded(Users users) throws Exception {
		int imageCount = 0;
		try {
			String DocumentPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
			File documentFolder = new File(DocumentPath + File.separator + users.getUsername() + File.separator + users.getUsername() + "_photo.jpg");
			if (documentFolder != null && !documentFolder.equals("") && documentFolder.exists()) {
				imageCount = 1;
			}
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
		return imageCount > 0;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<CandidateDocBean> getDocumentUploaded(Users users) throws Exception {
		List<CandidateDocBean> CandidateDocBeanList = new ArrayList<CandidateDocBean>();
		try {
			CandidateDocBeanList = writeJdbcTemplate.query(CandidateQueries.GET_CANDIDATE_DOCUMENTS, new BeanPropertyRowMapper(CandidateDocBean.class),
					new Object[] { users.getUserId() });
		} catch (Exception e) {
			throw new GenericException(e);
		}

		return CandidateDocBeanList;
	}

	public int updateRegDate(Users users) throws Exception {
		int updateCandidateEligibility = 0;
		try {
			updateCandidateEligibility = writeJdbcTemplate.update(CandidateQueries.UPDATE_REG_DATE, new Object[] { users.getUserId() });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return updateCandidateEligibility;
	}

	public int insertCandidateSignature(CandidateBean candidateBean, final Users loggedInUser, boolean signExists) throws Exception {
		byteArrImage = null;
		int updateAndInsertCandidate = 0;
		FileInputStream fi = null;
		File photoPath = null;
		try {
			if (candidateBean.getAttachmentSignature() != null && candidateBean.getAttachmentSignature().length() > 0) {
				photoPath = new File(candidateBean.getAttachmentSignature().getPath());
				try {
					fi = new FileInputStream(photoPath);
				} catch (FileNotFoundException e) {
					throw new GenericException(e);
				}
			}

			if (signExists) {
				updateAndInsertCandidate = writeJdbcTemplate.update(CandidateQueries.INSERT_CANDIDATE_SIGNATURE,
						new Object[] { loggedInUser.getUserId(), null, loggedInUser.getUsername(), candidateBean.getAttachmentSignatureFileName() });
			} else {
				updateAndInsertCandidate = writeJdbcTemplate.update(CandidateQueries.UPDATE_CANDIDATE_SIGNATURE,
						new Object[] { null, loggedInUser.getUsername(), candidateBean.getAttachmentSignatureFileName(), loggedInUser.getUserId() });
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return updateAndInsertCandidate;
	}

	public boolean checkCandidateSignExists(Users loggedInUser) {
		int signCount = getJdbcTemplate().queryForObject(CandidateQueries.CHECK_IMAGE_EXISTS_FOR_USERFK, Integer.class, new Object[] { loggedInUser.getUserId() });
		return signCount > 0;
	}

	public CandidateBean getCandidateSignature(Long userId) throws Exception {
		List<CandidateBean> lstImageAndSignature;
		try {
			lstImageAndSignature = getJdbcTemplate().query(CandidateQueries.GET_CANDIDATE_SIGNATURE, new RowMapper<CandidateBean>() {
				public CandidateBean mapRow(ResultSet rs, int rowNum) {
					CandidateBean imageDetailBean = new CandidateBean();
					ByteArrayInputStream byteArrayInputStreamForImage = null;
					try {
						if (rs.getBytes("oci_sign_image") != null) {
							imageDetailBean.setSignatureCast(Base64.encodeBytes(rs.getBytes("oci_sign_image")).toString());
							byteArrayInputStreamForImage = new ByteArrayInputStream(rs.getBytes("oci_sign_image"), 0, rs.getBytes("oci_sign_image").length);
							imageDetailBean.setInputStreamForImage(byteArrayInputStreamForImage);
						}
						imageDetailBean.setCandidateImagePK(rs.getString("oci_candidate_image_pk"));
					} catch (SQLException e) {
						LoggerHome.getLogger(getClass()).fatal(e, e);
					}
					return imageDetailBean;
				}
			}, new Object[] { 0 == userId ? null : userId });
		} catch (Exception e) {
			throw new GenericException(e);
		}

		if (lstImageAndSignature != null && !lstImageAndSignature.isEmpty()) {
			return lstImageAndSignature.get(0);
		} else {
			return null;
		}
	}

	public boolean checkIfSignatureUploaded(Users users) throws Exception {
		int imageCount = 0;
		try {
			String DocumentPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
			File documentFolder = new File(DocumentPath + File.separator + users.getUsername() + File.separator + users.getUsername() + "_sign.jpg");
			if (documentFolder != null && !documentFolder.equals("") && documentFolder.exists()) {
				imageCount = 1;
			}
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
		return imageCount > 0;
	}

	public int insertCandidateDocuments(String fileName, byte[] file, String flagValue, String getCheckCandidateAcceptance1, Users loggedInUser) throws Exception {
		int recordCount = 0;
		try {
			recordCount = writeJdbcTemplate.update(CandidateQueries.INSERT_CANDIDATE_DOCUMENT,
					new Object[] { loggedInUser.getUserId(), fileName, file, flagValue, loggedInUser.getUsername(), getCheckCandidateAcceptance1 });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return recordCount;
	}

	public int[] batchUpdateForCandidateDocuments(final List<CandidateDocumentUploadBean> documentsList, final Users loggedInUser) throws Exception {
		int[] updateCounts = null;
		try {
			updateCounts = writeJdbcTemplate.batchUpdate(CandidateQueries.INSERT_CANDIDATE_DOCUMENT, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement pStatement, int currBatchIndex) throws SQLException {
					pStatement.setLong(1, loggedInUser.getUserFk());
					pStatement.setString(2, documentsList.get(currBatchIndex).getFileName());
					pStatement.setBytes(3, documentsList.get(currBatchIndex).getFile());
					pStatement.setString(4, documentsList.get(currBatchIndex).getFlagValue());
					pStatement.setString(5, loggedInUser.getUsername());
				}

				@Override
				public int getBatchSize() {
					return documentsList.size();
				}
			});
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
		return updateCounts;
	}

	@SuppressWarnings("unchecked")
	public boolean isUserExists(Users user, CandidateBean candidateBean) throws Exception {
		boolean isUserExists = false;
		CandidateDocBean candidateDocBean = null;
		try {
			candidateDocBean = (CandidateDocBean) getJdbcTemplate().queryForObject(CandidateQueries.IS_CANDIDATE_EXIST_FOR_DOC, new BeanPropertyRowMapper(CandidateDocBean.class),
					new Object[] { user.getUserId(), candidateBean.getOcdFlagValue() });
		} catch (Exception e) {
			isUserExists = false;
		}
		if (candidateDocBean != null && candidateDocBean.getOCD_CAND_DOC_PK() != null) {
			isUserExists = true;
		}
		return isUserExists;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getCandidateDocPk(Users user, CandidateBean candidateBean) throws Exception {
		String candidatePk = null;
		CandidateDocBean candidateDocBean = null;
		try {
			candidateDocBean = (CandidateDocBean) getJdbcTemplate().queryForObject(CandidateQueries.IS_CANDIDATE_EXIST_FOR_DOC, new BeanPropertyRowMapper(CandidateDocBean.class),
					new Object[] { user.getUserId(), candidateBean.getOcdFlagValue() });
		} catch (Exception e) {
			candidatePk = null;
		}
		if (candidateDocBean != null && candidateDocBean.getOCD_CAND_DOC_PK() != null) {
			candidatePk = candidateDocBean.getOCD_CAND_DOC_PK();
		}
		return candidatePk;
	}

	public int updateCandidateDocuments(String candidateDocPk, String fileName, byte[] file, String checkbox, Users loggedInUser) throws Exception {
		int recordCount = 0;
		try {
			recordCount = writeJdbcTemplate.update(CandidateQueries.UPDATE_CANDIDATE_DOCUMENT,
					new Object[] { fileName, file, loggedInUser.getUsername(), checkbox, Integer.parseInt(candidateDocPk) });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return recordCount;
	}

	public Map<String, String> getCandidateDataForPaymentReceipt(Users loggedInUser) {
		Map<String, Object> paymentRelatedData = new HashMap<String, Object>();
		try {
			paymentRelatedData = getJdbcTemplate().queryForMap(CandidateQueries.GET_PAYMENT_DTLS_BASED_ON_USER_ID, new Object[] { loggedInUser.getUserId() });
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, String> paymentRelatedDataNew = new HashMap<String, String>();
		for (String key : paymentRelatedData.keySet()) {
			paymentRelatedDataNew.put(key, paymentRelatedData.get(key).toString());
		}
		return paymentRelatedDataNew;
	}

	public CandidateBean getDocument(String docId, Users loogedInUser) throws GenericException {
		List<CandidateBean> documentList = null;
		Long userId = 0L;
		if (loogedInUser != null) {
			userId = loogedInUser.getUserId();
		}
		try {
			documentList = getJdbcTemplate().query(CandidateQueries.GET_CANDIDATE_DOCUMENT, new RowMapper<CandidateBean>() {
				@Override
				public CandidateBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					CandidateBean candidateBean = new CandidateBean();
					candidateBean.setDocumentFileName(rs.getString("ocd_doc_file_name"));
					candidateBean.setDocumentByte(rs.getBytes("ocd_document"));
					candidateBean.setOcdFlagValue(rs.getString("ocd_flag"));
					return candidateBean;
				}
			}, new Object[] { Integer.parseInt(docId) });
		} catch (DataAccessException e) {
			throw new GenericException(e);
		}

		if (documentList != null && !documentList.isEmpty()) {
			return documentList.get(0);
		} else {
			return null;
		}
	}

	public int checkDataForUserExistInPaymentDetails(Users loggedInUser) throws Exception {
		int checkDataForUserExistInPaymentDetails = 0;
		try {
			checkDataForUserExistInPaymentDetails = getJdbcTemplate().queryForObject(CandidateQueries.checkDataForUserExistInPaymentDetails, Integer.class,
					new Object[] { loggedInUser.getUserId() });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checkDataForUserExistInPaymentDetails;
	}

	public CandidateBean getCandidateDetailsForFinalPrintPage(final Users loggedInUser) throws Exception {
		List<CandidateBean> getCandidateDetailsList = null;
		Long userId = 0L;
		if (loggedInUser != null) {
			userId = loggedInUser.getUserId();
		}
		try {
			getCandidateDetailsList = getJdbcTemplate().query(CandidateQueries.SELECT_CANDIDATE_DETAILS_FOR_FINAL_PRINT_PAGE_ENC1, new RowMapper<CandidateBean>() {
				public CandidateBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					CandidateBean candidateBean = new CandidateBean();
					PersonalDetailsBean personalBean = new PersonalDetailsBean();
					AddressBean address = new AddressBean();
					try {
						candidateBean.setPhotoupdateStatus(rs.getString("oum_photo_sign_status"));
						candidateBean.setSignupdateStatus(rs.getString("oum_sign_status"));
						candidateBean.setDisciplineType(rs.getString("otm_test_pk"));
						candidateBean.setDisciplineTypeDesc(rs.getString("otm_test_name"));
						candidateBean.setStatusID(rs.getInt("ocd_status_id_fk"));
						candidateBean.setDisciplinName(rs.getString("ocd_discipline"));

						personalBean.setCandidateName(rs.getString("oum_full_name"));
						personalBean.setCandidatePrefix(rs.getString("oum_initial"));
						candidateBean.setNationality(rs.getString("oum_nationality"));
						personalBean.setEmail(rs.getString("oum_email_id"));
						personalBean.setMobileNo(rs.getString("oum_mobile_no"));
						personalBean.setAppliedDist(rs.getString("oum_nativity_cert_dist"));

						candidateBean.setDomicileUp(rs.getString("ocd_domicile"));
						candidateBean.setDateOfBirth(rs.getString("oum_date_of_birth"));
						personalBean.setAge(rs.getString("oum_age"));
						candidateBean.setGenderVal(rs.getString("ocd_genderfk"));
						candidateBean.setMariatalStatus(rs.getString("ocd_marital_status"));
						candidateBean.setSpouseName(rs.getString("ocd_spouse_name"));
						candidateBean.setParentAndGuardianVal(rs.getString("ocd_relative_fk"));
						candidateBean.setFathersName(rs.getString("ocd_cand_father_first_name"));
						candidateBean.setFathersInitial(rs.getString("ocd_father_initial"));
						candidateBean.setMothersName(rs.getString("ocd_cand_mother_first_name"));
						candidateBean.setMothersInitial(rs.getString("ocd_mother_initial"));
						candidateBean.setGuardianName(rs.getString("ocd_guardian_name"));
						candidateBean.setReligionBelief(rs.getString("ocd_religion"));
						candidateBean.setReligionBeliefOthers(rs.getString("ocd_other_religion"));
						candidateBean.setCategorycertificate(rs.getString("ocd_cat_cert"));
						candidateBean.setCategoryValDesc(rs.getString("ocd_category_fk"));

						address.setAddress(rs.getString("ocd_perm_address"));
						candidateBean.setStateVal(rs.getString("ocd_perm_state_id"));
						candidateBean.setStateValDesc(rs.getString("ocd_perm_state_fk"));
						candidateBean.setDistrictVal(rs.getString("ocd_perm_district_fk"));
						candidateBean.setDistrictValother(rs.getString("ocd_perm_district_textbox"));
						address.setCityName(rs.getString("ocd_perm_city"));
						address.setPinCode(rs.getString("ocd_perm_pin_code"));

						candidateBean.setAddressChkBox(CommonUtil.getParameter(rs.getString("ocd_comm_same_as_perm")).compareTo("Y") == 0 ? true : false);

						address.setAlternateAddress(rs.getString("ocd_comm_address"));
						candidateBean.setAltStateVal(rs.getString("ocd_comm_state_id"));
						candidateBean.setAltStateValDesc(rs.getString("ocd_comm_state_fk"));
						candidateBean.setAltDistrictVal(rs.getString("ocd_comm_district_fk"));
						candidateBean.setAltDistrictValOthers(rs.getString("ocd_comm_district_textbox"));
						address.setAlternateCity(rs.getString("ocd_comm_city"));
						address.setAlternatePinCode(rs.getString("ocd_comm_pin_code"));

						candidateBean.setExServiceMen(rs.getString("oum_exserviceman"));
						candidateBean.setDischargeDate(rs.getString("oum_discharge_date"));
						candidateBean.setPpoNumber(rs.getString("oum_ppo_number"));
						candidateBean.setCommCertYesNo(rs.getString("oum_comm_cert_yesno"));
						candidateBean.setCommunity(rs.getString("oum_community"));
						candidateBean.setSubCaste(rs.getString("oum_subcaste"));
						candidateBean.setIssueAuthCommCert(rs.getString("oum_issue_auth_comm_cert"));
						candidateBean.setCommCertNo(rs.getString("oum_comm_cert_number"));
						candidateBean.setCommCertPlace(rs.getString("oum_comm_cert_issue_place"));
						candidateBean.setCommIssueDate(rs.getString("oum_comm_cert_issue_date"));
						candidateBean.setDisableYesNo(rs.getString("oum_disability_yesno"));
						if(rs.getInt("oum_diiferently_abled_checkbox")==6) 
							candidateBean.setDiffAbledChkBox(true);
						else
							candidateBean.setDiffAbledChkBox(false);
						candidateBean.setDisableType(rs.getString("oum_disability_type"));
						candidateBean.setDisablityPercent(rs.getString("oum_disability_percent"));
						candidateBean.setIsGovtService(rs.getString("ocd_govt_servant"));
						if(rs.getInt("ocd_certificate_declaration")==6) 
							candidateBean.setGovtServChkBox(true);
						else
							candidateBean.setGovtServChkBox(false);
						candidateBean.setOrgName(rs.getString("ocd_org_name"));
						candidateBean.setCurrentDesig(rs.getString("ocd_curr_designation"));
						candidateBean.setPlaceOfWork(rs.getString("ocd_plc_of_wrk"));
						candidateBean.setNativity(rs.getString("ocd_nativity"));
						candidateBean.setOtherNativity(rs.getString("ocd_other_nativity"));
						candidateBean.setPhotoIDProof1(rs.getString("ocd_photoidproof1"));
						candidateBean.setPhotoIDProof1Val(rs.getString("ocd_photoidproof1val"));
						
						candidateBean.setMotherTongue(rs.getString("ocd_mother_tongue"));
						candidateBean.setAge(rs.getString("oum_age"));
						
						candidateBean.setWidowYesNo(rs.getString("oum_widowYesNo"));
						candidateBean.setDesWidowCertNo(rs.getString("oum_widowCertNo"));
						candidateBean.setWidowIssueDate(rs.getString("oum_widowIssueDate"));
						candidateBean.setWidowIssueAuth(rs.getString("oum_widowIssueAuth"));
						candidateBean.setWidowDistrict(rs.getString("oum_widowIssueDist"));
						candidateBean.setWidowOtherDistrict(rs.getString("oum_widowIssueDistOther"));
						candidateBean.setWidowSubDivision(rs.getString("oum_widowSubDivision"));
						candidateBean.setGovtDate(rs.getString("ocd_govt_date"));
						candidateBean.setGovtAge(rs.getString("ocd_govt_age"));
						
						candidateBean.setAddressBean(address);
						candidateBean.setPersonalDetailsBean(personalBean);
						if (rs.getTimestamp("ocd_form_submited_date") != null) {
							candidateBean.setRegFormSubmitedDate(CommonUtil.formatTimeStamp(rs.getTimestamp("ocd_form_submited_date")));
						}
					} catch (Exception e) {
						logger.fatal(e, e);
					}
					return candidateBean;
				}
			}, new Object[] { ENC_KEY, ENC_KEY, ENC_KEY, ENC_KEY, ENC_KEY, ENC_KEY, ENC_KEY, ENC_KEY, userId });
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		if (getCandidateDetailsList != null && !getCandidateDetailsList.isEmpty()) {
			return getCandidateDetailsList.get(0);
		} else {
			return null;
		}
	}

	public CandidateBean getCandidateDetailsForDashboard(Users loggedInUser) throws Exception {
		List<CandidateBean> getCandidateDetailsList = null;
		Long userId = 0L;
		if (loggedInUser != null) {
			userId = loggedInUser.getUserId();
		}
		try {
			getCandidateDetailsList = getJdbcTemplate().query(CandidateQueries.SELECT_CANDIDATE_DETAILS_FOR_DASHBORAD_ENC1, new RowMapper<CandidateBean>() {
				public CandidateBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					CandidateBean candidateBean = new CandidateBean();
					PersonalDetailsBean personalBean = new PersonalDetailsBean();
					try {
						// personalBean.setCandidatePrefix(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(encryptWithAesGcm.decrypt(rs.getString("OUM_INITIAL")))));
						if (StringUtils.isNotBlank(rs.getString("oum_full_name"))) {
							personalBean.setCandidateName(rs.getString("oum_full_name"));
						}
						personalBean.setDateOfBirth(rs.getString("oum_date_of_birth"));
						candidateBean.setGenderValDesc(rs.getString("oum_genderfk"));
						personalBean.setEmail(rs.getString("oum_email_id"));
						personalBean.setMobileNo(rs.getString("oum_mobile_no"));
						candidateBean.setStatusID(rs.getInt("ocd_status_id_fk"));
						candidateBean.setDisciplineType(rs.getString("ocd_discipline"));
					} catch (Exception e) {
						logger.fatal(e, e);
					}
					candidateBean.setPersonalDetailsBean(personalBean);
					return candidateBean;
				}
			}, new Object[] { ENC_KEY, ENC_KEY, ENC_KEY, ENC_KEY, userId });
		} catch (Exception e) {
			throw new GenericException(e);
		}

		if (getCandidateDetailsList != null && !getCandidateDetailsList.isEmpty()) {
			return getCandidateDetailsList.get(0);
		} else {
			return null;
		}
	}

	public String getUserName(Long userId) throws Exception {
		String userName = "";
		try {
			userName = (String) getJdbcTemplate().queryForObject(CandidateQueries.GET_USER_NAME, String.class, new Object[] { userId });
		} catch (Exception e) {
			userName = "";
		}
		return userName;
	}

	public List<CommonBean> getDisciplineList() throws Exception {
		List<CommonBean> testDetailsList = null;
		try {
			testDetailsList = getJdbcTemplate().query(PaymentOfflineQueries.GET_TEST_DETAILS, new RowMapper<CommonBean>() {
				public CommonBean mapRow(ResultSet rs, int rowNum) {
					CommonBean testDetailsBean = new CommonBean();
					try {
						testDetailsBean.setLabelId(rs.getString("otm_test_pk"));
						testDetailsBean.setLabelValue(rs.getString("otm_test_name"));
					} catch (SQLException e) {
						LoggerHome.getLogger(getClass()).fatal(e);
					}
					return testDetailsBean;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return testDetailsList;
	}

	public int[] insertCandidateAcademicDetails(final List<EducationDetailsBean> candiadteList, final Users users, final String sslcTamil) throws Exception {
		int[] updateCounts = null;
		try {
			updateCounts = writeJdbcTemplate.batchUpdate(CandidateQueries.INSERT_CANDIDATE_ACADEMIC_DETAILS, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement pStatement, int currBatchIndex) throws SQLException {
					if (candiadteList.get(currBatchIndex).isEnabled()) {
						pStatement.setInt(1, Integer.parseInt(candiadteList.get(currBatchIndex).getDegreeSelected()));
						pStatement.setLong(2, users.getUserFk());
						pStatement.setInt(3, Integer.parseInt(candiadteList.get(currBatchIndex).getExamination()));
						pStatement.setString(4, candiadteList.get(currBatchIndex).getRegistrationNo());
						if (candiadteList.get(currBatchIndex).getDegreeSubject() != null && !candiadteList.get(currBatchIndex).getDegreeSubject().equals(""))
							pStatement.setInt(5, Integer.parseInt(candiadteList.get(currBatchIndex).getDegreeSubject()));
						else
							pStatement.setInt(5, 0);
						if (candiadteList.get(currBatchIndex).getMajorSubject() != null && !candiadteList.get(currBatchIndex).getMajorSubject().equals(""))
							pStatement.setString(6, candiadteList.get(currBatchIndex).getMajorSubject());
						else
							pStatement.setString(6, candiadteList.get(currBatchIndex).getMajorSubject1());
						pStatement.setString(7, "");
						pStatement.setFloat(8, 0);
						pStatement.setString(9, candiadteList.get(currBatchIndex).getDateOfPassing());
						pStatement.setInt(10, 0);
						pStatement.setString(11, candiadteList.get(currBatchIndex).getIssueAuthority());
						pStatement.setString(12, candiadteList.get(currBatchIndex).getDateOfIssue());
						pStatement.setString(13, candiadteList.get(currBatchIndex).getPartTimeFullTimeSelected());
						pStatement.setString(14, users.getUsername());
						pStatement.setString(15, sslcTamil);// specialization
						pStatement.setString(16, candiadteList.get(currBatchIndex).getUniversity());
						pStatement.setString(17, String.valueOf(candiadteList.get(currBatchIndex).getResultChkBox()));
						pStatement.setInt(18, Integer.parseInt(candiadteList.get(currBatchIndex).getExamName()));
					}
				}

				@Override
				public int getBatchSize() {
					return candiadteList.size();
				}
			});
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
		return updateCounts;
	}

	public int[] updateCandidateAcademicDetails(final List<EducationDetailsBean> candiadteList, final Users users) throws Exception {
		int[] updateCounts = null;
		try {
			updateCounts = writeJdbcTemplate.batchUpdate(CandidateQueries.UPDATE_CANDIDATE_ACADEMIC_DETAILS, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement pStatement, int currBatchIndex) throws SQLException {
					pStatement.setString(1, candiadteList.get(currBatchIndex).getSpecialization());
					pStatement.setString(2, candiadteList.get(currBatchIndex).getYearOfPassing());
					pStatement.setString(3, candiadteList.get(currBatchIndex).getUniversity());
					pStatement.setString(4, candiadteList.get(currBatchIndex).getObtndMarks());
					pStatement.setString(5, candiadteList.get(currBatchIndex).getOutOfMarks());
					pStatement.setString(6, candiadteList.get(currBatchIndex).getPercentage());
					pStatement.setString(7, users.getUsername());
					pStatement.setString(8, candiadteList.get(currBatchIndex).getPartTimeFullTimeSelected());
					pStatement.setString(9, candiadteList.get(currBatchIndex).getMonthOfPassing());
					pStatement.setLong(10, users.getUserFk());
				}

				@Override
				public int getBatchSize() {
					return candiadteList.size();
				}
			});

		} catch (Exception ex) {
			throw new GenericException(ex);
		}
		return updateCounts;
	}

	public List<EducationDetailsBean> getCandiateAcademicDetails(CandidateBean candidateBean, Users users) throws Exception {
		List<EducationDetailsBean> educationalDetailsList = null;
		try {
			educationalDetailsList = getJdbcTemplate().query(CandidateQueries.GET_ACADEMIC_DETAILS_FOR_CANDIATE, new RowMapper<EducationDetailsBean>() {
				public EducationDetailsBean mapRow(ResultSet rs, int rowNum) {
					EducationDetailsBean educationDetailsBean = new EducationDetailsBean();
					try {
						educationDetailsBean.setExamination(rs.getString("oacd_acdm_fk"));
						educationDetailsBean.setEducationPk(rs.getString("oacd_acdm_fk"));
						if (rs.getString("oacd_acdm_fk").equals("3")) {
							educationDetailsBean.setResultStatus(rs.getString("oacd_result_status"));
							if (rs.getString("oacd_result_status").equals("Passed") || rs.getString("oacd_result_status").equals("61")) {
								educationDetailsBean.setYearOfPassing(rs.getString("oacd_year_of_passing"));
							}
							/*
							 * if (rs.getString("oacd_result_status").equals("Appearing") || rs.getString("oacd_result_status").equals("62")) {
							 * educationDetailsBean.setYearOfPassing(rs.getString("oacd_date_of_award")); }
							 */
						}
						if (rs.getString("oacd_acdm_fk").equals("2")) {
							educationDetailsBean.setYearOfPassing(rs.getString("oacd_year_of_passing"));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					return educationDetailsBean;
				}
			}, new Object[] { users.getUserFk() });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return educationalDetailsList;
	}

	public int deleteAcademicDetailsEntryForCandidate(List<EducationDetailsBean> candiadteList, Users users, String sslcTamil) throws Exception {
		int delete = 0;
		int save[] = null;
		int returnVal = 0;
		try {
			delete = writeJdbcTemplate.update(CandidateQueries.DELETE_ACADEMIC_DETAILS_ENTRY, new Object[] { users.getUserId() });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		if (delete > 0) {
			save = insertCandidateAcademicDetails(candiadteList, users, sslcTamil);
			if (save != null && save.length > 0) {
				returnVal = 1;
			} else {
				returnVal = 0;
			}
		}
		return returnVal;
	}

	public List<EducationDetailsBean> getCandidateDataForAcademicDetails(CandidateBean candidateBean, Users users) throws Exception {
		List<EducationDetailsBean> educationDetailsBeanList = null;
		try {
			educationDetailsBeanList = getJdbcTemplate().query(CandidateQueries.GET_CANDIATE_ACADEMIC_DETAILS_USER_ID, new RowMapper<EducationDetailsBean>() {
				public EducationDetailsBean mapRow(ResultSet rs, int rowNum) {
					EducationDetailsBean educationDetailsBean = new EducationDetailsBean();
					try {
						educationDetailsBean.setExamination(rs.getString("OACD_ACDM_FK"));
						educationDetailsBean.setExamination(ConfigurationConstants.getInstance().getAcademicDetailsForPk(educationDetailsBean.getExamination()));
						educationDetailsBean.setDegree(rs.getString("OACD_DEGREE_FK"));
						educationDetailsBean.setEducationPk(rs.getString("OACD_ACDM_FK"));
						educationDetailsBean.setDegreeSubject(rs.getString("OACD_DEGREE_SUBJECT_FK"));
						educationDetailsBean.setOtherDegreeSubject(rs.getString("OACD_OTHER_DEGREE"));
						educationDetailsBean.setNameOfUniv(rs.getString("OACD_UNIVERSITY"));
						educationDetailsBean.setUniversityOth(rs.getString("OACD_UNIVERSITY_OTHER"));
						educationDetailsBean.setDateOfPassing(rs.getString("OACD_YEAR_OF_PASSING"));
						educationDetailsBean.setResultStatus(rs.getString("OACD_RESULT_STATUS"));
						educationDetailsBean.setRegistrationNo(rs.getString("OACD_ROLL_NO"));
						educationDetailsBean.setTypingHindi(rs.getString("OACD_HINDI_TYPING"));
						educationDetailsBean.setTypingEng(rs.getString("OACD_ENG_TYPING"));
						educationDetailsBean.setShorthand(rs.getString("OACD_SHORTHAND"));
						educationDetailsBean.setCertiO(rs.getString("OACD_O_CERTI"));
						educationDetailsBean.setTerriArmy(rs.getString("OACD_TERRI_ARMY"));
						educationDetailsBean.setCertiB(rs.getString("OACD_B_CERTI"));
						educationDetailsBean.setDoeacc(rs.getString("OACD_DOEACC"));
						educationDetailsBean.setUgcDegree(rs.getString("OACD_UGC_DEGREE"));
					} catch (Exception e) {
						e.printStackTrace();
					}
					return educationDetailsBean;
				}
			}, new Object[] { users.getUserFk() });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return educationDetailsBeanList;
	}

	public List<EducationDetailsBean> getGraduateDetailsForCandidate(Long usrFk) throws Exception {
		List<EducationDetailsBean> educationDetailsList = null;
		try {
			educationDetailsList = getJdbcTemplate().query(CandidateQueries.GET_EDU_DETAILS_FOR_FINAL_PRINT_PAGE, new RowMapper<EducationDetailsBean>() {
				public EducationDetailsBean mapRow(ResultSet rs, int rowNum) {
					EducationDetailsBean educationDetailsBean = new EducationDetailsBean();
					try {

						educationDetailsBean.setExamination(ConfigurationConstants.getInstance().getAcademicDetailsForPk(rs.getString("oacd_acdm_fk")));
						educationDetailsBean.setEducationPk(rs.getString("oacd_acdm_fk"));

						educationDetailsBean.setUniversity(rs.getString("oacd_university"));
						educationDetailsBean.setUniversityOth(rs.getString("oacd_university_other"));
						educationDetailsBean.setPrdOfStudyFrm(rs.getString("oacd_prd_of_study_from"));
						educationDetailsBean.setPrdOfStudyTo(rs.getString("oacd_prd_of_study_to"));
						educationDetailsBean.setDuration(rs.getString("oacd_duration_of_study"));
						educationDetailsBean.setInstitution(rs.getString("oacd_institution"));
						educationDetailsBean.setDateOfPassing(rs.getString("oacd_year_of_passing"));
						educationDetailsBean.setDipMarksYesNo(rs.getString("oacd_dipmarks_yesno"));
						educationDetailsBean.setTotalMarks(rs.getString("oacd_total_marks_agg"));
						educationDetailsBean.setObtainedMarks(rs.getString("oacd_obtained_marks_agg"));
						educationDetailsBean.setPercentage(rs.getString("oacd_percentage"));
						educationDetailsBean.setMedOfInstruction(rs.getString("oacd_med_of_inst"));
						educationDetailsBean.setTamilLang(rs.getString("oacd_tamil_lang"));
						educationDetailsBean.setUgYesNo(rs.getString("oacd_ugdeg_yesno"));
						educationDetailsBean.setPgYesNo(rs.getString("oacd_pgdeg_yesno"));
						educationDetailsBean.setSpecialization(rs.getString("oacd_specialization"));
						educationDetailsBean.setPgDipYesNo(rs.getString("oacd_yesno_pgdip"));
						educationDetailsBean.setPgDipSpecialization(rs.getString("oacd_specialization_pgdip"));
						educationDetailsBean.setPgDipDateofpassing(rs.getString("oacd_dateofpass_pgdip"));
						educationDetailsBean.setPstmPreference(rs.getString("oacd_pstm_pref"));
						educationDetailsBean.setTamilMedium(rs.getString("oacd_tamil_medium"));
						educationDetailsBean.setUgTamilMedium(rs.getString("oacd_tamil_medium_ug"));

					} catch (SQLException e) {
						LoggerHome.getLogger(getClass()).fatal(e);
					}
					return educationDetailsBean;
				}
			}, new Object[] { usrFk });
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return educationDetailsList;
	}

	public EducationDetailsBean getAdditionalEduDetailsForCandidate(Long usrFk) throws Exception {
		List<EducationDetailsBean> educationDetailsList = null;
		try {
			educationDetailsList = getJdbcTemplate().query(CandidateQueries.GET_ADD_GRADUATE_DETAILS_FOR_FINAL_PRINT_PAGE_ENC, new RowMapper<EducationDetailsBean>() {
				public EducationDetailsBean mapRow(ResultSet rs, int rowNum) {
					EducationDetailsBean educationDetailsBean = new EducationDetailsBean();
					try {
						educationDetailsBean.setDoeacc(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(rs.getString("oaed_doeacc"))));
						educationDetailsBean.setTerriArmy(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(rs.getString("oaed_terri_army"))));
						educationDetailsBean.setCertiB(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(rs.getString("oaed_bcerti"))));
					} catch (SQLException e) {
						logger.info(e.getMessage());
					}
					return educationDetailsBean;
				}
			}, new Object[] { usrFk });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		if (educationDetailsList != null && !educationDetailsList.isEmpty()) {
			return educationDetailsList.get(0);
		} else {
			return null;
		}
	}

	public int updateValidateStatus(Users users) throws Exception {
		int updateCandidateEligibility = writeJdbcTemplate.update(CandidateQueries.UPDATE_VALIDATE_STATUS, new Object[] { users.getUserId() });
		return updateCandidateEligibility;
	}

	public String getValidateStatus(Long userId) throws Exception {
		String validateStatus = "";
		try {
			validateStatus = (String) getJdbcTemplate().queryForObject(CandidateQueries.GET_VALIDATE_STATUS_ENC1, String.class, new Object[] { userId });
		} catch (Exception e) {
			validateStatus = "";
		}
		return validateStatus;
	}

	@Override
	public int updateCandidateStatus(Users loggedInUser, String status) throws Exception {
		int updateCandidateStatus = 0;
		try {
			updateCandidateStatus = writeJdbcTemplate.update(CandidateQueries.UPDATE_CANDIDATE_STATUS, new Object[] { Integer.parseInt(status), loggedInUser.getUserId() });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return updateCandidateStatus;
	}

	@Override
	public int updateCandidate(Users loggedInUser, CandidateBean candidateBean) throws Exception {
		int updateCandidate = 0;
		try {
			updateCandidate = writeJdbcTemplate.update(CandidateQueries.UPDATE_CANDIDATE,
					new Object[] { candidateBean.getCandidateBirthPlace(), candidateBean.getPersonalDetailsBean().getFatherFirstName(),
							candidateBean.getPersonalDetailsBean().getFatherMiddleName(), candidateBean.getPersonalDetailsBean().getFatherLastName(),
							candidateBean.getFatherBirthPlace(), candidateBean.getPersonalDetailsBean().getMotherFirstName(),
							candidateBean.getPersonalDetailsBean().getMotherMiddleName(), candidateBean.getPersonalDetailsBean().getMotherLastName(),
							candidateBean.getMotherTongue(), Integer.parseInt(candidateBean.getNationality()), Integer.parseInt(candidateBean.getGenderVal()),
							candidateBean.getMariatalStatus(), candidateBean.getReligionBelief(), candidateBean.getPersonalDetailsBean().getMobileNo(),
							candidateBean.getPersonalDetailsBean().getPhoneNo(), candidateBean.getPersonalDetailsBean().getEmail(), candidateBean.getExServiceman(),
							candidateBean.getDepartMentalCandidate(), Integer.parseInt(candidateBean.getCategoryVal()), loggedInUser.getUserId(), loggedInUser.getUserId() });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return updateCandidate;
	}

	@Override
	public int getDocCountInDB(Users loggedInUser, String checkingDocInDB) {
		int docCount = 0;
		try {
			String query = "select count(1) from OES_CANDIDATE_DOC where OCD_USER_FK = ? ";
			docCount = getJdbcTemplate().queryForObject(query, Integer.class, new Object[] { loggedInUser.getUserFk() });
		} catch (Exception ex) {
			docCount = 0;
		}
		return docCount;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<Integer, String> getExaminationList() throws Exception {
		return (Map<Integer, String>) getJdbcTemplate().query(CandidateQueries.SELECT_EXAMINATION_LIST, new ResultSetExtractor() {
			@Override
			public Map<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer, String> examList = new LinkedHashMap<Integer, String>();
				InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("global.properties");
				Properties properties = new Properties();
				try {
					properties.load(inputStream);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while (rs.next()) {
					examList.put(rs.getInt("oatm_acdm_pk"), rs.getString("oatm_acdm_code") + properties.getProperty(String.valueOf(rs.getInt("oatm_acdm_pk"))));
				}
				return examList;
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<Integer, String> getDegreeList() throws Exception {
		return (Map<Integer, String>) getJdbcTemplate().query(CandidateQueries.SELECT_DEGREE_LIST, new ResultSetExtractor() {
			@Override
			public Map<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer, String> degreeList = new LinkedHashMap<Integer, String>();
				while (rs.next()) {
					degreeList.put(rs.getInt("osdm_sub_degree_pk"), rs.getString("osdm_sub_degree_desc"));
				}
				return degreeList;
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<Integer, String> getDiplomaList() throws Exception {
		return (Map<Integer, String>) getJdbcTemplate().query(CandidateQueries.SELECT_DIPLOMA_LIST, new ResultSetExtractor() {
			@Override
			public Map<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer, String> degreeList = new LinkedHashMap<Integer, String>();
				while (rs.next()) {
					degreeList.put(rs.getInt("osdm_sub_degree_pk"), rs.getString("osdm_sub_degree_desc"));
				}
				return degreeList;
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<Integer, String> getAddExaminationList() throws Exception {
		return (Map<Integer, String>) getJdbcTemplate().query(CandidateQueries.SELECT_ADD_EXAMINATION_LIST, new ResultSetExtractor() {
			@Override
			public Map<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer, String> addexamList = new LinkedHashMap<Integer, String>();
				while (rs.next()) {
					addexamList.put(rs.getInt("oatm_acdm_pk"), rs.getString("oatm_acdm_desc"));
				}
				return addexamList;
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<Integer, String> getPgList() throws Exception {
		return (Map<Integer, String>) getJdbcTemplate().query(CandidateQueries.SELECT_PG_LIST, new ResultSetExtractor() {
			@Override
			public Map<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer, String> degreeList = new LinkedHashMap<Integer, String>();
				while (rs.next()) {
					degreeList.put(rs.getInt("osdm_sub_degree_pk"), rs.getString("osdm_sub_degree_desc"));
				}
				return degreeList;
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<Integer, String> getAddDegreeList() throws Exception {
		return (Map<Integer, String>) getJdbcTemplate().query(CandidateQueries.SELECT_ADD_DEGREE_LIST, new ResultSetExtractor() {
			@Override
			public Map<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer, String> adddegreeList = new LinkedHashMap<Integer, String>();
				while (rs.next()) {
					adddegreeList.put(rs.getInt("osdm_sub_degree_pk"), rs.getString("osdm_sub_degree_desc"));
				}
				return adddegreeList;
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<Integer, String> getAddDiplomaList() throws Exception {
		return (Map<Integer, String>) getJdbcTemplate().query(CandidateQueries.SELECT_ADD_DIPLOMA_LIST, new ResultSetExtractor() {
			@Override
			public Map<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer, String> adddiplomaList = new LinkedHashMap<Integer, String>();
				while (rs.next()) {
					adddiplomaList.put(rs.getInt("osdm_sub_degree_pk"), rs.getString("osdm_sub_degree_desc"));
				}
				return adddiplomaList;
			}
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<Integer, String> getAddPgList() throws Exception {
		return (Map<Integer, String>) getJdbcTemplate().query(CandidateQueries.SELECT_ADD_PG_LIST, new ResultSetExtractor() {
			@Override
			public Map<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer, String> addPgList = new LinkedHashMap<Integer, String>();
				while (rs.next()) {
					addPgList.put(rs.getInt("osdm_sub_degree_pk"), rs.getString("osdm_sub_degree_desc"));
				}
				return addPgList;
			}
		});
	}

	public AdditionalDetailsBean getCandidateQuotaDetails(Long loggedInUser, String courseId, String specialoptions) throws Exception {
		List<AdditionalDetailsBean> getCandidateDetailsList = null;
		try {
			getCandidateDetailsList = getJdbcTemplate().query(CandidateQueries.SELECT_CANDIDATE_QUOTA_DETAILS, new RowMapper<AdditionalDetailsBean>() {
				public AdditionalDetailsBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					AdditionalDetailsBean additionalDetailsBean = new AdditionalDetailsBean();
					// sports special
					/*
					 * additionalDetailsBean.setSportsSpecial(rs.getString("ocad_sports_special"));
					 * 
					 * additionalDetailsBean.setSportsQuaota(rs.getString("ocad_sports_quaota")); additionalDetailsBean.setSportsName(rs.getString("ocad_sports_name"));
					 * additionalDetailsBean.setEventName(rs.getString("ocad_event_name")); additionalDetailsBean.setDateOfStartEvent(rs.getString("ocad_date_of_start_event"));
					 * additionalDetailsBean.setDateOfComplEvent(rs.getString("ocad_date_of_compl_event"));
					 * additionalDetailsBean.setParticipationvel(rs.getString("ocad_participationvel"));
					 * additionalDetailsBean.setSportsCertificateNumber(rs.getString("ocad_sports_certificate_number"));
					 * additionalDetailsBean.setSportsCertIssuingAuthority(rs.getString("ocad_sports_cert_issuing_authority"));
					 * additionalDetailsBean.setDateOfsportsCertificate(rs.getString("ocad_date_of_sports_certificate")); // Ward Details Data
					 * additionalDetailsBean.setWardsQuaota(rs.getString("ocad_wards_quaota")); additionalDetailsBean.setWardname(rs.getString("ocad_wards_name"));
					 * additionalDetailsBean.setNameOfParent(rs.getString("ocad_name_of_parent")); additionalDetailsBean.setPresentRank(rs.getString("ocad_present_rank"));
					 * additionalDetailsBean.setPresentPosting(rs.getString("ocad_present_posting")); additionalDetailsBean.setGpsNumber(rs.getString("ocad_gps_number"));
					 * additionalDetailsBean.setSibilingWard(rs.getString("ocad_sibiling_ward"));
					 * additionalDetailsBean.setWardsCertificateNumber(rs.getString("ocad_wards_certificate_number"));
					 * additionalDetailsBean.setWardsCertIssuingAuthority(rs.getString("ocad_wards_cert_issuing_authority"));
					 * additionalDetailsBean.setDateOfwardsCertificate(rs.getString("ocad_date_of_wards_certificate"));
					 * additionalDetailsBean.setPresentPostingUnit(rs.getString("ocad_ward_present_posting_unit"));
					 * additionalDetailsBean.setPresentPostingMini(rs.getString("ocad_ward_present_posting_mini"));
					 * additionalDetailsBean.setPoliceStation(rs.getString("ocad_ward_present_posting_police_station"));
					 * additionalDetailsBean.setUnitsOther(rs.getString("ocad_ward_other_unit"));
					 * 
					 * // department quota details additionalDetailsBean.setDepartmentQuaota(rs.getString("ocad_dept_quota"));
					 * additionalDetailsBean.setDept_presentRank(rs.getString("ocad_Dept_present_rank"));
					 * additionalDetailsBean.setDept_presentPosting(rs.getString("ocad_dept_present_posting"));
					 * additionalDetailsBean.setDept_gpsNumber(rs.getString("ocad_dept_gps_number"));
					 * additionalDetailsBean.setDept_CertificateNumber(rs.getString("ocad_dept_certificate_number"));
					 * additionalDetailsBean.setDept_CertIssuingAuthority(rs.getString("ocad_dept_cert_issuing_authority"));
					 * additionalDetailsBean.setDept_dateOfEnlistment(rs.getString("ocad_dept_date_of_enlistment"));
					 * additionalDetailsBean.setDept_NocDate(rs.getString("ocad_noc_date"));
					 * additionalDetailsBean.setDept_serviceAsOn(rs.getString("ocad_dept_service_as_on_date"));
					 * additionalDetailsBean.setDeptpresentPostingUnit(rs.getString("ocad_dept_present_rank_unit"));
					 * additionalDetailsBean.setDept_policeStation(rs.getString("ocad_dept_policeStation"));
					 * 
					 * additionalDetailsBean.setNccCertificate(rs.getString("ocad_ncc_certificate"));
					 * additionalDetailsBean.setNccHighestCertificate(rs.getString("ocad_ncc_highest_certificate"));
					 * additionalDetailsBean.setNccCertificateNumber(rs.getString("ocad_ncc_certificate_number")); additionalDetailsBean.setNccUnit(rs.getString("ocad_ncc_unit"));
					 * additionalDetailsBean.setDateOfNccCertificate(rs.getString("ocad_date_of_ncc_certificate"));
					 * additionalDetailsBean.setNccCertIssuingAuthority(rs.getString("ocad_ncc_cert_issuing_authority"));
					 * additionalDetailsBean.setNssCertificate(rs.getString("ocad_nss_certificate"));
					 * additionalDetailsBean.setNssHighestCertificate(rs.getString("ocad_nss_highest_certificate"));
					 * additionalDetailsBean.setNssCertificateNumber(rs.getString("ocad_nss_certificate_number")); additionalDetailsBean.setNssUnit(rs.getString("ocad_nss_unit"));
					 * additionalDetailsBean.setDateOfNssCertificate(rs.getString("ocad_date_of_nss_certificate"));
					 * additionalDetailsBean.setNssCertIssuingAuthority(rs.getString("ocad_nss_cert_issuing_authority")); //
					 * additionalDetailsBean.setPresentPostingUnit(rs.getString("ocad_present_rank_unit")); //
					 * additionalDetailsBean.setPoliceStation(rs.getString("ocad_current_police_station")); //
					 * additionalDetailsBean.setUnitsOther(rs.getString("ocad_present_rank_unit_others"));
					 * additionalDetailsBean.setDept_unitsOther(rs.getString("ocad_dept_present_rank_unit_others"));
					 * additionalDetailsBean.setDepartmentType(rs.getString("ocad_department_type")); additionalDetailsBean.setPfUnit(rs.getString("ocad_unit"));
					 * additionalDetailsBean.setPfDistrict(rs.getString("ocad_district"));
					 * 
					 * additionalDetailsBean.setExServiceman(rs.getString("ocad_ex_serviceman")); additionalDetailsBean.setExServiceWoman(rs.getString("ocad_ex_service_woman") ==
					 * null ? "" : rs.getString("ocad_ex_service_woman")); additionalDetailsBean.setServiceNumber(rs.getString("ocad_service_number"));
					 * additionalDetailsBean.setDateOfEnlistment(rs.getString("ocad_date_of_enlistment"));
					 * 
					 * additionalDetailsBean.setDateOfDischarge(rs.getString("ocad_date_of_discharge"));
					 * additionalDetailsBean.setExCertificateNumber(rs.getString("ocad_ex_certificate_number"));
					 * additionalDetailsBean.setExCertIssuingAuthority(rs.getString("ocad_ex_cert_issuing_authority"));
					 * additionalDetailsBean.setDateOfExCertificate(rs.getString("ocad_date_of_ex_certificate"));
					 * 
					 * additionalDetailsBean.setWidow(rs.getString("ocad_widow")); additionalDetailsBean.setNameOfLateHusband(rs.getString("ocad_name_of_late_husband"));
					 * additionalDetailsBean.setDateOfDeath(rs.getString("ocad_date_of_death"));
					 * additionalDetailsBean.setDeathCertificateNumber(rs.getString("ocad_death_certificate_number"));
					 * additionalDetailsBean.setDeathCertIssuingAuthority(rs.getString("ocad_death_cert_issuing_authority"));
					 * additionalDetailsBean.setDateOfdeathCertificate(rs.getString("ocad_date_of_death_certificate"));
					 * 
					 * additionalDetailsBean.setExtraQualification(CommonUtil.getParameter(rs.getString("ocad_extra_qualification"))); additionalDetailsBean
					 * .setQualBEExtc(rs.getString("ocad_qual_be_extc") == null || "".equals(rs.getString("ocad_qual_be_extc")) ? false : rs.getBoolean("ocad_qual_be_extc"));
					 * additionalDetailsBean.setQualBscCSBscIT(rs.getString("ocad_qual_bsc_bca_cs_bscit") == null || "".equals(rs.getString("ocad_qual_bsc_bca_cs_bscit")) ? false :
					 * rs.getBoolean("ocad_qual_bsc_bca_cs_bscit")); additionalDetailsBean.setQualBEBTechCSIT(rs.getString("ocad_qual_be_btech_cs_it") == null ||
					 * "".equals(rs.getString("ocad_qual_be_btech_cs_it")) ? false : rs.getBoolean("ocad_qual_be_btech_cs_it")); additionalDetailsBean.setQualPGCA(
					 * rs.getString("ocad_qual_pg_comp_app") == null || "".equals(rs.getString("ocad_qual_pg_comp_app")) ? false : rs.getBoolean("ocad_qual_pg_comp_app"));
					 * additionalDetailsBean .setQualMEMTechCommSys(rs.getString("ocad_qual_me_mtech_comm_sys") == null || "".equals(rs.getString("ocad_qual_me_mtech_comm_sys")) ?
					 * false : rs.getBoolean("ocad_qual_me_mtech_comm_sys")); additionalDetailsBean.setQualMEMTechCSIT(rs.getString("ocad_qual_me_mtech_cs_it") == null ||
					 * "".equals(rs.getString("ocad_qual_me_mtech_cs_it")) ? false : rs.getBoolean("ocad_qual_me_mtech_cs_it"));
					 * additionalDetailsBean.setQualMCA(rs.getString("ocad_qual_mca") == null || "".equals(rs.getString("ocad_qual_mca")) ? false : rs.getBoolean("ocad_qual_mca"));
					 */return additionalDetailsBean;
				}
			}, new Object[] { loggedInUser, Integer.parseInt("10") });
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!getCandidateDetailsList.isEmpty()) {
			/*
			 * if (specialoptions != null && specialoptions.equalsIgnoreCase("1") && (getCandidateDetailsList.get(0).getExServiceman() == null ||
			 * getCandidateDetailsList.get(0).getExServiceman().equalsIgnoreCase("63"))) { getCandidateDetailsList.get(0).setWidow(null);
			 * getCandidateDetailsList.get(0).setNameOfLateHusband(null); getCandidateDetailsList.get(0).setDateOfDeath(null);
			 * getCandidateDetailsList.get(0).setDeathCertificateNumber(null); getCandidateDetailsList.get(0).setDeathCertIssuingAuthority(null);
			 * getCandidateDetailsList.get(0).setDateOfdeathCertificate(null); } else if (specialoptions != null && specialoptions.equalsIgnoreCase("4") &&
			 * (getCandidateDetailsList.get(0).getExServiceWoman() == null || getCandidateDetailsList.get(0).getExServiceWoman().equalsIgnoreCase("63"))) {
			 * getCandidateDetailsList.get(0).setWidow(null); getCandidateDetailsList.get(0).setNameOfLateHusband(null); getCandidateDetailsList.get(0).setDateOfDeath(null);
			 * getCandidateDetailsList.get(0).setDeathCertificateNumber(null); getCandidateDetailsList.get(0).setDeathCertIssuingAuthority(null);
			 * getCandidateDetailsList.get(0).setDateOfdeathCertificate(null); } if (specialoptions != null && specialoptions.equalsIgnoreCase("2") &&
			 * (getCandidateDetailsList.get(0).getWidow() == null || getCandidateDetailsList.get(0).getWidow().equalsIgnoreCase("No"))) {
			 * getCandidateDetailsList.get(0).setExServiceman(null); getCandidateDetailsList.get(0).setExServiceWoman(null); getCandidateDetailsList.get(0).setServiceNumber(null);
			 * getCandidateDetailsList.get(0).setDateOfEnlistment(null); getCandidateDetailsList.get(0).setDateOfDischarge(null);
			 * getCandidateDetailsList.get(0).setExCertificateNumber(null); getCandidateDetailsList.get(0).setExCertIssuingAuthority(null);
			 * getCandidateDetailsList.get(0).setDateOfExCertificate(null);
			 * 
			 * } if (specialoptions != null && specialoptions.equalsIgnoreCase("3")) { getCandidateDetailsList.get(0).setExServiceman(null);
			 * getCandidateDetailsList.get(0).setExServiceWoman(null); getCandidateDetailsList.get(0).setServiceNumber(null);
			 * getCandidateDetailsList.get(0).setDateOfEnlistment(null); getCandidateDetailsList.get(0).setDateOfDischarge(null);
			 * getCandidateDetailsList.get(0).setExCertificateNumber(null); getCandidateDetailsList.get(0).setExCertIssuingAuthority(null);
			 * getCandidateDetailsList.get(0).setDateOfExCertificate(null);
			 * 
			 * getCandidateDetailsList.get(0).setWidow(null); getCandidateDetailsList.get(0).setNameOfLateHusband(null); getCandidateDetailsList.get(0).setDateOfDeath(null);
			 * getCandidateDetailsList.get(0).setDeathCertificateNumber(null); getCandidateDetailsList.get(0).setDeathCertIssuingAuthority(null);
			 * getCandidateDetailsList.get(0).setDateOfdeathCertificate(null);
			 * 
			 * }
			 */
			return getCandidateDetailsList.get(0);
		} else
			return null;
	}

	@Override
	public int deletePreviousDocumentsforAll(String combination, Users loggedInUser) throws Exception {
		int delete = 0;
		try {
			if (combination.equals("")) {
				String query = "delete from oes_candidate_doc where ocd_user_fk = ?";
				delete = writeJdbcTemplate.update(query, new Object[] { loggedInUser.getUserId() });
			} else {
				String query = "delete from oes_candidate_doc where ocd_flag not in (" + combination + ") and ocd_user_fk = ?";
				delete = writeJdbcTemplate.update(query, new Object[] { loggedInUser.getUserId() });
			}
		} catch (Exception e) {
			delete = 0;
		}
		return delete;
	}

	@Override
	public int deleteDocumentsforAdditionaliDoc(String combination, Users loggedInUser) throws Exception {
		int delete = 0;
		try {
			String query = "delete from oes_candidate_doc where ocd_flag  in (" + combination + ") and ocd_user_fk = ?";
			delete = writeJdbcTemplate.update(query, new Object[] { loggedInUser.getUserId() });

		} catch (Exception e) {
			delete = 0;
		}
		return delete;
	}

	public String getCandidateDob(Users loggedInUser) throws Exception {
		String DOb = "";
		try {
			DOb = (String) getJdbcTemplate().queryForObject(CandidateQueries.CANDIDATE_DOB, String.class, new Object[] { loggedInUser.getUserId() });
		} catch (Exception e) {
			DOb = "";
		}
		return DOb;
	}

	public String getCandidateChallanDownloadedDate(Users loggedInUser) throws Exception {
		String getCandidateChallanDownloadedDate = "";
		try {
			getCandidateChallanDownloadedDate = (String) getJdbcTemplate().queryForObject(CandidateQueries.getCandidateChallanDownloadedDate, String.class,
					new Object[] { loggedInUser.getUserId() });
		} catch (Exception e) {
			getCandidateChallanDownloadedDate = "";
		}
		return getCandidateChallanDownloadedDate;
	}

	public int checkCandidateAlreadyAppliedPostforID(Users loggedInUser) throws Exception {
		int count = 0;
		try {
			count = getJdbcTemplate().queryForObject(CandidateQueries.GET_CANDIDATE_Already_Applied_POSTFORID, Integer.class, new Object[] { loggedInUser.getUserId() });
		} catch (Exception e) {
			throw new GenericException(e);

		}
		return count;
	}

	public Long getUsersStatusId(String registrationId) throws Exception {
		Long userId;
		try {
			userId = (Long) getJdbcTemplate().queryForObject(CandidateQueries.GET_CAND_STATUS_ID, Long.class, new Object[] { Integer.valueOf(registrationId) });
		} catch (Exception e) {
			userId = new Long(0);
		}
		return userId;
	}

	public String insertPDFJsonString1(long UserFk, long testfk, String encodeJson, String UserId) throws Exception {
		String updateVal = null;
		int count = 0;
		try {
			count = writeJdbcTemplate.update(CandidateQueries.INSER_JSON_PDF1, new Object[] { UserFk, testfk, encodeJson, UserId });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		updateVal = String.valueOf(count);
		return updateVal;
	}

	@Override
	public String getPaymentStatusForPdfApplicationNumber(long userFk, long testfk) throws Exception {
		String validateStatus = null;
		try {
			validateStatus = (String) getJdbcTemplate().queryForObject(CandidateQueries.GET_PAYMENT_STATUS_FOR_PDF_APPLICATION_NUMBER, String.class, new Object[] { userFk });
		} catch (Exception e) {
			validateStatus = null;
		}
		if (validateStatus != null && validateStatus != "" && validateStatus.equalsIgnoreCase("A")) {
			return validateStatus = "A";
		} else {
			return validateStatus = "R";
		}
	}

	@Override
	public String getCandidatePhotoName(Long userId) throws Exception {
		String candidatePhotoName = null;
		try {
			candidatePhotoName = (String) getJdbcTemplate().queryForObject(CandidateQueries.GET_CANDIDATE_PHOTO_NAME, String.class, new Object[] { userId });
		} catch (Exception e) {
			candidatePhotoName = "a.jpg";
		}
		return candidatePhotoName;
	}

	@Override
	public String getCandidateSignatureName(Long userId) throws Exception {
		String candidateSignatureName = null;
		try {
			candidateSignatureName = (String) getJdbcTemplate().queryForObject(CandidateQueries.GET_CANDIDATE_SIGNATURE_NAME, String.class, new Object[] { userId });
		} catch (Exception e) {
			candidateSignatureName = "a.jpg";
		}
		return candidateSignatureName;
	}

	@Override
	public int checkMobNo(CandidateBean candidateBean) throws Exception {
		int mobileCount = 0;
		try {
			mobileCount = getJdbcTemplate().queryForObject(CommonQueries.CHECK_IF_MOB_EXISTS, Integer.class,
					new Object[] { candidateBean.getMobileNo(), Integer.parseInt(candidateBean.getPostName()) });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return mobileCount;
	}

	@Override
	public int exceededMOBAttempts(String mobileNo1, String courseVal) {
		int otp_status = 0;
		try {
			otp_status = getJdbcTemplate().queryForObject(CommonQueries.CHECK_MOB_OTP_COUNT, Integer.class, new Object[] { mobileNo1, Integer.parseInt(courseVal) });
		} catch (Exception e) {
			otp_status = 0;
		}
		return otp_status;
	}

	@Override
	public String getCandidateCategory(Users loggedInUser) throws Exception {
		String category = null;
		try {
			category = (String) getJdbcTemplate().queryForObject(CommonQueries.GET_CANDIDATE_CATEGORY, String.class, new Object[] { loggedInUser.getUserFk() });
		} catch (Exception e) {
			logger.info("No Category found in Database for" + loggedInUser.getUserFk());
		}
		return category;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<Integer, String> getOtherNativeList() throws Exception {
		Map<Integer, String> stateMap = new LinkedHashMap<Integer, String>();
		stateMap = (Map<Integer, String>) getJdbcTemplate().query(MasterQueries.GET_COUNTRY_STATE_MAPPING, new ResultSetExtractor() {
			@Override
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer, String> stateMap = new LinkedHashMap<Integer, String>();
				while (rs.next()) {
					stateMap.put(rs.getInt("osm_state_pk"), rs.getString("osm_state_name"));
				}
				return stateMap;
			}
		}, new Object[] { 1 });
		return stateMap;
	}

	@Override
	public String saveScoreCardDownloadStatus(String downloadFlag, Users loggedInUser) {
		int updateStage = 0;
		try {
			updateStage = writeJdbcTemplate.update(CandidateQueries.UPDATE_SCORE_STATUS, new Object[] { loggedInUser.getUserId() });
			if (updateStage > 0) {
				return "UpdateStatus";
			}
		} catch (Exception ex) {
			updateStage = 0;
		}
		return "NotUpdate";
	}

	@Override
	public String saveCallLetterDownloadStatus(String downloadFlag, Users loggedInUser) {
		int updateStage = 0;
		try {
			/*
			 * if(downloadFlag.equals("Open")) { updateStage= writeJdbcTemplate.update(CandidateQueries.UPDATE_OPEN_STATUS,new Object[]{loggedInUser.getUserId()}); } else
			 * if(downloadFlag.equals("Dept")) { updateStage= writeJdbcTemplate.update(CandidateQueries.UPDATE_DEPT_STATUS,new Object[]{loggedInUser.getUserId()}); }
			 */
			updateStage = writeJdbcTemplate.update(CandidateQueries.UPDATE_CALL_LETTER_STATUS, new Object[] { loggedInUser.getUserId() });
			if (updateStage > 0) {
				return "UpdateStatus";
			}
		} catch (Exception ex) {
			updateStage = 0;
		}
		return "NotUpdate";
	}

	@Override
	public String savePracticeTestStatus(String downloadFlag, Users loggedInUser) {
		int updateStage = 0;
		try {
			updateStage = writeJdbcTemplate.update(CandidateQueries.UPDATE_PRACTICE_TEST_STATUS, new Object[] { loggedInUser.getUserId() });
			if (updateStage > 0) {
				return "UpdateStatus";
			}
		} catch (Exception ex) {
			updateStage = 0;
		}
		return "NotUpdate";
	}

	@Override
	public String saveAdmitCardDownloadStatus(String downloadFlag, Users loggedInUser) {
		int updateStage = 0;
		try {
			updateStage = writeJdbcTemplate.update(CandidateQueries.UPDATE_ADMIT_STATUS, new Object[] { loggedInUser.getUserId() });
			if (updateStage > 0) {
				return "UpdateStatus";
			}
		} catch (Exception ex) {
			updateStage = 0;
		}
		return "NotUpdate";
	}

	@Override
	public String getUserDisability(Long userFk) throws Exception {
		return (String) getJdbcTemplate().queryForObject(CommonQueries.GET_USER_DISABILITY, String.class, new Object[] { userFk });
	}

	@Override
	public String getDiscipline(Long userFk) throws Exception {
		return (String) getJdbcTemplate().queryForObject(CommonQueries.GET_USER_Discipline, String.class, new Object[] { userFk });
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<Integer, String> getDiplomaLawList() throws Exception {
		return (Map<Integer, String>) getJdbcTemplate().query(CandidateQueries.SELECT_DIPLOMA_LAW_LIST, new ResultSetExtractor() {
			@Override
			public Map<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer, String> degreeList = new LinkedHashMap<Integer, String>();
				while (rs.next()) {
					degreeList.put(rs.getInt("osdm_sub_degree_pk"), rs.getString("osdm_sub_degree_desc"));
				}
				return degreeList;
			}
		});
	}

	// Added for age matrix starts
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, String> getAgeMatrixDetails(String categoryVal, String isExServiceMen, String governmentEmp) throws Exception {
		Map<String, String> ageMatrixList = null;
		try {
			ageMatrixList = (Map<String, String>) getJdbcTemplate().query(CandidateQueries.GET_AGE_MATRIX, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, String> ageMatrixListMap = new LinkedHashMap<String, String>();
					while (rs.next()) {
						ageMatrixListMap.put("max_age", rs.getString("MAXAGE_YRS"));
						ageMatrixListMap.put("START_DATE", rs.getString("START_DATE"));
					}
					return ageMatrixListMap;
				}
			}, new Object[] { categoryVal, isExServiceMen, governmentEmp });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return ageMatrixList;
	}
	// Added for age matrix ends

	@Override
	public String checkMobNoOTP(String mobileNo) {
		int status = 0;
		String updateStatus = null;
		try {
			status = getJdbcTemplate().queryForObject(CandidateQueries.CHECK_OTP_EXIST_IN_MASTER, Integer.class, new Object[] { mobileNo });
			if (status > 0) {
				updateStatus = "Exists";
			}
		} catch (Exception e) {
		}
		return updateStatus;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Users getBasicInfoForUploadDoc(Users loggedInUser) {
		String add_edu_query = "SELECT ocd_category_fk,  ocd_discipline , ocd_govt_servant," + "oum_exserviceman,oum_community,oum_widowyesno FROM oes_candidate_details ocd , "
				+ "oes_user_master oum WHERE oum.oum_user_pk = ocd.ocd_user_fk AND ocd_user_fk=?";
		Users users = null;
		users = (Users) getJdbcTemplate().query(add_edu_query, new ResultSetExtractor() {
			@Override
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				Users users = null;
				if (rs.next()) {
					try {
						users = new Users();
						users.setExServiceMen(rs.getString("oum_exserviceman"));
						users.setCommunity(rs.getString("oum_community"));
						users.setDiscipline(rs.getString("ocd_discipline"));
						users.setWidowYesNo(rs.getString("oum_widowyesno"));

					} catch (Exception e) {
						logger.fatal(e, e);
					}
				}
				return users;
			}
		}, new Object[] { loggedInUser.getUserId() });
		return users;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String documentVerificationStatus(long userPk, int parseInt, String documentFileName) throws Exception {
		String data = "";
		try {
			writeJdbcTemplate.update(CandidateQueries.UPDATE_DOCUMENT_VERIFY_STATUS, new Object[] { parseInt, documentFileName });
			data = (String) getJdbcTemplate().query(CandidateQueries.DOC_COUNT_FILENAME, new ResultSetExtractor() {
				@Override
				public String extractData(ResultSet rs) throws SQLException, DataAccessException {
					String data = "";
					while (rs.next()) {
						if (rs.getString("uploaded").equals(rs.getString("confirmed"))) {
							data = "";
						} else {
							data = rs.getString("filenames");
						}
					}
					return data;
				}
			}, new Object[] { userPk, userPk, userPk });
		} catch (Exception e) {
			data = "";
			logger.info("Exception in documentVerificationStatus() CandidateDaoImpl for User FK : " + userPk + e.getMessage());
		}
		return data;
	}
}
