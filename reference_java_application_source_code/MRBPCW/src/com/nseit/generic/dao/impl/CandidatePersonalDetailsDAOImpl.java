package com.nseit.generic.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.nseit.generic.dao.BaseDAO;
import com.nseit.generic.dao.CandidatePersonalDetailsDAO;
import com.nseit.generic.models.AddressBean;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.PersonalDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.queries.CandidateQueries;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.LoggerHome;

public class CandidatePersonalDetailsDAOImpl extends BaseDAO implements CandidatePersonalDetailsDAO {
	private String ENC_KEY = ConfigurationConstants.getInstance().getENC_KEY();

	private Logger logger = LoggerHome.getLogger(getClass());
	private JdbcTemplate writeJdbcTemplate;

	public void setWriteJdbcTemplate(JdbcTemplate writeJdbcTemplate) {
		this.writeJdbcTemplate = writeJdbcTemplate;
	}

	@Override
	public int saveCandidateDetails(CandidateBean candidateBean, AddressBean addressBean, PersonalDetailsBean personalDetailsBean, Users loggedInUser) throws Exception {
		int saveCandidateDetails = 0;
		if (StringUtils.isBlank(ENC_KEY))
			ENC_KEY = ConfigurationConstants.getInstance().getENC_KEY();
		
		loggedInUser.setDisciplinName(ConfigurationConstants.getInstance().getDisciplineValueForKey(Integer.parseInt(loggedInUser.getDisciplineID())));
		String age = (candidateBean.getAgeInYears() + " years " + candidateBean.getAgeInMonths() + " months " + candidateBean.getAgeInDays() + " days");
		
		if (StringUtils.isNotBlank(candidateBean.getStateVal()) && candidateBean.getStateVal().equals("1"))
			candidateBean.setDistrictValother("");
		if (StringUtils.isNotBlank(candidateBean.getAltStateVal()) && candidateBean.getAltStateVal().equals("1"))
			candidateBean.setAltDistrictValOthers("");
			
		try {

			String INSERT_CANDIDATE_DETAILS_ENC1 = "INSERT INTO oes_candidate_details(ocd_cand_prsnl_detl_pk, ocd_user_fk, ocd_cand_father_first_name,"
					+ "ocd_cand_mother_first_name, ocd_marital_status,ocd_spouse_name,ocd_nativity,ocd_other_nativity, ocd_genderfk, ocd_religion, "
					+ "ocd_other_religion, ocd_govt_servant,ocd_org_name,ocd_curr_designation, ocd_plc_of_wrk,ocd_photoidproof1,ocd_photoidproof1val,"
					+ " ocd_perm_address, ocd_perm_state_fk, ocd_perm_district_fk,ocd_perm_district_textbox,ocd_perm_city, ocd_perm_pin_code, ocd_comm_same_as_perm, "
					+ " ocd_comm_address, ocd_comm_state_fk, ocd_comm_district_fk,ocd_comm_district_textbox,ocd_comm_city, ocd_comm_pin_code,ocd_mother_tongue,ocd_certificate_declaration, "
					+ "ocd_relative_fk, ocd_guardian_name, ocd_govt_date, ocd_govt_age, ocd_created_by, ocd_created_date) "
					+ " VALUES (nextval('oes_candidate_details_seq'), ?, ?, "
					+ " ?,?,?,?,?,?,?,"
					+ " ?,?,?,?,?,?,?,"
					+ " pgp_sym_encrypt(?, ?,'cipher-algo=aes256'), ?, "
					+ " ?,?,?,?,?, pgp_sym_encrypt(?, ?,'cipher-algo=aes256'),?,?,?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";
					
			
			if (loggedInUser.getUserFk() == 0 || loggedInUser.getUserFk() == null || loggedInUser.getUserFk().equals("")) {
				saveCandidateDetails = writeJdbcTemplate.update(INSERT_CANDIDATE_DETAILS_ENC1, new PreparedStatementSetter() {
					public void setValues(PreparedStatement pStmt) throws SQLException {
						pStmt.setLong(1, loggedInUser.getUserId());
						pStmt.setString(2, candidateBean.getFathersName());
						
						pStmt.setString(3, candidateBean.getMothersName());
						pStmt.setString(4, candidateBean.getMariatalStatus());
						pStmt.setString(5, candidateBean.getSpouseName());
						pStmt.setString(6, candidateBean.getNativity());
						pStmt.setString(7, candidateBean.getNativity().equals("39") ? candidateBean.getOtherNativity():"");
						pStmt.setString(8, loggedInUser.getGenderValDesc());
						pStmt.setString(9, candidateBean.getReligionBelief());
						
						pStmt.setString(10, candidateBean.getReligionBeliefOthers());
						pStmt.setString(11, candidateBean.getIsGovtService());
						pStmt.setString(12, candidateBean.getOrgName());
						pStmt.setString(13, candidateBean.getCurrentDesig());
						pStmt.setString(14, candidateBean.getPlaceOfWork());
						pStmt.setString(15, candidateBean.getPhotoIDProof1());
						pStmt.setString(16,candidateBean.getPhotoIDProof1Val());
						
						pStmt.setString(17, addressBean.getAddress());
						pStmt.setString(18, ENC_KEY);
						pStmt.setInt(19, Integer.parseInt(candidateBean.getStateVal()));
						
						pStmt.setString(20, candidateBean.getDistrictVal());
						pStmt.setString(21, candidateBean.getDistrictValother());
						pStmt.setString(22, addressBean.getCityName());
						pStmt.setString(23, addressBean.getPinCode());
						pStmt.setString(24, candidateBean.isAddressChkBox() ? "Y" : "N");
						pStmt.setString(25, addressBean.getAlternateAddress());
						pStmt.setString(26, ENC_KEY);
						pStmt.setInt(27, Integer.parseInt(candidateBean.getAltStateVal()));
						pStmt.setString(28, candidateBean.getAltDistrictVal());
						pStmt.setString(29, candidateBean.getAltDistrictValOthers());
						pStmt.setString(30, addressBean.getAlternateCity());
						pStmt.setString(31, addressBean.getAlternatePinCode());
						pStmt.setString(32, candidateBean.getMotherTongue());
						pStmt.setInt(33, candidateBean.isGovtServChkBox()?6:7);
						pStmt.setLong(34, candidateBean.getParentAndGuardian());
						pStmt.setString(35, candidateBean.getGuardianName());
						pStmt.setString(36, candidateBean.getGovtDate());
						{
							if(age.equals(" years  months  days"))
								pStmt.setString(37, null);
							else
								pStmt.setString(37, age);
						}
						pStmt.setString(38, loggedInUser.getUsername());
						
					}
				});
			} else {
				String UPDATE_CANDIDATE_DETAILS_ENC1 = "UPDATE oes_candidate_details SET "
						+ " ocd_cand_father_first_name = ?, ocd_cand_mother_first_name = ?,ocd_marital_status = ?,ocd_spouse_name=?, ocd_nativity=?,ocd_other_nativity=?,"
						+ " ocd_genderfk = ?, ocd_religion = ?, ocd_other_religion = ?,ocd_govt_servant=?,ocd_org_name=?,ocd_curr_designation=?, ocd_plc_of_wrk=?,"
						+ " ocd_photoidproof1=?,ocd_photoidproof1val=?,"
						+ " ocd_perm_address = pgp_sym_encrypt(?, ?,'cipher-algo=aes256'), ocd_perm_state_fk = ?, ocd_perm_district_fk = ?,ocd_perm_district_textbox=?,ocd_perm_city=?,"
						+ " ocd_perm_pin_code = ?,ocd_comm_same_as_perm = ?,"
						+ " ocd_comm_address = pgp_sym_encrypt(?, ?,'cipher-algo=aes256'), ocd_comm_state_fk = ?, ocd_comm_district_fk = ?,ocd_comm_district_textbox=?,ocd_comm_city=?,"
						+ " ocd_comm_pin_code = ?,ocd_mother_tongue=?, ocd_certificate_declaration=?,  ocd_relative_fk=?, ocd_guardian_name=?,ocd_govt_date=?, ocd_govt_age=?, ocd_updated_by = ?, ocd_updated_date = CURRENT_TIMESTAMP WHERE ocd_user_fk = ?";
				
				saveCandidateDetails = writeJdbcTemplate.update(UPDATE_CANDIDATE_DETAILS_ENC1, new PreparedStatementSetter() {
					public void setValues(PreparedStatement pStmt) throws SQLException {

						pStmt.setString(1, candidateBean.getFathersName());
						pStmt.setString(2, candidateBean.getMothersName());
						pStmt.setString(3, candidateBean.getMariatalStatus());
						pStmt.setString(4, candidateBean.getSpouseName());
						pStmt.setString(5, candidateBean.getNativity());
						pStmt.setString(6, candidateBean.getNativity().equals("39") ? candidateBean.getOtherNativity():"");
						pStmt.setString(7, loggedInUser.getGenderValDesc());
						pStmt.setString(8, candidateBean.getReligionBelief());
						pStmt.setString(9, candidateBean.getReligionBeliefOthers());
						pStmt.setString(10, candidateBean.getIsGovtService());
						pStmt.setString(11, candidateBean.getOrgName());
						pStmt.setString(12, candidateBean.getCurrentDesig());
						pStmt.setString(13, candidateBean.getPlaceOfWork());
						pStmt.setString(14, candidateBean.getPhotoIDProof1());
						pStmt.setString(15,candidateBean.getPhotoIDProof1Val());
						pStmt.setString(16, addressBean.getAddress());
						pStmt.setString(17, ENC_KEY);
						pStmt.setInt(18, Integer.parseInt(candidateBean.getStateVal()));
						pStmt.setString(19, candidateBean.getDistrictVal());
						pStmt.setString(20, candidateBean.getDistrictValother());
						pStmt.setString(21, addressBean.getCityName());
						pStmt.setString(22, addressBean.getPinCode());
						pStmt.setString(23, candidateBean.isAddressChkBox() ? "Y" : "N");
						pStmt.setString(24, addressBean.getAlternateAddress());
						pStmt.setString(25, ENC_KEY);
						pStmt.setInt(26, Integer.parseInt(candidateBean.getAltStateVal()));
						pStmt.setString(27, candidateBean.getAltDistrictVal());
						pStmt.setString(28, candidateBean.getAltDistrictValOthers());
						pStmt.setString(29, addressBean.getAlternateCity());
						pStmt.setString(30, addressBean.getAlternatePinCode());
						pStmt.setString(31, candidateBean.getMotherTongue());
						pStmt.setInt(32, candidateBean.isGovtServChkBox()?6:7);
						pStmt.setLong(33, candidateBean.getParentAndGuardian());
						pStmt.setString(34, candidateBean.getGuardianName());
						pStmt.setString(35, candidateBean.getGovtDate());
						{
							if(age.equals(" years  months  days"))
								pStmt.setString(36, null);
							else
								pStmt.setString(36, age);
						}
						pStmt.setString(37, loggedInUser.getUsername());
						pStmt.setLong(38,loggedInUser.getUserId());
					}
				});
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		// stored for education page
		loggedInUser.setDate_Of_Birth(candidateBean.getDateOfBirth());
		return saveCandidateDetails;
	}

	public PersonalDetailsBean getCandidateName(long userFk) throws Exception {
		List<PersonalDetailsBean> personalDetailsBean = null;
		personalDetailsBean = getJdbcTemplate().query(CandidateQueries.SELECT_CANDIDATE_NAME_DETAILS, new RowMapper<PersonalDetailsBean>() {
			public PersonalDetailsBean mapRow(ResultSet rs, int rowCount) throws SQLException {
				PersonalDetailsBean personalDetailsBean = new PersonalDetailsBean();
				personalDetailsBean.setCandidateFirstName(rs.getString("oum_first_name"));
				personalDetailsBean.setCandidateMiddleName(rs.getString("oum_middle_name"));
				personalDetailsBean.setCandidateLastName(rs.getString("oum_last_name"));
				return personalDetailsBean;
			}
		}, new Object[] { userFk });
		if (!personalDetailsBean.isEmpty())
			return personalDetailsBean.get(0);
		else
			return null;
	}

	@Override
	public int updateCandidateDetails(CandidateBean candidateBean, Users loggedInUser) throws GenericException {
		int update = 0;
		PersonalDetailsBean personalDetailsBean = null;
		if (candidateBean != null) {
			personalDetailsBean = candidateBean.getPersonalDetailsBean();
		}
		update = writeJdbcTemplate.update(CandidateQueries.UPDATE_USER_DETAILS, new Object[] { candidateBean.getDisciplineType(), personalDetailsBean.getEmail(),
				personalDetailsBean.getMobileNo(), loggedInUser.getUsername(), loggedInUser.getUserId() });
		return update;
	}

	public CandidateBean getCandidateDetails(Users loggedInUser, CandidateBean candidateBean) throws Exception {
		Long userId = 0L;
		if (StringUtils.isBlank(ENC_KEY))
			ENC_KEY = ConfigurationConstants.getInstance().getENC_KEY();
		
		List<CandidateBean> getCandidateDetailsList = null;
		if (loggedInUser != null) {
			userId = loggedInUser.getUserId();
		}
		try {
			getCandidateDetailsList = getJdbcTemplate().query(CandidateQueries.SELECT_CANDIDATE_DETAILS_ENC1, new RowMapper<CandidateBean>() {
				public CandidateBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					CandidateBean candidateBean = new CandidateBean();
					PersonalDetailsBean personalBean = new PersonalDetailsBean();
					AddressBean address = new AddressBean();
					try {
						candidateBean.setDisciplineId(rs.getInt("oum_test_fk"));
						candidateBean.setCandidatePk(rs.getString("ocd_cand_prsnl_detl_pk"));
						candidateBean.setStatusID(rs.getInt("ocd_status_id_fk"));
						candidateBean.setDisciplinName(rs.getString("ocd_discipline"));

						personalBean.setCandidateName(rs.getString("oum_full_name"));
						personalBean.setCandidatePrefix(rs.getString("oum_initial"));
						candidateBean.setNationality(rs.getString("oum_nationality"));
						personalBean.setEmail(rs.getString("oum_email_id"));
						personalBean.setMobileNo(rs.getString("oum_mobile_no"));
						personalBean.setAppliedDist(rs.getString("oum_nativity_cert_dist"));

						candidateBean.setDomicileUp(rs.getString("ocd_domicile"));
						candidateBean.setDateOfBirth(rs.getString("ocd_date_of_birth"));
						personalBean.setAge(rs.getString("oum_age"));
						candidateBean.setGenderValDesc(rs.getString("oum_genderfk"));
						candidateBean.setMariatalStatus(rs.getString("ocd_marital_status"));
						candidateBean.setSpouseName(rs.getString("ocd_spouse_name"));
						candidateBean.setParentAndGuardian(rs.getInt("ocd_relative_fk"));
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
						candidateBean.setStateVal(rs.getString("ocd_perm_state_fk"));
						candidateBean.setDistrictVal(rs.getString("ocd_perm_district_fk"));
						candidateBean.setDistrictValother(rs.getString("ocd_perm_district_textbox"));
						address.setCityName(rs.getString("ocd_perm_city"));
						address.setPinCode(rs.getString("ocd_perm_pin_code"));

						candidateBean.setAddressChkBox(CommonUtil.getParameter(rs.getString("ocd_comm_same_as_perm")).compareTo("Y") == 0 ? true : false);

						address.setAlternateAddress(rs.getString("ocd_comm_address"));
						candidateBean.setAltStateVal(rs.getString("ocd_comm_state_fk"));
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
						
						candidateBean.setWidowYesNo(rs.getString("oum_widowYesNo"));
						candidateBean.setDesWidowCertNo(rs.getString("oum_widowCertNo"));
						candidateBean.setWidowIssueDate(rs.getString("oum_widowIssueDate"));
						candidateBean.setWidowIssueAuth(rs.getString("oum_widowIssueAuth"));
						candidateBean.setWidowDistrict(rs.getString("oum_widowIssueDist"));
						candidateBean.setWidowOtherDistrict(rs.getString("oum_widowIssueDistOther"));
						candidateBean.setWidowSubDivision(rs.getString("oum_widowSubDivision"));
						candidateBean.setGovtDate(rs.getString("ocd_govt_date"));
						candidateBean.setGovtAge(rs.getString("ocd_govt_age"));
						
						//candidateBean.setIsWidowCheckbox(rs.getString("widowCheckbox"));
						candidateBean.setAddressBean(address);
						candidateBean.setPersonalDetailsBean(personalBean);
					} catch (SQLException e) {
						logger.info(e.getMessage());
					} catch (Exception e) {
						logger.info(e.getMessage());
					}
					return candidateBean;
				}
			}, new Object[] { ENC_KEY, ENC_KEY, ENC_KEY, ENC_KEY, ENC_KEY, ENC_KEY, ENC_KEY, ENC_KEY, ENC_KEY, ENC_KEY, userId });
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		if (getCandidateDetailsList != null && !getCandidateDetailsList.isEmpty()) {
			return getCandidateDetailsList.get(0);
		} else {
			return null;
		}
	}
}
