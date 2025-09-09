/**
 * 
 */
package com.nseit.generic.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.RowMapper;

import com.nseit.generic.dao.BaseDAO;
import com.nseit.generic.dao.CandidateMgmtDAO;
import com.nseit.generic.models.CandidateApproveRejectBean;
import com.nseit.generic.models.CandidateMgmtBean;
import com.nseit.generic.models.EnrollmentBean;
import com.nseit.generic.models.HallTicketBean;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.PaymentBean;
import com.nseit.generic.models.UserDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.queries.CandidateManagementQueries;
import com.nseit.generic.queries.CandidateQueries;
import com.nseit.generic.queries.CommonQueries;
import com.nseit.generic.queries.MasterQueries;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.ValidatorUtil;
import com.nseit.otbs.model.BatchDetailsBean;

/**
 * @author Sanjeev
 *
 */
@SuppressWarnings("unchecked")
public class CandidateMgmtDaoImpl extends BaseDAO implements CandidateMgmtDAO {
	
	private JdbcTemplate writeJdbcTemplate;
	
	public void setWriteJdbcTemplate(JdbcTemplate writeJdbcTemplate) {
		this.writeJdbcTemplate = writeJdbcTemplate;
	}
	
	private Logger logger = LoggerHome.getLogger(getClass());

	public byte[] getImageBytes() {
		return byteArrImage;
	}

	private byte[] byteArrImage = null;

	public byte[] getSignatureBytes() {
		return byteArrSignature;
	}

	private byte[] byteArrSignature = null;

	public List<CandidateMgmtBean> getCandidateHallTicket(CandidateMgmtBean candidateMgmtBean, String stage, Integer attempt) throws Exception {

		List<CandidateMgmtBean> hallticketList = null;
		try {
			hallticketList = getJdbcTemplate().query(CandidateManagementQueries.GET_CANDIDATE_HALLTICKET_DETAILS,
					new Object[] { candidateMgmtBean.getUserId().trim(), stage, attempt }, new RowMapper<CandidateMgmtBean>() {

						@Override
						public CandidateMgmtBean mapRow(ResultSet rs, int rowCount) throws SQLException {
							StringBuilder stringBuilder = new StringBuilder();
							CandidateMgmtBean candidateMgmtBean = new CandidateMgmtBean();

							candidateMgmtBean.setEnrollmentIdForHallTicket(rs.getString("oed_ENROLLMENT_PK"));
							candidateMgmtBean.setTestCenterValue(rs.getString("OTM_TEST_NAME"));
							candidateMgmtBean.setUserIdForHallticket(rs.getString("OCD_user_fk"));

							stringBuilder.append(rs.getString("OCD_CAND_TITLE")).append(" ");
							stringBuilder.append(rs.getString("OCD_FIRST_NAME")).append(" ");
							if (rs.getString("OCD_MIDDLE_NAME") != null) {
								stringBuilder.append(rs.getString("OCD_MIDDLE_NAME")).append(" ");
							}
							stringBuilder.append(rs.getString("OCD_LAST_NAME")).append(" ");

							candidateMgmtBean.setCandidateName(stringBuilder.toString());

							candidateMgmtBean.setCandidateDateOfBirth(CommonUtil.getStringDateStrForAdmin(rs.getTimestamp("OCD_date_of_birth")));
							candidateMgmtBean.setCandidateTestDate(CommonUtil.getStringDateStrForAdmin(rs.getTimestamp("obd_test_date")));
							candidateMgmtBean.setCandidateTestStartTime(rs.getString("start_time"));
							candidateMgmtBean.setCandidateTestEndTime(CommonUtil.getStringDate(rs.getTimestamp("obd_test_end_time")));
							candidateMgmtBean.setCandidateGender(rs.getString("ORVM_reference_value"));
							candidateMgmtBean.setCandidateTestCenterAddress(rs.getString("otcm_address"));
							candidateMgmtBean.setDuration(rs.getString("OTM_DURATION"));
							candidateMgmtBean.setUserIdHallTicket(rs.getString("OUM_USER_ID"));
							candidateMgmtBean.setTestCenterName(rs.getString("otcm_TEST_CENTRE_NAME"));
							candidateMgmtBean.setCandidateReportingTime(rs.getString("REPORTING_TIME"));
							candidateMgmtBean.setPassword(rs.getString("OUM_PASSWORD"));
							candidateMgmtBean.setRollNumberForHallTicket(rs.getString("OED_ROLL_NO"));
							candidateMgmtBean.setAttemptVal(rs.getString("OED_ATTEMPT"));
							return candidateMgmtBean;
						}
					});
		} catch (Exception e) {
			throw new GenericException(e);
		}

		if (hallticketList != null && !hallticketList.isEmpty()) {
			return hallticketList;
		} else {
			return null;
		}
	}

	public List<CandidateMgmtBean> getCandidateDetailsByIdForAdmin(CandidateMgmtBean candidateMgmtBean) throws Exception {

		List<CandidateMgmtBean> candidateDetailsList = null;
		try {
			candidateDetailsList = getJdbcTemplate().query(CandidateManagementQueries.GET_CANDIDATE_DETAILS_BY_USER_ID, new Object[] { candidateMgmtBean.getUserId(), },
					new RowMapper<CandidateMgmtBean>() {

						@Override
						public CandidateMgmtBean mapRow(ResultSet rs, int rowCount) throws SQLException {
							CandidateMgmtBean candidateMgmtBean = new CandidateMgmtBean();
							StringBuilder stringBuilder = new StringBuilder();

							stringBuilder.append(rs.getString("OCD_CAND_TITLE")).append(" ");
							stringBuilder.append(rs.getString("OCD_FIRST_NAME")).append(" ");
							if (rs.getString("OCD_MIDDLE_NAME") != null) {
								stringBuilder.append(rs.getString("OCD_MIDDLE_NAME")).append(" ");
							}
							stringBuilder.append(rs.getString("OCD_LAST_NAME")).append(" ");

							candidateMgmtBean.setTestCenterName(rs.getString("otcm_TEST_CENTRE_NAME"));
							candidateMgmtBean.setTestDates(rs.getString("testDate"));
							candidateMgmtBean.setTestName(rs.getString("OTM_TEST_NAME"));
							candidateMgmtBean.setUserId(rs.getString("OUM_USER_ID"));
							candidateMgmtBean.setCandidateTestStartTime(CommonUtil.getStringDate(rs.getTimestamp("obd_TEST_START_TIME")));
							candidateMgmtBean.setCandidateTestEndTime(CommonUtil.getStringDate(rs.getTimestamp("obd_TEST_END_TIME")));
							candidateMgmtBean.setCandidateName(stringBuilder.toString());

							return candidateMgmtBean;
						}
					});
		} catch (Exception e) {
			throw new GenericException(e);
		}

		if (candidateDetailsList != null && !candidateDetailsList.isEmpty()) {
			return candidateDetailsList;
		} else {
			return null;
		}
	}

	public Map<Integer, String> getTestCenterMasterDetails() throws Exception {

		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		try {
			map = (Map<Integer, String>) getJdbcTemplate().query(MasterQueries.GET_TEST_CENTER_DETAILS, new ResultSetExtractor() {

				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<Integer, String> map = new LinkedHashMap<Integer, String>();
					while (rs.next()) {
						map.put(rs.getInt("otcm_TEST_CENTRE_PK"), rs.getString("otcm_city"));
					}
					return map;
				}
			});

		} catch (Exception e) {
			throw new GenericException(e);
		}

