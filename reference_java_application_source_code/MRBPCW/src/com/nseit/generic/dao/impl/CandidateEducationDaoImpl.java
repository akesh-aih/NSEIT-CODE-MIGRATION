package com.nseit.generic.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.nseit.generic.dao.BaseDAO;
import com.nseit.generic.dao.CandidateEducationDao;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.EducationDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.queries.CandidateQueries;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericException;

import software.amazon.awssdk.utils.StringUtils;

@SuppressWarnings("unchecked")
public class CandidateEducationDaoImpl extends BaseDAO implements CandidateEducationDao {

	// For Academic Details Bhushan03.05.2018
	private JdbcTemplate writeJdbcTemplate;
	
	public void setWriteJdbcTemplate(JdbcTemplate writeJdbcTemplate) {
		this.writeJdbcTemplate = writeJdbcTemplate;
	}

	public List<EducationDetailsBean> getCandidateDataForAcademicDetails(CandidateBean candidateBean, Users users) throws Exception {
		List<EducationDetailsBean> educationDetailsBeanList = null;

		educationDetailsBeanList = getJdbcTemplate().query(CandidateQueries.GET_CANDIATE_ACADEMIC_DETAILS_USER_ID, 
				new RowMapper<EducationDetailsBean>() {
					public EducationDetailsBean mapRow(ResultSet rs, int rowNum) {
						EducationDetailsBean educationDetailsBean = new EducationDetailsBean();
						try {
							educationDetailsBean.setExamination(rs.getString("oacd_acdm_fk"));
							educationDetailsBean.setExamination(ConfigurationConstants.getInstance().getAcademicDetailsForPk(educationDetailsBean.getExamination()));
							educationDetailsBean.setDegree(rs.getString("oacd_degree_fk"));
							educationDetailsBean.setEducationPk(rs.getString("oacd_acdm_fk"));
							educationDetailsBean.setUniversity(rs.getString("oacd_university"));
							educationDetailsBean.setUniversityOth(rs.getString("oacd_university_other"));
							educationDetailsBean.setDateOfPassing(rs.getString("oacd_year_of_passing"));
							educationDetailsBean.setInstitution(rs.getString("oacd_institution"));
							educationDetailsBean.setUgYesNo(rs.getString("oacd_ugdeg_yesno"));
							educationDetailsBean.setPgYesNo(rs.getString("oacd_pgdeg_yesno"));
							educationDetailsBean.setDipMarksYesNo(rs.getString("oacd_dipmarks_yesno"));
							educationDetailsBean.setPrdOfStudyFrm(rs.getString("oacd_prd_of_study_from"));
							educationDetailsBean.setPrdOfStudyTo(rs.getString("oacd_prd_of_study_to"));
							educationDetailsBean.setDuration(rs.getString("oacd_duration_of_study"));
							educationDetailsBean.setTotalMarks(rs.getString("oacd_total_marks_agg"));
							educationDetailsBean.setObtainedMarks(rs.getString("oacd_obtained_marks_agg"));
							educationDetailsBean.setPercentage(rs.getString("oacd_percentage"));
							educationDetailsBean.setMedOfInstruction(rs.getString("oacd_med_of_inst"));
							educationDetailsBean.setTamilLang(rs.getString("oacd_tamil_lang"));
							educationDetailsBean.setTamilMedium(rs.getString("oacd_tamil_medium"));
							educationDetailsBean.setPstmPreference(rs.getString("oacd_pstm_pref"));
							
							educationDetailsBean.setSpecialization(rs.getString("oacd_specialization"));
							educationDetailsBean.setPgDipYesNo(rs.getString("oacd_yesno_pgdip"));
							educationDetailsBean.setPgDipSpecialization(rs.getString("oacd_specialization_pgdip"));
							educationDetailsBean.setPgDipDateofpassing(rs.getString("oacd_dateofpass_pgdip"));
							educationDetailsBean.setUgTamilMedium(rs.getString("oacd_tamil_medium_ug"));
							
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						return educationDetailsBean;
					}
				},new Object[] { users.getUserId() });
		return educationDetailsBeanList;
	}

	public Integer getAcademicDetailsRecordCount(Long userId) throws Exception {
		Integer userName = null;
		userName = (Integer) getJdbcTemplate().queryForObject(CandidateQueries.CHECK_ACADEMIC_DETAILS, new Object[] { userId }, Integer.class);
		return userName;
	}

	public int deleteAcademicDetailsEntryForCandidate(List<EducationDetailsBean> candiadteList, Users users, String sslcTamil) throws Exception {
		int delete = 0;
		int save[] = null;
		int returnVal = 0;
		delete = writeJdbcTemplate.update(CandidateQueries.DELETE_ACADEMIC_DETAILS_ENTRY, new Object[] { users.getUserId() });
		if (delete > 0) {
			save = insertCandidateAcademicDetails(candiadteList, users);
			if (save != null && save.length > 0) {
				returnVal = 1;
			} else {
				returnVal = 0;
			}
		}
		return returnVal;
	}

	public int[] insertCandidateAcademicDetails(final List<EducationDetailsBean> candiadteList, final Users users)
			throws Exception {
		int[] updateCounts = null;
		updateCounts = writeJdbcTemplate.batchUpdate(CandidateQueries.INSERT_CANDIDATE_ACADEMIC_DETAILS,
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement pStatement, int currBatchIndex) throws SQLException {
						pStatement.setInt(1, Integer.parseInt(candiadteList.get(currBatchIndex).getDegreeSelected()));
						
						pStatement.setLong(2, users.getUserId());
						
						if (StringUtils.isNotBlank(candiadteList.get(currBatchIndex).getExamination())) {
							pStatement.setInt(3, Integer.parseInt(candiadteList.get(currBatchIndex).getExamination()));
						}
						
						if (StringUtils.isNotBlank(candiadteList.get(currBatchIndex).getUniversity())) {
							pStatement.setString(4, candiadteList.get(currBatchIndex).getUniversity());
						} else {
							pStatement.setString(4, null);
						}
						
						if (StringUtils.isNotBlank(candiadteList.get(currBatchIndex).getUniversityOth())) {
							pStatement.setString(5, candiadteList.get(currBatchIndex).getUniversityOth());
						} else {
							pStatement.setString(5, null);
						}
						
						if (StringUtils.isNotBlank(candiadteList.get(currBatchIndex).getPrdOfStudyFrm())) {
							pStatement.setString(6, candiadteList.get(currBatchIndex).getPrdOfStudyFrm());
						} else {
							pStatement.setString(6, null);
						}

						if (StringUtils.isNotBlank(candiadteList.get(currBatchIndex).getPrdOfStudyTo())) {
							pStatement.setString(7, candiadteList.get(currBatchIndex).getPrdOfStudyTo());
						} else {
							pStatement.setString(7, null);
						}

						if (StringUtils.isNotBlank(candiadteList.get(currBatchIndex).getDuration())) {
							pStatement.setString(8, candiadteList.get(currBatchIndex).getDuration());
						} else {
							pStatement.setString(8, null);
						}

						if (StringUtils.isNotBlank(candiadteList.get(currBatchIndex).getInstitution())) {
							pStatement.setString(9, candiadteList.get(currBatchIndex).getInstitution());
						} else {
							pStatement.setString(9, null);
						}
						
						if (StringUtils.isNotBlank(candiadteList.get(currBatchIndex).getDateOfPassing())) {
							pStatement.setString(10, candiadteList.get(currBatchIndex).getDateOfPassing());
						} else {
							pStatement.setString(10, null);
						}
						
						if (StringUtils.isNotBlank(candiadteList.get(currBatchIndex).getPercentage())) {
							pStatement.setString(11, candiadteList.get(currBatchIndex).getPercentage());
						} else {
							pStatement.setString(11, null);
						}
						
						if (StringUtils.isNotBlank(candiadteList.get(currBatchIndex).getDipMarksYesNo())) {
							pStatement.setString(12, candiadteList.get(currBatchIndex).getDipMarksYesNo());
						} else {
							pStatement.setString(12, null);
						}

						if (StringUtils.isNotBlank(candiadteList.get(currBatchIndex).getTotalMarks())) {
							pStatement.setString(13, candiadteList.get(currBatchIndex).getTotalMarks());
						} else {
							pStatement.setString(13, null);
						}

						if (StringUtils.isNotBlank(candiadteList.get(currBatchIndex).getObtainedMarks())) {
							pStatement.setString(14, candiadteList.get(currBatchIndex).getObtainedMarks());
						} else {
							pStatement.setString(14, null);
						}
						
						if (StringUtils.isNotBlank(candiadteList.get(currBatchIndex).getMedOfInstruction())) {
							pStatement.setString(15, candiadteList.get(currBatchIndex).getMedOfInstruction());
						} else {
							pStatement.setString(15, null);
						}

						if (StringUtils.isNotBlank(candiadteList.get(currBatchIndex).getTamilLang())) {
							pStatement.setString(16, candiadteList.get(currBatchIndex).getTamilLang());
						} else {
							pStatement.setString(16, null);
						}
						
						if (StringUtils.isNotBlank(candiadteList.get(currBatchIndex).getUgYesNo())) {
							pStatement.setString(17, candiadteList.get(currBatchIndex).getUgYesNo());
						} else {
							pStatement.setString(17, null);
						}
						
						if (StringUtils.isNotBlank(candiadteList.get(currBatchIndex).getPgYesNo())) {
							pStatement.setString(18, candiadteList.get(currBatchIndex).getPgYesNo());
						} else {
							pStatement.setString(18, null);
						}
						
						if (StringUtils.isNotBlank(candiadteList.get(currBatchIndex).getSpecialization())) {
							pStatement.setString(19, candiadteList.get(currBatchIndex).getSpecialization());
						} else {
							pStatement.setString(19, null);
						}
						
						if (StringUtils.isNotBlank(candiadteList.get(currBatchIndex).getPgDipYesNo())) {
							pStatement.setString(20, candiadteList.get(currBatchIndex).getPgDipYesNo());
						} else {
							pStatement.setString(20, null);
						}

						if (StringUtils.isNotBlank(candiadteList.get(currBatchIndex).getPgDipSpecialization())) {
							pStatement.setString(21, candiadteList.get(currBatchIndex).getPgDipSpecialization());
						} else {
							pStatement.setString(21, null);
						}

						if (StringUtils.isNotBlank(candiadteList.get(currBatchIndex).getPgDipDateofpassing())) {
							pStatement.setString(22, candiadteList.get(currBatchIndex).getPgDipDateofpassing());
						} else {
							pStatement.setString(22, null);
						}			
						
						if (StringUtils.isNotBlank(candiadteList.get(currBatchIndex).getPstmPreference())) {
							pStatement.setString(23, candiadteList.get(currBatchIndex).getPstmPreference());
						} else {
							pStatement.setString(23, null);
						}

						if (StringUtils.isNotBlank(candiadteList.get(currBatchIndex).getTamilMedium())) {
							pStatement.setString(24, candiadteList.get(currBatchIndex).getTamilMedium());
						} else {
							pStatement.setString(24, null);
						}	
						
						if (StringUtils.isNotBlank(candiadteList.get(currBatchIndex).getUgTamilMedium())) {
							pStatement.setString(25, candiadteList.get(currBatchIndex).getUgTamilMedium());
						} else {
							pStatement.setString(25, null);
						}		

						pStatement.setString(26, users.getUsername());
					}

					@Override
					public int getBatchSize() {
						return candiadteList.size();
					}
				});

