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

    private EmailUtil emailUtil;

    public void setEmailUtil(EmailUtil emailUtil) {
        this.emailUtil = emailUtil;
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

        // Encrypt the provided password for comparison
        String encryptedProvidedPassword;
        try {
            encryptedProvidedPassword = LoginEncryptor.encrypt(password);
        } catch (Exception e) {
            logger.error("Error encrypting provided password: {}", e.getMessage(), e);

            throw new RuntimeException("Authentication failed due to encryption error", e);
        }
        logger.debug("Encrypted Password - {}",encryptedProvidedPassword);
        
        if (!encryptedProvidedPassword.equals(user.getPassword())) {
            logger.warn("Authentication failed: Invalid password for user - {}", username);
            throw new RuntimeException("Invalid credentials");
        }

        if (user.isChangePasswordOnNextLogin()) {
            logger.info("User must change password on next login: {}", username);
            throw new RuntimeException("Change password before login.");
        }

        // Authentication successful, update login status
        logger.info("Before logLogin - User ID: {}, User Login ID: {}", user.getUserId(), user.getUserLoginId());
        userDao.logLogin(user.getUserId(), user.getUserLoginId());
        userDao.updateLoginStatus(user.getUserLoginId());

        logger.info("User authenticated successfully: {}", username);

        // The full user object with all details is returned
        return user;
    }

    @Override
    public void resetPassword(String loginId, String emailId) {
        logger.info("Attempting to reset password for Login ID: {} and Email ID: {}", loginId, emailId);

        // 1. Verify User
        User user = userDao.findUserByLoginIdAndEmail(loginId, emailId);

        if (user == null) {
            logger.warn("Password reset failed: User not found or email mismatch for Login ID: {}", loginId);
            throw new RuntimeException("Invalid Login ID or Email ID.");
        }

        // 2. Status Checks (from SP logic)
        if (!user.isActive()) {
            logger.warn("Password reset failed: User is inactive - {}", loginId);
            throw new RuntimeException("User ID is inactive. Please contact Technical Support");
        }

        if (user.isSuspended()) {
            logger.warn("Password reset failed: User is suspended - {}", loginId);
            throw new RuntimeException("User ID is suspended. Please contact Technical Support");
        }

        // 3. Generate New Passwords
        // Using UUID for simplicity, can be replaced with a more robust random string generator
        String newPassword = java.util.UUID.randomUUID().toString().substring(0, 8);
        String newTrxnPassword = java.util.UUID.randomUUID().toString().substring(0, 8); // Assuming transaction password is also reset

        logger.info("Generated new password for {}: {}", loginId, newPassword);
        logger.info("Generated new transaction password for {}: {}", loginId, newTrxnPassword);


        // 4. Encrypt New Passwords
        String encryptedNewPassword;
        String encryptedNewTrxnPassword;
        try {
            encryptedNewPassword = LoginEncryptor.encrypt(newPassword);
            encryptedNewTrxnPassword = LoginEncryptor.encrypt(newTrxnPassword);
        } catch (Exception e) {
            logger.error("Error encrypting new password for {}: {}", loginId, e.getMessage(), e);
            throw new RuntimeException("Failed to encrypt new password.", e);
        }

        // 5. Update Database
        // As per SP: bitChgPwdOnNxtLogin = 1, bitIsSuspended = 0
        userDao.updatePassword(loginId, encryptedNewPassword, encryptedNewTrxnPassword, true, false);
        logger.info("Password and transaction password updated for Login ID: {}", loginId);


        // 6. Send Email
        String subject = "Your Password for IIIRegistrationPortal has been Reset";
        String body = "Dear User,<br><br>"
                    + "Your password for IIIRegistrationPortal has been reset.<br>"
                    + "Your new Login Password is: <b>" + newPassword + "</b><br>"
                    + "Your new Transaction Password is: <b>" + newTrxnPassword + "</b><br><br>"
                    + "Kindly change your password on next login.<br><br>"
                    + "Thanks and Regards,<br>IIIRegistrationPortal Team";
        try {
            emailUtil.sendEmail(emailId, subject, body);
            logger.info("Email with new password sent to {} (Login ID: {})", emailId, loginId);
        } catch (Exception e) {
            logger.error("Failed to send password reset email to {} (Login ID: {}): {}", emailId, loginId, e.getMessage(), e);
            // Optionally, re-throw or handle this error differently if email sending is critical
            throw new RuntimeException("Failed to send password reset email. Please contact support.", e);
        }
    }
}
