package com.nseit.generic.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.nseit.generic.models.Users;
import com.nseit.generic.models.WorkExperienceBean;
import com.nseit.generic.models.WorkExperienceDetailsBean;

public interface WorkExperienceService {
	public int isUserExistInWorkExperiance(Users loggedInUser) throws Exception;

	public String getPoliceStationList(WorkExperienceBean workExperienceBean) throws Exception;

	public String getDistrict(WorkExperienceBean workExperienceBean) throws Exception;

	public String getUnit(HttpServletRequest request, WorkExperienceBean workExperienceBean) throws Exception;

	public List<WorkExperienceDetailsBean> getAddEduDetails(Users loggedInUser) throws Exception;

	public WorkExperienceDetailsBean getMphilData(Users loggedInUser) throws Exception;

	public String getPostAppliedFor(Long usrId) throws Exception;

	public List<WorkExperienceDetailsBean> getWorkExperienceDetailsForCandidate(Long usrFk) throws Exception;

	public WorkExperienceBean getWorkExpAddDetails(Users users) throws Exception;

	public String saveWorkExperienceDetails(List<WorkExperienceDetailsBean> workExpFinalLst, Users loggedInUser, WorkExperienceBean workExperienceBean) throws Exception;

	public WorkExperienceDetailsBean getHighestEduData(Users loggedInUser) throws Exception ;

}
