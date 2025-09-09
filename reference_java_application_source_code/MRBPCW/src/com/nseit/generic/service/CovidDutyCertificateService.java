package com.nseit.generic.service;

import java.util.List;

import com.nseit.generic.models.CovidDutyCertDetailsBean;
import com.nseit.generic.models.CovidDutyCertificateBean;
import com.nseit.generic.models.Users;

public interface CovidDutyCertificateService {
	public String saveCovidDutyCertificateDetails(List<CovidDutyCertDetailsBean> covidDutyCertFinalLst, Users loggedInUser, CovidDutyCertificateBean covidDutyCertificateBean)
			throws Exception;

	public String deleteCovidDutyCertiDetails(Users loggedInUser) throws Exception;

	public List<CovidDutyCertDetailsBean> getCovidDutyCertiDetails(Long usrFk) throws Exception;

	public CovidDutyCertificateBean getCovidDutyCertiAddDetails(Users users) throws Exception;
}
