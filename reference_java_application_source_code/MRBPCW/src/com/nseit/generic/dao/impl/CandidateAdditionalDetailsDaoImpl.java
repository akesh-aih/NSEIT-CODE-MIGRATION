package com.nseit.generic.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.nseit.generic.dao.BaseDAO;
import com.nseit.generic.dao.CandidateAdditionalDetailsDao;
import com.nseit.generic.models.AdditionalDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.models.WorkExperienceBean;
import com.nseit.generic.queries.CandidateQueries;
import com.nseit.generic.util.GenericException;

@SuppressWarnings("unchecked")
public class CandidateAdditionalDetailsDaoImpl extends BaseDAO implements CandidateAdditionalDetailsDao {

private JdbcTemplate writeJdbcTemplate;
	
	public void setWriteJdbcTemplate(JdbcTemplate writeJdbcTemplate) {
		this.writeJdbcTemplate = writeJdbcTemplate;
	}
	
	@Override
	public AdditionalDetailsBean getCandidateAdditionalDetails(Long userId) throws Exception {

		List<AdditionalDetailsBean> getAdditionalDetailsList = null;

		getAdditionalDetailsList = getJdbcTemplate().query(CandidateQueries.SELECT_CANDIDATE_ADDITIONAL_DETAILS, new Object[] { userId },
				new RowMapper<AdditionalDetailsBean>() {
					public AdditionalDetailsBean mapRow(ResultSet rs, int rowCount) throws SQLException {
						AdditionalDetailsBean additionalDetailsBean = new AdditionalDetailsBean();

						additionalDetailsBean.setAcademicAward(rs.getString("ocad_academicaward"));
						additionalDetailsBean.setAdvertisement(rs.getString("ocad_advertisement"));
						additionalDetailsBean.setAppliedInPast(rs.getString("ocad_appliedinpast"));
						additionalDetailsBean.setYearsOfApply(rs.getString("ocad_yearsofapply"));
						additionalDetailsBean.setReasonForNotJoining(rs.getString("ocad_reasonfornotjoining"));
						additionalDetailsBean.setStmtOfPurpose(rs.getString("ocad_stmtofpurpose"));
						additionalDetailsBean.setOtherInfo(rs.getString("ocad_otherinfo"));
						additionalDetailsBean.setRef1Name(rs.getString("ocad_ref1name"));
						additionalDetailsBean.setRef1Desig(rs.getString("ocad_ref1desig"));
						additionalDetailsBean.setRef1IsAcademician(rs.getString("ocad_ref1isacademician"));
						additionalDetailsBean.setRef1Add1(rs.getString("ocad_ref1add1"));
						additionalDetailsBean.setRef1Add2(rs.getString("ocad_ref1add2"));
						additionalDetailsBean.setRef1State(rs.getString("ocad_ref1state"));
						additionalDetailsBean.setRef1District(rs.getString("ocad_ref1district"));
						additionalDetailsBean.setRef1City(rs.getString("ocad_ref1city"));
						additionalDetailsBean.setRef1Pincode(rs.getString("ocad_ref1pincode"));
						additionalDetailsBean.setRef1Contact(rs.getString("ocad_ref1contact"));
						additionalDetailsBean.setRef2Name(rs.getString("ocad_ref2name"));
						additionalDetailsBean.setRef2Desig(rs.getString("ocad_ref2desig"));
						additionalDetailsBean.setRef2IsAcademician(rs.getString("ocad_ref2isacademician"));
						additionalDetailsBean.setRef2Add1(rs.getString("ocad_ref2add1"));
						additionalDetailsBean.setRef2Add2(rs.getString("ocad_ref2add2"));
						additionalDetailsBean.setRef2State(rs.getString("ocad_ref2state"));
						additionalDetailsBean.setRef2District(rs.getString("ocad_ref2district"));
						additionalDetailsBean.setRef2City(rs.getString("ocad_ref2city"));
						additionalDetailsBean.setRef2Pincode(rs.getString("ocad_ref2pincode"));
						additionalDetailsBean.setRef2Contact(rs.getString("ocad_ref2contact"));
						additionalDetailsBean.setRef1EmailAddress(rs.getString("ocad_ref1email"));
						additionalDetailsBean.setRef2EmailAddress(rs.getString("ocad_ref2email"));

						return additionalDetailsBean;
					}
				});
		if (getAdditionalDetailsList != null && !getAdditionalDetailsList.isEmpty()) {
			return getAdditionalDetailsList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public int saveCandidateAdditionalDetails(AdditionalDetailsBean additionalDetailsBean, Users loggedInUser) throws Exception {
		int saveAdditionalDetails = 0;

		int count = checkIfCandidateAddionaldetailsExist(loggedInUser.getUserId());

		if (count == 0) {
			saveAdditionalDetails = writeJdbcTemplate.update(CandidateQueries.INSERT_CANDIDATE_ADDITIONAL_DETAILS,
					new Object[] { loggedInUser.getUserId(), additionalDetailsBean.getAcademicAward(),
							additionalDetailsBean.getAdvertisement(), additionalDetailsBean.getAppliedInPast(), additionalDetailsBean.getYearsOfApply(),
							additionalDetailsBean.getReasonForNotJoining(), additionalDetailsBean.getStmtOfPurpose(), additionalDetailsBean.getOtherInfo(),
							additionalDetailsBean.getRef1Name(), additionalDetailsBean.getRef1Desig(), additionalDetailsBean.getRef1IsAcademician(),
							additionalDetailsBean.getRef1Add1(), additionalDetailsBean.getRef1Add2(), additionalDetailsBean.getRef1State(), additionalDetailsBean.getRef1District(),
							additionalDetailsBean.getRef1City(), additionalDetailsBean.getRef1Pincode(), additionalDetailsBean.getRef1Contact(),additionalDetailsBean.getRef1EmailAddress(),
							additionalDetailsBean.getRef2Name(), additionalDetailsBean.getRef2Desig(), additionalDetailsBean.getRef2IsAcademician(),
							additionalDetailsBean.getRef2Add1(), additionalDetailsBean.getRef2Add2(), additionalDetailsBean.getRef2State(), additionalDetailsBean.getRef2District(),
							additionalDetailsBean.getRef2City(), additionalDetailsBean.getRef2Pincode(), additionalDetailsBean.getRef2Contact(),additionalDetailsBean.getRef2EmailAddress() });

		} else if (count > 0) {
			saveAdditionalDetails = writeJdbcTemplate.update(CandidateQueries.UPDATE_CANDIDATE_ADDITIONAL_DETAILS,
					new Object[] { additionalDetailsBean.getAcademicAward(), additionalDetailsBean.getAdvertisement(),
							additionalDetailsBean.getAppliedInPast(), additionalDetailsBean.getYearsOfApply(), additionalDetailsBean.getReasonForNotJoining(),
							additionalDetailsBean.getStmtOfPurpose(), additionalDetailsBean.getOtherInfo(), additionalDetailsBean.getRef1Name(),
							additionalDetailsBean.getRef1Desig(), additionalDetailsBean.getRef1IsAcademician(), additionalDetailsBean.getRef1Add1(),
							additionalDetailsBean.getRef1Add2(), additionalDetailsBean.getRef1State(), additionalDetailsBean.getRef1District(), additionalDetailsBean.getRef1City(),
							additionalDetailsBean.getRef1Pincode(), additionalDetailsBean.getRef1Contact(), additionalDetailsBean.getRef2Name(),
							additionalDetailsBean.getRef2Desig(), additionalDetailsBean.getRef2IsAcademician(), additionalDetailsBean.getRef2Add1(),
							additionalDetailsBean.getRef2Add2(), additionalDetailsBean.getRef2State(), additionalDetailsBean.getRef2District(), additionalDetailsBean.getRef2City(),
							additionalDetailsBean.getRef2Pincode(), additionalDetailsBean.getRef2Contact(), additionalDetailsBean.getRef1EmailAddress(),additionalDetailsBean.getRef2EmailAddress(),loggedInUser.getUserId() });
		}
		return saveAdditionalDetails;
	}

	@Override
	public Integer checkIfCandidateAddionaldetailsExist(Long userId) throws Exception {
		Integer count = 0;
		count = getJdbcTemplate().queryForObject(CandidateQueries.CHECK_IF_ADDITIONAL_EXISTS, new Object[] { userId }, Integer.class);
		return count;
	}

	

}
