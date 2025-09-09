package com.nseit.generic.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.Users;

public interface CandidateUploadDocService {

  
  public String showUploadDocument(Users users, CandidateBean candidateBean,
      Map<String, Object> sessionAttributes, HttpServletRequest request)
  throws Exception;
  public CandidateUploadDocConstants saveUploadDocument(Users users, CandidateBean candidateBean,
      Map<String, Object> sessionAttributes, HttpServletRequest request)
  throws Exception;
  
  public enum CandidateUploadDocConstants{
    FILE_UPLOAD_FAILE,
    UPLOAD_DOC,
    UPLOAD_DOC_FAIL,
    SUCCESS
  }
  
  public String getDocumentUploaded(Users loggedInUser) throws Exception;
}
