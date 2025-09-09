package com.nseit.generic.dao;

import java.util.List;
import java.util.Map;

import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.CandidateDetailsBean;
import com.nseit.generic.models.CandidateDocBean;
import com.nseit.generic.models.TemplateDownloadBean;
import com.nseit.generic.util.GenericException;

public interface TemplateDao {

	public TemplateDownloadBean getTemplate(String templateDesc) throws GenericException;
	
	public List<CandidateBean>  getCandidateImage() throws Exception;
	
	public List<CandidateBean>  getCandidateSignature() throws Exception;
	
	public List<CandidateDetailsBean> getCandidateDetailsList()throws GenericException;
	
	public List<CandidateDetailsBean> getCandidateDetailsListWithScheduling()throws GenericException;
	
	
	public List<CandidateBean>  getCandidateSignatureForScheduling() throws Exception;
	
	public List<CandidateBean>  getCandidateImageForScheduling() throws Exception;
	
	public Map<String, List<CandidateDocBean>> getCandidateDocumentMap() throws Exception;
	
}
