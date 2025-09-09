package com.nseit.generic.service.impl;

import java.util.List;

import org.apache.logging.log4j.Logger;

import com.nseit.generic.dao.CommonDao;
import com.nseit.generic.dao.CovidDutyCertificateDao;
import com.nseit.generic.dao.WorkExperienceDao;
import com.nseit.generic.models.CovidDutyCertDetailsBean;
import com.nseit.generic.models.CovidDutyCertificateBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.models.WorkExperienceBean;
import com.nseit.generic.service.CovidDutyCertificateService;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;

public class CovidDutyCertificateServiceImpl implements CovidDutyCertificateService {
	private CovidDutyCertificateDao covidDutyCertificateDao;
	private CommonDao commonDao;
	private Logger logger = LoggerHome.getLogger(getClass());

	public void setCovidDutyCertificateDao(CovidDutyCertificateDao covidDutyCertificateDao) {
		this.covidDutyCertificateDao = covidDutyCertificateDao;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	@Override
	public List<CovidDutyCertDetailsBean> getCovidDutyCertiDetails(Long usrFk) throws Exception {
		return covidDutyCertificateDao.getCovidDutyCertiDetails(usrFk);
	}
	
	@Override
	  public CovidDutyCertificateBean getCovidDutyCertiAddDetails(Users users) throws Exception {
	  	return covidDutyCertificateDao.getCovidDutyCertiAddDetails(users);
	  }

	@Override
	public String saveCovidDutyCertificateDetails(List<CovidDutyCertDetailsBean> covidDutyCertDetailsList, Users loggedInUser, CovidDutyCertificateBean covidDutyCertificateBean)
			throws Exception {
		try {
			int count = covidDutyCertificateDao.isUserExistInCovidDutyCertDetails(loggedInUser);
			int addDetailsCount = covidDutyCertificateDao.isUserExistInCovidDutyCertAddDetails(loggedInUser);
			
			if (count > 0) {
				covidDutyCertificateDao.deleteCovidDutyCertForCandidate(loggedInUser);
				covidDutyCertificateDao.insertCovidDutyCertDetails(covidDutyCertDetailsList, loggedInUser, covidDutyCertificateBean);
			} else {
				covidDutyCertificateDao.insertCovidDutyCertDetails(covidDutyCertDetailsList, loggedInUser, covidDutyCertificateBean);
			}
			if (addDetailsCount > 0) {
				covidDutyCertificateDao.deleteCovidDutyCertAddDetails(loggedInUser);
				covidDutyCertificateDao.insertCovidDutyCertAddDetails(loggedInUser, covidDutyCertificateBean);
			} else {
				covidDutyCertificateDao.insertCovidDutyCertAddDetails(loggedInUser, covidDutyCertificateBean);
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return GenericConstants.SAVE_SUCCESS;
	}

	@Override
	public String deleteCovidDutyCertiDetails(Users loggedInUser) throws Exception {
		try {
			int count = covidDutyCertificateDao.isUserExistInCovidDutyCertDetails(loggedInUser);

			if (count > 0) {
				covidDutyCertificateDao.deleteCovidDutyCertForCandidate(loggedInUser);
			}
		} catch (Exception e) {
			logger.fatal(e, e);
			return null;
		}
		return GenericConstants.SAVE_SUCCESS;
	}

}
