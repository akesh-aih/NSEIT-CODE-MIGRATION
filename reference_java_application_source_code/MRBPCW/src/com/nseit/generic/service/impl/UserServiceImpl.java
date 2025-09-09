package com.nseit.generic.service.impl;

import java.util.List;

import com.nseit.generic.dao.UserDao;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.UserBean;
import com.nseit.generic.service.UserService;

public class UserServiceImpl implements UserService {
	UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public int insertUserDetails(UserBean userBean) throws Exception {
		return userDao.insertUserDetails(userBean);
	}

	@Override
	public List<UserBean> getSearchUserDetails(UserBean userBean, Pagination pagination) throws Exception {
		return userDao.getSearchUserDetails(userBean, pagination);
	}

	@Override
	public int getUserCount(UserBean userBean) throws Exception {
		return userDao.getUserCount(userBean);
	}

	@Override
	public UserBean getUserEditDetails(UserBean userBean) throws Exception {
		return userDao.getUserEditDetails(userBean);
	}

	@Override
	public int updateUserDetails(UserBean userBean) throws Exception {
		return userDao.updateUserDetails(userBean);
	}
}