		return updateCounts;
	}

		@Override
	public int deleteAdditionalAcademicDetailsEntryForCandidate(CandidateBean candidateBean, Users users) throws Exception {
		int delete = 0;
		int save = 0;
		delete = writeJdbcTemplate.update(CandidateQueries.DELETE_ADDITIONAL_ACADEMIC_DETAILS_ENTRY, new Object[] { users.getUserId() });
		if (delete > 0) {
			save = insertCandidateAdditionalAcademicDetails(candidateBean, users);
		}
		return save;
	}

	public int insertCandidateAdditionalAcademicDetails(CandidateBean candidateBean, Users users) throws Exception {
		int insert = 0;

		insert = writeJdbcTemplate.update(CandidateQueries.INSERT_ADDITIONAL_EDUCATION_DETAILS,
				new Object[] { users.getUserId(), candidateBean.getDoeacc(), candidateBean.getTerriArmy(), candidateBean.getCertiB(), users.getUsername(), });
		return insert;
	}

	@Override
	public EducationDetailsBean populateAdditionalEducationDetails(Users users) throws Exception {

		List<EducationDetailsBean> AddEduDetails = null;
		try {
			AddEduDetails = getJdbcTemplate().query(CandidateQueries.SELECT_ADDITIONAL_EDUCATION_DETAILS, new Object[] { users.getUserId() },
					new RowMapper<EducationDetailsBean>() {
						public EducationDetailsBean mapRow(ResultSet rs, int rowCount) throws SQLException {
							EducationDetailsBean educationDetailsBean = new EducationDetailsBean();

							educationDetailsBean.setDoeacc(rs.getString("OAED_DOEACC"));
							educationDetailsBean.setTerriArmy(rs.getString("OAED_TERRI_ARMY"));
							educationDetailsBean.setCertiB(rs.getString("OAED_BCERTI"));

							return educationDetailsBean;
						}
					});
		} catch (Exception e) {
			throw new GenericException(e);
		}

		if (AddEduDetails != null && !AddEduDetails.isEmpty()) {
			return AddEduDetails.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public CandidateBean getProgrammeSelectedWithSubject(CandidateBean candidateBean, Long userFk) throws Exception {
		
		List<CandidateBean> candidateBean1 = null;
		try {
			candidateBean1 =  getJdbcTemplate().query(CandidateQueries.SELECT_DISCIPLINE_AND_SUBJECT, new Object[] { userFk },
					new RowMapper<CandidateBean>() {
						public CandidateBean mapRow(ResultSet rs, int rowCount) throws SQLException {
							CandidateBean candidateBean = new CandidateBean();
							candidateBean.setDisciplinName(rs.getString("ocd_discipline"));
							candidateBean.setShastraSubject(rs.getString("ocd_shastrasubject"));
							candidateBean.setPhdSpecialization(rs.getString("ocd_phdspecialization"));
							candidateBean.setCategoryVal(rs.getInt("ocd_category_fk")+"");
							
							return candidateBean;
						}
					});
		} catch (Exception e) {
			throw new GenericException(e);
		}

		
		return candidateBean1.get(0);
	}
}
