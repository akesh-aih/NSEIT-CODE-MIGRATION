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
import com.nseit.generic.dao.WorkExperienceDao;
import com.nseit.generic.models.Users;
import com.nseit.generic.models.WorkExperienceBean;
import com.nseit.generic.models.WorkExperienceDetailsBean;
import com.nseit.generic.queries.CandidateQueries;
import com.nseit.generic.queries.CommonQueries;
import com.nseit.generic.service.CommonService;
import com.nseit.generic.util.GenericException;

@SuppressWarnings("unchecked")
public class WorkExperienceDaoImpl extends BaseDAO implements WorkExperienceDao {
	CommonService commonService = null;
	private JdbcTemplate writeJdbcTemplate;
	
	public void setWriteJdbcTemplate(JdbcTemplate writeJdbcTemplate) {
		this.writeJdbcTemplate = writeJdbcTemplate;
	}
	@Override
	public List<WorkExperienceDetailsBean> getWorkExperienceDetailsForCandidate(Long userFk) throws Exception {
		List<WorkExperienceDetailsBean> workExperienceDetailsList = null;

		workExperienceDetailsList = getJdbcTemplate().query(CandidateQueries.GET_WORK_EXPERIENCE_DETAILS_FOR_CANDIDATE,
				new RowMapper<WorkExperienceDetailsBean>() {
					public WorkExperienceDetailsBean mapRow(ResultSet rs, int rowNum) throws SQLException {
						WorkExperienceDetailsBean workExperienceDetailsBean = new WorkExperienceDetailsBean();
						workExperienceDetailsBean.setOWE_WE_PK(Integer.parseInt(rs.getString("OWE_WE_PK") == null ? "" : rs.getString("OWE_WE_PK")));
						workExperienceDetailsBean.setArea(rs.getString("OWE_WE_ORGANISATION") == null ? "" : rs.getString("OWE_WE_ORGANISATION"));
						workExperienceDetailsBean.setJobRole(rs.getString("OWE_ROLE") == null ? "" : rs.getString("OWE_ROLE"));
						workExperienceDetailsBean.setFromYear(rs.getString("OWE_YOE_FROM") == null ? "" : rs.getString("OWE_YOE_FROM"));
						workExperienceDetailsBean.setYesNoA(rs.getString("OWE_CURRENT_COMPANY") == null ? "" : rs.getString("OWE_CURRENT_COMPANY"));
						workExperienceDetailsBean.setToYear(rs.getString("OWE_YOE_TO") == null ? "" : rs.getString("OWE_YOE_TO"));
						workExperienceDetailsBean.setWorkduration(rs.getString("OWE_TOTAL_EXPERIENCE") == null ? "" : rs.getString("OWE_TOTAL_EXPERIENCE"));
						workExperienceDetailsBean.setWrkexp_id(rs.getString("OWE_WRKEXP_DOC_ID") == null ? "" : rs.getString("OWE_WRKEXP_DOC_ID"));
						workExperienceDetailsBean.setLastSalary(rs.getString("OWE_LAST_SALARY") == null ? "" : rs.getString("OWE_LAST_SALARY"));
						String[] maxPay = workExperienceDetailsBean.getLastSalary().split(" ");
			            workExperienceDetailsBean.setMaxPayLakh(maxPay[0]);
			            workExperienceDetailsBean.setMaxPayThousnd(maxPay[2]);
			            workExperienceDetailsBean.setOrganizationOthers(rs.getString("OWE_JOB_DESCRIPTION"));
						return workExperienceDetailsBean;
					}
				}, new Object[] { userFk });
		return workExperienceDetailsList;
	}

