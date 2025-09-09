package com.nseit.generic.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.nseit.generic.dao.BaseDAO;
import com.nseit.generic.dao.LoginDao;
import com.nseit.generic.models.LoginBean;
import com.nseit.generic.models.RegistrationBean;
import com.nseit.generic.models.SessionDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.queries.UserQueries;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.LoggerHome;

public class LoginDaoImpl extends BaseDAO implements LoginDao {
	private Logger logger = LoggerHome.getLogger(getClass());
	private String ENC_KEY = ConfigurationConstants.getInstance().getENC_KEY();
	private JdbcTemplate writeJdbcTemplate;

	public void setWriteJdbcTemplate(JdbcTemplate writeJdbcTemplate) {
		this.writeJdbcTemplate = writeJdbcTemplate;
	}

	public Users getUserForUsername(String username) throws Exception {
		List<Users> lstUsers = null;

		try {
			lstUsers = getJdbcTemplate().query(UserQueries.SELECT_USER_FOR_LOGIN_ENC1, new RowMapper<Users>() {
				public Users mapRow(ResultSet rs, int rowCount) throws SQLException {
					Users users = new Users();
					try {
						users.setInvalidCount(rs.getInt("invalid_count"));
						users.setActiveStatus(rs.getString("oum_status"));
						users.setUserFk(rs.getLong("ocd_user_fk"));
						users.setUserId(rs.getLong("oum_user_pk"));
						users.setUsername(rs.getString("oum_user_id"));
						users.setPassword(rs.getString("oum_password"));
						users.setUserType(rs.getString("oum_user_type"));
						users.setStage(rs.getDouble("oum_stage_fk") + "");
						users.setDisciplineID(rs.getString("oum_test_fk"));
						users.setDeclaration(rs.getString("declaration_checkbox_status"));
						users.setStatusID(rs.getInt("ocd_status_id_fk"));
						users.setCandidateStatusId(rs.getInt("ocd_status_id_fk"));
						if (rs.getInt("oum_category_fk") != 0) {
							users.setCategory(rs.getInt("oum_category_fk"));
						}
						if (StringUtils.isNotBlank(rs.getString("ocd_user_fk"))) {
							users.setUserPk(Long.parseLong(rs.getString("ocd_user_fk")));
						}
						if (StringUtils.isNotBlank(rs.getString("ocd_test_center1"))) {
							users.setTestCenter1(rs.getString("ocd_test_center1"));
						}
						if (StringUtils.isNotBlank(rs.getString("ocd_test_center2"))) {
							users.setTestCenter2(rs.getString("ocd_test_center2"));
						}
						if (StringUtils.isNotBlank(rs.getString("ocd_test_center3"))) {
							users.setTestCenter3(rs.getString("ocd_test_center3"));
						}
						users.setEmailAddress(rs.getString("oum_email_id"));
						users.setMobile(rs.getString("oum_mobile_no"));
						users.setRoleFK(rs.getString("oum_role_fk"));
						users.setNativityCertDist(rs.getString("oum_nativity_cert_dist"));
						users.setNationality(rs.getString("oum_nationality"));
						if (StringUtils.isNotBlank(rs.getString("oum_date_of_birth"))) {
							users.setDate_Of_Birth(rs.getString("oum_date_of_birth"));
							users.setAge(rs.getString("oum_age"));
						}
						if (StringUtils.isNotBlank(rs.getString("ocd_discipline"))) {
							users.setDiscipline(rs.getString("ocd_discipline"));
						}
						users.setInitials(rs.getString("oum_initial"));
						users.setGenderValDesc(rs.getString("oum_genderfk"));
						users.setCandidateFirstName(rs.getString("oum_first_name"));
						users.setCandidateMiddleName(rs.getString("oum_middle_name"));
						users.setCandidateLastName(rs.getString("oum_last_name"));
						users.setCandidateName(rs.getString("oum_full_name"));
						users.setCategoryValDesc(rs.getString("ocd_category_fk"));
						users.setPhysicalDisability(rs.getString("ocd_benchmarkDisability"));
						users.setExServiceMen(rs.getString("oum_exserviceman"));
						users.setDischargeDate(rs.getString("oum_discharge_date"));
						users.setPpoNumber(rs.getString("oum_ppo_number"));
						users.setCommCertYesNo(rs.getString("oum_comm_cert_yesno"));
						users.setCommunity(rs.getString("oum_community"));
						users.setSubCaste(rs.getString("oum_subcaste"));
						users.setIssueAuthCommCert(rs.getString("oum_issue_auth_comm_cert"));
						users.setCommCertNo(rs.getString("oum_comm_cert_number"));
						users.setCommCertPlace(rs.getString("oum_comm_cert_issue_place"));
						users.setCommIssueDate(rs.getString("oum_comm_cert_issue_date"));
						users.setDisableYesNo(rs.getString("oum_disability_yesno"));
						if(rs.getInt("oum_diiferently_abled_checkbox")==6) 
							users.setDiffAbledChkBox(true);
						else
							users.setDiffAbledChkBox(false);
						users.setDisableType(rs.getString("oum_disability_type"));
						users.setDisablityPercent(rs.getString("oum_disability_percent"));
						users.setWidowYesNo(rs.getString("oum_widowYesNo"));
						users.setDesWidowCertNo(rs.getString("oum_widowCertNo"));
						users.setWidowIssueDate(rs.getString("oum_widowIssueDate"));
						users.setWidowIssueAuth(rs.getString("oum_widowIssueAuth"));
						users.setWidowDistrict(rs.getString("oum_widowIssueDist"));
						users.setWidowOtherDistrict(rs.getString("oum_widowIssueDistOther"));
						users.setWidowSubDivision(rs.getString("oum_widowSubDivision"));
						//candidateBean.setIsWidowCheckbox(rs.getString("oum_widowCheckbox"));
					} catch (SQLException e) {
						logger.error(e.getMessage());
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
					return users;
				}
			}, new Object[] { ENC_KEY, ENC_KEY, ENC_KEY, ENC_KEY, ENC_KEY, ENC_KEY, username });
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		if (lstUsers.size() > 0)
			return lstUsers.get(0);
		else
			return null;
	}
	
	
	@Override
	public String getStatusForUsername(String username) {
		String status = null;
		try {
			status = (String) getJdbcTemplate().queryForObject(UserQueries.GET_STATUS_FOR_USER, String.class, new Object[] { username });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public boolean checkForgotPassword(String userId) throws Exception {
		String result = null;
		boolean status = false;
		try {
			result = (String) getJdbcTemplate().queryForObject(UserQueries.CHECK_FORGOT_PASSWORD_FLAG_EXISTS, String.class, new Object[] { userId });
			if (result != null && result.equals("true"))
				status = true;
		} catch (Exception e) {
			status = false;
			e.printStackTrace();
		}
		return status;
	}

	public int changePassword(RegistrationBean registrationBean) throws Exception {
		int count = 0;
		try {
			count = writeJdbcTemplate.update(UserQueries.CHANGE_PASSWORD, new Object[] { registrationBean.getNewPwd(), registrationBean.getUserId().trim().toUpperCase() });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return count;
	}

	@Override
	public String checkForgotPasswordKey(String Userid) throws Exception {
		String result = null;
		try {
			result = (String) getJdbcTemplate().queryForObject(UserQueries.GET_FORGOT_PASSWORD_KEY, String.class, new Object[] { Userid });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean oldPassword(RegistrationBean registrationBean) throws Exception {
		boolean count = false;
		String result = null;
		try {
			result = (String) getJdbcTemplate().queryForObject(UserQueries.GET_OLD_PASSWORD, String.class, new Object[] { registrationBean.getUserId().trim().toUpperCase() });
			if (result != null && result.equals(registrationBean.getNewPassword()))
				count = true;
		} catch (Exception e) {
			count = false;
		}
		return count;
	}

	public int updateUserStatus(LoginBean loginBean) throws GenericException {
		int updateUserStatus = 0;
		try {
			updateUserStatus = writeJdbcTemplate.update(UserQueries.UPDATE_USER_STATUS,
					new Object[] { loginBean.getActiveStatus(), loginBean.getInvalidCount(), loginBean.getUsername().trim() });
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return updateUserStatus;
	}

	@Override
	public String getFirstTimeLoggedUser(Long userFk) {
		String beanObject = null;
		try {
			beanObject = (String) getJdbcTemplate().queryForObject(UserQueries.GET_FIRST_LOGGED_FLAG, String.class, new Object[] { userFk });
		} catch (Exception e) {
			beanObject = null;
		}
		return beanObject;
	}

	@Override
	public Users getUserFromEmail(String emailAddress) throws Exception {
		List<Users> lstUsers = null;

		lstUsers = getJdbcTemplate().query(UserQueries.GET_USER_FROM_EMAIL_ENC1, new RowMapper<Users>() {
			public Users mapRow(ResultSet rs, int rowCount) throws SQLException {
				Users users = new Users();
				try {
					users.setInvalidCount(rs.getInt("invalid_count"));
					users.setUserFk(rs.getLong("ocd_user_fk"));
					users.setUserId(rs.getLong("oum_user_pk"));
					users.setUsername(rs.getString("oum_user_id"));
					users.setPassword(rs.getString("oum_password"));
					users.setFirstLogin(rs.getString("oum_first_login"));
					users.setIpin(rs.getString("oum_ipin"));
					users.setUserType(rs.getString("oum_user_type"));
					users.setStage(rs.getDouble("oum_stage_fk") + "");
					users.setDisciplineID(rs.getString("oum_test_fk"));
					users.setDeclaration(rs.getString("declaration_checkbox_status"));
					users.setStatusID(rs.getInt("ocd_status_id_fk"));
					users.setCandidateStatusId(rs.getInt("ocd_status_id_fk"));
					users.setFirstName(rs.getString("oum_name"));
					if (rs.getString("ocd_user_fk") != null) {
						users.setUserPk(Long.parseLong(rs.getString("ocd_user_fk")));
					}
					users.setRoleFK(rs.getString("oum_role_fk"));
					if (StringUtils.isNotBlank(rs.getString("ocd_date_of_birth"))) {
						users.setDate_Of_Birth(rs.getString("ocd_date_of_birth"));
					}
					if (StringUtils.isNotBlank(rs.getString("ocd_discipline"))) {
						users.setDiscipline(rs.getString("ocd_discipline"));
					}
					users.setNationality(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(rs.getString("ocd_nationality"))));
					users.setInitials(rs.getString("oum_initial"));
					users.setGender(rs.getString("ocd_genderfk"));
					users.setEmailAddress(rs.getString("oum_email_id"));
					users.setMobile(rs.getString("oum_mobile_no"));
					users.setCandidateFirstName(rs.getString("oum_first_name"));
					users.setCandidateMiddleName(rs.getString("oum_middle_name"));
					users.setCandidateLastName(rs.getString("oum_last_name"));

				} catch (Exception e) {
					logger.fatal(e, e);
				}
				return users;
			}
		}, new Object[] { ENC_KEY, ENC_KEY, ENC_KEY, ENC_KEY, ENC_KEY, ENC_KEY, emailAddress });

		if (lstUsers.size() > 0)
			return lstUsers.get(0);
		else
			return null;
	}

	public int updateUserStatus(SessionDetailsBean sessionDetailsBean) throws GenericException {
		int updateUserStatus = 0;
		try {
			updateUserStatus = writeJdbcTemplate.update(UserQueries.UPDATE_USER_STATUS,
					new Object[] { sessionDetailsBean.getOUM_STATUS(), sessionDetailsBean.getOUM_INVAL_ATTEMPT_COUNT(), sessionDetailsBean.getOUM_USER_ID().trim() });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return updateUserStatus;
	}
}