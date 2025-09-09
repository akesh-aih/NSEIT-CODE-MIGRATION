package com.nseit.generic.dao;

import java.util.List;

import com.nseit.generic.models.Users;
import com.nseit.generic.models.WorkExperienceBean;
import com.nseit.generic.models.WorkExperienceDetailsBean;

public interface WorkExperienceDao {
	public int isUserExistInWorkExperiance(Users loggedInUser) throws Exception;

	public List<WorkExperienceDetailsBean> getAddEduDetails(Users loggedInUser) throws Exception;

	public WorkExperienceDetailsBean getMphilData(Users loggedInUser) throws Exception;

	public String getPostAppliedFor(Long usrId) throws Exception;

	public List<WorkExperienceDetailsBean> getWorkExperienceDetailsForCandidate(Long userFk) throws Exception;

	public WorkExperienceBean getWorkExpAddDetails(Users users) throws Exception;

	public int isUserExistInWorkExpAddDetails(Users loggedInUser) throws Exception;

	public int deleteWorkExpForCandidate(Users users) throws Exception;

	public int deleteWorkExpAddDetails(Users users) throws Exception;

	public int insertWorkExpAddDetails(Users loggedInUser, WorkExperienceBean workExperienceBean) throws Exception;

	public int[] insertWorkExperienceDetails(final List<WorkExperienceDetailsBean> workExperienceDetailsList, final Users logginUser, final WorkExperienceBean workExperienceBean)
			throws Exception;

	public WorkExperienceDetailsBean getHighestEduData(Users loggedInUser) throws Exception ;
}
