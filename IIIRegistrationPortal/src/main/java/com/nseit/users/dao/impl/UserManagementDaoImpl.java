package com.nseit.users.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.nseit.generic.dao.BaseDao;
import com.nseit.generic.util.GenericException;
import com.nseit.users.dao.UserManagementDao; // Updated interface
import com.nseit.users.models.UserBean;
import com.nseit.users.queries.UserQueries;

public class UserManagementDaoImpl extends BaseDao implements UserManagementDao { // Updated class name and interface
    private static final Logger logger = LogManager.getLogger(UserManagementDaoImpl.class); // Updated logger name

    @Override
    public List<Map<String, Object>> getMenuPermissions(int userId) {
        try {
            return getJdbcTemplate().queryForList(UserQueries.GET_MENU_PERMISSIONS, userId);
        } catch (DataAccessException e) {
            logger.error("Error getting menu permissions for user: {}", userId, e);
            throw new GenericException("Error getting menu permissions", e);
        }
    }

    @Override
    public String saveUser(UserBean user, int currentUserId) {
        // This will be implemented later, as it requires a function call
        // TODO: Implement robust exception handling here
        return null;
    }

    // UserRowMapper is now part of LoginDaoImpl, so it's removed from here.
    // If UserManagementDaoImpl needs a RowMapper for User, it should have its own or a shared one.
    // For now, assuming saveUser doesn't need it directly.
}