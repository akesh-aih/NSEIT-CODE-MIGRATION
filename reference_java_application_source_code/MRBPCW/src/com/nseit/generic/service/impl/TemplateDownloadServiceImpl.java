package com.nseit.generic.service.impl;

import java.util.List;
import java.util.Map;

import com.nseit.generic.dao.TemplateDao;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.CandidateDetailsBean;
import com.nseit.generic.models.CandidateDocBean;
import com.nseit.generic.models.TemplateDownloadBean;
import com.nseit.generic.service.TemplateDownloadService;
import com.nseit.generic.util.GenericException;

public class TemplateDownloadServiceImpl implements TemplateDownloadService {
	
	private TemplateDao templateDao;

	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}
	
	
	public TemplateDownloadBean getTemplate(String templateDesc) throws GenericException{
		return templateDao.getTemplate(templateDesc);
	}
	
	public List<CandidateBean>  getCandidateImage() throws Exception{
		return templateDao.getCandidateImage();
	}
	
	public List<CandidateBean>  getCandidateSignature() throws Exception{
		return templateDao.getCandidateSignature();
	}
	
	public List<CandidateDetailsBean> getCandidateDetailsList()throws GenericException{
		return templateDao.getCandidateDetailsList();
	}
	
	public List<CandidateDetailsBean> getCandidateDetailsListWithScheduling()throws GenericException{
		return templateDao.getCandidateDetailsListWithScheduling();
	}
	
	public List<CandidateBean>  getCandidateSignatureForScheduling() throws Exception{
		return templateDao.getCandidateSignatureForScheduling();
	}

	public List<CandidateBean>  getCandidateImageForScheduling() throws Exception{
		return templateDao.getCandidateImageForScheduling();
	}


	@Override
	public Map<String, List<CandidateDocBean>> getCandidateDocumentMap()throws Exception {
		return templateDao.getCandidateDocumentMap();
	}
}
