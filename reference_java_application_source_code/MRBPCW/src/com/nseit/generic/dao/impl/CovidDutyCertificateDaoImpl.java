package com.nseit.generic.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.nseit.generic.dao.BaseDAO;
import com.nseit.generic.dao.CovidDutyCertificateDao;
import com.nseit.generic.models.CovidDutyCertDetailsBean;
import com.nseit.generic.models.CovidDutyCertificateBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.models.WorkExperienceBean;
import com.nseit.generic.models.WorkExperienceDetailsBean;
import com.nseit.generic.queries.CandidateQueries;
import com.nseit.generic.util.GenericException;

public class CovidDutyCertificateDaoImpl extends BaseDAO implements CovidDutyCertificateDao {

	private JdbcTemplate writeJdbcTemplate;

	public void setWriteJdbcTemplate(JdbcTemplate writeJdbcTemplate) {
		this.writeJdbcTemplate = writeJdbcTemplate;
	}

	public int isUserExistInCovidDutyCertDetails(Users loggedInUser) throws Exception {
		@SuppressWarnings("deprecation")
		int count = getJdbcTemplate().queryForObject(CandidateQueries.IS_USER_EXIST_IN_COVID_DUTY_CERT, new Object[] { loggedInUser.getUserId() }, Integer.class);
		return count;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public int isUserExistInCovidDutyCertAddDetails(Users loggedInUser) throws Exception {
		int count = 0;
		try {
			count = getJdbcTemplate().queryForObject(CandidateQueries.IS_USER_EXIST_IN_COVID_DUTY_CERT_ADD_DETAILS, new Object[] { loggedInUser.getUserId() },Integer.class);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return count;
	}

	@Override
	public List<CovidDutyCertDetailsBean> getCovidDutyCertiDetails(Long userFk) throws Exception {
		List<CovidDutyCertDetailsBean> covidDutyCertDetailsList = null;

		covidDutyCertDetailsList = getJdbcTemplate().query(CandidateQueries.GET_COVID_DUTY_CERT_DETAILS, new RowMapper<CovidDutyCertDetailsBean>() {
			public CovidDutyCertDetailsBean mapRow(ResultSet rs, int rowNum) throws SQLException {
				CovidDutyCertDetailsBean covidDutyCertDetailsBean = new CovidDutyCertDetailsBean();
				covidDutyCertDetailsBean.setOcdc_cdc_pk(Integer.parseInt(rs.getString("ocdc_cdc_pk") == null ? "" : rs.getString("ocdc_cdc_pk")));
			//	covidDutyCertDetailsBean.setYesNo(rs.getString("ocdc_work_in_covid_yesno") == null ? "" : rs.getString("ocdc_work_in_covid_yesno"));
				covidDutyCertDetailsBean.setInstitutionType(rs.getString("ocdc_institution_type") == null ? "" : rs.getString("ocdc_institution_type"));
				covidDutyCertDetailsBean.setNameOfMedInstitution(rs.getString("ocdc_institution_name") == null ? "" : rs.getString("ocdc_institution_name"));
				covidDutyCertDetailsBean.setDistrictVal(rs.getString("ocdc_district") == null ? "" : rs.getString("ocdc_district"));
				covidDutyCertDetailsBean.setAddressOfInstitute(rs.getString("ocdc_institution_address") == null ? "" : rs.getString("ocdc_institution_address"));
				covidDutyCertDetailsBean.setFromYear(rs.getString("ocdc_period_of_work_from") == null ? "" : rs.getString("ocdc_period_of_work_from"));
				covidDutyCertDetailsBean.setToYear(rs.getString("ocdc_period_of_work_to") == null ? "" : rs.getString("ocdc_period_of_work_to"));
				covidDutyCertDetailsBean.setDurationOfCovidService(rs.getString("ocdc_duration_of_covid_service") == null ? "" : rs.getString("ocdc_duration_of_covid_service"));
				covidDutyCertDetailsBean.setCertificateSignedBy(rs.getString("ocdc_certificate_signed_by") == null ? "" : rs.getString("ocdc_certificate_signed_by"));
				covidDutyCertDetailsBean.setCertiCounterSignedBy(rs.getString("ocdc_certi_counter_signed_by") == null ? "" : rs.getString("ocdc_certi_counter_signed_by"));
				return covidDutyCertDetailsBean;
			}
		}, new Object[] { userFk });
		return covidDutyCertDetailsList;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public CovidDutyCertificateBean getCovidDutyCertiAddDetails(Users users) throws Exception {

		List<CovidDutyCertificateBean> covidDutyCertAddDetails = null;
		Long userId = 0L;
		if (users != null) {
			userId = users.getUserId();
		}
		try {
			covidDutyCertAddDetails = getJdbcTemplate().query(CandidateQueries.GET_COVID_DUTY_CERTI_ADD_DETAILS, new Object[] { userId }, new RowMapper<CovidDutyCertificateBean>() {
				public CovidDutyCertificateBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					CovidDutyCertificateBean covidDutyCertAddDetailsBean = new CovidDutyCertificateBean();
					covidDutyCertAddDetailsBean.setYesNo(rs.getString("ocdcad_work_in_covid_yesno"));
					if (rs.getString("ocdcad_years_of_total_service") == null || rs.getString("ocdcad_years_of_total_service").equals("")) {
						covidDutyCertAddDetailsBean.setYearOfTotalService("0");
					} else {
						covidDutyCertAddDetailsBean.setYearOfTotalService(String.valueOf(rs.getInt("ocdcad_years_of_total_service")));
					}
					if (rs.getString("ocdcad_months_of_total_service") == null || rs.getString("ocdcad_months_of_total_service").equals("")) {
						covidDutyCertAddDetailsBean.setMonthOfTotalService("0");
					} else {
						covidDutyCertAddDetailsBean.setMonthOfTotalService(String.valueOf(rs.getInt("ocdcad_months_of_total_service")));
					}
					if (rs.getString("ocdcad_days_of_total_service") == null || rs.getString("ocdcad_days_of_total_service").equals("")) {
						covidDutyCertAddDetailsBean.setDayOfTotalService("0");
					} else {
						covidDutyCertAddDetailsBean.setDayOfTotalService(String.valueOf(rs.getInt("ocdcad_days_of_total_service")));
					}
					return covidDutyCertAddDetailsBean;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		if (covidDutyCertAddDetails != null && !covidDutyCertAddDetails.isEmpty()) {
			return covidDutyCertAddDetails.get(0);
		} else {
			return null;
		}
	}

	@Override
	public int deleteCovidDutyCertForCandidate(Users users) throws Exception {
		int delete = 0;
		try {
			delete = writeJdbcTemplate.update(CandidateQueries.DELETE_COVID_DUTY_CERT_DETAILS, new Object[] { users.getUserId() });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return delete;
	}

	@Override
	public int[] insertCovidDutyCertDetails(List<CovidDutyCertDetailsBean> covidDutyCertDetailsList, Users logginUser, final CovidDutyCertificateBean covidDutyCertificateBean)
			throws Exception {
		int[] insertCount = null;
		try {
			DateFormat sdf = new SimpleDateFormat("dd-MMM-YYYY");
			insertCount = writeJdbcTemplate.batchUpdate(CandidateQueries.INSERT_COVID_DUTY_CERT_DETAILS, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					CovidDutyCertDetailsBean covidDutyCertDetailsBean = covidDutyCertDetailsList.get(i);
					ps.setLong(1, logginUser.getUserId());
				//	ps.setString(2, covidDutyCertificateBean.getYesNo());
					ps.setString(2, covidDutyCertDetailsBean.getInstitutionType());
					ps.setString(3, covidDutyCertDetailsBean.getNameOfMedInstitution());
					ps.setString(4, covidDutyCertDetailsBean.getDistrictVal());
					ps.setString(5, covidDutyCertDetailsBean.getAddressOfInstitute());
					ps.setString(6, covidDutyCertDetailsBean.getFromYear());
					ps.setString(7, covidDutyCertDetailsBean.getToYear());
					ps.setString(8, covidDutyCertDetailsBean.getDurationOfCovidService());
					ps.setString(9, covidDutyCertDetailsBean.getCertificateSignedBy());
					ps.setString(10, covidDutyCertDetailsBean.getCertiCounterSignedBy());
					 
					ps.setString(11, logginUser.getUsername());
					/*
					 * if (workExperienceDetailsBean.getYesNo().equals("6") && (workExperienceDetailsBean.getToYear() == null || workExperienceDetailsBean.getToYear().equals("")))
					 * { String date = "31-Mar-2022"; String toyear = sdf.format(date); ps.setString(9, logginUser.getUserId() + "_" + workExperienceDetailsBean.getFromYear() + "_"
					 * + toyear); } else { ps.setString(9, logginUser.getUserId() + "_" + workExperienceDetailsBean.getFromYear() + "_" + workExperienceDetailsBean.getToYear()); }
					 */
				}

				@Override
				public int getBatchSize() {
					return covidDutyCertDetailsList.size();
				}
			});
		} catch (Exception e) {
			logger.fatal(e, e);
			throw new GenericException(e);
		}
		return insertCount;
	}
	
	@Override
	public int deleteCovidDutyCertAddDetails(Users users) throws Exception {
		int delete = 0;
		try {
			delete = writeJdbcTemplate.update(CandidateQueries.DELETE_COVID_DUTY_CERTI_ADD_DETAILS, new Object[] { users.getUserId() });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return delete;
	}
	
	@Override
	public int insertCovidDutyCertAddDetails(Users loggedInUser, CovidDutyCertificateBean covidDutyCertificateBean) throws Exception {
		int insert = 0;
		String totalwrkexp = (covidDutyCertificateBean.getYearOfTotalService() + " years " + covidDutyCertificateBean.getMonthOfTotalService() + " months " + covidDutyCertificateBean.getDayOfTotalService() + " days");
		try {
			insert = writeJdbcTemplate.update(CandidateQueries.INSERT_COVID_DUTY_CERTI_ADD_DETAILS,
					new Object[] { loggedInUser.getUserId(),covidDutyCertificateBean.getYesNo(), covidDutyCertificateBean.getYearOfTotalService(), covidDutyCertificateBean.getMonthOfTotalService(),
							covidDutyCertificateBean.getDayOfTotalService(), totalwrkexp, loggedInUser.getUsername() });
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e, e);
		}
		return insert;
	}

}