		return map;
	}

	public List<String> getTestDates() throws Exception {

		List<String> testSlotList = null;
		try {
			testSlotList = getJdbcTemplate().query(CandidateManagementQueries.GET_TEST_DATES, new Object[] {
					// candidateMgmtBean.getTestCenterValue(),
			}, new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet rs, int rowCount) throws SQLException {

					return rs.getString("test_date");
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}

		if (testSlotList != null && !testSlotList.isEmpty()) {
			return testSlotList;
		} else {
			return null;
		}

	}

	public List<String> getTestSlots(CandidateMgmtBean candidateMgmtBean) throws Exception {

		List<String> testDateList = null;

		try {
			testDateList = getJdbcTemplate().query(CandidateManagementQueries.GET_TEST_SLOTS, new Object[] { candidateMgmtBean.getTestDateForSlot() },
					new RowMapper<String>() {

						@Override
						public String mapRow(ResultSet rs, int rowCount) throws SQLException {
							CandidateMgmtBean candidateMgmtBean = new CandidateMgmtBean();
							String slot = new String();

							candidateMgmtBean.setCandidateTestStartTime(rs.getString("start_date"));
							candidateMgmtBean.setCandidateTestEndTime(rs.getString("end_date"));

							if (rs.getString("start_date") != null && !rs.getString("start_date").equals("") && rs.getString("end_date") != null
									&& !rs.getString("end_date").equals("")) {
								slot = slot + rs.getString("start_date") + "-" + rs.getString("end_date");
							}

							return slot;
						}
					});
		} catch (DataAccessException e) {
			throw new GenericException(e);
		}

		if (testDateList != null && !testDateList.isEmpty()) {
			return testDateList;
		} else {
			return null;
		}
	}

	public List<CandidateMgmtBean> getCandidateDetailsForRegistration(CandidateMgmtBean candidateMgmtBean) throws Exception {

		List<CandidateMgmtBean> candidateList = null;

		try {

			if (candidateMgmtBean.getTestSlotSelected() != null && candidateMgmtBean.getTestSlotSelected().equals("-1")) {
				candidateMgmtBean.setTestSlotSelected(null);
			}

			if (candidateMgmtBean.getTestDateForSlot() == null || candidateMgmtBean.getTestDateForSlot().equals("-1") || candidateMgmtBean.getTestDateForSlot().equals("")
					|| candidateMgmtBean.getTestDateForSlot().equals("All")) {
				candidateMgmtBean.setTestDateForSlot(null);
			}

			candidateList = getJdbcTemplate().query(CandidateManagementQueries.GET_REGISTRATION_DETAILS_FOR_CANDIDATE, new Object[] {
					candidateMgmtBean.getDisciplineType() == null || candidateMgmtBean.getDisciplineType().compareTo("") == 0 ? null : candidateMgmtBean.getDisciplineType(),

					candidateMgmtBean.getTestCenterValue() == null || candidateMgmtBean.getTestCenterValue().compareTo("") == 0 ? null : candidateMgmtBean.getTestCenterValue(),

					candidateMgmtBean.getTestDateForSlot() == null || candidateMgmtBean.getTestDateForSlot().compareTo("") == 0 ? null : candidateMgmtBean.getTestDateForSlot(),

					candidateMgmtBean.getTestSlotSelected() == null || candidateMgmtBean.getTestSlotSelected().compareTo("") == 0 ? null
							: candidateMgmtBean.getTestSlotSelected().trim()

			}, new RowMapper<CandidateMgmtBean>() {
				public CandidateMgmtBean mapRow(ResultSet rs, int rowNum) {

					CandidateMgmtBean candidateMgmtBean = new CandidateMgmtBean();

					try {
						StringBuilder stringBuilder = new StringBuilder();

						stringBuilder.append(rs.getString("OCD_CAND_TITLE")).append(" ");
						stringBuilder.append(rs.getString("OCD_FIRST_NAME")).append(" ");
						if (rs.getString("OCD_MIDDLE_NAME") != null) {
							stringBuilder.append(rs.getString("OCD_MIDDLE_NAME")).append(" ");
						}
						stringBuilder.append(rs.getString("OCD_LAST_NAME")).append(" ");

						candidateMgmtBean.setTestCenterName(rs.getString("test_center"));
						candidateMgmtBean.setTestDates(rs.getString("testDate"));
						candidateMgmtBean.setTestName(rs.getString("test_name"));
						candidateMgmtBean.setUserId(rs.getString("user_id"));
						candidateMgmtBean.setCandidateTestStartTime(CommonUtil.getStringDate(rs.getTimestamp("obd_TEST_START_TIME")));
						candidateMgmtBean.setCandidateTestEndTime(CommonUtil.getStringDate(rs.getTimestamp("obd_TEST_END_TIME")));
						candidateMgmtBean.setCandidateName(stringBuilder.toString());
					} catch (Exception ex) {
						LoggerHome.getLogger(getClass()).fatal(ex);
					}

					return candidateMgmtBean;
				}
			});

			if (candidateList != null && !candidateList.isEmpty()) {
				return candidateList;
			} else {
				return null;
			}

		} catch (Exception ex) {
			throw new GenericException(ex);
		}
	}

	public List<EnrollmentBean> getCandidateDetailsForTestCenterAllocation(String stage, String approveVal) {
		List<EnrollmentBean> lstCandidateDetails = null;
		StringBuilder query = new StringBuilder();
		try {
			query.append(CandidateManagementQueries.GET_CANDIDATE_DATA_FOR_SEAT_ALLOCATION);
			if (ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.PAYMENT).equals("A")) {
				query.append(" and ocd.ocd_user_fk in( select opm.opd_user_fk       " + "    from oes_payment_details opm  "
						+ " where opm.opd_validated_status = 'A') order by OCPD.OCPD_APPL_DATE");
			} else {
				query.append(" and ocd.ocd_validated_status = '" + approveVal + "' order by OCPD.OCPD_APPL_DATE");
			}
			lstCandidateDetails = getJdbcTemplate().query(query.toString(), new Object[] {}, new RowMapper<EnrollmentBean>() {

				@Override
				public EnrollmentBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					EnrollmentBean enrollmentBean = new EnrollmentBean();

					enrollmentBean.setUserPK(rs.getString("ocpd_user_fk"));
					// enrollmentBean.setEnrollment_PK(rs.getString("ENROLLMENT_PK"));
					enrollmentBean.setPreferredTestCentre1FK(rs.getString("ocpd_pref_test_centre_1_fk"));
					enrollmentBean.setPreferredTestCentre2FK(rs.getString("ocpd_pref_test_centre_2_fk"));
					enrollmentBean.setPreferredTestCentre3FK(rs.getString("ocpd_pref_test_centre_3_fk"));

					return enrollmentBean;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstCandidateDetails;
	}

	public UserDetailsBean getCandidateDisciplineForPayment(Users users) throws GenericException {
		List<UserDetailsBean> candidateList = null;
		try {
			candidateList = getJdbcTemplate().query(CommonQueries.GET_CANDIDATE_DISCIPLINE_FOR_USER_FK, new Object[] { users.getUserFk() },
					new BeanPropertyRowMapper(UserDetailsBean.class));

		} catch (Exception e) {
			throw new GenericException(e);
		}
		return candidateList.get(0);
	}

	public List<HallTicketBean> getCandidateDataForRollNumberGeneration() throws Exception {
		List<HallTicketBean> hallTicketBeanList = null;
		try {
			hallTicketBeanList = getJdbcTemplate().query(CandidateManagementQueries.GET_CANDIDATE_DETAILS_FOR_ROLL_NUMBER_GENERATION, new Object[] {},
					new RowMapper<HallTicketBean>() {
						public HallTicketBean mapRow(ResultSet rs, int rowNum) {
							HallTicketBean hallTicketBean = new HallTicketBean();
							try {
								hallTicketBean.setTestDate(rs.getString("test_date"));
								hallTicketBean.setTestCenterId(rs.getString("OTCM_TEST_CENTRE_PK"));
								hallTicketBean.setBatchPk(rs.getString("obd_batch_pk"));
								hallTicketBean.setUserFK(Long.parseLong(rs.getString("OED_GCPD_USER_FK")));
							} catch (Exception e) {
								e.printStackTrace();
							}
							return hallTicketBean;
						}
					});

			if (hallTicketBeanList != null && !hallTicketBeanList.isEmpty()) {
				return hallTicketBeanList;
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
	}

	public String getRollNumberSeq(Integer maxDigitsForRollNumber) throws Exception {
		String seqVal = "";
		try {
			seqVal = (String) getJdbcTemplate().queryForObject(CandidateManagementQueries.GET_ROLL_NUMBER_SEQ_NEXT_VAL, new Object[] { maxDigitsForRollNumber }, String.class);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return seqVal;
	}

	public int[] updateCandidateRollNumbers(final Users users, final List<HallTicketBean> candidateDataForRollNumber) throws GenericException {
		int[] updateCounts = null;
		try {
			updateCounts = writeJdbcTemplate.batchUpdate(CandidateManagementQueries.UPDATE_CANDIDATE_ROLL_NUMBERS, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement pStatement, int currBatchIndex) throws SQLException {
					pStatement.setString(1, candidateDataForRollNumber.get(currBatchIndex).getRollNumber());
					pStatement.setString(2, users.getUsername());
					pStatement.setString(3, candidateDataForRollNumber.get(currBatchIndex).getBatchPk());
					pStatement.setLong(4, candidateDataForRollNumber.get(currBatchIndex).getUserFK());
				}

				@Override
				public int getBatchSize() {
					return candidateDataForRollNumber.size();
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);

		}
		return updateCounts;
	}

	public List<HallTicketBean> getCandidateDataForAdmitCard(CandidateMgmtBean candidateMgmtBean) throws Exception {
		List<HallTicketBean> hallTicketBeanList = null;
		try {
			hallTicketBeanList = getJdbcTemplate().query(CandidateManagementQueries.GET_CANDIDATE_DATA_FOR_ADMIT_CARD_USER_ID, new

			Object[] { candidateMgmtBean.getUserId().trim() }, new RowMapper<HallTicketBean>() {
				public HallTicketBean mapRow(ResultSet rs, int rowNum) {
					HallTicketBean hallTicketBean = new HallTicketBean();
					StringBuilder stringBuilder = new StringBuilder();
					try {
						stringBuilder.append(rs.getString("OCD_CAND_TITLE")).append(" ");
						stringBuilder.append(rs.getString("OCD_FIRST_NAME")).append(" ");
						if (rs.getString("OCD_MIDDLE_NAME") != null) {
							stringBuilder.append(rs.getString("OCD_MIDDLE_NAME")).append(" ");
						}
						stringBuilder.append(rs.getString("OCD_LAST_NAME")).append(" ");

						hallTicketBean.setTestDate(rs.getString("test_date"));
						hallTicketBean.setTestSlot(rs.getString("test_slot"));
						hallTicketBean.setUserId(rs.getString("oum_user_id"));
						hallTicketBean.setEnrollmetPk(rs.getString("oed_enrollment_pk"));
						hallTicketBean.setTestCenterName(rs.getString("otcm_test_centre_name"));

						hallTicketBean.setUserName(stringBuilder.toString());

						hallTicketBean.setUserFK(Long.parseLong(rs.getString("oum_user_pk")));
						hallTicketBean.setBookingAttempt(rs.getInt("oed_attempt"));
						hallTicketBean.setRollNumber(rs.getString("OED_ROLL_NO"));
					} catch (Exception e) {
						e.printStackTrace();
					}
					return hallTicketBean;
				}
			});

			if (hallTicketBeanList != null && !hallTicketBeanList.isEmpty()) {
				return hallTicketBeanList;
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
	}

	public int getCandidateDataForAdmitCardForSearchCount(CandidateMgmtBean candidateMgmtBean) throws Exception {
		int hallTicketBeanListCount = 0;
		Integer attempts = Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.NUMBER_OF_ATTEMPTS));
		if (attempts != null && !attempts.equals("") && attempts.equals(new Integer(1))) {
			candidateMgmtBean.setAttemptVal("1");
			if (candidateMgmtBean.getTestCenterVal() == null || candidateMgmtBean.getTestCenterVal().equals("")) {
				candidateMgmtBean.setTestCenterVal("0");
			}
		} else {
			if (candidateMgmtBean.getAttemptVal() == null || candidateMgmtBean.getAttemptVal().equals("")) {
				candidateMgmtBean.setAttemptVal("0");
			}

			if (candidateMgmtBean.getTestCenterVal() == null || candidateMgmtBean.getTestCenterVal().equals("")) {
				candidateMgmtBean.setTestCenterVal("0");
			}
		}
		try {
			hallTicketBeanListCount = getJdbcTemplate().queryForObject(CandidateManagementQueries.GET_CANDIDATE_DATA_FOR_ADMIT_CARD_COUNT, new Object[] {
					candidateMgmtBean.getTestCenterVal(), candidateMgmtBean.getTestCenterVal(), candidateMgmtBean.getAttemptVal(), candidateMgmtBean.getAttemptVal() }, Integer.class);

		} catch (Exception e) {
			hallTicketBeanListCount = 0;
			throw new GenericException(e);
		}
		return hallTicketBeanListCount;
	}

	public List<HallTicketBean> getCandidateDataForAdmitCardForSearch(CandidateMgmtBean candidateMgmtBean, Pagination pagination) throws Exception {
		List<HallTicketBean> hallTicketBeanList = null;
		Integer attempts = Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.NUMBER_OF_ATTEMPTS));
		if (attempts != null && !attempts.equals("") && attempts.equals(new Integer(1))) {
			candidateMgmtBean.setAttemptVal("1");
			if (candidateMgmtBean.getTestCenterVal() == null || candidateMgmtBean.getTestCenterVal().equals("")) {
				candidateMgmtBean.setTestCenterVal("0");
			}
		} else {
			if (candidateMgmtBean.getAttemptVal() == null || candidateMgmtBean.getAttemptVal().equals("")) {
				candidateMgmtBean.setAttemptVal("0");
			}

			if (candidateMgmtBean.getTestCenterVal() == null || candidateMgmtBean.getTestCenterVal().equals("")) {
				candidateMgmtBean.setTestCenterVal("0");
			}
		}

		try {
			hallTicketBeanList = getJdbcTemplate().query(
					CandidateManagementQueries.GET_CANDIDATE_DATA_FOR_ADMIT_CARD, new Object[] { candidateMgmtBean.getTestCenterVal(), candidateMgmtBean.getTestCenterVal(),
							candidateMgmtBean.getAttemptVal(), candidateMgmtBean.getAttemptVal(), pagination.getStart(), pagination.getEnd() },
					new RowMapper<HallTicketBean>() {
						public HallTicketBean mapRow(ResultSet rs, int rowNum) {
							HallTicketBean hallTicketBean = new HallTicketBean();
							StringBuilder stringBuilder = new StringBuilder();
							try {
								stringBuilder.append(rs.getString("OCD_CAND_TITLE")).append(" ");
								stringBuilder.append(rs.getString("OCD_FIRST_NAME")).append(" ");
								if (rs.getString("OCD_MIDDLE_NAME") != null) {
									stringBuilder.append(rs.getString("OCD_MIDDLE_NAME")).append(" ");
								}
								stringBuilder.append(rs.getString("OCD_LAST_NAME")).append(" ");

								hallTicketBean.setTestDate(rs.getString("test_date"));
								hallTicketBean.setTestSlot(rs.getString("test_slot"));
								hallTicketBean.setUserId(rs.getString("oum_user_id"));
								hallTicketBean.setEnrollmetPk(rs.getString("oed_enrollment_pk"));
								hallTicketBean.setTestCenterName(rs.getString("otcm_test_centre_name"));

								hallTicketBean.setUserName(stringBuilder.toString());

								hallTicketBean.setUserFK(Long.parseLong(rs.getString("oum_user_pk")));
								hallTicketBean.setBookingAttempt(rs.getInt("oed_attempt"));
								hallTicketBean.setRollNumber(rs.getString("OED_ROLL_NO"));
							} catch (Exception e) {
								e.printStackTrace();
							}
							return hallTicketBean;
						}
					});

			if (hallTicketBeanList != null && !hallTicketBeanList.isEmpty()) {
				return hallTicketBeanList;
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
	}

	public int[] updateGenerateHallticket(final List<String> enrollmentList, final Users users) {
		int[] updateCounts = null;
		try {
			updateCounts = writeJdbcTemplate.batchUpdate(CandidateManagementQueries.UPDATE_GENERATE_HALLTICKET_DATA, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement pStatement, int currBatchIndex) throws SQLException {
					pStatement.setString(1, "1");
					pStatement.setString(2, users.getUsername());
					pStatement.setInt(3, Integer.parseInt(enrollmentList.get(currBatchIndex)));
				}

				@Override
				public int getBatchSize() {
					return enrollmentList.size();
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return updateCounts;
	}

	public String generateRollNumberForCandidates() throws Exception {
		String regOutput = null;
		Map outputMap = null;
		List<SqlParameter> declaredParameters = new ArrayList<SqlParameter>();

		declaredParameters.add(new SqlOutParameter("sOUT", Types.VARCHAR));

		outputMap = getJdbcTemplate().call(new CallableStatementCreator() {
			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement stmnt = con.prepareCall("{ call SP_GENERATE_ROLL_NO(?)}");
				stmnt.registerOutParameter(1, Types.VARCHAR);
				return stmnt;
			}
		}, declaredParameters);
		regOutput = (String) outputMap.get("sOUT");
		return regOutput;
	}

	public UserDetailsBean getUserDetailsForEnrollmentId(String enrollmentPk) throws GenericException {
		List<UserDetailsBean> candidateList = null;
		try {
			candidateList = getJdbcTemplate().query(CandidateManagementQueries.GET_USER_DETAILS_FOR_ENROLLMENT_ID, new Object[] { enrollmentPk },
					new BeanPropertyRowMapper(UserDetailsBean.class));

		} catch (Exception e) {
			throw new GenericException(e);
		}
		return candidateList.get(0);
	}

	public int getRegisteredCandidateDetails(String userId, String fromDate, String toDate, Integer disciplineId, Integer candidateStatusId, int formStatusId) {
		int totalNumberOfRecord = 0;
		try {
			StringBuilder queryString = new StringBuilder();
			queryString.append(CandidateManagementQueries.GET_REGISTERED_CANDIDATE_COUNT);
			queryString.append(" AND OCD_STATUS_ID_FK >=" + formStatusId);
			if (!ValidatorUtil.isEmpty(userId)) {
				queryString.append(" AND oum.OUM_USER_ID = '" + userId + "'");
			}
			if (!ValidatorUtil.isEmpty(fromDate) && !ValidatorUtil.isEmpty(toDate)) {
				queryString.append(" AND TRUNC (ocd_created_date) BETWEEN '" + fromDate + "' AND '" + toDate + "' ");
			}
			if (!ValidatorUtil.isEmpty(fromDate) && ValidatorUtil.isEmpty(toDate)) {
				queryString.append("  AND TRUNC (ocd_created_date) >= '" + fromDate + "'");
			}
			if (ValidatorUtil.isEmpty(fromDate) && !ValidatorUtil.isEmpty(toDate)) {
				queryString.append("  AND TRUNC (ocd_created_date) <= '" + toDate + "'");
			}
			if (disciplineId != 0) {
				queryString.append(" AND otm.otm_test_pk = " + disciplineId);
			}
			if (candidateStatusId == 1 || candidateStatusId == 0) {
				queryString.append(" AND ocd.ocd_validated_status = " + candidateStatusId);
			}
			if (candidateStatusId == 2) {
				queryString.append(" AND ocd.ocd_validated_status is null ");
			}
			totalNumberOfRecord = getJdbcTemplate().queryForObject(queryString.toString(), new Object[] {}, Integer.class);
		} catch (Exception ex) {
			totalNumberOfRecord = 0;
		}
		return totalNumberOfRecord;
	}

	public List<CandidateApproveRejectBean> getRegisteredCandidateDetails(String userId, String fromDate, String toDate, Integer disciplineId, Integer candidateStatusId,
			int formStatusId, Pagination pagination) {
		List<CandidateApproveRejectBean> registeredCandidateList = new ArrayList<CandidateApproveRejectBean>();
		try {
			StringBuilder queryString = new StringBuilder();
			queryString.append(CandidateManagementQueries.GET_REGISTERED_CANDIDATE);
			queryString.append(" AND OCD_STATUS_ID_FK >=" + formStatusId);
			if (!ValidatorUtil.isEmpty(userId)) {
				queryString.append(" AND oum.OUM_USER_ID = '" + userId + "'");
			}
			if (!ValidatorUtil.isEmpty(fromDate) && !ValidatorUtil.isEmpty(toDate)) {
				queryString.append(" AND TRUNC (ocd_created_date) BETWEEN '" + fromDate + "' AND '" + toDate + "' ");
			}
			if (!ValidatorUtil.isEmpty(fromDate) && ValidatorUtil.isEmpty(toDate)) {
				queryString.append("  AND TRUNC (ocd_created_date) >= '" + fromDate + "'");
			}
			if (ValidatorUtil.isEmpty(fromDate) && !ValidatorUtil.isEmpty(toDate)) {
				queryString.append("  AND TRUNC (ocd_created_date) <= '" + toDate + "'");
			}
			if (disciplineId != null && disciplineId != 0) {
				queryString.append(" AND otm.otm_test_pk = " + disciplineId);
			}
			if (candidateStatusId != null) {
				if (candidateStatusId == 1 || candidateStatusId == 0) {
					queryString.append(" AND ocd.ocd_validated_status = " + candidateStatusId);
				}
				if (candidateStatusId == 2) {
					queryString.append(" AND ocd.ocd_validated_status is null ");
				}
			}
			queryString.append(" order by oum.OUM_USER_ID)");
			queryString.append(" WHERE num BETWEEN ").append(pagination.getStart()).append(" AND ").append(pagination.getEnd());

			registeredCandidateList = getJdbcTemplate().query(queryString.toString(), new BeanPropertyRowMapper(CandidateApproveRejectBean.class));

		} catch (Exception ex) {
			registeredCandidateList = new ArrayList<CandidateApproveRejectBean>();
		}
		return registeredCandidateList;
	}

	public BatchDetailsBean getbatchDetailsForSlot(String testCenterFk, String testDate, String testSlot) throws Exception {
		// logger.info("Calling getbatchDetailsForSlot()");
		BatchDetailsBean batchDetailsBean = null;
		// logger.info("testSlot "+testSlot);
		try {
			batchDetailsBean = (BatchDetailsBean) getJdbcTemplate().queryForObject(CandidateManagementQueries.GET_SLOT_DETAILS_FOR_CENTER_DATE,
					new Object[] { testCenterFk, testDate, testSlot }, new BeanPropertyRowMapper(BatchDetailsBean.class));
		} catch (Exception e) {
			batchDetailsBean = null;
		}
		return batchDetailsBean;
	}

	public int updateOesPaymentDetails(String challanNumber, Integer paymentModeId) throws Exception {
		int updateCount = 0;
		try {
			updateCount = writeJdbcTemplate.update(CandidateQueries.UPDATE_OES_PAYMENT_DETAILS, new Object[] { challanNumber, paymentModeId });
		} catch (Exception e) {
			e.printStackTrace();
			updateCount = 0;
		}
		return updateCount;
	}

	public int updateOesPaymentDetailsWithPaymentReject(String challanNumber, Integer paymentModeId) throws Exception {
		int updateCount = 0;
		try {
			updateCount = writeJdbcTemplate.update(CandidateQueries.UPDATE_OES_PAYMENT_DETAILS_FOR_REJECT, new Object[] { challanNumber, paymentModeId });
		} catch (Exception e) {
			e.printStackTrace();
			updateCount = 0;
		}
		return updateCount;
	}

	public int updateCandidateStatus(int candidateStatus, Integer userId) throws Exception {
		int updateCount = 0;
		try {
			updateCount = writeJdbcTemplate.update(CandidateQueries.UPDATE_CANDIDATE_STATUS, new Object[] { candidateStatus, userId });
		} catch (Exception e) {
			updateCount = 0;
		}
		return updateCount;
	}

	public Map<Integer, Map<Integer, Integer>> getTestCenterWiseBatchDetails() throws Exception {

		Map<Integer, Map<Integer, Integer>> testCenterWiseBatchDetailsMap = new LinkedHashMap<Integer, Map<Integer, Integer>>();
		Map<Integer, Integer> batchDetailsListMap = new LinkedHashMap<Integer, Integer>();
		List<Integer> testCenterIDList = new ArrayList<Integer>();

		try {
			testCenterIDList = (List<Integer>) getJdbcTemplate().query(MasterQueries.GET_TEST_CENTER_PK, new ResultSetExtractor() {

				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<Integer> testCenterList = new ArrayList<Integer>();
					while (rs.next()) {
						testCenterList.add(rs.getInt("OTCM_TEST_CENTRE_PK"));
					}
					return testCenterList;
				}

			});
			for (int i = 0; i < testCenterIDList.size(); i++) {
				batchDetailsListMap = (Map<Integer, Integer>) getJdbcTemplate().query(MasterQueries.GET_BATCH_DETAILS_FOR_TEST_CENTER, new Object[] { testCenterIDList.get(i) },
						new ResultSetExtractor() {

							@Override
							public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
								Map<Integer, Integer> batchDetailsMap = new LinkedHashMap<Integer, Integer>();
								while (rs.next()) {
									batchDetailsMap.put(rs.getInt("OBD_BATCH_PK"), rs.getInt("OBD_AVAILABLE_CAPACITY"));
								}
								return batchDetailsMap;
							}

						});
				testCenterWiseBatchDetailsMap.put(testCenterIDList.get(i), batchDetailsListMap);
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return testCenterWiseBatchDetailsMap;
	}

	public List<EnrollmentBean> getCandidateDetailsForTestCenterAllocationWithTestDates(String stage, String approveVal) {
		List<EnrollmentBean> lstCandidateDetails = null;
		StringBuilder query = new StringBuilder();
		try {

			query.append(CandidateManagementQueries.GET_CANDIDATE_DATA_FOR_SEAT_ALLOCATION_TEST_DATES);
			if (ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.PAYMENT).equals("A")) {
				query.append(" and ocd.ocd_user_fk in( select opm.opd_user_fk       " + "    from oes_payment_details opm  "
						+ "where opm.opd_validated_status = 'A') order by OCPD.OCPD_APPL_DATE");
			} else {
				query.append(" and ocd.ocd_validated_status = '" + approveVal + "' order by OCPD.OCPD_APPL_DATE");
			}

			lstCandidateDetails = getJdbcTemplate().query(query.toString(), new Object[] {}, new RowMapper<EnrollmentBean>() {

				@Override
				public EnrollmentBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					EnrollmentBean enrollmentBean = new EnrollmentBean();

					enrollmentBean.setUserPK(rs.getString("ocpd_user_fk"));
					// enrollmentBean.setEnrollment_PK(rs.getString("ENROLLMENT_PK"));
					enrollmentBean.setPreferredTestCentre1FK(rs.getString("ocpd_pref_test_centre_1_fk"));
					enrollmentBean.setPreferredTestCentre2FK(rs.getString("ocpd_pref_test_centre_2_fk"));
					enrollmentBean.setPreferredTestCentre3FK(rs.getString("ocpd_pref_test_centre_3_fk"));
					enrollmentBean.setPreferedTestDate1(rs.getString("OCPD_PREF_TEST_DATE1"));
					enrollmentBean.setPreferedTestDate2(rs.getString("OCPD_PREF_TEST_DATE2"));
					enrollmentBean.setPreferedTestDate3(rs.getString("OCPD_PREF_TEST_DATE3"));
					return enrollmentBean;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstCandidateDetails;
	}

	public Map<Integer, List<String>> getTestCenterTestDateMap() throws GenericException {
		// logger.info("Calling getStageOffListMap()");
		Map<Integer, List<String>> testDateTestCenterMap = new LinkedHashMap<Integer, List<String>>();
		List<Integer> testCenterIDList = null;
		testCenterIDList = (List<Integer>) getJdbcTemplate().query(MasterQueries.GET_TEST_CENTER_PK, new ResultSetExtractor() {

			@Override
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Integer> testCenterList = new ArrayList<Integer>();
				while (rs.next()) {
					testCenterList.add(rs.getInt("OTCM_TEST_CENTRE_PK"));
				}
				return testCenterList;
			}
		});
		for (Integer testCenterId : testCenterIDList) {
			testDateTestCenterMap.put(testCenterId, getTestDatesForTestCenter(testCenterId));
		}
		return testDateTestCenterMap;
	}

	public Map<String, List<BatchDetailsBean>> getTestDateBatchDetailsMap() throws GenericException {
		Map<String, List<BatchDetailsBean>> testDateBatchDetailsMap = new HashMap<String, List<BatchDetailsBean>>();
		List<String> testDateList = null;
		Map<Integer, List<String>> testDateTestCenterMap = getTestCenterTestDateMap();
		for (Map.Entry<Integer, List<String>> entry : testDateTestCenterMap.entrySet()) {
			if (entry.getValue() != null) {
				testDateList = entry.getValue();
				for (String testDate : testDateList) {
					// testDateBatchDetailsMap.put(testDate,
					// getBatchDetailsForTestDate(testDate));
				}
			}

		}
		return testDateBatchDetailsMap;
	}

	private List<String> getTestDatesForTestCenter(Integer testCenterId) {
		List<String> testDateList = null;
		try {
			// testDateList =
			// getJdbcTemplate().query(MasterQueries.GET_TEST_DATES_FOR_TEST_CENTER,new
			// Object[]{testCenterId}, new BeanPropertyRowMapper(String.class));
			testDateList = getJdbcTemplate().query(MasterQueries.GET_TEST_DATES_FOR_TEST_CENTER, new Object[] { testCenterId }, new RowMapper<String>() {
				public String mapRow(ResultSet rs, int rowCount) throws SQLException {
					String tstDate = rs.getString("obd_test_date");
					return tstDate;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			testDateList = null;
		}
		return testDateList;
	}

	public List<BatchDetailsBean> getBatchDetailsForTestDate(String testDate, Integer testCenterId) {
		List<BatchDetailsBean> batchDetailsList = null;
		try {
			batchDetailsList = getJdbcTemplate().query(CandidateManagementQueries.GET_BATCH_DETAILS_FOR_TEST_DATES, new Object[] { testDate, testCenterId },
					new BeanPropertyRowMapper(BatchDetailsBean.class));
		} catch (Exception e) {
			e.printStackTrace();
			batchDetailsList = null;
		}
		return batchDetailsList;
	}

	public Map<String, List<BatchDetailsBean>> getTestDatebatchDetailsMap() throws GenericException {
		// logger.info("Calling getStageOffListMap()");
		Map<String, List<BatchDetailsBean>> testDateTestCenterMap = new LinkedHashMap<String, List<BatchDetailsBean>>();
		List<String> testDateList = null;
		testDateList = (List<String>) getJdbcTemplate().query(MasterQueries.GET_TEST_DATES, new ResultSetExtractor() {

			@Override
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<String> testDateListTmp = new ArrayList<String>();
				while (rs.next()) {
					testDateListTmp.add(rs.getString("obd_test_date"));
				}
				return testDateListTmp;
			}

		});
		for (String testDate : testDateList) {
			// testDateTestCenterMap.put(testDate,
			// getBatchDetailsForTestDate(testDate));
		}
		return testDateTestCenterMap;
	}

	public int getPaymentReportSearchResultCount(CandidateMgmtBean candidateMgmtBean) throws Exception {
		int paymentReportResultBeanListCount = 0;

		String strFromDate1 = null;
		String strToDate2 = null;

		if (!ValidatorUtil.isEmpty(candidateMgmtBean.getFromDate()) && !ValidatorUtil.isEmpty(candidateMgmtBean.getToDate())) {
			Date date1 = new Date(candidateMgmtBean.getFromDate().toUpperCase());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			strFromDate1 = formatter1.format(date1);

			Date date2 = new Date(candidateMgmtBean.getToDate().toUpperCase());
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
			strToDate2 = formatter2.format(date2);
		}

		StringBuffer queryStr = new StringBuffer();

		queryStr.append(CandidateManagementQueries.GET_PAYMENT_REPORT_SEARCH_RESULT_COUNT);

		if (candidateMgmtBean.getDisciplineId() != null) {
			if (candidateMgmtBean.getDisciplineId().equals("")) {
				candidateMgmtBean.setDisciplineId("0");
			}
			queryStr.append(
					" AND otm.OTM_TEST_PK = CASE WHEN " + candidateMgmtBean.getDisciplineId() + " = 0 THEN otm.OTM_TEST_PK ELSE " + candidateMgmtBean.getDisciplineId() + " END ");
		}

		if (candidateMgmtBean.getPaymentStatus() != null && candidateMgmtBean.getPaymentStatus().equals("1")) {
			queryStr.append(" AND opd.OPD_VALIDATED_STATUS ='R' ");
		}
		if (candidateMgmtBean.getPaymentStatus() != null && candidateMgmtBean.getPaymentStatus().equals("2")) {
			queryStr.append(" AND opd.OPD_VALIDATED_STATUS ='A' ");
		}
		if (candidateMgmtBean.getPaymentStatus() != null && candidateMgmtBean.getPaymentStatus().equals("3")) {
			queryStr.append(" AND opd.OPD_VALIDATED_STATUS IS NULL ");
		}

		if (!ValidatorUtil.isEmpty(candidateMgmtBean.getFromDate()) && !ValidatorUtil.isEmpty(candidateMgmtBean.getToDate())) {
			queryStr.append(" AND opd.OPD_CREATED_DATE::date BETWEEN '" + strFromDate1 + "' AND '" + strToDate2 + "'");
		}

		if (!ValidatorUtil.isEmpty(candidateMgmtBean.getFromDate()) && ValidatorUtil.isEmpty(candidateMgmtBean.getToDate())) {
			queryStr.append("  AND to_char (opd.OPD_CREATED_DATE,'dd-MON-yyyy') >= '" + candidateMgmtBean.getFromDate().toUpperCase() + "'");
		}
		if (ValidatorUtil.isEmpty(candidateMgmtBean.getFromDate()) && !ValidatorUtil.isEmpty(candidateMgmtBean.getToDate())) {
			queryStr.append("  AND to_char (ocd_created_date,'dd-MON-yyyy') <= '" + candidateMgmtBean.getToDate().toUpperCase() + "'");
		}
		try {
			paymentReportResultBeanListCount = getJdbcTemplate().queryForObject(queryStr.toString(), new Object[] { Integer.parseInt(candidateMgmtBean.getPaymentMode()) }, Integer.class);
		} catch (Exception e) {
			paymentReportResultBeanListCount = 0;
			e.printStackTrace();
		}

		return paymentReportResultBeanListCount;
	}

	public List<CandidateMgmtBean> getPaymentReportSearchResult(CandidateMgmtBean candidateMgmtBean, Pagination pagination) throws Exception {
		List<CandidateMgmtBean> paymentReportResultBeanList = null;

		String strFromDate1 = null;
		String strToDate2 = null;

		if (!ValidatorUtil.isEmpty(candidateMgmtBean.getFromDate()) && !ValidatorUtil.isEmpty(candidateMgmtBean.getToDate())) {
			Date date1 = new Date(candidateMgmtBean.getFromDate().toUpperCase());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			strFromDate1 = formatter1.format(date1);

			Date date2 = new Date(candidateMgmtBean.getToDate().toUpperCase());
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
			strToDate2 = formatter2.format(date2);
		}

		StringBuffer queryStr = new StringBuffer();

		queryStr.append(CandidateManagementQueries.GET_PAYMENT_REPORT_SEARCH_RESULT);

		if (candidateMgmtBean.getDisciplineId() != null) {
			if (candidateMgmtBean.getDisciplineId().equals("")) {
				candidateMgmtBean.setDisciplineId("0");
			}
			queryStr.append(
					" AND otm.OTM_TEST_PK = CASE WHEN " + candidateMgmtBean.getDisciplineId() + " = 0 THEN otm.OTM_TEST_PK ELSE " + candidateMgmtBean.getDisciplineId() + " END ");
		}

		if (candidateMgmtBean.getPaymentStatus() != null && candidateMgmtBean.getPaymentStatus().equals("1")) {
			queryStr.append(" AND opd.OPD_VALIDATED_STATUS ='R' ");
		}
		if (candidateMgmtBean.getPaymentStatus() != null && candidateMgmtBean.getPaymentStatus().equals("2")) {
			queryStr.append(" AND opd.OPD_VALIDATED_STATUS ='A' ");
		}
		if (candidateMgmtBean.getPaymentStatus() != null && candidateMgmtBean.getPaymentStatus().equals("3")) {
			queryStr.append(" AND opd.OPD_VALIDATED_STATUS IS NULL ");
		}

		if (!ValidatorUtil.isEmpty(candidateMgmtBean.getFromDate()) && !ValidatorUtil.isEmpty(candidateMgmtBean.getToDate())) {
			queryStr.append(" AND opd.OPD_CREATED_DATE::date BETWEEN '" + strFromDate1 + "' AND '" + strToDate2 + "'");
		}

		queryStr.append(" ORDER BY oum.OUM_USER_ID )");
		queryStr.append(" as aliasTEST WHERE num BETWEEN ").append(pagination.getStart()).append(" AND ").append(pagination.getEnd());
		try {
			// logger.info("paymenty report query\t\n"+queryStr.toString());
			paymentReportResultBeanList = getJdbcTemplate().query(queryStr.toString(), new Object[] { Integer.parseInt(candidateMgmtBean.getPaymentMode()) },
					new RowMapper<CandidateMgmtBean>() {

						@Override
						public CandidateMgmtBean mapRow(ResultSet rs, int rowCount) throws SQLException {
							CandidateMgmtBean paymentReportBean = new CandidateMgmtBean();
							StringBuilder stringBuilder = new StringBuilder();

							if (rs.getString("OCD_CAND_TITLE") != null) {
								stringBuilder.append(rs.getString("OCD_CAND_TITLE")).append(" ");
							} else {
								stringBuilder.append("");
							}
							if (rs.getString("OCD_FIRST_NAME") != null) {
								stringBuilder.append(rs.getString("OCD_FIRST_NAME")).append(" ");
							} else {
								stringBuilder.append("");
							}
							if (rs.getString("OCD_MIDDLE_NAME") != null) {
								stringBuilder.append(rs.getString("OCD_MIDDLE_NAME")).append(" ");
							} else {
								stringBuilder.append("");
							}
							if (rs.getString("OCD_LAST_NAME") != null) {
								stringBuilder.append(rs.getString("OCD_LAST_NAME")).append(" ");
							} else {
								stringBuilder.append("");
							}
							paymentReportBean.setEnrollmentId(rs.getString("OUM_USER_ID"));
							paymentReportBean.setFirstName(stringBuilder.toString());
							paymentReportBean.setDisciplineType(rs.getString("OTM_TEST_NAME"));
							paymentReportBean.setCandidateCategory(rs.getString("OCTM_CATEGORY_CODE"));
							paymentReportBean.setPaymentMode(rs.getString("OPTM_PAYMENT_DESC"));
							paymentReportBean.setApplicableFees(rs.getString("OPD_AMOUNT"));
							paymentReportBean.setMobileNO(rs.getString("OUM_MOBILE_NO"));
							paymentReportBean.setEmailAddress(rs.getString("OUM_EMAIL_ID"));
							paymentReportBean.setPaymentSubmittedDate(rs.getString("CREATED_DATE"));
							paymentReportBean.setPaymentApprovalDate(rs.getString("UPDATED_DATE"));
							paymentReportBean.setDdChallanDate(rs.getString("OPD_DD_DATE"));
							paymentReportBean.setDdChallanReceiptNo(rs.getString("OPD_DD_CHALLAN_RECEIPT_NO"));
							paymentReportBean.setChallanBranchName(rs.getString("OPD_BRANCH_NAME"));
							paymentReportBean.setChallanBranchCode(rs.getString("OPD_BRANCH_CODE"));
							paymentReportBean.setDdBankName(rs.getString("OPD_BANK_NAME"));
							paymentReportBean.setDdCityName(rs.getString("OPD_CITY"));
							paymentReportBean.setOnlineTransactionNo(rs.getString("OPD_TRANSACTION_NO"));
							paymentReportBean.setOnlineTransactionDate(rs.getString("online_transaction_date"));

							if (rs.getString("OPD_VALIDATED_STATUS") != null) {
								if (rs.getString("OPD_VALIDATED_STATUS").equals("A")) {
									paymentReportBean.setPaymentStatus("Approved");
								}

								if (rs.getString("OPD_VALIDATED_STATUS").equals("R")) {
									paymentReportBean.setPaymentStatus("Rejected");
								}
							} else {
								paymentReportBean.setPaymentStatus("Pending For Approval");
							}

							return paymentReportBean;
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return paymentReportResultBeanList;
	}

	public List<CandidateMgmtBean> getPaymentReportSearchResult(CandidateMgmtBean candidateMgmtBean) throws Exception {
		List<CandidateMgmtBean> paymentReportResultBeanList = null;

		StringBuffer queryStr = new StringBuffer();

		queryStr.append(CandidateManagementQueries.GET_PAYMENT_REPORT_SEARCH_RESULT_EXCEL);

		if (candidateMgmtBean.getDisciplineId() != null) {
			if (candidateMgmtBean.getDisciplineId().equals("")) {
				candidateMgmtBean.setDisciplineId("0");
			}
			queryStr.append(
					" AND otm.OTM_TEST_PK = CASE WHEN " + candidateMgmtBean.getDisciplineId() + " = 0 THEN otm.OTM_TEST_PK ELSE " + candidateMgmtBean.getDisciplineId() + " END ");
		}

		if (candidateMgmtBean.getPaymentStatus() != null && candidateMgmtBean.getPaymentStatus().equals("1")) {
			queryStr.append(" AND opd.OPD_VALIDATED_STATUS ='R' ");
		}
		if (candidateMgmtBean.getPaymentStatus() != null && candidateMgmtBean.getPaymentStatus().equals("2")) {
			queryStr.append(" AND opd.OPD_VALIDATED_STATUS ='A' ");
		}
		if (candidateMgmtBean.getPaymentStatus() != null && candidateMgmtBean.getPaymentStatus().equals("3")) {
			queryStr.append(" AND opd.OPD_VALIDATED_STATUS IS NULL ");
		}

		if (!ValidatorUtil.isEmpty(candidateMgmtBean.getFromDate()) && !ValidatorUtil.isEmpty(candidateMgmtBean.getToDate())) {
			queryStr.append(" AND TO_CHAR(opd.OPD_CREATED_DATE, 'dd-MON-yyyy') BETWEEN '" + candidateMgmtBean.getFromDate().toUpperCase() + "' AND '"
					+ candidateMgmtBean.getToDate().toUpperCase() + "'");
		}

		queryStr.append(" ORDER BY oum.OUM_USER_ID)as aliasTEST");
		try {
			// logger.info("Payment Report Excel
			// query\t\n"+queryStr.toString());
			paymentReportResultBeanList = getJdbcTemplate().query(queryStr.toString(), new Object[] { Integer.parseInt(candidateMgmtBean.getPaymentMode()) },
					new RowMapper<CandidateMgmtBean>() {

						@Override
						public CandidateMgmtBean mapRow(ResultSet rs, int rowCount) throws SQLException {
							CandidateMgmtBean paymentReportBean = new CandidateMgmtBean();
							StringBuilder stringBuilder = new StringBuilder();
							// modified by Prudhvi 24-09-2017
							if (rs.getString("OCD_CAND_TITLE") != null) {
								stringBuilder.append(rs.getString("OCD_CAND_TITLE")).append(" ");
							} else {
								stringBuilder.append("");
							}
							if (rs.getString("OCD_FIRST_NAME") != null) {
								stringBuilder.append(rs.getString("OCD_FIRST_NAME")).append(" ");
							} else {
								stringBuilder.append("");
							}
							if (rs.getString("OCD_MIDDLE_NAME") != null) {
								stringBuilder.append(rs.getString("OCD_MIDDLE_NAME")).append(" ");
							} else {
								stringBuilder.append("");
							}
							if (rs.getString("OCD_LAST_NAME") != null) {
								stringBuilder.append(rs.getString("OCD_LAST_NAME")).append(" ");
							} else {
								stringBuilder.append("");
							}
							paymentReportBean.setEnrollmentId(rs.getString("OUM_USER_ID"));
							paymentReportBean.setFirstName(stringBuilder.toString());
							paymentReportBean.setDisciplineType(rs.getString("OTM_TEST_NAME"));
							paymentReportBean.setCandidateCategory(rs.getString("OCTM_CATEGORY_CODE"));
							paymentReportBean.setPaymentMode(rs.getString("OPTM_PAYMENT_DESC"));
							paymentReportBean.setApplicableFees(rs.getString("OFM_FEES"));
							paymentReportBean.setMobileNO(rs.getString("OUM_MOBILE_NO"));
							paymentReportBean.setEmailAddress(rs.getString("OUM_EMAIL_ID"));
							paymentReportBean.setPaymentSubmittedDate(rs.getString("CREATED_DATE"));
							paymentReportBean.setPaymentApprovalDate(rs.getString("UPDATED_DATE"));
							paymentReportBean.setDdChallanDate(rs.getString("OPD_DD_DATE"));
							paymentReportBean.setDdChallanReceiptNo(rs.getString("OPD_DD_CHALLAN_RECEIPT_NO"));
							paymentReportBean.setChallanBranchName(rs.getString("OPD_BRANCH_NAME"));
							paymentReportBean.setChallanBranchCode(rs.getString("OPD_BRANCH_CODE"));
							paymentReportBean.setDdBankName(rs.getString("OPD_BANK_NAME"));
							paymentReportBean.setDdCityName(rs.getString("OPD_CITY"));
							paymentReportBean.setOnlineTransactionNo(rs.getString("OPD_TRANSACTION_NO"));
							paymentReportBean.setOnlineTransactionDate(rs.getString("ONLINE_TRANSACTION_DATE"));

							if (rs.getString("OPD_VALIDATED_STATUS") != null) {
								if (rs.getString("OPD_VALIDATED_STATUS").equals("A")) {
									paymentReportBean.setPaymentStatus("Approved");
								}

								if (rs.getString("OPD_VALIDATED_STATUS").equals("R")) {
									paymentReportBean.setPaymentStatus("Rejected");
								}
							} else {
								paymentReportBean.setPaymentStatus("Pending For Approval");
							}

							return paymentReportBean;
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return paymentReportResultBeanList;
	}

	public int getCandidateDetailsForPaymentNotSubmittedCount(CandidateMgmtBean candidateMgmtBean) throws Exception {
		int candidateDetailsListCount = 0;
		try {
			if (candidateMgmtBean.getDisciplineId() == null || candidateMgmtBean.getDisciplineId().equals("")) {
				candidateMgmtBean.setDisciplineId("0");
			}
			candidateDetailsListCount = getJdbcTemplate().queryForObject(CandidateManagementQueries.GET_CANDIDATE_DETAILS_FOR_PAYMENT_NOT_SUBMITTED_COUNT,
					new Object[] { Integer.parseInt(candidateMgmtBean.getDisciplineId()), Integer.parseInt(candidateMgmtBean.getDisciplineId()) }, Integer.class);
			// logger.info("GET_CANDIDATE_DETAILS_FOR_PAYMENT_NOT_SUBMITTED_COUNT:\t\n"+CandidateManagementQueries.GET_CANDIDATE_DETAILS_FOR_PAYMENT_NOT_SUBMITTED_COUNT);
		} catch (Exception e) {
			candidateDetailsListCount = 0;
			throw new GenericException(e);
		}
		return candidateDetailsListCount;
	}

	public List<CandidateMgmtBean> getCandidateDetailsForPaymentNotSubmitted(CandidateMgmtBean candidateMgmtBean, Pagination pagination) throws Exception {
		List<CandidateMgmtBean> candidateDetailsList = null;
		try {

			if (candidateMgmtBean.getDisciplineId() == null || candidateMgmtBean.getDisciplineId().equals("")) {
				candidateMgmtBean.setDisciplineId("0");
			}
			candidateDetailsList = getJdbcTemplate().query(CandidateManagementQueries.GET_CANDIDATE_DETAILS_FOR_PAYMENT_NOT_SUBMITTED, new Object[] {
					Integer.parseInt(candidateMgmtBean.getDisciplineId()), Integer.parseInt(candidateMgmtBean.getDisciplineId()), pagination.getStart(), pagination.getEnd() },
					new RowMapper<CandidateMgmtBean>() {

						@Override
						public CandidateMgmtBean mapRow(ResultSet rs, int rowCount) throws SQLException {
							CandidateMgmtBean candidateMgmtBean = new CandidateMgmtBean();
							StringBuilder stringBuilder = new StringBuilder();

							candidateMgmtBean.setUserId(rs.getString("OUM_USER_ID"));
							stringBuilder.append(rs.getString("OCD_FIRST_NAME")).append(" ");
							if (rs.getString("OCD_MIDDLE_NAME") != null) {
								stringBuilder.append(rs.getString("OCD_MIDDLE_NAME")).append(" ");
							}
							stringBuilder.append(rs.getString("OCD_LAST_NAME")).append(" ");
							candidateMgmtBean.setCandidateName(stringBuilder.toString());
							candidateMgmtBean.setCandidateCategory(rs.getString("OCTM_CATEGORY_CODE"));
							candidateMgmtBean.setDisciplineType(rs.getString("OTM_TEST_NAME"));
							candidateMgmtBean.setMobileNO(rs.getString("OUM_MOBILE_NO"));
							candidateMgmtBean.setEmailAddress(rs.getString("Oum_EMAIL_ID"));
							candidateMgmtBean.setPaymentStatus("Payment Not Submitted");
							return candidateMgmtBean;
						}
					});
			// logger.info("GET_CANDIDATE_DETAILS_FOR_PAYMENT_NOT_SUBMITTED_COUNT:\t\n"+CandidateManagementQueries.GET_CANDIDATE_DETAILS_FOR_PAYMENT_NOT_SUBMITTED);
		} catch (Exception e) {
			throw new GenericException(e);
		}

		if (candidateDetailsList != null && !candidateDetailsList.isEmpty()) {
			return candidateDetailsList;
		} else {
			return null;
		}
	}

	public List<CandidateMgmtBean> getCandidateDetailsForPaymentNotSubmitted(CandidateMgmtBean candidateMgmtBean) throws Exception {
		List<CandidateMgmtBean> candidateDetailsList = null;
		try {

			if (candidateMgmtBean.getDisciplineId() == null || candidateMgmtBean.getDisciplineId().equals("")) {
				candidateMgmtBean.setDisciplineId("0");
			}
			candidateDetailsList = getJdbcTemplate().query(CandidateManagementQueries.GET_CANDIDATE_DETAILS_FOR_PAYMENT_NOT_SUBMITTED_EXCEL,
					new Object[] { Integer.parseInt(candidateMgmtBean.getDisciplineId()), Integer.parseInt(candidateMgmtBean.getDisciplineId()) },
					new RowMapper<CandidateMgmtBean>() {

						@Override
						public CandidateMgmtBean mapRow(ResultSet rs, int rowCount) throws SQLException {
							CandidateMgmtBean candidateMgmtBean = new CandidateMgmtBean();
							StringBuilder stringBuilder = new StringBuilder();

							candidateMgmtBean.setUserId(rs.getString("OUM_USER_ID"));
							stringBuilder.append(rs.getString("OCD_FIRST_NAME")).append(" ");
							if (rs.getString("OCD_MIDDLE_NAME") != null) {
								stringBuilder.append(rs.getString("OCD_MIDDLE_NAME")).append(" ");
							}
							stringBuilder.append(rs.getString("OCD_LAST_NAME")).append(" ");
							candidateMgmtBean.setCandidateName(stringBuilder.toString());
							candidateMgmtBean.setCandidateCategory(rs.getString("OCTM_CATEGORY_CODE"));
							candidateMgmtBean.setDisciplineType(rs.getString("OTM_TEST_NAME"));
							candidateMgmtBean.setMobileNO(rs.getString("OUM_MOBILE_NO"));
							candidateMgmtBean.setEmailAddress(rs.getString("Oum_EMAIL_ID"));
							candidateMgmtBean.setPaymentStatus("Payment Not Submitted");
							return candidateMgmtBean;
						}
					});
		} catch (Exception e) {
			throw new GenericException(e);
		}

		if (candidateDetailsList != null && !candidateDetailsList.isEmpty()) {
			return candidateDetailsList;
		} else {
			return null;
		}
	}

	@Override
	public void insertEpostPaymentDetails(PaymentBean pb, String applicationNo, Integer paymentKeyByDesc, String tranId) throws Exception {
		int insertDetails = 0;
		try {
			insertDetails = writeJdbcTemplate.update(CandidateQueries.INSERT_E_POST_PAYEMENT_DETAILS,
					new Object[] { paymentKeyByDesc, pb.getOcjm_user_fk(), tranId, "INR", "A", "A", "admin", pb.getOcjm_test_fk(), Long.parseLong(applicationNo) });
		} catch (Exception e) {
			logger.error(e, e);
		}
	}
}
