package com.nseit.generic.service.impl;

import org.apache.logging.log4j.Logger;

import com.nseit.generic.dao.UploadManagementDao;
import com.nseit.generic.models.UploadManagementBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.UploadManagementService;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.GenericServiceException;
import com.nseit.generic.util.LoggerHome;

/**
 * @author Laxman
 *
 */
/**
 * @author Laxman
 *
 */
public class UploadManagementServiceImpl implements UploadManagementService {

	private Logger logger = LoggerHome.getLogger(getClass());
	private UploadManagementDao uploadManagementDao;

	/**
	 * @author Shailendra Sharma
	 */
	public int insertCandidateImages(UploadManagementBean uploadManagementBean, Users loggedInUser) throws GenericServiceException {
		// logger.info("Calling insertCandidateImages() "+uploadManagementBean +
		// "Users "+loggedInUser);
		int insertCandidateImage = 0;
		try {
			insertCandidateImage = uploadManagementDao.insertCandidateImages(uploadManagementBean, loggedInUser);
		} catch (Exception ex) {
			try {
				throw new GenericException(ex);
			} catch (GenericException e) {
				throw new GenericServiceException(e);
			}
		}

		return insertCandidateImage;

	}

	/**
	 * @author Shailendra Sharma
	 * @param uploadManagementBean
	 * @param loggedInUser
	 * @return
	 * @throws Exception
	 */
	public int insertCandidateSign(UploadManagementBean uploadManagementBean, Users loggedInUser) throws GenericServiceException {
		// logger.info("Calling insertCandidateSign() "+uploadManagementBean +
		// "Users "+loggedInUser);
		int insertCandidateSign = 0;
		try {
			insertCandidateSign = uploadManagementDao.insertCandidateSign(uploadManagementBean, loggedInUser);
		} catch (Exception ex) {
			try {
				throw new GenericException(ex);
			} catch (GenericException e) {
				throw new GenericServiceException(e);
			}
		}

		return insertCandidateSign;

	}

	/**
	 * @author Nishant Verma
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Long getUserPK(String userId) throws Exception {
		// logger.info("Calling getUserPK() "+userId);
		return uploadManagementDao.getUserPK(userId);
	}

	public Long getUserId(String registrationId) throws Exception {
		return uploadManagementDao.getUserId(registrationId);
	}

	public void setUploadManagementDao(UploadManagementDao uploadManagementDao) {
		this.uploadManagementDao = uploadManagementDao;
	}

	public int saveOfflinePaymentDtls(UploadManagementBean uploadManagementBackingBean, Long userPk, Users loggedInUser) throws Exception {
		return uploadManagementDao.saveOfflinePaymentDtls(uploadManagementBackingBean, userPk, loggedInUser);
	}

	@Override
	public int testCenter(String testCenterID) throws Exception {
		return uploadManagementDao.testCenter(testCenterID);
	}

}
