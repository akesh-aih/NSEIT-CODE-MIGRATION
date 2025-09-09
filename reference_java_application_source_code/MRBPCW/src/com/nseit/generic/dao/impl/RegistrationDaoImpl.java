package com.nseit.generic.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.nseit.generic.dao.BaseDAO;
import com.nseit.generic.dao.RegistraionDAO;
import com.nseit.generic.models.RegistrationBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.queries.AgencyQueries;
import com.nseit.generic.queries.RegistrationQueries;
import com.nseit.generic.queries.UserQueries;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.DESEncrypter;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.LoggerHome;

public class RegistrationDaoImpl extends BaseDAO implements RegistraionDAO {

	private String ENC_KEY = ConfigurationConstants.getInstance().getENC_KEY();
	
	private JdbcTemplate writeJdbcTemplate;

	public void setWriteJdbcTemplate(JdbcTemplate writeJdbcTemplate) {
		this.writeJdbcTemplate = writeJdbcTemplate;
	}

	private Logger logger = LoggerHome.getLogger(getClass());

	public int forgotPassword(RegistrationBean registrationBean) throws Exception {
		int count = 0;
		count = writeJdbcTemplate.update(RegistrationQueries.FORGOT_PASSWORD, new Object[] {
				DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).encrypt(registrationBean.getNewPwd()), registrationBean.getUserId().trim().toUpperCase() });
		return count;
	}

	public int updatePasswordDetails(Users users, RegistrationBean registrationBean) throws Exception {
		int updatePassword = 0;
		try {
			updatePassword = writeJdbcTemplate.update(RegistrationQueries.UPDATE_USER_PASSWORD,
					new Object[] { registrationBean.getNewPassword(), users.getUserId(), registrationBean.getOldPassword() });
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
		return updatePassword;
	}

	public int updatePasswordForFirstTime(RegistrationBean registrationBean) throws Exception {
		int updatePassword = 0;
		try {
			updatePassword = writeJdbcTemplate.update(RegistrationQueries.UPDATE_USER_PASSWORD_FOR_FIRST_TIME,
					new Object[] { registrationBean.getNewPassword(), "Y", registrationBean.getUserName(), registrationBean.getOldPassword() });
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
		return updatePassword;
	}

	public int insertUserDetails(RegistrationBean registrationBean) throws Exception {
		String encryptPassword = null;
		int updateCount = 0;
		try {
			String age = registrationBean.getAgeInYears() + " years " + registrationBean.getAgeInMonths() + " months "
					+ registrationBean.getAgeInDays() + " days";

			String prefixVal = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.USER_ID_PREFIX);

			String noOfDigitsForUserId = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.NO_OF_DIGITS_FOR_MAX_USER_ID);
			String regSeqId = null;

			regSeqId = writeJdbcTemplate.queryForObject(RegistrationQueries.NOOFDIGITSRANDOM_SEQUENCE, String.class, new Object[] {});
			noOfDigitsForUserId = StringUtils.leftPad(regSeqId, 6, '0');
			Integer userIdExist = (Integer) getJdbcTemplate().queryForObject(RegistrationQueries.USER_ID_EXIST, Integer.class, new Object[] { prefixVal, noOfDigitsForUserId });

			if (userIdExist != 0) {
				regSeqId = writeJdbcTemplate.queryForObject(RegistrationQueries.NOOFDIGITSRANDOM_SEQUENCE, String.class, new Object[] {});
				noOfDigitsForUserId = StringUtils.leftPad(regSeqId, 6, '0');
			}
			registrationBean.setUserName(prefixVal+noOfDigitsForUserId);
			
			if (StringUtils.isNotBlank(registrationBean.getNewPassword())) {
				encryptPassword = DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).encrypt(registrationBean.getNewPassword());
			}
			registrationBean.setNewPassword(encryptPassword);

			updateCount = writeJdbcTemplate.update(RegistrationQueries.INSERT_USER_DETAILS_ENC1, (PreparedStatementSetter) pStatement -> {
					pStatement.setString(1, registrationBean.getUserName());
					pStatement.setString(2, registrationBean.getMobileNo());
					pStatement.setString(3, ENC_KEY);
					pStatement.setString(4, registrationBean.getEmailAddress().toLowerCase());
					pStatement.setString(5, ENC_KEY);
					pStatement.setString(6, registrationBean.getCandidateName());
					pStatement.setString(7, ENC_KEY);
					pStatement.setString(8, registrationBean.getInitials());
					pStatement.setString(9, ENC_KEY);
					pStatement.setString(10, registrationBean.getNationVal());
					pStatement.setString(11, "0");
					pStatement.setInt(12, 1);
					pStatement.setString(13, registrationBean.getNewPassword());
					pStatement.setString(14, registrationBean.getIpin());
					pStatement.setString(15, "Y");
					pStatement.setString(16, "C");
					pStatement.setInt(17, Integer.parseInt(ConfigurationConstants.getInstance().getStageKey(GenericConstants.STAGE_LOGIN_GENERATED) + ""));
					pStatement.setInt(18, 1);
					pStatement.setString(19, registrationBean.getUserName());
					pStatement.setString(20, registrationBean.getExServiceMen());
					pStatement.setString(21, registrationBean.getDischargeDate());
					pStatement.setString(22, registrationBean.getPpoNumber());
					pStatement.setString(23, registrationBean.getCommCertYesNo());
					pStatement.setString(24, registrationBean.getCommCertYesNo().equals("6")?registrationBean.getCommunity():"7");
					pStatement.setString(25, registrationBean.getSubCaste());
					pStatement.setString(26, registrationBean.getIssueAuthCommCert());
					pStatement.setString(27, registrationBean.getCommCertNo());
					pStatement.setString(28, registrationBean.getCommCertPlace());
					pStatement.setString(29, registrationBean.getCommIssueDate());
					pStatement.setString(30, registrationBean.getDisableYesNo());
					pStatement.setString(31, registrationBean.getDisableType());
					pStatement.setString(32, registrationBean.getDisablityPercent());
					pStatement.setString(33, registrationBean.getDateOfBirth());
					pStatement.setString(34, age);
					pStatement.setString(35, registrationBean.getMobileNo());
					pStatement.setString(36, registrationBean.getEmailAddress().toLowerCase());
					pStatement.setInt(37, registrationBean.isDiffAbledChkBox()?6:7);
					pStatement.setString(38, registrationBean.getWidowYesNo());
					pStatement.setString(39, registrationBean.getDesWidowCertNo());
					pStatement.setString(40, registrationBean.getWidowIssueDate());
					pStatement.setString(41, registrationBean.getWidowIssueAuth());
					pStatement.setString(42, registrationBean.getWidowDistrict());
					pStatement.setString(43, registrationBean.getWidowOtherDistrict());
					pStatement.setString(44, registrationBean.getWidowSubDivision());
					pStatement.setInt(45, registrationBean.isWidowCheckbox()?6:7);
					pStatement.setString(46, registrationBean.getGender());
			});
		} catch (Exception e) {
			logger.error(e,e);
		}
		return updateCount;
	}
	
	@Override
	public String getUserPkForUser(String username) {
		String status = null;
		try {
			status = (String) getJdbcTemplate().queryForObject(UserQueries.GET_USER_PK_FOR_USER, String.class, new Object[] { username });
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return status;
	}

	public boolean checkIfUserIdExists(RegistrationBean registrationBean) {
		String username = null;
		int recordCount = 0;
		if (registrationBean.getUserName() != null) {
			username = registrationBean.getUserName().toUpperCase();
		}
		recordCount = getJdbcTemplate().queryForObject(UserQueries.CHECK_IF_USER_EXISTS1, Integer.class, new Object[] { username });
		return recordCount != 0;
	}

	@Override
	public boolean checkIfUserIdEmailIDExists(RegistrationBean registrationBean) {
		String username = null;
		int recordCount = 0;
		String Email = registrationBean.getEmailAddress();
		if (registrationBean.getUserName() != null) {
			username = registrationBean.getUserName().toUpperCase();
		}
		recordCount = getJdbcTemplate().queryForObject(UserQueries.CHECK_IF_USER_EMAIL_EXIST, Integer.class,
				new Object[] { username == null ? null : username, Email == null ? null : Email });
		return recordCount != 0;
	}

	public RegistrationBean generateUserID(RegistrationBean registrationBean) {

		List<RegistrationBean> getRegistrationDetailsList = null;

		getRegistrationDetailsList = getJdbcTemplate().query(RegistrationQueries.SELECT_GENERATED_USER_ID, new RowMapper<RegistrationBean>() {
			public RegistrationBean mapRow(ResultSet rs, int rowCount) throws SQLException {
				RegistrationBean registrationBean = new RegistrationBean();
				registrationBean.setUserName(rs.getString("OUM_USER_ID"));
				registrationBean.setUserId(rs.getString("OUM_USER_PK"));
				return registrationBean;
			}
		}, new Object[] { ENC_KEY, registrationBean.getMobileNo(), ENC_KEY, registrationBean.getEmailAddress(), 1 });
		if (getRegistrationDetailsList != null && !getRegistrationDetailsList.isEmpty()) {
			return getRegistrationDetailsList.get(0);
		}
		return null;
	}

	@Override
	public String getMobileNoInUserMater(String userid) {
		String mobile = (String) getJdbcTemplate().queryForObject(RegistrationQueries.GET_MOBILE_IN_USER_MASTER, String.class, new Object[] { userid });
		return mobile;
	}

	@Override
	public String checkOTP(RegistrationBean registrationBean) throws Exception {
		String otp = null;
		try {
			otp = (String) getJdbcTemplate().queryForObject(UserQueries.CHECK_IF_OTP_FOR_EMAIL_EXISTS, String.class, new Object[] { registrationBean.getEmailAddress() });
		} catch (Exception e) {
			otp = null;
		}
		return otp;
	}

	@Override
	public String getOTPInEmailOtpMaster(RegistrationBean registrationBean, String otpflag) {
		String otp = null;
		if (otpflag.equalsIgnoreCase("mobile")) {
			otp = (String) getJdbcTemplate().queryForObject(UserQueries.CHECK_IF_OTP_FOR_EMAIL_EXISTS, String.class, new Object[] { registrationBean.getMobileNo() });
		} else if (otpflag.equalsIgnoreCase("email")) {
			otp = (String) getJdbcTemplate().queryForObject(UserQueries.CHECK_IF_OTP_FOR_EMAIL_EXISTS, String.class, new Object[] { registrationBean.getEmailAddress() });
		} else {
			otp = (String) getJdbcTemplate().queryForObject(UserQueries.CHECK_IF_OTP_FOR_EMAIL_EXISTS, String.class, new Object[] { registrationBean.getEmailAddress() });
		}
		return otp;
	}

	@Override
	public boolean checkUserExist(RegistrationBean registrationBean, String flag) throws Exception {
		boolean otpFlag = false;
		int otpEmail = 0;
		int otpMob = 0;
		try {
			if (flag.equals("email")) {
				otpEmail = (int) getJdbcTemplate().queryForObject(UserQueries.CHECK_IF_USER_FOR_EMAIL_AND_POST_EXISTS_ENC1, Integer.class,
						new Object[] { ENC_KEY, registrationBean.getEmailAddress().toLowerCase(), Integer.parseInt(registrationBean.getCourses()) });
				if (otpEmail != 0) {
					otpFlag = true;
					return otpFlag;
				} else {
					otpFlag = false;
				}
			} else if (flag.equals("mobile")) {
				otpMob = (int) getJdbcTemplate().queryForObject(UserQueries.CHECK_IF_USER_FOR_MOBILE_AND_POST_EXISTS_ENC1, Integer.class,
						new Object[] { ENC_KEY, registrationBean.getMobileNo(), Integer.parseInt(registrationBean.getCourses()) });
				if (otpMob != 0) {
					otpFlag = true;
					return otpFlag;
				} else {
					otpFlag = false;
				}
			}
		} catch (Exception e) {
			otpFlag = false;
		}
		return otpFlag;
	}

	public boolean checkOTP(RegistrationBean registrationBean, String otpflag) {
		boolean otpFlag = false;
		String otpEmail = null;
		String otpMob = null;
		try {
			if (otpflag.equals("email")) {
				otpEmail = (String) getJdbcTemplate().queryForObject(RegistrationQueries.CHECK_IF_OTP_FOR_EMAIL_EXISTS, String.class,
						new Object[] { registrationBean.getEmailAddress(), Integer.parseInt(registrationBean.getCourses()) });
				if (otpEmail != null && otpEmail.equals(registrationBean.getEmailotp())) {
					otpFlag = true;
					return otpFlag;
				} else {
					otpFlag = false;
				}
			} else if (otpflag.equals("mobile")) {
				otpMob = (String) getJdbcTemplate().queryForObject(RegistrationQueries.CHECK_IF_OTP_FOR_MOBILE_EXISTS, String.class,
						new Object[] { registrationBean.getMobileNo(), Integer.parseInt(registrationBean.getCourses()) });
				if (otpMob != null && otpMob.equals(registrationBean.getMobotp())) {
					otpFlag = true;
					return otpFlag;
				} else {
					otpFlag = false;
				}
			}
		} catch (Exception e) {
			otpFlag = false;
		}
		return otpFlag;
	}

	@Override
	public boolean checkPostExist(RegistrationBean registrationBean) {
		int emailCount = 0;
		emailCount = getJdbcTemplate().queryForObject(RegistrationQueries.CHECK_IF_POST_EXISTS, Integer.class,
				new Object[] { registrationBean.getEmailAddress().toLowerCase(), Integer.parseInt(registrationBean.getPostApplied()) });
		return emailCount != 0;
	}

	@Override
	public Map<String, String> getAgeMatrixDetails(String post_selected) throws Exception {
		Map<String, String> ageMatrixList = null;
		try {
			ageMatrixList = (Map<String, String>) getJdbcTemplate().query(UserQueries.GET_AGE_MATRIX, new ResultSetExtractor<Map<String, String>>() {
				@Override
				public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, String> ageMatrixListMap = new LinkedHashMap<String, String>();
					while (rs.next()) {
						ageMatrixListMap.put("oar_max_age", rs.getString("oar_max_age"));
						ageMatrixListMap.put("START_DATE", rs.getString("START_DATE"));
						ageMatrixListMap.put("END_DATE", rs.getString("END_DATE"));
					}
					return ageMatrixListMap;
				}
			}, new Object[] { post_selected });
		} catch (Exception e) {
			logger.fatal(e, e);
			throw new GenericException(e);
		}
		return ageMatrixList;
	}

	@Override
	public Long getRandomSequence() throws Exception {
		long genNum = 0;
		try {
			genNum = (Long) writeJdbcTemplate.queryForObject(RegistrationQueries.NOOFDIGITSRANDOM_SEQUENCE, Long.class, new Object[] {});
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return genNum;
	}

	@Override
	public RegistrationBean checkMobNoOTP(RegistrationBean registrationBean) throws GenericException {
		RegistrationBean regBean = null;
		try {
			regBean = (RegistrationBean) getJdbcTemplate().query(RegistrationQueries.CHECK_IF_MOB_FOR_OTP_EXISTS, new ResultSetExtractor<Object>() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					RegistrationBean regBean1 = null;
					while (rs.next()) {
						regBean1 = new RegistrationBean();
						regBean1.setOtpSate(rs.getString("omom_status"));
						regBean1.setOtpLockCount(rs.getInt("send_otp_count"));
					}
					return regBean1;
				}
			}, new Object[] { registrationBean.getMobileNo() });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return regBean;
	}

	@Override
	public int updateMOBOTPDetails(String mobileNo1, String otp, boolean flag) throws Exception { // , String courseVal
		try {
			String toAddressStr = mobileNo1;
			toAddressStr = toAddressStr.substring(1, toAddressStr.length() - 1);
			if (flag)
				return writeJdbcTemplate.update(RegistrationQueries.UPDATE_MOB_OTP_DETAIL1, new Object[] { otp, mobileNo1 });
			else
				return writeJdbcTemplate.update(RegistrationQueries.UPDATE_MOB_OTP_DETAIL, new Object[] { otp, mobileNo1 });
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	@Override
	public int insertMOBDetails(String mobileNo1, String otp) throws Exception { // , String courseVal
		try {
			String toAddressStr = mobileNo1;
			// int course = Integer.parseInt(courseVal);
			toAddressStr = toAddressStr.substring(1, toAddressStr.length() - 1);
			return writeJdbcTemplate.update(RegistrationQueries.INSERT_MOB_OTP_DETAIL, new Object[] { mobileNo1, otp, "send" }); // , course
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

	@Override
	public String checkIfUserDetailsExist(RegistrationBean registrationBean) {
		String mobOrEmailFound = null;
		/*List<String> userDetails = new ArrayList<String>();
		String otpFlagE = "false";
		String otpFlagM = "false";
		int otpEmail = 0;
		int otpMob = 0;*/
		try {

			/*mobOrEmailFound = getJdbcTemplate().queryForObject(UserQueries.CHECK_IF_USER_FOR_MOBILE_AND_POST_EX, String.class, new Object[] 
					   { ENC_KEY, registrationBean.getMobileNo(),ENC_KEY , registrationBean.getEmailAddress(),1 });*/
			
			mobOrEmailFound = getJdbcTemplate().queryForObject(UserQueries.CHECK_IF_USER_FOR_MOBILE_AND_POST_EX, String.class, new Object[] 
					   { registrationBean.getMobileNo(),registrationBean.getEmailAddress(),1 });
			
			/*if (otpEmail != 0) {
				userDetails.add(otpFlagE = "Email_Found");
			} else {
				userDetails.add(otpFlagE);
			}*/

			/*otpMob = getJdbcTemplate().query(UserQueries.CHECK_IF_USER_FOR_MOBILE_AND_POST_EXISTS_ENC1, (PreparedStatementSetter) preparedStatement -> {
				preparedStatement.setString(1, ENC_KEY);
				preparedStatement.setString(2, registrationBean.getMobileNo());
				preparedStatement.setInt(3, 1);
			}, (ResultSetExtractor<Integer>) resultSet -> {
				if (resultSet.next()) {
					return resultSet.getInt(1);
				}
				return null;
			});*/

			/*if (otpMob != 0) {
				userDetails.add(otpFlagM = "Mobile_Found");
			} else {
				userDetails.add(otpFlagM);
			}*/
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return mobOrEmailFound;

	}
	
	@Override
	public int checkIfMobOrEmailIsAlreadyVerified(String mobileNo , String emailAddress) {
		int count = 0;
		try {
			count = getJdbcTemplate().queryForObject(RegistrationQueries.CHECK_IF_SMS_EMAIL_IS_VERIFIED, Integer.class,
					new Object[] { mobileNo , emailAddress });
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return count;
		
	
	}

	@Override
	public void updateOtpStatus(RegistrationBean registrationBean) {
		writeJdbcTemplate.update(RegistrationQueries.UPDATE_MOBILE_OTP_DETAIL,
				new Object[] { "verified", registrationBean.getUserName(), registrationBean.getMobileNo(), registrationBean.getEmailAddress() });
	}

	@Override
	public int verifyEmailExists(String emailAddress) {
		int count = 0;
		try {
			count = getJdbcTemplate().query(RegistrationQueries.CHECK_IF_EMAIL_EXISTS_ENC1, (PreparedStatementSetter) preparedStatement -> {
				preparedStatement.setString(1, emailAddress);
				preparedStatement.setString(2, ENC_KEY);
			}, (ResultSetExtractor<Integer>) resultSet -> {
				if (resultSet.next()) {
					return resultSet.getInt(1);
				}
				return null;
			});

		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return count;
	}

	@Override
	public boolean verifyOtp(String enteredOtp, String mobileNo) {
		try {
			int updateCount = writeJdbcTemplate.update(RegistrationQueries.UPDATE_VERIFIED_COUNT, new Object[] { mobileNo });

			if (updateCount > 0) {
				Map<String, Object> newMap = getJdbcTemplate().queryForMap(RegistrationQueries.VERIFY_OTP, new Object[] { enteredOtp, mobileNo });
				return getElapsedTime((Timestamp) newMap.get("otp_time"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return true;
		}
		return false;
	}

	private boolean getElapsedTime(Timestamp loggedInTime) {
		Calendar currentTime = Calendar.getInstance();
		Calendar timeStampTime = Calendar.getInstance();
		timeStampTime.setTimeInMillis(loggedInTime.getTime());

		long elapsedTime = currentTime.getTimeInMillis() - timeStampTime.getTimeInMillis();
		long elapsedMinutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
		if (elapsedMinutes >= 15) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int checkAttemptCount(String mobileNo) {
		return getJdbcTemplate().queryForObject(RegistrationQueries.SELECT_MAX_TRY, Integer.class, new Object[] { mobileNo });
	}

	@Override
	public Long getSequence1() throws Exception {
		long genNum = 0;
		try {
			genNum = (Long) writeJdbcTemplate.queryForObject(RegistrationQueries.SEQ_1, Long.class, new Object[] {});
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return genNum;
	}

	@Override
	public int checkEmailID(RegistrationBean registrationBean) throws Exception {
		int emailCount = 0;

		try {
			emailCount = getJdbcTemplate().queryForObject(RegistrationQueries.CHECK_IF_EMAIL_EXISTS, Integer.class,
					new Object[] { registrationBean.getEmailAddress().toLowerCase().trim() });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return emailCount;
	}

	@Override
	public String checkEmailOTP(RegistrationBean registrationBean) throws Exception {
		String status = null;
		try {
			status = (String) getJdbcTemplate().query(RegistrationQueries.CHECK_IF_MOB_FOR_OTP_EXISTS, new ResultSetExtractor<String>() {
				@Override
				public String extractData(ResultSet rs) throws SQLException, DataAccessException {
					String status1 = null;
					while (rs.next()) {
						status1 = rs.getString(1);
					}
					return status1;
				}
			}, new Object[] { registrationBean.getEmailAddress().toLowerCase() });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return status;
	}

	@Override
	public int insertOTPDetails(String email, String otp, int pk) throws Exception {
		try {
			String toAddressStr = email;
			toAddressStr = toAddressStr.substring(1, toAddressStr.length() - 1);

			return writeJdbcTemplate.update(RegistrationQueries.INSERT_MOB_OTP_DETAIL, new Object[] { email, otp, "send" });
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	@Override
	public int updateOTPDetails(String email, String otp, boolean flag) throws Exception {
		try {
			String toAddressStr = email;
			toAddressStr = toAddressStr.substring(1, toAddressStr.length() - 1);
			if (flag)
				return writeJdbcTemplate.update(RegistrationQueries.UPDATE_MOB_OTP_DETAIL1, new Object[] { otp, email });
			else
				return writeJdbcTemplate.update(RegistrationQueries.UPDATE_MOB_OTP_DETAIL, new Object[] { otp, email });
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	@Override
	public String getExistingOTP(String email) throws Exception {
		String otp = "";
		try {
			otp = (String) getJdbcTemplate().queryForObject(UserQueries.CHECK_OTP_EXPIRY, String.class, new Object[] { email });
		} catch (Exception ex) {
			otp = "";
			throw new Exception(ex);
		}
		return otp;
	}

	public String getUserIdexistDtls(RegistrationBean registrationBean, String choice) throws Exception {
		logger.info("Method call Started " + this.getClass() + " :getUserIdDtlsForforgotUserId  ");
		String userId = null;
		if ("email".equals(choice)) {
			boolean isEmailExist = false;
			isEmailExist = checkEmialID(registrationBean);
			if (isEmailExist) {
				userId = getJdbcTemplate().query(RegistrationQueries.GET_FORGOT_USER_ID_DTLS_EXIST_EMAIL_ENC1, (PreparedStatementSetter) preparedStatement -> {
					preparedStatement.setString(1, registrationBean.getEmailAddress().toLowerCase());
				}, (ResultSetExtractor<String>) resultSet -> {
					if (resultSet.next()) {
						return resultSet.getString(1);
					}
					return null;
				});
			} else {
				userId = null;
			}
		}
		if ("mobile".equals(choice)) {
			boolean isMobileExist = false;
			isMobileExist = checkMobileNo(registrationBean);
			if (isMobileExist) {
				userId = getJdbcTemplate().query(RegistrationQueries.GET_FORGOT_USER_ID_DTLS_EXIST_MOB_ENC1, (PreparedStatementSetter) preparedStatement -> {
					preparedStatement.setString(1, registrationBean.getMobileNo());
				}, (ResultSetExtractor<String>) resultSet -> {
					if (resultSet.next()) {
						return resultSet.getString(1);
					}
					return null;
				});
			} else {
				userId = null;
			}
		}
		return userId;
	}

	@Override
	public int updateSentCount(String username) {
		int count = 0;
		try {
			count = writeJdbcTemplate.update(UserQueries.UPDATE_ATTEMPT_COUNT, new Object[] { username });
		} catch (Exception e) {
			logger.info(e.getMessage());
			return 0;
		}
		return count;
	}

	@Override
	public boolean checkEmialID(RegistrationBean registrationBean) {
		int emailCount = 0;
		try {
			emailCount = getJdbcTemplate().query(UserQueries.CHECK_IF_EMAIL_EXISTS_ENC1, (PreparedStatementSetter) preparedStatement -> {
				preparedStatement.setString(1, registrationBean.getEmailAddress().toLowerCase());
			}, (ResultSetExtractor<Integer>) resultSet -> {
				if (resultSet.next()) {
					return resultSet.getInt(1);
				}
				return null;
			});
		} catch (Exception e) {
			logger.fatal("Exception in checkEmialID" + e);
		}
		return emailCount != 0;
	}

	@Override
	public boolean checkMobileNo(RegistrationBean registrationBean) {
		int mobileCount = 0;
		try {
			mobileCount = getJdbcTemplate().query(UserQueries.CHECK_IF_MOBILE_EXISTS_ENC1, (PreparedStatementSetter) preparedStatement -> {
				preparedStatement.setString(1, registrationBean.getMobileNo());
			}, (ResultSetExtractor<Integer>) resultSet -> {
				if (resultSet.next()) {
					return resultSet.getInt(1);
				}
				return null;
			});
		} catch (Exception e) {
			logger.fatal("Exception in checkMobileNo" + e);
		}
		return mobileCount != 0;
	}

	public String getUserFK(RegistrationBean registrationBean) throws Exception {
		List<RegistrationBean> getRegistrationDetailsList = null;
		String userFk = null;

		getRegistrationDetailsList = getJdbcTemplate().query(UserQueries.CHECK_IF_USER_EXISTS, new RowMapper<RegistrationBean>() {
			public RegistrationBean mapRow(ResultSet rs, int rowCount) throws SQLException {
				RegistrationBean registrationBean = new RegistrationBean();
				registrationBean.setUserId(rs.getString("oum_user_pk"));
				return registrationBean;
			}
		}, new Object[] { registrationBean.getUserId().trim().toUpperCase() });

		if (getRegistrationDetailsList != null && !getRegistrationDetailsList.isEmpty()) {
			userFk = getRegistrationDetailsList.get(0).getUserId();
		}
		return userFk;
	}

	@Override
	public boolean checkFpOTP(RegistrationBean regBean, String otpflag) {
		boolean otpFlag = false;
		String otpMob = null;
		try {
			if (otpflag.equals("mobile")) {
				otpMob = (String) getJdbcTemplate().queryForObject(RegistrationQueries.CHECK_IF_FGT_PASS_OTP_FOR_MOBILE_EXISTS, String.class,
						new Object[] { regBean.getMobileNo(), regBean.getUserName() });
				if (otpMob != null && otpMob.equals(regBean.getFpOtp())) {
					otpFlag = true;
					return otpFlag;
				} else {
					otpFlag = false;
				}
			}
		} catch (Exception e) {
			otpFlag = false;
		}
		return otpFlag;
	}

	@Override
	public boolean checkNewOldPasswordMatch(RegistrationBean registrationBean) throws Exception {
		boolean count = false;
		String result = null;
		try {
			String newPassEncrypted = DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).encrypt(registrationBean.getNewPwd());
			result = (String) getJdbcTemplate().queryForObject(UserQueries.GET_OLD_PASSWORD, String.class, new Object[] { registrationBean.getUserId().trim().toUpperCase() });
			if (result != null && result.equals(newPassEncrypted))
				count = true;
		} catch (Exception e) {
			count = false;
		}
		return count;
	}

	@Override
	public boolean checkFpMobNoOTP(String mobileNo, String username) throws Exception {
		String result = "";
		boolean status = false;
		try {
			result = (String) getJdbcTemplate().queryForObject(RegistrationQueries.CHECK_IF_MOB_FOR_FGT_PASS_OTP_EXISTS, String.class, new Object[] { mobileNo, username });
			if (result.equals("true"))
				status = true;
			else if (result.equals("false"))
				status = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public int updateFpMobOTPDetails(String mobileNo1, String otp) throws Exception {
		try {
			String toAddressStr = mobileNo1;
			toAddressStr = toAddressStr.substring(1, toAddressStr.length() - 1);
			return writeJdbcTemplate.update(RegistrationQueries.UPDATE_FGT_PASS_MOB_OTP_DETAIL, new Object[] { otp, mobileNo1 });
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	@Override
	public int insertFpMobDetails(String mobileNo1, String otp, String userID) throws Exception {
		try {
			return writeJdbcTemplate.update(RegistrationQueries.INSERT_FGT_PASS_MOB_OTP_DETAIL, new Object[] { mobileNo1, otp, userID });
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	@Override
	public boolean checkIfUserIdMobileNoExists(RegistrationBean registrationBean) {
		logger.debug("Method call Started " + this.getClass() + " :CHECK_IF_USER_MOBILE_EXIST  ");

		String result = "";
		boolean status = false;
		String mobileNo = registrationBean.getMobileNo();
		String userId = registrationBean.getUserName();
		
		try {
			result = (String) getJdbcTemplate().queryForObject(UserQueries.CHECK_IF_USER_MOBILE_EXIST_ENC1, String.class, new Object[] { mobileNo, userId });
			if (result.equals("true"))
				status = true;
			else if (result.equals("false"))
				status = false;
		} catch (Exception e) {
			logger.fatal("Exception in checkIfUserIdMobileNoExists" + e);
		}
		return status;
	}
	
	@Override
	public boolean checkIfEmailExistsForUserId(RegistrationBean registrationBean) {
		logger.debug("Method call Started " + this.getClass() + " :CHECK_IF_EMAIL_EXIST_FOR_USER  ");

		String result = "";
		boolean status = false;
		String emailId = registrationBean.getEmailId().toLowerCase();
		String userId = registrationBean.getUserName();
		
		try {
			result = (String) getJdbcTemplate().queryForObject(UserQueries.CHECK_IF_EMAIL_EXIST_FOR_USER, String.class, new Object[] { emailId, userId });
			if (result.equals("true"))
				status = true;
			else if (result.equals("false"))
				status = false;
		} catch (Exception e) {
			logger.fatal("Exception in checkIfEmailExistsForUserId" + e);
		}
		return status;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	@Override
	public Map<String, String> getAgeMatrixDetails(int post_selected,String exserviceman,String disability,int category,String desti) throws Exception {
		Map<String, String> ageMatrixList = null;
		try
		{
			ageMatrixList = (Map<String, String>) getJdbcTemplate().query(UserQueries.GET_AGE_MATRIX,new Object[]{
					//post_selected,
					exserviceman,
					disability,
					category,desti
			}, 
			new ResultSetExtractor()
			{
			
			@Override
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException{
				Map<String, String> ageMatrixListMap = new LinkedHashMap<String, String>();
	
				while(rs.next()){
					ageMatrixListMap.put("START_DATE", rs.getString("START_DATE"));
				}
				return ageMatrixListMap;
			}
		});
		}catch (Exception e) {
			logger.fatal(e,e);
				throw new GenericException(e);
		}
	   return ageMatrixList;
	}
	
	@Override
	public boolean checkFpEmailNoOTP(String email, String username) {
		String result = "";
		boolean status = false;
		try {
			result = (String) getJdbcTemplate().queryForObject(RegistrationQueries.CHECK_IF_EMAIL_FOR_FGT_PASS_OTP_EXISTS, String.class, new Object[] { email, username });
			if (result.equals("true"))
				status = true;
			else if (result.equals("false"))
				status = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public int updateFpEmailOTPDetails(String email1, String otp) throws Exception{
		try {
			String toAddressStr = email1;
			toAddressStr = toAddressStr.substring(1, toAddressStr.length() - 1);
			return writeJdbcTemplate.update(RegistrationQueries.UPDATE_FGT_PASS_EMAIL_OTP_DETAIL, new Object[] { otp, email1 });
		}  catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	@Override
	public int insertFpEmailDetails(String email1, String otp, String username) throws Exception{
		try {
			return writeJdbcTemplate.update(RegistrationQueries.INSERT_FGT_PASS_EMAIL_OTP_DETAIL, new Object[] { email1, otp, username });
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}
}