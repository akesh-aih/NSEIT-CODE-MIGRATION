package com.nseit.generic.service;

import com.nseit.generic.models.UploadManagementBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.util.GenericServiceException;

public interface UploadManagementService {

	public int insertCandidateImages(UploadManagementBean uploadManagementBean, Users loggedInUser) throws GenericServiceException;

	public Long getUserId(String registrationId) throws Exception;

	public int insertCandidateSign(UploadManagementBean uploadManagementBean, Users loggedInUser) throws GenericServiceException;

	public Long getUserPK(String userId) throws Exception;

	public int saveOfflinePaymentDtls(UploadManagementBean uploadManagementBackingBean, Long userPk, Users loggedInUser) throws Exception;

	public int testCenter(String testCenterID) throws Exception;

}
