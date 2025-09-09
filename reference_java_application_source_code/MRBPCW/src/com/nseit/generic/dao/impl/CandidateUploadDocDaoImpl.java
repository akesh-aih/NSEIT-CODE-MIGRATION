package com.nseit.generic.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.nseit.generic.dao.BaseDAO;
import com.nseit.generic.dao.CandidateUploadDocDao;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.CandidateDocBean;
import com.nseit.generic.models.EducationDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.queries.CandidateQueries;
import com.nseit.generic.util.ConfigurationConstants;

@SuppressWarnings("unchecked")
public class CandidateUploadDocDaoImpl extends BaseDAO implements CandidateUploadDocDao {
	
	private JdbcTemplate writeJdbcTemplate;
	
	public void setWriteJdbcTemplate(JdbcTemplate writeJdbcTemplate) {
		this.writeJdbcTemplate = writeJdbcTemplate;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Integer> getEducationSelectionList(Users users) throws Exception {

		List<Integer> educationList = new ArrayList<Integer>();

		educationList = (List<Integer>) getJdbcTemplate().query(CandidateQueries.SELECT_EDUCATION_LIST, new Object[] { users.getUserFk() }, new ResultSetExtractor() {
			@Override
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Integer> educationList = new ArrayList<Integer>();
				while (rs.next()) {
					educationList.add(rs.getInt("oacd_acdm_fk"));
				}
				return educationList;
			}
		});

		return educationList;
	}
	public List<EducationDetailsBean> getEducationBeanList( Users users) throws Exception {
		List<EducationDetailsBean> educationDetailsBeanList = null;

		educationDetailsBeanList = getJdbcTemplate().query(CandidateQueries.GET_EDU_DETAILS_UPLOAD_DOC,
				new RowMapper<EducationDetailsBean>() {
					public EducationDetailsBean mapRow(ResultSet rs, int rowNum) {
						EducationDetailsBean educationDetailsBean = new EducationDetailsBean();
						try {
							educationDetailsBean.setExamination(rs.getString("oacd_acdm_fk"));
							educationDetailsBean.setUgDeg(rs.getString("oacd_ugdeg_yesno"));
							educationDetailsBean.setPgDeg(rs.getString("oacd_pgdeg_yesno"));
							educationDetailsBean.setPgDipYesNo(rs.getString("oacd_yesno_pgdip"));
							educationDetailsBean.setUgTamilMedium(rs.getString("oacd_tamil_medium_ug"));
						} catch (Exception e) {
							e.printStackTrace();
						}
						return educationDetailsBean;
					}
				}, new Object[] { users.getUserId() });
		return educationDetailsBeanList;
	}
	@Override
	public int deletePreviousDocumentsforAll(String combination, Users loggedInUser) throws Exception {
		int delete = 0;
		if (!combination.equals("")) {
			String query = "delete from oes_candidate_doc where ocd_flag not in (" + combination + ") and ocd_user_fk = ?";
			delete = writeJdbcTemplate.update(query, new Object[] { loggedInUser.getUserId() });

		}

		return delete;
	}

	public List<CandidateDocBean> getDocumentUploaded(Users users) throws Exception {
		List<CandidateDocBean> CandidateDocBeanList = new ArrayList<CandidateDocBean>();

		CandidateDocBeanList = writeJdbcTemplate.query(CandidateQueries.GET_CANDIDATE_DOCUMENTS, new Object[] { users.getUserId() },
				new BeanPropertyRowMapper(CandidateDocBean.class));

		return CandidateDocBeanList;
	}

	@Override
	public int getDocCountInDB(Users loggedInUser, String checkingDocInDB) throws Exception {
		String query = "select count(1) from OES_CANDIDATE_DOC where OCD_USER_FK = ? ";
		int docCount = writeJdbcTemplate.queryForObject(query, new Object[] { loggedInUser.getUserFk() }, Integer.class);
		return docCount;
	}

	public boolean isUserDocExists(Users user, CandidateBean candidateBean) throws Exception {
		boolean isUserExists = false;
		String IS_CANDIDATE_EXIST_FOR_DOC = "SELECT count(*) FROM OES_CANDIDATE_DOC WHERE OCD_USER_FK = ? AND OCD_FLAG = ?";
		int docCount = writeJdbcTemplate.queryForObject(IS_CANDIDATE_EXIST_FOR_DOC, new Object[] { user.getUserId(), candidateBean.getOcdFlagValue() }, Integer.class);

		if (docCount > 0) {
			isUserExists = true;
		}
		return isUserExists;
	}

	public int updateCandidateDocuments(String candidateDocPk, String docId, String fileName, byte[] file, String checkbox, Users loggedInUser) throws Exception {
		int recordCount = writeJdbcTemplate.update(CandidateQueries.UPDATE_CANDIDATE_DOCUMENT,
				new Object[] { fileName, docId, file, loggedInUser.getUsername(), checkbox, Integer.parseInt(candidateDocPk) });

		return recordCount;
	}

	public int insertCandidateDocuments(String fileName, String docId, byte[] file, String flagValue, String getCheckCandidateAcceptance1, Users loggedInUser) throws Exception {
		int recordCount = writeJdbcTemplate.update(CandidateQueries.INSERT_CANDIDATE_DOCUMENT,
				new Object[] { loggedInUser.getUserId(), docId, fileName, file, flagValue, loggedInUser.getUsername(), getCheckCandidateAcceptance1 });
		return recordCount;
	}

	@Override
	public int deleteDocMissingOnPhysical(String combination, Users loggedInUser) throws Exception {
		int delete = 0;
		if (!combination.equals("")) {
			String query = "delete from oes_candidate_doc where ocd_user_fk = ? and ocd_flag = ?";
			delete = writeJdbcTemplate.update(query, new Object[] { loggedInUser.getUserId(), combination });
		}

		return delete;
	}

}
