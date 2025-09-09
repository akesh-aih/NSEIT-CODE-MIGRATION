package com.nseit.generic.dao;

import java.util.List;

import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.UserBean;

public interface UserDao {

	int insertUserDetails(UserBean userBean) throws Exception;

	List<UserBean> getSearchUserDetails(UserBean userBean, Pagination pagination) throws Exception;

	int getUserCount(UserBean userBean) throws Exception;

	UserBean getUserEditDetails(UserBean userBean) throws Exception;

	int updateUserDetails(UserBean userBean) throws Exception;

	public int checkUserId(UserBean userBean) throws Exception;

}
