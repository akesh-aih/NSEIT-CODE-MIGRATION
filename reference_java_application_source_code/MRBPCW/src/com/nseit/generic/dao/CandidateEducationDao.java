package com.nseit.generic.dao;

import java.util.List;
import java.util.Map;

import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.EducationDetailsBean;
import com.nseit.generic.models.Users;

public interface CandidateEducationDao {

	// For Academic Details Bhushan03.05.2018

	public EducationDetailsBean populateAdditionalEducationDetails(Users users) throws Exception;

	public List<EducationDetailsBean> getCandidateDataForAcademicDetails(CandidateBean candidateBean, Users users) throws Exception;

	public Integer getAcademicDetailsRecordCount(Long userId) throws Exception;

	public int deleteAcademicDetailsEntryForCandidate(List<EducationDetailsBean> candiadteList, Users users, String sslcTamil) throws Exception;

	public int deleteAdditionalAcademicDetailsEntryForCandidate(CandidateBean candidateBean, Users users) throws Exception;

	public int[] insertCandidateAcademicDetails(final List<EducationDetailsBean> candiadteList, final Users users) throws Exception;

	public int insertCandidateAdditionalAcademicDetails(CandidateBean candidateBean, Users users) throws Exception;
	
	public CandidateBean getProgrammeSelectedWithSubject(CandidateBean candidateBean, Long userFk) throws Exception;
}
