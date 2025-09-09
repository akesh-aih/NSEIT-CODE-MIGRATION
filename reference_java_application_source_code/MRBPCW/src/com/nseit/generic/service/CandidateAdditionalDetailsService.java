package com.nseit.generic.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.CandidatePersonalDetailsService.ErrorCodeEnum;

public interface CandidateAdditionalDetailsService {

	public String getCandidateAdditionalDetails(Users users, CandidateBean candidateBean, Map<String, Object> sessionAttributes, HttpServletRequest request) throws Exception;

	public ErrorCodeEnum saveCandidateAdditionalDetails(CandidateBean candidateBean, Users loggedInUser, Map<String, Object> sessionAttributes, HttpServletRequest request)
			throws Exception;

}
