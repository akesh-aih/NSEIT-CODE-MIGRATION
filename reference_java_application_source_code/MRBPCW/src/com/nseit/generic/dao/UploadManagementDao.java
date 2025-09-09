package com.nseit.generic.dao;

import com.nseit.generic.models.UploadManagementBean;
import com.nseit.generic.models.Users;

public interface UploadManagementDao {

	public int insertCandidateImages(UploadManagementBean uploadManagementBean, Users loggedInUser) throws Exception;

	public Long getUserId(String registrationId) throws Exception;

	public int insertCandidateSign(UploadManagementBean uploadManagementBean, final Users loggedInUser) throws Exception;

	public Long getUserPK(String userId) throws Exception;

	public int saveOfflinePaymentDtls(UploadManagementBean uploadManagementBackingBean, Long userPk, Users loggedInUser) throws Exception;

	public String checkTestCenterName(String testCenterID) throws Exception;

	public String candidateName(Long userPk) throws Exception;

	public int testCenter(String testCenterID) throws Exception;

}