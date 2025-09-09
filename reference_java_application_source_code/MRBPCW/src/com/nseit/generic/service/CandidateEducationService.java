package com.nseit.generic.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.CandidatePersonalDetailsService.ErrorCodeEnum;

public interface CandidateEducationService {

	public ErrorCodeEnum saveEducationalDetails(Users users, CandidateBean candidateBean, Map<String, Object> sessionAttributes, HttpServletRequest request) throws Exception;

	String getCandidateEducationalDetailsPage(Users loggedInUser, CandidateBean candidateBean, Map<String, Object> sessionAttributes, HttpServletRequest request) throws Exception;

}
