package com.nseit.generic.dao;

import java.util.List;

import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.CandidateDocBean;
import com.nseit.generic.models.EducationDetailsBean;
import com.nseit.generic.models.Users;

public interface CandidateUploadDocDao {
	public List<Integer> getEducationSelectionList(Users users) throws Exception;
  public List<EducationDetailsBean> getEducationBeanList(Users users) throws Exception;
  public int deletePreviousDocumentsforAll(String combination, Users loggedInUser)throws Exception;
  public List<CandidateDocBean> getDocumentUploaded(Users users) throws Exception;
  public int getDocCountInDB(Users loggedInUser, String checkingDocInDB) throws Exception;
  
  public boolean isUserDocExists(Users user,CandidateBean candidateBean) throws Exception;
  public int updateCandidateDocuments(String candidateDocPk,String docId,String fileName,byte[] file,String checkbox, Users loggedInUser) throws Exception;
  public int insertCandidateDocuments(String fileName,String docId,byte[] file,String flagValue,String getCheckCandidateAcceptance1,Users loggedInUser) throws Exception;

  public int deleteDocMissingOnPhysical(String combination, Users loggedInUser)throws Exception;
  
}
