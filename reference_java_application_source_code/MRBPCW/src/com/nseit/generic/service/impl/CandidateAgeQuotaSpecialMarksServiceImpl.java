package com.nseit.generic.service.impl;

import com.nseit.generic.dao.CandidateAgeQuotaSpecialMarksDao;
import com.nseit.generic.dao.CandidateDao;
import com.nseit.generic.dao.CandidatePersonalDetailsDAO;
import com.nseit.generic.dao.CommonDao;
import com.nseit.generic.dao.WorkExperienceDao;
import com.nseit.generic.service.CandidateAgeQuotaSpecialMarksService;

public class CandidateAgeQuotaSpecialMarksServiceImpl implements CandidateAgeQuotaSpecialMarksService {
	private CandidateDao candidateDao;
	private WorkExperienceDao workExperienceDao;
	private CandidatePersonalDetailsDAO candidatePersonalDetailsDao;
	private CommonDao commonDao;
	private CandidateAgeQuotaSpecialMarksDao candidateAgeQuotaSpecialMarksDao;

	public void setCandidateAgeQuotaSpecialMarksDao(CandidateAgeQuotaSpecialMarksDao candidateAgeQuotaSpecialMarksDao) {
		this.candidateAgeQuotaSpecialMarksDao = candidateAgeQuotaSpecialMarksDao;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public void setCandidatePersonalDetailsDao(CandidatePersonalDetailsDAO candidatePersonalDetailsDao) {
		this.candidatePersonalDetailsDao = candidatePersonalDetailsDao;
	}

	public void setCandidateDao(CandidateDao candidateDao) {
		this.candidateDao = candidateDao;
	}

	public void setWorkExperienceDao(WorkExperienceDao workExperienceDao) {
		this.workExperienceDao = workExperienceDao;
	}
}
