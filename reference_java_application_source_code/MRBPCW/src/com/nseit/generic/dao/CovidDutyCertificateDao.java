package com.nseit.generic.dao;

import java.util.List;

import com.nseit.generic.models.CovidDutyCertDetailsBean;
import com.nseit.generic.models.CovidDutyCertificateBean;
import com.nseit.generic.models.Users;

public interface CovidDutyCertificateDao {

	public int isUserExistInCovidDutyCertDetails(Users loggedInUser) throws Exception;

	public int deleteCovidDutyCertForCandidate(Users users) throws Exception;

	public int[] insertCovidDutyCertDetails(final List<CovidDutyCertDetailsBean> covidDutyCertDetailsList, final Users logginUser,
			final CovidDutyCertificateBean covidDutyCertificateBean) throws Exception;

	public List<CovidDutyCertDetailsBean> getCovidDutyCertiDetails(Long usrFk) throws Exception;
	
	public CovidDutyCertificateBean getCovidDutyCertiAddDetails(Users users) throws Exception;

	public int isUserExistInCovidDutyCertAddDetails(Users loggedInUser) throws Exception;

	public int deleteCovidDutyCertAddDetails(Users loggedInUser) throws Exception;

	public int insertCovidDutyCertAddDetails(Users loggedInUser, CovidDutyCertificateBean covidDutyCertificateBean) throws Exception;

}
