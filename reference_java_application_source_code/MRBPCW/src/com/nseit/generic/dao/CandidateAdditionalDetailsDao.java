package com.nseit.generic.dao;

import com.nseit.generic.models.AdditionalDetailsBean;
import com.nseit.generic.models.Users;

public interface CandidateAdditionalDetailsDao {

	public AdditionalDetailsBean getCandidateAdditionalDetails(Long userId) throws Exception;

	public int saveCandidateAdditionalDetails(AdditionalDetailsBean additionalDetailsBean, Users loggedInUsers) throws Exception;

	public Integer checkIfCandidateAddionaldetailsExist(Long userId) throws Exception;

	
}
