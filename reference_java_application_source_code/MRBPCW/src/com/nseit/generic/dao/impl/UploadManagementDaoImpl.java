package com.nseit.generic.dao.impl;

import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.nseit.generic.dao.BaseDAO;
import com.nseit.generic.dao.UploadManagementDao;
import com.nseit.generic.models.UploadManagementBean;
import com.nseit.generic.models.UserDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.queries.CandidateQueries;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.LoggerHome;

public class UploadManagementDaoImpl extends BaseDAO implements UploadManagementDao {

	private Logger logger = LoggerHome.getLogger(getClass());
	
	private JdbcTemplate writeJdbcTemplate;
	
	public void setWriteJdbcTemplate(JdbcTemplate writeJdbcTemplate) {
		this.writeJdbcTemplate = writeJdbcTemplate;
	}

	/**
	 * @author Shailendra Sharma
	 * @see com.nseit.generic.dao.UploadManagementDao#getUserId(java.lang.String)
	 */
	public int insertCandidateImages(UploadManagementBean uploadManagementBean, final Users loggedInUser) throws Exception {
		// logger.info("Calling UploadManagementDaoImpl()");
		int updateAndInsertCandidateImage = 0;

		try {
			if (getJdbcTemplate().queryForObject(CandidateQueries.CHECK_IMAGE_EXISTS_FOR_USERFK, new Object[] { loggedInUser.getUserId() }, Integer.class) == 0) {
				updateAndInsertCandidateImage = writeJdbcTemplate.update(CandidateQueries.INSERT_CANDIDATE_IMAGES,
						new Object[] { loggedInUser.getUserId(), uploadManagementBean.getByteArrayImage(), loggedInUser.getUsername() });
			} else {
				if (uploadManagementBean.getByteArrayImage() != null && uploadManagementBean.getByteArrayImage().length > 0) {
					updateAndInsertCandidateImage = writeJdbcTemplate.update(CandidateQueries.UPDATE_CANDIDATE_IMAGES,
							new Object[] { uploadManagementBean.getByteArrayImage(), loggedInUser.getUsername(), loggedInUser.getUserId() });

				}
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}

		return updateAndInsertCandidateImage;
	}

	/**
	 * @author Shailendra Sharma
	 * @see com.nseit.generic.dao.UploadManagementDao#getUserId(java.lang.String)
	 */
	public int insertCandidateSign(UploadManagementBean uploadManagementBean, final Users loggedInUser) throws Exception {
		// logger.info("Calling insertCandidateSign()");
		int updateAndInsertCandidateSign = 0;

		try {
			if (getJdbcTemplate().queryForObject(CandidateQueries.CHECK_IMAGE_EXISTS_FOR_USERFK, new Object[] { loggedInUser.getUserId() }, Integer.class) == 0) {

				updateAndInsertCandidateSign = writeJdbcTemplate.update(CandidateQueries.INSERT_CANDIDATE_SIGNATURE,
						new Object[] { loggedInUser.getUserId(), uploadManagementBean.getByteArrayImage(), loggedInUser.getUsername() });
				// logger.info("Inserting Candidate Image
				// "+updateAndInsertCandidateSign);
			} else {
				if (uploadManagementBean.getByteArrayImage() != null && uploadManagementBean.getByteArrayImage().length > 0) {
					updateAndInsertCandidateSign = writeJdbcTemplate.update(CandidateQueries.UPDATE_CANDIDATE_SIGNATURE,
							new Object[] { uploadManagementBean.getByteArrayImage(), loggedInUser.getUsername(), loggedInUser.getUserId() });

				}
				// logger.info("Updating Candidate Image
				// "+updateAndInsertCandidateSign);
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}

		return updateAndInsertCandidateSign;
	}

	public Long getUserId(String registrationId) throws Exception {
		// logger.info("Calling getUserId()");
		Long userId = null;
		UserDetailsBean userDetailsBean = null;
		try {
			userDetailsBean = null;
			if (userDetailsBean != null) {
				userId = Long.valueOf(userDetailsBean.getOUM_USER_PK());
			}
		} catch (Exception e) {
			userId = new Long(0);
		}
		return userId;
	}

	public Long getUserPK(String userId) throws Exception {
		// logger.info("Calling getUserId() "+userId);
		Long userPk;
		try {
			userPk = null;// (Long)
							// getJdbcTemplate().queryForObject(CandidateQueries.GET_USER_PK,
							// new Object[] { userId }, Long.class);
		} catch (Exception e) {
			userPk = new Long(0);
		}
		return userPk;
	}

	@Override
	public int saveOfflinePaymentDtls(UploadManagementBean uploadManagementBackingBean, Long userPk, Users loggedInUser) throws Exception {
		int insertAndUpdateOfflinePayment = 0;
		try {

			if (getJdbcTemplate().queryForObject(CandidateQueries.CHECK_OFFLINE_PAYMENT_EXISTS, new Object[] { userPk }, Integer.class) == 0) {

				insertAndUpdateOfflinePayment = writeJdbcTemplate.update(CandidateQueries.INSERT_OFFLINE_PAYMENT_DETAILS,
						new Object[] { uploadManagementBackingBean.getTransactionType(), uploadManagementBackingBean.getTransactionDate(), userPk,
								uploadManagementBackingBean.getPaymentStatus(), uploadManagementBackingBean.getTransactionNo(), uploadManagementBackingBean.getBankName(),
								uploadManagementBackingBean.getBankBranchName(), uploadManagementBackingBean.getBankCode(), uploadManagementBackingBean.getBankBranchCode(),
								uploadManagementBackingBean.getBankCityName(), loggedInUser.getUsername() });
			} else {

				Long offlinePaymentPk = getJdbcTemplate().queryForObject(CandidateQueries.GET_OFFLINE_PAYMENT_PK, new Object[] { userPk }, Long.class);

				insertAndUpdateOfflinePayment = writeJdbcTemplate.update(CandidateQueries.UPDATE_OFFLINE_PAYMENT_DETAILS,
						new Object[] { uploadManagementBackingBean.getTransactionType(), uploadManagementBackingBean.getTransactionDate(),
								uploadManagementBackingBean.getTransactionNo(), uploadManagementBackingBean.getBankName(), uploadManagementBackingBean.getBankBranchName(),
								uploadManagementBackingBean.getBankCode(), uploadManagementBackingBean.getBankBranchCode(), uploadManagementBackingBean.getBankCityName(),
								loggedInUser.getUsername(), offlinePaymentPk });
			}

		} catch (Exception e) {
			throw new GenericException(e);
		} finally {

		}
		return insertAndUpdateOfflinePayment;
	}

	@Override
	public String checkTestCenterName(String testCenterID) throws Exception {
		// logger.info("Calling checkTestCenterName");
		String testcenter = null;
		try {
			testcenter = (String) getJdbcTemplate().queryForObject(CandidateQueries.TEST_CENTER_NAME, new Object[] { testCenterID }, String.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return testcenter;
	}

	@Override
	public String candidateName(Long userPk) throws Exception {
		// logger.info("Calling candidateName()");
		String candidatename = null;
		try {
			candidatename = (String) getJdbcTemplate().queryForObject(CandidateQueries.SELECT_CANDIDATE_NAME, new Object[] { userPk }, String.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return candidatename;
	}

	@Override
	public int testCenter(String testCenterID) throws Exception {
		// logger.info("Calling testCenter() ");
		int count;
		try {
			count = getJdbcTemplate().queryForObject(CandidateQueries.CHECK_TEST_CENTER, new Object[] { testCenterID }, Integer.class);
		} catch (Exception e) {
			count = 0;
		}
		return count;
	}
}