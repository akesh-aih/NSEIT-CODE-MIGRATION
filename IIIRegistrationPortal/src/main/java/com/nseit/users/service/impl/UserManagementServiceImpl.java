package com.nseit.users.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nseit.users.dao.UserManagementDao; // Updated DAO interface
import com.nseit.users.service.UserManagementService; // Implements UserManagementService

public class UserManagementServiceImpl implements UserManagementService {

    private static final Logger logger = LogManager.getLogger(UserManagementServiceImpl.class);

    private UserManagementDao userManagementDao; // Updated DAO field

    public void setUserManagementDao(UserManagementDao userManagementDao) { // Updated setter
        this.userManagementDao = userManagementDao;
    }

    // User management methods will be added here later
    // e.g., @Override
    // public List<User> getAllUsers() {
    //     return userManagementDao.getAllUsers();
    // }
}
