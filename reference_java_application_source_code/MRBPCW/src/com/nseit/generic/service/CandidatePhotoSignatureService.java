package com.nseit.generic.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.Users;

public interface CandidatePhotoSignatureService {
	public CandidateSignatureServiceConstants getUploadedSignature(Users loggedInUser, CandidateBean candidateBean, HttpServletRequest request) throws Exception;

	public boolean checkIfSignatureUploaded(Users users) throws Exception;

	public CandidateSignatureServiceConstants saveCandidateSignature(Users loggedInUser, CandidateBean candidateBean, Map<String, Object> requestAttributes,
			HttpServletRequest request) throws Exception;

	public CandidateSignatureServiceConstants saveCandidatePhoto(Users loggedInUser, CandidateBean candidateBean, Map<String, Object> requestAttributes, HttpServletRequest request)
			throws Exception;

	public int updateCandidatePhotoStatus(CandidateBean candidateBean, Users loggedInUser, String flag) throws Exception;

	public int insertCandidateImages(CandidateBean candidateBean, Users loggedInUser) throws Exception;

	public int insertCandidateSignature(CandidateBean candidateBean, Users loggedInUser) throws Exception;

	public CandidateSignatureServiceConstants getUploadedPhoto(Users loggedInUser, CandidateBean candidateBean, HttpServletRequest request) throws Exception;

	public boolean checkIfImageUploaded(Users users) throws Exception;

	public enum CandidateSignatureServiceConstants {
		FILE_UPLOAD_FAILE, UPLOAD_SIGNATURE, SUCCESS, UPLOAD_PHOTOGRAPH
	}
}
