package com.nseit.generic.service;

import java.util.List;

import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.UserBean;

public interface UserService {

	int insertUserDetails(UserBean userBean) throws Exception;

	public List<UserBean> getSearchUserDetails(UserBean userBean, Pagination pagination) throws Exception;

	int getUserCount(UserBean userBean) throws Exception;

	UserBean getUserEditDetails(UserBean userBean) throws Exception;

	int updateUserDetails(UserBean userBean) throws Exception;
}
