package com.nseit.users.service.impl;

import com.nseit.generic.util.LoginEncryptor;
import com.nseit.users.dao.UserDao;
import com.nseit.users.models.User;
import com.nseit.users.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User authenticate(String username, String password) {
        logger.info("Authenticating user: {}", username);

        User user = userDao.findByUsername(username);

        if (user == null) {
            logger.warn("Authentication failed: User not found - {}", username);
            throw new RuntimeException("Invalid credentials");
        }

        if (user.isSuspended()) {
            logger.warn("Authentication failed: User is suspended - {}", username);
            throw new RuntimeException("User has been suspended.");
        }

        if (!user.isActive()) {
            logger.warn("Authentication failed: User is inactive - {}", username);
            throw new RuntimeException("User is not active.");
        }

        String encryptedPassword = LoginEncryptor.encrypt(password);

        if (!encryptedPassword.equals(user.getPassword())) {
            logger.warn("Authentication failed: Invalid password for user - {}", username);
            throw new RuntimeException("Invalid credentials");
        }

        if (user.isChangePasswordOnNextLogin()) {
            logger.info("User must change password on next login: {}", username);
            throw new RuntimeException("Change password before login.");
        }

        // Authentication successful, update login status
        userDao.logLogin(user.getUserId(), user.getUserLoginId());
        userDao.updateLoginStatus(user.getUserLoginId());

        logger.info("User authenticated successfully: {}", username);

        // The full user object with all details is returned
        return user;
    }
}
