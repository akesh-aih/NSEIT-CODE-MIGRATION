package com.nseit.generic.dao;

import com.nseit.generic.models.AddressBean;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.PersonalDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.util.GenericException;

public interface CandidatePersonalDetailsDAO {
	public int saveCandidateDetails(CandidateBean candidateBean, AddressBean addressBean, PersonalDetailsBean personalDetailsBean, Users loggedInUser) throws Exception;

	public int updateCandidateDetails(CandidateBean candidateBean, Users loggedInUser) throws GenericException;

	public CandidateBean getCandidateDetails(Users loggedInUser, CandidateBean candidateBean) throws Exception;

}