	@Override
	public WorkExperienceBean getWorkExpAddDetails(Users users) throws Exception {

		List<WorkExperienceBean> workExpAddDetails = null;
		Long userId = 0L;
		if (users != null) {
			userId = users.getUserId();
		}
		try {
			workExpAddDetails = getJdbcTemplate().query(CandidateQueries.GET_WORK_EXP_ADD_DETAILS, new Object[] { userId }, new RowMapper<WorkExperienceBean>() {
				public WorkExperienceBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					WorkExperienceBean workExpAddDetailsBean = new WorkExperienceBean();
					workExpAddDetailsBean.setYesNo(rs.getString("OWEAD_RELEVANT_WORK_EXP"));
					workExpAddDetailsBean.setSponsor(rs.getString("OWEAD_WRKEXPANS"));
					if (rs.getString("OWEAD_TOTAL_EXP_YEARS") == null || rs.getString("OWEAD_TOTAL_EXP_YEARS").equals("")) {
						workExpAddDetailsBean.setYearOfExperience("0");
					} else {
						workExpAddDetailsBean.setYearOfExperience(String.valueOf(rs.getInt("OWEAD_TOTAL_EXP_YEARS")));
					}
					if (rs.getString("OWEAD_TOTAL_EXP_MONTHS") == null || rs.getString("OWEAD_TOTAL_EXP_MONTHS").equals("")) {
						workExpAddDetailsBean.setMonthOfExperience("0");
					} else {
						workExpAddDetailsBean.setMonthOfExperience(String.valueOf(rs.getInt("OWEAD_TOTAL_EXP_MONTHS")));
					}
					if (rs.getString("OWEAD_TOTAL_EXP_DAYS") == null || rs.getString("OWEAD_TOTAL_EXP_DAYS").equals("")) {
						workExpAddDetailsBean.setDayOfExperience("0");
					} else {
						workExpAddDetailsBean.setDayOfExperience(String.valueOf(rs.getInt("OWEAD_TOTAL_EXP_DAYS")));
					}
					return workExpAddDetailsBean;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		if (workExpAddDetails != null && !workExpAddDetails.isEmpty()) {
			return workExpAddDetails.get(0);
		} else {
			return null;
		}
	}

	public int isUserExistInWorkExperiance(Users loggedInUser) throws Exception {
		int count = getJdbcTemplate().queryForObject(CandidateQueries.IS_USER_EXIST_IN_WORK_EXP, new Object[] { loggedInUser.getUserId() },Integer.class);
		return count;
	}

	@Override
	public int isUserExistInWorkExpAddDetails(Users loggedInUser) throws Exception {
		int count = 0;
		try {
			count = getJdbcTemplate().queryForObject(CandidateQueries.IS_USER_EXIST_IN_WORK_EXP_ADD_DETAILS, new Object[] { loggedInUser.getUserId() },Integer.class);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return count;
	}

	@Override
	public int deleteWorkExpForCandidate(Users users) throws Exception {
		int delete = 0;
		try {
			delete = writeJdbcTemplate.update(CandidateQueries.DELETE_WORK_EXP_DETAILS, new Object[] { users.getUserId() });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return delete;
	}

	@Override
	public int deleteWorkExpAddDetails(Users users) throws Exception {
		int delete = 0;
		try {
			delete = writeJdbcTemplate.update(CandidateQueries.DELETE_WORK_EXP_ADD_DETAILS, new Object[] { users.getUserId() });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return delete;
	}

	public int[] insertWorkExperienceDetails(final List<WorkExperienceDetailsBean> workExperienceDetailsList, final Users logginUser, final WorkExperienceBean workExperienceBean)
			throws Exception {
		int[] insertCount = null;
		try {
			DateFormat sdf = new SimpleDateFormat("dd-MMM-YYYY");
			insertCount = writeJdbcTemplate.batchUpdate(CandidateQueries.INSERT_WORK_EXPERIENCE_DETAILS, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					WorkExperienceDetailsBean workExperienceDetailsBean = workExperienceDetailsList.get(i);
					ps.setLong(1, logginUser.getUserId());
					ps.setString(2, workExperienceDetailsBean.getArea());
					ps.setString(3, workExperienceDetailsBean.getJobRole());
					ps.setString(4, workExperienceDetailsBean.getFromYear());
					ps.setString(5, workExperienceDetailsBean.getYesNoA());
					if (workExperienceDetailsBean.getYesNoA().equals("6")) {
						Date date1;
						try {
							String date = "20-Jun-2023";
							date1 = sdf.parse(date);
							String toyear = sdf.format(date1);
							ps.setString(6, toyear);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						ps.setString(6, workExperienceDetailsBean.getToYear());
					}
					ps.setString(7, workExperienceDetailsBean.getWorkduration());
					String maxPay = workExperienceDetailsBean.getMaxPayLakh() + " Lakh "
							+ workExperienceDetailsBean.getMaxPayThousnd() + " Thousand";
					ps.setString(8,maxPay);
					ps.setString(9, workExperienceDetailsBean.getOrganizationOthers());
					ps.setString(10, logginUser.getUsername());
					/*if (workExperienceDetailsBean.getYesNo().equals("6") && (workExperienceDetailsBean.getToYear() == null || workExperienceDetailsBean.getToYear().equals(""))) {
						String date = "31-Mar-2022";
						String toyear = sdf.format(date);
						ps.setString(9, logginUser.getUserId() + "_" + workExperienceDetailsBean.getFromYear() + "_" + toyear);
					} else {
						ps.setString(9, logginUser.getUserId() + "_" + workExperienceDetailsBean.getFromYear() + "_" + workExperienceDetailsBean.getToYear());
					}*/
				}
				@Override
				public int getBatchSize() {
					return workExperienceDetailsList.size();
				}
			});
		} catch (Exception e) {
			logger.fatal(e, e);
			throw new GenericException(e);
		}
		return insertCount;
	}

	@Override
	public int insertWorkExpAddDetails(Users loggedInUser, WorkExperienceBean workExperienceBean) throws Exception {
		int insert = 0;
		try {
			insert = writeJdbcTemplate.update(CandidateQueries.INSERT_WORK_EXP_ADD_DETAILS,
					new Object[] { loggedInUser.getUserId(), workExperienceBean.getYearOfExperience(), workExperienceBean.getMonthOfExperience(),
							workExperienceBean.getDayOfExperience(), loggedInUser.getUsername() });
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e, e);
		}
		return insert;
	}

	public String getPostAppliedFor(Long usrId) throws Exception {
		String PostApplied = null;
		try {
			PostApplied = (String) getJdbcTemplate().queryForObject(CommonQueries.GET_POST_APPLIED_WRKEXP, new Object[] { usrId }, String.class);
		} catch (Exception e) {
			return null;
		}
		return PostApplied;
	}

	public List<WorkExperienceDetailsBean> getAddEduDetails(Users users) throws Exception {
		List<WorkExperienceDetailsBean> workExpDetailsList = null;
		workExpDetailsList = getJdbcTemplate().query(CommonQueries.GET_ADD_EDU_DETAILS, new Object[] { users.getUserFk() },
				new RowMapper<WorkExperienceDetailsBean>() {
					public WorkExperienceDetailsBean mapRow(ResultSet rs, int rowCount) throws SQLException {
						WorkExperienceDetailsBean workExperienceDetailsBean = new WorkExperienceDetailsBean();
						workExperienceDetailsBean.setPhdRegDate(rs.getString("OAED_PHD_REG_DATE"));
						workExperienceDetailsBean.setVivaDate(rs.getString("OAED_DATE_VIVA"));
						workExperienceDetailsBean.setIsPhd(rs.getString("OAED_IS_PHD_CHECKED"));
						workExperienceDetailsBean.setIsNet(rs.getString("OAED_IS_NET_CHECKED"));
						workExperienceDetailsBean.setIsSlet(rs.getString("OAED_IS_SLET_CHECKED"));
						return workExperienceDetailsBean;
					}
				});
		if (!workExpDetailsList.isEmpty())
			return workExpDetailsList;
		else
			return null;
	}

	public WorkExperienceDetailsBean getMphilData(Users users) throws Exception {
		List<WorkExperienceDetailsBean> workExpDetailsList = null;
		workExpDetailsList = getJdbcTemplate().query(CommonQueries.GET_DATA_FOR_MPHIL, new Object[] { users.getUserFk() }, new RowMapper<WorkExperienceDetailsBean>() {
			public WorkExperienceDetailsBean mapRow(ResultSet rs, int rowCount) throws SQLException {
				WorkExperienceDetailsBean workExperienceDetailsBean = new WorkExperienceDetailsBean();
				workExperienceDetailsBean.setMphilPassDate(rs.getString("OACD_YEAR_OF_PASSING") == null ? "" : rs.getString("OACD_YEAR_OF_PASSING"));
				return workExperienceDetailsBean;
			}
		});
		if (!workExpDetailsList.isEmpty())
			return workExpDetailsList.get(0);
		else
			return null;
	}
	
	public WorkExperienceDetailsBean getHighestEduData(Users users) throws Exception {
		List<WorkExperienceDetailsBean> workExpDetailsList = null;
		workExpDetailsList = getJdbcTemplate().query(CommonQueries.GET_DATA_FOR_HIGHEST_EDU, new Object[] { users.getUserFk() },
				new RowMapper<WorkExperienceDetailsBean>() {
					public WorkExperienceDetailsBean mapRow(ResultSet rs, int rowCount) throws SQLException {
						WorkExperienceDetailsBean workExperienceDetailsBean = new WorkExperienceDetailsBean();
						workExperienceDetailsBean.setAcdmPk(rs.getString("OACD_ACDM_FK") == null ? "" : rs.getString("OACD_ACDM_FK"));
						if(workExperienceDetailsBean.getAcdmPk().equals("6")) {
							workExperienceDetailsBean.setHighestEduDate(rs.getString("OACD_PERIOD_OF_STUDY_TO") == null ? "" : rs.getString("OACD_PERIOD_OF_STUDY_TO"));
						} else {
							workExperienceDetailsBean.setHighestEduDate(rs.getString("OACD_YEAR_OF_PASSING") == null ? "" : rs.getString("OACD_YEAR_OF_PASSING"));
						}
						return workExperienceDetailsBean;
					}
				});

		if (!workExpDetailsList.isEmpty())
			return workExpDetailsList.get(0);
		else
			return null;
	}
}