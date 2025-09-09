package com.nseit.generic.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nseit.generic.models.AddressBean;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.PersonalDetailsBean;
import com.nseit.generic.models.Users;

public interface CandidatePersonalDetailsService {
	public ErrorCodeEnum saveCandidateDetails(CandidateBean candidateBean, AddressBean addressBean, PersonalDetailsBean personalDetailsBean, Users loggedInUsers,
			Integer recordCount, Map<String, Object> sessionAttributes, HttpServletRequest request) throws Exception;

	public enum ErrorCodeEnum {
		UPDATE_CANDIDATE_STAGE, ERROR

	}

	public enum ErrorCodeEnumm

	{
		UPDATE_CANDIDATE_STAGE, ERROR

	}

	public String getCandidateDetails(Users users, CandidateBean candidateBean, Map<String, Object> sessionAttributes, HttpServletRequest request) throws Exception;

	public Map<Integer, String> getOtherNativeList() throws Exception;
}
