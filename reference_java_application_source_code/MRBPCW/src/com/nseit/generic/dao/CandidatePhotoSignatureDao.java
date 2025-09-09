package com.nseit.generic.dao;

import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.Users;

public interface CandidatePhotoSignatureDao {
	public boolean checkIfSignatureUploaded(Users users) throws Exception;

	public int insertCandidateSignature(CandidateBean candidateBean, Users loggedInUser) throws Exception;

	public boolean checkIfImageUploaded(Users users) throws Exception;

	public int insertCandidateImages(CandidateBean candidateBean, Users loggedInUser) throws Exception;

	public int updateCandidatePhotoStatus(CandidateBean candidateBean, Users loggedInUser, String flag) throws Exception;
}
